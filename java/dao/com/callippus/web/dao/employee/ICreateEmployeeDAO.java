package com.callippus.web.dao.employee;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.UserDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;

public interface ICreateEmployeeDAO {

	public String createEmployee(Object employee) throws Exception;

	public String createUser(UserDTO user) throws Exception;

	public String selfFamilyDetails(FamilyBean family) throws Exception;

	public EmployeeBean viewEmployeeDetails(EmployeeBean employee) throws Exception;

	public String saveEditEmployee(EmployeeBean employee) throws Exception;

	public EmployeeBean viewRequestedDetails(String sfid) throws Exception;

	public String checkParentSFID(int officeId) throws Exception;

	public String saveEmpRoleMapping(EmpRoleMappingDTO emprolemapping) throws Exception;

	public List selectedDivisionList(String directorateId) throws Exception;

	public String checkHeadName(String instanceId) throws Exception;

	public int getEmpRoleMapId(String sfid) throws Exception;

	public boolean checkHead(String sfid) throws Exception;

	public String saveApplicationRoleMapping(ApplicationRoleMappingDTO appRoleMap) throws Exception;

	public String getOfficeId(String officeId) throws Exception;

	public FamilyBean CheckSelfFamilyDetails(String sfid) throws Exception;

	public int getEmployeeDetails(String sfid, String columnName) throws Exception;
	
	public String createOtherDBEmployee(EmployeeBean employeeBean)throws Exception;
	
    public String addOtherEmployee(Object employee)throws Exception;
    
    public List<EmployeeBean> getEmployeesList(String nameFilter, int noOfRecords) throws Exception; 
    
   // public List getOrganizationList(EmployeeBean employee) throws Exception;
    
    public String addLeaveExceptionalDetails(String userId, String createdPerson, String creationDate, String lastModifiedPerson, String lastModifiedDate) throws Exception;
}
