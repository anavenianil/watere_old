package com.callippus.web.controller.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.impl.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Service
public class JasperReportCreator {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * This method is common for generating PDF reports for all types of request
	 * 
	 * @param session
	 * @param jasperFileName
	 * @param parameterMap
	 * @param folderName
	 * @param fileName
	 * @param date
	 * @throws Exception
	 */

	public JasperPrint createPdfStream(InputStream jasperStream, Map params)
			throws Exception {
		Session session = null;
		session = hibernateUtils.getSession();// session =
												// sessionFactory.openSession();

		SessionImpl sessionImpl = (SessionImpl) session;
		Connection conn = sessionImpl.connection();

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				params, conn);

		return jasperPrint;
	}

	public void createPdf(HttpSession session, String jasperFileName,
			HashMap<String, String> parameterMap, String folderName,
			String fileName, boolean date) throws Exception {
		Session dbsession = null;
		String datetime = null;
		Calendar cl = Calendar.getInstance();
		try {
			dbsession = hibernateUtils.getSession();// session =
													// sessionFactory.openSession();

			SessionImpl sessionImpl = (SessionImpl) dbsession;
			Connection conn = sessionImpl.connection();

			if (date) {
				datetime = cl.get(Calendar.YEAR) + "" + cl.get(Calendar.MONTH)
						+ 1 + "" + cl.get(Calendar.DATE) + ""
						+ cl.get(Calendar.HOUR) + "" + cl.get(Calendar.MINUTE)
						+ "" + cl.get(Calendar.SECOND);
				fileName += "_" + datetime;
			}
			JasperReport jr = (JasperReport) JRLoader.loadObject(session
					.getServletContext().getResourceAsStream(
							"/jasper/" + jasperFileName));
			JasperPrint jp = JasperFillManager.fillReport(jr,
					(Map) parameterMap, conn);
			File reportFile = new File(session.getServletContext()
					.getResource("/repository/" + folderName).getPath());
			if (!reportFile.exists())
				reportFile.mkdir();
			OutputStream fileout = new FileOutputStream(new File(
					reportFile.getAbsolutePath() + "/" + fileName + ".pdf"));
			JRPdfExporter pdfExporter = new JRPdfExporter();
			pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			pdfExporter
					.setParameter(JRExporterParameter.OUTPUT_STREAM, fileout);
			pdfExporter.exportReport();

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e)
					.getResultStatus().getErrorCode());
		} finally {

		}
	}
}
