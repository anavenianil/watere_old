package com.callippus.web.beans.letterNumberFormat;

import java.io.Serializable;

public class IonDesignDTO implements Serializable{
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

	public Integer getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}

	private Integer designationId;
	
	
	
	
	

}
