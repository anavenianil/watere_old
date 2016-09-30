package com.callippus.web.dao.hierarchy;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.EmpRoleMappingDTO;
import com.callippus.web.beans.dto.EmployeeRoleDTO;
import com.callippus.web.beans.dto.HierarchyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.hierarchy.HierarchyBean;

public interface IHierarchyDAO {

    public HierarchyBean getHierarchyLevelList(HierarchyBean hierarchyBean) throws Exception;

    public String createHierarchyLevel(HierarchyBean hierarchyBean) throws Exception;

    public String updateHierarchyLevel(HierarchyBean hierarchyBean) throws Exception;

    public String deleteHierarchyLevel(HierarchyBean hierarchyBean) throws Exception;

    public HierarchyBean getHierarchyNodeList(HierarchyBean hierarchyBean,List<Object> rolelist) throws Exception;

    public ArrayList<HierarchyDTO> getNodeInstanceParentList(HierarchyBean hierarchyBean,List<Object> rolelist) throws Exception;

    public String createRoleInstance(HierarchyBean hierarchyBean) throws Exception;

    public String updateRoleInstance(HierarchyBean hierarchyBean) throws Exception;

    public String deleteHierarchyNode(String nodeID,String instanceType) throws Exception;

    public ArrayList<KeyValueDTO> getInternalDivisionsList(String sfID) throws Exception;

    public ArrayList<KeyValueDTO> getInternalRolesList(String sfID) throws Exception;

    public ArrayList<EmployeeRoleDTO> getInternalStructureTree(String sfID) throws Exception;

    public String checkLevel(HierarchyBean hierarchyBean) throws Exception;

    public String checkNodeLevel(HierarchyBean hierarchyBean) throws Exception;

    public List<OrgInstanceDTO> getRoleInstanceList(HierarchyBean hierarchyBean,List roleList) throws Exception;

    public ArrayList<HierarchyDTO> getSubInstance(String instanceID) throws Exception;

    public String submitOrganizationRoleMapping(HierarchyBean hierarchyBean) throws Exception;

    public String submitInternalRoleMapping(HierarchyBean hierarchyBean) throws Exception;

    public String updateInternalRoleMapping(HierarchyBean hierarchyBean) throws Exception;

    public String deleteInternalEmployee(String id) throws Exception;
    
    public String chekHead(String sfid,String instanceID) throws Exception;
    
    public List getRoleInstances(HierarchyBean hierarchyBean,List roleList)throws Exception;
    
    public String getRoleInstanceValues(String instanceId) throws Exception;
    
    public String checkParentInstanceId(String sfid,String instanceID)throws Exception;
    
    public String deleteHeadMapping(String sfid,String instanceID)throws Exception;
    
    public List getEmployeeMappingDetails(HierarchyBean hierarchyBean,List roleList)throws Exception;
    
    public String saveEmployeeMapping(EmpRoleMappingDTO rolemappingdto,String instanceID) throws Exception; 
    
    public String getParentId(String instanceId) throws Exception;
    
    public String deleteEmployee(String sfid)throws Exception;
    
    public String chekEmployee(String sfid,String instanceID) throws Exception;
    
    public String checkTree(String sfid,String instanceId) throws Exception;
    
    public List<Object> getAllTreeInstances(String sfid) throws Exception;
    
    public List<Object> getRoleLevelList(String sfid) throws Exception;
    
    public String changeParentForSubEmp(String sfid)throws Exception;
    
    public String saveOrgPhysicalInstance(DepartmentsDTO hierarchyBean) throws Exception;
    
    public List<Object> getDepartmentsList(String sfid) throws Exception;
    
    public List<Object> getDepartments()throws Exception;
    
    public String chekDuplicateHead(String sfid, String instanceID) throws Exception;
    
    public ArrayList<KeyValueDTO> getOrgnRolesList(String sfid) throws Exception;
    
    public List<Object> getAllTreeDepartments(String sfid,List roleList) throws Exception;
    
    public String checkUserSpecificConfiguration(String sfid,String roleInstanceId)throws Exception;
    
    public Integer getApplicationRoleId(String sfid,String instanceId)throws Exception;
    
    public String saveApplicationRoleId(ApplicationRoleMappingDTO appRole)throws Exception;
}
