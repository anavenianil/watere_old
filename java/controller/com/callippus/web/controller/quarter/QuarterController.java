package com.callippus.web.controller.quarter;

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

import com.callippus.web.beans.quarter.QuarterBean;
import com.callippus.web.business.quarter.QuarterBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/quarterDetails.htm")
@SessionAttributes
public class QuarterController {

	private static Log log = LogFactory.getLog(QuarterController.class);
	@Autowired
	private QuarterBusiness quarterDetailsBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.QUARTER) QuarterBean quarterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewPage = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(quarterBean.getParam()))
				quarterBean.setParam(CPSConstants.HOME);
			else if (CPSUtils.compareStrings(CPSConstants.PAGING, quarterBean.getParam()))
						viewPage = session.getAttribute(CPSConstants.RETURN).toString();
			quarterBean.setLogInSfID(session.getAttribute(CPSConstants.SFID).toString());
			/**
			 * Admin Quarter Management starts
			 */
			if (CPSUtils.compareStrings(CPSConstants.ADMINHOME, quarterBean.getParam())) {
				if (CPSUtils.compareStrings(quarterBean.getRedirect(), CPSConstants.TRUE)) {
					log.debug("QuarterController --> onSumit --> param=adminHome");
					quarterBean.setSfid((String) session.getAttribute(CPSConstants.CHANGESFID));
					quarterBean = quarterDetailsBusiness.getQuarterAdminHomeDetails(quarterBean);
					session.setAttribute(CPSConstants.QUARTERSLIST, quarterBean.getQuarterDetails());
					session.setAttribute(CPSConstants.RETURN, CPSConstants.QUARTERLIST);
					viewPage = CPSConstants.QUARTERDETAILS;
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.EMUHOME, quarterBean.getParam())) {
				if (CPSUtils.compareStrings(quarterBean.getRedirect(), CPSConstants.TRUE)) {
					log.debug("QuarterController --> onSumit --> param=adminHome");
					quarterBean.setSfid((String) session.getAttribute(CPSConstants.CHANGESFID));
					quarterBean = quarterDetailsBusiness.getQuarterEmuHomeDetails(quarterBean);
					session.setAttribute(CPSConstants.QUARTERSLIST, quarterBean.getEmuQuarterDetails());
					session.setAttribute(CPSConstants.RETURN, CPSConstants.EMUREPORTHOME);
					viewPage = CPSConstants.EMUREPORTHOME;
				}
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, quarterBean.getParam())) {
				log.debug("QuarterController --> onSumit --> param=manage");
				quarterBean.setSfid((String) session.getAttribute(CPSConstants.CHANGESFID));
				quarterBean = quarterDetailsBusiness.manageQuarterDetails(quarterBean);
				session.setAttribute(CPSConstants.QUARTERSLIST, quarterDetailsBusiness.getQuarterDetails(quarterBean));
				viewPage = CPSConstants.QUARTERLIST;
				quarterBean.setRedirect(CPSConstants.TRUE);
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, quarterBean.getParam())) {
				log.debug("QuarterController --> onSumit --> param=delete");
				quarterBean.setSfid((String) session.getAttribute(CPSConstants.CHANGESFID));
				quarterBean = quarterDetailsBusiness.deleteQuarterDetails(quarterBean);
				session.setAttribute(CPSConstants.QUARTERSLIST, quarterDetailsBusiness.getQuarterDetails(quarterBean));
				viewPage = CPSConstants.QUARTERLIST;
				quarterBean.setRedirect(CPSConstants.TRUE);
			}
			/**
			 * Admin Quarter management ends
			 */
			/**
			 * View Quarter Details starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.VIEW, quarterBean.getParam())) {
				if (CPSUtils.compareStrings(quarterBean.getRedirect(), CPSConstants.TRUE)) {
					log.debug("QuarterController --> onSumit --> param=view");					
					quarterBean = quarterDetailsBusiness.getUserQuarterDetails(quarterBean);
					viewPage = CPSConstants.VIEWQUARTERDETAILS;
				}
			}
			/**
			 * View Quarter Details ends
			 */
			if (CPSUtils.compareStrings(quarterBean.getRedirect(), CPSConstants.TRUE)) {
				mav = new ModelAndView(viewPage, CPSConstants.QUARTER, quarterBean);
				mav.addObject(CPSConstants.RESULT, quarterBean.getResult());
			} else {
				session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + quarterBean.getParam());
				mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
			}	
		}
		catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}