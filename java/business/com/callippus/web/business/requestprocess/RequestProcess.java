package com.callippus.web.business.requestprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.workflow.GenericWorkflowProcess;
import com.callippus.web.business.workflow.InternalWorkflowProcess;
import com.callippus.web.business.workflow.RoleBasedWorkflowProcess;
import com.callippus.web.business.workflow.UserLevelWorkflowProcess;
import com.callippus.web.business.workflow.WorkflowProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class RequestProcess {

	private static Log log = LogFactory.getLog(RequestProcess.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private GenericWorkflowProcess genericWorkflowProcess;
	@Autowired
	private InternalWorkflowProcess internalWorkflowProcess;
	@Autowired
	private WorkflowProcess workflowProcess;
	@Autowired
	private UserLevelWorkflowProcess userLevelWorkflowProcess;
	@Autowired
	private RoleBasedWorkflowProcess roleBeasedWorkflowProcess;

	/**
	 * This method will generate the unique id for a key
	 * 
	 * @return request id
	 * @throws Exception
	 */
	public String generateUniqueID(String tableName) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>generateUniqueID(String tableName)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		ResultSet rsq = null;
		PreparedStatement ps = null;
		String generatedID = null;
		try {
			log.debug("::Table Name>>>>>>" + tableName);
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String getRequestID = "select value from id_generator where upper(table_name)=? FOR UPDATE";
			ps = con.prepareStatement(getRequestID);
			ps.setString(1, tableName.toUpperCase());
			log.debug("::SQL : getRequestID > > " + ps);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				int id = rsq.getInt("value") + 1;
				String updateID = "update id_generator set value=? where upper(table_name)=?";
				ps = con.prepareStatement(updateID);
				ps.setInt(1, id);
				ps.setString(2, tableName.toUpperCase());
				log.debug("::SQL : updateID > > " + ps);
				ps.executeUpdate();
				generatedID = String.valueOf(id);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		log.debug("::Generated ID > > " + generatedID);
		return generatedID;
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
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>getRequestTypeDetails(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		ResultSet rsq1 = null;
		PreparedStatement ps1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String getRequestTypeID = "select request_type_id,internal_flag from request_master where upper(request_type)=upper(?) and status=1";
			ps1 = con.prepareStatement(getRequestTypeID);
			ps1.setString(1, rb.getRequestType());
			log.debug("::SQL : getRequestTypeID > > " + ps1);
			rsq1 = ps1.executeQuery();
			if (rsq1.next()) {
				rb.setRequestTypeID(rsq1.getString("request_type_id"));
				if (CPSUtils.compareStrings(rsq1.getString("internal_flag"), "1")) {
					log.debug("::Internal Workflow Exists for " + rb.getRequestType());
					rb.setRequestInternalFlag(true);
				} else {
					log.debug("::No Internal Workflow for " + rb.getRequestType());
					rb.setRequestInternalFlag(false);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return rb;
	}

	public WorkflowTxnDTO txnRowDetails(String reqTypeID, String reqID, String referenceNumber, String colName, String ipAddress, String newValue, String prevValue, String rowNo, String desc) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>txnRowDetails()>>>>>>>>>");
		WorkflowTxnDTO workTxnDTO = null;
		try {
			workTxnDTO = new WorkflowTxnDTO();
			workTxnDTO.setTxnID(generateUniqueID(CPSConstants.WORKFLOW_TXN_DETAILS));
			workTxnDTO.setRequestTypeID(reqTypeID);
			workTxnDTO.setRequestID(reqID);
			workTxnDTO.setReferenceNumber(referenceNumber);
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
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>submitTxnDetails(RequestBean rb)>>>>>>>>>");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (!CPSUtils.isNull(txnDetails)) {
				for (int i = 0; i < txnDetails.size(); i++) {
					WorkflowTxnDTO workflowTxnDTO = (WorkflowTxnDTO) txnDetails.get(i);
					if (!CPSUtils.isNullOrEmpty(workflowTxnDTO.getPreviousValue()) || !CPSUtils.isNullOrEmpty(workflowTxnDTO.getNewValue())) {
						//tx = session.beginTransaction();
						session.saveOrUpdate(workflowTxnDTO);
						session.flush();//tx.commit() ;
					}
				}
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			//session.close();
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
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(RequestBean rb)>>>>>>>>>");
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
				rb = internalWorkflowProcess.getInternalDivisionWorkFlow(rb);
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
		} catch (Exception e) {
			rb.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return rb.getMessage();
	}

	public RequestBean decideWorkflow(RequestBean rb) throws Exception {
		try {
			// check the role based workflow
			rb = roleBeasedWorkflowProcess.initWorkflow(rb);
			if (CPSUtils.isNull(rb.getWorkflowID())) {
				// role based workflow not exists so follow the generic workflow
				rb = genericWorkflowProcess.initWorkflow(rb);
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
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>assignRequest(RequestBean rb)>>>>>>>>>");
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
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>autoDelegateProcess(RequestBean rb)>>>>>>>>>");
		String parentID = null;
		try {
			parentID = rb.getParentID();
			log.debug("::Parent ID>>>>>>>" + parentID);

			rb = userLevelWorkflowProcess.checkUserAutoDelegate(rb);
			// check the previous parentid & present parent id
			if (!CPSUtils.compareStrings(parentID, rb.getParentID())) {
				/**
				 * Employee has configured auto delegate configuration so update the status & delegate to his configured user
				 */
				log.debug("::Employee has configured auto delegate configuration");
				rb.setMessage(updateRequestHistory(rb.getHistoryID(), CPSConstants.DELEGATED, rb.getIpAddress(), "", CPSConstants.STATUSDELEGATED));
				if (CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage())) {
					rb = insertHistory(rb);

					/**
					 * After insertion also we should check the auto delegate configuration for assigned user
					 */
					log.debug("::After insertion also we should check the auto delegate");
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
	public RequestBean insertHistory(RequestBean rb) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>insertHistory(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Transaction tx = null;
		try {
			rb.setHistoryID(generateUniqueID(CPSConstants.HISTORY));
			rb.setAssignedDate(CPSUtils.getCurrentDateWithTime());
			rb.setStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			rb.setStageStatus(Integer.valueOf(CPSConstants.STATUSPENDING));
			rb.setIpAddress(null);
			rb.setRemarks(null);
			if (CPSUtils.isNullOrEmpty(rb.getRoleID())) {
				rb.setRoleID("0");
			}

			rb.setAssignedRoleID(Integer.valueOf(rb.getRoleID()));
			log.debug("::hisroty ID>>>>>>>" + rb.getHistoryID());
			log.debug("::Assigned Date>>>>>>>" + rb.getAssignedDate());
			log.debug("::Stage ID>>>>>>" + rb.getStageID());
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(rb);
			session.flush();//tx.commit() ;
			rb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			rb.setMessage(CPSConstants.FAILED);
			throw e;

		} finally {
			//session.close();
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
	public String updateRequestHistory(String historyID, String mode, String ipAddress, String remarks, String stageStatus) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>updateRequestHistory(String historyID, String mode)>>>>>>>>>");
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String updateHistory = "update request_workflow_history set status=(select id from status_master where upper(status)=upper(?)),actioned_date=sysdate,ip_address=?,remarks=?, stage_status=? where id=?";
			log.debug("::SQL : updateHistory > > " + updateHistory);
			log.debug("::Mode >>>>" + mode);
			log.debug("::History ID >>>" + historyID);
			log.debug("::IP Address>>>>" + ipAddress);
			ps2 = con.prepareStatement(updateHistory);
			ps2.setString(1, mode);
			ps2.setString(2, ipAddress);
			ps2.setString(3, remarks);
			ps2.setString(4, stageStatus);
			ps2.setString(5, historyID);

			ps2.executeUpdate();
			log.debug("::SQL : updateHistory > > " + ps2);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			ConnectionUtil.closeConnection(session, ps2, null);
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
	public String updateRequestStatusToEscalate(String requestID, String stageID) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>updateRequestStatusToEscalate(String requestID, String stageID)>>>>>>>>>");
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String updateHistory = "update request_workflow_history set status=(select id from status_master where status=upper(?)),actioned_date=sysdate where request_id=? and request_stage=?";
			ps2 = con.prepareStatement(updateHistory);
			ps2.setString(1, CPSConstants.ESCALATED);
			ps2.setString(2, requestID);
			ps2.setString(3, stageID);
			ps2.executeUpdate();
			log.debug("::SQL : updateHistory > > " + ps2);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			ConnectionUtil.closeConnection(session, ps2, null);
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
	public String declinedRequest(String historyID, String ipAddress, String remarks, String type, String stageID) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>declinedRequest(String historyID, String ipAddress, String remarks, String type, String stageID)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String sql = "update request_workflow_history set status=(select id from status_master where status=upper(?)),actioned_date=sysdate,ip_address=?,remarks=?, stage_status=? "
					+ "where request_id=(select request_id from request_workflow_history where id=?) and request_stage=(select request_stage from request_workflow_history where id=?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, type);
			ps.setString(2, ipAddress);
			ps.setString(3, remarks);
			ps.setString(4, stageID);
			ps.setString(5, historyID);
			ps.setString(6, historyID);
			ps.executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		finally {
			ConnectionUtil.closeConnection(session, ps, null);
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
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>delegateRequest(RequestBean rb)>>>>>>>>>");
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
	public String getInstanceParentID(String nodeID) throws Exception {
		Session session1 = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		String parentID = null;
		try {
			session1 = hibernateUtils.getSession();
			con = session1.connection();

			String getParentID = "select sfid from emp_role_mapping where org_role_id=? and status=1";
			ps = con.prepareStatement(getParentID);
			ps.setString(1, nodeID);
			rsq = ps.executeQuery();
			while (rsq.next()) {
				parentID = rsq.getString("sfid");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session1, ps, rsq);
		}
		return parentID;
	}

	/**
	 * This method will return the request stage details of a particular hisroty id
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean getRequestDetails(RequestBean rb) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>getRequestDetails(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps3 = null;
		ResultSet rsq3 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String getRequestDetails = "select request_id,workflow_id,request_type_id,request_stage,assigned_to from request_workflow_history where id=?";
			log.debug("::SQL : getRequestDetails > > " + getRequestDetails);
			ps3 = con.prepareStatement(getRequestDetails);
			ps3.setString(1, rb.getHistoryID());
			log.debug("::SQL : getRequestDetails > > " + ps3);
			rsq3 = ps3.executeQuery();
			while (rsq3.next()) {
				rb.setRequestID(rsq3.getString("request_id"));
				rb.setWorkflowID(rsq3.getString("workflow_id"));
				rb.setRequestTypeID(rsq3.getString("request_type_id"));
				rb.setStageID(rsq3.getString("request_stage"));
				rb.setSfID(rsq3.getString("assigned_to"));
			}
			log.debug("::historyID:" + rb.getHistoryID() + ", request ID:" + rb.getRequestID() + ", Workflow ID:" + rb.getWorkflowID() + ", request typeID:" + rb.getRequestTypeID() + ", stage ID:"
					+ rb.getStageID() + ", assigned ID:" + rb.getSfID());
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps3, rsq3);
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
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>escalatedRequests()>>>>>>>>>");
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

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
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
							rb.setRequesterOfficeID(workflowProcess.getRequesterOfficeID(rb.getRequestID(), rb.getSfID()));
						}

						if (CPSUtils.compareStrings(rsq5.getString("escalation_relationship_type"), CPSConstants.WORKFLOWRELATIONRELID)) {
							// RELATIVE
							result = workflowProcess.getAssignedID(rb.getSfID(), rsq5.getString("escalation_to"), null, CPSConstants.ESCALATED, null, rb.getRequestID());
							if (!CPSUtils.isNullOrEmpty(result)) {
								rb.setParentID(result.split("#")[0]);
								rb.setRoleID(result.split("#")[1]);
							}
						} else if (CPSUtils.compareStrings(rsq5.getString("escalation_relationship_type"), CPSConstants.WORKFLOWRELATIONABSID)) {
							// ABSOLUTE
							result = workflowProcess.getInstanceSFID(rsq5.getString("escalation_to"));
							if (!CPSUtils.isNullOrEmpty(result)) {
								rb.setParentID(result.split("#")[0]);
								rb.setRoleID(result.split("#")[1]);
							}
						} else if (CPSUtils.compareStrings(rsq5.getString("escalation_relationship_type"), CPSConstants.WORKFLOWRELATIONDEPID)) {
							// DEPARTMENT
							result = workflowProcess.getDependentDetails(rsq5.getString("escalation_to"), rb);
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
				ConnectionUtil.closeConnection(null, ps5, rsq5);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps4, rsq4);
			ConnectionUtil.closeConnection(null, ps5, rsq5);
		}
		return rb.getMessage();
	}

	public boolean checkEscalationHolidays(String assignedDate, String escalationDays) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps7 = null;
		ResultSet rsq7 = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String sql = "select count(*) from(select rownum rowlevel,dates,days from (select "
					+ "(trunc(to_date(?,'dd-mon-yyyy'), 'DD')-1) + level dates,to_char(to_date(to_char((trunc(to_date(?,'dd-mon-yyyy'), 'DD')-1) + level,'dd-mon-yyyy'),'dd-mon-yyyy'), 'DY') days "
					+ "from dual connect by level <= 20) where days!='SAT' and days!='SUN' and (select count(*) from holidays_master "
					+ "where status=1 and dates=to_char(holiday_date,'DD-MON-YYYY'))=0) where rowlevel=? " + "and to_date(sysdate,'DD-MON-YYYY')>=to_date(dates,'DD-MON-YYYY')";
			ps7 = con.prepareStatement(sql);
			ps7.setString(1, assignedDate);
			ps7.setString(2, assignedDate);
			ps7.setString(3, escalationDays);
			rsq7 = ps7.executeQuery();
			if (rsq7.next()) {
				if (rsq7.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps7, rsq7);
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
	public String getConfiguredValue(String name) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>getConfiguredValue(String name)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps6 = null;
		ResultSet rsq6 = null;
		String configuredValue = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String getEscalatedTime = "select value from configuration_details where name=?";
			ps6 = con.prepareStatement(getEscalatedTime);
			ps6.setString(1, name);
			log.debug("::SQL : getEscalatedTime > > " + ps6);
			rsq6 = ps6.executeQuery();
			if (rsq6.next()) {
				configuredValue = rsq6.getString("value");
			}
			log.debug("::Configuration ::::name>>>" + name + " ::::value>>>" + configuredValue);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps6, rsq6);
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
	public String changeDelegateOrPendingToApprove(String historyID, String stageID) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>changeDelegateOrPendingToApprove(String historyID)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps8 = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String updateDelegatedStatus = "update request_workflow_history rwh2 set rwh2.status=?,rwh2.stage_status=?,rwh2.actioned_date=sysdate where rwh2.id in ( select rwh.id from request_workflow_history rwh,"
					+ "status_master sm where rwh.request_id=(select rwh1.request_id from request_workflow_history rwh1 where rwh1.id=?) and rwh.status=sm.id and upper(sm.status) in (upper(?),upper(?)) and rwh.id!=?)";

			ps8 = con.prepareStatement(updateDelegatedStatus);
			ps8.setString(1, CPSConstants.STATUSAPPROVED);
			ps8.setString(2, stageID);
			ps8.setString(3, historyID);
			ps8.setString(4, CPSConstants.DELEGATED);
			ps8.setString(5, CPSConstants.PENDING);
			ps8.setString(6, historyID);
			log.debug("::SQL : updateDelegatedStatus > > " + ps8);
			ps8.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps8, null);
		}
		return message;
	}

	/**
	 * Change the pending status to delegated status
	 * 
	 * @param historyID
	 * @return
	 * @throws Exception
	 */
	public String changePendingToDelegate(String historyID) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>changePendingToDelegate(String historyID)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps8 = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String updateDelegatedStatus = "update request_workflow_history rwh2 set rwh2.status=5 where rwh2.id in ( select rwh.id from request_workflow_history rwh,"
					+ "status_master sm where rwh.request_id=(select rwh1.request_id from request_workflow_history rwh1 where rwh1.id=?) and rwh.status=sm.id and upper(sm.status)=upper(?) and rwh.id!=?)";

			ps8 = con.prepareStatement(updateDelegatedStatus);
			ps8.setString(1, historyID);
			ps8.setString(2, CPSConstants.PENDING);
			ps8.setString(3, historyID);
			log.debug("::SQL : updateDelegatedStatus > > " + ps8);
			ps8.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps8, null);
		}
		return message;
	}

	/**
	 * This method will be called when a user approve the request that was assigned to him
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean approvedRequest(RequestBean rb) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>approvedRequest(RequestBean rb)>>>>>>>>>");
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
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>getDetailsAndProcessRequest(RequestBean rb)>>>>>>>>>");
		try {
			// Get the assigned sfid for a particular workflow & stage
			rb = workflowProcess.getWorkFlowAssignedSfid(rb,new RequestBean());
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
	public RequestBean checkPreviousWorkflows(RequestBean rb) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>checkPreviousWorkflows(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps9 = null;
		ResultSet rsq9 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String getPreviousWorkflows = "select tab1.id,tab1.workflow_id,rwh.request_stage,rwh.request_id from (select "
					+ "request_id,workflow_id,max(id) id from request_workflow_history where request_id=(select request_id "
					+ "from request_workflow_history where id=?) group by request_id, workflow_id) tab1,request_workflow_history rwh " + "where rwh.id=tab1.id and rwh.id!=? order by tab1.id desc";

			ps9 = con.prepareStatement(getPreviousWorkflows);
			ps9.setString(1, rb.getHistoryID());
			ps9.setString(2, rb.getHistoryID());
			log.debug("::SQL : getPreviousWorkflows > > " + ps9);
			rsq9 = ps9.executeQuery();
			while (rsq9.next()) {
				if (!CPSUtils.isNullOrEmpty(rsq9.getString("workflow_id"))) {
					rb.setWorkflowID(rsq9.getString("workflow_id"));
					rb.setStageID(rsq9.getString("request_stage"));
					rb.setRequestID(rsq9.getString("request_id"));
					rb.setStageID(String.valueOf((Integer.parseInt(rb.getStageID()) + 1)));
					/**
					 * check whether this workflow stage was completed are not
					 */
					if (checkNextWorkFlowStage(rb)) {
						rb = getDetailsAndProcessRequest(rb);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps9, rsq9);
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
	public String getHandOverWorkFlow(String workFlowID) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>getHandOverWorkFlow(String workFlowID)>>>>>>>>>");
		String handOverWorkflow = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps10 = null;
		ResultSet rsq10 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String getHandOverWorkFlow = "select to_workflow from workflow_master where workflow_id=? and status=1";
			ps10 = con.prepareStatement(getHandOverWorkFlow);
			ps10.setString(1, workFlowID);
			log.debug("::SQL : getHandOverWorkFlow > > " + ps10);
			rsq10 = ps10.executeQuery();
			if (rsq10.next()) {
				handOverWorkflow = rsq10.getString("to_workflow");
			}
			log.debug("::handOverWorkflow>>>>" + handOverWorkflow);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps10, rsq10);
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
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>checkNextWorkFlowStage(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps11 = null;
		ResultSet rsq11 = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String checkNextStage = "select workflow_id from workflow where workflow_id=? and stage_id=?";
			ps11 = con.prepareStatement(checkNextStage);
			ps11.setString(1, rb.getWorkflowID());
			ps11.setString(2, rb.getStageID());
			log.debug("::SQL : checkNextStage > > " + ps11);
			rsq11 = ps11.executeQuery();
			if (rsq11.next()) {
				status = true;
			}
			log.debug("::next workflow stage>>>>" + status);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps11, rsq11);
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
		log.debug("::<<<<<RequestProcess<<<<Method>>>>>>>>>>>>>>>checkAssignedWorkflow(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps12 = null;
		ResultSet rsq12 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String checkWorkflow = "select workflow_id,request_stage,request_id from request_workflow_history where id=?";
			ps12 = con.prepareStatement(checkWorkflow);
			ps12.setString(1, rb.getHistoryID());
			log.debug("::SQL : checkWorkflow > > " + ps12);
			rsq12 = ps12.executeQuery();
			if (rsq12.next()) {
				rb.setWorkflowID(rsq12.getString("workflow_id"));
				rb.setStageID(rsq12.getString("request_stage"));
				rb.setRequestID(rsq12.getString("request_id"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps12, rsq12);
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
		Connection con = null;
		PreparedStatement ps13 = null;
		ResultSet rsq13 = null;
		PreparedStatement ps14 = null;
		ResultSet rsq14 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			//String getSfid = "select erm.sfid,erm.org_role_id from emp_role_mapping erm,org_role_instance ori where ori.department_id=? and ori.status=1 and erm.org_role_id=ori.org_role_id and erm.status=1 and ori.is_head=1";
			 String getSfid = "select sfid,org_role_id from emp_role_mapping where org_role_id=? and status=1";
				
			ps13 = con.prepareStatement(getSfid);
			ps13.setString(1, rb.getInstanceId());
			rsq13 = ps13.executeQuery();
			if (rsq13.next()) {
				rb.setParentID(rsq13.getString("sfid"));
				rb.setRoleID(rsq13.getString("org_role_id"));

				String[] requests = rb.getRequestIDs().split(",");
				for (int i = 0; i < requests.length; i++) {
					rb.setRequestID(requests[i]);

					String getRequestDetails = "select id,workflow_id,request_type_id,request_stage from request_workflow_history where id=(select max(id) from request_workflow_history where request_id=?)";
					ps14 = con.prepareStatement(getRequestDetails);
					ps14.setString(1, rb.getRequestID());
					rsq14 = ps14.executeQuery();
					if (rsq14.next()) {
						rb.setWorkflowID(rsq14.getString("workflow_id"));
						rb.setRequestTypeID(rsq14.getString("request_type_id"));
						rb.setStageID(rsq14.getString("request_stage"));

						// update the status to delegate
						String deletegate = "update request_workflow_history set status=?,remarks=?,stage_status=? where id=?";
						ps14 = con.prepareStatement(deletegate);
						ps14.setString(1, CPSConstants.STATUSDELEGATED);
						ps14.setString(2, rb.getRemarks());
						ps14.setString(3, CPSConstants.STATUSDELEGATED);
						ps14.setString(4, rsq14.getString("id"));
						ps14.executeUpdate();

						rb = insertHistory(rb);

					}
				}
			}else {
				rb.setMessage(CPSConstants.FAILED);
				rb.setRemarks("Please assign head for this role");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps13, rsq13);
			ConnectionUtil.closeConnection(null, ps14, rsq14);
		}
		return rb;
	}
}
