package com.callippus.web.ltc.dao.approval;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.CDADetailsDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.FinanceDetailsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.CancelLeaveRequestBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestCommonBean;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.dto.LeaveEncashmentDTO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;
import com.callippus.web.ltc.dto.LtcAdvanceRequestDTO;
import com.callippus.web.ltc.dto.LtcApprovalRequestDTO;
import com.callippus.web.ltc.dto.LtcBlockMasterDTO;
import com.callippus.web.ltc.dto.LtcEncashmentDTO;
import com.callippus.web.ltc.dto.LtcMemberDetailsDTO;
import com.callippus.web.ltc.dto.LtcReimbursementDTO;
import com.callippus.web.ltc.dto.LtcRequestProcessBean;

@SuppressWarnings("serial")
@Service
public class SQLLtcApprovalRequestDAO implements ILtcApprovalRequestDAO, Serializable {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;


	public EmployeeBean getEmployeeDetails(String sfID) throws Exception {
		Session session = null;
		EmployeeBean empBean = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();
			/*			sql="select emp.handicap_id handicap,emp.sfid sfid,emp.designation_id designationId,emp.directorate_id directorateId,emp.office_id officeId,to_char(emp.doj_drdo,'dd-Mon-yyyy') dojDrdo,to_char(emp.dob+((365*60)+15),'dd-Mon-yyyy') retirementDate,d1.department_name directorateName,d.department_name divisionName,"
							+"emp.name_in_service_book name,emp.internal_phone_no internalNo,dm.name designationName,(select grade_pay from emp_payment_details	where sfid=emp.sfid) as gradePay,"
							+"etm.name employmentTypeId,emp.employment_type_id employmentId from designation_master dm, emp_master emp, org_role_instance org,org_role_instance org1,departments_master d,departments_master d1,employment_type_master etm"
							+" where emp.sfid=? and emp.office_id=org.org_role_id and emp.directorate_id=org1.org_role_id  and org1.department_id=d1.department_id and d.status=1"
							+" and org.department_id=d.department_id and dm.status=1 and dm.id=emp.designation_id and etm.id=emp.employment_type_id";
			*/sql = "select (select value from configuration_details where name='LTC_PRIOR_ADVANCE_REQUEST_DAYS') ltcAdvanceDays,to_char((sysdate+(select value from configuration_details where name='LTC_PRIOR_ADVANCE_REQUEST_DAYS')),'dd-Mon-yyyy') checkDate,emp.handicap_id handicap,emp.sfid sfid,emp.designation_id designationId,emp.directorate_id directorateId,emp.office_id officeId, "
					+ "to_char(emp.doj_drdo,'dd-Mon-yyyy') dojDrdo,to_char(emp.dob+((365*60)+15),'dd-Mon-yyyy') retirementDate,dept1.department_name directorateName "
					+ ",dept2.department_name divisionName,emp.name_in_service_book name,emp.internal_phone_no internalNo,dm.name designationName,epd.grade_pay gradePay,epd.basic_pay basicPay "
					+ ",etm.name employmentTypeId,emp.employment_type_id employmentId "
					+ "from emp_master emp,org_role_instance ori1,org_role_instance ori2,departments_master dept1,departments_master dept2,designation_master dm "
					+ ",employment_type_master etm,emp_payment_details epd "
					+ "where emp.sfid=? and dept1.status=1 and dept2.status=1 and ori1.status=1 and ori2.status=1 and emp.directorate_id=ori1.org_role_id "
					+ "and emp.office_id=ori2.org_role_id and ori1.department_id=dept1.department_id and ori2.department_id=dept2.department_id and dm.status=1 "
					+ "and emp.designation_id=dm.id and etm.status=1 and etm.id=emp.employment_type_id and epd.sfid=emp.sfid";
			empBean = (EmployeeBean) session.createSQLQuery(sql).addScalar("ltcAdvanceDays").addScalar("checkDate").addScalar("sfid").addScalar("designationId", Hibernate.STRING).addScalar("officeId", Hibernate.STRING).addScalar("directorateId",
					Hibernate.STRING).addScalar("dojDrdo").addScalar("retirementDate", Hibernate.STRING).addScalar("directorateName").addScalar("divisionName")
					.addScalar("handicap", Hibernate.INTEGER).addScalar("name").addScalar("internalNo").addScalar("designationName").addScalar("gradePay", Hibernate.STRING).addScalar("basicPay", Hibernate.STRING).addScalar(
							"employmentTypeId").addScalar("employmentId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).setString(0, sfID).uniqueResult();

		} catch (Exception e) {
			throw e;
		} 
		return empBean;
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getLtcLeaveTypeList(String sfID,Date departureDate,Date returnDate) throws Exception {
		List<KeyValueDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			
			String sql = "select lrd.request_id as key,ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' as name " 
					+"from leave_request_details lrd,leave_type_master ltm where lrd.sfid=? and lrd.status not in(6,9)  "
					+"and  ltm.id=lrd.leave_type_id and lrd.leave_type_id!=? " 
					+"and (? between lrd.from_date and lrd.to_date or ? between lrd.from_date and lrd.to_date or lrd.from_date between ? and ? " 
					+"and lrd.to_date between ? and ?)and lrd.request_id not in(select leave_request_id from ltc_request_details where leave_request_id is not null and status not in(6,9)"
					+"union select leave_request_id from ltc_advance_request_details where leave_request_id is not null and status not in(6,9)) order by name";
		
			list = session.createSQLQuery(sql).addScalar("name").addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, sfID).setString(1, CPSConstants.CCL).setDate(2, departureDate).setDate(3, returnDate).setDate(4, departureDate).setDate(5, returnDate).setDate(6, departureDate).setDate(7, returnDate).list();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

	public AddressBean getHometownAddress(String sfId) throws Exception {
		AddressBean addBean = null;
		
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			addBean = (AddressBean) session.createCriteria(AddressBean.class).add(Expression.eq("status", 1)).add(Expression.or(Expression.eq("addressTypeId", "3"),Expression.eq("addressTypeId", "4"))).add(Expression.eq("sfid", sfId)).uniqueResult();
		     
		} catch (Exception e) {
			throw e;
		} 
		return addBean;
	}

