package com.callippus.web.beans.letterNumberFormat;

import java.util.List;

import com.callippus.web.controller.common.CPSUtils;

public class IONDTO {
	private int id;
	
	private Integer letterFormatId;


	private String ionDate;
	private String letterNumber;
	private String delegation;
    private String subject;
    private String reference;
    private String content;
    private String ionInitiatedSfid;
    private String enclosers;
	
	private int status;
	private int circulationStatus;
	

	private String createdBy;
	private String creationDate;
	private String lastModifiedDate;
	private String lastModifiedBy;
	
	private String deptName;
	
	private List deparmentList;
	private List designList;
	private List orgRoleList;
	private List roleHirarchyList;
	private List sfidList;
	private List orgList;
	
	private List deparmentListCopy;
	private List designListCopy;
	private List orgRoleListCopy;
	private List roleHirarchyListCopy;
	private List sfidListCopy;
	private List orgListCopy;
	
	private List ionCirculationDetailsList;
	
	private String ionInitiatedRoleId;
	private String sfid;
	private List ionInitiatedRoleList;
	
	private String ionForwardSfid;
	
	private String ionForwardRoleId;
	
	private String ionApprovedSfid;
	private String ionApprovedRoleId;
	private List fileEncloserList;
    private String publicStatus;
    private String circulateVisible;
    
    private String ionCreatedBy;
    private String ionCreationDate;
    private List<IONDTO> displayDetails;






public String getIonCreatedBy() {
		return ionCreatedBy;
	}

	public void setIonCreatedBy(String ionCreatedBy) {
		this.ionCreatedBy = ionCreatedBy;
	}

	public String getIonCreationDate() {
		return ionCreationDate;
	}

	public void setIonCreationDate(String ionCreationDate) {
		this.ionCreationDate = ionCreationDate;
	}

public String getCirculateVisible() {
	return circulateVisible;
}

public void setCirculateVisible(String circulateVisible) {
	this.circulateVisible = circulateVisible;
}

	
	public String getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(String publicStatus) {
		this.publicStatus = publicStatus;
	}


	
	
	


	public List getFileEncloserList() {
		return fileEncloserList;
	}

	public void setFileEncloserList(List fileEncloserList) {
		this.fileEncloserList = fileEncloserList;
	}

