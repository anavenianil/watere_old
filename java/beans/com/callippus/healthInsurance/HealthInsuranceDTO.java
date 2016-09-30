package com.callippus.healthInsurance;

import java.sql.Date;
import java.util.List;

//import com.callippus.web.ltc.dto.LtcEncashmentDTO;

public class HealthInsuranceDTO {

	private String sfid;  
	private String fullName;  
	private String designation;
	private Date joiningDate;
	private int status;
	private String jsonValue;
	private String param;
	private String requestId; 
	private String requestID;
	private String sfID;
	private String sfId;
	private String message;
	private Date fromDate;
	private Date toDate;
	private String type;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getSfId() {
		return sfId;
	}
	public void setSfId(String sfId) {
		this.sfId = sfId;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

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
    private List<HealthInsuranceDTO> healthInsuranceDetails;


	public List<HealthInsuranceDTO> getHealthInsuranceDetails() {
		return healthInsuranceDetails;
	}
	public void setHealthInsuranceDetails(
			List<HealthInsuranceDTO> healthInsuranceDetails) {
		this.healthInsuranceDetails = healthInsuranceDetails;
	}
}
