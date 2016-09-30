package com.callippus.web.beans.letterNumberFormat;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

public class LetterNumberFormatBean {
	
	private String id;
	
	private String deptNum;
	private String deptName;
	private String deptSeriesStartNum;
	private String deptSeriesEndNum;
	private String param;
	private String type;
	private String message;
	private String deptList;
	private String sfid;
	private String deptShortName;
	private String series;
	
	
	private String deptCode;
	
	private String topic;
	private String shortForm;
	private String serialNum;
	private String yearType;
	private String runningNum;
	private String letterNumberFormat;
	private String letterDate;
	
	private String status;
	private String createdBy;
	private String creationDate;
	private String lastModifiedDate;
	private String lastModifiedBy;
	private String back;
	
	private String delegationType;
	
	private String delegationsType;
	private String delegation;
	private String letterFormatId;
	private String letterNumber;
	private String ionDate;
	private List numberList;
	private String ionLetterNumberId;
	private String reference;
	private String subject;
	private String content;
	private String enclosers;
	
	private String initiatedSfid;
	private String ionInitiatedRoleId;
	private String ionInitiatedSfid;
	private String ionForwardRoleId;
	
	private String ionForwardSfid;
	private List ionForwardRoleList;
	private String ionApprovedRoleId;
	private String ionApprovedSfid;
	private List ionApprovedRoleList;
	
	
	
	
	private String departments;
	private String department;
	private String departmentCopy;
	private String departmentId;
	
	private String designations;
	private String designation;
	private String designationCopy;
	private String designationId;
	
	private String departmentOrRole;
	private String departmentOrRoleVal;
	
	private String organizations;
	private String organization;
	private String organizationCopy;
	private String organizationId;
	
	private String roles;
	private String role;
	private String roleCopy;
	private String roleId;
	private String orgRoleId;
	
	private String sfidNames;
	private String sfidName;
	private String sfidNameCopy;
	private String sfidNameId;
	
	private String designationIdCopy;
	
	private String roleHirarchyIdCopy;
	private String departmentIdCopy;
	private String organizationIdCopy;
	private String roleIdCopy;
	private String orgRoleIdCopy;
	private String sfidNameIdCopy;
	
	
	private List deparmentList;
	private List designList;
	private List orgRoleList;
	private List sfidList;
	private List roleHirarchyList;
	private List orgList;
	
	private List deparmentListCopy;
	private List designListCopy;
	private List orgRoleListCopy;
	private List roleHirarchyListCopy;
	private List sfidListCopy;
	private List orgListCopy;
	
	private String circulationStatus;
	private String startDate;
	private String endDate;
	
	private String roleHirarchyId;
	
	private String selectStatus;
	private String referenceVal;
	
	private String subjectVal;
	private String contentVal;
	private String enclosersVal;
	private String tempLetterFormatId;
	private String roleHirarchys;
	private String roleHirarchy;
	private String roleHirarchyCopy;
	private String savingType;
	private String jsonValues1;
	private String reason;
	
	private JSONObject roleDetails;
	
	private String editFlag;
	
	
	private List fileEncloserList;
	private MultipartFile enclosureFile;
	private String[] enclosureFileName;
	private String fileId;
	private String publicStatus;
	private String ionSubjectSearch;
	private String circulateVisible;
	
	private String fileTypeId;
	private String fileTypeName;
	private List fileTypeList;
	private String pk; 
	private List<IONDTO> displayIONDetails;
	private String ionScreenType;
	
	public List<IONDTO> getDisplayIONDetails() {
		return displayIONDetails;
	}

