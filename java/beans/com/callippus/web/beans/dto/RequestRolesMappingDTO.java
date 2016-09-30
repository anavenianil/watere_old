package com.callippus.web.beans.dto;

public class RequestRolesMappingDTO {
	
	private int id;
	private int applicationRoleId;
	private String  requestTypeId;
	private String name;
	public String getRequestTypeId() {
		return requestTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRequestTypeId(String requestTypeId) {
		this.requestTypeId = requestTypeId;
	}
	private int status;
	private String creationDate;
	private String modifiedDate;
	private String lastModifiedBy;
	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApplicationRoleId() {
		return applicationRoleId;
	}
	public void setApplicationRoleId(int applicationRoleId) {
		this.applicationRoleId = applicationRoleId;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	

}
