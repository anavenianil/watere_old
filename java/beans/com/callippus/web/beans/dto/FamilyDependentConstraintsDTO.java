package com.callippus.web.beans.dto;

public class FamilyDependentConstraintsDTO {
	private int id;
	private String sfid;
	private String type;
	private int status;
	private int familyDependentId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getFamilyDependentId() {
		return familyDependentId;
	}
	public void setFamilyDependentId(int familyDependentId) {
		this.familyDependentId = familyDependentId;
	}
	
	

}
