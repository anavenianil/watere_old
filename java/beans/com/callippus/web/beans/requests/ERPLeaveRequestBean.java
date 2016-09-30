package com.callippus.web.beans.requests;

import java.util.Date;


public class ERPLeaveRequestBean extends RequestBean {
	
	
	private String leaveType;
	private Date fromDate;
	private Date toDate;
	
	
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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
