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
import com.callippus.web.business.domainobject.TadaDomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.business.tx.workflow.TxWorkflowProcess;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.tada.beans.request.TadaWaterRequestBean;
import com.callippus.web.tada.dao.approval.ITadaTdApprovalRequestDAO;
import com.callippus.web.tada.dto.BankNamesDTO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;
import com.callippus.web.tada.dto.TadaTdLeaveDetailsDTO;
import com.callippus.web.tada.dto.TadaTdTxnDTO;
import com.callippus.web.tada.dto.TadaWaterApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaWaterRequestProcessBean;


@Service
public class TadaWaterRequestProcess  extends TxRequestProcess {
	
	private static Log log = LogFactory.getLog(TadaWaterRequestProcess.class);
	
	@Autowired
	private TxRequestProcess txRequestProcess;
	
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	@Autowired
	private TadaDomainObject tadaDomainObject;

	@Autowired
	private ITadaTdApprovalRequestDAO tadaTdApprovalRequestDAO;
	@Autowired
	private TxWorkflowProcess txWorkflowProcess;

	
	
	public String initWorkflow(TadaWaterRequestProcessBean tadaWRPB) throws Exception {
		
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			tadaWRPB.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			tadaWRPB.setRequestId(tadaWRPB.getRequestID());
			if(CPSUtils.compareStrings(CPSConstants.TADA_WATERAPPROVAL, tadaWRPB.getType())) {
				
				tadaWRPB.setRequestTypeID("99");
				tadaWRPB.setRequestType(CPSConstants.TADA_WATER);
				tadaWRPB = setTadaMemberDetails(tadaWRPB);
				message=submitTxnDetails(tadaWRPB);
				
			}
			
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(rb,tadaWRPB);
				txRequestProcess.initWorkflow(rb);
			}
			
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		
		
