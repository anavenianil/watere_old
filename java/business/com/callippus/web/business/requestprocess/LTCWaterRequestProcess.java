package com.callippus.web.business.requestprocess;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.StatusMasterDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.dto.ErpAvailableLeaveSaveDTO;
import com.callippus.web.ltc.beans.request.LTCWaterRequestBean;
import com.callippus.web.ltc.dto.LTCWaterFamilyDTO;
import com.callippus.web.ltc.dto.LTCWaterRequestDTO;
import com.callippus.web.ltc.dto.LTCWaterRequestProcessBean;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;
import com.callippus.web.tada.dto.TadaTdLeaveDetailsDTO;
import com.callippus.web.tada.dto.TadaTdTxnDTO;


@Service
public class LTCWaterRequestProcess  extends TxRequestProcess {
	
	
	private static Log log = LogFactory.getLog(LTCWaterRequestProcess.class);
	
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
public String initWorkflow(LTCWaterRequestProcessBean ltcWRPB) throws Exception {
		
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			ltcWRPB.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			ltcWRPB.setRequestId(ltcWRPB.getRequestID());
			if(CPSUtils.compareStrings(CPSConstants.LTC_WATERAPPROVAL, ltcWRPB.getType())) {
				
				ltcWRPB.setRequestTypeID("89"); 
				ltcWRPB.setRequestType(CPSConstants.LTC_WATER);
				ltcWRPB = setTadaMemberDetails(ltcWRPB);
				message=submitTxnDetails(ltcWRPB);
				
			}
			
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(rb,ltcWRPB);
				txRequestProcess.initWorkflow(rb);
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return ltcWRPB.getMessage();
		
	}


public LTCWaterRequestProcessBean setTadaMemberDetails(LTCWaterRequestProcessBean ltcWRPB) throws Exception {
	Session session=null;
	try {
		session=hibernateUtils.getSession();
		LTCWaterRequestBean empBean = (LTCWaterRequestBean) ltcWRPB.getSession().getAttribute(CPSConstants.EMPLOYEEDETAILS);
		String workingLocation=(String)session.createQuery("select workingLocation from OuterEmployeeBean where userSfid=?").setString(0, ltcWRPB.getSfid().toUpperCase()).uniqueResult();
		ltcWRPB.setDesignation(empBean.getDesignation());
		ltcWRPB.setPhnNo(empBean.getPhnNo());
		ltcWRPB.setDesignationID(String.valueOf(empBean.getEmpDetailsList().getDesignationDetails().getId()));
		ltcWRPB.setOrganizationID(workingLocation);
	} catch (Exception e) {
		throw e;
	}
	return ltcWRPB;
}
	

@SuppressWarnings("unchecked")
public String submitTxnDetails(LTCWaterRequestProcessBean ltcWRPB) throws Exception {
	
	Session session = null;
	LTCWaterRequestDTO ltcWaterRequestDTO = null;
	@SuppressWarnings("unused")
	TadaTdTxnDTO tadaTdTxnDTO=null;
	List<LTCWaterRequestDTO> list=null;
	List<LTCWaterRequestDTO> list1=null;
	String movementOrderNo=null;
	String maxDate=null;
	String minDate=null;
	Criteria crit=null;
	
	try {
		ltcWaterRequestDTO = new LTCWaterRequestDTO();
		BeanUtils.copyProperties(ltcWaterRequestDTO,ltcWRPB);
		ltcWaterRequestDTO.setCreationDate(CPSUtils.getCurrentDate());
		ltcWaterRequestDTO.setApplyDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
		ltcWaterRequestDTO.setDesigId(ltcWRPB.getEmpDetailsList().getDesignation());
		ltcWaterRequestDTO.setPhnNo(ltcWRPB.getEmpDetailsList().getPersonalNumber());
		ltcWaterRequestDTO.setDesignation(ltcWRPB.getEmpDetailsList().getDesignation());
		ltcWaterRequestDTO.setDeptId(ltcWRPB.getEmpDetailsList().getDirectorate());
		ltcWaterRequestDTO.setStageStatus(1);
		ltcWaterRequestDTO.setStatus(1);
		session = hibernateUtils.getSession();
		session.createCriteria(LTCWaterRequestDTO.class).add(Expression.eq("sfID", ltcWRPB.getSfID()));
		session.save(ltcWaterRequestDTO);
		session.flush();
		ltcWRPB.setMessage(CPSConstants.SUCCESS);
		
	} catch (Exception e) {
		ltcWRPB.setMessage(CPSConstants.FAILED);
		throw e;
	}finally{
		session.clear();
	}
	
	return ltcWRPB.getMessage();
	
}



@SuppressWarnings("unchecked")
public WorkFlowMappingBean getLTCRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
Session session = null;
String sql =null;
String qry = null;
String qry1 = null;
LTCWaterRequestDTO ltcWaterRequestDTO=null;
TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
TadaApprovalRequestDTO tadaApprovalRequestDTO1=null;
TadaTdAdvanceRequestDTO tadaAdvanceRequestDTO=null;
TadaTdAdvanceRequestDTO tadaAdvanceRequestDTO1=null;
EmployeeClaimDetailsDTO empClaimDetailsDTO=null;
EmployeeClaimDetailsDTO empClaimDetailsDTO1=null;
WorkFlowMappingBean workflowMap1=null;
List<TadaTdLeaveDetailsDTO> tdLeaveList=null;
List<LeaveRequestBean> leaveDetails=new ArrayList<LeaveRequestBean>();

try {

	session = hibernateUtils.getSession();//session = sessionFactory.openSession();
	String requestId=workflowMap.getRequestId();
	sql="select ltc.REQUEST_ID AS requestId,ltc.DESIGNATION_ID AS designation,ltc.DEPARTMENT_ID AS deptName,ltc.PHONE_NUMBER AS phnNo,ltc.LTC_TYPE AS typeOfLtc,ltc.LTC_YEAR_ID AS ltcYear,ltc.HOMETOWNADDR AS hometownAddr,ltc.STARTHOLIDAY AS startHoliday,ltc.RETURNHOLIDAY AS returnHoliday,ltc.NO_OF_DAYS AS nod,ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,ltc.NOOF_CHILDREN_TICKETS AS noOfChildrenTickets,ltc.LEAVE_TYPE AS leaveType,ltc.APPLIED_DATE AS applyDate,ltc.TOTAL_TICKETS AS totalTickets,ltc.IPADDRESS AS ipAddress  FROM LTC_WATER_REQUEST_DETAILS ltc WHERE ltc.REQUEST_ID="+requestId+"";
	ltcWaterRequestDTO=(LTCWaterRequestDTO) session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("deptName", Hibernate.STRING).addScalar("phnNo", Hibernate.STRING).addScalar("typeOfLtc", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("startHoliday", Hibernate.DATE).addScalar("returnHoliday", Hibernate.DATE).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets",Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("totalTickets",Hibernate.INTEGER).addScalar("ipAddress",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
	workflowMap.setLtcWaterRequestDTO(ltcWaterRequestDTO);
	if(!CPSUtils.isNullOrEmpty(tadaAdvanceRequestDTO)){
		qry="select max(rwh.id) as historyID,rwh.status as status,tttd.request_type as requestType from request_workflow_history rwh," +
				"tada_td_adv_request_details ttard,tada_td_txn_details tttd " +
				"where rwh.request_id=? and ttard.request_id=? and tttd.request_id=? and ttard.status not in (9,6) and rwh.id=(select max(id) from request_workflow_history where " +
				"request_id=?) group by rwh.status, tttd.request_type";
		tadaAdvanceRequestDTO1=(TadaTdAdvanceRequestDTO)session.createSQLQuery(qry).addScalar("historyID",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).setString(0, tadaAdvanceRequestDTO.getRequestId())
		.setString(1, tadaAdvanceRequestDTO.getRequestId()).setString(2, tadaAdvanceRequestDTO.getRequestId()).setString(3, tadaAdvanceRequestDTO.getRequestId()).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
		tadaAdvanceRequestDTO1.setRequestId(tadaAdvanceRequestDTO.getRequestId());
		StatusMasterDTO statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", tadaAdvanceRequestDTO1.getStatus())).uniqueResult();
		tadaAdvanceRequestDTO1.setStrStatus(statusMasterDTO.getName());
		workflowMap.setTadaTdAdvanceRequestDTO(tadaAdvanceRequestDTO1);
	}
	empClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(requestId))).add(Expression.eq("requestTypeID", 48)).add(Expression.and(Expression.ne("workFlowStatus", 9), Expression.ne("workFlowStatus", 6))).uniqueResult();
	if(!CPSUtils.isNullOrEmpty(empClaimDetailsDTO)){
		qry1="select max(rwh.id) as tdSettlementHistoryID,rwh.request_id as tdSettlementReqId,ecd.workflow_status as tdSettStatus " +
				"from request_workflow_history rwh,emp_claim_details ecd where rwh.request_id=ecd.request_id and ecd.ref_request_id=? and ecd.request_type_id=48 and ecd.workflow_status not in (9,6) " +
				"group by rwh.request_id, ecd.workflow_status ";
		workflowMap1 = (WorkFlowMappingBean)session.createSQLQuery(qry1).addScalar("tdSettlementHistoryID", Hibernate.STRING).addScalar("tdSettlementReqId", Hibernate.STRING)
		.addScalar("tdSettStatus", Hibernate.INTEGER).setInteger(0, Integer.parseInt(requestId)).setResultTransformer(Transformers.aliasToBean(WorkFlowMappingBean.class)).uniqueResult();
		StatusMasterDTO statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", workflowMap1.getTdSettStatus())).uniqueResult();
		workflowMap.setTdSettlementHistoryID(workflowMap1.getTdSettlementHistoryID());
		workflowMap.setTdSettlementReqId(workflowMap1.getTdSettlementReqId());
		workflowMap.setTdSettStrStatus(statusMasterDTO.getName());
	}
	empClaimDetailsDTO1=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(requestId))).add(Expression.eq("requestTypeID", 49)).add(Expression.and(Expression.ne("workFlowStatus", 9), Expression.ne("workFlowStatus", 6))).uniqueResult();
	if(!CPSUtils.isNullOrEmpty(empClaimDetailsDTO1)){
		qry1="select max(rwh.id) as tdReimHistoryID,rwh.request_id as tdReimReqId,ecd.workflow_status as tdReimStatus " +
				"from request_workflow_history rwh,emp_claim_details ecd where rwh.request_id=ecd.request_id and ecd.ref_request_id=? and ecd.request_type_id=49 and ecd.workflow_status not in (9,6) " +
				"group by rwh.request_id, ecd.workflow_status ";
		workflowMap1 = (WorkFlowMappingBean)session.createSQLQuery(qry1).addScalar("tdReimHistoryID", Hibernate.STRING).addScalar("tdReimReqId", Hibernate.STRING)
		.addScalar("tdReimStatus", Hibernate.INTEGER).setInteger(0, Integer.parseInt(requestId)).setResultTransformer(Transformers.aliasToBean(WorkFlowMappingBean.class)).uniqueResult();
		StatusMasterDTO statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", workflowMap1.getTdReimStatus())).uniqueResult();
		workflowMap.setTdReimHistoryID(workflowMap1.getTdReimHistoryID());
		workflowMap.setTdReimReqId(workflowMap1.getTdReimReqId());
		workflowMap.setTdReimStrStatus(statusMasterDTO.getName());
	}
	tdLeaveList = session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(requestId))).list();
	if(tdLeaveList.size()!=0){
		for (TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO : tdLeaveList) {
			LeaveRequestBean leaveRequestBean=new LeaveRequestBean();
			leaveRequestBean=(LeaveRequestBean)session.createCriteria(LeaveRequestBean.class).add(Expression.eq("requestID", String.valueOf(tadaTdLeaveDetailsDTO.getLeaveRequestId()))).add(Expression.ne("status", 9)).uniqueResult();
			leaveDetails.add(leaveRequestBean);
		}
		workflowMap.setTdLeaveDetailsList(leaveDetails);
	}
} catch (Exception e) {
	throw e;
} finally {
	session.flush();
}
return workflowMap;
}


