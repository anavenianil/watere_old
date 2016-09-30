package com.callippus.web.loan.beans.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanDetailsDTO extends CommonDTO {
	private int loanTypeID;
	private LoanTypeMasterDTO loanTypeDetails;
	private int loanSubTypeID;
	private String loanSubTypeName;
	private int minInstallments;
	private int maxInstallments;
	private int minInterestInstallments;
	private int maxInterestInstallments;
	private String periodTypeFlag;
	private Date fromDate;
	private Date toDate;
	private int noOfMonths;
	private float interestRate;
	private int maxRecoveryPeriod;
	private String impactOnLeaveFlag;
	private float experience;

	private int recoveryStart;
	private String recoveryFlag;

	public int getLoanSubTypeID() {
		return loanSubTypeID;
	}

	public void setLoanSubTypeID(int loanSubTypeID) {
		this.loanSubTypeID = loanSubTypeID;
	}

	public int getRecoveryStart() {
		return recoveryStart;
	}

	public void setRecoveryStart(int recoveryStart) {
		this.recoveryStart = recoveryStart;
	}

	public String getRecoveryFlag() {
		return recoveryFlag;
	}

	public void setRecoveryFlag(String recoveryFlag) {
		this.recoveryFlag = recoveryFlag;
	}

	public int getNoOfMonths() {
		return noOfMonths;
	}

	public void setNoOfMonths(int noOfMonths) {
		this.noOfMonths = noOfMonths;
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

	public int getMinInstallments() {
		return minInstallments;
	}

	public void setMinInstallments(int minInstallments) {
		this.minInstallments = minInstallments;
	}

	public int getMaxInstallments() {
		return maxInstallments;
	}

	public void setMaxInstallments(int maxInstallments) {
		this.maxInstallments = maxInstallments;
	}

	public String getPeriodTypeFlag() {
		return periodTypeFlag;
	}

	public void setPeriodTypeFlag(String periodTypeFlag) {
		this.periodTypeFlag = periodTypeFlag;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}


	public String getImpactOnLeaveFlag() {
		return impactOnLeaveFlag;
	}

	public void setImpactOnLeaveFlag(String impactOnLeaveFlag) {
		this.impactOnLeaveFlag = impactOnLeaveFlag;
	}

	public float getExperience() {
		return experience;
	}

	public void setExperience(float experience) {
		this.experience = experience;
	}

	public void setMaxRecoveryPeriod(int maxRecoveryPeriod) {
		this.maxRecoveryPeriod = maxRecoveryPeriod;
	}

	public int getMaxRecoveryPeriod() {
		return maxRecoveryPeriod;
	}

	public void setLoanSubTypeName(String loanSubTypeName) {
		this.loanSubTypeName = loanSubTypeName;
	}

	public String getLoanSubTypeName() {
		return loanSubTypeName;
	}

	public int getMinInterestInstallments() {
		return minInterestInstallments;
	}

	public void setMinInterestInstallments(int minInterestInstallments) {
		this.minInterestInstallments = minInterestInstallments;
	}

	public int getMaxInterestInstallments() {
		return maxInterestInstallments;
	}

	public void setMaxInterestInstallments(int maxInterestInstallments) {
		this.maxInterestInstallments = maxInterestInstallments;
	}

}