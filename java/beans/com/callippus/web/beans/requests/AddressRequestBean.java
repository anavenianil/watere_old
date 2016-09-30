package com.callippus.web.beans.requests;

public class AddressRequestBean extends RequestBean {
    private String addressTypeId;

    public String getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(String addressTypeId) {
        this.addressTypeId = addressTypeId;
    }
}
