package com.callippus.web.business.requestprocess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CDADetailsDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.FinanceDetailsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.LtcDomainObject;
import com.callippus.web.business.leave.admin.LeaveAdministratorBusiness;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.ltc.dao.approval.ILtcApprovalRequestDAO;
import com.callippus.web.ltc.dto.LtcAdvanceRequestDTO;
import com.callippus.web.ltc.dto.LtcApprovalRequestDTO;
import com.callippus.web.ltc.dto.LtcJourneyDetailsDTO;
import com.callippus.web.ltc.dto.LtcMemberDetailsDTO;
import com.callippus.web.ltc.dto.LtcRefundRequestDTO;
import com.callippus.web.ltc.dto.LtcReimbursementDTO;
import com.callippus.web.ltc.dto.LtcRequestProcessBean;

@Service
public class LtcRequestProcess extends TxRequestProcess {

	private static Log log = LogFactory.getLog(CghsRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private LtcDomainObject ltcDomainObject;
	@Autowired
	private LeaveAdministratorBusiness leaveAdministratorBusiness;
	@Autowired
	private ILtcApprovalRequestDAO ltcApprovalRequestDAO;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String initWorkflow(LtcRequestProcessBean ltcrb) throws Exception {
		log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflowForLtcApprovalRequest(LtcRequestProcessBean ltcrb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			ltcrb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			ltcrb.setRequestId(ltcrb.getRequestID());
			if(CPSUtils.compareStrings(ltcrb.getTypeValue(),CPSConstants.LTC_ADVANCE_AMENDMENT)) {
				ltcrb = setLtcMemberDetails(ltcrb);
				if(checkAdvanceIssuedMoney(ltcrb.getId())) {
					ltcrb.setRequestType(CPSConstants.LTC_APP_ADV);
					ltcrb.setRequestTypeID("21");
					ltcrb.setLtcRequestType(CPSConstants.LTCADVANCEAMENDMENT);
					message=submitLtcAdvanceTxnDetails(ltcrb);
				}else{
					ltcrb.setRequestType(CPSConstants.LTCAPPROVAL);
					ltcrb.setRequestTypeID("22");
					ltcrb.setLtcRequestType(CPSConstants.LTCADVANCEAMENDMENT);
					message=submitLtcAdvanceTxnDetails(ltcrb);
					message=updateOldValues(ltcrb.getId(),ltcrb.getRequestID());
				}
			}else if(CPSUtils.compareStrings(ltcrb.getTypeValue(),CPSConstants.LTC_APPROVAL_AMENDMENT)) {
				ltcrb = setLtcMemberDetails(ltcrb);
				if(CPSUtils.compareStrings(ltcrb.getType(),CPSConstants.LTC_APPROVAL)) {
					ltcrb.setRequestType(CPSConstants.LTCAPPROVAL);
					ltcrb.setRequestTypeID("22");
					ltcrb.setLtcRequestType(CPSConstants.LTCAPPROVALAMENDMENT);
					message=submitTxnDetails(ltcrb);
				}
			}else if (CPSUtils.compareStrings(CPSConstants.LTCREFUNDTYPE, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTCREFUNDVALUE);
				ltcrb.setRequestTypeID("24");
				message=submitLTCRefundTxnDetails(ltcrb);
			}else if (CPSUtils.compareStrings(CPSConstants.REIMBURSEMENT, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTCREIMBURSEMENT);
				ltcrb.setRequestTypeID("25");
				ltcrb = setLtcReimbursementDetails(ltcrb);
				message=submitLTCReimbursementTxnDetails(ltcrb);
			}else if (CPSUtils.compareStrings(CPSConstants.SETTLEMENT, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTCSETTLEMENT);
				ltcrb.setRequestTypeID("26");
				ltcrb = setLtcReimbursementDetails(ltcrb);
				message=submitLTCReimbursementTxnDetails(ltcrb);
			}else if(CPSUtils.compareStrings(CPSConstants.LTC_ADVANCE, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTC_APP_ADV);
				ltcrb.setRequestTypeID("21");
				ltcrb = setLtcMemberDetails(ltcrb);
				ltcrb.setLtcRequestType(CPSConstants.LTC_APP_ADV);
				message=submitLtcAdvanceTxnDetails(ltcrb);
			}else if(CPSUtils.compareStrings(CPSConstants.LTC_APPROVAL, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTCAPPROVAL);
				ltcrb.setRequestTypeID("22");
				ltcrb = setLtcMemberDetails(ltcrb);
				ltcrb.setLtcRequestType(CPSConstants.LTCAPPROVAL);
				message=submitTxnDetails(ltcrb);
			}else if(CPSUtils.compareStrings(CPSConstants.ADVANCE, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTCADVANCE);
				ltcrb.setRequestTypeID("30");
				message=submitAdvanceRequest(ltcrb);
			}else if (CPSUtils.compareStrings(CPSConstants.LTCCANCLE, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTC_CANCEL);
				ltcrb.setRequestTypeID("32");
				message=submitLTCRefundTxnDetails(ltcrb);
			}else if (CPSUtils.compareStrings(CPSConstants.LTCAPPRCUMADVCANCLE, ltcrb.getType())) {
				ltcrb.setRequestType(CPSConstants.LTC_APPR_CUM_ADV_CANCEL);
				ltcrb.setRequestTypeID("41");
				message=submitLTCRefundTxnDetails(ltcrb);
			}
			
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(ltcrb, rb);
				message = txRequestProcess.initWorkflow(rb);
			}

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public String updateOldValues(String oldReqId,String newReqId)throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			HashMap<String, String> hm = (HashMap<String, String> )session.createSQLQuery("select amount_claimed||'' as amount_claimed,amount_per_person||'' as amount_per_person,issued_amount||'' as issued_amount,cda_amount||'' as cda_amount,accountent_sign,sanction_no||'' as sanction_no,bill_no||'' as bill_no,dv_no||'' as dv_no,to_char(dv_date,'dd-Mon-yyyy') dv_date,amount_per_each_infant||'' as amount_per_each_infant from ltc_advance_request_details where request_id=?").setString(0, oldReqId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
			
			LtcAdvanceRequestDTO ltcAdvance = (LtcAdvanceRequestDTO)session.get(LtcAdvanceRequestDTO.class, newReqId);
			EmployeeClaimDetailsDTO empadvance=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID,Integer.valueOf(newReqId))).add(Expression.eq("requestType",CPSConstants.ADVANCE)).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(empadvance)) {
				if (!CPSUtils.isNullOrEmpty(hm.get("AMOUNT_CLAIMED"))) {
					empadvance.setAmountClaimed(Float.valueOf(hm.get("AMOUNT_CLAIMED")));
				}
				session.update(empadvance);
				FinanceDetailsDTO financeAdvance = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq("referenceID", empadvance.getId())).uniqueResult();
				if (!CPSUtils.isNullOrEmpty(financeAdvance)) {
					if (!CPSUtils.isNullOrEmpty(hm.get("ISSUED_AMOUNT"))) {
						financeAdvance.setAmount(Float.valueOf(hm.get("ISSUED_AMOUNT")));
					}
					if (!CPSUtils.isNullOrEmpty(hm.get("ACCOUNTENT_SIGN"))) {
						financeAdvance.setAccountentSign(hm.get("ACCOUNTENT_SIGN"));
					}
					if (!CPSUtils.isNullOrEmpty(hm.get("SANCTION_NO"))) {
						financeAdvance.setSanctionNo(hm.get("SANCTION_NO"));
					}
					if (!CPSUtils.isNullOrEmpty(hm.get("BILL_NO"))) {
						financeAdvance.setBillNo(hm.get("BILL_NO"));
					}
					session.update(financeAdvance);
					CDADetailsDTO cdaAdvance = (CDADetailsDTO) session.createCriteria(CDADetailsDTO.class).add(Expression.eq("referenceID", financeAdvance.getId())).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(cdaAdvance)){
						if (!CPSUtils.isNullOrEmpty(hm.get("CDA_AMOUNT"))) {
							cdaAdvance.setCdaAmount(Float.valueOf(hm.get("CDA_AMOUNT")));
						}
						if (!CPSUtils.isNullOrEmpty(hm.get("DV_NO"))) {
							cdaAdvance.setDvNumber(hm.get("DV_NO"));
						}
						if (!CPSUtils.isNullOrEmpty(hm.get("DV_DATE"))) {
							cdaAdvance.setDvDate(CPSUtils.convertStringToDate(hm.get("DV_DATE")));
						}
						session.update(cdaAdvance);
					}
				}
			}
			ltcAdvance.setAmountPerPerson(hm.get("AMOUNT_PER_PERSON"));
			ltcAdvance.setAmountPerEachInfant(hm.get("AMOUNT_PER_EACH_INFANT"));			
			session.update(ltcAdvance);
			message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
	public boolean checkAdvanceIssuedMoney(String requestId)throws Exception {
		Session session = null;
		boolean flag = false;
		try {
			session = hibernateUtils.getSession();
			if(CPSUtils.isNull(session.createSQLQuery("select issued_amount from ltc_advance_request_details  where request_id=?").setString(0, requestId).uniqueResult())){
				flag = true;
			}
		}catch (Exception e) {
			throw e;
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	public String submitAdvanceRequest (LtcRequestProcessBean ltcrb) throws Exception {
		String message=null;
		LtcAdvanceRequestDTO ltcAdvanceReq=null;
		Session session = null;
		Criteria crit = null;
		List<LtcAdvanceRequestDTO> list = null;
		try {
			session = hibernateUtils.getSession();
		
			if(!(CPSUtils.isNullOrEmpty(ltcrb.getReferenceRequestID()))){
			crit =session.createCriteria(LtcAdvanceRequestDTO.class).add(Expression.eq("referenceRequestID", ltcrb.getReferenceRequestID())).add(Expression.eq("sfID", ltcrb.getSfID())).add(Expression.eq("status",2));
			list = crit.list();
			}
			if(list.size() == 0){
			ltcAdvanceReq = ltcApprovalRequestDAO.getLtcApprovalDetails(ltcrb.getReferenceRequestID());
			ltcAdvanceReq.setRequestId(ltcrb.getRequestID());
			//ltcAdvanceReq.setAmountClaimed(ltcrb.getAmountClaimed());
			ltcAdvanceReq.setNoOfTickets(ltcrb.getNoOfTickets());
			ltcAdvanceReq.setAmountPerPerson(ltcrb.getAmountPerPerson());
			ltcAdvanceReq.setNoOfInfantTickets(ltcrb.getNoOfInfantTickets());
			ltcAdvanceReq.setAmountPerEachInfant(ltcrb.getAmountPerEachInfant());
			ltcAdvanceReq.setStatus(2);
			//ltcAdvanceReq.setAppliedBy(ltcrb.getSfID());
			ltcAdvanceReq.setAppliedDate(CPSUtils.getCurrentDate());
			//ltcAdvanceReq.setIpAddress(ltcrb.getIpAddress());
			ltcAdvanceReq.setLtcRequestType(CPSConstants.LTCADVANCE);
			session.saveOrUpdate(ltcAdvanceReq);
			
			EmployeeClaimDetailsDTO employeeClaimDetailsDTO=new EmployeeClaimDetailsDTO();
			employeeClaimDetailsDTO.setModuleId(Integer.valueOf(CPSConstants.LTC_MODULE_ID));
			employeeClaimDetailsDTO.setRequestType(CPSConstants.ADVANCE);
			employeeClaimDetailsDTO.setRequestTypeID(Integer.valueOf(ltcrb.getRequestTypeID()));
			employeeClaimDetailsDTO.setWorkFlowStatus(2);
			employeeClaimDetailsDTO.setRequestID(Integer.valueOf(ltcrb.getRequestID()));
			employeeClaimDetailsDTO.setRefRequestId(Integer.valueOf(ltcrb.getReferenceRequestID()));
			employeeClaimDetailsDTO.setAmountClaimed(Float.valueOf(ltcrb.getAmountClaimed()));
			employeeClaimDetailsDTO.setStatus(1);
			employeeClaimDetailsDTO.setAppliedBy(ltcrb.getSfID());
			employeeClaimDetailsDTO.setAppliedDate(CPSUtils.getCurrentDateWithTime());
			employeeClaimDetailsDTO.setIpAddress(ltcrb.getIpAddress());
			session.save(employeeClaimDetailsDTO);
			
			if(!CPSUtils.isNullOrEmpty(ltcAdvanceReq.getEncashmentDays())){
				EmployeeClaimDetailsDTO employeeEncashmentClaim=new EmployeeClaimDetailsDTO();
				employeeEncashmentClaim.setModuleId(Integer.valueOf(CPSConstants.LTC_MODULE_ID));
				employeeEncashmentClaim.setRequestType(CPSConstants.ENCASHMENT);
				employeeEncashmentClaim.setRequestTypeID(Integer.valueOf(ltcrb.getRequestTypeID()));
				employeeEncashmentClaim.setWorkFlowStatus(ltcAdvanceReq.getStatus());
				employeeEncashmentClaim.setRequestID(Integer.valueOf(ltcrb.getRequestID()));
				employeeEncashmentClaim.setRefRequestId(Integer.valueOf(ltcrb.getReferenceRequestID()));
				employeeEncashmentClaim.setAmountClaimed(Float.valueOf(ltcAdvanceReq.getEncashmentAmount()));
				employeeEncashmentClaim.setStatus(1);
				employeeEncashmentClaim.setAppliedBy(ltcrb.getSfID());
				employeeEncashmentClaim.setAppliedDate(CPSUtils.getCurrentDateWithTime());
				employeeEncashmentClaim.setIpAddress(ltcrb.getIpAddress());
				session.save(employeeEncashmentClaim);
			}
			
			JSONObject ltcMembersJson = new JSONObject(ltcrb.getJsonValue());
			ltcrb.setLtcMemberDetails(getLtcMembersList(ltcMembersJson,ltcrb));
			
			
			for (LtcMemberDetailsDTO ltcMember : ltcrb.getLtcMemberDetails()) {
				ltcMember.setLtcType(ltcAdvanceReq.getLtcTypeId());
				ltcMember.setBlockYear(ltcAdvanceReq.getLtcBlockYearId());
				ltcMember.setStatus("1");
				ltcMember.setRequestType(CPSConstants.LTCADVANCE);
				session.save(ltcMember);
			}
			
			
			session.createSQLQuery("update ltc_request_details_bkp set advance_flag='"+ltcrb.getRequestID()+"' where request_id=?").setString(0, ltcrb.getReferenceRequestID()).executeUpdate();
			session.createSQLQuery("update ltc_txn_details set status=9 where request_id=?").setString(0, ltcrb.getReferenceRequestID()).executeUpdate();
			
			message=CPSConstants.SUCCESS;
			}else{
			message = CPSConstants.DUPLICATE;
			}
			}catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String submitTxnDetails(LtcRequestProcessBean ltcrb) throws Exception {
  		Session session = null;
		LtcApprovalRequestDTO ltcAppr = null;
		try {
			log.debug("LtcRequestProcess >>>>>>>>>>>> submitTxnDetails(LtcRequestProcessBean ltcrb) >>>>> Start");
			ltcAppr = new LtcApprovalRequestDTO();
			BeanUtils.copyProperties(ltcrb, ltcAppr);
			ltcAppr.setCreatedBy(ltcAppr.getSfID());
			ltcAppr.setCreationDate(CPSUtils.getCurrentDate());
			ltcAppr.setStatus(2);
			ltcAppr.setAmendmentRefRequestID(ltcrb.getId());
			session = hibernateUtils.getSession();
			if(!CPSUtils.isNullOrEmpty(ltcAppr.getEncashmentDays())) {
				EmployeeClaimDetailsDTO employeeClaimDetailsDTO=new EmployeeClaimDetailsDTO();
				employeeClaimDetailsDTO.setModuleId(Integer.valueOf(CPSConstants.LTC_MODULE_ID));
				employeeClaimDetailsDTO.setAmountClaimed(Float.valueOf((String)session.createSQLQuery("select round(((epd.basic_pay+epd.grade_pay)+(epd.basic_pay+epd.grade_pay)*(select value from configuration_details where name='LTC_DA_PERCENTAGE')/100)*(?/30))||'' money from emp_payment_details epd where sfid=?").setString(0, ltcAppr.getEncashmentDays()).setString(1, ltcAppr.getSfID()).uniqueResult()));
				employeeClaimDetailsDTO.setRequestTypeID(Integer.valueOf(ltcrb.getRequestTypeID()));
				employeeClaimDetailsDTO.setWorkFlowStatus(2);
				employeeClaimDetailsDTO.setRequestID(Integer.valueOf(ltcAppr.getRequestId()));
				employeeClaimDetailsDTO.setStatus(1);
				employeeClaimDetailsDTO.setAppliedBy(ltcAppr.getSfID());
				employeeClaimDetailsDTO.setAppliedDate(CPSUtils.getCurrentDateWithTime());
				employeeClaimDetailsDTO.setIpAddress(ltcAppr.getIpAddress());
				employeeClaimDetailsDTO.setRequestType(CPSConstants.ENCASHMENT);
				session.save(employeeClaimDetailsDTO);
		//		ltcAppr.setEncashmentAmount((String)session.createSQLQuery("select round(((epd.basic_pay+epd.grade_pay)+(epd.basic_pay+epd.grade_pay)*(select value from configuration_details where name='LTC_DA_PERCENTAGE')/100)*(?/30))||'' money from emp_payment_details epd where sfid=?").setString(0, ltcAppr.getEncashmentDays()).setString(1, ltcAppr.getSfID()).uniqueResult());
			}
			session.save(ltcAppr);
			for (LtcMemberDetailsDTO ltcMember : ltcAppr.getLtcMemberDetails()) {
				ltcMember.setLtcType(ltcrb.getLtcTypeId());
				ltcMember.setBlockYear(ltcrb.getLtcBlockYearId());
				ltcMember.setStatus("1");
				ltcMember.setRequestType(CPSConstants.LTCAPPROVAL);
				session.save(ltcMember);
			}
			ltcrb.setMessage(CPSConstants.SUCCESS);
			if(CPSUtils.compareStrings(ltcrb.getMessage(),CPSConstants.SUCCESS) && !CPSUtils.isNullOrEmpty(ltcAppr.getEncashmentDays()) && Float.parseFloat(ltcAppr.getEncashmentDays()) >0){
				log.debug("LtcRequestProcess >>>>>>>>>>>> User Encashing "+ltcAppr.getEncashmentDays()+ " Leaves, Then call saveLeaveAdit() method");
				ltcrb.setMessage(deductEncashmentDays(ltcAppr.getSfID(),ltcAppr.getEncashTypeId(),"-"+ltcAppr.getEncashmentDays(),"LTC Leave Encashment"));
				/*ltcrb.setMessage(updateLtcLeaveEncashDays(ltcAppr.getSfID(),ltcAppr.getEncashTypeId(),"-"+ltcAppr.getEncashmentDays(),"LTC Leave Encashment"));
				session.createSQLQuery("update leave_emp_spells set ltc_encashment_days=(ltc_encashment_days+?) where sfid=? and leave_type_id=?").setFloat(0, Float.parseFloat(ltcAppr.getEncashmentDays())).setString(1, ltcAppr.getSfID()).setString(2, ltcAppr.getEncashTypeId()).executeUpdate();*/
			}
			log.debug("LtcRequestProcess >>>>>>>>>>>> submitTxnDetails(LtcRequestProcessBean ltcrb) >>>>> Start");
		} catch (Exception e) {
			ltcrb.setMessage(CPSConstants.FAILED);
			throw e;
		} 
		return ltcrb.getMessage();
	}
	public String deductEncashmentDays(String sfid,String encashLeaveTypeId,String encashDays,String remarks)throws Exception {
		String message=null;
		Session session =null;
		try{
			session= hibernateUtils.getSession();
			message= updateLtcLeaveEncashDays(sfid,encashLeaveTypeId,encashDays,remarks);
			session.createSQLQuery("update leave_emp_spells set ltc_encashment_days=(ltc_encashment_days+?) where sfid=? and leave_type_id=?").setFloat(0, Float.parseFloat(encashDays)).setString(1, sfid).setString(2, encashLeaveTypeId).executeUpdate();
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	public String updateLtcLeaveEncashDays(String sfid,String encashLeaveTypeId,String encashDays,String remarks)throws Exception {
		String message=null;
		try {
			LeaveAdministratorBean leaveBean = new LeaveAdministratorBean();
			leaveBean.setSearchSfid(sfid);
			leaveBean.setLeaveType(encashLeaveTypeId);
			leaveBean.setTxnDate(CPSUtils.getCurrentDate());
			leaveBean.setNoOfDays(encashDays);
			leaveBean.setRemarks(remarks);
			leaveBean.setTxnType(CPSConstants.LTCSTATUSID);
			leaveBean.setTxnTypeVal(CPSConstants.LTCSTATUSID);
			message = leaveAdministratorBusiness.saveLeaveAdit(leaveBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String submitLTCRefundTxnDetails(LtcRequestProcessBean ltcrb) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String query="select count(*) value from ltc_refund_request where reference_id=? and status not in(6,9)";
			KeyValueDTO keyvaluedto=(KeyValueDTO)session.createSQLQuery(query).addScalar("value",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, ltcrb.getRequestIDs()).uniqueResult();
			if(!CPSUtils.isGreaterOrNot(keyvaluedto.getValue(), Integer.parseInt(CPSConstants.ZERO))){
				
				session.createSQLQuery("update request_workflow_history set status=?,remarks=?,ip_address=?,stage_status=? where id=( select max(id) from request_workflow_history where request_id=?)").setString(0, CPSConstants.STATUSCANCELLED).setString(1, ltcrb.getRemarks()).setString(2, ltcrb.getIpAddress())	.setString(3, CPSConstants.STATUSCANCELLED).setString(4, ltcrb.getRequestIDs()).executeUpdate();			
				
				LtcRefundRequestDTO ltcRefundRequestDTO=new LtcRefundRequestDTO();
				ltcRefundRequestDTO.setRequestID(ltcrb.getRequestID());
				ltcRefundRequestDTO.setReferenceID(Integer.parseInt(ltcrb.getRequestIDs()));
				ltcRefundRequestDTO.setStatus(2);
				ltcRefundRequestDTO.setRequestDate(CPSUtils.getCurrentDate());
				ltcRefundRequestDTO.setIpAddress(ltcrb.getIpAddress());
				ltcRefundRequestDTO.setRemarks(ltcrb.getRemarks());
				session.saveOrUpdate(ltcRefundRequestDTO);
				
				query = "update ltc_txn_details set status=9 where request_id=?";
				session.createSQLQuery(query).setString(0, ltcrb.getRequestIDs()).executeUpdate();
				if(CPSUtils.compareStrings(ltcrb.getType(),"ltcrefundtype")||CPSUtils.compareStrings(ltcrb.getCancleType(),"ltcApprCumAdvanceCancle")) {
					//query = "update ltc_advance_request_details lard set status=9,lard.excess_amount=lard.cda_amount,EXCESS_AMOUNT_FINE=(select round((lard.cda_amount*round(sysdate-to_date(lard.dv_date)))/365*10/100) intrest from ltc_advance_request_details lard where lard.request_id=?) where request_id = ?";
					//query = "update ltc_advance_request_details lard set status=9 where request_id = ?";
					query = "update emp_claim_details set status=9 where request_id = ?";
					session.createSQLQuery(query).setString(0, ltcrb.getRequestIDs()).executeUpdate();
				}else {
					//query = "update ltc_request_details lard set status=9 where request_id = ?";
					query = "update emp_claim_details set status=9 where request_id = ?";
					session.createSQLQuery(query).setString(0, ltcrb.getRequestIDs()).executeUpdate();
				}
				ltcrb.setMessage(CPSConstants.SUCCESS);
			}
			else
			{
				ltcrb.setMessage(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			ltcrb.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return ltcrb.getMessage();
	}
	
	public String submitLTCReimbursementTxnDetails(LtcRequestProcessBean ltcrb) throws Exception {
		Session session = null;
		ltcrb.setMessage(CPSConstants.FAILED);
		try {
			session = hibernateUtils.getSession();
			if(checkDuplicate(ltcrb.getId())) {
				session.save(ltcrb.getLtcReimbursementDTO());
				session.save(ltcrb.getEmployeeClaimDetailsDTO());
				
				for (LtcJourneyDetailsDTO ltcJourney : ltcrb.getLtcJourneyDetails()) {
					session.save(ltcJourney);
				}
				for (LtcMemberDetailsDTO ltcMember : ltcrb.getLtcMemberDetails()) {
					session.createQuery("update LtcMemberDetailsDTO set referenceID=? where requestId=? and familyMemberId=?").setString(0, ltcrb.getRequestId())
					.setString(1, ltcrb.getId()).setString(2, ltcMember.getFamilyMemberId()).executeUpdate();
				}
				ltcrb.setMessage(CPSConstants.SUCCESS);
			}else {
				ltcrb.setMessage(CPSConstants.DUPLICATE);
			}
		} catch (Exception e) {
			ltcrb.setMessage(CPSConstants.FAILED);
			throw e;
		} 
		return ltcrb.getMessage();
	}
	
	public LtcRequestProcessBean setLtcMemberDetails(LtcRequestProcessBean ltcrb) throws Exception {
		try {
			EmployeeBean empBean = (EmployeeBean) ltcrb.getSession().getAttribute(CPSConstants.EMPLOYEEDETAILS);
			ltcrb.setDesignationId(empBean.getDesignationId());
			ltcrb.setDepartmentId(empBean.getOfficeId());
			ltcrb.setPhoneNum(empBean.getInternalNo());
			ltcrb.setGradePay(empBean.getGradePay());
			ltcrb.setEmploymentTypeId(String.valueOf(empBean.getEmploymentId()));
			
			JSONObject ltcMembersJson = new JSONObject(ltcrb.getJsonValue());
			ltcrb.setLtcMemberDetails(getLtcMembersList(ltcMembersJson,ltcrb));
		} catch (Exception e) {
			throw e;
		}
		return ltcrb;
	}
	public LtcRequestProcessBean setLtcReimbursementDetails(LtcRequestProcessBean ltcrb) throws Exception {
		List<LtcJourneyDetailsDTO> list=null;
		LtcReimbursementDTO ltcReimbursementDTO=null;
		try {
			
				EmployeeBean empBean = (EmployeeBean) ltcrb.getSession().getAttribute(CPSConstants.EMPLOYEEDETAILS);
				ltcReimbursementDTO=new LtcReimbursementDTO();
				ltcReimbursementDTO.setDesignationId(empBean.getDesignationId());
				ltcReimbursementDTO.setDepartmentId(empBean.getDirectorateId());
				ltcReimbursementDTO.setSfID(ltcrb.getSfID());
				ltcReimbursementDTO.setBasicPay(empBean.getGradePay());
				ltcReimbursementDTO.setRequestId(ltcrb.getRequestId());
				ltcReimbursementDTO.setReferenceId(ltcrb.getId());
				ltcReimbursementDTO.setRequestType(ltcrb.getType());
				ltcReimbursementDTO.setUnitFormation(ltcrb.getUnitFormation());
				ltcReimbursementDTO.setTotalAmount(ltcrb.getTotalAmount());
				
				EmployeeClaimDetailsDTO employeeClaimDetailsDTO=new EmployeeClaimDetailsDTO();
				employeeClaimDetailsDTO.setModuleId(Integer.valueOf(CPSConstants.LTC_MODULE_ID));
				employeeClaimDetailsDTO.setRequestID(Integer.valueOf(ltcrb.getRequestId()));
				employeeClaimDetailsDTO.setRefRequestId(Integer.valueOf(ltcrb.getId()));
				employeeClaimDetailsDTO.setRequestType(ltcrb.getType());
				employeeClaimDetailsDTO.setAmountClaimed(Float.valueOf(ltcrb.getTotalAmount()));
				employeeClaimDetailsDTO.setStatus(1);
				employeeClaimDetailsDTO.setWorkFlowStatus(2);
				employeeClaimDetailsDTO.setRequestTypeID(Integer.valueOf(ltcrb.getRequestTypeID()));
				employeeClaimDetailsDTO.setAppliedBy(ltcrb.getSfID());
				employeeClaimDetailsDTO.setAppliedDate(CPSUtils.getCurrentDateWithTime());
				employeeClaimDetailsDTO.setIpAddress(ltcrb.getIpAddress());
				ltcrb.setEmployeeClaimDetailsDTO(employeeClaimDetailsDTO);
				
				ltcReimbursementDTO.setClaimPreferredOn(ltcrb.getClaimPreferred());
				ltcReimbursementDTO.setModeOfPayment(ltcrb.getModeOfPayment());
				ltcReimbursementDTO.setLeaveRequestId(ltcrb.getLeaveRequestId());
				ltcReimbursementDTO.setStatus(2);
				ltcReimbursementDTO.setAppliedBy(ltcrb.getSfID());
				ltcReimbursementDTO.setAppliedDate(CPSUtils.getCurrentDate());
				ltcReimbursementDTO.setIpAddress(ltcrb.getIpAddress());
				
				
				ltcrb.setLtcReimbursementDTO(ltcReimbursementDTO);
				
				list=new ArrayList<LtcJourneyDetailsDTO>();
				JSONObject mainJson = new JSONObject(ltcrb.getJsonValue());
				
				JSONObject ltcMembersJson=(JSONObject)mainJson.get("ltcMembers");
				ltcrb.setLtcMemberDetails(getLtcMembersList(ltcMembersJson,ltcrb));
				
				JSONObject journeyDetailsJson=(JSONObject)mainJson.get("journeyDetails");
				for (int i = 3; i < journeyDetailsJson.length(); i++) {
					JSONObject valueJson=(JSONObject)journeyDetailsJson.get(String.valueOf(i));
					LtcJourneyDetailsDTO journeyDTO=new LtcJourneyDetailsDTO();
					journeyDTO.setReferenceId(ltcrb.getRequestId());
					journeyDTO.setDepartureDate(valueJson.get("dDate").toString());
					journeyDTO.setDepartureTime(valueJson.get("dTime").toString());
					journeyDTO.setDepartureStation(valueJson.get("dStation").toString());
					journeyDTO.setArrivalDate(valueJson.get("aDate").toString());
					journeyDTO.setArrivalTime(valueJson.get("aTime").toString());
					journeyDTO.setArrivalStation(valueJson.get("aStation").toString());
					journeyDTO.setDistance(valueJson.get("distance").toString());
					journeyDTO.setModeOfTravel(valueJson.get("modeOfTravel").toString());
					journeyDTO.setFarePerPerson(valueJson.get("fare").toString());
					journeyDTO.setNoOfPersons(valueJson.get("persons").toString());
					journeyDTO.setTotalClaimed(valueJson.get("totalAmt").toString());
					journeyDTO.setTitcketNos(valueJson.get("ticketNo").toString());
					journeyDTO.setJourneyType(valueJson.get("journyType").toString());
					list.add(journeyDTO);
				}
				ltcrb.setLtcJourneyDetails(list);

			
			
		} catch (Exception e) {
			throw e;
		}
		return ltcrb;
	}
	public List<LtcMemberDetailsDTO> getLtcMembersList(JSONObject ltcMembersJson,LtcRequestProcessBean ltcrb) throws Exception {
		List<LtcMemberDetailsDTO> ltcMemberDetails = null;
		try {
			ltcMemberDetails = new ArrayList<LtcMemberDetailsDTO>();
			for (int i = 0; i < ltcMembersJson.length(); i++) {
				LtcMemberDetailsDTO ltcMember = new LtcMemberDetailsDTO();
				ltcMember.setRequestId(ltcrb.getRequestId());
				ltcMember.setFamilyMemberId(ltcMembersJson.getString(String.valueOf(i)));
				ltcMemberDetails.add(ltcMember);
			}
		} catch (Exception e) {
			throw e;
		}
		return ltcMemberDetails;
	}

	public String approvedRequest(LtcRequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LtcRequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, processBean);
				rb.setMessage(ltcDomainObject.updateTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	
	public String approvedLtcAdvanceRequest(LtcRequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LtcRequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		Session session=null; 
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			
			session=hibernateUtils.getSession();
			//Object empRoleId = 	session.createSQLQuery("select org_role_id from emp_role_mapping where org_role_id in( select ori.org_role_id from org_role_instance ori where ori.status = 1 start with ori.org_role_id=(select value from configuration_details where name=?) connect by ori.parent_org_role_id = prior ori.org_role_id) and status=1 and sfid=?").setString(0, CPSConstants.ADMIN).setString(1, processBean.getSfID()).uniqueResult();
			Object empRoleId = 	session.createSQLQuery("select  distinct to_char( case when org_role_id is null  then null else   8  end ) from emp_role_mapping where org_role_id in  (select ori.org_role_id   from org_role_instance ori  where ori.status    = 1  start with ori.org_role_id=(select value from configuration_details where name=? ) CONNECT BY ori.parent_org_role_id = prior ori.org_role_id ) and status=1 and sfid =?").setString(0, CPSConstants.ADMIN).setString(1, processBean.getSfID()).uniqueResult();
			
			if(!CPSUtils.isNullOrEmpty(empRoleId)){
				//commented by bkr 19/04/2016 only one line
				//session.createQuery("update LtcAdvanceRequestDTO set status=29,doPartId=? where requestId=?").setString(0, processBean.getDoPartNo()).setString(1, processBean.getRequestId()).executeUpdate();
				//added by bkr 19/04/2016 only one line
				session.createQuery("update LtcAdvanceRequestDTO set status=29,MROPaidDate=?,nearesrAirport=? where requestId=?").setString(0, processBean.getDoPartDate()).setString(1,  processBean.getDoPartNo()).setString(2, processBean.getRequestId()).executeUpdate();
				session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=29 where requestID=?").setString(0, processBean.getRequestId()).executeUpdate();
				/**
				 * Update do_part_id in leave_request_details
				 */
				/**
				 * Leave_request_details status should be updated to completed state when attached to Ltc... so status 8 is adding in below query on 07-05-2014
				 * 
				 * and request_workflow_history  table status is also modifying 
				 * 
				 */
				
				
				
				//session.createSQLQuery("update leave_request_details set do_part_id=?,status=8  where request_id=(select leave_request_id from ltc_advance_request_details where request_id=?)").setString(0, processBean.getDoPartNo()).setString(1, processBean.getRequestId()).executeUpdate();
			   
				session.createSQLQuery(
						"update request_workflow_history set status=?,stage_status=? where id=(select id from (select max(id) id,max(request_stage) from request_workflow_history where request_id=? and ip_address is not null)) and status=?")
				.setString(0, CPSConstants.STATUSCOMPLETED).setString(1, CPSConstants.STATUSCOMPLETED).setString(2, processBean.getRequestId()).setString(3, CPSConstants.STATUSSANCTIONED).executeUpdate();
			
			}
			
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())||CPSUtils.compareStrings("4", rb.getStageID())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, processBean);
				rb.setMessage(ltcDomainObject.updateTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	
	public String approvedRequest(LtcRefundRequestDTO ltcRefundRequestDTO) throws Exception {
		log.debug("::<<<<<LtcRefundProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LtcRefundRequestDTO ltcRefundRequestDTO)>>>>>>>>>");
		RequestBean rb = null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			
			rb = new RequestBean();
			BeanUtils.copyProperties(ltcRefundRequestDTO, rb);
			rb = txRequestProcess.approvedRequest(rb);
			
			
			if(!CPSUtils.isNull(ltcRefundRequestDTO.getDoPartNo())) {
				session.createQuery("update LtcRefundRequestDTO set doPartID=?,status=? where requestID=?").setString(0, ltcRefundRequestDTO.getDoPartNo()).setString(1, CPSConstants.STATUSCOMPLETED).setString(2, ltcRefundRequestDTO.getRequestID()).executeUpdate();
			}
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, ltcRefundRequestDTO);
				
				rb.setMessage(ltcDomainObject.updateRefundTxnDetails(rb));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	public String approvedReimbersementRequest(LtcRequestProcessBean processBean) throws Exception {
		log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LtcRequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		try {System.out.println("check");
			rb = new RequestBean();
			BeanUtils.copyProperties(processBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage()) || CPSUtils.compareString("2", rb.getStageID())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, processBean);
				/*if(rb.getExcessAmount() !=null && !rb.getExcessAmount().equals("") && rb.getExcessAmountFine()!=null && !rb.getExcessAmountFine().equals("") && rb.getMROPaidNo() == null && rb.getMROPaidDate()== null){
					rb.setMessage(ltcDomainObject.updateRestrictionAmount(rb));  //added for MRO
				}*/
				rb.setMessage(ltcDomainObject.updateReimbursementTxnDetails(processBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	public RequestBean updateLtcRequestStatus(RequestBean rb,String status)throws Exception {
		log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>declineRequest(RequestBean rb)>>>>>>>>>");
		Session session=null;
		int stageStatus;
		int workflowStatus;
		try {
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCREIMBURSEMENT) || CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCSETTLEMENT)) {
				session.createQuery("update LtcMemberDetailsDTO set referenceID=null where referenceID=?").setString(0, rb.getRequestID()).executeUpdate();
				session.createQuery("update LtcReimbursementDTO set status="+status+" where requestId=?").setString(0, rb.getRequestID()).executeUpdate();
				if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCREIMBURSEMENT)){
					session.createQuery("update EmployeeClaimDetailsDTO set status=0, workFlowStatus="+status+" where requestID=? and requestType=?").setString(0, rb.getRequestID()).setString(1, "reimbursement").executeUpdate();
				}
				if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCSETTLEMENT)){
					session.createQuery("update EmployeeClaimDetailsDTO set status=0 , workFlowStatus="+status+" where requestID=? and requestType=?").setString(0, rb.getRequestID()).setString(1, "settlement").executeUpdate();
				}
			}else if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCAPPROVAL) || CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTC_APP_ADV)) {
				rb.setStatus(Integer.parseInt(status));
				rb = cancelLtcRequest(rb);
			}else if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCREFUND) || CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTC_CANCEL) || CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTC_APPR_CUM_ADV_CANCEL )|| CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCREFUNDVALUE)) {
				stageStatus=((BigDecimal)session.createSQLQuery("select get_last_stage_status((select reference_id from ltc_refund_request refund where refund.request_id=?)) stageStatus from dual").setString(0, rb.getRequestID()).uniqueResult()).intValue();
				workflowStatus=((BigDecimal)session.createSQLQuery("select get_last_workflow_status((select reference_id from ltc_refund_request refund where refund.request_id=?)) stageStatus from dual").setString(0, rb.getRequestID()).uniqueResult()).intValue();
				session.createSQLQuery("update request_workflow_history set remarks='This LTC Request is make it Active state due to LTC cancellation is Cancelled/Declained', status=?,stage_status=? where id=( select max(id) from request_workflow_history where request_id=(select reference_id from ltc_refund_request refund where refund.request_id=?))").setString(0, String.valueOf(workflowStatus)).setString(1, String.valueOf(stageStatus)).setString(2, rb.getRequestID()).executeUpdate();
				session.createQuery("update LtcMemberDetailsDTO set status=1 where requestId=(select referenceID from LtcRefundRequestDTO refund where refund.requestID=?)").setString(0, rb.getRequestID()).executeUpdate();
				session.createQuery("update LtcRefundRequestDTO set status="+status+" where requestID=?").setString(0, rb.getRequestID()).executeUpdate();
				if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCREFUND)|| CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTC_APPR_CUM_ADV_CANCEL ) || CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCREFUNDVALUE)) {
					session.createQuery("update LtcAdvanceRequestDTO set status=2 where requestId=(select referenceID from LtcRefundRequestDTO refund where refund.requestID=?)").setString(0, rb.getRequestID()).executeUpdate();
					session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=2 where requestID=(select referenceID from LtcRefundRequestDTO refund where refund.requestID=?)").setString(0, rb.getRequestID()).executeUpdate();
				}else {
					session.createQuery("update LtcApprovalRequestDTO set status=2 where requestId=(select referenceID from LtcRefundRequestDTO refund where refund.requestID=?)").setString(0, rb.getRequestID()).executeUpdate();
					session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=2 where requestID=(select referenceID from LtcRefundRequestDTO refund where refund.requestID=?)").setString(0, rb.getRequestID()).executeUpdate();
				}
			}else if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCAPPROVALAMENDMENT) || CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCADVANCEAMENDMENT)) {
				String previousId=null;
				String stageDescId=null;
				String presentStage=null;
				String workflowLastStage=null;
				String actionTakenStatus=null;
				String workflowId=null;
				LtcAdvanceRequestDTO ltcAdvDTO =null;
				LtcAdvanceRequestDTO ltcAdvDTO1 =null;
				
				session.createQuery("update LtcMemberDetailsDTO set status=? where request_id=?").setString(0, CPSConstants.STATUSCANCELLED).setString(1, rb.getRequestID()).executeUpdate();
				if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCAPPROVALAMENDMENT)) {
					ltcAdvDTO = getLeaveCancelValues(rb.getRequestID(),CPSConstants.LTCAPPROVAL); //added newly
					
					session.createQuery("update LtcApprovalRequestDTO set status=? where requestId=?").setString(0, CPSConstants.STATUSCANCELLED).setString(1, rb.getRequestID()).executeUpdate();
					session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=?,status=0 where requestID=?").setString(0, CPSConstants.STATUSCANCELLED).setString(1, rb.getRequestID()).executeUpdate();
					previousId= (String)session.createSQLQuery("select request_id||'#'||do_part_id id from ltc_request_details where request_id=( select amendment_ref_request_id id from ltc_request_details where request_id=?)").setString(0, rb.getRequestID()).uniqueResult();
					session.createSQLQuery("update ltc_request_details_bkp set status=case when (do_part_id=0 or do_part_id is null)  then ? else ? end where request_id=?").setString(0, CPSConstants.STATUSPENDING).setString(1, CPSConstants.STATUSSANCTIONED).setString(2, previousId.split("#")[0]).executeUpdate();
					session.createSQLQuery("update emp_claim_details set workflow_status=case when ((select do_part_id from ltc_request_details_bkp where request_id=?)=0 or (select do_part_id from ltc_request_details_bkp where request_id=?) is null)  then ? else ? end where request_id=?").setString(0, previousId.split("#")[0]).setString(1, previousId.split("#")[0]).setString(2, CPSConstants.STATUSPENDING).setString(3, CPSConstants.STATUSSANCTIONED).setString(4, previousId.split("#")[0]).executeUpdate();
					
					ltcAdvDTO1 = getLeaveCancelValues(previousId.split("#")[0],CPSConstants.LTCAPPROVAL); //added newly
				}else {
					ltcAdvDTO = getLeaveCancelValues(rb.getRequestID(),CPSConstants.LTCADVANCE); //added newly
					
					session.createQuery("update LtcAdvanceRequestDTO set status=? where requestId=?").setString(0, CPSConstants.STATUSCANCELLED).setString(1, rb.getRequestID()).executeUpdate();
					session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=? where requestID=?").setString(0, CPSConstants.STATUSCANCELLED).setString(1, rb.getRequestID()).executeUpdate();
					previousId= (String)session.createSQLQuery("select request_id||'#'||do_part_id id from ltc_advance_request_details where request_id=( select amendment_ref_request_id id from ltc_advance_request_details where request_id=?)").setString(0, rb.getRequestID()).uniqueResult();
					session.createSQLQuery("update ltc_advance_request_details_b set status=case when (do_part_id=0 or do_part_id is null)  then ? else ? end where request_id=?").setString(0, CPSConstants.STATUSPENDING).setString(1, CPSConstants.STATUSSANCTIONED).setString(2, previousId.split("#")[0]).executeUpdate();
					session.createSQLQuery("update emp_claim_details set workflow_status=case when ((select do_part_id from ltc_advance_request_details_b where request_id=?)=0 or (select do_part_id from ltc_advance_request_details_b where request_id=?) is null)  then ? else ? end where request_id=?").setString(0, previousId.split("#")[0]).setString(1, previousId.split("#")[0]).setString(2, CPSConstants.STATUSPENDING).setString(3, CPSConstants.STATUSSANCTIONED).setString(4, previousId.split("#")[0]).executeUpdate();
				
					ltcAdvDTO1 = getLeaveCancelValues(previousId.split("#")[0],CPSConstants.LTCADVANCE); //added newly
				}
				//Newly added start
				if(!CPSUtils.isNull(ltcAdvDTO)) {
					if(!CPSUtils.isNullOrEmpty(ltcAdvDTO.getEncashmentDays()) && Float.parseFloat(ltcAdvDTO.getEncashmentDays()) >0){
						updateLtcLeaveEncashDays(ltcAdvDTO.getSfID(),String.valueOf(ltcAdvDTO.getEncashTypeId()),ltcAdvDTO.getEncashmentDays(),"Due to LTC cancel/decline");
						session.createSQLQuery("update leave_emp_spells set ltc_encashment_days=(ltc_encashment_days-?) where sfid=? and leave_type_id=?").setFloat(0, Float.parseFloat(ltcAdvDTO.getEncashmentDays())).setString(1, ltcAdvDTO.getSfID()).setString(2, ltcAdvDTO.getEncashTypeId()).executeUpdate();
					}
				}
				//Newly added end
				presentStage=(String)session.createSQLQuery("select to_char(max(REQUEST_STAGE)) from REQUEST_WORKFLOW_HISTORY where REQUEST_ID=?").setString(0, previousId.split("#")[0]).uniqueResult();
				workflowId=(String)session.createSQLQuery("select distinct to_char(WORKFLOW_ID) from REQUEST_WORKFLOW_HISTORY where REQUEST_ID=?").setString(0, previousId.split("#")[0]).uniqueResult();
				stageDescId=(String)session.createSQLQuery("SELECT to_char(STAGE_DESC_ID) FROM WORKFLOW WHERE WORKFLOW_ID=? and STAGE_ID=?").setString(0,workflowId).setString(1,presentStage).uniqueResult();
				workflowLastStage=(String)session.createSQLQuery("select to_char(max(STAGE_ID)) from WORKFLOW where WORKFLOW_ID=?").setString(0, workflowId).uniqueResult();
				actionTakenStatus=String.valueOf(session.createSQLQuery("select CASE WHEN (SELECT max(ACTIONED_DATE) FROM REQUEST_WORKFLOW_HISTORY WHERE REQUEST_ID=? AND ACTIONED_DATE IS NOT NULL AND IP_ADDRESS IS NOT NULL AND REQUEST_STAGE=?)is null then 'N' else 'Y' end from dual").setString(0,previousId.split("#")[0]).setString(1, presentStage).uniqueResult());
				session.createSQLQuery("update request_workflow_history set remarks='This LTC Request is make it Active state due to LTC Amendment is Cancelled',status=(case when ?=0 then (case when ?='Y' then 4 else 2 end)  else(case when ?=? then (case when ?='Y' then 8 else 2 end) else  (case when ?='Y' then 4 else 2 end)  end) end),stage_status =(case when ?=0 then (case when ?='Y' then ? else 2 end)  else (case when ?=? then (case when ?='Y' then 8 else 2 end) else  (case when ?='Y' then ? else 2 end)  end) end) where request_stage=? and request_id=? and ip_address is not null").setInteger(0,Integer.valueOf(previousId.split("#")[1])).setString(1,actionTakenStatus).setInteger(2,Integer.valueOf(presentStage)).setInteger(3,Integer.valueOf(workflowLastStage)).setString(4,actionTakenStatus).setString(5,actionTakenStatus).setInteger(6,Integer.valueOf(previousId.split("#")[1])).setString(7,actionTakenStatus).setInteger(8, Integer.valueOf(stageDescId)).setInteger(9,Integer.valueOf(presentStage)).setInteger(10,Integer.valueOf(workflowLastStage)).setString(11,actionTakenStatus).setString(12,actionTakenStatus).setInteger(13, Integer.valueOf(stageDescId)).setInteger(14, Integer.valueOf(presentStage)).setInteger(15, Integer.valueOf(previousId.split("#")[0])).executeUpdate();
				session.createSQLQuery("update ltc_txn_details set status=1 where request_id=?").setString(0, previousId.split("#")[0]).executeUpdate();
			
				//Newly added start
				if(!CPSUtils.isNull(ltcAdvDTO1)) {
					if(!CPSUtils.isNullOrEmpty(ltcAdvDTO1.getEncashmentDays()) && Float.parseFloat(ltcAdvDTO1.getEncashmentDays()) >0){
						updateLtcLeaveEncashDays(ltcAdvDTO1.getSfID(),String.valueOf(ltcAdvDTO1.getEncashTypeId()),"-"+ltcAdvDTO1.getEncashmentDays(),"Due to LTC Amendment cancel/decline");
						session.createSQLQuery("update leave_emp_spells set ltc_encashment_days=(ltc_encashment_days-?) where sfid=? and leave_type_id=?").setFloat(0, Float.parseFloat(ltcAdvDTO1.getEncashmentDays())).setString(1, ltcAdvDTO1.getSfID()).setString(2, ltcAdvDTO1.getEncashTypeId()).executeUpdate();
					}
				}
				//Newly added end
		
			}else if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTCADVANCE)) {
				if(CPSUtils.compareStrings(String.valueOf(status), CPSConstants.STATUSCANCELLED) || CPSUtils.compareStrings(String.valueOf(status), CPSConstants.STATUSDECLINED) ){
					session.createQuery("update EmployeeClaimDetailsDTO set status=0,workFlowStatus="+status+" where requestID=? and requestTypeID=30)").setString(0, rb.getRequestID()).executeUpdate();
				}else{
					session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus="+status+" where requestID=? and requestTypeID=30)").setString(0, rb.getRequestID()).executeUpdate();
				}		
				session.createQuery("update LtcMemberDetailsDTO set status=? where request_id=?").setString(0, CPSConstants.STATUSCANCELLED).setString(1, rb.getRequestID()).executeUpdate();
				session.createQuery("update LtcAdvanceRequestDTO set status="+status+" where requestId=?)").setString(0, rb.getRequestID()).executeUpdate();
				session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus="+status+" where requestID=?)").setString(0, rb.getRequestID()).executeUpdate();
				session.createSQLQuery("update ltc_request_details_bkp set advance_flag='' where request_id=(select reference_request_id from ltc_advance_request_details where request_id=?)").setString(0, rb.getRequestID()).executeUpdate();
				session.createSQLQuery("update emp_claim_details set workflow_status=29,status=1 where request_id=(select reference_request_id from ltc_advance_request_details where request_id=?)").setString(0, rb.getRequestID()).executeUpdate();
				
			}
			rb.setMessage(CPSConstants.SUCCESS);
		}catch(Exception e){
			rb.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return rb;
	}
	public RequestBean cancelLtcRequest(RequestBean rb) throws Exception {
		Session session=null;
		KeyValueDTO keyValue = new KeyValueDTO();
		try {
			session=hibernateUtils.getSession();
			LtcAdvanceRequestDTO ltcAdvDTO = getLeaveCancelValues(rb.getRequestID(),rb.getRequestType());
			if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTC_APP_ADV) || CPSUtils.compareStrings(rb.getRequestType(), "ltcAdvance")){
				session.createQuery("update LtcAdvanceRequestDTO set status="+rb.getStatus()+" where requestId=?").setString(0, rb.getRequestID()).executeUpdate();
				session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus="+rb.getStatus()+" where requestID=?").setString(0, rb.getRequestID()).executeUpdate();
			}
			else {
				session.createQuery("update LtcApprovalRequestDTO set status="+rb.getStatus()+" where requestId=?").setString(0, rb.getRequestID()).executeUpdate();
				session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus="+rb.getStatus()+" where requestID=?").setString(0, rb.getRequestID()).executeUpdate();
			}
			
			session.createQuery("update LtcMemberDetailsDTO set status="+rb.getStatus()+" where requestId=?").setString(0, rb.getRequestID()).executeUpdate();
			
			if(!CPSUtils.isNull(ltcAdvDTO)) {
				keyValue.setValue(ltcAdvDTO.getId());
				keyValue.setKey(ltcAdvDTO.getLeaveRequestId());
				rb.setKeyValue(keyValue);
				if(!CPSUtils.isNullOrEmpty(ltcAdvDTO.getEncashmentDays()) && Float.parseFloat(ltcAdvDTO.getEncashmentDays()) >0){
					updateLtcLeaveEncashDays(ltcAdvDTO.getSfID(),String.valueOf(ltcAdvDTO.getEncashTypeId()),ltcAdvDTO.getEncashmentDays(),"Due to LTC cancel/decline");
					session.createSQLQuery("update leave_emp_spells set ltc_encashment_days=(ltc_encashment_days-?) where sfid=? and leave_type_id=?").setFloat(0, Float.parseFloat(ltcAdvDTO.getEncashmentDays())).setString(1, ltcAdvDTO.getSfID()).setString(2, ltcAdvDTO.getEncashTypeId()).executeUpdate();
				}
			}
			
			if(CPSUtils.compareStrings(rb.getRemarks(),CPSConstants.LTC_REQUEST_CANCEL_REASON)){
				session.createSQLQuery("update request_workflow_history set status=?,remarks=?,ip_address=?,stage_status=? where id=( select max(id) from request_workflow_history where request_id=?)").setString(0, CPSConstants.STATUSCANCELLED).setString(1, CPSConstants.LTC_REQUEST_CANCEL_REASON).setString(2, rb.getIpAddress()).setString(3, CPSConstants.STATUSCANCELLED).setString(4, rb.getRequestID()).executeUpdate();
			}
			rb.setMessage(CPSConstants.SUCCESS);
		}catch (Exception e) {
			throw e;
		}
		return rb;
	}
	@SuppressWarnings("unchecked")
	public String submitLtcAdvanceTxnDetails(LtcRequestProcessBean ltcrb) throws Exception {
		Session session = null;
		LtcAdvanceRequestDTO ltcAdvanceRequestDTO=null;
		List<LtcAdvanceRequestDTO> list=null;
		Criteria crit=null;
		try {
			ltcAdvanceRequestDTO = new LtcAdvanceRequestDTO();
			BeanUtils.copyProperties(ltcrb, ltcAdvanceRequestDTO);
			session = hibernateUtils.getSession();
			
			ltcAdvanceRequestDTO.setAppliedDate(CPSUtils.getCurrentDate());
			ltcAdvanceRequestDTO.setStatus(2);
			ltcAdvanceRequestDTO.setAmendmentRefRequestID(ltcrb.getId());
			session = hibernateUtils.getSession();
			EmployeeClaimDetailsDTO employeeClaimDetailsAdvance=new EmployeeClaimDetailsDTO();
			if(CPSUtils.isNullOrEmpty(ltcAdvanceRequestDTO.getAmountClaimed()) && CPSUtils.compareStrings(ltcAdvanceRequestDTO.getLtcRequestType(), CPSConstants.LTCADVANCEAMENDMENT)){
				EmployeeClaimDetailsDTO employeeReferenceClaimDetails =(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(ltcAdvanceRequestDTO.getReferenceRequestID()))).add(Expression.eq("requestType", CPSConstants.ADVANCE)).uniqueResult();
				EmployeeClaimDetailsDTO employeeEncashReferenceClaimDetails =(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(ltcAdvanceRequestDTO.getReferenceRequestID()))).add(Expression.eq("requestType", CPSConstants.ENCASHMENT)).uniqueResult();
				//get the previous request advance request details
				if(!CPSUtils.isNullOrEmpty(employeeReferenceClaimDetails)){
					EmployeeClaimDetailsDTO ecad=new EmployeeClaimDetailsDTO();
					BeanUtils.copyProperties(employeeReferenceClaimDetails, ecad);
					ecad.setAppliedBy(ltcrb.getSfID());
					ecad.setAppliedDate(CPSUtils.getCurrentDateWithTime());
					ecad.setStatus(2);
					ecad.setRefRequestId(Integer.valueOf(ltcrb.getId()));
					ecad.setRequestID(Integer.valueOf(ltcAdvanceRequestDTO.getRequestId()));
					session.save(ecad);
					FinanceDetailsDTO financeReferenceDetails=(FinanceDetailsDTO)session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID,employeeReferenceClaimDetails.getId())).uniqueResult();
						if(!CPSUtils.isNullOrEmpty(financeReferenceDetails)){
							FinanceDetailsDTO fad=new FinanceDetailsDTO();
							BeanUtils.copyProperties(financeReferenceDetails, fad);
							fad.setReferenceID(ecad.getId());
							fad.setCreatedBy(ltcrb.getSfID());
							fad.setCreationTime(CPSUtils.getCurrentDateWithTime());
							fad.setLastModifiedBy(ltcrb.getSfID());
							fad.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
							session.save(fad);
							CDADetailsDTO cdaReferenceDetails=(CDADetailsDTO)session.createCriteria(CDADetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID,financeReferenceDetails.getId())).uniqueResult();
							if(!CPSUtils.isNullOrEmpty(cdaReferenceDetails)){
								CDADetailsDTO cad=new CDADetailsDTO();
								BeanUtils.copyProperties(cdaReferenceDetails, cad);
								cad.setCreatedBy(ltcrb.getSfID());
								cad.setCreationTime(CPSUtils.getCurrentDateWithTime());
								cad.setLastModifiedBy(ltcrb.getSfID());
								cad.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
								cad.setReferenceID(fad.getId());
								session.save(cad);
							}
						}
				}
				//get the previous request encashment request details
				if(!CPSUtils.isNullOrEmpty(employeeEncashReferenceClaimDetails)){
					EmployeeClaimDetailsDTO eced=new EmployeeClaimDetailsDTO();
					BeanUtils.copyProperties(employeeEncashReferenceClaimDetails, eced);
					eced.setAppliedBy(ltcrb.getSfID());
					eced.setAppliedDate(CPSUtils.getCurrentDateWithTime());
					eced.setStatus(2);
					eced.setRefRequestId(Integer.valueOf(ltcrb.getId()));
					eced.setRequestID(Integer.valueOf(ltcAdvanceRequestDTO.getRequestId()));
					session.save(eced);
					FinanceDetailsDTO financeEncashmentReferenceDetails=(FinanceDetailsDTO)session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID,employeeEncashReferenceClaimDetails.getId())).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(financeEncashmentReferenceDetails)){
						FinanceDetailsDTO fed=new FinanceDetailsDTO();
						BeanUtils.copyProperties(financeEncashmentReferenceDetails, fed);
						fed.setReferenceID(eced.getId());
						fed.setCreatedBy(ltcrb.getSfID());
						fed.setCreationTime(CPSUtils.getCurrentDateWithTime());
						fed.setLastModifiedBy(ltcrb.getSfID());
						fed.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(fed);
							CDADetailsDTO cdaEncashmentReferenceDetails=(CDADetailsDTO)session.createCriteria(CDADetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID,financeEncashmentReferenceDetails.getId())).uniqueResult();
							if(!CPSUtils.isNullOrEmpty(cdaEncashmentReferenceDetails)){
								CDADetailsDTO ced=new CDADetailsDTO();
								BeanUtils.copyProperties(cdaEncashmentReferenceDetails, ced);
								ced.setCreatedBy(ltcrb.getSfID());
								ced.setCreationTime(CPSUtils.getCurrentDateWithTime());
								ced.setLastModifiedBy(ltcrb.getSfID());
								ced.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
								ced.setReferenceID(fed.getId());
								session.save(ced);
							}
						}
				}		
			}else{
				employeeClaimDetailsAdvance.setAmountClaimed(Float.valueOf(ltcAdvanceRequestDTO.getAmountClaimed()));
			employeeClaimDetailsAdvance.setRequestID(Integer.valueOf(ltcAdvanceRequestDTO.getRequestId()));
			employeeClaimDetailsAdvance.setRequestTypeID(Integer.valueOf(ltcrb.getRequestTypeID()));
			employeeClaimDetailsAdvance.setWorkFlowStatus(2);
			employeeClaimDetailsAdvance.setStatus(1);
			employeeClaimDetailsAdvance.setAppliedBy(ltcrb.getSfID());
			employeeClaimDetailsAdvance.setAppliedDate(CPSUtils.getCurrentDateWithTime());
			employeeClaimDetailsAdvance.setIpAddress(ltcAdvanceRequestDTO.getIpAddress());
			employeeClaimDetailsAdvance.setRequestType(CPSConstants.ADVANCE);
			session.save(employeeClaimDetailsAdvance);
			if(!CPSUtils.isNullOrEmpty(ltcAdvanceRequestDTO.getEncashmentDays())) {
				EmployeeClaimDetailsDTO employeeClaimDetailsDTO=new EmployeeClaimDetailsDTO();
				employeeClaimDetailsDTO.setAmountClaimed(Float.valueOf((String)session.createSQLQuery("select round(((epd.basic_pay+epd.grade_pay)+(epd.basic_pay+epd.grade_pay)*(select value from configuration_details where name='LTC_DA_PERCENTAGE')/100)*(?/30))||'' money from emp_payment_details epd where sfid=?").setString(0, ltcAdvanceRequestDTO.getEncashmentDays()).setString(1, ltcAdvanceRequestDTO.getSfID()).uniqueResult()));
				employeeClaimDetailsDTO.setRequestID(Integer.valueOf(ltcAdvanceRequestDTO.getRequestId()));
				employeeClaimDetailsDTO.setRequestTypeID(Integer.valueOf(ltcrb.getRequestTypeID()));
				employeeClaimDetailsDTO.setWorkFlowStatus(2);
				employeeClaimDetailsDTO.setStatus(1);
				employeeClaimDetailsDTO.setAppliedBy(ltcrb.getSfID());
				employeeClaimDetailsDTO.setAppliedDate(CPSUtils.getCurrentDateWithTime());
				employeeClaimDetailsDTO.setIpAddress(ltcAdvanceRequestDTO.getIpAddress());
				employeeClaimDetailsDTO.setRequestType(CPSConstants.ENCASHMENT);
				session.save(employeeClaimDetailsDTO);
			//	ltcAdvanceRequestDTO.setEncashmentAmount((String)session.createSQLQuery("select round(((epd.basic_pay+epd.grade_pay)+(epd.basic_pay+epd.grade_pay)*(select value from configuration_details where name='LTC_DA_PERCENTAGE')/100)*(?/30))||'' money from emp_payment_details epd where sfid=?").setString(0, ltcAdvanceRequestDTO.getEncashmentDays()).setString(1, ltcAdvanceRequestDTO.getSfID()).uniqueResult());
			}
			}
			session.save(ltcAdvanceRequestDTO);
			for (LtcMemberDetailsDTO ltcMember : ltcAdvanceRequestDTO.getLtcMemberDetails()) {
				ltcMember.setLtcType(ltcrb.getLtcTypeId());
				ltcMember.setBlockYear(ltcrb.getLtcBlockYearId());
				ltcMember.setStatus("1");
				ltcMember.setRequestType(CPSConstants.LTCADVANCE);
				session.save(ltcMember);
			}
			ltcrb.setMessage(CPSConstants.SUCCESS);
			/**
			 * Update encash leave days
			 */
			if(CPSUtils.compareStrings(ltcrb.getMessage(),CPSConstants.SUCCESS) && !CPSUtils.isNullOrEmpty(ltcrb.getEncashmentDays()) && Float.parseFloat(ltcrb.getEncashmentDays()) >0){
				log.debug("LtcRequestProcess >>>>>>>>>>>> User Encashing "+ltcrb.getEncashmentDays()+ " Leaves, Then call saveLeaveAdit() method");
				ltcrb.setMessage(updateLtcLeaveEncashDays(ltcrb.getSfID(),ltcrb.getEncashTypeId(),"-"+ltcrb.getEncashmentDays(),"LTC Leave Encashment"));
				session.createSQLQuery("update leave_emp_spells set ltc_encashment_days=(ltc_encashment_days+?) where sfid=? and leave_type_id=?").setFloat(0, Float.parseFloat(ltcrb.getEncashmentDays())).setString(1, ltcrb.getSfID()).setString(2, ltcrb.getEncashTypeId()).executeUpdate();
			}
			
		} catch (Exception e) {
			ltcrb.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return ltcrb.getMessage();
	}
	/**
	 * This method is used to check duplicate entry in LTC_REIMBURSEMENT_DETAILS for ltcReimbursement and ltcSettelement
	 * @param referenceID
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(String referenceID) throws Exception {
		log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>checkDuplicate(String referenceID)>>>>>");
		Session session=null;
		boolean status=true;
		try{
			session=hibernateUtils.getSession();
			if(!CPSUtils.isNull(session.createSQLQuery("select REFERENCE_ID from LTC_REIMBURSEMENT_DETAILS where REFERENCE_ID=? and status not in(?,?)").setString(0, referenceID).setString(1,CPSConstants.STATUSCANCELLED).setString(2,CPSConstants.STATUSDECLINED).uniqueResult())){
				status=false;
			}
		}catch (Exception e) {
			throw e;
		} 
		return status;
	}
	public LtcAdvanceRequestDTO getLeaveCancelValues(String requestID,String requestType) throws Exception {
		Session session=null;
		LtcAdvanceRequestDTO ltcAdvDto=null;
		try {
//GATTU			session=hibernateUtils.openNewSession();
			
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(requestType, CPSConstants.LTCAPPROVAL) || CPSUtils.compareStrings(requestType, "ltcApproval")){
				ltcAdvDto = (LtcAdvanceRequestDTO)session.createSQLQuery("select lrd.sfid sfID,lrd.encash_leave_type_id encashTypeId,lrd.encashment_days encashmentDays,lrd.leave_request_id as leaveRequestId,(select max(id) from request_workflow_history where request_id=lrd.leave_request_id) as id from ltc_request_details lrd where lrd.request_id=? and status not in(6,9)").addScalar("sfID").addScalar("encashTypeId", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("leaveRequestId", Hibernate.STRING).addScalar("id", Hibernate.STRING).setString(0, requestID).setResultTransformer(Transformers.aliasToBean(LtcAdvanceRequestDTO.class)).uniqueResult();
			}else {
				ltcAdvDto = (LtcAdvanceRequestDTO)session.createSQLQuery("select lrd.sfid sfID,lrd.encash_leave_type_id encashTypeId,lrd.encashment_days encashmentDays,lrd.leave_request_id as leaveRequestId,(select max(id) from request_workflow_history where request_id=lrd.leave_request_id) as id from ltc_advance_request_details lrd where lrd.request_id=? and status not in(6,9)").addScalar("sfID").addScalar("encashTypeId", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("leaveRequestId", Hibernate.STRING).addScalar("id", Hibernate.STRING).setString(0, requestID).setResultTransformer(Transformers.aliasToBean(LtcAdvanceRequestDTO.class)).uniqueResult();
			}
			session.flush();
		}catch (Exception e) {
			throw e;
		}finally {
//GATTU			//session.close();
		}
		return ltcAdvDto;
	}
}
