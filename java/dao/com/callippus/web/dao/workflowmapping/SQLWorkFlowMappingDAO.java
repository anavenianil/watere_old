package com.callippus.web.dao.workflowmapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.dto.CghsEmergencyRequestDTO;
import com.callippus.web.beans.dto.CghsReimbursementRequestDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.ReqDesigWorkflowMappingDTO;
import com.callippus.web.beans.dto.ReqOrgWorkflowMappingDTO;
import com.callippus.web.beans.dto.ReqRoleWorkMappingDTO;
import com.callippus.web.beans.dto.ReqWorkMappingDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.dto.TxnDetailsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.higherQualification.dto.HQRequestDTO;
import com.callippus.web.beans.nominee.NomineeBean;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.beans.requests.CGHSRequestProcessBean;
import com.callippus.web.beans.requests.CancelLeaveRequestBean;
import com.callippus.web.beans.requests.ConvertLeaveRequestBean;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.hrdg.training.request.TrainingRequestProcess;
import com.callippus.web.business.requestprocess.ErpLoanRequestProcess;
import com.callippus.web.business.requestprocess.FPARequestProcess;
import com.callippus.web.business.requestprocess.HQRequestProcess;
import com.callippus.web.business.requestprocess.LTCWaterRequestProcess;
import com.callippus.web.business.requestprocess.LeaveWaterRequestProcess;
import com.callippus.web.business.requestprocess.LoanHBARequestProcess;
import com.callippus.web.business.requestprocess.LoanRequestProcess;
import com.callippus.web.business.requestprocess.PassportRequestProcess;
import com.callippus.web.business.requestprocess.QuarterRequestProcess;
import com.callippus.web.business.requestprocess.TadaRequestProcess;
import com.callippus.web.business.requestprocess.TadaWaterRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.leave.dto.LeaveRequestExceptionsDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.dto.LtcAdvanceRequestDTO;
import com.callippus.web.ltc.dto.LtcApprovalRequestDTO;
import com.callippus.web.ltc.dto.LtcJourneyDetailsDTO;
import com.callippus.web.ltc.dto.LtcMemberDetailsDTO;
import com.callippus.web.ltc.dto.LtcRefundRequestDTO;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;
import com.callippus.web.mmg.cashbuildup.beans.InvoiceRequestBean;
import com.callippus.web.mmg.cashbuildup.beans.VoucherMasterBean;
import com.callippus.web.mmg.cashbuildup.beans.VoucherRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.FundsAllotedDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.IRItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.VoucherItemDetailsDTO;
import com.callippus.web.tada.beans.request.TadaRequestBean;

@Service
public  class SQLWorkFlowMappingDAO implements IWorkFlowMappingDAO, Serializable {
	private static final long serialVersionUID = -6728048066023585848L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private ErpLoanRequestProcess erpLoanRequestProcess;
	@Autowired
	private LoanRequestProcess loanRequestProcess;
	@Autowired
	private LoanHBARequestProcess loanHBARequestProcess;
	@Autowired
	private QuarterRequestProcess quarterRequestProcess;
	@Autowired
	HQRequestProcess hqRequestProcess;
	@Autowired
	private FPARequestProcess fpaRequestProcess;
	@Autowired
	private PassportRequestProcess passportRequestProcess;
	@Autowired
	private TadaRequestProcess tadaRequestProcess;
	@Autowired
	private TadaWaterRequestProcess tadaWaterRequestProcess;
	@Autowired
	private LTCWaterRequestProcess ltcWaterRequestProcess;
	@Autowired
	private LeaveWaterRequestProcess leaveWaterRequestProcess;
	@Autowired
	private TrainingRequestProcess trainingRequestProcess;
	
	private static Log log = LogFactory.getLog(SQLWorkFlowMappingDAO.class);
	
	@Override
	public List getAllList() throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		List<WorkFlowMappingBean> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select rrwm.id,org_role_name,rrwm.org_role_id,rm.request_type,rrwm.request_type_id, wm.workflow_id,wm.workflow_name "
					+ "from role_workflow_mapping rrwm,org_role_instance oi,request_master rm,workflow_master wm WHERE wm.workflow_id = rrwm.workflow_id and "
					+ "rrwm.org_role_id = oi.org_role_id and rrwm.request_type_id=rm.request_type_id and  rrwm.status=1";
			ps = con.prepareStatement(sql);
			rsq = ps.executeQuery();
			list = new ArrayList<WorkFlowMappingBean>();
			while (rsq.next()) {
				WorkFlowMappingBean reqwork = new WorkFlowMappingBean();
				reqwork.setRowId(rsq.getString("id"));
				reqwork.setRequestName(rsq.getString("request_type"));
				reqwork.setRequestType(rsq.getString("request_type_id"));
				reqwork.setWorkflowName(rsq.getString("workflow_name"));
				reqwork.setWorkflow(rsq.getString("workflow_id"));
				reqwork.setInstanceId(rsq.getString("org_role_id"));
				reqwork.setInstanceName(rsq.getString("org_role_name"));
				list.add(reqwork);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return list;
	}

