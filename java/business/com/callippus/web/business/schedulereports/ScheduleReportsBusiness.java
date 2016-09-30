package com.callippus.web.business.schedulereports;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.incometax.IncomeTaxMasterBean;
import com.callippus.web.beans.reports.ReportsBean;
import com.callippus.web.beans.schedulereports.ScheduleReportsBean;
import com.callippus.web.business.incometax.IncomeTaxBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
@Service
public class ScheduleReportsBusiness {
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IncomeTaxBusiness incomeTaxBusiness;
	@SuppressWarnings("unchecked")
	
	public ScheduleReportsBean getScheduleReportHome(ScheduleReportsBean schedulereportsBean, HttpServletRequest request) throws Exception
	{
		try
		{
			//schedulereportsBean.setCategoryList(commonDataDAO.getMasterData("PayBillEmpCategoryDTO")); // old code
			schedulereportsBean.setCategoryList(commonDataDAO.getListOfCategoryDetails(schedulereportsBean)); 
		}catch(Exception e) {
			throw e;
		}
		return schedulereportsBean;
	}

	@SuppressWarnings("deprecation")
	public ScheduleReportsBean getPdfReport(ScheduleReportsBean schedulereportsBean, HttpServletRequest request, Session dbsession,HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession(true);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String message = "";
		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			File jasparPath = new File(session.getServletContext().getResource("/jasper/ScheduleReports").getPath());
			if (request.getParameter("reportType").equals("HBA PRINCIPLE") || request.getParameter("reportType").equals("HBA INTEREST") || request.getParameter("reportType").equals("GPF")|| 
					request.getParameter("reportType").equals("CGHS")|| request.getParameter("reportType").equals("CGEIS")|| request.getParameter("reportType").equals("INCOME TAX")|| request.getParameter("reportType").equals("PROF TAX")|| 
					request.getParameter("reportType").equals("LFCHARGES")|| request.getParameter("reportType").equals("CONVEYANCE")||
					request.getParameter("reportType").equals("Car Principle")||
					request.getParameter("reportType").equals("Car Interest")||
					request.getParameter("reportType").equals("FESTIVAL")||
					request.getParameter("reportType").equals("PC PRINCIPLE")||
					request.getParameter("reportType").equals("PC INTEREST")||
					request.getParameter("reportType").equals("Cycle")||
					request.getParameter("reportType").equals("Scooter Principle")||
					request.getParameter("reportType").equals("Scooter Interest")||
					request.getParameter("reportType").equals("REGULAR PAYBILL")
					|| request.getParameter("reportType").equals("CUTTING SHEET(CASH STATEMENT)")
					|| request.getParameter("reportType").equals("OT")
				    || request.getParameter("reportType").equals("PAYBILL ACQUITTANCE ROLL")
				    || request.getParameter("reportType").equals("NPS")
				    || request.getParameter("reportType").equals("N-GRAMS FORM")
				    || request.getParameter("reportType").equals("NPS")
				   || request.getParameter("reportType").equals("LTC") || request.getParameter("reportType").equals("TADA"))
				   
			{
				if(request.getParameter("reportType").equals("OT"))
				{
					parameters.put("CategoryID", schedulereportsBean.getValue1());
					String date = schedulereportsBean.getSchedulemonth().toString();
					String[] tempDate = date.split(" ");
					String runMonth = tempDate[1].toUpperCase() + "-" + tempDate[5];
					parameters.put("Month", runMonth);
				}
				else
				{
					String[] categoryid = schedulereportsBean.getValue1().split(",");
					List<Object> value = new ArrayList<Object>();
					for (int i = 0; i < categoryid.length; i++) {
						value.add(categoryid[i]);
					}
					parameters.put("catId",value);
	            	parameters.put("CategoryID",value);
	            	parameters.put("Month",sdf.format(schedulereportsBean.getValue2()));
				}
			}
			else if(request.getParameter("reportType").equals("PAYSLIP"))
			{
				parameters.put("SFID",session.getAttribute(CPSConstants.SFID).toString());
				if(!CPSUtils.isNullOrEmpty(schedulereportsBean.getPaySlipYear()))
					parameters.put("Month",schedulereportsBean.getPaySlipYear());
				else
					parameters.put("Month",session.getAttribute("runMonth"));
			}
			else if(request.getParameter("reportType").equals("catList"))
			{
				String[] categoryid = schedulereportsBean.getValue1().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < categoryid.length; i++)
					value.add(categoryid[i]);
				parameters.put("catId",value);
			}
			else if(request.getParameter("reportType").equals("incomeTaxSlip"))
			{
				parameters.put("SFID",session.getAttribute(CPSConstants.SFID));
				parameters.put("FINYEARID",Integer.parseInt(schedulereportsBean.getFinancialYearId()));
				parameters.put("RUNTYPE",schedulereportsBean.getSlipType());
				parameters.put("FINYEAR",incomeTaxBusiness.getFinancialYear(Integer.parseInt(schedulereportsBean.getFinancialYearId())));
			}
			else if(request.getParameter("reportType").equals("incomeTaxSlipFinance"))
			{
				parameters.put("SFID", schedulereportsBean.getSfid());
				parameters.put("FINYEARID",Integer.parseInt(schedulereportsBean.getFinancialYearId()));
				parameters.put("RUNTYPE",schedulereportsBean.getSlipType());
				parameters.put("FINYEAR",incomeTaxBusiness.getFinancialYear(Integer.parseInt(schedulereportsBean.getFinancialYearId())));
			}
			else if(request.getParameter("reportType").equals("daArrearsDetails"))
			{
				parameters.put("daTypeFlag",schedulereportsBean.getDaTypeFlag());
				parameters.put("fromDate",schedulereportsBean.getFromDate());
				parameters.put("toDate",schedulereportsBean.getToDate());
				parameters.put("categoryId",Integer.parseInt(schedulereportsBean.getCategoryID()));
			}
			else if(CPSUtils.compareStrings("getFinancePaySlip", schedulereportsBean.getParam()))
			{
				parameters.put("MON",schedulereportsBean.getValue2());
				parameters.put("SFID",schedulereportsBean.getSfid());
			}
			else if(CPSUtils.compareStrings("getFinancePaySlipNew", schedulereportsBean.getParam()))
			{
				parameters.put("Month",schedulereportsBean.getValue1());
				parameters.put("SFID",schedulereportsBean.getSfid());
			}else if(CPSUtils.compareStrings("payBillComparisionReport", schedulereportsBean.getParam())){
				parameters.put("runMonth", sdf.format(schedulereportsBean.getSchedulemonth()));
			}
			else if(request.getParameter("reportType").equals("incomeTaxForm16"))
				parameters.put("SFID", schedulereportsBean.getSfid());
			else if(request.getParameter("reportType").equals("incomeTaxForm16Finance"))
				parameters.put("SFID", schedulereportsBean.getSfid());
			else if(CPSUtils.compareStrings("ireportPayValidationDetails", schedulereportsBean.getParam()))
				jasparPath = new File(session.getServletContext().getResource("/jasper/ValidationReports").getPath());
			else if(CPSUtils.compareStrings("printDataValidationReports", schedulereportsBean.getParam()))
				jasparPath = new File(session.getServletContext().getResource("/jasper/EssValidationReports").getPath());
			
