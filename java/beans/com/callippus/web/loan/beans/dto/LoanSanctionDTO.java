package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanSanctionDTO extends CommonDTO {
	//private List<LoanSanctionDetailsDTO> sanctionDetails;
	private String letterNo;
	private String sanctionDate;
	
	
	
	public String getLetterNo() {
		return letterNo;
	}
	public String getSanctionDate() {
		return sanctionDate;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public void setSanctionDate(String sanctionDate) {
		this.sanctionDate = sanctionDate;
	}
	

}
