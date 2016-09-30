package com.callippus.web.loan.beans.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class GPFClosingBalanceDTO extends CommonDTO {
	private String sfID;
	private float balance;
	private Date fromDate;
	private Date toDate;

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
