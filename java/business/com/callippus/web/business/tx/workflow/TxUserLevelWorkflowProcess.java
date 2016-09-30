package com.callippus.web.business.tx.workflow;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.UserSpecificDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;


@Service
public class TxUserLevelWorkflowProcess extends TxWorkflowProcess {
	private static Log log = LogFactory.getLog(TxUserLevelWorkflowProcess.class);
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * This method will return the auto delegate sfid
	 * 
	 * @param sfid
	 * @return
	 * @throws Exception
	 */
	public RequestBean checkUserAutoDelegate(RequestBean rb) throws Exception {
		Session session = null;
		String sfid = null;
		String parentID = null;
		try {
			session = hibernateUtils.getSession();

			// get the requester sfid & gazetted type
			if (CPSUtils.isNull(rb.getRequester())) {
				KeyValueDTO keyValueDTO = (KeyValueDTO) session.createSQLQuery(
						"select dm.type name,emp.sfid key,emp.designation_id value from emp_master emp,designation_mappings dm where emp.status=1 and "
								+ "emp.sfid=(select assigned_from from request_workflow_history where id=(select min(id) from request_workflow_history where request_id=?)) "
								+ "and emp.designation_id=dm.desig_id").addScalar("name", Hibernate.STRING).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, rb.getRequestID()).uniqueResult();
				if (CPSUtils.isNull(keyValueDTO)) {
					keyValueDTO = (KeyValueDTO) session.createSQLQuery(
							"select dm.type name,emp.sfid key,emp.designation_id value from emp_master emp,designation_mappings dm where emp.status=1 and "
									+ "emp.sfid=? and emp.designation_id=dm.desig_id").addScalar("name", Hibernate.STRING).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING)
							.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, rb.getSfID()).uniqueResult();

				}
				rb.setRequesterType(keyValueDTO.getName());
				rb.setRequester(keyValueDTO.getKey());
				rb.setDesignationID(keyValueDTO.getValue());
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
				/*rb.setRequesterType();
				rb.setDesignationID()*/
				
