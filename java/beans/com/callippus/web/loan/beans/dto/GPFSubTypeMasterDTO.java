package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class GPFSubTypeMasterDTO extends CommonDTO {

	private String rule;
	private int gpfType;
	private String purpose;

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public int getGpfType() {
		return gpfType;
	}

	public void setGpfType(int gpfType) {
		this.gpfType = gpfType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
