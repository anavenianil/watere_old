package com.callippus.web.business.workflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class WorkflowProcess {
	private static Log log = LogFactory.getLog(WorkflowProcess.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * This method will find the workflow assigned sfid
	 * 
	 * @param sfid
	 * @param type
	 * @return
	 * @throws Exception
	 */

	public String getAssignedID(String sfid, String type, String requesterOfficeID, String requestFrom, String requesterRoleID, String requestID) throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getAssignedID(String sfid, String type)>>>>>>>>>");
		String assignedID = null;
		try {
			log.debug("::sfid>>>" + sfid + " ::::& Role ID>>>>" + type);
			if (CPSUtils.compareStrings(type, CPSConstants.BOSSID)) {
				// BOSS
				assignedID = getBossID(sfid, requesterRoleID, requesterOfficeID);
			} else if (CPSUtils.compareStrings(type, CPSConstants.BOSSESBOSS)) {
				// BOSSES BOSS
				assignedID = getBossesBossID(sfid, requesterRoleID);
			} else if (CPSUtils.compareStrings(type, CPSConstants.ADMINID)) {
				// ADMIN
				assignedID = getAdminID();
			} else if (CPSUtils.compareStrings(type, CPSConstants.REQUESTERID)) {
				// REQUESTER
				assignedID = getRequesterID(requestID);
			} else {
				// 1-DIRECTOR,3-PROGRAMME DIRECTOR,5-ASSOCIATE DIRECTOR,7-PROJECT MANAGER,9-TECHNICAL DIRECTORATE,13-DIVISION HEAD
				assignedID = getHeadSfid(sfid, type, requesterOfficeID, requestFrom, requesterRoleID);
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::assigned ID>>>>" + assignedID);
		return assignedID;
	}

	/**
	 * This method will return the requester SFID
	 * 
	 * @param requestID
	 * @return
	 * @throws Exception
	 */
	public String getRequesterID(String requestID) throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getHeadSfid(String sfid, String roleID)>>>>>>>>>");
		String requesterID = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rsq1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String sql = "select assigned_from||'#'||'' assigned_from from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?)";
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, requestID);
			rsq1 = ps1.executeQuery();
			if (rsq1.next()) {
				requesterID = rsq1.getString("assigned_from");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return requesterID;
	}

	/**
	 * This method will return the current employee boss in the tree that the boss contains particular role
	 * 
	 * @param sfid
	 * @param roleID
	 * @return
	 * @throws Exception
	 */
	public String getHeadSfid(String sfid, String roleID, String requesterOfficeID, String requestFrom, String requesterRoleID) throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getHeadSfid(String sfid, String roleID)>>>>>>>>>");
		String headID = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rsq1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			StringBuffer sb = new StringBuffer();
			sb
					.append("select sfid||'#'||org_role_id sfid from emp_role_mapping where org_role_id in (select org_role_id from org_role_instance where status=1 and role_hierarchy_id=? start with org_role_id=");
			if (CPSUtils.compareStrings(requestFrom, CPSConstants.APPROVED)) {
				sb.append(requesterOfficeID);
			} else if (!CPSUtils.isNull(requesterRoleID)) {
				sb.append(requesterRoleID);
			} else {
				sb.append("(select office_id from emp_master where status=1 and sfid='" + sfid + "')");
			}

			sb.append(" connect by org_role_id = prior parent_org_role_id) and status=1");

			ps1 = con.prepareStatement(sb.toString());
			ps1.setString(1, roleID);
			log.debug("::SQL : getheadID > > " + ps1);
			rsq1 = ps1.executeQuery();
			if (rsq1.next()) {
				headID = rsq1.getString("sfid");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return headID;
	}

	/**
	 * This method will return the Admin SFID
	 * 
	 * @param sfid
	 * @return
	 * @throws Exception
	 */
	public String getAdminID() throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getAdminID(String sfid)>>>>>>>>>");
		String adminID = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rsq2 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			/**
			 * Get the admin configured instance id from the configuration table
			 */
			String getAdminID = "select sfid||'#'||org_role_id sfid from emp_role_mapping where org_role_id=(select value from configuration_details where upper(name)=upper(?)) and status=1";
			ps2 = con.prepareStatement(getAdminID);
			ps2.setString(1, CPSConstants.ADMIN);
			log.debug("::SQL : getAdminID > > " + ps2);
			rsq2 = ps2.executeQuery();
			if (rsq2.next()) {
				adminID = rsq2.getString("sfid");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps2, rsq2);
		}
		return adminID;
	}

	/**
	 * This method will return the bosses boss id of the current sfid
	 * 
	 * @param sfid
	 * @return
	 * @throws Exception
	 */
	public String getBossesBossID(String sfid, String requesterRoleID) throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getBossesBossID(String sfid)>>>>>>>>>");
		String bossesBossID = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps3 = null;
		ResultSet rsq3 = null;
		String getBossesBossID = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			if (CPSUtils.isNull(requesterRoleID)) {

				/**
				 * First check whether the employee is an head or not If the employee is an head then get the upper tree start with office id (default role) and level is 3. If the employee is a normal
				 * employee then get his bosses boss with his office id(default role)
				 */

				getBossesBossID = "select case when (select count(*) from emp_master emp,emp_role_mapping erm where emp.status=1 and erm.status=1 and erm.sfid=emp.sfid and emp.sfid=? "
						+ "and emp.office_id=erm.org_role_id and erm.parent_id is null)>0 then (select sfid||'#'||org_role_id from emp_role_mapping where org_role_id=(select ori.org_role_id from org_role_instance ori where ori.status=1 and level=3 start with "
						+ "ori.org_role_id=(select emp.office_id from emp_master emp,emp_role_mapping erm where emp.status=1 and erm.status=1 and erm.sfid=emp.sfid and emp.sfid=? and emp.office_id=erm.org_role_id) connect by ori.org_role_id=prior ori.parent_org_role_id) "
						+ "and status=1) else (select erm1.sfid||'#'||erm1.org_role_id from emp_master emp,emp_role_mapping erm,org_role_instance ori,emp_role_mapping erm1 where emp.status=1 and erm.status=1 and emp.sfid=? and erm.org_role_id=emp.office_id and ori.status=1 and ori.org_role_id=erm.org_role_id "
						+ "and erm1.status=1 and erm1.org_role_id=ori.parent_org_role_id) end parentId from dual";
				ps3 = con.prepareStatement(getBossesBossID);
				ps3.setString(1, sfid);
				ps3.setString(2, sfid);
				ps3.setString(3, sfid);
			} else {
				getBossesBossID = "select sfid||'#'||org_role_id parentId from emp_role_mapping where org_role_id=(select ori.org_role_id from org_role_instance ori where ori.status=1 and level=3 start with ori.org_role_id=? "
						+ "connect by ori.org_role_id=prior ori.parent_org_role_id) and status=1";
				ps3 = con.prepareStatement(getBossesBossID);
				ps3.setString(1, requesterRoleID);

			}

			log.debug("::SQL : getBossesBossID > > " + ps3);
			rsq3 = ps3.executeQuery();
			if (rsq3.next()) {
				bossesBossID = rsq3.getString("parentId");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps3, rsq3);
		}
		return bossesBossID;
	}

	/**
	 * This method will decide the employee boss
	 * 
	 * @param sfid
	 * @return parent id
	 * @throws Exception
	 */
	public String getBossID(String sfid, String requesterRoleID, String requesterOfficeID) throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getBossID(String sfid)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps4 = null;
		ResultSet rsq4 = null;
		String bossID = null;
		String getBossID = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			if (CPSUtils.isNull(requesterRoleID)) {
				// First get the requesterOfficeID tree
				getBossID = "select erm.org_role_id from emp_role_mapping erm where erm.status=1 and erm.org_role_id in ("
						+ "select ori.org_role_id from org_role_instance ori where ori.status = 1	start with ori.org_role_id=? connect by ori.org_role_id = prior ori.parent_org_role_id) and erm.sfid=?";
				ps4 = con.prepareStatement(getBossID);
				ps4.setString(1, requesterOfficeID);
				ps4.setString(2, sfid);
				rsq4 = ps4.executeQuery();
				if (rsq4.next()) {
					getBossID = "select erm.sfid||'#'||erm.org_role_id parentID from org_role_instance ori,emp_role_mapping erm where ori.status=1 and ori.org_role_id=? and erm.status=1 and erm.org_role_id=ori.parent_org_role_id";
					ps4 = con.prepareStatement(getBossID);
					ps4.setString(1, rsq4.getString("org_role_id"));
				} else {
					getBossID = "select case when (select count(*) from emp_role_mapping erm,emp_master emp where erm.sfid=? and erm.status=1 and emp.status=1 and emp.sfid=erm.sfid and erm.org_role_id=emp.office_id)>0 "
							+ "then (select sfid||'#'||org_role_id from emp_role_mapping where status=1 and org_role_id=(select ori.parent_org_role_id from emp_role_mapping erm,emp_master emp,org_role_instance ori where erm.sfid=? and erm.status=1 and emp.status=1 "
							+ "and emp.sfid=erm.sfid and erm.org_role_id=emp.office_id and ori.status=1 and ori.org_role_id=erm.org_role_id)) else (select erm.sfid||'#'||erm.org_role_id from emp_role_mapping erm where erm.status=1 and erm.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 "
							+ "and emp.sfid=?)) end parentID from dual";

					ps4 = con.prepareStatement(getBossID);
					ps4.setString(1, sfid);
					ps4.setString(2, sfid);
					ps4.setString(3, sfid);
				}
			} else {
				getBossID = "select erm.sfid||'#'||erm.org_role_id parentID from org_role_instance ori,emp_role_mapping erm where ori.org_role_id=? and ori.status=1 and erm.status=1 and erm.org_role_id=ori.parent_org_role_id";
				ps4 = con.prepareStatement(getBossID);
				ps4.setString(1, requesterRoleID);
			}
			log.debug("::SQL : getBossID > > " + ps4);
			rsq4 = ps4.executeQuery();
			if (rsq4.next()) {
				bossID = rsq4.getString("parentID");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps4, rsq4);
		}
		return bossID;
	}

	/**
	 * Get the SFID of an instance
	 * 
	 * @param instanceID
	 * @return
	 * @throws Exception
	 */
	public String getInstanceSFID(String instanceID) throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getInstanceSFID(String instanceID)>>>>>>>>>");
		String sfid = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps5 = null;
		ResultSet rsq5 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String getInstanceSfid = "select sfid from emp_role_mapping where org_role_id=? and status=1";
			ps5 = con.prepareStatement(getInstanceSfid);
			ps5.setString(1, instanceID);
			log.debug("::SQL : getInstanceSfid > > " + ps5);
			rsq5 = ps5.executeQuery();
			if (rsq5.next()) {
				sfid = rsq5.getString("sfid") + "#" + instanceID;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps5, rsq5);
		}
		return sfid;
	}

	/**
	 * This method will decide the workflow id depends on workflow type(Generic/Role)& request type(PIS/Leave/...)
	 * 
	 * @param workFlowType
	 * @param requestType
	 * @return
	 * @throws Exception
	 */
	public String decideWorkFlow(String requestType, String sfID, String orgROleID) throws Exception {
		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>decideWorkFlow(String workFlowType, String requestType)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps6 = null;
		ResultSet rsq6 = null;
		String workflowID = null;
		String getWorkFlowID = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			if (!CPSUtils.isNull(orgROleID)) {
				// Some of the employees are having two roles, In this case from which role the employee is requesting
				getWorkFlowID = "select workflow_id from role_workflow_mapping where org_role_id=? and request_type_id=? and status=1";
				ps6 = con.prepareStatement(getWorkFlowID);
				ps6.setString(1, orgROleID);
				ps6.setString(2, requestType);
			} else if (CPSUtils.isNull(sfID)) {
				// generic workflow
				getWorkFlowID = "select workflow_id from request_workflow_mapping where status=1 and request_type_id=?";
				ps6 = con.prepareStatement(getWorkFlowID);
				ps6.setString(1, requestType);
			} else {
				// role based workflow
				getWorkFlowID = "select workflow_id from role_workflow_mapping where org_role_id=(select emp.office_id from emp_master emp,emp_role_mapping erm "
						+ "where emp.sfid=? and emp.status=1 and erm.status=1 and erm.sfid=emp.sfid  and erm.parent_id is null and erm.org_role_id=emp.office_id) and request_type_id=? and status=1";
				ps6 = con.prepareStatement(getWorkFlowID);
				ps6.setString(1, sfID);
				ps6.setString(2, requestType);
			}
			log.debug("::SQL : getWorkFlowID > > " + ps6);
			rsq6 = ps6.executeQuery();
			if (rsq6.next()) {
				workflowID = rsq6.getString("workflow_id");
			}
			log.debug("::::Request Type>>>>>" + requestType + "::::::Assigned Workflow ID>>>>" + workflowID);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps6, rsq6);
		}
		return workflowID;
	}

	/**
	 * This method will return an object that contains assigned sfid of a particular workflow
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public RequestBean getWorkFlowAssignedSfid(RequestBean rb, RequestBean rb1) throws Exception {
 		log.debug("::<<<<<<WorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getWorkFlowAssignedSfid(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps7 = null;
		ResultSet rsq7 = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			if (CPSUtils.isNullOrEmpty(rb1.getPrevsfID())) {
				rb1.setPrevsfID(rb.getSfID());
				rb1.setFlag(true);
			}

			if (CPSUtils.isNull(rb.getRequesterOfficeID())) {
				rb.setRequesterOfficeID(getRequesterOfficeID(rb.getRequestID(), rb.getSfID()));
			}

			String getWorkFlowDetails = "select w.workflow_id,w.to_role,w.stage_id,w.relationship_type from workflow w where w.workflow_id=? and w.stage_id=?";
			ps7 = con.prepareStatement(getWorkFlowDetails);
			ps7.setString(1, rb.getWorkflowID());
			ps7.setString(2, rb.getStageID());
			log.debug("::SQL : getWorkFlowDetails > > " + ps7);
			rsq7 = ps7.executeQuery();
			if (rsq7.next()) {

				/**
				 * Check whether the relationship type is absolute or relative. If the relationship is absolute, then to_role column contains the instance id, so get the sfid mapped to that instance
				 * else if the relationship type is relative find the assigned sfid depends on the to_role column else if the relationship type is department find the department head role
				 */
				if (CPSUtils.compareStrings(rsq7.getString("relationship_type"), CPSConstants.WORKFLOWRELATIONRELID)) {
					// RELATIVE
					result = getAssignedID(rb.getSfID(), rsq7.getString("to_role"), rb.getRequesterOfficeID(), CPSConstants.APPROVED, rb.getOrgRoleID(), rb.getRequestID());
					if (!CPSUtils.isNullOrEmpty(result)) {
						rb.setParentID(result.split("#")[0]);
						rb.setRoleID(result.split("#")[1]);
					}

				} else if (CPSUtils.compareStrings(rsq7.getString("relationship_type"), CPSConstants.WORKFLOWRELATIONABSID)) {
					// ABSOLUTE
					result = getInstanceSFID(rsq7.getString("to_role"));
					if (!CPSUtils.isNullOrEmpty(result)) {
						rb.setParentID(result.split("#")[0]);
						rb.setRoleID(result.split("#")[1]);
					}
				} else if (CPSUtils.compareStrings(rsq7.getString("relationship_type"), CPSConstants.WORKFLOWRELATIONDEPID)) {
					// DEPARTMENT
					result = getDependentDetails(rsq7.getString("to_role"), rb);
					if (!CPSUtils.isNullOrEmpty(result)) {
						rb.setParentID(result.split("#")[0]);
						rb.setRoleID(result.split("#")[1]);
					}
				}

				/**
				 * Iterate this loop till the parent ID is different and the requester & assigned employee is different.
				 */
				if (CPSUtils.isNullOrEmpty(rb1.getParentID()) || CPSUtils.compareStrings(rb1.getParentID(), rb.getParentID())
						|| checkRequestAssigned(rb.getRequestID(), rb.getParentID(), rb.getRoleID())) {
					if (CPSUtils.isNullOrEmpty(rb1.getSfID())) {
						rb1.setSfID(rb.getSfID());
						rb1.setRequesterOfficeID(rb.getRequesterOfficeID());
					}
					rb.setSfID(rb.getParentID());
					rb.setStageID(String.valueOf((Integer.parseInt(rb.getStageID()) + 1)));

					rb1.setParentID(rb.getParentID());
					rb1.setRoleID(rb.getRoleID());
					rb1.setStageID(String.valueOf((Integer.parseInt(rb.getStageID()))));

					rb.setRequesterOfficeID(rb.getRoleID());
					rb = getWorkFlowAssignedSfid(rb, rb1);
				} else {
					rb.setSfID(rb1.getSfID());
					rb.setRequesterOfficeID(rb1.getRequesterOfficeID());
					rb.setParentID(rb1.getParentID());
					rb.setRoleID(rb1.getRoleID());
					rb.setStageID(String.valueOf(Integer.parseInt(rb.getStageID()) - 1));
				}
			}

			if (CPSUtils.compareStrings(rb.getSfID(), rb.getParentID())) {
				rb.setSfID(rb1.getSfID());
				rb.setRequesterOfficeID(rb1.getRequesterOfficeID());
				rb.setStageID(String.valueOf(Integer.parseInt(rb.getStageID()) - 1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps7, rsq7);
		}
		return rb;
	}

	/**
	 * Returns the sfid & their role ID.
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public String getDependentDetails(String toRole, RequestBean rb) throws Exception {
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.EMPTRANSFERREQUESTTYPEID) || CPSUtils.compareStrings(rb.getRequestTypeID(), CPSConstants.SELFTRANSFERREQUESTTYPEID)) {
				EmpTransferBean empTransferBean = (EmpTransferBean) session.createCriteria(EmpTransferBean.class).add(Expression.eq("requestID", rb.getRequestID())).uniqueResult();
				if (empTransferBean.getTransferTypeID() == Integer.valueOf(CPSConstants.INTERNALSTATUSID)) {
					// Request type is internal transfer request, so we should get the mapped department employee and his role
					result = (String) session
							.createSQLQuery(
									"select erm.sfid||'#'||ori.org_role_id from org_role_instance ori,emp_role_mapping erm where ori.status=1 and ori.department_id=(select department_to from transfer_request_details where request_id=? and status=1) "
											+ "and ori.is_head=1 and erm.status=1 and erm.org_role_id=ori.org_role_id").setString(0, empTransferBean.getRequestID()).uniqueResult();
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	/**
	 * This method will return the requester office ID
	 * 
	 * @param requestID
	 * @return
	 * @throws Exception
	 */
	public String getRequesterOfficeID(String requestID, String sfid) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps8 = null;
		ResultSet rsq8 = null;
		String requesterOfficeID = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String getRequesterOfficeID = "select case when (select count(*) from request_workflow_history where id=(select min(id) "
					+ "from request_workflow_history where request_id=?))!=0 then (select office_id from emp_master where sfid=(select assigned_from "
					+ "from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?)) and status=1) "
					+ "else (select office_id from emp_master where sfid=? and status=1) end officeID from dual";
			ps8 = con.prepareStatement(getRequesterOfficeID);
			ps8.setString(1, requestID);
			ps8.setString(2, requestID);
			ps8.setString(3, sfid);
			rsq8 = ps8.executeQuery();
			if (rsq8.next()) {
				requesterOfficeID = rsq8.getString("officeID");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps8, rsq8);
		}
		return requesterOfficeID;
	}

	/**
	 * This method will return true, if the request was not assigned earlier, otherwise it will return false
	 * 
	 * @param requestID
	 * @param parentID
	 * @return
	 * @throws Exception
	 */

	public boolean checkRequestAssigned(String requestID, String parentID, String roleID) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps9 = null;
		ResultSet rsq9 = null;
		PreparedStatement ps10 = null;
		ResultSet rsq10 = null;
		boolean requestFlag = false;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String checkRequestAssigned = "select id from request_workflow_history where request_id=? and assigned_to=? and assigned_role_id=?";
			ps9 = con.prepareStatement(checkRequestAssigned);
			ps9.setString(1, requestID);
			ps9.setString(2, parentID);
			ps9.setString(3, roleID);
			rsq9 = ps9.executeQuery();
			if (rsq9.next()) {
				requestFlag = true;
			} else {
				// check whether the requester is same as parentID
				// If the request is from doms we can allow the request for filing
				checkRequestAssigned = "select case when (select count(*) from emp_role_mapping where status=1 and sfid=? and org_role_id=(select value from configuration_details where name=?))>0 "
						+ "then 'false' when (select assigned_from from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?))=? then 'true' else 'false' end res from dual";
				ps10 = con.prepareStatement(checkRequestAssigned);
				ps10.setString(1, parentID);
				ps10.setString(2, CPSConstants.ADMIN);
				ps10.setString(3, requestID);
				ps10.setString(4, parentID);
				rsq10 = ps10.executeQuery();
				if (rsq10.next()) {
					if (CPSUtils.compareStrings(CPSConstants.TRUE, rsq10.getString("res"))) {
						requestFlag = true;
					}
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps9, rsq9);
			ConnectionUtil.closeConnection(null, ps10, rsq10);
		}
		return requestFlag;
	}

}
