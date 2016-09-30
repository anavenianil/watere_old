package com.callippus.web.loan.beans.management;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.FamilyRelationDTO;
import com.callippus.web.beans.holidays.HolidaysBean;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.loan.beans.dto.FinancialYearDTO;
import com.callippus.web.loan.beans.dto.GPFClosingBalanceDTO;
import com.callippus.web.loan.beans.dto.GPFRulesDTO;
import com.callippus.web.loan.beans.dto.GPFSubTypeMasterDTO;
import com.callippus.web.loan.beans.dto.LoanAmountDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanAmountGridDTO;
import com.callippus.web.loan.beans.dto.LoanCDADetailsDTO;
import com.callippus.web.loan.beans.dto.LoanDesigMappingDTO;
import com.callippus.web.loan.beans.dto.LoanDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO;
import com.callippus.web.loan.beans.dto.LoanHBAInterestGridDTO;
import com.callippus.web.loan.beans.dto.LoanLeavesMappingDTO;
import com.callippus.web.loan.beans.dto.LoanSigningAuthorityDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.loan.beans.request.LoanRequestBean;

public class LoanManagementBean extends CommonDTO {
	private String param;
	private String message;
	private int requestId;
	private String historyID;
	private String statusMsg;
	private String back;
	private String nodeID;
	private String sfID;
	private String result;
	private List<LoanTypeMasterDTO> loanTypeMasterList;
	private String loanName;
	private String loanType;
	private List<HolidaysBean> holidaysList;
	private List<LoanFestivalMasterDTO> loanFestivalList;
	private String selectedLinks;
	private String festivalName;
	private String festivalDate;
	

	private String festivalLoanStartDate;
	private String festivalLoanEndDate;
	private String listType;

	private List<FamilyRelationDTO> relationList;
	private String relationShipID;
	private String refSfID;
	private Date dod;
	private String appname;
	private String advanceAmount;
	private String witness1;
	private String witness2;
	private String address;
	private String approvedBy;

	private String balance;
	private String fromDate;
	private String toDate;
	private List<GPFClosingBalanceDTO> gpfBalanceList;
	private String changeSfID;

	private String loanSubType;
	private String rule;
	private List<GPFSubTypeMasterDTO> gpfSubTypeList;
	private String type;

	private List<LoanDetailsDTO> loanTypeDetailsList;
	private List<LeaveTypeDTO> leaveTypesList;
	private List<LoanLeavesMappingDTO> loanLeavesDetailsList;
	private int minInstallments;
	private int maxInstallments;
	private int minInterestInstallments;
	private int maxInterestInstallments;
	private String periodTypeFlag;
	private int noOfMonths;
	private float interestRate;
	private int maxRecoveryPeriod;
	private String impactOnLeaveFlag;
	private float experience;
	private String designation;
	private String selectedLoan;
	private List<DesignationDTO> designationList;
	private String gpfType;
	private String purpose;

	private String gazType;
	private int amountID;
	private List<GPFRulesDTO> gpfRulesList;
	private String RecoveryFlag;
	private String leave;
	private int loanDetailsID;
	private String impactOnPayFlag;
	private String impactOnTimesFlag;
	private String impactOnDAFlag;
	private String impactOnBalanceFlag;
	private String impactOnNoOfMonthsPayFlag;
	private int daPercentage;
	private String amountGrid;
	private List<LoanAmountDetailsDTO> loanAmountDetailsList;
	private List<LoanDesigMappingDTO> loanDesigMappingList;
	private List<LoanAmountGridDTO> loanAmountGrid;
	private String financialYear;
	private List<FinancialYearDTO> financialYearList;
	private String reportNumber;
	private Date reportDate;
	private List<LoanRequestBean> appliedLoansList;
	private String requests;
	private List<LoanCDADetailsDTO> reportNumberList;
	private String hqReportNumber;
	private Date hqReportDate;
	private List<LoanRequestBean> searchCDAList;
	private String cdaReportGrid;
	private Date hbaInterestYear;
	private String hbaInterestGrid;
	private List<LoanHBAInterestGridDTO> loanHBAInterestList;
	private List<LoanCDADetailsDTO> conveyanceReportList;
	private List<LoanSigningAuthorityDTO> signingAuthorityList;
	private List<LoanCDADetailsDTO> loanCDAist;
	private List<LoanCDADetailsDTO> paybillBACReportList;
	private List<LoanCDADetailsDTO> sanctionConReportList;
	private String ipAddress;
	private String srAccOfficer;
	private String accOfficer;
	private String cfaOfficer;
	private String cdaGrid;
	
	
	private String startDate;
	private String endDate;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
	private List<LoanCDADetailsDTO> appliedLFestivalList;

