package com.callippus.web.business.address;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.StateTypeDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.address.IAddressDAO;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class AddressBusiness {
	private static Log log = LogFactory.getLog(AddressBusiness.class);
	@Autowired
	private IAddressDAO addressDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public AddressBean getAddressHomeDetails(AddressBean addressBean) throws Exception {
		log.debug("::getAddressHomeDetails ---> method ---> start");
		try {
			addressBean.setAddressList(addressDAO.getEmployeeAddressDetails(addressBean));
			addressBean.setAddressTypeList(commonDataDAO.getMasterData(CPSConstants.ADDRESSTYPEDTO));
		} catch (Exception e) {
			throw e;
		}
		log.debug("::getAddressHomeDetails ---> method ---> end");
		return addressBean;
	}

	public AddressBean manageAddressDetails(AddressBean addressBean) throws Exception {
		log.debug("::manageAddressDetails ---> method ---> start");
		String message = null;
		try {

			if (CPSUtils.isNullOrEmpty(addressBean.getId())) {
				if (CPSUtils.checkList(addressDAO.addressTypeCheck(addressBean))) {
					message = CPSConstants.ADDRESSTYPEEXISTED;
					addressBean.setMessage(message);
				} else {
					int id = commonDataDAO.getTableID(CPSConstants.ADDRESSDETAILSMASTER, CPSConstants.UPDATE);
					addressBean.setId(String.valueOf(id));
					addressBean.setCreationDate(CPSUtils.getCurrentDate());
					addressBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					addressBean.setStatus(1);
					message = addressDAO.manageAddressDetails(addressBean);

					if (addressBean.getAddFlag().equals(CPSConstants.FALSE) && message.equals(CPSConstants.SUCCESS)) {
						message = addressDAO.setHometownStatus(addressBean);
					}
				}
			} else {
				message = addressDAO.updateAddressDetails(addressBean);
			}
			log.debug("::message -->" + message);
			addressBean.setMessage(message);
			addressBean.setAddressList(addressDAO.getEmployeeAddressDetails(addressBean));
			addressBean.setAddressTypeList(commonDataDAO.getMasterData(CPSConstants.ADDRESSTYPEDTO));
		} catch (Exception e) {
			throw e;
		}
		log.debug("::manageAddressDetails ---> method ---> end");
		return addressBean;
	}

	public AddressBean deleteAddressDetails(AddressBean addressBean) throws Exception {
		log.debug("::deleteAddressDetails ---> method ---> start");
		String message = null;
		try {
			message = addressDAO.deleteAddressDetails(addressBean);
			log.debug("::message -->" + message);
			addressBean.setMessage(message);
			addressBean.setAddressList(addressDAO.getEmployeeAddressDetails(addressBean));
			addressBean.setAddressTypeList(commonDataDAO.getMasterData(CPSConstants.ADDRESSTYPEDTO));

		} catch (Exception e) {
			throw e;
		}
		log.debug("::deleteAddressDetails ---> method ---> end");
		return addressBean;
	}

	public List<StateTypeDTO> getStateList() throws Exception {
		List<StateTypeDTO> stateList = null;
		try {
			stateList = commonDataDAO.getStateList();
		} catch (Exception e) {
			throw e;
		}
		return stateList;
	}

	public List<DistrictTypeDTO> getDistrictList() throws Exception {
		List<DistrictTypeDTO> districtList = null;
		try {
			districtList = commonDataDAO.getDistrictList();
		} catch (Exception e) {
			throw e;
		}
		return districtList;
	}

	public AddressBean getEditAddressDetails(AddressBean addressBean) throws Exception {
		log.debug("::getAddressHomeDetails ---> method ---> start");
		try {
			if (!CPSUtils.isNullOrEmpty(addressBean.getId()))
				addressBean = (AddressBean) addressDAO.getEditAddressDetails(addressBean.getId()).get(0);
			addressBean.setAddressTypeList(commonDataDAO.getMasterData(CPSConstants.ADDRESSTYPEDTO));
		} catch (Exception e) {
			throw e;
		}
		log.debug("::getAddressHomeDetails ---> method ---> end");
		return addressBean;
	}

}
