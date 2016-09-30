package com.callippus.web.beans.letterNumberFormat;

import java.io.Serializable;

public class IonSfidDTO implements Serializable{
	private Integer ionId;
private Integer copyStatus;
	
	public Integer getCopyStatus() {
		return copyStatus;
	}

	public void setCopyStatus(Integer copyStatus) {
		this.copyStatus = copyStatus;
	}

	
	private String sfid;
	
	public Integer getIonId() {
		return ionId;
	}
	public void setIonId(Integer ionId) {
		this.ionId = ionId;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	
	
	

}
