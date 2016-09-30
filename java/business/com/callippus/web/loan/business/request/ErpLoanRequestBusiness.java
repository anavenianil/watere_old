package com.callippus.web.loan.business.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.loan.beans.request.ErpLoanRequestBean;
import com.callippus.web.loan.beans.request.LoanRequestBean;
import com.callippus.web.loan.dao.request.ErpILoanRequestDAO;

@Service
public class ErpLoanRequestBusiness {
	@Autowired
	private ErpILoanRequestDAO erpLoanRequestDAO;

	public ErpLoanRequestBean getLoanRequestHomeDetails(ErpLoanRequestBean erploanRequestBean) throws Exception {
		try {
			String message=null;
			erploanRequestBean = erpLoanRequestDAO.getEmployeeDetails(erploanRequestBean);
			erploanRequestBean = erpLoanRequestDAO.getPayDetails(erploanRequestBean);
			erploanRequestBean = erpLoanRequestDAO.getdeptDetails(erploanRequestBean);
			erploanRequestBean = erpLoanRequestDAO.getLoanTypeMaster(erploanRequestBean);
			//erploanRequestBean = erpLoanRequestDAO.getGpfClosingBalanceDetails(erploanRequestBean);
			//erploanRequestBean = erpLoanRequestDAO.getLoanTypeMaster(erploanRequestBean);
			//erploanRequestBean = erpLoanRequestDAO.getFestivals(erploanRequestBean);
			//erploanRequestBean = erpLoanRequestDAO.getGpfSubTypes(erploanRequestBean);
			
		} catch (Exception e) {
			throw e;
		}
		return erploanRequestBean;
	}
	public LoanRequestBean getEmpNotExist(LoanRequestBean loanBean) throws Exception {
		try {
			//String message = loanRequestDAO.getEmpExist(loanBean.getSfidSearchKey().toUpperCase());
			String message = erpLoanRequestDAO.getEmpExist(loanBean.getOfflineSFID().toUpperCase());
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

	public ErpLoanRequestBean checkConstraints(ErpLoanRequestBean loanBean) throws Exception {
		try {
			loanBean = erpLoanRequestDAO.checkConstraints(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	/*public ErpLoanRequestBean editInstallmentsDetails(ErpLoanRequestBean loanBean) throws Exception {
		try {
			loanBean = erpLoanRequestDAO.editInstallmentsDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}*/

	/*public ErpLoanRequestBean getGpfDetails(ErpLoanRequestBean loanBean) throws Exception {
		try {
			loanBean = erpLoanRequestDAO.getGpfDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}*/

	/*public ErpLoanRequestBean getLoanTypeGrid(ErpLoanRequestBean loanBean) throws Exception {
		try {
			loanBean = erpLoanRequestDAO.getLoanTypeDetails(loanBean);
			loanBean = erpLoanRequestDAO.getLoanLeavesMapping(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}*/
	
	/*public ErpLoanRequestBean getLoanAmountListDetails(ErpLoanRequestBean loanBean) throws Exception {
		try {
			loanBean = erpLoanRequestDAO.getLoanAmountList(loanBean);
			loanBean = erpLoanRequestDAO.getLoanDesignationMappings(loanBean);
			loanBean = erpLoanRequestDAO.getLoanAmountGridDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}*/
	
	
	
}
