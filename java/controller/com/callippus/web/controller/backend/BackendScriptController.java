package com.callippus.web.controller.backend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.callippus.web.beans.backend.BackendBean;
import com.callippus.web.business.family.FamilyBusiness;
import com.callippus.web.business.requestprocess.LeaveRequestProcess;
import com.callippus.web.business.requestprocess.RequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.ltc.business.master.LtcMasterBusiness;
import com.callippus.web.promotions.business.management.PromotionManagementBusiness;

@Controller
@RequestMapping("/backend.htm")
@SessionAttributes
public class BackendScriptController {
	private static Log log = LogFactory.getLog(BackendScriptController.class);
	@Autowired
	private LeaveRequestProcess leaveRequestProcess;
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	private LtcMasterBusiness ltcBusiness;
	@Autowired
	private FamilyBusiness familyBusiness;
	@Autowired 
	private PromotionManagementBusiness promotionManagementBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute BackendBean bean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			if (CPSUtils.compareStrings(CPSConstants.HOUR, bean.getParam())) {
				log.debug("BackendScriptController --> execute --> start --> param=hour");

				log.debug("BackendScriptController --> execute --> end --> param=hour");
			} else if (CPSUtils.compareStrings(CPSConstants.DAY, bean.getParam())) {
				log.debug("BackendScriptController --> execute --> start --> param=day");

				log.debug("Leave credits from backend started");
				//bean.setResult(leaveRequestProcess.autoRunScript(null));
				log.debug("Leave credits from backend ends");

				log.debug("Request escalation from backend started");
				bean.setResult(requestProcess.escalatedRequests());
				log.debug("Request escalation from backend ends");

				log.debug("CGHS and LTC facility updation from backend started");
				//bean.setResult(familyBusiness.updateLtcAndCghsFacility());
				log.debug("CGHS and LTC facility updation from backend ends");
				
				log.debug("Promotion Designation and seniority date updation from backend started");
				//bean.setResult(promotionManagementBusiness.desigUpdation());
				log.debug("Promotion Designation and seniority date updation from backend ends");
				
				log.debug("BackendScriptController --> execute --> end --> param=day");
			} else if (CPSUtils.compareStrings(CPSConstants.MONTH, bean.getParam())) {
				log.debug("BackendScriptController --> execute --> start --> param=month");

				log.debug("BackendScriptController --> execute --> end --> param=month");
			} else if (CPSUtils.compareStrings(CPSConstants.YEAR, bean.getParam())) {
				log.debug("BackendScriptController --> execute --> start --> param=year");

				
				log.debug("Leave balance copy to next year from backend started");
				//bean.setResult(leaveRequestProcess.runYearScript(CPSUtils.getCurrentDate()));
				log.debug("Leave balance copy to next year from backend ends");

				log.debug("LTC One Year Block insert from backend started");
				//bean.setResult(ltcBusiness.insertLtcYearBlock(CPSConstants.ONE));
				log.debug("LTC One Year Block insert from backend ends");

				log.debug("Updation of AGE field in Family_Details Table from backend start");
				bean.setResult(familyBusiness.ageFieldUpdation());
				log.debug("Updation of AGE field in Family_Details Table from backend end");
				
				log.debug("BackendScriptController --> execute --> end --> param=year");
			} 

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return null;
	}
}