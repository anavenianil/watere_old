package com.callippus.web.beans.misc;

import java.util.Date;

import com.callippus.web.beans.requests.RequestBean;

public class AdminMisc extends RequestBean{

	private int id;
	private String name;
	private int status;
	private String creationDate;
	private String lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	private Date creationTime;
	private Date lastModifiedTime;
	
	
	private String id1;
	private String letterNo;
	private String letterDt;
	private String purpose;
	private String adminType;
	
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
	private String remarks;
	private String propertyType;
	
	private int wfStatus;
	
	
	//movable ends
	
	
	

	public int getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(int wfStatus) {
		this.wfStatus = wfStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getId1() {
		return id1;
	}
	public void setId1(String id1) {
		this.id1 = id1;
	}
	public String getAdminType() {
		return adminType;
	}
	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getLetterDt() {
		return letterDt;
	}
	public void setLetterDt(String letterDt) {
		this.letterDt = letterDt;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	



}
