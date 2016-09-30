package com.callippus.web.paybill.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class PayBillEmpCategoryDTO extends CommonDTO {

	private String effDate;
	private int payOrderNo;
	private String payBillType;

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public int getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(int payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getPayBillType() {
		return payBillType;
	}

	public void setPayBillType(String payBillType) {
		this.payBillType = payBillType;
	}
	
}
