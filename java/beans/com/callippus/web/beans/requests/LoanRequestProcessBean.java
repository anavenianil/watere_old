package com.callippus.web.beans.requests;

import java.util.Date;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.EmploymentDTO;

public class LoanRequestProcessBean extends RequestBean {
	private int id;
	private String loanSubType;
	private String temporaryDesc;
	private String prevLoanFlag;
	private String prevLoanName;
	private String prevLoanRecFlag;
	private String currentPositionFlag;
	private String loanType;
	private String circumstance;
	private String reqAmount;
	private String prevLoanDetails;
	private float anticipatedAmount;
	private int requestedInstalments;
	private int requestedInterestInstalments;
	private Date prevAdvanceDate;
	private String commitmentFlag;
	private String declarationFlag;
	private String negotiationFlag;
	private Date leaveToDate;
	private Date leaveFromDate;
	private String intensionRuleFlag;
	private String intensionFlag;
	private DesignationDTO designationDetails;
	private DepartmentsDTO departmentDetails;
	private int employeeType;
	private float basicPay;
	private float gradePay;
	private float da;
	private Date dateOfDrawl;
	private float prevOutAmt;
	private String sourceType;
	private int departmentID;
	private float gpfLoanBalance;
	private String reason;
	private EmploymentDTO employmentDetails;

	public EmploymentDTO getEmploymentDetails() {
		return employmentDetails;
	}

	public void setEmploymentDetails(EmploymentDTO employmentDetails) {
		this.employmentDetails = employmentDetails;
	}

	public float getGpfLoanBalance() {
		return gpfLoanBalance;
	}

	public void setGpfLoanBalance(float gpfLoanBalance) {
		this.gpfLoanBalance = gpfLoanBalance;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getLeaveToDate() {
		return leaveToDate;
	}

	public void setLeaveToDate(Date leaveToDate) {
		this.leaveToDate = leaveToDate;
	}

	public Date getLeaveFromDate() {
		return leaveFromDate;
	}

	public void setLeaveFromDate(Date leaveFromDate) {
		this.leaveFromDate = leaveFromDate;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}

	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}

	public DepartmentsDTO getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentsDTO departmentDetails) {
		this.departmentDetails = departmentDetails;
	}

	public int getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(int employeeType) {
		this.employeeType = employeeType;
	}

	public float getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(float basicPay) {
		this.basicPay = basicPay;
	}

	public float getGradePay() {
		return gradePay;
	}

	public void setGradePay(float gradePay) {
		this.gradePay = gradePay;
	}

	public float getDa() {
		return da;
	}

	public void setDa(float da) {
		this.da = da;
	}

	public Date getDateOfDrawl() {
		return dateOfDrawl;
	}

	public void setDateOfDrawl(Date dateOfDrawl) {
		this.dateOfDrawl = dateOfDrawl;
	}

	public float getPrevOutAmt() {
		return prevOutAmt;
	}

	public void setPrevOutAmt(float prevOutAmt) {
		this.prevOutAmt = prevOutAmt;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoanSubType() {
		return loanSubType;
	}

	public void setLoanSubType(String loanSubType) {
		this.loanSubType = loanSubType;
	}

	public String getTemporaryDesc() {
		return temporaryDesc;
	}

	public void setTemporaryDesc(String temporaryDesc) {
		this.temporaryDesc = temporaryDesc;
	}

	public String getPrevLoanFlag() {
		return prevLoanFlag;
	}

	public void setPrevLoanFlag(String prevLoanFlag) {
		this.prevLoanFlag = prevLoanFlag;
	}

	public String getPrevLoanName() {
		return prevLoanName;
	}

	public void setPrevLoanName(String prevLoanName) {
		this.prevLoanName = prevLoanName;
	}

	public String getPrevLoanRecFlag() {
		return prevLoanRecFlag;
	}

	public void setPrevLoanRecFlag(String prevLoanRecFlag) {
		this.prevLoanRecFlag = prevLoanRecFlag;
	}

	public String getCurrentPositionFlag() {
		return currentPositionFlag;
	}

	public void setCurrentPositionFlag(String currentPositionFlag) {
		this.currentPositionFlag = currentPositionFlag;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getCircumstance() {
		return circumstance;
	}

	public void setCircumstance(String circumstance) {
		this.circumstance = circumstance;
	}

	public String getReqAmount() {
		return reqAmount;
	}

	public void setReqAmount(String reqAmount) {
		this.reqAmount = reqAmount;
	}

	public String getPrevLoanDetails() {
		return prevLoanDetails;
	}

	public void setPrevLoanDetails(String prevLoanDetails) {
		this.prevLoanDetails = prevLoanDetails;
	}

	public float getAnticipatedAmount() {
		return anticipatedAmount;
	}

	public void setAnticipatedAmount(float anticipatedAmount) {
		this.anticipatedAmount = anticipatedAmount;
	}

	public int getRequestedInstalments() {
		return requestedInstalments;
	}

	public void setRequestedInstalments(int requestedInstalments) {
		this.requestedInstalments = requestedInstalments;
	}

	public String getCommitmentFlag() {
		return commitmentFlag;
	}

	public void setCommitmentFlag(String commitmentFlag) {
		this.commitmentFlag = commitmentFlag;
	}

	public String getDeclarationFlag() {
		return declarationFlag;
	}

	public void setDeclarationFlag(String declarationFlag) {
		this.declarationFlag = declarationFlag;
	}

	public String getNegotiationFlag() {
		return negotiationFlag;
	}

	public void setNegotiationFlag(String negotiationFlag) {
		this.negotiationFlag = negotiationFlag;
	}

	public String getIntensionRuleFlag() {
		return intensionRuleFlag;
	}

	public void setIntensionRuleFlag(String intensionRuleFlag) {
		this.intensionRuleFlag = intensionRuleFlag;
	}

	public String getIntensionFlag() {
		return intensionFlag;
	}

	public void setIntensionFlag(String intensionFlag) {
		this.intensionFlag = intensionFlag;
	}

	public void setRequestedInterestInstalments(int requestedInterestInstalments) {
		this.requestedInterestInstalments = requestedInterestInstalments;
	}

	public int getRequestedInterestInstalments() {
		return requestedInterestInstalments;
	}

	public void setPrevAdvanceDate(Date prevAdvanceDate) {
		this.prevAdvanceDate = prevAdvanceDate;
	}

	public Date getPrevAdvanceDate() {
		return prevAdvanceDate;
	}

}
