package com.callippus.web.business.workflowmapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.MT.MTRequestDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleAllocationtDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleDriverMapDTO;
import com.callippus.web.beans.MT.VehicleDetailsBean;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.ReqRoleWorkMappingDTO;
import com.callippus.web.beans.dto.RequestWorkDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.higherQualification.dto.HQRequestDTO;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneBillClaimDetailsDTO;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimDetailsDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.domainobject.TadaDomainObject;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.MT.IMechincalTransportDAO;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.quarter.IQuarterRequestDAO;
import com.callippus.web.dao.telephone.ITelePhoneBillDAO;
import com.callippus.web.dao.tutionFee.ITutionFeeDAO;
import com.callippus.web.dao.workflowmapping.IWorkFlowMappingDAO;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.promotions.dao.management.IPromotionManagementDAO;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dao.request.ITadaRequestDAO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaRequestProcessBean;
import com.callippus.web.tada.dto.TadaTdAccDetDayRepDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;

@Service
public class WorkFlowMappingBusiness {
	@Autowired
	private IWorkFlowMappingDAO workflowMapDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IPromotionManagementDAO promotionDAO;
	@Autowired
	private IQuarterRequestDAO quarterRequestDAO;
	@Autowired
    private ITutionFeeDAO iTutionFeeDAO;
	@Autowired
    private ITadaRequestDAO iTadaRequestDAO;
    @Autowired
    private IMechincalTransportDAO iMechincalTransportDAO;
    @Autowired
    private ITelePhoneBillDAO iTelePhoneBillDAO;
    @Autowired
    private TadaDomainObject tdDomainObject;
    
	public WorkFlowMappingBean getHomeMappingDetails() throws Exception {
		WorkFlowMappingBean workflowmap = new WorkFlowMappingBean();
		List list = null;
		try {
			workflowmap.setInstanceList(commonDataDAO.getMasterData(CPSConstants.ORGINSTANCEDTO));
			workflowmap.setRequestList(commonDataDAO.getMasterData(CPSConstants.REQUESTWORKDTO));
			workflowmap.setWorkflowList(commonDataDAO.getMasterData(CPSConstants.WORKFLOWMASTERDTO));
			list = workflowMapDAO.getAllList();
			workflowmap.setAllList(list);

		} catch (Exception e) {
			throw e;
		}
		return workflowmap;
	}

	public WorkFlowMappingBean saveValues(WorkFlowMappingBean workflowmap) throws Exception {
		String message = "";
		List list = null;
		try {
			ReqRoleWorkMappingDTO reqrolework = new ReqRoleWorkMappingDTO();

			reqrolework.setRoleInstanceId(Integer.parseInt(workflowmap.getInstanceId()));
			reqrolework.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
			reqrolework.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
			reqrolework.setCreationDate(CPSUtils.getCurrentDate());
			reqrolework.setLastModifiedDate(CPSUtils.getCurrentDate());
			if (workflowMapDAO.checkReqRoleWorkData(workflowmap)) {

				if (CPSUtils.isNullOrEmpty(workflowmap.getId()) || workflowmap.getId() == 0) {
					reqrolework.setId(commonDataDAO.getTableID("ROLE_WORKFLOW_MAPPING", CPSConstants.NEW));
					reqrolework.setStatus(1);
					message = workflowMapDAO.saveValues(reqrolework);
					workflowmap.setMessage(message);
					commonDataDAO.updateTableID("ROLE_WORKFLOW_MAPPING", String.valueOf(reqrolework.getId()));

				} else {
					reqrolework.setId(workflowmap.getId());
					reqrolework.setStatus(1);
					reqrolework.setLastModifiedDate(CPSUtils.getCurrentDate());
					message = workflowMapDAO.saveValues(reqrolework);
					workflowmap.setMessage(message);
				}
			}
			list = workflowMapDAO.getAllList();
			workflowmap.setAllList(list);
		} catch (Exception e) {
			throw e;
		}
		return workflowmap;
	}

