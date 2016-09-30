package com.callippus.web.tada.dto;


public class TadaTdSettlementDTO {
	private String requestType;
	private int requestID;
	private int refRequestId;
	private float amountClaimed;
	private float amountClaimedAftRes;
	private String appliedBy;
	private int id;
	private String userRemarks;
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public int getRefRequestId() {
		return refRequestId;
	}
	public void setRefRequestId(int refRequestId) {
		this.refRequestId = refRequestId;
	}
	public float getAmountClaimed() {
		return amountClaimed;
	}
	public void setAmountClaimed(float amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	public float getAmountClaimedAftRes() {
		return amountClaimedAftRes;
	}
	public void setAmountClaimedAftRes(float amountClaimedAftRes) {
		this.amountClaimedAftRes = amountClaimedAftRes;
	}
	public String getAppliedBy() {
		return appliedBy;
	}
	public void setAppliedBy(String appliedBy) {
		this.appliedBy = appliedBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserRemarks() {
		return userRemarks;
	}
	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}
	
	
	
	

}
