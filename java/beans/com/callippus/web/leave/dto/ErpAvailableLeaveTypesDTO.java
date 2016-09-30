package com.callippus.web.leave.dto;

public class ErpAvailableLeaveTypesDTO {
	
	private String natureofLeave;
	
	private String leaveCode;
	
	private String applicableGen;
	
	private int status;
	
	private int id;
	
	private String year;
	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getNatureofLeave() {
		return natureofLeave;
	}

	public void setNatureofLeave(String natureofLeave) {
		this.natureofLeave = natureofLeave;
	}

	public String getLeaveCode() {
		return leaveCode;
	}

	public void setLeaveCode(String leaveCode) {
		this.leaveCode = leaveCode;
	}

	public String getApplicableGen() {
		return applicableGen;
	}

	public void setApplicableGen(String applicableGen) {
		this.applicableGen = applicableGen;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	

}
