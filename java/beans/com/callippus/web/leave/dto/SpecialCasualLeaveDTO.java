package com.callippus.web.leave.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class SpecialCasualLeaveDTO extends CommonDTO {

	private String categoryType;
	private String leaveSubType;
	private String fromDate;
	private String toDate;
	private String eligibilityFlag;
	private String noOfDays;
	private String secondTimeRemarks;
	private String priorApprovalFlag;
	private String noticePeriodFlag;
	private String serviceBookFlag;
	private String doPartFlag;
	private String medicalCertFlag;
	private String otherCertFlag;
	private String otherFileName;
	private String strFromDate;
	private String strToDate;

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public String getEligibilityFlag() {
		return eligibilityFlag;
	}

	public void setEligibilityFlag(String eligibilityFlag) {
		this.eligibilityFlag = eligibilityFlag;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getLeaveSubType() {
		return leaveSubType;
	}

	public void setLeaveSubType(String leaveSubType) {
		this.leaveSubType = leaveSubType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getSecondTimeRemarks() {
		return secondTimeRemarks;
	}

	public void setSecondTimeRemarks(String secondTimeRemarks) {
		this.secondTimeRemarks = secondTimeRemarks;
	}

	public String getPriorApprovalFlag() {
		return priorApprovalFlag;
	}

	public void setPriorApprovalFlag(String priorApprovalFlag) {
		this.priorApprovalFlag = priorApprovalFlag;
	}

	public String getNoticePeriodFlag() {
		return noticePeriodFlag;
	}

	public void setNoticePeriodFlag(String noticePeriodFlag) {
		this.noticePeriodFlag = noticePeriodFlag;
	}

	public String getServiceBookFlag() {
		return serviceBookFlag;
	}

	public void setServiceBookFlag(String serviceBookFlag) {
		this.serviceBookFlag = serviceBookFlag;
	}

	public String getDoPartFlag() {
		return doPartFlag;
	}

	public void setDoPartFlag(String doPartFlag) {
		this.doPartFlag = doPartFlag;
	}

	public String getMedicalCertFlag() {
		return medicalCertFlag;
	}

	public void setMedicalCertFlag(String medicalCertFlag) {
		this.medicalCertFlag = medicalCertFlag;
	}

	public String getOtherCertFlag() {
		return otherCertFlag;
	}

	public void setOtherCertFlag(String otherCertFlag) {
		this.otherCertFlag = otherCertFlag;
	}

	public String getOtherFileName() {
		return otherFileName;
	}

	public void setOtherFileName(String otherFileName) {
		this.otherFileName = otherFileName;
	}

}