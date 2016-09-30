package com.callippus.web.cghs.controller.request;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.business.requestprocess.CghsRequestProcess;
import com.callippus.web.cghs.beans.request.CghsRequestBean;
import com.callippus.web.cghs.business.request.CghsRequestBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;

@Controller
@RequestMapping("/cghsRequest.htm")
@SessionAttributes
public class CghsRequestController {
	private static Log log = LogFactory.getLog(CghsRequestController.class);
	@Autowired
	private CghsRequestBusiness cghsRequestBusiness;
	@Autowired
	private CghsRequestProcess cghsRequestProcess;
	@Autowired
	private FileUpload fileUpload;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute CghsRequestBean cghsBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null; 	
		HttpSession session = null;
		String viewName = null;
		String temp3 = null;
		String message = null;
		try {
			log.debug("Executing  server details Are.....2::::::::::::::");
			log.debug("server name:"+request.getServerName());
			log.debug("RemoteHost:"+request.getRemoteHost());
			log.debug("RequestURL:"+request.getRequestURL());
			log.debug("RequestURI:"+request.getRequestURI());
			log.debug("ServerPort:"+request.getServerPort());
			log.debug("LocalPort:"+request.getLocalPort());
			log.debug("RemoteAddr:"+request.getRemoteAddr());
			ErpAction.userChecks(request);
			session=request.getSession(true);
			if (CPSUtils.isNullOrEmpty(cghsBean.getParam())) {
				cghsBean.setParam(session.getAttribute(CPSConstants.HOME).toString());
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, cghsBean.getParam())) {
				mav=new ModelAndView(session.getAttribute(CPSConstants.RETURN).toString(),CPSConstants.CGHS,cghsBean);
			}else if (CPSUtils.compareStrings(cghsBean.getParam(), "advancepaging")) {
				viewName = CPSConstants.CGHSADVANCELIST ;
			} else if(CPSUtils.compareStrings(cghsBean.getParam(), "settlementpaging")){
				viewName = CPSConstants.CGHSSETTLEMENTLIST ;
			} else if(CPSUtils.compareStrings(cghsBean.getParam(), "reimpaging")){
				mav = new ModelAndView(CPSConstants.CGHSREIMBURSEMENTDATALIST, CPSConstants.CGHS, cghsBean);
				
			}else if(CPSUtils.compareStrings(cghsBean.getParam(), "cghsEmergencyPaging")){
				viewName = CPSConstants.CGHSEMERGENCYLIST;
			}else if(CPSUtils.compareStrings(cghsBean.getParam(), "nonCghsPaging")){
				viewName = CPSConstants.NONCGHSREIMLIST;
			}
			if(CPSUtils.compareStrings(CPSConstants.HOME, cghsBean.getParam())){ 
				log.debug("CghsRequestController >>>>>>>>>> param=home");
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				String type = cghsBean.getType();
				cghsBean = cghsRequestBusiness.getTreatmentRequestHomeDetails(cghsBean);
				 //workflowmap.cghsAdvanceDTO.reimbursementId
				// cghsBean.setCghscardref(FamilyMemberDetailsDTO
				cghsBean.setType(type);
				mav=new ModelAndView(CPSConstants.TREATMENTREQUESTDETAILS,CPSConstants.CGHS,cghsBean);
				session.setAttribute(CPSConstants.HOME,CPSConstants.HOME);
				session.setAttribute(CPSConstants.JSONFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(cghsBean.getFamilyMemberList()));
			}else if(CPSUtils.compareStrings(CPSConstants.REFHOS, cghsBean.getParam())){ 
				log.debug("CghsRequestController >>>>>>>>>>  Ajax call param=refHos");
				cghsBean.setReferralHospitalList( cghsRequestBusiness.getReferralHospitalList(cghsBean.getPermissionDetails()));
				mav=new ModelAndView(CPSConstants.SELECTEDREFERRALHOSPITALIST,CPSConstants.CGHS,cghsBean);
			}
			
