package com.callippus.web.dao.nominee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.nominee.NomineeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLNomineeDAO implements INomineeDAO {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public ArrayList<KeyValueDTO> getFamilyMembersList(String sfid) throws Exception {
		ArrayList<KeyValueDTO> familyList = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			familyList = new ArrayList<KeyValueDTO>();
			String sql = "select id,name from family_details where sfid=? and status=1 order by name";
			ps = con.prepareStatement(sql);
			ps.setString(1, sfid);
			rsq = ps.executeQuery();

			while (rsq.next()) {
				KeyValueDTO keyVal = new KeyValueDTO();
				keyVal.setId(rsq.getInt("id"));
				keyVal.setName(rsq.getString("name"));
				familyList.add(keyVal);
			}
		} catch (Exception e) {
			throw e;
		}
		return familyList;
	}

	@SuppressWarnings("unchecked")
	public NomineeBean getNomineeList(NomineeBean nomineBean) throws Exception {
		ArrayList nomineeList = null;
		ArrayList<NomineeBean> displayNomineeList = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rsq = null;
		ResultSet rsq1 = null;
		ResultSet rsq2 = null;
		String nomineeTypeID = null;
		float percentage = 0;
		HashMap<String, String> percentageMap = null;
		ArrayList<KeyValueDTO> familyMembers = null;
		try {
			percentageMap = new HashMap<String, String>();
			familyMembers = new ArrayList<KeyValueDTO>();
			displayNomineeList = new ArrayList<NomineeBean>();
			session = hibernateUtils.getSession();
			con = session.connection();
			nomineeList = new ArrayList();

			JSONObject outerjson = new JSONObject(); // creating outer json,i
			// for viewnominee details
			int i = 0;

			String sql = "select nd.id,nm.name nomineeTypeName,nd.nominee_type_id,nd.nominee,nd.family_member_id,nd.percentage,to_char(nd.date_of_nominate,'DD-Mon-YYYY') dateOfNominate,nd.remarks,nd.incontangensy_parent from nominee_details nd,nominee_type_master nm "
					+ "where nd.sfid=? and nd.status=1 and nm.id=nd.nominee_type_id order by nd.id";
			ps = con.prepareStatement(sql);
			ps.setString(1, nomineBean.getChangedSfid());
			rsq = ps.executeQuery();

			while (rsq.next()) {
				NomineeBean nomineeBean = new NomineeBean();

				LinkedHashMap innerjson = new LinkedHashMap(); // creating
				// linkedhashmap
				// for
				// viewnominee
				// details

				nomineeBean.setId(rsq.getString("id"));
				nomineeBean.setNomineeTypeId(rsq.getString("nominee_type_id"));
				nomineeBean.setNomineeTypeName(rsq.getString("nomineeTypeName"));

				innerjson.put("id", rsq.getString("id"));
				innerjson.put("Nominee Type", rsq.getString("nomineeTypeName")); // Nomineetype
				// into
				// linkedhashmap
				innerjson.put("Family Member", rsq.getString("nominee"));

				if ((!CPSUtils.compareStrings(nomineeTypeID, rsq.getString("nominee_type_id")) || CPSUtils.isNull(rsq.getString("nominee_type_id")))
						&& CPSUtils.compareStrings(rsq.getString("incontangensy_parent"), "0")) {
					nomineeTypeID = rsq.getString("nominee_type_id");
					percentage = 0;
					percentage = rsq.getFloat("percentage");
				} else if (CPSUtils.compareStrings(rsq.getString("incontangensy_parent"), "0")) {
					percentage += rsq.getFloat("percentage");
				}
				nomineeBean.setNomineeName(rsq.getString("nominee"));
				nomineeBean.setFamilyID(rsq.getString("family_member_id"));
				nomineeBean.setPercentage(rsq.getString("percentage"));
				nomineeBean.setDateOfNominate(rsq.getString("dateOfNominate"));
				nomineeBean.setRemarks(rsq.getString("remarks"));
				nomineeBean.setInContengensyParent(rsq.getString("incontangensy_parent"));

				if (!CPSUtils.compareStrings(nomineeBean.getInContengensyParent(), "0")) {
					String sql1 = "select nominee from nominee_details where id=?";
					ps1 = con.prepareStatement(sql1);

					ps1.setString(1, nomineeBean.getInContengensyParent());
					rsq2 = ps1.executeQuery();

					if (rsq2.next()) {
						nomineeBean.setInContengensyParentName(rsq2.getString("nominee"));
						innerjson.put("In contingency of", rsq2.getString("nominee"));

					}
				} else {
					nomineeBean.setInContengensyParentName("");
					innerjson.put("In contingency of", "");
				}

				if (CPSUtils.isNullOrEmpty(rsq.getString("family_member_id"))) {
					// get the address details from nominee address details
					String sql1 = "select nad.address1,nad.address2,nad.address3,nad.city,nad.state_id,nad.district_id,nad.pincode from nominee_address_details nad where nominee_id=?";
					ps2 = con.prepareStatement(sql1);
					ps2.setString(1, rsq.getString("id"));
					rsq1 = ps2.executeQuery();

					if (rsq1.next()) {
						nomineeBean.setAddress1(rsq1.getString("address1"));
						nomineeBean.setAddress2(rsq1.getString("address2"));
						nomineeBean.setAddress3(rsq1.getString("address3"));
						nomineeBean.setCity(rsq1.getString("city"));
						nomineeBean.setStateId(rsq1.getString("state_id"));
						nomineeBean.setDistrictId(rsq1.getString("district_id"));
						nomineeBean.setPincode(rsq1.getString("pincode"));

						innerjson.put("Address Line1", rsq1.getString("address1"));
						innerjson.put("Address Line2", rsq1.getString("address2"));
						innerjson.put("Address Line3", rsq1.getString("address3"));
						innerjson.put("City", rsq1.getString("city"));
						innerjson.put("State", getMasterValue(CPSConstants.STATE_MASTER, rsq1.getString("state_id")));
						innerjson.put("District", getMasterValue(CPSConstants.DISTRICT_MASTER, rsq1.getString("district_id")));
						innerjson.put("Pincode", rsq1.getString("pincode"));
					}
				}
				innerjson.put("Percentage", rsq.getString("percentage"));
				innerjson.put("Date of Nomination", rsq.getString("dateOfNominate"));
				innerjson.put("Remarks", rsq.getString("remarks"));
				outerjson.put(String.valueOf(i), innerjson);
				i++;

				displayNomineeList.add(nomineeBean);
				percentageMap.put(rsq.getString("nominee_type_id"), String.valueOf(percentage));

				KeyValueDTO famDetails = new KeyValueDTO();
				famDetails.setKey(rsq.getString("nominee_type_id"));
				famDetails.setName(rsq.getString("family_member_id"));
				famDetails.setId(rsq.getInt("incontangensy_parent"));
				familyMembers.add(famDetails);

			}
			outerjson.put(String.valueOf(i), CPSConstants.EMPNOMINEE);
			nomineeList.add(outerjson);

			nomineBean.setPercentageList(JSONSerializer.toJSON(percentageMap));
			nomineBean.setFamilyMemberInNominee(JSONSerializer.toJSON(familyMembers));
			nomineBean.setNomineeList(nomineeList);
			nomineBean.setDisplayNomineeList(displayNomineeList);

		} catch (Exception e) {
			throw e;
		}
		return nomineBean;
	}

	public String getMasterValue(String tableName, String id) throws Exception {
		Session session = null;
		PreparedStatement ps2 = null;
		ResultSet rsq3 = null;
		Connection con = null;
		String value = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();

			String getState = "select name from " + tableName + " where id=?";
			ps2 = con.prepareStatement(getState);
			ps2.setString(1, id);
			rsq3 = ps2.executeQuery();
			if (rsq3.next()) {
				value = rsq3.getString("name");
			}
		} catch (Exception e) {
			throw e;
		}
		return value;
	}

	public String submitNomineeDetails(NomineeBean nomineeBean) throws Exception {
		JSONObject inconsistencyDetails = null;
		try {
			nomineeBean.setId((Integer.valueOf(getTableID("NOMINEE_DETAILS"))).toString());
			if (CPSUtils.compareStrings("other", nomineeBean.getFamilyID())) {
				nomineeBean.setFamilyID(null);
				nomineeBean.setNomineeAddressID((Integer.valueOf(getTableID("NOMINEE_ADDRESS_DETAILS"))).toString());
			}
			nomineeBean.setInconsistencyParentId(0);

			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, insertNomineeDetails(nomineeBean))) {
				nomineeBean.setInconsistencyParentId(Integer.parseInt(nomineeBean.getId()));

				String[] inconstDetails = nomineeBean.getInconsistencyDetails().split("#");
				for (int i = 0; i < inconstDetails.length; i++) {
					if (!CPSUtils.isNullOrEmpty(inconstDetails[i])) {
						inconsistencyDetails = (JSONObject) JSONSerializer.toJSON(inconstDetails[i]);
						nomineeBean.setId((Integer.valueOf(getTableID("NOMINEE_DETAILS"))).toString());
						nomineeBean.setNomineeName(inconsistencyDetails.get("nomineeName").toString());
						nomineeBean.setFamilyID(inconsistencyDetails.get("familyID").toString());
						nomineeBean.setPercentage(inconsistencyDetails.get("percentage").toString());
						nomineeBean.setDateOfNominate(inconsistencyDetails.get("dateOfNominate").toString());
						nomineeBean.setRemarks("");
						if (CPSUtils.compareStrings("other", inconsistencyDetails.get("familyID").toString())) {
							nomineeBean.setFamilyID(null);
							nomineeBean.setNomineeAddressID((Integer.valueOf(getTableID("NOMINEE_ADDRESS_DETAILS"))).toString());

							nomineeBean.setAddress1(inconsistencyDetails.get("address1").toString());
							nomineeBean.setAddress2(inconsistencyDetails.get("address2").toString());
							nomineeBean.setAddress3(inconsistencyDetails.get("address3").toString());
							nomineeBean.setCity(inconsistencyDetails.get("city").toString());
							nomineeBean.setStateId(inconsistencyDetails.get("state").toString());
							nomineeBean.setDistrictId(inconsistencyDetails.get("district").toString());
							nomineeBean.setPincode(inconsistencyDetails.get("pincode").toString());

						}
						insertNomineeDetails(nomineeBean);
					}
				}
			}
		} catch (Exception e) {
			nomineeBean.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return nomineeBean.getMessage();
	}

	public String insertNomineeDetails(NomineeBean nomineeBean) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String sql = "insert into nominee_details(id,nominee_type_id,nominee,family_member_id,percentage,date_of_nominate,remarks,incontangensy_parent,sfid,status,creation_date,last_modified_date) "
					+ "values(?,?,?,?,?,?,?,?,?,1,sysdate,sysdate)";
			ps = con.prepareStatement(sql);
			ps.setString(1, nomineeBean.getId());
			ps.setString(2, nomineeBean.getNomineeTypeId());
			ps.setString(3, nomineeBean.getNomineeName());
			ps.setString(4, nomineeBean.getFamilyID());
			ps.setString(5, nomineeBean.getPercentage());
			ps.setString(6, nomineeBean.getDateOfNominate());
			ps.setString(7, nomineeBean.getRemarks());
			ps.setInt(8, nomineeBean.getInconsistencyParentId());
			ps.setString(9, nomineeBean.getChangedSfid());
			ps.executeUpdate();
			if (CPSUtils.isNullOrEmpty(nomineeBean.getFamilyID())) {
				// insert into nominee address details

				if (CPSUtils.compareStrings("select", nomineeBean.getStateId())) {
					nomineeBean.setStateId("0");
				}
				if (CPSUtils.compareStrings("select", nomineeBean.getDistrictId())) {
					nomineeBean.setDistrictId("0");
				}

				String sql1 = "insert into nominee_address_details(id,nominee_id,address1,address2,address3,city,state_id,district_id,pincode) values(?,?,?,?,?,?,?,?,?)";
				ps1 = con.prepareStatement(sql1);
				ps1.setString(1, nomineeBean.getNomineeAddressID());
				ps1.setString(2, nomineeBean.getId());
				ps1.setString(3, nomineeBean.getAddress1());
				ps1.setString(4, nomineeBean.getAddress2());
				ps1.setString(5, nomineeBean.getAddress3());
				ps1.setString(6, nomineeBean.getCity());
				ps1.setString(7, nomineeBean.getStateId());
				ps1.setString(8, nomineeBean.getDistrictId());
				ps1.setString(9, nomineeBean.getPincode());
				ps1.executeUpdate();
			}
			nomineeBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			nomineeBean.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return nomineeBean.getMessage();
	}

	public String updateNomineeDetails(NomineeBean nomineeBean) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		JSONObject inconsistencyDetails = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			if (CPSUtils.compareStrings("other", nomineeBean.getFamilyID())) {
				nomineeBean.setFamilyID(null);
			}
			String sql = "update nominee_details set nominee_type_id=?,nominee=?,family_member_id=?,percentage=?,date_of_nominate=?,remarks=?,incontangensy_parent=0,last_modified_date=sysdate where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, nomineeBean.getNomineeTypeId());
			ps.setString(2, nomineeBean.getNomineeName());
			ps.setString(3, nomineeBean.getFamilyID());
			ps.setString(4, nomineeBean.getPercentage());
			ps.setString(5, nomineeBean.getDateOfNominate());
			ps.setString(6, nomineeBean.getRemarks());
			ps.setString(7, nomineeBean.getNomineeID());
			ps.executeUpdate();
			if (CPSUtils.isNullOrEmpty(nomineeBean.getFamilyID())) {
				if (CPSUtils.compareStrings("select", nomineeBean.getStateId())) {
					nomineeBean.setStateId("0");
				}
				if (CPSUtils.compareStrings("select", nomineeBean.getDistrictId())) {
					nomineeBean.setDistrictId("0");
				}

				String sql1 = "update nominee_address_details set address1=?,address2=?,address3=?,city=?,state_id=?,district_id=?,pincode=? where nominee_id=?";
				ps1 = con.prepareStatement(sql1);
				ps1.setString(1, nomineeBean.getAddress1());
				ps1.setString(2, nomineeBean.getAddress2());
				ps1.setString(3, nomineeBean.getAddress3());
				ps1.setString(4, nomineeBean.getCity());
				ps1.setString(5, nomineeBean.getStateId());
				ps1.setString(6, nomineeBean.getDistrictId());
				ps1.setString(7, nomineeBean.getPincode());
				ps1.setString(8, nomineeBean.getNomineeID());
				ps1.executeUpdate();
			}

			String sql1 = "update nominee_details set status=0 where incontangensy_parent=?";
			ps1 = con.prepareStatement(sql1);
			ps1.setString(1, nomineeBean.getNomineeID());
			ps1.executeUpdate();

			nomineeBean.setInconsistencyParentId(Integer.parseInt(nomineeBean.getNomineeID()));

			String[] inconstDetails = nomineeBean.getInconsistencyDetails().split("#");
			for (int i = 0; i < inconstDetails.length; i++) {
				if (!CPSUtils.isNullOrEmpty(inconstDetails[i])) {
					inconsistencyDetails = (JSONObject) JSONSerializer.toJSON(inconstDetails[i]);
					nomineeBean.setId((Integer.valueOf(getTableID("NOMINEE_DETAILS"))).toString());
					nomineeBean.setNomineeName(inconsistencyDetails.get("nomineeName").toString());
					nomineeBean.setFamilyID(inconsistencyDetails.get("familyID").toString());
					nomineeBean.setPercentage(inconsistencyDetails.get("percentage").toString());
					nomineeBean.setDateOfNominate(inconsistencyDetails.get("dateOfNominate").toString());
					nomineeBean.setRemarks("");
					if (CPSUtils.compareStrings("other", inconsistencyDetails.get("familyID").toString())) {
						nomineeBean.setFamilyID(null);
						nomineeBean.setNomineeAddressID((Integer.valueOf(getTableID("NOMINEE_ADDRESS_DETAILS"))).toString());

						nomineeBean.setAddress1(inconsistencyDetails.get("address1").toString());
						nomineeBean.setAddress2(inconsistencyDetails.get("address2").toString());
						nomineeBean.setAddress3(inconsistencyDetails.get("address3").toString());
						nomineeBean.setCity(inconsistencyDetails.get("city").toString());
						nomineeBean.setStateId(inconsistencyDetails.get("state").toString());
						nomineeBean.setDistrictId(inconsistencyDetails.get("district").toString());
						nomineeBean.setPincode(inconsistencyDetails.get("pincode").toString());

					}
					insertNomineeDetails(nomineeBean);
				}
			}
			nomineeBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			nomineeBean.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return nomineeBean.getMessage();
	}

	public String deleteNomineeDetails(String id) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();

			String sql = "update nominee_details set status=0 where id=? or incontangensy_parent=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, id);
			ps.executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} 
		return message;
	}

	public int getTableID(String tableName) throws Exception {
		List masterObjList = null;
		Session session = null;
		int id = 0;
		PreparedStatement pstmt = null;
		try {
			session = hibernateUtils.getSession();
			Query qry = session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE");
			qry.setString(0, tableName);
			masterObjList = qry.list();
			id = Integer.parseInt(masterObjList.get(0).toString()) + 1;

			Connection con = session.connection();
			String sql = "update id_generator set value=? where table_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, tableName);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		}
		return id;
	}
}