package com.callippus.web.leave.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LeaveAmendmentDTO extends CommonDTO {
	private int requestId;
	private int amendmentId;
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getAmendmentId() {
		return amendmentId;
	}

	public void setAmendmentId(int amendmentId) {
		this.amendmentId = amendmentId;
	}
}
