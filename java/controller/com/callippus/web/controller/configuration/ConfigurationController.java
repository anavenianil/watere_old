package com.callippus.web.controller.configuration;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.configuration.ConfigurationBean;
import com.callippus.web.beans.schedulereports.ScheduleReportsBean;
import com.callippus.web.business.configuration.ConfigurationBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@SessionAttributes
public class ConfigurationController {
	private static Log log = LogFactory.getLog(ConfigurationController.class);
	@Autowired
	private ConfigurationBusiness configurationBusiness;

	@RequestMapping(value = "/configuration.htm",method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.CONFIGURATION) ConfigurationBean configurationBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		String message = "";
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(configurationBean.getParam())) {
				configurationBean.setParam(CPSConstants.HOME);
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, configurationBean.getParam())) {
				log.debug("::ConfigurationController --> onSubmit --> param=home");
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					configurationBean = configurationBusiness.getConfigurationDetails();
					session.setAttribute(CPSConstants.ORGINSTANCELIST, configurationBean.getInstanceList());
					if(!CPSUtils.isNull(request.getParameter("type")) && (CPSUtils.compareStrings(request.getParameter("type"), "paybill")))
					viewName=CPSConstants.PAYBILLCONFIGURATION;
						else
					viewName = CPSConstants.CONFIGURATIONDETAILS;
					mav = new ModelAndView(viewName, CPSConstants.COMMAND, configurationBean);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param")+"&type="+request.getParameter("type"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}

			} else if (CPSUtils.compareStrings(CPSConstants.SAVE, configurationBean.getParam())) {
				log.debug("::ConfigurationController --> onSubmit --> param=save");
				message = configurationBusiness.submitConfigurationDetails(configurationBean.getConfigurationDetails());
				viewName = "Result";
				mav = new ModelAndView(viewName, CPSConstants.COMMAND, configurationBean);
			}
			mav.addObject(CPSConstants.MESSAGE, message);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	@RequestMapping(value = "/signingAuthority.htm", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=*/*")
	public ModelAndView searchData(@ModelAttribute(CPSConstants.CONFIGURATION) ConfigurationBean configurationBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mav = null;
		String viewName = null;
		String message = "";
		HttpSession session = null;
		try
		{
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if(CPSUtils.compareStrings(CPSConstants.CFAACCAUTHORITYDETAILS, configurationBean.getParam())){
				session.setAttribute("signingAuthorityList", configurationBusiness.getSigningAuthorityDetails());
				viewName = CPSConstants.SIGNINGAUTHORITYDETAILS;
				mav = new ModelAndView(viewName, CPSConstants.COMMAND, configurationBean);
			}else if(CPSUtils.compareStrings("submitSigningAuthorityDetails", configurationBean.getParam())){
				configurationBean.setMessage(configurationBusiness.submitSigningAuthorityDetails(configurationBean));
			    session.setAttribute("signingAuthorityList", configurationBusiness.getSigningAuthorityDetails());
			    viewName=CPSConstants.SIGNINGAUTHORITYLIST;
			    mav = new ModelAndView(viewName, CPSConstants.COMMAND, configurationBean);
			}else if(CPSUtils.compareString(CPSConstants.DELETESIGNINGAUTHORITY, configurationBean.getParam())){
				configurationBean.setMessage(configurationBusiness.deleteSigningAuthorityDetails(Integer.parseInt(configurationBean.getPk())));
				session.setAttribute("signingAuthorityList", configurationBusiness.getSigningAuthorityDetails());
			    viewName=CPSConstants.SIGNINGAUTHORITYLIST;
			    mav = new ModelAndView(viewName, CPSConstants.COMMAND, configurationBean);
			}
			
			if (!CPSUtils.isNullOrEmpty(configurationBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, configurationBean.getMessage());
			}	
		}catch (Exception e) 
		{
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} 
		return mav;
	}
	
	@RequestMapping(value = "/mailConfiguration.htm",method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView mailConfiguration(@ModelAttribute(CPSConstants.CONFIGURATION) ConfigurationBean configurationBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		String message = "";
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(configurationBean.getParam())) {
				configurationBean.setParam(CPSConstants.HOME);
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, configurationBean.getParam())) {
				log.debug("::mailConfiguration --> onSubmit --> param=home");
				viewName="mailConfiguration";
				mav = new ModelAndView(viewName, CPSConstants.COMMAND, configurationBean);
				
			} else if (CPSUtils.compareStrings(CPSConstants.SAVE, configurationBean.getParam())) {
				log.debug("::mailConfiguration --> onSubmit --> param=save");
				message = configurationBusiness.setConfigurationValue("MAIL_SENDING",configurationBean.getSendMail());
				viewName = "mailConfiguration";
				mav = new ModelAndView(viewName, CPSConstants.COMMAND, configurationBean);
			}
			mav.addObject(CPSConstants.MESSAGE, message);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	
}