	@Override
	public boolean checkReqRoleWorkData(WorkFlowMappingBean workflowMap) throws Exception {

		Session session = null;
		List list = null;
		boolean flag = true;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select * from role_workflow_mapping where request_type_id=? and workflow_id=? and org_role_id=? and status=1";
			Query qry = session.createSQLQuery(sql);
			qry.setInteger(0, Integer.parseInt(workflowMap.getRequestId()));
			qry.setInteger(1, Integer.parseInt(workflowMap.getWorkflowId()));
			qry.setInteger(2, Integer.parseInt(workflowMap.getInstanceId()));
			list = qry.list();
			if (CPSUtils.checkList(list)) {
				flag = false;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return flag;
	}

	@Override
	public String saveValues(ReqRoleWorkMappingDTO workflowMap) throws Exception {
		String message = null;
		//Transaction tx = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(workflowMap);
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

	@Override
	public String deleteMapping(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		PreparedStatement pstmt = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Connection con = session.connection();
			String sql = "update ROLE_WORKFLOW_MAPPING set status=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "0");
			pstmt.setString(2, String.valueOf(workflowMap.getId()));
			pstmt.executeUpdate();
			message = CPSConstants.DELETED;
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;

	}

	@Override
	public List getReqWorkFlowList() throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		List<WorkFlowMappingBean> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select rwm.id,rwm.request_type_id,rm.request_type,rwm.workflow_id,wtm.workflow_name from request_workflow_mapping rwm,request_master rm,workflow_master wtm where rm.request_type_id=rwm.request_type_id and wtm.workflow_id=rwm.workflow_id and rwm.status=1";
			ps = con.prepareStatement(sql);
			rsq = ps.executeQuery();
			list = new ArrayList<WorkFlowMappingBean>();
			while (rsq.next()) {
				WorkFlowMappingBean reqwork = new WorkFlowMappingBean();
				reqwork.setRowId(rsq.getString("id"));
				reqwork.setRequestName(rsq.getString("request_type"));
				reqwork.setRequestType(rsq.getString("request_type_id"));
				reqwork.setWorkflowName(rsq.getString("workflow_name"));
				reqwork.setWorkflow(rsq.getString("workflow_id"));
				reqwork.setWorkflowType(CPSConstants.GENERIC);
				list.add(reqwork);
			}

			String role = "select rwm.id,rm.request_type_id,rm.request_type,wtm.workflow_id,wtm.workflow_name,oi.org_role_id,oi.org_role_name "
					+ "from role_workflow_mapping rwm,request_master rm,workflow_master wtm,org_role_instance oi where rwm.status=1 and rm.status=1 and wtm.status=1 "
					+ "and rwm.request_type_id=rm.request_type_id and rwm.workflow_id=wtm.workflow_id and oi.status=1 and oi.org_role_id=rwm.org_role_id order by oi.org_role_name";
			ps = con.prepareStatement(role);
			rsq = ps.executeQuery();
			while (rsq.next()) {
				WorkFlowMappingBean reqwork = new WorkFlowMappingBean();
				reqwork.setRowId(rsq.getString("id"));
				reqwork.setRequestName(rsq.getString("request_type"));
				reqwork.setRequestType(rsq.getString("request_type_id"));
				reqwork.setWorkflowName(rsq.getString("workflow_name"));
				reqwork.setWorkflow(rsq.getString("workflow_id"));
				reqwork.setWorkflowType(CPSConstants.ROLE);
				reqwork.setRoleInstanceID(rsq.getString("org_role_id"));
				reqwork.setRoleInstanceName(rsq.getString("org_role_name"));
				list.add(reqwork);
			}
			//for designation based workflow
			String desination = "select rdwm.id,rm.request_type_id,rm.request_type,wtm.workflow_id,"
                +"wtm.workflow_name,dm.id as desigid,dm.name as designame from req_desig_workflow_mapping rdwm,"
                +"request_master rm,workflow_master wtm,designation_master dm " 
                +"where rdwm.status=1 and rm.status=1 and wtm.status=1 and "
                +"rdwm.request_type_id=rm.request_type_id and rdwm.workflow_id=wtm.workflow_id and "
                +"dm.status=1 and dm.id=rdwm.designation_id order by dm.name";
           ps = con.prepareStatement(desination);
           rsq = ps.executeQuery();
           while (rsq.next()) {
        	   WorkFlowMappingBean reqwork = new WorkFlowMappingBean();
        	   reqwork.setRowId(rsq.getString("id"));
        	   reqwork.setRequestName(rsq.getString("request_type"));
        	   reqwork.setRequestType(rsq.getString("request_type_id"));
        	   reqwork.setWorkflowName(rsq.getString("workflow_name"));
        	   reqwork.setWorkflow(rsq.getString("workflow_id"));
        	   reqwork.setWorkflowType(CPSConstants.DESIG);
        	   reqwork.setDesignationID(rsq.getString("desigid"));
        	   reqwork.setDesignation(rsq.getString("designame"));
        	   list.add(reqwork);
           }
         //for organization specific workflow
			String organization = "select rowm.id,rm.request_type_id,rm.request_type,wtm.workflow_id,wtm.workflow_name,om.id as orgid," +
					"om.name as orgname from req_org_workflow_mapping rowm,request_master rm,workflow_master wtm,organizations_master om " +
					"where rowm.status=1 and rm.status=1 and wtm.status=1 and rowm.request_type_id=rm.request_type_id and " +
					"rowm.workflow_id=wtm.workflow_id and om.status=1 and om.id=rowm.organization_id order by om.name";
          ps = con.prepareStatement(organization);
          rsq = ps.executeQuery();
          while (rsq.next()) {
       	   WorkFlowMappingBean reqwork = new WorkFlowMappingBean();
       	   reqwork.setRowId(rsq.getString("id"));
       	   reqwork.setRequestName(rsq.getString("request_type"));
       	   reqwork.setRequestType(rsq.getString("request_type_id"));
       	   reqwork.setWorkflowName(rsq.getString("workflow_name"));
       	   reqwork.setWorkflow(rsq.getString("workflow_id"));
       	   reqwork.setWorkflowType(CPSConstants.ORGSPECIFIC);
       	   reqwork.setOrganizationID(rsq.getString("orgid"));
       	   reqwork.setOrganization(rsq.getString("orgname"));
       	   list.add(reqwork);
          }

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return list;
	}

	@Override
	public String manageWorkFlowList(WorkFlowMappingBean workflowmap) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (CPSUtils.compareStrings(String.valueOf(workflowmap.getId()), "0")) {

				// new record
				if(CPSUtils.compareStrings(workflowmap.getWorkflowType(), "3")){
					//designation based workflow
					ReqDesigWorkflowMappingDTO desigWorkflowMappingDTO=new ReqDesigWorkflowMappingDTO();
					desigWorkflowMappingDTO.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					desigWorkflowMappingDTO.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					desigWorkflowMappingDTO.setDesignationId(Integer.parseInt(workflowmap.getDesignationID()));
					desigWorkflowMappingDTO.setStatus(1);
					desigWorkflowMappingDTO.setCreationDate(CPSUtils.getCurrentDate());
					desigWorkflowMappingDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					//tx = session.beginTransaction();
					session.saveOrUpdate(desigWorkflowMappingDTO);
					session.flush();//tx.commit() ;
				}else if(CPSUtils.compareStrings(workflowmap.getWorkflowType(), "4")){
					//organization specific workflow
					ReqOrgWorkflowMappingDTO orgWorkflowMappingDTO=new ReqOrgWorkflowMappingDTO();
					orgWorkflowMappingDTO.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					orgWorkflowMappingDTO.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					orgWorkflowMappingDTO.setOrganizationId(Integer.parseInt(workflowmap.getOrganizationID()));
					orgWorkflowMappingDTO.setStatus(1);
					orgWorkflowMappingDTO.setCreationDate(CPSUtils.getCurrentDate());
					orgWorkflowMappingDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					//tx = session.beginTransaction();
					session.saveOrUpdate(orgWorkflowMappingDTO);
					session.flush();//tx.commit() ;
				}else if (CPSUtils.compareStrings(workflowmap.getWorkflowType(), "2")) {
					// role based workflow
					ReqRoleWorkMappingDTO roleWorkMap = new ReqRoleWorkMappingDTO();
					roleWorkMap.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					roleWorkMap.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					roleWorkMap.setRoleInstanceId(Integer.parseInt(workflowmap.getRoleInstanceID()));
					roleWorkMap.setStatus(1);
					roleWorkMap.setCreationDate(CPSUtils.getCurrentDate());
					roleWorkMap.setLastModifiedDate(CPSUtils.getCurrentDate());
					//tx = session.beginTransaction();
					session.saveOrUpdate(roleWorkMap);
					session.flush();//tx.commit() ;

				} else {
					// generic workflow
					ReqWorkMappingDTO genWorkMap = new ReqWorkMappingDTO();
					genWorkMap.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					genWorkMap.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					genWorkMap.setStatus(1);
					genWorkMap.setCreationDate(CPSUtils.getCurrentDate());
					genWorkMap.setLastModifiedDate(CPSUtils.getCurrentDate());

					//tx = session.beginTransaction();
					session.saveOrUpdate(genWorkMap);
					session.flush();//tx.commit() ;
				}
			} else {
				// old record
				if(CPSUtils.compareStrings(workflowmap.getWorkflowType(), "3")){
					//designation based workflow
					ReqDesigWorkflowMappingDTO desigWorkflowMappingDTO=(ReqDesigWorkflowMappingDTO)session.get(ReqDesigWorkflowMappingDTO.class, workflowmap.getId());
					desigWorkflowMappingDTO.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					desigWorkflowMappingDTO.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					desigWorkflowMappingDTO.setDesignationId(Integer.parseInt(workflowmap.getDesignationID()));
					desigWorkflowMappingDTO.setStatus(1);
					desigWorkflowMappingDTO.setCreationDate(CPSUtils.getCurrentDate());
					desigWorkflowMappingDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					//tx = session.beginTransaction();
					session.saveOrUpdate(desigWorkflowMappingDTO);
					session.flush();//tx.commit() ;
				}else if(CPSUtils.compareStrings(workflowmap.getWorkflowType(), "4")){
					//organization specific workflow
					ReqOrgWorkflowMappingDTO orgWorkflowMappingDTO=(ReqOrgWorkflowMappingDTO)session.get(ReqOrgWorkflowMappingDTO.class, workflowmap.getId());
					orgWorkflowMappingDTO.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					orgWorkflowMappingDTO.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					orgWorkflowMappingDTO.setOrganizationId(Integer.parseInt(workflowmap.getDesignationID()));
					orgWorkflowMappingDTO.setStatus(1);
					orgWorkflowMappingDTO.setCreationDate(CPSUtils.getCurrentDate());
					orgWorkflowMappingDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					//tx = session.beginTransaction();
					session.saveOrUpdate(orgWorkflowMappingDTO);
					session.flush();//tx.commit() ;
				}else if (CPSUtils.compareStrings(workflowmap.getWorkflowType(), "2")) {
					// role based workflow
					ReqRoleWorkMappingDTO roleWorkMap = (ReqRoleWorkMappingDTO) session.get(ReqRoleWorkMappingDTO.class, workflowmap.getId());
					roleWorkMap.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					roleWorkMap.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					roleWorkMap.setRoleInstanceId(Integer.parseInt(workflowmap.getRoleInstanceID()));
					roleWorkMap.setCreationDate(CPSUtils.formattedDate(roleWorkMap.getCreationDate()));
					roleWorkMap.setLastModifiedDate(CPSUtils.getCurrentDate());
					//tx = session.beginTransaction();
					session.saveOrUpdate(roleWorkMap);
					session.flush();//tx.commit() ;

				} else {
					// generic workflow
					ReqWorkMappingDTO genWorkMap = (ReqWorkMappingDTO) session.get(ReqWorkMappingDTO.class, workflowmap.getId());
					genWorkMap.setRequestTypeId(Integer.parseInt(workflowmap.getRequestId()));
					genWorkMap.setWorkflowId(Integer.parseInt(workflowmap.getWorkflowId()));
					genWorkMap.setCreationDate(CPSUtils.formattedDate(genWorkMap.getCreationDate()));
					genWorkMap.setLastModifiedDate(CPSUtils.getCurrentDate());
					//tx = session.beginTransaction();
					session.saveOrUpdate(genWorkMap);
					session.flush();//tx.commit() ;
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

	@Override
	public boolean checkReqWorkData(WorkFlowMappingBean workflowmap) throws Exception {
		Session session = null;
		List list = null;
		boolean flag = true;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if(CPSUtils.compareStrings(workflowmap.getWorkflowType(), "3")){
				//for designation based workflow
				list=session.createCriteria(ReqDesigWorkflowMappingDTO.class).add(Expression.eq("requestTypeId", Integer.parseInt(workflowmap.getRequestId()))).add(Expression.eq("designationId", Integer.parseInt(workflowmap.getDesignationID()))).add(Expression.eq("status", 1)).list();
			}else if(CPSUtils.compareStrings(workflowmap.getWorkflowType(), "4")){
				//for organization specific workflow
				list=session.createCriteria(ReqOrgWorkflowMappingDTO.class).add(Expression.eq("requestTypeId", Integer.parseInt(workflowmap.getRequestId()))).add(Expression.eq("organizationId", Integer.parseInt(workflowmap.getOrganizationID()))).add(Expression.eq("status", 1)).list();
			}else if (CPSUtils.compareStrings(workflowmap.getWorkflowType(), "2")) {
				// role based workflow
				String sql = "select * from role_workflow_mapping where request_type_id=? and status=1 and org_role_id=? and id!=?";
				list = session.createSQLQuery(sql).setInteger(0, Integer.parseInt(workflowmap.getRequestId())).setInteger(1, Integer.parseInt(workflowmap.getRoleInstanceID())).setInteger(2,
						workflowmap.getId()).list();
			} else {
				// generic workflow
				String sql = "select * from request_workflow_mapping where request_type_id=? and id!=? and status=1";
				list = session.createSQLQuery(sql).setInteger(0, Integer.parseInt(workflowmap.getRequestId())).setInteger(1, workflowmap.getId()).list();
			}
			if (CPSUtils.checkList(list)) {
				flag = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return flag;
	}

	@Override
	public String deleteReqWorkFlow(String id, String workflowType) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			Connection con = session.connection();
			if(CPSUtils.compareStrings(workflowType, CPSConstants.DESIG)){
				//for designation based workflow
				sql = "update req_desig_workflow_mapping set status=0 where id=?";
			}else if(CPSUtils.compareStrings(workflowType, CPSConstants.ORGSPECIFIC)){
				//for organization specific workflow
				sql = "update req_org_workflow_mapping set status=0 where id=?";
			}else if (CPSUtils.compareStrings(workflowType, CPSConstants.GENERIC)) {
				sql = "update request_workflow_mapping set status=0 where id=?";
			} else {
				sql = "update role_workflow_mapping set status=0 where id=?";
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.executeUpdate();
			session.flush();//tx.commit() ;

			message = CPSConstants.DELETED;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
	}

	/**
	 * This method will return the pending, delegate, escalated & delegated requests for a particular user
	 */
	@Override
	public ArrayList<RequestsDTO> assignedRequests(String sfid, String type, String limit, String size, String requestType, String requestID, String fromDate, String toDate) throws Exception {
		Session session = null;
		ArrayList<RequestsDTO> requestList = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rsq = null;
		ResultSet rsq1 = null;
		Connection con = null;
		try {
			requestList = new ArrayList<RequestsDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			StringBuilder sb = new StringBuilder();

			/**
			 * First get the requests that are assigned to the current user
			 */
			/*sb
					.append("select id,request_id,assignedDate,request_type_id,assigned_date,actioned_date,actionedDate from (select rwh.id,rwh.request_id,rwh.request_type_id,rwh.assigned_date,rwh.actioned_date,TO_CHAR(rwh.actioned_date,'DD-MON-YYYY') actionedDate,TO_CHAR(rwh.assigned_date,'DD-MON-YYYY') assignedDate,ROW_NUMBER() OVER (ORDER BY rwh.id DESC) AS ora_row_num from request_workflow_history rwh,status_master sm "
							+ "where rwh.assigned_to=? and sm.id=rwh.status and sm.status in ("
							+ type
							+ ") and 0=(select count(*) from request_workflow_history rwh1 "
							+ "where rwh1.request_id=rwh.request_id and rwh1.status in (?)) order by rwh.id desc) where 1=1 ");*/
			/**
			 * applied_by is added in request_workflow_history table so query is modified with applied_by
			 */
			sb.append("select id,request_id,assignedDate,request_type_id,assigned_date,actioned_date,actionedDate,applied_by,request_type,name_in_service_book requester from " 
					+ "(select rwh.id,rwh.request_id,rwh.request_type_id,rwh.assigned_date,rwh.actioned_date,TO_CHAR(rwh.actioned_date,'DD-MON-YYYY') " 
					+ "actionedDate,TO_CHAR(rwh.assigned_date,'DD-MON-YYYY') assignedDate,ROW_NUMBER() OVER (ORDER BY rwh.id DESC) AS ora_row_num,rwh.applied_by,rm.request_type,em.name_in_service_book " 
					+ "from request_workflow_history rwh,status_master sm,request_master rm,emp_master em where rwh.assigned_to=? and sm.id=rwh.status and sm.status in ("+type+") " 
					+ "and 0=(select count(*) from request_workflow_history rwh1 where rwh1.request_id=rwh.request_id and rwh1.status in (?)) and rm.request_type_id=rwh.request_type_id and em.sfid=rwh.applied_by " 
					+ "order by rwh.id desc) where 1=1");
			if (CPSUtils.compareStrings(limit, CPSConstants.LIMIT)) {
				// limit the records
				sb.append(" and ora_row_num<=" + size);
			}

			if (!CPSUtils.isNullOrEmpty(requestType) && !CPSUtils.compareStrings(requestType, CPSConstants.SELECT)) {
				sb.append(" and request_type_id=" + requestType);
			}
			if (!CPSUtils.isNullOrEmpty(requestID)) {
				sb.append(" and request_id=" + requestID);
			}
			if (!CPSUtils.isNullOrEmpty(fromDate) && !CPSUtils.isNullOrEmpty(toDate)) {
				sb.append(" and assignedDate between to_date('" + fromDate + "','DD-MON-YYYY') and to_date('" + toDate + "','DD-MON-YYYY') ORDER BY assigned_date DESC");
			}else if(CPSUtils.isNullOrEmpty(requestID) && CPSUtils.isNullOrEmpty(requestType) && CPSUtils.compareStrings(requestType, CPSConstants.SELECT)){
				sb.append(" and assignedDate between to_date('01-JAN-" + CPSUtils.currentYear() + "','DD-MON-YYYY') and to_date('31-DEC-" + CPSUtils.currentYear() + "','DD-MON-YYYY')  ORDER BY assigned_date DESC");
			}
			
			

			ps = con.prepareStatement(sb.toString());
			ps.setString(1, sfid);
			ps.setString(2, CPSConstants.STATUSCANCELLED);
			// ps.setString(3, CPSConstants.STATUSDECLINED);
		
			rsq = ps.executeQuery();
			/*while (rsq.next()) {
				*//**
				 * Get the requester details
				 *//*
				String getRequester = "select rwh.id,rm.request_type,emp.name_in_service_book requester " + "from request_workflow_history rwh,request_master rm,emp_master emp "
						+ "where id=(select min(id) from request_workflow_history where request_id=?) and rwh.request_type_id=rm.request_type_id and emp.sfid=rwh.assigned_from";

				ps1 = con.prepareStatement(getRequester);
				ps1.setString(1, rsq.getString("request_id"));
				rsq1 = ps1.executeQuery();
				while (rsq1.next()) {
					RequestsDTO reqDTO = new RequestsDTO();
					reqDTO.setHistoryID(rsq.getString("id"));
					reqDTO.setAssignedDate(rsq.getString("assignedDate"));
					reqDTO.setActionedDate(rsq.getString("actionedDate"));
					reqDTO.setRequestID(rsq.getString("request_id"));
					reqDTO.setRequestType(rsq1.getString("request_type"));
					reqDTO.setRequester(rsq1.getString("requester"));
					requestList.add(reqDTO);
				}
				ConnectionUtil.closeConnection(null, ps1, rsq1);
			}*/
			while (rsq.next()) {
				RequestsDTO reqDTO = new RequestsDTO();
				reqDTO.setHistoryID(rsq.getString("id"));
				reqDTO.setAssignedDate(rsq.getString("assignedDate"));
				reqDTO.setActionedDate(rsq.getString("actionedDate"));
				reqDTO.setRequestID(rsq.getString("request_id"));
				reqDTO.setRequestType(rsq.getString("request_type"));
				reqDTO.setRequester(rsq.getString("requester"));
				requestList.add(reqDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return requestList;
	}
	

	/**
	 * This method will return the requests that the current employee was requested
	 */
	@SuppressWarnings("unused")
	@Override
	public ArrayList<RequestsDTO> myRequests(String sfid, String limit, String size, String requestType, String requestID, String fromDate, String toDate) throws Exception {
		Session session = null;
		ArrayList<RequestsDTO> myRequestsList = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rsq = null;
		ResultSet rsq1 = null;
		Connection con = null;
		
		StringBuilder myrequests = new StringBuilder();
		
		try {
			myRequestsList = new ArrayList<RequestsDTO>();
			
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			/*myrequests
					.append("select id,request_id,request_type,status,case when status1=1 then 1 else request_stage end request_stage,request_type_id,assigned_date from (select ROW_NUMBER() OVER (ORDER BY rwh.id DESC) AS ora_row_num,rwh.id,rwh.request_id,rm.request_type,rm.request_type_id,rwh.assigned_date,(select distinct sm.status from request_workflow_history rwh1,status_master sm where rwh1.request_id=rwh.request_id "
							+ "and rwh1.request_stage=rwh.request_stage and rwh1.ip_address is not null and sm.id=rwh1.status and rwh1.id=(select max(rwh2.id) from request_workflow_history rwh2 where rwh2.request_id=rwh.request_id and rwh2.request_stage=rwh.request_stage and rwh2.ip_address is not null)) status,(select count(*) from request_workflow_history where request_id=rwh.request_id) status1,rwh.request_stage from request_workflow_history rwh,request_master rm,status_master sm "
							+ "where rwh.id in( select history_id from (select max(id) history_id,request_id from request_workflow_history "
							+ "where request_id in (select tab1.request_id from (select id,request_id from request_workflow_history "
							+ "where assigned_from=?) tab1, "
							+ "(select min(id) id,request_id from request_workflow_history where request_id in( select request_id "
							+ "from request_workflow_history where assigned_from=?) "
							+ "group by request_id) tab2 where tab1.id=tab2.id) group by request_id)) and rm.request_type_id=rwh.request_type_id and sm.id=rwh.status and rm.status=1 order by rwh.id desc) where 1=1 ");*/
			
			/**
			 * applied_by is added in request_workflow_history table so query is modified with applied_by
			 */
			myrequests.append("select id,request_id,request_type,status,request_stage,request_type_id,assigned_date from (select rwh.id,rwh.request_id,rm.request_type,sm.status,rwh.request_type_id,rwh.assigned_date " 
					+ "from request_workflow_history rwh,status_master sm,request_master rm where rwh.id in (select max(id) from request_workflow_history where request_id in (select request_id from " 
					+ "request_workflow_history where applied_by=?) group by request_id) and sm.id=rwh.status and rm.request_type_id=rwh.request_type_id order by rwh.assigned_date desc)tab1," 
					+ "(select count(*) as request_stage,rwh1.request_id reqId from request_workflow_history rwh1 where applied_by=? group by rwh1.request_id order by " 
					+ "rwh1.request_id desc)tab2 where tab1.request_id=tab2.reqId");

			if (CPSUtils.compareStrings(limit, CPSConstants.LIMIT)) {
				// limit the records
				//myrequests.append(" and ora_row_num<=" + size);
				myrequests.append(" and rownum<="+size);
			}
			if (!CPSUtils.isNullOrEmpty(requestType) && !CPSUtils.compareStrings(requestType, CPSConstants.SELECT)) {
				myrequests.append(" and request_type_id=" + requestType);
			}
			if (!CPSUtils.isNullOrEmpty(requestID)) {
				myrequests.append(" and request_id=" + requestID);
			}
			if (!CPSUtils.isNullOrEmpty(fromDate) && !CPSUtils.isNullOrEmpty(toDate)) {
				myrequests.append(" and assigned_date between to_date('" + fromDate + "','DD-MON-YYYY') and to_date('" + toDate + "','DD-MON-YYYY')");
			}
			ps = con.prepareStatement(myrequests.toString());
			ps.setString(1, sfid);
			ps.setString(2, sfid);
			rsq = ps.executeQuery();
			while (rsq.next()) {
				RequestsDTO reqDTO = new RequestsDTO();
				reqDTO.setHistoryID(rsq.getString("id"));
				reqDTO.setRequestID(rsq.getString("request_id"));
				reqDTO.setRequestType(rsq.getString("request_type"));
				if ((CPSUtils.isNullOrEmpty(rsq.getString("request_stage")) || Integer.parseInt(rsq.getString("request_stage")) <= 1)
						&& !(CPSUtils.compareStrings(CPSConstants.DECLINED, rsq.getString("status")) || CPSUtils.compareStrings(CPSConstants.CANCELLED, rsq.getString("status"))
								|| CPSUtils.compareStrings(CPSConstants.COMPLETED, rsq.getString("status")) || CPSUtils.compareStrings(CPSConstants.SANCTIONED, rsq.getString("status")) || CPSUtils
								.compareStrings(CPSConstants.CONVERTED, rsq.getString("status")))) {
					reqDTO.setStatus(CPSConstants.PENDING.toUpperCase());
					myRequestsList.add(reqDTO);
				} else if (CPSUtils.compareStrings(CPSConstants.DECLINED, rsq.getString("status")) || CPSUtils.compareStrings(CPSConstants.COMPLETED, rsq.getString("status"))
						|| CPSUtils.compareStrings(CPSConstants.CANCELLED, rsq.getString("status")) || CPSUtils.compareStrings(CPSConstants.SANCTIONED, rsq.getString("status"))
						|| CPSUtils.compareStrings(CPSConstants.CONVERTED, rsq.getString("status")) || CPSUtils.compareString(CPSConstants.APPROVED, rsq.getString("status"))) {
					if(CPSUtils.compareString(CPSConstants.APPROVED, rsq.getString("status"))) {
						 reqDTO.setStatus(CPSConstants.COMPLETED.toUpperCase());
					}else {
					reqDTO.setStatus(rsq.getString("status"));
					}
					myRequestsList.add(reqDTO);
				}
				else {
					StringBuilder myrequests1 = new StringBuilder();
					
					myrequests1.append("select count(*),ip_address from request_workflow_history where request_id = '"+rsq.getString("request_id")+"' group by ip_address");
					ps1 = con.prepareStatement(myrequests1.toString());
				//	ps1.setString(1, requestID);
					//ps.setString(2, sfid);
					rsq1 = ps1.executeQuery();
					String test1;
					int test2;
					rsq.getFetchSize();
					while(rsq1.next()){
					test2=Integer.parseInt(rsq1.getString(1));
						test1=rsq1.getString(2);
						if(!CPSUtils.isNull(test1))
							 reqDTO.setStatus(CPSConstants.PROCESSING);
						else
							 reqDTO.setStatus(CPSConstants.PENDING.toUpperCase());
					}
					myRequestsList.add(reqDTO);
						/*reqDTO.setStatus(CPSConstants.PROCESSING);*/
					
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return myRequestsList;
	}
/////////
	@Override
	@SuppressWarnings("unchecked")
	public List<AlertMessageDTO> assignedalertList(String sfid,
			String limit, String size, String selectedAlertType, String fromDate,
			String toDate) throws Exception {
		Session session = null;
		List<AlertMessageDTO> alertList = null;
		try {
			
			session = hibernateUtils.getSession();
			if (!CPSUtils.isNullOrEmpty(fromDate) && !CPSUtils.isNullOrEmpty(toDate) && !CPSUtils.compareStrings(selectedAlertType, CPSConstants.SELECT)) {
				alertList = session.createCriteria(AlertMessageDTO.class).add(Expression.eq("assignedTo",sfid)).add(Expression.eq("alertID",Integer.valueOf(selectedAlertType)))
				.add(Expression.eq("status",2)).add(Expression.between("assignedDate", CPSUtils.convertStringToDate(fromDate), CPSUtils.convertStringToDate(toDate))).addOrder(Order.desc("assignedDate")).list();
			 }else if(!CPSUtils.isNullOrEmpty(selectedAlertType) && !CPSUtils.compareStrings(selectedAlertType, CPSConstants.SELECT)){
			alertList = session.createCriteria(AlertMessageDTO.class).add(Expression.eq("assignedTo",sfid)).add(Expression.eq("alertID",Integer.valueOf(selectedAlertType))).add(Expression.eq("status",2)).addOrder(Order.desc("assignedDate")).list();
			}else if(!CPSUtils.isNullOrEmpty(fromDate) && !CPSUtils.isNullOrEmpty(toDate)){
				alertList = session.createCriteria(AlertMessageDTO.class).add(Expression.eq("assignedTo",sfid))
				.add(Expression.eq("status",2)).add(Expression.between("assignedDate", CPSUtils.convertStringToDate(fromDate), CPSUtils.convertStringToDate(toDate))).addOrder(Order.desc("assignedDate")).list();
			}	
		 } catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			
		}
		return alertList;
	}
	
/////////////	
	/**
	 * This method will return a particular request details like requester SFID,Name,designation & history details.
	 * @param requestId 
	 */
	@Override
	public WorkFlowMappingBean getRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		PreparedStatement ps1 = null,ps2 = null,ps3 =null,ps4=null,ps5=null;
		ResultSet rsq1 = null,rsq2 = null , rsq3 = null,rsq4=null,rsq5=null;
		Connection con = null,con1=null,con5=null;
		boolean flag = true;
		String message ="";
		try {

			  session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			  con = session.connection();
			  con1 = session.connection();
			  if (CPSUtils.compareStrings(workflowMap.getRoleId(),"roleId")) {
				  String roleid3 = (String)session.createSQLQuery("select unique to_char(request_id) from request_workflow_history where request_id=?").setString(0,workflowMap.getRequestId()).uniqueResult();
				  if(roleid3 == null){
        	        	flag =false;
        	        	workflowMap.setRequestnotexist(CPSConstants.REQUESTNOTEXIST);
        	        }
				  if(roleid3 != null){  
				    String roleid1 = (String)session.createSQLQuery("select UNIQUE to_char(REQUEST_ID) from REQUEST_WORKFLOW_HISTORY where REQUEST_ID=? and (ASSIGNED_FROM=? or ASSIGNED_TO=?)").
                    setString(0,workflowMap.getRequestId()).setString(1,workflowMap.getSfid()).setString(2,workflowMap.getSfid()).uniqueResult();
            
			         //String roleid2 = (String)session.createSQLQuery("select to_char(id) from REQUEST_ROLE_MAPPING where APPLICATION_ROLE_ID in (select ROLE_ID from APPLICATION_ROLE_MAPPING where SFID=? and STATUS=1) and REQUEST_TYPE_ID in (select REQUEST_TYPE_ID from REQUEST_WORKFLOW_HISTORY where REQUEST_ID=?)").setString(0,workflowMap.getSfid()).setString(1,workflowMap.getRequestId()).uniqueResult();
			         List<String> roleid2 = session.createSQLQuery("select to_char(id) from REQUEST_ROLE_MAPPING where APPLICATION_ROLE_ID in (select ROLE_ID from APPLICATION_ROLE_MAPPING where SFID=? and STATUS=1) and REQUEST_TYPE_ID in (select UNIQUE REQUEST_TYPE_ID from REQUEST_WORKFLOW_HISTORY where REQUEST_ID=?)").setString(0,workflowMap.getSfid()).setString(1,workflowMap.getRequestId()).list();

			    
		            if(roleid1  != null || roleid2.size() != 0){
          	        flag = true;
          	        workflowMap.setRequestsuccess(CPSConstants.REQUESTSUCCESS);
		        
                   }
		            else{
		          	      flag=false;
		          	      workflowMap.setRequestfailure(CPSConstants.REQUESTFAILURE); 
		            }
				  }
			  }
           if(flag){
		      String requestDetails = "select rwh.id,rwh.request_id,rwh.request_type_id,rm.request_type,rwh.assigned_from,to_char(rwh.assigned_date,'DD-Mon-YYYY') assigned_date,emp.name_in_service_book requester,desig.name designation,to_char(emp.doj_drdo,'dd-Mon-yyyy') dojDrdo,(select GET_RETIREMENT_DATE(emp.sfid) from dual) retirementDate,etm.name employmentTypeId,epd.grade_pay gradePay,emp.gpf_ac_no "
					+ "from request_workflow_history rwh,request_master rm,emp_master emp,designation_master desig,employment_type_master etm,emp_payment_details epd "
					+ "where rwh.id=( select min(rwh.id) from request_workflow_history rwh where rwh.request_id=?) "
					+ "and rm.status=1 and rm.request_type_id=rwh.request_type_id and emp.sfid=rwh.assigned_from and desig.id=emp.designation_id and epd.sfid=emp.sfid and etm.id=emp.employment_type_id";
		       
		      //changed query by bkr due to emp_payment_details view problem (just removed emp_payment_details from query ) 20/08/2016
		       requestDetails = "select rwh.id,rwh.request_id,rwh.request_type_id,rm.request_type,rwh.assigned_from,to_char(rwh.assigned_date,'DD-Mon-YYYY') assigned_date,emp.name_in_service_book requester,desig.name designation,to_char(emp.doj_drdo,'dd-Mon-yyyy') dojDrdo,(select GET_RETIREMENT_DATE(emp.sfid) from dual) retirementDate,etm.name employmentTypeId,emp.gpf_ac_no "
						+ "from request_workflow_history rwh,request_master rm,emp_master emp,designation_master desig,employment_type_master etm "
						+ "where rwh.id=( select min(rwh.id) from request_workflow_history rwh where rwh.request_id=?) "
						+ "and rm.status=1 and rm.request_type_id=rwh.request_type_id and emp.sfid=rwh.assigned_from and desig.id=emp.designation_id  and etm.id=emp.employment_type_id";
			       
		      
		      
		      System.out.println(requestDetails);
		     // System.out.println("requestDetails  ::::::"+requestDetails);
		      ps1 = con.prepareStatement(requestDetails);
				ps1.setString(1, workflowMap.getRequestId());
				rsq1 = ps1.executeQuery();
				if (rsq1.next()) {
					workflowMap.setRequesterSfid(rsq1.getString("assigned_from"));
					workflowMap.setRequester(rsq1.getString("requester"));
					workflowMap.setDesignation(rsq1.getString("designation"));
					workflowMap.setRequestType(rsq1.getString("request_type"));
					workflowMap.setRequestTypeID(rsq1.getString("request_type_id"));
					workflowMap.setRequestDate(rsq1.getString("assigned_date"));
					workflowMap.setDojDrdo(rsq1.getString("dojDrdo"));
					workflowMap.setRetirementDate(rsq1.getString("retirementDate"));
					workflowMap.setEmploymentTypeId(rsq1.getString("employmentTypeId"));
					//workflowMap.setGradePay(rsq1.getString("gradePay"));
					workflowMap.setGpfNumber(rsq1.getString("gpf_ac_no"));
				}else{
					 
					// this else block added by bkr 18/05/2016 for tada_water purpose
					boolean flag1 = false;
					boolean flag2 = false;
					boolean flag3 = false;
					boolean ltcflag1 = false;
					String checkFlag="";
					 checkFlag="select * from TADA_WATER_ADV_REQUEST_DETAILS where REQUEST_ID='"+workflowMap.getRequestId()+"'";
					 con5 = session.connection();
					System.out.println(checkFlag);
					 ps5 = con5.prepareStatement(checkFlag);
						//ps5.setString(1, workflowMap.getRequestId());
						rsq5 = ps5.executeQuery();
						if (rsq5.next()) {
							flag1=true;
						}else{
							ltcflag1 = true;
						}
						if(flag1){
							String requestDetails1="SELECT request_type_id a,sfid b  "
									+ "FROM request_workflow_history a  LEFT JOIN TADA_WATER_ADV_REQUEST_DETAILS b  "
									+ " ON a.request_id=b.request_id   WHERE a.request_id=?";
							
							 ps4 = con1.prepareStatement(requestDetails1);
								ps4.setString(1, workflowMap.getRequestId());
								rsq4 = ps4.executeQuery();
								if (rsq4.next()) {
									workflowMap.setRequestTypeID(rsq4.getString(1));
									workflowMap.setRequesterSfid(rsq4.getString(2));
									flag2=true;
								}
								if(flag2){
									String checkFlag2="select NAME_IN_SERVICE_BOOK,DESIGNATION_ID from EMP_MASTER where SFID=?";
									 con5 = session.connection();
									//System.out.println(checkFlag2);
									 ps5 = con5.prepareStatement(checkFlag2);
										ps5.setString(1, workflowMap.getRequesterSfid());
										rsq5 = ps5.executeQuery();
										if (rsq5.next()) {
											
											workflowMap.setRequester(rsq5.getString(1));
											workflowMap.setDesignation(rsq5.getString(2));
											flag3=true;
										}
										if(flag3){
											
											String checkFlag3="select NAME from DESIGNATION_MASTER where ID=?";
											 con5 = session.connection();
											System.out.println(checkFlag3);
											 ps5 = con5.prepareStatement(checkFlag3);
												ps5.setString(1, workflowMap.getDesignation());
												rsq5 = ps5.executeQuery();
												if (rsq5.next()) {
													
													//workflowMap.setRequester(rsq5.getString(1));
													workflowMap.setDesignation(rsq5.getString(1));
													//flag3=true;
												}
											
										}
									
									
								}
						}
						
						if(ltcflag1){
							//System.out.println("hello ltcflag1");
							
							checkFlag="select * from LTC_WATER_REQUEST_DETAILS where REQUEST_ID='"+workflowMap.getRequestId()+"'";
							 con5 = session.connection();
							//System.out.println(checkFlag);
							 ps5 = con5.prepareStatement(checkFlag);
								//ps5.setString(1, workflowMap.getRequestId());
								rsq5 = ps5.executeQuery();
								if (rsq5.next()) {
									flag1=true;
								}else{
									//ltcflag1 = true;
								}
								if(flag1){

									String requestDetails1="SELECT request_type_id a,sfid b  "
											+ "FROM request_workflow_history a  LEFT JOIN LTC_WATER_REQUEST_DETAILS b  "
											+ " ON a.request_id=b.request_id   WHERE a.request_id=?";
									
									 ps4 = con1.prepareStatement(requestDetails1);
										ps4.setString(1, workflowMap.getRequestId());
										rsq4 = ps4.executeQuery();
										if (rsq4.next()) {
											workflowMap.setRequestTypeID(rsq4.getString(1));
											workflowMap.setRequesterSfid(rsq4.getString(2));
											flag2=true;
										}
										if(flag2){
											String checkFlag2="select NAME_IN_SERVICE_BOOK,DESIGNATION_ID from EMP_MASTER where SFID=?";
											 con5 = session.connection();
											//System.out.println(checkFlag2);
											 ps5 = con5.prepareStatement(checkFlag2);
												ps5.setString(1, workflowMap.getRequesterSfid());
												rsq5 = ps5.executeQuery();
												if (rsq5.next()) {
													
													workflowMap.setRequester(rsq5.getString(1));
													workflowMap.setDesignation(rsq5.getString(2));
													flag3=true;
												}
												if(flag3){
													
													String checkFlag3="select NAME from DESIGNATION_MASTER where ID=?";
													 con5 = session.connection();
													System.out.println(checkFlag3);
													 ps5 = con5.prepareStatement(checkFlag3);
														ps5.setString(1, workflowMap.getDesignation());
														rsq5 = ps5.executeQuery();
														if (rsq5.next()) {
															
															//workflowMap.setRequester(rsq5.getString(1));
															workflowMap.setDesignation(rsq5.getString(1));
															//flag3=true;
														}
													
												}
											
											
										}
								
									
									
								}
							
							
							
						}
					
				}
				
				workflowMap.setRequestsuccess(CPSConstants.REQUESTSUCCESS);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps1, rsq1);
		}
		return workflowMap;
	}
	/*
	@Override
	public String getSpecificRequestDetails(String sfid,WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		List<RequestsDTO> list = null;
		String roleType="";
		
		
		Connection con = null;
		try {
			  session = hibernateUtils.getSession();
			   con = session.connection();
			   
			   String qry = "select UNIQUE APM.SFID as sfid,RM.id as id,RRM.APPLICATION_ROLE_ID AproleID,RRM.REQUEST_TYPE_ID as requestTypeId  from ROLE_MASTER RM,REQUEST_ROLE_MAPPING RRM,APPLICATION_ROLE_MAPPING APM" +
               " where rm.id=RRM.APPLICATION_ROLE_ID and APM.ROLE_ID = RRM.APPLICATION_ROLE_ID and sfid = ?";
                list = session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("AProleID", Hibernate.INTEGER).addScalar("requestTypeId", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RequestsDTO.class)).setString(0, sfid).list();

                  for(int i=0;i<list.size();i++){
	                String val= list.get(i).getRequestTypeId();
	                    if(CPSUtils.isNullOrEmpty(val)){
	                    	roleType = "no";
		                   break;
	                       }else{
	                    	   roleType = "yes";
	                       }
                         }
            
			
			
		} catch (Exception e) {
			throw e;
		}
		return roleType;
	}
*/
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<RequestsDTO> getRequestStatusDetails(String requestID, String requesterSfid) throws Exception {
		Session session = null;
		ArrayList<RequestsDTO> statusList = null;
		String roles = "'First',";
		boolean status = true;
		try {
			statusList = new ArrayList<RequestsDTO>();

			session = hibernateUtils.getSession();

			if (CPSUtils.isNullOrEmpty(requesterSfid)) {
				requesterSfid = session.createSQLQuery("select assigned_from from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?)").setString(0,requestID).uniqueResult().toString();
			}

			String getRequestStatusDetails = "select rwh.assigned_from sfID,emp1.name_in_service_book requestFrom,rwh.assigned_date assignedDate,rwh.assigned_to parentID,"
					+ "emp2.name_in_service_book requestedTo,TO_CHAR(rwh.actioned_date,'DD-MON-YYYY') actionedDate,sm.status statusMsg,rwh.stage_status stageStatus,rwh.ip_address ipAddress,rwh.remarks,"
					+ "case when (select name from workflow_stage_master where id=rwh.stage_status) is not null then (select name from workflow_stage_master where id=rwh.stage_status) else "
					+ "(select status from status_master where id=rwh.stage_status) end stage,case when rwh.assigned_role_id is null then 'search' when rwh.assigned_role_id=0 then 'Employee' else (select org_role_name from org_role_instance where org_role_id=rwh.assigned_role_id) end role "
					+ "from request_workflow_history rwh,status_master sm,emp_master emp1,emp_master emp2 where rwh.request_id=? and "
					+ "rwh.status=sm.id and rwh.assigned_from=emp1.sfid and emp2.sfid=rwh.assigned_to order by rwh.id";

			List<RequestBean> reqList = session.createSQLQuery(getRequestStatusDetails).addScalar("sfID", Hibernate.STRING).addScalar("requestFrom", Hibernate.STRING).addScalar("assignedDate",
					Hibernate.DATE).addScalar("parentID", Hibernate.STRING).addScalar("requestedTo", Hibernate.STRING).addScalar("actionedDate", Hibernate.STRING).addScalar("statusMsg",
					Hibernate.STRING).addScalar("stageStatus", Hibernate.INTEGER).addScalar("ipAddress", Hibernate.STRING).addScalar("remarks", Hibernate.STRING).addScalar("stage", Hibernate.STRING)
					.addScalar("role", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RequestBean.class)).setString(0, requestID).list();

			for (RequestBean reqBean : reqList) {
				RequestsDTO reqDTO = new RequestsDTO();
				reqDTO.setAssignedFrom(reqBean.getRequestFrom());
				reqDTO.setAssignedTo(reqBean.getRequestedTo());

				if (CPSUtils.compareStrings(reqBean.getRole(), CPSConstants.SEARCH)) {
					String getRole = "select case when (select count(*) from emp_role_mapping erm,org_role_instance ori where erm.status=1 and erm.sfid=? and erm.org_role_id in "
							+ "(select org_role_id from org_role_instance ori start with ori.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 and emp.sfid=?) connect by ori.org_role_id=prior ori.parent_org_role_id "
							+ "union select org_role_id from org_role_instance ori start with ori.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 and emp.sfid=?) connect by ori.parent_org_role_id=prior ori.org_role_id) and ori.status=1 "
							+ "and ori.org_role_id=erm.org_role_id)>1 then (select ori.org_role_name from emp_role_mapping erm,org_role_instance ori where erm.status=1 and erm.sfid=? and erm.org_role_id in (select org_role_id from org_role_instance ori start with "
							+ "ori.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 and emp.sfid=?) connect by ori.org_role_id=prior ori.parent_org_role_id union "
							+ "select org_role_id from org_role_instance ori start with ori.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 and emp.sfid=?) connect by ori.parent_org_role_id=prior ori.org_role_id) and ori.status=1 "
							+ "and ori.org_role_id=erm.org_role_id and erm.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 and emp.sfid=?) and ori.org_role_name not in ("
							+ roles.substring(0, roles.length() - 1)
							+ ")) "
							+ "else (select ori.org_role_name from emp_role_mapping erm,org_role_instance ori where erm.status=1 and erm.sfid=? and erm.org_role_id in (select org_role_id from org_role_instance ori start with ori.org_role_id=(select emp.office_id "
							+ "from emp_master emp where emp.status=1 and emp.sfid=?) connect by ori.org_role_id=prior ori.parent_org_role_id union "
							+ "select org_role_id from org_role_instance ori start with ori.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 and emp.sfid=?) connect by ori.parent_org_role_id=prior ori.org_role_id) and ori.status=1 "
							+ "and ori.org_role_id=erm.org_role_id) end org_role_name from dual";

					Object obj = session.createSQLQuery(getRole).setString(0, reqBean.getParentID()).setString(1, requesterSfid).setString(2, requesterSfid).setString(3, reqBean.getParentID())
							.setString(4, requesterSfid).setString(5, requesterSfid).setString(6, requesterSfid).setString(7, reqBean.getParentID()).setString(8, requesterSfid).setString(9,
									requesterSfid).uniqueResult();

					status = true;

					if (!CPSUtils.isNull(obj)) {
						status = false;
						roles += "'" + obj.toString() + "',";
						reqDTO.setRoleName(obj.toString());
					}

					if (status) {
						// in the tree assigned employee is not exists
						// get the employees default role
						getRole = "select ori.org_role_name from emp_master emp, org_role_instance ori, emp_role_mapping erm where ori.org_role_id=emp.office_id and emp.status=1 and ori.status=1 and erm.status=1 and "
								+ "erm.org_role_id=emp.office_id and emp.sfid=? and erm.org_role_id is not null and erm.sfid=emp.sfid";

						Object obj1 = session.createSQLQuery(getRole).setString(0, reqBean.getParentID()).uniqueResult();

						if (CPSUtils.isNull(obj1)) {
							reqDTO.setRoleName("Employee");
						} else {
							reqDTO.setRoleName(obj1.toString());
						}
					}
				} else {
					reqDTO.setRoleName(reqBean.getRole());
				}

				reqDTO.setParentID(reqBean.getParentID());
				reqDTO.setActionedDate(reqBean.getActionedDate());
				reqDTO.setStatus(reqBean.getStage());
				reqDTO.setRemarks(reqBean.getRemarks());
				reqDTO.setIpAddress(reqBean.getIpAddress());
			/*	reqDTO.setRoleName(reqBean.getRole());*/
				statusList.add(reqDTO);
				
			}
		} catch (Exception e) {
			throw e;
		}
		return statusList;
	}

	/**
	 * Some times an employee is already configured the manual delegated list for request forward. This method will return that list.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> requestDelegateList(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		List<KeyValueDTO> delegateList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			delegateList = session
					.createSQLQuery(
							"select usc.delegate||'' key,case when usc.delegation_type=? then (select sfid||' - '||name_in_service_book from emp_master where status=1 and sfid=usc.delegate) else (select org_role_name from org_role_instance where status=1 and org_role_id = usc.delegate) end as name "
									+ "from user_specific_configuration usc where usc.status=1 and usc.org_role_id in (select ori.org_role_id from org_role_instance ori where ori.status=1 and ori.org_role_id in (select org_role_id from emp_role_mapping where status=1 and sfid=? and org_role_id is not null) start with "
									+ "ori.org_role_id=(select office_id from emp_master where status=1 and sfid=?) connect by ori.org_role_id=prior ori.parent_org_role_id) and usc.request_type_id=? and usc.assigned_type=? and usc.designation_id=(select case when usc.gazetted_type is null then 0 else " 
									+ "(select designation_id from emp_master where sfid=?) end as desigId from dual)")
					.addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, CPSConstants.STATUSSFID)
					.setString(1, workflowMap.getSfid()).setString(2, workflowMap.getRequesterSfid()).setString(3, workflowMap.getRequestTypeID()).setInteger(4,
							Integer.valueOf(CPSConstants.STATUSMANUAL)).setString(5, workflowMap.getRequesterSfid()).setFlushMode(FlushMode.ALWAYS).list();
			
			
			
			if ((delegateList.equals(null) || delegateList==null || delegateList.isEmpty()==true ||delegateList.size()==0 ) ) {			
				
				////"(SELECT DELEGATE key,B.SFID||'-'||B.NAME_IN_SERVICE_BOOK as name FROM USER_SPECIFIC_CONFIGURATION A,EMP_MASTER B WHERE ORG_ROLE_ID IN (?) AND ASSIGNED_TYPE=10 AND REQUEST_TYPE_ID=? AND DELEGATE =?) UNION (SELECT DELEGATE as key,b.org_role_name as name  FROM USER_SPECIFIC_CONFIGURATION A, ORG_ROLE_INSTANCE B WHERE A.ORG_ROLE_ID IN (?) AND ASSIGNED_TYPE=10 AND REQUEST_TYPE_ID=? AND A.DELEGATE=?)")

				delegateList = session
							.createSQLQuery("(SELECT DELEGATE KEY, B.SFID  ||'-' ||B.NAME_IN_SERVICE_BOOK AS name FROM USER_SPECIFIC_CONFIGURATION A,  EMP_MASTER B WHERE b.sfid IN ( ?) AND ASSIGNED_TYPE  =? AND REQUEST_TYPE_ID=? and a.delegate=b.sfid ) UNION  (SELECT to_char(b.org_role_id) AS KEY,   b.org_role_name AS name" 
	                                 +" FROM USER_SPECIFIC_CONFIGURATION A,  ORG_ROLE_INSTANCE B   WHERE a.ORG_ROLE_ID IN   (SELECT to_char(org_role_id) FROM emp_role_mapping WHERE sfid=? AND status=1) "
	                              +" AND ASSIGNED_TYPE   =?   AND REQUEST_TYPE_ID =? and TO_CHAR(B.org_role_id )  =TO_CHAR(A.DELEGATE))")
						.addScalar("key", Hibernate.STRING)
						.addScalar("name", Hibernate.STRING)
						
						.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))		
						.setString(0, workflowMap.getRequesterSfid())
						
						.setInteger(1, Integer.valueOf(CPSConstants.STATUSMANUAL))
						.setString(2, CPSConstants.STATUSSFID)
						.setString(3, workflowMap.getSfid())
						
						.setInteger(4, Integer.valueOf(CPSConstants.STATUSMANUAL))	
						.setString(5, workflowMap.getRequestTypeID())
						.list();
			}
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return delegateList;
	}

	@Override
	public WorkFlowMappingBean getChangedDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rsq1 = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();

			String getRequestType = "select request_type from request_master where "
					+ "request_type_id=(select request_type_id from request_workflow_history where request_id=? group by request_type_id) and status=1";
			ps = con.prepareStatement(getRequestType);
			ps.setString(1, workflowMap.getRequestId());
			rsq1 = ps.executeQuery();
			if (rsq1.next()) {
				workflowMap.setRequestType(rsq1.getString("request_type"));
				if (CPSUtils.compareStrings(CPSConstants.NOMINEE, workflowMap.getRequestType())) {
					workflowMap = getTxnRequestDetails(workflowMap);
					workflowMap.setContengensyList(getIncontengensyDetails(workflowMap.getRequestId()));
				} else if (CPSUtils.compareStrings(CPSConstants.LEAVE, workflowMap.getRequestType())) {
					workflowMap = getLeaveRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.CANCELLEAVEREQ, workflowMap.getRequestType())) {
					workflowMap = getLeaveCancelRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.CONVERTLEAVEREQ, workflowMap.getRequestType())) {
					workflowMap = getLeaveConvertRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.DEMAND, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CANCELDEMAND, workflowMap.getRequestType())) {
					workflowMap = getDemandRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.VOUCHER, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CERTIFICATESANCTION, workflowMap.getRequestType())) {
					workflowMap = getVoucherRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.INVOICE, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CANCELIR, workflowMap.getRequestType())) {
					workflowMap = getInvoiceRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS, workflowMap.getRequestType())) {
					workflowMap = getCghsRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS_ADVANCE, workflowMap.getRequestType())) {
					workflowMap = getCghsRequestAdvanceDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS_REIMBURSEMENT, workflowMap.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.CGHS_SETTLEMENT, workflowMap.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.NON_CGHS_REIMBURSEMENT, workflowMap.getRequestType())) {
					workflowMap = getCghsReimbursementDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.CGHS_EMERGENCY, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.NON_CGHS_EMERGENCY, workflowMap.getRequestType())) {
					workflowMap = getCghsEmergencyDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.LTCREFUNDVALUE, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LTC_CANCEL, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LTC_APPR_CUM_ADV_CANCEL, workflowMap.getRequestType())) {
					workflowMap = getLTCRefundDetails(workflowMap);
					workflowMap = getDopartShowFlag(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.LTCREIMBURSEMENT, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LTCSETTLEMENT, workflowMap.getRequestType())) {
					workflowMap.setLtcApprovalRequestDTO(getLtcReimbursementDetails(workflowMap.getRequestId(), workflowMap.getRequestType(), workflowMap.getSfid()));
					workflowMap.setIpAddress(workflowMap.getLtcApprovalRequestDTO().getIpAddress());
					workflowMap.setAmount(workflowMap.getLtcApprovalRequestDTO().getPrevAmount());
					workflowMap.setLtcJourneyDetails(getLtcJourneyDetails(workflowMap.getRequestId()));
				} else if (CPSUtils.compareStrings(CPSConstants.LTC_APP_ADV, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LTCADVANCE, workflowMap.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.LTCAPPROVAL, workflowMap.getRequestType())) {

					workflowMap = getLtcRequestDetails(workflowMap);
					workflowMap = getDopartShowFlag(workflowMap);
					if(CPSUtils.compareStrings(workflowMap.getDoPartShowFlag(),"Y")){
						workflowMap =getDopartDateList(workflowMap);
					}
					if (!CPSUtils.isNull(workflowMap.getLtcAdvanceRequestDTO())) {
						workflowMap.setIpAddress(workflowMap.getLtcAdvanceRequestDTO().getIpAddress());
						workflowMap.setRequestType(workflowMap.getLtcAdvanceRequestDTO().getLtcRequestType());
					} else {
						workflowMap.setIpAddress(workflowMap.getLtcApprovalRequestDTO().getIpAddress());
						workflowMap.setRequestType(workflowMap.getLtcApprovalRequestDTO().getLtcRequestType());
					}
				} else if (CPSUtils.compareStrings(CPSConstants.SELFTRANSFERREQUESTTYPE, workflowMap.getRequestType())
						|| CPSUtils.compareStrings(CPSConstants.EMPTRANSFERREQUESTTYPE, workflowMap.getRequestType())) {
					workflowMap = getEmpTransferRequestDetails(workflowMap);
				} else if (CPSUtils.compareStrings(CPSConstants.LOAN, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.LOANCONVEYANCE, workflowMap.getRequestType()) ) {
					/*workflowMap = loanRequestProcess.getLoanRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getLoanRequestDetails().getIpAddress());*/
					workflowMap = erpLoanRequestProcess.getErpLoanRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getErpLoanRequestDTO().getIpAddress());
				} else if (CPSUtils.compareStrings(CPSConstants.HOUSEBUILDINGLOAN, workflowMap.getRequestType())) {
					workflowMap = loanHBARequestProcess.getLoanRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getLoanHBARequestDetails().getIpAddress());
				} else if (CPSUtils.compareStrings(CPSConstants.NEWQUARTERREQ, workflowMap.getRequestType())) {
					workflowMap = quarterRequestProcess.getQuarterRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getQuarterDetails().getIpAddress());
				} else if (CPSUtils.compareStrings(CPSConstants.CHANGEQUARTERREQ, workflowMap.getRequestType())) {
					workflowMap = quarterRequestProcess.getQuarterRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getQuarterDetails().getIpAddress());
				}else if (CPSUtils.compareStrings(CPSConstants.HIGHER_QUALIFICATION, workflowMap.getRequestType())) {
					workflowMap = hqRequestProcess.getHQRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getHqRequestDTO().getIpAddress());
				}  else if (CPSUtils.compareStrings(CPSConstants.FPA, workflowMap.getRequestType())) {
					workflowMap = fpaRequestProcess.getFpaRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getFpaRequestDetails().getIpAddress());
				}else if (CPSUtils.compareStrings(CPSConstants.PASSPORT, workflowMap.getRequestType())) {
					//workflowMap = passportRequestProcess.getPassportRequestDetails(workflowMap);
					//workflowMap.setIpAddress(workflowMap.getPassportRequestDetails().getIpAddress());
				} else if (CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, workflowMap.getRequestType())){
					workflowMap = tadaRequestProcess.getTadaTdRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getTadaApprovalRequestDTO().getIpAddress());
				} else if (CPSUtils.compareStrings(CPSConstants.TADA_WATER, workflowMap.getRequestType())){
					workflowMap = tadaWaterRequestProcess.getTadaTdRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getTadaWaterApprovalRequestDTO().getIpAddress());
				}  
				 else if (CPSUtils.compareStrings(CPSConstants.LTC_WATER, workflowMap.getRequestType())){
						workflowMap = ltcWaterRequestProcess.getLTCRequestDetails(workflowMap);
						workflowMap.setIpAddress(workflowMap.getLtcWaterRequestDTO().getIpAddress());
						//workflowMap.setIpAddress(workflowMap.getTadaWaterApprovalRequestDTO().getIpAddress());
					} 
				 else if (CPSUtils.compareStrings(CPSConstants.ERP_LEAVE, workflowMap.getRequestType())){
					 //added by bkr 13/06/2016
						workflowMap = leaveWaterRequestProcess.getLeaveRequestDetails(workflowMap);
						//workflowMap.setIpAddress(workflowMap.getLtcWaterRequestDTO().getIpAddress());
						workflowMap.setIpAddress(workflowMap.getErpAvailableLeaveSaveDTO().getIpAddress());
					}
				 else if (CPSUtils.compareStrings(CPSConstants.ERP_LEAVE_CANCEL, workflowMap.getRequestType())){
					 //added by bkr 21/06/2016
					 
					 //get requestid  if cancel request
					 workflowMap = leaveWaterRequestProcess.getCancelRequestID(workflowMap);
					 
						workflowMap = leaveWaterRequestProcess.getLeaveRequestDetailsCancel(workflowMap);
						workflowMap.setIpAddress(workflowMap.getErpAvailableLeaveSaveDTO().getIpAddress());
					}
				else if(CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, workflowMap.getRequestType())){
					workflowMap = tadaRequestProcess.getTadaTdRequestDetails(workflowMap);
					workflowMap.setIpAddress(workflowMap.getTadaApprovalRequestDTO().getIpAddress());
					workflowMap = tadaRequestProcess.getPrjDirList(workflowMap);
					tadaRequestProcess.getProjectDirSfid(workflowMap);
				} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, workflowMap.getRequestType())){
					TadaRequestBean tadaRequestBean = new TadaRequestBean();
					
					workflowMap = tadaRequestProcess.getTadatdAdvReqDetails(workflowMap);
					tadaRequestBean.setRequestId(workflowMap.getTadaTdAdvanceRequestDTO().getReferenceRequestID());
					workflowMap.setPrjlist(tadaRequestProcess.getProjList(tadaRequestBean));
					
					
					workflowMap.setIpAddress(workflowMap.getTadaTdAdvanceRequestDTO().getIpAddress());
				} else if(CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, workflowMap.getRequestType())){
					//workflowMap.setTdDaNewDetailsList(tadaRequestProcess.getTadaTdDaNewDetails(workflowMap));
					workflowMap.setTdDaNewAccDetailsList(tadaRequestProcess.getTadaTdDaNewAccDetails(workflowMap));
					workflowMap.setTdDaNewFoodDetailsList(tadaRequestProcess.getTadaTdDaNewFoodDetails(workflowMap));
					workflowMap.setTdDaOldDetailsList(tadaRequestProcess.getTadaTdDaOldDetails(workflowMap));
					workflowMap.setTdRMAKmList(tadaRequestProcess.getTadaTdRMAKmDetails(workflowMap));
					workflowMap.setTdRMADailyList(tadaRequestProcess.getTadaTdRMADailyDetails(workflowMap));
					workflowMap.setTdRMADayList(tadaRequestProcess.getTadaTdRMADayDetails(workflowMap));
					workflowMap.setTdRMALocalList(tadaRequestProcess.getTadaTdRMALocalDetails(workflowMap));
					workflowMap.setTdJourneyList(tadaRequestProcess.getTadaTdJourneyDetails(workflowMap));
					workflowMap.setEmpClaimDetailsList(tadaRequestProcess.getEmpClaimDetails(workflowMap));
					workflowMap.setCdaDetailsDTO(tadaRequestProcess.getCdaDetails(workflowMap));
					workflowMap.setLtcPenalMasterList(tadaRequestProcess.getPenalInterest());
					workflowMap.setLtcPenalInterestRate(tadaRequestProcess.getPenalInterestRate());
					workflowMap.setTdTotalFoodDetails(tadaRequestProcess.getTdFoodDayTotalDetails(workflowMap));
					workflowMap = tadaRequestProcess.getTadatdAdvReqDetailsList(workflowMap);
					workflowMap.setTdLeaveDetailsList(tadaRequestProcess.getTdAttachedLeaveList(workflowMap));
					workflowMap.setMroDetailsList(tadaRequestProcess.getMRODetails(workflowMap));
					workflowMap.setMroPaymentDetailsList(tadaRequestProcess.getMROPaymentDetailsDTO(workflowMap));
					workflowMap.setUserRemarks(tadaRequestProcess.getTdUserRemarks(workflowMap));
					if(!CPSUtils.isNullOrEmpty(workflowMap.getTadaTdAdvanceRequestDTO())){
						workflowMap.setIpAddress(workflowMap.getTadaTdAdvanceRequestDTO().getIpAddress());
					}
					workflowMap.setTdSettStrStatus(workflowMap.getWorkflowHistory().get(workflowMap.getWorkflowHistory().size()-1).getStatus());
				}
				else if(CPSUtils.compareStrings(CPSConstants.TRAININGNOMINATION, workflowMap.getRequestType()) || CPSUtils.compareStrings(CPSConstants.CANCELTRAININGNOMINATION, workflowMap.getRequestType())){
					if(!CPSUtils.isNullOrEmpty(workflowMap.getRequestId()))
					{
					
					workflowMap.setIpAddress(trainingRequestProcess.getReqIPAddress(workflowMap.getRequestId(),workflowMap.getRequestType()));
					}
				}
				else {
					workflowMap = getTxnRequestDetails(workflowMap);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return workflowMap;
	}
	/*public List<TadaTdDaNewFoodDetailsDTO> getchangedFoodDetails(WorkFlowMappingBean workflowMap)throws Exception {
		List<TadaTdDaNewFoodDetailsDTO> tdChangedFoodDetails = null;
		try {
			tdChangedFoodDetails=tadaRequestProcess.getTadaTdDaNewFoodDetails(workflowMap);
		}
		catch (Exception e) {
			throw e;
		}
		return tdChangedFoodDetails;
	}*/
	public WorkFlowMappingBean getEmpTransferRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session1 = null;
		EmpTransferBean transferBean = null;
		try {
			session1 = hibernateUtils.getSession();

			transferBean = (EmpTransferBean) session1.createCriteria(EmpTransferBean.class).add(Restrictions.eq("requestID", workflowMap.getRequestId())).uniqueResult();
			if (!CPSUtils.isNull(transferBean)) {
				workflowMap.setIpAddress(transferBean.getIpAddress());
				transferBean.setDepartmentFrom(((DepartmentsDTO) session1.get(DepartmentsDTO.class, transferBean.getDepartmentFromID())).getDeptName());
				if (transferBean.getTransferTypeID() == Integer.valueOf(CPSConstants.INTERNALSTATUSID)) {
					transferBean.setDepartmentTo(((DepartmentsDTO) session1.get(DepartmentsDTO.class, Integer.valueOf(transferBean.getDepartmentTo()))).getDeptName());
				}
				workflowMap.setTransferRequestDetails(transferBean);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return workflowMap;
	}

	public ArrayList<NomineeBean> getIncontengensyDetails(String requestID) throws Exception {
		Session session2 = null;
		PreparedStatement ps1 = null;
		ResultSet rsq2 = null;
		Connection con = null;
		ArrayList<NomineeBean> contengensyList = null;
		try {
			session2 = hibernateUtils.getSession(); //session2 = sessionFactory.openSession();
			con = session2.connection();
			contengensyList = new ArrayList<NomineeBean>();

			String getTxnDetails = "SELECT row_number,reference_number,MAX(CASE WHEN column_name = 'SFID' THEN new_value END) AS SFID,"
					+ "MAX(CASE WHEN column_name = 'NOMINEE' THEN new_value END) AS NOMINEE,MAX(CASE WHEN column_name = 'PERCENTAGE' THEN new_value END) AS PERCENTAGE,"
					+ "MAX(CASE WHEN column_name = 'REMARKS' THEN new_value END) AS REMARKS, MAX(CASE WHEN column_name = 'DATE_OF_NOMINATE' THEN new_value END) AS DATE_OF_NOMINATE,"
					+ "MAX(CASE WHEN column_name = 'FAMILY_MEMBER_ID' THEN new_value END) AS FAMILY_MEMBER_ID,MAX(CASE WHEN column_name = 'NOMINEE_TYPE_ID' THEN new_value END) AS NOMINEE_TYPE_ID,"
					+ "MAX(CASE WHEN column_name = 'ADDRESS1' THEN new_value END) AS ADDRESS1,MAX(CASE WHEN column_name = 'ADDRESS2' THEN new_value END) AS ADDRESS2,"
					+ "MAX(CASE WHEN column_name = 'ADDRESS3' THEN new_value END) AS ADDRESS3,MAX(CASE WHEN column_name = 'CITY' THEN new_value END) AS CITY,"
					+ "MAX(CASE WHEN column_name = 'STATE_ID' THEN new_value END) AS STATE_ID,MAX(CASE WHEN column_name = 'DISTRICT_ID' THEN new_value END) AS DISTRICT_ID,"
					+ "MAX(CASE WHEN column_name = 'PINCODE' THEN new_value END) AS PINCODE FROM workflow_txn_details where request_id=? and reference_number='INC' GROUP BY row_number, reference_number order by row_number";

			ps1 = con.prepareStatement(getTxnDetails);
			ps1.setString(1, requestID);
			rsq2 = ps1.executeQuery();
			while (rsq2.next()) {
				NomineeBean nomBean = new NomineeBean();
				nomBean.setNomineeName(rsq2.getString("nominee"));
				if (CPSUtils.compareStrings(rsq2.getString("family_member_id"), "other")) {
					nomBean.setFamilyID("other");
				} else {
					nomBean.setFamilyID(getMasterValue(CPSConstants.FAMILY_DETAILS, CPSConstants.ID, rsq2.getString("family_member_id")));
				}
				nomBean.setDateOfNominate(rsq2.getString("date_of_nominate"));
				nomBean.setPercentage(rsq2.getString("percentage"));
				nomBean.setRemarks(rsq2.getString("remarks"));

				nomBean.setAddress1(rsq2.getString("address1"));
				nomBean.setAddress2(rsq2.getString("address2"));
				nomBean.setAddress3(rsq2.getString("address3"));
				nomBean.setCity(rsq2.getString("city"));
				nomBean.setStateId(getMasterValue(CPSConstants.STATE_MASTER, CPSConstants.ID, rsq2.getString("state_id")));
				nomBean.setDistrictId(getMasterValue(CPSConstants.DISTRICT_MASTER, CPSConstants.ID, rsq2.getString("district_id")));
				nomBean.setPincode(rsq2.getString("pincode"));
				contengensyList.add(nomBean);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session2, ps1, rsq2);
		}
		return contengensyList;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getLeaveCancelRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		//Session session2 = null;
		CancelLeaveRequestBean cancelLeaveRequest = null;
		List<DoPartDTO> cancelLeaveDoPartList = null;
		try {
			session = hibernateUtils.getSession();
			String role = (String) session.createSQLQuery("select distinct to_char(case when rm.id in(1,2,14) then 8 else null  end)id from role_master rm,   application_role_mapping apm WHERE rm.id   = apm.role_id and rm.status =1 AND apm.sfid  =? and apm.status=1 AND rm.id    IN (1,2,14)").setString(0, workflowMap.getSfid()).uniqueResult();
			workflowMap.setOrgroleIdLink(role);
			
			cancelLeaveRequest = (CancelLeaveRequestBean) session.createCriteria(CancelLeaveRequestBean.class).add(Restrictions.eq(CPSConstants.REQUESTID, workflowMap.getRequestId())).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(cancelLeaveRequest.getReferenceDetails().getOtherRemarks()) && cancelLeaveRequest.getReferenceDetails().getOtherRemarks().contains(CPSConstants.PROCEED)) {
				cancelLeaveRequest.getReferenceDetails().setFromDate(CPSUtils.addDays(cancelLeaveRequest.getReferenceDetails().getStrFromDate(), String.valueOf(-Integer.parseInt(cancelLeaveRequest.getReferenceDetails().getPrevHolidays()))));
				cancelLeaveRequest.getReferenceDetails().setToDate(CPSUtils.addDays(cancelLeaveRequest.getReferenceDetails().getStrToDate(), cancelLeaveRequest.getReferenceDetails().getNextHolidays()));
			}
			//cancelLeaveDoPart = (DoPartDTO) session1.createCriteria(DoPartDTO.class).add(Expression.eq(CPSConstants.ID, Integer.parseInt(cancelLeaveRequest.getLeaveCanceldOPartId()))).uniqueResult();		
			//code  for getting Dopartno and DopartDate's ::::::::::::::::::added by Rakesh 			
			if (!CPSUtils.isNullOrEmpty(cancelLeaveRequest.getLeaveCanceldOPartId())) {
				cancelLeaveDoPartList = session.createSQLQuery("select Ref_number as leaveCancelDopartno ,ref_date as leaveCancelDopartDate from REFERENCE_NUMBER_DETAILS where id=?").addScalar("leaveCancelDopartno", Hibernate.STRING).addScalar("leaveCancelDopartDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).setInteger(0,Integer.parseInt(cancelLeaveRequest.getLeaveCanceldOPartId())).list();
				workflowMap.setCancelLeaveDoPartList(cancelLeaveDoPartList);
				workflowMap.setLeaveCancelDopartno(workflowMap.getCancelLeaveDoPartList().get(0).getLeaveCancelDopartno());
				workflowMap.setLeaveCancelDopartDate(workflowMap.getCancelLeaveDoPartList().get(0).getLeaveCancelDopartDate());
			}
			workflowMap.setIpAddress(cancelLeaveRequest.getIpAddress());
			workflowMap.setLeaveRequestDetails(cancelLeaveRequest.getReferenceDetails());
			workflowMap.setRequestExceptionList(session.createCriteria(LeaveRequestExceptionsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, cancelLeaveRequest.getReferenceID())).list());
			workflowMap.setReason(cancelLeaveRequest.getReason());
			workflowMap.setFormattedAppliedDate(cancelLeaveRequest.getFormattedRequestedDate());
			workflowMap.setLeaveRequestID(String.valueOf(cancelLeaveRequest.getReferenceDetails().getRequestID()));
					
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return workflowMap;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getLeaveConvertRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session1 = null;
		Session session2 = null;
		List<ConvertLeaveRequestBean> convertList = null;
		ConvertLeaveRequestBean convertBean = null;
		List<DoPartDTO> convertLeaveDoPartList = null;
		try {
			session1 = hibernateUtils.getSession();
			session2 = hibernateUtils.getSession();
			String role = (String) session1.createSQLQuery("select distinct to_char(case when rm.id in(1,2,14) then 8 else null  end)id from role_master rm,   application_role_mapping apm WHERE rm.id   = apm.role_id and rm.status =1 AND apm.sfid  =? and apm.status=1 AND rm.id    IN (1,2,14)").setString(0, workflowMap.getSfid()).uniqueResult();
			workflowMap.setOrgroleIdLink(role);
			convertList = session1.createCriteria(ConvertLeaveRequestBean.class).add(Restrictions.eq("requestID", Integer.valueOf(workflowMap.getRequestId()))).list();
			//code for getting Dopartno and DopartDate's ::::::::::::::::::added by Rakesh  
			if (!CPSUtils.isNullOrEmpty(convertList.get(0).getLeaveConversionDopartId())) {
				convertLeaveDoPartList = session1.createSQLQuery("select Ref_number as leaveConversionDopartno, ref_date as leaveConversionDopartDate from REFERENCE_NUMBER_DETAILS where id=?").addScalar("leaveConversionDopartno", Hibernate.STRING).addScalar("leaveConversionDopartDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).setInteger(0,convertList.get(0).getLeaveConversionDopartId()).list();
				workflowMap.setConvertLeaveDoPartList(convertLeaveDoPartList);
				workflowMap.setLeaveConversionDopartDate(workflowMap.getConvertLeaveDoPartList().get(0).getLeaveConversionDopartDate());
				workflowMap.setLeaveConversionDopartno(workflowMap.getConvertLeaveDoPartList().get(0).getLeaveConversionDopartno());
			}
			for (int i = 0; i < convertList.size(); i++) {
				convertBean = (ConvertLeaveRequestBean) convertList.get(i);
				LeaveRequestBean reqBean = (LeaveRequestBean) session2.createCriteria(LeaveRequestBean.class).add(Expression.eq("convertionID", String.valueOf(convertBean.getId()))).uniqueResult();
				convertBean.setNewLeaveRequestID(reqBean.getRequestID());		
				workflowMap.setIpAddress(convertBean.getIpAddress());
				workflowMap.setLeaveRequestDetails(convertBean.getReferenceDetails());
				workflowMap.setRequestExceptionList(session1.createCriteria(LeaveRequestExceptionsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, convertBean.getReferenceID())).list());
				workflowMap.setReason(convertBean.getReason());
				workflowMap.setFormattedAppliedDate(convertBean.getFormattedRequestedDate());
				// workflowMap.setLeaveRequestID(String.valueOf(convertBean.getReferenceID()));
				workflowMap.setLeaveRequestID(String.valueOf(convertBean.getReferenceDetails().getRequestID()));
				//i = convertList.size();
			}
			workflowMap.setConversionDetails(convertList);
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return workflowMap;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getLeaveRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		LeaveRequestBean leaveBean = null;
		try {
			session = hibernateUtils.getSession();
			String role = (String) session.createSQLQuery("select distinct to_char(case when rm.id in(1,2,14) then 8 else null  end)id from role_master rm,   application_role_mapping apm WHERE rm.id   = apm.role_id and rm.status =1 AND apm.sfid  =? and apm.status=1 AND rm.id    IN (1,2,14)").setString(0, workflowMap.getSfid()).uniqueResult();
			workflowMap.setOrgroleIdLink(role);

			leaveBean = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Restrictions.eq("requestID", workflowMap.getRequestId())).uniqueResult();
			if (!CPSUtils.isNull(leaveBean)) {
				if (!CPSUtils.isNullOrEmpty(leaveBean.getOtherRemarks()) && leaveBean.getOtherRemarks().contains(CPSConstants.PROCEED)) {
					leaveBean.setFromDate(CPSUtils.addDays(leaveBean.getStrFromDate(), String.valueOf(-Integer.parseInt(leaveBean.getPrevHolidays()))));
					leaveBean.setToDate(CPSUtils.addDays(leaveBean.getStrToDate(), leaveBean.getNextHolidays()));
				} else {
					leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
					leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
				}
				workflowMap.setIpAddress(leaveBean.getIpAddress());
				workflowMap.setLeaveRequestDetails(leaveBean);
				workflowMap.setLeaveRequestID(leaveBean.getRequestID());
				workflowMap.setRequestExceptionList(session.createCriteria(LeaveRequestExceptionsDTO.class).add(Expression.eq("referenceID", Integer.valueOf(leaveBean.getId()))).list());
				if (!CPSUtils.isNullOrEmpty(leaveBean.getDoPartDetails())) {
					workflowMap.setDoPartNo(leaveBean.getDoPartDetails().getDoPartNo());
					workflowMap.setDoPartDate(CPSUtils.critFormattedDate(CPSUtils.formattedDate(leaveBean.getDoPartDetails().getDoPartDate())));
				}
				/*Object obj = null;
				if (!CPSUtils.isNullOrEmpty(leaveBean.getOtherRemarks())) {
					if (leaveBean.getOtherRemarks().contains(CPSConstants.AMENDMENT) && !CPSUtils.compareStrings(CPSConstants.STATUSCANCELLED, String.valueOf(leaveBean.getStatus())) && !CPSUtils.compareStrings(CPSConstants.STATUSDECLINED, String.valueOf(leaveBean.getStatus()))) {
						obj = session.createSQLQuery("SELECT lrd_request_id referenceId FROM leave_amendment_details WHERE lrd_amended_id = ?").setString(0, leaveBean.getRequestID()).uniqueResult();
						if (!CPSUtils.isNullOrEmpty(obj)) workflowMap.setReferenceID(obj.toString());
					}
				} else {
					if (CPSUtils.compareStrings(CPSConstants.STATUSCANCELLED, String.valueOf(leaveBean.getStatus())) && leaveBean.getOtherRemarks().contains(CPSConstants.AMENDMENT)) {
						obj = session.createSQLQuery("SELECT lrd_amended_id amendmentId FROM leave_amendment_details WHERE lrd_request_id = ?").setString(0, leaveBean.getRequestID()).uniqueResult();
						if (!CPSUtils.isNullOrEmpty(obj)) workflowMap.setAmendment(obj.toString());
					}
				}*/
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session1.close();
		}
		return workflowMap;
	}

	public WorkFlowMappingBean getTxnRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session1 = null;
		PreparedStatement ps1 = null;
		Connection con = null;
		ArrayList<TxnDetailsDTO> changedList = null;
		ResultSet rsq2 = null;
		String rowNumber = null;
		try {
			session1 = hibernateUtils.getSession();
			con = session1.connection();
			changedList = new ArrayList<TxnDetailsDTO>();
			StringBuffer getTxnDetails = new StringBuffer();
			if (CPSUtils.compareStrings(workflowMap.getRequestType(), "INVENTORYHOLDER")) {
				getTxnDetails
						.append("select row_number,description,previous_value,new_value,ip_address,column_name,reference_number from workflow_txn_details where request_id=? and column_name!='INVENTORY_NO'");
			} else {
				getTxnDetails
						.append("select row_number,description,previous_value,new_value,ip_address,column_name,reference_number from workflow_txn_details where request_id=? and column_name!='SFID' and reference_number!='INC' ");
			}
			ps1 = con.prepareStatement(getTxnDetails.toString());
			ps1.setString(1, workflowMap.getRequestId());
			rsq2 = ps1.executeQuery();
			while (rsq2.next()) {
				TxnDetailsDTO txnEmpDTO = new TxnDetailsDTO();
				txnEmpDTO.setFieldName(rsq2.getString("description"));
				if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.STATE) || CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.STATE_ID)) {
					txnEmpDTO.setFrom(getMasterValue(CPSConstants.STATE_MASTER, CPSConstants.ID, rsq2.getString("previous_value")));
					txnEmpDTO.setTo(getMasterValue(CPSConstants.STATE_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.DISTRICT) || CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.DISTRICT_ID)) {
					txnEmpDTO.setFrom(getMasterValue(CPSConstants.DISTRICT_MASTER, CPSConstants.ID, rsq2.getString("previous_value")));
					txnEmpDTO.setTo(getMasterValue(CPSConstants.DISTRICT_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.RELATION_ID)) {
					if (CPSUtils.compareStrings(CPSConstants.FAMILY, workflowMap.getRequestType())) {
						if (CPSUtils.compareStrings(rsq2.getString("reference_number"), "0")) {
							workflowMap.setRecordType("NEW");
							txnEmpDTO.setFrom(getMasterValue(CPSConstants.FAMILY_RELATION_MASTER, CPSConstants.ID, rsq2.getString("previous_value")));
							txnEmpDTO.setTo(getMasterValue(CPSConstants.FAMILY_RELATION_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
						} else {
							workflowMap.setRecordType("OLD");
							workflowMap.setFamilyMemberName(getMasterValue(CPSConstants.FAMILY_DETAILS, CPSConstants.ID, rsq2.getString("reference_number")));
						}
						workflowMap.setRelation(getMasterValue(CPSConstants.FAMILY_RELATION_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
					} else {
						txnEmpDTO.setFrom(getMasterValue(CPSConstants.FAMILY_RELATION_MASTER, CPSConstants.ID, rsq2.getString("previous_value")));
						txnEmpDTO.setTo(getMasterValue(CPSConstants.FAMILY_RELATION_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
					}
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.MARITAL_STATUS_ID)) {
					txnEmpDTO.setFrom(getMasterValue(CPSConstants.MARITAL_MASTER, CPSConstants.ID, rsq2.getString("previous_value")));
					txnEmpDTO.setTo(getMasterValue(CPSConstants.MARITAL_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.ADDRESS_TYPE_ID)) {
					txnEmpDTO.setFrom(getMasterValue(CPSConstants.ADDRESS_TYPE_MASTER, CPSConstants.ID, rsq2.getString("previous_value")));
					if (CPSUtils.compareStrings(rsq2.getString("new_value"), "100")) {
						txnEmpDTO.setTo("OTHER");
					} else {
						txnEmpDTO.setTo(getMasterValue(CPSConstants.ADDRESS_TYPE_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
					}
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.BLOOD_GROUP)) {
					txnEmpDTO.setFrom(getMasterValue(CPSConstants.BLOOD_GROUP_MASTER, CPSConstants.ID, rsq2.getString("previous_value")));
					txnEmpDTO.setTo(getMasterValue(CPSConstants.BLOOD_GROUP_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.FAMILY_MEMBER_ID)) {
					txnEmpDTO.setFrom(getMasterValue(CPSConstants.FAMILY_DETAILS, CPSConstants.ID, rsq2.getString("previous_value")));
					txnEmpDTO.setTo(getMasterValue(CPSConstants.FAMILY_DETAILS, CPSConstants.ID, rsq2.getString("new_value")));
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.NOMINEE_TYPE_ID)) {
					if (CPSUtils.compareStrings(rsq2.getString("reference_number"), "0")) {
						workflowMap.setRecordType("NEW");
					} else {
						workflowMap.setRecordType("OLD");
					}
					workflowMap.setNomineeType(getMasterValue(CPSConstants.NOMINEE_TYPE_MASTER, CPSConstants.ID, rsq2.getString("new_value")));
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.EMPLOYED_TYPE)) {
					if (CPSUtils.compareStrings(rsq2.getString("previous_value"), "20")) {
						txnEmpDTO.setFrom("Private");
					} else if (CPSUtils.compareStrings(rsq2.getString("previous_value"), "21")) {
						txnEmpDTO.setFrom("Govt");
					}
					if (CPSUtils.compareStrings(rsq2.getString("new_value"), "20")) {
						txnEmpDTO.setTo("Private");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "21")) {
						txnEmpDTO.setTo("Govt");
					}

				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), "DISABILITY_ID")) {
					if (CPSUtils.compareStrings(rsq2.getString("previous_value"), "0")) {
						txnEmpDTO.setFrom("NA");
					}
					if (CPSUtils.compareStrings(rsq2.getString("new_value"), "1")) {
						txnEmpDTO.setTo("Blindness");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "2")) {
						txnEmpDTO.setTo("Low Vision");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "3")) {
						txnEmpDTO.setTo("Leprosy Cured");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "4")) {
						txnEmpDTO.setTo("Hearing Impairment Cured");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "5")) {
						txnEmpDTO.setTo("Locomototr Disability");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "6")) {
						txnEmpDTO.setTo("Mental Retardation");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "7")) {
						txnEmpDTO.setTo("Mental Illness");
					}
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), "PH_FLAG")) {
					if (CPSUtils.compareStrings(rsq2.getString("previous_value"), "Y")) {
						txnEmpDTO.setFrom("Yes");
					} else if (CPSUtils.compareStrings(rsq2.getString("previous_value"), "N")) {
						txnEmpDTO.setFrom("No");
					}
					if (CPSUtils.compareStrings(rsq2.getString("new_value"), "Y")) {
						txnEmpDTO.setTo("Yes");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "N")) {
						txnEmpDTO.setTo("No");
					}
				} else if (CPSUtils.compareStrings(rsq2.getString("column_name"), "RESIDING_WITH")) {
					if (CPSUtils.compareStrings(rsq2.getString("previous_value"), "Y")) {
						txnEmpDTO.setFrom("Yes");
					} else if (CPSUtils.compareStrings(rsq2.getString("previous_value"), "N")) {
						txnEmpDTO.setFrom("No");
					}
					if (CPSUtils.compareStrings(rsq2.getString("new_value"), "Y")) {
						txnEmpDTO.setTo("Yes");
					} else if (CPSUtils.compareStrings(rsq2.getString("new_value"), "N")) {
						txnEmpDTO.setTo("No");
					}

				} else {
					txnEmpDTO.setFrom(rsq2.getString("previous_value"));
					txnEmpDTO.setTo(rsq2.getString("new_value"));
				}
				rowNumber = rsq2.getString("row_number");

				if (!(CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.RELATION_ID) && CPSUtils.compareStrings(CPSConstants.FAMILY, workflowMap.getRequestType()))
						&& !(CPSUtils.compareStrings(rsq2.getString("column_name"), CPSConstants.NOMINEE_TYPE_ID) && CPSUtils.compareStrings(CPSConstants.NOMINEE, workflowMap.getRequestType()))) {
					changedList.add(txnEmpDTO);
				}

				workflowMap.setIpAddress(rsq2.getString("ip_address"));
			}
			workflowMap.setRowNumber(getMasterValue(CPSConstants.ADDRESS_TYPE_MASTER, CPSConstants.ID, rowNumber) + " ");
			workflowMap.setModifiedValues(changedList);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session1, ps1, rsq2);
		}
		return workflowMap;
	}

	public TxnDetailsDTO getListValues(String fieldName, String from, String to) throws Exception {
		TxnDetailsDTO txnEmpDTO = null;
		try {
			txnEmpDTO = new TxnDetailsDTO();
			txnEmpDTO.setFieldName(fieldName);
			txnEmpDTO.setFrom(from);
			txnEmpDTO.setTo(to);
		} catch (Exception e) {
			throw e;
		}
		return txnEmpDTO;
	}

	public String getMasterValue(String tableName, String columnName, String id) throws Exception {
		Session session = null;
		PreparedStatement ps2 = null;
		ResultSet rsq3 = null;
		Connection con = null;
		String value = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String getState = "select name from " + tableName + " where " + columnName + "=?";
			ps2 = con.prepareStatement(getState);
			ps2.setString(1, id);
			rsq3 = ps2.executeQuery();
			if (rsq3.next()) {
				value = rsq3.getString("name");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps2, rsq3);
		}
		return value;
	}

	@Override
	public List getRequestTypes() throws Exception {
		List requestTypeList = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select request_type_id,request_type from request_master rm where rm.status='1'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			requestTypeList = new ArrayList();
			while (rs.next()) {
				RequestsDTO reqdto = new RequestsDTO();
				reqdto.setRequestType(rs.getString("request_type_id"));
				reqdto.setRequestTypeName(rs.getString("request_type"));
				requestTypeList.add(reqdto);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rs);
		}
		return requestTypeList;
	}

	@Override
	public List<EmployeeBean> getEmployeeList() throws Exception {
		List<EmployeeBean> employeeList = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select sfid,name_in_service_book from emp_master  where status='1' Order By Sfid";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			employeeList = new ArrayList<EmployeeBean>();
			while (rs.next()) {
				EmployeeBean empbean = new EmployeeBean();
				empbean.setId(rs.getString("sfid"));
				empbean.setName(rs.getString("name_in_service_book"));
				employeeList.add(empbean);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rs);
		}
		return employeeList;
	}

	@Override
	public List getSearchRequestList(RequestBean rb) throws Exception {
		List employeeList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			StringBuffer sb = new StringBuffer();
			sb
					.append("select reqwork.request_id,assigned_to,emp.name_in_service_book,req.request_type,reqwork.id historyID,sm.status from request_workflow_history reqwork,emp_master emp,request_master req,status_master sm ");
			sb.append(" where reqwork.status='2' and sm.id=reqwork.status and reqwork.assigned_to=emp.sfid ");
			if (!CPSUtils.isNullOrEmpty(rb.getSfID()) && !CPSUtils.compareStrings(rb.getSfID(), "select"))
				sb.append(" and reqwork.assigned_to= :sfid");
			sb.append(" and reqwork.request_type_id=req.request_type_id ");
			if (!CPSUtils.isNullOrEmpty(rb.getRequestTypeID()) && !CPSUtils.compareStrings(rb.getRequestTypeID(), "select"))
				sb.append(" and reqwork.request_type_id=:reqtype");
			if (!CPSUtils.isNullOrEmpty(rb.getRequestID()) && !CPSUtils.compareStrings(rb.getRequestID(), "select"))
				sb.append(" and reqwork.request_id=:reqid");

			Query qry = session.createSQLQuery(sb.toString());
			if (!CPSUtils.isNullOrEmpty(rb.getSfID()) && !CPSUtils.compareStrings(rb.getSfID(), "select"))
				qry.setString("sfid", rb.getSfID());
			if (!CPSUtils.isNullOrEmpty(rb.getRequestTypeID()) && !CPSUtils.compareStrings(rb.getRequestTypeID(), "select"))
				qry.setString("reqtype", rb.getRequestTypeID());
			if (!CPSUtils.isNullOrEmpty(rb.getRequestID()) && !CPSUtils.compareStrings(rb.getRequestID(), "select"))
				qry.setString("reqid", rb.getRequestID());
			employeeList = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return employeeList;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getDemandRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		List<Object> itemSubCatList = null;
		List<DemandItemDetailsDTO> demandItems = null;
		HashMap<String, Object> hm = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (CPSUtils.compareStrings(CPSConstants.DEMAND, workflowMap.getRequestType())) {
				hm = (HashMap<String, Object>) session
						.createSQLQuery(
								"select demand_no as demandNo,inv.INVENTORY_NO as invId,account.ACCOUNT_HEAD_NUMBER as accHeadNo,demand.INVENTORY_NO as inventoryNo,demand.ACCOUNT_HEAD_ID as accountHeadId,demand.DEMAND_DATE as demandDate,demand.MMG_CONTROL_NO as mmgControlNo,demand.reason as reason,"
										+ " demand.MMG_CONTROL_DATE as mmgControlDate,demand.IP_ADDRESS as ipAddress,demand.TOTAL_COST as totalCost from MMG_B_DEMAND_MASTER demand,MMG_B_INVENTORY_HOLDER inv,MMG_B_ACCOUNT_HEAD_MASTER account where request_id=? and demand.INVENTORY_NO=inv.id and demand.ACCOUNT_HEAD_ID=account.id")
						.addScalar("demandNo", Hibernate.STRING).addScalar("invId", Hibernate.STRING).addScalar("accHeadNo", Hibernate.STRING).addScalar("inventoryNo", Hibernate.STRING).addScalar(
								"accountHeadId", Hibernate.STRING).addScalar("demandDate", Hibernate.DATE).addScalar("mmgControlNo", Hibernate.STRING).addScalar("mmgControlDate", Hibernate.DATE)
						.addScalar("ipAddress").addScalar("reason").addScalar("totalCost", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(
								Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
			} else {
				hm = (HashMap<String, Object>) session
						.createSQLQuery(
								"select demand_no as demandNo,inv.INVENTORY_NO as invId,account.ACCOUNT_HEAD_NUMBER as accHeadNo,demand.INVENTORY_NO as inventoryNo,demand.ACCOUNT_HEAD_ID as accountHeadId,demand.DEMAND_DATE as demandDate,demand.MMG_CONTROL_NO as mmgControlNo,demand.reason as reason,"
										+ " demand.MMG_CONTROL_DATE as mmgControlDate,demand.IP_ADDRESS as ipAddress,demand.TOTAL_COST as totalCost from MMG_B_DEMAND_MASTER demand,MMG_B_INVENTORY_HOLDER inv,MMG_B_ACCOUNT_HEAD_MASTER account where cancel_request_id=? and demand.INVENTORY_NO=inv.id and demand.ACCOUNT_HEAD_ID=account.id")
						.addScalar("demandNo", Hibernate.STRING).addScalar("invId", Hibernate.STRING).addScalar("accHeadNo", Hibernate.STRING).addScalar("inventoryNo", Hibernate.STRING).addScalar(
								"accountHeadId", Hibernate.STRING).addScalar("demandDate", Hibernate.DATE).addScalar("mmgControlNo", Hibernate.STRING).addScalar("mmgControlDate", Hibernate.DATE)
						.addScalar("ipAddress").addScalar("reason").addScalar("totalCost", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(
								Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
			}
			DemandRequestBean demandRequest = new DemandRequestBean();
			BeanUtils.copyProperties(demandRequest, hm);

			String sql = "select mmd.id as materialCode,mmd.MATERIAL_NAME as description,mmd.CONSUMABLE_FLAG as cflag,ditem.UNIT_RATE as unitRate, uom.name as uom,amount.name as amountType,ditem.QTY as qty,ditem.uom as uomConvert,"
					+ " (ditem.qty*ditem.UNIT_RATE) as amount,ditem.stage_Id as stageId,ditem.amount_type as amountTypeId from MMG_B_MATERIAL_MASTER mmd,MMG_B_UOM_MASTER uom,MMG_B_AMOUNT_TYPE_MASTER amount,MMG_B_DEMAND_ITEM_DETAILS ditem where ditem.MATERIAL_CODE=mmd.id and ditem.demand_no=? "
					+ " and  stage_id=(select max(stage_id) from MMG_B_DEMAND_ITEM_DETAILS where demand_no=?) and ditem.AMOUNT_TYPE=amount.id and mmd.status=1 and mmd.uom=uom.id and uom.status=1";
			itemSubCatList = session.createSQLQuery(sql).addScalar("materialCode").addScalar("description").addScalar("cflag").addScalar("amount").addScalar("stageId", Hibernate.STRING).addScalar(
					"uomConvert", Hibernate.STRING).addScalar("unitRate").addScalar("uom").addScalar("amountType").addScalar("amountTypeId", Hibernate.STRING).addScalar("qty").setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).setString(0, hm.get("demandNo").toString()).setString(1, hm.get("demandNo").toString()).list();
			demandItems = new ArrayList();
			Float total = 0.0f;
			for (int i = 0; i < itemSubCatList.size(); i++) {
				HashMap map = (HashMap) itemSubCatList.get(i);
				DemandItemDetailsDTO items = new DemandItemDetailsDTO();
				BeanUtils.copyProperties(items, map);
				demandItems.add(items);
			}
			demandRequest.setDemandItems(demandItems);
			String dept = getEmployeeDept(workflowMap.getSfid());
			if (!CPSUtils.isNull(dept))
				demandRequest.setApprovedDept(dept);
			else
				demandRequest.setApprovedDept("");
			if (CPSUtils.compareStrings(dept, CPSConstants.BUDGET)) {
				List<Object> amountTypeList = session.createSQLQuery("select id,name from MMG_B_AMOUNT_TYPE_MASTER").addScalar("id").addScalar("name").setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP).list();
				demandRequest.setAmountTypeList(amountTypeList);
			}
			workflowMap.setApprovedDept(dept);
			workflowMap.setDemandRequestDetails(demandRequest);
			workflowMap.setIpAddress(hm.get("ipAddress").toString());
			workflowMap = getPaymentDetails(workflowMap);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMap;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getVoucherRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "Select Vm.Voucher_id As voucherId,Vm.Voucher_type_id As voucherTypeId,Vm.Inventory_no As inventoryNo,Vm.Issuing_unit As issuingUnit,Vm.Location As location,Vm.Posted_by As postedBy,Vm.Posting_date As postingDate,"
					+ "Vm.Receiving_unit as receivingUnit,Vm.Reference_number as referenceNo,Vm.Request_id as requestID,Vm.DIVISION_CONTROL_NUMBER as divControlNum,Vm.WORK_DETAILS as workDetails,Vm.WORK_AMOUNT as workAmount,Ih.Inventory_no as invNum,Vtm.Voucher_type_name as vouherTypeName From Mmg_b_voucher_master Vm,Mmg_b_voucher_type_master Vtm,"
					+ "Mmg_b_inventory_holder ih Where vm.request_id=? and vm.status=1 and ih.id=vm.inventory_no and ih.status=1 and vtm.id=vm.voucher_type_id and vtm.status=1";

			VoucherMasterBean vmaster = (VoucherMasterBean) session.createSQLQuery(sql).addScalar("voucherId").addScalar("voucherTypeId").addScalar("inventoryNo").addScalar("issuingUnit").addScalar(
					"location").addScalar("postedBy").addScalar("postingDate", Hibernate.STRING).addScalar("receivingUnit").addScalar("referenceNo").addScalar("requestID").addScalar("divControlNum")
					.addScalar("workDetails").addScalar("workAmount", Hibernate.STRING).addScalar("invNum").addScalar("vouherTypeName").setResultTransformer(
							Transformers.aliasToBean(VoucherMasterBean.class)).setString(0, workflowMap.getRequestId()).uniqueResult();
			// String voucherId = (String) session.createSQLQuery("select voucher_id from MMG_B_VOUCHER_MASTER where request_id=?").setString(0, workflowMap.getRequestId()).uniqueResult();
			// VoucherMasterBean vmaster = (VoucherMasterBean) session.createCriteria(VoucherMasterBean.class).add(Expression.eq("voucherId", voucherId)).uniqueResult();
			session.flush();//tx.commit() ;
			if (!CPSUtils.isNullOrEmpty(vmaster.getVoucherId())) {
				//tx = session.beginTransaction();
				String str = "select vid.id as id,vid.consumable_flag as cflag,vid.material_code as materialCode,vid.qty as qty,vid.uom as uom,vid.voucher_id as voucherId,mm.material_name as materialName,vid.UOM_TOBECONVERT as uomConvert,vid.CONSUMABLE_FLAG_TOBECONVERT as cncConvert from mmg_b_voucher_item_details vid,mmg_b_material_master mm where mm.id=vid.material_code and vid.status=1 and mm.status=1 and vid.voucher_id=?";
				vmaster.setVoucherItems(session.createSQLQuery(str).addScalar("id", Hibernate.INTEGER).addScalar("cflag").addScalar("materialCode").addScalar("qty").addScalar("uom").addScalar(
						"voucherId").addScalar("materialName").addScalar("uomConvert").addScalar("cncConvert").setResultTransformer(Transformers.aliasToBean(VoucherItemDetailsDTO.class)).setString(0,
						vmaster.getVoucherId()).list());
				session.flush();//tx.commit() ;
			}
			VoucherRequestBean voucherRequestBean = new VoucherRequestBean();
			BeanUtils.copyProperties(voucherRequestBean, vmaster);
			workflowMap.setVoucherRequestDetails(voucherRequestBean);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMap;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getInvoiceRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		List<Object> itemsList = null;
		List<IRItemDetailsDTO> irItems = null;
		HashMap voucherDetails = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (CPSUtils.compareStrings(CPSConstants.INVOICE, workflowMap.getRequestType())) {
				voucherDetails = (HashMap<String, Object>) session
						.createSQLQuery(
								"select invoice.demand_no as demandNo,inv.INVENTORY_NO as invId,account.ACCOUNT_HEAD_NUMBER as accHeadNo,invoice.INVENTORY_NO as inventoryNo,invoice.VOUCHER_DATE as voucherDate,demand.ACCOUNT_HEAD_ID as accountHeadId,invoice.voucher_no as voucherNo,invoice.reason as reason "
										+ " ,demand.IP_ADDRESS as ipAddress,invoice.TOTAL_AMOUNT as totalAmount from MMG_B_IR_MASTER invoice,MMG_B_INVENTORY_HOLDER inv,MMG_B_ACCOUNT_HEAD_MASTER account,MMG_B_DEMAND_MASTER demand where invoice.request_id=? and invoice.INVENTORY_NO=inv.id and demand.ACCOUNT_HEAD_ID=account.id and demand.demand_no=invoice.demand_no")
						.addScalar("demandNo", Hibernate.STRING).addScalar("invId", Hibernate.STRING).addScalar("accHeadNo", Hibernate.STRING).addScalar("inventoryNo", Hibernate.STRING).addScalar(
								"accountHeadId", Hibernate.STRING).addScalar("voucherDate", Hibernate.DATE).addScalar("ipAddress").addScalar("voucherNo").addScalar("reason").addScalar("totalAmount",
								Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
			} else {
				voucherDetails = (HashMap<String, Object>) session
						.createSQLQuery(
								"select invoice.demand_no as demandNo,inv.INVENTORY_NO as invId,account.ACCOUNT_HEAD_NUMBER as accHeadNo,invoice.INVENTORY_NO as inventoryNo,invoice.VOUCHER_DATE as voucherDate,demand.ACCOUNT_HEAD_ID as accountHeadId,invoice.voucher_no as voucherNo,invoice.reason as reason "
										+ " ,demand.IP_ADDRESS as ipAddress,invoice.TOTAL_AMOUNT as totalAmount from MMG_B_IR_MASTER invoice,MMG_B_INVENTORY_HOLDER inv,MMG_B_ACCOUNT_HEAD_MASTER account,MMG_B_DEMAND_MASTER demand where invoice.cancel_request_id=? and invoice.INVENTORY_NO=inv.id "
										+ " and invoice.demand_no=demand.demand_no and demand.ACCOUNT_HEAD_ID=account.id and demand.demand_no=invoice.demand_no").addScalar("demandNo",
								Hibernate.STRING).addScalar("invId", Hibernate.STRING).addScalar("accHeadNo", Hibernate.STRING).addScalar("inventoryNo", Hibernate.STRING).addScalar("accountHeadId",
								Hibernate.STRING).addScalar("voucherDate", Hibernate.DATE).addScalar("ipAddress").addScalar("voucherNo").addScalar("reason").addScalar("totalAmount", Hibernate.STRING)
						.setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
			}
			String sql = "select mmd.id as materialCode,mmd.MATERIAL_NAME as description,mmd.CONSUMABLE_FLAG as cflag,ditem.RATE as unitRate,ditem.QTY as qty,((ditem.qty*ditem.RATE)+ditem.TAX_AMOUNT) as amount,ditem.TAX_AMOUNT as taxAmount,"
					+ "ditem.memo_no as memoNo,ditem.uom as uomConvert,uom.name as uom from MMG_B_MATERIAL_MASTER mmd,MMG_B_IR_ITEM_DETAILS ditem,MMG_B_UOM_MASTER uom where ditem.MATERIAL_CODE=mmd.id and ditem.voucher_no=? "
					+ " and mmd.status=1 and uom.status=1 and ditem.uom=uom.id";
			itemsList = session.createSQLQuery(sql).addScalar("materialCode").addScalar("description").addScalar("cflag").addScalar("amount").addScalar("uomConvert").addScalar("uom").addScalar(
					"taxAmount").addScalar("memoNo").addScalar("unitRate").addScalar("qty").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setString(0,
					voucherDetails.get("voucherNo").toString()).list();
			irItems = new ArrayList<IRItemDetailsDTO>();
			for (int i = 0; i < itemsList.size(); i++) {
				HashMap map = (HashMap) itemsList.get(i);
				IRItemDetailsDTO items = new IRItemDetailsDTO();
				BeanUtils.copyProperties(items, map);
				irItems.add(items);
			}
			InvoiceRequestBean invoiceRequest = new InvoiceRequestBean();
			BeanUtils.copyProperties(invoiceRequest, voucherDetails);
			invoiceRequest.setIrItems(irItems);
			String dept = getEmployeeDept(workflowMap.getSfid());
			if (!CPSUtils.isNull(dept))
				invoiceRequest.setApprovedDept(dept);
			else
				invoiceRequest.setApprovedDept("");
			workflowMap.setApprovedDept(dept);
			workflowMap.setInvoiceRequestDetails(invoiceRequest);
			if (!CPSUtils.isNullOrEmpty(voucherDetails.get("reason")))
				workflowMap.setReason(voucherDetails.get("reason").toString());
			workflowMap.setIpAddress(voucherDetails.get("ipAddress").toString());
			workflowMap = getPaymentDetails(workflowMap);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMap;
	}

	public String getEmployeeDept(String sfid) throws Exception {
		String dept = null;
		Session session = null;
		String[] department = { "MMG", "CFA", "BUDGET", "FINANCE" };
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String officeId = (String) session.createSQLQuery("select office_id as officeId from EMP_MASTER where sfid=? and status=1").addScalar("officeId", Hibernate.STRING).setString(0, sfid)
					.uniqueResult();
			for (int i = 0; i < department.length; i++) {
				String configuresfid = (String) session.createSQLQuery("select value from configuration_details where name=?").setString(0, department[i].toString()).uniqueResult();
				if (CPSUtils.compareStrings(officeId, configuresfid)) {
					dept = department[i].toString();
					break;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return dept;
	}

	@Override
	public WorkFlowMappingBean getWorkflowStage(WorkFlowMappingBean workflowmap) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select wsm.id,wsm.name from request_workflow_history rwh,workflow w,workflow_stage_master wsm where w.workflow_id=rwh.workflow_id and rwh.request_id=? "
					+ "and rwh.assigned_to=? and wsm.status=1 and wsm.id=w.stage_desc_id and w.stage_id=rwh.request_stage";

			ps = con.prepareStatement(sql);
			ps.setString(1, workflowmap.getRequestId());
			ps.setString(2, workflowmap.getSfid());
			rsq = ps.executeQuery();
			if (rsq.next()) {
				workflowmap.setWorkflowStage(rsq.getString("name"));
				workflowmap.setWorkflowStageID(rsq.getString("id"));
			} else {
				workflowmap.setWorkflowStage("Approve");
				workflowmap.setWorkflowStageID(CPSConstants.STATUSAPPROVED);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowmap;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getPaymentDetails(WorkFlowMappingBean workflowmap) throws Exception {
		Session session = null;
		List<FundsAllotedDetailsDTO> fundDetails = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String demandNo = null;
			if (CPSUtils.compareStrings(CPSConstants.DEMAND, workflowmap.getRequestType()))
				demandNo = workflowmap.getDemandRequestDetails().getDemandNo();
			else
				demandNo = workflowmap.getInvoiceRequestDetails().getDemandNo();
			fundDetails = session.createSQLQuery("select FUND_AMOUNT as fundAmount,CREATION_DATE as creationDate,PAYMENT_TYPE_ID as paymentTypeId from MMG_B_FUNDS_ALLOTTED_DETAILS where DEMAND_NO=?")
					.addScalar("fundAmount").addScalar("creationDate", Hibernate.DATE).addScalar("paymentTypeId", Hibernate.STRING).setResultTransformer(
							Transformers.aliasToBean(FundsAllotedDetailsDTO.class)).setString(0, demandNo).list();
			if (CPSUtils.checkList(fundDetails)) {
				for (int i = 0; i < fundDetails.size(); i++) {
					FundsAllotedDetailsDTO fund = (FundsAllotedDetailsDTO) fundDetails.get(i);
					if (CPSUtils.compareStrings(CPSConstants.INVOICE, workflowmap.getRequestType())) {
						if (!CPSUtils.isNullOrEmpty(fund) && !CPSUtils.isNullOrEmpty(fund.getFundAmount())) {
							Float settleAmount = Float.parseFloat(workflowmap.getInvoiceRequestDetails().getTotalAmount()) - Float.parseFloat(fund.getFundAmount());
							workflowmap.setSettleAmount(String.valueOf(settleAmount));
							workflowmap.setPaymentDate(fund.getCreationDate());
							workflowmap.setSettlementDate(CPSUtils.getCurrentDateWithTime());
							workflowmap.setPaymentTypeId("2");
						} else {
							workflowmap.setPaymentDate(CPSUtils.getCurrentDateWithTime());
						}
					}
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.DEMAND, workflowmap.getRequestType()))
				workflowmap.getDemandRequestDetails().setFundDetails(fundDetails);
			else
				workflowmap.getInvoiceRequestDetails().setFundDetails(fundDetails);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowmap;
	}

	@Override
	public String getCheckStage(String requestID, String requestType) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			// check whether the request is in last stage or not
			message = session.createSQLQuery(
					"select case when (select max(stage_id) from workflow where workflow_id=(select distinct workflow_id from request_workflow_history where request_id=?))= "
							+ "(select max(request_stage) from request_workflow_history where request_id=?) then 'last' else 'previous' end from dual").setString(0, requestID).setString(1, requestID)
					.uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@Override
	public String getLastStagePendingCheck(String requestID, String sfID) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			// If the request is in last stage pending then return lpending, if the request is in middle stage it returns pending else completed
			message = session
					.createSQLQuery(
							"select case when (select max(request_stage) from request_workflow_history where request_id=? and assigned_to=?)=(select max(stage_id) from workflow where "
									+ "workflow_id=(select distinct workflow_id from request_workflow_history where request_id=?)) then case when (select unique status from request_workflow_history where request_id=? "
									+ "and assigned_to=? and id=(select max(id) from request_workflow_history where request_id=? and assigned_to=?) and request_stage=(select max(request_stage) from request_workflow_history rwh where rwh.request_id=? and rwh.assigned_to=?))=? then 'lpending' else 'completed' end else 'pending' end from dual")
					.setString(0, requestID).setString(1, sfID).setString(2, requestID).setString(3, requestID).setString(4, sfID).setString(5, requestID).setString(6, sfID).setString(7, requestID).setString(8, sfID).setString(9,
							CPSConstants.STATUSPENDING).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			session.clear();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getCghsReimbursementDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		StringBuffer sb = null;
		HashMap<String, String> hmap = null;
		KeyValueDTO KeyDTO=null;
		try {
			sb = new StringBuffer();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			workflowMap.setCghsReimbursement((CghsReimbursementRequestDTO) session.createCriteria(CghsReimbursementRequestDTO.class).add(Expression.eq("requestID", workflowMap.getRequestId()))
					.uniqueResult());
			workflowMap.setIpAddress(workflowMap.getCghsReimbursement().getIpAddress());
			if(CPSUtils.compareStrings(workflowMap.getRequestType(), CPSConstants.CGHS_REIMBURSEMENT)){
				 String qry=" select cda.cda_amount as key,finance.amount as id from cghs_reimbursement_details reim , emp_claim_details claim,finance_details finance,cda_details cda      where  reim.request_id=claim.request_id and claim.id=finance.reference_id and finance.id=cda.reference_id and claim.status=1 and finance.status=1   and  reim.request_id=?";
					
				 Object obj=session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("key", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
					
					if(!CPSUtils.isNullOrEmpty(obj)){
					 KeyDTO =(KeyValueDTO) obj;
					 if(!CPSUtils.isNullOrEmpty(KeyDTO.getId())){
						 workflowMap.getCghsReimbursement().setAdmissibleTotalAmount(KeyDTO.getId());
					 }
					 if(!CPSUtils.isNullOrEmpty(KeyDTO.getKey())){
							workflowMap.getCghsReimbursement().setCdaAmount(KeyDTO.getKey());
						}
				}
			}
			if (CPSUtils.compareStrings(workflowMap.getRequestType(), CPSConstants.CGHS_SETTLEMENT)) {
				/**
				 * In Settlement case get the CDA issued advance and deduct the advance value from total claim amount
				 */
				/*int advanceAmount =Integer
								.parseInt((String) session
										.createSQLQuery(
												"select cad.cda_amount||'' from cghs_reimbursement_details reim,cghs_advance_details cad where reim.request_id=? and cad.reference_request_id=reim.reference_request_id")
										.setString(0, workflowMap.getRequestId()).uniqueResult());*/
				/*int advanceAmount =Integer.parseInt((String) session.createSQLQuery("select cda.cda_amount  ||''from cghs_reimbursement_details reim,  cghs_advance_details cad,  emp_claim_details claim,finance_details finance,cda_details cda 		WHERE reim.request_id   =? and cad.reference_request_id=reim.reference_request_id and  reim.request_id=claim.request_id and claim.id=finance.reference_id and finance.id=cda.reference_id")
				.setString(0, workflowMap.getRequestId()).uniqueResult());*/
				int advanceAmount =Integer.parseInt((String) session.createSQLQuery("select cda.cda_amount ||''  from cghs_advance_details cad left outer join cghs_reimbursement_details reim on(cad.reference_request_id=reim.reference_request_id) left outer join emp_claim_details claim on(cad.request_id    =claim.request_id) left outer join finance_details finance on(claim.id=finance.reference_id) left outer join  cda_details cda on(cda.reference_id=finance.id) 	where reim.request_id  =?")
						.setString(0, workflowMap.getRequestId()).uniqueResult());
				
				workflowMap.getCghsReimbursement().setAmountClaimed(String.valueOf((Integer.parseInt(workflowMap.getCghsReimbursement().getAmountClaimed())-advanceAmount)));
				workflowMap.getCghsReimbursement().setAdvanceDetails(String.valueOf(advanceAmount));
              String qry=" select cda.cda_amount as key,finance.amount as id from cghs_reimbursement_details reim left outer join  emp_claim_details claim on( reim.request_id=claim.request_id) left outer join   finance_details finance on(claim.id=finance.reference_id)left  outer join   cda_details cda on (finance.id       =cda.reference_id) where reim.request_id  =?  and claim.status     =1   AND finance.status   =1";
				
				Object obj=session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("key", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
				
				if(!CPSUtils.isNullOrEmpty(obj)){
				 KeyDTO =(KeyValueDTO) obj;
				 if(!CPSUtils.isNullOrEmpty(KeyDTO.getId())){
					 workflowMap.getCghsReimbursement().setAdmissibleTotalAmount(KeyDTO.getId());
				 }
				 if(!CPSUtils.isNullOrEmpty(KeyDTO.getKey())){
						workflowMap.getCghsReimbursement().setCdaAmount(KeyDTO.getKey());
					}
			}
				/*session.flush();
				
				if(!CPSUtils.isNullOrEmpty(KeyDTO.getId())){
					workflowMap.getCghsReimbursement().setAdmissibleTotalAmount(KeyDTO.getId());
					if(!CPSUtils.isNullOrEmpty(KeyDTO.getKey())){
						workflowMap.getCghsReimbursement().setCdaAmount(KeyDTO.getKey());
					}
				}*/
				/*Object obj=session.createSQLQuery(qry).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(obj)){
					String cdaAmount=obj.toString();
					workflowMap.getCghsReimbursement().setCdaAmount(cdaAmount);
				}*/
            
              
              
			}
			if (CPSUtils.compareStrings(workflowMap.getRequestType(), CPSConstants.NON_CGHS_REIMBURSEMENT)) {
				workflowMap.getCghsReimbursement().setCghsRequestDetails(new CGHSRequestProcessBean());
				
				
				workflowMap.getCghsReimbursement().getCghsRequestDetails().setEmpMasterDetails(new EmployeeBean());
				sb
						.append("select emp.sfid sfid,emp.name_in_service_book name,emp.cgsh_number cghsCard,(select adm.address1||','||adm.address2||','||adm.address3||','||dm.name||','||sm.name||','||adm.pincode||','||adm.mobile_number from "
								+ "address_details_master adm,state_master sm,district_master dm where sfid=emp.sfid and adm.address_type_id=2 and dm.status=1 and sm.status=1 and adm.state=sm.id and adm.district=dm.id) address "
								+ "from emp_master emp where emp.sfid=?");
				hmap = (HashMap<String, String>) session.createSQLQuery(sb.toString()).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("cghsCard", Hibernate.STRING)
						.addScalar("address", Hibernate.STRING).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setString(0, workflowMap.getSfid()).uniqueResult();
			
				if(!CPSUtils.isNullOrEmpty(hmap)){
					String qry=" select cda.cda_amount as value , finance.amount as id from cghs_reimbursement_details reim , emp_claim_details claim,finance_details finance,cda_details cda      where  reim.request_id=claim.request_id and claim.id=finance.reference_id and finance.id=cda.reference_id and claim.status=1 and finance.status=1   and  reim.request_id=?";
					Object obj=session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("value", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
					
					
					if(!CPSUtils.isNullOrEmpty(obj)){
						 KeyDTO =(KeyValueDTO) obj;
						 if(!CPSUtils.isNullOrEmpty(KeyDTO.getId())){
							 workflowMap.getCghsReimbursement().setAdmissibleTotalAmount(KeyDTO.getId());
						 }
						 if(!CPSUtils.isNullOrEmpty(KeyDTO.getValue())){
								workflowMap.getCghsReimbursement().setCdaAmount(KeyDTO.getValue());
							}
					}
				}
				workflowMap.getCghsReimbursement().getCghsRequestDetails().getEmpMasterDetails().setUserSfid(hmap.get("sfid"));
				workflowMap.getCghsReimbursement().getCghsRequestDetails().getEmpMasterDetails().setNameInServiceBook(hmap.get("name"));
				workflowMap.getCghsReimbursement().getCghsRequestDetails().getEmpMasterDetails().setCgshNumber(hmap.get("cghsCard"));
				workflowMap.getCghsReimbursement().getCghsRequestDetails().setAddress(hmap.get("address"));
			}
			workflowMap.setTdSettStrStatus(workflowMap.getWorkflowHistory().get(workflowMap.getWorkflowHistory().size()-1).getStatus());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMap;
	}

	public WorkFlowMappingBean getCghsRequestAdvanceDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			workflowMap.setCghsAdvanceDTO((CghsAdvanceRequestDTO) session.createCriteria(CghsAdvanceRequestDTO.class).add(Expression.eq("requestID", workflowMap.getRequestId())).uniqueResult());
               if (!CPSUtils.isNullOrEmpty(workflowMap.getCghsAdvanceDTO())) {
				
				String qry="select cda.cda_amount from cghs_advance_details adv,emp_claim_details claim,finance_details finance," +
						"cda_details cda  where adv.request_id=claim.request_id and claim.id=finance.reference_id and " +
						" finance.id=cda.reference_id and claim.status=1 and finance.status=1 and adv.request_id="+workflowMap.getCghsAdvanceDTO().getRequestID();
				Object obj=session.createSQLQuery(qry).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(obj)){
					String cdaAmount=obj.toString();
					workflowMap.getCghsAdvanceDTO().setCdaAmount(cdaAmount);
				}
			
			} 
			//workflowMap.setIpAddress(workflowMap.getCghsAdvanceDTO().getIpAddress());
				
			//rakesh
			String sql = "SELECT ROUND((CAD.ESTIMATION_AMOUNT*(SELECT value FROM configuration_details WHERE name=? ))/100) ||''AS VALUE,CRMD.REQUEST_ID AS KEY,RWH.ID as flag FROM cghs_advance_details CAD LEFT OUTER join CGHS_REIMBURSEMENT_DETAILS crmd LEFT outer join REQUEST_WORKFLOW_HISTORY rwh on(CRMD.REQUEST_ID=RWH.REQUEST_ID) on (CAD.REFERENCE_REQUEST_ID = CRMD.REFERENCE_REQUEST_ID) WHERE CAD.request_id=?";
			KeyValueDTO KeyDTO =(KeyValueDTO) session.createSQLQuery(sql).addScalar("value", Hibernate.STRING).addScalar("key", Hibernate.STRING).addScalar("flag",Hibernate.STRING).setString(0, CPSConstants.CGHS_ADVANCE_APPROVED_PERCENTAGE).setString(1, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
			
		/*	workflowMap.getCghsAdvanceDTO().setConfigurationAmount(KeyDTO.getValue());
			workflowMap.getCghsAdvanceDTO().setReimbursementId(KeyDTO.getKey());	
			workflowMap.getCghsAdvanceDTO().setHistoryId(KeyDTO.getFlag());
		
		    String sql2 = "select value from Configuration_Details where Name=?";
		    workflowMap.getCghsAdvanceDTO().setPercentage((String)session.createSQLQuery(sql2).setString(0,CPSConstants.CGHS_ADVANCE_APPROVED_PERCENTAGE).uniqueResult() );*/
			
			
		    workflowMap.setTdSettStrStatus(workflowMap.getWorkflowHistory().get(workflowMap.getWorkflowHistory().size()-1).getStatus());
		    session.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> cghsDelegateList() throws Exception {
		List<KeyValueDTO> cghsDelegateList = null;
		Session session = null;
		StringBuilder sb = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sb = new StringBuilder();
			sb.append("select name_in_service_book||'-'||sfid name,sfid key from emp_master where status=1 and sfid in " + "(select sfid from emp_role_mapping where status=1 and org_role_id in "
					+ "(select org_role_id from org_role_instance where department_id=92 and status=1))");
			cghsDelegateList = session.createSQLQuery(sb.toString()).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(KeyValueDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return cghsDelegateList;

	}
	
	//@SuppressWarnings("unchecked")
	public WorkFlowMappingBean  cghsreque(WorkFlowMappingBean workflowMap) throws Exception {
		
		Session session = null;
		KeyValueDTO keydto=null;
		
		
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			
			/*String sql="SELECT CAD.REQUEST_ID AS key,(select id from request_workFlow_history where request_id=CAD.REQUEST_ID)as id "
					+ " FROM CGHS_ADVANCE_DETAILS CAD,REQUEST_WORKFLOW_HISTORY RWH   WHERE RWH.REQUEST_ID=CAD.REFERENCE_REQUEST_ID and RWH.REQUEST_ID=?";
			KeyValueDTO keydto =(KeyValueDTO)session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
			session.flush();
			if(!CPSUtils.isNullOrEmpty(keydto)){
				workflowMap.setCghsreq(keydto.getKey());
				workflowMap.setCghsrequadv(keydto.getId());
			
			}*/
			//rakesh
			
			
			/*if(workflowMap.getCghsRequestDetails().getCghsRequestTypeId()=="3")
			{
				String sql2="SELECT CRMD.REQUEST_ID, rwh.id  FROM    REQUEST_WORKFLOW_HISTORY  RWH ,CGHS_REIMBURSEMENT_DETAILS CRMD where RWH.REQUEST_ID =CRMD.reference_request_id and RWH.request_id=? ";
				
			keydto=(KeyValueDTO)session.createSQLQuery(sql2).addScalar("key", Hibernate.STRING).addScalar("flag", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
			workflowMap.setCghsreimbursementId(keydto.getKey());
			workflowMap.setCghsreimbursementHistoryId(keydto.getFlag());
			}
			else{*/
			 String sql="SELECT nvl(CAD.REQUEST_ID,0) AS KEY,nvl((SELECT ID FROM REQUEST_WORKFLOW_HISTORY WHERE REQUEST_ID=CAD.REQUEST_ID),0)AS ID,(SELECT ID FROM REQUEST_WORKFLOW_HISTORY WHERE REQUEST_ID= CRMD.REQUEST_ID) flag,CRMD.REQUEST_ID AS value FROM REQUEST_WORKFLOW_HISTORY RWH LEFT OUTER JOIN CGHS_REIMBURSEMENT_DETAILS CRMD ON(RWH.REQUEST_ID = CRMD.REFERENCE_REQUEST_ID) LEFT OUTER JOIN CGHS_ADVANCE_DETAILS CAD ON(RWH.REQUEST_ID    =CAD.REFERENCE_REQUEST_ID) WHERE RWH.REQUEST_ID =?";
			 keydto =(KeyValueDTO)session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("flag", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
			//}
			session.flush();
			      
			      if(!CPSUtils.isNullOrEmpty(keydto))
			      {
			    	  workflowMap.setCghsadvanceRequestId(keydto.getKey());
			    	  workflowMap.setCghsadvHistoryID(keydto.getId());
			    	if( Integer.valueOf(keydto.getKey()) !=0   &&  keydto.getId() != 0 ){
				      workflowMap.setCghssetlementRequestId(keydto.getValue());	
				      workflowMap.setCghssetlementHistoryID(keydto.getFlag()); 
			    	}else{
			    		workflowMap.setCghsreimbursementId(keydto.getValue());
						workflowMap.setCghsreimbursementHistoryId(keydto.getFlag());
			    	}
				      
				   }
			 
			 
	    	} catch (Exception e) {
			       throw e;
	      	} finally {
			//session.close();
		   }
		  return workflowMap;

	}
	
	

	public WorkFlowMappingBean getCghsRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			workflowMap.setCghsRequestDetails((CGHSRequestProcessBean) session.createCriteria(CGHSRequestProcessBean.class).add(Expression.eq("requestID", workflowMap.getRequestId())).uniqueResult());
			 
			if(!CPSUtils.isNullOrEmpty(workflowMap.getCghsRequestDetails())){
			
			workflowMap.setIpAddress(workflowMap.getCghsRequestDetails().getIpAddress());
			workflowMap.setPrescriptionReceivedFlag(workflowMap.getCghsRequestDetails().getPrescriptionReceivedFlag());
			workflowMap.setTdSettStrStatus(workflowMap.getWorkflowHistory().get(workflowMap.getWorkflowHistory().size()-1).getStatus());}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMap;
	}

	public WorkFlowMappingBean getCghsEmergencyDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		KeyValueDTO keydto =null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			workflowMap.setCghsEmergency((CghsEmergencyRequestDTO) session.createCriteria(CghsEmergencyRequestDTO.class).add(Expression.eq("requestID", workflowMap.getRequestId())).uniqueResult());
			if (!CPSUtils.isNullOrEmpty(workflowMap.getCghsEmergency())) {
				String qry=" select nvl(cda.cda_amount,0) as id , finance.amount as value from cghs_emergency_details ced left outer join emp_claim_details ecd  on (ecd.request_id=ced.request_id) left outer join  finance_details finance on (ecd.id=finance.reference_id) left outer join cda_details cda on (finance.id=cda.reference_id) where  ecd.request_id=?";
				/*Object obj=session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("value", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(obj)){
					keydto = (KeyValueDTO) obj;
					if(!CPSUtils.isNullOrEmpty(keydto.getId())){
						workflowMap.getCghsEmergency().setAdmissibleTotalAmount(keydto.getId());
					}
					if(!CPSUtils.isNullOrEmpty(keydto.getValue())){
						workflowMap.getCghsEmergency().setCdaAmount(keydto.getValue());

					}*/
				keydto=(KeyValueDTO)session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).addScalar("value", Hibernate.STRING).setString(0, workflowMap.getRequestId()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
				session.flush();
					if(!CPSUtils.isNullOrEmpty(keydto)){
						if(keydto.getId()!=0){
							workflowMap.getCghsEmergency().setAdmissibleTotalAmount(Integer.parseInt(keydto.getValue()));
						}
						if(!CPSUtils.isNullOrEmpty(keydto.getValue())){
							workflowMap.getCghsEmergency().setCdaAmount(String.valueOf(keydto.getId()));

						}
					}
					
				}
				
			//workflowMap.setIpAddress(workflowMap.getCghsEmergency().getIpAddress());
			//workflowMap.setTdSettStrStatus(workflowMap.getWorkflowHistory().get(workflowMap.getWorkflowHistory().size()-1).getStatus());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return workflowMap;
	}

	@Override
	public String setWaitingStatus(RequestBean rb) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			// update the status in this stage
			session.createSQLQuery(
					"update request_workflow_history set stage_status=?,actioned_date=sysdate,remarks=?,ip_address=? where id in (select id from request_workflow_history where request_id=(select request_id from "
							+ "request_workflow_history where id=?) and request_stage=(select max(request_stage) from request_workflow_history where "
							+ "request_id=(select request_id from request_workflow_history where id=?)))").setString(0, CPSConstants.STATUSWAIT).setString(1, rb.getRemarks()).setString(2,
					rb.getIpAddress()).setString(3, rb.getHistoryID()).setString(4, rb.getHistoryID()).executeUpdate();
			rb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return rb.getMessage();
	}
	@Override
	public String setEmuStatus(RequestBean rb) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			// update the status in this stage
			session.createSQLQuery(
					"update request_workflow_history set stage_status=?,actioned_date=sysdate,remarks=?,ip_address=? where id in (select id from request_workflow_history where request_id=(select request_id from "
							+ "request_workflow_history where id=?) and request_stage=(select max(request_stage) from request_workflow_history where "
							+ "request_id=(select request_id from request_workflow_history where id=?)))").setString(0, CPSConstants.EMUREQUESTID).setString(1, rb.getRemarks()).setString(2,
					rb.getIpAddress()).setString(3, rb.getHistoryID()).setString(4, rb.getHistoryID()).executeUpdate();
			rb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return rb.getMessage();
	}
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getLTCRefundDetails(WorkFlowMappingBean workflowBean) throws Exception {
		Session session = null;
		List<LtcMemberDetailsDTO> list = null;
		String sql = null;
		String sqlfamily = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (CPSUtils.compareStrings(workflowBean.getRequestType(), CPSConstants.LTCREFUNDVALUE) || CPSUtils.compareStrings(workflowBean.getRequestType(), CPSConstants.LTC_APPR_CUM_ADV_CANCEL) ) {
				sql = "select 'advElEncash' type,refund.request_id id,dmap.type gazType,refund.IPADDRESS ipAddress,lard.request_id requestIDs, to_char(lard.applied_date,'dd-Mon-yyyy') creationDate,lard.encashment_days encashmentDays,(select leavetype.leave_type||'('|| lrd.from_date||' To '||lrd.to_date||')' from leave_request_details lrd,leave_type_master leaveType where lrd.request_id=lard.leave_request_id and lrd.leave_type_id=leaveType.id) leaveDetails,lard.place_of_visit placeOfVisit, ltm.ltc_type ltcTypeId, case when (to_char(lbym.from_date,'yyyy')=to_char(lbym.to_date,'yyyy')) or to_date is null "
						+ "then (to_char(lbym.from_date,'yyyy')) else (to_char(lbym.from_date,'yyyy')||'-'||to_char(lbym.to_date,'yy')) end ltcBlockYear,(case when cda_amount is null then issued_amount else cda_amount end) ltcAdvance,"
						+ "case when(REFUND.DO_PART_ID is null) then '' else (select ref_number from reference_number_details rnd where rnd.id=refund.do_part_id) end doPartNo,case when(REFUND.DO_PART_ID is null) then '' else (select to_char(ref_date) from reference_number_details rnd where rnd.id=refund.do_part_id) end doPartDate from ltc_advance_request_details lard,designation_mappings dmap,ltc_refund_request refund,ltc_type_master ltm,ltc_block_year_master lbym where refund.request_id=? and lbym.id=lard.ltc_block_year_id and lard.ltc_type_id=ltm.id and refund.reference_id=lard.request_id and dmap.desig_id=lard.designation_id";
			} else {
				sql = "select 'appElEncash' type,refund.request_id id,dmap.type gazType,refund.IPADDRESS ipAddress, '' ltcAdvance,lard.request_id requestIDs, to_char(lard.creation_date,'dd-Mon-yyyy') creationDate,lard.encashment_days encashmentDays,(select leavetype.leave_type||'('|| lrd.from_date||' To '||lrd.to_date||')' from leave_request_details lrd,leave_type_master leaveType where lrd.request_id=lard.leave_request_id and lrd.leave_type_id=leaveType.id) leaveDetails,lard.place_of_visit placeOfVisit, ltm.ltc_type ltcTypeId, case when (to_char(lbym.from_date,'yyyy')=to_char(lbym.to_date,'yyyy')) or to_date is null "
						+ "then (to_char(lbym.from_date,'yyyy')) else (to_char(lbym.from_date,'yyyy')||'-'||to_char(lbym.to_date,'yy')) end ltcBlockYear,"
						+ "case when(REFUND.DO_PART_ID is null) then '' else (select ref_number from reference_number_details rnd where rnd.id=refund.do_part_id) end doPartNo,case when(REFUND.DO_PART_ID is null) then '' else (select to_char(ref_date) from reference_number_details rnd where rnd.id=refund.do_part_id) end doPartDate from ltc_request_details lard,designation_mappings dmap,ltc_refund_request refund,ltc_type_master ltm,ltc_block_year_master lbym where refund.request_id=? and lbym.id=lard.ltc_block_year_id and lard.ltc_type_id=ltm.id and refund.reference_id=lard.request_id and dmap.desig_id=lard.designation_id";
			}

			workflowBean.setlTCRefundDetails((LtcApplicationBean)session.createSQLQuery(sql).addScalar("doPartNo").addScalar("doPartDate").addScalar("type",Hibernate.STRING).addScalar("gazType").addScalar("id",Hibernate.STRING).addScalar("requestIDs",Hibernate.STRING).addScalar("creationDate").addScalar("encashmentDays", Hibernate.STRING).addScalar("leaveDetails").addScalar("placeOfVisit").addScalar("ltcTypeId").addScalar("ltcBlockYear").addScalar("ltcAdvance",Hibernate.STRING).addScalar("ipAddress").setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).setString(0, workflowBean.getRequestId()).uniqueResult());

			workflowBean.setIpAddress(workflowBean.getlTCRefundDetails().getIpAddress());
			sqlfamily = "select fd.name as familyMemberName,to_char(fd.dob,'dd-Mon-yyyy') as dob,case when fd.age is null then round(months_between(sysdate,fd.dob)/12)||'' else fd.age end as age,fr.name as relation "+
			"from family_details fd,family_relation_master fr where fd.status=1 " +
			" and fd.id in(select family_member_id from ltc_txn_details ltd where request_id=?) and fr.id=fd.relation_id order by fr.order_no";

			list = session.createSQLQuery(sqlfamily).addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0,String.valueOf(((LtcRefundRequestDTO)session.get(LtcRefundRequestDTO.class, workflowBean.getRequestId())).getReferenceID())).list();
			workflowBean.getlTCRefundDetails().setLtcApproveDetailsList(list);
			Object empRoleId = 	session.createSQLQuery("select org_role_id from emp_role_mapping where org_role_id in( select ori.org_role_id from org_role_instance ori where ori.status = 1 start with ori.org_role_id=(select value from configuration_details where name=?) connect by ori.parent_org_role_id = prior ori.org_role_id) and status=1 and sfid=?").setString(0, CPSConstants.ADMIN).setString(1, workflowBean.getSfid()).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(empRoleId)) {
				workflowBean.getlTCRefundDetails().setEmpRoleId(empRoleId.toString());
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}

		return workflowBean;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public WorkFlowMappingBean getLtcRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
	
	List<LtcMemberDetailsDTO> list = null;
	Session session = null;
	String sql =null;

	try {
		session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		/*String role =(String)session.createSQLQuery("SELECT Ori.Org_Role_Name FROM Emp_Master Emp,  Org_Role_Instance Ori,Emp_Role_Mapping Erm WHERE Ori.Org_Role_Id=Emp.Office_Id AND Emp.Status  =1"
                                                      +"AND Ori.Status   =1 AND Erm.Status  =1 AND Erm.Org_Role_Id  =Emp.Office_Id AND emp.sfid =? AND erm.org_role_id IS NOT NULL AND erm.sfid   =emp.sfid").setString(0,workflowMap.getSfid()).uniqueResult();
		*/
		
		//String role =(String)session.createSQLQuery("Select  To_Char(rm.id) as id From Role_Master Rm, Application_Role_Mapping Apm Where Rm.Id= Apm.Role_Id And Rm.Status=1 And Apm.Sfid=? And Apm.Status=1 And Rm.Id In (1,2,14)").setString(0, workflowMap.getSfid()).uniqueResult();

       String role =(String)session.createSQLQuery("select distinct to_char(case when rm.id in(1,2,14) then 8 else null  end)id from role_master rm,   application_role_mapping apm WHERE rm.id   = apm.role_id and rm.status =1 AND apm.sfid  =? and apm.status=1 AND rm.id    IN (1,2,14)").setString(0, workflowMap.getSfid()).uniqueResult();
		
		
		String type = (String)session.createSQLQuery("select unique request_type from ltc_txn_details where request_id=?").setString(0, workflowMap.getRequestId()).uniqueResult();
		workflowMap.setOrgroleIdLink(role);
		if(CPSUtils.compareStrings(type, CPSConstants.LTCADVANCE)) {
			/*sql="select ltcrd.leave_request_id leaveID,ltcrd.cda_amount cdaAmount,(select case when lrd1.leave_request_id is null then 'success' else (select case when leave.status in (29,8) then 'success' else 'pending' end  from leave_request_details leave where leave.request_id=lrd1.leave_request_id) end result from ltc_advance_request_details lrd1 where lrd1.request_id=ltcrd.request_id) leaveStatus," +
					"ltcrd.NO_OF_INFANT_TICKETS noOfInfantTickets,ltcrd.NO_OF_TICKETS noOfTickets,ltcrd.AMOUNT_PER_PERSON amountPerPerson,ltcrd.AMOUNT_PER_EACH_INFANT amountPerEachInfant,ltcrd.escort_details escortDetails,ltcrd.no_of_infant_tickets noOfInfantTickets,ltcrd.amount_per_each_infant amountPerEachInfant,ltcrd.request_type ltcRequestType,dm.type gazType,(select case when (ltcrd.do_part_id is not null and ltcrd.do_part_id !=0) then ref_number||' & '||to_char(ref_date,'dd-Mon-yyyy') else '' end from reference_number_details where id=ltcrd.do_part_id ) doPartNo,(select id from reference_number_details where id=ltcrd.do_part_id) doNo,(select to_char(ref_date,'dd-Mon-yyyy') from reference_number_details where id=ltcrd.do_part_id and status=1) doPartDate, ltm.ltc_type ltcTypeId,(select case when (select count(0) from ltc_block_year_master lbym where id=ltcrd.ltc_block_year_id and ltc_block_id=?) >0  then to_char(lbym.from_date,'YYYY') else to_char(lbym.from_date,'YYYY')||'-'||to_char(lbym.to_date,'YY') end from dual) ltcBlockYearId,ltcrd.place_of_visit placeOfVisit,ltcrd.issued_amount issuedAmount,to_char(ltcrd.encashment_finance_amt) financeEncashmentAmt,to_char(ltcrd.encashment_cda_amount) cdaEncashmentAmt,  "
						+"ltcrd.ip_address ipAddress,ltcrd.nearest_railway_station nearestRlyStation,ltcrd.departure_date departureDate,ltcrd.return_date returnDate,ltcrd.amount_claimed amountClaimed,ltcrd.amount_per_person amountPerPerson,ltcrd.no_of_tickets noOfTickets, "
						+"ltcrd.nearest_railway_station nearestRlyStation,ltcrd.status status,(select leave_type from leave_type_master where id=ltcrd.encash_leave_type_id and status=1) encashTypeId ,ltcrd.encashment_days encashmentDays,ltcrd.phone_number phoneNum,(select ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as " 
						+"name from leave_request_details lrd,leave_type_master ltm where ltm.id=lrd.leave_type_id and lrd.request_id=ltcrd.leave_request_id)leaveRequestId from ltc_advance_request_details ltcrd,ltc_type_master ltm,ltc_block_year_master lbym,designation_mappings dm where request_id=? and ltm.status=1 and ltcrd.ltc_type_id=ltm.id and lbym.status=1 and lbym.id=ltcrd.ltc_block_year_id and dm.desig_id=designation_id";
		*/
			
			
			/*commented by bkr 18/04/2016 original query */
			   sql="SELECT LTCRD.LEAVE_REQUEST_ID LEAVEID,  NVL(LRD.REQUEST_ID,0) setlrequestid,  nvl(rwh.id,0) setlhistoryid,  LRD.request_type requestType,  LTCRD.CDA_AMOUNT CDAAMOUNT,  (SELECT   CASE      WHEN lrd1.leave_request_id IS NULL      THEN 'success'      ELSE        (SELECT          CASE            WHEN leave.status IN (29,8)            THEN 'success'            ELSE 'pending'          END        FROM leave_request_details leave        WHERE leave.request_id=lrd1.leave_request_id      ) "
                         +  "  END result  FROM ltc_advance_request_details lrd1  WHERE lrd1.request_id=ltcrd.request_id  ) leaveStatus,  ltcrd.NO_OF_INFANT_TICKETS noOfInfantTickets,  ltcrd.NO_OF_TICKETS noOfTickets,  ltcrd.AMOUNT_PER_PERSON amountPerPerson,  ltcrd.AMOUNT_PER_EACH_INFANT amountPerEachInfant,  ltcrd.escort_details escortDetails,  ltcrd.no_of_infant_tickets noOfInfantTickets,  ltcrd.amount_per_each_infant amountPerEachInfant,  ltcrd.request_type ltcRequestType,  dm.type gazType,  (SELECT    CASE      WHEN (ltcrd.do_part_id IS NOT NULL      AND ltcrd.do_part_id   !=0)      THEN ref_number        ||' & '        ||TO_CHAR(ref_date,'dd-Mon-yyyy')      ELSE ''    END  FROM reference_number_details  WHERE id=ltcrd.do_part_id  ) doPartNo,  (SELECT id FROM reference_number_details WHERE id=ltcrd.do_part_id  ) doNo,  (SELECT TO_CHAR(ref_date,'dd-Mon-yyyy')  FROM reference_number_details  WHERE id  =ltcrd.do_part_id  AND status=1  ) doPartDate,  ltm.ltc_type ltcTypeId,  (SELECT    CASE      WHEN (SELECT COUNT(0)        FROM ltc_block_year_master lbym        WHERE id        =ltcrd.ltc_block_year_id        AND ltc_block_id=?) >0      THEN TO_CHAR(lbym.from_date,'YYYY')  "
                   		 + "    ELSE TO_CHAR(lbym.from_date,'YYYY')        ||'-'        ||TO_CHAR(lbym.to_date,'YY')    END  FROM dual  ) ltcBlockYearId,  ltcrd.place_of_visit placeOfVisit,  ltcrd.issued_amount issuedAmount,  TO_CHAR(ltcrd.encashment_finance_amt) financeEncashmentAmt,  TO_CHAR(ltcrd.encashment_cda_amount) cdaEncashmentAmt,  ltcrd.ip_address ipAddress,  ltcrd.nearest_railway_station nearestRlyStation,  ltcrd.departure_date departureDate,  ltcrd.return_date returnDate,  ltcrd.amount_claimed amountClaimed,  ltcrd.amount_per_person amountPerPerson,  ltcrd.no_of_tickets noOfTickets,  ltcrd.nearest_railway_station nearestRlyStation,  ltcrd.status status,  ltcrd.applied_date as applieddate,  (select leave_type  from leave_type_master  WHERE id  =ltcrd.encash_leave_type_id   AND status=1  ) encashTypeId ,  ltcrd.encashment_days encashmentDays, "
                         +   "  ltcrd.phone_number phonenum,  (select ltm.leave_type    ||'('    ||to_char(lrd.from_date,'dd-Mon-yyyy') ||' '   ||'To'    ||' '    ||to_char(lrd.to_date,'dd-Mon-yyyy')    ||')' as name   from leave_request_details lrd,    leave_type_master ltm   where ltm.id      =lrd.leave_type_id  and lrd.request_id=ltcrd.leave_request_id  )leaverequestid from ltc_advance_request_details ltcrd left outer join ltc_reimbursement_details lrd on (ltcrd.request_id= lrd.reference_id) left outer join request_workflow_history rwh on (rwh.request_id  =lrd.request_id  and rwh.status not in(4)) ,  ltc_type_master ltm,  ltc_block_year_master lbym,  designation_mappings dm where ltcrd.request_id =? and ltm.status   =1 AND LTCRD.LTC_TYPE_ID  =LTM.ID AND LBYM.STATUS        =1 AND LBYM.ID     =LTCRD.LTC_BLOCK_YEAR_ID and dm.desig_id        =ltcrd.designation_id ";

						
			workflowMap.setLtcAdvanceRequestDTO((LtcAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("leaveID",Hibernate.STRING).addScalar("setlrequestid", Hibernate.STRING).addScalar("setlhistoryid", Hibernate.INTEGER).addScalar("leaveStatus",Hibernate.STRING).addScalar("noOfTickets",Hibernate.STRING).addScalar("noOfInfantTickets",Hibernate.STRING).addScalar("amountPerPerson",Hibernate.STRING).addScalar("amountPerEachInfant",Hibernate.STRING)			
					.addScalar("escortDetails").addScalar("cdaAmount",Hibernate.STRING).addScalar("appliedDate", Hibernate.STRING).addScalar("status",Hibernate.INTEGER).addScalar("ltcRequestType").addScalar("gazType").addScalar("ltcTypeId").addScalar("ltcBlockYearId").addScalar("placeOfVisit").addScalar("nearestRlyStation").addScalar("departureDate",Hibernate.DATE).addScalar("noOfInfantTickets", Hibernate.STRING).addScalar("amountPerEachInfant", Hibernate.STRING).addScalar("encashTypeId", Hibernate.STRING).addScalar("ipAddress").addScalar("issuedAmount", Hibernate.STRING).addScalar("financeEncashmentAmt").addScalar("cdaEncashmentAmt").addScalar("returnDate",Hibernate.DATE).addScalar("amountClaimed",Hibernate.STRING).addScalar("doNo",Hibernate.STRING).addScalar("doPartDate",Hibernate.STRING).addScalar("doPartNo").addScalar("amountPerPerson",Hibernate.STRING).addScalar("noOfTickets", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("leaveRequestId").addScalar("phoneNum",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcAdvanceRequestDTO.class)).setString(0,CPSConstants.ONEYEARBLOCKID).setString(1,workflowMap.getRequestId()).uniqueResult());
			
				
			// newly added by by bkr 19/04/2016
			  //String sql1="select do_part_id, mro_paid_date from LTC_ADVANCE_REQUEST_DETAILS_B where request_id=?";
			String sql1="select nearest_airport, mro_paid_date,do_part_id from LTC_ADVANCE_REQUEST_DETAILS_B where request_id=?";
			   
			  Object[] results = (Object[]) session.createSQLQuery(sql1).setString(0, workflowMap.getRequestId()).uniqueResult();
			  
			 LtcAdvanceRequestDTO l = workflowMap.getLtcAdvanceRequestDTO();
			 l.setNearesrAirport(((String) results[0]));
			 l.setMROPaidDate((Date) results[1]);
			// l.setDoPartId(results[2]);
			 l.setDoPartId(((BigDecimal) results[2]).intValue());

			
			  log.debug("doPartID:" + workflowMap.getLtcAdvanceRequestDTO().getDoPartId() + ", mro_paid_date:" + workflowMap.getLtcAdvanceRequestDTO().getMROPaidDate());
			  
			
			/*
			sql = "select fd.name as familyMemberName,to_char(fd.dob,'dd-Mon-yyyy') as dob, CASE WHEN fd.age IS NULL THEN case when floor(months_between(sysdate,fd.dob)/12)>0 then floor(months_between(sysdate,fd.dob)/12)||' Y' else floor(months_between(sysdate,fd.dob))||' M' end ELSE fd.age END     AS age,fr.name as relation "+
			          "from family_details fd,family_relation_master fr where fd.status=1 " +
			             " and fd.id in(select family_member_id from ltc_txn_details ltd where request_id=?) and fr.id=fd.relation_id order by fr.order_no";

			list = session.createSQLQuery(sql).addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0,workflowMap.getRequestId()).list();*/
	
			//age calculation has been changed to LTC applied date :rakesh
			
			if(!CPSUtils.isNullOrEmpty(workflowMap.getLtcAdvanceRequestDTO())){
			sql  ="SELECT fd.name as familyMemberName,  TO_CHAR(fd.dob,'dd-Mon-yyyy') AS dob,  CASE    WHEN fd.age IS NULL  then  case   when trunc(months_between(?,fd.dob)) between 0 and 155   then    trunc(months_between(?,fd.dob)/12)||' Years and '  || (trunc(months_between(?,fd.dob) - trunc(months_between(?,fd.dob)/12)*12)) ||' Months'"+
            "  else  trunc(months_between(?,fd.dob)/12)||' Years'  end else fd.age  end     as age, fr.name as relation from family_details fd,  family_relation_master fr where fd.status=1 and fd.id in (select family_member_id from ltc_txn_details ltd where request_id=?" +
                   "  )AND fr.id=fd.relation_id ORDER BY fr.order_no";
				
			list = session.createSQLQuery(sql).addScalar("familyMemberName",Hibernate.STRING).addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setDate(0,CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcAdvanceRequestDTO().getAppliedDate()))).setDate(1, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcAdvanceRequestDTO().getAppliedDate()))).setDate(2,CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcAdvanceRequestDTO().getAppliedDate()))).setDate(3, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcAdvanceRequestDTO().getAppliedDate()))).setDate(4, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcAdvanceRequestDTO().getAppliedDate()))).setString(5, workflowMap.getRequestId()).list();
			}
			//list = session.createSQLQuery(sql).addScalar("familyMemberName",Hibernate.STRING).addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setDate(0, workflowMap.getLtcAdvanceRequestDTO().getDepartureDate()).setDate(1, workflowMap.getLtcAdvanceRequestDTO().getDepartureDate()).setDate(2, workflowMap.getLtcAdvanceRequestDTO().getDepartureDate()).setDate(3,workflowMap.getLtcAdvanceRequestDTO().getDepartureDate()).setDate(4, workflowMap.getLtcAdvanceRequestDTO().getDepartureDate()).setString(5, workflowMap.getRequestId()).list();
			
			else{
				sql = "select fd.name as familyMemberName,to_char(fd.dob,'dd-Mon-yyyy') as dob, CASE WHEN fd.age IS NULL THEN case when floor(months_between(sysdate,fd.dob)/12)>0 then floor(months_between(sysdate,fd.dob)/12)||' Y' else floor(months_between(sysdate,fd.dob))||' M' end ELSE fd.age END     AS age,fr.name as relation "+
		          "from family_details fd,family_relation_master fr where fd.status=1 " +
		             " and fd.id in(select family_member_id from ltc_txn_details ltd where request_id=?) and fr.id=fd.relation_id order by fr.order_no";

		list = session.createSQLQuery(sql).addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0,workflowMap.getRequestId()).list();

			}
				
			
			
			workflowMap.getLtcAdvanceRequestDTO().setLtcMemberDetails(list);
			/**
			 * get ltc advance money issue percentage
			 */
			workflowMap.getLtcAdvanceRequestDTO().setAdvanceCfgValue((String)session.createSQLQuery("select value from configuration_details where name=?").setString(0, CPSConstants.LTC_ADVANCE_APPROVED_PERCENTAGE).uniqueResult());

			//Object empRoleId = 	session.createSQLQuery("select org_role_id from emp_role_mapping where org_role_id in( select ori.org_role_id from org_role_instance ori where ori.status = 1 start with ori.org_role_id=(select value from configuration_details where name=?) connect by ori.parent_org_role_id = prior ori.org_role_id) and status=1 and sfid=?").setString(0, CPSConstants.ADMIN).setString(1, workflowMap.getSfid()).list();

			//Object empRoleId = 	session.createSQLQuery("select org_role_id from emp_role_mapping where org_role_id in( select ori.org_role_id from org_role_instance ori where ori.status = 1 start with ori.org_role_id=(select value from configuration_details where name=?) connect by ori.parent_org_role_id = prior ori.org_role_id) and status=1 and sfid=?").setString(0, CPSConstants.ADMIN).setString(1, workflowMap.getSfid()).uniqueResult();
				//Modified by rakesh 		
			String empRoleId = 	(String)session.createSQLQuery("select distinct to_char(case when org_role_id is NOT NUll  then 8 else null end )org_role_id  from emp_role_mapping where org_role_id in( select ori.org_role_id from org_role_instance ori where ori.status = 1 start with ori.org_role_id=(select value from configuration_details where name=?) connect by ori.parent_org_role_id = prior ori.org_role_id) and status=1 and sfid=?").setString(0, CPSConstants.ADMIN).setString(1, workflowMap.getSfid()).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(empRoleId)) {
				workflowMap.getLtcAdvanceRequestDTO().setEmpRoleId(empRoleId.toString());
			}
		} else {
			/*sql="select ltcrd.leave_request_id leaveId,(select case when lrd1.leave_request_id is null then 'success' else (select case when leave.status in (29,8) then 'success' else 'pending' end  from leave_request_details leave where leave.request_id=lrd1.leave_request_id) end result from ltc_request_details lrd1 where lrd1.request_id=ltcrd.request_id) leaveStatus,ltcrd.escort_details escortDetails,ltcrd.return_date returnDate,ltcrd.request_type ltcRequestType,dm.type gazType,to_char(ltcrd.encashment_finance_amt) financeEncashmentAmt,to_char(ltcrd.encashment_cda_amount) cdaEncashmentAmt, " +
					"(select case when (do_part_id is not null and do_part_id !=0) then ref_number||' & '||ref_Date else '' end from reference_number_details rnd where id=do_part_id ) doPartNo,ltm.ltc_type ltcTypeId,(select case when (to_char(lbym.from_date,'yyyy')=to_char(lbym.to_date,'yyyy')) or to_date is null " 
				+"then to_char(lbym.from_date,'YYYY') else to_char(lbym.from_date,'YYYY')||'-'||to_char(lbym.to_date,'YY') end from dual) ltcBlockYearId,ltcrd.place_of_visit placeOfVisit, "
				+"ltcrd.nearest_railway_station nearestRlyStation,ltcrd.departure_date departureDate,(select leave_type from leave_type_master where id=ltcrd.encash_leave_type_id and status=1) elEncashFlag "
				+",ltcrd.encashment_days encashmentDays ,ltcrd.spouse_employment_flag spouseEmploymentFlag, "
				+"(select ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' To '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as name from leave_request_details lrd,leave_type_master ltm where ltm.id=lrd.leave_type_id and "
				+"lrd.request_id=ltcrd.leave_request_id)leaveRequestId,ltcrd.ip_address ipAddress,ltcrd.status status from ltc_request_details ltcrd,ltc_type_master ltm,ltc_block_year_master lbym,designation_mappings dm  "
				+"where request_id=? and ltm.status=1 and ltcrd.ltc_type_id=ltm.id and lbym.status=1 and lbym.id=ltcrd.ltc_block_year_id and dm.desig_id=designation_id";

			
			sql="SELECT LTCRD.LEAVE_REQUEST_ID LEAVEID,nvl(LARD.REQUEST_ID,0) advrequestid, nvl(RWH.ID,0) advreqhistoryid,nvl(LRD.REQUEST_ID,0) reimrequestid, NVL(rwh1.id,0) reimhistoryid,(SELECT CASE WHEN lrd1.leave_request_id IS NULL THEN 'success'  ELSE(SELECT CASE WHEN leave.status IN (29,8) THEN 'success' ELSE 'pending'END  FROM leave_request_details leave WHERE leave.request_id=lrd1.leave_request_id )END result FROM ltc_request_details lrd1 WHERE lrd1.request_id=ltcrd.request_id ) leaveStatus,"
             +" ltcrd.escort_details escortDetails, ltcrd.return_date returnDate, ltcrd.request_type ltcRequestType,dm.type gazType, TO_CHAR(ltcrd.encashment_finance_amt) financeEncashmentAmt,TO_CHAR(ltcrd.encashment_cda_amount) cdaEncashmentAmt,(SELECT CASE WHEN (ltcrd.DO_PART_ID IS NOT NULL AND ltcrd.do_part_id   !=0)THEN ref_number ||' & ' ||ref_Date ELSE '' END FROM REFERENCE_NUMBER_DETAILS RND"
             +" WHERE id= ltcrd.do_part_id) doPartNo,ltm.ltc_type ltcTypeId, (SELECT CASE WHEN (TO_CHAR(lbym.from_date,'yyyy')=TO_CHAR(lbym.to_date,'yyyy'))OR to_date IS NULL THEN TO_CHAR(lbym.from_date,'YYYY') ELSE TO_CHAR(lbym.from_date,'YYYY') ||'-'||TO_CHAR(lbym.to_date,'YY') END FROM dual) ltcBlockYearId,"
             +" ltcrd.place_of_visit placeOfVisit,ltcrd.nearest_railway_station nearestRlyStation, ltcrd.departure_date departureDate,(SELECT leave_type FROM leave_type_master WHERE id  =ltcrd.encash_leave_type_id AND status=1) elEncashFlag , ltcrd.encashment_days encashmentDays, ltcrd.spouse_employment_flag spouseEmploymentFlag,(SELECT ltm.leave_type||'(' ||TO_CHAR(lrd.from_date,'dd-Mon-yyyy')||' To ' ||TO_CHAR(lrd.to_date,'dd-Mon-yyyy') ||')' AS name  FROM leave_request_details lrd,leave_type_master ltm  WHERE ltm.id =lrd.leave_type_id AND lrd.request_id=ltcrd.leave_request_id)leaveRequestId, ltcrd.ip_address ipAddress, LTCRD.STATUS STATUS"
             +" FROM LTC_REQUEST_DETAILS LTCRD LEFT OUTER JOIN LTC_ADVANCE_REQUEST_DETAILS LARD ON(LARD.REFERENCE_REQUEST_ID = LTCRD.REQUEST_ID) LEFT OUTER JOIN LTC_REIMBURSEMENT_DETAILS LRD LEFT OUTER JOIN REQUEST_WORKFLOW_HISTORY RWH1 ON(LRD.REQUEST_ID   = RWH1.REQUEST_ID) ON(LRD.REFERENCE_ID = LARD.REQUEST_ID) LEFT OUTER JOIN REQUEST_WORKFLOW_HISTORY RWH ON(LARD.REQUEST_ID=RWH.REQUEST_ID),  LTC_TYPE_MASTER LTM, LTC_BLOCK_YEAR_MASTER LBYM,   DESIGNATION_MAPPINGS DM WHERE LTCRD.REQUEST_ID =? AND LTM.STATUS  =1 AND LTCRD.LTC_TYPE_ID  =LTM.ID AND LBYM.STATUS =1 AND LBYM.ID =LTCRD.LTC_BLOCK_YEAR_ID AND DM.DESIG_ID= LTCRD.DESIGNATION_ID";
			*/
			/*sql="SELECT LTCRD.LEAVE_REQUEST_ID LEAVEID,nvl(LARD.REQUEST_ID,0) advrequestid, nvl(RWH.ID,0) advreqhistoryid,nvl(LRD.REQUEST_ID,0) reimrequestid, NVL(rwh1.id,0) reimhistoryid, NVL(Rwh3.Id,0) amphistoryid,NVL(LTCRD.Amendment_Ref_Request_Id, 0) amprequestid,(SELECT CASE WHEN lrd1.leave_request_id IS NULL THEN 'success'  ELSE(SELECT CASE WHEN leave.status IN (29,8) THEN 'success' ELSE 'pending'END  FROM leave_request_details leave WHERE leave.request_id=lrd1.leave_request_id )END result FROM ltc_request_details lrd1 WHERE lrd1.request_id=ltcrd.request_id ) leaveStatus,"
	             +" ltcrd.escort_details escortDetails, ltcrd.return_date returnDate, ltcrd.request_type ltcRequestType,dm.type gazType, TO_CHAR(ltcrd.encashment_finance_amt) financeEncashmentAmt,TO_CHAR(ltcrd.encashment_cda_amount) cdaEncashmentAmt,(SELECT CASE WHEN (ltcrd.DO_PART_ID IS NOT NULL AND ltcrd.do_part_id   !=0)THEN ref_number ||' & ' ||ref_Date ELSE '' END FROM REFERENCE_NUMBER_DETAILS RND"
	             +" WHERE id= ltcrd.do_part_id) doPartNo,ltm.ltc_type ltcTypeId, (SELECT CASE WHEN (TO_CHAR(lbym.from_date,'yyyy')=TO_CHAR(lbym.to_date,'yyyy'))OR to_date IS NULL THEN TO_CHAR(lbym.from_date,'YYYY') ELSE TO_CHAR(lbym.from_date,'YYYY') ||'-'||TO_CHAR(lbym.to_date,'YY') END FROM dual) ltcBlockYearId,"
	             +" ltcrd.place_of_visit placeOfVisit,ltcrd.nearest_railway_station nearestRlyStation, ltcrd.departure_date departureDate,(SELECT leave_type FROM leave_type_master WHERE id  =ltcrd.encash_leave_type_id AND status=1) elEncashFlag , ltcrd.encashment_days encashmentDays, ltcrd.spouse_employment_flag spouseEmploymentFlag,(SELECT ltm.leave_type||'(' ||TO_CHAR(lrd.from_date,'dd-Mon-yyyy')||' To ' ||TO_CHAR(lrd.to_date,'dd-Mon-yyyy') ||')' AS name  FROM leave_request_details lrd,leave_type_master ltm  WHERE ltm.id =lrd.leave_type_id AND lrd.request_id=ltcrd.leave_request_id)leaveRequestId, ltcrd.ip_address ipAddress, LTCRD.STATUS STATUS"
	             +" FROM LTC_REQUEST_DETAILS LTCRD LEFT OUTER JOIN LTC_ADVANCE_REQUEST_DETAILS LARD ON(LARD.REFERENCE_REQUEST_ID = LTCRD.REQUEST_ID) LEFT OUTER JOIN LTC_REIMBURSEMENT_DETAILS LRD LEFT OUTER JOIN REQUEST_WORKFLOW_HISTORY RWH1 ON(LRD.REQUEST_ID   = RWH1.REQUEST_ID) ON(LRD.REFERENCE_ID = LARD.REQUEST_ID) LEFT OUTER JOIN REQUEST_WORKFLOW_HISTORY RWH ON(LARD.REQUEST_ID=RWH.REQUEST_ID) Left Outer Join Request_Workflow_History Rwh3 ON(LTCRD.Amendment_Ref_Request_Id=Rwh3.REQUEST_ID AND Rwh3.status =9),  LTC_TYPE_MASTER LTM, LTC_BLOCK_YEAR_MASTER LBYM,   DESIGNATION_MAPPINGS DM WHERE LTCRD.REQUEST_ID =? AND LTM.STATUS  =1 AND LTCRD.LTC_TYPE_ID  =LTM.ID AND LBYM.STATUS =1 AND LBYM.ID =LTCRD.LTC_BLOCK_YEAR_ID AND DM.DESIG_ID= LTCRD.DESIGNATION_ID";
			*/
			
			
			
			sql="select ltcrd.leave_request_id leaveid, nvl(lard.request_id,0) advrequestid, nvl(rwh.id,0) advreqhistoryid,  nvl(lrd.request_id,0) reimrequestid,  NVL(rwh1.id,0) reimhistoryid,  nvl(rwh3.id,0) amphistoryid,  nvl(ltcreim.request_id,0) reimrequestidApp,  NVL(rwh4.id,0) reimhistoryidApp,  NVL(LTCRD.Amendment_Ref_Request_Id, 0) amprequestid,  (SELECT    CASE      WHEN lrd1.leave_request_id IS NULL      THEN 'success'      ELSE        (SELECT          CASE            WHEN leave.status IN (29,8)  THEN 'success'            ELSE 'pending' "
                  + "  END  FROM leave_request_details leave   WHERE leave.request_id=lrd1.leave_request_id )   END result  FROM ltc_request_details lrd1  WHERE lrd1.request_id=ltcrd.request_id  ) leaveStatus,  ltcrd.escort_details escortDetails,  ltcrd.return_date returnDate,  ltcrd.request_type ltcRequestType,  dm.type gazType,  TO_CHAR(ltcrd.encashment_finance_amt) financeEncashmentAmt,  TO_CHAR(ltcrd.encashment_cda_amount) cdaEncashmentAmt,  (SELECT    CASE  WHEN (ltcrd.DO_PART_ID IS NOT NULL      AND ltcrd.do_part_id   !=0) "
                  + " THEN ref_number   ||' & ' ||ref_Date      ELSE ''    END  FROM REFERENCE_NUMBER_DETAILS RND  WHERE id= ltcrd.do_part_id  ) doPartNo,  ltm.ltc_type ltcTypeId,  (SELECT    CASE      WHEN (TO_CHAR(lbym.from_date,'yyyy')=TO_CHAR(lbym.to_date,'yyyy'))      OR to_date            IS NULL      THEN TO_CHAR(lbym.from_date,'YYYY')      ELSE TO_CHAR(lbym.from_date,'YYYY')        ||'-'        ||TO_CHAR(lbym.to_date,'YY')    END  FROM dual  ) ltcBlockYearId,  ltcrd.place_of_visit placeOfVisit, "
                  + " ltcrd.nearest_railway_station nearestRlyStation,  ltcrd.departure_date departureDate, ltcrd.creation_date as creationDate, (SELECT leave_type  FROM leave_type_master  WHERE id  =ltcrd.encash_leave_type_id  AND status=1  ) elEncashFlag ,  ltcrd.encashment_days encashmentDays,  ltcrd.spouse_employment_flag spouseEmploymentFlag,  (SELECT ltm.leave_type    ||'('    ||TO_CHAR(lrd.from_date,'dd-Mon-yyyy')    ||' To '    ||TO_CHAR(lrd.to_date,'dd-Mon-yyyy')    ||')' as name  from leave_request_details lrd,    leave_type_master ltm  where ltm.id      =lrd.leave_type_id  and lrd.request_id=ltcrd.leave_request_id )leaverequestid,  ltcrd.ip_address ipaddress,  "
                  + " ltcrd.status status from ltc_request_details ltcrd left outer join ltc_reimbursement_details ltcreim on(ltcreim.reference_id= ltcrd.request_id) left outer join request_workflow_history rwh4 on(ltcreim.request_id=rwh4.request_id and rwh4.status=9)left outer join ltc_advance_request_details lard on(lard.reference_request_id = ltcrd.request_id) left outer join ltc_reimbursement_details lrd left outer join request_workflow_history rwh1 on(lrd.request_id   = rwh1.request_id) on(lrd.reference_id = lard.request_id) left outer join request_workflow_history rwh on(lard.request_id=rwh.request_id) left outer join request_workflow_history rwh3 "
                  + " on(ltcrd.amendment_ref_request_id=rwh3.request_id and rwh3.status   =9),  ltc_type_master ltm,   ltc_block_year_master lbym,  designation_mappings dm where ltcrd.request_id =? and ltm.status         =1 AND LTCRD.LTC_TYPE_ID  =LTM.ID AND LBYM.STATUS  =1 AND LBYM.ID    =LTCRD.LTC_BLOCK_YEAR_ID AND DM.DESIG_ID  = LTCRD.DESIGNATION_ID";
			
			
			
			workflowMap.setLtcApprovalRequestDTO((LtcApprovalRequestDTO) session.createSQLQuery(sql).addScalar("leaveId",Hibernate.STRING).addScalar("leaveStatus",Hibernate.STRING).addScalar("advrequestid", Hibernate.STRING).addScalar("advreqhistoryid", Hibernate.INTEGER).addScalar("reimrequestid", Hibernate.STRING).addScalar("amprequestid", Hibernate.STRING).addScalar("amphistoryid", Hibernate.INTEGER).addScalar("reimhistoryid", Hibernate.INTEGER).addScalar("escortDetails").addScalar("ltcRequestType").addScalar("gazType").addScalar("financeEncashmentAmt").addScalar("cdaEncashmentAmt").addScalar("doPartNo").addScalar("ltcTypeId").addScalar("ltcBlockYearId").addScalar("placeOfVisit").addScalar("nearestRlyStation").addScalar("departureDate",Hibernate.DATE).addScalar("returnDate",Hibernate.DATE).addScalar("elEncashFlag", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("spouseEmploymentFlag").addScalar("leaveRequestId").addScalar("creationDate", Hibernate.STRING).addScalar("ipAddress").addScalar("status",Hibernate.INTEGER).addScalar("reimrequestidApp", Hibernate.STRING).addScalar("reimhistoryidApp", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LtcApprovalRequestDTO.class)).setString(0, workflowMap.getRequestId()).uniqueResult());
			/*sql = "select fd.name as familyMemberName,to_char(fd.dob,'dd-Mon-yyyy') as dob, CASE WHEN fd.age IS NULL THEN case when floor(months_between(sysdate,fd.dob)/12)>0 then floor(months_between(sysdate,fd.dob)/12)||' Y' else floor(months_between(sysdate,fd.dob))||' M' end ELSE fd.age END     AS age,fr.name as relation "+
						"from family_details fd,family_relation_master fr where fd.status=1 " +
						" and fd.id in(select family_member_id from ltc_txn_details ltd where request_id=?) and fr.id=fd.relation_id order by fr.order_no";
			list = session.createSQLQuery(sql).addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0,workflowMap.getRequestId()).list();*/			
			
			
			
			//age calculation has been changed to LTC departure date :rakesh
			
			sql  ="SELECT fd.name as familyMemberName,  TO_CHAR(fd.dob,'dd-Mon-yyyy') AS dob,  CASE    WHEN fd.age IS NULL  then  case   when trunc(months_between(?,fd.dob)) between 0 and 155   then    trunc(months_between(?,fd.dob)/12)||' Years and '  || (trunc(months_between(?,fd.dob) - trunc(months_between(?,fd.dob)/12)*12)) ||' Months'"+
               "  else  trunc(months_between(?,fd.dob)/12)||' Years'  end else fd.age  end     as age, fr.name as relation from family_details fd,  family_relation_master fr where fd.status=1 and fd.id in (select family_member_id from ltc_txn_details ltd where request_id=?" +
                      "  )AND fr.id=fd.relation_id ORDER BY fr.order_no";
			list = session.createSQLQuery(sql).addScalar("familyMemberName",Hibernate.STRING).addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setDate(0, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcApprovalRequestDTO().getCreationDate()))).setDate(1, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcApprovalRequestDTO().getCreationDate()))).setDate(2, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcApprovalRequestDTO().getCreationDate()))).setDate(3, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcApprovalRequestDTO().getCreationDate()))).setDate(4, CPSUtils.convertStringToDate(CPSUtils.formattedDate(workflowMap.getLtcApprovalRequestDTO().getCreationDate()))).setString(5, workflowMap.getRequestId()).list();
					//list = session.createSQLQuery(sql).addScalar("familyMemberName",Hibernate.STRING).addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setDate(0, workflowMap.getLtcApprovalRequestDTO().getDepartureDate()).setDate(1, workflowMap.getLtcApprovalRequestDTO().getDepartureDate()).setDate(2, workflowMap.getLtcApprovalRequestDTO().getDepartureDate()).setDate(3, workflowMap.getLtcApprovalRequestDTO().getDepartureDate()).setDate(4, workflowMap.getLtcApprovalRequestDTO().getDepartureDate()).setString(5, workflowMap.getRequestId()).list();
			workflowMap.getLtcApprovalRequestDTO().setLtcMemberDetails(list);

		}
		
	} catch (Exception e) {
		throw e;
	} finally {
		//session.close();
	}
	return workflowMap;
}

	public WorkFlowMappingBean getDopartShowFlag(WorkFlowMappingBean workflowMap) throws Exception{
	
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String historyID = (String)session
					.createSQLQuery("select id||'' from request_workflow_history where assigned_to=(select sfid from emp_role_mapping where org_role_id=(select value from configuration_details where upper(name)=upper('ADMIN')) and status=1) and request_id=? and request_stage=(select stage_id from workflow where workflow_id=(select workflow_id from request_workflow_mapping where request_type_id=?) and to_role=?)").setString(0, workflowMap.getRequestId()).setString(1, workflowMap.getRequestTypeID()).setString(2, CPSConstants.ADMINID).uniqueResult();
			if (CPSUtils.isNullOrEmpty(historyID)) {
				workflowMap.setDoPartShowFlag("N");
			} else {
				String doPartRequestStage = ((BigDecimal) session
						.createSQLQuery(
								"select stage_id from workflow where workflow_id=(select workflow_id from request_workflow_mapping where request_type_id=?) and to_role=?")
						.setString(0, workflowMap.getRequestTypeID()).setString(1, CPSConstants.ADMINID).uniqueResult()).toPlainString();
				String presentStage = (String) session.createSQLQuery(
						"select min(request_stage)||'' from request_workflow_history where request_id=? and assigned_to=?").setString(0,
						workflowMap.getRequestId()).setString(1, workflowMap.getSfid()).uniqueResult();
				if (CPSUtils.isNullOrEmpty(presentStage)) {
					presentStage = "1";
				}
				if (CPSUtils.compareStrings(presentStage, doPartRequestStage)) {
					workflowMap.setDoPartShowFlag("Y");
					
				
				

				} else {
					workflowMap.setDoPartShowFlag("N");
				}
			}
		}catch(Exception e){
		throw e;
	} finally {
		//session.close();
	}
	return workflowMap;
}

	@SuppressWarnings("unchecked")
	public LtcApprovalRequestDTO getLtcReimbursementDetails(String requestID, String requestType, String sfID) throws Exception {
		Session session = null;
		LtcApprovalRequestDTO ltcAppReqDTO = null;
		List<LtcMemberDetailsDTO> list = null;
		
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			 ltcAppReqDTO =new LtcApprovalRequestDTO();
			String role =(String)session.createSQLQuery("select distinct to_char(case when rm.id in(1,2,14) then 8 else null  end)id from role_master rm,   application_role_mapping apm WHERE rm.id   = apm.role_id and rm.status =1 AND apm.sfid  =? and apm.status=1 AND rm.id    IN (1,2,14)").setString(0, sfID).uniqueResult();
			// Object role =(String)session.createSQLQuery("select distinct to_char(case when rm.id in(1,2,14) then 8 else null  end)id from role_master rm,   application_role_mapping apm WHERE rm.id   = apm.role_id and rm.status =1 AND apm.sfid  =? and apm.status=1 AND rm.id    IN (1,2,14)").setString(0, sfID).uniqueResult();
				
			
			if (CPSUtils.compareStrings(requestType, CPSConstants.LTCREIMBURSEMENT)) {

	/*			String sql = "select lr.amount settlementAmount,'ltcReim' requestType,lr.request_id requestId,lr.ip_address ipAddress, (select ref_number||' & '||to_char(ref_date,'dd-Mon-yyyy') from reference_number_details where id=lrd.do_part_id) doPartNo,lr.total_amount totalAmount,ltm.ltc_type ltcTypeId,to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy') ltcBlockYearId,lrd.place_of_visit placeOfVisit,(select ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as name from leave_request_details lrd,"
						+ "leave_type_master ltm where ltm.id=lrd.leave_type_id and lrd.request_id=lrd.leave_request_id) leaveRequestId from ltc_request_details lrd,ltc_type_master ltm,ltc_block_year_master lym,ltc_reimbursement_details lr where ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.request_id=(select reference_id from ltc_reimbursement_details where request_id=?) and lr.reference_id=lrd.request_id and lr.request_id=?";
				
	*/			
				
				String sql="select lr.amount settlementamount, 'ltcReim' requesttype,lrd.departure_date  as departureDate, lr.request_id requestid,  lrd.request_id requestidApprival, rwh1.id historyrequestidApprival,  lr.ip_address ipaddress, (select ref_number  ||' & ' ||to_char(ref_date,'dd-Mon-yyyy') from reference_number_details  where id=lrd.do_part_id ) dopartno, lr.total_amount totalamount, ltm.ltc_type ltctypeid, to_char(lym.from_date,'yyyy') ||'-'||to_char(lym.to_date,'yy') ltcblockyearid,  lrd.place_of_visit placeofvisit,  (select ltm.leave_type  ||'('||to_char(lrd.from_date,'dd-Mon-yyyy')  ||' '||'To'  ||' ' ||to_char(lrd.to_date,'dd-Mon-yyyy') ||')' as name  from leave_request_details lrd,  leave_type_master ltm  where ltm.id      =lrd.leave_type_id and lrd.request_id=lrd.leave_request_id ) leaverequestid from ltc_request_details lrd, ltc_type_master ltm,  ltc_block_year_master lym,  ltc_reimbursement_details lr left outer join ltc_request_details lrda on (lrda.request_id=lr.reference_id)  left outer join request_workflow_history rwh1  "+
                           " on(lrda.request_id=rwh1.request_id and rwh1.status =8) where ltm.id      =lrd.ltc_type_id and lym.id        =lrd.ltc_block_year_id and lrd.request_id= (SELECT distinct reference_id FROM ltc_reimbursement_details WHERE request_id=? ) AND lr.reference_id=lrd.request_id AND lr.request_id  =?";
				ltcAppReqDTO = (LtcApprovalRequestDTO) session.createSQLQuery(sql).addScalar("settlementAmount", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).addScalar("departureDate", Hibernate.DATE).addScalar("requestId",
						Hibernate.STRING).addScalar("ltcTypeId").addScalar("doPartNo").addScalar("totalAmount", Hibernate.STRING).addScalar("ipAddress").addScalar("ltcBlockYearId").addScalar("requestidApprival", Hibernate.STRING).addScalar("historyrequestidApprival", Hibernate.INTEGER).addScalar(
						"placeOfVisit").addScalar("leaveRequestId").setResultTransformer(Transformers.aliasToBean(LtcApprovalRequestDTO.class)).setString(0, requestID).setString(1, requestID)
						.uniqueResult();

			} else if (CPSUtils.compareStrings(requestType, CPSConstants.LTCSETTLEMENT)) {
						String sql = "select distinct lrd.excess_amount excessAmount,lrd.excess_amount_fine excessAmountFine,lrd.departure_date as departureDate,(case when lrd.excess_amount is  null then lrd.settlement_amount else lrd.excess_amount end) settlementAmount,'ltcSett' requestType,lr.request_id requestId,lr.status status,(lr.total_amount-lrd.cda_amount) totalAmount,lrd.ip_address ipAddress,lrd.cda_amount issuedAmount,(select ref_number||' & '||to_char(ref_date,'dd-Mon-yyyy') from reference_number_details where id=lrd.do_part_id) doPartNo,ltm.ltc_type ltcTypeId,to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy') ltcBlockYearId,lrd.place_of_visit placeOfVisit,(select ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as name from leave_request_details lrd,"
						+ "leave_type_master ltm where ltm.id=lrd.leave_type_id and lrd.request_id=lrd.leave_request_id) leaveRequestId from ltc_advance_request_details lrd,ltc_type_master ltm,ltc_block_year_master lym,ltc_reimbursement_details lr where ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.request_id=(select distinct reference_id from ltc_reimbursement_details where request_id=?) and lr.reference_id=lrd.request_id and lr.status not in(?) AND LR.request_id=?";
				ltcAppReqDTO = (LtcApprovalRequestDTO) session.createSQLQuery(sql).addScalar("excessAmount", Hibernate.STRING).addScalar("excessAmountFine", Hibernate.STRING).addScalar("departureDate", Hibernate.DATE).addScalar("requestType", Hibernate.STRING).addScalar("ltcTypeId")
						.addScalar("doPartNo").addScalar("status",Hibernate.INTEGER).addScalar("totalAmount", Hibernate.STRING).addScalar("issuedAmount", Hibernate.STRING).addScalar("settlementAmount", Hibernate.STRING).addScalar(
								"requestId", Hibernate.STRING).addScalar("ipAddress").addScalar("ltcBlockYearId").addScalar("placeOfVisit").addScalar("leaveRequestId").setResultTransformer(
								Transformers.aliasToBean(LtcApprovalRequestDTO.class)).setString(0, requestID).setString(1, CPSConstants.STATUSCANCELLED).setString(2, requestID).uniqueResult();
			
				
			/* String sql = "select lrd.excess_amount excessAmount,lrd.excess_amount_fine excessAmountFine,lrd.departure_date as departureDate,(case when lrd.excess_amount is  null then lrd.settlement_amount else lrd.excess_amount end) settlementAmount,'ltcSett' requestType,lr.request_id requestId,lr.status status,(lr.total_amount-lrd.cda_amount) totalAmount,lrd.ip_address ipAddress,lrd.cda_amount issuedAmount,(select ref_number||' & '||to_char(ref_date,'dd-Mon-yyyy') from reference_number_details where id=lrd.do_part_id) doPartNo,ltm.ltc_type ltcTypeId,to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy') ltcBlockYearId,lrd.place_of_visit placeOfVisit,(select ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as name from leave_request_details lrd,"
						+ "leave_type_master ltm where ltm.id=lrd.leave_type_id and lrd.request_id=lrd.leave_request_id) leaveRequestId from ltc_advance_request_details lrd,ltc_type_master ltm,ltc_block_year_master lym,ltc_reimbursement_details lr where ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.request_id=(select reference_id from ltc_reimbursement_details where request_id=?) and lr.reference_id=lrd.request_id and lr.status not in(?,?)";
				ltcAppReqDTO = (LtcApprovalRequestDTO) session.createSQLQuery(sql).addScalar("excessAmount", Hibernate.STRING).addScalar("excessAmountFine", Hibernate.STRING).addScalar("departureDate", Hibernate.DATE).addScalar("requestType", Hibernate.STRING).addScalar("ltcTypeId")
						.addScalar("doPartNo").addScalar("status",Hibernate.INTEGER).addScalar("totalAmount", Hibernate.STRING).addScalar("issuedAmount", Hibernate.STRING).addScalar("settlementAmount", Hibernate.STRING).addScalar(
								"requestId", Hibernate.STRING).addScalar("ipAddress").addScalar("ltcBlockYearId").addScalar("placeOfVisit").addScalar("leaveRequestId").setResultTransformer(
								Transformers.aliasToBean(LtcApprovalRequestDTO.class)).setString(0, requestID).setString(1, CPSConstants.STATUSCANCELLED).setString(2, CPSConstants.STATUSDECLINED).uniqueResult();*/
				
			}
			
			/*list = session
					.createSQLQuery(
							"select fd.name as familyMemberName,to_char(fd.dob,'dd-Mon-yyyy') as dob,fd.age as age,fr.name as relation from family_details fd,family_relation_master fr where fd.status=1"
									+ "and fd.id in(select family_member_id from ltc_txn_details where request_id=(select reference_id from ltc_reimbursement_details where request_id=?) and reference_id=?) and fr.id=fd.relation_id order by fr.name")*/
			if(!CPSUtils.isNullOrEmpty(ltcAppReqDTO))	{
				if(CPSUtils.compareStrings(requestType, CPSConstants.LTCSETTLEMENT)){
				if(ltcAppReqDTO.getStatus()==6){				
				list = session
					.createSQLQuery(		
									" select fd.name as familymembername,  to_char(fd.dob,'dd-Mon-yyyy') as dob,  fr.name as relation, case    when fd.age is null  then  case   when trunc(months_between(?,fd.dob)) between 0 and 155   then    trunc(months_between(?,fd.dob)/12)||' Years and '  || (trunc(months_between(?,fd.dob) - trunc(months_between(?,fd.dob)/12)*12)) ||' Months' "
					                + " else  trunc(months_between(?,fd.dob)/12)||' Years'  end else fd.age  end     as age from family_details fd,  family_relation_master fr where fd.status=1 and fd.id     in  (select family_member_id   from ltc_txn_details   where request_id= (SELECT distinct reference_id FROM ltc_reimbursement_details WHERE request_id=? )   )AND fr.id=fd.relation_id ORDER BY fr.name ")
					.addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setDate(0, ltcAppReqDTO.getDepartureDate()).setDate(1, ltcAppReqDTO.getDepartureDate()).setDate(2, ltcAppReqDTO.getDepartureDate()).setDate(3, ltcAppReqDTO.getDepartureDate()).setDate(4, ltcAppReqDTO.getDepartureDate()).setString(5,
							requestID).list();}
				else{
					list = session
					.createSQLQuery(		
									" select fd.name as familymembername,  to_char(fd.dob,'dd-Mon-yyyy') as dob,  fr.name as relation, case    when fd.age is null  then  case   when trunc(months_between(?,fd.dob)) between 0 and 155   then    trunc(months_between(?,fd.dob)/12)||' Years and '  || (trunc(months_between(?,fd.dob) - trunc(months_between(?,fd.dob)/12)*12)) ||' Months' "
					                + " else  trunc(months_between(?,fd.dob)/12)||' Years'  end else fd.age  end     as age from family_details fd,  family_relation_master fr where fd.status=1 and fd.id     in  (select family_member_id   from ltc_txn_details   where request_id= (SELECT distinct reference_id FROM ltc_reimbursement_details WHERE request_id=? )  AND reference_id=? )AND fr.id=fd.relation_id ORDER BY fr.name ")
					.addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setDate(0, ltcAppReqDTO.getDepartureDate()).setDate(1, ltcAppReqDTO.getDepartureDate()).setDate(2, ltcAppReqDTO.getDepartureDate()).setDate(3, ltcAppReqDTO.getDepartureDate()).setDate(4, ltcAppReqDTO.getDepartureDate()).setString(5,
							requestID).setString(6, requestID).list();
				}
				}
				else {
					
					list = session
						.createSQLQuery(		
										" select fd.name as familymembername,  to_char(fd.dob,'dd-Mon-yyyy') as dob,  fr.name as relation, case    when fd.age is null  then  case   when trunc(months_between(?,fd.dob)) between 0 and 155   then    trunc(months_between(?,fd.dob)/12)||' Years and '  || (trunc(months_between(?,fd.dob) - trunc(months_between(?,fd.dob)/12)*12)) ||' Months' "
						                + " else  trunc(months_between(?,fd.dob)/12)||' Years'  end else fd.age  end     as age from family_details fd,  family_relation_master fr where fd.status=1 and fd.id     in  (select family_member_id   from ltc_txn_details   where request_id= (SELECT distinct reference_id FROM ltc_reimbursement_details WHERE request_id=? )  AND reference_id=? )AND fr.id=fd.relation_id ORDER BY fr.name ")
						.addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setDate(0, ltcAppReqDTO.getDepartureDate()).setDate(1, ltcAppReqDTO.getDepartureDate()).setDate(2, ltcAppReqDTO.getDepartureDate()).setDate(3, ltcAppReqDTO.getDepartureDate()).setDate(4, ltcAppReqDTO.getDepartureDate()).setString(5,
								requestID).setString(6, requestID).list();
				}}
				
			
			else{
				list = session
				.createSQLQuery(
						"select fd.name as familyMemberName,to_char(fd.dob,'dd-Mon-yyyy') as dob,fd.age as age,fr.name as relation from family_details fd,family_relation_master fr where fd.status=1"
								+ "and fd.id in(select family_member_id from ltc_txn_details where request_id=(select reference_id from ltc_reimbursement_details where request_id=?) and reference_id=?) and fr.id=fd.relation_id order by fr.name").addScalar("familyMemberName").addScalar("age").addScalar("dob").addScalar("relation").setResultTransformer(Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0,requestID).setString(1, requestID).list();

								
			}

			ltcAppReqDTO.setLtcMemberDetails(list);
			ltcAppReqDTO.setLtcPenalMasterDTO(tadaRequestProcess.getPenalInterestRate());
			Object empRoleId = session.createSQLQuery(
					"select org_role_id as empRoleId  from emp_role_mapping where parent_role_id in "
							+ "(select department_id from departments_master where department_name in ('DOMS','Finance')) and sfid=? and status=1 and internal_role='Head'").setString(0, sfID)
					.uniqueResult();
			if (!CPSUtils.isNullOrEmpty(empRoleId)) {
				ltcAppReqDTO.setEmpRoleId(empRoleId.toString());
			}
			
			if(!CPSUtils.isNullOrEmpty(role)){
			 ltcAppReqDTO.setOrgroleIdLink(role);
			 }

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return ltcAppReqDTO;
	}

	@SuppressWarnings("unchecked")
	public List<LtcJourneyDetailsDTO> getLtcJourneyDetails(String requestID) throws Exception {
		Session session = null;
		List<LtcJourneyDetailsDTO> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list = session.createCriteria(LtcJourneyDetailsDTO.class).add(Expression.eq("referenceId", requestID)).addOrder(Order.asc("departureDate")).addOrder(Order.asc("departureTime")).addOrder(Order.asc("arrivalDate")).addOrder(Order.asc("arrivalTime")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	@Override
	public String getPresentRequestStage(String requestID, String sfid) throws Exception {
		String stage = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			stage = (String) session.createSQLQuery("select min(request_stage)||'' from request_workflow_history where request_id=? and assigned_from=?").setString(0, requestID).setString(1, sfid)
					.uniqueResult();
			if (CPSUtils.isNullOrEmpty(stage)) {
				stage = "1";
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return stage;
	}

	@Override
	public String submitDOPartDetails(RequestBean rb) throws Exception {
		Session session = null;
		Transaction tx = null;
		DoPartBean doPartDTO = new DoPartBean();
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			EmpTransferBean transferBean = (EmpTransferBean) session.get(EmpTransferBean.class, rb.getRequestID());

			if (!CPSUtils.isNullOrEmpty(rb.getDoPartNo())) {
				//tx = session.beginTransaction();

				doPartDTO.setDoPartNo(rb.getDoPartNo());
				doPartDTO.setDoPartDate(rb.getDoPartDate());
				doPartDTO.setStatus(1);
				doPartDTO.setCreatedBy(rb.getSfID());
				doPartDTO.setCreationDate(CPSUtils.getCurrentDate());
				doPartDTO.setRefType("I");
				session.saveOrUpdate(doPartDTO);
				session.flush();//tx.commit() ;
				transferBean.setDoPartDTO((DoPartDTO) session.createCriteria(DoPartDTO.class).addOrder(Order.desc("id")).setMaxResults(1).uniqueResult());
			}

			//tx = session.beginTransaction();
			transferBean.setHqRefNo(rb.getHqRefNo());
			transferBean.setReceivedDate(rb.getReceivedDate());
			transferBean.setModifiedBy(rb.getSfID());
			transferBean.setModifiedDate(CPSUtils.getCurrentDateWithTime());
			session.saveOrUpdate(transferBean);
			session.flush();//tx.commit() ;
			rb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return rb.getMessage();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DoPartBean> getDopartNumber(Date doPartDate, String gazType) throws Exception {

		Session session = null;
		List<DoPartBean> doPartNumber = null;
		try {
			if (gazType.equals("GAZETTED")) {
				gazType = "G";
			} else {
				gazType = "NG";
			}

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			doPartNumber = session.createQuery("from DoPartBean where doPartDate=? and gazettedType=? and status=1").setString(0, new SimpleDateFormat("dd-MMM-yyyy").format(doPartDate)).setString(1,
					gazType).list();
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

			

		}
		return doPartNumber;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AlertMessageDTO> myAlerts(String sfID,String limit,String size) throws Exception {
		Session session = null;
		List<AlertMessageDTO> alertList = null;
		try {
			session = hibernateUtils.getSession();

			Criteria crit = session.createCriteria(AlertMessageDTO.class).add(Expression.eq("assignedTo", sfID)).add(Expression.ne(CPSConstants.STATUS, 0));
			if (CPSUtils.compareStrings(limit, CPSConstants.LIMIT)) {
				// limit the records
				crit.setMaxResults(Integer.parseInt(size));
			}
			 alertList = crit.addOrder(Order.desc("assignedDate")).list();
		} catch (Exception e) {
			throw e;
		} return alertList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<RequestsDTO> myHoldRequests(String sfID,String requestId,String type) throws Exception {
		Session session = null;
		List<RequestsDTO> holdRequestList = null;
		try {
			session = hibernateUtils.getSession();
			String qry = null;
			if(type==null || type==""){
				 /*COMENTED ON 11-09-2013---
			/* String qry1 = "select count(*) as count from request_workflow_history where assigned_from='"+sfID+"' and id in " +
					     "(select min(id) from request_workflow_history where request_id in (SELECT REQUEST_ID FROM " +
					     " REQUEST_WORKFLOW_HISTORY WHERE ASSIGNED_FROM ='"+sfID+"' and request_id in " +
					     "(select  request_id from request_workflow_history where stage_status=103) AND STAGE_STATUS=103) group by request_id )";
					    */
				String qry1 = "  SELECT COUNT(*) AS COUNT FROM request_workflow_history WHERE assigned_from='"+sfID+"' AND id IN " +
						" (SELECT MIN(id) FROM request_workflow_history WHERE request_id IN" +
						" (SELECT request_id FROM request_workflow_history WHERE stage_status=103) GROUP BY request_id)";
			
			 holdRequestList = session.createSQLQuery(qry1).addScalar("count", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(RequestsDTO.class)).list();
			}else{
				qry =   "SELECT REMARKS as remarks,to_char(ACTIONED_DATE,'dd-Mon-yyyy') as actionedDate," +
				"(SELECT REQUEST_TYPE FROM REQUEST_MASTER WHERE REQUEST_TYPE_ID=RH.REQUEST_TYPE_ID) AS requestType," +
				"t2.request_id as requestID FROM REQUEST_WORKFLOW_HISTORY rh," +
				"(SELECT MAX(ID) as id FROM REQUEST_WORKFLOW_HISTORY WHERE REQUEST_ID IN(" +
				"SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY  WHERE ID IN(SELECT MIN(ID) FROM " +
				" REQUEST_WORKFLOW_HISTORY  WHERE REQUEST_ID IN(SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY " +
				" WHERE STAGE_STATUS=103) AND STAGE_STATUS=103 GROUP BY REQUEST_ID) AND ASSIGNED_FROM='"+sfID+"')GROUP BY REQUEST_ID)T1," +
				"(SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY  WHERE ID IN(SELECT MIN(ID) FROM " +
				" REQUEST_WORKFLOW_HISTORY  WHERE REQUEST_ID IN(SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY " +
				" WHERE STAGE_STATUS=103) AND STAGE_STATUS=103 GROUP BY REQUEST_ID) AND ASSIGNED_FROM='"+sfID+"')T2 where " +
				" rh.id in(t1.id) and rh.request_id in(t2.request_id) AND rh.assigned_from='"+sfID+"'";
		holdRequestList = session.createSQLQuery(qry).addScalar("requestID", Hibernate.STRING).addScalar("remarks", Hibernate.STRING).addScalar("actionedDate", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RequestsDTO.class)).list();
			}
			
           /*24-07-2013
            qry =   "SELECT REMARKS as remarks,to_char(ACTIONED_DATE,'dd-Mon-yyyy') as actionedDate," +
				"(SELECT REQUEST_TYPE FROM REQUEST_MASTER WHERE REQUEST_TYPE_ID=RH.REQUEST_TYPE_ID) AS requestType," +
				"t2.request_id as requestID FROM REQUEST_WORKFLOW_HISTORY rh," +
				"(SELECT MAX(ID) as id FROM REQUEST_WORKFLOW_HISTORY WHERE REQUEST_ID IN(" +
				"SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY  WHERE ID IN(SELECT MIN(ID) FROM " +
				" REQUEST_WORKFLOW_HISTORY  WHERE REQUEST_ID IN(SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY " +
				" WHERE STAGE_STATUS=103) AND STAGE_STATUS=103 GROUP BY REQUEST_ID) AND ASSIGNED_FROM='"+sfID+"')GROUP BY REQUEST_ID)T1," +
				"(SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY  WHERE ID IN(SELECT MIN(ID) FROM " +
				" REQUEST_WORKFLOW_HISTORY  WHERE REQUEST_ID IN(SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY " +
				" WHERE STAGE_STATUS=103) AND STAGE_STATUS=103 GROUP BY REQUEST_ID) AND ASSIGNED_FROM='"+sfID+"')T2 where " +
				" rh.id in(t1.id) and rh.request_id in(t2.request_id)";
            */
			
		} catch (Exception e) {
			throw e;
		} return holdRequestList;
	}
	/*
	public String requestStatus(String sfid) throws Exception {
		Session session = null;
		List<RequestsDTO> list = null;
		String type="";
		
		Connection con = null;
		
		try {
			  session = hibernateUtils.getSession();
			  con = session.connection();
			  		 
		
			   String qry = "select ORG_ROLE_ID as historyID from EMP_ROLE_MAPPING where SFID = ?";
			   list = session.createSQLQuery(qry).addScalar("historyID", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RequestsDTO.class)).setString(0, sfid).list();
			    for(int i=0;i<list.size();i++){
				  String val= list.get(i).getHistoryID();
				  if(CPSUtils.isNullOrEmpty(val)){
					  type = "no";
					  break;
				  }else{
					  type = "yes";
				  }
			   }
			
			  String qry = "select APM.SFID as sfid,RM.id as id,RRM.APPLICATION_ROLE_ID AproleID,RRM.REQUEST_TYPE_ID as requestTypeId  from ROLE_MASTER RM,REQUEST_ROLE_MAPPING RRM,APPLICATION_ROLE_MAPPING APM" +
                              " where rm.id=RRM.APPLICATION_ROLE_ID and APM.ROLE_ID = RRM.APPLICATION_ROLE_ID and sfid = ?";
			  list = session.createSQLQuery(qry).addScalar("sfid", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("AProleID", Hibernate.INTEGER).addScalar("requestTypeId", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RequestsDTO.class)).setString(0, sfid).list();
			  
			   for(int i=0;i<list.size();i++){
				  String val= list.get(i).getRequestTypeId();
				  if(CPSUtils.isNullOrEmpty(val)){
					  type = "no";
					  break;
				  }else{
					  type = "yes";
				  }
			   }
			
	        }catch (Exception e) {
			     throw e;
		    }
		   return type;
	}
	*/
	@Override
	public List<RequestsDTO> totalHoldRequests(String sfID,String requestId) throws Exception{
		@SuppressWarnings("unused")
		Session session = null;
		List<RequestsDTO> totalHoldRequsts = null;
		try{
			session = hibernateUtils.getSession();
			String qry = null;
			/*qry = "select unique request_id as requestID from request_workflow_history where request_id in" +
					" (select request_id from request_workflow_history where '"+sfID+"' in(assigned_from,assigned_to)) " +
					" and stage_status=103";*/
			qry = "SELECT REQUEST_ID as requestID,REMARKS as remarks FROM REQUEST_WORKFLOW_HISTORY WHERE ID IN" +
					" (SELECT MAX(ID) FROM REQUEST_WORKFLOW_HISTORY WHERE REQUEST_ID IN" +
					" (SELECT UNIQUE REQUEST_ID AS REQUESTID FROM REQUEST_WORKFLOW_HISTORY WHERE REQUEST_ID IN" +
					" (SELECT REQUEST_ID FROM REQUEST_WORKFLOW_HISTORY WHERE '"+sfID+"' IN(ASSIGNED_FROM,ASSIGNED_TO)) " +
					"and stage_status=103) GROUP BY REQUEST_ID) and STAGE_STATUS=103";
			totalHoldRequsts = session.createSQLQuery(qry).addScalar("requestID", Hibernate.STRING).addScalar("remarks", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RequestsDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return totalHoldRequsts;
	}
	@Override
	public AlertMessageDTO getAlertDetails(WorkFlowMappingBean workflowmap) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			workflowmap.setAlertDetails((AlertMessageDTO) session.createCriteria(AlertMessageDTO.class).add(Expression.eq("id", Integer.valueOf(workflowmap.getHistoryID()))).uniqueResult());
		} catch (Exception e) {
			throw e;
		} 
		return workflowmap.getAlertDetails();
	}

	@Override
	public String submitAlertResponse(WorkFlowMappingBean workflowmap) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			AlertMessageDTO alertMessageDTO = (AlertMessageDTO) session.get(AlertMessageDTO.class, Integer.valueOf(workflowmap.getAlertMsgID()));
			alertMessageDTO.setStatus(workflowmap.getStatus());
			if (workflowmap.getStatus() != 0) {
				alertMessageDTO.setResponse(workflowmap.getRemarks());
			}
			alertMessageDTO.setAssignedDate(CPSUtils.getCurrentDateWithTime());
			alertMessageDTO.setActionedIpAddress(workflowmap.getIpAddress());
			session.saveOrUpdate(alertMessageDTO);
			workflowmap.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		} 
		return workflowmap.getResult();
	}

	@Override
	public WorkFlowMappingBean editInstallmentDetails(WorkFlowMappingBean workBeanMap) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanHBAPaymentDTO loanPaymentDTO = new LoanHBAPaymentDTO();
			loanPaymentDTO = (LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(workBeanMap.getNodeID()))).uniqueResult();
			loanPaymentDTO.setRequestedAmount(Float.valueOf(workBeanMap.getReqAmount()));
			loanPaymentDTO.setRequestedInstalments(workBeanMap.getRequestedInstalments());
			loanPaymentDTO.setRequestedInterestInstalments(workBeanMap.getRequestedInterestInstalments());
			session.saveOrUpdate(loanPaymentDTO);
			workBeanMap.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return workBeanMap;
	}

	@Override
	public QuarterRequestBean getQuarterDetails(WorkFlowMappingBean workBeanMap) throws Exception {
		Session session = null;
		Transaction tx = null;
		QuarterRequestBean quarterRequestBean = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			quarterRequestBean = (QuarterRequestBean) session.createCriteria(QuarterRequestBean.class).add(Expression.eq("requestID", workBeanMap.getRequestId())).uniqueResult();
			quarterRequestBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return quarterRequestBean;
	}

	@Override
	public String setHQSendingStatus(RequestBean rb) throws Exception {

		Session session = null;
		try {
			session = hibernateUtils.getSession();
			// update the status in this stage
			session
					.createSQLQuery(
							"update request_workflow_history set stage_status=?,status=?,actioned_date=sysdate,remarks=?,ip_address=? where id in (select id from request_workflow_history where request_id=(select request_id from "
									+ "request_workflow_history where id=?) and request_stage=(select max(request_stage) from request_workflow_history where "
									+ "request_id=(select request_id from request_workflow_history where id=?)))").setString(0, CPSConstants.STATUSHEADQUARTER).setString(1,
							CPSConstants.STATUSAPPROVED).setString(2, rb.getRemarks()).setString(3, rb.getIpAddress()).setString(4, rb.getHistoryID()).setString(5, rb.getHistoryID()).executeUpdate();
			rb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	
	}

	@Override
	public HQRequestDTO getHQRequestDetails(WorkFlowMappingBean workBeanMap)throws Exception {
		Session session = null;
		Transaction tx = null;
		HQRequestDTO hqRequestDTO = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			hqRequestDTO = (HQRequestDTO) session.createCriteria(HQRequestDTO.class).add(Expression.eq("requestID", workBeanMap.getRequestId())).uniqueResult();
			hqRequestDTO.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return hqRequestDTO;
	}
	//code for adding conditions 
	@Override
	@SuppressWarnings("unchecked")
	public List<TelephoneDesigEligibilityDetailsDTO> teleMaxAmountCondition(WorkFlowMappingBean workBeanMap)throws Exception{
	     Session session=null;
	 	List<TelephoneDesigEligibilityDetailsDTO> teleMaxAmount = null;
	     try{
	    	 session=hibernateUtils.getSession();
	    	 if(workBeanMap.getTelephoneBillClaimList().get(0).getInternetFlag().equals("1")){
					String query ="select amount_withInternet as amountWithInternet,service_tax_withinternet as serviceTaxWithInternet,total_amount_withinternet as totAmountWithInternet from tele_desig_eligibile_details where designation_id=? and status=1";
					teleMaxAmount= session.createSQLQuery(query).addScalar("amountWithInternet", Hibernate.INTEGER).addScalar("serviceTaxWithInternet", Hibernate.FLOAT).addScalar("totAmountWithInternet", Hibernate.INTEGER).setString(0, workBeanMap.getTelephoneBillClaimList().get(0).getDesignationId()).setResultTransformer(Transformers.aliasToBean(TelephoneDesigEligibilityDetailsDTO.class)).list();
					
					workBeanMap.setAmount(String.valueOf(teleMaxAmount.get(0).getAmountWithInternet()));
					workBeanMap.setServiceTax(String.valueOf(teleMaxAmount.get(0).getServiceTaxWithInternet()));
					workBeanMap.setTotAmount(String.valueOf(teleMaxAmount.get(0).getTotAmountWithInternet()));
				    workBeanMap.setInternetFlag(workBeanMap.getTelephoneBillClaimList().get(0).getInternetFlag());
	    	 }else if(workBeanMap.getTelephoneBillClaimList().get(0).getInternetFlag().equals("2")){
					String query ="select amount_without_internet as amountWithoutInternet,service_tax_without_internet as serviceTaxWithoutInternet,total_amount_without_internet as totAmountWithoutInternet  from tele_desig_eligibile_details where designation_id=? and status=1";
					teleMaxAmount= session.createSQLQuery(query).addScalar("amountWithoutInternet", Hibernate.INTEGER).addScalar("serviceTaxWithoutInternet", Hibernate.FLOAT).addScalar("totAmountWithoutInternet", Hibernate.INTEGER).setString(0, workBeanMap.getTelephoneBillClaimList().get(0).getDesignationId()).setResultTransformer(Transformers.aliasToBean(TelephoneDesigEligibilityDetailsDTO.class)).list();
					
					workBeanMap.setAmount(String.valueOf(teleMaxAmount.get(0).getAmountWithoutInternet()));
					workBeanMap.setServiceTax(String.valueOf(teleMaxAmount.get(0).getServiceTaxWithoutInternet()));
					workBeanMap.setTotAmount(String.valueOf(teleMaxAmount.get(0).getTotAmountWithoutInternet()));
				    workBeanMap.setInternetFlag(workBeanMap.getTelephoneBillClaimList().get(0).getInternetFlag());
	    	 }
		 }catch (Exception e) {
	    	 throw e;
	     }finally{
	     }
	     return teleMaxAmount;
    }
	
	@Override
	public String getDesignationId(String sfid) throws Exception{
		Session session=null;
		try{
			 session=hibernateUtils.getSession();	
			 return session.createSQLQuery("select GET_DESIG_ID(?) from dual").setString(0, sfid).uniqueResult().toString();
		}catch (Exception e) {
			throw e;
		}
	}
	@Override
	public WorkFlowMappingBean getfinanceAcceptableAmount(WorkFlowMappingBean woBean)throws Exception{
		Session session =null;
		try{
			session=hibernateUtils.getSession();
			String query ="(select distinct (case when to_number(sanctionedAmount)>to_number(eligibility) then to_number(eligibility) else to_number(sanctionedAmount) end) eligibleAmount from (select distinct tfrd.grand_total as grantTotal,tfrd.sanctioned_amount as sanctionedAmount, (case when tbcd.internet_flag =1 then tded.total_amount_withinternet else tded.total_amount_without_internet end)  eligibility from tution_fee_request_details tfrd,telephone_bill_claim_details tbcd,tele_desig_eligibile_details tded where tfrd.sfid='"+woBean.getRequesterSfid()+"' and tfrd.request_id=? and tbcd.sfid=tfrd.sfid and tbcd.request_id=tfrd.request_id and tbcd.status=tfrd.status and tded.id=tbcd.eligibility_id))";
		  Object  eligibleAmount1= session.createSQLQuery(query).setString(0, woBean.getRequestId()).uniqueResult();
		  if(!CPSUtils.isNullOrEmpty(eligibleAmount1)){
			 int  eligibleAmount=((BigDecimal)eligibleAmount1).intValue();
		     woBean.setTeleEligibleAmt(String.valueOf(eligibleAmount));
		  }
		}catch (Exception e) {
			throw e;
		}
		return woBean;
	}
	/*//getting the dopart date added by rakesh
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getDopartDateList(WorkFlowMappingBean workflowMap) throws Exception{
		Session session =null;
		String gazType=null;
		List<String> g = null;
		List<DoPartDTO> dolist = null;
		try{
			session = hibernateUtils.getSession();
			if (workflowMap.getLtcAdvanceRequestDTO().getGazType().equals("GAZETTED")) {
				gazType = "G";
			} else {
				gazType = "NG";
			}
		
			
			dolist =session.createSQLQuery("select id as id ,to_date(ref_date ,'dd-mm-yyyy') as doPartDate  from reference_number_details where gaz_type =? and status =1 ORDER BY ID DESC").addScalar("doPartDate").addScalar("id", Hibernate.INTEGER).setString(0, gazType).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).list();
			Map map = new LinkedHashMap();
		
			if(){
				for(int i= 0; i<dolist.size();i++){
				dolist.set(i, dolist.get(i).getdOPartDate());
					}
			}
			
			workflowMap.setDopartdateList(dolist);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			
		}
	return workflowMap;
	}*/
	//getting the dopart date added by rakesh
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getDopartDateList(WorkFlowMappingBean workflowMap) throws Exception{
		Session session =null;
		String gazType=null;
		List<String> doPartDates = null;
		try{
			session = hibernateUtils.getSession();
			if(!CPSUtils.isNullOrEmpty(workflowMap.getLtcAdvanceRequestDTO())){
				gazType="";
			if (workflowMap.getLtcAdvanceRequestDTO().getGazType().equals("GAZETTED")) {
				gazType = "G";
			} else {
				gazType = "NG";
			}}
			if(!CPSUtils.isNullOrEmpty(workflowMap.getLtcApprovalRequestDTO())){
				gazType="";
				
				if (workflowMap.getLtcApprovalRequestDTO().getGazType().equals("GAZETTED")) {
					gazType = "G";
				} else {
					gazType = "NG";
				}
				
				
			}
		
			doPartDates = (List<String>) session.createCriteria(DoPartBean.class).add(Expression.eq("status", 1)).add(Expression.eq("gazettedType", gazType)).addOrder(Order.desc("id")).setProjection(Projections.property("doPartDate")).list();
			if (!CPSUtils.isNullOrEmpty(doPartDates)) {
				for(int i = 0; i < doPartDates.size(); i++) {
					doPartDates.set(i, CPSUtils.formattedDate(doPartDates.get(i)));
				}
			}
			workflowMap.setDopartdateList(doPartDates);
			
			
		}
		catch(Exception e){
			throw e;
		}
		finally{
			
		}
	return workflowMap;
	}
	
	
	
	
	
	
	@Override
	public WorkFlowMappingBean getCdaDetails(WorkFlowMappingBean woBean)throws Exception{
		Session session =null;
		try{
			session=hibernateUtils.getSession();
			String query ="select cda_amount cdaAmount,dv_no dvNumber,to_char(dv_date,'dd-mm-yyyy') dvDate from cda_details cd,finance_details fd,emp_claim_details ecd where ecd.request_id=? and ecd.id=fd.reference_id and fd.id=cd.reference_id and fd.status=1 and ecd.status=1";
			List  result= session.createSQLQuery(query).setString(0, woBean.getRequestId()).list();
		  /*if(!CPSUtils.isNullOrEmpty(cdaDetailsDTO)){
			 int  eligibleAmount=((BigDecimal)eligibleAmount1).intValue();
		     woBean.setCdaDetailsDTO(cdaDetailsDTO);
		}*/
			if(result.size()>0)
			{
			Object[] temp =(Object[])result.get(0);
			woBean.setCdaApprovedAmt(temp[0].toString());
			woBean.setDvNumber(temp[1].toString());
			woBean.setDvDate(temp[2].toString());
			}
		}catch (Exception e) {
			throw e;
		}
		return woBean;
	}
	
	@Override
	public WorkFlowMappingBean getRoleInstanceName(WorkFlowMappingBean woBean)throws Exception{
		Session session =null;
		try
		{
			session=hibernateUtils.getSession();
			String orgRoleName=(String)session.createQuery("select name from OrgInstanceDTO where id in (select roleInstanceId from EmpRoleMappingDTO where sfid=?) and name in (?,?) and status=1").setString(0, woBean.getSfid()).setString(1, "Tuition Fee and Telephone Bill task holder").setString(2, "TA DA /Medical Section Head").uniqueResult();
			woBean.setRoleInstanceName(orgRoleName);
			
		}
		catch (Exception e) {
			throw e;
		}
		return woBean;
	}
	
	@Override
	public WorkFlowMappingBean getApprovedBillNumbers(WorkFlowMappingBean woBean)throws Exception{
		Session session =null;
		try{
			session=hibernateUtils.getSession();
			String query ="select tfs.bill_or_sanction_no sanctionno,tfb.bill_or_sanction_no billno from emp_claim_details ecd,finance_details fd"+
						" LEFT OUTER JOIN tution_fee_bill_sanction tfb ON(TFB.ID=FD.BILL_NUMBER) LEFT OUTER JOIN tution_fee_bill_sanction tfs"+
						" ON(tfs.ID=fd.sanction_number) where ecd.request_id=? and ecd.id=fd.reference_id and fd.status=1 and ecd.status=1 and tfb.status=1 and tfs.status=1";
		  List  result= session.createSQLQuery(query).setString(0, woBean.getRequestId()).list();
		  if(result.size()>0)
		  {
		  Object[] temp=(Object[])result.get(0);
		  woBean.setApprovedSantionNo(temp[0].toString());
		  woBean.setApprovedBillNo(temp[1].toString());
		  }
		 
		}catch (Exception e) {
			throw e;
		}
		return woBean;
	}
	
}