	public String toString()
	{
		String str ="";
		try
		{
		if(!CPSUtils.isNullOrEmpty(this.getSubject()))
			str +="Subject:"+this.getSubject()+",";
		if(!CPSUtils.isNullOrEmpty(this.getContent()))
			str +="Content:"+this.getContent()+",";
		if(!CPSUtils.isNullOrEmpty(this.getEnclosers()))
			str +="Enclosers:"+this.getEnclosers()+",";
		if(!CPSUtils.isNullOrEmpty(this.getIonInitiatedSfid()))
			str +="IonInitiatedSfid:"+this.getIonInitiatedSfid()+",";
		if(!CPSUtils.isNullOrEmpty(this.getSfid()))
			str +="Sfid:"+this.getSfid()+",";
		if(!CPSUtils.isNullOrEmpty(this.getCirculationStatus()))
			str +="CirculationStatus:"+this.getCirculationStatus()+",";
		if(!CPSUtils.isNullOrEmpty(this.getReference()))
			str +="Reference:"+this.getReference()+",";
		if(!CPSUtils.isNullOrEmpty(this.getIonInitiatedRoleId()))
			str +="IonInitiatedRoleId:"+this.getIonInitiatedRoleId()+",";
		if(CPSUtils.isNull(this.getCirculationStatus()))
			str+= " circulationStatus is null value.";
		else if(CPSUtils.isNullOrEmpty(this.getCirculationStatus()))
			str+= " circulationStatus is empty value.";
		
		if(CPSUtils.isNull(this.getIonInitiatedRoleId()))
			str+= " IonInitiatedRoleId is null value.";
		else if(CPSUtils.isNullOrEmpty(this.getIonInitiatedRoleId()))
			str+= " IonInitiatedRoleId is empty value.";
			
		     
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return str;	
	}
	
	public List getIonInitiatedRoleList() {
		return ionInitiatedRoleList;
	}

	public void setIonInitiatedRoleList(List ionInitiatedRoleList) {
		this.ionInitiatedRoleList = ionInitiatedRoleList;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getIonInitiatedRoleId() {
		return ionInitiatedRoleId;
	}

	public void setIonInitiatedRoleId(String ionInitiatedRoleId) {
		this.ionInitiatedRoleId = ionInitiatedRoleId;
	}

	public List getIonCirculationDetailsList() {
		return ionCirculationDetailsList;
	}

	public void setIonCirculationDetailsList(List ionCirculationDetailsList) {
		this.ionCirculationDetailsList = ionCirculationDetailsList;
	}

	public List getRoleHirarchyList() {
		return roleHirarchyList;
	}

	public void setRoleHirarchyList(List roleHirarchyList) {
		this.roleHirarchyList = roleHirarchyList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIonDate() {
		return ionDate;
	}

	public void setIonDate(String ionDate) {
		this.ionDate = ionDate;
	}

	public String getLetterNumber() {
		return letterNumber;
	}

	public void setLetterNumber(String letterNumber) {
		this.letterNumber = letterNumber;
	}

	public String getDelegation() {
		return delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIonInitiatedSfid() {
		return ionInitiatedSfid;
	}

	public void setIonInitiatedSfid(String ionInitiatedSfid) {
		this.ionInitiatedSfid = ionInitiatedSfid;
	}

	public String getEnclosers() {
		return enclosers;
	}

	public void setEnclosers(String enclosers) {
		this.enclosers = enclosers;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public Integer getLetterFormatId() {
		return letterFormatId;
	}

	public void setLetterFormatId(Integer letterFormatId) {
		this.letterFormatId = letterFormatId;
	}


	public int getCirculationStatus() {
		return circulationStatus;
	}

	public void setCirculationStatus(int circulationStatus) {
		this.circulationStatus = circulationStatus;
	}

	public List getDeparmentList() {
		return deparmentList;
	}

	public void setDeparmentList(List deparmentList) {
		this.deparmentList = deparmentList;
	}

	public List getDesignList() {
		return designList;
	}

	public void setDesignList(List designList) {
		this.designList = designList;
	}

	public List getOrgRoleList() {
		return orgRoleList;
	}

	public void setOrgRoleList(List orgRoleList) {
		this.orgRoleList = orgRoleList;
	}

	public List getSfidList() {
		return sfidList;
	}

	public void setSfidList(List sfidList) {
		this.sfidList = sfidList;
	}

	public String getIonForwardSfid() {
		return ionForwardSfid;
	}

	public void setIonForwardSfid(String ionForwardSfid) {
		this.ionForwardSfid = ionForwardSfid;
	}

	public String getIonForwardRoleId() {
		return ionForwardRoleId;
	}

	public void setIonForwardRoleId(String ionForwardRoleId) {
		this.ionForwardRoleId = ionForwardRoleId;
	}

	public String getIonApprovedSfid() {
		return ionApprovedSfid;
	}

	public void setIonApprovedSfid(String ionApprovedSfid) {
		this.ionApprovedSfid = ionApprovedSfid;
	}

	public String getIonApprovedRoleId() {
		return ionApprovedRoleId;
	}

	public void setIonApprovedRoleId(String ionApprovedRoleId) {
		this.ionApprovedRoleId = ionApprovedRoleId;
	}

	
	public List getOrgList() {
		return orgList;
	}

	public void setOrgList(List orgList) {
		this.orgList = orgList;
	}
	
	public List getDeparmentListCopy() {
		return deparmentListCopy;
	}

	public void setDeparmentListCopy(List deparmentListCopy) {
		this.deparmentListCopy = deparmentListCopy;
	}

	public List getDesignListCopy() {
		return designListCopy;
	}

	public void setDesignListCopy(List designListCopy) {
		this.designListCopy = designListCopy;
	}

	public List getOrgRoleListCopy() {
		return orgRoleListCopy;
	}

	public void setOrgRoleListCopy(List orgRoleListCopy) {
		this.orgRoleListCopy = orgRoleListCopy;
	}

	public List getRoleHirarchyListCopy() {
		return roleHirarchyListCopy;
	}

	public void setRoleHirarchyListCopy(List roleHirarchyListCopy) {
		this.roleHirarchyListCopy = roleHirarchyListCopy;
	}

	public List getSfidListCopy() {
		return sfidListCopy;
	}

	public void setSfidListCopy(List sfidListCopy) {
		this.sfidListCopy = sfidListCopy;
	}

	public List getOrgListCopy() {
		return orgListCopy;
	}

	public void setOrgListCopy(List orgListCopy) {
		this.orgListCopy = orgListCopy;
	}

	
}
