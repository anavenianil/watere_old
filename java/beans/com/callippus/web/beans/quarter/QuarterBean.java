package com.callippus.web.beans.quarter;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

public class QuarterBean extends CommonDTO {
	private String param;
	private String type;
	private String sfid;
	private String result;
	private List<QuarterTypeBean> quarterSubTypeList;
	private List<PayQuarterManagementDTO> quarterDetails;
	private String quarterSubType;
	private String quarterNo;
	private Date occupiedDate;
	private String nodeID;
	private String logInSfID;
	private String redirect;
	private PayQuarterManagementDTO userQuarterDetails;
	private List<QuarterRequestBean> emuQuarterDetails;
	private String quarterStatus;
	
	
	public String getQuarterStatus() {
		return quarterStatus;
	}

	public void setQuarterStatus(String quarterStatus) {
		this.quarterStatus = quarterStatus;
	}

	public List<QuarterRequestBean> getEmuQuarterDetails() {
		return emuQuarterDetails;
	}

	public void setEmuQuarterDetails(List<QuarterRequestBean> emuQuarterDetails) {
		this.emuQuarterDetails = emuQuarterDetails;
	}

	public PayQuarterManagementDTO getUserQuarterDetails() {
		return userQuarterDetails;
	}

	public void setUserQuarterDetails(PayQuarterManagementDTO userQuarterDetails) {
		this.userQuarterDetails = userQuarterDetails;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getLogInSfID() {
		return logInSfID;
	}

	public void setLogInSfID(String logInSfID) {
		this.logInSfID = logInSfID;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getQuarterSubType() {
		return quarterSubType;
	}

	public void setQuarterSubType(String quarterSubType) {
		this.quarterSubType = quarterSubType;
	}

	public String getQuarterNo() {
		return quarterNo;
	}

	public void setQuarterNo(String quarterNo) {
		this.quarterNo = quarterNo;
	}

	public Date getOccupiedDate() {
		return occupiedDate;
	}

	public void setOccupiedDate(Date occupiedDate) {
		this.occupiedDate = occupiedDate;
	}

	public List<QuarterTypeBean> getQuarterSubTypeList() {
		return quarterSubTypeList;
	}

	public void setQuarterSubTypeList(List<QuarterTypeBean> quarterSubTypeList) {
		this.quarterSubTypeList = quarterSubTypeList;
	}

	public List<PayQuarterManagementDTO> getQuarterDetails() {
		return quarterDetails;
	}

	public void setQuarterDetails(List<PayQuarterManagementDTO> quarterDetails) {
		this.quarterDetails = quarterDetails;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
