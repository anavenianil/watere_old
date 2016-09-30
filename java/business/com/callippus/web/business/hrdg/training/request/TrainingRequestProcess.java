package com.callippus.web.business.hrdg.training.request;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.TadaDomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.hrdg.dao.ICommonDAO;
import com.callippus.web.hrdg.training.request.ITrainingRequestDAO;
import com.callippus.web.hrdg.training.request.beans.dto.HrdgTxnRequestDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingNominationDTO;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingRequestBean;
import com.callippus.web.tada.dao.approval.ITadaTdApprovalRequestDAO;
import com.callippus.web.tada.dto.TadaRequestProcessBean;

@Service
public class TrainingRequestProcess extends TxRequestProcess{
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Autowired
	private TrainingRequestBusiness trainingRequestBusiness;
	
	
	public String initWorkflow(TrainingRequestBean trainingReqBean) throws Exception {
		//log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflowForLtcApprovalRequest(LtcRequestProcessBean ltcrb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			trainingReqBean.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			trainingReqBean.setRequestId(trainingReqBean.getRequestID());
			if(CPSUtils.compareStrings(CPSConstants.MANAGETRAININGNOMINATION, trainingReqBean.getParam())) {
				trainingReqBean.setRequestType(CPSConstants.TRAININGNOMINATION);
				trainingReqBean.setRequestTypeID(CPSConstants.TRAININGNOMINATIONID);
				
				message = trainingRequestBusiness.manageTrainingNomination(trainingReqBean);
				message = trainingRequestBusiness.insertTxnDetails(trainingReqBean);
			}
			else if(CPSUtils.compareStrings("CancelTrainingNominationAndInitiateReq", trainingReqBean.getParam())) {
				trainingReqBean.setRequestType("CANCEL TRAINING NOMINATION");
				trainingReqBean.setRequestTypeID(CPSConstants.CANCELTRAININGNOMINATIONID);
				message = trainingRequestBusiness.insertTxnCancelDetails(trainingReqBean);
			}
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				
				BeanUtils.copyProperties(rb,trainingReqBean);
				rb.setSfID(trainingReqBean.getNomineeSfid());
				
				message = txRequestProcess.initWorkflow(rb);
			}
		} catch(Exception e){
			message = CPSConstants.FAILED;
			throw e;
		}
		
		return message;
	}
	public String approvedRequest(TrainingRequestBean trainingReqBean) throws Exception {
		//log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LtcRequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(rb, trainingReqBean);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				//log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, trainingReqBean);
				if(CPSUtils.compareStrings(trainingReqBean.getRequestType(), CPSConstants.TRAININGNOMINATION))
				{
					int status = 15;
					trainingRequestBusiness.updateRequest(trainingReqBean,status);
				}
				if(CPSUtils.compareStrings(trainingReqBean.getRequestType(), CPSConstants.CANCELTRAININGNOMINATION))
				{
				String status = CPSConstants.STATUSCANCELLED;
				HrdgTxnRequestDTO historyDto = trainingRequestBusiness.getRefRequestId(trainingReqBean);
				txRequestProcess.declinedRequest(historyDto.getHistoryID(), trainingReqBean.getIpAddress(), trainingReqBean.getCancelReason(), CPSConstants.CANCELLED, status);
				
				int status1 = 5;
				trainingRequestBusiness.updateCancelRequest(trainingReqBean,status1);
			    trainingReqBean.setRequestID(historyDto.getRequestId().toString());
//				trainingRequestBusiness.updateRequest(trainingReqBean,status1);
				trainingRequestBusiness.updateCancelStatusOfRequest(trainingReqBean);
				} 
			}
			
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	public String updateRequest(TrainingRequestBean trainingReqBean) throws Exception {
		//log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LtcRequestProcessBean processBean)>>>>>>>>>");
		
		try {
			
			//if (CPSUtils.compareStrings(CPSConstants.SUCCESS, trainingReqBean.getMessage())) {
				//log.debug("::request workflow completed, so update in the main table");
				
//			if(CPSUtils.compareStrings(trainingReqBean.getRequestType(), CPSConstants.TRAININGNOMINATION) && CPSUtils.compareStrings("CancelTrainingNominationAndInitiateReq", trainingReqBean.getParam()))   //cancel training nomination request creation
//			{
//				int status = 2;
//				trainingRequestBusiness.updateRequest(trainingReqBean,status);
//				trainingRequestBusiness.cancelRequest(trainingReqBean,"0");
//				
//			}	
			 if(CPSUtils.compareStrings(trainingReqBean.getRequestType(), CPSConstants.TRAININGNOMINATION))  //training nomination deletion at first level
				{
					int status = 1;
				//	trainingRequestBusiness.updateRequest(trainingReqBean,status);
					trainingReqBean.setRequestID(trainingReqBean.getRequestId());
					String message = trainingRequestBusiness.updateCancelStatusOfRequest(trainingReqBean);
					trainingReqBean.setMessage(message);
				}
			else if(CPSUtils.compareStrings(trainingReqBean.getRequestType(), CPSConstants.CANCELTRAININGNOMINATION))  //training nomination deletion at first level
			{
				int status = 1;
				trainingRequestBusiness.updateCancelRequest(trainingReqBean,status);
				trainingRequestBusiness.cancelNewRequest(trainingReqBean,"0");
			}
			
			//	} 
			
		} catch (Exception e) {
			throw e;
		}
		return trainingReqBean.getMessage();
	}
	public String cancelRequest(TrainingRequestBean trainingReqBean) throws Exception {
                 ///     log.debug("::<<<<<cancelRequest<<<<<<Method>>>>>>>>>>>>>>>cancelRequest(TrainingRequestBean trainingReqBean)>>>>>>>>>");
		String message = null;
		try {
			if(CPSUtils.compareStrings(trainingReqBean.getRequestType(), CPSConstants.TRAININGNOMINATION))
			{  
				String status = CPSConstants.STATUSCANCELLED;
				message = txRequestProcess.declinedRequest(trainingReqBean.getHistoryID(), trainingReqBean.getIpAddress(), trainingReqBean.getCancelReason(), CPSConstants.CANCELLED, status);
				updateRequest(trainingReqBean);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return trainingReqBean.getMessage();
	}
	public String getReqIPAddress(String requestId,String requestType) throws Exception {
		List list = null;
		String ipAddress = null;
		try
		{
			list = trainingRequestBusiness.getReqIPAddress(requestId,requestType);
			ipAddress = (CPSUtils.checkList(list) && !CPSUtils.isNullOrEmpty(list.get(0)))?list.get(0).toString():null;
		}
		catch(Exception e)
		{
			
		}
		return ipAddress;
	}

}
