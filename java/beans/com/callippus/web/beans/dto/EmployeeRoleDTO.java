package com.callippus.web.beans.dto;

public class EmployeeRoleDTO {
    private String id;
    private String sfid;
    private String employeeName;
    private String internalDivision;
    private String internalRole;
    private String parentID;
    private String parentName;
    private String officeName;
    private String departmentID;
    
    
    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
