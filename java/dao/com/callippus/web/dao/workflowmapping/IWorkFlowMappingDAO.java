package com.callippus.web.dao.workflowmapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.ReqRoleWorkMappingDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.higherQualification.dto.HQRequestDTO;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;

public interface IWorkFlowMappingDAO {
	public String saveValues(ReqRoleWorkMappingDTO workflowMap) throws Exception;

	public List getAllList() throws Exception;

	public String deleteMapping(WorkFlowMappingBean workflowMap) throws Exception;

	public List getReqWorkFlowList() throws Exception;

	public String manageWorkFlowList(WorkFlowMappingBean workflowmap) throws Exception;

	public String deleteReqWorkFlow(String id, String workflowType) throws Exception;

	public boolean checkReqWorkData(WorkFlowMappingBean workflowMap) throws Exception;

	public ArrayList<RequestsDTO> assignedRequests(String sfid, String type, String limit, String size, String requestType, String requestID, String fromDate, String toDate) throws Exception;

    public List<AlertMessageDTO> assignedalertList(String sfid,String limit, String size, String alertType,String fromDate, String toDate) throws Exception;
	
	public WorkFlowMappingBean getRequestDetails(WorkFlowMappingBean workflowMap) throws Exception;

	public List<KeyValueDTO> requestDelegateList(WorkFlowMappingBean workflowMap) throws Exception;

	public ArrayList<RequestsDTO> getRequestStatusDetails(String requestID, String requesterSfid) throws Exception;

	public WorkFlowMappingBean getChangedDetails(WorkFlowMappingBean workflowMap) throws Exception;


	public boolean checkReqRoleWorkData(WorkFlowMappingBean workflowMap) throws Exception;

	public List getRequestTypes() throws Exception;

	public List getEmployeeList() throws Exception;

	public List getSearchRequestList(RequestBean rb) throws Exception;

	public WorkFlowMappingBean getWorkflowStage(WorkFlowMappingBean workflowmap) throws Exception;

	public String getCheckStage(String requestID, String requestType) throws Exception;
	
	public String getLastStagePendingCheck(String requestID,String sfID) throws Exception;

	public List<KeyValueDTO> cghsDelegateList() throws Exception;

	public String setWaitingStatus(RequestBean rb) throws Exception;

	public ArrayList<RequestsDTO> myRequests(String sfid, String limit, String size, String requestType, String requestID, String fromDate, String toDate) throws Exception;

	public String getPresentRequestStage(String requestID, String sfid) throws Exception;
	
	public List<RequestsDTO> myHoldRequests(String sfID,String requestId,String type) throws Exception;
	
	public List<RequestsDTO> totalHoldRequests(String sfID,String requestId) throws Exception;

	public String submitDOPartDetails(RequestBean rb) throws Exception;
	
	public List<DoPartBean> getDopartNumber(Date doPartDate,String gazType)throws Exception;
	
	public List<AlertMessageDTO> myAlerts(String sfID, String limit, String string)throws Exception;
	
	public AlertMessageDTO getAlertDetails(WorkFlowMappingBean workflowmap)throws Exception;
	
	public String submitAlertResponse(WorkFlowMappingBean workflowmap)throws Exception;

	public WorkFlowMappingBean editInstallmentDetails(WorkFlowMappingBean workBeanMap)throws Exception;

	public QuarterRequestBean getQuarterDetails(WorkFlowMappingBean workBeanMap)throws Exception;

	public String setEmuStatus(RequestBean rb)throws Exception;

	public String setHQSendingStatus(RequestBean rb)throws Exception;

	public HQRequestDTO getHQRequestDetails(WorkFlowMappingBean workBeanMap)throws Exception;
	//public String requestStatus(String sfid) throws Exception;
	
	 public List<TelephoneDesigEligibilityDetailsDTO> teleMaxAmountCondition(WorkFlowMappingBean workBeanMap)throws Exception;
	
	 public String getDesignationId(String sfid) throws Exception;

	 

	public WorkFlowMappingBean  cghsreque(WorkFlowMappingBean workflowMap) throws Exception;

	

	 
	 public WorkFlowMappingBean getfinanceAcceptableAmount(WorkFlowMappingBean woBean)throws Exception;
	 
	 public WorkFlowMappingBean getCdaDetails(WorkFlowMappingBean woBean)throws Exception;
	 
	 public WorkFlowMappingBean getRoleInstanceName(WorkFlowMappingBean woBean)throws Exception;

	 public WorkFlowMappingBean getApprovedBillNumbers(WorkFlowMappingBean woBean)throws Exception;

}
