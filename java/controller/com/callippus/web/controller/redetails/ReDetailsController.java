package com.callippus.web.controller.redetails;

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

import com.callippus.web.beans.redetails.ReDetailsBean;
import com.callippus.web.business.redetails.ReDetailsBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
@Controller
@RequestMapping("/reDetails.htm")
@SessionAttributes
public class ReDetailsController {
	private static Log log = LogFactory.getLog(ReDetailsController.class);
	@Autowired
	private ReDetailsBusiness reDetailsBusiness;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute ReDetailsBean reDetailsBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("ReDetailsController--->onSubmit");
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			//ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(reDetailsBean.getParam())) {
				reDetailsBean.setParam(CPSConstants.HOME);
			}else if (CPSUtils.compareStrings(reDetailsBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {
				session.setAttribute(CPSConstants.DESIGNATIONLIST, reDetailsBusiness.getDesignations(reDetailsBean));
				session.setAttribute(CPSConstants.CATEGORYLIST, reDetailsBusiness.getCategoryList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.RELIST);
				viewName = CPSConstants.REDETAILS;
			}
			if (CPSUtils.compareStrings(CPSConstants.CATEGORY, request.getParameter(CPSConstants.PARAM))) {
				session.setAttribute(CPSConstants.DESIGNATIONLIST, reDetailsBusiness.getDesignations(reDetailsBean));
				viewName = CPSConstants.RELIST;
			}
			if (CPSUtils.compareStrings("desigReValues", request.getParameter(CPSConstants.PARAM))) {
				session.setAttribute(CPSConstants.DESIGNATIONLIST, reDetailsBusiness.getDesignations(reDetailsBean));
				viewName = CPSConstants.RELIST;
			}
			if (CPSUtils.compareStrings(CPSConstants.MANAGE, reDetailsBean.getParam())) {
				reDetailsBean = reDetailsBusiness.saveReDetails(reDetailsBean);
				viewName = CPSConstants.RESULT;
			}
			mav = new ModelAndView(viewName, CPSConstants.COMMAND, reDetailsBean);
			mav.addObject(CPSConstants.MESSAGE, reDetailsBean.getMessage());

			if (!CPSUtils.isNull(session.getAttribute(CPSConstants.DESIGNATIONLIST))) {
				mav.addObject(CPSConstants.DESIGNATIONLIST, (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.DESIGNATIONLIST)));
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}

		return mav;
	}


}
