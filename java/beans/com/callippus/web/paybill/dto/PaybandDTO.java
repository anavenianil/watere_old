package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class PaybandDTO extends CommonDTO {
	
	
	private int rangeFrom;
	private int rangeTo;
	private Date effDate;
	
	
	public Date getEffDate() {
		return effDate;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
	public int getRangeFrom() {
		return rangeFrom;
	}
	public void setRangeFrom(int rangeFrom) {
		this.rangeFrom = rangeFrom;
	}
	public int getRangeTo() {
		return rangeTo;
	}
	public void setRangeTo(int rangeTo) {
		this.rangeTo = rangeTo;
	}
	

}
