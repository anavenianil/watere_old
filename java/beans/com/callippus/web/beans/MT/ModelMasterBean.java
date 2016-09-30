package com.callippus.web.beans.MT;

import com.callippus.web.beans.dto.CommonDTO;

public class ModelMasterBean extends CommonDTO{
	private int modelId;
	private int vehicleCategoryId;
	private String modelName;
	private String modelDesc;
	private String vehicleCategoryName;
	private VehicleCategoryMasterBean categoryDetails;
	
	public VehicleCategoryMasterBean getCategoryDetails() {
		return categoryDetails;
	}
	public void setCategoryDetails(VehicleCategoryMasterBean categoryDetails) {
		this.categoryDetails = categoryDetails;
	}
	public String getVehicleCategoryName() {
		return vehicleCategoryName;
	}
	public void setVehicleCategoryName(String vehicleCategoryName) {
		this.vehicleCategoryName = vehicleCategoryName;
	}
	public int getVehicleCategoryId() {
		return vehicleCategoryId;
	}
	public void setVehicleCategoryId(int vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}
	
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelDesc() {
		return modelDesc;
	}
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}
	
}