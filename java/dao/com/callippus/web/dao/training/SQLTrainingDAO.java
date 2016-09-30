package com.callippus.web.dao.training;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.training.TrainingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLTrainingDAO implements ITrainingDAO {

	private static final long serialVersionUID = 7048899710717627065L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String saveTrainingDetails(TrainingBean training) throws Exception {
		Transaction tx = null;
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(training);
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

	public ArrayList getTrainingDetails(TrainingBean training) throws Exception {
		Session session = null;
		ArrayList trainingList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select td.id,td.sfid,td.course,td.subject,td.institute,td.year,ym.name yearName,td.duration,to_char(td.from_date,'DD-Mon-YYYY')from_date,to_char(td.to_date,'DD-Mon-YYYY')to_date,td.training_area from training_details td, year_master ym "
					+ "where td.status=? and ym.id=td.year and  td.sfid=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, "1");
			ps.setString(2, training.getSfid());
			rsq = ps.executeQuery();

			trainingList = new ArrayList();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {
				TrainingBean trainingBean = new TrainingBean();
				LinkedHashMap<String, String> innerjson = new LinkedHashMap();

				innerjson.put("id", rsq.getString("id"));
				innerjson.put("SFID", rsq.getString("sfid"));
				innerjson.put("Course", rsq.getString("course"));
				innerjson.put("Subject", rsq.getString("subject"));
				if (rsq.getString("training_area").equals("0"))
					innerjson.put("Training Area", "");
				else
					innerjson.put("Training Area", rsq.getString("training_area"));
				innerjson.put("Organization", rsq.getString("institute"));
				// innerjson.put("Year",rsq.getString("year"));
				innerjson.put("Year of Completion", rsq.getString("yearName"));
				innerjson.put("Duration", rsq.getString("duration"));
				innerjson.put("From Date", rsq.getString("from_date"));
				innerjson.put("To Date", rsq.getString("to_date"));

				trainingBean.setId(rsq.getString("id"));
				trainingBean.setSfid(rsq.getString("sfid"));
				trainingBean.setCourse(rsq.getString("course"));
				trainingBean.setSubject(rsq.getString("subject"));
				trainingBean.setInstitute(rsq.getString("institute"));
				trainingBean.setYear(rsq.getString("year"));
				trainingBean.setDuration(rsq.getString("duration"));
				trainingBean.setFromDate(rsq.getString("from_date"));
				trainingBean.setToDate(rsq.getString("to_date"));
				if (rsq.getString("training_area").equals("0"))
					trainingBean.setArea("");
				else
					trainingBean.setArea(rsq.getString("training_area"));

				trainingList.add(trainingBean);
				outerjson.put(String.valueOf(i), innerjson);
				i++;

			}
			outerjson.put(String.valueOf(i), CPSConstants.TRAINING);
			trainingList.add(outerjson);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return trainingList;
	}

	public String deleteTrainingDetails(TrainingBean training) throws Exception {
		PreparedStatement pstmt = null;
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Connection con = session.connection();
			String sql = "update training_details set status=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "0");
			pstmt.setString(2, training.getId());
			pstmt.executeUpdate();
			message = CPSConstants.DELETED;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return message;
	}
}