package com.callippus.web.beans.schedulereports;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;


public class ScheduleReportsBean {
	private String param;
	private String type;
	private List<PayBillEmpCategoryDTO> categoryList;
	private Date schedulemonth;
	private String categoryID;
	private String reportType;
	private String reportFormat;
	private String Value1;
	private Date Value2;
	private Date Value3;
	private String Parameter1;
	private String Parameter2;
	private String Parameter3;
	private String JasperFile;
	private String PdfFile;
	private String ExcelFile;
	private String financialYearId;
	private String slipType;
	private String fromDate;
	private String toDate;
	private int daTypeFlag;
	private String reportSubType;
	private String sfid;
	public List<String> paySlipMonthList;
    public List<String> paySlipYearList;
	public String paySlipYear;

	public String payBillType;
	private String employeeName;
	private List<KeyValueDTO> categories;
	private String requestType;
	private String exportedFile;
	private List<KeyValueDTO> scheduleTypes;
	

	public List<KeyValueDTO> getScheduleTypes() {
		return scheduleTypes;
	}
	public void setScheduleTypes(List<KeyValueDTO> scheduleTypes) {
		this.scheduleTypes = scheduleTypes;
	}
	public List<KeyValueDTO> getCategories() {
		return categories;
	}
	public void setCategories(List<KeyValueDTO> categories) {
		this.categories = categories;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getReportSubType() {
		return reportSubType;
	}
	public void setReportSubType(String reportSubType) {
		this.reportSubType = reportSubType;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getDaTypeFlag() {
		return daTypeFlag;
	}
	public void setDaTypeFlag(int daTypeFlag) {
		this.daTypeFlag = daTypeFlag;
	}
	public String getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(String financialYearId) {
		this.financialYearId = financialYearId;
	}
	public String getSlipType() {
		return slipType;
	}
	public void setSlipType(String slipType) {
		this.slipType = slipType;
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
	public Date getSchedulemonth() {
		return schedulemonth;
	}
	public void setSchedulemonth(Date schedulemonth) {
		this.schedulemonth = schedulemonth;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getValue1() {
		return Value1;
	}
	public void setValue1(String value1) {
		Value1 = value1;
	}
	public String getParameter1() {
		return Parameter1;
	}
	public void setParameter1(String parameter1) {
		Parameter1 = parameter1;
	}
	public String getParameter2() {
		return Parameter2;
	}
	public void setParameter2(String parameter2) {
		Parameter2 = parameter2;
	}
	public String getParameter3() {
		return Parameter3;
	}
	public void setParameter3(String parameter3) {
		Parameter3 = parameter3;
	}
	public String getJasperFile() {
		return JasperFile;
	}
	public void setJasperFile(String jasperFile) {
		JasperFile = jasperFile;
	}
	public String getPdfFile() {
		return PdfFile;
	}
	public void setPdfFile(String pdfFile) {
		PdfFile = pdfFile;
	}
	public String getReportFormat() {
		return reportFormat;
	}
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}
	public Date getValue3() {
		return Value3;
	}
	public void setValue3(Date value3) {
		Value3 = value3;
	}
	public String getExcelFile() {
		return ExcelFile;
	}
	public void setExcelFile(String excelFile) {
		ExcelFile = excelFile;
	}
	public Date getValue2() {
		return Value2;
	}
	public void setValue2(Date value2) {
		Value2 = value2;
	}
	public List<PayBillEmpCategoryDTO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<PayBillEmpCategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}
	public List<String> getPaySlipMonthList() {
		return paySlipMonthList;
	}
	public void setPaySlipMonthList(List<String> paySlipMonthList) {
		this.paySlipMonthList = paySlipMonthList;
	}
	public List<String> getPaySlipYearList() {
		return paySlipYearList;
	}
	public void setPaySlipYearList(List<String> paySlipYearList) {
		this.paySlipYearList = paySlipYearList;
	}
	public String getPaySlipYear() {
		return paySlipYear;
	}
	public void setPaySlipYear(String paySlipYear) {
		this.paySlipYear = paySlipYear;
	}
	public String getPayBillType() {
		return payBillType;
	}
	public void setPayBillType(String payBillType) {
		this.payBillType = payBillType;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getExportedFile() {
		return exportedFile;
	}
	public void setExportedFile(String exportedFile) {
		this.exportedFile = exportedFile;
	}
	
}
