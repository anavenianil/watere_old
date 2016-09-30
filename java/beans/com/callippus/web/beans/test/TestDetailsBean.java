package com.callippus.web.beans.test;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.file.FileUploadBean;

public class TestDetailsBean{
	
	private String param;
	private MultipartFile uploadFile;
	private MultipartFile uploadFile1;
	private String desc;
	private FileUploadBean fileUploadBean;
	private String requestID;
	private String requestId;
	private String addressTypeId1;
	private String addressTypeId2;
	private String ltcYear;
	private String status;
	private String fromDate;
	private String toDate;
	

	





	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLtcYear() {
		return ltcYear;
	}

	public void setLtcYear(String ltcYear) {
		this.ltcYear = ltcYear;
	}


	public String getAddressTypeId1() {
		return addressTypeId1;
	}

	public void setAddressTypeId1(String addressTypeId1) {
		this.addressTypeId1 = addressTypeId1;
	}

	public String getAddressTypeId2() {
		return addressTypeId2;
	}

	public void setAddressTypeId2(String addressTypeId2) {
		this.addressTypeId2 = addressTypeId2;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getParam() {
		return param;
	}

	public void setUploadFile1(MultipartFile uploadFile1) {
		this.uploadFile1 = uploadFile1;
	}

	public MultipartFile getUploadFile1() {
		return uploadFile1;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public FileUploadBean getFileUploadBean() {
		return fileUploadBean;
	}

	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}
		

}
