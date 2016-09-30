package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxSectionMappingDTO extends CommonDTO{
private int sectionId;
private int genderFlag;
private int disabilityFlag;
private int srCitizenFlag;
private int status;
private long limit=0;
//nagendra.v
private IncomeTaxSavingsDTO sectionDetails;
private int yearFrom;
private int yearTo;
private String fromYear;
private String toYear;
//nagendra.v
private int percentage;
/*private IncomeTaxConfigDTO incomTaxConfigDTO=null;*/
private IncomeTaxSavingsDTO incometaxsavingsdto=null;







public IncomeTaxSavingsDTO getIncometaxsavingsdto() {
	return incometaxsavingsdto;
}
public void setIncometaxsavingsdto(IncomeTaxSavingsDTO incometaxsavingsdto) {
	this.incometaxsavingsdto = incometaxsavingsdto;
}
/*public IncomeTaxConfigDTO getIncomTaxConfigDTO() {
	return incomTaxConfigDTO;
}
public void setIncomTaxConfigDTO(IncomeTaxConfigDTO incomTaxConfigDTO) {
	this.incomTaxConfigDTO = incomTaxConfigDTO;
}*/
public int getPercentage() {
	return percentage;
}
public void setPercentage(int percentage) {
	this.percentage = percentage;
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
/*public IncomeTaxSectionDTO getSectionDetails() {
	return sectionDetails;
}
public void setSectionDetails(IncomeTaxSectionDTO sectionDetails) {
	this.sectionDetails = sectionDetails;
}*/
public int getSectionId() {
	return sectionId;
}
public void setSectionId(int sectionId) {
	this.sectionId = sectionId;
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
public int getSrCitizenFlag() {
	return srCitizenFlag;
}
public void setSrCitizenFlag(int srCitizenFlag) {
	this.srCitizenFlag = srCitizenFlag;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public long getLimit() {
	return limit;
}
public void setLimit(long limit) {
	this.limit = limit;
}
public void setSectionDetails(IncomeTaxSavingsDTO sectionDetails) {
	this.sectionDetails = sectionDetails;
}
public IncomeTaxSavingsDTO getSectionDetails() {
	return sectionDetails;
}



}