			parameters.put("SubReportPath", jasparPath.getAbsolutePath() + File.separatorChar);
			
			response.setContentType("application/pdf");
			ServletOutputStream os = response.getOutputStream();
		
			InputStream is = session.getServletContext().getResourceAsStream(schedulereportsBean.getJasperFile());
     	    JasperRunManager.runReportToPdfStream(is, os, parameters, dbsession.connection());
			
			os.flush();
			os.close();
			is.close();

		  /*InputStream sourceFile = session.getServletContext().getResourceAsStream(schedulereportsBean.getJasperFile());
			JasperReport jr = (JasperReport) JRLoader.loadObject(sourceFile);
			jr.setWhenNoDataType(JasperReport.WHEN_NO_DATA_TYPE_NO_DATA_SECTION);
			JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dbsession.connection());
			File reportFile = new File(session.getServletContext().getResource("/PdfAndExcelReports").getPath());
			if (!reportFile.exists())
				reportFile.mkdir();
			OutputStream fileout = new FileOutputStream(new File(reportFile.getAbsolutePath() + "/" + schedulereportsBean.getPdfFile()));
			String filepath1 = "../PdfAndExcelReports/" + schedulereportsBean.getPdfFile();
			session.setAttribute("reportpath", filepath1);
			JRPdfExporter pdfExporter = new JRPdfExporter();
			pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fileout);
			pdfExporter.exportReport();*/
		}
		catch(Exception e)
		{
			throw e;
		}
		return schedulereportsBean;
	}
	//reports in excel format
	public ScheduleReportsBean getExcelReport(ScheduleReportsBean scheduleReportsBean, HttpServletRequest request, Session dbsession, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		try {

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			File jasparPath = new File(session.getServletContext().getResource("/jasper/EssValidationReports").getPath());
			parameters.put("SubReportPath", jasparPath.getAbsolutePath() + File.separatorChar);
			
			
			InputStream sourceFile = session.getServletContext().getResourceAsStream(scheduleReportsBean.getJasperFile());
			JasperReport jr = (JasperReport) JRLoader.loadObject(sourceFile);
			JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dbsession.connection());
			File reportFile = new File(session.getServletContext().getResource("/PdfAndExcelReports").getPath());
			reportFile.mkdirs();
			String filepath1 = "../PdfAndExcelReports/" + scheduleReportsBean.getExportedFile();
			session.setAttribute("reportpath", filepath1);
			OutputStream fileout = new FileOutputStream(new File(reportFile.getAbsolutePath() + scheduleReportsBean.getExportedFile()));
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, fileout);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.exportReport();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Content-Disposition", "inline; filename=\"" + scheduleReportsBean.getExportedFile() + "\"");
			File f = new File(session.getServletContext().getResource("/PdfAndExcelReports/").getPath() + scheduleReportsBean.getExportedFile());
			OutputStream out = response.getOutputStream();
			if (f.length() > 0) {
				BufferedInputStream in = new BufferedInputStream(session.getServletContext().getResourceAsStream("/PdfAndExcelReports/" + scheduleReportsBean.getExportedFile()));
				int length;
				byte[] buf = new byte[1024];
				while ((in != null) && ((length = in.read(buf)) != -1)) {
					out.write(buf, 0, length);
				}
			} else
				out.write(0);

			out.close();

		} catch (Exception e) {
			throw e;
		}

		return scheduleReportsBean;
	}
	public ScheduleReportsBean getScheduleTypes(ScheduleReportsBean scheduleReportsBean) throws Exception{
		Session session=null;
		List<KeyValueDTO> schedulesList=null;
		try{
			schedulesList=commonDataDAO.getScheduleTypes();
			scheduleReportsBean.setScheduleTypes(schedulesList);
		}catch(Exception e){
			throw e;
		}
		return scheduleReportsBean;
	}

}
