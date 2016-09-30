package com.callippus.web.mmg.cashbuildup.dto;

public class DemandTypeDTO {
	private String id;
	private String demandTypeName;
	private String demandDesc;
	private String status;
	private String creationDate;
	private String lastModifiedDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDemandTypeName() {
		return demandTypeName;
	}
	public void setDemandTypeName(String demandTypeName) {
		this.demandTypeName = demandTypeName;
	}
	public String getDemandDesc() {
		return demandDesc;
	}
	public void setDemandDesc(String demandDesc) {
		this.demandDesc = demandDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
	
}
