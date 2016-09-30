package com.callippus.web.controller.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.InputHandler;
import org.apache.fop.apps.XSLTInputHandler;

/**
 * This class demonstrates the conversion of an XML file to PDF using JAXP (XSLT) and FOP (XSL:FO).
 */
public class Render2PDF {
	private static Log log = LogFactory.getLog("com.callippus.irv.common.Render2PDF");

	// public static void render2PDF(File xmlfile, File xsltfile, File pdffile) {
	// try {
	//
	// Configurator.configure();
	// JAXPTransformer transform;
	// try {
	//          
	// JAXPErrorHandler ehandler = new JAXPErrorHandler(
	// JAXPErrorHandler.ABORT_ON_ERROR );
	//    	
	// // Define a tranform object
	// transform = new JAXPTransformer();
	//        
	// transform.setXMLDocument(xmlfile);
	//        
	// transform.setXSLDocument(xsltfile);
	//        
	// transform.setBaseURL(xsltfile);
	//
	// transform.setErrorHandler(ehandler);
	//
	// transform.setOutputFolder(pdffile);
	//        
	// transform.transform();
	//       
	// } finally {
	//         
	// }
	//      
	// System.out.println("Success!");
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// }
	public static void render2PDF(File xmlfile, File xsltfile, File pdffile) {
		try {
			Driver driver = null;
			OutputStream out = new FileOutputStream(pdffile);
			try {

//				// Instantiate org.apache.fop.apps.Driver.
//				driver = new Driver();
//
//				// Set the type of rendering you want to do.
//				driver.setRenderer(Driver.RENDER_PDF);
//
//				// Set an Input Handler as an XSLTInputHandler.
//				InputHandler inputHandler = new XSLTInputHandler(xmlfile, xsltfile);
//
//				// Set an output stream to render to.
//				driver.setOutputStream(out);
//
//				// Grab the parser out of this handler and render.
//				driver.render(inputHandler.getParser(), inputHandler.getInputSource());
			} finally {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

}
