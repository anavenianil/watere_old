package com.callippus.web.ltc.dto;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.callippus.web.beans.requests.RequestBean;

public class LTCWaterRequestProcessBean extends RequestBean {
	
	private HttpSession session;
private String sfID;
	
	private String designation;
	private String deptName;
	private  int status;
	private String phnNo;
	private String typeOfLtc;
	private String ltcYear;
	private String hometownAddr;
	private Date startHoliday;
	private Date returnHoliday;
	private int nod;
	private int noOfAdultsTickets;
	private int noOfChildrenTickets;
	private String leaveType;
	private Date appliedDate;
	
	private String requestID;
	private String requestId;
	private int totalTickets;
	
	private String creationDate;
	private Date applyDate;
	private int desigId;
	
	private String advance;
	
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
	
	
	private int ltcactualExpenditure;   
	private int ltcsettleOrReimAmt;
	private String ltcselOrReimRemarks;
	private Date ltcsettleOrReimApplyDate;
	private String ltcsettleOrReim;
	
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

	public String getLtcsettleOrReim() {
		return ltcsettleOrReim;
	}

	public void setLtcsettleOrReim(String ltcsettleOrReim) {
		this.ltcsettleOrReim = ltcsettleOrReim;
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

	public Date getLtcadvAdminDvDate() {
		return ltcadvAdminDvDate;
	}

	public void setLtcadvAdminDvDate(Date ltcadvAdminDvDate) {
		this.ltcadvAdminDvDate = ltcadvAdminDvDate;
	}

	public String getLtcadvChequeNo() {
		return ltcadvChequeNo;
	}

	public void setLtcadvChequeNo(String ltcadvChequeNo) {
		this.ltcadvChequeNo = ltcadvChequeNo;
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

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public String getPhnNo() {
		return phnNo;
	}

	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
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

	public int getNod() {
		return nod;
	}

	public void setNod(int nod) {
		this.nod = nod;
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

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public int getDesigId() {
		return desigId;
	}

	public void setDesigId(int desigId) {
		this.desigId = desigId;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
	

}
