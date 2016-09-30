package com.callippus.web.business.requestprocess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.requests.LoanRequestProcessBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.domainobject.LoanDomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.loan.beans.dto.ErpLoanRequestDTO;
import com.callippus.web.loan.beans.dto.GPFClosingBalanceDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;

@Service
public class ErpLoanRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(ErpLoanRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private LoanDomainObject loanDomainObject;

	public String initWorkflow(ErpLoanRequestDTO processBean) throws Exception {
		log.debug("::<<<<<ErpLoanRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(ErpLoanRequestProcessBean processBean)>>>>>>>>>");
		try {
			processBean.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			processBean.setRequestType(CPSConstants.LOAN);
			processBean.setRequestTypeID(CPSConstants.LOANREQUESTID);
			/*if(CPSUtils.compareStrings(processBean.getLoanType(), CPSConstants.FESTIVALLOANID) || CPSUtils.compareStrings(processBean.getLoanType(), CPSConstants.GPFADVANCELOANID)||CPSUtils.compareStrings(processBean.getLoanType(), CPSConstants.GPFWITHDRAWALLOANID)){
			processBean.setRequestType(CPSConstants.LOAN);
			processBean.setRequestTypeID(CPSConstants.LOANREQUESTID);
			}else{
				processBean.setRequestType(CPSConstants.LOANCONVEYANCE);
				processBean.setRequestTypeID(CPSConstants.LOANCONVEYANCEREQUESTID);	
			}*/
			processBean.setResult(submitLoanTxnDetails(processBean));

			if (CPSUtils.compareStrings(processBean.getResult(), CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(processBean, rb);
				processBean.setResult(txRequestProcess.initWorkflow(rb));

				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
					log.debug("::request workflow not assigned, so update in the main table");
					processBean.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
					//processBean.setResult(loanDomainObject.updateTxnDetails(processBean));
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}

	public String submitLoanTxnDetails(ErpLoanRequestDTO processBean) throws Exception {
		Session session = null;
		//ErpLoanRequestDTO erpLoanRequestDTO = null;
		try {
			session = hibernateUtils.getSession();
			processBean.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			processBean.setRequestedBy(processBean.getSfID());
			processBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			processBean.setErpLoanTypes(null);
			session.saveOrUpdate(processBean);
			/*ErpLoanRequestDTO erpLoanRequestDTO = new ErpLoanRequestDTO();
			erpLoanRequestDTO.setSfID(processBean.getSfID());
			erpLoanRequestDTO.setBasicPay(processBean.getBasicPay());
			erpLoanRequestDTO.setGrossPay(processBean.getGrossPay());
			erpLoanRequestDTO.setRequestedLoanType(processBean.getRequestedLoanType());
			erpLoanRequestDTO.setAmountRequested(processBean.getAmountRequested());
			erpLoanRequestDTO.setReasonForLoan(processBean.getReasonForLoan());
			erpLoanRequestDTO.setIpAddress(processBean.getIpAddress());
			erpLoanRequestDTO.setRequestedDate(processBean.getRequestedDate());
			erpLoanRequestDTO.setLoanStatus(processBean.getLoanStatus());*/
			
			//BeanUtils.copyProperties(processBean, erpLoanRequestDTO);
			/*//for time being resolving error 
			
			session.saveOrUpdate(erpLoanRequestDTO);*/

			processBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}

	public String approvedRequest(ErpLoanRequestDTO processBean) throws Exception {
		log.debug("::<<<<<LoanRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LoanRequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage())) {//changed from CPSConstants.UPDATE to CPSConstants.SUCCESS by mohib
				log.debug("::request workflow completed, so update in the main table");
				processBean.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	public WorkFlowMappingBean getErpLoanRequestDetails(WorkFlowMappingBean workflowBean) throws Exception {
		log.debug("::<<<<<LoanRequestProcess<<<<<<Method>>>>>>>>>>>>>>>getLoanRequestDetails(WorkFlowMappingBean workflowBean)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			workflowBean.setErpLoanRequestDTO((ErpLoanRequestDTO) session.get(ErpLoanRequestDTO.class, workflowBean.getRequestId()));

			workflowBean.setLoanPaymentDetails((LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(
					Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(workflowBean.getRequestId()))).uniqueResult());

			/*if (workflowBean.getLoanPaymentDetails().getLoanTypeID() == Integer.valueOf(CPSConstants.FESTIVALLOANID)) {
				workflowBean.setKeyValuePair((KeyValueDTO) session.createSQLQuery("select lfm.id,lfm.festival_name name from loan_festival_master lfm where lfm.status=1 and lfm.id=? ").addScalar(
						"id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setInteger(0,
						workflowBean.getLoanPaymentDetails().getLoanSubTypeID()).uniqueResult());
			} else if (workflowBean.getLoanPaymentDetails().getLoanTypeID() == Integer.valueOf(CPSConstants.GPFADVANCELOANID)
					|| workflowBean.getLoanPaymentDetails().getLoanTypeID() == Integer.valueOf(CPSConstants.GPFWITHDRAWALLOANID)) {
				workflowBean.setKeyValuePair((KeyValueDTO) session.createSQLQuery("select lgm.id,lgm.purpose name,lgm.gpf_rule value from loan_gpf_rules_master lgm where lgm.status=1 and lgm.id=?")
						.addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
						.setInteger(0, workflowBean.getLoanPaymentDetails().getLoanSubTypeID()).uniqueResult());
				workflowBean.getLoanRequestDetails().setGpfLoanBalance(((GPFClosingBalanceDTO) session.get(GPFClosingBalanceDTO.class, Integer.valueOf(((GPFClosingBalanceDTO)session.createCriteria(GPFClosingBalanceDTO.class).add(Expression.eq("sfID", workflowBean.getLoanRequestDetails().getSfID())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult()).getId()))).getBalance());
			}*/
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}

	public String declainedRequest(LoanRequestProcessBean processBean, String status, String message) throws Exception {
		log.debug("::<<<<<LoanRequestProcess<<<<<<Method>>>>>>>>>>>>>>>declainedRequest(LoanRequestProcessBean processBean,String status,String message)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				log.debug("::request workflow completed, so update in the main table");
				processBean.setStatus(Integer.valueOf(status));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();

	}

}