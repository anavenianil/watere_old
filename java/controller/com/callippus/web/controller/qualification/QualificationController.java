package com.callippus.web.controller.qualification;

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
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.qualification.QualificationBean;
import com.callippus.web.business.qualification.QualificationBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/qualification.htm")
@SessionAttributes
public class QualificationController {

	private static Log log = LogFactory.getLog(QualificationController.class);
	@Autowired
	private QualificationBusiness qualificationBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.QUALIFICATION) QualificationBean qualification, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(qualification.getParam())) {
				qualification.setParam(CPSConstants.VIEWQUALIFICATIONDETAILS);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, qualification.getParam())) {
				mav = new ModelAndView(CPSConstants.QUALIFICATIONLIST, CPSConstants.QUALIFICATION, qualification);
			}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONEMPQUALIFICATIONLIST))) {
				session.removeAttribute(CPSConstants.JSONEMPQUALIFICATIONLIST);
			}
			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				qualification.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				if (CPSUtils.compareStrings(CPSConstants.VIEW, qualification.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("QualificationController --> onSubmit --> param=view");
						qualification = qualificationBusiness.getEmpQualificationDetails(qualification);
						mav = new ModelAndView(CPSConstants.VIEWQUALIFICATION, CPSConstants.QUALIFICATION, qualification);
						session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWQUALIFICATION);
						if (CPSUtils.checkList(qualification.getQualificationList())) {
							// mav.addObject(CPSConstants.QUALLIST, qualification.getQualificationList());
							// if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.QUALLIST))) {
							session.setAttribute(CPSConstants.QUALLIST, qualification.getQualificationList());
							// }

						}
						if (CPSUtils.checkList(qualification.getYearList())) {
							// mav.addObject(CPSConstants.YEARLIST, qualification.getYearList());
							// if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.YEARLIST))) {
							session.setAttribute(CPSConstants.YEARLIST, qualification.getYearList());
							// }
						}
						if (CPSUtils.checkList(qualification.getDesciplineList())) {
							// mav.addObject(CPSConstants.DESCIPLINELIST, qualification.getDesciplineList());
							// if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DESCIPLINELIST))) {
							session.setAttribute(CPSConstants.DESCIPLINELIST, qualification.getDesciplineList());
							// }
						}
						if (CPSUtils.checkList(qualification.getDegreeList())) {
							// mav.addObject(CPSConstants.DEGREELIST, qualification.getDegreeList());
							// if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DEGREELIST))) {
							session.setAttribute(CPSConstants.DEGREELIST, qualification.getDegreeList());
							// }
						}
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, qualification.getParam())) {
					log.debug("QualificationController --> onSubmit --> param=manage");
					qualification = qualificationBusiness.manageQualification(qualification);
					mav = new ModelAndView(CPSConstants.QUALIFICATIONLIST, CPSConstants.QUALIFICATION, qualification);

				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, qualification.getParam())) {
					log.debug("QualificationController --> onSubmit --> param=delete");
					qualification = qualificationBusiness.deleteQualification(qualification);
					mav = new ModelAndView(CPSConstants.QUALIFICATIONLIST, CPSConstants.QUALIFICATION, qualification);
				}

			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					mav = new ModelAndView(CPSConstants.VIEWQUALIFICATION, CPSConstants.QUALIFICATION, qualification);
					qualification.setMessage(CPSConstants.CHANGESFIDFIRST);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.VIEWQUALIFICATIONDETAILS, qualification.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("QualificationController --> onSubmit --> param=ViewQualificationDetails");
					qualification.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					qualification = qualificationBusiness.getEmpQualificationDetails(qualification);
					mav = new ModelAndView(CPSConstants.VIEWMASTER, CPSConstants.QUALIFICATION, qualification);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);

				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}

			}
			mav.addObject(CPSConstants.MESSAGE, qualification.getMessage());
			if (CPSUtils.checkList(qualification.getEmpQualificationList())) {
				int size = qualification.getEmpQualificationList().size();
				if (size > 0) {
					session.setAttribute(CPSConstants.MASTERJSON, qualification.getEmpQualificationList().get(size - 1));
					qualification.getEmpQualificationList().remove(size - 1);
				}
				session.setAttribute(CPSConstants.JSONEMPQUALIFICATIONLIST, (JSONArray) JSONSerializer.toJSON(qualification.getEmpQualificationList()));
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
