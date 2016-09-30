package com.callippus.web.beans.MT;



import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;


public class MTApplicationBean {
	private String driverIdSfid;
	private int driverId;
	private int driverType;
	private String driverTravelAgencyName;
	private String driverName;
	private String driverMobileNo;
	private String creationDate;
	private String lastModifiedDate;
	private EmployeeBean employeeDetails;
	private int categoryId;
	private String categoryName;
	private int carriageType;
	
	private String catDesc;
	private String status;
	private int vehicleId;
	private int vehicleType;
	private String vehicleTravelAgencyName;
	private int vehicleCategoryId;
	private String vehicleNo;
	private String vehicleName;
	private int vehicleTypeid;
	private int travelId;
	private String travelAgencyName;
	private String contactPerson;
	private String travelMobileNo;
	private String address;
	
	private String param;
	private String vehicleStatus;
	private String driverStatus;
	private int applicationId;
	private String requestedBy;
	private String designation;
	private String sfID;
	private String requestedFor;
	private String typeOfVehicle;
	private String noOfPeople;
	private String travellingDate;
	private String time;
	private String pickupPoint;
	private String droppingPoint;
	private String flightNo;
	private String arrivalTime;
	private String dispalyCard;
	private String purposeOfVisiting;
	private String articleType;
	private String articleSize;
	private String accPlace;
	private String accAvilable;
	private String accSuiteNo;
	private String remarks;
	private String rVehicleId;
	private String rDriverId;
	private int addressId;
	private String addressName;
	private String type;
	private String message;
	private String length;
	private String height;
	private String weight;
	private String quantity;
	private String breadth;
	private String returnVehicleId;
	private String returnTime;
	private String returnPeople;
	private String returnTypeOfVehicle;
	private String returnDate;
	private String returnPickUpPoint;
	private String returnDroppingPoint;
	private String beanName;
	private String vehicleCategoryName;
	private String modelName;
	private String modelDesc;
	private int modelId;
	private int vehicleModelId;
	
	private String initialReading;
	private String mileage;
	private int vehiclePoolType;
	private String  fuelCapacity;
	private String pk;
	private int vehicleSensitiveType;
	
	private String mobileNo;
	private String pickUpTime;
	private String returnPickUpTime;
	private String otherDestiName;
	private String fromDate;
	private String toDate;
	private String fromTime;
	private String toTime;
	private int counter;
	private String dedicatedPerson;
	
	private String otherSourceName;
	private String articleCarry;
	private String accommodation;
	private String vehicleReturn;
	private String returnOtherSourceName;
	private String returnOtherDestiName;
	private String vehicleReturnSame;
	private String returnArticleCarry;
	
	
  
    private String requestID;
    private String requestTypeID;
    private String requestType;
    private String ipAddress;
    private String estimatedDateTime;
    private String returnArticleType;
    private String returnLength;
    private String returnBreadth;
    private String returnHeight;
    private String returnWeight;
    private String returnQuantity;
    private String returnEstimatedDateTime;
	private String articleJson;
	

	private String todayDate;
	private String natureOfDuty;
	private String fromPlace;
	private String toPlace;
	private String startingMilometerRde;
	private String endingMilometerRde;
	private String totKilometers;
	private String fuel;
	private String engineOil;
	private String fuelConsumed;
	private String engOilConsumed;
	private String year;
	private int fuelTypeId;
	
	private String fuelMRD;
	private String engineOilMRD;
	private String coolentConsumed;
	private String coolentMRD;
	private String coolent;
	
	private VehicleMileageDTO vehicleMileageDTO;
	
	private String onwardMainJSON;
	private String returnMainJSON;
	
	private String searchFromDate;
	private String searchFromTime;
	private String searchToDate;
	private String searchToTime;
	private String currentDate;
	private int journeyId;
	private int mapId;
	private String releasedDate;
	private String releasedVehiclesJSON;
	private String deallocateJourneyJSON;
	private String freeDedicatedVehicleJSON;
	private int allocId;
	private String roleId;
	private String requestId;	
	private int noOfDays;
	private int RnoOfDays;
	
	private String designationID;
	

	public int getRnoOfDays() {
		return RnoOfDays;
	}

