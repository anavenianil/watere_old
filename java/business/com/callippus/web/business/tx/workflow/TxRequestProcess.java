package com.callippus.web.business.tx.workflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.common.MailBean;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.RequestWorkDTO;
import com.callippus.web.beans.dto.StatusMasterDTO;
import com.callippus.web.beans.dto.WorkFlowMasterDTO;
import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.requests.RequestCommonBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.mailImpl.MailImplementaion;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class TxRequestProcess {
	private static Log log = LogFactory.getLog(TxRequestProcess.class);
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private TxGenericWorkflowProcess txGenericWorkflowProcess;
	@Autowired
	private TxInternalWorkflowProcess txInternalWorkflowProcess;
	@Autowired
	private TxWorkflowProcess txWorkflowProcess;
	@Autowired
	private TxUserLevelWorkflowProcess txUserLevelWorkflowProcess;
	@Autowired
	private TxRoleBasedWorkflowProcess txRoleBeasedWorkflowProcess;
	@Autowired
	private TxDesigBasedWorkflowProcess txDesigBeasedWorkflowProcess;
	@Autowired
	private TxOrgSpecificWorkflowProcess txOrgSpecificWorkflowProcess;

	@Autowired
	 private MailImplementaion mailSender;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	/**
	 * This method will generate the unique id for a key
	 * 
	 * @return request id
	 * @throws Exception
	 */
	public String generateUniqueID(final String tableName) throws Exception {
		//log.debug("::<<<<<TxRequestProcess<<<<<<Method>>>>>>>>>>>>>>>generateUniqueID(String tableName)>>>>>>>>>");
		Session session = null;
		String idValue = null;
		try {
			log.debug("::Table Name>>>>>>" + tableName);
			session = hibernateUtils.getSession();

			
			idValue = (String) session.createSQLQuery("select value||'' from id_generator where upper(table_name)=? FOR UPDATE").setString(0, tableName.toUpperCase()).uniqueResult();

			//idValue = (String) session.createSQLQuery("select value||'' from id_generator where upper(table_name)=? FOR UPDATE").setString(0, tableName.toUpperCase()).uniqueResult();

			if (!CPSUtils.isNullOrEmpty(idValue)) {
				int id = Integer.valueOf(idValue) + 1;
				session.createSQLQuery("update id_generator set value=? where upper(table_name)=?").setInteger(0, id).setString(1, tableName.toUpperCase()).executeUpdate();
				idValue = String.valueOf(id);
				session.flush();
			} else {
				log.debug("id_generator table doesn't have " + tableName);
			}
			
		} catch (Exception e) {
			throw e;
		}
		log.debug("::Generated ID > > " + idValue);
		return idValue;
	}

	/**
	 * This method will return the request bean that contains request type id & internal flag
	 * 
	 * @param request
	 *            bean
	 * @return request bean
	 * @throws Exception
	 */
	public RequestBean getRequestTypeDetails(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<<<Method>>>>>>>>>>>>>>>getRequestTypeDetails(RequestBean rb)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			RequestWorkDTO requestWorkDTO = (RequestWorkDTO) session.createCriteria(RequestWorkDTO.class).add(Expression.eq(CPSConstants.NAME, rb.getRequestType()).ignoreCase()).add(
					Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			if (!CPSUtils.isNull(requestWorkDTO)) {
				rb.setRequestTypeID(String.valueOf(requestWorkDTO.getId()));
				if (CPSUtils.compareStrings(requestWorkDTO.getInternalFlag(), "1")) {
					log.debug("::Internal Workflow Exists for " + rb.getRequestType());
					rb.setRequestInternalFlag(true);
				} else {
					log.debug("::No Internal Workflow for " + rb.getRequestType());
					rb.setRequestInternalFlag(false);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	public WorkflowTxnDTO txnRowDetails(final String reqTypeID, final String reqID, final String refNo, final String colName, final String ipAddress, final String newValue, final String prevValue,
			final String rowNo, final String desc) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<<<Method>>>>>>>>>>>>>>>txnRowDetails()>>>>>>>>>");
		WorkflowTxnDTO workTxnDTO = null;
		try {
			workTxnDTO = new WorkflowTxnDTO();
			workTxnDTO.setTxnID(generateUniqueID(CPSConstants.WORKFLOW_TXN_DETAILS));
			workTxnDTO.setRequestTypeID(reqTypeID);
			workTxnDTO.setRequestID(reqID);
			workTxnDTO.setReferenceNumber(refNo);
			workTxnDTO.setColumnName(colName);
			workTxnDTO.setIpAddress(ipAddress);
			workTxnDTO.setNewValue(newValue);
			workTxnDTO.setPreviousValue(prevValue);
			workTxnDTO.setRowNumber(rowNo);
			workTxnDTO.setDescription(desc);
		} catch (Exception e) {
			throw e;
		}
		return workTxnDTO;
	}

	/**
	 * This method will insert the txn details
	 * 
	 * @param txnDetails
	 * @return
	 * @throws Exception
	 */
	public String submitTxnDetails(ArrayList<WorkflowTxnDTO> txnDetails) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<<<Method>>>>>>>>>>>>>>>submitTxnDetails(RequestBean rb)>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			if (!CPSUtils.isNull(txnDetails)) {
				for (int i = 0; i < txnDetails.size(); i++) {
					WorkflowTxnDTO workflowTxnDTO = txnDetails.get(i);
					if (!CPSUtils.isNullOrEmpty(workflowTxnDTO.getPreviousValue()) || !CPSUtils.isNullOrEmpty(workflowTxnDTO.getNewValue())) {
						session.saveOrUpdate(workflowTxnDTO);
						session.flush();
					}
				}
				message = CPSConstants.SUCCESS;
			}else{
				message = CPSConstants.FAILED;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	/**
	 * This method will be called at the time of requesting. In this method we will decide the workflow for the current request & forwards that request to the next level (inserting details into
	 * workflow history table) And if the assigned user configured (instance user) auto delegate it will automatically forwards that request
	 * 
	 * @param request
	 *            bean
	 * @return success/failure messages
	 */
	public String initWorkflow(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(RequestBean rb)>>>>>>>>>");
		try {

			/**
			 * get the request type id & internal workflow flag
			 */
			rb = getRequestTypeDetails(rb);

			if (rb.isRequestInternalFlag()) {
				/**
				 * request should follow the internal division hierarchy structure so get the employee parent id
				 */
				log.debug("::Request should follow the internal division hierarchy");
				rb = txInternalWorkflowProcess.getInternalDivisionWorkFlow(rb);
				if (CPSUtils.isNullOrEmpty(rb.getParentID())) {
					/**
					 * If the parent ID is null mean boss is the requester or his immediate subordinates are the requesters, so there is no concept of internal workflow
					 */
					log.debug("::Internal workflow not exists for this employee, so follow the generic workflow");
					rb = decideWorkflow(rb);
				}
			} else {
				/**
				 * request should not follow the internal division structure so check the workflow from the generic workflows for this request
				 */
				log.debug("::No internal workflow for this request, so follow the generic workflow");
				rb = decideWorkflow(rb);
			}
			log.debug("::Before assiging Request");
			rb.setMessage(assignRequest(rb));
			
			sendMail(rb);
			
			
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	
	public void sendMail(RequestBean rb)throws Exception{
		if(rb.getAppliedBy()==null)
			rb.setAppliedBy(rb.getRequester());
		if(rb.getMessage().equals(CPSConstants.SUCCESS)){
			MailBean bean = new MailBean();
			MailBean mailBean = new MailBean();
			mailBean.setRequestedBy(hibernateUtils.getSession().createSQLQuery("select name_in_service_book from emp_master where sfid=?").setString(0, rb.getAppliedBy()).uniqueResult().toString());
			mailBean.setRequestId(rb.getRequestID());
			mailBean.setRequestType(rb.getRequestType());
			mailBean.setToMailAddress(rb.getParentID().toLowerCase()+"@asl.net");
			mailBean.setSendingToName(hibernateUtils.getSession().createSQLQuery("select name_in_service_book from emp_master  where sfid=? ").setString(0, rb.getParentID()).uniqueResult().toString());
			mailBean.setDescription(rb.getDescription());
			if( commonDataDAO.getConfigurationValue("MAIL_SENDING").equals("TRUE")){
				log.debug("::Before sending mail");
				mailSender.sendMail(mailBean);
				log.debug("::After sending mail");
			}
			MailBean mailBeanR = new MailBean();
			mailBeanR.setToMailAddress(rb.getAppliedBy().toLowerCase()+"@asl.net");
			mailBeanR.setRequestId(rb.getRequestID());
			mailBeanR.setRequestType(rb.getRequestType());
			mailBeanR.setSendingToName(hibernateUtils.getSession().createSQLQuery("select name_in_service_book from emp_master where sfid=?").setString(0, rb.getAppliedBy()).uniqueResult().toString());
			mailBeanR.setCurrentPosition(hibernateUtils.getSession().createSQLQuery("select name_in_service_book||'( '||dm.name||') ' from emp_master emp,designation_master dm  where sfid=? and dm.status=1 and emp.status=1 and emp.designation_id=dm.id").setString(0, rb.getParentID()).uniqueResult().toString());
			mailBeanR.setDescription(rb.getDescription());
			if( commonDataDAO.getConfigurationValue("MAIL_SENDING").equals("TRUE")){
				log.debug("::Before sending mail");
				mailSender.sendMailToRequester(mailBeanR);
				log.debug("::After sending mail");
			}
		}
		else if(rb.getMessage().equals(CPSConstants.UPDATE)){
			MailBean mailBeanR = new MailBean();
			mailBeanR.setToMailAddress(rb.getAppliedBy().toLowerCase()+"@asl.net");
			mailBeanR.setRequestId(rb.getRequestID());
			mailBeanR.setRequestType(rb.getRequestType());
			mailBeanR.setSendingToName(hibernateUtils.getSession().createSQLQuery("select name_in_service_book from emp_master where sfid=?").setString(0, rb.getAppliedBy()).uniqueResult().toString());
			//mailBeanR.setCurrentPosition(hibernateUtils.getSession().createSQLQuery("select name_in_service_book from emp_master where sfid=?").setString(0, rb.getParentID()).uniqueResult().toString());
			mailBeanR.setDescription(rb.getDescription());
			mailBeanR.setStatus("success");
			if( commonDataDAO.getConfigurationValue("MAIL_SENDING").equals("TRUE")){
				log.debug("::Before sending mail");
				mailSender.sendMailToRequester(mailBeanR);
				log.debug("::After sending mail");
			}
		}
		else if(rb.getMessage().equals(CPSConstants.DECLINED)){
			MailBean mailBeanR = new MailBean();
			mailBeanR.setToMailAddress(rb.getAppliedBy().toLowerCase()+"@asl.net");
			mailBeanR.setRequestId(rb.getRequestID());
			mailBeanR.setRequestType(rb.getRequestType());
			mailBeanR.setSendingToName(hibernateUtils.getSession().createSQLQuery("select name_in_service_book from emp_master where sfid=?").setString(0, rb.getAppliedBy()).uniqueResult().toString());
			//mailBeanR.setCurrentPosition(hibernateUtils.getSession().createSQLQuery("select name_in_service_book from emp_master where sfid=?").setString(0, rb.getParentID()).uniqueResult().toString());
			mailBeanR.setDescription(rb.getDescription());
			mailBeanR.setStatus("declined");
			if( commonDataDAO.getConfigurationValue("MAIL_SENDING").equals("TRUE")){
				log.debug("::Before sending mail");
				mailSender.sendMailToRequester(mailBeanR);
				log.debug("::After sending mail");
			}
		} 
		
	}

	public RequestBean decideWorkflow(RequestBean rb) throws Exception {
		try {
			//check the organization specific workflow
			rb = txOrgSpecificWorkflowProcess.initWorkflow(rb);
			if(CPSUtils.isNull(rb.getWorkflowID())){
				// organization specific workflow not exist so follow the role based workflow
				rb = txRoleBeasedWorkflowProcess.initWorkflow(rb);
			}
			if(CPSUtils.isNull(rb.getWorkflowID())){
				// organization specific,role based workflows not exist so follow the designation based workflow
				rb = txDesigBeasedWorkflowProcess.initWorkflow(rb);
			}
			if (CPSUtils.isNull(rb.getWorkflowID())) {
				// organization specific,role based,designation based workflows not exist role based workflow not exists so follow the generic workflow
				rb = txGenericWorkflowProcess.initWorkflow(rb);
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	/**
	 * In this method we will check the request assigned sfid And assigned the request to the assigned sfid Test auto delegate configuration, IF the assigned employee was configured then the request
	 * status will be updates & assigned to configured user
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public String assignRequest(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<<<Method>>>>>>>>>>>>>>>assignRequest(RequestBean rb)>>>>>>>>>");
		try {
			if (!CPSUtils.isNullOrEmpty(rb.getParentID())) {
				rb = insertHistory(rb);
				/**
				 * Check whether the employee is having auto delegate configuration or not. If the stageID is null or empty then it is an internal workflow, so there is no concept of auto delegate
				 */
				log.debug("::Check whether the employee is having auto delegate configuration or not");
				if (!CPSUtils.isNullOrEmpty(rb.getStageID())) {
					rb = autoDelegateProcess(rb);
				}
			} else {
				/**
				 * No workflow exists or completed the workflow Update the status to complete for this history id
				 */
				rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.COMPLETED, rb.getIpAddress(), rb.getRemarks(), CPSConstants.STATUSCOMPLETED));
				if (CPSUtils.compareStrings(rb.getMessage(), CPSConstants.SUCCESS)) {
					rb.setMessage(CPSConstants.UPDATE);
				}
			}
			log.debug("::<<<<<<<<<<<assignRequest(RequestBean rb)>>>>>>>>>Result Message>>>>" + rb.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	/**
	 * In this method we will check the auto delegate configurations
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean autoDelegateProcess(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>autoDelegateProcess(RequestBean rb)>>>>>>>>>");
		String parentID = null;
		try {
			rb.setIpAddress(null);
			parentID = rb.getParentID();
			log.debug("::Parent ID>>>>>>>" + parentID);

			rb = txUserLevelWorkflowProcess.checkUserAutoDelegate(rb);
			// check the previous parentid & present parent id
			if (!CPSUtils.compareStrings(parentID, rb.getParentID())) {
				/**
				 * Employee has configured auto delegate configuration so update the status & delegate to his configured user
				 */
				log.debug("::Employee has configured auto delegate configuration");
				rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.DELEGATED, rb.getIpAddress(), rb.getDelegateRemarks(), CPSConstants.STATUSDELEGATED));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage())) {
					rb = insertHistory(rb);

					/**
					 * After insertion also we should check the auto delegate configuration for assigned user
					 */
					log.debug("::After insertion also we should check the auto delegate");
					//rb.setDelegationFlag(false);
					rb = autoDelegateProcess(rb);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	/**
	 * In this method we will insert the history of the request
	 * 
	 * @param rb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RequestBean insertHistory(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>insertHistory(RequestBean rb)>>>>>>>>>");
		try {
			RequestCommonBean reqBean = new RequestCommonBean();
			reqBean.setRequestID(rb.getRequestID());
			reqBean.setWorkflowID(rb.getWorkflowID());

			reqBean.setRequestTypeID(rb.getRequestTypeID());
			reqBean.setStageID(rb.getStageID());
			reqBean.setSfID(rb.getSfID());
			reqBean.setParentID(rb.getParentID());
			reqBean.setHistoryID(generateUniqueID(CPSConstants.HISTORY));
			reqBean.setAssignedDate(CPSUtils.getCurrentDateWithTime());
			reqBean.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			reqBean.setStageStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			reqBean.setIpAddress(null);
			reqBean.setRemarks(null);
			
			if (CPSUtils.isNullOrEmpty(rb.getRoleID()) || CPSUtils.compareStrings(rb.getRoleID(), "null")) {
				reqBean.setAssignedRoleID(0);
			} else {
				reqBean.setAssignedRoleID(Integer.valueOf(rb.getRoleID()));
			}

			Session session = hibernateUtils.getSession();
			List<RequestCommonBean> reqList=session.createCriteria(RequestCommonBean.class).add(Expression.eq("requestID", rb.getRequestID())).list();
			if(reqList.size()==0){
				reqBean.setAppliedBy(rb.getSfID());
			}else{
				reqBean.setAppliedBy(reqList.get(0).getAppliedBy());
			}
			session.clear();
			session.save(reqBean);
			 session.flush();


			


			//hibernateUtils.closeSession();//for flush

			rb.setHistoryID(reqBean.getHistoryID());
			rb.setMessage(CPSConstants.SUCCESS);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		return rb;
	}

	/**
	 * This method will update the history table
	 * 
	 * @param historyID
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String updateRequestHistory(final String historyID, final String mode, final String ipAddress, final String remarks, final String stageStatus) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>updateRequestHistory(String historyID, String mode)>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			log.debug("::Mode >>>>" + mode);
			log.debug("::History ID >>>" + historyID);
			log.debug("::IP Address>>>>" + ipAddress);

			RequestCommonBean requestCommonBean = (RequestCommonBean) session.get(RequestCommonBean.class, historyID);
			requestCommonBean.setStatus(((StatusMasterDTO) session.createCriteria(StatusMasterDTO.class).add(Expression.eq(CPSConstants.NAME, mode).ignoreCase()).uniqueResult()).getId());
			requestCommonBean.setActionedDate(CPSUtils.getCurrentDate());
			requestCommonBean.setIpAddress(ipAddress);
			requestCommonBean.setRemarks(remarks);
			requestCommonBean.setStageStatus(Integer.valueOf(stageStatus));
			session.saveOrUpdate(requestCommonBean);
			session.flush();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	/**
	 * This method will update the history table
	 * 
	 * @param historyID
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String updateRequestStatusToEscalate(final String requestID, final String stageID) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>updateRequestStatusToEscalate(String requestID, String stageID)>>>>>>>>>");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			session.createSQLQuery("update request_workflow_history set status=(select id from status_master where status=upper(?)),actioned_date=sysdate where request_id=? and request_stage=?")
					.setString(0, CPSConstants.ESCALATED).setString(1, requestID).setString(2, stageID).executeUpdate();

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	/**
	 * Update the status of a request that contains particular history id
	 * 
	 * @param historyID
	 * @return
	 * @throws Exception
	 */
	public String declinedRequest(final String historyID, final String ipAddress, final String remarks, final String type, final String stageID) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>declinedRequest(String historyID, String ipAddress, String remarks, String type, String stageID)>>>>>>>>>");
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();

			session.createSQLQuery(
					"update request_workflow_history set status=(select id from status_master where status=upper(?)),actioned_date=sysdate,ip_address=?,remarks=?, stage_status=? "
							+ "where request_id=(select request_id from request_workflow_history where id=?) and request_stage=(select request_stage from request_workflow_history where id=?)")
					.setString(0, type).setString(1, ipAddress).setString(2, remarks).setString(3, stageID).setString(4, historyID).setString(5, historyID).executeUpdate();

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	/**
	 * This method will be called when the user manually delegate his assigned request to his selected user
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public String delegateRequest(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>delegateRequest(RequestBean rb)>>>>>>>>>");
		try {
			/**
			 * Change the status from pending to approved status for remaining history
			 */
			changePendingToDelegate(rb.getHistoryID());

			/**
			 * Update the status to DELEGATE
			 */
			rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.DELEGATED, rb.getIpAddress(), rb.getRemarks(), CPSConstants.STATUSDELEGATED));// DELEGATED

			log.debug("::Messge >>> " + rb.getMessage());

			if (CPSUtils.compareStrings(rb.getMessage(), CPSConstants.SUCCESS)) {
				/**
				 * get the details of a particular history ID
				 */
				rb = getRequestDetails(rb);

				/**
				 * Check whether the employee selected instance or an employee
				 */
				if (CPSUtils.isNumeric(rb.getParentID())) {
					rb.setParentID(getInstanceParentID(rb.getParentID()));
				}

				/**
				 * Assigning the request
				 */
				rb.setMessage(assignRequest(rb));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	/**
	 * If the user delegated to instance then it will return the sfid mapped to instance id
	 */
	public String getInstanceParentID(final String nodeID) throws Exception {
		Session session = null;
		EmpRoleMappingDTO empRoleMappingDTO = null;
		try {
			session = hibernateUtils.getSession();
			empRoleMappingDTO = (EmpRoleMappingDTO) session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq("roleInstanceId", Integer.valueOf(nodeID))).add(
					Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();

		} catch (Exception e) {
			throw e;
		}
		return empRoleMappingDTO.getSfid();
	}

	/**
	 * This method will return the request stage details of a particular hisroty id
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean getRequestDetails(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>getRequestDetails(RequestBean rb)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			RequestCommonBean requestBean = (RequestCommonBean) session.get(RequestCommonBean.class, rb.getHistoryID());
			rb.setRequestID(requestBean.getRequestID());
			rb.setWorkflowID(requestBean.getWorkflowID());
			rb.setRequestTypeID(requestBean.getRequestTypeID());
			rb.setStageID(requestBean.getStageID());
			/*rb.setSfID(requestBean.getSfID());*/
			rb.setSfID(requestBean.getParentID());

			log.debug("::historyID:" + rb.getHistoryID() + ", request ID:" + rb.getRequestID() + ", Workflow ID:" + rb.getWorkflowID() + ", request typeID:" + rb.getRequestTypeID() + ", stage ID:"
					+ rb.getStageID() + ", assigned ID:" + rb.getSfID());
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	/**
	 * This method will be called for escalating the requests
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public String escalatedRequests() throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>escalatedRequests()>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		ResultSet rsq4 = null;
		ResultSet rsq5 = null;
		RequestBean rb = null;
		String escalatedList = "";
		String requestsList = "";
		String escalationDays = null;
		String result = null;
		try {
			rb = new RequestBean();

			session = hibernateUtils.getSession();

			con = session.connection();
			escalationDays = getConfiguredValue(CPSConstants.ESCALATED_TIME);
			/**
			 * First get the delegated requests
			 */
			String getEscalatedRequests = "select rwh.id,rwh.request_id,rwh.workflow_id,rwh.request_type_id,rwh.request_stage,assigned_to,to_char(assigned_date,'DD-MON-YYYY') assignedDate from request_workflow_history rwh "
					+ "where id in (select min(id) from request_workflow_history where status=? and to_date(sysdate,'dd-mon-yyyy HH24:MI:SS')>(to_date(assigned_date,'dd-mon-yyyy HH24:MI:SS')+?) "
					+ "group by request_id)";

			ps4 = con.prepareStatement(getEscalatedRequests);
			ps4.setString(1, CPSConstants.STATUSDELEGATED);
			ps4.setString(2, escalationDays);
			log.debug("::SQL : getEscalatedRequests1 > > " + ps4);
			rsq4 = ps4.executeQuery();
			while (rsq4.next()) {
				if (checkEscalationHolidays(rsq4.getString("assignedDate"), escalationDays)) {
					escalatedList += rsq4.getString("id") + "," + rsq4.getString("request_id") + "," + rsq4.getString("workflow_id") + "," + rsq4.getString("request_type_id") + ","
							+ rsq4.getString("request_stage") + "," + rsq4.getString("assigned_to") + "#";
					requestsList += rsq4.getString("request_id") + ",";
				}
			}
			if (requestsList.length() == 0) {
				requestsList = "0,";
			}
			/**
			 * get the pending requests
			 */
			getEscalatedRequests = "select rwh.id,rwh.request_id,rwh.workflow_id,rwh.request_type_id,rwh.request_stage,assigned_to,to_char(assigned_date,'DD-MON-YYYY') assignedDate from request_workflow_history rwh "
					+ "where id in (select min(id) from request_workflow_history where status=? and request_id not in ("
					+ requestsList.substring(0, requestsList.length() - 1)
					+ ") "
					+ "and to_date(sysdate,'dd-mon-yyyy HH24:MI:SS')>(to_date(assigned_date,'dd-mon-yyyy HH24:MI:SS')+?) group by request_id)";
			ps4 = con.prepareStatement(getEscalatedRequests);
			ps4.setString(1, CPSConstants.STATUSPENDING);
			ps4.setString(2, escalationDays);
			log.debug("::SQL : getEscalatedRequests2 > > " + ps4);
			rsq4 = ps4.executeQuery();
			while (rsq4.next()) {
				if (checkEscalationHolidays(rsq4.getString("assignedDate"), escalationDays)) {
					escalatedList += rsq4.getString("id") + "," + rsq4.getString("request_id") + "," + rsq4.getString("workflow_id") + "," + rsq4.getString("request_type_id") + ","
							+ rsq4.getString("request_stage") + "," + rsq4.getString("assigned_to") + "#";
				}
			}

			String[] escalatedArr = escalatedList.split("#");
			for (int i = 0; i < escalatedArr.length; i++) {
				String[] reqDetails = escalatedArr[i].split(",");
				if (reqDetails.length > 1) {
					String escalatedWorkflowDetails = "select escalation_to,escalation_relationship_type from workflow where workflow_id=? and stage_id=? ";
					ps5 = con.prepareStatement(escalatedWorkflowDetails);
					ps5.setString(1, reqDetails[2]);
					ps5.setString(2, reqDetails[4]);
					log.debug("::SQL : escalatedWorkflowDetails > > " + ps5);
					rsq5 = ps5.executeQuery();
					if (rsq5.next()) {
						rb.setSfID(reqDetails[5]);
						rb.setRequestID(reqDetails[1]);
						rb.setWorkflowID(reqDetails[2]);
						rb.setRequestTypeID(reqDetails[3]);
						rb.setStageID(reqDetails[4]);
						if (CPSUtils.isNull(rb.getRequesterOfficeID())) {
							rb.setRequesterOfficeID(txWorkflowProcess.getRequesterOfficeID(rb.getRequestID(), rb.getSfID()));
						}

						if (CPSUtils.compareStrings(rsq5.getString("escalation_relationship_type"), CPSConstants.WORKFLOWRELATIONRELID)) {
							// RELATIVE
							result = txWorkflowProcess.getAssignedID(rb.getSfID(), rsq5.getString("escalation_to"), null, CPSConstants.ESCALATED, null, rb.getRequestID());
							if (!CPSUtils.isNullOrEmpty(result)) {
								rb.setParentID(result.split("#")[0]);
								rb.setRoleID(result.split("#")[1]);
							}
						} else if (CPSUtils.compareStrings(rsq5.getString("escalation_relationship_type"), CPSConstants.WORKFLOWRELATIONABSID)) {
							// ABSOLUTE
							result = txWorkflowProcess.getInstanceSFID(rsq5.getString("escalation_to"));
							if (!CPSUtils.isNullOrEmpty(result)) {
								rb.setParentID(result.split("#")[0]);
								rb.setRoleID(result.split("#")[1]);
							}
						} else if (CPSUtils.compareStrings(rsq5.getString("escalation_relationship_type"), CPSConstants.WORKFLOWRELATIONDEPID)) {
							// DEPARTMENT
							result = txWorkflowProcess.getDependentDetails(rsq5.getString("escalation_to"), rb);
							if (!CPSUtils.isNullOrEmpty(result)) {
								rb.setParentID(result.split("#")[0]);
								rb.setRoleID(result.split("#")[1]);
							}
						}

						/**
						 * Check whether the request was already escalated or not
						 */
						if (!CPSUtils.compareStrings(rb.getSfID(), rb.getParentID())) {
							// escalation record is exist for this workflow & stage
							rb.setMessage(updateRequestStatusToEscalate(rb.getRequestID(), rb.getStageID()));// ESCALATED

							if (CPSUtils.compareStrings(rb.getMessage(), CPSConstants.SUCCESS)) {
								// Assigning the request
								rb.setMessage(assignRequest(rb));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(null, ps4, rsq4);
			ConnectionUtil.closeConnection(null, ps5, rsq5);
		}
		return rb.getMessage();
	}

	public boolean checkEscalationHolidays(final String assignedDate, final String escalationDays) throws Exception {
		Session session = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();

			String sql = "select count(*) from(select rownum rowlevel,dates,days from (select "
					+ "(trunc(to_date(?,'dd-mon-yyyy'), 'DD')-1) + level dates,to_char(to_date(to_char((trunc(to_date(?,'dd-mon-yyyy'), 'DD')-1) + level,'dd-mon-yyyy'),'dd-mon-yyyy'), 'DY') days "
					+ "from dual connect by level <= 20) where days!='SAT' and days!='SUN' and (select count(*) from holidays_master "
					+ "where status=1 and dates=to_char(holiday_date,'DD-MON-YYYY'))=0) where rowlevel=? " + "and to_date(sysdate,'DD-MON-YYYY')>=to_date(dates,'DD-MON-YYYY')";

			int cnt = Integer.valueOf(session.createSQLQuery(sql).setString(0, assignedDate).setString(1, assignedDate).setString(2, escalationDays).uniqueResult().toString());
			if (cnt > 0) {
				status = true;
			}

		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	/**
	 * This method will return the value of the configured name
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String getConfiguredValue(final String name) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>getConfiguredValue(String name)>>>>>>>>>");
		Session session = null;
		String configuredValue = null;
		try {
			session = hibernateUtils.getSession();
			configuredValue = session.createSQLQuery("select value from configuration_details where name=?").setString(0, name).uniqueResult().toString();

			log.debug("::Configuration ::::name>>>" + name + " ::::value>>>" + configuredValue);
		} catch (Exception e) {
			throw e;
		}
		return configuredValue;
	}

	/**
	 * Change the delegated or pending status to approved status
	 * 
	 * @param historyID
	 * @return
	 * @throws Exception
	 */
	public void changeDelegateOrPendingToApprove(final String historyID, final String stageID) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>changeDelegateOrPendingToApprove(String historyID)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			session
					.createSQLQuery(
							"update request_workflow_history rwh2 set rwh2.status=?,rwh2.stage_status=?,rwh2.actioned_date=sysdate where rwh2.id in ( select rwh.id from request_workflow_history rwh,"
									+ "status_master sm where rwh.request_id=(select rwh1.request_id from request_workflow_history rwh1 where rwh1.id=?) and rwh.status=sm.id and upper(sm.status) in (upper(?),upper(?)) and rwh.id!=?)")
					.setString(0, CPSConstants.STATUSAPPROVED).setString(1, stageID).setString(2, historyID).setString(3, CPSConstants.DELEGATED).setString(4, CPSConstants.PENDING).setString(5,
							historyID).executeUpdate();
         
			session.flush();
			
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Change the pending status to delegated status
	 * 
	 * @param historyID
	 * @return
	 * @throws Exception
	 */
	public void changePendingToDelegate(final String historyID) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>changePendingToDelegate(String historyID)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			session
					.createSQLQuery(
							"update request_workflow_history rwh2 set rwh2.status=5 where rwh2.id in ( select rwh.id from request_workflow_history rwh,"
									+ "status_master sm where rwh.request_id=(select rwh1.request_id from request_workflow_history rwh1 where rwh1.id=?) and rwh.status=sm.id and upper(sm.status)=upper(?) and rwh.id!=?)")
					.setString(0, historyID).setString(1, CPSConstants.PENDING).setString(2, historyID).executeUpdate();

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This method will be called when a user approve the request that was assigned to him
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean approvedRequest(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>approvedRequest(RequestBean rb)>>>>>>>>>");
		try {

			/**
			 * update status to approve for all the txn details those are having delegated
			 */
			changeDelegateOrPendingToApprove(rb.getHistoryID(), rb.getWorkflowStageID());

			rb = checkAssignedWorkflow(rb);
			if (CPSUtils.isNullOrEmpty(rb.getWorkflowID())) {
				/**
				 * Work Flow Not yet decided, till now, the request is in internal workflow
				 */
				log.debug("::workflow not yet assigned");
				rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.APPROVED, rb.getIpAddress(), rb.getRemarks(), rb.getWorkflowStageID()));
				rb.setMessage(initWorkflow(rb));
			} else {
				/**
				 * work flow already started for this request so we need to follow the next stage
				 */
				log.debug("::workflow already assigned");
				rb.setStageID(String.valueOf((Integer.parseInt(rb.getStageID()) + 1)));
				// check whether this workflow was completed or not
				if (checkNextWorkFlowStage(rb)) {
					log.debug("::workflow not yet completed");
					rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.APPROVED, rb.getIpAddress(), rb.getRemarks(), rb.getWorkflowStageID()));

					// Next work flow stage exists
					log.debug("::check next workflow stage");
					rb = getDetailsAndProcessRequest(rb);
					sendMail(rb);
				} else {
					/**
					 * Work flow completed Check whether the last workflow handover to any other workflow or not if not then check the previous workflow that this request was followed If every thing
					 * is completed then update it in the DB
					 */
					log.debug("::workflow completed");
					String handOverWorkFlow = getHandOverWorkFlow(rb.getWorkflowID());
					if (CPSUtils.isNullOrEmpty(handOverWorkFlow) || CPSUtils.compareStrings(handOverWorkFlow, "0")) {

						/**
						 * There is no Hand over workflow Check the previous workflow that the current request was followed
						 */
						log.debug("::There is no Hand over workflow");
						rb = checkPreviousWorkflows(rb);
						if (!CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage())) {
							rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.COMPLETED, rb.getIpAddress(), rb.getRemarks(), rb.getWorkflowStageID()));
							log.debug("::Workflows completed");

							/**
							 * No previous workflows were exists so update in the DB
							 */
							log.debug("::update txn details");
							rb.setMessage(CPSConstants.UPDATE);
							sendMail(rb);
						}
					} else {
						/**
						 * Workflow handover to another workflow set the stage as 1
						 */
						log.debug("::Workflow handover to another workflow");
						rb.setStageID("1");
						rb.setWorkflowID(handOverWorkFlow);
						rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.APPROVED, rb.getIpAddress(), rb.getRemarks(), rb.getWorkflowStageID()));

						// get the next assigned parent ID
						rb = getDetailsAndProcessRequest(rb);
						sendMail(rb);
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return rb;
	}

	/**
	 * In this method we will get the parent ID of a particular workflow ID & stage And get the request type ID Assigns that request to next level
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean getDetailsAndProcessRequest(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>getDetailsAndProcessRequest(RequestBean rb)>>>>>>>>>");
		try {
			// Get the assigned sfid for a particular workflow & stage
			rb = txWorkflowProcess.getWorkFlowAssignedSfid(rb, new RequestBean(),new ArrayList<RequestBean>());
			// Get the request Type ID
			rb = getRequestTypeDetails(rb);
			// Assign the request
			rb.setMessage(assignRequest(rb));
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	/**
	 * In this method we will find the previous workflow that are already followed by the current request or not
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RequestBean checkPreviousWorkflows(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>checkPreviousWorkflows(RequestBean rb)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			List<RequestBean> list = session.createSQLQuery(
					"select tab1.id historyID,tab1.workflow_id workflowID,rwh.request_stage stageID,rwh.request_id requestID from (select "
							+ "request_id,workflow_id,max(id) id from request_workflow_history where request_id=(select request_id "
							+ "from request_workflow_history where id=?) group by request_id, workflow_id) tab1,request_workflow_history rwh "
							+ "where rwh.id=tab1.id and rwh.id!=? order by tab1.id desc").addScalar("historyID", Hibernate.STRING).addScalar("workflowID", Hibernate.STRING).addScalar("stageID",
					Hibernate.STRING).addScalar("requestID", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RequestBean.class)).setString(0, rb.getHistoryID()).setString(1,
					rb.getHistoryID()).list();

			for (RequestBean reqBean : list) {
				if (!CPSUtils.isNullOrEmpty(reqBean.getWorkflowID())) {
					rb.setWorkflowID(reqBean.getWorkflowID());
					rb.setStageID(reqBean.getStageID());
					rb.setRequestID(reqBean.getRequestID());
					rb.setStageID(String.valueOf((Integer.parseInt(rb.getStageID()) + 1)));
					/**
					 * check whether this workflow stage was completed are not
					 */
					if (checkNextWorkFlowStage(rb)) {
						rb = getDetailsAndProcessRequest(rb);
					}
					break;
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	/**
	 * This method will return the hand over workflow
	 * 
	 * @param workFlowID
	 * @return
	 * @throws Exception
	 */
	public String getHandOverWorkFlow(final String workFlowID) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>getHandOverWorkFlow(String workFlowID)>>>>>>>>>");
		String handOverWorkflow = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			handOverWorkflow = ((WorkFlowMasterDTO) session.get(WorkFlowMasterDTO.class, Integer.valueOf(workFlowID))).getToWorkFlow();
			log.debug("::handOverWorkflow>>>>" + handOverWorkflow);
		} catch (Exception e) {
			throw e;
		}
		return handOverWorkflow;
	}

	/**
	 * This method will check whether the request workflow next stage is exists or not
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public boolean checkNextWorkFlowStage(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>checkNextWorkFlowStage(RequestBean rb)>>>>>>>>>");
		Session session = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();
			WorkFlowMappingBean workFlowMappingBean = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
					Expression.eq("stageId", rb.getStageID())).uniqueResult();
			if (!CPSUtils.isNull(workFlowMappingBean)) {
				status = true;
			}
			log.debug("::next workflow stage>>>>" + status);
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	/**
	 * Get the work flow history details of a particular history id
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean checkAssignedWorkflow(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxRequestProcess<<<<Method>>>>>>>>>>>>>>>checkAssignedWorkflow(RequestBean rb)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			RequestCommonBean requestBean = (RequestCommonBean) session.get(RequestCommonBean.class, rb.getHistoryID());
			rb.setWorkflowID(requestBean.getWorkflowID());
			rb.setStageID(requestBean.getStageID());
			rb.setRequestID(requestBean.getRequestID());
			rb.setAppliedBy(requestBean.getAppliedBy());
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	/**
	 * super admin is delegating the requests
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean delegateRequests(RequestBean rb) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			EmpRoleMappingDTO empDTO = (EmpRoleMappingDTO) session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq("roleInstanceId", Integer.valueOf(rb.getInstanceId()))).add(
					Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();

			if (!CPSUtils.isNull(empDTO)) {

				rb.setParentID(empDTO.getSfid());
				rb.setRoleID(String.valueOf(empDTO.getOrgRoleId()));

				String[] requests = rb.getRequestIDs().split(",");

				for (String requestID : requests) {
					rb.setRequestID(requestID);

					RequestBean requestBean = (RequestBean) session
							.createSQLQuery(
									"select id historyID,workflow_id workflowID,request_type_id requestTypeID,request_stage stageID from request_workflow_history where id=(select max(id) from request_workflow_history where request_id=?)")
							.addScalar("historyID", Hibernate.STRING).addScalar("workflowID", Hibernate.STRING).addScalar("requestTypeID", Hibernate.STRING).addScalar("stageID", Hibernate.STRING)
							.setResultTransformer(Transformers.aliasToBean(RequestBean.class)).setString(0, rb.getRequestID()).uniqueResult();

					rb.setWorkflowID(requestBean.getWorkflowID());
					rb.setRequestTypeID(requestBean.getRequestTypeID());
					rb.setStageID(requestBean.getStageID());
					rb.setRoleID(String.valueOf(empDTO.getRoleInstanceId()));
					
					session.createSQLQuery("update request_workflow_history set status=?,remarks=?,stage_status=? where id=?").setString(0, CPSConstants.STATUSDELEGATED).setString(1, rb.getRemarks())
							.setString(2, CPSConstants.STATUSDELEGATED).setString(3, requestBean.getHistoryID()).executeUpdate();

					rb = insertHistory(rb);
				}
			} else {
				rb.setMessage(CPSConstants.FAILED);
				rb.setRemarks("Please assign head for this role");
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
}
