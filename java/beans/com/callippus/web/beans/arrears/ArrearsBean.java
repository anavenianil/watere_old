package com.callippus.web.beans.arrears;

import java.util.Date;
import java.util.List;

public class ArrearsBean {

	private String param;
	private String type;
	private String sfid;
	private String remarks;
	private String message;
	private Date fromDate;
	private Date toDate;
	private List<ArrearsStatusDTO> arrearsStatusList;
	private String adminAccDate;
	private String financeAccDate;
	private String empList;
	private String categoryId;
	private String userSfid;
	private String empName;
	private String designation;
	private String basicPay;
	private String gradePay;
	private String totalArrears;
	private String opLocation;
	private String check;
	private String fpaFlag;
	private String fpaGradePay;
	private String assessmentId;
	
	
	
	
	public String getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId;
	}
	public String getFpaFlag() {
		return fpaFlag;
	}
	public void setFpaFlag(String fpaFlag) {
		this.fpaFlag = fpaFlag;
	}
	public String getFpaGradePay() {
		return fpaGradePay;
	}
	public void setFpaGradePay(String fpaGradePay) {
		this.fpaGradePay = fpaGradePay;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getOpLocation() {
		return opLocation;
	}
	public void setOpLocation(String opLocation) {
		this.opLocation = opLocation;
	}
	public String getTotalArrears() {
		return totalArrears;
	}
	public void setTotalArrears(String totalArrears) {
		this.totalArrears = totalArrears;
	}
	public String getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(String basicPay) {
		this.basicPay = basicPay;
	}
	public String getGradePay() {
		return gradePay;
	}
	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getUserSfid() {
		return userSfid;
	}
	public void setUserSfid(String userSfid) {
		this.userSfid = userSfid;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getEmpList() {
		return empList;
	}
	public void setEmpList(String empList) {
		this.empList = empList;
	}
	public String getAdminAccDate() {
		return adminAccDate;
	}
	public void setAdminAccDate(String adminAccDate) {
		this.adminAccDate = adminAccDate;
	}
	public String getFinanceAccDate() {
		return financeAccDate;
	}
	public void setFinanceAccDate(String financeAccDate) {
		this.financeAccDate = financeAccDate;
	}
	public List<ArrearsStatusDTO> getArrearsStatusList() {
		return arrearsStatusList;
	}
	public void setArrearsStatusList(List<ArrearsStatusDTO> arrearsStatusList) {
		this.arrearsStatusList = arrearsStatusList;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
