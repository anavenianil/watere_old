package com.callippus.web.beans.dto;

public class FinanceDetailsDTO extends CommonDTO {
	private int referenceID;
	private float amount;
	private String accountentSign;
	private String sanctionNo;
	private String billNo;
	private EmployeeClaimDetailsDTO empClaimDetails;
	private String repSig;


	public String getRepSig() {
		return repSig;
	}

	public void setRepSig(String repSig) {
		this.repSig = repSig;
	}

	public String getAccountentSign() {
		return accountentSign;
	}

	public void setAccountentSign(String accountentSign) {
		this.accountentSign = accountentSign;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setEmpClaimDetails(EmployeeClaimDetailsDTO empClaimDetails) {
		this.empClaimDetails = empClaimDetails;
	}

	public EmployeeClaimDetailsDTO getEmpClaimDetails() {
		return empClaimDetails;
	}
}
