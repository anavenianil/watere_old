package com.callippus.web.loan.controller.request;

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
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.LoanRequestProcessBean;
import com.callippus.web.business.requestprocess.LoanRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.loan.beans.request.LoanRequestBean;
import com.callippus.web.loan.business.request.LoanRequestBusiness;

@Controller
@RequestMapping("/loanRequest.htm")
@SessionAttributes
public class LoanRequestController {
	private static Log log = LogFactory.getLog(LoanRequestController.class);
	@Autowired
	private LoanRequestBusiness loanRequestBusiness;
	@Autowired
	private LoanRequestProcess loanRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LOANREQUEST) LoanRequestBean loanBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			loanBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());

			if (CPSUtils.isNullOrEmpty(loanBean.getParam())) {
				loanBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(loanBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}

			if (CPSUtils.compareStrings(loanBean.getParam(), CPSConstants.HOME)) {
				log.debug("LoanRequestController --> execute --> param=home");
				
				if (!CPSUtils.isNullOrEmpty(loanBean.getOfflineSFID())) {
					loanBean.setSfID(loanBean.getOfflineSFID().toUpperCase());
					loanBean=loanRequestBusiness.getEmpNotExist(loanBean);
					//if (CPSUtils.compareStrings(CPSConstants.AUTORUNNOTCOMPLETED, loanBean.getMessage()) || CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, loanBean.getMessage())
						//	|| CPSUtils.compareStrings(CPSConstants.EMPPAYSTOP, loanBean.getMessage()))
					if (CPSUtils.compareStrings(CPSConstants.AUTORUNNOTCOMPLETED, loanBean.getMessage()) ||
							CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, loanBean.getMessage())){
							viewName = CPSConstants.LOANREQUESTPAGE;
							}
					
				}
				
				
				if(!CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, loanBean.getMessage())){
				loanBean.setMessage(loanBean.getType());
				
				loanBean = loanRequestBusiness.getLoanRequestHomeDetails(loanBean);
				loanBean = loanRequestBusiness.getLoanTypeGrid(loanBean);
				loanBean = loanRequestBusiness.getLoanAmountListDetails(loanBean);
				
				session.setAttribute(CPSConstants.DEPARTMENTID, loanBean.getDeptId());
				session.setAttribute("gpfTypes", loanBean.getGpfSubTypes());
				session.setAttribute("festivalsList", loanBean.getFestivalsList());
				session.setAttribute(CPSConstants.LOANTYPEDETAILSLIST, loanBean.getLoanTypeDetailsList());
				session.setAttribute(CPSConstants.LOANLEAVESMAPPINGJSON, loanBean.getLoanLeavesDetailsList());
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, loanBean.getEmployeeDetails());
				session.setAttribute(CPSConstants.PAYMENTDETAILS, loanBean.getPaymentDetails());
				session.setAttribute(CPSConstants.LOANAMOUNTLIST, loanBean.getLoanAmountDetailsList());
				session.setAttribute(CPSConstants.LOANDESIGMAPPINGS, loanBean.getLoanDesigMappingList());
				session.setAttribute(CPSConstants.LOANAMOUNTGRID, loanBean.getLoanAmountGrid());
				/*if (CPSUtils.compareStrings(CPSConstants.AUTORUNNOTCOMPLETED, loanBean.getMessage()) || CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, loanBean.getMessage())
						|| CPSUtils.compareStrings(CPSConstants.EMPPAYSTOP, loanBean.getMessage()))
					viewName = CPSConstants.RESULT;*/
							
				viewName = CPSConstants.LOANREQUESTPAGE;
				}
			} else if (CPSUtils.compareStrings(loanBean.getParam(), CPSConstants.SUBMITREQUEST)) {
				log.debug("LoanRequestController --> execute --> param=submitRequest");
				if (!CPSUtils.isNullOrEmpty(loanBean.getOfflineSFID())) {
					loanBean.setSfID(loanBean.getOfflineSFID());
				}
				// Check the Constraints
				EmployeeBean employeeBean = (EmployeeBean) session.getAttribute(CPSConstants.EMPLOYEEDETAILS);
				EmpPaymentsDTO paymentBean = (EmpPaymentsDTO) session.getAttribute(CPSConstants.PAYMENTDETAILS);
				loanBean.setPaymentDetails(paymentBean);
				loanBean.setEmployeeDetails(employeeBean);
				if (CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.GPFADVANCELOANID) || CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.GPFWITHDRAWALLOANID)) {
					loanBean = loanRequestBusiness.getGpfDetails(loanBean);
				}
				loanBean = loanRequestBusiness.checkConstraints(loanBean);
				if(CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)){
					LoanRequestProcessBean lrb = new LoanRequestProcessBean();
					BeanUtils.copyProperties(loanBean, lrb);
					lrb.setDesignationID(String.valueOf(employeeBean.getDesignation()));
					lrb.setDepartmentID((Integer)session.getAttribute(CPSConstants.DEPARTMENTID));
					lrb.setEmployeeType(employeeBean.getEmploymentId());
					lrb.setIpAddress(request.getRemoteAddr());
					
					lrb.setBasicPay(Float.parseFloat(paymentBean.getBasicPay()));
					lrb.setGradePay(Float.parseFloat(paymentBean.getGradePay()));
					lrb.setDa(paymentBean.getDa());

					loanBean.setResult(loanRequestProcess.initWorkflow(lrb));
					//redirect to workFlow mapping
					loanBean.setRequestID(lrb.getRequestID());
					session.setAttribute("LoanRequestIdwork", loanBean.getRequestID());
					//System.out.println("RAKESH"+session.getAttribute("LoanRequestIdwork"));
					//session.setAttribute("RequestIdWork", loanBean.getRequestID());
				}
				viewName = CPSConstants.LOANREQUESTRESULTPAGE;
			}else if(CPSUtils.compareStrings(CPSConstants.EDITINSTALLMENTS ,loanBean.getParam())){
				log.debug("LoanRequestController --> execute --> param=editInstallments");
				loanBean = loanRequestBusiness.editInstallmentsDetails(loanBean); 
				viewName = CPSConstants.LOANREQUESTDETAILSPAGE;
			}

			mav = new ModelAndView(viewName, CPSConstants.LOANREQUEST, loanBean);
			mav.addObject(CPSConstants.RESULT, loanBean.getResult());
			mav.addObject(CPSConstants.REMARKS, loanBean.getRemarks());
			if (!CPSUtils.isNullOrEmpty(loanBean.getMessage())) {
			mav.addObject(CPSConstants.MESSAGE, loanBean.getMessage());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}