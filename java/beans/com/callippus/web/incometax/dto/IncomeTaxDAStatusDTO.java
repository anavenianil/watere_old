package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;

public class IncomeTaxDAStatusDTO extends CommonDTO{
	private String fromDate;
	private String toDate;
	private int oldDa;
	private int newDa;
	private int daType;
	private int runStatus;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getOldDa() {
		return oldDa;
	}
	public void setOldDa(int oldDa) {
		this.oldDa = oldDa;
	}
	public int getNewDa() {
		return newDa;
	}
	public void setNewDa(int newDa) {
		this.newDa = newDa;
	}
	
	public int getDaType() {
		return daType;
	}
	public void setDaType(int daType) {
		this.daType = daType;
	}
	public int getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}
	
	

}
