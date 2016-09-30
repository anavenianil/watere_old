package com.callippus.web.beans.MT;

import com.callippus.web.beans.dto.CommonDTO;

public class VehicleDetailsBean extends CommonDTO{
	private int vehicleId;
	private int vehicleType;
	private String vehicleTravelAgencyName;
	private int vehicleCategoryId;
	private String vehicleNo;
	private String vehicleName;
	private String vehicleStatus;
	private String vehicleTravelAgencyNameDetail;
	private String vehicleCategoryName;
	private String vehicleTypeName;
	
	private int vehicleModelId;
	private String noOfPeople;
	private String mileage;
	private int vehicleSensitiveType;
	private String  fuelCapacity;
	private String driverName;
	private String vehicleModelName;
	private String driverMobileNo;
	private String initialReading;
	private int vehiclePoolType;
	private String vehiclePoolTypeName;
	private String vehicleSensitiveTypeName;
	private VehicleTypeDetailsBean vehicleTypeDetails;
	private ModelMasterBean modelMasterDetails;
	private VehiclePoolTypeDetailsBean vehiclePoolTypeDetails;
	private VehicleSensitiveTypeDetailsBean vehicleSensitiveTypeDetails;
	private String dedicatedPersonSfid;
	
	private String vehicleNoString;
	private int fuelTypeId;
	private FuelTypeDTO fuelTypeDetails;
	private String remarks;
	private String driverNameString;
	private TravelAgencyDetailsBean vehicleTravelAgencyDetails;

	public TravelAgencyDetailsBean getVehicleTravelAgencyDetails() {
		return vehicleTravelAgencyDetails;
	}

	public void setVehicleTravelAgencyDetails(
			TravelAgencyDetailsBean vehicleTravelAgencyDetails) {
		this.vehicleTravelAgencyDetails = vehicleTravelAgencyDetails;
	}

	public String getDriverNameString() {
		return driverNameString;
	}

	public void setDriverNameString(String driverNameString) {
		this.driverNameString = driverNameString;
	}

	public FuelTypeDTO getFuelTypeDetails() {
		return fuelTypeDetails;
	}

	public void setFuelTypeDetails(FuelTypeDTO fuelTypeDetails) {
		this.fuelTypeDetails = fuelTypeDetails;
	}

	public int getFuelTypeId() {
		return fuelTypeId;
	}

	public void setFuelTypeId(int fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}

	public String getVehicleNoString() {
		return vehicleNoString;
	}

	public void setVehicleNoString(String vehicleNoString) {
		this.vehicleNoString = vehicleNoString;
	}

	public String getDedicatedPersonSfid() {
		return dedicatedPersonSfid;
	}

	public void setDedicatedPersonSfid(String dedicatedPersonSfid) {
		this.dedicatedPersonSfid = dedicatedPersonSfid;
	}

	
	
	public VehicleTypeDetailsBean getVehicleTypeDetails() {
		return vehicleTypeDetails;
	}

	public void setVehicleTypeDetails(VehicleTypeDetailsBean vehicleTypeDetails) {
		this.vehicleTypeDetails = vehicleTypeDetails;
	}

	public ModelMasterBean getModelMasterDetails() {
		return modelMasterDetails;
	}

	public void setModelMasterDetails(ModelMasterBean modelMasterDetails) {
		this.modelMasterDetails = modelMasterDetails;
	}

	public VehiclePoolTypeDetailsBean getVehiclePoolTypeDetails() {
		return vehiclePoolTypeDetails;
	}

	public void setVehiclePoolTypeDetails(
			VehiclePoolTypeDetailsBean vehiclePoolTypeDetails) {
		this.vehiclePoolTypeDetails = vehiclePoolTypeDetails;
	}

	public VehicleSensitiveTypeDetailsBean getVehicleSensitiveTypeDetails() {
		return vehicleSensitiveTypeDetails;
	}

	public void setVehicleSensitiveTypeDetails(
			VehicleSensitiveTypeDetailsBean vehicleSensitiveTypeDetails) {
		this.vehicleSensitiveTypeDetails = vehicleSensitiveTypeDetails;
	}

	public String getVehicleTravelAgencyName() {
		return vehicleTravelAgencyName;
	}

	public void setVehicleTravelAgencyName(String vehicleTravelAgencyName) {
		this.vehicleTravelAgencyName = vehicleTravelAgencyName;
	}

	public String getFuelCapacity() {
		return fuelCapacity;
	}

	public void setFuelCapacity(String fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}


	
	public String getVehiclePoolTypeName() {
		return vehiclePoolTypeName;
	}

	public void setVehiclePoolTypeName(String vehiclePoolTypeName) {
		this.vehiclePoolTypeName = vehiclePoolTypeName;
	}

	
	
	
	public int getVehicleSensitiveType() {
		return vehicleSensitiveType;
	}

	public void setVehicleSensitiveType(int vehicleSensitiveType) {
		this.vehicleSensitiveType = vehicleSensitiveType;
	}

	public int getVehiclePoolType() {
		return vehiclePoolType;
	}

	public void setVehiclePoolType(int vehiclePoolType) {
		this.vehiclePoolType = vehiclePoolType;
	}

	
	
	public String getVehicleSensitiveTypeName() {
		return vehicleSensitiveTypeName;
	}

	public void setVehicleSensitiveTypeName(String vehicleSensitiveTypeName) {
		this.vehicleSensitiveTypeName = vehicleSensitiveTypeName;
	}



	public String getInitialReading() {
		return initialReading;
	}

	public void setInitialReading(String initialReading) {
		this.initialReading = initialReading;
	}

	public String getDriverMobileNo() {
		return driverMobileNo;
	}

	public void setDriverMobileNo(String driverMobileNo) {
		this.driverMobileNo = driverMobileNo;
	}

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	

	
	public String getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(String noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public int getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(int vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getVehicleTypeName() {
		return vehicleTypeName;
	}

	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}

	public String getVehicleCategoryName() {
		return vehicleCategoryName;
	}

	public void setVehicleCategoryName(String vehicleCategoryName) {
		this.vehicleCategoryName = vehicleCategoryName;
	}

	public String getVehicleTravelAgencyNameDetail() {
		return vehicleTravelAgencyNameDetail;
	}

	public void setVehicleTravelAgencyNameDetail(String vehicleTravelAgencyNameDetail) {
		this.vehicleTravelAgencyNameDetail = vehicleTravelAgencyNameDetail;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(int vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}


	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}