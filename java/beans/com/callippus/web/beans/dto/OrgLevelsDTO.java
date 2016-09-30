package com.callippus.web.beans.dto;

public class OrgLevelsDTO extends CommonDTO {
	private String parentName;
	private int parentID;

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
