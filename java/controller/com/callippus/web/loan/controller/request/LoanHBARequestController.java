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
import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.business.requestprocess.LoanHBARequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.loan.beans.request.LoanHBARequestBean;
import com.callippus.web.loan.business.request.LoanHBARequestBusiness;

@Controller
@RequestMapping("/loanHbaRequest.htm")
@SessionAttributes
public class LoanHBARequestController {

	private static Log log = LogFactory.getLog(LoanHBARequestController.class);

	@Autowired
	private LoanHBARequestBusiness loanRequestBusiness;
	@Autowired
	private LoanHBARequestProcess loanRequestProcess;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LOANHBAREQUEST) LoanHBARequestBean loanBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			loanBean.setSfID(String.valueOf(session.getAttribute(CPSConstants.SFID)));
			if (CPSUtils.isNullOrEmpty(loanBean.getParam())) {
				loanBean.setParam(CPSConstants.HBAHOME);
			} else if (CPSUtils.compareStrings(loanBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			/**
			 * House Building Advance starts
			 * 
			 */
			if (CPSUtils.compareStrings(loanBean.getParam(),CPSConstants.HBAHOME)) {
				log.debug("LoanManagementController --> onSubmit --> param=hbahome");
				if (!CPSUtils.isNullOrEmpty(loanBean.getOfflineSFID())) {
					loanBean.setSfID(loanBean.getOfflineSFID());
				}
				loanBean = loanRequestBusiness.getLoanHBARequestHomeDetails(loanBean);
				
				session.setAttribute(CPSConstants.DEPARTMENTID, loanBean.getDeptId());
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, loanBean.getEmployeeDetails());
				session.setAttribute(CPSConstants.PAYMENTDETAILS, loanBean.getPaymentDetails());
				viewName = CPSConstants.LOANHOUSEBUILDINGADVANCE;
			} else if (CPSUtils.compareStrings(loanBean.getParam(), CPSConstants.SUBMITHBAREQUEST)) {
				log.debug("LoanRequestController --> execute --> param=submitHBARequest");
				if (!CPSUtils.isNullOrEmpty(loanBean.getOfflineSFID())) {
					loanBean.setSfID(loanBean.getOfflineSFID());
				}
				// Check the Constraints
				loanBean = loanRequestBusiness.getLoanHBARequestHomeDetails(loanBean);
				Object obj = session.getAttribute(CPSConstants.EMPLOYEEDETAILS);
				EmployeeBean employeeBean = (EmployeeBean)obj;
				EmpPaymentsDTO paymentBean = (EmpPaymentsDTO) session.getAttribute(CPSConstants.PAYMENTDETAILS);
				loanBean.setEmployeeDetails(employeeBean);
				loanBean = loanRequestBusiness.checkConstraints(loanBean);
				if(CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)){
					LoanHBARequestProcessBean lrb = new LoanHBARequestProcessBean();
					BeanUtils.copyProperties(loanBean, lrb);
					lrb.setDesignationID(String.valueOf(employeeBean.getDesignation()));
					lrb.setDepartmentID((Integer)session.getAttribute(CPSConstants.DEPARTMENTID));
					lrb.setEmployeeType(employeeBean.getEmploymentId());
					lrb.setIpAddress(request.getRemoteAddr());

					lrb.setBasicPay(Float.parseFloat(paymentBean.getBasicPay()));
					lrb.setGradePay(Float.parseFloat(paymentBean.getGradePay()));
					lrb.setDa(paymentBean.getDa());
					
					loanBean.setResult(loanRequestProcess.initWorkflow(lrb));
				//	loanBean.setRequestID(lrb.getRequestID());
				//session.setAttribute("LoanHbaRequestIdwork", loanBean.getRequestID());
					loanBean.setRequestID(lrb.getRequestID());
					session.setAttribute("LoanHbaRequestIdwork", loanBean.getRequestID());
				}
				viewName = CPSConstants.LOANREQUESTRESULTPAGE;
			}else if(CPSUtils.compareStrings(CPSConstants.EDITHBA ,loanBean.getParam())){
				log.debug("LoanRequestController --> execute --> param=editHBA");
				loanBean = loanRequestBusiness.editHBADetails(loanBean); 
				viewName = CPSConstants.LOANREQUESTDETAILSPAGE;
			}
			/**
			 * House Building Advance Ends
			 * 
			 */

			mav = new ModelAndView(viewName, CPSConstants.LOANHBAREQUEST, loanBean);
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
