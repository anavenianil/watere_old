package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TaEntitleTypeDTO extends CommonDTO {
	private String entitleType;
	private String entitleClass;
	private String travelType;
	private int entitleTypeId;
	private int gradePay;
	private int entitleClassId;
	
	
	public int getEntitleClassId() {
		return entitleClassId;
	}
	public void setEntitleClassId(int entitleClassId) {
		this.entitleClassId = entitleClassId;
	}
	public int getGradePay() {
		return gradePay;
	}
	public void setGradePay(int gradePay) {
		this.gradePay = gradePay;
	}
	public int getEntitleTypeId() {
		return entitleTypeId;
	}
	public void setEntitleTypeId(int entitleTypeId) {
		this.entitleTypeId = entitleTypeId;
	}
	public String getTravelType() {
		return travelType;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public String getEntitleType() {
		return entitleType;
	}
	public void setEntitleType(String entitleType) {
		this.entitleType = entitleType;
	}
	public String getEntitleClass() {
		return entitleClass;
	}
	public void setEntitleClass(String entitleClass) {
		this.entitleClass = entitleClass;
	}
	

}
