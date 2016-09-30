package com.callippus.web.mmg.cashbuildup.dto;

import com.callippus.web.beans.dto.CommonDTO;


public class AccountHeadDTO extends CommonDTO{
	private String accId;
	private String accountHeadNumber;
	private String qtyHeld;
	private String qtyRequired;
	private String allottedFund;
	private String year;
	private String fundTypeId;
	private String departmentId;
	private String consumedFund;
	private String commitedFund;
	private String deptName;
	private String fundTypeName;	
	
	
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getFundTypeName() {
		return fundTypeName;
	}
	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}
	public String getAccountHeadNumber() {
		return accountHeadNumber;
	}
	public void setAccountHeadNumber(String accountHeadNumber) {
		this.accountHeadNumber = accountHeadNumber;
	}
	public String getQtyHeld() {
		return qtyHeld;
	}
	public void setQtyHeld(String qtyHeld) {
		this.qtyHeld = qtyHeld;
	}
	public String getQtyRequired() {
		return qtyRequired;
	}
	public void setQtyRequired(String qtyRequired) {
		this.qtyRequired = qtyRequired;
	}
	public String getAllottedFund() {
		return allottedFund;
	}
	public void setAllottedFund(String allottedFund) {
		this.allottedFund = allottedFund;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getFundTypeId() {
		return fundTypeId;
	}
	public void setFundTypeId(String fundTypeId) {
		this.fundTypeId = fundTypeId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getConsumedFund() {
		return consumedFund;
	}
	public void setConsumedFund(String consumedFund) {
		this.consumedFund = consumedFund;
	}
	public String getCommitedFund() {
		return commitedFund;
	}
	public void setCommitedFund(String commitedFund) {
		this.commitedFund = commitedFund;
	}
	
	
}
