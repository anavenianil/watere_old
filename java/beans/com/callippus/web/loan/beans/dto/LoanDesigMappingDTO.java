package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class LoanDesigMappingDTO extends CommonDTO {
	private int loanAmountID;
	private int designationID;
	private DesignationDTO designationDetails;
	private LoanAmountDetailsDTO loanAmountDetails;

	public int getLoanAmountID() {
		return loanAmountID;
	}

	public void setLoanAmountID(int loanAmountID) {
		this.loanAmountID = loanAmountID;
	}

	public int getDesignationID() {
		return designationID;
	}

	public void setDesignationID(int designationID) {
		this.designationID = designationID;
	}

	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}

	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}

	public LoanAmountDetailsDTO getLoanAmountDetails() {
		return loanAmountDetails;
	}

	public void setLoanAmountDetails(LoanAmountDetailsDTO loanAmountDetails) {
		this.loanAmountDetails = loanAmountDetails;
	}

}