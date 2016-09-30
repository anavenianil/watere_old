package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdDaNewAccDetailsDTO {
	private String settlementRequestId;
	private int id;
	private String type;
	private Date fromDate;
	private Date toDate;
	private String inTime;
	private String outTime;
	private float accAmount;
	private float calAccAmount;
	private float claimedAmount;
	private float accAmountAftRes;
	private float calAccAmountAftRes;
	private float claimedAmountAftRes;
	
	
	
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
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public float getAccAmount() {
		return accAmount;
	}
	public void setAccAmount(float accAmount) {
		this.accAmount = accAmount;
	}
	public float getCalAccAmount() {
		return calAccAmount;
	}
	public void setCalAccAmount(float calAccAmount) {
		this.calAccAmount = calAccAmount;
	}
	public float getClaimedAmount() {
		return claimedAmount;
	}
	public void setClaimedAmount(float claimedAmount) {
		this.claimedAmount = claimedAmount;
	}
	public float getAccAmountAftRes() {
		return accAmountAftRes;
	}
	public void setAccAmountAftRes(float accAmountAftRes) {
		this.accAmountAftRes = accAmountAftRes;
	}
	public float getCalAccAmountAftRes() {
		return calAccAmountAftRes;
	}
	public void setCalAccAmountAftRes(float calAccAmountAftRes) {
		this.calAccAmountAftRes = calAccAmountAftRes;
	}
	public float getClaimedAmountAftRes() {
		return claimedAmountAftRes;
	}
	public void setClaimedAmountAftRes(float claimedAmountAftRes) {
		this.claimedAmountAftRes = claimedAmountAftRes;
	}
	
	
	
	
	

}
