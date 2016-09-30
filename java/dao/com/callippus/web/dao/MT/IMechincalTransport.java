package com.callippus.web.dao.MT;

import java.util.List;

import com.callippus.web.beans.MT.AddressDetailsBean;
import com.callippus.web.beans.MT.DriverDetailsBean;
import com.callippus.web.beans.MT.MTApplicationBean;
import com.callippus.web.beans.MT.ModelMasterBean;
import com.callippus.web.beans.MT.TravelAgencyDetailsBean;
import com.callippus.web.beans.MT.VehicleCategoryMasterBean;
import com.callippus.web.beans.MT.VehicleDetailsBean;
import com.callippus.web.beans.MT.VehicleDriverDTO;

public interface IMechincalTransport {
	

	/*public List<DriverDetailsBean> getDriverList() throws Exception;

	public List<VehicleDetailsBean> vehicleList() throws Exception;

	public List<TravelAgencyDetailsBean> travelAgencyList() throws Exception;

	

	public List<ModelMasterBean> modelList() throws Exception;

	public List<DriverDetailsBean> getDriverDetailList() throws Exception;

	public List<AddressDetailsBean> getAddressList() throws Exception;

	public List<VehicleDetailsBean> getVehicleDetailList() throws Exception;

	public String updateMasterData(MTApplicationBean bean) throws Exception;

	public String deleteMasterData(MTApplicationBean mtBean) throws Exception;

	public List<VehicleTypeDetailsBean> getVehicleType() throws Exception;

	public List<EmployeeBean> getEmployeeDetails(MTApplicationBean mtBean) throws Exception ;*/
	
	//added by Narayana
	//for Vehicle Category
	public List<VehicleCategoryMasterBean> categoryList() throws Exception;
	
	public String saveCategoryMasterData(VehicleCategoryMasterBean vcmDTO) throws Exception;
	
	public String deleteCategoryDetails(MTApplicationBean mtBean) throws Exception;
	
	//for vehicle Model
	public List<ModelMasterBean> modelList() throws Exception;
	
	public String saveModelMasterData(ModelMasterBean vmmDTO) throws Exception;
	
	public String deleteModelDetails(MTApplicationBean mtBean) throws Exception;
	
	//for TravelAgency
	public List<TravelAgencyDetailsBean> travelAgencyList() throws Exception;
	
	public String saveTravelAgencyMasterData(TravelAgencyDetailsBean tamDTO) throws Exception;
	
	public String deleteTravelAgencyDetails(MTApplicationBean mtBean) throws Exception;
	
	//for Driver
	public List<DriverDetailsBean> driverList() throws Exception;
	
	public String saveDriverData(DriverDetailsBean dDTO) throws Exception;
	
	public String deleteDriverDetails(MTApplicationBean mtBean) throws Exception;
	
	//for Vehicle
	public List<VehicleDetailsBean> vehicleList() throws Exception ;
	
	public String saveVehicleData(VehicleDetailsBean vDTO) throws Exception;
	
	public String deleteVehicleDetails(MTApplicationBean mtBean) throws Exception ;
	
	//for Address
	public List<AddressDetailsBean> addressList() throws Exception ;
	
	public String saveAddressData(AddressDetailsBean aDTO) throws Exception;
	
	public String deleteAddressDetails(MTApplicationBean mtBean) throws Exception ;
	
	//for driver Absent
	public List getDriverEmplyeeList() throws Exception ;
	public String saveDriverAbsentDetails(MTApplicationBean mtBean) throws Exception ;
	
	//for vehicle Absent
	public List getVehicleList() throws Exception ;
	public String saveVehicleAbsentDetails(MTApplicationBean mtBean) throws Exception ;
	
	//for vehicle-driver map
	public List<VehicleDriverDTO> getVehicleDriverList() throws Exception ;
	public String saveVehicleDriverMasterData(VehicleDriverDTO vdDTO) throws Exception;
	public String deleteVehicleDriverDetails(MTApplicationBean mtBean) throws Exception ;
	
	//for getting driver,vehicle absent list
	public List getDriverAbsentList() throws Exception ;
	public List getVehicleAbsentList() throws Exception ;
	
	//for getting available drivers
	public List getCurrentAvailabeDrivers() throws Exception;
	
	public List getAbsentVehicleDrivers() throws Exception ;
	public String checkVehicleRequesterDetails(MTApplicationBean mtBean) throws Exception ;
}
