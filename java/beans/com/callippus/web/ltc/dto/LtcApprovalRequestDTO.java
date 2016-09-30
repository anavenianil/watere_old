package com.callippus.web.ltc.dto;

import java.util.Date;
import java.util.List;

public class LtcApprovalRequestDTO {
	private String requestId;
	private String sfID;
	private String designationId;
	private String departmentId;
	private String phoneNum;
	private String ltcTypeId;
	private String addressId;
	private String placeOfVisit;
	private String nearestRlyStation;
	private Date departureDate;
	private String leaveRequestId;
	private String elEncashFlag;
	private String encashmentDays;
	private String prevAdvanceDate;
	private String prevAmount;
	private String prevVisitPlace;
	private String prevAdvanceReason;
	private String spouseEmploymentFlag;
	private int status;
	private String creationDate;
	private String createdBy;
	private String ltcBlockYearId;
	private String doPartNo;
	private String doPartDate;
	private List<LtcMemberDetailsDTO> ltcMemberDetails;
	private String unitFormation;
	private String claimPreferredOn;
	private String modeOfPayment;
	private String totalAmount;
	private String encashTypeId;
	private String empRoleId;
	private String ipAddress;
	private String issuedAmount;
	private int doPartId;
	private String gazType;
	private String requestType;
	private String ltcRequestType;
	private String amendmentRefRequestID;
	private String settlementAmount;
	private Date returnDate;
	private String escortDetails;
	private String excessAmount;
	private String excessAmountFine;
	private String sanctionNo;
	private String billNo;
	private String encashmentAmount;
	private Date dvDate;
	private String dvNo;
	private String leaveStatus;
	private String block;
	private String ltcType;
	private String members;
	private String leaveId;
	private LtcPenalMasterDTO ltcPenalMasterDTO;
	private String financeEncashmentAmt;
	private String cdaEncashmentAmt;
	private String advrequestid;
	private String reimrequestid;
	private int advreqhistoryid;
	private int reimhistoryid;
	private String statusMsg;
	private String back;
	private String message;
	private String historyID;
	private String requestidApprival;
    private int  historyrequestidApprival;
    private int amphistoryid;
    private String amprequestid;	
    private String orgroleIdLink;
    private String reimrequestidApp;
    private int reimhistoryidApp;
    private int penalNoOfDays;
   // private String penalNoOfDays;
    private int  ltcpenalNoofDays;
 
   /* private MROPaymentDetailsDTO mroPaymentDetails;
    private FinanceDetailsDTO financeDetailsDTO;
    private MRODetailsDTO mroDetails;  
   */
	
	public int getAmphistoryid() {
	return amphistoryid;
}

public int getPenalNoOfDays() {
		return penalNoOfDays;
	}

	public void setPenalNoOfDays(int penalNoOfDays) {
		this.penalNoOfDays = penalNoOfDays;
	}

/*public String getPenalNoOfDays() {
		return penalNoOfDays;
	}

	public void setPenalNoOfDays(String penalNoOfDays) {
		this.penalNoOfDays = penalNoOfDays;
	}
*/
public void setAmphistoryid(int amphistoryid) {
	this.amphistoryid = amphistoryid;
}

public String getAmprequestid() {
	return amprequestid;
}

public void setAmprequestid(String amprequestid) {
	this.amprequestid = amprequestid;
}


	public String getOrgroleIdLink() {
		return orgroleIdLink;
	}

	public void setOrgroleIdLink(String orgroleIdLink) {
		this.orgroleIdLink = orgroleIdLink;
	}


	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getAdvrequestid() {
		return advrequestid;
	}

	public String getReimrequestid() {
		return reimrequestid;
	}

	public int getAdvreqhistoryid() {
		return advreqhistoryid;
	}

	public int getReimhistoryid() {
		return reimhistoryid;
	}

	public void setAdvrequestid(String advrequestid) {
		this.advrequestid = advrequestid;
	}

	public void setReimrequestid(String reimrequestid) {
		this.reimrequestid = reimrequestid;
	}

	public void setAdvreqhistoryid(int advreqhistoryid) {
		this.advreqhistoryid = advreqhistoryid;
	}

	public void setReimhistoryid(int reimhistoryid) {
		this.reimhistoryid = reimhistoryid;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public Date getDvDate() {
		return dvDate;
	}

	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}

