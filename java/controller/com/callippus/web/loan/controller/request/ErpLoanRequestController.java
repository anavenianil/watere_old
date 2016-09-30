package com.callippus.web.loan.controller.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
/*import org.apache.commons.logging.Log;*/
import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.business.requestprocess.ErpLoanRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.loan.beans.dto.ErpLoanRequestDTO;
import com.callippus.web.loan.beans.request.ErpLoanRequestBean;
import com.callippus.web.loan.business.request.ErpLoanRequestBusiness;

@Controller
@RequestMapping("/erpLoanRequest.htm")
@SessionAttributes
public class ErpLoanRequestController {
	private static Log log = LogFactory.getLog(ErpLoanRequestController.class);
	@Autowired
	private ErpLoanRequestBusiness erpLoanRequestBusiness;
	@Autowired
	private ErpLoanRequestProcess erpLoanRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.ERPLOANREQUESTS) ErpLoanRequestBean erpLoanRequestBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
		//	ErpAction.userChecks(request);
			session = request.getSession(true);

			erpLoanRequestBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());

			if (CPSUtils.isNullOrEmpty(erpLoanRequestBean.getParam())) {
				erpLoanRequestBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(erpLoanRequestBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}

			if (CPSUtils.compareStrings(erpLoanRequestBean.getParam(), CPSConstants.ERPLOANREQUESTPAGE)) {
				log.debug("ErpLoanRequestController --> execute --> param=home");
				if(!CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, erpLoanRequestBean.getMessage())){
					erpLoanRequestBean.setMessage(erpLoanRequestBean.getType());
					
					erpLoanRequestBean = erpLoanRequestBusiness.getLoanRequestHomeDetails(erpLoanRequestBean);
					
					session.setAttribute(CPSConstants.DEPARTMENTID, erpLoanRequestBean.getDeptId());
					session.setAttribute(CPSConstants.EMPLOYEEDETAILS, erpLoanRequestBean.getEmployeeDetails());
					session.setAttribute(CPSConstants.PAYMENTDETAILS, erpLoanRequestBean.getPaymentDetails());
					session.setAttribute(CPSConstants.ERPLOANTYPES, erpLoanRequestBean.getErpLoanTypes());
					
								
					viewName = CPSConstants.ERPLOANREQUESTPAGE;
					}
			}
			else if (CPSUtils.compareStrings(erpLoanRequestBean.getParam(), CPSConstants.ERPSUBMITREQUEST)) {
				if (!CPSUtils.isNullOrEmpty(erpLoanRequestBean.getOfflineSfID())) {
					erpLoanRequestBean.setSfID(erpLoanRequestBean.getOfflineSfID());
				}
				// Check the Constraints
				erpLoanRequestBean = erpLoanRequestBusiness.getLoanRequestHomeDetails(erpLoanRequestBean);
				erpLoanRequestBean.setBasicPay(Float.valueOf(erpLoanRequestBean.getPaymentDetails().getBasicPay()));
				erpLoanRequestBean.setGrossPay(Float.valueOf(erpLoanRequestBean.getPaymentDetails().getGradePay()));
				erpLoanRequestBean = erpLoanRequestBusiness.checkConstraints(erpLoanRequestBean);
				/*if(Integer.parseInt(erpLoanRequestBean.getExperience())>3){
					erpLoanRequestBean.setRemarks(CPSConstants.LOANNOTELIGIBLE);
					erpLoanRequestBean.setMessage(CPSConstants.LOANMINEXPREMARK);
					
					viewName = CPSConstants.ERPLOANREQUESTPAGE;
				}else{
					ErpLoanRequestDTO erpLoanRequestDTO = new ErpLoanRequestDTO();
					BeanUtils.copyProperties(erpLoanRequestDTO, erpLoanRequestBean);

					erpLoanRequestBean.setResult(erpLoanRequestProcess.initWorkflow(erpLoanRequestDTO));
					erpLoanRequestBean.setRequestID(erpLoanRequestDTO.getRequestID());
					session.setAttribute("LoanRequestIdwork", erpLoanRequestBean.getRequestID());
				}*/
				ErpLoanRequestDTO erpLoanRequestDTO = new ErpLoanRequestDTO();
				BeanUtils.copyProperties(erpLoanRequestDTO, erpLoanRequestBean);

				erpLoanRequestBean.setResult(erpLoanRequestProcess.initWorkflow(erpLoanRequestDTO));
				erpLoanRequestBean.setRequestID(erpLoanRequestDTO.getRequestID());
				session.setAttribute("LoanRequestIdwork", erpLoanRequestBean.getRequestID());
				viewName = CPSConstants.ERPLOANREQUESTRESULTPAGE;
				
			}
			mav = new ModelAndView(viewName, CPSConstants.ERPLOANREQUESTS, erpLoanRequestBean);
			mav.addObject(CPSConstants.RESULT, erpLoanRequestBean.getResult());
			mav.addObject(CPSConstants.REMARKS, erpLoanRequestBean.getRemarks());
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}