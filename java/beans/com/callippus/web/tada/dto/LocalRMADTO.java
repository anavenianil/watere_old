package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LocalRMADTO extends CommonDTO {
	private String fromPlace;
	private String toPlace;
	private float localrmadistance;
	
	
	
	
	public float getLocalrmadistance() {
		return localrmadistance;
	}
	public void setLocalrmadistance(float localrmadistance) {
		this.localrmadistance = localrmadistance;
	}
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	
	

}
