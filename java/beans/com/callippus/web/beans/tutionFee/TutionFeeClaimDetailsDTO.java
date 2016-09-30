package com.callippus.web.beans.tutionFee;

import java.util.Date;

import com.callippus.web.beans.family.FamilyBean;
public class TutionFeeClaimDetailsDTO{
	  private int id;
	  private int claimId;
	  private int childRelationId;
	  private String sfid;
	  private String appNo;
	  private Date appDate;
	  private int amount;
	  private int requestId;
	  private String createdBy;
	  private String lastModifiedBy;
	  private Date creationDate;
	  private Date lastModifiedDate;
	  private int status;
	  private int classId;
      private Date sanctionedDate;
	  private int sanctionedAmount;
	  private TutionFeeClaimMasterDTO claimDetails;
	  private FamilyBean familyDetails;
	  private TuitionFeeAcedemicYearDTO classDetails;
	  private int limitId;
	  private int claimTypeId;
	  private Date fromDate;
	  private Date toDate;
	  private String boardType;
	  
	  private int totalClaimed;
	  private int totalSanctionedAmt;
	
	  
	  
	
	
	
	public int getTotalClaimed() {
		return totalClaimed;
	}
	public void setTotalClaimed(int totalClaimed) {
		this.totalClaimed = totalClaimed;
	}
	public int getClaimTypeId() {
		return claimTypeId;
	}
	public void setClaimTypeId(int claimTypeId) {
		this.claimTypeId = claimTypeId;
	}
	public int getLimitId() {
		return limitId;
	}
	public void setLimitId(int limitId) {
		this.limitId = limitId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public TuitionFeeAcedemicYearDTO getClassDetails() {
		return classDetails;
	}
	public void setClassDetails(TuitionFeeAcedemicYearDTO classDetails) {
		this.classDetails = classDetails;
	}
	public FamilyBean getFamilyDetails() {
		return familyDetails;
	}
	public void setFamilyDetails(FamilyBean familyDetails) {
		this.familyDetails = familyDetails;
	}
	public TutionFeeClaimMasterDTO getClaimDetails() {
		return claimDetails;
	}
	public void setClaimDetails(TutionFeeClaimMasterDTO claimDetails) {
		this.claimDetails = claimDetails;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClaimId() {
		return claimId;
	}
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}
	public int getChildRelationId() {
		return childRelationId;
	}
	public void setChildRelationId(int childRelationId) {
		this.childRelationId = childRelationId;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
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
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public int getTotalSanctionedAmt() {
		return totalSanctionedAmt;
	}
	public void setTotalSanctionedAmt(int totalSanctionedAmt) {
		this.totalSanctionedAmt = totalSanctionedAmt;
	}
	 

}
