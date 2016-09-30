package com.callippus.web.promotions.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class DesignationExperienceDTO extends CommonDTO{
private String sfid;
private int designationID;
private Date startDate;
private int noOfAttempts;
private DesignationDTO designationName;
private String empName;
public String getSfid() {
	return sfid;
}
public void setSfid(String sfid) {
	this.sfid = sfid;
}
public int getDesignationID() {
	return designationID;
}
public void setDesignationID(int designationID) {
	this.designationID = designationID;
}
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public int getNoOfAttempts() {
	return noOfAttempts;
}
public void setNoOfAttempts(int noOfAttempts) {
	this.noOfAttempts = noOfAttempts;
}
public DesignationDTO getDesignationName() {
	return designationName;
}
public void setDesignationName(DesignationDTO designationName) {
	this.designationName = designationName;
}
public String getEmpName() {
	return empName;
}
public void setEmpName(String empName) {
	this.empName = empName;
}

}
