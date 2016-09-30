package com.callippus.web.leave.beans.management;

import java.util.List;

import javax.xml.ws.BindingType;

import net.sf.json.JSON;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.leave.dto.LeaveConversionMappingsDTO;
import com.callippus.web.leave.dto.LeaveDurationDTO;
import com.callippus.web.leave.dto.LeaveEncashmentDTO;
import com.callippus.web.leave.dto.LeaveExceptionDetailsDTO;
import com.callippus.web.leave.dto.LeaveFamilyDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;

@BindingType
public class LeaveManagementBean extends CommonDTO {
	private LeaveTypeDTO leaveTypeDetails;
	private String eligibilityFlag;
	private String priorApprovalFlag;
	private String holidayIntFlag;
	private String halfDayFlag;
	private String noticePeriodFlag;
	private String viewFlag;
	private String maxLeaves;
	private String offlineFlag;
	private String availWOLeaves;
	private String directDebitFlag;
	private String debitMappingID;
	private String debitMappingLeaves;
	private String medicalCertFlag;
	private String fitnessCertFlag;
	private String otherCertFlag;
	private String certificateName;
	private String otherCreditCheck;
	private String leaveCancellation;
	private String leaveConversion;
	private String doPartFlag;
	private String serviceBookFlag;
	private String familyImpactFlag;
	private String encashmentFlag;
	private String leaveCredits;
	private LeaveDurationDTO leaveDurationDetails;
	private String noOfDays;
	private String spellsInService;
	private String spellsPerYear;
	private String minPerSpell;
	private String maxPerSPell;
	private String ltcAvailFlag;
	private String maxShownFlag;
	private String param;
	private String type;
	private List<LeaveTypeDTO> leaveTypeList;
	private String leaveTypeId;
	private String leaveConversionType;
	private String exceptionsJson;
	private String leaveDurationID;
	private JSON exceptionMasterDetailsJSON;
	private List<LeaveExceptionDetailsDTO> exceptionList;
	private List<LeaveConversionMappingsDTO> conversionList;
	private String servivingChild;
	private String childAgeLimit;
	private String phChildAgeLimit;
	private String noOfDaysService;
	private String noOfSpellsYear;
	private String minDaysInSpell;
	private String maxDaysEncashInSPell;
	private String minDaysAfterEncash;
	private String sfid;
	private List<LeaveDurationDTO> leaveDurationList;
	private LeaveFamilyDTO LeaveFamilyDTO;
	private LeaveEncashmentDTO leaveEncashmentDTO;
	private String categoryType;
	private String leaveSubType;
	private String fromDate;
	private String toDate;
	private String secondTimeRemarks;
	private String otherFileName;
	private String strFromDate;
	private String strToDate;
	private String specialLeaveDesc;
	private LeaveRequestBean leaveRequestDetails;
	private String newEmpAvailLeaves;
	private List<KeyValueDTO> leaveSubTypeList;

	private String experience;
	private String currentDate;
	private String systemDate;

	public String getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public List<KeyValueDTO> getLeaveSubTypeList() {
		return leaveSubTypeList;
	}

	public void setLeaveSubTypeList(List<KeyValueDTO> leaveSubTypeList) {
		this.leaveSubTypeList = leaveSubTypeList;
	}

	public String getNewEmpAvailLeaves() {
		return newEmpAvailLeaves;
	}

	public void setNewEmpAvailLeaves(String newEmpAvailLeaves) {
		this.newEmpAvailLeaves = newEmpAvailLeaves;
	}

	public LeaveRequestBean getLeaveRequestDetails() {
		return leaveRequestDetails;
	}

	public void setLeaveRequestDetails(LeaveRequestBean leaveRequestDetails) {
		this.leaveRequestDetails = leaveRequestDetails;
	}

	public String getSpecialLeaveDesc() {
		return specialLeaveDesc;
	}

