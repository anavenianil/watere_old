package com.callippus.web.controller.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.login.LoginBean;
import com.callippus.web.beans.requests.PISRequestBean;
import com.callippus.web.business.employee.CreateEmployeeBusiness;
import com.callippus.web.business.requestprocess.PISRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
// @RequestMapping(value="/employee.htm")
@SessionAttributes
public class CreateEmployeeController {

	private static Log log = LogFactory.getLog(CreateEmployeeController.class);
	@Autowired
	private CreateEmployeeBusiness createEmployeeBusiness;
	@Autowired
	private PISRequestProcess pisRequestProcess;
	LoginBean login = null;
	@Autowired
	private FileUpload fileUpload;

	@RequestMapping(value = "/employee.htm", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute EmployeeBean employee,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewPage = null;
		String message1 = null;

		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(employee.getParam())) {
				employee.setParam(CPSConstants.VIEWPROFILE);
			}

			if (CPSUtils.compareStrings(CPSConstants.HOME, employee.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect"))
						&& (CPSUtils.compareStrings(
								request.getParameter("redirect"), "true"))) {
					log.debug("::CreateEmployeeController --> onSubmit Start--> param = home");
					employee = new EmployeeBean();
					employee = createEmployeeBusiness
							.getEmployeeGeneralDetails(session, employee);
					session.setAttribute(CPSConstants.GENERATEDID,
							employee.getGeneratedId());

					// /added by bkr 15/05/2016

					/*
					 * employee =
					 * createEmployeeBusiness.getProfessionalDetails(session,
					 * employee); session.setAttribute(CPSConstants.RETURN,
					 * CPSConstants.EMPPROFESSIONDETAILS);
					 */

					// /

					mav = new ModelAndView(CPSConstants.EMPGENERALDETAILS,
							CPSConstants.EMPLOYEE, employee);
					mav.addObject(CPSConstants.SFID, employee.getSfid());
					session.setAttribute(CPSConstants.RETURN,
							CPSConstants.EMPGENERALDETAILS);
					log.debug("::CreateEmployeeController --> onSubmit End--> RESPONSE JSP ------> EmpGeneralDetails");
				} else {
					session.setAttribute("path",
							request.getContextPath() + request.getServletPath()
									+ "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(
							request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.PROFESSIONAL,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param = professional");
				employee = createEmployeeBusiness.getProfessionalDetails(
						session, employee);
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.EMPPROFESSIONDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End -->RESPONSE JSP ------>EmpProfessionalDetails");
				mav = new ModelAndView(CPSConstants.EMPPROFESSIONDETAILS,
						CPSConstants.EMPLOYEE, employee);
			} else if (CPSUtils.compareStrings(CPSConstants.OTHER,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param = OTHER");
				employee = createEmployeeBusiness.getOtherDetails(session,
						employee);
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.EMPOTHERDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End-->RESPONSE JSP ------> EmpOtherDetails");
				mav = new ModelAndView(CPSConstants.EMPOTHERDETAILS,
						CPSConstants.EMPLOYEE, employee);
			} else if (CPSUtils.compareStrings(CPSConstants.PREVIEW,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param = preview");
				employee = createEmployeeBusiness
						.showPreview(session, employee);
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.EMPPROFESSIONDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End --> RESPONSE JSP ------>PreviewData ");
				mav = new ModelAndView(CPSConstants.PREVIEWDATA,
						CPSConstants.EMPLOYEE, employee);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMIT,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start--> param = submit");
				String message = createEmployeeBusiness.submitEmployeeDetails(
						session, employee);
				/*
				 * if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)){
				 * createEmployeeBusiness.submitEmployeeDetails(session,
				 * employee); }
				 */
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
					message = createEmployeeBusiness
							.leaveExceptionalDetails(employee);
				}
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
					message1 = createEmployeeBusiness
							.addOtherEmployeeDetails(employee);
				}
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.EMPOTHERDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit --> End--> RESPONSE JSP ------> EmpOtherDetails ");
				mav = new ModelAndView(CPSConstants.EMPOTHERDETAILS,
						CPSConstants.EMPLOYEE, employee);
				mav.addObject(CPSConstants.MESSAGE, message);
				mav.addObject(CPSConstants.MESSAGE1, message1);
			} else if (CPSUtils.compareStrings(CPSConstants.BACKOTHER,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start--> param=BackOther");
				employee = createEmployeeBusiness
						.backToOther(session, employee);
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.EMPPROFESSIONDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit --> End--> RESPONSE JSP ------> EmpOtherDetails ");
				mav = new ModelAndView(CPSConstants.EMPOTHERDETAILS,
						CPSConstants.EMPLOYEE, employee);
			} else if (CPSUtils.compareStrings(CPSConstants.BACKPROFESSIONAL,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start--> param=BACKPROFESSIONAL");
				employee = createEmployeeBusiness.backToProfessional(session,
						employee);
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.EMPPROFESSIONDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End -->RESPONSE JSP ------>EmpProfessionalDetails");
				mav = new ModelAndView(CPSConstants.EMPPROFESSIONDETAILS,
						CPSConstants.EMPLOYEE, employee);
			} else if (CPSUtils.compareStrings(CPSConstants.BACKHOME,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start--> param=BACKHOME");
				employee = createEmployeeBusiness.backToHome(session, employee);
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.EMPGENERALDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End--> RESPONSE JSP ------> EmpGeneralDetails");
				mav = new ModelAndView(CPSConstants.EMPGENERALDETAILS,
						CPSConstants.EMPLOYEE, employee);
				mav.addObject(CPSConstants.SFID, employee.getSfid());
			} else if (CPSUtils.compareStrings(CPSConstants.VIEWPROFILE,
					employee.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect"))
						&& (CPSUtils.compareStrings(
								request.getParameter("redirect"), "true"))) {
					log.debug("::CreateEmployeeController --> onSubmit Start--> param=VIEWPROFILE");
					employee.setSfid(session.getAttribute(CPSConstants.SFID)
							.toString());
					employee = createEmployeeBusiness.viewEmployeeDetails(
							employee, session);
					session.setAttribute(CPSConstants.RETURN,
							CPSConstants.VIEWEMPLOYEEDETAILS);
					log.debug("::CreateEmployeeController --> onSubmit End--> RESPONSE JSP ------>PreviewData");
					mav = new ModelAndView(CPSConstants.PREVIEWDATA,
							CPSConstants.EMPLOYEE, employee);
				} else {
					session.setAttribute("path",
							request.getContextPath() + request.getServletPath()
									+ "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(
							request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.EDITEMPLOYEE,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param=EDITEMPLOYEE");
				EmployeeBean emp = new EmployeeBean();
				emp.setParam(employee.getParam());
				emp.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				session.removeAttribute("EmployeeAllList");
				emp = createEmployeeBusiness.getAllEmployeeList(session, emp);
				emp.setUserSfid(emp.getSfid());
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.VIEWEMPLOYEEDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End--> RESPONSE JSP ------>ViewEmployeeDetails ");
				if (!CPSUtils.isNull(request.getParameter("redirect"))
						&& (CPSUtils.compareStrings(
								request.getParameter("redirect"), "true"))) {
					mav = new ModelAndView(CPSConstants.VIEWEMPLOYEEDETAILS,
							CPSConstants.COMMAND, emp);
					mav.addObject(CPSConstants.MESSAGE,
							CPSConstants.EDITEMPLOYEE);
				} else {
					session.setAttribute("path",
							request.getContextPath() + request.getServletPath()
									+ "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(
							request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.VIEWEMPLOYEE,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start--> param=VIEWEMPLOYEE");
				employee.setSfid(employee.getUserSfid());
				employee = createEmployeeBusiness.viewEmployeeDetails(employee,
						session);
				employee.setUserSfid(employee.getSfid());
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.VIEWEMPLOYEEDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End-->RESPONSE JSP ------> ViewEmployeeDetails");
				mav = new ModelAndView(CPSConstants.VIEWEMPLOYEEDETAILS,
						CPSConstants.COMMAND, employee);
				mav.addObject(CPSConstants.MESSAGE, CPSConstants.EDITEMPLOYEE);
			} else if (CPSUtils.compareStrings(CPSConstants.SAVEEDITEMPLOYEE,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit --> param=saveEditEmployee");
				String editMessage = null;
				editMessage = createEmployeeBusiness.saveEditEmployee(employee,
						session);
				employee = createEmployeeBusiness.getAllEmployeeList(session,
						employee);
				employee.setSfid(employee.getUserSfid());
				session.setAttribute(CPSConstants.RETURN,
						CPSConstants.VIEWEMPLOYEEDETAILS);
				log.debug("::CreateEmployeeController --> onSubmit End-->RESPONSE JSP ------> ViewEmployeeDetails ");
				mav = new ModelAndView(CPSConstants.VIEWEMPLOYEEDETAILS,
						CPSConstants.COMMAND, employee);
				mav.addObject(CPSConstants.MESSAGE, CPSConstants.EDITEMPLOYEE);
				mav.addObject(CPSConstants.EDITMESSAGE, editMessage);
			} else if (CPSUtils.compareStrings(CPSConstants.CHECKHEAD,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit -->Ajax Call Start param=checkHead");
				employee = createEmployeeBusiness.checkHeadName(employee);
				mav = new ModelAndView(CPSConstants.CHECKHEADNAME,
						CPSConstants.EMPLOYEE, employee);
				log.debug("::CreateEmployeeController --> onSubmit -->Ajax Call End -->RESPONSE JSP ------>CheckHeadName");
			} else if (CPSUtils.compareStrings(CPSConstants.REQUEST,
					employee.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect"))
						&& (CPSUtils.compareStrings(
								request.getParameter("redirect"), "true"))) {
					log.debug("::CreateEmployeeController --> onSubmit Start--> param=request");
					employee = createEmployeeBusiness
							.viewRequestedDetails((String) session
									.getAttribute(CPSConstants.SFID));
					mav = new ModelAndView(CPSConstants.PISREQUESTPAGE,
							CPSConstants.EMPLOYEE, employee);
					log.debug("::CreateEmployeeController --> onSubmit End-->RESPONSE JSP ------> PisRequest");
				} else {
					session.setAttribute("path",
							request.getContextPath() + request.getServletPath()
									+ "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(
							request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit --> param=submitRequest");
				/**
				 * PISRequest mapping employee bean to PISRequestBean
				 */
				PISRequestBean prb = new PISRequestBean();
				BeanUtils.copyProperties(employee, prb);
				prb.setSfID((String) session.getAttribute(CPSConstants.SFID));
				prb.setIpAddress(request.getRemoteAddr());
				// call PISRequestProcess initial workflow method
				String message = pisRequestProcess.initWorkflow(prb);
				employee.setRequestID(prb.getRequestID());
				session.setAttribute("pisrequestID", employee.getRequestID());
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE,
						CPSConstants.EMPLOYEE, employee);
				mav.addObject(CPSConstants.MESSAGE, message);
				if (!CPSUtils.isNullOrEmpty(prb.getRemarks())) {
					mav.addObject(CPSConstants.REMARKS, prb.getRemarks());
				}
			} else if (CPSUtils.compareStrings("selectedDivision",
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit --> param=selectedDivision");
				employee = createEmployeeBusiness.selectedDivisionList(
						employee, session);
				mav = new ModelAndView("SelectedDivision",
						CPSConstants.EMPLOYEE, employee);
				mav.addObject(CPSConstants.DIVISIONLIST,
						employee.getDivisionList());
			}
			// change password functionality
			else if (CPSUtils.compareStrings(CPSConstants.CHANGEPWD,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param=changepwd");
				employee = new EmployeeBean();
				// String employe =
				// createEmployeeBusiness.changePassword(employee);
				// session.setAttribute(CPSConstants.RETURN,
				// CPSConstants.EMPPROFESSIONDETAILS);
				log.debug("::CreateEmploye9eController --> onSubmit End -->RESPONSE JSP ------>EmpProfessionalDetails");
				viewPage = CPSConstants.CHANGEPASSWORD;
				mav = new ModelAndView(viewPage, CPSConstants.EMPLOYEE,
						employee);
				// mav.addObject(CPSConstants.MESSAGE, message);
			} else if (CPSUtils.compareStrings(CPSConstants.CHANGEPWDSUBMIT,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param=changepwdsubmit");
				// String sid = employee.getSfid();
				// String npwd = employee.getNewpassword();
				// System.out.println(sid);
				employee.setSfid((String) session.getAttribute("sfid"));
				String message = createEmployeeBusiness
						.changePassword(employee);
				if (message == "success" && message.equals("success")) {
					log.debug("::CreateEmployeeController -->changepwdsubmit success");
				}
				viewPage = CPSConstants.RESULT;
				mav = new ModelAndView(viewPage, CPSConstants.EMPLOYEE,
						employee);
				mav.addObject(CPSConstants.MESSAGE, message);
			}
			// end of change password
			// Employee Status functionality
			else if (CPSUtils.compareStrings(CPSConstants.EMPSTATUS,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param=empStatus");
				employee = new EmployeeBean();
				employee = createEmployeeBusiness.getEmployeeGeneralDetails(
						session, employee);

				// String message =
				// createEmployeeBusiness.manageEmployeeStatus()
				viewPage = CPSConstants.EMPSTATUSPAGE;
				mav = new ModelAndView(viewPage, CPSConstants.EMPLOYEE,
						employee);
			} else if (CPSUtils.compareStrings(CPSConstants.GETORGNIZATIONDATA,
					employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param=getOrgnizatonData");
				employee = createEmployeeBusiness
						.getEmployeeWithOrgnizationWise(employee);
				session.setAttribute("enableEmpList",
						employee.getEnabledEmployeeList());
				session.setAttribute("disabledEmpList",
						employee.getDisabledEmployeeList());
				viewPage = CPSConstants.EMPSTATUSLIST;
				mav = new ModelAndView(viewPage, CPSConstants.EMPLOYEE,
						employee);
			} else if (CPSUtils.compareStrings(
					CPSConstants.EMPSTATUSCHNAGESUBMIT, employee.getParam())) {
				log.debug("::CreateEmployeeController --> onSubmit Start --> param=empstatusChangeSubmit");
				String message = createEmployeeBusiness.manageEmpStatus(
						(String) session.getAttribute(CPSConstants.SFID),
						employee);
				employee = createEmployeeBusiness
						.getEmployeeWithOrgnizationWise(employee);
				session.setAttribute("enableEmpList",
						employee.getEnabledEmployeeList());
				session.setAttribute("disabledEmpList",
						employee.getDisabledEmployeeList());
				viewPage = CPSConstants.EMPSTATUSLIST;
				mav = new ModelAndView(viewPage, CPSConstants.EMPLOYEE,
						employee);
				// session.setAttribute("message", message);
				mav.addObject(CPSConstants.MESSAGE, message);
			}
			// End Employee Status functionality
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e)
					.getResultStatus().getErrorCode());
		}
		return mav;
	}


	@RequestMapping(value = "/empSearchHome.htm", method = RequestMethod.POST)
	public ModelAndView getPages() {

		ModelAndView model = new ModelAndView("EmpAjaxSearch");
		return model;

	}
	
	@RequestMapping(value = "/searchEmployee.htm", method = RequestMethod.GET)
	public void getTags(
			@RequestParam String searchString, HttpServletRequest request, HttpServletResponse response) throws ResultStatus, Exception{

		try {
			List<EmployeeBean> employees = simulateSearchResult(searchString);
			
			JSONArray json =  (JSONArray) JSONSerializer.toJSON(employees);
		    response.setContentType("text/json");
			response.getWriter().print(json.toString());
			
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e)
					.getResultStatus().getErrorCode());
		}
	}

	private List<EmployeeBean> simulateSearchResult(String searchString)
			throws Exception {

		List<EmployeeBean> employees = createEmployeeBusiness
				.searchEmployees(searchString);

		return employees;
	}

	@RequestMapping(value = "/image.htm", method = { RequestMethod.POST,
			RequestMethod.GET }, headers = "Accept=*/*")
	public ModelAndView uploadImageFile(
			@ModelAttribute(CPSConstants.EMPLOYEE) EmployeeBean employee,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = "";
		HttpSession session = null;
		String message = null;
		try {
			session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.PHOTOUPLOAD,
					employee.getParam())) {
				log.debug("CreateEmployeeController --> onSubmit Start --> param = home");
				viewName = CPSConstants.EMPLOYEEPHOTOUPLOAD;
			} else if (CPSUtils.compareStrings(CPSConstants.UPLOADIMAGEFILE,
					employee.getParam())) {
				Map<String, MultipartFile> map = new HashMap<String, MultipartFile>();
				if (!CPSUtils.isNullOrEmpty(employee.getPassportImageFile()))
					map.put("passportSizeImage", employee.getPassportImageFile());
				if (!map.isEmpty()) {
					FileUploadBean fub = new FileUploadBean();
					fub.setFiles(map);
					// fub.setFileIDList(fileIDMap);
					FileUploadBean fub1 = fileUpload.uploadFileToDatabase(fub);
				}
			}

			mav = new ModelAndView(viewName, CPSConstants.PHOTOBEAN, employee);
			if (!CPSUtils.isNullOrEmpty(message))
				mav.addObject(CPSConstants.MESSAGE, message);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e)
					.getResultStatus().getErrorCode());
		}
		return mav;
	}
}