package com.callippus.web.tada.controller.request;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanUtils;
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

import com.callippus.web.business.requestprocess.TadaRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.business.request.TadaRequestBusiness;
import com.callippus.web.tada.dto.TadaRequestProcessBean;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/tadaApprovalRequest.htm")
@SessionAttributes
public class TadaRequestController implements Serializable{
	private static Log log = LogFactory.getLog(TadaRequestController.class);
	@Autowired
	TadaRequestBusiness tadaRequestBusiness;
	
	
	@Autowired
	private TadaRequestProcess tadaRequestProcess;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute TadaRequestBean tadaRequestBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try{
			ErpAction.userChecks(request);
			session = request.getSession(true);
			tadaRequestBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(tadaRequestBean.getParam())) {
				tadaRequestBean.setParam(CPSConstants.TADAAPPROVALREQUEST);
			} else if (CPSUtils.compareStrings(tadaRequestBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			} else if (CPSUtils.compareStrings(tadaRequestBean.getParam(), "advancePaging")) {
				viewName = CPSConstants.TADATDADVLIST;
			} else if(CPSUtils.compareStrings(tadaRequestBean.getParam(), "settlementPaging")){
				viewName = CPSConstants.TADATDSETTLEMENTLIST;
			} else if(CPSUtils.compareStrings(tadaRequestBean.getParam(), "reimbursementPaging")){
				viewName = CPSConstants.TADATDREIMBURSEMENTLIST;
			}else if (CPSUtils.compareStrings(tadaRequestBean.getParam(), "cdaAdvancePaging")) {       // new condition for cdaAdvancePaging
				viewName = "CDATadaTdAdvList";
			} else if(CPSUtils.compareStrings(tadaRequestBean.getParam(), "cdaSettlementPaging")){
				viewName = "CdaTadaTdSettlementList";
			}
			else if(CPSUtils.compareStrings(tadaRequestBean.getParam(), "cdareimbursementPaging")){   //new condition for cdareimbursementPaging
				viewName = "CdaReimbursementList";
			}
			
			if(CPSUtils.compareStrings(CPSConstants.TADAAPPROVALREQUEST, tadaRequestBean.getParam())){
				tadaRequestBean=tadaRequestBusiness.getEmpDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getDeptDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getPayDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getTadaRequestTimeBoundDetails(tadaRequestBean);    //Tada Request Time Bound of past and fuure
				if(!tadaRequestBean.getStatusMsg().equals("failure")){
				tadaRequestBean=tadaRequestBusiness.getConveyanceMode(tadaRequestBean);
				session.setAttribute("travelTypeMapDetailsJSON",(JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getTravelTypeMapList(tadaRequestBean)));
				session.setAttribute("entitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getEntitleTypeList(tadaRequestBean)));
				session.setAttribute("taEntitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getEntitlementClassList(tadaRequestBean)));
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getTravelTypeList(tadaRequestBean)));
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, tadaRequestBusiness.getEmpDetails(tadaRequestBean));
				session.setAttribute("empDetailsJSON", (JSONObject) JSONSerializer.toJSON(tadaRequestBusiness.getEmpDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.CITYTYPELIST, tadaRequestProcess.getCityList(tadaRequestBean));
				session.setAttribute("cityList", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getCityList(tadaRequestBean)));
				request.setAttribute("TadaRequestBean", (JSONObject) JSONSerializer.toJSON(tadaRequestBean));
				session.setAttribute("tadaProjDirNamesList", tadaRequestBusiness.getPrjDirNamesList(tadaRequestBean));
				session.setAttribute("entitleExemptionList", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getEntitleExemptionList()));
				}
				viewName=CPSConstants.TADAAPPROVALREQUESTHOME;
			} else if(CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, tadaRequestBean.getParam())){
				log.debug("TadaRequestController --> onSubmit --> param=submitRequest");
				viewName = CPSConstants.REQUESTRESULTPAGE;
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, tadaRequestBusiness.getEmpDetails(tadaRequestBean));
				TadaRequestProcessBean tadaRequestProcessBean = new TadaRequestProcessBean();
				BeanUtils.copyProperties(tadaRequestProcessBean, tadaRequestBean);
				tadaRequestProcessBean.setSfID((String) session.getAttribute(CPSConstants.SFID));
				tadaRequestProcessBean.setIpAddress(request.getRemoteAddr());
				tadaRequestProcessBean.setSession(request.getSession(true));
			    
				tadaRequestBean.setResult(tadaRequestProcess.initWorkflow(tadaRequestProcessBean));
				tadaRequestBean.setRequestID(tadaRequestProcessBean.getRequestID());
			session.setAttribute("tadaAprovalRequestID",tadaRequestBean.getRequestID());
				
			} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCEREQUEST, tadaRequestBean.getParam())){
				tadaRequestBean=tadaRequestBusiness.getEmpDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getPayDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getConveyanceMode(tadaRequestBean);
				session.setAttribute("travelTypeMapDetailsJSON",(JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getTravelTypeMapList(tadaRequestBean)));
				session.setAttribute("entitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getEntitleTypeList(tadaRequestBean)));
				session.setAttribute("taEntitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getEntitlementClassList(tadaRequestBean)));
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getTravelTypeList(tadaRequestBean)));
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, tadaRequestBusiness.getEmpDetails(tadaRequestBean));
				if(!CPSUtils.isNullOrEmpty(tadaRequestBusiness.getDaNewDetails(tadaRequestBean))){
					session.setAttribute("tadaDaNewDetails", (JSONObject) JSONSerializer.toJSON(tadaRequestBusiness.getDaNewDetails(tadaRequestBean)));
				}
				request.setAttribute("TadaRequestBean", (JSONObject) JSONSerializer.toJSON(tadaRequestBean));
				if(tadaRequestProcess.getTdRequestDetails(tadaRequestBean)!=null){
				   session.setAttribute("tdRequestDetails", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdRequestDetails(tadaRequestBean)));
				}
				session.setAttribute("tdReqJourneyDetails", tadaRequestProcess.getTdReqJourneyDetails(tadaRequestBean));
				session.setAttribute("projList", tadaRequestProcess.getProjList(tadaRequestBean));
				viewName=CPSConstants.TADATDADVANCE;
			} else if(CPSUtils.compareStrings(CPSConstants.TADATDCLAIM, tadaRequestBean.getParam())) {
				viewName=CPSConstants.TADATDAPPROVALDETAILS;
				if(tadaRequestProcess.getTdTxnDetails(tadaRequestBean)!=null){
					session.setAttribute(CPSConstants.TADATDTXNDETAILS, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdTxnDetails(tadaRequestBean)));
				}
				if(tadaRequestProcess.getTdAdvTxnDetails(tadaRequestBean)!=null){
					session.setAttribute(CPSConstants.TADATDADVTXNDETAILS, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdAdvTxnDetails(tadaRequestBean)));
				}
				if(tadaRequestProcess.getTdSettTxnDetails(tadaRequestBean)!=null){
					session.setAttribute(CPSConstants.TADATDSETTTXNDETAILS, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdSettTxnDetails(tadaRequestBean)));
				}
				if(tadaRequestProcess.getTdReimTxnDetails(tadaRequestBean)!=null){
					session.setAttribute(CPSConstants.TADATDREIMTXNDETAILS, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdReimTxnDetails(tadaRequestBean)));
				}
				session.setAttribute("cdaDetailsList", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdCdaDetails(tadaRequestBean)));
				if(tadaRequestProcess.getTadaTdDetails(tadaRequestBean)!=null){
					session.setAttribute("tadaTdDetails", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTadaTdDetails(tadaRequestBean)));
				}
