package com.callippus.web.paybill.dto;

import java.util.Date;

public class PayBillResidentialSecurityMasterDTO {
private int pk;
private int quarterTypeId;
private float amount;
private int status;
private Date creationDate;
private String createdBy;
private Date modifiedDate;
private String modifiedBy;
private PayBillQuartersTypeMasterDTO quartersTypeMasterDTO;
private Date effDate;



public Date getEffDate() {
	return effDate;
}
public void setEffDate(Date effDate) {
	this.effDate = effDate;
}
public PayBillQuartersTypeMasterDTO getQuartersTypeMasterDTO() {
	return quartersTypeMasterDTO;
}
public void setQuartersTypeMasterDTO(PayBillQuartersTypeMasterDTO quartersTypeMasterDTO) {
	this.quartersTypeMasterDTO = quartersTypeMasterDTO;
}
public int getPk() {
	return pk;
}
public void setPk(int pk) {
	this.pk = pk;
}
public int getQuarterTypeId() {
	return quarterTypeId;
}
public void setQuarterTypeId(int quarterTypeId) {
	this.quarterTypeId = quarterTypeId;
}

public float getAmount() {
	return amount;
}
public void setAmount(float amount) {
	this.amount = amount;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public Date getCreationDate() {
	return creationDate;
}
public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public Date getModifiedDate() {
	return modifiedDate;
}
public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}

}
