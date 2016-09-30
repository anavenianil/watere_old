package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class GPFRulesDTO extends CommonDTO {
	private int loanTypeID;
	private LoanTypeMasterDTO loanTypeDetails;
	private String purpose;
	private String rule;

	public LoanTypeMasterDTO getLoanTypeDetails() {
		return loanTypeDetails;
	}

	public void setLoanTypeDetails(LoanTypeMasterDTO loanTypeDetails) {
		this.loanTypeDetails = loanTypeDetails;
	}

	public int getLoanTypeID() {
		return loanTypeID;
	}

	public void setLoanTypeID(int loanTypeID) {
		this.loanTypeID = loanTypeID;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

}
