package com.callippus.web.business.requestworkflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.beans.requests.LoanRequestProcessBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.LoanDomainObject;
import com.callippus.web.business.domainobject.LtcDomainObject;
import com.callippus.web.business.domainobject.QuarterDomainObject;
import com.callippus.web.business.domainobject.TadaDomainObject;
import com.callippus.web.business.requestprocess.QuarterRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.workflowmapping.IWorkFlowMappingDAO;
import com.callippus.web.tada.dto.TadaRequestProcessBean;

@Service
public class RequestWorkFlowBusiness {
	@Autowired
	private IWorkFlowMappingDAO workflowMapDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private LoanDomainObject loanDomainObject;
	@Autowired
	private LtcDomainObject ltcDomainObject;
	@Autowired
	private QuarterDomainObject quarterDomainObject;
	@Autowired
	private TadaDomainObject tadaDomainObject;
	
	public ArrayList<RequestsDTO> getRequestStatusDetails(String requestID) throws Exception {
		ArrayList<RequestsDTO> statusDetails = null;
		try {
			statusDetails = new ArrayList<RequestsDTO>();
			statusDetails = workflowMapDAO.getRequestStatusDetails(requestID, null);
		} catch (Exception e) {
			throw e;
		}
		return statusDetails;
	}

	// For Delegation of anybody to anybody
	@SuppressWarnings("unchecked")
	public RequestBean getMasterData(RequestBean reqbean) throws Exception {
		try {
			reqbean.setEmpList(workflowMapDAO.getEmployeeList());
			reqbean.setInstanceList(commonDataDAO.getMasterData(CPSConstants.ORGINSTANCEDTO));
			reqbean.setRequestTypeList(workflowMapDAO.getRequestTypes());
		} catch (Exception e) {
			throw e;
		}
		return reqbean;
	}

