package com.callippus.web.controller.misc;

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

import com.callippus.web.beans.misc.AdminMisc;
import com.callippus.web.business.misc.AdminMiscBusiness;
import com.callippus.web.business.requestprocess.PropertyRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/adminMisc.htm")
@SessionAttributes
public class AdminMiscController {
	private static Log log = LogFactory.getLog(AdminMiscController.class);
	
	@Autowired
	private AdminMiscBusiness adminMiscBusiness;
	@Autowired
	private PropertyRequestProcess propertyRequestProcess;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.ADMINMISC) AdminMisc adminMisc, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String message="";
		String viewName="";
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
		//	session.removeAttribute(CPSConstants.ADMINMISCTYPE);
			
			if (!CPSUtils.isNullOrEmpty(adminMisc.getType()))
				session.setAttribute(CPSConstants.ADMINMISCTYPE, adminMisc.getType());
			

			//Movable Property Starts
			
			if (CPSUtils.compareStrings("movablePropertyHome", adminMisc.getParam())) {
				log.debug("PassportController --> onSubmit --> param=nocPassportHome");
				adminMisc.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				//adminMisc.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				mav = new ModelAndView("movableProperty", CPSConstants.ADMINMISC, adminMisc);
				session.setAttribute("movablePropertyList",(JSONArray)JSONSerializer.toJSON(adminMiscBusiness.getMovablePropertyDetails(adminMisc)));
			}
			
			else if(CPSUtils.compareStrings("saveMovableRecord",adminMisc.getParam())){
				log.debug("PassportController --> onSubmit --> param=saveMovableRecord");
				adminMisc.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				message=adminMiscBusiness.saveMovableRecord(adminMisc);
				session.setAttribute("movablePropertyList",(JSONArray)JSONSerializer.toJSON(adminMiscBusiness.getMovablePropertyDetails(adminMisc)));
				mav = new ModelAndView("movablePropertyList", CPSConstants.ADMINMISC, adminMisc);
				mav.addObject(CPSConstants.MESSAGE, message);
			}
			
			else if(CPSUtils.compareStrings("deletePropertyRecord",adminMisc.getParam())){
				adminMisc=adminMiscBusiness.deletePropertyRecord(adminMisc);
				mav = new ModelAndView("movablePropertyList", CPSConstants.ADMINMISC, adminMisc);
				mav.addObject(CPSConstants.MESSAGE, adminMisc.getResult());
			}
			
			//sendRequest to workFlow
			
			else if(CPSUtils.compareStrings("sendRequest",adminMisc.getParam())){
				adminMisc.setIpAddress(request.getRemoteAddr());
				adminMisc.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				adminMisc.setResult(propertyRequestProcess.initWorkflow(adminMisc));
				session.setAttribute("movablePropertyList",(JSONArray)JSONSerializer.toJSON(adminMiscBusiness.getMovablePropertyDetails(adminMisc)));
				mav = new ModelAndView("movablePropertyList", CPSConstants.ADMINMISC, adminMisc);
				mav.addObject(CPSConstants.MESSAGE, adminMisc.getResult());
			}
			
			//Movable property ends
			
			
			else if(CPSUtils.compareStrings("adminMiscview",adminMisc.getParam())){
				log.debug("PassportController --> onSubmit --> param=adminMiscview");
				
				mav = new ModelAndView("adminMisc", CPSConstants.ADMINMISC, adminMisc);
			}
			else if(CPSUtils.compareStrings("save",adminMisc.getParam())){
				log.debug("PassportController --> onSubmit --> param=save");
				String gazType=adminMisc.getAdminType();
				adminMisc.setType(session.getAttribute(CPSConstants.ADMINMISCTYPE).toString());
				adminMisc.setSfID(adminMisc.getSfID().toUpperCase());
				message=adminMiscBusiness.saveRecord(adminMisc);
				String s[]=message.split("#");
				if(!s[1].isEmpty()){
					session.setAttribute("idGenerator",s[1] );
				}
				mav = new ModelAndView("miscResult", CPSConstants.ADMINMISC, adminMisc);
				mav.addObject(CPSConstants.CATEGORY, gazType);
				mav.addObject(CPSConstants.MESSAGE, s[0]);
			}
			
			
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	
	
	
	
	
	
	
}
