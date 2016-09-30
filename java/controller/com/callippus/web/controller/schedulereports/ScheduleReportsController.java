package com.callippus.web.controller.schedulereports;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.master.MasterDataBean;
import com.callippus.web.beans.reports.ReportsBean;
import com.callippus.web.beans.schedulereports.ScheduleReportsBean;
import com.callippus.web.business.reports.ReportsBusiness;
import com.callippus.web.business.schedulereports.ScheduleReportsBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.paybill.IPayBillDAO;
import com.callippus.web.dao.paybill.IPayBillMasterDAO;

@Controller
@SessionAttributes
public class ScheduleReportsController {
private static Log log=LogFactory.getLog(ScheduleReportsController.class);
@Autowired
private ScheduleReportsBusiness scheduleReportsBusiness;
@Autowired
com.callippus.web.controller.common.HibernateUtils hibernateUtils;
@Autowired
private IPayBillMasterDAO payBillMasterDAO;
@Autowired
private IPayBillDAO payBillDAO;
@Autowired
private ReportsBusiness reportsBusiness;


@RequestMapping(value = "/scheduleReports.htm",method = { RequestMethod.POST, RequestMethod.GET })
public ModelAndView execute(@ModelAttribute(CPSConstants.SCHEDULEREPORTS) ScheduleReportsBean schedulereportsBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
	String viewName = null;
 	Session dbsession = null;
    ModelAndView mav=null;
	try 
	{
		ErpAction.sessionChecks(request);
		Calendar cl = Calendar.getInstance();
		HttpSession session= request.getSession(true);
		String datetime = cl.get(Calendar.YEAR) + "" + cl.get(Calendar.MONTH) + 1 + "" + cl.get(Calendar.DATE) + "" + cl.get(Calendar.HOUR) + "" + cl.get(Calendar.MINUTE) + ""
				+ cl.get(Calendar.SECOND);
		if (CPSUtils.isNullOrEmpty(schedulereportsBean.getParam())) {
			schedulereportsBean.setParam("schedulereportshome");
		}
		dbsession = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		if(CPSUtils.compareStrings("financePaySlipHome", schedulereportsBean.getParam()) || CPSUtils.compareStrings("financePaySlipHomeNew", schedulereportsBean.getParam())) {
			schedulereportsBean.setSfid((String)session.getAttribute("Labid"));
			schedulereportsBean.setEmployeeName("");
			viewName = "financePaySlip";
			mav = new ModelAndView(viewName, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
		}
	  if (CPSUtils.compareStrings("schedulereportshome", schedulereportsBean.getParam())) {
			log.debug("ScheduleReportsController --> onSubmit --> param=schedulereportshome");
		//session.setAttribute("loanTypeList", payBillMasterDAO.getLoanMasterList());
		schedulereportsBean.setPaySlipYearList(payBillDAO.getPaySlipYearList());
		//schedulereportsBean.setCategories(payBillDAO.getOTCategoryList());
		session.setAttribute("OTCategories", schedulereportsBean.getCategories());
		schedulereportsBean = scheduleReportsBusiness.getScheduleTypes(schedulereportsBean);
		//schedulereportsBean = scheduleReportsBusiness.getScheduleReportHome(schedulereportsBean, request);
		viewName = "ScheduleReports";
		mav = new ModelAndView(viewName, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	}
	  //new code for schedule reports
	  if(CPSUtils.compareStrings("scheduleReportsType", schedulereportsBean.getParam())){
		  schedulereportsBean = scheduleReportsBusiness.getScheduleReportHome(schedulereportsBean, request);
		  viewName = "ScheduleReportsList";
		  mav = new ModelAndView(viewName, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	  }
	  //data validation report
	  if(CPSUtils.compareStrings("dataValidationReport", schedulereportsBean.getParam())){
		  viewName = "dataValidationReport";
		  mav = new ModelAndView(viewName, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	  }
	  if(CPSUtils.compareStrings("payValidationReport", schedulereportsBean.getParam())){
		  viewName = "payValidationReport";
		  mav = new ModelAndView(viewName, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	  }
	  else if(CPSUtils.compareStrings("ireportShedulereports", schedulereportsBean.getParam())){
		 log.debug("ScheduleReportsController --> onSubmit --> param=ireportShedulereports");
	    schedulereportsBean.setValue1(request.getParameter("categoryID"));
	    schedulereportsBean.setValue2(schedulereportsBean.getSchedulemonth());
	    schedulereportsBean.setParameter1("categoryID");
	    schedulereportsBean.setParameter2("schedulemonth");
		if(CPSUtils.compareStrings("HBA PRINCIPLE", request.getParameter("reportType"))){		   
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/HBAPrincipleSchedule.jasper");
		    schedulereportsBean.setPdfFile("/HBA_Principle_Schedule" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("HBA INTEREST", request.getParameter("reportType"))){		   
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/HBASchedule.jasper");
		    schedulereportsBean.setPdfFile("/HBA_Interest_Schedule" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("GPF", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/GPFSchedule.jasper");
		    schedulereportsBean.setPdfFile("/GPF_Schedule" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("INCOME TAX", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/IncomeTaxSchedule.jasper");
		    schedulereportsBean.setPdfFile("/IncomeTax_Schedule" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("CGHS", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/CGHSSchedule.jasper");
		    schedulereportsBean.setPdfFile("/CGHS_Schedule" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("CGEIS", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/CGEISSchedule.jasper");
		    schedulereportsBean.setPdfFile("/CGEIS_Schedule" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("LFCHARGES", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/LFChargesSchedule.jasper");
		    schedulereportsBean.setPdfFile("/LFCharges_Schedule" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("PROF TAX", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/PISchedule.jasper");
		    schedulereportsBean.setPdfFile("/PI_Schedule" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("Car Principle", request.getParameter("reportType"))){
				schedulereportsBean.setJasperFile("/jasper/ScheduleReports/CarPrincipleConveyance.jasper");
			    schedulereportsBean.setPdfFile("/carPrincipleConveyance" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("Car Interest", request.getParameter("reportType"))){
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/CarConveyance.jasper");
		    schedulereportsBean.setPdfFile("/carInterestConveyance" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("FESTIVAL", request.getParameter("reportType"))){
				schedulereportsBean.setJasperFile("/jasper/ScheduleReports/festConveyance.jasper");
			    schedulereportsBean.setPdfFile("/festConveyance" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("PC PRINCIPLE", request.getParameter("reportType"))){
				schedulereportsBean.setJasperFile("/jasper/ScheduleReports/pcPrincipleConveyance.jasper");
			    schedulereportsBean.setPdfFile("/pcPrincipalConveyance" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("PC INTEREST", request.getParameter("reportType"))){
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/pcConveyance.jasper");
		    schedulereportsBean.setPdfFile("/pcInterestConveyance" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("Cycle", request.getParameter("reportType"))){
				schedulereportsBean.setJasperFile("/jasper/ScheduleReports/cycleConveyance.jasper");
			    schedulereportsBean.setPdfFile("/cycleConveyance" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("Scooter Principle", request.getParameter("reportType"))){
				schedulereportsBean.setJasperFile("/jasper/ScheduleReports/scooterPrincipleConveyance.jasper");
			    schedulereportsBean.setPdfFile("/scooterPrincipleConveyance" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("Scooter Interest", request.getParameter("reportType"))){
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/scooterConveyance.jasper");
		    schedulereportsBean.setPdfFile("/scooterInterestConveyance" + datetime+ ".pdf");
	   }else if(CPSUtils.compareStrings("REGULAR PAYBILL", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/regularPaybill.jasper");
		    schedulereportsBean.setPdfFile("/regularPaybill" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("PAYSLIP", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/EmpPaySlip.jasper");
		    schedulereportsBean.setPdfFile("/EmployeePaySlip" + datetime+ ".pdf");
		}
		else if(CPSUtils.compareStrings("CUTTING SHEET(CASH STATEMENT)", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/CashStatement.jasper");
		    schedulereportsBean.setPdfFile("/CashStatement" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("NPS", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/nps4.jasper");
		    schedulereportsBean.setPdfFile("/NPSSchedule" + datetime+ ".pdf");
		//added by naagendra.v    
		}else if(CPSUtils.compareStrings("N-GRAMS FORM", request.getParameter("reportType"))){
		    schedulereportsBean.setJasperFile("/jasper/ScheduleReports/NPSSchedule.jasper");
		    schedulereportsBean.setPdfFile("/N-grams-form" + datetime+ ".pdf");
		// end by naagendra.v
		}else if (CPSUtils.compareStrings("incomeTaxSlip", request.getParameter("reportType"))) {
			//schedulereportsBean.setJasperFile("/jasper/ScheduleReports/IncomeTaxSlip.jasper");
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/iTaxMain.jasper");
			schedulereportsBean.setPdfFile("/incomeTaxSlip" + datetime+ ".pdf");
		}else if(CPSUtils.compareStrings("incomeTaxSlipFinance", request.getParameter("reportType"))){
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/iTaxMain.jasper");
			schedulereportsBean.setPdfFile("/incomeTaxSlip" + datetime+ ".pdf");
		}
		else if (CPSUtils.compareStrings("daArrearsDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/daArrearsDetails.jasper");
			schedulereportsBean.setPdfFile("/daArrearsDetails" + datetime+ ".pdf");
		}else if (CPSUtils.compareStrings("catList", request.getParameter("reportType"))) {
				schedulereportsBean.setJasperFile("/jasper/ScheduleReports/payEmpCategoryWiseList.jasper");
				schedulereportsBean.setPdfFile("/payEmpCategoryWiseList" + datetime+ ".pdf");
		}
		else if (CPSUtils.compareStrings("printCategoryDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_emp_category_master.jasper");
			schedulereportsBean.setPdfFile("/printCategoryDetails" + datetime+ ".pdf");
	    }
		else if (CPSUtils.compareStrings("printDAMasterDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_dearness_allowance_master.jasper");
			schedulereportsBean.setPdfFile("/printDAMasterDetails" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printPayBillAllwConfDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_allowances_details.jasper");
			schedulereportsBean.setPdfFile("/printPayBillAllwConfDetails" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printPayBandDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_payband_master.jasper");
			schedulereportsBean.setPdfFile("/printPayBandDetails" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printPayscaleDesignation", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_designation_mapping.jasper");
			schedulereportsBean.setPdfFile("/printPayscaleDesignation" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printPaybillCghsMaster", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/pay_CGHS_details_master.jasper");
			schedulereportsBean.setPdfFile("/printPaybillCghsMaster" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printCgeisMasterDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_cgeis_master.jasper");
			schedulereportsBean.setPdfFile("/printCgeisMasterDetails" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printPayProfTaxDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_emp_category_master.jasper");
			schedulereportsBean.setPdfFile("/printPayProfTaxDetails" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printRSMasterDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_resd_security_master.jasper");
			schedulereportsBean.setPdfFile("/printRSMasterDetails" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("printQuarterTypeDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_quarters_type_master.jasper");
			schedulereportsBean.setPdfFile("/printQuarterTypeDetails" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("familyPlanning", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_family_planning_master.jasper");
			schedulereportsBean.setPdfFile("/familyPlanning" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("professionalTax", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_professional_tax_master.jasper");
			schedulereportsBean.setPdfFile("/professionalTax" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("variableIncrement", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_variable_increment_master.jasper");
			schedulereportsBean.setPdfFile("/variableIncrement" + datetime+ ".pdf");
	    }
	    else if (CPSUtils.compareStrings("travellingAllowance", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/Pay_tra_master.jasper");
			schedulereportsBean.setPdfFile("/travellingAllowance" + datetime+ ".pdf");
	    } else if (CPSUtils.compareStrings("printLicenceFeeDetails", request.getParameter("reportType"))) {
			schedulereportsBean.setJasperFile("/jasper/ScheduleReports/pay_licence_fee_master.jasper");
			schedulereportsBean.setPdfFile("/printLicenceFeeDetails" + datetime+ ".pdf");
	    }
	    else if(CPSUtils.compareStrings("OT", request.getParameter("reportType")))
	    {
	    	schedulereportsBean.setJasperFile("/jasper/ScheduleReports/OT_sanctionGrantExtraTime.jasper");
	    	schedulereportsBean.setPdfFile("/printOTDetails" + datetime + ".pdf");
	    }else if(CPSUtils.compareStrings("PAYBILL ACQUITTANCE ROLL", request.getParameter("reportType"))){
	    	schedulereportsBean.setJasperFile("/jasper/ScheduleReports/PayBillAndAcquittanceRoll.jasper");
	    	schedulereportsBean.setPdfFile("/printPayBillAcquittanceRoll" + datetime+".pdf");
	    }
	    else if(CPSUtils.compareStrings("LTC", request.getParameter("reportType"))){
	    	//schedulereportsBean.setJasperFile("/jasper/ScheduleReports/LTCChargesSchedule.jasper");
	    	schedulereportsBean.setJasperFile("/jasper/ScheduleReports/LTCSchedule.jasper");
	    	//schedulereportsBean.setPdfFile("/LTCCharges_Schedule" + datetime+".pdf");
	    	schedulereportsBean.setPdfFile("/LTCSchedule" + datetime+".pdf");
	    }
	    else if(CPSUtils.compareStrings("TADA", request.getParameter("reportType"))){
	    	schedulereportsBean.setJasperFile("/jasper/ScheduleReports/TADASchedule.jasper");
	    	//schedulereportsBean.setJasperFile("/jasper/ScheduleReports/TADAChargesSchedule.jasper");
	    	//schedulereportsBean.setPdfFile("/TADACharges_Schedule" + datetime+".pdf");
	    	schedulereportsBean.setPdfFile("/TADASchedule" + datetime+".pdf");
	    }
		
		schedulereportsBean = scheduleReportsBusiness.getPdfReport(schedulereportsBean, request, dbsession,response);
		mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);

	}else if(CPSUtils.compareStrings("getFinancePaySlip", schedulereportsBean.getParam())) {
		 schedulereportsBean.setParameter1("month");
	     schedulereportsBean.setParameter2("sfid");
	     schedulereportsBean.setJasperFile("/jasper/ScheduleReports/payslip2.jasper");
		 schedulereportsBean.setPdfFile("/financePaySlip" + datetime+ ".pdf");
	    
	    schedulereportsBean = scheduleReportsBusiness.getPdfReport(schedulereportsBean, request, dbsession,response);
		mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	}else if(CPSUtils.compareStrings("getFinancePaySlipNew", schedulereportsBean.getParam())) {
		 schedulereportsBean.setParameter1("month");
	     schedulereportsBean.setParameter2("sfid");
	     schedulereportsBean.setJasperFile("/jasper/ScheduleReports/EmpPaySlip.jasper");
		 schedulereportsBean.setPdfFile("/EmployeePaySlip" + datetime+ ".pdf");
	    
	    schedulereportsBean = scheduleReportsBusiness.getPdfReport(schedulereportsBean, request, dbsession,response);
		mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	}else if(CPSUtils.compareStrings("incomeTaxForm16Report", schedulereportsBean.getParam())){
	     schedulereportsBean.setJasperFile("/jasper/ScheduleReports/incometax_form16.jasper");
		 schedulereportsBean.setPdfFile("/incomeTaxForm16Slip" + datetime+ ".pdf");
	     
	     schedulereportsBean = scheduleReportsBusiness.getPdfReport(schedulereportsBean, request, dbsession,response);
		 mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	}else if(CPSUtils.compareStrings("payBillComparisionReport", schedulereportsBean.getParam())){
		log.debug("ScheduleReportsController --> onSubmit --> param=payBillComparisionReport");
		if(CPSUtils.compareStrings("CreditsReport", request.getParameter("reportType"))){
			 schedulereportsBean.setJasperFile("/jasper/ScheduleReports/payCreditCompareReport.jasper");
			 schedulereportsBean.setPdfFile("/payBill_Credit" + datetime+ ".pdf");
		}
		schedulereportsBean = scheduleReportsBusiness.getPdfReport(schedulereportsBean, request, dbsession,response);
		mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
	}
	} catch (Exception e) {
		throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
	}
	finally{
		
	}
    return mav;
    
}
@RequestMapping(value = "/validateReport.htm", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=*/*")
public ModelAndView searchData(@ModelAttribute(CPSConstants.SCHEDULEREPORTS) ScheduleReportsBean schedulereportsBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception 
{
	String viewName = null;
	 ModelAndView mav=null;
	 Session dbsession = null;
	try
	{
		dbsession = hibernateUtils.getSession();
		Calendar cl = Calendar.getInstance();
		String datetime = cl.get(Calendar.YEAR) + "" + cl.get(Calendar.MONTH) + 1 + "" + cl.get(Calendar.DATE) + "" + cl.get(Calendar.HOUR) + "" + cl.get(Calendar.MINUTE) + ""
		+ cl.get(Calendar.SECOND);
		if(CPSUtils.compareStrings("ireportPayValidationDetails", schedulereportsBean.getParam())){
			 log.debug("ScheduleReportsController --> onSubmit --> param=ireportPayValidationDetails");
			 if(CPSUtils.compareStrings("payGpfDetails", request.getParameter("reportType"))){	
				   schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Gpf_Data.jasper");
				    schedulereportsBean.setPdfFile("/Check_Gpf" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payQuarterDetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Quarter_Details_Pay.jasper");
				    schedulereportsBean.setPdfFile("/Quarter_Details" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payLoanDetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Loan_Details_For_Pay.jasper");
				    schedulereportsBean.setPdfFile("/Loan_Details" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payEmpCategoryDetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Pay_Category_Wise_Employee.jasper");
				    schedulereportsBean.setPdfFile("/CategoryWise_Employee" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payEmpCghsDetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Emp_Pay_Cghs.jasper");
				    schedulereportsBean.setPdfFile("/Emp_Cghs" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payEmpFPADetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Emp_Pay_FPA_Master.jasper");
				    schedulereportsBean.setPdfFile("/Emp_FPA" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payEmpTRADetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Emp_Pay_TRA_Master.jasper");
				    schedulereportsBean.setPdfFile("/Emp_TRA" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payEmpTwoAIDetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Emp_Pay_TwoAddInc_master.jasper");
				    schedulereportsBean.setPdfFile("/Emp_TwoAI" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payEmpVarIncDetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Emp_Pay_VariableIncre_Master.jasper");
				    schedulereportsBean.setPdfFile("/Emp_VarInc" + datetime+ ".pdf");
				}else if(CPSUtils.compareStrings("payEmpDADetails", request.getParameter("reportType"))){
				    schedulereportsBean.setJasperFile("/jasper/ValidationReports/Check_Emp_Pay_DA_Master.jasper");
				    schedulereportsBean.setPdfFile("/Emp_TwoAI" + datetime+ ".pdf");
				}
			 
			 schedulereportsBean = scheduleReportsBusiness.getPdfReport(schedulereportsBean, request, dbsession,response);
			  mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
			}
		if(CPSUtils.compareStrings("printDataValidationReports", schedulereportsBean.getParam())){
			 log.debug("ScheduleReportsController --> onSubmit --> param=printDataValidationReports");
			 if(CPSUtils.compareStrings("addressDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Address_Entry.jasper");
				 schedulereportsBean.setPdfFile("/Address_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("presentaddress", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/EMP_PRESENT_ADDRESS.jasper");
				 schedulereportsBean.setPdfFile("/presentaddress" + datetime+ ".pdf");
			 }
			 else if(CPSUtils.compareStrings("nullMotherTongueDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Mother_Tongue.jasper");
				 schedulereportsBean.setPdfFile("/NullMotherTongue_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("empMotherTongueDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_View_MotherTongue_Data.jasper");
				 schedulereportsBean.setPdfFile("/EmpMotherTongue_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("phyHandicappedDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Phy_Handicapped.jasper");
				 schedulereportsBean.setPdfFile("/PhyHandicapped_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("exserviceManDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_ExServiceMan_Data.jasper");
				 schedulereportsBean.setPdfFile("/ExserviceMan_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("nominalRoleDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_NominalRole_Data.jasper");
				 schedulereportsBean.setPdfFile("/NominalRole_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("leaveManualEntryDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Leave_Manual_Entry.jasper");
				 schedulereportsBean.setPdfFile("/LeaveEntry_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("loanDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Loan_Details.jasper");
				 schedulereportsBean.setPdfFile("/Loan_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("nearestRailwayDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Ltc_Addr_Nrest_Railway.jasper");
				 schedulereportsBean.setPdfFile("/Railway_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("missingPayDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Missing_Pay.jasper");
				 schedulereportsBean.setPdfFile("/MissingPay_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("missingBasicPayDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Missing_Pay_Basic.jasper");
				 schedulereportsBean.setPdfFile("/MissingBasicPay_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("missingGradePayDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Missing_Pay_Grade.jasper");
				 schedulereportsBean.setPdfFile("/MissingGradePay_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("payEntryDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Pay_Entry.jasper");
				 schedulereportsBean.setPdfFile("/PayEntry_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("empQuarterDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Emp_Quarter_Details.jasper");
				 schedulereportsBean.setPdfFile("/EmpQuarter_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("empTaskHolderDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Task_Holder_Employee.jasper");
				 schedulereportsBean.setPdfFile("/EmpTaskHolder_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("taskHolderDesigDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Holder_Designations.jasper");
				 schedulereportsBean.setPdfFile("/TaskHolder_Desig_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("hindiIncentiveDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Hindi_Incentive_Details.jasper");
				 schedulereportsBean.setPdfFile("/HindiIncentive_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareStrings("empQualificationDetails", request.getParameter("reportType"))){
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Emp_Qualification.jasper");
				if(schedulereportsBean.getRequestType() != null && schedulereportsBean.getRequestType().equalsIgnoreCase("excel")){
					 	schedulereportsBean.setExportedFile("/EmpQualification_Details" +datetime + ".xls");
						schedulereportsBean = scheduleReportsBusiness.getExcelReport(schedulereportsBean, request, dbsession, response);
					}else{
						schedulereportsBean.setPdfFile("/EmpQualification_Details" + datetime+ ".pdf");
					}
					
				 /*schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Emp_Qualification.jasper");
				 schedulereportsBean.setPdfFile("/EmpQualification_Details" + datetime+ ".pdf");*/
			 }else if(CPSUtils.compareString("leaveBalanceValidation", request.getParameter("reportType"))) {
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/leaveDataValidation.jasper");
				 schedulereportsBean.setPdfFile("/Leave-Balance_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareString("empTeleNoDetails", request.getParameter("reportType"))) {
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/Check_Emp_TeleNo_Details.jasper");
				 schedulereportsBean.setPdfFile("/Emp_TeleNo_Details" + datetime+ ".pdf");
			 }else if(CPSUtils.compareString("empPermanentAddrDetails", request.getParameter("reportType"))) {
				 schedulereportsBean.setJasperFile("/jasper/EssValidationReports/PIS_TEST_AUDIT_EMP_PRESENT_ADDRESS.jasper");
				 schedulereportsBean.setPdfFile("/Emp_Permanent_Address_Details" + datetime+ ".pdf");
			 }
			 schedulereportsBean = scheduleReportsBusiness.getPdfReport(schedulereportsBean, request, dbsession,response);
			  mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.SCHEDULEREPORTS, schedulereportsBean);
		}
	}catch (Exception e) 
	{
		throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
	} 
	return mav;
}
}
