package com.callippus.web.mmg.cashbuildup.beans;

import java.util.List;
import net.sf.json.JSONArray;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.VoucherItemDetailsDTO;

public class VoucherMasterBean{
	private String param;
	private String type;
	private String voucherId;
	private String voucherTypeId;
	private String inventoryNo;
	private String issuingUnit;
	private String receivingUnit;
	private String location;
	private String toInventory;
	private String referenceNo;
	private String postedBy;
	private String postingDate;
	private String status;
	private String creationDate;
	private String lastModifiedDate;
	private List<VoucherItemDetailsDTO> voucherItems;
	private String vouherType;
	private String purpose;
	private String voucherDate;
	private String labName;
	private List<InventoryMasterDTO> inventoryNumsList;
	private List<InventoryMasterDTO> toInventoryNumsList;
	private String inventorySfid;
	private String jsonValue;
	private JSONArray itemsJson; 
	private String message;
	private String requestID;
	private String vouherTypeName;
	private String invNum;
	private String periodFrom;
	private String periodTo;
	private String ineNum;
	private String rfcondemnation;
	private String divControlNum;
	private String workDetails;
	private String workAmount;
	private String ipAddress;
	
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getWorkAmount() {
		return workAmount;
	}
	public void setWorkAmount(String workAmount) {
		this.workAmount = workAmount;
	}
	public String getWorkDetails() {
		return workDetails;
	}
	public void setWorkDetails(String workDetails) {
		this.workDetails = workDetails;
	}
	public String getDivControlNum() {
		return divControlNum;
	}
	public void setDivControlNum(String divControlNum) {
		this.divControlNum = divControlNum;
	}
	public String getIneNum() {
		return ineNum;
	}
	public void setIneNum(String ineNum) {
		this.ineNum = ineNum;
	}
	public String getRfcondemnation() {
		return rfcondemnation;
	}
	public void setRfcondemnation(String rfcondemnation) {
		this.rfcondemnation = rfcondemnation;
	}
	public List<InventoryMasterDTO> getToInventoryNumsList() {
		return toInventoryNumsList;
	}
	public void setToInventoryNumsList(List<InventoryMasterDTO> toInventoryNumsList) {
		this.toInventoryNumsList = toInventoryNumsList;
	}
	public String getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}
	public String getPeriodTo() {
		return periodTo;
	}
	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}
	public String getInvNum() {
		return invNum;
	}
	public void setInvNum(String invNum) {
		this.invNum = invNum;
	}
	public String getVouherTypeName() {
		return vouherTypeName;
	}
	public void setVouherTypeName(String vouherTypeName) {
		this.vouherTypeName = vouherTypeName;
	}
	
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public JSONArray getItemsJson() {
		return itemsJson;
	}
	public void setItemsJson(JSONArray itemsJson) {
		this.itemsJson = itemsJson;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getInventorySfid() {
		return inventorySfid;
	}
	public void setInventorySfid(String inventorySfid) {
		this.inventorySfid = inventorySfid;
	}
	public List<InventoryMasterDTO> getInventoryNumsList() {
		return inventoryNumsList;
	}
	public void setInventoryNumsList(List<InventoryMasterDTO> inventoryNumsList) {
		this.inventoryNumsList = inventoryNumsList;
	}
	public String getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getVouherType() {
		return vouherType;
	}
	public void setVouherType(String vouherType) {
		this.vouherType = vouherType;
	}
	public List<VoucherItemDetailsDTO> getVoucherItems() {
		return voucherItems;
	}
	public void setVoucherItems(List<VoucherItemDetailsDTO> voucherItems) {
		this.voucherItems = voucherItems;
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
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getVoucherTypeId() {
		return voucherTypeId;
	}
	public void setVoucherTypeId(String voucherTypeId) {
		this.voucherTypeId = voucherTypeId;
	}
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public String getIssuingUnit() {
		return issuingUnit;
	}
	public void setIssuingUnit(String issuingUnit) {
		this.issuingUnit = issuingUnit;
	}
	public String getReceivingUnit() {
		return receivingUnit;
	}
	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getToInventory() {
		return toInventory;
	}
	public void setToInventory(String toInventory) {
		this.toInventory = toInventory;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
	
}
