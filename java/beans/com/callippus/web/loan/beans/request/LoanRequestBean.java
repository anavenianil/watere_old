package com.callippus.web.loan.beans.request;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.loan.beans.dto.GPFRulesDTO;
import com.callippus.web.loan.beans.dto.LoanAmountDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanAmountGridDTO;
import com.callippus.web.loan.beans.dto.LoanDesigMappingDTO;
import com.callippus.web.loan.beans.dto.LoanDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO;
import com.callippus.web.loan.beans.dto.LoanLeavesMappingDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;

public class LoanRequestBean extends CommonDTO {
	private String param;
	private String historyID;
	private String type;
	private int nodeID;
	private String sfID;
	private String result;
	private String message;
	private List<LoanFestivalMasterDTO> festivalsList;
	private EmpPaymentsDTO paymentDetails;
	private String loanSubType;
	private String temporaryDesc;
	private String prevLoanFlag;
	private String prevLoanName;
	private String prevLoanRecFlag;
	private String currentPositionFlag;
	private List<GPFRulesDTO> gpfSubTypes;
	private String loanType;
	private String loanName;
	private EmployeeBean employeeDetails;
	private String circumstance;
	private String reqAmount;
	private String requestID;
	private String prevLoanDetails;
	private float anticipatedAmount;
	private int requestedInstalments;
	private int requestedInterestInstalments;
	private Date prevAdvanceDate;
	private String commitmentFlag;
	private String declarationFlag;
	private String negotiationFlag;
	private Date leaveToDate;
	private Date leaveFromDate;
	private String intensionRuleFlag;
	private String intensionFlag;
	private String instalments;
	private String remarks;
	private int cdaId;
	private Date dateOfDrawl;
	private int stepID;
	private float confLoanAmount;
	private LoanAmountDetailsDTO loanAmountDetails;
	private LoanAmountGridDTO loanAmountGridDetails;
	private List<LoanTypeMasterDTO> loanTypeMasterList;
	private String reason;
	private List<LoanDetailsDTO> loanTypeDetailsList;
	private List<LoanLeavesMappingDTO> loanLeavesDetailsList;
	private List<LoanAmountDetailsDTO> loanAmountDetailsList;
	private List<LoanDesigMappingDTO> loanDesigMappingList;
	private List<LoanAmountGridDTO> loanAmountGrid;
	private int deptId;
	private String offlineSFID;
	private float interestRate;
	private String requestId;

	public List<LoanTypeMasterDTO> getLoanTypeMasterList() {
		return loanTypeMasterList;
	}

	public void setLoanTypeMasterList(List<LoanTypeMasterDTO> loanTypeMasterList) {
		this.loanTypeMasterList = loanTypeMasterList;
	}

	public LoanAmountGridDTO getLoanAmountGridDetails() {
		return loanAmountGridDetails;
	}

	public void setLoanAmountGridDetails(LoanAmountGridDTO loanAmountGridDetails) {
		this.loanAmountGridDetails = loanAmountGridDetails;
	}

	public LoanAmountDetailsDTO getLoanAmountDetails() {
		return loanAmountDetails;
	}

	public void setLoanAmountDetails(LoanAmountDetailsDTO loanAmountDetails) {
		this.loanAmountDetails = loanAmountDetails;
	}

	public float getConfLoanAmount() {
		return confLoanAmount;
	}

	public void setConfLoanAmount(float confLoanAmount) {
		this.confLoanAmount = confLoanAmount;
	}

	public int getStepID() {
		return stepID;
	}

