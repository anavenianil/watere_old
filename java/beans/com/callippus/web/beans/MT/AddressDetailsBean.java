package com.callippus.web.beans.MT;

import com.callippus.web.beans.dto.CommonDTO;

public class AddressDetailsBean extends CommonDTO{
 private int addressId;
 private String addressName;
 
public int getAddressId() {
	return addressId;
}
public void setAddressId(int addressId) {
	this.addressId = addressId;
}
public String getAddressName() {
	return addressName;
}
public void setAddressName(String addressName) {
	this.addressName = addressName;
}

}
