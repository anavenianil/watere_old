package com.callippus.web.promotions.controller.request;

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
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.promotions.beans.request.PromotionRequestBean;
import com.callippus.web.promotions.business.management.PromotionManagementBusiness;
import com.callippus.web.promotions.business.request.PromotionRequestBusiness;

@Controller
@RequestMapping("/promotionReq.htm")
@SessionAttributes
public class PromotionRequestController {
	private static Log log = LogFactory.getLog(PromotionRequestController.class);
	@Autowired
	private PromotionRequestBusiness promotionRequestBusiness;
	
	@Autowired
	private PromotionManagementBusiness proManagementBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute PromotionRequestBean promotionBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			
		//	promotionBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(promotionBean.getParam())) {
				promotionBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(promotionBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}

			/**
			 * Optional Certificate starts
			 */
			if (CPSUtils.compareStrings(CPSConstants.HOME, promotionBean.getParam())) {
				promotionBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				
				log.debug("PromotionRequestController --> onSubmit --> param=home");
				promotionBean = promotionRequestBusiness.getOptionalCertificateHome(promotionBean);
				session.setAttribute(CPSConstants.VIEWOPTIONALCERTIFICATELIST, promotionBean.getAssessmentDetails());
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails1());
				session.setAttribute(CPSConstants.BOARDTYPELIST,promotionBean.getAssessmentCategoryList());
				session.setAttribute(CPSConstants.YEARLIST, promotionBean.getYearList());
				session.setAttribute(CPSConstants.OPTIONALCERTIFICATELIST, promotionBean.getOptionalCertificateList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.OPTIONALCERTIFICATELISTPAGE);
				viewName = CPSConstants.OPTIONALCERTIFICATE;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITOPTCERT, promotionBean.getParam())) {
				if(CPSUtils.isNullOrEmpty(promotionBean.getSfID())){
				promotionBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				}log.debug("PromotionRequestController --> onSubmit --> param=submitOptCert");
				promotionBean.setIpAddress(request.getRemoteAddr());
				promotionBean = promotionRequestBusiness.submitOptionalCertificate(promotionBean);
				if(promotionBean.getResult().equals(CPSConstants.SUCCESS)){
					session.setAttribute(CPSConstants.OPTIONALCERTIFICATELIST, promotionRequestBusiness.getOptionalCertificateList(promotionBean));
					session.setAttribute(CPSConstants.RETURN, CPSConstants.OPTIONALCERTIFICATELISTPAGE);
				}
				/*	session.setAttribute(CPSConstants.OPTIONALCERTIFICATELIST, promotionRequestBusiness.getOptionalCertificateList(promotionBean)); */
				viewName = CPSConstants.OPTIONALCERTIFICATELISTPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEOPTCERT, promotionBean.getParam())) {
				if(CPSUtils.isNullOrEmpty(promotionBean.getSfID())){
				promotionBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				}log.debug("PromotionRequestController --> onSubmit --> param=deleteOptCert");
				promotionBean = promotionRequestBusiness.deleteOptionalCertificate(promotionBean);
				session.setAttribute(CPSConstants.OPTIONALCERTIFICATELIST, promotionRequestBusiness.getOptionalCertificateList(promotionBean));
				viewName = CPSConstants.OPTIONALCERTIFICATELISTPAGE;
			}else if(CPSUtils.compareStrings(CPSConstants.GETOPTCERT, promotionBean.getParam())) {
				session.setAttribute(CPSConstants.OPTIONALCERTIFICATELIST, promotionRequestBusiness.getOptionalCertificateList(promotionBean));
				viewName = CPSConstants.OPTIONALCERTIFICATELISTPAGE;
			}
			else if(CPSUtils.compareStrings(CPSConstants.GETSFIDLIST, promotionBean.getParam())) {
			    promotionBean =	promotionRequestBusiness.getOptionalCertificatesSfidLIst(promotionBean);
			 
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails1());
				
				viewName = CPSConstants.SFIDLIST;
			}
			/**
			 * Optional Certificate ends
			 */
			

			mav = new ModelAndView(viewName, CPSConstants.PROMOTION, promotionBean);
			mav.addObject(CPSConstants.RESULT, promotionBean.getResult());
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
