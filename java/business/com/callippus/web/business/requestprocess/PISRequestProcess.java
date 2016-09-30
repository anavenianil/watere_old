package com.callippus.web.business.requestprocess;

import java.util.ArrayList;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.PISRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.PISDomainObject;
import com.callippus.web.business.leave.request.CommonConstraints;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class PISRequestProcess extends TxRequestProcess {
	private static Log log = LogFactory.getLog(PISRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private PISDomainObject pisDomainObject;
	@Autowired
	private CommonConstraints commonConstraints;
	@Autowired
	private AddressRequestProcess addressRequestProcess;
	/**
	 * This method will be called when a user requested for his personal information changes
	 * 
	 * @param prb
	 * @return
	 */
	public String initWorkflow(PISRequestBean prb) throws Exception {
		log.debug("::<<<<<PISRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(PISRequestBean prb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			prb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			prb.setRequestType(CPSConstants.PIS);
			prb.setRequestTypeID(CPSConstants.PISREQUESTID);// PIS Request type ID = 1

			/**
			 * This is the PIS request, so we need to insert the requested modified data into the employee txn table
			 */
			prb = setChangedValues(prb);
			message = txRequestProcess.submitTxnDetails(prb.getTxnDetails());

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(prb, rb);
				message = txRequestProcess.initWorkflow(rb);

				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, prb);
					message = pisDomainObject.updateTxnDetails(prb);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public PISRequestBean setChangedValues(PISRequestBean prb) throws Exception {
		Session session = null;
		String message = null;
		JSONObject changedValues = null;
		ArrayList<WorkflowTxnDTO> txnDetails = null;
		int historyStatus=0;
		try {
			session = hibernateUtils.getSession();

			txnDetails = new ArrayList<WorkflowTxnDTO>();
			changedValues = (JSONObject) JSONSerializer.toJSON(prb.getChangedValues());

			EmployeeBean empBean = (EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq(CPSConstants.USERSFID, prb.getSfID())).add(Expression.eq(CPSConstants.STATUS, 1))
					.uniqueResult();

			if (!CPSUtils.isNull(empBean)) {
				if(!CPSUtils.isNullOrEmpty(changedValues.getString("PERSONAL_NUMBER"))){
				if (!CPSUtils.compareStrings(empBean.getPersonalNumber(), changedValues.getString("PERSONAL_NUMBER"))) {
					historyStatus=addressRequestProcess.getAddressTxnHistoryStatus(prb.getSfID(), 0, "PERSONAL_NUMBER");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6 || historyStatus == 0){
					txnDetails.add(txRequestProcess.txnRowDetails(prb.getRequestTypeID(), prb.getRequestID(), prb.getSfID(), "PERSONAL_NUMBER", prb.getIpAddress(), changedValues
							.getString("PERSONAL_NUMBER"), empBean.getPersonalNumber(), "0", CPSConstants.MOBILENUMBER));
					}else{
						message=CPSConstants.FAILED;
						prb.setRemarks(commonConstraints.setRemarkDetails(prb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.MOBILENUMBER).replace("%2%", CPSConstants.MOBILENUMBER)) );
					}	
				}else {
					message=CPSConstants.FAILED;
					prb.setRemarks(commonConstraints.setRemarkDetails(prb.getRemarks(),CPSConstants.UPDATENOTPOSSIBLE.replace("%1%", CPSConstants.MOBILENUMBER)) );
				}
				historyStatus=0;
				}
				if(!CPSUtils.isNullOrEmpty(changedValues.getString("INTERNAL_PHONE_NO"))){
				if (!CPSUtils.compareStrings(empBean.getInternalNo(), changedValues.getString("INTERNAL_PHONE_NO"))) {
					historyStatus=addressRequestProcess.getAddressTxnHistoryStatus(prb.getSfID(), 0, "INTERNAL_PHONE_NO");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6 || historyStatus == 0){
					txnDetails.add(txRequestProcess.txnRowDetails(prb.getRequestTypeID(), prb.getRequestID(), prb.getSfID(), "INTERNAL_PHONE_NO", prb.getIpAddress(), changedValues
							.getString("INTERNAL_PHONE_NO"), empBean.getInternalNo(), "0", CPSConstants.INTERNALNUMBER));
					}else{
						message=CPSConstants.FAILED;
						prb.setRemarks(commonConstraints.setRemarkDetails(prb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.INTERNALNUMBER).replace("%2%", CPSConstants.INTERNALNUMBER)) );
					}	
				}else {
					message=CPSConstants.FAILED;
					prb.setRemarks(commonConstraints.setRemarkDetails(prb.getRemarks(),CPSConstants.UPDATENOTPOSSIBLE.replace("%1%", CPSConstants.INTERNALNUMBER)) );
				}
				historyStatus=0;
				}
				if(!CPSUtils.isNullOrEmpty(changedValues.getString("RESIDENCE_NO"))){
				if (!CPSUtils.compareStrings(empBean.getResidenceNo(), changedValues.getString("RESIDENCE_NO"))) {
					historyStatus=addressRequestProcess.getAddressTxnHistoryStatus(prb.getSfID(), 0, "RESIDENCE_NO");
					if(historyStatus== 8 || historyStatus== 9 || historyStatus== 6 || historyStatus == 0){
					txnDetails.add(txRequestProcess.txnRowDetails(prb.getRequestTypeID(), prb.getRequestID(), prb.getSfID(), "RESIDENCE_NO", prb.getIpAddress(), changedValues.getString("RESIDENCE_NO"),
							empBean.getResidenceNo(), "0", CPSConstants.RESIDENCENUMBER));
				}else{
					message=CPSConstants.FAILED;
					prb.setRemarks(commonConstraints.setRemarkDetails(prb.getRemarks(),CPSConstants.PREVIOUSUPATIONPENDING.replace("%1%", CPSConstants.RESIDENCENUMBER).replace("%2%", CPSConstants.RESIDENCENUMBER)) );
				}	
				}else {
					message=CPSConstants.FAILED;
					prb.setRemarks(commonConstraints.setRemarkDetails(prb.getRemarks(),CPSConstants.UPDATENOTPOSSIBLE.replace("%1%", CPSConstants.RESIDENCENUMBER)) );
				}
				}
				historyStatus=0;
			}
			if(txnDetails.size()!=0 && message!=CPSConstants.FAILED){
			prb.setTxnDetails(txnDetails);
			}
		} catch (Exception e) {
			throw e;
		}
		return prb;
	}

	/**
	 * This method will be called when a user wants to approve the request
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String approvedRequest(PISRequestBean prb) throws Exception {
		log.debug("::<<<<<PISRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(PISRequestBean prb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(prb, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, prb);
				rb.setMessage(pisDomainObject.updateTxnDetails(prb));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

}
