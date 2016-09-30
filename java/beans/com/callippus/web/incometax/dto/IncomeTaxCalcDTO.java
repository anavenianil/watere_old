package com.callippus.web.incometax.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxCalcDTO extends CommonDTO{
	private String sfid;
	private int selectedFYear;
	private String plannedMonth;
	private String actualMonth;
	private String projectedMonth;
	private int plannedTaxAmt;
	private int projectedTaxAmt;
	private int actualTaxAmt;
	private long plannedTotTaxAmt;
	private long projectedTotTaxAmt;
	private long actualTotTaxAmt;
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	
	
	
	public int getSelectedFYear() {
		return selectedFYear;
	}
	public void setSelectedFYear(int selectedFYear) {
		this.selectedFYear = selectedFYear;
	}
	public String getPlannedMonth() {
		return plannedMonth;
	}
	public void setPlannedMonth(String plannedMonth) {
		this.plannedMonth = plannedMonth;
	}
	public String getActualMonth() {
		return actualMonth;
	}
	public void setActualMonth(String actualMonth) {
		this.actualMonth = actualMonth;
	}
	public String getProjectedMonth() {
		return projectedMonth;
	}
	public void setProjectedMonth(String projectedMonth) {
		this.projectedMonth = projectedMonth;
	}
	public int getPlannedTaxAmt() {
		return plannedTaxAmt;
	}
	public void setPlannedTaxAmt(int plannedTaxAmt) {
		this.plannedTaxAmt = plannedTaxAmt;
	}
	public int getProjectedTaxAmt() {
		return projectedTaxAmt;
	}
	public void setProjectedTaxAmt(int projectedTaxAmt) {
		this.projectedTaxAmt = projectedTaxAmt;
	}
	public int getActualTaxAmt() {
		return actualTaxAmt;
	}
	public void setActualTaxAmt(int actualTaxAmt) {
		this.actualTaxAmt = actualTaxAmt;
	}
	public long getPlannedTotTaxAmt() {
		return plannedTotTaxAmt;
	}
	public void setPlannedTotTaxAmt(long plannedTotTaxAmt) {
		this.plannedTotTaxAmt = plannedTotTaxAmt;
	}
	public long getProjectedTotTaxAmt() {
		return projectedTotTaxAmt;
	}
	public void setProjectedTotTaxAmt(long projectedTotTaxAmt) {
		this.projectedTotTaxAmt = projectedTotTaxAmt;
	}
	public long getActualTotTaxAmt() {
		return actualTotTaxAmt;
	}
	public void setActualTotTaxAmt(long actualTotTaxAmt) {
		this.actualTotTaxAmt = actualTotTaxAmt;
	}
	
	

}
