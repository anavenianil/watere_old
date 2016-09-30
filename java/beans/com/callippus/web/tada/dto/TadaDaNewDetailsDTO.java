package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TadaDaNewDetailsDTO extends CommonDTO{
	
	private int accommodationBill;
	private int foodBill;
	private String milageAllw;
	private String travelBy;
	private int distance;
	private int amount;
	private int gradePay;
	
	private Float daOnTourPercentage;  //new proprety for daOnTourPercentage
	
	
	
	
	public Float getDaOnTourPercentage() {
		return daOnTourPercentage;
	}
	public void setDaOnTourPercentage(Float daOnTourPercentage) {
		this.daOnTourPercentage = daOnTourPercentage;
	}
	
	public int getAccommodationBill() {
		return accommodationBill;
	}
	public void setAccommodationBill(int accommodationBill) {
		this.accommodationBill = accommodationBill;
	}
	public int getFoodBill() {
		return foodBill;
	}
	public void setFoodBill(int foodBill) {
		this.foodBill = foodBill;
	}
	public String getMilageAllw() {
		return milageAllw;
	}
	public void setMilageAllw(String milageAllw) {
		this.milageAllw = milageAllw;
	}
	public String getTravelBy() {
		return travelBy;
	}
	public void setTravelBy(String travelBy) {
		this.travelBy = travelBy;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getGradePay() {
		return gradePay;
	}
	public void setGradePay(int gradePay) {
		this.gradePay = gradePay;
	}
	
	

}
