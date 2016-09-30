package com.callippus.web.beans.requests;

import java.util.Date;

import com.callippus.web.leave.dto.LeaveTypeDTO;

public class ConvertLeaveRequestBean {
	private int id;
	private int requestID;
	private LeaveTypeDTO leaveTypeDetails;
	private String sfID;
	private String fromDate;
	private String toDate;
	private String prefix;
	private String suffix;
	private String noOfDays;
	private String reason;
	private String ipAddress;
	private String requestedBy;
	private Date requestedDate;
	private int status;
	private String convertedTo;
	private int referenceID;
	private int debitFrom;
	private String debitLeaves;
	private Date formattedRequestedDate;
	private Date formattedFromDate;
	private Date formattedToDate;
	private LeaveRequestBean referenceDetails;
	private Integer leaveConversionDopartId;
	
	
   public Integer getLeaveConversionDopartId() {
		return leaveConversionDopartId;
	}

	public void setLeaveConversionDopartId(Integer leaveConversionDopartId) {
		this.leaveConversionDopartId = leaveConversionDopartId;
	}

	//Narayana
	private String newLeaveRequestID;
	
	public String getNewLeaveRequestID() {
		return newLeaveRequestID;
	}

	public void setNewLeaveRequestID(String newLeaveRequestID) {
		this.newLeaveRequestID = newLeaveRequestID;
	}

	public LeaveRequestBean getReferenceDetails() {
		return referenceDetails;
	}

	public void setReferenceDetails(LeaveRequestBean referenceDetails) {
		this.referenceDetails = referenceDetails;
	}

	public Date getFormattedFromDate() {
		return formattedFromDate;
	}

	public void setFormattedFromDate(Date formattedFromDate) {
		this.formattedFromDate = formattedFromDate;
	}

	public Date getFormattedToDate() {
		return formattedToDate;
	}

	public void setFormattedToDate(Date formattedToDate) {
		this.formattedToDate = formattedToDate;
	}

	public Date getFormattedRequestedDate() {
		return formattedRequestedDate;
	}

	public void setFormattedRequestedDate(Date formattedRequestedDate) {
		this.formattedRequestedDate = formattedRequestedDate;
	}

	public int getDebitFrom() {
		return debitFrom;
	}

	public void setDebitFrom(int debitFrom) {
		this.debitFrom = debitFrom;
	}

	public String getDebitLeaves() {
		return debitLeaves;
	}

	public void setDebitLeaves(String debitLeaves) {
		this.debitLeaves = debitLeaves;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getConvertedTo() {
		return convertedTo;
	}

	public void setConvertedTo(String convertedTo) {
		this.convertedTo = convertedTo;
	}

}
