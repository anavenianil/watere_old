package com.callippus.web.tada.dto;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.callippus.web.beans.dto.DynamicWorkflowTxnDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.RequestBean;

public class TadaRequestProcessBean extends RequestBean {
	
	private float taxi;
	private float onTransit;
	
	private String requestId;
	private String sfid;
	private int desigId;
	private int deptId;
	private String phnNo;
	private Date departureDate;
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
	private String id;
	private String sfID;
	private String creationDate;
	private int status;
	private EmployeeBean empDetailsList;
	private int tadaAdvanceAmount;
	private Date applyDate;
	private String referenceRequestID;
	private String requestID;
	private String amendmentType;
	private String amendmentReqId;
	private String jsonValue;
	private float claimAmount;
	private String innerRequestType;
	private String gpfNo;
	private int noOfDaysAcc;
	private int noOfDaysFood;
	private float accAmtPerDay;
	private float foodAmtPerDay;
	private float totalAccAmt;
	private float totalFoodAmt;
	private float ticketFare;
	private String projDirName;
	private String leaveRequirement;
	private String leaveId;
	private List<KeyValueDTO> tdLeaveTypeList;
	private Date arrivalDate;
	private String reason;
	private String movementOrderNo;
	private String approvedStage;
	private String projectName;
	private float ticketCancelCharges;
	private String ticketCancelReason;
	private DynamicWorkflowTxnDTO dynamicWorkFlowTxnDTO;
	private EmpPaymentsDTO  payDetailsList;
	private String entitlementExemption;
	private String userRemarks;
	
	
	
	public float getTaxi() {
		return taxi;
	}
	public void setTaxi(float taxi) {
		this.taxi = taxi;
	}
	public float getOnTransit() {
		return onTransit;
	}
	public void setOnTransit(float onTransit) {
		this.onTransit = onTransit;
	}
	public EmpPaymentsDTO getPayDetailsList() {
		return payDetailsList;
	}
	public void setPayDetailsList(EmpPaymentsDTO payDetailsList) {
		this.payDetailsList = payDetailsList;
	}
	public String getGpfNo() {
		return gpfNo;
	}
	public void setGpfNo(String gpfNo) {
		this.gpfNo = gpfNo;
	}
	public String getInnerRequestType() {
		return innerRequestType;
	}
	public void setInnerRequestType(String innerRequestType) {
		this.innerRequestType = innerRequestType;
	}
	public float getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getAmendmentReqId() {
		return amendmentReqId;
	}
	public void setAmendmentReqId(String amendmentReqId) {
		this.amendmentReqId = amendmentReqId;
	}
	public String getAmendmentType() {
		return amendmentType;
	}
	public void setAmendmentType(String amendmentType) {
		this.amendmentType = amendmentType;
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
	public EmployeeBean getEmpDetailsList() {
		return empDetailsList;
	}
	public void setEmpDetailsList(EmployeeBean empDetailsList) {
		this.empDetailsList = empDetailsList;
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
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
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
	public String getProjDirName() {
		return projDirName;
	}
	public void setProjDirName(String projDirName) {
		this.projDirName = projDirName;
	}
	public String getLeaveRequirement() {
		return leaveRequirement;
	}
	public void setLeaveRequirement(String leaveRequirement) {
		this.leaveRequirement = leaveRequirement;
	}
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	public List<KeyValueDTO> getTdLeaveTypeList() {
		return tdLeaveTypeList;
	}
	public void setTdLeaveTypeList(List<KeyValueDTO> tdLeaveTypeList) {
		this.tdLeaveTypeList = tdLeaveTypeList;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMovementOrderNo() {
		return movementOrderNo;
	}
	public void setMovementOrderNo(String movementOrderNo) {
		this.movementOrderNo = movementOrderNo;
	}
	public String getApprovedStage() {
		return approvedStage;
	}
	public void setApprovedStage(String approvedStage) {
		this.approvedStage = approvedStage;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public DynamicWorkflowTxnDTO getDynamicWorkFlowTxnDTO() {
		return dynamicWorkFlowTxnDTO;
	}
	public void setDynamicWorkFlowTxnDTO(DynamicWorkflowTxnDTO dynamicWorkFlowTxnDTO) {
		this.dynamicWorkFlowTxnDTO = dynamicWorkFlowTxnDTO;
	}
	public String getEntitlementExemption() {
		return entitlementExemption;
	}
	public void setEntitlementExemption(String entitlementExemption) {
		this.entitlementExemption = entitlementExemption;
	}
	public String getUserRemarks() {
		return userRemarks;
	}
	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}
	
	

}
