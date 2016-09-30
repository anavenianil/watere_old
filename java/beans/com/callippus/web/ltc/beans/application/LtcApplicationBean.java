package com.callippus.web.ltc.beans.application;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.CancelLeaveRequestBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestCommonBean;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.leave.dto.LeaveEncashmentDTO;
import com.callippus.web.ltc.dto.LtcAdvanceRequestDTO;
import com.callippus.web.ltc.dto.LtcApprovalRequestDTO;
import com.callippus.web.ltc.dto.LtcBlockMasterDTO;
import com.callippus.web.ltc.dto.LtcEncashmentDTO;
import com.callippus.web.ltc.dto.LtcMemberDetailsDTO;
import com.callippus.web.ltc.dto.LtcTypeMasterDTO;

public class LtcApplicationBean {
	private String id;
	private String name;
	private String param;
	private String type;
	private String message;
	private String sfID;
	private String typeValue;
	private String description;
	private List<LtcTypeMasterDTO> ltcTypeDetails;
	private List<LtcBlockMasterDTO> ltcBlockDetails;
	private EmployeeBean empBean;
	private List<KeyValueDTO> ltcLeaveTypeList;
	private AddressBean HomeTownAddress;
	private List<FamilyBean> FamilyMemberDetails;
	private List<KeyValueDTO> ltcBlockYearList;
	private String ltcBlockYear;
	private String ltcAmendmentBlockYear;
	private String fromDate;
	private String toDate;
	private String ltcBlockId;
	private String extendedDate;
	private int status;
	private String createdBy;
	private String creationDate;
	private String lastModifiedBy;
	private String lastModifiedDate;
	@SuppressWarnings("unchecked")
	private List masterDataList;
	private String ltcTypeId;
	private String ltcBlockYearId;
	private String placeOfVisit;
	private String nearestRlyStation;
	private Date departureDate;
	private String leaveRequestId;
	private String elEncashFlag;
	private String encashmentDays;
	private String encashmentAmount;
	private String ltcAdvance;
	private String prevAdvanceDate;
	private String prevAmount;
	private String prevVisitPlace;
	private String prevAdvanceReason;
	private String spouseEmploymentFlag;
	private String jsonValue;
	private String designationId;
	private String departmentId;
	private String phoneNum;
	private String addressId;
	private List<LtcMemberDetailsDTO> ltcApproveDetailsList;
	private String doPartDate;
	private String doPartNo;
	private String leaveType;
	private String remarks;
	private String result;
	private String unitFormation;
	private String claimPreferred;
	private String requestIDs;
	private String modeOfPayment;
	private String issuedAmount;
	private String encashTypeId;
	private List<LeaveEncashmentDTO> leaveEncasList;
	private Date returnDate;
	private String amountClaimed;
	private String escortDetails;
	private String amountPerPerson;
	private String noOfTickets;
	private String tableName;
	private String totalAmount;
	private String fourYearBlockId;
	private String referenceRequestID;
	private LtcAdvanceRequestDTO ltcAdvanceRequestDTO;
	private List<SingingAuthorityDTO> accountOfficerList;
	private List<SingingAuthorityDTO> cfaOfficerList;
	private List<LtcApplicationBean> advanceList;
	private List<LtcApplicationBean> settlementList;
	private List<LtcApplicationBean> reimbursementList;
	private String accountentSign;
	private String cfaSign;
	private String sanctionNo;
	private String billNo;
	private String jsonValues;
	private String dvNo;
	private String dvDate;
	private String leaveDetails;
	private String familyMember;
	private String excessAmount;
	private String excessAmountFine;
	private String MROPaidNo;
	private String MROPaidDate;
	private String formatedDepartureDate;
	private String formatedReturnDate;
	private String historyID;
	private String amountPerEachInfant;
	private String noOfInfantTickets;
	private List<LtcApplicationBean> encashmentList;
	private String leaveCancelFlag;
	private String empRoleId;
	private String ipAddress;
	private String gazType;
	private String familyMemberId;
	private String refSfID;
	private List<LtcApprovalRequestDTO> adminEntryDetailsList;
	private String cancleType;
	private String changeLeaveFlag;
	private String cancelLeaveFlag;
	private LeaveRequestBean leaveRequestBean;
	private CancelLeaveRequestBean cancelLeaveRequestBean;
	private RequestCommonBean leaveRequestHistoryBean;
	private String requestID;
	private String ltcRequestType;
	private String requestId;
	private String statusMsg;
	private String back;
	private int penalInterest;
	private String fromDateOne;
	private String toDateOne;
	private String repSig;

