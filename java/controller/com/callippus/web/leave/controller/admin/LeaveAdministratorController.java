package com.callippus.web.leave.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

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

import com.callippus.web.business.leave.admin.LeaveAdministratorBusiness;
import com.callippus.web.business.requestprocess.LeaveRequestProcess;
import com.callippus.web.business.requestprocess.LeaveWaterRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.leave.dao.request.ILeaveRequestDAO;

@Controller
@RequestMapping("/leaveAdmin.htm")
@SessionAttributes
public class LeaveAdministratorController {
	private static Log log = LogFactory.getLog(LeaveAdministratorController.class);
	@Autowired
	private LeaveAdministratorBusiness leaveAdministratorBusiness;
	@Autowired
	private LeaveRequestProcess leaveRequestProcess;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private ILeaveRequestDAO leaveRequestDAO;
	
	@Autowired
	LeaveWaterRequestProcess leaveWaterRequestProcess;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.LEAVEADMIN) LeaveAdministratorBean leaveBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			/*request.getHeader("Cache-Control");
			response.setHeader("no-cache", "no-store");*/
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(leaveBean.getParam())) {
				leaveBean.setParam(CPSConstants.HOME);  
			} else if (CPSUtils.compareStrings(leaveBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			
			if (CPSUtils.isNullOrEmpty(leaveBean.getSfID())) {
				leaveBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getType())) {
				session.setAttribute(CPSConstants.MASTERTYPE, leaveBean.getType());
			}
			/**
			 * Data entry code starts
			 */
			if (CPSUtils.compareStrings(CPSConstants.DATAENTRYHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=dataEntryHome");
				leaveBean = leaveAdministratorBusiness.getEmpAvailableLeaves(leaveBean);
				if (CPSUtils.checkList(leaveBean.getEntryLeaves())) {
					session.setAttribute(CPSConstants.JSONAVAILABLELEAVESLIST, (JSONArray) JSONSerializer.toJSON(leaveBean.getEntryLeaves()));
				}
				
				viewName = CPSConstants.ENTERAVAILABLELEAVES;
			}
			else if (CPSUtils.compareStrings(CPSConstants.DATAERPENTRYHOME, leaveBean.getParam())) {
				// added by bkr 14/06/2016
				leaveBean = leaveAdministratorBusiness.getEmpAvailableOrNot(leaveBean);
				if (CPSUtils.checkList(leaveBean.getEntryErpLeaves())) {
					session.setAttribute(CPSConstants.JSONERPAVAILABLELEAVESLIST, (JSONArray) JSONSerializer.toJSON(leaveBean.getEntryErpLeaves()));
				}
				viewName = CPSConstants.ENTERERPAVAILABLELEAVES;
			}
				else if (CPSUtils.compareStrings(CPSConstants.ERPEMPLEAVESENTRYSAVE, leaveBean.getParam())) {
				// added by bkr 14/06/2016
				leaveBean = leaveAdministratorBusiness.getEmplyeeLeavesSave(leaveBean);
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.ERPEMPLEAVEBALANCE, leaveBean.getParam())) {
				
				// added by bkr 14/06/2016
				leaveBean = leaveRequestProcess.geterpEmpLeaveBalance(leaveBean);
				session.setAttribute(CPSConstants.JSONEMPAVAILLEAVESLIST, JSONSerializer.toJSON(leaveBean.getSetErpEmpAppliedLeavesList()));
				//added 29/06/2016
				leaveRequestProcess.getEmployeeAppliedLeavesList(leaveBean);
				session.setAttribute(CPSConstants.ERPAPPLIEDLEAVESLIST, leaveBean.setErpAppliedLeavesList);
				viewName = CPSConstants.ERPEMPLEAVEBALANEHOME;
			}
			else if (CPSUtils.compareStrings("spellsDetails", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = spellsDetails");
				leaveBean = leaveAdministratorBusiness.getSpellsDetails(leaveBean);
				session.setAttribute(CPSConstants.JSONLEAVESPELLSLIST, JSONSerializer.toJSON(leaveBean.getSpellsDetails()));
				leaveBean.setMessage("dataEntry");
				viewName = CPSConstants.LEAVERESULT;
			} else if (CPSUtils.compareStrings("saveEnterLeaves", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=saveEnterLeaves");
				leaveBean.setMessage(leaveAdministratorBusiness.submitEnterAvailableLeaves(leaveBean));
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.VERIFYHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=verifyHome");
				leaveBean.setDesignationList(leaveAdministratorBusiness.getDesignationList());
				leaveBean.setLeaveBalanceList(leaveAdministratorBusiness.getLeaveBalanceList(leaveBean));
				session.setAttribute(CPSConstants.LEAVEBALACELIST, leaveBean.getLeaveBalanceList());
				session.setAttribute(CPSConstants.DESIGNATIONLIST, leaveBean.getDesignationList());
				viewName = CPSConstants.LEAVEBALANCEVERIFY;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LEAVEBALANCEVERIFYLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.EMPSEARCH, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=empSearch");
				leaveBean.setLeaveBalanceList(leaveAdministratorBusiness.getLeaveBalanceList(leaveBean));
				session.setAttribute(CPSConstants.LEAVEBALACELIST, leaveBean.getLeaveBalanceList());
				viewName = CPSConstants.LEAVEBALANCEVERIFYLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.VERIFYLEAVES, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=verifyLeaves");
				leaveBean.setMessage(leaveAdministratorBusiness.submitEnterAvailableLeaves(leaveBean));
				if (CPSUtils.compareStrings(leaveBean.getVerifyFlag(), CPSConstants.Y)) {
					// leave balance verified, so insert in the main table
					leaveBean.setMessage(leaveAdministratorBusiness.submitLeaveBalance(leaveBean));
				}
				leaveBean.setLeaveBalanceList(leaveAdministratorBusiness.getLeaveBalanceList(leaveBean));
				session.setAttribute(CPSConstants.LEAVEBALACELIST, leaveBean.getLeaveBalanceList());
				viewName = CPSConstants.LEAVEBALANCEVERIFYLIST;
			} else if (CPSUtils.compareStrings("dbScript", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=verifyLeaves");
				leaveAdministratorBusiness.dataEntry();
			}

			/**
			 * Data entry code ends
			 */

			/**
			 * DO Part code starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.DOPARTHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=dopartHome");
				leaveBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
				/*leaveBean = leaveAdministratorBusiness.getSearchedLeaves(leaveBean);
				session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveBean.getAppliedLeavesList());
				session.setAttribute(CPSConstants.CANCELLEDLEAVESLIST, leaveBean.getCancelledLavesList());
				session.setAttribute(CPSConstants.CONVERTEDLEAVESLIST, leaveBean.getConvertedLavesList());*/
				//session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getLeaveCasualitiesList(leaveBean));
				session.setAttribute(CPSConstants.TYPEMASTERLIST, leaveBean.getTypeList());
				viewName = CPSConstants.LEAVERESEARCHDETAILS;
				leaveBean.setMessage(CPSConstants.ADMIN);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LEAVEADMINSEARCHLIST);
			} else if (CPSUtils.compareStrings("gazetted", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = gazetted");
				leaveBean.setDoPartList(leaveRequestDAO.getDoPartList(leaveBean));
				/*leaveBean = leaveAdministratorBusiness.getSearchedLeaves(leaveBean);
				session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveBean.getAppliedLeavesList());
				session.setAttribute(CPSConstants.CANCELLEDLEAVESLIST, leaveBean.getCancelledLavesList());
				session.setAttribute(CPSConstants.CONVERTEDLEAVESLIST, leaveBean.getConvertedLavesList());
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());*/
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				//session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getLeaveCasualitiesList(leaveBean));
				viewName = CPSConstants.LEAVEADMINSEARCHLIST;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LEAVEADMINSEARCHLIST);
			}else if (CPSUtils.compareStrings("gazettedNEW", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = gazetted");
				leaveBean.setParam("gazetted");
				leaveBean = leaveAdministratorBusiness.getSearchedLeaves(leaveBean);
				session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveBean.getAppliedLeavesList());
				session.setAttribute(CPSConstants.CANCELLEDLEAVESLIST, leaveBean.getCancelledLavesList());
				session.setAttribute(CPSConstants.CONVERTEDLEAVESLIST, leaveBean.getConvertedLavesList());
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getLeaveCasualitiesList(leaveBean));
				viewName = CPSConstants.LEAVEADMINSEARCHLIST;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LEAVEADMINSEARCHLIST);
			} 
			else if (CPSUtils.compareStrings(CPSConstants.GETLEAVES, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = getLeaves");
				session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveAdministratorBusiness.getSearchedLeaves(leaveBean).getAppliedLeavesList());
				session.setAttribute(CPSConstants.CANCELLEDLEAVESLIST, leaveBean.getCancelledLavesList());
				session.setAttribute(CPSConstants.CONVERTEDLEAVESLIST, leaveBean.getConvertedLavesList());
				if (CPSUtils.compareStrings(leaveBean.getType(), CPSConstants.USER)) {
					viewName = CPSConstants.LEAVESEARCHLIST;
				} else {
					viewName = CPSConstants.LEAVEADMINSEARCHLIST;
				}
				session.setAttribute(CPSConstants.RETURN, viewName);

			} else if (CPSUtils.compareStrings(CPSConstants.PUBLISHLEAVES, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=publishLeaves");
				leaveBean.setIpAddress(request.getRemoteAddr());
				leaveBean = leaveAdministratorBusiness.completeLeaveRequests(leaveBean);

				/*
				 * if (CPSUtils.compareStrings(leaveBean.getMessage(), CPSConstants.SUCCESS)) { HashMap<String, String> pmap = new HashMap<String, String>(); pmap.put("DoPartID",
				 * String.valueOf(leaveBean.getDoPartID())); jasperReportCreator.createPdf(session, "LeaveDoPartReport.jasper", pmap, "LeaveDoParts", "DO_PART_" + leaveBean.getDoPartID(), false); }
				 */

				session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveAdministratorBusiness.getSearchedLeaves(leaveBean).getAppliedLeavesList());
				session.setAttribute(CPSConstants.CANCELLEDLEAVESLIST, leaveBean.getCancelledLavesList());
				session.setAttribute(CPSConstants.CONVERTEDLEAVESLIST, leaveBean.getConvertedLavesList());
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				viewName = CPSConstants.LEAVEADMINSEARCHLIST;
				session.setAttribute(CPSConstants.RETURN, viewName);
			} else if (CPSUtils.compareStrings(CPSConstants.DOPARTSEARCH, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=doPartSearch");
				leaveBean.setDoPartNo("");
				leaveBean.setDoPartDate("");
				leaveBean.setGazettedType("");
				leaveBean = leaveAdministratorBusiness.getCasualitiesDetails(leaveBean);
				//leaveBean = leaveAdministratorBusiness.getDoPartSearchList(leaveBean);
				//session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				session.setAttribute("DopartType", leaveBean.getTypeList());
				session.setAttribute(CPSConstants.YEARS, leaveBean.getYearsList());
				session.removeAttribute(CPSConstants.DOPARTLIST);
				viewName = CPSConstants.DOPARTSEARCHPAGE;
				//session.setAttribute(CPSConstants.RETURN, CPSConstants.DOPARTSEARCHLISTPAGE);
			} else if (CPSUtils.compareStrings(CPSConstants.GETDOPARTLIST, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param=getDoPartList");
				leaveBean = leaveAdministratorBusiness.getDoPartSearchList(leaveBean);
				
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				
				viewName = CPSConstants.DOPARTSEARCHLISTPAGE;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DOPARTSEARCHLISTPAGE);
			}
			//DO-Part-II Starts
			else if (CPSUtils.compareStrings(CPSConstants.TYPEHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=typeHome");
				leaveBean=leaveAdministratorBusiness.getTypeHomeDetails(leaveBean);
				viewName = CPSConstants.TYPEMASTER;
				session.setAttribute(CPSConstants.TYPEMASTERLIST, leaveBean.getTypeList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TYPEMASTERLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SAVETYPEDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=submitType");
				leaveAdministratorBusiness.manageTypeDetails(leaveBean);
				leaveBean=leaveAdministratorBusiness.getTypeHomeDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
				session.setAttribute(CPSConstants.TYPEMASTERLIST, leaveBean.getTypeList());
				}viewName = CPSConstants.TYPEMASTERLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETETYPEDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=submitType");
				leaveAdministratorBusiness.deleteTypeDetails(leaveBean);
				leaveBean=leaveAdministratorBusiness.getTypeHomeDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.DELETE)){
				session.setAttribute(CPSConstants.TYPEMASTERLIST, leaveBean.getTypeList());
				}viewName = CPSConstants.TYPEMASTERLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.CASUALITIESHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=casualitiesHome");
				leaveBean=leaveAdministratorBusiness.getCasualitiesDetails(leaveBean);
				viewName = CPSConstants.CASUALITIESMASTER;
				session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getCasualitiesList(leaveBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CASUALITIESLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SEARCHCASUALITY, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=getGazettedWiseCasuality");
				viewName = CPSConstants.CASUALITIESLIST;
				session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getGazettedCasualitiesList(leaveBean));
			}else if (CPSUtils.compareStrings(CPSConstants.SAVECASUALITYDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=saveCasualityDetails");
				leaveAdministratorBusiness.manageCasualityDetails(leaveBean);
				//if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS))
				//session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getCasualitiesList(leaveBean));
				session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getGazettedCasualitiesList(leaveBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CASUALITIESLIST);
				viewName = CPSConstants.CASUALITIESLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETECASUALITIESDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=deleteCasualitiesDetails");
				leaveAdministratorBusiness.deleteCasualityDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.DELETE)){
				    //session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getCasualitiesList(leaveBean));
					session.setAttribute(CPSConstants.CASUALITIESLIST, leaveAdministratorBusiness.getGazettedCasualitiesList(leaveBean));
				}
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CASUALITIESLIST);
				viewName = CPSConstants.CASUALITIESLIST;
			}
			else if (CPSUtils.compareStrings(CPSConstants.TYPEDESIGNATIONHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=typeDesignationHome");
				leaveBean=leaveAdministratorBusiness.getTypeDesigHomeDetails(leaveBean);
				viewName = CPSConstants.TYPEDESIGNATIONHOME;
				session.setAttribute(CPSConstants.DESIGNATIONLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveAdministratorBusiness.getEditingDesignations(leaveBean)));
				session.setAttribute("deselectedDesig", (JSONArray) JSONSerializer.toJSON(leaveBean.getDeselectedDesigList()));
				session.setAttribute(CPSConstants.TYPEDESIGNATIONLIST, leaveAdministratorBusiness.getTypeDesigList(leaveBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TYPEDESIGNATIONLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SAVETYPEDESIGDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=manageTypeDesignations");
				leaveAdministratorBusiness.manageTypeDesigDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
				session.setAttribute(CPSConstants.TYPEDESIGNATIONLIST, leaveAdministratorBusiness.getTypeDesigList(leaveBean));
				}viewName = CPSConstants.TYPEDESIGNATIONLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETETYPEDESIGDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=deleteTypeDesigDetails");
				leaveAdministratorBusiness.deleteTypeDesigDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
				session.setAttribute(CPSConstants.TYPEDESIGNATIONLIST, leaveAdministratorBusiness.getTypeDesigList(leaveBean));
				}viewName = CPSConstants.TYPEDESIGNATIONLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.TASKHOLDERHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=taskHolderHome");
				leaveBean=leaveAdministratorBusiness.getTaskHolderDetails(leaveBean);
				viewName = CPSConstants.TASKHOLDERDETAILS;
				session.setAttribute(CPSConstants.TASKHOLDERDETAILSLIST, leaveAdministratorBusiness.getTaskHolderList(leaveBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TASKHOLDERDETAILSLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SAVETASKHOLDERDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=saveTaskHolderDetails");
				leaveAdministratorBusiness.manageTaskHolderDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
				session.setAttribute(CPSConstants.TASKHOLDERDETAILSLIST, leaveAdministratorBusiness.getTaskHolderList(leaveBean));
				}viewName = CPSConstants.TASKHOLDERDETAILSLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETETASKHOLDERDETAILS, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=deleteTaskHolderDetails");
				leaveAdministratorBusiness.deleteTaskHolderDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
				session.setAttribute(CPSConstants.TASKHOLDERDETAILSLIST, leaveAdministratorBusiness.getTaskHolderList(leaveBean));
				}viewName = CPSConstants.TASKHOLDERDETAILSLIST;
			}
			else if (CPSUtils.compareStrings(CPSConstants.DOPARTDETAILSHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=doPartDetailsHome");
				leaveBean=leaveAdministratorBusiness.getDoPartRoleTaskDetails(leaveBean);
				session.setAttribute(CPSConstants.TASKHOLDERDETAILSLIST, leaveAdministratorBusiness.getTypeTaskHolder(leaveBean));
				viewName = CPSConstants.DOPARTROLETASK;
				//session.setAttribute(CPSConstants.DESIGNATIONLISTJSON, (JSONArray) JSONSerializer.toJSON(leaveAdministratorBusiness.getEditingDesignations(leaveBean)));
				session.setAttribute(CPSConstants.DOPARTROLETASKLIST, leaveAdministratorBusiness.getTaskHolderDesigList(leaveBean));
				session.setAttribute(CPSConstants.DESIGNATIONLISTJSON, leaveBean.getDesignationList());
				session.setAttribute("getAllDesignations", (JSONArray) JSONSerializer.toJSON(leaveBean.getAllDesignationList()));

				session.setAttribute(CPSConstants.RETURN, CPSConstants.DOPARTROLETASKLIST);
			}
			//sridhar starts
			
			else if (CPSUtils.compareStrings("getAssignedDesignations", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=getAssignedDesignations");
				session.removeAttribute("getAllDesignations");
				leaveBean=leaveAdministratorBusiness.getAssigningDesignationsList(leaveBean);
				session.setAttribute("getDesignations", (JSONArray) JSONSerializer.toJSON(leaveBean.getDesignationList()));
				session.setAttribute("getAllDesignations", (JSONArray) JSONSerializer.toJSON(leaveBean.getAllDesignationList()));
				session.setAttribute("assignedDesignations", (JSONArray) JSONSerializer.toJSON(leaveAdministratorBusiness.getAssignedDesignations(leaveBean.getGazettedType())));
				viewName = "DoPartRoleTaskDetailsList";
			}
			
			else if (CPSUtils.compareStrings("deleteAssignedTypeDesig", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=deleteAssignedTypeDesig");
				leaveBean.setMessage(leaveAdministratorBusiness.deleteAssignedTypeDesig(leaveBean));
				session.setAttribute(CPSConstants.DOPARTROLETASKLIST, leaveAdministratorBusiness.getTaskHolderDesigList(leaveBean));
				viewName = CPSConstants.DOPARTROLETASKLIST;
			}
			
			//sridhar ends
			
			else if (CPSUtils.compareStrings(CPSConstants.SAVETASKHOLDERDESIG, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=manageTaskHolderDesig");
				leaveAdministratorBusiness.manageTaskHolderDesignations(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
				session.setAttribute(CPSConstants.DOPARTROLETASKLIST, leaveAdministratorBusiness.getTaskHolderDesigList(leaveBean));
				}viewName = CPSConstants.DOPARTROLETASKLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETETASKHOLDERDESIG, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=deleteTaskHolderDetails");
				leaveAdministratorBusiness.deleteTaskHolderDesigDetails(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
				session.setAttribute(CPSConstants.DOPARTROLETASKLIST, leaveAdministratorBusiness.getTaskHolderDesigList(leaveBean));
				}viewName = CPSConstants.DOPARTROLETASKLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.RELEASEDOPART, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=releaseDOPart");
				leaveBean=leaveAdministratorBusiness.getDOPartReleaseDetails(leaveBean);
				leaveBean=leaveAdministratorBusiness.getdoPartCountHomeDetails(leaveBean);               //This line has been added for doPartType List box
				viewName = CPSConstants.DOPARTPORTTALHOME;
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DOPARTRELEASEDETAILS);
			}else if (CPSUtils.compareStrings("releaseDOPartList", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=releaseDOPartList");
				leaveBean=leaveAdministratorBusiness.getDOPartReleaseDetails(leaveBean);
				leaveBean=leaveAdministratorBusiness.getdoPartCountHomeDetails(leaveBean);               //This line has been added for doPartType List box
				viewName = CPSConstants.DOPARTRELEASEDETAILS;
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				session.setAttribute(CPSConstants.RETURN,CPSConstants.DOPARTRELEASEDETAILS);
			}else if (CPSUtils.compareStrings(CPSConstants.SAVEDOPARTPORTAL, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=saveDoPartPotal");
				leaveBean=leaveAdministratorBusiness.getDOPartReleaseDetails(leaveBean);
				leaveAdministratorBusiness.manageDoPartPortal(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
					leaveBean=leaveAdministratorBusiness.getDOPartReleaseDetails(leaveBean);
					session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
					}viewName = CPSConstants.DOPARTRELEASEDETAILS;
			}else if (CPSUtils.compareStrings("freezeOut", leaveBean.getParam())) {       // New code for doPartII freeze out condition
				log.debug("LeaveAdministratorController --> onSubmit --> param=freezeOut");
//				leaveBean=leaveAdministratorBusiness.getDOPartReleaseDetails(leaveBean);
				leaveAdministratorBusiness.dopartIIFreezeOut(leaveBean);
				if(CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)){
					leaveBean=leaveAdministratorBusiness.getDOPartReleaseDetails(leaveBean);
					session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
					}viewName = CPSConstants.DOPARTRELEASEDETAILS;
			}else if (CPSUtils.compareStrings(CPSConstants.DOPARTMODULEHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=DoPartModulesHome");
				leaveBean=leaveAdministratorBusiness.getdoPartCountHomeDetails(leaveBean);
				//leaveBean=leaveAdministratorBusiness.getDoPartModulesHome(leaveBean);
				//session.setAttribute(CPSConstants.ESSMODULELIST, leaveAdministratorBusiness.getEssModuleList(leaveBean));
				viewName = CPSConstants.DOPARTMODULEHOME;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TASKHOLDERDETAILSLIST);
			}else if (CPSUtils.compareStrings("getDoPartCount", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=getDoPartCount");
				leaveBean=leaveAdministratorBusiness.getdoPartCountList(leaveBean);
				viewName = CPSConstants.DOPARTCOUNT;
				session.setAttribute(CPSConstants.DOPARTLIST, leaveBean.getDoPartList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DOPARTCOUNT);
			}
			else if (CPSUtils.compareStrings("getModuleWiseDoPartDetails", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=getModuleWiseDoPartDetails");
				viewName = "DoPartDetailsModuleWise";
				leaveBean = leaveAdministratorBusiness.getSearchedLeaves(leaveBean);
				session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveBean.getAppliedLeavesList());
				session.setAttribute(CPSConstants.CANCELLEDLEAVESLIST, leaveBean.getCancelledLavesList());
				session.setAttribute(CPSConstants.CONVERTEDLEAVESLIST, leaveBean.getConvertedLavesList());
				//session.setAttribute(CPSConstants.RETURN, CPSConstants.TASKHOLDERDETAILSLIST);
			}
			/**
			 * DO Part code ends
			 */
			/**
			 * Leave search code starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVESEARCH, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=leaveSearch");
				leaveBean.setType(CPSConstants.USER);
				session.setAttribute(CPSConstants.APPLIEDLEAVESLIST, leaveAdministratorBusiness.getSearchedLeaves(leaveBean).getAppliedLeavesList());

				leaveBean.setMessage(CPSConstants.USER);
				viewName = CPSConstants.LEAVERESEARCHDETAILS;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LEAVESEARCHLIST);
			}
			/**
			 * Leave search code ends
			 */

			/**
			 * Employees leave account search starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.EMPLEAVEACCOUNT, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=empLeaveAccount");
				//leaveBean = leaveAdministratorBusiness.getLeaveAccountHomeDetails(leaveBean);
				viewName = CPSConstants.GETEMPLOYEEDETAILS;
			}
			/**
			 * Employees leave account search end
			 */
			
			/**
			 * Checking for the existence of an employee with respect to leave script applicable years 
			 */
			// Added by Naresh
			else if (CPSUtils.compareStrings(CPSConstants.CHECKEMP, leaveBean.getParam())) {
				log.debug("LeaveRequestController --> onSubmit --> param = checkEmp");
				leaveBean.setMessage(leaveAdministratorBusiness.checkEmployee(leaveBean.getSearchSfid()));
				if (CPSUtils.compareStrings(leaveBean.getMessage(), CPSConstants.SUCCESS)) {
					leaveBean = leaveAdministratorBusiness.getApplicableYears(leaveBean);
					request.setAttribute("applicableYears", leaveBean.getYearsList());
					if (leaveBean.getYearsList().isEmpty()) {
						request.setAttribute("retiredDate", leaveAdministratorBusiness.getRetirementDate(leaveBean.getSearchSfid()));
						leaveBean.setMessage(CPSConstants.RETIRED);
					} else {
						leaveBean.setMessage(CPSConstants.EMPEXITS);
					}
				} else if (CPSUtils.compareStrings(leaveBean.getMessage(), CPSConstants.INVALID)) {
					leaveBean.setMessage(CPSConstants.EMPNOTEXISTS);
				}
				viewName = CPSConstants.LEAVERESULT;
			} // End
			
			/**
			 * Leave Manual Script Starts
			 */

			else if (CPSUtils.compareStrings(CPSConstants.MANUALSCRIPT, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = manualScript");
				viewName = CPSConstants.LEAVEMANUALSCRIPT;
			} else if (CPSUtils.compareStrings(CPSConstants.RUNSCRIPT, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = runScript");
				if (CPSUtils.isNullOrEmpty(leaveBean.getScriptDate())) {
					leaveBean.setScriptDate(CPSUtils.getCurrentDate());
				}
				leaveBean.setMessage(leaveRequestProcess.autoRunScript(leaveBean.getScriptDate()));
				session.setAttribute("scriptDate", leaveBean.getScriptDate());
				viewName = CPSConstants.RESULT;
			}
			/**
			 * Leave Manual Script ends
			 */

			/**
			 * Leave Transaction Search Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVETXNHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=leaveTxnHome");
				leaveBean = leaveAdministratorBusiness.getLeaveTypeList(leaveBean);
				session.setAttribute(CPSConstants.LEAVETXNLIST, leaveBean.getLeaveTxnList());
				viewName = CPSConstants.LEAVETXNSEARCH;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LEAVETXNSEARCHLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.TXNLEAVESEARCH, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=txnLeaveSearch");
				leaveBean = leaveAdministratorBusiness.getTxnSearchedLeaves(leaveBean);
				session.setAttribute(CPSConstants.LEAVETXNLIST, leaveBean.getLeaveTxnList());
				viewName = CPSConstants.LEAVETXNSEARCHLIST;
			}

			/**
			 * Leave Transaction Search end
			 */

			/**
			 * Leave Exceptional Employees Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVEEXCEPTIONALEMP, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = leaveExceptionEmp");
				leaveBean = leaveAdministratorBusiness.getEmployeesList(leaveBean);
				leaveBean = leaveAdministratorBusiness.getLeaveExpList(leaveBean);
				session.setAttribute(CPSConstants.LEAVEEXPLIST, leaveBean.getLeaveExpList());
				session.setAttribute(CPSConstants.EMPLOYEESLIST, leaveBean.getEmployeesList());
				viewName = CPSConstants.LEAVEEXCEPTIONALEMPLOYEES;
			} else if (CPSUtils.compareStrings(CPSConstants.LEAVEEXPSUBMIT, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = leaveExpSubmit");
				leaveBean.setMessage(leaveAdministratorBusiness.submitLeaveExceptionalEmp(leaveBean));
				viewName = CPSConstants.RESULT;
			}

			/**
			 * Leave Exceptional Employees end
			 */

			/**
			 * Leave Adit starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVEADITHOME, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=leaveAditHome");
				leaveBean = leaveAdministratorBusiness.getLeaveTypeList(leaveBean);
				viewName = CPSConstants.LEAVEADIT;
			} else if (CPSUtils.compareStrings(CPSConstants.SAVELEAVEADIT, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=saveLeaveAdit");
				leaveBean.setMessage(leaveAdministratorBusiness.saveLeaveAdit(leaveBean));
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.AVAILABLELEAVES, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=availableLeaves");
				leaveBean.setBalanceLeaves(leaveAdministratorBusiness.getAvailableLeavesList(leaveBean));
				viewName = CPSConstants.LEAVEBALANCE;
			}
			/**
			 * Leave Adit end
			 */

			/**
			 * Copying Leaves from Leave manual entry to leave txn details
			 */
			else if (CPSUtils.compareStrings(CPSConstants.COPYLEAVES, leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=copyLeaves");
				leaveAdministratorBusiness.copyLeaves();
			}
			/**
			 * Script for 2011 leave balance backup, update leave account balance column For this add previous year available_leaves table as backup_leave_balance
			 */
			else if (CPSUtils.compareStrings("2011backup", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param=2011backup");
				leaveAdministratorBusiness.backup2011();
			}
			/**
			 * Leave Business Rules
			 * @param businessRules
			 * @return value
			 * @throws Exception
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LEAVEBUSINESSRULES, leaveBean.getParam())) { // Added by Naresh
				log.debug("LeaveAdministratorController --> onSubmit --> param = businessRules");
				leaveBean = leaveAdministratorBusiness.getLeaveBusinessRules(leaveBean);
				//request.setAttribute("leaveRequestTypes", leaveBean.getRequestTypes());
				session.setAttribute("leaveTypes", leaveBean.getLeaveTypeList());
				request.setAttribute("leaveBusinessRules", leaveBean.getBusinessRules());
				viewName = CPSConstants.LEAVEBUSINESS;
			} else if (CPSUtils.compareStrings("saveBusinessRules", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = saveBusinessRules");
				leaveBean = leaveAdministratorBusiness.submitLeaveBusinessRules(leaveBean);
				//request.setAttribute("leaveTypes", leaveBean.getLeaveTypeList());
				request.setAttribute("leaveBusinessRules", leaveBean.getBusinessRules());
				viewName = CPSConstants.LEAVEBUSINESS;
			} else if (CPSUtils.compareStrings("deleteBusinessRule", leaveBean.getParam())) {
				log.debug("LeaveAdministratorController --> onSubmit --> param = deleteBusinessRule");
				leaveBean = leaveAdministratorBusiness.deleteBusinessRule(leaveBean);
				request.removeAttribute("leaveBusinessRules");
				request.setAttribute("leaveBusinessRules", leaveBean.getBusinessRules());
				viewName = CPSConstants.LEAVEBUSINESS;
			} // End
			mav = new ModelAndView(viewName, CPSConstants.LEAVEADMIN, leaveBean);
			if (!CPSUtils.isNullOrEmpty(leaveBean.getResult())) {
				mav.addObject(CPSConstants.RESULTMSG, leaveBean.getResult());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, leaveBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getNoOfLeaves())) {
				mav.addObject(CPSConstants.AVAILABLELEAVES, leaveBean.getBalanceLeaves());
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getResult())) {
				mav.addObject(CPSConstants.RESULT, leaveBean.getResult());
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
