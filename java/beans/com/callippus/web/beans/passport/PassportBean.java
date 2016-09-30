package com.callippus.web.beans.passport;

import java.util.List;

import com.callippus.web.beans.common.ErpBean;
import com.callippus.web.beans.dto.ProceedingTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;

public class PassportBean extends ErpBean{

	private String type;
	private String beanType;
	private String passportType;
	private String passportAppType;
	private String passportNumber;
	private String issuePlace;
	private String validUpto;
	private String details;
	private String remarks;
	private String id;
	private List passportList;
	private int status;
	private String message;
	private String passPortFor;
	private String familyMember;
	private String passPortId;
	private String creationDate;
	private String lastModifiedDate;
	private EmployeeBean empDetails;
	private List<ProceedingTypeDTO> proceedingList;
	private List<FamilyBean> familyMemberDetails;
	private String departureDate;
	private String relations;
	private String employmentDetails;
	private String vigilanceFlag;
	private String passportPossessFlag;
	private String issueDate;
	private String validityDate;
	private String returnDate;
	private String countriesToVisit;
	private String purpose;
	private String duration;
	private String familyMembersFlag;
	private String familyMemberId;
	private String spendingAmount;
	private String selfFinanceFlag;
	private String sourceOfAmount;
	private String lenderName;
	private String nationality;
	private String lenderRelationship;
	private String ipAddress;
	private String result;
	private String sfID;
	private String empAddress;
	private String letterDate;
	private String adminNoteDt;
	private String receivedDt;
	private String letterDesc;
	private String adminNoteNo;
	private String verificationType;
	private String visualFlag;
	private String propReturnFlag;
	
	private String sfidForAbroad;
	private String hqLetterDt;
	private String hqLetterNo;
	

	private String detailsFlag;
	private String supportName;
	private String supportOccupation;
	private String supportAddress;
	

	private String passportLostFlag;
	private String lostPassportType;
	private String lostPassportNumber;
	private String ppissueDate;
	private String ppvalidityDate;
	
	private String hospitalFlag;
	private String prevCountryDetails;
	private String idCardFlag;
	private String settleFlag;
	private String contractualFlag;
	private String coPeriodFrom;
	private String coPeriodTo;
	private String certifyStatus;
	
	
	private String idCertificateNo;
	private String idCertificateDt;
	
	
	//movable property
	

	private String acqOrDis;
	private String acqOrDisDate;
	private String propertyActualDate;
	private String description;
	private String modeOfReg;
	private String modeOfAcquisition;
	private String saleOrPurchasePrice;
	private String source;
	private String personalSavings;
	private String otherSource;
	private String partyName;
	private String relationship;
	private String applicantDealings;
	private String officialDealings;
	private String transaction;
	private String acqByGift;
	
