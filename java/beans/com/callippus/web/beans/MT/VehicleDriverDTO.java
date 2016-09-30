package com.callippus.web.beans.MT;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class VehicleDriverDTO extends CommonDTO{
 private int statusFlag;
 private int vehicleId;
 private int driverId;
 private VehicleDetailsBean vehicleDetails;
 private DriverDetailsBean diverDetails;
 private String driverName;
 private String vehicleNo;
 private String type;
 private int id;
 private int mapId;
 private int status;
 private int vehicleDriverMapId;
 
 private List travelVehicleList;
 private List returnTravelList;
 
public List getTravelVehicleList() {
	return travelVehicleList;
}
public void setTravelVehicleList(List travelVehicleList) {
	this.travelVehicleList = travelVehicleList;
}
public List getReturnTravelList() {
	return returnTravelList;
}
public void setReturnTravelList(List returnTravelList) {
	this.returnTravelList = returnTravelList;
}
public int getVehicleDriverMapId() {
	return vehicleDriverMapId;
}
public void setVehicleDriverMapId(int vehicleDriverMapId) {
	this.vehicleDriverMapId = vehicleDriverMapId;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public int getMapId() {
	return mapId;
}
public void setMapId(int mapId) {
	this.mapId = mapId;
}
public int getStatusFlag() {
	return statusFlag;
}
public void setStatusFlag(int statusFlag) {
	this.statusFlag = statusFlag;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getDriverName() {
	return driverName;
}
public void setDriverName(String driverName) {
	this.driverName = driverName;
}
public String getVehicleNo() {
	return vehicleNo;
}
public void setVehicleNo(String vehicleNo) {
	this.vehicleNo = vehicleNo;
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
