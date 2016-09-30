package com.callippus.web.beans.MT;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class MTVehicleAbsentDTO extends CommonDTO{
	
	private int id;
	private int vehicleId;
	private Date fromDate;
	private String fromTime;
	private Date toDate;
	private String toTime;
	private VehicleDetailsBean vehicleDetails;
	private int status;
	private String remarks;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
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
	public VehicleDetailsBean getVehicleDetails() {
		return vehicleDetails;
	}
	public void setVehicleDetails(VehicleDetailsBean vehicleDetails) {
		this.vehicleDetails = vehicleDetails;
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