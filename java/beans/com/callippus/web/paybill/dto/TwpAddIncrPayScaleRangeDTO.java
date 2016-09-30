package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class TwpAddIncrPayScaleRangeDTO extends CommonDTO{

	private int designationId;
	private int amount;
	private Date effDate;
	private DesignationDTO designationDetails;
	
	
	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}
	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getEffDate() {
		return effDate;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
	
	
}
