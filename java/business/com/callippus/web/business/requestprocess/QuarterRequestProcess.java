package com.callippus.web.business.requestprocess;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

@Service
public class QuarterRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(QuarterRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	
	/**
	 * Initial workflow.
	 * 
	 * @param lrb
	 *            the lrb
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String initWorkflow(QuarterRequestBean qrb) throws Exception {
		log.debug("<<<<<QuarterRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(QuarterRequestBean qrb)>>>>>>>>>");
		try {
			qrb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			if (CPSUtils.compareStrings(qrb.getType(), CPSConstants.NEW)) {
				qrb.setRequestTypeID(CPSConstants.NEWQUARTERREQUESTID);
				qrb.setResult(submitTxnDetails(qrb));
			} else {
				qrb.setRequestType(CPSConstants.CHANGEQUARTERREQ);
				qrb.setRequestTypeID(CPSConstants.CHANGEQUARTERREQUESTID);
				qrb.setResult(submitTxnDetails(qrb));
			}

			if (CPSUtils.compareStrings(qrb.getResult(), CPSConstants.SUCCESS)) {
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(qrb, rb);
				qrb.setResult(txRequestProcess.initWorkflow(rb));
			}
		} catch (Exception e) {
			throw e;
		}
		return qrb.getResult();
	}

	private String submitTxnDetails(QuarterRequestBean qrb) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if(CPSUtils.compareStrings(qrb.getRequestType(),CPSConstants.NEWQUARTERREQ)) 
				qrb.setStatus(1);
			else
				qrb.setStatus(Integer.parseInt(CPSConstants.QUARTER_CHANGE_REQ));
			qrb.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			if(!CPSUtils.isNullOrEmpty(qrb.getEntitledType())){
				if(qrb.getEntitledType().equalsIgnoreCase("y"))
					qrb.setEntitledType("A to D");
				else
					qrb.setEntitledType("E & above");
			}
			session.saveOrUpdate(qrb);
			qrb.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			qrb.setResult(CPSConstants.FAILED);
			throw e;
		} 
		return qrb.getResult();
	}

	/**
	 * This method will be called when a user wants to approve the request.
	 * 
	 * @param quarterBean
	 * @return
	 * @throws Exception
	 */
	public String approvedRequest(QuarterRequestBean quarterBean) throws Exception {
		log.debug("<<<<<QuarterRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(QuarterRequestBean lrb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(quarterBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			rb.setResult(rb.getMessage());
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getResult())) {
				rb.setResult(updateQuarterDetails(quarterBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getResult();
	}

	public String updateQuarterDetails(QuarterRequestBean quarterBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			QuarterRequestBean quarterBean1 = (QuarterRequestBean) session.createCriteria(QuarterRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, quarterBean.getRequestID()))
					.uniqueResult();
			quarterBean1.setAllotedQuarterID(0);
			quarterBean1.setAllotedDate(quarterBean.getAllotedDate());
			
			/*if(CPSUtils.compareStrings(quarterBean.getRequestType(), "CHANGE QUARTER"))
				quarterBean1.setStatus(Integer.valueOf(CPSConstants.QUARTER_CHANGE_REQ));
			else*/	
				quarterBean1.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
			quarterBean1.setWfStatus(Integer.valueOf(CPSConstants.QUARTER_ADMIN));
			quarterBean1.setPresentQuarter(quarterBean.getQuarterType());
			session.saveOrUpdate(quarterBean1);
			
			PayQuarterManagementDTO quarterMngDTO = (PayQuarterManagementDTO) session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.eq(CPSConstants.SFID, quarterBean1.getSfID())).uniqueResult();
			if (CPSUtils.isNull(quarterMngDTO)) {
				// New
				quarterMngDTO = new PayQuarterManagementDTO();
			}
			quarterMngDTO.setQuartersType(quarterBean.getQuarterType());
			quarterMngDTO.setQuarterNo(quarterBean.getQuarterNo());
			quarterMngDTO.setSfid(quarterBean1.getSfID());
			quarterMngDTO.setStatus(Integer.valueOf(CPSConstants.STATUSUPDATIONREQUIRED));
			quarterMngDTO.setCreatedBy(quarterBean.getSfID());
			quarterMngDTO.setCreationDate(CPSUtils.getCurrentDate());
			quarterMngDTO.setLastModifiedBy(quarterBean.getSfID());
			quarterMngDTO.setLastModifiedDate(quarterMngDTO.getCreationDate());
			quarterMngDTO.setOccupiedDate(quarterBean.getOccupiedDate());
			session.saveOrUpdate(quarterMngDTO);

			quarterBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return quarterBean.getResult();
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getQuarterRequestDetails(WorkFlowMappingBean workflowBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			workflowBean
					.setQuarterDetails((QuarterRequestBean) session.createCriteria(QuarterRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, workflowBean.getRequestId())).uniqueResult());
			
			if(CPSUtils.compareStrings(workflowBean.getQuarterDetails().getAllotment(),"Y")){
				List<PayQuarterManagementDTO> list=session.createSQLQuery("select quarter_no quarterNo from PAY_QUARTER_MANAGEMENT_DETAILS where sfid='"+workflowBean.getQuarterDetails().getSfID()+"' and (status=50 or status=1 or status=51)").addScalar("quarterNo").setResultTransformer(Transformers.aliasToBean(PayQuarterManagementDTO.class)).list();
				if(!CPSUtils.isNullOrEmpty(list) && list.size()!=0){
					workflowBean.setQuarterNo(list.get(0).getQuarterNo());
				}
			}
		} catch (Exception e) {
			throw e;
		} 
		return workflowBean;
	}
	
	//historyid,requestid,requesttype
	//public static final String STATUSCANCELLED = "9";
	
	public String cancelQuarterRequest(RequestBean requestBean) throws Exception {
		Session session = null;
		String message="";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			
			String sql="update QuarterRequestBean set status=? where requestID=?";
			
			Query query = session.createQuery(sql);
			query.setString(0, CPSConstants.STATUSCANCELLED);
			query.setString(1, requestBean.getRequestID());
			query.executeUpdate();
			message="cancel";
			/*String sql="update quarter_request_details set status="+CPSConstants.STATUSCANCELLED+" where request_id="+requestBean.getRequestID();
			session.createSQLQuery(sql).executeUpdate();*/
			
			
		} catch (Exception e) {
			throw e;
		} 
		finally{
			//session.close();
		}
		return message;
	}
	
}
