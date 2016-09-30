package com.callippus.web.leave.dao.management;

import java.util.List;

import net.sf.json.JSON;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.dto.LeaveDurationDTO;
import com.callippus.web.leave.dto.LeaveExceptionDetailsDTO;
import com.callippus.web.leave.dto.LeaveRelationsDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

public interface ILeaveManagementDAO {

	public List<LeaveTypeDTO> getLeaveTypeMasterDetails(LeaveManagementBean leaveBean) throws Exception;

	public String InsertLeaveGeneralDetailsDAO(LeaveManagementBean leaveBean) throws Exception;

	public String InsertLeaveOtherDetailsDAO(LeaveManagementBean leaveBean) throws Exception;

	public String InsertLeaveRulesDetailsDAO(LeaveManagementBean leaveBean) throws Exception;

	public JSON getExceptionMasterDetails() throws Exception;

	public List<LeaveDurationDTO> getLeaveDurationMasterDetailsDAO(LeaveManagementBean leaveBean) throws Exception;

	public LeaveManagementBean getLeaveTypeDetailsDAO(LeaveManagementBean leaveBean) throws Exception;

	public String getLeaveEncashmentIDDetails(String leaveTypeID) throws Exception;

	public String getLeaveFamilyIDDetails(String leaveTypeID) throws Exception;

	public void deleteExceptionDetails(String leaveTypeID, int subtype) throws Exception;

	public void deleteELconversionDetails(String leaveTypeID) throws Exception;

	public List<KeyValueDTO> getSpecialCasualLeaveTypes(String categoryType) throws Exception;

	public SpecialCasualLeaveDTO getSpecialCasualLeaveDetails(String leavesubtype) throws Exception;

	public List<LeaveExceptionDetailsDTO> getExceptionDetails(String leaveTypeId, String leaveSubType) throws Exception;

	public String InsertSpecialCasualLeaveDetails(SpecialCasualLeaveDTO specialCasualLeaveDTO, List<LeaveExceptionDetailsDTO> exceptionlist) throws Exception;

	public String insertLeaveRelationMappingDetails(List<LeaveRelationsDTO> exceptionlist) throws Exception;
	
	public void deleteLeaveMappingDetails() throws Exception;
	
	public List<KeyValueDTO> getLeaveRelationMappingDetails() throws Exception; 
	
	public String getLeaveTypeIDDetails(String leaveTypeID) throws Exception;
	
	public String InsertSpecialCasualLeaveExceptionDetails(SpecialCasualLeaveDTO specialCasualLeaveDTO, List<LeaveExceptionDetailsDTO> exceptionlist) throws Exception;
	
	public String getSpCLID(String leavesubtype) throws Exception;
	public String getSpCLID1(String leavesubtype,String fromdate,String todate) throws Exception;
}
