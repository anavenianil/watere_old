package com.callippus.web.beans.requests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DynamicWorkflowTxnDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimDetailsDTO;

public class RequestBean {
private int doPartId;
	
	public int getDoPartId() {
		return doPartId;
	}
	public void setDoPartId(int doPartId) {
		this.doPartId = doPartId;
	}
	private String requestID; // request ID
	private String workflowID; // Workflow ID
	private String requestTypeID; // Request Type ID
	private int status;
	private String sfID; // Assigned from sfid
	private String requestType; // request type PIS/LEAVE
	private boolean requestInternalFlag; // request internal workflow flag
	private String parentID; // assigned to
	private String historyID; // history table unique id
	private String stageID; // workflow stage id
	private Date assignedDate;
	private String actionedDate;
	private String ipAddress;
	private String remarks;
	private String message;
	private String workflowType;
	private ArrayList<RequestsDTO> workflowHistory;
	private String requesterOfficeID;
	private List<EmployeeBean> empList;
	private List<OrgInstanceDTO> instanceList;
	private List requestTypeList;
	private String instanceId;
	private String param;
	private String type;
	private String name;
	private List searchList;
	private String requestIDs;
	private String changedValues;
	private ArrayList<WorkflowTxnDTO> txnDetails;
	private String referenceNumber;
	private String paymentTypeId;
	private String approvedDept;
	private String amountType;
	private String jsonValue;
	private String fundAmount;
	private String requester;
	private String requesterType;
	private String requestCount;
	private int stageStatus;
	private String workflowStageID;
	private String paymentDate;
	private String settlementDate;
	private String settleAmount;
	private String description;
	private String paymentType;
	private String qty;
	private String unitRate;
	private String taxType;
	private String memoNo;
	private String hardCopyFlag;
	private String orgRoleID;
	private String medicalFlag;
	private String approvedBy;
	private String prescriptionReceivedFlag;
	private String doPartNo;
	private String result;
	private String doPartDate;
	private String issuedAmount;
	private String treasury;
	private String payee;
	private String amount;
	private String dvNo;
	private String checkNo;
	private String checkDate;
	private String designationID;
	private KeyValueDTO keyValue;
	private String requestId;
	private String back;
	private String statusMsg;
	private String roleID;
	private String hqRefNo;
	private Date receivedDate;
	private int assignedRoleID;
	private String requestFrom;
	private String careOfAddress;
	private String requestedBy;
	private Date requestedDate;
	private String prevsfID;
	private boolean flag;
	private float sanctionedAmount;
	private Date sanctionedDate;
	private int sanctionedInstalments;
	private int sanctionedIntInstalments;
	private float emi;
	private String bankAccount;
	private String bankBranch;
	private Date recStartDate;
	private String loanRefNo;
	private String gazType;
	private String quarterType;
	private Date allotedDate;
	private String quarterNo;
	private Date occupiedDate;
	private String excessAmount;
	private String excessAmountFine;
	private String MROPaidNo;
	private Date MROPaidDate;
	private String amountPerPerson;
	private String amountPerEachInfant;
	private String requestedTo;
	private String role;
	private String stage;

	private int admissibleLabCharges;
	private int admissibleMedicinesCharges;
	private int admissibleConsultationFees;
	private int admissibleDisposableCharges;
	private int admissibleSpecialDevices;
	private int admissibleRoomRent;
	private int admissibleOtCharges;
	private int admissibleOtConsumables;
	private int admissibleAnaesthesiaCharges;
	private int admissibleImplantsCharges;
	private int admissibleArtificialCharges;
	private int admissibleProcedure;
	private int admissibleSpecialNurse;
	private int admissibleMiscellaneousCharges;
	private int admissibleTotalAmount;
	private Date approvedDate;
	private int penalNoOfDays;
	private int mroAmount;

	private String dischargeOfDuties;
	private String labWork;

	private String hqAcquired;
	private String incentiveClaimed;
	private String sponceredByGovt;
	private String hqIsEssential;
	private String hqRecog;
	private String nexus;
	private String hqEnclosed;
	private String addHq;
	private String hqAfterInduction;
	private String dateOfAcquire;
	private float sanctionedFpaAmount;
	private Date sanctionedFpaDate;

