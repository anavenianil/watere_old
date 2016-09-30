package com.callippus.web.beans.passport;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.ProceedingTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.RequestBean;

public class PassportRequestProcessBean extends RequestBean{
	
	private String type;
	
	private String passportType;
	private String passportNumber;
	private String issuePlace;
	private String validUpto;
	private String remarks;
	private int status;
	private String familyMember;
	private String passPortId;
	private EmployeeBean empDetails;
	private List<ProceedingTypeDTO> proceedingList;
	private List<FamilyBean> familyMemberDetails;
	private Date departureDate;
	private String relations;
	private String employmentDetails;
	private String vigilanceFlag;
	private String passportPossessFlag;
	private Date issueDate;
	private Date validityDate;
	private Date returnDate;
	private String countriesToVisit;
	private String purpose;
	private String duration;
	private String familyMembersFlag;
	private String familyMemberId;
	private String spendingAmount;
	private String selfFinanceFlag;
	private String sourceOfAmount;
	private String lenderName;
	private String nationality;
	private String lenderRelationship;
	private String ipAddress;
	private String result;
	private String sfID;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassportType() {
		return passportType;
	}
	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getIssuePlace() {
		return issuePlace;
	}
	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}
	public String getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFamilyMember() {
		return familyMember;
	}
	public void setFamilyMember(String familyMember) {
		this.familyMember = familyMember;
	}
	public String getPassPortId() {
		return passPortId;
	}
	public void setPassPortId(String passPortId) {
		this.passPortId = passPortId;
	}
	public EmployeeBean getEmpDetails() {
		return empDetails;
	}
	public void setEmpDetails(EmployeeBean empDetails) {
		this.empDetails = empDetails;
	}
	public List<ProceedingTypeDTO> getProceedingList() {
		return proceedingList;
	}
	public void setProceedingList(List<ProceedingTypeDTO> proceedingList) {
		this.proceedingList = proceedingList;
	}
	public List<FamilyBean> getFamilyMemberDetails() {
		return familyMemberDetails;
	}
	public void setFamilyMemberDetails(List<FamilyBean> familyMemberDetails) {
		this.familyMemberDetails = familyMemberDetails;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public String getRelations() {
		return relations;
	}
	public void setRelations(String relations) {
		this.relations = relations;
	}
	public String getEmploymentDetails() {
		return employmentDetails;
	}
	public void setEmploymentDetails(String employmentDetails) {
		this.employmentDetails = employmentDetails;
	}
	public String getVigilanceFlag() {
		return vigilanceFlag;
	}
	public void setVigilanceFlag(String vigilanceFlag) {
		this.vigilanceFlag = vigilanceFlag;
	}
	public String getPassportPossessFlag() {
		return passportPossessFlag;
	}
	public void setPassportPossessFlag(String passportPossessFlag) {
		this.passportPossessFlag = passportPossessFlag;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getCountriesToVisit() {
		return countriesToVisit;
	}
	public void setCountriesToVisit(String countriesToVisit) {
		this.countriesToVisit = countriesToVisit;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getFamilyMembersFlag() {
		return familyMembersFlag;
	}
	public void setFamilyMembersFlag(String familyMembersFlag) {
		this.familyMembersFlag = familyMembersFlag;
	}
	public String getFamilyMemberId() {
		return familyMemberId;
	}
	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}
	public String getSpendingAmount() {
		return spendingAmount;
	}
	public void setSpendingAmount(String spendingAmount) {
		this.spendingAmount = spendingAmount;
	}
	public String getSelfFinanceFlag() {
		return selfFinanceFlag;
	}
	public void setSelfFinanceFlag(String selfFinanceFlag) {
		this.selfFinanceFlag = selfFinanceFlag;
	}
	public String getSourceOfAmount() {
		return sourceOfAmount;
	}
	public void setSourceOfAmount(String sourceOfAmount) {
		this.sourceOfAmount = sourceOfAmount;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getLenderRelationship() {
		return lenderRelationship;
	}
	public void setLenderRelationship(String lenderRelationship) {
		this.lenderRelationship = lenderRelationship;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

}
