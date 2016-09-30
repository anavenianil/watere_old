package com.callippus.web.beans.dto;

import java.util.Date;

public class EmployeeClaimDetailsDTO extends CommonDTO {
	private int moduleId;
	private int fundId;
	private String requestType;
	private int requestID;
	private int refRequestId;
	private float amountClaimed;
	private Date appliedDate;
	private String appliedBy;
	private String ipAddress;
	private int requestTypeID;
	private int workFlowStatus;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public int getRefRequestId() {
		return refRequestId;
	}

	public void setRefRequestId(int refRequestId) {
		this.refRequestId = refRequestId;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setAmountClaimed(float amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public float getAmountClaimed() {
		return amountClaimed;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestType() {
		return requestType;
	}

	public int getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(int requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public int getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(int workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public void setAppliedBy(String appliedBy) {
		this.appliedBy = appliedBy;
	}

	public String getAppliedBy() {
		return appliedBy;
	}

}
