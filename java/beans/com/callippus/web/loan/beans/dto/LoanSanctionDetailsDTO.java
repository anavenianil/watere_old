package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanSanctionDetailsDTO extends CommonDTO{
	//private LoanRequestBean requestDetails;
	//private Integer sanctionId;
	
	private Integer requestId;
	private String sAmount;
	private LoanSanctionDTO sanctionDetails;
	
	
	
	public LoanSanctionDTO getSanctionDetails() {
		return sanctionDetails;
	}
	public void setSanctionDetails(LoanSanctionDTO sanctionDetails) {
		this.sanctionDetails = sanctionDetails;
	}
	
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	
	public void setsAmount(String sAmount) {
		this.sAmount = sAmount;
	}
	
	
	public String getsAmount() {
		return sAmount;
	}

}
