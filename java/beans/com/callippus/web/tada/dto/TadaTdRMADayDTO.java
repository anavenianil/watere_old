package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdRMADayDTO {
	private Date dateRMADay;
	private String fromPlace;
	private String toPlace;
	private float amountPerDay;
	private float totalRMADayAmount;
	
	private String settlementRequestId;
	private int id;
	private String type;
	private float amountAftRestriction;
	private float amountPerDayAftRes;
	private float calAmountPerDay;
	private float calAmountPerDayAftRes;
	
	
	
	
	public float getCalAmountPerDay() {
		return calAmountPerDay;
	}
	public void setCalAmountPerDay(float calAmountPerDay) {
		this.calAmountPerDay = calAmountPerDay;
	}
	public float getCalAmountPerDayAftRes() {
		return calAmountPerDayAftRes;
	}
	public void setCalAmountPerDayAftRes(float calAmountPerDayAftRes) {
		this.calAmountPerDayAftRes = calAmountPerDayAftRes;
	}
	public Date getDateRMADay() {
		return dateRMADay;
	}
	public void setDateRMADay(Date dateRMADay) {
		this.dateRMADay = dateRMADay;
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
	public float getAmountPerDay() {
		return amountPerDay;
	}
	public void setAmountPerDay(float amountPerDay) {
		this.amountPerDay = amountPerDay;
	}
	public float getTotalRMADayAmount() {
		return totalRMADayAmount;
	}
	public void setTotalRMADayAmount(float totalRMADayAmount) {
		this.totalRMADayAmount = totalRMADayAmount;
	}
	public String getSettlementRequestId() {
		return settlementRequestId;
	}
	public void setSettlementRequestId(String settlementRequestId) {
		this.settlementRequestId = settlementRequestId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getAmountAftRestriction() {
		return amountAftRestriction;
	}
	public void setAmountAftRestriction(float amountAftRestriction) {
		this.amountAftRestriction = amountAftRestriction;
	}
	public float getAmountPerDayAftRes() {
		return amountPerDayAftRes;
	}
	public void setAmountPerDayAftRes(float amountPerDayAftRes) {
		this.amountPerDayAftRes = amountPerDayAftRes;
	}
	
	
	

}
