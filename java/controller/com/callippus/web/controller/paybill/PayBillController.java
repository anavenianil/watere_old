package com.callippus.web.controller.paybill;

import java.math.BigDecimal;
import java.sql.Date;

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

import com.callippus.web.beans.paybill.PayBillBean;
import com.callippus.web.business.paybill.PayBillBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/pay.htm")
@SessionAttributes
public class PayBillController {
	private static Log log = LogFactory.getLog(PayBillController.class);
	@Autowired
	private PayBillBusiness payBillBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.PAYBILL) PayBillBean payBillBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		request.getHeaderNames();
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
            if (CPSUtils.isNullOrEmpty(payBillBean.getParam())) {
				payBillBean.setParam(CPSConstants.PAYBILLHOME);
			}
            if (CPSUtils.compareStrings(CPSConstants.PAGING, payBillBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			payBillBean.setSfId(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.compareStrings(CPSConstants.PAYBILLHOME, payBillBean.getParam())) {
				log.debug("PayBillController --> execute --> param=PAYBILLHOME");
				//payBillBean.setSearchSfid(payBillBusiness.getLabid());
				payBillBean.setSearchSfid((String)session.getAttribute("Labid"));
				payBillBean.setEmployeeName("");
				payBillBean.setPaySlipYearList(payBillBusiness.getYearListForEditEmployee());
				viewName = CPSConstants.PAYBILLEMPSEARCH;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYSEARCHSFID, payBillBean.getParam())) {
				payBillBean = payBillBusiness.getEmpPayDetails(payBillBean);
				if (CPSUtils.compareStrings(CPSConstants.AUTORUNNOTCOMPLETED, payBillBean.getMessage()) || CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillBean.getMessage()))
				//		|| CPSUtils.compareStrings(CPSConstants.EMPPAYSTOP, payBillBean.getMessage()))
					viewName = CPSConstants.RESULT;
				else if(CPSUtils.compareStrings(CPSConstants.EMPPAYSTOP, payBillBean.getMessage()))
					viewName = "payStopRemarks";
				else
					viewName = CPSConstants.EMPPAYBILLDETAILS;
			} 
			//for edit employee data recalculate //kumari
			else if (CPSUtils.compareStrings("RecalculatePayValues", payBillBean.getParam())) {
				payBillBean=payBillBusiness.getRecalcultedPayDetails(payBillBean);//with changed values recalculte all values
					viewName = CPSConstants.EMPPAYBILLDETAILS;
			} 
			else if (CPSUtils.compareStrings(CPSConstants.PAYEMPUPDATE, payBillBean.getParam())) {
				payBillBean = payBillBusiness.updateEmpPayBillDetails(payBillBean);
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYBILLSTATUSHOME, payBillBean.getParam())) {
				payBillBean = payBillBusiness.getPayBillStatus(payBillBean);
				viewName = CPSConstants.PAYBILLSTATUS;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYSTARTAUTORUN, payBillBean.getParam())) {
				payBillBean = payBillBusiness.startAutoRun(payBillBean);
				viewName = CPSConstants.PAYBILLSTATUSDETAILS;
			}else if (CPSUtils.compareStrings("regeneration", payBillBean.getParam())) {
				payBillBean = payBillBusiness.startRegeneratingPayBill(payBillBean);
				viewName = CPSConstants.PAYBILLSTATUSDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYUPDATESTATUS, payBillBean.getParam())) {
				payBillBean = payBillBusiness.changeStatusToManual(payBillBean);
				viewName = CPSConstants.PAYBILLSTATUSDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYCOMPLETESTATUS, payBillBean.getParam())) {
				payBillBean = payBillBusiness.changeStatusToVisible(payBillBean);
				viewName = CPSConstants.PAYBILLSTATUSDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.GETNEGPAYEMP, payBillBean.getParam())) {
				payBillBean = payBillBusiness.getNegPayEmployees(payBillBean);
				viewName = CPSConstants.NEGPAYEMPDETAILS;
			}else if (CPSUtils.compareStrings(CPSConstants.DRYRUN, payBillBean.getParam())) {
				payBillBean = payBillBusiness.startDryRun(payBillBean);
				viewName = CPSConstants.PAYBILLSTATUSDETAILS;
			}else if (CPSUtils.compareStrings(CPSConstants.PAYSLIPHOME, payBillBean.getParam())) {
				payBillBean.setPaySlipYearList(payBillBusiness.getPaySlipYearList());
				viewName = CPSConstants.EMPPAYSLIP;
			}else if (CPSUtils.compareStrings("getPaySlipMonthList", payBillBean.getParam())) {
				payBillBean.setPaySlipMonthList(payBillBusiness.getPaySlipMonthList(payBillBean.getPaySlipYear()));
				viewName = "EmpPaySlipMonthList";
			} else if (CPSUtils.compareStrings(CPSConstants.SHOWPAYSLIP, payBillBean.getParam())) {
				payBillBean = payBillBusiness.showPaySlip(payBillBean);
				session.setAttribute("runMonth", payBillBean.getPaySlipMonth()+"-"+payBillBean.getPaySlipYear());
				if (CPSUtils.compareStrings(CPSConstants.YES, payBillBean.getMessage()))
					viewName = CPSConstants.EMPPAYSLIPDETAILS;
				else
					viewName = CPSConstants.RESULT;
			} 
			//new code for paybill supplimentary
			else if(CPSUtils.compareStrings("payEmpSupplimentaryDetails", payBillBean.getParam())){
				payBillBean.setPaySlipYearList(payBillBusiness.getPaySlipYearList());
				viewName="PayBillEmpSupplimentaryDetails";
			}
			else if(CPSUtils.compareStrings("paySupplimentaryEmpList", payBillBean.getParam())){
				payBillBean.setEmpSuppSelectedList(payBillBusiness.getEmpSupplimentaryBillDetails(payBillBean));
				viewName="payBillSupplimentaryEmpList";
			}
			// to get month list of edit employee screen
			else if (CPSUtils.compareStrings("getMonthListForEditEmployee", payBillBean.getParam())) {
				payBillBean.setPaySlipMonthList(payBillBusiness.getMonthListForEditEmployee(payBillBean.getPaySlipYear()));
				viewName = "EmpPaySlipMonthList";
			} 
			mav = new ModelAndView(viewName, CPSConstants.PAYBILL, payBillBean);
			if (!CPSUtils.isNullOrEmpty(payBillBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, payBillBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(payBillBean.getRemarks())) {
				mav.addObject(CPSConstants.REMARKS, payBillBean.getRemarks());
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

}
