package com.callippus.web.tada.dto;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;


public class TadaApprovalRequestDTO{
	
	private String requestId;
	private String sfid;
	private int desigId;
	private int deptId;
	private String phnNo;
	private Date departureDate;
	private String departureDateOne;
	private String conveyanceMode;
	private String tdWorkPlace;
	private String tdMoveId;
	private String daType;
	private String ipAddress;
	private String ammendementId;
	private HttpSession session;
	private String tadaRequestType;
	private String type;
	private String name;
	private String designation;
	private String basicPay;
	private String gradePay;
	private String workingPlace;
	private String entitlementClass;
	private int stayDuration;
	private String purpose;
	private String authorizedMove;
	private String tadaRequirement;
	private String tatkalQuota;
	private String departmentId;
	private String employmentTypeId;
	private String sfID;
	private String id;
	private String creationDate;
	private int status;
	private EmployeeBean empDetailsList;
	private Integer tadaAdvanceAmount;
	private Date applyDate;
	private String advanceFlag;
	
	private String accountentSign;
	private String sanctionNo;
	private String billNo;
	private Float cdaAmount;
	private String dvNumber;
	private String dvDate;
	private String dvNo;
	private List<SingingAuthorityDTO> accountOfficerList;
	private String reimFlag;
	private String gpfNo;
	private String settFlag;
	private float advanceAmountAftRes;
	private String referenceRequestID;
	private String projectName;
	private String historyID;
	private String remarks;
	private int tadaSettlementAmount;
	private Date arrivalDate;
	private String reason;
	private String movementOrderNo;
	private float tdSettAmountAftRes;
	private float ticketCancelCharges;
	private String ticketCancelReason;
	private String entitleExemption;
	
	
	
	
	
	
	public int getTadaSettlementAmount() {
		return tadaSettlementAmount;
	}
	public void setTadaSettlementAmount(int tadaSettlementAmount) {
		this.tadaSettlementAmount = tadaSettlementAmount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public float getAdvanceAmountAftRes() {
		return advanceAmountAftRes;
	}
	public void setAdvanceAmountAftRes(float advanceAmountAftRes) {
		this.advanceAmountAftRes = advanceAmountAftRes;
	}
	public String getSettFlag() {
		return settFlag;
	}
	public void setSettFlag(String settFlag) {
		this.settFlag = settFlag;
	}
	public String getGpfNo() {
		return gpfNo;
	}
	public void setGpfNo(String gpfNo) {
		this.gpfNo = gpfNo;
	}
	public String getReimFlag() {
		return reimFlag;
	}
	public void setReimFlag(String reimFlag) {
		this.reimFlag = reimFlag;
	}
	public List<SingingAuthorityDTO> getAccountOfficerList() {
		return accountOfficerList;
	}
	public void setAccountOfficerList(List<SingingAuthorityDTO> accountOfficerList) {
		this.accountOfficerList = accountOfficerList;
	}
	public String getDvNo() {
		return dvNo;
	}
	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}
	public String getAccountentSign() {
		return accountentSign;
	}
	public void setAccountentSign(String accountentSign) {
		this.accountentSign = accountentSign;
	}
	public String getSanctionNo() {
		return sanctionNo;
	}
	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public Float getCdaAmount() {
		return cdaAmount;
	}
	public void setCdaAmount(Float cdaAmount) {
		this.cdaAmount = cdaAmount;
	}
	public String getDvNumber() {
		return dvNumber;
	}
	public void setDvNumber(String dvNumber) {
		this.dvNumber = dvNumber;
	}
	
