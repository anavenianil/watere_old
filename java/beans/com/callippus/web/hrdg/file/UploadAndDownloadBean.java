package com.callippus.web.hrdg.file;


import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.dto.CommonDTO;

public class UploadAndDownloadBean extends CommonDTO{
	
	
	
	private String file;
	private String param;
	private String type;
	private MultipartFile fileContent;
	private String originalFilename;
	private String contentType;
	private Integer refId;
	private Integer refType;
	
	
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MultipartFile getFileContent() {
		return fileContent;
	}
	public void setFileContent(MultipartFile fileContent) {
		this.fileContent = fileContent;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Integer getRefId() {
		return refId;
	}
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	public Integer getRefType() {
		return refType;
	}
	public void setRefType(Integer refType) {
		this.refType = refType;
	}
	
	


}
