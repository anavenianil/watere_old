package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class PromoteesEntryDTO extends CommonDTO{
	private String sfID;
	private DesignationDTO designationFromDetails;
	private DesignationDTO designationToDetails;
	private int varIncPt;
	private int promotedDesignation;
	private Date presentEffectiveDate;
	private Date promotedEffectiveDate;
	private float pay;
	private float newPay;
	private float gradePay;
	private float newGradePay;
	private String empName;
	private String presentDesigName;
	private String promotedDesigName;
	private String referenceType;
	private String basicPay;
	private String seniorityDate;
	private Date varIncEndDate;
	private String twoAddl;
	private String empNewGradePay;
	
	
	

	public Date getVarIncEndDate() {
		return varIncEndDate;
	}
	public void setVarIncEndDate(Date varIncEndDate) {
		this.varIncEndDate = varIncEndDate;
	}
	public int getVarIncPt() {
		return varIncPt;
	}
	public void setVarIncPt(int varIncPt) {
		this.varIncPt = varIncPt;
	}
	public String getTwoAddl() {
		return twoAddl;
	}
	public void setTwoAddl(String twoAddl) {
		this.twoAddl = twoAddl;
	}
	
	public String getSeniorityDate() {
		return seniorityDate;
	}
	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}
	public String getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(String basicPay) {
		this.basicPay = basicPay;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public String getPresentDesigName() {
		return presentDesigName;
	}
	public void setPresentDesigName(String presentDesigName) {
		this.presentDesigName = presentDesigName;
	}
	public String getPromotedDesigName() {
		return promotedDesigName;
	}
	public void setPromotedDesigName(String promotedDesigName) {
		this.promotedDesigName = promotedDesigName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public DesignationDTO getDesignationFromDetails() {
		return designationFromDetails;
	}
	public void setDesignationFromDetails(DesignationDTO designationFromDetails) {
		this.designationFromDetails = designationFromDetails;
	}
	public DesignationDTO getDesignationToDetails() {
		return designationToDetails;
	}
	public void setDesignationToDetails(DesignationDTO designationToDetails) {
		this.designationToDetails = designationToDetails;
	}
	
	public int getPromotedDesignation() {
		return promotedDesignation;
	}
	public void setPromotedDesignation(int promotedDesignation) {
		this.promotedDesignation = promotedDesignation;
	}
	public Date getPresentEffectiveDate() {
		return presentEffectiveDate;
	}
	public void setPresentEffectiveDate(Date presentEffectiveDate) {
		this.presentEffectiveDate = presentEffectiveDate;
	}
	public Date getPromotedEffectiveDate() {
		return promotedEffectiveDate;
	}
	public void setPromotedEffectiveDate(Date promotedEffectiveDate) {
		this.promotedEffectiveDate = promotedEffectiveDate;
	}
	public float getPay() {
		return pay;
	}
	public void setPay(float pay) {
		this.pay = pay;
	}
	public float getNewPay() {
		return newPay;
	}
	public void setNewPay(float newPay) {
		this.newPay = newPay;
	}
	public float getGradePay() {
		return gradePay;
	}
	public void setGradePay(float gradePay) {
		this.gradePay = gradePay;
	}
	public float getNewGradePay() {
		return newGradePay;
	}
	public void setNewGradePay(float newGradePay) {
		this.newGradePay = newGradePay;
	}
	public String getEmpNewGradePay() {
		return empNewGradePay;
	}
	public void setEmpNewGradePay(String empNewGradePay) {
		this.empNewGradePay = empNewGradePay;
	}
	
	
	
	
}
