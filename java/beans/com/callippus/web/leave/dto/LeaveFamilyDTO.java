package com.callippus.web.leave.dto;

public class LeaveFamilyDTO {
	private int id;
	private LeaveTypeDTO leaveTypeDetails;
	private String noOfChildren;
	private String childAge;
	private String phChildAge;
	private String leaveTypeID;

	public String getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(String noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public String getChildAge() {
		return childAge;
	}

	public void setChildAge(String childAge) {
		this.childAge = childAge;
	}

	public String getPhChildAge() {
		return phChildAge;
	}

	public void setPhChildAge(String phChildAge) {
		this.phChildAge = phChildAge;
	}

	public String getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(String leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

}
