package com.callippus.web.beans.redetails;

import java.util.List;

import com.callippus.web.beans.common.ErpBean;

public class ReDetailsBean extends ErpBean{
	private int id;
	private String txnDate;
	private String designationId;
	private String reportType;
	private String param;
	private String reValue;
	private String jsonValue;
	private String message;
	private List<ReDetailsBean> reList;
	private String categoryID;
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReValue() {
		return reValue;
	}
	public void setReValue(String reValue) {
		this.reValue = reValue;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public List<ReDetailsBean> getReList() {
		return reList;
	}
	public void setReList(List<ReDetailsBean> reList) {
		this.reList = reList;
	}

}
