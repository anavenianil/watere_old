package com.callippus.web.ltc.dto;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

public class LtcAdvanceRequestDTO {
	private String id;
	private String requestId;
	private String sfID;
	private String designationId;
	private String departmentId;
	private String phoneNum;
	private String gradePay;
	private String employmentTypeId;
	private String ltcTypeId;
	private String ltcBlockYearId;
	private String leaveRequestId;
	private String addressId;
	private String placeOfVisit;
	private Date departureDate;
	private Date returnDate;
	private String amountClaimed;
	private String amountClaimedInwords;
	private String nearestRlyStation;
	private String amountPerPerson;
	private String noOfTickets;
	private String issuedAmount;
	private String issuedAmountInwords;
	private String appliedBy;
	private String appliedDate;
	private String doPartNo;
	private String doPartDate;
	private List<LtcMemberDetailsDTO> ltcMemberDetails;
	private HttpSession session;
	private int status;
	private String encashmentDays;
	private String encashTypeId;
	private String nearesrAirport;
	private String empRoleId;
	private String ipAddress;
	private String referenceRequestID;
	private String ltcType;
	private String ltcBlockYear;
	private int doPartId;
	private String gazType;
	private String advanceCfgValue;
	private String cdaAmount;
	private String sanctionNo;
	private String billNo;
	private Date dvDate;
	private String cfaSign;
	private String accountentSign;
	private String dvNo;
	private String ltcRequestType;
	private String amendmentRefRequestID;
	private String settlementAmount;
	private String amountPerEachInfant;
	private String noOfInfantTickets;
	private String escortDetails;
	private String excessAmount;
	private String excessAmountFine;
	private String MROPaidNo;
	private Date MROPaidDate;
	private String encashmentAmount;
	private String financeEncashmentAmt;
	private String cdaEncashmentAmt;
	private String leaveStatus;
	private String leaveID;
	private String doNo;
private String reimrequestid;
private String setlrequestid;
private int reimbhistoryid;
private int setlhistoryid;
private String  back;
private String  message;
private String  statusMsg;
	public String getDoNo() {
		return doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public String getLeaveID() {
		return leaveID;
	}

	public void setLeaveID(String leaveID) {
		this.leaveID = leaveID;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getEncashmentAmount() {
		return encashmentAmount;
	}

	public void setEncashmentAmount(String encashmentAmount) {
		this.encashmentAmount = encashmentAmount;
	}

	public String getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(String excessAmount) {
		this.excessAmount = excessAmount;
	}

	public String getExcessAmountFine() {
		return excessAmountFine;
	}

	public void setExcessAmountFine(String excessAmountFine) {
		this.excessAmountFine = excessAmountFine;
	}

	public String getMROPaidNo() {
		return MROPaidNo;
	}

	public void setMROPaidNo(String mROPaidNo) {
		MROPaidNo = mROPaidNo;
	}

	public Date getMROPaidDate() {
		return MROPaidDate;
	}

	public void setMROPaidDate(Date mROPaidDate) {
		MROPaidDate = mROPaidDate;
	}

	public String getEscortDetails() {
		return escortDetails;
	}

	public void setEscortDetails(String escortDetails) {
		this.escortDetails = escortDetails;
	}

	public String getAmountPerEachInfant() {
		return amountPerEachInfant;
	}

	public void setAmountPerEachInfant(String amountPerEachInfant) {
		this.amountPerEachInfant = amountPerEachInfant;
	}

	public String getNoOfInfantTickets() {
		return noOfInfantTickets;
	}

	public void setNoOfInfantTickets(String noOfInfantTickets) {
		this.noOfInfantTickets = noOfInfantTickets;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getLtcRequestType() {
		return ltcRequestType;
	}

	public void setLtcRequestType(String ltcRequestType) {
		this.ltcRequestType = ltcRequestType;
	}

	public String getAmendmentRefRequestID() {
		return amendmentRefRequestID;
	}

	public void setAmendmentRefRequestID(String amendmentRefRequestID) {
		this.amendmentRefRequestID = amendmentRefRequestID;
	}

	public String getDvNo() {
		return dvNo;
	}

	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}

	public String getCdaAmount() {
		return cdaAmount;
	}

	public void setCdaAmount(String cdaAmount) {
		this.cdaAmount = cdaAmount;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getDvDate() {
		return dvDate;
	}

	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}

	public String getCfaSign() {
		return cfaSign;
	}

	public void setCfaSign(String cfaSign) {
		this.cfaSign = cfaSign;
	}

	public String getAccountentSign() {
		return accountentSign;
	}

	public void setAccountentSign(String accountentSign) {
		this.accountentSign = accountentSign;
	}

	public String getAdvanceCfgValue() {
		return advanceCfgValue;
	}

	public void setAdvanceCfgValue(String advanceCfgValue) {
		this.advanceCfgValue = advanceCfgValue;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public int getDoPartId() {
		return doPartId;
	}

	public void setDoPartId(int doPartId) {
		this.doPartId = doPartId;
	}

	public String getLtcType() {
		return ltcType;
	}

	public void setLtcType(String ltcType) {
		this.ltcType = ltcType;
	}

	public String getLtcBlockYear() {
		return ltcBlockYear;
	}

	public void setLtcBlockYear(String ltcBlockYear) {
		this.ltcBlockYear = ltcBlockYear;
	}

	public String getReferenceRequestID() {
		return referenceRequestID;
	}

	public void setReferenceRequestID(String referenceRequestID) {
		this.referenceRequestID = referenceRequestID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getEncashmentDays() {
		return encashmentDays;
	}

	public void setEncashmentDays(String encashmentDays) {
		this.encashmentDays = encashmentDays;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpRoleId() {
		return empRoleId;
	}

	public void setEmpRoleId(String empRoleId) {
		this.empRoleId = empRoleId;
	}

	public String getNearesrAirport() {
		return nearesrAirport;
	}

	public void setNearesrAirport(String nearesrAirport) {
		this.nearesrAirport = nearesrAirport;
	}

	public String getNearestRlyStation() {
		return nearestRlyStation;
	}

	public void setNearestRlyStation(String nearestRlyStation) {
		this.nearestRlyStation = nearestRlyStation;
	}

	public String getEncashTypeId() {
		return encashTypeId;
	}

	public void setEncashTypeId(String encashTypeId) {
		this.encashTypeId = encashTypeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public List<LtcMemberDetailsDTO> getLtcMemberDetails() {
		return ltcMemberDetails;
	}

	public void setLtcMemberDetails(List<LtcMemberDetailsDTO> ltcMemberDetails) {
		this.ltcMemberDetails = ltcMemberDetails;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getEmploymentTypeId() {
		return employmentTypeId;
	}

	public void setEmploymentTypeId(String employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}

	public String getLtcTypeId() {
		return ltcTypeId;
	}

	public void setLtcTypeId(String ltcTypeId) {
		this.ltcTypeId = ltcTypeId;
	}

	public String getLtcBlockYearId() {
		return ltcBlockYearId;
	}

	public void setLtcBlockYearId(String ltcBlockYearId) {
		this.ltcBlockYearId = ltcBlockYearId;
	}

	public String getLeaveRequestId() {
		return leaveRequestId;
	}

	public void setLeaveRequestId(String leaveRequestId) {
		this.leaveRequestId = leaveRequestId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getPlaceOfVisit() {
		return placeOfVisit;
	}

	public void setPlaceOfVisit(String placeOfVisit) {
		this.placeOfVisit = placeOfVisit;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(String amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public String getAmountClaimedInwords() {
		return amountClaimedInwords;
	}

	public void setAmountClaimedInwords(String amountClaimedInwords) {
		this.amountClaimedInwords = amountClaimedInwords;
	}

	public String getAmountPerPerson() {
		return amountPerPerson;
	}

	public void setAmountPerPerson(String amountPerPerson) {
		this.amountPerPerson = amountPerPerson;
	}

	public String getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(String noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	public String getIssuedAmountInwords() {
		return issuedAmountInwords;
	}

	public void setIssuedAmountInwords(String issuedAmountInwords) {
		this.issuedAmountInwords = issuedAmountInwords;
	}

	public String getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(String appliedBy) {
		this.appliedBy = appliedBy;
	}

	public String getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public String getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(String doPartDate) {
		this.doPartDate = doPartDate;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setFinanceEncashmentAmt(String financeEncashmentAmt) {
		this.financeEncashmentAmt = financeEncashmentAmt;
	}

	public String getFinanceEncashmentAmt() {
		return financeEncashmentAmt;
	}

	public void setCdaEncashmentAmt(String cdaEncashmentAmt) {
		this.cdaEncashmentAmt = cdaEncashmentAmt;
	}

	public String getCdaEncashmentAmt() {
		return cdaEncashmentAmt;
	}

	public String getReimrequestid() {
		return reimrequestid;
	}

	public String getSetlrequestid() {
		return setlrequestid;
	}

	public void setReimrequestid(String reimrequestid) {
		this.reimrequestid = reimrequestid;
	}

	public void setSetlrequestid(String setlrequestid) {
		this.setlrequestid = setlrequestid;
	}

	public int getReimbhistoryid() {
		return reimbhistoryid;
	}

	public void setReimbhistoryid(int reimbhistoryid) {
		this.reimbhistoryid = reimbhistoryid;
	}

	public String getBack() {
		return back;
	}

	public String getMessage() {
		return message;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public int getSetlhistoryid() {
		return setlhistoryid;
	}

	public void setSetlhistoryid(int setlhistoryid) {
		this.setlhistoryid = setlhistoryid;
	}

}