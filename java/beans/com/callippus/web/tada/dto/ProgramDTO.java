package com.callippus.web.tada.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class ProgramDTO extends CommonDTO{
	private String programName;
	private int programCode;
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public int getProgramCode() {
		return programCode;
	}
	public void setProgramCode(int programCode) {
		this.programCode = programCode;
	}
	

}
