package com.callippus.web.promotions.beans.management;

import java.util.Date;

public class PromotionOfflineEntryBean {
	private int id;
	private int designationID;
	private String desigName;
	private Date seniorityDate;
	private int designationTo;
	private int designationFrom;
	private String sfid;
	private float basicPay;
	private float gradePay;
	private Date effectiveDate;
	
	private Integer varIncPt;
	
	public Integer getVarIncPt() {
		return varIncPt;
	}
	public void setVarIncPt(Integer varIncPt) {
		this.varIncPt = varIncPt;
	}
	private Date varIncStartDate;
	private Date varIncEndDate;
	
	
	
	public Date getVarIncStartDate() {
		return varIncStartDate;
	}
	public void setVarIncStartDate(Date varIncStartDate) {
		this.varIncStartDate = varIncStartDate;
	}
	public Date getVarIncEndDate() {
		return varIncEndDate;
	}
	public void setVarIncEndDate(Date varIncEndDate) {
		this.varIncEndDate = varIncEndDate;
	}
	public String getDesigName() {
		return desigName;
	}
	public void setDesigName(String desigName) {
		this.desigName = desigName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDesignationFrom() {
		return designationFrom;
	}
	public void setDesignationFrom(int designationFrom) {
		this.designationFrom = designationFrom;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public int getDesignationID() {
		return designationID;
	}
	public void setDesignationID(int designationID) {
		this.designationID = designationID;
	}
	public Date getSeniorityDate() {
		return seniorityDate;
	}
	public void setSeniorityDate(Date seniorityDate) {
		this.seniorityDate = seniorityDate;
	}
	public int getDesignationTo() {
		return designationTo;
	}
	public void setDesignationTo(int designationTo) {
		this.designationTo = designationTo;
	}
	public float getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(float basicPay) {
		this.basicPay = basicPay;
	}
	public float getGradePay() {
		return gradePay;
	}
	public void setGradePay(float gradePay) {
		this.gradePay = gradePay;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
	
}
