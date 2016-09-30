package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdReqJourneyDTO {
	private Date departureDate;
	private String fromPlace;
	private String toPlace;
	private int noOfDays;
	private String conveyanceMode;
	private String classOfEntitlement;
	private String tatkalQuota;
	private int id;
	private int referenceId;
	private float ticketFare;
	private float ticketFareAftRes;
	private String strDepartureDate;
	private Date arrivalDate;
	private String strArrivalDate;
	
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
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
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getConveyanceMode() {
		return conveyanceMode;
	}
	public void setConveyanceMode(String conveyanceMode) {
		this.conveyanceMode = conveyanceMode;
	}
	public String getClassOfEntitlement() {
		return classOfEntitlement;
	}
	public void setClassOfEntitlement(String classOfEntitlement) {
		this.classOfEntitlement = classOfEntitlement;
	}
	public String getTatkalQuota() {
		return tatkalQuota;
	}
	public void setTatkalQuota(String tatkalQuota) {
		this.tatkalQuota = tatkalQuota;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
	public float getTicketFare() {
		return ticketFare;
	}
	public void setTicketFare(float ticketFare) {
		this.ticketFare = ticketFare;
	}
	public float getTicketFareAftRes() {
		return ticketFareAftRes;
	}
	public void setTicketFareAftRes(float ticketFareAftRes) {
		this.ticketFareAftRes = ticketFareAftRes;
	}
	public String getStrDepartureDate() {
		return strDepartureDate;
	}
	public void setStrDepartureDate(String strDepartureDate) {
		this.strDepartureDate = strDepartureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getStrArrivalDate() {
		return strArrivalDate;
	}
	public void setStrArrivalDate(String strArrivalDate) {
		this.strArrivalDate = strArrivalDate;
	}
	
	
	

}
