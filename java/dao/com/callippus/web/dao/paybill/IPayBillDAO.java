package com.callippus.web.dao.paybill;

import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.paybill.PayBillBean;
import com.callippus.web.beans.paybill.PayBillMasterBean;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillStatusDTO;

public interface IPayBillDAO {
	
	public String getEmpExist(String sfid) throws Exception;

	public String getEmpPayStopFlag(String sfid) throws Exception;

	public PayBillDTO getEmpPayDeatails(String sfid,String runMonth) throws Exception;
	
	public EmployeeBean getEmployeeDetails(String sfID) throws Exception;

	public PayBillStatusDTO startPayAutoRun(String runMonth,String sfid) throws Exception;
	
	public PayBillStatusDTO getPayBillStatus() throws Exception;

	public PayBillStatusDTO changeStatusToVisible(String runMonth,String sfid) throws Exception;

	public PayBillStatusDTO changeStatusToManual(String runMonth,String sfid) throws Exception;
	
	public List<PayBillDTO> getNegativePayEmployees() throws Exception;
	
	public PayBillDTO showPaySlip(String sfid,String runmonth) throws Exception;
		
	public PayBillStatusDTO startRun(String userId,String runmonth,PayBillBean payBillBean) throws Exception;
	
	public List<PayBillStatusDTO> getPaySlipMonthList()throws Exception;
	
	public List<String> getPaySlipYearList()throws Exception;
	
	public List<String> getPaySlipMonthList(String year)throws Exception;
	
	public PayBillStatusDTO startRegeneratingPayBill(String runMonth, String userId) throws Exception;
	
	public String updateEmpPayBillDetails(PayBillDTO paydto) throws Exception ;
	
	public List<KeyValueDTO> getPayAutoRunList() throws Exception;
	
	public List<KeyValueDTO> getEmpSupplimentaryBillDetails(PayBillBean payBillBean) throws Exception;
	
	public PayBillBean countOfRegularAndSupplementaryForGenPayBill(PayBillBean payBillBean) throws Exception;
	
	public List<String> getMonthListForEditEmployee(String year) throws Exception;
	
	public List<String> getYearListForEditEmployee() throws Exception;
	
	public List<String> getEmpNameList(String empName) throws Exception;
	
	public List<KeyValueDTO> getOTCategoryList() throws Exception;
	public String getEmpPayStopRemarks(String sfid) throws Exception;
	public PayBillBean getRecalcultedPayDetails(PayBillBean payBillBean) throws Exception;
	
	public PayBillStatusDTO CheckPayAutoRun(PayBillBean payBillBean) throws Exception;
	
}
