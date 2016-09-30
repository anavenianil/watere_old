package com.callippus.web.beans.paybill;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.PHTypeDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.paybill.dto.PayBillCGEISMasterDTO;
import com.callippus.web.paybill.dto.PayBillCGHSMasterDTO;
import com.callippus.web.paybill.dto.PayBillCanfinDTO;
import com.callippus.web.paybill.dto.PayBillDearnessAllowanceMasterDTO;
import com.callippus.web.paybill.dto.PayBillDuesDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
import com.callippus.web.paybill.dto.PayBillGroupMasterDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;
import com.callippus.web.paybill.dto.PayBillResidentialSecurityMasterDTO;
import com.callippus.web.paybill.dto.PayHindiInceDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;

public class PayBillMasterBean {

	private String pk;
	private String param;
	private String message;
	private String result;
	private String type;
	private String sfID;
	private List<PayBillCGHSMasterDTO> cghsMasterDetails;
	private List<PayBillCGEISMasterDTO> cgeisMasterDetails;
	private List<PayBillDearnessAllowanceMasterDTO> dearNessAllowanceMasterDetails;
	private List<PayBillResidentialSecurityMasterDTO> residentialSecurityMasterDeatails;
	private List<PayBillGroupMasterDTO> payBillGroupMasterList;
	private List<PayBillQuartersTypeMasterDTO> payBillQuartersTypeMasterList;
	private String gradePay;
	private String allowanceAmount;
	private String nodeID;
	private Date groupInsuranceDate;
	private String groupName;
	private String groupId;
	private String beforeMember;
	private String afterMember;
	private String daValue;
	private Date daDate;
	private String quarterTypeId;
	private String amount;
	private MultipartFile xslFile;
	private List<KeyValueDTO> coinCutMasterDetails;
	private String deductionID;
	private String empID;
	private String inst;
	private String effDate;
	private String categoryName;
	private List<PayBillEmpCategoryDTO> empCategroyMasterList;
	private List<PayScaleDesignationDTO> payscaleList;
	private List<DesignationDTO> designationList;
	private String sfidSearchKey;
	private List<LoanTypeMasterDTO> loanMasterList;
	private float interestRate;

	public int categoryID;
	public int basicPay;
	public int gPay;
	public int twoAddIncr;
	public String tptFlag;
	public int varIncrPts;
	public int fpaGradePay;
	public int gpfSubAmt;
	public int cgeisGroupID;
	public String cgeisMemFlag;
	public int ccsSubAmt;
	public String ccsNo;
	public int electricityBillAmt;
	public String payStopFlag;
	public int incomeTaxAmt;
	public String panCardNo;
	public String gpfFlag;
	public String quartersFlag;
	public String ccsMemFlag;
	public String gpfAccountNo;
	public String fpaFlag;
	public String bankFlag;
	public String bankAccNo;
	public String bankBranch;
	public String quarterTypeID;
	public String quarterNo;
	public List<PayBillLoanDTO> empLoanList;
	public int loanType;
	public int presentInst;
	public int totalInst;
	public int emi;
	public int totalAmt;
	public String loanDeduType;
	public String branchCode;
	public List<KeyValueDTO> QuarterDetailsList;
	public String allowanceID;
	public List<PayBillCanfinDTO> empCCList;
	public String fromID;
	public String toID;
	public List<KeyValueDTO> allowanceGroupList;
	public List<KeyValueDTO> allowanceSelGroupList;
	public String remarks;
	public List<PHTypeDTO> handicapList;
	public String dojGovt;
	public String dob;
	public String handicap;
	public String gender;
	public String designationId;
	public String payscaleId;
	public String divisionName;
	public String nameInServiceBook;
	public String designationName;
	public List<PayBillDuesDTO> empDuesList;
	public String startDate;
	public String endDate;
	public List<KeyValueDTO> empEolList;
	
	private long year;
	private String flowname;
	private String from;
	private String to;
	
	
	private String portFromDate;
	private String portToDate;
	private String outStandAmt;
	private List<PayHindiInceDTO> hindiList;
	private String member;
	private String cghsFlag;
	private List<KeyValueDTO> empList;
	private String referenceId;
	private String loanRefId;
	private String runId;
	private Date recoveryStartDate;
	private String washAllwFlag;
	private String basicId;
	private String gradeId;
    private String payDesignationId;
    
