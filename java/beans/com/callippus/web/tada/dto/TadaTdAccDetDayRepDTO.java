package com.callippus.web.tada.dto;

public class TadaTdAccDetDayRepDTO {
	private String representationDate;
	private int settlementRequestId;
	private float claimedAmount;
	private float eligibleAmount;
	private float admisAmount;
	private int id;
	private String halfDayFlag;
	private String representationDate1;
	
	private float presentationAmount1;
	private float representationAmount2;
	
	private Float TadaDaPercentage;
	
	
	
	public Float getTadaDaPercentage() {
		return TadaDaPercentage;
	}
	public void setTadaDaPercentage(Float tadaDaPercentage) {
		TadaDaPercentage = tadaDaPercentage;
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
	public float getAdmisAmount() {
		return admisAmount;
	}
	public void setAdmisAmount(float admisAmount) {
		this.admisAmount = admisAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHalfDayFlag() {
		return halfDayFlag;
	}
	public void setHalfDayFlag(String halfDayFlag) {
		this.halfDayFlag = halfDayFlag;
	}
	public String getRepresentationDate1() {
		return representationDate1;
	}
	public void setRepresentationDate1(String representationDate1) {
		this.representationDate1 = representationDate1;
	}
	public float getPresentationAmount1() {
		return presentationAmount1;
	}
	public void setPresentationAmount1(float presentationAmount1) {
		this.presentationAmount1 = presentationAmount1;
	}
	public float getRepresentationAmount2() {
		return representationAmount2;
	}
	public void setRepresentationAmount2(float representationAmount2) {
		this.representationAmount2 = representationAmount2;
	}
	
	

}
