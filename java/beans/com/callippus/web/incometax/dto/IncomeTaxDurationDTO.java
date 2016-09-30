package com.callippus.web.incometax.dto;

import java.util.ArrayList;
import java.util.Date;

public class IncomeTaxDurationDTO {

	private int id;
	private int finYearId;
	private String runType;
	private Date fromDate;
	private Date toDate;
	private int status;
	
	private PayFinYearDTO itFinYearList;
	
	
	public PayFinYearDTO getItFinYearList() {
		return itFinYearList;
	}
	public void setItFinYearList(PayFinYearDTO itFinYearList) {
		this.itFinYearList = itFinYearList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
}