	public void setDisplayIONDetails(List<IONDTO> displayIONDetails) {
		this.displayIONDetails = displayIONDetails;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List getFileTypeList() {
		return fileTypeList;
	}

	public void setFileTypeList(List fileTypeList) {
		this.fileTypeList = fileTypeList;
	}

	public String getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
	
	
	
	


	
	public String getCirculateVisible() {
		return circulateVisible;
	}

	public void setCirculateVisible(String circulateVisible) {
		this.circulateVisible = circulateVisible;
	}

	public String getIonSubjectSearch() {
		return ionSubjectSearch;
	}

	public void setIonSubjectSearch(String ionSubjectSearch) {
		this.ionSubjectSearch = ionSubjectSearch;
	}

	public String getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(String publicStatus) {
		this.publicStatus = publicStatus;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String[] getEnclosureFileName() {
		return enclosureFileName;
	}

	public void setEnclosureFileName(String[] enclosureFileName) {
		this.enclosureFileName = enclosureFileName;
	}

	public MultipartFile getEnclosureFile() {
		return enclosureFile;
	}

	public void setEnclosureFile(MultipartFile enclosureFile) {
		this.enclosureFile = enclosureFile;
	}

	public List getFileEncloserList() {
		return fileEncloserList;
	}

	public void setFileEncloserList(List fileEncloserList) {
		this.fileEncloserList = fileEncloserList;
	}
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	private List ionInitiatedRoleList;
	
	public List getIonInitiatedRoleList() {
		return ionInitiatedRoleList;
	}
	public void setIonInitiatedRoleList(List ionInitiatedRoleList) {
		this.ionInitiatedRoleList = ionInitiatedRoleList;
	}
	public JSONObject getRoleDetails() {
		return roleDetails;
	}
	public void setRoleDetails(JSONObject roleDetails) {
		this.roleDetails = roleDetails;
	}
	public String getIonInitiatedSfid() {
		return ionInitiatedSfid;
	}
	public void setIonInitiatedSfid(String ionInitiatedSfid) {
		this.ionInitiatedSfid = ionInitiatedSfid;
	}
	public String getIonInitiatedRoleId() {
		return ionInitiatedRoleId;
	}
	public void setIonInitiatedRoleId(String ionInitiatedRoleId) {
		this.ionInitiatedRoleId = ionInitiatedRoleId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getJsonValues2() {
		return jsonValues2;
	}
	public void setJsonValues2(String jsonValues2) {
		this.jsonValues2 = jsonValues2;
	}
	private String jsonValues2;
	
	private String refOrgRoleId;
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRefOrgRoleId() {
		return refOrgRoleId;
	}
	public void setRefOrgRoleId(String refOrgRoleId) {
		this.refOrgRoleId = refOrgRoleId;
	}
	public String getJsonValues1() {
		return jsonValues1;
	}
	public void setJsonValues1(String jsonValues1) {
		this.jsonValues1 = jsonValues1;
	}
	public String getSavingType() {
		return savingType;
	}
	public void setSavingType(String savingType) {
		this.savingType = savingType;
	}
	public String getRoleHirarchys() {
		return roleHirarchys;
	}
	public void setRoleHirarchys(String roleHirarchys) {
		this.roleHirarchys = roleHirarchys;
	}
	public String getRoleHirarchy() {
		return roleHirarchy;
	}
	public void setRoleHirarchy(String roleHirarchy) {
		this.roleHirarchy = roleHirarchy;
	}
	
	
	
	
	
	public String getTempLetterFormatId() {
		return tempLetterFormatId;
	}
	public void setTempLetterFormatId(String tempLetterFormatId) {
		this.tempLetterFormatId = tempLetterFormatId;
	}
	public String getSelectStatus() {
		return selectStatus;
	}
	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}
	public String getCirculationStatus() {
		return circulationStatus;
	}
	public void setCirculationStatus(String circulationStatus) {
		this.circulationStatus = circulationStatus;
	}
	public String getDepartmentOrRoleVal() {
		return departmentOrRoleVal;
	}
	public void setDepartmentOrRoleVal(String departmentOrRoleVal) {
		this.departmentOrRoleVal = departmentOrRoleVal;
	}
	public String getDepartmentOrRole() {
		return departmentOrRole;
	}
	public void setDepartmentOrRole(String departmentOrRole) {
		this.departmentOrRole = departmentOrRole;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getInitiatedSfid() {
		return initiatedSfid;
	}
	public void setInitiatedSfid(String initiatedSfid) {
		this.initiatedSfid = initiatedSfid;
	}
	public String getIonLetterNumberId() {
		return ionLetterNumberId;
	}
	public void setIonLetterNumberId(String ionLetterNumberId) {
		this.ionLetterNumberId = ionLetterNumberId;
	}
	public List getNumberList() {
		return numberList;
	}
	public void setNumberList(List numberList) {
		this.numberList = numberList;
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
	public String getLetterFormatId() {
		return letterFormatId;
	}
	public void setLetterFormatId(String letterFormatId) {
		this.letterFormatId = letterFormatId;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptSeriesStartNum() {
		return deptSeriesStartNum;
	}
	public void setDeptSeriesStartNum(String deptSeriesStartNum) {
		this.deptSeriesStartNum = deptSeriesStartNum;
	}
	public String getDeptSeriesEndNum() {
		return deptSeriesEndNum;
	}
	public void setDeptSeriesEndNum(String deptSeriesEndNum) {
		this.deptSeriesEndNum = deptSeriesEndNum;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDeptList() {
		return deptList;
	}
	public void setDeptList(String deptList) {
		this.deptList = deptList;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptShortName() {
		return deptShortName;
	}
	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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

	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getShortForm() {
		return shortForm;
	}
	public void setShortForm(String shortForm) {
		this.shortForm = shortForm;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getYearType() {
		return yearType;
	}
	public void setYearType(String yearType) {
		this.yearType = yearType;
	}
	public String getRunningNum() {
		return runningNum;
	}
	public void setRunningNum(String runningNum) {
		this.runningNum = runningNum;
	}
	public String getLetterNumberFormat() {
		return letterNumberFormat;
	}
	public void setLetterNumberFormat(String letterNumberFormat) {
		this.letterNumberFormat = letterNumberFormat;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}

	public String getLetterDate() {
		return letterDate;
	}
	public void setLetterDate(String letterDate) {
		this.letterDate = letterDate;
	}
	public String getDelegationType() {
		return delegationType;
	}
	public void setDelegationType(String delegationType) {
		this.delegationType = delegationType;
	}
	public String getDelegationsType() {
		return delegationsType;
	}
	public void setDelegationsType(String delegationsType) {
		this.delegationsType = delegationsType;
	}
	public String getDelegation() {
		return delegation;
	}
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEnclosers() {
		return enclosers;
	}
	public void setEnclosers(String enclosers) {
		this.enclosers = enclosers;
	}
	public String getDepartments() {
		return departments;
	}
	public void setDepartments(String departments) {
		this.departments = departments;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignations() {
		return designations;
	}
	public void setDesignations(String designations) {
		this.designations = designations;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getSfidNames() {
		return sfidNames;
	}
	public void setSfidNames(String sfidNames) {
		this.sfidNames = sfidNames;
	}
	public String getSfidName() {
		return sfidName;
	}
	public void setSfidName(String sfidName) {
		this.sfidName = sfidName;
	}
	public String getSfidNameId() {
		return sfidNameId;
	}
	public void setSfidNameId(String sfidNameId) {
		this.sfidNameId = sfidNameId;
	}

	public String getOrgRoleId() {
		return orgRoleId;
	}
	public void setOrgRoleId(String orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getReferenceVal() {
		return referenceVal;
	}
	public void setReferenceVal(String referenceVal) {
		this.referenceVal = referenceVal;
	}
	public String getSubjectVal() {
		return subjectVal;
	}
	public void setSubjectVal(String subjectVal) {
		this.subjectVal = subjectVal;
	}
	public String getContentVal() {
		return contentVal;
	}
	public void setContentVal(String contentVal) {
		this.contentVal = contentVal;
	}
	public String getEnclosersVal() {
		return enclosersVal;
	}
	public void setEnclosersVal(String enclosersVal) {
		this.enclosersVal = enclosersVal;
	}
	public List getRoleHirarchyList() {
		return roleHirarchyList;
	}
	public void setRoleHirarchyList(List roleHirarchyList) {
		this.roleHirarchyList = roleHirarchyList;
	}

	public String getRoleHirarchyId() {
		return roleHirarchyId;
	}
	public void setRoleHirarchyId(String roleHirarchyId) {
		this.roleHirarchyId = roleHirarchyId;
	}
	public String getIonForwardRoleId() {
		return ionForwardRoleId;
	}
	public void setIonForwardRoleId(String ionForwardRoleId) {
		this.ionForwardRoleId = ionForwardRoleId;
	}
	public String getIonForwardSfid() {
		return ionForwardSfid;
	}
	public void setIonForwardSfid(String ionForwardSfid) {
		this.ionForwardSfid = ionForwardSfid;
	}
	public String getIonApprovedRoleId() {
		return ionApprovedRoleId;
	}
	public void setIonApprovedRoleId(String ionApprovedRoleId) {
		this.ionApprovedRoleId = ionApprovedRoleId;
	}
	public String getIonApprovedSfid() {
		return ionApprovedSfid;
	}
	public void setIonApprovedSfid(String ionApprovedSfid) {
		this.ionApprovedSfid = ionApprovedSfid;
	}
	public String getOrganizations() {
		return organizations;
	}
	public void setOrganizations(String organizations) {
		this.organizations = organizations;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public List getOrgList() {
		return orgList;
	}
	public void setOrgList(List orgList) {
		this.orgList = orgList;
	}
	public List getIonForwardRoleList() {
		return ionForwardRoleList;
	}
	public void setIonForwardRoleList(List ionForwardRoleList) {
		this.ionForwardRoleList = ionForwardRoleList;
	}
	public List getIonApprovedRoleList() {
		return ionApprovedRoleList;
	}
	public void setIonApprovedRoleList(List ionApprovedRoleList) {
		this.ionApprovedRoleList = ionApprovedRoleList;
	}
	public String getDepartmentCopy() {
		return departmentCopy;
	}
	public void setDepartmentCopy(String departmentCopy) {
		this.departmentCopy = departmentCopy;
	}
	public String getDesignationCopy() {
		return designationCopy;
	}
	public void setDesignationCopy(String designationCopy) {
		this.designationCopy = designationCopy;
	}
	public String getOrganizationCopy() {
		return organizationCopy;
	}
	public void setOrganizationCopy(String organizationCopy) {
		this.organizationCopy = organizationCopy;
	}
	public String getRoleCopy() {
		return roleCopy;
	}
	public void setRoleCopy(String roleCopy) {
		this.roleCopy = roleCopy;
	}
	public String getSfidNameCopy() {
		return sfidNameCopy;
	}
	public void setSfidNameCopy(String sfidNameCopy) {
		this.sfidNameCopy = sfidNameCopy;
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
	public String getRoleHirarchyCopy() {
		return roleHirarchyCopy;
	}
	public void setRoleHirarchyCopy(String roleHirarchyCopy) {
		this.roleHirarchyCopy = roleHirarchyCopy;
	}
	public String getDesignationIdCopy() {
		return designationIdCopy;
	}
	public void setDesignationIdCopy(String designationIdCopy) {
		this.designationIdCopy = designationIdCopy;
	}
	public String getDepartmentIdCopy() {
		return departmentIdCopy;
	}
	public void setDepartmentIdCopy(String departmentIdCopy) {
		this.departmentIdCopy = departmentIdCopy;
	}
	public String getOrganizationIdCopy() {
		return organizationIdCopy;
	}
	public void setOrganizationIdCopy(String organizationIdCopy) {
		this.organizationIdCopy = organizationIdCopy;
	}
	public String getRoleIdCopy() {
		return roleIdCopy;
	}
	public void setRoleIdCopy(String roleIdCopy) {
		this.roleIdCopy = roleIdCopy;
	}
	public String getOrgRoleIdCopy() {
		return orgRoleIdCopy;
	}
	public void setOrgRoleIdCopy(String orgRoleIdCopy) {
		this.orgRoleIdCopy = orgRoleIdCopy;
	}
	public String getSfidNameIdCopy() {
		return sfidNameIdCopy;
	}
	public void setSfidNameIdCopy(String sfidNameIdCopy) {
		this.sfidNameIdCopy = sfidNameIdCopy;
	}
	public String getRoleHirarchyIdCopy() {
		return roleHirarchyIdCopy;
	}
	public void setRoleHirarchyIdCopy(String roleHirarchyIdCopy) {
		this.roleHirarchyIdCopy = roleHirarchyIdCopy;
	}

	public String getIonScreenType() {
		return ionScreenType;
	}

	public void setIonScreenType(String ionScreenType) {
		this.ionScreenType = ionScreenType;
	}
    

	
}
