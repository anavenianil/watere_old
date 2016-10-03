package com.callippus.web.leave.controller.request;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.json.JSON;
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

import com.callippus.web.business.requestprocess.LeaveWaterRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.leave.beans.request.LeaveWaterApplicationBean;
import com.callippus.web.leave.beans.request.LeaveWaterRequestProcessBean;
import com.callippus.web.leave.business.request.LeaveWaterRequestBusiness;

@Controller
@SessionAttributes
public class LeaveWaterRequestController {
	private static Log log = LogFactory
			.getLog(LeaveWaterRequestController.class);

	@Autowired
	LeaveWaterRequestBusiness leaveWaterRequestBusiness;

	@Autowired
	private FileUpload fileUpload;

	@Autowired
	LeaveWaterRequestProcess leaveWaterRequestProcess;

	@RequestMapping(value = "/leaveWaterRequest.htm", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(
			@ModelAttribute(CPSConstants.LEAVEWATERREQUEST) LeaveWaterApplicationBean leaveWaterBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			log.debug("enter into controller");
			leaveWaterBean.setLeaveType(leaveWaterBean.getLeaveTypeCopy());

			// ErpAction.userChecks(request);

			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(leaveWaterBean.getSfID())
					|| CPSUtils.compareStrings(CPSConstants.SELF,
							leaveWaterBean.getSfID())) {
				leaveWaterBean.setSfID(session.getAttribute(CPSConstants.SFID)
						.toString());
			}
			if (CPSUtils.compareStrings(CPSConstants.LEAVEWATERDAYSENTRYHOME,
					leaveWaterBean.getParam())) {
				log.debug("levae controller 1st condition");
				leaveWaterBean = leaveWaterRequestBusiness
						.getleaveYearsList(leaveWaterBean);
				viewName = CPSConstants.LEAVEDAYSENTRY;
			} else if (CPSUtils.compareStrings(
					CPSConstants.LEAVEWATERDAYSENTRYSAVE,
					leaveWaterBean.getParam())) {
				log.debug("levae controller 2nd condition");
				leaveWaterBean.setResult(leaveWaterRequestProcess
						.saveLeaveDays(leaveWaterBean));
				leaveWaterBean = leaveWaterRequestBusiness
						.getleaveYearsList(leaveWaterBean);
				viewName = CPSConstants.LEAVEDAYSENTRY;
			} else if (CPSUtils
					.compareStrings(CPSConstants.APPLICATIONFORMHOME,
							leaveWaterBean.getParam())) {
				log.debug("levae controller 3rd condition");

				leaveWaterRequestBusiness.getEligibleOrNot1(leaveWaterBean,
						leaveWaterBean.getSfID());
				leaveWaterBean = leaveWaterRequestBusiness
						.getEmpDetails(leaveWaterBean);
				leaveWaterBean = leaveWaterRequestBusiness
						.getAvailableLeaveTypes(leaveWaterBean);

				// added by bkr 17/06/2016 for erp applied leaves list
				leaveWaterRequestProcess
						.getErpAppliedLeavesList(leaveWaterBean);
				session.setAttribute(CPSConstants.ERPAPPLIEDLEAVESLIST,
						leaveWaterBean.setErpAppliedLeavesList);
				viewName = CPSConstants.ERPLEAVEAPPLICATION;

			} else if (CPSUtils.compareStrings(CPSConstants.ERPLEAVESAVE,
					leaveWaterBean.getParam())) {
				log.debug("levae controller 4th condition");
				leaveWaterBean = leaveWaterRequestBusiness
						.getEmpDetails(leaveWaterBean);
				LeaveWaterRequestProcessBean leaveWaterRequestProcessBean = new LeaveWaterRequestProcessBean();
				leaveWaterBean.setSession(session);
				BeanUtils.copyProperties(leaveWaterRequestProcessBean,
						leaveWaterBean);

				leaveWaterBean.setResult(leaveWaterRequestProcess
						.initWorkflow(leaveWaterRequestProcessBean));

				// System.out.println("Prescriptiondoc() Name :: "+leaveWaterBean.getPrescriptiondoc());

				File reportFile = new File(session.getServletContext().getResource("/repository/").getPath());

				if (leaveWaterBean.getPrescriptionFile() != null) {
					if (!leaveWaterBean.getPrescriptionFile().isEmpty()) {
						String fileName = fileUpload.uploadFile(
								leaveWaterBean.getPrescriptionFile(),
								"PrescriptionCertificate",
								leaveWaterBean.getPrescriptionFile()
										.getOriginalFilename().split("\\.")[1],
								request.getServletContext().getRealPath(
										"/leaveFiles/"), CPSConstants.CHANGE);
					}
				}

				leaveWaterBean.setRequestID(leaveWaterRequestProcessBean
						.getRequestID());
				session.setAttribute("leaveWaterAprovalRequestID",
						leaveWaterBean.getRequestID());
				session.setAttribute("leaveAprovalRequestID",
						leaveWaterBean.getRequestID());
				// viewName = CPSConstants.ERPLEAVEAPPLICATION;
				viewName = CPSConstants.REQUESTRESULTPAGE;
			} else if (CPSUtils
					.compareStrings(CPSConstants.LEAVEDATEEXISTORNOT,
							leaveWaterBean.getParam())) {
				log.debug("levae controller 5th condition");
				leaveWaterRequestBusiness.getLeaveDateChecking(leaveWaterBean);
				viewName = CPSConstants.ERPLEAVEAPPLICATION;

			} else if (CPSUtils.compareStrings(CPSConstants.ERPLEAVECANCEL,
					leaveWaterBean.getParam())) {
				log.debug("levae controller 6th condition");
				leaveWaterRequestProcess.cancelErpLeave(leaveWaterBean);
				viewName = CPSConstants.REQUESTRESULTPAGE;
				// viewName = CPSConstants.REQUESTRESULTPAGE;
			} else if (CPSUtils.compareStrings(
					CPSConstants.APPLICATIONCANCELFORMHOME,
					leaveWaterBean.getParam())) {
				log.debug("levae controller 7th condition 21/06/2016");
				leaveWaterBean = leaveWaterRequestProcess
						.erpLeaveCancelHome(leaveWaterBean);
				viewName = CPSConstants.ERPLEAVECANCELHOME;
			} else if (CPSUtils.compareStrings(
					CPSConstants.ERPLEAVECANCELREQUEST,
					leaveWaterBean.getParam())) {
				log.debug("levae controller 8th condition");
				leaveWaterBean = leaveWaterRequestBusiness
						.getEmpDetails(leaveWaterBean);
				LeaveWaterRequestProcessBean leaveWaterRequestProcessBean = new LeaveWaterRequestProcessBean();
				leaveWaterBean.setSession(session);
				BeanUtils.copyProperties(leaveWaterRequestProcessBean,
						leaveWaterBean);
				leaveWaterBean.setResult(leaveWaterRequestProcess
						.initWorkflow1(leaveWaterRequestProcessBean));
				leaveWaterBean.setRequestID(leaveWaterRequestProcessBean
						.getRequestID());
				session.setAttribute("leaveWaterAprovalRequestID",
						leaveWaterBean.getRequestID());
				session.setAttribute("leaveAprovalRequestID",
						leaveWaterBean.getRequestID());
				session.setAttribute("leaveAprovalcopyRequestID",
						leaveWaterBean.getCancelRequestId());
				// viewName = CPSConstants.ERPLEAVEAPPLICATION;
				viewName = CPSConstants.REQUESTRESULTPAGE;
			} else if (CPSUtils.compareStrings(
					CPSConstants.APPLICATIONAMENDFORMHOME,
					leaveWaterBean.getParam())) {
				log.debug("levae controller 9th condition 22/06/2016");
				// --------------------------***ERP Leave Amendment
				// Home*****---------------------//
				leaveWaterBean = leaveWaterRequestProcess
						.getErpLeaveDetails(leaveWaterBean);
				viewName = CPSConstants.ERPLEAVEAMENDHOME;
			}

			mav = new ModelAndView(viewName, CPSConstants.LEAVEWATERREQUEST,
					leaveWaterBean);
			if (!CPSUtils.isNullOrEmpty(leaveWaterBean.getResult()))
				if (CPSUtils.compareStrings(leaveWaterBean.getResult(),
						CPSConstants.SUCCESS)) {
					mav.addObject(CPSConstants.MESSAGE,
							leaveWaterBean.getResult());
				} else {
					leaveWaterBean.setRemarks(leaveWaterBean.getResult());
				}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return mav;
	}

