package com.callippus.web.ltc.dto;

public class LTCWaterFamilyDTO {
	
	private String familyMemberId;
	private String sfID;
	private String requestId;
	private String ltcYear;
	private int id;
	
	
	
	public String getFamilyMemberId() {
		return familyMemberId;
	}
	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getLtcYear() {
		return ltcYear;
	}
	public void setLtcYear(String ltcYear) {
		this.ltcYear = ltcYear;
	}
	
	

}
