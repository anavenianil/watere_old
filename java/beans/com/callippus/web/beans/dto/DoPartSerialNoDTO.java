package com.callippus.web.beans.dto;

public class DoPartSerialNoDTO extends CommonDTO{
private EssModuleDTO moduleMasterDetails;
private CasualityDetailsDTO casualityMasterDetails;
private DoPartDTO refDoPartDetails;
private String slNo;
private String requestId;
private int refDoPartId;
private int moduleId;
private int casualityId;


public int getRefDoPartId() {
	return refDoPartId;
}
public void setRefDoPartId(int refDoPartId) {
	this.refDoPartId = refDoPartId;
}
public int getModuleId() {
	return moduleId;
}
public void setModuleId(int moduleId) {
	this.moduleId = moduleId;
}
public int getCasualityId() {
	return casualityId;
}
public void setCasualityId(int casualityId) {
	this.casualityId = casualityId;
}
public String getSlNo() {
	return slNo;
}
public void setSlNo(String slNo) {
	this.slNo = slNo;
}
public String getRequestId() {
	return requestId;
}
public void setRequestId(String requestId) {
	this.requestId = requestId;
}
public EssModuleDTO getModuleMasterDetails() {
	return moduleMasterDetails;
}
public void setModuleMasterDetails(EssModuleDTO moduleMasterDetails) {
	this.moduleMasterDetails = moduleMasterDetails;
}
public CasualityDetailsDTO getCasualityMasterDetails() {
	return casualityMasterDetails;
}
public void setCasualityMasterDetails(CasualityDetailsDTO casualityMasterDetails) {
	this.casualityMasterDetails = casualityMasterDetails;
}
public DoPartDTO getRefDoPartDetails() {
	return refDoPartDetails;
}
public void setRefDoPartDetails(DoPartDTO refDoPartDetails) {
	this.refDoPartDetails = refDoPartDetails;
}


}
