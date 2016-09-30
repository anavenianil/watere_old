package com.callippus.web.leave.dao.request;

import java.util.HashMap;
import java.util.List;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dto.AvailableLeavesDTO;
import com.callippus.web.leave.dto.LeaveExceptionDetailsDTO;

public interface ILeaveRequestDAO {

	public LeaveApplicationBean getAvailableLeaves(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getFamilyMembers(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getAppliedLeaves(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getFamilyImpactDetails(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getHolidays(LeaveApplicationBean leaveBean) throws Exception;

	public List<AddressBean> getAddressDetails(String sfid) throws Exception;

	public LeaveApplicationBean getNoOfDays(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean saveOrUpdateSpellsCount(LeaveApplicationBean leaveBean) throws Exception;

	public List<LeaveExceptionDetailsDTO> getExceptionDetails() throws Exception;

	public List<AvailableLeavesDTO> getLeaveCreditsList(String sfid) throws Exception;

	public LeaveApplicationBean getSpecialCasualList(LeaveApplicationBean leaveBean) throws Exception;

	public HashMap<String, String> getOfficeIDAndPhone(String sfid) throws Exception;

	public LeaveApplicationBean getLeaveDetails(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getConfiguredConvertLeaves(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean checkRequests(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean checkCancelLeaveConstraints(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean checkConvertLeaveConstraints(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getLeaveCardDetails(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getLeaveBalaceDetails(LeaveApplicationBean leaveBean) throws Exception;

	public LeaveApplicationBean getEmployeeDetails(LeaveApplicationBean leaveBean) throws Exception;

	public String getLeaveRequestPkValue(String requestID) throws Exception;

	public LeaveApplicationBean getCancelledLeaves(LeaveApplicationBean leaveAppBean) throws Exception;

	public LeaveApplicationBean getConvertedLeaves(LeaveApplicationBean leaveAppBean) throws Exception;

	public List<DoPartDTO> getDoPartList(LeaveAdministratorBean leaveBean) throws Exception;
	
	public String getYearId(String year) throws Exception;
	
	public List<YearTypeDTO> getYearsList() throws Exception;
	
	public List<YearTypeDTO> getYearsList(String sfid) throws Exception;
	
	public LeaveApplicationBean getRetirementDate(LeaveApplicationBean leaveBean) throws Exception;
	
	public String getRetirementDate(String sfid) throws Exception;
	
	public String getASLJoiningDate(String sfid) throws Exception;
	
	public String checkBalance(LeaveApplicationBean leaveAppBean) throws Exception;
	
	public String leaveImpact(LeaveApplicationBean leaveAppBean) throws Exception;

	public String changeRecordStatus(String requestId, String sfid, String type) throws Exception;
	
	public LeaveRequestBean getLeaveDetails(String requestId) throws Exception;
	
	public String getLeaveFutureBound() throws Exception;
	
	public String getLeavePastBound() throws Exception;
}
