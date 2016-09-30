package com.callippus.web.loan.beans.dto;

import java.util.Date;

import com.callippus.web.beans.requests.LoanRequestProcessBean;

public class LoanPaymentDTO {
	private int id;
	private int requestID;
	private String sfID;
	private LoanRequestProcessBean requestDetails;
	private int loanTypeID;
	private LoanTypeMasterDTO loanTypeDetails;
	private int loanSubTypeID;
	private float requestedAmount;
	private Date requestedDate;
	private int requestedInstalments;
	private int requestedInterestInstalments;
	private float sanctionedAmount;
	private Date sanctionedDate;
	private int sanctionedInstalments;
	private int sanctionedIntInstalments;
	private float emi;
	private float lastEmi;
	private String bankAccount;
	private String bankBranch;
	private Date recStartDate;
	private String loanRefNo;
	private int status;
	private float interestRate;
	private float interestAmount;
	private float interestEmi;
	private float interestLastEmi;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLoanRefNo() {
		return loanRefNo;
	}

	public void setLoanRefNo(String loanRefNo) {
		this.loanRefNo = loanRefNo;
	}

	public Date getRecStartDate() {
		return recStartDate;
	}

	public void setRecStartDate(Date recStartDate) {
		this.recStartDate = recStartDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
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

	public float getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(float requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public int getRequestedInstalments() {
		return requestedInstalments;
	}

	public void setRequestedInstalments(int requestedInstalments) {
		this.requestedInstalments = requestedInstalments;
	}

	public float getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(float sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public Date getSanctionedDate() {
		return sanctionedDate;
	}

	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	public int getSanctionedInstalments() {
		return sanctionedInstalments;
	}

	public void setSanctionedInstalments(int sanctionedInstalments) {
		this.sanctionedInstalments = sanctionedInstalments;
	}

	public float getEmi() {
		return emi;
	}

	public void setEmi(float emi) {
		this.emi = emi;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public void setRequestDetails(LoanRequestProcessBean requestDetails) {
		this.requestDetails = requestDetails;
	}

	public LoanRequestProcessBean getRequestDetails() {
		return requestDetails;
	}

	public void setRequestedInterestInstalments(int requestedInterestInstalments) {
		this.requestedInterestInstalments = requestedInterestInstalments;
	}

	public int getRequestedInterestInstalments() {
		return requestedInterestInstalments;
	}

	public void setSanctionedIntInstalments(int sanctionedIntInstalments) {
		this.sanctionedIntInstalments = sanctionedIntInstalments;
	}

	public int getSanctionedIntInstalments() {
		return sanctionedIntInstalments;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public float getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(float interestAmount) {
		this.interestAmount = interestAmount;
	}

	public float getInterestEmi() {
		return interestEmi;
	}

	public void setInterestEmi(float interestEmi) {
		this.interestEmi = interestEmi;
	}

	public float getInterestLastEmi() {
		return interestLastEmi;
	}

	public void setInterestLastEmi(float interestLastEmi) {
		this.interestLastEmi = interestLastEmi;
	}

	public void setLastEmi(float lastEmi) {
		this.lastEmi = lastEmi;
	}

	public float getLastEmi() {
		return lastEmi;
	}

}
