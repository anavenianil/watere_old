package com.callippus.web.leave.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LeaveExceptionDetailsDTO extends CommonDTO {
	private ExceptionTypeMasterDTO exceptionTypeDetails;
	private String exceptionTypeId;
	private String leaveTypeID;
	private LeaveTypeDTO leaveTypeDetails;
	private SpecialCasualLeaveDTO specialLeaveDetails;
	private String leaveSubTypeID;
	
	
	public String getLeaveSubTypeID() {
		return leaveSubTypeID;
	}

	public void setLeaveSubTypeID(String leaveSubTypeID) {
		this.leaveSubTypeID = leaveSubTypeID;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

	public SpecialCasualLeaveDTO getSpecialLeaveDetails() {
		return specialLeaveDetails;
	}

	public void setSpecialLeaveDetails(SpecialCasualLeaveDTO specialLeaveDetails) {
		this.specialLeaveDetails = specialLeaveDetails;
	}

	public String getExceptionTypeId() {
		return exceptionTypeId;
	}

	public void setExceptionTypeId(String exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}

	public String getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(String leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public ExceptionTypeMasterDTO getExceptionTypeDetails() {
		return exceptionTypeDetails;
	}

	public void setExceptionTypeDetails(ExceptionTypeMasterDTO exceptionTypeDetails) {
		this.exceptionTypeDetails = exceptionTypeDetails;
	}

}
