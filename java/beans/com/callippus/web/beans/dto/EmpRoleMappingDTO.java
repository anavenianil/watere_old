package com.callippus.web.beans.dto;

public class EmpRoleMappingDTO {

	private int id;
	private String sfid;
	private Integer roleInstanceId;
	private int status;
	private String internalDivision;
	private String internalRole;
	private String parentId;
	private String creationDate;
	private String lastModifiedDate;
	private int applicationRoleId;
	private Integer orgRoleId;
	private String departmentId;
	private int parentRoleID;
	
	public int getParentRoleID() {
		return parentRoleID;
	}

	public void setParentRoleID(int parentRoleID) {
		this.parentRoleID = parentRoleID;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getOrgRoleId() {
		return orgRoleId;
	}

	public void setOrgRoleId(Integer orgRoleId) {
		this.orgRoleId = orgRoleId;
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

	public Integer getRoleInstanceId() {
		return roleInstanceId;
	}

	public void setRoleInstanceId(Integer roleInstanceId) {
		this.roleInstanceId = roleInstanceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInternalDivision() {
		return internalDivision;
	}

	public void setInternalDivision(String internalDivision) {
		this.internalDivision = internalDivision;
	}

	public String getInternalRole() {
		return internalRole;
	}

	public void setInternalRole(String internalRole) {
		this.internalRole = internalRole;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

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

	public int getApplicationRoleId() {
		return applicationRoleId;
	}

	public void setApplicationRoleId(int applicationRoleId) {
		this.applicationRoleId = applicationRoleId;
	}

}
