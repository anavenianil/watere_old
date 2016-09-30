package com.callippus.web.leave.dto;

import java.util.Date;

public class LeaveTxnDTO {
	private int id;
	private String sfID;
	private LeaveTypeDTO leaveTypeDetails;
	private String txnDate;
	private float noOfLeaves;
	private String strTxnDate;
	private String txnType;
	private String txnToDate;
	private String remarks;
	private int status;
	private Date creationDate;
	private String createdBy;
	private int oneTimeEntryFlag;

	public int getOneTimeEntryFlag() {
		return oneTimeEntryFlag;
	}

	public void setOneTimeEntryFlag(int oneTimeEntryFlag) {
		this.oneTimeEntryFlag = oneTimeEntryFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTxnToDate() {
		return txnToDate;
	}

	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getStrTxnDate() {
		return strTxnDate;
	}

	public void setStrTxnDate(String strTxnDate) {
		this.strTxnDate = strTxnDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public float getNoOfLeaves() {
		return noOfLeaves;
	}

	public void setNoOfLeaves(float noOfLeaves) {
		this.noOfLeaves = noOfLeaves;
	}

}
