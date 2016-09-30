package com.callippus.web.beans.tutionFee;

public class TuitionFeeBillAndSanctionDTO {

	private int id;
	private int financialYearId;
	private int billOrSanctionNo;
	private int status;
	private String type;
	private int claimTypeId;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(int financialYearId) {
		this.financialYearId = financialYearId;
	}
	public int getBillOrSanctionNo() {
		return billOrSanctionNo;
	}
	public void setBillOrSanctionNo(int billOrSanctionNo) {
		this.billOrSanctionNo = billOrSanctionNo;
	}
	public int getClaimTypeId() {
		return claimTypeId;
	}
	public void setClaimTypeId(int claimTypeId) {
		this.claimTypeId = claimTypeId;
	}
	
	
	
	
	
	
}
