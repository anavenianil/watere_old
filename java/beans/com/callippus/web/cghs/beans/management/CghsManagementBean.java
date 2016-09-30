package com.callippus.web.cghs.beans.management;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.cghs.beans.dto.ReferralHospitalDTO;
import com.callippus.web.cghs.beans.dto.WardTypeDTO;

public class CghsManagementBean extends CommonDTO{
	
	private String param;
	private String type;
	private String hospitalName;
	private String hospitalLocation;
	private String contactPerson;
	private String contactPersonNo;
	private String faxNumber;
	private String address;
	private String message;
	private String sfid;
	private String pk;
	private Double endBasicPay;
	private Double startBasicPay;
	private String wardName;
	private List<ReferralHospitalDTO> referralHospitalList;
	private List<WardTypeDTO> wardTypeList;
	private String selectedDelete;
	private String deletionDate;
	private String acNumber;
	private String bankName;
	private String branchName;
	
	
	
	
	
	public String getAcNumber() {
		return acNumber;
	}
	public void setAcNumber(String acNumber) {
		this.acNumber = acNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDeletionDate() {
		return deletionDate;
	}
	public void setDeletionDate(String deletionDate) {
		this.deletionDate = deletionDate;
	}
	public String getSelectedDelete() {
		return selectedDelete;
	}
	public void setSelectedDelete(String selectedDelete) {
		this.selectedDelete = selectedDelete;
	}
	public Double getEndBasicPay() {
		return endBasicPay;
	}
	public void setEndBasicPay(Double endBasicPay) {
		this.endBasicPay = endBasicPay;
	}
	public Double getStartBasicPay() {
		return startBasicPay;
	}
	public void setStartBasicPay(Double startBasicPay) {
		this.startBasicPay = startBasicPay;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
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
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getHospitalLocation() {
		return hospitalLocation;
	}
	public void setHospitalLocation(String hospitalLocation) {
		this.hospitalLocation = hospitalLocation;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPersonNo() {
		return contactPersonNo;
	}
	public void setContactPersonNo(String contactPersonNo) {
		this.contactPersonNo = contactPersonNo;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public List<ReferralHospitalDTO> getReferralHospitalList() {
		return referralHospitalList;
	}
	public void setReferralHospitalList(
			List<ReferralHospitalDTO> referralHospitalList) {
		this.referralHospitalList = referralHospitalList;
	}
	public List<WardTypeDTO> getWardTypeList() {
		return wardTypeList;
	}
	public void setWardTypeList(List<WardTypeDTO> wardTypeList) {
		this.wardTypeList = wardTypeList;
	}
	
	
	
}
