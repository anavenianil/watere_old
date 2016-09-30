package com.callippus.web.beans.dto;

public class EmpPaymentsDTO extends CommonDTO{
	private int id;
	private String sfid;
	private String basicPay;
	private String gradePay;
	private float da;
	private float gpfClosingBalance;
	private String twoAddIncr;
	private String desigName;

	public String getDesigName() {
		return desigName;
	}

	public void setDesigName(String desigName) {
		this.desigName = desigName;
	}

	public String getTwoAddIncr() {
		return twoAddIncr;
	}

	public void setTwoAddIncr(String twoAddIncr) {
		this.twoAddIncr = twoAddIncr;
	}

	public float getGpfClosingBalance() {
		return gpfClosingBalance;
	}

	public void setGpfClosingBalance(float gpfClosingBalance) {
		this.gpfClosingBalance = gpfClosingBalance;
	}

	public float getDa() {
		return da;
	}

	public void setDa(float da) {
		this.da = da;
	}

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

	public String getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(String basicPay) {
		this.basicPay = basicPay;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

}
