package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxSavingsRunDTO extends CommonDTO{
	private int finYearId;
	private String sfid;
	private String runType;
	private int confTypeId;
	private int amount;
	private int runId;
	private float totalDeductions;
	
	
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public int getFinYearId() {
		return finYearId;
	}
	public void setFinYearId(int finYearId) {
		this.finYearId = finYearId;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
	
	public int getConfTypeId() {
		return confTypeId;
	}
	public void setConfTypeId(int confTypeId) {
		this.confTypeId = confTypeId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public float getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(float totalDeductions) {
		this.totalDeductions = totalDeductions;
	}
	
	
}
