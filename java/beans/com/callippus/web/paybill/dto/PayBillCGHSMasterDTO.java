package com.callippus.web.paybill.dto;

import java.util.Date;

public class PayBillCGHSMasterDTO {
	private int pk;
	private float gradePay;
	private float allowanceAmount;
	private int status;
	private Date creationDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Date effDate;
	
	
	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public float getGradePay() {
		return gradePay;
	}

	public void setGradePay(float gradePay) {
		this.gradePay = gradePay;
	}

	public float getAllowanceAmount() {
		return allowanceAmount;
	}

	public void setAllowanceAmount(float allowanceAmount) {
		this.allowanceAmount = allowanceAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
