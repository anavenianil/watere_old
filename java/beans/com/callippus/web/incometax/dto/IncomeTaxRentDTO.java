package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxRentDTO extends CommonDTO{
	private String rentFromDate;
	private String rentToDate;
	private int rent;
	private String remarks;
	private int finYearId;
	private String sfid;
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	
	
	public String getRentFromDate() {
		return rentFromDate;
	}
	public void setRentFromDate(String rentFromDate) {
		this.rentFromDate = rentFromDate;
	}
	public String getRentToDate() {
		return rentToDate;
	}
	public void setRentToDate(String rentToDate) {
		this.rentToDate = rentToDate;
	}
	public int getRent() {
		return rent;
	}
	public void setRent(int rent) {
		this.rent = rent;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

}
