package com.callippus.web.leave.dto;

import com.callippus.web.beans.dto.YearTypeDTO;

public class LeaveYearlyBalanceDTO {
	private int id;
	private int leaveTypeID;
	private int status;
	private int yearID;
	private float balance;
	private YearTypeDTO yearDetails;
	private LeaveTypeDTO leaveTypeDetails;
	private String sfID;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public YearTypeDTO getYearDetails() {
		return yearDetails;
	}

	public void setYearDetails(YearTypeDTO yearDetails) {
		this.yearDetails = yearDetails;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(int leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}
