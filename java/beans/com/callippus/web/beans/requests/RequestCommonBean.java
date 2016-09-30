package com.callippus.web.beans.requests;

import java.util.Date;

public class RequestCommonBean {
	private String requestID;
	private String workflowID;
	private String historyID;
	private Date assignedDate;
	private String requestTypeID;
	private String actionedDate;
	private String ipAddress;
	private String remarks;
	private int status;
	private String stageID;
	private String sfID;
	private String parentID;
	private int stageStatus;
	private int assignedRoleID;
	private String appliedBy;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public String getActionedDate() {
		return actionedDate;
	}

	public void setActionedDate(String actionedDate) {
		this.actionedDate = actionedDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStageID() {
		return stageID;
	}

	public void setStageID(String stageID) {
		this.stageID = stageID;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public int getStageStatus() {
		return stageStatus;
	}

	public void setStageStatus(int stageStatus) {
		this.stageStatus = stageStatus;
	}

	public int getAssignedRoleID() {
		return assignedRoleID;
	}

	public void setAssignedRoleID(int assignedRoleID) {
		this.assignedRoleID = assignedRoleID;
	}

	public String getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(String appliedBy) {
		this.appliedBy = appliedBy;
	}

}