    private int payOrderNo;
    private String retirementDate;
    private String seniorityDate;
    private String payRunMonth;
    

    private String dvDate;//These three properties are newly enabled
    private int dvNo;
    private String mainPayList;
    private String payStopRemarks;
    private String workLocation;
    private String gpfRange;
    private int reservation;
   //naagendra.v
    private Date runmonth;
    

    public Date getRunmonth() {
		return runmonth;
	}

	public void setRunmonth(Date runmonth) {
		this.runmonth = runmonth;
	}

	public String getGpfRange() {
		return gpfRange;
	}

	public void setGpfRange(String gpfRange) {
		this.gpfRange = gpfRange;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public String getPayStopRemarks() {
		return payStopRemarks;
	}

	public void setPayStopRemarks(String payStopRemarks) {
		this.payStopRemarks = payStopRemarks;
	}

	public String getDvDate() {
		return dvDate;
	}

	public void setDvDate(String dvDate) {
		this.dvDate = dvDate;
	}

	public int getDvNo() {
		return dvNo;
	}

	public void setDvNo(int dvNo) {
		this.dvNo = dvNo;
	}

	public String getMainPayList() {
		return mainPayList;
	}

	public void setMainPayList(String mainPayList) {
		this.mainPayList = mainPayList;
	}

	private String payBillType;
    private String employeeName;


    
    private List<PayBillMasterBean> details;
        
    public List<PayBillMasterBean> getDetails() {
		return details;
	}
   
	public void setDetails(List<PayBillMasterBean> details) {
		this.details = details;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPayRunMonth() {
		return payRunMonth;
	}

	public void setPayRunMonth(String payRunMonth) {
		this.payRunMonth = payRunMonth;
	}

	public String getSeniorityDate() {
		return seniorityDate;
	}

	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}

	public String getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}

	private List<PayBillMasterBean> gradePayList;
	
	
	public List<PayBillMasterBean> getGradePayList() {
		return gradePayList;
	}

	public void setGradePayList(List<PayBillMasterBean> gradePayList) {
		this.gradePayList = gradePayList;
	}

	public String getPayDesignationId() {
		return payDesignationId;
	}

	public void setPayDesignationId(String payDesignationId) {
		this.payDesignationId = payDesignationId;
	}

	public String getBasicId() {
		return basicId;
	}

