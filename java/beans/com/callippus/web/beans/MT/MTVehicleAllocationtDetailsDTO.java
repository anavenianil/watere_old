package com.callippus.web.beans.MT;

import java.util.Date;

public class MTVehicleAllocationtDetailsDTO {
	
		private int id;
	    private int requestId;
	    private int journeyId;
	    private int referenceId;
	    private int vehicleDriverMapId;
	    private Date fromDate;
	    private Date toDate;
	    private int requestType;
	    private int statusFlag;
	    private int vehicleFlag;
	    private String stringFromDate;
	    private String stringToDate;
	    private String sfid;
	    
	    private MTVehicleDriverMapDTO vehicleDriverMapDetails;
	    private Date releasedDate;
	    private String vehicleStatus;
	    private int allotedNoOfPersons;
	    
	    private String driversDetails;
	    private String flag;
	    
		
		public String getFlag() {
			return flag;
		}
		public void setFlag(String flag) {
			this.flag = flag;
		}
		public String getDriversDetails() {
			return driversDetails;
		}
		public void setDriversDetails(String driversDetails) {
			this.driversDetails = driversDetails;
		}
		public int getAllotedNoOfPersons() {
			return allotedNoOfPersons;
		}
		public void setAllotedNoOfPersons(int allotedNoOfPersons) {
			this.allotedNoOfPersons = allotedNoOfPersons;
		}
		public MTVehicleDriverMapDTO getVehicleDriverMapDetails() {
			return vehicleDriverMapDetails;
		}
		public void setVehicleDriverMapDetails(
				MTVehicleDriverMapDTO vehicleDriverMapDetails) {
			this.vehicleDriverMapDetails = vehicleDriverMapDetails;
		}
		public int getJourneyId() {
			return journeyId;
		}
		public void setJourneyId(int journeyId) {
			this.journeyId = journeyId;
		}
		public String getVehicleStatus() {
			return vehicleStatus;
		}
		public void setVehicleStatus(String vehicleStatus) {
			this.vehicleStatus = vehicleStatus;
		}
		public Date getReleasedDate() {
			return releasedDate;
		}
		public void setReleasedDate(Date releasedDate) {
			this.releasedDate = releasedDate;
		}
		public String getSfid() {
			return sfid;
		}
		public void setSfid(String sfid) {
			this.sfid = sfid;
		}
		public String getStringToDate() {
			return stringToDate;
		}
		public void setStringToDate(String stringToDate) {
			this.stringToDate = stringToDate;
		}
		public String getStringFromDate() {
			return stringFromDate;
		}
		public void setStringFromDate(String stringFromDate) {
			this.stringFromDate = stringFromDate;
		}
		
		public int getVehicleFlag() {
			return vehicleFlag;
		}
		public void setVehicleFlag(int vehicleFlag) {
			this.vehicleFlag = vehicleFlag;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getRequestId() {
			return requestId;
		}
		public void setRequestId(int requestId) {
			this.requestId = requestId;
		}
	
		public int getReferenceId() {
			return referenceId;
		}
		public void setReferenceId(int referenceId) {
			this.referenceId = referenceId;
		}
		public int getVehicleDriverMapId() {
			return vehicleDriverMapId;
		}
		public void setVehicleDriverMapId(int vehicleDriverMapId) {
			this.vehicleDriverMapId = vehicleDriverMapId;
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
		public int getRequestType() {
			return requestType;
		}
		public void setRequestType(int requestType) {
			this.requestType = requestType;
		}
		public int getStatusFlag() {
			return statusFlag;
		}
		public void setStatusFlag(int statusFlag) {
			this.statusFlag = statusFlag;
		}
	
	   
}