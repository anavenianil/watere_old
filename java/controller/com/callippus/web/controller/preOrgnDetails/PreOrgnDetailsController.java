package com.callippus.web.controller.preOrgnDetails;

import java.util.List;

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

import com.callippus.web.beans.preOrgnDetails.PreOrgnDetailsBean;
import com.callippus.web.business.preOrgnDetails.PreOrgnDetailsBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/preOrgnDetails.htm")
@SessionAttributes
public class PreOrgnDetailsController {
	private static Log log = LogFactory.getLog(PreOrgnDetailsController.class);
	@Autowired
	private PreOrgnDetailsBusiness preOrgnDetailsBusiness;

	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.EMPPREORGN) PreOrgnDetailsBean preOrgnBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		List PreorgnList = null;
		String message = "";
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(preOrgnBean.getParam())) {
				preOrgnBean.setParam(CPSConstants.GETPREORGNDTLS);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, preOrgnBean.getParam())) {
				mav = new ModelAndView(CPSConstants.PREORGDETAILSLIST, CPSConstants.EMPPREORGN, preOrgnBean);
			}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				preOrgnBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				if (CPSUtils.compareStrings(CPSConstants.CREATE, preOrgnBean.getParam())) {
					log.debug("PreOrgnDetailsController --> onSubmit --> param=create");
					message = preOrgnDetailsBusiness.createPreOrgnDetails(preOrgnBean);
					mav = new ModelAndView(CPSConstants.PREORGDETAILSLIST, CPSConstants.EMPPREORGN, preOrgnBean);
					mav.addObject(CPSConstants.MESSAGE, message);
				} else if (CPSUtils.compareStrings(CPSConstants.GETPREORGN, preOrgnBean.getParam())) {
					if(!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))){
					log.debug("PreOrgnDetailsController --> onSubmit --> param=getPreOrgn");
					// get employee experience details
					mav = new ModelAndView(CPSConstants.PREORGDETAILS, CPSConstants.EMPPREORGN, preOrgnBean);
					if (!CPSUtils.isNull(session.getAttribute(CPSConstants.GETPREORGNDTLS)) && CPSUtils.compareStrings(session.getAttribute(CPSConstants.GETPREORGNDTLS).toString(), CPSConstants.YES)) {
						mav.addObject(CPSConstants.MESSAGE, CPSConstants.GETPREORGNDTLS);
					}
					session.setAttribute(CPSConstants.RETURN, CPSConstants.PREORGDETAILS);
					// set the list into session
					}else{
						session.setAttribute("path", request.getContextPath()+request.getServletPath()+"?param="+request.getParameter("param"));
						mav=new ModelAndView(new RedirectView(request.getContextPath()+"/views/example.jsp"));
					}
				} else if (CPSUtils.compareStrings(CPSConstants.EDITPREORGN, preOrgnBean.getParam())) {
					log.debug("PreOrgnDetailsController --> onSubmit --> param=getPreOrgnMember");
					// get the employee pre organisation details from the session & set it in form
					preOrgnBean = preOrgnDetailsBusiness.editPreOrgnDetails(preOrgnBean);
					preOrgnBean.setParam(CPSConstants.EDITPREORGN);
					mav = new ModelAndView(CPSConstants.PREORGDETAILS, CPSConstants.EMPPREORGN, preOrgnBean);
					if (!CPSUtils.isNull(session.getAttribute(CPSConstants.GETPREORGNDTLS)) && CPSUtils.compareStrings(session.getAttribute(CPSConstants.GETPREORGNDTLS).toString(), CPSConstants.YES)) {
						mav.addObject(CPSConstants.MESSAGE, CPSConstants.GETPREORGNDTLS);
					}
				} else if (CPSUtils.compareStrings(CPSConstants.UPDATE, preOrgnBean.getParam())) {
					log.debug("PreOrgnDetailsController --> onSubmit --> param=update");
					message = preOrgnDetailsBusiness.updatePreOrgnDetails(preOrgnBean);
					mav = new ModelAndView(CPSConstants.PREORGDETAILSLIST, CPSConstants.EMPPREORGN, preOrgnBean);
					mav.addObject(CPSConstants.MESSAGE, message);
				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, preOrgnBean.getParam())) {
					log.debug("PreOrgnDetailsController --> onSubmit --> param=delete");
					message = preOrgnDetailsBusiness.deletePreOrgnDetails(preOrgnBean);
					mav = new ModelAndView(CPSConstants.PREORGDETAILSLIST, CPSConstants.EMPPREORGN, preOrgnBean);
					mav.addObject(CPSConstants.MESSAGE, message);
				}

			} else {
				if(!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))){
				mav = new ModelAndView(CPSConstants.PREORGDETAILS, CPSConstants.EMPPREORGN, preOrgnBean);
				mav.addObject(CPSConstants.MESSAGE, CPSConstants.CHANGESFIDFIRST);
			}else{
				session.setAttribute("path", request.getContextPath()+request.getServletPath()+"?param="+request.getParameter("param"));
				mav=new ModelAndView(new RedirectView(request.getContextPath()+"/views/example.jsp"));
			}
			}
			if (CPSUtils.compareStrings(CPSConstants.GETPREORGNDTLS, preOrgnBean.getParam())) {
				if(!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))){
				log.debug("PreOrgnDetailsController --> onSubmit --> param=getPreOrgnDetails");
				preOrgnBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				session.setAttribute(CPSConstants.VIEWFAMILYDETAILS, CPSConstants.YES);
				mav = new ModelAndView(CPSConstants.VIEWMASTER, CPSConstants.EMPPREORGN, preOrgnBean);
				session.setAttribute(CPSConstants.GETPREORGNDTLS, CPSConstants.YES);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
			}else{
				session.setAttribute("path", request.getContextPath()+request.getServletPath()+"?param="+request.getParameter("param"));
				mav=new ModelAndView(new RedirectView(request.getContextPath()+"/views/example.jsp"));
			}
			}

			PreorgnList = preOrgnDetailsBusiness.getPreOrgnDetails(preOrgnBean);
			int size = PreorgnList.size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, PreorgnList.get(size - 1));
				PreorgnList.remove(size - 1);
			}
			session.setAttribute(CPSConstants.PREORGNLIST, PreorgnList);
			session.setAttribute("preOrgListJSON", (JSONArray) JSONSerializer.toJSON(PreorgnList));

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}