	public List<LoanAmountDetailsDTO> getLoanAmountDetailsList() {
		return loanAmountDetailsList;
	}

	public void setLoanAmountDetailsList(List<LoanAmountDetailsDTO> loanAmountDetailsList) {
		this.loanAmountDetailsList = loanAmountDetailsList;
	}

	public List<LoanDesigMappingDTO> getLoanDesigMappingList() {
		return loanDesigMappingList;
	}

	public void setLoanDesigMappingList(List<LoanDesigMappingDTO> loanDesigMappingList) {
		this.loanDesigMappingList = loanDesigMappingList;
	}

	public List<LoanAmountGridDTO> getLoanAmountGrid() {
		return loanAmountGrid;
	}

	public void setLoanAmountGrid(List<LoanAmountGridDTO> loanAmountGrid) {
		this.loanAmountGrid = loanAmountGrid;
	}

	public String getAmountGrid() {
		return amountGrid;
	}

	public void setAmountGrid(String amountGrid) {
		this.amountGrid = amountGrid;
	}

	public List<GPFRulesDTO> getGpfRulesList() {
		return gpfRulesList;
	}

	public void setGpfRulesList(List<GPFRulesDTO> gpfRulesList) {
		this.gpfRulesList = gpfRulesList;
	}

	public int getAmountID() {
		return amountID;
	}

