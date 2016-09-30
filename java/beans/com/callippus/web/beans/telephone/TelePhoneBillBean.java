package com.callippus.web.beans.telephone;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;

public class TelePhoneBillBean {
	private String param;
	private String type;
	private String sfid;
	private String remarks;
	private String message;
	private String fromID;
	private String toID;
	private String designationId;
	private List<DesignationDTO> designationList;
	private List<KeyValueDTO> selectedEmployeeList;
	private List<KeyValueDTO> notSelectedEmployeeList;
	private List<KeyValueDTO> empSelectedList;
	private List<KeyValueDTO> empNotselectedList;
	private List<TutionFeeClaimMasterDTO> claimList;
	private String pk;
	private String amount;
	private String serviceTax;
	private String totAmount;
	private String amount1;
	private String serviceTax1;
	private String totAmount1;
	private String internetFlag;
	private Date fromDate;
	private Date toDate;
	private EmployeeBean employeeBean;
	private List<TutionFeeClaimMasterDTO> telephoneClaimList;
	private String grandTotal;
	private String mainTelephoneList;
    private String telephoneNo;
    private String billNo;
    private String amountClaimed;
    private String requestId;
    private String receiptNo;
	private String ipAddress;
	private String requestTypeID;
	private String requestType;
	private String sanctionedAmount;
	private String name;
	private String claimId;
	private int claimTypeId;
	private int id;
	private String sfID;
	 private String requestID;
	
