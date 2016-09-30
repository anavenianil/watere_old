package com.callippus.web.beans.requests;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

public class LeaveRequestBean extends RequestBean {
	private int id;
	private String departmentID;
	private String internalNo;
	private String designationID;
	private String contactNumber;
	private LeaveTypeDTO leaveTypeDetails;
	private SpecialCasualLeaveDTO leaveSubTypeDetails;
	private String fromDate;
	private String toDate;
	private String fromHalfDayFlag;
	private String toHalfDayFlag;
	private String noOfDays;
	private String prefix;
	private String suffix;
	private String reason;
	private String address;
	private String medicalCertName;
	private String fitnessCertName;
	private String otherCertName;
	private String requestedBy;
	private Date requestedDate;
	private String additionalData;
	private Map<String, String> employeeDetails;
	private HttpSession session;
	private String leaveRequestType;
	private LeaveManagementBean selectedLeaveDetails;
	private String leaveType;
	private String leaveSubType;
	private String exceptionalDetails;
	private String result;
	private String otherRemarks;
	private Date formattedFromDate;
	private Date formattedToDate;
	private Date formattedRequestedDate;
	private DepartmentsDTO departmentDetails;
	private DoPartBean doPartDetails;
	private int debitFrom;
	private String debitLeaves;
	private int referenceID;
	private String referenceId;
	private String leaveTypeID;
	private String statusMsg;
	private String convertLeaves;
	private String serviceBookFlag;
	private String leaveConversion;
	private String leaveCancellation;
	private String strToDate;
	private String prevHolidays;
	private String nextHolidays;
	private String debitCode;
	private String designationName;
	private String requestStage;
	private String leaveCode;
	private String strFromDate;
	private float previousPeriodDebit;
	private float presentPeriodDebit;
	private float nextPeriodDebit;
	private String convertRefID;
	private ConvertLeaveRequestBean convertedLeaveDetails;
	private String convertionID;
	
	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getConvertionID() {
		return convertionID;
	}

	public void setConvertionID(String convertionID) {
		this.convertionID = convertionID;
	}

	public float getPreviousPeriodDebit() {
		return previousPeriodDebit;
	}

	public void setPreviousPeriodDebit(float previousPeriodDebit) {
		this.previousPeriodDebit = previousPeriodDebit;
	}

	public float getPresentPeriodDebit() {
		return presentPeriodDebit;
	}

	public void setPresentPeriodDebit(float presentPeriodDebit) {
		this.presentPeriodDebit = presentPeriodDebit;
	}

	public float getNextPeriodDebit() {
		return nextPeriodDebit;
	}

	public void setNextPeriodDebit(float nextPeriodDebit) {
		this.nextPeriodDebit = nextPeriodDebit;
	}

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getLeaveCode() {
		return leaveCode;
	}

	public void setLeaveCode(String leaveCode) {
		this.leaveCode = leaveCode;
	}

	public String getRequestStage() {
		return requestStage;
	}

