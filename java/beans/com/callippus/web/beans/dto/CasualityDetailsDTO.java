package com.callippus.web.beans.dto;


public class CasualityDetailsDTO extends CommonDTO{
private int moduleId;
private EssModuleDTO moduleList;
private int orderBy;
private TypeDetailsDTO typeList;
private int typeId;
private int code;
private String typeName;
private String moduleName;


public String getTypeName() {
	return typeName;
}
public void setTypeName(String typeName) {
	this.typeName = typeName;
}
public String getModuleName() {
	return moduleName;
}
public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public TypeDetailsDTO getTypeList() {
	return typeList;
}
public void setTypeList(TypeDetailsDTO typeList) {
	this.typeList = typeList;
}
public int getTypeId() {
	return typeId;
}
public void setTypeId(int typeId) {
	this.typeId = typeId;
}
public int getOrderBy() {
	return orderBy;
}
public void setOrderBy(int orderBy) {
	this.orderBy = orderBy;
}
public EssModuleDTO getModuleList() {
	return moduleList;
}
public void setModuleList(EssModuleDTO moduleList) {
	this.moduleList = moduleList;
}
public int getModuleId() {
	return moduleId;
}
public void setModuleId(int moduleId) {
	this.moduleId = moduleId;
}
}
