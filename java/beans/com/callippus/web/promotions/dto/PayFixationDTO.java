package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.requests.DoPartBean;

public class PayFixationDTO extends CommonDTO {
	private AssessmentDetailsDTO assessmentDetails;
	private int assessmentID;
	private float pay;
	private float newPay;
	private float gradePay;
	private float newGradePay;
	private float twoAddPay;
	private float newTwoAddPay;
	private DoPartBean doPartDetails;
	private int doPartID;
	private int referenceId;
	private int categoryId;
	private Date incrementsAccepteddate;
	private Date fixationAcceptedDate;
	private Date fixationAcceptedDateFinance;
	private String variableIncrements;
	private int incrementsDoPartId;
	private int fixationDoPartId;
	private Date varIncEndDate;
	private Integer variableIncrValue;       //This property has been defined for VariableIncrValue saving
	
	
		
	public Integer getVariableIncrValue() {
		return variableIncrValue;
	}

	public void setVariableIncrValue(Integer variableIncrValue) {
		this.variableIncrValue = variableIncrValue;
	}

	public Date getVarIncEndDate() {
		return varIncEndDate;
	}

	public void setVarIncEndDate(Date varIncEndDate) {
		this.varIncEndDate = varIncEndDate;
	}

	public String getVariableIncrements() {
		return variableIncrements;
	}

	public void setVariableIncrements(String variableIncrements) {
		this.variableIncrements = variableIncrements;
	}

	public Date getIncrementsAccepteddate() {
		return incrementsAccepteddate;
	}

	public void setIncrementsAccepteddate(Date incrementsAccepteddate) {
		this.incrementsAccepteddate = incrementsAccepteddate;
	}

	public Date getFixationAcceptedDate() {
		return fixationAcceptedDate;
	}

	public void setFixationAcceptedDate(Date fixationAcceptedDate) {
		this.fixationAcceptedDate = fixationAcceptedDate;
	}

	public int getIncrementsDoPartId() {
		return incrementsDoPartId;
	}

	public void setIncrementsDoPartId(int incrementsDoPartId) {
		this.incrementsDoPartId = incrementsDoPartId;
	}

	public int getFixationDoPartId() {
		return fixationDoPartId;
	}

	public void setFixationDoPartId(int fixationDoPartId) {
		this.fixationDoPartId = fixationDoPartId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	public AssessmentDetailsDTO getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(AssessmentDetailsDTO assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public int getAssessmentID() {
		return assessmentID;
	}

	public void setAssessmentID(int assessmentID) {
		this.assessmentID = assessmentID;
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

	public float getTwoAddPay() {
		return twoAddPay;
	}

	public void setTwoAddPay(float twoAddPay) {
		this.twoAddPay = twoAddPay;
	}

	public float getNewTwoAddPay() {
		return newTwoAddPay;
	}

	public void setNewTwoAddPay(float newTwoAddPay) {
		this.newTwoAddPay = newTwoAddPay;
	}

	public DoPartBean getDoPartDetails() {
		return doPartDetails;
	}

	public void setDoPartDetails(DoPartBean doPartDetails) {
		this.doPartDetails = doPartDetails;
	}

	public int getDoPartID() {
		return doPartID;
	}

	public void setDoPartID(int doPartID) {
		this.doPartID = doPartID;
	}

	public void setFixationAcceptedDateFinance(Date fixationAcceptedDateFinance) {
		this.fixationAcceptedDateFinance = fixationAcceptedDateFinance;
	}

	public Date getFixationAcceptedDateFinance() {
		return fixationAcceptedDateFinance;
	}

}
