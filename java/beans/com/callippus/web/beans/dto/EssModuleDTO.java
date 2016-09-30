package com.callippus.web.beans.dto;

public class EssModuleDTO extends CommonDTO{
	private String maxSlNo;
	private int orderBy;
	
	
	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public String getMaxSlNo() {
		return maxSlNo;
	}

	public void setMaxSlNo(String maxSlNo) {
		this.maxSlNo = maxSlNo;
	}
	
	
}