	public String getDvNo() {
		return dvNo;
	}

	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}

	public String getEncashmentAmount() {
		return encashmentAmount;
	}

	public void setEncashmentAmount(String encashmentAmount) {
		this.encashmentAmount = encashmentAmount;
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

	public String getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(String excessAmount) {
		this.excessAmount = excessAmount;
	}

	public String getEscortDetails() {
		return escortDetails;
	}

	public void setEscortDetails(String escortDetails) {
		this.escortDetails = escortDetails;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public String getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getEmpRoleId() {
		return empRoleId;
	}

	public void setEmpRoleId(String empRoleId) {
		this.empRoleId = empRoleId;
	}

	public String getEncashTypeId() {
		return encashTypeId;
	}

	public void setEncashTypeId(String encashTypeId) {
		this.encashTypeId = encashTypeId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getClaimPreferredOn() {
		return claimPreferredOn;
	}

	public void setClaimPreferredOn(String claimPreferredOn) {
		this.claimPreferredOn = claimPreferredOn;
	}

	public String getUnitFormation() {
		return unitFormation;
	}

	public void setUnitFormation(String unitFormation) {
		this.unitFormation = unitFormation;
	}

	public List<LtcMemberDetailsDTO> getLtcMemberDetails() {
		return ltcMemberDetails;
	}

	public void setLtcMemberDetails(List<LtcMemberDetailsDTO> ltcMemberDetails) {
		this.ltcMemberDetails = ltcMemberDetails;
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

	public String getLtcTypeId() {
		return ltcTypeId;
	}

	public void setLtcTypeId(String ltcTypeId) {
		this.ltcTypeId = ltcTypeId;
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

	public String getNearestRlyStation() {
		return nearestRlyStation;
	}

	public void setNearestRlyStation(String nearestRlyStation) {
		this.nearestRlyStation = nearestRlyStation;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getLeaveRequestId() {
		return leaveRequestId;
	}

	public void setLeaveRequestId(String leaveRequestId) {
		this.leaveRequestId = leaveRequestId;
	}

	public String getElEncashFlag() {
		return elEncashFlag;
	}

	public void setElEncashFlag(String elEncashFlag) {
		this.elEncashFlag = elEncashFlag;
	}

	public String getEncashmentDays() {
		return encashmentDays;
	}

	public void setEncashmentDays(String encashmentDays) {
		this.encashmentDays = encashmentDays;
	}

	public String getPrevAdvanceDate() {
		return prevAdvanceDate;
	}

	public void setPrevAdvanceDate(String prevAdvanceDate) {
		this.prevAdvanceDate = prevAdvanceDate;
	}

	public String getPrevAmount() {
		return prevAmount;
	}

	public void setPrevAmount(String prevAmount) {
		this.prevAmount = prevAmount;
	}

	public String getPrevVisitPlace() {
		return prevVisitPlace;
	}

	public void setPrevVisitPlace(String prevVisitPlace) {
		this.prevVisitPlace = prevVisitPlace;
	}

	public String getPrevAdvanceReason() {
		return prevAdvanceReason;
	}

	public void setPrevAdvanceReason(String prevAdvanceReason) {
		this.prevAdvanceReason = prevAdvanceReason;
	}

	public String getSpouseEmploymentFlag() {
		return spouseEmploymentFlag;
	}

	public void setSpouseEmploymentFlag(String spouseEmploymentFlag) {
		this.spouseEmploymentFlag = spouseEmploymentFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLtcBlockYearId() {
		return ltcBlockYearId;
	}

	public void setLtcBlockYearId(String ltcBlockYearId) {
		this.ltcBlockYearId = ltcBlockYearId;
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

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getLtcType() {
		return ltcType;
	}

	public void setLtcType(String ltcType) {
		this.ltcType = ltcType;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public void setExcessAmountFine(String excessAmountFine) {
		this.excessAmountFine = excessAmountFine;
	}

	public String getExcessAmountFine() {
		return excessAmountFine;
	}

	public void setLtcPenalMasterDTO(LtcPenalMasterDTO ltcPenalMasterDTO) {
		this.ltcPenalMasterDTO = ltcPenalMasterDTO;
	}

	public LtcPenalMasterDTO getLtcPenalMasterDTO() {
		return ltcPenalMasterDTO;
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

	public String getStatusMsg() {
		return statusMsg;
	}

	public String getBack() {
		return back;
	}

	public String getMessage() {
		return message;
	}

	
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestidApprival() {
		return requestidApprival;
	}

	public void setRequestidApprival(String requestidApprival) {
		this.requestidApprival = requestidApprival;
	}

	public int getHistoryrequestidApprival() {
		return historyrequestidApprival;
	}

	public void setHistoryrequestidApprival(int historyrequestidApprival) {
		this.historyrequestidApprival = historyrequestidApprival;
	}

	public String getReimrequestidApp() {
		return reimrequestidApp;
	}

	public void setReimrequestidApp(String reimrequestidApp) {
		this.reimrequestidApp = reimrequestidApp;
	}

	public int getReimhistoryidApp() {
		return reimhistoryidApp;
	}

	public void setReimhistoryidApp(int reimhistoryidApp) {
		this.reimhistoryidApp = reimhistoryidApp;
	}

	public int getLtcpenalNoofDays() {
		return ltcpenalNoofDays;
	}

	public void setLtcpenalNoofDays(int ltcpenalNoofDays) {
		this.ltcpenalNoofDays = ltcpenalNoofDays;
	}

	
}