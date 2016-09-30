package com.callippus.web.beans.dto;

public class ReqRoleWorkMappingDTO {

	private int id;
	private int requestTypeId;
	private int workflowId;
	private int roleInstanceId;
	private int status;
	private String creationDate;
	private String lastModifiedDate;

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(int requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

	public int getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}

	public int getRoleInstanceId() {
		return roleInstanceId;
	}

	public void setRoleInstanceId(int roleInstanceId) {
		this.roleInstanceId = roleInstanceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
