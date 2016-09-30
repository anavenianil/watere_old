package com.callippus.web.beans.requests;

import java.util.Date;

public class CancelLeaveRequestBean {
	private String requestID;
	private String sfID;
	private String reason;
	private String ipAddress;
	private String requestedBy;
	private Date requestedDate;
	private int status;
	private int referenceID;
	private Date formattedRequestedDate;
	private LeaveRequestBean referenceDetails;
	private  String  leaveCanceldOPartId;
	

	public String getLeaveCanceldOPartId() {
		return leaveCanceldOPartId;
	}

	public void setLeaveCanceldOPartId(String leaveCanceldOPartId) {
		this.leaveCanceldOPartId = leaveCanceldOPartId;
	}

	public LeaveRequestBean getReferenceDetails() {
		return referenceDetails;
	}

	public void setReferenceDetails(LeaveRequestBean referenceDetails) {
		this.referenceDetails = referenceDetails;
	}

	public Date getFormattedRequestedDate() {
		return formattedRequestedDate;
	}

	public void setFormattedRequestedDate(Date formattedRequestedDate) {
		this.formattedRequestedDate = formattedRequestedDate;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

}