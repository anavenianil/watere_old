package com.callippus.web.business.empsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.award.AwardDetails;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.nominee.NomineeBean;
import com.callippus.web.beans.preOrgnDetails.PreOrgnDetailsBean;
import com.callippus.web.beans.qualification.QualificationBean;
import com.callippus.web.beans.training.TrainingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.address.IAddressDAO;
import com.callippus.web.dao.award.IAwardDAO;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.empExperience.IEmpExperienceDAO;
import com.callippus.web.dao.employee.ICreateEmployeeDAO;
import com.callippus.web.dao.empsearch.IEmployeeSearchDAO;
import com.callippus.web.dao.family.IFamilyDAO;
import com.callippus.web.dao.nominee.INomineeDAO;
import com.callippus.web.dao.passport.IPassportDAO;
import com.callippus.web.dao.preOrgnDetails.IPreOrgnDetailsDAO;
import com.callippus.web.dao.qualification.IQualificationDAO;
import com.callippus.web.dao.quarter.IQuarterDAO;
import com.callippus.web.dao.training.ITrainingDAO;

@Service
public class EmployeeSearchBusiness {
	@Autowired
	private IEmployeeSearchDAO employeeSearchDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private ICreateEmployeeDAO createEmployeeDAO;
	@Autowired
	private IAddressDAO addressDAO;
	@Autowired
	private IQualificationDAO qualificationDAO;
	@Autowired
	private ITrainingDAO trainingDAO;
	@Autowired
	private IPassportDAO passportDAO;
	@Autowired
	private IFamilyDAO familyDAO;
	@Autowired
	private IEmpExperienceDAO empExpDAO;
	@Autowired
	private INomineeDAO nomineeDAO;
	@Autowired
	private IPreOrgnDetailsDAO preOrgnDAO;
	@Autowired
	private IAwardDAO awardDAO;
	@Autowired
	private IQuarterDAO quarterDAO;

	public EmployeeBean getEmpTreeSearch(EmployeeBean employeeBean, HttpSession session) throws Exception {
		try {
			if (!CPSUtils.isNull(employeeBean.getType()) && CPSUtils.compareStrings(employeeBean.getType(), "tree")) {
				List<Object> treeList = employeeSearchDAO.getOrganizationTree(employeeBean.getInstanceId());

				HashMap hm = new HashMap();

				if (CPSUtils.checkList(treeList)) {
					for (int i = 0; i < treeList.size(); i++) {
						HashMap dept = (HashMap) treeList.get(i);
						JSONObject node = new JSONObject();
						createNode(hm, node, dept);
					}
				}

				for (int i = 0; i < treeList.size(); i++) {
					HashMap dept = (HashMap) treeList.get(i);
					String parentId = dept.get("PARENT_DEPARTMENT_ID").toString();
					String myId = dept.get("DEPARTMENT_ID").toString();
					if (!parentId.equals("0")) {
						JSONObject parentNode = (JSONObject) hm.get(parentId);
						if (parentNode != null) {
							JSONArray parentsChildren = parentNode.getJSONArray("children");
							JSONObject myNode = ((JSONObject) hm.get(myId));
							parentsChildren.add(myNode);
							parentNode.put("children", parentsChildren);
							hm.put(parentId, parentNode);
						}
					}
				}
				session.setAttribute("tree", hm.get(employeeBean.getInstanceId()));
			} else {
				employeeBean = employeeSearchDAO.getEmployeeTreeDetails(employeeBean);
			}

		} catch (Exception e) {
			throw e;
		}
		return employeeBean;
	}

	public EmployeeBean getEmployeeSearchList(EmployeeBean empbean, HttpSession session) throws Exception {

		try {
			empbean.setEmployeeList(employeeSearchDAO.getEmpDetails(empbean));
		} catch (Exception e) {
			throw e;
		}
		return empbean;
	}

