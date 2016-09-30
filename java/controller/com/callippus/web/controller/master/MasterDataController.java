package com.callippus.web.controller.master;

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
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.dto.LetterNumberReferenceDTO;
import com.callippus.web.beans.master.MasterDataBean;
import com.callippus.web.business.master.MasterDataBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/master.htm")
@SessionAttributes
public class MasterDataController {

	private static Log log = LogFactory.getLog(MasterDataController.class);
	@Autowired
	private MasterDataBusiness masterDataBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.MASTER) MasterDataBean masterBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = null;
		HttpSession session = null;
		String message = "";
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.compareStrings(CPSConstants.PAGING, masterBean.getParam())) {
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.MASTER, masterBean);
			} else if (CPSUtils.isNullOrEmpty(masterBean.getParam())) {
				// masterBean.setType(CPSConstants.CATEGORY);
				if (!CPSUtils.isNullOrEmpty(request.getParameter(CPSConstants.TYPE)))
					masterBean.setType(request.getParameter(CPSConstants.TYPE));
				else if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.MASTERTYPE)))
					masterBean.setType(session.getAttribute(CPSConstants.MASTERTYPE).toString());
				else
					masterBean.setType(CPSConstants.CATEGORY);
				if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.DESIGNATION))
					masterBean.setParam(CPSConstants.DESIGNATION);
				else if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.QUALIFICATION))
					masterBean.setParam(CPSConstants.QUALIFICATION);
				else if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.RELIGION))
					masterBean.setParam(CPSConstants.RELIGION);
				else if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.PIN))
					masterBean.setParam(CPSConstants.PIN);
				else if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.DISCIPLINE))
					masterBean.setParam(CPSConstants.DISCIPLINE);
				
				else
					masterBean.setParam(CPSConstants.HOME);
			}
			if (!CPSUtils.isNullOrEmpty(request.getParameter(CPSConstants.TYPE))) {
				masterBean.setType(request.getParameter(CPSConstants.TYPE));
			}
			if (CPSUtils.compareStrings(CPSConstants.HOME, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=home");
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					masterBean = masterDataBusiness.getMasterData(masterBean);

					session.setAttribute(CPSConstants.MASTERLIST, masterBean.getMasterDataList());
					session.setAttribute(CPSConstants.MASTERTYPE, masterBean.getType());
					
					if (CPSUtils.compareStrings(CPSConstants.REQUEST, masterBean.getType())) {
						mav = new ModelAndView(CPSConstants.REQTYPEMASTER, CPSConstants.MASTER, masterBean);
						session.setAttribute(CPSConstants.RETURN, CPSConstants.REQTYPEMASTER);
						log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> RequestTypeMaster");
					} else {
						mav = new ModelAndView(CPSConstants.MASTERDATA, CPSConstants.MASTER, masterBean);
						session.setAttribute(CPSConstants.RETURN, CPSConstants.MASTERDATALIST);
						log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> MasterData");
					}
					if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.DISTRICT) || CPSUtils.compareStrings(masterBean.getType(), CPSConstants.SUBCATEGORY)
							|| CPSUtils.compareStrings(masterBean.getType(), CPSConstants.DISNUMBER) || CPSUtils.compareStrings(masterBean.getType(), CPSConstants.AWARDCATEGORY) 
							|| CPSUtils.compareStrings(masterBean.getType(), CPSConstants.SUBQUARTER)) {
						masterBean.setMasterDataList(masterDataBusiness.getParentDataList(masterBean.getType()));
						// mav.addObject(CPSConstants.PARENTLIST, parentList);
						session.setAttribute(CPSConstants.PARENTLIST, masterBean.getMasterDataList());
						session.setAttribute(CPSConstants.LEVEL, "parent");
					} else {
						session.setAttribute(CPSConstants.LEVEL, "sub");
					}
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param") + "&type=" + request.getParameter("type"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}

			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=manage");
				masterBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				message = masterDataBusiness.manageMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.MASTERLIST, masterBean.getMasterDataList());

				mav = new ModelAndView(CPSConstants.MASTERDATALIST, CPSConstants.MASTER, masterBean);
				if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.DISTRICT) || CPSUtils.compareStrings(masterBean.getType(), CPSConstants.SUBCATEGORY)
						|| CPSUtils.compareStrings(masterBean.getType(), CPSConstants.DISNUMBER)|| CPSUtils.compareStrings(masterBean.getType(), CPSConstants.SUBQUARTER)) {
					mav.addObject(CPSConstants.PARENTLIST, masterDataBusiness.getParentDataList(masterBean.getType()));
					mav.addObject(CPSConstants.LEVEL, "parent");
				}
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MASTERDATALIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> MasterDataList");
			} else if (CPSUtils.compareStrings(CPSConstants.DELETE, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=delete");
				message = masterDataBusiness.deleteMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.MASTERLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.MASTERDATALIST, CPSConstants.MASTER, masterBean);
				if (CPSUtils.compareStrings(masterBean.getType(), CPSConstants.DISTRICT) || CPSUtils.compareStrings(masterBean.getType(), CPSConstants.SUBCATEGORY) || CPSUtils.compareStrings(masterBean.getType(), CPSConstants.SUBQUARTER)) {
					mav.addObject(CPSConstants.PARENTLIST, masterDataBusiness.getParentDataList(masterBean.getType()));
					mav.addObject(CPSConstants.LEVEL, "parent");
				}
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MASTERDATALIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> MasterDataList");
			} /*
			 * else if (CPSUtils.compareStrings(CPSConstants.PAGING, masterBean.getParam())) { log.debug("MasterDataController --> onSubmit --> param=paging");
			 * 
			 * if(CPSUtils.compareStrings(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.DESIGMASTER) ||
			 * CPSUtils.compareStrings(session.getAttribute(CPSConstants.RETURN).toString(), CPSConstants.DESIGNATIONLISTPAGE)){
			 * 
			 * }else{ masterBean.setMasterDataList((List) session.getAttribute(CPSConstants.MASTERLIST)); }
			 * 
			 * 
			 * mav = new ModelAndView((String) session.getAttribute(CPSConstants.RETURN), CPSConstants.MASTER, masterBean);
			 * 
			 * }
			 */else if (CPSUtils.compareStrings(CPSConstants.DIRHOME, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=dirhome");

				masterBean.setMasterDataList((List) session.getAttribute(CPSConstants.MASTERLIST));
				mav = new ModelAndView(CPSConstants.MASTERDATALIST, CPSConstants.MASTER, masterBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.MASTERDATALIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> MasterDataList");
			} else if(CPSUtils.compareStrings("changeEmployee",masterBean.getParam())){
				message = masterDataBusiness.changeEmployeeDetails(masterBean.getChangeSfid().toUpperCase(), session);
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
			} else if(CPSUtils.compareStrings("changeEmployeeOne",masterBean.getParam())){
				message = masterDataBusiness.changeEmployeeDetails(masterBean.getChangeSfid().toUpperCase(), session);
				//mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.MASTER, masterBean);
				//mav.addObject(CPSConstants.MESSAGE, message);
			}
			
			else if (CPSUtils.compareStrings(CPSConstants.DESIGNATION, masterBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("MasterDataController --> onSubmit --> param=designation");
					masterBean = masterDataBusiness.getDesignationHome(session);
					mav = new ModelAndView(CPSConstants.DESIGMASTER, CPSConstants.MASTER, masterBean);
					session.setAttribute(CPSConstants.MASTERTYPE, CPSConstants.DESIGNATION);
					session.setAttribute("subCategoryJSON",(JSONArray) JSONSerializer.toJSON(masterBean.getSubCategoryList()));
					mav.addObject(CPSConstants.CATEGORYLIST, masterBean.getCategoryList());
					mav.addObject(CPSConstants.SUBCATEGORYLIST, masterBean.getSubCategoryList());
					mav.addObject(CPSConstants.GROUPLIST, masterBean.getGroupList());
					session.setAttribute(CPSConstants.RETURN, CPSConstants.DESIGMASTER);
					log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DesignationMaster");
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param") + "&type=" + request.getParameter("type"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEDESIGNATION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=manageDesignation");
				masterBean = masterDataBusiness.manageDesignation(masterBean, session);
				mav = new ModelAndView(CPSConstants.DESIGNATIONLISTPAGE, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, masterBean.getMessage());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DESIGNATIONLISTPAGE);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DesignationList");
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEDESIGNATION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=deleteDesignation");
				masterBean = masterDataBusiness.deleteDesignation(masterBean.getId(), session);
				mav = new ModelAndView(CPSConstants.DESIGNATIONLISTPAGE, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, masterBean.getMessage());
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DESIGNATIONLISTPAGE);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DesignationList");
			} else if (CPSUtils.compareStrings(CPSConstants.QUALIFICATION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=qualification");
				session.setAttribute(CPSConstants.MASTERTYPE, masterBean.getType());
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.QUALIFICATIONSLIST, masterBean.getMasterDataList());
				mav = new ModelAndView("Qualification", CPSConstants.MASTER, masterBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.QUALIFICATIONSLIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> Qualification");
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEQUALIFICATION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=manageQualification");
				message = masterDataBusiness.manageMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.QUALIFICATIONSLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.QUALIFICATIONSLIST, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.QUALIFICATIONSLIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> QualificationsList");
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEQUALIFICATION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=deleteQualification");
				message = masterDataBusiness.deleteMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.QUALIFICATIONSLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.QUALIFICATIONSLIST, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.QUALIFICATIONSLIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> QualificationsList");
			} else if (CPSUtils.compareStrings(CPSConstants.RELIGION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=religion");
				session.setAttribute(CPSConstants.MASTERTYPE, masterBean.getType());
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.RELIGIONLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.RELIGIONMASTER, CPSConstants.MASTER, masterBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.RELIGIONLIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> ReligionMaster");
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGERELIGION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=manageReligion");
				message = masterDataBusiness.manageMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.RELIGIONLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.RELIGIONLIST, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.RELIGIONLIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> ReligionList");
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEERELIGION, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=deleteReligion");
				message = masterDataBusiness.deleteMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.RELIGIONLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.RELIGIONLIST, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, "ReligionList");
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> ReligionList");
			} else if (CPSUtils.compareStrings(CPSConstants.PIN, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=pin");
				masterBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				session.setAttribute(CPSConstants.MASTERTYPE, masterBean.getType());
				masterBean.setSfidPinNoList(masterDataBusiness.getPinNoSfidList(masterBean.getSfID()));
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.PINLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.PINMASTER, CPSConstants.MASTER, masterBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PINLIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> PinNumber");
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEPIN, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=managePin");
				message = masterDataBusiness.manageMasterData(masterBean);
				masterBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				masterBean.setSfidPinNoList(masterDataBusiness.getPinNoSfidList(masterBean.getSfID()));
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.PINLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.PINMASTER, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.PINLIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> PinList");
			} else if (CPSUtils.compareStrings(CPSConstants.DISCIPLINE, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=discipline");
				session.setAttribute(CPSConstants.MASTERTYPE, masterBean.getType());
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.DISCIPLINELIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.DISCIPLINEMASTER, CPSConstants.MASTER, masterBean);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DISCIPLINELIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DisciplineMaster");
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGEDISCIPLINE, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=manageDiscipline");
				message = masterDataBusiness.manageMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.DISCIPLINELIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.DISCIPLINELIST, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DISCIPLINELIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DisciplineList");
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEDISCIPLINE, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=deleteDiscipline");
				message = masterDataBusiness.deleteMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.DISCIPLINELIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.DISCIPLINELIST, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.DISCIPLINELIST);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DisciplineList");
			} else if (CPSUtils.compareStrings(CPSConstants.CHANGEEMPSFID, masterBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("MasterDataController --> onSubmit --> param=changeEmpSfid");
					mav = new ModelAndView(CPSConstants.CHANGEEMPSFIDJSP, CPSConstants.MASTER, masterBean);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.CHANGEEMPSFIDJSP);
					log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> chageEmpSfid");
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.CHANGEDSFID, masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=changedSfid");
				masterBean = masterDataBusiness.empChangedSFID(masterBean);
				if (CPSUtils.compareStrings(masterBean.getMessage(), CPSConstants.SUCCESS)) {
					session.setAttribute(CPSConstants.CHANGESFID, masterBean.getChangeSfid());
					session.setAttribute(CPSConstants.CHANGESFIDNAME, masterBean.getChangeSfidName());
				}
				mav = new ModelAndView("ChangedSFId", CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, masterBean.getMessage());
				session.setAttribute(CPSConstants.RETURN, "ChangedSFId");
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> chageEmpSfid");
			} else if (CPSUtils.compareStrings(CPSConstants.SAVEREQTYPE, masterBean.getParam())) {
				message = masterDataBusiness.saveRequestTypes(masterBean);
				masterBean.setType(session.getAttribute(CPSConstants.MASTERTYPE).toString());
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute(CPSConstants.MASTERLIST, masterBean.getMasterDataList());
				mav = new ModelAndView(CPSConstants.RESULT, CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, CPSConstants.REQTYPEMASTER);
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> Result");
			}
			else if (CPSUtils.compareStrings("awardMaster", masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=awardMaster");
				masterBean.setMasterDataList(masterDataBusiness.getParentDataList(CPSConstants.AWARDCATEGORY));
				session.setAttribute(CPSConstants.PARENTLIST, masterBean.getMasterDataList());
				List list = masterDataBusiness.getAwardCategoryList();
				if(CPSUtils.checkList(list))
					session.setAttribute("jsonAwardCategoryList", (JSONArray) JSONSerializer.toJSON(list));
				else if(CPSUtils.isNull(session.getAttribute("jsonAwardCategoryList"))) session.removeAttribute("jsonAwardCategoryList");
				masterBean = masterDataBusiness.getMasterData(masterBean);
				mav = new ModelAndView("AwardMaster", CPSConstants.MASTER, masterBean);
				session.setAttribute(CPSConstants.RETURN, "AwardMaster");
				session.setAttribute(CPSConstants.JSONMASTERLIST, (JSONArray) JSONSerializer.toJSON(masterBean.getMasterDataList()));
				
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> AwardMaster");
			} else if (CPSUtils.compareStrings("manageAwardMaster", masterBean.getParam())) {
				log.debug("MasterDataController --> onSubmit --> param=manageAwardMaster");
				message = masterDataBusiness.manageAwardMasterData(masterBean);
				masterBean = masterDataBusiness.getMasterData(masterBean);
				mav = new ModelAndView("AwardMasterDataList", CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, "AwardMasterDataList");
				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DisciplineList");
				session.setAttribute(CPSConstants.JSONMASTERLIST, (JSONArray) JSONSerializer.toJSON(masterBean.getMasterDataList()));
			} else if (CPSUtils.compareStrings(CPSConstants.LETTERNUMBERREFERENCE, masterBean.getParam())) {
				log.debug("	MasterDataController --> onSubmit --> param=letterNumber");
				session.setAttribute(CPSConstants.MASTERTYPE, masterBean.getType());	
				session.removeAttribute("letterNumberList");
				mav = new ModelAndView("LetterNumberReference", CPSConstants.MASTER, masterBean);
				
				
//				message = masterDataBusiness.deleteMasterData(masterBean);
//				masterBean = masterDataBusiness.getMasterData(masterBean);
//				session.setAttribute(CPSConstants.DISCIPLINELIST, masterBean.getMasterDataList());
//				mav = new ModelAndView(CPSConstants.DISCIPLINELIST, CPSConstants.MASTER, masterBean);
//				mav.addObject(CPSConstants.MESSAGE, message);
//				session.setAttribute(CPSConstants.RETURN, CPSConstants.DISCIPLINELIST);
//				log.debug("::MasterDataController --> onSubmit End--> RESPONSE JSP ------> DisciplineList");
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGELETTERNUMBERREFERENCE, masterBean.getParam())) {
				log.debug("	MasterDataController --> onSubmit --> param=manageletterNumber");
				masterBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());      
				message = masterDataBusiness.manageLetter(masterBean);  
				masterBean = masterDataBusiness.getMasterData(masterBean);
				session.setAttribute("letterNumberList", masterBean.getLetterList());
				mav = new ModelAndView("LetterNumberList", CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
			}else if(CPSUtils.compareStrings(CPSConstants.DELETELETTERNUMBERREFERENCE,masterBean.getParam())){
				log.debug("MasterDataController --> onSubmit --> param=deleteLetterNumber");
				
				message=masterDataBusiness.deleteLetterNumberDetails(masterBean);
				if(CPSUtils.compareStrings(CPSConstants.DELETE, message)){
					
					masterBean = masterDataBusiness.getMasterData(masterBean);
					session.setAttribute("letterNumberList", masterBean.getLetterList());
					
				}
					mav = new ModelAndView("LetterNumberList", CPSConstants.MASTER, masterBean);
					mav.addObject(CPSConstants.RESULT, message);
					//mav.addObject(CPSConstants.RESULT, tadaBean.getResult());
				
			}
			else if(CPSUtils.compareStrings(CPSConstants.GETLETTERLIST,masterBean.getParam())){
				log.debug("MasterDataController --> onSubmit --> param=getLetter");	
				masterBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());	
				masterBean = masterDataBusiness.getMasterData(masterBean);
					
					session.setAttribute("letterNumberList", masterBean.getLetterList());
					mav = new ModelAndView("LetterNumberList", CPSConstants.MASTER, masterBean);
					//mav.addObject(CPSConstants.RESULT, message);
					//mav.addObject(CPSConstants.RESULT, tadaBean.getResult());
				
			}
			//delete in create award screen
			else if(CPSUtils.compareString("deleteAwardMaster", masterBean.getParam())){
				log.debug("MasterDataController --> onSubmit --> param=deleteAwardMaster");
				message = masterDataBusiness.deleteAwardMasterDetails(Integer.parseInt(masterBean.getId()));
				masterBean = masterDataBusiness.getMasterData(masterBean);
				mav = new ModelAndView("AwardMasterDataList", CPSConstants.MASTER, masterBean);
				mav.addObject(CPSConstants.MESSAGE, message);
				session.setAttribute(CPSConstants.RETURN, "AwardMasterDataList");
				session.setAttribute(CPSConstants.JSONMASTERLIST, (JSONArray) JSONSerializer.toJSON(masterBean.getMasterDataList()));
			}
			if (!CPSUtils.isNullOrEmpty(masterBean.getType()))
				mav.addObject(CPSConstants.TYPE, masterBean.getType());
			if (CPSUtils.checkList(masterBean.getMasterDataList())) {
				mav.addObject(CPSConstants.JSONMASTERLIST, (JSONArray) JSONSerializer.toJSON(masterBean.getMasterDataList()));
			
			}
			
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		
		}
	
		return mav;
		}
	
}