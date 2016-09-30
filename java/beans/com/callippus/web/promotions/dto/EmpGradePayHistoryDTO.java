package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class EmpGradePayHistoryDTO extends CommonDTO{
	private int id;
	private String sfID;
	private int promotedDesignation;
	private Date promotedEffectiveDate;
	private float newGradePay;
	private String referenceType;
	private String seniorityDate;
	private String twoAddl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public int getPromotedDesignation() {
		return promotedDesignation;
	}
	public void setPromotedDesignation(int promotedDesignation) {
		this.promotedDesignation = promotedDesignation;
	}
	public Date getPromotedEffectiveDate() {
		return promotedEffectiveDate;
	}
	public void setPromotedEffectiveDate(Date promotedEffectiveDate) {
		this.promotedEffectiveDate = promotedEffectiveDate;
	}
	public float getNewGradePay() {
		return newGradePay;
	}
	public void setNewGradePay(float newGradePay) {
		this.newGradePay = newGradePay;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public String getSeniorityDate() {
		return seniorityDate;
	}
	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}
	public String getTwoAddl() {
		return twoAddl;
	}
	public void setTwoAddl(String twoAddl) {
		this.twoAddl = twoAddl;
	}
	
}
