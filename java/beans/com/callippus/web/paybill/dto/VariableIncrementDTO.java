package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class VariableIncrementDTO extends CommonDTO {
	private String pk;
	private String gradePayFrom;
	private String gradePayTo;
	private String incrementValue;
	private Date effDate;
	
	

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public String getIncrementValue() {
		return incrementValue;
	}

	public void setIncrementValue(String incrementValue) {
		this.incrementValue = incrementValue;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getGradePayFrom() {
		return gradePayFrom;
	}

	public void setGradePayFrom(String gradePayFrom) {
		this.gradePayFrom = gradePayFrom;
	}

	public String getGradePayTo() {
		return gradePayTo;
	}

	public void setGradePayTo(String gradePayTo) {
		this.gradePayTo = gradePayTo;
	}

}
