package com.callippus.web.business.hierarchy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.HierarchyDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.hierarchy.HierarchyBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.hierarchy.IHierarchyDAO;

@Service
public class HierarchyBusiness {
	@Autowired
	private IHierarchyDAO hierarchyDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public HierarchyBean getHierarchyLevelHome(HierarchyBean hierarchyBean, HttpSession session) throws Exception {
		try {
			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				hierarchyBean.setParentList(commonDataDAO.getMasterData(CPSConstants.HIERARCHYDTO));
			} else {
				// deptartment
				hierarchyBean.setParentList(commonDataDAO.getMasterData(CPSConstants.DEPTHIERARCHYDTO));
			}
			session.setAttribute(CPSConstants.PARENTLIST, hierarchyBean.getParentList());
			hierarchyBean = hierarchyDAO.getHierarchyLevelList(hierarchyBean);

			session.setAttribute(CPSConstants.DISPLAYLEVEL, hierarchyBean.getHierarchyDisplayTable());
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	public HierarchyBean manageHierarchyLevel(HierarchyBean hierarchyBean, HttpSession session) throws Exception {
		try {
			hierarchyBean.setMessage(hierarchyDAO.checkLevel(hierarchyBean));
			if (CPSUtils.compareStrings(hierarchyBean.getMessage(), CPSConstants.SUCCESS)) {
				if (CPSUtils.isNullOrEmpty(hierarchyBean.getId())) {
					// created new level
					if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
						hierarchyBean.setId((Integer.valueOf(commonDataDAO.getTableID(CPSConstants.ORG_ROLE_HIERARCHY, CPSConstants.UPDATE))).toString());
					} else {
						hierarchyBean.setId((Integer.valueOf(commonDataDAO.getTableID(CPSConstants.DEPARTMENTS_HIERARCHY, CPSConstants.UPDATE))).toString());
					}
					hierarchyBean.setMessage(hierarchyDAO.createHierarchyLevel(hierarchyBean));
				} else {
					hierarchyBean.setMessage(hierarchyDAO.updateHierarchyLevel(hierarchyBean));
					hierarchyBean.setId("");
				}
			}
			getHierarchyLevelHome(hierarchyBean, session);
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	public HierarchyBean deleteHierarchyLevel(HierarchyBean hierarchyBean, HttpSession session) throws Exception {
		try {

			if (CPSUtils.compareStrings(hierarchyBean.getType(), CPSConstants.ROLE)) {
				hierarchyBean.setMessage(commonDataDAO.deleteCheckMaster(CPSConstants.ORG_ROLE_INSTANCE, CPSConstants.ROLE_HIERARCHY_ID, CPSConstants.STATUS, hierarchyBean.getId()));
			} else {
				hierarchyBean.setMessage(commonDataDAO.deleteCheckMaster(CPSConstants.DEPARTMENTS_MASTER, CPSConstants.DEPT_HIERARCHY_ID, CPSConstants.STATUS, hierarchyBean.getId()));
			}

			if (!CPSUtils.compareStrings(CPSConstants.FAILED, hierarchyBean.getMessage())) {
				hierarchyBean.setMessage(hierarchyDAO.deleteHierarchyLevel(hierarchyBean));

				getHierarchyLevelHome(hierarchyBean, session);
			} else {
				hierarchyBean.setMessage(CPSConstants.DELETEFAIL);
			}

		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	@SuppressWarnings("unchecked")
	public HierarchyBean getHierarchyNodeHome(HttpSession session, HierarchyBean hierarchyBean) throws Exception {
		try {
			List rolelist = (List) session.getAttribute(CPSConstants.ROLELIST);
			if (rolelist.contains("ROLE_ADMIN") || rolelist.contains("ROLE_SUPERADMIN")) {
				if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical"))
					hierarchyBean.setRoleLevelList(commonDataDAO.getMasterData(CPSConstants.HIERARCHYDTO));
				else {
					hierarchyBean.setRoleLevelList(commonDataDAO.getMasterData(CPSConstants.DEPTHIERARCHYDTO));
					hierarchyBean.setDepartmentTypeList(commonDataDAO.getMasterData(CPSConstants.DEPARTMENTTYPEDTO));
				}
			} else {
				if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical"))
					hierarchyBean.setRoleLevelList(hierarchyDAO.getRoleLevelList(session.getAttribute(CPSConstants.SFID).toString()));
				else
					hierarchyBean.setRoleLevelList(hierarchyDAO.getDepartmentsList(session.getAttribute(CPSConstants.SFID).toString()));
			}
			hierarchyBean = hierarchyDAO.getHierarchyNodeList(hierarchyBean, rolelist);
			// session.setAttribute(CPSConstants.DISPLAYNODE, hierarchyBean.getHierarchyDisplayTable());
			// for departments
			if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical")) {
				List departments = hierarchyDAO.getDepartments();
				session.setAttribute(CPSConstants.DEPARTMENTS, departments);
			}
			session.setAttribute(CPSConstants.DISPLAYNODE, (JSONArray) JSONSerializer.toJSON(hierarchyBean.getHierarchyDisplayTable()));
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	public HierarchyBean getNodeInstanceParentList(HierarchyBean hierarchyBean, List<Object> roleList) throws Exception {
		try {

			hierarchyBean.setRoleParentList(hierarchyDAO.getNodeInstanceParentList(hierarchyBean, roleList));
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	@SuppressWarnings("unchecked")
	public HierarchyBean manageHierarchyNode(HierarchyBean hierarchyBean, HttpSession session) throws Exception {
		try {
			hierarchyBean.setMessage(hierarchyDAO.checkNodeLevel(hierarchyBean));
			if (CPSUtils.compareStrings(hierarchyBean.getMessage(), CPSConstants.SUCCESS)) {
				if (CPSUtils.isNullOrEmpty(hierarchyBean.getId())) {
					// new designation created
					if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical")) {
						hierarchyBean.setId((Integer.valueOf(commonDataDAO.getTableID(CPSConstants.ORG_ROLE_INSTANCE, CPSConstants.UPDATE))).toString());
						hierarchyBean.setMessage(hierarchyDAO.createRoleInstance(hierarchyBean));
					} else {
						DepartmentsDTO deptDTO = getDepartmentDTO(hierarchyBean);
						deptDTO.setId(Integer.valueOf(commonDataDAO.getTableID(CPSConstants.DEPARTMENTS_MASTER, CPSConstants.UPDATE)));
						deptDTO.setDepartmentTypeId(Integer.valueOf(hierarchyBean.getDepartmentType()));
						hierarchyBean.setMessage(hierarchyDAO.saveOrgPhysicalInstance(deptDTO));
					}
				} else {
					// updated old designation

					if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical")) {
						hierarchyBean.setMessage(hierarchyDAO.updateRoleInstance(hierarchyBean));
					} else {
						DepartmentsDTO deptDTO = getDepartmentDTO(hierarchyBean);
						deptDTO.setId(Integer.valueOf(hierarchyBean.getId()));
						deptDTO.setDepartmentTypeId(Integer.valueOf(hierarchyBean.getDepartmentType()));
						hierarchyBean.setMessage(hierarchyDAO.saveOrgPhysicalInstance(deptDTO));
					}

				}
			}
			List<Object> rolelist = (List<Object>) session.getAttribute(CPSConstants.ROLELIST);
			hierarchyBean = hierarchyDAO.getHierarchyNodeList(hierarchyBean, rolelist);
			session.setAttribute(CPSConstants.DISPLAYNODE, (JSONArray) JSONSerializer.toJSON(hierarchyBean.getHierarchyDisplayTable()));
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	@SuppressWarnings("unchecked")
	public HierarchyBean deleteHierarchyNode(HierarchyBean hierarchyBean, HttpSession session) throws Exception {

		String message = null;
		String message1 = null;
		String message2 = null;
		try {
			String nodeID = hierarchyBean.getId();
			if (CPSUtils.compareStrings(hierarchyBean.getType(), "Logical")) {
				message = commonDataDAO.deleteCheckMaster(CPSConstants.ORG_ROLE_INSTANCE, CPSConstants.PARENT_ORG_ROLE_ID, CPSConstants.STATUS, nodeID);
				message1 = commonDataDAO.deleteCheckMaster(CPSConstants.EMP_ROLE_MAPPING, CPSConstants.ORG_ROLE_ID, CPSConstants.STATUS, nodeID);
				message2 = commonDataDAO.deleteCheckMaster(CPSConstants.EMP_MASTER, CPSConstants.OFFICE_ID, CPSConstants.STATUS, nodeID);
			} else {
				message = commonDataDAO.deleteCheckMaster(CPSConstants.ORG_ROLE_INSTANCE, CPSConstants.DEPARTMENTID, CPSConstants.STATUS, nodeID);
				message1 = CPSConstants.SUCCESS;
				message2 = CPSConstants.SUCCESS;
			}
			List<Object> rolelist = (List<Object>) session.getAttribute(CPSConstants.ROLELIST);
			if (!CPSUtils.compareStrings(CPSConstants.FAILED, message) && !CPSUtils.compareStrings(CPSConstants.FAILED, message1) && !CPSUtils.compareStrings(CPSConstants.FAILED, message2)) {
				hierarchyBean.setMessage(hierarchyDAO.deleteHierarchyNode(nodeID, hierarchyBean.getType()));
				hierarchyBean = hierarchyDAO.getHierarchyNodeList(hierarchyBean, rolelist);
				session.setAttribute(CPSConstants.DISPLAYNODE, (JSONArray) JSONSerializer.toJSON(hierarchyBean.getHierarchyDisplayTable()));
			} else {
				hierarchyBean.setMessage(CPSConstants.SUBORDINATES);
			}

		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	public HierarchyBean getInternalEmployeeMappingHomeDetails(String sfid) throws Exception {
		HierarchyBean hierarchyBean = null;
		try {
			hierarchyBean = new HierarchyBean();
			hierarchyBean.setOrgnRolesList(hierarchyDAO.getOrgnRolesList(sfid));
			hierarchyBean.setSubOrdinatesList(commonDataDAO.getSubOrdinatesList(sfid));
			hierarchyBean.setInternalDivisionsList(hierarchyDAO.getInternalDivisionsList(sfid));
			hierarchyBean.setInternalRolesList(hierarchyDAO.getInternalRolesList(sfid));
			hierarchyBean.setInternalStructureTree(hierarchyDAO.getInternalStructureTree(sfid));
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	public List<OrgInstanceDTO> getInstanceEmployeeMappingHomeDetails(HierarchyBean hierarchyBean, List roleList) throws Exception {
		List<OrgInstanceDTO> roleInstanceList = null;
		try {
			roleInstanceList = hierarchyDAO.getRoleInstanceList(hierarchyBean, roleList);
		} catch (Exception e) {
			throw e;
		}
		return roleInstanceList;
	}

	public String submitInternalRoleMapping(HierarchyBean hierarchyBean) throws Exception {
		try {
			if (CPSUtils.isNullOrEmpty(hierarchyBean.getManageID())) {
				hierarchyBean.setId((Integer.valueOf(commonDataDAO.getTableID("EMP_ROLE_MAPPING", CPSConstants.GET))).toString());
				hierarchyBean.setMessage(hierarchyDAO.submitInternalRoleMapping(hierarchyBean));
				commonDataDAO.updateTableID("EMP_ROLE_MAPPING", hierarchyBean.getId());
			} else {
				hierarchyBean.setMessage(hierarchyDAO.updateInternalRoleMapping(hierarchyBean));
			}
			hierarchyBean.setInternalStructureTree(hierarchyDAO.getInternalStructureTree(hierarchyBean.getSfid()));
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean.getMessage();
	}

	public String submitOrganizationRoleMapping(HierarchyBean hierarchyBean) throws Exception {
		String message = null;
		String message1 = null;
		String message2 = null;
		String message3 = null;
		try {
			message1 = commonDataDAO.checkEmployee(hierarchyBean.getMapSFID());
			if (CPSUtils.isNullOrEmpty(hierarchyBean.getType())) {
				// validation is if head is already there for that instance and if the person is not an employee of other instance
				if (CPSUtils.isNull(hierarchyBean.getRoleInstanceSubName()))
					message = hierarchyDAO.chekHead(hierarchyBean.getMapSFID(), hierarchyBean.getRoleInstanceSubName());
				else
					message = hierarchyDAO.chekHead(hierarchyBean.getMapSFID(), hierarchyBean.getSelectedRole());
				message2 = hierarchyDAO.chekEmployee(hierarchyBean.getMapSFID(), hierarchyBean.getRoleInstanceSubName());

				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS) && CPSUtils.compareStrings(message1, CPSConstants.SUCCESS) && CPSUtils.compareStrings(message2, CPSConstants.SUCCESS)) {
					hierarchyBean.setId((Integer.valueOf(commonDataDAO.getTableID("EMP_ROLE_MAPPING", CPSConstants.UPDATE))).toString());
					hierarchyBean.setMessage(hierarchyDAO.submitOrganizationRoleMapping(hierarchyBean));
				} else {
					if (!CPSUtils.compareStrings(message1, CPSConstants.SUCCESS))
						hierarchyBean.setMessage(message1);
					else if (!CPSUtils.compareStrings(message2, CPSConstants.SUCCESS))
						hierarchyBean.setMessage(message2);
					else
						hierarchyBean.setMessage(message);
				}
			} else if (CPSUtils.compareStrings(hierarchyBean.getType(), "updatehead")) {
				message1 = commonDataDAO.checkEmployee(hierarchyBean.getMapSFID());
				// for avoiding duplicate head while editing
				message = hierarchyDAO.chekDuplicateHead(hierarchyBean.getMapSFID(), hierarchyBean.getSelectedRole());
				if (CPSUtils.compareStrings(message1, CPSConstants.SUCCESS) && CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					String id = Integer.valueOf(commonDataDAO.getTableID("EMP_ROLE_MAPPING", CPSConstants.UPDATE)).toString();
					hierarchyBean.setManageID(id);
					// hierarchyDAO.deleteEmployee(hierarchyBean.getMapSFID());
					message2 = hierarchyDAO.submitOrganizationRoleMapping(hierarchyBean);
					hierarchyBean.setMessage(message2);
				} else if (!CPSUtils.compareStrings(message1, CPSConstants.SUCCESS))
					hierarchyBean.setMessage(message1);
				else
					hierarchyBean.setMessage(message);

			} else {
				if (CPSUtils.isNull(hierarchyBean.getId())) // while editing should not check whether head exists or not
				{
					if (CPSUtils.isNull(hierarchyBean.getRoleInstanceSubName()))
						message = hierarchyDAO.chekHead(hierarchyBean.getMapSFID(), hierarchyBean.getRoleInstanceSubName());
					else
						message = hierarchyDAO.chekHead(hierarchyBean.getMapSFID(), hierarchyBean.getSelectedRole());
				} else
					message = "success";
				message2 = commonDataDAO.checkEmployee(hierarchyBean.getMapSFID());
				// if normal employee is an division head changing his subornites parent Id
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					// message3 = hierarchyDAO.changeParentForSubEmp(hierarchyBean.getMapSFID());
					// message1 = hierarchyDAO.deleteEmployee(hierarchyBean.getMapSFID());
					message3 = CPSConstants.SUCCESS;
					message1 = CPSConstants.SUCCESS;
				}
				if (CPSUtils.compareStrings(message1, CPSConstants.SUCCESS) && CPSUtils.compareStrings(message2, CPSConstants.SUCCESS) && CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					hierarchyBean.setId((Integer.valueOf(commonDataDAO.getTableID("EMP_ROLE_MAPPING", CPSConstants.UPDATE))).toString());
					message = hierarchyDAO.submitOrganizationRoleMapping(hierarchyBean);
					hierarchyBean.setMessage(message);
				} else {
					if (!CPSUtils.compareStrings(message1, CPSConstants.SUCCESS))
						hierarchyBean.setMessage(message1);
					else if (!CPSUtils.compareStrings(message2, CPSConstants.SUCCESS))
						hierarchyBean.setMessage(message2);
					else
						hierarchyBean.setMessage(message);
				}
				hierarchyBean.setMessage(message);
			}
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean.getMessage();
	}

	public ArrayList<HierarchyDTO> getSubInstance(String instanceID) throws Exception {
		ArrayList<HierarchyDTO> list = null;
		try {
			list = hierarchyDAO.getSubInstance(instanceID);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public HierarchyBean deleteInternalEmployee(String sfid, String id) throws Exception {
		HierarchyBean hierarchyBean = null;
		try {
			hierarchyBean = new HierarchyBean();
			hierarchyBean.setMessage(hierarchyDAO.deleteInternalEmployee(id));
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, hierarchyBean.getMessage())) {
				hierarchyBean.setInternalStructureTree(hierarchyDAO.getInternalStructureTree(sfid));
			}
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	public HierarchyBean getRoleInstances(HierarchyBean hierarchyBean, List roleList) throws Exception {
		try {
			hierarchyBean.setInstanceList(hierarchyDAO.getRoleInstances(hierarchyBean, roleList));
		} catch (Exception e) {
			throw e;
		}
		return hierarchyBean;
	}

	public List getAllInstances(HierarchyBean hierarchyBean, List roleList) throws Exception {
		List instancelist = null;
		try {
			if (roleList.contains("ROLE_ADMIN") || roleList.contains("ROLE_SUPERADMIN")) {
				instancelist = commonDataDAO.getMasterData(CPSConstants.ORGINSTANCEDTO);
			} else {
				instancelist = hierarchyDAO.getAllTreeInstances(hierarchyBean.getSfid());
			}
		} catch (Exception e) {
			throw e;
		}
		return instancelist;
	}

	public String deleteHeadMapping(HierarchyBean hierarchyBean) throws Exception {
		String message = null;
		String message2 = null;
		try {
			message = hierarchyDAO.checkParentInstanceId(hierarchyBean.getMapSFID(), hierarchyBean.getRoleInstanceSubName());
			// message1 = hierarchyDAO.checkTree(hierarchyBean.getMapSFID(), hierarchyBean.getRoleInstanceSubName());
			message2 = hierarchyDAO.checkUserSpecificConfiguration(hierarchyBean.getMapSFID(), hierarchyBean.getRoleInstanceSubName());
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message) && CPSUtils.compareStrings(CPSConstants.SUCCESS, message2)) {
				message = hierarchyDAO.deleteHeadMapping(hierarchyBean.getMapSFID(), hierarchyBean.getRoleInstanceSubName());
			} else {
				message = CPSConstants.EMPEXITS;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getEmployeeMappingDetails(HierarchyBean hierarchyBean, List roleList) throws Exception {
		List<Object> instanceList = null;
		List<Object> list = null;
		try {
			list = hierarchyDAO.getEmployeeMappingDetails(hierarchyBean, roleList);
			if (CPSUtils.checkList(list)) {
				instanceList = new ArrayList<Object>();
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					boolean flag = true;
					for (int j = 0; j < instanceList.size(); j++) {
						EmployeeBean dto = (EmployeeBean) instanceList.get(j);
						if (CPSUtils.compareStrings(obj[1].toString(), String.valueOf(dto.getSfid()))) {
							flag = false;
							break;
						}
					}
					if (flag) {
						EmployeeBean empbean = new EmployeeBean();
						empbean.setId(obj[0].toString());
						empbean.setSfid(obj[1].toString());
						empbean.setEmpName(obj[2].toString());
						empbean.setParent(obj[3].toString());
						empbean.setDivisionName(obj[4].toString());
						empbean.setInstanceId(obj[5].toString());
						empbean.setParentName(obj[6].toString());
						empbean.setDesignationName(obj[7].toString());
						empbean.setOfficeId(obj[8].toString());
						instanceList.add(empbean);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return instanceList;
	}

	public String saveEmpMapping(HierarchyBean hierarchyBean) throws Exception {
		String message = null;
		try {
			String[] checkedValues = hierarchyBean.getCheckedValues().split(",");
			for (int i = 0; i < checkedValues.length; i++) {
				String[] values = checkedValues[i].toString().split("#");
				EmpRoleMappingDTO rolemappingdto = new EmpRoleMappingDTO();
				rolemappingdto.setId(Integer.parseInt(values[0].toString()));
				rolemappingdto.setSfid(values[1]);
				String instanceId = "";
				if (CPSUtils.compareStrings(hierarchyBean.getParam(), CPSConstants.EMPMAPDELETE)) {
					rolemappingdto.setStatus(0);
					instanceId = values[2];
				} else {
					rolemappingdto.setStatus(1);
					instanceId = hierarchyBean.getRoleInstanceName();
				}
				/*
				 * if (values.length == 4 && !CPSUtils.isNull(values[3]) && CPSUtils.compareStrings(values[3].toString(), "yes")) { hierarchyDAO.changeParentForSubEmp(values[1]); }
				 */rolemappingdto.setInternalRole("Employee");
				rolemappingdto.setInternalDivision(null);
				rolemappingdto.setParentId(hierarchyDAO.getParentId(instanceId));
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				rolemappingdto.setCreationDate(df.format(new Date()));
				rolemappingdto.setLastModifiedDate(df.format(new Date()));
				rolemappingdto.setOrgRoleId(null);
				rolemappingdto.setParentRoleID(Integer.parseInt(instanceId));
				message = hierarchyDAO.saveEmployeeMapping(rolemappingdto, instanceId);
			}
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				if (CPSUtils.compareStrings(hierarchyBean.getParam(), CPSConstants.EMPMAPDELETE))
					message = CPSConstants.DELETE;
				else
					message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String getHierarchyLevel(String sfid, List rolelist) throws Exception {
		String level = "";
		try {
			if (rolelist.contains("ROLE_ADMIN") || rolelist.contains("ROLE_SUPERADMIN")) {
				level = "Director/AD/TD/PD/Division/Group/Section";
			} else {
				level = commonDataDAO.getHierarchyLevel(sfid);
			}
		} catch (Exception e) {
			throw e;
		}
		return level;
	}

	public DepartmentsDTO getDepartmentDTO(HierarchyBean hierarchyBean) throws Exception {
		DepartmentsDTO deptdto = null;
		try {
			deptdto = new DepartmentsDTO();
			deptdto.setHierarchyID(Integer.valueOf(hierarchyBean.getLevelName()));
			deptdto.setDeptName(hierarchyBean.getNodeNames());
			deptdto.setParentID(Integer.valueOf(hierarchyBean.getNodeParentName()));
			deptdto.setStatus(1);
			deptdto.setCreationDate(CPSUtils.getCurrentDate());
			deptdto.setLastModifiedDate(CPSUtils.getCurrentDate());
		} catch (Exception e) {
			throw e;
		}
		return deptdto;
	}

	public List getAllDepartments(HierarchyBean hierarchyBean, List roleList) throws Exception {
		List instancelist = null;
		try {
			instancelist = hierarchyDAO.getAllTreeDepartments(hierarchyBean.getSfid(), roleList);
		} catch (Exception e) {
			throw e;
		}
		return instancelist;
	}
}