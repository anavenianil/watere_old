package com.callippus.web.beans.dto;

public class GeneralListDTO {
    private String sfid;
    private String empName;
    private String designation;
    private String officeName;
    private String applicationRoleName;
    private String level;
    private String id;
    private String applicationRoleID;

    public String getApplicationRoleID() {
        return applicationRoleID;
    }

    public void setApplicationRoleID(String applicationRoleID) {
        this.applicationRoleID = applicationRoleID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
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

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getApplicationRoleName() {
        return applicationRoleName;
    }

    public void setApplicationRoleName(String applicationRoleName) {
        this.applicationRoleName = applicationRoleName;
    }

}
