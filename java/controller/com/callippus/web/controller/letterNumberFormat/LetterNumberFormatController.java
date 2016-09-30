package com.callippus.web.controller.letterNumberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatBean;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.business.letterNumberFormat.LetterNumberFormatBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/letterNumberFormat")
@SessionAttributes
public class LetterNumberFormatController {

	private static Log log = LogFactory.getLog(LetterNumberFormatController.class);
	@Autowired
	private LetterNumberFormatBusiness letterNumberBusiness;
	@Autowired
	private FileUpload fileUpload;
	
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView onSubmit(@ModelAttribute("letterNumberFormat") LetterNumberFormatBean letterNumberFormatBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = "";
		String message = "";
		Map<String,String> modelMap = null;

		try { 
			ErpAction.userChecks(request); 
			HttpSession session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.PAGING, letterNumberFormatBean.getParam())) {
				mav = new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(), "letterNumberFormat", letterNumberFormatBean);
				return mav;
			}
			
			letterNumberFormatBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
			if(CPSUtils.compareStrings("letterNumberMaster", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=letterNumberMaster");
				viewName = "LetterNumberMaster";
				List deptList = letterNumberBusiness.getDeptList(); 
				session.setAttribute("deptList", deptList);
				List letterNumberSeriesList = letterNumberBusiness.getLetterNumberSeriesList();
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberSeriesList")))session.removeAttribute("jsonLetterNumberSeriesList");
				if(CPSUtils.checkList(letterNumberSeriesList))
				{
					session.setAttribute("jsonLetterNumberSeriesList", (JSONArray)JSONSerializer.toJSON(letterNumberSeriesList));
				}
				
					
			}
			if(CPSUtils.compareStrings("manageLetterNumber", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageLetterNumber");
				viewName = "LetterNumberMasterDataList";
				message = letterNumberBusiness.manageLetterNumberSeries(letterNumberFormatBean);
				List letterNumberSeriesList = letterNumberBusiness.getLetterNumberSeriesList();
				if(CPSUtils.checkList(letterNumberSeriesList))
				{
					session.setAttribute("jsonLetterNumberSeriesList", (JSONArray)JSONSerializer.toJSON(letterNumberSeriesList));
				}
			}
			if(CPSUtils.compareStrings("deleteLetterNumber", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=deleteLetterNumber");
				viewName = "LetterNumberMasterDataList";
				letterNumberFormatBean = letterNumberBusiness.deleteLetterNumberSeries(letterNumberFormatBean);
				message = letterNumberFormatBean.getMessage();
				session.setAttribute("reason", letterNumberFormatBean.getReason());
				List letterNumberSeriesList = letterNumberBusiness.getLetterNumberSeriesList();
				if(CPSUtils.checkList(letterNumberSeriesList))
				{
					session.setAttribute("jsonLetterNumberSeriesList", (JSONArray)JSONSerializer.toJSON(letterNumberSeriesList));
				}
			}
			if(CPSUtils.compareStrings("letterNumberFormatMaster", request.getParameter(CPSConstants.PARAM)) || CPSUtils.compareStrings("letterNumberFormatMaster1", request.getParameter(CPSConstants.PARAM))) 
			{
				log.debug("TrainingMasterController --> onSubmit --> param=letterNumberFormatMaster");
				session.setAttribute("LetterNumberEditFlag", "flag");  // Letter number format screen from admin
				
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberFormatList")))session.removeAttribute("jsonLetterNumberFormatList");
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberSerisList")))session.removeAttribute("jsonLetterNumberSerisList");
				if(CPSUtils.compareStrings("letterNumberFormatMaster1", request.getParameter(CPSConstants.PARAM)))
					session.setAttribute("LetterNumberEditFlag", "");												// Letter number format screen from myadmin
				
				viewName = "LetterNumberFormatMaster";
				List deptList = letterNumberBusiness.getDeptListBasedOnRole(letterNumberFormatBean); 
				session.setAttribute("deptList", deptList);
				
				if(!CPSUtils.compareStrings("letterNumberFormatMaster1", request.getParameter(CPSConstants.PARAM))) 
				{
					
					List fileTypeList = letterNumberBusiness.getFileTypeList();
					session.setAttribute("fileTypeList", fileTypeList);
				}
				//if click on Goto Letter nUmber Format. Then for field set(department code && series )
			if(CPSUtils.compareStrings("letterNumberFormatMaster",letterNumberFormatBean.getBack()))
			{
				List deptSeriesList = letterNumberBusiness.getLetterNumberSerisList();
				if(CPSUtils.checkList(deptSeriesList))
				{
					session.setAttribute("jsonLetterNumberSerisList", (JSONArray)JSONSerializer.toJSON(deptSeriesList));  //json for fieldset (department code && series) it also used to check Department series(Letter Number Series) is created for the department or not 
				}
				}
			}
			// onchange of department getting details of department.
			if(CPSUtils.compareStrings("getDeptLetterNumberFormat", request.getParameter(CPSConstants.PARAM))) 
			{
				
				log.debug("TrainingMasterController --> onSubmit --> param=getDeptLetterNumberFormat");
				viewName = "LetterNumberFormatMasterDataList";
				
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberFormatList")))session.removeAttribute("jsonLetterNumberFormatList");
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberSerisList")))session.removeAttribute("jsonLetterNumberSerisList");
				
				List letterNumberFormatList = letterNumberBusiness.getLetterNumberFormatList(letterNumberFormatBean);
				
				if(CPSUtils.checkList(letterNumberFormatList))
				{
					session.setAttribute("jsonLetterNumberFormatList", (JSONArray)JSONSerializer.toJSON(letterNumberFormatList));  
				}
				List deptSeriesList = letterNumberBusiness.getLetterNumberSerisList();
				if(CPSUtils.checkList(deptSeriesList))
				{
					session.setAttribute("jsonLetterNumberSerisList", (JSONArray)JSONSerializer.toJSON(deptSeriesList));  //json for fieldset (department code && series) it also used to check Department series(Letter Number Series) is created for the department or not 
				}
								
			}
			if(CPSUtils.compareStrings("manageLetterNumberFormat", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageLetterNumberFormat");
				viewName = "LetterNumberFormatMasterDataList";
				message = letterNumberBusiness.manageLetterNumberFormat(letterNumberFormatBean);
				List letterNumberFormatList = letterNumberBusiness.getLetterNumberFormatList(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberFormatList")))session.removeAttribute("jsonLetterNumberFormatList");
				if(CPSUtils.checkList(letterNumberFormatList))
				{
					session.setAttribute("jsonLetterNumberFormatList", (JSONArray)JSONSerializer.toJSON(letterNumberFormatList));
				}
			}
			if(CPSUtils.compareStrings("deleteLetterNumberFormat", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=deleteLetterNumberFormat");
				viewName = "LetterNumberFormatMasterDataList";
				letterNumberFormatBean = letterNumberBusiness.deleteLetterNumberFormat(letterNumberFormatBean);
				message = letterNumberFormatBean.getMessage();
				session.setAttribute("reason", letterNumberFormatBean.getReason());
				List letterNumberFormatList = letterNumberBusiness.getLetterNumberFormatList(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberFormatList")))session.removeAttribute("jsonLetterNumberFormatList");
				if(CPSUtils.checkList(letterNumberFormatList))
				{
					session.setAttribute("jsonLetterNumberFormatList", (JSONArray)JSONSerializer.toJSON(letterNumberFormatList));
				}
			}
			if(CPSUtils.compareStrings("getIncrementLetterNumberFormat", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=getIncrementLetterNumberFormat");
				viewName = "LetterNumberFormatDetails";
				letterNumberFormatBean = letterNumberBusiness.getIncrementLetterNumberFormat(letterNumberFormatBean);
				List ionMstList = letterNumberBusiness.getIONMstList(letterNumberFormatBean);
				session.setAttribute("letterNumber",(JSONArray)JSONSerializer.toJSON(letterNumberFormatBean.getNumberList()));
				
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList")))session.removeAttribute("jsonIonMstList");
				if(CPSUtils.checkList(ionMstList))
				{
					session.setAttribute("jsonIonMstList", (JSONArray)JSONSerializer.toJSON(ionMstList));
				}
			}
			if(CPSUtils.compareStrings("manageLetterNumberFormatDetails", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageLetterNumberFormatDetails");
				viewName = "ionDataList";
				letterNumberFormatBean = letterNumberBusiness.manageLetterNumberFormatDetails(letterNumberFormatBean);
				message = letterNumberFormatBean.getMessage();
				session.setAttribute("letterNumber",(JSONArray)JSONSerializer.toJSON(letterNumberFormatBean.getNumberList()));
				List ionMstList = letterNumberBusiness.getIONMstList(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList")))session.removeAttribute("jsonIonMstList");
				if(CPSUtils.checkList(ionMstList))
				{
					session.setAttribute("jsonIonMstList", (JSONArray)JSONSerializer.toJSON(ionMstList));
				}
			}
			//code for deleting records in the letter number format details screen
			if(CPSUtils.compareStrings("deleteLetterNumberFormatDetails", request.getParameter(CPSConstants.PARAM))){
				viewName = "ionDataList";
				letterNumberFormatBean.setMessage(letterNumberBusiness.deleteLetterNumberFormatDetails(Integer.parseInt(letterNumberFormatBean.getPk())));
				message = letterNumberFormatBean.getMessage();
				List ionMstList = letterNumberBusiness.getIONMstList(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList")))session.removeAttribute("jsonIonMstList");
				if(CPSUtils.checkList(ionMstList))
				{
					session.setAttribute("jsonIonMstList", (JSONArray)JSONSerializer.toJSON(ionMstList));
				}
			}
			if(CPSUtils.compareStrings("IONDetails", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=IONDetails");
				viewName = "IONDetails";
				//List ionDetails = letterNumberBusiness.getIONDetails(letterNumberFormatBean);
				List deptList = letterNumberBusiness.getDeptListBasedOnRole(letterNumberFormatBean); 
				
				session.setAttribute("deptList", deptList);
				if(!CPSUtils.isNull(session.getAttribute("jsonIonDetails")))session.removeAttribute("jsonIonDetails");
//				if(CPSUtils.checkList(ionDetails))
//				{
//					session.setAttribute("jsonIonDetails", (JSONArray)JSONSerializer.toJSON(ionDetails));
//				}
			}
			if(CPSUtils.compareStrings("IONDetailsDataList", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=IONDetailsDataList");
				viewName = "IONDetailsDataList";
				List ionDetails = letterNumberBusiness.getIONDetails(letterNumberFormatBean);
				
				if(!CPSUtils.isNull(session.getAttribute("jsonIonDetails")))session.removeAttribute("jsonIonDetails");
				if(CPSUtils.checkList(ionDetails))
				{
					session.setAttribute("jsonIonDetails", (JSONArray)JSONSerializer.toJSON(ionDetails));
				}
			}
			
			if(CPSUtils.compareStrings("ionMaster", request.getParameter(CPSConstants.PARAM))) 
			{
				log.debug("TrainingMasterController --> onSubmit --> param=ionMaster");
				viewName = "IONMaster"; 
				letterNumberFormatBean = letterNumberBusiness.getIONMstDetails(letterNumberFormatBean);
				List<IONDTO> list1=letterNumberBusiness.getIONDisplayDetails(letterNumberFormatBean);
				letterNumberFormatBean.setDisplayIONDetails(list1);
				//Enclosers File List
				session.setAttribute("fileEncloserList", letterNumberFormatBean.getFileEncloserList());
				
				//Initiated,Forward,Approved Sfids
				List sfidList1 = letterNumberBusiness.getSfidList(letterNumberFormatBean.getSfid(),"sfid",letterNumberFormatBean);
				session.setAttribute("initiatedSfidList", sfidList1);
				if(letterNumberFormatBean.getCirculateVisible()!=null){
					if((letterNumberFormatBean.getCirculationStatus().equals("1") && letterNumberFormatBean.getCirculateVisible().equals("1")) || (letterNumberFormatBean.getCirculationStatus().equals("1") && letterNumberFormatBean.getSelectStatus().equals("0"))){
						List sfidList2 = letterNumberBusiness.getSfidList(letterNumberFormatBean.getIonInitiatedSfid(),"forwardBy",letterNumberFormatBean);
						session.setAttribute("forwardSfidList", sfidList2);
						}
				}else{
							List sfidList2 = letterNumberBusiness.getSfidList(letterNumberFormatBean.getSfid(),"forwardBy",letterNumberFormatBean); // geting the boss of login sfid
							//List sfidList2 = letterNumberBusiness.getSfidList(letterNumberFormatBean.getIonInitiatedSfid(),"forwardBy",letterNumberFormatBean);
							session.setAttribute("forwardSfidList", sfidList2);
				}
				List sfidList3 = letterNumberBusiness.getSfidList(letterNumberFormatBean.getSfid(),"approvedBy",letterNumberFormatBean);
				session.setAttribute("approvedSfidList", sfidList3);
				
				//Initiated,Forward,Approved Roles
				session.setAttribute("initiatedRoleList", letterNumberFormatBean.getIonInitiatedRoleList());
				session.setAttribute("forwardRoleList", letterNumberFormatBean.getIonForwardRoleList());
				session.setAttribute("approvedRoleList", letterNumberFormatBean.getIonApprovedRoleList());
				
				
				//department Selection
				List departmentList = letterNumberBusiness.getDeptList(); 
				List list = departmentList;
				List selectedList = letterNumberFormatBean.getDeparmentList();
				List selectedListCopy = letterNumberFormatBean.getDeparmentListCopy();
				letterNumberBusiness.removeLists(list, selectedList,"ionDept");
				letterNumberBusiness.removeLists(list, selectedListCopy,"ionDept");
				departmentList = list;
				
				session.setAttribute("departmentList", departmentList);
				session.setAttribute("ionDeptList", selectedList);
				session.setAttribute("ionDeptListCopy", selectedListCopy);
				
				//desig Selection
				List desiList = letterNumberBusiness.designationList();
				list = desiList;
				selectedList = letterNumberFormatBean.getDesignList();
				selectedListCopy = letterNumberFormatBean.getDesignListCopy();
				letterNumberBusiness.removeLists(list, selectedList,"ionDesign");
				letterNumberBusiness.removeLists(list, selectedListCopy,"ionDesign");
				desiList = list;
				session.setAttribute("designList", desiList);
				session.setAttribute("ionDesignList", selectedList);
				session.setAttribute("ionDesignListCopy", selectedListCopy);
				
				//role Selection
				List deptRoleBasedList = letterNumberBusiness.getDeptRoleBasedList(); 
				list = deptRoleBasedList;
				selectedList = letterNumberFormatBean.getOrgRoleList();
				selectedListCopy = letterNumberFormatBean.getOrgRoleListCopy();
				letterNumberBusiness.removeLists(list, selectedList,"ionOrgRole");
				letterNumberBusiness.removeLists(list, selectedListCopy,"ionOrgRole");
				deptRoleBasedList = list;
				session.setAttribute("roleList", deptRoleBasedList);
				session.setAttribute("ionRoleList", selectedList);
				session.setAttribute("ionRoleListCopy", selectedListCopy);
				
				//role hirarchy Selection
				List roleHirarchyList = letterNumberBusiness.getRoleHirarchyList(); 
				list = roleHirarchyList;
				selectedList = letterNumberFormatBean.getRoleHirarchyList();
				selectedListCopy = letterNumberFormatBean.getRoleHirarchyListCopy();
				letterNumberBusiness.removeLists(list, selectedList,"ionRoleHirarchy");
				letterNumberBusiness.removeLists(list, selectedListCopy,"ionRoleHirarchy");
				roleHirarchyList = list;
				session.setAttribute("roleHirarchyList", roleHirarchyList);
				session.setAttribute("ionRoleHirarchyList", selectedList);
				session.setAttribute("ionRoleHirarchyListCopy", selectedListCopy);
				
				//Sfid selection
				List sfidList = letterNumberBusiness.getSfidList("","", letterNumberFormatBean);
				list = sfidList;
				selectedList = letterNumberFormatBean.getSfidList();
				selectedListCopy = letterNumberFormatBean.getSfidListCopy();
				letterNumberBusiness.removeSfidLists(list, selectedList,"ionSfid");
				letterNumberBusiness.removeSfidLists(list, selectedListCopy,"ionSfid");
				sfidList = list;
				session.setAttribute("sfidList", sfidList);
				session.setAttribute("ionSfidList", selectedList);
				session.setAttribute("ionSfidListCopy", selectedListCopy);
				
				//organization Selection
				List orgList = letterNumberBusiness.getOrganizations(letterNumberFormatBean); 
				list = orgList;
				selectedList = letterNumberFormatBean.getOrgList();
				selectedListCopy = letterNumberFormatBean.getOrgListCopy();
				letterNumberBusiness.removeLists(list, selectedList,"org");
				letterNumberBusiness.removeLists(list, selectedListCopy,"org");
				orgList = list;
				session.setAttribute("orgList", orgList);
				session.setAttribute("ionOrgList", selectedList);
				session.setAttribute("ionOrgListCopy", selectedListCopy);
				
				
				
				
			
				
				
				
				
				
			}
			if(CPSUtils.compareStrings("getLetterNumberFormatList", request.getParameter(CPSConstants.PARAM))) 
			{
				log.debug("TrainingMasterController --> onSubmit --> param=getLetterNumberFormatList");
				viewName = "LetterNumberION";
				
				List letterNumberFormatList = letterNumberBusiness.getLetterNumberFormatList(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("jsonLetterNumberFormatList")))session.removeAttribute("jsonLetterNumberFormatList");
				if(CPSUtils.checkList(letterNumberFormatList))
				{
					session.setAttribute("jsonLetterNumberFormatList", (JSONArray)JSONSerializer.toJSON(letterNumberFormatList));
				}
				List ionMstList = letterNumberBusiness.getIONMasterList(letterNumberFormatBean);
				
				if(!CPSUtils.isNull(session.getAttribute("jsonIonMstList")))session.removeAttribute("jsonIonMstList");
				if(CPSUtils.checkList(ionMstList))
				{
					session.setAttribute("jsonIonMstList", (JSONArray)JSONSerializer.toJSON(ionMstList));
				}
			}
			if(CPSUtils.compareStrings("getIONInitiatedRoleList", request.getParameter(CPSConstants.PARAM)) || CPSUtils.compareStrings("getIONForwardRoleList", request.getParameter(CPSConstants.PARAM)) || CPSUtils.compareStrings("getIONApprovedRoleList", request.getParameter(CPSConstants.PARAM))) 
			{
				log.debug("TrainingMasterController --> onSubmit --> param=getIONInitiatedRoleList");
				viewName = "IONInitiatedRoleList";
				session.setAttribute("getIONRoleList", request.getParameter(CPSConstants.PARAM));
				
				if(CPSUtils.compareStrings("getIONInitiatedRoleList", request.getParameter(CPSConstants.PARAM)))
				{
				List list = letterNumberBusiness.getIONInitiatedRoleList(letterNumberFormatBean,"initiatedBy");
				if(!CPSUtils.isNull(session.getAttribute("jsonIONInitiatedRoleList")))session.removeAttribute("jsonIONInitiatedRoleList");
				if(CPSUtils.checkList(list))
				{
					session.setAttribute("jsonIONInitiatedRoleList", (JSONArray)JSONSerializer.toJSON(list));
				}
				}
				
				if(CPSUtils.compareStrings("getIONForwardRoleList", request.getParameter(CPSConstants.PARAM))) 
				{
					letterNumberFormatBean.setIonForwardSfid(letterNumberFormatBean.getIonInitiatedSfid());
				List list = letterNumberBusiness.getIONInitiatedRoleList(letterNumberFormatBean,"forwardBy");
				if(!CPSUtils.isNull(session.getAttribute("jsonIONInitiatedRoleList")))session.removeAttribute("jsonIONForwardRoleList");
				if(CPSUtils.checkList(list))
				{
					session.setAttribute("jsonIONForwardRoleList", (JSONArray)JSONSerializer.toJSON(list));
				}
				}
				
				if(CPSUtils.compareStrings("getIONApprovedRoleList", request.getParameter(CPSConstants.PARAM)))
				{
					letterNumberFormatBean.setIonApprovedSfid(letterNumberFormatBean.getIonInitiatedSfid());
				List list = letterNumberBusiness.getIONInitiatedRoleList(letterNumberFormatBean,"approvedBy");
				if(!CPSUtils.isNull(session.getAttribute("jsonIONApprovedRoleList")))session.removeAttribute("jsonIONApprovedRoleList");
				if(CPSUtils.checkList(list))
				{
					session.setAttribute("jsonIONApprovedRoleList", (JSONArray)JSONSerializer.toJSON(list));
				}
				}
				
			}
			if(CPSUtils.compareStrings("uploadEncloserFile", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageIONMaster");
				viewName = "Result";
				Map<String, MultipartFile> map =new HashMap<String, MultipartFile>();
				map.put("ionFile#"+letterNumberFormatBean.getEnclosureFileName()[0], letterNumberFormatBean.getEnclosureFile());
				
				FileUploadBean fub=new FileUploadBean();
				fub.setFiles(map);
				fileUpload.uploadFileToDatabase(fub);
				letterNumberBusiness.checkHRDGIon(letterNumberFormatBean,fub);
				message = letterNumberBusiness.uploadEncloserFile(letterNumberFormatBean,fub);
				
			}
			
			if(CPSUtils.compareStrings("showFile", request.getParameter(CPSConstants.PARAM)))
			{
				//viewName = "IONMaster"; 
				FilesBean file = fileUpload.downloadFromDatabase(Integer.parseInt(letterNumberFormatBean.getFileId()));
				response.setContentType(file.getType());
				response.setContentLength(file.getFile().length);
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getFilename() + "\"");
				FileCopyUtils.copy(file.getFile(), response.getOutputStream());
			}
			if(CPSUtils.compareStrings("removeFile", request.getParameter(CPSConstants.PARAM)))
			{
				viewName = "Result"; 
				fileUpload.deleteFileFromDatabase(Integer.parseInt(letterNumberFormatBean.getFileId()));
				message = letterNumberBusiness.removeIONEncloserFile(letterNumberFormatBean);
				//request.setAttribute(CPSConstants.PARAM,"getEncloserFileList");
			}
			if(CPSUtils.compareStrings("getEncloserFileList", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageIONMaster");
				viewName = "EncloserFileList";
				List list = letterNumberBusiness.getIONFileList(letterNumberFormatBean);
				if(CPSUtils.compareStrings("getEnclosedFileList", request.getParameter("type")))
					viewName = "EnclosedFileList";
				session.setAttribute("fileEncloserList", list);
				session.removeAttribute("jsonfileEncloserList");
				if(CPSUtils.checkList(list))
				
				session.setAttribute("jsonfileEncloserList", (JSONArray)JSONSerializer.toJSON(list));
				session.setAttribute("getIONRoleList", request.getParameter(CPSConstants.PARAM));
				viewName = "IONInitiatedRoleList";
			}
					
			if(CPSUtils.compareStrings("manageIONMaster", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageIONMaster");
				viewName = "Result";
				message = letterNumberBusiness.manageIONMaster(letterNumberFormatBean);
				
			}
			if(CPSUtils.compareStrings("manageCirculation", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageIONMaster");
				viewName = "CirculateIONDataList";
				message = letterNumberBusiness.manageCirculation(letterNumberFormatBean);
				
				List list = letterNumberBusiness.getIONListToCirculate(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("jsonIONListToCirculate")))session.removeAttribute("jsonIONListToCirculate");
				if(CPSUtils.checkList(list))
				{
					session.setAttribute("jsonIONListToCirculate", (JSONArray)JSONSerializer.toJSON(list));
				}
				
			}
			if(CPSUtils.compareStrings("CirculateION", request.getParameter(CPSConstants.PARAM)))
			{
				log.debug("TrainingMasterController --> onSubmit --> param=manageIONMaster");
				viewName = "CirculateION";
				List list = letterNumberBusiness.getIONListToCirculate(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("jsonIONListToCirculate")))session.removeAttribute("jsonIONListToCirculate");
				if(CPSUtils.checkList(list))
				{
					session.setAttribute("jsonIONListToCirculate", (JSONArray)JSONSerializer.toJSON(list));
				}
				
				
				if(CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRefOrgRoleId()))
				{
				List userOrgRoleList = letterNumberBusiness.getUserOrgRoleList(letterNumberFormatBean);
				if(!CPSUtils.isNull(session.getAttribute("userOrgRoleList")))session.removeAttribute("userOrgRoleList");
				if(CPSUtils.checkList(userOrgRoleList))
				session.setAttribute("userOrgRoleList", userOrgRoleList);
				}
				if(CPSUtils.compareStrings("CirculateIONDataList", request.getParameter(CPSConstants.TYPE)))
					viewName = "CirculateIONDataList";
			}
			
			mav = new ModelAndView(viewName, "letterNumberFormat", letterNumberFormatBean);
			mav.addObject(CPSConstants.MESSAGE, message);
			mav.addAllObjects(modelMap);
			session.setAttribute(CPSConstants.RETURN, viewName);
			
		

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}


	

}


