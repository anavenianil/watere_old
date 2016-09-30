package com.callippus.web.controller.empPayment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

import com.callippus.web.beans.EmpPayment.EmployeePaymentBean;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.business.empPayment.EmployeePaymentBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/empPayment.htm")
@SessionAttributes
public class EmployeePaymentController {

	private static Log log = LogFactory.getLog(EmployeePaymentController.class);
	@Autowired
	private EmployeePaymentBusiness empPaymentBusiness;
	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.EMPPAYMENT) EmployeePaymentBean empPaymentBean , BindingResult result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = null;
		String viewName = null;
        HttpSession session=null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			empPaymentBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(empPaymentBean.getParam())) {
				empPaymentBean.setParam(CPSConstants.EMPPAYMENTHOME);
			}else if (CPSUtils.compareStrings(empPaymentBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			if (CPSUtils.compareStrings(CPSConstants.EMPPAYMENTHOME, empPaymentBean.getParam())) {
				log.debug("EmployeePaymentController --> onSubmit --> param=empPaymentHome");
				session.removeAttribute(CPSConstants.EMPPAYMENTLIST);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.EMPLOYEEPAYMENTENTRYLIST);
				session.removeAttribute("empDetailsList");
				viewName ="CPSConstants.EMPLOYEEPAYMENTENTRY";// CPSConstants.EMPLOYEEPAYMENTENTRY;
				//viewName = "EmployeeBasicGradePay";
				//viewName ="empPaymentSearch";
			}
			
             	else if (CPSUtils.compareStrings("basicPaySFIDDetails", empPaymentBean.getParam())) {
				log.debug("EmployeePaymentController  --> onSubmit --> param=basicPaySFIDDetails");
				List<EmployeeBean> empSFIDvalidateList = empPaymentBusiness.validateSFID(empPaymentBean.getSfid());
				if(empSFIDvalidateList.size()>0){
					session.setAttribute("empDetailsList", empSFIDvalidateList);
					
					empPaymentBean = empPaymentBusiness.getDetails(empPaymentBean);
					session.setAttribute("jsonWorkLocation", empPaymentBean.getWorklocation());
					session.setAttribute("jsonRole", empPaymentBean.getRole());
					session.setAttribute(CPSConstants.EMPPAYMENTLIST, empPaymentBusiness.getEmpBasicPayHistory(empPaymentBean.getSfid()));
					empPaymentBean=empPaymentBusiness.getDesignationList(empPaymentBean);
					session.setAttribute("empPayScaleJson", (JSONArray) JSONSerializer.toJSON(empPaymentBean.getEmpPayScaleList()));
					if(CPSUtils.checkList(empPaymentBean.getCatwiseDesig())){
						session.setAttribute("catWiseDesigJson", (JSONArray) JSONSerializer.toJSON(empPaymentBean.getCatwiseDesig()));
						empPaymentBean.setMessage("catWiseDesig");
					}
				}else{
					empPaymentBean.setInvalidSFID("Invalid SFID");
				}
	           
				
				
				
				viewName = CPSConstants.EMPLOYEEPAYMENTENTRYLIST;
				//viewName="EmployeeBasicGradePay";
			
			}




			//code for getting the  basic pay and grade pay




			else if (CPSUtils.compareStrings("empBGPaymentHome", empPaymentBean.getParam())) {
				log.debug("EmployeePaymentController --> onSubmit --> param=empPaymentHome");
				session.removeAttribute(CPSConstants.EMPPAYMENTLIST);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.EMPLOYEEPAYMENTENTRYLIST);
				session.removeAttribute("empDetailsList");
				//viewName = CPSConstants.EMPLOYEEPAYMENTENTRY;
				//viewName = "EmployeeBasicGradePay";
				viewName ="empPaymentSearch";
			}   


			else if(CPSUtils.compareString("basicpaygradepay",empPaymentBean.getParam())){
				log.debug("EmployeePaymentController  --> onSubmit --> param=basicpaygradepaySFIDDetails");
			
				List<EmployeeBean> empSFIDvalidateList = empPaymentBusiness.validateSFID(empPaymentBean.getSfid());
				if(empSFIDvalidateList.size()>0){
                 session.setAttribute("empDetailsList", empSFIDvalidateList);
				empPaymentBean = empPaymentBusiness.getDetails(empPaymentBean);
				session.setAttribute("jsonWorkLocation", empPaymentBean.getWorklocation());
				session.setAttribute("jsonRole", empPaymentBean.getRole());
				session.setAttribute(CPSConstants.EMPPAYMENTLIST, empPaymentBusiness.getEmpBasicPayHistory(empPaymentBean.getSfid()));
				empPaymentBean=empPaymentBusiness.getDesignationList(empPaymentBean);
				session.setAttribute("empPayScaleJson", (JSONArray) JSONSerializer.toJSON(empPaymentBean.getEmpPayScaleList()));
				if(CPSUtils.checkList(empPaymentBean.getCatwiseDesig())){
					session.setAttribute("catWiseDesigJson", (JSONArray) JSONSerializer.toJSON(empPaymentBean.getCatwiseDesig()));
					empPaymentBean.setMessage("catWiseDesig");
				}
				
			}
				else{
					empPaymentBean.setInvalidSFID("Invalid SFID");
				}
				
				
				List<EmployeeBean> empDetailsList = empPaymentBusiness.validateSFID(empPaymentBean.getSfid());
				if(empDetailsList.size()>0){
					session.setAttribute("empDetailsList", empDetailsList);
					//code newly added to disable submit button
				//EmployeePaymentBean empPaymentBean= new EmployeePaymentBean();
					//empPaymentBean.setRefSfid(session.getAttribute(CPSConstants.SFID).toString());
					empPaymentBean.setSfID(empPaymentBean.getSfID());
					empPaymentBean.setSfid(empPaymentBean.getRefSfid());
					empPaymentBean = empPaymentBusiness.getDetails(empPaymentBean);
					session.setAttribute("jsonWorkLocation", empPaymentBean.getWorklocation());
					session.setAttribute("jsonRole", empPaymentBean.getRole());
				
					
					empPaymentBean =empPaymentBusiness.retrieveFamilyCatDesigList(empPaymentBean);
					empPaymentBean.setBasicPay(empPaymentBusiness.getBasicPayValue(empPaymentBean));
					session.setAttribute("empGradePayJson", JSONSerializer.toJSON(empPaymentBean.getEmpPayScaleList()));
					
					
					
					session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,empPaymentBusiness.getOfflineEntryList(empPaymentBean));
					// code for var Inc Val
				//	empPaymentBean.setVarIncVal(empPaymentBusiness.getVariableIncrementValue(empPaymentBean));
					if (CPSUtils.checkList(empPaymentBean.getCatwiseDesig())) {
						session.setAttribute("catWiseDesigJson", JSONSerializer.toJSON(empPaymentBean.getCatwiseDesig()));
					}
				}else{
					
					empPaymentBean.setMessage("invalidSFID");
					session.removeAttribute("catWiseDesigJson");
					session.removeAttribute("empGradePayJson");
				}
				
				
				
				//viewName = CPSConstants.EMPLOYEEPAYMENTENTRYLIST;
				viewName="EmployeeBasicGradePay";
			}
			
			//edit basic pay code for cat
			else if (CPSUtils.compareStrings(CPSConstants.CATEGORYDESIGNATION, empPaymentBean.getParam())) {
				log.debug("EmployeePaymentController --> onSubmit --> param=getCategoryDesignation");
				List<EmployeeBean> empDetailsList = empPaymentBusiness.validateSFID(empPaymentBean.getSfid());
				if(empDetailsList.size()>0){
					session.setAttribute("empDetailsList", empDetailsList);
					//code newly added to disable submit button
				//EmployeePaymentBean empPaymentBean= new EmployeePaymentBean();
					//empPaymentBean.setRefSfid(session.getAttribute(CPSConstants.SFID).toString());
					empPaymentBean.setSfID(empPaymentBean.getSfID());
					empPaymentBean.setSfid(empPaymentBean.getRefSfid());
					empPaymentBean = empPaymentBusiness.getDetails(empPaymentBean);
					session.setAttribute("jsonWorkLocation", empPaymentBean.getWorklocation());
					session.setAttribute("jsonRole", empPaymentBean.getRole());
				
					
					empPaymentBean =empPaymentBusiness.retrieveFamilyCatDesigList(empPaymentBean);
					empPaymentBean.setBasicPay(empPaymentBusiness.getBasicPayValue(empPaymentBean));
					session.setAttribute("empGradePayJson", JSONSerializer.toJSON(empPaymentBean.getEmpPayScaleList()));
					
					
					session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,empPaymentBusiness.getOfflineEntryList(empPaymentBean));
					
					if (CPSUtils.checkList(empPaymentBean.getCatwiseDesig())) {
						session.setAttribute("catWiseDesigJson", JSONSerializer.toJSON(empPaymentBean.getCatwiseDesig()));
					}
				}else{
					
					empPaymentBean.setMessage("invalidSFID");
					session.removeAttribute("catWiseDesigJson");
					session.removeAttribute("empGradePayJson");
				}
				viewName = "PromotionOfflineEntryList";
				//viewName="EmployeeBasicGradePay";
				
			}
			
			
			
			//submit grade pay for employee
			else if (CPSUtils.compareStrings(CPSConstants.MANAGEPROMOTIONOFFLINEENTRY, empPaymentBean.getParam())) {
				log.debug("EmployeePaymentController --> onSubmit --> param=managePromotionOfflineEntry");
				empPaymentBean=empPaymentBusiness.submitOfflineEntry(empPaymentBean);
				String message = empPaymentBean.getResult();

				List<EmployeeBean> empDetailsList = empPaymentBusiness.validateSFID(empPaymentBean.getSfid());
				if(empDetailsList.size()>0){
					session.setAttribute("empDetailsList", empDetailsList);
					empPaymentBean =empPaymentBusiness.retrieveFamilyCatDesigList(empPaymentBean);
					empPaymentBean.setBasicPay(empPaymentBusiness.getBasicPayValue(empPaymentBean));
					session.setAttribute("empGradePayJson", JSONSerializer.toJSON(empPaymentBean.getEmpPayScaleList()));
					session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,empPaymentBusiness.getOfflineEntryList(empPaymentBean));
					if (CPSUtils.checkList(empPaymentBean.getCatwiseDesig())) {
						session.setAttribute("catWiseDesigJson", JSONSerializer.toJSON(empPaymentBean.getCatwiseDesig()));
					}
					empPaymentBean.setResult(message);
				}
				viewName = CPSConstants.PROMOTIONOFFLINEENTRYLIST;
				//viewName="EmployeeBasicGradePay";
			}
			//delete record from grade pay
			
			else if (CPSUtils.compareStrings("deleteRecordGradepay", empPaymentBean.getParam())) {
				log.debug("EmployeePaymentController --> onSubmit --> param=deleteRecord");
				empPaymentBean=empPaymentBusiness.deleteRecordPay(empPaymentBean);
				
				session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,empPaymentBusiness.getOfflineEntryList(empPaymentBean));
				viewName="PromotionOfflineEntryList";
			}
			
			
			//emp var value
			else if(CPSUtils.compareStrings("getVariableIncrementValue", empPaymentBean.getParam())){
				log.debug("EmployeePaymentController --> onSubmit --> param=getVariableIncrementValue");
				empPaymentBean.setVarIncVal(empPaymentBusiness.getVariableIncrementValue(empPaymentBean));
				viewName = "PromotionOfflineEntryList";
				//viewName="EmployeeBasicGradePay";
			}
			
			
			
			
			
			
			else if (CPSUtils.compareStrings(CPSConstants.MANAGE, empPaymentBean.getParam())) {
				log.debug("EmployeePaymentController  --> onSubmit --> param=manage");
				empPaymentBean.setMessage(empPaymentBusiness.insertEmpPaymentDetails(empPaymentBean));
				/*if(CPSUtils.compareStrings(CPSConstants.SUCCESS, empPaymentBean.getMessage())){
					//session.setAttribute(CPSConstants.EMPPAYMENTLIST,empPaymentBusiness.getPaymentDetails());
					session.setAttribute(CPSConstants.EMPPAYMENTLIST,empPaymentBusiness.getEmpBasicPayHistory(empPaymentBean.getSfid()));
				}*/
				session.setAttribute(CPSConstants.EMPPAYMENTLIST,empPaymentBusiness.getEmpBasicPayHistory(empPaymentBean.getSfid()));
				viewName = CPSConstants.EMPLOYEEPAYMENTENTRYLIST;
				//viewName="EmployeeBasicGradePay";
			}else if(CPSUtils.compareStrings("deleteRecord",empPaymentBean.getParam())){
				log.debug("EmployeePaymentController  --> onSubmit --> param=deleteRecord");
				empPaymentBean=empPaymentBusiness.deleteRecord(empPaymentBean);
				session.setAttribute(CPSConstants.EMPPAYMENTLIST,empPaymentBusiness.getEmpBasicPayHistory(empPaymentBean.getSfid()));
				viewName=CPSConstants.EMPLOYEEPAYMENTENTRYLIST;
			}
			mav = new ModelAndView(viewName, CPSConstants.EMPPAYMENT, empPaymentBean);

			if (!CPSUtils.isNullOrEmpty(empPaymentBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, empPaymentBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(empPaymentBean.getResult())) {
				mav.addObject(CPSConstants.RESULT, empPaymentBean.getResult());
			}
			if(!CPSUtils.isNullOrEmpty(empPaymentBean.getInvalidSFID())){
				mav.addObject("invalidSFID", empPaymentBean.getInvalidSFID());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}


	
	
}