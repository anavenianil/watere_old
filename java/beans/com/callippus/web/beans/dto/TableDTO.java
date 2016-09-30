package com.callippus.web.beans.dto;

import java.util.ArrayList;

public class TableDTO {
	
	private ArrayList rows;
	
	private String page;
	private String total;
	private String records;
	private String value;
	private String param;
	
	private String[] userdata;
	
	public String[] getUserdata() {
		return userdata;
	}
	public void setUserdata(String[] userdata) {
		this.userdata = userdata;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public ArrayList getRows() {
		return rows;
	}
	public void setRows(ArrayList rows) {
		this.rows = rows;
	}
	
}
