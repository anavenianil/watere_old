package com.callippus.healthInsurance;

import java.util.Date;

public class HealthSubscriptionDTO {
	
	
	private int id;
	private String sfId;
	private Date effctiveDate;
	private int subScriptiomAmt;
	private String hicName;
	private int hicNo;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfId() {
		return sfId;
	}
	public void setSfId(String sfId) {
		this.sfId = sfId;
	}
	public Date getEffctiveDate() {
		return effctiveDate;
	}
	public void setEffctiveDate(Date effctiveDate) {
		this.effctiveDate = effctiveDate;
	}
	public int getSubScriptiomAmt() {
		return subScriptiomAmt;
	}
	public void setSubScriptiomAmt(int subScriptiomAmt) {
		this.subScriptiomAmt = subScriptiomAmt;
	}
	public String getHicName() {
		return hicName;
	}
	public void setHicName(String hicName) {
		this.hicName = hicName;
	}
	public int getHicNo() {
		return hicNo;
	}
	public void setHicNo(int hicNo) {
		this.hicNo = hicNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
