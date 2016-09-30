package com.callippus.web.beans.higherQualification.dto;

import java.util.Date;

public class HQDetailsDTO {
	private int id;
	private String sfID;
	private Date fromDate;
	private Date toDate;
	private String dischargeOfDuties;
	private int status;
	private String labWork;
	private String course;
	private String result;
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
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
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getDischargeOfDuties() {
		return dischargeOfDuties;
	}
	public void setDischargeOfDuties(String dischargeOfDuties) {
		this.dischargeOfDuties = dischargeOfDuties;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLabWork() {
		return labWork;
	}
	public void setLabWork(String labWork) {
		this.labWork = labWork;
	}

		
}
