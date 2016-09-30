package com.callippus.web.leave.dto;

import com.callippus.web.beans.requests.LeaveRequestBean;

public class LeaveRequestExceptionsDTO {
	private int id;
	private LeaveExceptionDetailsDTO exceptionDetails;
	private int referenceID;
	private int exceptionID;
	private LeaveRequestBean referenceDetails;

	public LeaveRequestBean getReferenceDetails() {
		return referenceDetails;
	}

	public void setReferenceDetails(LeaveRequestBean referenceDetails) {
		this.referenceDetails = referenceDetails;
	}
	public int getExceptionID() {
		return exceptionID;
	}

	public void setExceptionID(int exceptionID) {
		this.exceptionID = exceptionID;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LeaveExceptionDetailsDTO getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(LeaveExceptionDetailsDTO exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

}
