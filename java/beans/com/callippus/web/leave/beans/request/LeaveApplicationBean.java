package com.callippus.web.leave.beans.request;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.dto.AvailableLeavesDTO;
import com.callippus.web.leave.dto.LeaveCardDTO;
import com.callippus.web.leave.dto.LeaveExceptionDetailsDTO;
import com.callippus.web.leave.dto.LeaveFamilyDTO;
import com.callippus.web.leave.dto.LeaveRequestExceptionsDTO;
import com.callippus.web.leave.dto.LeaveTxnDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.leave.dto.LeaveYearlyBalanceDTO;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

public class LeaveApplicationBean {
	private int id;
	private String requestID;
	private String requestId;
	private String referenceId;

	private String sfID;
	private String internalNo;
	private String contactNumber;
	private LeaveTypeDTO leaveTypeDetails;
	private String eligibility;
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
	private int status;

	private String param;
	private String type;
	private List<LeaveManagementBean> leaveDetailsList;
	private List<FamilyBean> familyMembersList;
	private List<LeaveRequestBean> appliedLeavesList;
	private List<LeaveRequestBean> cancelledLeavesList;
	private List<LeaveRequestBean> convertedLeavesList;

	private List<LeaveFamilyDTO> leaveFamilyImpactDetails;
	private List<LeaveExceptionDetailsDTO> exceptionDetailsList;

	private String leaveType;
	private String leaveSubType;
	private String childName;
	private String studyDegree;
	private String deliveryDate;
	private String selectAddress;
	private MultipartFile mcFile;
	private MultipartFile fitnessFile;
	private MultipartFile otherCertFile;

	private String selectedDate;
	private String offlineSFID;

	private String message;
	private String result;
	private String remarks;
	private String noOfLeaves;
	private String additionalData;
	private String currentDate;
	private List<AvailableLeavesDTO> leaveCreditsList;
	private String empGender;
	private List<SpecialCasualLeaveDTO> specialCasualList;

	private String date;
	private String dateType;
	private String startDate;
	private String holidays;
	private LeaveManagementBean selectedLeaveDetails;
	private String remarksNote;
	private String appliedBy;
	private String leaveRequestType;
	private String exceptionalDetails;
	private SpecialCasualLeaveDTO seletedSpecialLeaveDetails;
	private String otherRemarks;
	private LeaveRequestBean referenceLeaveDetails;
	private String menuFlag;

	private List<LeaveCardDTO> casualLeaveCard;
	private List<LeaveCardDTO> earnedLeaveCard;
	private List<LeaveCardDTO> halfPayLeaveCard;
	private List<LeaveCardDTO> leaveNotDueLeaveCard;
	private List<LeaveCardDTO> childCareLeaveCard;
	private List<LeaveCardDTO> otherLeaveCard;
	private List<LeaveCardDTO> specialCasualLeaveCard;

	private String gazType;
	private String currentYear;
	private List<AvailableLeavesDTO> leaveBalanceList;
	private Date formattedRequestedDate;
	private Date formattedFromDate;
	private Date formattedToDate;
	private List<LeaveRequestExceptionsDTO> requestExceptionList;
	private DepartmentsDTO departmentDetails;
	private int referenceID;
	private String leaveTypeID;
	private List<AvailableLeavesDTO> conversionMappingDetails;
	private String convertLeaves;
	private String serviceBookFlag;
	private SpecialCasualLeaveDTO leaveSubTypeDetails;

	private String empName;
	private String designation;
	private String department;

	private float totalNumberOfDays;
	private String searchSfid;
	private String mcExceptionID;
	private String prevHolidays;
	private String nextHolidays;
	private int previousDays;
	private int nextDays;
	private String currentFutureYearDays;
	private String futureAvailableLeaves;
	private String debitCode;
	private String debitLeaves;
	private String gazettedType;
	private float clBalance;
	private List<YearTypeDTO> yearsList;
	private String yearID;
	private String year;
	private String ltcRequestType;
    private String doPartDate;
    private String fileId;
	private List<LeaveYearlyBalanceDTO> yearAvailableLeaves;
	private String roleId;
	private String userIntimation;

