package com.callippus.web.business.domainobject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.ConnectionUtil;

@Service
public class DomainObject {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	

	public int getTableID(String tableName) throws Exception {
		List masterObjList = null;
		Session session = null;
		int id = 0;
		PreparedStatement pstmt = null;
		Transaction tx = null;
		Connection con = null;
		try {
//VN		session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			session = hibernateUtils.getSession();
			con = session.connection();

			////tx = session.beginTransaction();
			masterObjList = session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE").setString(0, tableName).list();

			tx = session.beginTransaction();
			masterObjList = session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE").setString(0, tableName).list();

			id = Integer.parseInt(masterObjList.get(0).toString()) + 1;

			String sql = "update id_generator set value=? where table_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, tableName);
			pstmt.executeUpdate();
			session.flush();
			//session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//ConnectionUtil.closeConnection(session, pstmt, null);
			ConnectionUtil.closeConnection(null, pstmt, null);
		}
		return id;
	}
}