	public void setBasicId(String basicId) {
		this.basicId = basicId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getWashAllwFlag() {
		return washAllwFlag;
	}

	public void setWashAllwFlag(String washAllwFlag) {
		this.washAllwFlag = washAllwFlag;
	}

	public Date getRecoveryStartDate() {
		return recoveryStartDate;
	}

	public void setRecoveryStartDate(Date recoveryStartDate) {
		this.recoveryStartDate = recoveryStartDate;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getLoanRefId() {
		return loanRefId;
	}

	public void setLoanRefId(String loanRefId) {
		this.loanRefId = loanRefId;
	}

	public List<KeyValueDTO> getEmpList() {
		return empList;
	}

	public void setEmpList(List<KeyValueDTO> empList) {
		this.empList = empList;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getCghsFlag() {
		return cghsFlag;
	}

	public void setCghsFlag(String cghsFlag) {
		this.cghsFlag = cghsFlag;
	}

	public List<PayHindiInceDTO> getHindiList() {
		return hindiList;
	}

	public void setHindiList(List<PayHindiInceDTO> hindiList) {
		this.hindiList = hindiList;
	}

	public String getOutStandAmt() {
		return outStandAmt;
	}

	public void setOutStandAmt(String outStandAmt) {
		this.outStandAmt = outStandAmt;
	}

	public String getPortFromDate() {
		return portFromDate;
	}

	public void setPortFromDate(String portFromDate) {
		this.portFromDate = portFromDate;
	}

	public String getPortToDate() {
		return portToDate;
	}

	public void setPortToDate(String portToDate) {
		this.portToDate = portToDate;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public List<KeyValueDTO> getEmpEolList() {
		return empEolList;
	}

	public void setEmpEolList(List<KeyValueDTO> empEolList) {
		this.empEolList = empEolList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<PayBillDuesDTO> getEmpDuesList() {
		return empDuesList;
	}

	public void setEmpDuesList(List<PayBillDuesDTO> empDuesList) {
		this.empDuesList = empDuesList;
	}

	public String getDojGovt() {
		return dojGovt;
	}

	public void setDojGovt(String dojGovt) {
		this.dojGovt = dojGovt;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getHandicap() {
		return handicap;
	}

	public void setHandicap(String handicap) {
		this.handicap = handicap;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getPayscaleId() {
		return payscaleId;
	}

	public void setPayscaleId(String payscaleId) {
		this.payscaleId = payscaleId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getNameInServiceBook() {
		return nameInServiceBook;
	}

	public void setNameInServiceBook(String nameInServiceBook) {
		this.nameInServiceBook = nameInServiceBook;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public List<PHTypeDTO> getHandicapList() {
		return handicapList;
	}

	public void setHandicapList(List<PHTypeDTO> handicapList) {
		this.handicapList = handicapList;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<KeyValueDTO> getAllowanceGroupList() {
		return allowanceGroupList;
	}

	public void setAllowanceGroupList(List<KeyValueDTO> allowanceGroupList) {
		this.allowanceGroupList = allowanceGroupList;
	}

	public List<KeyValueDTO> getAllowanceSelGroupList() {
		return allowanceSelGroupList;
	}

	public void setAllowanceSelGroupList(List<KeyValueDTO> allowanceSelGroupList) {
		this.allowanceSelGroupList = allowanceSelGroupList;
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

	public List<PayBillCanfinDTO> getEmpCCList() {
		return empCCList;
	}

	public void setEmpCCList(List<PayBillCanfinDTO> empCCList) {
		this.empCCList = empCCList;
	}

	public String getAllowanceID() {
		return allowanceID;
	}

	public void setAllowanceID(String allowanceID) {
		this.allowanceID = allowanceID;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<KeyValueDTO> getQuarterDetailsList() {
		return QuarterDetailsList;
	}

	public void setQuarterDetailsList(List<KeyValueDTO> quarterDetailsList) {
		QuarterDetailsList = quarterDetailsList;
	}

	public String getQuarterTypeID() {
		return quarterTypeID;
	}

	public void setQuarterTypeID(String quarterTypeID) {
		this.quarterTypeID = quarterTypeID;
	}

	public String getQuarterNo() {
		return quarterNo;
	}

	public void setQuarterNo(String quarterNo) {
		this.quarterNo = quarterNo;
	}

	public String getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(String bankFlag) {
		this.bankFlag = bankFlag;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getFpaFlag() {
		return fpaFlag;
	}

	public void setFpaFlag(String fpaFlag) {
		this.fpaFlag = fpaFlag;
	}

	public String getGpfAccountNo() {
		return gpfAccountNo;
	}

	public void setGpfAccountNo(String gpfAccountNo) {
		this.gpfAccountNo = gpfAccountNo;
	}

	public String getCcsMemFlag() {
		return ccsMemFlag;
	}

	public void setCcsMemFlag(String ccsMemFlag) {
		this.ccsMemFlag = ccsMemFlag;
	}

	public List<PayBillLoanDTO> getEmpLoanList() {
		return empLoanList;
	}

	public int getLoanType() {
		return loanType;
	}

	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}

	public int getPresentInst() {
		return presentInst;
	}

	public void setPresentInst(int presentInst) {
		this.presentInst = presentInst;
	}

	public int getTotalInst() {
		return totalInst;
	}

	public void setTotalInst(int totalInst) {
		this.totalInst = totalInst;
	}

	public int getEmi() {
		return emi;
	}

	public void setEmi(int emi) {
		this.emi = emi;
	}

	public int getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getLoanDeduType() {
		return loanDeduType;
	}

	public void setLoanDeduType(String loanDeduType) {
		this.loanDeduType = loanDeduType;
	}

	public void setEmpLoanList(List<PayBillLoanDTO> empLoanList) {
		this.empLoanList = empLoanList;
	}

	public String getQuartersFlag() {
		return quartersFlag;
	}

	public void setQuartersFlag(String quartersFlag) {
		this.quartersFlag = quartersFlag;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(int basicPay) {
		this.basicPay = basicPay;
	}

	public int getgPay() {
		return gPay;
	}

	public void setgPay(int gPay) {
		this.gPay = gPay;
	}

	public int getTwoAddIncr() {
		return twoAddIncr;
	}

	public void setTwoAddIncr(int twoAddIncr) {
		this.twoAddIncr = twoAddIncr;
	}

	public String getTptFlag() {
		return tptFlag;
	}

	public void setTptFlag(String tptFlag) {
		this.tptFlag = tptFlag;
	}

	public int getVarIncrPts() {
		return varIncrPts;
	}

	public void setVarIncrPts(int varIncrPts) {
		this.varIncrPts = varIncrPts;
	}

	public int getFpaGradePay() {
		return fpaGradePay;
	}

	public void setFpaGradePay(int fpaGradePay) {
		this.fpaGradePay = fpaGradePay;
	}

	public int getGpfSubAmt() {
		return gpfSubAmt;
	}

	public void setGpfSubAmt(int gpfSubAmt) {
		this.gpfSubAmt = gpfSubAmt;
	}

	public int getCgeisGroupID() {
		return cgeisGroupID;
	}

	public void setCgeisGroupID(int cgeisGroupID) {
		this.cgeisGroupID = cgeisGroupID;
	}

	public String getCgeisMemFlag() {
		return cgeisMemFlag;
	}

	public void setCgeisMemFlag(String cgeisMemFlag) {
		this.cgeisMemFlag = cgeisMemFlag;
	}

	public int getCcsSubAmt() {
		return ccsSubAmt;
	}

	public void setCcsSubAmt(int ccsSubAmt) {
		this.ccsSubAmt = ccsSubAmt;
	}

	public String getCcsNo() {
		return ccsNo;
	}

	public void setCcsNo(String ccsNo) {
		this.ccsNo = ccsNo;
	}

	public int getElectricityBillAmt() {
		return electricityBillAmt;
	}

	public void setElectricityBillAmt(int electricityBillAmt) {
		this.electricityBillAmt = electricityBillAmt;
	}

	public String getPayStopFlag() {
		return payStopFlag;
	}

	public void setPayStopFlag(String payStopFlag) {
		this.payStopFlag = payStopFlag;
	}

	public int getIncomeTaxAmt() {
		return incomeTaxAmt;
	}

	public void setIncomeTaxAmt(int incomeTaxAmt) {
		this.incomeTaxAmt = incomeTaxAmt;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getGpfFlag() {
		return gpfFlag;
	}

	public void setGpfFlag(String gpfFlag) {
		this.gpfFlag = gpfFlag;
	}

	public String getSfidSearchKey() {
		return sfidSearchKey;
	}

	public void setSfidSearchKey(String sfidSearchKey) {
		this.sfidSearchKey = sfidSearchKey;
	}

	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public List<PayScaleDesignationDTO> getPayscaleList() {
		return payscaleList;
	}

	public void setPayscaleList(List<PayScaleDesignationDTO> payscaleList) {
		this.payscaleList = payscaleList;
	}

	public List<PayBillEmpCategoryDTO> getEmpCategroyMasterList() {
		return empCategroyMasterList;
	}

	public void setEmpCategroyMasterList(List<PayBillEmpCategoryDTO> empCategroyMasterList) {
		this.empCategroyMasterList = empCategroyMasterList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getInst() {
		return inst;
	}

	public void setInst(String inst) {
		this.inst = inst;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getDeductionID() {
		return deductionID;
	}

	public void setDeductionID(String deductionID) {
		this.deductionID = deductionID;
	}

	public List<KeyValueDTO> getCoinCutMasterDetails() {
		return coinCutMasterDetails;
	}

	public void setCoinCutMasterDetails(List<KeyValueDTO> coinCutMasterDetails) {
		this.coinCutMasterDetails = coinCutMasterDetails;
	}

	public MultipartFile getXslFile() {
		return xslFile;
	}

	public void setXslFile(MultipartFile xslFile) {
		this.xslFile = xslFile;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getQuarterTypeId() {
		return quarterTypeId;
	}

	public void setQuarterTypeId(String quarterTypeId) {
		this.quarterTypeId = quarterTypeId;
	}

	public List<PayBillQuartersTypeMasterDTO> getPayBillQuartersTypeMasterList() {
		return payBillQuartersTypeMasterList;
	}

	public void setPayBillQuartersTypeMasterList(List<PayBillQuartersTypeMasterDTO> payBillQuartersTypeMasterList) {
		this.payBillQuartersTypeMasterList = payBillQuartersTypeMasterList;
	}

	public List<PayBillResidentialSecurityMasterDTO> getResidentialSecurityMasterDeatails() {
		return residentialSecurityMasterDeatails;
	}

	public void setResidentialSecurityMasterDeatails(List<PayBillResidentialSecurityMasterDTO> residentialSecurityMasterDeatails) {
		this.residentialSecurityMasterDeatails = residentialSecurityMasterDeatails;
	}

	public Date getDaDate() {
		return daDate;
	}

	public void setDaDate(Date daDate) {
		this.daDate = daDate;
	}

	public String getDaValue() {
		return daValue;
	}

	public void setDaValue(String daValue) {
		this.daValue = daValue;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<PayBillDearnessAllowanceMasterDTO> getDearNessAllowanceMasterDetails() {
		return dearNessAllowanceMasterDetails;
	}

	public void setDearNessAllowanceMasterDetails(List<PayBillDearnessAllowanceMasterDTO> dearNessAllowanceMasterDetails) {
		this.dearNessAllowanceMasterDetails = dearNessAllowanceMasterDetails;
	}

	public List<PayBillGroupMasterDTO> getPayBillGroupMasterList() {
		return payBillGroupMasterList;
	}

	public void setPayBillGroupMasterList(List<PayBillGroupMasterDTO> payBillGroupMasterList) {
		this.payBillGroupMasterList = payBillGroupMasterList;
	}

	public Date getGroupInsuranceDate() {
		return groupInsuranceDate;
	}

	public void setGroupInsuranceDate(Date groupInsuranceDate) {
		this.groupInsuranceDate = groupInsuranceDate;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBeforeMember() {
		return beforeMember;
	}

	public void setBeforeMember(String beforeMember) {
		this.beforeMember = beforeMember;
	}

	public String getAfterMember() {
		return afterMember;
	}

	public void setAfterMember(String afterMember) {
		this.afterMember = afterMember;
	}

	public List<PayBillCGEISMasterDTO> getCgeisMasterDetails() {
		return cgeisMasterDetails;
	}

	public void setCgeisMasterDetails(List<PayBillCGEISMasterDTO> cgeisMasterDetails) {
		this.cgeisMasterDetails = cgeisMasterDetails;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public String getAllowanceAmount() {
		return allowanceAmount;
	}

	public void setAllowanceAmount(String allowanceAmount) {
		this.allowanceAmount = allowanceAmount;
	}

	public List<PayBillCGHSMasterDTO> getCghsMasterDetails() {
		return cghsMasterDetails;
	}

	public void setCghsMasterDetails(List<PayBillCGHSMasterDTO> cghsMasterDetails) {
		this.cghsMasterDetails = cghsMasterDetails;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<LoanTypeMasterDTO> getLoanMasterList() {
		return loanMasterList;
	}

	public void setLoanMasterList(List<LoanTypeMasterDTO> loanMasterList) {
		this.loanMasterList = loanMasterList;
	}
	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public int getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(int payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	public String getPayBillType() {
		return payBillType;
	}

	public void setPayBillType(String payBillType) {
		this.payBillType = payBillType;
	}

	public int getReservation() {
		return reservation;
	}

	public void setReservation(int reservation) {
		this.reservation = reservation;
	}

	
	

}
