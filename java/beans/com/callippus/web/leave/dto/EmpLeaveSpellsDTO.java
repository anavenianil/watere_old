package com.callippus.web.leave.dto;

public class EmpLeaveSpellsDTO {
	private int id;
	private String sfID;
	private int leaveTypeID;
	private int yearSpellsCount;
	private int serviceSpellsCount;
	private int ltcEncashmentDays;
	private int leavesCount;

	public int getLeavesCount() {
		return leavesCount;
	}

	public void setLeavesCount(int leavesCount) {
		this.leavesCount = leavesCount;
	}

	public int getLtcEncashmentDays() {
		return ltcEncashmentDays;
	}

	public void setLtcEncashmentDays(int ltcEncashmentDays) {
		this.ltcEncashmentDays = ltcEncashmentDays;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public int getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(int leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public int getYearSpellsCount() {
		return yearSpellsCount;
	}

	public void setYearSpellsCount(int yearSpellsCount) {
		this.yearSpellsCount = yearSpellsCount;
	}

	public int getServiceSpellsCount() {
		return serviceSpellsCount;
	}

	public void setServiceSpellsCount(int serviceSpellsCount) {
		this.serviceSpellsCount = serviceSpellsCount;
	}

}
