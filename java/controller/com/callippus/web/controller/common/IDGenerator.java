package com.callippus.web.controller.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@SuppressWarnings("serial")
@Service
public class IDGenerator implements Serializable {
	private static Log log = LogFactory.getLog("com.callippus.web.controller.common.idgenerator.IDGenerator");
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	synchronized public int getId(String table_name) throws Exception {
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		int intId = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			synchronized (table_name) {
				ps = con.prepareStatement("select id_value from id_generator where table_name=? FOR UPDATE");
				ps.setString(1, table_name);

				rsq = ps.executeQuery();
				if (rsq.next()) {
					intId = rsq.getInt(1) + 1;
					ps = con.prepareStatement("update id_generate set id_value=? where table_name=?");
					ps.setInt(1, intId);
					ps.setString(2, table_name);
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		log.debug("::generated id is:::" + intId + "::for table:::" + table_name);
		return intId;
	}
}