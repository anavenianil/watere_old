package com.callippus.web.tada.dto;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;




public class TadaTdTxnDTO {
	
	private String requestId;
	private int id;
	private String sfID;
	private int status;
	private String requestType;
	private Date journeyDate;
	private String journeyDateOne;
	private String historyID;
	private String advanceFlag;
	private int advRequestId;
	private String cdaAmount;
	private String refRequestId;
	private String reimFlag;
	private String settFlag;
	private String strStatus;
	private int stayDuration;
	private String amendmentId;
	private Date arrivalDate;
	private String strArrivalDate;
	private LeaveRequestBean leaveRequestDetails;
	private KeyValueDTO leaveDetails;
	private List<KeyValueDTO> leaveDetailsList;
	
	public String getSettFlag() {
		return settFlag;
	}
	public void setSettFlag(String settFlag) {
		this.settFlag = settFlag;
	}
	public String getReimFlag() {
		return reimFlag;
	}
	public void setReimFlag(String reimFlag) {
		this.reimFlag = reimFlag;
	}
	public String getRefRequestId() {
		return refRequestId;
	}
	public void setRefRequestId(String refRequestId) {
		this.refRequestId = refRequestId;
	}
	public String getCdaAmount() {
		return cdaAmount;
	}
	public void setCdaAmount(String cdaAmount) {
		this.cdaAmount = cdaAmount;
	}
	public int getAdvRequestId() {
		return advRequestId;
	}
	public void setAdvRequestId(int advRequestId) {
		this.advRequestId = advRequestId;
	}
	public String getAdvanceFlag() {
		return advanceFlag;
	}
	public void setAdvanceFlag(String advanceFlag) {
		this.advanceFlag = advanceFlag;
	}
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public Date getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}
	public String getJourneyDateOne() {
		return journeyDateOne;
	}
	public void setJourneyDateOne(String journeyDateOne) {
		this.journeyDateOne = journeyDateOne;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public int getStayDuration() {
		return stayDuration;
	}
	public void setStayDuration(int stayDuration) {
		this.stayDuration = stayDuration;
	}
	public String getAmendmentId() {
		return amendmentId;
	}
	public void setAmendmentId(String amendmentId) {
		this.amendmentId = amendmentId;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getStrArrivalDate() {
		return strArrivalDate;
	}
	public void setStrArrivalDate(String strArrivalDate) {
		this.strArrivalDate = strArrivalDate;
	}
	public LeaveRequestBean getLeaveRequestDetails() {
		return leaveRequestDetails;
	}
	public void setLeaveRequestDetails(LeaveRequestBean leaveRequestDetails) {
		this.leaveRequestDetails = leaveRequestDetails;
	}
	public KeyValueDTO getLeaveDetails() {
		return leaveDetails;
	}
	public void setLeaveDetails(KeyValueDTO leaveDetails) {
		this.leaveDetails = leaveDetails;
	}
	public List<KeyValueDTO> getLeaveDetailsList() {
		return leaveDetailsList;
	}
	public void setLeaveDetailsList(List<KeyValueDTO> leaveDetailsList) {
		this.leaveDetailsList = leaveDetailsList;
	}
	
	
	

}
