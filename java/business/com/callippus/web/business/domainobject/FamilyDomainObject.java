package com.callippus.web.business.domainobject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.FamilyRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class FamilyDomainObject extends DomainObject {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private DomainObject domainObject;

	/**
	 * This method will update the txn employee details
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String updateTxnDetails(FamilyRequestBean frb) throws Exception {

		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rsq = null;
		Transaction tx = null;
		try {
			// VN session = hibernateUtils.getSession();//session =
			// sessionFactory.openSession();
			session = hibernateUtils.getSession();
			con = session.connection();

			String getTxnDetails = "SELECT reference_number,MAX(CASE WHEN column_name = 'SFID' THEN new_value END) AS SFID,MAX(CASE WHEN column_name = 'NAME' THEN new_value END) AS NAME,"
					+ "MAX(CASE WHEN column_name = 'RELATION_ID' THEN new_value END) AS RELATION_ID,MAX(CASE WHEN column_name = 'SEX' THEN new_value END) AS SEX,"
					+ "MAX(CASE WHEN column_name = 'DOB' THEN new_value END) AS DOB, MAX(CASE WHEN column_name = 'DEPENDENT' THEN new_value END) AS DEPENDENT,"
					+ "MAX(CASE WHEN column_name = 'DEPENDENT_FROM' THEN new_value END) AS DEPENDENT_FROM,MAX(CASE WHEN column_name = 'BLOOD_GROUP' THEN new_value END) AS BLOOD_GROUP,"
					+ "MAX(CASE WHEN column_name = 'CONTACT_NUMBER' THEN new_value END) AS CONTACT_NUMBER, MAX(CASE WHEN column_name = 'ADDRESS1' THEN new_value END) AS ADDRESS1,"
					+ "MAX(CASE WHEN column_name = 'ADDRESS2' THEN new_value END) AS ADDRESS2, MAX(CASE WHEN column_name = 'ADDRESS3' THEN new_value END) AS ADDRESS3,"
					+ "MAX(CASE WHEN column_name = 'CITY' THEN new_value END) AS CITY, MAX(CASE WHEN column_name = 'STATE_ID' THEN new_value END) AS STATE_ID,"
					+ "MAX(CASE WHEN column_name = 'DISTRICT_ID' THEN new_value END) AS DISTRICT_ID, MAX(CASE WHEN column_name = 'PIN_CODE' THEN new_value END) AS PIN_CODE,"
					+ "MAX(CASE WHEN column_name = 'RESIDING_WITH' THEN new_value END) AS RESIDING_WITH, MAX(CASE WHEN column_name = 'FAMILY_DECLARE_DATE' THEN new_value END) AS FAMILY_DECLARE_DATE,"
					+ "MAX(CASE WHEN column_name = 'AGE' THEN new_value END) AS AGE, MAX(CASE WHEN column_name = 'EMPLOYED_TYPE' THEN new_value END) AS EMPLOYED_TYPE,"
					+ "MAX(CASE WHEN column_name = 'CGHS_FACILITY' THEN new_value END) AS CGHS_FACILITY, MAX(CASE WHEN column_name = 'LTC_FACILITY' THEN new_value END) AS LTC_FACILITY,"
					+ "MAX(CASE WHEN column_name = 'EMPLOYED' THEN new_value END) AS EMPLOYED,MAX(CASE WHEN column_name = 'MARITAL_STATUS_ID' THEN new_value END) AS MARITAL_STATUS_ID,"
					+ "MAX(CASE WHEN column_name = 'PH_TYPE_FLAG' THEN new_value END) AS PH_TYPE_FLAG,MAX(CASE WHEN column_name = 'EARNING_MONEY' THEN new_value END) AS EARNING_MONEY,"
					+ "MAX(CASE WHEN column_name = 'DISABILITY_ID' THEN new_value END) AS DISABILITY_ID,"
					+ "MAX(CASE WHEN column_name = 'PLACE_OF_ISSUE' THEN new_value END) AS PLACE_OF_ISSUE,"
					+ "MAX(CASE WHEN column_name = 'BENEFICIARY' THEN new_value END) AS BENEFICIARY,"
					+ "MAX(CASE WHEN column_name = 'VALID_FROM' THEN new_value END) AS VALID_FROM,"
					+ "MAX(CASE WHEN column_name = 'VALID_TO' THEN new_value END) AS VALID_TO,"
					+ "MAX(CASE WHEN column_name = 'ADDRESS_TYPE_ID' THEN new_value END) AS ADDRESS_TYPE_ID FROM workflow_txn_details where request_id=? GROUP BY reference_number";
			ps = con.prepareStatement(getTxnDetails);
			ps.setString(1, frb.getRequestID());
			rsq = ps.executeQuery();
			if (rsq.next()) {
				String reference_number = rsq.getString("reference_number");
				String sfid = rsq.getString("sfid");
				String name = rsq.getString("name");
				String relation_id = rsq.getString("relation_id");
				String sex = rsq.getString("sex");
				String dob = rsq.getString("dob");
				String age = rsq.getString("age");
				String dependent = rsq.getString("dependent");
				String dependent_from = rsq.getString("dependent_from");
				String marital_status_id = rsq.getString("marital_status_id");
				String blood_group = rsq.getString("blood_group");
				String contact_number = rsq.getString("contact_number");
				String address1 = rsq.getString("address1");
				String address2 = rsq.getString("address2");
				String address3 = rsq.getString("address3");
				String address_type_id = rsq.getString("address_type_id");
				String city = rsq.getString("city");
				String state_id = rsq.getString("state_id");
				String district_id = rsq.getString("district_id");
				String pin_code = rsq.getString("pin_code");
				String employed = rsq.getString("employed");
				String employed_type = rsq.getString("employed_type");
				String residing_with = rsq.getString("residing_with");
				String family_declare_date = rsq
						.getString("family_declare_date");
				String ltc_facility = rsq.getString("ltc_facility");
				String cghs_facility = rsq.getString("cghs_facility");
				String ph_type_flag = rsq.getString("ph_type_flag");
				String earning_money = rsq.getString("earning_money");
				String disability_id = rsq.getString("disability_id");
				@SuppressWarnings("unused")
				String place_of_issue = rsq.getString("place_of_issue");
				String beneficiary = rsq.getString("beneficiary");
				String valid_from = rsq.getString("valid_from");
				String valid_to = rsq.getString("valid_to");

				Criteria crit = session.createCriteria(FamilyBean.class);
				crit.add(Expression.eq("id", reference_number));
				List<AddressBean> list = crit.list();
				Iterator it = list.iterator();
				FamilyBean familyBean = null;
				String manage = null;
				if (it.hasNext()) {
					familyBean = (FamilyBean) it.next();
					familyBean.setId(reference_number);
					manage = CPSConstants.UPDATE;
					familyBean.setCreationDate(CPSUtils
							.formattedDate(familyBean.getCreationDate()));
					familyBean.setDob(CPSUtils.formattedDate(familyBean
							.getDob()));
					familyBean.setValidFrom(CPSUtils.formattedDate(familyBean
							.getValidFrom()));
					familyBean.setValidTo(CPSUtils.formattedDate(familyBean
							.getValidTo()));
					familyBean.setDeclareDate(CPSUtils.formattedDate(familyBean.getDeclareDate()));    // This line add to handle the Declare_Date(P).
				} else {
					familyBean = new FamilyBean();
					familyBean.setId(String.valueOf(domainObject
							.getTableID(CPSConstants.FAMILY_DETAILS)));
					familyBean.setStatus(1);
					familyBean.setCreationDate(CPSUtils.getCurrentDate());
					manage = CPSConstants.NEW;
				}
				familyBean.setModifiedDate(CPSUtils.getCurrentDate());

				if (!CPSUtils.isNullOrEmpty(sfid)) {
					familyBean.setSfid(sfid);
				}
				if (!CPSUtils.isNullOrEmpty(name)) {
					familyBean.setName(CPSUtils.setNullIfEmpty(name));
				}
				if (!CPSUtils.isNullOrEmpty(relation_id)) {
					familyBean.setRelationId(CPSUtils
							.setNullIfEmpty(relation_id));
				}
				if (!CPSUtils.isNullOrEmpty(sex)) {
					familyBean.setGender(CPSUtils.setNullIfEmpty(sex));
				}
				if (!CPSUtils.isNullOrEmpty(CPSUtils.setNullIfEmpty(dob))) {                              // Changed code in condition to  handle date of DOB(P).
					familyBean.setDob(dob);
				}
				if (!CPSUtils.isNullOrEmpty(age)) {
					familyBean.setAge(CPSUtils.setNullIfEmpty(age));
				}
				if (!CPSUtils.isNullOrEmpty(dependent)) {
					familyBean.setDependent(CPSUtils.setNullIfEmpty(dependent));
				}
				if (!CPSUtils.isNullOrEmpty(dependent_from)) {
					familyBean.setDependentFrom(CPSUtils
							.setNullIfEmpty(dependent_from));
				}
				if (!CPSUtils.isNullOrEmpty(marital_status_id)) {
					familyBean.setMaritalstatus(CPSUtils
							.setNullIfEmpty(marital_status_id));
				}

				if (!CPSUtils.isNullOrEmpty(blood_group)) {
					familyBean.setBloodGroup(CPSUtils
							.setNullIfEmpty(blood_group));
				}
				if (!CPSUtils.isNullOrEmpty(contact_number)) {
					familyBean.setContactNumber(CPSUtils
							.setNullIfEmpty(contact_number));
				}
				if (!CPSUtils.isNullOrEmpty(address1)) {
					familyBean.setAddress1(CPSUtils.setNullIfEmpty(address1));
				}
				if (!CPSUtils.isNullOrEmpty(address2)) {
					familyBean.setAddress2(CPSUtils.setNullIfEmpty(address2));
				}
				if (!CPSUtils.isNullOrEmpty(address3)) {
					familyBean.setAddress3(CPSUtils.setNullIfEmpty(address3));
				}
				if (!CPSUtils.isNullOrEmpty(address_type_id)) {
					familyBean.setAddressTypeId(CPSUtils
							.setNullIfEmpty(address_type_id));
				}
				if (!CPSUtils.isNullOrEmpty(city)) {
					familyBean.setCity(CPSUtils.setNullIfEmpty(city));
				}
				if (!CPSUtils.isNullOrEmpty(state_id)) {
					familyBean.setState(CPSUtils.setNullIfEmpty(state_id));
				}
				if (!CPSUtils.isNullOrEmpty(district_id)) {
					familyBean
							.setDistrict(CPSUtils.setNullIfEmpty(district_id));
				}
				if (!CPSUtils.isNullOrEmpty(pin_code)) {
					familyBean.setPincode(CPSUtils.setNullIfEmpty(pin_code));
				}
				if (!CPSUtils.isNullOrEmpty(employed)) {
					familyBean.setEmployeed(CPSUtils.setNullIfEmpty(employed));
				}
				if (!CPSUtils.isNullOrEmpty(employed_type)) {
					familyBean.setEmployeedType(CPSUtils
							.setNullIfEmpty(employed_type));
				}
				if (!CPSUtils.isNullOrEmpty(residing_with)) {
					familyBean.setResidingWith(CPSUtils
							.setNullIfEmpty(residing_with));
				}
				if (!CPSUtils.isNullOrEmpty(family_declare_date)) {
					familyBean.setDeclareDate(CPSUtils
							.setNullIfEmpty(family_declare_date));
				}
				if (!CPSUtils.isNullOrEmpty(ltc_facility)) {
					familyBean.setLtcFacility(CPSUtils
							.setNullIfEmpty(ltc_facility));
				}
				if (!CPSUtils.isNullOrEmpty(cghs_facility)) {
					familyBean.setCghsFacility(CPSUtils
							.setNullIfEmpty(cghs_facility));
				}
				if (!CPSUtils.isNullOrEmpty(ph_type_flag)) {
					familyBean.setPhtypeFlag(CPSUtils
							.setNullIfEmpty(ph_type_flag));
				}
				if (!CPSUtils.isNullOrEmpty(earning_money)) {
					familyBean.setEarningMoney(CPSUtils
							.setNullIfEmpty(earning_money));
				}
				if (!CPSUtils.isNullOrEmpty(disability_id)) {
					familyBean.setDisabilityId(CPSUtils
							.setNullIfEmpty(disability_id));
				}
				if (!CPSUtils.isNullOrEmpty(place_of_issue)) {
					familyBean.setPlaceOfIssue(CPSUtils
							.setNullIfEmpty(place_of_issue));
				}
				if (!CPSUtils.isNullOrEmpty(beneficiary)) {
					familyBean.setBeneficiary(CPSUtils
							.setNullIfEmpty(beneficiary));
				}
				if (!CPSUtils.isNullOrEmpty(valid_from)) {
					familyBean.setValidFrom(valid_from);
				}
				if (!CPSUtils.isNullOrEmpty(valid_to)) {
					familyBean.setValidTo(valid_to);
				}

				if (CPSUtils.compareStrings(CPSConstants.NEW, manage)) {
					// new record
					String insertHometown = "insert into  family_details(id,sfid,name,relation_id,sex,dob,dependent,dependent_from,blood_group,contact_number,address1,address2,address3,"
							+ "city,state_id,district_id,pin_code,status,creation_date,last_modified_date,residing_with,family_declare_date,age,employed,employed_type,marital_status_id,address_type_id,cghs_facility,ltc_facility,ph_type_flag,earning_money,disability_id,place_of_issue,beneficiary,to_char(valid_from,'DD-Mon-YYYY')as fromDate,to_char(valid_to,'DD-Mon-YYYY')as toDate) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,sysdate,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					ps1 = con.prepareStatement(insertHometown);
					ps1.setString(1, familyBean.getId());
					ps1.setString(2, familyBean.getSfid());
					ps1.setString(3, familyBean.getName());
					ps1.setString(4, familyBean.getRelationId());
					ps1.setString(5, familyBean.getGender());
					ps1.setString(6, familyBean.getDob());
					ps1.setString(7, familyBean.getDependent());
					ps1.setString(8, familyBean.getDependentFrom());
					ps1.setString(9, familyBean.getBloodGroup());
					ps1.setString(10, familyBean.getContactNumber());
					ps1.setString(11, familyBean.getAddress1());
					ps1.setString(12, familyBean.getAddress2());
					ps1.setString(13, familyBean.getAddress3());
					ps1.setString(14, familyBean.getCity());
					ps1.setString(15, familyBean.getState());
					ps1.setString(16, familyBean.getDistrict());
					ps1.setString(17, familyBean.getPincode());
					ps1.setString(18, familyBean.getResidingWith());
					ps1.setString(19, familyBean.getDeclareDate());
					ps1.setString(20, familyBean.getAge());
					ps1.setString(21, familyBean.getEmployeed());
					ps1.setString(22, familyBean.getEmployeedType());
					ps1.setString(23, familyBean.getMaritalstatus());
					ps1.setString(24, familyBean.getAddressTypeId());
					ps1.setString(25, familyBean.getCghsFacility());
					ps1.setString(26, familyBean.getLtcFacility());
					ps1.setString(27, familyBean.getPhtypeFlag());
					ps1.setString(28, familyBean.getEarningMoney());
					ps1.setString(29, familyBean.getDisabilityId());
					ps1.setString(30, familyBean.getPlaceOfIssue());
					ps1.setString(31, familyBean.getBeneficiary());
					ps1.setString(32, familyBean.getValidFrom());
					ps1.setString(33, familyBean.getValidTo());
					ps1.executeUpdate();
				} else {
					// update
					// //tx = session.beginTransaction();
					session.saveOrUpdate(familyBean);
					//session.flush();//tx.commit() ;
					session.flush();
				}
			}
			frb.setMessage(CPSConstants.SUCCESS);

		} catch (Exception e) {
			frb.setMessage(CPSConstants.FAILED);
			throw e;
		} finally {
			ConnectionUtil.closeConnection(null, ps, rsq);
			ConnectionUtil.closeConnection(null, ps1, null);
		}
		return frb.getMessage();
	}
}
