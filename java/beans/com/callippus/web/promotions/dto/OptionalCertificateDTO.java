package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class OptionalCertificateDTO extends CommonDTO {
	private AssessmentDetailsDTO assessmentDetails;
	private int assessmentID;
	private String ipAddress;
	private Date incrementDate;
	private float scaleOfPay;
	private String requestedBy;
	private Date requestedDate;
	private Integer payUpdate;

	

	
	public Integer getPayUpdate() {
		return payUpdate;
	}

	public void setPayUpdate(Integer payUpdate) {
		this.payUpdate = payUpdate;
	}

	public AssessmentDetailsDTO getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(AssessmentDetailsDTO assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	public int getAssessmentID() {
		return assessmentID;
	}

	public void setAssessmentID(int assessmentID) {
		this.assessmentID = assessmentID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getIncrementDate() {
		return incrementDate;
	}

	public void setIncrementDate(Date incrementDate) {
		this.incrementDate = incrementDate;
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

	public float getScaleOfPay() {
		return scaleOfPay;
	}

	public void setScaleOfPay(float scaleOfPay) {
		this.scaleOfPay = scaleOfPay;
	}

}
