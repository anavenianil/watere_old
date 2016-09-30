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
import com.callippus.web.beans.requests.AddressRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class AddressDomainObject extends DomainObject {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private DomainObject domainObject;

	/**
	 * This method will update the txn address details
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String updateTxnDetails(AddressRequestBean arb) throws Exception {

		Session session = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps1 = null;
		ResultSet rsq2 = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();

			String getTxnDetails = "SELECT row_number,reference_number,MAX(CASE WHEN column_name = 'SFID' THEN new_value END) AS SFID,MAX(CASE WHEN column_name = 'ADDRESS1' "
					+ "THEN new_value END) AS ADDRESS1, MAX(CASE WHEN column_name = 'ADDRESS2' THEN new_value END) AS ADDRESS2,MAX(CASE WHEN column_name = 'ADDRESS3' "
					+ "THEN new_value END) AS ADDRESS3,MAX(CASE WHEN column_name = 'CITY' THEN new_value END) AS CITY,MAX(CASE WHEN column_name = 'CARE_OF_ADDRESS' THEN new_value END) AS CARE_OF_ADDRESS, MAX(CASE WHEN column_name = 'STATE' THEN new_value END) AS STATE, "
					+ "MAX(CASE WHEN column_name = 'DISTRICT' THEN new_value END) AS DISTRICT, MAX(CASE WHEN column_name = 'PINCODE' THEN new_value END) AS PINCODE, MAX(CASE WHEN column_name = 'PHONE_NUMBER' "
					+ "THEN new_value END) AS PHONE_NUMBER,MAX(CASE WHEN column_name = 'MOBILE_NUMBER' THEN new_value END) AS MOBILE_NUMBER, MAX(CASE WHEN column_name = 'EMAIL' "
					+ "THEN new_value END) AS EMAIL, MAX(CASE WHEN column_name = 'NEAREST_RLY_STN' THEN new_value END) AS NEAREST_RLY_STN, MAX(CASE WHEN column_name = 'DISPENSARY_NUMBER' THEN new_value END) AS DISPENSARY_NUMBER,MAX(CASE WHEN column_name = 'NEAREST_AIRPORT' "
					+ "THEN new_value END) AS NEAREST_AIRPORT,MAX(CASE WHEN column_name = 'ADDRESS_TYPE_ID' "
					+ "THEN new_value END) AS ADDRESS_TYPE_ID FROM workflow_txn_details where request_id=? GROUP BY row_number, reference_number";

			ps2 = con.prepareStatement(getTxnDetails);
			ps2.setString(1, arb.getRequestID());
			rsq2 = ps2.executeQuery();
			if (rsq2.next()) {

				String rowNumber = rsq2.getString("row_number");
				String sfid = rsq2.getString("sfid");
				String addressTypeId = rsq2.getString("address_type_id");
				String referenceNumber = rsq2.getString("reference_number");
				String address1 = rsq2.getString("address1");
				String address2 = rsq2.getString("address2");
				String address3 = rsq2.getString("address3");
				String careOfAddress = rsq2.getString("CARE_OF_ADDRESS");
				String city = rsq2.getString("city");
				String state = rsq2.getString("state");
				String district = rsq2.getString("district");
				String pincode = rsq2.getString("pincode");
				String phoneNumber = rsq2.getString("phone_number");
				String mobileNumber = rsq2.getString("mobile_number");
				String email = rsq2.getString("email");
				String nearest_rly_stn = rsq2.getString("nearest_rly_stn");
				String nearest_airport = rsq2.getString("nearest_airport");
				String dispensary_number = rsq2.getString("dispensary_number");

				if (CPSUtils.compareStrings(rowNumber, "3")) {
					// Home town address changed
					String updateStatus = "update address_details_master set status=0,last_modified_date=sysdate where id=?";
					ps1 = con.prepareStatement(updateStatus);
					ps1.setString(1, referenceNumber);
					ps1.executeUpdate();
				}
				AddressBean addBean = null;
				String manage = null;
				Criteria crit = session.createCriteria(AddressBean.class);
				crit.add(Expression.eq("id", referenceNumber));
				List<AddressBean> list = crit.list();
				Iterator it = list.iterator();
				if (it.hasNext()) {
					addBean = (AddressBean) it.next();
					addBean.setId(referenceNumber);
					manage = CPSConstants.UPDATE;
					addBean.setCreationDate(CPSUtils.formattedDate(addBean.getCreationDate()));
				} else {
					addBean = new AddressBean();
					addBean.setId(String.valueOf(domainObject.getTableID(CPSConstants.FAMILY_DETAILS)));
					addBean.setStatus(1);
					addBean.setCreationDate(CPSUtils.getCurrentDate());
					manage = CPSConstants.NEW;
				}
				addBean.setLastModifiedDate(CPSUtils.getCurrentDate());

				if (!CPSUtils.isNullOrEmpty(sfid)) {
					addBean.setSfid(CPSUtils.setNullIfEmpty(sfid));
				}
				if (!CPSUtils.isNullOrEmpty(addressTypeId)) {
					addBean.setAddressTypeId(CPSUtils.setNullIfEmpty(addressTypeId));
				}
				if (!CPSUtils.isNullOrEmpty(address1)) {
					addBean.setAddress1(CPSUtils.setNullIfEmpty(address1));
				}
				if (!CPSUtils.isNullOrEmpty(address2)) {
					addBean.setAddress2(CPSUtils.setNullIfEmpty(address2));
				}
				if (!CPSUtils.isNullOrEmpty(address3)) {
					addBean.setAddress3(CPSUtils.setNullIfEmpty(address3));
				}
				if (!CPSUtils.isNullOrEmpty(careOfAddress)) {
					addBean.setCareOfAddress(CPSUtils.setNullIfEmpty(careOfAddress));
				}
				if (!CPSUtils.isNullOrEmpty(city)) {
					addBean.setCity(CPSUtils.setNullIfEmpty(city));
				}
				if (!CPSUtils.isNullOrEmpty(state)) {
					addBean.setState(state);
				}
				if (!CPSUtils.isNullOrEmpty(district)) {
					addBean.setDistrict(district);
				}
				if (!CPSUtils.isNullOrEmpty(pincode)) {
					addBean.setPincode(CPSUtils.setNullIfEmpty(pincode));
				}

				if (!CPSUtils.isNullOrEmpty(phoneNumber)) {
					addBean.setPhone(CPSUtils.setNullIfEmpty(phoneNumber));
				}
				if (!CPSUtils.isNullOrEmpty(mobileNumber)) {
					addBean.setMobile(CPSUtils.setNullIfEmpty(mobileNumber));
				}
				if (!CPSUtils.isNullOrEmpty(email)) {
					addBean.setEmail(CPSUtils.setNullIfEmpty(email));
				}
				if (!CPSUtils.isNullOrEmpty(nearest_rly_stn)) {
					addBean.setNearestRyStation(CPSUtils.setNullIfEmpty(nearest_rly_stn));
				}
				if (!CPSUtils.isNullOrEmpty(nearest_airport)) {
					addBean.setNearestAirport(CPSUtils.setNullIfEmpty(nearest_airport));
				}
				if (CPSUtils.compareStrings(dispensary_number, "N") || CPSUtils.compareStrings(dispensary_number, "Y")) {
					if (!CPSUtils.isNullOrEmpty(dispensary_number)) {
						addBean.setDispensaryNumber(CPSUtils.setNullIfEmpty(dispensary_number));
					}
				} else {
					addBean.setDispensaryNumber(null);
				}
				addBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				if (CPSUtils.compareStrings(rowNumber, "3") || CPSUtils.compareStrings(CPSConstants.NEW, manage)) {

					String insertHometown = "insert into address_details_master(id,address_type_id,care_of_address,address1,address2,address3,city,state,district,pincode,phone_number,mobile_number,email,"
							+ "nearest_rly_stn,creation_date,status,last_modified_date,remarks,sfid,nearest_airport)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,1,sysdate,'',?,?)";
					ps3 = con.prepareStatement(insertHometown);
					ps3.setString(1, String.valueOf(domainObject.getTableID(CPSConstants.ADDRESSDETAILSMASTER)));
					if (CPSUtils.compareStrings(CPSConstants.NEW, manage) || CPSUtils.compareStrings(CPSConstants.UPDATE, manage))
						ps3.setString(2, addBean.getAddressTypeId());
					/*else if (CPSUtils.compareStrings(rowNumber, "3"))
						ps3.setString(2, "4");*/
					ps3.setString(3, addBean.getCareOfAddress());
					ps3.setString(4, addBean.getAddress1());
					ps3.setString(5, addBean.getAddress2());
					ps3.setString(6, addBean.getAddress3());
					ps3.setString(7, addBean.getCity());
					ps3.setString(8, addBean.getState());
					ps3.setString(9, addBean.getDistrict());
					ps3.setString(10, addBean.getPincode());
					ps3.setString(11, addBean.getPhone());
					ps3.setString(12, addBean.getMobile());
					ps3.setString(13, addBean.getEmail());
					ps3.setString(14, addBean.getNearestRyStation());
					ps3.setString(15, addBean.getSfid());
					ps3.setString(16, addBean.getNearestAirport());
					ps3.executeUpdate();

					/*
					 * AddressBean newObj = new AddressBean(); newObj.setId(); newObj.setAddressTypeId("4"); newObj.setStatus(1); newObj.setCreationDate(CPSUtils.getCurrentDate());
					 * newObj.setRemarks(""); newObj.setAddress1(addBean.getAddress1()); newObj.setAddress2(addBean.getAddress2()); newObj.setAddress3(addBean.getAddress3());
					 * newObj.setCity(addBean.getCity()); newObj.setState(addBean.getState()); newObj.setDistrict(addBean.getDistrict()); newObj.setPincode(addBean.getPincode());
					 * newObj.setPhone(addBean.getPhone()); newObj.setMobile(addBean.getMobile()); newObj.setEmail(addBean.getEmail()); newObj.setNearestRyStation(addBean.getNearestRyStation());
					 * newObj.setLastModifiedDate(CPSUtils.getCurrentDate()); newObj.setSfid(addBean.getSfid());
					 * 
					 * newObj.setNearestAirport(addBean.getNearestAirport()); //tx = session.beginTransaction(); session.save(newObj); session.flush();//tx.commit() ;
					 */
				} else {
					addBean.setId(referenceNumber);
					// addBean.setCreationDate(CPSUtils.formattedDate(addBean.getCreationDate()));
					session.saveOrUpdate(addBean);
				}
			}
			arb.setMessage(CPSConstants.SUCCESS);

		} catch (Exception e) {
			arb.setMessage(CPSConstants.FAILED);
			throw e;

		}
		return arb.getMessage();
	}
}
