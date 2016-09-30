package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LoanTypeDTO extends CommonDTO{

	private float maxLoanAmount;
	private int maxInstalment;
	
	
	public float getMaxLoanAmount() {
		return maxLoanAmount;
	}
	public void setMaxLoanAmount(float maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}
	public int getMaxInstalment() {
		return maxInstalment;
	}
	public void setMaxInstalment(int maxInstalment) {
		this.maxInstalment = maxInstalment;
	}
	
}
