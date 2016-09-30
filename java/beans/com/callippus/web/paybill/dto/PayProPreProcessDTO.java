package com.callippus.web.paybill.dto;

import java.util.Date;

public class PayProPreProcessDTO {

	private int incrementPay;
	private int doPartId;
	private int newBasic;
	private int newGrade;
	private int newTwoAdd;
	private int varIncrPoints;
	private Date varIncrStartDate;
	private Date varIncrEndDate;
	private int basicId;
	private int gradeId;
	private Date financeAccDate;
	private int payFixationDoPartId;
	private int varIncrDoPartId;
	private String sfid;
	private int designationId;
	
	
	
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public int getNewBasic() {
		return newBasic;
	}
	public void setNewBasic(int newBasic) {
		this.newBasic = newBasic;
	}
	public int getNewGrade() {
		return newGrade;
	}
	public void setNewGrade(int newGrade) {
		this.newGrade = newGrade;
	}
	public int getNewTwoAdd() {
		return newTwoAdd;
	}
	public void setNewTwoAdd(int newTwoAdd) {
		this.newTwoAdd = newTwoAdd;
	}
	public int getVarIncrPoints() {
		return varIncrPoints;
	}
	public void setVarIncrPoints(int varIncrPoints) {
		this.varIncrPoints = varIncrPoints;
	}
	public Date getVarIncrStartDate() {
		return varIncrStartDate;
	}
	public void setVarIncrStartDate(Date varIncrStartDate) {
		this.varIncrStartDate = varIncrStartDate;
	}
	public Date getVarIncrEndDate() {
		return varIncrEndDate;
	}
	public void setVarIncrEndDate(Date varIncrEndDate) {
		this.varIncrEndDate = varIncrEndDate;
	}
	public int getBasicId() {
		return basicId;
	}
	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public Date getFinanceAccDate() {
		return financeAccDate;
	}
	public void setFinanceAccDate(Date financeAccDate) {
		this.financeAccDate = financeAccDate;
	}
	public int getPayFixationDoPartId() {
		return payFixationDoPartId;
	}
	public void setPayFixationDoPartId(int payFixationDoPartId) {
		this.payFixationDoPartId = payFixationDoPartId;
	}
	public int getVarIncrDoPartId() {
		return varIncrDoPartId;
	}
	public void setVarIncrDoPartId(int varIncrDoPartId) {
		this.varIncrDoPartId = varIncrDoPartId;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public int getIncrementPay() {
		return incrementPay;
	}
	public void setIncrementPay(int incrementPay) {
		this.incrementPay = incrementPay;
	}
	public int getDoPartId() {
		return doPartId;
	}
	public void setDoPartId(int doPartId) {
		this.doPartId = doPartId;
	}
	
	
}