	// @RequestMapping(value = "/searchEmployee.htm", method ={
	// RequestMethod.GET,RequestMethod.POST })

	@RequestMapping(value = "/searchLeaveFromDateCheck.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void getValidateFromDate(
			@ModelAttribute(CPSConstants.LEAVEWATERREQUEST) LeaveWaterApplicationBean leaveWaterBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = null;
		session = request.getSession(true);
		if (CPSUtils.isNullOrEmpty(leaveWaterBean.getSfID())
				|| CPSUtils.compareStrings(CPSConstants.SELF,
						leaveWaterBean.getSfID())) {
			leaveWaterBean.setSfID(session.getAttribute(CPSConstants.SFID)
					.toString());
		}

		leaveWaterRequestBusiness.getLeaveDateChecking(leaveWaterBean);
		response.setContentType("text/json");
		JSON json = JSONSerializer.toJSON(leaveWaterBean);
		response.getWriter().print(json.toString());
	}

	@RequestMapping(value = "/searchLeaveToDateCheck.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void getValidateToDate(
			@ModelAttribute(CPSConstants.LEAVEWATERREQUEST) LeaveWaterApplicationBean leaveWaterBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = null;
		session = request.getSession(true);
		if (CPSUtils.isNullOrEmpty(leaveWaterBean.getSfID())
				|| CPSUtils.compareStrings(CPSConstants.SELF,
						leaveWaterBean.getSfID())) {
			leaveWaterBean.setSfID(session.getAttribute(CPSConstants.SFID)
					.toString());
		}
		leaveWaterRequestBusiness.getLeaveToDateChecking(leaveWaterBean);
		response.setContentType("text/json");
		JSON json = JSONSerializer.toJSON(leaveWaterBean);
		response.getWriter().print(json.toString());
	}

