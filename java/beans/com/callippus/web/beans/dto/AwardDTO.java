package com.callippus.web.beans.dto;

public class AwardDTO extends CommonDTO{
	private String awardCatId;
	private String awardTypeId;
	private String awardMaxLimit;
	private String awardMoney;
	private String remarks;
	private String parentID;
	
	
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getAwardCatId() {
		return awardCatId;
	}
	public void setAwardCatId(String awardCatId) {
		this.awardCatId = awardCatId;
	}
	public String getAwardTypeId() {
		return awardTypeId;
	}
	public void setAwardTypeId(String awardTypeId) {
		this.awardTypeId = awardTypeId;
	}
	public String getAwardMaxLimit() {
		return awardMaxLimit;
	}
	public void setAwardMaxLimit(String awardMaxLimit) {
		this.awardMaxLimit = awardMaxLimit;
	}
	public String getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(String awardMoney) {
		this.awardMoney = awardMoney;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
