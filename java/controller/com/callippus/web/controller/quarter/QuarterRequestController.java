package com.callippus.web.controller.quarter;

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

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.quarter.QuarterBean;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.business.quarter.QuarterBusiness;
import com.callippus.web.business.quarter.QuarterRequestBusiness;
import com.callippus.web.business.requestprocess.QuarterRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/quarterReq.htm")
@SessionAttributes
public class QuarterRequestController {

	private static Log log = LogFactory.getLog(QuarterRequestController.class);
	@Autowired
	private QuarterRequestBusiness quarterRequestBusiness;
	@Autowired
	private QuarterRequestProcess quarterRequestProcess;
	@Autowired
	private QuarterBusiness quarterDetailsBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.QUARTERREQUEST) QuarterRequestBean quarter, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		String message = null;
		try {
			//ErpAction.userChecks(request);
			session = request.getSession(true);
			if(!CPSUtils.compareStrings("sfidDetails", quarter.getParam()))
				quarter.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if(!CPSUtils.isNullOrEmpty(quarter.getType()))
				session.setAttribute(CPSConstants.QUARTERTITLETYPE, quarter.getType());
			if(CPSUtils.compareStrings(CPSConstants.VIEW, quarter.getParam())) {
				log.debug("QuarterController --> onSumit --> param=emuDetailsHome");
				quarter = quarterRequestBusiness.getQuarterSubTypeList(quarter);
				session.setAttribute("emuSFID", quarterRequestBusiness.getQuarterEmuDetailsList(quarter.getType()));
				session.setAttribute("emuSFIDJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute("emuSFID")));
				session.setAttribute("letterNumberList", quarterRequestBusiness.getEMULetterNumbersList(quarter));
				if(!CPSUtils.isNullOrEmpty(session.getAttribute("letterNumberList")))
					session.setAttribute("letterNumberListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute("letterNumberList")));
				mav = new ModelAndView("emuDetailsHome", CPSConstants.QUARTERREQUEST, quarter);
			} else if(CPSUtils.compareStrings("saveEmuDetailsForQuarter", quarter.getParam())) {
				log.debug("QuarterController --> onSumit --> param=saveEmuDetailsForQuarter");
				quarter.setSfID1(session.getAttribute(CPSConstants.SFID).toString());
				message = quarterRequestBusiness.updateQuarterDetailsWithEMU(quarter);
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.QUARTERREQUEST, quarter);
				//mav = new ModelAndView("emuDetailsHome", CPSConstants.QUARTERREQUEST, quarter);
				mav.addObject(CPSConstants.MESSAGE, message);
			} else if(CPSUtils.compareStrings("emuRequestForm", quarter.getParam())) {
				log.debug("QuarterController --> onSumit --> param=emuRequestForm");
				session.setAttribute("emuSFIDList", quarterRequestBusiness.getEMUSendingSFIDList());
				session.setAttribute("emuSFIDListJSON", (JSONArray) JSONSerializer.toJSON(session.getAttribute("emuSFIDList")));
				session.setAttribute("letterNumberList", quarterRequestBusiness.getEMULetterNumbersList(quarter));
				if(!CPSUtils.isNullOrEmpty(session.getAttribute("letterNumberList")))
					session.setAttribute("letterNumberListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute("letterNumberList")));
				mav = new ModelAndView("emuRequestForm", CPSConstants.QUARTERREQUEST, quarter);
			} else if(CPSUtils.compareStrings("saveRequestFormDetails", quarter.getParam())) {
				log.debug("QuarterController --> onSumit --> param=saveRequestFormDetails");
				quarter.setSfID1(session.getAttribute(CPSConstants.SFID).toString());
				message = quarterRequestBusiness.saveEmuRequestDetails(quarter);
				if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
					session.removeAttribute("emuSFIDList");
					session.setAttribute("emuSFIDList", quarterRequestBusiness.getEMUSendingSFIDList());
					session.setAttribute("emuSFIDListJSON", (JSONArray) JSONSerializer.toJSON(session.getAttribute("emuSFIDList")));
				}
				//mav = new ModelAndView("emuRequestForm", CPSConstants.QUARTERREQUEST, quarter);
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.QUARTERREQUEST, quarter);
				mav.addObject(CPSConstants.MESSAGE, message);
			} else if(CPSUtils.compareStrings("quarterGradePay", quarter.getParam())) {
				log.debug("QuarterController --> onSumit --> param=quarterGradePay");
				quarter = quarterRequestBusiness.getQuarterSubTypeList(quarter);
				session.setAttribute(CPSConstants.GRADEPAYLIST, quarterRequestBusiness.getUniqueGradePays());
				session.setAttribute(CPSConstants.QUARTERTYPELISTS, quarterRequestBusiness.getQuarterTypeList());
				mav = new ModelAndView("quarterGradePayDetails", CPSConstants.QUARTERREQUEST, quarter);
			} else if(CPSUtils.compareStrings("saveQuarterGradePays", quarter.getParam())) {
				log.debug("QuarterController --> onSumit --> param=quarterGradePay");
				//quarter.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				message = quarterRequestBusiness.saveQuarterGradePays(quarter);
				mav = new ModelAndView("Result", CPSConstants.QUARTERREQUEST, quarter);
				mav.addObject(CPSConstants.MESSAGE, message);
			} else if (CPSUtils.compareStrings("getSelQuarterTypes", quarter.getParam())) {
				log.debug("QuarterController --> onSubmit --> param=getSelQuarterTypes");
				session.setAttribute(CPSConstants.GRADEPAYLIST, (JSONArray) JSONSerializer.toJSON(quarterRequestBusiness.getUniqueGradePays()));
				session.setAttribute("deQuarterTypeList", (JSONArray) JSONSerializer.toJSON(quarterRequestBusiness.getMappedQuarterGrades(quarter.getQuarterType())));
				viewName = "quarterGradePayList";
				mav = new ModelAndView(viewName, CPSConstants.QUARTERREQUEST, quarter);
			}	

			//offline entry with WorkFlow starts
			else if(CPSUtils.compareStrings("quarterOfflineEntry",quarter.getParam())) {
				log.debug("QuarterRequestController --> onSubmit --> param=quarterOfflineEntry");
				quarter.setSfID("");
				mav = new ModelAndView("NewQuarterRequest", CPSConstants.QUARTERREQUEST, quarter);
			}
			else if(CPSUtils.compareStrings("sfidDetails",quarter.getParam())) {
				log.debug("QuarterRequestController --> onSubmit --> param=sfidDetails");
				quarter = quarterRequestBusiness.getQuarterRequestHomeDetails(quarter);
				if(!CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, quarter.getResult()))
				{
					QuarterBean quarterBean=new QuarterBean();
					quarterBean.setLogInSfID(quarter.getSfID());
					quarterDetailsBusiness.getUserQuarterDetails(quarterBean);
					quarter.setResult(quarterBean.getResult());
					if(!CPSUtils.isNull(quarter.getResult())){
						session.setAttribute(CPSConstants.EMPLOYEEDETAILS, quarter.getEmployeeDetails());
						session.setAttribute(CPSConstants.PAYMENTDETAILS, quarter.getEmployeePaymentDetails());
						if(quarter.getQuarterTypeList() != null)
							session.setAttribute("quarterListJson", (JSONArray) JSONSerializer.toJSON(quarter.getQuarterTypeList()));
					}
				}
				mav = new ModelAndView("quarterOfflineEntry", CPSConstants.QUARTERREQUEST, quarter);
				mav.addObject(CPSConstants.MESSAGE, quarter.getResult());
			}
			//without WorkFlow
			else if(CPSUtils.compareStrings("quarterOfflineDetailsEntry", quarter.getParam())) {
				/*log.debug("QuarterRequestController --> onSubmit --> param=quarterOfflineDetailsEntry");
				quarter=quarterRequestBusiness.getQuarterSubTypeList(quarter);
				session.setAttribute("quarterTypeList",quarterRequestBusiness.getQuarterTypeDetails(quarter));
				session.setAttribute("quarterSubTypeList", quarter.getQuarterSubTypeList());*/
				quarter.setSfID((String)session.getAttribute("Labid"));
				mav = new ModelAndView("quarterOfflineDetailsEntry", CPSConstants.QUARTERREQUEST, quarter);
			}
			else if(CPSUtils.compareStrings("sfidQuarterDetails",quarter.getParam())){
				log.debug("QuarterRequestController --> onSubmit --> param=sfidQuarterDetails");
				quarter = quarterRequestBusiness.getQuarterRequestHomeDetails(quarter);

				mav = new ModelAndView("quarterOfflineEntry", CPSConstants.QUARTERREQUEST, quarter);
				if(CPSUtils.isNull(quarter.getEmployeeDetails()))
					mav.addObject(CPSConstants.MESSAGE,CPSConstants.EMPNOTEXISTS);

			}

			/*else if(CPSUtils.compareStrings("saveQuarterOfflineDetails,quarterOfflineDetailsEntry",quarter.getParam()) || 
					CPSUtils.compareStrings("saveQuarterOfflineDetails",quarter.getParam())){*/
			else if(CPSUtils.compareStrings("onchangeSfid",quarter.getParam())) {
				quarter.setEmployeeDetails(quarterRequestBusiness.getEmployeeDetails(quarter.getSfID1()));
				if(CPSUtils.isNull(quarter.getEmployeeDetails())) {
					mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.QUARTERREQUEST, quarter);
					mav.addObject(CPSConstants.MESSAGE,CPSConstants.EMPNOTEXISTS);
				} else {
					session.setAttribute("userSfid", quarter.getSfID());
					quarter.setQuarterTypeList(quarterRequestBusiness.getQuarterTypeList());
					quarter.setQuarterSubTypeList(quarterRequestBusiness.getQuarterSubTypeList());
					session.setAttribute("quarterTypeList", quarter.getQuarterTypeList());
					session.setAttribute("quarterSubTypeList", quarter.getQuarterSubTypeList());
					//quarter.setEmployeeDetails(quarterRequestBusiness.getEmployeeDetails(quarter.getSfID()));
					quarter.setEmpQuarterDetails(quarterRequestBusiness.getQuarterOfflineDetailsList(quarter.getSfID1()));
					session.setAttribute("empQuarterDetails", quarter.getEmpQuarterDetails());
					session.setAttribute("quarterOfflineDetailsEntryList", (JSONArray) JSONSerializer.toJSON(quarter.getEmpQuarterDetails()));
					mav = new ModelAndView("quarterOfflineEntryDetails", CPSConstants.QUARTERREQUEST, quarter);
					mav.addObject(CPSConstants.MESSAGE, message);
				}
			}

			else if(CPSUtils.compareStrings("saveQuarterOfflineDetails",quarter.getParam())) {
				log.debug("QuarterController --> onSumit --> param=saveQuarterOfflineDetails");
				//quarter.setSfID1(session.getAttribute(CPSConstants.SFID).toString());
				message = quarterRequestBusiness.saveQuarterOfflineDetails(quarter);
				//quarter.setEmpQuarterDetails(quarterRequestBusiness.getQuarterOfflineDetailsList((String)session.getAttribute("userSfid")));
				quarter.setEmpQuarterDetails(quarterRequestBusiness.getQuarterOfflineDetailsList(quarter.getSfID1()));
				session.setAttribute("empQuarterDetails", quarter.getEmpQuarterDetails());
				session.setAttribute("quarterOfflineDetailsEntryList", (JSONArray) JSONSerializer.toJSON(quarter.getEmpQuarterDetails()));
				mav = new ModelAndView("quarterOfflineDetailsEntryList", CPSConstants.QUARTERREQUEST, quarter);
				mav.addObject(CPSConstants.MESSAGE, message);
			} else if(CPSUtils.compareStrings("deleteQuarterOfflineDetails", quarter.getParam())) {
				log.debug("QuarterController --> onDelete --> param=deleteQuarterOfflineDetails");
				quarter.setSfID1(session.getAttribute(CPSConstants.SFID).toString());
				message = quarterRequestBusiness.deleteQuarterOfflineDetails(Integer.parseInt(quarter.getParentID()));
				quarter.setEmpQuarterDetails(quarterRequestBusiness.getQuarterOfflineDetailsList((String)session.getAttribute("userSfid")));
				session.setAttribute("empQuarterDetails", quarter.getEmpQuarterDetails());
				session.setAttribute("quarterOfflineDetailsEntryList", (JSONArray) JSONSerializer.toJSON(quarter.getEmpQuarterDetails()));
				mav = new ModelAndView("quarterOfflineDetailsEntryList", CPSConstants.QUARTERREQUEST, quarter);
				mav.addObject(CPSConstants.MESSAGE, message);
			}

			//offline entry ends
			else{
				if (CPSUtils.isNullOrEmpty(quarter.getParam())) {
					quarter.setParam(CPSConstants.HOME);
				}
				quarter.setSfID(session.getAttribute(CPSConstants.SFID).toString());

				if (CPSUtils.compareStrings(quarter.getParam(), CPSConstants.HOME)) {
					log.debug("QuarterRequestController --> onSubmit --> param=home");
					if(CPSUtils.compareString(quarter.getType(), "change")){
						quarter = quarterRequestBusiness.getAppliedQuarterType(quarter);
						quarter.setType("change");
					}
					quarter = quarterRequestBusiness.getQuarterRequestHomeDetails(quarter);
					session.setAttribute(CPSConstants.EMPLOYEEDETAILS, quarter.getEmployeeDetails());
					session.setAttribute(CPSConstants.PAYMENTDETAILS, quarter.getEmployeePaymentDetails());
					
					if(quarter.getQuarterTypeList()!=null)
					session.setAttribute("quarterList", (JSONArray) JSONSerializer.toJSON(quarter.getQuarterTypeList()));
					viewName = CPSConstants.NEWQUARTERREQUEST;
				} else if (CPSUtils.compareStrings(quarter.getParam(), CPSConstants.SUBMITNEWREQUEST)) {
					log.debug("QuarterRequestController --> onSubmit --> param=submitNewRequest");
					quarter.setRequestType(CPSConstants.NEWQUARTERREQ);
					quarter.setEmployeeDetails((EmployeeBean) session.getAttribute(CPSConstants.EMPLOYEEDETAILS));
					quarter.setEmployeePaymentDetails((EmpPaymentsDTO) session.getAttribute(CPSConstants.PAYMENTDETAILS));
					quarter.setDesignationID(String.valueOf(quarter.getEmployeeDetails().getDesignation()));
					quarter.setDepartmentID(String.valueOf(quarter.getEmployeeDetails().getOffice()));
					quarter.setBasicPay(quarter.getEmployeePaymentDetails().getBasicPay());
					quarter.setGradePay(quarter.getEmployeePaymentDetails().getGradePay());
					quarter.setIpAddress(request.getRemoteAddr());
					quarter.setResult(quarterRequestProcess.initWorkflow(quarter));
					session.setAttribute("quarterrequestID", quarter.getRequestID());
					if(CPSUtils.compareStrings(CPSConstants.SUCCESS, quarter.getResult()));
					quarter.setResult("quarterRequest");
					viewName = CPSConstants.RESULT;
				} else if (CPSUtils.compareStrings(quarter.getParam(), CPSConstants.SUBMITCHANGEREQUEST)) {
					log.debug("QuarterRequestController --> onSubmit --> param=submitChangeRequest");
					quarter.setRequestType(CPSConstants.CHANGEQUARTERREQ);
					quarter.setEmployeeDetails((EmployeeBean) session.getAttribute(CPSConstants.EMPLOYEEDETAILS));
					quarter.setEmployeePaymentDetails((EmpPaymentsDTO) session.getAttribute(CPSConstants.PAYMENTDETAILS));
					quarter.setDesignationID(String.valueOf(quarter.getEmployeeDetails().getDesignation()));
					quarter.setDepartmentID(String.valueOf(quarter.getEmployeeDetails().getOffice()));
					quarter.setBasicPay(quarter.getEmployeePaymentDetails().getBasicPay());
					quarter.setGradePay(quarter.getEmployeePaymentDetails().getGradePay());
					quarter.setIpAddress(request.getRemoteAddr());
					quarter.setResult(quarterRequestProcess.initWorkflow(quarter));
					if(CPSUtils.compareStrings(CPSConstants.SUCCESS,quarter.getResult()));
					session.setAttribute("quarterrequestID", quarter.getRequestID());
					quarter.setResult("quarterRequest");
					viewName = CPSConstants.RESULT;
				}
				mav = new ModelAndView(viewName, CPSConstants.QUARTERREQUEST, quarter);
				mav.addObject(CPSConstants.RESULT, quarter.getResult());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