	public void setRnoOfDays(int rnoOfDays) {
		RnoOfDays = rnoOfDays;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getAllocId() {
		return allocId;
	}

	public void setAllocId(int allocId) {
		this.allocId = allocId;
	}

	public String getDeallocateJourneyJSON() {
		return deallocateJourneyJSON;
	}

	public void setDeallocateJourneyJSON(String deallocateJourneyJSON) {
		this.deallocateJourneyJSON = deallocateJourneyJSON;
	}

	public String getReleasedVehiclesJSON() {
		return releasedVehiclesJSON;
	}

	public void setReleasedVehiclesJSON(String releasedVehiclesJSON) {
		this.releasedVehiclesJSON = releasedVehiclesJSON;
	}

	public String getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getSearchFromDate() {
		return searchFromDate;
	}

	public void setSearchFromDate(String searchFromDate) {
		this.searchFromDate = searchFromDate;
	}

	public String getSearchFromTime() {
		return searchFromTime;
	}

	public void setSearchFromTime(String searchFromTime) {
		this.searchFromTime = searchFromTime;
	}

	public String getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}

	public String getSearchToTime() {
		return searchToTime;
	}

	public void setSearchToTime(String searchToTime) {
		this.searchToTime = searchToTime;
	}

	public String getOnwardMainJSON() {
		return onwardMainJSON;
	}

	public void setOnwardMainJSON(String onwardMainJSON) {
		this.onwardMainJSON = onwardMainJSON;
	}

	public String getReturnMainJSON() {
		return returnMainJSON;
	}

	public void setReturnMainJSON(String returnMainJSON) {
		this.returnMainJSON = returnMainJSON;
	}

	public VehicleMileageDTO getVehicleMileageDTO() {
		return vehicleMileageDTO;
	}

	public void setVehicleMileageDTO(VehicleMileageDTO vehicleMileageDTO) {
		this.vehicleMileageDTO = vehicleMileageDTO;
	}

	public String getCoolent() {
		return coolent;
	}

	public void setCoolent(String coolent) {
		this.coolent = coolent;
	}

	public String getFuelMRD() {
		return fuelMRD;
	}

	public void setFuelMRD(String fuelMRD) {
		this.fuelMRD = fuelMRD;
	}

	public String getEngineOilMRD() {
		return engineOilMRD;
	}

	public void setEngineOilMRD(String engineOilMRD) {
		this.engineOilMRD = engineOilMRD;
	}

	public String getCoolentConsumed() {
		return coolentConsumed;
	}

	public void setCoolentConsumed(String coolentConsumed) {
		this.coolentConsumed = coolentConsumed;
	}

	public String getCoolentMRD() {
		return coolentMRD;
	}

	public void setCoolentMRD(String coolentMRD) {
		this.coolentMRD = coolentMRD;
	}

	public int getFuelTypeId() {
		return fuelTypeId;
	}

