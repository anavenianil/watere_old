package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanAmountGridDTO extends CommonDTO {
	private int loanAmountID;
	private LoanAmountDetailsDTO loanAmountDetails;
	private int stageID;
	private String payBalanceRelation;
	private float amount;
	private String relationShip;
	private String payOrGpf;
	private int da;
	private String subseqRelation;
	private int subseqMonths;

	public int getLoanAmountID() {
		return loanAmountID;
	}

	public void setLoanAmountID(int loanAmountID) {
		this.loanAmountID = loanAmountID;
	}

	public LoanAmountDetailsDTO getLoanAmountDetails() {
		return loanAmountDetails;
	}

	public void setLoanAmountDetails(LoanAmountDetailsDTO loanAmountDetails) {
		this.loanAmountDetails = loanAmountDetails;
	}

	public int getStageID() {
		return stageID;
	}

	public void setStageID(int stageID) {
		this.stageID = stageID;
	}

	public String getPayBalanceRelation() {
		return payBalanceRelation;
	}

	public void setPayBalanceRelation(String payBalanceRelation) {
		this.payBalanceRelation = payBalanceRelation;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getPayOrGpf() {
		return payOrGpf;
	}

	public void setPayOrGpf(String payOrGpf) {
		this.payOrGpf = payOrGpf;
	}

	public int getDa() {
		return da;
	}

	public void setDa(int da) {
		this.da = da;
	}

	public String getSubseqRelation() {
		return subseqRelation;
	}

	public void setSubseqRelation(String subseqRelation) {
		this.subseqRelation = subseqRelation;
	}

	public int getSubseqMonths() {
		return subseqMonths;
	}

	public void setSubseqMonths(int subseqMonths) {
		this.subseqMonths = subseqMonths;
	}

}
