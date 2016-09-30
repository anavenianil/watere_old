package com.callippus.web.beans.MT;

import com.callippus.web.beans.dto.CommonDTO;

public class VehicleCategoryMasterBean extends CommonDTO {
	private int categoryId;
	private String categoryName;
	private int carriageType;
	private String catDesc;
	private String carriageTypeName;
	private VehicleCarriageTypeDetailsBean carriageDetails;

	public VehicleCarriageTypeDetailsBean getCarriageDetails() {
		return carriageDetails;
	}

	public void setCarriageDetails(VehicleCarriageTypeDetailsBean carriageDetails) {
		this.carriageDetails = carriageDetails;
	}

	public String getCarriageTypeName() {
		return carriageTypeName;
	}

	public void setCarriageTypeName(String carriageTypeName) {
		this.carriageTypeName = carriageTypeName;
	}

	public int getCarriageType() {
		return carriageType;
	}

	public void setCarriageType(int carriageType) {
		this.carriageType = carriageType;
	}
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	

}