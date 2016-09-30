package com.callippus.web.beans.requests;

public class NomineeRequestBean extends RequestBean {

    private String txnID;
    private String nomineeID;
    private String nomineeTypeId;
    private String familyID;
    private String nomineeName;
    private String percentage;
    private String dateOfNominate;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String stateId;
    private String districtId;
    private String pincode;
    private String inconsistencyDetails;
    
    
    public String getInconsistencyDetails() {
        return inconsistencyDetails;
    }

    public void setInconsistencyDetails(String inconsistencyDetails) {
        this.inconsistencyDetails = inconsistencyDetails;
    }

    public String getTxnID() {
        return txnID;
    }

    public void setTxnID(String txnID) {
        this.txnID = txnID;
    }

    public String getNomineeID() {
        return nomineeID;
    }

    public void setNomineeID(String nomineeID) {
        this.nomineeID = nomineeID;
    }

    public String getNomineeTypeId() {
        return nomineeTypeId;
    }

    public void setNomineeTypeId(String nomineeTypeId) {
        this.nomineeTypeId = nomineeTypeId;
    }

    public String getFamilyID() {
        return familyID;
    }

    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
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

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}
