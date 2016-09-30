package com.callippus.web.beans.dto;

import java.util.Date;

public class AllowanceEmpDetailsDTO {
	private int allowanceId;
	private String allowanceFor;
	private String allowanceName;
	private int allowanceAmount;
	private Date approvedDate;
	private String approvedBy;
	private String sfid;
	private String designationId;
	private int departmentId;
	private int basicPay;
	private int gradePay;
	private Date effectFromDate;
	private Date effectToDate;
	private int status;
	private String ipAddress;
	
	public Date getEffectFromDate() {
		return effectFromDate;
	}
	public void setEffectFromDate(Date effectFromDate) {
		this.effectFromDate = effectFromDate;
	}
	public Date getEffectToDate() {
		return effectToDate;
	}
	public void setEffectToDate(Date effectToDate) {
		this.effectToDate = effectToDate;
	}
	public int getAllowanceId() {
		return allowanceId;
	}
	public void setAllowanceId(int allowanceId) {
		this.allowanceId = allowanceId;
	}
	public String getAllowanceFor() {
		return allowanceFor;
	}
	public void setAllowanceFor(String allowanceFor) {
		this.allowanceFor = allowanceFor;
	}
	public String getAllowanceName() {
		return allowanceName;
	}
	public void setAllowanceName(String allowanceName) {
		this.allowanceName = allowanceName;
	}
	public int getAllowanceAmount() {
		return allowanceAmount;
	}
	public void setAllowanceAmount(int allowanceAmount) {
		this.allowanceAmount = allowanceAmount;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(int basicPay) {
		this.basicPay = basicPay;
	}
	public int getGradePay() {
		return gradePay;
	}
	public void setGradePay(int gradePay) {
		this.gradePay = gradePay;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
