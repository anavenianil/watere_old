package com.callippus.web.beans.hierarchy;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.dto.DepartmentTypeDTO;
import com.callippus.web.beans.dto.EmployeeRoleDTO;
import com.callippus.web.beans.dto.HierarchyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;

public class HierarchyBean {
	private String param;
	private String type;
	private String parent;
	private String newLevel;
	private List parentList;
	private ArrayList<HierarchyDTO> hierarchyDisplayTable;
	private String id;
	private String message;
	private List roleLevelList;
	private String nodeNames;
	private String levelName;
	private String nodeParentName;
	private ArrayList<HierarchyDTO> roleParentList;
	private String roleInstanceName;
	private String internalDivisions;
	private String newInternalDiv;
	private String internalRoleName;
	private String newInternalRole;
	private String parentSFID;
	private List<OrgInstanceDTO> roleInstanceList;
	private ArrayList<KeyValueDTO> internalDivisionsList;
	private ArrayList<KeyValueDTO> internalRolesList;
	private String sfid;
	private String mapSFID;
	private String intRole;
	private String intDivision;
	private String roleInstanceSubName;
	private String instanceRoleID;
	private ArrayList<KeyValueDTO> subOrdinatesList;
	private String selectedRole;
	private ArrayList<EmployeeRoleDTO> internalStructureTree;
	private String manageID;
	private List instanceList;
	private String defaultFlag;
	private String searchWith;
	private String empname;
	private String searchvalue;
	private String divisionName;
	private String searchtype;
	private String checkedValues;
	

	
	private String departmentId;
	private ArrayList<KeyValueDTO> orgnRolesList;
	private String department;
	private List<DepartmentTypeDTO> departmentTypeList;
	private String departmentType;

	

	public String getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}

	public List<DepartmentTypeDTO> getDepartmentTypeList() {
		return departmentTypeList;
	}

	public void setDepartmentTypeList(List<DepartmentTypeDTO> departmentTypeList) {
		this.departmentTypeList = departmentTypeList;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public ArrayList<KeyValueDTO> getOrgnRolesList() {
		return orgnRolesList;
	}

	public void setOrgnRolesList(ArrayList<KeyValueDTO> orgnRolesList) {
		this.orgnRolesList = orgnRolesList;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCheckedValues() {
		return checkedValues;
	}

	public void setCheckedValues(String checkedValues) {
		this.checkedValues = checkedValues;
	}

	public String getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getSearchWith() {
		return searchWith;
	}

	public void setSearchWith(String searchWith) {
		this.searchWith = searchWith;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public List getInstanceList() {
		return instanceList;
	}

	public void setInstanceList(List instanceList) {
		this.instanceList = instanceList;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getManageID() {
		return manageID;
	}

	public void setManageID(String manageID) {
		this.manageID = manageID;
	}

	public ArrayList<EmployeeRoleDTO> getInternalStructureTree() {
		return internalStructureTree;
	}

	public void setInternalStructureTree(ArrayList<EmployeeRoleDTO> internalStructureTree) {
		this.internalStructureTree = internalStructureTree;
	}

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public String getInstanceRoleID() {
		return instanceRoleID;
	}

	public ArrayList<KeyValueDTO> getSubOrdinatesList() {
		return subOrdinatesList;
	}

	public void setSubOrdinatesList(ArrayList<KeyValueDTO> subOrdinatesList) {
		this.subOrdinatesList = subOrdinatesList;
	}

	public void setInstanceRoleID(String instanceRoleID) {
		this.instanceRoleID = instanceRoleID;
	}

	public String getRoleInstanceSubName() {
		return roleInstanceSubName;
	}

	public void setRoleInstanceSubName(String roleInstanceSubName) {
		this.roleInstanceSubName = roleInstanceSubName;
	}

	public String getIntRole() {
		return intRole;
	}

	public void setIntRole(String intRole) {
		this.intRole = intRole;
	}

	public String getIntDivision() {
		return intDivision;
	}

	public void setIntDivision(String intDivision) {
		this.intDivision = intDivision;
	}

	public String getMapSFID() {
		return mapSFID;
	}

	public void setMapSFID(String mapSFID) {
		this.mapSFID = mapSFID;
	}

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public List<OrgInstanceDTO> getRoleInstanceList() {
		return roleInstanceList;
	}

	public void setRoleInstanceList(List<OrgInstanceDTO> roleInstanceList) {
		this.roleInstanceList = roleInstanceList;
	}

	public ArrayList<KeyValueDTO> getInternalDivisionsList() {
		return internalDivisionsList;
	}

	public void setInternalDivisionsList(ArrayList<KeyValueDTO> internalDivisionsList) {
		this.internalDivisionsList = internalDivisionsList;
	}

	public ArrayList<KeyValueDTO> getInternalRolesList() {
		return internalRolesList;
	}

	public void setInternalRolesList(ArrayList<KeyValueDTO> internalRolesList) {
		this.internalRolesList = internalRolesList;
	}

	public String getRoleInstanceName() {
		return roleInstanceName;
	}

	public void setRoleInstanceName(String roleInstanceName) {
		this.roleInstanceName = roleInstanceName;
	}

	public String getInternalDivisions() {
		return internalDivisions;
	}

	public void setInternalDivisions(String internalDivisions) {
		this.internalDivisions = internalDivisions;
	}

	public String getNewInternalDiv() {
		return newInternalDiv;
	}

	public void setNewInternalDiv(String newInternalDiv) {
		this.newInternalDiv = newInternalDiv;
	}

	public String getInternalRoleName() {
		return internalRoleName;
	}

	public void setInternalRoleName(String internalRoleName) {
		this.internalRoleName = internalRoleName;
	}

	public String getNewInternalRole() {
		return newInternalRole;
	}

	public void setNewInternalRole(String newInternalRole) {
		this.newInternalRole = newInternalRole;
	}

	public String getParentSFID() {
		return parentSFID;
	}

	public void setParentSFID(String parentSFID) {
		this.parentSFID = parentSFID;
	}

	public ArrayList<HierarchyDTO> getRoleParentList() {
		return roleParentList;
	}

	public void setRoleParentList(ArrayList<HierarchyDTO> roleParentList) {
		this.roleParentList = roleParentList;
	}

	public String getMessage() {
		return message;
	}

	public String getNodeNames() {
		return nodeNames;
	}

	public void setNodeNames(String nodeNames) {
		this.nodeNames = nodeNames;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getNodeParentName() {
		return nodeParentName;
	}

	public void setNodeParentName(String nodeParentName) {
		this.nodeParentName = nodeParentName;
	}

	public List getRoleLevelList() {
		return roleLevelList;
	}

	public void setRoleLevelList(List roleLevelList) {
		this.roleLevelList = roleLevelList;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<HierarchyDTO> getHierarchyDisplayTable() {
		return hierarchyDisplayTable;
	}

	public void setHierarchyDisplayTable(ArrayList<HierarchyDTO> hierarchyDisplayTable) {
		this.hierarchyDisplayTable = hierarchyDisplayTable;
	}

	public List getParentList() {
		return parentList;
	}

	public void setParentList(List parentList) {
		this.parentList = parentList;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getNewLevel() {
		return newLevel;
	}

	public void setNewLevel(String newLevel) {
		this.newLevel = newLevel;
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
}
