package com.callippus.web.beans.file;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadBean {

	private Map<String, MultipartFile> files;
	private Map<String, Integer> fileIds;
	private Map<String, String> fileNames;
	
	private Map<String, Integer> fileIDList;

	public Map<String, String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Map<String, String> fileNames) {
		this.fileNames = fileNames;
	}

	public void setFiles(Map<String, MultipartFile> files) {
		this.files = files;
	}

	public Map<String, MultipartFile> getFiles() {
		return files;
	}

	public void setFileIds(Map<String, Integer> fileIds) {
		this.fileIds = fileIds;
	}

	public Map<String, Integer> getFileIds() {
		return fileIds;
	}

	public Map<String, Integer> getFileIDList() {
		return fileIDList;
	}

	public void setFileIDList(Map<String, Integer> fileIDList) {
		this.fileIDList = fileIDList;
	}
	

}