//added 24/05/2016 end of the day
public String approvedRequest(LTCWaterRequestProcessBean processBean) throws Exception {
	RequestBean rb = null;
	Session session=null,session1=null,session2=null;
	try {
		session=hibernateUtils.getSession();
		rb = new RequestBean();
		if(CPSUtils.compareStrings(processBean.getRequestTypeID(), "89") )
		{
			processBean.setHistoryID(processBean.getHistoryID().split(",")[0]);
		}else{
			processBean.setHistoryID(processBean.getHistoryID().split(",")[0]);
		}
		//BeanUtils.copyProperties(processBean, rb);
		BeanUtils.copyProperties(rb, processBean);
		
		rb = txRequestProcess.approvedRequest(rb);
		if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
			log.debug("::request workflow completed, so update in the main table");
			
			session.createQuery("update LTCWaterRequestDTO set status='2' , stageStatus=2 where requestId=? ")
			.setString(0, rb.getRequestId()).executeUpdate();
			
			//added by bkr 20/09/2016
			
			LTCWaterRequestDTO ltcWaterRequestDTO =new LTCWaterRequestDTO();
		String	sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
					+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
					+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
					+ "  ltc.NO_OF_DAYS AS nod,  "
					+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
					+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
					+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt,ltc.AMOUNT_ADULTS AS amountAdults,ltc.AMOUNT_CHILDREN AS amountChildren,ltc.TOTAL_ADULTS_AMT AS adultsTotAmt,ltc.TOTAL_CHILDREN_AMT AS childrenTotAmt,ltc.STARTHOLIDAY AS startHoliday,ltc.RETURNHOLIDAY AS returnHoliday  "
					+ " FROM LTC_WATER_REQUEST_DETAILS ltc "
					+ "WHERE ltc.REQUEST_ID="+rb.getRequestId()+"";
					
			ltcWaterRequestDTO=(LTCWaterRequestDTO)	session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("ipAddress", Hibernate.STRING).addScalar("totalTicketsAmt", Hibernate.INTEGER).addScalar("amountAdults", Hibernate.INTEGER).addScalar("amountChildren", Hibernate.INTEGER).addScalar("adultsTotAmt", Hibernate.INTEGER).addScalar("childrenTotAmt", Hibernate.INTEGER).addScalar("startHoliday", Hibernate.DATE).addScalar("returnHoliday", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			ErpAvailableLeaveSaveDTO erpAvailableLeaveSaveDTO = new ErpAvailableLeaveSaveDTO() ;
			erpAvailableLeaveSaveDTO.setApplyDate(erpAvailableLeaveSaveDTO.getApplyDate());
			erpAvailableLeaveSaveDTO.setLeaveType("AL");;
			erpAvailableLeaveSaveDTO.setFromDate(ltcWaterRequestDTO.getStartHoliday());
			erpAvailableLeaveSaveDTO.setToDate(ltcWaterRequestDTO.getReturnHoliday());
			erpAvailableLeaveSaveDTO.setDesignation(ltcWaterRequestDTO.getDesignation());
			erpAvailableLeaveSaveDTO.setReason("Going To Home Town");
			int nOOfDays=ltcWaterRequestDTO.getNod();
			String NoOfDays=Integer.toString(nOOfDays);
			erpAvailableLeaveSaveDTO.setNoOfDays(NoOfDays);
			erpAvailableLeaveSaveDTO.setRequestId(rb.getRequestId());
			erpAvailableLeaveSaveDTO.setSfID(ltcWaterRequestDTO.getSfID());
			erpAvailableLeaveSaveDTO.setStatus(2);
			erpAvailableLeaveSaveDTO.setLeaveStatus(2);
			session1=hibernateUtils.getSession();
			session1.save(erpAvailableLeaveSaveDTO);
			session1.flush();
			////
			session2=hibernateUtils.getSession();
			session2.createQuery(
					"update ErpEmpLeavesDTO set noOfDays=noOfDays-? where sfID=? and leaveCode='AL' ")
					.setString(0, erpAvailableLeaveSaveDTO.getNoOfDays())
					.setString(1, ltcWaterRequestDTO.getSfID())
					.executeUpdate();
			
//end			
			
			BeanUtils.copyProperties(rb, processBean);
			if(CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDBUILDUP) || CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDPROJECT) || CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDADVANCES)){
			//	processBean.setApprovedStage("2");
			//	tadaDomainObject.updateTxnDetails(processBean);
        
			} else if(CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDSETTLEMENTS)){
				JSONObject mainJson = new JSONObject(processBean.getJsonValue());
				JSONObject accJson=(JSONObject)mainJson.get("accDetails");
				JSONObject mroPaymentJson=(JSONObject)mainJson.get("mroPaymentDetails");
				if(Float.parseFloat(processBean.getExcessAmount())<0 && !CPSUtils.compareStrings("", processBean.getExcessAmountFine())){
				//	tadaDomainObject.updateTxnDetails(processBean);
					if(accJson.length()>0){
					
					if(Integer.parseInt(processBean.getExcessAmount())<0){
					//rb.setMessage(tadaDomainObject.submitMroPaymentDetails(processBean));
					}
				} 
					
					
					if(Integer.parseInt(processBean.getExcessAmount())<0 && accJson.length()==0 && mroPaymentJson.length()>0  ){
				//		rb.setMessage(tadaDomainObject.submitMroPaymentDetails(processBean));
						
					}else {
					
				}
					}else{
					if(accJson.length()>0){
					}
					//rb.setMessage(tadaDomainObject.updateTxnDetails(processBean));
				}	
			} else if(CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDREIMBURSEMENT)){
			//	tadaDomainObject.updateTxnDetails(processBean);
				JSONObject mainJson = new JSONObject(processBean.getJsonValue());
				JSONObject accJson=(JSONObject)mainJson.get("accDetails");
				if(accJson.length()>0){
			//		tadaDomainObject.submitTdNewDaAccDetails(processBean);
				}
			}
		} else if(CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage()) && !CPSUtils.isNullOrEmpty(processBean.getProjectType())){
			TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", processBean.getRequestId())).uniqueResult();
			tadaApprovalRequestDTO.setProjectName(processBean.getProjectType());
			
			session.saveOrUpdate(tadaApprovalRequestDTO);
			session.flush();
		} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, processBean.getRequestType())){
		//	processBean.setApprovedStage("1");
		//	tadaDomainObject.updateTxnDetails(processBean);
		} else if((CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, processBean.getRequestType()) || (CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, processBean.getRequestType()))) && CPSUtils.compareStrings(rb.getParentID(), "SF0794") ){
		
			
		//	processBean.setApprovedStage("1");
		//	tadaDomainObject.updateTxnDetails(processBean);
		//	tadaDomainObject.submitMroDetails(processBean);
		}
	} catch (Exception e) {
		throw e;
	}
	return rb.getMessage();
}

