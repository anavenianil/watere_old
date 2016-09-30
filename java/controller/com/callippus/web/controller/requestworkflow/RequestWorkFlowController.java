package com.callippus.web.controller.requestworkflow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

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
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.higherQualification.HQRequestBean;
import com.callippus.web.beans.misc.AdminMisc;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.requests.AddressRequestBean;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.beans.requests.FamilyRequestBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.beans.requests.LoanRequestProcessBean;
import com.callippus.web.beans.requests.NomineeRequestBean;
import com.callippus.web.beans.requests.PISRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.business.hrdg.training.request.TrainingRequestProcess;
import com.callippus.web.business.mmg.cashbuildup.workflow.DemandRequestProcess;
import com.callippus.web.business.mmg.cashbuildup.workflow.InvHolderRequestProcess;
import com.callippus.web.business.mmg.cashbuildup.workflow.InvoiceRequestProcess;
import com.callippus.web.business.mmg.cashbuildup.workflow.VoucherRequestProcess;
import com.callippus.web.business.requestprocess.AddressRequestProcess;
import com.callippus.web.business.requestprocess.CghsRequestProcess;
import com.callippus.web.business.requestprocess.ErpLoanRequestProcess;
import com.callippus.web.business.requestprocess.FPARequestProcess;
import com.callippus.web.business.requestprocess.FamilyRequestProcess;
import com.callippus.web.business.requestprocess.HQRequestProcess;
import com.callippus.web.business.requestprocess.LTCWaterRequestProcess;
import com.callippus.web.business.requestprocess.LeaveRequestProcess;
import com.callippus.web.business.requestprocess.LeaveWaterRequestProcess;
import com.callippus.web.business.requestprocess.LoanHBARequestProcess;
import com.callippus.web.business.requestprocess.LoanRequestProcess;
import com.callippus.web.business.requestprocess.LtcRequestProcess;
import com.callippus.web.business.requestprocess.NomineeRequestProcess;
import com.callippus.web.business.requestprocess.PISRequestProcess;
import com.callippus.web.business.requestprocess.PropertyRequestProcess;
import com.callippus.web.business.requestprocess.QuarterRequestProcess;
import com.callippus.web.business.requestprocess.TadaRequestProcess;
import com.callippus.web.business.requestprocess.TadaWaterRequestProcess;
import com.callippus.web.business.requestprocess.TransferRequestProcess;
import com.callippus.web.business.requestworkflow.RequestWorkFlowBusiness;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.MT.MTVehicleRequestProcess;
import com.callippus.web.dao.telephone.TelephoneBillRequestProcess;
import com.callippus.web.dao.tutionFee.TutionFeeRequestProcess;
import com.callippus.web.hrdg.training.request.beans.dto.TrainingRequestBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.beans.request.LeaveWaterRequestProcessBean;
import com.callippus.web.leave.business.request.LeaveRequestBusiness;
import com.callippus.web.loan.beans.dto.ErpLoanRequestDTO;
import com.callippus.web.loan.beans.request.ErpLoanRequestBean;
import com.callippus.web.ltc.dto.LTCWaterRequestProcessBean;
import com.callippus.web.ltc.dto.LtcRefundRequestDTO;
import com.callippus.web.ltc.dto.LtcRequestProcessBean;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;
import com.callippus.web.mmg.cashbuildup.beans.InvoiceRequestBean;
import com.callippus.web.mmg.cashbuildup.beans.VoucherRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.InventoryHolderRequestBean;
import com.callippus.web.tada.dto.TadaRequestProcessBean;
import com.callippus.web.tada.dto.TadaWaterRequestProcessBean;

