package com.callippus.web.loan.business.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.loan.beans.management.LoanManagementBean;
import com.callippus.web.loan.beans.request.LoanRequestBean;
import com.callippus.web.loan.dao.request.ILoanRequestDAO;

@Service
public class LoanRequestBusiness {
	@Autowired
	private ILoanRequestDAO loanRequestDAO;

	public LoanRequestBean getLoanRequestHomeDetails(LoanRequestBean loanBean) throws Exception {
		try {
			String message=null;
			loanBean = loanRequestDAO.getEmployeeDetails(loanBean);
			
			
			loanBean = loanRequestDAO.getPayDetails(loanBean);
			loanBean = loanRequestDAO.getLoanTypeMaster(loanBean);
			loanBean = loanRequestDAO.getFestivals(loanBean);
			loanBean = loanRequestDAO.getGpfSubTypes(loanBean);
			loanBean = loanRequestDAO.getdeptDetails(loanBean);
			loanBean = loanRequestDAO.getGpfClosingBalanceDetails(loanBean);
			
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	public LoanRequestBean getEmpNotExist(LoanRequestBean loanBean) throws Exception {
		try {
			//String message = loanRequestDAO.getEmpExist(loanBean.getSfidSearchKey().toUpperCase());
			String message = loanRequestDAO.getEmpExist(loanBean.getOfflineSFID().toUpperCase());
			if (message != null && message.equalsIgnoreCase(CPSConstants.YES)) {
				message = loanBean.getOfflineSFID() .toUpperCase();
				if (CPSUtils.compareStrings(CPSConstants.YES, message)) {
					return loanBean;
				}}
			else {
				loanBean.setMessage(CPSConstants.EMPNOTEXISTS);
				
		}
			} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanRequestBean checkConstraints(LoanRequestBean loanBean) throws Exception {
		try {
			loanBean = loanRequestDAO.checkConstraints(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanRequestBean editInstallmentsDetails(LoanRequestBean loanBean) throws Exception {
		try {
			loanBean = loanRequestDAO.editInstallmentsDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanRequestBean getGpfDetails(LoanRequestBean loanBean) throws Exception {
		try {
			loanBean = loanRequestDAO.getGpfDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanRequestBean getLoanTypeGrid(LoanRequestBean loanBean) throws Exception {
		try {
			loanBean = loanRequestDAO.getLoanTypeDetails(loanBean);
			loanBean = loanRequestDAO.getLoanLeavesMapping(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	public LoanRequestBean getLoanAmountListDetails(LoanRequestBean loanBean) throws Exception {
		try {
			loanBean = loanRequestDAO.getLoanAmountList(loanBean);
			loanBean = loanRequestDAO.getLoanDesignationMappings(loanBean);
			loanBean = loanRequestDAO.getLoanAmountGridDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
}
