package com.callippus.web.hrdg.training.controller.file;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Service
public class HRDGFileUpload {
	private static Log log = LogFactory.getLog(HRDGFileUpload.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String uploadFile(MultipartFile file, String fileName, String extension, String destPath, String change) throws Exception {
		log.debug("FileUpload:::::::uploadFile >>>>>>>>>method starts");
		InputStream stream = null;
		OutputStream bos = null;
		try {
			log.debug("fileName : " + fileName);
			log.debug("destPath : " + destPath);

//			if (CPSUtils.compareStrings(change, CPSConstants.CHANGE)) {
//				fileName = fileName + "_" + getRequestID() + "." + extension;
//			}
			fileName = fileName + "." + extension;;
			stream = file.getInputStream();
			bos = new FileOutputStream(destPath + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			log.debug("FileUpload:::::::uploadFile >>>>>>>>>method end. Uploaded FileName > " + fileName);
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} finally {
			bos.close();
			stream.close();
		}
		return fileName;
	}

	public String getRequestID() throws Exception {
		Session reqSession = null;
		String requestID = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rsq = null;
		try {
			reqSession = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = reqSession.connection();

			String sql = "select value from id_generator where table_name='REQUEST' FOR UPDATE";
			stmt = con.createStatement();
			rsq = stmt.executeQuery(sql);
			if (rsq.next()) {
				requestID = String.valueOf(Integer.valueOf(rsq.getString("value")) + 1);
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} finally {
			ConnectionUtil.closeConnection(reqSession, stmt, rsq);
		}
		return requestID;
	}
}
