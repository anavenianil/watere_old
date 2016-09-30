package com.callippus.web.leave.dao.admin;

import java.util.List;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.EmploymentDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.TaskHolderDesignationsDTO;
import com.callippus.web.beans.dto.TaskHolderDetailsDTO;
import com.callippus.web.beans.dto.TypeDesigDetailsDTO;
import com.callippus.web.beans.dto.TypeDetailsDTO;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.leave.dto.LeaveBusinessRulesVO;
import com.callippus.web.leave.dto.LeaveManualEntryDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;

public interface ILeaveAdministratorDAO {
	public LeaveAdministratorBean getEmpAvailableLeaves(LeaveAdministratorBean leaveBean) throws Exception;
	
	//added newly 
	public LeaveAdministratorBean getEmpAvailableOrNot(LeaveAdministratorBean leaveBean) throws Exception;
	public LeaveAdministratorBean getEmplyeeLeavesSave(LeaveAdministratorBean leaveBean) throws Exception;

	public LeaveAdministratorBean getSpellsDetails(LeaveAdministratorBean leaveBean) throws Exception;

	public String saveEnterAvailableLeaves(LeaveAdministratorBean leaveBean) throws Exception;
	
	public void removeFromLeaveExceptionalEmployees(String userId, String lastModifiedPerson) throws Exception;

	public List<LeaveManualEntryDTO> getLeaveBalanceList(LeaveAdministratorBean leaveBean) throws Exception;

	public String submitLeaveBalance(LeaveAdministratorBean leaveBean) throws Exception;

	public LeaveAdministratorBean completeLeaveRequests(LeaveAdministratorBean leaveBean) throws Exception;

	public LeaveAdministratorBean getDoPartSearchList(LeaveAdministratorBean leaveBean) throws Exception;

	public List<DoPartDTO> getPublishedDoParts() throws Exception;

	public List<LeaveTypeDTO> getLeaveTypeList() throws Exception;

	public List<LeaveAdministratorBean> getTxnSearchedLeaves(LeaveAdministratorBean leaveBean) throws Exception;

	public List<EmploymentDTO> getLeaveExpList(LeaveAdministratorBean leaveBean) throws Exception;

	public List<EmploymentDTO> getEmployeesList(LeaveAdministratorBean leaveBean) throws Exception;

	public String submitLeaveExceptionalEmp(LeaveAdministratorBean leaveBean) throws Exception;

	public String saveLeaveAdit(LeaveAdministratorBean leaveBean) throws Exception;

	public String updateLeaveCredit(LeaveAdministratorBean leaveBean) throws Exception;

	public void dataEntry() throws Exception;

	public void copyLeaves() throws Exception;
	
	public float getAvailableLeavesList(LeaveAdministratorBean leaveBean)throws Exception;
	
	public void backup2011() throws Exception;
	
	public void updateLeaveAccountBalance() throws Exception;

	public String saveTypeDetails(TypeDetailsDTO detailsDTO) throws Exception;

	public String checkDupliateType(LeaveAdministratorBean leaveBean) throws Exception;

	public String deleteTypeDetails(LeaveAdministratorBean leaveBean) throws Exception;

	public List<CasualityDetailsDTO> getCasualitiesList(LeaveAdministratorBean leaveBean) throws Exception;

	public String checkDupliateCasuality(LeaveAdministratorBean leaveBean) throws Exception;

	public String saveCasualityDetails(CasualityDetailsDTO casualityDetailsDTO) throws Exception;

	public String deleteCasualityDetails(LeaveAdministratorBean leaveBean) throws Exception;

	public List<OrgInstanceDTO> getRoleList() throws Exception;

	public List<TaskHolderDetailsDTO> getTaskHolderList(LeaveAdministratorBean leaveBean) throws Exception;

	public String submitTaskHolderDetails(TaskHolderDetailsDTO holderDetailsDTO) throws Exception;

	public String checkDupliateTaskHolder(LeaveAdministratorBean leaveBean) throws Exception;

	public String deleteTaskHolderDetails(LeaveAdministratorBean leaveBean) throws Exception;

	public List<TypeDesigDetailsDTO> getTypeDesigList(LeaveAdministratorBean leaveBean) throws Exception;

	public String saveTypeDesigDetails(LeaveAdministratorBean leaveBean) throws Exception;

	public String checkDuplicateTypeDesig(LeaveAdministratorBean leaveBean) throws Exception;

	public String deleteTypeDesigDetails(LeaveAdministratorBean leaveBean) throws Exception;

	public List<DesignationDTO> getAssigningDesignations(LeaveAdministratorBean leaveBean) throws Exception;

	public List<TaskHolderDetailsDTO> getTypeTaskHolder(LeaveAdministratorBean leaveBean) throws Exception;

	public List<DesignationDTO> getDelectedDesignations(LeaveAdministratorBean leaveBean) throws Exception;

	public String saveTaskHolderDesignations(LeaveAdministratorBean leaveBean) throws Exception;

	public List<TaskHolderDesignationsDTO> getTaskHolderDesigList(LeaveAdministratorBean leaveBean) throws Exception;

	public String deleteTaskHolderDesigDetails(LeaveAdministratorBean leaveBean) throws Exception;


	public List<DoPartDTO> getReleaseDOPartDetails(LeaveAdministratorBean leaveBean) throws Exception;

	public String saveDoPartPOrtal(LeaveAdministratorBean leaveBean) throws Exception;
	public String dopartIIFreezeOut(LeaveAdministratorBean leaveBean)throws Exception;                     //This is dopartIIFreezeOut functionality

	public List<CasualityDetailsDTO> getLeaveCasualityList(LeaveAdministratorBean leaveBean) throws Exception;

	public List<DoPartDTO> getdoPartCount(LeaveAdministratorBean leaveBean) throws Exception;

	public List<CasualityDetailsDTO> getGazettedCasualityList(LeaveAdministratorBean leaveBean) throws Exception;

	public List<DesignationDTO> getAllAssigningDesignations(LeaveAdministratorBean leaveBean) throws Exception;

	
	//sridhar
	public List<DesignationDTO> getAssigningDesignationsList(LeaveAdministratorBean leaveBean) throws Exception ;
	
	public List<DesignationDTO> getAssignedDesignations(LeaveAdministratorBean leaveBean) throws Exception ;
	
	public List<DesignationDTO> getAssignedDesignations(String gazType) throws Exception ;
	
	public String deleteAssignedTypeDesig(String taskHolderID) throws Exception ;
	
	 // Leave Business Rules
	public List<LeaveBusinessRulesVO> getLeaveBusinessRulesList() throws Exception;
	
	public List<KeyValueDTO> getRequestTypes() throws Exception;
	
	public List<LeaveTypeDTO> getAllLeaveTypes() throws Exception;
	
	public String saveLeaveBusinessRules(LeaveAdministratorBean leaveBean) throws Exception;
	
	public String deleteBusinessRule(int id) throws Exception;
}