	public RequestBean getSearchRequests(RequestBean rb) throws Exception {
		List requestList = null;
		List searchList = null;
		try {
			requestList = workflowMapDAO.getSearchRequestList(rb);
			if (CPSUtils.checkList(requestList)) {
				searchList = new ArrayList();
				for (int i = 0; i < requestList.size(); i++) {
					Object[] obj = (Object[]) requestList.get(i);
					RequestBean reqbean = new RequestBean();
					reqbean.setRequestID(obj[0].toString());
					reqbean.setSfID(obj[1].toString());
					reqbean.setName(obj[2].toString());
					reqbean.setRequestType(obj[3].toString());
					reqbean.setHistoryID(obj[4].toString());
					reqbean.setStageID(obj[5].toString());
					searchList.add(reqbean);
				}
				rb.setSearchList(searchList);
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
	public String setEmuStatus(RequestBean rb)throws Exception{
		try {
			rb.setMessage(workflowMapDAO.setEmuStatus(rb));
		if(CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.CHANGEQUARTERREQUESTID)) {
			QuarterRequestBean processBean = new QuarterRequestBean();
			BeanUtils.copyProperties(processBean, rb);
			processBean.setStatus(Integer.valueOf(CPSConstants.EMUREQUESTID));
			rb.setMessage(quarterDomainObject.updateTxnDetails(processBean));
		}else if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.NEWQUARTERREQUESTID)) {
			QuarterRequestBean processBean = new QuarterRequestBean();
			BeanUtils.copyProperties(processBean, rb);
			processBean.setStatus(Integer.valueOf(CPSConstants.EMUREQUESTID));
			rb.setMessage(quarterDomainObject.updateTxnDetails(processBean));
		}
		}catch(Exception e){
			throw e;
		}
		return rb.getMessage();
	}
	public String setWaitingStatus(RequestBean rb) throws Exception {
		try {
			rb.setMessage(workflowMapDAO.setWaitingStatus(rb));

			if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.SELFTRANSFERREQUESTTYPEID) || CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.EMPTRANSFERREQUESTTYPEID)) {
				rb.setMessage(workflowMapDAO.submitDOPartDetails(rb));
			} else if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.LOANREQUESTID) || CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.LOANCONVEYANCEREQUESTID)) {
				LoanRequestProcessBean processBean = new LoanRequestProcessBean();
				BeanUtils.copyProperties(processBean, rb);
				processBean.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			} else if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.LOANHBAREQUESTID)) {
				LoanHBARequestProcessBean processBean = new LoanHBARequestProcessBean();
				BeanUtils.copyProperties(processBean, rb);
				processBean.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			}  else if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.LTCSETTLEMENTREQID)) {
				rb.setMessage(ltcDomainObject.updateRestrictionAmount(rb));
			}else if(CPSUtils.compareStrings(rb.getRequestTypeID(), "48")){
				TadaRequestProcessBean tadaRPB=new TadaRequestProcessBean();
				BeanUtils.copyProperties(tadaRPB, rb);
				tadaRPB.setRequestType(CPSConstants.TADATDSETTLEMENTS);
				tadaRPB.setType(CPSConstants.TADATDSETTLEMENTS);
				tadaRPB.setRequestId(tadaRPB.getRequestID());
				tadaDomainObject.updateTxnDetails(tadaRPB);
				JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
				JSONObject accJson=(JSONObject)mainJson.get("accDetails");
				if(accJson.length()>0){
					//tadaDomainObject.submitTdNewDaAccDetails(tadaRPB);
				}
				/*if(!CPSUtils.isNullOrEmpty(tadaRPB.getMROPaidNo()) && !CPSUtils.isNullOrEmpty(tadaRPB.getMROPaidDate())){
					tadaDomainObject.submitMroDetails(tadaRPB);
					rb.setMessage(tadaDomainObject.submitMroPaymentDetails(tadaRPB));
				}else{
					rb.setMessage(tadaDomainObject.submitMroDetails(tadaRPB));
				}*/
				
				JSONObject mroJson = new JSONObject(tadaRPB.getJsonValue());
				JSONObject mainJson2 = (JSONObject)mroJson.getJSONObject("mroDetails");
				if(mainJson2.length()>0){
				tadaDomainObject.submitMroDetails(tadaRPB);
				}
				JSONObject tempJson = new JSONObject(tadaRPB.getJsonValue());
				JSONObject mainJson1 = (JSONObject)tempJson.getJSONObject("mroPaymentDetails");
				if(mainJson1.length()>0){
					rb.setMessage(tadaDomainObject.submitMroPaymentDetails(tadaRPB));
				}else{
					rb.setMessage(CPSConstants.SUCCESS);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	
	public String setHQSendingStatus(RequestBean rb) throws Exception {
		try {
			rb.setMessage(workflowMapDAO.setHQSendingStatus(rb));
			if(CPSUtils.compareStrings(rb.getMessage(),CPSConstants.SUCCESS)){
				
			if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.LOANREQUESTID) || CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.LOANCONVEYANCEREQUESTID)) {
				LoanRequestProcessBean processBean = new LoanRequestProcessBean();
				BeanUtils.copyProperties(processBean, rb);
				processBean.setStatus(Integer.valueOf(CPSConstants.STATUSHEADQUARTER));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			}else if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.LOANHBAREQUESTID)) {
				LoanHBARequestProcessBean processBean = new LoanHBARequestProcessBean();
				BeanUtils.copyProperties(processBean, rb);
				processBean.setStatus(Integer.valueOf(CPSConstants.STATUSHEADQUARTER));
				rb.setMessage(loanDomainObject.updateTxnDetails(processBean));
			}
		}
	} catch (Exception e) {
		throw e;
	}
	return rb.getMessage();
	}
	
	public String getDesignationId(String sfid) throws Exception{
		try{
			return workflowMapDAO.getDesignationId(sfid);
		}catch (Exception e) {
			throw e;
		}
	}
}
