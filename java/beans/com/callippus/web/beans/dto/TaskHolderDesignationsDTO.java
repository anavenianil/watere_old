package com.callippus.web.beans.dto;

public class TaskHolderDesignationsDTO extends CommonDTO{
private TypeDesigDetailsDTO typeDesigDetails;
private int typeDesigId;
private int roleId;
private String designations;
private String typeDesigIds;
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
public String getTypeDesigIds() {
	return typeDesigIds;
}
public void setTypeDesigIds(String typeDesigIds) {
	this.typeDesigIds = typeDesigIds;
}
public String getDesignations() {
	return designations;
}
public void setDesignations(String designations) {
	this.designations = designations;
}
public TypeDesigDetailsDTO getTypeDesigDetails() {
	return typeDesigDetails;
}
public void setTypeDesigDetails(TypeDesigDetailsDTO typeDesigDetails) {
	this.typeDesigDetails = typeDesigDetails;
}
public int getTypeDesigId() {
	return typeDesigId;
}
public void setTypeDesigId(int typeDesigId) {
	this.typeDesigId = typeDesigId;
}
public int getRoleId() {
	return roleId;
}
public void setRoleId(int roleId) {
	this.roleId = roleId;
}


}
