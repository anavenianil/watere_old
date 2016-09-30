package com.callippus.web.controller.higherQualification;

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

import com.callippus.web.beans.higherQualification.HQBean;
import com.callippus.web.business.hqBusiness.HQBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/higherQualification.htm")
@SessionAttributes
public class HQController {
	private static Log log = LogFactory.getLog(HQController.class);
	@Autowired
	HQBusiness hqBusiness;
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.HIGHERQUALIFICATION) HQBean hqBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewPage = null;
		try {
			//ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(hqBean.getSfID())) {
				hqBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			}
			if (CPSUtils.isNullOrEmpty(hqBean.getParam())) {
				hqBean.setParam(CPSConstants.HOME);
			}else if (CPSUtils.compareStrings(CPSConstants.PAGING, hqBean.getParam())) {
				viewPage = session.getAttribute(CPSConstants.RETURN).toString();
			}else if (CPSUtils.compareStrings("home", hqBean.getParam())) {
				hqBean=hqBusiness.getHQDetails(hqBean);
				hqBean=hqBusiness.getSanctionOfIncentiveDetails(hqBean);
				session.setAttribute(CPSConstants.HQREQUEST,hqBean);
				viewPage = "HigherQualificationDetails";
			}
			mav = new ModelAndView(viewPage,CPSConstants.HIGHERQUALIFICATION, hqBean);
			if (!CPSUtils.isNullOrEmpty(hqBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, hqBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(hqBean.getResult())) {
				mav.addObject(CPSConstants.RESULT, hqBean.getResult());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}