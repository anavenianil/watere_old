package com.callippus.web.beans.letterNumberFormat;

import java.io.Serializable;

public class IonOrgDTO implements Serializable{
	private Integer ionId;
	
 private Integer copyStatus;
	
	public Integer getCopyStatus() {
		return copyStatus;
	}

	public void setCopyStatus(Integer copyStatus) {
		this.copyStatus = copyStatus;
	}

	public Integer getIonId() {
		return ionId;
	}

	public void setIonId(Integer ionId) {
		this.ionId = ionId;
	}

	
	private Integer organizationId;

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	
	

}
