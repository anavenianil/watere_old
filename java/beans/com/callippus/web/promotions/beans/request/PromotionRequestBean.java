package com.callippus.web.promotions.beans.request;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.promotions.dto.AssessmentCategoryDTO;
import com.callippus.web.promotions.dto.AssessmentDetailsDTO;

public class PromotionRequestBean {
	private String param;
	private String type;
	private String sfID;
	private String result;
	private EmployeeBean employeeDetails;
	private String scaleOfPay;
	private Date incrementDate;
	private String nodeID;
	private List<AssessmentDetailsDTO> optionalCertificateList;
	private String ipAddress;
	private String refSfid;
	private List<AssessmentDetailsDTO> ViewOptionalCertificateList;
	private Integer payUpdate;
	private List<ApplicationRoleMappingDTO> appRoleList;
   	
	private List<AssessmentDetailsDTO> assessmentDetails;
	private int assessmentTypeID;
	private int assessmentCategoryID;
	private int yearID;
	private List<AssessmentCategoryDTO> assessmentCategoryList;
	private List<CategoryDTO> assessmentTypeList;
	private List<YearTypeDTO> yearList;
	private Integer appRoleID;
	private List<AssessmentDetailsDTO> assessmentDetails1;
	
	
	
	

	
	
	
	

	public List<AssessmentDetailsDTO> getAssessmentDetails1() {
		return assessmentDetails1;
	}

	public void setAssessmentDetails1(List<AssessmentDetailsDTO> assessmentDetails1) {
		this.assessmentDetails1 = assessmentDetails1;
	}

	public Integer getAppRoleID() {
		return appRoleID;
	}

	public void setAppRoleID(Integer appRoleID) {
		this.appRoleID = appRoleID;
	}

	public List<AssessmentCategoryDTO> getAssessmentCategoryList() {
		return assessmentCategoryList;
	}

	public void setAssessmentCategoryList(
			List<AssessmentCategoryDTO> assessmentCategoryList) {
		this.assessmentCategoryList = assessmentCategoryList;
	}

	public List<CategoryDTO> getAssessmentTypeList() {
		return assessmentTypeList;
	}

	public void setAssessmentTypeList(List<CategoryDTO> assessmentTypeList) {
		this.assessmentTypeList = assessmentTypeList;
	}

	public List<YearTypeDTO> getYearList() {
		return yearList;
	}

	public void setYearList(List<YearTypeDTO> yearList) {
		this.yearList = yearList;
	}

	public int getAssessmentTypeID() {
		return assessmentTypeID;
	}

	public void setAssessmentTypeID(int assessmentTypeID) {
		this.assessmentTypeID = assessmentTypeID;
	}

	public int getAssessmentCategoryID() {
		return assessmentCategoryID;
	}

	public void setAssessmentCategoryID(int assessmentCategoryID) {
		this.assessmentCategoryID = assessmentCategoryID;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public List<ApplicationRoleMappingDTO> getAppRoleList() {
		return appRoleList;
	}

	public void setAppRoleList(List<ApplicationRoleMappingDTO> appRoleList) {
		this.appRoleList = appRoleList;
	}

	public List<AssessmentDetailsDTO> getAssessmentDetails() {
		return assessmentDetails;
	}

	public void setAssessmentDetails(List<AssessmentDetailsDTO> assessmentDetails) {
		this.assessmentDetails = assessmentDetails;
	}

	
	

	public Integer getPayUpdate() {
		return payUpdate;
	}

	public void setPayUpdate(Integer payUpdate) {
		this.payUpdate = payUpdate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<AssessmentDetailsDTO> getOptionalCertificateList() {
		return optionalCertificateList;
	}

	public void setOptionalCertificateList(List<AssessmentDetailsDTO> optionalCertificateList) {
		this.optionalCertificateList = optionalCertificateList;
	}

	public String getScaleOfPay() {
		return scaleOfPay;
	}

	public void setScaleOfPay(String scaleOfPay) {
		this.scaleOfPay = scaleOfPay;
	}

	public Date getIncrementDate() {
		return incrementDate;
	}

	public void setIncrementDate(Date incrementDate) {
		this.incrementDate = incrementDate;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getRefSfid() {
		return refSfid;
	}

	public void setRefSfid(String refSfid) {
		this.refSfid = refSfid;
	}

	public List<AssessmentDetailsDTO> getViewOptionalCertificateList() {
		return ViewOptionalCertificateList;
	}

	public void setViewOptionalCertificateList(List<AssessmentDetailsDTO> viewOptionalCertificateList) {
		ViewOptionalCertificateList = viewOptionalCertificateList;
	}

}
