package com.callippus.web.beans.MT;

import java.util.Date;

import com.callippus.web.beans.dto.StatusMasterDTO;

public class MTVehicleRequestDetailsDTO {
	
		private int id;
	    private String sfid;
	    private String requestID;
	    private int status;
	    private String createdBy;
	    private String lastModifiedBy;
	    private Date creationDate;
	    private Date lastModifiedDate;
	    private String requestTypeID;
	    private String requestType;
	    private String message;
	    
	  
		private Date allottedDate;
	    private String requestedFor;
	    private String mobileNo;
	    private String noOfPeople;
	    private String purposeOfVisiting;
	    private Date travellingDateTime;
	    private int pickupPoint;
	    private String otherSourcePlace;
	    private int droppingPoint;
	    private String otherDestinationPlace;
	    private int articleCarry;
	    private int accommodationRequired;
	    private String accommodationPlace;
	    private int returnVehicleRequired;
	    private int returnVehicleSame;
	    private String returnNoOfPeople;
	    private Date returnTravellingDateTime;
	    private int returnPickupPoint;
	    private String returnOtherSourcePlace;
	    private int returnDroppingPoint;
	    private String returnOtherDestinationPlace;
	    private int returnArticleCarry;
	    private String vehicleFlag;
	    private Date estimatedDateTime;
	    private Date returnEstimatedDateTime;
	    
	    private AddressDetailsBean pickupAddrDetails;
	    private AddressDetailsBean dropAddrDetails;
	    private VehicleDetailsBean vehicleDetails;
	    private DriverDetailsBean diverDetails;
	    
	
		private int driverId;
	    private int vehicleId;
		private String vehicleNo;
		private String driverName;
		private String rVehicleNo;
		private String rDriverName;
		private String ipAddress;
	    
		
		
	
		
		public String getIpAddress() {
			return ipAddress;
		}
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
		public String getrDriverName() {
			return rDriverName;
		}
		public void setrDriverName(String rDriverName) {
			this.rDriverName = rDriverName;
		}
		public String getrVehicleNo() {
			return rVehicleNo;
		}
		public void setrVehicleNo(String rVehicleNo) {
			this.rVehicleNo = rVehicleNo;
		}
		
