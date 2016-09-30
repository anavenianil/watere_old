package com.callippus.web.dao.award;

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
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.award.AwardDetails;
import com.callippus.web.beans.dto.AwardCategoryDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLAwardDAO implements IAwardDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLAwardDAO.class);
	private static final long serialVersionUID = 7048899710717627065L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public ArrayList getAwardHomeDetails(AwardDetails awardBean) throws Exception {
		log.debug("getAwardHomeDetails ---> method start");
		ArrayList list = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select awd.id id1,awd.awarded_year_id year1,awd.organization organization1,awd.details_of_work details_of_work1,awd.awarded_cash cash1,awd.medallion medallion1,"
					+ "awd.citation citation1,awd.certificate certificate1,awd.award_category_id award_category1,awd.award_description award_description1,awd.remarks remarks1,"
					+ "ym.name yearname,awc.name categoryname from award_details awd,year_master ym,award_category_master awc where awd.sfid=? and awd.status=1 "
					+ "and ym.id=awd.awarded_year_id and awc.id=awd.award_category_id";
			log.debug("sql query --->" + sql);
			ps = con.prepareStatement(sql);
			ps.setString(1, awardBean.getSfid());
			rsq = ps.executeQuery();

			list = new ArrayList();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {
				AwardDetails awdBean = new AwardDetails();
				awdBean.setId(rsq.getString("id1"));
				awdBean.setYear(rsq.getString("year1"));
				awdBean.setOrganization(rsq.getString("organization1"));
				awdBean.setDetailsOfWork(rsq.getString("details_of_work1"));
				awdBean.setCash(rsq.getString("cash1"));
				awdBean.setMedallion(rsq.getString("medallion1"));
				awdBean.setCitation(rsq.getString("citation1"));
				awdBean.setCertificate(rsq.getString("certificate1"));
				awdBean.setAwardCategory(rsq.getString("award_category1"));
				awdBean.setAwardDescription(rsq.getString("award_description1"));
				awdBean.setRemarks(rsq.getString("remarks1"));
				awdBean.setYearName(rsq.getString("yearname"));
				awdBean.setCategoryName(rsq.getString("categoryname"));
				list.add(awdBean);

				LinkedHashMap innerjson = new LinkedHashMap();
				innerjson.put("id", rsq.getString("id1"));
				innerjson.put("Award Category", rsq.getString("categoryname"));
				innerjson.put("Organization", rsq.getString("organization1"));
				innerjson.put("Year", rsq.getString("yearname"));
				innerjson.put("Cash", rsq.getString("cash1"));
				innerjson.put("Medallion", rsq.getString("medallion1"));
				innerjson.put("Citation", rsq.getString("citation1"));
				innerjson.put("Cerificate", rsq.getString("certificate1"));
				innerjson.put("Details of Work", rsq.getString("details_of_work1"));
				innerjson.put("Award Description", rsq.getString("award_description1"));
				innerjson.put("Remarks", rsq.getString("remarks1"));
				outerjson.put(String.valueOf(i), innerjson);
				i++;
			}
			outerjson.put(String.valueOf(i), CPSConstants.AWARD);
			list.add(outerjson);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		log.debug("getAwardHomeDetails ---> method start");
		return list;
	}

	public String manageAwardDetails(AwardDetails awardBean) throws Exception {
		log.debug("manageAwardDetails ---> method start");
		Session session = null;
		Transaction tx = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			awardBean.setStatus(1);
			session.saveOrUpdate(awardBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		log.debug("message --->" + message);
		log.debug("manageAwardDetails ---> method end");
		return message;
	}

	public String deleteAwardDetails(AwardDetails awardBean) throws Exception {
		log.debug("deleteAwardDetails ---> method start");
		Session session = null;
		Transaction tx = null;
		String message = null;
		PreparedStatement pstmt = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			Connection con = session.connection();
			String sql = "update award_details set status=0,last_modified_date=sysdate where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, awardBean.getId());
			pstmt.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		log.debug("message --->" + message);
		log.debug("deleteAwardDetails ---> method end");
		return message;
	}

	public List<AwardCategoryDTO> getAwardCategoryList() throws Exception {
		log.debug("getAwardCategoryList ---> method start");
		List<AwardCategoryDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crt = session.createCriteria(AwardCategoryDTO.class);
			crt.add(Expression.eq("status", 1));
			list = crt.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getAwardCategoryList ---> method end");
		return list;
	}

	public String updateAwardDetails(AwardDetails awardBean) throws Exception {
		log.debug("updateAwardDetails ---> method start");
		Session session = null;
		Transaction tx = null;
		PreparedStatement pst = null;
		Connection con = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			con = session.connection();
			String sql = "UPDATE AWARD_DETAILS SET AWARD_CATEGORY_ID=?,ORGANIZATION=?,AWARDED_YEAR_ID=?,AWARDED_CASH=?,"
					+ "MEDALLION=?,CITATION=?,CERTIFICATE=?,DETAILS_OF_WORK=?,AWARD_DESCRIPTION=?,REMARKS=?,LAST_MODIFIED_DATE=SYSDATE " + "WHERE ID=? AND STATUS=1";
			log.debug("sql query --->" + sql);
			pst = con.prepareStatement(sql);
			pst.setString(1, awardBean.getAwardCategory());
			pst.setString(2, awardBean.getOrganization());
			pst.setString(3, awardBean.getYear());
			pst.setString(4, awardBean.getCash());
			pst.setString(5, awardBean.getMedallion());
			pst.setString(6, awardBean.getCitation());
			pst.setString(7, awardBean.getCertificate());
			pst.setString(8, awardBean.getDetailsOfWork());
			pst.setString(9, awardBean.getAwardDescription());
			pst.setString(10, awardBean.getRemarks());
			pst.setString(11, awardBean.getId());
			pst.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pst, null);
		}
		log.debug("updateAwardDetails ---> method end");
		return message;
	}
}