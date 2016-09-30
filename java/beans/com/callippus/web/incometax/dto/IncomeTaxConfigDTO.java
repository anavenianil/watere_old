package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxConfigDTO extends CommonDTO{
	
	private String sfID;
	private String selectedFYear;
	private int savingsTypeId;
	private int projection;
	private int actual;
	private String remarks;
	private String submissionFlag;
	private IncomeTaxSavingsDTO savingsDetails;
	
	private IncomeTaxSectionMappingDTO incometaxsavingsdto=null; 
	
	
	public IncomeTaxSectionMappingDTO getIncometaxsavingsdto() {
		return incometaxsavingsdto;
	}
	public void setIncometaxsavingsdto(
			IncomeTaxSectionMappingDTO incometaxsavingsdto) {
		this.incometaxsavingsdto = incometaxsavingsdto;
	}
	public IncomeTaxSavingsDTO getSavingsDetails() {
		return savingsDetails;
	}
	public void setSavingsDetails(IncomeTaxSavingsDTO savingsDetails) {
		this.savingsDetails = savingsDetails;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	
	public String getSelectedFYear() {
		return selectedFYear;
	}
	public void setSelectedFYear(String selectedFYear) {
		this.selectedFYear = selectedFYear;
	}
	public int getSavingsTypeId() {
		return savingsTypeId;
	}
	public void setSavingsTypeId(int savingsTypeId) {
		this.savingsTypeId = savingsTypeId;
	}
	public int getProjection() {
		return projection;
	}
	public void setProjection(int projection) {
		this.projection = projection;
	}
	public int getActual() {
		return actual;
	}
	public void setActual(int actual) {
		this.actual = actual;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSubmissionFlag() {
		return submissionFlag;
	}
	public void setSubmissionFlag(String submissionFlag) {
		this.submissionFlag = submissionFlag;
	}
	
}
