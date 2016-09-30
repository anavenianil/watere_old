package com.callippus.web.dao.empsearch;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLEmployeeSearchDAO implements IEmployeeSearchDAO, Serializable {

	private static final long serialVersionUID = 7048899710717627065L;
	
	@Autowired
	private HibernateUtils hibernateUtils;

	@Override
	public ArrayList<EmployeeBean> getEmpDetails(EmployeeBean empbean) throws Exception {
		Session session = null;
		List employeeList = null;
		ArrayList<EmployeeBean> employeesList = null;
		StringBuffer sb = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			sb = new StringBuffer();

			sb.append("select emp.sfid,emp.name_in_service_book,emp.personal_number,emp.internal_phone_no,dm.name,d.department_name,"
					+ "(select case when (select orh1.role_hierarchy_name from org_role_instance ori1,org_role_hierarchy orh1,emp_role_mapping erm1 where ori1.status=1 "
					+ "and orh1.status=1 and erm1.status=1 and erm1.org_role_id=ori1.org_role_id and ori1.role_hierarchy_id=orh1.role_hierarchy_id and "
					+ "erm1.sfid=emp.sfid and erm1.org_role_id=ori.org_role_id) is null then 'Employee' else orh.role_hierarchy_name end from dual) as roleName "
					+ "from emp_master emp,designation_master dm,emp_role_mapping erm,org_role_instance ori,departments_master d,org_role_hierarchy orh "
					+ "where ori.status=1 and emp.status=1 and dm.status=1 and erm.status=1 and d.status=1 and orh.status=1 and ori.department_id=d.department_id "
					+ "and emp.office_id=ori.org_role_id and ori.org_role_id=erm.org_role_id and emp.designation_id=dm.id and orh.role_hierarchy_id=ori.role_hierarchy_id ");

			if (!CPSUtils.isNullOrEmpty(empbean.getSfid())) {
				sb.append(" and upper(emp.sfid) like :sfid ");
			}
			if (!CPSUtils.isNullOrEmpty(empbean.getSearchWith()) && !CPSUtils.isNullOrEmpty(empbean.getFirstName())) {
				sb.append(" and upper(emp.name_in_service_book) like upper(:name) ");
			}
			if (!CPSUtils.isNullOrEmpty(empbean.getDesignationId())) {
				sb.append("  and dm.id= :desigid");
			}
			sb.append(" order by dm.order_no,emp.sfid");

			Query qry = session.createSQLQuery(sb.toString());

			if (!CPSUtils.isNullOrEmpty(empbean.getSfid())) {
				qry.setString("sfid", "%" + empbean.getSfid().toUpperCase().toUpperCase() + "%");
			}
			if (!CPSUtils.isNullOrEmpty(empbean.getSearchWith()) && !CPSUtils.isNullOrEmpty(empbean.getFirstName())) {
				if (CPSUtils.compareStrings(empbean.getSearchWith(), CPSConstants.STARTSWITH)) {
					qry.setString("name", empbean.getFirstName().toUpperCase() + "%");
					qry.setString("name", empbean.getFirstName().toUpperCase() + "%");
				} else {
					qry.setString("name", "%" + empbean.getFirstName() + "%");
					qry.setString("name", "%" + empbean.getFirstName() + "%");
				}
			}
			if (!CPSUtils.isNullOrEmpty(empbean.getDesignationId())) {
				qry.setString("desigid", empbean.getDesignationId());
			}
			employeeList = qry.list();
			employeesList = new ArrayList<EmployeeBean>();
			if (CPSUtils.checkList(employeeList)) {
				for (int i = 0; i < employeeList.size(); i++) {
					Object[] obj = (Object[]) employeeList.get(i);
					EmployeeBean empdetailsbean = new EmployeeBean();
					empdetailsbean.setSfid(obj[0].toString());
					empdetailsbean.setEmpName(obj[1].toString());
					if (!CPSUtils.isNull(obj[2]))
						empdetailsbean.setPersonalNumber(obj[2].toString());
					if (!CPSUtils.isNull(obj[3]))
						empdetailsbean.setInternalNo(obj[3].toString());
					if (!CPSUtils.isNull(obj[4]))
						empdetailsbean.setDesignationName(obj[4].toString());
					empdetailsbean.setOfficeName(obj[5].toString());
					empdetailsbean.setDirectorateName(obj[6].toString());
					employeesList.add(empdetailsbean);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

		}
		return employeesList;
	}
@Override
@SuppressWarnings("unchecked")
public EmployeeBean empRolesList(EmployeeBean empbean) throws Exception {
	Session session = null;
	List<EmployeeBean> empRoleslist = null;
	EmployeeBean tempEmpBean = null;
	boolean flag = false;
	try{

		session = hibernateUtils.getSession();//session = sessionFactory.openSession();

		/*String qry = null;
		String qry1 = "select org_role_id as value from emp_role_mapping where status=1 and sfid='"+empbean.getSfid()+"'";
		List<KeyValueDTO> roleIdList = session.createSQLQuery(qry1).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		for(int i=0;i<roleIdList.size();i++){
			if(CPSUtils.isNullOrEmpty(roleIdList.get(i).getValue())){
				flag = true;
				break;
			}
		}
		if(flag == true)
			{
			 qry = "SELECT INTERNAL_ROLE AS defaultRole,PARENT_ID AS parentSfid," +
						 " (" +
						 " SELECT NAME_IN_SERVICE_BOOK FROM EMP_MASTER WHERE STATUS=1 AND SFID IN" +
						 "(SELECT PARENT_ID FROM EMP_ROLE_MAPPING WHERE SFID='"+empbean.getSfid()+"' AND ORG_ROLE_ID IS NULL AND STATUS=1))AS parentName," +
						 "(" +
						 " SELECT ORG_ROLE_NAME as parentRole FROM ORG_ROLE_INSTANCE WHERE ORG_ROLE_ID IN" +
						 " (SELECT UNIQUE ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE ROLEM.SFID IN " +
						 " (SELECT PARENT_ID FROM EMP_ROLE_MAPPING WHERE SFID='"+empbean.getSfid()+"' and org_role_id is null and status=1) AND ROLEM.ORG_ROLE_ID IN " +
						 " (SELECT OFFICE_ID FROM EMP_MASTER WHERE STATUS=1 AND SFID='"+empbean.getSfid()+"'))) AS parentRole" +
						 " from emp_role_mapping where sfid='"+empbean.getSfid()+"' and org_role_id is null and status=1";
			tempEmpBean = (EmployeeBean)session.createSQLQuery(qry).addScalar("parentSfid", Hibernate.STRING).addScalar("parentName", Hibernate.STRING).addScalar("parentRole", Hibernate.STRING).addScalar("defaultRole", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).uniqueResult();
			
			}
	
		else{
			if(roleIdList.size()>1){
				String qry2 = "SELECT PARENT_ORG_ROLE_ID as id FROM ORG_ROLE_INSTANCE WHERE STATUS=1 AND ORG_ROLE_ID IN" +
						      "(SELECT PARENT_ROLE_ID FROM EMP_ROLE_MAPPING WHERE STATUS=1 AND SFID='"+empbean.getSfid()+"' AND " +
						      "ORG_ROLE_ID=(SELECT OFFICE_ID FROM EMP_MASTER WHERE STATUS=1 AND SFID='"+empbean.getSfid()+"'))";
				List<KeyValueDTO> parentList = session.createSQLQuery(qry2).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();	
				
				if(parentList.size()>0 && parentList.get(0).getId()==0)
				{
				qry="SELECT PRIMARYROLE.PRIMARYROLE AS defaultRole,TEMP.ADDITIONALROLES AS additionalRoles FROM " +
						" (SELECT ROLEI.ORG_ROLE_NAME AS PRIMARYROLE FROM ORG_ROLE_INSTANCE ROLEI WHERE ROLEI.ORG_ROLE_ID IN " +
						" (SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE EMP.STATUS=1 AND  " +
						" ROLEM.SFID='"+empbean.getSfid()+"' AND ROLEM.ORG_ROLE_ID =EMP.OFFICE_ID AND ROLEM.SFID=EMP.SFID))PRIMARYROLE," +
						" (select case when exists(select rolei.org_role_name FROM ORG_ROLE_INSTANCE ROLEI,EMP_ROLE_MAPPING ERP" +
						" WHERE ERP.SFID='"+empbean.getSfid()+"' AND ERP.STATUS=1 AND ERP.ORG_ROLE_ID = ROLEI.ORG_ROLE_ID AND ROLEI.ORG_ROLE_ID NOT IN " +
						"(SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE EMP.STATUS=1 AND  " +
						" ROLEM.SFID='"+empbean.getSfid()+"'AND ROLEM.ORG_ROLE_ID =EMP.OFFICE_ID AND ROLEM.SFID=EMP.SFID)) THEN " +
						"(SELECT RTRIM( XMLAGG( XMLELEMENT(E, TAB.ORG_ROLE_NAME||',')).EXTRACT('//text()'),',')  FROM " +
						" (select rolei.org_role_name FROM ORG_ROLE_INSTANCE ROLEI,EMP_ROLE_MAPPING ERP " +
						"WHERE erp.sfid='"+empbean.getSfid()+"' AND ERP.STATUS=1 AND ERP.ORG_ROLE_ID = ROLEI.ORG_ROLE_ID AND ROLEI.ORG_ROLE_ID NOT IN " +
						"(SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE EMP.STATUS=1" +
						" AND  ROLEM.SFID='"+empbean.getSfid()+"' AND ROLEM.ORG_ROLE_ID =EMP.OFFICE_ID AND ROLEM.SFID=EMP.SFID)) TAB)" +
						"ELSE('null' )end as additionalRoles from dual) temp";
				tempEmpBean = (EmployeeBean)session.createSQLQuery(qry).addScalar("defaultRole", Hibernate.STRING).addScalar("additionalRoles", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).uniqueResult();
				
				empbean.setDefaultRole(tempEmpBean.getDefaultRole());
				empbean.setAdditionalRoles(tempEmpBean.getAdditionalRoles());
				return empbean;
				}else{
				qry = "SELECT ROLEM.SFID AS parentSfid, EMP.NAME_IN_SERVICE_BOOK AS parentName,ROLEI.ORG_ROLE_NAME AS parentRole," +
			     "PRIMARYROLE.PRIMARYROLE AS defaultRole,temp.additionalRoles as additionalRoles FROM EMP_ROLE_MAPPING ROLEM," +
			     "ORG_ROLE_INSTANCE ROLEI,EMP_MASTER EMP," +
			     "(" +
			     "SELECT ROLEI.ORG_ROLE_NAME AS PRIMARYROLE FROM ORG_ROLE_INSTANCE ROLEI WHERE ROLEI.ORG_ROLE_ID IN " +
			     "(SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE EMP.STATUS=1 AND  " +
			     "ROLEM.SFID='"+empbean.getSfid()+"' AND ROLEM.ORG_ROLE_ID =EMP.OFFICE_ID AND ROLEM.SFID=EMP.SFID)" +
			     ")PRIMARYROLE," +
			     "(" +
			     "select case when exists(select rolei.org_role_name FROM ORG_ROLE_INSTANCE ROLEI,EMP_ROLE_MAPPING ERP " +
			     "WHERE erp.sfid='"+empbean.getSfid()+"' AND ERP.STATUS=1 AND ERP.ORG_ROLE_ID = ROLEI.ORG_ROLE_ID AND ROLEI.ORG_ROLE_ID NOT IN " +
			     "(SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE EMP.STATUS=1 AND  " +
			     "ROLEM.SFID='"+empbean.getSfid()+"'AND ROLEM.ORG_ROLE_ID =EMP.OFFICE_ID AND ROLEM.SFID=EMP.SFID)) " +
			     "THEN (SELECT RTRIM( XMLAGG( XMLELEMENT(E, tab.org_role_name||',')).EXTRACT('//text()'),',')  FROM" +
			     " (select rolei.org_role_name FROM ORG_ROLE_INSTANCE ROLEI,EMP_ROLE_MAPPING ERP WHERE erp.sfid='"+empbean.getSfid()+"' " +
			     " AND ERP.ORG_ROLE_ID = ROLEI.ORG_ROLE_ID AND ERP.STATUS=1 AND ROLEI.ORG_ROLE_ID NOT IN " +
			     "(SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE EMP.STATUS=1 AND  " +
			     "ROLEM.SFID='"+empbean.getSfid()+"'AND ROLEM.ORG_ROLE_ID =EMP.OFFICE_ID AND ROLEM.SFID=EMP.SFID)) tab)" +
			     "ELSE" +
			     "(" +
			     "'null' )end as additionalRoles from dual) temp " +
			     "WHERE ROLEM.STATUS=1 AND ROLEI.STATUS=1 AND EMP.STATUS=1 AND ROLEM.ORG_ROLE_ID=ROLEI.ORG_ROLE_ID AND " +
			     " ROLEM.ORG_ROLE_ID IN (SELECT PARENT_ORG_ROLE_ID FROM ORG_ROLE_INSTANCE WHERE STATUS=1 AND ORG_ROLE_ID IN" +
			     "(SELECT OFFICE_ID FROM EMP_MASTER WHERE STATUS=1 AND SFID='"+empbean.getSfid()+"')) AND ROLEM.SFID=EMP.SFID " +
			     "group by ROLEM.SFID,EMP.NAME_IN_SERVICE_BOOK,ROLEI.ORG_ROLE_NAME,primaryRole.primaryRole";	
				}
			}else if(roleIdList.size()==1){
				qry = "SELECT ROLEM.SFID AS parentSfid, EMP.NAME_IN_SERVICE_BOOK AS parentName," +
					  "ROLEI.ORG_ROLE_NAME AS parentRole,PRIMARYROLE.PRIMARYROLE AS defaultRole," +
					  " temp.additionalRoles as additionalRoles FROM EMP_ROLE_MAPPING ROLEM,ORG_ROLE_INSTANCE ROLEI," +
					  "EMP_MASTER EMP," +
					  "(SELECT ROLEI.ORG_ROLE_NAME AS PRIMARYROLE FROM ORG_ROLE_INSTANCE ROLEI WHERE ROLEI.ORG_ROLE_ID " +
					  " IN (SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM WHERE  rolem.status=1 and " +
					  " ROLEM.SFID='"+empbean.getSfid()+"'))PRIMARYROLE,(SELECT CASE WHEN EXISTS" +
					  " (SELECT ROLEI.ORG_ROLE_NAME FROM ORG_ROLE_INSTANCE ROLEI,EMP_ROLE_MAPPING ERP " +
					  " WHERE ERP.SFID='"+empbean.getSfid()+"' AND ERP.STATUS=1 AND ERP.ORG_ROLE_ID = ROLEI.ORG_ROLE_ID AND ROLEI.ORG_ROLE_ID NOT IN" +
					  "(SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM WHERE status=1 and ROLEM.SFID='"+empbean.getSfid()+"')) " +
					  " THEN (SELECT RTRIM( XMLAGG( XMLELEMENT(E, tab.org_role_name||',')).EXTRACT('//text()'),',') " +
					  " FROM (SELECT ROLEI.ORG_ROLE_NAME FROM ORG_ROLE_INSTANCE ROLEI,EMP_ROLE_MAPPING ERP WHERE " +
					  " ERP.SFID='"+empbean.getSfid()+"' AND ERP.ORG_ROLE_ID = ROLEI.ORG_ROLE_ID AND ERP.STATUS=1 AND ROLEI.ORG_ROLE_ID NOT IN " +
					  "(SELECT ROLEM.ORG_ROLE_ID FROM EMP_ROLE_MAPPING ROLEM,EMP_MASTER EMP WHERE EMP.STATUS=1 AND  " +
					  " ROLEM.SFID='"+empbean.getSfid()+"'AND ROLEM.ORG_ROLE_ID =EMP.OFFICE_ID AND ROLEM.SFID=EMP.SFID)) tab)" +
					  " ELSE('null' ) end asS additionalRoles from dual)temp WHERE ROLEM.STATUS=1 AND ROLEI.STATUS=1 " +
					  " AND EMP.STATUS=1 AND ROLEM.ORG_ROLE_ID=ROLEI.ORG_ROLE_ID AND  ROLEM.ORG_ROLE_ID IN " +
					  " (SELECT PARENT_ORG_ROLE_ID FROM ORG_ROLE_INSTANCE WHERE STATUS=1 AND ORG_ROLE_ID" +
					  " IN(SELECT OFFICE_ID FROM EMP_MASTER WHERE STATUS=1AND SFID='"+empbean.getSfid()+"')) " +
					  " AND ROLEM.SFID=EMP.SFID group by ROLEM.SFID,EMP.NAME_IN_SERVICE_BOOK," +
					  "ROLEI.ORG_ROLE_NAME,primaryRole.primaryRole";
			}
	tempEmpBean = (EmployeeBean)session.createSQLQuery(qry).addScalar("parentSfid", Hibernate.STRING).addScalar("parentName", Hibernate.STRING).addScalar("parentRole", Hibernate.STRING).addScalar("defaultRole", Hibernate.STRING).addScalar("additionalRoles", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).uniqueResult();
	if(tempEmpBean != null){
	empbean.setAdditionalRoles(tempEmpBean.getAdditionalRoles());
	}
}
		if(tempEmpBean != null){s
	empbean.setParentSfid(tempEmpBean.getParentSfid());
	empbean.setParentName(tempEmpBean.getParentName());
	empbean.setParentRole(tempEmpBean.getParentRole());
	empbean.setDefaultRole(tempEmpBean.getDefaultRole());
		}*/
		/*String qry="select (case when TAB.ORG_ROLE_ID is null then 'Employee' else (select ORG_ROLE_NAME from ORG_ROLE_INSTANCE where " +
				" ORG_ROLE_ID=TAB.ORG_ROLE_ID and STATUS=1) end) as additionalRoles," +
				"(select case when ((select OFFICE_ID from EMP_MASTER where STATUS=1 and SFID=TAB.SFID) not in (select NVL(ORG_ROLE_ID,-1) " +
				" from EMP_ROLE_MAPPING where STATUS=1 and SFID=TAB.SFID) and TAB.ORG_ROLE_ID is null) then 'YES' when " +
				" TAB.ORG_ROLE_ID=(select OFFICE_ID from EMP_MASTER where STATUS=1 and SFID=TAB.SFID) then 'YES' else 'NO' end from DUAL) as defaultRole," +
				" TAB.PARENT_SFID as parentSfid,(select NAME_IN_SERVICE_BOOK from EMP_MASTER where SFID=TAB.PARENT_SFID) as parentName," +
				" (select ORG_ROLE_NAME from ORG_ROLE_INSTANCE where ORG_ROLE_ID=(select OFFICE_ID from EMP_MASTER where STATUS=1 and " +
				" SFID=TAB.PARENT_SFID)) as parentRole" +
				" from (select EMP_RM.SFID,EMP_RM.ORG_ROLE_ID,(case when EMP_RM.ORG_ROLE_ID is null then PARENT_ID else " +
				" (select SFID from EMP_ROLE_MAPPING where STATUS=1 and ORG_ROLE_ID = (select PARENT_ORG_ROLE_ID from ORG_ROLE_INSTANCE " +
				" where STATUS=1 and ORG_ROLE_ID in (select ORG_ROLE_ID from EMP_ROLE_MAPPING where STATUS=1 and ORG_ROLE_ID=EMP_RM.ORG_ROLE_ID))) " +
				" end) as PARENT_SFID from EMP_ROLE_MAPPING EMP_RM where EMP_RM.STATUS=1) TAB where TAB.SFID='"+empbean.getSfid()+"' order by defaultRole desc";*/
		String qry="select (case when TAB.ORG_ROLE_ID is null then 'Employee' else (select ORG_ROLE_NAME from ORG_ROLE_INSTANCE where " +
		" ORG_ROLE_ID=TAB.ORG_ROLE_ID and STATUS=1) end) as additionalRoles," +
		"(select case when ((select OFFICE_ID from EMP_MASTER where STATUS=1 and SFID=TAB.SFID) not in (select NVL(ORG_ROLE_ID,-1) " +
		" from EMP_ROLE_MAPPING where STATUS=1 and SFID=TAB.SFID) and TAB.ORG_ROLE_ID is null) then 'YES' when " +
		" TAB.ORG_ROLE_ID=(select OFFICE_ID from EMP_MASTER where STATUS=1 and SFID=TAB.SFID) then 'YES' else 'NO' end from DUAL) as defaultRole," +
		" TAB.PARENT_SFID as parentSfid,(select NAME_IN_SERVICE_BOOK from EMP_MASTER where SFID=TAB.PARENT_SFID) as parentName," +
		" (select ORG_ROLE_NAME from ORG_ROLE_INSTANCE where ORG_ROLE_ID=TAB.PARENT_ORG_ROLE_ID) as parentRole" +
		" from (select EMP_RM.SFID,EMP_RM.ORG_ROLE_ID,(case when EMP_RM.ORG_ROLE_ID is null then PARENT_ID else " +
		" (select SFID from EMP_ROLE_MAPPING where STATUS=1 and ORG_ROLE_ID = (select PARENT_ORG_ROLE_ID from ORG_ROLE_INSTANCE " +
		" where STATUS=1 and ORG_ROLE_ID in (select ORG_ROLE_ID from EMP_ROLE_MAPPING where STATUS=1 and ORG_ROLE_ID=EMP_RM.ORG_ROLE_ID))) " +
		" end) as PARENT_SFID,(Select Case When  Emp_Rm.Org_Role_Id Is Null Then (Select Parent_Role_Id From Emp_Role_Mapping Where " +
		" Sfid=Emp_Rm.Sfid And Id=Emp_Rm.Id) Else (Select Parent_ORG_Role_Id From org_role_instance Where Org_Role_Id= EMP_RM.ORG_ROLE_ID) " +
		" end as parent_role_id from dual) AS PARENT_ORG_ROLE_ID from EMP_ROLE_MAPPING EMP_RM where EMP_RM.STATUS=1) TAB where " +
		" TAB.SFID='"+empbean.getSfid()+"' order by defaultRole desc";
		
		empRoleslist = session.createSQLQuery(qry).addScalar("parentSfid", Hibernate.STRING).addScalar("parentName", Hibernate.STRING).addScalar("parentRole", Hibernate.STRING).addScalar("defaultRole", Hibernate.STRING).addScalar("additionalRoles", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
		if(empRoleslist.size()>0){
			empbean.setEmpRoleslist(empRoleslist);
		}
		}catch (Exception e) {
		throw e;
	} finally {

	}
	return empbean;
	
}
@Override
public EmployeeBean empPayList(EmployeeBean empbean) throws Exception {
	Session session = null;
	EmployeeBean empTempBean = null;
	
	try{

		session = hibernateUtils.getSession();//session = sessionFactory.openSession();

		String qry = "select BASIC_PAY AS basicPay,GRADE_PAY AS gradePay from emp_payment_details where status=1 and sfid='"+empbean.getSfid()+"'";


		empTempBean = (EmployeeBean)session.createSQLQuery(qry).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).uniqueResult();
		if(empTempBean != null){
		empbean.setBasicPay(empTempBean.getBasicPay());
		empbean.setGradePay(empTempBean.getGradePay());
		}
	}catch(Exception e){
		throw e;
	} finally {

		//session.close();

	}
	return empbean;
}
	@Override
	public EmployeeBean getEmployeeDetails(EmployeeBean emp) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			String sql = "select to_char(emp.dob,'DD-Mon-YYYY')  dob,(select  rtrim (xmlagg (xmlelement (e, dis.name || ',')).extract ('//text()'), ',') from qualification_details qd,discipline_master dis where sfid=? and dis.id=qd.discipline_id) dis, "
					+ " to_char(emp.doj_asl,'DD-Mon-YYYY') aslDate,emp.personal_number mobile,emp.sfid sfid,d1.department_name directorate,d.department_name division,emp.name_in_service_book name, "
					+ " emp.internal_phone_no internalNo,dm.name designation from designation_master dm, emp_master emp, org_role_instance org,org_role_instance org1,departments_master d ,departments_master d1 where emp.sfid=? and "
					+ " emp.office_id=org.org_role_id and emp.directorate_id=org1.org_role_id  and org1.department_id=d1.department_id and d.status=1 and org.department_id=d.department_id and dm.status=1 and dm.id=emp.designation_id";
			ps = con.prepareStatement(sql);
			ps.setString(1, emp.getSfid());
			ps.setString(2, emp.getSfid());
			rsq = ps.executeQuery();
			if (rsq.next()) {
				emp.setDojAsl(rsq.getString("aslDate"));
				emp.setDob(rsq.getString("dob"));
				emp.setPersonalNumber(rsq.getString("mobile"));
				emp.setDiscipline(rsq.getString("dis"));
				emp.setDirectorateName(rsq.getString("directorate"));
				emp.setDivisionName(rsq.getString("division"));
				emp.setSfid(rsq.getString("sfid"));
				emp.setName(rsq.getString("name"));
				emp.setInternalNo(rsq.getString("internalNo"));
				emp.setDesignationName(rsq.getString("designation"));

			}
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

		}
		return emp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EmployeeBean getEmployeeTreeDetails(EmployeeBean empbean) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		String getInstanceTree = null;
		List<EmpRoleMappingDTO> instanceList = null;
		ArrayList<String> orgRolesList = null;
		ArrayList<KeyValueDTO> departemntsList = null;
		ArrayList<EmployeeBean> employeesList = null;
		ArrayList<String> testRoles = null;
		StringBuffer rolesql = new StringBuffer();
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			employeesList = new ArrayList<EmployeeBean>();
			departemntsList = new ArrayList<KeyValueDTO>();
			testRoles = new ArrayList<String>();
			orgRolesList = new ArrayList<String>();
			rolesql.append("select org_role_id as roleInstanceId from emp_role_mapping where parent_id is null ");
			if (!empbean.getRoleList().contains("ROLE_SUPERADMIN") || (!CPSUtils.isNull(empbean.getSfid()) && CPSUtils.compareStrings("tree", empbean.getParam()))) {
				rolesql.append("and sfid='" + empbean.getSfid() + "'");
			}
			rolesql.append(" and status=1 order by org_role_id");
			instanceList = session.createSQLQuery(rolesql.toString()).addScalar("roleInstanceId", Hibernate.INTEGER).setResultTransformer(
					Transformers.aliasToBean(EmpRoleMappingDTO.class)).list();
			// for tree display
			HashMap<Object, Object> deptlevels = new HashMap<Object, Object>();
			int level = 0;
			for (int i = 0; i < instanceList.size(); i++) {
				EmpRoleMappingDTO empRoleMapDTO = instanceList.get(i);
				getInstanceTree = "select ori.org_role_id,dm.department_id,dm.department_name,level from org_role_instance ori,departments_master dm where ori.status=1 and dm.status=1 and dm.department_id=ori.department_id "
						+ "start with ori.org_role_id=? connect by ori.parent_org_role_id = prior ori.org_role_id";
				ps = con.prepareStatement(getInstanceTree);
				ps.setInt(1, empRoleMapDTO.getRoleInstanceId());
				rsq = ps.executeQuery();
				while (rsq.next()) {
					if (!orgRolesList.contains(rsq.getString("org_role_id"))) {
						orgRolesList.add(rsq.getString("org_role_id"));
					}
					if (!testRoles.contains(rsq.getString("org_role_id"))) {
						KeyValueDTO keyValDto = new KeyValueDTO();
						keyValDto.setId(Integer.parseInt(rsq.getString("department_id")));
						keyValDto.setName(rsq.getString("department_name"));
						if (!testRoles.contains(rsq.getString("department_id"))) {
							departemntsList.add(keyValDto);
						}
						deptlevels.put(rsq.getString("org_role_id"), rsq.getString("level"));
						level = rsq.getInt("level");
						testRoles.add(rsq.getString("org_role_id"));
					}
				}
				ps.close();
				rsq.close();
			}
			for (int i = 0; i < orgRolesList.size(); i++) {
				String sfid = " ";
				StringBuffer headDetails = new StringBuffer();
				// get the instances mapped with the role_instance_id
				headDetails
						.append("select ori.org_role_name AS divisionName,emp.sfid AS sfid,emp.name_in_service_book,dm.name designation,emp.personal_number,emp.internal_phone_no,d.department_name,orh.role_hierarchy_name,ori.org_role_id,d.department_id,"
								+ " (select parent_org_role_id from org_role_instance pdm where pdm.org_role_id=ori.org_role_id) parentdept "
								+ "from emp_master emp,emp_role_mapping erm,designation_master dm,org_role_instance ori,org_role_hierarchy orh,departments_master d "
								+ "where erm.org_role_id=? and erm.status=1 and emp.status=1 and erm.sfid=emp.sfid and dm.status=1 and emp.designation_id=dm.id "
								+ "and ori.status=1 and ori.org_role_id=erm.org_role_id and orh.status=1 and orh.role_hierarchy_id=ori.role_hierarchy_id and d.status=1 and d.department_id=ori.department_id");

				if (CPSUtils.compareStrings("name", empbean.getSearchingWith())) {
					if (CPSUtils.compareStrings("startWith", empbean.getSelectedValue())) {
						headDetails.append(" and upper(emp.name_in_service_book) like '" + empbean.getEnteredValue().toUpperCase() + "%'");
					} else {
						headDetails.append(" and upper(emp.name_in_service_book) like upper('%" + empbean.getEnteredValue() + "%')");
					}
				} else if (CPSUtils.compareStrings("sfid", empbean.getSearchingWith())) {
					headDetails.append(" and upper(emp.sfid)='" + empbean.getEnteredValue().toUpperCase() + "'");
				} else if (CPSUtils.compareStrings("designation", empbean.getSearchingWith())) {
					headDetails.append(" and dm.id=" + empbean.getSelectedValue());
				} else if (CPSUtils.compareStrings("instance", empbean.getSearchingWith()) && !CPSUtils.compareStrings("select", empbean.getSelectedValue())) {
					headDetails.append(" and d.department_id=" + empbean.getSelectedValue());
				}
				headDetails.append(" order by dm.order_no,emp.sfid");
				ps = con.prepareStatement(headDetails.toString());
				ps.setString(1, orgRolesList.get(i));
				rsq = ps.executeQuery();
				session.flush();
				if (rsq.next()) {
					sfid = rsq.getString("sfid");
					EmployeeBean empdetailsbean = new EmployeeBean();
					empdetailsbean.setSfid(rsq.getString("sfid"));
					empdetailsbean.setDivisionName(rsq.getString("divisionName"));
					empdetailsbean.setEmpName(rsq.getString("name_in_service_book"));
					empdetailsbean.setPersonalNumber(rsq.getString("personal_number"));
					empdetailsbean.setInternalNo(rsq.getString("internal_phone_no"));
					empdetailsbean.setDesignationName(rsq.getString("designation"));
					empdetailsbean.setOfficeName(rsq.getString("department_name"));
					empdetailsbean.setDirectorateName(rsq.getString("role_hierarchy_name"));
					empdetailsbean.setLevel(Integer.parseInt(deptlevels.get(orgRolesList.get(i)).toString()));
					empdetailsbean.setDepartmentId(rsq.getString("org_role_id"));
					if (empdetailsbean.getLevel() == 1)
						empdetailsbean.setParent(rsq.getString("department_id"));
					else
						empdetailsbean.setParent(rsq.getString("parentdept"));
					employeesList.add(empdetailsbean);
				}

				StringBuffer getEmpDetails = new StringBuffer();
				getEmpDetails
						.append("select  emp.sfid  AS sfid,emp.name_in_service_book,emp.personal_number,emp.internal_phone_no,dm.name designation,d.department_name,d.department_id,ori.org_role_id, ori1.org_role_name AS divisionName  "
								+ "from emp_master emp,designation_master dm,org_role_instance ori,departments_master d,emp_role_mapping erm LEFT outer join org_role_instance ORI1 oN(erm.org_role_id = ORI1.org_role_id ) where erm.parent_role_id=? and emp.status=1 and dm.status=1 and "
								+ "dm.id=emp.designation_id and ori.status=1 and ori.org_role_id=erm.parent_role_id and d.status=1  and  d.department_id=ori.department_id and emp.sfid!=? and erm.status=1 and erm.sfid=emp.sfid and erm.parent_id is not null AND erm.org_role_id IS NULL ");

				if (CPSUtils.compareStrings("name", empbean.getSearchingWith())) {
					if (CPSUtils.compareStrings("startWith", empbean.getSelectedValue())) {
						getEmpDetails.append(" and upper(emp.name_in_service_book) like '" + empbean.getEnteredValue().toUpperCase() + "%'");
					} else {
						getEmpDetails.append(" and upper(emp.name_in_service_book) like upper('%" + empbean.getEnteredValue() + "%')");
					}
				} else if (CPSUtils.compareStrings("sfid", empbean.getSearchingWith())) {
					getEmpDetails.append(" and upper(emp.sfid)='" + empbean.getEnteredValue().toUpperCase() + "'");
				} else if (CPSUtils.compareStrings("designation", empbean.getSearchingWith())) {
					getEmpDetails.append(" and dm.id=" + empbean.getSelectedValue());
				} else if (CPSUtils.compareStrings("instance", empbean.getSearchingWith()) && !CPSUtils.compareStrings("select", empbean.getSelectedValue())) {
					getEmpDetails.append(" and d.department_id=" + empbean.getSelectedValue());
				}
				getEmpDetails.append(" order by d.department_id,dm.id,emp.sfid");
				ps = con.prepareStatement(getEmpDetails.toString());
				ps.setString(1, orgRolesList.get(i));
				ps.setString(2, sfid);
				rsq = ps.executeQuery();
				session.flush();
				while (rsq.next()) {
					EmployeeBean empdetailsbean = new EmployeeBean();
					empdetailsbean.setSfid(rsq.getString("sfid"));
					empdetailsbean.setDivisionName(rsq.getString("divisionName"));
					empdetailsbean.setEmpName(rsq.getString("name_in_service_book"));
					empdetailsbean.setPersonalNumber(rsq.getString("personal_number"));
					empdetailsbean.setInternalNo(rsq.getString("internal_phone_no"));
					empdetailsbean.setDesignationName(rsq.getString("designation"));
					empdetailsbean.setOfficeName(rsq.getString("department_name"));
					empdetailsbean.setDirectorateName("Employee");
					empdetailsbean.setLevel(level + 1);
					empdetailsbean.setDepartmentId(rsq.getString("org_role_id"));
					employeesList.add(empdetailsbean);
				}
				rsq.close();
				ps.close();
			}
			empbean.setInstanceList(departemntsList);
			empbean.setEmpSearchList(employeesList);
		} catch (Exception e) {
			throw e;
		} finally {
			
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return empbean;
	}

	@Override
	public List<KeyValueDTO> getHeadList() throws Exception {
		Session session = null;
		List<KeyValueDTO> headList = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String sql = "select emp.sfid key,emp.sfid||'-'||emp.name_in_service_book value from emp_role_mapping erm,emp_master emp "
					+ "where erm.status=1 and erm.org_role_id is not null and emp.status=1 and emp.sfid=erm.sfid and emp.flag is null "
					+ "group by emp.sfid,emp.name_in_service_book order by emp.sfid";
			headList = session.createSQLQuery(sql).addScalar("value").addScalar("key").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

		}
		return headList;
	}

	@Override
	public boolean checkDownTree(String searchedSfid, String loginSfid) throws Exception {
		Session session = null;
		Session session1 = null;
		Connection con = null;
		Connection con1 = null;
		boolean status = false;
		ArrayList<String> searchedEmpNodes = null;
		ArrayList<String> loginEmpNodes = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rsq = null;
		ResultSet rsq1 = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			con = session.connection();
			session1 = hibernateUtils.getSession();
			con1 = session1.connection();

			searchedEmpNodes = new ArrayList<String>();

			String nodes = "select office_id from emp_master where sfid=?";
			ps = con.prepareStatement(nodes);
			ps.setString(1, searchedSfid);
			rsq = ps.executeQuery();
			if (rsq.next()) {
				searchedEmpNodes.add(rsq.getString("office_id"));
			}
			nodes = "select org_role_id from emp_role_mapping where sfid=? and status=1 and org_role_id is not null";
			ps = con.prepareStatement(nodes);
			ps.setString(1, searchedSfid);
			rsq = ps.executeQuery();
			while (rsq.next()) {
				searchedEmpNodes.add(rsq.getString("org_role_id"));
			}
			loginEmpNodes = new ArrayList<String>();

			/**
			 * get the login emp nodes
			 */
			String loginNodes = "select org_role_id from emp_role_mapping where sfid=? and status=1 and org_role_id is not null";
			ps = con.prepareStatement(loginNodes);
			ps.setString(1, loginSfid);
			rsq = ps.executeQuery();
			while (rsq.next()) {
				loginEmpNodes.add(rsq.getString("org_role_id"));
				String nodeDownTree = "select org_role_id from org_role_instance where status=1 start with org_role_id=? connect by parent_org_role_id = prior org_role_id";
				ps1 = con1.prepareStatement(nodeDownTree);
				ps1.setString(1, rsq.getString("org_role_id"));
				rsq1 = ps1.executeQuery();
				while (rsq1.next()) {
					loginEmpNodes.add(rsq1.getString("org_role_id"));
				}
			}
			for (int i = 0; i < searchedEmpNodes.size(); i++) {

				for (int j = 0; j < loginEmpNodes.size(); j++) {

					if (searchedEmpNodes.get(i).equals(loginEmpNodes.get(j))) {
						status = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
			ConnectionUtil.closeConnection(session1, ps1, rsq1);
		}
		return status;
	}

	@Override
	public List<Object> getOrganizationTree(String instanceId) throws Exception {
		Session session = null;
		List<Object> tree = null;
		List<Object> emptree = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (CPSUtils.isNull(instanceId))
				instanceId = "1";
			String sql = "select level,dm.department_id,department_name,dm.parent_department_id,erm.sfid,emp.name_in_service_book as name from departments_master dm,emp_role_mapping erm,emp_master emp,org_role_instance org where dm.status=1 and  erm.status=1 and org.status=1 and dm.department_id=org.department_id "
					+ " and emp.status=1 and emp.sfid=erm.sfid and org.org_role_id = erm.org_role_id start with dm.department_id=? connect by parent_department_id=prior dm.department_id order by level desc ";
			tree = session.createSQLQuery(sql).setString(0, instanceId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			emptree = new ArrayList();
			for (int i = 0; i < tree.size(); i++) {
				HashMap emp = (HashMap) tree.get(i);
				String empsql = "select emp.sfid,department_name,emp.sfid as department_id,dm.department_id as parent_department_id,emp.name_in_service_book as name from emp_role_mapping erm,departments_master dm,org_role_instance org,emp_master emp where dm.status=1 and org.status=1 "
						+ " and erm.parent_role_id=org.org_role_id and dm.department_id=org.department_id and parent_id=? and erm.status=1 and emp.status=1 and erm.parent_role_id=? and emp.sfid=erm.sfid";
				List employees = session.createSQLQuery(empsql).setString(0, emp.get("SFID").toString()).setString(1, emp.get("DEPARTMENT_ID").toString()).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP).list();
				emptree.addAll(employees);
				emptree.add(emp);
			}
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

		}
		return emptree;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getCustomReportEmpDetails(JSONObject json) throws Exception {
		Session session = null;
		ArrayList<HashMap<String, String>> employeesList = null;
		StringBuffer sb = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			sb = new StringBuffer();
			String fields[] = getFields(json.getString("fields"));
			String conditions[] = getConditions(json);

			sb
					.append("select "
							+ fields[0]
							+ " (from( select to_char(employee.last_promotion,'DD-Mon-yyyy') lastPromotion,dm.name designation,employee.name_in_service_book employeeName,cm.name categoryName, rm.name religionName,ph.name phName,employee.INTERNAL_PHONE_NO internalNo,)order by select");
			
			sb.append(" (leave.cl casualLeave ,leave.hpl halfPayLeave,leave.el earnedLeave,quarter.quarter_number quarterNumber, to_char(employee.doj_asl,'DD-Mon-yyyy') dojAsl,to_char(employee.dob,'DD-Mon-yyyy') Dob,employee.cgsh_number cghsNumber, a.reportingOfficer reportingTo,'Group '||gm.name groupName,employee.sfid sfid,quaType.name qualification )order by select");
					//.append(" leave.cl casualLeave ,leave.hpl halfPayLeave,leave.el earnedLeave,quarter.quarter_number quarterNumber, to_char(employee.doj_asl,'DD-Mon-yyyy') dojAsl,to_char(employee.dob,'DD-Mon-yyyy') Dob,employee.cgsh_number cghsNumber, a.reportingOfficer reportingTo,'Group '||gm.name groupName,employee.sfid sfid,quaType.name qualification ,a.division division ");
			sb
					.append("	from (select erm.sfid, (case when erm.org_role_id is not null then (select org_role_name from org_role_instance porl where porl.org_role_id =orl.parent_org_role_id) else (select name_in_service_book from emp_master where sfid=erm.parent_id) end)      ");
			
			sb .append("reportingOfficer, (2) as division From Org_role_instance Orl,Emp_role_mapping Erm where orl.org_role_id = erm.parent_role_id and orl.status=1 and erm.status=1) a,   ");     
					//.append("reportingOfficer, (select department_name from departments_master where department_id=orl.department_id) as division From Org_role_instance Orl,Emp_role_mapping Erm where orl.org_role_id = erm.parent_role_id and orl.status=1 and erm.status=1) a,   ");
			sb
					.append(" emp_master employee,designation_master dm,Designation_mappings Dms,Category_master Cm,group_master gm,religion_master rm, quarter_details quarter,qualification_details qualification,qualification_master quaType, ph_type_master ph,  ");
			sb
					.append(" (select sfid,  MAX(CASE WHEN leave_type_id = '3' THEN available_leaves END) as CL ,MAX(CASE WHEN leave_type_id = '1' THEN available_leaves END) as EL,MAX(CASE WHEN leave_type_id = '2' THEN available_leaves END) as HPL from  ");
			sb
					.append("(select available_leaves,la.leave_type_id,la.sfid from AVAILABLE_LEAVES la,leave_type_master lm where  la.leave_type_id=lm.id ) group by sfid order by sfid)leave Where Employee.Sfid=A.Sfid And Employee.Status=1 And Dm.Status=1 And Cm.Status=1   ");
			sb
					.append("And employee.designation_id=dm.id and dm.id=dms.desig_id and employee.religion_id=rm.id and rm.status=1 and quarter.sfid(+)=employee.sfid and qualification.sfid(+)=employee.sfid and quaType.id(+)=qualification.qualification_id and leave.sfid(+)=employee.sfid  ");
			
			sb.append("and ph.id(+)=employee.handicap_id and dms.category_id=cm.id and gm.status=1 and dms.group_id=gm.id   ");
			
			
			
			/*if(conditions[0].length()==0 && conditions[1].length()==0)
				sb.append("and ph.id(+)=employee.handicap_id and dms.category_id=cm.id and gm.status=1 and dms.group_id=gm.id   ");
			else if(conditions[0].length()>0 && conditions[1].length()==0)
				sb.append("and ph.id(+)=employee.handicap_id and dms.category_id=cm.id and gm.status=1 and dms.group_id=gm.id "+conditions[0] + " and employee.flag is null )");
			
			else if(conditions[0].length()>0 && conditions[1].length()>0)
				sb.append("and ph.id(+)=employee.handicap_id and dms.category_id=cm.id and gm.status=1 and dms.group_id=gm.id "+conditions[0] + " and employee.flag is null order by dm.order_no" + conditions[1] + ")");

			else if(conditions[0].length()==0 && conditions[1].length()>0)
				sb.append("and ph.id(+)=employee.handicap_id and dms.category_id=cm.id and gm.status=1 and dms.group_id=gm.id "+conditions[0] + " and employee.flag is null");

			else
				sb.append("and ph.id(+)=employee.handicap_id and dms.category_id=cm.id and gm.status=1 and dms.group_id=gm.id   ");
			*/
			// for where condition
			
			sb.append(conditions[0] + " and employee.flag is null");
			
			sb.append(" order by dm.order_no" + conditions[1] + ")");
			
			sb.append(" group by " + fields[1]);
			sb.append(" order by " + fields[1]);
			String temp = sb.toString();

			employeesList = (ArrayList<HashMap<String, String>>) session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

			//session.close();

		}
		return employeesList;
	}

	public String[] getFields(String field) throws Exception {
		StringBuffer sb1 = null;
		StringBuffer sb2 = null;
		String[] values = new String[3];
		try {
			String[] fields = field.split(",");
			String fieldName = "";
			String groupby = "";
			sb1 = new StringBuffer();
			sb2 = new StringBuffer();
			for (int i = 0; i < fields.length; i++) {
				if (CPSUtils.compareStrings(fields[i], "SFID")) {
					fieldName = "sfid";
					groupby = "sfid";
				} else if (CPSUtils.compareStrings(fields[i], "Name")) {
					fieldName = "employeeName";
					groupby = "employeeName";
				} else if (CPSUtils.compareStrings(fields[i], "Category")) {
					fieldName = "categoryName";
					groupby = "categoryName";
				} else if (CPSUtils.compareStrings(fields[i], "Group")) {
					fieldName = "groupName";
					groupby = "groupName ";
				} else if (CPSUtils.compareStrings(fields[i], "Reporting Officer")) {
					fieldName = "reportingTo";
					groupby = "reportingTo ";
				} else if (CPSUtils.compareStrings(fields[i], "Division")) {
					fieldName = "division";
					groupby = "division";
				} else if (CPSUtils.compareStrings(fields[i], "CGHS Disp No")) {
					fieldName = "cghsNumber";
					groupby = "cghsNumber";
				} else if (CPSUtils.compareStrings(fields[i], "Doj in ASL")) {
					fieldName = "dojAsl";
					groupby = "dojAsl";
				} else if (CPSUtils.compareStrings(fields[i], "Qualification")) {
					fieldName = " rtrim (xmlagg (xmlelement (e, qualification || ',')).extract ('//text()'), ',') qualification";
				} else if (CPSUtils.compareStrings(fields[i], "DOB")) {
					fieldName = "Dob";
					groupby = "Dob";
				} else if (CPSUtils.compareStrings(fields[i], "Phone No")) {
					fieldName = "internalNo";
					groupby = "internalNo";
				} else if (CPSUtils.compareStrings(fields[i], "Date Of Present Rank")) {
					fieldName = "lastPromotion ";
					groupby = "lastPromotion ";
				} else if (CPSUtils.compareStrings(fields[i], "Religion")) {
					fieldName = "religionName";
					groupby = "religionName ";
				} else if (CPSUtils.compareStrings(fields[i], "Quarter No")) {
					fieldName = "quarterNumber";
					groupby = "quarterNumber";
				} else if (CPSUtils.compareStrings(fields[i], "PH")) {
					fieldName = "phName";
					groupby = "phName";
				} else if (CPSUtils.compareStrings(fields[i], "CL")) {
					fieldName = "casualLeave";
					groupby = "casualLeave";
				} else if (CPSUtils.compareStrings(fields[i], "EL")) {
					fieldName = "earnedLeave";
					groupby = "earnedLeave";
				} else if (CPSUtils.compareStrings(fields[i], "HPL")) {
					fieldName = "halfPayLeave";
					groupby = "halfPayLeave";
				} else if (CPSUtils.compareStrings(fields[i], "Designation")) {
					fieldName = "designation";
					groupby = "designation";
				}
				if (i == 0) {
					sb1.append(fieldName);
					sb2.append(groupby);
				} else {
					sb1.append("," + fieldName);
					sb2.append("," + groupby);
				}
			}
			values[0] = sb1.toString();
			values[1] = sb2.toString();
		} catch (Exception e) {
			throw e;
		}
		return values;
	}

	public String[] getConditions(JSONObject json) throws Exception {
		StringBuffer condision = null;
		StringBuffer orders = null;
		String[] values = new String[3];
		try {
			condision = new StringBuffer();
			orders = new StringBuffer();
			for (int i = 0; i < json.length() - 1; i++) {
				JSONObject rowjson = (JSONObject) json.get(String.valueOf(i));
				if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "sfid")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (employee.sfid like '%" + rowjson.getString("value") + "%' or employee.sfid like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (employee.sfid like '" + rowjson.getString("value") + "%' or employee.sfid like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (employee.sfid = '" + rowjson.getString("value") + "' or employee.sfid = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",employee.sfid asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",employee.sfid desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "name")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (employee.name_in_service_book like INITCAP('%" + rowjson.getString("value") + "%') or employee.name_in_service_book like upper('%"
								+ rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (employee.name_in_service_book like INITCAP('" + rowjson.getString("value") + "%') or employee.name_in_service_book like upper('"
								+ rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (employee.name_in_service_book = INITCAP('" + rowjson.getString("value") + "') or employee.name_in_service_book = upper('"
								+ rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",employee.name_in_service_book asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",employee.name_in_service_book desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "desig")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (dm.name like '%" + rowjson.getString("value") + "%' or dm.name like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (dm.name like '" + rowjson.getString("value") + "%' or dm.name like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (dm.id = '" + rowjson.getString("value") + "' or dm.id = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",dm.name asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",dm.name desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "dob")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "equal"))
						condision.append(" and employee.dob='" + rowjson.getString("value") + "'");
					else
						condision.append(" and employee.dob between '" + rowjson.getString("from") + "' and '" + rowjson.getString("to") + "'");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",employee.dob asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",employee.dob desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "dojasl")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "equal"))
						condision.append(" and employee.DOJ_ASL='" + rowjson.getString("value") + "'");
					else
						condision.append(" and employee.DOJ_ASL between '" + rowjson.getString("from") + "' and '" + rowjson.getString("to") + "'");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",employee.DOJ_ASL asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",employee.DOJ_ASL desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "groupName")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (gm.name like '%" + rowjson.getString("value") + "%' or gm.name like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (gm.name like '" + rowjson.getString("value") + "%' or gm.name like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (gm.id = '" + rowjson.getString("value") + "' or gm.id = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",gm.name asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",gm.name desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "division")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (a.division like '%" + rowjson.getString("value") + "%' or a.division like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (a.division like '" + rowjson.getString("value") + "%' or a.division like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (a.division = '" + rowjson.getString("value") + "' or a.division = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",a.division asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",a.division desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "category")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (cm.name like '%" + rowjson.getString("value") + "%' or cm.name like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (cm.name like '" + rowjson.getString("value") + "%' or cm.name like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value"))){
						//condision.append("  and  (cm.id = '" + rowjson.getString("value") + "' or cm.id = upper('" + rowjson.getString("value") + "')) ");
						condision.append("  and  (cm.id = (select scm.category_id from sub_category_master scm where scm.id="  +rowjson.getString("value")+ "))");
					}
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",cm.name asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",cm.name desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "ph")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (ph.name like '%" + rowjson.getString("value") + "%' or ph.name like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (ph.name like '" + rowjson.getString("value") + "%' or ph.name like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (ph.id = '" + rowjson.getString("value") + "' or ph.id = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",ph.name asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",ph.name desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "religion")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (rm.name like '%" + rowjson.getString("value") + "%' or rm.name like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (rm.name like '" + rowjson.getString("value") + "%' or rm.name like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (rm.id = '" + rowjson.getString("value") + "' or rm.id = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",rm.name asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",rm.name desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "cghs")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (employee.DISPENSARY_NUMBER like '%" + rowjson.getString("value") + "%' or employee.DISPENSARY_NUMBER like upper('%" + rowjson.getString("value")
								+ "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (employee.DISPENSARY_NUMBER like '" + rowjson.getString("value") + "%' or employee.DISPENSARY_NUMBER like upper('" + rowjson.getString("value")
								+ "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (employee.DISPENSARY_NUMBER = '" + rowjson.getString("value") + "' or employee.DISPENSARY_NUMBER = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",employee.DISPENSARY_NUMBER asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",employee.DISPENSARY_NUMBER desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "quarter")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (quarter.quarter_number like '%" + rowjson.getString("value") + "%' or quarter.quarter_number like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (quarter.quarter_number like '" + rowjson.getString("value") + "%' or quarter.quarter_number like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append("  and  (quarter.quarter_number = '" + rowjson.getString("value") + "' or quarter.quarter_number = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",quarter.quarter_number asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",quarter.quarter_number desc");
					}
				} else if (CPSUtils.compareStrings(rowjson.getString("fieldname"), "qualification")) {
					if (CPSUtils.compareStrings(rowjson.getString("search"), "contains") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and  (quaType.name like '%" + rowjson.getString("value") + "%' or quaType.name like upper('%" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "starts") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and (quaType.name like '" + rowjson.getString("value") + "%' or quaType.name like upper('" + rowjson.getString("value") + "%')) ");
					else if (CPSUtils.compareStrings(rowjson.getString("search"), "equal") && !CPSUtils.isNullOrEmpty(rowjson.getString("value")))
						condision.append(" and (quaType.id = '" + rowjson.getString("value") + "' or quaType.id = upper('" + rowjson.getString("value") + "')) ");
					if (CPSUtils.compareStrings(rowjson.getString("order"), "asc")) {
						orders.append(",quaType.name asc");
					} else if (CPSUtils.compareStrings(rowjson.getString("order"), "desc")) {
						orders.append(",quaType.name desc");
					}
				}
			}
			values[0] = condision.toString();
			values[1] = orders.toString();

		} catch (Exception e) {
			throw e;
		}
		return values;
	}
	
	
		@SuppressWarnings("unchecked")
		@Override
		public List<String> getEmpNameList(String empName) throws Exception {
		   List<String> list = null;

		   Session  session = null;
		   try{
		     String empName2 = 	CPSUtils.convertFirstLetterToUpperCase(empName);
			   session = hibernateUtils.getSession();
		    	 list = session.createSQLQuery("select NAME_IN_SERVICE_BOOK from Emp_Master where  NAME_IN_SERVICE_BOOK like '%"+empName2+"%'  ").setFlushMode(FlushMode.ALWAYS).setFetchSize(50).list();
		  
		   List<String>    	 list2 = session.createSQLQuery("select NAME_IN_SERVICE_BOOK from Emp_Master where  NAME_IN_SERVICE_BOOK like '%"+empName+"%'  ").setFlushMode(FlushMode.ALWAYS).setFetchSize(50).list();
		    	 
		   list.addAll(list2) ;	 
		   
		   if(list.size() == 0) {
		    	Locale l1 =  Locale.getDefault();
		    	empName.toUpperCase(l1);
		    	list = session.createSQLQuery("select NAME_IN_SERVICE_BOOK from Emp_Master where FIRST_NAME like '%"+empName+"%'").list();
		    }
		  
		   }catch (Exception e) {
				e.printStackTrace();
		    	 throw e;
		}
	  return list;
		}
	
	
	
}
