package com.callippus.web.tada.beans.request;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.tada.dto.BankNamesDTO;
import com.callippus.web.tada.dto.TadaWaterApprovalRequestDTO;

public class TadaWaterRequestBean {

	/*private String requestId;
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}*/
	private String name;
	private String designation;
	private String sfid;
	private String phnNo;
	private String claimPurpose;
	private String travellingTo;
	private Date fromDate;
	private Date toDate;
	private int foodandAccmAmt;
	private int daAmt;
	private int taxiAmt;
	private int transitAmt;
	private int totalAmt;
	private Date applyDate;
	private String param;
	private int noOfDays;
	private int perDayFoodandAccmAmt;
	private EmployeeBean empDetailsList;
	private String sfID;
	private DepartmentsDTO deptDTO;
	private String reason;
	
	private String requestId;
	private String requestID;
	
	private String ipAddress;
	
	private String deptName;
	
	private String status;
	
	private String result;
	
	private String type;
	
	private String remarks;
	
	private String message;
	
	private String historyID;
	
	private String back;
	
	private String statusMsg;
	
	
	private String dvno;
private Date dvDate;

	private String cashorcheck;
	private String advance;
	
	private int actualExpenditure;
	
	private String settleOrReim;
	
	private String selOrReimRemarks;
	
	private Date settleOrReimApplyDate;
	
	
	
	private int settleOrReimAmt;
	
	

	
	private String settlementRemarks;
	
	private Date waterSettlementDate;
	
	private Date waterReimbursementDate;
	
	private String reimbursementRemarks;
	
	private String reimcashorcheck;
	
	private Date reimDvDate;
	private String reimDvno;
	
	
	
	private String advBankName;
	private String advBranchName;
	private String advChequeNo;
	private String advDvno;
	private Date advDvDate;
	
	
	private String reimBankName;
	private String reimBranchName;
	private String reimChequeNo;
	private String reimAdminDvno;
	private Date reimAdminDvDate;
	
	//private int stage_Status;
	private int stageStatus;
	
	private List<BankNamesDTO> bankNamesList;
	
	
	

