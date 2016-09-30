package com.callippus.web.beans.dto;

import java.util.Date;

public class CDADetailsDTO extends CommonDTO {
	private int requestTypeId;
	private int fundId;
	private float cdaAmount;
	private String dvNumber;
	private Date dvDate;
	private int referenceID;

	public int getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(int requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public float getCdaAmount() {
		return cdaAmount;
	}

	public void setCdaAmount(float cdaAmount) {
		this.cdaAmount = cdaAmount;
	}

	public String getDvNumber() {
		return dvNumber;
	}

	public void setDvNumber(String dvNumber) {
		this.dvNumber = dvNumber;
	}

	public Date getDvDate() {
		return dvDate;
	}

	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getReferenceID() {
		return referenceID;
	}

}
