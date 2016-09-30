package com.callippus.web.business.workflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class UserLevelWorkflowProcess extends WorkflowProcess {
	private static Log log = LogFactory.getLog(UserLevelWorkflowProcess.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	/**
	 * This method will return the auto delegate sfid
	 * 
	 * @param sfid
	 * @return
	 * @throws Exception
	 */
	public RequestBean checkUserAutoDelegate(RequestBean rb) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		String sfid = null;
		String parentID = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			// get the requester sfid & gazetted type
			if (CPSUtils.isNull(rb.getRequester())) {
				String getGazType = "select dm.type,emp.sfid,emp.designation_id from emp_master emp,designation_mappings dm where emp.status=1 and "
						+ "emp.sfid=(select assigned_from from request_workflow_history where id=(select min(id) from "
						+ "request_workflow_history where request_id=(select request_id from request_workflow_history where id=?))) " + "and emp.designation_id=dm.desig_id";
				ps = con.prepareStatement(getGazType);
				ps.setString(1, rb.getHistoryID());
				rsq = ps.executeQuery();
				if (rsq.next()) {
					rb.setRequesterType(rsq.getString("type"));
					rb.setRequester(rsq.getString("sfid"));
					rb.setDesignationID(rsq.getString("designation_id"));
				}
			}

			sfid = rb.getSfID();
			parentID = rb.getParentID();

			rb = checkUserNormalConfiguration(rb, CPSConstants.AUTOMATIC);
			// check whether the requester & auto delegated sfid is same or not, if same we should not assign the request to the same person
			if (CPSUtils.compareStrings(rb.getParentID(), rb.getRequester())) {
				// same person
				rb.setSfID(sfid);
				rb.setParentID(parentID);
				rb.setRequestCount(null);
				rb = checkUserNormalConfiguration(rb, CPSConstants.ONLEAVE);
			}

			if (CPSUtils.compareStrings(parentID, rb.getParentID())) {
				rb = checkUserTreeConfiguration(rb, CPSConstants.AUTOMATIC);
				// check whether the requester & auto delegated sfid is same or not, if same we should not assign the request to the same person
				if (CPSUtils.compareStrings(rb.getParentID(), rb.getRequester())) {
					// same person
					rb.setSfID(sfid);
					rb.setParentID(parentID);
					rb.setRequestCount(null);
					rb = checkUserTreeConfiguration(rb, CPSConstants.ONLEAVE);
				}
			}
//			if (CPSUtils.compareStrings(parentID, rb.getParentID()) && CPSUtils.compareStrings(rb.getRoleID(), CPSConstants.ADMINROLEID)) {
//				rb = checkAdminConfiguration(rb, CPSConstants.AUTOMATIC);
//				// check whether the requester & auto delegated sfid is same or not, if same we should not assign the request to the same person
//				if (CPSUtils.compareStrings(rb.getParentID(), rb.getRequester())) {
//					// same person
//					rb.setSfID(sfid);
//					rb.setParentID(parentID);
//					rb.setRequestCount(null);
//					rb = checkAdminConfiguration(rb, CPSConstants.ONLEAVE);
//				}
//			}

			log.debug("::Delegated from " + rb.getSfID() + " to " + rb.getParentID());
			if (!CPSUtils.isNullOrEmpty(rb.getRequestCount())) {
				// update the count
				String updatecount = "update user_specific_configuration set no_of_requests=(select no_of_requests+1 from user_specific_configuration where id=?) where id=? ";
				ps = con.prepareStatement(updatecount);
				ps.setString(1, rb.getRequestCount());
				ps.setString(2, rb.getRequestCount());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return rb;
	}

//	public RequestBean checkAdminConfiguration(RequestBean rb, String assignType) throws Exception {
//		Session session2 = null;
//		Connection con2 = null;
//		PreparedStatement ps2 = null;
//		ResultSet rsq2 = null;
//		try {
//			session2 = hibernateUtils.getSession(); //session2 = sessionFactory.openSession();
//			con2 = session2.connection();
//
//			String getDelegatedID = "select id,delegate,gazetted_type,delegation_type,case when delegation_type=? then delegate||'' else null end roleID from (select usc.id,usc.delegate,usc.gazetted_type,usc.delegation_type from emp_master emp,configuration_details con,user_specific_configuration usc,status_master sm "
//					+ "where emp.sfid=? and emp.status=1 and upper(con.name)=upper('admin') and con.value=? and con.value=usc.org_role_id and usc.status=1 and "
//					+ "usc.request_type_id=? and (upper(usc.gazetted_type)=upper(?) or usc.gazetted_type is null) and (usc.designation_id=? or usc.designation_id=0) and sm.id=usc.assigned_type  and upper(sm.status)=upper(?) order by usc.no_of_requests) where rownum=1";
//			ps2 = con2.prepareStatement(getDelegatedID);
//			ps2.setString(1, CPSConstants.STATUSINSTANCE);
//			ps2.setString(2, rb.getParentID());
//			ps2.setString(3, rb.getRoleID());
//			ps2.setString(4, rb.getRequestTypeID());
//			ps2.setString(5, rb.getRequesterType());
//			ps2.setString(6, rb.getDesignationID());
//			ps2.setString(7, assignType);
//			rsq2 = ps2.executeQuery();
//			if (rsq2.next()) {
//				if (CPSUtils.isNullOrEmpty(rsq2.getString("gazetted_type")) || CPSUtils.compareStrings(rb.getRequesterType(), rsq2.getString("gazetted_type"))) {
//					rb.setSfID(rb.getParentID());
//					if (CPSUtils.compareStrings(rsq2.getString("delegation_type"), CPSConstants.STATUSINSTANCE)) {
//						// INSTANCE
//						rb.setParentID(getHead(rsq2.getString("delegate")));
//					} else {
//						rb.setParentID(rsq2.getString("delegate"));
//					}
//					rb.setRequestCount(rsq2.getString("id"));
//					rb.setRoleID(rsq2.getString("roleID"));
//				}
//			}
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			ConnectionUtil.closeConnection(session2, ps2, rsq2);
//		}
//		return rb;
//	}

	public RequestBean checkUserNormalConfiguration(RequestBean rb, String assignType) throws Exception {
		Session session2 = null;
		Connection con2 = null;
		PreparedStatement ps2 = null;
		ResultSet rsq2 = null;
		try {
			session2 = hibernateUtils.getSession(); //session2 = sessionFactory.openSession();
			con2 = session2.connection();

			String getDelegatedID = "select id,delegate,gazetted_type,delegation_type,case when delegation_type=? then delegate||'' else null end roleID from (select usc.id,usc.delegate,usc.gazetted_type,usc.delegation_type "
					+ "from user_specific_configuration usc,status_master sm where usc.org_role_id=?   "
					+ "and usc.status=1 and sm.id=usc.assigned_type and upper(sm.status)=upper(?) and usc.request_type_id=? "
					+ "and (upper(usc.gazetted_type)=upper(?) or usc.gazetted_type is null) and (usc.designation_id=? or usc.designation_id=0) order by usc.no_of_requests) where rownum=1";
			ps2 = con2.prepareStatement(getDelegatedID);
			ps2.setString(1, CPSConstants.STATUSINSTANCE);
			ps2.setString(2, rb.getRoleID());
			ps2.setString(3, assignType);
			ps2.setString(4, rb.getRequestTypeID());
			ps2.setString(5, rb.getRequesterType());
			ps2.setString(6, rb.getDesignationID());
			rsq2 = ps2.executeQuery();
			if (rsq2.next()) {
				if (CPSUtils.isNullOrEmpty(rsq2.getString("gazetted_type")) || CPSUtils.compareStrings(rb.getRequesterType(), rsq2.getString("gazetted_type"))) {
					rb.setSfID(rb.getParentID());

					if (CPSUtils.compareStrings(rsq2.getString("delegation_type"), CPSConstants.STATUSINSTANCE)) {
						// INSTANCE
						rb.setParentID(getHead(rsq2.getString("delegate")));
					} else {
						rb.setParentID(rsq2.getString("delegate"));
					}
					rb.setRequestCount(rsq2.getString("id"));
					rb.setRoleID(rsq2.getString("roleID"));
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session2, ps2, rsq2);
		}
		return rb;
	}

	public RequestBean checkUserTreeConfiguration(RequestBean rb, String assignType) throws Exception {
		Session session2 = null;
		Connection con2 = null;
		PreparedStatement ps2 = null;
		ResultSet rsq2 = null;
		try {
			session2 = hibernateUtils.getSession(); //session2 = sessionFactory.openSession();
			con2 = session2.connection();

			String getDelegatedID = "select id,delegate,gazetted_type,delegation_type,case when delegation_type=? then delegate||'' else null end roleID from (select id,delegate,gazetted_type,delegation_type "
					+ "from user_specific_configuration where id=(select case when (select org_role_id from org_role_instance where status=1 and "
					+ "org_role_id=? start with org_role_id=(select office_id from emp_master where sfid=? and status=1) "
					+ "connect by parent_org_role_id = prior org_role_id) is not null then (select usc.id from user_specific_configuration usc,status_master sm "
					+ "where usc.org_role_id=? and usc.status=1 and usc.assigned_type=sm.id "
					+ "and upper(sm.status)=upper(?) and usc.request_type_id=? and (upper(usc.gazetted_type)=upper(?) or usc.gazetted_type is null) and (usc.designation_id=? or usc.designation_id=0) and rownum=1) else "
					+ "(select case when (select org_role_id from org_role_instance where status=1 and org_role_id=? "
					+ "start with org_role_id=(select office_id from emp_master where sfid=? and status=1) connect by org_role_id = prior parent_org_role_id) is not null "
					+ "then (select usc.id from user_specific_configuration usc,status_master sm where usc.org_role_id=? "
					+ " and usc.status=1 and usc.assigned_type=sm.id and upper(sm.status)=upper(?) and usc.request_type_id=? and "
					+ "(upper(usc.gazetted_type)=upper(?) or usc.gazetted_type is null) and (usc.designation_id=? or usc.designation_id=0) and rownum=1) else null end from dual ) end from dual) order by no_of_requests) where rownum=1";
			ps2 = con2.prepareStatement(getDelegatedID);
			ps2.setString(1, CPSConstants.STATUSINSTANCE);
			ps2.setString(2, rb.getRoleID());
			ps2.setString(3, rb.getSfID());
			ps2.setString(4, rb.getRoleID());
			// ps2.setString(4, rb.getParentID());
			ps2.setString(5, assignType);
			ps2.setString(6, rb.getRequestTypeID());
			ps2.setString(7, rb.getRequesterType());
			ps2.setString(8, rb.getDesignationID());
			ps2.setString(9, rb.getRoleID());
			ps2.setString(10, rb.getSfID());
			ps2.setString(11, rb.getRoleID());
			// ps2.setString(12, rb.getParentID());
			ps2.setString(12, assignType);
			ps2.setString(13, rb.getRequestTypeID());
			ps2.setString(14, rb.getRequesterType());
			ps2.setString(15, rb.getDesignationID());
			log.debug("::SQL : getDelegatedID > > " + ps2);
			rsq2 = ps2.executeQuery();
			if (rsq2.next()) {
				if (CPSUtils.isNullOrEmpty(rsq2.getString("gazetted_type")) || CPSUtils.compareStrings(rb.getRequesterType(), rsq2.getString("gazetted_type"))) {
					rb.setSfID(rb.getParentID());
					if (CPSUtils.compareStrings(rsq2.getString("delegation_type"), CPSConstants.STATUSINSTANCE)) {
						// INSTANCE
						rb.setParentID(getHead(rsq2.getString("delegate")));
					} else {
						rb.setParentID(rsq2.getString("delegate"));
					}
					rb.setRequestCount(rsq2.getString("id"));
					rb.setRoleID(rsq2.getString("roleID"));
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session2, ps2, rsq2);
		}
		return rb;
	}

	public String getHead(String roleInstanceID) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rsq1 = null;
		String parentID = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String getParentSFID = "select sfid from emp_role_mapping where status=1 and org_role_id=?";
			ps1 = con.prepareStatement(getParentSFID);
			ps1.setString(1, roleInstanceID);
			rsq1 = ps1.executeQuery();
			if (rsq1.next()) {
				parentID = rsq1.getString("sfid");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return parentID;
	}
}
