package com.callippus.web.dao.holidays;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.holidays.HolidaysBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

@SuppressWarnings("serial")
@Service
public class SQLHolidaysDAO implements IHolidaysDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLHolidaysDAO.class);
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Override
	@SuppressWarnings("unchecked")
	public List<HolidaysBean> getHolidaysList(final String year) throws Exception {
		List<HolidaysBean> holidaysList;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			// commit transaction & close the session
			hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			sb.append("select id,holiday,to_char(holiday_date,'DD-Mon-YYYY') holidayDate,description from holidays_master where status=1 ");
			if (!CPSUtils.isNullOrEmpty(year)) {
				sb.append("and to_char(holiday_date,'YYYY')='" + year + "'");
			}
			sb.append(" order by holiday_date");
			holidaysList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("holiday").addScalar("holidayDate")
					.addScalar("description").setResultTransformer(Transformers.aliasToBean(HolidaysBean.class)).list();

		} catch (Exception e) {
			throw e;
		}
		return holidaysList;
	}

	@Override
	public String saveHolidaysDetails(HolidaysBean holidaysBean) throws Exception {
		Session session = null;
		String message = null;
		StringBuffer sb = new StringBuffer();
		String sfID = holidaysBean.getSfID();
		try {
			session = hibernateUtils.getSession();
			if (holidaysBean.getStatus() == 0) {
				// delete
				holidaysBean = (HolidaysBean) session.get(HolidaysBean.class, Integer.valueOf(holidaysBean.getPk()));
				holidaysBean.setStatus(0);
				holidaysBean.setHolidayDate(CPSUtils.formattedDate(holidaysBean.getHolidayDate()));
				holidaysBean.setLastModifiedBy(sfID);
				holidaysBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(holidaysBean);

				message = CPSConstants.SUCCESS;
			} else {
				// duplicate checking
				sb.append("select count(*) cnt from holidays_master where holiday_date=? and status=1 ");
				if (!CPSUtils.isNullOrEmpty(holidaysBean.getPk())) {
					sb.append("and id!=" + holidaysBean.getPk());
				}
				String count = session.createSQLQuery(sb.toString()).setString(0, holidaysBean.getHolidayDate()).uniqueResult().toString();
				if (CPSUtils.compareStrings(count, "0")) {
					if (CPSUtils.isNullOrEmpty(holidaysBean.getPk())) {
						// new
						holidaysBean.setStatus(1);
						holidaysBean.setCreatedBy(holidaysBean.getSfID());
						holidaysBean.setCreationDate(CPSUtils.getCurrentDate());
						holidaysBean.setLastModifiedBy(sfID);
						holidaysBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					} else {
						// update
						holidaysBean.setId(Integer.valueOf(holidaysBean.getPk()));
						holidaysBean.setStatus(1);
						holidaysBean.setLastModifiedBy(sfID);
						holidaysBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					}
					session.saveOrUpdate(holidaysBean);
					message = CPSConstants.SUCCESS;
				} else {
					// duplicate
					message = CPSConstants.DUPLICATE;
				}
			}
			session.flush();
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public void changeCalendarScript(final String realPath) throws Exception {
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			log.debug("::SQLHolidaysDAO>>>>>>>>>>>>changeCalendarScript(-,-) Start>>>>>>>>>>>>>>");
			session = hibernateUtils.getSession();

			//sb.append("Calendar._TT[\"HOLIDAYS\"] = \"");
			sb.append("var _TT = \"");
			sb.append(((HolidaysBean) session.createSQLQuery("select rtrim (xmlagg (xmlelement (e, to_char(holiday_date,'DD-Mon-YYYY') || ',')).extract ('//text()'), ',') holidayDate from holidays_master where status=1")
					.addScalar("holidayDate").setResultTransformer(Transformers.aliasToBean(HolidaysBean.class)).uniqueResult()).getHolidayDate());
			sb.append("\";");

			String fileName = realPath + File.separatorChar + "calendar-hd.js";
			log.debug("fileName ---> " + fileName);
			// A File object to represent the filename
			File f = new File(fileName);

			if (f.delete()) {
				FileOutputStream fos = new FileOutputStream(fileName);
				PrintStream ps = new PrintStream(fos);
				ps.print(sb.toString());
				ps.flush();
				ps.close();
			}

		} catch (Exception e) {
			throw e;
		}
		log.debug("::SQLHolidaysDAO>>>>>>>>>>>>changeCalendarScript(-,-) End>>>>>>>>>>>>>>");
	}
}