			///////////////
			  /* else if(CPSUtils.compareStrings(CPSConstants.MANAGEADVANCE, cghsBean.getParam())){
				log.debug("CghsRequestController >>>>>>>>>> param=manage,type="+cghsBean.getType());
				if(CPSUtils.isNullOrEmpty(cghsBean.getOfflineSFID())) {
					cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
					cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
				}else {
					/**
					 * In case of Offline Request
					
					cghsBean.setSfID(cghsBean.getOfflineSFID());
					cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
				}
				
				CGHSRequestProcessBean crpb = new CGHSRequestProcessBean();
				BeanUtils.copyProperties(cghsBean, crpb);
				setUplodedFilesNames(session,crpb);
				if((cghsBean.getRequestId().equals(""))){
					crpb.setReferrenceRequestID	(session.getAttribute("refReqID").toString()); 
				}
				else{
				crpb.setReferrenceRequestID(cghsBean.getRequestId());
				//crpb.setSfID((String) session.getAttribute(CPSConstants.SFID));
				}
				crpb.setIpAddress(request.getRemoteAddr());
				crpb.setSession(request.getSession(true));
				
				//String refid = 
			 
				
				String message = cghsRequestProcess.initWorkflow(crpb);
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE,CPSConstants.CGHS, cghsBean);
		     	mav.addObject(CPSConstants.MESSAGE, message);
			   }
			   */
			 /* else if(CPSUtils.compareStrings(CPSConstants.MANAGEREIMBURSEMENT , cghsBean.getParam())){
					log.debug("CghsRequestController >>>>>>>>>> param=manage,type="+cghsBean.getType());
					if(CPSUtils.isNullOrEmpty(cghsBean.getOfflineSFID())) {
						cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
						cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
					}else {
						cghsBean.setSfID(cghsBean.getOfflineSFID());
						cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
					}
				
					CGHSRequestProcessBean crpb = new CGHSRequestProcessBean();
					BeanUtils.copyProperties(cghsBean, crpb);
					setUplodedFilesNames(session,crpb);
					crpb.setReferrenceRequestID(cghsBean.getRequestId());
					//crpb.setSfID((String) session.getAttribute(CPSConstants.SFID));
					crpb.setIpAddress(request.getRemoteAddr());
					crpb.setSession(request.getSession(true));
					
					//String refid = session.getAttribute("refReqID").toString(); 
					
					//crpb.setReferrenceRequestID(refid);
					
					String message = cghsRequestProcess.initWorkflow(crpb);
					mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE,CPSConstants.CGHS, cghsBean);
			     	mav.addObject(CPSConstants.MESSAGE, message);
			/////////
			   }  
			   	*/
	          else if(CPSUtils.compareStrings(CPSConstants.MANAGE, cghsBean.getParam())){
	        	  Map<String, MultipartFile> map =new HashMap<String, MultipartFile>();
					log.debug("CghsRequestController >>>>>>>>>> param=manage,type="+cghsBean.getType());
					if(CPSUtils.isNullOrEmpty(cghsBean.getOfflineSFID())) {
						cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
						cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
					}else {
						/**
						 * In case of Offline Request
						 */
						cghsBean.setSfID(cghsBean.getOfflineSFID().toUpperCase());
						cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
					}
					
					CGHSRequestProcessBean crpb = new CGHSRequestProcessBean();
					if(!CPSUtils.isNullOrEmpty(cghsBean.getPrescriptionFile())){
						 session.setAttribute("prescriptionFile", cghsBean.getPrescriptionFile());
						 cghsBean.setPrescriptionFile(null);
					}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getEstimationFile())){
					    session.setAttribute("estimationFile", cghsBean.getEstimationFile());
					    cghsBean.setEstimationFile(null);
					}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFile())){
					    session.setAttribute("cghsCardFile", cghsBean.getCghsCardFile());
					    cghsBean.setCghsCardFile(null);
					}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFile())){
						 session.setAttribute("medicalBillsFile", cghsBean.getMedicalBillsFile());
						 cghsBean.setMedicalBillsFile(null);
					}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getRefundChequeFile())){
						 session.setAttribute("refundChequeFile", cghsBean.getRefundChequeFile());
						 cghsBean.setRefundChequeFile(null);
					}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getDischargeSummeryFile())){
						//if(!CPSUtils.isNullOrEmpty(cghsBean.getDischargeSummeryFile().getOriginalFilename())){
						 session.setAttribute("dischargeSummeryFile", cghsBean.getDischargeSummeryFile());
					//	}
					cghsBean.setDischargeSummeryFile(null);
					}
				
				  if(((cghsBean.getType().equals("cghsAdvance"))) || (cghsBean.getType().equals("cghsreimbursement"))){
						BeanUtils.copyProperties(cghsBean, crpb);
					if((CPSUtils.isNullOrEmpty(cghsBean.getRequestId()))){
						crpb.setReferrenceRequestID	(session.getAttribute("refReqID").toString()); 
					}
					
					else{ 
					crpb.setReferrenceRequestID(cghsBean.getRequestId());
					}
					}
					else{ 
						BeanUtils.copyProperties(cghsBean, crpb);
						 
						crpb.setReferrenceRequestID(cghsBean.getRequestId());
						
						}
					crpb.setIpAddress(request.getRemoteAddr());
					crpb.setSession(request.getSession(true));
					
				   if((CPSUtils.compareStrings(cghsBean.getType(), CPSConstants.TREATMENT))){
					   if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("prescriptionFile")).getOriginalFilename())){
					   map.put("prescriptionFile", ((MultipartFile)session.getAttribute("prescriptionFile")));
					   }FileUploadBean fub=new FileUploadBean();
					   if(map.size() != 0){
					   fub.setFiles(map);
						fub = fileUpload.uploadFileToDatabase(fub);
						crpb.setPrescriptionFile(String.valueOf(fub.getFileIds().get("prescriptionFile")));
					}}
					else if((CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.CGHSADVANCE))){
					if(!CPSUtils.isNullOrEmpty((((MultipartFile)session.getAttribute("estimationFile"))).getOriginalFilename())){	
					map.put("estimationFile", (MultipartFile)session.getAttribute("estimationFile"));
					}if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("cghsCardFile")).getOriginalFilename())){
					map.put("cghsCardFile", (MultipartFile)session.getAttribute("cghsCardFile"));
				    }FileUploadBean fub=new FileUploadBean();
					//System.out.println("hkhkl");
				    if(map.size() != 0){
				    fub.setFiles(map);
					fub = fileUpload.uploadFileToDatabase(fub);
					crpb.setEstimationFile(String.valueOf(fub.getFileIds().get("estimationFile")));
					crpb.setCghsCardFile(String.valueOf(fub.getFileIds().get("cghsCardFile")));
					}}
					else if((CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.CGHSREIMBURSEMENT)) || 
							(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.NONCGHSREIMBURSEMENT))){	
					if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("medicalBillsFile")).getOriginalFilename())){
						map.put("medicalBillsFile", (MultipartFile)session.getAttribute("medicalBillsFile"));
					}if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("cghsCardFile")).getOriginalFilename())){
					map.put("cghsCardFile", (MultipartFile)session.getAttribute("cghsCardFile"));
					}if(!CPSUtils.isNullOrEmpty((((MultipartFile)session.getAttribute("refundChequeFile")).getOriginalFilename()))){
					map.put("refundChequeFile",(((MultipartFile)session.getAttribute("refundChequeFile"))));
					}if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("dischargeSummeryFile")))){
					map.put("dischargeSummeryFile", (MultipartFile)session.getAttribute("dischargeSummeryFile"));
					}FileUploadBean fub=new FileUploadBean();
					if(map.size() != 0){
					fub.setFiles(map);
					//System.out.println("rakesh");
                    fub = fileUpload.uploadFileToDatabase(fub);
					crpb.setMedicalBillsFile(String.valueOf(fub.getFileIds().get("medicalBillsFile")));
					crpb.setCghsCardFile(String.valueOf(fub.getFileIds().get("cghsCardFile")));
					if(CPSUtils.isNullOrEmpty(String.valueOf(fub.getFileIds().get("dischargeSummeryFile")))){
					crpb.setDischargeSummeryFile(String.valueOf(fub.getFileIds().get("dischargeSummeryFile")));
					}if(CPSUtils.isNullOrEmpty(String.valueOf(fub.getFileIds().get("refundChequeFile")))){
					crpb.setRefundChequeFile(String.valueOf(fub.getFileIds().get("refundChequeFile")));
					}}}
					else if((CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.EMERGENCY))){		
					if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("medicalBillsFile")).getOriginalFilename())){
					map.put("medicalBillsFile", (MultipartFile)session.getAttribute("medicalBillsFile"));
					}if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("cghsCardFile")).getOriginalFilename())){
					map.put("cghsCardFile", (MultipartFile)session.getAttribute("cghsCardFile"));
					}if(!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("dischargeSummeryFile")).getOriginalFilename())){
					map.put("dischargeSummeryFile", (MultipartFile)session.getAttribute("dischargeSummeryFile"));
					}FileUploadBean fub=new FileUploadBean();
					if(map.size() != 0){
					fub.setFiles(map);
					fub = fileUpload.uploadFileToDatabase(fub);
					crpb.setMedicalBillsFile(String.valueOf(fub.getFileIds().get("medicalBillsFile")));
					crpb.setCghsCardFile(String.valueOf(fub.getFileIds().get("cghsCardFile")));
					crpb.setDischargeSummeryFile(String.valueOf(fub.getFileIds().get("dischargeSummeryFile")));
					}}
				   crpb.setCghscardref(cghsBean.getCghscardref());
					message = cghsRequestProcess.initWorkflow(crpb);
					cghsBean.setRequestID(crpb.getRequestID());
					session.setAttribute("CghsRequestID",cghsBean.getRequestID() );
					//cghsBean.setRequestId(crpb.getRequestId());
					//session.setAttribute("NONCghsRequestId",crpb.getRequestId());
					
					mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE,CPSConstants.CGHS, cghsBean);
					mav.addObject(CPSConstants.MESSAGE, message);
	       }else if(CPSUtils.compareStrings(CPSConstants.MANAGEINVESTIGATION, cghsBean.getParam())){
				log.debug("CghsRequestController >>>>>>>>>> param=manageinvestigation,type="+cghsBean.getType());
				if(CPSUtils.isNullOrEmpty(cghsBean.getOfflineSFID())) {
					cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
					cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
				}else {
					/**
					 * In case of Offline Request
					 */
					cghsBean.setSfID(cghsBean.getOfflineSFID());
					cghsBean.setRequestedBy(session.getAttribute(CPSConstants.SFID).toString());
				}
				
				CGHSRequestProcessBean crpb = new CGHSRequestProcessBean();
				BeanUtils.copyProperties(cghsBean, crpb);
				//setUplodedFilesNames(session,crpb);
				crpb.setReferrenceRequestID(cghsBean.getRequestId());
				
				crpb.setIpAddress(request.getRemoteAddr());
				crpb.setSession(request.getSession(true));
				if(crpb.getCghsRequestTypeId().equals("2")&& crpb.getSettlement().equals("advance")){
					crpb.setType("advance");
				}else if(crpb.getCghsRequestTypeId().equals("2")&& crpb.getSettlement().equals("reimbursement")){
					crpb.setType("reimbursement");
				}
				else{
					cghsBean.getFamilyMemberId();
				}
				message = cghsRequestProcess.initWorkflow(crpb);
		        if(message == "success" && message.equals("success")){
			    if(cghsBean.getCghsRequestTypeId().equals("2")){	
				if(CPSUtils.compareStrings("advance", cghsBean.getSettlement())){
					session.setAttribute("refReqID", crpb.getRequestID());
					
			    mav = new ModelAndView("redirect:cghsRequest.htm?param=directadvance&redirect=true&referrenceRequestID='"+crpb.getRequestID()+"'");
				}
				
			    else if(CPSUtils.compareStrings("reimbursement", cghsBean.getSettlement())){
			   mav = new ModelAndView("redirect:cghsRequest.htm?param=directreimbursement&type=cghsreimbursement&requestId='"+crpb.getRequestID()+"'&redirect=true");
			        session.setAttribute("refReqID",crpb.getRequestID() );
				}
			    else{
			    	mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE,CPSConstants.CGHS, cghsBean);
			     	mav.addObject(CPSConstants.MESSAGE, message);	
			    }}}
				}else if(CPSUtils.compareStrings(CPSConstants.REIMHOME, cghsBean.getParam())){
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean = cghsRequestBusiness.getFamilyMemberReimbursementList(cghsBean);
				mav = new ModelAndView(CPSConstants.REIMBURSEMENTDETAILS,CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.HOME,CPSConstants.REIMHOME);
				cghsBean.setCghsRequestTypeId("Admission");
				cghsBean.setStatusName("ReimReqPending");
				session.setAttribute(CPSConstants.RETURN, CPSConstants.CGHSREIMBURSEMENTLIST);
			    session.setAttribute(CPSConstants.REIMBURSEMENTMEMBERLIST,cghsBean.getReimbursementMemberList());
			}else if(CPSUtils.compareStrings(CPSConstants.ADVANCE, cghsBean.getParam())){
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean = cghsRequestBusiness.getFamilyMemberAdvanceDetails(cghsBean);
				mav = new ModelAndView(CPSConstants.ADVANCEDETAILSFORM,CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.HOME,CPSConstants.ADVANCE);
			}else if(CPSUtils.compareStrings(CPSConstants.EMERGENCY, cghsBean.getParam())){
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean = cghsRequestBusiness.getTreatmentRequestHomeDetails(cghsBean);
				cghsBean.setReferralHospitalList( cghsRequestBusiness.getReferralHospitalList(null));
				session.setAttribute(CPSConstants.JSONFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(cghsBean.getFamilyMemberList()));
				//cghsBean.getFamilyMemberList().get(0).getCghscard();
				mav = new ModelAndView(CPSConstants.EMERGENCYDETAILSFORM,CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.HOME,CPSConstants.EMERGENCY);
			}else if(CPSUtils.compareStrings(CPSConstants.REIMBURSEMENT, cghsBean.getParam())){
				
				cghsBean = cghsRequestBusiness.getFamilyMemberReimbursementList(cghsBean);
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				
				
				
				cghsBean = cghsRequestBusiness.getFamilyMemberAdvanceDetails(cghsBean);
				if(CPSUtils.compareStrings(CPSConstants.CGHSADVANCE, cghsBean.getType()))
					viewName =CPSConstants.ADVANCEDETAILSFORM;
				else 
					viewName =CPSConstants.REIMBURSEMENTDETAILSFORM;

			
				session.setAttribute(CPSConstants.HOME,CPSConstants.REIMBURSEMENT);
				mav = new ModelAndView(viewName,CPSConstants.CGHS, cghsBean);
				
				session.setAttribute(CPSConstants.JSONFAMILYMEMBERLIST,(JSONArray) JSONSerializer.toJSON(cghsBean.getCghsRequestBean().getFamilyMemberList()));
//				session.setAttribute(CPSConstants.JSONFAMILYMEMBERLIST, (JSONArray) JSONSerializer.toJSON(cghsBean.getFamilyMemberList()));
				
			}
			//finance settlement code start 
			else if(CPSUtils.compareStrings(CPSConstants.FINANCEHOME, cghsBean.getParam())){
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean = cghsRequestBusiness.financeReimbursementHome(cghsBean);
				mav = new ModelAndView(CPSConstants.CGHSREIMBURSEMENTJSP,CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.HOME,CPSConstants.FINANCEHOME);
				session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getAdvanceList());
				session.setAttribute(CPSConstants.SETTLEMENTLIST,cghsBean.getSettlementList());
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,cghsBean.getReimbursementList());
				session.setAttribute(CPSConstants.NONCGHSREIMLIST,cghsBean.getNonCghsReimList());
				session.setAttribute(CPSConstants.EMERGENCYLIST,cghsBean.getEmergencyList());
				session.setAttribute(CPSConstants.ACCOUNTOFFICERLIST,cghsBean.getAccountOfficerList());
				session.setAttribute(CPSConstants.CFAOFFICERLIST,cghsBean.getCfaOfficerList());
			}
			
		else if(CPSUtils.compareStrings(CPSConstants.ADVMANAGE, cghsBean.getParam())){
			cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean = cghsRequestBusiness.saveCdaAmount(cghsBean);
				if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.ADVANCE)){
					//session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getAdvanceList());
					mav = new ModelAndView(CPSConstants.CGHSADVANCELIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getAdvanceList());
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.SETTLEMENT)){
					mav = new ModelAndView(CPSConstants.CGHSSETTLEMENTLIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.SETTLEMENTLIST,cghsBean.getSettlementList());
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.REIMBURSEMENT)){
					mav = new ModelAndView(CPSConstants.CGHSREIMBURSEMENTDATALIST,CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,cghsBean.getReimbursementList());
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.NONCGHSREIM)){
					mav = new ModelAndView(CPSConstants.NONCGHSREIMLISTJSP,CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.NONCGHSREIMLIST,cghsBean.getNonCghsReimList());
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.EMERGENCY)){
					mav = new ModelAndView(CPSConstants.CGHSEMERGENCYLIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.EMERGENCYLIST,cghsBean.getEmergencyList());
				}
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
				session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getAdvanceList());
				session.setAttribute(CPSConstants.SETTLEMENTLIST,cghsBean.getSettlementList());
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,cghsBean.getReimbursementList());
				session.setAttribute(CPSConstants.NONCGHSREIMLIST,cghsBean.getNonCghsReimList());
				session.setAttribute(CPSConstants.EMERGENCYLIST,cghsBean.getEmergencyList());
			}
			//cda settlement code start
			else if(CPSUtils.compareStrings(CPSConstants.CDAHOME, cghsBean.getParam())){

				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean = cghsRequestBusiness.cdaReimbursementHome(cghsBean);
				mav = new ModelAndView(CPSConstants.CGHSCDAJSP,CPSConstants.CGHS, cghsBean);
				session.setAttribute(CPSConstants.HOME,CPSConstants.CDAHOME);
				session.setAttribute(CPSConstants.CDAADVANCELIST,cghsBean.getCdaadvanceList());
				session.setAttribute(CPSConstants.CDASETTLEMENTLIST,cghsBean.getCdasettlementList());
				session.setAttribute(CPSConstants.CDAREIMBURSEMENTLIST,cghsBean.getCdareimbursementList());
				session.setAttribute(CPSConstants.CDANONCGHSREIMLIST,cghsBean.getCdanonCghsReimList());
				session.setAttribute(CPSConstants.CDAEMERGENCYLIST,cghsBean.getCdaemergencyList());
				/*session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getCdaadvanceList());
				session.setAttribute(CPSConstants.SETTLEMENTLIST,cghsBean.getCdasettlementList());
				session.setAttribute(CPSConstants.REIMBURSEMENTLIST,cghsBean.getCdareimbursementList());
				session.setAttribute(CPSConstants.NONCGHSREIMLIST,cghsBean.getCdanonCghsReimList());
				session.setAttribute(CPSConstants.EMERGENCYLIST,cghsBean.getCdaemergencyList());*/
				session.setAttribute(CPSConstants.ACCOUNTOFFICERLIST,cghsBean.getAccountOfficerList());
				session.setAttribute(CPSConstants.CFAOFFICERLIST,cghsBean.getCfaOfficerList());
			}
			else if(CPSUtils.compareStrings(CPSConstants.CDAMANAGE, cghsBean.getParam())){

				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				cghsBean = cghsRequestBusiness.saveCdaAmount(cghsBean);
				if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.ADVANCE)){
					//viewName = CPSConstants.CGHSCDAADVANCELIST;  
					mav = new ModelAndView(CPSConstants.CGHSCDAADVANCELIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.CDAADVANCELIST,cghsBean.getCdaadvanceList());
					//session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getCdaadvanceList());
					/*mav = new ModelAndView(CPSConstants.CGHSCDAADVANCELIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.ADVANCELIST,cghsBean.getCdaadvanceList());*/
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.SETTLEMENT)){
					mav = new ModelAndView(CPSConstants.CGHSCDASETTLEMENTLIST,CPSConstants.CGHS, cghsBean);
					//viewName = CPSConstants.CGHSCDASETTLEMENTLIST;
					session.setAttribute(CPSConstants.CDASETTLEMENTLIST,cghsBean.getCdasettlementList());
					//session.setAttribute(CPSConstants.SETTLEMENTLIST,cghsBean.getCdasettlementList());
					/*mav = new ModelAndView(CPSConstants.CGHSCDASETTLEMENTLIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.SETTLEMENTLIST,cghsBean.getCdasettlementList());*/
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.REIMBURSEMENT)){
					//viewName = CPSConstants.CGHSCDAREIMBURSEMENTDATALIST;
					mav = new ModelAndView(CPSConstants.CGHSCDAREIMBURSEMENTDATALIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.CDAREIMBURSEMENTLIST,cghsBean.getCdareimbursementList());
					/*session.setAttribute(CPSConstants.CDAREIMBURSEMENTLIST,cghsBean.getCdareimbursementList());
					mav = new ModelAndView(CPSConstants.CGHSCDAREIMBURSEMENTDATALIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.REIMBURSEMENTLIST,cghsBean.getCdareimbursementList());*/
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.NONCGHSREIM)){
					//viewName = CPSConstants.NONCGHSCDAREIMLISTJSP;
					mav = new ModelAndView(CPSConstants.NONCGHSCDAREIMLISTJSP,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.CDANONCGHSREIMLIST,cghsBean.getCdanonCghsReimList());
					//session.setAttribute(CPSConstants.NONCGHSREIMLIST,cghsBean.getCdanonCghsReimList());
					/*mav = new ModelAndView(CPSConstants.NONCGHSCDAREIMLISTJSP,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.NONCGHSREIMLIST,cghsBean.getCdanonCghsReimList());*/
				}else if(CPSUtils.compareStrings(cghsBean.getType(),CPSConstants.EMERGENCY)){
					//viewName = CPSConstants.CGHSCDAEMERGENCYLIST;
					mav =new ModelAndView(CPSConstants.CGHSCDAEMERGENCYLIST,CPSConstants.CGHS,cghsBean);
					session.setAttribute(CPSConstants.CDAEMERGENCYLIST,cghsBean.getCdaemergencyList());
					//session.setAttribute(CPSConstants.EMERGENCYLIST,cghsBean.getCdaemergencyList());
					/*mav = new ModelAndView(CPSConstants.CGHSCDAEMERGENCYLIST,CPSConstants.CGHS, cghsBean);
					session.setAttribute(CPSConstants.EMERGENCYLIST,cghsBean.getCdaemergencyList());*/
				}
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
				//session.setAttribute(CPSConstants.HOME,CPSConstants.CDAHOME);
				session.setAttribute(CPSConstants.CDAADVANCELIST,cghsBean.getCdaadvanceList());
				session.setAttribute(CPSConstants.CDASETTLEMENTLIST,cghsBean.getCdasettlementList());
				session.setAttribute(CPSConstants.CDAREIMBURSEMENTLIST,cghsBean.getCdareimbursementList());
				session.setAttribute(CPSConstants.CDANONCGHSREIMLIST,cghsBean.getCdanonCghsReimList());
				session.setAttribute(CPSConstants.CDAEMERGENCYLIST,cghsBean.getCdaemergencyList());
			}
			//cda settlement code end
			else if(CPSUtils.compareStrings(CPSConstants.DEPENDENT, cghsBean.getParam())){
				log.debug("CghsRequestController >>>>>>>>> Ajax call param=Dependent ");
				cghsBean = cghsRequestBusiness.getDependentList(cghsBean);
				mav = new ModelAndView("DependentList",CPSConstants.CGHS, cghsBean);
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
				if(CPSUtils.checkList(cghsBean.getFamilyMemberList())) {
					mav.addObject(CPSConstants.FAMILYMEMBERSLISTJSON, (JSONArray) JSONSerializer.toJSON(cghsBean.getFamilyMemberList()));	
				}
			}else if(CPSUtils.compareStrings(CPSConstants.DIRECTREIMBURSEMENT, cghsBean.getParam())){
				
				cghsBean = cghsRequestBusiness.getFamilyMemberReimbursementList(cghsBean);
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				
				cghsBean.setRequestId(session.getAttribute("refReqID").toString());
				
				cghsBean = cghsRequestBusiness.getFamilyMemberAdvanceDetails(cghsBean);
				if(CPSUtils.compareStrings(CPSConstants.CGHSADVANCE, cghsBean.getType()))
					viewName =CPSConstants.ADVANCEDETAILSFORM;
				else 
					viewName =CPSConstants.REIMBURSEMENTDETAILSFORM;
				session.setAttribute(CPSConstants.HOME,CPSConstants.REIMBURSEMENT);
				mav = new ModelAndView(viewName,CPSConstants.CGHS, cghsBean);
			log.debug("CghsRequestController >>>>>>>>> End"+cghsBean.getParam());
			}else if(CPSUtils.compareStrings(CPSConstants.DIRECTADVANCE, cghsBean.getParam())){
				
				cghsBean = cghsRequestBusiness.getFamilyMemberReimbursementList(cghsBean);
				cghsBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
				
				cghsBean = cghsRequestBusiness.getFamilyMemberAdvanceDetails(cghsBean);
				mav = new ModelAndView(CPSConstants.ADVANCEDETAILSFORM,CPSConstants.CGHS, cghsBean);
			session.setAttribute(CPSConstants.HOME,CPSConstants.ADVANCE);
			
			//cghs uploading to database
			}else if(CPSUtils.compareStrings("uploadToDatabase", cghsBean.getParam())){

				Map<String, MultipartFile> map =new HashMap<String, MultipartFile>();
				Map<String, Integer> fileIDMap= new HashMap<String, Integer>();
				List<String> tempfileName = new ArrayList<String>();
				
				if(cghsBean.getRequestType() == "normal" || cghsBean.getRequestType().equals("normal")){
				
				if(!CPSUtils.isNullOrEmpty(cghsBean.getPrescriptionFileName())){
					map.put("prescriptionFile", cghsBean.getPrescriptionFile());
					fileIDMap.put("prescriptionFile",Integer.parseInt(cghsBean.getPrescriptionFileName()));
					tempfileName.add(cghsBean.getPrescriptionFileName());	
				}else{
					if(!CPSUtils.isNullOrEmpty(cghsBean.getPrescriptionFile())){
					map.put("prescriptionFile", cghsBean.getPrescriptionFile());
				 }}
				
				}else if(cghsBean.getRequestType() == "advance" || cghsBean.getRequestType().equals("advance")){
					if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFile().getOriginalFilename())){
						map.put("cghsCardFile", cghsBean.getCghsCardFile());
						}fileIDMap.put("cghsCardFile",Integer.parseInt(cghsBean.getCghsCardFileName()));
						tempfileName.add(cghsBean.getCghsCardFileName());	
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFile())){
						map.put("cghsCardFile", cghsBean.getCghsCardFile());
					}}
					  
					if(!CPSUtils.isNullOrEmpty(cghsBean.getEstimationFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getEstimationFile().getOriginalFilename())){
						map.put("estimationFile", cghsBean.getEstimationFile());
						}fileIDMap.put("estimationFile",Integer.parseInt(cghsBean.getEstimationFileName()));
						tempfileName.add(cghsBean.getEstimationFileName());	
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getEstimationFile())){
						map.put("estimationFile", cghsBean.getEstimationFile());
					}}
									
				
				}else if(cghsBean.getRequestType() == "reimbursement" || cghsBean.getRequestType().equals("reimbursement")){
					if(!CPSUtils.isNullOrEmpty(cghsBean.getPrescriptionFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getPrescriptionFile().getOriginalFilename())){
						map.put("prescriptionFile", cghsBean.getPrescriptionFile());
						}fileIDMap.put("prescriptionFile",Integer.parseInt(cghsBean.getPrescriptionFileName()));
						tempfileName.add(cghsBean.getPrescriptionFileName());
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getPrescriptionFile())){
						 map.put("prescriptionFile", cghsBean.getPrescriptionFile());
					}}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFile().getOriginalFilename())){
						map.put("cghsCardFile", cghsBean.getCghsCardFile());
						}fileIDMap.put("cghsCardFile",Integer.parseInt(cghsBean.getCghsCardFileName()));
					    tempfileName.add(cghsBean.getCghsCardFileName());
					}else{	
						if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFile())){
      						map.put("cghsCardFile", cghsBean.getCghsCardFile());
					}}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFile().getOriginalFilename())){
						map.put("medicalBillsFile", cghsBean.getMedicalBillsFile());
						}fileIDMap.put("medicalBillsFile",Integer.parseInt(cghsBean.getMedicalBillsFileName()));
					    tempfileName.add(cghsBean.getMedicalBillsFileName());
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFile())){
						map.put("medicalBillsFile", cghsBean.getMedicalBillsFile());
					}}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getRefundChequeFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getRefundChequeFile().getOriginalFilename())){
						map.put("refundChequeFile", cghsBean.getRefundChequeFile());
					}fileIDMap.put("refundChequeFile",Integer.parseInt(cghsBean.getRefundChequeFileName()));
					    tempfileName.add(cghsBean.getRefundChequeFileName());
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getRefundChequeFile())){
						map.put("refundChequeFile", cghsBean.getRefundChequeFile());	
					}}
				
				
				}else if(cghsBean.getRequestType() == "emergency" || cghsBean.getRequestType().equals("emergency")){
					if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFile().getOriginalFilename())){
						map.put("medicalBillsFile", cghsBean.getMedicalBillsFile());
						}fileIDMap.put("medicalBillsFile",Integer.parseInt(cghsBean.getMedicalBillsFileName()));
						tempfileName.add(cghsBean.getMedicalBillsFileName());
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getMedicalBillsFile())){
							map.put("medicalBillsFile", cghsBean.getMedicalBillsFile());
					}}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getDischargeSummeryFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getDischargeSummeryFile().getOriginalFilename())){
						map.put("dischargeSummeryFile", cghsBean.getDischargeSummeryFile());
						}fileIDMap.put("dischargeSummeryFile",Integer.parseInt(cghsBean.getDischargeSummeryFileName()));
						tempfileName.add(cghsBean.getDischargeSummeryFileName());	
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getDischargeSummeryFile())){
						map.put("dischargeSummeryFile", cghsBean.getDischargeSummeryFile());
					}}
					if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFileName())){
						if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFile().getOriginalFilename())){
						map.put("cghsCardFile", cghsBean.getCghsCardFile());
						}fileIDMap.put("cghsCardFile",Integer.parseInt(cghsBean.getCghsCardFileName()));
						tempfileName.add(cghsBean.getCghsCardFileName());
					}else{
						if(!CPSUtils.isNullOrEmpty(cghsBean.getCghsCardFile())){
						map.put("cghsCardFile", cghsBean.getCghsCardFile());
					}}
				
				}
				
				
				if(map.size()>0){
				FileUploadBean fub=new FileUploadBean();
				fub.setFiles(map);
				fub.setFileIDList(fileIDMap);
				FileUploadBean fub1=fileUpload.uploadFileToDatabase(fub);
				
				if(!((String.valueOf(fub1.getFileIds().get("prescriptionFile"))== "null") 
						|| (String.valueOf(fub1.getFileIds().get("prescriptionFile")).equals("null")))){ 
					cghsBean.setPrescriptionFileName(String.valueOf(fub1.getFileIds().get("prescriptionFile")));	
				}
				
				if(!((String.valueOf(fub1.getFileIds().get("cghsCardFile"))== "null") 
						|| (String.valueOf(fub1.getFileIds().get("cghsCardFile")).equals("null")))){
					
					cghsBean.setCghsCardFileName(String.valueOf(fub1.getFileIds().get("cghsCardFile")));
				}
				if(!((String.valueOf(fub1.getFileIds().get("estimationFile"))== "null") 
						|| (String.valueOf(fub1.getFileIds().get("estimationFile")).equals("null")))){
					cghsBean.setEstimationFileName(String.valueOf(fub1.getFileIds().get("estimationFile")));
				}
				if(!((String.valueOf(fub1.getFileIds().get("medicalBillsFile"))== "null") 
						|| (String.valueOf(fub1.getFileIds().get("medicalBillsFile")).equals("null")))){
				
					cghsBean.setMedicalBillsFileName(String.valueOf(fub1.getFileIds().get("medicalBillsFile")));
				}
				if(!((String.valueOf(fub1.getFileIds().get("refundChequeFile"))== "null") 
						|| (String.valueOf(fub1.getFileIds().get("refundChequeFile")).equals("null")))){ 
					
					cghsBean.setRefundChequeFileName(String.valueOf(fub.getFileIds().get("refundChequeFile")));
				}
				if(!((String.valueOf(fub1.getFileIds().get("dischargeSummeryFile"))== "null") 
						|| (String.valueOf(fub1.getFileIds().get("dischargeSummeryFile")).equals("null")))){
					cghsBean.setDischargeSummeryFileName(String.valueOf(fub.getFileIds().get("dischargeSummeryFile")));
				}
				
				
				if(tempfileName.size() == 0){
				 message = cghsRequestProcess.updateFileStatus(cghsBean);
				}
				}
                cghsBean.setMessage(message);
				viewName = CPSConstants.RESULT;	
				mav = new ModelAndView(viewName, CPSConstants.CGHS, cghsBean);
			}
			/*if(!CPSUtils.isNullOrEmpty(cghsBean.getResult()))
			 viewName = CPSConstants.RESULT;*/
			/*mav.addObject(CPSConstants.MESSAGE, cghsBean.getResult());*/
			if (!CPSUtils.isNullOrEmpty(cghsBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, cghsBean.getMessage());
			}
			}catch(Exception e) {
				
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
	return mav;
	}
	
	public void setUplodedFilesNames(HttpSession session,CGHSRequestProcessBean crpb) throws Exception{
		Thread.sleep(1000);
		if(!CPSUtils.isNull(session.getAttribute("estimationFile"))) {
			crpb.setEstimationFile(session.getAttribute("estimationFile").toString());
		}if(!CPSUtils.isNull(session.getAttribute("cghsCardFile"))) {
			crpb.setCghsCardFile(session.getAttribute("cghsCardFile").toString());
		}if(!CPSUtils.isNull(session.getAttribute("prescriptionFile"))) {
			crpb.setPrescriptionFile(session.getAttribute("prescriptionFile").toString());
		}if(!CPSUtils.isNull(session.getAttribute("refundChequeFile"))) {
			crpb.setRefundChequeFile(session.getAttribute("refundChequeFile").toString());
		}if(!CPSUtils.isNull(session.getAttribute("dischargeSummeryFile"))) {
			crpb.setDischargeSummeryFile(session.getAttribute("dischargeSummeryFile").toString());
		}if(!CPSUtils.isNull(session.getAttribute("medicalBillsFile"))) {
			crpb.setMedicalBillsFile(session.getAttribute("medicalBillsFile").toString());
		}if(!CPSUtils.isNull(session.getAttribute("reimbursementFile"))) {
			crpb.setReimbursementFile(session.getAttribute("reimbursementFile").toString());
		}
	}
	

}
