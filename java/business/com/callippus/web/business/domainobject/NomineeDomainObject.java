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
import com.callippus.web.beans.dto.NomineeAddressDTO;
import com.callippus.web.beans.nominee.NomineeBean;
import com.callippus.web.beans.requests.NomineeRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class NomineeDomainObject extends DomainObject {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private DomainObject domainObject;

	
	/**
	 * This method will update the txn nominee details
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String updateTxnDetails(NomineeRequestBean nrb) throws Exception {

		Session session1 = null;
		Session session2 = null;
		Session session3 = null;
		Session session4 = null;
		Session session5 = null;
		Session session6 = null;
		Session session7 = null;
		Connection con1 = null;
		PreparedStatement ps10 = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rsq10 = null;
		Transaction tx = null;
		String nomineeID = null;

		String row_number = null;
		String reference_number = null;
		String sfid = null;
		String nominee = null;
		String percentage = null;
		String remarks = null;
		String date_of_nominate = null;
		String nominee_type_id = null;
		String family_member_id = null;
		String address1 = null;
		String address2 = null;
		String address3 = null;
		String city = null;
		String state_id = null;
		String district_id = null;
		String pincode = null;
		boolean status = true;

		try {
//VN        session1 = hibernateUtils.getSession();
			session1 = hibernateUtils.getSession();

			con1 = session1.connection();

			String getTxnDetails = "SELECT row_number,reference_number,MAX(CASE WHEN column_name = 'SFID' THEN new_value END) AS SFID,"
					+ "MAX(CASE WHEN column_name = 'NOMINEE' THEN new_value END) AS NOMINEE,MAX(CASE WHEN column_name = 'PERCENTAGE' THEN new_value END) AS PERCENTAGE,"
					+ "MAX(CASE WHEN column_name = 'REMARKS' THEN new_value END) AS REMARKS, MAX(CASE WHEN column_name = 'DATE_OF_NOMINATE' THEN new_value END) AS DATE_OF_NOMINATE,"
					+ "MAX(CASE WHEN column_name = 'FAMILY_MEMBER_ID' THEN new_value END) AS FAMILY_MEMBER_ID,MAX(CASE WHEN column_name = 'NOMINEE_TYPE_ID' THEN new_value END) AS NOMINEE_TYPE_ID,"
					+ "MAX(CASE WHEN column_name = 'ADDRESS1' THEN new_value END) AS ADDRESS1,MAX(CASE WHEN column_name = 'ADDRESS2' THEN new_value END) AS ADDRESS2,"
					+ "MAX(CASE WHEN column_name = 'ADDRESS3' THEN new_value END) AS ADDRESS3,MAX(CASE WHEN column_name = 'CITY' THEN new_value END) AS CITY,"
					+ "MAX(CASE WHEN column_name = 'STATE_ID' THEN new_value END) AS STATE_ID,MAX(CASE WHEN column_name = 'DISTRICT_ID' THEN new_value END) AS DISTRICT_ID,"
					+ "MAX(CASE WHEN column_name = 'PINCODE' THEN new_value END) AS PINCODE FROM workflow_txn_details where request_id=? GROUP BY row_number, reference_number order by row_number";

			ps10 = con1.prepareStatement(getTxnDetails);
			ps10.setString(1, nrb.getRequestID());
			rsq10 = ps10.executeQuery();
			while (rsq10.next()) {
				row_number = rsq10.getString("row_number");
				reference_number = rsq10.getString("reference_number");
				if (CPSUtils.isNullOrEmpty(sfid)) {
					sfid = rsq10.getString("sfid");
				}

				nominee = rsq10.getString("nominee");
				percentage = rsq10.getString("percentage");
				remarks = rsq10.getString("remarks");
				date_of_nominate = rsq10.getString("date_of_nominate");
				nominee_type_id = rsq10.getString("nominee_type_id");
				family_member_id = rsq10.getString("family_member_id");
				address1 = rsq10.getString("address1");
				address2 = rsq10.getString("address2");
				address3 = rsq10.getString("address3");
				city = rsq10.getString("city");
				state_id = rsq10.getString("state_id");
				district_id = rsq10.getString("district_id");
				pincode = rsq10.getString("pincode");
				if (CPSUtils.compareStrings(state_id, CPSConstants.SELECT)) {
					state_id = "0";
				}
				if (CPSUtils.compareStrings(district_id, CPSConstants.SELECT)) {
					district_id = "0";
				}

				if (CPSUtils.compareStrings(row_number, "0")) {
					// main row
	//VN            session2 = hibernateUtils.getSession(); //session2 = sessionFactory.openSession();
					session2 = hibernateUtils.getSession();

					
					Criteria crit = session2.createCriteria(NomineeBean.class);
					crit.add(Expression.eq("id", reference_number));
					List<AddressBean> list = crit.list();
					Iterator it = list.iterator();
					NomineeBean nomineeBean = null;
					String manage = null;
					if (it.hasNext()) {
						nomineeBean = (NomineeBean) it.next();
						nomineeBean.setId(reference_number);
						nomineeBean.setDateOfNominate(CPSUtils.formattedDate(date_of_nominate));
						nomineeBean.setCreationDate(CPSUtils.formattedDate(nomineeBean.getCreationDate()));
						manage = CPSConstants.UPDATE;
					} else {
						nomineeBean = new NomineeBean();
						nomineeBean.setId(String.valueOf(domainObject.getTableID(CPSConstants.NOMINEE_DETAILS)));
						nomineeBean.setStatus(1);
						nomineeBean.setCreationDate(CPSUtils.getCurrentDate());
						manage = CPSConstants.NEW;
					}
					nomineeID = nomineeBean.getId();
					nomineeBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					if (!CPSUtils.isNullOrEmpty(sfid)) {
						nomineeBean.setSfid(sfid);
					}
					if (!CPSUtils.isNullOrEmpty(nominee)) {
						nomineeBean.setNomineeName(CPSUtils.setNullIfEmpty(nominee));
					}
					if (!CPSUtils.isNullOrEmpty(percentage)) {
						nomineeBean.setPercentage(CPSUtils.setNullIfEmpty(percentage));
					}
					if (!CPSUtils.isNullOrEmpty(remarks)) {
						nomineeBean.setRemarks(CPSUtils.setNullIfEmpty(remarks));
					}
					if (!CPSUtils.isNullOrEmpty(date_of_nominate)) {
						nomineeBean.setDateOfNominate(date_of_nominate);
					}
					if (!CPSUtils.isNullOrEmpty(nominee_type_id)) {
						nomineeBean.setNomineeTypeId(CPSUtils.setNullIfEmpty(nominee_type_id));
					}
					nomineeBean.setInContengensyParent("0");
					if (CPSUtils.compareStrings(family_member_id, "OTHER")) {
						nomineeBean.setFamilyID(null);
						NomineeAddressDTO nomineeAddDTO = null;
		//VN           session3 = sessionFactory.openSession();
						session3 = hibernateUtils.getSession();

						Criteria crit1 = session3.createCriteria(NomineeAddressDTO.class);
						crit1.add(Expression.eq("nomineeID", nomineeBean.getId()));
						List<AddressBean> list1 = crit1.list();
						Iterator it1 = list1.iterator();
						if (it1.hasNext()) {
							// address details exists
							nomineeAddDTO = (NomineeAddressDTO) it1.next();
						} else {
							// no address details
							nomineeAddDTO = new NomineeAddressDTO();
							nomineeAddDTO.setId(String.valueOf(domainObject.getTableID(CPSConstants.NOMINEE_ADDRESS_DETAILS)));
						}
						nomineeAddDTO.setNomineeID(nomineeBean.getId());

						if (!CPSUtils.isNullOrEmpty(address1)) {
							nomineeAddDTO.setAddress1(CPSUtils.setNullIfEmpty(address1));
						}
						if (!CPSUtils.isNullOrEmpty(address2)) {
							nomineeAddDTO.setAddress2(CPSUtils.setNullIfEmpty(address2));
						}
						if (!CPSUtils.isNullOrEmpty(address3)) {
							nomineeAddDTO.setAddress3(CPSUtils.setNullIfEmpty(address3));
						}
						if (!CPSUtils.isNullOrEmpty(city)) {
							nomineeAddDTO.setCity(CPSUtils.setNullIfEmpty(city));
						}
						if (!CPSUtils.isNullOrEmpty(state_id)) {
							nomineeAddDTO.setStateID(state_id);// lim
						}
						if (!CPSUtils.isNullOrEmpty(district_id)) {
							nomineeAddDTO.setDistrictID(district_id);// lim
						}
						if (!CPSUtils.isNullOrEmpty(pincode)) {
							nomineeAddDTO.setPincode(CPSUtils.setNullIfEmpty(pincode));
						}
		//VN            session4 = sessionFactory.openSession();
						session4 = hibernateUtils.getSession();

						//tx = session4.beginTransaction();
						session4.saveOrUpdate(nomineeAddDTO);
						session4.flush();
						//session.flush();//tx.commit() ;

					} else {
						// changed some other to family member so update the
						// status=0 in nominee_address_details

						nomineeBean.setFamilyID(CPSUtils.setNullIfEmpty(family_member_id));
					}
					if (CPSUtils.compareStrings(CPSConstants.UPDATE, manage)) {
		//VN         session5 = sessionFactory.openSession();
					 session5 = hibernateUtils.getSession();

						//tx = session5.beginTransaction();
						session5.saveOrUpdate(nomineeBean);
						session5.flush();
						//session.flush();//tx.commit() ;
					} else {
						// new record
						String createNominee = "insert into nominee_details(id,sfid,nominee,percentage,creation_date,status,remarks,last_modified_date,date_of_nominate,family_member_id,nominee_type_id,incontangensy_parent) "
								+ "values(?,?,?,?,sysdate,1,?,sysdate,?,?,?,?)";
						ps2 = con1.prepareStatement(createNominee);
						ps2.setString(1, nomineeBean.getId());
						ps2.setString(2, nomineeBean.getSfid());
						ps2.setString(3, nomineeBean.getNomineeName());
						ps2.setString(4, nomineeBean.getPercentage());
						ps2.setString(5, nomineeBean.getRemarks());
						ps2.setString(6, nomineeBean.getDateOfNominate());
						ps2.setString(7, nomineeBean.getFamilyID());
						ps2.setString(8, nomineeBean.getNomineeTypeId());
						ps2.setString(9, nomineeBean.getInContengensyParent());
						ps2.executeUpdate();
					}

				} else {
					// incontengensy row
					if (CPSUtils.compareStrings(row_number, "1") && status) {
						status = false;
						String updateIncDetails = "update nominee_details set status=0,last_modified_date=sysdate where incontangensy_parent=?";
						ps1 = con1.prepareStatement(updateIncDetails);
						ps1.setString(1, nomineeID);
						ps1.executeUpdate();
					}
					NomineeBean nomineeBean = new NomineeBean();
					nomineeBean.setId(String.valueOf(domainObject.getTableID(CPSConstants.NOMINEE_DETAILS)));
					nomineeBean.setStatus(1);
					nomineeBean.setCreationDate(CPSUtils.getCurrentDate());
					nomineeBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					nomineeBean.setSfid(sfid);
					nomineeBean.setNomineeName(nominee);
					nomineeBean.setPercentage(percentage);
					nomineeBean.setRemarks(remarks);
					nomineeBean.setDateOfNominate(date_of_nominate);
					nomineeBean.setNomineeTypeId(nominee_type_id);
					nomineeBean.setInconsistencyParentId(Integer.parseInt(nomineeID));
					NomineeAddressDTO nomineeAddDTO = null;
					if (CPSUtils.compareStrings(family_member_id, "OTHER")) {
						nomineeBean.setFamilyID(null);
						nomineeAddDTO = new NomineeAddressDTO();
						nomineeAddDTO.setId(String.valueOf(domainObject.getTableID(CPSConstants.NOMINEE_ADDRESS_DETAILS)));
						nomineeAddDTO.setNomineeID(nomineeBean.getId());
						nomineeAddDTO.setAddress1(address1);
						nomineeAddDTO.setAddress2(address2);
						nomineeAddDTO.setAddress3(address3);
						nomineeAddDTO.setCity(city);

						nomineeAddDTO.setStateID(state_id);
						nomineeAddDTO.setDistrictID(district_id);
						nomineeAddDTO.setPincode(pincode);

					} else {
						// changed some other to family member so update the
						// status=0 in nominee_address_details

						nomineeBean.setFamilyID(family_member_id);
					}
	//VN         session7 = sessionFactory.openSession();
					session7 = hibernateUtils.getSession();

				//tx = session7.beginTransaction();
					session7.saveOrUpdate(nomineeBean);
					session7.flush();
					//session.flush();//tx.commit() ;
					if (CPSUtils.compareStrings(family_member_id, "OTHER")) {
		//VN          session6 = sessionFactory.openSession();
						session6 = hibernateUtils.getSession();

						//tx = session6.beginTransaction();
						session6.saveOrUpdate(nomineeAddDTO);
						session6.flush();
						//session.flush();//tx.commit() ;

					}
				}
			}
			nrb.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			nrb.setMessage(CPSConstants.FAILED);
			throw e;

		} finally {
			ConnectionUtil.closeConnection(null, ps10, rsq10);
			ConnectionUtil.closeConnection(null, ps1, null);
			//session3.close();
			//session4.close();
			ConnectionUtil.closeConnection(null, ps2, null);
			//session6.close();
			//session7.close();

		}
		return nrb.getMessage();
	}
}
