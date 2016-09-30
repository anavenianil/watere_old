package com.callippus.web.controller.reportDetails;

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

import com.callippus.web.beans.reportDetails.ReportDetailsBean;
import com.callippus.web.business.reportDetails.ReportDetailsBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/reportDetails.htm")
@SessionAttributes
public class ReportDetailsController {

	private static Log log = LogFactory.getLog(ReportDetailsController.class);
	@Autowired
	private ReportDetailsBusiness reportDetailsBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute ReportDetailsBean reportDetailsBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("ReDetailsController--->onSubmit");
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(reportDetailsBean.getParam())) {
				reportDetailsBean.setParam(CPSConstants.HOME);
			}

			if (CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {
				session.setAttribute(CPSConstants.DESIGNATIONLIST, reportDetailsBusiness.getDesignations());
				viewName = CPSConstants.CPOOLDETAILS;
			}
			if (CPSUtils.compareStrings(CPSConstants.MANAGE, reportDetailsBean.getParam())) {
				reportDetailsBean = reportDetailsBusiness.saveCpoolDetails(reportDetailsBean);
				viewName = CPSConstants.RESULT;
			}
			mav = new ModelAndView(viewName, CPSConstants.COMMAND, reportDetailsBean);
			mav.addObject(CPSConstants.MESSAGE, reportDetailsBean.getMessage());

			if (!CPSUtils.isNull(session.getAttribute(CPSConstants.DESIGNATIONLIST))) {
				mav.addObject(CPSConstants.DESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.DESIGNATIONLIST)));
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}

		return mav;
	}

}
