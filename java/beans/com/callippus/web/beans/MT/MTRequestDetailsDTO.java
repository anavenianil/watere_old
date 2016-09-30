package com.callippus.web.beans.MT;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.StatusMasterDTO;

public class MTRequestDetailsDTO {
	
		private int id;
	    private String requestID;
	    private String sfid;
	    private String requestTypeID;
	    private String requestType;
	    private int vehicleRequiredFlag;
	    private String purposeOfVisit;
	    private String requestedBy;
	    private Date requestedDate;
	    private String ipAddress;
	    private int status;
	   
	    
	    private String message;
		private List<MTJourneyDetailsDTO> mtJourneyDetails;
	    private String requesterName;
	    private StatusMasterDTO statusDetails;
	    
	  
		public StatusMasterDTO getStatusDetails() {
			return statusDetails;
		}
		public void setStatusDetails(StatusMasterDTO statusDetails) {
			this.statusDetails = statusDetails;
		}
		public String getRequesterName() {
			return requesterName;
		}
		public void setRequesterName(String requesterName) {
			this.requesterName = requesterName;
		}
		public List<MTJourneyDetailsDTO> getMtJourneyDetails() {
			return mtJourneyDetails;
		}
		public void setMtJourneyDetails(List<MTJourneyDetailsDTO> mtJourneyDetails) {
			this.mtJourneyDetails = mtJourneyDetails;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getRequestID() {
			return requestID;
		}
		public void setRequestID(String requestID) {
			this.requestID = requestID;
		}
		public String getSfid() {
			return sfid;
		}
		public void setSfid(String sfid) {
			this.sfid = sfid;
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
		
		public int getVehicleRequiredFlag() {
			return vehicleRequiredFlag;
		}
		public void setVehicleRequiredFlag(int vehicleRequiredFlag) {
			this.vehicleRequiredFlag = vehicleRequiredFlag;
		}
		public String getPurposeOfVisit() {
			return purposeOfVisit;
		}
		public void setPurposeOfVisit(String purposeOfVisit) {
			this.purposeOfVisit = purposeOfVisit;
		}
		public String getRequestedBy() {
			return requestedBy;
		}
		public void setRequestedBy(String requestedBy) {
			this.requestedBy = requestedBy;
		}
		
		public Date getRequestedDate() {
			return requestedDate;
		}
		public void setRequestedDate(Date requestedDate) {
			this.requestedDate = requestedDate;
		}
		public String getIpAddress() {
			return ipAddress;
		}
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	  
	    
	    
}