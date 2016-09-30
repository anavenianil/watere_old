package com.callippus.web.paybill.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class PayScaleDesignationDTO extends CommonDTO {
	private DesignationDTO designationDetails;
	private PaybandDTO paybandDetails;
	private String designation;
	private String payband;
	private String gradePay;
	

	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}

	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}

	public PaybandDTO getPaybandDetails() {
		return paybandDetails;
	}

	public void setPaybandDetails(PaybandDTO paybandDetails) {
		this.paybandDetails = paybandDetails;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPayband() {
		return payband;
	}

	public void setPayband(String payband) {
		this.payband = payband;
	}

	
	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	
}
