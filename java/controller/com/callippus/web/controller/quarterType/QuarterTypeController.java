package com.callippus.web.controller.quarterType;

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

import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.business.quarterType.QuarterTypeBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/quarterType.htm")
@SessionAttributes
public class QuarterTypeController {
	private static Log log = LogFactory.getLog(QuarterTypeController.class);
	@Autowired
	private QuarterTypeBusiness quarterTypeBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.QUARTERTYPE) QuarterTypeBean quarterTypeBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
			//ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(quarterTypeBean.getParam())) {
				quarterTypeBean.setParam(CPSConstants.HOME);
			}
			if (CPSUtils.compareStrings(CPSConstants.PAGING, quarterTypeBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			if (CPSUtils.isNullOrEmpty(quarterTypeBean.getSfid())) {
				quarterTypeBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			}
			quarterTypeBean.setPayRunMonth(quarterTypeBusiness.getCurrentRunMonth());
			if (CPSUtils.compareStrings(CPSConstants.HOME, quarterTypeBean.getParam())) {
				log.debug("QuarterTypeController --> onSubmit --> param=Home");
				quarterTypeBean = quarterTypeBusiness.getQuarterList(quarterTypeBean);
				session.setAttribute("quarterTypeList", quarterTypeBean.getQuarterTypeList());
				session.setAttribute("quarterList", quarterTypeBean.getQuarterList());
				viewName = CPSConstants.QUARTERTYPEMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.QUARTERTYPEMASTERLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, quarterTypeBean.getParam())) {
				log.debug("QuarterTypeController  --> onSubmit --> param=manage");
				quarterTypeBean.setMessage(quarterTypeBusiness.submitQuarterTypeDetails(quarterTypeBean));
				quarterTypeBean = quarterTypeBusiness.getQuarterList(quarterTypeBean);
				session.setAttribute("quarterList", quarterTypeBean.getQuarterList());
				viewName = CPSConstants.QUARTERTYPEMASTERLIST;
			}
			else if (CPSUtils.compareStrings(CPSConstants.LICENCEFEEHOME, quarterTypeBean.getParam())) {
				log.debug("QuarterTypeController --> onSubmit --> param=licenceFeeHome");
				quarterTypeBean = quarterTypeBusiness.getLicenceFeeList(quarterTypeBean);
				session.setAttribute("LicenceFeeList", quarterTypeBean.getLicenceFeeList());
				session.setAttribute("quarterTypeList", quarterTypeBean.getQuarterTypeMasterList());
				session.setAttribute("quarterSubTypeList", quarterTypeBean.getQuarterSubTypeList());
				viewName = CPSConstants.LICENCEFEECHARGESMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LICENCEFEECHARGESMASTERLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.LICENCEFEEMANAGE, quarterTypeBean.getParam())) {
				log.debug("QuarterTypeController  --> onSubmit --> param=licenceFeeManage");
				quarterTypeBean.setMessage(quarterTypeBusiness.submitLicenceFeeDetails(quarterTypeBean));
				quarterTypeBean = quarterTypeBusiness.getLicenceFeeList(quarterTypeBean);
				session.setAttribute("LicenceFeeList", quarterTypeBean.getLicenceFeeList());
				viewName = CPSConstants.LICENCEFEECHARGESMASTERLIST;
			}
			mav = new ModelAndView(viewName, CPSConstants.QUARTERTYPE, quarterTypeBean);
			if (!CPSUtils.isNullOrEmpty(quarterTypeBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, quarterTypeBean.getMessage());
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
