package com.callippus.web.dao.MT;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.callippus.web.beans.MT.AddressDetailsBean;
import com.callippus.web.beans.MT.ArticleDetailsBean;
import com.callippus.web.beans.MT.DriverDetailsBean;
import com.callippus.web.beans.MT.FuelTypeDTO;
import com.callippus.web.beans.MT.MTApplicationBean;
import com.callippus.web.beans.MT.MTDriverAbsentDTO;
import com.callippus.web.beans.MT.MTRequestDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleAbsentDTO;
import com.callippus.web.beans.MT.MTVehicleAllocationtDetailsDTO;
import com.callippus.web.beans.MT.MTVehicleDriverMapDTO;
import com.callippus.web.beans.MT.MTVehicleRequestDetailsDTO;
import com.callippus.web.beans.MT.ModelMasterBean;
import com.callippus.web.beans.MT.TravelAgencyDetailsBean;
import com.callippus.web.beans.MT.VehicleCategoryMasterBean;
import com.callippus.web.beans.MT.VehicleDetailsBean;
import com.callippus.web.beans.MT.VehicleDriverDTO;
import com.callippus.web.beans.MT.VehicleMileageDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;

public interface IMechincalTransportDAO {
	

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
	public List<VehicleDetailsBean> vehicleList(MTApplicationBean mtBean) throws Exception ;
	
	public String saveVehicleData(VehicleDetailsBean vDTO) throws Exception;
	
	public String deleteVehicleDetails(MTApplicationBean mtBean) throws Exception ;
	
	//for Address
	public List<AddressDetailsBean> addressList() throws Exception ;
	
	public String saveAddressData(AddressDetailsBean aDTO) throws Exception;
	
	public String deleteAddressDetails(MTApplicationBean mtBean) throws Exception ;
	
	//for driver Absent
	public List getDriverEmplyeeList() throws Exception ;
	public String saveDriverAbsentDetails(MTDriverAbsentDTO vdaDTO) throws Exception ;
	public String deleteDriverAbsentDetails(MTApplicationBean mtBean) throws Exception ;
	public List getDriverAbsentDetails() throws Exception;
	
	//for vehicle Absent
	public List getVehicleList() throws Exception ;
	public String saveVehicleAbsentDetails(MTVehicleAbsentDTO vdaDTO) throws Exception ;
	public String deleteVehicleAbsentDetails(MTApplicationBean mtBean) throws Exception ;
	public List getVehicleAbsentDetails() throws Exception;
	
	//for vehicle-driver map
	public List getNotInMapVehicleList() throws Exception ;
	public List getNotInMapDriverList() throws Exception; 
	public List<MTVehicleDriverMapDTO> getVehicleDriverList(MTApplicationBean mtBean) throws Exception ;
	public String saveVehicleDriverMasterData(MTVehicleDriverMapDTO vdDTO,String param) throws Exception;
	public String deleteVehicleDriverDetails(MTApplicationBean mtBean) throws Exception ;
	public String checkVehicleDriverMap(MTApplicationBean mtBean) throws Exception;
	
	//for getting driver,vehicle absent list
	public List getDriverAbsentList(MTApplicationBean mtBean) throws Exception ;
	public List getVehicleAbsentList(MTApplicationBean mtBean) throws Exception ;
	
	//for getting available drivers
	//public List getCurrentAvailabeDrivers() throws Exception;
	public List getCurrentAvailabeDrivers(MTApplicationBean mtBean) throws Exception;
	
	public List getAbsentVehicleDrivers() throws Exception ;
	
	public EmployeeBean getEmployeeDetails(MTApplicationBean mtbBean) throws Exception ;
	
	//for gettong requester details 
	public MTRequestDetailsDTO getVehicleApplicantDetails(String requestID) throws Exception;
	public String checkVehicleRequesterDetails(MTApplicationBean mtBean) throws Exception;
	
	//public List<ArticleDetailsBean> getArticleDetails(String requestId) throws Exception;
	//public List<ArticleDetailsBean> getReturnArticleDetails(String requestId) throws Exception;
	
	//public MTVehicleDriverMapDTO getAvailableVehicleDriverList(String reqiestId) throws Exception ;
	public List getAvailableBusyVehicleDriverList() throws Exception;
	public List<VehicleDetailsBean> getAllHiredVehiclesList() throws Exception;
	//public List getAllBusyVehicles(String requestId)throws Exception;
	
