package com.callippus.web.beans.higherQualification.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class HQRequestDTO extends CommonDTO{
	private Date fromDate;
	private Date toDate;
	private String sfID;
	private int designation;
	private String course;
	private String labWorkFlag;
	private String religionFlag;
	private String dischargeOfDutiesFlag;
	private String requestID;
	private String ipAddress;
	private String requestTypeID;
	private String result;
	private String desigID;
	
	
	
	public String getDesigID() {
		return desigID;
	}
	public void setDesigID(String desigID) {
		this.desigID = desigID;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRequestTypeID() {
		return requestTypeID;
	}
	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
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
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int designation) {
		this.designation = designation;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getLabWorkFlag() {
		return labWorkFlag;
	}
	public void setLabWorkFlag(String labWorkFlag) {
		this.labWorkFlag = labWorkFlag;
	}
	public String getReligionFlag() {
		return religionFlag;
	}
	public void setReligionFlag(String religionFlag) {
		this.religionFlag = religionFlag;
	}
	public String getDischargeOfDutiesFlag() {
		return dischargeOfDutiesFlag;
	}
	public void setDischargeOfDutiesFlag(String dischargeOfDutiesFlag) {
		this.dischargeOfDutiesFlag = dischargeOfDutiesFlag;
	}
	
	
}
