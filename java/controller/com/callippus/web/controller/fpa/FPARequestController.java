package com.callippus.web.controller.fpa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.fpa.FPARequestBean;
import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.business.fpa.FPARequestBusiness;
import com.callippus.web.business.requestprocess.FPARequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/fpaRequest.htm")
@SessionAttributes
public class FPARequestController {
private static Log log= LogFactory.getLog(FPARequestController.class);
@Autowired
private FPARequestBusiness fpaRequestBusiness;
@Autowired
private FPARequestProcess fpaRequestProcess;

@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST } )
public ModelAndView execute(@ModelAttribute(CPSConstants.FPAREQUEST) FPARequestBean fpaBean, BindingResult result,HttpServletRequest request,HttpServletResponse response)throws Exception{
	ModelAndView mav=null;
	HttpSession session=null;
	String viewName=null;
	try {
		ErpAction.userChecks(request);
		session = request.getSession(true);

		fpaBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());

		if (CPSUtils.isNullOrEmpty(fpaBean.getParam())) {
			fpaBean.setParam(CPSConstants.FPAHOME);
		} else if (CPSUtils.compareStrings(fpaBean.getParam(), CPSConstants.PAGING)) {
			viewName = session.getAttribute(CPSConstants.RETURN).toString();
		}

		if (CPSUtils.compareStrings(fpaBean.getParam(), CPSConstants.FPAHOME)) {
			log.debug("FPARequestController --> execute --> param=fpaHome");
			fpaBean = fpaRequestBusiness.getFPARequestHomeDetails(fpaBean);
			
			session.setAttribute(CPSConstants.DEPARTMENTID, fpaBean.getDeptId());
			session.setAttribute(CPSConstants.EMPLOYEEDETAILS, fpaBean.getEmployeeDetails());
			session.setAttribute(CPSConstants.PAYMENTDETAILS, fpaBean.getPaymentDetails());
			
			EmployeeBean employeeBean = (EmployeeBean) session.getAttribute(CPSConstants.EMPLOYEEDETAILS);
			EmpPaymentsDTO paymentBean = (EmpPaymentsDTO) session.getAttribute(CPSConstants.PAYMENTDETAILS);
			fpaBean.setPaymentDetails(paymentBean);
			fpaBean.setEmployeeDetails(employeeBean);
			fpaBean = fpaRequestBusiness.checkConstraints(fpaBean);
			 if(fpaBean.getRemarks()!="" && fpaBean.getRemarks()!=null){
				viewName=CPSConstants.FPADETAILSPAGE;
			}
			else{
			viewName=CPSConstants.FPAREQUESTPAGE;
			}
			
		}else if (CPSUtils.compareStrings(fpaBean.getParam(), CPSConstants.SUBMITFPAREQUEST)) {
			log.debug("LoanRequestController --> execute --> param=submitFPARequest");
			// Check the Constraints
			EmployeeBean employeeBean = (EmployeeBean) session.getAttribute(CPSConstants.EMPLOYEEDETAILS);
			EmpPaymentsDTO paymentBean = (EmpPaymentsDTO) session.getAttribute(CPSConstants.PAYMENTDETAILS);
			fpaBean.setPaymentDetails(paymentBean);
			fpaBean.setEmployeeDetails(employeeBean);
			fpaBean = fpaRequestBusiness.checkConstraints(fpaBean);
			if(CPSUtils.compareStrings(fpaBean.getResult(), CPSConstants.SUCCESS)){
				FPARequestProcessBean frb = new FPARequestProcessBean();
				BeanUtils.copyProperties(fpaBean, frb);
				frb.setDesignationID(String.valueOf(employeeBean.getDesignation()));
				frb.setDepartmentID((Integer)session.getAttribute(CPSConstants.DEPARTMENTID));
				frb.setEmployeeType(employeeBean.getEmploymentId());
				frb.setIpAddress(request.getRemoteAddr());
				
				frb.setBasicPay(Float.parseFloat(paymentBean.getBasicPay()));
				frb.setGradePay(Float.parseFloat(paymentBean.getGradePay()));
				frb.setDa(paymentBean.getDa());

				fpaBean.setResult(fpaRequestProcess.initWorkflow(frb));
				fpaBean.setRequestID(frb.getRequestID());
				session.setAttribute("fpaRequestID", fpaBean.getRequestID());
			}
			viewName = CPSConstants.LOANREQUESTRESULTPAGE;
		}
		mav=new ModelAndView(viewName,CPSConstants.FPAREQUEST,fpaBean);
		mav.addObject(CPSConstants.RESULT, fpaBean.getResult());
		mav.addObject(CPSConstants.REMARKS, fpaBean.getRemarks());
	}
	catch(Exception e){
	throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
	}
return mav;
}
}