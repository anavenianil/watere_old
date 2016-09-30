package com.callippus.web.mmg.cashbuildup.dto;

import com.callippus.web.beans.requests.RequestBean;

public class InventoryHolderRequestBean extends RequestBean{
	private String name;
	private String sfid;
	private String demandPurchaseLimit;
	private String inventoryHolderType;
	private String holderName;
	private String directorateName;
	private String divisionName;
	private String designation;
	private String phone;
	private String projectName;
	private String invId;
	private String changedValues;
	private String inventoryNo;
	
	
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public String getChangedValues() {
		return changedValues;
	}
	public void setChangedValues(String changedValues) {
		this.changedValues = changedValues;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getDemandPurchaseLimit() {
		return demandPurchaseLimit;
	}
	public void setDemandPurchaseLimit(String demandPurchaseLimit) {
		this.demandPurchaseLimit = demandPurchaseLimit;
	}
	public String getInventoryHolderType() {
		return inventoryHolderType;
	}
	public void setInventoryHolderType(String inventoryHolderType) {
		this.inventoryHolderType = inventoryHolderType;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public String getDirectorateName() {
		return directorateName;
	}
	public void setDirectorateName(String directorateName) {
		this.directorateName = directorateName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getInvId() {
		return invId;
	}
	public void setInvId(String invId) {
		this.invId = invId;
	}
	
	
				
}
