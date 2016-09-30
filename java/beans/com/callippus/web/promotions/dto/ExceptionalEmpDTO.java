package com.callippus.web.promotions.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.YearTypeDTO;

public class ExceptionalEmpDTO extends CommonDTO {
	private YearTypeDTO yearDetails;
	private int yearID;
	private String sfID;
	private String description;

	public YearTypeDTO getYearDetails() {
		return yearDetails;
	}

	public void setYearDetails(YearTypeDTO yearDetails) {
		this.yearDetails = yearDetails;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
