package com.callippus.web.controller.login;

import java.lang.reflect.Array;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.login.LoginBean;
import com.callippus.web.business.login.LoginBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/index.htm")
@SessionAttributes
public class LoginController {
	private static Log log = LogFactory.getLog(LoginController.class);
	@Autowired
	private LoginBusiness loginBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(@ModelAttribute(CPSConstants.LOGIN) LoginBean loginBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			session = request.getSession(true);
			if (CPSUtils.isNull(session.getAttribute(CPSConstants.SFID))) {
				viewName = CPSConstants.LOGINPAGE;
			} else {
				viewName = CPSConstants.REDIRECT;
			}
			mav = new ModelAndView(viewName, CPSConstants.LOGIN, loginBean);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView doPost(@ModelAttribute(CPSConstants.LOGIN) LoginBean loginBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		try {
			log.debug("LoginController onSubmit ---> LOGGING SFID IS:::" + loginBean.getUsername());
			session = request.getSession(true);
			
			String browser = request.getHeader("User-Agent");
			
	         String[] b = 		browser.split("\\s");
	         if(b.length==7){
	        	 if(b[6] !=null && b[6].contains("Firefox")){
		        	 loginBean.setBrowser(b[6]);
		         }
	         }else if(b.length==3){
	        	 if(b[2] !=null && b[2].contains("MSIE")){
		        	 String temp = b[2].concat(b[3].toString());
		        	 loginBean.setBrowser(temp);
		         } 
	         }else if(b.length==9){
	        	 if(b[8] !=null && b[8].contains("Chrome")){
		        	 loginBean.setBrowser(b[8]);
		         }
	         }else{
	        	 loginBean.setBrowser("Browser Version Not Found: "+browser);
	         }  
	        

			// CasAuthenticationToken
			// token=(CasAuthenticationToken)request.getUserPrincipal();
			// String username=token.getUserDetails().getUsername();
			// loginBean.setUsername(username);
			// loginBean.setPassword(token.getUserDetails().getPassword());
			loginBean.setIpAddress(request.getRemoteAddr());
			loginBean = loginBusiness.checkUser(loginBean, session);
			if (!CPSUtils.isNull(loginBean.getLoginList())) {
				mav = new ModelAndView("redirect:workflowmap.htm?param=pisHome&redirect=true");
				if (CPSUtils.checkMap((HashMap<String, String>) session.getAttribute(CPSConstants.MENULINKS))) {
					mav.addObject(CPSConstants.MENULINKS, (HashMap<String, String>) session.getAttribute(CPSConstants.MENULINKS));
				}
				log.debug("LoginController onSubmit ---> LOGGING SUCCESSFUL FOR SFID :::" + loginBean.getUsername());
				session.setAttribute(CPSConstants.SFID, loginBean.getSfid());
				session.setAttribute(CPSConstants.CHANGESFID, loginBean.getSfid());
				session.setAttribute(CPSConstants.CHANGESFIDNAME, loginBean.getName().split(",")[0]);
				session.setAttribute(CPSConstants.ROLESLIST, loginBean.getRoleList());
				session.setAttribute(CPSConstants.ROLEACTIONS, loginBean.getRoleActions());
			} else {
				log.debug("LoginController onSubmit ---> LOGGING FAIL FOR SFID :::" + loginBean.getUsername());
				mav = new ModelAndView(CPSConstants.LOGINPAGE, CPSConstants.LOGIN, loginBean);
				mav.addObject(CPSConstants.MESSAGE, CPSConstants.INVALIDUSER);
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	// protected boolean isFormSubmission(HttpServletRequest request) throws ResultStatus, Exception{
	// boolean value=false;
	// try {
	// if(!CPSUtils.isNull(request.getUserPrincipal()))
	// value=true;
	// }catch(Exception e)
	// {
	// throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
	// }
	// return value;
	// }
}
