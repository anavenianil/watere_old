package com.callippus.web.controller.doPart;

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

import com.callippus.web.beans.doPart.DoPartBean;
import com.callippus.web.business.doPart.DoPartBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@RequestMapping("/doPart.htm")
@Controller
@SessionAttributes
public class DoPartController {
	private static Log log = LogFactory.getLog(DoPartController.class);
	@Autowired
	DoPartBusiness doPartBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.DOPART) DoPartBean doPartBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("DoPartController-------> onSubmit");
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(doPartBean.getSfid())) {
				doPartBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			}
			if (CPSUtils.isNullOrEmpty(doPartBean.getParam())) {
				doPartBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, doPartBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {
				doPartBean.setYear(CPSUtils.currentYear());
				doPartBean = doPartBusiness.getDoPartDetails(doPartBean);
				session.setAttribute(CPSConstants.DOPARTLIST, doPartBean.getDoPartList());
				session.setAttribute(CPSConstants.YEARLIST, doPartBean.getYearsList());
				session.setAttribute(CPSConstants.RETURN, "DoPartList");
				viewName = CPSConstants.D0PARTDETAILS;
			} else if (CPSUtils.compareStrings("manageDOPart", doPartBean.getParam())) {
				doPartBean.setMessage(doPartBusiness.manageDOPartDetails(doPartBean));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, doPartBean.getMessage()) || CPSUtils.compareStrings(CPSConstants.UPDATE, doPartBean.getMessage())) {
					doPartBean = doPartBusiness.getDoPartDetails(doPartBean);
					session.setAttribute(CPSConstants.DOPARTLIST, doPartBean.getDoPartList());
				}
				viewName = "DoPartList";
			} else if (CPSUtils.compareStrings("deleteDOPart", doPartBean.getParam())) {
				doPartBean.setMessage(doPartBusiness.deleteDOPartDetails(doPartBean.getId()));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, doPartBean.getMessage())) {
					doPartBean = doPartBusiness.getDoPartDetails(doPartBean);
					session.setAttribute(CPSConstants.DOPARTLIST, doPartBean.getDoPartList());
				}
				viewName = "DoPartList";
			} else if (CPSUtils.compareStrings("yearWiseDOPart", doPartBean.getParam())) {
				doPartBean = doPartBusiness.getDoPartDetails(doPartBean);
				session.setAttribute(CPSConstants.DOPARTLIST, doPartBean.getDoPartList());
				viewName = "DoPartList";
			} else if (CPSUtils.compareStrings("gazettedWiseDOPart", doPartBean.getParam())) {
				doPartBean = doPartBusiness.getDoPartDetails(doPartBean);
				session.setAttribute(CPSConstants.DOPARTLIST, doPartBean.getDoPartList());
				viewName = "DoPartList";
			}
			mav = new ModelAndView(viewName, CPSConstants.DOPART, doPartBean);

			if (!CPSUtils.isNullOrEmpty(doPartBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, doPartBean.getMessage());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;

	}
}
