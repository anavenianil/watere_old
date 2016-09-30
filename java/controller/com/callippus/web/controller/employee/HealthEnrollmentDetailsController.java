package com.callippus.web.controller.employee;

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

import com.callippus.HealthInsuranceBusiness.HealthEnrollmentDetailsBusiness;
import com.callippus.healthInsurance.HealthEnrollmentBean;
import com.callippus.healthInsurance.HealthEnrollmentDetailsDTO;
import com.callippus.web.controller.common.CPSConstants;

@Controller
@RequestMapping("/enrollmentdetails.htm")
@SessionAttributes
public class HealthEnrollmentDetailsController {
	private static Log log = LogFactory
			.getLog(HealthEnrollmentDetailsController .class);

	@Autowired
	private HealthEnrollmentDetailsBusiness healthBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(
			@ModelAttribute HealthEnrollmentDetailsDTO healthEnrollmentDetailsDTO,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		String message = null;
		try {
		healthEnrollmentDetailsDTO =  healthBusiness
				.getHealthEnrollmentDetails(healthEnrollmentDetailsDTO);
	
	
	
		
		viewName = "HealthEnrollment";


	mav = new ModelAndView(viewName , CPSConstants.HEALTHENROLLMENTDETAILS,healthEnrollmentDetailsDTO);

	
}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mav;
}
	
}