package com.callippus.web.beans.MT;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class MTDriverAbsentDTO extends CommonDTO {
	
	private int id;
	private int driverId;
	private Date fromDate;
	private String fromTime;
	private Date toDate;
	private String toTime;
	private DriverDetailsBean diverDetails;
	private int status;
	private String remarks;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public DriverDetailsBean getDiverDetails() {
		return diverDetails;
	}
	public void setDiverDetails(DriverDetailsBean diverDetails) {
		this.diverDetails = diverDetails;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}