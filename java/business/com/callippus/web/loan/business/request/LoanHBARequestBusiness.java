package com.callippus.web.loan.business.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.loan.beans.request.LoanHBARequestBean;
import com.callippus.web.loan.dao.request.ILoanHBARequestDAO;

@Service
public class LoanHBARequestBusiness {
	@Autowired
	private ILoanHBARequestDAO loanRequestDAO;

	public LoanHBARequestBean getLoanHBARequestHomeDetails(
			LoanHBARequestBean loanBean) throws Exception {
		try {
			loanBean = loanRequestDAO.getHbaLoanTypes(loanBean);
			loanBean = loanRequestDAO.getPayDetails(loanBean);
			loanBean = loanRequestDAO.getEmployeeDetails(loanBean);
			loanBean = loanRequestDAO.getdeptDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanHBARequestBean checkConstraints(LoanHBARequestBean loanBean)
			throws Exception {
		try {
			loanBean = loanRequestDAO.checkConstraints(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanHBARequestBean editHBADetails(LoanHBARequestBean loanBean)
			throws Exception {
		try {
			loanBean = loanRequestDAO.editHBADetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
}
