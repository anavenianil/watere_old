package com.callippus.web.beans.tutionFee;
import java.util.Date;

public class TutionFeeRequestDetailsDTO {
    private int id;
    private String sfid;
    private String requestID;
    private int grandToatal;
    private String createdBy;
    private String lastModifiedBy;
    private Date creationDate;
    private Date lastModifiedDate;
    private int status;
    private Date fromDate;
    private Date toDate;
    private String requestTypeID;
    private String requestType;
  	private Date sanctionedDate;
	private int sanctionedAmount;
	private int limitId;
	private String ipAddress;
	private int claimTypeId;
	private int financialYearId;
	private String historyID;
	private int cashAssignmentAmount;
	private String missingClaimRemarks;
	private String missingPeriodFrom;
	private String missingPeriodTo;
	private String taskHolderRemarks;
	private String userRemarks;
	private int eligibilityId;
	
	
	
	
   public int getClaimTypeId() {
		return claimTypeId;
	}
	public void setClaimTypeId(int claimTypeId) {
		this.claimTypeId = claimTypeId;
	}
public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
public int getLimitId() {
		return limitId;
	}
	public void setLimitId(int limitId) {
		this.limitId = limitId;
	}
public Date getSanctionedDate() {
		return sanctionedDate;
	}
	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}
	public int getSanctionedAmount() {
		return sanctionedAmount;
	}
	public void setSanctionedAmount(int sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
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
	public String getRequestTypeID() {
		return requestTypeID;
	}
	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public int getGrandToatal() {
		return grandToatal;
	}
	public void setGrandToatal(int grandToatal) {
		this.grandToatal = grandToatal;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(int financialYearId) {
		this.financialYearId = financialYearId;
	}
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public int getCashAssignmentAmount() {
		return cashAssignmentAmount;
	}
	public void setCashAssignmentAmount(int cashAssignmentAmount) {
		this.cashAssignmentAmount = cashAssignmentAmount;
	}
	
	public String getMissingClaimRemarks() {
		return missingClaimRemarks;
	}
	public void setMissingClaimRemarks(String missingClaimRemarks) {
		this.missingClaimRemarks = missingClaimRemarks;
	}
	public String getMissingPeriodFrom() {
		return missingPeriodFrom;
	}
	public void setMissingPeriodFrom(String missingPeriodFrom) {
		this.missingPeriodFrom = missingPeriodFrom;
	}
	public String getMissingPeriodTo() {
		return missingPeriodTo;
	}
	public void setMissingPeriodTo(String missingPeriodTo) {
		this.missingPeriodTo = missingPeriodTo;
	}
	public String getTaskHolderRemarks() {
		return taskHolderRemarks;
	}
	public void setTaskHolderRemarks(String taskHolderRemarks) {
		this.taskHolderRemarks = taskHolderRemarks;
	}
	public String getUserRemarks() {
		return userRemarks;
	}
	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}
	public int getEligibilityId() {
		return eligibilityId;
	}
	public void setEligibilityId(int eligibilityId) {
		this.eligibilityId = eligibilityId;
	}
	
	
}
