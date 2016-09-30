package com.callippus.web.leave.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class ErpAvailableLeavesDTO extends CommonDTO{
	
	private int id;
	private String sfid;
	private String sfID;
	private int annualLeaves;
	private int sickLeaves;
	private int maternityLeaves;
	private int peternityLeaves;
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
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public int getAnnualLeaves() {
		return annualLeaves;
	}
	public void setAnnualLeaves(int annualLeaves) {
		this.annualLeaves = annualLeaves;
	}
	public int getSickLeaves() {
		return sickLeaves;
	}
	public void setSickLeaves(int sickLeaves) {
		this.sickLeaves = sickLeaves;
	}
	public int getMaternityLeaves() {
		return maternityLeaves;
	}
	public void setMaternityLeaves(int maternityLeaves) {
		this.maternityLeaves = maternityLeaves;
	}
	public int getPeternityLeaves() {
		return peternityLeaves;
	}
	public void setPeternityLeaves(int peternityLeaves) {
		this.peternityLeaves = peternityLeaves;
	}
	
	
	
}
