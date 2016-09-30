package com.callippus.web.beans.dto;

import java.util.Date;

public class PayChallanDetailsDTO {
	private Integer id;
	private Integer itTxnId;
	private String challanNo;
	private Date challanDate;
	private Integer challanAmt;
	private String bankBSRNo;
	private String challanRemarks;
	private Integer status;
	
	private Date creationDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	private String sfid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public Integer getItTxnId() {
		return itTxnId;
	}
	public void setItTxnId(Integer itTxnId) {
		this.itTxnId = itTxnId;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public Date getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}
	public Integer getChallanAmt() {
		return challanAmt;
	}
	public void setChallanAmt(Integer challanAmt) {
		this.challanAmt = challanAmt;
	}
	public String getBankBSRNo() {
		return bankBSRNo;
	}
	public void setBankBSRNo(String bankBSRNo) {
		this.bankBSRNo = bankBSRNo;
	}
	
	public String getChallanRemarks() {
		return challanRemarks;
	}
	public void setChallanRemarks(String challanRemarks) {
		this.challanRemarks = challanRemarks;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	
	

}
