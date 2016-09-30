package com.callippus.web.beans.letterNumberFormat;

import java.io.Serializable;

public class IonOrgRoleDTO implements Serializable{
	private Integer ionId;
	private String orgOrHirarchy;
	
	

	


	public String getOrgOrHirarchy() {
		return orgOrHirarchy;
	}



	public void setOrgOrHirarchy(String orgOrHirarchy) {
		this.orgOrHirarchy = orgOrHirarchy;
	}



	public Integer getIonId() {
		return ionId;
	}



	public void setIonId(Integer ionId) {
		this.ionId = ionId;
	}



	public Integer getOrgRoleId() {
		return orgRoleId;
	}



	public void setOrgRoleId(Integer orgRoleId) {
		this.orgRoleId = orgRoleId;
	}



	private Integer orgRoleId;
	

}
