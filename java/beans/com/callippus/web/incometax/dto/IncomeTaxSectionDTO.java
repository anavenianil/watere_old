package com.callippus.web.incometax.dto;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxSectionDTO extends CommonDTO{
	
	private String secName;
	private int secOrderNo;
	private int yearFrom;
	private int yearTo;
    private int genderFlag;
    private int disabilityFlag;
    private int limit;
    private int srCitizen;
    private List<IncomeTaxSavingsDTO> configList;
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
	public int getSecOrderNo() {
		return secOrderNo;
	}
	public void setSecOrderNo(int secOrderNo) {
		this.secOrderNo = secOrderNo;
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
	public List<IncomeTaxSavingsDTO> getConfigList() {
		return configList;
	}
	public void setConfigList(List<IncomeTaxSavingsDTO> configList) {
		this.configList = configList;
	}
	public int getSrCitizen() {
		return srCitizen;
	}
	public void setSrCitizen(int srCitizen) {
		this.srCitizen = srCitizen;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public int getGenderFlag() {
		return genderFlag;
	}
	public void setGenderFlag(int genderFlag) {
		this.genderFlag = genderFlag;
	}
	public int getDisabilityFlag() {
		return disabilityFlag;
	}
	public void setDisabilityFlag(int disabilityFlag) {
		this.disabilityFlag = disabilityFlag;
	}
    
}
