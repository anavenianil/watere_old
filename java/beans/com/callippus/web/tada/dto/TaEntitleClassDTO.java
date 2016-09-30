package com.callippus.web.tada.dto;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class TaEntitleClassDTO extends CommonDTO {
	private String entitleType;
	private String entitleClass;
	private String travelType;
	private int entitleTypeId;
	private int gradePay;
	private int entitleClassId;
	private List<TaEntitleClassDTO> entitleClassList;
	private List<TaEntitleTypeDTO> entitleClassLists;
	
	
	
	
	public List<TaEntitleTypeDTO> getEntitleClassLists() {
		return entitleClassLists;
	}
	public void setEntitleClassLists(List<TaEntitleTypeDTO> entitleClassLists) {
		this.entitleClassLists = entitleClassLists;
	}
	public List<TaEntitleClassDTO> getEntitleClassList() {
		return entitleClassList;
	}
	public void setEntitleClassList(List<TaEntitleClassDTO> entitleClassList) {
		this.entitleClassList = entitleClassList;
	}
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
