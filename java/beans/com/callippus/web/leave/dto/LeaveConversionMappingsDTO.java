package com.callippus.web.leave.dto;

public class LeaveConversionMappingsDTO {
	private int id;
	private LeaveTypeDTO convertFromDetails;
	private LeaveTypeDTO convertToDetails;
	private String noOfDays;
	private int status;
	private String conversionTo;
	private String conversionFrom;
	
	
	public String getConversionTo() {
		return conversionTo;
	}

	public void setConversionTo(String conversionTo) {
		this.conversionTo = conversionTo;
	}

	public String getConversionFrom() {
		return conversionFrom;
	}

	public void setConversionFrom(String conversionFrom) {
		this.conversionFrom = conversionFrom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LeaveTypeDTO getConvertFromDetails() {
		return convertFromDetails;
	}

	public void setConvertFromDetails(LeaveTypeDTO convertFromDetails) {
		this.convertFromDetails = convertFromDetails;
	}

	public LeaveTypeDTO getConvertToDetails() {
		return convertToDetails;
	}

	public void setConvertToDetails(LeaveTypeDTO convertToDetails) {
		this.convertToDetails = convertToDetails;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
