package com.callippus.web.ltc.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LtcPenalMasterDTO extends CommonDTO{
	private String fromDate;
	private String toDate;
	private String fromDateOne;
	private String toDateOne;
	private String typeValue;
	
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public String getFromDateOne() {
		return fromDateOne;
	}
	public void setFromDateOne(String fromDateOne) {
		this.fromDateOne = fromDateOne;
	}
	public String getToDateOne() {
		return toDateOne;
	}
	public void setToDateOne(String toDateOne) {
		this.toDateOne = toDateOne;
	}
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

}
