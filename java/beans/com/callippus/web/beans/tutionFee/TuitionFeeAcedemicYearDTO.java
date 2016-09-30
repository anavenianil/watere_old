package com.callippus.web.beans.tutionFee;

import java.util.Date;
public class TuitionFeeAcedemicYearDTO {
   private int id;
   private String className;
   private String orderNo;
   private String description;
   private Date fromDate;
   private Date toDate;
   private String createdBy;
   private String lastModifiedBy;
   private Date creationDate;
   private Date lastModifiedDate;
   private int status;
   
   private int pk;
   private String academicType;
   
public String getAcademicType() {
	return academicType;
}
public void setAcademicType(String academicType) {
	this.academicType = academicType;
}

public String getOrderNo() {
	return orderNo;
}
public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public int getPk() {
	return pk;
}
public void setPk(int pk) {
	this.pk = pk;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getClassName() {
	return className;
}
public void setClassName(String className) {
	this.className = className;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
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

}
