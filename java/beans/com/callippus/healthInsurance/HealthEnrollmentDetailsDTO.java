package com.callippus.healthInsurance;

import java.util.Date;
import java.util.List;

public class HealthEnrollmentDetailsDTO {
	private String sfid;  
	private String fullName;  
	private String designation;
	private Date joiningDate;
	private int status;
	private String jsonValue;
	private String requestId;
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public Date getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private Date fromDate;
	private Date toDate;
	private String type;
	private String param;
	private String message;
	private List<HealthEnrollmentDetailsDTO> healthEnrollmentDetails;
	public List<HealthEnrollmentDetailsDTO> getHealthEnrollmentDetails() {
		return healthEnrollmentDetails;
	}
	public void setHealthEnrollmentDetails(
			List<HealthEnrollmentDetailsDTO> healthEnrollmentDetails) {
		this.healthEnrollmentDetails = healthEnrollmentDetails;
	}
	
}
