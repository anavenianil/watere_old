/*package com.callippus.web.controller.healthInsuranceSchema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.healthInsurance.HealthInsuranceDTO;

@Controller
//@RequestMapping(value="/employeeabc.htm")
@SessionAttributes
public class HealthInsuranceSchemaController {
	

	private static Log log = LogFactory
			.getLog(HealthInsuranceSchemaController.class);
	
	@RequestMapping(value="/abc.htm",method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(
			@ModelAttribute HealthInsuranceDTO healthInsuranceDTO,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		 
		try {
			System.out.println("Request coming");
			
			// ErpAction.userChecks(request);
			session = request.getSession(true);

			System.out.println("Inside HealthInsuranceDetailsController1");
			
				
				viewName = "HealthInsurance";
		

			mav = new ModelAndView(viewName , CPSConstants.HEALTH,healthInsuranceDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}

	
}
*/