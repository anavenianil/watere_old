package com.callippus.web.controller.common;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Service
public class ConnectionUtil {

	public static void closeConnection(Session session, Statement stmt, ResultSet rsq) throws Exception {
		if (session != null) {
			try {
				//session.close();
			} catch (Exception e) {
				throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
			} finally {
				//session = null;
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqle) {
				throw new ResultStatus(SetErpException.setException(sqle).getResultStatus().getErrorCode());
			} catch (Exception e) {
				throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
			} finally {
				stmt = null;
			}
		}
		if (rsq != null) {
			try {
				rsq.close();
			} catch (SQLException sqle) {
				throw new ResultStatus(SetErpException.setException(sqle).getResultStatus().getErrorCode());
			} catch (Exception e) {
				throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
			} finally {
				rsq = null;
			}
		}
	}

	public Properties loadProps() throws Exception {
		Properties props = null;
		try {
			props = new Properties();
			InputStream fis = getClass().getResourceAsStream("/cps.properties");
			props.load(fis);
			fis.close();
		} catch (Exception e) {
			throw e;
		}
		return props;
	}
}