package com.callippus.web.loan.dao.request;

import com.callippus.web.loan.beans.request.LoanHBARequestBean;

public interface ILoanHBARequestDAO {

	public LoanHBARequestBean getEmployeeDetails(LoanHBARequestBean loanBean) throws Exception;

	public LoanHBARequestBean getHbaLoanTypes(LoanHBARequestBean loanBean) throws Exception;

	public LoanHBARequestBean checkConstraints(LoanHBARequestBean loanBean)throws Exception;

	public LoanHBARequestBean getPayDetails(LoanHBARequestBean loanBean)throws Exception;

	public LoanHBARequestBean editHBADetails(LoanHBARequestBean loanBean)throws Exception;

	public LoanHBARequestBean getdeptDetails(LoanHBARequestBean loanBean)throws Exception;

}
