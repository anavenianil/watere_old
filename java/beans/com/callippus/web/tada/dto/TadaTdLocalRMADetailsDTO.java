package com.callippus.web.tada.dto;

import java.util.Date;

public class TadaTdLocalRMADetailsDTO {
	private String settlementRequestId;
	private int id;
	private String type;
	private Date localRMADate;
	private String localFromPlace;
	private String localToPlace;
	private String localCMode;
	private float localDistance;
	private float amountPerKmLocal;
	private float claimedAmount;
	private float localDistanceAftRes;
	private float amountPerKmLocalAftRes;
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
	public Date getLocalRMADate() {
		return localRMADate;
	}
	public void setLocalRMADate(Date localRMADate) {
		this.localRMADate = localRMADate;
	}
	public String getLocalFromPlace() {
		return localFromPlace;
	}
	public void setLocalFromPlace(String localFromPlace) {
		this.localFromPlace = localFromPlace;
	}
	public String getLocalToPlace() {
		return localToPlace;
	}
	public void setLocalToPlace(String localToPlace) {
		this.localToPlace = localToPlace;
	}
	public String getLocalCMode() {
		return localCMode;
	}
	public void setLocalCMode(String localCMode) {
		this.localCMode = localCMode;
	}
	public float getLocalDistance() {
		return localDistance;
	}
	public void setLocalDistance(float localDistance) {
		this.localDistance = localDistance;
	}
	public float getAmountPerKmLocal() {
		return amountPerKmLocal;
	}
	public void setAmountPerKmLocal(float amountPerKmLocal) {
		this.amountPerKmLocal = amountPerKmLocal;
	}
	public float getClaimedAmount() {
		return claimedAmount;
	}
	public void setClaimedAmount(float claimedAmount) {
		this.claimedAmount = claimedAmount;
	}
	public float getLocalDistanceAftRes() {
		return localDistanceAftRes;
	}
	public void setLocalDistanceAftRes(float localDistanceAftRes) {
		this.localDistanceAftRes = localDistanceAftRes;
	}
	public float getAmountPerKmLocalAftRes() {
		return amountPerKmLocalAftRes;
	}
	public void setAmountPerKmLocalAftRes(float amountPerKmLocalAftRes) {
		this.amountPerKmLocalAftRes = amountPerKmLocalAftRes;
	}
	public float getClaimedAmountAftRes() {
		return claimedAmountAftRes;
	}
	public void setClaimedAmountAftRes(float claimedAmountAftRes) {
		this.claimedAmountAftRes = claimedAmountAftRes;
	}
	
	
	

}