	public void setStepID(int stepID) {
		this.stepID = stepID;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInstalments() {
		return instalments;
	}

	public void setInstalments(String instalments) {
		this.instalments = instalments;
	}

	public String getIntensionFlag() {
		return intensionFlag;
	}

	public void setIntensionFlag(String intensionFlag) {
		this.intensionFlag = intensionFlag;
	}

	public String getCircumstance() {
		return circumstance;
	}

	public void setCircumstance(String circumstance) {
		this.circumstance = circumstance;
	}

	public String getReqAmount() {
		return reqAmount;
	}

	public void setReqAmount(String reqAmount) {
		this.reqAmount = reqAmount;
	}

	public String getPrevLoanDetails() {
		return prevLoanDetails;
	}

	public void setPrevLoanDetails(String prevLoanDetails) {
		this.prevLoanDetails = prevLoanDetails;
	}

	public float getAnticipatedAmount() {
		return anticipatedAmount;
	}

	public void setAnticipatedAmount(float anticipatedAmount) {
		this.anticipatedAmount = anticipatedAmount;
	}

	public int getRequestedInstalments() {
		return requestedInstalments;
	}

	public void setRequestedInstalments(int requestedInstalments) {
		this.requestedInstalments = requestedInstalments;
	}

	public String getCommitmentFlag() {
		return commitmentFlag;
	}

	public void setCommitmentFlag(String commitmentFlag) {
		this.commitmentFlag = commitmentFlag;
	}

	public String getDeclarationFlag() {
		return declarationFlag;
	}

	public void setDeclarationFlag(String declarationFlag) {
		this.declarationFlag = declarationFlag;
	}

	public String getNegotiationFlag() {
		return negotiationFlag;
	}

	public void setNegotiationFlag(String negotiationFlag) {
		this.negotiationFlag = negotiationFlag;
	}

	public Date getLeaveToDate() {
		return leaveToDate;
	}

	public void setLeaveToDate(Date leaveToDate) {
		this.leaveToDate = leaveToDate;
	}

	public Date getLeaveFromDate() {
		return leaveFromDate;
	}

	public void setLeaveFromDate(Date leaveFromDate) {
		this.leaveFromDate = leaveFromDate;
	}

	public String getIntensionRuleFlag() {
		return intensionRuleFlag;
	}

	public void setIntensionRuleFlag(String intensionRuleFlag) {
		this.intensionRuleFlag = intensionRuleFlag;
	}

	public EmployeeBean getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeBean employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public List<GPFRulesDTO> getGpfSubTypes() {
		return gpfSubTypes;
	}

	public void setGpfSubTypes(List<GPFRulesDTO> gpfSubTypes) {
		this.gpfSubTypes = gpfSubTypes;
	}

	public String getTemporaryDesc() {
		return temporaryDesc;
	}

	public void setTemporaryDesc(String temporaryDesc) {
		this.temporaryDesc = temporaryDesc;
	}

	public String getPrevLoanFlag() {
		return prevLoanFlag;
	}

	public void setPrevLoanFlag(String prevLoanFlag) {
		this.prevLoanFlag = prevLoanFlag;
	}

	public String getPrevLoanName() {
		return prevLoanName;
	}

	public void setPrevLoanName(String prevLoanName) {
		this.prevLoanName = prevLoanName;
	}

	public String getPrevLoanRecFlag() {
		return prevLoanRecFlag;
	}

	public void setPrevLoanRecFlag(String prevLoanRecFlag) {
		this.prevLoanRecFlag = prevLoanRecFlag;
	}

	public String getCurrentPositionFlag() {
		return currentPositionFlag;
	}

	public void setCurrentPositionFlag(String currentPositionFlag) {
		this.currentPositionFlag = currentPositionFlag;
	}

	public EmpPaymentsDTO getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(EmpPaymentsDTO paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String getLoanSubType() {
		return loanSubType;
	}

	public void setLoanSubType(String loanSubType) {
		this.loanSubType = loanSubType;
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

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setCdaId(int cdaId) {
		this.cdaId = cdaId;
	}

	public int getCdaId() {
		return cdaId;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setFestivalsList(List<LoanFestivalMasterDTO> festivalsList) {
		this.festivalsList = festivalsList;
	}

	public List<LoanFestivalMasterDTO> getFestivalsList() {
		return festivalsList;
	}

	public void setRequestedInterestInstalments(int requestedInterestInstalments) {
		this.requestedInterestInstalments = requestedInterestInstalments;
	}

	public int getRequestedInterestInstalments() {
		return requestedInterestInstalments;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setDateOfDrawl(Date dateOfDrawl) {
		this.dateOfDrawl = dateOfDrawl;
	}

	public Date getDateOfDrawl() {
		return dateOfDrawl;
	}

	public void setPrevAdvanceDate(Date prevAdvanceDate) {
		this.prevAdvanceDate = prevAdvanceDate;
	}

	public Date getPrevAdvanceDate() {
		return prevAdvanceDate;
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

	public void setLoanTypeDetailsList(List<LoanDetailsDTO> loanTypeDetailsList) {
		this.loanTypeDetailsList = loanTypeDetailsList;
	}

	public List<LoanDetailsDTO> getLoanTypeDetailsList() {
		return loanTypeDetailsList;
	}

	public void setLoanLeavesDetailsList(List<LoanLeavesMappingDTO> loanLeavesDetailsList) {
		this.loanLeavesDetailsList = loanLeavesDetailsList;
	}

	public List<LoanLeavesMappingDTO> getLoanLeavesDetailsList() {
		return loanLeavesDetailsList;
	}

	public List<LoanAmountDetailsDTO> getLoanAmountDetailsList() {
		return loanAmountDetailsList;
	}

	public void setLoanAmountDetailsList(List<LoanAmountDetailsDTO> loanAmountDetailsList) {
		this.loanAmountDetailsList = loanAmountDetailsList;
	}

	public List<LoanDesigMappingDTO> getLoanDesigMappingList() {
		return loanDesigMappingList;
	}

	public void setLoanDesigMappingList(List<LoanDesigMappingDTO> loanDesigMappingList) {
		this.loanDesigMappingList = loanDesigMappingList;
	}

	public List<LoanAmountGridDTO> getLoanAmountGrid() {
		return loanAmountGrid;
	}

	public void setLoanAmountGrid(List<LoanAmountGridDTO> loanAmountGrid) {
		this.loanAmountGrid = loanAmountGrid;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
