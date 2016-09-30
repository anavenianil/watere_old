package com.callippus.web.beans.MT;

import java.util.Date;
import java.util.List;

public class MTJourneyDetailsDTO {
	
		private int id;
	    private int referenceID;
	    private int noOfPeople;
	    private String nameWithDesignation;
	    private Date departureDate;
	    private String departureTime;
	    private Date estimatedDate;
	    private String estimatedTime;
	    private int pickupPoint;
	    private String pickupPlace;
	    private int dropPoint;
	    private String dropPlace;
	    private int accommReqFlag;
	    private String accommPlace;
	    private int articleCarryFlag;
	    private String journeyTypeFlag;
	    private String remarks;
	    private int status;
	    
	    private String message;
	    private AddressDetailsBean mtPickAddressDetails;
	    private AddressDetailsBean mtDropAddressDetails;
	    private List<MTArticleDetailsDTO> mtArticleDetails;
	    
	    private String departureDateString;
	    private String estimatedDateString;
	    
	    
	    private List<MTVehicleAllocationtDetailsDTO> mtVehicleAllocationtDetailsDTO;

	    private Date requestFromDate;
	    private Date requestToDate;
	    private String allocId;
	
		

		public String getAllocId() {
			return allocId;
		}


		public void setAllocId(String allocId) {
			this.allocId = allocId;
		}


		public Date getRequestFromDate() {
			return requestFromDate;
		}


		public void setRequestFromDate(Date requestFromDate) {
			this.requestFromDate = requestFromDate;
		}


		public Date getRequestToDate() {
			return requestToDate;
		}


		public void setRequestToDate(Date requestToDate) {
			this.requestToDate = requestToDate;
		}


		public List<MTVehicleAllocationtDetailsDTO> getMtVehicleAllocationtDetailsDTO() {
			return mtVehicleAllocationtDetailsDTO;
		}


		public void setMtVehicleAllocationtDetailsDTO(
				List<MTVehicleAllocationtDetailsDTO> mtVehicleAllocationtDetailsDTO) {
			this.mtVehicleAllocationtDetailsDTO = mtVehicleAllocationtDetailsDTO;
		}


		public String getDepartureDateString() {
			return departureDateString;
		}


		public void setDepartureDateString(String departureDateString) {
			this.departureDateString = departureDateString;
		}


		public String getEstimatedDateString() {
			return estimatedDateString;
		}


		public void setEstimatedDateString(String estimatedDateString) {
			this.estimatedDateString = estimatedDateString;
		}


		public AddressDetailsBean getMtPickAddressDetails() {
			return mtPickAddressDetails;
		}


		public void setMtPickAddressDetails(AddressDetailsBean mtPickAddressDetails) {
			this.mtPickAddressDetails = mtPickAddressDetails;
		}


		public AddressDetailsBean getMtDropAddressDetails() {
			return mtDropAddressDetails;
		}


		public void setMtDropAddressDetails(AddressDetailsBean mtDropAddressDetails) {
			this.mtDropAddressDetails = mtDropAddressDetails;
		}


	

		public List<MTArticleDetailsDTO> getMtArticleDetails() {
			return mtArticleDetails;
		}


		public void setMtArticleDetails(List<MTArticleDetailsDTO> mtArticleDetails) {
			this.mtArticleDetails = mtArticleDetails;
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


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public int getReferenceID() {
			return referenceID;
		}


		public void setReferenceID(int referenceID) {
			this.referenceID = referenceID;
		}


		public int getNoOfPeople() {
			return noOfPeople;
		}


		public void setNoOfPeople(int noOfPeople) {
			this.noOfPeople = noOfPeople;
		}


		public String getNameWithDesignation() {
			return nameWithDesignation;
		}


		public void setNameWithDesignation(String nameWithDesignation) {
			this.nameWithDesignation = nameWithDesignation;
		}


		public Date getDepartureDate() {
			return departureDate;
		}


		public void setDepartureDate(Date departureDate) {
			this.departureDate = departureDate;
		}


		public String getDepartureTime() {
			return departureTime;
		}


		public void setDepartureTime(String departureTime) {
			this.departureTime = departureTime;
		}


		public Date getEstimatedDate() {
			return estimatedDate;
		}


		public void setEstimatedDate(Date estimatedDate) {
			this.estimatedDate = estimatedDate;
		}


		public String getEstimatedTime() {
			return estimatedTime;
		}


		public void setEstimatedTime(String estimatedTime) {
			this.estimatedTime = estimatedTime;
		}


		public int getPickupPoint() {
			return pickupPoint;
		}


		public void setPickupPoint(int pickupPoint) {
			this.pickupPoint = pickupPoint;
		}


		public String getPickupPlace() {
			return pickupPlace;
		}


		public void setPickupPlace(String pickupPlace) {
			this.pickupPlace = pickupPlace;
		}


		public int getDropPoint() {
			return dropPoint;
		}


		public void setDropPoint(int dropPoint) {
			this.dropPoint = dropPoint;
		}


		public String getDropPlace() {
			return dropPlace;
		}


		public void setDropPlace(String dropPlace) {
			this.dropPlace = dropPlace;
		}


		public int getAccommReqFlag() {
			return accommReqFlag;
		}


		public void setAccommReqFlag(int accommReqFlag) {
			this.accommReqFlag = accommReqFlag;
		}


		public String getAccommPlace() {
			return accommPlace;
		}


		public void setAccommPlace(String accommPlace) {
			this.accommPlace = accommPlace;
		}


		public int getArticleCarryFlag() {
			return articleCarryFlag;
		}


		public void setArticleCarryFlag(int articleCarryFlag) {
			this.articleCarryFlag = articleCarryFlag;
		}


		

		public String getJourneyTypeFlag() {
			return journeyTypeFlag;
		}


		public void setJourneyTypeFlag(String journeyTypeFlag) {
			this.journeyTypeFlag = journeyTypeFlag;
		}


		public String getMessage() {
			return message;
		}


		public void setMessage(String message) {
			this.message = message;
		}
	    
	    
	    
}