package com.callippus.web.controller.empsearch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.master.MasterDataBean;
import com.callippus.web.beans.paybill.PayBillMasterBean;
import com.callippus.web.business.empsearch.EmployeeSearchBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller

@SessionAttributes
public class EmployeeSearchController {
	private static Log log = LogFactory.getLog(EmployeeSearchController.class);
	@Autowired
	private EmployeeSearchBusiness employeeSearchBusiness;
	@RequestMapping(value="/empSearch.htm", method = { RequestMethod.POST, RequestMethod.GET })
	
	public ModelAndView execute(@ModelAttribute(CPSConstants.EMPLOYEE) EmployeeBean employeeBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.compareStrings(CPSConstants.PAGING, employeeBean.getParam())) {
				mav = new ModelAndView(CPSConstants.EMPSEARCHDETAILSLIST, CPSConstants.EMPLOYEE, employeeBean);
			} else if (CPSUtils.isNullOrEmpty(employeeBean.getParam())) {
				employeeBean.setParam(CPSConstants.HOME);
			}

			if (employeeBean.getParam() != CPSConstants.EMPTREE || employeeBean.getParam() != CPSConstants.EMPTREESEARCH) {
				session.removeAttribute(CPSConstants.EMPTREEIDENTITY);
				session.removeAttribute(CPSConstants.DESIGNATIONLIST);
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, employeeBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("EmployeeSearchController --> onSubmit --> param=home");
					mav = new ModelAndView(CPSConstants.EMPSEARCHDETAILS, CPSConstants.EMPLOYEE, employeeBean);
					employeeBean = employeeSearchBusiness.getMasterData(employeeBean);
					// mav.addObject(CPSConstants.DESIGNATIONLIST,
					// employeeBean.getDesignationList());
					if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DESIGNATIONLIST))) {
						session.setAttribute(CPSConstants.DESIGNATIONLIST, employeeBean.getDesignationList());
					}
					session.setAttribute(CPSConstants.RETURN, CPSConstants.EMPSEARCHDETAILS);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.GETEMPDETAILS, employeeBean.getParam())) {
				log.debug("EmployeeSearchController --> onSubmit --> param=getDetails");
				employeeBean.setUserSfid(session.getAttribute(CPSConstants.SFID).toString());
				employeeBean = employeeSearchBusiness.getEmployeeSearchList(employeeBean, session);
				mav = new ModelAndView(CPSConstants.EMPSEARCHDETAILSLIST, CPSConstants.EMPLOYEE, employeeBean);
				session.setAttribute(CPSConstants.EMPSEARCHLIST, employeeBean.getEmployeeList());
			} else if (CPSUtils.compareStrings(CPSConstants.GETEMPSEARCHDETAILS, employeeBean.getParam())) {

				employeeBean.setSfid(request.getParameter(CPSConstants.ID));
				employeeBean.setLoginSfid(session.getAttribute(CPSConstants.SFID).toString());
				employeeBean.setRoleList((ArrayList) session.getAttribute(CPSConstants.ROLESLIST));
				
				//EmployeeBean employeeBean1 = employeeSearchBusiness.getEmployeeSearchList(employeeBean, session);
				employeeBean = employeeSearchBusiness.viewEmployeeDetails(employeeBean);
				employeeBean = employeeSearchBusiness.empRolesDetails(employeeBean);
				employeeBean = employeeSearchBusiness.empPayDetails(employeeBean);
				
				if (!CPSUtils.isNull(employeeBean.getEmpTreeDetailsJson()) && employeeBean.getEmpTreeDetailsJson().size() > 0)
					session.setAttribute(CPSConstants.JSONEMPTREEDETAILS, employeeBean.getEmpTreeDetailsJson());
				if (employeeBean.getEmpSearchId().equals("yes"))
					mav = new ModelAndView(CPSConstants.TREEEMPDETAILSJSP, CPSConstants.EMPLOYEE, employeeBean);
				else
					mav = new ModelAndView(CPSConstants.EMPLOYEESEARCHDETAILS, CPSConstants.EMPLOYEE, employeeBean);
				mav.addObject(CPSConstants.EMPTREEIDENTITY, request.getParameter(CPSConstants.EMPTREEIDENTITY));
			} else if (CPSUtils.compareStrings(CPSConstants.VIEW, employeeBean.getParam())) {
				employeeBean.setSfid(request.getParameter(CPSConstants.ID));
				mav = new ModelAndView(CPSConstants.EMPSEARCHDETAILS, CPSConstants.EMPLOYEE, employeeBean);
			} else if (CPSUtils.compareStrings(CPSConstants.EMPTREE, employeeBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("EmployeeSearchController --> onSubmit --> param=empTree");
					employeeBean.setSearchingWith("");
					employeeBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					employeeBean.setRoleList((ArrayList<String>) session.getAttribute(CPSConstants.ROLELIST));
					employeeBean = employeeSearchBusiness.getEmployeeTreeDetails(employeeBean, session);
					mav = new ModelAndView(CPSConstants.EMPTREEDETAILS, CPSConstants.EMPLOYEE, employeeBean);
					if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.EMPTREEIDENTITY)))
						session.setAttribute(CPSConstants.EMPTREEIDENTITY, CPSConstants.YES);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.EMPTREEDETAILS);
					if ((CPSUtils.compareStrings("tree", employeeBean.getType()))) {
						mav = new ModelAndView("EmployeeTreeView", CPSConstants.EMPLOYEE, employeeBean);
					}
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param") + "&type=" + request.getParameter("type"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings("tree", employeeBean.getParam())) {
				log.debug("EmployeeSearchController --> onSubmit --> Ajax call param=empTreeSearch");
				employeeBean.setRoleList((ArrayList<String>) session.getAttribute(CPSConstants.ROLELIST));
				employeeBean = employeeSearchBusiness.getTreeDetails(employeeBean);
				if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.EMPTREEIDENTITY)))
					session.setAttribute(CPSConstants.EMPTREEIDENTITY, CPSConstants.YES);
				mav = new ModelAndView("EmpSearchTreeDetails", CPSConstants.EMPLOYEE, employeeBean);
			} else if (CPSUtils.compareStrings(CPSConstants.EMPTREESEARCH, employeeBean.getParam())) {
				log.debug("EmployeeSearchController --> onSubmit --> param=empTreeSearch");
				employeeBean.setRoleList((ArrayList<String>) session.getAttribute(CPSConstants.ROLELIST));
				employeeBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				employeeBean = employeeSearchBusiness.getEmpTreeSearch(employeeBean, session);
				session.setAttribute(CPSConstants.EMPSEARCHLIST, employeeBean.getEmpSearchList());
				mav = new ModelAndView("EmpSearchTreeDetails", CPSConstants.EMPLOYEE, employeeBean);
				session.setAttribute(CPSConstants.EMPTREEIDENTITY, CPSConstants.YES);
				if ((CPSUtils.compareStrings("tree", employeeBean.getType()))) {
					mav = new ModelAndView("TreeView", CPSConstants.EMPLOYEE, employeeBean);
				}
			} else if (CPSUtils.compareStrings(CPSConstants.TREEEMPDETAILS, employeeBean.getParam())) {
				employeeBean.setSfid(request.getParameter(CPSConstants.ID));
				employeeBean.setName(request.getParameter(CPSConstants.TYPE).trim());
				employeeBean = employeeSearchBusiness.downTreeEmployeeDetails(employeeBean);
				if (employeeBean.getEmpTreeDetailsJson().size() > 0)
					session.setAttribute(CPSConstants.JSONEMPTREEDETAILS, employeeBean.getEmpTreeDetailsJson());
				employeeBean.setEmpSearchId(request.getParameter(CPSConstants.EMPSEARCHID));
				mav = new ModelAndView(CPSConstants.TREEEMPDETAILSJSP, CPSConstants.EMPLOYEE, employeeBean);
			} else if (CPSUtils.compareStrings("employeeAllDetails", employeeBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					employeeBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					employeeBean = employeeSearchBusiness.getTreeEmployeeJson(employeeBean);
					employeeBean = employeeSearchBusiness.empRolesDetails(employeeBean);
					employeeBean = employeeSearchBusiness.empPayDetails(employeeBean);
					mav = new ModelAndView(CPSConstants.TREEEMPDETAILSJSP, CPSConstants.EMPLOYEE, employeeBean);
					session.setAttribute(CPSConstants.JSONEMPTREEDETAILS, employeeBean.getEmpTreeDetailsJson());
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}

			if (CPSUtils.checkList(employeeBean.getEmpInstanceList())) {
				mav.addObject(CPSConstants.INSTANCELIST, employeeBean.getEmpInstanceList());
			}
			if (CPSUtils.checkList(employeeBean.getEmpSearchList())) {
				mav.addObject(CPSConstants.EMPSEARCHLIST, employeeBean.getEmpSearchList());
			}
			

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	
	@RequestMapping(value = "/empName.htm", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=*/*")
	public void searchData(@ModelAttribute(CPSConstants.MASTER) MasterDataBean masterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		List<String> list = null; 
		try
		{
			String	query = request.getParameter("term").trim();
			masterBean.setType("letters");
			if(!query.isEmpty())
			{
				list = employeeSearchBusiness.getSearchList(query);
				org.json.JSONArray json = new org.json.JSONArray(list);
			    response.setContentType("text/json");
				response.getWriter().print(json.toString());
			}
		}catch (Exception e) 
		{
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} 
	}
	
}
