package com.callippus.web.beans.menulinks;

import java.util.List;

import com.callippus.web.beans.dto.ApplicationRolesDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.MenuLinksDTO;
import com.callippus.web.beans.dto.RequestRolesMappingDTO;
import com.callippus.web.beans.dto.RequestWorkDTO;

public class MenuLinksBean {
	
	private String param;
	private String type;
	private List<DesignationDTO> designationList;
	private List<DesignationDTO> selectedMenuLinksList;
	private List<DesignationDTO> deselectedMenuLinksList;
	private String designationId;
	private String menuLinksId;
	private String id;
    private int uid;
	private String designation;
	private List<ApplicationRolesDTO> applicationRolesList;
	private String applicationRole;
	private String selectedLinks;
	private List<MenuLinksDTO> descriptionList;
	private List<MenuLinksBean> menuLinksList;
	private String parentId;
	private String message;
	private String parentName;
	private int status;
	private String className;
	private String parent;
    private String pk;
    private String controllerClass;
    private String keyName;
    private String linkName;
    private String fromID;
    private String Sfid;
    private List<RequestRolesMappingDTO> SelectedRequestList;
    private List<RequestWorkDTO> DeSelectedRequestList;
    public List<RequestWorkDTO> getDeSelectedRequestList() {
		return DeSelectedRequestList;
	}

	public void setDeSelectedRequestList(List<RequestWorkDTO> deSelectedRequestList) {
		DeSelectedRequestList = deSelectedRequestList;
	}

	private String applicationRoleId;
    private String RequestTypes;
    private List<RequestWorkDTO> RequestTypesList;

	public List<RequestWorkDTO> getRequestTypesList() {
		return RequestTypesList;
	}

	public void setRequestTypesList(List<RequestWorkDTO> requestTypesList) {
		RequestTypesList = requestTypesList;
	}

	public String getRequestTypes() {
		return RequestTypes;
	}

	public void setRequestTypes(String requestTypes) {
		RequestTypes = requestTypes;
	}

	public String getApplicationRoleId() {
		return applicationRoleId;
	}

	public void setApplicationRoleId(String applicationRoleId) {
		this.applicationRoleId = applicationRoleId;
	}

	

	public void setSelectedRequestList1(List<RequestRolesMappingDTO> list) {
		SelectedRequestList = list;
	}

	public String getSfid() {
		return Sfid;
	}

	public void setSfid(String sfid) {
		Sfid = sfid;
	}

	public String getFromID() {
		return fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public String getToID() {
		return toID;
	}

	public void setToID(String toID) {
		this.toID = toID;
	}

	private String toID;
    
	public String getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(String controllerClass) {
		this.controllerClass = controllerClass;
	}
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<MenuLinksBean> getMenuLinksList() {
		return menuLinksList;
	}

	public void setMenuLinksList(List<MenuLinksBean> menuLinksList) {
		this.menuLinksList = menuLinksList;
	}
	
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	public List<MenuLinksDTO> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<MenuLinksDTO> descriptionList) {
		this.descriptionList = descriptionList;
	}
	

	public String getSelectedLinks() {
		return selectedLinks;
	}

	public void setSelectedLinks(String selectedLinks) {
		this.selectedLinks = selectedLinks;
	}

	public String getApplicationRole() {
		return applicationRole;
	}

	public void setApplicationRole(String applicationRole) {
		this.applicationRole = applicationRole;
	}

	public List<ApplicationRolesDTO> getApplicationRolesList() {
		return applicationRolesList;
	}

	public void setApplicationRolesList(List<ApplicationRolesDTO> applicationRolesList) {
		this.applicationRolesList = applicationRolesList;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getMenuLinksId() {
		return menuLinksId;
	}

	public void setMenuLinksId(String menuLinksId) {
		this.menuLinksId = menuLinksId;
	}

	public List<DesignationDTO> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<DesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public List<DesignationDTO> getSelectedMenuLinksList() {
		return selectedMenuLinksList;
	}

	public void setSelectedMenuLinksList(List<DesignationDTO> selectedMenuLinksList) {
		this.selectedMenuLinksList = selectedMenuLinksList;
	}

	public List<DesignationDTO> getDeselectedMenuLinksList() {
		return deselectedMenuLinksList;
	}

	public void setDeselectedMenuLinksList(List<DesignationDTO> deselectedMenuLinksList) {
		this.deselectedMenuLinksList = deselectedMenuLinksList;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<RequestRolesMappingDTO> getSelectedRequestList() {
		return SelectedRequestList;
	}

	public void setSelectedRequestList(
			List<RequestRolesMappingDTO> selectedRequestList) {
		SelectedRequestList = selectedRequestList;
	}

	
}
