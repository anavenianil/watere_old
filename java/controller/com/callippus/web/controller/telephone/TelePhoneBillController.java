package com.callippus.web.controller.telephone;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.business.telephone.TelePhoneBillBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.telephone.TelephoneBillRequestProcess;
@Controller
@RequestMapping("/telephone.htm")
@SessionAttributes
public class TelePhoneBillController {
	@Autowired
	private TelePhoneBillBusiness telePhoneBillBusiness;
	@Autowired
	private TelephoneBillRequestProcess telephoneBillRequestProcess;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.TELEPHONE) TelePhoneBillBean telePhoneBillBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
		        session = request.getSession(true);
		        if (CPSUtils.compareStrings(CPSConstants.PAGING, telePhoneBillBean.getParam())) {
					viewName = session.getAttribute(CPSConstants.RETURN).toString();
				}
				if (CPSUtils.isNullOrEmpty(telePhoneBillBean.getSfid())) {
					telePhoneBillBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				}
				if (!CPSUtils.isNullOrEmpty(telePhoneBillBean.getType())) {
					session.setAttribute(CPSConstants.MASTERTYPE, telePhoneBillBean.getType());
				}
				if (CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLELIGIBILITYPARAM, telePhoneBillBean.getParam())) {
					//telePhoneBillBean.setDesignationList(telePhoneBillBusiness.getDesignationList());
					viewName = CPSConstants.TELEPHONEBILLELIGIBILITYHOME;
				}else if (CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLREQUESTFORMPARAM, telePhoneBillBean.getParam())) {
					telePhoneBillBean.setMessage(telePhoneBillBusiness.checkTelephoneBillEnableLoginLink(telePhoneBillBean));
					if(CPSUtils.compareStrings(CPSConstants.YES,telePhoneBillBean.getMessage())){
					List<TutionFeeClaimMasterDTO> list = telePhoneBillBusiness.getTelephoneBillClaimMasterDetails(telePhoneBillBean);
					telePhoneBillBean.setTelephoneClaimList(list);
					telePhoneBillBean.setSfid(session.getAttribute("sfid").toString());
					telePhoneBillBusiness.getEmpDetails(telePhoneBillBean);
					}
					viewName = CPSConstants.TELEPHONEBILLREQUESTFORMHOME;
				}else if (CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLFINANCEPARAM, telePhoneBillBean.getParam())) {
					viewName = CPSConstants.TELEPHONEBILLFINANCEHOME;
				} else if (CPSUtils.compareStrings(CPSConstants.SUBMITTELEPHONEBILLREQUESTDETAILS, telePhoneBillBean.getParam())) {
					telePhoneBillBean.setIpAddress(request.getRemoteAddr());
					telePhoneBillBean.setMessage(telePhoneBillBusiness.checkTelephoneBillApplicantDetails(telePhoneBillBean));
					if(CPSUtils.compareStrings(CPSConstants.SUCCESS, telePhoneBillBean.getMessage())){
						telePhoneBillBean.setMessage(telephoneBillRequestProcess.initWorkFlow(telePhoneBillBean));
						//telePhoneBillBean.setRequestID(telePhoneBillBean.getRequestID());
						//session.setAttribute("RequestIDtel",telePhoneBillBean.getRequestID());
						session.setAttribute("RequestIDtel",telePhoneBillBean.getRequestId());
					}
                     //telePhoneBillBean.setMessage(telephoneBillRequestProcess.initWorkFlow(telePhoneBillBean));
					 viewName = CPSConstants.RESULT;
				}else if (CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLCDAPARAM, telePhoneBillBean.getParam())) {
					viewName = CPSConstants.TELEPHONEBILLCDAHOME;
				}else if(CPSUtils.compareStrings(CPSConstants.GETEMPLOYEEDATA, telePhoneBillBean.getParam())) {
					//telePhoneBillBean=telePhoneBillBusiness.getEmployeeWithDesignationWise(telePhoneBillBean);
					//session.setAttribute("telephoneBillEmpSelectedList", telePhoneBillBean.getSelectedEmployeeList());
					//session.setAttribute("telephoneBillEmpNotSelectedList", telePhoneBillBean.getNotSelectedEmployeeList());
					viewName = CPSConstants.TELEPHONEBILLEMPLOYEELIST;
				}else if(CPSUtils.compareStrings(CPSConstants.SUBMITTELEBILLEMPLIST, telePhoneBillBean.getParam())){	
					telePhoneBillBean.setMessage(telePhoneBillBusiness.submitTelephoneElibilityDetails(telePhoneBillBean));
					viewName = CPSConstants.RESULT;
				}else if(CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLEMPLOYEEELIGIBILITYDETAILS, telePhoneBillBean.getParam())){
					List<KeyValueDTO> list=(telePhoneBillBusiness.getTelephoneSelectedEmployeeList());
					telePhoneBillBean.setTelephoneEmployeeSelectedList(list);
					viewName="TelephoneBillEmployeeEligibilityDetails";
				}else if(CPSUtils.compareStrings(CPSConstants.GETEMPLOYEEDESIGNATIONLISTDATA, telePhoneBillBean.getParam())){
					/*telePhoneBillBean=telePhoneBillBusiness.getEmployeeWithDesignationWise(telePhoneBillBean);
					session.setAttribute("telephoneBillEmpNotSelectedList", telePhoneBillBean.getNotSelectedEmployeeList());*/
				     telePhoneBillBean=telePhoneBillBusiness.getEmployeeSfidDesignationWiseList(telePhoneBillBean);
				     session.setAttribute("telephoneBillEmpSelectedList", telePhoneBillBean.getSelectedEmployeeList());
					 session.setAttribute("telephoneBillEmpNotSelectedList", telePhoneBillBean.getNotSelectedEmployeeList());
				   /* if(telePhoneBillBean.getSelectedEmployeeList()!=null){
				    	 telePhoneBillBean.setMessage(telePhoneBillBusiness.submitTelephoneEmpEligibiltyDetails(telePhoneBillBean));
				    }*/
					 viewName="TelephoneBillEmployeeEligibilityDetailsList";
				}
				// code for telephone desig eligibility details
				else if(CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLDESIGNATIONEMPLOYEEDETAILS, telePhoneBillBean.getParam())){
					telePhoneBillBean.setTelephoneDesignationDeSelectedList((telePhoneBillBusiness.getTelephoneDesignationDeSelectedList()));
					List<TelephoneDesigEligibilityDetailsDTO> list = telePhoneBillBusiness.getTelephoneDesigEligibilityDetails(telePhoneBillBean);
					telePhoneBillBean.setTelephoneDesigList(list);
					viewName="TelePhoneBillDesigEmployeeDetails";
				}else if(CPSUtils.compareStrings(CPSConstants.SUBMITTELEPHONEDESIGELIGIBILITYDETAILS, telePhoneBillBean.getParam())){
					telePhoneBillBean.setMessage(telePhoneBillBusiness.submitTelephoneDesigEligibilityDetails(telePhoneBillBean));
					List<TelephoneDesigEligibilityDetailsDTO> list = telePhoneBillBusiness.getTelephoneDesigEligibilityDetails(telePhoneBillBean);
					telePhoneBillBean.setTelephoneDesigList(list);
					viewName="TelePhoneBillDesigEmployeeDetailsList";
				}else if(CPSUtils.compareStrings(CPSConstants.DELETETELEPHONEDESIGELIGIBILITYDETAILS, telePhoneBillBean.getParam())){
					telePhoneBillBean.setMessage(telePhoneBillBusiness.deleteDesignationEligibilityDetails(telePhoneBillBean));
					List<TelephoneDesigEligibilityDetailsDTO> list = telePhoneBillBusiness.getTelephoneDesigEligibilityDetails(telePhoneBillBean);
					telePhoneBillBean.setTelephoneDesigList(list);
					viewName="TelePhoneBillDesigEmployeeDetailsList";
				}else if(CPSUtils.compareStrings(CPSConstants.GETSELECTEDDESIGNATIONLIST, telePhoneBillBean.getParam())){
					List<DesignationDTO> list=(telePhoneBillBusiness.getTelephoneDesignationSelectedList(telePhoneBillBean));
					telePhoneBillBean.setTelephoneDesignationSelectedList(list);
					viewName="TelephoneEmpDesignationList";
				}
				// code for submiting the selected employee details
				else if(CPSUtils.compareStrings(CPSConstants.SUBMITSELECTEDTELEPHONEEMPLOYEELIST, telePhoneBillBean.getParam())){
				      telePhoneBillBean.setMessage(telePhoneBillBusiness.submitTelephoneEmpEligibiltyDetails(telePhoneBillBean));
				      viewName = CPSConstants.RESULT;
				}else if(CPSUtils.compareStrings(CPSConstants.CHECKTELEEMPDETAILSWITHINTERNET,telePhoneBillBean.getParam())){
					 telePhoneBillBusiness.checkTelephoneBillInternetDetails(telePhoneBillBean);
					 viewName ="telephoneBillRequestFormHomeInternetDetails";
				}
				//cash Assignment screen
				else if(CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLCASHASSIGNMENTDETAILS, telePhoneBillBean.getParam())){
					 telePhoneBillBean.setTeleCashDetails(telePhoneBillBusiness.getTelephoneCashAssignment());
					viewName="TelephoneCashAssignment";
				}else if(CPSUtils.compareStrings(CPSConstants.SUBMITTELEPHONECASHASSIGNAMENTDETAILS, telePhoneBillBean.getParam())){
					telePhoneBillBean.setMessage(telePhoneBillBusiness.submitTelephoneCashAssignment(telePhoneBillBean));
				    telePhoneBillBean.setTeleCashDetails(telePhoneBillBusiness.getTelephoneCashAssignment());
				    viewName="TelephoneCashAssignmentList";
				}else if(CPSUtils.compareStrings(CPSConstants.DELETETELECASHASSIGNMENTMASTER, telePhoneBillBean.getParam())){
					telePhoneBillBean.setMessage(telePhoneBillBusiness.deleteCashAssignmentDetails(Integer.parseInt(telePhoneBillBean.getPk())));
					telePhoneBillBean.setTeleCashDetails(telePhoneBillBusiness.getTelephoneCashAssignment());
					viewName="TelephoneCashAssignmentList";
				}
				//missing period 
				else if(CPSUtils.compareStrings(CPSConstants.CHECKMISSINGPERIODDETAILS, telePhoneBillBean.getParam())){
					telePhoneBillBean=telePhoneBillBusiness.checkMissingPeriodDetails(telePhoneBillBean);
				   viewName="telephoneBillMissingPeriodDetails";
				}
				
				
				mav = new ModelAndView(viewName, CPSConstants.TELEPHONE, telePhoneBillBean);
				if (!CPSUtils.isNullOrEmpty(telePhoneBillBean.getMessage())) {
					mav.addObject(CPSConstants.MESSAGE, telePhoneBillBean.getMessage());
				}
				if (!CPSUtils.isNullOrEmpty(telePhoneBillBean.getRemarks())) {
					mav.addObject(CPSConstants.REMARKS, telePhoneBillBean.getRemarks());
				} 
}catch (Exception e) {
		throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
	}
	return mav;
 }
}
