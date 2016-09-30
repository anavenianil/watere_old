package com.callippus.web.promotions.controller.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JSONString;
import net.sf.json.util.JSONStringer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.beans.request.PromotionRequestBean;
import com.callippus.web.promotions.business.management.PromotionManagementBusiness;
import com.callippus.web.promotions.business.request.PromotionRequestBusiness;
import com.callippus.web.promotions.dto.AssessmentDetailsDTO;

@Controller

@SessionAttributes
public class PromotionManagementController {
	private static Log log = LogFactory.getLog(PromotionManagementController.class);
	@Autowired
	private PromotionManagementBusiness promotionManagementBusiness;
	
	@Autowired
	private PromotionRequestBusiness promotionRequestBusiness;
	@Autowired
	private EmployeePaymentBusiness empPaymentBusiness;
	@RequestMapping(value="/promotion.htm",method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute PromotionManagementBean promotionBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
 		HttpSession session = null;
		String viewName = null;
		
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			promotionBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(promotionBean.getParam())) {
				promotionBean.setParam(CPSConstants.HOME);
			} else if (CPSUtils.compareStrings(promotionBean.getParam(), CPSConstants.PAGING)) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
				if(CPSUtils.isNullOrEmpty(viewName))
			         viewName = request.getAttribute(CPSConstants.RETURN).toString();
			}

			/**
			 * Residency period master start
			 */
			if (CPSUtils.compareStrings(CPSConstants.HOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=home");
				promotionBean = promotionManagementBusiness.getResidencyHomeDetails(promotionBean);
				session.removeAttribute("masterDataList");
				session.setAttribute(CPSConstants.RESIDENCYPERIODLIST, promotionManagementBusiness.getResidencyPeriodList(promotionBean));
				session.setAttribute(CPSConstants.DESIGNATIONLISTJSON,JSONSerializer.toJSON(promotionBean.getDesignationList()));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.RESIDENCYPERIODLISTPAGE);
				session.setAttribute(CPSConstants.TOTALDESIGNATIONLISTJSON,(JSONArray)JSONSerializer.toJSON(promotionBean.getDesignationList()));
				viewName = CPSConstants.RESIDENCYPERIODMASTER;
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGERESIDENCY, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=manageResidency");
				promotionBean.setResult(promotionManagementBusiness.manageResidencyPeriodDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, promotionBean.getResult())) {
		
					session.setAttribute(CPSConstants.RESIDENCYPERIODLIST, promotionManagementBusiness.getResidencyPeriodList(promotionBean));
				}
				
				viewName = CPSConstants.RESIDENCYPERIODLISTPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETERESIDENCY, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteResidency");
				promotionBean.setResult(promotionManagementBusiness.deleteResidencyPeriodDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.RESIDENCYPERIODLIST, promotionManagementBusiness.getResidencyPeriodList(promotionBean));
				}
				viewName = CPSConstants.RESIDENCYPERIODLISTPAGE;
			}
			else if (CPSUtils.compareStrings(CPSConstants.RESIDENCYPERIODLIST, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=residencyPeriodList");
				promotionBean.setResidencyPeriodList(promotionManagementBusiness.getResidencyPeriodList(promotionBean));
				session.setAttribute(CPSConstants.RESIDENCYPERIODLIST, promotionBean.getResidencyPeriodList());
				session.setAttribute("residencyListJSON", JSONSerializer.toJSON(promotionBean.getResidencyPeriodList()));
				viewName = CPSConstants.RESIDENCYPERIODLISTPAGE;
			}
			/**
			 * Residency period master end
			 */
			
			else if (CPSUtils.compareStrings(CPSConstants.BOARDINFO, promotionBean.getParam())){
				log.debug("PromotionManagementController --> onSubmit --> param=home");
				session.setAttribute(CPSConstants.BOARDINFOLIST, promotionManagementBusiness.getBoardTypeInfoList(promotionBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.BOARDINFOLIST);
				
				viewName = CPSConstants.BOARDMASTER;
			}else if (CPSUtils.compareStrings(CPSConstants.BOARDINFORMATION, promotionBean.getParam())){
				log.debug("PromotionManagementController --> onSubmit --> param=boardInformation");	
		     	promotionBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
		     	
		     	
				promotionBean.setResult(promotionManagementBusiness.storeBoardTypeInfo(promotionBean));
					promotionBean.setAssessmentCategoryList(promotionManagementBusiness.getBoardTypeInfoList(promotionBean));
					session.setAttribute(CPSConstants.BOARDINFOLIST,promotionBean.getAssessmentCategoryList());
					session.setAttribute("boardInfoListJSON", (JSONArray)JSONSerializer.toJSON(promotionBean.getAssessmentCategoryList()));
					session.setAttribute(CPSConstants.RETURN, CPSConstants.BOARDINFOLIST);
				viewName=CPSConstants.BOARDINFOLISTPAGE;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETEBOARDTYPE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteBoardType");
				promotionBean.setResult(promotionManagementBusiness.deleteBoardTypeInfo(promotionBean));
				//message1=promotionManagementBusiness.deleteBoardTypeInfo(promotionBean);
//				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, promotionBean.getResult())) {
//					//promotionBean.setAssessmentCategoryList(promotionManagementBusiness.getBoardTypeInfoList(promotionBean));
//				}else if(CPSUtils.compareStrings(CPSConstants.FAILED, promotionBean.getResult())){
//					
//				}
				promotionBean.setAssessmentCategoryList(promotionManagementBusiness.getBoardTypeInfoList(promotionBean));
				session.setAttribute(CPSConstants.BOARDINFOLIST, promotionBean.getAssessmentCategoryList());
				session.setAttribute("boardInfoListJSON", (JSONArray)JSONSerializer.toJSON(promotionBean.getAssessmentCategoryList()));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.BOARDINFOLIST);
				viewName = CPSConstants.BOARDINFOLISTPAGE;
			}
			/**
			 * Board Type Information end
			 */

			/**
			 * Venue Details Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.VENUEHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=venueHome");
				promotionBean = promotionManagementBusiness.getVenueHomeDetails(promotionBean);
				viewName = CPSConstants.PROMOTIONVENUEDETAILS;
				session.setAttribute(CPSConstants.VENUELIST, promotionManagementBusiness.getVenueList(promotionBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONVENUEDETAILSLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEVENUE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=manageVenue");
				promotionBean.setResult(promotionManagementBusiness.manageVenueDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.VENUELIST, promotionManagementBusiness.getVenueList(promotionBean));
				}
				viewName = CPSConstants.PROMOTIONVENUEDETAILSLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEVENUE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteVenue");
				promotionBean.setResult(promotionManagementBusiness.deleteVenueDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.VENUELIST, promotionManagementBusiness.getVenueList(promotionBean));
				}
				viewName = CPSConstants.PROMOTIONVENUEDETAILSLIST;
			}
			else if (CPSUtils.compareStrings(CPSConstants.PROMOTIONVENUEDETAILSLIST, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=PromotionVenueDetailsList");
					session.setAttribute(CPSConstants.VENUELIST, promotionManagementBusiness.getVenueList(promotionBean));
				viewName = CPSConstants.PROMOTIONVENUEDETAILSLIST;
			}
			/**
			 * Venue Details ends
			 */

			/**
			 * Add employee for assessment starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.ADDASSESSMENTHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=addAssessmentHome");
				promotionBean = promotionManagementBusiness.getAddAssessmentHomeDetails(promotionBean);
				viewName = CPSConstants.ADDASSESSMENT;
				session.setAttribute(CPSConstants.ADDASSESSMENTLIST, promotionManagementBusiness.getAddAssessmentEmpList(promotionBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ADDASSESSMENTLISTPAGE);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEEXPEMP, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=manageExpEmp");
				promotionBean.setResult(promotionManagementBusiness.manageExceptionEmpDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.ADDASSESSMENTLIST, promotionManagementBusiness.getAddAssessmentEmpList(promotionBean));
				}
				viewName = CPSConstants.ADDASSESSMENTLISTPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEEXPEMP, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteExpEmp");
				promotionBean.setResult(promotionManagementBusiness.deleteExceptionEmpDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.ADDASSESSMENTLIST, promotionManagementBusiness.getAddAssessmentEmpList(promotionBean));
				}
				viewName = CPSConstants.ADDASSESSMENTLISTPAGE;
			}
			/**
			 * Add employee for assessment ends
			 */

			/**
			 * Local Assessment Board start
			 */
			else if (CPSUtils.compareStrings(CPSConstants.LOCALBOARDHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=localBoardHome");
				promotionBean = promotionManagementBusiness.getLocalBoardHomeDetails(promotionBean);
				viewName = CPSConstants.LOCALASSESSMENTBOARD;
				session.setAttribute("membersListJSON", JSONSerializer.toJSON(promotionBean.getLocalBoardMembersList()));
				session.setAttribute(CPSConstants.LOCALASSESSMENTBOARDLIST, promotionManagementBusiness.getLocalAssessmentBoardList(promotionBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOCALASSESSMENTBOARDLISTPAGE);
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGELOCALBOARD, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=manageLocalBoard");
				promotionBean.setResult(promotionManagementBusiness.manageLocalBoardDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.LOCALASSESSMENTBOARDLIST, promotionManagementBusiness.getLocalAssessmentBoardList(promotionBean));
				}
				viewName = CPSConstants.LOCALASSESSMENTBOARDLISTPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETELOCALBOARD, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteLocalBoard");
				promotionBean.setResult(promotionManagementBusiness.deleteLocalBoardDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.LOCALASSESSMENTBOARDLIST, promotionManagementBusiness.getLocalAssessmentBoardList(promotionBean));
				}
				viewName = CPSConstants.LOCALASSESSMENTBOARDLISTPAGE;
			}
			else if (CPSUtils.compareStrings(CPSConstants.LOCALASSESSMENTBOARDLIST, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=localAssessmentBoardList");
				session.setAttribute(CPSConstants.LOCALASSESSMENTBOARDLIST, promotionManagementBusiness.getLocalAssessmentBoardList(promotionBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.LOCALASSESSMENTBOARDLISTPAGE);
				viewName = CPSConstants.LOCALASSESSMENTBOARDLISTPAGE;
			}
			/**
			 * Local Assessment Board end
			 */

			/**
			 * Desig Experience Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.DESIGEXPERIENCEHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=desigExperienceHome");
				promotionBean = promotionManagementBusiness.getDesigExperienceHomeDetails(promotionBean);
				session.setAttribute(CPSConstants.DESIGEXPERIENCELIST, promotionManagementBusiness.getDesigExperienceList(promotionBean));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DESIGEXPERIENCELISTPAGE);
				viewName = CPSConstants.DESIGEXPERIENCE;
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEDESIGEXPERIENCE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=manageDesigExperience");
				promotionBean.setResult(promotionManagementBusiness.manageDesigExperienceDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.DESIGEXPERIENCELIST, promotionManagementBusiness.getDesigExperienceList(promotionBean));
				}
				viewName = CPSConstants.DESIGEXPERIENCELISTPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEDESIGEXPERIENCE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteDesigExperience");
				promotionBean.setResult(promotionManagementBusiness.deleteDesigExperienceDetails(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.DESIGEXPERIENCELIST, promotionManagementBusiness.getDesigExperienceList(promotionBean));
				}
				viewName = CPSConstants.DESIGEXPERIENCELISTPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.CHECKDESIGNATION, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=checkDesignation");
				promotionBean.setResult(promotionManagementBusiness.retrieveDesigName(promotionBean));
				if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.SUCCESS)) {
					promotionBean.setResult(CPSConstants.EMPEXITS);
					session.setAttribute("employeeDetails", promotionBean.getEmpDetails());
				} else if (CPSUtils.compareStrings(promotionBean.getResult(), CPSConstants.INVALID)) {
					promotionBean.setResult(CPSConstants.EMPNOTEXISTS);
				}
				viewName = "NameDesignation";
			}
			/**
			 * Desig Experience end
			 */

			/**
			 * Head Quarters Sending starts
			 */

			else if (CPSUtils.compareStrings(CPSConstants.HEADQUARTERSEND, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=headQuarterSend");				
				promotionBean = promotionManagementBusiness.getQualifiedCandidatesHome(promotionBean);
			    
				session.setAttribute("designationListJSON", JSONSerializer.toJSON(promotionBean.getDesignationList()));
				viewName = CPSConstants.HEADQUARTERPROMOTION;
			}

			else if (CPSUtils.compareStrings(CPSConstants.GETHQCANDIDATES, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=getHQCandidates");
				promotionBean = promotionManagementBusiness.getHQCandidates(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTCATEGORYID, promotionBean.getAssessmentCategoryID());
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.HEADQUARTERPROMOTIONLIST);
				session.setAttribute(CPSConstants.DISCIPLINELIST,promotionBean.getDisciplineDetails());
				session.setAttribute(CPSConstants.SUBDISCIPLINELIST,promotionBean.getDisciplineList());
				viewName = CPSConstants.HEADQUARTERPROMOTIONLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITHQCANDIDATES, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=submitHQCandidates");
				promotionBean.setIpAddress(request.getRemoteAddr());
				promotionBean = promotionManagementBusiness.submitQualifiedCandidates(promotionBean);
				promotionBean = promotionManagementBusiness.getHQCandidates(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				viewName = CPSConstants.HEADQUARTERPROMOTIONLIST;
			}
			/**
			 * Head Quarters Sending end
			 */

			/**
			 * Qualified Candidates for assessment starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.QUALIFIEDCANDIDATESHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=qualifiedCandidates");
				promotionBean.setType(CPSConstants.QUALIFIEDCANDIDATES);
				promotionBean = promotionManagementBusiness.getQualifiedCandidatesHome(promotionBean);
				session.setAttribute("boardMasterJSON", (JSONArray)JSONSerializer.toJSON(promotionBean.getBoardMasterList()));
				session.setAttribute("venueMasterJSON", (JSONArray)JSONSerializer.toJSON(promotionBean.getVenueList()));
				session.setAttribute("designationListJSON", (JSONArray)JSONSerializer.toJSON(promotionBean.getDesignationList()));
				viewName = CPSConstants.QUALIFIEDCANDIDATES;
			} else if (CPSUtils.compareStrings(CPSConstants.GETQUALIFIEDCANDIDATES, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=getQualifiedCandidates");
				promotionBean.setType(CPSConstants.QUALIFIEDCANDIDATES);
				promotionBean = promotionManagementBusiness.getQualifiedCandidates(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTCATEGORYID, promotionBean.getAssessmentCategoryID());
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				if(!CPSUtils.isNullOrEmpty(promotionBean.getResidencyPeriodList())){
				session.setAttribute(CPSConstants.RESIDENCYPERIODLIST,(JSONArray)JSONSerializer.toJSON( promotionBean.getResidencyPeriodList()));
				}session.setAttribute(CPSConstants.DISCIPLINELIST,promotionBean.getDisciplineDetails());
				session.setAttribute(CPSConstants.SUBDISCIPLINELIST,promotionBean.getDisciplineList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.QUALIFIEDCANDIDATESLIST);
				
				viewName = CPSConstants.QUALIFIEDCANDIDATESLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITQUALIFIEDCANDIDATES, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=submitQualifiedCandidates");
				promotionBean.setIpAddress(request.getRemoteAddr());
				promotionBean.setType(CPSConstants.QUALIFIEDCANDIDATES);
				promotionBean = promotionManagementBusiness.submitQualifiedCandidates(promotionBean);
				promotionBean = promotionManagementBusiness.getQualifiedCandidates(promotionBean);
				session.setAttribute(CPSConstants.RESIDENCYPERIODLIST,(JSONArray)JSONSerializer.toJSON( promotionBean.getResidencyPeriodList()));
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				viewName = CPSConstants.QUALIFIEDCANDIDATESLIST;
			}

			/**
			 * Qualified Candidates for assessment ends
			 */
			/**
			 * Promoted Candidates Home starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PROMOTEDCANDIDATESHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=promotedCandidatesHome");
				
				
				promotionBean = promotionManagementBusiness.getQualifiedCandidatesHome(promotionBean);
				session.setAttribute("designationListJSON", (JSONArray)JSONSerializer.toJSON(promotionBean.getDesignationList()));
				
				viewName = CPSConstants.PROMOTEDCANDIDATES;
			}else if (CPSUtils.compareStrings(CPSConstants.GETPROMOTEDCANDIDATES, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=getPromotedCandidates");
				promotionBean = promotionManagementBusiness.getPromotedCandidates(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTCATEGORYID, promotionBean.getAssessmentCategoryID());
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTEDCANDIDATESLIST);
				viewName = CPSConstants.PROMOTEDCANDIDATESLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITPROMOTEDCANDIDATES, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=submitPromotedCandidates");
				promotionBean.setIpAddress(request.getRemoteAddr());
				promotionBean.setType(CPSConstants.PROMOTEDCANDIDATES);
				promotionBean = promotionManagementBusiness.submitQualifiedCandidates(promotionBean);
				promotionBean = promotionManagementBusiness.getPromotedCandidates(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				viewName = CPSConstants.PROMOTEDCANDIDATESLIST;
			}
			/**
			 * Promoted Candidates Home ends
			 */

			/**
			 * Pay Fixation Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PAYFIXATIONHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=payFixationHome");
				promotionBean.setYearID(0);
				promotionBean.setAssessmentTypeID(0);
			
				promotionBean.setModuleId(Integer.parseInt(CPSConstants.PROMOTIONMODULEID));
			//	promotionBean = promotionManagementBusiness.getQualifiedCandidatesHome(promotionBean);
				
				promotionBean =	promotionManagementBusiness.getVenueHomeDetails(promotionBean);
				
		    	promotionBean.setViewOptionalCertificateList(promotionBean.getAssessmentDetails());
				
				promotionBean=promotionManagementBusiness.getPublishDoPartHome(promotionBean);
				promotionBean = promotionManagementBusiness.getPayFixationDetails(promotionBean);
//				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
//				session.setAttribute("incrementsList", promotionBean.getAssessmentDetails1());
				session.setAttribute(CPSConstants.CASUALITIESLIST, promotionBean.getCasualitiesList());
//				session.setAttribute(CPSConstants.TYPELIST, promotionBean.getTypeList());
//				session.setAttribute(CPSConstants.DOPARTLIST, promotionBean.getDoPartList());
				viewName = CPSConstants.PAYFIXATION;
			} else if (CPSUtils.compareStrings(CPSConstants.GETPAYFIXATION, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=getPayFixation");
				promotionBean = promotionManagementBusiness.getPayFixationDetails(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYFIXATIONLIST);
				viewName = CPSConstants.PAYFIXATIONLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITPAYFIXATION, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=submitPayFixation");
				promotionBean = promotionManagementBusiness.submitPayFixationDetails(promotionBean);
				promotionBean = promotionManagementBusiness.getPayFixationDetails(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				session.setAttribute("incrementsList", promotionBean.getAssessmentDetails1());
				viewName = CPSConstants.PAYFIXATIONLIST;
			
			}else if (CPSUtils.compareStrings("gazetted", promotionBean.getParam())) {
				log.debug("PromotionManagementController --> Ajax --> param=gazetted");
				promotionBean.setModuleId(Integer.parseInt(CPSConstants.PROMOTIONMODULEID));
				
				promotionBean = promotionManagementBusiness.getPayFixationDetails(promotionBean);                    //These three lines has interchanged for the Publish Promotion/Pay Fixation Do part II screen without choose the gazettedType
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				session.setAttribute("incrementsList", promotionBean.getAssessmentDetails1());
				
			    promotionBean=promotionManagementBusiness.getPublishDoPartHome(promotionBean);
				session.setAttribute(CPSConstants.CASUALITIESLIST, promotionBean.getCasualitiesList());
				session.setAttribute(CPSConstants.TYPELIST, promotionBean.getTypeList());
				session.setAttribute(CPSConstants.DOPARTLIST, promotionBean.getDoPartList());
				
				
				
				viewName = CPSConstants.PUBLISHDOPART;
			}
			
			/**
			 * Pay Fixation ends
			 */

			/**
			 * Finance Acceptance Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PROFINANCEACCEPTANCE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=financeAcceptanceHome");
				
				
				promotionBean.setModuleId(Integer.parseInt(CPSConstants.PROMOTIONMODULEID));
				promotionBean=promotionManagementBusiness.getDoPartList(promotionBean);
				
			    session.setAttribute(CPSConstants.TYPE,CPSConstants.PROMOTION);
				viewName = CPSConstants.PROMOTIONFINANCEACCEPTANCE;
			//	session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONFINANCEACCEPTANCELIST);
			}else if (CPSUtils.compareStrings(CPSConstants.DOPARTLIST,promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=doPartList");
				promotionBean=promotionManagementBusiness.getTypeMasterDetails(promotionBean);
				promotionBean=promotionManagementBusiness.getDoPartList(promotionBean);
				//session.removeAttribute("assessmentDetails");
				//session.removeAttribute("varAssessmentDetails");
			//	session.setAttribute(CPSConstants.TYPE,CPSConstants.PROMOTION);
				viewName=CPSConstants.DOPARTPAGE;
        	//  promotionBean = promotionManagementBusiness.getGazettedTypeDo(promotionBean);
      	  //	session.setAttribute(CPSConstants.JSONARRAYOBJECT, (JSONArray) JSONSerializer.toJSON(promotionBean.getDoPartList()));
          //	viewName = CPSConstants.RESULTOBJECTS;
			}else if (CPSUtils.compareStrings(promotionBean.getParam(), CPSConstants.CASUALITYLIST)) {
				log.debug("AnnualIncrementController --> onSubmit --> param=doPartList");
				promotionBean=promotionManagementBusiness.getTypeMasterDetails(promotionBean);
				promotionBean=promotionManagementBusiness.getDoPartList(promotionBean);
				promotionBean=promotionManagementBusiness.getPromotionCasualitiesList(promotionBean);
				session.setAttribute(CPSConstants.CASUALITIESLIST, promotionBean.getCasualitiesList());
				/*session.removeAttribute("assessmentDetails");
				session.removeAttribute("varAssessmentDetails");*/
				viewName=CPSConstants.DOPARTPAGE;
			}else if (CPSUtils.compareStrings(promotionBean.getParam(), CPSConstants.EMPLOYEELIST)) {
				promotionBean=promotionManagementBusiness.getEmpDetails(promotionBean);
				session.setAttribute("assessmentDetails", promotionBean.getAssessmentDetails());
				session.setAttribute("varAssessmentDetails", promotionBean.getVarAssessmentDetails());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONFINANCEACCEPTANCELIST);
				viewName=CPSConstants.PROMOTIONFINANCEACCEPTANCELIST;
			}
//			else if (CPSUtils.compareStrings(CPSConstants.GETPUBLISHEDDOLIST, promotionBean.getParam())) {
//				log.debug("PromotionManagementController --> onSubmit --> param=getPublishedDoList");
//				promotionBean = promotionManagementBusiness.getPublishedFixationDOPartDetails(promotionBean);
//				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
//				session.setAttribute("varAssessmentList", promotionBean.getVarAssessmentDetails());
//				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONFINANCEACCEPTANCELIST);
//				viewName = CPSConstants.PROMOTIONFINANCEACCEPTANCELIST;
//			}
			else if (CPSUtils.compareStrings(CPSConstants.SUBMITFINANCEACCEPTANCE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=submitFinanceAcceptance");
				promotionBean = promotionManagementBusiness.submitFinanceAcceptanceDetails(promotionBean);
				promotionBean=promotionManagementBusiness.getEmpDetails(promotionBean);
				session.setAttribute("assessmentDetails", promotionBean.getAssessmentDetails());
				session.setAttribute("varAssessmentDetails", promotionBean.getVarAssessmentDetails());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONFINANCEACCEPTANCELIST);
				viewName = CPSConstants.PROMOTIONFINANCEACCEPTANCELIST;
			}
			/**
			 * Finance Acceptance Ends
			 */

			/**
			 * Pay Fixation DO Part Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PAYFIXATIONDOPARTHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=payFixationDOPartHome");
				viewName = CPSConstants.PAYFIXATIONDOPART;
			} else if (CPSUtils.compareStrings(CPSConstants.GETPAYFIXATIONDOPART, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=getPayFixationDOPart");
				promotionBean = promotionManagementBusiness.getPayFixationDOPartDetails(promotionBean);
				session.setAttribute(CPSConstants.PAYFIXATIONDOPARTDETAILS, promotionBean.getPayFixationDOPartDetails());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PAYFIXATIONDOPARTLIST);
				viewName = CPSConstants.PAYFIXATIONDOPARTLIST;
			}
			/**
			 * Pay Fixation DO Part Ends
			 */
			
			/**
			 * Promotion Report Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PROMOTIONREPORTS, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=qualifiedCandidates");
				promotionBean = promotionManagementBusiness.getQualifiedCandidatesHome(promotionBean);
				viewName = CPSConstants.PROMOTIONREPORT;
			} 
			/**
			 * Promotion Report Ends
			 */
			
			/**
			 * Discipline Master Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.DISCIPLINEMASTER, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=PromotionDisciplineMaster");
				promotionBean= promotionManagementBusiness.getDisciplineDetails(promotionBean);
				session.setAttribute(CPSConstants.DISCIPLINELIST,promotionBean.getDisciplineList());
				viewName = CPSConstants.PROMOTIONDISCIPLINEMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONDISCIPLINEMASTERLIST);
			} 
			else if (CPSUtils.compareStrings(CPSConstants.MANAGEPROMOTIONDISCIPLINE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=managePromotionDiscipline");
				promotionBean=promotionManagementBusiness.submitDiscipline(promotionBean);
				session.setAttribute(CPSConstants.DISCIPLINELIST,promotionManagementBusiness.getDisciplineList(promotionBean));
				viewName = CPSConstants.PROMOTIONDISCIPLINEMASTERLIST;
			} 
			else if (CPSUtils.compareStrings(CPSConstants.DELETEDISCIPLINEMASTER, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=DeletePromotionDisciplineMaster");
				promotionBean.setResult(promotionManagementBusiness.deleteDiscipline(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.DISCIPLINELIST,promotionManagementBusiness.getDisciplineList(promotionBean));
				}
				viewName = CPSConstants.PROMOTIONDISCIPLINEMASTERLIST;
			} 

			/**
			 * Discipline Master Ends
			 */
			
			/**
			 * Sub Discipline Master Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.SUBDISCIPLINEMASTER, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=PromotionSubDisciplineMaster");
				promotionBean=promotionManagementBusiness.getSubDisciplineHomeList(promotionBean);
				session.setAttribute(CPSConstants.SUBDISCIPLINELIST,promotionManagementBusiness.getSubDisciplineDetails(promotionBean));
				viewName = CPSConstants.PROMOTIONSUBDISCIPLINEMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONSUBDISCIPLINEMASTERLIST);
			} 
			else if (CPSUtils.compareStrings(CPSConstants.MANAGEPROMOTIONSUBDISCIPLINE, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=managePromotionSubDiscipline");
				promotionBean.setType("manageSubDiscipline");
				promotionBean=promotionManagementBusiness.submitSubDiscipline(promotionBean);
				session.setAttribute(CPSConstants.SUBDISCIPLINELIST,promotionManagementBusiness.getSubDisciplineDetails(promotionBean));
				viewName = CPSConstants.PROMOTIONSUBDISCIPLINEMASTERLIST;
			} 
			else if (CPSUtils.compareStrings(CPSConstants.DELETESUBDISCIPLINEMASTER, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=DeleteSubPromotionDisciplineMaster");
				promotionBean.setResult(promotionManagementBusiness.deleteSubDiscipline(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.DELETE, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.SUBDISCIPLINELIST,promotionManagementBusiness.getSubDisciplineDetails(promotionBean));
						}
				viewName = CPSConstants.PROMOTIONSUBDISCIPLINEMASTERLIST;
			} 

			/**
			 * Sub Discipline Master Ends
			 */
			
			
			/**
			 * Add external Board Member Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.EXTERNALBOARDMEMBER, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=externalBoardMember");
				session.setAttribute(CPSConstants.EXTERNALBOARDEMPLOYEELIST,promotionManagementBusiness.getExternalBoardList(promotionBean));
				viewName = CPSConstants.EXTERNALBOARDEMPLOYEE;
			} 
			else if (CPSUtils.compareStrings(CPSConstants.MANAGEEXTERNALBOARDMEMBER, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=manageExternalBoardMember");
				promotionBean=promotionManagementBusiness.submitExternalBoardMember(promotionBean);
				session.setAttribute(CPSConstants.EXTERNALBOARDEMPLOYEELIST,promotionManagementBusiness.getExternalBoardList(promotionBean));
				viewName = CPSConstants.EXTERNALBOARDEMPLOYEELIST;
			} 
			else if (CPSUtils.compareStrings(CPSConstants.DELETEEXTERNALBOARDMASTER, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteExternalBoardMember");
				promotionBean.setResult(promotionManagementBusiness.deleteExternalBoardMember(promotionBean));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, promotionBean.getResult())) {
					session.setAttribute(CPSConstants.EXTERNALBOARDEMPLOYEELIST,promotionManagementBusiness.getExternalBoardList(promotionBean));
						}
				viewName = CPSConstants.EXTERNALBOARDEMPLOYEELIST;
			} 

			/**
			 * Promotion Offline Entry Starts
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PROMOTIONOFFLINEENTRY, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=PromotionOfflineEntry");
				promotionBean=promotionManagementBusiness.getOfflineEntryHomeDetails(promotionBean);
				//session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,promotionManagementBusiness.getOfflineEntryList(promotionBean));
				session.removeAttribute(CPSConstants.PROMOTIONOFFLINELIST);
				session.removeAttribute("empDetailsList");
				session.removeAttribute("catWiseDesigJson");
				session.removeAttribute("empGradePayJson");
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONOFFLINEENTRYLIST);
				viewName = CPSConstants.PROMOTIONOFFLINEENTRY;
			} 
			else if (CPSUtils.compareStrings(CPSConstants.MANAGEPROMOTIONOFFLINEENTRY, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=managePromotionOfflineEntry");
				promotionBean=promotionManagementBusiness.submitOfflineEntry(promotionBean);
				String message = promotionBean.getResult();
				List<EmployeeBean> empDetailsList = promotionManagementBusiness.validateSFID(promotionBean);
				if(empDetailsList.size()>0){
					session.setAttribute("empDetailsList", empDetailsList);
					promotionBean =promotionManagementBusiness.retrieveFamilyCatDesigList(promotionBean);
					promotionBean.setBasicPay(promotionManagementBusiness.getBasicPayValue(promotionBean));
					session.setAttribute("empGradePayJson", JSONSerializer.toJSON(promotionBean.getEmpPayScaleList()));
					session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,promotionManagementBusiness.getOfflineEntryList(promotionBean));
					if (CPSUtils.checkList(promotionBean.getCatwiseDesig())) {
						session.setAttribute("catWiseDesigJson", JSONSerializer.toJSON(promotionBean.getCatwiseDesig()));
					}
					promotionBean.setResult(message);
				}
				viewName = CPSConstants.PROMOTIONOFFLINEENTRYLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.CATEGORYDESIGNATION, promotionBean.getParam())) {
				log.debug("LtcApprovalRequestController --> onSubmit --> param=getCategoryDesignation");
				List<EmployeeBean> empDetailsList = promotionManagementBusiness.validateSFID(promotionBean);
				if(empDetailsList.size()>0){
					session.setAttribute("empDetailsList", empDetailsList);
					//code newly added to disable submit button
					EmployeePaymentBean empPaymentBean= new EmployeePaymentBean();
					empPaymentBean.setSfID(promotionBean.getSfID());
					empPaymentBean.setSfid(promotionBean.getRefSfid());
					empPaymentBean = empPaymentBusiness.getDetails(empPaymentBean);
					session.setAttribute("jsonWorkLocation", empPaymentBean.getWorklocation());
					session.setAttribute("jsonRole", empPaymentBean.getRole());
					promotionBean =promotionManagementBusiness.retrieveFamilyCatDesigList(promotionBean);
					promotionBean.setBasicPay(promotionManagementBusiness.getBasicPayValue(promotionBean));
					session.setAttribute("empGradePayJson", JSONSerializer.toJSON(promotionBean.getEmpPayScaleList()));
					session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,promotionManagementBusiness.getOfflineEntryList(promotionBean));
					if (CPSUtils.checkList(promotionBean.getCatwiseDesig())) {
						session.setAttribute("catWiseDesigJson", JSONSerializer.toJSON(promotionBean.getCatwiseDesig()));
					}
				}else{
					promotionBean.setMessage("invalidSFID");
					session.removeAttribute("catWiseDesigJson");
					session.removeAttribute("empGradePayJson");
				}
				viewName = "PromotionOfflineEntryList";
				
			}else if(CPSUtils.compareStrings("getVariableIncrementValue", promotionBean.getParam())){
				log.debug("LtcApprovalRequestController --> onSubmit --> param=getVariableIncrementValue");
				promotionBean.setVarIncVal(promotionManagementBusiness.getVariableIncrementValue(promotionBean));
				viewName = "PromotionOfflineEntryList";
			}
			else if (CPSUtils.compareStrings("deleteRecord", promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=deleteRecord");
				promotionBean=promotionManagementBusiness.deleteRecord(promotionBean);
				session.setAttribute(CPSConstants.PROMOTIONOFFLINELIST,promotionManagementBusiness.getOfflineEntryList(promotionBean));
				viewName="PromotionOfflineEntryList";
			}
			/**
			 * Promotion Offline Entry Ends
			 */
			/**
			 * View Optional Certificate starts
			 * @author Rajendra
			 */
			else if (CPSUtils.compareStrings(CPSConstants.OPTIONALCERTIFICATESHOME, promotionBean.getParam())) {
				log.debug("PromotionRequestController --> onSubmit --> param=optionalCertificatesHome");
				
				promotionBean = promotionManagementBusiness.getVenueHomeDetails(promotionBean);
				
				request.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				request.removeAttribute(CPSConstants.OPTIONALCERTIFICATELIST);
				viewName = CPSConstants.VIEWOPTIONALCERTIFICATE;
			}
			else if (CPSUtils.compareStrings(CPSConstants.GETOPTIONALCERTIFICATES, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=getOptionalCertificates");
				
				promotionBean = promotionManagementBusiness.getPayFixationDetailsWithOption(promotionBean);
				request.setAttribute(CPSConstants.OPTIONALCERTIFICATELIST, promotionBean.getAssessmentDetails1());
				request.setAttribute(CPSConstants.RETURN, "OptionCertificateList1");			
			viewName = "OptionCertificateList1";
			}
			
			else if (CPSUtils.compareStrings(CPSConstants.SUBMITCANDIDATESOPTION, promotionBean.getParam())) {
			
				promotionBean =     promotionManagementBusiness.submitCandidatesOption(promotionBean);
				promotionBean = promotionManagementBusiness.getPayFixationDetailsWithOption(promotionBean);
				request.removeAttribute(CPSConstants.OPTIONALCERTIFICATELIST);
				request.setAttribute(CPSConstants.OPTIONALCERTIFICATELIST, promotionBean.getAssessmentDetails1());
				request.setAttribute(CPSConstants.RETURN, "OPTIONALCERTIFICATELIST");
			
				viewName = "OptionCertificateList1";
			}
			
			
			/**
			 * View Optional Certificate ends
			 * @author Rajendra
			 */
			/****
			 * Pay Updation Before DoPartII Ganeration starts
			 * @author Rajendra
			 */
			else if (CPSUtils.compareStrings(CPSConstants.PROMOTIONPAYUPDATEHOME, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=promotionPayUpdateHome");
                promotionBean = promotionManagementBusiness.getVenueHomeDetails(promotionBean);
				session.setAttribute(CPSConstants.ASSESSMENTLIST, promotionBean.getAssessmentDetails());
				viewName = CPSConstants.PROMOTIONPAYUPDATEPAGE;
			} else if (CPSUtils.compareStrings(CPSConstants.GETPROMOTIONPAYUPDATELIST, promotionBean.getParam())) {
				log.debug("PromotionManagementController --> onSubmit --> param=getpromotionPayUpdateList");
				promotionBean = promotionManagementBusiness.getPayFixationPayDetails(promotionBean);
				session.setAttribute(CPSConstants.PROMOTIONPAYUPDATELIST, promotionBean.getAssessmentDetails1());
				
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONPAYUPDATELISTPAGE);
				viewName = CPSConstants.PROMOTIONPAYUPDATELISTPAGE;
			}
			
			 else if (CPSUtils.compareStrings(CPSConstants.SUBMITPROMOTIONPAYUPDATE, promotionBean.getParam())) {
				 log.debug("PromotionManagementController --> onSubmit --> param=submitPromotionPayUpdate");
	      promotionBean = promotionManagementBusiness.submitPromotionPayDetails(promotionBean);
				 
				 promotionBean = promotionManagementBusiness.getPayFixationPayDetails(promotionBean);
				 session.setAttribute(CPSConstants.PROMOTIONPAYUPDATELIST, promotionBean.getAssessmentDetails1());
				 session.setAttribute(CPSConstants.RETURN, CPSConstants.PROMOTIONPAYUPDATELISTPAGE);
				 viewName = CPSConstants.PROMOTIONPAYUPDATELISTPAGE;
			 }
			
			/****
			 * Pay Updation Before DoPartII Ganeration ends
			 * @author Rajendra
			 */
			mav = new ModelAndView(viewName, CPSConstants.PROMOTION, promotionBean);
			if(!CPSUtils.isNullOrEmpty(promotionBean.getMessage())){
				mav.addObject("invalidSFID", promotionBean.getMessage());
			}
			mav.addObject("empVarVal", promotionBean.getVarIncVal());
			mav.addObject("basicPay", promotionBean.getBasicPay());
			mav.addObject(CPSConstants.RESULT, promotionBean.getResult());
			mav.addObject(CPSConstants.TYPE, session.getAttribute(CPSConstants.ASSESSMENTCATEGORYID));
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
	
	@RequestMapping(value="/promotionYearWise.htm",method={RequestMethod.POST,RequestMethod.GET})
	public void yearWisePromotionList(@ModelAttribute PromotionManagementBean promotionBean,HttpServletRequest request,HttpServletResponse response){
			log.debug("PromotionManagementController --> onSubmit --> param=yearWiseAssesmentList");
			JSON json = null;
			try {
				 if (CPSUtils.compareStrings(CPSConstants.YEARWISEASSESSMENTLIST, promotionBean.getParam())) {
				promotionBean = promotionManagementBusiness.getQualifiedCandidatesHome(promotionBean);
				request.setAttribute(CPSConstants.YEARWISEASSESSMENTLIST, promotionBean.getLocalBoardMembersList());
				 json = JSONSerializer.toJSON(promotionBean.getLocalBoardMembersList());
			    response.setContentType("text/json");
				response.getWriter().print(json);
				 } }
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//return json;	 
			
		
		}
	}

	
	


