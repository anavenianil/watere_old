package com.callippus.web.dao.empExperience;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.empExperience.EmpExperienceBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;


@Service
public class SQLEmpExperienceDAO implements IEmpExperienceDAO {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String createEmpExperience(EmpExperienceBean empExpBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			// get the family details of sfid from family_details
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//Transaction tx = session.beginTransaction();
			session.save(empExpBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String deleteEmpExperience(EmpExperienceBean empExpBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			// insert into family details table
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//Transaction tx = session.beginTransaction();
			session.delete(empExpBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.DELETE;

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String updateEmpExperience(EmpExperienceBean empExpBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			// insert into family details table
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//Transaction tx = session.beginTransaction();
			session.update(empExpBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.UPDATE;

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	@SuppressWarnings("unchecked")
	public ArrayList getEmpExperienceDetails(String sfid) throws Exception {
		ArrayList experienceList = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select id,experience,to_char(from_date,'DD-Mon-YYYY')from_date,to_char(to_date,'DD-Mon-YYYY')to_date,description from emp_experience where sfid=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, sfid);
			rsq = ps.executeQuery();

			experienceList = new ArrayList();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {
				LinkedHashMap innerjson = new LinkedHashMap();
				EmpExperienceBean empExpBean = new EmpExperienceBean();
				empExpBean.setId(rsq.getString("id"));
				empExpBean.setExperience(rsq.getString("experience"));
				empExpBean.setFromDate(rsq.getString("from_date"));
				empExpBean.setToDate(rsq.getString("to_date"));
				if (!CPSUtils.isNull(rsq.getString("description")))
					empExpBean.setDescription(rsq.getString("description"));
				else
					empExpBean.setDescription("");
				experienceList.add(empExpBean);

				innerjson.put("id", rsq.getString("id"));
				innerjson.put("Employee Experience", rsq.getString("experience"));
				innerjson.put("From Date", rsq.getString("from_date"));
				innerjson.put("To Date", rsq.getString("to_date"));
				innerjson.put("From Date", rsq.getString("from_date"));
				innerjson.put("Description", rsq.getString("description"));
				outerjson.put(String.valueOf(i), innerjson);
				i++;
			}
			outerjson.put(String.valueOf(i), CPSConstants.EMPEXPERIENCE);
			experienceList.add(outerjson);

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return experienceList;

	}

	public List getExperience(EmpExperienceBean empExpBean) throws Exception {
		List experienceList = null;
		Session session = null;
		try {
			// get the family details of sfid from family_details
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(EmpExperienceBean.class);
			crit.add(Expression.eq("sfid", empExpBean.getSfid()));
			crit.add(Expression.eq("id", empExpBean.getId()));
			experienceList = crit.list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return experienceList;
	}
}