package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TaTravelTypeMapDTO extends CommonDTO{
	private String travelType;
	private int gradePay;
	private int key;
	private int value;
	private int travelTypeId;
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTravelTypeId() {
		return travelTypeId;
	}
	public void setTravelTypeId(int travelTypeId) {
		this.travelTypeId = travelTypeId;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public int getGradePay() {
		return gradePay;
	}
	public void setGradePay(int gradePay) {
		this.gradePay = gradePay;
	}

}
