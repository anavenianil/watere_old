package com.callippus.web.controller.orgstructure;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrgLevelsDTO;
import com.callippus.web.beans.orgstructure.OrgStructureBean;
import com.callippus.web.business.orgstructure.OrgStructureBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/orgStructure.htm")
@SessionAttributes
public class OrgStructureController {
	private static Log log = LogFactory.getLog(OrgStructureController.class);
	@Autowired
	private OrgStructureBusiness orgStructureBusiness;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.ORGSTRUCTURE) OrgStructureBean orgStrBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(orgStrBean.getParam())) {
				orgStrBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, orgStrBean.getParam())) {
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.TYPE))) {
					orgStrBean.setMessage(session.getAttribute(CPSConstants.TYPE).toString());
				}
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			orgStrBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			orgStrBean.setEmpRoles((List<String>) session.getAttribute(CPSConstants.ROLELIST));

			// Levels Code Starts
			if (CPSUtils.compareStrings(CPSConstants.HOME, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=home");
				orgStrBean = orgStructureBusiness.getLevels(orgStrBean);
				session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getLevelsList());
				session.setAttribute(CPSConstants.ROLESLISTJSON, (JSONArray) JSONSerializer.toJSON((List<OrgLevelsDTO>) session.getAttribute(CPSConstants.ROLESLIST)));
				orgStrBean.setMessage(orgStrBean.getType());
				viewName = CPSConstants.ORGANISATIONALLEVELS;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ORGANISATIONALLEVELSLIST);
				session.setAttribute(CPSConstants.TYPE, orgStrBean.getMessage());
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITLEVEL, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=submitLevel");
				orgStrBean = orgStructureBusiness.submitLevels(orgStrBean);
				if (CPSUtils.checkList(orgStrBean.getLevelsList())) {
					session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getLevelsList());
				}
				session.setAttribute(CPSConstants.ROLESLISTJSON, (JSONArray) JSONSerializer.toJSON((List<OrgLevelsDTO>) session.getAttribute(CPSConstants.ROLESLIST)));
				orgStrBean.setMessage(orgStrBean.getType());
				viewName = CPSConstants.ORGANISATIONALLEVELSLIST;
			}
			// Levels Code Ends

			// Departments Code Start
			else if (CPSUtils.compareStrings(CPSConstants.DEPTHOME, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=deptHome");
				orgStrBean = orgStructureBusiness.getDepartmentsHomeDetails(orgStrBean);
				session.setAttribute(CPSConstants.DEPARTMENTSLIST, orgStrBean.getDepartmentsList());
				session.setAttribute(CPSConstants.DEPARTMENTSLISTJSON, (JSONArray) JSONSerializer.toJSON((List<OrgLevelsDTO>) session.getAttribute(CPSConstants.DEPARTMENTSLIST)));
				viewName = CPSConstants.DEPARTMENTSPAGE;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DEPARTMENTSLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITDEPARTMENT, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=submitDept");
				orgStrBean = orgStructureBusiness.submitDepartment(orgStrBean);
				if (CPSUtils.checkList(orgStrBean.getDepartmentsList())) {
					session.setAttribute(CPSConstants.DEPARTMENTSLIST, orgStrBean.getDepartmentsList());
				}
				session.setAttribute(CPSConstants.DEPARTMENTSLISTJSON, (JSONArray) JSONSerializer.toJSON((List<OrgLevelsDTO>) session.getAttribute(CPSConstants.DEPARTMENTSLIST)));
				viewName = CPSConstants.DEPARTMENTSLIST;
			}
			// Departments Code Ends

			// Get List
			else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.GETLIST)) {
				log.debug("OrgStructureController --> execute --> param=getList");

				orgStrBean = orgStructureBusiness.getChildList(orgStrBean);
				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPTREPORTSTO)) {
					session.setAttribute(CPSConstants.CHILDLIST, (JSONArray) JSONSerializer.toJSON(orgStrBean.getDepartmentsList()));
				} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLEREPORTSTO)) {
					session.setAttribute(CPSConstants.CHILDLIST, (JSONArray) JSONSerializer.toJSON(orgStrBean.getRolesList()));
				}

				viewName = CPSConstants.ORGCHILDLIST;
			}
			// Departments Code Start  ASL,DRDO Organization
			else if (CPSUtils.compareStrings("orgMaster", orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=orgMaster");
				orgStrBean = orgStructureBusiness.getOrganizations(orgStrBean);
				session.setAttribute("orgList", orgStrBean.getOrganizationList());
				if (CPSUtils.checkList(orgStrBean.getOrganizationList())) {
					session.setAttribute("jsonOrgList", (JSONArray) JSONSerializer.toJSON( session.getAttribute("orgList")));
				}
				viewName = "Organizations";
				session.setAttribute(CPSConstants.RETURN, "Organization");
			} else if (CPSUtils.compareStrings("submitOrg", orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=submitOrg");
				orgStrBean = orgStructureBusiness.submitOrganization(orgStrBean);
				orgStrBean = orgStructureBusiness.getOrganizations(orgStrBean);
				
			
					session.setAttribute("orgList", orgStrBean.getOrganizationList());
				
				if (CPSUtils.checkList(orgStrBean.getOrganizationList())) {
				session.setAttribute("jsonOrgList", (JSONArray) JSONSerializer.toJSON( session.getAttribute("orgList")));
				}
				viewName = "OrganizationsList";
				session.setAttribute(CPSConstants.RETURN, "OrganizationsList");
			}
			// Departments Code Ends

			
			// Roles Code Starts
			else if (CPSUtils.compareStrings(CPSConstants.ROLEHOME, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=roleHome");
				orgStrBean = orgStructureBusiness.getOrgRoleDetails(orgStrBean);
				session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getRolesList());

				session.setAttribute(CPSConstants.ROLELISTJSON, (JSONArray) JSONSerializer.toJSON(orgStrBean.getRolesList()));

				viewName = CPSConstants.ORGROLESTRUCTURE;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ORGROLESTRUCTURELIST);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITROLE, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=submitRole");
				orgStrBean = orgStructureBusiness.submitRole(orgStrBean);
				if (CPSUtils.checkList(orgStrBean.getRolesList())) {
					session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getRolesList());
				}
				session.setAttribute(CPSConstants.ROLELISTJSON, (JSONArray) JSONSerializer.toJSON(orgStrBean.getRolesList()));
				viewName = CPSConstants.ORGROLESTRUCTURELIST;
			}
			// Roles Code Ends

			// Delete code for levels,Departments & Roles
			else if (CPSUtils.compareStrings(CPSConstants.DELETE, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=delete");
				orgStrBean = orgStructureBusiness.deleteOrgStructure(orgStrBean);
				
				orgStrBean = orgStructureBusiness.getOrgRoleDetails(orgStrBean);
				
				//session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getRolesList());
				//session.setAttribute(CPSConstants.ROLELISTJSON, (JSONArray) JSONSerializer.toJSON(orgStrBean.getRolesList()));
				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENTLEVEL) || CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLELEVEL)) {
					if (CPSUtils.checkList(orgStrBean.getLevelsList())) {
						session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getLevelsList());
					}
					orgStrBean.setMessage(orgStrBean.getType());
					viewName = CPSConstants.ORGANISATIONALLEVELSLIST;
				} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.DEPARTMENT)) {
					if (CPSUtils.checkList(orgStrBean.getDepartmentsList())) {
						session.setAttribute(CPSConstants.DEPARTMENTSLIST, orgStrBean.getDepartmentsList());
					}
					viewName = CPSConstants.DEPARTMENTSLIST;
				} else if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.ROLE)) {

					if (CPSUtils.checkList(orgStrBean.getRolesList())) {
						session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getRolesList());
					}
					viewName = CPSConstants.ORGROLESTRUCTURELIST;
				}
				else if (CPSUtils.compareStrings(orgStrBean.getType(), "organizations")) {
					orgStrBean = orgStructureBusiness.getOrganizations(orgStrBean);
			
					session.setAttribute("orgList", orgStrBean.getOrganizationList());
				
					if (CPSUtils.checkList(orgStrBean.getOrganizationList())) {
						session.setAttribute("jsonOrgList", (JSONArray) JSONSerializer.toJSON( session.getAttribute("orgList")));
					}
					viewName = "OrganizationsList";
					session.setAttribute(CPSConstants.RETURN, "OrganizationsList");
				}
			}
			// Delete code ends

			// Employee Role Mapping code starts
			else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.EMPROLEMAP)) {
				log.debug("OrgStructureController --> execute --> param=empRoleMap");
				orgStrBean = orgStructureBusiness.roleEmployeesDetails(orgStrBean);

				session.setAttribute(CPSConstants.ROLEEMPLOYEESLIST, orgStrBean.getRoleEmployeesList());
				viewName = CPSConstants.EMPLOYEEROLEMAPPING;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.EMPLOYEEROLEMAPPINGLIST);
			} else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.SUBMITEMPROLE)) {
				log.debug("OrgStructureController --> execute --> param=submitEmpRole");
				orgStrBean = orgStructureBusiness.submitEmployeeRole(orgStrBean);
				if (CPSUtils.checkList(orgStrBean.getRoleEmployeesList())) {
					session.setAttribute(CPSConstants.ROLEEMPLOYEESLIST, orgStrBean.getRoleEmployeesList());
				}
				viewName = CPSConstants.EMPLOYEEROLEMAPPINGLIST;
			} else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.DELETEEMPROLE)) {
				log.debug("OrgStructureController --> execute --> param=deleteEmpRole");

				orgStrBean = orgStructureBusiness.deleteEmployeeRole(orgStrBean);
				if (CPSUtils.checkList(orgStrBean.getRoleEmployeesList())) {
					session.setAttribute(CPSConstants.ROLEEMPLOYEESLIST, orgStrBean.getRoleEmployeesList());
				}
				viewName = CPSConstants.EMPLOYEEROLEMAPPINGLIST;
			}
			// Employee Role Mapping code ends

			// Normal Employee Mapping code starts
			else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.NORMALEMP)) {
				orgStrBean = orgStructureBusiness.normalEmployeesDetails(orgStrBean);
				session.setAttribute(CPSConstants.NORMALEMPLOYEESLIST, orgStrBean.getNormalEmpDetails());
				if (CPSUtils.checkList(orgStrBean.getRolesList())) {
					session.setAttribute(CPSConstants.ROLESLIST, orgStrBean.getRolesList());
				}
				viewName = CPSConstants.NORMALEMPLOYEEMAPPING;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.NORMALEMPLOYEEMAPPINGLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEEMP, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=deleteEmp");

				orgStrBean = orgStructureBusiness.deleteEmployees(orgStrBean);
				if (CPSUtils.checkList(orgStrBean.getNormalEmpDetails())) {
					session.setAttribute(CPSConstants.NORMALEMPLOYEESLIST, orgStrBean.getNormalEmpDetails());
				}
				viewName = CPSConstants.NORMALEMPLOYEEMAPPINGLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITNORMALEMP, orgStrBean.getParam())) {
				log.debug("OrgStructureController --> execute --> param=submitNormalEmp");
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.ROLESLIST))) {
					orgStrBean.setRolesList((List<OrgInstanceDTO>) session.getAttribute(CPSConstants.ROLESLIST));
				}

				orgStrBean = orgStructureBusiness.submitEmployees(orgStrBean);
				if (CPSUtils.checkList(orgStrBean.getNormalEmpDetails())) {
					session.setAttribute(CPSConstants.NORMALEMPLOYEESLIST, orgStrBean.getNormalEmpDetails());
				}
				viewName = CPSConstants.NORMALEMPLOYEEMAPPINGLIST;
			}
			// Normal Employee Mapping code ends

			// Search starts
			else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.SEARCH)) {
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.ROLESLIST))) {
					orgStrBean.setRolesList((List<OrgInstanceDTO>) session.getAttribute(CPSConstants.ROLESLIST));
				}

				orgStrBean = orgStructureBusiness.searchEmployeeDetails(orgStrBean);

				if (CPSUtils.compareStrings(orgStrBean.getType(), CPSConstants.NORMAL)) {
					session.setAttribute(CPSConstants.NORMALEMPLOYEESLIST, orgStrBean.getNormalEmpDetails());
					viewName = CPSConstants.NORMALEMPLOYEEMAPPINGLIST;
				} else {
					session.setAttribute(CPSConstants.ROLEEMPLOYEESLIST, orgStrBean.getRoleEmployeesList());
					viewName = CPSConstants.EMPLOYEEROLEMAPPINGLIST;
				}
			}
			// Search ends

			// User specific starts
			else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.ROLESPECIFIC)) {
				orgStrBean = orgStructureBusiness.getRoleSpecificDetails(orgStrBean);
				session.setAttribute(CPSConstants.USERSPECIFICLIST, orgStrBean.getUserSpecificList());
				session.setAttribute(CPSConstants.DESIGNATIONLISTJSON, orgStrBean.getDesignationsList());
				session.setAttribute(CPSConstants.REQUESTLISTJSON, orgStrBean.getRequestTypeList());

				session.setAttribute(CPSConstants.SUBORDINATESLIST, orgStrBean.getSubordinatesList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ROLESPECIFICCONFIGURATIONLIST);
				viewName = CPSConstants.ROLESPECIFICCONFIGURATION;
			} else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.GETSUBORDINATES)||CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.GETSUBORDINATES2)) {
				orgStrBean = orgStructureBusiness.getSubordinatesList(orgStrBean);
				session.setAttribute(CPSConstants.SUBORDINATESLIST, orgStrBean.getSubordinatesList());
				if(CPSUtils.compareStrings("displayTable", orgStrBean.getType()) || CPSUtils.checkList(orgStrBean.getUserSpecificList())) {
					session.setAttribute(CPSConstants.USERSPECIFICLIST, orgStrBean.getUserSpecificList());
				}
				orgStrBean.setMessage(orgStrBean.getType());
				viewName = CPSConstants.ROLESPECIFICCONFIGURATIONLIST;
			} else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.SUBMITROLECONF)) {
				orgStrBean = orgStructureBusiness.submitConfiguration(orgStrBean);
				session.setAttribute(CPSConstants.USERSPECIFICLIST, orgStrBean.getUserSpecificList());
				viewName = CPSConstants.ROLESPECIFICCONFIGURATIONLIST;
			} else if (CPSUtils.compareStrings(orgStrBean.getParam(), CPSConstants.DELETEROLECONF)) {
				orgStrBean = orgStructureBusiness.deleteConfiguration(orgStrBean);
				session.setAttribute(CPSConstants.USERSPECIFICLIST, orgStrBean.getUserSpecificList());
				viewName = CPSConstants.ROLESPECIFICCONFIGURATIONLIST;
			}

			// User specific ends
			mav = new ModelAndView(viewName, CPSConstants.ORGSTRUCTURE, orgStrBean);
			if (!CPSUtils.isNullOrEmpty(orgStrBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, orgStrBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(orgStrBean.getResult())) {
				mav.addObject(CPSConstants.RESULT, orgStrBean.getResult());
			}
			if (!CPSUtils.isNullOrEmpty(orgStrBean.getRemarks())) {
				mav.addObject(CPSConstants.REMARKS, orgStrBean.getRemarks());
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}