@SuppressWarnings("unchecked")
public List<LTCWaterRequestDTO> getFinAdvDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
	List<LTCWaterRequestDTO> finAdvList=null;
	Session session=null;
	String sql=null;
	try{
		session=hibernateUtils.getSession();
		
		
		
		sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
				+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
				+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
				+ "  ltc.NO_OF_DAYS AS nod,  "
				+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
				+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
				+ " ltc.IPADDRESS AS ipAddress "
				+ " FROM LTC_WATER_REQUEST_DETAILS ltc where STATUS=2 "
				+ " and ADVANCE_FLAG is null ORDER BY ltc.REQUEST_ID desc";
				
		
		finAdvList =session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).list();
	}catch(Exception e){
		throw e;
	}
	return finAdvList;
}

@SuppressWarnings("unchecked")
public List<LTCWaterRequestDTO> getFinAdvCompletedDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
	List<LTCWaterRequestDTO> finAdvList=null;
	Session session=null;
	String sql=null;
	String sfid=ltcWaterRequestBean.getSfid();
	if(sfid==null){
		sfid = ltcWaterRequestBean.getSfID();
		ltcWaterRequestBean.setSfid(sfid);
	}
	try{
		session=hibernateUtils.getSession();
		
		sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
				+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
				+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
				+ "  ltc.NO_OF_DAYS AS nod,  "
				+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
				+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
				+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt "
				+ " FROM LTC_WATER_REQUEST_DETAILS ltc where STATUS=2 "
				+ " and ADVANCE_FLAG is not null and ltc.SEL_OR_REIM is null  and SFID='"+sfid+"'  ORDER BY ltc.REQUEST_ID desc";
		
		
		finAdvList =session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("totalTicketsAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).list();

	
	}catch(Exception e){
		throw e;
	}
	return finAdvList;
}


