package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxRunStatusDTO extends CommonDTO{
	
	
private int finYearId;
private String runType;
private PayFinYearDTO finDetails;
private int runId;
private int plannedStatus;
private int projectedStatus;
private int actualStatus;
private String finYear;



public String getFinYear() {
	return finYear;
}
public void setFinYear(String finYear) {
	this.finYear = finYear;
}
public int getPlannedStatus() {
	return plannedStatus;
}
public void setPlannedStatus(int plannedStatus) {
	this.plannedStatus = plannedStatus;
}
public int getProjectedStatus() {
	return projectedStatus;
}
public void setProjectedStatus(int projectedStatus) {
	this.projectedStatus = projectedStatus;
}
public int getActualStatus() {
	return actualStatus;
}
public void setActualStatus(int actualStatus) {
	this.actualStatus = actualStatus;
}
public int getRunId() {
	return runId;
}
public void setRunId(int runId) {
	this.runId = runId;
}
public PayFinYearDTO getFinDetails() {
	return finDetails;
}
public void setFinDetails(PayFinYearDTO finDetails) {
	this.finDetails = finDetails;
}
public int getFinYearId() {
	return finYearId;
}
public void setFinYearId(int finYearId) {
	this.finYearId = finYearId;
}
public String getRunType() {
	return runType;
}
public void setRunType(String runType) {
	this.runType = runType;
}

}
