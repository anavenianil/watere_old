package com.callippus.web.beans.reportDetails;

import java.util.List;

import com.callippus.web.beans.common.ErpBean;

public class ReportDetailsBean extends ErpBean {
	private int id;
	private String txnDate;
	private String designationId;
	private String cPoolIn;
	private String cPoolOut;
	
	private String type;
	private String jsonValue;
	private String message;
	private List<ReportDetailsBean> reportTypesList;
	
	
	
	public List<ReportDetailsBean> getReportTypesList() {
		return reportTypesList;
	}
	public void setReportTypesList(List<ReportDetailsBean> reportTypesList) {
		this.reportTypesList = reportTypesList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getcPoolIn() {
		return cPoolIn;
	}
	public void setcPoolIn(String cPoolIn) {
		this.cPoolIn = cPoolIn;
	}
	public String getcPoolOut() {
		return cPoolOut;
	}
	public void setcPoolOut(String cPoolOut) {
		this.cPoolOut = cPoolOut;
	}
	
	


}
