package com.callippus.web.cghs.dao.request;

import java.util.List;

import org.json.JSONObject;

import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.request.CghsRequestBean;

public interface ICghsRequestDao {

	public CghsRequestBean getEmployeeDetails(String sfid) throws Exception;

	public List<CghsRequestBean> getFamilyMemberReimbursementList(String sfid) throws Exception;

	public CghsRequestBean getFamilyMemberAdvanceDetails(CghsRequestBean cghsRequest) throws Exception;

	public List<ReferralHospitalDTO> getReferralHospitalList(String prescriptionDate) throws Exception;
	
	
	
	public CghsRequestBean financeReimbursementHome(CghsRequestBean cghsRequest) throws Exception;

	public String saveCdaAmountAdvance(String requestId, String cdaAmount, String sanctionNo, String billNo, String repSig, String dvNo, String dvDate, String accOfficer) throws Exception;

	public String saveCdaAmountReimbursement(String requestId, String cdaAmount, String sanctionNo, String billNo, String repSig, String dvNo, String dvDate, String accOfficer) throws Exception;

	public String saveCdaAmountEmergency(String requestId, String cdaAmount, String sanctionNo, String billNo, String repSig, String dvNo, String dvDate, String accOfficer) throws Exception;

	public CghsRequestBean cdaReimbursementHome(CghsRequestBean cghsRequest)throws Exception;;
	public String saveFinanceAmount(JSONObject subJson ,CghsRequestBean cghsRequest)throws Exception;;
	public String saveCdaAmount(JSONObject subJson ,CghsRequestBean cghsRequest)throws Exception;
	
}
