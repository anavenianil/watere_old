package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxSavingsDTO extends CommonDTO{
	private String sName;
	private String sType;
	private int pk;
	private IncomeTaxSectionDTO sectionDetails;
	private int sectionId;
	private IncomeTaxSectionDTO sectionName;
	private String secName;
	private int yearFrom;
	private int yearTo;
	private String fromYear;
	private String toYear;
	//nagendra.v
	private int id;
	
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFromYear() {
		return fromYear;
	}
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}
	public String getToYear() {
		return toYear;
	}
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}
	public int getYearFrom() {
		return yearFrom;
	}
	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}
	public int getYearTo() {
		return yearTo;
	}
	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public IncomeTaxSectionDTO getSectionDetails() {
		return sectionDetails;
	}
	public void setSectionDetails(IncomeTaxSectionDTO sectionDetails) {
		this.sectionDetails = sectionDetails;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsType() {
		return sType;
	}
	public void setsType(String sType) {
		this.sType = sType;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public IncomeTaxSectionDTO getSectionName() {
		return sectionName;
	}
	public void setSectionName(IncomeTaxSectionDTO sectionName) {
		this.sectionName = sectionName;
	}
	

}