		public Date getReturnEstimatedDateTime() {
			return returnEstimatedDateTime;
		}
		public void setReturnEstimatedDateTime(Date returnEstimatedDateTime) {
			this.returnEstimatedDateTime = returnEstimatedDateTime;
		}
		public Date getEstimatedDateTime() {
			return estimatedDateTime;
		}
		public void setEstimatedDateTime(Date estimatedDateTime) {
			this.estimatedDateTime = estimatedDateTime;
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
		public String getVehicleFlag() {
			return vehicleFlag;
		}
		public void setVehicleFlag(String vehicleFlag) {
			this.vehicleFlag = vehicleFlag;
		}
		public String getVehicleNo() {
			return vehicleNo;
		}
		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}
		public String getDriverName() {
			return driverName;
		}
		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}
		public int getDriverId() {
			return driverId;
		}
		public void setDriverId(int driverId) {
			this.driverId = driverId;
		}
		public int getVehicleId() {
			return vehicleId;
		}
		public void setVehicleId(int vehicleId) {
			this.vehicleId = vehicleId;
		}
		public AddressDetailsBean getPickupAddrDetails() {
			return pickupAddrDetails;
		}
		public void setPickupAddrDetails(AddressDetailsBean pickupAddrDetails) {
			this.pickupAddrDetails = pickupAddrDetails;
		}
		public AddressDetailsBean getDropAddrDetails() {
			return dropAddrDetails;
		}
		public void setDropAddrDetails(AddressDetailsBean dropAddrDetails) {
			this.dropAddrDetails = dropAddrDetails;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	    public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getSfid() {
			return sfid;
		}
		public void setSfid(String sfid) {
			this.sfid = sfid;
		}
		public String getRequestID() {
			return requestID;
		}
		public void setRequestID(String requestID) {
			this.requestID = requestID;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getLastModifiedBy() {
			return lastModifiedBy;
		}
		public void setLastModifiedBy(String lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
		}
		public Date getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
		public Date getLastModifiedDate() {
			return lastModifiedDate;
		}
		public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
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
		public Date getAllottedDate() {
			return allottedDate;
		}
		public void setAllottedDate(Date allottedDate) {
			this.allottedDate = allottedDate;
		}
		public String getRequestedFor() {
			return requestedFor;
		}
		public void setRequestedFor(String requestedFor) {
			this.requestedFor = requestedFor;
		}
		public String getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}
		public String getNoOfPeople() {
			return noOfPeople;
		}
		public void setNoOfPeople(String noOfPeople) {
			this.noOfPeople = noOfPeople;
		}
		public String getPurposeOfVisiting() {
			return purposeOfVisiting;
		}
		public void setPurposeOfVisiting(String purposeOfVisiting) {
			this.purposeOfVisiting = purposeOfVisiting;
		}
		public Date getTravellingDateTime() {
			return travellingDateTime;
		}
		public void setTravellingDateTime(Date travellingDateTime) {
			this.travellingDateTime = travellingDateTime;
		}
		public int getPickupPoint() {
			return pickupPoint;
		}
		public void setPickupPoint(int pickupPoint) {
			this.pickupPoint = pickupPoint;
		}
		public String getOtherSourcePlace() {
			return otherSourcePlace;
		}
		public void setOtherSourcePlace(String otherSourcePlace) {
			this.otherSourcePlace = otherSourcePlace;
		}
		public int getDroppingPoint() {
			return droppingPoint;
		}
		public void setDroppingPoint(int droppingPoint) {
			this.droppingPoint = droppingPoint;
		}
		public String getOtherDestinationPlace() {
			return otherDestinationPlace;
		}
		public void setOtherDestinationPlace(String otherDestinationPlace) {
			this.otherDestinationPlace = otherDestinationPlace;
		}
		public int getArticleCarry() {
			return articleCarry;
		}
		public void setArticleCarry(int articleCarry) {
			this.articleCarry = articleCarry;
		}
		public int getAccommodationRequired() {
			return accommodationRequired;
		}
		public void setAccommodationRequired(int accommodationRequired) {
			this.accommodationRequired = accommodationRequired;
		}
		public String getAccommodationPlace() {
			return accommodationPlace;
		}
		public void setAccommodationPlace(String accommodationPlace) {
			this.accommodationPlace = accommodationPlace;
		}
		public int getReturnVehicleRequired() {
			return returnVehicleRequired;
		}
		public void setReturnVehicleRequired(int returnVehicleRequired) {
			this.returnVehicleRequired = returnVehicleRequired;
		}
		public int getReturnVehicleSame() {
			return returnVehicleSame;
		}
		public void setReturnVehicleSame(int returnVehicleSame) {
			this.returnVehicleSame = returnVehicleSame;
		}
		public String getReturnNoOfPeople() {
			return returnNoOfPeople;
		}
		public void setReturnNoOfPeople(String returnNoOfPeople) {
			this.returnNoOfPeople = returnNoOfPeople;
		}
		public Date getReturnTravellingDateTime() {
			return returnTravellingDateTime;
		}
		public void setReturnTravellingDateTime(Date returnTravellingDateTime) {
			this.returnTravellingDateTime = returnTravellingDateTime;
		}
		public int getReturnPickupPoint() {
			return returnPickupPoint;
		}
		public void setReturnPickupPoint(int returnPickupPoint) {
			this.returnPickupPoint = returnPickupPoint;
		}
		public String getReturnOtherSourcePlace() {
			return returnOtherSourcePlace;
		}
		public void setReturnOtherSourcePlace(String returnOtherSourcePlace) {
			this.returnOtherSourcePlace = returnOtherSourcePlace;
		}
		public int getReturnDroppingPoint() {
			return returnDroppingPoint;
		}
		public void setReturnDroppingPoint(int returnDroppingPoint) {
			this.returnDroppingPoint = returnDroppingPoint;
		}
		public String getReturnOtherDestinationPlace() {
			return returnOtherDestinationPlace;
		}
		public void setReturnOtherDestinationPlace(String returnOtherDestinationPlace) {
			this.returnOtherDestinationPlace = returnOtherDestinationPlace;
		}
		public int getReturnArticleCarry() {
			return returnArticleCarry;
		}
		public void setReturnArticleCarry(int returnArticleCarry) {
			this.returnArticleCarry = returnArticleCarry;
		}
	    
	    
	
}