	public WorkFlowMappingBean deleteMapping(WorkFlowMappingBean workflowmap) throws Exception {
		List list = null;
		try {
			workflowmap.setMessage(workflowMapDAO.deleteMapping(workflowmap));
			list = workflowMapDAO.getAllList();
			workflowmap.setAllList(list);
		} catch (Exception e) {
			throw e;
		}
		return workflowmap;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getRequestWorkFlowType() throws Exception {
		WorkFlowMappingBean workflowmap = new WorkFlowMappingBean();
		try {
			workflowmap.setRequestList(commonDataDAO.getMasterData(CPSConstants.REQUESTWORKDTO));
			workflowmap.setWorkflowList(commonDataDAO.getMasterData(CPSConstants.WORKFLOWMASTERDTO));
			workflowmap.setRoleInstanceList(commonDataDAO.getMasterData(CPSConstants.ORGINSTANCEDTO));
			workflowmap.setReqWorkFlowList(workflowMapDAO.getReqWorkFlowList());
			workflowmap.setDesignationList(commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO));
			workflowmap.setOrganizationsList(commonDataDAO.getMasterData("OrganizationsDTO"));
		} catch (Exception e) {
			throw e;
		}
		return workflowmap;
	}

	public String getReqWorkFlowList(WorkFlowMappingBean workflowmap) throws Exception {
		String message = "";
		try {
			if (workflowMapDAO.checkReqWorkData(workflowmap)) {
				message = workflowMapDAO.manageWorkFlowList(workflowmap);
			} else {
				message = CPSConstants.DUPLICATE;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deleteReqWorkFlow(String id, String workflowType) throws Exception {
		String message = "";
		try {
			message = workflowMapDAO.deleteReqWorkFlow(id, workflowType);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public WorkFlowMappingBean pisHomeDetails(String sfid,String type) throws Exception {
		WorkFlowMappingBean workflowmap = null;
		try {
			workflowmap = new WorkFlowMappingBean();
			workflowmap.setRequestSize(commonDataDAO.getConfigurationValue(CPSConstants.DASHBOARD_RECORDS));
			workflowmap.setMyalertSize(commonDataDAO.getConfigurationValue(CPSConstants.MY_REQUESTS_DASHBOARD_RECORDS));
			workflowmap.setPendingRequests(workflowMapDAO.assignedRequests(sfid, "'PENDING'", CPSConstants.LIMIT, workflowmap.getRequestSize(), null, null, null, null));
			workflowmap.setDelegatedRequests(workflowMapDAO.assignedRequests(sfid, "'DELEGATED'", CPSConstants.LIMIT, workflowmap.getRequestSize(), null, null, null, null));
			workflowmap.setCompletedRequests(workflowMapDAO.assignedRequests(sfid, "'APPROVED','DECLINED','COMPLETED','SANCTIONED'", CPSConstants.LIMIT, workflowmap.getRequestSize(), null, null,
					null, null));
			workflowmap.setEscalatedRequests(workflowMapDAO.assignedRequests(sfid, "'ESCALATED'", CPSConstants.LIMIT, workflowmap.getRequestSize(), null, null, null, null));
			workflowmap.setMyRequestSize(commonDataDAO.getConfigurationValue(CPSConstants.MY_REQUESTS_DASHBOARD_RECORDS));
			workflowmap.setMyRequests(workflowMapDAO.myRequests(sfid, CPSConstants.LIMIT, workflowmap.getMyRequestSize(), null, null, null, null));
		//	workflowmap.setMyAlerts(workflowMapDAO.myAlerts(sfid,CPSConstants.LIMIT, workflowmap.getMyalertSize()));
			workflowmap.setMyHoldRequests(workflowMapDAO.myHoldRequests(sfid,null,type));
			//workflowmap.setType(workflowMapDAO.requestStatus(sfid));
			
			if(workflowmap.getMyHoldRequests().size()!=0){
			workflowmap.setCount(workflowmap.getMyHoldRequests().get(0).getCount());
			}
			workflowmap.setTotalHoldRequests(workflowMapDAO.totalHoldRequests(sfid,null));

		} catch (Exception e) {
			throw e;
		}
		return workflowmap;
	}
  
	public WorkFlowMappingBean getRequestDetails(WorkFlowMappingBean workflowmap) throws Exception {
		try {
			workflowmap = workflowMapDAO.getRequestDetails(workflowmap);
			if(!CPSUtils.compareStrings(workflowmap.getRequestnotexist(),"requestNotExist")){
				workflowmap = workflowMapDAO.getWorkflowStage(workflowmap);
				workflowmap.setWorkflowHistory(workflowMapDAO.getRequestStatusDetails(workflowmap.getRequestId(), workflowmap.getRequesterSfid()));
				if (CPSUtils.compareStrings(workflowmap.getMessage(), CPSConstants.PENDING) || CPSUtils.compareStrings(workflowmap.getMessage(), CPSConstants.DELEGATED)) {
				/**
				 * only for delegate & pending requests we should display the manual delegate employees list
				*/
				workflowmap.setDelegateList(workflowMapDAO.requestDelegateList(workflowmap));
				}
			}
			else {
				workflowmap.setRequestnotexist(CPSConstants.REQUESTNOTEXIST);
			}
		} catch (Exception e) {
			throw e;
		}
		return workflowmap;
	}
  
	/*
	//role
	 public WorkFlowMappingBean getRoleRequestDetails(String sfid,WorkFlowMappingBean workflowmap) throws Exception {
		String message = "";
		try {
			
			message = workflowmap.setRoleType(workflowMapDAO.getSpecificRequestDetails(sfid,workflowmap));
			  
			if(CPSUtils.isNullOrEmpty(message)){
				  
              workflowmap = workflowMapDAO.getRequestDetails(workflowmap);
		       workflowmap = workflowMapDAO.getWorkflowStage(workflowmap);
			  workflowmap.setWorkflowHistory(workflowMapDAO.getRequestStatusDetails(workflowmap.getRequestId(), workflowmap.getRequesterSfid()));
			  if (CPSUtils.compareStrings(workflowmap.getMessage(), CPSConstants.PENDING) || CPSUtils.compareStrings(workflowmap.getMessage(), CPSConstants.DELEGATED)) {
				///**
				 //* only for delegate & pending requests we should display the manual delegate employees list
			
				workflowmap.setDelegateList(workflowMapDAO.requestDelegateList(workflowmap));
			  }
			  }
			  else
			  {
				 message = CPSConstants.FAILED;
			  }
		} catch (Exception e) {
			throw e;
		}
		return workflowmap;
	}
	*/
//role
	public WorkFlowMappingBean getChangedDetails(WorkFlowMappingBean workBeanMap) throws Exception {
		try {
			workBeanMap = workflowMapDAO.getChangedDetails(workBeanMap);
			if (CPSUtils.compareStrings(CPSConstants.DEMAND, workBeanMap.getRequestType())) {
				String projectcode = null;
				String controlNo = null;
				DemandRequestBean drb = workBeanMap.getDemandRequestDetails();
				if (CPSUtils.compareStrings(drb.getApprovedDept(), CPSConstants.MMG)) {
					String id = drb.getDemandNo().split("/")[3].toString();
					InventoryMasterDTO inventory = commonDataDAO.getInventoryHolderDetails(drb.getInventoryNo(), "", "");
					Calendar cl = Calendar.getInstance();
					if (CPSUtils.isNull(drb.getProjectCode()) && CPSUtils.compareStrings(drb.getProjectCode(), "null"))
						projectcode = drb.getProjectCode();
					else
						projectcode = "00";
					controlNo = "CP" + "/" + cl.get(Calendar.YEAR) + "/" + inventory.getDivisionId() + "/" + projectcode + "/" + id;
					if (!CPSUtils.isNullOrEmpty(controlNo))
						workBeanMap.getDemandRequestDetails().setMmgControlNo(controlNo);
					workBeanMap.getDemandRequestDetails().setMmgControlDate(CPSUtils.getCurrentDateWithTime());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return workBeanMap;
	}
	public String getCheckStage(String requestID, String requestType) throws Exception {
		String message = null;
		try {
			message = workflowMapDAO.getCheckStage(requestID, requestType);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String getLastStagePendingCheck(String requestID, String sfID) throws Exception {
		String message = null;
		try {
			message = workflowMapDAO.getLastStagePendingCheck(requestID, sfID);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<KeyValueDTO> cghsDelegateList() throws Exception {
		List<KeyValueDTO> cghsDelegateList;
		try {
			cghsDelegateList = workflowMapDAO.cghsDelegateList();
		} catch (Exception e) {
			throw e;
		}
		return cghsDelegateList;

	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<RequestsDTO> getDashBoardList(WorkFlowMappingBean workBeanMap) throws Exception {
		ArrayList<RequestsDTO> reqList = null;
  
		try {
			reqList = new ArrayList<RequestsDTO>();
			if (CPSUtils.compareStrings(CPSConstants.PENDING, workBeanMap.getType())) {
				reqList = workflowMapDAO.assignedRequests(workBeanMap.getSfid(), "'PENDING'", CPSConstants.ALL, CPSConstants.ALL, workBeanMap.getSelectedRequestType(),
						workBeanMap.getRequestIDValue(), workBeanMap.getFromDate(), workBeanMap.getToDate());
			} else if (CPSUtils.compareStrings(CPSConstants.DELEGATED, workBeanMap.getType())) {
				reqList = workflowMapDAO.assignedRequests(workBeanMap.getSfid(), "'DELEGATED'", CPSConstants.ALL, CPSConstants.ALL, workBeanMap.getSelectedRequestType(), workBeanMap
						.getRequestIDValue(), workBeanMap.getFromDate(), workBeanMap.getToDate());
			} else if (CPSUtils.compareStrings(CPSConstants.COMPLETED, workBeanMap.getType())) {
				reqList = workflowMapDAO.assignedRequests(workBeanMap.getSfid(), "'APPROVED','DECLINED','COMPLETED','SANCTIONED'", CPSConstants.ALL, CPSConstants.ALL, workBeanMap
						.getSelectedRequestType(), workBeanMap.getRequestIDValue(), workBeanMap.getFromDate(), workBeanMap.getToDate());
			} else if (CPSUtils.compareStrings(CPSConstants.ESCALATED, workBeanMap.getType())) {
				reqList = workflowMapDAO.assignedRequests(workBeanMap.getSfid(), "'ESCALATED'", CPSConstants.ALL, CPSConstants.ALL, workBeanMap.getSelectedRequestType(), workBeanMap
						.getRequestIDValue(), workBeanMap.getFromDate(), workBeanMap.getToDate());
			} else if (CPSUtils.compareStrings(CPSConstants.MYREQUEST, workBeanMap.getType())) {
				reqList = workflowMapDAO.myRequests(workBeanMap.getSfid(), CPSConstants.ALL, CPSConstants.ALL, workBeanMap.getSelectedRequestType(), workBeanMap.getRequestIDValue(), workBeanMap
						.getFromDate(), workBeanMap.getToDate());
		   }else if (CPSUtils.compareStrings(CPSConstants.HOLD, workBeanMap.getType())) {
			
			reqList = (ArrayList)workflowMapDAO.myHoldRequests(workBeanMap.getSfid(),workBeanMap.getRequestIDValue(),workBeanMap.getType());
			
	     	}else if (CPSUtils.compareStrings(CPSConstants.TOTALHOLD, workBeanMap.getType())) {
				
				reqList = (ArrayList)workflowMapDAO.totalHoldRequests(workBeanMap.getSfid(),workBeanMap.getRequestIDValue());
				
		     	}
		} catch (Exception e) {
			throw e;
		}
		return reqList;
	}

	public List<AlertMessageDTO> getAlertList1(WorkFlowMappingBean workBeanMap) throws Exception {
		List<AlertMessageDTO> alertList = null;
		try{
			alertList = workflowMapDAO.assignedalertList(workBeanMap.getSfid(),CPSConstants.ALL, CPSConstants.ALL, workBeanMap.getSelectedAlertType(),
			workBeanMap.getFromDate(), workBeanMap.getToDate());

		}catch (Exception e) {
			
		}
		return alertList;
	}
	
	@SuppressWarnings("unchecked")
	public List<RequestWorkDTO> getRequestTypes() throws Exception {
		List<RequestWorkDTO> reqList = null;
		try {
			reqList = commonDataDAO.getMasterData(CPSConstants.REQUESTWORKDTO);
		} catch (Exception e) {
			throw e;
		}
		return reqList;
	}
	@SuppressWarnings("unchecked")
	public List<AlertMessageDTO> getAlertTypeList() throws Exception {
		List<AlertMessageDTO> alertList = null;
		try {
			
			alertList = commonDataDAO.getMasterData(CPSConstants.ALERTMASTERDTO);
		} catch (Exception e) {
			throw e;
		}
		return alertList;
	}
	
	public List<AlertMessageDTO> getAlertList(WorkFlowMappingBean workBeanMap) throws Exception {
		List<AlertMessageDTO> alertList = null;
		try {
			
			alertList = workflowMapDAO.myAlerts(workBeanMap.getSfid(),"","");
		} catch (Exception e) {
			throw e;
		}
		return alertList;
	}

	public String getPresentRequestStage(String requestID, String sfid) throws Exception {
		String stage = null;
		try {
			stage = workflowMapDAO.getPresentRequestStage(requestID, sfid);
		} catch (Exception e) {
			throw e;
		}
		return stage;
	}

	public List<DoPartBean> getDopartNumber(Date doPartDate, String gazType) throws Exception {
		List<DoPartBean> doPartNumber = null;
		try {
			doPartNumber = workflowMapDAO.getDopartNumber(doPartDate, gazType);
		} catch (Exception e) {
			throw e;
		}
		return doPartNumber;
	}

	public AlertMessageDTO getAlertDetails(WorkFlowMappingBean workflowMappingBean) throws Exception {
		try {
			workflowMappingBean.setAlertDetails(workflowMapDAO.getAlertDetails(workflowMappingBean));
		} catch (Exception e) {
			throw e;
		}
		return workflowMappingBean.getAlertDetails();
	}

	public String submitAlertResponse(WorkFlowMappingBean workflowMappingBean) throws Exception {
		try {
			workflowMappingBean.setResult(workflowMapDAO.submitAlertResponse(workflowMappingBean));

			if (workflowMappingBean.getStatus() != 0 && CPSUtils.compareStrings(workflowMappingBean.getAlertTypeID(), CPSConstants.PROMOTIONALERTID)) {
				// Change the promotion status
				workflowMappingBean.setResult(promotionDAO.changeStatus(workflowMappingBean.getReferenceID(), workflowMappingBean.getStatus()));
			}
		} catch (Exception e) {
			throw e;
		}
		return workflowMappingBean.getResult();
	}

	public List<QuarterTypeBean> getQuarterSubTypeList(WorkFlowMappingBean workflowMappingBean) throws Exception {
		try {
			workflowMappingBean.setQuarterSubTypeList(quarterRequestDAO.getQuarterSubTypeList());
		} catch (Exception e) {
			throw e;
		}
		return workflowMappingBean.getQuarterSubTypeList();
	}

	public WorkFlowMappingBean editInstallmentDetails(WorkFlowMappingBean workBeanMap) throws Exception {
		try {
			workBeanMap = workflowMapDAO.editInstallmentDetails(workBeanMap);
		} catch (Exception e) {
			throw e;
		}
		return workBeanMap;
	}

	public QuarterRequestBean getQuarterDetails(WorkFlowMappingBean workBeanMap)throws Exception {
		QuarterRequestBean quarterRequestBean=null;
		try {
			quarterRequestBean=workflowMapDAO.getQuarterDetails(workBeanMap);
		} catch (Exception e) {
			throw e;
		}
		return quarterRequestBean;
	}

	public HQRequestDTO getHQRequestDetails(WorkFlowMappingBean workBeanMap)throws Exception {
		HQRequestDTO hqRequestDTO=null;
		try {
			hqRequestDTO=workflowMapDAO.getHQRequestDetails(workBeanMap);
		} catch (Exception e) {
			throw e;
		}
		return hqRequestDTO;
	}
public List<TutionFeeBean> getTfClaimRequestDetails(int requestId,WorkFlowMappingBean workFlowMappingBean)throws Exception{
	List<TutionFeeBean> keyList=null;
	try{
		keyList=iTutionFeeDAO.getTfClaimRequestDetails(requestId,workFlowMappingBean);
	}catch (Exception e) {
		throw e;
	}
	return keyList;
}
public List<List<TutionFeeClaimDetailsDTO>> getClaimListDetails(int requestId) throws Exception{
	List<List<TutionFeeClaimDetailsDTO>> mainClaimList=null;
	try{
		mainClaimList=iTutionFeeDAO.getClaimListDetais(requestId);
	}catch (Exception e) {
		throw e;
	}
	return mainClaimList;
}
public List<TelephoneBillClaimDetailsDTO> getTelephoneBillClaimListDetails(int requestId)throws Exception{
	List<TelephoneBillClaimDetailsDTO> telephoneMainClaimList=null;
	try{
		telephoneMainClaimList=iTelePhoneBillDAO.getTelephoneBillClaimListDetails(requestId);
	}catch (Exception e) {
		throw e;
	}
	return telephoneMainClaimList;
}
public int getTutionFeeMaxAmountPerOneChild()throws Exception{
	String finalValue="";
	int maxAmountPerOneChild=15000;
	try{
		finalValue=iTutionFeeDAO.getTutionFeeConfigDetails();
		maxAmountPerOneChild=Integer.parseInt(finalValue.split("@")[1]);
	}catch (Exception e) {
		throw e;
	}
	return maxAmountPerOneChild;
}
public List<TadaDaNewDetailsDTO> getDaNewDetails() throws Exception{
	List<TadaDaNewDetailsDTO> list=null;
	try{
		list=iTadaRequestDAO.getDaNewDetailsList();
	} catch (Exception e) {
		throw e;
	}
	return list;
}
public List<TadaTdDaNewFoodDetailsDTO> getFoodDetails(TadaRequestBean tadaRequestBean)throws Exception{
	List<TadaTdDaNewFoodDetailsDTO> list=null;
	try {
		list=iTadaRequestDAO.getFoodDetails(tadaRequestBean);
	} catch (Exception e) {
		throw e;
	}
	return list;
}
public List<TadaTdAccDetDayRepDTO> getAccDetails(TadaRequestBean tadaRequestBean)throws Exception{
	List<TadaTdAccDetDayRepDTO> daNewAccDayRepList=null;
	TadaRequestProcessBean trpb = new TadaRequestProcessBean();
	
	try {
		BeanUtils.copyProperties(trpb, tadaRequestBean);
		
		daNewAccDayRepList = tdDomainObject.submitTdNewDaAccDetails(trpb);
	} catch (Exception e) {
		throw e;
	}
	return daNewAccDayRepList;
}
public List<TadaTdDaNewFoodDetailsDTO> getDayFoodDetails(TadaRequestBean tadaRequestBean)throws Exception{
	List<TadaTdDaNewFoodDetailsDTO> list=null;
	try {
		list=iTadaRequestDAO.getDayFoodDetails(tadaRequestBean);
		
	} catch (Exception e) {
		throw e;
	}
	return list;
}
public List<TadaTdAccDetDayRepDTO> getDayAccDetails(TadaRequestBean tadaRequestBean)throws Exception{
	List<TadaTdAccDetDayRepDTO> list=null;
	try {
		list=iTadaRequestDAO.getDayAccDetails(tadaRequestBean);
		
	} catch (Exception e) {
		throw e;
	}
	return list;
}
public TadaRequestBean getPayDetails(TadaRequestBean tadaRequestBean)throws Exception {
	try{
		tadaRequestBean=iTadaRequestDAO.getPayDetails(tadaRequestBean);
	} catch(Exception e){
		throw e;
	}
	return tadaRequestBean;
}
public TadaRequestBean getDaOnTourDetails(TadaRequestBean tadaRequestBean)throws Exception {                 //This functionality has been provide for daOnTour Detais
	try{
		tadaRequestBean=iTadaRequestDAO.getDaOnTourDetails(tadaRequestBean);
	} catch(Exception e){
		throw e;
	}
	return tadaRequestBean;
}


public String getIpAddress(String requestId) throws Exception{
	String ipAddress=null;
	try{
		ipAddress=iTutionFeeDAO.getIpAddress(requestId);
	}catch (Exception e) {
		throw e;
	}
	return ipAddress;
}
//for MT
public MTRequestDetailsDTO getVehicleApplicantDetails(String requestID) throws Exception {
	MTRequestDetailsDTO mtrDTO = null;
	try{
		mtrDTO = iMechincalTransportDAO.getVehicleApplicantDetails(requestID);
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
	return mtrDTO;
}
/*@SuppressWarnings("unchecked")
public MTVehicleDriverMapDTO getVehicleDriverList(String reqiestId) throws Exception {
	MTVehicleDriverMapDTO vdDto = null;
	try{
		vdDto = iMechincalTransportDAO.getAvailableVehicleDriverList(reqiestId);
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return vdDto;
}*/
/*@SuppressWarnings("unchecked")
public List getAllBusyVehicles(String requestId) throws Exception {
	List<MTVehicleDriverMapDTO> vdList = null;
	try{
		vdList = iMechincalTransportDAO.getAllBusyVehicles(requestId);
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return vdList;
}*/
@SuppressWarnings("unchecked")
public List getVehicleDriverBusyList() throws Exception {
	List<MTVehicleAllocationtDetailsDTO> vdList = null;
	try{
		vdList = iMechincalTransportDAO.getAvailableBusyVehicleDriverList();
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return vdList;
}

public List getVehicleNoList() throws Exception {
	List<MTVehicleDriverMapDTO> vdList = null;
	try{
		//vdList = iMechincalTransportDAO.getVehicleDriverList();
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return vdList;
}

public List getHiredVehicleList() throws Exception {
	List<VehicleDetailsBean> vdList = null;
	try{
		vdList = iMechincalTransportDAO.getAllHiredVehiclesList();
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return vdList;
}
/*public List<ArticleDetailsBean> getArticleDetails(String requestId) throws Exception{
	List<ArticleDetailsBean> artiList = null;
	try{
		artiList = iMechincalTransportDAO.getArticleDetails(requestId);
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
	return artiList;
}*/

/*public List<ArticleDetailsBean> getReturnArticleDetails(String requestId) throws Exception{
	List<ArticleDetailsBean> returnArtiList = null;
	try{
		returnArtiList = iMechincalTransportDAO.getReturnArticleDetails(requestId);
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
	return returnArtiList;
}*/
public List<TelePhoneBillBean> getTelephoneBillClaimRequestDetails(int requestId,WorkFlowMappingBean workFlowMappingBean)throws Exception{
	List<TelePhoneBillBean> keyList=null;
	try{
	keyList=iTelePhoneBillDAO.getTelephoneBillClaimRequestDetails(requestId,workFlowMappingBean);
		
	}catch (Exception e) {
		throw e;
	}
	return keyList;
}
public String manageCghsUploads(WorkFlowMappingBean workBeanMap) throws Exception{
	String message = "";
	try{
		
	//	message =commonDataDAO.manageCghsUploads(workBeanMap);
	}catch (Exception e) {
		throw e;
	}
	return message;
}
public List<TelephoneDesigEligibilityDetailsDTO> teleMaxAmountCondition(WorkFlowMappingBean workBeanMap)throws Exception{
	List<TelephoneDesigEligibilityDetailsDTO> teleMaxAmount = null;
	try{
		teleMaxAmount = workflowMapDAO.teleMaxAmountCondition(workBeanMap);
	}catch (Exception e) {
		throw e;
    }
	return teleMaxAmount;
 }
public String getFinanceAcceptedAmount(WorkFlowMappingBean woBean) throws Exception {
	String message = null;
	try{
		woBean = workflowMapDAO.getfinanceAcceptableAmount(woBean);
	}catch (Exception e) {
		throw e;
	}
	return message;
}

public String getCdaDetails(WorkFlowMappingBean woBean) throws Exception {
	String message = null;
	try{
		woBean = workflowMapDAO.getCdaDetails(woBean);
	}catch (Exception e) {
		throw e;
	}
	return message;
}

public String getRoleInstanceName(WorkFlowMappingBean woBean) throws Exception {
	String message = null;
	try{
		woBean = workflowMapDAO.getRoleInstanceName(woBean);
	}catch (Exception e) {
		throw e;
	}
	return message;
}

public String getApprovedBillNumbers(WorkFlowMappingBean woBean) throws Exception {
	String message = null;
	try{
		woBean = workflowMapDAO.getApprovedBillNumbers(woBean);
	}catch (Exception e) {
		throw e;
	}
	return message;
}



public WorkFlowMappingBean cghsreque(WorkFlowMappingBean workBeanMap) throws Exception {
	try {
		workBeanMap = workflowMapDAO.cghsreque(workBeanMap);
	} catch (Exception e) {
		throw e;
	}
	return workBeanMap;
}
}


