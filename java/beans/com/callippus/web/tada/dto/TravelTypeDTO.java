package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TravelTypeDTO extends CommonDTO {
	private String travelType;
	private String gradePay;
	private int travelTypeId;

	

	public int getTravelTypeId() {
		return travelTypeId;
	}

	public void setTravelTypeId(int travelTypeId) {
		this.travelTypeId = travelTypeId;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	
	

}
