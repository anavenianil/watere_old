package com.callippus.web.controller.incometax;

import java.util.List;
import java.util.Map;

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

import com.callippus.web.beans.incometax.IncomeTaxMasterBean;
import com.callippus.web.business.configuration.ConfigurationBusiness;
import com.callippus.web.business.incometax.IncomeTaxBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.paybill.IPayBillDAO;
import com.callippus.web.incometax.dto.DBComparisonDTO;

@Controller
@RequestMapping("incomeTax.htm")
@SessionAttributes
public class IncomeTaxController {
	@Autowired
	private IncomeTaxBusiness incomeTaxBusiness;
	@Autowired
	private ConfigurationBusiness configurationBusiness;
	@Autowired
	private IPayBillDAO payBillDAO;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.INCOMETAXMASTER) IncomeTaxMasterBean incomeTaxMasterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
 		String viewName = null;
		HttpSession session = null;
		try {	
			//ErpAction.userChecks(request);
			session = request.getSession(true);
			incomeTaxMasterBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.compareStrings(CPSConstants.PAGING, incomeTaxMasterBean.getParam())) {
				viewName = session.getAttribute(CPSConstants.RETURN).toString();
			}
			else if (CPSUtils.compareStrings(CPSConstants.FYMASTER, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxMasterBean.getFinYearList());
				viewName = CPSConstants.FYMASTERDETAILS;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.FINYEARMASTERDETAILS);
 			} else if (CPSUtils.compareStrings(CPSConstants.INSERTFYMASTERDETAILS, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.setFinYearMasterDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxBusiness.getFinYearList());
				viewName = CPSConstants.FYMASTERLISTDETAILS;

			} else if (CPSUtils.compareStrings(CPSConstants.DELETEFYDETAILS, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.deleteFinYearDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxMasterBean.getFinYearList());
				viewName = CPSConstants.FYMASTERLISTDETAILS;

			} else if (CPSUtils.compareStrings(CPSConstants.ITMASTER, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxMasterBean.getFinYearList());
				viewName = CPSConstants.ITTAXRANGEMASTER;
			} else if (CPSUtils.compareStrings(CPSConstants.INCOMESTAGES, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean = incomeTaxBusiness.getincomeTaxStagelist(incomeTaxMasterBean);
				if(CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getMessage()))
				{
					if (incomeTaxMasterBean.getIncomeTaxStageList().size() != 0)
						incomeTaxMasterBean.setType("old");
					session.setAttribute(CPSConstants.INCOMETAXSTAGELIST, incomeTaxMasterBean.getIncomeTaxStageList());
					viewName = CPSConstants.ITTAXRANGEMASTERSTAGES;
				}
				else
					viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.TAXMANAGE, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean = incomeTaxBusiness.getincomeTaxStagelist(incomeTaxMasterBean);
				if (incomeTaxMasterBean.getIncomeTaxStageList().size() != 0)
					incomeTaxMasterBean.setType("old");
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitIncomeTaxDetails(incomeTaxMasterBean));
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.TAXDELETE, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean = incomeTaxBusiness.getincomeTaxStagelist(incomeTaxMasterBean);
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.deleteIncomeTaxDetails(incomeTaxMasterBean));
				viewName = CPSConstants.RESULT;
			}  else if (CPSUtils.compareStrings(CPSConstants.SAVINGS, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute("financialYear", incomeTaxMasterBean.getFinYearList());
				incomeTaxMasterBean.setSavingsList(incomeTaxBusiness.getSavingsListWithSections());
				session.setAttribute(CPSConstants.SAVINGSLIST, incomeTaxMasterBean.getSavingsList());
				incomeTaxMasterBean.setSectionList(incomeTaxBusiness.getITSectionList());//must be unique
				session.setAttribute(CPSConstants.SECTIONLIST, incomeTaxMasterBean.getSectionList());
				viewName = CPSConstants.ITSAVINGSMASTER;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSAVINGSMASTERLIST);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITSAVINGS, incomeTaxMasterBean.getParam())) {
				//saving the section values
				request.setAttribute("message",incomeTaxBusiness.submitSavingsDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setSavingsList(incomeTaxBusiness.getSavingsListWithSections());
				//values placed in sectionname  drop down in jsp
				session.setAttribute(CPSConstants.SAVINGSLIST, incomeTaxMasterBean.getSavingsList());
				//commented by nagendra.v
				//incomeTaxMasterBean.setSectionList(incomeTaxBusiness.getITSectionList());
				//session.setAttribute(CPSConstants.SECTIONLIST, incomeTaxMasterBean.getSectionList());
				viewName = CPSConstants.ITSAVINGSMASTERLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.SAVINGSDELETE, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.deleteSavingsDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setSavingsList(incomeTaxBusiness.getSavingsListWithSections());
				session.setAttribute(CPSConstants.SAVINGSLIST, incomeTaxMasterBean.getSavingsList());
				viewName = CPSConstants.ITSAVINGSMASTERLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.CONFIGHOME, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setSearchSfid((String)session.getAttribute("Labid"));
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxMasterBean.getFinYearList());
				viewName = CPSConstants.ITCONFIGHOME;
			} else if (CPSUtils.compareStrings("challanConfig", incomeTaxMasterBean.getParam())) {                   //This if condition for ChallanAmountDetails(P)
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.saveChallanDetails(incomeTaxMasterBean));
				//session.setAttribute(CPSConstants.MESSAGE, incomeTaxMasterBean.getMessage());
				viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.CONFIG, incomeTaxMasterBean.getParam())) {
				//nagendra working today 12/12
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.getEmpExist(incomeTaxMasterBean));
				if (CPSUtils.compareStrings(incomeTaxMasterBean.getMessage(), CPSConstants.YES)) 
				{
					incomeTaxMasterBean = incomeTaxBusiness.getEmpList(incomeTaxMasterBean);
					session.setAttribute(CPSConstants.SAVINGSLIST, incomeTaxBusiness.getRelatedSectionById(incomeTaxMasterBean));
					incomeTaxMasterBean.setFinancialYear(incomeTaxBusiness.getFinancialYear(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())));
					incomeTaxMasterBean.setConfigDetailsList(incomeTaxBusiness.getConfigListDetails(incomeTaxMasterBean));
					//incomeTaxMasterBean.setArrearsList(incomeTaxBusiness.getArrearsListDetails(incomeTaxMasterBean));
					incomeTaxMasterBean.setPayMonthList(incomeTaxBusiness.getPayMonthList(incomeTaxMasterBean.getFinancialYear()));
					session.setAttribute(CPSConstants.PAYMONTHLIST, incomeTaxMasterBean.getPayMonthList());
					//session.setAttribute(CPSConstants.ARREARSDETAILSLIST, incomeTaxMasterBean.getArrearsList());
					session.setAttribute("arrearsDetailslist",incomeTaxBusiness.getITarrears(incomeTaxMasterBean));
					session.setAttribute(CPSConstants.CONFIGDETAILSLIST, incomeTaxMasterBean.getConfigDetailsList());
				//	incomeTaxMasterBean.setSavingsList(incomeTaxBusiness.getSavingsListWithSections());
				/*	session.setAttribute(CPSConstants.SAVINGSLIST, incomeTaxMasterBean.getSavingsList());*/
					session.setAttribute(CPSConstants.SEARCHSFID, incomeTaxMasterBean.getSearchSfid());
					session.setAttribute(CPSConstants.SEARCHFINYAER, incomeTaxMasterBean.getSelectedFYear());
					incomeTaxMasterBean.setRentList(incomeTaxBusiness.getITRentList(incomeTaxMasterBean));
					session.setAttribute(CPSConstants.RENTLIST, incomeTaxMasterBean.getRentList());