//				if(tadaRequestProcess.getTdAmendmentDetails(tadaRequestBean)!=null){
//					session.setAttribute("tadaTdAmendmentDetails", tadaRequestProcess.getTdAmendmentDetails(tadaRequestBean));
//				}
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TADATDAPPROVALDETAILS);
				
			} else if(CPSUtils.compareStrings(CPSConstants.AMENDMENT, tadaRequestBean.getParam())){
				tadaRequestBean = tadaRequestBusiness.getAmendmentDetails(tadaRequestBean);
				session.setAttribute("amendmentDetails", (JSONObject) JSONSerializer.toJSON(tadaRequestBusiness.getAmendmentDetails(tadaRequestBean)));
				tadaRequestBean=tadaRequestBusiness.getEmpDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getDeptDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getPayDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getConveyanceMode(tadaRequestBean);
				session.setAttribute("travelTypeMapDetailsJSON",(JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getTravelTypeMapList(tadaRequestBean)));
				session.setAttribute("entitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getEntitleTypeList(tadaRequestBean)));
				session.setAttribute("taEntitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getEntitlementClassList(tadaRequestBean)));
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getTravelTypeList(tadaRequestBean)));
				session.setAttribute(CPSConstants.EMPLOYEEDETAILS, tadaRequestBean.getEmpBean());
				session.setAttribute("empDetailsJSON", (JSONObject) JSONSerializer.toJSON(tadaRequestBusiness.getEmpDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.CITYTYPELIST, tadaRequestProcess.getCityList(tadaRequestBean));
				session.setAttribute("cityList", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getCityList(tadaRequestBean)));
				session.setAttribute("tadaProjDirNamesList", tadaRequestBusiness.getPrjDirNamesList(tadaRequestBean));
				tadaRequestBean.setEntitlementExemption(tadaRequestBusiness.getEntitleExemptionOption(tadaRequestBean));
				request.setAttribute("TadaRequestBean", (JSONObject) JSONSerializer.toJSON(tadaRequestBean));
				if(CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, tadaRequestBean.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, tadaRequestBean.getRequestType())){
					viewName = "TadaApprovalRequest";
				} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, tadaRequestBean.getRequestType())){
					if(tadaRequestProcess.getTdRequestDetails(tadaRequestBean)!=null){
						session.setAttribute("tadaDaNewDetails", (JSONObject) JSONSerializer.toJSON(tadaRequestBusiness.getDaNewDetails(tadaRequestBean)));
						session.setAttribute("tdRequestDetails", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdRequestDetails(tadaRequestBean)));
						session.setAttribute("tdReqJourneyDetails", tadaRequestProcess.getTdReqJourneyDetails(tadaRequestBean));
						session.setAttribute("tdReqJourneyDetailsJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getTdReqJourneyDetails(tadaRequestBean)));
					}
					viewName = CPSConstants.TADATDADVANCE;
				} else if(CPSUtils.compareStrings(CPSConstants.SETTLEMENT, tadaRequestBean.getRequestType())){
					tadaRequestBean = tadaRequestProcess.getTadaTdRequestDetails(tadaRequestBean);
					session.setAttribute(CPSConstants.DANEWDETAILSLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getDaNewDetails(tadaRequestBean)));
					session.setAttribute(CPSConstants.DAOLDDETAILSLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getDaOldDetails(tadaRequestBean)));
					session.setAttribute(CPSConstants.CITYTYPELISTJSON, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getCityList(tadaRequestBean)));
					session.setAttribute(CPSConstants.CITYTYPELIST, tadaRequestProcess.getCityList(tadaRequestBean));
					tadaRequestBean.setTdDaNewDetailsList(tadaRequestProcess.getTadaTdDaNewDetails(tadaRequestBean));
					tadaRequestBean.setTdDaOldDetailsList(tadaRequestProcess.getTadaTdDaOldDetails(tadaRequestBean));
					tadaRequestBean.setTdRMAKmList(tadaRequestProcess.getTadaTdRMAKmDetails(tadaRequestBean));
					tadaRequestBean.setTdRMADayList(tadaRequestProcess.getTadaTdRMADayDetails(tadaRequestBean));
					if(tadaRequestProcess.getTadaTdDaNewDetails(tadaRequestBean).size()!=0){
						tadaRequestBean.setDaType("newDA");
					} else {
						tadaRequestBean.setDaType("oldDA");
						tadaRequestBean.setDaTypeRate(tadaRequestProcess.getDaOldType(tadaRequestBean));
					}
					tadaRequestBean.setType(CPSConstants.TADA_TD_SETTLEMENT);
					tadaRequestBean.setAmendmentType(CPSConstants.TADA_TD_SETTLEMENT);
					session.setAttribute("TadaRequestBean", (JSONObject) JSONSerializer.toJSON(tadaRequestBean));
					tadaRequestBean.setTdJourneyList(tadaRequestProcess.getTadaTdJourneyDetails(tadaRequestBean));
					viewName = CPSConstants.TADATDSETTLEMENT;
				} else if(CPSUtils.compareStrings(CPSConstants.REIMBURSEMENT, tadaRequestBean.getRequestType())){
					tadaRequestBean = tadaRequestProcess.getTadaTdRequestDetails(tadaRequestBean);
					tadaRequestProcess.getTdJourneyDates(tadaRequestBean);
					session.setAttribute(CPSConstants.DANEWDETAILSLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getDaNewDetails(tadaRequestBean)));
					session.setAttribute(CPSConstants.DAOLDDETAILSLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getDaOldDetails(tadaRequestBean)));
					session.setAttribute(CPSConstants.CITYTYPELISTJSON, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getCityList(tadaRequestBean)));
					session.setAttribute(CPSConstants.CITYTYPELIST, tadaRequestProcess.getCityList(tadaRequestBean));
					tadaRequestBean.setTdDaNewDetailsList(tadaRequestProcess.getTadaTdDaNewDetails(tadaRequestBean));
					tadaRequestBean.setTdDaOldDetailsList(tadaRequestProcess.getTadaTdDaOldDetails(tadaRequestBean));
					tadaRequestBean.setTdRMAKmList(tadaRequestProcess.getTadaTdRMAKmDetails(tadaRequestBean));
					tadaRequestBean.setTdRMADayList(tadaRequestProcess.getTadaTdRMADayDetails(tadaRequestBean));
					if(tadaRequestProcess.getTadaTdDaNewDetails(tadaRequestBean).size()!=0){
						tadaRequestBean.setDaType("newDA");
					} else {
						tadaRequestBean.setDaType("oldDA");
					}
					tadaRequestBean.setType(CPSConstants.TADA_TD_REIMBURSEMENT);
					tadaRequestBean.setAmendmentType(CPSConstants.TADA_TD_REIMBURSEMENT);
					session.setAttribute("TadaRequestBean", (JSONObject) JSONSerializer.toJSON(tadaRequestBean));
					tadaRequestBean.setTdJourneyList(tadaRequestProcess.getTadaTdJourneyDetails(tadaRequestBean));
					viewName = CPSConstants.TADATDSETTLEMENT;
				}
				
			} else if(CPSUtils.compareStrings(CPSConstants.SETTLEMENT, tadaRequestBean.getParam())){
				tadaRequestBean = tadaRequestProcess.getTadaTdRequestDetails(tadaRequestBean);
				tadaRequestProcess.getTdJourneyDates(tadaRequestBean);
				session.setAttribute(CPSConstants.DANEWDETAILSLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getDaNewDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.DAOLDDETAILSLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getDaOldDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.CITYTYPELISTJSON, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getCityList(tadaRequestBean)));
				session.setAttribute(CPSConstants.CITYTYPELIST, tadaRequestProcess.getCityList(tadaRequestBean));
				session.setAttribute("travelTypeList", tadaRequestBusiness.getTravelTypeList(tadaRequestBean));
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getTravelTypeList(tadaRequestBean)));
				session.setAttribute(CPSConstants.LOCALRMALIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getLocalRMAList(tadaRequestBean)));
				tadaRequestBean.setTdReqJourneyList(tadaRequestProcess.getTdReqJourneyList(tadaRequestBean));
				session.setAttribute("TadaRequestBean", (JSONObject) JSONSerializer.toJSON(tadaRequestBean));
				viewName = CPSConstants.TADATDSETTLEMENT;
			} else if(CPSUtils.compareStrings(CPSConstants.TADASETTLEMENTS, tadaRequestBean.getParam())){
				session.setAttribute(CPSConstants.TADAFINADVLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinAdvDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.TADATDFINADVCMPLLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinAdvCompleteDetails(tadaRequestBean)));
				
				session.setAttribute(CPSConstants.TADAFINSETTLEMENTLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinSettlementDetails(tadaRequestBean)));

