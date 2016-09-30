package com.callippus.web.hrdg.training.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TrainingInistituteDTO extends CommonDTO{
	
	private int trainingTypeId;
	
	private String trainingType;
	
	private String shortName;
	
	private int trainingRegionId;
	private String trainingRegion;
	
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public int getTrainingTypeId() {
		return trainingTypeId;
	}
	public void setTrainingTypeId(int trainingTypeId) {
		this.trainingTypeId = trainingTypeId;
	}
	
	
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	public int getTrainingRegionId() {
		return trainingRegionId;
	}
	public void setTrainingRegionId(int trainingRegionId) {
		this.trainingRegionId = trainingRegionId;
	}
	public String getTrainingRegion() {
		return trainingRegion;
	}
	public void setTrainingRegion(String trainingRegion) {
		this.trainingRegion = trainingRegion;
	}
	

}
