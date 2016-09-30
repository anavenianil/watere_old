package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanTypeMasterDTO extends CommonDTO {
	private String loanName;
	private String loanType;

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
}