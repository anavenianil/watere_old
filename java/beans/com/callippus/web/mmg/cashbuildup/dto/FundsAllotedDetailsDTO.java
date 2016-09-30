package com.callippus.web.mmg.cashbuildup.dto;

import java.util.Date;

public class FundsAllotedDetailsDTO {
	private String id;
	private String fundAmount;
	private String demandNo;
	private String accountHeadId;
	private String paymentTypeId;
	private String status;
	private Date creationDate;
	private Date lastModifiedDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFundAmount() {
		return fundAmount;
	}
	public void setFundAmount(String fundAmount) {
		this.fundAmount = fundAmount;
	}
	public String getDemandNo() {
		return demandNo;
	}
	public void setDemandNo(String demandNo) {
		this.demandNo = demandNo;
	}
	public String getAccountHeadId() {
		return accountHeadId;
	}
	public void setAccountHeadId(String accountHeadId) {
		this.accountHeadId = accountHeadId;
	}
	public String getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
