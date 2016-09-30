package com.callippus.web.controller.retirement;

import java.util.List;

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

import com.callippus.web.beans.retirement.RetirementBean;
import com.callippus.web.business.retirement.RetirementBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/retirementDetails.htm")
@SessionAttributes
public class RetirementController {

	private static Log log = LogFactory.getLog(RetirementController.class);
	@Autowired
	private RetirementBusiness retirementDetailsBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.RETIREMENT) RetirementBean retirementBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null; 
		HttpSession session = null;
		String viewPage = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(retirementBean.getParam())) {
				retirementBean.setParam(CPSConstants.VIEWRETIREMENTDETAILS);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, retirementBean.getParam())) {
				viewPage = CPSConstants.RETIREMENTLIST;
				mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
			}
			 else if (CPSUtils.compareStrings(CPSConstants.PAGING1, retirementBean.getParam())) {
					mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.RETIREMENT, retirementBean);
				}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONRETIREMENTLIST))) {
				session.removeAttribute(CPSConstants.JSONRETIREMENTLIST);
			}
			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				retirementBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				session.setAttribute("addressType", "admin");
				if (CPSUtils.compareStrings(CPSConstants.VIEW, retirementBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("RetirementController --> onSumit --> param=view");
						viewPage = CPSConstants.RETIREMENTDETAILS;
						mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
						session.setAttribute(CPSConstants.RETURN, CPSConstants.RETIREMENTDETAILS);
						retirementBean = retirementDetailsBusiness.getRetirementHomeDetails(retirementBean, session);
						if (!CPSUtils.isNull(session.getAttribute(CPSConstants.VIEWRETIREMENTDETAILS))
								&& CPSUtils.compareStrings(session.getAttribute(CPSConstants.VIEWRETIREMENTDETAILS).toString(), CPSConstants.YES)) {
							retirementBean.setMessage(CPSConstants.VIEWRETIREMENTDETAILS);
						}
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, retirementBean.getParam())) {
					log.debug("RetirementController --> onSumit --> param=manage");
					viewPage = CPSConstants.RETIREMENTLIST;
					mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
					retirementBean = retirementDetailsBusiness.manageRetirementDetails(retirementBean, session);

				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, retirementBean.getParam())) {
					log.debug("RetirementController --> onSumit --> param=delete");
					viewPage = CPSConstants.RETIREMENTLIST;
					mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
					retirementBean = retirementDetailsBusiness.deleteRetirementDetails(retirementBean, session);

				}
			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					viewPage = CPSConstants.RETIREMENTDETAILS;
					mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
					retirementBean.setMessage(CPSConstants.CHANGESFIDFIRST);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.VIEWRETIREMENTDETAILS, retirementBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("RetirementController --> onSumit --> param=viewRetirementDetails");
					retirementBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					retirementBean = retirementDetailsBusiness.getRetirementHomeDetails(retirementBean, session);
					viewPage = CPSConstants.VIEWMASTER;
					mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
					session.setAttribute(CPSConstants.VIEWRETIREMENTDETAILS, CPSConstants.YES);
					retirementBean.setMessage(CPSConstants.VIEWRETIREMENTDETAILS);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}

			}
			if (!CPSUtils.compareStrings(CPSConstants.HOME, retirementBean.getParam())) {
				if (CPSUtils.checkList(retirementBean.getRetirementList())) {
					session.setAttribute(CPSConstants.JSONRETIREMENTLIST, (JSONArray) JSONSerializer.toJSON(retirementBean.getRetirementList()));
				}
				if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.RETIREMENTTYPELIST)) && CPSUtils.checkList(retirementDetailsBusiness.getRetirementTypeList())) {
					session.setAttribute(CPSConstants.RETIREMENTTYPELIST, retirementDetailsBusiness.getRetirementTypeList());
				}
			}
			if (CPSUtils.compareStrings("retirementReportDetails", retirementBean.getParam())) {
				
					log.debug("RetirementController --> onSumit --> param=viewRetirementDetails");
					retirementBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					List yearList = retirementDetailsBusiness.getYearList(retirementBean);
					viewPage = "RetirementReportDetails";
					mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
					
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
					session.setAttribute("yearList", yearList);
			
			}
			if (CPSUtils.compareStrings("getRetirementReportDetailsList", retirementBean.getParam())) {
				
				log.debug("RetirementController --> onSumit --> param=retirementReportDetailsList");
				retirementBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				retirementBean = retirementDetailsBusiness.getRetirementReportDetails(retirementBean);
				viewPage = "RetirementReportDetailsList";
				mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
				session.setAttribute("retirementReportDetailsList",retirementBean.getRetirementList());
				session.setAttribute(CPSConstants.RETURN, viewPage);
		
		}
				if (CPSUtils.compareStrings("RetirementFinalDetails", retirementBean.getParam())) {
				
				log.debug("RetirementController --> onSumit --> param=RetirementFinalDetails");
				retirementBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				viewPage = "RetirementFinalDetails";
				mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
				session.setAttribute(CPSConstants.RETURN, viewPage);
		
				}
				if (CPSUtils.compareStrings("RetirementFinalDetailsList", retirementBean.getParam())) {
					
					log.debug("RetirementController --> onSumit --> param=RetirementFinalDetailsList");
					retirementBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					retirementBean = retirementDetailsBusiness.getRetirementFinalDetails(retirementBean);
					viewPage = "RetirementFinalDetailsList";
					mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
					session.setAttribute("retirementFinalDetailsList",retirementBean.getRetirementList());
					session.setAttribute(CPSConstants.RETURN, viewPage);
			
					}
				if (CPSUtils.compareStrings("RetirementFinalDetail", retirementBean.getParam())) {
					
					log.debug("RetirementController --> onSumit --> param=RetirementFinalDetail");
					retirementBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					retirementBean = retirementDetailsBusiness.getRetirementFinalDetail(retirementBean);
					viewPage = "RetirementFinalDetail";
					mav = new ModelAndView(viewPage, CPSConstants.RETIREMENT, retirementBean);
					session.setAttribute(CPSConstants.RETURN, viewPage);
			
					}
			
				
				
			

			if (!CPSUtils.isNullOrEmpty(retirementBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, retirementBean.getMessage());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	
	
	
	
}