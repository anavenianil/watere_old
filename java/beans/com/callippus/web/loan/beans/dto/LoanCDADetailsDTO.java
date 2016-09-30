package com.callippus.web.loan.beans.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.requests.LoanRequestProcessBean;

public class LoanCDADetailsDTO extends CommonDTO {
	private int requestID;
	private String historyID;
	private String sfID;
	private String empName;
	private String sendingReportNumber;
	private Date sendingReportDate;
	private String hqReportNumber;
	private String srAccOfficer;
	private String accOfficer;
	private String cfaOfficer;
	private Date hqReportDate;
	private int hqReturnFlag;
	private int sanctionNo;
	private int billNo;
	private int dvNo;
	private int cdaAmount;
	private int cdaReturnFlag;
	private Date dvDate;
	private LoanRequestProcessBean loanRequestDetails;
	private String loanName;
	private int loanTypeID;

	public int getLoanTypeID() {
		return loanTypeID;
	}

	public void setLoanTypeID(int loanTypeID) {
		this.loanTypeID = loanTypeID;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getSendingReportNumber() {
		return sendingReportNumber;
	}

	public void setSendingReportNumber(String sendingReportNumber) {
		this.sendingReportNumber = sendingReportNumber;
	}

	public void setSendingReportDate(Date sendingReportDate) {
		this.sendingReportDate = sendingReportDate;
	}

	public Date getSendingReportDate() {
		return sendingReportDate;
	}

	public String getSrAccOfficer() {
		return srAccOfficer;
	}

	public void setSrAccOfficer(String srAccOfficer) {
		this.srAccOfficer = srAccOfficer;
	}

	public String getAccOfficer() {
		return accOfficer;
	}

	public void setAccOfficer(String accOfficer) {
		this.accOfficer = accOfficer;
	}

	public String getCfaOfficer() {
		return cfaOfficer;
	}

	public void setCfaOfficer(String cfaOfficer) {
		this.cfaOfficer = cfaOfficer;
	}

	public void setHqReturnFlag(int hqReturnFlag) {
		this.hqReturnFlag = hqReturnFlag;
	}

	public int getHqReturnFlag() {
		return hqReturnFlag;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getSfID() {
		return sfID;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpName() {
		return empName;
	}

	public int getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(int sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public int getDvNo() {
		return dvNo;
	}

	public void setDvNo(int dvNo) {
		this.dvNo = dvNo;
	}

	public int getCdaAmount() {
		return cdaAmount;
	}

	public void setCdaAmount(int cdaAmount) {
		this.cdaAmount = cdaAmount;
	}

	public int getCdaReturnFlag() {
		return cdaReturnFlag;
	}

	public void setCdaReturnFlag(int cdaReturnFlag) {
		this.cdaReturnFlag = cdaReturnFlag;
	}

	public Date getDvDate() {
		return dvDate;
	}

	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}

	public String getHqReportNumber() {
		return hqReportNumber;
	}

	public void setHqReportNumber(String hqReportNumber) {
		this.hqReportNumber = hqReportNumber;
	}

	public Date getHqReportDate() {
		return hqReportDate;
	}

	public void setHqReportDate(Date hqReportDate) {
		this.hqReportDate = hqReportDate;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getHistoryID() {
		return historyID;
	}

	public LoanRequestProcessBean getLoanRequestDetails() {
		return loanRequestDetails;
	}

	public void setLoanRequestDetails(LoanRequestProcessBean loanRequestDetails) {
		this.loanRequestDetails = loanRequestDetails;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

}