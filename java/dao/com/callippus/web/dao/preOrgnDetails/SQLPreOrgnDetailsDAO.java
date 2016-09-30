package com.callippus.web.dao.preOrgnDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.preOrgnDetails.PreOrgnDetailsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class SQLPreOrgnDetailsDAO implements IPreOrgnDetailsDAO {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public ArrayList getPreOrgnDetails(PreOrgnDetailsBean preOrgn) throws Exception {
		ArrayList<Object> orgnList = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select id,org_type,org_name,to_char(from_date,'DD-Mon-YYYY')from_date,to_char(to_date,'DD-Mon-YYYY')to_date,division,rank_held,job_description,skills,remarks,pay,scale_of_pay from pre_orgn_details where sfid=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, preOrgn.getSfid());
			rsq = ps.executeQuery();

			orgnList = new ArrayList<Object>();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {
				LinkedHashMap<Object, Object> innerjson = new LinkedHashMap<Object, Object>();

				PreOrgnDetailsBean preOrgBean = new PreOrgnDetailsBean();
				preOrgBean.setId(rsq.getString("id"));
				preOrgBean.setFromDate(rsq.getString("from_date"));
				preOrgBean.setToDate(rsq.getString("to_date"));
				preOrgBean.setOrgTypeId(rsq.getString("org_type"));
				if (rsq.getString("org_type").equals("20"))
					preOrgBean.setOrgType("Private");
				else if (rsq.getString("org_type").equals("21"))
					preOrgBean.setOrgType("Government");
				preOrgBean.setOrgName(rsq.getString("org_name"));
				preOrgBean.setDivisionName(rsq.getString("division"));
				if (!CPSUtils.isNull(rsq.getString("job_description")))
					preOrgBean.setJobDescription(rsq.getString("job_description"));
				else
					preOrgBean.setJobDescription("");
				preOrgBean.setRankHeld(rsq.getString("rank_held"));
				preOrgBean.setSkills(rsq.getString("skills"));
				preOrgBean.setRemarks(rsq.getString("remarks"));
				preOrgBean.setPay(rsq.getString("pay"));
				preOrgBean.setScaleOfPay(rsq.getString("scale_of_pay"));
				orgnList.add(preOrgBean);

				innerjson.put("id", rsq.getString("id"));
				innerjson.put("From Date", rsq.getString("from_date"));
				innerjson.put("To Date", rsq.getString("to_date"));

				innerjson.put("Organisation Type", preOrgBean.getOrgType());
				innerjson.put("Organisation Name", rsq.getString("org_name"));
				innerjson.put("Division", rsq.getString("division"));
				innerjson.put("Job Description", rsq.getString("job_description"));

				innerjson.put("Rank Held", rsq.getString("rank_held"));
				innerjson.put("Skills", rsq.getString("skills"));
				innerjson.put("Remarks", rsq.getString("remarks"));
				innerjson.put("Pay", rsq.getString("pay"));
				innerjson.put("Scale Of Pay", rsq.getString("scale_of_pay"));
				outerjson.put(String.valueOf(i), innerjson);
				i++;

			}
			outerjson.put(String.valueOf(i), CPSConstants.PREORGDETAILS);
			orgnList.add(outerjson);

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		return orgnList;
	}

	public String createPreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//Transaction tx = session.beginTransaction();
			session.save(preOrgnBean);
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

	public String deletePreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//Transaction tx = session.beginTransaction();
			session.delete(preOrgnBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.DELETE;

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String updatePreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		String message = "";
		Session session = null;
		try {
			// update pre orgn details table
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//Transaction tx = session.beginTransaction();
			session.update(preOrgnBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.UPDATE;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public List editOrganisation(PreOrgnDetailsBean preOrgnBean) throws Exception {
		Session session = null;
		List orgnList = null;
		try {
			// get the pre orgn details of sfid
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(PreOrgnDetailsBean.class);
			crit.add(Expression.eq("sfid", preOrgnBean.getSfid()));
			crit.add(Expression.eq("id", preOrgnBean.getId()));
			orgnList = crit.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return orgnList;
	}
}