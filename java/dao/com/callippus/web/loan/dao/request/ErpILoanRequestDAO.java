package com.callippus.web.loan.dao.request;

import com.callippus.web.loan.beans.request.ErpLoanRequestBean;

public interface ErpILoanRequestDAO {
	public ErpLoanRequestBean getEmployeeDetails(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getFestivals(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	public ErpLoanRequestBean getPayDetails(ErpLoanRequestBean erpLoanRequestBean) throws Exception;
	public ErpLoanRequestBean getLoanTypeMaster(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getGpfSubTypes(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	public ErpLoanRequestBean checkConstraints(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getLoanTypeMaster(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean editInstallmentsDetails(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	public ErpLoanRequestBean getdeptDetails(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getGpfDetails(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getGpfClosingBalanceDetails(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getLoanTypeDetails(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getLoanLeavesMapping(ErpLoanRequestBean erpLoanRequestBean) throws Exception;

	//public ErpLoanRequestBean getLoanAmountList(ErpLoanRequestBean erpLoanRequestBean)throws Exception;

	//public ErpLoanRequestBean getLoanDesignationMappings(ErpLoanRequestBean erpLoanRequestBean)throws Exception;

	//public ErpLoanRequestBean getLoanAmountGridDetails(ErpLoanRequestBean erpLoanRequestBean)throws Exception;
	public String getEmpExist(String sfid) throws Exception;
}
