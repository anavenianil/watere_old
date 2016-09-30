package com.callippus.web.dao.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.AllowancesDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.dto.StateTypeDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.DoPartBean;
import com.callippus.web.beans.schedulereports.ScheduleReportsBean;
import com.callippus.web.leave.beans.request.LeaveWaterApplicationBean;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.MaterialMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.UomDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;

public interface IComonObjectDAO {
	
	
	//added by bkr
	public List getAvailLeaveData(String beanName,LeaveWaterApplicationBean leaveWaterBean) throws Exception;

	
	public List<EmployeeBean> getEnabledEmployeeList(EmployeeBean employee)throws Exception;
	
	public List<EmployeeBean> getDisabledEmployeeList(EmployeeBean employee)throws Exception;
	
	public int getID(String tableName)throws Exception;
	
	public List<OrganizationsDTO> getOrganizations(EmployeeBean employee) throws Exception;
	
	public List<OrganizationsDTO> getOrganizations() throws Exception;
	
	public List getMasterData(String beanName) throws Exception;

	public int getTableID(String beanName, String type) throws Exception;

	public void updateTableID(String tableName, String sfid) throws Exception;

	public List getDirectorateList() throws Exception;

	public List getDivisionList() throws Exception;

	public List<StateTypeDTO> getStateList() throws Exception;
	
	public List<AllowancesDTO> getAllowancesList()  throws Exception;

	public List<DistrictTypeDTO> getDistrictList() throws Exception;

	public String checkEmployee(String sfID) throws Exception;

	public ArrayList<KeyValueDTO> getSubOrdinatesList(String sfid) throws Exception;

	public String deleteCheckMaster(String tableName, String columnName, String columnValue, String status) throws Exception;

	public List getOfficeList() throws Exception;

	public List<YearTypeDTO> getYearList() throws Exception;

	public List<EmployeeBean> getSubordinateList(String id) throws Exception;

	public List<KeyValueDTO> getInstanceList(String sfid) throws Exception;

	public List checkingChangedSFID(String sfid) throws Exception;

	public List<CategoryDTO> getSubCategoryList() throws Exception;

	public String getConfigurationValue(String name) throws Exception;
	
	public String setConfigurationValue(String name,String value) throws Exception;

	public List<EmployeeBean> getEmployeesList() throws Exception;

	public String getHierarchyLevel(String sfid) throws Exception;

	public String getCreationDate(String id, String sfid, String tableName) throws Exception;

	public EmployeeBean getEmployeeOfficeId(String sfid) throws Exception;

	public InventoryMasterDTO getInventoryHolderDetails(String inventoryNo, String sfid, String orgRoleId) throws Exception;

	public List<KeyValueDTO> getSfidList() throws Exception;

	public List<Object> getOrgDivisionList() throws Exception;

	public List<Object> getFundTypeList() throws Exception;

	public List<MaterialMasterDTO> searchValues(DemandMasterDTO demand) throws Exception;

	public List<UomDTO> getUomTypes() throws Exception;

	public List<InventoryMasterDTO> getInventoryNumsList(String invHolderSfid) throws Exception;

	public List<InventoryMasterDTO> getInventoryNo() throws Exception;

	// public List<MaterialMasterDTO> getMaterialCode()throws Exception;

	public String checkDuplicate(String tableName, String columnName, String columnValue, String editID) throws Exception;

	public DoPartBean getDoPartID(String doPartDate, String doPartNo, String gazType, String createdBy) throws Exception;

	public ArrayList<CategoryDTO> getCategoryList() throws Exception;
	
	public Date getPaybillRunmonth() throws Exception;
	
	public String saveObject(Object obj) throws Exception;

	public String chageUserPassword(EmployeeBean employee) throws Exception;
	
	public String manageEmpStatus(EmployeeBean employee,String sfid)throws Exception;
	//public String manageCghsUploads(WorkFlowMappingBean workBeanMap)throws Exception;
	
	public String getSequenceValue(String srqName) throws Exception;
	
	public String getSequenceCurrentValue(String srqName) throws Exception;
	
	public List<PayBillEmpCategoryDTO> getListOfCategoryDetails(ScheduleReportsBean scheduleReportsBean) throws Exception;
	public List<KeyValueDTO> getScheduleTypes() throws Exception;
	public int getWorkingLocation(String sfid) throws Exception;
}
