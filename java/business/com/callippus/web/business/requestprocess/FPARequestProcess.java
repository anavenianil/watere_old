package com.callippus.web.business.requestprocess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.domainobject.FPADomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;

@Service
public class FPARequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(LoanRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private FPADomainObject fpaDomainObject;

	public String initWorkflow(FPARequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<FPARequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(FPARequestProcessBean processBean)>>>>>>>>>");
		try {
			processBean.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			processBean.setRequestType(CPSConstants.FPA);
			processBean.setRequestTypeID(CPSConstants.FPAREQUESTID);

			processBean.setResult(submitFPATxnDetails(processBean));

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
					processBean.setResult(fpaDomainObject.updateTxnDetails(processBean));
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}

	public String submitFPATxnDetails(FPARequestProcessBean processBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			processBean.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			processBean.setRequestedBy(processBean.getSfID());
			processBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			session.saveOrUpdate(processBean);
			processBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}

	public WorkFlowMappingBean getFpaRequestDetails(WorkFlowMappingBean workflowBean) throws Exception {
		log.debug("::<<<<<FPARequestProcess<<<<<<Method>>>>>>>>>>>>>>>getFpaRequestDetails(WorkFlowMappingBean workflowBean)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			workflowBean.setFpaRequestDetails((FPARequestProcessBean) session.get(FPARequestProcessBean.class, workflowBean.getRequestId()));
		} catch (Exception e) {
			throw e;
		}
		return workflowBean;
	}

	public String approvedRequest(FPARequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<FPARequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(FPARequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				processBean.setStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
				rb.setMessage(fpaDomainObject.updateTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	public String declainedRequest(FPARequestProcessBean processBean, String status, String message) throws Exception {
		log.debug("::<<<<<FPARequestProcess<<<<<<Method>>>>>>>>>>>>>>>declainedRequest(FPARequestProcessBean processBean,String status,String message)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				log.debug("::request workflow completed, so update in the main table");
				processBean.setStatus(Integer.valueOf(status));
				rb.setMessage(fpaDomainObject.updateTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();

	}
}
