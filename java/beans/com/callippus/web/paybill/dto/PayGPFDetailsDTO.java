package com.callippus.web.paybill.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class PayGPFDetailsDTO extends CommonDTO{
	
	public String sfid;
	public int presentGpf;
	public int previousGpf;
	public String type;
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public int getPresentGpf() {
		return presentGpf;
	}
	public void setPresentGpf(int presentGpf) {
		this.presentGpf = presentGpf;
	}
	public int getPreviousGpf() {
		return previousGpf;
	}
	public void setPreviousGpf(int previousGpf) {
		this.previousGpf = previousGpf;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
