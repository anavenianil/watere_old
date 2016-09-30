package com.callippus.web.beans.MT;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class MTVehicleDriverMapDTO extends CommonDTO{
 
 private int id;
 private int vehicleId;
 private int driverId;
 private String vehicleAbsentId;
 private String driverAbsentId;
 private Date allotmentFromDate;
 private String allotmentFromTime;
 private Date allotmentToDate;
 private String allotmentToTime;
 private int status;
 private String remarks;
 
 private VehicleDetailsBean vehicleDetails;
 private DriverDetailsBean diverDetails;
 
 
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
public int getDriverId() {
	return driverId;
}
public void setDriverId(int driverId) {
	this.driverId = driverId;
}
public String getVehicleAbsentId() {
	return vehicleAbsentId;
}
public void setVehicleAbsentId(String vehicleAbsentId) {
	this.vehicleAbsentId = vehicleAbsentId;
}
public String getDriverAbsentId() {
	return driverAbsentId;
}
public void setDriverAbsentId(String driverAbsentId) {
	this.driverAbsentId = driverAbsentId;
}
public Date getAllotmentFromDate() {
	return allotmentFromDate;
}
public void setAllotmentFromDate(Date allotmentFromDate) {
	this.allotmentFromDate = allotmentFromDate;
}
public String getAllotmentFromTime() {
	return allotmentFromTime;
}
public void setAllotmentFromTime(String allotmentFromTime) {
	this.allotmentFromTime = allotmentFromTime;
}
public Date getAllotmentToDate() {
	return allotmentToDate;
}
public void setAllotmentToDate(Date allotmentToDate) {
	this.allotmentToDate = allotmentToDate;
}
public String getAllotmentToTime() {
	return allotmentToTime;
}
public void setAllotmentToTime(String allotmentToTime) {
	this.allotmentToTime = allotmentToTime;
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
public VehicleDetailsBean getVehicleDetails() {
	return vehicleDetails;
}
public void setVehicleDetails(VehicleDetailsBean vehicleDetails) {
	this.vehicleDetails = vehicleDetails;
}
public DriverDetailsBean getDiverDetails() {
	return diverDetails;
}
public void setDiverDetails(DriverDetailsBean diverDetails) {
	this.diverDetails = diverDetails;
}

 

}