//				session.setAttribute(CPSConstants.TADAFINSETTLEMENTSANCTIONLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinSettlementSanctionDetails(tadaRequestBean))); //Here i add this line for FinSettlementSanctionDetails
				
				session.setAttribute(CPSConstants.TADATDFINSETTLEMENTCMPLLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinSettlementCompleteDetails(tadaRequestBean)));
				System.out.println("ss");
				session.setAttribute(CPSConstants.TADAFINREIMLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinReimDetails(tadaRequestBean)));
//				session.setAttribute(CPSConstants.TADAFINREIMSANCTIONLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinReimSanctionDetails(tadaRequestBean))); //Here i add this line for cdaReimbursementDetails

				
				session.setAttribute(CPSConstants.TADATDFINREIMCMPLLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinReimCompleteDetails(tadaRequestBean)));
				
				session.setAttribute(CPSConstants.ACCOUNTOFFICERLIST, tadaRequestProcess.getAccOffList(tadaRequestBean));
				
				//session.setAttribute(CPSConstants.RETURN, CPSConstants.TADAFINSETTLEMENTS);
				
				viewName = CPSConstants.TADAFINSETTLEMENTS;
			}else if(CPSUtils.compareStrings("tadaCDASettlements", tadaRequestBean.getParam())){                            //This is new code for TadaCdaFinanceSettlement
				session.setAttribute(CPSConstants.TADATDADVSANCTIONLIST,(JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinAdvSanctionDetails(tadaRequestBean))); //Here i add this line for FinSettlementSanctionDetails);
				session.setAttribute(CPSConstants.TADAFINSETTLEMENTSANCTIONLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinSettlementSanctionDetails(tadaRequestBean))); //Here i add this line for FinSettlementSanctionDetails
				session.setAttribute(CPSConstants.TADAFINREIMSANCTIONLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinReimSanctionDetails(tadaRequestBean))); //Here i add this line for cdaReimbursementDetails
				session.setAttribute(CPSConstants.ACCOUNTOFFICERLIST, tadaRequestProcess.getAccOffList(tadaRequestBean));
				viewName = "TadaCDAFinanceSettlements";
				
			}else if (CPSUtils.compareStrings(CPSConstants.MANAGE, tadaRequestBean.getParam())) {
			
				
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),CPSConstants.SETTLEMENT)||CPSUtils.compareStrings(tadaRequestBean.getType(),CPSConstants.REIMBURSEMENT)||CPSUtils.compareStrings(tadaRequestBean.getType(),CPSConstants.ADVANCE)){
					tadaRequestBean = tadaRequestProcess.saveFinDetails(tadaRequestBean);
					if(CPSUtils.compareStrings(tadaRequestBean.getType(),CPSConstants.ADVANCE)){
						
						tadaRequestBean = tadaRequestProcess.saveCdaAmount(tadaRequestBean);
						tadaRequestBean = tadaRequestProcess.updateFlagDetails(tadaRequestBean);
					}
					
				}else if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaAdvance")||CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaReimbursement")||CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaSettlement")){
