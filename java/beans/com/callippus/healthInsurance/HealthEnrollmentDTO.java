package com.callippus.healthInsurance;
import java.util.Date;
import java.util.List;

public class HealthEnrollmentDTO 
{	
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
	
	private Date fromDate;
	private Date toDate;
	private String type;
	private Date effctiveDate;
	private int subScriptiomAmt;
	
	private String hicName;
	private int hicNo;
	
	private int basicPay;
	
	
	
	
	public int getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(int basicPay) {
		this.basicPay = basicPay;
	}
	public Date getEffctiveDate() {
		return effctiveDate;
	}
	public void setEffctiveDate(Date effctiveDate) {
		this.effctiveDate = effctiveDate;
	}
	public int getSubScriptiomAmt() {
		return subScriptiomAmt;
	}
	public void setSubScriptiomAmt(int subScriptiomAmt) {
		this.subScriptiomAmt = subScriptiomAmt;
	}
	public String getHicName() {
		return hicName;
	}
	public void setHicName(String hicName) {
		this.hicName = hicName;
	}
	public int getHicNo() {
		return hicNo;
	}
	public void setHicNo(int hicNo) {
		this.hicNo = hicNo;
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
    private List<HealthEnrollmentDTO> healthInsuranceDetails;


	public List<HealthEnrollmentDTO> getHealthInsuranceDetails() {
		return healthInsuranceDetails;
	}
	public void setHealthInsuranceDetails(
			List<HealthEnrollmentDTO> healthInsuranceDetails) {
		this.healthInsuranceDetails = healthInsuranceDetails;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HealthEnrollmentDTO [sfid=");
		builder.append(sfid);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", designation=");
		builder.append(designation);
		builder.append(", joiningDate=");
		builder.append(joiningDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", jsonValue=");
		builder.append(jsonValue);
		builder.append(", param=");
		builder.append(param);
		builder.append(", requestId=");
		builder.append(requestId);
		builder.append(", requestID=");
		builder.append(requestID);
		builder.append(", sfID=");
		builder.append(sfID);
		builder.append(", sfId=");
		builder.append(sfId);
		builder.append(", fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", type=");
		builder.append(type);
		builder.append(", effctiveDate=");
		builder.append(effctiveDate);
		builder.append(", subScriptiomAmt=");
		builder.append(subScriptiomAmt);
		builder.append(", hicName=");
		builder.append(hicName);
		builder.append(", hicNo=");
		builder.append(hicNo);
		builder.append(", basicPay=");
		builder.append(basicPay);
		builder.append(", healthInsuranceDetails=");
		builder.append(healthInsuranceDetails);
		builder.append("]");
		return builder.toString();
	}
	
	

	

	
}

		
	