	public void setFuelTypeId(int fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFuelConsumed() {
		return fuelConsumed;
	}

	public void setFuelConsumed(String fuelConsumed) {
		this.fuelConsumed = fuelConsumed;
	}

	public String getEngOilConsumed() {
		return engOilConsumed;
	}

	public void setEngOilConsumed(String engOilConsumed) {
		this.engOilConsumed = engOilConsumed;
	}

	public String getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(String todayDate) {
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

	public String getStartingMilometerRde() {
		return startingMilometerRde;
	}

	public void setStartingMilometerRde(String startingMilometerRde) {
		this.startingMilometerRde = startingMilometerRde;
	}

	public String getEndingMilometerRde() {
		return endingMilometerRde;
	}

	public void setEndingMilometerRde(String endingMilometerRde) {
		this.endingMilometerRde = endingMilometerRde;
	}

	public String getTotKilometers() {
		return totKilometers;
	}

	public void setTotKilometers(String totKilometers) {
		this.totKilometers = totKilometers;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public String getEngineOil() {
		return engineOil;
	}

	public void setEngineOil(String engineOil) {
		this.engineOil = engineOil;
	}

	public String getArticleJson() {
		return articleJson;
	}

	public void setArticleJson(String articleJson) {
		this.articleJson = articleJson;
	}

	public String getReturnEstimatedDateTime() {
		return returnEstimatedDateTime;
	}

	public void setReturnEstimatedDateTime(String returnEstimatedDateTime) {
		this.returnEstimatedDateTime = returnEstimatedDateTime;
	}

	public String getReturnArticleType() {
		return returnArticleType;
	}

	public void setReturnArticleType(String returnArticleType) {
		this.returnArticleType = returnArticleType;
	}

	public String getReturnLength() {
		return returnLength;
	}

	public void setReturnLength(String returnLength) {
		this.returnLength = returnLength;
	}

	public String getReturnBreadth() {
		return returnBreadth;
	}

	public void setReturnBreadth(String returnBreadth) {
		this.returnBreadth = returnBreadth;
	}

	public String getReturnHeight() {
		return returnHeight;
	}

	public void setReturnHeight(String returnHeight) {
		this.returnHeight = returnHeight;
	}

	public String getReturnWeight() {
		return returnWeight;
	}

	public void setReturnWeight(String returnWeight) {
		this.returnWeight = returnWeight;
	}

	public String getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(String returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public String getEstimatedDateTime() {
	return estimatedDateTime;
	}

	public void setEstimatedDateTime(String estimatedDateTime) {
	this.estimatedDateTime = estimatedDateTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getVehicleReturnSame() {
		return vehicleReturnSame;
	}

	public void setVehicleReturnSame(String vehicleReturnSame) {
		this.vehicleReturnSame = vehicleReturnSame;
	}

	public String getReturnArticleCarry() {
		return returnArticleCarry;
	}

	public void setReturnArticleCarry(String returnArticleCarry) {
		this.returnArticleCarry = returnArticleCarry;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getReturnOtherSourceName() {
		return returnOtherSourceName;
	}

	public void setReturnOtherSourceName(String returnOtherSourceName) {
		this.returnOtherSourceName = returnOtherSourceName;
	}

	public String getReturnOtherDestiName() {
		return returnOtherDestiName;
	}

	public void setReturnOtherDestiName(String returnOtherDestiName) {
		this.returnOtherDestiName = returnOtherDestiName;
	}

	public String getOtherSourceName() {
		return otherSourceName;
	}

	public void setOtherSourceName(String otherSourceName) {
		this.otherSourceName = otherSourceName;
	}

	public String getArticleCarry() {
		return articleCarry;
	}

	public void setArticleCarry(String articleCarry) {
		this.articleCarry = articleCarry;
	}

	public String getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
	}

	public String getVehicleReturn() {
		return vehicleReturn;
	}

	public void setVehicleReturn(String vehicleReturn) {
		this.vehicleReturn = vehicleReturn;
	}

	public String getDedicatedPerson() {
		return dedicatedPerson;
	}

	public void setDedicatedPerson(String dedicatedPerson) {
		this.dedicatedPerson = dedicatedPerson;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public String getReturnPickUpTime() {
		return returnPickUpTime;
	}

	public String getOtherDestiName() {
		return otherDestiName;
	}

	public void setOtherDestiName(String otherDestiName) {
		this.otherDestiName = otherDestiName;
	}

	public void setReturnPickUpTime(String returnPickUpTime) {
		this.returnPickUpTime = returnPickUpTime;
	}

	
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getVehicleTravelAgencyName() {
		return vehicleTravelAgencyName;
	}

	public void setVehicleTravelAgencyName(String vehicleTravelAgencyName) {
		this.vehicleTravelAgencyName = vehicleTravelAgencyName;
	}

	public String getDriverTravelAgencyName() {
		return driverTravelAgencyName;
	}

	public void setDriverTravelAgencyName(String driverTravelAgencyName) {
		this.driverTravelAgencyName = driverTravelAgencyName;
	}

	
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	
	public String getFuelCapacity() {
		return fuelCapacity;
	}

	public void setFuelCapacity(String fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
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

	
	
	public String getInitialReading() {
		return initialReading;
	}

	public void setInitialReading(String initialReading) {
		this.initialReading = initialReading;
	}

	public int getCarriageType() {
		return carriageType;
	}

	public void setCarriageType(int carriageType) {
		this.carriageType = carriageType;
	}

	
	
	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(int vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	

	
	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public String getDriverIdSfid() {
		return driverIdSfid;
	}

	public void setDriverIdSfid(String driverIdSfid) {
		this.driverIdSfid = driverIdSfid;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public String getVehicleCategoryName() {
		return vehicleCategoryName;
	}

	public void setVehicleCategoryName(String vehicleCategoryName) {
		this.vehicleCategoryName = vehicleCategoryName;
	}

	

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getReturnPickUpPoint() {
		return returnPickUpPoint;
	}

	public void setReturnPickUpPoint(String returnPickUpPoint) {
		this.returnPickUpPoint = returnPickUpPoint;
	}

	public String getReturnDroppingPoint() {
		return returnDroppingPoint;
	}

	public void setReturnDroppingPoint(String returnDroppingPoint) {
		this.returnDroppingPoint = returnDroppingPoint;
	}

	public String getReturnVehicleId() {
		return returnVehicleId;
	}

	public void setReturnVehicleId(String returnVehicleId) {
		this.returnVehicleId = returnVehicleId;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getReturnPeople() {
		return returnPeople;
	}

	public void setReturnPeople(String returnPeople) {
		this.returnPeople = returnPeople;
	}

	public String getReturnTypeOfVehicle() {
		return returnTypeOfVehicle;
	}

	public void setReturnTypeOfVehicle(String returnTypeOfVehicle) {
		this.returnTypeOfVehicle = returnTypeOfVehicle;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getBreadth() {
		return breadth;
	}

	public void setBreadth(String breadth) {
		this.breadth = breadth;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	

	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getRequestedFor() {
		return requestedFor;
	}

	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}

	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}

	public void setTypeOfVehicle(String typeOfVehicle) {
		this.typeOfVehicle = typeOfVehicle;
	}

	public String getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(String noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public String getTravellingDate() {
		return travellingDate;
	}

	public void setTravellingDate(String travellingDate) {
		this.travellingDate = travellingDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPickupPoint() {
		return pickupPoint;
	}

	public void setPickupPoint(String pickupPoint) {
		this.pickupPoint = pickupPoint;
	}

	public String getDroppingPoint() {
		return droppingPoint;
	}

	public void setDroppingPoint(String droppingPoint) {
		this.droppingPoint = droppingPoint;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDispalyCard() {
		return dispalyCard;
	}

	public void setDispalyCard(String dispalyCard) {
		this.dispalyCard = dispalyCard;
	}

	public String getPurposeOfVisiting() {
		return purposeOfVisiting;
	}

	public void setPurposeOfVisiting(String purposeOfVisiting) {
		this.purposeOfVisiting = purposeOfVisiting;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleSize() {
		return articleSize;
	}

	public void setArticleSize(String articleSize) {
		this.articleSize = articleSize;
	}

	public String getAccPlace() {
		return accPlace;
	}

	public void setAccPlace(String accPlace) {
		this.accPlace = accPlace;
	}

	public String getAccAvilable() {
		return accAvilable;
	}

	public void setAccAvilable(String accAvilable) {
		this.accAvilable = accAvilable;
	}

	public String getAccSuiteNo() {
		return accSuiteNo;
	}

	public void setAccSuiteNo(String accSuiteNo) {
		this.accSuiteNo = accSuiteNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getrVehicleId() {
		return rVehicleId;
	}

	public void setrVehicleId(String rVehicleId) {
		this.rVehicleId = rVehicleId;
	}

	public String getrDriverId() {
		return rDriverId;
	}

	public void setrDriverId(String rDriverId) {
		this.rDriverId = rDriverId;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public String getDriverStatus() {
		return driverStatus;
	}

	public void setDriverStatus(String driverStatus) {
		this.driverStatus = driverStatus;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	

	
	public int getDriverType() {
		return driverType;
	}

	public void setDriverType(int driverType) {
		this.driverType = driverType;
	}

	
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverMobileNo() {
		return driverMobileNo;
	}

	public void setDriverMobileNo(String driverMobileNo) {
		this.driverMobileNo = driverMobileNo;
	}



	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getVehicleTypeid() {
		return vehicleTypeid;
	}

	public void setVehicleTypeid(int vehicleTypeid) {
		this.vehicleTypeid = vehicleTypeid;
	}

	public int getTravelId() {
		return travelId;
	}

	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}

	public String getTravelAgencyName() {
		return travelAgencyName;
	}

	public void setTravelAgencyName(String travelAgencyName) {
		this.travelAgencyName = travelAgencyName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getTravelMobileNo() {
		return travelMobileNo;
	}

	public void setTravelMobileNo(String travelMobileNo) {
		this.travelMobileNo = travelMobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFreeDedicatedVehicleJSON() {
		return freeDedicatedVehicleJSON;
	}

	public void setFreeDedicatedVehicleJSON(String freeDedicatedVehicleJSON) {
		this.freeDedicatedVehicleJSON = freeDedicatedVehicleJSON;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDesignationID() {
		return designationID;
	}

	public void setDesignationID(String designationID) {
		this.designationID = designationID;
	}

}