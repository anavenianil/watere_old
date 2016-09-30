package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class PayBillCanfinDTO extends CommonDTO {

	private String sfid;
	private Date effDate;
	private float amount;
	private int noOfInst;
	private int deductionID;
	private int presentInst;
	private PayDeductionDTO deductionDetails;
	private String toDate;
	private Date runMonth;
	private int runId;
	private int referenceId;
	
	

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public int getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	public Date getRunMonth() {
		return runMonth;
	}

	public void setRunMonth(Date runMonth) {
		this.runMonth = runMonth;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public PayDeductionDTO getDeductionDetails() {
		return deductionDetails;
	}

	public void setDeductionDetails(PayDeductionDTO deductionDetails) {
		this.deductionDetails = deductionDetails;
	}

	public int getPresentInst() {
		return presentInst;
	}

	public void setPresentInst(int presentInst) {
		this.presentInst = presentInst;
	}

	

	public int getDeductionID() {
		return deductionID;
	}

	public void setDeductionID(int deductionID) {
		this.deductionID = deductionID;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}



	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getNoOfInst() {
		return noOfInst;
	}

	public void setNoOfInst(int noOfInst) {
		this.noOfInst = noOfInst;
	}

}
