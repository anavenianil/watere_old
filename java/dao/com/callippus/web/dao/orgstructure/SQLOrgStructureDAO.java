package com.callippus.web.dao.orgstructure;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DeptHierarchyDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.EmployeeDetailsDTO;
import com.callippus.web.beans.dto.HierarchyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrgLevelsDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.UserSpecificDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.orgstructure.OrgStructureBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.hrdg.dao.SQLCommonDAO;



@Service
public class SQLOrgStructureDAO implements IOrgStructureDAO {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	@Autowired
	private SQLCommonDAO sqlCommonDAO;


	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean getLevels(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		List<String> levels = new ArrayList<String>();
		boolean status = true;
		boolean flag = true;
		List<OrgLevelsDTO> livelsList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			// For super admin start id=1, For other get their levels first then give the list
			if (orgStrBean.getEmpRoles().contains("ROLE_ADMIN") || orgStrBean.getEmpRoles().contains("ROLE_SUPERADMIN")) {
				levels.add(CPSConstants.ROLE_SUPERADMIN);
			} else {
				// first get the departments hierarchy id's, then find their down tree
				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL)) {
					// Department Levels
					levels = session
							.createSQLQuery(
									"select distinct dm.dept_hierarchy_id||'' hierarchyID from emp_role_mapping erm,org_role_instance ori,departments_master dm where erm.status=1 and ori.status=1 and erm.sfid=? and ori.org_role_id=erm.org_role_id and dm.status=1 and ori.department_id=dm.department_id")
							.setString(0, orgStrBean.getSfID()).list();
				} else {
					// Role Levels
					levels = session
							.createSQLQuery(
									"select distinct ori.role_hierarchy_id||'' hierarchyID from emp_role_mapping erm,org_role_instance ori where erm.status=1 and ori.status=1 and erm.org_role_id=ori.org_role_id and erm.sfid=?")
							.setString(0, orgStrBean.getSfID()).list();
				}

			}
			for (String levelID : levels) {
				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL)) {
					// Department Levels
					livelsList = session
							.createSQLQuery(
									"select id,name,parentName,parentID from (select dept_hierarchy_id id,dept_hierarchy_name name,'' parentName,parent_dept_hierarchy_id parentID from departments_hierarchy where dept_hierarchy_id=? and parent_dept_hierarchy_id=0 and status=1 "
											+ "union select dh1.dept_hierarchy_id id,dh1.dept_hierarchy_name name,dh2.dept_hierarchy_name parentName,dh1.parent_dept_hierarchy_id parentID from departments_hierarchy dh1,departments_hierarchy dh2 where dh1.status=1 and dh2.status=1 and "
											+ "dh1.parent_dept_hierarchy_id=dh2.dept_hierarchy_id and dh1.dept_hierarchy_id in (select dh.dept_hierarchy_id from departments_hierarchy dh where status=1 start with dh.parent_dept_hierarchy_id=? "
											+ "connect by dh.parent_dept_hierarchy_id = prior dh.dept_hierarchy_id)) order by parentID").addScalar("id", Hibernate.INTEGER).addScalar("name",
									Hibernate.STRING).addScalar("parentName", Hibernate.STRING).addScalar("parentID", Hibernate.INTEGER).setResultTransformer(
									Transformers.aliasToBean(OrgLevelsDTO.class)).setString(0, levelID).setString(1, levelID).list();

				} else {
					// Role Levels
					livelsList = session
							.createSQLQuery(
									"select id,name,parentName,parentID from (select role_hierarchy_id id,role_hierarchy_name name,'' parentName,parent_role_hierarchy_id parentID from org_role_hierarchy where parent_role_hierarchy_id=0 and status=1 and role_hierarchy_id=? "
											+ "union select orh1.role_hierarchy_id id,orh1.role_hierarchy_name name,orh2.role_hierarchy_name parentName,orh1.parent_role_hierarchy_id parentID from org_role_hierarchy orh1,org_role_hierarchy orh2 where orh1.status=1 and orh2.status=1 "
											+ "and orh1.parent_role_hierarchy_id=orh2.role_hierarchy_id and orh1.role_hierarchy_id in (select orh.role_hierarchy_id from org_role_hierarchy orh where orh.status=1 start with orh.parent_role_hierarchy_id=? "
											+ "connect by orh.parent_role_hierarchy_id= prior orh.role_hierarchy_id) ) order by parentID").addScalar("id", Hibernate.INTEGER).addScalar("name",
									Hibernate.STRING).addScalar("parentName", Hibernate.STRING).addScalar("parentID", Hibernate.INTEGER).setResultTransformer(
									Transformers.aliasToBean(OrgLevelsDTO.class)).setString(0, levelID).setString(1, levelID).list();

				}

				if (status) {
					// For the first time we should add entire list
					status = false;
					orgStrBean.setLevelsList(livelsList);
				} else {
					// Check whether already level added or not
					for (OrgLevelsDTO nlevelDTO : livelsList) {
						flag = true;
						for (OrgLevelsDTO llevelDTO : orgStrBean.getLevelsList()) {
							if (llevelDTO.getId() == nlevelDTO.getId()) {
								// Already level added
								flag = false;
								break;
							}
						}
						if (flag) {
							orgStrBean.getLevelsList().add(nlevelDTO);
						}
					}
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	public String checkDuplicate(String tableName, String columnName, String columnValue, String uniqueColumnName, String levelID) throws Exception {
		String result = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if(columnValue.contains("'")){
				columnValue=columnValue.replaceAll("'", "||'||'");
			}
			sb.append("select count(*) from " + tableName + " where upper(" + columnName + ")=upper('" + columnValue + "') and status=1");
			if (!CPSUtils.isNullOrEmpty(levelID)) {
				sb.append(" and " + uniqueColumnName + "!=" + levelID);
			}

			if (CPSUtils.compareStrings(session.createSQLQuery(sb.toString()).uniqueResult().toString(), "0")) {
				result = CPSConstants.SUCCESS;
			} else {
				result = CPSConstants.DUPLICATE;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	@Override
	public OrgStructureBean submitLevels(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL)) {
				DeptHierarchyDTO deptHierarchyDTO = new DeptHierarchyDTO();
				if (!CPSUtils.isNullOrEmpty(orgStrBean.getLevelID())) {
					deptHierarchyDTO.setId(Integer.valueOf(orgStrBean.getLevelID()));
				}
				deptHierarchyDTO.setHierarchyName(orgStrBean.getNewLevel());
				if(deptHierarchyDTO.getId()==1){
					deptHierarchyDTO.setParentID(0);
				}else{
				   deptHierarchyDTO.setParentID(Integer.valueOf(orgStrBean.getParentLevel()));
				}
				deptHierarchyDTO.setStatus(1);
				deptHierarchyDTO.setCreationDate(CPSUtils.getCurrentDate());
				deptHierarchyDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(deptHierarchyDTO);
			} else {
				HierarchyDTO hierarchyDTO = new HierarchyDTO();
				if (!CPSUtils.isNullOrEmpty(orgStrBean.getLevelID())) {
					hierarchyDTO.setRoleID(Integer.valueOf(orgStrBean.getLevelID()));
				}
				hierarchyDTO.setRoleName(orgStrBean.getNewLevel());
				if(hierarchyDTO.getRoleID()==1){
					hierarchyDTO.setParentID(0);
				}else{
					hierarchyDTO.setParentID(Integer.valueOf(orgStrBean.getParentLevel()));
				}
				
				hierarchyDTO.setStatus(1);
				hierarchyDTO.setCreationDate(CPSUtils.getCurrentDate());
				hierarchyDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(hierarchyDTO);
			}
			session.flush();//tx.commit() ;
			orgStrBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean deleteOrgStructure(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();
			
			orgStrBean.setRemarks("");
			if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL)) {
				// Department Level
				// Check whether any other level attached to this level
				List<DeptHierarchyDTO> levellist = session.createCriteria(DeptHierarchyDTO.class).add(Expression.eq("parentID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(levellist)) {
					// other level is mapped with this, So display those levels
					orgStrBean.setResult(CPSConstants.FAILED);
					for (DeptHierarchyDTO deptDTO : levellist) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + deptDTO.getHierarchyName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Level(s) mapped with this level.");
				}

				// Check whether any department mapped with this level
				List<DepartmentsDTO> deptlist = session.createCriteria(DepartmentsDTO.class).add(Expression.eq("hierarchyID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(deptlist)) {
					// other department is mapped with this, So display those department
					if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
					} else {
						orgStrBean.setResult(CPSConstants.FAILED);
					}

					for (DepartmentsDTO deptDTO : deptlist) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + deptDTO.getDeptName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Department(s) mapped with this level.");
				}
				if (!CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
					// No level is mapped, so we can delete
					DeptHierarchyDTO deptHirarchyDTO = (DeptHierarchyDTO) session.createCriteria(DeptHierarchyDTO.class).add(Expression.eq("id", Integer.valueOf(orgStrBean.getDeleteID()))).add(
							Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
					deptHirarchyDTO.setStatus(0);
					deptHirarchyDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(deptHirarchyDTO);
					orgStrBean.setResult(CPSConstants.SUCCESS);
				}
			} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLELEVEL)) {
				// Role Level
				// Check whether any other level attached to this level
				List<HierarchyDTO> list = session.createCriteria(HierarchyDTO.class).add(Expression.eq("parentID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(list)) {
					// other level is mapped with this, So display those levels
					orgStrBean.setResult(CPSConstants.FAILED);

					for (HierarchyDTO roleDTO : list) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + roleDTO.getRoleName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Level(s) mapped with this level.");
				}

				// Check whether any role attached to this level
				List<OrgInstanceDTO> roleslist = session.createCriteria(OrgInstanceDTO.class).add(Expression.eq("roleID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(roleslist)) {
					// Role is mapped with this, So display those Roles
					if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
					} else {
						orgStrBean.setResult(CPSConstants.FAILED);
					}
					for (OrgInstanceDTO roleDTO : roleslist) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + roleDTO.getName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Role(s) mapped with this level.");
				}

				if (!CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
					// No level is mapped, so we can delete
					HierarchyDTO roleHirarchyDTO = (HierarchyDTO) session.createCriteria(HierarchyDTO.class).add(Expression.eq("roleID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
							Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
					roleHirarchyDTO.setStatus(0);
					roleHirarchyDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(roleHirarchyDTO);
					orgStrBean.setResult(CPSConstants.SUCCESS);
				}
			} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENT)) {
				// Department
				// Check whether any other child department attached to this department
				List<DepartmentsDTO> deptList = session.createCriteria(DepartmentsDTO.class).add(Expression.eq("parentID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(deptList)) {
					// other department is mapped with this, So display those department
					orgStrBean.setResult(CPSConstants.FAILED);

					for (DepartmentsDTO deptDTO : deptList) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + deptDTO.getDeptName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Department(s) mapped with this department.");
				}

				// Check whether any role is depends on this department
				List<OrgInstanceDTO> roleslist = session.createCriteria(OrgInstanceDTO.class).add(Expression.eq("departmentID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(roleslist)) {
					// Role is mapped with this, So display those Roles
					if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
					} else {
						orgStrBean.setResult(CPSConstants.FAILED);
					}
					for (OrgInstanceDTO roleDTO : roleslist) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + roleDTO.getName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Role(s) mapped with this department.");
				}
				if (!CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
					// No department is mapped, so we can delete
					DepartmentsDTO deptDTO = (DepartmentsDTO) session.createCriteria(DepartmentsDTO.class).add(Expression.eq("id", Integer.valueOf(orgStrBean.getDeleteID()))).add(
							Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
					deptDTO.setStatus(0);
					deptDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(deptDTO);
					orgStrBean.setResult(CPSConstants.SUCCESS);
				}
			} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLE)) {
				// Role
				// Check whether any other roles attached to this role (child)
				List<OrgInstanceDTO> childList = session.createCriteria(OrgInstanceDTO.class).add(Expression.eq("parentID", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(childList)) {
					// other roles is mapped with this, So display those roles
					orgStrBean.setResult(CPSConstants.FAILED);

					for (OrgInstanceDTO deptDTO : childList) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + deptDTO.getName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Roles(s) mapped with this role.");
				}

				// Check whether any employee assigned to this role
				List<EmpRoleMappingDTO> roleList = session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq("roleInstanceId", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(roleList)) {
					// An employee is mapped to this role, So display those sfid
					if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
					} else {
						orgStrBean.setResult(CPSConstants.FAILED);
					}

					for (EmpRoleMappingDTO roleDTO : roleList) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + roleDTO.getSfid() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " employee mapped with this role.");
				   
				}

				// Check whether any employee delegated his requests to this role
				List<UserSpecificDTO> delegateList = session.createCriteria(UserSpecificDTO.class).add(Expression.eq("delegate", orgStrBean.getDeleteID())).add(Expression.eq(CPSConstants.STATUS, 1))
						.list();
				if (CPSUtils.checkList(delegateList)) {
					// An employee is mapped to this role, So display those sfid
					if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
					} else {
						orgStrBean.setResult(CPSConstants.FAILED);
					}

					for (UserSpecificDTO delegateDTO : delegateList) {
						OrgInstanceDTO orgRoles = (OrgInstanceDTO) session.get(OrgInstanceDTO.class, delegateDTO.getRoleMapId());
						if (!orgStrBean.getRemarks().contains(orgRoles.getName())) {
							orgStrBean.setRemarks(orgStrBean.getRemarks() + orgRoles.getName() + ", ");
						}
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " delegated his/her request(s) to this role.");
				}

				// Check whether this role is head for that department, if so we should assign an head for this department first then we can able to delete
				OrgInstanceDTO orgInstanceDTO = (OrgInstanceDTO) session.get(OrgInstanceDTO.class, Integer.valueOf(orgStrBean.getDeleteID()));
				if (CPSUtils.checkList(roleList)) {
				if (orgInstanceDTO.getIsHead() == 1) {
					// THis role is head for the department, so display the message
					
					if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
					} else {
						orgStrBean.setResult(CPSConstants.FAILED);
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks() + "This Role is head of the department, So please assign head of the department first. ");
				 }
				}
				if (!CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
					// All the constraints satisfied, so we can delete
					orgInstanceDTO.setStatus(0);
					orgInstanceDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(orgInstanceDTO);
					orgStrBean.setResult(CPSConstants.DELETE);
				}
			}

			else if(CPSUtils.compareStrings(orgStrBean.getType(), "organizations")) 
			{

				// Role
				// Check whether any other roles attached to this role (child)
				List<OrganizationsDTO> childList = session.createCriteria(OrganizationsDTO.class).add(Expression.eq("id", Integer.valueOf(orgStrBean.getDeleteID()))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list();
				OrganizationsDTO OrganizationsDTO = childList.get(0);
				if (CPSUtils.checkList(childList)) {
					// other roles is mapped with this, So display those roles
					orgStrBean.setResult(CPSConstants.FAILED);

					for (OrganizationsDTO dto : childList) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + dto.getName() + ", ");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Roles(s) mapped with this role.");
				}
			
				if (!CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
					// All the constraints satisfied, so we can delete
					OrganizationsDTO.setStatus(0);
					OrganizationsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(OrganizationsDTO);
					orgStrBean.setResult(CPSConstants.DELETE);
				}
			
				
			}
			
		
			session.flush();
		} catch (Exception e) {
			
			throw e;
		} finally {
			 session.flush();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean getDepartments(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		List<String> roles = new ArrayList<String>();
		StringBuffer sb = null;
		boolean status = true;
		boolean flag = true;
		String hierarchyLevels = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			// For super admin start id=1, For other get their departments first then give the list
			if (orgStrBean.getEmpRoles().contains("ROLE_ADMIN") || orgStrBean.getEmpRoles().contains("ROLE_SUPERADMIN")) {
				roles.add(CPSConstants.ROLE_SUPERADMIN);
			} else {
				roles = session.createSQLQuery(
						"select ori.department_id||'' from emp_role_mapping erm,org_role_instance ori where erm.sfid=? and erm.status=1 and ori.status=1 and ori.org_role_id=erm.org_role_id")
						.setString(0, orgStrBean.getSfID()).list();
			}

			if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPTREPORTSTO)) {
				// When user selects hierarchy level we should display the upper tree level departments
				hierarchyLevels = (String) session
						.createSQLQuery(
								"select rtrim (xmlagg (xmlelement (e, dept_hierarchy_id || ',')).extract ('//text()'), ',') from departments_hierarchy where status=1  and dept_hierarchy_id!=? start with dept_hierarchy_id=? connect by dept_hierarchy_id = prior parent_dept_hierarchy_id")
						.setString(0, orgStrBean.getParentLevel()).setString(1, orgStrBean.getParentLevel()).uniqueResult();

				if (CPSUtils.isNullOrEmpty(hierarchyLevels)) {
					hierarchyLevels = "''";
				}
			}

			for (String roleID : roles) {
				sb = new StringBuffer();
				sb
						.append("select id,description,fax,phoneNumber,email,hierarchyID,LPAD('-', level, '-') || deptName levelDeptName,deptName,parentID,hierarchyName,parentName,departmentTypeId,departmentTypeName from ( select dm.department_id id,dm.description description,dm.fax fax,dm.phone_number phoneNumber,dm.email email,dm.dept_hierarchy_id hierarchyID,dm.department_name deptName,"
								+ "dm.parent_department_id parentID,dh.dept_hierarchy_name hierarchyName,'' parentName,dm.department_type_id departmentTypeId,dtm.name departmentTypeName from departments_master dm,departments_hierarchy dh,department_type_master dtm "
								+ "where dm.status=1 and dm.department_id=? and dm.parent_department_id=0 and dh.status=1 and dh.dept_hierarchy_id=dm.dept_hierarchy_id and dtm.status=1 and dtm.id=dm.department_type_id union select dm1.department_id id,dm1.description description,dm1.fax fax,dm1.phone_number phoneNumber,dm1.email email,"
								+ "dm1.dept_hierarchy_id hierarchyID,dm1.department_name deptName,dm1.parent_department_id parentID,dh.dept_hierarchy_name hierarchyName,dm2.department_name parentName,dm1.department_type_id departmentTypeId,dtm.name departmentTypeName from departments_master dm1,departments_master dm2,"
								+ "departments_hierarchy dh,department_type_master dtm where dh.status=1 and dh.dept_hierarchy_id=dm1.dept_hierarchy_id and dtm.status=1 and dtm.id=dm1.department_type_id and dm2.status=1 and dm1.status=1 and dm1.parent_department_id=dm2.department_id ");

				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPTREPORTSTO)) {
					sb.append("and dm1.dept_hierarchy_id in (" + hierarchyLevels + ") ");
				}
				sb.append(") start with id=? connect by parentID = prior id ");

				List<DepartmentsDTO> deptList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("hierarchyID", Hibernate.INTEGER).addScalar("levelDeptName",
						Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("parentID", Hibernate.INTEGER).addScalar("hierarchyName", Hibernate.STRING).addScalar("parentName",
						Hibernate.STRING).addScalar("departmentTypeId", Hibernate.INTEGER).addScalar("departmentTypeName", Hibernate.STRING).addScalar("description",Hibernate.STRING).addScalar("fax", Hibernate.STRING).addScalar("phoneNumber",Hibernate.STRING).addScalar("email", Hibernate.STRING)
						.setResultTransformer(
						Transformers.aliasToBean(DepartmentsDTO.class)).setString(0, roleID).setString(1, roleID).list();
				if (status) {
					status = false;
					orgStrBean.setDepartmentsList(deptList);
				} else {
					// Check whether already department added or not
					for (DepartmentsDTO ndeptDTO : deptList) {
						flag = true;
						for (DepartmentsDTO ldeptDTO : orgStrBean.getDepartmentsList()) {
							if (ldeptDTO.getId() == ndeptDTO.getId()) {
								// Already department added
								flag = false;
								break;
							}
						}
						if (flag) {
							orgStrBean.getDepartmentsList().add(ndeptDTO);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	public OrgStructureBean submitDepartment(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			DepartmentsDTO deptDTO = new DepartmentsDTO();

			if (!CPSUtils.isNullOrEmpty(orgStrBean.getNodeID())) {
				deptDTO.setId(Integer.valueOf(orgStrBean.getNodeID()));
			}
			deptDTO.setHierarchyID(Integer.valueOf(orgStrBean.getParentLevel()));
			deptDTO.setDeptName(orgStrBean.getDepartmentName());
			if(deptDTO.getId()==1){
				deptDTO.setParentID(0);	
			}else{
				deptDTO.setParentID(Integer.valueOf(orgStrBean.getParentDept()));
			}
			deptDTO.setDescription(orgStrBean.getDescription());
			deptDTO.setFax(orgStrBean.getFax());
			deptDTO.setPhoneNumber(orgStrBean.getPhoneNumber());
			deptDTO.setEmail(orgStrBean.getEmail());
			deptDTO.setStatus(1);
			deptDTO.setCreationDate(CPSUtils.getCurrentDate());
			deptDTO.setLastModifiedDate(deptDTO.getCreationDate());
			deptDTO.setDepartmentTypeId(Integer.valueOf(orgStrBean.getDepartmentTypeId()));
			session.saveOrUpdate(deptDTO);
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean getRolesList(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		List<String> roles = new ArrayList<String>();
		boolean status = true;
		boolean flag = true;
		String hierarchyLevels = "";
		StringBuffer sb = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			
			
			/// For Role Admin also adding links  according requirement:rajendra
			if (orgStrBean.getEmpRoles().contains("ROLE_ADMIN") || orgStrBean.getEmpRoles().contains("ROLE_SUPERADMIN")  || orgStrBean.getEmpRoles().contains("ROLE_LEAVE_TASK_HOLDER1")) {
				roles.add(CPSConstants.ROLE_SUPERADMIN);
			////Rajendra	
				
			} else {
				roles = session.createSQLQuery(
						"select ori.org_role_id||'' from org_role_instance ori,emp_role_mapping erm where ori.status=1 and erm.status=1 and erm.org_role_id=ori.org_role_id and erm.sfid=?").setString(
						0, orgStrBean.getSfID()).list();
			}
			if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLEREPORTSTO)) {
				// When user selects hierarchy level we should display the upper tree level roles
				hierarchyLevels = (String) session
						.createSQLQuery(
								"select rtrim (xmlagg (xmlelement (e, role_hierarchy_id || ',')).extract ('//text()'), ',') from org_role_hierarchy where status=1 and role_hierarchy_id!=? start with role_hierarchy_id=? connect by role_hierarchy_id = prior parent_role_hierarchy_id")
						.setString(0, orgStrBean.getParentLevel()).setString(1, orgStrBean.getParentLevel()).uniqueResult();

				if (CPSUtils.isNullOrEmpty(hierarchyLevels)) {
					hierarchyLevels = "''";
				}
			}
			for (String roleID : roles) {
				sb = new StringBuffer();
				sb
						.append("select id,roleName,LPAD(' ', level, '- ') || roleName levelRoleName,hierarchyID,hierarchyName,parentID,parentName,departmentID,departmentName,isHead from (select ori.org_role_id id,ori.org_role_name roleName,ori.role_hierarchy_id hierarchyID,"
								+ "orh.role_hierarchy_name hierarchyName,ori.parent_org_role_id parentID,'' parentName,ori.department_id departmentID,dm.department_name departmentName,ori.is_head isHead from org_role_instance ori,org_role_hierarchy orh,departments_master dm where ori.status=1 "
								+ "and ori.org_role_id=? and ori.parent_org_role_id=0 and orh.status=1 and orh.role_hierarchy_id=ori.role_hierarchy_id and dm.status=1 and dm.department_id=ori.department_id union select ori1.org_role_id id,ori1.org_role_name roleName,ori1.role_hierarchy_id hierarchyID,"
								+ "orh.role_hierarchy_name hierarchyName,ori1.parent_org_role_id parentID,ori2.org_role_name parentName,ori1.department_id departmentID,dm.department_name departmentName,ori1.is_head isHead from org_role_instance ori1,org_role_instance ori2,org_role_hierarchy orh,"
								+ "departments_master dm where ori1.status=1 and ori2.status=1 and ori1.parent_org_role_id=ori2.org_role_id and orh.status=1 and orh.role_hierarchy_id=ori1.role_hierarchy_id and dm.status=1 and dm.department_id=ori1.department_id ");

				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLEREPORTSTO)) {
					sb.append("and ori1.role_hierarchy_id in (" + hierarchyLevels + ") ");
				}
				sb.append(") start with id=? connect by parentID = prior id  ");

				List<OrgInstanceDTO> rolesList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("roleName", Hibernate.STRING).addScalar("levelRoleName",
						Hibernate.STRING).addScalar("hierarchyID", Hibernate.INTEGER).addScalar("parentID", Hibernate.INTEGER).addScalar("hierarchyName", Hibernate.STRING).addScalar("parentName",
						Hibernate.STRING).addScalar("departmentID", Hibernate.INTEGER).addScalar("departmentName", Hibernate.STRING).addScalar("isHead", Hibernate.INTEGER).setResultTransformer(
						Transformers.aliasToBean(OrgInstanceDTO.class)).setString(0, roleID).setString(1, roleID).list();
				if (status) {
					status = false;
					orgStrBean.setRolesList(rolesList);
				} else {
					// Check whether already role added or not
					for (OrgInstanceDTO nroleDTO : rolesList) {
						flag = true;
						for (OrgInstanceDTO lroleDTO : orgStrBean.getRolesList()) {
							if (lroleDTO.getId() == nroleDTO.getId()) {
								// Already role added
								flag = false;
								break;
							}
						}
						if (flag) {
							orgStrBean.getRolesList().add(nroleDTO);
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	public OrgStructureBean submitRole(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			OrgInstanceDTO roleDTO = new OrgInstanceDTO();

			if (!CPSUtils.isNullOrEmpty(orgStrBean.getNodeID())) {
				roleDTO.setId(Integer.valueOf(orgStrBean.getNodeID()));
			}
			roleDTO.setRoleID(Integer.valueOf(orgStrBean.getParentLevel()));
			roleDTO.setName(orgStrBean.getRoleName());
			if(roleDTO.getId()==1){
				roleDTO.setParentID(0);
			}else{
				roleDTO.setParentID(Integer.valueOf(orgStrBean.getParentRole()));
			}	
			roleDTO.setStatus(1);
			roleDTO.setCreationDate(CPSUtils.getCurrentDate());
			roleDTO.setLastModifiedDate(roleDTO.getCreationDate());
			roleDTO.setDepartmentID(Integer.valueOf(orgStrBean.getDepartmentId()));
			roleDTO.setIsHead(Integer.valueOf(orgStrBean.getIsHead()));

			session.saveOrUpdate(roleDTO);

			if (!CPSUtils.isNullOrEmpty(orgStrBean.getHeadID())) {
				OrgInstanceDTO orgInstanceDTO = (OrgInstanceDTO) session.get(OrgInstanceDTO.class, Integer.valueOf(orgStrBean.getHeadID()));
				orgInstanceDTO.setIsHead(0);
				orgInstanceDTO.setLastModifiedDate(roleDTO.getCreationDate());
				session.saveOrUpdate(orgInstanceDTO);
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean normalEmployeesDetails(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		String roles = "";
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			sb
					.append("select erm.id,erm.sfid empSfid,emp1.name_in_service_book empName,desig.name designation,dm.department_name department,erm.parent_id parentSfid,emp2.name_in_service_book parentName,case when erm.parent_role_id = emp1.office_id then 'YES' else ' NO' end defaultStatus "
							+ "from emp_role_mapping erm,emp_master emp1,emp_master emp2,designation_master desig,org_role_instance ori,departments_master dm where erm.status=1 and erm.org_role_id is null "
							+ "and emp1.status=1 and emp1.sfid=erm.sfid and emp2.status=1 and emp2.sfid=erm.parent_id and desig.status=1 and desig.id=emp1.designation_id and ori.status=1 and erm.parent_role_id=ori.org_role_id "
							+ "and dm.status=1 and dm.department_id=ori.department_id ");

			if (!(orgStrBean.getEmpRoles().contains("ROLE_ADMIN") || orgStrBean.getEmpRoles().contains("ROLE_SUPERADMIN"))) {
				// If the user other than super admin or admin, we should get their roles first
				List<OrgInstanceDTO> rolesList = orgStrBean.getRolesList();
				for (OrgInstanceDTO role : rolesList) {
					roles += role.getId() + ",";
				}
				roles = roles.substring(0, roles.length() - 1);

				sb.append(" and emp1.office_id in (" + roles + ") ");
			}

			// Searching
			if (!CPSUtils.isNullOrEmpty(orgStrBean.getSearchingWith()) && !CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "0")) {
				// User is searching the values
				if (CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "sfid")) {
					sb.append(" and emp1.sfid ='" + orgStrBean.getSearchBox().toUpperCase() + "' ");
				} else if (CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "name")) {
					if (CPSUtils.compareStrings(orgStrBean.getSearchingName(), "contains")) {
						sb.append(" and emp1.name_in_service_book like initcap('%" + orgStrBean.getSearchBox() + "%') ");
					} else {
						sb.append(" and emp1.name_in_service_book like initcap('%" + orgStrBean.getSearchBox().toUpperCase() + "%') ");
					}
				} else if (CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "dept")) {
					sb.append(" and dm.department_id ='" + orgStrBean.getSearchDept() + "' ");
				}

			}
			sb.append(" order by defaultStatus,desig.order_no");
			orgStrBean.setNormalEmpDetails(session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("empSfid", Hibernate.STRING).addScalar("empName", Hibernate.STRING)
					.addScalar("designation", Hibernate.STRING).addScalar("department", Hibernate.STRING).addScalar("parentSfid", Hibernate.STRING).addScalar("parentName", Hibernate.STRING).addScalar("defaultStatus",Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(EmployeeDetailsDTO.class)).list());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean deleteEmployees(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		String sql = null;
	String 	Sql2 = null;
		String undeletedSfids = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			// Check employee dependencies
			orgStrBean = checkDependencies(orgStrBean);

			if (!CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
				orgStrBean.setResult(CPSConstants.SUCCESS);
				// All the constraints satisfied, so we can delete the employee
				String currentDate = CPSUtils.getCurrentDate();
				for (String sfid : orgStrBean.getEmpGroup().split(",")) {
					// Get the details from emp role mapping
					List<EmpRoleMappingDTO> empROleMapList = session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("sfid", sfid)).add(
							Expression.isNull("roleInstanceId")).list();
					for (EmpRoleMappingDTO empRoleDTO : empROleMapList) {
						// Check whether this employee's default role is same or not, If the default role is same we should give an alert
						
						 Sql2="select count(*) from emp_role_mapping erm,emp_master emp where erm.sfid=? and erm.status=1 and emp.status=1 and emp.sfid=erm.sfid "
						  +"and erm.parent_role_id=emp.office_id and erm.org_role_id is null";
						 
						sql = "select count(*) from emp_role_mapping where sfid=? and org_role_id is not null and status=1";
						
						
						// In case Default Role From Default Role Screen For restricting Not to delete without reassigning  Primary Role
						//Sql2  = "select to_char(parent_role_id)  from emp_role_mapping where sfid= ?  and status=1" ;
						
						
						
						if (Integer.valueOf(session.createSQLQuery(sql).setString(0, empRoleDTO.getSfid()).uniqueResult().toString()) > 0    && !(Integer.valueOf(session.createSQLQuery(Sql2).setString(0, empRoleDTO.getSfid()).uniqueResult().toString()) > 0)) {
							empRoleDTO.setLastModifiedDate(currentDate);
							empRoleDTO.setStatus(0);
							session.saveOrUpdate(empRoleDTO);
						} else {
							orgStrBean.setResult(CPSConstants.FAILED);
							undeletedSfids = undeletedSfids + empRoleDTO.getSfid() + " ";
						}

					}

					// Employee should not be delete permanently

					// get the details from emp_master
					// EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, sfid);
					// empBean.setLastModifiedDate(currentDate);
					// empBean.setLastModifiedBy(orgStrBean.getSfID());
					// empBean.setStatus(0);
					// empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
					// empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
					// empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
					// empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
					// empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
					// empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
					// empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
					//
					// session.saveOrUpdate(empBean);
					// get the details from users table
					// LoginBean loginBean = (LoginBean) session.createCriteria(LoginBean.class).add(Expression.eq("userId", sfid)).uniqueResult();
					// loginBean.setStatus(CPSConstants.ZERO);
					// session.saveOrUpdate(loginBean);
				}
				if (!CPSUtils.isNullOrEmpty(undeletedSfids)) {
					orgStrBean.setRemarks(orgStrBean.getRemarks() + " Please assign default role(s) to " + undeletedSfids);
				}
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			orgStrBean.setResult(CPSConstants.FAILED);
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;

	}

	@SuppressWarnings("unchecked")
	public OrgStructureBean checkDependencies(OrgStructureBean orgStrBean) throws Exception {
		Session session1 = null;
		try {
			session1 = hibernateUtils.getSession();
			orgStrBean.setRemarks("");

			for (String sfid : orgStrBean.getEmpGroup().split(",")) {
				// Check whether any employee delegated his requests to this sfid
				List<UserSpecificDTO> delegateList = session1.createCriteria(UserSpecificDTO.class).add(Expression.eq("delegate", sfid)).add(Expression.eq(CPSConstants.STATUS, 1)).list();
				if (CPSUtils.checkList(delegateList)) {
					// employee is mapped to this sfid, So display those sfid
					if (CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
					} else {
						orgStrBean.setResult(CPSConstants.FAILED);
					}

					for (UserSpecificDTO delegateDTO : delegateList) {
						OrgInstanceDTO orgRoles = (OrgInstanceDTO) session1.get(OrgInstanceDTO.class, delegateDTO.getRoleMapId());
						if (!orgStrBean.getRemarks().contains(orgRoles.getName() + " role delegating his/her requests to " + sfid)) {
							orgStrBean.setRemarks(orgStrBean.getRemarks() + orgRoles.getName() + " role delegating his/her requests to " + sfid);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean submitEmployees(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			orgStrBean.setRemarks("");
			// Check employee dependencies
			if (!CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.PROCEED)) {
				orgStrBean = checkDependencies(orgStrBean);
			}

			if (!CPSUtils.compareStrings(orgStrBean.getResult(), CPSConstants.FAILED)) {
				// All the constraints satisfied, so we can move the employee
				String sfidParentRoleID = (String) session.createSQLQuery(
						"select erm.sfid||'#'||case when ori.parent_org_role_id=0 then 1 else ori.parent_org_role_id end from emp_role_mapping erm,org_role_instance ori "
								+ "where erm.status=1 and erm.org_role_id=? and ori.status=1 and ori.org_role_id=erm.org_role_id").setString(0, orgStrBean.getParentRole()).uniqueResult();
				String currentDate = CPSUtils.getCurrentDate();
				if (!CPSUtils.isNull(sfidParentRoleID)) {
					for (String sfid : orgStrBean.getEmpGroup().split(",")) {
						// get the parent role id & sfid assigned to selected role

						// change directorateID & officeID in emp_master table
						EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, sfid);
						empBean.setDirectorate(Integer.valueOf(sfidParentRoleID.split("#")[1]));
						empBean.setOffice(Integer.valueOf(orgStrBean.getParentRole()));
						empBean.setLastModifiedDate(currentDate);
						empBean.setLastModifiedBy(orgStrBean.getSfID());
						empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
						empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
						empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
						empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
						empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
						empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
						empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
						empBean.setRetirementDate(CPSUtils.formattedDate(empBean.getRetirementDate()));
						empBean.setRetiredDate(CPSUtils.formatDate(CPSUtils.convertStringToDate((empBean.getRetiredDate()))));
						empBean.setUptoDate(CPSUtils.formattedDate(empBean.getUptoDate()));
						
						session.saveOrUpdate(empBean);
                        session.flush();
						// change parent_id,parent_role_id
						List<EmpRoleMappingDTO> empROleMapList = session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("sfid", sfid)).list();
						for (EmpRoleMappingDTO empRoleDTO : empROleMapList) {
							empRoleDTO.setLastModifiedDate(currentDate);
							empRoleDTO.setParentId(sfidParentRoleID.split("#")[0]);
							empRoleDTO.setParentRoleID(Integer.valueOf(orgStrBean.getParentRole()));
							session.saveOrUpdate(empRoleDTO);
							session.flush();
						}
					}
					orgStrBean.setResult(CPSConstants.SUCCESS);
					session.flush();//tx.commit() ;
				} else {
					// orgStrBean.setResult(CPSConstants.FAILED);
					orgStrBean.setRemarks("Please assign head for this role");
				}

			}

		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	public OrgStructureBean mapEmployee(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(CPSUtils.compareString(sqlCommonDAO.checkEmployee(orgStrBean.getMapSfID()),CPSConstants.SUCCESS)){
			sql = "select ori.org_role_name from emp_role_mapping erm2,org_role_instance ori "
					+ "where erm2.sfid=(select erm1.parent_id from emp_role_mapping erm1 where erm1.sfid=? and erm1.status=1 and erm1.org_role_id is null) "
					+ "and erm2.org_role_id=(select erm3.parent_role_id from emp_role_mapping erm3 where erm3.sfid=? and erm3.status=1 and erm3.org_role_id is null) "
					+ "and erm2.status=1  and ori.status=1 and erm2.org_role_id=ori.org_role_id";
			String roleName = (String) session.createSQLQuery(sql).setString(0, orgStrBean.getMapSfID()).setString(1, orgStrBean.getMapSfID()).uniqueResult();
			if (CPSUtils.isNull(roleName)) {
				String parentID = (String) session.createSQLQuery("select sfid from emp_role_mapping where status=1 and org_role_id=?").setString(0, orgStrBean.getParentRole()).uniqueResult();

				EmpRoleMappingDTO empRoleMappingDTO = new EmpRoleMappingDTO();
				empRoleMappingDTO.setSfid(orgStrBean.getMapSfID());
				empRoleMappingDTO.setStatus(1);
				empRoleMappingDTO.setInternalDivision(null);
				empRoleMappingDTO.setInternalRole("Employee");
				empRoleMappingDTO.setParentId(parentID);
				empRoleMappingDTO.setCreationDate(CPSUtils.getCurrentDate());
				empRoleMappingDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				empRoleMappingDTO.setParentRoleID(Integer.parseInt(orgStrBean.getParentRole()));
				session.saveOrUpdate(empRoleMappingDTO);
				session.flush();//tx.commit() ;
				orgStrBean.setResult(CPSConstants.SUCCESS);

				if (CPSUtils.compareStrings(orgStrBean.getIsDefault(), "1")  &&  CPSUtils.compareString(orgStrBean.getResult(), CPSConstants.SUCCESS)) {
					// Get the employee details
					EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, orgStrBean.getMapSfID());

					String sfidParentRoleID = (String) session.createSQLQuery(
							"select erm.sfid||'#'||case when ori.parent_org_role_id=0 then 1 else ori.parent_org_role_id end from emp_role_mapping erm,org_role_instance ori "
									+ "where erm.status=1 and erm.org_role_id=? and ori.status=1 and ori.org_role_id=erm.org_role_id").setString(0, orgStrBean.getParentRole()).uniqueResult();
					String currentDate = CPSUtils.getCurrentDate();
					if (!CPSUtils.isNull(sfidParentRoleID)) {
					
					// Get the directorate ID
					/*OrgInstanceDTO orgInstance = (OrgInstanceDTO) session.get(OrgInstanceDTO.class, Integer.valueOf(orgStrBean.getRoleName()));
					if (orgInstance.getParentID() == 0) {
						// Director
						orgInstance.setParentID(1);
					}*/
					empBean.setDirectorate(Integer.valueOf(sfidParentRoleID.split("#")[1]));
					empBean.setOffice(Integer.parseInt(orgStrBean.getParentRole()));
					empBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					empBean.setLastModifiedBy(orgStrBean.getSfID());
					empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
					empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
					empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
					empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
					empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
					empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
					empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
					session.clear();
					session.saveOrUpdate(empBean);
					session.flush();
				}
				}
				
				
				
				
				
			} else {
				orgStrBean.setRemarks(orgStrBean.getMapSfID() + " Is already an employee under " + roleName);
			}
			}else{
				orgStrBean.setResult(CPSConstants.INVALID);
			}
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean roleEmployeesDetails(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		List<String> roles = new ArrayList<String>();
		StringBuffer sb = null;
		boolean status = true;
		boolean flag = true;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (orgStrBean.getEmpRoles().contains("ROLE_ADMIN") || orgStrBean.getEmpRoles().contains("ROLE_SUPERADMIN")) {
				roles.add(CPSConstants.ROLE_SUPERADMIN);
			} else {
				roles = session.createSQLQuery(
						"select ori.org_role_id||'' from org_role_instance ori,emp_role_mapping erm where ori.status=1 and erm.status=1 and erm.org_role_id=ori.org_role_id and erm.sfid=?").setString(
						0, orgStrBean.getSfID()).list();
			}

			for (String roleID : roles) {
				sb = new StringBuffer();
				sb
						.append("select erm.id id,(select get_retirement_date(emp.sfid) as rDate from dual) retirementDate,emp.sfid empSfid,emp.name_in_service_book empName,dm.name designation,ori.org_role_name roleName,ori.org_role_id roleID,dept.department_name department,case when emp.office_id=ori.org_role_id then 1 else 0 end defRole from emp_role_mapping erm,emp_master emp,org_role_instance ori,designation_master dm,departments_master dept where erm.org_role_id in ("
								+ "select ori.org_role_id roleID from org_role_instance ori where ori.status=1 start with ori.org_role_id=? connect by ori.parent_org_role_id = prior ori.org_role_id) "
								+ "and erm.status=1 and emp.status=1 and emp.sfid=erm.sfid and ori.status=1 and ori.org_role_id=erm.org_role_id and dm.status=1 and dm.id=emp.designation_id and dept.status=1 and dept.department_id=ori.department_id ");

				// Searching
				if (!CPSUtils.isNullOrEmpty(orgStrBean.getSearchingWith()) && !CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "0")) {
					// User is searching the values
					if (CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "sfid")) {
						sb.append(" and emp.sfid ='" + orgStrBean.getSearchBox().toUpperCase() + "' ");
					} else if (CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "name")) {
						if (CPSUtils.compareStrings(orgStrBean.getSearchingName(), "contains")) {
							sb.append(" and emp.name_in_service_book like initcap('%" + orgStrBean.getSearchBox() + "%') ");
						} else {
							sb.append(" and emp.name_in_service_book like initcap('%" + orgStrBean.getSearchBox().toUpperCase() + "%') ");
						}
					} else if (CPSUtils.compareStrings(orgStrBean.getSearchingWith(), "dept")) {
						sb.append(" and dept.department_id ='" + orgStrBean.getSearchDept() + "' ");
					}
					//add role condition for searching
					if(!CPSUtils.isNullOrEmpty(orgStrBean.getRoleName())) {
					if(Integer.parseInt(orgStrBean.getRoleName())!=0){
						sb.append("and ori.org_role_id="+Integer.parseInt(orgStrBean.getRoleName()));
					}}
				}

				sb.append(" order by ori.role_hierarchy_id");

				List<EmployeeDetailsDTO> employeesList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("empSfid", Hibernate.STRING).addScalar("empName",
						Hibernate.STRING).addScalar("designation", Hibernate.STRING).addScalar("roleName", Hibernate.STRING).addScalar("department", Hibernate.STRING).addScalar("roleID",
						Hibernate.INTEGER).addScalar("defRole", Hibernate.INTEGER).addScalar("retirementDate",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeDetailsDTO.class)).setString(0, roleID).list();
				if (status) {
					status = false;
					orgStrBean.setRoleEmployeesList(employeesList);
				} else {
					// Check whether already role emp added or not
					for (EmployeeDetailsDTO nroleDTO : employeesList) {
						flag = true;
						for (EmployeeDetailsDTO lroleDTO : orgStrBean.getRoleEmployeesList()) {
							if (lroleDTO.getId() == nroleDTO.getId()) {
								// Already role emp added
								flag = false;
								break;
							}
						}
						if (flag) {
							orgStrBean.getRoleEmployeesList().add(nroleDTO);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean checkRoleAssigned(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			orgStrBean.setRemarks("");

			// User trying to assigning this role
			List<EmpRoleMappingDTO> list = session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
					Expression.eq("roleInstanceId", Integer.valueOf(orgStrBean.getRoleName()))).list();
			if (CPSUtils.checkList(list)) {
				orgStrBean.setResult(CPSConstants.FAILED);
				for (EmpRoleMappingDTO roleDTO : list) {
					orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br> Already " + roleDTO.getSfid() + " assigned to the role.");
				}
			} else {
				orgStrBean.setResult(CPSConstants.SUCCESS);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	public OrgStructureBean submitEmployeeRole(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (CPSUtils.isNullOrEmpty(orgStrBean.getNodeID())) {
				EmpRoleMappingDTO empRoleDTO = new EmpRoleMappingDTO();
				empRoleDTO.setSfid(orgStrBean.getMapSfID());
				empRoleDTO.setRoleInstanceId(Integer.valueOf(orgStrBean.getRoleName()));
				empRoleDTO.setStatus(1);
				empRoleDTO.setInternalRole(CPSConstants.HEAD);
				empRoleDTO.setCreationDate(CPSUtils.getCurrentDate());
				empRoleDTO.setLastModifiedDate(empRoleDTO.getCreationDate());
				empRoleDTO.setParentRoleID(empRoleDTO.getRoleInstanceId());
				session.saveOrUpdate(empRoleDTO);
			} else {
				// Updating the role
				EmpRoleMappingDTO empRoleDTO = (EmpRoleMappingDTO) session.get(EmpRoleMappingDTO.class, Integer.valueOf(orgStrBean.getNodeID()));
				//empRoleDTO.setSfid(orgStrBean.getMapSfID());
				// updating the existing record to status 0 as specified by navya on 23-04-14(By Rajendra)
				empRoleDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				empRoleDTO.setStatus(0);
				session.saveOrUpdate(empRoleDTO);
		         session.flush();
				// (Author Rajendra)
				// inserting new record with changed details as specified by navya on 23-04-14(By Rajendra)
				EmpRoleMappingDTO empRoleDTO1 = new EmpRoleMappingDTO();
				empRoleDTO1.setSfid(orgStrBean.getMapSfID());
				empRoleDTO1.setRoleInstanceId(empRoleDTO.getRoleInstanceId());
				empRoleDTO1.setStatus(1);
				empRoleDTO1.setInternalRole(CPSConstants.HEAD);
				empRoleDTO1.setCreationDate(CPSUtils.getCurrentDate());
				empRoleDTO1.setLastModifiedDate(empRoleDTO1.getCreationDate());
				empRoleDTO1.setParentRoleID(empRoleDTO.getParentRoleID());
				session.saveOrUpdate(empRoleDTO1);
				session.flush(); 
				//(Author Rajendra)
			}
			session.flush();
			// change the default role , if the user checks is default check box
			if (CPSUtils.compareStrings(orgStrBean.getIsDefault(), "1")) {
				// Get the employee details
				EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, orgStrBean.getMapSfID());

				// Get the directorate ID
				OrgInstanceDTO orgInstance = (OrgInstanceDTO) session.get(OrgInstanceDTO.class, Integer.valueOf(orgStrBean.getRoleName()));
				if (orgInstance.getParentID() == 0) {
					// Director
					orgInstance.setParentID(1);
				}
				empBean.setDirectorate(orgInstance.getParentID());
				empBean.setOffice(Integer.valueOf(orgStrBean.getRoleName()));
				empBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				empBean.setLastModifiedBy(orgStrBean.getSfID());
				empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
				empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
				empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
				empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
				empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
				empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
				empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
				session.clear();
				session.saveOrUpdate(empBean);
			}

			
			checkDuplicateRecords(orgStrBean);
			orgStrBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			
			throw e;
		} finally {
			session.flush();
		}
		return orgStrBean;
	}

	/**
	 * While assigning new role to an normal employee check assigning role and previous employee departments are same then delete the employee details from emp_role_mapping table
	 * 
	 * @param orgStrBean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void checkDuplicateRecords(OrgStructureBean orgStrBean) throws Exception {
		Session session1 = null;
		Transaction tx1 = null;
		List<String> employeeList = null;
		try {
			session1 = hibernateUtils.getSession();
			//tx1 = session1.beginTransaction();
			String sql = "select ori.department_id||'' from org_role_instance ori where ori.org_role_id in( select parent_role_id from emp_role_mapping where sfid=? and status=1) and ori.status=1";
			employeeList = session1.createSQLQuery(sql).setString(0, orgStrBean.getMapSfID()).list();
			if (employeeList.size() > 1) {
				if (CPSUtils.compareStrings(employeeList.get(0), employeeList.get(1))) {
					sql = "update emp_role_mapping set status=0 where sfid=? and status=1 and org_role_id is null";
					session1.createSQLQuery(sql).setString(0, orgStrBean.getMapSfID()).executeUpdate();
					session1.flush(); //tx1.commit();
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
	}

	@Override
	public OrgStructureBean assignAsNormalEmployee(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			if (!CPSUtils.compareStrings(orgStrBean.getMapSfID(), orgStrBean.getEditSfid())) {
				// User trying to assigning other employee for this role

				// Get the employee default role, if the default role is same then we should give an alert
				EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, orgStrBean.getEditSfid());
				
				String retirementDate =  session.createSQLQuery("select to_char(get_retirement_date ('"+orgStrBean.getEditSfid()+"')) from dual").uniqueResult().toString();
					
				if (empBean.getOffice() == Integer.valueOf(orgStrBean.getRoleName())  &&  !(CPSUtils.compareTwoDatesUptoDate((CPSUtils.convertStringToDate(CPSUtils.getCurrentDate())),(CPSUtils.convertStringToDate(retirementDate))) ==1 ) ) {
					// give an alert
					orgStrBean.setResult(CPSConstants.FAILED);
					if (CPSUtils.isNullOrEmpty(orgStrBean.getRemarks())) {
						orgStrBean.setRemarks("");
					}
					orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>Please re-assign default role first.");

				} else {
					// We should assign the old employee as a normal employee in the upper level & if his default role is same we should update that role also
					String parentID = (String) session
							.createSQLQuery(
									"select erm.sfid||'#'||ori.parent_org_role_id from org_role_instance ori,emp_role_mapping erm where ori.org_role_id=? and ori.status=1 and erm.status=1 and erm.org_role_id=ori.parent_org_role_id")
							.setString(0, orgStrBean.getRoleName()).uniqueResult();

					if (CPSUtils.isNullOrEmpty(parentID)) {
						// Assigning the role for director
						parentID = orgStrBean.getMapSfID() + "#" + orgStrBean.getRoleName();
					}

					// Default role is some other
					if (CPSUtils.isNullOrEmpty(orgStrBean.getMapSfID())) {
						// Deleting user from the role
						EmpRoleMappingDTO empRoleDTO = (EmpRoleMappingDTO) session.get(EmpRoleMappingDTO.class, Integer.valueOf(orgStrBean.getNodeID()));
						empRoleDTO.setStatus(0);
						empRoleDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						session.saveOrUpdate(empRoleDTO);
					}
					if (!CPSUtils.compareStrings(parentID.split("#")[0], orgStrBean.getEditSfid()) && checkAsNormalEmployee(orgStrBean.getEditSfid())) {
						// Upper department head is other person, so we can assign as subordinate to that person
						EmpRoleMappingDTO empRoleDTO = new EmpRoleMappingDTO();
						empRoleDTO.setSfid(orgStrBean.getEditSfid());
						empRoleDTO.setStatus(1);
						empRoleDTO.setInternalRole("Employee");
						empRoleDTO.setParentId(parentID.split("#")[0]);
						empRoleDTO.setCreationDate(CPSUtils.getCurrentDate());
						empRoleDTO.setLastModifiedDate(empRoleDTO.getCreationDate());
						empRoleDTO.setParentRoleID(Integer.valueOf(parentID.split("#")[1]));
						session.saveOrUpdate(empRoleDTO);

					}
					session.flush();//tx.commit() ;
					orgStrBean.setResult(CPSConstants.SUCCESS);
				}

				// if (CPSUtils.isNullOrEmpty(orgStrBean.getMapSfID())) {
				// // Deleting user from the role
				// EmpRoleMappingDTO empRoleDTO = (EmpRoleMappingDTO) session.get(EmpRoleMappingDTO.class, Integer.valueOf(orgStrBean.getNodeID()));
				// empRoleDTO.setStatus(0);
				// empRoleDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				// session.saveOrUpdate(empRoleDTO);
				// }
				//				
				// EmpRoleMappingDTO empRoleDTO = new EmpRoleMappingDTO();
				// empRoleDTO.setSfid(orgStrBean.getEditSfid());
				// empRoleDTO.setStatus(1);
				// empRoleDTO.setInternalRole("Employee");
				// empRoleDTO.setParentId(parentID.split("#")[0]);
				// empRoleDTO.setCreationDate(CPSUtils.getCurrentDate());
				// empRoleDTO.setLastModifiedDate(empRoleDTO.getCreationDate());
				// empRoleDTO.setParentRoleID(Integer.valueOf(parentID.split("#")[1]));
				// session.saveOrUpdate(empRoleDTO);
				// session.flush();//tx.commit() ;
				// //tx = session.beginTransaction();
				// // Get the employee default role, if the default role is same then we should update
				// EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, orgStrBean.getEditSfid());
				// if (empBean.getOffice() == Integer.valueOf(orgStrBean.getRoleName())) {
				// // Default role is same, we should update the default role (office_id & directorate_id)
				//
				// // Get the directorate ID
				// OrgInstanceDTO orgInstance = (OrgInstanceDTO) session.get(OrgInstanceDTO.class, Integer.valueOf(parentID.split("#")[1]));
				//
				// if (orgInstance.getParentID() == 0) {
				// // Director
				// orgInstance.setParentID(1);
				// }
				// empBean.setDirectorate(orgInstance.getParentID());
				// empBean.setOffice(Integer.valueOf(parentID.split("#")[1]));
				// empBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				// empBean.setLastModifiedBy(orgStrBean.getSfID());
				// empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
				// empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
				// empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
				// empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
				// empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
				// empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
				// empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
				// session.clear();
				// session.saveOrUpdate(empBean);
				// }

			} else {
				// Same employee
				if (!CPSUtils.isNullOrEmpty(orgStrBean.getIsDefault())) {
					// Changed employee default role
					if (CPSUtils.compareStrings(orgStrBean.getIsDefault(), "1")) {
						// Assigning this role as default role

						// Get the employee details
						EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, orgStrBean.getEditSfid());

						// Get the directorate ID
						OrgInstanceDTO orgInstance = (OrgInstanceDTO) session.get(OrgInstanceDTO.class, Integer.valueOf(orgStrBean.getRoleName()));
						if (orgInstance.getParentID() == 0) {
							// Director
							orgInstance.setParentID(1);
						}
						empBean.setDirectorate(orgInstance.getParentID());
						empBean.setOffice(Integer.valueOf(orgStrBean.getRoleName()));
						empBean.setLastModifiedDate(CPSUtils.getCurrentDate());
						empBean.setLastModifiedBy(orgStrBean.getSfID());
						empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
						empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
						empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
						empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
						empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
						empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
						empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
						empBean.setUptoDate(CPSUtils.formattedDate(empBean.getUptoDate()));
						session.clear();
						session.saveOrUpdate(empBean);
						session.flush();//tx.commit() ;
						orgStrBean.setResult(CPSConstants.SUCCESS);
					} else {
						// Deleting this role as default role is not possible
						orgStrBean.setResult(CPSConstants.FAILED);
						if (CPSUtils.isNullOrEmpty(orgStrBean.getRemarks())) {
							orgStrBean.setRemarks("");
						}
						orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>Please update the default role from edit employee screen.");
					}
				} else {
					orgStrBean.setResult(CPSConstants.SUCCESS);
				}
			}

		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	/**
	 * This method checks employee is having normal EMPLOYEE role or not, if EMPLOYEE role is there then it return false
	 * 
	 * @param sfid
	 * @return
	 * @throws Exception
	 */
	public boolean checkAsNormalEmployee(String sfid) throws Exception {
		Session session1 = null;
		boolean status = true;
		try {
			session1 = hibernateUtils.getSession();
			if (CPSUtils.checkList(session1.createSQLQuery("select count(*) from emp_role_mapping where status=1 and sfid=? and org_role_id is null").setSerializable(0, sfid).list())) {
				status = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return status;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean assignNewHead(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			List<EmpRoleMappingDTO> list = session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq("parentRoleID", Integer.valueOf(orgStrBean.getRoleName()))).add(
					Expression.eq(CPSConstants.STATUS, 1)).add(Expression.isNull("roleInstanceId")).list();
			for (EmpRoleMappingDTO empRoleDTO : list) {
				empRoleDTO.setParentId(orgStrBean.getMapSfID());
				empRoleDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(empRoleDTO);
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean checkAsHead(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			orgStrBean.setRemarks("");
			List<EmpRoleMappingDTO> list = session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq("parentRoleID", Integer.valueOf(orgStrBean.getRoleName()))).add(
					Expression.eq(CPSConstants.STATUS, 1)).list();

			if (CPSUtils.checkList(list)) {
				orgStrBean.setResult(CPSConstants.FAILED);
				for (EmpRoleMappingDTO empRoleDTO : list) {
					if (!CPSUtils.compareStrings(empRoleDTO.getSfid(), orgStrBean.getEditSfid())) {
						orgStrBean.setRemarks(orgStrBean.getRemarks() + empRoleDTO.getSfid() + ", ");
					}
				}
				if (!CPSUtils.isNullOrEmpty(orgStrBean.getRemarks())) {
					orgStrBean.setRemarks("This Employee is the boss for " + orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " employee(s).");
				}
			}
			// Check whether any other role is child to this role with employee attached
			List<EmployeeDetailsDTO> empList = session.createSQLQuery(
					"select ori.org_role_id id,ori.org_role_name roleName,erm.sfid empSfid from org_role_instance ori,emp_role_mapping erm where ori.status=1 and erm.status=1 and erm.org_role_id=ori.org_role_id "
							+ "start with ori.org_role_id=? connect by ori.parent_org_role_id = prior ori.org_role_id").addScalar("id", Hibernate.INTEGER).addScalar("roleName", Hibernate.STRING)
					.addScalar("empSfid", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeDetailsDTO.class)).setString(0, orgStrBean.getRoleName()).list();

			if (CPSUtils.checkList(list)) {
				if (CPSUtils.isNullOrEmpty(orgStrBean.getRemarks())) {
					orgStrBean.setResult(CPSConstants.FAILED);
				} else {
					orgStrBean.setRemarks(orgStrBean.getRemarks() + "<br>");
				}
				boolean status = false;
				for (EmployeeDetailsDTO empDTO : empList) {
					if (empDTO.getId() != Integer.valueOf(orgStrBean.getRoleName())) {
						status = true;
						orgStrBean.setRemarks(orgStrBean.getRemarks() + empDTO.getRoleName() + " (" + empDTO.getEmpSfid() + "), ");
					}
				}
				if (status) {
					orgStrBean.setRemarks(orgStrBean.getRemarks().substring(0, orgStrBean.getRemarks().length() - 2) + " Role(s) are mapped.");
				}
			}

			if (CPSUtils.isNullOrEmpty(orgStrBean.getRemarks())) {
				orgStrBean.setResult(CPSConstants.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean getEmpRoles(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			sb.append("select ori.org_role_id id,ori.org_role_name name from emp_role_mapping erm,org_role_instance ori where ori.status=1 and erm.status=1 and erm.org_role_id=ori.org_role_id ");

			if (!(orgStrBean.getEmpRoles().contains("ROLE_SUPERADMIN"))) {
				// Navya said that no need to show all roles to ROLE_ADMIN
				sb.append(" and erm.sfid='" + orgStrBean.getSfID() + "' ");
			}
			sb.append(" order by ori.org_role_id");

			orgStrBean.setEmpRolesList(session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(KeyValueDTO.class)).list());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean getConfiguredList(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		List<UserSpecificDTO> list = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (!CPSUtils.isNullOrEmpty(orgStrBean.getRoleName())) {
				KeyValueDTO keyDTO = new KeyValueDTO();
				keyDTO.setId(Integer.valueOf(orgStrBean.getRoleName()));
				List<KeyValueDTO> keyList = new ArrayList<KeyValueDTO>();
				keyList.add(keyDTO);
				orgStrBean.setEmpRolesList(keyList);
			}

			for (KeyValueDTO keyDTO : orgStrBean.getEmpRolesList()) {
				/*
				 * sql =
				 * "select usc.id id,rm.request_type_id requestTypeId,rm.request_type requestName,ori.org_role_id roleMapId,ori.org_role_name roleName,dm.id designationID,dm.name designation,usc.delegate delegateUser,"
				 * +
				 * "case when usc.delegation_type=? then (select name_in_service_book from emp_master where sfid=usc.delegate) when usc.delegation_type=? then (select org_role_name from org_role_instance where status=1 and org_role_id=usc.delegate) end delegate,"
				 * +
				 * "sm.status assigned,sm.id assignedType,usc.gazetted_type gazettedType from user_specific_configuration usc, org_role_instance ori, request_master rm,designation_master dm,status_master sm where usc.status=1 and usc.org_role_id=? and ori.status=1 and ori.org_role_id=usc.org_role_id "
				 * + "and rm.status=1 and rm.request_type_id=usc.request_type_id and dm.status=1 and dm.id=usc.designation_id and sm.id=usc.assigned_type";
				 *//*sql = "select gazettedType,assignedType,assigned,delegateUser,uniqueID,requestTypeId,requestName,roleMapId,roleName,designation,designationID,(case when delegation_type=? then (select   rtrim (xmlagg (xmlelement (e, name_in_service_book || ',')).extract ('//text()'), ',')   from emp_master "
						+ " where sfid=delegateUser)when delegation_type=? then (select rtrim (xmlagg (xmlelement (e, org_role_name || ',')).extract ('//text()'), ',') "
						+ " from org_role_instance where status=1 and org_role_id=delegateUser) end) delegate "
						+ " from (select usc.delegation_type,rtrim (xmlagg (xmlelement (e, usc.id || ',')).extract ('//text()'), ',') uniqueID, rm.request_type_id requestTypeId,rm.request_type requestName,ori.org_role_id roleMapId,ori.org_role_name roleName, "
						+ " usc.delegate delegateUser,rtrim (xmlagg (xmlelement (e, dm.name || ',')).extract ('//text()'), ',') designation,rtrim (xmlagg (xmlelement (e, dm.id || ',')).extract ('//text()'), ',') designationID,"
						+ " sm.status assigned,sm.id assignedType,usc.gazetted_type gazettedType from user_specific_configuration usc, org_role_instance ori, "
						+ " request_master rm,designation_master dm,status_master sm where usc.status=1 and usc.org_role_id=? and ori.status=1 and "
						+ " ori.org_role_id=usc.org_role_id and rm.status=1 and rm.request_type_id=usc.request_type_id "
						+ " and dm.id=usc.designation_id and sm.id=usc.assigned_type "
						+ " group by usc.delegation_type,rm.request_type_id,rm.request_type,rm.request_type_id,usc.delegate,ori.org_role_id,ori.org_role_name,sm.status,sm.id,usc.gazetted_type)maintable";*/
				sql="select tab.gazettedType as gazettedType,tab.assignedType as assignedType,tab.assigned as assigned,tab.delegateUser as delegateUser,rtrim (xmlagg (xmlelement (e, tab.uniqueID || ',')).extract ('//text()'), ',') uniqueID, rtrim (xmlagg (xmlelement (e, tab.requestTypeId || ',')).extract ('//text()'), ',') requestTypeIdValue," +
					" rtrim (xmlagg (xmlelement (e, tab.delegate || ',')).extract ('//text()'), ',') delegate,rtrim (xmlagg (xmlelement (e,  tab.requestName || ',')).extract ('//text()'), ',') requestName, tab.roleMapId,tab.roleName," +
					" rtrim (xmlagg (xmlelement (e,  tab.designation || ',')).extract ('//text()'), ',') designation,rtrim (xmlagg (xmlelement (e,  tab.designationID || ',')).extract ('//text()'), ',') designationID from " +
					" (select gazettedType,assignedType,assigned,delegateUser,uniqueID,requestTypeId,requestName,roleMapId,roleName,designation,designationID,(case  when delegation_type=? then (select rtrim (xmlagg (xmlelement (e, name_in_service_book || ',')).extract ('//text()'), ',')  " +
					" from emp_master  where sfid=delegateUser ) when delegation_type=? then   (select rtrim (xmlagg (xmlelement (e, org_role_name || ',')).extract ('//text()'), ',')   from org_role_instance  where status   =1 and org_role_id=delegateUser )  end) delegate from " +
					" (select usc.delegation_type, rtrim (xmlagg (xmlelement (e, usc.id  || ',')).extract ('//text()'), ',') uniqueID, rtrim (xmlagg (xmlelement (e,rm.request_type_id || ',')).extract ('//text()'), ',') requestTypeId, rm.request_type requestName, ori.org_role_id roleMapId, " +
					" ori.org_role_name roleName, usc.delegate delegateUser, rtrim (xmlagg (xmlelement (e, dm.description || ',')).extract ('//text()'), ',') designation, rtrim (xmlagg (xmlelement (e, dm.id     || ',')).extract ('//text()'), ',') designationID," +
					" sm.status assigned, sm.id assignedType,usc.gazetted_type gazettedType   from user_specific_configuration usc, org_role_instance ori, request_master rm,designation_master dm,status_master sm where usc.status=1 and usc.org_role_id=?  " +
					" and ori.status=1  and ori.org_role_id  =usc.org_role_id and rm.status =1 and rm.request_type_id=usc.request_type_id and dm.id  =usc.designation_id and sm.id =usc.assigned_type   GROUP BY usc.delegation_type, usc.delegate, ori.org_role_id," +
					" ori.org_role_name,sm.status, sm.id, usc.gazetted_type, rm.request_type )maintable )tab GROUP BY  gazettedType,assignedType,assigned,delegateUser,roleMapId,roleName";

				list =session.createSQLQuery(sql).addScalar("gazettedType", Hibernate.STRING).addScalar("assignedType", Hibernate.INTEGER).addScalar("assigned", Hibernate.STRING).addScalar("delegateUser", Hibernate.STRING).addScalar("uniqueID", Hibernate.STRING)
						.addScalar("requestTypeIdValue", Hibernate.STRING).addScalar("requestName", Hibernate.STRING).addScalar("roleMapId", Hibernate.INTEGER).addScalar("roleName", Hibernate.STRING).addScalar("designation", Hibernate.STRING)
						.addScalar("delegate", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(UserSpecificDTO.class)).setString(0, CPSConstants.STATUSSFID).setString(1, CPSConstants.STATUSINSTANCE).setString(2, String.valueOf(keyDTO.getId())).list();
				
				if (CPSUtils.checkList(orgStrBean.getUserSpecificList())) {
					for (UserSpecificDTO userDTO : list) {
						orgStrBean.getUserSpecificList().add(userDTO);
					}
				} else {
					orgStrBean.setUserSpecificList(list);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getDesignationsList() throws Exception {
		Session session = null;
		List<DesignationDTO> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			list = session.createSQLQuery(
					"select dm.id id,dm.name name,dmap.type key from designation_master dm,designation_mappings dmap where dm.status=1 and dm.id=dmap.desig_id order by dm.order_no").addScalar("id",
					Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	@Override
	@SuppressWarnings("unchecked")
	public OrgStructureBean getSubOrdinatesList(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			orgStrBean
					.setSubordinatesList(session
							.createSQLQuery(
									"select key,name from (select sfid key,sfid||' - '||name_in_service_book name from emp_master where status=1 and office_id=? and sfid not in (select sfid from emp_role_mapping where status=1 and org_role_id=?) "
											+ "union select org_role_id||'' key,org_role_name name from org_role_instance where status=1 start with org_role_id=? connect by parent_org_role_id = prior org_role_id "
											+ "union select ori2.org_role_id||'' key,ori2.org_role_name name from org_role_instance ori1,org_role_instance ori2 where ori1.status=1 and ori2.status=1 and ori1.parent_org_role_id=ori2.org_role_id and ori1.org_role_id=? "
											+ "union select org_role_id||'' key,org_role_name name from org_role_instance where role_hierarchy_id=(select role_hierarchy_id from org_role_instance where status=1 and org_role_id=?) " 
											+ "union select org_role_id||'' key,org_role_name name from org_role_instance where status=1 start with org_role_id in (select ori2.org_role_id from org_role_instance ori1,org_role_instance ori2 where " 
											+ "ori1.org_role_id=? and ori2.org_role_id!=? and ori1.role_hierarchy_id=ori2.role_hierarchy_id and ori1.department_id=ori2.department_id and ori1.status=1 and ori2.status=1) connect by parent_org_role_id = prior org_role_id and org_role_id!=? and status=1 ) order by name")
							.addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0,
									orgStrBean.getRoleName()).setString(1, orgStrBean.getRoleName()).setString(2, orgStrBean.getRoleName()).setString(3, orgStrBean.getRoleName()).setString(4,
									orgStrBean.getRoleName()).setString(5, orgStrBean.getRoleName()).setString(6, orgStrBean.getRoleName()).setString(7, orgStrBean.getRoleName()).list());
			KeyValueDTO key =new KeyValueDTO();
			key.setKey("161");
			key.setName("Admin SAO II - Primary");
			orgStrBean.getSubordinatesList().add(key);

			String sql = "select rtrim (xmlagg (xmlelement (e, designation_id || ',')).extract ('//text()'), ',') from user_specific_configuration where status=1 and org_role_id=? "
					+ "and assigned_type=? and delegate=? and request_type_id in ("+orgStrBean.getRequestName()+")";// old code back up
			if (CPSUtils.isNullOrEmpty(orgStrBean.getGazType())) {
				sql += " and gazetted_type is null";
			} else {
				sql += " and gazetted_type ='" + orgStrBean.getGazType() + "'";
			}
			orgStrBean.setMultipleDesignation((String) session.createSQLQuery(sql).setString(0, orgStrBean.getRoleName()).setString(1,
					orgStrBean.getAssignType()).setString(2, orgStrBean.getSubordinateId()).uniqueResult());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}
	@Override
	public OrgStructureBean deleteConfiguration(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			//tx = session.beginTransaction();
			String ids[] = orgStrBean.getNodeID().split(",");
			for (String id : ids) {
				UserSpecificDTO userdDTO = (UserSpecificDTO) session.get(UserSpecificDTO.class, Integer.valueOf(id));
				userdDTO.setStatus(0);
				session.saveOrUpdate(userdDTO);
			}
			session.flush();//tx.commit() ;
			orgStrBean.setMessage(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgStrBean;
	}


	@Override
	public OrgStructureBean submitConfiguration(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		int delegateType = 0;
		int userSpecID = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String[] ids = orgStrBean.getNodeID().split(",");
			for (String id : ids) {
				session.createQuery("update UserSpecificDTO set status=0 where id =?").setString(0, id).executeUpdate();  
				//tx = session.beginTransaction();
				session.flush();//tx.commit() ;
			}
			userSpecID = Integer.valueOf((String) session.createSQLQuery("select case when max(id) is null then 1||'' else max(id)+1||'' end from user_specific_configuration").uniqueResult());
			// Insert the configuration
			String[] selectedRequests = orgStrBean.getRequestName().split(",");
			String[] selectedDesignations = orgStrBean.getDesignation().split(",");

			if (orgStrBean.getSubordinateId().contains("SF"))
				delegateType = Integer.valueOf(CPSConstants.STATUSSFID);
			else
				delegateType = Integer.valueOf(CPSConstants.STATUSINSTANCE);

			for (String requestID : selectedRequests) {

				// designation
				for (String designationID : selectedDesignations) {
					UserSpecificDTO userDTO = new UserSpecificDTO();
					userDTO.setId(userSpecID);
					userDTO.setRoleMapId(Integer.valueOf(orgStrBean.getRoleName()));
					userDTO.setRequestTypeId(Integer.valueOf(requestID));
					userDTO.setGazettedType(orgStrBean.getGazType());
					userDTO.setDelegate(orgStrBean.getSubordinateId());
					userDTO.setAssignedType(Integer.valueOf(orgStrBean.getAssignType()));
					userDTO.setDelegateType(delegateType);
					if (CPSUtils.isNullOrEmpty(designationID)) {
						userDTO.setDesignation("0");
					} else {
						userDTO.setDesignation(designationID);
					}

					userDTO.setStatus(1);
					if (checkDuplicateConfiguration(userDTO)) {
						userDTO.setNoOfRequests(Integer.valueOf(getNoOfRequest(userDTO)));
						//tx = session.beginTransaction();
						session.saveOrUpdate(userDTO);
						session.flush();//tx.commit() ;
						userSpecID++;
						orgStrBean.setResult(CPSConstants.SUCCESS);
					}else{
						orgStrBean.setResult(CPSConstants.DUPLICATE);
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		return orgStrBean;
	}

	public String getNoOfRequest(UserSpecificDTO userDTO) throws Exception {
		String numberOfRequest = "0";
		Session session1 = null;
		StringBuffer sb = new StringBuffer();
		try {
			session1 = hibernateUtils.getSession();
			sb.append("select min(no_of_requests)||'' noofrequests from user_specific_configuration where org_role_id=? and request_type_id=? and status=1 and ");
			if (CPSUtils.isNullOrEmpty(userDTO.getGazettedType())) {
				sb.append("gazetted_type is null ");
			} else {
				sb.append("gazetted_type ='" + userDTO.getGazettedType() + "' ");
			}
			sb.append(" and designation_id ='" + userDTO.getDesignation() + "'");

			numberOfRequest = (String) session1.createSQLQuery(sb.toString()).setString(0, String.valueOf(userDTO.getRoleMapId())).setString(1, String.valueOf(userDTO.getRequestTypeId()))
					.uniqueResult();
			if (CPSUtils.isNullOrEmpty(numberOfRequest)) {
				numberOfRequest = "0";
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return numberOfRequest;
	}

	public boolean checkDuplicateConfiguration(UserSpecificDTO userDTO) throws Exception {
		boolean status = true;
		Session session1 = null;
		StringBuffer sb = new StringBuffer();
		int result = 0;
		try {
			session1 = hibernateUtils.getSession();
			sb.append("select count(*) from user_specific_configuration where status=1 and org_role_id=? and request_type_id=? and delegate=? and delegation_type=? and assigned_type=? ");
			if (!CPSUtils.isNullOrEmpty(userDTO.getGazettedType())) {
				sb.append(" and gazetted_type='" + userDTO.getGazettedType() + "' ");
			}
			if (!CPSUtils.isNullOrEmpty(userDTO.getDesignation())) {
				sb.append(" and designation_id='" + userDTO.getDesignation() + "' ");
			}

			result = Integer.valueOf(session1.createSQLQuery(sb.toString()).setString(0, String.valueOf(userDTO.getRoleMapId())).setString(1, String.valueOf(userDTO.getRequestTypeId())).setString(2,
					String.valueOf(userDTO.getDelegate())).setString(3, String.valueOf(userDTO.getDelegateType())).setString(4, String.valueOf(userDTO.getAssignedType())).uniqueResult().toString());

			if (result > 0) {
				status = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return status; 
	}

	@Override
	public List checkOrganization(OrgStructureBean orgStrBean) throws Exception {
		
		
		Session session = null;
		String sql = null;
		List list = null;
		
		try {
			session = hibernateUtils.getSession();
			sql = "select id as id,name as name,description as description,phoneNumber as phoneNumber,fax as fax,pincode as pincode,email as email,address1 as address1,address2 as address2,address3 as address3 from OrganizationsDTO where upper(name)=? and status=1";
			if(!CPSUtils.isNullOrEmpty(orgStrBean.getNodeID()))
				sql +=" and id != "+orgStrBean.getNodeID();
			Query qry = session.createQuery(sql);
			qry.setString(0, orgStrBean.getName().trim().toUpperCase());
			qry = qry.setResultTransformer(Transformers.aliasToBean(OrganizationsDTO.class));
			list = qry.list();

		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
		}
		
		return list;
	}

	@Override
	public String updateOrganization(OrgStructureBean orgStrBean) throws Exception {
		Session session = null;
		String sql = null;
		List list = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			sql = "update OrganizationsDTO set name=?,description=?,email=?,phoneNumber=?,pincode=?,address1=?,address2=?,address3=?,fax=? where id=?";
			session.createQuery(sql).setString(0,orgStrBean.getName()).setString(1, orgStrBean.getDescription())
				.setString(2, orgStrBean.getEmail()).setString(3, orgStrBean.getPhoneNumber()).setString(4, orgStrBean.getPincode()).setString(5,orgStrBean.getAddress1()).setString(6, orgStrBean.getAddress2()).setString(7, orgStrBean.getAddress3()).setString(8,orgStrBean.getFax()).setString(9,orgStrBean.getNodeID()).executeUpdate();
			message = CPSConstants.SUCCESS;	
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
			
		} finally {
			
		}
		
		return message;
	}
}
