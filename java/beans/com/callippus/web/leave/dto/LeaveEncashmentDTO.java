package com.callippus.web.leave.dto;

public class LeaveEncashmentDTO {

	private int id;
	private LeaveTypeDTO leaveTypeDetails;
	private String noOfDaysInService;
	private String noOfSpellsInService;
	private String noOfSpellsInYear;
	private String minDaysPerSpell;
	private String minLeavesAfterEncash;
	private String maxDaysPerSpell;
	private String leaveTypeID;
	private int availableLeaves;
	private int totalUsedELs;
	
	
	public int getTotalUsedELs() {
		return totalUsedELs;
	}

	public void setTotalUsedELs(int totalUsedELs) {
		this.totalUsedELs = totalUsedELs;
	}

	public int getAvailableLeaves() {
		return availableLeaves;
	}

	public void setAvailableLeaves(int availableLeaves) {
		this.availableLeaves = availableLeaves;
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

	public String getNoOfDaysInService() {
		return noOfDaysInService;
	}

	public void setNoOfDaysInService(String noOfDaysInService) {
		this.noOfDaysInService = noOfDaysInService;
	}

	public String getNoOfSpellsInService() {
		return noOfSpellsInService;
	}

	public void setNoOfSpellsInService(String noOfSpellsInService) {
		this.noOfSpellsInService = noOfSpellsInService;
	}

	public String getNoOfSpellsInYear() {
		return noOfSpellsInYear;
	}

	public void setNoOfSpellsInYear(String noOfSpellsInYear) {
		this.noOfSpellsInYear = noOfSpellsInYear;
	}

	public String getMinDaysPerSpell() {
		return minDaysPerSpell;
	}

	public void setMinDaysPerSpell(String minDaysPerSpell) {
		this.minDaysPerSpell = minDaysPerSpell;
	}

	public String getMinLeavesAfterEncash() {
		return minLeavesAfterEncash;
	}

	public void setMinLeavesAfterEncash(String minLeavesAfterEncash) {
		this.minLeavesAfterEncash = minLeavesAfterEncash;
	}

	public String getMaxDaysPerSpell() {
		return maxDaysPerSpell;
	}

	public void setMaxDaysPerSpell(String maxDaysPerSpell) {
		this.maxDaysPerSpell = maxDaysPerSpell;
	}

	

}
