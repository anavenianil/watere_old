package com.callippus.web.mmg.cashbuildup.dto;



public class VoucherItemDetailsDTO {
	private int id;
	private String voucherId;
	private String materialCode;
	private String qty;
	private String cflag;
	private String uom;
	private String irNo;
	private String status;
	private String creationDate;
	private String lastModifiedDate;
	private String postedBy;
	private String materialName;
	private String uomConvert;
	private String cncConvert;
	
	
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
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
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
	public String getCflag() {
		return cflag;
	}
	public void setCflag(String cflag) {
		this.cflag = cflag;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getIrNo() {
		return irNo;
	}
	public void setIrNo(String irNo) {
		this.irNo = irNo;
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
