package com.callippus.web.paybill.dto;

import java.util.Date;

public class PayBillCGEISMasterDTO {
private int pk;
private Date groupInsuranceDate;
private String groupName;
private int groupId;
private float beforeMember;
private float afterMember;
private Date creationDate;
private String createdBy;
private Date modifiedDate;
private String modifiedBy;
private int status;
private PayBillGroupMasterDTO groupMasterDTO;
private String grpInsuranceDate;

public PayBillGroupMasterDTO getGroupMasterDTO() {
	return groupMasterDTO;
}
public void setGroupMasterDTO(PayBillGroupMasterDTO groupMasterDTO) {
	this.groupMasterDTO = groupMasterDTO;
}
public int getGroupId() {
	return groupId;
}
public void setGroupId(int groupId) {
	this.groupId = groupId;
}
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}

public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public Date getModifiedDate() {
	return modifiedDate;
}
public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}

public int getPk() {
	return pk;
}
public void setPk(int pk) {
	this.pk = pk;
}


public String getGrpInsuranceDate() {
	return grpInsuranceDate;
}
public void setGrpInsuranceDate(String grpInsuranceDate) {
	this.grpInsuranceDate = grpInsuranceDate;
}
public Date getGroupInsuranceDate() {
	return groupInsuranceDate;
}
public void setGroupInsuranceDate(Date groupInsuranceDate) {
	this.groupInsuranceDate = groupInsuranceDate;
}

public float getBeforeMember() {
	return beforeMember;
}
public void setBeforeMember(float beforeMember) {
	this.beforeMember = beforeMember;
}
public float getAfterMember() {
	return afterMember;
}
public void setAfterMember(float afterMember) {
	this.afterMember = afterMember;
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


}
