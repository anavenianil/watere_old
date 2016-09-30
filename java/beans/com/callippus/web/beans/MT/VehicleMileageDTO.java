package com.callippus.web.beans.MT;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class VehicleMileageDTO extends CommonDTO{

	private int id;
	private int vehicleId;
	private Date todayDate;
	private String natureOfDuty;
	private String fromPlace;
	private String toPlace;
	private int startingMilometerRde;
	private int endingMilometerRde;
	private int totKilometers;
	private int fuel;
	private int engineOil;
	private int status;
	private VehicleDetailsBean vehicleDetails;
	private int coolent;
	private int fuelMRD;
	private int engineOilMRD;
	private int coolentMRD;
	private int fuelConsumed;
	private int engineOilConsumed;
	private int coolentConsumed;
	
	private List<VehicleMileageDTO> maxRdeList ;
	
	
	public List<VehicleMileageDTO> getMaxRdeList() {
		return maxRdeList;
	}
	public void setMaxRdeList(List<VehicleMileageDTO> maxRdeList) {
		this.maxRdeList = maxRdeList;
	}
	public int getFuelConsumed() {
		return fuelConsumed;
	}
	public void setFuelConsumed(int fuelConsumed) {
		this.fuelConsumed = fuelConsumed;
	}
	public int getEngineOilConsumed() {
		return engineOilConsumed;
	}
	public void setEngineOilConsumed(int engineOilConsumed) {
		this.engineOilConsumed = engineOilConsumed;
	}
	public int getCoolentConsumed() {
		return coolentConsumed;
	}
	public void setCoolentConsumed(int coolentConsumed) {
		this.coolentConsumed = coolentConsumed;
	}
	public int getCoolent() {
		return coolent;
	}
	public void setCoolent(int coolent) {
		this.coolent = coolent;
	}
	public int getFuelMRD() {
		return fuelMRD;
	}
	public void setFuelMRD(int fuelMRD) {
		this.fuelMRD = fuelMRD;
	}
	public int getEngineOilMRD() {
		return engineOilMRD;
	}
	public void setEngineOilMRD(int engineOilMRD) {
		this.engineOilMRD = engineOilMRD;
	}
	public int getCoolentMRD() {
		return coolentMRD;
	}
	public void setCoolentMRD(int coolentMRD) {
		this.coolentMRD = coolentMRD;
	}
	public VehicleDetailsBean getVehicleDetails() {
		return vehicleDetails;
	}
	public void setVehicleDetails(VehicleDetailsBean vehicleDetails) {
		this.vehicleDetails = vehicleDetails;
	}
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
	public Date getTodayDate() {
		return todayDate;
	}
	public void setTodayDate(Date todayDate) {
		this.todayDate = todayDate;
	}
	public String getNatureOfDuty() {
		return natureOfDuty;
	}
	public void setNatureOfDuty(String natureOfDuty) {
		this.natureOfDuty = natureOfDuty;
	}
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public int getStartingMilometerRde() {
		return startingMilometerRde;
	}
	public void setStartingMilometerRde(int startingMilometerRde) {
		this.startingMilometerRde = startingMilometerRde;
	}
	public int getEndingMilometerRde() {
		return endingMilometerRde;
	}
	public void setEndingMilometerRde(int endingMilometerRde) {
		this.endingMilometerRde = endingMilometerRde;
	}
	public int getTotKilometers() {
		return totKilometers;
	}
	public void setTotKilometers(int totKilometers) {
		this.totKilometers = totKilometers;
	}
	public int getFuel() {
		return fuel;
	}
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	public int getEngineOil() {
		return engineOil;
	}
	public void setEngineOil(int engineOil) {
		this.engineOil = engineOil;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