	//movable ends
	

    
	
	
	
	
	public String getDetailsFlag() {
		return detailsFlag;
	}
	public String getAcqOrDis() {
		return acqOrDis;
	}
	public void setAcqOrDis(String acqOrDis) {
		this.acqOrDis = acqOrDis;
	}
	public String getAcqOrDisDate() {
		return acqOrDisDate;
	}
	public void setAcqOrDisDate(String acqOrDisDate) {
		this.acqOrDisDate = acqOrDisDate;
	}
	public String getPropertyActualDate() {
		return propertyActualDate;
	}
	public void setPropertyActualDate(String propertyActualDate) {
		this.propertyActualDate = propertyActualDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getModeOfReg() {
		return modeOfReg;
	}
	public void setModeOfReg(String modeOfReg) {
		this.modeOfReg = modeOfReg;
	}
	public String getModeOfAcquisition() {
		return modeOfAcquisition;
	}
	public void setModeOfAcquisition(String modeOfAcquisition) {
		this.modeOfAcquisition = modeOfAcquisition;
	}
	public String getSaleOrPurchasePrice() {
		return saleOrPurchasePrice;
	}
	public void setSaleOrPurchasePrice(String saleOrPurchasePrice) {
		this.saleOrPurchasePrice = saleOrPurchasePrice;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPersonalSavings() {
		return personalSavings;
	}
	public void setPersonalSavings(String personalSavings) {
		this.personalSavings = personalSavings;
	}
	public String getOtherSource() {
		return otherSource;
	}
	public void setOtherSource(String otherSource) {
		this.otherSource = otherSource;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getApplicantDealings() {
		return applicantDealings;
	}
	public void setApplicantDealings(String applicantDealings) {
		this.applicantDealings = applicantDealings;
	}
	public String getOfficialDealings() {
		return officialDealings;
	}
	public void setOfficialDealings(String officialDealings) {
		this.officialDealings = officialDealings;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public String getAcqByGift() {
		return acqByGift;
	}
	public void setAcqByGift(String acqByGift) {
		this.acqByGift = acqByGift;
	}
	public void setDetailsFlag(String detailsFlag) {
		this.detailsFlag = detailsFlag;
	}
	public String getSupportName() {
		return supportName;
	}
	public void setSupportName(String supportName) {
		this.supportName = supportName;
	}
	public String getSupportOccupation() {
		return supportOccupation;
	}
	public void setSupportOccupation(String supportOccupation) {
		this.supportOccupation = supportOccupation;
	}
	public String getSupportAddress() {
		return supportAddress;
	}
	public void setSupportAddress(String supportAddress) {
		this.supportAddress = supportAddress;
	}
	public String getPassportLostFlag() {
		return passportLostFlag;
	}
	public void setPassportLostFlag(String passportLostFlag) {
		this.passportLostFlag = passportLostFlag;
	}
	public String getLostPassportType() {
		return lostPassportType;
	}
	public void setLostPassportType(String lostPassportType) {
		this.lostPassportType = lostPassportType;
	}
	public String getLostPassportNumber() {
		return lostPassportNumber;
	}
	public void setLostPassportNumber(String lostPassportNumber) {
		this.lostPassportNumber = lostPassportNumber;
	}
	public String getPpissueDate() {
		return ppissueDate;
	}
	public void setPpissueDate(String ppissueDate) {
		this.ppissueDate = ppissueDate;
	}
	public String getPpvalidityDate() {
		return ppvalidityDate;
	}
	public void setPpvalidityDate(String ppvalidityDate) {
		this.ppvalidityDate = ppvalidityDate;
	}
	public String getHospitalFlag() {
		return hospitalFlag;
	}
	public void setHospitalFlag(String hospitalFlag) {
		this.hospitalFlag = hospitalFlag;
	}
	public String getPrevCountryDetails() {
		return prevCountryDetails;
	}
	public void setPrevCountryDetails(String prevCountryDetails) {
		this.prevCountryDetails = prevCountryDetails;
	}
	public String getIdCardFlag() {
		return idCardFlag;
	}
	public void setIdCardFlag(String idCardFlag) {
		this.idCardFlag = idCardFlag;
	}
	public String getSettleFlag() {
		return settleFlag;
	}
	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag;
	}
	public String getContractualFlag() {
		return contractualFlag;
	}
	public void setContractualFlag(String contractualFlag) {
		this.contractualFlag = contractualFlag;
	}
	public String getCoPeriodFrom() {
		return coPeriodFrom;
	}
	public void setCoPeriodFrom(String coPeriodFrom) {
		this.coPeriodFrom = coPeriodFrom;
	}
	public String getCoPeriodTo() {
		return coPeriodTo;
	}
	public void setCoPeriodTo(String coPeriodTo) {
		this.coPeriodTo = coPeriodTo;
	}
	public String getCertifyStatus() {
		return certifyStatus;
	}
	public void setCertifyStatus(String certifyStatus) {
		this.certifyStatus = certifyStatus;
	}
	public String getIdCertificateNo() {
		return idCertificateNo;
	}
	public void setIdCertificateNo(String idCertificateNo) {
		this.idCertificateNo = idCertificateNo;
	}
	public String getIdCertificateDt() {
		return idCertificateDt;
	}
	public void setIdCertificateDt(String idCertificateDt) {
		this.idCertificateDt = idCertificateDt;
	}
	public String getSfidForAbroad() {
		return sfidForAbroad;
	}
	public void setSfidForAbroad(String sfidForAbroad) {
		this.sfidForAbroad = sfidForAbroad;
	}
	public String getHqLetterDt() {
		return hqLetterDt;
	}
	public void setHqLetterDt(String hqLetterDt) {
		this.hqLetterDt = hqLetterDt;
	}
	public String getHqLetterNo() {
		return hqLetterNo;
	}
	public void setHqLetterNo(String hqLetterNo) {
		this.hqLetterNo = hqLetterNo;
	}
	public String getVisualFlag() {
		return visualFlag;
	}
	public void setVisualFlag(String visualFlag) {
		this.visualFlag = visualFlag;
	}
	public String getPropReturnFlag() {
		return propReturnFlag;
	}
	public void setPropReturnFlag(String propReturnFlag) {
		this.propReturnFlag = propReturnFlag;
	}
	public String getVerificationType() {
		return verificationType;
	}
	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}
    public String getPassportAppType() {
		return passportAppType;
	}
	public void setPassportAppType(String passportAppType) {
		this.passportAppType = passportAppType;
	}
	public String getBeanType() {
		return beanType;
	}
	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}
	
	public String getLetterDate() {
		return letterDate;
	}
	public void setLetterDate(String letterDate) {
		this.letterDate = letterDate;
	}
	public String getAdminNoteDt() {
		return adminNoteDt;
	}
	public void setAdminNoteDt(String adminNoteDt) {
		this.adminNoteDt = adminNoteDt;
	}
	public String getReceivedDt() {
		return receivedDt;
	}
	public void setReceivedDt(String receivedDt) {
		this.receivedDt = receivedDt;
	}
	public String getLetterDesc() {
		return letterDesc;
	}
	public void setLetterDesc(String letterDesc) {
		this.letterDesc = letterDesc;
	}
	public String getAdminNoteNo() {
		return adminNoteNo;
	}
	public void setAdminNoteNo(String adminNoteNo) {
		this.adminNoteNo = adminNoteNo;
	}
	public String getEmpAddress() {
		return empAddress;
	}
	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}
	public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    public String getPassPortId() {
        return passPortId;
    }
    public void setPassPortId(String passPortId) {
        this.passPortId = passPortId;
    }
    public String getPassPortFor() {
        return passPortFor;
    }
    public void setPassPortFor(String passPortFor) {
        this.passPortFor = passPortFor;
    }
    public String getFamilyMember() {
        return familyMember;
    }
    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }
    public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List getPassportList() {
		return passportList;
	}
	public void setPassportList(List passportList) {
		this.passportList = passportList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassportType() {
		return passportType;
	}
	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getIssuePlace() {
		return issuePlace;
	}
	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}
	public String getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	public EmployeeBean getEmpDetails() {
		return empDetails;
	}
	public void setEmpDetails(EmployeeBean empDetails) {
		this.empDetails = empDetails;
	}
	public List<ProceedingTypeDTO> getProceedingList() {
		return proceedingList;
	}
	public void setProceedingList(List<ProceedingTypeDTO> proceedingList) {
		this.proceedingList = proceedingList;
	}
	
	public String getRelations() {
		return relations;
	}
	public void setRelations(String relations) {
		this.relations = relations;
	}
	public String getEmploymentDetails() {
		return employmentDetails;
	}
	public void setEmploymentDetails(String employmentDetails) {
		this.employmentDetails = employmentDetails;
	}
	public String getVigilanceFlag() {
		return vigilanceFlag;
	}
	public void setVigilanceFlag(String vigilanceFlag) {
		this.vigilanceFlag = vigilanceFlag;
	}
	public String getPassportPossessFlag() {
		return passportPossessFlag;
	}
	public void setPassportPossessFlag(String passportPossessFlag) {
		this.passportPossessFlag = passportPossessFlag;
	}
	public String getCountriesToVisit() {
		return countriesToVisit;
	}
	public void setCountriesToVisit(String countriesToVisit) {
		this.countriesToVisit = countriesToVisit;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getFamilyMembersFlag() {
		return familyMembersFlag;
	}
	public void setFamilyMembersFlag(String familyMembersFlag) {
		this.familyMembersFlag = familyMembersFlag;
	}
	public String getFamilyMemberId() {
		return familyMemberId;
	}
	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}
	public String getSpendingAmount() {
		return spendingAmount;
	}
	public void setSpendingAmount(String spendingAmount) {
		this.spendingAmount = spendingAmount;
	}
	public String getSelfFinanceFlag() {
		return selfFinanceFlag;
	}
	public void setSelfFinanceFlag(String selfFinanceFlag) {
		this.selfFinanceFlag = selfFinanceFlag;
	}
	public String getSourceOfAmount() {
		return sourceOfAmount;
	}
	public void setSourceOfAmount(String sourceOfAmount) {
		this.sourceOfAmount = sourceOfAmount;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getLenderRelationship() {
		return lenderRelationship;
	}
	public void setLenderRelationship(String lenderRelationship) {
		this.lenderRelationship = lenderRelationship;
	}
	public List<FamilyBean> getFamilyMemberDetails() {
		return familyMemberDetails;
	}
	public void setFamilyMemberDetails(List<FamilyBean> familyMemberDetails) {
		this.familyMemberDetails = familyMemberDetails;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	
	
}
