package com.callippus.web.dao.allowances;

import java.util.List;

import com.callippus.web.beans.allowances.AllowancesRequestBean;
import com.callippus.web.beans.dto.AllowanceTypeMasterDTO;
//import com.callippus.web.beans.fpa.FPARequestBean;
import com.callippus.web.beans.employee.EmployeeBean;

public interface IAllowancesRequestDAO {

	public AllowancesRequestBean getEmployeeDetails(AllowancesRequestBean allowancesBean) throws Exception;
		
	public List<AllowanceTypeMasterDTO> getAllowancesList() throws Exception;

	public AllowancesRequestBean getPayDetails(AllowancesRequestBean allowancesBean) throws Exception;
	
	public AllowancesRequestBean getdeptDetails(AllowancesRequestBean allowancesBean)throws Exception;

	/*public AllowancesRequestBean checkConstraints(AllowancesRequestBean allowancesBean)throws Exception;

	public AllowancesRequestBean getFamilyDetails(AllowancesRequestBean allowancesBean)throws Exception;*/

	
}
