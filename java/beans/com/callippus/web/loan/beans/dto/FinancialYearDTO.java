package com.callippus.web.loan.beans.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class FinancialYearDTO extends CommonDTO {
	private Date fromDate;
	private Date toDate;
	private String financialYear;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getFinancialYear() {
		return financialYear;
	}

}
