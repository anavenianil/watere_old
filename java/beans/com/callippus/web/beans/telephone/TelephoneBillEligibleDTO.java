package com.callippus.web.beans.telephone;

public class TelephoneBillEligibleDTO {

	private int id;
	private int designationId;
	private int internetFlag;
	private int amount;
	private float serviceTax;
	private String sfid;
	private int totAmount;
	
	
	public float getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(float serviceTax) {
		this.serviceTax = serviceTax;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public int getInternetFlag() {
		return internetFlag;
	}
	public void setInternetFlag(int internetFlag) {
		this.internetFlag = internetFlag;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public int getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(int totAmount) {
		this.totAmount = totAmount;
	}
	
	
	
}
