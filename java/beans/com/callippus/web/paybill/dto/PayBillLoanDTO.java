package com.callippus.web.paybill.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;

public class PayBillLoanDTO extends CommonDTO {
	
	
	public int loanType;
	public String sfid;
	public int presentInst;
	public int totalInst;
	public int emi;
	public int totalAmt;
	public String loanDeduType;
	public LoanTypeMasterDTO loanMasterDetails;
	private int outStandAmt;
	private int referenceId;
	private LoanPaymentDTO loanPaymentDetails;
	private int loanRefId;
	private int runId;
	private Date recoveryStartDate;
	private float interestRate;
	
	
	
	public float getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}
	public Date getRecoveryStartDate() {
		return recoveryStartDate;
	}
	public void setRecoveryStartDate(Date recoveryStartDate) {
		this.recoveryStartDate = recoveryStartDate;
	}
	public int getLoanRefId() {
		return loanRefId;
	}
	public void setLoanRefId(int loanRefId) {
		this.loanRefId = loanRefId;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public LoanPaymentDTO getLoanPaymentDetails() {
		return loanPaymentDetails;
	}
	public void setLoanPaymentDetails(LoanPaymentDTO loanPaymentDetails) {
		this.loanPaymentDetails = loanPaymentDetails;
	}
	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
	public int getOutStandAmt() {
		return outStandAmt;
	}
	public void setOutStandAmt(int outStandAmt) {
		this.outStandAmt = outStandAmt;
	}
	public LoanTypeMasterDTO getLoanMasterDetails() {
		return loanMasterDetails;
	}
	public void setLoanMasterDetails(LoanTypeMasterDTO loanMasterDetails) {
		this.loanMasterDetails = loanMasterDetails;
	}
	public int getLoanType() {
		return loanType;
	}
	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
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
	

}