	private String daNewAmount;
	private String rmaKmAmount;
	private String journeyAmount;
	private String rmaDayAmount;
	private String daOldAmount;
	private int tadaAdvanceAmount;
	private String foodDaNewAmount;
	private String stayDaNewAmount;
	private String accDaNewAmount;
	private String distanceAftRes;
	private String amountPerKmAftRes;
	private String noOfDays;
	private int penalInterestId;
	private int projectTypeId;
	private String projectType;
	private String projDirSfid;
	private String amountPerDayAftRes;
	private String stayDaOldAmount;
	private String amountPerDay;
	private String strTicketFareAftRes;
	private String strClaimedJourneyAmtAftRes;
	private String strAccAmtAftRes;
	private String strClaimedAccAmtAftRes;
	private String strRMAKmDisAftRes;
	private String strRMAKmAmtAftRes;
	private String strClaimedRMAKmAftRes;
	private String strRMALocalDisAftRes;
	private String strRMALocalAmtAftRes;
	private String strClaimedRMALocalAftRes;
	private String strFoodAmtAftRes;
	private String penalInterestReq;
	private String totalFoodAmount;
	private String foodAmtRep;
	private Float issuedAdvAmount;
	private float totalTadaTdCalAmount;
	private String strRMADayAmtPerDay;
	private String strRMADayAmtAftRes;
	private String organizationID;
	private String delegateRemarks;
	private String strRMADailyDisAftRes;
	private String strRMADailyAmtAftRes;
	private String strClaimedRMADailyAftRes;
	private DynamicWorkflowTxnDTO dynamicWorkFlowTxnDTO;
	private boolean delegationFlag;
    //for MT
   
    private String vehicleDriverMapId;
    private String returnVehicleType;
    private String returnVehicleDriverMapId;
    private float FinanceAmountEncash;
    private String remarksDecline;
    
    
    private String appliedBy;
    private int ltcpenalNoofDays;
    
    
    
    
    
    
  //  private String name;
	private String designation;
	private String sfid;
	private String phnNo;
	private String claimPurpose;
	private String travellingTo;
	//private Date fromDate;
	//private Date toDate;
	private int foodandAccmAmt;
	private int daAmt;
	private int taxiAmt;
	private int transitAmt;
	private int totalAmt;
	private Date applyDate;
	//private String param;
//	private String noOfDays;
	private int perDayFoodandAccmAmt;
	private EmployeeBean empDetailsList;
	//private String sfID;
	private DepartmentsDTO deptDTO;
	private String reason;
	
//	private String requestId;
//	private String requestID;
	
//	private String ipAddress;
	
	private String deptName;
	
	//private int status;
	
	//added by mohib
	private Float eligibleAmount;
	private Float previousLoanAmount;
	private Float monthlyDeduction;
	private Float approvedAmount;
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	public Float getApprovedAmount() {
		return approvedAmount;
	}
	public void setApprovedAmount(Float approvedAmount) {
		this.approvedAmount = approvedAmount;
	}
	public Float getEligibleAmount() {
		return eligibleAmount;
	}
	public void setEligibleAmount(Float eligibleAmount) {
		this.eligibleAmount = eligibleAmount;
	}
	public Float getPreviousLoanAmount() {
		return previousLoanAmount;
	}
	public void setPreviousLoanAmount(Float previousLoanAmount) {
		this.previousLoanAmount = previousLoanAmount;
	}
	public Float getMonthlyDeduction() {
		return monthlyDeduction;
	}
	public void setMonthlyDeduction(Float monthlyDeduction) {
		this.monthlyDeduction = monthlyDeduction;
	}
	public int getLtcpenalNoofDays() {
		return ltcpenalNoofDays;
	}




