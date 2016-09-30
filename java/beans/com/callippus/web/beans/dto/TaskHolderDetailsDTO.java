package com.callippus.web.beans.dto;



public class TaskHolderDetailsDTO extends CommonDTO{
private int roleId;
private String sfID;
private String roleName;
private OrgInstanceDTO roleList;
private TypeDetailsDTO typeList;
private String remarks;
private int typeId;
private String typeName;


public String getTypeName() {
	return typeName;
}
public void setTypeName(String typeName) {
	this.typeName = typeName;
}
public int getTypeId() {
	return typeId;
}
public void setTypeId(int typeId) {
	this.typeId = typeId;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public TypeDetailsDTO getTypeList() {
	return typeList;
}
public void setTypeList(TypeDetailsDTO typeList) {
	this.typeList = typeList;
}
public OrgInstanceDTO getRoleList() {
	return roleList;
}
public void setRoleList(OrgInstanceDTO roleList) {
	this.roleList = roleList;
}
public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
public int getRoleId() {
	return roleId;
}
public void setRoleId(int roleId) {
	this.roleId = roleId;
}
public String getSfID() {
	return sfID;
}
public void setSfID(String sfID) {
	this.sfID = sfID;
}


}
