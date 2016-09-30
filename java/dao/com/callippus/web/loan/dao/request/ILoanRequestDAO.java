package com.callippus.web.loan.dao.request;

import com.callippus.web.loan.beans.request.LoanRequestBean;

public interface ILoanRequestDAO {
	public LoanRequestBean getEmployeeDetails(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getFestivals(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getPayDetails(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getGpfSubTypes(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean checkConstraints(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getLoanTypeMaster(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean editInstallmentsDetails(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getdeptDetails(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getGpfDetails(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getGpfClosingBalanceDetails(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getLoanTypeDetails(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getLoanLeavesMapping(LoanRequestBean loanBean) throws Exception;

	public LoanRequestBean getLoanAmountList(LoanRequestBean loanBean)throws Exception;

	public LoanRequestBean getLoanDesignationMappings(LoanRequestBean loanBean)throws Exception;

	public LoanRequestBean getLoanAmountGridDetails(LoanRequestBean loanBean)throws Exception;
	public String getEmpExist(String sfid) throws Exception;
}
