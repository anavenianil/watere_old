package com.callippus.web.mmg.cashbuildup.beans;

import java.util.Date;
import java.util.List;

import com.callippus.web.mmg.cashbuildup.dto.IRItemDetailsDTO;

public class IRMasterBean {
	private String voucherNo;
	private String demandNo;
	private String rebate;
	private String totalAmount;
	private int status;
	private Date voucherDate;
	private Date creationDate;
	private Date lastModifiedDate;
	private List<IRItemDetailsDTO> irItems;	
	private String param;
	private String type;
	private String jsonValue;
	private String voucherType;
	private List<DemandMasterDTO> demandList;
	private List<Object>  taxTypeList;
	private String totalCost;
	private String taxAmount;
	private String inventoryNo;
	private String taxType;
	private String requestId;
	private String cancelRequestId;
	private String invoiceCancelDate;
	private String reason;
	private String ipAddress;
	private String invId;
	private String accHeadNo;
	private String accountHeadId;
	
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
	public String getAccountHeadId() {
		return accountHeadId;
	}
	public void setAccountHeadId(String accountHeadId) {
		this.accountHeadId = accountHeadId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	public String getInvoiceCancelDate() {
		return invoiceCancelDate;
	}
	public void setInvoiceCancelDate(String invoiceCancelDate) {
		this.invoiceCancelDate = invoiceCancelDate;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public List<Object> getTaxTypeList() {
		return taxTypeList;
	}
	public void setTaxTypeList(List<Object> taxTypeList) {
		this.taxTypeList = taxTypeList;
	}
	public List<DemandMasterDTO> getDemandList() {
		return demandList;
	}
	public void setDemandList(List<DemandMasterDTO> demandList) {
		this.demandList = demandList;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
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
	public List<IRItemDetailsDTO> getIrItems() {
		return irItems;
	}
	public void setIrItems(List<IRItemDetailsDTO> irItems) {
		this.irItems = irItems;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getDemandNo() {
		return demandNo;
	}
	public void setDemandNo(String demandNo) {
		this.demandNo = demandNo;
	}
	public String getRebate() {
		return rebate;
	}
	public void setRebate(String rebate) {
		this.rebate = rebate;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
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

}
