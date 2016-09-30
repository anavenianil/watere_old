package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.quarterType.QuarterTypeBean;

public class LicenceFeeChargesDTO extends CommonDTO {
	private String quartersType;
	private String gradePay;
	private String licenceFee;
	private String quarterSubType;
	private QuarterTypeBean quarterSubTypeDetails;
	private Date effDate;
	private int water;
	private int furn;
	private int elec;
	
	

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getFurn() {
		return furn;
	}

	public void setFurn(int furn) {
		this.furn = furn;
	}

	public int getElec() {
		return elec;
	}

	public void setElec(int elec) {
		this.elec = elec;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public String getQuartersType() {
		return quartersType;
	}

	public void setQuartersType(String quartersType) {
		this.quartersType = quartersType;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getLicenceFee() {
		return licenceFee;
	}

	public void setLicenceFee(String licenceFee) {
		this.licenceFee = licenceFee;
	}

	public String getQuarterSubType() {
		return quarterSubType;
	}

	public void setQuarterSubType(String quarterSubType) {
		this.quarterSubType = quarterSubType;
	}

	public QuarterTypeBean getQuarterSubTypeDetails() {
		return quarterSubTypeDetails;
	}

	public void setQuarterSubTypeDetails(QuarterTypeBean quarterSubTypeDetails) {
		this.quarterSubTypeDetails = quarterSubTypeDetails;
	}

}
