package com.callippus.web.controller.reports;

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

import com.callippus.web.beans.reports.ReportsBean;
import com.callippus.web.business.reports.ReportsBusiness;
import com.callippus.web.business.requestprocess.TadaRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/report.htm")
@SessionAttributes
public class ReportsController {
	private static Log log = LogFactory.getLog(ReportsController.class);
	@Autowired
	private ReportsBusiness reportsBusiness;
	@Autowired
	private HibernateUtils hibernateUtils;
	@Autowired
	private TadaRequestProcess tadaRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.REPORTS) ReportsBean reportBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Session dbsession = null;
		String viewName = null;
		ModelAndView mav = null;
		try {
			ErpAction.sessionChecks(request);
			response.setHeader("no-cache","no-store");
			request.getHeader("Cache-Control");
			Calendar cl = Calendar.getInstance();
			String datetime = cl.get(Calendar.YEAR) + "" + cl.get(Calendar.MONTH) + 1 + "" + cl.get(Calendar.DATE) + "" + cl.get(Calendar.HOUR) + "" + cl.get(Calendar.MINUTE) + ""
					+ cl.get(Calendar.SECOND);

			if (CPSUtils.isNullOrEmpty(reportBean.getParam())) {
				reportBean.setParam("categoryreport");
			}
			dbsession = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (CPSUtils.compareStrings("categoryreport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=categoryreport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
//				viewName = "R_CategoryWiseReport";
				viewName = "PisMiscReports";              //This merged as single page pisMiscReports
				
	 			mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
	 			mav.addObject("categoryReportHomeList", reportBean.getList());
			} else if (CPSUtils.compareStrings("areaofDeployment", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=areaofDeployment");
			 	// reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
				viewName = "AreaOfDeployment";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("pisReportsHome", reportBean.getParam())) {                  //pisReporstHome screen
				log.debug("ReportsController --> onSubmit --> param=pisReportsHome");
				viewName = "PisReportsHome";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("pisMiscReportsHome", reportBean.getParam())) {                  //pisMiscReporstHome screen
				log.debug("ReportsController --> onSubmit --> param=pisMiscReportsHome");
				reportBean = reportsBusiness.getPisMiscReportHome(reportBean, request);
				viewName = "PisMiscReports";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("statusReports", reportBean.getParam())) {                  //statusReports screen orgHierarchyReport
				log.debug("ReportsController --> onSubmit --> param=statusReports");
				viewName = "statusReports";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("orgHierarchyReport", reportBean.getParam())) {                  //statusReports screen orgHierarchyReport
				log.debug("ReportsController --> onSubmit --> param=orgHierarchyReport");
	/*	request.getParameter("orgHierarchyReport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);*/
				viewName = "OrgHierarchyReport";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("addressReports", reportBean.getParam())) {                  //statusReports screen orgHierarchyReport
				log.debug("ReportsController --> onSubmit --> param=orgHierarchyReport");
				viewName = "addressReports";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("nominalRoleReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=nominalRoleReport");
				// reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
				viewName = "NominalRoleReport";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("inventoryreport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=inventoryreport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
				viewName = "InventoryReport";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("phVacant", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=EmpCommunityReport");
				viewName = "DisabilitiesAnnualReport";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("designationwiseReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=designationwiseReport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
//				viewName = "R_DesignationWiseReport";  
				viewName = "PisMiscReports";                //This merged as single page pisMiscReports
				//request.setAttribute("designationReportHomeList", reportBean.getList());
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
				//mav.addObject("designationReportHomeList", reportBean.getList());
 			} else if (CPSUtils.compareStrings("printITConfigDetails", reportBean.getParam())) {   //updationAddrReportThis is add for IncomeTaxSavingDetails Report (P)-2412
				log.debug("ReportsController --> onSubmit --> param=printITConfigDetails");
				reportBean.setJasperFile("/jasper/PaySavingsReport.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);	
			}else if (CPSUtils.compareStrings("updationAddrReport", reportBean.getParam())) {   //This is add for AddrUpdation Report (P)-2412
				log.debug("ReportsController --> onSubmit --> param=updationAddrReport");
				reportBean.setJasperFile("/jasper/ChangeOfHomeTown_Report.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);	
			}else if (CPSUtils.compareStrings("retirementWiseReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=retirementWiseReport");
				reportBean.setJasperFile("/jasper/RetirementReport.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("financeStatusActiveReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=financeStatusActiveReport");
				reportBean.setJasperFile("/jasper/financeStatusActiveReport.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("AgeReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=AgeReport");
				reportBean.setJasperFile("/jasper/EMP_AGE_Report.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			
			//NARESH
			else if (CPSUtils.compareStrings("PRESENTADDRESSREPORT", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=PRESENTADDRESSREPORT");
				reportBean.setJasperFile("/jasper/PRESENTADDRESSREPORT.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("CGHSREPORT", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=CGHSREPORT");
				reportBean.setJasperFile("/jasper/CGHSREPORT.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("addressreport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=addressreport");
				reportBean.setJasperFile("/jasper/addressreport.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			
			
			else if (CPSUtils.compareStrings("PersonalInfo", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=addressreport");
				reportBean.setJasperFile("/jasper/PersonalInformationEmployee.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("QualificationReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=QualificationReport");
				reportBean.setJasperFile("/jasper/QualificationReport.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("nominalReport", reportBean.getParam())) {                //asl nominal report
				log.debug("ReportsController --> onSubmit --> param=nominalReport");
				reportBean.setJasperFile("/jasper/Asl_Nominal_report.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("nominalReportXl", reportBean.getParam())) {                //asl nominal report EXCEL
				log.debug("ReportsController --> onSubmit --> param=nominalReport");
				reportBean.setJasperFile("/jasper/Asl_Nominal_report.jasper");
				
				reportBean.setExportedFile("/Asl_Nominal_Report" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
				
			}
			else if (CPSUtils.compareStrings("divisionReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=divisionReport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
				viewName = "DivisionReport";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("empDOJwiseReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=empDOJwiseReport");
//				viewName = "R_EmpDOJReport";
				viewName = "PisMiscReports";                    //This merged as single page pisMiscReports
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("EmpGroupwiseReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=EmpGroupwiseReport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
//				viewName = "R_EmpGroupwiseReport";
				viewName = "PisMiscReports";              //This merged as single page pisMiscReports
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("EmpReligionwiseReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=EmpReligionwiseReport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
//				viewName = "R_EmpReligionReport";
				viewName = "PisMiscReports"; //This merged as single page pisMiscReports
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("PHEmpReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=PHEmpReport");
//				viewName = "R_PHEmpReport";
				viewName = "PisMiscReports"; //This merged as single page pisMiscReports
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("EmpReservationwiseReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=EmpReservationwiseReport");
//				viewName = "R_ReservationWiseEmpReport";
				viewName = "PisMiscReports"; //This merged as single page pisMiscReports
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("EmpCommunityReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=EmpCommunityReport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
//				/*viewName = "R_CommunityWiseEmpReport";*/
				viewName = "PisMiscReports"; //This merged as single page pisMiscReports
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("EmpLastModifyReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=EmpLastModifyReport");
//				viewName = "R_LastModifiedEmpReport";
                viewName = "PisMiscReports"; //This merged as single page pisMiscReports
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("EmpExperienceReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=EmpExperienceReport");
				reportBean.setJasperFile("/jasper/ExperienceReportDRDO.jasper");
				//reportBean.setExportedFile("/HalfYearlyAllPhReport" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (request.getParameter("param") != null && request.getParameter("param").equals("EmpHierarchyHome")) {
				log.debug("ReportsController --> onSubmit --> param=EmpHierarchyHome");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
				/*if (!CPSUtils.isNull(reportBean.getType()) && CPSUtils.compareStrings(reportBean.getType(), "Physical")) {   
					viewName = "EmployeePhysicalHierarchy";
				} else {
					viewName = "EmployeeHierarchyReport";
				}*/
				viewName = "OrgHierarchyReport";
			
				
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
				// for custom report
			} else if (CPSUtils.compareStrings("customreport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=customreport");
				reportBean = reportsBusiness.customReportHomeDate(reportBean);
				mav = new ModelAndView("CustomReport", CPSConstants.REPORTS, reportBean);
			}else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportareaofDeployment")) {
				log.debug("ReportsController --> onSubmit --> param=ireportareaofDeployment");
				reportBean.setJasperFile("/jasper/drds.jasper");
				//reportBean.setExportedFile("/AreaOfDeployment" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (request.getParameter("param") != null && request.getParameter("param").equals("passportReport")) {
				log.debug("ReportsController --> onSubmit --> param=passportReport");
				reportBean.setJasperFile("/jasper/passportApplicationForm.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportnominalRolePdf")) {
				log.debug("ReportsController --> onSubmit --> param=ireportnominalRolePdf");
				reportBean.setJasperFile("/jasper/Admin_NominalRole.jasper");
				//reportBean.setExportedFile("/Admin_NominalRole" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("HalfYearAllPhReport")) {
				reportBean.setJasperFile("/jasper/HalfYearlyAllPhReport.jasper");
				//reportBean.setExportedFile("/HalfYearlyAllPhReport" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("phVacantReport")) {
				reportBean.setValue1(request.getParameter("fromDate"));
				reportBean.setValue2(request.getParameter("toDate"));
				reportBean.setParameter1("fromDate");
				reportBean.setParameter2("toDate");     //these above lines are added for parameters
				reportBean.setJasperFile("/jasper/DisabilitiesInServices.jasper");
				//reportBean.setExportedFile("/DisabilitiesInServices" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}//This is letternumberreport.
			else if(request.getParameter("param") != null && request.getParameter("param").equals("letterNumberReport") ){
				log.debug("MasterDataController --> onReport --> param=letterNumberReport");
				reportBean.setJasperFile("/jasper/letterNo_report.jasper");
				reportsBusiness.getPdfReport(reportBean,request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (request.getParameter("param") != null && request.getParameter("param").equals("quarterReport1")) {
				if(reportBean.getQuarterType().equalsIgnoreCase("occupied"))
					reportBean.setJasperFile("/jasper/occupation.jasper");
				else if(reportBean.getQuarterType().equalsIgnoreCase("vacated"))
					reportBean.setJasperFile("/jasper/vacation.jasper");
				else if(reportBean.getQuarterType().equalsIgnoreCase("alloted"))
					reportBean.setJasperFile("/jasper/QtrAllotmentIONForm.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpCategorywise")) {
				reportBean.setValue1(request.getParameter("category"));
				reportBean.setParameter1("CATEGORY");
				reportBean.setJasperFile("/jasper/R_Emp_Categorywise.jasper");
				//reportBean.setExportedFile("/R_Emp_Categorywise_" + request.getParameter("category") + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportInventorywise")) {
				reportBean.setValue1(request.getParameter("inventoryNo"));
				reportBean.setParameter1("inventoryNo");
				reportBean.setJasperFile("/jasper/InventoryReport.jasper");
				//reportBean.setExportedFile("/InventoryReport" + request.getParameter("inventoryNo") + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("Designationwise")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportEmpDesignationwise Pdf Report :::::Designation Id = " + request.getParameter("designation"));
				reportBean.setValue1(request.getParameter("designation"));
				reportBean.setParameter1("DESIG");
				reportBean.setJasperFile("/jasper/DivisionReport.jasper");
				//reportBean.setExportedFile("/DivisionReport_" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpDesignationwise")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportEmpDesignationwise Pdf Report :::::Designation Id = " + request.getParameter("designation"));
				reportBean.setValue1(request.getParameter("designation"));
				reportBean.setParameter1("DESIG");
				reportBean.setJasperFile("/jasper/R_Emp_Designationwise.jasper");
				//reportBean.setExportedFile("/R_Emp_Designationwise_" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpDOJwsie")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportEmpDOJwsie Pdf Report");
				log.debug("From Date ---->" + request.getParameter("fromDate") + "To Date" + request.getParameter("toDate"));
				reportBean.setValue1(request.getParameter("fromDate"));
				reportBean.setValue2(request.getParameter("toDate"));
				reportBean.setParameter1("fromDate");
				reportBean.setParameter2("toDate");
				reportBean.setJasperFile("/jasper/R_Emp_DOJwise.jasper");
				//reportBean.setExportedFile("/R_Emp_DOJwise_" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpGroupwise")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportEmpGroupwise Pdf Report ::::Group Id= " + request.getParameter("groupName"));
				reportBean.setValue1(request.getParameter("groupName"));
				reportBean.setParameter1("GROUPNAME");
				reportBean.setJasperFile("/jasper/R_Emp_Groupwise.jasper");
				//reportBean.setExportedFile("/R_Emp_Groupwise_" + request.getParameter("groupName") + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportPHEmp")) {
				log.debug("ReportsController --> onSubmit --> param=ireportPHEmp Pdf Report");
				reportBean.setJasperFile("/jasper/R_Emp_PHEmployees.jasper");
				//reportBean.setExportedFile("/R_Emp_PHEmployees" + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportReligionwise")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportReligionwise Pdf Report :::: Religion Id=" + request.getParameter("religion"));
				reportBean.setValue1(request.getParameter("religion"));
				reportBean.setParameter1("RELIGION");
				reportBean.setJasperFile("/jasper/R_Emp_Religion.jasper");
				//reportBean.setExportedFile("/R_Emp_Religion_" + request.getParameter("religion") + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportReservationwise")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportReservationwise Pdf Report ");
				if (CPSUtils.compareStrings(reportBean.getType(), "reservationReport")) {
					reportBean.setJasperFile("/jasper/R_Emp_Reservationwise.jasper");
					//reportBean.setExportedFile("/R_Emp_Reservationwise_" + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "catVacant")) {
					reportBean.setJasperFile("/jasper/CategoryVacancyPositionReport.jasper");
					//reportBean.setExportedFile("/CategoryVacancyPositionReport" + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "desigVacant")) {
					reportBean.setJasperFile("/jasper/DesignationVacancyPositionReport.jasper");
					//reportBean.setExportedFile("/DesignationVacancyPositionReport" + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "phVacant")) {
					reportBean.setJasperFile("/jasper/DisabilitiesInServices.jasper");
					//reportBean.setExportedFile("/DisabilitiesInServices" + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "empDetails")) {
					reportBean.setJasperFile("/jasper/AnnualNominalRollReport.jasper");
					//reportBean.setExportedFile("/AnnualNominalRollReport" + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "halfYearEnding")) {
					reportBean.setJasperFile("/jasper/HalfYearEndingReport.jasper");
					//reportBean.setExportedFile("/HalfYearEndingReport" + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "totalVacant")) {
					reportBean.setValue1(request.getParameter("fromDate"));
					reportBean.setParameter1("fromDate");
					reportBean.setJasperFile("/jasper/R_Total_Vacant_DetailedReport.jasper");
					//reportBean.setExportedFile("/R_Total_Vacant_DetailedReport" + request.getParameter("fromDate") + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "annualYearEnding")) {
					reportBean.setJasperFile("/jasper/AnnualYearRrport.jasper");
					//reportBean.setExportedFile("/AnnualYearRrport" + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "ledgerReport")) {
					reportBean.setJasperFile("/jasper/LedgerReport.jasper");
					//reportBean.setExportedFile("/LedgerReport" + datetime + ".pdf");
				}else if (CPSUtils.compareStrings(reportBean.getType(), "hrdgAnnual")) {
					reportBean.setJasperFile("/jasper/AnnualNominalRollReport_HRDG.jasper");
					//reportBean.setExportedFile("/LedgerReport" + datetime + ".pdf");
				}
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportCommunitywise")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportCommunitywise Pdf Report :::: Community Id=" + request.getParameter("community"));
				reportBean.setValue1(request.getParameter("community"));
				reportBean.setParameter1("COMMUNITY_ID");
				reportBean.setJasperFile("/jasper/R_Emp_Communitywise.jasper");
				//reportBean.setExportedFile("/R_Emp_Communitywise_" + request.getParameter("community") + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpLastModified")) {
				log.debug("ReportsController --> onSubmit Start --> param=ireportEmpLastModified Pdf Report");
				log.debug("From Date ---->" + request.getParameter("fromDate") + "To Date" + request.getParameter("toDate"));
				reportBean.setValue1(request.getParameter("fromDate"));
				reportBean.setValue2(request.getParameter("toDate"));
				reportBean.setParameter1("fromDate");
				reportBean.setParameter2("toDate");
				if (CPSUtils.compareStrings(reportBean.getType(), "lastModified")) {
					reportBean.setJasperFile("/jasper/R_Emp_LastModified.jasper");
					//reportBean.setExportedFile("/R_Emp_LastModified_" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "scStObc")) {
					reportBean.setJasperFile("/jasper/R_SC_ST_OBC_Report.jasper");
					//reportBean.setExportedFile("/R_SC_ST_OBC_Report" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "HalfYearPhReport")) {
					reportBean.setJasperFile("/jasper/HalfYearlyPhReport.jasper");
					//reportBean.setExportedFile("/HalfYearlyPhReport" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "HalfYearAllPhReport")) {
					reportBean.setJasperFile("/jasper/HalfYearlyAllPhReport.jasper");
					//reportBean.setExportedFile("/HalfYearlyAllPhReport" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "CashPurchaseDemand")) {
					reportBean.setJasperFile("/jasper/cashpurchasedemand.jasper");
					//reportBean.setExportedFile("/cashpurchasedemand" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "CashPurchaseVoucher")) {
					reportBean.setJasperFile("/jasper/CashPurchaseVoucher.jasper");
					//reportBean.setExportedFile("/CashPurchaseVoucher" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "DOJDRDO")) {
					reportBean.setJasperFile("/jasper/R_Emp_Doj_DRDO.jasper");
					//reportBean.setExportedFile("/DateOFJoinInDRDO" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				}
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("empHierarchyPdf")) {
				log.debug("ReportsController --> onSubmit Start --> param=empHierarchyPdf");
				log.debug("Role Instance Id ---->" + request.getParameter("roleInstanceId"));
				reportBean.setValue1(request.getParameter("roleInstanceId"));
				reportBean.setParameter1("roleInstanceId");
				if (!CPSUtils.isNull(request.getParameter("type")) && CPSUtils.compareStrings(request.getParameter("type"), "Physical")) {
					reportBean.setJasperFile("/jasper/PhysicalHierarchyReport.jasper");
					//reportBean.setExportedFile("/PhysicalHierarchyReport" + request.getParameter("roleInstanceId") + datetime + ".pdf");
				} else {
					reportBean.setJasperFile("/jasper/EmployeeHierarchyReport.jasper");
					//reportBean.setExportedFile("/EmployeeHierarchyReport" + request.getParameter("roleInstanceId") + datetime + ".pdf");
				}
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("empMappingPdf")) {
				log.debug("Role Instance Id ---->" + request.getParameter("roleInstanceId"));
				reportBean.setValue1(request.getParameter("roleInstanceId"));
				reportBean.setParameter1("roleInstanceId");

				reportBean.setJasperFile("/jasper/EmpRolesMappingReport.jasper");
				//reportBean.setExportedFile("/EmpRolesMappingReport" + request.getParameter("roleInstanceId") + datetime + ".pdf");

				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				/* EXCEL REPORTS */
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("customreportpdf")) {
				log.debug("ReportsController --> onSubmit Start --> param=customreportpdf");
				if (request.getParameter("type") != null && request.getParameter("type").equals("showpdf"))
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				else {
					reportBean.setExportedFile("/CustomReport" + datetime + ".pdf");
					reportBean.setXmlFile("/CustomReport" + datetime + ".xml");
					reportsBusiness.getCustomReport(reportBean, request, response);
				}
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpCategorywiseExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportEmpCategorywiseExcel Excel Reports :::: Category Id=" + reportBean.getCategory());
				reportBean.setValue1(request.getParameter("category"));
				reportBean.setParameter1("CATEGORY");
				reportBean.setJasperFile("/jasper/R_Emp_Categorywise.jasper");
				reportBean.setExportedFile("/R_Emp_Categorywise_" + reportBean.getCategory() + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportareaofDeploymentExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportareaofDeploymentExcel Excel Report");
				reportBean.setJasperFile("/jasper/drds.jasper");
				reportBean.setExportedFile("/AreaOfDeployment" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportnominalRoleExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportnominalRoleExcel");
				reportBean.setJasperFile("/jasper/Admin_NominalRole.jasper");
				reportBean.setExportedFile("/Admin_NominalRole" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpCategorywiseExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportEmpCategorywiseExcel Excel Reports :::: Category Id=" + reportBean.getCategory());
				reportBean.setValue1(request.getParameter("category"));
				reportBean.setParameter1("CATEGORY");
				reportBean.setJasperFile("/jasper/R_Emp_Categorywise.jasper");
				reportBean.setExportedFile("/R_Emp_Categorywise_" + reportBean.getCategory() + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpDesignationwiseExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportEmpDesignationwiseExcel Excel Reports :::: Designation Id=" + reportBean.getDesignation());
				reportBean.setValue1(request.getParameter("designation"));
				reportBean.setParameter1("DESIG");
				reportBean.setJasperFile("/jasper/R_Emp_Designationwise.jasper");
				reportBean.setExportedFile("/R_Emp_Designationwise_" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("DesignationExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportEmpDesignationwiseExcel Excel Reports :::: Designation Id=" + reportBean.getDesignation());
				reportBean.setValue1(request.getParameter("designation"));
				reportBean.setParameter1("DESIG");
				reportBean.setJasperFile("/jasper/DivisionReport.jasper");
				reportBean.setExportedFile("/DivisionReport_" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			}
			// if (CPSUtils.compareStrings("balancereport", reportBean.getParam())) {
			// log.debug("ReportsController --> onSubmit --> param=balancereport");
			// reportBean.setJasperFile("/jasper/report4.jasper");
			// reportBean.setExportedFile("/report4_"+datetime+".xls");
			// reportBean=reportsBusiness.getExcelReport(reportBean,request,dbsession,response);
			//				
			// }
			else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportInventoryExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportInventoryExcel Excel Reports :::: Inventory No=" + reportBean.getInventoryNo());
				reportBean.setValue1(request.getParameter("inventoryNo"));
				reportBean.setParameter1("inventoryNo");
				reportBean.setJasperFile("/jasper/InventoryReport.jasper");
				reportBean.setExportedFile("/InventoryReport" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpDOJwsieExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportEmpDOJwsieExcel");
				log.debug("From Date --> " + reportBean.getFromDate() + "To Date ----->" + reportBean.getToDate());
				reportBean.setValue1(request.getParameter("fromDate"));
				reportBean.setValue2(request.getParameter("toDate"));
				reportBean.setParameter1("fromDate");
				reportBean.setParameter2("toDate");
				reportBean.setJasperFile("/jasper/R_Emp_DOJwise.jasper");
				reportBean.setExportedFile("/R_Emp_DOJwise_" + reportBean.getFromDate() + "To" + reportBean.getToDate() + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpGroupwiseExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportEmpGroupwiseExcel Excel Report :::: Group Id=" + reportBean.getGroupName());
				reportBean.setValue1(request.getParameter("groupName"));
				reportBean.setParameter1("GROUPNAME");
				reportBean.setJasperFile("/jasper/R_Emp_Groupwise.jasper");
				reportBean.setExportedFile("/R_Emp_Groupwise_" + reportBean.getGroupName() + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportPHEmpExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportPHEmpExcel Excel Report");
				reportBean.setJasperFile("/jasper/R_Emp_PHEmployees.jasper");
				reportBean.setExportedFile("/R_Emp_PHEmployees" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportReligionwiseExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportReligionwiseExcel Excel Reports ::::Religion Id=" + reportBean.getReligionName());
				reportBean.setValue1(reportBean.getReligionName());
				reportBean.setParameter1("RELIGION");
				reportBean.setJasperFile("/jasper/R_Emp_Religion.jasper");
				reportBean.setExportedFile("/R_Emp_Religion_" + reportBean.getReligionName() + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportReservationwiseExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportReservationwiseExcel Excel Reports ");
				if (CPSUtils.compareStrings(reportBean.getType(), "reservationReport")) {
					reportBean.setJasperFile("/jasper/R_Emp_Reservationwise.jasper");
					reportBean.setExportedFile("/R_Emp_Reservationwise" + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "catVacant")) {
					reportBean.setJasperFile("/jasper/CategoryVacancyPositionReport.jasper");
					reportBean.setExportedFile("/CategoryVacancyPositionReport" + datetime + ".xml");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "desigVacant")) {
					reportBean.setJasperFile("/jasper/DesignationVacancyPositionReport.jasper");
					reportBean.setExportedFile("/DesignationVacancyPositionReport" + datetime + ".xml");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "phVacant")) {
					reportBean.setJasperFile("/jasper/DisabilitiesInServices.jasper");
					reportBean.setExportedFile("/DisabilitiesInServices" + datetime + ".xml");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "empDetails")) {
					reportBean.setJasperFile("/jasper/AnnualNominalRollReport.jasper");
					reportBean.setExportedFile("/AnnualNominalRollReport" + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "halfYearEnding")) {
					reportBean.setJasperFile("/jasper/HalfYearEndingReport.jasper");
					reportBean.setExportedFile("/HalfYearEndingReport" + datetime + ".xml");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "totalVacant")) {
					reportBean.setValue1(request.getParameter("fromDate"));
					reportBean.setParameter1("fromDate");
					reportBean.setJasperFile("/jasper/R_Total_Vacant_DetailedReport.jasper");
					reportBean.setExportedFile("/R_Total_Vacant_DetailedReport" + datetime + ".xml");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "annualYearEnding")) {
					reportBean.setJasperFile("/jasper/AnnualYearRrport.jasper");
					reportBean.setExportedFile("/AnnualYearRrport" + datetime + ".xml");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "ledgerReport")) {
					reportBean.setJasperFile("/jasper/LedgerReport.jasper");
					reportBean.setExportedFile("/LedgerReport" + datetime + ".xls");
				}else if (CPSUtils.compareStrings(reportBean.getType(), "hrdgAnnual")) {
					reportBean.setJasperFile("/jasper/AnnualNominalRollReport_HRDG.jasper");
					reportBean.setExportedFile("/HRDGAnnualReport" + datetime + ".xls");
				}
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportCommunitywiseExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportCommunitywiseExcel ::: Community Id=" + reportBean.getCommunity());
				reportBean.setValue1(request.getParameter("community"));
				reportBean.setParameter1("COMMUNITY_ID");
				reportBean.setJasperFile("/jasper/R_Emp_Communitywise.jasper");
				reportBean.setExportedFile("/R_Emp_Communitywise_" + reportBean.getCommunity() + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportEmpLastModifiedExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=ireportEmpLastModifiedExcel");
				log.debug("From Date --> " + reportBean.getFromDate() + "To Date ----->" + reportBean.getToDate());
				reportBean.setValue1(request.getParameter("fromDate"));
				reportBean.setValue2(request.getParameter("toDate"));
				reportBean.setParameter1("fromDate");
				reportBean.setParameter2("toDate");
				if (CPSUtils.compareStrings(reportBean.getType(), "lastModified")) {
					reportBean.setJasperFile("/jasper/R_Emp_LastModified.jasper");
					reportBean.setExportedFile("/R_Emp_LastModified_" + reportBean.getFromDate() + "To" + reportBean.getToDate() + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "scStObc")) {
					reportBean.setJasperFile("/jasper/R_SC_ST_OBC_Report.jasper");
					reportBean.setExportedFile("/R_SC_ST_OBC_Report" + reportBean.getFromDate() + "To" + reportBean.getToDate() + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "HalfYearPhReport")) {
					reportBean.setJasperFile("/jasper/HalfYearlyPhReport.jasper");
					reportBean.setExportedFile("/HalfYearlyPhReport" + reportBean.getFromDate() + "To" + reportBean.getToDate() + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "HalfYearAllPhReport")) {
					reportBean.setJasperFile("/jasper/HalfYearlyAllPhReport.jasper");
					reportBean.setExportedFile("/HalfYearlyAllPhReport" + reportBean.getFromDate() + "To" + reportBean.getToDate() + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "CashPurchaseDemand")) {
					reportBean.setJasperFile("/jasper/cashpurchasedemand.jasper");
					reportBean.setExportedFile("/cashpurchasedemand" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "CashPurchaseVoucher")) {
					reportBean.setJasperFile("/jasper/CashPurchaseVoucher.jasper");
					reportBean.setExportedFile("/CashPurchaseVoucher" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".xls");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "DOJDRDO")) {
					reportBean.setJasperFile("/jasper/R_Emp_Doj_DRDO.jasper");
					reportBean.setExportedFile("/DateOFJoinInDRDO" + reportBean.getFromDate() + "To" + reportBean.getToDate() + datetime + ".xls");
				}
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("empHierarchyExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=empHierarchyExcel");
				reportBean.setValue1(reportBean.getHierarchy());
				reportBean.setParameter1("roleInstanceId");
				if (!CPSUtils.isNull(request.getParameter("type")) && CPSUtils.compareStrings(request.getParameter("type"), "Physical")) {
					reportBean.setJasperFile("/jasper/PhysicalHierarchyReport.jasper");
					reportBean.setExportedFile("/PhysicalHierarchyReport" + reportBean.getHierarchy() + datetime + ".xls");
				} else {
					reportBean.setJasperFile("/jasper/EmployeeHierarchyReport.jasper");
					reportBean.setExportedFile("/EmployeeHierarchyReport" + reportBean.getHierarchy() + datetime + ".xls");
				}
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("empMappingExcel")) {
				log.debug("ReportsController --> onSubmit Start   --> param=empMappingExcel");
				reportBean.setValue1(reportBean.getHierarchy());
				reportBean.setParameter1("roleInstanceId");
				reportBean.setJasperFile("/jasper/EmpRolesMappingReport.jasper");
				reportBean.setExportedFile("/EmpRolesMappingReport.jasper" + reportBean.getHierarchy() + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("HalfYearAllPhExcelReport")) {
				reportBean.setJasperFile("/jasper/HalfYearlyAllPhReport.jasper");
				reportBean.setExportedFile("/HalfYearlyAllPhReport" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("phVacantExcelReport")) {
				reportBean.setJasperFile("/jasper/DisabilitiesInServices.jasper");
				reportBean.setExportedFile("/DisabilitiesInServices" + datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("overAllLoanRequests")) {
				log.debug("ReportsController --> onSubmit Start--> param=overAllLoanRequests");
				log.debug("From Date ---->" + request.getParameter("fromDate") + "To Date" + request.getParameter("toDate"));
				reportBean.setValue1(request.getParameter("fromDate"));
				reportBean.setValue2(request.getParameter("toDate"));
				reportBean.setParameter1("fromDate");
				reportBean.setParameter2("toDate");
				reportBean.setJasperFile("/jasper/overAllLoans.jasper");
				//reportBean.setExportedFile("/overAllLoans" + request.getParameter("fromDate") + "To" + request.getParameter("toDate") + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("organizationReport", reportBean.getParam())) {
				log.debug("ReportsController  --> onSubmit --> param=organizationReport");
				viewName = "R_OrganizationReport";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (CPSUtils.compareStrings("empMapping", reportBean.getParam())) {//changed to orghierarchyreport
				log.debug("ReportsController  --> onSubmit --> param=EmployeeRoleMappingReport");
				reportBean = reportsBusiness.getReportHomeDate(reportBean, request);
				//viewName = "R_EmpMapping";
				viewName = "OrgHierarchyReport";
				mav = new ModelAndView(viewName, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param") != null && request.getParameter("param").equals("ireportOrganizationwise")) {
				log.debug("ReportsController --> onSubmit Start--> param=ireportOrganizationwise Pdf Report");
				reportBean.setValue1(request.getParameter("organization"));
				reportBean.setParameter1("Organization");

				if (CPSUtils.compareStrings(reportBean.getType(), "DepartmentHierarchy")) {
					reportBean.setJasperFile("/jasper/DepartmentHierarchy.jasper");
					//reportBean.setExportedFile("/DepartmentHierarchy " + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "OrgStructure")) {
					reportBean.setJasperFile("/jasper/OrgStructure.jasper");
					//reportBean.setExportedFile("/OrgStructure " + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "RolesHierarchy")) {
					reportBean.setJasperFile("/jasper/RolesHierarchy.jasper");
					//reportBean.setExportedFile("/RolesHierarchy " + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "RolesStructure")) {
					reportBean.setJasperFile("/jasper/RolesStructure.jasper");
					//reportBean.setExportedFile("/RolesStructure " + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "EmpRolesMapping")) {
					reportBean.setJasperFile("/jasper/EmpRolesMapping.jasper");
					//reportBean.setExportedFile("/EmpRolesMapping " + datetime + ".pdf");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "EmployeesMapping")) {
					reportBean.setJasperFile("/jasper/EmployeesMapping.jasper");
					//reportBean.setExportedFile("/EmployeesMapping " + datetime + ".pdf");
				}
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} 
			else if (request.getParameter("param") != null && request.getParameter("param").equals("leaveReport")) {
				//reportBean.setExportedFile("/" + reportBean.getRequestID() + datetime + ".pdf");
				if (CPSUtils.compareStrings(reportBean.getType(),"doPartReport")) {
					reportBean.setJasperFile("/jasper/DoPartIIReport.jasper");
					reportBean.setDoPartId(reportBean.getRequestID()); 
					if (reportBean.getRequestType() != null && reportBean.getRequestType().equalsIgnoreCase("doc")) {
						reportBean.setExportedFile("/" + reportBean.getRequestID() + ".doc");
						reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession, response);
					} else {
						reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					}
				} else if (CPSUtils.compareStrings(reportBean.getRequestType(), CPSConstants.LEAVE) && CPSUtils.compareStrings(reportBean.getType(), "CCL")) {
					/**
					 * If the Leave type is CCL
					 */
					reportBean.setJasperFile("/jasper/LeaveReports/CclLeaveApplicationReport.jasper");
					reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				} else if (CPSUtils.compareStrings(reportBean.getRequestType(), CPSConstants.LEAVE) && !CPSUtils.compareStrings(reportBean.getType(), "CCL")) {
					/**
					 * If the Leave is other than CCL
					 */
					reportBean.setJasperFile("/jasper/LeaveReports/LeaveApplicationReport.jasper");
					reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				} else if (CPSUtils.compareStrings(reportBean.getRequestType(), CPSConstants.CANCELLEAVEREQ)) {
					reportBean.setJasperFile("/jasper/LeaveReports/LeaveCancellationApplicationReport.jasper");
					reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				} else if (CPSUtils.compareStrings(reportBean.getRequestType(), CPSConstants.CONVERTLEAVEREQ)) {
					reportBean.setJasperFile("/jasper/LeaveReports/LeaveConversionReport.jasper");
					reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				}
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param").equals("report")) {
				if (CPSUtils.compareStrings(reportBean.getType(), "advance")) {
					reportBean.setJasperFile("/jasper/Cghs_Advance.jasper");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "settlement")) {
					reportBean.setJasperFile("/jasper/Cghs_Settlement.jasper");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "reimbursement")) {
					reportBean.setJasperFile("/jasper/Cghs_Reimbursement.jasper");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "nonCghsReim")) {
					reportBean.setJasperFile("/jasper/Non_Cghs_Reimbursement.jasper");
				} else if (CPSUtils.compareStrings(reportBean.getType(), "emergency")) {
					reportBean.setJasperFile("/jasper/Cghs_Emergency_Reimbursement.jasper");
				} 
				reportBean.setExportedFile("/" + reportBean.getRequestID() + datetime + ".pdf");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				
				//reportBean.setExportedFile("/" + reportBean.getRequestID() + ".pdf");
				//reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			} else if (request.getParameter("param").equals("initial")) {
				if(CPSUtils.compareStrings(reportBean.getType(),"initialReq")) {
				//	reportBean.setJasperFile("/jasper/Initial_Treatment_request.jasper");
					
					//added by bkr testing purpose 05/07/2016
					reportBean.setJasperFile("/jasper/LeaveRequestForm.jasper");
					
				}else if(CPSUtils.compareStrings(reportBean.getType(),"Consultation")) {
					reportBean.setJasperFile("/jasper/ConsultationReport.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"Investigation") || CPSUtils.compareStrings(reportBean.getType(),"Admission")) {
					reportBean.setJasperFile("/jasper/PermissionLetter.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"CGHS SETTLEMENT") || CPSUtils.compareStrings(reportBean.getType(),"CGHS REIMBURSEMENT")) {
					reportBean.setJasperFile("/jasper/Cghs_CheckList.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"NON CGHS REIMBURSEMENT")) {
					reportBean.setJasperFile("/jasper/Non_Cghs_CheckList.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"emerForm")) {
					reportBean.setJasperFile("/jasper/Emergency_Form_1.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"emerReim")) {
					reportBean.setJasperFile("/jasper/Emergency_CheckList.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcApproval")) {
					reportBean.setJasperFile("/jasper/LTC_Request_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcAdvance")) {
					reportBean.setJasperFile("/jasper/LTC_Advance_Finance_Form.jasper");
					
				}
				
				else if(CPSUtils.compareStrings(reportBean.getType(),"ltcReim")) {
					reportBean.setJasperFile("/jasper/LTC_Claim.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"TourSettlement")) {
						reportBean.setJasperFile("/jasper/LTC_Tour_Particulars.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"SettTourSettlement")) {
						reportBean.setJasperFile("/jasper/LTC_Settlement_Tour_Particulars.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcSett")) {
					reportBean.setJasperFile("/jasper/LTC_Settlement_Claim.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"advanceForm1")) {
					reportBean.setJasperFile("/jasper/LTC_Advance_Request_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"advanceForm2")) {
					reportBean.setJasperFile("/jasper/LTC_Advance_Main.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcFinReim")) {
					reportBean.setJasperFile("/jasper/LTC_Reimbursement_Finance.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcFinSett")) {
					reportBean.setJasperFile("/jasper/LTC_Settlement_Finance.jasper");
				}
				
				
				else if(CPSUtils.compareStrings(reportBean.getType(),"ltcNillSett")) {
					reportBean.setJasperFile("/jasper/LTC_Nill_Settlement.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"MroForm")) {
					reportBean.setJasperFile("/jasper/MilitaryReceivableOrder.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"encash")) {
					reportBean.setJasperFile("/jasper/Ltc_Encashment.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcRefund")) {
					reportBean.setJasperFile("/jasper/LTC_Refund_Mro_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"appElEncash")) {
					reportBean.setJasperFile("/jasper/LTC_Approval_Encashment_Mro_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"advElEncash")) {
					reportBean.setJasperFile("/jasper/LTC_Advance_Encashment_Mro_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcHistory")) {
					reportBean.setJasperFile("/jasper/Emplyee_Ltc_History.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcExperience")) {
					reportBean.setJasperFile("/jasper/LTC_Emp_Exp_Status.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcAppCancel")) {
					reportBean.setJasperFile("/jasper/LTC_Request_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcAppAdvCancel")) {
					reportBean.setJasperFile("/jasper/LTC_Advance_Request_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdApproval")) {               
					reportBean.setJasperFile("/jasper/tadaTdRequest.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdAmendmentApproval")) {      
					reportBean.setJasperFile("/jasper/tadaTdAmendmentForm.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdCancelApproval")) {         
					reportBean.setJasperFile("/jasper/tadaTdCancellationForm.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdAdvanceFin")) {            
					reportBean.setJasperFile("/jasper/tada_td_advance_finance.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdAdvanceCda")) {             
					reportBean.setJasperFile("/jasper/tada_td_adv_cda.jasper");
					//reportBean.setJasperFile("/jasper/tada_td_advance_cda.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaAdvanceFinLevel")) {       
					reportBean.setJasperFile("/jasper/tada_td_advance_finance.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaAdvanceCDALevel")) {             
					reportBean.setJasperFile("/jasper/tada_td_advance_cda.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdSettlementRmaDay")) {
					reportBean.setJasperFile("/jasper/tadaSettRmaDay.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdSettlementRmaKm")) {
					reportBean.setJasperFile("/jasper/tadaSettRmaKm.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdSettlementOldDa")) {
					reportBean.setJasperFile("/jasper/TADA_Claim_DA_Old_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdReimRmaDay")) {
					reportBean.setJasperFile("/jasper/tadaReimRmaDay.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdReimRmaKm")) {
					reportBean.setJasperFile("/jasper/tadaReimRmaKm.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdReimOldDa")) {
					reportBean.setJasperFile("/jasper/TADA_Reim_DA_Old_Form.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdMRODetails")) {
					reportBean.setJasperFile("/jasper/TadaMROForm.jasper");                         
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdSettlement")) {
					/*if(tadaRequestProcess.getAllOldDaDetails(reportBean.getRequestID()).size()!=0){
						reportBean.setJasperFile("/jasper/TADA_Claim_DA_Old_Form.jasper");
					}else if(tadaRequestProcess.getAllNewDaAccDetails(reportBean.getRequestID()).size()!=0 && tadaRequestProcess.getAllRMAKmDetails(reportBean.getRequestID()).size()!=0){
						reportBean.setJasperFile("/jasper/tadaSettRmaKm.jasper");
					}else if(tadaRequestProcess.getAllNewDaAccDetails(reportBean.getRequestID()).size()!=0 && tadaRequestProcess.getAllRMADayDetails(reportBean.getRequestID()).size()!=0){
						reportBean.setJasperFile("/jasper/tadaSettRmaDay.jasper");
					}else if(tadaRequestProcess.getAllNewDaAccDetails(reportBean.getRequestID()).size()!=0 && tadaRequestProcess.getAllRMADayDetails(reportBean.getRequestID()).size()==0 && tadaRequestProcess.getAllRMAKmDetails(reportBean.getRequestID()).size()==0){
						reportBean.setJasperFile("/jasper/tadaSettRmaKm.jasper");
					}*/
					reportBean.setJasperFile("/jasper/TD_Settlement_Finance.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdReimbursement")) {
					/*if(tadaRequestProcess.getAllOldDaDetails(reportBean.getRequestID()).size()!=0){
						reportBean.setJasperFile("/jasper/TADA_Reim_DA_Old_Form.jasper");
					}else if(tadaRequestProcess.getAllNewDaAccDetails(reportBean.getRequestID()).size()!=0 && tadaRequestProcess.getAllRMAKmDetails(reportBean.getRequestID()).size()!=0){
						reportBean.setJasperFile("/jasper/tadaReimRmaKm.jasper");
					}else if(tadaRequestProcess.getAllNewDaAccDetails(reportBean.getRequestID()).size()!=0 && tadaRequestProcess.getAllRMADayDetails(reportBean.getRequestID()).size()!=0){
						reportBean.setJasperFile("/jasper/tadaReimRmaDay.jasper");
					}else if(tadaRequestProcess.getAllNewDaAccDetails(reportBean.getRequestID()).size()!=0 && tadaRequestProcess.getAllRMADayDetails(reportBean.getRequestID()).size()==0 && tadaRequestProcess.getAllRMAKmDetails(reportBean.getRequestID()).size()==0){
						reportBean.setJasperFile("/jasper/tadaReimRmaKm.jasper");
					}*/
					reportBean.setJasperFile("/jasper/TD_Reim_Finance.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdNillSettlement")) {
					reportBean.setJasperFile("/jasper/TD_Nill_Settlement.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(), "tdSettTourParticulars") || CPSUtils.compareStrings(reportBean.getType(), "tdReimTourParticulars")){
					reportBean.setJasperFile("/jasper/tdTourParticulars.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdSett")) {
					reportBean.setJasperFile("/jasper/TD_Settlement_Finance.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdReim")) {
					reportBean.setJasperFile("/jasper/TD_Reim_Finance.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"tadaTdNillSett")) {
					reportBean.setJasperFile("/jasper/TD_Nill_Settlement.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(), "tdAppliedUsers")){
					reportBean.setJasperFile("/jasper/TdTxnSearchReport.jasper");
				}
				//reportBean.setExportedFile("/" + reportBean.getRequestID() + datetime + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				
			}
			//Ltc Report For Finance after Entering Bill No
			 else if (request.getParameter("param").equals("initialCda")) {
			 if(CPSUtils.compareStrings(reportBean.getType(),"ltcCdaSett")) {
				reportBean.setJasperFile("/jasper/LTC_Settlement_CDA.jasper");
			}
			 if(CPSUtils.compareStrings(reportBean.getType(),"ltcCdaAdv")) {
					reportBean.setJasperFile("/jasper/LTC_Advance_Finance_CDA.jasper");
				}
			 if(CPSUtils.compareStrings(reportBean.getType(),"ltcCdaReimb")) {
					reportBean.setJasperFile("/jasper/LTC_Reimbursement_Finance_CDA.jasper");
				}
			 if(CPSUtils.compareStrings(reportBean.getType(),"ltcCdaEncash")) {
					reportBean.setJasperFile("/jasper/Ltc_Encashment_CDA.jasper");
				}
			 reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			 }
			//cghs Reports for Finance after entering Bill no
			 else if(request.getParameter("param").equals("initialCdacghs")){
				 if(CPSUtils.compareString(reportBean.getType(), "cghsCdaAdv")){ 
					//reportBean.setJasperFile("/jasper/Cghs/Cghs_Advance_CDA.jasper");
					 reportBean.setJasperFile("/jasper/Cghs_Advance_CDA.jasper");
					 
				 }
				 if(CPSUtils.compareString(reportBean.getType(), "cghsCdaSett")){
					 //reportBean.setJasperFile("/jasper/Cghs/Cghs_Settlement_CDA.jasper");
					 reportBean.setJasperFile("/jasper/Cghs_Settlement_CDA.jasper");
					 
				 }
				 if(CPSUtils.compareString(reportBean.getType(), "cghsCdaReimb")){
					 //reportBean.setJasperFile("/jasper/Cghs/Cghs_Reimbursement_CDA.jasper");
					 reportBean.setJasperFile("/jasper/Cghs_Reimbursement_CDA.jasper");
				 }
				 if(CPSUtils.compareString(reportBean.getType(), "nonCghsReim")){
					// reportBean.setJasperFile("/jasper/Cghs/Non_Cghs_Reimbursement_CDA.jasper");
					 reportBean.setJasperFile("/jasper/Non_Cghs_Reimbursement_CDA.jasper");
				 }
                 if(CPSUtils.compareString(reportBean.getType(), "emergency")){
                	// reportBean.setJasperFile("/jasper/Cghs/Cghs_Emergency_Reimbursement_CDA.jasper");
                	 reportBean.setJasperFile("/jasper/Cghs_Emergency_Reimbursement_CDA.jasper");
				 }
                 reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
 				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			 }
			else if (request.getParameter("param").equals("initialDocs")) {
				if(CPSUtils.compareStrings(reportBean.getType(),"ltcApprovalAmendment")) {
					reportBean.setJasperFile("/jasper/LTCApprovalAmendmentReport.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcAdvanceAmendment")){
					reportBean.setJasperFile("/jasper/LTCAdvanceAmendmentReport.jasper");
				}
				
				else if(CPSUtils.compareStrings(reportBean.getType(),"TourSettlement")) {
					reportBean.setJasperFile("/jasper/LTC_Tour_Particulars.jasper");
			}
				
				
				else if(CPSUtils.compareStrings(reportBean.getType(),"ltcSett")) {
					reportBean.setJasperFile("/jasper/LTC_Settlement_Claim.jasper");}
				
				else if(CPSUtils.compareStrings(reportBean.getType(),"ltcReim")) {
					reportBean.setJasperFile("/jasper/LTC_Claim.jasper");
				}
				
				else if(CPSUtils.compareString(reportBean.getType(), "SettTourSettlement")){
					reportBean.setJasperFile("/jasper/LTC_Settlement_Tour_Particulars.jasper");
					
				}
				
				
			    reportBean.setExportedFile("/" + datetime + ".doc");
				//reportBean.setExportedFile("/" + reportBean.getRequestID() + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("initialDocspdf")) {
				if(CPSUtils.compareStrings(reportBean.getType(),"ltcApprovalAmendment")) {
					reportBean.setJasperFile("/jasper/LTCApprovalAmendmentReport.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"ltcAdvanceAmendment")){
					reportBean.setJasperFile("/jasper/LTCAdvanceAmendmentReport.jasper");
				}
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			
			else if (CPSUtils.compareStrings(CPSConstants.LOANREPORT, reportBean.getParam())) {
				if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.DEATH)) {
					// Festival loan form
					reportBean.setJasperFile("/jasper/ImmediateRelief.jasper");
				} else if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.FESTIVALLOANID)) {
					// Festival loan form
					reportBean.setJasperFile("/jasper/FestivalLoanReport.jasper");
				}else if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.HBALOANID)) {
					if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.APPLICATION)){
						// HBA loan form
						reportBean.setJasperFile("/jasper/hbaCheckList.jasper");
					}else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.SANCTION)){
						//HBA Sanction Report
						reportBean.setJasperFile("/jasper/HBASanction.jasper");
					}else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.SANCTION1)){
						//HBA Sanction Report2
						reportBean.setJasperFile("/jasper/HBAGrant.jasper");
					}
					else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CONTINGENT)){
						//HBA ContingentBill Report
						reportBean.setJasperFile("/jasper/ConveyanceContingentBill.jasper");
					}
				} 
				else if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.GPFADVANCELOANID)) {
					// GPF loan form
					if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.APPLICATION)){
						reportBean.setJasperFile("/jasper/GPFAdvanceRequestForm.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CONTINGENT)){
						reportBean.setJasperFile("/jasper/GPFAdvanceContingentBill.jasper");
					}  else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CLAIM)){
						reportBean.setJasperFile("/jasper/GPFAdvanceClaim.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.SANCTION)){
						reportBean.setJasperFile("/jasper/GPFAdvanceSanction.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CERTIFICATE)){
						reportBean.setJasperFile("/jasper/GPFAdvanceCertSanction.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.DEBIT)){
						reportBean.setJasperFile("/jasper/GPFAdvanceDebit.jasper");
					}	
					
				} else if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.GPFWITHDRAWALLOANID)) {
					// GPF loan form
					if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.APPLICATION)){
						reportBean.setJasperFile("/jasper/GPFWithdrawalRequestForm.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CONTINGENT)){
						reportBean.setJasperFile("/jasper/GPFWithdrawalContingentBill.jasper");
					}  else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CLAIM)){
						reportBean.setJasperFile("/jasper/GPFWithdrawalClaim.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.SANCTION)){
						reportBean.setJasperFile("/jasper/GPFWithdrawalSanction.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CERTIFICATE)){
						reportBean.setJasperFile("/jasper/GPFWithdrawalFinal.jasper");
					} else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.DEBIT)){
						reportBean.setJasperFile("/jasper/GPFWithdrawalDebit.jasper");
					}		
							
				} else if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.MOTORCARLOANID) || CPSUtils.compareStrings(reportBean.getType(), CPSConstants.MOTORCYCLELOANID)
						|| CPSUtils.compareStrings(reportBean.getType(), CPSConstants.PCLOANID) || CPSUtils.compareStrings(reportBean.getType(), CPSConstants.MOTORSCOOTERLOANID)
						|| CPSUtils.compareStrings(reportBean.getType(), CPSConstants.MOPEDLOANID)) {
					// Motor loan form
					if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.APPLICATION)){
						reportBean.setJasperFile("/jasper/ConveyanceApplicationForm.jasper");
					}else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CERTIFICATE)){
						reportBean.setJasperFile("/jasper/MotorLoans.jasper");
					}else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.CONTINGENT)){
						//conveyance ContingentBill Report
						reportBean.setJasperFile("/jasper/ConveyanceContingentBill.jasper");
					}else if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.AGREEMENT)){
						//conveyance ContingentBill Report
						reportBean.setJasperFile("/jasper/AgreementForm.jasper");
					}
				} 
				//reportBean.setExportedFile("/" + reportBean.getRequestID() + ".pdf");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("annualIncrementDOReport", reportBean.getParam())) {
				log.debug("ReportsController --> onSubmit --> param=annualIncrementDOReport");
				reportBean.setJasperFile("/jasper/AnnualIncrement.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings(CPSConstants.FPAREPORT, reportBean.getParam())) {
				if(CPSUtils.compareStrings(reportBean.getReport(), CPSConstants.APPLICATION)){
					// FPA Application Form
					reportBean.setJasperFile("/jasper/FPAApplication.jasper");
				}
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings(CPSConstants.QUARTERREPORT, reportBean.getParam())) {
				reportBean.setJasperFile("/jasper/GovernmentAccomodationAllotmentForm.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			
			//naresh
			else if (CPSUtils.compareStrings(CPSConstants.VIEWADDRESSREPORTS, reportBean.getParam())) {
				reportBean.setJasperFile("/jasper/addressreport.jasper");
				reportBean.setRequesterSfid(request.getParameter("requestersfid"));
				reportBean.setAddresstype(request.getParameter("addressTypeId"));
				//reportBean = reportsBusiness.getAddressType(reportBean);
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings(CPSConstants.VIEWADDRESSREPORTS1, reportBean.getParam())) {
				reportBean.setJasperFile("/jasper/Address_Permanent.jasper");
				//reportBean.setRequesterSfid(request.getParameter("requestersfid"));
				//reportBean.setAddresstype(request.getParameter("addressTypeId"));
				//reportBean = reportsBusiness.getAddressType(reportBean);
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings("caQuarterReport", reportBean.getParam())) {
				reportBean.setJasperFile("/jasper/GovernmentAccomodationAllotmentFormCA.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("IONQuarterReport", reportBean.getParam())) {
				reportBean.setJasperFile("/jasper/GovernmentAccomodationAllotmentIONForm.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("ChangeQuarterReport", reportBean.getParam())) {
				reportBean.setJasperFile("/jasper/GovernmentAccomodationChangeFormEMU.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings("hQReport", reportBean.getParam())) {
				reportBean.setJasperFile("/jasper/HigherQualification_Appln.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}

			else if(CPSUtils.compareStrings(CPSConstants.LOANCONVEYANCEREPORT, reportBean.getParam())){
					// CONVEYANCE ADVANCE DEMAND
					reportBean.setJasperFile("/jasper/LoanConveyanceReport.jasper");
					reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if(CPSUtils.compareStrings(CPSConstants.LOANACQUITTANCEREPORT, reportBean.getParam())){
				// CONVEYANCE ACQUITTANCE REPORT
				reportBean.setJasperFile("/jasper/LoanAcquittance.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings(CPSConstants.PAYFIXATIONREPORT, reportBean.getParam())) {
				if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.DOPART)) {
					// Pay Fixation DO Part 
					reportBean.setJasperFile("/jasper/PayFixation.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"optCert")){
					//Optional Certificate Report
					reportBean.setJasperFile("/jasper/OptionCertificate.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"joinReport")){
					//Optional Certificate Report
					reportBean.setJasperFile("/jasper/JoiningReport.jasper");
				}
				reportBean.setExportedFile("/" +reportBean.getRequestID() + datetime + ".pdf");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if(CPSUtils.compareStrings(reportBean.getParam(),"emuReport")){
				reportBean.setJasperFile("/jasper/EMU_Request_Form1.jasper");
				reportBean =  reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings(CPSConstants.PROMOTIONREPORTS, reportBean.getParam())) {
				if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.SENDTOHQ)) {
					// Sending To HQ Report
					reportBean.setJasperFile("/jasper/SendingToHQ.jasper");
				}
				if (CPSUtils.compareStrings(reportBean.getType(), CPSConstants.PROMOTIONASLELIGIBLE)) {
					// ASL Eligible Report
					reportBean.setJasperFile("/jasper/PromotionAslEligible.jasper");
				}
				else if(CPSUtils.compareStrings(reportBean.getType(),CPSConstants.QUALIFIEDCANDIDATES)){
					//Qualified Candiadtes Report
					reportBean.setJasperFile("/jasper/QualifiedCandidates.jasper");
				}
				else if(CPSUtils.compareStrings(reportBean.getType(),CPSConstants.PROMOTEDCANDIDATES)){
					//Promoted Candidates Report
					reportBean.setJasperFile("/jasper/PromotedCandidates.jasper");
				}
				else if(CPSUtils.compareStrings(reportBean.getType(),CPSConstants.PAYFIXATION)){
					//Pay Fixation Report
					reportBean.setJasperFile("/jasper/PayFixationReport.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),CPSConstants.RESIDENCYPERIODMASTER)){
					//Residency Period Master Report
					reportBean.setJasperFile("/jasper/PromotionResidencyPeriod.jasper");
				}
				reportBean.setExportedFile("/" +reportBean.getRequestID() + datetime + ".pdf");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(CPSConstants.NORMALREPORT, reportBean.getParam())){
				if(CPSUtils.compareStrings(reportBean.getType(),"all")){
					reportBean.setJasperFile("/jasper/Ess_Pending.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"penCount")){
					reportBean.setJasperFile("/jasper/Pending_Req_Count.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"leavePending")){
					reportBean.setJasperFile("/jasper/Leave_Req_Pending.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"designation")){
					reportBean.setJasperFile("/j" +
							"asper/Designation_List.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"discipline")){
					reportBean.setJasperFile("/jasper/Disciplines_List.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"qualification")){
					reportBean.setJasperFile("/jasper/Qualification_List.jasper");
				}
				else if(CPSUtils.compareStrings(reportBean.getType(),"hospital")){
					reportBean.setJasperFile("/jasper/Referral_Hospital.jasper");
				}
				reportBean.setExportedFile("/" +reportBean.getRequestID() + datetime + ".pdf");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(CPSConstants.AVAILABLELEAVESREPORT, reportBean.getParam())){
				reportBean.setJasperFile("/jasper/availableLeaves.jasper");
				reportBean.setExportedFile("/availableLeavesReport"+ datetime + ".xls");
				reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if (CPSUtils.compareStrings(CPSConstants.PROMOTIONIONREPORTS, reportBean.getParam())) {
				if(CPSUtils.compareStrings(reportBean.getType(),CPSConstants.ELIGIBLEION)){
					//Eligible Candidates ION
					reportBean.setJasperFile("/jasper/AssessmentBoardRACION.jasper");
				}
				else if(CPSUtils.compareStrings(reportBean.getType(),CPSConstants.QUALIFIEDION)){
					//Qualified Candidates ION
					reportBean.setJasperFile("/jasper/AssessmentBoardION.jasper");
				}
				else if(CPSUtils.compareStrings(reportBean.getType(),"payCCS")){
					//Pay Fixation CCS
					reportBean.setJasperFile("/jasper/PayFixationCCS.jasper");
				}
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}

			else if (request.getParameter("param").equals("adminReport")) {
				reportBean.setJasperFile("/jasper/adminNoteforNOC.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}

			else if (request.getParameter("param").equals("courseReport")) {
				reportBean.setJasperFile("/jasper/HRDGTrainingReport_testing.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("circulationReport")) {
				reportBean.setJasperFile("/jasper/HRDGTrainingCirculationReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("trainingNominationReport")) {
				reportBean.setJasperFile("/jasper/HRDGTrainingNominationReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("trainingCancelNominationReport")) {
				reportBean.setJasperFile("/jasper/HRDGTrainingCancelNominationReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("trainingNominationFormReport")) {
				reportBean.setJasperFile("/jasper/HRDGTrainingNominationFormReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			
			else if (request.getParameter("param").equals("trainingRegionReport")) {
				reportBean.setJasperFile("/jasper/HRDGTrainingRegionReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("ionReport")) {
				reportBean.setJasperFile("/jasper/HRDGCirculationIONReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				//reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response); 
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("trainingNominationToMDBReport")) {
				reportBean.setJasperFile("/jasper/HRDGNominationsToMDBReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("trainingNominationsApprovedByMDBReport")) {
				reportBean.setJasperFile("/jasper/HRDGNominationsApprovedByMDBReport.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("trainingNominationsToCDAReport")) {
				reportBean.setJasperFile("/jasper/HRDGTrainingNominationsToCDA_ION.jasper");
				
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("trainingNominationsToInistituteReport")) {
				reportBean.setJasperFile("/jasper/HRDGNominationsToInistituteReport_ION.jasper");
				
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("yearBookReport")) {
				if(CPSUtils.compareStrings(request.getParameter("yearBookType"), "calendar"))
				{
					reportBean.setJasperFile("/jasper/HRDGTrainingReport_main.jasper");
					reportBean.setJasperFile("/jasper/first_category.jasper");
				}
				if(CPSUtils.compareStrings(request.getParameter("yearBookType"), "yearBook"))
					reportBean.setJasperFile("/jasper/HRDGCEPYearBook_main.jasper");
				
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("HRDGReport")) {
				if(CPSUtils.compareStrings(request.getParameter("yearBookType"), "category"))
				{
					reportBean.setJasperFile("/jasper/HRDG_Reports_category.jasper");
				}
				if(CPSUtils.compareStrings(request.getParameter("yearBookType"), "designation"))
					reportBean.setJasperFile("/jasper/HRDG_Reports_category1.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("previewIONinDOC")) {
				reportBean.setJasperFile("/jasper/notice_board_ion.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("previewIONinPDF")) {
				reportBean.setJasperFile("/jasper/notice_board_ion.jasper");
				reportBean.setExportedFile("/" + datetime + ".pdf");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("ionDispatchListinDOC")) {
				reportBean.setJasperFile("/jasper/notice_ion_dispatch_list.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			else if (request.getParameter("param").equals("ionDispatchListinPDF")) {
				reportBean.setJasperFile("/jasper/notice_ion_dispatch_list.jasper");
				reportBean.setExportedFile("/" + datetime + ".doc");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			
			else if (request.getParameter("param").equals("previewIONinPreview")) {
				reportBean.setJasperFile("/jasper/notice_board_ion_preview.jasper");
				reportBean.setExportedFile("/" + datetime + ".pdf");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);

			}
			
			
			else if(CPSUtils.compareStrings(reportBean.getParam(),"miscReport")){
				if(CPSUtils.compareStrings("residential",reportBean.getType())){
					reportBean.setJasperFile("/jasper/ResidentialProof.jasper");
					reportBean.setExportedFile("/" + datetime + ".doc");
				}
				else if(CPSUtils.compareStrings("service",reportBean.getType())){
					reportBean.setJasperFile("/jasper/serviceCertificate.jasper");
					reportBean.setExportedFile("/" + datetime + ".doc");
				}
				reportBean =  reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(),"printTutionFeeRequestFormDetails")){
				reportBean.setJasperFile("/jasper/tutionFeeRequestForm.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(),"printAllTuitionFeeRelatedDocuments")){
				reportBean.setJasperFile("/jasper/tuitionFeeSanctionReport.jasper");
				if(reportBean.getRequestType() != null && reportBean.getRequestType().equalsIgnoreCase("doc")){
					reportBean.setExportedFile("/"+reportBean.getRequestID() +".doc");
					reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				}else{
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				}
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(), "printTuitionFeeSanctionedIndividualReport")){
				reportBean.setJasperFile("/jasper/tuitionFeeSanctionSyBill.jasper");
				if(reportBean.getRequestType() != null && reportBean.getRequestType().equalsIgnoreCase("doc")){
					reportBean.setExportedFile("/"+reportBean.getRequestID() +".doc");
					reportBean = reportsBusiness.getDocumentReport(reportBean, request, dbsession,response);
				}else{
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				}
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(), "printTuitionFeeClaimDetailsReport")){
				reportBean.setJasperFile("/jasper/Tuition_fee_claim_details.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings(reportBean.getParam(), "printTelephoneBillRequestFormDetails")){
				reportBean.setJasperFile("/jasper/telephoneBillRequestForm_ClaimDetails.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings(reportBean.getParam(), "printAllTelephoneBillRelatedDocuments")){
				reportBean.setJasperFile("/jasper/TelephoneBillReimbursement.jasper");
			    if(reportBean.getRequestType() != null && reportBean.getRequestType().equalsIgnoreCase("excel")){
					reportBean.setExportedFile("/Reimbursement" + datetime + ".xls");
					reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
				}else{
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				}
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if (CPSUtils.compareStrings(reportBean.getParam(), "printTelephoneBillSanctionedIndividualReport")){
				reportBean.setJasperFile("/jasper/TelephoneBillAnnexureSanction.jasper");
				if(reportBean.getRequestType() != null && reportBean.getRequestType().equalsIgnoreCase("excel")){
					reportBean.setExportedFile("/AnnexureSanction" +datetime + ".xls");
					reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
				}else{
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				}
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(), "printTelephoneBillClaimDetailsReport")){
				reportBean.setJasperFile("/jasper/TelephoneBillClaimDetails.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession, response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(),"printAnnualIncrArrearsDetails")){
				reportBean.setJasperFile("/jasper/annualIncrementArrearsDetails.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if(CPSUtils.compareStrings(reportBean.getParam(),"printDAArrearsDetails")){
				 HttpSession session=request.getSession(true);
				 String categoryId=(String)session.getAttribute("categoryId");
			     reportBean.setCategory(categoryId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			     if(CPSUtils.compareStrings(categoryId, "0")){
			    	 reportBean.setJasperFile("/jasper/DAArrearsAllCategoriesMain.jasper");
			     }else{
			    	 reportBean.setJasperFile("/jasper/ScheduleReports/DAArrearsAllCategoryList.jasper");
			     }
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(),"printPromotionArrearsDetails")){
				reportBean.setJasperFile("/jasper/PromotionArrearsDetails.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareStrings(reportBean.getParam(),"retirement")){
				if(CPSUtils.compareStrings(reportBean.getType(),"claimTopPriority")){
					reportBean.setJasperFile("/jasper/PensionClaim_TopPriority.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"pensionRequestLetter")){
					reportBean.setJasperFile("/jasper/Pension_request_letter.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"pensionApplication")){
					reportBean.setJasperFile("/jasper/application_for_retirement_gratuity.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"commutationApplication")){
					reportBean.setJasperFile("/jasper/Application_Pension_WithoutmedicalExam.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"calculationSheet")){
					reportBean.setJasperFile("/jasper/retirement_calculation_sheet.jasper");
				}else if(CPSUtils.compareStrings(reportBean.getType(),"bankVerificationForm")){
					reportBean.setJasperFile("/jasper/bank_verification_details.jasper");
				}
				reportBean.setExportedFile("/" +reportBean.getRequestID() + datetime + ".pdf");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			else if(CPSUtils.compareStrings(reportBean.getParam(),"retirementReport")){
				reportBean.setJasperFile("/jasper/retirement_main_report.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}else if(CPSUtils.compareString(reportBean.getParam(), "MTReports")){
				if(reportBean.getType().equals("yearlyMileageCardPdf")){
					reportBean.setJasperFile("/jasper/MT_Yearly_MileageCard.jasper");
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				}else if(reportBean.getType().equals("yearlyMileageCardExcel")){
					log.debug("ReportsController --> onSubmit Start   --> param=Yearly Mileage Excel Report");
					reportBean.setJasperFile("/jasper/MT_Yearly_MileageCard.jasper");
					reportBean.setExportedFile("/MileageCard" + datetime + ".xls");
					reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
				}else if(reportBean.getType().equals("dailyMileageEntryPdf")){
					reportBean.setJasperFile("/jasper/MT_Daily_Mileage.jasper");
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				}else if(reportBean.getType().equals("dailyMileageEntryExcel")){
					log.debug("ReportsController --> onSubmit Start   --> param=Yearly Mileage Excel Report");
					reportBean.setJasperFile("/jasper/MT_Daily_Mileage.jasper");
					reportBean.setExportedFile("/DailyMileage" + datetime + ".xls");
					reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
				}else if(reportBean.getType().equals("distanceKPLRecordPdf")){
					reportBean.setJasperFile("/jasper/MT_Yearly_Distance_Covered.jasper");
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				}else if(reportBean.getType().equals("distanceKPLRecordExcel")){
					log.debug("ReportsController --> onSubmit Start   --> param=Yearly Mileage Excel Report");
					reportBean.setJasperFile("/jasper/MT_Yearly_Distance_Covered.jasper");
					reportBean.setExportedFile("/DistanceKPL" + datetime + ".xls");
					reportBean = reportsBusiness.getExcelReport(reportBean, request, dbsession, response);
				}
				else if(reportBean.getType().equals("dayWiseAllocationReport")){
					reportBean.setJasperFile("/jasper/mt_daily_allocation_details.jasper");
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				}else if(reportBean.getType().equals("completionReport")){
					reportBean.setJasperFile("/jasper/mt_completion_details.jasper");
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				}else if(reportBean.getType().equals("mtRequestForm")){
					reportBean.setJasperFile("/jasper/MT_Request_Form.jasper");
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				}else if(reportBean.getType().equals("yearlyMielageCard")){
					reportBean.setJasperFile("/jasper/MT_Yearly_MileageCard.jasper");
					reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
					mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
				}
				}
			//GPF Closing Report
			else if(reportBean.getParam().equals("gpfclosing")){
				reportBean.setJasperFile("/jasper/GPFClosing.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
			//Employee Exp Report
			else if(reportBean.getParam().equals("loanEmpX")){
				reportBean.setJasperFile("/jasper/employeeExp.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
	
			//loan History details Report for employee
			else if(reportBean.getParam().equals("loanHistory")){
				reportBean.setJasperFile("/jasper/LoanHistoryReportForEmp.jasper");
				reportBean = reportsBusiness.getPdfReport(reportBean, request, dbsession,response);
				mav = new ModelAndView(CPSConstants.R_REPORTSPATH, CPSConstants.REPORTS, reportBean);
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} finally {
			
		}
		return mav;
	}
}
/*testing do*/