				/*if((Long)session.createQuery("select count(*) from ReqWorkMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1 || 
						(Long)session.createQuery("select count(*) from ReqDesigWorkflowMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1 || 
						(Long)session.createQuery("select count(*) from ReqRoleWorkMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1 || 
						(Long)session.createQuery("select count(*) from ReqOrgWorkflowMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1){
					rb.setDelegationFlag(false);
				}else{
					rb.setDelegationFlag(true);
				}*/
				//rb = checkUserNormalConfiguration(rb, CPSConstants.ONLEAVE);
			}

			if (CPSUtils.compareStrings(parentID, rb.getParentID())) {
				rb = checkUserTreeConfiguration(rb, CPSConstants.AUTOMATIC);
				// check whether the requester & auto delegated sfid is same or not, if same we should not assign the request to the same person
				if (CPSUtils.compareStrings(rb.getParentID(), rb.getRequester())) {
					// same person
					rb.setSfID(sfid);
					rb.setParentID(parentID);
					rb.setRequestCount(null);
					/*if((Long)session.createQuery("select count(*) from ReqWorkMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1 || 
							(Long)session.createQuery("select count(*) from ReqDesigWorkflowMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1 || 
							(Long)session.createQuery("select count(*) from ReqRoleWorkMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1 || 
							(Long)session.createQuery("select count(*) from ReqOrgWorkflowMappingDTO where workflowId=? and status=1 and requestTypeId=?").setInteger(0, Integer.parseInt(rb.getWorkflowID())).setInteger(1, Integer.parseInt(rb.getRequestTypeID())).uniqueResult()==1){
						rb.setDelegationFlag(false);
					}else{
						rb.setDelegationFlag(true);
					}*/
					//rb = checkUserTreeConfiguration(rb, CPSConstants.ONLEAVE);
				}
			}
			//on leave and on tdmove delegation code
			if(CPSUtils.isNullOrEmpty(rb.getRequestCount())){
				if(checkLeaveDetails(rb,parentID) || checkTdMoveDetails(rb,parentID)){
					rb.setSfID(sfid);
					rb.setParentID(parentID);
					rb.setRequestCount(null);
					rb = checkUserNormalConfiguration(rb, CPSConstants.ONLEAVE);
				}
				if(checkLeaveDetails(rb,parentID)){
					if (!CPSUtils.isNullOrEmpty(rb.getRequestCount())) {
						List<LeaveRequestBean> leaveDetailsList=getLevaeDetails(rb,parentID);
						EmployeeBean empBean=(EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", parentID.toUpperCase())).uniqueResult();
						String remarks=empBean.getNameInServiceBook()+" is on leave from "+leaveDetailsList.get(0).getStrFromDate()+" to "+leaveDetailsList.get(0).getStrToDate();
						//session.createQuery("update RequestCommonBean set remarks=? where historyID=?").setString(0, remarks).setString(1, rb.getHistoryID()).executeUpdate();
						rb.setDelegateRemarks(remarks);
					}
				}else if(checkTdMoveDetails(rb,parentID)){
					if (!CPSUtils.isNullOrEmpty(rb.getRequestCount())) {
						List<TadaApprovalRequestDTO> tdDetailsList=getTdDetails(rb, parentID);
						EmployeeBean empBean=(EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", parentID.toUpperCase())).uniqueResult();
						String remarks=empBean.getNameInServiceBook()+" is on TD from "+tdDetailsList.get(0).getDepartureDateOne()+" to "+CPSUtils.formatDate(tdDetailsList.get(0).getArrivalDate());
						rb.setDelegateRemarks(remarks);
					}
				}
				
			}
			
			log.debug("::Delegated from " + rb.getSfID() + " to " + rb.getParentID());
			if (!CPSUtils.isNullOrEmpty(rb.getRequestCount())) {
				// update the count
				UserSpecificDTO userSpecificDTO =(UserSpecificDTO)session.get(UserSpecificDTO.class,Integer.valueOf(rb.getRequestCount()));
				userSpecificDTO.setNoOfRequests(userSpecificDTO.getNoOfRequests()+1);
				session.saveOrUpdate(userSpecificDTO);
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}
	public RequestBean checkUserNormalConfiguration(RequestBean rb, String assignType) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String getDelegatedID = "select id,delegate,gazetted_type gazettedType,delegation_type delegateType,case when delegation_type=? then delegate||'' else null end delegateUser from (select usc.id,usc.delegate,usc.gazetted_type,usc.delegation_type "
					+ "from user_specific_configuration usc,status_master sm where usc.org_role_id=?   "
					+ "and usc.status=1 and sm.id=usc.assigned_type and upper(sm.status)=upper(?) and usc.request_type_id=? "
					+ "and (upper(usc.gazetted_type)=upper(?) or usc.gazetted_type is null) and (usc.designation_id=? or usc.designation_id=0) order by usc.no_of_requests) where rownum=1";

			UserSpecificDTO userSpecificDTO = (UserSpecificDTO) session.createSQLQuery(getDelegatedID).addScalar("id", Hibernate.INTEGER).addScalar("delegate", Hibernate.STRING).addScalar(
					"gazettedType", Hibernate.STRING).addScalar("delegateType", Hibernate.INTEGER).addScalar("delegateUser", Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(UserSpecificDTO.class)).setString(0, CPSConstants.STATUSINSTANCE).setString(1, rb.getRoleID()).setString(2, assignType)
					.setString(3, rb.getRequestTypeID()).setString(4, rb.getRequesterType()).setString(5, rb.getDesignationID()).uniqueResult();

			if (!CPSUtils.isNull(userSpecificDTO)) {
				if (CPSUtils.isNullOrEmpty(userSpecificDTO.getGazettedType()) || CPSUtils.compareStrings(rb.getRequesterType(), userSpecificDTO.getGazettedType())) {
					rb.setSfID(rb.getParentID());

					if (userSpecificDTO.getDelegateType() == Integer.valueOf(CPSConstants.STATUSINSTANCE)) {
						// INSTANCE
						rb.setParentID(getHead(userSpecificDTO.getDelegate()));
					} else {
						rb.setParentID(userSpecificDTO.getDelegate());
					}
					rb.setRequestCount(String.valueOf(userSpecificDTO.getId()));
					rb.setRoleID(userSpecificDTO.getDelegateUser());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	public RequestBean checkUserTreeConfiguration(RequestBean rb, String assignType) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			String getDelegatedID = "select id,delegate,gazetted_type gazettedType,delegation_type delegateType,case when delegation_type=? then delegate||'' else null end delegateUser from (select id,delegate,gazetted_type,delegation_type "
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

			UserSpecificDTO userSpecificDTO = (UserSpecificDTO) session.createSQLQuery(getDelegatedID).addScalar("id", Hibernate.INTEGER).addScalar("delegate", Hibernate.STRING).addScalar(
					"gazettedType", Hibernate.STRING).addScalar("delegateType", Hibernate.INTEGER).addScalar("delegateUser", Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(UserSpecificDTO.class)).setString(0, CPSConstants.STATUSINSTANCE).setString(1, rb.getRoleID()).setString(2, rb.getSfID()).setString(3, rb.getRoleID())
					.setString(4, assignType).setString(5, rb.getRequestTypeID()).setString(6, rb.getRequesterType()).setString(7, rb.getDesignationID()).setString(8, rb.getRoleID()).setString(9,
							rb.getSfID()).setString(10, rb.getRoleID()).setString(11, assignType).setString(12, rb.getRequestTypeID()).setString(13, rb.getRequesterType()).setString(14,
							rb.getDesignationID()).uniqueResult();

			if (!CPSUtils.isNull(userSpecificDTO)) {
				if (CPSUtils.isNullOrEmpty(userSpecificDTO.getGazettedType()) || CPSUtils.compareStrings(rb.getRequesterType(), userSpecificDTO.getGazettedType())) {
					rb.setSfID(rb.getParentID());
					if (userSpecificDTO.getDelegateType() == Integer.valueOf(CPSConstants.STATUSINSTANCE)) {
						// INSTANCE
						rb.setParentID(getHead(userSpecificDTO.getDelegate()));
					} else {
						rb.setParentID(userSpecificDTO.getDelegate());
					}
					rb.setRequestCount(String.valueOf(userSpecificDTO.getId()));
					rb.setRoleID(userSpecificDTO.getDelegateUser());
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return rb;
	}

	public String getHead(String roleInstanceID) throws Exception {
		Session session = null;
		EmpRoleMappingDTO empRoleMappingDTO = null;
		try {
			session = hibernateUtils.getSession();
			empRoleMappingDTO = (EmpRoleMappingDTO) session.createCriteria(EmpRoleMappingDTO.class).add(Expression.eq("roleInstanceId", Integer.valueOf(roleInstanceID))).add(
					Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return empRoleMappingDTO.getSfid();
	}
	public boolean checkLeaveDetails(RequestBean rb,String parentID)throws Exception{
		boolean flag=false;
		Session session=null;
		String qry=null;
		String leaveReqCount=null;
		try {
			session=hibernateUtils.getSession();
			qry="select to_char(count(*)) from LeaveRequestBean where sfID=? and ((sysdate between fromDate and toDate) or " +
					"(to_char(sysdate,'dd-Mon-yyyy') in (to_char(fromDate,'dd-Mon-yyyy'),to_char(toDate,'dd-Mon-yyyy')))) and status not in (9,6)";
			leaveReqCount=(String)session.createQuery(qry).setString(0, parentID.toUpperCase()).uniqueResult();
			if(Integer.parseInt(leaveReqCount)>0){
				flag=true;
			}
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}
	public boolean checkTdMoveDetails(RequestBean rb,String parentID)throws Exception{
		boolean flag=false;
		Session session=null;
		String qry=null;
		String tdReqCount=null;
		
		try {
			session=hibernateUtils.getSession();
			qry="select to_char(count(*)) from TadaApprovalRequestDTO where sfID=? and ((sysdate between departureDate and arrivalDate) " +
					"or (to_char(sysdate,'dd-Mon-yyyy') in (to_char(departureDate,'dd-Mon-yyyy'),to_char(arrivalDate,'dd-Mon-yyyy')))) " +
					"and status not in (9,6)";
			tdReqCount=(String)session.createQuery(qry).setString(0, parentID.toUpperCase()).uniqueResult();
			if(Integer.parseInt(tdReqCount)>0){
				flag=true;
			}
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	public List<LeaveRequestBean> getLevaeDetails(RequestBean rb,String parentID)throws Exception{
		List<LeaveRequestBean> leaveDetailsList=null;
		List<LeaveRequestBean> leaveDetailsList1=null;
		Session session=null;
		String qry=null;
		try {
			session=hibernateUtils.getSession();
			/*qry="select lrb from LeaveRequestBean lrb where sfID=? and ((sysdate between lrb.fromDate and lrb.toDate) or " +
					"(to_char(sysdate,'dd-Mon-yyyy') in (to_char(lrb.fromDate,'dd-Mon-yyyy'),to_char(lrb.toDate,'dd-Mon-yyyy')))) and lrb.status not in (9,6)";*/
			/*qry= "SELECT lrb  FROM  LeaveRequestBean lrb WHERE sfID=? AND ((sysdate BETWEEN lrb.fromDate AND lrb.toDate) OR " +
					"(TO_CHAR(sysdate,'dd-Mon-yyyy') IN (TO_CHAR(lrb.fromDate,'dd-Mon-yyyy'),TO_CHAR(lrb.toDate,'dd-Mon-yyyy')))) AND lrb.status NOT IN (9,6)";
			leaveDetailsList=session.createQuery(qry).setString(0, parentID.toUpperCase()).list();*/
			qry= "select lrb from LeaveRequestBean lrb WHERE sfID=? AND ((requestedDate BETWEEN lrb.fromDate AND lrb.toDate) OR " +
					  "(TO_CHAR(sysdate,'dd-Mon-yyyy') IN (TO_CHAR(lrb.fromDate,'dd-Mon-yyyy'),TO_CHAR(lrb.toDate,'dd-Mon-yyyy')))) AND lrb.status NOT IN (9,6)";
			leaveDetailsList=session.createQuery(qry).setString(0, parentID.toUpperCase()).list();
			/*if(leaveDetailsList!=null)
			{
				for(int i=0;i<leaveDetailsList1.size();i++)
				leaveDetailsList.add((LeaveRequestBean)leaveDetailsList1.get(0));
			}*/
		} catch (Exception e) {
			throw e;
		}
		return leaveDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getTdDetails(RequestBean rb,String parentID)throws Exception{
		List<TadaApprovalRequestDTO> tdDetailsList=null;
		Session session=null;
		String qry=null;
		try {
			session=hibernateUtils.getSession();		
			qry="select tard from TadaApprovalRequestDTO tard where sfID=? and ((sysdate between tard.departureDate and tard.arrivalDate) or " +
					"(to_char(sysdate,'dd-Mon-yyyy') in (to_char(tard.departureDate,'dd-Mon-yyyy'),to_char(tard.arrivalDate,'dd-Mon-yyyy')))) and tard.status not in (9,6)";
			tdDetailsList=session.createQuery(qry).setString(0, parentID.toUpperCase()).list();
		} catch (Exception e) {
			throw e;
		}
		return tdDetailsList;
	}
}
