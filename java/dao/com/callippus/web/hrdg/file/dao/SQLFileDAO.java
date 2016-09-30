package com.callippus.web.hrdg.file.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;

@Service
public class SQLFileDAO implements IFileDAO{
	private static Log log = LogFactory.getLog(SQLFileDAO.class);
	private static final long serialVersionUID = 5611041426238862266L; 

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	

	@SuppressWarnings("unchecked")
	
	public String saveFile(UploadAndDownloadBean uploadBean,int refId) throws Exception
	{
		log.debug("save() --> Start");
		String message = null;
		Session session = null;
		Connection con = null;
		PreparedStatement preparedSt = null; 
		Transaction tx = null;
		try {
			
			
			
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			con = session.connection();
			List list = session.createCriteria(UploadAndDownloadBean.class).list();  
			UploadAndDownloadBean uploadBean1 = new UploadAndDownloadBean();
			BeanUtils.copyProperties(uploadBean1, uploadBean);
			uploadBean1.setStatus(1);
			session.save(uploadBean1);
			session.flush();//tx.commit() ;
			
			FileInputStream fi = new FileInputStream(uploadBean.getName());
			
			preparedSt = con.prepareStatement("update hrdg_file_master set content = ? where ref_id=? and ref_type=?"); 
			preparedSt.setBinaryStream(1, fi, fi.available());
			
			preparedSt.setInt(2, Integer.parseInt(uploadBean1.getRefId().toString()));
			preparedSt.setInt(3, Integer.parseInt(uploadBean1.getRefType().toString()));
			
			
			preparedSt.executeUpdate();
			session.flush() ; //con.commit();
//			FileInputStream fs=new FileInputStream("C:/Documents and Settings/All Users/one.docx");
//			preparedSt = con.prepareStatement("update hrdg_file_master set content = ? where ref_id=? and ref_type=?"); 
//			preparedSt.setBinaryStream(1, fs, fs.available());
//			
//			
//			preparedSt.setInt(2, Integer.parseInt(uploadBean1.getRefId().toString()));
//			preparedSt.setInt(3, Integer.parseInt(uploadBean1.getRefType().toString()));
//			preparedSt.executeUpdate();
//			session.flush() ; //con.commit();
			message = CPSConstants.SUCCESS;
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
			preparedSt.close();
			con.close();
			//session.close();
		}
		log.debug("save() --> End");
		return message;
	}
	public List getBrochure(int refId,int refType,HttpServletResponse response,HttpSession session1) throws Exception
	 {
		 Session session = null;
			Query qry = null;
			String sb = null;
			List list = null;
			Connection con = null;
			PreparedStatement preparedSt = null; 
			ResultSet rs = null;
			try {
				session = hibernateUtils.getSession();
				con = session.connection();
				preparedSt = con.prepareStatement("select content from hrdg_file_master where ref_id=? and ref_type=?"); 
								
				preparedSt.setInt(1, refId);
				preparedSt.setInt(2, refType);
				
				
				rs = preparedSt.executeQuery();
				if(rs.next()){
					response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
					response.addHeader("Pragma", "no-cache");
					response.setHeader("Content-Disposition", "inline; filename=\"one.docx\"");
					File f = new File(session1.getAttribute("filepath")+"one.docx");
					OutputStream out = response.getOutputStream();
					if (true) {
						//BufferedInputStream in = new BufferedInputStream(session.getServletContext().getResourceAsStream(session.getAttribute("filepath")+"one.docx"));
						
						BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(1));
						
						int length;
						byte[] buf = new byte[1024];
						while ((in != null) && ((length = in.read(buf)) != -1)) {
							out.write(buf, 0, length);
						}
					} else
						out.write(0);

					out.close();
				
				}
				
					    			
				
			} catch (Exception e) {
				
				throw e;
			} finally {
				
			}
			log.debug("getTrainingCirculationDetails() --> End");
			return list;
	 }
	public FilesBean downloadFromDatabase(int i) throws Exception {
		Session session = null;
		String query = "select * from files where id =" + i;
		try {
			
			session = hibernateUtils.getSession();
			ResultSet rs = session.connection().createStatement().executeQuery(query);
			FilesBean file = null;
			if(rs.next()) {
				file = new FilesBean();
				file.setId(rs.getInt(1));
				file.setFilename(rs.getString(2));
				file.setDesc(rs.getString(3));
				file.setType(rs.getString(4));
				file.setFile(rs.getBytes(5));
			}
			return file;
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
	}

}
