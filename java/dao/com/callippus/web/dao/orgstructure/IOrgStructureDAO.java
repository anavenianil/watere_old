package com.callippus.web.dao.orgstructure;

import java.util.List;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.orgstructure.OrgStructureBean;

public interface IOrgStructureDAO {
	public OrgStructureBean getLevels(OrgStructureBean orgStrBean) throws Exception;

	public String checkDuplicate(String tableName, String columnName, String columnValue, String uniqueColumnName, String levelID) throws Exception;

	public OrgStructureBean submitLevels(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean deleteOrgStructure(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean getDepartments(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean submitDepartment(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean getRolesList(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean submitRole(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean normalEmployeesDetails(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean deleteEmployees(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean submitEmployees(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean mapEmployee(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean roleEmployeesDetails(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean checkRoleAssigned(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean submitEmployeeRole(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean assignAsNormalEmployee(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean assignNewHead(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean checkAsHead(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean getEmpRoles(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean getConfiguredList(OrgStructureBean orgStrBean) throws Exception;

	public List<DesignationDTO> getDesignationsList() throws Exception;
	
	public OrgStructureBean getSubOrdinatesList(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean deleteConfiguration(OrgStructureBean orgStrBean) throws Exception;

	public OrgStructureBean submitConfiguration(OrgStructureBean orgStrBean) throws Exception;

	public List checkOrganization(OrgStructureBean orgStrBean)throws Exception;

	public String updateOrganization(OrgStructureBean orgStrBean)throws Exception;

}