	public void setSpecialLeaveDesc(String specialLeaveDesc) {
		this.specialLeaveDesc = specialLeaveDesc;
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

	public String getSecondTimeRemarks() {
		return secondTimeRemarks;
	}

	public void setSecondTimeRemarks(String secondTimeRemarks) {
		this.secondTimeRemarks = secondTimeRemarks;
	}

	public String getOtherFileName() {
		return otherFileName;
	}

	public void setOtherFileName(String otherFileName) {
		this.otherFileName = otherFileName;
	}

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

	public LeaveFamilyDTO getLeaveFamilyDTO() {
		return LeaveFamilyDTO;
	}

	public void setLeaveFamilyDTO(LeaveFamilyDTO leaveFamilyDTO) {
		LeaveFamilyDTO = leaveFamilyDTO;
	}

	public LeaveEncashmentDTO getLeaveEncashmentDTO() {
		return leaveEncashmentDTO;
	}

	public void setLeaveEncashmentDTO(LeaveEncashmentDTO leaveEncashmentDTO) {
		this.leaveEncashmentDTO = leaveEncashmentDTO;
	}

	public List<LeaveDurationDTO> getLeaveDurationList() {
		return leaveDurationList;
	}

	public void setLeaveDurationList(List<LeaveDurationDTO> leaveDurationList) {
		this.leaveDurationList = leaveDurationList;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getNoOfDaysService() {
		return noOfDaysService;
	}

	public void setNoOfDaysService(String noOfDaysService) {
		this.noOfDaysService = noOfDaysService;
	}

	public String getNoOfSpellsYear() {
		return noOfSpellsYear;
	}

	public void setNoOfSpellsYear(String noOfSpellsYear) {
		this.noOfSpellsYear = noOfSpellsYear;
	}

	public String getMinDaysInSpell() {
		return minDaysInSpell;
	}

	public void setMinDaysInSpell(String minDaysInSpell) {
		this.minDaysInSpell = minDaysInSpell;
	}

	public String getMaxDaysEncashInSPell() {
		return maxDaysEncashInSPell;
	}

	public void setMaxDaysEncashInSPell(String maxDaysEncashInSPell) {
		this.maxDaysEncashInSPell = maxDaysEncashInSPell;
	}

	public String getMinDaysAfterEncash() {
		return minDaysAfterEncash;
	}

	public void setMinDaysAfterEncash(String minDaysAfterEncash) {
		this.minDaysAfterEncash = minDaysAfterEncash;
	}

	public String getServivingChild() {
		return servivingChild;
	}

	public void setServivingChild(String servivingChild) {
		this.servivingChild = servivingChild;
	}

	public String getChildAgeLimit() {
		return childAgeLimit;
	}

	public void setChildAgeLimit(String childAgeLimit) {
		this.childAgeLimit = childAgeLimit;
	}

	public String getPhChildAgeLimit() {
		return phChildAgeLimit;
	}

	public void setPhChildAgeLimit(String phChildAgeLimit) {
		this.phChildAgeLimit = phChildAgeLimit;
	}

	public List<LeaveConversionMappingsDTO> getConversionList() {
		return conversionList;
	}

	public void setConversionList(List<LeaveConversionMappingsDTO> conversionList) {
		this.conversionList = conversionList;
	}

	public List<LeaveExceptionDetailsDTO> getExceptionList() {
		return exceptionList;
	}

	public void setExceptionList(List<LeaveExceptionDetailsDTO> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public JSON getExceptionMasterDetailsJSON() {
		return exceptionMasterDetailsJSON;
	}

	public void setExceptionMasterDetailsJSON(JSON exceptionMasterDetailsJSON) {
		this.exceptionMasterDetailsJSON = exceptionMasterDetailsJSON;
	}

	public String getLeaveDurationID() {
		return leaveDurationID;
	}

	public void setLeaveDurationID(String leaveDurationID) {
		this.leaveDurationID = leaveDurationID;
	}

	public String getExceptionsJson() {
		return exceptionsJson;
	}

	public void setExceptionsJson(String exceptionsJson) {
		this.exceptionsJson = exceptionsJson;
	}

	public String getLeaveConversionType() {
		return leaveConversionType;
	}

	public void setLeaveConversionType(String leaveConversionType) {
		this.leaveConversionType = leaveConversionType;
	}

	public String getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<LeaveTypeDTO> getLeaveTypeList() {
		return leaveTypeList;
	}

	public void setLeaveTypeList(List<LeaveTypeDTO> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}

	public String getMaxShownFlag() {
		return maxShownFlag;
	}

	public void setMaxShownFlag(String maxShownFlag) {
		this.maxShownFlag = maxShownFlag;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

	public String getEligibilityFlag() {
		return eligibilityFlag;
	}

	public void setEligibilityFlag(String eligibilityFlag) {
		this.eligibilityFlag = eligibilityFlag;
	}

	public String getPriorApprovalFlag() {
		return priorApprovalFlag;
	}

	public void setPriorApprovalFlag(String priorApprovalFlag) {
		this.priorApprovalFlag = priorApprovalFlag;
	}

	public String getHolidayIntFlag() {
		return holidayIntFlag;
	}

	public void setHolidayIntFlag(String holidayIntFlag) {
		this.holidayIntFlag = holidayIntFlag;
	}

	public String getHalfDayFlag() {
		return halfDayFlag;
	}

	public void setHalfDayFlag(String halfDayFlag) {
		this.halfDayFlag = halfDayFlag;
	}

	public String getNoticePeriodFlag() {
		return noticePeriodFlag;
	}

	public void setNoticePeriodFlag(String noticePeriodFlag) {
		this.noticePeriodFlag = noticePeriodFlag;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getMaxLeaves() {
		return maxLeaves;
	}

	public void setMaxLeaves(String maxLeaves) {
		this.maxLeaves = maxLeaves;
	}

	public String getOfflineFlag() {
		return offlineFlag;
	}

	public void setOfflineFlag(String offlineFlag) {
		this.offlineFlag = offlineFlag;
	}

	public String getAvailWOLeaves() {
		return availWOLeaves;
	}

	public void setAvailWOLeaves(String availWOLeaves) {
		this.availWOLeaves = availWOLeaves;
	}

	public String getDirectDebitFlag() {
		return directDebitFlag;
	}

	public void setDirectDebitFlag(String directDebitFlag) {
		this.directDebitFlag = directDebitFlag;
	}

	public String getDebitMappingID() {
		return debitMappingID;
	}

	public void setDebitMappingID(String debitMappingID) {
		this.debitMappingID = debitMappingID;
	}

	public String getDebitMappingLeaves() {
		return debitMappingLeaves;
	}

	public void setDebitMappingLeaves(String debitMappingLeaves) {
		this.debitMappingLeaves = debitMappingLeaves;
	}

	public String getMedicalCertFlag() {
		return medicalCertFlag;
	}

	public void setMedicalCertFlag(String medicalCertFlag) {
		this.medicalCertFlag = medicalCertFlag;
	}

	public String getFitnessCertFlag() {
		return fitnessCertFlag;
	}

	public void setFitnessCertFlag(String fitnessCertFlag) {
		this.fitnessCertFlag = fitnessCertFlag;
	}

	public String getOtherCertFlag() {
		return otherCertFlag;
	}

	public void setOtherCertFlag(String otherCertFlag) {
		this.otherCertFlag = otherCertFlag;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getOtherCreditCheck() {
		return otherCreditCheck;
	}

	public void setOtherCreditCheck(String otherCreditCheck) {
		this.otherCreditCheck = otherCreditCheck;
	}

	public String getLeaveCancellation() {
		return leaveCancellation;
	}

	public void setLeaveCancellation(String leaveCancellation) {
		this.leaveCancellation = leaveCancellation;
	}

	public String getLeaveConversion() {
		return leaveConversion;
	}

	public void setLeaveConversion(String leaveConversion) {
		this.leaveConversion = leaveConversion;
	}

	public String getDoPartFlag() {
		return doPartFlag;
	}

	public void setDoPartFlag(String doPartFlag) {
		this.doPartFlag = doPartFlag;
	}

	public String getServiceBookFlag() {
		return serviceBookFlag;
	}

	public void setServiceBookFlag(String serviceBookFlag) {
		this.serviceBookFlag = serviceBookFlag;
	}

	public String getFamilyImpactFlag() {
		return familyImpactFlag;
	}

	public void setFamilyImpactFlag(String familyImpactFlag) {
		this.familyImpactFlag = familyImpactFlag;
	}

	public String getEncashmentFlag() {
		return encashmentFlag;
	}

	public void setEncashmentFlag(String encashmentFlag) {
		this.encashmentFlag = encashmentFlag;
	}

	public String getLeaveCredits() {
		return leaveCredits;
	}

	public void setLeaveCredits(String leaveCredits) {
		this.leaveCredits = leaveCredits;
	}

	public LeaveDurationDTO getLeaveDurationDetails() {
		return leaveDurationDetails;
	}

	public void setLeaveDurationDetails(LeaveDurationDTO leaveDurationDetails) {
		this.leaveDurationDetails = leaveDurationDetails;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getSpellsInService() {
		return spellsInService;
	}

	public void setSpellsInService(String spellsInService) {
		this.spellsInService = spellsInService;
	}

	public String getSpellsPerYear() {
		return spellsPerYear;
	}

	public void setSpellsPerYear(String spellsPerYear) {
		this.spellsPerYear = spellsPerYear;
	}

	public String getMinPerSpell() {
		return minPerSpell;
	}

	public void setMinPerSpell(String minPerSpell) {
		this.minPerSpell = minPerSpell;
	}

	public String getMaxPerSPell() {
		return maxPerSPell;
	}

	public void setMaxPerSPell(String maxPerSPell) {
		this.maxPerSPell = maxPerSPell;
	}

	public String getLtcAvailFlag() {
		return ltcAvailFlag;
	}

	public void setLtcAvailFlag(String ltcAvailFlag) {
		this.ltcAvailFlag = ltcAvailFlag;
	}

}