	private String financeIssuedAmount;
	private String cancelReqDoPartId;
	private String cancelReqHistoryId;
	private String cancelReqIssuedAmount;
	private String cancelReqCdaAmount;
	private String cancelReqRequestId;
	private String cancelReqRequestType;
	private String validFromDate;
	private String  validToDate;
	private List<LtcApplicationBean> cdaadvanceList;
	private List<LtcApplicationBean> cdasettlementList;
	private List<LtcApplicationBean> cdareimbursementList;
	private List<LtcApplicationBean> cdaencashmentList;
    private Date appliedDate;
    private int daValue;
    private int basicPay;
    private String gradePay;
    private Integer enchashmentamountDavalue;
    private List<LtcEncashmentDTO> ltcEncashmentList;
    
	public List<LtcEncashmentDTO> getLtcEncashmentList() {
		return ltcEncashmentList;
	}

	public void setLtcEncashmentList(List<LtcEncashmentDTO> ltcEncashmentList) {
		this.ltcEncashmentList = ltcEncashmentList;
	}

	public List<LtcApplicationBean> getCdaadvanceList() {
		return cdaadvanceList;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public void setCdaadvanceList(List<LtcApplicationBean> cdaadvanceList) {
		this.cdaadvanceList = cdaadvanceList;
	}

	public List<LtcApplicationBean> getCdasettlementList() {
		return cdasettlementList;
	}

	public void setCdasettlementList(List<LtcApplicationBean> cdasettlementList) {
		this.cdasettlementList = cdasettlementList;
	}

	public List<LtcApplicationBean> getCdareimbursementList() {
		return cdareimbursementList;
	}

	public void setCdareimbursementList(
			List<LtcApplicationBean> cdareimbursementList) {
		this.cdareimbursementList = cdareimbursementList;
	}

	public List<LtcApplicationBean> getCdaencashmentList() {
		return cdaencashmentList;
	}

	public void setCdaencashmentList(List<LtcApplicationBean> cdaencashmentList) {
		this.cdaencashmentList = cdaencashmentList;
	}

	public String getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
	}

