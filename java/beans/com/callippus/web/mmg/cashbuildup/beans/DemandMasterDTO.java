package com.callippus.web.mmg.cashbuildup.beans;

import java.util.Date;
import java.util.List;

import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandTypeDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.ProjectCodeDTO;

public class DemandMasterDTO {
	private String demandNo;
	private String demadTypeId;
	private String inventoryNo;
	private String accountHeadId;
	private Date demandDate;
	private String projectCode;
	private String mmgControlNo;
	private Date mmgControlDate;
	private String totalCost;
	private int status;
	private String remarks;	
	private String raisedBy;	
	private Date creationDate;
	private Date lastModifiedDate;
	private String param;
	private String type;
	private List<DemandItemDetailsDTO> demandItems;
	private String search;
	private String materialCode;
	private String qty;
	private String rate;
	private String jsonValue;
	private String demandFlag;
	private String message;
	private String sfid;
	private List<InventoryMasterDTO> invList;
	private List<ProjectCodeDTO> projectList;
	private List<AccountHeadDTO> accountHeadList;
	private List<DemandTypeDTO> demandTypeList;
	private String requestId;
	private String voucherType;
	private String cancelRequestId;
	private Date demandCancelDate;
	private String reason;
	private String ipAddress;
	private String invId;
	private String accHeadNo;
	
	public String getInvId() {
		return invId;
	}
	public void setInvId(String invId) {
		this.invId = invId;
	}
	public String getAccHeadNo() {
		return accHeadNo;
	}
	public void setAccHeadNo(String accHeadNo) {
		this.accHeadNo = accHeadNo;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCancelRequestId() {
		return cancelRequestId;
	}
	public void setCancelRequestId(String cancelRequestId) {
		this.cancelRequestId = cancelRequestId;
	}
	public Date getDemandCancelDate() {
		return demandCancelDate;
	}
	public void setDemandCancelDate(Date demandCancelDate) {
		this.demandCancelDate = demandCancelDate;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public List<DemandTypeDTO> getDemandTypeList() {
		return demandTypeList;
	}
	public void setDemandTypeList(List<DemandTypeDTO> demandTypeList) {
		this.demandTypeList = demandTypeList;
	}
	public List<InventoryMasterDTO> getInvList() {
		return invList;
	}
	public void setInvList(List<InventoryMasterDTO> invList) {
		this.invList = invList;
	}
	public List<ProjectCodeDTO> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<ProjectCodeDTO> projectList) {
		this.projectList = projectList;
	}
	public List<AccountHeadDTO> getAccountHeadList() {
		return accountHeadList;
	}
	public void setAccountHeadList(List<AccountHeadDTO> accountHeadList) {
		this.accountHeadList = accountHeadList;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDemandFlag() {
		return demandFlag;
	}
	public void setDemandFlag(String demandFlag) {
		this.demandFlag = demandFlag;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public List<DemandItemDetailsDTO> getDemandItems() {
		return demandItems;
	}
	public void setDemandItems(List<DemandItemDetailsDTO> demandItems) {
		this.demandItems = demandItems;
	}
	public String getDemandNo() {
		return demandNo;
	}
	public void setDemandNo(String demandNo) {
		this.demandNo = demandNo;
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
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public String getAccountHeadId() {
		return accountHeadId;
	}
	public void setAccountHeadId(String accountHeadId) {
		this.accountHeadId = accountHeadId;
	}
	public Date getDemandDate() {
		return demandDate;
	}
	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getMmgControlNo() {
		return mmgControlNo;
	}
	public void setMmgControlNo(String mmgControlNo) {
		this.mmgControlNo = mmgControlNo;
	}
	public Date getMmgControlDate() {
		return mmgControlDate;
	}
	public void setMmgControlDate(Date mmgControlDate) {
		this.mmgControlDate = mmgControlDate;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRaisedBy() {
		return raisedBy;
	}
	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
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
	public String getDemadTypeId() {
		return demadTypeId;
	}
	public void setDemadTypeId(String demadTypeId) {
		this.demadTypeId = demadTypeId;
	}

}
