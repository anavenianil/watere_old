package com.callippus.web.dao.hindi;

import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.hindi.HindiIncentiveBean;
import com.callippus.web.hindi.dto.EmpKeyValueDTO;
import com.callippus.web.hindi.dto.ExamDTO;
import com.callippus.web.hindi.dto.HindiCashAwardDTO;
import com.callippus.web.hindi.dto.HindiEmpResultDTO;
import com.callippus.web.hindi.dto.HindiEmployeeDTO;
import com.callippus.web.hindi.dto.HindiExamConfigDTO;
import com.callippus.web.hindi.dto.HindiIncentiveDetailsDTO;
import com.callippus.web.hindi.dto.HindiNominationDTO;

public interface IHindiIncentive {
	
	public String getCurrentRunMonth() throws Exception;
	/*public String saveExamData(HindiExamConfigDTO examDTO) throws Exception;
	public List getExamDetailsList() throws Exception;
	public String deleteExamData(HindiIncentiveBean hindiBean) throws Exception;*/
	public String saveExamData(ExamDTO examDTO) throws Exception;
	@SuppressWarnings("unchecked")
	public List examDuplicateChecklist(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public List<ExamDTO> getExamsList() throws Exception;
	public String deleteExam(HindiIncentiveBean hindiBean) throws Exception;
	
	public List<KeyValueDTO> getSfidList() throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEmployeeList() throws Exception;
	@SuppressWarnings("unchecked")
	public List getEmployeeDetailsList() throws Exception;
	 @SuppressWarnings("unchecked")
	public List getCheckExamsList(HindiIncentiveBean hindiBean) throws Exception;
	public String saveEmployeeData(HindiEmployeeDTO employeeDTO)throws Exception;
	@SuppressWarnings("unchecked")
	public List employeeDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception;
	public void saveNonEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception;
	public void deleteNonEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public void saveEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception;
	public void deleteEligibleEmpExamData(HindiIncentiveBean hindiBean) throws Exception;
	public List getNonEligibleEmpExamList() throws Exception;
	public String deleteEmployeeData(HindiIncentiveBean hindiBean) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCategorylist() throws Exception;
	@SuppressWarnings("unchecked")
	public List getDeselectDesignationList(HindiIncentiveBean hindiBean) throws Exception;
	public String saveExamConfigDetails(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public List getExamConfigDetails() throws Exception;
	@SuppressWarnings("unchecked")
	public List getSelectDesignationList(HindiIncentiveBean hindiBean) throws Exception;
	public String deleteExamConfigDetails(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public List examConfigDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception;
	
	public String saveCashAwardData(HindiCashAwardDTO cashAwardDTO) throws Exception;
	@SuppressWarnings("unchecked")
	public List cashAwardDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public List getCashAwardDetailsList() throws Exception;
	public String deleteCashAwardDetails(HindiIncentiveBean hindiBean) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getDepartmentsList() throws Exception;
	@SuppressWarnings("unchecked")
	public List getNominationList(HindiIncentiveBean hindiBean) throws Exception;
	public String saveNominationList(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public List getSelectedNominationList() throws Exception;
	
	public String saveResultDetails(HindiEmpResultDTO resultDTO) throws Exception;
	@SuppressWarnings("unchecked")
	public List resultDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public List getResultDetails() throws Exception;
	public String deleteResultDetails(HindiIncentiveBean hindiBean) throws Exception;
	@SuppressWarnings("unchecked")
	public List getEligibleExamsList(HindiIncentiveBean hindiBean) throws Exception;
	
	public String saveIncentiveDetails(HindiIncentiveDetailsDTO incentiveDTO) throws Exception;
		
	public List<HindiIncentiveDetailsDTO> getIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception;
	
	public String deleteIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception;
	
	public List<EmpKeyValueDTO> getIncentiveEmpList(HindiIncentiveBean hindiBean) throws Exception;
	
	public String getIncentiveAmount(HindiIncentiveBean hindiBean) throws Exception;
	
	public List<HindiIncentiveDetailsDTO> incentiveDuplicateCheckList(HindiIncentiveBean hindiBean) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getEligibleEmpForIncentives(HindiIncentiveBean hindiBean) throws Exception;
	public List getIncentiveEmpListForFinance(HindiIncentiveBean hindiBean) throws Exception;
	public String savePayHindiIncentiveDetails(HindiIncentiveBean hindiBean) throws Exception;
}
