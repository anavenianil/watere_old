package com.callippus.web.beans.tutionFee;

import java.util.Date;
public class TutionFeeClaimMasterDTO{
	
	 private int id;
	 private String claimName;
	 private String claimType;
	 private int status;
	 private String lastModifiedBy;
	 private String createdBy;
	 private Date creationTime;
	 private Date lastModifiedTime;
	 private String description;
	 private int claimTypeId;
	 private ClaimTypeMasterDTO claimTypeMaster;
	 private String orderNo;
	 
	 
	 
	 
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public ClaimTypeMasterDTO getClaimTypeMaster() {
		return claimTypeMaster;
	}
	public void setClaimTypeMaster(ClaimTypeMasterDTO claimTypeMaster) {
		this.claimTypeMaster = claimTypeMaster;
	}
	public int getClaimTypeId() {
		return claimTypeId;
	}
	public void setClaimTypeId(int claimTypeId) {
		this.claimTypeId = claimTypeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClaimName() {
		return claimName;
	}
	public void setClaimName(String claimName) {
		this.claimName = claimName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
