package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class EmpVarIncHistoryDTO extends CommonDTO{
	private int id;
	private int varIncPt;
	private Date vatIncStartDate;
	private Date varIncEndDate;
	private int gradePayId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVarIncPt() {
		return varIncPt;
	}
	public void setVarIncPt(int varIncPt) {
		this.varIncPt = varIncPt;
	}
	public Date getVatIncStartDate() {
		return vatIncStartDate;
	}
	public void setVatIncStartDate(Date vatIncStartDate) {
		this.vatIncStartDate = vatIncStartDate;
	}
	public Date getVarIncEndDate() {
		return varIncEndDate;
	}
	public void setVarIncEndDate(Date varIncEndDate) {
		this.varIncEndDate = varIncEndDate;
	}
	public int getGradePayId() {
		return gradePayId;
	}
	public void setGradePayId(int gradePayId) {
		this.gradePayId = gradePayId;
	}
	
	
}
