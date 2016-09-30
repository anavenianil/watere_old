package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdDaOldDetailsDTO {
	private Date daOldDate;
	private float jdaDays;
	private float jdaAmount;
	private float totalJdaAmount;
	private float sdaDays;
	private float sdaAmount;
	private float totalSdaAmount;
	private int settlementRequestId;
	private int id;
	private String daTypeRate;
	private String requestType;
	
	public Date getDaOldDate() {
		return daOldDate;
	}
	public void setDaOldDate(Date daOldDate) {
		this.daOldDate = daOldDate;
	}
	public float getJdaDays() {
		return jdaDays;
	}
	public void setJdaDays(float jdaDays) {
		this.jdaDays = jdaDays;
	}
	public float getJdaAmount() {
		return jdaAmount;
	}
	public void setJdaAmount(float jdaAmount) {
		this.jdaAmount = jdaAmount;
	}
	public float getTotalJdaAmount() {
		return totalJdaAmount;
	}
	public void setTotalJdaAmount(float totalJdaAmount) {
		this.totalJdaAmount = totalJdaAmount;
	}
	public float getSdaDays() {
		return sdaDays;
	}
	public void setSdaDays(float sdaDays) {
		this.sdaDays = sdaDays;
	}
	public float getSdaAmount() {
		return sdaAmount;
	}
	public void setSdaAmount(float sdaAmount) {
		this.sdaAmount = sdaAmount;
	}
	public float getTotalSdaAmount() {
		return totalSdaAmount;
	}
	public void setTotalSdaAmount(float totalSdaAmount) {
		this.totalSdaAmount = totalSdaAmount;
	}
	public int getSettlementRequestId() {
		return settlementRequestId;
	}
	public void setSettlementRequestId(int settlementRequestId) {
		this.settlementRequestId = settlementRequestId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDaTypeRate() {
		return daTypeRate;
	}
	public void setDaTypeRate(String daTypeRate) {
		this.daTypeRate = daTypeRate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	
	
	
}
