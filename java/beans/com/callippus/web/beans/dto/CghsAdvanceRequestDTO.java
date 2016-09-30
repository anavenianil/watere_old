package com.callippus.web.beans.dto;

import java.util.Date;

import com.callippus.web.beans.requests.CGHSRequestProcessBean;

public class CghsAdvanceRequestDTO {
	
	private Date admissionDate;
	private String permissionDetails;
	private String message;
	private String sfID;
	private String ipAddress;
	private String requestID;
	private String requestType;
	private String requestTypeID;
	private String referrenceRequestID;
	private Date approvedDate;
	private String approvedBy;
	private Date appliedDate;
	private int status;
	private String essentialityCertificate;
	private String employeeName;
	private String cghsCardNumber;
	private String address;
	private String relation;
	private String requestIdadv;
	
	private String estimationFile;
	private String contingentFile;
	private String cghsCardFile;
	private String prescriptionFile;
	private String permissionFile;
	private CGHSRequestProcessBean cghsRequestDetails;
	private Date estimationDate;
	private String estimationAmount;
	private String InPatientNumber;
	private String issuedAmount;
	private String settleAmount;
	private String cdaAmount;
	private String billNo;
	private String sanctionNo;
	private String configurationAmount;
	private String percentage;
	
	private String repSig;
	private String dvNo;      
	private Date dvDate;      
	private String accOfficer;
	private String reimbursementId;
	private String historyId;
	private String cghscardref;
	
	
	//
	
	
	
	public String getHistoryId() {
		return historyId;
	}
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	public String getPercentage() {
		return percentage;
	}
	public String getReimbursementId() {
		return reimbursementId;
	}
	public void setReimbursementId(String reimbursementId) {
		this.reimbursementId = reimbursementId;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	//
	
	
	
	
	
	public String getDvNo() {
		return dvNo;
	}
	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}
	public Date getDvDate() {
		return dvDate;
	}
	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}
	public String getAccOfficer() {
		return accOfficer;
	}
	public void setAccOfficer(String accOfficer) {
		this.accOfficer = accOfficer;
	}
	public String getRepSig() {
		return repSig;
	}
	public void setRepSig(String repSig) {
		this.repSig = repSig;
	}
	public String getConfigurationAmount() {
		return configurationAmount;
	}
	public void setConfigurationAmount(String configurationAmount) {
		this.configurationAmount = configurationAmount;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getSanctionNo() {
		return sanctionNo;
	}
	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}
	public String getCdaAmount() {
		return cdaAmount;
	}
	public void setCdaAmount(String cdaAmount) {
		this.cdaAmount = cdaAmount;
	}
	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getIssuedAmount() {
		return issuedAmount;
	}
	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
	}
	public Date getEstimationDate() {
		return estimationDate;
	}
	public void setEstimationDate(Date estimationDate) {
		this.estimationDate = estimationDate;
	}
	public String getEstimationAmount() {
		return estimationAmount;
	}
	public void setEstimationAmount(String estimationAmount) {
		this.estimationAmount = estimationAmount;
	}
	public String getInPatientNumber() {
		return InPatientNumber;
	}
	public void setInPatientNumber(String inPatientNumber) {
		InPatientNumber = inPatientNumber;
	}
	public Date getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getPermissionDetails() {
		return permissionDetails;
	}
	public void setPermissionDetails(String permissionDetails) {
		this.permissionDetails = permissionDetails;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
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
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestTypeID() {
		return requestTypeID;
	}
	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}
	public String getReferrenceRequestID() {
		return referrenceRequestID;
	}
	public void setReferrenceRequestID(String referrenceRequestID) {
		this.referrenceRequestID = referrenceRequestID;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEssentialityCertificate() {
		return essentialityCertificate;
	}
	public void setEssentialityCertificate(String essentialityCertificate) {
		this.essentialityCertificate = essentialityCertificate;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCghsCardNumber() {
		return cghsCardNumber;
	}
	public void setCghsCardNumber(String cghsCardNumber) {
		this.cghsCardNumber = cghsCardNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getEstimationFile() {
		return estimationFile;
	}
	public void setEstimationFile(String estimationFile) {
		this.estimationFile = estimationFile;
	}
	public String getContingentFile() {
		return contingentFile;
	}
	public void setContingentFile(String contingentFile) {
		this.contingentFile = contingentFile;
	}
	public String getCghsCardFile() {
		return cghsCardFile;
	}
	public void setCghsCardFile(String cghsCardFile) {
		this.cghsCardFile = cghsCardFile;
	}
	public String getPrescriptionFile() {
		return prescriptionFile;
	}
	public void setPrescriptionFile(String prescriptionFile) {
		this.prescriptionFile = prescriptionFile;
	}
	public String getPermissionFile() {
		return permissionFile;
	}
	public void setPermissionFile(String permissionFile) {
		this.permissionFile = permissionFile;
	}
	public CGHSRequestProcessBean getCghsRequestDetails() {
		return cghsRequestDetails;
	}
	public void setCghsRequestDetails(CGHSRequestProcessBean cghsRequestDetails) {
		this.cghsRequestDetails = cghsRequestDetails;
	}
	public String getRequestIdadv() {
		return requestIdadv;
	}
	public void setRequestIdadv(String requestIdadv) {
		this.requestIdadv = requestIdadv;
	}
	public String getCghscardref() {
		return cghscardref;
	}
	public void setCghscardref(String cghscardref) {
		this.cghscardref = cghscardref;
	}
	
	
	
	
	
}
