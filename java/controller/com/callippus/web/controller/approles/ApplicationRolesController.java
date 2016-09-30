package com.callippus.web.controller.approles;

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

import com.callippus.web.beans.approles.ApplicationRolesBean;
import com.callippus.web.business.approles.ApplicationRolesBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/approles.htm")
@SessionAttributes
public class ApplicationRolesController {

	private static Log log = LogFactory.getLog(ApplicationRolesController.class);
	@Autowired
	private ApplicationRolesBusiness approlesBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.APPROLES) ApplicationRolesBean appRolesBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = "";
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.compareStrings(CPSConstants.PAGING, appRolesBean.getParam())) {
				viewName = "ApplicationRolesList";
				mav = new ModelAndView(viewName, CPSConstants.APPROLES, appRolesBean);
			} else if (CPSUtils.isNullOrEmpty(appRolesBean.getParam())) {
				appRolesBean.setParam(CPSConstants.HOME);
			}

			if (CPSUtils.compareStrings(CPSConstants.HOME, appRolesBean.getParam())) {
				log.debug("::ApplicationRolesController --> onSubmit --> param=home");
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					appRolesBean = approlesBusiness.getHomeDetails(session);
					viewName = "ApplicationRoleMapping";
					session.setAttribute(CPSConstants.RETURN, viewName);
					mav = new ModelAndView(viewName, CPSConstants.APPROLES, appRolesBean);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.SEARCH, appRolesBean.getParam())) {
				log.debug("::ApplicationRolesController --> onSubmit --> param=search");
				appRolesBean = approlesBusiness.getSearchedDetails(appRolesBean, session);
				viewName = "ApplicationRolesList";
				mav = new ModelAndView(viewName, CPSConstants.APPROLES, appRolesBean);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMIT, appRolesBean.getParam())) {
				log.debug("::ApplicationRolesController --> onSubmit --> param=submit");
				appRolesBean = approlesBusiness.submitApplicationRoles(appRolesBean, session);
				viewName = "ApplicationRolesList";
				mav = new ModelAndView(viewName, CPSConstants.APPROLES, appRolesBean);
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, appRolesBean.getParam())) {
				log.debug("ApplicationRolesController --> onSubmit --> param=delete");
				appRolesBean = approlesBusiness.deleteApplicationDetails(appRolesBean);
				viewName = "ApplicationRolesList";
				session.setAttribute(CPSConstants.APPLICATIONROLESLIST, appRolesBean.getAppRolesMapList());
				mav = new ModelAndView(viewName, CPSConstants.APPROLES, appRolesBean);
			}
			mav.addObject(CPSConstants.MESSAGE, appRolesBean.getMessage());
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}