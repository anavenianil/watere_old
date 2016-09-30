package com.callippus.web.controller.MT;

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

import com.callippus.web.beans.MT.MTApplicationBean;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.business.MT.MechincalTransportBusiness;
import com.callippus.web.business.requestworkflow.RequestWorkFlowBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.MT.MTVehicleRequestProcess;

@Controller
@RequestMapping("/transport.htm")
@SessionAttributes
public class MechincalTransportController {

	private static Log log = LogFactory.getLog(MechincalTransportController.class);
	@Autowired
	private MechincalTransportBusiness mechincalTransportBusiness;
	
	@Autowired
	private MTVehicleRequestProcess mtVehicleRequestProcess;
	
	@Autowired
	private RequestWorkFlowBusiness requestWorkflowBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.MTBEAN) MTApplicationBean mtBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = null;
		String viewName = "";
		String message = "";

		try { 
			ErpAction.userChecks(request);
			HttpSession session = request.getSession(true);
			mtBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			mtBean.setIpAddress(request.getRemoteAddr());
			mtBean.setCurrentDate(CPSUtils.getCurrentDate().toString());
			if(CPSUtils.compareStrings(CPSConstants.PAGING, mtBean.getParam())){
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTCATEGORY, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=category");
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute("categoryListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute("categoryList")));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTCATEGORYLIST);
				viewName = CPSConstants.MTCATEGORYMASTER;
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTSAVECATEGORY, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=savecategory");
				message = mechincalTransportBusiness.saveCategoryDetails(mtBean);
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute("categoryListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute("categoryList")));
				viewName = CPSConstants.MTCATEGORYLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTDELETECATEGORY, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deletecategory");
				message = mechincalTransportBusiness.deleteCategoryDetails(mtBean);
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute("categoryListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute("categoryList")));
				viewName = CPSConstants.MTCATEGORYLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTMODEL, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=model");
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute(CPSConstants.MODELLISTVALUES, mechincalTransportBusiness.modelList());
				session.setAttribute("modelListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute("modelList")));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTMODELLIST);
				viewName = CPSConstants.MTMODELMASTER;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTSAVEMODEL, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=savemodel");
				message = mechincalTransportBusiness.saveModelDetails(mtBean);
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute(CPSConstants.MODELLISTVALUES, mechincalTransportBusiness.modelList());
				session.setAttribute("modelListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute("modelList")));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTMODELLIST);
				viewName = CPSConstants.MTMODELLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTDELETEMODEL, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deletemodel");
				message = mechincalTransportBusiness.deleteModelDetails(mtBean);
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute(CPSConstants.MODELLISTVALUES, mechincalTransportBusiness.modelList());
				session.setAttribute("modelListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute("modelList")));
				viewName = CPSConstants.MTMODELLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.TRAVELAGENCY, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=travelagency");
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, mechincalTransportBusiness.travelAgencyList());
				session.setAttribute("TravelListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.TRAVELLISTVALUES)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTTRAVELAGENCYLIST);
				viewName = CPSConstants.MTTRAVELAGENCY;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTSAVETRAVELAGENCY, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=savetravelagency");
				message = mechincalTransportBusiness.saveTravelAgencyDetails(mtBean);
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, mechincalTransportBusiness.travelAgencyList());
				session.setAttribute("TravelListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.TRAVELLISTVALUES)));
				viewName = CPSConstants.MTTRAVELAGENCYLIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTDELETETRAVELAGENCY, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deletetravelagency");
				message = mechincalTransportBusiness.deleteTravelAgencyDetails(mtBean);
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, mechincalTransportBusiness.travelAgencyList());
				session.setAttribute("TravelListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.TRAVELLISTVALUES)));
				viewName = CPSConstants.MTTRAVELAGENCYLIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.NEWDRIVER, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=newDriver");
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.travelAgencyList()));
				session.setAttribute(CPSConstants.DRIVERLISTVALUES, mechincalTransportBusiness.driverList());
				session.setAttribute("driverListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.DRIVERLISTVALUES)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTDRIVERLIST);
				viewName = CPSConstants.MTDRIVERMASTER;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTSAVENEWDRIVER, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=savedriver");
				message = mechincalTransportBusiness.saveDriverDetails(mtBean);
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.travelAgencyList()));
				session.setAttribute(CPSConstants.DRIVERLISTVALUES, mechincalTransportBusiness.driverList());
				session.setAttribute("driverListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.DRIVERLISTVALUES)));
				viewName = CPSConstants.MTDRIVERLIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTDELETEDRIVER, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deletedriver");
				message = mechincalTransportBusiness.deleteDriverDetails(mtBean);
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.travelAgencyList()));
				session.setAttribute(CPSConstants.DRIVERLISTVALUES, mechincalTransportBusiness.driverList());
				session.setAttribute("driverListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.DRIVERLISTVALUES)));
				viewName = CPSConstants.MTDRIVERLIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.NEWVEHICLE, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=newVehicle");
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.travelAgencyList()));
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute(CPSConstants.MTFUELTYPELIST, mechincalTransportBusiness.getFuelTypeList());
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES1, (JSONArray) JSONSerializer.toJSON( mechincalTransportBusiness.categoryList()));
				session.setAttribute(CPSConstants.MODELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.modelList()));
				session.setAttribute("AllEmployeeList", (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.getAllEmployeeList()));
				
				session.setAttribute(CPSConstants.VEHICLELISTVALUES, mechincalTransportBusiness.vehicleList(mtBean));
				
				session.setAttribute("vehicleListJson", (JSONArray) JSONSerializer.toJSON( mechincalTransportBusiness.vehicleList(mtBean)));
				session.setAttribute("hiredDriversJson", (JSONArray) JSONSerializer.toJSON( mechincalTransportBusiness.getHiredDriversList()));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTVEHICLELIST);
				if(mtBean.getVehicleType()==0 && mtBean.getVehiclePoolType()==0){
					viewName = CPSConstants.MTVEHICLEMASTER;	
				}else{
					//session.setAttribute("", mechincalTransportBusiness.getHiredDriversList());
					viewName = CPSConstants.MTVEHICLELIST;	
				}
				
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTSAVENEWVEHICLE, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=saveNewVehicle");
				
				message = mechincalTransportBusiness.saveVehicleDetails(mtBean);
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.travelAgencyList()));
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute(CPSConstants.MTFUELTYPELIST, mechincalTransportBusiness.getFuelTypeList());
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES1, (JSONArray) JSONSerializer.toJSON( mechincalTransportBusiness.categoryList()));
				session.setAttribute(CPSConstants.MODELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.modelList()));
				session.setAttribute(CPSConstants.VEHICLELISTVALUES, mechincalTransportBusiness.vehicleList(mtBean));
				session.setAttribute("vehicleListJson", (JSONArray) JSONSerializer.toJSON( mechincalTransportBusiness.vehicleList(mtBean)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTVEHICLELIST);
				viewName = CPSConstants.MTVEHICLELIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTDELETENEWVEHICLE, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deleteVehicle");
				message = mechincalTransportBusiness.deleteVehicleDetails(mtBean);
				session.setAttribute(CPSConstants.TRAVELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.travelAgencyList()));
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES, mechincalTransportBusiness.categoryList());
				session.setAttribute(CPSConstants.MTFUELTYPELIST, mechincalTransportBusiness.getFuelTypeList());
				session.setAttribute(CPSConstants.CATEGORYLISTVALUES1, (JSONArray) JSONSerializer.toJSON( mechincalTransportBusiness.categoryList()));
				session.setAttribute(CPSConstants.MODELLISTVALUES, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.modelList()));
				session.setAttribute(CPSConstants.VEHICLELISTVALUES, mechincalTransportBusiness.vehicleList(mtBean));
				session.setAttribute("vehicleListJson", (JSONArray) JSONSerializer.toJSON( mechincalTransportBusiness.vehicleList(mtBean)));
				viewName = CPSConstants.MTVEHICLELIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.NEWADDRESS, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=newAddress");
				session.setAttribute(CPSConstants.MTADDRESSLISTVALUES, mechincalTransportBusiness.addressList());
				session.setAttribute("addressListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.MTADDRESSLISTVALUES)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTADDRESSLIST);
				viewName = CPSConstants.MTADDRESSDETAILS;	
			
			}else if(CPSUtils.compareStrings(CPSConstants.MTSAVEADDRESS, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=saveAddress");
				message = mechincalTransportBusiness.saveAddressDetails(mtBean);
				session.setAttribute(CPSConstants.MTADDRESSLISTVALUES, mechincalTransportBusiness.addressList());
				session.setAttribute("addressListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.MTADDRESSLISTVALUES)));
				viewName = CPSConstants.MTADDRESSLIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.MTDELETEADDRESS, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deleteAddress");
				message = mechincalTransportBusiness.deleteAddressDetails(mtBean);
				session.setAttribute(CPSConstants.MTADDRESSLISTVALUES, mechincalTransportBusiness.addressList());
				session.setAttribute("addressListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.MTADDRESSLISTVALUES)));
				viewName = CPSConstants.MTADDRESSLIST;	
				
			}else if(CPSUtils.compareStrings(CPSConstants.DRIVERABSENT, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=driverAbsent");
				session.setAttribute(CPSConstants.DRIVEREMPLOYEELIST, mechincalTransportBusiness.getDriverEmplyeeList());
				session.setAttribute(CPSConstants.ABSENTDRIVERLIST,mechincalTransportBusiness.getDriverAbsentDetails());
				session.setAttribute("AbsentDriverListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.ABSENTDRIVERLIST)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTDRIVERABSENTLIST);
				viewName = CPSConstants.MTDRIVERABSENT;	
			
			}else if(CPSUtils.compareStrings(CPSConstants.SAVEDRIVERABSENT, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=savedriverAbsent");
				session.setAttribute(CPSConstants.DRIVEREMPLOYEELIST, mechincalTransportBusiness.getDriverEmplyeeList());
				message = mechincalTransportBusiness.saveDriverAbsentDetails(mtBean);
				session.setAttribute(CPSConstants.ABSENTDRIVERLIST,mechincalTransportBusiness.getDriverAbsentDetails());
				session.setAttribute("AbsentDriverListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.ABSENTDRIVERLIST)));
				viewName = CPSConstants.MTDRIVERABSENTLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.DELETEDRIVERABSENT, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deletedriverAbsent");
				session.setAttribute(CPSConstants.DRIVEREMPLOYEELIST, mechincalTransportBusiness.getDriverEmplyeeList());
				message = mechincalTransportBusiness.deleteDriverAbsentDetails(mtBean);
				session.setAttribute(CPSConstants.ABSENTDRIVERLIST,mechincalTransportBusiness.getDriverAbsentDetails());
				session.setAttribute("AbsentDriverListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.ABSENTDRIVERLIST)));
				viewName = CPSConstants.MTDRIVERABSENTLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.VEHICLEABSENT, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=vehicleAbsent");
				session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				session.setAttribute(CPSConstants.ABSENTVEHICLELIST,mechincalTransportBusiness.getVehicleAbsentDetails());
				session.setAttribute("AbsentVehicleListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.ABSENTVEHICLELIST)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTVEHICLEABSENTLIST);
				viewName = CPSConstants.MTVEHICLEABSENT;	
			
			}else if(CPSUtils.compareStrings(CPSConstants.SAVEVEHICLEABSENT, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=savevehicleAbsent");
				//session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				message = mechincalTransportBusiness.saveVehicleAbsentDetails(mtBean);
				session.setAttribute(CPSConstants.ABSENTVEHICLELIST,mechincalTransportBusiness.getVehicleAbsentDetails());
				session.setAttribute("AbsentVehicleListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.ABSENTVEHICLELIST)));
				viewName = CPSConstants.MTVEHICLEABSENTLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.DELETEVEHICLEABSENT, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deletevehicleAbsent");
				//session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				message = mechincalTransportBusiness.deleteVehicleAbsentDetails(mtBean);
				session.setAttribute(CPSConstants.ABSENTVEHICLELIST,mechincalTransportBusiness.getVehicleAbsentDetails());
				session.setAttribute("AbsentVehicleListJson", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.ABSENTVEHICLELIST)));
				viewName = CPSConstants.MTVEHICLEABSENTLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.VEHICLEVSDRIVER, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=vehicleVsdriver");
				session.setAttribute(CPSConstants.DRIVEREMPLOYEELIST, mechincalTransportBusiness.getNotInMapDriverList());
				session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getNotInMapVehicleList());
				//session.setAttribute(CPSConstants.VEHICLELISTJSON, (JSONArray)JSONSerializer.toJSON(mechincalTransportBusiness.getVehicleList()));
				session.setAttribute(CPSConstants.VEHICLEDRIVERLIST, mechincalTransportBusiness.getVehicleDriverList(mtBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTVEHICLEVSDRIVERLIST);
				
				session.setAttribute("DriverEmployeeListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.DRIVEREMPLOYEELIST)));
				session.setAttribute("VehicleListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.VEHICLELIST)));
				viewName = CPSConstants.MTVEHICLEVSDRIVER;
				
			}else if(CPSUtils.compareStrings(CPSConstants.SAVEVEHICLEVSDRIVER, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=savevehicleVsdriver");
				message = mechincalTransportBusiness.saveVehicleDriverMasterData(mtBean);
				session.setAttribute(CPSConstants.DRIVEREMPLOYEELIST, mechincalTransportBusiness.getNotInMapDriverList());
				session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getNotInMapVehicleList());
				//session.setAttribute(CPSConstants.VEHICLELISTJSON, (JSONArray)JSONSerializer.toJSON(mechincalTransportBusiness.getVehicleList()));
				session.setAttribute(CPSConstants.VEHICLEDRIVERLIST, mechincalTransportBusiness.getVehicleDriverList(mtBean));
				
				
				session.setAttribute("DriverEmployeeListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.DRIVEREMPLOYEELIST)));
				session.setAttribute("VehicleListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.VEHICLELIST)));
				
				
				viewName = CPSConstants.MTVEHICLEVSDRIVER;
				//viewName = CPSConstants.MTVEHICLEVSDRIVERLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.DELETEVEHICLEVSDRIVER, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=deletevehicleVsdriver");
				message = mechincalTransportBusiness.deleteVehicleDriverDetails(mtBean);
				session.setAttribute(CPSConstants.DRIVEREMPLOYEELIST, mechincalTransportBusiness.getNotInMapDriverList());
				session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getNotInMapVehicleList());
				//session.setAttribute(CPSConstants.VEHICLELISTJSON, (JSONArray)JSONSerializer.toJSON(mechincalTransportBusiness.getVehicleList()));
				session.setAttribute(CPSConstants.VEHICLEDRIVERLIST, mechincalTransportBusiness.getVehicleDriverList(mtBean));
				viewName = CPSConstants.MTVEHICLEVSDRIVERLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.VEHICLEVSDRIVERDETAILS, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=vehicleVsdriverList");
				session.setAttribute(CPSConstants.VEHICLEDRIVERLIST, mechincalTransportBusiness.getVehicleDriverList(mtBean));
				session.setAttribute(CPSConstants.DRIVERABSENTLIST, mechincalTransportBusiness.getDriverAbsentList(mtBean));
				session.setAttribute(CPSConstants.VEHICLEABSENTLIST, mechincalTransportBusiness.getVehicleAbsentList(mtBean));
				session.setAttribute("allNotMappedVehicles", mechincalTransportBusiness.getallNotMappedVehicles());
				/*////////session.setAttribute("DriverAbsentListJson", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.DRIVERABSENTLIST)));
			
				//session.setAttribute(CPSConstants.AVAILABLEDRIVERLIST, mechincalTransportBusiness.getCurrentAvailabeDrivers(mtBean));
				//session.setAttribute(CPSConstants.ABSENTVEHICLEDRIVERLIST, mechincalTransportBusiness.getAbsentVehicleDrivers());
				*/
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTVEHICLEVSDRIVERDETAILS);
				
				if(mtBean.getType() !=null && mtBean.getType().equals("getDateWiseVDMap")){
						viewName = "MTVehicleVsDriverDetailsHistory";
					//viewName = CPSConstants.MTVEHICLEVSDRIVERDETAILSLIST;
				}else{
				viewName = CPSConstants.MTVEHICLEVSDRIVERDETAILS;
				}
				
			}else if(CPSUtils.compareStrings(CPSConstants.CHANGEDRIVER, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=changedriver");
				session.removeAttribute(CPSConstants.VEHICLEDRIVERLIST);
				session.removeAttribute(CPSConstants.DRIVERABSENTLIST);
				session.removeAttribute(CPSConstants.VEHICLEABSENTLIST);
				//message = mechincalTransportBusiness.checkVehicleDriverMap(mtBean);
				//if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)){
				message = mechincalTransportBusiness.saveVehicleDriverMasterData(mtBean);
				//}else{
				// message = CPSConstants.FAILED;	
				//}
				session.setAttribute(CPSConstants.VEHICLEDRIVERLIST, mechincalTransportBusiness.getVehicleDriverList(mtBean));
				session.setAttribute(CPSConstants.DRIVERABSENTLIST, mechincalTransportBusiness.getDriverAbsentList(mtBean));
				session.setAttribute(CPSConstants.VEHICLEABSENTLIST, mechincalTransportBusiness.getVehicleAbsentList(mtBean));
				//session.setAttribute(CPSConstants.AVAILABLEDRIVERLIST, mechincalTransportBusiness.getCurrentAvailabeDrivers(mtBean));
				//session.setAttribute(CPSConstants.ABSENTVEHICLEDRIVERLIST, mechincalTransportBusiness.getAbsentVehicleDrivers());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTVEHICLEVSDRIVERDETAILSLIST);
				viewName = CPSConstants.MTVEHICLEVSDRIVERDETAILSLIST;
				
			}else if(CPSUtils.compareStrings(CPSConstants.VEHICLEREQUESTFORM, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=vehicleRequestForm");
				mtBean.setEmployeeDetails(mechincalTransportBusiness.getEmployeeDetails(mtBean));
				session.setAttribute(CPSConstants.MTADDRESSLISTVALUES, mechincalTransportBusiness.addressList());
				session.setAttribute("addressListJSON", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.MTADDRESSLISTVALUES)));
				viewName = CPSConstants.MTREQUESTAPPLICATION;	
			
			}else if(CPSUtils.compareStrings(CPSConstants.SAVEVEHICLEREQUESTDETAILS, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=saveRequestDetails");
				//for offline request form
				if(!CPSUtils.isNullOrEmpty(mtBean.getRequestedFor())){
					mtBean.setSfID(mtBean.getRequestedFor().toUpperCase());
				}
				message=mechincalTransportBusiness.getEmpExist(mtBean.getSfID());
				if (CPSUtils.compareStrings(message, CPSConstants.YES)) {
					mtBean.setDesignationID(requestWorkflowBusiness.getDesignationId(mtBean.getSfID()));
					message = mtVehicleRequestProcess.initWorkflow(mtBean);	
					session.setAttribute("MTRequestID", mtBean.getRequestID());
					
				}
				/*message = mechincalTransportBusiness.checkVehicleRequesterDetails(mtBean);
				if(message.equals(CPSConstants.SUCCESS)){
					mtBean.setIpAddress(request.getRemoteAddr());
					message = mtVehicleRequestProcess.initWorkflow(mtBean);	
				}*/
				viewName = CPSConstants.RESULT;
			}else if(CPSUtils.compareStrings("VehicleAllocation", mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=VehicleAllocation");
				session.setAttribute("ApprovedReqList", mechincalTransportBusiness.getApprovedRequests());
				session.setAttribute("ApprovedReqJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("ApprovedReqList")));
				session.setAttribute("AllocatedReqList", mechincalTransportBusiness.getAllocatedRequests());
				session.setAttribute("AllocatedReqJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("AllocatedReqList")));
				session.setAttribute(CPSConstants.MTADDRESSLISTVALUES, mechincalTransportBusiness.addressList());
				session.setAttribute("addressListJSON", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.MTADDRESSLISTVALUES)));
				viewName = CPSConstants.MTVEHICLEALLOCATION;
			}/*else if(CPSUtils.compareStrings(CPSConstants.RELEASEVEHICLE, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=ReleaseVehicle");
				mtBean.setEmployeeDetails(mechincalTransportBusiness.getEmployeeDetails(mtBean));
				session.setAttribute(CPSConstants.ALLOCATEDVEHICLES, mechincalTransportBusiness.getAllocatedVehicleList(mtBean));
				viewName = CPSConstants.MTVEHICLERELEASE;
			}else if(CPSUtils.compareStrings(CPSConstants.RELEASESELECTEDVEHICLE, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=ReleaseSelectedVehicle");
				message = mechincalTransportBusiness.releaseSelectedVehicle(mtBean);
				session.removeAttribute(CPSConstants.ALLOCATEDVEHICLES);
				session.setAttribute(CPSConstants.ALLOCATEDVEHICLES, mechincalTransportBusiness.getAllocatedVehicleList(mtBean));
				viewName = CPSConstants.MTVEHICLERELEASELIST;
			}*/else if(CPSUtils.compareStrings(CPSConstants.RELEASEALLVEHICLES, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=ReleaseAllVehicles");
				session.removeAttribute(CPSConstants.ALLOCATEDVEHICLES);
				session.setAttribute(CPSConstants.ALLOCATEDALLVEHICLES, mechincalTransportBusiness.getAllAllocatedVehiclesList());
				session.setAttribute("CanceledJourneyList", mechincalTransportBusiness.getCanceledRequests());
				//viewName = CPSConstants.MTRELEASEALLVEHICLES;
				viewName = "MTVehicleAllocationConformation";
			}else if(CPSUtils.compareStrings(CPSConstants.RELEASEALLSELECTEDVEHICLES, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=ReleaseAllSelectedVehicles");
				/*message = mechincalTransportBusiness.releaseAllSelectedVehicles(mtBean);
				session.removeAttribute(CPSConstants.ALLOCATEDVEHICLES);
				session.setAttribute(CPSConstants.ALLOCATEDALLVEHICLES, mechincalTransportBusiness.getAllAllocatedVehiclesList());*/
				message = mechincalTransportBusiness.releaseVehicles(mtBean);
				session.setAttribute(CPSConstants.ALLOCATEDALLVEHICLES, mechincalTransportBusiness.getAllAllocatedVehiclesList());
				viewName = CPSConstants.MTRELEASEALLVEHICLESLIST;
			}
			else if(CPSUtils.compareStrings(CPSConstants.MILEAGE,mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=Mileage");
				session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				session.setAttribute(CPSConstants.MILEAGELIST, mechincalTransportBusiness.getMileageList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTMILEAGEMASTERLIST);
				viewName = CPSConstants.MTMILEAGEMASTER;
			}else if(CPSUtils.compareStrings(CPSConstants.SAVEMILEAGE, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=SaveMileage");
				//session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				message = mechincalTransportBusiness.saveMileageDetails(mtBean);
				session.setAttribute(CPSConstants.MILEAGELIST, mechincalTransportBusiness.getMileageList());
				viewName = CPSConstants.MTMILEAGEMASTERLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.DELETEMILEAGE, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=DeleteMileage");
				//session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				message = mechincalTransportBusiness.deleteMileageDetails(mtBean);
				session.setAttribute(CPSConstants.MILEAGELIST, mechincalTransportBusiness.getMileageList());
				viewName = CPSConstants.MTMILEAGEMASTERLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.MTREPORTS, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=MtReports");
				//session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				session.setAttribute(CPSConstants.MTYEARLIST, mechincalTransportBusiness.getYearList());
			
				viewName = CPSConstants.MTMILEAGEREPORTS;
			}else if(CPSUtils.compareStrings("getPreDayClosingRDE", mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=getPreDayClosingRDE");
				mtBean.setVehicleMileageDTO(mechincalTransportBusiness.getPreDayClosingDetails(mtBean));
				message = "getPreDayClosingRDE";
				viewName = CPSConstants.MTMILEAGEMASTERLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.OFFLINEVEHICLEREQUESTFORM, mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=OfflineVehicleRequestForm");
				//mtBean.setEmployeeDetails(mechincalTransportBusiness.getEmployeeDetails(mtBean));
				session.setAttribute("AllEmployeeList", mechincalTransportBusiness.getAllEmployeeList());
				session.setAttribute(CPSConstants.MTADDRESSLISTVALUES, mechincalTransportBusiness.addressList());
				session.setAttribute("addressListJSON", (JSONArray) JSONSerializer.toJSON(session.getAttribute(CPSConstants.MTADDRESSLISTVALUES)));
				//viewName = CPSConstants.MTOFFLINEREQUESTAPPLICATION;	
				viewName = CPSConstants.MTREQUESTAPPLICATION;//
			
			}else if(CPSUtils.compareStrings("getVDList", mtBean.getParam())){
				log.debug("MechincalTransportController --> onSubmit --> param=getVDList");
				
				//session.setAttribute("VDList", mechincalTransportBusiness.getAvailableVDListForParticularJourney(mtBean.getPk()));
				session.setAttribute("VDList", mechincalTransportBusiness.getAvailableVDListForParticularJourney(mtBean));
				session.setAttribute("BusyVDList", mechincalTransportBusiness.getVDListForCombineAlloc(mtBean));
				session.setAttribute("BusyVDListJSON", (JSONArray) JSONSerializer.toJSON(session.getAttribute("BusyVDList")));
				//session.setAttribute("allocationDetails", mechincalTransportBusiness.getAllocationDetails(mtBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MTAVAILABLEVEHICLEDRIVERLIST);
				viewName = "MTAvailableVehicleDriverList";
			}/*else if(CPSUtils.compareStrings("getVDListForCombineAlloc", mtBean.getParam())){
				session.setAttribute("BusyVDList", mechincalTransportBusiness.getVDListForCombineAlloc(mtBean.getPk()));
				session.setAttribute("BusyVDListJSON", (JSONArray) JSONSerializer.toJSON(session.getAttribute("BusyVDList")));
				viewName = "MTBusyVehicleDriverList";
			}*/
			else if(CPSUtils.compareStrings("getAvailableDrivers", mtBean.getParam())){
				session.setAttribute(CPSConstants.AVAILABLEDRIVERLIST, (JSONArray) JSONSerializer.toJSON(mechincalTransportBusiness.getCurrentAvailabeDrivers(mtBean)));
				session.setAttribute("DriverAbsentListJson", (JSONArray)JSONSerializer.toJSON(mechincalTransportBusiness.getDriverAbsentList(mtBean)));
				viewName = CPSConstants.MTVEHICLEVSDRIVERDETAILSLIST;
			}else if(CPSUtils.compareStrings("saveVehicleAllocationDetails", mtBean.getParam())){
				
				message=mechincalTransportBusiness.saveVehicleAllocationDetails(mtBean);
				session.setAttribute("ApprovedReqList", mechincalTransportBusiness.getApprovedRequests());
				session.setAttribute("ApprovedReqJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("ApprovedReqList")));
				viewName = "MTVehicleAllocationPendingList";
				//viewName = "MTVehicleAllocationResult";
				//viewName = CPSConstants.RESULT;
			}else if(CPSUtils.compareStrings("userCancelJourney", mtBean.getParam())){
				message=mechincalTransportBusiness.userCancelJourney(mtBean);
				//viewName = "MTVehicleRequestDetails";
				viewName = CPSConstants.RESULT;
			}else if(CPSUtils.compareStrings("deallocateVehicleForJourney",mtBean.getParam())){
				message=mechincalTransportBusiness.deallocateVehicle(mtBean);
				session.setAttribute("CanceledJourneyList", mechincalTransportBusiness.getCanceledRequests());
				viewName="MTVehicleAllocationCanceledList";
			}
			else if(CPSUtils.compareStrings("mtDayWiseAllocation",mtBean.getParam())){
				session.setAttribute("vehicleTypeList", mechincalTransportBusiness.getVehicleTypeList());
				viewName = "MTDayWiseAllocation";
			}else if(CPSUtils.compareStrings("getCompletionDetails",mtBean.getParam())){
				session.setAttribute("vehicleTypeList", mechincalTransportBusiness.getVehicleTypeList());
				viewName = "MTCompletionReportPage";
			}else if(CPSUtils.compareStrings("getDailyMileageDetails",mtBean.getParam())){
				session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				viewName = "MTDailyMileageReportPage";
			}else if(CPSUtils.compareStrings("getYearlyMileageDetails",mtBean.getParam())){
				session.setAttribute(CPSConstants.VEHICLELIST, mechincalTransportBusiness.getVehicleList());
				session.setAttribute(CPSConstants.MTYEARLIST, mechincalTransportBusiness.getYearList());
				viewName = "MTYearlyMileageReportPage";
			}
			else if(CPSUtils.compareStrings("getEmpDetails", mtBean.getParam())){
				
				message=mechincalTransportBusiness.getEmpExist(mtBean.getRequestedFor().toUpperCase());
				EmployeeBean employee = null;
				if (CPSUtils.compareStrings(message, CPSConstants.YES)) {
					 employee = mechincalTransportBusiness.getEmpDetails(mtBean.getRequestedFor().toUpperCase());
				}else{
					mtBean.setMessage(message);
				}
				
					mtBean.setEmployeeDetails(employee);
					/*if(mtBean.getRequestedFor().contains(",")){
						mtBean.setRequestedFor(mtBean.getRequestedFor().split(",")[0]);
					}*/
					
					//session.setAttribute("empDetailsJson", (JSONArray)JSONSerializer.toJSON(mtBean.getEmployeeDetails()));
					viewName = CPSConstants.MTREQUESTAPPLICATION;	
				//viewName = CPSConstants.RESULT;
			}else if(CPSUtils.compareStrings("getDedicatedVehicles", mtBean.getParam())){
				session.setAttribute("DedicatedVehiclesList", mechincalTransportBusiness.getDedicatedVehicles());
				session.setAttribute("DedicatedVehiclesListJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("DedicatedVehiclesList")));
				viewName= "MTDedicatedAllVehicles";
			}else if(CPSUtils.compareStrings("freeDedicatedVehicles", mtBean.getParam())){
				message=mechincalTransportBusiness.freeDedicatedVehicles(mtBean);
				session.setAttribute("DedicatedVehiclesList", mechincalTransportBusiness.getDedicatedVehicles());
				viewName= "MTDedicatedAllVehiclesList";
			}else if(CPSUtils.compareStrings("saveCombineAlloc", mtBean.getParam())){
				message=mechincalTransportBusiness.saveVehicleAllocationDetails(mtBean);
				//message=mechincalTransportBusiness.saveCombineAlloc(mtBean);
				session.setAttribute("ApprovedReqList", mechincalTransportBusiness.getApprovedRequests());
				session.setAttribute("ApprovedReqJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("ApprovedReqList")));
				//viewName = "MTVehicleAllocationResult";
				viewName = "MTVehicleAllocationPendingList";
			}else if(CPSUtils.compareStrings("updateRequestDetails", mtBean.getParam())){
				message=mechincalTransportBusiness.updateRequestDetails(mtBean);
				session.setAttribute("AllocatedReqList", mechincalTransportBusiness.getAllocatedRequests());
				session.setAttribute("AllocatedReqJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("AllocatedReqList")));
				
				viewName = "MTVehicleAllocationCompltedList";
			}else if(CPSUtils.compareStrings("updateVehicleAllocationDetails", mtBean.getParam())){
				message=mechincalTransportBusiness.updateVehicleAllocationDetails(mtBean);
				session.setAttribute("AllocatedReqList", mechincalTransportBusiness.getAllocatedRequests());
				session.setAttribute("AllocatedReqJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("AllocatedReqList")));
				viewName = "MTVehicleAllocationCompltedList";
			}else if(CPSUtils.compareStrings("updateCombineAlloc", mtBean.getParam())){
				message=mechincalTransportBusiness.updateVehicleAllocationDetails(mtBean);
				session.setAttribute("AllocatedReqList", mechincalTransportBusiness.getAllocatedRequests());
				session.setAttribute("AllocatedReqJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("AllocatedReqList")));
				viewName = "MTVehicleAllocationCompltedList";
			}
			
			

			mav = new ModelAndView(viewName, CPSConstants.MTBEAN, mtBean);
			mav.addObject(CPSConstants.MESSAGE, message);
			
		
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}