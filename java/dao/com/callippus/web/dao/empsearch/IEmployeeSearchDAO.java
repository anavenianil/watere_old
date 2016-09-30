package com.callippus.web.dao.empsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;

public interface IEmployeeSearchDAO {
	public ArrayList<EmployeeBean> getEmpDetails(EmployeeBean empbean) throws Exception;

	public EmployeeBean getEmployeeDetails(EmployeeBean emp) throws Exception;

	public EmployeeBean getEmployeeTreeDetails(EmployeeBean empbean) throws Exception;

	public boolean checkDownTree(String searchedSfid, String loginSfid) throws Exception;

	public List<Object> getOrganizationTree(String instanceId) throws Exception;

	public List<HashMap<String, String>> getCustomReportEmpDetails(JSONObject json) throws Exception;

	public List<KeyValueDTO> getHeadList() throws Exception;

	public EmployeeBean empRolesList(EmployeeBean empbean) throws Exception;
	public EmployeeBean empPayList(EmployeeBean empbean) throws Exception ;
	
	public List<String> getEmpNameList(String empbean)throws Exception;
	
	
}
