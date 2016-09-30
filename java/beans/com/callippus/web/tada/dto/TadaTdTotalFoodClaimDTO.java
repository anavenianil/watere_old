package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TadaTdTotalFoodClaimDTO extends CommonDTO{
	private String settlementRequestId;
	private String foodAmountChoice;
	private float totalClaimedFoodAmount;
	
	
	public String getSettlementRequestId() {
		return settlementRequestId;
	}
	public void setSettlementRequestId(String settlementRequestId) {
		this.settlementRequestId = settlementRequestId;
	}
	public String getFoodAmountChoice() {
		return foodAmountChoice;
	}
	public void setFoodAmountChoice(String foodAmountChoice) {
		this.foodAmountChoice = foodAmountChoice;
	}
	public float getTotalClaimedFoodAmount() {
		return totalClaimedFoodAmount;
	}
	public void setTotalClaimedFoodAmount(float totalClaimedFoodAmount) {
		this.totalClaimedFoodAmount = totalClaimedFoodAmount;
	}
	
	
	

}
