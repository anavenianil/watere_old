package com.callippus.web.dao.empPayment;

import java.util.List;

import com.callippus.web.beans.EmpPayment.EmployeePaymentBean;
import com.callippus.web.beans.dto.EmpBasicPayHistoryDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.beans.management.PromotionOfflineEntryBean;
import com.callippus.web.promotions.dto.EmpGradePayHistoryDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;

public interface IEmployeePaymentDAO {
	public String insertEmpPaymentDetails(EmployeePaymentBean empPaymentBean) throws Exception;

	public List<EmpPaymentsDTO> getEmpPaymentDetails()throws Exception;
	
	public List<EmpBasicPayHistoryDTO> getEmpBasicPayHistory(String sfid) throws Exception ;
	
	public String updateBasicPayHistory(EmployeePaymentBean empPaymentBean) throws Exception ;
	
	public List<PromotionOfflineEntryBean> getCatwiseDesig(String sfid) throws Exception ;
	
	public String deleteRecord(EmployeePaymentBean empPaymentBean)throws Exception;
	
	public String validateBasicSal(String sfId, Float basicSal , int desigId)throws Exception;
	
	public List<EmployeeBean> validateSFID(String SFID) throws Exception;
	
	public List<PayScaleDesignationDTO> getPayScaleDetailsList()throws Exception;
	
	public String checkDuplicateHistory(EmployeePaymentBean empPaymentBean) throws Exception;

	public EmployeePaymentBean getDetails(EmployeePaymentBean  employeePaymentBean) throws Exception;

	public List<EmpGradePayHistoryDTO> getOfflineEntryList(String sfid) throws Exception;
	
	public String getBasicPay(EmployeePaymentBean employeePaymentBean) throws Exception;
 
	public List<PromoteesEntryDTO> getOfflineEntryList(EmployeePaymentBean employeePaymentBean) throws Exception;
	public String checkDuplicateOfflineEntry(EmployeePaymentBean employeePaymentBean) throws Exception;

	public String saveOfflineEntry(EmployeePaymentBean employeePaymentBean) throws Exception;
	 public String deleteRecordPay(EmployeePaymentBean employeePaymentBean) throws Exception;
	 public String getVarIncr(int gradepay, int varIncrPts) throws Exception;
}
