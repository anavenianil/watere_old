package com.callippus.web.beans.file;

import org.springframework.web.multipart.MultipartFile;


public class UploadAndDownloadBean {
	
	private String file;
	private String param;
	private String type;
	private MultipartFile uploadFile;
	private String desc;
	private int downloadId;
	private FileUploadBean fileUploadBean;
	private String requestId;
		
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
	public void setDownloadId(int downloadId) {
		this.downloadId = downloadId;
	}
	public int getDownloadId() {
		return downloadId;
	}
	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}
	public FileUploadBean getFileUploadBean() {
		return fileUploadBean;
	}
 
	

	
	
	

}
