package com.callippus.web.loan.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;

public class LoanLeavesMappingDTO extends CommonDTO {
	private int loanDetailsID;
	private LoanDetailsDTO loanTypeDetails;
	private int leaveTypeID;
	private LeaveTypeDTO leaveTypeDetails;

	public int getLoanDetailsID() {
		return loanDetailsID;
	}

	public void setLoanDetailsID(int loanDetailsID) {
		this.loanDetailsID = loanDetailsID;
	}

	public LoanDetailsDTO getLoanTypeDetails() {
		return loanTypeDetails;
	}

	public void setLoanTypeDetails(LoanDetailsDTO loanTypeDetails) {
		this.loanTypeDetails = loanTypeDetails;
	}

	public int getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(int leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public LeaveTypeDTO getLeaveTypeDetails() {
		return leaveTypeDetails;
	}

	public void setLeaveTypeDetails(LeaveTypeDTO leaveTypeDetails) {
		this.leaveTypeDetails = leaveTypeDetails;
	}

}
