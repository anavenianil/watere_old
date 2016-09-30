package com.callippus.web.business.workflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class InternalWorkflowProcess extends WorkflowProcess {
	private static Log log = LogFactory.getLog(InternalWorkflowProcess.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;	

	/**
	 * This method is for finding the parent id of that requester
	 * 
	 * @param rb
	 * @return
	 */
	public RequestBean getInternalDivisionWorkFlow(RequestBean rb) throws Exception {
		log.debug("::<<<<<InternalWorkflowProcess<<<<<<Method>>>>>>>>>>>>>>>getInternalDivisionWorkFlow(RequestBean rb)>>>>>>>>>");
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			/**
			 * This internal workflow is only for sub subordinates otherwise this query will return null (Immediate subordinates & instance head should not follow internal workflow )
			 */
			String getParentID = "select erm1.sfid from emp_role_mapping erm1,emp_role_mapping erm3,(select erm.sfid,emp.office_id,erm.parent_id from emp_role_mapping erm,emp_master emp "
					+ "where erm.status=1 and erm.sfid=? and emp.status=1 and emp.sfid=erm.sfid) erm2 where erm1.status=1 and erm1.sfid=erm2.parent_id and erm3.status=1 and erm3.sfid=erm1.parent_id and erm3.org_role_id=erm2.office_id and erm3.parent_id is null";
			ps = con.prepareStatement(getParentID);
			ps.setString(1, rb.getSfID());
			log.debug("::SQL:::::getParentID>>>>>>" + getParentID);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				rb.setParentID(rsq.getString("sfid"));
			}
			log.debug("::Parent ID>>>>>>" + rb.getParentID());
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return rb;
	}
}