@Controller
@RequestMapping("/workflow.htm")
@SessionAttributes
public class RequestWorkFlowController {
	private static Log log = LogFactory.getLog(RequestWorkFlowController.class);
	@Autowired
	private PISRequestProcess pisRequestProcess;
	@Autowired
	private RequestWorkFlowBusiness requestWorkflowBusiness;
	@Autowired
	private FamilyRequestProcess familyRequestProcess;
	@Autowired
	private NomineeRequestProcess nomineeRequestProcess;
	@Autowired
	private AddressRequestProcess addressRequestProcess;
	@Autowired
	private LeaveRequestProcess leaveRequestProcess;
	@Autowired
	private DemandRequestProcess demandRequestProcess;
	@Autowired
	private VoucherRequestProcess voucherRequestProcess;
	@Autowired
	private InvoiceRequestProcess invoiceRequestProcess;
	@Autowired
	private InvHolderRequestProcess invHolderRequestProcess;
	@Autowired
	private CghsRequestProcess cghsRequestProcess;
	@Autowired
	private LoanRequestProcess loanRequestProcess;
	@Autowired
	private LtcRequestProcess ltcRequestProcess;
	@Autowired
	private TransferRequestProcess transferRequestProcess;
	@Autowired
	private LoanHBARequestProcess loanHBARequestProcess;
	@Autowired
	private QuarterRequestProcess quarterRequestProcess;
	@Autowired
	private HQRequestProcess hqRequestProcess;
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private FPARequestProcess fpaRequestProcess;
	@Autowired
	private TadaRequestProcess tadaRequestProcess;
	@Autowired
	private TadaWaterRequestProcess tadaWaterRequestProcess;
	@Autowired
	private LTCWaterRequestProcess ltcWaterRequestProcess;
	@Autowired
	private LeaveWaterRequestProcess leaveWaterRequestProcess;
	@Autowired
	private TutionFeeRequestProcess tutionFeeRequestProcess;
	@Autowired
	private PropertyRequestProcess propertyRequestProcess;
	@Autowired
	private TrainingRequestProcess trainingRequestProcess;
	@Autowired
	private MTVehicleRequestProcess mtVehicleRequestProcess;
	@Autowired
	private TelephoneBillRequestProcess telephoneBillRequestProcess;
	@Autowired
	private LeaveRequestBusiness leaveRequestBusiness;
	@Autowired
	private ErpLoanRequestProcess erpLoanRequestProcess;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.WORKFLOW) RequestBean rb, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String message = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.sessionChecks(request);
			session = request.getSession(true);
			rb.setIpAddress(request.getRemoteAddr());
			session.setAttribute(CPSConstants.RESULT, CPSConstants.SUCCESS);
			rb.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			if (CPSUtils.isNullOrEmpty(rb.getParam())) {
				rb.setParam(CPSConstants.SUPERDELEGATION);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, rb.getParam())) {
				mav = new ModelAndView(CPSConstants.REQUESTLIST, CPSConstants.SUPERDELEGATION, rb);
			}

			if (CPSUtils.compareStrings(CPSConstants.DECLINED, rb.getParam())) {
				log.debug("RequestWorkFlowController --> onSubmit --> param = declined");
	 			String status = null;
				if (CPSUtils.compareStrings(rb.getType(), CPSConstants.CANCELLED)) {
					status = CPSConstants.STATUSCANCELLED;
				} else {
					status = CPSConstants.STATUSDECLINED;
				}
				if (!CPSUtils.compareStrings(CPSConstants.LEAVE, rb.getRequestType())) {
					message = txRequestProcess.declinedRequest(rb.getHistoryID(), rb.getIpAddress(), rb.getRemarks(), rb.getType(), status);
				}

				if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.DEMAND)) {
					message = demandRequestProcess.declineRequest(rb);
				} else if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.INVOICE)) {
					message = invoiceRequestProcess.declineRequest(rb);
				} else if (CPSUtils.compareStrings(CPSConstants.LEAVE, rb.getRequestType())) {
					// check whether this Leave is attached to any LTC request or not 
					if (!CPSUtils.isNullOrEmpty(rb.getRemarks())) {
						rb.setRemarksDecline(rb.getRemarks());
					}
					rb.setRemarks(leaveRequestProcess.checkLeaveAttachedToLtc(rb.getRequestID()));
					if (CPSUtils.isNull(rb.getRemarks())) {
						rb.setRemarks(rb.getRemarksDecline());
						// update cancelled / decline status in workflow
						message = txRequestProcess.declinedRequest(rb.getHistoryID(), rb.getIpAddress(), rb.getRemarks(), rb.getType(), status);
						// update the status to cancelled / declined
						message = leaveRequestProcess.updateLeaveRequestStatus(rb.getRequestID(), rb.getRequestType(), status, null);
						// credit no of leaves which has been applied by the user
						message = leaveRequestProcess.creaditLeaves(CPSConstants.LEAVE, rb.getRequestID(), null, rb.getSfID());
						// update employee leave spell count
						message = leaveRequestProcess.updateLeaveCount(rb.getRequestID(), CPSConstants.LEAVE);
						LeaveApplicationBean lab = new LeaveApplicationBean();
						lab.setRequestID(rb.getRequestID());
						lab = leaveRequestBusiness.getLeaveDetails(lab);
						rb.setRequester(lab.getRequestedBy());
						rb.setAppliedBy(lab.getRequestedBy());
						// decline the cancel leave request, if any request was applied
						message = leaveRequestProcess.declineLeaveCancelRequest(rb);
					} else {
						message = CPSConstants.FAILED;
					}
				} else if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.CANCELLEAVEREQ)) {
					// assigned user declined the cancel leave request, so we need to update status of that cancel request
					message = leaveRequestProcess.updateLeaveRequestStatus(rb.getRequestID(), rb.getRequestType(), status, null);
					message = leaveRequestProcess.updateLeaveCount(rb.getRequestID(), CPSConstants.CANCELLEAVEREQ);
				} else if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.CONVERTLEAVEREQ)) {
					// assigned user declined the cancel leave request, so we need to update status of that convert request and roll back the leaves
					message = leaveRequestProcess.updateLeaveRequestStatus(rb.getRequestID(), rb.getRequestType(), status, null);
					rb.setStatus(Integer.valueOf(status));
					message = leaveRequestProcess.rollBackConvertedLeaves(rb);
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CGHS_ADVANCE, rb.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.CGHS_REIMBURSEMENT, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CGHS_SETTLEMENT, rb.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.NON_CGHS_REIMBURSEMENT, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CGHS_EMERGENCY, rb.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.NON_CGHS_EMERGENCY, rb.getRequestType())) {
					message = cghsRequestProcess.updateStatusToDecline(rb.getRequestID(), rb.getRequestType(), status);
				} else if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCREIMBURSEMENT) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCSETTLEMENT)
						|| CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCAPPROVAL) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTC_APP_ADV) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCADVANCE)
						|| CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCREFUND)|| CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTC_CANCEL)
						|| CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCADVANCEAMENDMENT)|| CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCAPPROVALAMENDMENT) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTC_APPR_CUM_ADV_CANCEL) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTCREFUNDVALUE)) {
					rb.setSfID((String)session.getAttribute(CPSConstants.SFID));
					rb = ltcRequestProcess.updateLtcRequestStatus(rb, status);
					message = rb.getMessage();
				}else if (CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.HOUSEBUILDINGLOAN)){
					LoanHBARequestProcessBean processBean = new LoanHBARequestProcessBean();
					BeanUtils.copyProperties(rb, processBean);
					message = loanHBARequestProcess.declainedRequest(processBean,status,message);
				}else if (CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LOAN)){
					LoanRequestProcessBean processBean = new LoanRequestProcessBean();
					BeanUtils.copyProperties(rb, processBean);
					message = loanRequestProcess.declainedRequest(processBean,status,message);
				}else if (CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.FPA)){
					FPARequestProcessBean processBean = new FPARequestProcessBean();
					BeanUtils.copyProperties(rb, processBean);
					message = fpaRequestProcess.declainedRequest(processBean,status,message);
				} else if(CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.TADATDBUILDUP) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.TADATDPROJECT) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.TADATDADVANCES) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.SETTLEMENT) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.TADATDSETTLEMENTS) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.REIMBURSEMENT) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.TADATDREIMBURSEMENT)){
					rb.setSfID((String)session.getAttribute(CPSConstants.SFID));
					//rb = tadaRequestProcess.updateStatusToDecline(rb,status);
					message = tadaRequestProcess.updateStatusToDecline(rb.getRequestID(), rb.getRequestType(), status,rb.getIpAddress());
				}
				else if(CPSUtils.compareStrings(rb.getRequestType(), "NEW QUARTER") ){
					QuarterRequestBean quarterBean = new QuarterRequestBean();
					BeanUtils.copyProperties(rb, quarterBean);
					quarterBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = quarterRequestProcess.cancelQuarterRequest(rb);
				}
				else if(CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.MOVABLEPROPERTYREQ) ){
					AdminMisc adminMisc= new AdminMisc();
					BeanUtils.copyProperties(rb, adminMisc);
					adminMisc.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = propertyRequestProcess.cancelPropertyRequest(rb);

				}else if(CPSUtils.compareStrings(rb.getRequestType(),"TUITION FEE")){
					message=tutionFeeRequestProcess.cancelRequest(rb);

				}else if(CPSUtils.compareStrings(rb.getRequestType(), "TELEPHONE BILL")){
					message=telephoneBillRequestProcess.telephoneCancelRequest(rb);
					viewName = CPSConstants.RESULT;
					
				}else if(CPSUtils.compareStrings(CPSConstants.MTVEHICLEREQUESTYPE, rb.getRequestType())){
					//message = mtVehicleRequestProcess.updateStatusToDecline(rb.getRequestID(), rb.getRequestType(), status);
					message = mtVehicleRequestProcess.declainedRequest(rb.getRequestID(), status);
					
				}else if (CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.ERPLEAVE)){
					
					log.debug("Erp leave Decline here");
					log.debug("ERP_LEAVE_REQUEST_DETAILS Status updated here ");
					leaveWaterRequestProcess.updateErpLeaveRequestDetails(rb.getRequestID());
					
				} else if (CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.TADA_WATER)){
					
					log.debug("Erp tada Decline here");
					log.debug("TADA_WATER_ADV_REQUEST_DETAILS Status updated here ");
					tadaWaterRequestProcess.updateErpTADARequestDetails(rb.getRequestID());
					
				} else if (CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTC_WATER)){
					
					log.debug("Erp ltc/annual leave Decline here");
					log.debug("TADA_WATER_ADV_REQUEST_DETAILS Status updated here ");
					ltcWaterRequestProcess.updateErpLTCRequestDetails(rb.getRequestID());
					
				}


				if(CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.TRAININGNOMINATION) && CPSUtils.compareStrings(status,CPSConstants.STATUSCANCELLED) ){
					//After cancel the request update the status in Training Nomination.
					TrainingRequestBean trainingReqBean = new TrainingRequestBean();
					BeanUtils.copyProperties(rb, trainingReqBean);
					trainingReqBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
					trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					trainingReqBean.setRequestId(rb.getRequestID());
					trainingRequestProcess.updateRequest(trainingReqBean);
				}
				if(CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.CANCELTRAININGNOMINATION)){
					//After cancel the request update the status in Training Nomination.
					TrainingRequestBean trainingReqBean = new TrainingRequestBean();
					BeanUtils.copyProperties(rb, trainingReqBean);
					trainingReqBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
					trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					trainingReqBean.setRequestId(rb.getRequestID());
					trainingRequestProcess.updateRequest(trainingReqBean);
				}
				
				
				// commit transaction & close the session
				hibernateUtils.closeSession();
				
				if(!CPSUtils.compareStrings(rb.getRequestFrom(), CPSConstants.OTHER)){
					rb.setWorkflowHistory(requestWorkflowBusiness.getRequestStatusDetails(rb.getRequestID()));
					viewName = CPSConstants.REQUESTHISTORYPAGE;
				} else {
					viewName = CPSConstants.RESULT;
				}
				
				mav = new ModelAndView(viewName, CPSConstants.WORKFLOW, rb);
				mav.addObject(CPSConstants.MESSAGE, message);
				mav.addObject(CPSConstants.REMARKS, rb.getRemarks());
				if(CPSUtils.checkList(rb.getWorkflowHistory())){
					mav.addObject(CPSConstants.REQUESTHISTORYLIST, rb.getWorkflowHistory());
				}				
				mav.addObject("leaveCancelDetails", rb.getKeyValue());
			} else if (CPSUtils.compareStrings(CPSConstants.DELEGATED, rb.getParam())) {
				log.debug("RequestWorkFlowController --> onSubmit --> param=delegated");
				
				rb.setIpAddress(request.getRemoteAddr());
				message = txRequestProcess.delegateRequest(rb);
				
				// commit transaction & close the session
				hibernateUtils.closeSession();
				
				rb.setWorkflowHistory(requestWorkflowBusiness.getRequestStatusDetails(rb.getRequestID()));
				mav = new ModelAndView(CPSConstants.REQUESTHISTORYPAGE, CPSConstants.WORKFLOW, rb);
				mav.addObject(CPSConstants.MESSAGE, message);
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, rb.getWorkflowHistory());
			} else if (CPSUtils.compareStrings(CPSConstants.APPROVED, rb.getParam())) {
				log.debug("RequestWorkFlowController --> onSubmit --> param=approved");
				
				if (CPSUtils.compareStrings(CPSConstants.CGHS, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CGHS_ADVANCE, rb.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.CGHS_REIMBURSEMENT, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CGHS_SETTLEMENT, rb.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.NON_CGHS_REIMBURSEMENT, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CGHS_EMERGENCY, rb.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.NON_CGHS_EMERGENCY, rb.getRequestType())) {
					CGHSRequestProcessBean crpb = new CGHSRequestProcessBean();
					BeanUtils.copyProperties(rb, crpb);
					crpb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					crpb.setSession(request.getSession(true));
					message = cghsRequestProcess.approvedRequest(crpb);
				} else if (CPSUtils.compareStrings(CPSConstants.LOAN, rb.getRequestType())) {
					/*LoanRequestProcessBean processBean = new LoanRequestProcessBean(); commented By MOHIB
					BeanUtils.copyProperties(rb, processBean);
					processBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = loanRequestProcess.approvedRequest(processBean);*/
					ErpLoanRequestDTO erpLoanRequestDTO = new ErpLoanRequestDTO();
					BeanUtils.copyProperties(rb, erpLoanRequestDTO);
					erpLoanRequestProcess.approvedRequest(erpLoanRequestDTO);
				}else if (CPSUtils.compareStrings(CPSConstants.HOUSEBUILDINGLOAN, rb.getRequestType())) {
					LoanHBARequestProcessBean processBean = new LoanHBARequestProcessBean();
					BeanUtils.copyProperties(rb, processBean);
					processBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = loanHBARequestProcess.approvedRequest(processBean);
				}
				else if (CPSUtils.compareStrings(CPSConstants.FPA, rb.getRequestType())) {
					FPARequestProcessBean processBean = new FPARequestProcessBean();
					BeanUtils.copyProperties(rb, processBean);
					processBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = fpaRequestProcess.approvedRequest(processBean);
				}else if (CPSUtils.compareStrings(CPSConstants.LTCAPPROVAL, rb.getRequestType()) || CPSUtils.compareStrings("LTC APPROVAL AMENDMENT", rb.getRequestType())) {
					LtcRequestProcessBean ltcProcessBean = new LtcRequestProcessBean();
					BeanUtils.copyProperties(rb, ltcProcessBean);
					ltcProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					ltcProcessBean.setRequestId(rb.getRequestID());
					ltcProcessBean.setDoPartDate(rb.getDoPartDate());
					ltcProcessBean.setDoPartNo(rb.getDoPartNo());
					message = ltcRequestProcess.approvedRequest(ltcProcessBean);
				} else if (CPSUtils.compareStrings(CPSConstants.LTCREIMBURSEMENT, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LTCSETTLEMENT, rb.getRequestType())) {
					LtcRequestProcessBean ltcProcessBean = new LtcRequestProcessBean();
					BeanUtils.copyProperties(rb, ltcProcessBean);
					ltcProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					ltcProcessBean.setRequestId(rb.getRequestID());
					message = ltcRequestProcess.approvedReimbersementRequest(ltcProcessBean);
				} else if (CPSUtils.compareStrings(CPSConstants.LTCREFUNDVALUE, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LTC_CANCEL, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LTC_APPR_CUM_ADV_CANCEL, rb.getRequestType())) {
					LtcRefundRequestDTO ltcRefundRequestDTO = new LtcRefundRequestDTO();
					BeanUtils.copyProperties(rb, ltcRefundRequestDTO);
					ltcRefundRequestDTO.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = ltcRequestProcess.approvedRequest(ltcRefundRequestDTO);
				} else if (CPSUtils.compareStrings(CPSConstants.LTCADVANCE, rb.getRequestType()) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LTC_APP_ADV) || CPSUtils.compareStrings(rb.getRequestType(), "LTC ADVANCE AMENDMENT")) {
					LtcRequestProcessBean ltcProcessBean = new LtcRequestProcessBean();
					BeanUtils.copyProperties(rb, ltcProcessBean);
					ltcProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					ltcProcessBean.setRequestId(rb.getRequestID());
					ltcProcessBean.setDoPartDate(rb.getDoPartDate());
					ltcProcessBean.setDoPartNo(rb.getDoPartNo());
					ltcProcessBean.setIssuedAmount(rb.getIssuedAmount());
					message = ltcRequestProcess.approvedLtcAdvanceRequest(ltcProcessBean);
				} else if (CPSUtils.compareStrings(CPSConstants.PIS, rb.getRequestType())) {
					PISRequestBean prb = new PISRequestBean();
					BeanUtils.copyProperties(rb, prb);
					prb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = pisRequestProcess.approvedRequest(prb);
				} else if (CPSUtils.compareStrings(CPSConstants.FAMILY, rb.getRequestType())) {
					FamilyRequestBean frb = new FamilyRequestBean();
					BeanUtils.copyProperties(rb, frb);
					frb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = familyRequestProcess.approvedRequest(frb);
				} else if (CPSUtils.compareStrings(CPSConstants.ADDRESS, rb.getRequestType())) {
					AddressRequestBean arb = new AddressRequestBean();
					BeanUtils.copyProperties(rb, arb);
					arb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = addressRequestProcess.approvedRequest(arb);
				} else if (CPSUtils.compareStrings(CPSConstants.NOMINEE, rb.getRequestType())) {
					NomineeRequestBean nrb = new NomineeRequestBean();
					BeanUtils.copyProperties(rb, nrb);
					nrb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = nomineeRequestProcess.approvedRequest(nrb);
				} else if (CPSUtils.compareStrings(CPSConstants.LEAVE, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CANCELLEAVEREQ, rb.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.CONVERTLEAVEREQ, rb.getRequestType())) {
					LeaveRequestBean lrb = new LeaveRequestBean();
					BeanUtils.copyProperties(rb, lrb);
					lrb.setSfID(session.getAttribute(CPSConstants.SFID).toString());
					lrb.setIpAddress(request.getRemoteAddr());
					message = leaveRequestProcess.approvedRequest(lrb);
				} else if (CPSUtils.compareStrings(CPSConstants.DEMAND, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CANCELDEMAND, rb.getRequestType())) {
					DemandRequestBean drb = (DemandRequestBean) session.getAttribute(CPSConstants.DEMANDITEMS);
					BeanUtils.copyProperties(rb, drb);
					drb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					drb.setItemsJson((JSONArray) session.getAttribute(CPSConstants.DEMANDITEMSJSON));
					String message1 = "";
					if (CPSUtils.compareStrings(CPSConstants.DEMAND, rb.getRequestType()))
						message1 = demandRequestProcess.setValues(drb);
					else
						message1 = CPSConstants.SUCCESS;
					if (CPSUtils.compareStrings(message1, CPSConstants.SUCCESS))
						message = demandRequestProcess.approvedRequest(drb);
				} else if (CPSUtils.compareStrings(CPSConstants.VOUCHER, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CERTIFICATESANCTION, rb.getRequestType())) {
					VoucherRequestBean vrb = new VoucherRequestBean();
					vrb = voucherRequestProcess.getVoucherDetails(rb);
					BeanUtils.copyProperties(rb, vrb);
					vrb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = voucherRequestProcess.approvedRequest(vrb);
				} else if (CPSUtils.compareStrings(CPSConstants.INVOICE, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CANCELIR, rb.getRequestType())) {
					InvoiceRequestBean irb = (InvoiceRequestBean) session.getAttribute(CPSConstants.INVOICEITEMS);
					BeanUtils.copyProperties(rb, irb);
					irb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					irb.setItemsJson((JSONArray) session.getAttribute(CPSConstants.JSONINVOICELIST));
					String message1 = "";
					if (CPSUtils.compareStrings(CPSConstants.INVOICE, rb.getRequestType())) {
						message1 = invoiceRequestProcess.setValues(irb);
					} else
						message1 = CPSConstants.SUCCESS;
					if (CPSUtils.compareStrings(message1, CPSConstants.SUCCESS))
						message = invoiceRequestProcess.approvedRequest(irb);
				} else if (CPSUtils.compareStrings("INVENTORYHOLDER", rb.getRequestType())) {
					InventoryHolderRequestBean invBean = new InventoryHolderRequestBean();
					BeanUtils.copyProperties(rb, invBean);
					invBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = invHolderRequestProcess.approvedRequest(invBean);
				} else if (CPSUtils.compareStrings(CPSConstants.SELFTRANSFERREQUESTTYPE, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.EMPTRANSFERREQUESTTYPE, rb.getRequestType())) {
					EmpTransferBean transferBean = new EmpTransferBean();
					BeanUtils.copyProperties(rb, transferBean);
					transferBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = transferRequestProcess.approvedRequest(transferBean);
				} else if (CPSUtils.compareStrings(CPSConstants.NEWQUARTERREQ, rb.getRequestType())) {
					QuarterRequestBean quarterBean = new QuarterRequestBean();
					BeanUtils.copyProperties(rb, quarterBean);
					quarterBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = quarterRequestProcess.approvedRequest(quarterBean);
				}else if (CPSUtils.compareStrings(CPSConstants.CHANGEQUARTERREQ, rb.getRequestType())) {
					QuarterRequestBean quarterBean = new QuarterRequestBean();
					BeanUtils.copyProperties(rb, quarterBean);
					quarterBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = quarterRequestProcess.approvedRequest(quarterBean);
				}else if (CPSUtils.compareStrings(CPSConstants.MOVABLEPROPERTYREQ, rb.getRequestType())) {
					AdminMisc adminMisc= new AdminMisc();
					BeanUtils.copyProperties(rb, adminMisc);
					adminMisc.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = propertyRequestProcess.approvedRequest(adminMisc);
				}else if (CPSUtils.compareStrings(CPSConstants.HIGHER_QUALIFICATION, rb.getRequestType())) {
					HQRequestBean hqRequestBean = new HQRequestBean();
					BeanUtils.copyProperties(rb, hqRequestBean);
					hqRequestBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = hqRequestProcess.approvedRequest(hqRequestBean);
				}else if (CPSUtils.compareStrings(CPSConstants.SANCTIONOFINCENTIVE, rb.getRequestType())) {
					HQRequestBean hqRequestBean = new HQRequestBean();
					BeanUtils.copyProperties(rb, hqRequestBean);
					hqRequestBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = hqRequestProcess.approvedRequest(hqRequestBean);
				} else if (CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, rb.getRequestType())) {
					TadaRequestProcessBean tadaRequestProcessBean = new TadaRequestProcessBean();
					BeanUtils.copyProperties(rb, tadaRequestProcessBean);
					tadaRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					tadaRequestProcessBean.setRequestId(rb.getRequestID());
					//tadaRequestProcessBean.setDoPartDate(rb.getDoPartDate());
					//tadaRequestProcessBean.setDoPartNo(rb.getDoPartNo());
					message = tadaRequestProcess.approvedRequest(tadaRequestProcessBean);
				} else if (CPSUtils.compareStrings(CPSConstants.TADA_WATER, rb.getRequestType())) {
					// this else if added by bkr 05/05/2016
					TadaWaterRequestProcessBean tadaWaterRequestProcessBean = new TadaWaterRequestProcessBean();
					BeanUtils.copyProperties(rb, tadaWaterRequestProcessBean);
					tadaWaterRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					tadaWaterRequestProcessBean.setRequestId(rb.getRequestID());
				//	message = tadaWaterRequestProcess.approvedRequest(tadaWaterRequestProcessBean);
					
					message=tadaWaterRequestProcess.approvedRequest(tadaWaterRequestProcessBean);
					
					
					
				} else if (CPSUtils.compareStrings(CPSConstants.LTC_WATER, rb.getRequestType())) {
					// this else if added by bkr 24/05/2016
					LTCWaterRequestProcessBean ltcWaterRequestProcessBean = new LTCWaterRequestProcessBean();
					BeanUtils.copyProperties(rb, ltcWaterRequestProcessBean);
					ltcWaterRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					ltcWaterRequestProcessBean.setRequestId(rb.getRequestID());
				//	message = tadaWaterRequestProcess.approvedRequest(tadaWaterRequestProcessBean);
					
					message=ltcWaterRequestProcess.approvedRequest(ltcWaterRequestProcessBean);
					
					
					
				} else if (CPSUtils.compareStrings(CPSConstants.LEAVE_WATER, rb.getRequestType())) {
					// this else if added by bkr 24/05/2016
					LeaveWaterRequestProcessBean leaveWaterRequestProcessBean = new LeaveWaterRequestProcessBean();
					BeanUtils.copyProperties(rb, leaveWaterRequestProcessBean);
					leaveWaterRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					leaveWaterRequestProcessBean.setRequestId(rb.getRequestID());
					
					message=leaveWaterRequestProcess.approvedRequest(leaveWaterRequestProcessBean);
					
					
					
				}else if (CPSUtils.compareStrings(CPSConstants.LEAVE_WATER_CANCEL, rb.getRequestType())) {
					// this else if added by bkr 21/06/2016
					LeaveWaterRequestProcessBean leaveWaterRequestProcessBean = new LeaveWaterRequestProcessBean();
					BeanUtils.copyProperties(rb, leaveWaterRequestProcessBean);
					leaveWaterRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					leaveWaterRequestProcessBean.setRequestId(rb.getRequestID());
					
					message=leaveWaterRequestProcess.approvedRequestForErpLeaveCancel(leaveWaterRequestProcessBean);
					
					
					
				}
				
				else if (CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, rb.getRequestType())) {
					TadaRequestProcessBean tadaRequestProcessBean = new TadaRequestProcessBean();
					BeanUtils.copyProperties(rb, tadaRequestProcessBean);
					tadaRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					tadaRequestProcessBean.setRequestId(rb.getRequestID());
					//tadaRequestProcessBean.setDoPartDate(rb.getDoPartDate());
					//tadaRequestProcessBean.setDoPartNo(rb.getDoPartNo());
//					if(CPSUtils.compareStrings(tadaRequestProcessBean.getWorkflowStageID(), "104")){
//						tadaRequestProcess.insertProjDirDetails(tadaRequestProcessBean);
//					}
					message = tadaRequestProcess.approvedRequest(tadaRequestProcessBean);
				} else if (CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, rb.getRequestType())) {
					TadaRequestProcessBean tadaRequestProcessBean = new TadaRequestProcessBean();
					BeanUtils.copyProperties(rb, tadaRequestProcessBean);
					tadaRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					tadaRequestProcessBean.setRequestId(rb.getRequestID());
					//tadaRequestProcessBean.setDoPartDate(rb.getDoPartDate());
					//tadaRequestProcessBean.setDoPartNo(rb.getDoPartNo());
					message = tadaRequestProcess.approvedRequest(tadaRequestProcessBean);
				} else if(CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, rb.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, rb.getRequestType())){
					TadaRequestProcessBean tadaRequestProcessBean = new TadaRequestProcessBean();
					BeanUtils.copyProperties(rb, tadaRequestProcessBean);
					tadaRequestProcessBean.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					tadaRequestProcessBean.setRequestId(rb.getRequestID());
					message = tadaRequestProcess.approvedRequest(tadaRequestProcessBean);
				} else if(CPSUtils.compareStrings(CPSConstants.TUTIONFEEREQUESTYPE, rb.getRequestType())){
					rb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message=tutionFeeRequestProcess.approveRequest(rb);
				}
				else if(CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLREQUESTYPE, rb.getRequestType())){
					rb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					message = telephoneBillRequestProcess.approveRequest(rb);
		     	}else if (CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATION, rb.getRequestType()) || CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.CANCELTRAININGNOMINATION)) {
					TrainingRequestBean trainingReqBean = new TrainingRequestBean();
					BeanUtils.copyProperties(rb, trainingReqBean);
					trainingReqBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
					trainingReqBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					trainingReqBean.setRequestId(rb.getRequestID());
					trainingRequestProcess.approvedRequest(trainingReqBean);
					
				} else if(CPSUtils.compareStrings(CPSConstants.MTVEHICLEREQUESTYPE, rb.getRequestType())){
					
					/*MTVehicleRequestDetailsDTO mtVehicleRequestDetailsDTO = new MTVehicleRequestDetailsDTO();
					mtVehicleRequestDetailsDTO.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					mtVehicleRequestDetailsDTO.setRequestID(rb.getRequestID());
					message = mtVehicleRequestProcess.approvedRequest(mtVehicleRequestDetailsDTO,rb);*/
					rb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
					rb.setDesignationID(requestWorkflowBusiness.getDesignationId(rb.getSfID()));
					message = mtVehicleRequestProcess.approvedRequest(rb);
					
				}else if(CPSUtils.compareStrings(CPSConstants.LOANCONVEYANCE, rb.getRequestType())){
					ErpLoanRequestDTO erpLoanRequestDTO = new ErpLoanRequestDTO();
					BeanUtils.copyProperties(rb, erpLoanRequestDTO);
					//erpLoanRequestBean.setEligibleAmount(eligibleAmount);
					erpLoanRequestProcess.approvedRequest(erpLoanRequestDTO);
				}
				

				// commit transaction & close the session
				hibernateUtils.closeSession();
				
				rb.setWorkflowHistory(requestWorkflowBusiness.getRequestStatusDetails(rb.getRequestID()));
				
				mav = new ModelAndView(CPSConstants.REQUESTHISTORYPAGE, CPSConstants.WORKFLOW, rb);
				mav.addObject(CPSConstants.MESSAGE, message);
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, rb.getWorkflowHistory());

			}
			// Super admin anybody to anybody delegation
			else if (CPSUtils.compareStrings(CPSConstants.SUPERDELEGATION, rb.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					rb = requestWorkflowBusiness.getMasterData(rb);
					mav = new ModelAndView(CPSConstants.REQUESTDELEGATION, CPSConstants.WORKFLOW, rb);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.SEARCHREQUEST, rb.getParam())) {
				rb = requestWorkflowBusiness.getSearchRequests(rb);
				mav = new ModelAndView(CPSConstants.REQUESTLIST, CPSConstants.WORKFLOW, rb);
				session.setAttribute(CPSConstants.REQUESTDETAILS, rb.getSearchList());
			} else if (CPSUtils.compareStrings("delegateSubmit", rb.getParam())) {
				rb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
				rb = txRequestProcess.delegateRequests(rb);

				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE, CPSConstants.WORKFLOW, rb);
				mav.addObject(CPSConstants.MESSAGE, rb.getMessage());
				mav.addObject(CPSConstants.REMARKS, rb.getRemarks());
				session.setAttribute(CPSConstants.REQUESTRESULTPAGE, rb.getSearchList());
			}
			// Super admin functionality end

			// Request wait code starts
			else if (CPSUtils.compareStrings(CPSConstants.WAIT, rb.getParam())) {
				rb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
				message = requestWorkflowBusiness.setWaitingStatus(rb);
				rb.setWorkflowHistory(requestWorkflowBusiness.getRequestStatusDetails(rb.getRequestID()));
				mav = new ModelAndView(CPSConstants.REQUESTHISTORYPAGE, CPSConstants.WORKFLOW, rb);
				mav.addObject(CPSConstants.MESSAGE, message);
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, rb.getWorkflowHistory());
			}else if (CPSUtils.compareStrings(CPSConstants.SENDTOEMU, rb.getParam())) {
				rb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
				message = requestWorkflowBusiness.setEmuStatus(rb);
				rb.setWorkflowHistory(requestWorkflowBusiness.getRequestStatusDetails(rb.getRequestID()));
				mav = new ModelAndView(CPSConstants.REQUESTHISTORYPAGE, CPSConstants.WORKFLOW, rb);
				mav.addObject(CPSConstants.MESSAGE, message);
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, rb.getWorkflowHistory());
			}
			else if(CPSUtils.compareStrings(CPSConstants.SENDTOHQ, rb.getParam())){
				rb.setSfID((session.getAttribute(CPSConstants.SFID)).toString());
				message = requestWorkflowBusiness.setHQSendingStatus(rb);
				rb.setWorkflowHistory(requestWorkflowBusiness.getRequestStatusDetails(rb.getRequestID()));
				mav = new ModelAndView(CPSConstants.REQUESTHISTORYPAGE, CPSConstants.WORKFLOW, rb);
				mav.addObject(CPSConstants.MESSAGE, message);
				mav.addObject(CPSConstants.REQUESTHISTORYLIST, rb.getWorkflowHistory());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