    private String employeeConfig;
    private String applicableEmployee;
    private List<TelephoneDesigEligibilityDetailsDTO> telephoneDesigList;
    private List<DesignationDTO> telephoneDesignationDeSelectedList;
    private List<DesignationDTO> telephoneDesignationSelectedList;
    private String editDesignations;
    private List<KeyValueDTO> telephoneEmployeeSelectedList;
    private List<TelephoneDesigEligibilityDetailsDTO> teleEmpInternetDetails;
    private String teleSfid;
    private List<TelephoneCashAssignmentDTO> teleCashDetails;
    private List<TelephoneDesigEligibilityDetailsDTO> teleDesigEmpInternet;
    
   
	private int amountWithInternet;
	private float serviceTaxWithInternet;
	private int totAmountWithInternet;
	private int amountWithoutInternet;
	private float serviceTaxWithoutInternet;
	private int totAmountWithoutInternet;
	private List<TelePhoneBillBean> telephoneNoList;
	private int cashAssignmentAmount;
	private String taskHolderRemarks;
    private String userRemarks;
    private String missingClaimRemarks;
	private String missingPeriodFrom;
	private String missingPeriodTo;
	
	
   public List<TelephoneDesigEligibilityDetailsDTO> getTeleEmpInternetDetails() {
		return teleEmpInternetDetails;
	}
	public void setTeleEmpInternetDetails(
			List<TelephoneDesigEligibilityDetailsDTO> teleEmpInternetDetails) {
		this.teleEmpInternetDetails = teleEmpInternetDetails;
	}
	public int getClaimTypeId() {
		return claimTypeId;
	}
	public void setClaimTypeId(int claimTypeId) {
		this.claimTypeId = claimTypeId;
	}
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSanctionedAmount() {
		return sanctionedAmount;
	}
	public void setSanctionedAmount(String sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}
	public String getRequestTypeID() {
		return requestTypeID;
	}
	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getAmountClaimed() {
		return amountClaimed;
	}
	public void setAmountClaimed(String amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getMainTelephoneList() {
		return mainTelephoneList;
	}
	public void setMainTelephoneList(String mainTelephoneList) {
		this.mainTelephoneList = mainTelephoneList;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public List<TutionFeeClaimMasterDTO> getTelephoneClaimList() {
		return telephoneClaimList;
	}
	public void setTelephoneClaimList(
			List<TutionFeeClaimMasterDTO> telephoneClaimList) {
		this.telephoneClaimList = telephoneClaimList;
	}
	public EmployeeBean getEmployeeBean() {
		return employeeBean;
	}
	public void setEmployeeBean(EmployeeBean employeeBean) {
		this.employeeBean = employeeBean;
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
	public List<TutionFeeClaimMasterDTO> getClaimList() {
		return claimList;
	}
	public void setClaimList(List<TutionFeeClaimMasterDTO> claimList) {
		this.claimList = claimList;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public List<KeyValueDTO> getSelectedEmployeeList() {
		return selectedEmployeeList;
	}
	public void setSelectedEmployeeList(List<KeyValueDTO> selectedEmployeeList) {
		this.selectedEmployeeList = selectedEmployeeList;
	}
	public List<KeyValueDTO> getNotSelectedEmployeeList() {
		return notSelectedEmployeeList;
	}
	public void setNotSelectedEmployeeList(List<KeyValueDTO> notSelectedEmployeeList) {
		this.notSelectedEmployeeList = notSelectedEmployeeList;
	}
	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}
	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getFromID() {
		return fromID;
	}
	public void setFromID(String fromID) {
		this.fromID = fromID;
	}
	public String getToID() {
		return toID;
	}
	public void setToID(String toID) {
		this.toID = toID;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmployeeConfig() {
		return employeeConfig;
	}
	public void setEmployeeConfig(String employeeConfig) {
		this.employeeConfig = employeeConfig;
	}
	public List<KeyValueDTO> getEmpSelectedList() {
		return empSelectedList;
	}
	public void setEmpSelectedList(List<KeyValueDTO> empSelectedList) {
		this.empSelectedList = empSelectedList;
	}
	public List<KeyValueDTO> getEmpNotselectedList() {
		return empNotselectedList;
	}
	public void setEmpNotselectedList(List<KeyValueDTO> empNotselectedList) {
		this.empNotselectedList = empNotselectedList;
	}
	public String getApplicableEmployee() {
		return applicableEmployee;
	}
	public void setApplicableEmployee(String applicableEmployee) {
		this.applicableEmployee = applicableEmployee;
	}
	public List<TelephoneDesigEligibilityDetailsDTO> getTelephoneDesigList() {
		return telephoneDesigList;
	}
	public void setTelephoneDesigList(
			List<TelephoneDesigEligibilityDetailsDTO> telephoneDesigList) {
		this.telephoneDesigList = telephoneDesigList;
	}
	public List<DesignationDTO> getTelephoneDesignationDeSelectedList() {
		return telephoneDesignationDeSelectedList;
	}
	public void setTelephoneDesignationDeSelectedList(
			List<DesignationDTO> telephoneDesignationDeSelectedList) {
		this.telephoneDesignationDeSelectedList = telephoneDesignationDeSelectedList;
	}
	public List<DesignationDTO> getTelephoneDesignationSelectedList() {
		return telephoneDesignationSelectedList;
	}
	public void setTelephoneDesignationSelectedList(
			List<DesignationDTO> telephoneDesignationSelectedList) {
		this.telephoneDesignationSelectedList = telephoneDesignationSelectedList;
	}
	public String getEditDesignations() {
		return editDesignations;
	}
	public void setEditDesignations(String editDesignations) {
		this.editDesignations = editDesignations;
	}
	public List<KeyValueDTO> getTelephoneEmployeeSelectedList() {
		return telephoneEmployeeSelectedList;
	}
	public void setTelephoneEmployeeSelectedList(
			List<KeyValueDTO> telephoneEmployeeSelectedList) {
		this.telephoneEmployeeSelectedList = telephoneEmployeeSelectedList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getTeleSfid() {
		return teleSfid;
	}
	public void setTeleSfid(String teleSfid) {
		this.teleSfid = teleSfid;
	}
	public List<TelephoneCashAssignmentDTO> getTeleCashDetails() {
		return teleCashDetails;
	}
	public void setTeleCashDetails(List<TelephoneCashAssignmentDTO> teleCashDetails) {
		this.teleCashDetails = teleCashDetails;
	}
	public int getAmountWithInternet() {
		return amountWithInternet;
	}
	public void setAmountWithInternet(int amountWithInternet) {
		this.amountWithInternet = amountWithInternet;
	}
	public float getServiceTaxWithInternet() {
		return serviceTaxWithInternet;
	}
	public void setServiceTaxWithInternet(float serviceTaxWithInternet) {
		this.serviceTaxWithInternet = serviceTaxWithInternet;
	}
	public int getTotAmountWithInternet() {
		return totAmountWithInternet;
	}
	public void setTotAmountWithInternet(int totAmountWithInternet) {
		this.totAmountWithInternet = totAmountWithInternet;
	}
	public int getAmountWithoutInternet() {
		return amountWithoutInternet;
	}
	public void setAmountWithoutInternet(int amountWithoutInternet) {
		this.amountWithoutInternet = amountWithoutInternet;
	}
	public float getServiceTaxWithoutInternet() {
		return serviceTaxWithoutInternet;
	}
	public void setServiceTaxWithoutInternet(float serviceTaxWithoutInternet) {
		this.serviceTaxWithoutInternet = serviceTaxWithoutInternet;
	}
	public int getTotAmountWithoutInternet() {
		return totAmountWithoutInternet;
	}
	public void setTotAmountWithoutInternet(int totAmountWithoutInternet) {
		this.totAmountWithoutInternet = totAmountWithoutInternet;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(String totAmount) {
		this.totAmount = totAmount;
	}
	public String getAmount1() {
		return amount1;
	}
	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}
	public String getServiceTax1() {
		return serviceTax1;
	}
	public void setServiceTax1(String serviceTax1) {
		this.serviceTax1 = serviceTax1;
	}
	public String getTotAmount1() {
		return totAmount1;
	}
	public void setTotAmount1(String totAmount1) {
		this.totAmount1 = totAmount1;
	}
	public String getInternetFlag() {
		return internetFlag;
	}
	public void setInternetFlag(String internetFlag) {
		this.internetFlag = internetFlag;
	}
	public List<TelePhoneBillBean> getTelephoneNoList() {
		return telephoneNoList;
	}
	public void setTelephoneNoList(List<TelePhoneBillBean> telephoneNoList) {
		this.telephoneNoList = telephoneNoList;
	}
	public int getCashAssignmentAmount() {
		return cashAssignmentAmount;
	}
	public void setCashAssignmentAmount(int cashAssignmentAmount) {
		this.cashAssignmentAmount = cashAssignmentAmount;
	}
	public List<TelephoneDesigEligibilityDetailsDTO> getTeleDesigEmpInternet() {
		return teleDesigEmpInternet;
	}
	public void setTeleDesigEmpInternet(
			List<TelephoneDesigEligibilityDetailsDTO> teleDesigEmpInternet) {
		this.teleDesigEmpInternet = teleDesigEmpInternet;
	}
	public String getTaskHolderRemarks() {
		return taskHolderRemarks;
	}
	public void setTaskHolderRemarks(String taskHolderRemarks) {
		this.taskHolderRemarks = taskHolderRemarks;
	}
	public String getMissingPeriodFrom() {
		return missingPeriodFrom;
	}
	public void setMissingPeriodFrom(String missingPeriodFrom) {
		this.missingPeriodFrom = missingPeriodFrom;
	}
	public String getMissingPeriodTo() {
		return missingPeriodTo;
	}
	public void setMissingPeriodTo(String missingPeriodTo) {
		this.missingPeriodTo = missingPeriodTo;
	}
	public String getUserRemarks() {
		return userRemarks;
	}
	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}
	public String getMissingClaimRemarks() {
		return missingClaimRemarks;
	}
	public void setMissingClaimRemarks(String missingClaimRemarks) {
		this.missingClaimRemarks = missingClaimRemarks;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	
	
}
