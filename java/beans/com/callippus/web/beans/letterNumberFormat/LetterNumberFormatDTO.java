package com.callippus.web.beans.letterNumberFormat;

public class LetterNumberFormatDTO {
	private int id;
	private Integer deptNum;
	private String topic;
	private String shortForm;
	private Integer serialNum;
	private String yearType;
	private Integer runningNum;
	private String letterNumberFormat;
	
	private int status;
	private String createdBy;
	private String creationDate;
	private String lastModifiedDate;
	private String lastModifiedBy;
	private String deptName;
	
	private Integer fileTypeId;
	private String fileTypeName; 
	
	public Integer getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public String getFileTypeName() {
		return fileTypeName;
	}
	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
	
	
	
	public String getLetterNumberFormat() {
		return letterNumberFormat;
	}
	public void setLetterNumberFormat(String letterNumberFormat) {
		this.letterNumberFormat = letterNumberFormat;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(Integer deptNum) {
		this.deptNum = deptNum;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getShortForm() {
		return shortForm;
	}
	public void setShortForm(String shortForm) {
		this.shortForm = shortForm;
	}
	public Integer getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}
	public String getYearType() {
		return yearType;
	}
	public void setYearType(String yearType) {
		this.yearType = yearType;
	}
	public Integer getRunningNum() {
		return runningNum;
	}
	public void setRunningNum(Integer runningNum) {
		this.runningNum = runningNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	

}