	public String getDvDate() {
		return dvDate;
	}
	public void setDvDate(String dvDate) {
		this.dvDate = dvDate;
	}
	public String getAdvanceFlag() {
		return advanceFlag;
	}
	public void setAdvanceFlag(String advanceFlag) {
		this.advanceFlag = advanceFlag;
	}
	public Integer getTadaAdvanceAmount() {
		return tadaAdvanceAmount;
	}
	public void setTadaAdvanceAmount(Integer tadaAdvanceAmount) {
		this.tadaAdvanceAmount = tadaAdvanceAmount;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getDepartureDateOne() {
		return departureDateOne;
	}
	public void setDepartureDateOne(String departureDateOne) {
		this.departureDateOne = departureDateOne;
	}
	public EmployeeBean getEmpDetailsList() {
		return empDetailsList;
	}
	public void setEmpDetailsList(EmployeeBean empDetailsList) {
		this.empDetailsList = empDetailsList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getTadaRequestType() {
		return tadaRequestType;
	}
	public void setTadaRequestType(String tadaRequestType) {
		this.tadaRequestType = tadaRequestType;
	}
	public String getEmploymentTypeId() {
		return employmentTypeId;
	}
	public void setEmploymentTypeId(String employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
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
	public String getWorkingPlace() {
		return workingPlace;
	}
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}
	public String getEntitlementClass() {
		return entitlementClass;
	}
	public void setEntitlementClass(String entitlementClass) {
		this.entitlementClass = entitlementClass;
	}
	public int getStayDuration() {
		return stayDuration;
	}
	public void setStayDuration(int stayDuration) {
		this.stayDuration = stayDuration;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getAuthorizedMove() {
		return authorizedMove;
	}
	public void setAuthorizedMove(String authorizedMove) {
		this.authorizedMove = authorizedMove;
	}
	public String getTadaRequirement() {
		return tadaRequirement;
	}
	public void setTadaRequirement(String tadaRequirement) {
		this.tadaRequirement = tadaRequirement;
	}
	public String getTatkalQuota() {
		return tatkalQuota;
	}
	public void setTatkalQuota(String tatkalQuota) {
		this.tatkalQuota = tatkalQuota;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public int getDesigId() {
		return desigId;
	}
	public void setDesigId(int desigId) {
		this.desigId = desigId;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	
	public String getPhnNo() {
		return phnNo;
	}
	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public String getConveyanceMode() {
		return conveyanceMode;
	}
	public void setConveyanceMode(String conveyanceMode) {
		this.conveyanceMode = conveyanceMode;
	}
	
	
	public String getTdWorkPlace() {
		return tdWorkPlace;
	}
	public void setTdWorkPlace(String tdWorkPlace) {
		this.tdWorkPlace = tdWorkPlace;
	}
	public String getTdMoveId() {
		return tdMoveId;
	}
	public void setTdMoveId(String tdMoveId) {
		this.tdMoveId = tdMoveId;
	}
	public String getDaType() {
		return daType;
	}
	public void setDaType(String daType) {
		this.daType = daType;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getAmmendementId() {
		return ammendementId;
	}
	public void setAmmendementId(String ammendementId) {
		this.ammendementId = ammendementId;
	}
	public String getReferenceRequestID() {
		return referenceRequestID;
	}
	public void setReferenceRequestID(String referenceRequestID) {
		this.referenceRequestID = referenceRequestID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMovementOrderNo() {
		return movementOrderNo;
	}
	public void setMovementOrderNo(String movementOrderNo) {
		this.movementOrderNo = movementOrderNo;
	}
	public float getTdSettAmountAftRes() {
		return tdSettAmountAftRes;
	}
	public void setTdSettAmountAftRes(float tdSettAmountAftRes) {
		this.tdSettAmountAftRes = tdSettAmountAftRes;
	}
	public float getTicketCancelCharges() {
		return ticketCancelCharges;
	}
	public void setTicketCancelCharges(float ticketCancelCharges) {
		this.ticketCancelCharges = ticketCancelCharges;
	}
	public String getTicketCancelReason() {
		return ticketCancelReason;
	}
	public void setTicketCancelReason(String ticketCancelReason) {
		this.ticketCancelReason = ticketCancelReason;
	}
	public String getEntitleExemption() {
		return entitleExemption;
	}
	public void setEntitleExemption(String entitleExemption) {
		this.entitleExemption = entitleExemption;
	}
	

}