	@SuppressWarnings("unchecked")
	public List<FamilyBean> getFamilyMemberDetails(String sfId) throws Exception {
		List<FamilyBean> list = null;
		Session session = null;
		try {
			//String query = "select fd.id id,fd.name name,to_char(fd.dob,'dd-Mon-yyyy')as dob, case when fd.dob  is null then fd.age else round(months_between(sysdate,fd.dob)/12)||'' end age ,frm.name relation from family_details fd,family_relation_master frm where fd.sfid=? and fd.status=1 and fd.ltc_facility='Y' and frm.id=fd.relation_id order by frm.order_no";
			 /*String query ="SELECT fd.id id, to_char(sysdate) as toDay, fd.name name,  TO_CHAR(fd.dob,'dd-Mon-yyyy')AS dob,  CASE    WHEN fd.dob IS NULL    THEN fd.age    else         case when TRUNC(months_between(sysdate,fd.dob)) between 0 and 155 "+
                     " then    trunc(months_between(sysdate,fd.dob)/12)||' Years and '  || (trunc(months_between(sysdate,fd.dob) - trunc(months_between(sysdate,fd.dob)/12)*12)) ||' Months'"+
                     "  else    trunc(months_between(sysdate,fd.dob)/12)||' Years'    end       end age ,  frm.name relation from family_details fd,  family_relation_master frm WHERE fd.sfid      =? AND fd.status      =1 AND fd.ltc_facility='Y' and frm.id         =fd.relation_id order by frm.order_no";*/
		/*	String query ="SELECT fd.id id, to_char(sysdate) as toDay, fd.name name, ( SELECT  rtrim (xmlagg (xmlelement (e, TO_CHAR(from_date,'yyyy')  ||'-' ||to_char(to_date , 'yy') || ',')).extract ('//text()'), ',')	  AS availyears from ltc_block_year_master lbym where id in (SELECT ltd.ltc_block_year_id   "
					     + " from ltc_txn_details ltd where family_member_id = (fd.id) and ltd.status    =1)) AS ltcAvail, TO_CHAR(fd.dob,'dd-Mon-yyyy')AS dob,  CASE    WHEN fd.dob IS NULL    THEN fd.age    else         case when TRUNC(months_between(sysdate,fd.dob)) between 0 and 155 " +
                             " then    trunc(months_between(sysdate,fd.dob)/12)||' Years and '  || (trunc(months_between(sysdate,fd.dob) - trunc(months_between(sysdate,fd.dob)/12)*12)) ||' Months' " +
                            "  else    trunc(months_between(sysdate,fd.dob)/12)||' Years'    end       end age ,  frm.name relation from family_details fd,  family_relation_master frm WHERE fd.sfid      =? AND fd.status      =1 AND fd.ltc_facility='Y' and frm.id         =fd.relation_id order by frm.order_no";*/
			
			String query ="SELECT fd.id id, TO_CHAR(sysdate) AS toDay, fd.name name, (select case when  rtrim (xmlagg (xmlelement (e, to_char(from_date,'yy')  || ',')).extract ('//text()'), ',')=rtrim (xmlagg (xmlelement (e, to_char(to_date , 'yy')  || ',')).extract ('//text()'), ',') then  rtrim (xmlagg (xmlelement (e, to_char(from_date,'yyyy') || ',')).extract ('//text()'), ',') else   " 
				        +  " rtrim (xmlagg (xmlelement (e, TO_CHAR(from_date,'yyyy')  ||'-' ||to_char(to_date , 'yy') || ',')).extract ('//text()'), ',') end    as availyears  FROM ltc_block_year_master lbyma  WHERE id IN    (SELECT ltd.ltc_block_year_id    FROM ltc_txn_details ltd    WHERE family_member_id = (fd.id)    AND ltd.status         =1    ) "
                       + "    ) AS ltcAvail,  TO_CHAR(fd.dob,'dd-Mon-yyyy')AS dob,  CASE    WHEN fd.dob IS NULL  THEN fd.age    ELSE      CASE        WHEN TRUNC(months_between(sysdate,fd.dob)) BETWEEN 0 AND 155    THEN TRUNC(months_between(sysdate,fd.dob)/12) ||' Years and '          || (trunc(months_between(sysdate,fd.dob) - trunc(months_between(sysdate,fd.dob)/12)*12))          ||' Months'    else trunc(months_between(sysdate,fd.dob)/12) ||' Years' "
                       + " end  end age ,  frm.name relation from family_details fd, family_relation_master frm WHERE fd.sfid      =? AND fd.status      =1 AND fd.ltc_facility='Y' AND frm.id         =fd.relation_id order by frm.order_no";
 
			
			
			session = hibernateUtils.getSession();
			list = session.createSQLQuery(query).addScalar("id",Hibernate.STRING).addScalar("name").addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("ltcAvail", Hibernate.STRING).addScalar("relation").addScalar("toDay").setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).setString(0, sfId).list();
			
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

	/**
	 * This method return BlockYear List based on the No of Years in service
	 */
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getLtcBlockYearsListOLD(String sfid,String district,LtcApplicationBean ltcBean) throws Exception {
		List<KeyValueDTO> list = null;
		Session session = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();
			
			sql="select case when" +
				" ((select to_char(from_date,'yyyy') sysFromYear from  ltc_block_year_master where status=1 and ltc_block_id=3 and sysdate between from_date and to_date)-" +
				" (select to_char(to_date,'yyyy') ltcElgToYear from ltc_block_year_master where status=1 and ltc_block_id=3 and " +
				" (select (to_char(doj_govt,'yyyy')+1) ltcApplicableYear from emp_master where status=1 and sfid='"+sfid+"') between" +
				" to_char(from_date,'yyyy') and to_char(to_date,'yyyy')))>1" +
				" then 'Experienced'" +
				" when" +
				" ((select to_char(from_date,'yyyy') sysFromYear from  ltc_block_year_master where status=1 and ltc_block_id=3 and sysdate between from_date and to_date)-" +
				" (select to_char(to_date,'yyyy') ltcElgToYear from ltc_block_year_master where status=1 and ltc_block_id=3 and " +
				" (select (to_char(doj_govt,'yyyy')+1) ltcApplicableYear from emp_master where status=1 and sfid='"+sfid+"') between" +
				" to_char(from_date,'yyyy') and to_char(to_date,'yyyy')))<1" +
				" then 'PresentBlockYearFresher'" +
				" else 'PreviousBlockYearFresher' end result from dual";
			String result = session.createSQLQuery(sql).uniqueResult().toString();
			if (CPSUtils.compareStrings(result,"Experienced")) {
				if(!(district.equals("6")||district.equals("18"))){
					//show only current and next two_year_blocks
					sql = "SELECT id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value FROM (select id,from_date,to_date from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(from_date,'yyyy')>=" +
					" (SELECT to_char(from_date,'yyyy') FROM ltc_block_year_master WHERE status =1 AND ltc_block_id=? AND to_char(sysdate,'yyyy') BETWEEN to_char(from_date,'yyyy') AND to_char(to_date,'yyyy'))" +
					" ) WHERE rownum<=2";	
					list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
							0, CPSConstants.TWOYEARBLOCKID).setString(1, CPSConstants.TWOYEARBLOCKID).list();
					
					//add extension block start
					if(list.size()>0){
						String year = list.get(0).getValue().split("-")[0];
						String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
						List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
								0, year).setString(1, CPSConstants.TWOYEARBLOCKID).list();
						if(list1.size()>0){
						list.addAll(list1);	
						}
					}
					//add extension block end
				}else{
					//show only last year of four_year_block
					sql = "select id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value  from (select id,from_date,to_date,rownum rowval from ltc_block_year_master where status=1" +
					" and ltc_block_id=? and to_char(to_date, 'yyyy')>=(select to_char(sysdate, 'yyyy') from dual)) where rowval<=2";
					list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
							0, CPSConstants.FOURYEARBLOCKID).list();	
					//add extension block start
					if(list.size()>0){
						String year = list.get(0).getValue().split("-")[0];
						String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
						List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
								0, year).setString(1, CPSConstants.FOURYEARBLOCKID).list();
						if(list1.size()>0){
						list.addAll(list1);	
						}
					}
					//add extension block end
				}
			
			}else if (CPSUtils.compareStrings(result, "PresentBlockYearFresher")) {
				// Check Whether Last year of current block year
				sql = "select count(*) from (select from_date,rownum rowval from ltc_block_year_master where status=1 and ltc_block_id=?" +
				  " and four_year_block_id=(select id from ltc_block_year_master where status=1 and ltc_block_id=?" +
				  " and sysdate between from_date and to_date)) where rowval=4 and  to_char(from_date, 'yyyy')= to_char(sysdate, 'yyyy')";
				result = session.createSQLQuery(sql).setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.FOURYEARBLOCKID).uniqueResult().toString();
				
				if (CPSUtils.compareStrings(result, "1")) {
					sql="select * from emp_master where status=1 and sfid='"+sfid+"' and to_char(doj_govt,'yyyy')=to_char(sysdate,'yyyy')";
					Object flag = session.createSQLQuery(sql).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(flag)){
						if(district.equals("6")||district.equals("18")){
							// Show  next eligible four_year_blocks
							sql = "select id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value from (select id,from_date,to_date,rownum rowval " +
							  " from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(sysdate,'yyyy')<to_char(from_date,'yyyy')" +
							  " ) where rowval in(1,2)";
						list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
								0, CPSConstants.FOURYEARBLOCKID).list();	
						//add extension block start
						if(list.size()>0){
							String year = list.get(0).getValue().split("-")[0];
							String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
							List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
									0, year).setString(1, CPSConstants.FOURYEARBLOCKID).list();
							if(list1.size()>0){
							list.addAll(list1);	
							}
						}
						//add extension block end
						}else{
							// Show  next eligible one_year_blocks
							sql = "SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM (select id,from_date from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(from_date,'yyyy')>" +
							  " (SELECT to_char(from_date,'yyyy') FROM ltc_block_year_master WHERE status =1 AND ltc_block_id=? AND to_char(sysdate,'yyyy') BETWEEN to_char(from_date,'yyyy') AND to_char(to_date,'yyyy'))" +
							  " ) WHERE rownum<=2";	
							list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
								.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.ONEYEARBLOCKID).list();
							//add extension block start
							if(list.size()>0){
								String year = list.get(0).getValue();
								String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
								List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
										0, year).setString(1, CPSConstants.ONEYEARBLOCKID).list();
								if(list1.size()>0){
								list.addAll(list1);	
								}
							}
							//add extension block end
						}
						
					}else{
						// Show  next eligible four_year_blocks
						if(district.equals("6")||district.equals("18")){
							sql = "select id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value from (select id,from_date,to_date,rownum rowval " +
							  " from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(sysdate,'yyyy')<=to_char(to_date,'yyyy')" +
							  " ) where rowval in(1,2)";
						list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
								0, CPSConstants.FOURYEARBLOCKID).list();	
						//add extension block start
						if(list.size()>0){
							String year = list.get(0).getValue().split("-")[0];
							String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
							List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
									0, year).setString(1, CPSConstants.FOURYEARBLOCKID).list();
							if(list1.size()>0){
							list.addAll(list1);	
							}
						}
						//add extension block end
						}else{
							// Show current & next one_year_blocks
							sql = "SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM (select id,from_date from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(from_date,'yyyy')>=" +
							  " (SELECT to_char(from_date,'yyyy') FROM ltc_block_year_master WHERE status =1 AND ltc_block_id=? AND to_char(sysdate,'yyyy') BETWEEN to_char(from_date,'yyyy') AND to_char(to_date,'yyyy'))" +
							  " ) WHERE rownum<=2";	
							list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
								.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.ONEYEARBLOCKID).list();
							//add extension block start
							if(list.size()>0){
								String year = list.get(0).getValue();
								String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
								List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
										0, year).setString(1, CPSConstants.ONEYEARBLOCKID).list();
								if(list1.size()>0){
								list.addAll(list1);	
								}
							}
							//add extension block end
						}
					}
				
				}else{
					// Show  next eligible four_year_blocks
					if(district.equals("6")||district.equals("18")){
						sql = "select id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value from (select id,from_date,to_date,rownum rowval " +
						  " from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(sysdate,'yyyy')<=to_char(to_date,'yyyy')" +
						  " ) where rowval in(1,2)";
					list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
							0, CPSConstants.FOURYEARBLOCKID).list();	
					//add extension block start
					if(list.size()>0){
						String year = list.get(0).getValue().split("-")[0];
						String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
						List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
								0, year).setString(1, CPSConstants.FOURYEARBLOCKID).list();
						if(list1.size()>0){
						list.addAll(list1);	
						}
					}
					//add extension block end
					}else{
						// Show current & next one_year_blocks
						sql = "SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM (select id,from_date from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(from_date,'yyyy')>=" +
						  " (SELECT to_char(from_date,'yyyy') FROM ltc_block_year_master WHERE status =1 AND ltc_block_id=? AND to_char(sysdate,'yyyy') BETWEEN to_char(from_date,'yyyy') AND to_char(to_date,'yyyy'))" +
						  " ) WHERE rownum<=2";	
						list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
							.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.ONEYEARBLOCKID).list();
						//add extension block start
						if(list.size()>0){
							String year = list.get(0).getValue();
							String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
							List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
									0, year).setString(1, CPSConstants.ONEYEARBLOCKID).list();
							if(list1.size()>0){
							list.addAll(list1);	
							}
						}
						//add extension block end
					}
					
				}
			}else if (CPSUtils.compareStrings(result, "PreviousBlockYearFresher")) {
				
				if(!(district.equals("6")||district.equals("18"))){
					
					// Check Whether Last year of current block year
					sql = "select count(*) from (select from_date,rownum rowval from ltc_block_year_master where status=1 and ltc_block_id=?" +
					  " and four_year_block_id=(select id from ltc_block_year_master where status=1 and ltc_block_id=?" +
					  " and sysdate between from_date and to_date)) where rowval=4 and  to_char(from_date, 'yyyy')= to_char(sysdate, 'yyyy')";
					result = session.createSQLQuery(sql).setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.FOURYEARBLOCKID).uniqueResult().toString();
					
					if (CPSUtils.compareStrings(result, "1")) {
						// Last year of current block year
						// So we should show current one one_year_ block & next one two_year_block
						sql = "select id key,fromDate value from ( select id,to_char(from_date,'YYYY') fromDate from ltc_block_year_master where status=1 and ltc_block_id=? and sysdate between from_date and to_date union "
								+ "select id,fromDate from(select id,to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') fromDate,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date>sysdate) where rowno=1) order by fromDate";
						list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
								.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.TWOYEARBLOCKID).list();
						//add extension block start
						if(list.size()>0){
							String year = list.get(0).getValue();
							String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
							List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
									0, year).setString(1, CPSConstants.ONEYEARBLOCKID).list();
							if(list1.size()>0){
							list.addAll(list1);	
							}
						}
						//add extension block end
					} else {
						// Show current & next one_year_blocks
						sql = "SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM (select id,from_date from ltc_block_year_master where status=1 and ltc_block_id=? and to_char(from_date,'yyyy')>=" +
						  " (SELECT to_char(from_date,'yyyy') FROM ltc_block_year_master WHERE status =1 AND ltc_block_id=? AND to_char(sysdate,'yyyy') BETWEEN to_char(from_date,'yyyy') AND to_char(to_date,'yyyy'))" +
						  " ) WHERE rownum<=2";	
						list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
							.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.ONEYEARBLOCKID).list();
						//add extension block start
						if(list.size()>0){
							String year = list.get(0).getValue();
							String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
							List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
									0, year).setString(1, CPSConstants.ONEYEARBLOCKID).list();
							if(list1.size()>0){
							list.addAll(list1);	
							
							}
						}
						//add extension block end
					  }
				}else{
					//show only last year of four_year_block
					sql = "select id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value  from (select id,from_date,to_date,rownum rowval from ltc_block_year_master where status=1" +
							" and ltc_block_id=? and to_char(to_date, 'yyyy')>=(select to_char(sysdate, 'yyyy') from dual)) where rowval<=2";
					list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
							0, CPSConstants.FOURYEARBLOCKID).list();	
					//add extension block start
					if(list.size()>0){
						String year = list.get(0).getValue().split("-")[0];
						String qry ="SELECT id KEY,TO_CHAR(from_date,'YYYY')||'-'||TO_CHAR(to_date,'YY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE<=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? and ltc_block_id=?";
						List<KeyValueDTO> list1 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
								0, year).setString(1, CPSConstants.FOURYEARBLOCKID).list();
						if(list1.size()>0){
						list.addAll(list1);	
						}
					}
					//add extension block end
					
				}
			
	
			}
				
			//for Amendment
			if (CPSUtils.compareStrings(ltcBean.getTypeValue(), CPSConstants.LTC_ADVANCE_AMENDMENT)
					|| CPSUtils.compareStrings(ltcBean.getTypeValue(), CPSConstants.LTC_APPROVAL_AMENDMENT)) {
				int u = 0;
				for (KeyValueDTO kvd : list) {
					if (CPSUtils.compareStrings(kvd.getKey(), ltcBean.getLtcBlockYearId())) {
						u++;
					}
				}
				if (u == 0) {
					KeyValueDTO kvdNew = new KeyValueDTO();
					kvdNew.setKey(ltcBean.getLtcBlockYearId());
					kvdNew.setValue(ltcBean.getLtcAmendmentBlockYear());
					list.add(kvdNew);
				}
			}
		
			//////////////////Previous code Start////////////////////////////////////

		/*	// get whether the employee is experience or not, If the employee crosses two block years we should treat as experienced
			sql = "select case when (select count(*) from ltc_block_year_master where from_date <= (select lbym.from_date from ltc_block_year_master lbym where status=1 and (select doj_drdo from emp_master where sfid=?) "
					+ "between lbym.from_date and lbym.to_date and lbym.ltc_block_id=1) and status=1 and ltc_block_id=?)>2 then ? else (case when (select to_char(doj_drdo,'YYYY') from emp_master where sfid=?)<2006 then ? else ? end) end from dual";
			String result = session.createSQLQuery(sql).setString(0, sfid).setString(1, CPSConstants.FOURYEARBLOCKID).setString(2, CPSConstants.EXPERIENCE).setString(3, sfid).setString(4,
					CPSConstants.EXPERIENCE).setString(5, CPSConstants.FRESHER).uniqueResult().toString();
		
			sql = "select case when (select count(*) from ltc_block_year_master where from_date <= (select lbym.from_date from ltc_block_year_master lbym where status=1 and (select doj_drdo from emp_master where sfid=?) between lbym.from_date and lbym.to_date and lbym.ltc_block_id=?) " 
					+"and status=1 and ltc_block_id=?)>2 then ? else (case when (select to_char(doj_drdo,'YYYY') from emp_master where sfid=?)<2006 and (select to_char(to_date,'yyyy') from (select from_date,to_date,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where ltc_block_id=? " 
					+"and four_year_block_id=(select id from ltc_block_year_master where sysdate between from_date and to_date and ltc_block_id=? )) where rowno=4)!=to_char(sysdate,'YYYY') then ? else ? end) end from dual";
						
			 result = session.createSQLQuery(sql).setString(0, sfid).setString(1, CPSConstants.FOURYEARBLOCKID).setString(2, CPSConstants.FOURYEARBLOCKID).setString(3, CPSConstants.EXPERIENCE).setString(4, sfid).setString(5,
				CPSConstants.ONEYEARBLOCKID).setString(6, CPSConstants.FOURYEARBLOCKID).setString(7, CPSConstants.EXPERIENCE).setString(8, CPSConstants.FRESHER).uniqueResult().toString();
		
			if (CPSUtils.compareStrings(result, CPSConstants.EXPERIENCE)) {
				// check current year is even or odd
				sql = "select case when (select mod((select to_char(sysdate,'YYYY') from dual),2) from dual)=0 then ? else ? end from dual";
				result = session.createSQLQuery(sql).setString(0, CPSConstants.EVEN).setString(1, CPSConstants.ODD).uniqueResult().toString();
				if (CPSUtils.compareStrings(result, CPSConstants.EVEN)) {
					// If the current year is even then we should show the current & previous two year blocks
					sql = "select id key,to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value from (select id,from_date,to_date,ROW_NUMBER() OVER (ORDER BY from_date DESC) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date<=(select from_date from ltc_block_year_master where status=1 and ltc_block_id=? and sysdate between from_date and to_date)) where rowno<=2";
				} else {
					// If the current year is odd then we should show the current & next two year blocks
					sql = "select id key,to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value from (select id,from_date,to_date,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date>=( select from_date from ltc_block_year_master where status=1 and ltc_block_id=? and sysdate between from_date and to_date)) where rowno<=2";
				}
				list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
						0, CPSConstants.TWOYEARBLOCKID).setString(1, CPSConstants.TWOYEARBLOCKID).list();
			} else {
				if(district.equals("6")||district.equals("18")){
					// check current year is even or odd
					sql = "select case when (select mod((select to_char(sysdate,'YYYY') from dual),2) from dual)=0 then ? else ? end from dual";
					result = session.createSQLQuery(sql).setString(0, CPSConstants.EVEN).setString(1, CPSConstants.ODD).uniqueResult().toString();
					if (CPSUtils.compareStrings(result, CPSConstants.EVEN)) {
						// If the current year is even then we should show the current & previous two year blocks
						sql = "select id key,to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value from (select id,from_date,to_date,ROW_NUMBER() OVER (ORDER BY from_date DESC) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date<=(select from_date from ltc_block_year_master where status=1 and ltc_block_id=? and sysdate between from_date and to_date)) where rowno<=2";
					} else {
						// If the current year is odd then we should show the current & next two year blocks
						sql = "select id key,to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value from (select id,from_date,to_date,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date>=( select from_date from ltc_block_year_master where status=1 and ltc_block_id=? and sysdate between from_date and to_date)) where rowno<=2";
					}
					list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(
							0, CPSConstants.TWOYEARBLOCKID).setString(1, CPSConstants.TWOYEARBLOCKID).list();
				}else{
				// Fresher
				// check whether the employee doj_dro is in the current four year block or not
				sql = "select count(*) from ltc_block_year_master where status=1 and (select doj_drdo from emp_master where sfid=?) between from_date and to_date and ltc_block_id=? and sysdate between from_date and to_date";
				result = session.createSQLQuery(sql).setString(0, sfid).setString(1, CPSConstants.FOURYEARBLOCKID).uniqueResult().toString();
				if (CPSUtils.compareStrings(result, "1")) {
					// Current block year
					// Show current & next year one year block
					sql = "select id key,fromDate value from (select id,to_char(from_date,'YYYY') fromDate,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date>=(select from_date from ltc_block_year_master where status=1 and ltc_block_id=? and sysdate between from_date and to_date)) where rowno<=2";
					list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
							.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.ONEYEARBLOCKID).list();
				} else {
					// Previous block year
					// First get whether the current year is the last year of this four year block or not
					sql = "select count(*) from(select to_char(from_date,'YYYY') fromDate,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and four_year_block_id=(select id from ltc_block_year_master where status=1 and ltc_block_id=? "
							+ "and sysdate between from_date and to_date)) where fromDate=to_char(sysdate,'YYYY') and rowno=4";
					result = session.createSQLQuery(sql).setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.FOURYEARBLOCKID).uniqueResult().toString();
					if (CPSUtils.compareStrings(result, "1")) {
						// Last year of current block year
						// So we should show current one one_year_ block & next one two_year_block
						sql = "select id key,fromDate value from ( select id,to_char(from_date,'YYYY') fromDate from ltc_block_year_master where status=1 and ltc_block_id=? and sysdate between from_date and to_date union "
								+ "select id,fromDate from(select id,to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') fromDate,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date>sysdate) where rowno=1) order by fromDate";
						list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
								.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.TWOYEARBLOCKID).list();
					} else {
						// Show current & next one year blocks
						sql = "select id key,to_char(from_date,'YYYY') value from(select id,from_date,ROW_NUMBER() OVER (ORDER BY from_date) rowno from ltc_block_year_master where status=1 and ltc_block_id=? and from_date>=(select from_date from ltc_block_year_master where status=1 and ltc_block_id=? "
								+ "and sysdate between from_date and to_date)) where rowno<=2";
						list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
								.setString(0, CPSConstants.ONEYEARBLOCKID).setString(1, CPSConstants.ONEYEARBLOCKID).list();
					}
				}
			}}*/
			
			//////////////////Previous code End////////////////////////////////////
		
			
		/*	if (CPSUtils.compareStrings(ltcBean.getTypeValue(), CPSConstants.LTC_ADVANCE_AMENDMENT)
					|| CPSUtils.compareStrings(ltcBean.getTypeValue(), CPSConstants.LTC_APPROVAL_AMENDMENT)) {
				int u = 0;
				for (KeyValueDTO kvd : list) {
					if (CPSUtils.compareStrings(kvd.getKey(), ltcBean.getLtcBlockYearId())) {
						u++;
					}
				}
				if (u == 0) {
					KeyValueDTO kvdNew = new KeyValueDTO();
					kvdNew.setKey(ltcBean.getLtcBlockYearId());
					kvdNew.setValue(ltcBean.getLtcAmendmentBlockYear());
					list.add(kvdNew);
				}
			}*/
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	// This method return BlockYear List based on the No of Years in service(This Rules are applicable  from  2014-Dec  )
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getLtcBlockYearsList(String sfid,String district,LtcApplicationBean ltcBean) throws Exception {
		List<KeyValueDTO> list = null;		
		Session session = null;
		String sql = null;
		try {
			  session = hibernateUtils.getSession();
			  sql ="select id as key ,extract (year from from_date) as value,extract (year from to_date) as flag, extended_date as name from ltc_block_year_master where ltc_block_id=1 and ( extract (year from from_date) between (select extract ( year from  doj_govt )+1 fromyear  from emp_master  where sfid='"+sfid+"') "
                   + "  and (select extract ( year from  doj_govt)+8 fromyear  from emp_master  where sfid='"+sfid+"')) and extract (year from  SYSDATE )  <=  extract (year from from_date) ";
			  //list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			  list = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("flag", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			      /*if(list.size()>0){
			     	String year = list.get(0).getValue();
				    String qry ="SELECT id key, TO_CHAR(from_date,'YYYY') value FROM LTC_BLOCK_YEAR_MASTER WHERE SYSDATE   <=EXTENDED_DATE AND TO_CHAR(EXTENDED_DATE,'yyyy')=? AND ltc_block_id   =1"
			       List<KeyValueDTO> list0 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, year).list();
                        if(list0.size()>0){
                        list.addAll(list0);}
			       }*/
		     //if(list.size()<=3){}
		    if(!(district.equals("6")||district.equals("18")))
		    {
		    	sql ="select id as key,extract (year from from_date)  || '-'|| to_char(to_date(to_date),'yy')as value , extended_date as name from ltc_block_year_master where ltc_block_id=2 and ( extract (year from from_date) >= (select extract ( year from  doj_govt )+9 fromyear  from emp_master  where sfid='"+sfid+"')) "
              +  " and  (extract (year from sysdate) <= extract (year from from_date)) AND ROWNUM<6";
		    	List<KeyValueDTO>  list1 = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			      if(list1.size()>0)
			      {
				      String year = list1.get(0).getValue().split("-")[0];
				       //sql = "select id , from_date,extended_date FROM LTC_BLOCK_YEAR_MASTER where sysdate    <=extended_date and extract (year from to_date)='2019' and ltc_block_id                 =2;";
             		  	/*String  qry ="select id key , to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value, extended_date name from ltc_block_year_master "
                         + " where sysdate <=extended_date and ltc_block_id   =2 and extract (year from extended_date)=?  ";*/
				      String  qry ="select id key , to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value, extended_date name from ltc_block_year_master "
	                        + " where to_date(sysdate,'dd-mm-yyyy')  <= to_date(extended_date,'dd-mm-yyyy') and ltc_block_id   =2 and extract (year from extended_date)=?  AND( extract (YEAR FROM from_date) >=  (SELECT extract ( YEAR FROM doj_govt )+9 fromyear  from emp_master  WHERE sfid='"+sfid+"'  ))  ";
				       
			          List<KeyValueDTO> list3 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, year).list();
            		 if(list3.size()>0)
            		 {
			            //list1.addAll(list3);
            			 list.addAll(list3);
			          }
			        }
			      if(list1.size()>0)
			      {
						list.addAll(list1);
				  }
			      
		   }     
		if((district.equals("6")||district.equals("18"))){
			 sql ="select id as key,extract (year from from_date)  || '-'|| to_char(to_date(to_date),'yy') as value, extended_date  as name from ltc_block_year_master where ltc_block_id=3 and ( extract (year from from_date) >= (select extract ( year from  doj_govt )+9 fromyear  from emp_master  where sfid='"+sfid+"')) "
                +  " and  (extract (year from sysdate) <= extract (year from from_date))  AND ROWNUM<4" ;
			
			  List<KeyValueDTO>  list2 = session.createSQLQuery(sql).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			        if(list2.size()>0)
			        {
			       	 String year = list2.get(0).getValue().split("-")[0];
			       	//String qry = "select id key , to_char(from_date,'YYYY') ||'-'||to_char(to_date,'YY') value, extended_date name from ltc_block_year_master where sysdate  <=extended_date and ltc_block_id   =3 AND extract (YEAR FROM extended_date)='2018'";
				     String qry ="select id key , to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value, extended_date name from ltc_block_year_master where to_date(sysdate,'dd-mm-yyyy') <= to_date(extended_date,'dd-mm-yyyy') and ltc_block_id   =3 and extract (year from extended_date)=? AND ( extract (YEAR FROM from_date) >=  (SELECT extract ( YEAR FROM doj_govt )+9 fromyear  from emp_master  where sfid='"+sfid+"' ))";

				     List<KeyValueDTO> list4 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, year).list();
	                  if(list4.size()>0){		      
				      //list2.addAll(list4);
	                	  list.addAll(list4);
				      }
			        }
			        if(list2.size()>0){
						list.addAll(list2);
					}
			       
		}
			/*Set<KeyValueDTO> s = new HashSet<KeyValueDTO>(list);
			s.size();
			list.clear();
			int i=0;
			for(KeyValueDTO str:s){
				//String i="";
				//for(i=0;i<=s.size();i++){
					KeyValueDTO kvdNew = new KeyValueDTO();
					//kvdNew.get
					kvdNew.setKey(str.getKey());
					kvdNew.setValue(str.getValue());
					kvdNew.setName(str.getName());
					list.add(kvdNew);
				}
				*/
			//}
		//for Amendment
		if (CPSUtils.compareStrings(ltcBean.getTypeValue(), CPSConstants.LTC_ADVANCE_AMENDMENT)
				|| CPSUtils.compareStrings(ltcBean.getTypeValue(), CPSConstants.LTC_APPROVAL_AMENDMENT)) {
			int u = 0;
			for (KeyValueDTO kvd : list) {
				if (CPSUtils.compareStrings(kvd.getKey(), ltcBean.getLtcBlockYearId())) {
					u++;
				}
			}
			if (u == 0) {
				KeyValueDTO kvdNew = new KeyValueDTO();
				kvdNew.setKey(ltcBean.getLtcBlockYearId());
				kvdNew.setValue(ltcBean.getLtcAmendmentBlockYear());
				list.add(kvdNew);
			}
		}
		
			
	}
	
	
		catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<LtcMemberDetailsDTO> getLtcApprovedDetailsDAO(String sfID) throws Exception {
		List<LtcMemberDetailsDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			/*String query = "select * from (select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,lrd.cda_amount||'' cdaAmount,lrd.amendment_ref_request_id amendmentRefId,lrd.issued_amount||'' issuedAmount,lrd.do_part_id doPartId, to_char(sysdate,'dd-Mon-yyyy') currentDate,'ltcAdvance' requestType,sm.status,(select rtrim (xmlagg (xmlelement (e, fd.name || ',')).extract ('//text()'), ',')   from family_details fd,family_relation_master fr where fd.status=1"
					+ " and fd.id in(select family_member_id from ltc_txn_details where request_id=lrd.request_id) and fr.id=fd.relation_id) as familyMemberName,"
					+ " ltm.ltc_type as ltcType,case when (to_char(lym.from_date,'yyyy')=to_char(lym.to_date,'yyyy')) or to_date is null then (to_char(lym.from_date,'yyyy')) else (to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy')) end as blockYear,"
					+ " to_char(lrd.departure_date,'dd-Mon-yyyy') as journeyDate, lrd.request_id as requestId,"
					+ " to_char(lrd.applied_date,'dd-Mon-yyyy') as appliedDate,  case when (select reference_id from ltc_reimbursement_details reim where reim.reference_id=lrd.request_id and reim.status not in(6)) is null " 
					+ " then 'Sett/Refund' else '' end reimbursementFlag, '' refundFlag "
					+ " from ltc_advance_request_details lrd,ltc_type_master ltm,ltc_block_year_master lym,status_master sm "
					+ " where ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.sfid=? and sm.id=lrd.status and lrd.status not in(6,8)"
					+ " union "
					+ " select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,'' cdaAmount,lrd.amendment_ref_request_id amendmentRefId,'' issuedAmount,lrd.do_part_id doPartId,to_char(sysdate,'dd-Mon-yyyy') currentDate,'ltcApproval' requestType,sm.status,(select rtrim (xmlagg (xmlelement (e, fd.name || ',')).extract ('//text()'), ',')   from family_details fd,family_relation_master fr where fd.status=1"
					+ " and fd.id in(select family_member_id from ltc_txn_details where request_id=lrd.request_id) and fr.id=fd.relation_id) as familyMemberName,"
					+ " ltm.ltc_type as ltcType,case when (to_char(lym.from_date,'yyyy')=to_char(lym.to_date,'yyyy')) or to_date is null then (to_char(lym.from_date,'yyyy')) else (to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy')) end as blockYear,"
					+ " to_char(lrd.departure_date,'dd-Mon-yyyy') as journeyDate, lrd.request_id as requestId,"
					+ " to_char(lrd.creation_date,'dd-Mon-yyyy') as appliedDate, case when (select reference_request_id from ltc_advance_request_details  adv where adv.reference_request_id=lrd.request_id and adv.status=29) is null and (select reference_id from ltc_refund_request  refund where refund.reference_id=lrd.request_id and refund.status not in (6)) is null then 'Sett/Refund' else '' end reimbursementFlag,case when (select reference_id from ltc_reimbursement_details reim where reim.reference_id=lrd.request_id and reim.status=29) is null then 'Advance' else '' end refundFlag"
					+ " from ltc_request_details lrd,ltc_type_master ltm,ltc_block_year_master lym,status_master sm "
					+ " where lrd.advance_flag is null and ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.sfid=? and sm.id=lrd.status and lrd.status not in(6,8)) order by requestId desc";

			*/
			//code changed for cda_details table's cda_amount rakesh
			
			/*String query = "select * from (select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,cda.cda_amount||'' cdaAmount,lrd.amendment_ref_request_id amendmentRefId,lrd.issued_amount||'' issuedAmount,lrd.do_part_id doPartId, to_char(sysdate,'dd-Mon-yyyy') currentDate,'ltcAdvance' requestType,sm.status,(select rtrim (xmlagg (xmlelement (e, fd.name || ',')).extract ('//text()'), ',')   from family_details fd,family_relation_master fr where fd.status=1"
				+ " and fd.id in(select family_member_id from ltc_txn_details where request_id=lrd.request_id) and fr.id=fd.relation_id) as familyMemberName,"
				+ " ltm.ltc_type as ltcType,case when (to_char(lym.from_date,'yyyy')=to_char(lym.to_date,'yyyy')) or to_date is null then (to_char(lym.from_date,'yyyy')) else (to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy')) end as blockYear,"
				+ " to_char(lrd.departure_date,'dd-Mon-yyyy') as journeyDate, lrd.request_id as requestId,"
				+ " to_char(lrd.applied_date,'dd-Mon-yyyy') as appliedDate,  case when (select reference_id from ltc_reimbursement_details reim where reim.reference_id=request_id and reim.status not in(6)) is null " 
				+ " then 'Sett/Refund' else '' end reimbursementFlag, '' refundFlag "
				+ " from  ltc_type_master ltm,ltc_block_year_master lym,status_master sm, ltc_advance_request_details lrd left outer join emp_claim_details ecd on(lrd.request_id=ecd.request_id AND ECD.REQUEST_TYPE='advance')  left outer join finance_details on(finance_details.reference_id=ecd.id) left outer join cda_details cda on(finance_details.id =cda.reference_id) "
				+ " where ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.sfid=? and sm.id=lrd.status and lrd.status not in(6,8)"
				+ " union "
				+ " select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,'' cdaAmount,lrd.amendment_ref_request_id amendmentRefId,'' issuedAmount,lrd.do_part_id doPartId,to_char(sysdate,'dd-Mon-yyyy') currentDate,'ltcApproval' requestType,sm.status,(select rtrim (xmlagg (xmlelement (e, fd.name || ',')).extract ('//text()'), ',')   from family_details fd,family_relation_master fr where fd.status=1"
				+ " and fd.id in(select family_member_id from ltc_txn_details where request_id=lrd.request_id) and fr.id=fd.relation_id) as familyMemberName,"
				+ " ltm.ltc_type as ltcType,case when (to_char(lym.from_date,'yyyy')=to_char(lym.to_date,'yyyy')) or to_date is null then (to_char(lym.from_date,'yyyy')) else (to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy')) end as blockYear,"
				+ " to_char(lrd.departure_date,'dd-Mon-yyyy') as journeyDate, lrd.request_id as requestId,"
				+ " to_char(lrd.creation_date,'dd-Mon-yyyy') as appliedDate, case when (select reference_request_id from ltc_advance_request_details  adv where adv.reference_request_id=lrd.request_id and adv.status=29) is null and (select reference_id from ltc_refund_request  refund where refund.reference_id=lrd.request_id and refund.status not in (6)) is null then 'Sett/Refund' else '' end reimbursementFlag,case when (select reference_id from ltc_reimbursement_details reim where reim.reference_id=lrd.request_id and reim.status=29) is null then 'Advance' else '' end refundFlag"
				+ " from ltc_request_details lrd,ltc_type_master ltm,ltc_block_year_master lym,status_master sm "
				+ " where lrd.advance_flag is null and ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.sfid=? and sm.id=lrd.status and lrd.status not in(6,8)) order by requestId desc";
			*/
			
		 /*   String query = "select * from (select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,cda.cda_amount||'' cdaAmount,lrd.amendment_ref_request_id amendmentRefId,lrd.issued_amount||'' issuedAmount,lrd.do_part_id doPartId, to_char(sysdate,'dd-Mon-yyyy') currentDate,'ltcAdvance' requestType,sm.status,(select rtrim (xmlagg (xmlelement (e, fd.name || ',')).extract ('//text()'), ',')   from family_details fd,family_relation_master fr where fd.status=1"
				+ " and fd.id in(select family_member_id from ltc_txn_details where request_id=lrd.request_id) and fr.id=fd.relation_id) as familyMemberName,"
				+ " ltm.ltc_type as ltcType,case when (to_char(lym.from_date,'yyyy')=to_char(lym.to_date,'yyyy')) or to_date is null then (to_char(lym.from_date,'yyyy')) else (to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy')) end as blockYear,"
				+ " to_char(lrd.departure_date,'dd-Mon-yyyy') as journeyDate, lrd.request_id as requestId,"
				+ " to_char(lrd.applied_date,'dd-Mon-yyyy') as appliedDate,  case when (select reference_id from ltc_reimbursement_details reim where reim.reference_id=request_id ) is null " 
				+ " then 'Sett/Refund' else '' end reimbursementFlag, '' refundFlag "
				+ " from  ltc_type_master ltm,ltc_block_year_master lym,status_master sm, ltc_advance_request_details lrd left outer join emp_claim_details ecd on(lrd.request_id=ecd.request_id AND ECD.REQUEST_TYPE='advance')  left outer join finance_details on(finance_details.reference_id=ecd.id) left outer join cda_details cda on(finance_details.id =cda.reference_id) "
				+ " where ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.sfid=? and sm.id=lrd.status and lrd.status not in(6)"
				+ " union "
				+ " select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,'' cdaAmount,lrd.amendment_ref_request_id amendmentRefId,'' issuedAmount,lrd.do_part_id doPartId,to_char(sysdate,'dd-Mon-yyyy') currentDate,'ltcApproval' requestType,sm.status,(select rtrim (xmlagg (xmlelement (e, fd.name || ',')).extract ('//text()'), ',')   from family_details fd,family_relation_master fr where fd.status=1"
				+ " and fd.id in(select family_member_id from ltc_txn_details where request_id=lrd.request_id) and fr.id=fd.relation_id) as familyMemberName,"
				+ " ltm.ltc_type as ltcType,case when (to_char(lym.from_date,'yyyy')=to_char(lym.to_date,'yyyy')) or to_date is null then (to_char(lym.from_date,'yyyy')) else (to_char(lym.from_date,'yyyy')||'-'||to_char(lym.to_date,'yy')) end as blockYear,"
				+ " to_char(lrd.departure_date,'dd-Mon-yyyy') as journeyDate, lrd.request_id as requestId,"
				+ " to_char(lrd.creation_date,'dd-Mon-yyyy') as appliedDate, case when (select reference_request_id from ltc_advance_request_details  adv where adv.reference_request_id=lrd.request_id and adv.status=29) is null and (select reference_id from ltc_refund_request  refund where refund.reference_id=lrd.request_id and refund.status not in (6)) is null then 'Sett/Refund' else '' end reimbursementFlag,case when (select reference_id from ltc_reimbursement_details reim where reim.reference_id=lrd.request_id and reim.status=29) is null then 'Advance' else '' end refundFlag"
				+ " from ltc_request_details lrd,ltc_type_master ltm,ltc_block_year_master lym,status_master sm "
				+ " where lrd.advance_flag is null and ltm.id=lrd.ltc_type_id and lym.id=lrd.ltc_block_year_id and lrd.sfid=? and sm.id=lrd.status ) order by requestId desc";
			    */
								
			 String query ="select * from  (SELECT    (SELECT MAX(id)    FROM request_workflow_history    WHERE request_id=lrd.request_id    ) historyID,    cda.cda_amount    ||'' cdaAmount,    lrd.amendment_ref_request_id amendmentRefId,    lrd.issued_amount    ||'' issuedAmount,    lrd.do_part_id doPartId,    TO_CHAR(sysdate,'dd-Mon-yyyy') currentDate,    'ltcAdvance' requestType,    sm.status,    (SELECT rtrim (xmlagg (xmlelement (e, fd.name      || ',')).extract ('//text()'), ',')    FROM family_details fd,      family_relation_master fr    WHERE fd.status=1    AND fd.id     IN      (SELECT family_member_id      FROM ltc_txn_details      WHERE request_id=lrd.request_id  )    AND fr.id=fd.relation_id    )        "
	              +	 "   AS familyMemberName,    ltm.ltc_type AS ltcType,    CASE      WHEN (TO_CHAR(lym.from_date,'yyyy')=TO_CHAR(lym.to_date,'yyyy'))      OR to_date                        IS NULL      THEN (TO_CHAR(lym.from_date,'yyyy'))      ELSE (TO_CHAR(lym.from_date,'yyyy')        ||'-'  ||TO_CHAR(lym.to_date,'yy'))    END     AS blockYear,    TO_CHAR(lrd.departure_date,'dd-Mon-yyyy') AS journeyDate,    lrd.request_id                            AS requestId, "
                  +  "   TO_CHAR(lrd.applied_date,'dd-Mon-yyyy')   AS appliedDate,    CASE      WHEN (SELECT reference_id        FROM ltc_reimbursement_details reim        WHERE reim.reference_id=request_id ) IS NULL      THEN 'Sett/Refund'      ELSE ''    END reimbursementFlag,    '' refundFlag  FROM ltc_type_master ltm,    ltc_block_year_master lym,    status_master sm,    ltc_advance_request_details lrd  LEFT OUTER JOIN emp_claim_details ecd  ON(lrd.request_id   =ecd.request_id  AND ECD.REQUEST_TYPE='advance')  LEFT OUTER JOIN finance_details  ON(finance_details.reference_id=ecd.id)  LEFT OUTER JOIN cda_details cda  ON(finance_details.id =cda.reference_id)  WHERE ltm.id          =lrd.ltc_type_id  "
                  +  "     and lym.id            =lrd.ltc_block_year_id  AND lrd.sfid          =?  and sm.id             =lrd.status   UNION  SELECT    (SELECT MAX(id) FROM request_workflow_history WHERE request_id=lrd.request_id    ) historyID,    '' cdaAmount,    lrd.amendment_ref_request_id amendmentRefId,    '' issuedAmount,    lrd.do_part_id doPartId,    TO_CHAR(sysdate,'dd-Mon-yyyy') currentDate,    'ltcApproval' requestType,    sm.status,    (SELECT rtrim (xmlagg (xmlelement (e, fd.name      || ',')).extract ('//text()'), ',')    FROM family_details fd,      family_relation_master fr    WHERE fd.status=1    AND fd.id     IN      (SELECT family_member_id FROM ltc_txn_details WHERE request_id=lrd.request_id ) "
                  +  "   and fr.id=fd.relation_id    )            as familymembername,    ltm.ltc_type as ltctype,    case      when (to_char(lym.from_date,'yyyy')=to_char(lym.to_date,'yyyy'))      OR to_date                        IS NULL      THEN (TO_CHAR(lym.from_date,'yyyy'))      ELSE (TO_CHAR(lym.from_date,'yyyy')    ||'-'||TO_CHAR(lym.to_date,'yy'))    END                                       AS blockYear,  TO_CHAR(lrd.departure_date,'dd-Mon-yyyy') AS journeyDate,  lrd.request_id     AS requestId,    TO_CHAR(lrd.creation_date,'dd-Mon-yyyy')  AS appliedDate,   CASE      WHEN (SELECT reference_request_id        FROM ltc_advance_request_details adv "
                  +  "   where adv.reference_request_id=lrd.request_id        and adv.status                =29) is null      and (select reference_id        from ltc_refund_request refund        where refund.reference_id=lrd.request_id     ) IS NULL      THEN 'Sett/Refund'      ELSE ''    END reimbursementFlag,    CASE      WHEN (SELECT reference_id        FROM ltc_reimbursement_details reim        WHERE reim.reference_id=lrd.request_id        AND reim.status        =29) IS NULL      THEN 'Advance'      ELSE ''    END refundFlag  FROM ltc_request_details lrd,    ltc_type_master ltm, "
                  +  "   ltc_block_year_master lym,    status_master sm  WHERE lrd.advance_flag IS NULL  AND ltm.id              =lrd.ltc_type_id  and lym.id              =lrd.ltc_block_year_id  AND lrd.sfid            =?  AND sm.id               =lrd.status  )ORDER BY requestId DESC";
			
			  list = session.createSQLQuery(query).addScalar("historyID",Hibernate.STRING).addScalar("cdaAmount",Hibernate.STRING).addScalar("amendmentRefId",Hibernate.STRING).addScalar("doPartId",Hibernate.STRING).addScalar("issuedAmount",Hibernate.STRING).addScalar("currentDate").addScalar("requestType").addScalar("status").addScalar("familyMemberName").addScalar("ltcType").addScalar("blockYear").addScalar("journeyDate").addScalar("appliedDate")
					.addScalar("reimbursementFlag", Hibernate.STRING).addScalar("refundFlag", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).setResultTransformer(
							Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0, sfID).setString(1, sfID).list();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

	@SuppressWarnings("unchecked")
	public LtcApplicationBean getLtcReimbursementDetailsDAO(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		List<LtcMemberDetailsDTO> list = null;
		String query = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(ltcBean.getType(), CPSConstants.SETTLEMENT)) {
				query = "select  lrd.departure_date departureDate,lrd.return_date returnDate,lrd.sfid sfID ,lrd.cda_amount issuedAmount,lrd.request_id id,ltm.ltc_type ltctypeid,(case when (to_char(lbym.from_date,'yyyy')=to_char(lbym.to_date,'yyyy')) or to_date is null then (to_char(lbym.from_date,'yyyy')) else (to_char(lbym.from_date,'yyyy')||'-'||to_char(lbym.to_date,'yy')) end) ltcblockyear "
							+", to_char(lrd.applied_date,'dd-Mon-yyyy') creationDate,lrd.place_of_visit placeOfVisit,(select ref_number||' & '||to_char(ref_date,'dd-Mon-yyyy') from reference_number_details where id=lrd.do_part_id) doPartNo from ltc_advance_request_details lrd,ltc_type_master ltm,ltc_block_year_master lbym "
							+"where lrd.request_id=? and ltm.id=lrd.ltc_type_id and ltm.status=1 and lbym.status=1 and lbym.id=lrd.ltc_block_year_id";
				ltcBean = (LtcApplicationBean) session.createSQLQuery(query).addScalar("issuedAmount", Hibernate.STRING).addScalar("sfID").addScalar("departureDate",Hibernate.DATE).addScalar("returnDate",Hibernate.DATE)
						.addScalar("id", Hibernate.STRING).addScalar("ltcTypeId").addScalar("ltcBlockYear").addScalar("creationDate").addScalar("placeOfVisit").addScalar("doPartNo").setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).setString(0,ltcBean.getId()).uniqueResult();
			} else {
				query = "select lrd.departure_date departureDate,lrd.return_date returnDate, lrd.sfid sfID ,lrd.request_id id,ltm.ltc_type ltctypeid,(case when to_char(lbym.from_date,'YYYY')=to_char(lbym.to_date,'YYYY') then to_char(lbym.from_date,'YYYY') else to_char(lbym.from_date,'YYYY')||'-'||to_char(lbym.to_date,'YY') end) ltcblockyear "
							+", to_char(lrd.creation_date,'dd-Mon-yyyy') creationDate,lrd.place_of_visit placeOfVisit,(select ref_number||' & '||to_char(ref_date,'dd-Mon-yyyy') from reference_number_details where id=lrd.do_part_id) doPartNo from ltc_request_details lrd,ltc_type_master ltm,ltc_block_year_master lbym "
							+"where lrd.request_id=? and ltm.id=lrd.ltc_type_id and ltm.status=1 and lbym.status=1 and lbym.id=lrd.ltc_block_year_id";
				ltcBean = (LtcApplicationBean) session.createSQLQuery(query).addScalar("departureDate",Hibernate.DATE).addScalar("returnDate",Hibernate.DATE).addScalar("sfID").addScalar("id", Hibernate.STRING)
				.addScalar("ltcTypeId", Hibernate.STRING).addScalar("ltcBlockYear", Hibernate.STRING).addScalar("creationDate", Hibernate.STRING).addScalar("placeOfVisit", Hibernate.STRING).addScalar("doPartNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).setString(0, ltcBean.getId()).uniqueResult();
			}
			query = "select fd.id id,fd.name familyMemberName,to_char(fd.dob,'dd-Mon-yyyy')as dob, case when fd.dob  is null then fd.age else round(months_between(sysdate,fd.dob)/12)||'' end age ,fr.name relation"
					+ " from family_details fd,family_relation_master fr where fd.status=1"
					+ " and fd.id in(select family_member_id from ltc_txn_details where request_id=? and reference_id is null)" + " and fr.id=fd.relation_id order by fr.name";
			list = session.createSQLQuery(query).addScalar("familyMemberName").addScalar("dob").addScalar("age").addScalar("relation").addScalar("id", Hibernate.INTEGER).setResultTransformer(
					Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0, ltcBean.getId()).list();
			ltcBean.setLtcApproveDetailsList(list);
			ltcBean.setEmpBean(getEmployeeDetails(ltcBean.getSfID()));
		} catch (Exception e) {
			throw e;
		} 
		return ltcBean;
	}

	public LtcApplicationBean getLtcRefundDetailsDAO(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		String query = null;
		try {
			session = hibernateUtils.getSession();
			if(CPSUtils.compareStrings(ltcBean.getType(),"ltcApproval")) {
				query = "select '' issuedAmount,'' financeIssuedAmount,lrd.leave_request_id leaveRequestId,(select max(id) from request_workflow_history where request_id=lrd.request_id) historyID,'LTC APPROVAL' type,lrd.request_id id, "
					+"to_char(lrd.creation_date,'dd-Mon-yyyy')creationDate,lrd.place_of_visit placeOfVisit,ltm.ltc_type ltcTypeId,(case when (to_char(lbym.from_date,'yyyy')=to_char(lbym.to_date,'yyyy')) or to_date is null "
					+"then (to_char(lbym.from_date,'yyyy')) else (to_char(lbym.from_date,'yyyy')||'-'||to_char(lbym.to_date,'yy')) end) ltcBlockYear ,to_char(rnd.ref_date,'dd-Mon-yyyy') doPartDate,rnd.ref_number doPartNo,lrd.encashment_days encashmentDays, "
					+"(select leave.from_date||' To '||leave.to_date||' for '||leave.no_of_days||' Days' from leave_request_details leave where leave.request_id=lrd.leave_request_id)leaveDetails "
					+"from ltc_request_details lrd,ltc_type_master ltm,ltc_block_year_master lbym,reference_number_details rnd where lrd.request_id=? and ltm.id=lrd.ltc_type_id and lbym.id=lrd.ltc_block_year_id and rnd.id=lrd.do_part_id ";
			}else {
				query = "select lard.leave_request_id leaveRequestId,lard.cda_amount issuedAmount,lard.issued_amount financeIssuedAmount,(select max(id) from request_workflow_history where request_id=lard.request_id)historyID,'LTC APPROVAL CUM ADVANCE' type,lard.request_id id "
					+",ltm.ltc_type ltcTypeId,(select lrd.from_date||' To '||lrd.to_date||' for '||lrd.no_of_days||' Days' from leave_request_details lrd where lrd.request_id=lard.leave_request_id) leaveDetails,(case when (to_char(lbym.from_date,'yyyy')=to_char(lbym.to_date,'yyyy')) or to_date is null "
					+"then (to_char(lbym.from_date,'yyyy')) else (to_char(lbym.from_date,'yyyy')||'-'||to_char(lbym.to_date,'yy')) end) ltcBlockYear,lard.place_of_visit placeOfVisit,to_char(lard.applied_date,'dd-Mon-yyyy') creationDate "
					+",to_char(rnd.ref_date,'dd-Mon-yyyy') doPartDate,rnd.ref_number doPartNo,lard.encashment_days encashmentDays "
					+"from ltc_advance_request_details lard,ltc_type_master ltm,ltc_block_year_master lbym,reference_number_details rnd where request_id=? and lard.ltc_type_id=ltm.id and lbym.id=lard.ltc_block_year_id and rnd.id=lard.do_part_id ";
			}
			
				ltcBean = (LtcApplicationBean) session.createSQLQuery(query).addScalar("leaveRequestId",Hibernate.STRING).addScalar("issuedAmount",Hibernate.STRING).addScalar("financeIssuedAmount",Hibernate.STRING).addScalar("historyID",Hibernate.STRING).addScalar("type",Hibernate.STRING).addScalar("id",Hibernate.STRING).addScalar("ltcTypeId").addScalar("leaveDetails").addScalar("ltcBlockYear").addScalar("placeOfVisit").addScalar("creationDate").addScalar("doPartDate",Hibernate.STRING).addScalar("doPartNo",Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).setString(0, ltcBean.getId()).uniqueResult();
				
				
		} catch (Exception e) {
			throw e;
		} 
		return ltcBean;
	}

	@SuppressWarnings("unchecked")
	public List<LeaveEncashmentDTO> getLeaveEncashList() throws Exception {
		List<LeaveEncashmentDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(LeaveEncashmentDTO.class).addOrder(Order.asc("leaveTypeID")).list();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

 /* public LtcRequestProcessBean checkLtcConstraints(LtcRequestProcessBean ltcReqBean) throws Exception {
		Session session = null;
		LtcMemberDetailsDTO ltcMemberDTO = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();
			if(Float.parseFloat(session.createSQLQuery("select months_between(?,doj_drdo)/12 from emp_master where sfid=?").setDate(0, ltcReqBean.getDepartureDate()).setString(1, ltcReqBean.getSfID()).uniqueResult().toString())>=1.00) {
				ltcReqBean.setRemarks("");
				if (!CPSUtils.compareStrings(CPSConstants.HOMETOWMTRIPID, ltcReqBean.getLtcTypeId())) {
					status = true;
				}
				JSONObject ltcMembersJson = new JSONObject(ltcReqBean.getJsonValue());
				for (int i = 0; i < ltcMembersJson.length(); i++) {

					ltcMemberDTO = (LtcMemberDetailsDTO) session.createCriteria(LtcMemberDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).add(
							Expression.eq("familyMemberId", ltcMembersJson.getString(String.valueOf(i)))).add(Expression.eq("blockYear", ltcReqBean.getLtcBlockYearId())).uniqueResult();

					if (!CPSUtils.isNullOrEmpty(ltcMemberDTO) && CPSUtils.compareStrings(CPSConstants.HOMETOWMTRIPID, ltcReqBean.getLtcTypeId())) {
						ltcReqBean.setRemarks(ltcReqBean.getRemarks() + ltcMemberDTO.getFamilyMemberDetails().getName()+ "<br>");
					}
					if (status) {
						// Check whether the family member went to all india trip in this four year block or not
						if (!CPSUtils
								.compareStrings(
										session
												.createSQLQuery(
														"select count(*) from ltc_txn_details where ltc_block_year_id in (select id from ltc_block_year_master where four_year_block_id = (select four_year_block_id from ltc_block_year_master where id=? and status=1)) "
																+ "and status=1 and family_member_id=? and ltc_type_id=?").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1,
														ltcMembersJson.getString(String.valueOf(i))).setString(2, ltcReqBean.getLtcTypeId()).uniqueResult().toString(), "0")) {
							ltcReqBean.setRemarks(ltcReqBean.getRemarks() +  ((FamilyBean)session.get(FamilyBean.class, ltcMembersJson.getString(String.valueOf(i)))).getName()+"<br>");
						}
					}
				}
				if (!CPSUtils.isNullOrEmpty(ltcReqBean.getRemarks())) {
					ltcReqBean.setResult(CPSConstants.FAILED);
					ltcReqBean.setRemarks(ltcReqBean.getRemarks()+ "Already appplied in selected block year");
				com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();		
				} else {
					ltcReqBean.setResult(CPSConstants.SUCCESS);
				}

			}else {
				ltcReqBean.setResult(CPSConstants.FAILED);
				ltcReqBean.setRemarks("User should have minimum of one year service in organisation");
				com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();
			}
			session = hibernateUtils.getSession();
			
			if (CPSUtils.compareStrings(CPSConstants.ALLINDIATRIPID, ltcReqBean.getLtcTypeId())) {
				*//**
				 * For junior employee he can avail All India Trip for last year of Four Year Block:: Rule Amended
				 *//*
				if(!CPSUtils.compareStrings((String)session.createSQLQuery("select case when selectedyear=lastyear or lastyear='success' then 'success' else 'fails' end result " 
						+"from (select to_char(to_date,'yyyy')selectedyear,case when (select ltc_block_id from ltc_block_year_master where id=?)!=1 then 'success' " 
						+"else (select to_char(to_date,'yyyy') from ltc_block_year_master where id=(select four_year_block_id from ltc_block_year_master  "
						+"where id=?)) end lastyear from ltc_block_year_master where id=?)").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1, ltcReqBean.getLtcBlockYearId()).setString(2, ltcReqBean.getLtcBlockYearId()).uniqueResult(), CPSConstants.SUCCESS)) {
					
					//String sql =null;
					//session = hibernateUtils.getSession();
					//getting the block yearid present the user wich block year avail
				Object blockyearid=session.createSQLQuery("select to_char(ltc_block_id) as blockyearid from ltc_block_year_master where id=?").setString(0, ltcReqBean.getLtcBlockYearId()).uniqueResult();
									//	String year=(String) session.createSQLQuery("select  to_number(to_char(doj_govt,'yyyy'))+1+3 as year from emp_master where sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult();
					
				Object year= session.createSQLQuery("select  to_number(to_char(sysdate,'yyyy'))-to_number(to_char(doj_govt,'yyyy')) as year from emp_master where sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult();
					ltcReqBean.setLtcblockidindia(blockyearid.toString());
					ltcReqBean.setYearallindia(Integer.parseInt(year.toString()));
				
				//User should have minimum of four years service in organisation he can avail All India Trip This is new constraint :::::Rakesh 
					if(ltcReqBean.getYearallindia()>= 4){
						
						ltcReqBean.setResult(CPSConstants.SUCCESS);
					}
					else{
						ltcReqBean.setRemarks(ltcReqBean.getRemarks()+" All India Trip can only be availed for the last year of Four Year Block \n");
						ltcReqBean.setResult(CPSConstants.FAILED);	
						com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();
					}
					
					
					
				}
			}
		

		} catch (Exception e) {
			throw e;
		}
		return ltcReqBean;
	}
  
  */

	//this rules applicable afte dec-2014::: added by Rakesh
	@SuppressWarnings("unchecked")
	public LtcRequestProcessBean checkLtcConstraints(LtcRequestProcessBean ltcReqBean) throws Exception {
		Session session = null;
		List<KeyValueDTO> listkey = null;
		String sql = null;
		LtcMemberDetailsDTO ltcMemberDTO = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();
				if(Float.parseFloat(session.createSQLQuery("select months_between(?,doj_drdo)/12 from emp_master where sfid=?").setDate(0, ltcReqBean.getDepartureDate()).setString(1, ltcReqBean.getSfID()).uniqueResult().toString())>=1.00) {
						if(CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(session.createSQLQuery("select upper(get_retirement_date(?)) as retire from dual").setString(0, ltcReqBean.getSfID()).uniqueResult().toString()), ltcReqBean.getReturnDate())>0){
						ltcReqBean.setRemarks("");
						if (!CPSUtils.compareStrings(CPSConstants.HOMETOWMTRIPID, ltcReqBean.getLtcTypeId())) {
							status = true;
						}
						JSONObject ltcMembersJson = new JSONObject(ltcReqBean.getJsonValue());
						for (int i = 0; i < ltcMembersJson.length(); i++) {
							/*
							if(CPSUtils.compareStrings(session.createSQLQuery("SELECT COUNT(*) FROM ltc_txn_details WHERE ltc_block_year_id IN  (SELECT id FROM ltc_block_year_master "
		                             + " where four_year_block_id = (SELECT four_year_block_id FROM ltc_block_year_master WHERE id=? AND status=1 ) ) and status    =1 and family_member_id=? AND ltc_type_id =?").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1,ltcMembersJson.getString(String.valueOf(i))).setString(2,ltcReqBean.getLtcTypeId()).uniqueResult().toString(), "0"))*/
							if(CPSUtils.compareStrings(session.createSQLQuery("select count(*) from ltc_txn_details where family_member_id=? and ltc_block_year_id=? and status=1 ").setString(0,ltcMembersJson.getString(String.valueOf(i))).setString(1,ltcReqBean.getLtcBlockYearId()).uniqueResult().toString(), "0"))
							{
		               /*
							if(!CPSUtils.isNullOrEmpty(session.createCriteria(LtcMemberDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).add(
									Expression.eq("familyMemberId", ltcMembersJson.getString(String.valueOf(i)))).add(Expression.eq("blockYear", ltcReqBean.getLtcBlockYearId())).uniqueResult())){*/
							ltcMemberDTO = (LtcMemberDetailsDTO)session.createCriteria(LtcMemberDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).add(
									Expression.eq("familyMemberId", ltcMembersJson.getString(String.valueOf(i)))).add(Expression.eq("blockYear", ltcReqBean.getLtcBlockYearId())).uniqueResult();
							
							if (!CPSUtils.isNullOrEmpty(ltcMemberDTO) && CPSUtils.compareStrings(CPSConstants.HOMETOWMTRIPID, ltcReqBean.getLtcTypeId())) {
								ltcReqBean.setRemarks(ltcReqBean.getRemarks() + ltcMemberDTO.getFamilyMemberDetails().getName()+ "<br>");
							}//}
							if (status) {
								// Check whether the family member went to all india trip in this four year block or not
								if (!CPSUtils
										.compareStrings(
												session
														.createSQLQuery(
																"select count(*) from ltc_txn_details where ltc_block_year_id in (select id from ltc_block_year_master where four_year_block_id = (select four_year_block_id from ltc_block_year_master where id=? and status=1)) "
																		+ "and status=1 and family_member_id=? and ltc_type_id=?").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1,
																ltcMembersJson.getString(String.valueOf(i))).setString(2, ltcReqBean.getLtcTypeId()).uniqueResult().toString(), "0")) {
									ltcReqBean.setRemarks(ltcReqBean.getRemarks() +  ((FamilyBean)session.get(FamilyBean.class, ltcMembersJson.getString(String.valueOf(i)))).getName()+"<br>");
								}
							}  //}//IF COUNT
						}//if
							else{
		
								ltcReqBean.setResult(CPSConstants.FAILED);
								
								String query = null;
								List<LtcMemberDetailsDTO> list = null;
		
								
								/*query ="SELECT FD.NAME familyMemberName FROM family_details FD WHERE FD.ID=?";
								list = session.createSQLQuery(query).addScalar("familyMemberName").setResultTransformer(
								Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0, ltcMembersJson.getString(String.valueOf(i))).list();*/
								
								query ="select fd.name as familymembername, ltm.ltc_type as ltcType , ltd.request_id as referenceID  from family_details fd,  ltc_block_year_master lbym , ltc_type_master ltm , ltc_txn_details ltd  where ltd.family_member_id=? "
									+ " and ltd.family_member_id=fd.id and ltd.ltc_block_year_id=? and ltd.status =1 and  fd.status=1  and ltd.ltc_type_id=ltm.id and ltd.ltc_block_year_id=lbym.id";
								list = session.createSQLQuery(query).addScalar("familyMemberName").addScalar("ltcType", Hibernate.STRING).addScalar("referenceID", Hibernate.STRING).setResultTransformer(
										Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0, ltcMembersJson.getString(String.valueOf(i))).setString(1, ltcReqBean.getLtcBlockYearId()).list();
							
							ltcReqBean.setRemarks(ltcReqBean.getRemarks() + list.get(0).getLtcType() +"LTC is already applied for -"+list.get(0).getFamilyMemberName()+ " for the selected block year.Applied LTC RequestId : " + list.get(0).getReferenceID() +"<br>");
								
								//ltcReqBean.setRemarks(ltcReqBean.getRemarks()+ " ");
								ltcReqBean.setCheckApplied("Already applied ");
							com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();		
							
							}
						}//for
						if (!CPSUtils.isNullOrEmpty(ltcReqBean.getRemarks())) {
							ltcReqBean.setResult(CPSConstants.FAILED);
							//ltcReqBean.setRemarks(ltcReqBean.getRemarks()+ "Already appplied in selected block year");
							ltcReqBean.setRemarks(ltcReqBean.getRemarks());
						com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();		
						} else {
							ltcReqBean.setResult(CPSConstants.SUCCESS);
						}
		
						
					}//retirement
				else {
					//retirement check
					 String d = session.createSQLQuery("SELECT get_retirement_date(?)as retirement FROM DUAL").setString(0, ltcReqBean.getSfID()).uniqueResult().toString();
					ltcReqBean.setRetirementDate(d);
					 ltcReqBean.setResult(CPSConstants.FAILED);
					ltcReqBean.setRemarks("Employee should return  from LTC minimum one day before Retirement date("+ltcReqBean.getRetirementDate()+") LTC Arrival date: "+ CPSUtils.formatDate(ltcReqBean.getReturnDate())+" is invalid.");
					com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();
				}
					
					}else {
					ltcReqBean.setResult(CPSConstants.FAILED);
					ltcReqBean.setRemarks("User should have minimum of one year service in organisation");
					com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();
				}
			session = hibernateUtils.getSession();
			if(!CPSUtils.compareString(CPSConstants.FAILED, ltcReqBean.getResult())){/*
				ltcReqBean.setHomeTownAddress(getHometownAddress(ltcReqBean.getSfID()));
				String District1 =ltcReqBean.getHomeTownAddress().getDistrict();
				
				sql ="select extract (year from from_date) as value  from ltc_block_year_master where ltc_block_id=1 and ( extract (year from from_date) between (select extract ( year from  doj_govt )+1 fromyear  from emp_master  where sfid='?') "
                + "  and (select extract ( year from  doj_govt)+8 fromyear  from emp_master  where sfid='?')) and extract (year from  SYSDATE )  <=  extract (year from from_date) ";
			 
	listkey = session.createSQLQuery(sql).addScalar("value", Hibernate.STRING).setString(0, ltcReqBean.getSfID()).setString(1, ltcReqBean.getSfID()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			    
	
	
	 if(!(District1.equals("6")||District1.equals("18")))
	    {
	    	sql ="select id as key,extract (year from from_date)  || '-'|| to_char(to_date(to_date),'yy')as value , extended_date as name from ltc_block_year_master where ltc_block_id=2 and ( extract (year from from_date) >= (select extract ( year from  doj_govt )+9 fromyear  from emp_master  where sfid='"+sfid+"')) "
       +  " and  (extract (year from sysdate) <= extract (year from from_date)) AND ROWNUM=1";
	    	List<KeyValueDTO>  listkey1 = session.createSQLQuery(sql).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		      if(listkey1.size()>0)
		      {
			      String year = listkey1.get(0).getValue().split("-")[0];
			      
			      String  qry ="select id key , to_char(from_date,'YYYY')||'-'||to_char(to_date,'YY') value, extended_date name from ltc_block_year_master "
                     + " where to_date(sysdate,'dd-mm-yyyy')  <= to_date(extended_date,'dd-mm-yyyy') and ltc_block_id   =2 and extract (year from extended_date)=?  AND( extract (YEAR FROM from_date) >=  (SELECT extract ( YEAR FROM doj_govt )+9 fromyear  from emp_master  WHERE sfid='"+sfid+"'  ))  ";
			       
		          List<KeyValueDTO> list3 = session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, year).list();
     		
		        }
		      
		      
	   }     
	if((District1.equals("6")||District1.equals("18"))){
		 sql =" select  extract (year from from_date)  as value FROM ltc_block_year_master WHERE ltc_block_id  =3 AND ( extract (YEAR FROM from_date) >=  (SELECT extract ( YEAR FROM doj_govt )+9 fromyear  from emp_master WHERE sfid='?'  )) and (extract (year from sysdate) <= extract (year from from_date))AND ROWNUM    =1" ;
		
		  List<KeyValueDTO>  listkey2 = session.createSQLQuery(sql).addScalar("value", Hibernate.STRING).setString(0, ltcReqBean.getSfID()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		        
		        
		       
	}
	
	
	
	
	
				
			*/}
			
			if (CPSUtils.compareStrings(CPSConstants.ALLINDIATRIPID, ltcReqBean.getLtcTypeId()) && CPSUtils.isNullOrEmpty(ltcReqBean.getCheckApplied()) &&!CPSUtils.compareString(ltcReqBean.getResult(),"failure")) {
				/**
				 * For junior employee he can avail All India Trip for last year of Four Year Block:: Rule Amended
				 */
				if(!CPSUtils.compareStrings((String)session.createSQLQuery("select case when selectedyear=lastyear or lastyear='success' then 'success' else 'fails' end result " 
						+"from (select to_char(to_date,'yyyy')selectedyear,case when (select ltc_block_id from ltc_block_year_master where id=?)!=1 then 'success' " 
						+"else (select to_char(to_date,'yyyy') from ltc_block_year_master where id=(select four_year_block_id from ltc_block_year_master  "
						+"where id=?)) end lastyear from ltc_block_year_master where id=?)").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1, ltcReqBean.getLtcBlockYearId()).setString(2, ltcReqBean.getLtcBlockYearId()).uniqueResult(), CPSConstants.SUCCESS)) 
				{
					
					//String sql =null;
					//session = hibernateUtils.getSession();
					//getting the block yearid present the user wich block year avail
				    Object blockyearid=session.createSQLQuery("select to_char(ltc_block_id) as blockyearid from ltc_block_year_master where id=?").setString(0, ltcReqBean.getLtcBlockYearId()).uniqueResult();
									//	String year=(String) session.createSQLQuery("select  to_number(to_char(doj_govt,'yyyy'))+1+3 as year from emp_master where sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult();
					
				    Object year= session.createSQLQuery("select  to_number(to_char(sysdate,'yyyy'))-to_number(to_char(doj_govt,'yyyy')) as year from emp_master where sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult();
					ltcReqBean.setLtcblockidindia(blockyearid.toString());
					ltcReqBean.setYearallindia(Integer.parseInt(year.toString()));
				
				   //Employee should have minimum of four years service in organisation he can avail All India Trip This is new constraint :::::Rakesh 
					/* if(ltcReqBean.getYearallindia()>= 4)
					 {
					  ltcReqBean.setResult(CPSConstants.SUCCESS);
					 }
					 else
					 {
						ltcReqBean.setRemarks(ltcReqBean.getRemarks()+" All India Trip can only be availed for the last year of Four Year Block \n");
						ltcReqBean.setResult(CPSConstants.FAILED);	
						com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();
					 }*/
					
					
					
				}
				/*if(Integer.parseInt(session.createSQLQuery("select TO_CHAR(SYSDATE,'yyyy')-to_char(doj_govt,'yyyy') AS EXP  from emp_master where status=1 and sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult().toString())<=10){
					
					JSONObject ltcMembersJson = new JSONObject(ltcReqBean.getJsonValue());
					for (int i = 0; i < ltcMembersJson.length(); i++) {
                          
						Integer s= Integer.parseInt(session.createSQLQuery("select Count(*) from ltc_txn_details where ltc_type_id =1 and status =1 and family_member_id =?").setString(0, ltcMembersJson.getString(String.valueOf(i))).uniqueResult().toString());
					if(s>0){
						ltcReqBean.setRemarks("All India Trip can't  be avail \n");
						
					}
					
					}
					
					//System.out.println("rakesh");		
				}*/
				
				JSONObject ltcMembersJson = new JSONObject(ltcReqBean.getJsonValue());
				for (int h = 0; h < ltcMembersJson.length(); h++) {
	          //if(CPSUtils.compareStrings(session.createSQLQuery("SELECT count (*) FROM ltc_txn_details txn WHERE txn.ltc_block_year_id  in (SELECT lm.id   FROM ltc_block_year_master  lm  WHERE four_year_block_id =  (select four_year_block_id    from ltc_block_year_master    where id  =?    and status=1 ) ) and status  =1 and family_member_id=?  AND  ltc_type_id     =1").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1, ltcMembersJson.getString(String.valueOf(h))).uniqueResult().toString(),"0"))
					 if(CPSUtils.compareStrings(session.createSQLQuery("SELECT COUNT (*) FROM ltc_txn_details txn WHERE txn.ltc_block_year_id=? AND status  =1 AND family_member_id   =?").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1, ltcMembersJson.getString(String.valueOf(h))).uniqueResult().toString(),"0"))
		        {
	
					
					
					if(Integer.parseInt(session.createSQLQuery("select TO_CHAR(SYSDATE,'yyyy')-to_char(doj_govt,'yyyy') AS EXP  from emp_master where status=1 and sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult().toString())<=10)
						{
						
						Integer i = Integer.parseInt(session.createSQLQuery("select to_char(doj_govt,'yyyy')+1 AS EXP  from emp_master where status=1 and sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult().toString());
						Integer departure =ltcReqBean.getDepartureDate().getYear()+1900;
						if(i+3 ==departure){
							ltcReqBean.setResult(CPSConstants.SUCCESS);
						}
						else if (i+7==departure){
							ltcReqBean.setResult(CPSConstants.SUCCESS);
						}
						else if(departure-i>=9){
							
							
							ltcReqBean.setResult(CPSConstants.SUCCESS);
							/*
							if(i+11==departure ||i+15==departure || i+19 ==departure || i+23==departure || i+27==departure || i+31==departure ){
								ltcReqBean.setResult(CPSConstants.SUCCESS);}
							else{
								ltcReqBean.setResult(CPSConstants.FAILED);	
								ltcReqBean.setRemarks("All India Trip can't  be avail selected block year\n");
							}
						*/}
						else {
							ltcReqBean.setResult(CPSConstants.FAILED);	
							ltcReqBean.setRemarks("All India Trip can't  be avail selected block year\n");
						}
						
						}
					
					
					
					
					if(Integer.parseInt(session.createSQLQuery("select TO_CHAR(SYSDATE,'yyyy')-to_char(doj_govt,'yyyy') AS EXP  from emp_master where status=1 and sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult().toString())>=9)
						{
							if(CPSUtils.compareString(ltcReqBean.getResult(), CPSConstants.FAILED)){
								ltcReqBean.setResult("");
							ltcReqBean.setRemarks("");}
							//getting the home town address details
							ltcReqBean.setHomeTownAddress(getHometownAddress(ltcReqBean.getSfID()));
							String District =ltcReqBean.getHomeTownAddress().getDistrict();
							Integer fouryear =0;
							
							  if(District.equals("6")||District.equals("18")){
							   //Integer fouryear = Integer.parseInt(session.createSQLQuery("select (select to_char(from_date,'YY')   from ltc_block_year_master l where l.id= lby.four_year_block_id  ) as fouryear FROM ltc_block_year_master LBY ,  ltc_block_master lm where lm.id     =2  and  LM.STATUS    =1 and lby.status    =1 AND lby.id=?").setString(0, ltcReqBean.getLtcBlockYearId()).uniqueResult().toString());
					
							  fouryear = Integer.parseInt(session.createSQLQuery("select TO_CHAR(from_date,'YY') from  ltc_block_year_master where id=? and status =1").setString(0, ltcReqBean.getLtcBlockYearId()).uniqueResult().toString());
							 
							  if(!CPSUtils.isNullOrEmpty(fouryear)){
									
				                    Integer departure =ltcReqBean.getDepartureDate().getYear()+1900;
				                    ltcReqBean.setResult(CPSConstants.SUCCESS);
				                 /*if(departure==fouryear+2000){
				                	ltcReqBean.setRemarks("All India Trip can't  be avail selected block year\n");
					               ltcReqBean.setResult(CPSConstants.FAILED);  }
				                else if(departure==fouryear+2001){
				             	ltcReqBean.setRemarks("All India Trip can't  be avail selected block year\n");
				                	ltcReqBean.setResult(CPSConstants.FAILED);}*/
			}}
							 else{
								 fouryear = Integer.parseInt(session.createSQLQuery("select (select to_char(from_date,'YY')   from ltc_block_year_master l where l.id= lby.four_year_block_id  ) as fouryear FROM ltc_block_year_master LBY ,  ltc_block_master lm where lm.id     =2  and  LM.STATUS    =1 and lby.status    =1 AND lby.id=?").setString(0, ltcReqBean.getLtcBlockYearId()).uniqueResult().toString());
							
								 if(!CPSUtils.isNullOrEmpty(fouryear)){
										
					                    Integer departure =ltcReqBean.getDepartureDate().getYear()+1900;
					                  
					                 if(departure==fouryear+2000){
					                	ltcReqBean.setRemarks("All India Trip can't  be avail selected block year\n");
						               ltcReqBean.setResult(CPSConstants.FAILED);  }
					                else if(departure==fouryear+2001){
					             	ltcReqBean.setRemarks("All India Trip can't  be avail selected block year\n");
					                	ltcReqBean.setResult(CPSConstants.FAILED);}
					                else {
					                	  ltcReqBean.setResult(CPSConstants.SUCCESS);
					                }
				}
							}
							
		
		
							}
					}
		else {
			ltcReqBean.setRemarks("All India Trip can't  be avail selected block year\n");
	    	ltcReqBean.setResult(CPSConstants.FAILED);
	    	
	    	
	    	
	    	String query = null;
			List<LtcMemberDetailsDTO> list = null;
	
			
			query ="SELECT FD.NAME familyMemberName FROM family_details FD WHERE FD.ID=?";
			list = session.createSQLQuery(query).addScalar("familyMemberName").setResultTransformer(
			Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0, ltcMembersJson.getString(String.valueOf(h))).list();
		
		ltcReqBean.setRemarks(ltcReqBean.getRemarks() + list.get(0).getFamilyMemberName()+ "<br>");
		
			
			
			
			
			//ltcReqBean.setRemarks(ltcReqBean.getRemarks()+ " ");
			ltcReqBean.setCheckApplied("Already applied");
		com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();
		}
	   
				}//for
				
			}
			if(CPSUtils.compareStrings(CPSConstants.HOMETOWMTRIPID,ltcReqBean.getLtcTypeId())  && CPSUtils.isNullOrEmpty(ltcReqBean.getCheckApplied()) && !CPSUtils.compareString(ltcReqBean.getResult(),"failure")|| !CPSUtils.compareStrings(CPSConstants.ALLINDIATRIPID, ltcReqBean.getLtcTypeId()) &&CPSUtils.isNullOrEmpty(ltcReqBean.getCheckApplied()) && !CPSUtils.compareString(ltcReqBean.getResult(),"failure"))
				{			
					
					JSONObject ltcMembersJson = new JSONObject(ltcReqBean.getJsonValue());
					for (int h = 0; h < ltcMembersJson.length(); h++) {
		          if(CPSUtils.compareStrings(session.createSQLQuery("SELECT COUNT (*) FROM ltc_txn_details txn WHERE txn.ltc_block_year_id=? AND status   =1 AND family_member_id =?").setString(0, ltcReqBean.getLtcBlockYearId()).setString(1, ltcMembersJson.getString(String.valueOf(h))).uniqueResult().toString(),"0"))
		
		           {			
					
					
					
					
					if(Integer.parseInt(session.createSQLQuery("select TO_CHAR(SYSDATE,'yyyy')-to_char(doj_govt,'yyyy') AS EXP  from emp_master where status=1 and sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult().toString())<=10)
					{					
						Integer i = Integer.parseInt(session.createSQLQuery("select to_char(doj_govt,'yyyy')+1 AS EXP  from emp_master where status=1 and sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult().toString());
						//Integer departure =ltcReqBean.getDepartureDate().getYear();
						Integer departure =ltcReqBean.getDepartureDate().getYear()+1900;	
									
						if(i+3 ==departure){
							ltcReqBean.setRemarks(" Home Town Trip can't  be avail selected year\n");
							ltcReqBean.setResult(CPSConstants.FAILED);	
						}
						else if (i+7==departure){
							ltcReqBean.setRemarks(" Home Town Trip can't  be avail selected year \n");
							ltcReqBean.setResult(CPSConstants.FAILED);	
						}
						else {						
							ltcReqBean.setResult(CPSConstants.SUCCESS);
						}
						
			     	}
					
	
					
					if(Integer.parseInt(session.createSQLQuery("select TO_CHAR(SYSDATE,'yyyy')-to_char(doj_govt,'yyyy') AS EXP  from emp_master where status=1 and sfid=?").setString(0, ltcReqBean.getSfID()).uniqueResult().toString())>10)
					{
					
					
					Integer fouryear = Integer.parseInt(session.createSQLQuery("select (select to_char(from_date,'YY')   from ltc_block_year_master l where l.id= lby.four_year_block_id  ) as fouryear FROM ltc_block_year_master LBY ,  ltc_block_master lm where lm.id     =2  and  LM.STATUS    =1 and lby.status    =1 AND lby.id=?").setString(0, ltcReqBean.getLtcBlockYearId()).uniqueResult().toString());
					
					  if(!CPSUtils.isNullOrEmpty(fouryear))
					  {
						
						  Integer departure =ltcReqBean.getDepartureDate().getYear()+1900;
						  if(departure==fouryear+2002)
						  {
							ltcReqBean.setRemarks("Home Town Trip can't  be avail \n");
							ltcReqBean.setResult(CPSConstants.FAILED);	
						  }
						  else if(departure==fouryear+2003)
						 {
							ltcReqBean.setRemarks("Home Town Trip can't  be avail \n");
							ltcReqBean.setResult(CPSConstants.FAILED);	
							
						 }
						  else {						
								ltcReqBean.setResult(CPSConstants.SUCCESS);
							}
					 }
				
					}
				}
		else{
			//ltcReqBean.setRemarks("Home Town/Home Town Conversion  Trip  can't  be avail \n");
			ltcReqBean.setRemarks("Already applied selected Block year  \n");
			ltcReqBean.setResult(CPSConstants.FAILED);	
			
			String query = null;
			List<LtcMemberDetailsDTO> list = null;
	
			
			query ="SELECT FD.NAME familyMemberName FROM family_details FD WHERE FD.ID=?";
			list = session.createSQLQuery(query).addScalar("familyMemberName").setResultTransformer(
			Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0, ltcMembersJson.getString(String.valueOf(h))).list();
		
		ltcReqBean.setRemarks(ltcReqBean.getRemarks() + list.get(0).getFamilyMemberName()+ "<br>");
		
			
			
			
			
			//ltcReqBean.setRemarks(ltcReqBean.getRemarks()+ " ");
			ltcReqBean.setCheckApplied("Already applied");
		com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();
			
			
			
		}
		}//for
				
				}
		

		}
		catch (Exception e) 
		{
			//Session session1 = null;
			try
			{
				session = hibernateUtils.getSession();
				String query = null;
				List<LtcMemberDetailsDTO> list = null;
				
				JSONObject ltcMembersJson = new JSONObject(ltcReqBean.getJsonValue());
				for (int i = 0; i < ltcMembersJson.length(); i++) 
				{					
					query ="SELECT FD.NAME familyMemberName FROM family_details FD WHERE FD.ID=?";
					list = session.createSQLQuery(query).addScalar("familyMemberName").setResultTransformer(
					Transformers.aliasToBean(LtcMemberDetailsDTO.class)).setString(0, ltcMembersJson.getString(String.valueOf(i))).list();				
				   ltcReqBean.setRemarks(ltcReqBean.getRemarks() + list.get(0).getFamilyMemberName()+ "<br>");
				}
				if (!CPSUtils.isNullOrEmpty(ltcReqBean.getRemarks())) 
				{
					ltcReqBean.setResult(CPSConstants.FAILED);
					ltcReqBean.setRemarks(ltcReqBean.getRemarks()+ "Already appplied in selected block year");
				   com.callippus.web.controller.common.HibernateUtils.rollbackTransaction();		
				}
			}
		   catch (Exception e1)
		   {
				throw e;
			}
			
		}
		return ltcReqBean;
	}
	//
	public LtcRequestProcessBean checkEncashDaysConstraints(LtcRequestProcessBean ltcReqBean)throws Exception {
		Session session = null;
		String sql=null;
		try {
			session = hibernateUtils.getSession();
			sql="select "
					+"case when (select available_leaves from available_leaves where sfid=? and leave_type_id=?) >=? " 
					 +"then (select "
					  +"  case when (select max_days_per_spell from LEAVE_ENCASHMENT_DETAILS where leave_type_id=?) >= ? " 
					   +"  then (select "
					    +"    case when (select al.available_leaves- ? from available_leaves al  where al.sfid=? and al.leave_type_id=?) >=30 "
					    +"then " 
        					+"case when (select ltc_encashment_days+? from leave_emp_spells where sfid=? and leave_type_id=?) <=60 " 
        					+"       then "  
					        	+"       'success' " 
					        +"else 'Max encashment 60 days completed.' end"
					         +"    else  "
					           +"   'EL encashment is not possible since u r not having 30 days EL in credit after encashment.' end from dual) " 
					      +"else 'More than 28 days can not be encashed.' "
					    +"end from dual) "
					+"else 'No Leave balance in EL can not encash.' " 
					+"end from dual";
			String result = session.createSQLQuery(sql).setString(0, ltcReqBean.getSfID()).setString(1, ltcReqBean.getEncashTypeId()).setString(2, ltcReqBean.getEncashmentDays())
			.setString(3, ltcReqBean.getEncashTypeId()).setString(4, ltcReqBean.getEncashmentDays()).setString(5, ltcReqBean.getEncashmentDays())
			.setString(6, ltcReqBean.getSfID()).setString(7, ltcReqBean.getEncashTypeId()).setString(8, ltcReqBean.getEncashmentDays()).setString(9, ltcReqBean.getSfID()).setString(10, ltcReqBean.getEncashTypeId()).uniqueResult().toString();
			
			if(CPSUtils.compareStrings(result, CPSConstants.SUCCESS)) {
				ltcReqBean.setResult(CPSConstants.SUCCESS);
			}else {
				ltcReqBean.setResult(CPSConstants.FAILED);
				ltcReqBean.setRemarks(result);
			}
			
		}catch (Exception e) {
			throw e;
		}
		return ltcReqBean;
	}
	public LtcAdvanceRequestDTO getLtcApprovalDetails (String referenceRequestID) throws Exception {
		Session session = null;
		String sql=null;
		LtcAdvanceRequestDTO ltcAdvanceRequestDTO=null;
		try {
			session = hibernateUtils.getSession();
			sql = "select 'advance' type,NVL(lrd.do_part_id, 0) doPartId,(select ref_number||' & '||ref_date from reference_number_details where id=lrd.do_part_id) doPartNo,emp.employment_type_id employmentTypeId, epd.grade_pay gradePay,lrd.leave_request_id leaveRequestId,lrd.encash_leave_type_id encashTypeId,lrd.encashment_days encashmentDays,lrd.encashment_amount encashmentAmount,lrd.address_id addressId,lrd.ltc_type_id ltcTypeId,lrd.sfid sfID,lrd.designation_id designationId,lrd.department_id departmentId,lrd.phone_number phoneNum,lrd.request_id referenceRequestID,lrd.status status,ltm.ltc_type ltcType,case when to_char(lbym.from_date,'yyyy')=to_char(lbym.to_date,'yyyy')  " 
					+"then to_char(lbym.from_date,'yyyy')  else to_char(lbym.from_date,'yyyy')||' - '||to_char(lbym.to_date,'yy') end ltcBlockYear,lrd.ltc_block_year_id ltcBlockYearId,lrd.place_of_visit placeOfVisit,lrd.nearest_railway_station nearestRlyStation,lrd.departure_date departureDate,lrd.return_date returnDate "
					+"from ltc_request_details lrd,ltc_type_master ltm,ltc_block_year_master lbym,emp_payment_details epd,emp_master emp where lrd.request_id=?  and ltm.id=lrd.ltc_type_id and lbym.id=lrd.ltc_block_year_id and epd.sfid=lrd.sfid and emp.sfid=lrd.sfid";
			ltcAdvanceRequestDTO = (LtcAdvanceRequestDTO)session.createSQLQuery(sql).addScalar("doPartId",Hibernate.INTEGER).addScalar("employmentTypeId",Hibernate.STRING).addScalar("gradePay",Hibernate.STRING).addScalar("doPartNo").addScalar("sfID").addScalar("addressId",Hibernate.STRING).addScalar("departmentId",Hibernate.STRING).addScalar("phoneNum",Hibernate.STRING).addScalar("designationId",Hibernate.STRING).addScalar("encashmentDays",Hibernate.STRING).addScalar("encashmentAmount",Hibernate.STRING).addScalar("encashTypeId",Hibernate.STRING).addScalar("leaveRequestId",Hibernate.STRING).addScalar("referenceRequestID",Hibernate.STRING).addScalar("status",Hibernate.INTEGER).addScalar("ltcType").addScalar("ltcBlockYear").addScalar("ltcTypeId",Hibernate.STRING).addScalar("ltcBlockYearId",Hibernate.STRING).addScalar("placeOfVisit").addScalar("nearestRlyStation").addScalar("returnDate",Hibernate.DATE).addScalar("departureDate",Hibernate.DATE).setString(0, referenceRequestID).setResultTransformer(Transformers.aliasToBean(LtcAdvanceRequestDTO.class)).uniqueResult();
		}catch (Exception e) {
			throw e;
		}
		return ltcAdvanceRequestDTO;
	}
	@SuppressWarnings("unchecked")
	public LtcApplicationBean tourSettlementList(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		String sql=null;
		try {
			// commit transaction & close the session
			hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			/**
			 * advance list
			 *///s
			sql= "SELECT lard.encashment_days encashmentDays,  get_history_id(lard.request_id) historyID,  ecd.request_id id,  fd.accountent_sign accountentSign,  fd.amount amountClaimed,  emp.sfid sfID,  Emp.Name_In_Service_Book Name,  Fd.Sanction_Number Sanctionno,  Fd.Bill_Number Billno From Emp_Claim_Details Ecd Left Outer Join Finance_Details Fd ON(ecd.id=fd.reference_id) Left Outer Join Ltc_Advance_Request_Details Lard On (Ecd.Request_Id=Lard.Request_Id),   Emp_Master Emp Where Emp.Sfid          =Lard.Sfid And Ecd.Request_Type ='advance' And Ecd.Workflow_Status =29 And Ecd.Status  =1 AND  fd.sanction_number  IS  NULL AND fd.amount  IS NOT NULL ";
//changes for cda rakesh
			//sql= "select lard.encashment_days encashmentDays,get_history_id(lard.request_id) historyID,ecd.request_id id,fd.accountent_sign accountentSign,fd.amount amountClaimed,emp.sfid sfID,emp.name_in_service_book name,fd.sanction_number sanctionNo,fd.bill_number billNo,cd.dv_no dvNo,to_char(cd.dv_date,'dd-Mon-yyyy') dvDate from emp_claim_details ecd left outer join finance_details fd on(ecd.id=fd.reference_id) left outer join cda_details cd on(fd.id=cd.reference_id) left outer join ltc_advance_request_details lard on (ecd.request_id=lard.request_id),emp_master emp where emp.sfid=lard.sfid and ecd.request_type='advance' and ecd.workflow_status =29 and ecd.status=1 and fd.amount is not null and cd.cda_amount is null ";
//			sql = "select lard.encashment_days encashmentDays,(select max(id)  from request_workflow_history where request_id=lard.request_id) historyID,lard.request_id id,lard.accountent_sign accountentSign,lard.issued_amount amountClaimed,emp.sfid sfID,emp.name_in_service_book name,lard.sanction_no sanctionNo,lard.bill_no billNo,lard.dv_no dvNo,to_char(lard.dv_date,'dd-Mon-yyyy') dvDate " 
//				+"from ltc_advance_request_details lard,emp_master emp where lard.status=29 and lard.issued_amount is not null and lard.cda_amount is null and emp.sfid=lard.sfid";

			ltcBean.setAdvanceList(session.createSQLQuery(sql).addScalar("sfID").addScalar("encashmentDays", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("name").addScalar("accountentSign").addScalar("amountClaimed",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			
			/**
			 * settlement list
			 */
			//sql = "select * from emp_claim_details ecd left outer join finance_details fd on(ecd.id=fd.reference_id) left outer join cda_details cd on(fd.id=cd.reference_id) where ecd.request_type='settlement' and ecd.workflow_status =8 and ecd.status=1 and cd.cda_amount is null";
			/*sql = "select (select max(id)  from request_workflow_history where request_id=lard.request_id) historyID,(case when lard.cda_amount-reim.total_amount > 0 then 'ltcNillSett' else 'ltcFinSett' end) type,reim.request_id id,emp.sfid sfID,emp.name_in_service_book name,reim.accountent_sign accountentSign,reim.bill_no billNo,reim.sanction_no sanctionNo,reim.amount amountClaimed,reim.dv_no dvNo,to_char(reim.dv_date,'dd-Mon-yyyy') dvDate "
				+"from ltc_reimbursement_details reim,emp_master emp,ltc_advance_request_details lard where reim.request_type='settlement' and lard.request_id=reim.reference_id and emp.sfid=lard.sfid and reim.status=8 and reim.cda_amount is null";
			*/
			
			
			sql = "select (select max(id)  from request_workflow_history where request_id=lard.request_id) historyID,(case when lard.cda_amount-reim.total_amount > 0 then 'ltcNillSett' else 'ltcFinSett' end) type,reim.request_id id,emp.sfid sfID,emp.name_in_service_book name,reim.accountent_sign accountentSign,reim.bill_no billNo,reim.sanction_no sanctionNo,reim.amount amountClaimed "
				+"from ltc_reimbursement_details reim,emp_master emp,ltc_advance_request_details lard where reim.request_type='settlement' and lard.request_id=reim.reference_id and emp.sfid=lard.sfid and reim.status=8 and reim.Bill_No  is null";
			
			ltcBean.setSettlementList(session.createSQLQuery(sql).addScalar("historyID", Hibernate.STRING).addScalar("type").addScalar("sfID").addScalar("id", Hibernate.STRING).addScalar("name").addScalar("accountentSign").addScalar("amountClaimed",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			
			/**
			 * Reimbursement list
			 */
			
			sql = "Select Get_History_Id(Ecd.Request_Id) Historyid, Ecd.Request_Id Id, Emp.Name_In_Service_Book Name, Emp.Sfid Sfid, Fd.Amount Amountclaimed,Fd.Sanction_Number Sanctionno, Fd.Bill_Number Billno, Fd.Accountent_Sign Accountentsign From Ltc_Reimbursement_Details Reim Left Outer Join Emp_Claim_Details Ecd On (Reim.Request_Id=Ecd.Request_Id) Left Outer Join Finance_Details Fd On(Ecd.Id=Fd.Reference_Id) Left Outer Join Ltc_Reimbursement_Details Lrd On(Lrd.Request_Id=Ecd.Request_Id) Left Outer Join Emp_Master Emp On (Lrd.Sfid =Emp.Sfid) Where Ecd.Request_Type  ='reimbursement' AND ecd.workflow_status =8 And Ecd.Status =1 and  fd.sanction_number is null";
//			
			//cda sql = "select get_history_id(ecd.request_id) historyID,ecd.request_id id,emp.name_in_service_book name,emp.sfid sfID,fd.amount amountClaimed,fd.sanction_number sanctionNo,fd.bill_number billNo,fd.accountent_sign accountentSign,to_char(cd.dv_date,'dd-Mon-yyyy') dvDate,cd.dv_no dvNo from ltc_reimbursement_details reim LEFT OUTER JOIN  emp_claim_details ecd on (reim.request_id=ecd.request_id) left outer join finance_details fd on(ecd.id=fd.reference_id) left outer join cda_details cd on(fd.id=cd.reference_id) left outer join ltc_reimbursement_details lrd on(lrd.request_id=ecd.request_id) left outer join emp_master emp on (lrd.sfid=emp.sfid)  where ecd.request_type='reimbursement' and ecd.workflow_status =8 and ecd.status=1 and cd.cda_amount is null";
//			sql = "select (select max(id)  from request_workflow_history where request_id=reim.request_id) historyID,reim.request_id id,emp.name_in_service_book name,reim.sfid sfID,reim.amount amountClaimed,reim.sanction_no sanctionNo,reim.bill_no billNo,reim.accountent_sign accountentSign,to_char(reim.dv_date,'dd-Mon-yyyy') dvDate,reim.dv_no dvNo "
//				+"from ltc_reimbursement_details reim,emp_master emp where reim.request_type='reimbursement' and reim.cda_amount is null and reim.status=8 and emp.sfid=reim.sfid";
			
			//ltcBean.setReimbursementList(session.createSQLQuery(sql).addScalar("sfID").addScalar("historyID", Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("name").addScalar("accountentSign").addScalar("amountClaimed",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			ltcBean.setReimbursementList(session.createSQLQuery(sql).addScalar("sfID").addScalar("historyID", Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("name").addScalar("accountentSign").addScalar("amountClaimed",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			//sql =  "Select Get_History_Id(Ecd.Request_Id) Historyid,Ecd.Request_Id Id, lrd.sfid sfID, TO_CHAR(ecd.total_amount) encashmentAmount, fd.accountent_sign accountentSign, emp.name_in_service_book name,Lrd.Encashment_Days Encashmentdays, fd.sanction_number sanctionNo, Fd.Bill_Number Billno FROM emp_claim_details ecd Left Outer Join Finance_Details Fd ON(ecd.id=fd.reference_id) LEFT OUTER JOIN (SELECT request_id, sfid, encashment_days  FROM ltc_request_details  Where Status Not In(9,6)  UNION  SELECT request_id,   sfid,  encashment_days   FROM ltc_advance_request_details   Where Status Not In (9,6)   ) lrd On(Ecd.Request_Id=Lrd.Request_Id) LEFT OUTER JOIN emp_master emp ON(lrd.sfid              =emp.sfid) Where Ecd.Request_Type   ='encashment' And Ecd.Workflow_Status In(29,8) AND ecd.status =1 And  fd.sanction_number Is Null";
			// cda  sql = "select get_history_id(ecd.request_id) historyID,ecd.request_id id,lrd.sfid sfID,to_char(ecd.total_amount) encashmentAmount,fd.accountent_sign accountentSign,emp.name_in_service_book name,lrd.encashment_days encashmentDays,fd.sanction_number sanctionNo,fd.bill_number billNo from emp_claim_details ecd left outer join finance_details fd on(ecd.id=fd.reference_id) left outer join cda_details cd on(fd.id=cd.reference_id)left outer join (select request_id,sfid,encashment_days from ltc_request_details where status not in(9,6) union select request_id,sfid,encashment_days from ltc_advance_request_details where status not in (9,6))  lrd on(ecd.request_id=lrd.request_id) left outer join emp_master emp on(lrd.sfid=emp.sfid) where ecd.request_type='encashment' and ecd.workflow_status in(29,8) and ecd.status=1 and cd.dv_no is null";
//			sql = "select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,lrd.request_id id,lrd.sfid sfID,emp.name_in_service_book name,lrd.encashment_days encashmentDays,lrd.sanction_no sanctionNo,lrd.bill_no billNo from ltc_request_details lrd,emp_master emp "
//					+"where lrd.status not in(6,9) and lrd.encashment_days is not null and lrd.dv_date is null and emp.sfid=lrd.sfid";
			
			
			//code has been changed for getting the davalue in :::::Rakesh
			/*sql="SELECT Get_History_Id(Ecd.Request_Id) Historyid, Ecd.Request_Id Id, lrd.sfid sfID, TO_CHAR(ecd.total_amount) encashmentAmount, fd.accountent_sign accountentSign, emp.name_in_service_book name, Lrd.Encashment_Days Encashmentdays,  fd.sanction_number sanctionno, fd.bill_number billno, nvl(epd.basic_pay,0) basicPay,nvl(epd.grade_pay,0) gradePay,lrd.applied_date appliedDate,(select max(da_value) from pay_dearness_allowance_master where da_date<=lrd.applied_date) as daValue from emp_claim_details ecd left outer join finance_details fd on(ecd.id=fd.reference_id) left outer join  (select request_id,creation_date applied_date,    sfid, encashment_days from ltc_request_details  where status not in(9,6)  union  select request_id,  applied_date, sfid,  encashment_days  FROM ltc_advance_request_details WHERE Status NOT IN (9,6) ) lrd ON(Ecd.Request_Id=Lrd.Request_Id)left outer join emp_master emp on(lrd.sfid  =emp.sfid)  left outer join emp_payment_details epd on (epd.sfid=lrd.sfid)where ecd.request_type   ='encashment'and ecd.workflow_status in(29,8)"
			+"and ecd.status  =1 AND fd.sanction_number  IS NULL";*/
		    	sql="select get_history_id(ecd.request_id) historyid,  ecd.request_id id,  lrd.sfid sfid,  to_char(ecd.total_amount) encashmentamount,  fd.accountent_sign accountentsign,  emp.name_in_service_book name,  lrd.encashment_days encashmentdays,  fd.sanction_number sanctionno,  fd.bill_number billno,  nvl(epd.basic_pay,0) basicpay,  nvl(epd.grade_pay,0) gradepay,  lrd.applied_date  applieddate ,  (select max(pdam1.da_value)  from pay_dearness_allowance_master pdam1"
                    +" where pdam1.da_date<=lrd.applied_date ) as daValue ,( select calculate_ltc_da_val(epd.basic_pay,epd.grade_pay,( select max(pdam1.da_value) as da  from pay_dearness_allowance_master pdam1  where pdam1.da_date<=lrd.applied_date ),lrd.encashment_days )from dual) as enchashmentamountDavalue from emp_claim_details ecd left outer join finance_details fd on(ecd.id=fd.reference_id) left outer join  (select request_id, creation_date applied_date,    sfid, encashment_days  from ltc_request_details  where status not in(9,6)  union  select request_id, applied_date, sfid, encashment_days"
                   +" from ltc_advance_request_details  where status not in (9,6)  ) lrd on(ecd.request_id=lrd.request_id) left outer join emp_master emp on(lrd.sfid =emp.sfid) left outer join emp_payment_details epd on (epd.sfid =lrd.sfid) where ecd.request_type   ='encashment' and ecd.workflow_status in(29,8) and ecd.status  =1 AND fd.sanction_number  IS NULL";
			
			
			
			ltcBean.setEncashmentList(session.createSQLQuery(sql).addScalar("sfID").addScalar("encashmentAmount").addScalar("accountentSign").addScalar("historyID", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("name").addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("basicPay",Hibernate.INTEGER).addScalar("gradePay",Hibernate.STRING).addScalar("daValue", Hibernate.INTEGER).addScalar("enchashmentamountDavalue", Hibernate.INTEGER).addScalar("appliedDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			
			ltcBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
			
			ltcBean.setCfaOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "CFA")).add(Expression.eq("status", 1)).list());

			
		}catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}
	
	
	@SuppressWarnings("unchecked")
	public LtcApplicationBean cdatourSettlementList(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		String sql=null;
		try {
			// commit transaction & close the session
			hibernateUtils.closeSession();
			session = hibernateUtils.getSession();
			/**
			 * advance list
			 *///s
			sql= "SELECT lard.encashment_days encashmentDays, get_history_id(lard.request_id) historyID, ecd.request_id id, fd.accountent_sign accountentSign, Fd.Amount Amountclaimed, emp.sfid sfID, emp.name_in_service_book name, fd.sanction_number sanctionNo, fd.bill_number billNo, cd.dv_no dvNo, TO_CHAR(cd.dv_date,'dd-Mon-yyyy') dvDate FROM emp_claim_details ecd LEFT OUTER JOIN finance_details fd ON(ecd.id=fd.reference_id) LEFT OUTER JOIN cda_details cd ON(fd.id=cd.reference_id) LEFT OUTER JOIN ltc_advance_request_details lard ON (ecd.request_id=lard.request_id), emp_master emp WHERE emp.sfid =lard.sfid AND ecd.request_type  ='advance' AND ecd.workflow_status =29 And Ecd.Status  =1 AND fd.amount    IS NOT NULL And Cd.Cda_Amount Is Null  And Fd.Fd.Sanction_Number Is Not Null AND  fd.bill_number is not null ";
//			sql = "select lard.encashment_days encashmentDays,(select max(id)  from request_workflow_history where request_id=lard.request_id) historyID,lard.request_id id,lard.accountent_sign accountentSign,lard.issued_amount amountClaimed,emp.sfid sfID,emp.name_in_service_book name,lard.sanction_no sanctionNo,lard.bill_no billNo,lard.dv_no dvNo,to_char(lard.dv_date,'dd-Mon-yyyy') dvDate " 
//				+"from ltc_advance_request_details lard,emp_master emp where lard.status=29 and lard.issued_amount is not null and lard.cda_amount is null and emp.sfid=lard.sfid";

			ltcBean.setCdaadvanceList(session.createSQLQuery(sql).addScalar("sfID").addScalar("encashmentDays", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("name").addScalar("accountentSign").addScalar("amountClaimed",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			
			/**
			 * settlement list
			 */
			//sql = "select * from emp_claim_details ecd left outer join finance_details fd on(ecd.id=fd.reference_id) left outer join cda_details cd on(fd.id=cd.reference_id) where ecd.request_type='settlement' and ecd.workflow_status =8 and ecd.status=1 and cd.cda_amount is null";
			/*sql = "select (select max(id)  from request_workflow_history where request_id=lard.request_id) historyID,(case when lard.cda_amount-reim.total_amount > 0 then 'ltcNillSett' else 'ltcFinSett' end) type,reim.request_id id,emp.sfid sfID,emp.name_in_service_book name,reim.accountent_sign accountentSign,reim.bill_no billNo,reim.sanction_no sanctionNo,reim.amount amountClaimed,reim.dv_no dvNo,to_char(reim.dv_date,'dd-Mon-yyyy') dvDate "
				+"from ltc_reimbursement_details reim,emp_master emp,ltc_advance_request_details lard where reim.request_type='settlement' and lard.request_id=reim.reference_id and emp.sfid=lard.sfid and reim.status=8 and reim.cda_amount is null";
		*/	
			
			
			sql="Select (Select Max(Id) From Request_Workflow_History Where Request_Id=Lard.Request_Id) Historyid, (Case When Lard.Cda_Amount-Reim.Total_Amount > 0 Then 'ltcNillSett' Else 'ltcFinSett' End) Type,Reim.Request_Id Id, Emp.Sfid Sfid, Emp.Name_In_Service_Book Name,Reim.Accountent_Sign Accountentsign, Reim.Bill_No Billno, Reim.Sanction_No Sanctionno,Reim.Amount Amountclaimed FROM ltc_reimbursement_details reim, Emp_Master Emp, Ltc_Advance_Request_Details Lard WHERE reim.request_type='settlement' And Lard.Request_Id    =Reim.Reference_Id And Emp.Sfid =Lard.Sfid AND reim.status =8 And Reim.Cda_Amount   Is Null And Reim.Bill_No   is not null And  reim.sanction_no is not null";
			ltcBean.setCdasettlementList(session.createSQLQuery(sql).addScalar("historyID", Hibernate.STRING).addScalar("type").addScalar("sfID").addScalar("id", Hibernate.STRING).addScalar("name").addScalar("accountentSign").addScalar("amountClaimed",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			
			/**
			 * Reimbursement list
			 */
			sql = "Select Get_History_Id(Ecd.Request_Id) Historyid, Ecd.Request_Id Id, Emp.Name_In_Service_Book Name, Emp.Sfid Sfid, fd.amount amountClaimed, fd.sanction_number sanctionNo, fd.bill_number billNo, fd.accountent_sign accountentSign  From Ltc_Reimbursement_Details Reim Left Outer Join Emp_Claim_Details Ecd On (Reim.Request_Id=Ecd.Request_Id) Left Outer Join Finance_Details Fd ON(ecd.id=fd.reference_id) LEFT OUTER JOIN cda_details cd On(Fd.Id=Cd.Reference_Id) Left Outer Join Ltc_Reimbursement_Details Lrd On(Lrd.Request_Id=Ecd.Request_Id) Left Outer Join Emp_Master Emp On (Lrd.Sfid            =Emp.Sfid)Where Ecd.Request_Type  ='reimbursement' And Ecd.Workflow_Status =8 And Ecd.Status =1 And Cd.Cda_Amount  Is Null And Fd.Sanction_Number Is Not Null And  Fd.Bill_Number Is Not Null";
//			sql = "select (select max(id)  from request_workflow_history where request_id=reim.request_id) historyID,reim.request_id id,emp.name_in_service_book name,reim.sfid sfID,reim.amount amountClaimed,reim.sanction_no sanctionNo,reim.bill_no billNo,reim.accountent_sign accountentSign,to_char(reim.dv_date,'dd-Mon-yyyy') dvDate,reim.dv_no dvNo "
//				+"from ltc_reimbursement_details reim,emp_master emp where reim.request_type='reimbursement' and reim.cda_amount is null and reim.status=8 and emp.sfid=reim.sfid";
			
			ltcBean.setCdareimbursementList(session.createSQLQuery(sql).addScalar("sfID").addScalar("historyID", Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("name").addScalar("accountentSign").addScalar("amountClaimed",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
		//sql = "Select Get_History_Id(Ecd.Request_Id) Historyid, ecd.request_id id, lrd.sfid sfID,  TO_CHAR(ecd.total_amount) encashmentAmount, fd.accountent_sign accountentSign, fd.rep_Sig as cfaSign, emp.name_in_service_book name, lrd.encashment_days encashmentDays, Fd.Sanction_Number Sanctionno, Fd.Bill_Number Billno FROM emp_claim_details ecd LEFT OUTER JOIN finance_details fd ON(ecd.id=fd.reference_id) Left Outer Join Cda_Details Cd On(Fd.Id=Cd.Reference_Id) Left Outer Join (Select Request_Id, Sfid, Encashment_Days From Ltc_Request_Details Where Status Not In(9,6) UNION SELECT request_id, Sfid, Encashment_Days   From Ltc_Advance_Request_Details  Where Status Not In (9,6)   ) Lrd On(Ecd.Request_Id=Lrd.Request_Id) Left Outer Join Emp_Master Emp On(Lrd.Sfid              =Emp.Sfid) Where Ecd.Request_Type   ='encashment' AND ecd.workflow_status IN(29,8) And Ecd.Status  =1 And Fd.Sanction_Number Is Not Null and   fd.bill_number is not null And Cd.CDA_amount is null";
			sql = "Select Get_History_Id(Ecd.Request_Id) Historyid, ecd.request_id id, lrd.sfid sfID,  To_char( fd.amount) encashmentAmount, fd.accountent_sign accountentSign, fd.rep_Sig as cfaSign, emp.name_in_service_book name, lrd.encashment_days encashmentDays, Fd.Sanction_Number Sanctionno, Fd.Bill_Number Billno FROM emp_claim_details ecd LEFT OUTER JOIN finance_details fd ON(ecd.id=fd.reference_id) Left Outer Join Cda_Details Cd On(Fd.Id=Cd.Reference_Id) Left Outer Join (Select Request_Id, Sfid, Encashment_Days From Ltc_Request_Details Where Status Not In(9,6) UNION SELECT request_id, Sfid, Encashment_Days   From Ltc_Advance_Request_Details  Where Status Not In (9,6)   ) Lrd On(Ecd.Request_Id=Lrd.Request_Id) Left Outer Join Emp_Master Emp On(Lrd.Sfid              =Emp.Sfid) Where Ecd.Request_Type   ='encashment' AND ecd.workflow_status IN(29,8) And Ecd.Status  =1 And Fd.Sanction_Number Is Not Null and   fd.bill_number is not null And Cd.CDA_amount is null";

	  			//sql = "Select Get_History_Id(Ecd.Request_Id) Historyid, ecd.request_id id, lrd.sfid sfID,  TO_CHAR(ecd.total_amount) encashmentAmount, fd.accountent_sign accountentSign, emp.name_in_service_book name, lrd.encashment_days encashmentDays, Fd.Sanction_Number Sanctionno, Fd.Bill_Number Billno FROM emp_claim_details ecd LEFT OUTER JOIN finance_details fd ON(ecd.id=fd.reference_id) Left Outer Join Cda_Details Cd On(Fd.Id=Cd.Reference_Id) Left Outer Join (Select Request_Id, Sfid, Encashment_Days From Ltc_Request_Details Where Status Not In(9,6) UNION SELECT request_id, Sfid, Encashment_Days   From Ltc_Advance_Request_Details  Where Status Not In (9,6)   ) Lrd On(Ecd.Request_Id=Lrd.Request_Id) Left Outer Join Emp_Master Emp On(Lrd.Sfid              =Emp.Sfid) Where Ecd.Request_Type   ='encashment' AND ecd.workflow_status IN(29,8) And Ecd.Status  =1 And Fd.Sanction_Number Is Not Null and   fd.bill_number is not null";
//			sql = "select (select max(id)  from request_workflow_history where request_id=lrd.request_id) historyID,lrd.request_id id,lrd.sfid sfID,emp.name_in_service_book name,lrd.encashment_days encashmentDays,lrd.sanction_no sanctionNo,lrd.bill_no billNo from ltc_request_details lrd,emp_master emp "
//					+"where lrd.status not in(6,9) and lrd.encashment_days is not null and lrd.dv_date is null and emp.sfid=lrd.sfid";
			
			
			ltcBean.setCdaencashmentList(session.createSQLQuery(sql).addScalar("sfID").addScalar("encashmentAmount").addScalar("accountentSign").addScalar("historyID", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("name").addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("cfaSign", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).list());
			
			ltcBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
			
			ltcBean.setCfaOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "CFA")).add(Expression.eq("status", 1)).list());

			
			
			
		}catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}
	
	
	
	
	public String saveCdaAmountAdvance(String requestId,String cdaAmount,String sanctionNo,String billNo,String dvNo,String dvDate,String accOfficer)throws Exception{
		Session session = null;
		String message=null;
		LtcAdvanceRequestDTO ltcAdvance=null;
		try {
			session=hibernateUtils.getSession();
			ltcAdvance = (LtcAdvanceRequestDTO)session.get(LtcAdvanceRequestDTO.class, requestId);
			if(!CPSUtils.isNullOrEmpty(cdaAmount)){
				ltcAdvance.setCdaAmount(cdaAmount);
			}if(!CPSUtils.isNullOrEmpty(sanctionNo)){
				ltcAdvance.setSanctionNo(sanctionNo);
			}if(!CPSUtils.isNullOrEmpty(billNo)){
				ltcAdvance.setBillNo(billNo);
			}if(!CPSUtils.isNullOrEmpty(dvNo)){
				ltcAdvance.setDvNo(dvNo);
			}if(!CPSUtils.isNullOrEmpty(dvDate)){
				ltcAdvance.setDvDate(CPSUtils.convertStringToDate(dvDate));
			}if(!CPSUtils.isNullOrEmpty(accOfficer)){
				ltcAdvance.setAccountentSign(accOfficer);
			}
			session.saveOrUpdate(ltcAdvance);
			hibernateUtils.commitTransaction();			
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String saveCdaAmountReimbursement(String requestId,String cdaAmount,String sanctionNo,String billNo,String dvNo,String dvDate,String accOfficer)throws Exception {
		Session session = null;
		String message=null;
		LtcReimbursementDTO ltcReim=null;
		try {
			session=hibernateUtils.getSession();
			ltcReim = (LtcReimbursementDTO)session.get(LtcReimbursementDTO.class, requestId);
			if(!CPSUtils.isNullOrEmpty(cdaAmount)){
				ltcReim.setCdaAmount(cdaAmount);
			}if(!CPSUtils.isNullOrEmpty(sanctionNo)){
				ltcReim.setSanctionNo(sanctionNo);
			}if(!CPSUtils.isNullOrEmpty(billNo)){
				ltcReim.setBillNo(billNo);
			}if(!CPSUtils.isNullOrEmpty(dvNo)){
				ltcReim.setDvNo(dvNo);
			}if(!CPSUtils.isNullOrEmpty(dvDate)){
				ltcReim.setDvDate(CPSUtils.convertStringToDate(dvDate));
			}if(!CPSUtils.isNullOrEmpty(accOfficer)){
				ltcReim.setAccountentSign(accOfficer);
			}
			session.saveOrUpdate(ltcReim);
			message=CPSConstants.SUCCESS;
			hibernateUtils.commitTransaction();
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public LtcApplicationBean amendmentDetails(String requestId,String requestType,LtcApplicationBean ltcBean)throws Exception {
		Session session = null;
		String sql=null;
		LtcApplicationBean ltcAppBean=null;
		try {
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings("ltcAdvance", requestType)) {
				sql = "select lard.nearest_railway_station nearestRlyStation, lard.no_of_infant_tickets noOfInfantTickets,lard.amount_per_each_infant amountPerEachInfant,lard.issued_amount issuedAmount,(select lard.leave_request_id||'#'||ltm.leave_type||'('||leave.from_date||' To '||leave.to_date||')'from leave_request_details leave, "
					+"leave_type_master ltm where leave.request_id=lard.leave_request_id and ltm.id=leave.leave_type_id) leaveDetails,case when lard.issued_amount is null then 'ltcAdvance' else 'ltcApproval' end type,'ltcAdvanceAmendment' typeValue " 
					+",lard.request_id id,lard.sfid sfID,lard.ltc_type_id ltcTypeId,lard.place_of_visit placeOfVisit,to_char(lard.departure_date,'dd-Mon-yyyy') formatedDepartureDate "
					+",lard.leave_request_id leaveRequestId,lard.encash_leave_type_id encashTypeId,lard.encashment_days encashmentDays,lard.ltc_block_year_id ltcBlockYearId "
					+",to_char(lard.return_date,'dd-Mon-yyyy') formatedReturnDate,lard.amount_claimed amountClaimed,lard.no_of_tickets noOfTickets,lard.amount_per_person amountPerPerson "
					+" from ltc_advance_request_details lard where lard.request_id=?";
				ltcAppBean = (LtcApplicationBean)session.createSQLQuery(sql).addScalar("nearestRlyStation").addScalar("noOfInfantTickets",Hibernate.STRING).addScalar("amountPerEachInfant",Hibernate.STRING).addScalar("issuedAmount",Hibernate.STRING).addScalar("typeValue",Hibernate.STRING).addScalar("type",Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("leaveDetails").addScalar("sfID").addScalar("ltcTypeId",Hibernate.STRING).addScalar("placeOfVisit").addScalar("formatedDepartureDate",Hibernate.STRING).addScalar("leaveRequestId",Hibernate.STRING).addScalar("encashTypeId", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("ltcBlockYearId", Hibernate.STRING).addScalar("formatedReturnDate",Hibernate.STRING).addScalar("amountClaimed", Hibernate.STRING).addScalar("noOfTickets", Hibernate.STRING).addScalar("amountPerPerson", Hibernate.STRING).setString(0, requestId).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).uniqueResult();
			}else {
				sql = "select lrd.nearest_railway_station nearestRlyStation,(select lrd.leave_request_id||'#'||ltm.leave_type||'('||leave.from_date||' To '||leave.to_date||')'from leave_request_details leave,leave_type_master ltm where leave.request_id=lrd.leave_request_id and ltm.id=leave.leave_type_id) leaveDetails,'ltcApproval' type,'ltcApprovalAmendment' typeValue,lrd.request_id id,lrd.sfid sfID,lrd.ltc_type_id ltcTypeId,lrd.place_of_visit placeOfVisit,to_char(lrd.departure_date,'dd-Mon-yyyy') formatedDepartureDate,lrd.leave_request_id leaveRequestId,lrd.encash_leave_type_id encashTypeId, "
					+"lrd.encashment_days encashmentDays,to_char(lrd.return_date,'dd-Mon-yyyy') formatedReturnDate,lrd.ltc_block_year_id ltcBlockYearId "
					+"from ltc_request_details lrd where lrd.request_id=?";
				ltcAppBean = (LtcApplicationBean)session.createSQLQuery(sql).addScalar("nearestRlyStation").addScalar("formatedReturnDate",Hibernate.STRING).addScalar("typeValue",Hibernate.STRING).addScalar("type",Hibernate.STRING).addScalar("id", Hibernate.STRING).addScalar("leaveDetails").addScalar("sfID").addScalar("ltcTypeId",Hibernate.STRING).addScalar("placeOfVisit").addScalar("formatedDepartureDate",Hibernate.STRING).addScalar("leaveRequestId",Hibernate.STRING).addScalar("encashTypeId", Hibernate.STRING).addScalar("encashmentDays", Hibernate.STRING).addScalar("ltcBlockYearId", Hibernate.STRING).setString(0, requestId).setResultTransformer(Transformers.aliasToBean(LtcApplicationBean.class)).uniqueResult();
			}
			
			ltcAppBean.setFamilyMember((String)session.createSQLQuery("select rtrim (xmlagg (xmlelement (e, family_member_id || ',')).extract ('//text()'), ',') familyMember from ltc_txn_details where request_id=?").setString(0, requestId).uniqueResult());
			ltcAppBean.setCancelReqIssuedAmount(ltcBean.getCancelReqIssuedAmount());
			ltcAppBean.setCancelReqCdaAmount(ltcBean.getCancelReqCdaAmount());
			ltcAppBean.setCancelReqDoPartId(ltcBean.getCancelReqDoPartId());
			ltcAppBean.setCancelReqHistoryId(ltcBean.getCancelReqHistoryId());
			ltcAppBean.setCancelReqRequestId(ltcBean.getId());
			ltcAppBean.setCancelReqRequestType(ltcBean.getType());
			
		}catch (Exception e) {
			throw e;
		}
		return ltcAppBean;
	}
	@SuppressWarnings("unchecked")
	public List<FamilyBean> approvedFamilyMember(String referenceRequestID) throws Exception {
		List<FamilyBean> list = null;
		Session session = null;
		try {
			String query = "select fd.id id,fd.name name,to_char(fd.dob,'dd-Mon-yyyy')as dob,TO_CHAR(sysdate) AS toDay, case when fd.dob  is null then fd.age else round(months_between(sysdate,fd.dob)/12)||'' end age ,frm.name relation "  
							+" from ltc_txn_details ltc,family_details fd,family_relation_master frm where ltc.request_id=? and fd.id=ltc.family_member_id and fd.relation_id=frm.id";
			session = hibernateUtils.getSession();
			list = session.createSQLQuery(query).addScalar("id",Hibernate.STRING).addScalar("name").addScalar("toDay", Hibernate.STRING).addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("relation").setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).setString(0, referenceRequestID).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String saveEncashmentAmount(String requestId,String billNo,String sanctionNo,String dvNo,String dvDate) throws Exception {
		Session session = null;
		String message=null;
		LtcApprovalRequestDTO ltcApp=null;
		try {
			session=hibernateUtils.getSession();
			ltcApp = (LtcApprovalRequestDTO)session.get(LtcApprovalRequestDTO.class, requestId);
			if(!CPSUtils.isNullOrEmpty(sanctionNo)){
				ltcApp.setSanctionNo(sanctionNo);
			}if(!CPSUtils.isNullOrEmpty(billNo)){
				ltcApp.setBillNo(billNo);
			}if(!CPSUtils.isNullOrEmpty(dvNo)){
				ltcApp.setDvNo(dvNo);
			}if(!CPSUtils.isNullOrEmpty(dvDate)){
				ltcApp.setDvDate(CPSUtils.convertStringToDate(dvDate));
			}
			session.saveOrUpdate(ltcApp);
			hibernateUtils.commitTransaction();
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<LtcBlockMasterDTO> getAdminEntryBlockYearList(LtcApplicationBean ltcBean) throws Exception {
		List<LtcBlockMasterDTO> list = null;
		Session session = null;
		StringBuffer sql=new StringBuffer();
		try {
			
			session = hibernateUtils.getSession();
			sql.append("select case when (select count(*) from ltc_block_year_master where from_date <= (select lbym.from_date from ltc_block_year_master lbym where status=1 and (select doj_drdo from emp_master where sfid=?) "
					+ "between lbym.from_date and lbym.to_date and lbym.ltc_block_id=1) and status=1 and ltc_block_id=?)>2 then ? else (case when (select to_char(doj_drdo,'YYYY') from emp_master where sfid=?)<2006 then ? else ? end) end from dual");
			String result = session.createSQLQuery(sql.toString()).setString(0, ltcBean.getRefSfID()).setString(1, CPSConstants.FOURYEARBLOCKID).setString(2, CPSConstants.EXPERIENCE).setString(3, ltcBean.getRefSfID()).setString(4,
					CPSConstants.EXPERIENCE).setString(5, CPSConstants.FRESHER).uniqueResult().toString();
			sql.setLength(0);
			sql.append("select id,case when extended_date is null then to_char(from_date,'yyyy') else to_char(from_date,'yyyy')||'-'||to_char(to_date,'yy') end name from ltc_block_year_master where status=1 and four_year_block_id is not null ");
			if (CPSUtils.compareStrings(result, CPSConstants.EXPERIENCE)) {
				sql.append("and ltc_block_id=2");
			}else {
				sql.append("and ltc_block_id=1");
			}
			sql.append("and four_year_block_id=(select id from ltc_block_year_master where sysdate between from_date and to_date and four_year_block_id is null)");
			list = session.createSQLQuery(sql.toString()).addScalar("id",Hibernate.INTEGER).addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcBlockMasterDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}

	public String saveRequestDetails(LtcApprovalRequestDTO requestDTO) throws Exception {
		Session session = null;
		String message=null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(requestDTO);
			message=CPSConstants.SUCCESS;
		} catch (Exception e) {
			message=CPSConstants.FAILED;
			throw e;
		} 
		return message;
	}

	public String saveMemberDetails(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();

			String[] familyIDs = ltcBean.getFamilyMemberId().split(",");
			for (String familyID : familyIDs) {
				LtcMemberDetailsDTO memberDetailsDTO = new LtcMemberDetailsDTO();
				memberDetailsDTO.setFamilyMemberId(familyID);
				memberDetailsDTO.setLtcType(ltcBean.getLtcTypeId());
				memberDetailsDTO.setBlockYear(ltcBean.getLtcBlockYearId());
				memberDetailsDTO.setRequestId(ltcBean.getRequestIDs());
				memberDetailsDTO.setStatus("1");
				memberDetailsDTO.setRequestType("LTC OFFLINE ENTRY");
				session.saveOrUpdate(memberDetailsDTO);
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message=CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<LtcApprovalRequestDTO> getAdminEntryDetails(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		StringBuilder sb=new StringBuilder();
		try {
			session = hibernateUtils.getSession();
			sb.append("select (case when lrd.request_type='LTC OFFLINE ENTRY' then 'OFFLINE' else 'ONLINE' END) requestType,lrd.departure_date departureDate, lrd.sfid sfID,(case when lrd.ltc_type_id=1 then to_char(lbym2.from_date,'yyyy')||'-'||to_char(lbym2.to_date,'yy') else case when lbym1.extended_date is null then to_char(lbym1.from_date,'yyyy') else to_char(lbym1.from_date,'yyyy')||'-'||to_char(lbym1.to_date,'yy') end end)  block,lrd.place_of_visit placeOfVisit, ltm.ltc_type ltcType, lrd.request_id requestID,rtrim (xmlagg (xmlelement (e, fd.name || ',')).extract ('//text()'), ',') members from ltc_request_details lrd,ltc_txn_details txn,family_details fd,ltc_type_master ltm,ltc_block_year_master lbym1,ltc_block_year_master lbym2 where txn.request_id=lrd.request_id and lrd.status=8 and lrd.ltc_block_year_id=lbym1.id and lrd.ltc_type_id=ltm.id and lbym2.id=lbym1.four_year_block_id and fd.id=txn.family_member_id");
			if (!CPSUtils.compareStrings(ltcBean.getType(),"EntireDetails")) {
				sb.append(" and lrd.request_type='LTC OFFLINE ENTRY' ");
			}
			else{
				sb.append(" and lrd.sfid='"+ltcBean.getRefSfID().toUpperCase()+"' ");
			}
			sb.append("group by  (case when lrd.request_type='LTC OFFLINE ENTRY' then 'OFFLINE' else 'ONLINE' END),lrd.departure_date,lrd.sfid,lrd.request_id,ltm.ltc_type,lrd.place_of_visit,(case when lrd.ltc_type_id=1 then to_char(lbym2.from_date,'yyyy')||'-'||to_char(lbym2.to_date,'yy') else case when lbym1.extended_date is null then to_char(lbym1.from_date,'yyyy') else to_char(lbym1.from_date,'yyyy')||'-'||to_char(lbym1.to_date,'yy') end end)");
			ltcBean.setAdminEntryDetailsList(session.createSQLQuery(sb.toString())
					.addScalar("requestType",Hibernate.STRING).addScalar("requestId",Hibernate.STRING).addScalar("sfID",Hibernate.STRING).addScalar("ltcType",Hibernate.STRING).addScalar("placeOfVisit",Hibernate.STRING)
					.addScalar("block",Hibernate.STRING).addScalar("departureDate",Hibernate.DATE).addScalar("members",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LtcApprovalRequestDTO.class)).list());
		} catch (Exception e) {
			throw e;
		} 
		return ltcBean.getAdminEntryDetailsList();
	}

	public LtcApplicationBean getCancleRequestType(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if(!CPSUtils.compareStrings(ltcBean.getType(),CPSConstants.LTCAPPROVAL)){
			ltcBean.setTypeValue(((LtcAdvanceRequestDTO)session.createCriteria(LtcAdvanceRequestDTO.class).add(Expression.eq("requestId", ltcBean.getId())).uniqueResult()).getLtcRequestType());
			}
		}catch (Exception e) {
			throw e;
		} 
		return ltcBean;
	}

	public LtcApplicationBean getLeaveDetails(LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		LeaveRequestBean lrBean = null;
		CancelLeaveRequestBean clrBean = null;
		try {
			session = hibernateUtils.getSession();
			if (!CPSUtils.isNullOrEmpty(ltcBean.getLeaveRequestId())) {
				lrBean = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, ltcBean.getLeaveRequestId())).uniqueResult();
				ltcBean.setLeaveRequestHistoryBean((RequestCommonBean)session.createSQLQuery("select to_char(max(id)) historyID,to_char(request_id) requestID,to_char(request_stage) stageID from REQUEST_WORKFLOW_HISTORY where REQUEST_ID=? and REQUEST_STAGE=(select max(REQUEST_STAGE) from REQUEST_WORKFLOW_HISTORY where REQUEST_ID=?) GROUP BY to_char(request_id), to_char(request_stage)").addScalar("historyID").addScalar("requestID").addScalar("stageID").setInteger(0, Integer.valueOf(ltcBean.getLeaveRequestId())).setInteger(1, Integer.valueOf(ltcBean.getLeaveRequestId())).setResultTransformer(Transformers.aliasToBean(RequestCommonBean.class)).uniqueResult());
				if (!CPSUtils.isNullOrEmpty(lrBean)) {
					ltcBean.setLeaveRequestBean(lrBean);
					clrBean = (CancelLeaveRequestBean) session.createCriteria(CancelLeaveRequestBean.class).add(Expression.eq(CPSConstants.REFERENCEID, lrBean.getId())).add(Expression.not(Expression.in(CPSConstants.STATUS,new Integer[]{Integer.valueOf(CPSConstants.STATUSCANCELLED),Integer.valueOf(CPSConstants.STATUSDECLINED)}))).uniqueResult();
				}
				if (!CPSUtils.isNullOrEmpty(clrBean)) {
					ltcBean.setCancelLeaveRequestBean(clrBean);
					ltcBean.setResult(CPSConstants.FAILED);
					ltcBean.setRemarks(CPSConstants.LEAVECANCLEINIT);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return ltcBean;
	}

	public String saveCdaAmount(JSONObject subJson,LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		String message=null;
		EmployeeClaimDetailsDTO employeeClaimDetailsDTO=null;
		FinanceDetailsDTO financeDetails=null;
		try{
			session = hibernateUtils.getSession();
			employeeClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(subJson.get("requestId").toString()))).add(Expression.eq("requestType", ltcBean.getType())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)) {
				financeDetails = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, employeeClaimDetailsDTO.getId())).uniqueResult();
				if (CPSUtils.isNullOrEmpty(financeDetails)) {
					FinanceDetailsDTO financeDetailsDTO = new FinanceDetailsDTO();
					financeDetailsDTO.setReferenceID(employeeClaimDetailsDTO.getId());
					if(ltcBean.getType().equals("encashment") ){						
						if(!CPSUtils.isNullOrEmpty(subJson.get("encashmentAmount"))){
							financeDetailsDTO.setAmount(Integer.valueOf(subJson.get("encashmentAmount").toString()));
						}}
					if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
						financeDetailsDTO.setSanctionNo(subJson.get("sanctionNo").toString());
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
						financeDetailsDTO.setBillNo(subJson.get("billNo").toString());
					}
					//amount
					if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
						financeDetailsDTO.setAccountentSign(subJson.get("accOfficer").toString());
					}
					if(ltcBean.getType().equals("encashment")){
						if (!CPSUtils.isNullOrEmpty(subJson.get("repSig").toString())) {
						
						financeDetailsDTO.setRepSig(subJson.get("repSig").toString());
					}}
					if(!ltcBean.getType().equals("encashment") ){
                    if (!CPSUtils.isNullOrEmpty(subJson.get("amount").toString())) {						
						financeDetailsDTO.setAmount(Float.valueOf((subJson.get("amount").toString())));
					   }}
					financeDetailsDTO.setCreatedBy(ltcBean.getSfID());
					financeDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					financeDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
					financeDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					financeDetailsDTO.setStatus(1);
					session.clear();
					session.save(financeDetailsDTO);
					session.flush();
				}
				
				else if(!CPSUtils.isNullOrEmpty(financeDetails)){
					FinanceDetailsDTO financeDetailsDTO = new FinanceDetailsDTO();
					if(!CPSUtils.isNullOrEmpty(financeDetails.getId())){
					financeDetailsDTO.setId(financeDetails.getId());
					}
					financeDetailsDTO.setReferenceID(employeeClaimDetailsDTO.getId());
					if(ltcBean.getType().equals("encashment") ){						
						if(!CPSUtils.isNullOrEmpty(subJson.get("encashmentAmount"))){
							financeDetailsDTO.setAmount(Integer.valueOf(subJson.get("encashmentAmount").toString()));
						}}
					if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
						financeDetailsDTO.setSanctionNo(subJson.get("sanctionNo").toString());
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
						financeDetailsDTO.setBillNo(subJson.get("billNo").toString());
					}
					//amount
					if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
						financeDetailsDTO.setAccountentSign(subJson.get("accOfficer").toString());
					}
					if(ltcBean.getType().equals("encashment")){
					if (!CPSUtils.isNullOrEmpty(subJson.get("repSig").toString())) {						
						financeDetailsDTO.setRepSig(subJson.get("repSig").toString());
					}}
					if(!ltcBean.getType().equals("encashment") ){	
                  if (!CPSUtils.isNullOrEmpty(subJson.get("amount").toString())) {						
						financeDetailsDTO.setAmount(Float.valueOf((subJson.get("amount").toString())));
					   }}
					financeDetailsDTO.setCreatedBy(ltcBean.getSfID());
					financeDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					financeDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
					financeDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					financeDetailsDTO.setStatus(1);
					session.clear();
					session.update(financeDetailsDTO);
					session.flush();
				}else{
					if (financeDetails.getAmount()<0 && CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo()) ) {
						if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						/*if (!CPSUtils.isNullOrEmpty(subJson.get("repSig").toString())) {
							
							financeDetails.setRepSig(subJson.get("repSig").toString());
						}*/
						 if (!CPSUtils.isNullOrEmpty(subJson.get("amount").toString())) {
								
							 financeDetails.setAmount(subJson.getLong("amount"));
							   }
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(financeDetails);
					}else if(CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo())){

						if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						/*if (!CPSUtils.isNullOrEmpty(subJson.get("repSig").toString())) {
							
							financeDetails.setRepSig(subJson.get("repSig").toString());
						}*/
						 if (!CPSUtils.isNullOrEmpty(subJson.get("amount").toString())) {
								
							 financeDetails.setAmount(subJson.getLong("amount"));
							   }
						 
						 
						 
						 if(ltcBean.getType().equals("encashment") ){
							 if(!CPSUtils.isNullOrEmpty(subJson.get("encashmentAmount"))){
							 financeDetails.setAmount(Integer.valueOf(subJson.get("encashmentAmount").toString()));}
							}
						 
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						int finId = (Integer)session.save(financeDetails);
						
						/*if(!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString()) && !CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString()) && !CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())){
							CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
							cdaDetailsDTO.setReferenceID(finId);
							cdaDetailsDTO.setDvNumber(subJson.get("dvNo").toString());
							cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.get("dvDate").toString()));
							cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("cdaAmount").toString()));
							cdaDetailsDTO.setCreatedBy(ltcBean.getSfID());
							cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
							cdaDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
							cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
							session.save(cdaDetailsDTO);
						}*/
					}/*else{
					CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
					cdaDetailsDTO.setReferenceID(financeDetails.getId());
					if (!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())) {
						cdaDetailsDTO.setDvNumber(subJson.get("dvNo").toString());
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString())) {
						cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.get("dvDate").toString()));
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())) {
						cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("cdaAmount").toString()));
					}
					cdaDetailsDTO.setCreatedBy(ltcBean.getSfID());
					cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					cdaDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
					cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					session.save(cdaDetailsDTO);
					}*/
					//else if(!CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo()) && CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())){
					else if(!CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo())){	
					
					if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo"))) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo"))) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer"))) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						/*if (!CPSUtils.isNullOrEmpty(subJson.get("repSig"))) {
							
							financeDetails.setRepSig(subJson.get("repSig").toString());
							
						}*/
						if(CPSUtils.isNullOrEmpty(subJson.get("encashmentAmount")) && ltcBean.getType().equals("encashment") ){
						 if (!CPSUtils.isNullOrEmpty(subJson.get("amount").toString())) {								
							 financeDetails.setAmount(subJson.getLong("amount"));
							   }
						 }								 
						 					 
						 if(!CPSUtils.isNullOrEmpty(subJson.get("encashmentAmount")) && ltcBean.getType().equals("encashment") ){
							 financeDetails.setAmount(Integer.valueOf(subJson.get("encashmentAmount").toString()));
							}
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(financeDetails);	
					}
					//else if(!CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo()) && !CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString()) && !CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())){
						
					else if(!CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo()) ){						
					if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo"))) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo"))) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer"))) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						/*if (!CPSUtils.isNullOrEmpty(subJson.get("repSig"))) {
							
							financeDetails.setRepSig(subJson.get("repSig").toString());
						}*/
						 if (!CPSUtils.isNullOrEmpty(subJson.get("amount").toString())) {								
							 financeDetails.setAmount(subJson.getLong("amount"));
						 }
						 					 
						 if(!CPSUtils.isNullOrEmpty(subJson.get("encashmentAmount")) && ltcBean.getType().equals("encashment") ){
							 financeDetails.setAmount(Integer.valueOf(subJson.get("encashmentAmount").toString()));
							}
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(financeDetails);	
						
						/*CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
						cdaDetailsDTO.setReferenceID(financeDetails.getId());
						if (!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())) {
							cdaDetailsDTO.setDvNumber(subJson.get("dvNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString())) {
							cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.get("dvDate").toString()));
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())) {
							cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("cdaAmount").toString()));
						}
						cdaDetailsDTO.setCreatedBy(ltcBean.getSfID());
						cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
						cdaDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
						cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(cdaDetailsDTO);*/
					}
				}
				message = CPSConstants.SUCCESS;
			}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public String saveCdaAmountsettlemt(JSONObject subJson,LtcApplicationBean ltcBean) throws Exception {
		Session session = null;
		String message=null;
		EmployeeClaimDetailsDTO employeeClaimDetailsDTO=null;
		FinanceDetailsDTO financeDetails=null;
		try{
			session = hibernateUtils.getSession();
			//employeeClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(subJson.get("requestId").toString()))).add(Expression.eq("requestType", ltcBean.getType())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
		/*	if (!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)) {
				financeDetails = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, employeeClaimDetailsDTO.getId())).uniqueResult();
				if (CPSUtils.isNullOrEmpty(financeDetails)) {
					FinanceDetailsDTO financeDetailsDTO = new FinanceDetailsDTO();
					financeDetailsDTO.setReferenceID(employeeClaimDetailsDTO.getId());
					if(!CPSUtils.isNullOrEmpty(subJson.get("encashmentAmount").toString()) && ltcBean.getType().equals("encashment") ){
						financeDetailsDTO.setAmount(Integer.valueOf(subJson.get("encashmentAmount").toString()));
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
						financeDetailsDTO.setSanctionNo(subJson.get("sanctionNo").toString());
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
						financeDetailsDTO.setBillNo(subJson.get("billNo").toString());
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
						financeDetailsDTO.setAccountentSign(subJson.get("accOfficer").toString());
					}
					financeDetailsDTO.setCreatedBy(ltcBean.getSfID());
					financeDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					financeDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
					financeDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					financeDetailsDTO.setStatus(1);
					session.save(financeDetailsDTO);
				}else{
					if (financeDetails.getAmount()<0 && CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo()) ) {
						if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(financeDetails);
					}else if(CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo())){

						if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						int finId = (Integer)session.save(financeDetails);
						
						if(!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString()) && !CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString()) && !CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())){
							CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
							cdaDetailsDTO.setReferenceID(finId);
							cdaDetailsDTO.setDvNumber(subJson.get("dvNo").toString());
							cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.get("dvDate").toString()));
							cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("cdaAmount").toString()));
							cdaDetailsDTO.setCreatedBy(ltcBean.getSfID());
							cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
							cdaDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
							cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
							session.save(cdaDetailsDTO);
						}
					}else{
					CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
					cdaDetailsDTO.setReferenceID(financeDetails.getId());
					if (!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())) {
						cdaDetailsDTO.setDvNumber(subJson.get("dvNo").toString());
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString())) {
						cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.get("dvDate").toString()));
					}
					if (!CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())) {
						cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("cdaAmount").toString()));
					}
					cdaDetailsDTO.setCreatedBy(ltcBean.getSfID());
					cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					cdaDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
					cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					session.save(cdaDetailsDTO);
					}
					else if(!CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo()) && CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())){
						if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(financeDetails);	
					}
					else if(!CPSUtils.isNullOrEmpty(financeDetails.getSanctionNo()) && !CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString()) && !CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())){
						if (!CPSUtils.isNullOrEmpty(subJson.get("sanctionNo").toString())) {
							financeDetails.setSanctionNo(subJson.get("sanctionNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("billNo").toString())) {
							financeDetails.setBillNo(subJson.get("billNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("accOfficer").toString())) {
							financeDetails.setAccountentSign(subJson.get("accOfficer").toString());
						}
						financeDetails.setLastModifiedBy(ltcBean.getSfID());
						financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(financeDetails);	
						
						CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
						cdaDetailsDTO.setReferenceID(financeDetails.getId());
						if (!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())) {
							cdaDetailsDTO.setDvNumber(subJson.get("dvNo").toString());
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString())) {
							cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.get("dvDate").toString()));
						}
						if (!CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())) {
							cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("cdaAmount").toString()));
						}
						cdaDetailsDTO.setCreatedBy(ltcBean.getSfID());
						cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
						cdaDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
						cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.save(cdaDetailsDTO);
					}
				}*/
			employeeClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(subJson.get("requestId").toString()))).add(Expression.eq("requestType", ltcBean.getType())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			financeDetails = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, employeeClaimDetailsDTO.getId())).uniqueResult();
			CDADetailsDTO cdaDetailsDTO= new CDADetailsDTO();
			cdaDetailsDTO.setReferenceID(financeDetails.getId());
			if (!CPSUtils.isNullOrEmpty(subJson.get("dvNo").toString())) {
				cdaDetailsDTO.setDvNumber(subJson.get("dvNo").toString());
			}
			if (!CPSUtils.isNullOrEmpty(subJson.get("dvDate").toString())) {
				cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.get("dvDate").toString()));
			}
			if (!CPSUtils.isNullOrEmpty(subJson.get("cdaAmount").toString())) {
				cdaDetailsDTO.setCdaAmount(Float.valueOf(subJson.get("cdaAmount").toString()));
			}
			cdaDetailsDTO.setCreatedBy(ltcBean.getSfID());
			cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			cdaDetailsDTO.setLastModifiedBy(ltcBean.getSfID());
			cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			session.save(cdaDetailsDTO);
				message = CPSConstants.SUCCESS;
			
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public String getLtcBlockYear(String id) throws Exception{
		Session session =null;
		String ltcBlockYearId =null;
		try{
			session=hibernateUtils.getSession();
			
			String query ="select to_char(EXTENDED_DATE,'yyyy') exYear from ltc_block_year_master where extended_date is not null and id="+id+" and status=1";
			ltcBlockYearId =(String)session.createSQLQuery(query).uniqueResult();
		}catch (Exception e) {
			throw e;
		}
		return ltcBlockYearId;
	}
	
	//Getting the age from family_details table based on  the  Ltc departure_date :::::added by Rakesh
	/*@SuppressWarnings("unchecked")
	public List<FamilyBean> getFamilyMemberDetailsLtc(LtcApplicationBean ltcBean) throws Exception {
		List<FamilyBean> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			//String query = "select fd.id id,fd.name name,to_char(fd.dob,'dd-Mon-yyyy')as dob, case when fd.dob  is null then fd.age else round(months_between(sysdate,fd.dob)/12)||'' end age ,frm.name relation from family_details fd,family_relation_master frm where fd.sfid=? and fd.status=1 and fd.ltc_facility='Y' and frm.id=fd.relation_id order by frm.order_no";
			String query ="SELECT fd.id id,  fd.name name,  TO_CHAR(fd.dob,'dd-Mon-yyyy')AS dob,  CASE    WHEN fd.dob IS NULL    THEN fd.age    else         case when TRUNC(months_between(?,fd.dob)) between 24 and 36"+
             " then    trunc(months_between(?,fd.dob)/12)||' Years and '  || (trunc(months_between(?,fd.dob) - trunc(months_between(?,fd.dob)/12)*12)+1) ||' Months'"+
             "  else    trunc(months_between(?,fd.dob)/12)||' Years'    end       end age ,  frm.name relation from family_details fd,  family_relation_master frm WHERE fd.sfid      =? AND fd.status      =1 AND fd.ltc_facility='Y' and frm.id         =fd.relation_id order by frm.order_no";
			
			
			list = session.createSQLQuery(query).addScalar("id",Hibernate.STRING).addScalar("name").addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("relation").setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).setDate(0, ltcBean.getDepartureDate()).setDate(1, ltcBean.getDepartureDate()).setDate(2, ltcBean.getDepartureDate()).setDate(3, ltcBean.getDepartureDate()).setDate(4, ltcBean.getDepartureDate()).setString(5, ltcBean.getSfID()).list();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	*/
	
	
	@SuppressWarnings("unchecked")
	public LtcApplicationBean getLtcEncashmentDays(LtcApplicationBean  ltcBean) throws Exception{
		List<LtcEncashmentDTO> list =null;
		Session session =null;
		try{
			session = hibernateUtils.getSession();
			//list = session.createCriteria(LtcEncashmentDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("sfid", ltcBean.getSfID())).list();
			
			
					list= session.createSQLQuery("select ID as id ,SFID as sfid, LTC_ENCASHMENT_DAYS as encashmentdays,LEAVE_TYPE_ID  as leaveTypeId,SERVICE_SPELLS_COUNT as serviceSpellCount , YEAR_SPELLS_COUNT as yearsSpellcount  from leave_emp_spells where sfid=? ").addScalar("id", Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("encashmentdays", Hibernate.INTEGER).addScalar("leaveTypeId", Hibernate.INTEGER).addScalar("serviceSpellCount", Hibernate.INTEGER).addScalar("yearsSpellcount", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LtcEncashmentDTO.class)).setString(0, ltcBean.getSfID()).list();

					ltcBean.setLtcEncashmentList(list);	
		}catch(Exception e){
			throw e;
		}
		return ltcBean;
		
	}
	
	
	
	
	
}
