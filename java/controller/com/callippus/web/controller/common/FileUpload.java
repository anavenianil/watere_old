package com.callippus.web.controller.common;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Service
public class FileUpload {
	private static Log log = LogFactory.getLog(FileUpload.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Autowired
	private TxRequestProcess txRequestProcess;

	public String uploadFile(MultipartFile file, String fileName, String extension, String destPath, String change) throws Exception {
		log.debug("FileUpload:::::::uploadFile >>>>>>>>>method starts");
		InputStream stream = null;
		OutputStream bos = null;
		String RequestId = null;
		try {
			log.debug("fileName : " + fileName);
			log.debug("destPath : " + destPath);
			destPath=destPath+"/";
          if(CPSUtils.compareStrings(change, CPSConstants.CHANGE)) {
				fileName = fileName + "_" + getRequestID() + "." + extension;
			}

			stream = file.getInputStream();
			bos = new FileOutputStream(destPath + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			log.debug("FileUpload:::::::uploadFile >>>>>>>>>method end. Uploaded FileName > " + fileName);
		} catch (Exception e) {
			log.debug("FileUpload:::::::uploadFile >>>>>>>>>Exception");
			log.debug(SetErpException.setException(e).getResultStatus().getErrorCode());
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} finally {
			bos.close();
			stream.close();
		}
		return fileName;
	}
	public String uploadCghsFile(MultipartFile file, String fileName, String extension, String destPath, String change,String requestId) throws Exception {
		log.debug("FileUpload:::::::uploadFile >>>>>>>>>method starts");
		InputStream stream = null;
		OutputStream bos = null;
		String RequestId = null;
		try {
			log.debug("fileName : " + fileName);
			log.debug("destPath : " + destPath);
            if(CPSUtils.isNullOrEmpty(requestId)){
            	if(CPSUtils.compareStrings(change, CPSConstants.CHANGE)) {
    				fileName = fileName + "_" + getRequestID() + "." + extension;
    			}	
            }
            else{
          if(CPSUtils.compareStrings(change, CPSConstants.CHANGE)) {
        	  fileName = fileName + "_" + requestId + "." + extension;
			}
            }
			stream = file.getInputStream();
			bos = new FileOutputStream(destPath + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			log.debug("FileUpload:::::::uploadFile >>>>>>>>>method end. Uploaded FileName > " + fileName);
		} catch (Exception e) {
			log.debug("FileUpload:::::::uploadFile >>>>>>>>>Exception");
			log.debug(SetErpException.setException(e).getResultStatus().getErrorCode());
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		} finally {
			bos.close();
			stream.close();
		}
		return fileName;
	}


	@SuppressWarnings("deprecation")
	public FileUploadBean uploadFileToDatabase(FileUploadBean fub) throws Exception {
		Session session = null;
		String id = null;
		String query = null;
		try {
			log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>start");
			Map<String, Integer> map =new HashMap<String, Integer>();
			Map<String, String> fileNames =new HashMap<String, String>();
			Iterator<String> itr = fub.getFiles().keySet().iterator();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				MultipartFile multipartFile = fub.getFiles().get(key);
			
                	
				if(fub.getFileIDList() == null || (fub.getFileIDList() !=null && fub.getFileIDList().size()==0)){
					id = txRequestProcess.generateUniqueID(CPSConstants.FILES);
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>after id generated for file");
					query = "insert into files (id,filename, notes, type, file1) values (" + id + ",?,?, ?, ?)";
				}else if(fub.getFileIDList().size()!=0  && !(fub.getFileIDList().containsKey(key))){
					  
					id = txRequestProcess.generateUniqueID(CPSConstants.FILES);
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>after id generated for file");
					query = "insert into files (id,filename, notes, type, file1) values (" + id + ",?,?, ?, ?)";
				}else{
					 id = fub.getFileIDList().get(key).toString();
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>before update");
					query = "update files set filename=? ,notes=? ,type=?, file1=? where id="+id;
				}
				
				synchronized (this) {
					session = hibernateUtils.getSession();
					PreparedStatement statement = session.connection().prepareStatement(query);
					statement.setString(1, multipartFile.getOriginalFilename());
					statement.setString(2, key);
					if(!CPSUtils.isNullOrEmpty(key) && key.contains("ionFile#") && key.split("ionFile#").length >1)
						{   if(!CPSUtils.isNullOrEmpty(key.split("ionFile#")[1])){
							statement.setString(1, key.split("ionFile#")[1]);
							statement.setString(2, multipartFile.getOriginalFilename());
						}}
					
					statement.setString(3, multipartFile.getContentType());
					statement.setBytes(4, multipartFile.getBytes());
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>before insert file");
					statement.executeUpdate();
					session.flush();
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>after insert file");
				}	
				map.put(key, Integer.valueOf(id));
				fileNames.put(key, multipartFile.getOriginalFilename());
			}
			fub.setFileIds(map);
			fub.setFileNames(fileNames);
			log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>insert completed");
			return fub;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>Exception");
			log.debug(SetErpException.setException(e).getResultStatus().getErrorCode());
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
	}

	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("deprecation")
	public FilesBean downloadFromDatabase(int i) throws Exception {
		Session session = null;
		String query = "select * from files where id =" + i;
		try {
			session = hibernateUtils.getSession();
			ResultSet rs = session.connection().createStatement().executeQuery(query);
			FilesBean file = null;
			while (rs.next()) {
				file = new FilesBean();
				file.setId(rs.getInt(1));
				file.setFilename(rs.getString(2));
				file.setDesc(rs.getString(3));
				file.setType(rs.getString(4));
				file.setFile(rs.getBytes(5));
			}
			return file;
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
	}
	@SuppressWarnings("deprecation")
	public String deleteFileFromDatabase(int fileId) throws Exception {
		Session session = null;
		String id = null;
		String query = null;
		String message = null;
		try {
			log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>start");
			
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>before delete");
					query = "delete from files where id="+fileId;
				
				synchronized (this) {
					session = hibernateUtils.getSession();
					PreparedStatement statement = session.connection().prepareStatement(query);
					
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>before delete file");
					statement.executeUpdate();
					session.flush();
					message = CPSConstants.SUCCESS;
					log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>after delete file");
				}	
				
			
			log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>delete completed");
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			e.printStackTrace();
			log.debug("FileUpload:::::::uploadFileToDatabase >>>>>>>>>Exception");
			log.debug(SetErpException.setException(e).getResultStatus().getErrorCode());
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
			
		}
		return message;
	}
}