	public void setAmountID(int amountID) {
		this.amountID = amountID;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getGpfType() {
		return gpfType;
	}

	public void setGpfType(String gpfType) {
		this.gpfType = gpfType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSelectedLoan() {
		return selectedLoan;
	}

	public void setSelectedLoan(String selectedLoan) {
		this.selectedLoan = selectedLoan;
	}

	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public int getMinInstallments() {
		return minInstallments;
	}

	public void setMinInstallments(int minInstallments) {
		this.minInstallments = minInstallments;
	}

	public int getMaxInstallments() {
		return maxInstallments;
	}

	public void setMaxInstallments(int maxInstallments) {
		this.maxInstallments = maxInstallments;
	}

	public String getPeriodTypeFlag() {
		return periodTypeFlag;
	}

	public void setPeriodTypeFlag(String periodTypeFlag) {
		this.periodTypeFlag = periodTypeFlag;
	}

	public int getNoOfMonths() {
		return noOfMonths;
	}

	public void setNoOfMonths(int noOfMonths) {
		this.noOfMonths = noOfMonths;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public String getImpactOnLeaveFlag() {
		return impactOnLeaveFlag;
	}

	public void setImpactOnLeaveFlag(String impactOnLeaveFlag) {
		this.impactOnLeaveFlag = impactOnLeaveFlag;
	}

	public float getExperience() {
		return experience;
	}

	public void setExperience(float experience) {
		this.experience = experience;
	}

	public List<LeaveTypeDTO> getLeaveTypesList() {
		return leaveTypesList;
	}

	public void setLeaveTypesList(List<LeaveTypeDTO> leaveTypesList) {
		this.leaveTypesList = leaveTypesList;
	}

	public List<LoanDetailsDTO> getLoanTypeDetailsList() {
		return loanTypeDetailsList;
	}

	public void setLoanTypeDetailsList(List<LoanDetailsDTO> loanTypeDetailsList) {
		this.loanTypeDetailsList = loanTypeDetailsList;
	}

	public List<GPFSubTypeMasterDTO> getGpfSubTypeList() {
		return gpfSubTypeList;
	}

	public void setGpfSubTypeList(List<GPFSubTypeMasterDTO> gpfSubTypeList) {
		this.gpfSubTypeList = gpfSubTypeList;
	}

	public String getLoanSubType() {
		return loanSubType;
	}

	public void setLoanSubType(String loanSubType) {
		this.loanSubType = loanSubType;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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

	public List<GPFClosingBalanceDTO> getGpfBalanceList() {
		return gpfBalanceList;
	}

	public void setGpfBalanceList(List<GPFClosingBalanceDTO> gpfBalanceList) {
		this.gpfBalanceList = gpfBalanceList;
	}

	public String getChangeSfID() {
		return changeSfID;
	}

	public void setChangeSfID(String changeSfID) {
		this.changeSfID = changeSfID;
	}

	public List<HolidaysBean> getHolidaysList() {
		return holidaysList;
	}

	public void setHolidaysList(List<HolidaysBean> holidaysList) {
		this.holidaysList = holidaysList;
	}

	public List<LoanFestivalMasterDTO> getLoanFestivalList() {
		return loanFestivalList;
	}

	public void setLoanFestivalList(List<LoanFestivalMasterDTO> loanFestivalList) {
		this.loanFestivalList = loanFestivalList;
	}

	public String getSelectedLinks() {
		return selectedLinks;
	}

	public void setSelectedLinks(String selectedLinks) {
		this.selectedLinks = selectedLinks;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public List<LoanTypeMasterDTO> getLoanTypeMasterList() {
		return loanTypeMasterList;
	}

	public void setLoanTypeMasterList(List<LoanTypeMasterDTO> loanTypeMasterList) {
		this.loanTypeMasterList = loanTypeMasterList;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<FamilyRelationDTO> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<FamilyRelationDTO> relationList) {
		this.relationList = relationList;
	}

	public String getWitness1() {
		return witness1;
	}

	public void setWitness1(String witness1) {
		this.witness1 = witness1;
	}

	public String getWitness2() {
		return witness2;
	}

	public void setWitness2(String witness2) {
		this.witness2 = witness2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getRelationShipID() {
		return relationShipID;
	}

	public void setRelationShipID(String relationShipID) {
		this.relationShipID = relationShipID;
	}

	public void setRecoveryFlag(String recoveryFlag) {
		RecoveryFlag = recoveryFlag;
	}

	public String getRecoveryFlag() {
		return RecoveryFlag;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getLeave() {
		return leave;
	}

	public void setLoanDetailsID(int loanDetailsID) {
		this.loanDetailsID = loanDetailsID;
	}

	public int getLoanDetailsID() {
		return loanDetailsID;
	}

	public String getImpactOnPayFlag() {
		return impactOnPayFlag;
	}

	public void setImpactOnPayFlag(String impactOnPayFlag) {
		this.impactOnPayFlag = impactOnPayFlag;
	}

	public String getImpactOnTimesFlag() {
		return impactOnTimesFlag;
	}

	public void setImpactOnTimesFlag(String impactOnTimesFlag) {
		this.impactOnTimesFlag = impactOnTimesFlag;
	}

	public String getImpactOnDAFlag() {
		return impactOnDAFlag;
	}

	public void setImpactOnDAFlag(String impactOnDAFlag) {
		this.impactOnDAFlag = impactOnDAFlag;
	}

	public String getImpactOnBalanceFlag() {
		return impactOnBalanceFlag;
	}

	public void setImpactOnBalanceFlag(String impactOnBalanceFlag) {
		this.impactOnBalanceFlag = impactOnBalanceFlag;
	}

	public String getImpactOnNoOfMonthsPayFlag() {
		return impactOnNoOfMonthsPayFlag;
	}

	public void setImpactOnNoOfMonthsPayFlag(String impactOnNoOfMonthsPayFlag) {
		this.impactOnNoOfMonthsPayFlag = impactOnNoOfMonthsPayFlag;
	}

	public void setMaxRecoveryPeriod(int maxRecoveryPeriod) {
		this.maxRecoveryPeriod = maxRecoveryPeriod;
	}

	public int getMaxRecoveryPeriod() {
		return maxRecoveryPeriod;
	}

	public void setLoanLeavesDetailsList(List<LoanLeavesMappingDTO> loanLeavesDetailsList) {
		this.loanLeavesDetailsList = loanLeavesDetailsList;
	}

	public List<LoanLeavesMappingDTO> getLoanLeavesDetailsList() {
		return loanLeavesDetailsList;
	}

	public void setDaPercentage(int daPercentage) {
		this.daPercentage = daPercentage;
	}

	public int getDaPercentage() {
		return daPercentage;
	}

	public Date getDod() {
		return dod;
	}

	public void setDod(Date dod) {
		this.dod = dod;
	}

	public String getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(String advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getRefSfID() {
		return refSfID;
	}

	public void setRefSfID(String refSfID) {
		this.refSfID = refSfID;
	}

	public void setFinancialYearList(List<FinancialYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}

	public List<FinancialYearDTO> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public void setAppliedLoansList(List<LoanRequestBean> appliedLoansList) {
		this.appliedLoansList = appliedLoansList;
	}

	public List<LoanRequestBean> getAppliedLoansList() {
		return appliedLoansList;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public String getReportNumber() {
		return reportNumber;
	}

	public void setRequests(String requests) {
		this.requests = requests;
	}

	public String getRequests() {
		return requests;
	}

	public void setReportNumberList(List<LoanCDADetailsDTO> reportNumberList) {
		this.reportNumberList = reportNumberList;
	}

	public List<LoanCDADetailsDTO> getReportNumberList() {
		return reportNumberList;
	}

	public void setSearchCDAList(List<LoanRequestBean> searchCDAList) {
		this.searchCDAList = searchCDAList;
	}

	public List<LoanRequestBean> getSearchCDAList() {
		return searchCDAList;
	}

	public void setCdaReportGrid(String cdaReportGrid) {
		this.cdaReportGrid = cdaReportGrid;
	}

	public String getCdaReportGrid() {
		return cdaReportGrid;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}

	public String getFestivalName() {
		return festivalName;
	}

	public void setMinInterestInstallments(int minInterestInstallments) {
		this.minInterestInstallments = minInterestInstallments;
	}

	public int getMinInterestInstallments() {
		return minInterestInstallments;
	}

	public void setMaxInterestInstallments(int maxInterestInstallments) {
		this.maxInterestInstallments = maxInterestInstallments;
	}

	public int getMaxInterestInstallments() {
		return maxInterestInstallments;
	}

	public void setHbaInterestYear(Date hbaInterestYear) {
		this.hbaInterestYear = hbaInterestYear;
	}

	public Date getHbaInterestYear() {
		return hbaInterestYear;
	}

	public void setHbaInterestGrid(String hbaInterestGrid) {
		this.hbaInterestGrid = hbaInterestGrid;
	}

	public String getHbaInterestGrid() {
		return hbaInterestGrid;
	}

	public void setLoanHBAInterestList(List<LoanHBAInterestGridDTO> loanHBAInterestList) {
		this.loanHBAInterestList = loanHBAInterestList;
	}

	public List<LoanHBAInterestGridDTO> getLoanHBAInterestList() {
		return loanHBAInterestList;
	}

	public void setConveyanceReportList(List<LoanCDADetailsDTO> conveyanceReportList) {
		this.conveyanceReportList = conveyanceReportList;
	}

	public List<LoanCDADetailsDTO> getConveyanceReportList() {
		return conveyanceReportList;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setSigningAuthorityList(List<LoanSigningAuthorityDTO> signingAuthorityList) {
		this.signingAuthorityList = signingAuthorityList;
	}

	public List<LoanSigningAuthorityDTO> getSigningAuthorityList() {
		return signingAuthorityList;
	}

	public void setSrAccOfficer(String srAccOfficer) {
		this.srAccOfficer = srAccOfficer;
	}

	public String getSrAccOfficer() {
		return srAccOfficer;
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

	public void setLoanCDAist(List<LoanCDADetailsDTO> loanCDAist) {
		this.loanCDAist = loanCDAist;
	}

	public List<LoanCDADetailsDTO> getLoanCDAist() {
		return loanCDAist;
	}

	public void setPaybillBACReportList(List<LoanCDADetailsDTO> paybillBACReportList) {
		this.paybillBACReportList = paybillBACReportList;
	}

	public List<LoanCDADetailsDTO> getPaybillBACReportList() {
		return paybillBACReportList;
	}

	public void setCdaGrid(String cdaGrid) {
		this.cdaGrid = cdaGrid;
	}

	public String getCdaGrid() {
		return cdaGrid;
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

	public void setAppliedLFestivalList(List<LoanCDADetailsDTO> appliedLFestivalList) {
		this.appliedLFestivalList = appliedLFestivalList;
	}

	public List<LoanCDADetailsDTO> getAppliedLFestivalList() {
		return appliedLFestivalList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getHistoryID() {
		return historyID;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setSanctionConReportList(List<LoanCDADetailsDTO> sanctionConReportList) {
		this.sanctionConReportList = sanctionConReportList;
	}

	public List<LoanCDADetailsDTO> getSanctionConReportList() {
		return sanctionConReportList;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getFestivalDate() {
		return festivalDate;
	}

	public void setFestivalDate(String festivalDate) {
		this.festivalDate = festivalDate;
	}

	public String getFestivalLoanStartDate() {
		return festivalLoanStartDate;
	}

	public void setFestivalLoanStartDate(String festivalLoanStartDate) {
		this.festivalLoanStartDate = festivalLoanStartDate;
	}

	public String getFestivalLoanEndDate() {
		return festivalLoanEndDate;
	}

	public void setFestivalLoanEndDate(String festivalLoanEndDate) {
		this.festivalLoanEndDate = festivalLoanEndDate;
	}

}