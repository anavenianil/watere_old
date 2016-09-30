package com.callippus.web.loan.dao.management;

import com.callippus.web.loan.beans.management.ErpLoanManagementBean;

public interface IErpLoanManagementDAO {


	public ErpLoanManagementBean submitLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception;
	
	public ErpLoanManagementBean getLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception;
	
	public ErpLoanManagementBean deleteLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception;


}
