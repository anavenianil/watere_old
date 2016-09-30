package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanAmountDetailsDTO extends CommonDTO {
	private int loanTypeID;
	private LoanTypeMasterDTO loanTypeDetails;
	private int loanSubTypeID;
	private String gazType;
	private String payFlag;
	private String daFlag;
	private String balanceFlag;
	private String monthsFlag;
	private int daPercentage;
	private String multipleFlag;
	private String loanSubTypeName;

	public String getLoanSubTypeName() {
		return loanSubTypeName;
	}

	public void setLoanSubTypeName(String loanSubTypeName) {
		this.loanSubTypeName = loanSubTypeName;
	}

	public String getMultipleFlag() {
		return multipleFlag;
	}

	public void setMultipleFlag(String multipleFlag) {
		this.multipleFlag = multipleFlag;
	}

	public int getDaPercentage() {
		return daPercentage;
	}

	public void setDaPercentage(int daPercentage) {
		this.daPercentage = daPercentage;
	}

	public int getLoanTypeID() {
		return loanTypeID;
	}

	public void setLoanTypeID(int loanTypeID) {
		this.loanTypeID = loanTypeID;
	}

	public LoanTypeMasterDTO getLoanTypeDetails() {
		return loanTypeDetails;
	}

	public void setLoanTypeDetails(LoanTypeMasterDTO loanTypeDetails) {
		this.loanTypeDetails = loanTypeDetails;
	}

	public int getLoanSubTypeID() {
		return loanSubTypeID;
	}

	public void setLoanSubTypeID(int loanSubTypeID) {
		this.loanSubTypeID = loanSubTypeID;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getDaFlag() {
		return daFlag;
	}

	public void setDaFlag(String daFlag) {
		this.daFlag = daFlag;
	}

	public String getBalanceFlag() {
		return balanceFlag;
	}

	public void setBalanceFlag(String balanceFlag) {
		this.balanceFlag = balanceFlag;
	}

	public String getMonthsFlag() {
		return monthsFlag;
	}

	public void setMonthsFlag(String monthsFlag) {
		this.monthsFlag = monthsFlag;
	}

}