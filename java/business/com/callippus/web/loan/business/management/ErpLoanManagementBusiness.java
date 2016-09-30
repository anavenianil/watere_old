package com.callippus.web.loan.business.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.orgstructure.IOrgStructureDAO;
import com.callippus.web.loan.beans.management.ErpLoanManagementBean;
import com.callippus.web.loan.beans.management.LoanManagementBean;
import com.callippus.web.loan.dao.management.IErpLoanManagementDAO;

@Service
public class ErpLoanManagementBusiness {
	@Autowired
	private IErpLoanManagementDAO erpLoanManagementDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IOrgStructureDAO orgStructureDAO;


	public ErpLoanManagementBean submitLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception {
		try {
			loanBean = erpLoanManagementDAO.submitLoanTypeMasterDetails(loanBean);		
			} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	public ErpLoanManagementBean getLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception {
		try {
			loanBean = erpLoanManagementDAO.getLoanTypeMasterDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	//to delete
	public ErpLoanManagementBean deleteLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception {
		try {
			loanBean = erpLoanManagementDAO.deleteLoanTypeMasterDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	
}