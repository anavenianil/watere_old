package com.callippus.web.loan.beans.dto;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.RequestBean;

public class ErpLoanRequestDTO extends RequestBean {
	private int id;
	private String staffNo;
	private String employeeType;
	private Float basicPay;
	private Float grossPay;
	private String requestedLoanType;
	private Float amountRequested;
	private String reasonForLoan;
	private String ipAddress;
	private Date requestedDate;
	private Date sanctionedDate;
	private String previousLoanType;
	private Date previousLoanDate;
	private Float previousLoanAmount;
	private Float outstandingAmount;
	private Float monthlyDeduction;
	private Integer loanStatus;
	private String maxLaonAmtType;
	
	private List<ErpLoanPurposeDTO> erpLoanTypes;
	
	
	private Float eligibleAmount;
	private String previousLoanStaus;
	private Float approvedAmount;
	
	
	private String param;
	private String message;
	private String type;
	private int deptId;
	private EmployeeBean employeeDetails;
	private EmpPaymentsDTO paymentDetails;
	private String result;
	private String remarks;
	private String loanType;
	private String offlineSfid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public Float getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(Float basicPay) {
		this.basicPay = basicPay;
	}
	public Float getGrossPay() {
		return grossPay;
	}
	public void setGrossPay(Float grossPay) {
		this.grossPay = grossPay;
	}
	public String getRequestedLoanType() {
		return requestedLoanType;
	}
	public void setRequestedLoanType(String requestedLoanType) {
		this.requestedLoanType = requestedLoanType;
	}
	public Float getAmountRequested() {
		return amountRequested;
	}
	public void setAmountRequested(Float amountRequested) {
		this.amountRequested = amountRequested;
	}
	public String getReasonForLoan() {
		return reasonForLoan;
	}
	public void setReasonForLoan(String reasonForLoan) {
		this.reasonForLoan = reasonForLoan;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	public Date getSanctionedDate() {
		return sanctionedDate;
	}
	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}
	public String getPreviousLoanType() {
		return previousLoanType;
	}
	public void setPreviousLoanType(String previousLoanType) {
		this.previousLoanType = previousLoanType;
	}
	public Date getPreviousLoanDate() {
		return previousLoanDate;
	}
	public void setPreviousLoanDate(Date previousLoanDate) {
		this.previousLoanDate = previousLoanDate;
	}
	public Float getPreviousLoanAmount() {
		return previousLoanAmount;
	}
	public void setPreviousLoanAmount(Float previousLoanAmount) {
		this.previousLoanAmount = previousLoanAmount;
	}
	public Float getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(Float outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	public Float getMonthlyDeduction() {
		return monthlyDeduction;
	}
	public void setMonthlyDeduction(Float monthlyDeduction) {
		this.monthlyDeduction = monthlyDeduction;
	}
	public Integer getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	public List<ErpLoanPurposeDTO> getErpLoanTypes() {
		return erpLoanTypes;
	}
	public void setErpLoanTypes(List<ErpLoanPurposeDTO> erpLoanTypes) {
		this.erpLoanTypes = erpLoanTypes;
	}
	public Float getEligibleAmount() {
		return eligibleAmount;
	}
	public void setEligibleAmount(Float eligibleAmount) {
		this.eligibleAmount = eligibleAmount;
	}
	public String getPreviousLoanStaus() {
		return previousLoanStaus;
	}
	public void setPreviousLoanStaus(String previousLoanStaus) {
		this.previousLoanStaus = previousLoanStaus;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}
	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
	public EmpPaymentsDTO getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(EmpPaymentsDTO paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getOfflineSfid() {
		return offlineSfid;
	}
	public void setOfflineSfid(String offlineSfid) {
		this.offlineSfid = offlineSfid;
	}
	public Float getApprovedAmount() {
		return approvedAmount;
	}
	public void setApprovedAmount(Float approvedAmount) {
		this.approvedAmount = approvedAmount;
	}
	public String getMaxLaonAmtType() {
		return maxLaonAmtType;
	}
	public void setMaxLaonAmtType(String maxLaonAmtType) {
		this.maxLaonAmtType = maxLaonAmtType;
	}
	
	

}