	public int getStageStatus() {
		return stageStatus;
	}
	public void setStageStatus(int stageStatus) {
		this.stageStatus = stageStatus;
	}
	public List<BankNamesDTO> getBankNamesList() {
		return bankNamesList;
	}
	public void setBankNamesList(List<BankNamesDTO> bankNamesList) {
		this.bankNamesList = bankNamesList;
	}
	public String getReimBankName() {
		return reimBankName;
	}
	public void setReimBankName(String reimBankName) {
		this.reimBankName = reimBankName;
	}
	public String getReimBranchName() {
		return reimBranchName;
	}
	public void setReimBranchName(String reimBranchName) {
		this.reimBranchName = reimBranchName;
	}
	public String getReimChequeNo() {
		return reimChequeNo;
	}
	public void setReimChequeNo(String reimChequeNo) {
		this.reimChequeNo = reimChequeNo;
	}
	public String getReimAdminDvno() {
		return reimAdminDvno;
	}
	public void setReimAdminDvno(String reimAdminDvno) {
		this.reimAdminDvno = reimAdminDvno;
	}
	public Date getReimAdminDvDate() {
		return reimAdminDvDate;
	}
	public void setReimAdminDvDate(Date reimAdminDvDate) {
		this.reimAdminDvDate = reimAdminDvDate;
	}
	public String getAdvBankName() {
		return advBankName;
	}
	public void setAdvBankName(String advBankName) {
		this.advBankName = advBankName;
	}
	public String getAdvBranchName() {
		return advBranchName;
	}
	public void setAdvBranchName(String advBranchName) {
		this.advBranchName = advBranchName;
	}
	public String getAdvChequeNo() {
		return advChequeNo;
	}
	public void setAdvChequeNo(String advChequeNo) {
		this.advChequeNo = advChequeNo;
	}
	public String getAdvDvno() {
		return advDvno;
	}
	public void setAdvDvno(String advDvno) {
		this.advDvno = advDvno;
	}
	public Date getAdvDvDate() {
		return advDvDate;
	}
	public void setAdvDvDate(Date advDvDate) {
		this.advDvDate = advDvDate;
	}
	public String getReimcashorcheck() {
		return reimcashorcheck;
	}
	public void setReimcashorcheck(String reimcashorcheck) {
		this.reimcashorcheck = reimcashorcheck;
	}
	public Date getReimDvDate() {
		return reimDvDate;
	}
	public void setReimDvDate(Date reimDvDate) {
		this.reimDvDate = reimDvDate;
	}
	public String getReimDvno() {
		return reimDvno;
	}
	public void setReimDvno(String reimDvno) {
		this.reimDvno = reimDvno;
	}
	public Date getWaterReimbursementDate() {
		return waterReimbursementDate;
	}
	public void setWaterReimbursementDate(Date waterReimbursementDate) {
		this.waterReimbursementDate = waterReimbursementDate;
	}
	public String getReimbursementRemarks() {
		return reimbursementRemarks;
	}
	public void setReimbursementRemarks(String reimbursementRemarks) {
		this.reimbursementRemarks = reimbursementRemarks;
	}
	public Date getWaterSettlementDate() {
		return waterSettlementDate;
	}
	public void setWaterSettlementDate(Date waterSettlementDate) {
		this.waterSettlementDate = waterSettlementDate;
	}
	public String getSettlementRemarks() {
		return settlementRemarks;
	}
	public void setSettlementRemarks(String settlementRemarks) {
		this.settlementRemarks = settlementRemarks;
	}
	public int getSettleOrReimAmt() {
		return settleOrReimAmt;
	}
	public void setSettleOrReimAmt(int settleOrReimAmt) {
		this.settleOrReimAmt = settleOrReimAmt;
	}
	public Date getSettleOrReimApplyDate() {
		return settleOrReimApplyDate;
	}
	public void setSettleOrReimApplyDate(Date settleOrReimApplyDate) {
		this.settleOrReimApplyDate = settleOrReimApplyDate;
	}
	public String getSelOrReimRemarks() {
		return selOrReimRemarks;
	}
	public void setSelOrReimRemarks(String selOrReimRemarks) {
		this.selOrReimRemarks = selOrReimRemarks;
	}
	public String getSettleOrReim() {
		return settleOrReim;
	}
	public void setSettleOrReim(String settleOrReim) {
		this.settleOrReim = settleOrReim;
	}
	public int getActualExpenditure() {
		return actualExpenditure;
	}
	public void setActualExpenditure(int actualExpenditure) {
		this.actualExpenditure = actualExpenditure;
	}
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public Date getDvDate() {
		return dvDate;
	}
	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}
	public String getDvno() {
		return dvno;
	}
	public void setDvno(String dvno) {
		this.dvno = dvno;
	}
	public String getCashorcheck() {
		return cashorcheck;
	}
	public void setCashorcheck(String cashorcheck) {
		this.cashorcheck = cashorcheck;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	//added by bkr 07/05/2016
    private TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO;
	
	public TadaWaterApprovalRequestDTO getTadaWaterApprovalRequestDTO() {
		return tadaWaterApprovalRequestDTO;
	}
	public void setTadaWaterApprovalRequestDTO(
			TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO) {
		this.tadaWaterApprovalRequestDTO = tadaWaterApprovalRequestDTO;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private List<SingingAuthorityDTO> accountOfficerList;
	
	
	public List<SingingAuthorityDTO> getAccountOfficerList() {
		return accountOfficerList;
	}
	public void setAccountOfficerList(List<SingingAuthorityDTO> accountOfficerList) {
		this.accountOfficerList = accountOfficerList;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public int getPerDayFoodandAccmAmt() {
		return perDayFoodandAccmAmt;
	}
	public void setPerDayFoodandAccmAmt(int perDayFoodandAccmAmt) {
		this.perDayFoodandAccmAmt = perDayFoodandAccmAmt;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getPhnNo() {
		return phnNo;
	}
	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
	}
	public String getTravellingTo() {
		return travellingTo;
	}
	public void setTravellingTo(String travellingTo) {
		this.travellingTo = travellingTo;
	}
	public String getClaimPurpose() {
		return claimPurpose;
	}
	public void setClaimPurpose(String claimPurpose) {
		this.claimPurpose = claimPurpose;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public int getFoodandAccmAmt() {
		return foodandAccmAmt;
	}
	public void setFoodandAccmAmt(int foodandAccmAmt) {
		this.foodandAccmAmt = foodandAccmAmt;
	}
	public int getDaAmt() {
		return daAmt;
	}
	public void setDaAmt(int daAmt) {
		this.daAmt = daAmt;
	}
	public int getTaxiAmt() {
		return taxiAmt;
	}
	public void setTaxiAmt(int taxiAmt) {
		this.taxiAmt = taxiAmt;
	}
	public int getTransitAmt() {
		return transitAmt;
	}
	public void setTransitAmt(int transitAmt) {
		this.transitAmt = transitAmt;
	}
	public int getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}

}
