package com.callippus.web.controller.increment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.callippus.web.beans.increment.AnnualIncrementBean;
import com.callippus.web.business.increment.AnnualIncrementBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/annualIncrement.htm")
@SessionAttributes
public class AnnualIncrementController {
private static Log log= LogFactory.getLog(AnnualIncrementController.class);
@Autowired
private AnnualIncrementBusiness annualIncrementBusiness;

@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST } )
public ModelAndView execute(@ModelAttribute(CPSConstants.INCREMENT) AnnualIncrementBean annualIncrementBean, BindingResult result,HttpServletRequest request,HttpServletResponse response)throws Exception{
	ModelAndView mav=null;
	HttpSession session=null;
	String viewName=null;
	try {
		ErpAction.userChecks(request);
		session = request.getSession(true);
		
		annualIncrementBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());

		if (CPSUtils.isNullOrEmpty(annualIncrementBean.getParam())) {
			annualIncrementBean.setParam(CPSConstants.ANNUALINCREMENTHOME);
		} else if (CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.PAGING)) {
			log.debug("AnnualIncrementController --> onSubmit --> param=paging");
			viewName = session.getAttribute(CPSConstants.RETURN).toString();
		}

		if (CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.ANNUALINCREMENTHOME)) {
			log.debug("AnnualIncrementController --> onSubmit --> param=annualIncrementHome");
			annualIncrementBean=annualIncrementBusiness.getEmpDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getTypeMasterDetails(annualIncrementBean);
			session.setAttribute(CPSConstants.TYPE, CPSConstants.INCREMENT);
			viewName=CPSConstants.ANNUALINCREMENTHOMEPAGE;
		}else if (CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.SUBMITANNUALINCREMENTS)) {
			log.debug("AnnualIncrementController --> onSubmit --> param=submitAnnualIncrements");
			annualIncrementBean=annualIncrementBusiness.submitIncrementDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getEmpDetails(annualIncrementBean);
			viewName=CPSConstants.ANNUALINCREMENTLIST;
		}else if (CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.DOPARTLIST)) {
			log.debug("AnnualIncrementController --> onSubmit --> param=doPartList");
			annualIncrementBean=annualIncrementBusiness.getTypeMasterDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getDoPartList(annualIncrementBean);
			//annualIncrementBean=annualIncrementBusiness.getIncrementCasualitiesList(annualIncrementBean);
			viewName=CPSConstants.DOPARTPAGE;
		}
		else if (CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.CASUALITYLIST)) {
			log.debug("AnnualIncrementController --> onSubmit --> param=doPartList");
			annualIncrementBean=annualIncrementBusiness.getTypeMasterDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getDoPartList(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getIncrementCasualitiesList(annualIncrementBean);
			session.setAttribute(CPSConstants.CASUALITIESLIST, annualIncrementBean.getCasualitiesList());
			viewName=CPSConstants.DOPARTPAGE;
		}else if (CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.EMPLOYEELIST)) {
			annualIncrementBean=annualIncrementBusiness.getEmpDetails(annualIncrementBean);
			viewName=CPSConstants.ANNUALINCREMENTLIST;
		}else if(CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.ANNUALINCREMENTFINANCE)){
			annualIncrementBean=annualIncrementBusiness.getAnnualIncrementDetailsInFinance(annualIncrementBean);
			//annualIncrementBean=annualIncrementBusiness.getEmpIncrementDoPartDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getPaybillRunmonth(annualIncrementBean);
			viewName=CPSConstants.ANNUALINCREMENTFINANCE;
		}else if(CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.SUBMITANNUALINCREMENTSTOPAY)){
			log.debug("AnnualIncrementController --> onSubmit --> param=submitIncrementsToPay");
			annualIncrementBean=annualIncrementBusiness.submitIncrementToPayDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getEmpIncrementDoPartDetails(annualIncrementBean);
			viewName=CPSConstants.ANNUALINCREMENTLIST;
		}
		// code for new screen in annual details
		else if(CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.ANNUALINCREMENTFINALDETAILS)){
			annualIncrementBean=annualIncrementBusiness.getAnnualIncrementBasicPayIdDetails(annualIncrementBean);
			viewName="annualIncrementFinalDetailsScreen";
		}else if(CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.EMPLOYEEFINALDETAILSLIST)){
			annualIncrementBean=annualIncrementBusiness.getEmpNullDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getEmpNotNullDetails(annualIncrementBean);
			viewName ="annualIncrementFinalDetailsScreenList";
		}else if(CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.UPDATEANNUALINCREMENTDETAILS)){
			annualIncrementBean=annualIncrementBusiness.getUpdateAnnualIncrementDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getEmpNullDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getEmpNotNullDetails(annualIncrementBean);
			viewName ="annualIncrementFinalDetailsScreenList";
		}else if(CPSUtils.compareStrings(annualIncrementBean.getParam(), CPSConstants.EMPLOYEEINTEGRATEDPAYINCREMENT)){
			annualIncrementBean=annualIncrementBusiness.getEmpIncrementDoPartDetails(annualIncrementBean);
			annualIncrementBean=annualIncrementBusiness.getPaybillRunmonth(annualIncrementBean);
			viewName=CPSConstants.ANNUALINCREMENTLIST;
		}
		mav=new ModelAndView(viewName,CPSConstants.INCREMENT,annualIncrementBean);
		if(!CPSUtils.isNullOrEmpty(annualIncrementBean.getResult())){
		mav.addObject(CPSConstants.RESULT, annualIncrementBean.getResult());
		}
		if(!CPSUtils.isNullOrEmpty(annualIncrementBean.getRemarks())){
		mav.addObject(CPSConstants.REMARKS, annualIncrementBean.getRemarks());
		}
	}
	catch(Exception e){
	throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
	}
return mav;

}
}