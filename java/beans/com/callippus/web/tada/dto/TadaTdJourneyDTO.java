package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdJourneyDTO {
	private Date journeyDate;
	private String startTime;
	private String startStation;
	private Date journeyEndDate;
	private String endTime;
	private String endStation;
	private float distanceJourney;
	private String modeOfTravel;
	private float ticketFare;
	private float ticketFareAftRes;
	private float totalJourneyAmount;
	private String ticketNo;
	private String settlementRequestId;
	private int id;
	private String type;
	private float amountAftRestriction;
	
	
	
	
	public float getTicketFareAftRes() {
		return ticketFareAftRes;
	}
	public void setTicketFareAftRes(float ticketFareAftRes) {
		this.ticketFareAftRes = ticketFareAftRes;
	}
	public Date getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public Date getJourneyEndDate() {
		return journeyEndDate;
	}
	public void setJourneyEndDate(Date journeyEndDate) {
		this.journeyEndDate = journeyEndDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndStation() {
		return endStation;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public float getDistanceJourney() {
		return distanceJourney;
	}
	public void setDistanceJourney(float distanceJourney) {
		this.distanceJourney = distanceJourney;
	}
	public String getModeOfTravel() {
		return modeOfTravel;
	}
	public void setModeOfTravel(String modeOfTravel) {
		this.modeOfTravel = modeOfTravel;
	}
	public float getTicketFare() {
		return ticketFare;
	}
	public void setTicketFare(float ticketFare) {
		this.ticketFare = ticketFare;
	}
	public float getTotalJourneyAmount() {
		return totalJourneyAmount;
	}
	public void setTotalJourneyAmount(float totalJourneyAmount) {
		this.totalJourneyAmount = totalJourneyAmount;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
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
	
	
	

}
