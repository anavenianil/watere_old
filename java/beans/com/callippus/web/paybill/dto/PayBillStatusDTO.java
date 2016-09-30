package com.callippus.web.paybill.dto;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.KeyValueDTO;

public class PayBillStatusDTO extends CommonDTO{
	
	public int runID;
	public String runMonth;
	public int payStatus;
	public int runStatus;
	public int manualStatus;
	public int userStatus;
	public int regenerateStatus;
	public int runSubId;
	public String count;
	public String generatedCount;
	public List<KeyValueDTO> sfidList;
	//nagendra.v
	public String autorunsignal=null;
	
	
	
	public String getAutorunsignal() {
		return autorunsignal;
	}
	public void setAutorunsignal(String autorunsignal) {
		this.autorunsignal = autorunsignal;
	}
	public String getGeneratedCount() {
		return generatedCount;
	}
	public void setGeneratedCount(String generatedCount) {
		this.generatedCount = generatedCount;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getRunSubId() {
		return runSubId;
	}
	public void setRunSubId(int runSubId) {
		this.runSubId = runSubId;
	}
	public int getRegenerateStatus() {
		return regenerateStatus;
	}
	public void setRegenerateStatus(int regenerateStatus) {
		this.regenerateStatus = regenerateStatus;
	}
	public int getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}
	public int getManualStatus() {
		return manualStatus;
	}
	public void setManualStatus(int manualStatus) {
		this.manualStatus = manualStatus;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	public int getRunID() {
		return runID;
	}
	public void setRunID(int runID) {
		this.runID = runID;
	}
	public String getRunMonth() {
		return runMonth;
	}
	public void setRunMonth(String runMonth) {
		this.runMonth = runMonth;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public List<KeyValueDTO> getSfidList() {
		return sfidList;
	}
	public void setSfidList(List<KeyValueDTO> sfidList) {
		this.sfidList = sfidList;
	}
	
}
