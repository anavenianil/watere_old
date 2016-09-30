package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class PayMastersPortingDTO extends CommonDTO{

	private Date fromDate;
	private Date toDate;
	private int runId;
	
	
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
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
