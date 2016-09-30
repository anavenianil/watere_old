package com.callippus.web.ltc.business.approval;


import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.requestprocess.LtcRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.dao.approval.ILtcApprovalRequestDAO;
import com.callippus.web.ltc.dto.LtcApprovalRequestDTO;
import com.callippus.web.ltc.dto.LtcEncashmentDTO;
import com.callippus.web.ltc.dto.LtcMemberDetailsDTO;
import com.callippus.web.ltc.dto.LtcRequestProcessBean;

@Service
public class LtcApprovalRequestBusiness {
	@Autowired
	private ILtcApprovalRequestDAO ltcApprovalRequestDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private LtcRequestProcess ltcRequestProcess;

	@SuppressWarnings("unchecked")
	public LtcApplicationBean getLTCDetails(LtcApplicationBean ltcBean) throws Exception {
		try {
			ltcBean.setEmpBean(ltcApprovalRequestDAO.getEmployeeDetails(ltcBean.getSfID()));
			ltcBean.setHomeTownAddress(ltcApprovalRequestDAO.getHometownAddress(ltcBean.getSfID()));
			if(!CPSUtils.isNullOrEmpty(ltcBean.getHomeTownAddress())){
				if(!CPSUtils.isNullOrEmpty(ltcBean.getEmpBean())){
				if(!CPSUtils.isNullOrEmpty(ltcBean.getHomeTownAddress().getNearestAirport())){	
					if(!CPSUtils.isNullOrEmpty(ltcBean.getHomeTownAddress().getNearestRyStation())){
				ltcBean.getHomeTownAddress().setNearestRyStation(ltcBean.getHomeTownAddress().getNearestRyStation() +"/" + ltcBean.getHomeTownAddress().getNearestAirport());}
				}
				//ltcBean.setLtcBlockYearList(ltcApprovalRequestDAO.getLtcBlockYearsList(ltcBean.getSfID(),ltcBean.getHomeTownAddress().getDistrict(),ltcBean));
				ltcBean.setLtcBlockYearList(ltcApprovalRequestDAO.getLtcBlockYearsList(ltcBean.getSfID(), ltcBean.getHomeTownAddress().getDistrict(), ltcBean));
				ltcBean.setFamilyMemberDetails(ltcApprovalRequestDAO.getFamilyMemberDetails(ltcBean.getSfID()));
				ltcBean.setLtcTypeDetails(commonDataDAO.getMasterData(CPSConstants.LTCTYPEMASTERDTO));
				ltcBean.setLeaveEncasList(ltcApprovalRequestDAO.getLeaveEncashList());
				}
				else{
					ltcBean.setResult("BASIC PAY GRADE PAY DETAILS NOT ENTER");
				}
				
			}else{
				ltcBean.setResult("LTCHOMETOWNNULL");
				//ltcBean.setResult("BASIC PAY GRADE PAY DETAILS NOT ENTER");
			}
			
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}
	public LtcRequestProcessBean ltcRulesChecking(LtcRequestProcessBean ltcReqBean)throws Exception{
		try{
			
			if(CPSUtils.compareStrings(ltcReqBean.getTypeValue(),"ltcApprovalAmendment") || CPSUtils.compareStrings(ltcReqBean.getTypeValue(),"ltcAdvanceAmendment")) {
				/**
				 * User amending already applied LTC Request then cancel already applied LTC Request
				 */
				RequestBean rb = new RequestBean();
				rb.setRequestID(ltcReqBean.getId());
				if(CPSUtils.compareStrings(ltcReqBean.getTypeValue(), "ltcApprovalAmendment")) {
					rb.setRequestType("ltcApproval");
				}else {
					rb.setRequestType("ltcAdvance");
				}
				rb.setStatus(9);
				rb.setSfID(ltcReqBean.getSfID());
				rb.setIpAddress(ltcReqBean.getIpAddress());
				rb.setRemarks(CPSConstants.LTC_REQUEST_CANCEL_REASON);
				rb = ltcRequestProcess.cancelLtcRequest(rb);
			}
			/**
			 * Check whether already applied on this ltc_block_year_id (one or two year blocks) for individual
			 * If the request type is all india trip then we should check whether already applied in this four year block or not for individual
			 */
			ltcReqBean = ltcApprovalRequestDAO.checkLtcConstraints(ltcReqBean);
			/**
			 * If user wants to encash leave days then check constrains for leave encashment
			 */
			if(CPSUtils.compareStrings(CPSConstants.SUCCESS, ltcReqBean.getResult()) && !CPSUtils.isNullOrEmpty(ltcReqBean.getEncashmentDays()) && Float.parseFloat(ltcReqBean.getEncashmentDays()) >0){
				ltcReqBean=ltcApprovalRequestDAO.checkEncashDaysConstraints(ltcReqBean);
			}
		}catch (Exception e) {
			throw e;
		}
		return ltcReqBean;
	}

	public List<LtcMemberDetailsDTO> getLtcApprovedDetails(String sfID) throws Exception {
		List<LtcMemberDetailsDTO> list=null;
		try {
			list=ltcApprovalRequestDAO.getLtcApprovedDetailsDAO(sfID);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public LtcApplicationBean getLtcReimbursementDetails(LtcApplicationBean ltcBean) throws Exception {
		try {			
			ltcBean=ltcApprovalRequestDAO.getLtcReimbursementDetailsDAO(ltcBean);
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}

	
	public LtcApplicationBean getLtcRefundDetails(LtcApplicationBean ltcBean) throws Exception {
		try {
			ltcBean=ltcApprovalRequestDAO.getLtcRefundDetailsDAO(ltcBean);
			ltcBean=ltcApprovalRequestDAO.getCancleRequestType(ltcBean);
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;	
	}
	public LtcApplicationBean getLtcApprovalDetails(LtcApplicationBean ltcBean) throws Exception {
		try {
			ltcBean.setLtcAdvanceRequestDTO(ltcApprovalRequestDAO.getLtcApprovalDetails(ltcBean.getId()));
			ltcBean.setFamilyMemberDetails(ltcApprovalRequestDAO.approvedFamilyMember(ltcBean.getId()));
		//Getting the date of join
			ltcBean.setEmpBean(ltcApprovalRequestDAO.getEmployeeDetails(ltcBean.getSfID()));
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}
	public LtcApplicationBean tourSettlementList(LtcApplicationBean ltcBean) throws Exception {
		try {
			ltcBean = ltcApprovalRequestDAO.tourSettlementList(ltcBean);
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}
	public LtcApplicationBean cdatourSettlementList(LtcApplicationBean ltcBean) throws Exception {
		try {
			ltcBean = ltcApprovalRequestDAO.cdatourSettlementList(ltcBean);
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}
	
	
	public LtcApplicationBean saveCdaAmount(LtcApplicationBean ltcBean) throws Exception{ 
		try {
			JSONObject mainJson = new JSONObject(ltcBean.getJsonValues());
			for(int i=0;i<mainJson.length();i++) {
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				ltcBean.setResult(ltcApprovalRequestDAO.saveCdaAmount(subJson,ltcBean));
			}
			
			
			
			
			
			
			
			
			
			
			
			
//			if(CPSUtils.compareStrings(ltcBean.getType(),"advance")){
//				for(int i=0;i<mainJson.length();i++) {
//					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
//					ltcBean.setResult(ltcApprovalRequestDAO.saveCdaAmountAdvance(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
//				}
//			}else 
//			if(CPSUtils.compareStrings(ltcBean.getType(),"settlement") || CPSUtils.compareStrings(ltcBean.getType(),"reimbursement")){
//				for(int i=0;i<mainJson.length();i++) {
//					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
//					ltcBean.setResult(ltcApprovalRequestDAO.saveCdaAmountReimbursement(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
//				}
//			}if(CPSUtils.compareStrings(ltcBean.getType(),"encashment")){
//				for(int i=0;i<mainJson.length();i++) {
//					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
//					ltcBean.setResult(ltcApprovalRequestDAO.saveEncashmentAmount(subJson.get("requestId").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString()));
//				}
//			}
			if(CPSUtils.compareStrings(ltcBean.getResult(), CPSConstants.SUCCESS)) {
				ltcBean = ltcApprovalRequestDAO.tourSettlementList(ltcBean);
			}
		}catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}

	
	public LtcApplicationBean saveCdaAmountsettlemt(LtcApplicationBean ltcBean) throws Exception{ 
		try {
			JSONObject mainJson = new JSONObject(ltcBean.getJsonValues());
			for(int i=0;i<mainJson.length();i++) {
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				ltcBean.setResult(ltcApprovalRequestDAO.saveCdaAmountsettlemt(subJson,ltcBean));
			}
			if(CPSUtils.compareStrings(ltcBean.getResult(), CPSConstants.SUCCESS)) {
				ltcBean = ltcApprovalRequestDAO.cdatourSettlementList(ltcBean);
			}
		}catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}
	
	public LtcApplicationBean amendmentDetails(String requestId,String requestType,String AmendmentBlockYear,LtcApplicationBean ltcBean)throws Exception{
		LtcApplicationBean ltcAppBean=null;
		try {
			ltcAppBean = ltcApprovalRequestDAO.amendmentDetails(requestId,requestType,ltcBean);
			ltcAppBean.setLtcAmendmentBlockYear(AmendmentBlockYear);
			getLTCDetails(ltcAppBean);
		}catch (Exception e) {
			throw e;
		}
		return ltcAppBean;
	}
	public List<KeyValueDTO> getLtcLeaveTypeList(String sfid,Date departureDate,Date returnDate)throws Exception{
		try {
			return ltcApprovalRequestDAO.getLtcLeaveTypeList(sfid,departureDate,returnDate);
		}catch (Exception e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public LtcApplicationBean getLtcAdminEntryHome(LtcApplicationBean ltcBean) throws Exception {
		try {
			ltcBean.setLtcTypeDetails(commonDataDAO.getMasterData(CPSConstants.LTCTYPEMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}

	public LtcApplicationBean retrieveFamilyMembersList(LtcApplicationBean ltcBean) throws Exception {
		try {
			ltcBean.setResult(commonDataDAO.checkEmployee(ltcBean.getRefSfID()));
			if (CPSUtils.compareStrings(ltcBean.getResult(), CPSConstants.SUCCESS)) {
				ltcBean.setFamilyMemberDetails(ltcApprovalRequestDAO.getFamilyMemberDetails(ltcBean.getRefSfID()));
				ltcBean.setLtcBlockDetails((ltcApprovalRequestDAO.getAdminEntryBlockYearList(ltcBean)));
			}
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}

	public String manageAdminEntryDetails(LtcApplicationBean ltcBean) throws Exception {
		String requestId = null;
		String remarks="";
		String encashLeaveTypeId="1";
		try {
			LtcApprovalRequestDTO requestDTO = new LtcApprovalRequestDTO();
				LtcRequestProcessBean ltcReqBean=new LtcRequestProcessBean();
				BeanUtils.copyProperties(ltcBean,ltcReqBean);
				ltcReqBean=ltcApprovalRequestDAO.checkLtcConstraints(ltcReqBean);
				if(CPSUtils.compareStrings(CPSConstants.SUCCESS,ltcReqBean.getResult())){
			requestId = ltcRequestProcess.generateUniqueID(CPSConstants.REQUEST);
			EmployeeBean employeeBean = ltcApprovalRequestDAO.getEmployeeDetails(ltcBean.getRefSfID());
			
			requestDTO.setEncashmentDays(ltcBean.getEncashmentDays());		
			requestDTO.setSfID(ltcBean.getRefSfID());
			requestDTO.setRequestId(requestId);
			requestDTO.setDesignationId(employeeBean.getDesignationId());
			requestDTO.setDepartmentId(employeeBean.getOfficeId());
			requestDTO.setPhoneNum(employeeBean.getPersonalNumber());
			requestDTO.setEncashTypeId(encashLeaveTypeId);
			requestDTO.setLtcTypeId(ltcBean.getLtcTypeId());
            requestDTO.setLtcBlockYearId(ltcBean.getLtcBlockYearId());
			requestDTO.setPlaceOfVisit(ltcBean.getPlaceOfVisit());
			requestDTO.setNearestRlyStation(ltcBean.getNearestRlyStation());
			requestDTO.setDepartureDate(ltcBean.getDepartureDate());
			requestDTO.setReturnDate(ltcBean.getReturnDate());
			requestDTO.setCreatedBy(ltcBean.getSfID());
			requestDTO.setCreationDate(CPSUtils.getCurrentDate());
			requestDTO.setIpAddress(ltcBean.getIpAddress());
			requestDTO.setLtcRequestType("LTC OFFLINE ENTRY");
			requestDTO.setStatus(8);
			ltcBean.setRequestIDs(requestId);
			ltcBean.setResult(ltcApprovalRequestDAO.saveRequestDetails(requestDTO));
			if(CPSUtils.compareStrings(ltcBean.getResult(),CPSConstants.SUCCESS)){
			ltcBean.setResult(ltcApprovalRequestDAO.saveMemberDetails(ltcBean));
			if(!CPSUtils.isNullOrEmpty(ltcBean.getEncashmentDays())){
				ltcBean.setMessage(ltcRequestProcess.updateLtcLeaveEncashDays(ltcBean.getRefSfID(),encashLeaveTypeId,"-"+ltcBean.getEncashmentDays(), remarks));								
			}
			}						
			}else{
				ltcBean.setRemarks(ltcReqBean.getRemarks());
				return ltcReqBean.getResult();
				}
		} catch (Exception e) {
			throw e;
		}
		return ltcBean.getResult();
	}

	public List<LtcApprovalRequestDTO> getltcAdminEntryList(LtcApplicationBean ltcBean) throws Exception {
		try{
		ltcBean.setAdminEntryDetailsList(ltcApprovalRequestDAO.getAdminEntryDetails(ltcBean));
		}catch(Exception e){
			throw e;
		}return ltcBean.getAdminEntryDetailsList();
	}
	public LtcApplicationBean getLeaveDetails(LtcApplicationBean ltcBean) throws Exception {
		try{
			ltcBean = ltcApprovalRequestDAO.getLeaveDetails(ltcBean);
			}catch(Exception e){
				throw e;
			}return ltcBean;
		}
	public String getLtcBlockYearId(String id) throws Exception{
		String ltcBlockYearId=null;
		try{
			ltcBlockYearId=ltcApprovalRequestDAO.getLtcBlockYear(id);
		}catch (Exception e) {
			throw e;
		}
		return ltcBlockYearId;
	}
	
	
	public LtcApplicationBean  getLtcencashmentDays(LtcApplicationBean ltcBean)throws Exception{
		List<LtcEncashmentDTO> list = null;
		try{
			ltcBean = ltcApprovalRequestDAO.getLtcEncashmentDays(ltcBean);
			//ltcBean =ltcBean.setLtcEncashmentList(ltcApprovalRequestDAO.getLtcEncashmentDays(ltcBean);
		}catch(Exception e){
			
		}
		
		return ltcBean;
		
	}
/*public List<FamilyBean> getFamilyMemberDetailsLtc(LtcApplicationBean ltcBean)throws Exception {
		
		List<FamilyBean> list = null;
		try{
			list = ltcApprovalRequestDAO.getFamilyMemberDetailsLtc(ltcBean);
			}catch(Exception e){
				throw e;
			}return list;
	
}*/
	
}
