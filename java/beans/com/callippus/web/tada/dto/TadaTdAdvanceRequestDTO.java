package com.callippus.web.tada.dto;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.callippus.web.beans.employee.EmployeeBean;

public class TadaTdAdvanceRequestDTO {
	
	
	private Float taxi;
	private Float onTransit;
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
	private int tadaAdvanceAmount;
	private Date applyDate;
	private String referenceRequestID;
	private String requestID;
	private int cdaAmount;
	private int noOfDaysAcc;
	private int noOfDaysFood;
	private float accAmtPerDay;
	private float foodAmtPerDay;
	private float totalAccAmt;
	private float totalFoodAmt;
	private float ticketFare;
	private float advanceAmountAftRes;
	private String dvNumber;
	private Date dvDate;
	private String historyID;
	private String strStatus;
	private String requestType;
	private float ticketCancelCharges;
	private String entitleExemption;
	
	
	

	public Float getTaxi() {
		return taxi;
	}
	public void setTaxi(Float taxi) {
		this.taxi = taxi;
	}
	public Float getOnTransit() {
		return onTransit;
	}
	public void setOnTransit(Float onTransit) {
		this.onTransit = onTransit;
	}
	public float getTicketCancelCharges() {
		return ticketCancelCharges;
	}
	public void setTicketCancelCharges(float ticketCancelCharges) {
		this.ticketCancelCharges = ticketCancelCharges;
	}
	public int getCdaAmount() {
		return cdaAmount;
	}
	public void setCdaAmount(int cdaAmount) {
		this.cdaAmount = cdaAmount;
	}
	public String getReferenceRequestID() {
		return referenceRequestID;
	}
	public void setReferenceRequestID(String referenceRequestID) {
		this.referenceRequestID = referenceRequestID;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	public String getDepartureDateOne() {
		return departureDateOne;
	}
	public void setDepartureDateOne(String departureDateOne) {
		this.departureDateOne = departureDateOne;
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
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public String getTadaRequestType() {
		return tadaRequestType;
	}
	public void setTadaRequestType(String tadaRequestType) {
		this.tadaRequestType = tadaRequestType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getEmploymentTypeId() {
		return employmentTypeId;
	}
	public void setEmploymentTypeId(String employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public EmployeeBean getEmpDetailsList() {
		return empDetailsList;
	}
	public void setEmpDetailsList(EmployeeBean empDetailsList) {
		this.empDetailsList = empDetailsList;
	}
	public int getTadaAdvanceAmount() {
		return tadaAdvanceAmount;
	}
	public void setTadaAdvanceAmount(int tadaAdvanceAmount) {
		this.tadaAdvanceAmount = tadaAdvanceAmount;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public int getNoOfDaysAcc() {
		return noOfDaysAcc;
	}
	public void setNoOfDaysAcc(int noOfDaysAcc) {
		this.noOfDaysAcc = noOfDaysAcc;
	}
	public int getNoOfDaysFood() {
		return noOfDaysFood;
	}
	public void setNoOfDaysFood(int noOfDaysFood) {
		this.noOfDaysFood = noOfDaysFood;
	}
	public float getAccAmtPerDay() {
		return accAmtPerDay;
	}
	public void setAccAmtPerDay(float accAmtPerDay) {
		this.accAmtPerDay = accAmtPerDay;
	}
	public float getFoodAmtPerDay() {
		return foodAmtPerDay;
	}
	public void setFoodAmtPerDay(float foodAmtPerDay) {
		this.foodAmtPerDay = foodAmtPerDay;
	}
	public float getTotalAccAmt() {
		return totalAccAmt;
	}
	public void setTotalAccAmt(float totalAccAmt) {
		this.totalAccAmt = totalAccAmt;
	}
	public float getTotalFoodAmt() {
		return totalFoodAmt;
	}
	public void setTotalFoodAmt(float totalFoodAmt) {
		this.totalFoodAmt = totalFoodAmt;
	}
	public float getTicketFare() {
		return ticketFare;
	}
	public void setTicketFare(float ticketFare) {
		this.ticketFare = ticketFare;
	}
	public float getAdvanceAmountAftRes() {
		return advanceAmountAftRes;
	}
	public void setAdvanceAmountAftRes(float advanceAmountAftRes) {
		this.advanceAmountAftRes = advanceAmountAftRes;
	}
	public String getDvNumber() {
		return dvNumber;
	}
	public void setDvNumber(String dvNumber) {
		this.dvNumber = dvNumber;
	}
	public Date getDvDate() {
		return dvDate;
	}
	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getEntitleExemption() {
		return entitleExemption;
	}
	public void setEntitleExemption(String entitleExemption) {
		this.entitleExemption = entitleExemption;
	}
	
	
	

}
