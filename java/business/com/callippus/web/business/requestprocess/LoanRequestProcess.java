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
import com.callippus.web.loan.beans.dto.LoanDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;

@Service
public class LoanRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(LoanRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private LoanDomainObject loanDomainObject;

	public String initWorkflow(LoanRequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<LoanRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(LoanRequestProcessBean processBean)>>>>>>>>>");
		try {
			processBean.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			if(CPSUtils.compareStrings(processBean.getLoanType(), CPSConstants.FESTIVALLOANID) || CPSUtils.compareStrings(processBean.getLoanType(), CPSConstants.GPFADVANCELOANID)||CPSUtils.compareStrings(processBean.getLoanType(), CPSConstants.GPFWITHDRAWALLOANID)){
			processBean.setRequestType(CPSConstants.LOAN);
			processBean.setRequestTypeID(CPSConstants.LOANREQUESTID);
			}else{
				processBean.setRequestType(CPSConstants.LOANCONVEYANCE);
				processBean.setRequestTypeID(CPSConstants.LOANCONVEYANCEREQUESTID);	
			}
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
					processBean.setResult(loanDomainObject.updateTxnDetails(processBean));
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}

	public String submitLoanTxnDetails(LoanRequestProcessBean processBean) throws Exception {
		Session session = null;
		LoanDetailsDTO loanDetailsDTO = null;
		try {
			session = hibernateUtils.getSession();
			processBean.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			processBean.setRequestedBy(processBean.getSfID());
			processBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());

			session.saveOrUpdate(processBean);

			LoanPaymentDTO loanPaymentDTO = new LoanPaymentDTO();
			if(CPSUtils.isNullOrEmpty(processBean.getLoanSubType())){
				loanDetailsDTO =(LoanDetailsDTO)session.createCriteria(LoanDetailsDTO.class).add(Expression.eq("loanTypeID",Integer.valueOf(processBean.getLoanType()))).add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult();
			}else{
				loanDetailsDTO =(LoanDetailsDTO)session.createCriteria(LoanDetailsDTO.class).add(Expression.eq("loanTypeID",Integer.valueOf(processBean.getLoanType()))).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("loanSubTypeID",Integer.valueOf(processBean.getLoanSubType()))).uniqueResult();
			}
			loanPaymentDTO.setRequestID(Integer.valueOf(processBean.getRequestID()));
			loanPaymentDTO.setSfID(processBean.getSfID());
			loanPaymentDTO.setLoanTypeID(Integer.valueOf(processBean.getLoanType()));
			loanPaymentDTO.setInterestRate(loanDetailsDTO.getInterestRate());
			loanPaymentDTO.setLoanSubTypeID(Integer.valueOf(processBean.getLoanSubType()));
			loanPaymentDTO.setRequestedAmount(Float.valueOf(processBean.getReqAmount()));
			loanPaymentDTO.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			loanPaymentDTO.setRequestedInstalments(processBean.getRequestedInstalments());
			loanPaymentDTO.setRequestedInterestInstalments(processBean.getRequestedInterestInstalments());
			loanPaymentDTO.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			session.saveOrUpdate(loanPaymentDTO);
			processBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}

	public String approvedRequest(LoanRequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<LoanRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LoanRequestProcessBean processBean)>>>>>>>>>");
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

	public WorkFlowMappingBean getLoanRequestDetails(WorkFlowMappingBean workflowBean) throws Exception {
		log.debug("::<<<<<LoanRequestProcess<<<<<<Method>>>>>>>>>>>>>>>getLoanRequestDetails(WorkFlowMappingBean workflowBean)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			//workflowBean.setLoanRequestDetails((LoanRequestProcessBean) session.get(LoanRequestProcessBean.class, workflowBean.getRequestId()));
			
			workflowBean.setLoanRequestDetails((LoanRequestProcessBean) session.get(ErpLoanRequestDTO.class, workflowBean.getRequestId()));

			workflowBean.setLoanPaymentDetails((LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(
					Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(workflowBean.getRequestId()))).uniqueResult());

			if (workflowBean.getLoanPaymentDetails().getLoanTypeID() == Integer.valueOf(CPSConstants.FESTIVALLOANID)) {
				workflowBean.setKeyValuePair((KeyValueDTO) session.createSQLQuery("select lfm.id,lfm.festival_name name from loan_festival_master lfm where lfm.status=1 and lfm.id=? ").addScalar(
						"id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setInteger(0,
						workflowBean.getLoanPaymentDetails().getLoanSubTypeID()).uniqueResult());
			} else if (workflowBean.getLoanPaymentDetails().getLoanTypeID() == Integer.valueOf(CPSConstants.GPFADVANCELOANID)
					|| workflowBean.getLoanPaymentDetails().getLoanTypeID() == Integer.valueOf(CPSConstants.GPFWITHDRAWALLOANID)) {
				workflowBean.setKeyValuePair((KeyValueDTO) session.createSQLQuery("select lgm.id,lgm.purpose name,lgm.gpf_rule value from loan_gpf_rules_master lgm where lgm.status=1 and lgm.id=?")
						.addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
						.setInteger(0, workflowBean.getLoanPaymentDetails().getLoanSubTypeID()).uniqueResult());
				workflowBean.getLoanRequestDetails().setGpfLoanBalance(((GPFClosingBalanceDTO) session.get(GPFClosingBalanceDTO.class, Integer.valueOf(((GPFClosingBalanceDTO)session.createCriteria(GPFClosingBalanceDTO.class).add(Expression.eq("sfID", workflowBean.getLoanRequestDetails().getSfID())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult()).getId()))).getBalance());
			}
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