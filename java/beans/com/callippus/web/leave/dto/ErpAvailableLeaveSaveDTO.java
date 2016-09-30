package com.callippus.web.leave.dto;

import java.util.Date;

public class ErpAvailableLeaveSaveDTO {
	private int id;
	private String sfID;
	private String leaveType;
	private Date fromDate;
	private Date toDate;
	private String noOfDays;
	private String reason;
	private String contactNo;
	private String sfid;
	private String sfId;
	private int designation;
	private String phnNo;
	private int status;
	private String address;
	private String requestedBy;
	private Date applyDate;
	private String requestId;
	private String requestID;
	private String ipAddress;
	private String reasonCancellation;
	private String cancelRequestId;
	private int leaveStatus;
	private String prescriptionYorN;
	private String prescriptiondoc; 
	
	
	public String getPrescriptionYorN() {
		return prescriptionYorN;
	}
	public void setPrescriptionYorN(String prescriptionYorN) {
		this.prescriptionYorN = prescriptionYorN;
	}
	public String getPrescriptiondoc() {
		return prescriptiondoc;
	}
	public void setPrescriptiondoc(String prescriptiondoc) {
		this.prescriptiondoc = prescriptiondoc;
	}
	public int getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(int leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	public String getCancelRequestId() {
		return cancelRequestId;
	}
	public void setCancelRequestId(String cancelRequestId) {
		this.cancelRequestId = cancelRequestId;
	}
	public String getReasonCancellation() {
		return reasonCancellation;
	}
	public void setReasonCancellation(String reasonCancellation) {
		this.reasonCancellation = reasonCancellation;
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
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int designation) {
		this.designation = designation;
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
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getSfId() {
		return sfId;
	}
	public void setSfId(String sfId) {
		this.sfId = sfId;
	}
	public String getPhnNo() {
		return phnNo;
	}
	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErpAvailableLeaveSaveDTO [id=");
		builder.append(id);
		builder.append(", sfID=");
		builder.append(sfID);
		builder.append(", leaveType=");
		builder.append(leaveType);
		builder.append(", fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", noOfDays=");
		builder.append(noOfDays);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", contactNo=");
		builder.append(contactNo);
		builder.append(", sfid=");
		builder.append(sfid);
		builder.append(", sfId=");
		builder.append(sfId);
		builder.append(", designation=");
		builder.append(designation);
		builder.append(", phnNo=");
		builder.append(phnNo);
		builder.append(", status=");
		builder.append(status);
		builder.append(", address=");
		builder.append(address);
		builder.append(", requestedBy=");
		builder.append(requestedBy);
		builder.append(", applyDate=");
		builder.append(applyDate);
		builder.append(", requestId=");
		builder.append(requestId);
		builder.append(", requestID=");
		builder.append(requestID);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
}
