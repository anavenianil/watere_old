package com.callippus.web.mmg.cashbuildup.beans;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.FundsAllotedDetailsDTO;

public class DemandRequestBean extends RequestBean{

	private String demandNo;
	private String demadTypeId;
	private String inventoryNo;
	private String accountHeadId;
	private Date demandDate;
	private String projectCode;
	private String mmgControlNo;
	private Date mmgControlDate;
	private String totalCost;
	private String remarks;	
	private String raisedBy;
	private DemandMasterDTO demand;
	private JSONArray itemsJson; 
	private List<DemandItemDetailsDTO> demandItems;
	private String approvedDept;
	private List<Object> amountTypeList;
	private String paymentTypeId;
	private String fundAmount;
	private String jsonValue;
	private String reason;
	private String accHeadNo;
	private String invId;
	private List<FundsAllotedDetailsDTO> fundDetails;
	
	public List<FundsAllotedDetailsDTO> getFundDetails() {
		return fundDetails;
	}
	public void setFundDetails(List<FundsAllotedDetailsDTO> fundDetails) {
		this.fundDetails = fundDetails;
	}
	public String getAccHeadNo() {
		return accHeadNo;
	}
	public void setAccHeadNo(String accHeadNo) {
		this.accHeadNo = accHeadNo;
	}
	public void setInvId(String invId) {
		this.invId = invId;
	}
	public String getInvId() {
		return invId;
	}
	public void setInvid(String invId) {
		this.invId = invId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getFundAmount() {
		return fundAmount;
	}
	public void setFundAmount(String fundAmount) {
		this.fundAmount = fundAmount;
	}
	public List<Object> getAmountTypeList() {
		return amountTypeList;
	}
	public void setAmountTypeList(List<Object> amountTypeList) {
		this.amountTypeList = amountTypeList;
	}
	public String getApprovedDept() {
		return approvedDept;
	}
	public void setApprovedDept(String approvedDept) {
		this.approvedDept = approvedDept;
	}
	public List<DemandItemDetailsDTO> getDemandItems() {
		return demandItems;
	}
	public void setDemandItems(List<DemandItemDetailsDTO> demandItems) {
		this.demandItems = demandItems;
	}
	public JSONArray getItemsJson() {
		return itemsJson;
	}
	public void setItemsJson(JSONArray itemsJson) {
		this.itemsJson = itemsJson;
	}
	public DemandMasterDTO getDemand() {
		return demand;
	}
	public void setDemand(DemandMasterDTO demand) {
		this.demand = demand;
	}
	public String getDemandNo() {
		return demandNo;
	}
	public void setDemandNo(String demandNo) {
		this.demandNo = demandNo;
	}
	public String getDemadTypeId() {
		return demadTypeId;
	}
	public void setDemadTypeId(String demadTypeId) {
		this.demadTypeId = demadTypeId;
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
}
