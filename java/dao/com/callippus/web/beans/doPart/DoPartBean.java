package com.callippus.web.beans.doPart;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.TypeDetailsDTO;
import com.callippus.web.beans.dto.YearTypeDTO;

public class DoPartBean {
private String doPartNo;
private Date doPartDate;
private String preDoPartNo;
private Date preDoPartDate;
private String gazType;
private int id;
private String createdBy;
private String status;
private String description;
private String doPartType;
private Date creationDate;
private List<DoPartDTO>  doPartList;
private String param;
private String type;
private String sfid;
private String message;
private String year;
private List<YearTypeDTO> yearsList;
private List<TypeDetailsDTO> typeList;
private String verifyFlag;
private String distribution;


public String getDistribution() {
	return distribution;
}
public void setDistribution(String distribution) {
	this.distribution = distribution;
}
public List<TypeDetailsDTO> getTypeList() {
	return typeList;
}
public void setTypeList(List<TypeDetailsDTO> typeList) {
	this.typeList = typeList;
}
public String getVerifyFlag() {
	return verifyFlag;
}
public void setVerifyFlag(String verifyFlag) {
	this.verifyFlag = verifyFlag;
}
public List<YearTypeDTO> getYearsList() {
	return yearsList;
}
public void setYearsList(List<YearTypeDTO> yearsList) {
	this.yearsList = yearsList;
}


public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getSfid() {
	return sfid;
}
public void setSfid(String sfid) {
	this.sfid = sfid;
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
public List<DoPartDTO> getDoPartList() {
	return doPartList;
}
public void setDoPartList(List<DoPartDTO> doPartList) {
	this.doPartList = doPartList;
}
public String getDoPartNo() {
	return doPartNo;
}
public void setDoPartNo(String doPartNo) {
	this.doPartNo = doPartNo;
}
public Date getDoPartDate() {
	return doPartDate;
}
public void setDoPartDate(Date doPartDate) {
	this.doPartDate = doPartDate;
}
public String getPreDoPartNo() {
	return preDoPartNo;
}
public void setPreDoPartNo(String preDoPartNo) {
	this.preDoPartNo = preDoPartNo;
}
public Date getPreDoPartDate() {
	return preDoPartDate;
}
public void setPreDoPartDate(Date preDoPartDate) {
	this.preDoPartDate = preDoPartDate;
}
public String getGazType() {
	return gazType;
}
public void setGazType(String gazType) {
	this.gazType = gazType;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Date getCreationDate() {
	return creationDate;
}
public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getDoPartType() {
	return doPartType;
}
public void setDoPartType(String doPartType) {
	this.doPartType = doPartType;
}

}
