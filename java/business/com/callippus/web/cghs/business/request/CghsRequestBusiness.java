package com.callippus.web.cghs.business.request;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.cghs.beans.dto.FamilyMemberDetailsDTO;
import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.dto.RequestTypeDTO;
import com.callippus.web.cghs.beans.request.CghsRequestBean;
import com.callippus.web.cghs.dao.management.ICghsManagementDAO;
import com.callippus.web.cghs.dao.request.ICghsRequestDao;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class CghsRequestBusiness {	
	@Autowired
	private ICghsRequestDao cghsRequestDAO;
	@Autowired
	private ICghsManagementDAO cghsManagementDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	
	
	@SuppressWarnings("unchecked")
	public CghsRequestBean getTreatmentRequestHomeDetails(CghsRequestBean cghsRequest) throws Exception{ 
		try {
			cghsRequest = cghsRequestDAO.getEmployeeDetails(cghsRequest.getSfID());
			cghsRequest.setRequestTypeList(cghsManagementDAO.getList(RequestTypeDTO.class));
			cghsRequest.setTodaysDate(CPSUtils.getCurrentDate());
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	}
	public List<ReferralHospitalDTO> getReferralHospitalList(String prescriptionDate) throws Exception {
		List<ReferralHospitalDTO> hospitalList=null;
		try {
			hospitalList = cghsRequestDAO.getReferralHospitalList(prescriptionDate);
		}catch (Exception e) {
			throw e;
		}
		return hospitalList;
	}
	
	public CghsRequestBean getFamilyMemberReimbursementList (CghsRequestBean cghsRequest) throws Exception{ 
		try {
			cghsRequest.setReimbursementMemberList(cghsRequestDAO.getFamilyMemberReimbursementList(cghsRequest.getSfID()));
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	}

	public CghsRequestBean getFamilyMemberAdvanceDetails(CghsRequestBean cghsRequest) throws Exception{ 
		try {
			cghsRequest.setCghsRequestBean(cghsRequestDAO.getEmployeeDetails(cghsRequest.getSfID()));
			cghsRequest = cghsRequestDAO.getFamilyMemberAdvanceDetails(cghsRequest);
			
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	}
	public CghsRequestBean financeReimbursementHome(CghsRequestBean cghsRequest) throws Exception{ 
		try {
			cghsRequest = cghsRequestDAO.financeReimbursementHome(cghsRequest);
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	}
	public CghsRequestBean cdaReimbursementHome(CghsRequestBean cghsRequest) throws Exception{ 
		try {
			cghsRequest = cghsRequestDAO.cdaReimbursementHome(cghsRequest);
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	}
	
	public CghsRequestBean saveCdaAmount(CghsRequestBean cghsRequest) throws Exception{ 
		try {
			 JSONObject mainJson = new JSONObject(cghsRequest.getJsonValues());
		     if(CPSUtils.compareStrings(cghsRequest.getParam(),"cdaManage"))
		     {
			   /*if(CPSUtils.compareStrings(cghsRequest.getType(),"advance")){
				   for(int i=0;i<mainJson.length();i++) {
					  JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					  cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountAdvance(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
					  //cghsRequest.setResult(cghsRequestDAO.saveFinanceAmount(subJson, cghsRequest));
				   }
			    }else if(CPSUtils.compareStrings(cghsRequest.getType(),"settlement") 
					||CPSUtils.compareStrings(cghsRequest.getType(),"reimbursement")
					||CPSUtils.compareStrings(cghsRequest.getType(),"nonCghsReim")){
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountReimbursement(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
			     	
				//cghsRequest.setResult(cghsRequestDAO.saveFinanceAmount(subJson, cghsRequest));
				}
			   }else if(CPSUtils.compareStrings(cghsRequest.getType(),"emergency")){
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountEmergency(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
					//cghsRequest.setResult(cghsRequestDAO.saveFinanceAmount(subJson, cghsRequest));  
				}
			   }*/
		    	 if(!CPSUtils.isNullOrEmpty(cghsRequest.getType())){
		    		 for(int i=0;i<mainJson.length();i++) {
							JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
		    		 cghsRequest.setMessage(cghsRequestDAO.saveCdaAmount(subJson,cghsRequest));	
		    		 }			  
		    	 }
			   if(CPSUtils.compareStrings(cghsRequest.getMessage(), CPSConstants.SUCCESS)) {
				  cghsRequest = cghsRequestDAO.cdaReimbursementHome(cghsRequest);
			  }
		  }
		   else
		   {        if(CPSUtils.compareStrings(cghsRequest.getType(),"advance")){
			         	for(int i=0;i<mainJson.length();i++) {
				     	JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				     	/*if(subJson.has("cdaAmount") && subJson.has("dvNo") && subJson.has("dvDate"))
				     	cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountAdvance(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
				     	else
				     		cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountAdvance(subJson.get("requestId").toString(),"",subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),"","",subJson.get("accOfficer").toString()));*/
			         		cghsRequest.setMessage(cghsRequestDAO.saveFinanceAmount(subJson, cghsRequest));
				   }
			     }else if(CPSUtils.compareStrings(cghsRequest.getType(),"settlement") 
					||CPSUtils.compareStrings(cghsRequest.getType(),"reimbursement")
					||CPSUtils.compareStrings(cghsRequest.getType(),"nonCghsReim")){
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					cghsRequest.setMessage(cghsRequestDAO.saveFinanceAmount(subJson, cghsRequest));
					/*if(subJson.has("cdaAmount") && subJson.has("dvNo") &&subJson.has("dvDate"))
						cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountReimbursement(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
					else
						cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountReimbursement(subJson.get("requestId").toString(),"",subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),"","",subJson.get("accOfficer").toString()));*/				}
		       }else if(CPSUtils.compareStrings(cghsRequest.getType(),"emergency")){
			         	for(int i=0;i<mainJson.length();i++) {
					 JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					/* if(subJson.has("cdaAmount") && subJson.has("dvNo") &&subJson.has("dvDate"))
					 cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountEmergency(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
					 else
						 cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountEmergency(subJson.get("requestId").toString(),"",subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),"","",subJson.get("accOfficer").toString()));*/
					 cghsRequest.setMessage(cghsRequestDAO.saveFinanceAmount(subJson, cghsRequest));  		 
				}
			}
			if(CPSUtils.compareStrings(cghsRequest.getMessage(), CPSConstants.SUCCESS)) {
		  		cghsRequest = cghsRequestDAO.financeReimbursementHome(cghsRequest);
		    	}
		   }
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	}
         /*public CghsRequestBean saveCdaAmount(CghsRequestBean cghsRequest) throws Exception{ 
		try {
			JSONObject mainJson = new JSONObject(cghsRequest.getJsonValues());
			if(CPSUtils.compareStrings(cghsRequest.getType(),"advance")){
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountAdvance(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
				}
			}else if(CPSUtils.compareStrings(cghsRequest.getType(),"settlement") 
					||CPSUtils.compareStrings(cghsRequest.getType(),"reimbursement")
					||CPSUtils.compareStrings(cghsRequest.getType(),"nonCghsReim")){
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountReimbursement(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
				}
			}else if(CPSUtils.compareStrings(cghsRequest.getType(),"emergency")){
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					cghsRequest.setMessage(cghsRequestDAO.saveCdaAmountEmergency(subJson.get("requestId").toString(),subJson.get("cdaAmount").toString(),subJson.get("sanctionNo").toString(),subJson.get("billNo").toString(),subJson.get("repSig").toString(),subJson.get("dvNo").toString(),subJson.get("dvDate").toString(),subJson.get("accOfficer").toString()));
				}
			}
			if(CPSUtils.compareStrings(cghsRequest.getMessage(), CPSConstants.SUCCESS)) {
				cghsRequest = cghsRequestDAO.financeReimbursementHome(cghsRequest);
			}
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	  }
      */
	public CghsRequestBean getDependentList(CghsRequestBean cghsRequest) throws Exception {
		try {
			cghsRequest.setMessage(commonDataDAO.checkEmployee(cghsRequest.getOfflineSFID()));
			if(CPSUtils.compareStrings(cghsRequest.getMessage(),CPSConstants.SUCCESS)) {
				cghsRequest = cghsRequestDAO.getEmployeeDetails(cghsRequest.getOfflineSFID());	
			}
		}catch (Exception e) {
			throw e;
		}
		return cghsRequest;
	}
}
