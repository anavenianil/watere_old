package com.callippus.web.beans.requests;

import java.util.Date;

import com.callippus.web.beans.dto.AlertMasterDTO;
import com.callippus.web.beans.dto.StatusMasterDTO;
import com.callippus.web.hrdg.training.beans.dto.TrainingCirculationDTO;
import com.callippus.web.promotions.dto.VenueDetailsDTO;

public class AlertMessageDTO {
	
	private Integer id;
	private String alertMessage;
	private String response;
	private String assignedFrom;
	private Date assignedDate;
	private String assignedIpAddress;
	private String assignedTo;
	private Date actionedDate;
	private String actionedIpAddress;
	private Integer referenceID;
	private Integer status;
	private Integer alertID;
	private Integer venueID;
	private AlertMasterDTO alertDetails;
	private StatusMasterDTO statusDetails;
	private VenueDetailsDTO venueDetails;
	private String labRepresentative;
	private String empName;
	
	

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getLabRepresentative() {
		return labRepresentative;
	}

	public void setLabRepresentative(String labRepresentative) {
		this.labRepresentative = labRepresentative;
	}

	public StatusMasterDTO getStatusDetails() {
		return statusDetails;
	}

	public void setStatusDetails(StatusMasterDTO statusDetails) {
		this.statusDetails = statusDetails;
	}

	public AlertMasterDTO getAlertDetails() {
		return alertDetails;
	}

	public void setAlertDetails(AlertMasterDTO alertDetails) {
		this.alertDetails = alertDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getAssignedFrom() {
		return assignedFrom;
	}

	public void setAssignedFrom(String assignedFrom) {
		this.assignedFrom = assignedFrom;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getAssignedIpAddress() {
		return assignedIpAddress;
	}

	public void setAssignedIpAddress(String assignedIpAddress) {
		this.assignedIpAddress = assignedIpAddress;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Date getActionedDate() {
		return actionedDate;
	}

	public void setActionedDate(Date actionedDate) {
		this.actionedDate = actionedDate;
	}

	public String getActionedIpAddress() {
		return actionedIpAddress;
	}

	public void setActionedIpAddress(String actionedIpAddress) {
		this.actionedIpAddress = actionedIpAddress;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public Integer getAlertID() {
		return alertID;
	}

	public void setAlertID(Integer alertID) {
		this.alertID = alertID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public VenueDetailsDTO getVenueDetails() {
		return venueDetails;
	}

	public void setVenueDetails(VenueDetailsDTO venueDetails) {
		this.venueDetails = venueDetails;
	}

	public int getVenueID() {
		return venueID;
	}

	public void setVenueID(int venueID) {
		this.venueID = venueID;
	}


}
