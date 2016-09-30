package com.callippus.web.controller.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.test.TestDetailsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.controller.common.JasperReportCreator;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.reportDetails.IReportDetailsDAO;

@Controller
@SessionAttributes
public class TestDetailsController {

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private FileUpload fileUpload;
	@Autowired
	private JasperReportCreator jasperReportCreator;

	@RequestMapping(value = { "/hello.htm" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public void report(
			@ModelAttribute(value = "test") TestDetailsBean testBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		InputStream jasperStream = null;
		HashMap<String, String> params = null;
		if (CPSUtils.compareStrings(CPSConstants.EXPENSEESCLAIM,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/ExpenseesClaim.jasper");
			params = new HashMap<String, String>();
			params.put("requestId", testBean.getRequestId());

		} else if (CPSUtils.compareStrings(CPSConstants.LEAVEREQUESTFORM,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/LeaveRequestForm.jasper");
			params = new HashMap<String, String>();
			params.put("requestID", testBean.getRequestID());

		} else if (CPSUtils.compareStrings(CPSConstants.APPLICATIONFORRECESS,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/APPLICATION FOR RECESS.jasper");
			params = new HashMap<String, String>();
			params.put("requestID", testBean.getRequestID());
			params.put("addressTypeId1", testBean.getAddressTypeId1());
			params.put("addressTypeId2", testBean.getAddressTypeId2());

		} else if (CPSUtils.compareStrings(CPSConstants.EMPDETAILS,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/EmpDetails.jasper");
			params = new HashMap<String, String>();
		}
		/*
		 * InputStream jasperStream = session.getServletContext()
		 * .getResourceAsStream("/jasper/LeaveRequestForm.jasper");
		 * 
		 * HashMap<String,String> params = new HashMap<String,String>();
		 * params.put("requestID",testBean.getRequestID());
		 */

		JasperPrint jasperPrint = jasperReportCreator.createPdfStream(
				jasperStream, params);
		response.setContentType("application/x-pdf");
		// response.setHeader("Content-disposition","inline; filename=Test.pdf");
		response.setHeader("Content-disposition", "inline; filename="
				+ testBean.getParam() + ".pdf");
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}

	@RequestMapping(value = { "/test.htm" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView execute(
			@ModelAttribute(value = "test") TestDetailsBean testBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		try {
			if (CPSUtils.compareStrings("testHome", testBean.getParam())) {
				viewName = "test";
				mav = new ModelAndView(viewName, "test", testBean);
			} else if (CPSUtils.compareStrings("download", testBean.getParam())) {
				FilesBean file = fileUpload.downloadFromDatabase(1);
				response.setContentType(file.getType());
				response.setContentLength(file.getFile().length);
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + file.getFilename() + "\"");
				FileCopyUtils.copy(file.getFile(), response.getOutputStream());
				mav = new ModelAndView(viewName, "test", testBean);
			} else if (CPSUtils.compareStrings("submitTest",
					testBean.getParam())) {

				// Upload File
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> files = multiRequest.getFileMap();
				Map<String, MultipartFile> map = new HashMap<String, MultipartFile>();
				map.put(testBean.getDesc(), testBean.getUploadFile());
				FileUploadBean fub = new FileUploadBean();
				fub.setFiles(map);
				testBean.setFileUploadBean(fub);
				testBean.setFileUploadBean(fileUpload
						.uploadFileToDatabase(testBean.getFileUploadBean()));
				System.out.println(testBean.getFileUploadBean().getFileIds()
						.get(testBean.getDesc()));
				// session1 = hibernateUtils.getSession();
				// con = session1.connection();
				// TestDeatailsDTO testdto=new TestDeatailsDTO();
				// testdto.setId(3);
				// testdto.setOriginalFile(new File(testBean.getTestName()));
				// FileInputStream fs=new
				// FileInputStream("C:/Documents and Settings/mis/Desktop/New Folder (2)/abc.doc");
				// PreparedStatement
				// ps=con.prepareStatement("insert into test_master values(?,?)");
				// ps.setInt(1, testdto.getId());
				// ps.setBinaryStream(2,fs,fs.available());
				// int i=ps.executeUpdate();
				// System.out.println(i);
				// TestMasterDTO testmaster = new TestMasterDTO();
				// // testmaster.setId(4);
				// testmaster.setName("Test");
				// testmaster.setStatus(1);
				// testmaster.setCreationTime(CPSUtils.getCurrentDateWithTime());
				// testmaster.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				// session1.saveOrUpdate(testmaster);
				//
				// int intId = ((Integer)
				// session1.getIdentifier(testmaster)).intValue();
				// System.out.println("id:" + intId);
				// //
				// session1.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				// String test = (String)
				// session1.createSQLQuery("select name from test_master where id=?").setInteger(0,
				// intId).uniqueResult();
				// System.out.println("SQL test1 : " + test);
				//
				// TestMasterDTO testmaster1 = (TestMasterDTO)
				// session1.get(TestMasterDTO.class, testmaster.getId());
				// System.out.println("Get test : " + testmaster1.getName());
				//
				// testmaster1 = (TestMasterDTO)
				// session1.createCriteria(TestMasterDTO.class).add(Expression.eq("id",
				// testmaster.getId())).uniqueResult();
				// System.out.println("Criteria test : " +
				// testmaster1.getName());
				//
				// TestDeatailsDTO testDetailsDTO = new TestDeatailsDTO();
				// testDetailsDTO.setTestName("Test");
				// testDetailsDTO.setTestMasterId(String.valueOf(testmaster.getId()));
				// session1.saveOrUpdate(testDetailsDTO);
				// //
				// session1.createSQLQuery("insert into test_master values(5,'test',1,'17-JAN-2012','17-JAN-2012','no desc')").executeUpdate();
				// //
				// session1.createSQLQuery("insert into test_details values(1,'sample','5')").executeUpdate();
				// hibernateUtils.commitTransaction();
				//
				// test = (String)
				// session1.createSQLQuery("select name from test_master where id=?").setInteger(0,
				// intId).uniqueResult();
				// System.out.println("SQL test2 : " + test);
				//
				viewName = "test";
				mav = new ModelAndView(viewName, "test", testBean);
			}
		} catch (Exception e) {
			hibernateUtils.rollbackTransaction();
			throw new ResultStatus(SetErpException.setException(e)
					.getResultStatus().getErrorCode());
		} finally {
			hibernateUtils.closeSession();
		}
		return mav;
	}

	// added by bkr

	@RequestMapping(value = { "/reports.htm" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public void reports(
			@ModelAttribute(value = "test") TestDetailsBean testBean,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		InputStream jasperStream = null;
		HashMap<String, String> params = null;
		if (CPSUtils.compareStrings(CPSConstants.EXPENSEESCLAIM,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/ExpenseesClaim.jasper");
			params = new HashMap<String, String>();
			params.put("requestId", testBean.getRequestId());

		} else if (CPSUtils.compareStrings(CPSConstants.LEAVEREQUESTFORM,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/LeaveRequestForm.jasper");
			params = new HashMap<String, String>();
			params.put("requestID", testBean.getRequestID());

		} else if (CPSUtils.compareStrings(CPSConstants.APPLICATIONFORRECESS,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/APPLICATION FOR RECESS.jasper");
			params = new HashMap<String, String>();
			params.put("requestID", testBean.getRequestID());
			params.put("addressTypeId1", testBean.getAddressTypeId1());
			params.put("addressTypeId2", testBean.getAddressTypeId2());

		} else if (CPSUtils.compareStrings(CPSConstants.EMPDETAILS,
				testBean.getParam())) {
			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/EmpDetails.jasper");
			params = new HashMap<String, String>();

		} else if (CPSUtils.compareStrings(CPSConstants.ANNUALLEAVE,
				testBean.getParam())) {

			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/AnnualLeave.jasper");
			params = new HashMap<String, String>();
			params.put("ltcYear", testBean.getLtcYear());
			params.put("status", testBean.getStatus());

		} else if (CPSUtils.compareStrings(CPSConstants.TADADETAILS,
				testBean.getParam())) {

			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/TadaDetails.jasper");
			params = new HashMap<String, String>();
			params.put("fromDate", testBean.getFromDate());
			params.put("toDate", testBean.getToDate());
			params.put("status", testBean.getStatus());

		} else if (CPSUtils.compareStrings(CPSConstants.LEAVEBALANCES,
				testBean.getParam())) {

			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/LeaveBalances.jasper");
			params = new HashMap<String, String>();
		} else if (CPSUtils.compareStrings(CPSConstants.RETRIEDEMPLOYEELIST,
				testBean.getParam())) {

			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/RetirementDetails.jasper");
			params = new HashMap<String, String>();

		} else if (CPSUtils.compareStrings(CPSConstants.RETRIEDEMPLOYEELISTTEST,
				testBean.getParam())) {

			jasperStream = session.getServletContext().getResourceAsStream(
					"/jasper/RetirementDetailsOne.jasper");
			params = new HashMap<String, String>();

		}

		JasperPrint jasperPrint = jasperReportCreator.createPdfStream(
				jasperStream, params);
		response.setContentType("application/x-pdf");
		// response.setHeader("Content-disposition","inline; filename=Test.pdf");
		response.setHeader("Content-disposition", "inline; filename="
				+ testBean.getParam() + ".pdf");
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}

}
