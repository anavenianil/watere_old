package com.callippus.web.beans.dto;

public class EmployeeDetailsDTO {
	private int id;
	private String empSfid;
	private String empName;
	private String designation;
	private String department;
	private String parentSfid;
	private String parentName;
	private String roleName;
	private int roleID;
	private int defRole;
	
	
	
	
	
	
	// new
	private String defaultStatus;
	private String retirementDate;
    public String getDefaultStatus() {
		return defaultStatus;
	}
	public void setDefaultStatus(String defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
	public String getRetirementDate() {
		return retirementDate;
	}
	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}
	//
	public int getDefRole() {
		return defRole;
	}

	public void setDefRole(int defRole) {
		this.defRole = defRole;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmpSfid() {
		return empSfid;
	}

	public void setEmpSfid(String empSfid) {
		this.empSfid = empSfid;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getParentSfid() {
		return parentSfid;
	}

	public void setParentSfid(String parentSfid) {
		this.parentSfid = parentSfid;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
