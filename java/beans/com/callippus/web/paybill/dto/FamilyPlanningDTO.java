package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class FamilyPlanningDTO extends CommonDTO {
	private String gradePayFrom;
	private String gradePayTo;
	private String rate;
	private Date effDate;
	
	

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
