package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class PayAllwDetailsDTO extends CommonDTO{

	private int allwTypeId;
	private int amount;
	private Date effDate;
	private PayAllwTypeDTO confTypeDetails;
	
	
	public PayAllwTypeDTO getConfTypeDetails() {
		return confTypeDetails;
	}
	public void setConfTypeDetails(PayAllwTypeDTO confTypeDetails) {
		this.confTypeDetails = confTypeDetails;
	}
	public int getAllwTypeId() {
		return allwTypeId;
	}
	public void setAllwTypeId(int allwTypeId) {
		this.allwTypeId = allwTypeId;
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
