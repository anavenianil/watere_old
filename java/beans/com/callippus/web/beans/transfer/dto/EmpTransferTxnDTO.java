package com.callippus.web.beans.transfer.dto;

import java.util.Date;

public class EmpTransferTxnDTO {
private int id;
private String fromDept;
private String toDept;
private String assignedTo;
private int doPartId;
private String doPartDate;
private String sfID;
private String createdBy;
private Date creationDate;
private int status;



public Date getCreationDate() {
	return creationDate;
}
public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFromDept() {
	return fromDept;
}
public void setFromDept(String fromDept) {
	this.fromDept = fromDept;
}
public String getToDept() {
	return toDept;
}
public void setToDept(String toDept) {
	this.toDept = toDept;
}
public String getAssignedTo() {
	return assignedTo;
}
public void setAssignedTo(String assignedTo) {
	this.assignedTo = assignedTo;
}
public int getDoPartId() {
	return doPartId;
}
public void setDoPartId(int doPartId) {
	this.doPartId = doPartId;
}
public String getDoPartDate() {
	return doPartDate;
}
public void setDoPartDate(String doPartDate) {
	this.doPartDate = doPartDate;
}
public String getSfID() {
	return sfID;
}
public void setSfID(String sfID) {
	this.sfID = sfID;
}


}
