package com.callippus.web.beans.dto;

public class DistrictTypeDTO extends CommonDTO {

	private String stateId;
	private String state;
	private StateTypeDTO stateDetails;

	public StateTypeDTO getStateDetails() {
		return stateDetails;
	}

	public void setStateDetails(StateTypeDTO stateDetails) {
		this.stateDetails = stateDetails;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
}
