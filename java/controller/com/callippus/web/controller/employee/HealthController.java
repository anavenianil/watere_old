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

import com.callippus.HealthInsuranceBusiness.HealthEnrollmentBusiness;
import com.callippus.HealthInsuranceBusiness.HealthInsuranceBusiness;
import com.callippus.healthInsurance.HealthEnrollmentBean;
//import com.callippus.HealthInsuranceController.HealthInsuranceController2;
import com.callippus.healthInsurance.HealthInsuranceDTO;
import com.callippus.web.controller.common.CPSConstants;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.login.LoginBean;
import com.callippus.web.beans.requests.PISRequestBean;
import com.callippus.web.business.employee.CreateEmployeeBusiness;
import com.callippus.web.business.requestprocess.PISRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@SessionAttributes
public class HealthController {
	private static Log log = LogFactory.getLog(HealthController.class);

	@Autowired
	private HealthInsuranceBusiness healthInsuranceBusiness;
	
	@Autowired
	private HealthEnrollmentBusiness healthBusiness;

	@RequestMapping(value = "/healthInsuranceEnrollment.htm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(
			@ModelAttribute HealthEnrollmentBean healthInsuranceBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("HealthController --> execute");
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			// ErpAction.userChecks(request);
			session = request.getSession(true);
			healthInsuranceBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(healthInsuranceBean.getParam())) {
				log.debug("HealthController --> execute --> param=\"\"");
				healthInsuranceBean.setParam(CPSConstants.HEALTHENROLLMENTHOME);
			} else if (CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.HEALTHENROLLMENTHOME)) {
				log.debug("HealthController --> execute --> param=HealthEnrollmentHome");
				healthInsuranceBean = healthInsuranceBusiness.getHealthInsuranceDetails(healthInsuranceBean);
				viewName = "HealthInsurance";
			} else if (CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.HEALTHENROLLMENTDETAILSHOME)) { 
				log.debug("HealthController --> execute --> param=HealthEnrollmentDetailsHome");
				healthInsuranceBean = healthInsuranceBusiness.getHealthInsuranceEnrollmentDetails(healthInsuranceBean);
				viewName = CPSConstants.HEALTHENROLLMENTDETAILSHOME;
			} else if (CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.SUBSCRIPTIONDETAILSHOME)) {
				log.debug("HealthController --> execute --> param=SubscriptionDetailsHome");
				healthInsuranceBean = healthInsuranceBusiness.getHealthInsuranceEnrollmentDetails(healthInsuranceBean);
				healthInsuranceBean=healthBusiness.getHealthInsEmpList(healthInsuranceBean);
				session.setAttribute(CPSConstants.HEALTHINSURANCEEMPLOYEESLIST , healthInsuranceBean.getHealthEnrollmentDTO());
				
				viewName = CPSConstants.SUBSCRIPTIONDETAILSHOME;
			} else if (CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.EXITFORMALITIESHOME)) {
				log.debug("HealthController --> execute --> param=ExitFormalitiesHome");
				healthInsuranceBean = healthInsuranceBusiness.getHealthInsuranceEnrollmentDetails(healthInsuranceBean);
				viewName = CPSConstants.EXITFORMALITIESHOME;
			} else if (CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.HEALTHENROLLMENTSAVE)) {
				log.debug("HealthController --> execute --> param=HealthEnrollmentSave");
				healthInsuranceBean = healthBusiness.saveCdaAmont(healthInsuranceBean);
				viewName = CPSConstants.REQUESTRESULTPAGE;
			} else if(CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.HEALTHINSURANCEEMPSEARCH)){
				//added by bkr 20/07/2016
			healthInsuranceBean=healthBusiness.getHealthInsEmpList(healthInsuranceBean);
			session.setAttribute(CPSConstants.HEALTHINSURANCEEMPLOYEESLIST , healthInsuranceBean.getHealthEnrollmentDTO());
			viewName = CPSConstants.SUBSCRIPTIONDETAILSHOME;
				
			} else if(CPSUtils.compareStrings(healthInsuranceBean.getParam(), CPSConstants.HEALTHSUBSCRIPTIONSUBMIT)){
				System.out.println("welcome");
				healthBusiness.updateHealthSubscriptionDetails(healthInsuranceBean);
				viewName = CPSConstants.SUBSCRIPTIONDETAILSHOME;
			}
			
			mav = new ModelAndView(viewName, CPSConstants.HEALTH, healthInsuranceBean);
			if (
				CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.HEALTHENROLLMENTSAVE) || 
				CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.SUBSCRIPTIONDETAILSHOME) || 
				CPSUtils.compareStrings(healthInsuranceBean.getParam(),CPSConstants.EXITFORMALITIESHOME) 
				) {
				mav.addObject(CPSConstants.MESSAGE, healthInsuranceBean.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
