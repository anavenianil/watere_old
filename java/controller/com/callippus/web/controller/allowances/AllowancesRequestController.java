package com.callippus.web.controller.allowances;

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

import com.callippus.web.beans.allowances.AllowancesRequestBean;
import com.callippus.web.business.allowances.AllowancesRequestBusiness;
import com.callippus.web.business.requestprocess.AllowancesRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/allowancesRequest.htm")
@SessionAttributes
public class AllowancesRequestController {
	private static Log log = LogFactory
			.getLog(AllowancesRequestController.class);

	@Autowired
	private AllowancesRequestBusiness allowancesRequestBusiness;
	@Autowired
	private AllowancesRequestProcess allowancesRequestProcess;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView execute(
			@ModelAttribute(CPSConstants.ALLOWANCESREQUEST) AllowancesRequestBean allowancesBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			//ErpAction.userChecks(request);
			session = request.getSession(true);

			allowancesBean.setSfID(session.getAttribute(CPSConstants.SFID)
					.toString());
			if (CPSUtils.isNullOrEmpty(allowancesBean.getParam())) {
				log.debug("AllowancesRequestController --> execute --> param=\"\"");
				allowancesBean.setParam(CPSConstants.ALLOWANCESHOME);
			} else if (CPSUtils.compareStrings(allowancesBean.getParam(), CPSConstants.ALLOWANCESHOME)) {
				log.debug("AllowancesRequestController --> execute --> param=AllowancesHome");
				allowancesBean = allowancesRequestBusiness.getFPARequestHomeDetails(allowancesBean);
				viewName = CPSConstants.ALLOWANCESHOME;
			}
			if (CPSUtils.compareStrings(allowancesBean.getParam(), CPSConstants.SUBMITALLOWANCEREQUEST)) {
				log.debug("AllowancesRequestController --> execute --> param=submitAllowanceRequest");
				allowancesRequestProcess.saveAllowanceDetails(allowancesBean);
				viewName = CPSConstants.REQUESTRESULTPAGE;
			}

			mav = new ModelAndView(viewName, CPSConstants.ALLOWANCESREQUEST, allowancesBean);
			if (CPSUtils.compareStrings(allowancesBean.getParam(),CPSConstants.SUBMITALLOWANCEREQUEST)) {
				mav.addObject(CPSConstants.MESSAGE, CPSConstants.SUCCESS);
			}
			// mav.addObject(CPSConstants.RESULT, allowancesBean.getResult());
			// mav.addObject(CPSConstants.REMARKS, allowancesBean.getRemarks());
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e)
					.getResultStatus().getErrorCode());
		}
		return mav;
	}
}