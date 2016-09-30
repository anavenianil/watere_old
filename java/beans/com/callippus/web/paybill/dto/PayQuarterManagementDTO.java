package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.quarterType.QuarterTypeBean;

public class PayQuarterManagementDTO extends CommonDTO {
	private String sfid;
	private String quarterNo;
	private String quartersType;
	private QuarterTypeBean quarterTypeDetails;
	private Date occupiedDate;
	private String vacatedDt;
	private String quarter;
	private int quarterSubTypeID;
	private String mainQuarter;
	private Date vacationDate;
	
	
	
	
	public Date getVacationDate() {
		return vacationDate;
	}

	public void setVacationDate(Date vacationDate) {
		this.vacationDate = vacationDate;
	}

	public String getVacatedDt() {
		return vacatedDt;
	}

	public void setVacatedDt(String vacatedDt) {
		this.vacatedDt = vacatedDt;
	}

	public String getMainQuarter() {
		return mainQuarter;
	}

	public void setMainQuarter(String mainQuarter) {
		this.mainQuarter = mainQuarter;
	}

	public int getQuarterSubTypeID() {
		return quarterSubTypeID;
	}

	public void setQuarterSubTypeID(int quarterSubTypeID) {
		this.quarterSubTypeID = quarterSubTypeID;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public Date getOccupiedDate() {
		return occupiedDate;
	}

	public void setOccupiedDate(Date occupiedDate) {
		this.occupiedDate = occupiedDate;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getQuarterNo() {
		return quarterNo;
	}

	public void setQuarterNo(String quarterNo) {
		this.quarterNo = quarterNo;
	}

	public String getQuartersType() {
		return quartersType;
	}

	public void setQuartersType(String quartersType) {
		this.quartersType = quartersType;
	}

	public QuarterTypeBean getQuarterTypeDetails() {
		return quarterTypeDetails;
	}

	public void setQuarterTypeDetails(QuarterTypeBean quarterTypeDetails) {
		this.quarterTypeDetails = quarterTypeDetails;
	}
}
