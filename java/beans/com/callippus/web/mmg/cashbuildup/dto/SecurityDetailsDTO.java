package com.callippus.web.mmg.cashbuildup.dto;

import java.util.Date;
import java.util.List;

import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;

public class SecurityDetailsDTO {
	private Integer id;
	private String sfID;
	private Date demandDate;
	private String supplierId;
	private String materialCode;
	private String qty;
	private String verifiedBy;
	private String status;
	private String creationDate;
	private String lastModifiedDate;
	private String param;
	private String type;
	private String uom;
	private String description;
	private String memoNo;
	private String demandNo;
	private Date memoDate;
	private Date securityMemoDate;
	private String remarks;
	private String uomConvert;
	private String cncConvert;
	
	private List<SecurityDetailsDTO> demandList;
	private List<DemandMasterDTO> demandItemsList;
	private List<Object> demandItems;
	private List<SecurityDetailsDTO> securityItemsList;
	
	public String getUomConvert() {
		return uomConvert;
	}
	public void setUomConvert(String uomConvert) {
		this.uomConvert = uomConvert;
	}
	public String getCncConvert() {
		return cncConvert;
	}
	public void setCncConvert(String cncConvert) {
		this.cncConvert = cncConvert;
	}
	public List<SecurityDetailsDTO> getSecurityItemsList() {
		return securityItemsList;
	}
	public void setSecurityItemsList(List<SecurityDetailsDTO> securityItemsList) {
		this.securityItemsList = securityItemsList;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public List<Object> getDemandItems() {
		return demandItems;
	}
	public void setDemandItems(List<Object> demandItems) {
		this.demandItems = demandItems;
	}
	public List<DemandMasterDTO> getDemandItemsList() {
		return demandItemsList;
	}
	public void setDemandItemsList(List<DemandMasterDTO> demandItemsList) {
		this.demandItemsList = demandItemsList;
	}	
	public Date getMemoDate() {
		return memoDate;
	}
	public void setMemoDate(Date memoDate) {
		this.memoDate = memoDate;
	}
	public List<SecurityDetailsDTO> getDemandList() {
		return demandList;
	}
	public void setDemandList(List<SecurityDetailsDTO> demandList) {
		this.demandList = demandList;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMemoNo() {
		return memoNo;
	}
	public void setMemoNo(String memoNo) {
		this.memoNo = memoNo;
	}
	public String getDemandNo() {
		return demandNo;
	}
	public void setDemandNo(String demandNo) {
		this.demandNo = demandNo;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
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
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public Date getDemandDate() {
		return demandDate;
	}
	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSecurityCheckJson() {
		return securityCheckJson;
	}
	public void setSecurityCheckJson(String securityCheckJson) {
		this.securityCheckJson = securityCheckJson;
	}
	public Date getSecurityMemoDate() {
		return securityMemoDate;
	}
	public void setSecurityMemoDate(Date securityMemoDate) {
		this.securityMemoDate = securityMemoDate;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String securityCheckJson;
	

}
