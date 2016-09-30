package com.callippus.web.controller.nominee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.nominee.NomineeBean;
import com.callippus.web.beans.requests.NomineeRequestBean;
import com.callippus.web.business.nominee.NomineeBusiness;
import com.callippus.web.business.requestprocess.NomineeRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/nominee.htm")
@SessionAttributes
public class NomineeController {
	private static Log log = LogFactory.getLog(NomineeController.class);
	@Autowired
	private NomineeBusiness nomineeBusiness;
	@Autowired
	private NomineeRequestProcess nomineeRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.EMPNOMINEE) NomineeBean nomineeBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		String message = null;
		String editNomineeId = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(nomineeBean.getParam())) {
				nomineeBean.setParam(CPSConstants.VIEWNOMINEE);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, nomineeBean.getParam())) {
				viewName = "NomineeList";
				mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
			}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				if (CPSUtils.compareStrings(CPSConstants.HOME, nomineeBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("NomineeController --> onSubmit --> param=home");
						nomineeBean.setChangedSfid((String) session.getAttribute(CPSConstants.CHANGESFID));
						// nomineeBean.setChangedSfidName((String)
						// session.getAttribute(CPSConstants.CHANGESFIDNAME));
						nomineeBean = nomineeBusiness.getNomineeHomeDetails(nomineeBean.getChangedSfid(), session);
						session.setAttribute("displayNomineeList", nomineeBean.getDisplayNomineeList());
						session.setAttribute("nomineeListJSON", (JSONArray) JSONSerializer.toJSON(nomineeBean.getDisplayNomineeList()));
						session.setAttribute("percentageList", nomineeBean.getPercentageList());
						session.setAttribute("familyMemberInNominee", nomineeBean.getFamilyMemberInNominee());

						viewName = "Nominee";
						message = "";
						session.setAttribute(CPSConstants.RETURN, viewName);
						mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				}
				if (CPSUtils.compareStrings(CPSConstants.SUBMIT, nomineeBean.getParam())) {
					log.debug("NomineeController --> onSubmit --> param=submit");
					nomineeBean.setChangedSfid((String) session.getAttribute(CPSConstants.CHANGESFID));
					nomineeBean = nomineeBusiness.submitNomineeDetails(nomineeBean);
					session.setAttribute("displayNomineeList", nomineeBean.getDisplayNomineeList());
					session.setAttribute("nomineeListJSON", (JSONArray) JSONSerializer.toJSON(nomineeBean.getDisplayNomineeList()));
					session.setAttribute("percentageList", nomineeBean.getPercentageList());
					session.setAttribute("familyMemberInNominee", nomineeBean.getFamilyMemberInNominee());
					viewName = "NomineeList";
					mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
					message = nomineeBean.getMessage();
				}
				if (CPSUtils.compareStrings(CPSConstants.DELETE, nomineeBean.getParam())) {
					log.debug("NomineeController --> onSubmit --> param=delete");
					nomineeBean.setChangedSfid((String) session.getAttribute(CPSConstants.CHANGESFID));
					nomineeBean.setMessage(nomineeBusiness.deleteNomineeDetails(nomineeBean));
					session.setAttribute("displayNomineeList", nomineeBean.getDisplayNomineeList());
					session.setAttribute("nomineeListJSON", (JSONArray) JSONSerializer.toJSON(nomineeBean.getDisplayNomineeList()));
					session.setAttribute("percentageList", nomineeBean.getPercentageList());
					session.setAttribute("familyMemberInNominee", nomineeBean.getFamilyMemberInNominee());
					viewName = "NomineeList";
					mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
					message = nomineeBean.getMessage();
				}

			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					viewName = "Nominee";
					message = CPSConstants.CHANGESFIDFIRST;
					mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.VIEWNOMINEE, nomineeBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("NomineeController --> onSubmit --> param=viewNominee");
					nomineeBean.setChangedSfid(session.getAttribute(CPSConstants.SFID).toString());
					nomineeBean = nomineeBusiness.getNomineeHomeDetails(nomineeBean.getChangedSfid(), session);
					viewName = CPSConstants.VIEWMASTER;
					mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
					if (CPSUtils.checkList(nomineeBean.getNomineeList())) {
						int size = nomineeBean.getNomineeList().size();
						if (size > 0) {
							session.setAttribute(CPSConstants.MASTERJSON, nomineeBean.getNomineeList().get(size - 1));
							nomineeBean.getNomineeList().remove(size - 1);
						}
					}
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.EDITNOMINEE, nomineeBean.getParam())) {
				log.debug("NomineeController --> onSubmit --> param=EditNominee");
				nomineeBean.setChangedSfid(session.getAttribute(CPSConstants.SFID).toString());
				editNomineeId = nomineeBean.getId();
				nomineeBean = nomineeBusiness.getNomineeHomeDetails(nomineeBean.getChangedSfid(), session);
				session.setAttribute("displayNomineeList", nomineeBean.getDisplayNomineeList());
				session.setAttribute("nomineeListJSON", (JSONArray) JSONSerializer.toJSON(nomineeBean.getDisplayNomineeList()));
				session.setAttribute("percentageList", nomineeBean.getPercentageList());
				session.setAttribute("familyMemberInNominee", nomineeBean.getFamilyMemberInNominee());

				viewName = CPSConstants.VIEWNOMINEEDETAILS;
				mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
				message = CPSConstants.EDITNOMINEE;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, nomineeBean.getParam())) {
				log.debug("NomineeController --> onSubmit --> param=submitRequest");

				NomineeRequestBean nrb = new NomineeRequestBean();
				BeanUtils.copyProperties(nomineeBean, nrb);

				nrb.setSfID((String) session.getAttribute(CPSConstants.SFID));
				nrb.setIpAddress(request.getRemoteAddr());

				message = nomineeRequestProcess.initWorkflow(nrb);
				viewName = CPSConstants.REQUESTRESULTPAGE;
				mav = new ModelAndView(viewName, CPSConstants.EMPNOMINEE, nomineeBean);
			}
			mav.addObject(CPSConstants.MESSAGE, message);
			if (!CPSUtils.isNullOrEmpty(editNomineeId)) {
				mav.addObject("editNomineeId", editNomineeId);
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}