	@RequestMapping(value = "/searchNoOfLeaveDays.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void getNoOfLeaveDays(
			@ModelAttribute(CPSConstants.LEAVEWATERREQUEST) LeaveWaterApplicationBean leaveWaterBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = null;
		session = request.getSession(true);
		if (CPSUtils.isNullOrEmpty(leaveWaterBean.getSfID())
				|| CPSUtils.compareStrings(CPSConstants.SELF,
						leaveWaterBean.getSfID())) {
			leaveWaterBean.setSfID(session.getAttribute(CPSConstants.SFID)
					.toString());
		}
		leaveWaterRequestBusiness.getLeaveNoOfDaysChecking(leaveWaterBean);
		response.setContentType("text/json");
		JSON json = JSONSerializer.toJSON(leaveWaterBean);
		response.getWriter().print(json.toString());
	}

	@RequestMapping(value = "/thisLeaveAvailableOrNotForU.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void thisLeaveAvailableOrNotForU(
			@ModelAttribute(CPSConstants.LEAVEWATERREQUEST) LeaveWaterApplicationBean leaveWaterBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = null;
		session = request.getSession(true);
		if (CPSUtils.isNullOrEmpty(leaveWaterBean.getSfID())
				|| CPSUtils.compareStrings(CPSConstants.SELF,
						leaveWaterBean.getSfID())) {
			leaveWaterBean.setSfID(session.getAttribute(CPSConstants.SFID)
					.toString());
		}

		leaveWaterRequestBusiness.thisLeaveAvailableOrNotForU(leaveWaterBean);
		response.setContentType("text/json");
		JSON json = JSONSerializer.toJSON(leaveWaterBean);
		response.getWriter().print(json.toString());
	}

	@RequestMapping(value = "/downlodedPrescriptionCopy.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void downlodedPrescriptionCopy(
			@ModelAttribute(CPSConstants.LEAVEWATERREQUEST) LeaveWaterApplicationBean leaveWaterBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = null;
		session = request.getSession(true);
		if (CPSUtils.isNullOrEmpty(leaveWaterBean.getSfID())
				|| CPSUtils.compareStrings(CPSConstants.SELF,
						leaveWaterBean.getSfID())) {
			leaveWaterBean.setSfID(session.getAttribute(CPSConstants.SFID)
					.toString());
		}
		String viewName = null;
		ModelAndView mav = null;
		/*
		 * response.setContentType("text/html"); PrintWriter out =
		 * response.getWriter(); String filename =
		 * "PrescriptionCertificate_43619.doc"; String filepath =
		 * "/home/srinivas/leaveFiles/";
		 * response.setContentType("APPLICATION/OCTET-STREAM");
		 * response.setHeader("Content-Disposition","attachment; filename=\"" +
		 * filename + "\"");
		 * 
		 * FileInputStream fileInputStream = new FileInputStream(filepath +
		 * filename);
		 * 
		 * int i; while ((i=fileInputStream.read()) != -1) { out.write(i); }
		 * fileInputStream.close(); out.close();
		 */

		log.debug("LeaveRequestController --> onSubmit --> param=getDbFile");
		String file = "/home/srinivas/leaveFiles/PrescriptionCertificate_43619.doc";
		// FilesBean file =
		// leaveRequestBusiness.getDBFile(Integer.parseInt(leaveBean.getFileId()));

		// response.setContentType(file.getType());
		// response.setContentLength(file.getFile().length);
		// response.setHeader("Content-Disposition", "inline; filename=\"" +
		// file.getFilename() + "\"");
		response.setHeader("Content-Disposition", "inline; filename=\"" + file
				+ "\"");
		// FileCopyUtils.copy(file.getFile(), response.getOutputStream());
		viewName = CPSConstants.VIEWLEAVE;

		mav = new ModelAndView(viewName, CPSConstants.LEAVEREQUEST,
				leaveWaterBean);
		// return mav;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pdfDownloded.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void pdfDownloded(
			@ModelAttribute(CPSConstants.LEAVEWATERREQUEST) LeaveWaterApplicationBean leaveWaterBean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = null;
		session = request.getSession(true);
		// leaveWaterBean.setRequestID("43650");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", leaveWaterBean.getRequestID());
		InputStream jasperStream = session.getServletContext()
				.getResourceAsStream("/jasper/LeaveRequestForm.jasper");
		// InputStream jasperStream =
		// session.getServletContext().getResourceAsStream("/jasper/ConsultationReport.jasper");

		JasperPrint jasperPrint = leaveWaterRequestProcess.generateReport(
				jasperStream, params);
		response.setContentType("application/x-pdf");
		response.setHeader(
				"Content-disposition",
				"inline; filename=ApplicationTxn_Report_"
						+ leaveWaterBean.getRequestID() + ".pdf");

		final ServletOutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

	}

}
