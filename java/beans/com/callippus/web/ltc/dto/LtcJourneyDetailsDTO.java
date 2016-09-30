package com.callippus.web.ltc.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LtcJourneyDetailsDTO extends CommonDTO{
	private String referenceId;
	private String departureDate;
	private String departureDateOne;
	private String departureTime;
	private String arrivalDate;
	private String arrivalDateOne;
	private String arrivalTime;
	private String arrivalStation;
	private String departureStation;
	private String distance;
	private String modeOfTravel;
	private String farePerPerson;
	private String noOfPersons;
	private String amtAfterRestriction;
	private String totalClaimed;
	private String titcketNos;
	private String remarks;
	private String journeyType;
	
	
	public String getDepartureDateOne() {
		return departureDateOne;
	}
	public void setDepartureDateOne(String departureDateOne) {
		this.departureDateOne = departureDateOne;
	}
	public String getArrivalDateOne() {
		return arrivalDateOne;
	}
	public void setArrivalDateOne(String arrivalDateOne) {
		this.arrivalDateOne = arrivalDateOne;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getArrivalStation() {
		return arrivalStation;
	}
	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}
	public String getDepartureStation() {
		return departureStation;
	}
	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getModeOfTravel() {
		return modeOfTravel;
	}
	public void setModeOfTravel(String modeOfTravel) {
		this.modeOfTravel = modeOfTravel;
	}
	public String getFarePerPerson() {
		return farePerPerson;
	}
	public void setFarePerPerson(String farePerPerson) {
		this.farePerPerson = farePerPerson;
	}
	public String getNoOfPersons() {
		return noOfPersons;
	}
	public void setNoOfPersons(String noOfPersons) {
		this.noOfPersons = noOfPersons;
	}
	public String getAmtAfterRestriction() {
		return amtAfterRestriction;
	}
	public void setAmtAfterRestriction(String amtAfterRestriction) {
		this.amtAfterRestriction = amtAfterRestriction;
	}
	public String getTotalClaimed() {
		return totalClaimed;
	}
	public void setTotalClaimed(String totalClaimed) {
		this.totalClaimed = totalClaimed;
	}
	public String getTitcketNos() {
		return titcketNos;
	}
	public void setTitcketNos(String titcketNos) {
		this.titcketNos = titcketNos;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getJourneyType() {
		return journeyType;
	}
	public void setJourneyType(String journeyType) {
		this.journeyType = journeyType;
	}
	
	
}