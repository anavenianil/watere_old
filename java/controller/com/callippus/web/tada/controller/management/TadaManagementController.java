package com.callippus.web.tada.controller.management;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.tada.beans.management.TadaManagementBean;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.business.management.TadaManagementBusiness;

@Controller
@RequestMapping("/tada.htm")
@SessionAttributes
public class TadaManagementController {
	private static Log log = LogFactory.getLog(TadaManagementController.class);
	@Autowired
	private TadaManagementBusiness tadaManagementBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute TadaManagementBean tadaBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			tadaBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(tadaBean.getParam())) {
				tadaBean.setParam(CPSConstants.DAENTITLEMASTER);
			} else if (CPSUtils.compareStrings(tadaBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			if(CPSUtils.compareStrings(CPSConstants.CITYTYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=cityType");
				//session.setAttribute(CPSConstants.CITYTYPELIST,tadaManagementBusiness.getCityTypeList(tadaBean));
				session.setAttribute(CPSConstants.CITYTYPELIST,tadaManagementBusiness.cityTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CITYTYPEMASTERLIST);
				viewName = CPSConstants.CITYTYPEMASTER;
			} else if(CPSUtils.compareStrings(CPSConstants.MANAGECITYTYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageCityType");
				tadaBean=tadaManagementBusiness.submitCityType(tadaBean);
				//session.setAttribute(CPSConstants.CITYTYPELIST,tadaManagementBusiness.getCityTypeList(tadaBean));
				session.setAttribute(CPSConstants.CITYTYPELIST,tadaManagementBusiness.cityTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CITYTYPEMASTERLIST);
				viewName = CPSConstants.CITYTYPEMASTERLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.CITYTYPELIST, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=CityTypeList");
				tadaManagementBusiness.cityTypeList(tadaBean);
				session.setAttribute(CPSConstants.CITYTYPELIST,tadaManagementBusiness.cityTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CITYTYPEMASTERLIST);
				viewName = CPSConstants.CITYTYPEMASTERLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.DELETECITYTYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=deleteCityType");
				tadaManagementBusiness.deleteCityType(tadaBean);
				//session.setAttribute(CPSConstants.CITYTYPELIST,tadaManagementBusiness.getCityTypeList(tadaBean));
				session.setAttribute(CPSConstants.CITYTYPELIST,tadaManagementBusiness.cityTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CITYTYPEMASTERLIST);
				viewName = CPSConstants.CITYTYPEMASTERLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.DAENTITLEMASTER, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=daEntitleMaster");
				session.setAttribute(CPSConstants.DADETAILSLIST,tadaManagementBusiness.getDaDetails(tadaBean));
				session.setAttribute(CPSConstants.UNIQUECITYTYPELIST, tadaManagementBusiness.getUniqueCityTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DAENTITLEMENT);
				viewName = CPSConstants.DAENTITLEMENT;
			} else if(CPSUtils.compareStrings(CPSConstants.MANAGEDADETAILS, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageDaDetails");
				tadaBean=tadaManagementBusiness.submitDaDetails(tadaBean);
				session.setAttribute(CPSConstants.DADETAILSLIST,tadaManagementBusiness.getDaDetails(tadaBean));
				viewName = CPSConstants.DAENTITLEMENTLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.DELETEDADETAILS, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=deleteDaDetails");
				tadaBean.setResult(tadaManagementBusiness.deleteDaDetails(tadaBean));
				if(CPSUtils.compareStrings(CPSConstants.DELETE, tadaBean.getResult()))
					session.setAttribute(CPSConstants.DADETAILSLIST,tadaManagementBusiness.getDaDetails(tadaBean));
				viewName = CPSConstants.DAENTITLEMENTLIST;	
			} else if(CPSUtils.compareStrings(CPSConstants.DANEWENTITLEMASTER, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=daNewEntitleMaster");
				session.setAttribute("gradePayList",tadaManagementBusiness.getUniqueGradePayDetails(tadaBean) );
				session.setAttribute(CPSConstants.DANEWDETAILSLIST,tadaManagementBusiness.getDaNewDetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DANEWENTITLEMENTLIST);
				viewName = CPSConstants.DANEWENTITLEMENT;
			} else if(CPSUtils.compareStrings(CPSConstants.MANAGEDANEWDETAILS, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageDaNewDetails");
				tadaBean=tadaManagementBusiness.submitDaNewDetails(tadaBean);
				session.setAttribute(CPSConstants.DANEWDETAILSLIST,tadaManagementBusiness.getDaNewDetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DANEWENTITLEMENTLIST);
				viewName = CPSConstants.DANEWENTITLEMENTLIST;
				session.setAttribute("gradePayList",tadaManagementBusiness.getUniqueGradePayDetails(tadaBean) );
			} else if(CPSUtils.compareStrings(CPSConstants.DELETEDANEWDETAILS, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=deleteDaNewDetails");
				tadaBean.setResult(tadaManagementBusiness.deleteDaNewDetails(tadaBean));
				session.setAttribute(CPSConstants.DANEWDETAILSLIST,tadaManagementBusiness.getDaNewDetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DANEWENTITLEMENTLIST);
				viewName = CPSConstants.DANEWENTITLEMENTLIST;	
			} else if(CPSUtils.compareStrings(CPSConstants.TRAVELTYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=travelType");
				session.setAttribute(CPSConstants.TTLIST,tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TRAVELTYPELIST);
				viewName = CPSConstants.TRAVEL;
			} else if(CPSUtils.compareStrings(CPSConstants.MANAGETRAVELTYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageTravelType");
				tadaBean=tadaManagementBusiness.submitTravelType(tadaBean);
				session.setAttribute(CPSConstants.TTLIST,tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TRAVELTYPELIST);
				viewName = CPSConstants.TRAVELTYPELIST;
			} else if(CPSUtils.compareStrings(CPSConstants.DELETETRAVELTYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=deleteTravelType");
				tadaManagementBusiness.deleteTravelType(tadaBean);
				session.setAttribute(CPSConstants.TTLIST,tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TRAVELTYPELIST);
				viewName = CPSConstants.TRAVELTYPELIST;
			} else if(CPSUtils.compareStrings(CPSConstants.TAENTITLEMASTER, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=taEntitleMaster");
				session.setAttribute("gradePayList",tadaManagementBusiness.getUniqueGradePayDetails(tadaBean) );
				session.setAttribute("travelTypeMapDetailsJSON",(JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeMapList()));
				session.setAttribute("gradePayListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getUniqueGradePayDetails(tadaBean)));
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeList(tadaBean)));
				session.setAttribute("entitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitleTypeList(tadaBean)));
				session.setAttribute("travelTypeList", tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute("taEntitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitlementClassList(tadaBean)));
				session.setAttribute("taEntitleJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitlementList(tadaBean)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TAENTITLEMENTLIST);
				viewName=CPSConstants.TAENTITLEMENT;
			} else if(CPSUtils.compareStrings(CPSConstants.MANAGEENTITLEMENT, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageEntitlement");
				tadaBean=tadaManagementBusiness.submitEntitlementClass(tadaBean);
				session.setAttribute("gradePayList",tadaManagementBusiness.getUniqueGradePayDetails(tadaBean) );
				session.setAttribute("gradePayListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getUniqueGradePayDetails(tadaBean)));
				session.setAttribute("travelTypeMapDetailsJSON",(JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeMapList()));
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeList(tadaBean)));
				session.setAttribute("travelTypeList", tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute("entitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitleTypeList(tadaBean)));
				tadaBean.setGradePay("");
				session.setAttribute("taEntitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitlementClassList(tadaBean)));
				session.setAttribute("taEntitleJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitlementList(tadaBean)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TAENTITLEMENTLIST);
				viewName = CPSConstants.TAENTITLEMENTLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.TAENTITLEMENTLISTS, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=taEntitlementList");
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeList(tadaBean)));
				session.setAttribute("entitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitleTypeList(tadaBean)));
				session.setAttribute("taEntitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitlementClassList(tadaBean)));
				//session.setAttribute("taEntitleClassListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getEntitlementClassLists(tadaBean)));
				session.setAttribute("travelTypeList", tadaManagementBusiness.getTravelTypeList(tadaBean));
				viewName = CPSConstants.TAENTITLEMENTLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.ENTITLETYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=entitleType");
				//session.setAttribute(CPSConstants.ENTITLETYPELIST, tadaManagementBusiness.getEntitleTypeList(tadaBean));
				session.setAttribute(CPSConstants.ENTITLETYPELIST, tadaManagementBusiness.getEntitleClassList(tadaBean));
				session.setAttribute("travelTypeList", tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ENTITLETYPEMASTERLIST);
				viewName=CPSConstants.ENTITLETYPEMASTER;
			} else if(CPSUtils.compareStrings(CPSConstants.MANAGEENTITLETYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageEntitleType");
				tadaBean=tadaManagementBusiness.submitEntitleType(tadaBean);
				//session.setAttribute(CPSConstants.ENTITLETYPELIST, tadaManagementBusiness.getEntitleTypeList(tadaBean));
				session.setAttribute(CPSConstants.ENTITLETYPELIST, tadaManagementBusiness.getEntitleClassList(tadaBean));
				session.setAttribute("travelTypeList", tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ENTITLETYPEMASTERLIST);
				viewName=CPSConstants.ENTITLETYPEMASTERLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.ENTITLECLASSLIST, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=entitleClassList");
				session.setAttribute(CPSConstants.ENTITLETYPELIST, tadaManagementBusiness.getEntitleClassList(tadaBean));
				session.setAttribute("travelTypeList", tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ENTITLETYPEMASTERLIST);
				viewName=CPSConstants.ENTITLETYPEMASTERLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.DELETEENTITLETYPE, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=deleteEntitleType");
				tadaManagementBusiness.deleteEntitleType(tadaBean);
				//session.setAttribute(CPSConstants.ENTITLETYPELIST, tadaManagementBusiness.getEntitleTypeList(tadaBean));
				session.setAttribute(CPSConstants.ENTITLETYPELIST, tadaManagementBusiness.getEntitleClassList(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ENTITLETYPEMASTERLIST);
				viewName=CPSConstants.ENTITLETYPEMASTERLIST;
			} else if(CPSUtils.compareStrings(CPSConstants.TRAVELTYPEMAP,tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=travelTypeMap");
				session.setAttribute("gradePayList",tadaManagementBusiness.getUniqueGradePayDetails(tadaBean) );
				session.setAttribute("travelTypeList", tadaManagementBusiness.getTravelTypeList(tadaBean));
				session.setAttribute("gradePayListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getUniqueGradePayDetails(tadaBean)));
				session.setAttribute("travelTypeListJSON", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeList(tadaBean)));
				session.setAttribute("travelTypeMapDetailsJSON",(JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeMapList()));
				viewName=CPSConstants.TATRAVELTYPEMAP;
			} else if(CPSUtils.compareStrings(CPSConstants.MANAGETRAVELTYPEMAP, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageTravelTypeMap");
				tadaBean=tadaManagementBusiness.submitTravelTypeMap(tadaBean);
				viewName=CPSConstants.RESULT;
			} else if(CPSUtils.compareStrings(CPSConstants.LOCALRMA, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=localRMA");
				session.setAttribute("localRmaList", tadaManagementBusiness.getLocalRMADetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOCALRMAMASTERLIST);
				viewName=CPSConstants.LOCALRMAMASTER;
				
			}else if(CPSUtils.compareStrings(CPSConstants.DAONTOUR, tadaBean.getParam())){                           //This condition added for getting records new DaOnTour master
				log.debug("TadaManagementController --> onSubmit --> param=daOnTour");
				session.setAttribute("daOnTourList", tadaManagementBusiness.getTourDaDetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DAONTOURMASTERLIST);
				viewName=CPSConstants.DAONTOURMASTER;
				
			}else if(CPSUtils.compareStrings(CPSConstants.MANAGELOCALRMA, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=manageLocalRMA");
				tadaBean=tadaManagementBusiness.submitLocalRMA(tadaBean);
				session.setAttribute("localRmaList", tadaManagementBusiness.getLocalRMADetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOCALRMAMASTERLIST);
				viewName=CPSConstants.LOCALRMAMASTERLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.MANAGEDAONTOUR, tadaBean.getParam())){                 //This condition added for getting records new DaOnTour master
				log.debug("TadaManagementController --> onSubmit --> param=MANAGEDAONTOUR");
				tadaBean=tadaManagementBusiness.submitDaOnTour(tadaBean);
				session.setAttribute("daOnTourList", tadaManagementBusiness.getTourDaDetails(tadaBean));
				System.out.println("vvv");
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DAONTOURMASTERLIST);
				viewName=CPSConstants.DAONTOURMASTERLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.DELETEDAONTOUR, tadaBean.getParam())){              
				log.debug("TadaManagementController --> onSubmit --> param=deleteDaOnTour");
				tadaManagementBusiness.deleteDaOnTOur(tadaBean);
				session.setAttribute("daOnTourList", tadaManagementBusiness.getTourDaDetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DAONTOURMASTERLIST);
				viewName=CPSConstants.DAONTOURMASTERLIST;
			} 	
			
			else if(CPSUtils.compareStrings(CPSConstants.DELETELOCALRMA, tadaBean.getParam())){
				log.debug("TadaManagementController --> onSubmit --> param=deleteLocalRMA");
				tadaManagementBusiness.deleteLocalRMA(tadaBean);
				session.setAttribute("localRmaList", tadaManagementBusiness.getLocalRMADetails(tadaBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOCALRMAMASTERLIST);
				viewName=CPSConstants.LOCALRMAMASTERLIST;
			}
			//tada project director
			else if(CPSUtils.compareStrings(CPSConstants.TADAPROJECTDIRECTOR, tadaBean.getParam())){
				 List<TadaProjectDirectorsDTO> list=tadaManagementBusiness.getTadaProjectDirectorList();
				 tadaBean.setDirectorList(list);
				 tadaBean.setProgramList(tadaManagementBusiness.programList());
				 session.setAttribute("directorList", list);
				 session.setAttribute(CPSConstants.RETURN, "TadaProjectDirectorList");
				 viewName = "TadaProjectDirector";
			} else if(CPSUtils.compareStrings(CPSConstants.SUBMITPROJECTDIRECTOR, tadaBean.getParam())){
				 tadaBean.setMessage(tadaManagementBusiness.submitTadaProjectDirectorDetails(tadaBean));
				 List<TadaProjectDirectorsDTO> list=tadaManagementBusiness.getTadaProjectDirectorList();
				 tadaBean.setDirectorList(list);
				 session.setAttribute(CPSConstants.RETURN, "TadaProjectDirectorList");
				 viewName = "TadaProjectDirectorList";
			 }else if(CPSUtils.compareStrings(CPSConstants.DELETEPROJECTDIRECTOR, tadaBean.getParam())){
				 tadaBean.setMessage(tadaManagementBusiness.deleteTadaProjectDetails(Integer.parseInt(tadaBean.getPk())));
				 List<TadaProjectDirectorsDTO> list=tadaManagementBusiness.getTadaProjectDirectorList();
				 tadaBean.setDirectorList(list);
				 session.setAttribute(CPSConstants.RETURN, "TadaProjectDirectorList");
				 viewName = "TadaProjectDirectorList";
			 }else if(CPSUtils.compareStrings(CPSConstants.TAENTITLEEXEMPTION, tadaBean.getParam())){
				 log.debug("TadaManagementController --> onSubmit --> param=taEntilteExemption");
				 tadaBean.setTadaBeanList(tadaManagementBusiness.getSfIDList());
				 tadaBean.setProgramList(tadaManagementBusiness.programList());
				 tadaBean.setProjectsList(tadaManagementBusiness.getProjectsList());
				 tadaBean.setTravelTypeList(tadaManagementBusiness.getTravelTypeList(tadaBean));
				 tadaBean.setEntitleExemptionList(tadaManagementBusiness.getEntitleExemptionList(tadaBean));
				 session.setAttribute("entitleExemptionList", tadaManagementBusiness.getEntitleExemptionList(tadaBean));
				 session.setAttribute("projectsList", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getProjectsList()));
				 session.setAttribute("travelTypeMapDetailsJSON",(JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeUnMapList()));
				 session.setAttribute("gradePayList", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getGradePayList()));
				 session.setAttribute("travelTypeList", (JSONArray) JSONSerializer.toJSON(tadaManagementBusiness.getTravelTypeList(tadaBean)));
				 session.setAttribute(CPSConstants.RETURN, "TaEntitleExemptionList");
				 viewName = "TaEntitleExemption";
			 }else if(CPSUtils.compareStrings(CPSConstants.SUBMITENTITLEEXEMPTION, tadaBean.getParam())){
				 log.debug("TadaManagementController --> onSubmit --> param=submitEntitleExemption");
				 tadaManagementBusiness.submitTaEntitleExemption(tadaBean);
				 tadaBean.setEntitleExemptionList(tadaManagementBusiness.getEntitleExemptionList(tadaBean));
				 session.setAttribute("entitleExemptionList", tadaManagementBusiness.getEntitleExemptionList(tadaBean));
				 session.setAttribute(CPSConstants.RETURN, "TaEntitleExemptionList");
				 viewName = "TaEntitleExemptionList";
			 }else if(CPSUtils.compareStrings("deleteEntitleExemption", tadaBean.getParam())){
				 log.debug("TadaManagementController --> onSubmit --> param=deleteEntitleExemption");
				 tadaBean.setMessage(tadaManagementBusiness.deleteEntitleExemption(tadaBean));
				 tadaBean.setEntitleExemptionList(tadaManagementBusiness.getEntitleExemptionList(tadaBean));
				 session.setAttribute("entitleExemptionList", tadaManagementBusiness.getEntitleExemptionList(tadaBean));
				 session.setAttribute(CPSConstants.RETURN, "TaEntitleExemptionList");
				 viewName = "TaEntitleExemptionList";
			 }else if(CPSUtils.compareStrings("program", tadaBean.getParam())){
				 log.debug("TadaManagementController --> onSubmit --> param=program");
				 tadaBean.setProgramList(tadaManagementBusiness.programList());
				 session.setAttribute("programList", tadaManagementBusiness.programList());
				 session.setAttribute(CPSConstants.RETURN, "TadaProgramList");
				 viewName="TadaProgram";
			 }else if(CPSUtils.compareStrings("submitProgram", tadaBean.getParam())){
				 tadaManagementBusiness.submitProgram(tadaBean);
				 tadaBean.setProgramList(tadaManagementBusiness.programList());
				 session.setAttribute("programList", tadaManagementBusiness.programList());
				 session.setAttribute(CPSConstants.RETURN, "TadaProgramList");
				 viewName = "TadaProgramList";
			 }else if(CPSUtils.compareStrings("deleteProgram", tadaBean.getParam())){
				 tadaManagementBusiness.deleteProgram(tadaBean);
				 tadaBean.setProgramList(tadaManagementBusiness.programList());
				 session.setAttribute("programList", tadaManagementBusiness.programList());
				 session.setAttribute(CPSConstants.RETURN, "TadaProgramList");
				 viewName = "TadaProgramList";
			 }
			
		
			mav = new ModelAndView(viewName, CPSConstants.TADA, tadaBean);
			if(!CPSUtils.isNullOrEmpty(tadaBean.getResult())){
				mav.addObject(CPSConstants.RESULT, tadaBean.getResult());
			} else if(!CPSUtils.isNullOrEmpty(tadaBean.getMessage())){
				mav.addObject(CPSConstants.RESULT, tadaBean.getMessage());
			}
			
		}catch(Exception e){
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}


}
