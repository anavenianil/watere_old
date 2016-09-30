package com.callippus.web.mmg.cashbuildup.dto;

import com.callippus.web.beans.dto.CommonDTO;


public class MaterialMasterDTO extends CommonDTO{
	private String materialCode;
	private String companyCode;
	private String materialName;
	private String consumableFlag;
	private String uom;
	private String categoryCode;
	private String subCategoryCode;
	private String itemCode;
	private String itemSubCode;
	private String rcFlag;
	private String unitRate;
	private UomDTO uomName;
	
	
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getSubCategoryCode() {
		return subCategoryCode;
	}
	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemSubCode() {
		return itemSubCode;
	}
	public void setItemSubCode(String itemSubCode) {
		this.itemSubCode = itemSubCode;
	}
	public String getRcFlag() {
		return rcFlag;
	}
	public void setRcFlag(String rcFlag) {
		this.rcFlag = rcFlag;
	}
	public UomDTO getUomName() {
		return uomName;
	}
	public void setUomName(UomDTO uomName) {
		this.uomName = uomName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getConsumableFlag() {
		return consumableFlag;
	}
	public void setConsumableFlag(String consumableFlag) {
		this.consumableFlag = consumableFlag;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getUnitRate() {
		return unitRate;
	}
	public void setUnitRate(String unitRate) {
		this.unitRate = unitRate;
	}
	
	
	
}
