package com.callippus.web.beans.requests;

import java.util.Date;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DesignationDTO;

public class FPARequestProcessBean extends RequestBean {
	private DesignationDTO designationDetails;
	private DepartmentsDTO departmentDetails;
	
	private String operationLocation;
	private Date operationDate;
	private String sterilizationCert;
	private String sterilCertFlag;
	private String livingChildFlag;
	private String wifeName;
	private String wifePregFlag;
	private String spouseAvailedFlag;
	
	private int employeeType;
	private int departmentID;
	private float basicPay;
	private float gradePay;
	private float da;
	private Date payEffectiveDate;
	
	public String getOperationLocation() {
		return operationLocation;
	}
	public void setOperationLocation(String operationLocation) {
		this.operationLocation = operationLocation;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public String getSterilizationCert() {
		return sterilizationCert;
	}
	public void setSterilizationCert(String sterilizationCert) {
		this.sterilizationCert = sterilizationCert;
	}
	public String getSterilCertFlag() {
		return sterilCertFlag;
	}
	public void setSterilCertFlag(String sterilCertFlag) {
		this.sterilCertFlag = sterilCertFlag;
	}
	public String getLivingChildFlag() {
		return livingChildFlag;
	}
	public void setLivingChildFlag(String livingChildFlag) {
		this.livingChildFlag = livingChildFlag;
	}
	public String getWifeName() {
		return wifeName;
	}
	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}
	public String getWifePregFlag() {
		return wifePregFlag;
	}
	public void setWifePregFlag(String wifePregFlag) {
		this.wifePregFlag = wifePregFlag;
	}
	public String getSpouseAvailedFlag() {
		return spouseAvailedFlag;
	}
	public void setSpouseAvailedFlag(String spouseAvailedFlag) {
		this.spouseAvailedFlag = spouseAvailedFlag;
	}
	public int getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(int employeeType) {
		this.employeeType = employeeType;
	}
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public float getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(float basicPay) {
		this.basicPay = basicPay;
	}
	public float getGradePay() {
		return gradePay;
	}
	public void setGradePay(float gradePay) {
		this.gradePay = gradePay;
	}
	public float getDa() {
		return da;
	}
	public void setDa(float da) {
		this.da = da;
	}
	public void setDesignationDetails(DesignationDTO designationDetails) {
		this.designationDetails = designationDetails;
	}
	public DesignationDTO getDesignationDetails() {
		return designationDetails;
	}
	public void setDepartmentDetails(DepartmentsDTO departmentDetails) {
		this.departmentDetails = departmentDetails;
	}
	public DepartmentsDTO getDepartmentDetails() {
		return departmentDetails;
	}
	public void setPayEffectiveDate(Date payEffectiveDate) {
		this.payEffectiveDate = payEffectiveDate;
	}
	public Date getPayEffectiveDate() {
		return payEffectiveDate;
	}
	

}
