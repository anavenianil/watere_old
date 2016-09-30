package com.callippus.web.beans.fpa;

import java.util.Date;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.RequestBean;

public class FPARequestBean extends RequestBean {
	
	private FamilyBean familyDetails;
	private EmployeeBean employeeDetails;
	private EmpPaymentsDTO paymentDetails;
	private String operationLocation;
	private Date operationDate;
	private String sterilizationCert;
	private String sterilCertFlag;
	private String livingChildFlag;
	private String wifeName;
	private String wifePregFlag;
	private String spouseAvailedFlag;
	private int deptId;

	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}

	public void setPaymentDetails(EmpPaymentsDTO paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public EmpPaymentsDTO getPaymentDetails() {
		return paymentDetails;
	}

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

	public void setFamilyDetails(FamilyBean familyDetails) {
		this.familyDetails = familyDetails;
	}

	public FamilyBean getFamilyDetails() {
		return familyDetails;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getDeptId() {
		return deptId;
	}
}
