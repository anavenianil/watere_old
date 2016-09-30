package com.callippus.web.tada.dto;

import java.util.Date;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;


public class TadaWaterApprovalRequestDTO{
	
	private String name;
	private int designation;
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
	private String ipAddress;
	private String deptName;
	private String status;
	
	private String creationDate;
	
	private int desigId;
	
	private String dvno;
	private Date dvDate;

	private String cashorcheck;

	private String advance;

	private int actualExpenditure;

	private String settleOrReim;

	private String selOrReimRemarks;

	private Date settleOrReimApplyDate;
	
	
	private int settleOrReimAmt;
	
	private Date waterSettlementDate;
	
	private String settlementRemarks;
	
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
	
	private int stageStatus;
	
	
	private String stageStatus1; 
	private int deptId;
	
	
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getStageStatus1() {
		return stageStatus1;
	}
	public void setStageStatus1(String stageStatus1) {
		this.stageStatus1 = stageStatus1;
	}
	public int getStageStatus() {
		return stageStatus;
	}
	public void setStageStatus(int stageStatus) {
		this.stageStatus = stageStatus;
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
	public int getActualExpenditure() {
		return actualExpenditure;
	}
	public void setActualExpenditure(int actualExpenditure) {
		this.actualExpenditure = actualExpenditure;
	}
	public String getSettleOrReim() {
		return settleOrReim;
	}
	public void setSettleOrReim(String settleOrReim) {
		this.settleOrReim = settleOrReim;
	}
	public String getSelOrReimRemarks() {
		return selOrReimRemarks;
	}
	public void setSelOrReimRemarks(String selOrReimRemarks) {
		this.selOrReimRemarks = selOrReimRemarks;
	}
	public Date getSettleOrReimApplyDate() {
		return settleOrReimApplyDate;
	}
	public void setSettleOrReimApplyDate(Date settleOrReimApplyDate) {
		this.settleOrReimApplyDate = settleOrReimApplyDate;
	}
	public int getSettleOrReimAmt() {
		return settleOrReimAmt;
	}
	public void setSettleOrReimAmt(int settleOrReimAmt) {
		this.settleOrReimAmt = settleOrReimAmt;
	}
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public String getDvno() {
		return dvno;
	}
	public void setDvno(String dvno) {
		this.dvno = dvno;
	}
	public Date getDvDate() {
		return dvDate;
	}
	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}
	public String getCashorcheck() {
		return cashorcheck;
	}
	public void setCashorcheck(String cashorcheck) {
		this.cashorcheck = cashorcheck;
	}
	public int getDesigId() {
		return desigId;
	}
	public void setDesigId(int desigId) {
		this.desigId = desigId;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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
	private String requestId;
	private String requestID;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int i) {
		this.designation = i;
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
	public String getClaimPurpose() {
		return claimPurpose;
	}
	public void setClaimPurpose(String claimPurpose) {
		this.claimPurpose = claimPurpose;
	}
	public String getTravellingTo() {
		return travellingTo;
	}
	public void setTravellingTo(String travellingTo) {
		this.travellingTo = travellingTo;
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
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
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
	public DepartmentsDTO getDeptDTO() {
		return deptDTO;
	}
	public void setDeptDTO(DepartmentsDTO deptDTO) {
		this.deptDTO = deptDTO;
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
}
