package com.callippus.web.beans.dto;

import com.callippus.web.tada.dto.ProgramDTO;

public class TadaProjectDirectorsDTO extends CommonDTO {

	private String sfID;
	private String projectName;
	private String projectDirName;
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

	public String getProjectDirName() {
		return projectDirName;
	}

	public void setProjectDirName(String projectDirName) {
		this.projectDirName = projectDirName;
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
