package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdRMADailyDTO {
	private Date dateRMAKm;
	private String fromPlace;
	private String toPlace;
	private String travelBy;
	private float distance;
	private float amountPerKm;
	private float totalRMAKmAmount;
	
	private String settlementRequestId;
	private int id;
	private String type;
	private float amountAftRestriction;
	private float distanceAftRes;
	private float amountPerKmAftRes;
	
	public Date getDateRMAKm() {
		return dateRMAKm;
	}
	public void setDateRMAKm(Date dateRMAKm) {
		this.dateRMAKm = dateRMAKm;
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
	public String getTravelBy() {
		return travelBy;
	}
	public void setTravelBy(String travelBy) {
		this.travelBy = travelBy;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public float getAmountPerKm() {
		return amountPerKm;
	}
	public void setAmountPerKm(float amountPerKm) {
		this.amountPerKm = amountPerKm;
	}
	public float getTotalRMAKmAmount() {
		return totalRMAKmAmount;
	}
	public void setTotalRMAKmAmount(float totalRMAKmAmount) {
		this.totalRMAKmAmount = totalRMAKmAmount;
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
	public float getDistanceAftRes() {
		return distanceAftRes;
	}
	public void setDistanceAftRes(float distanceAftRes) {
		this.distanceAftRes = distanceAftRes;
	}
	public float getAmountPerKmAftRes() {
		return amountPerKmAftRes;
	}
	public void setAmountPerKmAftRes(float amountPerKmAftRes) {
		this.amountPerKmAftRes = amountPerKmAftRes;
	}
	
	

}
