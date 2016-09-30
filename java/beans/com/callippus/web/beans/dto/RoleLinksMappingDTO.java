package com.callippus.web.beans.dto;

public class RoleLinksMappingDTO {
    private int id;
    private int roleID;
    private int linkID;
    private int status;
    private String description;
    
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
    public int getLinkID() {
        return linkID;
    }
    public void setLinkID(int linkID) {
        this.linkID = linkID;
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
