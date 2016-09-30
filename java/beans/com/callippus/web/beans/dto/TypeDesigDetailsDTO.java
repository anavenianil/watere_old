package com.callippus.web.beans.dto;


public class TypeDesigDetailsDTO extends CommonDTO{
private int typeId;
private TypeDetailsDTO typeList;
private String remarks;
private DesignationDTO designationDetails;
private int designationId;
private String designations;
private String designationIds;
private String nodeIds;


public String getNodeIds() {
	return nodeIds;
}
public void setNodeIds(String nodeIds) {
	this.nodeIds = nodeIds;
}
public String getDesignations() {
	return designations;
}
public void setDesignations(String designations) {
	this.designations = designations;
}
public String getDesignationIds() {
	return designationIds;
}
public void setDesignationIds(String designationIds) {
	this.designationIds = designationIds;
}
public DesignationDTO getDesignationDetails() {
	return designationDetails;
}
public void setDesignationDetails(DesignationDTO designationDetails) {
	this.designationDetails = designationDetails;
}
public int getDesignationId() {
	return designationId;
}
public void setDesignationId(int designationId) {
	this.designationId = designationId;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public int getTypeId() {
	return typeId;
}
public TypeDetailsDTO getTypeList() {
	return typeList;
}
public void setTypeList(TypeDetailsDTO typeList) {
	this.typeList = typeList;
}
public void setTypeId(int typeId) {
	this.typeId = typeId;
}



}
