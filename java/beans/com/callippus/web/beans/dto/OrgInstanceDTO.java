package com.callippus.web.beans.dto;

public class OrgInstanceDTO {
	private int id;
	private int roleID;
	private String name;
	private int parentID;
	private int status;
	private String creationDate;
	private String lastModifiedDate;
	private int departmentID;
	private int isHead;
	private String roleName;
	private int hierarchyID;
	private String hierarchyName;
	private String parentName;;
	private String departmentName;
	private String levelRoleName;

	public String getLevelRoleName() {
		return levelRoleName;
	}

	public void setLevelRoleName(String levelRoleName) {
		this.levelRoleName = levelRoleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getHierarchyID() {
		return hierarchyID;
	}

	public void setHierarchyID(int hierarchyID) {
		this.hierarchyID = hierarchyID;
	}

	public String getHierarchyName() {
		return hierarchyName;
	}

	public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public int getIsHead() {
		return isHead;
	}

	public void setIsHead(int isHead) {
		this.isHead = isHead;
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

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

}
