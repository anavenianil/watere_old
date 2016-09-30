package com.callippus.web.beans.dto;

import java.util.Date;

public class EmpTotalPayDTO {

	private Integer id;

	private Integer payTxnId;

	private Integer dvNo;

	private Date dvDate;
	private Date creationDate;
	private String createdBy;

	private String lastModifiedBy;

	private Date lastModifiedDate;

	private Integer status;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPayTxnId() {
		return payTxnId;
	}

	public void setPayTxnId(Integer payTxnId) {
		this.payTxnId = payTxnId;
	}

	public Integer getDvNo() {
		return dvNo;
	}

	public void setDvNo(Integer dvNo) {
		this.dvNo = dvNo;
	}

	public Date getDvDate() {
		return dvDate;
	}

	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}