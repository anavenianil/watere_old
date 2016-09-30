package com.callippus.web.controller.paybill;

import java.io.File;
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

import com.callippus.web.beans.paybill.PayBillMasterBean;
import com.callippus.web.business.paybill.PayBillMasterBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.paybill.dto.PayMastersPortingDTO;

@Controller
//@RequestMapping("/payBill.htm")
@SessionAttributes
public class PayBillMasterController {
	private static Log log = LogFactory.getLog(PayBillMasterController.class);
	@Autowired
	private PayBillMasterBusiness payBillMasterBusiness;
	@Autowired
	private FileUpload fileUpload;

	@RequestMapping(value="/payBill.htm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.PAYBILLMASTER) PayBillMasterBean payBillMasterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
			
			request.getHeaderNames();
			//ErpAction.userChecks(request);
			response.setHeader("no-cache","no-store");
			request.getHeader("Cache-Control");
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(payBillMasterBean.getParam())) {
				payBillMasterBean.setParam(CPSConstants.PAYSCALEHOME);
			}
			if (CPSUtils.compareStrings(CPSConstants.PAGING, payBillMasterBean.getParam())) { 
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			payBillMasterBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			payBillMasterBean.setPayRunMonth(payBillMasterBusiness.getCurrentRunMonth());
			/**
			 * Start CGHS Master Details
			 */
		if (CPSUtils.compareStrings(CPSConstants.CGHSMASTER, payBillMasterBean.getParam())) {
				log.debug("PayBillMasterController --> execute --> param=cghsMaster");
				payBillMasterBean = payBillMasterBusiness.getPayBillCGHSDetails(payBillMasterBean);
				session.setAttribute("gradepaylist", payBillMasterBean.getGradePayList());
				session.setAttribute(CPSConstants.CGHSMASTERDETAILS, payBillMasterBean.getCghsMasterDetails());
				viewName = CPSConstants.PAYBILLCGHSMASTERDETAILS;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYBILLCGHSMASTERLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGECGHS, payBillMasterBean.getParam())) {
				log.debug("PayBillMasterController --> execute --> param=manageCghs");
				payBillMasterBean = payBillMasterBusiness.managePayBillCGHSDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.CGHSMASTERDETAILS, payBillMasterBean.getCghsMasterDetails());
				viewName = CPSConstants.PAYBILLCGHSMASTERLIST;
			}
			/**
			 * End CGHS Master Details
			 */

			/**
			 * Start CGEIS Master Details
			 */
			else if (CPSUtils.compareStrings(CPSConstants.CGEISMASTER, payBillMasterBean.getParam()))
			{
				log.debug("PayBillMasterController --> execute --> param=cgeisMaster");
				payBillMasterBean = payBillMasterBusiness.getPayBillCGEISDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.CGEISMASTERDETAILS, payBillMasterBean.getCgeisMasterDetails());
				viewName = CPSConstants.PAYBILLCGEISMASTERDETAILS;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYBILLCGEISMASTERLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGECGEIS, payBillMasterBean.getParam())) {
				log.debug("PayBillMasterController --> execute --> param=manageCghs");
				payBillMasterBean = payBillMasterBusiness.managePayBillCGEISDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.CGEISMASTERDETAILS, payBillMasterBean.getCgeisMasterDetails());
				payBillMasterBean.setMessage(payBillMasterBean.getResult());
				viewName = CPSConstants.PAYBILLCGEISMASTERLIST;
			}/**
			 * End CGEIS Master Details
			 */

			/**
			 * Start DA Master Details
			 */
			else if (CPSUtils.compareStrings(CPSConstants.DAMASTER, payBillMasterBean.getParam())) {
				log.debug("PayBillMasterController --> execute --> param=DAMaster");
				payBillMasterBean = payBillMasterBusiness.getPayBillDearNessAllowanceDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.DAMASTERDETAILS, payBillMasterBean.getDearNessAllowanceMasterDetails());
				viewName = CPSConstants.PAYBILLDAMASTERDETAILS;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYBILLDAMASTERLIST);

			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEDA, payBillMasterBean.getParam())) {
				log.debug("PayBillMasterController --> execute --> param=manageDA");
				payBillMasterBean = payBillMasterBusiness.managePayBillDearNessAllowanceDetails(payBillMasterBean);
				//if (CPSUtils.compareStrings(payBillMasterBean.getResult(), CPSConstants.SUCCESS)) {
					session.setAttribute(CPSConstants.DAMASTERDETAILS, payBillMasterBean.getDearNessAllowanceMasterDetails());
				//}
				payBillMasterBean.setMessage(payBillMasterBean.getResult());
				viewName = CPSConstants.PAYBILLDAMASTERLIST;
			}/**
			 * End DA Master Details
			 */

			/**
			 * Start RS Master Details
			 */
			else if (CPSUtils.compareStrings(CPSConstants.RSMASTER, payBillMasterBean.getParam())) {
				log.debug("PayBillMasterController --> execute --> param=residentialSecurityMaster");
				payBillMasterBean = payBillMasterBusiness.getPayBillResidentialSecurityDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.RSMASTERDETAILS, payBillMasterBean.getResidentialSecurityMasterDeatails());
				viewName = CPSConstants.PAYBILLRSMASTERDETAILS;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYBILLRSMASTERLIST);

			} else if (CPSUtils.compareStrings(CPSConstants.MANAGERS, payBillMasterBean.getParam())) {
				log.debug("PayBillMasterController --> execute --> param=manageRS");
				payBillMasterBean = payBillMasterBusiness.managePayBillResidentialSecurityDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.RSMASTERDETAILS, payBillMasterBean.getResidentialSecurityMasterDeatails());
				viewName = CPSConstants.PAYBILLRSMASTERLIST;
			}/**
			 * End RS Master Details
			 */

			/**
			 * Start Delete Master Details
			 */
			else if (CPSUtils.compareStrings(CPSConstants.DELETE, payBillMasterBean.getParam())) 
			{
				log.debug("PayBillMasterController --> execute --> param=delete");
				payBillMasterBean = payBillMasterBusiness.deletePayBillMasterRecord(payBillMasterBean);
				if (CPSUtils.compareStrings(payBillMasterBean.getResult(), CPSConstants.DELETE) || CPSUtils.compareStrings(payBillMasterBean.getResult(), CPSConstants.INACTIVE))
				{
					if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.CGHS)) {
						payBillMasterBean = payBillMasterBusiness.getPayBillCGHSDetails(payBillMasterBean);
						session.setAttribute(CPSConstants.CGHSMASTERDETAILS, payBillMasterBean.getCghsMasterDetails());
						viewName = CPSConstants.PAYBILLCGHSMASTERLIST;
					} else if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.CGEIS)) {
						payBillMasterBean = payBillMasterBusiness.getPayBillCGEISDetails(payBillMasterBean);
						session.setAttribute(CPSConstants.CGEISMASTERDETAILS, payBillMasterBean.getCgeisMasterDetails());
						viewName = CPSConstants.PAYBILLCGEISMASTERLIST;
					} else if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.DEARNESSALLOWANCE)) {
						payBillMasterBean = payBillMasterBusiness.getPayBillDearNessAllowanceDetails(payBillMasterBean);
						session.setAttribute(CPSConstants.DAMASTERDETAILS, payBillMasterBean.getDearNessAllowanceMasterDetails());
						viewName = CPSConstants.PAYBILLDAMASTERLIST;
					} else if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.RESIDENTIALSECURITY)) {
						payBillMasterBean = payBillMasterBusiness.getPayBillResidentialSecurityDetails(payBillMasterBean);
						session.setAttribute(CPSConstants.RSMASTERDETAILS, payBillMasterBean.getResidentialSecurityMasterDeatails());
						viewName = CPSConstants.PAYBILLRSMASTERLIST;
					}
				}
				payBillMasterBean.setMessage(payBillMasterBean.getResult());
			}
			/**
			 * End Delete Master Details
			 */
			/**
			 * Start of Css file upload
			 */
			else if (CPSUtils.compareStrings(CPSConstants.HOMECCS, payBillMasterBean.getParam())) {
				viewName = CPSConstants.CCSUPLOAD;
			} else if (CPSUtils.compareStrings(CPSConstants.NEWCCSUPLOAD, payBillMasterBean.getParam())) {

				File reportFile = new File(session.getServletContext().getResource("/repository/").getPath());
				if (payBillMasterBean.getXslFile() != null) {
					String fileName = fileUpload.uploadFile(payBillMasterBean.getXslFile(), payBillMasterBean.getXslFile().getOriginalFilename(), payBillMasterBean.getXslFile().getOriginalFilename()
							.split("\\.")[1], reportFile.getAbsolutePath() + "/CCSUpload/", CPSConstants.NO);
					String message = payBillMasterBusiness.uploadCCSDetails(reportFile.getAbsolutePath() + File.separatorChar + "CCSUpload" + File.separatorChar + fileName, payBillMasterBean
							.getSfID());
					payBillMasterBean.setMessage(message);
					if (CPSUtils.compareStrings(CPSConstants.SUCCESS, payBillMasterBean.getMessage())) {
						payBillMasterBean.setMessage("empPayStop");
						payBillMasterBean.setRemarks("upload Record Successfully");
					}
				}

				viewName = CPSConstants.RESULT;
			}
			/**
			 * End of Css file upload
			 */
			/***
			 * Start of Deduction Screen
			 */
			else if (CPSUtils.compareStrings(CPSConstants.COINCUTSEARCH, payBillMasterBean.getParam())) {
				
				
				payBillMasterBean.setSfidSearchKey((String)session.getAttribute("Labid"));
				payBillMasterBean.setEmployeeName("");
				viewName = CPSConstants.COINCUTSEARCH;
			}

			else if (CPSUtils.compareStrings(CPSConstants.COINCUTHOME, payBillMasterBean.getParam())) {

				payBillMasterBean = payBillMasterBusiness.getCoinCutDeductionMasterDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.COINCUTMASTERDETAILS, payBillMasterBean.getCoinCutMasterDetails());
				session.setAttribute(CPSConstants.CCDETAILS, payBillMasterBean.getEmpCCList());
				if (CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()))
					viewName = CPSConstants.RESULT;
				else
					viewName = CPSConstants.COINCUTDEDUCTION;
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGECCDETAILS, payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.submitCanfinDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.CCDETAILS, payBillMasterBean.getEmpCCList());
				viewName = CPSConstants.EMPCCLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETECCDETAILS, payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.deleteEmpCCDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.CCDETAILS, payBillMasterBean.getEmpCCList());
				viewName = CPSConstants.EMPCCLIST;
			}
			/***
			 * End of Deduction Screen
			 */
			/***
			 * Start of EMPCategory Screen
			 */
			else if (CPSUtils.compareStrings(CPSConstants.EMPCATEGORYHOME, payBillMasterBean.getParam())) {
				payBillMasterBean.setEmpCategroyMasterList(payBillMasterBusiness.getEmpCategoryDetailsList());
				session.setAttribute(CPSConstants.EMPCATEGORYMASTERDETAILS, payBillMasterBean.getEmpCategroyMasterList());
				session.setAttribute("jsonPayOrderNo", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.EMPCATEGORYMASTERDETAILS)));
				viewName = CPSConstants.PAYBILLEMPCATEGORY;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYBILLEMPCATEGORYDETAILS);
			} else if (CPSUtils.compareStrings(CPSConstants.EMPCATEGORYUPDATE, payBillMasterBean.getParam())) {
				payBillMasterBean.setMessage(payBillMasterBusiness.setEmpCategoryMasterDetails(payBillMasterBean));
				payBillMasterBean.setEmpCategroyMasterList(payBillMasterBusiness.getEmpCategoryDetailsList());
				session.setAttribute(CPSConstants.EMPCATEGORYMASTERDETAILS, payBillMasterBean.getEmpCategroyMasterList());
				session.setAttribute("jsonPayOrderNo", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.EMPCATEGORYMASTERDETAILS)));
				viewName = CPSConstants.PAYBILLEMPCATEGORYDETAILS;
			}

			else if (CPSUtils.compareStrings(CPSConstants.EMPCATEGORYDELETE, payBillMasterBean.getParam())) {
				payBillMasterBean.setMessage(payBillMasterBusiness.deleteEmpCategoryMasterDetails(payBillMasterBean));

				payBillMasterBean.setEmpCategroyMasterList(payBillMasterBusiness.getEmpCategoryDetailsList());
				session.setAttribute(CPSConstants.EMPCATEGORYMASTERDETAILS, payBillMasterBean.getEmpCategroyMasterList());

				viewName = CPSConstants.PAYBILLEMPCATEGORYDETAILS;
			}

			/***
			 * End of EMPCategory Screen
			 */
			/******
			 * Start EMP PAY DETAILS entry
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PAYONETIMEENTRYHOME, payBillMasterBean.getParam())) {
				payBillMasterBean.setEmpList(payBillMasterBusiness.getEmployeesNotExistedInPayBill());
				payBillMasterBean.setEmpCategroyMasterList(payBillMasterBusiness.getEmpCategoryDetailsList());
				session.setAttribute(CPSConstants.EMPCATEGORYMASTERDETAILS, payBillMasterBean.getEmpCategroyMasterList());
				payBillMasterBean = payBillMasterBusiness.getPayScaleDetailsList(payBillMasterBean);
				session.setAttribute(CPSConstants.PAYBILLGROUPMASTERLIST, payBillMasterBean.getPayBillGroupMasterList());
				session.setAttribute(CPSConstants.PAYBILLQUARTERLIST, payBillMasterBean.getQuarterDetailsList());
				viewName = CPSConstants.PAYBILLEMPDETAILS;
			} else if (CPSUtils.compareStrings("insertPayOneTimeDetails", payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.insertEmpOneTimeEntry(payBillMasterBean);
				session.setAttribute(CPSConstants.REMARKS, payBillMasterBean.getRemarks());
				payBillMasterBean.setEmpList(payBillMasterBusiness.getEmployeesNotExistedInPayBill());
				session.setAttribute("empList", payBillMasterBean.getEmpList());
				viewName = CPSConstants.PAYBILLEMPCORESFIDDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYEMPCONFIGURATIONDETAILS, payBillMasterBean.getParam())) {
				payBillMasterBean.setSfidSearchKey((String)session.getAttribute("Labid"));
				payBillMasterBean.setEmployeeName("");
				viewName = CPSConstants.PAYEMPCONFIGURATIONDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYSEARCHSFID, payBillMasterBean.getParam())) {
				payBillMasterBean.setEmpCategroyMasterList(payBillMasterBusiness.getEmpCategoryDetailsList());
				session.setAttribute(CPSConstants.EMPCATEGORYMASTERDETAILS, payBillMasterBean.getEmpCategroyMasterList());
				payBillMasterBean = payBillMasterBusiness.getEmpDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.GRADEPAYLIST, payBillMasterBean.getGradePayList());
				session.setAttribute(CPSConstants.PAYBILLSCALELIST, payBillMasterBean.getPayscaleList());
				session.setAttribute(CPSConstants.PAYBILLDESGLIST, payBillMasterBean.getDesignationList());
				session.setAttribute(CPSConstants.PAYBILLGROUPMASTERLIST, payBillMasterBean.getPayBillGroupMasterList());
				session.setAttribute(CPSConstants.PAYBILLQUARTERLIST, payBillMasterBean.getQuarterDetailsList());
				session.setAttribute(CPSConstants.PHTYPELIST, payBillMasterBean.getHandicapList());
				if (CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()) || CPSUtils.compareStrings(CPSConstants.PAYDETAILSNOTEXISTS, payBillMasterBean.getMessage()))
					viewName = CPSConstants.RESULT;
				else
					viewName = CPSConstants.PAYEMPDETAILSLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.UPDATEPAYONETIMEDETAILS, payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.updateEmpPayDetails(payBillMasterBean);

				viewName = CPSConstants.RESULT;
			}else if(CPSUtils.compareString("totIncomeDetails",payBillMasterBean.getParam())){          //new link  in payBill in TotalIncomeDetails0912
		           if(CPSUtils.compareString("totDetails",payBillMasterBean.getType())){
			        	 
			        	
		        	session.setAttribute("payBillList", payBillMasterBusiness.totalPayDetails(payBillMasterBean.getStartDate(),Integer.parseInt(payBillMasterBean.quarterTypeID)));
		        	session.setAttribute("runMonth",payBillMasterBean.getStartDate());
		        	session.setAttribute("quarterTypeID",payBillMasterBean.quarterTypeID);
		        	viewName="TotalIncomeDetailsHome";
		           }else if(CPSUtils.compareString("saveDetails",payBillMasterBean.getType())){
		        	   payBillMasterBean.setMessage(payBillMasterBusiness.saveTOTPayDetails(payBillMasterBean));
		        	   session.setAttribute("payBillList", payBillMasterBusiness.totalPayDetails(payBillMasterBean.getStartDate(),Integer.parseInt(payBillMasterBean.quarterTypeID)));
		        	   
		        	   viewName="TotalIncomeDetailsHome";
		           }
		           else{
			        session.setAttribute("categoryList",( payBillMasterBusiness).getAssessmentTypeList());
		                viewName="TotalIncomeDetails";
		           }
		}
			/******
			 * END EMP PAY DETAILS entry
			 */

			/**
			 * StartPay Loan Details
			 * */
			else if (CPSUtils.compareStrings(CPSConstants.PAYLOANDETAILSHOME, payBillMasterBean.getParam())) {
				payBillMasterBean.setSfidSearchKey((String)session.getAttribute("Labid"));
				payBillMasterBean.setEmployeeName("");
				viewName = CPSConstants.PAYEMPLOANSEARCH;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYLOANSEARCHSFID, payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.getEmpLoanDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.EMPLOANLIST, payBillMasterBean.getEmpLoanList());
				if (CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()))
					viewName = CPSConstants.RESULT;
				else
					viewName = CPSConstants.PAYEMPLOANDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.INSERTPAYLOANDETAILS, payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.updateEmpLoanDetails(payBillMasterBean);
				session.setAttribute(CPSConstants.EMPLOANLIST, payBillMasterBean.getEmpLoanList());
				viewName = CPSConstants.PAYEMPLOANDETAILSLIST;
			}
			/**
			 * End Pay Loan Details
			 * **/

			/**
			 * Start Allowance Configuration
			 * */

			else if (CPSUtils.compareStrings(CPSConstants.PAYALLOWANCEHOME, payBillMasterBean.getParam())) {
				
				viewName = CPSConstants.PAYALLOWANCECONFIGURATION;
				
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEALLOWANCEDETAILS, payBillMasterBean.getParam())) {
				payBillMasterBean.setMessage(payBillMasterBusiness.manageAllowanceConfigurationList(payBillMasterBean));
				viewName = CPSConstants.RESULT;
				
			} else if (CPSUtils.compareStrings(CPSConstants.SHOWALLOWANCEGROUP, payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.getAllowanceGroupList(payBillMasterBean);
				session.setAttribute(CPSConstants.PAYALLOWANCEGROUPLIST, payBillMasterBean.getAllowanceGroupList());
				session.setAttribute(CPSConstants.PAYALLOWANCESELGROUPLIST, payBillMasterBean.getAllowanceSelGroupList());
				viewName = CPSConstants.PAYALLOWANCEGROUPLIST;

			}
			/***
			 * End Allowance Configuration
			 * */
			else if (CPSUtils.compareStrings(CPSConstants.PAYDUEHOME, payBillMasterBean.getParam())) {
				payBillMasterBean.setSfidSearchKey((String)session.getAttribute("Labid"));
				payBillMasterBean.setEmployeeName("");
				viewName = CPSConstants.EMPPAYDUES;
			} else if (CPSUtils.compareStrings(CPSConstants.UPDATEEMPDUES, payBillMasterBean.getParam())) {

				payBillMasterBean = payBillMasterBusiness.updateEmpDues(payBillMasterBean);
				session.setAttribute("payEmpDuesList", payBillMasterBean.getEmpDuesList());
				session.setAttribute(CPSConstants.RETURN,  CPSConstants.EMPPAYDUESDETAILSLIST);
				viewName = CPSConstants.EMPPAYDUESDETAILSLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.GETDUEDETAILS, payBillMasterBean.getParam())) {

				payBillMasterBean = payBillMasterBusiness.getDuesDetails(payBillMasterBean);
				session.setAttribute("payEmpDuesList", payBillMasterBean.getEmpDuesList());
				session.setAttribute(CPSConstants.RETURN,  CPSConstants.EMPPAYDUESDETAILSLIST);
				if (CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()) || CPSUtils.compareStrings(CPSConstants.CONSTRAINTS, payBillMasterBean.getMessage()))
					viewName = CPSConstants.RESULT;
				else
					viewName = CPSConstants.EMPPAYDUESDETAILS;
			}

			else if (CPSUtils.compareStrings(CPSConstants.PAYEOLHOME, payBillMasterBean.getParam())) {
				payBillMasterBean.setSfidSearchKey((String)session.getAttribute("Labid"));
				viewName = CPSConstants.EMPEOLSEARCH;
			} else if (CPSUtils.compareStrings(CPSConstants.GETPAYEOL, payBillMasterBean.getParam())) {
				payBillMasterBean.setDivisionName((String)session.getAttribute("Labid"));
				payBillMasterBean = payBillMasterBusiness.getEmpEolDetails(payBillMasterBean);
				session.setAttribute("payeolList", payBillMasterBean.getEmpEolList());
				
				payBillMasterBusiness.getRunmonth(payBillMasterBean);
				// added by naagendra.v  used for pagination
				// when ever go for pagination we would put the session attribute.
				session.setAttribute(CPSConstants.RETURN,  CPSConstants.EMPEOLDETAILS);
				viewName = CPSConstants.EMPEOLDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.PAYEOLACCEPT, payBillMasterBean.getParam())) {
				payBillMasterBean = payBillMasterBusiness.empEolDetails(payBillMasterBean);
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings("payHindiHome", payBillMasterBean.getParam())) {
				payBillMasterBean.setHindiList(payBillMasterBusiness.getPayHindiInceList());
				session.setAttribute("hindiInceList", payBillMasterBean.getHindiList());
				viewName = "payHindiIncHome";
			} else if (CPSUtils.compareStrings("submitPayHindiDetails", payBillMasterBean.getParam())) {
				payBillMasterBean.setMessage(payBillMasterBusiness.submitPayHindiIncDetails(payBillMasterBean));
				session.setAttribute("hindiInceList", payBillMasterBusiness.getPayHindiInceList());
				viewName = "payHindiInceList";
			} else if (CPSUtils.compareStrings("deletePayHindiDetails", payBillMasterBean.getParam())) {
				payBillMasterBean.setMessage(payBillMasterBusiness.deletePayHindiDetails(Integer.parseInt(payBillMasterBean.getPk()), payBillMasterBean.getSfID()));
				session.setAttribute("hindiInceList", payBillMasterBusiness.getPayHindiInceList());
				viewName = "payHindiInceList";
			} else if (CPSUtils.compareStrings("getEmpCoreDetails", payBillMasterBean.getParam())) {
				payBillMasterBean.setEmpList(payBillMasterBusiness.getEmployeesNotExistedInPayBill());
				session.setAttribute("empList", payBillMasterBean.getEmpList());
				payBillMasterBean = payBillMasterBusiness.getEmpCorePayDetails(payBillMasterBean);
				viewName = CPSConstants.PAYBILLEMPCOREDETAILS;
				payBillMasterBean.setParam("");
			} else if (CPSUtils.compareStrings(CPSConstants.PAYWELCOMEHOME, payBillMasterBean.getParam())) {
				 session.setAttribute("Labid",payBillMasterBusiness.getLabid());
				viewName = CPSConstants.PAYWELCOMEPAGE;
			}
			/*else if(CPSUtils.compareString(CPSConstants.EMPSEARCHSFID, payBillMasterBean.getParam()))
			{
				payBillMasterBean.setMessage(payBillMasterBusiness.getEmployee(payBillMasterBean.getSfidSearchKey()));
				if(CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()))
				{
					viewName = CPSConstants.RESULT;
				}
				else 
				{
					viewName = "EmployeeBankAccount";
				}
			}*/
				// Added By Naresh for Employee Bank Account Master screen
			else if(CPSUtils.compareStrings(CPSConstants.EMPBANKACCOUNT, payBillMasterBean.getParam()))
			{
				payBillMasterBean.setSfidSearchKey((String)session.getAttribute("Labid"));
				payBillMasterBean.setEmployeeName("");
				viewName = CPSConstants.BANKACCOUNTMASTER;
			}
			else if(CPSUtils.compareStrings(CPSConstants.EMPBANKRECORD, payBillMasterBean.getParam()))
			{
				payBillMasterBean.setMessage(payBillMasterBusiness.getEmployee(payBillMasterBean.getSfidSearchKey()));
				if(CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()))
				{
					viewName = CPSConstants.RESULT;
				}
				else 
				{
					payBillMasterBean = payBillMasterBusiness.getDetails(payBillMasterBean);
					session.setAttribute("bankList", payBillMasterBusiness.getDetails(payBillMasterBean));
					viewName = CPSConstants.BANKACCOUNTLIST;
				}
			}
			else if(CPSUtils.compareStrings(CPSConstants.EMPBANKSUBMITDETAILS, payBillMasterBean.getParam()))
			{
				payBillMasterBean.setMessage(payBillMasterBusiness.getEmployee(payBillMasterBean.getSfidSearchKey()));
				if(CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()))
				{
					viewName = CPSConstants.RESULT;
				}
				else 
				{
					payBillMasterBean.setMessage(payBillMasterBusiness.submitDetails(payBillMasterBean));
					session.setAttribute("bankList", payBillMasterBusiness.getDetails(payBillMasterBean));
					viewName = CPSConstants.BANKACCOUNTLIST;
				}
			}	// End : Employee Bank Account Master Screen
				
    		mav = new ModelAndView(viewName, CPSConstants.PAYBILLMASTER, payBillMasterBean);
			if (!CPSUtils.isNullOrEmpty(payBillMasterBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, payBillMasterBean.getMessage());

			}
			if (!CPSUtils.isNullOrEmpty(payBillMasterBean.getRemarks())) {
				mav.addObject(CPSConstants.REMARKS, payBillMasterBean.getRemarks());

			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;	
	}

	//@RequestMapping(value = "/employeesfid.htm", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=*/*")
	/*public ModelAndView getRecordDetails(@ModelAttribute("payBillMaster") PayBillMasterBean payBillMasterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mav = null;
		try
		{
			String	str = payBillMasterBean.getSfidSearchKey();
			if(!str.isEmpty())
			payBillMasterBean.setRecord(payBillMasterBusiness.getRecord(str));
			mav = new ModelAndView("EmployeeBankAccount", CPSConstants.PAYBILLMASTER, payBillMasterBean);
		}catch (Exception e) 
		{
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	*/
	
	// Added By Naresh
	// This method get the list of employees with 'Name' and 'SFID' regarding to entered character in the text field
	@RequestMapping(value = "/empSFID.htm", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=*/*")
	public ModelAndView searchSFID(@ModelAttribute(CPSConstants.PAYBILLMASTER) PayBillMasterBean payBillMasterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mav = null;
		String viewName = "";
		HttpSession session = null;
		String message = null;
		try
		{
			String	query = payBillMasterBean.getSfidSearchKey();
			if(!query.isEmpty())
			{
				session = request.getSession(true);
				payBillMasterBean.setMessage(payBillMasterBusiness.getEmployee(query));
				if(CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, payBillMasterBean.getMessage()))
				{
					message = CPSConstants.EMPNOTEXISTS;
					viewName = CPSConstants.RESULT;
				}
				else
				{
					payBillMasterBean.setEmployeeName(payBillMasterBusiness.getSearchName(query));
					session.setAttribute("ename", payBillMasterBean.getEmployeeName());
					viewName = CPSConstants.RESULT;
				}
			}
			mav = new ModelAndView(viewName, CPSConstants.PAYBILLMASTER, payBillMasterBean);
			if(!CPSUtils.isNullOrEmpty(message))
			mav.addObject(CPSConstants.MESSAGE, message);
		}catch (Exception e) 
		{
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} 
		return mav;
	}
	
	@RequestMapping(value = "/employeeName.htm", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=*/*")
	public void searchData(@ModelAttribute(CPSConstants.PAYBILLMASTER) PayBillMasterBean payBillMasterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		List<String> list = null; 
		try
		{
			String	query = request.getParameter("term").trim();
			payBillMasterBean.setType("letters");
			if(!query.isEmpty())
			{
				list = payBillMasterBusiness.getSearchList(query);
				org.json.JSONArray json = new org.json.JSONArray(list);
			    response.setContentType("text/json");
				response.getWriter().print(json.toString());
			}
		}catch (Exception e)
		{
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} 
	}

}