	@SuppressWarnings("unchecked")
	public EmployeeBean getMasterData(EmployeeBean empbean) throws Exception {
		try {
			empbean.setDesignationList(commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO));
		} catch (Exception e) {
			throw e;
		}
		return empbean;
	}

	public EmployeeBean viewEmployeeDetails(EmployeeBean employee) throws Exception {
		List empdetailsList = null;
		try {
			if (employee.getRoleList().contains("ROLE_SUPERADMIN") || employee.getRoleList().contains("ROLE_MIS_ADMIN")) {
				employee = getTreeEmployeeJson(employee);
				employee.setEmpSearchId("yes");
			} else if (employeeSearchDAO.checkDownTree(employee.getSfid(), employee.getLoginSfid())) {

				employee = downTreeEmployeeDetails(employee);
				employee.setEmpSearchId("yes");
			} else {
				employee = employeeSearchDAO.getEmployeeDetails(employee);
			}
		} catch (Exception e) {
			throw e;
		}
		return employee;
	}
	public EmployeeBean empRolesDetails(EmployeeBean employee) throws Exception {
		List<EmployeeBean> empRolesList = null;
		try {
			employee = employeeSearchDAO.empRolesList(employee);
		} catch (Exception e) {
			throw e;
		}
		return employee;
	}
	public EmployeeBean empPayDetails(EmployeeBean employee) throws Exception {
		List<EmployeeBean> empPaylist = null;
		try {
			employee = employeeSearchDAO.empPayList(employee);
		} catch (Exception e) {
			throw e;
		}
		return employee;
	}
	@SuppressWarnings("unchecked")
	public EmployeeBean getEmployeeTreeDetails(EmployeeBean employeeBean, HttpSession session) throws Exception {
		try {
			List rolelist = (List) session.getAttribute(CPSConstants.ROLELIST);
			if (rolelist.contains("ROLE_ADMIN") || rolelist.contains("ROLE_SUPERADMIN")) {
				session.setAttribute(CPSConstants.INSTANCELISTJSON, JSONSerializer.toJSON(commonDataDAO.getMasterData(CPSConstants.ORGINSTANCEDTO)));
				employeeBean.setHeadList(employeeSearchDAO.getHeadList());
			}
			if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DESIGNATIONLISTJSON))) {
				employeeBean.setDesignationList(commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO));
				session.setAttribute(CPSConstants.DESIGNATIONLISTJSON, JSONSerializer.toJSON(employeeBean.getDesignationList()));
			}

			employeeBean = employeeSearchDAO.getEmployeeTreeDetails(employeeBean);
			if (!rolelist.contains("ROLE_SUPERADMIN")) {
				if (!CPSUtils.isNullOrEmpty(employeeBean.getInstanceList())) {
					session.setAttribute(CPSConstants.INSTANCELISTJSON, JSONSerializer.toJSON(employeeBean.getInstanceList()));
				}
			}
			// session.setAttribute(CPSConstants.EMPSEARCHLIST, employeeBean.getEmpSearchList());

		} catch (Exception e) {
			throw e;
		}
		return employeeBean;
	}

	public EmployeeBean getTreeDetails(EmployeeBean employeeBean) throws Exception {
		try {
			employeeBean = employeeSearchDAO.getEmployeeTreeDetails(employeeBean);
		} catch (Exception e) {
			throw e;
		}
		return employeeBean;
	}

	public void createNode(HashMap hm, JSONObject node, HashMap dept) {
		JSONArray children = new JSONArray();
		node.put("id", dept.get("DEPARTMENT_ID").toString());
		node.put("name", dept.get("DEPARTMENT_NAME") + "-" + dept.get("NAME"));
		node.put("data", dept.get("PARET_DEPARTMENT_ID"));
		node.put("children", children);
		hm.put(dept.get("DEPARTMENT_ID").toString(), node);
	}

	public EmployeeBean getTreeEmployeeJson(EmployeeBean employeeBean) throws Exception {
		ArrayList list = null;
		JSONObject empTreeJson = null;
		try {
			int i = 0;
			empTreeJson = new JSONObject();
			list = new ArrayList();
			
			employeeBean = createEmployeeDAO.viewEmployeeDetails(employeeBean);///This modified newly.
			// Address Details
			AddressBean addressBean = new AddressBean();
			addressBean.setSfid(employeeBean.getSfid());
			list = addressDAO.getEmployeeAddressDetails(addressBean);
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Qualification Details
			QualificationBean qualification = new QualificationBean();
			qualification.setSfid(employeeBean.getSfid());
			list = qualificationDAO.getEmpQualificationDetails(qualification);
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Training Details
			TrainingBean training = new TrainingBean();
			training.setSfid(employeeBean.getSfid());
			list = trainingDAO.getTrainingDetails(training);
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Passport Details
			list = passportDAO.getEmployeePassportDetails(employeeBean.getSfid());
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Family Details
			list = familyDAO.getFamilyDetails(employeeBean.getSfid());
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Experiance Details
			list = empExpDAO.getEmpExperienceDetails(employeeBean.getSfid());
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Nominee Details
			NomineeBean nomineBean = new NomineeBean();
			nomineBean.setChangedSfid(employeeBean.getSfid());
			nomineBean = nomineeDAO.getNomineeList(nomineBean);
			if (CPSUtils.checkList(nomineBean.getNomineeList())) {
				if (nomineBean.getNomineeList().size() > 0) {
					empTreeJson.put(String.valueOf(i), nomineBean.getNomineeList().get(nomineBean.getNomineeList().size() - 1));
					i++;
				}
			}
			// Pre-Organization Details
			PreOrgnDetailsBean preOrgn = new PreOrgnDetailsBean();
			preOrgn.setSfid(employeeBean.getSfid());
			list = preOrgnDAO.getPreOrgnDetails(preOrgn);
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Award Details
			AwardDetails awardBean = new AwardDetails();
			awardBean.setSfid(employeeBean.getSfid());
			list = awardDAO.getAwardHomeDetails(awardBean);
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			// Quaretr Details
			// QuarterBean quarterBean = new QuarterBean();
			// quarterBean.setSfID(employeeBean.getSfid());
			// list = quarterDAO.getQuarterHomeDetails(quarterBean);
			// if (CPSUtils.checkList(list)) {
			// if (list.size() > 0) {
			// empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
			// i++;
			// }
			// }
			employeeBean.setEmpTreeDetailsJson(empTreeJson);
			employeeBean.setEmpSearchId("details");
		} catch (Exception e) {
			throw e;
		}
		return employeeBean;
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

	public EmployeeBean downTreeEmployeeDetails(EmployeeBean employeeBean) throws Exception {

		ArrayList list = null;
		JSONObject empTreeJson = null;
		try {
			int i = 0;
			empTreeJson = new JSONObject();
			list = new ArrayList();
			employeeBean = employeeSearchDAO.getEmployeeDetails(employeeBean);

			// Address Details
			AddressBean addressBean = new AddressBean();
			addressBean.setSfid(employeeBean.getSfid());
			list = addressDAO.getEmployeeAddressDetails(addressBean);
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}

			// Qualification Details
			QualificationBean qualification = new QualificationBean();
			qualification.setSfid(employeeBean.getSfid());
			list = qualificationDAO.getEmpQualificationDetails(qualification);
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}

			// Family Details
			list = familyDAO.getFamilyDetails(employeeBean.getSfid());
			if (CPSUtils.checkList(list)) {
				if (list.size() > 0) {
					empTreeJson.put(String.valueOf(i), list.get(list.size() - 1));
					i++;
				}
			}
			employeeBean.setEmpTreeDetailsJson(empTreeJson);
			employeeBean.setType("downTree");
		} catch (Exception e) {
			throw e;
		}
		return employeeBean;

	}
	
	public List<String> getSearchList(String qry) throws Exception
	{
		List<String> list = null;
		try
		{
			list = employeeSearchDAO.getEmpNameList(qry);
		}catch (Exception e) 
		{
			throw e;
		}
		return list;
	}
	
	public String getSearchName(String qry) throws Exception
	{
		String st = "";
		try
		{
			//st = employeeSearchDAO.getEmpName(qry);
		}catch (Exception e) 
		{
			throw e;
		}
		return st;
	}
}