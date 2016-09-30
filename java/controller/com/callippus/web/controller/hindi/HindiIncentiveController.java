package com.callippus.web.controller.hindi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.hindi.HindiIncentiveBean;
import com.callippus.web.business.hindi.HindiIncentiveBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.hindi.dto.EmpKeyValueDTO;


@Controller
@RequestMapping("/hindi.htm")
@SessionAttributes
public class HindiIncentiveController {
	@Autowired
	private HindiIncentiveBusiness hindiBusiness;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.HINDI) HindiIncentiveBean hindiBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
       ModelAndView mav = null;
       String viewName = "";
       String message = "";
		try{
			HttpSession session = request.getSession(true);
			hindiBean.setLoginSfid((session.getAttribute(CPSConstants.SFID).toString()));
			hindiBean.setPayRunMonth(hindiBusiness.getCurrentRunMonth());
			if(CPSUtils.compareStrings(CPSConstants.PAGING, hindiBean.getParam())){
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
				else if(CPSUtils.compareStrings(CPSConstants.EXAMMASTER, hindiBean.getParam())){				
				//for grid
				 session.setAttribute(CPSConstants.HINDIEXAMSLIST, hindiBusiness.getExamsList());
				 session.setAttribute(CPSConstants.HINDIEXAMSJSON, (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.HINDIEXAMSLIST)));
				 session.setAttribute(CPSConstants.RETURN, CPSConstants.HINDIEXAMSLISTPAGE);
				viewName = CPSConstants.HINDIEXAMMASTER;
				}
			  else if(CPSUtils.compareStrings(CPSConstants.SAVEEXAMS, hindiBean.getParam())){
				 message = hindiBusiness.saveExams(hindiBean);
				 session.setAttribute(CPSConstants.HINDIEXAMSLIST, hindiBusiness.getExamsList());			 
				 session.setAttribute(CPSConstants.HINDIEXAMSJSON, (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.HINDIEXAMSLIST)));
					viewName = CPSConstants.HINDIEXAMSLISTPAGE;
					}
			   else if(CPSUtils.compareStrings(CPSConstants.DELETEEXAM, hindiBean.getParam())){
					 message = hindiBusiness.deleteExam(hindiBean);
					 session.setAttribute(CPSConstants.HINDIEXAMSLIST, hindiBusiness.getExamsList());
					 session.setAttribute(CPSConstants.HINDIEXAMSJSON, (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.HINDIEXAMSLIST)));
						viewName = CPSConstants.HINDIEXAMSLISTPAGE;
						}

			 
				else if(CPSUtils.compareStrings(CPSConstants.CASHAWARDMASTER, hindiBean.getParam())){
					session.setAttribute(CPSConstants.HINDIEXAMSLIST, hindiBusiness.getExamsList());
					session.setAttribute(CPSConstants.HINDICASHAWARDLIST, hindiBusiness.getCashAwardDetailsList());
					session.setAttribute(CPSConstants.RETURN, CPSConstants.HINDCASHAWARDLISTPAGE);
					viewName = CPSConstants.HINDCASHAWARDMASTER;
					}
				else if(CPSUtils.compareStrings(CPSConstants.SAVECASHAWARDDETAILS, hindiBean.getParam())){
					 message = hindiBusiness.saveCashAwardDetails(hindiBean);
					 session.setAttribute(CPSConstants.HINDICASHAWARDLIST, hindiBusiness.getCashAwardDetailsList());
						viewName = CPSConstants.HINDCASHAWARDLISTPAGE;
						}
				else if(CPSUtils.compareStrings(CPSConstants.DELETECASHAWARDDETAILS, hindiBean.getParam())){
						 message = hindiBusiness.deleteCashAwardDetails(hindiBean);
						 session.setAttribute(CPSConstants.HINDICASHAWARDLIST, hindiBusiness.getCashAwardDetailsList());
		 				viewName = CPSConstants.HINDCASHAWARDLISTPAGE;
							}		
			 
			 
				else if(CPSUtils.compareStrings(CPSConstants.EXAMNOMINATIONMASTER, hindiBean.getParam())){
					session.setAttribute(CPSConstants.HINDIDEPARTMENTSLIST, hindiBusiness.getDepartmentsList());
					session.setAttribute(CPSConstants.RETURN, CPSConstants.HINDINOMINATIONLISTPAGE);
					viewName = CPSConstants.HINDINOMINATIONMASTER;
					}
				else if(CPSUtils.compareStrings(CPSConstants.GETNOMINATIONLIST, hindiBean.getParam())){
					//for all eligible emp list
					List<EmpKeyValueDTO> enList = hindiBusiness.getNominationList(hindiBean);
					if(enList.size()==0){
						hindiBean.setListSize("");
					}
					session.setAttribute(CPSConstants.HINDINOMINATIONLIST, enList);
					session.setAttribute(CPSConstants.HINDINOMINATIONJSONLIST, (JSONArray)JSONSerializer.toJSON(enList));
					session.setAttribute(CPSConstants.SELECTEDNOMINATIONLIST,hindiBusiness.getSelectedNominationList());
					viewName = CPSConstants.HINDINOMINATIONLISTPAGE;
					}
				else if(CPSUtils.compareStrings(CPSConstants.SAVENOMINATIONJSONLIST, hindiBean.getParam())){								
					message = hindiBusiness.saveNominationList(hindiBean);
					session.setAttribute(CPSConstants.HINDINOMINATIONLIST, hindiBusiness.getNominationList(hindiBean));
					session.setAttribute(CPSConstants.HINDINOMINATIONJSONLIST, (JSONArray)JSONSerializer.toJSON(hindiBusiness.getNominationList(hindiBean)));
					session.setAttribute(CPSConstants.SELECTEDNOMINATIONLIST,hindiBusiness.getSelectedNominationList());
					viewName = CPSConstants.HINDINOMINATIONLISTPAGE;
					}
			 
				else if(CPSUtils.compareStrings(CPSConstants.EMPRESULTMASTER, hindiBean.getParam())){
					session.setAttribute(CPSConstants.SELECTEDNOMINATIONLIST,hindiBusiness.getSelectedNominationList());
					session.setAttribute(CPSConstants.HINDIEXAMSLIST, hindiBusiness.getExamsList());
					session.setAttribute(CPSConstants.HINDIEXAMSJSON, (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.HINDIEXAMSLIST)));
					session.setAttribute(CPSConstants.GETEXAMCONFIGDETAILSLIST, hindiBusiness.getExamConfigDetails());
					session.setAttribute("examConfigDetailsJSON", (JSONArray)JSONSerializer.toJSON(session.getAttribute("getExamConfigDetailsList"))) ;
					
					session.setAttribute(CPSConstants.GETRESULTDETAILS, hindiBusiness.getResultDetails());
					session.setAttribute(CPSConstants.RETURN, CPSConstants.HINDIELIGIBLEEXAMSLISTPAGE);
					viewName = CPSConstants.HINDIEMPRESULTMASTER;
					}
			 
			 else if(CPSUtils.compareStrings(CPSConstants.GETELIGIBLEEXAMS, hindiBean.getParam())){									 	
				 session.setAttribute(CPSConstants.ELIGIBLEEXAMSLIST, hindiBusiness.getEligibleExamsList(hindiBean));	
					viewName = CPSConstants.HINDIELIGIBLEEXAMSLISTPAGE;
					}
				else if(CPSUtils.compareStrings(CPSConstants.SAVERESULTDETAILS, hindiBean.getParam())){
					message = hindiBusiness.saveResultDetails(hindiBean);
					session.setAttribute(CPSConstants.GETRESULTDETAILS, hindiBusiness.getResultDetails());
					viewName = CPSConstants.HINDIEMPRESULTLISTPAGE;
					}
				else if(CPSUtils.compareStrings(CPSConstants.DELETERESULTDETAILS, hindiBean.getParam())){
					message = hindiBusiness.deleteResultDetails(hindiBean);
					session.setAttribute(CPSConstants.GETRESULTDETAILS, hindiBusiness.getResultDetails());
					viewName = CPSConstants.HINDIEMPRESULTLISTPAGE;
					}
			 
			 else if(CPSUtils.compareStrings(CPSConstants.EMPLOYEEMASTER, hindiBean.getParam())){		        
				 session.setAttribute(CPSConstants.SFIDLIST, hindiBusiness.getSfidList());
				 session.setAttribute(CPSConstants.SFIDNAMEJSON, (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.SFIDLIST)));
				//for grid				
				session.setAttribute(CPSConstants.HINDIEMPLOYEEDETAILSLIST, hindiBusiness.getEmployeeDetailsList());				
				session.setAttribute(CPSConstants.HINDIEMPJSONLIST, (JSONArray)JSONSerializer.toJSON(hindiBusiness.getEmployeeList()));	
				session.setAttribute(CPSConstants.HINDINONELIGIBLEEXAMJSONLIST, (JSONArray)JSONSerializer.toJSON(hindiBusiness.getNonEligibleEmpExamList()));
				session.setAttribute(CPSConstants.RETURN,CPSConstants.HINDIEMPLOYEELISTPAGE);
				viewName = CPSConstants.HINDIEMPLOYEEMASTER;
			}
			 else if(CPSUtils.compareStrings(CPSConstants.GETCHECKEXAMSLIST, hindiBean.getParam())){
				 session.setAttribute(CPSConstants.EMPCHECKEXAMSLIST,hindiBusiness.getCheckExamsList(hindiBean));
				 viewName = CPSConstants.HINDIEMPCHECKEXAMSLISTPAGE;
			 }
			 else if(CPSUtils.compareStrings(CPSConstants.SAVEEMPDETAILS,hindiBean.getParam()))
				{
					message = hindiBusiness.saveEmployeeDetails(hindiBean);
					session.setAttribute(CPSConstants.HINDIEMPLOYEEDETAILSLIST, hindiBusiness.getEmployeeDetailsList());	
					session.setAttribute(CPSConstants.HINDINONELIGIBLEEXAMJSONLIST, (JSONArray)JSONSerializer.toJSON(hindiBusiness.getNonEligibleEmpExamList()));
					viewName = CPSConstants.HINDIEMPLOYEELISTPAGE;
				}
			 else if(CPSUtils.compareStrings(CPSConstants.DELETEEMPDETAILS,hindiBean.getParam()))
				{
					message = hindiBusiness.deleteEmployeeDetails(hindiBean);
					session.setAttribute(CPSConstants.HINDIEMPLOYEEDETAILSLIST, hindiBusiness.getEmployeeDetailsList());	
					session.setAttribute(CPSConstants.HINDINONELIGIBLEEXAMJSONLIST, (JSONArray)JSONSerializer.toJSON(hindiBusiness.getNonEligibleEmpExamList()));
					viewName = CPSConstants.HINDIEMPLOYEELISTPAGE;
				}
			 
			 
			 else if(CPSUtils.compareStrings(CPSConstants.EXAMCONFIGMASTER, hindiBean.getParam())){
				 session.setAttribute(CPSConstants.HINDIEXAMSLIST, hindiBusiness.getExamsList());
				 session.setAttribute(CPSConstants.HINDIEMPCATEGORYLIST, hindiBusiness.getCategoryList());
				 session.setAttribute(CPSConstants.GETEXAMCONFIGDETAILSLIST, hindiBusiness.getExamConfigDetails());
				 session.setAttribute(CPSConstants.RETURN, CPSConstants.HINDIEXAMCONFIGMASTER);
					viewName = CPSConstants.HINDIEXAMCONFIGMASTER;
					}	
			 else if(CPSUtils.compareStrings(CPSConstants.GETDESIGNATIONS, hindiBean.getParam())){									 	
				 session.setAttribute(CPSConstants.HINDIDESELECTDESIGNATIONLIST, hindiBusiness.getDeselectDesignationList(hindiBean));	
				 session.setAttribute(CPSConstants.HINDISELECTDESIGNATIONLIST, hindiBusiness.getSelectDesignationList(hindiBean));
					viewName = CPSConstants.HINDIDESIGNATIONLISTPAGE;
					}							
			  else if(CPSUtils.compareStrings(CPSConstants.SAVEEXAMCONFIGDETAILS, hindiBean.getParam())){
				message = hindiBusiness.saveExamConfigDetails(hindiBean);
				 session.setAttribute(CPSConstants.GETEXAMCONFIGDETAILSLIST, hindiBusiness.getExamConfigDetails());
				viewName = CPSConstants.HINDIEXAMCONFIGLISTPAGE;
				}	
			 
			else if(CPSUtils.compareStrings(CPSConstants.DELETEEXAMCONFIGDETAILS, hindiBean.getParam())){
				message = hindiBusiness.deleteExamConfigDetails(hindiBean);
			    session.setAttribute(CPSConstants.GETEXAMCONFIGDETAILSLIST, hindiBusiness.getExamConfigDetails());
				viewName = CPSConstants.HINDIEXAMCONFIGLISTPAGE;
				}
			else if(CPSUtils.compareStrings(CPSConstants.INCENTIVEDETAILSMASTER, hindiBean.getParam()))
			{
				hindiBean.setSfidSearch("SF");
				hindiBean.setEmployeeName("");
				session.setAttribute(CPSConstants.SFIDJSONLIST, (JSONArray)JSONSerializer.toJSON(hindiBusiness.getSfidList()));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.HINDIINCENTIVEDETAILSMASTER);
				viewName = CPSConstants.HINDIINCENTIVEDETAILSMASTER;
			}
			else if(CPSUtils.compareStrings(CPSConstants.GETINCENTIVEEMPDETAILS, hindiBean.getParam()))
			{
				session.setAttribute(CPSConstants.HINDIINCENTIVEEMPLIST, hindiBusiness.getIncentiveEmpList(hindiBean));
				session.setAttribute(CPSConstants.HINDIINCENTIVEAMOUNT, hindiBusiness.getIncentiveAmount(hindiBean));
				session.setAttribute(CPSConstants.HINDIINCENTIVEDETAILSLIST, hindiBusiness.getIncentiveDetails(hindiBean));					
				viewName = CPSConstants.HINDIINCENTIVEEMPLISTPAGE;
			}
			else if(CPSUtils.compareStrings(CPSConstants.SAVEINCENTIVEDETAILS, hindiBean.getParam()))
			{				
				message = hindiBusiness.saveIncentiveDetails(hindiBean);
				session.setAttribute(CPSConstants.HINDIINCENTIVEDETAILSLIST, hindiBusiness.getIncentiveDetails(hindiBean));
				viewName = CPSConstants.HINDIINCENTIVELIST;
			}
			else if(CPSUtils.compareStrings(CPSConstants.DELETEINCENTIVEDETAILS, hindiBean.getParam()))
			{
				message = hindiBusiness.deleteIncentiveDetails(hindiBean);
				session.setAttribute(CPSConstants.HINDIINCENTIVEDETAILSLIST, hindiBusiness.getIncentiveDetails(hindiBean));
				viewName = CPSConstants.HINDIINCENTIVELIST;
			}
			else if(CPSUtils.compareStrings("hindiIncentiveFinance", hindiBean.getParam())){
				session.setAttribute("hindiIncEmpListForFinance", hindiBusiness.getIncentiveEmpListForFinance(hindiBean));
				viewName = "payHindiIncEmpAcceptList";
			}else if(CPSUtils.compareStrings("savePayHindiIncentiveDetails", hindiBean.getParam())){
				 message = hindiBusiness.savePayHindiIncentiveDetails(hindiBean);
				 session.setAttribute("hindiIncEmpListForFinance", hindiBusiness.getIncentiveEmpListForFinance(hindiBean));
				viewName = "payHindiIncEmpAcceptResult";
			}
			
    	   mav = new ModelAndView(viewName,CPSConstants.HINDI,hindiBean);
    	   mav.addObject(CPSConstants.MESSAGE, message);
    	   mav.addObject("CheckSize", hindiBean.getListSize());
       }
       catch(Exception e)
       {
    	   throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
       }
		return mav;
	}
}