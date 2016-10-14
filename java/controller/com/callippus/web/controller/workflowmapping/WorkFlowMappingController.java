package com.callippus.web.controller.workflowmapping;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
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
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.hrdg.training.TrainingCirculationBusiness;
import com.callippus.web.business.hrdg.training.request.TrainingRequestBusiness;
import com.callippus.web.business.telephone.TelePhoneBillBusiness;
import com.callippus.web.business.tutionFee.TutionFeeBusiness;
import com.callippus.web.business.workflowmapping.WorkFlowMappingBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.controller.master.MasterDataController;
import com.callippus.web.tada.beans.request.TadaRequestBean;

@Controller
@RequestMapping("/workflowmap.htm")
@SessionAttributes
public class WorkFlowMappingController {
	private static Log log = LogFactory.getLog(MasterDataController.class);
	@Autowired
	private WorkFlowMappingBusiness workFlowMappingBusiness;
	@Autowired
	private ConnectionUtil connectionUtils;
	@Autowired
	private TrainingCirculationBusiness trainingCirculationBusiness;
	@Autowired
	private TrainingRequestBusiness trainingRequestBusiness;
    @Autowired
	private TutionFeeBusiness tutionFeeBusiness;
    @Autowired
	private TelePhoneBillBusiness telePhoneBillBusiness;
    
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.WORKFLOWMAP) WorkFlowMappingBean workBeanMap, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String message = "";
		String viewName = "";
		try {
			ErpAction.sessionChecks(request);
			session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(workBeanMap.getParam())) {
				workBeanMap.setParam(CPSConstants.PISHOME);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, workBeanMap.getParam())) {
				workBeanMap.setType((String) session.getAttribute(CPSConstants.TYPE));
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.WORKFLOWMAP, workBeanMap);
			}

			if (CPSUtils.compareStrings(CPSConstants.MAPPING, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=mapping");
				workBeanMap = workFlowMappingBusiness.getHomeMappingDetails();
				mav = new ModelAndView(CPSConstants.REQUESTWORKFLOWMAPPING, CPSConstants.WORKFLOWMAP, workBeanMap);
				mav.addObject(CPSConstants.INSTANCELIST, workBeanMap.getInstanceList());
				mav.addObject(CPSConstants.REQUESTLIST, workBeanMap.getRequestList());
				mav.addObject(CPSConstants.WORKFLOWLIST, workBeanMap.getWorkflowList());
				session.setAttribute("allList", workBeanMap.getAllList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.REQUESTWORKFLOWMAPPING);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=manage");
				workBeanMap = workFlowMappingBusiness.saveValues(workBeanMap);
				mav = new ModelAndView("RequestWorkflowList", CPSConstants.WORKFLOWMAP, workBeanMap);
				session.setAttribute("allList", workBeanMap.getAllList());
				session.setAttribute(CPSConstants.RETURN, "RequestWorkflowList");
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());

			} else if (CPSUtils.compareStrings(CPSConstants.DELETEMAPPING, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=DELETEMAPPING");
				workBeanMap = workFlowMappingBusiness.deleteMapping(workBeanMap);
				mav = new ModelAndView("RequestWorkflowList", CPSConstants.WORKFLOWMAP, workBeanMap);
				session.setAttribute("allList", workBeanMap.getAllList());
				session.setAttribute(CPSConstants.RETURN, "RequestWorkflowList");
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());

			} else if (CPSUtils.compareStrings(CPSConstants.REQWORKFLOW, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=reqworkflow");
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					workBeanMap = workFlowMappingBusiness.getRequestWorkFlowType();
					mav = new ModelAndView(CPSConstants.WORKFLOWMAPPING, CPSConstants.WORKFLOWMAP, workBeanMap);
					session.setAttribute(CPSConstants.REQWORKFLOWLIST, workBeanMap.getReqWorkFlowList());
					session.setAttribute(CPSConstants.WORKFLOWLIST, workBeanMap.getWorkflowList());
					session.setAttribute(CPSConstants.REQUESTLIST, workBeanMap.getRequestList());
					session.setAttribute(CPSConstants.INSTANCELIST, workBeanMap.getRoleInstanceList());
					session.setAttribute(CPSConstants.DESIGNATIONLIST, workBeanMap.getDesignationList());
					session.setAttribute(CPSConstants.RETURN, CPSConstants.WORKFLOWMAPPING);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
				session.setAttribute(CPSConstants.TYPE, "WorkFlowMappingList");
			} else if (CPSUtils.compareStrings(CPSConstants.REQMANAGE, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=reqmanage");
				message = workFlowMappingBusiness.getReqWorkFlowList(workBeanMap);
				workBeanMap = workFlowMappingBusiness.getRequestWorkFlowType();
				mav = new ModelAndView("WorkFlowMappingList", CPSConstants.WORKFLOWMAP, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.REQWORKFLOWLIST, workBeanMap.getReqWorkFlowList());
				session.setAttribute(CPSConstants.RETURN, "WorkFlowMappingList");
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=delete");
				message = workFlowMappingBusiness.deleteReqWorkFlow(request.getParameter(CPSConstants.ID), workBeanMap.getType());
				workBeanMap = workFlowMappingBusiness.getRequestWorkFlowType();
				mav = new ModelAndView("WorkFlowMappingList", CPSConstants.WORKFLOWMAP, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.REQWORKFLOWLIST, workBeanMap.getReqWorkFlowList());
				mav.addObject(CPSConstants.REQWORKFLOWLIST, workBeanMap.getReqWorkFlowList());
				session.setAttribute(CPSConstants.RETURN, "WorkFlowMappingList");
			}

			else if (CPSUtils.compareStrings("viewFile", workBeanMap.getParam())) {
				Properties props = connectionUtils.loadProps();
				String path = (String) props.getProperty("repositoryPath");
				// byte[] buf = new byte[1024];
				// 1 -- download
				/*
				 * BufferedInputStream in = new BufferedInputStream(new FileInputStream(file)); ServletOutputStream out = response.getOutputStream();
				 * 
				 * response.setContentType("application/text");
				 * 
				 * //response.addHeader("Content-Disposition", "inline; filename=\"" + path + "\""); response.setHeader("Content-Disposition", (new
				 * StringBuilder("inline; filename=\"")).append(path).toString());
				 */
				ServletOutputStream stream = null;
				BufferedInputStream buf = null;
				stream = response.getOutputStream();
				File pdf = new File(path);
				response.setContentType("application/text");

				response.addHeader("Content-Disposition", "attachment; filename=" + path);

				response.setContentLength((int) pdf.length());
				FileInputStream input = new FileInputStream(pdf);
				buf = new BufferedInputStream(input);
				int readBytes = 0;

				while ((readBytes = buf.read()) != -1)
					stream.write(readBytes);
				buf.close();
				/*
				 * while ((in != null) && ((length = in.read(buf)) != -1)) { out.write(buf, 0, length); } out.close();
				 */

				// 2 -- View
				// ServletOutputStream out = response.getOutputStream();
				// BufferedReader in = new BufferedReader(new FileReader(path));
				// response.setContentType("application/text");
				// String line;
				// while((line = in.readLine())!=null){
				// out.print(line);
				// }
				// in.close();

				// 3 -- dll
				// Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path); // open the file chart.pdf

			} else if (CPSUtils.compareStrings(CPSConstants.PISHOME, workBeanMap.getParam())) {
				log.debug("WorkFlowMappingController --> onSubmit --> param=pisHome");
				session.setAttribute(CPSConstants.RESULT, CPSConstants.HOME);
				if (!CPSUtils.isNull(request.getParameter("redirect")) && CPSUtils.compareStrings(request.getParameter("redirect"), "true")) {
					workBeanMap = workFlowMappingBusiness.pisHomeDetails((session.getAttribute(CPSConstants.SFID)).toString(),workBeanMap.getType());
					session.setAttribute("holdRequests", workBeanMap);
					mav = new ModelAndView(CPSConstants.WELCOMEPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter(CPSConstants.PARAM));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
				mav.addObject("myRequests", workBeanMap.getMyRequests());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.WELCOMEPAGE);
			} else if (CPSUtils.compareStrings(CPSConstants.REQUESTDETAILS, workBeanMap.getParam())) {
				log.debug("WorkFlowMappingController --> onSubmit --> param=requestDetails");
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				
				workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				
				/*if(workBeanMap.getRequestfailure().equals(CPSConstants.FAILED)){
					mav = new ModelAndView(CPSConstants.REQUESTDETAILSPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
		 			mav.addObject(CPSConstants.MESSAGE, workBeanMap.getRequestfailure());
				}*/
				if(CPSUtils.compareStrings(workBeanMap.getRequestsuccess(), CPSConstants.REQUESTSUCCESS)){
					workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
					if(!CPSUtils.isNullOrEmpty(workBeanMap.getDopartdateList())){
					request.setAttribute("doPartDatewiseList",workBeanMap.getDopartdateList());}
					
					workBeanMap.setRequestStage(workFlowMappingBusiness.getPresentRequestStage(workBeanMap.getRequestId(), workBeanMap.getSfid()));
					workBeanMap.setCheckStage(workFlowMappingBusiness.getCheckStage(workBeanMap.getRequestId(), workBeanMap.getRequestType()));
					
					workBeanMap.setLastStagePendingCheck(workFlowMappingBusiness.getLastStagePendingCheck(workBeanMap.getRequestId(),workBeanMap.getSfid()));
					
					if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CGHS)) {
						workBeanMap.setCghsDelegateList(workFlowMappingBusiness.cghsDelegateList());
						workBeanMap=workFlowMappingBusiness.cghsreque(workBeanMap);
						workBeanMap.setApprovedBy(session.getAttribute(CPSConstants.SFID).toString());
						workBeanMap.setTdSettStrStatus(workBeanMap.getWorkflowHistory().get(workBeanMap.getWorkflowHistory().size()-1).getStatus());
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.DEMAND) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CANCELDEMAND)) {
						workBeanMap.getDemandRequestDetails().getDemandItems().removeAll(Collections.singletonList(null));
						session.setAttribute(CPSConstants.DEMANDITEMSJSON, (JSONArray) JSONSerializer.toJSON(workBeanMap.getDemandRequestDetails().getDemandItems()));
						session.setAttribute(CPSConstants.DEMANDITEMS, workBeanMap.getDemandRequestDetails());
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.VOUCHER)) {
						session.setAttribute(CPSConstants.JSONVOUCHERLIST, (JSONArray) JSONSerializer.toJSON(workBeanMap.getVoucherRequestDetails().getVoucherItems()));
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.INVOICE) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CANCELIR)) {
						session.setAttribute(CPSConstants.JSONINVOICELIST, (JSONArray) JSONSerializer.toJSON(workBeanMap.getInvoiceRequestDetails().getIrItems()));
						session.setAttribute(CPSConstants.INVOICEITEMS, workBeanMap.getInvoiceRequestDetails());
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.NEWQUARTERREQ) && CPSUtils.compareStrings(workBeanMap.getLastStagePendingCheck(), CPSConstants.LPENDING)) {
						workBeanMap.setQuarterSubTypeList(workFlowMappingBusiness.getQuarterSubTypeList(workBeanMap));
					}else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CHANGEQUARTERREQ)) {
						//workBeanMap.setQuarterSubTypeList(workFlowMappingBusiness.getQuarterSubTypeList(workBeanMap));
						workBeanMap.setQuarterDetails(workFlowMappingBusiness.getQuarterDetails(workBeanMap));
						workBeanMap.setQuarterSubTypeList(workFlowMappingBusiness.getQuarterSubTypeList(workBeanMap));
					}else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.HIGHER_QUALIFICATION)) {
						workBeanMap.setHqRequestDTO(workFlowMappingBusiness.getHQRequestDetails(workBeanMap));
					}else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TUTIONFEEREQUESTYPE)) {
						workBeanMap.setMaxAmountPerOneChild(workFlowMappingBusiness.getTutionFeeMaxAmountPerOneChild());
						workBeanMap.setTutionFeeClaimList(workFlowMappingBusiness.getTfClaimRequestDetails(Integer.parseInt(workBeanMap.getRequestId()),workBeanMap));
						workBeanMap.setMainTutionFeeClaimList(workFlowMappingBusiness.getClaimListDetails(Integer.parseInt(workBeanMap.getRequestId())));
						workBeanMap.setTutionFeeOrgRoleId(tutionFeeBusiness.getTutionFeeOrgRoleId(session.getAttribute(CPSConstants.SFID).toString()));
						workBeanMap.setIpAddress(workFlowMappingBusiness.getIpAddress(workBeanMap.getRequestId()));
						session.setAttribute(CPSConstants.TUTIONFEELIMITLIST, (JSONArray) JSONSerializer.toJSON(tutionFeeBusiness.getTutionFeeLimitMasterDetails()));
					}else if(CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TELEPHONEBILLREQUESTYPE)){
						workBeanMap.setIpAddress(workFlowMappingBusiness.getIpAddress(workBeanMap.getRequestId()));
						workBeanMap.setTelephoneBillClaimList(workFlowMappingBusiness.getTelephoneBillClaimRequestDetails(Integer.parseInt(workBeanMap.getRequestId()), workBeanMap));
						workBeanMap.setMainTelephoneBillClaimList(workFlowMappingBusiness.getTelephoneBillClaimListDetails(Integer.parseInt(workBeanMap.getRequestId())));
						workBeanMap.setTeleMaxAmount(workFlowMappingBusiness.teleMaxAmountCondition(workBeanMap));
					    workBeanMap.setCashAssignEligibilitySfid(telePhoneBillBusiness.getCashAssignmentDetails(workBeanMap.getRequesterSfid()));
					    workFlowMappingBusiness.getFinanceAcceptedAmount(workBeanMap);
					    workFlowMappingBusiness.getCdaDetails(workBeanMap);
					    workFlowMappingBusiness.getApprovedBillNumbers(workBeanMap);
					    workFlowMappingBusiness.getRoleInstanceName(workBeanMap);
					}else if(CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDSETTLEMENTS) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDREIMBURSEMENT)){
						session.setAttribute(CPSConstants.DANEWDETAILSLIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getDaNewDetails()));
						//session.setAttribute(CPSConstants.DAOLDDETAILSLIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getDaOldDetails()));
						//session.setAttribute(CPSConstants.CITYTYPELIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getCityList()));
						session.setAttribute("workBeanMap", (net.sf.json.JSONObject) JSONSerializer.toJSON(workBeanMap));
						
						
						TadaRequestBean tadaRequestBean=new TadaRequestBean();
						tadaRequestBean.setSfid(workBeanMap.getRequesterSfid());
						tadaRequestBean.setRequestId(workBeanMap.getRequestId());
						tadaRequestBean.setJsonValue(workBeanMap.getJsonValue());
						tadaRequestBean=workFlowMappingBusiness.getPayDetails(tadaRequestBean);
						tadaRequestBean=workFlowMappingBusiness.getDaOnTourDetails(tadaRequestBean);        
						workBeanMap.setTadaDaPercentage(tadaRequestBean.getTadaDaPercentage());       //This is new line for TadaTdPerecentage
						workBeanMap.setDaValue(tadaRequestBean.getPayDaValue());                     //This is new line for TadaTdPerecentage
						//session.setAttribute("foodDetailsDayRep", (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getFoodDetails(tadaRequestBean)));
						//workBeanMap.setDaNewFoodDayRepList(workFlowMappingBusiness.getFoodDetails(tadaRequestBean));
						
						workBeanMap.setDaNewAccDayRepList(workFlowMappingBusiness.getDayAccDetails(tadaRequestBean));
						workBeanMap.setDaNewFoodDayRepList(workFlowMappingBusiness.getDayFoodDetails(tadaRequestBean));
						
					}else if(CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDBUILDUP) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDPROJECT)){
						session.setAttribute("workBeanMap", (net.sf.json.JSONObject) JSONSerializer.toJSON(workBeanMap));
					} 
					else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TRAININGNOMINATION)) {
						workBeanMap.setTrainingNominationDto(trainingRequestBusiness.getNominationDetails(Integer.parseInt(workBeanMap.getRequestId())));
						
					}else if(CPSUtils.compareStrings(CPSConstants.MTVEHICLEREQUESTYPE, workBeanMap.getRequestType())){
						
						/*workBeanMap.setMtRequester(workFlowMappingBusiness.getVehicleApplicantDetails(workBeanMap.getRequestId()));
						workBeanMap.setIpAddress(workBeanMap.getMtRequester().getIpAddress());
						workBeanMap.setArticleList(workFlowMappingBusiness.getArticleDetails(workBeanMap.getRequestId()));
						workBeanMap.setReturnArticleList(workFlowMappingBusiness.getReturnArticleDetails(workBeanMap.getRequestId()));
						VehicleDriverDTO vdDTO = workFlowMappingBusiness.getVehicleDriverList(workBeanMap.getRequestId());
						workBeanMap.setVehicleDriverFreeList(vdDTO.getTravelVehicleList());
						workBeanMap.setVehicleDriverBusyList(vdDTO.getReturnTravelList());
						session.setAttribute("ReturnVehicleJSON", (JSONArray)JSONSerializer.toJSON(vdDTO.getReturnTravelList()));
						//workBeanMap.setVehicleDriverFreeList( workFlowMappingBusiness.getVehicleDriverList(workBeanMap.getRequestId()));
						//workBeanMap.setVehicleDriverBusyList(workFlowMappingBusiness.getAllBusyVehicles(workBeanMap.getRequestId()));
						workBeanMap.setHiredVehicleList(workFlowMappingBusiness.getHiredVehicleList());
						session.setAttribute(CPSConstants.MTVEHICLEHIREDJSON, (JSONArray)JSONSerializer.toJSON(workBeanMap.getHiredVehicleList()));
						
						session.setAttribute(CPSConstants.MTVEHICLEDRIVERJSON, (JSONArray)JSONSerializer.toJSON(workFlowMappingBusiness.getVehicleDriverBusyList()));
						session.setAttribute(CPSConstants.MTVEHICLENOJSON, (JSONArray)JSONSerializer.toJSON(workFlowMappingBusiness.getVehicleNoList()));*/
						workBeanMap.setMtRequester(workFlowMappingBusiness.getVehicleApplicantDetails(workBeanMap.getRequestId()));
						workBeanMap.setIpAddress(workBeanMap.getMtRequester().getIpAddress());
						session.setAttribute("MTRequestJSON", JSONSerializer.toJSON(workBeanMap.getMtRequester()));
					}
					else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CANCELTRAININGNOMINATION)) {
						workBeanMap.setTrainingNominationDto(trainingRequestBusiness.getNominationCancelDetails(Integer.parseInt(workBeanMap.getRequestId())));
						
					}
					

					session.setAttribute(CPSConstants.RETURN, CPSConstants.REQUESTDETAILSPAGE);

					mav = new ModelAndView(CPSConstants.REQUESTDETAILSPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
					mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
					mav.addObject(CPSConstants.REQUESTHISTORYLIST, workBeanMap.getWorkflowHistory());
				}else if(CPSUtils.compareStrings(workBeanMap.getRequestfailure(), CPSConstants.REQUESTFAILURE) || CPSUtils.compareStrings(workBeanMap.getRequestnotexist(), CPSConstants.REQUESTNOTEXIST)){
						 String msg1 =workBeanMap.getRequestnotexist();
					      String msg = workBeanMap.getRequestfailure(); 
					      workBeanMap = workFlowMappingBusiness.pisHomeDetails((session.getAttribute(CPSConstants.SFID)).toString(),workBeanMap.getType());
					   workBeanMap.setRequestfailure(msg);
					   workBeanMap.setRequestnotexist(msg1);
					   
					   session.setAttribute("holdRequests", workBeanMap);
					   mav = new ModelAndView(CPSConstants.WELCOMEPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
					   mav.addObject("myRequests", workBeanMap.getMyRequests());
					   mav.addObject("myAlerts", workBeanMap.getMyAlerts());
					   
					}
			} else if (CPSUtils.compareStrings(CPSConstants.LTCREQUESTDETAILS, workBeanMap.getParam())) {
				//added by bkr 24/05/2016 ltcwater
				//workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				
				if(CPSUtils.compareStrings(workBeanMap.getRequestsuccess(), CPSConstants.REQUESTSUCCESS)){
					
					workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
					
					workBeanMap.setRequestStage(workFlowMappingBusiness.getPresentRequestStage(workBeanMap.getRequestId(), workBeanMap.getSfid()));
					workBeanMap.setCheckStage(workFlowMappingBusiness.getCheckStage(workBeanMap.getRequestId(), workBeanMap.getRequestType()));
					
					workBeanMap.setLastStagePendingCheck(workFlowMappingBusiness.getLastStagePendingCheck(workBeanMap.getRequestId(),workBeanMap.getSfid()));
					
				}
				session.setAttribute(CPSConstants.RETURN, CPSConstants.REQUESTDETAILSPAGE);

				mav = new ModelAndView(CPSConstants.REQUESTDETAILSPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, workBeanMap.getWorkflowHistory());
				
			}  else if (CPSUtils.compareStrings(CPSConstants.ERPLEAVEAPPLYDETAILS, workBeanMap.getParam())) {
				
				
				
				//added by bkr 11/06/2016 erp leave
				//workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				
				if(CPSUtils.compareStrings(workBeanMap.getRequestsuccess(), CPSConstants.REQUESTSUCCESS)){
					
					//imp
					workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
					
					workBeanMap.setRequestStage(workFlowMappingBusiness.getPresentRequestStage(workBeanMap.getRequestId(), workBeanMap.getSfid()));
					workBeanMap.setCheckStage(workFlowMappingBusiness.getCheckStage(workBeanMap.getRequestId(), workBeanMap.getRequestType()));
					
					workBeanMap.setLastStagePendingCheck(workFlowMappingBusiness.getLastStagePendingCheck(workBeanMap.getRequestId(),workBeanMap.getSfid()));
					
				}
				session.setAttribute(CPSConstants.RETURN, CPSConstants.REQUESTDETAILSPAGE);

				mav = new ModelAndView(CPSConstants.REQUESTDETAILSPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, workBeanMap.getWorkflowHistory());
				
			}  else if (CPSUtils.compareStrings(CPSConstants.ERPLEAVEAPPLYDETAILS, workBeanMap.getParam())) {
				
				//added by bkr 21/06/2016 erp leave cancel
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				if(CPSUtils.compareStrings(workBeanMap.getRequestsuccess(), CPSConstants.REQUESTSUCCESS)){
					//imp
					workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
					
					workBeanMap.setRequestStage(workFlowMappingBusiness.getPresentRequestStage(workBeanMap.getRequestId(), workBeanMap.getSfid()));
					workBeanMap.setCheckStage(workFlowMappingBusiness.getCheckStage(workBeanMap.getRequestId(), workBeanMap.getRequestType()));
					
					workBeanMap.setLastStagePendingCheck(workFlowMappingBusiness.getLastStagePendingCheck(workBeanMap.getRequestId(),workBeanMap.getSfid()));
					
				}
				session.setAttribute(CPSConstants.RETURN, CPSConstants.REQUESTDETAILSPAGE);

				mav = new ModelAndView(CPSConstants.REQUESTDETAILSPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, workBeanMap.getWorkflowHistory());
				
			}
			
			else if (CPSUtils.compareStrings(CPSConstants.REQUESTWATERDETAILS, workBeanMap.getParam())) {
				

				log.debug("WorkFlowMappingController --> onSubmit --> param=requestDetails");
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				
				workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				
				/*if(workBeanMap.getRequestfailure().equals(CPSConstants.FAILED)){
					mav = new ModelAndView(CPSConstants.REQUESTDETAILSPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
		 			mav.addObject(CPSConstants.MESSAGE, workBeanMap.getRequestfailure());
				}*/
				if(CPSUtils.compareStrings(workBeanMap.getRequestsuccess(), CPSConstants.REQUESTSUCCESS)){
					workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
					if(!CPSUtils.isNullOrEmpty(workBeanMap.getDopartdateList())){
					request.setAttribute("doPartDatewiseList",workBeanMap.getDopartdateList());}
					
					workBeanMap.setRequestStage(workFlowMappingBusiness.getPresentRequestStage(workBeanMap.getRequestId(), workBeanMap.getSfid()));
					workBeanMap.setCheckStage(workFlowMappingBusiness.getCheckStage(workBeanMap.getRequestId(), workBeanMap.getRequestType()));
					
					workBeanMap.setLastStagePendingCheck(workFlowMappingBusiness.getLastStagePendingCheck(workBeanMap.getRequestId(),workBeanMap.getSfid()));
					
					if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CGHS)) {
						workBeanMap.setCghsDelegateList(workFlowMappingBusiness.cghsDelegateList());
						workBeanMap=workFlowMappingBusiness.cghsreque(workBeanMap);
						workBeanMap.setApprovedBy(session.getAttribute(CPSConstants.SFID).toString());
						workBeanMap.setTdSettStrStatus(workBeanMap.getWorkflowHistory().get(workBeanMap.getWorkflowHistory().size()-1).getStatus());
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.DEMAND) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CANCELDEMAND)) {
						workBeanMap.getDemandRequestDetails().getDemandItems().removeAll(Collections.singletonList(null));
						session.setAttribute(CPSConstants.DEMANDITEMSJSON, (JSONArray) JSONSerializer.toJSON(workBeanMap.getDemandRequestDetails().getDemandItems()));
						session.setAttribute(CPSConstants.DEMANDITEMS, workBeanMap.getDemandRequestDetails());
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.VOUCHER)) {
						session.setAttribute(CPSConstants.JSONVOUCHERLIST, (JSONArray) JSONSerializer.toJSON(workBeanMap.getVoucherRequestDetails().getVoucherItems()));
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.INVOICE) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CANCELIR)) {
						session.setAttribute(CPSConstants.JSONINVOICELIST, (JSONArray) JSONSerializer.toJSON(workBeanMap.getInvoiceRequestDetails().getIrItems()));
						session.setAttribute(CPSConstants.INVOICEITEMS, workBeanMap.getInvoiceRequestDetails());
					} else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.NEWQUARTERREQ) && CPSUtils.compareStrings(workBeanMap.getLastStagePendingCheck(), CPSConstants.LPENDING)) {
						workBeanMap.setQuarterSubTypeList(workFlowMappingBusiness.getQuarterSubTypeList(workBeanMap));
					}else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CHANGEQUARTERREQ)) {
						//workBeanMap.setQuarterSubTypeList(workFlowMappingBusiness.getQuarterSubTypeList(workBeanMap));
						workBeanMap.setQuarterDetails(workFlowMappingBusiness.getQuarterDetails(workBeanMap));
						workBeanMap.setQuarterSubTypeList(workFlowMappingBusiness.getQuarterSubTypeList(workBeanMap));
					}else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.HIGHER_QUALIFICATION)) {
						workBeanMap.setHqRequestDTO(workFlowMappingBusiness.getHQRequestDetails(workBeanMap));
					}else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TUTIONFEEREQUESTYPE)) {
						workBeanMap.setMaxAmountPerOneChild(workFlowMappingBusiness.getTutionFeeMaxAmountPerOneChild());
						workBeanMap.setTutionFeeClaimList(workFlowMappingBusiness.getTfClaimRequestDetails(Integer.parseInt(workBeanMap.getRequestId()),workBeanMap));
						workBeanMap.setMainTutionFeeClaimList(workFlowMappingBusiness.getClaimListDetails(Integer.parseInt(workBeanMap.getRequestId())));
						workBeanMap.setTutionFeeOrgRoleId(tutionFeeBusiness.getTutionFeeOrgRoleId(session.getAttribute(CPSConstants.SFID).toString()));
						workBeanMap.setIpAddress(workFlowMappingBusiness.getIpAddress(workBeanMap.getRequestId()));
						session.setAttribute(CPSConstants.TUTIONFEELIMITLIST, (JSONArray) JSONSerializer.toJSON(tutionFeeBusiness.getTutionFeeLimitMasterDetails()));
					}else if(CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TELEPHONEBILLREQUESTYPE)){
						workBeanMap.setIpAddress(workFlowMappingBusiness.getIpAddress(workBeanMap.getRequestId()));
						workBeanMap.setTelephoneBillClaimList(workFlowMappingBusiness.getTelephoneBillClaimRequestDetails(Integer.parseInt(workBeanMap.getRequestId()), workBeanMap));
						workBeanMap.setMainTelephoneBillClaimList(workFlowMappingBusiness.getTelephoneBillClaimListDetails(Integer.parseInt(workBeanMap.getRequestId())));
						workBeanMap.setTeleMaxAmount(workFlowMappingBusiness.teleMaxAmountCondition(workBeanMap));
					    workBeanMap.setCashAssignEligibilitySfid(telePhoneBillBusiness.getCashAssignmentDetails(workBeanMap.getRequesterSfid()));
					    workFlowMappingBusiness.getFinanceAcceptedAmount(workBeanMap);
					    workFlowMappingBusiness.getCdaDetails(workBeanMap);
					    workFlowMappingBusiness.getApprovedBillNumbers(workBeanMap);
					    workFlowMappingBusiness.getRoleInstanceName(workBeanMap);
					}else if(CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDSETTLEMENTS) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDREIMBURSEMENT)){
						session.setAttribute(CPSConstants.DANEWDETAILSLIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getDaNewDetails()));
						//session.setAttribute(CPSConstants.DAOLDDETAILSLIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getDaOldDetails()));
						//session.setAttribute(CPSConstants.CITYTYPELIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getCityList()));
						session.setAttribute("workBeanMap", (net.sf.json.JSONObject) JSONSerializer.toJSON(workBeanMap));
						
						
						TadaRequestBean tadaRequestBean=new TadaRequestBean();
						tadaRequestBean.setSfid(workBeanMap.getRequesterSfid());
						tadaRequestBean.setRequestId(workBeanMap.getRequestId());
						tadaRequestBean.setJsonValue(workBeanMap.getJsonValue());
						tadaRequestBean=workFlowMappingBusiness.getPayDetails(tadaRequestBean);
						tadaRequestBean=workFlowMappingBusiness.getDaOnTourDetails(tadaRequestBean);        
						workBeanMap.setTadaDaPercentage(tadaRequestBean.getTadaDaPercentage());       //This is new line for TadaTdPerecentage
						workBeanMap.setDaValue(tadaRequestBean.getPayDaValue());                     //This is new line for TadaTdPerecentage
						//session.setAttribute("foodDetailsDayRep", (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getFoodDetails(tadaRequestBean)));
						//workBeanMap.setDaNewFoodDayRepList(workFlowMappingBusiness.getFoodDetails(tadaRequestBean));
						
						workBeanMap.setDaNewAccDayRepList(workFlowMappingBusiness.getDayAccDetails(tadaRequestBean));
						workBeanMap.setDaNewFoodDayRepList(workFlowMappingBusiness.getDayFoodDetails(tadaRequestBean));
						
					}else if(CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDBUILDUP) || CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TADATDPROJECT)){
						session.setAttribute("workBeanMap", (net.sf.json.JSONObject) JSONSerializer.toJSON(workBeanMap));
					} 
					else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.TRAININGNOMINATION)) {
						workBeanMap.setTrainingNominationDto(trainingRequestBusiness.getNominationDetails(Integer.parseInt(workBeanMap.getRequestId())));
						
					}else if(CPSUtils.compareStrings(CPSConstants.MTVEHICLEREQUESTYPE, workBeanMap.getRequestType())){
						
						/*workBeanMap.setMtRequester(workFlowMappingBusiness.getVehicleApplicantDetails(workBeanMap.getRequestId()));
						workBeanMap.setIpAddress(workBeanMap.getMtRequester().getIpAddress());
						workBeanMap.setArticleList(workFlowMappingBusiness.getArticleDetails(workBeanMap.getRequestId()));
						workBeanMap.setReturnArticleList(workFlowMappingBusiness.getReturnArticleDetails(workBeanMap.getRequestId()));
						VehicleDriverDTO vdDTO = workFlowMappingBusiness.getVehicleDriverList(workBeanMap.getRequestId());
						workBeanMap.setVehicleDriverFreeList(vdDTO.getTravelVehicleList());
						workBeanMap.setVehicleDriverBusyList(vdDTO.getReturnTravelList());
						session.setAttribute("ReturnVehicleJSON", (JSONArray)JSONSerializer.toJSON(vdDTO.getReturnTravelList()));
						//workBeanMap.setVehicleDriverFreeList( workFlowMappingBusiness.getVehicleDriverList(workBeanMap.getRequestId()));
						//workBeanMap.setVehicleDriverBusyList(workFlowMappingBusiness.getAllBusyVehicles(workBeanMap.getRequestId()));
						workBeanMap.setHiredVehicleList(workFlowMappingBusiness.getHiredVehicleList());
						session.setAttribute(CPSConstants.MTVEHICLEHIREDJSON, (JSONArray)JSONSerializer.toJSON(workBeanMap.getHiredVehicleList()));
						
						session.setAttribute(CPSConstants.MTVEHICLEDRIVERJSON, (JSONArray)JSONSerializer.toJSON(workFlowMappingBusiness.getVehicleDriverBusyList()));
						session.setAttribute(CPSConstants.MTVEHICLENOJSON, (JSONArray)JSONSerializer.toJSON(workFlowMappingBusiness.getVehicleNoList()));*/
						workBeanMap.setMtRequester(workFlowMappingBusiness.getVehicleApplicantDetails(workBeanMap.getRequestId()));
						workBeanMap.setIpAddress(workBeanMap.getMtRequester().getIpAddress());
						session.setAttribute("MTRequestJSON", JSONSerializer.toJSON(workBeanMap.getMtRequester()));
					}
					else if (CPSUtils.compareStrings(workBeanMap.getRequestType(), CPSConstants.CANCELTRAININGNOMINATION)) {
						workBeanMap.setTrainingNominationDto(trainingRequestBusiness.getNominationCancelDetails(Integer.parseInt(workBeanMap.getRequestId())));
						
					}
					

					session.setAttribute(CPSConstants.RETURN, CPSConstants.REQUESTDETAILSPAGE);

					mav = new ModelAndView(CPSConstants.REQUESTDETAILSPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
					mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
					mav.addObject(CPSConstants.REQUESTHISTORYLIST, workBeanMap.getWorkflowHistory());
				}else if(CPSUtils.compareStrings(workBeanMap.getRequestfailure(), CPSConstants.REQUESTFAILURE) || CPSUtils.compareStrings(workBeanMap.getRequestnotexist(), CPSConstants.REQUESTNOTEXIST)){
						 String msg1 =workBeanMap.getRequestnotexist();
					      String msg = workBeanMap.getRequestfailure(); 
					      workBeanMap = workFlowMappingBusiness.pisHomeDetails((session.getAttribute(CPSConstants.SFID)).toString(),workBeanMap.getType());
					   workBeanMap.setRequestfailure(msg);
					   workBeanMap.setRequestnotexist(msg1);
					   
					   session.setAttribute("holdRequests", workBeanMap);
					   mav = new ModelAndView(CPSConstants.WELCOMEPAGE, CPSConstants.WORKFLOWMAP, workBeanMap);
					   mav.addObject("myRequests", workBeanMap.getMyRequests());
					   mav.addObject("myAlerts", workBeanMap.getMyAlerts());
					   
					}
			
				
				
			}
			else if (CPSUtils.compareStrings(CPSConstants.MORE, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=more");
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap.setRequestList(workFlowMappingBusiness.getDashBoardList(workBeanMap));
				session.setAttribute(CPSConstants.REQUESTTYPELIST, workFlowMappingBusiness.getRequestTypes());
				session.setAttribute(CPSConstants.RESULT, CPSConstants.HOME);
                if(CPSUtils.compareStrings(CPSConstants.HOLD, workBeanMap.getType())){
                	 mav = new ModelAndView(CPSConstants.DASHBOARDREQUEST, CPSConstants.WORKFLOWMAP, workBeanMap);
                 }else{
                	 mav = new ModelAndView(CPSConstants.DASHBOARDREQUEST, CPSConstants.WORKFLOWMAP, workBeanMap); 
                 }
				
				session.setAttribute(CPSConstants.DASHBORDLIST, workBeanMap.getRequestList());
				session.setAttribute(CPSConstants.TYPE, workBeanMap.getType());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DASHBOARDREQUESTLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.MOREALERT, workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=morealert");
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			     workBeanMap.setAlertList(workFlowMappingBusiness.getAlertList(workBeanMap));
				session.setAttribute(CPSConstants.ALERTTYPELIST, workFlowMappingBusiness.getAlertTypeList());
                
                 mav = new ModelAndView(CPSConstants.MYALERTREQUESTS, CPSConstants.WORKFLOWMAP, workBeanMap);
                 
				
				session.setAttribute(CPSConstants.ALERTLIST, workBeanMap.getAlertList());
				session.setAttribute(CPSConstants.TYPE, workBeanMap.getType());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MYALERTLIST);
			}


			else if (CPSUtils.compareStrings(CPSConstants.SEARCH, workBeanMap.getParam())) {
				log.debug("::WorkFlowController --> onSubmit --> param=search");
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap.setRequestList(workFlowMappingBusiness.getDashBoardList(workBeanMap));
				session.setAttribute(CPSConstants.DASHBORDLIST, workBeanMap.getRequestList());
				mav = new ModelAndView(CPSConstants.DASHBOARDREQUESTLIST, CPSConstants.WORKFLOWMAP, workBeanMap);
			}
			else if (CPSUtils.compareStrings(CPSConstants.SEARCHALERT, workBeanMap.getParam())) {
				log.debug("::WorkFlowController --> onSubmit --> param=searchalert");
				workBeanMap.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				workBeanMap.setAlertList(workFlowMappingBusiness.getAlertList1(workBeanMap));
         		session.setAttribute(CPSConstants.ALERTLIST, workBeanMap.getAlertList());

				mav = new ModelAndView(CPSConstants.MYALERTLIST, CPSConstants.WORKFLOWMAP, workBeanMap); 
			}else if (CPSUtils.compareStrings(CPSConstants.REQUESTSPECIFICDETAILS, workBeanMap.getParam())) {
				log.debug("::WorkFlowController --> onSubmit --> param=reqSpeDet");
				workBeanMap.setSfid((session.getAttribute(CPSConstants.SFID)).toString());
				
				
				workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
				workBeanMap.setLastStagePendingCheck(workFlowMappingBusiness.getLastStagePendingCheck(workBeanMap.getRequestId(),workBeanMap.getSfid()));
				if (CPSUtils.compareStrings(CPSConstants.LOAN, workBeanMap.getRequestType())) {
					viewName = CPSConstants.LOANREQUESTDETAILSPAGE;
				}
				mav = new ModelAndView(viewName, CPSConstants.WORKFLOWMAP, workBeanMap);
			} else if (CPSUtils.compareStrings("dopart", workBeanMap.getParam())) {
				log.debug("WorkFlowController --> Ajax Call --> param=dopart");
				workBeanMap.setDoPartList(workFlowMappingBusiness.getDopartNumber(workBeanMap.getDoPartDate(), workBeanMap.getGazettedType()));
				mav = new ModelAndView(CPSConstants.DOPARTNUMBER, CPSConstants.WORKFLOW, workBeanMap);
				mav.addObject(CPSConstants.DOPARTNUMBER, workBeanMap.getDoPartList());
			} else if (CPSUtils.compareStrings("alertDetails", workBeanMap.getParam())) {
				log.debug("WorkFlowController --> onSubmit --> param=alertDetails");
				workBeanMap.setAlertDetails(workFlowMappingBusiness.getAlertDetails(workBeanMap));
				mav = new ModelAndView(CPSConstants.ALERTDETAILS, CPSConstants.WORKFLOW, workBeanMap);
				if(CPSUtils.compareStrings(String.valueOf(workBeanMap.getAlertDetails().getAlertDetails().getId()),CPSConstants.TRAININGALERTID) || CPSUtils.compareStrings(String.valueOf(workBeanMap.getAlertDetails().getAlertDetails().getId()),CPSConstants.TRAININGFINALSELECTIONALERTID))
				{
					workBeanMap.setParam("HRDGAlertDetails");
					if (CPSUtils.compareStrings("HRDGAlertDetails", workBeanMap.getParam())) {
						log.debug("WorkFlowController --> onSubmit --> param=HRDGAlertDetails");
						workBeanMap.setTrainingCirculationDto(trainingCirculationBusiness.getAlertDetails(workBeanMap));
					mav = new ModelAndView(CPSConstants.ALERTDETAILS, CPSConstants.WORKFLOW, workBeanMap);
					}
				}
				
			}
			else if (CPSUtils.compareStrings("submitAlert", workBeanMap.getParam())) {
				log.debug("WorkFlowController --> Ajax Call --> param=submitAlert");
				workBeanMap.setIpAddress(request.getRemoteAddr());
				workBeanMap.setMessage(workFlowMappingBusiness.submitAlertResponse(workBeanMap));
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.WORKFLOW, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
			} else if(CPSUtils.compareStrings(CPSConstants.EDITINSTALLMENTS ,workBeanMap.getParam())){
				log.debug("RequestWorkFlowController --> execute --> param=editInstallments");
				workBeanMap = workFlowMappingBusiness.editInstallmentDetails(workBeanMap); 
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.WORKFLOW, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
			} else if(CPSUtils.compareStrings(workBeanMap.getParam(), "getFoodDetails")){
				//workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				//workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
			  //workBeanMap.setCheckStage(workFlowMappingBusiness.getCheckStage(workBeanMap.getRequestId(), workBeanMap.getRequestType()));
				 // workBeanMap.setSfid((session.getAttribute(CPSConstants.SFID)).toString());
				  session.setAttribute(CPSConstants.DANEWDETAILSLIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getDaNewDetails()));
				//session.setAttribute(CPSConstants.DAOLDDETAILSLIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getDaOldDetails()));
				//session.setAttribute(CPSConstants.CITYTYPELIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getCityList()));
				session.setAttribute("workBeanMap", (net.sf.json.JSONObject) JSONSerializer.toJSON(workBeanMap));
				
				
				TadaRequestBean tadaRequestBean=new TadaRequestBean();
				tadaRequestBean.setSfid(workBeanMap.getRequesterSfid());
				tadaRequestBean.setRequestId(workBeanMap.getRequestId());
				tadaRequestBean.setJsonValue(workBeanMap.getJsonValue());
				tadaRequestBean=workFlowMappingBusiness.getPayDetails(tadaRequestBean);
				//session.setAttribute("foodDetailsDayRep", (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getFoodDetails(tadaRequestBean)));
				workBeanMap.setDaNewFoodDayRepList(workFlowMappingBusiness.getFoodDetails(tadaRequestBean));
				//workBeanMap.setDaNewAccDayRepList(workFlowMappingBusiness.getAccDetails(tadaRequestBean));
				workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
				workBeanMap.setRequestType(CPSConstants.TADATDSETTLEMENTS);
				session.setAttribute(CPSConstants.RETURN, "TadaTdFoodDetailsDayRep");
				mav = new ModelAndView("TadaTdFoodDetailsDayRep", CPSConstants.WORKFLOWMAP, workBeanMap);
				//mav = new ModelAndView("changedFoodDetails", CPSConstants.WORKFLOWMAP, workBeanMap);
				
			}else if(CPSUtils.compareStrings(workBeanMap.getParam(), "getAccDetails")){
				  session.setAttribute(CPSConstants.DANEWDETAILSLIST, (JSONArray) JSONSerializer.toJSON(workFlowMappingBusiness.getDaNewDetails()));
				  session.setAttribute("workBeanMap", (net.sf.json.JSONObject) JSONSerializer.toJSON(workBeanMap));
				
				
				TadaRequestBean tadaRequestBean=new TadaRequestBean();
				tadaRequestBean.setSfid(workBeanMap.getRequesterSfid());
				tadaRequestBean.setRequestId(workBeanMap.getRequestId());
				tadaRequestBean.setJsonValue(workBeanMap.getJsonValue());
				tadaRequestBean=workFlowMappingBusiness.getPayDetails(tadaRequestBean);
				workBeanMap.setDaNewAccDayRepList(workFlowMappingBusiness.getAccDetails(tadaRequestBean));
				workBeanMap = workFlowMappingBusiness.getRequestDetails(workBeanMap);
				workBeanMap = workFlowMappingBusiness.getChangedDetails(workBeanMap);
				workBeanMap.setRequestType(CPSConstants.TADATDSETTLEMENTS);
				session.setAttribute(CPSConstants.RETURN, "TadaTdAcomidationDetailsDayRep");
				mav = new ModelAndView("TadaTdAcomidationDetailsDayRep", CPSConstants.WORKFLOWMAP, workBeanMap);
			}
	         //end request id search
			//cghs upload start
			else if(CPSUtils.compareStrings("manageUpload", workBeanMap.getParam())){
				
				/*ArrayList<Integer> list = new ArrayList<Integer>();
			         list  =  (ArrayList<Integer>)session.getAttribute("fileID");
				int i = list.get(0);
				System.out.println(i);
				
				Map<String, Integer> fileIDMap= new HashMap<String, Integer>();
				
				if(!CPSUtils.isNullOrEmpty(session.getAttribute("cghsCardFile"))){
				workBeanMap.setCghsCardFile(session.getAttribute("cghsCardFile").toString());
				}
				if(workBeanMap.getRequestType().equals("normal")|| workBeanMap.getRequestType() == "normal"){
					if(!CPSUtils.isNullOrEmpty(session.getAttribute("prescriptionFile"))){
					workBeanMap.setPrescriptionFile(session.getAttribute("prescriptionFile").toString());
					}
				}
				else if(workBeanMap.getRequestType().equals("advance")|| workBeanMap.getRequestType() == "advance"){
					if(!CPSUtils.isNullOrEmpty(session.getAttribute("estimationFile"))){
					workBeanMap.setEstimationFile(session.getAttribute("estimationFile").toString());
					}
				}
				else if(workBeanMap.getRequestType().equals("reimbursement")||workBeanMap.getRequestType() == "reimbursement"){
					 if(!CPSUtils.isNullOrEmpty(session.getAttribute("prescriptionFile"))){
					workBeanMap.setPrescriptionFile(session.getAttribute("prescriptionFile").toString());
					}
					 if(!CPSUtils.isNullOrEmpty(session.getAttribute("medicalBillsFile"))){
					workBeanMap.setMedicalBillsFile(session.getAttribute("medicalBillsFile").toString());	
				   }
				}
				else if(workBeanMap.getRequestType().equals("emergency")||workBeanMap.getRequestType() == "emergency"){
				  if(!CPSUtils.isNullOrEmpty(session.getAttribute("medicalBillsFile"))){
						workBeanMap.setMedicalBillsFile(session.getAttribute("medicalBillsFile").toString());
					}
					if(!CPSUtils.isNullOrEmpty(session.getAttribute("dischargeSummeryFile"))){
					workBeanMap.setDischargeSummeryFile(session.getAttribute("dischargeSummeryFile").toString());
				 }
				}*/
				workFlowMappingBusiness.manageCghsUploads(workBeanMap);
				workBeanMap.setMessage(CPSConstants.SUCCESS);
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.WORKFLOWMAP, workBeanMap);
				mav.addObject(CPSConstants.MESSAGE, workBeanMap.getMessage());
			}//cghs upload end
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		//workBeanMap.setRequestType("TADA WATER");
		return mav;
	}
}

