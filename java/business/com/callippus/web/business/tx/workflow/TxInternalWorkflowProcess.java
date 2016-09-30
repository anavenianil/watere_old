package com.callippus.web.business.tx.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class TxInternalWorkflowProcess extends TxWorkflowProcess {
	private static Log log = LogFactory.getLog(TxInternalWorkflowProcess.class);
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * This method is for finding the parent id of that requester
	 * 
	 * @param rb
	 * @return
	 */
	public RequestBean getInternalDivisionWorkFlow(RequestBean rb) throws Exception {
		log.debug("::<<<<<TxInternalWorkflowProcess<<<<<<Method>>>>>>>>>>>>>>>getInternalDivisionWorkFlow(RequestBean rb)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			String sfID = (String) session
					.createSQLQuery(
							"select erm1.sfid from emp_role_mapping erm1,emp_role_mapping erm3,(select erm.sfid,emp.office_id,erm.parent_id from emp_role_mapping erm,emp_master emp "
									+ "where erm.status=1 and erm.sfid=? and emp.status=1 and emp.sfid=erm.sfid) erm2 where erm1.status=1 and erm1.sfid=erm2.parent_id and erm3.status=1 and erm3.sfid=erm1.parent_id and erm3.org_role_id=erm2.office_id and erm3.parent_id is null")
					.setString(0, rb.getSfID()).uniqueResult();
                    session.flush();
			if (!CPSUtils.isNullOrEmpty(sfID)) {
				rb.setParentID(sfID);
			}

			log.debug("::Parent ID>>>>>>" + rb.getParentID());
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
}