//					incomeTaxMasterBean.setPayChallanDetailsList(incomeTaxBusiness.getSaveChallanDetails(incomeTaxMasterBean));
//					session.setAttribute("challanDetailsList",incomeTaxMasterBean.getPayChallanDetailsList());//This is for challanDetails
					incomeTaxMasterBean = incomeTaxBusiness.getSaveChallanDetails(incomeTaxMasterBean);
					//for totals display--kumari
					incomeTaxMasterBean = incomeTaxBusiness.getITTotals(incomeTaxMasterBean);
					viewName = CPSConstants.ITAXCONFIGLIST;
				}
				else
					viewName = CPSConstants.RESULT;
			} else if (CPSUtils.compareStrings(CPSConstants.GETPAYMONTHDETAILS, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setSearchSfid((String) (session.getAttribute(CPSConstants.SEARCHSFID)));
				incomeTaxMasterBean.setSelectedFYear((String)session.getAttribute(CPSConstants.SEARCHFINYAER));
				incomeTaxBusiness.getEditEmpList(incomeTaxMasterBean);
				incomeTaxMasterBean=incomeTaxBusiness.getEmpList(incomeTaxMasterBean);
				incomeTaxMasterBean.setFinancialYear(incomeTaxBusiness.getFinancialYear(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())));
				incomeTaxMasterBean=incomeTaxBusiness.getPayMonthDetailsList(incomeTaxMasterBean);
				incomeTaxMasterBean=incomeTaxBusiness.getITTotals(incomeTaxMasterBean);
				viewName = CPSConstants.ITAXCONFIGLIST;
			}
			else if (CPSUtils.compareStrings(CPSConstants.CONFIGDETAILS, incomeTaxMasterBean.getParam())) {
				//incomeTaxMasterBean.setSearchSfid((String) (session.getAttribute(CPSConstants.SEARCHSFID)));
				//incomeTaxMasterBean.setSelectedFYear((String) (session.getAttribute(CPSConstants.SEARCHFINYAER)));
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitConfigDetails(incomeTaxMasterBean));
				viewName = CPSConstants.RESULT;
			}
			else if (CPSUtils.compareStrings(CPSConstants.CALUCULATEHOME, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxMasterBean.getFinYearList());
				//session.setAttribute("incometaxStatusList", incomeTaxBusiness.getIncomeTaxRunStatusList());
				session.setAttribute("incometaxDurationDetails", incomeTaxBusiness.getITDurationDetails());
				viewName = CPSConstants.ITCALUCULATEHOME;
			}/*else if (CPSUtils.compareStrings(CPSConstants.SUBMITITCALC, incomeTaxMasterBean.getParam())) {
				session.setAttribute("incometaxStatusList", incomeTaxBusiness.submitITCalcDetails(incomeTaxMasterBean));
				viewName ="incomeTaxClaculateList";
			}*/
			else if (CPSUtils.compareStrings("submitITDurationDetails", incomeTaxMasterBean.getParam())) {
				incomeTaxBusiness.submitITDurationDetails(incomeTaxMasterBean);
				session.setAttribute("incometaxDurationDetails", incomeTaxBusiness.getITDurationDetails());
				viewName ="incometaxDurationList";
			}else if (CPSUtils.compareStrings("deleteITDurationDetails", incomeTaxMasterBean.getParam())) {
				incomeTaxBusiness.deleteITDurationDetails(incomeTaxMasterBean);
				session.setAttribute("incometaxDurationDetails", incomeTaxBusiness.getITDurationDetails());
				viewName ="incometaxDurationList";
			}
			else if (CPSUtils.compareStrings(CPSConstants.SECTIONHOME, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute("financialYear", incomeTaxMasterBean.getFinYearList());
				incomeTaxMasterBean.setSectionList(incomeTaxBusiness.getITSectionList());
				session.setAttribute(CPSConstants.SECTIONLIST, incomeTaxMasterBean.getSectionList());
				session.setAttribute("jsonSectionList", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.SECTIONLIST)));
				viewName = CPSConstants.ITSECTIONHOME;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SUBITSECTION, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.insertSectionDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setSectionList(incomeTaxBusiness.getITSectionList());
				session.setAttribute(CPSConstants.SECTIONLIST, incomeTaxMasterBean.getSectionList());
				session.setAttribute("jsonSectionList", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.SECTIONLIST)));
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute("financialYear", incomeTaxMasterBean.getFinYearList());
				viewName = CPSConstants.ITSECTIONLIST;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SECTIONSDELETE, incomeTaxMasterBean.getParam())) {
				//nagendra.v
			/*	session.removeAttribute(CPSConstants.SECTIONLIST);
				session.removeAttribute("jsonSectionList");*/
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.deleteSectionDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute("financialYear", incomeTaxMasterBean.getFinYearList());
				incomeTaxMasterBean.setSectionList(incomeTaxBusiness.getITSectionList());
				session.setAttribute(CPSConstants.SECTIONLIST, incomeTaxMasterBean.getSectionList());
				session.setAttribute("jsonSectionList", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.SECTIONLIST)));
				viewName = CPSConstants.ITSECTIONLIST;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONLIST);
				
				
				
			/*	
				incomeTaxMasterBean.setSectionList(incomeTaxBusiness.getITSectionList());
				session.setAttribute(CPSConstants.SECTIONLIST, incomeTaxMasterBean.getSectionList());
				session.setAttribute("jsonSectionList", (JSONArray)JSONSerializer.toJSON(session.getAttribute(CPSConstants.SECTIONLIST)));
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONLIST);*/
			} else if (CPSUtils.compareStrings(CPSConstants.ALLOWANCECONFIG, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setConfBean(configurationBusiness.getConfigurationDetails());
				incomeTaxMasterBean.setIttransportAllowance(incomeTaxMasterBean.getConfBean().getItTraAllw());
				incomeTaxMasterBean.setItphyHandicapped(incomeTaxMasterBean.getConfBean().getItphysicallyHandicapped());
				incomeTaxMasterBean.setAgeLimitForSrCitizen(incomeTaxMasterBean.getConfBean().getSrCitizemMaxAge());
				viewName = CPSConstants.ITALLOWANCECONFIGURATION;
			} else if (CPSUtils.compareStrings(CPSConstants.SAVEALLOWANCECONFIG, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(configurationBusiness.submitConfigurationDetails(incomeTaxMasterBean.getConfigurationDetails()));
				viewName = CPSConstants.RESULT;
			}  else if (CPSUtils.compareStrings(CPSConstants.SUBMITRENT, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setSearchSfid((String) (session.getAttribute(CPSConstants.SEARCHSFID)));
				incomeTaxMasterBean.setSelectedFYear((String) (session.getAttribute(CPSConstants.SEARCHFINYAER)));
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitRentDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setRentList(incomeTaxBusiness.getITRentList(incomeTaxMasterBean));
				session.setAttribute(CPSConstants.RENTLIST, incomeTaxMasterBean.getRentList());
				viewName = CPSConstants.ITRENTLIST;	
			} else if (CPSUtils.compareStrings(CPSConstants.ITRENTDELETE, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.deleteRentDetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setSearchSfid((String) (session.getAttribute(CPSConstants.SEARCHSFID)));
				incomeTaxMasterBean.setSelectedFYear((String) (session.getAttribute(CPSConstants.SEARCHFINYAER)));
				incomeTaxMasterBean.setRentList(incomeTaxBusiness.getITRentList(incomeTaxMasterBean));
				session.setAttribute(CPSConstants.RENTLIST, incomeTaxMasterBean.getRentList());
				viewName = CPSConstants.ITRENTLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.MYITSLIPHOME, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxMasterBean.getFinYearList());
				viewName = CPSConstants.MYITSLIP;
			}else if (CPSUtils.compareStrings(CPSConstants.PRUPDATEALLW, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setEmpDesignationList(incomeTaxBusiness.getEmpDesignationList());
				incomeTaxMasterBean.setDesignationList(incomeTaxBusiness.getDesignationList());
				incomeTaxMasterBean.setPuaList(incomeTaxBusiness.getPUAList());
				session.setAttribute("empDesignationList", incomeTaxMasterBean.getEmpDesignationList());
				session.setAttribute("PayDesignationList", incomeTaxMasterBean.getDesignationList());
				session.setAttribute(CPSConstants.PUALIST, incomeTaxMasterBean.getPuaList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PRUPDATEALLWLIST);
				viewName = CPSConstants.PRUPDATEALLWHOME;
			}else if (CPSUtils.compareStrings(CPSConstants.MANGEPUA, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitPUADetails(incomeTaxMasterBean));
				incomeTaxMasterBean.setPuaList(incomeTaxBusiness.getPUAList());
				session.setAttribute(CPSConstants.PUALIST, incomeTaxMasterBean.getPuaList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PRUPDATEALLWLIST);
				viewName = CPSConstants.PRUPDATEALLWLIST;
			}else if (CPSUtils.compareStrings(CPSConstants.DELETEPUA, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.deletePUADetails(Integer.parseInt(incomeTaxMasterBean.getPk())));
				incomeTaxMasterBean.setPuaList(incomeTaxBusiness.getPUAList());
				session.setAttribute(CPSConstants.PUALIST, incomeTaxMasterBean.getPuaList());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PRUPDATEALLWLIST);
				viewName = CPSConstants.PRUPDATEALLWLIST;
			}else if (CPSUtils.compareStrings("dbMigrationHome", incomeTaxMasterBean.getParam())) {
				session.setAttribute("PayAutoRunList", payBillDAO.getPayAutoRunList());
				viewName ="databaseMigrationHome";
			}
			else if (CPSUtils.compareStrings("dbMigrationSubmit", incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitDBMigrationDetails(incomeTaxMasterBean.getsName()));
				viewName =CPSConstants.RESULT;
			}else if (CPSUtils.compareStrings("dbMigrationCompare", incomeTaxMasterBean.getParam())) {
				Map map=incomeTaxBusiness.compareDBMigrationDetails(incomeTaxMasterBean.getsName(),incomeTaxMasterBean.getMatched(),incomeTaxMasterBean);
				incomeTaxMasterBean.setCompList((List<DBComparisonDTO>)map.get("dbList"));
				session.setAttribute("creditsMatched", map.get("creditsMatched"));
				session.setAttribute("debitsMatched", map.get("debitsMatched"));
				session.setAttribute("recoveriesMatched", map.get("recoveriesMatched"));
				session.setAttribute("takeHomeMathced", map.get("takeHomeMathced"));
				session.setAttribute("compList", incomeTaxMasterBean.getCompList());
				session.setAttribute("totalCount", incomeTaxMasterBean.getCompList().size());
				session.setAttribute("matchedCount", map.get("matchedCount"));
				session.setAttribute("totRunEmpCount", map.get("totRunEmpCount"));
				//for credits,debits,recoveries matched or unmatched//kumari
				session.setAttribute("creditsList", map.get("creditsList"));
				session.setAttribute("debitsList", map.get("debitsList"));
				session.setAttribute("recoveriesList", map.get("recoveriesList"));
				//nagendra
				//for displaying the comparing month in jsp
				session.setAttribute("ToMonth",incomeTaxMasterBean.getCompareToMonth());
				session.setAttribute("FromMonth", incomeTaxMasterBean.getsName());
				
				
				//if(CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getType())){
					viewName ="PayDBComareList";
				//}else{
					//viewName ="PayDBComareIndividualList";
				//}
				
			}
			else if (CPSUtils.compareStrings(CPSConstants.SECTIONMAPPINGMASTER, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean = incomeTaxBusiness.getSectionMappingDetails(incomeTaxMasterBean);
				session.setAttribute("sectionList", incomeTaxMasterBean.getSectionList());
				session.setAttribute("sectionMappingList", incomeTaxMasterBean.getSectionMappingList());
			//nagendra.v
				session.setAttribute("savingsections",incomeTaxBusiness.getItSavingSections(incomeTaxMasterBean));
				session.setAttribute("uniquesections", incomeTaxBusiness.getUniqueSectionName());
			
				session.setAttribute("financialYear", incomeTaxMasterBean.getFinYearList());
				viewName = CPSConstants.ITSECTIONMAPPINGHOME;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONMAPPINGLIST);
			}	else if (CPSUtils.compareStrings("sectionName", incomeTaxMasterBean.getParam())) {
				//nagendra.v
				session.removeAttribute("sectionName");	
				session.setAttribute("sectionName",incomeTaxBusiness.getSectionName(incomeTaxMasterBean));
				//viewName = "ItSectionName";
				viewName = CPSConstants.RESULT;
				//session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONMAPPINGLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.SUBMITSECTIONMAPPINGDETAILS, incomeTaxMasterBean.getParam())) {
				                     
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitIncomeTaxSectionMappingDetails(incomeTaxMasterBean));
				incomeTaxMasterBean = incomeTaxBusiness.getSectionMappingDetails(incomeTaxMasterBean);
				session.setAttribute("sectionMappingList", incomeTaxMasterBean.getSectionMappingList());
				viewName = CPSConstants.ITSECTIONMAPPINGLIST;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONMAPPINGLIST);
			}else if (CPSUtils.compareStrings(CPSConstants.DELETESECTIONMAPPINGDETAILS, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.deleteIncomeTaxSectionMappingDetails(incomeTaxMasterBean));
				incomeTaxMasterBean = incomeTaxBusiness.getSectionMappingDetails(incomeTaxMasterBean);
				session.setAttribute("sectionMappingList", incomeTaxMasterBean.getSectionMappingList());
				viewName = CPSConstants.ITSECTIONMAPPINGLIST;
				session.setAttribute(CPSConstants.RETURN, CPSConstants.ITSECTIONMAPPINGLIST);
			}else if(CPSUtils.compareStrings("submitTransferEmpDetails", incomeTaxMasterBean.getParam())){
				incomeTaxBusiness.getEditEmpList(incomeTaxMasterBean);
				incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitTransferEmpDetails(incomeTaxMasterBean));
				viewName = CPSConstants.ITAXCONFIGLIST;
			}else if(CPSUtils.compareStrings("submitArrears", incomeTaxMasterBean.getParam())){
				//session.setAttribute("arrearsDetailsList",incomeTaxBusiness.saveITarrears(incomeTaxMasterBean));
				
			
				request.setAttribute("message",incomeTaxBusiness.saveItarrear(incomeTaxMasterBean));
				session.setAttribute("arrearsDetailslist",incomeTaxBusiness.getITarrears(incomeTaxMasterBean));
				//session.setAttribute("arrearsDetailsList", (JSONArray)JSONSerializer.toJSON(session.getAttribute("arrearsDetailslist")));
				/*session.setAttribute("");incomeTaxBusiness.saveITarrears(incomeTaxMasterBean);
				session.
				*/
				//incomeTaxMasterBean.setMessage(incomeTaxBusiness.submitTransferEmpDetails(incomeTaxMasterBean));
				viewName = "ItArrearsList";
				//viewName = CPSConstants.RESULT;
			}
			
			
			// Start: Over Time(OT) Added By Naresh
			else if(CPSUtils.compareStrings(CPSConstants.OVERTIMEHOME, incomeTaxMasterBean.getParam())) {
				session.setAttribute("otYear", incomeTaxBusiness.getOTYearList());
				incomeTaxMasterBean.setCategories(incomeTaxBusiness.getCategoryList());
				session.setAttribute("category", incomeTaxMasterBean.getCategories());
				incomeTaxMasterBean.setDesignations(incomeTaxBusiness.getDesignations());
				session.setAttribute("designation", incomeTaxMasterBean.getDesignations());
				viewName = CPSConstants.OVERTIMEHOMEPAGE;
			} else if(CPSUtils.compareStrings(CPSConstants.OTCALCULATION, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean = incomeTaxBusiness.getEmpOTAmount(incomeTaxMasterBean);
				if(CPSUtils.compareStrings(CPSConstants.EMPNOTEXISTS, incomeTaxMasterBean.getMessage()) || CPSUtils.compareStrings(CPSConstants.OTNOTAPPLICABLE, incomeTaxMasterBean.getMessage()))
					viewName = CPSConstants.RESULT;
				else {
					session.removeAttribute("otBillList");
					session.setAttribute("otBill", incomeTaxMasterBean.getOverTime());
					viewName = CPSConstants.OVERTIMELISTPAGE;
				}
			} else if(CPSUtils.compareStrings(CPSConstants.OTCALCULATIONLIST, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean = incomeTaxBusiness.getOTCalculationDetails(incomeTaxMasterBean);
				session.removeAttribute("otBill");
				session.setAttribute("otBillList", incomeTaxMasterBean.getOverTimeList());
				viewName = CPSConstants.OVERTIMELISTPAGE;
			} else if(CPSUtils.compareStrings(CPSConstants.OTSAVEDETAILS, incomeTaxMasterBean.getParam())) {
				incomeTaxMasterBean = incomeTaxBusiness.saveOTDetails(incomeTaxMasterBean);
				session.removeAttribute("otBill");
				session.setAttribute("otBillList", incomeTaxMasterBean.getOverTimeList());
				viewName = CPSConstants.OVERTIMELISTPAGE;
			} // End: Over Time(OT)
			
			//new code for reports page
			else if(CPSUtils.compareStrings(CPSConstants.MYITSLIPFINANCE, incomeTaxMasterBean.getParam())){
				incomeTaxMasterBean.setSearchSfid((String)session.getAttribute("Labid"));
				incomeTaxMasterBean.setFinYearList(incomeTaxBusiness.getFinYearList());
				session.setAttribute(CPSConstants.FINYEARLIST, incomeTaxMasterBean.getFinYearList());
				viewName=CPSConstants.MYITSLIP;
			}
			//Mis pay comparision screen
			else if(CPSUtils.compareStrings(CPSConstants.MISPAYDBCOMPARISIONHOME, incomeTaxMasterBean.getParam())){
				session.setAttribute("PayAutoRunList", payBillDAO.getPayAutoRunList());
				viewName="databaseComparisionWithMonths";
			}
         	mav = new ModelAndView(viewName, CPSConstants.INCOMETAXMASTER, incomeTaxMasterBean);
			if (!CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, incomeTaxMasterBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getRemarks())) {
				mav.addObject(CPSConstants.REMARKS, incomeTaxMasterBean.getRemarks());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}

}
