package com.callippus.web.dao.transfer;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.beans.transfer.dto.EmpTransferTxnDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

@SuppressWarnings("serial")
@Service
public class SQLEmpTransferDAO implements IEmpTransferDAO, Serializable {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getEmployees(EmpTransferBean transferBean) throws Exception {
		Session session = null;
		List<KeyValueDTO> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			list = session
					.createSQLQuery(
							"select emp.sfid key,emp.name_in_service_book value from emp_master emp,org_role_instance ori where emp.status=1 and emp.office_id=ori.org_role_id and ori.department_id=? and emp.sfid!=(select erm1.sfid from emp_role_mapping erm1,org_role_instance ori1 " +
							"where erm1.status=1 and ori1.status=1 and ori1.department_id=? and ori1.is_head=1 and ori1.org_role_id=erm1.org_role_id)")
					.addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, transferBean.getDepartmentID()).setString(1, transferBean.getDepartmentID()).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;

	}

	@Override
	public String manageTransTxnDetails(EmpTransferBean transferBean) throws Exception {
		Session session = null;
		String message = "";
		Transaction tx = null;
		EmpTransferTxnDTO empTransferTxnDTO=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			empTransferTxnDTO=(EmpTransferTxnDTO)session.createCriteria(EmpTransferTxnDTO.class).add(Expression.eq("sfID",transferBean.getTransferedSFID())).uniqueResult();
			if(CPSUtils.isNull(empTransferTxnDTO)){
				empTransferTxnDTO=new EmpTransferTxnDTO();
				empTransferTxnDTO.setCreatedBy(transferBean.getSfID());
				empTransferTxnDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
				message=CPSConstants.SUCCESS;
			}else if(!CPSUtils.isNull(empTransferTxnDTO)){
				message=CPSConstants.UPDATE;
			}
			empTransferTxnDTO.setSfID(transferBean.getTransferedSFID());
			empTransferTxnDTO.setStatus(1);
			empTransferTxnDTO.setFromDept(transferBean.getFromDept());
			empTransferTxnDTO.setToDept(transferBean.getToDept());
			empTransferTxnDTO.setAssignedTo(transferBean.getAssignedTo());
			empTransferTxnDTO.setDoPartId(Integer.parseInt(transferBean.getDoPartNo()));
			//tx=session.beginTransaction();
			session.saveOrUpdate(empTransferTxnDTO);
			if (CPSUtils.compareStrings(session.createSQLQuery("select case when emp.office_id in (select org_role_id from org_role_instance where department_id=?) then 'true' else 'false' end from emp_master emp where status=1 and emp.sfid=?").setString(0,transferBean.getFromDept()).setString(1,transferBean.getTransferedSFID()).uniqueResult().toString(), CPSConstants.TRUE)) {

					String roles = session.createSQLQuery("select ori.org_role_id||'#'||case when ori.parent_org_role_id=0 then 1 else ori.parent_org_role_id end from org_role_instance ori where ori.status=1 and ori.department_id=? and ori.is_head=1").setString(0,transferBean.getToDept()).uniqueResult().toString();
					EmployeeBean empBean = (EmployeeBean) session.get(EmployeeBean.class, transferBean.getTransferedSFID());
					empBean.setDirectorate(Integer.valueOf(roles.split("#")[1]));
					empBean.setOffice(Integer.valueOf(roles.split("#")[0]));
					empBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					empBean.setLastModifiedBy(transferBean.getSfID());
					empBean.setDob(CPSUtils.formattedDate(empBean.getDob()));
					empBean.setDojAsl(CPSUtils.formattedDate(empBean.getDojAsl()));
					empBean.setDojDrdo(CPSUtils.formattedDate(empBean.getDojDrdo()));
					empBean.setDojGovt(CPSUtils.formattedDate(empBean.getDojGovt()));				
					empBean.setSeniorityDate(CPSUtils.formattedDate(empBean.getSeniorityDate()));
					empBean.setLastPromotion(CPSUtils.formattedDate(empBean.getLastPromotion()));
					empBean.setCreationDate(CPSUtils.formattedDate(empBean.getCreationDate()));
					session.saveOrUpdate(empBean);
				EmpRoleMappingDTO empRoleDTO = (EmpRoleMappingDTO) session.get(EmpRoleMappingDTO.class,Integer.valueOf(session.createSQLQuery("select id from emp_role_mapping erm where erm.status=1 and erm.sfid=? and erm.parent_role_id in (select ori.org_role_id from org_role_instance ori where ori.status=1 and ori.department_id=?)").setString(0, transferBean.getTransferedSFID()).setString(1, transferBean.getFromDept()).uniqueResult().toString()));

				empRoleDTO.setStatus(0);
				empRoleDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(empRoleDTO);

				String parentDetails = session.createSQLQuery("select erm.sfid||'#'||erm.org_role_id from org_role_instance ori,emp_role_mapping erm where ori.status=1 and ori.department_id=? and ori.is_head=1 and erm.status=1 and erm.org_role_id=ori.org_role_id").setString(0, transferBean.getToDept()).uniqueResult().toString();
				EmpRoleMappingDTO newEmpRoleDTO = new EmpRoleMappingDTO();
				newEmpRoleDTO.setSfid(transferBean.getTransferedSFID());
				newEmpRoleDTO.setStatus(1);
				newEmpRoleDTO.setInternalRole("Employee");
				newEmpRoleDTO.setCreationDate(CPSUtils.getCurrentDate());
				newEmpRoleDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				newEmpRoleDTO.setParentId(parentDetails.split("#")[0]);
				newEmpRoleDTO.setParentRoleID(Integer.valueOf(parentDetails.split("#")[1]));
				session.saveOrUpdate(newEmpRoleDTO);
				}
				session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@Override
	public String deleteTransTxnDetails(int doPartID) throws Exception {
		Session session = null;
		String message = "";
		Transaction tx = null;
		EmpTransferTxnDTO empTransferTxnDTO=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx=session.beginTransaction();
			empTransferTxnDTO=(EmpTransferTxnDTO)session.createCriteria(EmpTransferTxnDTO.class).add(Expression.eq("id",doPartID)).uniqueResult();
			empTransferTxnDTO.setStatus(0);
			session.saveOrUpdate(empTransferTxnDTO);
			message=CPSConstants.DELETE;
			session.flush();//tx.commit() ;
		}catch(Exception e){
			//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentsDTO> getDepartments(String transferedSFID) throws Exception {
		Session session = null;
		Transaction tx = null;
		List<DepartmentsDTO> departmentsList=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx=session.beginTransaction();
			departmentsList=session.createSQLQuery("select department_id id,department_name deptName from departments_master where department_id in( select department_id from org_role_instance where org_role_id in( select PARENT_ROLE_ID from emp_role_mapping where sfid=? and status=1) and status=1) and status=1").addScalar("id",Hibernate.INTEGER).addScalar("deptName",Hibernate.STRING).setString(0,transferedSFID).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).list();
			session.flush();//tx.commit() ;
		}catch(Exception e){
			//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return departmentsList;
	}

}