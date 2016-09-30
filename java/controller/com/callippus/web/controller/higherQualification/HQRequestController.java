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

import com.callippus.web.beans.higherQualification.HQRequestBean;
import com.callippus.web.business.hqBusiness.HQRequestBusiness;
import com.callippus.web.business.requestprocess.HQRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.controller.quarter.QuarterRequestController;
@Controller
@RequestMapping("/hqReq.htm")
@SessionAttributes
public class HQRequestController {
	private static Log log = LogFactory.getLog(QuarterRequestController.class);
	@Autowired
	HQRequestBusiness hqRequestBusiness;
	@Autowired
	private HQRequestProcess hqRequestProcess;
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.HQREQUEST)HQRequestBean hqRequestBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			//ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(hqRequestBean.getSfID())) {
				hqRequestBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			}
			if (CPSUtils.isNullOrEmpty(hqRequestBean.getParam())) {
				hqRequestBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(hqRequestBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			if (CPSUtils.compareStrings(hqRequestBean.getParam(), CPSConstants.HOME)) {
				hqRequestBean=hqRequestBusiness.getHQRequestHomeDetails(hqRequestBean.getSfID());
				session.setAttribute(CPSConstants.HQREQUEST,hqRequestBean);
				session.setAttribute(CPSConstants.QUALIFICATIONSLIST, hqRequestBean.getQualificationList());
				viewName = CPSConstants.HQREQUEST;
			}
			if (CPSUtils.compareStrings("manageHQ", hqRequestBean.getParam())) {
				log.debug("HQRequestController --> onSubmit --> param=manage");
				hqRequestBean.setIpAddress(request.getRemoteAddr());
				hqRequestBean.setResult(hqRequestProcess.initWorkflow(hqRequestBean));
				viewName = CPSConstants.RESULT;
			}if (CPSUtils.compareStrings("manageSOI", hqRequestBean.getParam())) {
				log.debug("HQRequestController --> onSubmit --> param=manageSOI");
				hqRequestBean.setIpAddress(request.getRemoteAddr());
				hqRequestBean.setResult(hqRequestProcess.initWorkflow(hqRequestBean));
				viewName = CPSConstants.RESULT;
			}if (CPSUtils.compareStrings("sanctionOfIncentive", hqRequestBean.getParam())) {
				log.debug("HQRequestController --> onSubmit --> param=sanctionOfIncentive");
				viewName = CPSConstants.HQINCENTIVESANCTION;
			}
			mav = new ModelAndView(viewName, CPSConstants.HQREQUEST, hqRequestBean);
			mav.addObject(CPSConstants.RESULT, hqRequestBean.getResult());
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}  
}
