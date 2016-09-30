package com.callippus.web.beans.dto;

import java.util.Date;

public class MROPaymentDetailsDTO extends CommonDTO {

	private int referenceID;
	private String mroNumber;
	private Date mroDate;
	private float totalAmount;


	public String getMroNumber() {
		return mroNumber;
	}

	public void setMroNumber(String mroNumber) {
		this.mroNumber = mroNumber;
	}

	public Date getMroDate() {
		return mroDate;
	}

	public void setMroDate(Date mroDate) {
		this.mroDate = mroDate;
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
}
