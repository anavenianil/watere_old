package com.callippus.web.dao.passport;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.FamilyRelationDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.passport.PassportApplicationDTO;
import com.callippus.web.beans.passport.PassportApplicationDetailsDTO;
import com.callippus.web.beans.passport.PassportBean;
import com.callippus.web.beans.passport.PassportRequestProcessBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLPassportDAO implements IPassportDAO, Serializable {

	private static final long serialVersionUID = 7048899710717627065L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public ArrayList getEmployeePassportDetails(String sfid) throws Exception {
		Session session = null;
		ArrayList passportList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();

			String sql = "select pd.id,pd.sfid,(select frm.name from family_details fd,family_relation_master frm where"
					+ " fd.status=1 and fd.relation_id=frm.id and fd.id=pd.family_member_id)familymembername,pd.passport_type,pd.passport_number,pd.issued_place,to_char(pd.valid_upto,'DD-Mon-YYYY')valid_upto,pd.details,pd.remarks,pd.passport_to,pd.family_member_id "
					+ "from passport_details pd where pd.status=? and pd.sfid=? order by pd.passport_type";
			ps = con.prepareStatement(sql);
			ps.setString(1, "1");
			ps.setString(2, sfid);
			rsq = ps.executeQuery();

			passportList = new ArrayList();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {
				PassportBean passbean = new PassportBean();
				passbean.setId(rsq.getString("id"));
				passbean.setSfid(rsq.getString("sfid"));
				passbean.setPassportType(rsq.getString("passport_type"));
				passbean.setPassportNumber(rsq.getString("passport_number"));
				passbean.setIssuePlace(rsq.getString("issued_place"));
				passbean.setValidUpto(rsq.getString("valid_upto"));
				passbean.setDetails(rsq.getString("details"));
				passbean.setRemarks(rsq.getString("remarks"));
				passbean.setPassPortId(rsq.getString("passport_to"));
				passbean.setFamilyMember(rsq.getString("family_member_id"));

				LinkedHashMap<String, String> innerjson = new LinkedHashMap<String, String>();

				innerjson.put("id", rsq.getString("id"));
				innerjson.put("SFID", rsq.getString("sfid"));
				innerjson.put("Passport Type", rsq.getString("passport_type"));
				innerjson.put("Passport For", rsq.getString("passport_to"));
				innerjson.put("Family Member", rsq.getString("familymembername"));
				innerjson.put("Passport Number", rsq.getString("passport_number"));
				innerjson.put("Issued Place", rsq.getString("issued_place"));
				innerjson.put("Valid Upto", rsq.getString("valid_upto"));
				innerjson.put("Details", rsq.getString("details"));
				innerjson.put("Remarks", rsq.getString("remarks"));
				passportList.add(passbean);

				outerjson.put(String.valueOf(i), innerjson);
				i++;
			}
			outerjson.put(String.valueOf(i), CPSConstants.PASSPORT);
			passportList.add(outerjson);
		} catch (Exception e) {

			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return passportList;
	}

	public String deletePassport(String id) throws Exception {
		String message = "";
		PreparedStatement pstmt = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			Connection con = session.connection();
			String sql = "update passport_details set status=0,last_modified_date=sysdate where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.DELETE;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
	}

	public String managePassportDetails(PassportBean passportBean) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(passportBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public List<PassportBean> getEditPassportDetails(String passportId) throws Exception {
		List<PassportBean> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(PassportBean.class);
			crit.add(Expression.eq("id", passportId));
			crit.add(Expression.eq("status", 1));
			list = crit.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<FamilyRelationDTO> getFamilyMembersList(String sfid) throws Exception {
		List<FamilyRelationDTO> list = null;
		List<Object[]> nameList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select fd.id,fd.name||'-'||frm.name as name from family_details fd,family_relation_master frm where fd.relation_id=frm.id and fd.status=1 and fd.sfid='" + sfid
					+ "' order by frm.name";
			nameList = session.createSQLQuery(sql).list();
			list = new ArrayList<FamilyRelationDTO>();
			for (Object[] object : nameList) {
				FamilyRelationDTO familyRelationDTO = new FamilyRelationDTO();
				familyRelationDTO.setId(Integer.parseInt(object[0].toString()));
				familyRelationDTO.setName(object[1].toString());
				list.add(familyRelationDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public List passportTypeChecking(PassportBean passportBean) throws Exception {
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(PassportBean.class);
			crit.add(Expression.eq("passportType", passportBean.getPassportType()));
			crit.add(Expression.eq("sfid", passportBean.getSfid()));
			crit.add(Expression.eq("status", 1));
			list = crit.list();
			if (list.size() != 0) {
				crit.add(Expression.eq("passPortFor", passportBean.getPassPortFor()));
				list = crit.list();
				if (list.size() != 0) {
					crit.add(Expression.eq("familyMember", passportBean.getFamilyMember()));
					list = crit.list();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public PassportBean getEmployeeDetails(PassportBean passportBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			passportBean.setEmpDetails((EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", passportBean.getSfid())).uniqueResult());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return passportBean;
	}
	@SuppressWarnings("unchecked")
	public List<FamilyBean> getFamilyMemberDetails(PassportBean passportBean) throws Exception {
		List<FamilyBean> list = null;
		Session session = null;
		try {
			String query = "select fd.id id,fd.name name,to_char(fd.dob,'dd-Mon-yyyy')as dob, case when fd.dob  is null then fd.age else round(months_between(sysdate,fd.dob)/12)||'' end age ,frm.name relation from family_details fd,family_relation_master frm where fd.sfid=? and fd.status=1 and fd.ltc_facility='Y' and frm.id=fd.relation_id order by frm.order_no";
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list = session.createSQLQuery(query).addScalar("id",Hibernate.STRING).addScalar("name").addScalar("dob").addScalar("age",Hibernate.STRING).addScalar("relation").setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).setString(0,passportBean.getSfid()).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	public EmployeeBean getEmpDetails(PassportRequestProcessBean passportBean) throws Exception {
		Session session = null;
		EmployeeBean bean=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			bean=(EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", passportBean.getSfID())).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return bean;
	}
	
	public List<PassportApplicationDetailsDTO> checkSFIDRecord(PassportBean passportBean) throws Exception{
		Session session = null;
		List<PassportApplicationDetailsDTO> list=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//list=session.createCriteria(PassportApplicationDetailsDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.between("departureDate", lo, hi)).list();
		String qry="select id as id from PASSPORT_APPLICATION_DETAILS where STATUS=1 and SFID='"+passportBean.getSfID()+"' and (" +
				" (DEPARTURE_DATE between '"+passportBean.getDepartureDate()+"' and '"+passportBean.getReturnDate()+"' ) or " +
				" (RETURN_DATE between '"+passportBean.getDepartureDate()+"' and '"+passportBean.getReturnDate()+"') or " +
				" ('"+passportBean.getDepartureDate()+"' between DEPARTURE_DATE AND RETURN_DATE ) or ('"+passportBean.getReturnDate()+"'" +
				" between DEPARTURE_DATE AND RETURN_DATE) )";
		list=session.createSQLQuery(qry).addScalar("id", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PassportApplicationDetailsDTO.class)).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public String submitPassportApplicationDetails(PassportApplicationDetailsDTO detailsDTO) throws Exception {
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(detailsDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			e.printStackTrace();
			message=CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	
	public String deletePassportApplicationDetails(int id,String bean)throws Exception{
		Session session = null;
		String message = null;
		Transaction tx = null;
		String sql="";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if(CPSUtils.compareStrings(bean, "PassportApplicationDetailsDTO")){
				sql = "update PassportApplicationDetailsDTO set status = 0 where id = ?";
			}
			else if(CPSUtils.compareStrings(bean, "PassportBean"))//modified because wrong dto is assigned
				sql = "update PassportBean set status = 0 where id = ?";
			else if(CPSUtils.compareStrings(bean, "PassportApplicationDTO"))
				sql = "update PassportApplicationDTO set status = 0 where id = ?";
			Query query = session.createQuery(sql);
			query.setInteger(0, id);
			query.executeUpdate();
			session.flush();
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			//tx.rollback();
			message=CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	
	
	
	public String getEmpAddress(String sfid) throws Exception{
		Session session = null;
		String sql="";
		String address="";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sql="SELECT ADM.ADDRESS1||'   '||ADM.ADDRESS2 ||'   '||ADM.CITY||'   '||ST.NAME||'    '||ADM.PINCODE FROM " +
				"ADDRESS_DETAILS_MASTER ADM,STATE_MASTER ST WHERE ADM.STATE=ST.ID AND ADM.SFID='"+sfid+"' and ADM.STATUS=1 and ADM.ADDRESS_TYPE_ID=2";
			address=session.createSQLQuery(sql).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return address;
	}

	public List<PassportApplicationDetailsDTO> getPassportApplicationDetails() throws Exception{
		Session session = null;
		List<PassportApplicationDetailsDTO> list=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list=session.createCriteria(PassportApplicationDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.ID)).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public String submitPassportDetailsforNOC(PassportApplicationDTO applicationDTO)throws Exception{
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(applicationDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			e.printStackTrace();
			message=CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	
	public List<PassportApplicationDTO> getPassportApplicationDetailsForNOC() throws Exception{
		Session session = null;
		String message = null;
		List<PassportApplicationDTO> list=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list=session.createCriteria(PassportApplicationDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.ID)).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	
	public List<PassportApplicationDetailsDTO> getPassportApplicationDetails(String sfid) throws Exception{
		Session session = null;
		String message = null;
		List<PassportApplicationDetailsDTO> list=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list=session.createCriteria(PassportApplicationDetailsDTO.class).add(Expression.eq(CPSConstants.SFID, sfid)).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return list;		
	}
	
	public List<PassportApplicationDetailsDTO> getSFIDForVerifiedType() throws Exception{
		Session session = null;
		String message = null;
		List<PassportApplicationDetailsDTO> list=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list=session.createCriteria(PassportApplicationDetailsDTO.class).add(Expression.eq("verificationType", "Y")).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.ID)).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return list;	
	}

	public String updatePassportApplicationDetails(PassportBean passportBean)throws Exception{
		String message = "";
		PreparedStatement pstmt = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			Connection con = session.connection();
			String sql = "update PASSPORT_APPLICATION_DETAILS pa set pa.HQRS_LETTER_NO=?,pa.HQRS_LETTER_DATE=? where pa.SFID=? and pa.STATUS=1 and pa.VERIFICATION_TYPE='Y'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, passportBean.getHqLetterNo());
			pstmt.setString(2, passportBean.getHqLetterDt());
			pstmt.setString(3, passportBean.getSfidForAbroad());
			pstmt.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.UPDATE;

		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
		
	}
	
	public List<PassportApplicationDetailsDTO> getSFIDHQrsDetails() throws Exception{
		Session session = null;
		List<PassportApplicationDetailsDTO> list=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list=session.createCriteria(PassportApplicationDetailsDTO.class).add(Expression.isNotNull("hqLetterNo")).add(Expression.isNotNull("hqLetterDt")).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.ID)).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return list;	
	}
		
}