package com.callippus.web.controller.empExperience;

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

import com.callippus.web.beans.empExperience.EmpExperienceBean;
import com.callippus.web.business.empExperience.EmpExperienceBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/empExperience.htm")
@SessionAttributes
public class EmpExperienceController {

	private static Log log = LogFactory.getLog(EmpExperienceController.class);
	@Autowired
	private EmpExperienceBusiness empExperienceBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.EMPEXPERIENCE) EmpExperienceBean empExpBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		List experienceDetails = null;
		HttpSession session = null;
		String message = "";
		String viewPage = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.compareStrings(CPSConstants.PAGING, empExpBean.getParam())) {
				viewPage = CPSConstants.EXPERIENCEDETAILSLIST;
				mav = new ModelAndView(viewPage, CPSConstants.EMPEXPERIENCE, empExpBean);
			} else if (CPSUtils.isNullOrEmpty(empExpBean.getParam())) {
				empExpBean.setParam(CPSConstants.GETEXPDETAILS);
			}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				empExpBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				if (CPSUtils.compareStrings(CPSConstants.CREATE, empExpBean.getParam())) {
					log.debug("EmpExperienceController --> onSubmit --> param=home");
					message = empExperienceBusiness.saveExperienceDetails(empExpBean);
					viewPage = CPSConstants.EXPERIENCEDETAILSLIST;
					mav = new ModelAndView(viewPage, CPSConstants.EMPEXPERIENCE, empExpBean);

				} else if (CPSUtils.compareStrings(CPSConstants.GETEXPERIENCE, empExpBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("EmpExperienceController --> onSubmit --> param=getEmpExp");
						// get employee experience details
						experienceDetails = empExperienceBusiness.getEmpExperienceDetails(request.getParameter(CPSConstants.SFID));
						session.setAttribute(CPSConstants.RETURN, CPSConstants.EXPERIENCEDETAILS);
						if (!CPSUtils.isNull(session.getAttribute(CPSConstants.GETEXPDETAILS))
								&& CPSUtils.compareStrings(session.getAttribute(CPSConstants.GETEXPDETAILS).toString(), CPSConstants.YES)) {
							message = CPSConstants.GETEXPDETAILS;
						}
						viewPage = CPSConstants.EXPERIENCEDETAILS;
						mav = new ModelAndView(viewPage, CPSConstants.EMPEXPERIENCE, empExpBean);
						// set the list into session

					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} else if (CPSUtils.compareStrings(CPSConstants.EDITEXPERIENCE, empExpBean.getParam())) {
					// log.debug("EmpExperienceController --> onSubmit --> param=getEmpMember");
					// // get the employee experience details from the session &
					// set it in form
					// empExpBean=empExperienceBusiness.EditExperience(empExpBean);
					// empExpBean.setParam(CPSConstants.EDITEXPERIENCE);
					// mav = new ModelAndView(CPSConstants.EXPERIENCEDETAILS,
					// CPSConstants.EMPEXPERIENCE, empExpBean);
					// if(!CPSUtils.isNull(session.getAttribute(CPSConstants.GETEXPDETAILS))
					// &&
					// CPSUtils.compareStrings(session.getAttribute(CPSConstants.GETEXPDETAILS).toString(),CPSConstants.YES))
					// {
					// mav.addObject(CPSConstants.MESSAGE,CPSConstants.GETEXPDETAILS);
					// }
				} else if (CPSUtils.compareStrings(CPSConstants.UPDATE, empExpBean.getParam())) {
					log.debug("EmpExperienceController --> onSubmit --> param=update");
					message = empExperienceBusiness.updateEmpExperience(empExpBean);

				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, empExpBean.getParam())) {
					log.debug("EmpExperienceController --> onSubmit --> param=delete");
					message = empExperienceBusiness.deleteEmpExperience(empExpBean);
					viewPage = CPSConstants.EXPERIENCEDETAILSLIST;
					mav = new ModelAndView(viewPage, CPSConstants.EMPEXPERIENCE, empExpBean);
				}
			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					viewPage = CPSConstants.EXPERIENCEDETAILS;
					mav = new ModelAndView(viewPage, CPSConstants.EMPEXPERIENCE, empExpBean);
					message = CPSConstants.CHANGESFIDFIRST;
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.GETEXPDETAILS, empExpBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("EmpExperienceController --> onSubmit --> param=getExperienceDetails");
					// get employee experience details
					empExpBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					session.setAttribute(CPSConstants.GETEXPDETAILS, CPSConstants.YES);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
					viewPage = CPSConstants.VIEWMASTER;
					mav = new ModelAndView(viewPage, CPSConstants.EMPEXPERIENCE, empExpBean);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			experienceDetails = empExperienceBusiness.getEmpExperienceDetails(empExpBean.getSfid());
			int size = experienceDetails.size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, experienceDetails.get(size - 1));
				experienceDetails.remove(size - 1);
			}
			session.setAttribute(CPSConstants.EXPERIENCELIST, experienceDetails);
			if (!CPSUtils.isNullOrEmpty(message)) {
				mav.addObject(CPSConstants.MESSAGE, message);
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}