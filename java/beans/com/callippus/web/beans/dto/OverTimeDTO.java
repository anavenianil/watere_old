package com.callippus.web.beans.dto;

import java.util.Date;
public class OverTimeDTO
{
	private int id;
	private String sfid;
	private String singleHours;
	private String doubleHours;
	private String singleRate;
	private String doubleRate;
	private String singleAmount;
	private String doubleAmount;
	private Integer totalOTAmount;
	private String month;
	private Date creationDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getSingleHours() {
		return singleHours;
	}
	public void setSingleHours(String singleHours) {
		this.singleHours = singleHours;
	}
	public String getDoubleHours() {
		return doubleHours;
	}
	public void setDoubleHours(String doubleHours) {
		this.doubleHours = doubleHours;
	}
	public String getSingleRate() {
		return singleRate;
	}
	public void setSingleRate(String singleRate) {
		this.singleRate = singleRate;
	}
	public String getDoubleRate() {
		return doubleRate;
	}
	public void setDoubleRate(String doubleRate) {
		this.doubleRate = doubleRate;
	}
	public String getSingleAmount() {
		return singleAmount;
	}
	public void setSingleAmount(String singleAmount) {
		this.singleAmount = singleAmount;
	}
	public String getDoubleAmount() {
		return doubleAmount;
	}
	public void setDoubleAmount(String doubleAmount) {
		this.doubleAmount = doubleAmount;
	}
	public Integer getTotalOTAmount() {
		return totalOTAmount;
	}
	public void setTotalOTAmount(Integer totalOTAmount) {
		this.totalOTAmount = totalOTAmount;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
}
