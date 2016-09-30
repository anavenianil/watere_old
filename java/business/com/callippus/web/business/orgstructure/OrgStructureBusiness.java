package com.callippus.web.business.orgstructure;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.orgstructure.OrgStructureBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.orgstructure.IOrgStructureDAO;
import com.callippus.web.hrdg.training.beans.TrainingMasterBean;

@Service
public class OrgStructureBusiness {
	@Autowired
	private IOrgStructureDAO orgStructureDAO;
	@Autowired
	private IComonObjectDAO commonObjectDAO;

	public OrgStructureBean getLevels(OrgStructureBean orgStrBean) throws Exception {
		try {
			orgStrBean = orgStructureDAO.getLevels(orgStrBean);
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean submitLevels(OrgStructureBean orgStrBean) throws Exception {
		String tableName = null;
		String columnName = null;
		String uniqueColumnName = null;
		try {
			// Check the duplicate
			if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL)) {
				tableName = CPSConstants.DEPARTMENTS_HIERARCHY;
				columnName = CPSConstants.DEPT_HIERARCHY_NAME;
				uniqueColumnName = CPSConstants.DEPT_HIERARCHY_ID;
			} else {
				tableName = CPSConstants.ORG_ROLE_HIERARCHY;
				columnName = CPSConstants.ROLE_HIERARCHY_NAME;
				uniqueColumnName = CPSConstants.ROLE_HIERARCHY_ID;
			}
			orgStrBean.setResult(orgStructureDAO.checkDuplicate(tableName, columnName, orgStrBean.getNewLevel(), uniqueColumnName, orgStrBean.getLevelID()));

			if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
				orgStrBean = orgStructureDAO.submitLevels(orgStrBean);
				orgStrBean = orgStructureDAO.getLevels(orgStrBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean deleteOrgStructure(OrgStructureBean orgStrBean) throws Exception {
		try {
			orgStrBean = orgStructureDAO.deleteOrgStructure(orgStrBean);
			if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL) || CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL)) {
					orgStrBean = orgStructureDAO.getLevels(orgStrBean);
				} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENT)) {
					orgStrBean = orgStructureDAO.getDepartments(orgStrBean);
				} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLE)) {
					orgStrBean = orgStructureDAO.getRolesList(orgStrBean);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	@SuppressWarnings("unchecked")
	public OrgStructureBean getDepartmentsHomeDetails(OrgStructureBean orgStrBean) throws Exception {
		try {
			// Get departments Hierarchy Levels
			orgStrBean.setType(CPSConstants.DEPARTMENTLEVEL);
			orgStrBean = orgStructureDAO.getLevels(orgStrBean);
			// Get Departments Type List
			orgStrBean.setDepartmentsTypeList(commonObjectDAO.getMasterData(CPSConstants.DEPARTMENTTYPEDTO));
			// Get Departments List
			orgStrBean = orgStructureDAO.getDepartments(orgStrBean);

		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean getChildList(OrgStructureBean orgStrBean) throws Exception {
		try {
			if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPTREPORTSTO)) {
				// Get Departments List
				orgStrBean = orgStructureDAO.getDepartments(orgStrBean);
			} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLEREPORTSTO)) {
				// Get Roles Details
				orgStrBean = orgStructureDAO.getRolesList(orgStrBean);
			}

		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean submitDepartment(OrgStructureBean orgStrBean) throws Exception {
		try {
			// Check the duplicate
			orgStrBean.setResult(orgStructureDAO.checkDuplicate(CPSConstants.DEPARTMENTS_MASTER, CPSConstants.DEPARTMENT_NAME, orgStrBean.getDepartmentName(), CPSConstants.DEPARTMENT_ID, orgStrBean
					.getNodeID()));

			if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
				orgStrBean = orgStructureDAO.submitDepartment(orgStrBean);
				// Get Departments List
				orgStrBean = orgStructureDAO.getDepartments(orgStrBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean getOrgRoleDetails(OrgStructureBean orgStrBean) throws Exception {
		try {
			// Get departments Hierarchy Levels
			//orgStrBean.setType(CPSConstants.ROLELEVEL);
			orgStrBean = orgStructureDAO.getLevels(orgStrBean);
			// Get Departments List
			orgStrBean = orgStructureDAO.getDepartments(orgStrBean);

			// Get Roles Details
			orgStrBean = orgStructureDAO.getRolesList(orgStrBean);

		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean submitRole(OrgStructureBean orgStrBean) throws Exception {
		try {
			// Check the duplicate
			orgStrBean
					.setResult(orgStructureDAO.checkDuplicate(CPSConstants.ORG_ROLE_INSTANCE, CPSConstants.ORG_ROLE_NAME, orgStrBean.getRoleName(), CPSConstants.ORG_ROLE_ID, orgStrBean.getNodeID()));

			if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
				orgStrBean = orgStructureDAO.submitRole(orgStrBean);
				// Get Roles Details
				orgStrBean = orgStructureDAO.getRolesList(orgStrBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean normalEmployeesDetails(OrgStructureBean orgStrBean) throws Exception {
		try {
			// Get the departments List
			orgStrBean = orgStructureDAO.getDepartments(orgStrBean);
			// Get Roles Details
			orgStrBean = orgStructureDAO.getRolesList(orgStrBean);
			// Get the employees details
			orgStrBean = orgStructureDAO.normalEmployeesDetails(orgStrBean);
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean roleEmployeesDetails(OrgStructureBean orgStrBean) throws Exception {
		try {
			// Get the departments List
			orgStrBean = orgStructureDAO.getDepartments(orgStrBean);
			// Get Roles Details
			orgStrBean = orgStructureDAO.getRolesList(orgStrBean);
			// Get the employees details
			orgStrBean = orgStructureDAO.roleEmployeesDetails(orgStrBean);
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean submitEmployeeRole(OrgStructureBean orgStrBean) throws Exception {
		try {
			//for check employee
			if(CPSUtils.compareString(commonObjectDAO.checkEmployee(orgStrBean.getMapSfID()),CPSConstants.SUCCESS)){
			if (CPSUtils.isNullOrEmpty(orgStrBean.getNodeID())) {
				// New role assigning
				// check whether this role is already assigned or not
				orgStrBean = orgStructureDAO.checkRoleAssigned(orgStrBean);

				if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
					orgStrBean = orgStructureDAO.submitEmployeeRole(orgStrBean);
					// Get the employees details
					orgStrBean = orgStructureDAO.roleEmployeesDetails(orgStrBean);
				}
			} else {
				// Updating the role
				// First assign the employee as a normal employee to the superior(upper level), then update the employee sfid
				orgStrBean = orgStructureDAO.assignAsNormalEmployee(orgStrBean);

				if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS) && !CPSUtils.compareStrings(orgStrBean.getMapSfID(), orgStrBean.getEditSfid())) {
					// assign new employee to the role
					orgStrBean = orgStructureDAO.submitEmployeeRole(orgStrBean);

					// assign the new employee as head for the employees
					orgStrBean = orgStructureDAO.assignNewHead(orgStrBean);
				}
				// Get the employees details
				orgStrBean = orgStructureDAO.roleEmployeesDetails(orgStrBean);
			}
			}else{
				orgStrBean.setRemarks("Employee Not Exist");
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean deleteEmployeeRole(OrgStructureBean orgStrBean) throws Exception {
		try {
			// Check whether this user is head for any employee
			orgStrBean = orgStructureDAO.checkAsHead(orgStrBean);

			if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
				// Assigning the current user as normal employee to the superior
				orgStrBean = orgStructureDAO.assignAsNormalEmployee(orgStrBean);
				// Get the employees details
				orgStrBean = orgStructureDAO.roleEmployeesDetails(orgStrBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean searchEmployeeDetails(OrgStructureBean orgStrBean) throws Exception {
		try {
			if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.NORMAL)) {
				orgStrBean = orgStructureDAO.normalEmployeesDetails(orgStrBean);
			} else {
				orgStrBean = orgStructureDAO.roleEmployeesDetails(orgStrBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean deleteEmployees(OrgStructureBean orgStrBean) throws Exception {
		try {
			orgStrBean = orgStructureDAO.deleteEmployees(orgStrBean);
			if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
				orgStrBean = orgStructureDAO.normalEmployeesDetails(orgStrBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean submitEmployees(OrgStructureBean orgStrBean) throws Exception {
		try {
			if(!CPSUtils.isNullOrEmpty(orgStrBean.getEmpGroup())) {
				orgStrBean = orgStructureDAO.submitEmployees(orgStrBean);
			}else {
				// This block execute when assigning an SFID under specific role
				orgStrBean = orgStructureDAO.mapEmployee(orgStrBean);
			}
			if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
				orgStrBean = orgStructureDAO.normalEmployeesDetails(orgStrBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}
	
	@SuppressWarnings("unchecked")
	public OrgStructureBean getRoleSpecificDetails(OrgStructureBean orgStrBean) throws Exception {
		try {
			//Get request type list
			orgStrBean.setRequestTypeList(commonObjectDAO.getMasterData(CPSConstants.REQUESTWORKDTO));
			//Get designation List
			orgStrBean.setDesignationsList(orgStructureDAO.getDesignationsList());
			//Get roles List
			orgStrBean = orgStructureDAO.getEmpRoles(orgStrBean);
			//Get configured List
			//if ((orgStrBean.getEmpRoles().contains("ROLE_ADMIN") || orgStrBean.getEmpRoles().contains("ROLE_SUPERADMIN"))) {
				orgStrBean = orgStructureDAO.getConfiguredList(orgStrBean);
			//}
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}
	

	public OrgStructureBean getSubordinatesList(OrgStructureBean orgStrBean) throws Exception {
		try {
			if(!CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.RESULT)){
				//Get configured List
				orgStrBean = orgStructureDAO.getConfiguredList(orgStrBean);
			}
			//Get subordinates list
			orgStrBean = orgStructureDAO.getSubOrdinatesList(orgStrBean);
			
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}
	public OrgStructureBean deleteConfiguration(OrgStructureBean orgStrBean) throws Exception {
		try {
			//Delete Configuration
			orgStrBean = orgStructureDAO.deleteConfiguration(orgStrBean);
			//Get roles List
			orgStrBean = orgStructureDAO.getEmpRoles(orgStrBean);
			//Get configured List			
			orgStrBean = orgStructureDAO.getConfiguredList(orgStrBean);			
			
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}
	
	public OrgStructureBean submitConfiguration(OrgStructureBean orgStrBean) throws Exception {
		try {
			//Delete Configuration
			orgStrBean = orgStructureDAO.submitConfiguration(orgStrBean);
			//Get configured List			
			orgStrBean = orgStructureDAO.getConfiguredList(orgStrBean);			
			
		} catch (Exception e) {
			throw e;
		}
		return orgStrBean;
	}

	public OrgStructureBean getOrganizations(OrgStructureBean orgStrBean) throws Exception{
		try
		{
			orgStrBean.setOrganizationList(commonObjectDAO.getOrganizations());
		}
		catch (Exception e) {
			throw e;
		}
		return orgStrBean;
		
	}

	
	public OrgStructureBean submitOrganization(OrgStructureBean orgStrBean) throws Exception {
		String message = null;
		try {
					
			if (CPSUtils.isNullOrEmpty(orgStrBean.getNodeID())) {
				if (CPSUtils.compareStrings(checkOrganization(orgStrBean), CPSConstants.SUCCESS)) {

					OrganizationsDTO dto = new OrganizationsDTO();
					BeanUtils.copyProperties(dto, orgStrBean);

					message = commonObjectDAO.saveObject(dto);

				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkOrganization(orgStrBean), CPSConstants.SUCCESS)) {
					message = orgStructureDAO.updateOrganization(orgStrBean);

				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		orgStrBean.setMessage(message);
		return orgStrBean;
	}

	public String checkOrganization(OrgStructureBean orgStrBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(orgStructureDAO.checkOrganization(orgStrBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}
}