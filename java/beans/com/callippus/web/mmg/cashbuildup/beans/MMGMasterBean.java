package com.callippus.web.mmg.cashbuildup.beans;

import java.util.List;

import net.sf.json.JSONObject;

public class MMGMasterBean {
	
	private String typeValue;
	private String description;
	private String param;
	private String type;
	private String name;
	@SuppressWarnings("unchecked")
	private List masterDataList;
	private List fundTypeList;
	private String id;
	private String benaName;
	private String tableName;
	private String percentage;
	private String companyCode;
	private String categoryCode;
	private String categoryId;
	private String subCategoryId;
	private String subCategoryCode;
	private String itemCode;
	private String creationDate;
	private String lastModifiedDate;
	private int status;
	private String itemCodeId;
	private String itemSubCode;
	private String materialCode;
	private String rcFlag;
	private String unitRate;
	private String consumableFlag;
	private String uom;
	private String materialName;
	private String categoryName;
	private String subCategoryName;
	private String codeName;
	private String subCodeName;
	private String companyName;
	private String uomName;
	private String itemSubCodeId;
	private String companyId;
	private String inventoryNo;
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
	private String accountHeadNumber;
	private String qtyHeld;
	private String qtyRequired;
	private String allottedFund;
	private String year;
	private String fundTypeId;
	private String departmentId;
	private String consumedFund;
	private String commitedFund;
	private String searchWith;
	private String searchWithValue;
	private String accId;
	private JSONObject inventoryDetails;
	private String changedValues;
	private String orgRoleId;
	private String financialYear;
	private String message;
	private String purchaseLimitDesc;
	
	
	public String getPurchaseLimitDesc() {
		return purchaseLimitDesc;
	}

	public void setPurchaseLimitDesc(String purchaseLimitDesc) {
		this.purchaseLimitDesc = purchaseLimitDesc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getOrgRoleId() {
		return orgRoleId;
	}

	public void setOrgRoleId(String orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	public String getChangedValues() {
		return changedValues;
	}

	public void setChangedValues(String changedValues) {
		this.changedValues = changedValues;
	}

	public JSONObject getInventoryDetails() {
		return inventoryDetails;
	}

	public void setInventoryDetails(JSONObject inventoryDetails) {
		this.inventoryDetails = inventoryDetails;
	}

	public List getFundTypeList() {
		return fundTypeList;
	}

	public void setFundTypeList(List fundTypeList) {
		this.fundTypeList = fundTypeList;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getSearchWithValue() {
		return searchWithValue;
	}

	public void setSearchWithValue(String searchWithValue) {
		this.searchWithValue = searchWithValue;
	}

	public String getSearchWith() {
		return searchWith;
	}

	public void setSearchWith(String searchWith) {
		this.searchWith = searchWith;
	}

	public String getAccountHeadNumber() {
		return accountHeadNumber;
	}

	public void setAccountHeadNumber(String accountHeadNumber) {
		this.accountHeadNumber = accountHeadNumber;
	}

	public String getQtyHeld() {
		return qtyHeld;
	}

	public void setQtyHeld(String qtyHeld) {
		this.qtyHeld = qtyHeld;
	}

	public String getQtyRequired() {
		return qtyRequired;
	}

	public void setQtyRequired(String qtyRequired) {
		this.qtyRequired = qtyRequired;
	}

	public String getAllottedFund() {
		return allottedFund;
	}

	public void setAllottedFund(String allottedFund) {
		this.allottedFund = allottedFund;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFundTypeId() {
		return fundTypeId;
	}

	public void setFundTypeId(String fundTypeId) {
		this.fundTypeId = fundTypeId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getConsumedFund() {
		return consumedFund;
	}

	public void setConsumedFund(String consumedFund) {
		this.consumedFund = consumedFund;
	}

	public String getCommitedFund() {
		return commitedFund;
	}

	public void setCommitedFund(String commitedFund) {
		this.commitedFund = commitedFund;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	public String getItemSubCodeId() {
		return itemSubCodeId;
	}

	public void setItemSubCodeId(String itemSubCodeId) {
		this.itemSubCodeId = itemSubCodeId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getSubCodeName() {
		return subCodeName;
	}

	public void setSubCodeName(String subCodeName) {
		this.subCodeName = subCodeName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getRcFlag() {
		return rcFlag;
	}

	public void setRcFlag(String rcFlag) {
		this.rcFlag = rcFlag;
	}

	public String getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(String unitRate) {
		this.unitRate = unitRate;
	}

	public String getConsumableFlag() {
		return consumableFlag;
	}

	public void setConsumableFlag(String consumableFlag) {
		this.consumableFlag = consumableFlag;
	}

	public String getItemCodeId() {
		return itemCodeId;
	}

	public void setItemCodeId(String itemCodeId) {
		this.itemCodeId = itemCodeId;
	}

	public String getItemSubCode() {
		return itemSubCode;
	}

	public void setItemSubCode(String itemSubCode) {
		this.itemSubCode = itemSubCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryCode() {
		return subCategoryCode;
	}

	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getBenaName() {
		return benaName;
	}

	public void setBenaName(String benaName) {
		this.benaName = benaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getMasterDataList() {
		return masterDataList;
	}

	public void setMasterDataList(List masterDataList) {
		this.masterDataList = masterDataList;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
