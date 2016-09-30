package com.callippus.web.leave.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class PastLeavesDTO extends CommonDTO {
	private int requestId;
	private String sfid;

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
}
