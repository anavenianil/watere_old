package com.callippus.web.hindi.dto;



import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class HindiIncentiveDetailsDTO extends CommonDTO{

	private int id;
	private String sfid;
	private Date fromDate;
	private Date toDate;
	private int noOfInst;
	private float totalAmount;
	private int status;
	private int presentInst;
	private int outStandingAmount;
	private String sfidSearch;
	private float cashAwardAmount; 
	private String loginSfid;
	
	
	public String getLoginSfid() {
		return loginSfid;
	}
	public void setLoginSfid(String loginSfid) {
		this.loginSfid = loginSfid;
	}
	public float getCashAwardAmount() {
		return cashAwardAmount;
	}
	public void setCashAwardAmount(float cashAwardAmount) {
		this.cashAwardAmount = cashAwardAmount;
	}
	public String getSfidSearch() {
		return sfidSearch;
	}
	public void setSfidSearch(String sfidSearch) {
		this.sfidSearch = sfidSearch;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
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
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	
	
	public int getNoOfInst() {
		return noOfInst;
	}
	public void setNoOfInst(int noOfInst) {
		this.noOfInst = noOfInst;
	}
	
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getPresentInst() {
		return presentInst;
	}
	public void setPresentInst(int presentInst) {
		this.presentInst = presentInst;
	}
	public int getOutStandingAmount() {
		return outStandingAmount;
	}
	public void setOutStandingAmount(int outStandingAmount) {
		this.outStandingAmount = outStandingAmount;
	}
	
	
	
}
