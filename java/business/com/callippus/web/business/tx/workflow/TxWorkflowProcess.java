package com.callippus.web.business.tx.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DynamicWorkflowTxnDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.ReqDesigWorkflowMappingDTO;
import com.callippus.web.beans.dto.ReqOrgWorkflowMappingDTO;
import com.callippus.web.beans.dto.ReqRoleWorkMappingDTO;
import com.callippus.web.beans.dto.ReqWorkMappingDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class TxWorkflowProcess {
	private static Log log = LogFactory.getLog(TxWorkflowProcess.class);
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * This method will find the workflow assigned sfid
	 * 
	 * @param sfid
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String getAssignedID(final String sfid, final String type, final String requesterOfficeID, final String requestFrom, final String requesterRoleID, final String requestID) throws Exception {
		log
				.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getAssignedID(final String sfid, final String type,final String requesterOfficeID,final String requestFrom,final String requesterRoleID,final String requestID)>>>>>>>>>");
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
	public String getRequesterID(final String requestID) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getRequesterID(final String requestID)>>>>>>>>>");
		String requesterID = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			requesterID = (String) session.createSQLQuery(
					"select assigned_from||'#'||'' assigned_from from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?)").setString(0, requestID)
					.uniqueResult();
		} catch (Exception e) {
			throw e;
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
	public String getHeadSfid(final String sfid, final String roleID, final String requesterOfficeID, final String requestFrom, final String requesterRoleID) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getHeadSfid(String sfid, String roleID)>>>>>>>>>");
		String headID = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

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
			headID = (String) session.createSQLQuery(sb.toString()).setString(0, roleID).uniqueResult();
		} catch (Exception e) {
			throw e;
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
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getAdminID()>>>>>>>>>");
		String adminID = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			adminID = (String) session.createSQLQuery(
					"select sfid||'#'||org_role_id sfid from emp_role_mapping where org_role_id=(select value from configuration_details where upper(name)=upper(?)) and status=1").setString(0,
					CPSConstants.ADMIN).uniqueResult();
		} catch (Exception e) {
			throw e;
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
	public String getBossesBossID(final String sfid, final String requesterRoleID) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getBossesBossID(final String sfid,final String requesterRoleID)>>>>>>>>>");
		String bossesBossID = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			if (CPSUtils.isNull(requesterRoleID)) {
				/**
				 * First check whether the employee is an head or not If the employee is an head then get the upper tree start with office id (default role) and level is 3. If the employee is a normal
				 * employee then get his bosses boss with his office id(default role)
				 */
				bossesBossID = (String) session
						.createSQLQuery(
								"select case when (select count(*) from emp_master emp,emp_role_mapping erm where emp.status=1 and erm.status=1 and erm.sfid=emp.sfid and emp.sfid=? "
										+ "and emp.office_id=erm.org_role_id and erm.parent_id is null)>0 then (select sfid||'#'||org_role_id from emp_role_mapping where org_role_id=(select ori.org_role_id from org_role_instance ori where ori.status=1 and level=3 start with "
										+ "ori.org_role_id=(select emp.office_id from emp_master emp,emp_role_mapping erm where emp.status=1 and erm.status=1 and erm.sfid=emp.sfid and emp.sfid=? and emp.office_id=erm.org_role_id) connect by ori.org_role_id=prior ori.parent_org_role_id) "
										+ "and status=1) else (select erm1.sfid||'#'||erm1.org_role_id from emp_master emp,emp_role_mapping erm,org_role_instance ori,emp_role_mapping erm1 where emp.status=1 and erm.status=1 and emp.sfid=? and erm.org_role_id=emp.office_id and ori.status=1 and ori.org_role_id=erm.org_role_id "
										+ "and erm1.status=1 and erm1.org_role_id=ori.parent_org_role_id) end parentId from dual").setString(0, sfid).setString(1, sfid).setString(2, sfid)
						.uniqueResult();
			} else {
				bossesBossID = (String) session.createSQLQuery(
						"select sfid||'#'||org_role_id parentId from emp_role_mapping where org_role_id=(select ori.org_role_id from org_role_instance ori where ori.status=1 and level=3 start with ori.org_role_id=? "
								+ "connect by ori.org_role_id=prior ori.parent_org_role_id) and status=1").setString(0, requesterRoleID).uniqueResult();
			}
		} catch (Exception e) {
			throw e;
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
	@SuppressWarnings("unchecked")
	public String getBossID(final String sfid, final String requesterRoleID, final String requesterOfficeID) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getBossID(String sfid)>>>>>>>>>");
		Session session = null;
		String bossID = null;
		try {
			session = hibernateUtils.getSession();

			if (CPSUtils.isNull(requesterRoleID)) {
				// First get the requesterOfficeID tree
				List<String> list = session
						.createSQLQuery(
								"select erm.org_role_id||'' from emp_role_mapping erm where erm.status=1 and erm.org_role_id in ("
										+ "select ori.org_role_id from org_role_instance ori where ori.status = 1 start with ori.org_role_id=? connect by ori.org_role_id = prior ori.parent_org_role_id) and erm.sfid=?")
						.setString(0, requesterOfficeID).setString(1, sfid).list();

				if (CPSUtils.checkList(list)) {
					bossID = (String) session
							.createSQLQuery(
									"select erm.sfid||'#'||erm.org_role_id parentID from org_role_instance ori,emp_role_mapping erm where ori.status=1 and ori.org_role_id=? and erm.status=1 and erm.org_role_id=ori.parent_org_role_id")
							.setString(0, list.get(0).toString()).uniqueResult();

				} else {
					bossID = (String) session
							.createSQLQuery(
									"select case when (select count(*) from emp_role_mapping erm,emp_master emp where erm.sfid=? and erm.status=1 and emp.status=1 and emp.sfid=erm.sfid and erm.org_role_id=emp.office_id)>0 "
											+ "then (select sfid||'#'||org_role_id from emp_role_mapping where status=1 and org_role_id=(select ori.parent_org_role_id from emp_role_mapping erm,emp_master emp,org_role_instance ori where erm.sfid=? and erm.status=1 and emp.status=1 "
											+ "and emp.sfid=erm.sfid and erm.org_role_id=emp.office_id and ori.status=1 and ori.org_role_id=erm.org_role_id)) else (select erm.sfid||'#'||erm.org_role_id from emp_role_mapping erm where erm.status=1 and erm.org_role_id=(select emp.office_id from emp_master emp where emp.status=1 "
											+ "and emp.sfid=?)) end parentID from dual").setString(0, sfid).setString(1, sfid).setString(2, sfid).uniqueResult();
				}
			} else {
				bossID = (String) session
						.createSQLQuery(
								"select erm.sfid||'#'||erm.org_role_id parentID from org_role_instance ori,emp_role_mapping erm where ori.org_role_id=? and ori.status=1 and erm.status=1 and erm.org_role_id=ori.parent_org_role_id")
						.setString(0, requesterRoleID).uniqueResult();
			}
		} catch (Exception e) {
			throw e;
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
	public String getInstanceSFID(final String instanceID) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getInstanceSFID(String instanceID)>>>>>>>>>");
		String sfid = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			EmpRoleMappingDTO empRoleMappingDTO = (EmpRoleMappingDTO) session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq("roleInstanceId", Integer.valueOf(instanceID))).add(
					Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			if (!CPSUtils.isNull(empRoleMappingDTO)) {
				sfid = empRoleMappingDTO.getSfid() + "#" + instanceID;
			}
		} catch (Exception e) {
			throw e;
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
	public String decideWorkFlow(final String requestType, final String sfID, final String orgROleID) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>decideWorkFlow(String workFlowType, String requestType)>>>>>>>>>");
		Session session = null;
		String workflowID = null;
		try {
			session = hibernateUtils.getSession();

			if (!CPSUtils.isNull(orgROleID)) {
				// Some of the employees are having two roles, In this case from which role the employee is requesting
				ReqRoleWorkMappingDTO roleWorkDTO = (ReqRoleWorkMappingDTO) session.createCriteria(ReqRoleWorkMappingDTO.class).add(Expression.eq("roleInstanceId", Integer.valueOf(orgROleID))).add(
						Expression.eq("requestTypeId", Integer.valueOf(requestType))).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
				if (!CPSUtils.isNull(roleWorkDTO)) {
					workflowID = String.valueOf(roleWorkDTO.getWorkflowId());
				}
			} else if (CPSUtils.isNull(sfID)) {
				// generic workflow

				ReqWorkMappingDTO reqWorkMappingDTO = (ReqWorkMappingDTO) session.createCriteria(ReqWorkMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
						Expression.eq("requestTypeId", Integer.valueOf(requestType))).uniqueResult();
				if (!CPSUtils.isNull(reqWorkMappingDTO)) {
					workflowID = String.valueOf(reqWorkMappingDTO.getWorkflowId());
				}
			} else {
				// role based workflow
				workflowID = (String) session
						.createSQLQuery(
								"select workflow_id||'' from role_workflow_mapping where org_role_id=(select emp.office_id from emp_master emp,emp_role_mapping erm "
										+ "where emp.sfid=? and emp.status=1 and erm.status=1 and erm.sfid=emp.sfid  and erm.parent_id is null and erm.org_role_id=emp.office_id) and request_type_id=? and status=1")
						.setString(0, sfID).setString(1, requestType).uniqueResult();

			}

			log.debug("::::Request Type>>>>>" + requestType + "::::::Assigned Workflow ID>>>>" + workflowID);
		} catch (Exception e) {
			throw e;
		}
		return workflowID;
	}
	/**
	 * This method will decide the workflow id depends on workflow type(Designation)& request type(TADA)
	 * 
	 * @param workFlowType
	 * @param requestType
	 * @return
	 * @throws Exception
	 */
	public String decideWorkFlow(RequestBean requestBean)throws Exception{
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>decideWorkFlow(RequestBean requestBean)>>>>>>>>>");
		Session session = null;
		String workflowID = null;
		try {
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(CPSConstants.DESIG, requestBean.getWorkflowType())){
				if(!CPSUtils.isNullOrEmpty(requestBean.getDesignationID())){
					//designation based workflow
					ReqDesigWorkflowMappingDTO reqDesigWorkflowMappingDTO= (ReqDesigWorkflowMappingDTO)session.createCriteria(ReqDesigWorkflowMappingDTO.class).add(Expression.eq("status", 1)).add(
							Expression.eq("requestTypeId", Integer.parseInt(requestBean.getRequestTypeID()))).add(Expression.eq("designationId", Integer.parseInt(requestBean.getDesignationID()))).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(reqDesigWorkflowMappingDTO)){
						workflowID = String.valueOf(reqDesigWorkflowMappingDTO.getWorkflowId());
					}
				}
			}else{
				if(!CPSUtils.isNullOrEmpty(requestBean.getOrganizationID())){
					//organization specific workflow
					ReqOrgWorkflowMappingDTO reqOrgWorkflowMappingDTO = (ReqOrgWorkflowMappingDTO)session.createCriteria(ReqOrgWorkflowMappingDTO.class).add(Expression.eq("status", 1)).add(
							Expression.eq("requestTypeId", Integer.parseInt(requestBean.getRequestTypeID()))).add(Expression.eq("organizationId", Integer.parseInt(requestBean.getOrganizationID()))).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(reqOrgWorkflowMappingDTO)){
						workflowID = String.valueOf(reqOrgWorkflowMappingDTO.getWorkflowId());
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
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
	public RequestBean getWorkFlowAssignedSfid(RequestBean rb, RequestBean rb1,List<RequestBean> reqList) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<< Method>>>>>>>>>>>>>>>getWorkFlowAssignedSfid(RequestBean rb)>>>>>>>>>");
		
		log.debug("1.rb parentID   ::   "+rb.getParentID());
		log.debug("2.rb SFID   ::    "+rb.getSfID());
		log.debug("3.rb Role  ::   "+rb.getRole());
		log.debug("4.rb StageId  ::   "+rb.getStageID());
		
	
		
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();

			if (CPSUtils.isNullOrEmpty(rb1.getPrevsfID())) {
				rb1.setPrevsfID(rb.getSfID());
				rb1.setFlag(true);
			}

			if (CPSUtils.isNull(rb.getRequesterOfficeID())) {
				rb.setRequesterOfficeID(getRequesterOfficeID(rb.getRequestID(), rb.getSfID()));
			}

			WorkFlowMappingBean workFlowMappingBean = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
					Expression.eq("stageId", rb.getStageID())).uniqueResult();
                  
			if (!CPSUtils.isNull(workFlowMappingBean)) {

				log.debug("5.workFlowMappingBean.getRelation()  ::   "+workFlowMappingBean.getRelation());
				
				/**
				 * Check whether the relationship type is absolute or relative. If the relationship is absolute, then to_role column contains the instance id, so get the sfid mapped to that instance
				 * else if the relationship type is relative find the assigned sfid depends on the to_role column else if the relationship type is department find the department head role
				 */
				if (CPSUtils.compareStrings(workFlowMappingBean.getRelation(), CPSConstants.WORKFLOWRELATIONRELID)) {
					// RELATIVE
//					if(CPSUtils.compareStrings(rb.getRequestTypeID(), "46")){
//						//for TADA TD PROJECT
//						result = getAssignedID(rb,workFlowMappingBean,rb1);
//					}else{
						result = getAssignedID(rb.getSfID(), workFlowMappingBean.getTo(), rb.getRequesterOfficeID(), CPSConstants.APPROVED, rb.getOrgRoleID(), rb.getRequestID());
//					}
						
					log.debug("6.result  ::   "+result);
						
					if (!CPSUtils.isNullOrEmpty(result)) {
						rb.setParentID(result.split("#")[0]);
						rb.setRoleID(result.split("#")[1]);
					}

				} else if (CPSUtils.compareStrings(workFlowMappingBean.getRelation(), CPSConstants.WORKFLOWRELATIONABSID)) {
					// ABSOLUTE
					result = getInstanceSFID(workFlowMappingBean.getTo());
					log.debug("7.result  ::   "+result);
					log.debug("7.-1 rb1.getParentID()  ::   "+rb1.getParentID());
					if (!CPSUtils.isNullOrEmpty(result)) {
						rb.setParentID(result.split("#")[0]);
						rb.setRoleID(result.split("#")[1]);
					}
				} else if (CPSUtils.compareStrings(workFlowMappingBean.getRelation(), CPSConstants.WORKFLOWRELATIONDEPID)) {
					// DEPARTMENT
					result = getDependentDetails(workFlowMappingBean.getTo(), rb);
					log.debug("8.result  ::   "+result);
					log.debug("8.-1 rb1.getParentID()  ::   "+rb1.getParentID());
					if (!CPSUtils.isNullOrEmpty(result)) {
						rb.setParentID(result.split("#")[0]);
						rb.setRoleID(result.split("#")[1]);
					}
				} 
				else if (CPSUtils.compareStrings(workFlowMappingBean.getRelation(), CPSConstants.WORKFLOWRELATIONDYNAMICID)) {
					// DYNAMIC(for TADA)
					result = getDynamicDetails(rb);
					log.debug("9.result  ::   "+result);
					log.debug("9.-1 rb1.getParentID()  ::   "+rb1.getParentID());
					if (!CPSUtils.isNullOrEmpty(result)) {
						rb.setParentID(result.split("#")[0]);
						rb.setRoleID(result.split("#")[1]);
						//return rb;
					}
//					else{
//						return rb;
//					}
				}

				
				/**
				 * Iterate this loop till the parent ID is different and the requester & assigned employee is different.
				 * This is a recursive call checking for next stage parent. Next stage parent should be different from Requester
				 */
				log.debug("10.rb.getRequestTypeID()  ::   "+rb.getRequestTypeID());
				log.debug("10.-1 rb1.getParentID()  ::   "+rb1.getParentID());
				if(CPSUtils.compareStrings(rb.getRequestTypeID(), "46") || CPSUtils.compareStrings(rb.getRequestTypeID(), "45")){
					if(CPSUtils.isNullOrEmpty(reqList)){
						reqList=new ArrayList<RequestBean>();
					}
					RequestBean tempReqBean=new RequestBean();
					BeanUtils.copyProperties(tempReqBean, rb);
					reqList.add(tempReqBean);
					rb.setSfID(rb.getParentID());
					rb.setStageID(String.valueOf((Integer.parseInt(rb.getStageID()) + 1)));
					if(!CPSUtils.compareStrings(workFlowMappingBean.getRelation(), CPSConstants.WORKFLOWRELATIONDYNAMICID))
						rb.setRequesterOfficeID(rb.getRoleID());
					else
						rb.setRequesterOfficeID(rb.getRequesterOfficeID());
					rb = getWorkFlowAssignedSfid(rb, rb1,reqList);
				} else{
					log.debug("11.-1 rb1.getParentID()  ::   "+rb1.getParentID());
					log.debug("11.rb1.getParentID()  ::   "+rb1.getParentID());
					log.debug("12.rb.getParentID()  ::   "+rb.getParentID());
					
					if (CPSUtils.isNullOrEmpty(rb1.getParentID()) || CPSUtils.compareStrings(rb1.getParentID(), rb.getParentID())) {
//						Commented by Gattu/BKR dtd 14/Oct/16 to bypass forward checking of request already processed by same user.
// 						Ex: TADA Process: MD > Finance Mgr > MD will be allowed now. With below condition, it was not allowed.
//						|| checkRequestAssigned(rb.getRequestID(), rb.getParentID(), rb.getRoleID())) {
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
						log.debug("13.-1 rb1.getParentID()  ::   "+rb1.getParentID());
						log.debug("13.About to invoke recursive call with rb.getSfID() ::   "+rb.getSfID() +", and rb1.getSfID()" + rb1.getSfID());
						rb = getWorkFlowAssignedSfid(rb, rb1,reqList);
					} else {
						log.debug("14.-1 rb1.getParentID()  ::   "+rb1.getParentID());
						log.debug("14.Else block");
						rb.setSfID(rb1.getSfID());
						rb.setRequesterOfficeID(rb1.getRequesterOfficeID());
						rb.setParentID(rb1.getParentID());
						rb.setRoleID(rb1.getRoleID());
						rb.setStageID(String.valueOf(Integer.parseInt(rb.getStageID()) - 1));
					}
				}
			}
			if(CPSUtils.compareStrings(rb.getRequestTypeID(), "46") || CPSUtils.compareStrings(rb.getRequestTypeID(), "45")){
				String preserveSfid=null;
				boolean flag1=true;
				if(reqList.size()>0){
					preserveSfid=reqList.get(0).getSfID();
					for(int i=0;i<reqList.size();i++){
						boolean flag=true;
						for(int j=i+1;j<reqList.size();j++){
							/*if(CPSUtils.compareStrings(reqList.get(i).getSfID(), reqList.get(j).getSfID()) && 
									CPSUtils.compareStrings(reqList.get(i).getParentID(), reqList.get(j).getParentID())){
								rb.setStageID(String.valueOf(Integer.parseInt(reqList.get(i).getStageID())+1));
								rb.setSfID(reqList.get(i).getSfID());
								rb.setParentID(reqList.get(j).getParentID());
								rb.setRequestID(reqList.get(i).getRequestID());
								rb.setRequestTypeID(reqList.get(i).getRequestTypeID());
								rb.setWorkflowID(reqList.get(i).getWorkflowID());
								rb.setRoleID(reqList.get(j).getRoleID());
								flag=false;
								break;
							}else */
							if(CPSUtils.compareStrings(reqList.get(i).getParentID(), reqList.get(j).getParentID())){
								rb.setStageID(String.valueOf(Integer.parseInt(reqList.get(i).getStageID())+1));
								rb.setSfID(preserveSfid);
								rb.setParentID(reqList.get(i+1).getParentID());
								rb.setRequestID(reqList.get(i).getRequestID());
								rb.setRequestTypeID(reqList.get(i).getRequestTypeID());
								rb.setWorkflowID(reqList.get(i).getWorkflowID());
								rb.setRoleID(reqList.get(i+1).getRoleID());
								flag=false;
								flag1=false;
							}
						}
						if(flag){
							break;
						}
					}
					if(flag1){
						rb.setStageID(String.valueOf(Integer.parseInt(reqList.get(0).getStageID())));
						rb.setSfID(reqList.get(0).getSfID());
						rb.setParentID(reqList.get(0).getParentID());
						rb.setRequestID(reqList.get(0).getRequestID());
						rb.setRequestTypeID(reqList.get(0).getRequestTypeID());
						rb.setWorkflowID(reqList.get(0).getWorkflowID());
						rb.setRoleID(reqList.get(0).getRoleID());
					}
				}
			}else{
				//log.debug("15.Else block: rb1.getSfID(), rb1.getRequesterOfficeID(), rb1.getStageID() to set" + rb1.getSfID() +","+ rb1.getRequesterOfficeID() +"," + String.valueOf(Integer.parseInt(rb1.getStageID()) - 1)) ;
				if (CPSUtils.compareStrings(rb.getSfID(), rb.getParentID())) {
					rb.setSfID(rb1.getSfID());
					rb.setRequesterOfficeID(rb1.getRequesterOfficeID());
					rb.setStageID(String.valueOf(Integer.parseInt(rb1.getStageID()) - 1));
				}
			}
		} catch (Exception e) {
			throw e;
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
	@SuppressWarnings("unchecked")
	public String getDynamicDetails(RequestBean rbean) throws Exception {
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();
			if(CPSUtils.isNullOrEmpty(rbean.getDynamicWorkFlowTxnDTO())){
				DynamicWorkflowTxnDTO dynamicDTO=(DynamicWorkflowTxnDTO)session.createCriteria(DynamicWorkflowTxnDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(rbean.getRequestId()))).add(Expression.eq("requestTypeID", Integer.valueOf(rbean.getRequestTypeID()))).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(dynamicDTO)){
					result=(String)session.createSQLQuery("SELECT SFID||'#'||ROLEID FROM (SELECT EMP.SFID SFID,ORI.ORG_ROLE_ID ROLEID,CASE WHEN EMP.OFFICE_ID=ORI.ORG_ROLE_ID THEN 1 ELSE 0 END DEFROLE FROM EMP_ROLE_MAPPING ERM,EMP_MASTER EMP,ORG_ROLE_INSTANCE ORI,DESIGNATION_MASTER DM,DEPARTMENTS_MASTER DEPT WHERE erm.status=1 and emp.status=1 and emp.sfid=erm.sfid and ori.status=1 and ori.org_role_id=erm.org_role_id and dm.status=1 and dm.id=emp.designation_id and dept.status=1 and dept.department_id=ori.department_id and EMP.SFID=?) where defrole=1").setString(0, dynamicDTO.getDynamicTo()).uniqueResult();
				}
			}else{
				if(!CPSUtils.isNullOrEmpty(rbean.getDynamicWorkFlowTxnDTO())){
					result=(String)session.createSQLQuery("SELECT SFID||'#'||ROLEID FROM (SELECT EMP.SFID SFID,ORI.ORG_ROLE_ID ROLEID,CASE WHEN EMP.OFFICE_ID=ORI.ORG_ROLE_ID THEN 1 ELSE 0 END DEFROLE FROM EMP_ROLE_MAPPING ERM,EMP_MASTER EMP,ORG_ROLE_INSTANCE ORI,DESIGNATION_MASTER DM,DEPARTMENTS_MASTER DEPT WHERE erm.status=1 and emp.status=1 and emp.sfid=erm.sfid and ori.status=1 and ori.org_role_id=erm.org_role_id and dm.status=1 and dm.id=emp.designation_id and dept.status=1 and dept.department_id=ori.department_id and EMP.SFID=?) where defrole=1").setString(0, rbean.getDynamicWorkFlowTxnDTO().getDynamicTo()).uniqueResult();
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * Returns the sfid & their role ID.
	 * 
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public String getDependentDetails(final String toRole, RequestBean rb) throws Exception {
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();

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
	public String getRequesterOfficeID(final String requestID, final String sfid) throws Exception {
		Session session = null;
		String requesterOfficeID = null;
		try {
			session = hibernateUtils.getSession();
			requesterOfficeID = String.valueOf(session.createSQLQuery(
					"select case when (select count(*) from request_workflow_history where id=(select min(id) "
							+ "from request_workflow_history where request_id=?))!=0 then (select office_id from emp_master where sfid=(select assigned_from "
							+ "from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?)) and status=1) "
							+ "else (select office_id from emp_master where sfid=? and status=1) end officeID from dual").setString(0, requestID).setString(1, requestID).setString(2, sfid)
					.uniqueResult());

		} catch (Exception e) {
			throw e;
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
	public boolean checkRequestAssigned(final String requestID, final String parentID, final String roleID) throws Exception {
		Session session = null;
		boolean requestFlag = false;
		try {
			session = hibernateUtils.getSession();

			log.debug("Check request already assigned:");
			log.debug("select to_char(id) from request_workflow_history where request_id='" + requestID + "' and assigned_to='" + parentID + "' and assigned_role_id='" +roleID+ "'");
			
			String historyID = (String)session.createSQLQuery("select to_char(id) from request_workflow_history where request_id=? and assigned_to=? and assigned_role_id=?").setString(0, requestID)
					.setString(1, parentID).setString(2, roleID).uniqueResult();

			if (!CPSUtils.isNullOrEmpty(historyID)) {
				requestFlag = true;
			} else {
				// check whether the requester is same as parentID
				// If the request is from doms we can allow the request for filing
				String result = (String) session
						.createSQLQuery(
								"select case when (select count(*) from emp_role_mapping where status=1 and sfid=? and org_role_id=(select value from configuration_details where name=?))>0 "
										+ "then 'false' when (select assigned_from from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?))=? then 'true' else 'false' end res from dual")
						.setString(0, parentID).setString(1, CPSConstants.ADMIN).setString(2, requestID).setString(3, parentID).uniqueResult();

				if (CPSUtils.compareStrings(CPSConstants.TRUE, result)) {
					requestFlag = true;
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return requestFlag;
	}
	/**
	 * This method will find the workflow assigned sfid
	 * 
	 * @param sfid
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String getAssignedID(RequestBean rb,WorkFlowMappingBean workFlowMappingBean,RequestBean rb1)throws Exception{
		log
		.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getAssignedID(final String sfid, final String type,final String requesterOfficeID,final String requestFrom,final String requesterRoleID,final String requestID)>>>>>>>>>");
		String assignedID = null;
		try {
			if (CPSUtils.compareStrings(workFlowMappingBean.getTo(), CPSConstants.BOSSID)) {
				// BOSS
				assignedID = getBossID(rb.getSfID(), rb.getOrgRoleID(), rb.getRequesterOfficeID());
			} else if (CPSUtils.compareStrings(workFlowMappingBean.getTo(), CPSConstants.BOSSESBOSS)) {
				// BOSSES BOSS
				assignedID = getBossesBossID(rb.getSfID(), rb.getOrgRoleID());
			} else if (CPSUtils.compareStrings(workFlowMappingBean.getTo(), CPSConstants.ADMINID)) {
				// ADMIN
				assignedID = getAdminID();
			} else if (CPSUtils.compareStrings(workFlowMappingBean.getTo(), CPSConstants.REQUESTERID)) {
				// REQUESTER
				assignedID = getRequesterID(rb.getRequestID());
			} else {
				// 1-DIRECTOR,3-PROGRAMME DIRECTOR,5-ASSOCIATE DIRECTOR,7-PROJECT MANAGER,9-TECHNICAL DIRECTORATE,13-DIVISION HEAD
				//assignedID = getHeadSfid(rb,workFlowMappingBean,rb1);
				if(CPSUtils.compareStrings(workFlowMappingBean.getRelationShip(), CPSConstants.WORKFLOWRELATIONDYNAMICID)){
					assignedID = getDynamicDetails(rb);
				}else if(CPSUtils.compareString(workFlowMappingBean.getRelation(), CPSConstants.WORKFLOWRELATIONABSID)){
					assignedID = getInstanceSFID(workFlowMappingBean.getTo());
				}else{
					assignedID = getHeadSfid(rb,workFlowMappingBean,rb1);
					//assignedID = getHeadSfid(rb.getSfID(), workFlowMappingBean.getTo(), rb.getRequesterOfficeID(), CPSConstants.APPROVED, rb.getOrgRoleID());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::assigned ID>>>>" + assignedID);
		return assignedID;
	}
	/**
	 * This method will return the current employee boss in the tree that the boss contains particular role
	 * 
	 * @param sfid
	 * @param roleID
	 * @return
	 * @throws Exception
	 */
	public String getHeadSfid(RequestBean rb,WorkFlowMappingBean workFlowMappingBean,RequestBean rb1) throws Exception {
		log.debug("::<<<<<TxWorkflowProcess<<<<<Method>>>>>>>>>>>>>>>getHeadSfid(String sfid, String roleID)>>>>>>>>>");
		String headID = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			StringBuffer sb = new StringBuffer();
			/*sb.append("select sfid||'#'||org_role_id sfid from emp_role_mapping where org_role_id in " +
					"(select case when (select parent_org_role_id from org_role_instance where org_role_id=(select assigned_role_id from request_workflow_history where request_id=? and request_stage=(select min(request_stage) from request_workflow_history where request_id=?)))>= " +
					"(select unique parent_org_role_id from org_role_instance where role_hierarchy_id=?) " +
					"then (select org_role_id from org_role_instance where status=1 and role_hierarchy_id=? " +
					"start with org_role_id=(select assigned_role_id from request_workflow_history where request_id=? and request_stage=(select min(request_stage) from request_workflow_history where request_id=?)) connect by org_role_id = prior parent_org_role_id) " +
					"else (select ori.org_role_id from org_role_instance ori,emp_role_mapping erm " +
					"where ori.role_hierarchy_id=? and erm.org_role_id=ori.org_role_id and erm.status=1 " +
					"and ori.org_role_id=(select parent_org_role_id from org_role_instance where org_role_id=(select assigned_role_id from request_workflow_history where request_id=? and request_stage=(select min(request_stage) from request_workflow_history where request_id=?)))) end org_role_id from dual) and status=1");
			headID = (String) session.createSQLQuery(sb.toString()).setInteger(0, Integer.parseInt(rb.getRequestId())).setInteger(1, Integer.parseInt(rb.getRequestId()))
			          .setInteger(2, Integer.parseInt(workFlowMappingBean.getTo())).setInteger(3, Integer.parseInt(workFlowMappingBean.getTo())).setInteger(4, Integer.parseInt(rb.getRequestId()))
			          .setInteger(5, Integer.parseInt(rb.getRequestId())).setInteger(6, Integer.parseInt(workFlowMappingBean.getTo()))
			          .setInteger(7, Integer.parseInt(rb.getRequestId())).setInteger(8, Integer.parseInt(rb.getRequestId())).uniqueResult();*/
			
			if(CPSUtils.compareStrings(workFlowMappingBean.getRelation(), CPSConstants.WORKFLOWRELATIONDYNAMICID)){
				headID = getDynamicDetails(rb);
			}else{
				sb.append("select sfid||'#'||org_role_id sfid from emp_role_mapping where org_role_id in (select case when " +
						"(select parent_org_role_id from org_role_instance where org_role_id=" +
						"(SELECT ROLEID FROM (SELECT EMP.SFID SFID,ORI.ORG_ROLE_ID ROLEID,CASE WHEN EMP.OFFICE_ID=ORI.ORG_ROLE_ID THEN 1 ELSE 0 " +
						"END DEFROLE FROM EMP_ROLE_MAPPING ERM,EMP_MASTER EMP,ORG_ROLE_INSTANCE ORI,DESIGNATION_MASTER DM,DEPARTMENTS_MASTER DEPT WHERE " +
						"erm.status=1 and emp.status=1 and emp.sfid=erm.sfid and ori.status=1 and ori.org_role_id=erm.org_role_id and dm.status=1 " +
						"and dm.id=emp.designation_id and dept.status=1 and dept.department_id=ori.department_id and EMP.SFID=" +
						"(select rwh.assigned_from from request_workflow_history rwh where rwh.id=(select min(id) from request_workflow_history where request_id=?))) where defrole=1))>= " +
						"(select unique parent_org_role_id from org_role_instance where role_hierarchy_id=?) then (select org_role_id from org_role_instance " +
						"where status=1 and role_hierarchy_id=? start with org_role_id=(SELECT ROLEID FROM " +
						"(SELECT EMP.SFID SFID,ORI.ORG_ROLE_ID ROLEID,CASE WHEN EMP.OFFICE_ID=ORI.ORG_ROLE_ID THEN 1 ELSE 0 " +
						"END DEFROLE FROM EMP_ROLE_MAPPING ERM,EMP_MASTER EMP,ORG_ROLE_INSTANCE ORI,DESIGNATION_MASTER DM,DEPARTMENTS_MASTER DEPT WHERE " +
						"erm.status=1 and emp.status=1 and emp.sfid=erm.sfid and ori.status=1 and ori.org_role_id=erm.org_role_id and dm.status=1 " +
						"and dm.id=emp.designation_id and dept.status=1 and dept.department_id=ori.department_id and EMP.SFID=" +
						"(select rwh.assigned_from from request_workflow_history rwh where rwh.id=(select min(id) from request_workflow_history where request_id=?))) where defrole=1) connect by " +
						"org_role_id = prior parent_org_role_id) else (select ori.org_role_id from org_role_instance ori,emp_role_mapping erm " +
						"where ori.role_hierarchy_id=? and erm.org_role_id=ori.org_role_id and erm.status=1 and ori.org_role_id=" +
						"(select parent_org_role_id from org_role_instance where org_role_id=(select assigned_role_id from request_workflow_history " +
						"where request_id=? and request_stage=(select min(request_stage) from request_workflow_history where request_id=?)))) " +
						"end org_role_id from dual) and status=1");
				headID = (String) session.createSQLQuery(sb.toString()).setInteger(0, Integer.parseInt(rb.getRequestId()))
				          .setInteger(1, Integer.parseInt(workFlowMappingBean.getTo())).setInteger(2, Integer.parseInt(workFlowMappingBean.getTo())).setInteger(3, Integer.parseInt(rb.getRequestId()))
				          .setInteger(4, Integer.parseInt(workFlowMappingBean.getTo()))
				          .setInteger(5, Integer.parseInt(rb.getRequestId())).setInteger(6, Integer.parseInt(rb.getRequestId())).uniqueResult();
			}
			if(CPSUtils.isNullOrEmpty(headID)){
				StringBuffer sb1 = new StringBuffer();
				sb1.append("select sfid||'#'||org_role_id sfid from emp_role_mapping where org_role_id in " +
						"(select case when (select parent_org_role_id from org_role_instance where org_role_id=(select office_id from emp_master where sfid=?))>= " +
						"(select unique parent_org_role_id from org_role_instance where role_hierarchy_id=?) " +
						"then (select org_role_id from org_role_instance where status=1 and role_hierarchy_id=? " +
						"start with org_role_id=(select office_id from emp_master where sfid=?) connect by org_role_id = prior parent_org_role_id) " +
						"else (select ori.org_role_id from org_role_instance ori,emp_role_mapping erm " +
						"where ori.role_hierarchy_id=? and erm.org_role_id=ori.org_role_id and erm.status=1 " +
						"and ori.org_role_id=(select parent_org_role_id from org_role_instance where org_role_id=(select office_id from emp_master where sfid=?))) end org_role_id from dual) and status=1");
				headID = (String) session.createSQLQuery(sb1.toString()).setString(0, rb1.getPrevsfID())
				          .setInteger(1, Integer.parseInt(workFlowMappingBean.getTo())).setInteger(2, Integer.parseInt(workFlowMappingBean.getTo()))
				          .setString(3, rb1.getPrevsfID()).setInteger(4, Integer.parseInt(workFlowMappingBean.getTo()))
				          .setString(5, rb1.getPrevsfID()).uniqueResult();
			}
		} catch (Exception e) {
			throw e;
		}
		return headID;
	}
	public int getPrevNextWorkFlowStage(WorkFlowMappingBean workFlowMappingBean,RequestBean rb,RequestBean rb1)throws Exception{
		int flag=0;
		WorkFlowMappingBean workBean1=null;
		WorkFlowMappingBean workBean2=null;
		WorkFlowMappingBean workBean3=null;
		WorkFlowMappingBean workBean4=null;
		String assidnedID1=null;
		String assidnedID2=null;
		String assidnedID3=null;
		String assidnedID4=null;
		Session session = null;
		RequestBean tempRequestBean=new RequestBean();
		RequestBean temRequestBean1=new RequestBean();
		boolean flag1=true;
		try {
			session = hibernateUtils.getSession();
			BeanUtils.copyProperties(tempRequestBean, rb);
			BeanUtils.copyProperties(temRequestBean1, rb1);
			if(CPSUtils.compareStrings(rb1.getPrevsfID(), rb1.getParentID())){
				EmployeeBean empBean=(EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", rb1.getPrevsfID())).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(empBean)){
					tempRequestBean.setSfID(empBean.getUserSfid());
					tempRequestBean.setRequesterOfficeID(empBean.getOfficeId());
				}
			}else{
				tempRequestBean.setSfID(rb1.getPrevsfID());
				tempRequestBean.setRoleID(rb1.getRoleID());
			}
			if(CPSUtils.compareString(rb.getRequestType(), CPSConstants.TADATDBUILDUP)){
				workBean1 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())-2))).uniqueResult();
				
				workBean2 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())-1))).uniqueResult();
				workBean3 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())))).uniqueResult();
				workBean4 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())+1))).uniqueResult();
			}else{
				workBean1 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())-1))).uniqueResult();
				
				workBean2 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())))).uniqueResult();
				workBean3 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())+1))).uniqueResult();
				workBean4 = (WorkFlowMappingBean) session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", rb.getWorkflowID())).add(
						Expression.eq("stageId", String.valueOf(Integer.parseInt(tempRequestBean.getStageID())+2))).uniqueResult();
			}
			if(!CPSUtils.isNullOrEmpty(workBean1)){
				assidnedID1 = getAssignedID(tempRequestBean,workBean1,temRequestBean1);
			}
			if(!CPSUtils.isNullOrEmpty(workBean2)){
				assidnedID2 = getAssignedID(tempRequestBean,workBean2,temRequestBean1);
			}
			if(!CPSUtils.isNullOrEmpty(workBean3)){
				assidnedID3 = getAssignedID(tempRequestBean,workBean3,temRequestBean1);
			}
			if(!CPSUtils.isNullOrEmpty(workBean4)){
				assidnedID4 = getAssignedID(tempRequestBean,workBean4,temRequestBean1);
			}
			if(!CPSUtils.isNullOrEmpty(assidnedID1)){
				if(!CPSUtils.isNullOrEmpty(assidnedID2)){
					if(CPSUtils.compareStrings(assidnedID1.split("#")[0], assidnedID2.split("#")[0])){
						flag =1;
					}else if(!CPSUtils.isNullOrEmpty(assidnedID3)){
						if(CPSUtils.compareStrings(assidnedID1.split("#")[0], assidnedID3.split("#")[0])){
							flag =1;
						}else if(!CPSUtils.isNullOrEmpty(assidnedID4)){
							if(CPSUtils.compareStrings(assidnedID1.split("#")[0], assidnedID4.split("#")[0])){
								flag =1;
							}else{
								flag1=false;
							}
						}else{
							flag1=false;
						}
					}else if(!CPSUtils.isNullOrEmpty(assidnedID4)){
						if(CPSUtils.compareStrings(assidnedID1.split("#")[0], assidnedID4.split("#")[0])){
							flag =1;
						}else{
							flag1=false;
						}
					}else{
						flag1=false;
					}
				}else if(!CPSUtils.isNullOrEmpty(assidnedID3)){
					if(CPSUtils.compareStrings(assidnedID1.split("#")[0], assidnedID3.split("#")[0])){
						flag =1;
					}else if(!CPSUtils.isNullOrEmpty(assidnedID4)){
						if(CPSUtils.compareStrings(assidnedID1.split("#")[0], assidnedID4.split("#")[0])){
							flag =1;
						}else{
							flag1=false;
						}
					}else{
						flag1=false;
					}
				}else if(!CPSUtils.isNullOrEmpty(assidnedID4)){
					if(CPSUtils.compareStrings(assidnedID1.split("#")[0], assidnedID4.split("#")[0])){
						flag =1;
					}else{
						flag1=false;
					}
				}else{
					flag1=false;
				}
			}
			if(flag1){
				if(!CPSUtils.isNullOrEmpty(assidnedID2)){
					if(!CPSUtils.isNullOrEmpty(assidnedID3)){
						if(CPSUtils.compareStrings(assidnedID2.split("#")[0], assidnedID3.split("#")[0])){
							flag=2;
						}else if(!CPSUtils.isNullOrEmpty(assidnedID4)){
							if(CPSUtils.compareStrings(assidnedID2.split("#")[0], assidnedID4.split("#")[0])){
								flag=2;
							}else{
								flag1=false;
							}
						}else{
							flag1=false;
						}
					}else if(!CPSUtils.isNullOrEmpty(assidnedID4)){
						if(CPSUtils.compareStrings(assidnedID2.split("#")[0], assidnedID4.split("#")[0])){
							flag=2;
						}else{
							flag1=false;
						}
					}else{
						flag1=false;
					}
				}
			}
			if(flag1){
				if(!CPSUtils.isNullOrEmpty(assidnedID3)){
					if(!CPSUtils.isNullOrEmpty(assidnedID4)){
						if(CPSUtils.compareStrings(assidnedID3.split("#")[0], assidnedID4.split("#")[0])){
							flag=3;
						}else{
							flag1=false;
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}
}