	public float getFinanceAmountEncash() {
		return FinanceAmountEncash;
	}
	public void setFinanceAmountEncash(float financeAmountEncash) {
		FinanceAmountEncash = financeAmountEncash;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getPhnNo() {
		return phnNo;
	}
	public void setPhnNo(String phnNo) {
		this.phnNo = phnNo;
	}
	public String getClaimPurpose() {
		return claimPurpose;
	}
	public void setClaimPurpose(String claimPurpose) {
		this.claimPurpose = claimPurpose;
	}
	public String getTravellingTo() {
		return travellingTo;
	}
	public void setTravellingTo(String travellingTo) {
		this.travellingTo = travellingTo;
	}
	
	public int getFoodandAccmAmt() {
		return foodandAccmAmt;
	}
	public void setFoodandAccmAmt(int foodandAccmAmt) {
		this.foodandAccmAmt = foodandAccmAmt;
	}
	public int getDaAmt() {
		return daAmt;
	}
	public void setDaAmt(int daAmt) {
		this.daAmt = daAmt;
	}
	public int getTaxiAmt() {
		return taxiAmt;
	}
	public void setTaxiAmt(int taxiAmt) {
		this.taxiAmt = taxiAmt;
	}
	public int getTransitAmt() {
		return transitAmt;
	}
	public void setTransitAmt(int transitAmt) {
		this.transitAmt = transitAmt;
	}
	public int getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public int getPerDayFoodandAccmAmt() {
		return perDayFoodandAccmAmt;
	}
	public void setPerDayFoodandAccmAmt(int perDayFoodandAccmAmt) {
		this.perDayFoodandAccmAmt = perDayFoodandAccmAmt;
	}
	public EmployeeBean getEmpDetailsList() {
		return empDetailsList;
	}
	public void setEmpDetailsList(EmployeeBean empDetailsList) {
		this.empDetailsList = empDetailsList;
	}
	public DepartmentsDTO getDeptDTO() {
		return deptDTO;
	}
	public void setDeptDTO(DepartmentsDTO deptDTO) {
		this.deptDTO = deptDTO;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setLtcpenalNoofDays(int ltcpenalNoofDays) {
		this.ltcpenalNoofDays = ltcpenalNoofDays;
	}




	public String getAppliedBy() {
		return appliedBy;
	}




	public void setAppliedBy(String appliedBy) {
		this.appliedBy = appliedBy;
	}




	public String getRemarksDecline() {
		return remarksDecline;
	}




	public void setRemarksDecline(String remarksDecline) {
		this.remarksDecline = remarksDecline;
	}




	public String getReturnVehicleDriverMapId() {
		return returnVehicleDriverMapId;
	}




	public void setReturnVehicleDriverMapId(String returnVehicleDriverMapId) {
		this.returnVehicleDriverMapId = returnVehicleDriverMapId;
	}




	public String getReturnVehicleType() {
		return returnVehicleType;
	}




	public void setReturnVehicleType(String returnVehicleType) {
		this.returnVehicleType = returnVehicleType;
	}


	public String getVehicleDriverMapId() {
		return vehicleDriverMapId;
	}




	public void setVehicleDriverMapId(String vehicleDriverMapId) {
		this.vehicleDriverMapId = vehicleDriverMapId;
	}


	private List<List<TutionFeeClaimDetailsDTO>> mainTutionFeeClaimList;

	public List<List<TutionFeeClaimDetailsDTO>> getMainTutionFeeClaimList() {
		return mainTutionFeeClaimList;
	}

	public void setMainTutionFeeClaimList(List<List<TutionFeeClaimDetailsDTO>> mainTutionFeeClaimList) {
		this.mainTutionFeeClaimList = mainTutionFeeClaimList;
	}

	public Float getIssuedAdvAmount() {
		return issuedAdvAmount;
	}

	public void setIssuedAdvAmount(Float issuedAdvAmount) {
		this.issuedAdvAmount = issuedAdvAmount;
	}

	public String getStayDaOldAmount() {
		return stayDaOldAmount;
	}

	public void setStayDaOldAmount(String stayDaOldAmount) {
		this.stayDaOldAmount = stayDaOldAmount;
	}

	public String getAmountPerDay() {
		return amountPerDay;
	}

	public void setAmountPerDay(String amountPerDay) {
		this.amountPerDay = amountPerDay;
	}

	public String getAmountPerDayAftRes() {
		return amountPerDayAftRes;
	}

	public void setAmountPerDayAftRes(String amountPerDayAftRes) {
		this.amountPerDayAftRes = amountPerDayAftRes;
	}

	public String getStayDaNewAmount() {
		return stayDaNewAmount;
	}

	public void setStayDaNewAmount(String stayDaNewAmount) {
		this.stayDaNewAmount = stayDaNewAmount;
	}

	public String getProjDirSfid() {
		return projDirSfid;
	}

	public void setProjDirSfid(String projDirSfid) {
		this.projDirSfid = projDirSfid;
	}

	public int getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(int projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getDaNewAmount() {
		return daNewAmount;
	}

	public void setDaNewAmount(String daNewAmount) {
		this.daNewAmount = daNewAmount;
	}

	public String getRmaKmAmount() {
		return rmaKmAmount;
	}

	public void setRmaKmAmount(String rmaKmAmount) {
		this.rmaKmAmount = rmaKmAmount;
	}

	public String getJourneyAmount() {
		return journeyAmount;
	}

	public void setJourneyAmount(String journeyAmount) {
		this.journeyAmount = journeyAmount;
	}

	public String getRmaDayAmount() {
		return rmaDayAmount;
	}

	public void setRmaDayAmount(String rmaDayAmount) {
		this.rmaDayAmount = rmaDayAmount;
	}

	public String getDaOldAmount() {
		return daOldAmount;
	}

	public void setDaOldAmount(String daOldAmount) {
		this.daOldAmount = daOldAmount;
	}

	public int getTadaAdvanceAmount() {
		return tadaAdvanceAmount;
	}

	public void setTadaAdvanceAmount(int tadaAdvanceAmount) {
		this.tadaAdvanceAmount = tadaAdvanceAmount;
	}

	public String getFoodDaNewAmount() {
		return foodDaNewAmount;
	}

	public void setFoodDaNewAmount(String foodDaNewAmount) {
		this.foodDaNewAmount = foodDaNewAmount;
	}

	public String getAccDaNewAmount() {
		return accDaNewAmount;
	}

	public void setAccDaNewAmount(String accDaNewAmount) {
		this.accDaNewAmount = accDaNewAmount;
	}

	public String getDistanceAftRes() {
		return distanceAftRes;
	}

	public void setDistanceAftRes(String distanceAftRes) {
		this.distanceAftRes = distanceAftRes;
	}

	public String getAmountPerKmAftRes() {
		return amountPerKmAftRes;
	}

	public void setAmountPerKmAftRes(String amountPerKmAftRes) {
		this.amountPerKmAftRes = amountPerKmAftRes;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int getPenalInterestId() {
		return penalInterestId;
	}

	public void setPenalInterestId(int penalInterestId) {
		this.penalInterestId = penalInterestId;
	}

	public String getAddHq() {
		return addHq;
	}

	public void setAddHq(String addHq) {
		this.addHq = addHq;
	}

	public String getHqAfterInduction() {
		return hqAfterInduction;
	}

	public void setHqAfterInduction(String hqAfterInduction) {
		this.hqAfterInduction = hqAfterInduction;
	}

	public String getDateOfAcquire() {
		return dateOfAcquire;
	}

	public void setDateOfAcquire(String dateOfAcquire) {
		this.dateOfAcquire = dateOfAcquire;
	}

	public String getHqAcquired() {
		return hqAcquired;
	}

	public void setHqAcquired(String hqAcquired) {
		this.hqAcquired = hqAcquired;
	}

	public String getIncentiveClaimed() {
		return incentiveClaimed;
	}

	public void setIncentiveClaimed(String incentiveClaimed) {
		this.incentiveClaimed = incentiveClaimed;
	}

	public String getSponceredByGovt() {
		return sponceredByGovt;
	}

	public void setSponceredByGovt(String sponceredByGovt) {
		this.sponceredByGovt = sponceredByGovt;
	}

	public String getHqIsEssential() {
		return hqIsEssential;
	}

	public void setHqIsEssential(String hqIsEssential) {
		this.hqIsEssential = hqIsEssential;
	}

	public String getHqRecog() {
		return hqRecog;
	}

	public void setHqRecog(String hqRecog) {
		this.hqRecog = hqRecog;
	}

	public String getNexus() {
		return nexus;
	}

	public void setNexus(String nexus) {
		this.nexus = nexus;
	}

	public String getHqEnclosed() {
		return hqEnclosed;
	}

	public void setHqEnclosed(String hqEnclosed) {
		this.hqEnclosed = hqEnclosed;
	}

	public String getDischargeOfDuties() {
		return dischargeOfDuties;
	}

	public void setDischargeOfDuties(String dischargeOfDuties) {
		this.dischargeOfDuties = dischargeOfDuties;
	}

	public String getLabWork() {
		return labWork;
	}

	public void setLabWork(String labWork) {
		this.labWork = labWork;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public int getAdmissibleTotalAmount() {
		return admissibleTotalAmount;
	}

	public void setAdmissibleTotalAmount(int admissibleTotalAmount) {
		this.admissibleTotalAmount = admissibleTotalAmount;
	}

	public int getAdmissibleDisposableCharges() {
		return admissibleDisposableCharges;
	}

	public void setAdmissibleDisposableCharges(int admissibleDisposableCharges) {
		this.admissibleDisposableCharges = admissibleDisposableCharges;
	}

	public int getAdmissibleLabCharges() {
		return admissibleLabCharges;
	}

	public void setAdmissibleLabCharges(int admissibleLabCharges) {
		this.admissibleLabCharges = admissibleLabCharges;
	}

	public int getAdmissibleMedicinesCharges() {
		return admissibleMedicinesCharges;
	}

	public void setAdmissibleMedicinesCharges(int admissibleMedicinesCharges) {
		this.admissibleMedicinesCharges = admissibleMedicinesCharges;
	}

	public int getAdmissibleConsultationFees() {
		return admissibleConsultationFees;
	}

	public void setAdmissibleConsultationFees(int admissibleConsultationFees) {
		this.admissibleConsultationFees = admissibleConsultationFees;
	}

	public int getAdmissibleSpecialDevices() {
		return admissibleSpecialDevices;
	}

	public void setAdmissibleSpecialDevices(int admissibleSpecialDevices) {
		this.admissibleSpecialDevices = admissibleSpecialDevices;
	}

	public int getAdmissibleRoomRent() {
		return admissibleRoomRent;
	}

	public void setAdmissibleRoomRent(int admissibleRoomRent) {
		this.admissibleRoomRent = admissibleRoomRent;
	}

	public int getAdmissibleOtCharges() {
		return admissibleOtCharges;
	}

	public void setAdmissibleOtCharges(int admissibleOtCharges) {
		this.admissibleOtCharges = admissibleOtCharges;
	}

	public int getAdmissibleOtConsumables() {
		return admissibleOtConsumables;
	}

	public void setAdmissibleOtConsumables(int admissibleOtConsumables) {
		this.admissibleOtConsumables = admissibleOtConsumables;
	}

	public int getAdmissibleAnaesthesiaCharges() {
		return admissibleAnaesthesiaCharges;
	}

	public void setAdmissibleAnaesthesiaCharges(int admissibleAnaesthesiaCharges) {
		this.admissibleAnaesthesiaCharges = admissibleAnaesthesiaCharges;
	}

	public int getAdmissibleImplantsCharges() {
		return admissibleImplantsCharges;
	}

	public void setAdmissibleImplantsCharges(int admissibleImplantsCharges) {
		this.admissibleImplantsCharges = admissibleImplantsCharges;
	}

	public int getAdmissibleArtificialCharges() {
		return admissibleArtificialCharges;
	}

	public void setAdmissibleArtificialCharges(int admissibleArtificialCharges) {
		this.admissibleArtificialCharges = admissibleArtificialCharges;
	}

	public int getAdmissibleProcedure() {
		return admissibleProcedure;
	}

	public void setAdmissibleProcedure(int admissibleProcedure) {
		this.admissibleProcedure = admissibleProcedure;
	}

	public int getAdmissibleSpecialNurse() {
		return admissibleSpecialNurse;
	}

	public void setAdmissibleSpecialNurse(int admissibleSpecialNurse) {
		this.admissibleSpecialNurse = admissibleSpecialNurse;
	}

	public int getAdmissibleMiscellaneousCharges() {
		return admissibleMiscellaneousCharges;
	}

	public void setAdmissibleMiscellaneousCharges(int admissibleMiscellaneousCharges) {
		this.admissibleMiscellaneousCharges = admissibleMiscellaneousCharges;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getRequestedTo() {
		return requestedTo;
	}

	public void setRequestedTo(String requestedTo) {
		this.requestedTo = requestedTo;
	}

	public String getAmountPerPerson() {
		return amountPerPerson;
	}

	public void setAmountPerPerson(String amountPerPerson) {
		this.amountPerPerson = amountPerPerson;
	}

	public String getAmountPerEachInfant() {
		return amountPerEachInfant;
	}

	public void setAmountPerEachInfant(String amountPerEachInfant) {
		this.amountPerEachInfant = amountPerEachInfant;
	}

	public String getExcessAmount() {
		return excessAmount;
	}

	public void setExcessAmount(String excessAmount) {
		this.excessAmount = excessAmount;
	}

	public String getExcessAmountFine() {
		return excessAmountFine;
	}

	public void setExcessAmountFine(String excessAmountFine) {
		this.excessAmountFine = excessAmountFine;
	}

	public String getMROPaidNo() {
		return MROPaidNo;
	}

	public void setMROPaidNo(String mROPaidNo) {
		MROPaidNo = mROPaidNo;
	}

	public Date getMROPaidDate() {
		return MROPaidDate;
	}

	public void setMROPaidDate(Date mROPaidDate) {
		MROPaidDate = mROPaidDate;
	}

	public String getQuarterNo() {
		return quarterNo;
	}

	public void setQuarterNo(String quarterNo) {
		this.quarterNo = quarterNo;
	}

	public Date getOccupiedDate() {
		return occupiedDate;
	}

	public void setOccupiedDate(Date occupiedDate) {
		this.occupiedDate = occupiedDate;
	}

	public String getQuarterType() {
		return quarterType;
	}

	public void setQuarterType(String quarterType) {
		this.quarterType = quarterType;
	}

	public Date getAllotedDate() {
		return allotedDate;
	}

	public void setAllotedDate(Date allotedDate) {
		this.allotedDate = allotedDate;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getPrevsfID() {
		return prevsfID;
	}

	public void setPrevsfID(String prevsfID) {
		this.prevsfID = prevsfID;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public float getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(float sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public Date getSanctionedDate() {
		return sanctionedDate;
	}

	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	public int getSanctionedInstalments() {
		return sanctionedInstalments;
	}

	public void setSanctionedInstalments(int sanctionedInstalments) {
		this.sanctionedInstalments = sanctionedInstalments;
	}

	public float getEmi() {
		return emi;
	}

	public void setEmi(float emi) {
		this.emi = emi;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Date getRecStartDate() {
		return recStartDate;
	}

	public void setRecStartDate(Date recStartDate) {
		this.recStartDate = recStartDate;
	}

	public String getLoanRefNo() {
		return loanRefNo;
	}

	public void setLoanRefNo(String loanRefNo) {
		this.loanRefNo = loanRefNo;
	}

	public String getCareOfAddress() {
		return careOfAddress;
	}

	public void setCareOfAddress(String careOfAddress) {
		this.careOfAddress = careOfAddress;
	}

	public String getRequestFrom() {
		return requestFrom;
	}

	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}

	public int getAssignedRoleID() {
		return assignedRoleID;
	}

	public void setAssignedRoleID(int assignedRoleID) {
		this.assignedRoleID = assignedRoleID;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getHqRefNo() {
		return hqRefNo;
	}

	public void setHqRefNo(String hqRefNo) {
		this.hqRefNo = hqRefNo;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public String getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(String requestTypeID) {
		this.requestTypeID = requestTypeID;
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

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public boolean isRequestInternalFlag() {
		return requestInternalFlag;
	}

	public void setRequestInternalFlag(boolean requestInternalFlag) {
		this.requestInternalFlag = requestInternalFlag;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getStageID() {
		return stageID;
	}

	public void setStageID(String stageID) {
		this.stageID = stageID;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getActionedDate() {
		return actionedDate;
	}

	public void setActionedDate(String actionedDate) {
		this.actionedDate = actionedDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public String getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}

	public ArrayList<RequestsDTO> getWorkflowHistory() {
		return workflowHistory;
	}

	public void setWorkflowHistory(ArrayList<RequestsDTO> workflowHistory) {
		this.workflowHistory = workflowHistory;
	}

	public String getRequesterOfficeID() {
		return requesterOfficeID;
	}

	public void setRequesterOfficeID(String requesterOfficeID) {
		this.requesterOfficeID = requesterOfficeID;
	}

	public List<EmployeeBean> getEmpList() {
		return empList;
	}

	public void setEmpList(List<EmployeeBean> empList) {
		this.empList = empList;
	}

	public List<OrgInstanceDTO> getInstanceList() {
		return instanceList;
	}

	public void setInstanceList(List<OrgInstanceDTO> instanceList) {
		this.instanceList = instanceList;
	}

	public List getRequestTypeList() {
		return requestTypeList;
	}

	public void setRequestTypeList(List requestTypeList) {
		this.requestTypeList = requestTypeList;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getSearchList() {
		return searchList;
	}

	public void setSearchList(List searchList) {
		this.searchList = searchList;
	}

	public String getRequestIDs() {
		return requestIDs;
	}

	public void setRequestIDs(String requestIDs) {
		this.requestIDs = requestIDs;
	}

	public String getChangedValues() {
		return changedValues;
	}

	public void setChangedValues(String changedValues) {
		this.changedValues = changedValues;
	}

	public ArrayList<WorkflowTxnDTO> getTxnDetails() {
		return txnDetails;
	}

	public void setTxnDetails(ArrayList<WorkflowTxnDTO> txnDetails) {
		this.txnDetails = txnDetails;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getApprovedDept() {
		return approvedDept;
	}

	public void setApprovedDept(String approvedDept) {
		this.approvedDept = approvedDept;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getFundAmount() {
		return fundAmount;
	}

	public void setFundAmount(String fundAmount) {
		this.fundAmount = fundAmount;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getRequesterType() {
		return requesterType;
	}

	public void setRequesterType(String requesterType) {
		this.requesterType = requesterType;
	}

	public String getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(String requestCount) {
		this.requestCount = requestCount;
	}

	public int getStageStatus() {
		return stageStatus;
	}

	public void setStageStatus(int stageStatus) {
		this.stageStatus = stageStatus;
	}

	public String getWorkflowStageID() {
		return workflowStageID;
	}

	public void setWorkflowStageID(String workflowStageID) {
		this.workflowStageID = workflowStageID;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(String unitRate) {
		this.unitRate = unitRate;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getMemoNo() {
		return memoNo;
	}

	public void setMemoNo(String memoNo) {
		this.memoNo = memoNo;
	}

	public String getHardCopyFlag() {
		return hardCopyFlag;
	}

	public void setHardCopyFlag(String hardCopyFlag) {
		this.hardCopyFlag = hardCopyFlag;
	}

	public String getOrgRoleID() {
		return orgRoleID;
	}

	public void setOrgRoleID(String orgRoleID) {
		this.orgRoleID = orgRoleID;
	}

	public String getMedicalFlag() {
		return medicalFlag;
	}

	public void setMedicalFlag(String medicalFlag) {
		this.medicalFlag = medicalFlag;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getPrescriptionReceivedFlag() {
		return prescriptionReceivedFlag;
	}

	public void setPrescriptionReceivedFlag(String prescriptionReceivedFlag) {
		this.prescriptionReceivedFlag = prescriptionReceivedFlag;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDoPartDate() {
		return doPartDate;
	}

	public void setDoPartDate(String doPartDate) {
		this.doPartDate = doPartDate;
	}

	public String getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(String issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getTreasury() {
		return treasury;
	}

	public void setTreasury(String treasury) {
		this.treasury = treasury;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDvNo() {
		return dvNo;
	}

	public void setDvNo(String dvNo) {
		this.dvNo = dvNo;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getDesignationID() {
		return designationID;
	}

	public void setDesignationID(String designationID) {
		this.designationID = designationID;
	}

	public KeyValueDTO getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(KeyValueDTO keyValue) {
		this.keyValue = keyValue;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public void setSanctionedIntInstalments(int sanctionedIntInstalments) {
		this.sanctionedIntInstalments = sanctionedIntInstalments;
	}

	public int getSanctionedIntInstalments() {
		return sanctionedIntInstalments;
	}

	public void setSanctionedFpaAmount(float sanctionedFpaAmount) {
		this.sanctionedFpaAmount = sanctionedFpaAmount;
	}

	public float getSanctionedFpaAmount() {
		return sanctionedFpaAmount;
	}

	public void setSanctionedFpaDate(Date sanctionedFpaDate) {
		this.sanctionedFpaDate = sanctionedFpaDate;
	}

	public Date getSanctionedFpaDate() {
		return sanctionedFpaDate;
	}

	public String getStrTicketFareAftRes() {
		return strTicketFareAftRes;
	}

	public void setStrTicketFareAftRes(String strTicketFareAftRes) {
		this.strTicketFareAftRes = strTicketFareAftRes;
	}

	public String getStrClaimedJourneyAmtAftRes() {
		return strClaimedJourneyAmtAftRes;
	}

	public void setStrClaimedJourneyAmtAftRes(String strClaimedJourneyAmtAftRes) {
		this.strClaimedJourneyAmtAftRes = strClaimedJourneyAmtAftRes;
	}

	public String getStrAccAmtAftRes() {
		return strAccAmtAftRes;
	}

	public void setStrAccAmtAftRes(String strAccAmtAftRes) {
		this.strAccAmtAftRes = strAccAmtAftRes;
	}

	public String getStrClaimedAccAmtAftRes() {
		return strClaimedAccAmtAftRes;
	}

	public void setStrClaimedAccAmtAftRes(String strClaimedAccAmtAftRes) {
		this.strClaimedAccAmtAftRes = strClaimedAccAmtAftRes;
	}

	public String getStrRMAKmDisAftRes() {
		return strRMAKmDisAftRes;
	}

	public void setStrRMAKmDisAftRes(String strRMAKmDisAftRes) {
		this.strRMAKmDisAftRes = strRMAKmDisAftRes;
	}

	public String getStrRMAKmAmtAftRes() {
		return strRMAKmAmtAftRes;
	}

	public void setStrRMAKmAmtAftRes(String strRMAKmAmtAftRes) {
		this.strRMAKmAmtAftRes = strRMAKmAmtAftRes;
	}

	public String getStrClaimedRMAKmAftRes() {
		return strClaimedRMAKmAftRes;
	}

	public void setStrClaimedRMAKmAftRes(String strClaimedRMAKmAftRes) {
		this.strClaimedRMAKmAftRes = strClaimedRMAKmAftRes;
	}

	public String getStrRMALocalDisAftRes() {
		return strRMALocalDisAftRes;
	}

	public void setStrRMALocalDisAftRes(String strRMALocalDisAftRes) {
		this.strRMALocalDisAftRes = strRMALocalDisAftRes;
	}

	public String getStrRMALocalAmtAftRes() {
		return strRMALocalAmtAftRes;
	}

	public void setStrRMALocalAmtAftRes(String strRMALocalAmtAftRes) {
		this.strRMALocalAmtAftRes = strRMALocalAmtAftRes;
	}

	public String getStrClaimedRMALocalAftRes() {
		return strClaimedRMALocalAftRes;
	}

	public void setStrClaimedRMALocalAftRes(String strClaimedRMALocalAftRes) {
		this.strClaimedRMALocalAftRes = strClaimedRMALocalAftRes;
	}

	public String getStrFoodAmtAftRes() {
		return strFoodAmtAftRes;
	}

	public void setStrFoodAmtAftRes(String strFoodAmtAftRes) {
		this.strFoodAmtAftRes = strFoodAmtAftRes;
	}

	public String getPenalInterestReq() {
		return penalInterestReq;
	}

	public void setPenalInterestReq(String penalInterestReq) {
		this.penalInterestReq = penalInterestReq;
	}

	public String getTotalFoodAmount() {
		return totalFoodAmount;
	}

	public void setTotalFoodAmount(String totalFoodAmount) {
		this.totalFoodAmount = totalFoodAmount;
	}

	public String getFoodAmtRep() {
		return foodAmtRep;
	}

	public void setFoodAmtRep(String foodAmtRep) {
		this.foodAmtRep = foodAmtRep;
	}

	public int getPenalNoOfDays() {
		return penalNoOfDays;
	}

	public void setPenalNoOfDays(int penalNoOfDays) {
		this.penalNoOfDays = penalNoOfDays;
	}

	public int getMroAmount() {
		return mroAmount;
	}

	public void setMroAmount(int mroAmount) {
		this.mroAmount = mroAmount;
	}

	public float getTotalTadaTdCalAmount() {
		return totalTadaTdCalAmount;
	}

	public void setTotalTadaTdCalAmount(float totalTadaTdCalAmount) {
		this.totalTadaTdCalAmount = totalTadaTdCalAmount;
	}

	public String getStrRMADayAmtPerDay() {
		return strRMADayAmtPerDay;
	}

	public void setStrRMADayAmtPerDay(String strRMADayAmtPerDay) {
		this.strRMADayAmtPerDay = strRMADayAmtPerDay;
	}

	public String getStrRMADayAmtAftRes() {
		return strRMADayAmtAftRes;
	}

	public void setStrRMADayAmtAftRes(String strRMADayAmtAftRes) {
		this.strRMADayAmtAftRes = strRMADayAmtAftRes;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	
	public String getDelegateRemarks() {
		return delegateRemarks;
	}
	
	public void setDelegateRemarks(String delegateRemarks) {
		this.delegateRemarks = delegateRemarks;
	}




	public String getStrRMADailyDisAftRes() {
		return strRMADailyDisAftRes;
	}




	public void setStrRMADailyDisAftRes(String strRMADailyDisAftRes) {
		this.strRMADailyDisAftRes = strRMADailyDisAftRes;
	}




	public String getStrRMADailyAmtAftRes() {
		return strRMADailyAmtAftRes;
	}




	public void setStrRMADailyAmtAftRes(String strRMADailyAmtAftRes) {
		this.strRMADailyAmtAftRes = strRMADailyAmtAftRes;
	}




	public String getStrClaimedRMADailyAftRes() {
		return strClaimedRMADailyAftRes;
	}




	public void setStrClaimedRMADailyAftRes(String strClaimedRMADailyAftRes) {
		this.strClaimedRMADailyAftRes = strClaimedRMADailyAftRes;
	}




	public DynamicWorkflowTxnDTO getDynamicWorkFlowTxnDTO() {
		return dynamicWorkFlowTxnDTO;
	}




	public void setDynamicWorkFlowTxnDTO(DynamicWorkflowTxnDTO dynamicWorkFlowTxnDTO) {
		this.dynamicWorkFlowTxnDTO = dynamicWorkFlowTxnDTO;
	}




	public boolean isDelegationFlag() {
		return delegationFlag;
	}




	public void setDelegationFlag(boolean delegationFlag) {
		this.delegationFlag = delegationFlag;
	}
	

}