public String ltcWaterSettlementApply(
		LTCWaterRequestProcessBean ltcWaterRequestProcessBean) throws Exception{
	Session session=null,session2=null,session3=null;
	try {
		session=hibernateUtils.getSession();
		if(ltcWaterRequestProcessBean.getLtcactualExpenditure()>0){
			session.createQuery("update LTCWaterRequestDTO set ltcsettleOrReim=?,ltcactualExpenditure=?,ltcsettleOrReimAmt=?,ltcselOrReimRemarks=?,ltcsettleOrReimApplyDate=?,stageStatus=4 where requestId=?").setString(0, ltcWaterRequestProcessBean.getLtcsettleOrReim()).setInteger(1,  ltcWaterRequestProcessBean.getLtcactualExpenditure()).setInteger(2, ltcWaterRequestProcessBean.getLtcsettleOrReimAmt()).setString(3, ltcWaterRequestProcessBean.getLtcselOrReimRemarks()).setDate(4,  ltcWaterRequestProcessBean.getLtcsettleOrReimApplyDate()).setString(5, ltcWaterRequestProcessBean.getRequestId()).executeUpdate();
		}else{
			session.createQuery("update LTCWaterRequestDTO set ltcsettleOrReim=?,ltcactualExpenditure=?,ltcsettleOrReimAmt=?,ltcselOrReimRemarks=?,ltcsettleOrReimApplyDate=?,stageStatus=4 ,status=0 where requestId=?").setString(0, ltcWaterRequestProcessBean.getLtcsettleOrReim()).setInteger(1,  ltcWaterRequestProcessBean.getLtcactualExpenditure()).setInteger(2, ltcWaterRequestProcessBean.getLtcsettleOrReimAmt()).setString(3, ltcWaterRequestProcessBean.getLtcselOrReimRemarks()).setDate(4,  ltcWaterRequestProcessBean.getLtcsettleOrReimApplyDate()).setString(5, ltcWaterRequestProcessBean.getRequestId()).executeUpdate();
			
			//21/09/2016
			LTCWaterRequestDTO ltcWaterRequestDTO =new LTCWaterRequestDTO();
			String	sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
						+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
						+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
						+ "  ltc.NO_OF_DAYS AS nod,  "
						+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
						+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
						+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt,ltc.AMOUNT_ADULTS AS amountAdults,ltc.AMOUNT_CHILDREN AS amountChildren,ltc.TOTAL_ADULTS_AMT AS adultsTotAmt,ltc.TOTAL_CHILDREN_AMT AS childrenTotAmt,ltc.STARTHOLIDAY AS startHoliday,ltc.RETURNHOLIDAY AS returnHoliday  "
						+ " FROM LTC_WATER_REQUEST_DETAILS ltc "
						+ "WHERE ltc.REQUEST_ID="+ltcWaterRequestProcessBean.getRequestId()+"";
						
				ltcWaterRequestDTO=(LTCWaterRequestDTO)	session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("ipAddress", Hibernate.STRING).addScalar("totalTicketsAmt", Hibernate.INTEGER).addScalar("amountAdults", Hibernate.INTEGER).addScalar("amountChildren", Hibernate.INTEGER).addScalar("adultsTotAmt", Hibernate.INTEGER).addScalar("childrenTotAmt", Hibernate.INTEGER).addScalar("startHoliday", Hibernate.DATE).addScalar("returnHoliday", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
				int nOOfDays=ltcWaterRequestDTO.getNod();
				String NoOfDays=Integer.toString(nOOfDays);
				System.out.println(NoOfDays);
				session2=hibernateUtils.getSession();
				session2.createQuery(
						"update ErpEmpLeavesDTO set noOfDays=noOfDays+? where sfID=? and leaveCode='AL' ")
						.setString(0, NoOfDays)
						.setString(1, ltcWaterRequestDTO.getSfID())
						.executeUpdate();
				session3=hibernateUtils.getSession();
				session3.createQuery("update ErpAvailableLeaveSaveDTO set leaveStatus='0',status='0' where requestId='"+ltcWaterRequestDTO.getRequestId()+"' ").executeUpdate();
			
		}
			
	} catch (Exception e) {
			throw e;
	}
	
	return "success";
}


@SuppressWarnings("unchecked")
public List<LTCWaterRequestDTO> getLTCWaterSettlementDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
	List<LTCWaterRequestDTO> finTadaSettlementList=null;
	Session session=null;
	String sql=null;
	try{
		session=hibernateUtils.getSession();
		
		sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
				+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
				+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
				+ "  ltc.NO_OF_DAYS AS nod,  "
				+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
				+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
				+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt,ltc.SETTLEORREIM_AMT AS ltcsettleOrReimAmt "
				+ " FROM LTC_WATER_REQUEST_DETAILS ltc  "
				+ " where STAGE_STATUS=4 and SEL_OR_REIM='Settlement' and SETTELMENT_ADMIN_DATE is null ORDER BY ltc.REQUEST_ID desc ";
		
		
		
		finTadaSettlementList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("typeOfLtc", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("ipAddress",Hibernate.STRING).addScalar("totalTicketsAmt", Hibernate.INTEGER).addScalar("ltcsettleOrReimAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).list();
		
		
	}catch(Exception e){
		throw e;
	}
	return finTadaSettlementList;
}


@SuppressWarnings("unchecked")
public List<LTCWaterRequestDTO> getLTCWaterReimbursementDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception{
	List<LTCWaterRequestDTO> finTadaReimbursementList=null;
	Session session=null;
	String sql=null;
	try{
		session=hibernateUtils.getSession();
		
		sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
				+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
				+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
				+ "  ltc.NO_OF_DAYS AS nod,  "
				+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
				+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
				+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt,ltc.SETTLEORREIM_AMT AS ltcsettleOrReimAmt "
				+ " FROM LTC_WATER_REQUEST_DETAILS ltc  "
				+ " where STATUS=2 and SEL_OR_REIM='Reimbursement'  and REIM_ADMIN_DATE is null   ORDER BY ltc.REQUEST_ID desc ";
		
		
		
		finTadaReimbursementList =session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("typeOfLtc", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("ipAddress",Hibernate.STRING).addScalar("totalTicketsAmt", Hibernate.INTEGER).addScalar("ltcsettleOrReimAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).list();
		
		
	}catch(Exception e){
		throw e;
	}
	return finTadaReimbursementList;
}


public String ltcWaterAdminSettlement(
		LTCWaterRequestProcessBean ltcWaterRequestProcessBean) throws Exception{
	Session session=null;
	try {
		
		session=hibernateUtils.getSession();
			session.createQuery("update LTCWaterRequestDTO set settlementAdminDate=?,settlementAdminRemarks=?,stageStatus=5 where requestId=?").setDate(0, ltcWaterRequestProcessBean.getSettlementAdminDate()).setString(1,  ltcWaterRequestProcessBean.getSettlementAdminRemarks()).setString(2, ltcWaterRequestProcessBean.getRequestId()).executeUpdate();
			
	} catch (Exception e) {
			throw e;
	}
	
	return "success";
}


public String ltcWaterAdminReimbursement(
		LTCWaterRequestProcessBean ltcWaterRequestProcessBean) throws Exception{
	Session session=null;
	try {
		
		session=hibernateUtils.getSession();
		
		
		session.createQuery("update LTCWaterRequestDTO set reimbursementAdminRemarks=?,reimbursementAdminDate=?,ltcreimcashorcheck=?,ltcreimBankName=?,ltcreimBranchName=?,ltcreimChequeNo=?,ltcreimAdminDvno=?,ltcreimAdminDvDate=?,stageStatus=6 where requestId=?").setString(0, ltcWaterRequestProcessBean.getReimbursementAdminRemarks()).setDate(1,  ltcWaterRequestProcessBean.getReimbursementAdminDate()).setString(2, ltcWaterRequestProcessBean.getLtcreimcashorcheck()).setString(3, ltcWaterRequestProcessBean.getLtcreimBankName()).setString(4, ltcWaterRequestProcessBean.getLtcreimBranchName()).setString(5, ltcWaterRequestProcessBean.getLtcreimChequeNo()).setString(6, ltcWaterRequestProcessBean.getLtcreimAdminDvno()).setDate(7, ltcWaterRequestProcessBean.getLtcreimAdminDvDate()).setString(8, ltcWaterRequestProcessBean.getRequestId()).executeUpdate();
		
	} catch (Exception e) {
			throw e;
	}
	
	return "success";
}



public LTCWaterRequestProcessBean saveltcdetails(LTCWaterRequestProcessBean ltcBean) throws Exception{ 
	try {
		JSONObject mainJson = new JSONObject(ltcBean.getJsonValue());
		for(int i=0;i<mainJson.length();i++) {
			String value = (String) mainJson.get(String.valueOf(i));
			submitLtcFamilyTxnDetails(ltcBean,value);
			
		}
	}catch (Exception e) {
		throw e;
	}
	return ltcBean;
}

@SuppressWarnings("unchecked")
public String submitLtcFamilyTxnDetails(LTCWaterRequestProcessBean ltcWRPB,String value) throws Exception {
	
	Session session = null;
	LTCWaterFamilyDTO ltcWaterFamilyDTO = null;
	try {
		
		ltcWaterFamilyDTO = new LTCWaterFamilyDTO();
		BeanUtils.copyProperties(ltcWaterFamilyDTO,ltcWRPB);
		ltcWaterFamilyDTO.setFamilyMemberId(value);
		ltcWaterFamilyDTO.setSfID(ltcWRPB.getSfID());
		ltcWaterFamilyDTO.setLtcYear(ltcWRPB.getLtcYear());
		ltcWaterFamilyDTO.setRequestId(ltcWRPB.getRequestId());
		session = hibernateUtils.getSession();
		session.createCriteria(LTCWaterFamilyDTO.class);
		session.save(ltcWaterFamilyDTO);
		session.flush();
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		session.clear();
	}
	return ltcWRPB.getMessage();
	
}


/*public void updateErpLTCRequestDetails(String requestID) {
	// TODO Auto-generated method stub
	
}*/
public String updateErpLTCRequestDetails(String requestID) throws Exception {
	
	try {
		Session session = null;
		session = hibernateUtils.getSession(); 
		
		session.createQuery("update LTCWaterRequestDTO set status=0 , stageStatus=0 where  requestId=?  ").setString(0, requestID).executeUpdate();
		
	} catch (Exception e) {
	throw e;
	}
	return requestID;
	
}


}
