package com.callippus.web.leave.controller.request;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.business.requestprocess.LeaveRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.common.JasperReportCreator;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.business.request.LeaveRequestBusiness;

@Controller
@RequestMapping("/leaveRequest.htm")
@SessionAttributes
public class LeaveRequestController {
	private static Log log = LogFactory.getLog(LeaveRequestController.class);
	@Autowired
	private LeaveRequestBusiness leaveRequestBusiness;
	@Autowired
	private FileUpload fileUpload;
	@Autowired
	private LeaveRequestProcess leaveRequestProcess;
	@Autowired
	private JasperReportCreator jasperReportCreator;
		
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LEAVEREQUEST) LeaveApplicationBean leaveBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);

			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(leaveBean.getSfID()) || CPSUtils.compareStrings(CPSConstants.SELF, leaveBean.getSfID())) {
				leaveBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			}
			if (CPSUtils.isNullOrEmpty(leaveBean.getParam())) {
				leaveBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(leaveBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}

			/**
			 * Leave Application code starts
			 */
			if (CPSUtils.compareStrings(CPSConstants.HOME, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> execute --> param = home");
				if (!CPSUtils.isNullOrEmpty(leaveBean.getOfflineSFID())) {
					leaveBean.setSfID(leaveBean.getOfflineSFID());
				}
				leaveBean.setMessage(leaveBean.getType());
				leaveBean.setFromDate("");
				leaveBean.setToDate("");
                leaveBean.setGazettedType("");
                
				leaveBean = leaveRequestBusiness.getLeaveApplicationDetails(leaveBean);
				
				if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.INVALID)) {
					session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveBean.getAppliedLeavesList());
					session.setAttribute(CPSConstants.AVAILABLELEAVESLIST, leaveBean.getLeaveDetailsList());
					session.setAttribute(CPSConstants.FAMILYMEMBERSLIST, leaveBean.getFamilyMembersList());

					if (CPSUtils.checkList(leaveBean.getLeaveDetailsList())) {
						session.setAttribute(CPSConstants.AVAILABLELEAVESDETAILSJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveDetailsList()));
					}
					if (CPSUtils.checkList(leaveBean.getFamilyMembersList())) {
						session.setAttribute(CPSConstants.FAMILYMEMBERSLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getFamilyMembersList()));
					}
					if (CPSUtils.checkList(leaveBean.getLeaveFamilyImpactDetails())) {
						session.setAttribute(CPSConstants.FAMILYIMPACTLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveFamilyImpactDetails()));
					}
					if (CPSUtils.checkList(leaveBean.getExceptionDetailsList())) {
						session.setAttribute(CPSConstants.EXCEPTIONALDETAILSLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getExceptionDetailsList()));
					}
					if (CPSUtils.checkList(leaveBean.getLeaveCreditsList())) {
						session.setAttribute(CPSConstants.AVAILABLELEAVECREDITSJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveCreditsList()));
					}
					if (CPSUtils.checkList(leaveBean.getSpecialCasualList())) {
						session.setAttribute(CPSConstants.SPECIALLEAVESLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getSpecialCasualList()));
					}
					leaveBean.setCurrentDate(CPSUtils.getCurrentDate());
				} else {
					leaveBean.setResult(CPSConstants.SFIDNOTEXISTS);
				}
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LEAVESEARCHLIST);
				viewName = CPSConstants.LEAVEAPPLICATIONPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.HOLIDAYS, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> execute --> param = holidays");
				leaveBean = leaveRequestBusiness.getHolidays(leaveBean);
				leaveBean.setMessage(CPSConstants.HOLIDAYS);
				if (!CPSUtils.isNullOrEmpty(leaveBean.getUserIntimation())) {
					request.setAttribute("intimation", leaveBean.getUserIntimation());
				} else {
					request.removeAttribute("intimation");
				}
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.ADDRESS, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> execute --> param=address");
				session.setAttribute(CPSConstants.ADDRESSLIST, (JSONArray) JSONSerializer.toJSON(leaveRequestBusiness.getAddressDetails(leaveBean.getSfID())));
				leaveBean.setMessage(CPSConstants.ADDRESS);
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.NOOFDAYS, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = noofdays");
				leaveBean = leaveRequestBusiness.getNoOfDays(leaveBean);
				request.setAttribute("currentNextToken", leaveBean.getCurrentFutureYearDays());
				request.setAttribute("futureAvailableLeaves", leaveBean.getFutureAvailableLeaves());
				leaveBean.setMessage(CPSConstants.NOOFDAYS);
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.UPLOAD, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=upload");
				// upload file
				File reportFile = new File(session.getServletContext().getResource("/repository/").getPath());
				if (leaveBean.getMcFile() != null) {
					String fileName = fileUpload.uploadFile(leaveBean.getMcFile(), "MedicalCertificate", leaveBean.getMcFile().getOriginalFilename().split("\\.")[1], reportFile.getAbsolutePath()
							+ "/LeaveAttachments/", CPSConstants.CHANGE);
					session.setAttribute(CPSConstants.MCPATH, fileName);

				} else if (leaveBean.getFitnessFile() != null) {
					String fileName = fileUpload.uploadFile(leaveBean.getFitnessFile(), "Fitness", leaveBean.getFitnessFile().getOriginalFilename().split("\\.")[1], reportFile.getAbsolutePath()
							+ "/LeaveAttachments/", CPSConstants.CHANGE);
					session.setAttribute(CPSConstants.FITNESSPATH, fileName);

				} else if (leaveBean.getOtherCertFile() != null) {
					String fileName = fileUpload.uploadFile(leaveBean.getOtherCertFile(), "OtherCert", leaveBean.getOtherCertFile().getOriginalFilename().split("\\.")[1], reportFile.getAbsolutePath()
							+ "/LeaveAttachments/", CPSConstants.CHANGE);
					session.setAttribute(CPSConstants.OTHERCERTPATH, fileName);

				}
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = submitRequest");

				session.removeAttribute("selectedLeaveDetails");
				session.removeAttribute("continuousHolidays");
				session.removeAttribute("prevHolidays");
				session.removeAttribute("nextHolidays");
				// Leave bounds(upper and lower bound)
				if ((CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()), CPSUtils.convertStringToDate(leaveBean.getToDate())) == -1) && (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()), CPSUtils.convertStringToDate(leaveBean.getFromDate())) == -1)) { // future status
					String upperBound = leaveRequestBusiness.getLeaveFutureBound();
					if (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(leaveBean.getFromDate()), CPSUtils.convertStringToDate(CPSUtils.addNoOfMonths(CPSUtils.getCurrentDate(), upperBound))) < 1) {
						leaveBean = leaveRequestBusiness.checkLeaveConstraints(leaveBean);
					} else {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.LEAVEFUTUREBOUNDCONSTRAINTVIOLATION.replace("%boundValue%", upperBound));
					}
				} else if (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()), CPSUtils.convertStringToDate(leaveBean.getToDate())) == 1) { // past status
					if (!CPSUtils.compareStrings(CPSConstants.CL, leaveBean.getLeaveType())) {
						String lowerBound = leaveRequestBusiness.getLeavePastBound();
						if (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(leaveBean.getToDate()), CPSUtils.convertStringToDate(CPSUtils.addNoOfMonths(CPSUtils.getCurrentDate(), String.valueOf(-Integer.parseInt(lowerBound))))) >= 0) {
							leaveBean = leaveRequestBusiness.checkLeaveConstraints(leaveBean);
						} else {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.LEAVEPASTBOUNDCONSTRAINTVIOLATION.replace("%boundValue%", lowerBound));
						}
					} else {
						leaveBean = leaveRequestBusiness.checkLeaveConstraints(leaveBean);
					}
				} else if (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()), CPSUtils.convertStringToDate(leaveBean.getToDate())) == 0) { // current day
					leaveBean = leaveRequestBusiness.checkLeaveConstraints(leaveBean);
				} else { // No leave span status constraints
					leaveBean = leaveRequestBusiness.checkLeaveConstraints(leaveBean);
				}
				// Check constraints
				//leaveBean = leaveRequestBusiness.checkLeaveConstraints(leaveBean);
				
				if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.FAILED) && CPSUtils.isNullOrEmpty(leaveBean.getRemarks())) {
					// All constraints are fulfilled, so we can send a request to workflow
					leaveBean.setRemarksNote(null);
					leaveBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
					leaveBean.setLeaveRequestType(CPSConstants.LEAVE);
					
					Map<String, MultipartFile> map = new HashMap<String, MultipartFile>();
					if (!CPSUtils.isNullOrEmpty(leaveBean.getMedicalCertName())) {
						map.put("mcFile", leaveBean.getMcFile());
					}
					if (!CPSUtils.isNullOrEmpty(leaveBean.getFitnessCertName())) {
						map.put("fitnessFile", leaveBean.getFitnessFile());
					}
					if (!CPSUtils.isNullOrEmpty(leaveBean.getOtherCertName())) {
						map.put("otherCertFile", leaveBean.getOtherCertFile());//Narayana
					}
					if (map.size() > 0) {
						FileUploadBean fub = new FileUploadBean();
						fub.setFiles(map);
						FileUploadBean fub1 = fileUpload.uploadFileToDatabase(fub);
						if(!CPSUtils.isNullOrEmpty(fub1.getFileIds().get("mcFile"))) {
							leaveBean.setMedicalCertName(String.valueOf(fub1.getFileIds().get("mcFile")));	
						}
					 	if(!CPSUtils.isNullOrEmpty(fub1.getFileIds().get("fitnessFile"))) {
							leaveBean.setFitnessCertName(String.valueOf(fub1.getFileIds().get("fitnessFile")));
					 	}
					 	if(!CPSUtils.isNullOrEmpty(fub1.getFileIds().get("otherCertFile"))) {
					 		leaveBean.setOtherCertName(String.valueOf(fub1.getFileIds().get("otherCertFile")));//Narayana	
					 	}
					}
					if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.MCPATH))) {
						leaveBean.setMedicalCertName(session.getAttribute(CPSConstants.MCPATH).toString());
					}
					if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.FITNESSPATH))) {
						leaveBean.setFitnessCertName(session.getAttribute(CPSConstants.FITNESSPATH).toString());
					}
					if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.OTHERCERTPATH))) {
						leaveBean.setOtherCertName(session.getAttribute(CPSConstants.OTHERCERTPATH).toString());
					}
					
					leaveBean = leaveRequestBusiness.saveOrUpdateSpellsCount(leaveBean);
					
					leaveBean.setResult(sendToWorkflow(leaveBean, request));
					session.setAttribute("RequestIdWork", leaveBean.getRequestID());
					//if user want  go to workflow diagram, we are giving alert based on  this requestID 
				} else {
					// Some constraints are failed, we give an alert to the user
					session.setAttribute("selectedLeaveDetails", leaveBean.getSelectedLeaveDetails());
					session.setAttribute("continuousHolidays", leaveBean.getHolidays());
					session.setAttribute("prevHolidays", leaveBean.getPrevHolidays());
					session.setAttribute("nextHolidays", leaveBean.getNextHolidays());
					request.setAttribute("previousDays", leaveBean.getPreviousDays());
					request.setAttribute("nextDays", leaveBean.getNextDays());
				}
				leaveBean.setMessage(CPSConstants.REQUEST);
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.PROCEED, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = proceed");
				if (CPSUtils.isNull(session.getAttribute("prevHolidays"))) {
					leaveBean.setPrevHolidays("0");
				} else {
					leaveBean.setPrevHolidays(session.getAttribute("prevHolidays").toString());
				}
				if (CPSUtils.isNull(session.getAttribute("nextHolidays"))) {
					leaveBean.setNextHolidays("0");
				} else {
					leaveBean.setNextHolidays(session.getAttribute("nextHolidays").toString());
				}
				leaveBean.setSelectedLeaveDetails((LeaveManagementBean) session.getAttribute("selectedLeaveDetails"));

				// check the total number of days
				leaveBean = leaveRequestProcess.checkNoOfLeaves(leaveBean);
				if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.FAILED)) {
					leaveBean.setRemarks(null);
					leaveBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
					leaveBean.setLeaveRequestType(CPSConstants.LEAVE);

					if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.MCPATH))) {
						leaveBean.setMedicalCertName(session.getAttribute(CPSConstants.MCPATH).toString());
					}
					if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.FITNESSPATH))) {
						leaveBean.setFitnessCertName(session.getAttribute(CPSConstants.FITNESSPATH).toString());
					}
					if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.OTHERCERTPATH))) {
						leaveBean.setOtherCertName(session.getAttribute(CPSConstants.OTHERCERTPATH).toString());
					}
					leaveBean = leaveRequestBusiness.saveOrUpdateSpellsCount(leaveBean);
					leaveBean.setResult(sendToWorkflow(leaveBean, request));
					session.setAttribute("RequestIdWork", leaveBean.getRequestID());
				}

				leaveBean.setMessage(CPSConstants.REQUEST);
				viewName = CPSConstants.LEAVERESULT;
			}
			/**
			 * Leave Application code ends
			 */

			/**
			 * Leave Form PDF
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVEREPORT, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=leaveReport");
				if (CPSUtils.isNull(request.getParameter("file"))) {
					request.setAttribute("fileName", request.getParameter("requestID") + ".pdf");
				} else {
					request.setAttribute("fileName", request.getParameter("file"));
				}
				viewName = CPSConstants.VIEWLEAVE;
			}
			else if (CPSUtils.compareStrings("getPreccriptionFile", leaveBean.getParam())) {
				log.debug("dowlloaded file from local server");
				String fileexten = leaveBean.getFileName();
				String ext1 = FilenameUtils.getExtension(fileexten);
				String file1 = "PrescriptionCertificate_"+leaveBean.getFileno()+"."+ext1;
				//String fileSaveLocation="/home/srinivas/leaveFiles/";
				
				String fileSaveLocation=request.getServletContext().getRealPath("/leaveFiles/");
				fileSaveLocation=fileSaveLocation+"/";
				
				File file2 =new File(fileSaveLocation+file1);
				String ext = FilenameUtils.getExtension(fileSaveLocation+file1);
				boolean fileExists = file2.exists();
				final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
				fileTypeMap.getContentType(file2);
				response.setContentType(fileTypeMap.getContentType(file2));
				response.setContentLength((int) (long)file2.length());
				response.setHeader("Content-Disposition", "inline; filename=\"" + fileexten + "\"");
				 Path path = Paths.get(fileSaveLocation+file1);
			      byte[] data = Files.readAllBytes(path);
				FileCopyUtils.copy(data, response.getOutputStream());
			}
			else if (CPSUtils.compareStrings("getDbFile", leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=getDbFile");
				FilesBean file = leaveRequestBusiness.getDBFile(Integer.parseInt(leaveBean.getFileId()));
				response.setContentType(file.getType());
				System.out.println("get type  :: "+file.getType());
				response.setContentLength(file.getFile().length);
				System.out.println("get type  :: "+file.getFile().length);
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"");
				FileCopyUtils.copy(file.getFile(), response.getOutputStream());
				viewName = CPSConstants.VIEWLEAVE;
			}
			else if (CPSUtils.compareStrings("getPreccriptionFile1221", leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=getDbFile");
				FilesBean file =null;
				/*try{
			 file = leaveRequestBusiness.getDBFile(Integer.parseInt(leaveBean.getFileId()));
				}catch(Exception e){
					e.printStackTrace();
					
				}*/
				String file1 = "/home/srinivas/leaveFiles/PrescriptionCertificate_43619.doc";
				//response.setContentType(file.getType());
				
				//response.setContentLength(file.getFile().length);
				//response.setHeader("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"");
				response.setHeader("Content-Disposition", "inline; filename=\"" + file1 + "\"");
				//FileCopyUtils.copy(file.getFile(), response.getOutputStream());
				viewName = CPSConstants.VIEWLEAVE;
			}
			/**
			 * Leave Form PDF end
			 */

			/**
			 * Leave cancellation code starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.GETLEAVEDETAILS, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = getLeaveDetails");
				leaveBean = leaveRequestBusiness.getLeaveDetails(leaveBean);
				//session.setAttribute("RequestIdLeaveCancel", leaveBean.getRequestId());
				if (CPSUtils.compareStrings(leaveBean.getType(), CPSConstants.CONVERT)) {
					leaveBean = leaveRequestBusiness.getConfiguredConvertLeaves(leaveBean);
					request.setAttribute("appliedLeave", leaveBean.getLeaveTypeID());
					request.setAttribute("appliedLeaveFromDate", CPSUtils.formattedDate(leaveBean.getFromDate()));
					request.setAttribute("appliedLeaveToDate", CPSUtils.formattedDate(leaveBean.getToDate()));
					request.setAttribute("appliedLeaveNoDays", leaveBean.getNoOfDays());
				}
				viewName = CPSConstants.LEAVEREQUESTDETAILS;
			} else if (CPSUtils.compareStrings(CPSConstants.CANCELLEAVE, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = cancelLeave");
				// check whether this Leave is attached to any LTC Request or Not 
				if (!(CPSUtils.compareStrings(CPSConstants.LTC_ADVANCE_AMENDMENT, leaveBean.getLtcRequestType()) 
						|| CPSUtils.compareStrings(CPSConstants.LTC_APPROVAL_AMENDMENT, leaveBean.getLtcRequestType()) 
						|| CPSUtils.compareStrings(CPSConstants.LTC_LEAVE_CANCEL, leaveBean.getLtcRequestType()))) {
					leaveBean.setRemarks(leaveRequestProcess.checkLeaveAttachedToLtc(leaveBean.getRequestID()));
				}
				if (CPSUtils.isNull(leaveBean.getRemarks())) {
					// check whether the user already applied any cancel request for this leave type
					leaveBean = leaveRequestBusiness.checkRequests(leaveBean);
					if (CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)) {
						leaveBean = leaveRequestBusiness.checkCancelLeaveConstraints(leaveBean);

						if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.FAILED) && CPSUtils.isNullOrEmpty(leaveBean.getRemarks())) {
							leaveBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
							leaveBean.setLeaveRequestType(CPSConstants.CANCEL);
							LeaveApplicationBean lab = new LeaveApplicationBean();
							BeanUtils.copyProperties(leaveBean, lab);
							lab = leaveRequestBusiness.getLeaveDetails(lab);
							leaveBean.setReferenceID(lab.getId());
							leaveBean.setFromDate(lab.getFromDate());
							leaveBean.setFromDate(lab.getToDate());
							leaveBean.setResult(sendToWorkflow(leaveBean, request));
							}
						} else {
							leaveBean.setRemarks(CPSConstants.ALREADYAPPLIED);
						}
					session.setAttribute("RequestIdLeaveCancel", leaveBean.getRequestID());
					leaveBean.setMessage(CPSConstants.CANCEL);
					viewName = CPSConstants.LEAVERESULT;
				} else {
					leaveBean.setMessage(CPSConstants.CANCEL);
					leaveBean.setResult(CPSConstants.FAILED);
					viewName = CPSConstants.LEAVERESULT;
				}
			}

			/**
			 * Leave cancellation code ends
			 */

			/**
			 * Leave balance code starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVEBALANCE, leaveBean.getParam())) {
				log.debug("LeaveManagementController --> onSubmit --> param = leaveAvailableHome");
				leaveBean = leaveRequestBusiness.getLeaveBalaceDetails(leaveBean);
				viewName = CPSConstants.AVAILABLELEAVESPAGE;
			}
			/**
			 * Leave balance code end
			 */

			/**
			 * Leave conversion code starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.CONVERTLEAVE, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = convertLeave");
				// check whether this Leave is attached to any LTC Request or Not 
				leaveBean.setRemarks(leaveRequestProcess.checkLeaveAttachedToLtc(leaveBean.getRequestID()));
				if (CPSUtils.isNull(leaveBean.getRemarks())) {
					session.removeAttribute("prevHolidays");
					session.removeAttribute("nextHolidays");
					if (CPSUtils.compareStrings(leaveBean.getType(), CPSConstants.PROCEED)) {
						leaveBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
						leaveBean.setLeaveRequestType(CPSConstants.CONVERT);
						leaveBean.setReferenceID(Integer.valueOf(leaveRequestBusiness.getLeaveRequestPkValue(leaveBean.getRequestID())));
						leaveBean.setResult(sendToWorkflow(leaveBean, request));
					} else {
						// check whether any cancel request or conversion request was already applied for this leaves are not
						leaveBean = leaveRequestBusiness.checkRequests(leaveBean);

						if (CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)) {
							leaveBean = leaveRequestBusiness.checkConvertLeaveConstraints(leaveBean);
							if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.FAILED) && CPSUtils.isNullOrEmpty(leaveBean.getRemarks())) {
								// All constraints are fulfilled, so we can send a request to workflow
								leaveBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
								leaveBean.setLeaveRequestType(CPSConstants.CONVERT);
								leaveBean.setReferenceID(Integer.valueOf(leaveRequestBusiness.getLeaveRequestPkValue(leaveBean.getRequestID())));
								leaveBean.setResult(sendToWorkflow(leaveBean, request));
								session.setAttribute("RequestIdLeaveConvert", leaveBean.getRequestID());						
							} else {
								session.setAttribute("prevHolidays", leaveBean.getPrevHolidays());
								session.setAttribute("nextHolidays", leaveBean.getNextHolidays());
							}
						} else {
							leaveBean.setRemarks(CPSConstants.ALREADYAPPLIED);
						}
					}
					leaveBean.setMessage(CPSConstants.CONVERT);
					viewName = CPSConstants.LEAVERESULT;
				} else {
					leaveBean.setMessage(CPSConstants.CONVERT);
					leaveBean.setResult(CPSConstants.FAILED);
					viewName = CPSConstants.LEAVERESULT;
				}
			}

			/**
			 * Leave conversion code ends
			 */

			/**
			 * Leave Card code starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVECARD, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=leavecard");
				if (!CPSUtils.isNullOrEmpty(leaveBean.getYear())) {
					leaveBean.setCurrentYear(leaveBean.getYear());
				} else {
					leaveBean.setCurrentYear(CPSUtils.currentYear());
				}
				leaveBean = leaveRequestBusiness.getLeaveCardDetails(leaveBean);
				leaveBean = leaveRequestBusiness.getEmployeeDetails(leaveBean);
				viewName = CPSConstants.LEAVECARDPAGE;
			}
			/**
			 * Leave Card code end
			 */

			/**
			 * Leave Account code starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVEACCOUNT, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=leaveAccount");

				if (!CPSUtils.isNullOrEmpty(leaveBean.getSearchSfid())) {
					leaveBean.setSfID(leaveBean.getSearchSfid());
				}
				if(CPSUtils.isNullOrEmpty(leaveBean.getYear())){
					leaveBean.setYear(CPSUtils.currentYear());
				}
				if (CPSUtils.compareStrings(leaveBean.getType(), CPSConstants.CARD)) {
					if (!CPSUtils.isNullOrEmpty(leaveBean.getYear())) {
						leaveBean.setCurrentYear(leaveBean.getYear());
					} else {
						leaveBean.setCurrentYear(CPSUtils.currentYear());
					}

					leaveBean = leaveRequestBusiness.getLeaveCardDetails(leaveBean);
					leaveBean = leaveRequestBusiness.getEmployeeDetails(leaveBean);
					leaveBean.setMenuFlag(CPSConstants.DISABLE);
					viewName = CPSConstants.LEAVECARDPAGE;
				} else {
					// create pdf report
					HashMap<String, String> pmap = new HashMap<String, String>();
					pmap.put("sfID", leaveBean.getSfID());
					pmap.put("yearID", leaveBean.getYear());
					jasperReportCreator.createPdf(session, "LeaveAccountReport.jasper", pmap, "LeaveAccount", leaveBean.getSfID(), false);
					request.setAttribute("fileName", leaveBean.getSfID() + ".pdf");
					viewName = CPSConstants.VIEWLEAVE;
				}

			} else if (CPSUtils.compareStrings(CPSConstants.CHECKEMP, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = checkEmp");
				leaveBean.setMessage(leaveRequestBusiness.checkEmployee(leaveBean.getSearchSfid()));
				if (CPSUtils.compareStrings(leaveBean.getMessage(), CPSConstants.SUCCESS)) {
					leaveBean.setMessage(CPSConstants.EMPEXITS);
				} else if (CPSUtils.compareStrings(leaveBean.getMessage(), CPSConstants.INVALID)) {
					leaveBean.setMessage(CPSConstants.EMPNOTEXISTS);
				}
				viewName = CPSConstants.RESULT;
			}
			/**
			 * Leave Account code end
			 */

			/**
			 * Leave Txn View (Account and Card) starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVETXNVIEW, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=leaveTxnView");
				leaveBean = leaveRequestBusiness.getLeaveTxnHomeDetails(leaveBean);
				viewName = CPSConstants.LEAVETXNVIEWPAGE;
			}
			/**
			 * Leave Txn View (Account and Card) ends
			 */
			
			/***
			 * Uploaded Files Update Code Start  
			 ***/
			if(CPSUtils.compareStrings("uploadedFilesUpdate", leaveBean.getParam())){
				Map<String, MultipartFile> map =new HashMap<String, MultipartFile>();
				Map<String, Integer> fileIDMap= new HashMap<String, Integer>();
				if(!CPSUtils.isNullOrEmpty(leaveBean.getMedicalCertName())){
					map.put("mcFile", leaveBean.getMcFile());
					fileIDMap.put("mcFile",Integer.parseInt(leaveBean.getMedicalCertName()));
					}else{
					if(!CPSUtils.isNullOrEmpty(leaveBean.getMcFile()))
					map.put("mcFile", leaveBean.getMcFile());
				}
				if(!CPSUtils.isNullOrEmpty(leaveBean.getFitnessCertName())){
					map.put("fitnessFile", leaveBean.getFitnessFile());
					fileIDMap.put("fitnessFile",Integer.parseInt(leaveBean.getFitnessCertName()));
					}else{
						if(!CPSUtils.isNullOrEmpty(leaveBean.getFitnessFile()))
							map.put("fitnessFile", leaveBean.getFitnessFile());
						}
				if(!CPSUtils.isNullOrEmpty(leaveBean.getOtherCertName())){
					map.put("otherCertFile", leaveBean.getOtherCertFile());
					fileIDMap.put("otherCertFile",Integer.parseInt(leaveBean.getOtherCertName()));
					}else{
						if(!CPSUtils.isNullOrEmpty(leaveBean.getOtherCertFile())){
							map.put("otherCertFile", leaveBean.getOtherCertFile());
						}
						}
				if(map.size()>0){
				FileUploadBean fub=new FileUploadBean();
				fub.setFiles(map);
				fub.setFileIDList(fileIDMap);
				FileUploadBean fub1=fileUpload.uploadFileToDatabase(fub);
				
				if(!CPSUtils.isNullOrEmpty(fub1.getFileIds().get("mcFile"))){
					leaveBean.setMedicalCertName(String.valueOf(fub1.getFileIds().get("mcFile")));	
				}
				if(!CPSUtils.isNullOrEmpty(fub1.getFileIds().get("fitnessFile"))){
					leaveBean.setFitnessCertName(String.valueOf(fub1.getFileIds().get("fitnessFile")));
				}
				if(!CPSUtils.isNullOrEmpty(fub1.getFileIds().get("otherCertFile"))){
					leaveBean.setOtherCertName(String.valueOf(fub1.getFileIds().get("otherCertFile")));//Narayana	
				}
				leaveRequestProcess.updateFileStatus(leaveBean);
			  }
				leaveBean.setMessage(CPSConstants.SUCCESS);
				viewName = CPSConstants.RESULT;
			}
			/** end of Uploaded Files Update **/
			// Leave amendment
			if (CPSUtils.compareStrings(CPSConstants.LEAVEAMENDMENT, leaveBean.getParam())) {
				leaveBean = leaveRequestBusiness.getLeaveDetails(leaveBean);
				leaveBean = leaveRequestBusiness.getDefaultDetails(leaveBean);
				if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.INVALID)) {
					session.setAttribute(CPSConstants.AVAILABLELEAVESLIST, leaveBean.getLeaveDetailsList());
					session.setAttribute(CPSConstants.FAMILYMEMBERSLIST, leaveBean.getFamilyMembersList());
	
					if (CPSUtils.checkList(leaveBean.getLeaveDetailsList())) {
						session.setAttribute(CPSConstants.AVAILABLELEAVESDETAILSJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveDetailsList()));
					}
					if (CPSUtils.checkList(leaveBean.getFamilyMembersList())) {
						session.setAttribute(CPSConstants.FAMILYMEMBERSLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getFamilyMembersList()));
					}
					if (CPSUtils.checkList(leaveBean.getLeaveFamilyImpactDetails())) {
						session.setAttribute(CPSConstants.FAMILYIMPACTLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveFamilyImpactDetails()));
					}
					if (CPSUtils.checkList(leaveBean.getExceptionDetailsList())) {
						session.setAttribute(CPSConstants.EXCEPTIONALDETAILSLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getExceptionDetailsList()));
					}
					if (CPSUtils.checkList(leaveBean.getLeaveCreditsList())) {
						session.setAttribute(CPSConstants.AVAILABLELEAVECREDITSJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getLeaveCreditsList()));
					}
					if (CPSUtils.checkList(leaveBean.getSpecialCasualList())) {
						session.setAttribute(CPSConstants.SPECIALLEAVESLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveBean.getSpecialCasualList()));
					}
					leaveBean.setCurrentDate(CPSUtils.getCurrentDate());

					request.setAttribute("fromDate", (!CPSUtils.isNullOrEmpty(leaveBean.getOtherRemarks()) && leaveBean.getOtherRemarks().contains(CPSConstants.PROCEED)) ? leaveBean.getFromDate() : CPSUtils.formattedDate(leaveBean.getFromDate()));
					request.setAttribute("toDate", (!CPSUtils.isNullOrEmpty(leaveBean.getOtherRemarks()) && leaveBean.getOtherRemarks().contains(CPSConstants.PROCEED)) ? leaveBean.getToDate() : CPSUtils.formattedDate(leaveBean.getToDate()));
					request.setAttribute("previousHolidays", leaveBean.getPrevHolidays());
					request.setAttribute("fromHalfDayFlag", leaveBean.getFromHalfDayFlag());			
					request.setAttribute("nextHolidays", leaveBean.getNextHolidays());
					request.setAttribute("toHalfDayFlag", leaveBean.getToHalfDayFlag());
					request.setAttribute("leaveTypeId", leaveBean.getLeaveTypeID());
					request.setAttribute("noOfDays", (!CPSUtils.isNullOrEmpty(leaveBean.getOtherRemarks()) && leaveBean.getOtherRemarks().contains(CPSConstants.PROCEED)) ? leaveBean.getDebitLeaves() : leaveBean.getNoOfDays());
					if (!CPSUtils.isNullOrEmpty(leaveBean.getAdditionalData())) {
						request.setAttribute("additionalData", leaveBean.getAdditionalData());
					}
				} else {
					leaveBean.setResult(CPSConstants.SFIDNOTEXISTS);
				}
				viewName = CPSConstants.LEAVEAMENDMENTVIEW;
			} else if (CPSUtils.compareStrings(CPSConstants.CHECKCANCELCONVERTSTATUS, leaveBean.getParam())) {
				leaveBean = leaveRequestBusiness.checkRequests(leaveBean);
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, leaveBean.getResult())) {
					leaveBean.setMessage(CPSConstants.SUCCESS);
				}
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.CANCELAPPLIEDLEAVE, leaveBean.getParam())) {
				leaveBean.setMessage(leaveRequestBusiness.cancelAppliedLeave(leaveBean));
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.ROLLBACKAPPLIEDLEAVECANCELLATION, leaveBean.getParam())) {
				leaveBean.setMessage(leaveRequestBusiness.rollBackAppliedLeaveCancellation(leaveBean));
				viewName = CPSConstants.LEAVERESULT;
			}
			mav = new ModelAndView(viewName, CPSConstants.LEAVEREQUEST, leaveBean);
			if (!CPSUtils.isNullOrEmpty(leaveBean.getResult())) {
				mav.addObject(CPSConstants.RESULTMSG, leaveBean.getResult());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, leaveBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getRemarks())) {
				mav.addObject(CPSConstants.REMARKS, leaveBean.getRemarks());
			} else {
				mav.addObject(CPSConstants.REMARKS, CPSConstants.SUCCESS);
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

	public String sendToWorkflow(LeaveApplicationBean leaveBean, HttpServletRequest request) throws Exception {
		try {
			LeaveRequestBean lrb = new LeaveRequestBean();
			BeanUtils.copyProperties(leaveBean, lrb);
			if (CPSUtils.compareStrings(leaveBean.getLeaveRequestType(), CPSConstants.LEAVE)) {
				lrb.setEmployeeDetails(leaveRequestBusiness.getOfficeIDAndPhone(leaveBean.getSfID()));
				lrb.setDepartmentID(lrb.getEmployeeDetails().get("DEPARTMENT"));
				lrb.setInternalNo(lrb.getEmployeeDetails().get("INTERNAL_PHONE_NO"));
				lrb.setDesignationID(lrb.getEmployeeDetails().get("DESIGNATION"));
			}

			lrb.setIpAddress(request.getRemoteAddr());
			lrb.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			lrb.setSession(request.getSession(true));

			if (!CPSUtils.isNullOrEmpty(leaveBean.getChildName())) {
				lrb.setAdditionalData(leaveBean.getChildName());
			} else if (!CPSUtils.isNullOrEmpty(leaveBean.getStudyDegree())) {
				lrb.setAdditionalData(leaveBean.getStudyDegree());
			} else if (!CPSUtils.isNullOrEmpty(leaveBean.getDeliveryDate())) {
				lrb.setAdditionalData(leaveBean.getDeliveryDate());
			}

			leaveBean.setResult(leaveRequestProcess.initWorkflow(lrb));
			
			leaveBean.setRequestID(lrb.getRequestID());
			
			//session.setAttribute("RequestIdLeaveConvert", leaveBean.getRequestID());
          
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}
}
