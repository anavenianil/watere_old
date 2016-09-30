package com.callippus.web.loan.beans.request;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.loan.beans.dto.LoanHBATypeMasterDTO;

public class LoanHBARequestBean extends CommonDTO {
	private String param;
	private String type;
	private int nodeID;

	private String sfID;
	private String result;
	private String message;
	private EmployeeBean employeeDetails;
	private String requestID;
	private String requestId;
	

	

	private String pfFlag;
	private int departmentID;
	private String locationWithAddress;
	private String ruralOrUrban;
	private String demarAndDev;
	private float approximateArea;
	private float cost;
	private float amountPaid;
	private Date proposedAcquire;
	private String unExPortionLease;
	private int floorType;
	private float estimatedCost;
	private float advanceReq;
	private int noOfInstalPrinciple;
	private int noOfInstalInterest;
	private float plinthArea;
	private float pliProposedEnlarge;
	private float costAcquisition;
	private float costProposed;
	private float totalPlinth;
	private float totalCost;
	private Date whenConstructed;
	private float priceSettled;
	private String agencyFrmPurchased;
	private float amtAlreadyPaid;
	private float amtToPaid;
	private float plinthAreaFW;
	private float presentMarketValue;
	private String reasons;
	private int employeeType;
	private String houseAdvanceType;
	private String floorGrid;
	private Float houseTotalCost;
	private String miscFlag;
	private String miscLocationAddress;
	private float interestRate;

	private List<LoanHBATypeMasterDTO> hbaLoanTypesList;
	private EmpPaymentsDTO paymentDetails;
	private String remarks;
	
	private int deptId;
	private String offlineSFID;
	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public String getLocationWithAddress() {
		return locationWithAddress;
	}

	public void setLocationWithAddress(String locationWithAddress) {
		this.locationWithAddress = locationWithAddress;
	}

	public String getRuralOrUrban() {
		return ruralOrUrban;
	}

	public void setRuralOrUrban(String ruralOrUrban) {
		this.ruralOrUrban = ruralOrUrban;
	}

	public String getDemarAndDev() {
		return demarAndDev;
	}

	public void setDemarAndDev(String demarAndDev) {
		this.demarAndDev = demarAndDev;
	}

	public float getApproximateArea() {
		return approximateArea;
	}

	public void setApproximateArea(float approximateArea) {
		this.approximateArea = approximateArea;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Date getProposedAcquire() {
		return proposedAcquire;
	}

	public void setProposedAcquire(Date proposedAcquire) {
		this.proposedAcquire = proposedAcquire;
	}

	public String getUnExPortionLease() {
		return unExPortionLease;
	}

	public void setUnExPortionLease(String unExPortionLease) {
		this.unExPortionLease = unExPortionLease;
	}

	public float getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(float estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public float getAdvanceReq() {
		return advanceReq;
	}

	public void setAdvanceReq(float advanceReq) {
		this.advanceReq = advanceReq;
	}

	public float getPlinthArea() {
		return plinthArea;
	}

	public void setPlinthArea(float plinthArea) {
		this.plinthArea = plinthArea;
	}

	public float getPliProposedEnlarge() {
		return pliProposedEnlarge;
	}

	public void setPliProposedEnlarge(float pliProposedEnlarge) {
		this.pliProposedEnlarge = pliProposedEnlarge;
	}

	public float getCostAcquisition() {
		return costAcquisition;
	}

	public void setCostAcquisition(float costAcquisition) {
		this.costAcquisition = costAcquisition;
	}

	public float getCostProposed() {
		return costProposed;
	}

	public void setCostProposed(float costProposed) {
		this.costProposed = costProposed;
	}

	public float getTotalPlinth() {
		return totalPlinth;
	}

	public void setTotalPlinth(float totalPlinth) {
		this.totalPlinth = totalPlinth;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public Date getWhenConstructed() {
		return whenConstructed;
	}

	public void setWhenConstructed(Date whenConstructed) {
		this.whenConstructed = whenConstructed;
	}

	public float getPriceSettled() {
		return priceSettled;
	}

	public void setPriceSettled(float priceSettled) {
		this.priceSettled = priceSettled;
	}

	public String getAgencyFrmPurchased() {
		return agencyFrmPurchased;
	}

	public void setAgencyFrmPurchased(String agencyFrmPurchased) {
		this.agencyFrmPurchased = agencyFrmPurchased;
	}

	public float getAmtAlreadyPaid() {
		return amtAlreadyPaid;
	}

	public void setAmtAlreadyPaid(float amtAlreadyPaid) {
		this.amtAlreadyPaid = amtAlreadyPaid;
	}

	public float getAmtToPaid() {
		return amtToPaid;
	}

	public void setAmtToPaid(float amtToPaid) {
		this.amtToPaid = amtToPaid;
	}

	public float getPlinthAreaFW() {
		return plinthAreaFW;
	}

	public void setPlinthAreaFW(float plinthAreaFW) {
		this.plinthAreaFW = plinthAreaFW;
	}

	public float getPresentMarketValue() {
		return presentMarketValue;
	}

	public void setPresentMarketValue(float presentMarketValue) {
		this.presentMarketValue = presentMarketValue;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public int getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(int employeeType) {
		this.employeeType = employeeType;
	}

	public List<LoanHBATypeMasterDTO> getHbaLoanTypesList() {
		return hbaLoanTypesList;
	}

	public void setHbaLoanTypesList(List<LoanHBATypeMasterDTO> hbaLoanTypesList) {
		this.hbaLoanTypesList = hbaLoanTypesList;
	}

	public EmpPaymentsDTO getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(EmpPaymentsDTO paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String getHouseAdvanceType() {
		return houseAdvanceType;
	}

	public void setHouseAdvanceType(String houseAdvanceType) {
		this.houseAdvanceType = houseAdvanceType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setFloorGrid(String floorGrid) {
		this.floorGrid = floorGrid;
	}

	public String getFloorGrid() {
		return floorGrid;
	}

	public void setHouseTotalCost(Float houseTotalCost) {
		this.houseTotalCost = houseTotalCost;
	}

	public Float getHouseTotalCost() {
		return houseTotalCost;
	}

	public void setFloorType(int floorType) {
		this.floorType = floorType;
	}

	public int getFloorType() {
		return floorType;
	}

	public void setMiscFlag(String miscFlag) {
		this.miscFlag = miscFlag;
	}

	public String getMiscFlag() {
		return miscFlag;
	}

	public void setMiscLocationAddress(String miscLocationAddress) {
		this.miscLocationAddress = miscLocationAddress;
	}

	public String getMiscLocationAddress() {
		return miscLocationAddress;
	}

	public void setNoOfInstalPrinciple(int noOfInstalPrinciple) {
		this.noOfInstalPrinciple = noOfInstalPrinciple;
	}

	public int getNoOfInstalPrinciple() {
		return noOfInstalPrinciple;
	}

	public void setNoOfInstalInterest(int noOfInstalInterest) {
		this.noOfInstalInterest = noOfInstalInterest;
	}

	public int getNoOfInstalInterest() {
		return noOfInstalInterest;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setPfFlag(String pfFlag) {
		this.pfFlag = pfFlag;
	}

	public String getPfFlag() {
		return pfFlag;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setOfflineSFID(String offlineSFID) {
		this.offlineSFID = offlineSFID;
	}

	public String getOfflineSFID() {
		return offlineSFID;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
