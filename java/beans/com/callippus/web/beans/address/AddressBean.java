package com.callippus.web.beans.address;

import java.util.List;

import net.sf.json.JSON;

import com.callippus.web.beans.common.ErpBean;
import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.StateTypeDTO;

public class AddressBean extends ErpBean {
    private String id;
    private List addressList;
    private List addressTypeList;
    private String addressType;
    private String addressTypeId;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String district;
    private String state;
    private String pincode;
    private String phone;
    private String mobile;
    private String email;
    private String nearestRyStation;
    private String type;
    private String creationDate;
    private int status;
    private String remarks;
    private String lastModifiedDate;
    private JSON addressDetailsJSON;
    private String message;
    private String districtId;
    private String stateId;
    private String nearestAirport;
    private String addFlag;
    private String changedValues;
    private String dispensaryNumber;
	private String address4;
    private String careOfAddress;
    private DistrictTypeDTO districtDetails;
    private StateTypeDTO stateDetails;
    private String requestID;
    private String requestId;
    
    
    public DistrictTypeDTO getDistrictDetails() {
		return districtDetails;
	}

	public void setDistrictDetails(DistrictTypeDTO districtDetails) {
		this.districtDetails = districtDetails;
	}

	public StateTypeDTO getStateDetails() {
		return stateDetails;
	}

	public void setStateDetails(StateTypeDTO stateDetails) {
		this.stateDetails = stateDetails;
	}

	public String getCareOfAddress() {
		return careOfAddress;
	}

	public void setCareOfAddress(String careOfAddress) {
		this.careOfAddress = careOfAddress;
	}

	public String getDispensaryNumber() {
		return dispensaryNumber;
	}

	public void setDispensaryNumber(String dispensaryNumber) {
		this.dispensaryNumber = dispensaryNumber;
	}

	public String getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(String addFlag) {
        this.addFlag = addFlag;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(String addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public String getNearestAirport() {
        return nearestAirport;
    }

    public void setNearestAirport(String nearestAirport) {
        this.nearestAirport = nearestAirport;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public JSON getAddressDetailsJSON() {
        return addressDetailsJSON;
    }

    public void setAddressDetailsJSON(JSON addressDetailsJSON) {
        this.addressDetailsJSON = addressDetailsJSON;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getAddressTypeList() {
        return addressTypeList;
    }

    public void setAddressTypeList(List addressTypeList) {
        this.addressTypeList = addressTypeList;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNearestRyStation() {
        return nearestRyStation;
    }

    public void setNearestRyStation(String nearestRyStation) {
        this.nearestRyStation = nearestRyStation;
    }

    public List getAddressList() {
        return addressList;
    }

    public void setAddressList(List addressList) {
        this.addressList = addressList;
    }

    public String getChangedValues() {
        return changedValues;
    }

    public void setChangedValues(String changedValues) {
        this.changedValues = changedValues;
    }

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
