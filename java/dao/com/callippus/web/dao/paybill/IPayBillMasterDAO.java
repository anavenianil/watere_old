package com.callippus.web.dao.paybill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.paybill.PayBillMasterBean;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.paybill.dto.PayBillCGEISMasterDTO;
import com.callippus.web.paybill.dto.PayBillCanfinDTO;
import com.callippus.web.paybill.dto.PayBillCcsDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillDearnessAllowanceMasterDTO;
import com.callippus.web.paybill.dto.PayBillDuesDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
import com.callippus.web.paybill.dto.PayBillEmpPaymentDeatilsDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayHindiInceDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;

public interface IPayBillMasterDAO {
	
	public String getCurrentRunMonth() throws Exception;
	
	public PayBillMasterBean submitPayBillCGHSDetails(PayBillMasterBean payBillMasterBean) throws Exception;

	public String deletePayBillMasterRecord(PayBillMasterBean payBillMasterBean) throws Exception;

	public String submitPayBillCGEISDetails(PayBillCGEISMasterDTO payBillCGEISList) throws Exception;

	public String submitPayBillDearNessAllowanceDetails(PayBillDearnessAllowanceMasterDTO payBillDearnessAllowanceMasterDTO) throws Exception;

	public PayBillMasterBean submitPayBillResidentialSecurityDetails(PayBillMasterBean payBillMasterBean) throws Exception;

	public String submitPayBillCcsDetails(ArrayList<PayBillCcsDTO> arrList) throws Exception;

	public int getPresentIDDetails(String sfID) throws Exception;

	public List<KeyValueDTO> getDeductionMasterDeatails() throws Exception;

	public String updateCanfinDetails(PayBillCanfinDTO canfinDTO) throws Exception;

	public String insertEmpCategoryMasterDetails(PayBillEmpCategoryDTO cDTO) throws Exception;

	public String deleteEmpCategoryMasterDetails(PayBillEmpCategoryDTO cDTO) throws Exception;

	public List<PayBillEmpCategoryDTO> getEmpCategoryDetailsList() throws Exception;

	public String getEmpExist(String sfid) throws Exception;

	public List<PayScaleDesignationDTO> getPayScaleDetailsList() throws Exception;

	public PayBillEmpPaymentDeatilsDTO getEmpDetails(String sfid) throws Exception;

	public List<PayBillLoanDTO> getEmpLoanDetails(String sfid) throws Exception;

	public List<LoanTypeMasterDTO> getLoanMasterList() throws Exception;

	public String getEmpLoanExist(PayBillLoanDTO pblDTO) throws Exception;

	public String updateEmpLoanDetails(PayBillLoanDTO pblDTO) throws Exception;

	public List<KeyValueDTO> getQuarterDetails() throws Exception;

	public List<PayBillCanfinDTO> getEmpCCDetails(String sfid) throws Exception;

	public String deleteEmpCCDetails(PayBillCanfinDTO cDTO) throws Exception;

	public List<KeyValueDTO> getAllowanceGroupList(String deductionID) throws Exception;

	public List<KeyValueDTO> getAllowanceSelGroupList(String deductionID) throws Exception;

	public String manageAllowanceConfigurationList(String allowanceType, String pk) throws Exception;

	public String updateEmpDetails(PayBillMasterBean payBillMasterBean) throws Exception;

	public EmployeeBean getEmployeeDetails(String sfID) throws Exception;

	public PayQuarterManagementDTO getEmpQuarterDetails(String sfid) throws Exception;

	public String updateEmpPayDetails(PayBillMasterBean payBillMasterBean) throws Exception;

	public String verfiyCCDetailsExist(String sfid, String deductionType) throws Exception;

	public String verfiyCCEditDetailsExist(int parseInt, int parseInt2) throws Exception;

	public String verfiyLoanEditDetailsExist(int pk, int deductionType) throws Exception;

	public String getEmpEntryDetails(String sfid) throws Exception;

	public List<PayBillDuesDTO> getEmpDuesDetails(String sfid) throws Exception;

	public String updateEmpDues(PayBillDuesDTO payduesDTO) throws Exception;

	public List<KeyValueDTO> getEOLLeaves(String sfid, String fromDate, String toDate, String division) throws Exception;
 // argument  date added by naagendra.v 
	public String updateEmpEolDetails(String sfid, String type,String accessSfid, Date runmonth) throws Exception;
	
	
	public String getPayMastersEffDate() throws Exception;
	
	public Object getOldMasterDTODetails(int id,String type) throws Exception;
	
	public String checkCGHSDTODetails(PayBillMasterBean payBillMasterBean) throws Exception ;
	
    public String checkGPFmember(String sfid) throws Exception ;
    
    public String submitPayHindiIncDetails(PayBillMasterBean payBillMasterBean) throws Exception;
    
    public List<PayHindiInceDTO> getPayHindiInceList() throws Exception;
    
    public String deletePayHindiDetails(int id ,String sfid) throws Exception;
    
    public PayBillMasterBean getEmpCorePayDetails(PayBillMasterBean payBillMasterBean) throws Exception;
    
    public List<KeyValueDTO> getEmployeesNotExistedInPayBill()throws Exception;

	public List checkEmpCategoryDetailsList(PayBillEmpCategoryDTO cDTO)throws Exception;
	
	public String checkUpdateEmpDues(PayBillMasterBean payBillMasterBean) throws Exception;
	
	public List<PayBillMasterBean> getGradePayList(PayBillMasterBean payBillMasterBean) throws Exception;
	
	public String checkEmpCategoryDetailsList(PayBillMasterBean payBillMasterBean) throws Exception;
	
	// Employee Bank Account
	public String submitDetails(PayBillMasterBean payBillMasterBean) throws Exception;
	public PayBillEmpPaymentDeatilsDTO getDetails(String sfid) throws Exception;
	public String employeeName(String sfid) throws Exception;
	//public PayBillMasterBean getRecordDetails(String sfid) throws Exception;

	public List<String> getEmpNameList(String sfid) throws Exception;

	public List<CategoryDTO> getAssessmentTypeList()throws Exception;
	public List<PayBillDTO> getTotalPayDetails(String payRunMonth,
			int categoryId) throws Exception;

	public String saveTOTPayDetails(PayBillMasterBean payBillMasterBean) throws Exception;
	public String getPayEmpExist(String sfid) throws Exception;
	public String  getLabid() throws Exception;
}
