package com.callippus.web.controller.award;

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
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.award.AwardDetails;
import com.callippus.web.business.award.AwardBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/awardDetails.htm")
@SessionAttributes
public class AwardController {

	private static Log log = LogFactory.getLog(AwardController.class);
	@Autowired
	private AwardBusiness awardDetailsBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.AWARD) AwardDetails awardBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewPage = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.compareStrings(CPSConstants.PAGING, awardBean.getParam())) {
				viewPage = CPSConstants.AWARDLIST;
				mav = new ModelAndView(viewPage, CPSConstants.AWARD, awardBean);
			} else if (CPSUtils.isNullOrEmpty(awardBean.getParam())) {
				awardBean.setParam(CPSConstants.VIEWAWARDDETAILS);
			}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONAWARDLIST))) {
				session.removeAttribute(CPSConstants.JSONAWARDLIST);
			}
			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				awardBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				session.setAttribute("addressType", "admin");
				if (CPSUtils.compareStrings(CPSConstants.VIEW, awardBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("::AwardController --> onSumit --> param=view");
						viewPage = CPSConstants.AWARDDETAILS;
						mav = new ModelAndView(viewPage, CPSConstants.AWARD, awardBean);
						session.setAttribute(CPSConstants.RETURN, CPSConstants.AWARDDETAILS);
						awardBean = awardDetailsBusiness.getAwardHomeDetails(awardBean, request);
						if (!CPSUtils.isNull(session.getAttribute(CPSConstants.VIEWAWARDDETAILS))
								&& CPSUtils.compareStrings(session.getAttribute(CPSConstants.VIEWAWARDDETAILS).toString(), CPSConstants.YES)) {
							awardBean.setMessage(CPSConstants.VIEWAWARDDETAILS);
						}
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, awardBean.getParam())) {
					log.debug("::AwardController --> onSumit --> param=manage");
					viewPage = CPSConstants.AWARDLIST;
					mav = new ModelAndView(viewPage, CPSConstants.AWARD, awardBean);

					awardBean = awardDetailsBusiness.manageAwardDetails(awardBean);

				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, awardBean.getParam())) {
					log.debug("::AwardController --> onSumit --> param=delete");
					viewPage = CPSConstants.AWARDLIST;
					mav = new ModelAndView(viewPage, CPSConstants.AWARD, awardBean);
					awardBean = awardDetailsBusiness.deleteAwardDetails(awardBean);
				}
			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					viewPage = CPSConstants.AWARDDETAILS;
					mav = new ModelAndView(viewPage, CPSConstants.AWARD, awardBean);
					awardBean.setMessage(CPSConstants.CHANGESFIDFIRST);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.VIEWAWARDDETAILS, awardBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("::AwardController --> onSumit --> param=viewAwardDetails");
					viewPage = CPSConstants.VIEWMASTER;
					mav = new ModelAndView(viewPage, CPSConstants.AWARD, awardBean);
					awardBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					awardBean = awardDetailsBusiness.getAwardHomeDetails(awardBean, request);
					session.setAttribute(CPSConstants.VIEWAWARDDETAILS, CPSConstants.YES);
					awardBean.setMessage(CPSConstants.VIEWAWARDDETAILS);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}

			if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.YEARLIST)) && CPSUtils.checkList(awardDetailsBusiness.getYearList())) {
				// mav.addObject(CPSConstants.YEARLIST, awardDetailsBusiness.getYearList());
				session.setAttribute(CPSConstants.YEARLIST, awardDetailsBusiness.getYearList());
			}
			if (CPSUtils.checkList(awardBean.getAwardList())) {
				session.setAttribute(CPSConstants.JSONAWARDLIST, (JSONArray) JSONSerializer.toJSON(awardBean.getAwardList()));
			}
			if (CPSUtils.checkList(awardDetailsBusiness.getAwardCategoryList())) {
				// mav.addObject(CPSConstants.AWARDCATEGORYLIST, awardDetailsBusiness.getAwardCategoryList());
				session.setAttribute(CPSConstants.AWARDCATEGORYLIST, awardDetailsBusiness.getAwardCategoryList());
			}

			mav.addObject(CPSConstants.MESSAGE, awardBean.getMessage());
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}