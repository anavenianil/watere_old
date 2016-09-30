package com.callippus.web.ltc.dto;

import com.callippus.web.beans.requests.RequestBean;

public class LtcRefundRequestDTO extends RequestBean{

	private int referenceID;
	private String requestDate;
	private String doPartID;
	private String accountentSign;
	private String billNo;
	private String sanctionNo;
	
	
	

	public String getAccountentSign() {
		return accountentSign;
	}

	public void setAccountentSign(String accountentSign) {
		this.accountentSign = accountentSign;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public String getDoPartID() {
		return doPartID;
	}

	public void setDoPartID(String doPartID) {
		this.doPartID = doPartID;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	
}
