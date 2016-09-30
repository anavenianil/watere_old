package com.callippus.web.beans.dto;

public class DynamicWorkflowTxnDTO extends CommonDTO {
	private int requestID;
	private int requestTypeID;
	private String dynamicTo;

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(int requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public String getDynamicTo() {
		return dynamicTo;
	}

	public void setDynamicTo(String dynamicTo) {
		this.dynamicTo = dynamicTo;
	}

}
