package com.callippus.web.ltc.dao.approval;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.leave.dto.LeaveEncashmentDTO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.dto.LtcAdvanceRequestDTO;
import com.callippus.web.ltc.dto.LtcApprovalRequestDTO;
import com.callippus.web.ltc.dto.LtcBlockMasterDTO;
import com.callippus.web.ltc.dto.LtcEncashmentDTO;
import com.callippus.web.ltc.dto.LtcMemberDetailsDTO;
import com.callippus.web.ltc.dto.LtcRequestProcessBean;

public interface ILtcApprovalRequestDAO {

	public EmployeeBean getEmployeeDetails(String sfID) throws Exception;

	public List<KeyValueDTO> getLtcLeaveTypeList(String sfID,Date departureDate,Date returnDate) throws Exception;

	public AddressBean getHometownAddress(String sfId) throws Exception;

	public List<FamilyBean> getFamilyMemberDetails(String sfId) throws Exception;

	public List<KeyValueDTO> getLtcBlockYearsList(String sfid,String district,LtcApplicationBean ltcBean) throws Exception;
	
	public List<LtcMemberDetailsDTO> getLtcApprovedDetailsDAO(String sfID) throws Exception;
	
	public LtcApplicationBean getLtcReimbursementDetailsDAO(LtcApplicationBean ltcBean) throws Exception;
	
	public LtcApplicationBean getLtcRefundDetailsDAO(LtcApplicationBean ltcBean) throws Exception;
	
	public List<LeaveEncashmentDTO> getLeaveEncashList() throws Exception;
	
	public LtcRequestProcessBean checkLtcConstraints(LtcRequestProcessBean ltcReqBean)throws Exception;
	
	public LtcRequestProcessBean checkEncashDaysConstraints(LtcRequestProcessBean ltcReqBean)throws Exception;
	
	public LtcAdvanceRequestDTO getLtcApprovalDetails (String referenceRequestID) throws Exception;
	
	public LtcApplicationBean tourSettlementList(LtcApplicationBean ltcBean) throws Exception;
	
	public String saveCdaAmountAdvance(String requestId,String cdaAmount,String sanctionNo,String billNo,String dvNo,String dvDate,String accOfficer)throws Exception;
	
	public String saveCdaAmountReimbursement(String requestId,String cdaAmount,String sanctionNo,String billNo,String dvNo,String dvDate,String accOfficer)throws Exception;
	
	public LtcApplicationBean amendmentDetails(String requestId,String requestType,LtcApplicationBean ltcBean)throws Exception;
	
	public List<FamilyBean> approvedFamilyMember(String referenceRequestID) throws Exception;
	
	public String saveEncashmentAmount(String requestId,String billNo,String sanctionNo,String dvNo,String dvDate) throws Exception;

	public List<LtcBlockMasterDTO> getAdminEntryBlockYearList(LtcApplicationBean ltcBean) throws Exception;

	public String saveRequestDetails(LtcApprovalRequestDTO requestDTO) throws Exception;

	public String saveMemberDetails(LtcApplicationBean ltcBean) throws Exception;

	public List<LtcApprovalRequestDTO> getAdminEntryDetails(LtcApplicationBean ltcBean) throws Exception;

	public LtcApplicationBean getCancleRequestType(LtcApplicationBean ltcBean)throws Exception;

	public LtcApplicationBean getLeaveDetails(LtcApplicationBean ltcBean)throws Exception;

	public String saveCdaAmount(JSONObject subJson,LtcApplicationBean ltcBean)throws Exception;

	public String getLtcBlockYear(String id) throws Exception;
	public LtcApplicationBean cdatourSettlementList(LtcApplicationBean ltcBean) throws Exception;
	public String saveCdaAmountsettlemt(JSONObject subJson,LtcApplicationBean ltcBean)throws Exception;
	public LtcApplicationBean getLtcEncashmentDays(LtcApplicationBean ltcBean)throws Exception;

	
	
	//public List<FamilyBean> getFamilyMemberDetailsLtc(LtcApplicationBean ltcBean) throws Exception;
	
	
}
