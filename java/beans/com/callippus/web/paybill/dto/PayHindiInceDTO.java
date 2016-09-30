package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class PayHindiInceDTO extends CommonDTO{
    private String sfid;
	private Date startDate;
	private Date endDate;
	private int totInst;
	private int presentInst;
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getTotInst() {
		return totInst;
	}
	public void setTotInst(int totInst) {
		this.totInst = totInst;
	}
	public int getPresentInst() {
		return presentInst;
	}
	public void setPresentInst(int presentInst) {
		this.presentInst = presentInst;
	}
	
}
