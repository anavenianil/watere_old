package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class TaEntitleExemptionDTO extends CommonDTO {
	private String sfID;
	private String projectName;
	private int entitleTypeId;
	private String remarks;
	private TravelTypeDTO entitleTypeDetails;
	private int programCode;
	private String programName;
	private ProgramDTO programDetails;
	
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getEntitleTypeId() {
		return entitleTypeId;
	}
	public void setEntitleTypeId(int entitleTypeId) {
		this.entitleTypeId = entitleTypeId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public TravelTypeDTO getEntitleTypeDetails() {
		return entitleTypeDetails;
	}
	public void setEntitleTypeDetails(TravelTypeDTO entitleTypeDetails) {
		this.entitleTypeDetails = entitleTypeDetails;
	}
	public int getProgramCode() {
		return programCode;
	}
	public void setProgramCode(int programCode) {
		this.programCode = programCode;
	}
	public ProgramDTO getProgramDetails() {
		return programDetails;
	}
	public void setProgramDetails(ProgramDTO programDetails) {
		this.programDetails = programDetails;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	

}
