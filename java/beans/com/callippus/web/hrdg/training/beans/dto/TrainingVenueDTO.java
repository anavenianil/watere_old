package com.callippus.web.hrdg.training.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TrainingVenueDTO extends CommonDTO{
	private int cityId;
	
	private String city;
	private String address;
	private String phone;
	private String fax;
	private String email;
	private String webSite;
	private int ddFlag;
	private String ddAddress;
	private Integer payableAt;
	
	private int headOfficeFlag;
	private int trainingInistituteId;
	private String trainingInistitute;
	private int trainingRegionId;
	private String trainingRegion;
	private int trainingTypeId;
	private String trainingType;
	
	
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public int getDdFlag() {
		return ddFlag;
	}
	public void setDdFlag(int ddFlag) {
		this.ddFlag = ddFlag;
	}
	public String getDdAddress() {
		return ddAddress;
	}
	public void setDdAddress(String ddAddress) {
		this.ddAddress = ddAddress;
	}
	public int getHeadOfficeFlag() {
		return headOfficeFlag;
	}
	public void setHeadOfficeFlag(int headOfficeFlag) {
		this.headOfficeFlag = headOfficeFlag;
	}
	public int getTrainingInistituteId() {
		return trainingInistituteId;
	}
	public void setTrainingInistituteId(int trainingInistituteId) {
		this.trainingInistituteId = trainingInistituteId;
	}
	public String getTrainingInistitute() {
		return trainingInistitute;
	}
	public void setTrainingInistitute(String trainingInistitute) {
		this.trainingInistitute = trainingInistitute;
	}
	public Integer getPayableAt() {
		return payableAt;
	}
	public void setPayableAt(Integer payableAt) {
		this.payableAt = payableAt;
	}
	
	
	public int getTrainingTypeId() {
		return trainingTypeId;
	}
	public void setTrainingTypeId(int trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}
	public int getTrainingRegionId() {
		return trainingRegionId;
	}
	public void setTrainingRegionId(int trainingRegionId) {
		this.trainingRegionId = trainingRegionId;
	}
	public String getTrainingRegion() {
		return trainingRegion;
	}
	public void setTrainingRegion(String trainingRegion) {
		this.trainingRegion = trainingRegion;
	}
	
	
	

}
