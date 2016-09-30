package com.callippus.web.beans.dto;

public class DepartmentsDTO {
	private int id;
	private int hierarchyID;
	private String deptName;
	private int parentID;
	private int status;
	private String creationDate;
	private String lastModifiedDate;
	private String levelId;
	private int departmentTypeId;
	private String hierarchyName;
	private String departmentTypeName;
	private String parentName;
	private String levelDeptName;
	private String description;
	private String fax;
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String phoneNumber;
	private String email;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevelDeptName() {
		return levelDeptName;
	}

	public void setLevelDeptName(String levelDeptName) {
		this.levelDeptName = levelDeptName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHierarchyID() {
		return hierarchyID;
	}

	public void setHierarchyID(int hierarchyID) {
		this.hierarchyID = hierarchyID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
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

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public int getDepartmentTypeId() {
		return departmentTypeId;
	}

	public void setDepartmentTypeId(int departmentTypeId) {
		this.departmentTypeId = departmentTypeId;
	}

	public String getHierarchyName() {
		return hierarchyName;
	}

	public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;
	}

	public String getDepartmentTypeName() {
		return departmentTypeName;
	}

	public void setDepartmentTypeName(String departmentTypeName) {
		this.departmentTypeName = departmentTypeName;
	}

}
