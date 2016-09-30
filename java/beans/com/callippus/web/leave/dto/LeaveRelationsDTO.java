package com.callippus.web.leave.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LeaveRelationsDTO extends CommonDTO {

	private LeaveTypeDTO leaveTypeDetails1;
	private LeaveTypeDTO leaveTypeDetails2;
	private String leaveTypeID1;
	private String leaveTypeID2;

	public String getLeaveTypeID1() {
		return leaveTypeID1;
	}

	public void setLeaveTypeID1(String leaveTypeID1) {
		this.leaveTypeID1 = leaveTypeID1;
	}

	public String getLeaveTypeID2() {
		return leaveTypeID2;
	}

	public void setLeaveTypeID2(String leaveTypeID2) {
		this.leaveTypeID2 = leaveTypeID2;
	}

	public LeaveTypeDTO getLeaveTypeDetails1() {
		return leaveTypeDetails1;
	}

	public void setLeaveTypeDetails1(LeaveTypeDTO leaveTypeDetails1) {
		this.leaveTypeDetails1 = leaveTypeDetails1;
	}

	public LeaveTypeDTO getLeaveTypeDetails2() {
		return leaveTypeDetails2;
	}

	public void setLeaveTypeDetails2(LeaveTypeDTO leaveTypeDetails2) {
		this.leaveTypeDetails2 = leaveTypeDetails2;
	}

}