	public void setRequestStage(String requestStage) {
		this.requestStage = requestStage;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDebitCode() {
		return debitCode;
	}

	public void setDebitCode(String debitCode) {
		this.debitCode = debitCode;
	}

	public String getPrevHolidays() {
		return prevHolidays;
	}

	public void setPrevHolidays(String prevHolidays) {
		this.prevHolidays = prevHolidays;
	}

	public String getNextHolidays() {
		return nextHolidays;
	}

	public void setNextHolidays(String nextHolidays) {
		this.nextHolidays = nextHolidays;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public String getLeaveConversion() {
		return leaveConversion;
	}

	public void setLeaveConversion(String leaveConversion) {
		this.leaveConversion = leaveConversion;
	}

	public String getLeaveCancellation() {
		return leaveCancellation;
	}

	public void setLeaveCancellation(String leaveCancellation) {
		this.leaveCancellation = leaveCancellation;
	}

	public String getServiceBookFlag() {
		return serviceBookFlag;
	}

	public void setServiceBookFlag(String serviceBookFlag) {
		this.serviceBookFlag = serviceBookFlag;
	}

	public String getConvertLeaves() {
		return convertLeaves;
	}

	public void setConvertLeaves(String convertLeaves) {
		this.convertLeaves = convertLeaves;
	}

	public String getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(String leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getDebitFrom() {
		return debitFrom;
	}

	public void setDebitFrom(int debitFrom) {
		this.debitFrom = debitFrom;
	}

	public String getDebitLeaves() {
		return debitLeaves;
	}

	public void setDebitLeaves(String debitLeaves) {
		this.debitLeaves = debitLeaves;
	}

	public DepartmentsDTO getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentsDTO departmentDetails) {
		this.departmentDetails = departmentDetails;
	}

	public DoPartBean getDoPartDetails() {
		return doPartDetails;
	}

	public void setDoPartDetails(DoPartBean doPartDetails) {
		this.doPartDetails = doPartDetails;
	}

	public Date getFormattedRequestedDate() {
		return formattedRequestedDate;
	}

	public void setFormattedRequestedDate(Date formattedRequestedDate) {
		this.formattedRequestedDate = formattedRequestedDate;
	}

	public Date getFormattedFromDate() {
		return formattedFromDate;
	}

	public void setFormattedFromDate(Date formattedFromDate) {
		this.formattedFromDate = formattedFromDate;
	}

	public Date getFormattedToDate() {
		return formattedToDate;
	}

	public void setFormattedToDate(Date formattedToDate) {
		this.formattedToDate = formattedToDate;
	}

	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExceptionalDetails() {
		return exceptionalDetails;
	}

	public void setExceptionalDetails(String exceptionalDetails) {
		this.exceptionalDetails = exceptionalDetails;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveSubType() {
		return leaveSubType;
	}

	public void setLeaveSubType(String leaveSubType) {
		this.leaveSubType = leaveSubType;
	}

	public LeaveManagementBean getSelectedLeaveDetails() {
		return selectedLeaveDetails;
	}

	public void setSelectedLeaveDetails(LeaveManagementBean selectedLeaveDetails) {
		this.selectedLeaveDetails = selectedLeaveDetails;
	}

	public String getLeaveRequestType() {
		return leaveRequestType;
	}

	public void setLeaveRequestType(String leaveRequestType) {
		this.leaveRequestType = leaveRequestType;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Map<String, String> getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(Map<String, String> employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getInternalNo() {
		return internalNo;
	}

	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
	}

	public String getDesignationID() {
		return designationID;
	}

	public void setDesignationID(String designationID) {
		this.designationID = designationID;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

	public SpecialCasualLeaveDTO getLeaveSubTypeDetails() {
		return leaveSubTypeDetails;
	}

	public void setLeaveSubTypeDetails(SpecialCasualLeaveDTO leaveSubTypeDetails) {
		this.leaveSubTypeDetails = leaveSubTypeDetails;
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

	public String getFromHalfDayFlag() {
		return fromHalfDayFlag;
	}

	public void setFromHalfDayFlag(String fromHalfDayFlag) {
		this.fromHalfDayFlag = fromHalfDayFlag;
	}

	public String getToHalfDayFlag() {
		return toHalfDayFlag;
	}

	public void setToHalfDayFlag(String toHalfDayFlag) {
		this.toHalfDayFlag = toHalfDayFlag;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMedicalCertName() {
		return medicalCertName;
	}

	public void setMedicalCertName(String medicalCertName) {
		this.medicalCertName = medicalCertName;
	}

	public String getFitnessCertName() {
		return fitnessCertName;
	}

	public void setFitnessCertName(String fitnessCertName) {
		this.fitnessCertName = fitnessCertName;
	}

	public String getOtherCertName() {
		return otherCertName;
	}

	public void setOtherCertName(String otherCertName) {
		this.otherCertName = otherCertName;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public void setConvertedLeaveDetails(ConvertLeaveRequestBean convertedLeaveDetails) {
		this.convertedLeaveDetails = convertedLeaveDetails;
	}

	public ConvertLeaveRequestBean getConvertedLeaveDetails() {
		return convertedLeaveDetails;
	}

	public void setConvertRefID(String convertRefID) {
		this.convertRefID = convertRefID;
	}

	public String getConvertRefID() {
		return convertRefID;
	}

}
