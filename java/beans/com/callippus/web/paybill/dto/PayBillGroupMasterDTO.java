package com.callippus.web.paybill.dto;

import java.util.Date;

public class PayBillGroupMasterDTO {
private int pk;
private String name;
private Date creationDate;
private Date lastModifiedDate;
private int status;
private String description;


public int getPk() {
	return pk;
}
public void setPk(int pk) {
	this.pk = pk;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
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
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

}
