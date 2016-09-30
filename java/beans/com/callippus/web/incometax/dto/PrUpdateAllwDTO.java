package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;

public class PrUpdateAllwDTO extends CommonDTO{
  private int gradePay;
  private int designationId;
  private PayScaleDesignationDTO designationDetails;
  private int amount;
public int getGradePay() {
	return gradePay;
}
public void setGradePay(int gradePay) {
	this.gradePay = gradePay;
}
public int getDesignationId() {
	return designationId;
}
public void setDesignationId(int designationId) {
	this.designationId = designationId;
}
public PayScaleDesignationDTO getDesignationDetails() {
	return designationDetails;
}
public void setDesignationDetails(PayScaleDesignationDTO designationDetails) {
	this.designationDetails = designationDetails;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
  
  
}
