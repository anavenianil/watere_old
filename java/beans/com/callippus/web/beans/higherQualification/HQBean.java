package com.callippus.web.beans.higherQualification;

import java.util.Date;

import com.callippus.web.beans.higherQualification.dto.HQDetailsDTO;
import com.callippus.web.beans.higherQualification.dto.HQSanctionDTO;

public class HQBean {
private String sfID;
private String course;
private String dischargeOfDuties;
private String labWork;
private String param;
private String type;
private String message;
private Date fromDate;
private Date toDate;
private String religionFlag;
private int status;
private HQDetailsDTO hqDetailsDTO;
private HQSanctionDTO hqSanctionDTO;
private String result;


public String getResult() {
	return result;
}
public void setResult(String result) {
	this.result = result;
}
public HQSanctionDTO getHqSanctionDTO() {
	return hqSanctionDTO;
}
public void setHqSanctionDTO(HQSanctionDTO hqSanctionDTO) {
	this.hqSanctionDTO = hqSanctionDTO;
}
public HQDetailsDTO getHqDetailsDTO() {
	return hqDetailsDTO;
}
public void setHqDetailsDTO(HQDetailsDTO hqDetailsDTO) {
	this.hqDetailsDTO = hqDetailsDTO;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getReligionFlag() {
	return religionFlag;
}
public void setReligionFlag(String religionFlag) {
	this.religionFlag = religionFlag;
}
public Date getFromDate() {
	return fromDate;
}
public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
}
public Date getToDate() {
	return toDate;
}
public void setToDate(Date toDate) {
	this.toDate = toDate;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
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
public String getCourse() {
	return course;
}
public void setCourse(String course) {
	this.course = course;
}

public String getDischargeOfDuties() {
	return dischargeOfDuties;
}
public void setDischargeOfDuties(String dischargeOfDuties) {
	this.dischargeOfDuties = dischargeOfDuties;
}
public String getLabWork() {
	return labWork;
}
public void setLabWork(String labWork) {
	this.labWork = labWork;
}
public String getSfID() {
	return sfID;
}
public void setSfID(String sfID) {
	this.sfID = sfID;
}


}
