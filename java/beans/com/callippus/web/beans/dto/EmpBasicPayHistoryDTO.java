package com.callippus.web.beans.dto;

import java.util.Date;

public class EmpBasicPayHistoryDTO extends CommonDTO {
	private String sfid;
	private float basicPay;
	private String incrementType;
	private float incrementValue;
	private String referenceType;
	private int designationId;
	private Date presentEffectiveDate;
	private String desigName;
	
	
	
	

	public String getDesigName() {
		return desigName;
	}

	public void setDesigName(String desigName) {
		this.desigName = desigName;
	}

	public Date getPresentEffectiveDate() {
		return presentEffectiveDate;
	}

	public void setPresentEffectiveDate(Date presentEffectiveDate) {
		this.presentEffectiveDate = presentEffectiveDate;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getSfid() {
		return sfid;
	}

	public float getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(float basicPay) {
		this.basicPay = basicPay;
	}

	public String getIncrementType() {
		return incrementType;
	}

	public void setIncrementType(String incrementType) {
		this.incrementType = incrementType;
	}

	public float getIncrementValue() {
		return incrementValue;
	}

	public void setIncrementValue(float incrementValue) {
		this.incrementValue = incrementValue;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}


	

}
