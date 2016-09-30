package com.callippus.web.beans.approles;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.dto.GeneralListDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;

public class ApplicationRolesBean {
    private String param;
    private String type;
    private List designationList;
    private List instanceList;
    private List applicationRolesList;
    private ArrayList<GeneralListDTO> appRolesMapList;
    private String searchedSfid;
    private String designationID;
    private String instanceID;
    private String applicationRoleID;
    private String searchingName;
    private String searchingWith;
    private String selectedValue;
    private String enteredValue;
    private String assigningRole;
    private String checkedValues;
    private List<EmployeeBean> employeesList;
    private String mapSfid;
    private String message;
    private String id;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMapSfid() {
        return mapSfid;
    }

    public void setMapSfid(String mapSfid) {
        this.mapSfid = mapSfid;
    }

    public String getCheckedValues() {
        return checkedValues;
    }

    public List<EmployeeBean> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<EmployeeBean> employeesList) {
        this.employeesList = employeesList;
    }

    public void setCheckedValues(String checkedValues) {
        this.checkedValues = checkedValues;
    }

    public String getAssigningRole() {
        return assigningRole;
    }

    public void setAssigningRole(String assigningRole) {
        this.assigningRole = assigningRole;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getEnteredValue() {
        return enteredValue;
    }

    public void setEnteredValue(String enteredValue) {
        this.enteredValue = enteredValue;
    }

    public String getSearchingName() {
        return searchingName;
    }

    public void setSearchingName(String searchingName) {
        this.searchingName = searchingName;
    }

    public String getSearchingWith() {
        return searchingWith;
    }

    public void setSearchingWith(String searchingWith) {
        this.searchingWith = searchingWith;
    }

    public String getSearchedSfid() {
        return searchedSfid;
    }

    public void setSearchedSfid(String searchedSfid) {
        this.searchedSfid = searchedSfid;
    }

    public String getDesignationID() {
        return designationID;
    }

    public void setDesignationID(String designationID) {
        this.designationID = designationID;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public String getApplicationRoleID() {
        return applicationRoleID;
    }

    public void setApplicationRoleID(String applicationRoleID) {
        this.applicationRoleID = applicationRoleID;
    }

    public String getParam() {
        return param;
    }

    public List getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List designationList) {
        this.designationList = designationList;
    }

    public List getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List instanceList) {
        this.instanceList = instanceList;
    }

    public List getApplicationRolesList() {
        return applicationRolesList;
    }

    public void setApplicationRolesList(List applicationRolesList) {
        this.applicationRolesList = applicationRolesList;
    }

    public ArrayList<GeneralListDTO> getAppRolesMapList() {
        return appRolesMapList;
    }

    public void setAppRolesMapList(ArrayList<GeneralListDTO> appRolesMapList) {
        this.appRolesMapList = appRolesMapList;
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

}
