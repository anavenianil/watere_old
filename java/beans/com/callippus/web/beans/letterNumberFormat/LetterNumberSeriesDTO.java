package com.callippus.web.beans.letterNumberFormat;

public class LetterNumberSeriesDTO {
	private int id;
	
	private int deptNum;
	private Integer deptSeriesStartNum;
	private Integer deptSeriesEndNum;
	private String deptShortName;
	private String deptCode;
	
	private int status;
	private String createdBy;
	private String creationDate;
	private String lastModifiedDate;
	private String lastModifiedBy;
	private String deptName;
	private String series;
	
	
	
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(int deptNum) {
		this.deptNum = deptNum;
	}
	
	public String getDeptShortName() {
		return deptShortName;
	}
	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
	public Integer getDeptSeriesStartNum() {
		return deptSeriesStartNum;
	}
	public void setDeptSeriesStartNum(Integer deptSeriesStartNum) {
		this.deptSeriesStartNum = deptSeriesStartNum;
	}
	public Integer getDeptSeriesEndNum() {
		return deptSeriesEndNum;
	}
	public void setDeptSeriesEndNum(Integer deptSeriesEndNum) {
		this.deptSeriesEndNum = deptSeriesEndNum;
	}
	
}
