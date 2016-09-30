package com.callippus.web.business.requestprocess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.domainobject.LoanDomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.loan.beans.dto.LoanHBAInterestGridDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;

@Service
public class LoanHBARequestProcess {
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private LoanDomainObject loanDomainObject;
	private static Log log = LogFactory.getLog(LoanHBARequestProcess.class);
	public String initWorkflow(LoanHBARequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<LoanHBARequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(LoanHBARequestProcessBean processBean)>>>>>>>>>");
		try {
			processBean.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			processBean.setRequestType(CPSConstants.HOUSEBUILDINGLOAN);
			processBean.setRequestTypeID(CPSConstants.LOANHBAREQUESTID);
			processBean.setResult(submitLoanTxnDetails(processBean));
			if (CPSUtils.compareStrings(processBean.getResult(), CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(processBean, rb);
				processBean.setResult(txRequestProcess.initWorkflow(rb));
				
				/**
				 * If No work flow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
					log.debug("::request workflow not assigned, so update in the main table");
					processBean.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
					processBean.setResult(loanDomainObject.updateTxnDetails(processBean));
				}
			}
		}catch(Exception e){
			throw e;
		}
		return processBean.getResult();
	}

	private String submitLoanTxnDetails(LoanHBARequestProcessBean processBean) throws Exception{
	Session session=null;
	LoanHBAInterestGridDTO hbaInterest=null;
	try{
		session = hibernateUtils.getSession();
		processBean.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
		processBean.setRequestedBy(processBean.getSfID());
		processBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
		hbaInterest=(LoanHBAInterestGridDTO)session.createSQLQuery("select interest_rate interestRate from loan_hba_interest_grid where applicable_year=(select max(applicable_year) from loan_hba_interest_grid) and "+processBean.getAdvanceReq()+" between lower_amount_limit and upper_amount_limit").addScalar("interestRate",Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(LoanHBAInterestGridDTO.class)).uniqueResult();
		//processBean.setInterestRate(hbaInterest.getInterestRate());
		if(!CPSUtils.isNullOrEmpty(processBean.getFloorGrid())){
		JSONObject floorGridJSON = new JSONObject(processBean.getFloorGrid());
		for (int i = 0; i < floorGridJSON.length(); i++) {
//GATTU			hibernateUtils.beginTransaction(session);
			LoanHBARequestProcessBean lhrb = new LoanHBARequestProcessBean();
			BeanUtils.copyProperties(processBean, lhrb);
			JSONObject rowDetails = (JSONObject) floorGridJSON.get(String.valueOf(i));
			lhrb.setFloorType(Integer.valueOf(rowDetails.getString("floorType")));
			lhrb.setEstimatedCost(Float.valueOf(rowDetails.getString("estimatedCost")));
			session.saveOrUpdate(lhrb);
			session.flush();
//GATTU			hibernateUtils.commitTransaction();
		}}
		else{
//GATTU			hibernateUtils.beginTransaction(session);
			session.saveOrUpdate(processBean);
			session.flush();
//GATTU			hibernateUtils.commitTransaction();
		}
//GATTU		hibernateUtils.beginTransaction(session);
		LoanHBAPaymentDTO loanPaymentDTO = new LoanHBAPaymentDTO();
		loanPaymentDTO.setRequestID(Integer.valueOf(processBean.getRequestID()));
		loanPaymentDTO.setSfID(processBean.getSfID());
		loanPaymentDTO.setLoanTypeID(Integer.valueOf(CPSConstants.HBALOANID));
		loanPaymentDTO.setLoanSubTypeID(Integer.valueOf(processBean.getHouseAdvanceType()));
		loanPaymentDTO.setRequestedAmount(Float.valueOf(processBean.getAdvanceReq()));
		loanPaymentDTO.setRequestedDate(CPSUtils.getCurrentDateWithTime());
		loanPaymentDTO.setRequestedInstalments(processBean.getNoOfInstalPrinciple());
		loanPaymentDTO.setRequestedInterestInstalments(processBean.getNoOfInstalInterest());
		loanPaymentDTO.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
		session.saveOrUpdate(loanPaymentDTO);
		processBean.setResult(CPSConstants.SUCCESS);
		
	}catch(Exception e){
		throw e;
	}
	return processBean.getResult();
}

	public WorkFlowMappingBean getLoanRequestDetails(WorkFlowMappingBean workflowBean) throws Exception {
		log.debug("::<<<<<LoanHBARequestProcess<<<<<<Method>>>>>>>>>>>>>>>getLoanRequestDetails(WorkFlowMappingBean workflowBean)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			workflowBean.setLoanHBARequestDetails((LoanHBARequestProcessBean) session.createCriteria(LoanHBARequestProcessBean.class).add(Expression.eq(CPSConstants.REQUESTID, workflowBean.getRequestId())).add(Expression.eq(CPSConstants.FLOORTYPE,Integer.valueOf(CPSConstants.FLOOR0))).uniqueResult());
	
			workflowBean.setLoanHBAPaymentDetails((LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(Expression.eq(CPSConstants.REQUESTID,Integer.valueOf(workflowBean.getRequestId()))).uniqueResult());
		
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowBean;
	}

	public String approvedRequest(LoanHBARequestProcessBean processBean) throws Exception{
		log.debug("::<<<<<LoanHBARequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LoanHBARequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				processBean.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	public String declainedRequest(LoanHBARequestProcessBean processBean,String status,String message) throws Exception{
		log.debug("::<<<<<LoanHBARequestProcess<<<<<<Method>>>>>>>>>>>>>>>declainedRequest(LoanHBARequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message )) {
				log.debug("::request workflow completed, so update in the main table");
				processBean.setStatus(Integer.valueOf(status));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			}
		}catch (Exception e) {
		throw e;
		}
	return rb.getMessage();
		
	}



}