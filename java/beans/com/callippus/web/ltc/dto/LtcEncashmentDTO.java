package com.callippus.web.ltc.dto;

public class LtcEncashmentDTO {
	private int id;
	private String sfid;	
	private int encashmentdays;
	private int leaveTypeId;
	private int yearsSpellcount;
	private int ltcEnacashmentDays;
	private int serviceSpellCount;
	private int leavewithoutmc;
	private int status ;
	private String message;
	private String param;
	private String type;
	private String back;
	
	
	
	public int getServiceSpellCount() {
		return serviceSpellCount;
	}
	public void setServiceSpellCount(int serviceSpellCount) {
		this.serviceSpellCount = serviceSpellCount;
	}
	public int getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public int getYearsSpellcount() {
		return yearsSpellcount;
	}
	public void setYearsSpellcount(int yearsSpellcount) {
		this.yearsSpellcount = yearsSpellcount;
	}
	public int getLtcEnacashmentDays() {
		return ltcEnacashmentDays;
	}
	public void setLtcEnacashmentDays(int ltcEnacashmentDays) {
		this.ltcEnacashmentDays = ltcEnacashmentDays;
	}
	
	public int getLeavewithoutmc() {
		return leavewithoutmc;
	}
	public void setLeavewithoutmc(int leavewithoutmc) {
		this.leavewithoutmc = leavewithoutmc;
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
	
	public int getEncashmentdays() {
		return encashmentdays;
	}
	public void setEncashmentdays(int encashmentdays) {
		this.encashmentdays = encashmentdays;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	
	
	
}
