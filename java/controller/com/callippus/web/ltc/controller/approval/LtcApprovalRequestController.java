package com.callippus.web.ltc.controller.approval;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanUtils;
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

import com.callippus.web.business.requestprocess.LtcRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.business.approval.LtcApprovalRequestBusiness;
import com.callippus.web.ltc.dto.LtcRequestProcessBean;

@Controller
@RequestMapping("/" + CPSConstants.LTCAPPROVALREQUEST)
@SessionAttributes
public class LtcApprovalRequestController {
	private static Log log = LogFactory.getLog(LtcApprovalRequestController.class);
	@Autowired
	private LtcApprovalRequestBusiness ltcApprovalRequestBusiness;
	@Autowired
	private LtcRequestProcess ltcRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LTCAPPROVALREQUEST) LtcApplicationBean ltcBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(ltcBean.getParam())) {
				ltcBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(ltcBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			ltcBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.compareStrings(CPSConstants.HOME, ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=home");
				viewName = CPSConstants.LTCAPPROVALREQUESTJSP;
				ltcBean = ltcApprovalRequestBusiness.getLTCDetails(ltcBean);
							//added by Narayana
				if(ltcBean.getResult() !=null && ltcBean.getResult().equalsIgnoreCase("LTCHOMETOWNNULL")){
					session.setAttribute("PaymentDetailsNotFound", ltcBean.getResult());
					//viewName = CPSConstants.RESULT;
					viewName = CPSConstants.LTCAPPROVALREQUESTJSP;
				}else{
					if(!CPSUtils.compareStrings("BASIC PAY GRADE PAY DETAILS NOT ENTER", ltcBean.getResult())){
						
						//session.setAttribute("PaymentDetailsNotFound", ltcBean.getResult());
						
					session.setAttribute("ltcTypeDetailsJson", (JSONArray) JSONSerializer.toJSON(ltcBean.getLtcTypeDetails()));
					session.setAttribute(CPSConstants.EMPLOYEEDETAILS, ltcBean.getEmpBean());
					session.setAttribute(CPSConstants.LTCFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(ltcBean.getFamilyMemberDetails()));
					}
					else{
						session.setAttribute("PaymentDetailsNotFound", ltcBean.getResult());
						viewName = CPSConstants.LTCAPPROVALREQUESTJSP;
					}}
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=submitRequest");
				viewName = CPSConstants.REQUESTRESULTPAGE;
				LtcRequestProcessBean ltcRequestBean = new LtcRequestProcessBean();
				BeanUtils.copyProperties(ltcRequestBean, ltcBean);
				ltcRequestBean.setSfID((String) session.getAttribute(CPSConstants.SFID));
				ltcRequestBean.setIpAddress(request.getRemoteAddr());
				ltcRequestBean.setSession(request.getSession(true));
				if (CPSUtils.compareStrings(CPSConstants.LTCREFUNDTYPE, ltcBean.getType()) || CPSUtils.compareStrings(CPSConstants.REIMBURSEMENT, ltcBean.getType())
						|| CPSUtils.compareStrings(CPSConstants.SETTLEMENT, ltcBean.getType())||CPSUtils.compareStrings(CPSConstants.ADVANCE, ltcBean.getType()) ||CPSUtils.compareStrings(CPSConstants.LTCCANCLE, ltcBean.getType()) || CPSUtils.compareStrings(CPSConstants.LTCAPPRCUMADVCANCLE, ltcBean.getType())) {
					ltcBean.setResult(ltcRequestProcess.initWorkflow(ltcRequestBean));
					ltcBean.setRequestID(ltcRequestBean.getRequestID());
					session.setAttribute("reimbursRequestID", ltcBean.getRequestID());
				} else {
					ltcRequestBean = ltcApprovalRequestBusiness.ltcRulesChecking(ltcRequestBean);
					if (CPSUtils.compareStrings(ltcRequestBean.getResult(), CPSConstants.SUCCESS)) {
						ltcBean.setResult(ltcRequestProcess.initWorkflow(ltcRequestBean));
						//
						ltcBean.setRequestID(ltcRequestBean.getRequestID());
						session.setAttribute("LtcApprovalRequest", ltcBean.getRequestID());
								//System.out.println("rakesh");
					} else {
						ltcBean.setRemarks(ltcRequestBean.getRemarks());
						ltcBean.setResult(ltcRequestBean.getResult());
					}
				}
			} else if (CPSUtils.compareStrings(CPSConstants.LTCAPPROVALHOME, ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=ltcapprovalhome");
				ltcBean.setLtcApproveDetailsList(ltcApprovalRequestBusiness.getLtcApprovedDetails(session.getAttribute(CPSConstants.SFID).toString()));
				session.setAttribute("ltcApprovalRequest.ltcApproveDetailsList", ltcBean.getLtcApproveDetailsList());
				session.setAttribute("ltcApproveDetailsList", ltcBean.getLtcApproveDetailsList());
				session.setAttribute(CPSConstants.RETURN,  CPSConstants.LTCAPPROVALDETAILS);
				viewName = CPSConstants.LTCAPPROVALDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.LTCREIMBURSEMENTHOME, ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=ltcReimbursementHome");
				String type = ltcBean.getType();
				ltcBean = ltcApprovalRequestBusiness.getLtcReimbursementDetails(ltcBean);
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, ltcBean.getEmpBean());
				ltcBean.setType(type);
				viewName = CPSConstants.LTCREIMBURSEMENTDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.LTCREFUNDHOME, ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=LTCRefundHome");
				ltcBean = ltcApprovalRequestBusiness.getLtcRefundDetails(ltcBean);
				viewName = CPSConstants.LTCREFUND;
			}else if (CPSUtils.compareStrings(CPSConstants.ADVANCEHOME, ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=advanceHome");
				ltcBean = ltcApprovalRequestBusiness.getLtcApprovalDetails(ltcBean);
				ltcBean.setType(CPSConstants.ADVANCE);
				session.setAttribute(CPSConstants.LTCFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(ltcBean.getFamilyMemberDetails()));
				viewName = CPSConstants.LTCAPPROVALREQUESTJSP;
			}else if (CPSUtils.compareStrings(CPSConstants.FINANCEHOME, ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=financeHome");
				ltcBean = ltcApprovalRequestBusiness.tourSettlementList(ltcBean);
				session.setAttribute(CPSConstants.ADVANCELIST,ltcBean.getAdvanceList());
				session.setAttribute(CPSConstants.SETTLEMENTLIST,ltcBean.getSettlementList());
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,ltcBean.getReimbursementList());
				session.setAttribute(CPSConstants.ACCOUNTOFFICERLIST,ltcBean.getAccountOfficerList());
				session.setAttribute(CPSConstants.CFAOFFICERLIST,ltcBean.getCfaOfficerList());
				session.setAttribute(CPSConstants.ENCASHMENTLIST,ltcBean.getEncashmentList());
				viewName = "LtcFinanceSettlement";
			}			
			else if(CPSUtils.compareString(ltcBean.getParam(), "ltcencashmentdays")){				
				ltcBean= ltcApprovalRequestBusiness.getLtcencashmentDays(ltcBean);
				session.setAttribute("ltcEncashmentdaysList", ltcBean.getLtcEncashmentList());	
				viewName ="LtcEncashmentDaysDetails";
			}
			
			else if (CPSUtils.compareStrings(CPSConstants.MANAGE, ltcBean.getParam())) {

				ltcBean = ltcApprovalRequestBusiness.saveCdaAmount(ltcBean);
				if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ADVANCE)){
					viewName = CPSConstants.LTCADVANCELIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.SETTLEMENT)){
					viewName = CPSConstants.LTCSETTLEMENTLIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.REIMBURSEMENT)){
					viewName = CPSConstants.LTCREIMBURSEMENTLIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ENCASHMENT)){
					viewName = CPSConstants.LTCENCASHMENTLIST;
				}
				session.setAttribute(CPSConstants.ADVANCELIST,ltcBean.getAdvanceList());
				session.setAttribute(CPSConstants.SETTLEMENTLIST,ltcBean.getSettlementList());
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,ltcBean.getReimbursementList());
				session.setAttribute(CPSConstants.ENCASHMENTLIST,ltcBean.getEncashmentList());
			}
			/*else if (CPSUtils.compareStrings(CPSConstants.LTCCDASAVE, ltcBean.getParam())) {

				ltcBean = ltcApprovalRequestBusiness.saveCdaAmount(ltcBean);
				if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ADVANCE)){
					viewName = CPSConstants.LTCADVANCELIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.SETTLEMENT)){
					viewName = CPSConstants.LTCSETTLEMENTLIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.REIMBURSEMENT)){
					viewName = CPSConstants.LTCREIMBURSEMENTLIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ENCASHMENT)){
					viewName = CPSConstants.LTCENCASHMENTLIST;
				}
				session.setAttribute(CPSConstants.ADVANCELIST,ltcBean.getAdvanceList());
				session.setAttribute(CPSConstants.SETTLEMENTLIST,ltcBean.getSettlementList());
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,ltcBean.getReimbursementList());
				session.setAttribute(CPSConstants.ENCASHMENTLIST,ltcBean.getEncashmentList());
			}
			*/
			
			else if (CPSUtils.compareStrings(CPSConstants.AMENDMENT, ltcBean.getParam())) {
				ltcBean = ltcApprovalRequestBusiness.amendmentDetails(ltcBean.getId(),ltcBean.getType(),ltcBean.getLtcAmendmentBlockYear(),ltcBean);
				ltcBean = ltcApprovalRequestBusiness.getLeaveDetails(ltcBean);
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, ltcBean.getEmpBean());
				session.setAttribute(CPSConstants.LTCFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(ltcBean.getFamilyMemberDetails()));
				request.setAttribute("LtcApplicationBean", (JSONObject) JSONSerializer.toJSON(ltcBean));
				viewName = CPSConstants.LTCAPPROVALREQUESTJSP;
			}
			else if (CPSUtils.compareStrings("leaves", ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> Ajax Call --> param=leaves");
				ltcBean.setSfID((String)session.getAttribute(CPSConstants.SFID));
				ltcBean.setLtcLeaveTypeList(ltcApprovalRequestBusiness.getLtcLeaveTypeList(ltcBean.getSfID(),ltcBean.getDepartureDate(),ltcBean.getReturnDate()));
				session.setAttribute(CPSConstants.LTCLEAVETYPELIST, (JSONArray) JSONSerializer.toJSON(ltcBean.getLtcLeaveTypeList()));
				viewName = "AppliedLeaveList";
			}
			// LTC Admin Entry Starts
			else if (CPSUtils.compareStrings("getFamilyMembersList", ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=getFamilyMembersList");
				ltcBean = ltcApprovalRequestBusiness.retrieveFamilyMembersList(ltcBean);
				if (CPSUtils.compareStrings(ltcBean.getResult(), CPSConstants.SUCCESS)) {
					ltcBean.setResult(CPSConstants.EMPEXITS);
					session.setAttribute("familyMembersJson", (JSONArray) JSONSerializer.toJSON(ltcBean.getFamilyMemberDetails()));
					session.setAttribute("blockYearListJson", (JSONArray) JSONSerializer.toJSON(ltcBean.getLtcBlockDetails()));
				} else if (CPSUtils.compareStrings(ltcBean.getResult(), CPSConstants.INVALID)) {
					ltcBean.setResult(CPSConstants.EMPNOTEXISTS);
				}
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings("adminEntryHome", ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=adminEntryHome");
				ltcBean = ltcApprovalRequestBusiness.getLtcAdminEntryHome(ltcBean);
				session.setAttribute(CPSConstants.ADMINENTRYLIST, ltcApprovalRequestBusiness.getltcAdminEntryList(ltcBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ADMINENTRYLISTPAGE);
				viewName = CPSConstants.ADMINENTRYHOME;
			} else if (CPSUtils.compareStrings("manageAdminEntry", ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=manageAdminEntry");
				ltcBean.setIpAddress(request.getRemoteAddr());
				ltcBean.setResult(ltcApprovalRequestBusiness.manageAdminEntryDetails(ltcBean));
				session.setAttribute(CPSConstants.ADMINENTRYLIST, ltcApprovalRequestBusiness.getltcAdminEntryList(ltcBean));
				viewName=CPSConstants.ADMINENTRYLISTPAGE;
			}else if (CPSUtils.compareStrings("searchAdminEntryDetails", ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=searchAdminEntryDetails");
				ltcBean.setType("EntireDetails");
				session.setAttribute(CPSConstants.ADMINENTRYLIST, ltcApprovalRequestBusiness.getltcAdminEntryList(ltcBean));
				viewName=CPSConstants.ADMINENTRYLISTPAGE;
			}else if (CPSUtils.compareStrings("leaveStatusCheck", ltcBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=leaveStatusCheck");
				ltcBean = ltcApprovalRequestBusiness.getLeaveDetails(ltcBean);
				viewName=CPSConstants.REQUESTRESULTPAGE;
			}else if(CPSUtils.compareStrings("ltcBlockYearId", ltcBean.getParam())){
				//ltcBean.setLtcBlockYearId(ltcApprovalRequestBusiness.getLtcBlockYearId(ltcBean.getId()));
				
				/*if(!CPSUtils.isNullOrEmpty(ltcBean.getDepartureDate())){
					session.removeAttribute(CPSConstants.LTCFAMILYMEMBERLIST);
					//ltcBean.setFamilyMemberDetails(ltcApprovalRequestBusiness.getFamilyMemberDetailsLtc(ltcBean));
					session.setAttribute(CPSConstants.LTCFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(ltcBean.getFamilyMemberDetails()));}*/
				session.removeAttribute("ltcExtBlockYear");
				session.setAttribute("ltcExtBlockYear", ltcApprovalRequestBusiness.getLtcBlockYearId(ltcBean.getId()));
				viewName = CPSConstants.RESULT;
			}
			//ltccda settlement code start
			else if(CPSUtils.compareStrings("ltcCDASettlements", ltcBean.getParam())){
				log.debug("LtcApprovalRequestController --> onSubmit --> param=ltcCDASettlements");
              ltcBean = ltcApprovalRequestBusiness.cdatourSettlementList(ltcBean);
				
				session.setAttribute(CPSConstants.CDAADVANCELIST,ltcBean.getCdaadvanceList());
				session.setAttribute(CPSConstants.CDASETTLEMENTLIST,ltcBean.getCdasettlementList());
				session.setAttribute(CPSConstants.CDAREIMBURSEMENTLIST,ltcBean.getCdareimbursementList());
				session.setAttribute(CPSConstants.ACCOUNTOFFICERLIST,ltcBean.getAccountOfficerList());
				session.setAttribute(CPSConstants.CFAOFFICERLIST,ltcBean.getCfaOfficerList());
				session.setAttribute(CPSConstants.CDAENCASHMENTLIST,ltcBean.getCdaencashmentList());
				viewName = CPSConstants.LTCCDAFINANCESETTLEMENT;
			}
		
			else if (CPSUtils.compareStrings("ltcCDAamountsettlsave", ltcBean.getParam())) {

				ltcBean = ltcApprovalRequestBusiness.saveCdaAmountsettlemt(ltcBean);
				 //ltcBean = ltcApprovalRequestBusiness.cdatourSettlementList(ltcBean);
				 //session.removeAttribute(CPSConstants.CDAADVANCELIST,ltcBean.getCdaadvanceList());
				
				if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ADVANCE)){
					//session.setAttribute(CPSConstants.CDAADVANCELIST,ltcBean.getCdaadvanceList());
					//session.setAttribute(CPSConstants.RETURN, ltcBean.getCdaadvanceList());
					//viewName = CPSConstants.CDAADVANCELIST;
					viewName = CPSConstants.LTCCDAADVANCESETTLEMENT;
					session.setAttribute(CPSConstants.CDAADVANCELIST,ltcBean.getCdaadvanceList());
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.SETTLEMENT)){
					//session.setAttribute(CPSConstants.RETURN, ltcBean.getCdasettlementList());
					//viewName = CPSConstants.CDASETTLEMENTLIST;
					viewName = CPSConstants.LTCCDASETTLEMENTSETTLEMENT;
					session.setAttribute(CPSConstants.CDASETTLEMENTLIST,ltcBean.getCdasettlementList());
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.REIMBURSEMENT)){
					//session.setAttribute(CPSConstants.RETURN, ltcBean.getCdareimbursementList());
					//viewName = CPSConstants.CDAREIMBURSEMENTLIST;
					viewName = CPSConstants.LTCCDAREIMBURSEMENTSETTLEMENT;
					session.setAttribute(CPSConstants.CDAREIMBURSEMENTLIST,ltcBean.getCdareimbursementList());
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ENCASHMENT)){
					//session.setAttribute(CPSConstants.RETURN, ltcBean.getCdaencashmentList());
					//viewName = CPSConstants.CDAENCASHMENTLIST;
					viewName = CPSConstants.LTCCDAENCASHMENTSETTLEMENT;
					session.setAttribute(CPSConstants.CDAENCASHMENTLIST,ltcBean.getCdaencashmentList());
				}
				
				//mav.addObject(CPSConstants.MESSAGE, ltcBean.getMessage());
				
			
				/*
				 * if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ADVANCE)){
					viewName = CPSConstants.LTCADVANCELIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.SETTLEMENT)){
					viewName = CPSConstants.LTCSETTLEMENTLIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.REIMBURSEMENT)){
					viewName = CPSConstants.LTCREIMBURSEMENTLIST;
				}else if(CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.ENCASHMENT)){
					viewName = CPSConstants.LTCENCASHMENTLIST;
				}
				session.setAttribute(CPSConstants.ADVANCELIST,ltcBean.getAdvanceList());
				session.setAttribute(CPSConstants.SETTLEMENTLIST,ltcBean.getSettlementList());
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,ltcBean.getReimbursementList());
				session.setAttribute(CPSConstants.ENCASHMENTLIST,ltcBean.getEncashmentList());*/
			}
			
			//ltccda settlement code Ends
			
			// LTC Admin Entry Ends
			mav = new ModelAndView(viewName, CPSConstants.LTCAPPROVALREQUEST, ltcBean);
			if(!CPSUtils.isNullOrEmpty(ltcBean.getResult()))
			mav.addObject(CPSConstants.MESSAGE, ltcBean.getResult());
			if(!CPSUtils.isNullOrEmpty(ltcBean.getRemarks()))
			mav.addObject(CPSConstants.REMARKS, ltcBean.getRemarks());
			if(!CPSUtils.isNullOrEmpty(ltcBean.getLtcTypeDetails()))
			mav.addObject(CPSConstants.LTCTYPELIST, ltcBean.getLtcTypeDetails());

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

}
