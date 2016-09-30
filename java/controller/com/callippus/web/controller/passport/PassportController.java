package com.callippus.web.controller.passport;

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

import com.callippus.web.beans.passport.PassportBean;
import com.callippus.web.business.passport.PassportBusiness;
import com.callippus.web.business.requestprocess.PassportRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/passport.htm")
@SessionAttributes
public class PassportController {
	private static Log log = LogFactory.getLog(PassportController.class);
	@Autowired
	private PassportBusiness passportBusiness;
    @Autowired
    private PassportRequestProcess passportRequestProcess;
    
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.PASSPORT) PassportBean passportBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String message="";
		String viewName="";
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			
			/*
			//Movable Property Starts
			
			if (CPSUtils.compareStrings("movablePropertyHome", passportBean.getParam())) {
				log.debug("PassportController --> onSubmit --> param=nocPassportHome");
				passportBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				mav = new ModelAndView("movableProperty", CPSConstants.PASSPORT, passportBean);
			}
			
			else if(CPSUtils.compareStrings("",passportBean.getParam())){
				log.debug("PassportController --> onSubmit --> param=");
				message=passportBusiness.savePassportApplicationDetails(passportBean);
				if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)){
					session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, passportBusiness.getPassportApplicationDetails(passportBean));
				}
				mav = new ModelAndView(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, CPSConstants.PASSPORT, passportBean);
				mav.addObject(CPSConstants.MESSAGE, message);
			}
			
			//Movable property ends
			*/
			
			//NOC for PASSPORT STARTS
			
			if (CPSUtils.compareStrings(CPSConstants.NOCPASSPORTHOME, passportBean.getParam())) {
				log.debug("PassportController --> onSubmit --> param=nocPassportHome");
				session.removeAttribute("familyMembersJson");
				session.removeAttribute("passPortTypeJson");
				session.setAttribute("sfIDsForVerified", passportBusiness.getSFIDForVerifiedType());
	//session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, passportBusiness.getPassportApplicationDetails());
				session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, passportBusiness.getSFIDHQrsDetails());
				session.setAttribute(CPSConstants.NOCFORPASSPORTLIST, passportBusiness.getPassportApplicationDetailsForNOC());
				mav = new ModelAndView(CPSConstants.NOCFORPASSPORT, CPSConstants.PASSPORT, passportBean);
			}
			else if(CPSUtils.compareStrings("submitPassportDetailsforNOC",passportBean.getParam())){
				log.debug("PassportController --> onSubmit --> param=submitPassportDetailsforNOC");
				message=passportBusiness.submitPassportDetailsforNOC(passportBean);
				if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)){
					session.setAttribute(CPSConstants.NOCFORPASSPORTLIST, passportBusiness.getPassportApplicationDetailsForNOC());
				}
				mav = new ModelAndView(CPSConstants.NOCFORPASSPORTLIST, CPSConstants.PASSPORT, passportBean);
				mav.addObject(CPSConstants.MESSAGE, message);
			}
			else if(CPSUtils.compareStrings("deletePassportDetailsforNOC",passportBean.getParam())){
				log.debug("PassportController --> onSubmit --> param=deletePassportDetailsforNOC");
				message=passportBusiness.deletePassportApplicationDetails(passportBean);
				if(CPSUtils.compareStrings(CPSConstants.DELETE, message)){
					session.setAttribute(CPSConstants.NOCFORPASSPORTLIST, passportBusiness.getPassportApplicationDetailsForNOC());
				}
					mav = new ModelAndView(CPSConstants.NOCFORPASSPORTLIST, CPSConstants.PASSPORT, passportBean);
					mav.addObject(CPSConstants.MESSAGE, message);
				
			}
			
			//NOC for PASSPORT Ends
			else if (CPSUtils.compareStrings(CPSConstants.PASSPORTHOME, passportBean.getParam())) {
				log.debug("PassportController --> onSubmit --> param=passportHome");
				session.removeAttribute("familyMembersJson");
				session.removeAttribute("passPortTypeJson");
				//session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, (JSONArray) JSONSerializer.toJSON(passportBusiness.getPassportApplicationDetails()));
				//TEST
				
				passportBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				session.removeAttribute("familyMembersJson");
				session.removeAttribute("passPortTypeJson");
				session.removeAttribute("nocPassportResult");
				passportBean = passportBusiness.getPassportProceedingDetails(passportBean);
				session.setAttribute("familyMembersJson", (JSONArray) JSONSerializer.toJSON(passportBean.getFamilyMemberDetails()));
				session.setAttribute("passPortTypeJson", (JSONArray) JSONSerializer.toJSON(passportBean.getProceedingList()));
				
				
				//TEST ENDS 
				session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, passportBusiness.getPassportApplicationDetails(passportBean));
				mav = new ModelAndView(CPSConstants.PASSPORTANDPROCEEDINGDETAILS, CPSConstants.PASSPORT, passportBean);
			}
			else if (CPSUtils.compareStrings(CPSConstants.SFIDDETAILS, passportBean.getParam())) {
				log.debug("PassportController --> onSubmit --> param=sfidDetails");
				session.removeAttribute("familyMembersJson");
				session.removeAttribute("passPortTypeJson");
				session.removeAttribute("nocPassportResult");
				passportBean = passportBusiness.getPassportProceedingDetails(passportBean);
				if(CPSUtils.isNullOrEmpty(passportBean.getMessage())){
					session.setAttribute("familyMembersJson", (JSONArray) JSONSerializer.toJSON(passportBean.getFamilyMemberDetails()));
					session.setAttribute("passPortTypeJson", (JSONArray) JSONSerializer.toJSON(passportBean.getProceedingList()));
				}
				viewName="passportResult";
				if(CPSUtils.compareStrings(CPSConstants.SFIDDETAILS, passportBean.getType())){
					if(CPSUtils.isNullOrEmpty(passportBean.getMessage()))
					    session.setAttribute("nocPassportResult", (JSONArray) JSONSerializer.toJSON(passportBusiness.getPassportApplicationDetails(passportBean)));
					//session.setAttribute("nocPassportResult", passportBusiness.getPassportApplicationDetails(passportBean));
					viewName="nocPassportResult";
				}
				mav = new ModelAndView(viewName, CPSConstants.PASSPORT, passportBean);
				if (!CPSUtils.isNullOrEmpty(passportBean.getMessage())) {
					mav.addObject(CPSConstants.MESSAGE, passportBean.getMessage());
				}
				
			}
			/*else if(CPSUtils.compareStrings(CPSConstants.MANAGEPASSPORTREQUESTDETAILS,passportBean.getParam())){
				log.debug("PassportController --> onSubmit --> param=managePassportRequestDetails");
				passportBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				passportBean.setIpAddress(request.getRemoteAddr());
				PassportRequestProcessBean passportRequestProcessBean=new PassportRequestProcessBean();
				BeanUtils.copyProperties(passportRequestProcessBean,passportBean);
				passportBean.setResult(passportRequestProcess.initWorkflow(passportRequestProcessBean));
				//passportBean.setResult(passportBusiness.submitPassportRequest(passportBean));
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.PASSPORT, passportBean);
			}*/
			else if(CPSUtils.compareStrings(CPSConstants.SAVE,passportBean.getParam())){
				log.debug("PassportController --> onSubmit --> param=save");
				message=passportBusiness.savePassportApplicationDetails(passportBean);
				if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)){
					session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, passportBusiness.getPassportApplicationDetails(passportBean));
				}
				mav = new ModelAndView(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, CPSConstants.PASSPORT, passportBean);
				mav.addObject(CPSConstants.MESSAGE, message);
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETE,passportBean.getParam())){
				log.debug("PassportController --> onSubmit --> param=delete");
				message=passportBusiness.deletePassportApplicationDetails(passportBean);
				if(CPSUtils.compareStrings(CPSConstants.DELETE, message)){ // list page is worgly assigned so corrected
					passportBean = passportBusiness.getPassportHomeDetails(passportBean, session);
					session.setAttribute(CPSConstants.JSONPASSPORTLIST, (JSONArray) JSONSerializer.toJSON(passportBean.getPassportList()));
					//session.setAttribute(CPSConstants.JSONPASSPORTLIST, (JSONArray) JSONSerializer.toJSON(passportBusiness.getPassportHomeDetails(passportBean, session)));
				}
					mav = new ModelAndView("PassportList", CPSConstants.PASSPORT, passportBean);
					mav.addObject(CPSConstants.MESSAGE, message);
				
			}
			else if(CPSUtils.compareStrings("updatePassportApplication",passportBean.getParam())){
				log.debug("PassportController --> onSubmit --> param=updatePassportApplication");
					message=passportBusiness.updatePassportApplicationDetails(passportBean);
					if(CPSUtils.compareStrings(CPSConstants.UPDATE, message)){
			//session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, passportBusiness.getPassportApplicationDetails());
						session.setAttribute(CPSConstants.PASSPORTPROCEEDINGDETAILSLIST, passportBusiness.getSFIDHQrsDetails());
					}
					mav = new ModelAndView("passportAndAbroadList", CPSConstants.PASSPORT, passportBean);
					mav.addObject(CPSConstants.MESSAGE, message);
			}
			//Passport and Proceeding Details Ends
			
			else{
				
				if (CPSUtils.isNullOrEmpty(passportBean.getParam())) {
					passportBean.setParam(CPSConstants.VIEWPASSPORT);
				} else if (CPSUtils.compareStrings(CPSConstants.PAGING, passportBean.getParam())) {
					mav = new ModelAndView(CPSConstants.PASSPORTLIST, CPSConstants.PASSPORT, passportBean);
				}
	
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONPASSPORTLIST))) {
					session.removeAttribute(CPSConstants.JSONPASSPORTLIST);
				}
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
					passportBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
					passportBean.setSfID(session.getAttribute(CPSConstants.CHANGESFID).toString());
					if (CPSUtils.compareStrings(CPSConstants.VIEW, passportBean.getParam())) {
						if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
							log.debug("PassportController --> onSubmit --> param=view");
							passportBean = passportBusiness.getPassportHomeDetails(passportBean, session);
							session.setAttribute(CPSConstants.RETURN, CPSConstants.PASSPORTDETAILS);
							mav = new ModelAndView(CPSConstants.PASSPORTDETAILS, CPSConstants.PASSPORT, passportBean);
						} else {
							session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
							mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
						}
					} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, passportBean.getParam())) {
						log.debug("PassportController --> onSubmit --> param=manage");
						passportBean = passportBusiness.managePassportDetails(passportBean, session);
						mav = new ModelAndView(CPSConstants.PASSPORTLIST, CPSConstants.PASSPORT, passportBean);
					} else if (CPSUtils.compareStrings(CPSConstants.DELETE, passportBean.getParam())) {
						mav = new ModelAndView(CPSConstants.PASSPORTLIST, CPSConstants.PASSPORT, passportBean);
						log.debug("PassportController --> onSubmit --> param=delete");
						passportBean = passportBusiness.deletePassport(passportBean, session);
					}
				} else {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						passportBean.setMessage(CPSConstants.CHANGESFIDFIRST);
						mav = new ModelAndView(CPSConstants.PASSPORTDETAILS, CPSConstants.PASSPORT, passportBean);
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				}
				if (CPSUtils.checkList(passportBean.getPassportList())) {
					session.setAttribute(CPSConstants.JSONPASSPORTLIST, (JSONArray) JSONSerializer.toJSON(passportBean.getPassportList()));
				}
				if (CPSUtils.compareStrings(CPSConstants.VIEWPASSPORT, passportBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("PassportController --> onSubmit --> param=viewPassport");
						passportBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
						passportBean = passportBusiness.getPassportHomeDetails(passportBean, session);
						mav = new ModelAndView(CPSConstants.VIEWMASTER, CPSConstants.PASSPORT, passportBean);
						mav.addObject(CPSConstants.MESSAGE, CPSConstants.VIEWPASSPORTDETAILS);
						mav.addObject(CPSConstants.FAMILYLIST, passportBusiness.getFamilyMembersList(session.getAttribute(CPSConstants.SFID).toString()));
						session.setAttribute(CPSConstants.JSONPASSPORTLIST, (JSONArray) JSONSerializer.toJSON(passportBean.getPassportList()));
						session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				}
				if (!CPSUtils.isNullOrEmpty(passportBean.getMessage())) {
					mav.addObject(CPSConstants.MESSAGE, passportBean.getMessage());
				}
				//Passport and Proceeding Details starts
				
				session.setAttribute(CPSConstants.FAMILYLIST, passportBusiness.getFamilyMembersList(passportBean.getSfid()));
				if (!CPSUtils.isNullOrEmpty(passportBean.getResult())) {
					mav.addObject(CPSConstants.MESSAGE, passportBean.getResult());
				}
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}