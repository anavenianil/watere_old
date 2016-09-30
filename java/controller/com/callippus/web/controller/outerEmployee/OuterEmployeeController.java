package com.callippus.web.controller.outerEmployee;

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

import com.callippus.web.beans.outerEmployee.OuterEmployeeBean;
import com.callippus.web.business.outerEmployee.OuterEmployeeBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/outerEmp.htm")
@SessionAttributes
public class OuterEmployeeController {
	private static Log log = LogFactory.getLog(OuterEmployeeController.class);
	@Autowired
	private OuterEmployeeBusiness otherEmployeeBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute OuterEmployeeBean employee, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		//String message1 = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(employee.getParam())) {
				employee.setParam(CPSConstants.HOME);
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, employee.getParam())) {
				log.debug("::OtherEmployeeController --> onSubmit Start--> param = home");
				employee = otherEmployeeBusiness.getOuterEmployeeGeneralDetails(session, employee);
				
				
				// session.setAttribute(CPSConstants.GENERATEDID, employee.getGeneratedId());
				
				
				mav = new ModelAndView(CPSConstants.OUTEREMPLOYEEDETAILS, CPSConstants.OUTSIDEEMP, employee);
				mav.addObject(CPSConstants.SFID, employee.getSfid());
				log.debug("::OtherEmployeeController --> onSubmit End--> RESPONSE JSP ------> OtherEmployeeDetails");
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMIT, employee.getParam())) {
				log.debug("::OtherEmployeeController --> onSubmit Start--> param = submit");
				String message = otherEmployeeBusiness.submitEmployeeDetails(session, employee);
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.OUTSIDEEMP, employee);
				mav.addObject(CPSConstants.MESSAGE, message);
				log.debug("::OtherEmployeeController --> onSubmit --> End--> RESPONSE JSP ------> OtherEmployeeDetails ");
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

}
