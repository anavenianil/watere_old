package com.callippus.web.dao.empPayment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.CppCodeGenerator;

import com.callippus.web.beans.EmpPayment.EmployeePaymentBean;
import com.callippus.web.beans.dto.EmpBasicPayHistoryDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.KeyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.beans.management.PromotionOfflineEntryBean;
import com.callippus.web.promotions.dto.EmpGradePayHistoryDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;

@Service
public class SQLEmployeePaymentDAO implements IEmployeePaymentDAO, Serializable {
	private static final long serialVersionUID = 7048899710717627065L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
/*	public String insertEmpPaymentDetails(EmployeePaymentBean empPaymentBean) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		EmpBasicPayHistoryDTO basicPayHistoryDTO=null;
		PromotionOfflineEntryBean entryBean=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			
			entryBean=(PromotionOfflineEntryBean)session.createSQLQuery("SELECT EMP.DESIGNATION_ID designationID,EMP.SENIORITY_DATE seniorityDate,nvl(PAY.BASIC_PAY,0) basicPay,nvl(pay.GRADE_PAY,0) gradePay FROM EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY where EMP.SFID=PAY.SFID(+) AND EMP.STATUS=1 and EMP.sfid=?")
			.addScalar("designationID", Hibernate.INTEGER).addScalar("seniorityDate", Hibernate.DATE).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).setString(0,empPaymentBean.getSfid()).setResultTransformer(Transformers.aliasToBean(PromotionOfflineEntryBean.class)).uniqueResult();
		
			basicPayHistoryDTO=new EmpBasicPayHistoryDTO();
			basicPayHistoryDTO.setSfid(empPaymentBean.getSfid());
			basicPayHistoryDTO.setBasicPay(Float.parseFloat(empPaymentBean.getBasic()));
			basicPayHistoryDTO.setIncrementType(empPaymentBean.getIncrementType());
			basicPayHistoryDTO.setIncrementValue(empPaymentBean.getIncrementValue());
			basicPayHistoryDTO.setDesignationId(empPaymentBean.getDesignationId());
			basicPayHistoryDTO.setStatus(1);
			basicPayHistoryDTO.setPresentEffectiveDate(CPSUtils.convertStringToDate(empPaymentBean.getEffectiveDate()));
			basicPayHistoryDTO.setReferenceType("online");
			
			basicPayHistoryDTO.setCreationDate(CPSUtils.getCurrentDate());
			basicPayHistoryDTO.setCreatedBy(empPaymentBean.getSfid());
			basicPayHistoryDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			basicPayHistoryDTO.setLastModifiedBy(empPaymentBean.getSfid());
			//tx = session.beginTransaction();
				session.saveOrUpdate(basicPayHistoryDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	*/
	public String insertEmpPaymentDetails(EmployeePaymentBean empPaymentBean) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		EmpBasicPayHistoryDTO basicPayHistoryDTO=null;
		PromotionOfflineEntryBean entryBean=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			
			/*entryBean=(PromotionOfflineEntryBean)session.createSQLQuery("SELECT EMP.DESIGNATION_ID designationID,EMP.SENIORITY_DATE seniorityDate,nvl(PAY.BASIC_PAY,0) basicPay,nvl(pay.GRADE_PAY,0) gradePay FROM EMP_MASTER EMP,EMP_PAYMENT_DETAILS PAY where EMP.SFID=PAY.SFID(+) AND EMP.STATUS=1 and EMP.sfid=?")
			.addScalar("designationID", Hibernate.INTEGER).addScalar("seniorityDate", Hibernate.DATE).addScalar("basicPay", Hibernate.FLOAT).addScalar("gradePay", Hibernate.FLOAT).setString(0,empPaymentBean.getSfid()).setResultTransformer(Transformers.aliasToBean(PromotionOfflineEntryBean.class)).uniqueResult();*/
		if(!CPSUtils.isNullOrEmpty(empPaymentBean.getId()==0)){
			EmpBasicPayHistoryDTO basicPayHistoryDTO1=(EmpBasicPayHistoryDTO)session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq("sfid", empPaymentBean.getSfid())).add(Expression.eq("status", 1)).uniqueResult();
			if(basicPayHistoryDTO1!=null){
				basicPayHistoryDTO1.setStatus(100);
				basicPayHistoryDTO1.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(basicPayHistoryDTO1.getPresentEffectiveDate().toString())));
				basicPayHistoryDTO1.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.update(basicPayHistoryDTO1);
				session.flush();
			}
			
		}
			basicPayHistoryDTO=new EmpBasicPayHistoryDTO();
			basicPayHistoryDTO.setSfid(empPaymentBean.getSfid());
			basicPayHistoryDTO.setBasicPay(Float.parseFloat(empPaymentBean.getBasic()));
			basicPayHistoryDTO.setIncrementType(empPaymentBean.getIncrementType());
			basicPayHistoryDTO.setIncrementValue(empPaymentBean.getIncrementValue());
			basicPayHistoryDTO.setDesignationId(empPaymentBean.getDesignationId());
			basicPayHistoryDTO.setStatus(1);
			basicPayHistoryDTO.setPresentEffectiveDate(CPSUtils.convertStringToDate(empPaymentBean.getEffectiveDate()));
			basicPayHistoryDTO.setReferenceType("online");
			
			basicPayHistoryDTO.setCreationDate(CPSUtils.getCurrentDate());
			basicPayHistoryDTO.setCreatedBy(empPaymentBean.getSfid());
			basicPayHistoryDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			basicPayHistoryDTO.setLastModifiedBy(empPaymentBean.getSfid());
			//tx = session.beginTransaction();
				session.saveOrUpdate(basicPayHistoryDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public String updateBasicPayHistory(EmployeePaymentBean empPaymentBean) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		EmpBasicPayHistoryDTO ebDTO = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		//	ebDTO = (EmpBasicPayHistoryDTO)session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq("id", empPaymentBean.getId())).uniqueResult();
			ebDTO = (EmpBasicPayHistoryDTO)session.get(EmpBasicPayHistoryDTO.class, empPaymentBean.getId());
			
			ebDTO.setBasicPay(Float.parseFloat(empPaymentBean.getBasic()));
			ebDTO.setPresentEffectiveDate(CPSUtils.convertStringToDate(empPaymentBean.getEffectiveDate()));
			ebDTO.setDesignationId(empPaymentBean.getDesignationId());
			ebDTO.setIncrementType(empPaymentBean.getIncrementType());
			ebDTO.setIncrementValue(empPaymentBean.getIncrementValue());
			
			ebDTO.setSfid(empPaymentBean.getSfid());
			ebDTO.setStatus(empPaymentBean.getStatus());
			ebDTO.setReferenceType(empPaymentBean.getReferenceType());
			ebDTO.setCreationDate(CPSUtils.formattedDate(ebDTO.getCreationDate()));
			ebDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			ebDTO.setLastModifiedBy(empPaymentBean.getSfid());
			//tx = session.beginTransaction();
			session.update(ebDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
	} catch (Exception e) {
		message = CPSConstants.FAILED;
		throw e;
	} finally {
		//session.close();
	}
	return message;
		
	}

	@SuppressWarnings("unchecked")
	public List<EmpPaymentsDTO> getEmpPaymentDetails() throws Exception {
		Session session = null;
		List<EmpPaymentsDTO> list=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		list= session.createSQLQuery("SELECT emp.SFID sfid,EMP.NAME_IN_SERVICE_BOOK name,DESIG.NAME desigName,PAY.BASIC_PAY basicPay,PAY.GRADE_PAY gradePay FROM EMP_PAYMENT_DETAILS PAY,EMP_MASTER EMP,DESIGNATION_MASTER DESIG WHERE EMP.SFID=PAY.SFID(+) AND EMP.DESIGNATION_ID=DESIG.ID AND EMP.STATUS=1 order by PAY.BASIC_PAY desc,DESIG.ORDER_NO").addScalar(CPSConstants.SFID,Hibernate.STRING).addScalar("name",Hibernate.STRING).addScalar("desigName",Hibernate.STRING).addScalar("basicPay",Hibernate.STRING).addScalar("gradePay",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmpPaymentsDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		finally {
			//session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<EmployeeBean> validateSFID(String SFID) throws Exception{
		Session session = null;
		List<EmployeeBean> empList = null;
		try{
			session = hibernateUtils.getSession();
			String qry = "select sfid as key,name_in_service_book as name,  " +
					" (select dm.name from emp_master emp,designation_master dm  where emp.status=1 and dm.status=1 " +
					" and emp.designation_id=dm.id and emp.sfid='"+SFID+"') as flag, rtrim((select distinct dept.department_name " +
					" from emp_master emp,departments_master dept,org_role_instance ori, emp_role_mapping erm  where emp.status=1 " +
					" and dept.status=1 and ori.status=1 and erm.status=1 and erm.sfid=emp.sfid  " +
					" and emp.office_id=ori.org_role_id   and ori.department_id=dept.department_id and emp.sfid='"+SFID+"') " +
					" ||','|| " +
					" (select XMLAGG(XMLELEMENT(\"e\", dept.department_name||',').extract('//text()')) from emp_master emp,departments_master dept,org_role_instance ori,emp_role_mapping erm" +
					"  where emp.status=1 and dept.status=1 and ori.status=1 and erm.status=1 and erm.sfid=emp.sfid " +
					"  and erm.org_role_id=ori.org_role_id  and emp.office_id !=ori.org_role_id  and ori.department_id=dept.department_id " +
					" and emp.sfid='"+SFID+"' ),',') as value from emp_master where status=1 and sfid='"+SFID+"'";
			
			/*String qry = "select sfid as key,name_in_service_book as name,  " +
					" (select dm.name from emp_master emp,designation_master dm  where emp.status=1 and dm.status=1 " +
					" and emp.designation_id=dm.id and emp.sfid='"+SFID+"') as flag, " +
					" (select distinct dept.department_name  from emp_master emp,departments_master dept,org_role_instance ori  " +
					" where emp.status=1  and dept.status=1 and ori.status=1 and emp.office_id=ori.org_role_id   " +
					" and ori.department_id=dept.department_id and emp.sfid='"+SFID+"')as value  from  emp_master where status=1 " +
					" and sfid='"+SFID+"'";*/
			empList = session.createSQLQuery(qry).addScalar("key",Hibernate.STRING).addScalar("name",Hibernate.STRING).addScalar("flag",Hibernate.STRING).addScalar("value",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return empList;
	}
	
	public EmployeePaymentBean getDetails(EmployeePaymentBean  employeePaymentBean) throws Exception
	{
		Session session = null;
		int worklocation;
		int role = 0;
		try
		{
			session = hibernateUtils.getSession();
			
			worklocation = ((BigDecimal)session.createSQLQuery("select working_location from emp_master  where sfid = '"+employeePaymentBean.getSfid()+"'").uniqueResult()).intValue();
			Object obj = session.createSQLQuery("select distinct role_id  from application_role_mapping where role_id = 1 and status = 1 and sfid = '"+employeePaymentBean.getSfID()+"'").uniqueResult();
				if(!CPSUtils.isNull(obj))
				{
					role = ((BigDecimal)obj).intValue();
				}
			
			employeePaymentBean.setWorklocation(worklocation);
			employeePaymentBean.setRole(role);
		}catch(Exception e)
		{
			throw e;
		}
		return employeePaymentBean;
	}
	
	public int getWorkLocation(String SFID) throws Exception
	{
		Session session = null;
		int result = 0;
			try
			{
				session = hibernateUtils.getSession();
				Object res = session.createSQLQuery("select working_location from emp_master where sfid = '"+SFID+"'").uniqueResult();
					if(!CPSUtils.isNullOrEmpty(res))
					result=((BigDecimal)res).intValue();
			}catch(Exception e)
			{
				throw e;
			}
		return result;
	}
	
	public String checkDuplicateHistory(EmployeePaymentBean empPaymentBean) throws Exception {
		String message = "";
		Session session = null;
		List existList = null;
		try{
			session = hibernateUtils.getSession();
			String sql = "select id from emp_basic_pay_history where status=1 and sfid='"+empPaymentBean.getSfid()+"' and inc_type='"+empPaymentBean.getIncrementType()+"' " +
					     " and designation_id="+empPaymentBean.getDesignationId()+" and to_char(effective_date,'dd-Mon-YYYY')='"+empPaymentBean.getEffectiveDate()+"'";
			existList = session.createSQLQuery(sql).list();
			if(existList.size()>0){
				message = CPSConstants.DUPLICATE;
			}else{
				message = CPSConstants.SUCCESS;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	//Employee Basic Pay From EMP_BASIC_PAY_HISTORY//
	@SuppressWarnings("unchecked")
	public List<EmpBasicPayHistoryDTO> getEmpBasicPayHistory(String sfid) throws Exception{
		Session session = null;
		List<EmpBasicPayHistoryDTO> list=null;
		String sql="";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sql="select ebph.id,ebph.sfid,ebph.basic_pay basicPay,ebph.inc_type incrementType,ebph.inc_value incrementValue,ebph.status,ebph.reference_type referenceType,ebph.effective_date presentEffectiveDate,dm.name desigName,em.name_in_service_book name,ebph.designation_id designationId from emp_master em,emp_basic_pay_history ebph,designation_master dm where em.sfid=ebph.sfid and ebph.designation_id=dm.id and ebph.sfid=? and ebph.status=1 order by ebph.Effective_Date desc";
			
			list= session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("sfid").addScalar("basicPay", Hibernate.FLOAT).addScalar("incrementType")
			.addScalar("incrementValue", Hibernate.FLOAT).addScalar("status", Hibernate.INTEGER).addScalar("referenceType").addScalar("presentEffectiveDate", Hibernate.DATE).addScalar("desigName").addScalar("name").addScalar("designationId",Hibernate.INTEGER).setString(0,sfid).setResultTransformer(Transformers.aliasToBean(EmpBasicPayHistoryDTO.class)).list();
			
		} catch (Exception e) {
			throw e;
		}
		finally {
			//session.close();
		}
		return list;
	}
	
	//Employee Grade Pay From 
	@Override
	@SuppressWarnings("unchecked")
	public List<EmpGradePayHistoryDTO> getOfflineEntryList(String sfid) throws Exception {
		Session session = null;
		List<EmpGradePayHistoryDTO> list=null;
		String sql = "";
		try {
			session = hibernateUtils.getSession();
			
			sql = "select egph.id id,egph.sfid sfID,egph.variable_increments varIncPt,egph.var_inc_effective_date presentEffectiveDate,egph.two_addl_inc twoAddl,egph.var_inc_end_date varIncEndDate,"
				+ "egph.promoted_designation promotedDesignation,egph.promoted_effective_date promotedEffectiveDate,egph.grade_pay newGradePay,egph.reference_type referenceType,to_char(egph.seniority_date,'dd-Mon-YYYY') seniorityDate,"
				+ "nvl((select ebph.basic_pay from emp_basic_pay_history ebph where ebph.sfid=? and ebph.effective_date=(select max(effective_date) from emp_basic_pay_history ebp where ebp.designation_id=egph.promoted_designation and ebp.sfid=? and ebp.status=1)),'0') pay,"
				+ "dm.name name,emp.NAME_IN_SERVICE_BOOK empName from emp_grade_pay_history egph,designation_master dm,emp_master emp where egph.promoted_designation=dm.id and egph.sfid=? and egph.sfid=emp.sfid and emp.status=1 and egph.status=1";

		
  list=session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("sfID", Hibernate.STRING).addScalar("empName", Hibernate.STRING).addScalar("name").addScalar("pay", Hibernate.FLOAT)
			.addScalar("varIncPt", Hibernate.INTEGER)
				.addScalar("promotedDesignation", Hibernate.INTEGER)
				.addScalar("presentEffectiveDate", Hibernate.DATE)
				.addScalar("promotedEffectiveDate", Hibernate.DATE)
				.addScalar("newGradePay", Hibernate.FLOAT)
				.addScalar("referenceType", Hibernate.STRING)
				.addScalar("twoAddl", Hibernate.STRING)
				.addScalar("seniorityDate", Hibernate.STRING)
				.addScalar("varIncEndDate", Hibernate.DATE).setString(0,sfid).setResultTransformer(Transformers.aliasToBean(EmpGradePayHistoryDTO.class)).list();
			
			
			
			
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return list;
}
	
	@SuppressWarnings("unchecked")
	public List<PromotionOfflineEntryBean> getCatwiseDesig(String sfid) throws Exception {
		List<PromotionOfflineEntryBean> list = null;
		Session session = null;
		try {
			String query = "SELECT CM1.ID id,DESIG.ID designationID,DESIG.NAME desigName FROM CATEGORY_MASTER CM1,DESIGNATION_MASTER DESIG,DESIGNATION_MAPPINGS DMAP1 WHERE CM1.ID=(SELECT CM.ID FROM CATEGORY_MASTER CM,DESIGNATION_MASTER DM,DESIGNATION_MAPPINGS DMAP,EMP_MASTER EMP WHERE DMAP.DESIG_ID=DM.ID AND CM.ID=DMAP.CATEGORY_ID AND CM.STATUS=1 and DM.STATUS=1 AND EMP.DESIGNATION_ID=dm.id and emp.sfid=?) AND DMAP1.CATEGORY_ID=CM1.ID and DMAP1.DESIG_ID=desig.id and DESIG.STATUS=1 and CM1.STATUS=1";
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list = session.createSQLQuery(query).addScalar("id",Hibernate.INTEGER).addScalar("designationID",Hibernate.INTEGER).addScalar("desigName",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PromotionOfflineEntryBean.class)).setString(0, sfid).list();
		} catch (Exception e) {
			throw e;
		} 
		finally {
			//session.close();
		}
		return list;
	}

	public String deleteRecord(EmployeePaymentBean empPaymentBean)throws Exception{
		String message="";
		Session session=null;
		try {
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			Query qry=session.createQuery("update EmpBasicPayHistoryDTO set status=? where id=?");
			qry.setInteger(0,0);
			qry.setInteger(1, empPaymentBean.getId());
			qry.executeUpdate();
			message=CPSConstants.DELETE;
		} catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List<PayScaleDesignationDTO> getPayScaleDetailsList()throws Exception {
		List<PayScaleDesignationDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = session.createCriteria(PayScaleDesignationDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).list();
			
			for(int i=0; i<keyValueList.size(); i++){
				PayScaleDesignationDTO pdtoDesignationDTO = keyValueList.get(i);
				pdtoDesignationDTO.setId(pdtoDesignationDTO.getDesignationDetails().getId());
				pdtoDesignationDTO.setName(pdtoDesignationDTO.getPaybandDetails().getName() + "-" + pdtoDesignationDTO.getPaybandDetails().getRangeFrom() + "-"
								+ pdtoDesignationDTO.getPaybandDetails().getRangeTo());
				pdtoDesignationDTO.setGradePay(pdtoDesignationDTO.getGradePay());
				session.evict(pdtoDesignationDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}
	
	public String validateBasicSal(String sfId, Float basicSal, int desigId)throws Exception{
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String Query = "select case when count(*)>0  then 'YES' else 'Basic Pay value is not in prescribed band' end as valid from (select range_from,range_to from pay_payband_master where "
					+ " id=(select payband_type_id from payband_designation_mapping  where designation_id=?)) where " + " range_from<=? and range_to>=?";

			message = (String) session.createSQLQuery(Query).setInteger(0, desigId).setFloat(1, basicSal).setFloat(2, basicSal).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	
	
	@Override
	public String getBasicPay(EmployeePaymentBean employeePaymentBean)
			throws Exception {
		Session session = null;
		KeyDTO kDTO = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();

			String qry = "select ebph.basic_pay as key from emp_basic_pay_history ebph "
					+ " where ebph.sfid='"
					+ employeePaymentBean.getRefSfid().toUpperCase()
					+ "' and ebph.effective_date=(select max(effective_date) from emp_basic_pay_history ebp "
					+ " where  ebp.sfid='"
					+ employeePaymentBean.getRefSfid().toUpperCase()
					+ "' and ebp.status=1)" + " and ebph.status=1";

			kDTO = (KeyDTO) session
					.createSQLQuery(qry)
					.addScalar("key", Hibernate.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(KeyDTO.class))
							.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if(kDTO!=null){
			return kDTO.getKey();
		}else{
			return message;
			//promotionBean.setBasicPay(kDTO.get)
		}
		//return kDTO.getKey();
	}

	
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PromoteesEntryDTO> getOfflineEntryList(EmployeePaymentBean employeePaymentBean) throws Exception {
		
		Session session = null;
		String sql = "";
		try {
			session = hibernateUtils.getSession();
			sql = "select egph.id id,egph.sfid sfID,egph.variable_increments varIncPt,egph.var_inc_effective_date presentEffectiveDate,egph.two_addl_inc twoAddl,egph.var_inc_end_date varIncEndDate,"
				+ "egph.promoted_designation promotedDesignation,egph.promoted_effective_date promotedEffectiveDate,egph.grade_pay empNewGradePay,egph.reference_type referenceType,to_char(egph.seniority_date,'dd-Mon-YYYY') seniorityDate,"
				+ "nvl((select ebph.basic_pay from emp_basic_pay_history ebph where ebph.sfid=? and ebph.effective_date=(select max(effective_date) from emp_basic_pay_history ebp where ebp.designation_id=egph.promoted_designation and ebp.sfid=? and ebp.status=1) AND ebph.status=1),'0') pay,"
				+ "dm.name name,emp.NAME_IN_SERVICE_BOOK empName from emp_grade_pay_history egph,designation_master dm,emp_master emp where egph.promoted_designation=dm.id and egph.sfid=? and egph.sfid=emp.sfid and emp.status=1 and egph.status=1";

			employeePaymentBean.setOfflineEntryList(session
				.createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("sfID", Hibernate.STRING)
				.addScalar("empName", Hibernate.STRING)
				.addScalar("name")
				.addScalar("pay", Hibernate.FLOAT)
				.addScalar("varIncPt", Hibernate.INTEGER)
				.addScalar("promotedDesignation", Hibernate.INTEGER)
				.addScalar("presentEffectiveDate", Hibernate.DATE)
				.addScalar("promotedEffectiveDate", Hibernate.DATE)
				//.addScalar("newGradePay", Hibernate.FLOAT)
				.addScalar("empNewGradePay", Hibernate.STRING) // grade pay is set to empNewGradePay
				.addScalar("referenceType", Hibernate.STRING)
				.addScalar("twoAddl", Hibernate.STRING)
				.addScalar("seniorityDate", Hibernate.STRING)
				.addScalar("varIncEndDate", Hibernate.DATE)
				.setString(0, employeePaymentBean.getRefSfid().toUpperCase())
				.setString(1, employeePaymentBean.getRefSfid().toUpperCase())
				.setString(2, employeePaymentBean.getRefSfid().toUpperCase())
				.setResultTransformer(
						Transformers.aliasToBean(PromoteesEntryDTO.class))
						.list());

	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	return employeePaymentBean.getOfflineEntryList();
}

	
	@Override
	public String checkDuplicateOfflineEntry(
			EmployeePaymentBean employeePaymentBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session
					.createCriteria(PromoteesEntryDTO.class)
					.add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("sfID", employeePaymentBean.getRefSfid()
							.toUpperCase()))
							.add(Expression.eq("promotedDesignation",
									employeePaymentBean.getDesignationTo()));
			if (!CPSUtils.isNullOrEmpty(employeePaymentBean.getNodeID())) {
				crit = crit.add(Expression.ne(CPSConstants.ID,
						Integer.parseInt(employeePaymentBean.getNodeID())));
			}
			if (CPSUtils.checkList(crit.list())) {
				employeePaymentBean.setResult(CPSConstants.DUPLICATE);
			} else {
				employeePaymentBean.setResult(CPSConstants.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		}
		return employeePaymentBean.getResult();
	}

	@Override
	@SuppressWarnings("static-access")
	public String saveOfflineEntry(EmployeePaymentBean employeePaymentBean)
			throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			if(CPSUtils.isNullOrEmpty(employeePaymentBean.getNodeID())){  //new code added to update old record to 100 and new record to status 1
				PromoteesEntryDTO pDto=(PromoteesEntryDTO)session.createCriteria(PromoteesEntryDTO.class).add(Expression.eq("sfID", employeePaymentBean.getRefSfid())).add(Expression.eq("status", 1)).uniqueResult();
				if(pDto!=null){
					pDto.setPresentEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(pDto.getPresentEffectiveDate().toString())));
					pDto.setPromotedEffectiveDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(pDto.getPromotedEffectiveDate().toString())));
					pDto.setLastModifiedDate(CPSUtils.getCurrentDate());
					pDto.setVarIncEndDate(CPSUtils.convertStringToDate(CPSUtils.formattedDate(pDto.getVarIncEndDate().toString())));
					pDto.setSeniorityDate(CPSUtils.formattedDate(pDto.getSeniorityDate()));
					pDto.setReferenceType(pDto.getReferenceType());
					pDto.setStatus(100);
					session.saveOrUpdate(pDto);
					session.flush();	
				}

			}

			PromoteesEntryDTO entryDTO = new PromoteesEntryDTO();
			if (!CPSUtils.isNullOrEmpty(employeePaymentBean.getNodeID())) {
				entryDTO.setId(Integer.valueOf(employeePaymentBean.getNodeID()));
			}
			entryDTO.setSfID(employeePaymentBean.getRefSfid().toUpperCase());
			entryDTO.setPromotedDesignation(employeePaymentBean.getDesignationTo());
			entryDTO.setPromotedEffectiveDate(employeePaymentBean.getPromotionDate());
			// entryDTO.setNewPay(Float.valueOf(promotionBean.getNewBasicPay()));
			entryDTO.setNewGradePay(Float.valueOf(employeePaymentBean
					.getNewGradePay()));
			entryDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			entryDTO.setCreatedBy(employeePaymentBean.getSfID());
			entryDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			entryDTO.setLastModifiedBy(employeePaymentBean.getSfID());
			entryDTO.setReferenceType("Offline");
			entryDTO.setTwoAddl(employeePaymentBean.getTwoAddl());
			entryDTO.setVarIncEndDate(CPSUtils.critFormattedDate(employeePaymentBean
					.getVarIncEnd()));
			entryDTO.setStatus(1);
			entryDTO.setVarIncPt(employeePaymentBean.getDesignationFrom());
			entryDTO.setPresentEffectiveDate(employeePaymentBean
					.getPresentEffectivedate());
			entryDTO.setSeniorityDate(employeePaymentBean.getSeniorityDate());
			session.saveOrUpdate(entryDTO);
			message = CPSConstants.SUCCESS;
		
		} catch (Exception e) {
			e.printStackTrace();
			hibernateUtils.rollbackTransaction();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			hibernateUtils.closeSession();
		}
		return message;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public String deleteRecordPay(EmployeePaymentBean employeePaymentBean)
			throws Exception {
		
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();
			Query qry = session
					.createQuery("update PromoteesEntryDTO set status=? where id=?");
			qry.setInteger(0, 0);
			qry.setInteger(1, Integer.parseInt(employeePaymentBean.getNodeID()));
			qry.executeUpdate();
			employeePaymentBean.setResult(CPSConstants.DELETE);

		} catch (Exception e) {
			throw e;
		}
		return employeePaymentBean.getResult();

	}
	
	
	
	
	@Override
	public String getVarIncr(int gradepay, int varIncrPts) throws Exception {
		Session session = null;
		String varIncr = "";
		try {
			session = hibernateUtils.getSession();

			String query = "select calculate_var_incr(?,?) from dual";

			varIncr = session.createSQLQuery(query).setFloat(0, gradepay)
					.setFloat(1, varIncrPts).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		}
		return varIncr;
	}
	
	
	
	
}
