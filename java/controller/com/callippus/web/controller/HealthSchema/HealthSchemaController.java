/*package com.callippus.web.controller.HealthSchema;

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

import com.callippus.HealthInsuranceBusiness.HealthEnrollmentBusiness;
import com.callippus.HealthInsuranceBusiness.HealthInsuranceBusiness;
import com.callippus.healthInsurance.HealthEnrollmentBean;
import com.callippus.healthInsurance.HealthInsuranceDTO;
import com.callippus.web.business.requestprocess.TadaWaterRequestProcess;
import com.callippus.web.cghs.business.request.CghsRequestBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
@SuppressWarnings("serial")
@Controller
@RequestMapping("/healthenroll.htm")
@SessionAttributes
	public class HealthSchemaController {
		

		private static Log log = LogFactory
				.getLog(HealthSchemaController .class);

		@Autowired
		private HealthEnrollmentBusiness healthBusiness;
		
		@Autowired
		private TadaWaterRequestProcess tadaWaterRequestProcess;

		@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
		public ModelAndView execute(
				@ModelAttribute HealthEnrollmentBean bean,
				BindingResult result, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ModelAndView mav = null;
			HttpSession session = null;
			String viewName = null;
			
			try {
				// ErpAction.userChecks(request);
				session = request.getSession(true);
				if(CPSUtils.compareStrings(CPSConstants.ADVMANAGE, bean.getPram())){
					bean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
					bean = healthBusiness.saveCdaAmont(bean);
					if(CPSUtils.compareStrings(bean.getType(),CPSConstants.HEALTHY)){
						//session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getAdvanceList());
						mav = new ModelAndView(CPSConstants.HEALTHINSURANCE,CPSConstants.HEALTH, bean);
						session.setAttribute(CPSConstants.HEALTHINSURANCEDETAILS,bean.getHealthInsuranceList());
					}
					mav.addObject(CPSConstants.MESSAGE, bean.getMessage());
					session.setAttribute(CPSConstants.ADVANCELIST,bean.getHealthInsuranceList());
					
				}
			}  
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			return mav;

		}
	}
		*/