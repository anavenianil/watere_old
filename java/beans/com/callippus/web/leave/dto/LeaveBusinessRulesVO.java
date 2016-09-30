package com.callippus.web.leave.dto;

import java.io.Serializable;

public class LeaveBusinessRulesVO implements Serializable {
	private int id;
	private String requestType;
	private String leaveCode1;
	private String leaveType1;
	private String leaveCode2;
	private String leaveType2;
	private String leaveCode3;
	private String leaveType3;
	private String leaveCode4;
	private String leaveType4;
	private String leaveCode5;
	private String leaveType5;
	private String condition;
	private Integer resultValue;
	private String duration;
	private String remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getLeaveCode1() {
		return leaveCode1;
	}

	public void setLeaveCode1(String leaveCode1) {
		this.leaveCode1 = leaveCode1;
	}

	public String getLeaveType1() {
		return leaveType1;
	}

	public void setLeaveType1(String leaveType1) {
		this.leaveType1 = leaveType1;
	}

	public String getLeaveCode2() {
		return leaveCode2;
	}

	public void setLeaveCode2(String leaveCode2) {
		this.leaveCode2 = leaveCode2;
	}

	public String getLeaveType2() {
		return leaveType2;
	}

	public void setLeaveType2(String leaveType2) {
		this.leaveType2 = leaveType2;
	}

	public String getLeaveCode3() {
		return leaveCode3;
	}

	public void setLeaveCode3(String leaveCode3) {
		this.leaveCode3 = leaveCode3;
	}

	public String getLeaveType3() {
		return leaveType3;
	}

	public void setLeaveType3(String leaveType3) {
		this.leaveType3 = leaveType3;
	}

	public String getLeaveCode4() {
		return leaveCode4;
	}

	public void setLeaveCode4(String leaveCode4) {
		this.leaveCode4 = leaveCode4;
	}

	public String getLeaveType4() {
		return leaveType4;
	}

	public void setLeaveType4(String leaveType4) {
		this.leaveType4 = leaveType4;
	}

	public String getLeaveCode5() {
		return leaveCode5;
	}

	public void setLeaveCode5(String leaveCode5) {
		this.leaveCode5 = leaveCode5;
	}

	public String getLeaveType5() {
		return leaveType5;
	}

	public void setLeaveType5(String leaveType5) {
		this.leaveType5 = leaveType5;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getResultValue() {
		return resultValue;
	}

	public void setResultValue(Integer resultValue) {
		this.resultValue = resultValue;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
