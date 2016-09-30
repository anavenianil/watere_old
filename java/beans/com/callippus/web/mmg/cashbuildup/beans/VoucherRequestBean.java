package com.callippus.web.mmg.cashbuildup.beans;

import java.util.List;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.mmg.cashbuildup.dto.VoucherItemDetailsDTO;
import net.sf.json.JSONArray;

public class VoucherRequestBean extends RequestBean{
	
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
	private String id;
	private String voucherNo;
	private String qty;
	private String materialCode;
	private String uom;
	private String cflag;
	private String irNo;
	private JSONArray itemsJson;
	private List<VoucherItemDetailsDTO> voucherItems;
	private String vouherTypeName;
	private String invNum;
	private String periodFrom;
	private String periodTo;
	private String ineNum;
	private String rfcondemnation;
	private String uomConvert;
	private String cncConvert;
	private String divControlNum;
	private String workDetails;
	private String workAmount;
	private String purpose;
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getWorkAmount() {
		return workAmount;
	}
	public void setWorkAmount(String workAmount) {
		this.workAmount = workAmount;
	}
	
	public String getDivControlNum() {
		return divControlNum;
	}
	public void setDivControlNum(String divControlNum) {
		this.divControlNum = divControlNum;
	}
	public String getWorkDetails() {
		return workDetails;
	}
	public void setWorkDetails(String workDetails) {
		this.workDetails = workDetails;
	}
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
	public String getVouherTypeName() {
		return vouherTypeName;
	}
	public void setVouherTypeName(String vouherTypeName) {
		this.vouherTypeName = vouherTypeName;
	}
	public String getInvNum() {
		return invNum;
	}
	public void setInvNum(String invNum) {
		this.invNum = invNum;
	}
	public List<VoucherItemDetailsDTO> getVoucherItems() {
		return voucherItems;
	}
	public void setVoucherItems(List<VoucherItemDetailsDTO> voucherItems) {
		this.voucherItems = voucherItems;
	}
	public JSONArray getItemsJson() {
		return itemsJson;
	}
	public void setItemsJson(JSONArray itemsJson) {
		this.itemsJson = itemsJson;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getCflag() {
		return cflag;
	}
	public void setCflag(String cflag) {
		this.cflag = cflag;
	}
	public String getIrNo() {
		return irNo;
	}
	public void setIrNo(String irNo) {
		this.irNo = irNo;
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
	
	
}
