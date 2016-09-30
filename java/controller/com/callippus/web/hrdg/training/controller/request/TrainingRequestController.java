package com.callippus.web.hrdg.training.controller.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.business.hrdg.training.request.TrainingRequestBusiness;
import com.callippus.web.business.hrdg.training.request.TrainingRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingRequestBean;

@Controller
@RequestMapping("/" + CPSConstants.TRAININGREQUEST)
@SessionAttributes
public class TrainingRequestController {

	private static Log log = LogFactory.getLog(TrainingRequestController.class);
	@Autowired
	private TrainingRequestBusiness trainingRequestBusiness;
	@Autowired
	private TrainingRequestProcess trainingRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.TRAININGREQUEST) TrainingRequestBean trainingReqBean, BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView mav = null;
		HttpSession session = null;
		String message = "";
		String view = "";
		try {
			ErpAction.sessionChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(trainingReqBean.getParam())) {
				trainingReqBean.setParam(CPSConstants.TRAININGREQUESTHOME);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, trainingReqBean.getParam())) {
				trainingReqBean.setType((String) session.getAttribute(CPSConstants.TYPE));
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.TRAININGREQUEST, trainingReqBean);
				return mav;
			}
			
			if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONMASTER, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=TrainingNominationMaster");
				view = CPSConstants.TRAININGNOMINATIONMASTER;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setNomineeSfid(trainingReqBean.getSfid());
				List courseList = trainingRequestBusiness.getCourseList(trainingReqBean);
				session.setAttribute(CPSConstants.COURSELIST, courseList);
				trainingReqBean = trainingRequestBusiness.getEmpDetails(trainingReqBean);
				if (CPSUtils.checkList(courseList)) {
					session.setAttribute(CPSConstants.JSONCOURSELIST, (JSONArray) JSONSerializer.toJSON(courseList));
				}

			} 
			else if (CPSUtils.compareStrings("getBrochureAndION", trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=getBrochureAndION");
				view = "TrainingNominationBrochure"; 
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				//trainingReqBean.setNomineeSfid(trainingReqBean.getSfid());
				trainingReqBean = trainingRequestBusiness.getBrochureAndION(trainingReqBean);
				trainingReqBean = trainingRequestBusiness.getNominationReqStatus(trainingReqBean);
				List list1 = trainingRequestBusiness.getNominationLastAttededCourse(trainingReqBean);
				if(!CPSUtils.isNullOrEmpty(session.getAttribute("jsonLastAttendedCourse")))
						session.removeAttribute("jsonLastAttendedCourse");
				if(CPSUtils.checkList(list1))
				session.setAttribute("jsonLastAttendedCourse", (JSONArray) JSONSerializer.toJSON(list1));
			} 
			else if (CPSUtils.compareStrings(CPSConstants.MANAGETRAININGNOMINATION, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=manageTrainingNomination");
				view = CPSConstants.TRAININGNOMINATIONMASTER;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setIpAddress(request.getRemoteAddr());
				trainingReqBean.setSession(request.getSession(true));
				if (CPSUtils.compareStrings(trainingRequestBusiness.checkTrainingNomination(trainingReqBean), CPSConstants.SUCCESS)) {
					message = trainingRequestProcess.initWorkflow(trainingReqBean);

				}
				List courseList = trainingRequestBusiness.getCourseList(trainingReqBean);
				session.setAttribute(CPSConstants.COURSELIST, courseList);
				if (CPSUtils.checkList(courseList)) {
					session.setAttribute(CPSConstants.JSONCOURSELIST, (JSONArray) JSONSerializer.toJSON(courseList));
				}

			} 
			else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONOFFLINEMASTER, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=TrainingNominationMaster");
				view = CPSConstants.TRAININGNOMINATIONOFFLINEMASTER;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean = trainingRequestBusiness.getEmpDetails(trainingReqBean);
				
			}
			else if (CPSUtils.compareStrings(CPSConstants.HRDGTRAININGNOMINATIONDETAILS, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=HrdgTrainingNominationDetails");
				view = CPSConstants.HRDGTRAININGNOMINATIONDETAILS;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());

				List trainingRequestCourseList = trainingRequestBusiness.getNominationsCourseList(trainingReqBean);
				session.setAttribute("trainingRequestCourseList", trainingRequestCourseList);
				trainingReqBean.setParam(CPSConstants.PARAM);
				List trainingRequestCourseList1 = trainingRequestBusiness.getNominationsCourseList(trainingReqBean);
				session.setAttribute("trainingRequestCourseList1", trainingRequestCourseList1);
				List boardList = trainingRequestBusiness.getMDBList();
				session.setAttribute("boardList", boardList);
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.TRAININGNOMINATIONREQUESTLIST)))
					session.removeAttribute(CPSConstants.TRAININGNOMINATIONREQUESTLIST);
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.TRAININGNOMINATIONBOARDSELECTIONLIST)))
					session.removeAttribute(CPSConstants.TRAININGNOMINATIONBOARDSELECTIONLIST);
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.TRAININGNOMINATIONADSELECTIONLIST)))
					session.removeAttribute(CPSConstants.TRAININGNOMINATIONADSELECTIONLIST);
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.TRAININGNOMINATIONFINALSELECTIONLIST)))
					session.removeAttribute(CPSConstants.TRAININGNOMINATIONFINALSELECTIONLIST);
				
				if(!CPSUtils.isNull(session.getAttribute("jsonSeriesMstList")))session.removeAttribute("jsonSeriesMstList");
				if(!CPSUtils.isNull(session.getAttribute("jsonSeriesMstList3")))session.removeAttribute("jsonSeriesMstList3");
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList2")))session.removeAttribute("jsonIonMstList2");
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList4")))session.removeAttribute("jsonIonMstList4");
				
				
				List seriesMstList = trainingRequestBusiness.getSeriesMstList();
				session.setAttribute("seriesMstList", seriesMstList);
				
				if (CPSUtils.checkList(seriesMstList)) {
					session.setAttribute("jsonSeriesMstList", (JSONArray) JSONSerializer.toJSON(seriesMstList));
				}

			}
			else if(CPSUtils.compareStrings("getLetterFormatMstList", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=getLetterFormatMstList"); 
				view ="TrainingNominationLetterFormat";
				if(!CPSUtils.isNull(session.getAttribute("jsonSeriesMstList3")))session.removeAttribute("jsonSeriesMstList3");
				trainingReqBean = trainingRequestBusiness.checkCourseFee(trainingReqBean);
				List seriesMstList = trainingRequestBusiness.getSeriesMstList();
				session.setAttribute("seriesMstList3", seriesMstList);
				
				if (CPSUtils.checkList(seriesMstList)) {
					session.setAttribute("jsonSeriesMstList3", (JSONArray) JSONSerializer.toJSON(seriesMstList));
				}

				
			}
			else if(CPSUtils.compareStrings("getIonMstList", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=getIonMstList"); 
				view ="TrainingNominationION";
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList2")) && CPSUtils.compareStrings(trainingReqBean.getIonFlag(), "2") )session.removeAttribute("jsonIonMstList2");
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList3")) && CPSUtils.compareStrings(trainingReqBean.getIonFlag(), "3") )session.removeAttribute("jsonIonMstList3");
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList4")) && CPSUtils.compareStrings(trainingReqBean.getIonFlag(), "4") )session.removeAttribute("jsonIonMstList4");
				List ionMstList = trainingRequestBusiness.getIONMstList(trainingReqBean);
				
				
					if(CPSUtils.compareStrings(trainingReqBean.getIonFlag(), "2"))
					{
						if (CPSUtils.checkList(ionMstList)) session.setAttribute("jsonIonMstList2", (JSONArray) JSONSerializer.toJSON(ionMstList));
					}

					if(CPSUtils.compareStrings(trainingReqBean.getIonFlag(), "3"))
					{
						if (CPSUtils.checkList(ionMstList)) session.setAttribute("jsonIonMstList3", (JSONArray) JSONSerializer.toJSON(ionMstList));
					}
					if(CPSUtils.compareStrings(trainingReqBean.getIonFlag(), "4"))
					{
						if (CPSUtils.checkList(ionMstList)) session.setAttribute("jsonIonMstList4", (JSONArray) JSONSerializer.toJSON(ionMstList));
					}
				
			}else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONREQUESTLIST, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=HrdgTrainingNominationDetailsList");
				view = CPSConstants.TRAININGNOMINATIONREQUESTLIST;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());

				List trainingRequestList = trainingRequestBusiness.getTrainingNominationReqList(trainingReqBean);
				session.setAttribute(CPSConstants.TRAININGNOMINATIONREQUESTLIST, trainingRequestList);

			} else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONBOARDSELECTIONLIST, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=TrainingNominationBoardSelectionList");
				view = CPSConstants.TRAININGNOMINATIONBOARDSELECTIONLIST;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());

				List trainingRequestList = trainingRequestBusiness.getTrainingNominationBoardSelectionList(trainingReqBean);
				session.setAttribute(CPSConstants.TRAININGNOMINATIONBOARDSELECTIONLIST, trainingRequestList);

			} else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONADSELECTIONLIST, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=TrainingNominationADSelectionList");
				view = CPSConstants.TRAININGNOMINATIONADSELECTIONLIST;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());

				List trainingRequestList = trainingRequestBusiness.getTrainingNominationADSelectionList(trainingReqBean);
				session.setAttribute(CPSConstants.TRAININGNOMINATIONADSELECTIONLIST, trainingRequestList);

			} else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONFINALSELECTIONLIST, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=TrainingNominationFinalSelectionList");
				view = CPSConstants.TRAININGNOMINATIONFINALSELECTIONLIST;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());

				List trainingRequestList = trainingRequestBusiness.getTrainingNominationFinalSelectionList(trainingReqBean);
				session.setAttribute(CPSConstants.TRAININGNOMINATIONFINALSELECTIONLIST, trainingRequestList);

			} else if (CPSUtils.compareStrings(CPSConstants.MANAGETRAININGNOMINATIONS, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=manageTrainingNominations");
				if (CPSUtils.compareStrings(trainingReqBean.getType(), "nomination"))
					view = CPSConstants.TRAININGNOMINATIONREQUESTLIST;
				else if (CPSUtils.compareStrings(trainingReqBean.getType(), "boardSelection"))
					view = CPSConstants.TRAININGNOMINATIONBOARDSELECTIONLIST;
				else if (CPSUtils.compareStrings(trainingReqBean.getType(), "ADSelection"))
					view = CPSConstants.TRAININGNOMINATIONADSELECTIONLIST;

				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				message = trainingRequestBusiness.manageHRDGTrainingNominations(trainingReqBean);
				List trainingRequestList = null;
				if (CPSUtils.compareStrings(trainingReqBean.getType(), "nomination")) {
					trainingRequestList = trainingRequestBusiness.getTrainingNominationReqList(trainingReqBean);
					session.setAttribute(CPSConstants.TRAININGNOMINATIONREQUESTLIST, trainingRequestList);
				} else if (CPSUtils.compareStrings(trainingReqBean.getType(), "boardSelection")) {
					    trainingRequestList =trainingRequestBusiness.getTrainingNominationBoardSelectionList(trainingReqBean);
						session.setAttribute(CPSConstants.TRAININGNOMINATIONBOARDSELECTIONLIST, trainingRequestList);
				} else if (CPSUtils.compareStrings(trainingReqBean.getType(), "ADSelection")) {
					trainingRequestList = trainingRequestBusiness.getTrainingNominationADSelectionList(trainingReqBean);
					session.setAttribute(CPSConstants.TRAININGNOMINATIONADSELECTIONLIST, trainingRequestList);
				}
				

			} else if (CPSUtils.compareStrings(CPSConstants.MANAGETRAININGFINALNOMINATIONS, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=manageTrainingFinalList");
				view = CPSConstants.TRAININGNOMINATIONFINALSELECTIONLIST;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setIpAddress(request.getRemoteAddr());
				message = trainingRequestBusiness.manageTrainingFinalList(trainingReqBean);
				List trainingRequestList = trainingRequestBusiness.getTrainingNominationFinalSelectionList(trainingReqBean);
				session.setAttribute(CPSConstants.TRAININGNOMINATIONFINALSELECTIONLIST, trainingRequestList);
			}
			else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONSCFASELECTION, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=TrainingNominationsCFASelection");
				view = CPSConstants.TRAININGNOMINATIONSCFASELECTION;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());

				List trainingRequestCourseList = trainingRequestBusiness.getNominationsForCFACourseList(trainingReqBean);
				session.setAttribute("trainingRequestCFACourseList", trainingRequestCourseList);
				
				if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.TRAININGNOMINATIONSCFASELECTIONLIST)))
					session.removeAttribute(CPSConstants.TRAININGNOMINATIONSCFASELECTIONLIST);
				

			} else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATIONSCFASELECTIONLIST, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=TrainingNominationsCFASelectionList");
				view = CPSConstants.TRAININGNOMINATIONSCFASELECTIONLIST;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());

				List trainingRequestList = trainingRequestBusiness.getTrainingNominationsCFASelectionList(trainingReqBean);
				session.setAttribute(CPSConstants.TRAININGNOMINATIONSCFASELECTIONLIST, trainingRequestList);

			}
			else if (CPSUtils.compareStrings(CPSConstants.MANAGETRAININGNOMINATIONSCFASELECTIONLIST, trainingReqBean.getParam())) {
				log.debug("TrainingRequestController --> onSubmit --> param=manageTrainingNominationsCFASelectionList");
				view = CPSConstants.TRAININGNOMINATIONSCFASELECTIONLIST;
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				message = trainingRequestBusiness.manageTrainingNominationsCFASelectionList(trainingReqBean);
				List trainingRequestList = trainingRequestBusiness.getTrainingNominationsCFASelectionList(trainingReqBean);
				session.setAttribute(CPSConstants.TRAININGNOMINATIONSCFASELECTIONLIST, trainingRequestList);

			}
			else if(CPSUtils.compareStrings("ViewTrainingNominationDetails", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=ViewTrainingNominationDetails");
				view = "ViewTrainingNominationDetails";
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setNomineeSfid(trainingReqBean.getSfid());
				List list = trainingRequestBusiness.viewTrainingNominationDetails(trainingReqBean);
				session.setAttribute("ViewTrainingNominationDetailsDataList", list);
				
				
			}
			else if(CPSUtils.compareStrings("ViewTrainingNomination", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=ViewTrainingNomination");
				view = "ViewTrainingNomination";
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setNomineeSfid(trainingReqBean.getSfid());
				trainingReqBean = trainingRequestBusiness.viewTrainingNomination(trainingReqBean);
				
			}
			else if(CPSUtils.compareStrings("ViewTrainingNominationDetailsDataList", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=ViewTrainingNominationDetailsDataList");
	
				view = "ViewTrainingNominationDetailsDataList"; 
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setIpAddress(request.getRemoteAddr());
				List list = trainingRequestBusiness.viewTrainingNominationDetails(trainingReqBean);
				session.setAttribute("ViewTrainingNominationDetailsDataList", list);
				
			}
			else if(CPSUtils.compareStrings("CancelTrainingNomination", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CancelTrainingNomination");
				view = "ViewTrainingNominationDetails";
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setIpAddress(request.getRemoteAddr());
				trainingReqBean.setNomineeSfid(trainingReqBean.getSfid());
				message = trainingRequestProcess.cancelRequest(trainingReqBean);
				
				List list = trainingRequestBusiness.viewTrainingNominationDetails(trainingReqBean);
				session.setAttribute("ViewTrainingNominationDetailsDataList", list);
				
				
			}
			else if(CPSUtils.compareStrings("CancelTrainingNominationAndInitiateReq", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=CancelTrainingNominationAndInitiateReq");
				view = "ViewTrainingNominationDetails";
				trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingReqBean.setIpAddress(request.getRemoteAddr());
				trainingReqBean.setRefRequestId(trainingReqBean.getRequestId());
				trainingReqBean.setNomineeSfid(trainingReqBean.getSfid());
				//message = trainingRequestProcess.updateRequest(trainingReqBean);
				message = trainingRequestProcess.initWorkflow(trainingReqBean);
				
				List list = trainingRequestBusiness.viewTrainingNominationDetails(trainingReqBean);
				session.setAttribute("ViewTrainingNominationDetailsDataList", list);
				
				
			}
			else if(CPSUtils.compareStrings("GetBrochure", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=GetBrochure");
				view = CPSConstants.TRAININGCIRCULATIONMASTER;
				FilesBean file = trainingRequestBusiness.getBrochure(Integer.parseInt(trainingReqBean.getFileId()));
				response.setContentType(file.getType());
				response.setContentLength(file.getFile().length);
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"");
				FileCopyUtils.copy(file.getFile(), response.getOutputStream());
				
				
			}
			//Year Book
			else if(CPSUtils.compareStrings("HRDGYearBook", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGYearBook");
				view = "HRDGYearBookMaster";
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingRequestBusiness.getTrainingTypeList());
				session.setAttribute(CPSConstants.FINANCIALYEARLIST, trainingRequestBusiness.getFinancialYear());
			}
			
			//HRDG Report
			else if(CPSUtils.compareStrings("HRDGReport", request.getParameter(CPSConstants.PARAM))) {
				log.debug("TrainingMasterController --> onSubmit --> param=HRDGReport");
				view = "HRDGTabularReport";
				session.setAttribute(CPSConstants.TRAININGTYPELIST, trainingRequestBusiness.getTrainingTypeList());
				session.setAttribute(CPSConstants.FINANCIALYEARLIST, trainingRequestBusiness.getFinancialYear());
			}
			
			
			session.setAttribute(CPSConstants.RETURN, view);
			mav = new ModelAndView(view, CPSConstants.TRAININGREQUEST, trainingReqBean);
			mav.addObject(CPSConstants.MESSAGE, message);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}

		return mav;
	}

}
