package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxStageDTO extends CommonDTO{
	private String taxableFlag;
	private String selectedFYear;
	private int stageId;
	private int from;
	private int to;
	private int tax;
	
	private String age;
	private int rebate;
	private int surcharge;
	private int educationCess;
	private int secHigherEducation;
	
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getRebate() {
		return rebate;
	}
	public void setRebate(int rebate) {
		this.rebate = rebate;
	}
	public int getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(int surcharge) {
		this.surcharge = surcharge;
	}
	public int getEducationCess() {
		return educationCess;
	}
	public void setEducationCess(int educationCess) {
		this.educationCess = educationCess;
	}
	public int getSecHigherEducation() {
		return secHigherEducation;
	}
	public void setSecHigherEducation(int secHigherEducation) {
		this.secHigherEducation = secHigherEducation;
	}
	public String getTaxableFlag() {
		return taxableFlag;
	}
	public void setTaxableFlag(String taxableFlag) {
		this.taxableFlag = taxableFlag;
	}
	public String getSelectedFYear() {
		return selectedFYear;
	}
	public void setSelectedFYear(String selectedFYear) {
		this.selectedFYear = selectedFYear;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public int getTax() {
		return tax;
	}
	public void setTax(int tax) {
		this.tax = tax;
	}
	
	

}
