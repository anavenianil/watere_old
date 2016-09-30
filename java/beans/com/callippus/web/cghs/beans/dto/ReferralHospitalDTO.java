package com.callippus.web.cghs.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class ReferralHospitalDTO extends CommonDTO{
	
	private String hospitalName;
	private String hospitalLocation;
	private String address;
	private String deletionDate;
	private String strCreationDate;
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
	public String getStrCreationDate() {
		return strCreationDate;
	}
	public void setStrCreationDate(String strCreationDate) {
		this.strCreationDate = strCreationDate;
	}
	public String getDeletionDate() {
		return deletionDate;
	}
	public void setDeletionDate(String deletionDate) {
		this.deletionDate = deletionDate;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

	
}
