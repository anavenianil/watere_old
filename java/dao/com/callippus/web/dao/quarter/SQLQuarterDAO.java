package com.callippus.web.dao.quarter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.callippus.web.beans.quarter.QuarterBean;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

@Service
public class SQLQuarterDAO implements IQuarterDAO, Serializable {
	private static final long serialVersionUID = 7048899710717627065L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<QuarterTypeBean> getQuarterSubTypeList() throws Exception {
		Session session = null;
		List<QuarterTypeBean> list = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();

			list = session.createCriteria(QuarterTypeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<PayQuarterManagementDTO> getQuarterDetailsList(QuarterBean quarterBean) throws Exception {
		Session session = null;
		List<PayQuarterManagementDTO> list = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();

			list = session.createSQLQuery("select qmd.id id,qsm.id quarterSubTypeID,qsm.quarters_sub_type quartersType,qtm.quarter_type quarter,qmd.quarter_no quarterNo, "
					+ "qmd.occupied_date occupiedDate from pay_quarter_management_details qmd,pay_quarters_subtype_master qsm,pay_quarters_type_master qtm "
					+ "where qmd.status=1 and qmd.sfid=? and qsm.status=1 and qsm.id=qmd.quarter_sub_type and qtm.status=1 and qtm.id=qsm.quarters_type_id").addScalar("id", Hibernate.INTEGER)
					.addScalar("quartersType", Hibernate.STRING).addScalar("quarter", Hibernate.STRING).addScalar("quarterNo", Hibernate.STRING).addScalar("occupiedDate", Hibernate.DATE)
					.addScalar("quarterSubTypeID", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayQuarterManagementDTO.class)).setString(0, quarterBean.getSfid()).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Transactional(readOnly = false)
	public String submitQuarterDetails(PayQuarterManagementDTO quarterManagementDTO) throws Exception {
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(quarterManagementDTO);
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		}
		return result;
	}

	@Transactional(readOnly = false)
	public String deleteQuarterDetails(QuarterBean quarterBean) throws Exception {
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
			PayQuarterManagementDTO quarterManagementDTO = (PayQuarterManagementDTO) session.get(PayQuarterManagementDTO.class, Integer.valueOf(quarterBean.getNodeID()));
			quarterManagementDTO.setLastModifiedBy(quarterBean.getLogInSfID());
			quarterManagementDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			quarterManagementDTO.setStatus(0);
			session.saveOrUpdate(quarterManagementDTO);
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Transactional(readOnly = true)
	public QuarterBean getUserQuarterDetails(QuarterBean quarterBean) throws Exception {
		Session session = null;
		String sql = "";
		List<PayQuarterManagementDTO> payQuarterManagementDTO = null;
		try
		{
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
			/*PayQuarterManagementDTO payQuarterManagementDTO = (PayQuarterManagementDTO) session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.eq(CPSConstants.SFID, quarterBean.getLogInSfID())).uniqueResult();*/
			int record = ((BigDecimal)session.createSQLQuery("SELECT COUNT(*) FROM quarter_request_details WHERE sfid = ? AND status = 1").setString(0, quarterBean.getLogInSfID()).uniqueResult()).intValue();
			if(record == 0) {
				sql = "select pqmd.SFID as sfid, pqmd.status status, pqmd.id from PAY_QUARTER_MANAGEMENT_DETAILS pqmd where pqmd.id = (select max(pqm.id) "
						+ "from  PAY_QUARTER_MANAGEMENT_DETAILS pqm where pqm.sfid = '"+ quarterBean.getLogInSfID() +"')";
				payQuarterManagementDTO = session.createSQLQuery(sql).addScalar("sfid", Hibernate.STRING).addScalar("status", Hibernate.INTEGER)
						.addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayQuarterManagementDTO.class)).list();
				if(payQuarterManagementDTO.size() > 0) {
					PayQuarterManagementDTO bean = (PayQuarterManagementDTO) session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq("id", payQuarterManagementDTO.get(0).getId())).uniqueResult();
					quarterBean.setUserQuarterDetails(bean);
					if(payQuarterManagementDTO.get(0).getStatus() == 51) { // vacated stage
						quarterBean.setResult("vacated");
						quarterBean.setQuarterStatus("vacated");
					} else if(payQuarterManagementDTO.get(0).getStatus() == 50) { // sending request to emu stage
						quarterBean.setResult(CPSConstants.EMPTY);
						quarterBean.setQuarterStatus("Empty");
					} else { // occupied stage
						quarterBean.setResult(CPSConstants.SUCCESS);
						quarterBean.setQuarterStatus("success");
					}
				} else {
					QuarterRequestBean requestBean = (QuarterRequestBean)session.createCriteria(QuarterRequestBean.class).add(Expression.eq("sfID", quarterBean.getLogInSfID()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("wfStatus", 0)).uniqueResult();
					if (!CPSUtils.isNull(requestBean)) { // Applied but workflow not completed
						quarterBean.setResult("Applied");
						quarterBean.setQuarterStatus("Applied");
					} else { // Request not applied
						quarterBean.setResult("Not Applied");
						quarterBean.setQuarterStatus("Not Applied");
					}
				}
			} else {
				quarterBean.setResult("Applied");
				quarterBean.setQuarterStatus("Not Applied");
			}

			/*
					if (!CPSUtils.isNull(payQuarterManagementDTO)) {
						if(payQuarterManagementDTO.size()>0)
							quarterBean.setUserQuarterDetails(payQuarterManagementDTO.get(0));
						quarterBean.setResult(CPSConstants.SUCCESS);
					}else {
						quarterBean.setResult(CPSConstants.EMPTY);
						//quarterBean.setResult(CPSConstants.SUCCESS);
					}
					//QuarterRequestBean quarterRequestBean=(QuarterRequestBean)session.createCriteria(QuarterRequestBean.class).add(Expression.eq("sfID", quarterBean.getLogInSfID())).add(Expression.not(Expression.in(CPSConstants.STATUS,new Integer[]{Integer.valueOf(CPSConstants.STATUSCANCELLED),Integer.valueOf(CPSConstants.STATUSDECLINED)}))).add(Expression.eq("requestTypeID",CPSConstants.NEWQUARTERREQUESTID)).uniqueResult();
					//QuarterRequestBean quarterRequestBean=(QuarterRequestBean)session.createCriteria(QuarterRequestBean.class).add(Expression.eq("sfID", quarterBean.getLogInSfID())).add(Expression.not(Expression.in(CPSConstants.STATUS,new Integer[]{Integer.valueOf(CPSConstants.STATUSCANCELLED),Integer.valueOf(CPSConstants.STATUSDECLINED)}))).add(Expression.not(Expression.in("wfStatus",new Integer[]{Integer.valueOf(CPSConstants.QUARTER_VACATED)}))).add(Expression.in("wfStatus",new Integer[]{Integer.valueOf(0)})).add(Expression.eq("requestTypeID",CPSConstants.NEWQUARTERREQUESTID)).uniqueResult();
					QuarterRequestBean quarterRequestBean=(QuarterRequestBean)session.createCriteria(QuarterRequestBean.class).add(Expression.eq("sfID", quarterBean.getLogInSfID())).add(Expression.not(Expression.in(CPSConstants.STATUS,new Integer[]{Integer.valueOf(CPSConstants.STATUSCANCELLED),Integer.valueOf(CPSConstants.STATUSDECLINED)}))).add(Expression.not(Expression.in("wfStatus",new Integer[]{Integer.valueOf(CPSConstants.QUARTER_VACATED)}))).add(Expression.in("wfStatus",new Integer[]{Integer.valueOf(0)})).add(Expression.eq("requestTypeID",CPSConstants.NEWQUARTERREQUESTID)).uniqueResult();
					if(!CPSUtils.isNull(quarterRequestBean)){
						quarterBean.setQuarterStatus("Applied");
						sql="select rwh.status as status from request_workflow_history rwh where rwh.request_id='"+quarterRequestBean.getRequestID()+"'";
						List<RequestCommonBean> commonBean=session.createSQLQuery(sql).addScalar("status",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(RequestCommonBean.class)).list();
						for(int i=0;i<commonBean.size();i++){
							if(commonBean.get(i).getStatus()==6 || commonBean.get(i).getStatus()==9)
								quarterBean.setQuarterStatus("Not Applied");
						}
						//PayQuarterManagementDTO quarterManagementDTO = (PayQuarterManagementDTO) session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.parseInt(CPSConstants.PAY_QUARTER_VACATED))).add(Expression.eq(CPSConstants.SFID, quarterBean.getLogInSfID())).setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult();
						sql="select pqmd.SFID as sfid,pqmd.status status from PAY_QUARTER_MANAGEMENT_DETAILS pqmd where pqmd.id=(select max(pqm.id) from  PAY_QUARTER_MANAGEMENT_DETAILS pqm where pqm.sfid='"+quarterBean.getLogInSfID()+"')" ;
						List<PayQuarterManagementDTO> list=session.createSQLQuery(sql).addScalar("sfid").addScalar("sfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayQuarterManagementDTO.class)).list();
						if(list.size()>0){
							if(list.get(0).getStatus()==51)
								quarterBean.setQuarterStatus("vacated");
							else
								quarterBean.setResult(CPSConstants.SUCCESS);
						}
					}else{
						quarterBean.setQuarterStatus("Not Applied");
					}
			 */
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<QuarterRequestBean> getQuarterEmuDetailsList(QuarterBean quarterBean) throws Exception {
		Session session = null;
		List<QuarterRequestBean> list = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
			list = session.createSQLQuery("select id,sfid sfID,request_id requestID,request_type_id requestTypeID,requested_date requestedDate,status "
					+ "from quarter_request_details where (request_type_id = ? or request_type_id = ?) and status=?").addScalar("requestedDate",Hibernate.DATE)
					.addScalar("id",Hibernate.INTEGER).addScalar("requestTypeID",Hibernate.STRING).addScalar("sfID",Hibernate.STRING).addScalar("status",Hibernate.INTEGER)
					.addScalar("requestID",Hibernate.STRING).setString(0,CPSConstants.NEWQUARTERREQUESTID).setString(1,CPSConstants.CHANGEQUARTERREQUESTID).setString(2,CPSConstants.EMUREQUESTID).setResultTransformer(Transformers.aliasToBean(QuarterRequestBean.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
}