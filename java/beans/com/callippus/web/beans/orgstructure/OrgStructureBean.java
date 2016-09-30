package com.callippus.web.beans.orgstructure;

import java.util.List;

import com.callippus.web.beans.dto.DepartmentTypeDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.EmployeeDetailsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrgLevelsDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.dto.UserSpecificDTO;
import com.callippus.web.cghs.beans.dto.RequestTypeDTO;

public class OrgStructureBean {
	private String type;
	private String param;
	private String result;
	private String message;
	private List<OrgLevelsDTO> levelsList;
	private String newLevel;
	private String parentLevel;
	private String levelID;
	private String remarks;
	private List<DepartmentsDTO> departmentsList;
	private List<DepartmentTypeDTO> departmentsTypeList;
	private String departmentTypeId;
	private String parentDept;
	private String departmentName;
	private String nodeID;
	private String deleteID;
	private List<OrgInstanceDTO> rolesList;
	private String parentRole;
	private String roleName;
	private String departmentId;
	private String isHead;
	private String headID;
	private List<String> empRoles;
	private String sfID;
	private List<EmployeeDetailsDTO> normalEmpDetails;
	private String searchingWith;
	private String searchingName;
	private String searchBox;
	private String searchDept;
	private String empGroup;
	private List<EmployeeDetailsDTO> roleEmployeesList;
	private String mapSfID;
	private String isDefault;
	private String editSfid;
	private List<RequestTypeDTO> requestTypeList;
	private List<DesignationDTO> designationsList;
	private List<RequestsDTO>requestsList;
	private List<UserSpecificDTO> requestNameList;
	

	private List<KeyValueDTO> empRolesList;
	private List<UserSpecificDTO> userSpecificList;
	private String requestName;
	private String designation;
	private String assignType;
	private String subordinateId;
	private String gazType;
	private List<KeyValueDTO> subordinatesList;
	private String multipleDesignation;
	private String multipleRequest;
	

	private String description;
	private String fax;
	private List organizationList;
	private String pincode;
	
	private String address1;
	private String address2;
	private String address3;
	private String name;
	
	
	public String getMultipleRequest() {
		return multipleRequest;
	}

	public void setMultipleRequest(String multipleRequest) {
		this.multipleRequest = multipleRequest;
	}
	
	public List<RequestsDTO> getRequestsList() {
		return requestsList;
	}

	public void setRequestsList(List<RequestsDTO> requestsList) {
		this.requestsList = requestsList;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List organizationList) {
		this.organizationList = organizationList;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String phoneNumber;
	private String email;
	

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMultipleDesignation() {
		return multipleDesignation;
	}

	public void setMultipleDesignation(String multipleDesignation) {
		this.multipleDesignation = multipleDesignation;
	}

	public List<KeyValueDTO> getSubordinatesList() {
		return subordinatesList;
	}

	public void setSubordinatesList(List<KeyValueDTO> subordinatesList) {
		this.subordinatesList = subordinatesList;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getSubordinateId() {
		return subordinateId;
	}

	public void setSubordinateId(String subordinateId) {
		this.subordinateId = subordinateId;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public List<UserSpecificDTO> getUserSpecificList() {
		return userSpecificList;
	}

	public void setUserSpecificList(List<UserSpecificDTO> userSpecificList) {
		this.userSpecificList = userSpecificList;
	}

	public List<KeyValueDTO> getEmpRolesList() {
		return empRolesList;
	}

	public void setEmpRolesList(List<KeyValueDTO> empRolesList) {
		this.empRolesList = empRolesList;
	}

	public List<DesignationDTO> getDesignationsList() {
		return designationsList;
	}

	public void setDesignationsList(List<DesignationDTO> designationsList) {
		this.designationsList = designationsList;
	}

	public List<RequestTypeDTO> getRequestTypeList() {
		return requestTypeList;
	}

	public void setRequestTypeList(List<RequestTypeDTO> requestTypeList) {
		this.requestTypeList = requestTypeList;
	}

	public String getEditSfid() {
		return editSfid;
	}

	public void setEditSfid(String editSfid) {
		this.editSfid = editSfid;
	}

	public String getMapSfID() {
		return mapSfID;
	}

	public void setMapSfID(String mapSfID) {
		this.mapSfID = mapSfID;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public List<EmployeeDetailsDTO> getRoleEmployeesList() {
		return roleEmployeesList;
	}

	public void setRoleEmployeesList(List<EmployeeDetailsDTO> roleEmployeesList) {
		this.roleEmployeesList = roleEmployeesList;
	}

	public String getEmpGroup() {
		return empGroup;
	}

	public void setEmpGroup(String empGroup) {
		this.empGroup = empGroup;
	}

	public String getSearchingWith() {
		return searchingWith;
	}

	public void setSearchingWith(String searchingWith) {
		this.searchingWith = searchingWith;
	}

	public String getSearchingName() {
		return searchingName;
	}

	public void setSearchingName(String searchingName) {
		this.searchingName = searchingName;
	}

	public String getSearchBox() {
		return searchBox;
	}

	public void setSearchBox(String searchBox) {
		this.searchBox = searchBox;
	}

	public String getSearchDept() {
		return searchDept;
	}

	public void setSearchDept(String searchDept) {
		this.searchDept = searchDept;
	}

	public List<EmployeeDetailsDTO> getNormalEmpDetails() {
		return normalEmpDetails;
	}

	public void setNormalEmpDetails(List<EmployeeDetailsDTO> normalEmpDetails) {
		this.normalEmpDetails = normalEmpDetails;
	}

	public List<String> getEmpRoles() {
		return empRoles;
	}

	public void setEmpRoles(List<String> empRoles) {
		this.empRoles = empRoles;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getHeadID() {
		return headID;
	}

	public void setHeadID(String headID) {
		this.headID = headID;
	}

	public String getIsHead() {
		return isHead;
	}

	public void setIsHead(String isHead) {
		this.isHead = isHead;
	}

	public String getParentRole() {
		return parentRole;
	}

	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public List<OrgInstanceDTO> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<OrgInstanceDTO> rolesList) {
		this.rolesList = rolesList;
	}

	public String getDeleteID() {
		return deleteID;
	}

	public void setDeleteID(String deleteID) {
		this.deleteID = deleteID;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getParentDept() {
		return parentDept;
	}

	public String getDepartmentTypeId() {
		return departmentTypeId;
	}

	public void setDepartmentTypeId(String departmentTypeId) {
		this.departmentTypeId = departmentTypeId;
	}

	public void setParentDept(String parentDept) {
		this.parentDept = parentDept;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<DepartmentTypeDTO> getDepartmentsTypeList() {
		return departmentsTypeList;
	}

	public void setDepartmentsTypeList(List<DepartmentTypeDTO> departmentsTypeList) {
		this.departmentsTypeList = departmentsTypeList;
	}

	public List<DepartmentsDTO> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<DepartmentsDTO> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLevelID() {
		return levelID;
	}

	public void setLevelID(String levelID) {
		this.levelID = levelID;
	}

	public String getNewLevel() {
		return newLevel;
	}

	public void setNewLevel(String newLevel) {
		this.newLevel = newLevel;
	}

	public String getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	public List<OrgLevelsDTO> getLevelsList() {
		return levelsList;
	}

	public void setLevelsList(List<OrgLevelsDTO> levelsList) {
		this.levelsList = levelsList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public List<UserSpecificDTO> getRequestNameList() {
		return requestNameList;
	}

	public void setRequestNameList(List<UserSpecificDTO> requestNameList) {
		this.requestNameList = requestNameList;
	}

	

}
