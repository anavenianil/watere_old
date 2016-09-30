package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdJDADetailsDTO {
	private String journeyDate;
	private float jda;
	private float sda;
	private float jdaAmount;
	private float sdaAmount;
	private String place;
	private String cityClass;
	private int cityClassId;
	private String leaveHalfDayFlag;
	
	public int getCityClassId() {
		return cityClassId;
	}
	public void setCityClassId(int cityClassId) {
		this.cityClassId = cityClassId;
	}
	public String getCityClass() {
		return cityClass;
	}
	public void setCityClass(String cityClass) {
		this.cityClass = cityClass;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(String journeyDate) {
		this.journeyDate = journeyDate;
	}
	public float getJda() {
		return jda;
	}
	public void setJda(float jda) {
		this.jda = jda;
	}
	public float getSda() {
		return sda;
	}
	public void setSda(float sda) {
		this.sda = sda;
	}
	public float getJdaAmount() {
		return jdaAmount;
	}
	public void setJdaAmount(float jdaAmount) {
		this.jdaAmount = jdaAmount;
	}
	public float getSdaAmount() {
		return sdaAmount;
	}
	public void setSdaAmount(float sdaAmount) {
		this.sdaAmount = sdaAmount;
	}
	public String getLeaveHalfDayFlag() {
		return leaveHalfDayFlag;
	}
	public void setLeaveHalfDayFlag(String leaveHalfDayFlag) {
		this.leaveHalfDayFlag = leaveHalfDayFlag;
	}
	
	
	

}
