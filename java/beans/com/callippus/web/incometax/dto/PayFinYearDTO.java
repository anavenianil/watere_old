package com.callippus.web.incometax.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class PayFinYearDTO extends CommonDTO{
	 private String fyToFrom;
	 private int fyFrom;
	 private int fyTo;
	 
	public String getFyToFrom() {
		return fyToFrom;
	}
	public void setFyToFrom(String fyToFrom) {
		this.fyToFrom = fyToFrom;
	}
	public int getFyFrom() {
		return fyFrom;
	}
	public void setFyFrom(int fyFrom) {
		this.fyFrom = fyFrom;
	}
	public int getFyTo() {
		return fyTo;
	}
	public void setFyTo(int fyTo) {
		this.fyTo = fyTo;
	}
	 
}
