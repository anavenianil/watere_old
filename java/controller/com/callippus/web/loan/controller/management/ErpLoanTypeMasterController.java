package com.callippus.web.loan.controller.management;

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

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.loan.beans.management.ErpLoanManagementBean;
import com.callippus.web.loan.business.management.ErpLoanManagementBusiness;

@Controller
@RequestMapping("/erpLoanPurpose.htm")
@SessionAttributes
public class ErpLoanTypeMasterController {
	private static Log log = LogFactory.getLog(ErpLoanTypeMasterController.class);
	@Autowired
	private ErpLoanManagementBusiness erpLoanManagementBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.ERPLOAN) ErpLoanManagementBean loanBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
			//ErpAction.userChecks(request);
			session = request.getSession(true);
			loanBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			
			

			if (CPSUtils.compareStrings(CPSConstants.ERPLOANPURPOSE, loanBean.getParam())) {
				loanBean = erpLoanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.setAttribute(CPSConstants.LOANMASTERLIST, loanBean.getErpLoanTypeMasterList());
				viewName = CPSConstants.ERPLOANPURPOSE;
			}else if (CPSUtils.compareStrings(CPSConstants.MANAGE, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=manage");
				loanBean = erpLoanManagementBusiness.submitLoanTypeMasterDetails(loanBean);
				loanBean = erpLoanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.setAttribute(CPSConstants.LOANMASTERLIST, loanBean.getErpLoanTypeMasterList());
				viewName = CPSConstants.ERPLOANPURPOSELIST;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETE, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=delete");
				loanBean = erpLoanManagementBusiness.deleteLoanTypeMasterDetails(loanBean);
				loanBean = erpLoanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.setAttribute(CPSConstants.LOANMASTERLIST, loanBean.getErpLoanTypeMasterList());
				viewName = CPSConstants.ERPLOANPURPOSELIST;
			}
			
			/**
			 * Loan Type creation starts
			 */
			// Loan Type Master
			
			
			/**
			 * Reports End
			 * 
			 */
			mav = new ModelAndView(viewName, CPSConstants.ERPLOAN, loanBean);

			if (!CPSUtils.isNullOrEmpty(loanBean.getResult())) {
				mav.addObject(CPSConstants.MESSAGE,loanBean.getResult());
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}