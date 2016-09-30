package com.callippus.web.controller.paybill;

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

import com.callippus.web.beans.paybill.PayScaleBean;
import com.callippus.web.business.paybill.PayScaleBussiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/payScale.htm")
@SessionAttributes
public class PayScaleController {
	private static Log log = LogFactory.getLog(PayScaleController.class);
	@Autowired
	private PayScaleBussiness payScaleBussiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.PAYSCALE) PayScaleBean payScaleBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try { 
			ErpAction.userChecks(request);
			session = request.getSession(true);
     		if (CPSUtils.isNullOrEmpty(payScaleBean.getParam())) {
				payScaleBean.setParam(CPSConstants.PAYSCALEHOME);
			} 
			if (CPSUtils.compareStrings(CPSConstants.PAGING, payScaleBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			if (CPSUtils.isNullOrEmpty(payScaleBean.getSfid())) {
				payScaleBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			}

			if (!CPSUtils.isNullOrEmpty(payScaleBean.getType())) {
				session.setAttribute(CPSConstants.MASTERTYPE, payScaleBean.getType());
			}
			payScaleBean.setPayRunMonth(payScaleBussiness.getCurrentRunMonth());
			if (CPSUtils.compareStrings(CPSConstants.PAYSCALEHOME, payScaleBean.getParam())) {
				log.debug("PayScaleController --> onSubmit --> param=payScaleHome");
					if (!CPSUtils.isNullOrEmpty(payScaleBean.getType()))
					payScaleBean.setType(session.getAttribute(CPSConstants.MASTERTYPE).toString());
				payScaleBean = payScaleBussiness.getPayScaleList(payScaleBean);
				session.setAttribute("payScaleList", payScaleBean.getPayScaleList());
				session.setAttribute("jsonPayScaleList", (JSONArray)JSONSerializer.toJSON(session.getAttribute("payScaleList")));
				viewName = CPSConstants.PAYSCALEMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYSCALEMASTERLIST);
			
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, payScaleBean.getParam())) {
				log.debug("PayScaleController  --> onSubmit --> param=manage");
				payScaleBean.setMessage(payScaleBussiness.submitPayScaleDetails(payScaleBean));
				payScaleBean = payScaleBussiness.getPayScaleList(payScaleBean);
				session.setAttribute("payScaleList", payScaleBean.getPayScaleList());
				session.setAttribute("jsonPayScaleList", (JSONArray)JSONSerializer.toJSON(session.getAttribute("payScaleList")));
				viewName = CPSConstants.PAYSCALEMASTERLIST;
			}
			if (CPSUtils.compareStrings(CPSConstants.PAYDESIGNATIONHOME, payScaleBean.getParam())) {
				log.debug("PayScaleController --> onSubmit --> param=payDesignationHome");
				payScaleBean = payScaleBussiness.getDesignationPaybandList(payScaleBean);
				session.setAttribute("designationList",  payScaleBean.getDesignationList());
				session.setAttribute("paybandList",  payScaleBean.getPaybandList());
				session.setAttribute("payDesignationList",  payScaleBean.getPayDesignationList());
				viewName = CPSConstants.PAYSCALEDESIGNATIONMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYSCALEDESIGNATIONMASTERLIST);
				
			} else if (CPSUtils.compareStrings(CPSConstants.PAYDESIGNATIONMANAGE, payScaleBean.getParam())) {
				log.debug("PayScaleController  --> onSubmit --> param=payDesignationManage");
				payScaleBean.setMessage(payScaleBussiness.submitPayScaleDesignationDetails(payScaleBean));
				payScaleBean = payScaleBussiness.getDesignationPaybandList(payScaleBean);
            	session.setAttribute("payDesignationList", payScaleBean.getPayDesignationList());
				viewName = CPSConstants.PAYSCALEDESIGNATIONMASTERLIST;
			}
			
			/**
			 * Start pay band creation
			 * */
			else if(CPSUtils.compareStrings(CPSConstants.PAYBANDMASTERHOME, payScaleBean.getParam()))
			{
				session.setAttribute("payBandListDetails", payScaleBussiness.getPayBandList());
				session.setAttribute("jsonRangeDetails", (JSONArray)JSONSerializer.toJSON(session.getAttribute("payBandListDetails")));
				viewName = CPSConstants.PAYBANDMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYBANDMASTER);
				
			}
			else if(CPSUtils.compareStrings(CPSConstants.INSERTPAYBAND, payScaleBean.getParam()))
			{
				payScaleBean = payScaleBussiness.insertPayBandDetails(payScaleBean);
				session.setAttribute("payBandListDetails", payScaleBean.getPayBandList());
				session.setAttribute("jsonRangeDetails", (JSONArray)JSONSerializer.toJSON(session.getAttribute("payBandListDetails")));
				viewName = CPSConstants.PAYBANDMASTERLIST;
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETEPAYBAND, payScaleBean.getParam()))
			{
				payScaleBean.setMessage(payScaleBussiness.deletePayBandDetails(payScaleBean));
				session.setAttribute("payBandListDetails", payScaleBussiness.getPayBandList());
				session.setAttribute("jsonRangeDetails", (JSONArray)JSONSerializer.toJSON(session.getAttribute("payBandListDetails")));
				viewName = CPSConstants.PAYBANDMASTERLIST;
			}
			else if (CPSUtils.compareStrings(CPSConstants.PAYSCALETAIHOME, payScaleBean.getParam()))
			{
				payScaleBean.setPayDesigList(payScaleBussiness.getPayScaleDesignationList());
				session.setAttribute("jsonPayDesigList", (JSONArray)JSONSerializer.toJSON(payScaleBean.getPayDesigList()));
				session.setAttribute("twoAddIncrList", payScaleBussiness.getTwoAddIncrPayScaleList());
				session.setAttribute("jsonPayDesigIds", (JSONArray)JSONSerializer.toJSON(session.getAttribute("twoAddIncrList")));
				viewName = CPSConstants.TWOADDINCRPAYSCALES;
			}
			else if (CPSUtils.compareStrings(CPSConstants.MANAGEPAYSCALETAI, payScaleBean.getParam())) {
				payScaleBean.setMessage(payScaleBussiness.submitTWAIPayScaleDetails(payScaleBean));
				payScaleBean.setPayDesigList(payScaleBussiness.getPayScaleDesignationList());
				session.setAttribute("jsonPayDesigList", (JSONArray)JSONSerializer.toJSON(payScaleBean.getPayDesigList()));
				session.setAttribute("twoAddIncrList", payScaleBussiness.getTwoAddIncrPayScaleList());
				session.setAttribute("jsonPayDesigIds", (JSONArray)JSONSerializer.toJSON(session.getAttribute("twoAddIncrList")));
				viewName = CPSConstants.TWOADDINCRPAYSCALESLIST;
			}
			else if (CPSUtils.compareStrings(CPSConstants.TAIMASTERDELETE, payScaleBean.getParam())) {
				payScaleBean.setMessage(payScaleBussiness.deleteTAIPayScaleDetails(Integer.parseInt(payScaleBean.getPk()),payScaleBean.getSfid()));
				payScaleBean.setPayDesigList(payScaleBussiness.getPayScaleDesignationList());
				session.setAttribute("jsonPayDesigList", (JSONArray)JSONSerializer.toJSON(payScaleBean.getPayDesigList()));
				session.setAttribute("twoAddIncrList", payScaleBussiness.getTwoAddIncrPayScaleList());
				session.setAttribute("jsonPayDesigIds", (JSONArray)JSONSerializer.toJSON(session.getAttribute("twoAddIncrList")));
				viewName = CPSConstants.TWOADDINCRPAYSCALESLIST;
			}
			else if (CPSUtils.compareStrings("payAllwDetailsHome", payScaleBean.getParam())) {
				payScaleBean.setAllwTypeList(payScaleBussiness.getPayAllwTypeList());
				payScaleBean.setAllwDetailsList(payScaleBussiness.getPayAllwDetailsList());
				session.setAttribute("allwDetailsList", payScaleBean.getAllwDetailsList());
				viewName = "payAllwDetails";
				session.setAttribute(CPSConstants.RETURN, "payAllwDetailsList");
				
			}
			else if (CPSUtils.compareStrings("submitPayAllwDetailsHome", payScaleBean.getParam())) {
				payScaleBean.setMessage(payScaleBussiness.submitPayConfDetails(payScaleBean));
				payScaleBean.setAllwDetailsList(payScaleBussiness.getPayAllwDetailsList());
				session.setAttribute("allwDetailsList", payScaleBean.getAllwDetailsList());
				viewName = "payAllwDetailsList";
			}
			else if (CPSUtils.compareStrings("deletePayAllwDetailsHome", payScaleBean.getParam())) {
				payScaleBean.setMessage(payScaleBussiness.deletePayAllwDetails(Integer.parseInt(payScaleBean.getPk()),payScaleBean.getSfid()));
				payScaleBean.setAllwDetailsList(payScaleBussiness.getPayAllwDetailsList());
				session.setAttribute("allwDetailsList", payScaleBean.getAllwDetailsList());
				viewName = "payAllwDetailsList";
			}
			else if (CPSUtils.compareStrings("payAllwTypeHome", payScaleBean.getParam())) {
				payScaleBean.setAllwTypeList(payScaleBussiness.getPayAllwTypeList());
				session.setAttribute("allwTypeList", payScaleBean.getAllwTypeList());
				viewName = "payAllwTypeHome";
				session.setAttribute(CPSConstants.RETURN, "payAllwTypeList");
			}
			else if (CPSUtils.compareStrings("submitPayAllwTypeHome", payScaleBean.getParam())) {
				payScaleBean.setMessage(payScaleBussiness.submitpayAllwTypeDetails(payScaleBean));
				payScaleBean.setAllwTypeList(payScaleBussiness.getPayAllwTypeList());
				session.setAttribute("allwTypeList", payScaleBean.getAllwTypeList());
				viewName = "payAllwTypeList";
			}else if (CPSUtils.compareStrings("deletePayAllwTypeHome", payScaleBean.getParam())) {
				payScaleBean.setMessage(payScaleBussiness.deletePayAllwType(Integer.parseInt(payScaleBean.getPk()),payScaleBean.getSfid()));
				payScaleBean.setAllwTypeList(payScaleBussiness.getPayAllwTypeList());
				session.setAttribute("allwTypeList", payScaleBean.getAllwTypeList());
				viewName = "payAllwTypeList";
			}
			
			/***
			 * End pay band creation
			 * */
			mav = new ModelAndView(viewName, CPSConstants.PAYSCALE, payScaleBean);
			if (!CPSUtils.isNullOrEmpty(payScaleBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, payScaleBean.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
