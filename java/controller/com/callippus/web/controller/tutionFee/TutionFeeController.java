package com.callippus.web.controller.tutionFee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.callippus.web.beans.tutionFee.TuitionFeeAcedemicYearDTO;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeLimitMasterDTO;
import com.callippus.web.business.configuration.ConfigurationBusiness;
import com.callippus.web.business.incometax.IncomeTaxBusiness;
import com.callippus.web.business.tutionFee.TutionFeeBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.tutionFee.TutionFeeRequestProcess;
@Controller
@RequestMapping("/tutionFee.htm")
@SessionAttributes
public class TutionFeeController {
	private static Log log = LogFactory.getLog(TutionFeeController.class);
	@Autowired
	private TutionFeeBusiness tutionFeeBusiness;
	@Autowired
	private TutionFeeRequestProcess tutionFeeRequestProcess;
	@Autowired
	private ConfigurationBusiness configurationBusiness;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.TUTIONFEE) TutionFeeBean tutionFeeBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
 		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			tutionFeeBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
            if (CPSUtils.compareStrings(CPSConstants.PAGING, tutionFeeBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}if(CPSUtils.compareStrings("tutionFeeRequestFormHome", tutionFeeBean.getParam())){
				tutionFeeBean=tutionFeeBusiness.getFamilyDetails(tutionFeeBean);
			    session.setAttribute("familyDetailsList", tutionFeeBean.getFamilyList());
				session.setAttribute("academicYearList", tutionFeeBusiness.getTutionFeeAcademicYearList(tutionFeeBean));
			    session.setAttribute("academicTypeList", tutionFeeBusiness.getTutionFeeLimitMasterDetails());
			    tutionFeeBean.setYearList(tutionFeeBusiness.getYearDetails(tutionFeeBean));
				viewName = CPSConstants.TUTIONFEEREQUESTDETAILS;
			}else if(CPSUtils.compareStrings("submitTFRequestDetails", tutionFeeBean.getParam())){
				tutionFeeBean.setIpAddress(request.getRemoteAddr());
				tutionFeeBean.setMessage(tutionFeeBusiness.checkTutionFeeApplicantDetails(tutionFeeBean));
			  if(CPSUtils.compareStrings(CPSConstants.SUCCESS, tutionFeeBean.getMessage())){
				tutionFeeBean.setMessage(tutionFeeRequestProcess.initWorkflow(tutionFeeBean));
				session.setAttribute("tutionFeeReID", tutionFeeBean.getRequestID());				

				}else{
					tutionFeeBean.setRemarks(tutionFeeBean.getMessage());
					tutionFeeBean.setMessage(CPSConstants.TUTIONFEEREQUESTFAILED);
				}
				tutionFeeBean=tutionFeeBusiness.getFamilyDetails(tutionFeeBean);
				session.setAttribute("familyDetailsList", tutionFeeBean.getFamilyList());
				viewName = "tuitionFeeRequestDetailsList";
			}else if(CPSUtils.compareStrings("tutionFeeFinanceHome", tutionFeeBean.getParam())){
				session.setAttribute("claimTypeMasterList",tutionFeeBusiness.getClaimTypeMasterDetails());
				viewName = CPSConstants.CLAIMFINANCEDETAILS;
			}else if(CPSUtils.compareStrings(CPSConstants.GETCLAIMFINANCEDETAILS, tutionFeeBean.getParam())){
				Map<String,List<TutionFeeBean>> keyMap=tutionFeeBusiness.getTfClaimFinanceDetails(Integer.parseInt(tutionFeeBean.getClaimType()),tutionFeeBean);
				tutionFeeBean.setOfficersList(keyMap.get("officers"));
				tutionFeeBean.setStaffList(keyMap.get("staff"));
				session.setAttribute("accountOfficerList", tutionFeeBean.getAccountOfficerList());
				session.setAttribute("cfaOfficerList", tutionFeeBean.getCfaOfficerList());
				int officersTotal =0;
				int staffTotal=0;
				for(TutionFeeBean obj:tutionFeeBean.getOfficersList()){
					officersTotal+=Integer.parseInt(obj.getSanctionedAmount());
				}
				for(TutionFeeBean obj:tutionFeeBean.getStaffList()){
					staffTotal+=Integer.parseInt(obj.getSanctionedAmount());
				}
				session.setAttribute("officersTotal", officersTotal);
				session.setAttribute("staffTotal", staffTotal);
				session.setAttribute(CPSConstants.RETURN, "ClaimFinanceDetailsHome");
				viewName = "ClaimFinanceDetailsHome";
			}else if(CPSUtils.compareStrings(CPSConstants.SAVECDAFINANCEDETAILS, tutionFeeBean.getParam())){
				tutionFeeBean.setMessage(tutionFeeBusiness.saveCDAFinanceDetails(tutionFeeBean));
				Map<String,List<TutionFeeBean>> keyMap=tutionFeeBusiness.getTfClaimFinanceDetails(tutionFeeBean.getClaimTypeId(),tutionFeeBean);
				tutionFeeBean.setOfficersList(keyMap.get("officers"));
				tutionFeeBean.setStaffList(keyMap.get("staff"));
				session.setAttribute("accountOfficerList", tutionFeeBean.getAccountOfficerList());
				session.setAttribute("cfaOfficerList", tutionFeeBean.getCfaOfficerList());
				int officersTotal =0;
				int staffTotal=0;
				for(TutionFeeBean obj:tutionFeeBean.getOfficersList()){
					officersTotal+=Integer.parseInt(obj.getSanctionedAmount());
				}
				for(TutionFeeBean obj:tutionFeeBean.getStaffList()){
					staffTotal+=Integer.parseInt(obj.getSanctionedAmount());
				}
				session.setAttribute("officersTotal", officersTotal);
				session.setAttribute("staffTotal", staffTotal);
				//viewName=CPSConstants.RESULT;
				viewName="ClaimFinanceDetailsHome";
			}else if(CPSUtils.compareStrings(CPSConstants.TUITIONFEESENDTOCDAHOME, tutionFeeBean.getParam())){
				session.setAttribute("claimTypeMasterList",tutionFeeBusiness.getClaimTypeMasterDetails());
				viewName=CPSConstants.CLAIMCDADETAILS;
			}else if(CPSUtils.compareStrings(CPSConstants.GETCLAIMCDADETAILS, tutionFeeBean.getParam())){
				tutionFeeBean.setBillNo(tutionFeeBusiness.setBillNumberInSendToCDA(Integer.parseInt(tutionFeeBean.getClaimType())));
				viewName="ClaimCdaDetailsHome";
			}else if(CPSUtils.compareStrings(CPSConstants.GETTUITIONFEECDALIST, tutionFeeBean.getParam())){
				tutionFeeBean.setTfClaimFinanceDetails(tutionFeeBusiness.getTuitionFeeSendToCDAList(tutionFeeBean.getBillNo(),Integer.parseInt(tutionFeeBean.getClaimType()), tutionFeeBean));
				if(tutionFeeBean.getTfClaimFinanceDetails().size()==0){
					tutionFeeBean.setMessage("billNumber");
				}
				session.setAttribute("tfCDAList", tutionFeeBean.getTfClaimFinanceDetails());
				int total=0;
				for(TutionFeeBean obj:tutionFeeBean.getTfClaimFinanceDetails()){
					total+=Integer.parseInt(obj.getSanctionedAmount());
				}
				session.setAttribute("cdaTotal", total);
				viewName=CPSConstants.TUITIONFEESENDTOCDALIST;
			}else if(CPSUtils.compareStrings(CPSConstants.TUITIONFEECDAAMOUNT, tutionFeeBean.getParam())){
				tutionFeeBean.setMessage(tutionFeeBusiness.saveCDADetails(tutionFeeBean));
				tutionFeeBean.setTfClaimFinanceDetails(tutionFeeBusiness.getTuitionFeeSendToCDAList(tutionFeeBean.getBillNo(),Integer.parseInt(tutionFeeBean.getClaimType()),tutionFeeBean));
				session.setAttribute("tfCDAList", tutionFeeBean.getTfClaimFinanceDetails());
				int total=0;
				for(TutionFeeBean obj:tutionFeeBean.getTfClaimFinanceDetails()){
					total+=Integer.parseInt(obj.getSanctionedAmount());
				}
				session.setAttribute("cdaTotal", total);
				viewName=CPSConstants.TUITIONFEESENDTOCDALIST;
			}
			//Tuition fee Claim master 
			else if(CPSUtils.compareStrings(CPSConstants.TUITIONFEECLAIMMASTER, tutionFeeBean.getParam())){
				//List<TutionFeeClaimMasterDTO> list=tutionFeeBusiness.getTuitionFeeClaimMasterDetails();// previously list has added to the tuitionfeebean
				//tutionFeeBean.setClaimList(list);
				session.setAttribute("claimTypeMasterList",tutionFeeBusiness.getClaimTypeMasterDetails());
				session.setAttribute("claimList", tutionFeeBusiness.getTuitionFeeClaimMasterDetails());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.TUITIONFEECLAIMMASTERLIST);
				viewName=CPSConstants.TUITIONFEECLAIMMASTER;
			}else if(CPSUtils.compareStrings(CPSConstants.SUBMITTFCLAIMNAME, tutionFeeBean.getParam())){
				tutionFeeBean.setMessage(tutionFeeBusiness.submitTuitionFeeClaimDetails(tutionFeeBean));
				session.setAttribute("claimList", tutionFeeBusiness.getTuitionFeeClaimMasterDetails());
				viewName=CPSConstants.TUITIONFEECLAIMMASTERLIST;
			}else if(CPSUtils.compareStrings(CPSConstants.TUITIONFEEDELETECLAIMMASTER, tutionFeeBean.getParam())){
				tutionFeeBean.setMessage(tutionFeeBusiness.deleteTuitionFeeClaimDetails(Integer.parseInt(tutionFeeBean.getPk())));
				session.setAttribute("claimList", tutionFeeBusiness.getTuitionFeeClaimMasterDetails());
				viewName=CPSConstants.TUITIONFEECLAIMMASTERLIST;
				}
           //tuition fee academic year master		
			else if(CPSUtils.compareStrings(CPSConstants.TUTIONFEEACADEMICYEARMASTER, tutionFeeBean.getParam())){
				//List<TuitionFeeAcedemicYearDTO> list=tutionFeeBusiness.getTutionFeeAcademicYearDetails();// previously list has added to the tuitionfeebean
				//tutionFeeBean.setClassNameList(list);
				session.setAttribute("classNameList", tutionFeeBusiness.getTutionFeeAcademicYearDetails());
				session.setAttribute(CPSConstants.RETURN, "TutionFeeAcademicYearMasterList");
				viewName="TutionFeeAcademicYearMaster";
			}else if(CPSUtils.compareStrings(CPSConstants.SUBMITTFACLASSNAME, tutionFeeBean.getParam())){
				tutionFeeBean.setMessage(tutionFeeBusiness.submitTutionFeeAcademicYearMaster(tutionFeeBean));
			    session.setAttribute("classNameList", tutionFeeBusiness.getTutionFeeAcademicYearDetails());
				viewName="TutionFeeAcademicYearMasterList";
			} else if(CPSUtils.compareStrings(CPSConstants.DELETETUTIONFEEACADEMICMASTER, tutionFeeBean.getParam())){
				tutionFeeBean.setMessage(tutionFeeBusiness.deleteTutionFeeAcademicYearMasterDetails(Integer.parseInt(tutionFeeBean.getPk())));
				session.setAttribute("classNameList", tutionFeeBusiness.getTutionFeeAcademicYearDetails());
				viewName="TutionFeeAcademicYearMasterList";
			}
			//Tution fee Configuration
			 else if(CPSUtils.compareStrings(CPSConstants.TUITIONFEECONFIGURATION, tutionFeeBean.getParam())){
				tutionFeeBean.setConfBean(configurationBusiness.getConfigurationDetails());
				tutionFeeBean.setTutionFeeMaxLimitPerOneChild(tutionFeeBean.getConfBean().getTutionFeeMaxLimitPerOneChild());
				tutionFeeBean.setTutionFeeMaxChildToBeClaimed(tutionFeeBean.getConfBean().getTutionFeeMaxChildToBeClaimed());
				viewName="TuitionFeeConfiguration"; 
			 } else if (CPSUtils.compareStrings(CPSConstants.SAVETUITIONFEECONFIGURATION, tutionFeeBean.getParam())) {
				 tutionFeeBean.setMessage(configurationBusiness.submitConfigurationDetails(tutionFeeBean.getConfigurationDetails()));
				 viewName = CPSConstants.RESULT;
			 }
			//Tution fee limit master
			 else if(CPSUtils.compareStrings(CPSConstants.TUITIONFEELIMITMASTER, tutionFeeBean.getParam())){
				List<TutionFeeLimitMasterDTO> list= tutionFeeBusiness.getTutionFeeLimitMasterDetails();
				tutionFeeBean.setAcademicTypeList(list);
				viewName="TutionFeeLimitMaster";
			 }
			 else if(CPSUtils.compareStrings(CPSConstants.SUBMITACADEMICYEAR, tutionFeeBean.getParam())){
				 tutionFeeBean.setMessage(tutionFeeBusiness.submitTutionFeeLimitMaster(tutionFeeBean));
				 List<TutionFeeLimitMasterDTO> list= tutionFeeBusiness.getTutionFeeLimitMasterDetails();
				 tutionFeeBean.setAcademicTypeList(list);
				 viewName="tutionFeeLimitMasterList";
			 }
			 else if(CPSUtils.compareStrings(CPSConstants.DELETETUTIONFEELIMITMASTER, tutionFeeBean.getParam())){
				tutionFeeBean.setMessage(tutionFeeBusiness.deleteTutionFeeLimitMasterDetails(Integer.parseInt(tutionFeeBean.getPk())));
				List<TutionFeeLimitMasterDTO> list= tutionFeeBusiness.getTutionFeeLimitMasterDetails();
				tutionFeeBean.setAcademicTypeList(list);
				viewName="tutionFeeLimitMasterList";
			 }
			 //tuition fee claims amendement details
			 else if(CPSUtils.compareStrings(CPSConstants.CLAIMSAMENDEMENTDETAILSPARAM, tutionFeeBean.getParam())){
				 session.setAttribute("tutionFeeAppliedDetails", tutionFeeBusiness.checkingTuitionFeeAppliedDetails(tutionFeeBean));
				 session.setAttribute("telephoneBillAppliedDetails", tutionFeeBusiness.telephoneBillAppliedDetails(tutionFeeBean));
				 viewName="TutionFeeTelephoneClaimsAmendement";
			 }else if(CPSUtils.compareStrings(CPSConstants.CHECKTUITIONFEEAPPLIEDYEARDETAILS, tutionFeeBean.getParam())){
				 tutionFeeBean=tutionFeeBusiness.setTutionFeeRequestDetails(tutionFeeBean);
				 viewName="tutionFeeRequestDetailsList";
			 }else if(CPSUtils.compareStrings(CPSConstants.FINANCECLAIMSAMENDEMENTDETAILSPARAM, tutionFeeBean.getParam())){
				 session.setAttribute("tutionFeeAppliedDetails", tutionFeeBusiness.checkingTuitionFeeAppliedDetails(tutionFeeBean));
				 session.setAttribute("telephoneBillAppliedDetails", tutionFeeBusiness.telephoneBillAppliedDetails(tutionFeeBean));
				 viewName="TutionFeeTelephoneClaimsAmendement";
			 }
			mav = new ModelAndView(viewName, CPSConstants.TUTIONFEE, tutionFeeBean);
			if (!CPSUtils.isNullOrEmpty(tutionFeeBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, tutionFeeBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(tutionFeeBean.getRemarks())) {
				mav.addObject(CPSConstants.REMARKS, tutionFeeBean.getRemarks());
			}
	
      }catch (Exception e) {
    	  e.printStackTrace();
    	  throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
	}
      return mav;
  }
}
