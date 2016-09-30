package com.callippus.web.dao.address;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.address.AddressBean;

public interface IAddressDAO {
	public ArrayList getEmployeeAddressDetails(AddressBean addressBean) throws Exception;

	public String manageAddressDetails(AddressBean addressBean) throws Exception;

	public List<AddressBean> getAddressDetailsMasterData(String addressId) throws Exception;

	public String deleteAddressDetails(AddressBean addressBean) throws Exception;

	public List<AddressBean> addressTypeCheck(AddressBean addressBean) throws Exception;

	public String updateAddressDetails(AddressBean addressBean) throws Exception;

	public List getEditAddressDetails(String id) throws Exception;

	public String setHometownStatus(AddressBean addressBean) throws Exception;

}
