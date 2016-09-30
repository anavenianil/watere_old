package com.callippus.web.controller.mmg.cashbuildup;

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

import com.callippus.web.business.mmg.cashbuildup.SecurityDetailsBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.mmg.cashbuildup.dto.SecurityDetailsDTO;



@Controller
@RequestMapping("/securityDetails")
@SessionAttributes
public class SecurityDetailsController {
		private static Log log = LogFactory.getLog(SecurityDetailsController.class);
		@Autowired
		private SecurityDetailsBusiness securityBusiness;
		
		@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
		public ModelAndView execute(@ModelAttribute SecurityDetailsDTO security, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView mav = null;
			log.debug("SecurityDetailsController on submit");
			String message = "";
			HttpSession session=null;
			try {
				session=request.getSession(true);
				if(CPSUtils.compareStrings(request.getParameter(CPSConstants.PARAM), CPSConstants.PAGING)){
					mav = new ModelAndView(CPSConstants.SECURITYDEMANDITEMS, CPSConstants.COMMAND,security);
				}
				if (CPSUtils.compareStrings(CPSConstants.HOME, request.getParameter(CPSConstants.PARAM))) {	
					log.debug("SecurityDetailsController --> onSubmit --> param=home");
					session.removeAttribute(CPSConstants.DEMANDITEMS);
					session.setAttribute(CPSConstants.DEMANDNOSLIST,securityBusiness.getDemandNumbers(security).getDemandList());
			    	mav = new ModelAndView(CPSConstants.SECURITYCHECK, CPSConstants.COMMAND,security);
			    }if (CPSUtils.compareStrings(CPSConstants.GETDEMANDETAILS, request.getParameter(CPSConstants.PARAM))) {	
					log.debug("SecurityDetailsController --> onSubmit --> param=getDemandDetails");
					security.setSfID((String)session.getAttribute(CPSConstants.SFID));
					security=securityBusiness.getDemandDetails(security, request);
					if(!CPSUtils.isNull(security)) { 
						session.setAttribute(CPSConstants.DEMANDITEMS,(JSONArray)JSONSerializer.toJSON(security.getDemandItems()));
					}
			    	mav = new ModelAndView(CPSConstants.SECURITYDEMANDITEMS, CPSConstants.COMMAND,security);
			    }if(CPSUtils.compareStrings(CPSConstants.SAVESECURITYCHECK,request.getParameter(CPSConstants.PARAM))){
			    	log.debug("SecurityDetailsController -->onsubmit -->param=savesSecuritycheck");
			    	message=securityBusiness.saveSecurityCheckItems(security,request);
			        if(message.equals(CPSConstants.SUCCESS)){
			        	if(!CPSUtils.isNull(security)) 
			        		session.setAttribute(CPSConstants.DEMANDITEMS,securityBusiness.getDemandDetails(security, request).getDemandItems());
			        }
			    	mav=new ModelAndView(CPSConstants.SECURITYDEMANDITEMS,CPSConstants.COMMAND,security);
			    	mav.addObject(CPSConstants.MESSAGE,message);
			    }
			    
			}catch(Exception e){
				throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
			}
			return mav;
		}
}