//					tadaRequestBean = tadaRequestProcess.saveFinDetails(tadaRequestBean);
					tadaRequestBean = tadaRequestProcess.saveCdaAmount(tadaRequestBean);
//					tadaRequestBean = tadaRequestProcess.updateClosedStatus(tadaRequestBean);
					tadaRequestBean = tadaRequestProcess.updateFlagDetails(tadaRequestBean);
					
				}	
				session.setAttribute(CPSConstants.TADAFINADVLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinAdvDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.TADATDADVSANCTIONLIST,(JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinAdvSanctionDetails(tadaRequestBean))); //Here i add this line for FinSettlementSanctionDetails);

				session.setAttribute(CPSConstants.TADATDFINADVCMPLLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinAdvCompleteDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.TADAFINSETTLEMENTLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinSettlementDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.TADAFINSETTLEMENTSANCTIONLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinSettlementSanctionDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.TADATDFINSETTLEMENTCMPLLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinSettlementCompleteDetails(tadaRequestBean)));
				
				session.setAttribute(CPSConstants.TADAFINREIMLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinReimDetails(tadaRequestBean)));
				session.setAttribute(CPSConstants.TADAFINREIMSANCTIONLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinReimSanctionDetails(tadaRequestBean)));
				
				session.setAttribute(CPSConstants.TADATDFINREIMCMPLLIST, (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getFinReimCompleteDetails(tadaRequestBean)));
				
				session.setAttribute(CPSConstants.ACCOUNTOFFICERLIST, tadaRequestProcess.getAccOffList(tadaRequestBean));
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),CPSConstants.ADVANCE)){
					viewName = CPSConstants.TADATDADVLIST;
				}else if(CPSUtils.compareStrings(tadaRequestBean.getType(),CPSConstants.SETTLEMENT)){
					viewName = CPSConstants.TADATDSETTLEMENTLIST;
				}else if(CPSUtils.compareStrings(tadaRequestBean.getType(),CPSConstants.REIMBURSEMENT)){
					viewName = CPSConstants.TADATDREIMBURSEMENTLIST;
				}else if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaAdvance")){
					viewName = "CDATadaTdAdvList";
				}else if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaSettlement")){
					viewName = "CdaTadaTdSettlementList";
				}else if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaReimbursement")){
					viewName = "CdaReimbursementList";
				}
				
			} else if(CPSUtils.compareStrings("getFoodDetails", tadaRequestBean.getParam())){
				//tadaRequestBean.setFoodDetailsDayRep(tadaRequestBusiness.getFoodDetails(tadaRequestBean));
				tadaRequestBean.setSfid(tadaRequestBean.getRequesterSfid());
				tadaRequestBean=tadaRequestBusiness.getPayDetails(tadaRequestBean);
				session.setAttribute("foodDetailsDayRep", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getFoodDetails(tadaRequestBean)));
				viewName = "TadaTdFoodDetailsDayRep";
			} else if(CPSUtils.compareStrings("getToPlace", tadaRequestBean.getParam())){
				session.setAttribute("toPlaceList", tadaRequestProcess.getToPlaceList(tadaRequestBean));
				session.setAttribute("toPlaceListJSON", (JSONArray) JSONSerializer.toJSON(tadaRequestProcess.getToPlaceList(tadaRequestBean)));
			} else if(CPSUtils.compareStrings("jDADetails", tadaRequestBean.getParam())){
				tadaRequestBusiness.getPayDetails(tadaRequestBean);
				tadaRequestBean.setCityTypeList(tadaRequestBusiness.getCitiesList(tadaRequestBean));
				tadaRequestBean.setDaOldDetailsList(tadaRequestProcess.getDaOldDetails(tadaRequestBean));
				tadaRequestBean.setTadaTdJdaList(tadaRequestBusiness.getJDADetails(tadaRequestBean));
				session.setAttribute("jdaDetailsList", (JSONArray) JSONSerializer.toJSON(tadaRequestBusiness.getJDADetails(tadaRequestBean)));
				viewName = "TadaTdSettlementOldDa";
			} else if(CPSUtils.compareStrings(CPSConstants.CANCELTADATDREQUEST, tadaRequestBean.getParam())){
				tadaRequestBean=tadaRequestBusiness.getEmpDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getDeptDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getPayDetails(tadaRequestBean);
				tadaRequestBean=tadaRequestBusiness.getConveyanceMode(tadaRequestBean);
				viewName = CPSConstants.TADATDREQUESTCANCELJSP;
			} else if(CPSUtils.compareStrings("leaveDetails", tadaRequestBean.getParam())){
				tadaRequestBean.setSfID((String)session.getAttribute(CPSConstants.SFID));
				tadaRequestBean.setTdLeaveTypeList(tadaRequestBusiness.getTadaLeaveTypeList(tadaRequestBean));
				session.setAttribute("tdLeaveTypeList", (JSONArray) JSONSerializer.toJSON(tadaRequestBean.getTdLeaveTypeList()));
				viewName = "TadaAppliedLeaveList";
			} else if(CPSUtils.compareStrings("tdUserDelegation", tadaRequestBean.getParam())){
				tadaRequestBean.setPendingReqList(tadaRequestProcess.getPendingReqList(tadaRequestBean));
				session.setAttribute("pendingReqListJSON", (JSONArray)JSONSerializer.toJSON(tadaRequestProcess.getPendingReqList(tadaRequestBean)));
				session.setAttribute("keyListJSON", (JSONArray)JSONSerializer.toJSON(tadaRequestProcess.getKeyList(tadaRequestBean)));
				tadaRequestBusiness.getEmpDetails(tadaRequestBean);
				tadaRequestBusiness.getRequesterRoleID(tadaRequestBean);
				tadaRequestBean.setBuildUpWfList(tadaRequestProcess.getWorkFlowList(45));
				tadaRequestBean.setBuildUpScGWflist(tadaRequestProcess.getWorkFlowList(46));
				tadaRequestProcess.getSuperiorIds(tadaRequestBean);
				session.setAttribute("tdRequestBeanJSON", (JSONObject)JSONSerializer.toJSON(tadaRequestBean));
				viewName = "TdUserDelegation";
			}else if(CPSUtils.compareStrings("deletefood", tadaRequestBean.getParam())){
				tadaRequestBusiness.removeRowId(tadaRequestBean);	
			}else if(CPSUtils.compareStrings("tadaAppliedUsers", tadaRequestBean.getParam())){
				tadaRequestBusiness.getDirectorateList(tadaRequestBean);
				viewName = "TadaAppliedUsers";
			}else if(CPSUtils.compareStrings("searchTdAppliedUsers", tadaRequestBean.getParam())){
				tadaRequestBusiness.getTdAppliedUsers(tadaRequestBean);
				session.setAttribute("tdAppliedUsersList", tadaRequestBean.getTdAppliedUsersList());
				session.setAttribute(CPSConstants.RETURN, "TadaAppliedUsersList");
				viewName="TadaAppliedUsersList";
			}
			mav = new ModelAndView(viewName, CPSConstants.TADA, tadaRequestBean);
			if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getResult()))
				if(CPSUtils.compareStrings(tadaRequestBean.getResult(), CPSConstants.SUCCESS) || CPSUtils.compareStrings(tadaRequestBean.getResult(), CPSConstants.TDREQUESTSUBMIT) || CPSUtils.compareStrings(tadaRequestBean.getResult(), CPSConstants.TDADVANCESUBMIT) || CPSUtils.compareStrings(tadaRequestBean.getResult(), CPSConstants.TDSETTLEMENTSUBMIT) || CPSUtils.compareStrings(tadaRequestBean.getResult(), CPSConstants.TDREIMSUBMIT)){
					mav.addObject(CPSConstants.MESSAGE, tadaRequestBean.getResult());
				} else {
					tadaRequestBean.setRemarks(tadaRequestBean.getResult());
				}
			if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getRemarks()))
			mav.addObject(CPSConstants.REMARKS, tadaRequestBean.getRemarks());
		}catch(Exception e){
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	
}
