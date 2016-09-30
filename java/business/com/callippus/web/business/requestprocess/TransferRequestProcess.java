package com.callippus.web.business.requestprocess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.workflowmapping.IWorkFlowMappingDAO;

@Service
public class TransferRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(PISRequestProcess.class);
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private IWorkFlowMappingDAO workflowMapDAO;
	/**
	 * This method will be called when a user or his boss requested for transfer
	 * 
	 * @param etb
	 * @return
	 */

	public String initWorkflow(EmpTransferBean etb) throws Exception {
		log.debug("::<<<<<TransferRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(EmpTransferBean etb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			etb.setRequestID(requestProcess.generateUniqueID(CPSConstants.REQUEST));
			if (CPSUtils.compareStrings(etb.getType(), CPSConstants.SELF)) {
				// Self Transfer request
				etb.setRequestType(CPSConstants.SELFTRANSFERREQUESTTYPE);
				etb.setRequestTypeID(CPSConstants.SELFTRANSFERREQUESTTYPEID);
				etb.setTransferTypeID(Integer.valueOf(etb.getTransferType()));
				etb.setTransferedSFID(etb.getSfID());
			} else {
				// Employee transfer
				etb.setRequestType(CPSConstants.EMPTRANSFERREQUESTTYPE);
				etb.setRequestTypeID(CPSConstants.EMPTRANSFERREQUESTTYPEID);
				etb.setTransferTypeID(Integer.valueOf(CPSConstants.INTERNALSTATUSID));
			}

			message = submitRequestDetails(etb);

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(etb, rb);
				rb.setSfID(etb.getTransferedSFID());
				message = requestProcess.initWorkflow(rb);

				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, etb);
					message = transferEmployee(etb);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String submitRequestDetails(EmpTransferBean etb) throws Exception {
		Session session = null;
		String message = "";
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			etb.setDepartmentFromID(Integer.valueOf(session.createSQLQuery(
					"select ori.department_id from emp_master emp,org_role_instance ori where ori.status=1 and emp.status=1 and emp.office_id=ori.org_role_id and emp.sfid=?").setString(0,
					etb.getTransferedSFID()).uniqueResult().toString()));

			// set attachment file path
			etb.setStatus(1);
			etb.setRequestedBy(etb.getSfID());
			etb.setRequestedDate(CPSUtils.getCurrentDateWithTime());
			etb.setModifiedBy(etb.getSfID());
			etb.setModifiedDate(etb.getRequestedDate());
			//tx = session.beginTransaction();
			session.saveOrUpdate(etb);
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

	/**
	 * This method will be called when a user wants to approve the request
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String approvedRequest(EmpTransferBean etb) throws Exception {
		log.debug("::<<<<<PISRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(PISRequestBean prb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(etb, rb);
			rb = requestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, etb);
				rb.setMessage(transferEmployee(etb));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	public String transferEmployee(EmpTransferBean etb) throws Exception {
		String result = null;
		Session session = null;
		String currentDate = CPSUtils.getCurrentDate();
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			EmpTransferBean reqBean = (EmpTransferBean) session.get(EmpTransferBean.class, etb.getRequestID());

			
			if(!CPSUtils.isNullOrEmpty(etb.getDoPartNo()) || !CPSUtils.isNullOrEmpty(etb.getHqRefNo())){
				//Insert DO Part details & HQ reference details
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(etb, rb);
				etb.setMessage(workflowMapDAO.submitDOPartDetails(rb));
			}
			if (reqBean.getTransferTypeID() == Integer.valueOf(CPSConstants.INTERNALSTATUSID)){
				// Internal transfer
				// Check whether emp_master office_id column & emp_transfer_details department_from(related org_role_id) column same or not, If they are equal then we should update the
				// office_id(default)
				if (CPSUtils
						.compareStrings(
								session
										.createSQLQuery(
												"select case when emp.office_id in (select org_role_id from org_role_instance where department_id=?) then 'true' else 'false' end from emp_master emp where status=1 and emp.sfid=?")
										.setInteger(0, reqBean.getDepartmentFromID()).setString(1, reqBean.getTransferedSFID()).uniqueResult().toString(), CPSConstants.TRUE)) {

					String roles = session
							.createSQLQuery(
									"select ori.org_role_id||'#'||case when ori.parent_org_role_id=0 then 1 else ori.parent_org_role_id end from org_role_instance ori where ori.status=1 and ori.department_id=? and ori.is_head=1")
							.setString(0, reqBean.getDepartmentTo()).uniqueResult().toString();

					// Update office ID
					EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, reqBean.getTransferedSFID());
					empBean.setDirectorate(Integer.valueOf(roles.split("#")[1]));
					empBean.setOffice(Integer.valueOf(roles.split("#")[0]));
					empBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					empBean.setLastModifiedBy(etb.getSfID());
					empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
					empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
					empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
					empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));
					empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
					empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
					empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
					session.saveOrUpdate(empBean);
				}

				// change the status=0 in emp_role_mapping & insert new row
				EmpRoleMappingDTO empRoleDTO = (EmpRoleMappingDTO) session
						.get(
								EmpRoleMappingDTO.class,
								Integer
										.valueOf(session
												.createSQLQuery(
														"select id from emp_role_mapping erm where erm.status=1 and erm.sfid=? and erm.parent_role_id in (select ori.org_role_id from org_role_instance ori where ori.status=1 and ori.department_id=?)")
												.setString(0, reqBean.getTransferedSFID()).setInteger(1, reqBean.getDepartmentFromID()).uniqueResult().toString()));

				empRoleDTO.setStatus(0);
				empRoleDTO.setLastModifiedDate(currentDate);
				session.saveOrUpdate(empRoleDTO);

				// Insert new Row
				String parentDetails = session
						.createSQLQuery(
								"select erm.sfid||'#'||erm.org_role_id from org_role_instance ori,emp_role_mapping erm where ori.status=1 and ori.department_id=? and ori.is_head=1 and erm.status=1 and erm.org_role_id=ori.org_role_id")
						.setString(0, reqBean.getDepartmentTo()).uniqueResult().toString();
				EmpRoleMappingDTO newEmpRoleDTO = new EmpRoleMappingDTO();
				newEmpRoleDTO.setSfid(reqBean.getTransferedSFID());
				newEmpRoleDTO.setStatus(1);
				newEmpRoleDTO.setInternalRole("Employee");
				newEmpRoleDTO.setCreationDate(currentDate);
				newEmpRoleDTO.setLastModifiedDate(currentDate);
				newEmpRoleDTO.setParentId(parentDetails.split("#")[0]);
				newEmpRoleDTO.setParentRoleID(Integer.valueOf(parentDetails.split("#")[1]));
				session.saveOrUpdate(newEmpRoleDTO);

			} else {
				// External
			}
			session.flush();//tx.commit() ;
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}
}
