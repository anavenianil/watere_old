package com.callippus.web.beans.family;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.dto.DisabilityDTO;
import com.callippus.web.beans.dto.FamilyRelationDTO;

public class FamilyBean {

	private String id;
	private String sfid;
	private String name;
	private String relationId;
	private String gender;
	private String dob;
	private String dependent;
	private String dependentFrom;
	private String maritalstatus;
	private String bloodGroup;
	private String contactNumber;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String state;
	private String district;
	private String pincode;
	private int status;
	private String creationDate;
	private String modifiedDate;
	private String param;
	private String remarks;//new for deletion remarks
	private String type;
	private List relationList;
	private List maritalStatusList;
	private String employeed;
	private String employeedType;
	private String residingWith;
	private String declareDate;
	private String familyID;
	private String age;
	private String relation;
	private String addressTypeId;
	private List addressTypeList;
	private String message;
	private List empFamilyList;
	private String maritalstatusId;
	private List bloodGroupList;
	private String changedValues;
	private String phtypeFlag;
	private String adopted;
	private String adoptedDate;
	private String ltcFacility;
	private String cghsFacility;
	private String ageLimit;
	private String major;
	private String earningMoney;
	private List<DisabilityDTO> disabilityList;
	private String disabilityId;
	private String reason;
	private FamilyRelationDTO relationDetails;
	private Date dobFormated;
	private String strDob;
	private String placeOfIssue;
	private String beneficiary;
	private String validFrom;
	private String validTo;
	private String  cghscard;
	private MultipartFile cghscardfile; 
	private String rid; 
	private String months;
	private String toDay;
	private String ltcAvail; 
	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}
	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public String getStrDob() {
		return strDob;
	}

	public void setStrDob(String strDob) {
		this.strDob = strDob;
	}

	public Date getDobFormated() {
		return dobFormated;
	}

	public void setDobFormated(Date dobFormated) {
		this.dobFormated = dobFormated;
	}

	public FamilyRelationDTO getRelationDetails() {
		return relationDetails;
	}

	public void setRelationDetails(FamilyRelationDTO relationDetails) {
		this.relationDetails = relationDetails;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDisabilityId() {
		return disabilityId;
	}

	public void setDisabilityId(String disabilityId) {
		this.disabilityId = disabilityId;
	}

	public List<DisabilityDTO> getDisabilityList() {
		return disabilityList;
	}

	public void setDisabilityList(List<DisabilityDTO> disabilityList) {
		this.disabilityList = disabilityList;
	}

	public String getEarningMoney() {
		return earningMoney;
	}

	public void setEarningMoney(String earningMoney) {
		this.earningMoney = earningMoney;
	}

	public String getAgeLimit() {
		return ageLimit;
	}

	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCghsFacility() {
		return cghsFacility;
	}

	public void setCghsFacility(String cghsFacility) {
		this.cghsFacility = cghsFacility;
	}

	public String getLtcFacility() {
		return ltcFacility;
	}

	public void setLtcFacility(String ltcFacility) {
		this.ltcFacility = ltcFacility;
	}

	public String getAdoptedDate() {
		return adoptedDate;
	}

	public void setAdoptedDate(String adoptedDate) {
		this.adoptedDate = adoptedDate;
	}

	public String getAdopted() {
		return adopted;
	}

	public void setAdopted(String adopted) {
		this.adopted = adopted;
	}

	public String getPhtypeFlag() {
		return phtypeFlag;
	}

	public void setPhtypeFlag(String phtypeFlag) {
		this.phtypeFlag = phtypeFlag;
	}

	public List getBloodGroupList() {
		return bloodGroupList;
	}

	public void setBloodGroupList(List bloodGroupList) {
		this.bloodGroupList = bloodGroupList;
	}

	public String getMaritalstatusId() {
		return maritalstatusId;
	}

	public void setMaritalstatusId(String maritalstatusId) {
		this.maritalstatusId = maritalstatusId;
	}

	public List getEmpFamilyList() {
		return empFamilyList;
	}

	public void setEmpFamilyList(List empFamilyList) {
		this.empFamilyList = empFamilyList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public void setAddressTypeList(List addressTypeList) {
		this.addressTypeList = addressTypeList;
	}

	public List getAddressTypeList() {
		return addressTypeList;
	}

	

	public String getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(String addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public String getAddress3() {

		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFamilyID() {
		return familyID;
	}

	public void setFamilyID(String familyID) {
		this.familyID = familyID;
	}

	public String getResidingWith() {
		return residingWith;
	}

	public void setResidingWith(String residingWith) {
		this.residingWith = residingWith;
	}

	public String getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(String declareDate) {
		this.declareDate = declareDate;
	}

	public String getEmployeed() {
		return employeed;
	}

	public void setEmployeed(String employeed) {
		this.employeed = employeed;
	}

	public String getEmployeedType() {
		return employeedType;
	}

	public void setEmployeedType(String employeedType) {
		this.employeedType = employeedType;
	}

	public List getRelationList() {
		return relationList;
	}

	public void setRelationList(List relationList) {
		this.relationList = relationList;
	}

	public List getMaritalStatusList() {
		return maritalStatusList;
	}

	public void setMaritalStatusList(List maritalStatusList) {
		this.maritalStatusList = maritalStatusList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDependent() {
		return dependent;
	}

	public void setDependent(String dependent) {
		this.dependent = dependent;
	}

	public String getDependentFrom() {
		return dependentFrom;
	}

	public void setDependentFrom(String dependentFrom) {
		this.dependentFrom = dependentFrom;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getChangedValues() {
		return changedValues;
	}

	public void setChangedValues(String changedValues) {
		this.changedValues = changedValues;
	}

	public String getCghscard() {
		return cghscard;
	}

	public void setCghscard(String cghscard) {
		this.cghscard = cghscard;
	}

	public MultipartFile getCghscardfile() {
		return cghscardfile;
	}

	public void setCghscardfile(MultipartFile cghscardfile) {
		this.cghscardfile = cghscardfile;
	}

	public String getToDay() {
		return toDay;
	}

	public void setToDay(String toDay) {
		this.toDay = toDay;
	}

	public String getLtcAvail() {
		return ltcAvail;
	}

	public void setLtcAvail(String ltcAvail) {
		this.ltcAvail = ltcAvail;
	}

	
}