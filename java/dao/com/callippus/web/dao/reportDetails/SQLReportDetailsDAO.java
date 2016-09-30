package com.callippus.web.dao.reportDetails;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.reportDetails.ReportDetailsBean;
import com.callippus.web.controller.common.CPSConstants;

@Service
public class SQLReportDetailsDAO implements IReportDetailsDAO, Serializable {

	private static final long serialVersionUID = -3961924378632808464L;
	private static Log log = LogFactory.getLog(SQLReportDetailsDAO.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public List<ReportDetailsBean> designationsList() throws Exception {
		log.debug("SQLReDetailsDAO-->designationsList");
		List designationsList = null;
		Session session = null;

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			designationsList = session.createCriteria(DesignationDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return designationsList;
	}

	public ReportDetailsBean saveCpoolDetails(ReportDetailsBean reportDetailsBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(reportDetailsBean);
			session.flush();//tx.commit() ;

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return reportDetailsBean;
	}

	public ReportDetailsBean checkCpoolDetails(ReportDetailsBean reDetailsBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			reDetailsBean = (ReportDetailsBean) session.createCriteria(ReportDetailsBean.class).add(Expression.eq(CPSConstants.DESIGNATIONID, reDetailsBean.getDesignationId())).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return reDetailsBean;
	}
}
