package com.callippus.web.controller.logout;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
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

import com.callippus.web.beans.login.LoginBean;
import com.callippus.web.business.login.LoginBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/logout.htm")
@SessionAttributes
public class LogoutController {
	private static Log log = LogFactory.getLog(LogoutController.class);
	private LoginBusiness loginBusiness;

	public LoginBusiness getLoginBusiness() {
		return loginBusiness;
	}

	@Autowired
	public void setLoginBusiness(LoginBusiness loginBusiness) {
		this.loginBusiness = loginBusiness;
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LOGIN) LoginBean loginBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		try {

				log.debug("LogoutController------>execute()");
				session = request.getSession(true);
				loginBean.setIpAddress(request.getRemoteAddr());
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
				if (request.isRequestedSessionIdValid()) {
					if (!CPSUtils.isNull(session.getAttribute(CPSConstants.SFID))) {
						loginBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
						getLoginBusiness().saveLogOutDetails(loginBean);
						log.debug(" \n LogoutController onSubmit ---> LOGOUT SUCCESSFUL FOR SFID :::" + session.getAttribute(CPSConstants.SFID));
					}			
					session.invalidate();
				} else {
					log.debug("LogoutController------> REQUESTED SESSION ID INVALID FOR "+session.getAttribute(CPSConstants.SFID));
				}
			loginBean.setIpAddress(request.getRemoteAddr());
			if (request.isRequestedSessionIdValid()) {
				if (!CPSUtils.isNull(session.getAttribute(CPSConstants.SFID))) {
					loginBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					loginBusiness.saveLogOutDetails(loginBean);
				}
				log.debug(" \n LogoutController onSubmit ---> LOGOUT SUCCESSFUL FOR SFID :::" + session.getAttribute(CPSConstants.SFID));
				session.invalidate();
				// mav = new ModelAndView("redirect:secure/index");

			}
				//mav = new ModelAndView("redirect:secure/index");
			mav = new ModelAndView(CPSConstants.LOGINPAGE, CPSConstants.LOGIN, loginBean);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}