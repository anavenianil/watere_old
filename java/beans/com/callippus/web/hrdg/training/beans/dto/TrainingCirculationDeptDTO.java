package com.callippus.web.hrdg.training.beans.dto;

import java.io.Serializable;

import com.callippus.web.beans.dto.CommonDTO;

public class TrainingCirculationDeptDTO extends CommonDTO implements Serializable{
	private Integer circulationId;
	private Integer departmentId;
	
	
	
	
	public Integer getCirculationId() {
		return circulationId;
	}
	public void setCirculationId(Integer circulationId) {
		this.circulationId = circulationId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
		
}