package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanFestivalMasterDTO extends CommonDTO {
	public String festivalName;
	
	public String festivalDate;
	
	public String festivalLoanStartDate;
	public String festivalLoanEndDate;

	public String getFestivalName() {
		return festivalName;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}
	public String getFestivalDate() {
		return festivalDate;
	}

	public void setFestivalDate(String festivalDate) {
		this.festivalDate = festivalDate;
	}

	public String getFestivalLoanStartDate() {
		return festivalLoanStartDate;
	}

	public void setFestivalLoanStartDate(String festivalLoanStartDate) {
		this.festivalLoanStartDate = festivalLoanStartDate;
	}

	public String getFestivalLoanEndDate() {
		return festivalLoanEndDate;
	}

	public void setFestivalLoanEndDate(String festivalLoanEndDate) {
		this.festivalLoanEndDate = festivalLoanEndDate;
	}

	
}