		return tadaWRPB.getMessage();
		
	}
	
	
	
	public TadaWaterRequestProcessBean setTadaMemberDetails(TadaWaterRequestProcessBean tadaWRPB) throws Exception {
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			TadaWaterRequestBean empBean = (TadaWaterRequestBean) tadaWRPB.getSession().getAttribute(CPSConstants.EMPLOYEEDETAILS);
			String workingLocation=(String)session.createQuery("select workingLocation from OuterEmployeeBean where userSfid=?").setString(0, tadaWRPB.getSfid().toUpperCase()).uniqueResult();
			tadaWRPB.setDesignation(empBean.getDesignation());
			tadaWRPB.setPhnNo(empBean.getPhnNo());
			tadaWRPB.setDesignationID(String.valueOf(empBean.getEmpDetailsList().getDesignationDetails().getId()));
			tadaWRPB.setOrganizationID(workingLocation);
		} catch (Exception e) {
			throw e;
		}
		return tadaWRPB;
	}

	
	
	@SuppressWarnings("unchecked")
	public String submitTxnDetails(TadaWaterRequestProcessBean tadaWRPB) throws Exception {
		
		Session session = null;
		TadaWaterApprovalRequestDTO tadaWaterApprDTO = null;
		@SuppressWarnings("unused")
		TadaTdTxnDTO tadaTdTxnDTO=null;
		List<TadaApprovalRequestDTO> list=null;
		List<TadaApprovalRequestDTO> list1=null;
		String movementOrderNo=null;
		String maxDate=null;
		String minDate=null;
		Criteria crit=null;
		
		
		
		try {
			
			tadaWaterApprDTO = new TadaWaterApprovalRequestDTO();
			BeanUtils.copyProperties(tadaWaterApprDTO,tadaWRPB);
			tadaWaterApprDTO.setCreationDate(CPSUtils.getCurrentDate());
			tadaWaterApprDTO.setApplyDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
			tadaWaterApprDTO.setDesigId(tadaWRPB.getEmpDetailsList().getDesignation());
			tadaWaterApprDTO.setPhnNo(tadaWRPB.getEmpDetailsList().getPersonalNumber());
			tadaWaterApprDTO.setDesignation(tadaWRPB.getEmpDetailsList().getDesignation());
		//	tadaWaterApprDTO.setDeptName(tadaWRPB.getEmpDetailsList().getDirectorate());
			
			tadaWaterApprDTO.setDeptId(tadaWRPB.getEmpDetailsList().getDirectorate());
			
			tadaWaterApprDTO.setStatus("1");
			tadaWaterApprDTO.setStageStatus(1);
			//tadaWaterApprDTO.setStageStatus1("1");
			session = hibernateUtils.getSession();
			session.createCriteria(TadaWaterApprovalRequestDTO.class).add(Expression.eq("sfID", tadaWRPB.getSfID()));
		//	session.createCriteria(TadaWaterApprovalRequestDTO.class);
			session.save(tadaWaterApprDTO);
			session.flush();
			tadaWRPB.setMessage(CPSConstants.SUCCESS);
			
		} catch (Exception e) {
			tadaWRPB.setMessage(CPSConstants.FAILED);
			throw e;
		}finally{
			session.clear();
		}
		
		return tadaWRPB.getMessage();
		
	}
	
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getTadaTdRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
	Session session = null;
	String sql =null;
	String qry = null;
	String qry1 = null;
	TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO=null;
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
		
		sql=" select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid, tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, tdrd.TRAVELLING_TO AS travellingTo,FROM_DATE AS fromDate,TO_DATE AS toDate, tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt,tdrd.STATUS AS status   FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd WHERE tdrd.REQUEST_ID="+requestId+"";
		
		
		tadaWaterApprovalRequestDTO=(TadaWaterApprovalRequestDTO) session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptId", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("status", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaWaterApprovalRequestDTO.class)).uniqueResult();
		
		
		workflowMap.setTadaWaterApprovalRequestDTO(tadaWaterApprovalRequestDTO);
		
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
	
	public String approvedRequest(TadaWaterRequestProcessBean processBean) throws Exception {
		RequestBean rb = null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			rb = new RequestBean();
			if(CPSUtils.compareStrings(processBean.getRequestTypeID(), "99") )
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
				
				//added by bkr 11/07/2016 
				session.createQuery("update TadaWaterApprovalRequestDTO set status='2' , stageStatus=2 where requestId=? ")
				.setString(0, rb.getRequestId()).executeUpdate();
				
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
	public List<TadaWaterApprovalRequestDTO> getFinAdvDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		List<TadaWaterApprovalRequestDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			//commented by bkr and added by bkr 11/07/2016 
		//	sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_NAME AS deptName,tdrd.SFID AS sfid, tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, tdrd.TRAVELLING_TO AS travellingTo, tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt,tdrd.STATUS AS status   FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd where REQUEST_ID IN (select REQUEST_ID from ess.REQUEST_WORKFLOW_HISTORY where STAGE_STATUS=105) and ADVANCE_FLAG is null ORDER BY tdrd.REQUEST_ID desc";
			sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid, "
					+ " tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose,  "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  "
					+ " tdrd.TAXI_AMT AS taxiAmt, tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  "
					+ " tdrd.NOOF_DAYS  AS noOfDays,  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, "
					+ " tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt,tdrd.STATUS AS status  "
					+ "  FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd where STATUS='2' and ADVANCE_FLAG is null ORDER BY tdrd.REQUEST_ID desc";
			finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptId", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("status", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaWaterApprovalRequestDTO.class)).list();
			tadaWaterRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		return finAdvList;
	}



	public String tadaWaterSettlementApply(
			TadaWaterRequestProcessBean tadaWaterRequestProcessBean) throws Exception{
		Session session=null;
		try {
			
			session=hibernateUtils.getSession();
			if(tadaWaterRequestProcessBean.getActualExpenditure()>0){
				session.createQuery("update TadaWaterApprovalRequestDTO set settleOrReim=?,actualExpenditure=?,settleOrReimAmt=?,selOrReimRemarks=?,settleOrReimApplyDate=?,stageStatus=4 where requestId=?").setString(0, tadaWaterRequestProcessBean.getSettleOrReim()).setInteger(1,  tadaWaterRequestProcessBean.getActualExpenditure()).setInteger(2, tadaWaterRequestProcessBean.getSettleOrReimAmt()).setString(3, tadaWaterRequestProcessBean.getSelOrReimRemarks()).setDate(4,  tadaWaterRequestProcessBean.getSettleOrReimApplyDate()).setString(5, tadaWaterRequestProcessBean.getRequestId()).executeUpdate();
			}else{
				
				session.createQuery("update TadaWaterApprovalRequestDTO set settleOrReim=?,actualExpenditure=?,settleOrReimAmt=?,selOrReimRemarks=?,settleOrReimApplyDate=?,stageStatus=4 ,status='0' where requestId=?").setString(0, tadaWaterRequestProcessBean.getSettleOrReim()).setInteger(1,  tadaWaterRequestProcessBean.getActualExpenditure()).setInteger(2, tadaWaterRequestProcessBean.getSettleOrReimAmt()).setString(3, tadaWaterRequestProcessBean.getSelOrReimRemarks()).setDate(4,  tadaWaterRequestProcessBean.getSettleOrReimApplyDate()).setString(5, tadaWaterRequestProcessBean.getRequestId()).executeUpdate();
				
			}
		} catch (Exception e) {
				throw e;
		}
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public List<TadaWaterApprovalRequestDTO> getFinAdvCompletedDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		List<TadaWaterApprovalRequestDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		String sfid=tadaWaterRequestBean.getSfid();
		if(sfid==null){
			sfid = tadaWaterRequestBean.getSfID();
			tadaWaterRequestBean.setSfid(sfid);
		}
		try{
			session=hibernateUtils.getSession();
			
			/*sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_NAME AS deptName, tdrd.SFID AS sfid, "
					+ " tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose,  "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,   "
					+ "tdrd.TAXI_AMT AS taxiAmt, tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  "
					+ " tdrd.NOOF_DAYS  AS noOfDays,  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, "
					+ " tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt,tdrd.STATUS AS status   "
					+ " FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd  "
					+ "where REQUEST_ID IN (select REQUEST_ID from ess.REQUEST_WORKFLOW_HISTORY where STAGE_STATUS=105) and ADVANCE_FLAG='COMP' and SETTLEORREIM is null and SFID='"+sfid+"' ORDER BY tdrd.REQUEST_ID desc";
		*/
			sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId, tdrd.SFID AS sfid, "
					+ " tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose,  "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,   "
					+ "tdrd.TAXI_AMT AS taxiAmt, tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  "
					+ " tdrd.NOOF_DAYS  AS noOfDays,  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, "
					+ " tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt,tdrd.STATUS AS status   "
					+ " FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd  "
					+ "where STATUS='2' and ADVANCE_FLAG='COMP' and SETTLEORREIM is null and SFID='"+sfid+"' ORDER BY tdrd.REQUEST_ID desc";
		
			
			finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptId", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("status", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaWaterApprovalRequestDTO.class)).list();
			
			
			tadaWaterRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		return finAdvList;
	}
	

	public String updateTadaAdvance(
			TadaWaterRequestProcessBean tadaWaterRequestProcessBean) throws Exception{
		Session session=null;
		try {
			
			session=hibernateUtils.getSession();
			
				//  session.createQuery("update TadaWaterApprovalRequestDTO set foodandAccmAmt=?,daAmt=?,taxiAmt=?,transitAmt=?,totalAmt=?,noOfDays=?,perDayFoodandAccmAmt=?,dvno=?,cashorcheck=?,dvDate=?,advance='COMP',advBankName=?,advBranchName=?,advChequeNo=?,advDvno=?,advDvDate=? where requestId=?").setInteger(0, tadaWaterRequestProcessBean.getFoodandAccmAmt()).setInteger(1,  tadaWaterRequestProcessBean.getDaAmt()).setInteger(2, tadaWaterRequestProcessBean.getTaxiAmt()).setInteger(3, tadaWaterRequestProcessBean.getTransitAmt()).setInteger(4,  tadaWaterRequestProcessBean.getTotalAmt()).setString(5, tadaWaterRequestProcessBean.getNoOfDays()).setInteger(6, tadaWaterRequestProcessBean.getPerDayFoodandAccmAmt()).setString(7, tadaWaterRequestProcessBean.getDvno()).setString(8,  tadaWaterRequestProcessBean.getCashorcheck()).setDate(9, tadaWaterRequestProcessBean.getDvDate()).setString(10, tadaWaterRequestProcessBean.getAdvBankName()).setString(11, tadaWaterRequestProcessBean.getAdvBranchName()).setString(12, tadaWaterRequestProcessBean.getAdvChequeNo()).setString(13, tadaWaterRequestProcessBean.getAdvDvno()).setDate(14, tadaWaterRequestProcessBean.getAdvDvDate()).setString(15, tadaWaterRequestProcessBean.getRequestId()).executeUpdate();
			
			  session.createQuery("update TadaWaterApprovalRequestDTO set foodandAccmAmt=?,daAmt=?,taxiAmt=?,transitAmt=?,totalAmt=?,noOfDays=?,perDayFoodandAccmAmt=?,dvno=?,cashorcheck=?,dvDate=?,advance='COMP',advBankName=?,advBranchName=?,advChequeNo=?,advDvno=?,advDvDate=?,stageStatus=3 where requestId=?").setInteger(0, tadaWaterRequestProcessBean.getFoodandAccmAmt()).setInteger(1,  tadaWaterRequestProcessBean.getDaAmt()).setInteger(2, tadaWaterRequestProcessBean.getTaxiAmt()).setInteger(3, tadaWaterRequestProcessBean.getTransitAmt()).setInteger(4,  tadaWaterRequestProcessBean.getTotalAmt()).setString(5, tadaWaterRequestProcessBean.getNoOfDays()).setInteger(6, tadaWaterRequestProcessBean.getPerDayFoodandAccmAmt()).setString(7, tadaWaterRequestProcessBean.getDvno()).setString(8,  tadaWaterRequestProcessBean.getCashorcheck()).setDate(9, tadaWaterRequestProcessBean.getDvDate()).setString(10, tadaWaterRequestProcessBean.getAdvBankName()).setString(11, tadaWaterRequestProcessBean.getAdvBranchName()).setString(12, tadaWaterRequestProcessBean.getAdvChequeNo()).setString(13, tadaWaterRequestProcessBean.getAdvDvno()).setDate(14, tadaWaterRequestProcessBean.getAdvDvDate()).setString(15, tadaWaterRequestProcessBean.getRequestId()).executeUpdate();
				
		} catch (Exception e) {
				throw e;
		}
		
		return "success";
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<TadaWaterApprovalRequestDTO> getTadaWaterSettlementDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		List<TadaWaterApprovalRequestDTO> finTadaSettlementList=null;
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			
			/*sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_NAME AS deptName,tdrd.SFID AS sfid,      "
					+ " tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, "
					+ " tdrd.CLAIM_PURPOSE AS claimPurpose, tdrd.TRAVELLING_TO AS travellingTo, "
					+ " tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt,  "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays, "
					+ "  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, "
					+ "tdrd.STATUS AS status,tdrd.ACTUAL_EXPENDITURE AS actualExpenditure ,tdrd.SETTLEORREIM_AMT AS settleOrReimAmt     "
					+ "  FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd  "
					+ "where REQUEST_ID IN (select REQUEST_ID from ess.REQUEST_WORKFLOW_HISTORY where STAGE_STATUS=105) and SETTLEORREIM='Settlement' and SETTELMENT_DATE is null ORDER BY tdrd.REQUEST_ID desc";
		*/
			
			sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid,      "
					+ " tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, "
					+ " tdrd.CLAIM_PURPOSE AS claimPurpose, tdrd.TRAVELLING_TO AS travellingTo, "
					+ " tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt,  "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays, "
					+ "  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, "
					+ "tdrd.STATUS AS status,tdrd.ACTUAL_EXPENDITURE AS actualExpenditure ,tdrd.SETTLEORREIM_AMT AS settleOrReimAmt     "
					+ "  FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd  "
					+ "where STAGE_STATUS=4 and SETTLEORREIM='Settlement' and SETTELMENT_DATE is null ORDER BY tdrd.REQUEST_ID desc";
		
			
			finTadaSettlementList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptId", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("status", Hibernate.STRING).addScalar("actualExpenditure", Hibernate.INTEGER).addScalar("settleOrReimAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaWaterApprovalRequestDTO.class)).list();
			
			
			tadaWaterRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		return finTadaSettlementList;
	}
	

	@SuppressWarnings("unchecked")
	public List<TadaWaterApprovalRequestDTO> getTadaWaterReimbursementDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		List<TadaWaterApprovalRequestDTO> finTadaReimbursementList=null;
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid,      "
					+ " tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, "
					+ " tdrd.CLAIM_PURPOSE AS claimPurpose, tdrd.TRAVELLING_TO AS travellingTo, "
					+ " tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt,  "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays, "
					+ "  tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, "
					+ "tdrd.STATUS AS status ,tdrd.ACTUAL_EXPENDITURE AS actualExpenditure ,tdrd.SETTLEORREIM_AMT AS settleOrReimAmt      "
					+ "  FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd  "
					+ "where STATUS='2' and SETTLEORREIM='Reimbursement' and REIMBURSEMENT_DATE is null  ORDER BY tdrd.REQUEST_ID desc";
		
			
			
			finTadaReimbursementList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptId", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("status", Hibernate.STRING).addScalar("actualExpenditure", Hibernate.INTEGER).addScalar("settleOrReimAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaWaterApprovalRequestDTO.class)).list();
			
			
			tadaWaterRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		return finTadaReimbursementList;
	}
	
	

	public String tadaWaterAdminSettlement(
			TadaWaterRequestProcessBean tadaWaterRequestProcessBean) throws Exception{
		Session session=null;
		try {
			
			session=hibernateUtils.getSession();
				session.createQuery("update TadaWaterApprovalRequestDTO set waterSettlementDate=?,settlementRemarks=?,stageStatus=5 where requestId=?").setDate(0, tadaWaterRequestProcessBean.getWaterSettlementDate()).setString(1,  tadaWaterRequestProcessBean.getSettlementRemarks()).setString(2, tadaWaterRequestProcessBean.getRequestId()).executeUpdate();
				
		} catch (Exception e) {
				throw e;
		}
		
		return "success";
	}
	
	public String tadaWaterAdminReimbursement(
			TadaWaterRequestProcessBean tadaWaterRequestProcessBean) throws Exception{
		Session session=null;
		try {
			
			session=hibernateUtils.getSession();
			//	session.createQuery("update TadaWaterApprovalRequestDTO set reimcashorcheck=?,reimDvDate=?,reimDvno=?,waterReimbursementDate=?,reimbursementRemarks=? where requestId=?").setString(0, tadaWaterRequestProcessBean.getReimcashorcheck()).setDate(1,  tadaWaterRequestProcessBean.getReimDvDate()).setString(2, tadaWaterRequestProcessBean.getReimDvno()).setDate(3,  tadaWaterRequestProcessBean.getWaterReimbursementDate()).setString(4, tadaWaterRequestProcessBean.getReimbursementRemarks()).setString(5, tadaWaterRequestProcessBean.getRequestId()).executeUpdate();
			
			session.createQuery("update TadaWaterApprovalRequestDTO set reimcashorcheck=?,reimDvDate=?,reimDvno=?,waterReimbursementDate=?,reimbursementRemarks=?,reimBankName=?,reimBranchName=?,reimChequeNo=?,reimAdminDvno=?,reimAdminDvDate=?,stageStatus=6 where requestId=?").setString(0, tadaWaterRequestProcessBean.getReimcashorcheck()).setDate(1,  tadaWaterRequestProcessBean.getReimDvDate()).setString(2, tadaWaterRequestProcessBean.getReimDvno()).setDate(3,  tadaWaterRequestProcessBean.getWaterReimbursementDate()).setString(4, tadaWaterRequestProcessBean.getReimbursementRemarks()).setString(5, tadaWaterRequestProcessBean.getReimBankName()).setString(6, tadaWaterRequestProcessBean.getReimBranchName()).setString(7, tadaWaterRequestProcessBean.getReimChequeNo()).setString(8, tadaWaterRequestProcessBean.getReimAdminDvno()).setDate(9, tadaWaterRequestProcessBean.getReimAdminDvDate()).setString(10, tadaWaterRequestProcessBean.getRequestId()).executeUpdate();
				
		} catch (Exception e) {
				throw e;
		}
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public List<BankNamesDTO> getBankNamesList(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		List<TadaWaterApprovalRequestDTO> finAdvList=null;
		List<BankNamesDTO>  banklist=null;
		Session session=null;
		String sql=null;
		String sfid=tadaWaterRequestBean.getSfid();
		if(sfid==null){
			sfid = tadaWaterRequestBean.getSfID();
			tadaWaterRequestBean.setSfid(sfid);
		}
		try{
			session=hibernateUtils.getSession();
			
			sql="select BANK_NAME AS bankName,ID AS bankid FROM BANK_MASTER";
				
			
			banklist = session.createSQLQuery(sql).addScalar("bankName", Hibernate.STRING).addScalar("bankid", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(BankNamesDTO.class)).list();
			
			
		}catch(Exception e){
			throw e;
		}
		return banklist;
	}



	/*public void updateErpTADARequestDetails(String requestID) {
		// TODO Auto-generated method stub
		
	}*/
public String updateErpTADARequestDetails(String requestID) throws Exception {
		
		try {
			Session session = null;
			session = hibernateUtils.getSession();
			
			session.createQuery("update TadaWaterApprovalRequestDTO set status='0' , stageStatus=0 where  requestId=?  ").setString(0, requestID).executeUpdate();
			
		} catch (Exception e) {
		throw e;
		}
		return requestID;
		
	}
	

}



