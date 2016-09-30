package com.callippus.web.loan.beans.dto;

import java.util.Date;
import com.callippus.web.beans.dto.CommonDTO;

public class LoanHBAInterestGridDTO extends CommonDTO {

	public float lowerAmountLimit;
	public float upperAmountLimit;
	public float interestRate;
	public Date applicableYear;

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public Date getApplicableYear() {
		return applicableYear;
	}

	public void setApplicableYear(Date applicableYear) {
		this.applicableYear = applicableYear;
	}

	public float getLowerAmountLimit() {
		return lowerAmountLimit;
	}

	public void setLowerAmountLimit(float lowerAmountLimit) {
		this.lowerAmountLimit = lowerAmountLimit;
	}

	public float getUpperAmountLimit() {
		return upperAmountLimit;
	}

	public void setUpperAmountLimit(float upperAmountLimit) {
		this.upperAmountLimit = upperAmountLimit;
	}

	

}
