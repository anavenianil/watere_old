package com.callippuss.web.retriment.controller;

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

import com.callippus.web.business.master.MasterDataBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.retriment.business.RetrimentBusiness;
import com.callippus.web.retriments.beans.RetrimentBean;

@Controller
@RequestMapping("/retriment.htm")
@SessionAttributes
public class RetrimentController {
	private static Log log = LogFactory.getLog(RetrimentController.class);

	@Autowired
	private MasterDataBusiness masterDataBusiness;

	@Autowired
	private RetrimentBusiness retrimentBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute RetrimentBean retrimentBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		String message = "";
		try {
			session = request.getSession(true);
			retrimentBean.setSfId(retrimentBean.getSfID());
			retrimentBean.setSfID(session.getAttribute(CPSConstants.SFID)
					.toString());
			if (CPSUtils.compareStrings(CPSConstants.RETRIMENTHOME,
					retrimentBean.getParam())) {
				session.setAttribute("retrimentBenfitList",
						retrimentBusiness.getRetrimentDetails(retrimentBean));

				if (CPSUtils.compareStrings("hiddendata",
						retrimentBean.getParam1())) {
					session.setAttribute("employeeName", "");
					session.setAttribute("employeeID", "");
				}

				viewName = CPSConstants.RETRIMENTHOMEPAGE;
			} else if (CPSUtils.compareStrings("changeEmployeeOne",
					retrimentBean.getParam())) {
				session.setAttribute("employeeName", retrimentBusiness
						.changeEmpDetails(retrimentBean.getChangeSfid()));
				session.setAttribute("employeeID",
						retrimentBean.getChangeSfid());
				viewName = CPSConstants.RETRIMENTHOMEPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.RETRIMENTSAVE,
					retrimentBean.getParam())) {
				retrimentBean = retrimentBusiness.saveDetails(retrimentBean);
				session.setAttribute("retrimentBenfitList",
						retrimentBusiness.getRetrimentDetails(retrimentBean));
				session.setAttribute("employeeName", "");
				session.setAttribute("employeeID", "");
				viewName = CPSConstants.RETRIMENTHOMEPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.RETRIMENTDELETE,
					retrimentBean.getParam())) {
				retrimentBusiness.deleteRetrimentDetails(retrimentBean);
				session.setAttribute("retrimentBenfitList",
						retrimentBusiness.getRetrimentDetails(retrimentBean));
				viewName = CPSConstants.RETRIMENTHOMEPAGE;
			} else if (CPSUtils.compareStrings(
					CPSConstants.RETRIMENTAMTISSUEHOME,
					retrimentBean.getParam())) {
				session.setAttribute("retrimentBenfitAmtIssueList",
						retrimentBusiness.getRetrimentDetails(retrimentBean));
				viewName = CPSConstants.RETRIMENTAMOUNTISSUEHOME;
			} else if (CPSUtils.compareStrings(CPSConstants.RETRIMENTAMTHOME,
					retrimentBean.getParam())) {

				retrimentBean = retrimentBusiness
						.getBankNameDetails(retrimentBean);
				session.setAttribute("getretriedEmpDetails",
						retrimentBusiness.getretriedEmpDetails(retrimentBean));
				System.out
						.println(session.getAttribute("getretriedEmpDetails"));
				viewName = CPSConstants.RETRIEDEMPDETAILS;

			} else if (CPSUtils
					.compareStrings(CPSConstants.RETRIMENTPAYMENTSAVE,
							retrimentBean.getParam())) {

				System.out.println("save payment details");
				retrimentBean = retrimentBusiness
						.savePaymentDetails(retrimentBean);
				session.setAttribute("retrimentBenfitAmtIssueList",
						retrimentBusiness.getRetrimentDetails(retrimentBean));
				viewName = CPSConstants.RETRIMENTAMOUNTISSUEHOME;
			}

			mav = new ModelAndView(viewName, CPSConstants.RETRIMENT,
					retrimentBean);

			if (!CPSUtils.isNullOrEmpty(retrimentBean.getResult())) {
				mav.addObject(CPSConstants.RESULT, retrimentBean.getResult());
			} else if (!CPSUtils.isNullOrEmpty(retrimentBean.getMessage())) {
				mav.addObject(CPSConstants.RESULT, retrimentBean.getMessage());

			}

		} catch (Exception e) {
			throw e;
		}

		return mav;

	}

}