	public String getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}

	public String getCancelReqRequestType() {
		return cancelReqRequestType;
	}

	public void setCancelReqRequestType(String cancelReqRequestType) {
		this.cancelReqRequestType = cancelReqRequestType;
	}

	public String getCancelReqRequestId() {
		return cancelReqRequestId;
	}

	public void setCancelReqRequestId(String cancelReqRequestId) {
		this.cancelReqRequestId = cancelReqRequestId;
	}

	public String getFromDateOne() {
		return fromDateOne;
	}

	public void setFromDateOne(String fromDateOne) {
		this.fromDateOne = fromDateOne;
	}

	public String getToDateOne() {
		return toDateOne;
	}

	public void setToDateOne(String toDateOne) {
		this.toDateOne = toDateOne;
	}

	public int getPenalInterest() {
		return penalInterest;
	}

	public void setPenalInterest(int penalInterest) {
		this.penalInterest = penalInterest;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public LeaveRequestBean getLeaveRequestBean() {
		return leaveRequestBean;
	}

	public void setLeaveRequestBean(LeaveRequestBean leaveRequestBean) {
		this.leaveRequestBean = leaveRequestBean;
	}

	public CancelLeaveRequestBean getCancelLeaveRequestBean() {
		return cancelLeaveRequestBean;
	}

	public void setCancelLeaveRequestBean(CancelLeaveRequestBean cancelLeaveRequestBean) {
		this.cancelLeaveRequestBean = cancelLeaveRequestBean;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
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

	public String getLeaveCancelFlag() {
		return leaveCancelFlag;
	}

	public void setLeaveCancelFlag(String leaveCancelFlag) {
		this.leaveCancelFlag = leaveCancelFlag;
	}

	public List<LtcApplicationBean> getEncashmentList() {
		return encashmentList;
	}

	public void setEncashmentList(List<LtcApplicationBean> encashmentList) {
		this.encashmentList = encashmentList;
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

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getFormatedDepartureDate() {
		return formatedDepartureDate;
	}

	public void setFormatedDepartureDate(String formatedDepartureDate) {
		this.formatedDepartureDate = formatedDepartureDate;
	}

	public String getFormatedReturnDate() {
		return formatedReturnDate;
	}

	public void setFormatedReturnDate(String formatedReturnDate) {
		this.formatedReturnDate = formatedReturnDate;
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

	public String getMROPaidDate() {
		return MROPaidDate;
	}

	public void setMROPaidDate(String mROPaidDate) {
		MROPaidDate = mROPaidDate;
	}

	public String getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(String familyMember) {
		this.familyMember = familyMember;
	}

	public String getLeaveDetails() {
		return leaveDetails;
	}

	public void setLeaveDetails(String leaveDetails) {
		this.leaveDetails = leaveDetails;
	}

	public String getDvNo() {
		return dvNo;
	}

	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}

	public String getDvDate() {
		return dvDate;
	}

	public void setDvDate(String dvDate) {
		this.dvDate = dvDate;
	}

	public String getJsonValues() {
		return jsonValues;
	}

	public void setJsonValues(String jsonValues) {
		this.jsonValues = jsonValues;
	}

	public String getAccountentSign() {
		return accountentSign;
	}

	public void setAccountentSign(String accountentSign) {
		this.accountentSign = accountentSign;
	}

	public String getCfaSign() {
		return cfaSign;
	}

	public void setCfaSign(String cfaSign) {
		this.cfaSign = cfaSign;
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

	public List<LtcApplicationBean> getAdvanceList() {
		return advanceList;
	}

	public void setAdvanceList(List<LtcApplicationBean> advanceList) {
		this.advanceList = advanceList;
	}

	public List<LtcApplicationBean> getSettlementList() {
		return settlementList;
	}

	public void setSettlementList(List<LtcApplicationBean> settlementList) {
		this.settlementList = settlementList;
	}

	public List<LtcApplicationBean> getReimbursementList() {
		return reimbursementList;
	}

	public void setReimbursementList(List<LtcApplicationBean> reimbursementList) {
		this.reimbursementList = reimbursementList;
	}

	public List<SingingAuthorityDTO> getAccountOfficerList() {
		return accountOfficerList;
	}

	public void setAccountOfficerList(List<SingingAuthorityDTO> accountOfficerList) {
		this.accountOfficerList = accountOfficerList;
	}

	public List<SingingAuthorityDTO> getCfaOfficerList() {
		return cfaOfficerList;
	}

	public void setCfaOfficerList(List<SingingAuthorityDTO> cfaOfficerList) {
		this.cfaOfficerList = cfaOfficerList;
	}

	public LtcAdvanceRequestDTO getLtcAdvanceRequestDTO() {
		return ltcAdvanceRequestDTO;
	}

	public void setLtcAdvanceRequestDTO(LtcAdvanceRequestDTO ltcAdvanceRequestDTO) {
		this.ltcAdvanceRequestDTO = ltcAdvanceRequestDTO;
	}

	public String getReferenceRequestID() {
		return referenceRequestID;
	}

	public void setReferenceRequestID(String referenceRequestID) {
		this.referenceRequestID = referenceRequestID;
	}

	public String getFourYearBlockId() {
		return fourYearBlockId;
	}

	public void setFourYearBlockId(String fourYearBlockId) {
		this.fourYearBlockId = fourYearBlockId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public String getEscortDetails() {
		return escortDetails;
	}

	public void setEscortDetails(String escortDetails) {
		this.escortDetails = escortDetails;
	}

	public String getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(String amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public List<LeaveEncashmentDTO> getLeaveEncasList() {
		return leaveEncasList;
	}

	public void setLeaveEncasList(List<LeaveEncashmentDTO> leaveEncasList) {
		this.leaveEncasList = leaveEncasList;
	}

	public String getEncashTypeId() {
		return encashTypeId;
	}

	public void setEncashTypeId(String encashTypeId) {
		this.encashTypeId = encashTypeId;
	}

	public String getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getRequestIDs() {
		return requestIDs;
	}

	public void setRequestIDs(String requestIDs) {
		this.requestIDs = requestIDs;
	}

	public String getUnitFormation() {
		return unitFormation;
	}

	public void setUnitFormation(String unitFormation) {
		this.unitFormation = unitFormation;
	}

	public String getClaimPreferred() {
		return claimPreferred;
	}

	public void setClaimPreferred(String claimPreferred) {
		this.claimPreferred = claimPreferred;
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

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(String doPartDate) {
		this.doPartDate = doPartDate;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public List<LtcMemberDetailsDTO> getLtcApproveDetailsList() {
		return ltcApproveDetailsList;
	}

	public void setLtcApproveDetailsList(List<LtcMemberDetailsDTO> ltcApproveDetailsList) {
		this.ltcApproveDetailsList = ltcApproveDetailsList;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
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

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
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

	public String getLtcAdvance() {
		return ltcAdvance;
	}

	public void setLtcAdvance(String ltcAdvance) {
		this.ltcAdvance = ltcAdvance;
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

	public List getMasterDataList() {
		return masterDataList;
	}

	public void setMasterDataList(List masterDataList) {
		this.masterDataList = masterDataList;
	}

	public List<KeyValueDTO> getLtcLeaveTypeList() {
		return ltcLeaveTypeList;
	}

	public void setLtcLeaveTypeList(List<KeyValueDTO> ltcLeaveTypeList) {
		this.ltcLeaveTypeList = ltcLeaveTypeList;
	}

	public String getLtcBlockYear() {
		return ltcBlockYear;
	}

	public void setLtcBlockYear(String ltcBlockYear) {
		this.ltcBlockYear = ltcBlockYear;
	}

	public List<KeyValueDTO> getLtcBlockYearList() {
		return ltcBlockYearList;
	}

	public void setLtcBlockYearList(List<KeyValueDTO> ltcBlockYearList) {
		this.ltcBlockYearList = ltcBlockYearList;
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

	public String getLtcBlockId() {
		return ltcBlockId;
	}

	public void setLtcBlockId(String ltcBlockId) {
		this.ltcBlockId = ltcBlockId;
	}

	public List<FamilyBean> getFamilyMemberDetails() {
		return FamilyMemberDetails;
	}

	public void setFamilyMemberDetails(List<FamilyBean> familyMemberDetails) {
		FamilyMemberDetails = familyMemberDetails;
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

	public List<LtcBlockMasterDTO> getLtcBlockDetails() {
		return ltcBlockDetails;
	}

	public void setLtcBlockDetails(List<LtcBlockMasterDTO> ltcBlockDetails) {
		this.ltcBlockDetails = ltcBlockDetails;
	}

	public List<LtcTypeMasterDTO> getLtcTypeDetails() {
		return ltcTypeDetails;
	}

	public void setLtcTypeDetails(List<LtcTypeMasterDTO> ltcTypeDetails) {
		this.ltcTypeDetails = ltcTypeDetails;
	}

	public String getExtendedDate() {
		return extendedDate;
	}

	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFamilyMemberId() {
		return familyMemberId;
	}

	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}

	public String getRefSfID() {
		return refSfID;
	}

	public void setRefSfID(String refSfID) {
		this.refSfID = refSfID;
	}

	public List<LtcApprovalRequestDTO> getAdminEntryDetailsList() {
		return adminEntryDetailsList;
	}

	public void setAdminEntryDetailsList(List<LtcApprovalRequestDTO> adminEntryDetailsList) {
		this.adminEntryDetailsList = adminEntryDetailsList;
	}

	public void setCancleType(String cancleType) {
		this.cancleType = cancleType;
	}

	public String getCancleType() {
		return cancleType;
	}

	public void setChangeLeaveFlag(String changeLeaveFlag) {
		this.changeLeaveFlag = changeLeaveFlag;
	}

	public String getChangeLeaveFlag() {
		return changeLeaveFlag;
	}

	public void setCancelLeaveFlag(String cancelLeaveFlag) {
		this.cancelLeaveFlag = cancelLeaveFlag;
	}

	public String getCancelLeaveFlag() {
		return cancelLeaveFlag;
	}

	public void setLeaveRequestHistoryBean(RequestCommonBean leaveRequestHistoryBean) {
		this.leaveRequestHistoryBean = leaveRequestHistoryBean;
	}

	public RequestCommonBean getLeaveRequestHistoryBean() {
		return leaveRequestHistoryBean;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setLtcRequestType(String ltcRequestType) {
		this.ltcRequestType = ltcRequestType;
	}

	public String getLtcRequestType() {
		return ltcRequestType;
	}

	public void setFinanceIssuedAmount(String financeIssuedAmount) {
		this.financeIssuedAmount = financeIssuedAmount;
	}

	public String getFinanceIssuedAmount() {
		return financeIssuedAmount;
	}

	public void setEncashmentAmount(String encashmentAmount) {
		this.encashmentAmount = encashmentAmount;
	}

	public String getEncashmentAmount() {
		return encashmentAmount;
	}

	public void setLtcAmendmentBlockYear(String ltcAmendmentBlockYear) {
		this.ltcAmendmentBlockYear = ltcAmendmentBlockYear;
	}

	public String getLtcAmendmentBlockYear() {
		return ltcAmendmentBlockYear;
	}

	public String getCancelReqDoPartId() {
		return cancelReqDoPartId;
	}

	public void setCancelReqDoPartId(String cancelReqDoPartId) {
		this.cancelReqDoPartId = cancelReqDoPartId;
	}

	public String getCancelReqHistoryId() {
		return cancelReqHistoryId;
	}

	public void setCancelReqHistoryId(String cancelReqHistoryId) {
		this.cancelReqHistoryId = cancelReqHistoryId;
	}

	public String getCancelReqIssuedAmount() {
		return cancelReqIssuedAmount;
	}

	public void setCancelReqIssuedAmount(String cancelReqIssuedAmount) {
		this.cancelReqIssuedAmount = cancelReqIssuedAmount;
	}

	public void setCancelReqCdaAmount(String cancelReqCdaAmount) {
		this.cancelReqCdaAmount = cancelReqCdaAmount;
	}

	public String getCancelReqCdaAmount() {
		return cancelReqCdaAmount;
	}

	public String getRepSig() {
		return repSig;
	}

	public void setRepSig(String repSig) {
		this.repSig = repSig;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public int getDaValue() {
		return daValue;
	}

	public void setDaValue(int daValue) {
		this.daValue = daValue;
	}

	public int getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(int basicPay) {
		this.basicPay = basicPay;
	}

	

	public Integer getEnchashmentamountDavalue() {
		return enchashmentamountDavalue;
	}

	public void setEnchashmentamountDavalue(Integer enchashmentamountDavalue) {
		this.enchashmentamountDavalue = enchashmentamountDavalue;
	}

	

}
