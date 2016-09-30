package com.callippus.web.dao.family;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.business.requestprocess.FamilyRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@SuppressWarnings("serial")
@Service
public class SQLFamilyDAO implements IFamilyDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLFamilyDAO.class);
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public ArrayList getFamilyDetails(String sfid) throws Exception {
		Session session = null;
		ArrayList familyList = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rsq = null;
		ResultSet familyrs = null;
		try {
			// get the family details of sfid from family_details
			session = hibernateUtils.getSession();
			con = session.connection();
			String sql = "select address_type_id,id from family_details where sfid=? and status=1";
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, sfid);
			familyrs = ps1.executeQuery();
			familyList = new ArrayList();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (familyrs.next()) {
				String familysql = "";


				if (CPSUtils.isNull(familyrs.getString("address_type_id")) || CPSUtils.compareStrings(familyrs.getString("address_type_id"), "100")) {
					familysql = "select dm.name disabilityName,fd.earning_money,fd.disability_id,fd.id,fd.name,fd.address_type_id,fd.relation_id,relation.name relation,age,to_char(fd.dob,'DD-Mon-YYYY')as dob,fd.marital_status_id,(select name from marital_master where id=fd.marital_status_id)martlstatus,contact_number,address1,address2,address3,city, "
							+ " fd.district_id,(select name from district_master where id=fd.district_id)district,cghs_facility,ltc_facility,sex,fd.state_id,(select name from state_master where id=fd.state_id)state,pin_code,dependent,to_char(dependent_from,'DD-Mon-YYYY')dependent_from,blood_group,(select name from blood_group_master where id=fd.blood_group)blood_group_name, "
							+ " employed,employed_type,residing_with,to_char(FAMILY_DECLARE_DATE,'DD-Mon-YYYY')as FAMILY_DECLARE_DATE,ph_type_flag,adopted,to_char(ADOPTED_DATE,'DD-Mon-YYYY')as ADOPTED_DATE,place_of_issue,beneficiary,to_char(valid_from,'DD-Mon-YYYY') fromDate ,to_char(valid_to,'DD-Mon-YYYY') toDate  from family_details fd,family_relation_master relation,disability_master dm where fd.relation_id=relation.id  and sfid=? and fd.status=1 and fd.id=? and relation.status='1' and fd.disability_id=dm.id";
				} else {
					familysql = "select dm.name disabilityName,fd.earning_money,fd.disability_id,fd.id,fd.name,fd.address_type_id,fd.relation_id,relation.name relation,age,to_char(fd.dob,'DD-Mon-YYYY')as dob,fd.marital_status_id,(select name from marital_master where id=fd.marital_status_id)martlstatus,contact_number,ad.address1, "
							+ "ad.address2,ad.address3,ad.city,fd.district_id,(select name from district_master where id=ad.district)district,cghs_facility,ltc_facility,sex,fd.state_id,(select name from state_master where id=ad.state)state,pin_code,dependent,to_char(dependent_from,'DD-Mon-YYYY')dependent_from, "
							+ "blood_group,(select name from blood_group_master where id=fd.blood_group)blood_group_name,employed,employed_type,residing_with,to_char(FAMILY_DECLARE_DATE,'DD-Mon-YYYY')as FAMILY_DECLARE_DATE,ph_type_flag,adopted,to_char(ADOPTED_DATE,'DD-Mon-YYYY')as ADOPTED_DATE,place_of_issue,beneficiary,to_char(valid_from,'DD-Mon-YYYY') as fromDate,to_char(valid_to,'DD-Mon-YYYY') as toDate from family_details fd,family_relation_master relation,address_details_master ad,disability_master dm where fd.relation_id=relation.id and fd.sfid=? and fd.status=1 and fd.address_type_id=ad.address_type_id and fd.sfid=ad.sfid and fd.id=? and ad.status='1' and relation.status='1' and fd.disability_id=dm.id";
				}

				ps = con.prepareStatement(familysql);
				ps.setString(1, sfid);
				ps.setString(2, familyrs.getString("id"));
				rsq = ps.executeQuery();

				while (rsq.next()) {
					LinkedHashMap innerjson = new LinkedHashMap();
					innerjson.put("id", rsq.getString("id"));
					innerjson.put("Family Member Name", rsq.getString("name"));
					innerjson.put("Relation", rsq.getString("relation"));
					if (CPSUtils.compareStrings(rsq.getString("sex"), "M"))
						innerjson.put("Gender", "Male");
					else
						innerjson.put("Gender", "Female");
					if (CPSUtils.isNullOrEmpty(rsq.getString("age")))
						innerjson.put("Date Of Birth", rsq.getString("dob"));
					else
						innerjson.put("Age", rsq.getString("age"));
					if (!CPSUtils.compareStrings(rsq.getString("relation_id"), "16")) {
						// if(CPSUtils.compareStrings(rsq.getString("cghs_facility"),CPSConstants.N)||CPSUtils.isNull(rsq.getString("cghs_facility"))) {
						// innerjson.put("CGHS Dependent", "NA");
						// }else {
						// innerjson.put("CGHS Dependent", "Yes");
						// }
						// if(CPSUtils.compareStrings(rsq.getString("ltc_facility"),CPSConstants.N)||CPSUtils.isNull(rsq.getString("ltc_facility"))) {
						// innerjson.put("LTC Dependent", "NA");
						// }else {
						// innerjson.put("LTC Dependent", "Yes");
						// }
						if (CPSUtils.compareStrings(rsq.getString("residing_with"), CPSConstants.N)) {
							innerjson.put("Residing With", "No");
						} else {
							innerjson.put("Residing With", "Yes");
						}
						if (CPSUtils.compareStrings(rsq.getString("adopted"), CPSConstants.N)) {
							innerjson.put("Adopted", "NA");
						} else {
							innerjson.put("Adopted", "Yes");
						}
						innerjson.put("Adopted Date", rsq.getString("adopted_date"));
						innerjson.put("Declare Date", rsq.getString("FAMILY_DECLARE_DATE"));
					}
					innerjson.put("Marital Status", rsq.getString("martlstatus"));
					innerjson.put("Address1", rsq.getString("address1"));
					innerjson.put("Address2", rsq.getString("address2"));
					innerjson.put("Address3", rsq.getString("address3"));
					innerjson.put("District", rsq.getString("district"));
					
					//innerjson.put("cghscard", rsq.getString("cghscard"));
					innerjson.put("State", rsq.getString("state"));
					innerjson.put("State", rsq.getString("state"));
					innerjson.put("City", rsq.getString("city"));
					innerjson.put("Pin Code", rsq.getString("pin_code"));
					/*
					 * if (CPSUtils.compareStrings(rsq.getString("dependent"), CPSConstants.N)) innerjson.put("Dependent", "NA"); else innerjson.put("Dependent", "Yes");
					 * innerjson.put("Dependent From", rsq.getString("dependent_from"));
					 */
					innerjson.put("Blood Group", rsq.getString("blood_group_name"));
					if (CPSUtils.compareStrings(rsq.getString("employed"), CPSConstants.N)) {
						innerjson.put("Employed", "NA");
					} else {
						innerjson.put("Employed", "Yes");
					}
					if (CPSUtils.compareStrings(rsq.getString("employed_type"), "20"))
						innerjson.put("Employment Type", "Private");
					if (CPSUtils.compareStrings(rsq.getString("employed_type"), "21"))
						innerjson.put("Employment Type", "Govt");
					innerjson.put("Earning Salary", rsq.getString("earning_money"));
					innerjson.put("Contact Number", rsq.getString("contact_number"));
					if (CPSUtils.compareStrings(rsq.getString("ph_type_flag"), CPSConstants.N)) {
						innerjson.put("PH Type", "NA");
					} else {
						innerjson.put("PH Type", "Yes");
					}
					innerjson.put("Disability Type", rsq.getString("disabilityName"));
					innerjson.put("Place of issue", rsq.getString("place_of_issue"));
					innerjson.put("CGHS Number", rsq.getString("beneficiary"));
					//innerjson.put("cghscard", rsq.getString("cghscard"));
					innerjson.put("Validity of CGHS From", rsq.getString("fromDate"));
					innerjson.put("To Date", rsq.getString("toDate"));
					FamilyBean familyBean = new FamilyBean();
					familyBean.setId(rsq.getString("id"));
					familyBean.setName(rsq.getString("name"));
					familyBean.setCghsFacility(rsq.getString("cghs_facility"));
					familyBean.setLtcFacility(rsq.getString("ltc_facility"));
					familyBean.setRelationId(rsq.getString("relation_id"));
					familyBean.setRelation(rsq.getString("relation"));
					if (CPSUtils.isNullOrEmpty(rsq.getString("age"))) {
						familyBean.setDob(rsq.getString("dob"));
						familyBean.setAge("");
					} else {
						familyBean.setDob("");
						familyBean.setAge(rsq.getString("age"));
					}
					familyBean.setMaritalstatusId(rsq.getString("martlstatus"));
					familyBean.setMaritalstatus(rsq.getString("marital_status_id"));
					familyBean.setAddressTypeId(rsq.getString("address_type_id"));
					familyBean.setAddress1(rsq.getString("address1"));
					familyBean.setAddress2(rsq.getString("address2"));
					familyBean.setAddress3(rsq.getString("address3"));
					familyBean.setDistrict(rsq.getString("district_id"));
					familyBean.setCity(rsq.getString("city"));
					familyBean.setState(rsq.getString("state_id"));
					familyBean.setGender(rsq.getString("sex"));
					familyBean.setPincode(rsq.getString("pin_code"));
					// familyBean.setDependent(rsq.getString("dependent"));
					familyBean.setDependentFrom(rsq.getString("dependent_from"));
					familyBean.setEmployeed(rsq.getString("employed"));
					familyBean.setEmployeedType(rsq.getString("employed_type"));
					familyBean.setEarningMoney(rsq.getString("earning_money"));
					familyBean.setResidingWith(rsq.getString("residing_with"));
					familyBean.setPincode(rsq.getString("pin_code"));
					familyBean.setBloodGroup(rsq.getString("blood_group"));
					if (!CPSUtils.isNull(rsq.getString("contact_number"))) {
						familyBean.setContactNumber(rsq.getString("contact_number"));

					} else {
						familyBean.setContactNumber("");
					}
					familyBean.setDeclareDate(rsq.getString("FAMILY_DECLARE_DATE"));
					familyBean.setPhtypeFlag(rsq.getString("ph_type_flag"));
					familyBean.setDisabilityId(rsq.getString("disability_Id"));
					familyBean.setAdopted(rsq.getString("adopted"));
					familyBean.setAdoptedDate(rsq.getString("adopted_date"));
					familyBean.setPlaceOfIssue(rsq.getString("place_of_issue"));
					
				//familyBean.setCghscard(rsq.getString("cghscard"));   
					
					familyBean.setBeneficiary(rsq.getString("beneficiary"));
					familyBean.setValidFrom(rsq.getString("fromDate"));
					familyBean.setValidTo(rsq.getString("toDate"));
					familyList.add(familyBean);
					outerjson.put(String.valueOf(i), innerjson);
					i++;
				}
			}
			outerjson.put(String.valueOf(i), CPSConstants.FAMILY);
			familyList.add(outerjson);

		} catch (Exception e) {
			throw e;
		}
		return familyList;
	}

	public String deleteFamily(String id,String remarks) throws Exception {
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			Connection con = session.connection();
//			FamilyBean familyBean2=(FamilyBean)session.load(FamilyBean.class,id);
//			familyBean2.setStatus(100);
//			session.saveOrUpdate(familyBean2);
//			session.flush();
			String sql = "update Family_details set status=100,deletion_remarks=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,remarks);
			pstmt.setInt(2, Integer.parseInt(id));
			pstmt.executeUpdate();

			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	public List checkFamily(FamilyBean FamilyBean) throws Exception {
		List list = null;
		Session session = null;
		String qry = null;
		try {
			session = hibernateUtils.getSession();
			qry = "select upper(frm.name) from family_details fb,family_relation_master frm  where fb.status=1 and fb.sfid='" + FamilyBean.getSfid() + "' and fb.relation_id="
					+ FamilyBean.getRelationId() + " and frm.id=fb.relation_id group by frm.name";
			Query query = session.createSQLQuery(qry);
			list = query.list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String createFamily(FamilyBean familyBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			// insert into family details table
			session = hibernateUtils.getSession();
			session.save(familyBean);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String updateFamily(FamilyBean familyBean) throws Exception {
		String message = "";
		Session session = null;
		List<FamilyBean> familyDetailsList=null;
		try {
			// insert into family details table
			session = hibernateUtils.getSession();
			if(familyBean.getRelationId().equals("1") || familyBean.getRelationId().equals("2") || familyBean.getRelationId().equals("3") || familyBean.getRelationId().equals("4") || familyBean.getRelationId().equals("16")){
			familyDetailsList=session.createCriteria(FamilyBean.class).add(Expression.eq("status", 1)).add(Expression.eq("sfid", familyBean.getSfid())).add(Expression.eq("relationId", familyBean.getRelationId())).list();
			session.clear();
			if(familyDetailsList.size()==0){
				session.update(familyBean);
				message = CPSConstants.UPDATE;
			}else if(familyDetailsList.size()==1 && (familyDetailsList.get(0).getId().equals(familyBean.getId()))){
				session.update(familyBean);
				message = CPSConstants.UPDATE;
			}else{
				message = CPSConstants.DUPLICATE;
			}
			}else{
				session.update(familyBean);
				message = CPSConstants.UPDATE;
			}
			
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String manageFamily(FamilyBean FamilyBean) throws Exception {
		String message = null;
		Session session = null;
		//List<FamilyBean> familyDetailsList = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(FamilyBean);
			session.flush();
			message = CPSConstants.SUCCESS;
			
			//duplicate check for mother,father,wife/husband and self
			/*if((!CPSUtils.isNullOrEmpty(FamilyBean.getId())) &&( FamilyBean.getRelationId().equals("1") || FamilyBean.getRelationId().equals("2") || FamilyBean.getRelationId().equals("3") || FamilyBean.getRelationId().equals("4") || FamilyBean.getRelationId().equals("16")) ){
			familyDetailsList=session.createCriteria(FamilyBean.class).add(Expression.eq("status", 1)).add(Expression.eq("sfid", FamilyBean.getSfid())).add(Expression.eq("relationId", FamilyBean.getRelationId())).list();
			if(familyDetailsList.size()==0){
				session.saveOrUpdate(FamilyBean);
				message = CPSConstants.SUCCESS;
			}else{
				message = CPSConstants.DUPLICATE;
			}
			}else{
				session.saveOrUpdate(FamilyBean);
				message = CPSConstants.SUCCESS;
			}*/
		
			hibernateUtils.closeSession();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	public boolean checkNomineeData(FamilyBean familyBean) throws Exception {
		Session session = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean flag = true;
		try {
			session = hibernateUtils.getSession();
			Connection con = session.connection();
			String sql = "select count(*) from nominee_details where sfid=? and status =1 and family_member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, familyBean.getSfid());
			pstmt.setString(2, familyBean.getId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					flag = false;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}

	public String updateLtcAndCghsDetails(String cghsFacility, String ltcFacility, String sfid, String id) throws Exception {
		String message = "";
		Session session = null;
		StringBuffer sql = null;
		try {
			sql = new StringBuffer();
			session = hibernateUtils.getSession();
			sql.append("update FamilyBean set cghs_facility=?,ltc_facility=? where sfid=?  and id = (?)");
			Query qry = session.createQuery(sql.toString());
			qry.setString(0, cghsFacility);
			qry.setString(1, ltcFacility);
			qry.setString(2, sfid);
			qry.setString(3, id);
			qry.executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;

	}

	public String getPreviousValue(String type, String sfid, String id) throws Exception {
		Session session = null;
		StringBuffer sql = null;
		String result = CPSConstants.N;
		try {
			sql = new StringBuffer();
			session = hibernateUtils.getSession();
			sql.append("select ");
			if (CPSUtils.compareStrings(type, CPSConstants.LTC)) {
				sql.append("ltc_facility from family_details where sfid=? and id=? and status=1 ");
			} else {
				sql.append("cghs_facility from family_details where sfid=? and id=? and status=1 ");
			}
			if (!CPSUtils.isNull(session.createSQLQuery(sql.toString()).setString(0, sfid).setString(1, id).uniqueResult())) {
				result = session.createSQLQuery(sql.toString()).setString(0, sfid).setString(1, id).uniqueResult().toString();
			}
		} catch (Exception e) {
			throw e;
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public void updateCghsAndLtcFacility(String type, int sonAgeLimit, int majorAgeLimit) throws Exception {
		Session session = null;
		StringBuffer sql = null;
		List<FamilyBean> updateList = null;
		String updateSql = null;
		try {
			sql = new StringBuffer();
			session = hibernateUtils.getSession();
			sql.append("select status,id,sfid,name from (select (case "
					+ " when (relation_id=5) then (case when ((add_months(dob,?*12)>sysdate or age >=?) and ph_type_flag='N') then 'true' else 'false' end) "
					+ " when (relation_id=8 or relation_id=11 or relation_id=14) then (case when ((add_months(dob,?*12)>sysdate or age >=?) and ph_type_flag='N') then 'true' else 'false' end) "
					+ " else 'true' end) status ,id,sfid,name,dobage,relation_id,marital_status_id " + " from "
					+ " (select (select round(months_between(sysdate,fd.dob)/12) from dual) dobage,fd.marital_status_id,fd.age,fd.id,fd.sfid,fd.name,fd.ph_type_flag,fd.dob,fd.relation_id "
					+ " from family_details fd,emp_master emp where fd.status=1 and emp.status=1 " + " and emp.sfid=fd.sfid and fd.relation_id!=16 and fd.relation_id in(5,8,11,14)");
			if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
				sql.append(" and fd.cghs_facility='Y' order by emp.sfid)) where status='false'");
				updateSql = "update family_details set cghs_facility='N' where id=? and status=1";
			} else {
				sql.append(" and fd.ltc_facility='Y' order by emp.sfid)) where status='false'");
				updateSql = "update family_details set ltc_facility='N' where id=? and status=1";
			}
			updateList = session.createSQLQuery(sql.toString()).addScalar("id", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(FamilyBean.class)).setInteger(0, sonAgeLimit).setInteger(1, sonAgeLimit).setInteger(2, majorAgeLimit).setInteger(3, majorAgeLimit).list();
			if (updateList.size() > 0) {
				Iterator<FamilyBean> iterator = updateList.iterator();
				while (iterator.hasNext()) {
					FamilyBean familyBean = iterator.next();
					session.createSQLQuery(updateSql).setString(0, familyBean.getId()).executeUpdate();
					log.debug("updated " + type + "_FACILUT ='N' to the family member of '" + familyBean.getSfid() + "'having name='" + familyBean.getName() + "' with id=" + familyBean.getId());
				}

			} else {
				log.debug("Today no updates are happen for " + type);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public String ageFieldUpdation() throws Exception {
		Session session = null;
		String message = null;
		List <FamilyBean> familyList=null;
		try {
			/**
			 * Every year age field is incremented by one(1) 
			 */
			session = hibernateUtils.getSession();
			familyList = session.createSQLQuery("select fd.name,fd.sfid,fd.id,fd.creation_date,fd.age from family_details fd,emp_master emp where fd.status=1 and fd.dob is null and fd.age is not null and emp.status=1 and emp.sfid=fd.sfid ").addScalar("id", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("age", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).list();
			log.debug("Updation list length :::::::::::::"+familyList.size());			
			for (FamilyBean eachRow : familyList) {
				log.debug("Updating AGE field value in family_details where id= "+eachRow.getId()+",name= "+eachRow.getName()+",sfid= "+eachRow.getSfid());
				int i = session.createQuery("update FamilyBean set age=age+1,modifiedDate=sysdate where id=?").setString(0, eachRow.getId()).executeUpdate();
				log.debug("no fo rows updated :::"+i);
			}
			message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
}