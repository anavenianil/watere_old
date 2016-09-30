package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class ErpLoanPurposeDTO extends CommonDTO {
	private String loanType;
	
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
}