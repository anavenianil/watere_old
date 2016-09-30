package com.callippus.web.dao.qualification;

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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DegreeDTO;
import com.callippus.web.beans.qualification.QualificationBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.dao.address.SQLAddressDAO;

@Service
public class SQLQualificationDAO implements IQualificationDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLAddressDAO.class);
	private static final long serialVersionUID = -4710823014550415449L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public ArrayList getEmpQualificationDetails(QualificationBean qualification) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		ArrayList list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select qd.id id,qd.sfid sfid,qd.qualification_id qual,qd.discipline_id discipline,qd.specialisation specialisation,qd.year year,"
					+ "qd.university uni,qd.division division,qd.sponsored spons,qd.qual_attained_drdo qadrdo,qd.re_organised organised,qd.incentive insentive,"
					+ "to_char(qd.creation_date,'DD-Mon-YYYY') creationdate,qd.degree deg,qd.type_value tpercen,qd.type marks,qm.name qmname,dm.name dmname,ym.name ymname,qd.grade grade,dms.name degName "
					+ "from degree_master dms,qualification_details qd,qualification_master qm,discipline_master dm,year_master ym where "
					+ "qm.id=qd.qualification_id and dm.id=qd.discipline_id and ym.id=qd.year and qd.status=1 and dms.id=qd.degree and qd.sfid=? order by qd.qualification_id";
			ps = con.prepareStatement(sql);
			ps.setString(1, qualification.getSfid());
			rsq = ps.executeQuery();
			list = new ArrayList();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {
				QualificationBean qualBean = new QualificationBean();
				qualBean.setId(rsq.getString("id"));
				qualBean.setSfid(rsq.getString("sfid"));
				qualBean.setQualification(rsq.getString("qual"));
				qualBean.setDiscipline(rsq.getString("discipline"));
				qualBean.setSpecilisation(rsq.getString("specialisation"));
				qualBean.setYear(rsq.getString("year"));
				qualBean.setUniversity(rsq.getString("uni"));
				qualBean.setDivisionId(rsq.getString("division"));
				qualBean.setSponsored(rsq.getString("spons"));
				qualBean.setQualAttainedDRDO(rsq.getString("qadrdo"));
				qualBean.setReOraganised(rsq.getString("organised"));
				qualBean.setIncentive(rsq.getString("insentive"));
				qualBean.setCreationDate(rsq.getString("creationdate"));
				qualBean.setDegree(rsq.getString("deg"));
				qualBean.setTotalPercentage(rsq.getString("tpercen"));
				qualBean.setQualiMasterName(rsq.getString("qmname"));
				qualBean.setDiciplineMasterName(rsq.getString("dmname"));
				qualBean.setYearMasterName(rsq.getString("ymname"));
				qualBean.setGrade(rsq.getString("grade"));
				qualBean.setMarks(rsq.getString("marks"));
				list.add(qualBean);

				LinkedHashMap innerjson = new LinkedHashMap();
				innerjson.put("id", rsq.getString("id"));
				innerjson.put("SFID", rsq.getString("sfid"));
				innerjson.put("Degree", rsq.getString("degName"));
				innerjson.put("Qualification", rsq.getString("qmname"));
				innerjson.put("Discipline", rsq.getString("dmname"));
				innerjson.put("Specialization", rsq.getString("specialisation"));
				innerjson.put("University", rsq.getString("uni"));
				innerjson.put("Year of Passing", rsq.getString("ymname"));
				// innerjson.put("Total Marks",rsq.getString("marks"));
				innerjson.put("CGPS/Percentage/Marks", rsq.getString("tpercen"));
				innerjson.put("Division", rsq.getString("division"));
				innerjson.put("Grade", rsq.getString("grade"));
				innerjson.put("Qualification Attained While DRDO", rsq.getString("qadrdo"));
				innerjson.put("Incentive", rsq.getString("insentive"));
				outerjson.put(String.valueOf(i), innerjson);
				i++;
			}
			outerjson.put(String.valueOf(i), CPSConstants.QUALIFICATION);
			list.add(outerjson);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return list;
	}

	public String manageQualification(QualificationBean qualificationBean) throws Exception {
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(qualificationBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String deleteQualification(String id) throws Exception {
		PreparedStatement pstmt = null;
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			Connection con = session.connection();
			String sql = "update qualification_details set status=0 where id=?";
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

	public List getDescipline() throws Exception {
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "from DisciplineDTO as dm where dm.status=1 order by dm.name";
			Query query = session.createQuery(sql);
			list = query.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public List getQualification() throws Exception {
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "from QualificationDTO as qm where qm.status=1 order by qm.name";
			Query query = session.createQuery(sql);
			list = query.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public List checkQualification(QualificationBean qualificationBean) throws Exception {
		List list = null;
		Session session = null;
		String qry = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			qry = "select qb.qualification from QualificationBean as qb where status=1 and qb.sfid='" + qualificationBean.getSfid() + "' and qb.qualification=" + qualificationBean.getQualification();
			Query query = session.createQuery(qry);
			list = query.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public List<DegreeDTO> getDegreeList() throws Exception {
		List<DegreeDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(DegreeDTO.class);
			crit.add(Expression.eq("status", 1));
			crit.addOrder(Order.asc("name"));
			list = crit.list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;

	}

	public String updateQualification(QualificationBean qualificationBean) throws Exception {
		log.debug("updateQualification ---> method start");
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "UPDATE QUALIFICATION_DETAILS SET DEGREE=?,QUALIFICATION_ID=?,DISCIPLINE_ID=?,SPECIALISATION=?,"
					+ "UNIVERSITY=?,YEAR=?,type_value=?,DIVISION=?,GRADE=?,QUAL_ATTAINED_DRDO=?,SPONSORED=?,INCENTIVE=?,type=?,LAST_MODIFIED_DATE=SYSDATE " + "WHERE ID=? AND STATUS=1";
			log.debug("sql ---> " + sql);
			ps = con.prepareStatement(sql);
			ps.setString(1, qualificationBean.getDegree());
			ps.setString(2, qualificationBean.getQualification());
			ps.setString(3, qualificationBean.getDiscipline());
			ps.setString(4, qualificationBean.getSpecilisation());
			ps.setString(5, qualificationBean.getUniversity());
			ps.setString(6, qualificationBean.getYear());
			ps.setString(7, qualificationBean.getTotalPercentage());
			ps.setString(8, qualificationBean.getDivisionId());
			ps.setString(9, qualificationBean.getGrade());
			ps.setString(10, qualificationBean.getQualAttainedDRDO());
			ps.setString(11, qualificationBean.getSponsored());
			ps.setString(12, qualificationBean.getIncentive());
			ps.setString(13, qualificationBean.getMarks());
			ps.setString(14, qualificationBean.getId());
			ps.executeQuery();
			message = CPSConstants.UPDATE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
		}
		log.debug("message ---> " + message);
		log.debug("updateQualification ---> method end");
		return message;
	}
}