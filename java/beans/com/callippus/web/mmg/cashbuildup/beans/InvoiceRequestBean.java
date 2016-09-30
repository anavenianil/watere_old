package com.callippus.web.mmg.cashbuildup.beans;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.mmg.cashbuildup.dto.FundsAllotedDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.IRItemDetailsDTO;

public class InvoiceRequestBean extends RequestBean{
	private String voucherNo;
	private String demandNo;
	private String rebate;
	private String totalAmount;
	private Date voucherDate;
	private Date creationDate;
	private Date lastModifiedDate;
	private List<IRItemDetailsDTO> irItems;	
	private String param;
	private String type;
	private JSONArray itemsJson;
	private String inventoryNo;
	private String postingDate;
	private Date memoDate;
	private String reason;
	private String accHeadNo;
	private String invId;
	private List<FundsAllotedDetailsDTO> fundDetails;
	
	public String getAccHeadNo() {
		return accHeadNo;
	}
	public void setAccHeadNo(String accHeadNo) {
		this.accHeadNo = accHeadNo;
	}
	public String getInvId() {
		return invId;
	}
	public void setInvId(String invId) {
		this.invId = invId;
	}
	public List<FundsAllotedDetailsDTO> getFundDetails() {
		return fundDetails;
	}
	public void setFundDetails(List<FundsAllotedDetailsDTO> fundDetails) {
		this.fundDetails = fundDetails;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getMemoDate() {
		return memoDate;
	}
	public void setMemoDate(Date memoDate) {
		this.memoDate = memoDate;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
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
	public List<IRItemDetailsDTO> getIrItems() {
		return irItems;
	}
	public void setIrItems(List<IRItemDetailsDTO> irItems) {
		this.irItems = irItems;
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
	public JSONArray getItemsJson() {
		return itemsJson;
	}
	public void setItemsJson(JSONArray itemsJson) {
		this.itemsJson = itemsJson;
	} 
}
