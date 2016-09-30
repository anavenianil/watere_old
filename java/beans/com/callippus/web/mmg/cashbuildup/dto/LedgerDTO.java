package com.callippus.web.mmg.cashbuildup.dto;

import java.util.Date;

public class LedgerDTO {
	private String ledgerCode;
	private String inventoryNo;
	private String materialCode;
	private String qty;
	private String cflag;
	private String rate;
	private String voucherNo;
	private String refLedgerCode;
	private String auditCheck;
	private String purpose;
	private String status;
	private String postingDate;
	private String uom;
	private String creditDebitFlag;
	
	
	public String getLedgerCode() {
		return ledgerCode;
	}
	public void setLedgerCode(String ledgerCode) {
		this.ledgerCode = ledgerCode;
	}
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getCflag() {
		return cflag;
	}
	public void setCflag(String cflag) {
		this.cflag = cflag;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getRefLedgerCode() {
		return refLedgerCode;
	}
	public void setRefLedgerCode(String refLedgerCode) {
		this.refLedgerCode = refLedgerCode;
	}
	public String getAuditCheck() {
		return auditCheck;
	}
	public void setAuditCheck(String auditCheck) {
		this.auditCheck = auditCheck;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getCreditDebitFlag() {
		return creditDebitFlag;
	}
	public void setCreditDebitFlag(String creditDebitFlag) {
		this.creditDebitFlag = creditDebitFlag;
	}	

}
