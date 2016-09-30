package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class TravellingAllowanceDTO extends CommonDTO {
	private String gradePayFrom;
	private String gradePayTo;
	private String travellingAllowance;
	private Date effDate;
	private String basicPayFrom;
	private String basicPayTo;

	public Date getEffDate() {
		return effDate;
	}

	public String getBasicPayFrom() {
		return basicPayFrom;
	}

	public void setBasicPayFrom(String basicPayFrom) {
		this.basicPayFrom = basicPayFrom;
	}

	public String getBasicPayTo() {
		return basicPayTo;
	}

	public void setBasicPayTo(String basicPayTo) {
		this.basicPayTo = basicPayTo;
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

	public String getTravellingAllowance() {
		return travellingAllowance;
	}

	public void setTravellingAllowance(String travellingAllowance) {
		this.travellingAllowance = travellingAllowance;
	}
}
