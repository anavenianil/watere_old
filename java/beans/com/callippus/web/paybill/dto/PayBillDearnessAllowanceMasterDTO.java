package com.callippus.web.paybill.dto;

import java.util.Date;

public class PayBillDearnessAllowanceMasterDTO {
private int pk;
private int status;
private Date daDate;
private float daValue;
private String displayDate;
private Date creationDate;
private String createdBy;
private Date modifiedDate;
private String modifiedBy;

public String getDisplayDate() {
	return displayDate;
}
public void setDisplayDate(String displayDate) {
	this.displayDate = displayDate;
}
public int getPk() {
	return pk;
}
public void setPk(int pk) {
	this.pk = pk;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public Date getDaDate() {
	return daDate;
}

public float getDaValue() {
	return daValue;
}
public void setDaValue(float daValue) {
	this.daValue = daValue;
}
public void setDaDate(Date daDate) {
	this.daDate = daDate;
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