	private String lastWorkingDay;
    private String retirementDate;
    private String dateOfJoining;
    private List<LeaveTxnDTO> onetimeEntryBalance;
    
    private int fileno;
    private String fileName;
    
    
    
    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileno() {
		return fileno;
	}

	public void setFileno(int fileno) {
		this.fileno = fileno;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getFutureAvailableLeaves() {
		return futureAvailableLeaves;
	}

	public void setFutureAvailableLeaves(String futureAvailableLeaves) {
		this.futureAvailableLeaves = futureAvailableLeaves;
	}

	public String getCurrentFutureYearDays() {
		return currentFutureYearDays;
	}

	public void setCurrentFutureYearDays(String currentFutureYearDays) {
		this.currentFutureYearDays = currentFutureYearDays;
	}

	public int getPreviousDays() {
		return previousDays;
	}

	public void setPreviousDays(int previousDays) {
		this.previousDays = previousDays;
	}

	public int getNextDays() {
		return nextDays;
	}

	public void setNextDays(int nextDays) {
		this.nextDays = nextDays;
	}

	public String getLastWorkingDay() {
		return lastWorkingDay;
	}

	public void setLastWorkingDay(String lastWorkingDay) {
		this.lastWorkingDay = lastWorkingDay;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public List<LeaveTxnDTO> getOnetimeEntryBalance() {
		return onetimeEntryBalance;
	}

	public void setOnetimeEntryBalance(List<LeaveTxnDTO> onetimeEntryBalance) {
		this.onetimeEntryBalance = onetimeEntryBalance;
	}

	public String getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}
	public String getUserIntimation() {
		return userIntimation;
	}

	public void setUserIntimation(String userIntimation) {
		this.userIntimation = userIntimation;
	}
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<LeaveYearlyBalanceDTO> getYearAvailableLeaves() {
		return yearAvailableLeaves;
	}

	public void setYearAvailableLeaves(
			List<LeaveYearlyBalanceDTO> yearAvailableLeaves) {
		this.yearAvailableLeaves = yearAvailableLeaves;
	}

	public String getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(String doPartDate) {
		this.doPartDate = doPartDate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYearID() {
		return yearID;
	}

	public void setYearID(String yearID) {
		this.yearID = yearID;
	}

	public List<YearTypeDTO> getYearsList() {
		return yearsList;
	}

	public void setYearsList(List<YearTypeDTO> yearsList) {
		this.yearsList = yearsList;
	}

	public float getClBalance() {
		return clBalance;
	}

	public void setClBalance(float clBalance) {
		this.clBalance = clBalance;
	}

	public List<LeaveRequestBean> getCancelledLeavesList() {
		return cancelledLeavesList;
	}

	public void setCancelledLeavesList(List<LeaveRequestBean> cancelledLeavesList) {
		this.cancelledLeavesList = cancelledLeavesList;
	}

	public List<LeaveRequestBean> getConvertedLeavesList() {
		return convertedLeavesList;
	}

	public void setConvertedLeavesList(List<LeaveRequestBean> convertedLeavesList) {
		this.convertedLeavesList = convertedLeavesList;
	}

	public String getGazettedType() {
		return gazettedType;
	}

	public void setGazettedType(String gazettedType) {
		this.gazettedType = gazettedType;
	}

	public String getDebitLeaves() {
		return debitLeaves;
	}

	public void setDebitLeaves(String debitLeaves) {
		this.debitLeaves = debitLeaves;
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

	public String getMcExceptionID() {
		return mcExceptionID;
	}

	public void setMcExceptionID(String mcExceptionID) {
		this.mcExceptionID = mcExceptionID;
	}

	public String getSearchSfid() {
		return searchSfid;
	}

	public void setSearchSfid(String searchSfid) {
		this.searchSfid = searchSfid;
	}

	public float getTotalNumberOfDays() {
		return totalNumberOfDays;
	}

	public void setTotalNumberOfDays(float totalNumberOfDays) {
		this.totalNumberOfDays = totalNumberOfDays;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<LeaveCardDTO> getSpecialCasualLeaveCard() {
		return specialCasualLeaveCard;
	}

	public void setSpecialCasualLeaveCard(List<LeaveCardDTO> specialCasualLeaveCard) {
		this.specialCasualLeaveCard = specialCasualLeaveCard;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getInternalNo() {
		return internalNo;
	}

	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
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

	public String getEligibility() {
		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
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

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<LeaveManagementBean> getLeaveDetailsList() {
		return leaveDetailsList;
	}

	public void setLeaveDetailsList(List<LeaveManagementBean> leaveDetailsList) {
		this.leaveDetailsList = leaveDetailsList;
	}

	public List<FamilyBean> getFamilyMembersList() {
		return familyMembersList;
	}

	public void setFamilyMembersList(List<FamilyBean> familyMembersList) {
		this.familyMembersList = familyMembersList;
	}

	public List<LeaveRequestBean> getAppliedLeavesList() {
		return appliedLeavesList;
	}

	public void setAppliedLeavesList(List<LeaveRequestBean> appliedLeavesList) {
		this.appliedLeavesList = appliedLeavesList;
	}

	public List<LeaveFamilyDTO> getLeaveFamilyImpactDetails() {
		return leaveFamilyImpactDetails;
	}

	public void setLeaveFamilyImpactDetails(List<LeaveFamilyDTO> leaveFamilyImpactDetails) {
		this.leaveFamilyImpactDetails = leaveFamilyImpactDetails;
	}

	public List<LeaveExceptionDetailsDTO> getExceptionDetailsList() {
		return exceptionDetailsList;
	}

	public void setExceptionDetailsList(List<LeaveExceptionDetailsDTO> exceptionDetailsList) {
		this.exceptionDetailsList = exceptionDetailsList;
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

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getStudyDegree() {
		return studyDegree;
	}

	public void setStudyDegree(String studyDegree) {
		this.studyDegree = studyDegree;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getSelectAddress() {
		return selectAddress;
	}

	public void setSelectAddress(String selectAddress) {
		this.selectAddress = selectAddress;
	}

	public MultipartFile getMcFile() {
		return mcFile;
	}

	public void setMcFile(MultipartFile mcFile) {
		this.mcFile = mcFile;
	}

	public MultipartFile getFitnessFile() {
		return fitnessFile;
	}

	public void setFitnessFile(MultipartFile fitnessFile) {
		this.fitnessFile = fitnessFile;
	}

	public MultipartFile getOtherCertFile() {
		return otherCertFile;
	}

	public void setOtherCertFile(MultipartFile otherCertFile) {
		this.otherCertFile = otherCertFile;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getOfflineSFID() {
		return offlineSFID;
	}

	public void setOfflineSFID(String offlineSFID) {
		this.offlineSFID = offlineSFID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(String noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public List<AvailableLeavesDTO> getLeaveCreditsList() {
		return leaveCreditsList;
	}

	public void setLeaveCreditsList(List<AvailableLeavesDTO> leaveCreditsList) {
		this.leaveCreditsList = leaveCreditsList;
	}

	public String getEmpGender() {
		return empGender;
	}

	public void setEmpGender(String empGender) {
		this.empGender = empGender;
	}

	public List<SpecialCasualLeaveDTO> getSpecialCasualList() {
		return specialCasualList;
	}

	public void setSpecialCasualList(List<SpecialCasualLeaveDTO> specialCasualList) {
		this.specialCasualList = specialCasualList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

	public LeaveManagementBean getSelectedLeaveDetails() {
		return selectedLeaveDetails;
	}

	public void setSelectedLeaveDetails(LeaveManagementBean selectedLeaveDetails) {
		this.selectedLeaveDetails = selectedLeaveDetails;
	}

	public String getRemarksNote() {
		return remarksNote;
	}

	public void setRemarksNote(String remarksNote) {
		this.remarksNote = remarksNote;
	}

	public String getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(String appliedBy) {
		this.appliedBy = appliedBy;
	}

	public String getLeaveRequestType() {
		return leaveRequestType;
	}

	public void setLeaveRequestType(String leaveRequestType) {
		this.leaveRequestType = leaveRequestType;
	}

	public String getExceptionalDetails() {
		return exceptionalDetails;
	}

	public void setExceptionalDetails(String exceptionalDetails) {
		this.exceptionalDetails = exceptionalDetails;
	}

	public SpecialCasualLeaveDTO getSeletedSpecialLeaveDetails() {
		return seletedSpecialLeaveDetails;
	}

	public void setSeletedSpecialLeaveDetails(SpecialCasualLeaveDTO seletedSpecialLeaveDetails) {
		this.seletedSpecialLeaveDetails = seletedSpecialLeaveDetails;
	}

	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	public LeaveRequestBean getReferenceLeaveDetails() {
		return referenceLeaveDetails;
	}

	public void setReferenceLeaveDetails(LeaveRequestBean referenceLeaveDetails) {
		this.referenceLeaveDetails = referenceLeaveDetails;
	}

	public String getMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}

	public List<LeaveCardDTO> getCasualLeaveCard() {
		return casualLeaveCard;
	}

	public void setCasualLeaveCard(List<LeaveCardDTO> casualLeaveCard) {
		this.casualLeaveCard = casualLeaveCard;
	}

	public List<LeaveCardDTO> getEarnedLeaveCard() {
		return earnedLeaveCard;
	}

	public void setEarnedLeaveCard(List<LeaveCardDTO> earnedLeaveCard) {
		this.earnedLeaveCard = earnedLeaveCard;
	}

	public List<LeaveCardDTO> getHalfPayLeaveCard() {
		return halfPayLeaveCard;
	}

	public void setHalfPayLeaveCard(List<LeaveCardDTO> halfPayLeaveCard) {
		this.halfPayLeaveCard = halfPayLeaveCard;
	}

	public List<LeaveCardDTO> getLeaveNotDueLeaveCard() {
		return leaveNotDueLeaveCard;
	}

	public void setLeaveNotDueLeaveCard(List<LeaveCardDTO> leaveNotDueLeaveCard) {
		this.leaveNotDueLeaveCard = leaveNotDueLeaveCard;
	}

	public List<LeaveCardDTO> getChildCareLeaveCard() {
		return childCareLeaveCard;
	}

	public void setChildCareLeaveCard(List<LeaveCardDTO> childCareLeaveCard) {
		this.childCareLeaveCard = childCareLeaveCard;
	}

	public List<LeaveCardDTO> getOtherLeaveCard() {
		return otherLeaveCard;
	}

	public void setOtherLeaveCard(List<LeaveCardDTO> otherLeaveCard) {
		this.otherLeaveCard = otherLeaveCard;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public List<AvailableLeavesDTO> getLeaveBalanceList() {
		return leaveBalanceList;
	}

	public void setLeaveBalanceList(List<AvailableLeavesDTO> leaveBalanceList) {
		this.leaveBalanceList = leaveBalanceList;
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

	public List<LeaveRequestExceptionsDTO> getRequestExceptionList() {
		return requestExceptionList;
	}

	public void setRequestExceptionList(List<LeaveRequestExceptionsDTO> requestExceptionList) {
		this.requestExceptionList = requestExceptionList;
	}

	public DepartmentsDTO getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentsDTO departmentDetails) {
		this.departmentDetails = departmentDetails;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public String getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(String leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public List<AvailableLeavesDTO> getConversionMappingDetails() {
		return conversionMappingDetails;
	}

	public void setConversionMappingDetails(List<AvailableLeavesDTO> conversionMappingDetails) {
		this.conversionMappingDetails = conversionMappingDetails;
	}

	public String getConvertLeaves() {
		return convertLeaves;
	}

	public void setConvertLeaves(String convertLeaves) {
		this.convertLeaves = convertLeaves;
	}

	public String getServiceBookFlag() {
		return serviceBookFlag;
	}

	public void setServiceBookFlag(String serviceBookFlag) {
		this.serviceBookFlag = serviceBookFlag;
	}

	public SpecialCasualLeaveDTO getLeaveSubTypeDetails() {
		return leaveSubTypeDetails;
	}

	public void setLeaveSubTypeDetails(SpecialCasualLeaveDTO leaveSubTypeDetails) {
		this.leaveSubTypeDetails = leaveSubTypeDetails;
	}

	public void setLtcRequestType(String ltcRequestType) {
		this.ltcRequestType = ltcRequestType;
	}

	public String getLtcRequestType() {
		return ltcRequestType;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

}
