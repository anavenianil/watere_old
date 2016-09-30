package com.callippus.web.tada.dto;

import java.util.List;

public class TadaEmpDetailsDTO {
	private String name;
	private String designation;
	private String sfid;
	private long phnNo;
	//private List<TadaEmpDetailsDTO> empDetails;
	
	
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public long getPhnNo() {
		return phnNo;
	}
	public void setPhnNo(long phnNo) {
		this.phnNo = phnNo;
	}
	
	
	
	

}
