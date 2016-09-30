package com.callippus.web.dao.workflowmapping;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.WorkFlowMasterDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

@SuppressWarnings("serial")
@Service
public class SQLCreateWorkflowDAO implements ICreateWorkflowDAO, Serializable {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getWorkflowDetails(WorkFlowMappingBean workflowMapBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			// Get workfloe stage details
			workflowMapBean.setWorkflowDetails(session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", workflowMapBean.getNodeID())).addOrder(Order.asc("stageId")).list());

			// Get workflow details
			workflowMapBean.setToworkflow(((WorkFlowMasterDTO) session.get(WorkFlowMasterDTO.class, Integer.valueOf(workflowMapBean.getNodeID()))).getToWorkFlow());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMapBean;
	}

	public String submitNewWorkflow(WorkFlowMasterDTO workflowMasterDTO) throws Exception {
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(workflowMasterDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String deleteWorkflowStages(String nodeID) throws Exception {
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.createSQLQuery("delete workflow where workflow_id=?").setString(0, nodeID).executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String getWorkflowID(String workflowName) throws Exception {
		Session session = null;
		String nodeID = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			nodeID = String.valueOf(((WorkFlowMasterDTO) session.createCriteria(WorkFlowMasterDTO.class).add(Expression.eq(CPSConstants.NAME, workflowName)).uniqueResult()).getId());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return nodeID;
	}

	public String submitWorkflowStages(WorkFlowMappingBean workflowStagesDTO) throws Exception {
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(workflowStagesDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean checkAssignedRequests(WorkFlowMappingBean workflowStagesDTO) throws Exception {
		Session session = null;
		List<KeyValueDTO> list = null;
		workflowStagesDTO.setRemarks("");
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list = session.createSQLQuery(
					"select key,value from (select rm.request_type_id key,rm.request_type value from request_workflow_mapping rwm,request_master rm where "
							+ "rm.status=1 and rwm.status=1 and rwm.request_type_id=rm.request_type_id and rwm.workflow_id=? union "
							+ "select rm.request_type_id key,rm.request_type value from role_workflow_mapping rwm,request_master rm where "
							+ "rm.status=1 and rwm.status=1 and rwm.request_type_id=rm.request_type_id and rwm.workflow_id=?)").addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, workflowStagesDTO.getNodeID()).setString(1, workflowStagesDTO.getNodeID()).list();

			for (KeyValueDTO keyDTO : list) {
				workflowStagesDTO.setRemarks(workflowStagesDTO.getRemarks() + keyDTO.getValue() + ", ");
			}
			if (!CPSUtils.isNullOrEmpty(workflowStagesDTO.getRemarks())) {
				workflowStagesDTO.setRemarks(workflowStagesDTO.getRemarks().substring(0, workflowStagesDTO.getRemarks().length() - 2) + " request(s) mapped with this workflow.");
				workflowStagesDTO.setMessage(CPSConstants.FAILED);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowStagesDTO;
	}

	public String deleteWorkflow(WorkFlowMappingBean workflowStagesDTO) throws Exception {
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			WorkFlowMasterDTO workflowDTO = (WorkFlowMasterDTO) session.get(WorkFlowMasterDTO.class, Integer.valueOf(workflowStagesDTO.getNodeID()));
			workflowDTO.setStatus(0);
			workflowDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(workflowDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
}
