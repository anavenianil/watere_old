package com.callippus.web.business.domainobject;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.requests.PISRequestBean;
import com.callippus.web.business.tx.workflow.TxDomainObject;
import com.callippus.web.controller.common.CPSConstants;

@Service
public class PISDomainObject extends TxDomainObject {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * This method will update the txn employee details
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String updateTxnDetails(PISRequestBean prb) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			List<WorkflowTxnDTO> list = session.createCriteria(WorkflowTxnDTO.class).add(Expression.eq("requestID", prb.getRequestID())).list();

			StringBuffer updateEmpTxnDetails = new StringBuffer();
			updateEmpTxnDetails.append("update emp_master set ");

			for (WorkflowTxnDTO workflowTxnDTO : list) {
				updateEmpTxnDetails.append(workflowTxnDTO.getColumnName() + "='" + workflowTxnDTO.getNewValue() + "',");
				prb.setReferenceNumber(workflowTxnDTO.getReferenceNumber());
			}

			String txnDetails = updateEmpTxnDetails.toString().substring(0, updateEmpTxnDetails.toString().length() - 1) + " where sfid=?";
			session.createSQLQuery(txnDetails).setString(0, prb.getReferenceNumber()).executeUpdate();

			prb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return prb.getMessage();
	}
}