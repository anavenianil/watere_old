package com.callippus.web.business.allowances;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.allowances.AllowancesRequestBean;
import com.callippus.web.beans.dto.AllowanceTypeMasterDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.fpa.FPARequestBean;
import com.callippus.web.dao.allowances.IAllowancesRequestDAO;
import com.callippus.web.dao.employee.ICreateEmployeeDAO;
import com.callippus.web.dao.fpa.IFPARequestDAO;
import com.callippus.web.loan.beans.request.LoanRequestBean;

@Service
public class AllowancesRequestBusiness {
	@Autowired
	private IAllowancesRequestDAO allowancesRequestDAO;
	@Autowired
	private ICreateEmployeeDAO employeeRequestDAO;

	public AllowancesRequestBean getFPARequestHomeDetails(AllowancesRequestBean allowancesBean)
			throws Exception {
		try {
			allowancesBean = allowancesRequestDAO.getEmployeeDetails(allowancesBean);
			allowancesBean = allowancesRequestDAO.getPayDetails(allowancesBean);
			allowancesBean = allowancesRequestDAO.getdeptDetails(allowancesBean);
			List<EmployeeBean> empsList = employeeRequestDAO.getEmployeesList("",0);
			allowancesBean.setEmpList(empsList);
			List<AllowanceTypeMasterDTO> allowancesList = allowancesRequestDAO.getAllowancesList();
			allowancesBean.setAllowancesList(allowancesList);
		} catch (Exception e) {
			throw e;
		}
		return allowancesBean;
	}
}
