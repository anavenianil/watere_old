package com.callippus.web.ltc.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class LtcTypeMasterDTO extends CommonDTO {
	private Date creationDate1;
	private String validFromDate;
	private String  validToDate;

	public Date getCreationDate1() {
		return creationDate1;
	}

	public void setCreationDate1(Date creationDate1) {
		this.creationDate1 = creationDate1;
	}

	public String getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
	}

	public String getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	} 
		
	
	

}