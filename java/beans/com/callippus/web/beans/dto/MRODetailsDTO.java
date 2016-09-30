package com.callippus.web.beans.dto;

public class MRODetailsDTO extends CommonDTO {
	private int referenceID;
	private int noOfDays;
	private float penalInterestAmount;
	private int penalInterestId;
	private float unUtilizedBalance;
	private float totalAmount;
	private MROPaymentDetailsDTO mroPaymentDetailsDTO;

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int getPenalInterestId() {
		return penalInterestId;
	}

	public void setPenalInterestId(int penalInterestId) {
		this.penalInterestId = penalInterestId;
	}

	public float getUnUtilizedBalance() {
		return unUtilizedBalance;
	}

	public void setUnUtilizedBalance(float unUtilizedBalance) {
		this.unUtilizedBalance = unUtilizedBalance;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setPenalInterestAmount(float penalInterestAmount) {
		this.penalInterestAmount = penalInterestAmount;
	}

	public float getPenalInterestAmount() {
		return penalInterestAmount;
	}

	public MROPaymentDetailsDTO getMroPaymentDetailsDTO() {
		return mroPaymentDetailsDTO;
	}

	public void setMroPaymentDetailsDTO(MROPaymentDetailsDTO mroPaymentDetailsDTO) {
		this.mroPaymentDetailsDTO = mroPaymentDetailsDTO;
	}

}
