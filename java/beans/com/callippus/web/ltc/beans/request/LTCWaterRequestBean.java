package com.callippus.web.ltc.beans.request;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.ltc.dto.LTCWaterRequestDTO;
import com.callippus.web.ltc.dto.LtcYearsDTO;
import com.callippus.web.tada.dto.BankNamesDTO;

public class LTCWaterRequestBean {
	
	private String sfid;
	private String sfID;
	private String designation;
	private String phnNo;
	private String deptName;
	private String param;
	private String id;
	private String type;
	private String requestID;
	private String requestId;
	private String ltcRequestType;
	
	
	private String typeOfLtc;
	private String ltcYear;
	
	private String hometownAddr;
	private Date startHoliday; 
	private Date returnHoliday; 
	private String doj;
	private String advance;
	
	private EmployeeBean empDetailsList;
	private DepartmentsDTO deptDTO;
	
	
	
	
	
	private List<LtcYearsDTO> ltcYearsList;
	
	private List<LtcYearsDTO> ltcYearList;
	
	private int nod;
	
	private int noOfAdultsTickets; 
	private int noOfChildrenTickets; 
	
	
	
	private Date appliedDate;
	
	private EmployeeBean empBean;
	private AddressBean HomeTownAddress;
	
	
	private int status;
	private String leaveType;
	private int totalTickets;
	private String result;
	private String remarks;
	private String ipAddress;
	
	private String message;
	
	private String historyID;
	
	private String back;
	
	private String statusMsg;
	private LTCWaterRequestDTO ltcWaterRequestDTO;
	
	
	private int amountAdults;
	private int amountChildren; 
	private int adultsTotAmt;   
	private int childrenTotAmt;
	private int otherAmt;
	private int totalTicketsAmt;
	private Date refLetterDate;
	private String refLetterNo;
	
	
	private String ltcadvcashorcheck;
	private String ltcadvBankName;
	private String ltcadvBranchName;
	private String ltcChequeNo;
	private String ltcadvAdminDvno;
	private Date ltcadvAdminDvDate;
	private String ltcadvChequeNo;
	
	private List<BankNamesDTO> bankNamesList;
	
	private int ltcactualExpenditure;
	private int ltcsettleOrReimAmt;
	private String ltcselOrReimRemarks;
	private Date ltcsettleOrReimApplyDate;
	private String ltcsettleOrReim;
	
	private List<FamilyBean> FamilyMemberDetails;
	
	private Date settlementAdminDate;
	private String settlementAdminRemarks;
	
	
	private String reimbursementAdminRemarks;
	private Date reimbursementAdminDate;
	private String ltcreimcashorcheck;
	private String ltcreimBankName;
	private String ltcreimBranchName;
	private String ltcreimAdminDvno;
	private Date ltcreimAdminDvDate;
	private String ltcreimChequeNo;
	
	private String jsonValue;
	
	
	private int stageStatus;
	
	
	
	
	
	
	public int getStageStatus() {
		return stageStatus;
	}

