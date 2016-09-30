package com.callippus.web.loan.controller.management;

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

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.loan.beans.management.LoanManagementBean;
import com.callippus.web.loan.business.management.LoanManagementBusiness;

@Controller
@RequestMapping("/loan.htm")
@SessionAttributes
public class LoanManagementController {
	private static Log log = LogFactory.getLog(LoanManagementController.class);
	@Autowired
	private LoanManagementBusiness loanManagementBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LOAN) LoanManagementBean loanBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(loanBean.getParam())) {
				loanBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, loanBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}else if (CPSUtils.compareStrings(CPSConstants.PAGING1, loanBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN1).toString();
			}
			loanBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());

			/**
			 * Loan Type creation starts
			 */
			// Loan Type Master
			if (CPSUtils.compareStrings(CPSConstants.HOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=home");
				loanBean = loanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.setAttribute(CPSConstants.LOANMASTERLIST, loanBean.getLoanTypeMasterList());
				viewName = CPSConstants.LOANTYPEMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANTYPEMASTERLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=manage");
				loanBean = loanManagementBusiness.submitLoanTypeMasterDetails(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.setAttribute(CPSConstants.LOANMASTERLIST, loanBean.getLoanTypeMasterList());
				viewName = CPSConstants.LOANTYPEMASTERLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=delete");
				loanBean = loanManagementBusiness.deleteLoanTypeMasterDetails(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.setAttribute(CPSConstants.LOANMASTERLIST, loanBean.getLoanTypeMasterList());
				viewName = CPSConstants.LOANTYPEMASTERLIST;
			}

			// Loan Type Details
			else if (CPSUtils.compareStrings(CPSConstants.LOANTYPEDETAILS, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=loanTypeDetails");
				loanBean = loanManagementBusiness.getLoanTypeHomeDetails(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeGrid(loanBean);
				loanBean = loanManagementBusiness.getLoanFestivalList(loanBean);
				session.setAttribute(CPSConstants.SUBTYPELISTJSON, loanBean.getLoanFestivalList());
				session.setAttribute(CPSConstants.LOANTYPEDETAILSLIST, loanBean.getLoanTypeDetailsList());
				session.setAttribute(CPSConstants.LOANLEAVESMAPPINGJSON, loanBean.getLoanLeavesDetailsList());
				viewName = CPSConstants.LOANTYPEDETAILSPAGE;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANTYPEDETAILSLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGELOANDETAILS, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=manageLoanDetails");
				loanBean = loanManagementBusiness.manageLoanTypeDetails(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeGrid(loanBean);
				session.setAttribute(CPSConstants.LOANTYPEDETAILSLIST, loanBean.getLoanTypeDetailsList());
				session.setAttribute(CPSConstants.LOANLEAVESMAPPINGJSON, loanBean.getLoanLeavesDetailsList());
				viewName = CPSConstants.LOANTYPEDETAILSLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.LOANDETAILSDELETE, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=loanDetailsDelete");
				loanBean = loanManagementBusiness.deleteLoanTypeDetails(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeGrid(loanBean);
				session.setAttribute(CPSConstants.LOANTYPEDETAILSLIST, loanBean.getLoanTypeDetailsList());
				viewName = CPSConstants.LOANTYPEDETAILSLIST;
			}
			/**
			 * Loan Type creation ends
			 */

			/**
			 * Loan Festival Master Start
			 */
			else if (CPSUtils.compareStrings(CPSConstants.FESTIVALHOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=festivalHome");				
				loanBean = loanManagementBusiness.getLoanFestivalList(loanBean);
				session.setAttribute(CPSConstants.LOANFESTIVALLIST, loanBean.getLoanFestivalList());
				viewName = CPSConstants.LOANFESTIVALMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANFESTIVALMASTERLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.FESTIVALMANAGE, loanBean.getParam())) {
				log.debug("LoanManagementController  --> onSubmit --> param=festivalManage");
				loanBean.setResult(loanManagementBusiness.saveLoanFestivalDetails(loanBean));
				loanBean = loanManagementBusiness.getLoanFestivalList(loanBean);
				session.setAttribute(CPSConstants.LOANFESTIVALLIST, loanBean.getLoanFestivalList());
				viewName = CPSConstants.LOANFESTIVALMASTERLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.FESTIVALDELETE, loanBean.getParam())) {
				log.debug("LoanManagementController  --> onSubmit --> param=festivalDelete");
				loanBean.setResult(loanManagementBusiness.deleteLoanFestivalDetails(loanBean));
				loanBean = loanManagementBusiness.getLoanFestivalList(loanBean);
				session.setAttribute(CPSConstants.LOANFESTIVALLIST, loanBean.getLoanFestivalList());
				viewName = CPSConstants.LOANFESTIVALMASTERLIST;
			}
			/**
			 * Loan Festival Master end
			 * 
			 */

			/**
			 * GPF SubType Master Start
			 */
			else if (CPSUtils.compareStrings(CPSConstants.GPFRULESHOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=gpfRulesHome");
				loanBean = loanManagementBusiness.getGpfRulesHome(loanBean);
				session.setAttribute("gpfRulesList", loanBean.getGpfRulesList());
				viewName = CPSConstants.GPFSUBTYPEMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.GPFSUBTYPEMASTERLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEGPFSUBTYPE, loanBean.getParam())) {
				log.debug("LoanManagementController  --> onSubmit --> param=manageGpfSubType");
				loanBean.setResult(loanManagementBusiness.submitGPFRulesDetails(loanBean));
				loanBean = loanManagementBusiness.getGpfRulesHome(loanBean);
				session.setAttribute("gpfRulesList", loanBean.getGpfRulesList());
				viewName = CPSConstants.GPFSUBTYPEMASTERLIST;
			}
			/**
			 * GPF SubType Master end
			 */

			/**
			 * GPF Closing Balance Start
			 * 
			 */
			if (CPSUtils.compareStrings(CPSConstants.GPFBALANCEHOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=gpfBalanceHome");
				loanBean = loanManagementBusiness.getGpfBalanceList(loanBean);
				session.setAttribute(CPSConstants.GPFBALANCELIST, loanBean.getGpfBalanceList());
				viewName = CPSConstants.GPFCLOSINGBALANCE;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.GPFCLOSINGBALANCELIST);
			} else if (CPSUtils.compareStrings(CPSConstants.GPFBALANCEMANAGE, loanBean.getParam())) {
				log.debug("LoanManagementController  --> onSubmit --> param=gpfBalanceManage");
				loanBean.setResult(loanManagementBusiness.submitGpfClosingBalanceDetails(loanBean));
				loanBean = loanManagementBusiness.getGpfBalanceList(loanBean);
				session.setAttribute(CPSConstants.GPFBALANCELIST, loanBean.getGpfBalanceList());
				viewName = CPSConstants.GPFCLOSINGBALANCELIST;
			} else if (CPSUtils.compareStrings(CPSConstants.GPFBALANCEDELETE, loanBean.getParam())) {
				log.debug("LoanManagementController  --> onSubmit --> param=gpfBalanceDelete");
				loanBean.setResult(loanManagementBusiness.deleteGpfClosingBalanceDetails(loanBean));
				loanBean = loanManagementBusiness.getGpfBalanceList(loanBean);
				session.setAttribute(CPSConstants.GPFBALANCELIST, loanBean.getGpfBalanceList());
				viewName = CPSConstants.GPFCLOSINGBALANCELIST;
			}

			/**
			 * GPF Closing Balance end
			 * 
			 */
			/**
			 * Loan Immediate Relief Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LOANRELIEFHOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=loanReliefHome");
				loanBean = loanManagementBusiness.getFamilyRelationList(loanBean);
				session.setAttribute(CPSConstants.RELIEFLIST, loanManagementBusiness.getReliefList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANIMMEDIATERELIEFLIST);
				viewName = CPSConstants.LOANIMMEDIATERELIEF;
			} else if (CPSUtils.compareStrings(CPSConstants.RELIEFMANAGE, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=reliefManage");
				loanBean.setResult(loanManagementBusiness.submitLoanReliefDetails(loanBean));
				session.setAttribute(CPSConstants.RELIEFLIST, loanManagementBusiness.getReliefList());
				viewName = CPSConstants.LOANIMMEDIATERELIEFLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETERELIEF, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=deleteRelief");
				loanBean.setResult(loanManagementBusiness.deleteLoanReliefDetails(loanBean));
				session.setAttribute(CPSConstants.RELIEFLIST, loanManagementBusiness.getReliefList());
				viewName = CPSConstants.LOANIMMEDIATERELIEFLIST;
			}
			/**
			 * Loan Immediate Relief Ends
			 */

			/**
			 * Loan amount details starts
			 * 
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LOANAMOUNTHOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=LoanAmountHome");
				loanBean = loanManagementBusiness.getLoanAmtMasterDetails(loanBean);
				//loanBean = loanManagementBusiness.getLoanFestivalList(loanBean);
				loanBean = loanManagementBusiness.getLoanAmountListDetails(loanBean);
				//session.setAttribute(CPSConstants.SUBTYPELISTJSON, loanBean.getLoanFestivalList());
				session.setAttribute(CPSConstants.DESIGNATIONLISTJSON, loanBean.getDesignationList());
				session.setAttribute(CPSConstants.LOANAMOUNTLIST, loanBean.getLoanAmountDetailsList());
				session.setAttribute(CPSConstants.LOANDESIGMAPPINGS, loanBean.getLoanDesigMappingList());
				session.setAttribute(CPSConstants.LOANAMOUNTGRID, loanBean.getLoanAmountGrid());
				viewName = CPSConstants.LOANAMOUNTHOME;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANAMOUNTDETAILSLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGELOANAMOUNT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=manageLoanAmount");
				loanBean = loanManagementBusiness.manageLoanAmountDetails(loanBean);
				loanBean = loanManagementBusiness.getLoanAmountListDetails(loanBean);
				session.setAttribute(CPSConstants.LOANAMOUNTLIST, loanBean.getLoanAmountDetailsList());
				session.setAttribute(CPSConstants.LOANDESIGMAPPINGS, loanBean.getLoanDesigMappingList());
				session.setAttribute(CPSConstants.LOANAMOUNTGRID, loanBean.getLoanAmountGrid());
				viewName = CPSConstants.LOANAMOUNTDETAILSLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETELOANAMOUNTDETAILS, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=loanAmountDetailsDelete");
				loanBean.setResult(loanManagementBusiness.deleteLoanAmountDetails(loanBean));
				loanBean = loanManagementBusiness.getLoanAmountListDetails(loanBean);
				session.setAttribute(CPSConstants.LOANAMOUNTLIST, loanBean.getLoanAmountDetailsList());
				viewName = CPSConstants.LOANAMOUNTDETAILSLIST;
			}
			/**
			 * Loan amount details ends
			 * 
			 */
			/**
			 * Sending Report starts
			 * 
			 */
			else if (CPSUtils.compareStrings(CPSConstants.SENDINGREPORTHOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=sendingReportHome");
				loanBean = loanManagementBusiness.getFinancialYearList(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.removeAttribute(CPSConstants.APPLIEDLOANSLIST);
				viewName = CPSConstants.SENDINGREPORT;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANREQUESTDETAILSLIST);

			} else if (CPSUtils.compareStrings(CPSConstants.SEARCHSENDINGREPORT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=searchSendingReport");
				loanBean = loanManagementBusiness.getAppliedLoansList(loanBean);
				session.setAttribute(CPSConstants.APPLIEDLOANSLIST, loanBean.getAppliedLoansList());
				viewName = CPSConstants.LOANREQUESTDETAILSLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITSENDINGREPORT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=submitSendingReport");
				loanBean = loanManagementBusiness.submitSendingReportDetails(loanBean);
				session.removeAttribute(CPSConstants.APPLIEDLOANSLIST);
				viewName = CPSConstants.RESULT;
			}
			/**
			 * Sending Report Ends
			 * 
			 */
			/**
			 * HQ Report starts
			 * 
			 */
			else if (CPSUtils.compareStrings(CPSConstants.HQREPORTHOME, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=hqReportHome");
				loanBean = loanManagementBusiness.getReportNumberList(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.removeAttribute(CPSConstants.SEARCHHQLIST);
				viewName = CPSConstants.HQREPORTHOMEPAGE;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANREPORTDETAILSLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.SEARCHHQREPORT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=searchHQReport");
				loanBean = loanManagementBusiness.getSearchCDAList(loanBean);
				session.setAttribute(CPSConstants.SEARCHHQLIST, loanBean.getSearchCDAList());
				viewName = CPSConstants.LOANREPORTDETAILSLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITHQREPORT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=submitCDAReport");
				loanBean.setIpAddress(request.getRemoteAddr());
				loanBean = loanManagementBusiness.submitCDAReportDetails(loanBean);
				session.removeAttribute(CPSConstants.SEARCHHQLIST);
				viewName = CPSConstants.RESULT;
			}else if (CPSUtils.compareStrings(CPSConstants.REGENERATEHQREPORT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=reGenerateHQReport");
				loanBean = loanManagementBusiness.submitReGenerateDetails(loanBean);
				session.removeAttribute(CPSConstants.SEARCHHQLIST);
				viewName = CPSConstants.RESULT;
			}
			/**
			 * HQ Report Ends
			 * 
			 */
			/**
			 * HBA INTEREST RATES START
			 * 
			 */
			else if(CPSUtils.compareStrings(CPSConstants.HBAINTERESTRATES, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=hbaInterestRate");  
				loanBean = loanManagementBusiness.getLoanHBAInterestGridList(loanBean);
				session.setAttribute(CPSConstants.LOANHBAINTERESTLIST, loanBean.getLoanHBAInterestList());
				if(!CPSUtils.isNullOrEmpty(session.getAttribute("jsonLoanHBAInterestGridList"))) session.removeAttribute("jsonLoanHBAInterestGridList");
				if(CPSUtils.checkList(loanBean.getLoanHBAInterestList())) session.setAttribute("jsonLoanHBAInterestGridList",(JSONArray)JSONSerializer.toJSON(loanBean.getLoanHBAInterestList())); 
				viewName = CPSConstants.HBAINTERESTRATESHOME;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOANHBAINTERESTLIST);
				
			}
			else if(CPSUtils.compareStrings(CPSConstants.MANAGEHBAINTERESTRATES, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=hbaInterestRate");
				loanBean=loanManagementBusiness.manageLoanHBAInterestGrid(loanBean);
				loanBean = loanManagementBusiness.getLoanHBAInterestGridList(loanBean);
				session.setAttribute(CPSConstants.LOANHBAINTERESTLIST, loanBean.getLoanHBAInterestList());
				if(!CPSUtils.isNullOrEmpty(session.getAttribute("jsonLoanHBAInterestGridList"))) session.removeAttribute("jsonLoanHBAInterestGridList");
				if(CPSUtils.checkList(loanBean.getLoanHBAInterestList())) session.setAttribute("jsonLoanHBAInterestGridList",(JSONArray)JSONSerializer.toJSON(loanBean.getLoanHBAInterestList())); 
				viewName = CPSConstants.LOANHBAINTERESTLIST;
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETEHBAINTERESTRATES, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=deleteHBAGrid");
				loanBean.setResult(loanManagementBusiness.deleteLoanHBAInterestGrid(loanBean));
				loanBean = loanManagementBusiness.getLoanHBAInterestGridList(loanBean);
				session.setAttribute(CPSConstants.LOANHBAINTERESTLIST, loanBean.getLoanHBAInterestList());
				if(!CPSUtils.isNullOrEmpty(session.getAttribute("jsonLoanHBAInterestGridList"))) session.removeAttribute("jsonLoanHBAInterestGridList");
				if(CPSUtils.checkList(loanBean.getLoanHBAInterestList())) session.setAttribute("jsonLoanHBAInterestGridList",(JSONArray)JSONSerializer.toJSON(loanBean.getLoanHBAInterestList())); 
				viewName = CPSConstants.LOANHBAINTERESTLIST;
			}
			/**
			 * HBA INTEREST RATES END
			 * 
			 */			
			
			/**
			 * Reports START
			 * 
			 */
			else if(CPSUtils.compareStrings(CPSConstants.CONVEYANCEREPORTSHOME, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=conveyanceReportshome");
				loanBean = loanManagementBusiness.getConveyanceReportList(loanBean);
				session.setAttribute(CPSConstants.CONVEYANCEREPORTLIST, loanBean.getConveyanceReportList());
				viewName = CPSConstants.CONVEYANCEADVANCEDEMAND;
			}
			else if(CPSUtils.compareStrings(CPSConstants.LOANACQUITTANCEREPORT, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=loanAcquittanceReport");
				loanBean = loanManagementBusiness.getHQReportList(loanBean);
				loanBean = loanManagementBusiness.getCDAReportList(loanBean);
				loanBean = loanManagementBusiness.getFinancialYearList(loanBean);
				loanBean = loanManagementBusiness.getLoanFestivalList(loanBean);
				session.setAttribute(CPSConstants.LOANCDALIST, loanBean.getLoanCDAist());
				session.removeAttribute(CPSConstants.LOANPAYBILLACQUITTANCELIST);
				session.removeAttribute(CPSConstants.FESTIVALPAYBILLACQUITTANCELIST);
				viewName = CPSConstants.LOANPAYBILLACQUITTANCE;
				session.setAttribute(CPSConstants.RETURN1, CPSConstants.LOANPAYBILLACQUITTANCELIST);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.FESTIVALPAYBILLACQUITTANCELIST);
				
			}
			else if (CPSUtils.compareStrings(CPSConstants.SEARCHSPAYACREPORT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=searchPayAcReport");
				loanBean = loanManagementBusiness.getAppliedFestivalList(loanBean);
				session.setAttribute(CPSConstants.FESTIVALPAYBILLACQUITTANCELIST, loanBean.getAppliedLFestivalList());
				viewName = CPSConstants.FESTIVALPAYBILLACQUITTANCELIST;
			}
			else if(CPSUtils.compareStrings(CPSConstants.LOANPAYBILLACREPORT, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=searchPayBillAcReport");
				loanBean = loanManagementBusiness.getPaybillBACReportList(loanBean);
				session.setAttribute(CPSConstants.LOANPAYBILLACQUITTANCELIST, loanBean.getPaybillBACReportList());
				viewName = CPSConstants.LOANPAYBILLACQUITTANCELIST;
			}
			else if(CPSUtils.compareStrings(CPSConstants.SAVESRADMINOFFICER, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=saveSrAdminOfficer");
				loanBean = loanManagementBusiness.submitAdminOfficer(loanBean);
				viewName = CPSConstants.RESULT;
			}
			else if(CPSUtils.compareStrings(CPSConstants.SAVECDAREPORT, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=saveCDAReport");
				loanBean = loanManagementBusiness.submitCDAReport(loanBean);
				//viewName = CPSConstants.RESULT;
				if(loanBean.getListType().equals("dataList")){
					loanBean = loanManagementBusiness.getPaybillBACReportList(loanBean);
					session.setAttribute(CPSConstants.LOANPAYBILLACQUITTANCELIST, loanBean.getPaybillBACReportList());
					viewName = CPSConstants.LOANPAYBILLACQUITTANCELIST;
				}else if(loanBean.getListType().equals("festivalList")){
					loanBean = loanManagementBusiness.getAppliedFestivalList(loanBean);
					session.setAttribute(CPSConstants.FESTIVALPAYBILLACQUITTANCELIST, loanBean.getAppliedLFestivalList());
					viewName = CPSConstants.FESTIVALPAYBILLACQUITTANCELIST;				
				}
			}else if(CPSUtils.compareStrings(CPSConstants.SANCTIONANDCONTINGENT, loanBean.getParam())){
				log.debug("LoanManagementController --> onSubmit --> param=sanctionAndContingent");
				session.removeAttribute(CPSConstants.SANCTIONCONTINGENTLIST);
				loanBean = loanManagementBusiness.getHQReportNumberList(loanBean);
				loanBean = loanManagementBusiness.getLoanTypeMasterDetails(loanBean);
				session.setAttribute(CPSConstants.LOANHQLISTJSON, loanBean.getReportNumberList());
				viewName = CPSConstants.SANCTIONANDCONTINGENTHOME;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.SANCTIONCONTINGENTLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SEARCHSANCCONREPORT, loanBean.getParam())) {
				log.debug("LoanManagementController --> onSubmit --> param=searchSanctionConReport");
				loanBean = loanManagementBusiness.getSanctionContingentList(loanBean);
				session.setAttribute(CPSConstants.SANCTIONCONTINGENTLIST, loanBean.getSanctionConReportList());
				viewName = CPSConstants.SANCTIONCONTINGENTLIST;
			}
			
			/**
			 * Reports End
			 * 
			 */
			mav = new ModelAndView(viewName, CPSConstants.LOAN, loanBean);

			if (!CPSUtils.isNullOrEmpty(loanBean.getResult())) {
				mav.addObject(CPSConstants.MESSAGE,loanBean.getResult());
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}