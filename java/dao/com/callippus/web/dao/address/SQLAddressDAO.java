package com.callippus.web.dao.address;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLAddressDAO implements IAddressDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLAddressDAO.class);
	private static final long serialVersionUID = 7048899710717627065L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public ArrayList getEmployeeAddressDetails(AddressBean addressBean) throws Exception {
		log.debug("getEmployeeAddressDetails ---> method start");
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		ArrayList list = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String sql = "select atm.name addname,adm.address1 address1,adm.address2 address2,adm.address3 address3,adm.care_of_address,adm.city city,adm.phone_number phone_number,adm.email email,adm.district did,adm.state sid,dsm.name district,stm.name state,adm.pincode pincode,adm.mobile_number mobile_number,adm.nearest_rly_stn nearest_rly_stn,adm.sfid sfid,adm.id id,adm.address_type_id address_type_id,adm.nearest_airport nearest_airport,dispensary_number dispensary"
					+ " from address_details_master adm,address_type_master atm,district_master dsm,state_master stm where"
					+ " adm.address_type_id=atm.id and stm.id=adm.state and dsm.id=adm.district and adm.sfid=? and adm.status=1 order by address_type_id";

			log.debug("sql ---> " + sql);
			ps = con.prepareStatement(sql);
			ps.setString(1, addressBean.getSfid());
			rsq = ps.executeQuery();
		

			list = new ArrayList();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {
				AddressBean addBean = new AddressBean();
				addBean.setAddressTypeId(rsq.getString("address_type_id"));
				addBean.setAddressType(rsq.getString("addname"));
				addBean.setAddress1(rsq.getString("address1"));
				addBean.setAddress2(rsq.getString("address2"));
				addBean.setAddress3(rsq.getString("address3"));
				addBean.setCareOfAddress(rsq.getString("care_of_address"));
				addBean.setCity(rsq.getString("city"));
				addBean.setPhone(rsq.getString("phone_number"));
				addBean.setEmail(rsq.getString("email"));
				addBean.setDistrictId(rsq.getString("did"));
				addBean.setStateId(rsq.getString("sid"));
				addBean.setDistrict(rsq.getString("district"));
				addBean.setState(rsq.getString("state"));
				addBean.setPincode(rsq.getString("pincode"));
				addBean.setMobile(rsq.getString("mobile_number"));
				addBean.setNearestRyStation(rsq.getString("nearest_rly_stn"));
				addBean.setNearestAirport(rsq.getString("nearest_airport"));
				addBean.setSfid(rsq.getString("sfid"));
				addBean.setId(rsq.getString("id"));
				addBean.setDispensaryNumber(rsq.getString("dispensary"));
				list.add(addBean);

				LinkedHashMap innerjson = new LinkedHashMap();
				innerjson.put("id", rsq.getString("id"));
				innerjson.put("SFID", rsq.getString("sfid"));
				innerjson.put("Address Type", rsq.getString("addname"));
				innerjson.put("Care Of Address", rsq.getString("care_of_address"));
				innerjson.put("Address Line1", rsq.getString("address1"));
				innerjson.put("Address Line2", rsq.getString("address2"));
				innerjson.put("Address Line3", rsq.getString("address3"));
				innerjson.put("City", rsq.getString("city"));
				innerjson.put("State", rsq.getString("state"));
				innerjson.put("District", rsq.getString("district"));
				innerjson.put("Pincode", rsq.getString("pincode"));
				innerjson.put("Phone Number", rsq.getString("phone_number"));
				innerjson.put("Mobile Number", rsq.getString("mobile_number"));
				innerjson.put("Email", rsq.getString("email"));
				innerjson.put("Nearest Railway Station", rsq.getString("nearest_rly_stn"));
				innerjson.put("Nearest Airport", rsq.getString("nearest_airport"));
				if (!CPSUtils.isNull(rsq.getString("dispensary")))
					innerjson.put("Dispensary No Allocation", rsq.getString("dispensary"));
				outerjson.put(String.valueOf(i), innerjson);
				i++;
			}
		

			outerjson.put(String.valueOf(i), CPSConstants.ADDRESS);
			list.add(outerjson);
			session.flush() ; //con.commit();
		} catch (Exception e) {
			//con.rollback();
			throw e;
		}
		log.debug("getEmployeeAddressDetails ---> method end");
		return list;
	}

	public String manageAddressDetails(AddressBean addressBean) throws Exception {
		log.debug("manageAddressDetails ---> method start");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.saveOrUpdate(addressBean);
			message = CPSConstants.SUCCESS;
			session.flush();
			hibernateUtils.closeSession();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("message ---> " + message);
		log.debug("manageAddressDetails ---> method end");
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<AddressBean> getAddressDetailsMasterData(String addressId) throws Exception {
		log.debug("getAddressDetailsMasterData ---> method start");
		List<AddressBean> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(AddressBean.class).add(Expression.eq("id", addressId)).add(Expression.eq("status", 1)).list();
			session.flush();
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		log.debug("getAddressDetailsMasterData ---> method end");
		return list;
	}

	public String deleteAddressDetails(AddressBean addressBean) throws Exception {
		log.debug("deleteAddressDetails ---> method start");
		String message = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Connection con = session.connection();
			String sql = "update address_details_master set status=0,last_modified_date=sysdate where id=?";
			log.debug("sql ---> " + sql);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, addressBean.getId());
			pstmt.executeUpdate();
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		log.debug("message ---> " + message);
		log.debug("deleteAddressDetails ---> method end");
		return message;
	}

	public List<AddressBean> addressTypeCheck(AddressBean addressBean) throws Exception {
		log.debug("addressTypeCheck ---> method start");
		List<AddressBean> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session.createCriteria(AddressBean.class);
			crit.add(Expression.eq("addressTypeId", addressBean.getAddressTypeId()));
			crit.add(Expression.eq("sfid", addressBean.getSfid()));
			crit.add(Expression.eq("status", 1));
			list = crit.list();
		} catch (Exception e) {
			throw e;
		}
		log.debug("addressTypeCheck ---> method end");
		return list;
	}

	public String updateAddressDetails(AddressBean addressBean) throws Exception {
		log.debug("updateAddressDetails ---> method start");
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String sql = "UPDATE ADDRESS_DETAILS_MASTER SET ADDRESS_TYPE_ID=?,ADDRESS1=?,ADDRESS2=?,ADDRESS3=?,CITY=?,"
					+ "STATE=?,DISTRICT=?,PINCODE=?,PHONE_NUMBER=?,MOBILE_NUMBER=?,EMAIL=?,NEAREST_RLY_STN=?,NEAREST_AIRPORT=?,DISPENSARY_NUMBER=?,CARE_OF_ADDRESS=?,LAST_MODIFIED_DATE=SYSDATE "
					+ "WHERE ID=? AND STATUS=1";
			log.debug("sql ---> " + sql);
			ps = con.prepareStatement(sql);
			ps.setString(1, addressBean.getAddressTypeId());
			ps.setString(2, addressBean.getAddress1());
			ps.setString(3, addressBean.getAddress2());
			ps.setString(4, addressBean.getAddress3());

			ps.setString(5, addressBean.getCity());
			ps.setString(6, addressBean.getState());
			ps.setString(7, addressBean.getDistrict());

			ps.setString(8, addressBean.getPincode());
			ps.setString(9, addressBean.getPhone());
			ps.setString(10, addressBean.getMobile());
			ps.setString(11, addressBean.getEmail());
			ps.setString(12, addressBean.getNearestRyStation());
			ps.setString(13, addressBean.getNearestAirport());
			if (CPSUtils.isNull(addressBean.getDispensaryNumber()))
				ps.setString(14, "N");
			else
				ps.setString(14, addressBean.getDispensaryNumber());
			ps.setString(15, addressBean.getCareOfAddress());
			ps.setString(16, addressBean.getId());
			ps.executeQuery();
			session.flush();
			message = CPSConstants.UPDATE;
			session.flush() ; //con.commit();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			//con.rollback();
			throw e;
		} 
		log.debug("message ---> " + message);
		log.debug("updateAddressDetails ---> method end");
		return message;
	}

	public List getEditAddressDetails(String id) throws Exception {
		log.debug("getEditAddressDetails ---> method start");
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			Criteria crit = session.createCriteria(AddressBean.class);
			crit.add(Expression.eq("id", id));
			crit.add(Expression.eq("status", 1));
			list = crit.list();
			session.flush();
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		log.debug("getEditAddressDetails ---> method end");
		return list;
	}

	public String setHometownStatus(AddressBean addressBean) throws Exception {
		log.debug("setHometownStatus ---> method start");
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String sql = "update address_details_master set status='0',last_modified_date=to_date(sysdate,'DD-MON-YYYY') where address_type_id='3' and sfid=?";
			log.debug("sql ---> " + sql);
			ps = con.prepareStatement(sql);
			ps.setString(1, addressBean.getSfid());
			ps.executeUpdate();
			message = CPSConstants.SUCCESS;
			session.flush();
			session.flush() ; //con.commit();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			//con.rollback();
			throw e;
		}
		log.debug("message ---> " + message);
		log.debug("setHometownStatus ---> method end");
		return message;
	}
}