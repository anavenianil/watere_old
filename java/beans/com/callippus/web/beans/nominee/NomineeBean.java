package com.callippus.web.beans.nominee;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;

import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.RequestsDTO;
import com.callippus.web.beans.dto.StateTypeDTO;
import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.employee.EmployeeBean;

public class NomineeBean {
    private List nomineeTypeList;
    private ArrayList<KeyValueDTO> familyMembersList;
    private String percentage;
    private String dateOfNominate;
    private String remarks;
    private String nomineeName;

    private String familyID;
    private ArrayList nomineeList;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String districtId;
    private String stateId;
    private String pincode;
    private String id;
    private String nomineeTypeId;
    private String inContengensyParent;
    private List<StateTypeDTO> stateList;
    private List<DistrictTypeDTO> districtList;
    private String param;
    private String type;
    private String changedSfid;
    private String changedSfidName;
    private String message;
    private String nomineeAddressID;
    private String nomineeID;
    private JSON percentageList;
    private JSON familyMemberInNominee;
    private String nomineeTypeName;
    private String inconsistencyDetails;
    private int inconsistencyParentId;
    private String inContengensyParentName;
    private ArrayList<NomineeBean> displayNomineeList;
    private String creationDate;
    private String lastModifiedDate;
    private int status;
    private String sfid;
    private String changedValues;

    public ArrayList getNomineeList() {
        return nomineeList;
    }

    public void setNomineeList(ArrayList nomineeList) {
        this.nomineeList = nomineeList;
    }

    public ArrayList<NomineeBean> getDisplayNomineeList() {
        return displayNomineeList;
    }

    public void setDisplayNomineeList(ArrayList<NomineeBean> displayNomineeList) {
        this.displayNomineeList = displayNomineeList;
    }

    public int getInconsistencyParentId() {
        return inconsistencyParentId;
    }

    public void setInconsistencyParentId(int inconsistencyParentId) {
        this.inconsistencyParentId = inconsistencyParentId;
    }

    public String getInconsistencyDetails() {
        return inconsistencyDetails;
    }

    public String getInContengensyParentName() {
        return inContengensyParentName;
    }

    public void setInContengensyParentName(String inContengensyParentName) {
        this.inContengensyParentName = inContengensyParentName;
    }

    public void setInconsistencyDetails(String inconsistencyDetails) {
        this.inconsistencyDetails = inconsistencyDetails;
    }

    public String getNomineeTypeName() {
        return nomineeTypeName;
    }

    public void setNomineeTypeName(String nomineeTypeName) {
        this.nomineeTypeName = nomineeTypeName;
    }

    public JSON getFamilyMemberInNominee() {
        return familyMemberInNominee;
    }

    public void setFamilyMemberInNominee(JSON familyMemberInNominee) {
        this.familyMemberInNominee = familyMemberInNominee;
    }

    public JSON getPercentageList() {
        return percentageList;
    }

    public void setPercentageList(JSON percentageList) {
        this.percentageList = percentageList;
    }

    public String getNomineeID() {
        return nomineeID;
    }

    public void setNomineeID(String nomineeID) {
        this.nomineeID = nomineeID;
    }

    public String getNomineeAddressID() {
        return nomineeAddressID;
    }

    public void setNomineeAddressID(String nomineeAddressID) {
        this.nomineeAddressID = nomineeAddressID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChangedSfidName() {
        return changedSfidName;
    }

    public void setChangedSfidName(String changedSfidName) {
        this.changedSfidName = changedSfidName;
    }

    public String getChangedSfid() {
        return changedSfid;
    }

    public void setChangedSfid(String changedSfid) {
        this.changedSfid = changedSfid;
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

    public List<StateTypeDTO> getStateList() {
        return stateList;
    }

    public void setStateList(List<StateTypeDTO> stateList) {
        this.stateList = stateList;
    }

    public List<DistrictTypeDTO> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictTypeDTO> districtList) {
        this.districtList = districtList;
    }

    public List getNomineeTypeList() {
        return nomineeTypeList;
    }

    public void setNomineeTypeList(List nomineeTypeList) {
        this.nomineeTypeList = nomineeTypeList;
    }

    public ArrayList<KeyValueDTO> getFamilyMembersList() {
        return familyMembersList;
    }

    public void setFamilyMembersList(ArrayList<KeyValueDTO> familyMembersList) {
        this.familyMembersList = familyMembersList;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomineeTypeId() {
        return nomineeTypeId;
    }

    public void setNomineeTypeId(String nomineeTypeId) {
        this.nomineeTypeId = nomineeTypeId;
    }

    public String getInContengensyParent() {
        return inContengensyParent;
    }

    public void setInContengensyParent(String inContengensyParent) {
        this.inContengensyParent = inContengensyParent;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getDateOfNominate() {
        return dateOfNominate;
    }

    public void setDateOfNominate(String dateOfNominate) {
        this.dateOfNominate = dateOfNominate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFamilyID() {
        return familyID;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }

    public String getChangedValues() {
        return changedValues;
    }

    public void setChangedValues(String changedValues) {
        this.changedValues = changedValues;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
