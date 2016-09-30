package com.callippus.web.leave.dto;

import com.callippus.web.leave.beans.management.LeaveManagementBean;

public class AvailableLeavesDTO {
	private int id;
	private String sfID;
	private float availableLeaves;
	private LeaveTypeDTO leaveTypeDetails;
	private int leaveTypeID;
	private String leaveType;
	private String leaves;
	private float mapDays;
	private float leaveCredits;
	private String noOfMonths;
	private String durationType;
	private String presentLeaves;
	private String toDate;
	private String fromDate;
	private int yearID;
	
	private LeaveManagementBean leaveDetails;

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPresentLeaves() {
		return presentLeaves;
	}

	public void setPresentLeaves(String presentLeaves) {
		this.presentLeaves = presentLeaves;
	}

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public String getNoOfMonths() {
		return noOfMonths;
	}

	public void setNoOfMonths(String noOfMonths) {
		this.noOfMonths = noOfMonths;
	}

	public float getLeaveCredits() {
		return leaveCredits;
	}

	public void setLeaveCredits(float leaveCredits) {
		this.leaveCredits = leaveCredits;
	}

	public float getMapDays() {
		return mapDays;
	}

	public void setMapDays(float mapDays) {
		this.mapDays = mapDays;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaves() {
		return leaves;
	}

	public void setLeaves(String leaves) {
		this.leaves = leaves;
	}

	public int getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(int leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAvailableLeaves() {
		return availableLeaves;
	}

	public void setAvailableLeaves(float availableLeaves) {
		this.availableLeaves = availableLeaves;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public void setLeaveDetails(LeaveManagementBean leaveDetails) {
		this.leaveDetails = leaveDetails;
	}

	public LeaveManagementBean getLeaveDetails() {
		return leaveDetails;
	}

}
