package com.callippus.web.beans.dto;

public class ApplicationRoleMappingDTO extends CommonDTO {
	
	private String sfid;
	private int applicationRoleId;
	
	

	public int getApplicationRoleId() {
		return applicationRoleId;
	}

	public void setApplicationRoleId(int applicationRoleId) {
		this.applicationRoleId = applicationRoleId;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	
	
	
}
