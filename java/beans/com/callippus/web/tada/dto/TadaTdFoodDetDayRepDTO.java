package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TadaTdFoodDetDayRepDTO extends CommonDTO{
	private String representationDate;
	private int settlementRequestId;
	private float representationAmount1;
	private float representationAmount2;
	private String representationDateOne;
	private float claimedAmount;
	private float eligibleAmount;
	private String halfDayFlag;
	
	public String getRepresentationDateOne() {
		return representationDateOne;
	}
	public void setRepresentationDateOne(String representationDateOne) {
		this.representationDateOne = representationDateOne;
	}
	public String getRepresentationDate() {
		return representationDate;
	}
	public void setRepresentationDate(String representationDate) {
		this.representationDate = representationDate;
	}
	public int getSettlementRequestId() {
		return settlementRequestId;
	}
	public void setSettlementRequestId(int settlementRequestId) {
		this.settlementRequestId = settlementRequestId;
	}
	public float getRepresentationAmount1() {
		return representationAmount1;
	}
	public void setRepresentationAmount1(float representationAmount1) {
		this.representationAmount1 = representationAmount1;
	}
	public float getRepresentationAmount2() {
		return representationAmount2;
	}
	public void setRepresentationAmount2(float representationAmount2) {
		this.representationAmount2 = representationAmount2;
	}
	public float getClaimedAmount() {
		return claimedAmount;
	}
	public void setClaimedAmount(float claimedAmount) {
		this.claimedAmount = claimedAmount;
	}
	public float getEligibleAmount() {
		return eligibleAmount;
	}
	public void setEligibleAmount(float eligibleAmount) {
		this.eligibleAmount = eligibleAmount;
	}
	public String getHalfDayFlag() {
		return halfDayFlag;
	}
	public void setHalfDayFlag(String halfDayFlag) {
		this.halfDayFlag = halfDayFlag;
	}
	
	
	
	
	
	

}

