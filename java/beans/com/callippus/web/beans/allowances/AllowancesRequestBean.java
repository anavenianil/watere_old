package com.callippus.web.beans.allowances;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.AllowanceTypeMasterDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.RequestBean;

public class AllowancesRequestBean extends RequestBean {
	
	private FamilyBean familyDetails;
	private EmployeeBean employeeDetails;
	private List<EmployeeBean> employeesList;
	private List<AllowanceTypeMasterDTO> allowancesList;
	private EmpPaymentsDTO paymentDetails;
	private String operationLocation;
	private Date operationDate;
	private String param;
	private String sterilizationCert;
	private String sterilCertFlag;
	private String livingChildFlag;
	private String allowanceName;
	private String allowanceFor;
	private String allowanceAmt;
	private String approvedBy;
	private Date approvedDate;
	private Date effectFromDate;
	private Date effectToDate;
	private String spouseAvailedFlag;
	private String empDetails;
	private int deptId;
	private String jsonValue;

	
	public String getEmpDetails() {
		return empDetails;
	}

	public void setEmpDetails(String empDetails) {
		this.empDetails = empDetails;
	}

	public Date getEffectToDate() {
		return effectToDate;
	}

	public void setEffectToDate(Date effectToDate) {
		this.effectToDate = effectToDate;
	}

	public String getAllowanceAmt() {
		return allowanceAmt;
	}

	public void setAllowanceAmt(String allowanceAmt) {
		this.allowanceAmt = allowanceAmt;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getEffectFromDate() {
		return effectFromDate;
	}

	public void setEffectFromDate(Date effectFromDate) {
		this.effectFromDate = effectFromDate;
	}

	public List<AllowanceTypeMasterDTO> getAllowancesList() {
		return allowancesList;
	}

	public void setAllowancesList(List<AllowanceTypeMasterDTO> allowancesList) {
		this.allowancesList = allowancesList;
	}

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
	
	public List<EmployeeBean> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<EmployeeBean> employeesList) {
		this.employeesList = employeesList;
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
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
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

	public String getAllowanceName() {
		return allowanceName;
	}

	public void setAllowanceName(String allowanceName) {
		this.allowanceName = allowanceName;
	}

	public String getAllowanceFor() {
		return allowanceFor;
	}

	public void setAllowanceFor(String allowanceFor) {
		this.allowanceFor = allowanceFor;
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
