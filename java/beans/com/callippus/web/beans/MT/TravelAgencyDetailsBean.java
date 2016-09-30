package com.callippus.web.beans.MT;

import com.callippus.web.beans.dto.CommonDTO;

public class TravelAgencyDetailsBean extends CommonDTO{
	private int travelId;
	private String travelAgencyName;
	private String contactPerson;
	private String travelMobileNo;
	private String address;
	

	public String getTravelAgencyName() {
		return travelAgencyName;
	}

	public void setTravelAgencyName(String travelAgencyName) {
		this.travelAgencyName = travelAgencyName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public int getTravelId() {
		return travelId;
	}

	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}

	public String getTravelMobileNo() {
		return travelMobileNo;
	}

	public void setTravelMobileNo(String travelMobileNo) {
		this.travelMobileNo = travelMobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}