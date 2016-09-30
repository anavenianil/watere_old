package com.callippus.web.beans.dto;

public class EmploymentDTO extends CommonDTO {
    private String sfid;
    private String ename;
    
    
    public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }
}
