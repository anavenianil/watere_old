package com.callippus.web.ltc.dto;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.requests.RequestBean;

public class LtcRequestProcessBean extends RequestBean {
private int doPartId;
	
	public int getDoPartId() {
		return doPartId;
	}
	public void setDoPartId(int doPartId) {
		this.doPartId = doPartId;
	}
	private String requestId;
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
	private String creationDate;
	private String createdBy;
	private String ltcBlockYearId;
	private List<LtcMemberDetailsDTO> ltcMemberDetails;
	private String jsonValue;
	private HttpSession session;
	private String requestFormFile;
	private String result;
	private String id;
	private String unitFormation;
	private String claimPreferred;
	private String modeOfPayment;
	private List<LtcJourneyDetailsDTO> ltcJourneyDetails;
	LtcReimbursementDTO ltcReimbursementDTO;
	private String encashTypeId;
	private String gradePay;
	private String employmentTypeId;
	private Date returnDate;
	private String amountClaimed;
	private String amountClaimedInwords;
	private String amountPerPerson;
	private String noOfTickets;
	private String issuedAmount;
	private String issuedAmountInwords;
	private String nearesrAirport;
	private String escortDetails;
	private String totalAmount;
	private String referenceRequestID;
	private String typeValue;
	private String ltcRequestType;
	private String amendmentRefRequestID;
	private String amountPerEachInfant;
	private String noOfInfantTickets;
	private String leaveCancelFlag;
	private String refSfID;
	private String cancleType;
	private EmployeeClaimDetailsDTO employeeClaimDetailsDTO;
	private String ltcblockid;
	private int yearallindia;
	private String ltcblockidindia;
	private AddressBean HomeTownAddress;
	private float FinanceAmountEncash;
	private String checkApplied;
	 private int ltcpenalNoofDays;
	 private String retirementDate;
	
	
	public String getRetirementDate() {
		return retirementDate;
	}
	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}
	public int getLtcpenalNoofDays() {
		return ltcpenalNoofDays;
	}
	public void setLtcpenalNoofDays(int ltcpenalNoofDays) {
		this.ltcpenalNoofDays = ltcpenalNoofDays;
	}
	public AddressBean getHomeTownAddress() {
		return HomeTownAddress;
	}
	public void setHomeTownAddress(AddressBean homeTownAddress) {
		HomeTownAddress = homeTownAddress;
	}
	public String getCheckApplied() {
		return checkApplied;
	}
	public void setCheckApplied(String checkApplied) {
		this.checkApplied = checkApplied;
	}
	public int getYearallindia() {
		return yearallindia;
	}
	public void setYearallindia(int yearallindia) {
		this.yearallindia = yearallindia;
	}
	public String getLtcblockidindia() {
		return ltcblockidindia;
	}
	public void setLtcblockidindia(String ltcblockidindia) {
		this.ltcblockidindia = ltcblockidindia;
	}
	public String getLtcblockid() {
		return ltcblockid;
	}
	public void setLtcblockid(String ltcblockid) {
		this.ltcblockid = ltcblockid;
	}
	public String getLeaveCancelFlag() {
		return leaveCancelFlag;
	}
	public void setLeaveCancelFlag(String leaveCancelFlag) {
		this.leaveCancelFlag = leaveCancelFlag;
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
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public String getReferenceRequestID() {
		return referenceRequestID;
	}
	public void setReferenceRequestID(String referenceRequestID) {
		this.referenceRequestID = referenceRequestID;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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
	public String getIssuedAmount() {
		return issuedAmount;
	}
	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
	}
	public String getIssuedAmountInwords() {
		return issuedAmountInwords;
	}
	public void setIssuedAmountInwords(String issuedAmountInwords) {
		this.issuedAmountInwords = issuedAmountInwords;
	}
	public String getNearesrAirport() {
		return nearesrAirport;
	}
	public void setNearesrAirport(String nearesrAirport) {
		this.nearesrAirport = nearesrAirport;
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
	public String getEncashTypeId() {
		return encashTypeId;
	}
	public void setEncashTypeId(String encashTypeId) {
		this.encashTypeId = encashTypeId;
	}
	public LtcReimbursementDTO getLtcReimbursementDTO() {
		return ltcReimbursementDTO;
	}
	public void setLtcReimbursementDTO(LtcReimbursementDTO ltcReimbursementDTO) {
		this.ltcReimbursementDTO = ltcReimbursementDTO;
	}
	public List<LtcJourneyDetailsDTO> getLtcJourneyDetails() {
		return ltcJourneyDetails;
	}
	public void setLtcJourneyDetails(List<LtcJourneyDetailsDTO> ltcJourneyDetails) {
		this.ltcJourneyDetails = ltcJourneyDetails;
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
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public String getRequestFormFile() {
		return requestFormFile;
	}
	public void setRequestFormFile(String requestFormFile) {
		this.requestFormFile = requestFormFile;
	}
	public List<LtcMemberDetailsDTO> getLtcMemberDetails() {
		return ltcMemberDetails;
	}
	public void setLtcMemberDetails(List<LtcMemberDetailsDTO> ltcMemberDetails) {
		this.ltcMemberDetails = ltcMemberDetails;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	public String getRefSfID() {
		return refSfID;
	}
	public void setRefSfID(String refSfID) {
		this.refSfID = refSfID;
	}
	public void setCancleType(String cancleType) {
		this.cancleType = cancleType;
	}
	public String getCancleType() {
		return cancleType;
	}
	public void setEmployeeClaimDetailsDTO(EmployeeClaimDetailsDTO employeeClaimDetailsDTO) {
		this.employeeClaimDetailsDTO = employeeClaimDetailsDTO;
	}
	public EmployeeClaimDetailsDTO getEmployeeClaimDetailsDTO() {
		return employeeClaimDetailsDTO;
	}
	public float getFinanceAmountEncash() {
		return FinanceAmountEncash;
	}
	public void setFinanceAmountEncash(float financeAmountEncash) {
		FinanceAmountEncash = financeAmountEncash;
	}
				
}