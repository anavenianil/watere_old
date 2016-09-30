package com.callippus.web.hrdg.file.dao;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;

public interface IFileDAO {
	
	public String saveFile(UploadAndDownloadBean uploadBean,int refId) throws Exception;

	public List getBrochure(int refId,int refType,HttpServletResponse res,HttpSession session) throws Exception;
	
	public FilesBean downloadFromDatabase(int id) throws Exception;
}
