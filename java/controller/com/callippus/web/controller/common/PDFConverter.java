package com.callippus.web.controller.common;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

public class PDFConverter {
	private Log log = LogFactory.getFactory().getInstance(this.getClass().getName());

	public PDFConverter(String xmlFilePath, String xslFilePath, String pdfFilePath, String root) throws Exception {
		log.debug("Entered PDFConvert  File 1");
		// renderToPDF( xmlFilePath,xslFilePath, pdfFilePath);
		try {
			Render2PDF.render2PDF(new File(xmlFilePath), new File(xslFilePath), new File(pdfFilePath));
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
	}
}