	//public List<MTVehicleRequestDetailsDTO> getAllocatedVehicleList(MTApplicationBean mtBean) throws Exception;
	public List getAllAllocatedVehiclesList() throws Exception;
	
	/*public String releaseSelectedVehicle(MTApplicationBean mtBean) throws Exception;*/
	public String releaseAllSelectedVehicles(MTApplicationBean mtBean) throws Exception;
	public String releaseAllAllocatedVehicles(MTApplicationBean mtBean) throws Exception;
	
	//kumari
	public String releaseVehicles(MTApplicationBean mtBean) throws Exception;
	//for Mileage
	public List getMileageList()throws Exception;
	public String saveMileageDetails(VehicleMileageDTO vmDTO) throws Exception;
	public String deleteMileageDetails(MTApplicationBean mtbBean) throws Exception;
	
	public List<YearTypeDTO> getYearList() throws Exception;
	
	public List<FuelTypeDTO> getFuelTypeList() throws Exception;
	//public String getPreDayClosingRDE(MTApplicationBean mtbean) throws Exception;
	public VehicleMileageDTO getPreDayClosingDetails(MTApplicationBean mtbean) throws Exception;
	public List<VehicleMileageDTO> getMaxMeterReadings(MTApplicationBean mtbean) throws Exception;
	
	public List<MTVehicleAllocationtDetailsDTO> getVehicleFreeDetails(Date fdate, Date tdate, String mapId , Session session) throws Exception;
	
	public List<MTRequestDetailsDTO> getApprovedRequests() throws Exception;
	
	//public List<MTVehicleDriverMapDTO> getAvailableVDListForParticularJourney(String journeyId) throws Exception;
	public List<MTVehicleDriverMapDTO> getAvailableVDListForParticularJourney(MTApplicationBean mtbean) throws Exception;
	
	//for save vehicle allocation details
	public String saveVehicleAllocationDetails(MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO) throws Exception;
	public MTVehicleAllocationtDetailsDTO getVehicleAllocationDetails(MTApplicationBean mtbean) throws Exception;
	public  MTVehicleDriverMapDTO getVehicleDriverMapDetails(MTApplicationBean mtbean) throws Exception;
	public String updateAllocationMap(MTVehicleDriverMapDTO oldMapDTO,MTVehicleDriverMapDTO newMapDTO) throws Exception ;
	public MTVehicleAllocationtDetailsDTO getVehicleAllocationDetailsForPerticulerDate(String date,int mapId) throws Exception;
	public List<MTVehicleAllocationtDetailsDTO> getVehicleAllocationDetailsForDiffDates(MTApplicationBean mtbean) throws Exception;
	public String getEmpExist(String sfid) throws Exception;
	public List<MTRequestDetailsDTO> getAllocatedRequests() throws Exception;
	public String userCancelJourney(MTApplicationBean mtbean) throws Exception;
	public List<MTRequestDetailsDTO> getCanceledRequests() throws Exception;
	public String deallocateVehicle(MTApplicationBean mtbean) throws Exception;
	public EmployeeBean getEmpDetails(String sfid) throws Exception;
	public VehicleDetailsBean getDedicatedVehicleDetails(int vehicleId) throws Exception;
	public void saveAllocationForDedicated(MTVehicleAllocationtDetailsDTO mAllocationtDetailsDTO) throws Exception;
	public List<EmployeeBean> getAllEmployeeList() throws Exception;
	public List getDedicatedVehicles() throws Exception;
	public String freeDedicatedVehicles(MTApplicationBean mtbean) throws Exception;
	public List<DriverDetailsBean> getHiredDriversList() throws Exception;
	public String getVehicleDriverMapId(MTApplicationBean mtbean) throws Exception;
	//public List<MTVehicleAllocationtDetailsDTO> getVDListForCombineAlloc(String journeyId) throws Exception;
	public List<MTVehicleAllocationtDetailsDTO> getVDListForCombineAlloc(MTApplicationBean mtbean) throws Exception;
	public String saveCombineAlloc(MTApplicationBean mtbean) throws Exception;
	public String updateJourneyDetails(int journeyId,int allocId) throws Exception;
	public List getallNotMappedVehicles() throws Exception;
	public String checkNextAllocationForRelease(MTApplicationBean mtbean) throws Exception;
	public String updateRequestDetails(MTApplicationBean mtBean) throws Exception;
	public String updateVehicleAllocationDetails(MTApplicationBean mtbean) throws Exception;
	public List getVehicleTypeList() throws Exception;
}