	public void setStageStatus(int stageStatus) {
		this.stageStatus = stageStatus;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getLtcreimChequeNo() {
		return ltcreimChequeNo;
	}

	public void setLtcreimChequeNo(String ltcreimChequeNo) {
		this.ltcreimChequeNo = ltcreimChequeNo;
	}

	public String getReimbursementAdminRemarks() {
		return reimbursementAdminRemarks;
	}

	public void setReimbursementAdminRemarks(String reimbursementAdminRemarks) {
		this.reimbursementAdminRemarks = reimbursementAdminRemarks;
	}

	public Date getReimbursementAdminDate() {
		return reimbursementAdminDate;
	}

	public void setReimbursementAdminDate(Date reimbursementAdminDate) {
		this.reimbursementAdminDate = reimbursementAdminDate;
	}

	public String getLtcreimcashorcheck() {
		return ltcreimcashorcheck;
	}

	public void setLtcreimcashorcheck(String ltcreimcashorcheck) {
		this.ltcreimcashorcheck = ltcreimcashorcheck;
	}

	public String getLtcreimBankName() {
		return ltcreimBankName;
	}

	public void setLtcreimBankName(String ltcreimBankName) {
		this.ltcreimBankName = ltcreimBankName;
	}

	public String getLtcreimBranchName() {
		return ltcreimBranchName;
	}

	public void setLtcreimBranchName(String ltcreimBranchName) {
		this.ltcreimBranchName = ltcreimBranchName;
	}

	public String getLtcreimAdminDvno() {
		return ltcreimAdminDvno;
	}

	public void setLtcreimAdminDvno(String ltcreimAdminDvno) {
		this.ltcreimAdminDvno = ltcreimAdminDvno;
	}

	public Date getLtcreimAdminDvDate() {
		return ltcreimAdminDvDate;
	}

	public void setLtcreimAdminDvDate(Date ltcreimAdminDvDate) {
		this.ltcreimAdminDvDate = ltcreimAdminDvDate;
	}

	public Date getSettlementAdminDate() {
		return settlementAdminDate;
	}

	public void setSettlementAdminDate(Date settlementAdminDate) {
		this.settlementAdminDate = settlementAdminDate;
	}

	public String getSettlementAdminRemarks() {
		return settlementAdminRemarks;
	}

	public void setSettlementAdminRemarks(String settlementAdminRemarks) {
		this.settlementAdminRemarks = settlementAdminRemarks;
	}

	public List<FamilyBean> getFamilyMemberDetails() {
		return FamilyMemberDetails;
	}

	public void setFamilyMemberDetails(List<FamilyBean> familyMemberDetails) {
		FamilyMemberDetails = familyMemberDetails;
	}
	
	
	



	public String getLtcsettleOrReim() {
		return ltcsettleOrReim;
	}

	public void setLtcsettleOrReim(String ltcsettleOrReim) {
		this.ltcsettleOrReim = ltcsettleOrReim;
	}

	public int getLtcactualExpenditure() {
		return ltcactualExpenditure;
	}

	public void setLtcactualExpenditure(int ltcactualExpenditure) {
		this.ltcactualExpenditure = ltcactualExpenditure;
	}

	public int getLtcsettleOrReimAmt() {
		return ltcsettleOrReimAmt;
	}

	public void setLtcsettleOrReimAmt(int ltcsettleOrReimAmt) {
		this.ltcsettleOrReimAmt = ltcsettleOrReimAmt;
	}

	public String getLtcselOrReimRemarks() {
		return ltcselOrReimRemarks;
	}

	public void setLtcselOrReimRemarks(String ltcselOrReimRemarks) {
		this.ltcselOrReimRemarks = ltcselOrReimRemarks;
	}

	public Date getLtcsettleOrReimApplyDate() {
		return ltcsettleOrReimApplyDate;
	}

	public void setLtcsettleOrReimApplyDate(Date ltcsettleOrReimApplyDate) {
		this.ltcsettleOrReimApplyDate = ltcsettleOrReimApplyDate;
	}

	public String getLtcadvChequeNo() {
		return ltcadvChequeNo;
	}

	public void setLtcadvChequeNo(String ltcadvChequeNo) {
		this.ltcadvChequeNo = ltcadvChequeNo;
	}

	public List<BankNamesDTO> getBankNamesList() {
		return bankNamesList;
	}

	public void setBankNamesList(List<BankNamesDTO> bankNamesList) {
		this.bankNamesList = bankNamesList;
	}

	public Date getLtcadvAdminDvDate() {
		return ltcadvAdminDvDate;
	}

	public void setLtcadvAdminDvDate(Date ltcadvAdminDvDate) {
		this.ltcadvAdminDvDate = ltcadvAdminDvDate;
	}

	public String getLtcadvcashorcheck() {
		return ltcadvcashorcheck;
	}

	public void setLtcadvcashorcheck(String ltcadvcashorcheck) {
		this.ltcadvcashorcheck = ltcadvcashorcheck;
	}

	public String getLtcadvBankName() {
		return ltcadvBankName;
	}

	public void setLtcadvBankName(String ltcadvBankName) {
		this.ltcadvBankName = ltcadvBankName;
	}

	public String getLtcadvBranchName() {
		return ltcadvBranchName;
	}

	public void setLtcadvBranchName(String ltcadvBranchName) {
		this.ltcadvBranchName = ltcadvBranchName;
	}

	public String getLtcChequeNo() {
		return ltcChequeNo;
	}

	public void setLtcChequeNo(String ltcChequeNo) {
		this.ltcChequeNo = ltcChequeNo;
	}

	public String getLtcadvAdminDvno() {
		return ltcadvAdminDvno;
	}

	public void setLtcadvAdminDvno(String ltcadvAdminDvno) {
		this.ltcadvAdminDvno = ltcadvAdminDvno;
	}


	public int getAmountAdults() {
		return amountAdults;
	}

	public void setAmountAdults(int amountAdults) {
		this.amountAdults = amountAdults;
	}

	public int getAmountChildren() {
		return amountChildren;
	}

	public void setAmountChildren(int amountChildren) {
		this.amountChildren = amountChildren;
	}

	public int getAdultsTotAmt() {
		return adultsTotAmt;
	}

	public void setAdultsTotAmt(int adultsTotAmt) {
		this.adultsTotAmt = adultsTotAmt;
	}

	public int getChildrenTotAmt() {
		return childrenTotAmt;
	}

	public void setChildrenTotAmt(int childrenTotAmt) {
		this.childrenTotAmt = childrenTotAmt;
	}

	public int getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(int otherAmt) {
		this.otherAmt = otherAmt;
	}

	public int getTotalTicketsAmt() {
		return totalTicketsAmt;
	}

	public void setTotalTicketsAmt(int totalTicketsAmt) {
		this.totalTicketsAmt = totalTicketsAmt;
	}

	public Date getRefLetterDate() {
		return refLetterDate;
	}

	public void setRefLetterDate(Date refLetterDate) {
		this.refLetterDate = refLetterDate;
	}

	public String getRefLetterNo() {
		return refLetterNo;
	}

	public void setRefLetterNo(String refLetterNo) {
		this.refLetterNo = refLetterNo;
	}

	public LTCWaterRequestDTO getLtcWaterRequestDTO() {
		return ltcWaterRequestDTO;
	}

	public void setLtcWaterRequestDTO(LTCWaterRequestDTO ltcWaterRequestDTO) {
		this.ltcWaterRequestDTO = ltcWaterRequestDTO;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

	public Date getStartHoliday() {
		return startHoliday;
	}

	public void setStartHoliday(Date startHoliday) {
		this.startHoliday = startHoliday;
	}

	public Date getReturnHoliday() {
		return returnHoliday;
	}

	public void setReturnHoliday(Date returnHoliday) {
		this.returnHoliday = returnHoliday;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPhnNo() {
		return phnNo;
	}

	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public AddressBean getHomeTownAddress() {
		return HomeTownAddress;
	}

	public void setHomeTownAddress(AddressBean homeTownAddress) {
		HomeTownAddress = homeTownAddress;
	}

	public EmployeeBean getEmpBean() {
		return empBean;
	}

	public void setEmpBean(EmployeeBean empBean) {
		this.empBean = empBean;
	}

	public int getNoOfAdultsTickets() {
		return noOfAdultsTickets;
	}

	public void setNoOfAdultsTickets(int noOfAdultsTickets) {
		this.noOfAdultsTickets = noOfAdultsTickets;
	}

	public int getNoOfChildrenTickets() {
		return noOfChildrenTickets;
	}

	public void setNoOfChildrenTickets(int noOfChildrenTickets) {
		this.noOfChildrenTickets = noOfChildrenTickets;
	}

	public int getNod() {
		return nod;
	}

	public void setNod(int nod) {
		this.nod = nod;
	}

	public List<LtcYearsDTO> getLtcYearsList() {
		return ltcYearsList;
	}

	public void setLtcYearsList(List<LtcYearsDTO> ltcYearsList) {
		this.ltcYearsList = ltcYearsList;
	}

	public List<LtcYearsDTO> getLtcYearList() {
		return ltcYearList;
	}

	public void setLtcYearList(List<LtcYearsDTO> ltcYearList) {
		this.ltcYearList = ltcYearList;
	}

	public DepartmentsDTO getDeptDTO() {
		return deptDTO;
	}

	public void setDeptDTO(DepartmentsDTO deptDTO) {
		this.deptDTO = deptDTO;
	}

	public EmployeeBean getEmpDetailsList() {
		return empDetailsList;
	}

	public void setEmpDetailsList(EmployeeBean empDetailsList) {
		this.empDetailsList = empDetailsList;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLtcRequestType() {
		return ltcRequestType;
	}

	public void setLtcRequestType(String ltcRequestType) {
		this.ltcRequestType = ltcRequestType;
	}

	public String getTypeOfLtc() {
		return typeOfLtc;
	}

	public void setTypeOfLtc(String typeOfLtc) {
		this.typeOfLtc = typeOfLtc;
	}

	public String getLtcYear() {
		return ltcYear;
	}

	public void setLtcYear(String ltcYear) {
		this.ltcYear = ltcYear;
	}

	public String getHometownAddr() {
		return hometownAddr;
	}

	public void setHometownAddr(String hometownAddr) {
		this.hometownAddr = hometownAddr;
	}


	

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	

}
