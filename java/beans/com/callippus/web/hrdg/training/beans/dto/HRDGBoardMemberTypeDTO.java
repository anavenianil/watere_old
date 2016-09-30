package com.callippus.web.hrdg.training.beans.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class HRDGBoardMemberTypeDTO { 
	private int id;
	
	private String name;
	private String description;
	

	private int status;
	
	private String creationDate;
	private String lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy; 
	
	private int maxLimit;
	private int one;
	
	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
	

}
