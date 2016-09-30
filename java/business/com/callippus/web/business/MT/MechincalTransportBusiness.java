package com.callippus.web.business.MT;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.MT.AddressDetailsBean;
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
import com.callippus.web.beans.MT.VehicleMileageDTO;
import com.callippus.web.beans.dto.KeyDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.MT.IMechincalTransportDAO;


@Service
public class MechincalTransportBusiness {
	@Autowired
	private IMechincalTransportDAO mtDAO;
	String message = "";

	
	
	
	//added by Narayana
	//for Vehicle Category
	public List<VehicleCategoryMasterBean> categoryList() throws Exception {
		List<VehicleCategoryMasterBean> cateList = null;
		try {

			cateList = mtDAO.categoryList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cateList;

	}
	public List<FuelTypeDTO> getFuelTypeList() throws Exception {
		List<FuelTypeDTO> fuelTypeList = null;
		try {

			fuelTypeList = mtDAO.getFuelTypeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fuelTypeList;

	}
	public String saveCategoryDetails(MTApplicationBean mtBean) throws Exception{
		VehicleCategoryMasterBean vcmDTO = new VehicleCategoryMasterBean();
		if(!mtBean.getPk().equals("")){
			vcmDTO.setCategoryId(Integer.parseInt(mtBean.getPk()));	
		}
		vcmDTO.setCategoryName(mtBean.getCategoryName());
		vcmDTO.setCarriageType(mtBean.getCarriageType());
		vcmDTO.setCatDesc(mtBean.getCatDesc());
		vcmDTO.setStatus(1);
		vcmDTO.setLastModifiedBy(mtBean.getSfID());
		vcmDTO.setCreationDate(CPSUtils.getCurrentDate());
		vcmDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		message = mtDAO.saveCategoryMasterData(vcmDTO);
		return message;
	}
	public String deleteCategoryDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteCategoryDetails(mtBean);
		return message;
	}
	
	//for Vehicle Model
	public List<ModelMasterBean> modelList() throws Exception {
		List<ModelMasterBean> modelList = null;
		try {

			modelList = mtDAO.modelList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelList;

	}
	public String saveModelDetails(MTApplicationBean mtBean) throws Exception{
		ModelMasterBean vmmDTO = new ModelMasterBean();
		if(!mtBean.getPk().equals("")){
			vmmDTO.setModelId(Integer.parseInt(mtBean.getPk()));	
		}
		vmmDTO.setVehicleCategoryId(mtBean.getVehicleCategoryId());
		vmmDTO.setModelName(mtBean.getModelName());
		vmmDTO.setModelDesc(mtBean.getModelDesc());
		vmmDTO.setStatus(1);
		vmmDTO.setLastModifiedBy(mtBean.getSfID());
		vmmDTO.setCreationDate(CPSUtils.getCurrentDate());
		vmmDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		message = mtDAO.saveModelMasterData(vmmDTO);
		return message;
	}
	public String deleteModelDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteModelDetails(mtBean);
		return message;
	}
	
	//for TravelAgency
	public List<TravelAgencyDetailsBean> travelAgencyList() throws Exception {
		List<TravelAgencyDetailsBean> travelAgencyList = null;
		try {

			travelAgencyList = mtDAO.travelAgencyList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return travelAgencyList;

	}
	public String saveTravelAgencyDetails(MTApplicationBean mtBean) throws Exception{
		TravelAgencyDetailsBean tamDTO = new TravelAgencyDetailsBean();
		if(!mtBean.getPk().equals("")){
			tamDTO.setTravelId((Integer.parseInt(mtBean.getPk())));	
		}
		tamDTO.setTravelAgencyName(mtBean.getTravelAgencyName());
		tamDTO.setContactPerson(mtBean.getContactPerson());
		tamDTO.setTravelMobileNo(mtBean.getTravelMobileNo());
		tamDTO.setAddress(mtBean.getAddress());
		tamDTO.setStatus(1);
		tamDTO.setLastModifiedBy(mtBean.getSfID());
		tamDTO.setCreationDate(CPSUtils.getCurrentDate());
		tamDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		message = mtDAO.saveTravelAgencyMasterData(tamDTO);
		return message;
	}
	public String deleteTravelAgencyDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteTravelAgencyDetails(mtBean);
		return message;
	}
	
	//for Driver
	public List<DriverDetailsBean> driverList() throws Exception {
		List<DriverDetailsBean> driverList = null;
		try {

			driverList = mtDAO.driverList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driverList;

	}
	//for hired drivers
	
	public List<DriverDetailsBean> getHiredDriversList() throws Exception {
		List<DriverDetailsBean> driverList = null;
		try {

			driverList = mtDAO.getHiredDriversList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driverList;

	}
	public String saveDriverDetails(MTApplicationBean mtBean) throws Exception{
		DriverDetailsBean dDTO = new DriverDetailsBean();
		if(!mtBean.getPk().equals("")){
			dDTO.setDriverId((Integer.parseInt(mtBean.getPk())));	
		}
		dDTO.setDriverType(mtBean.getDriverType());
		dDTO.setDriverName(mtBean.getDriverName());
		dDTO.setDriverIdSfid(mtBean.getDriverIdSfid());
		dDTO.setDriverMobileNo(mtBean.getDriverMobileNo());
		if(mtBean.getDriverTravelAgencyName() != null && !mtBean.getDriverTravelAgencyName().equals("0")){
		dDTO.setDriverTravelAgencyName(mtBean.getDriverTravelAgencyName());
		}else{
			dDTO.setDriverTravelAgencyName("");	
		}
		dDTO.setStatus(1);
		dDTO.setLastModifiedBy(mtBean.getSfID());
		dDTO.setCreationDate(CPSUtils.getCurrentDate());
		dDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		message = mtDAO.saveDriverData(dDTO);
		return message;
	}
	public String deleteDriverDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteDriverDetails(mtBean);
		return message;
	}
	
	//for Vehicle
	public List<VehicleDetailsBean> vehicleList(MTApplicationBean mtBean) throws Exception {
		List<VehicleDetailsBean> vehicleList = null;
		try {

			vehicleList = mtDAO.vehicleList(mtBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleList;

	}
	public String saveVehicleDetails(MTApplicationBean mtBean) throws Exception{
		VehicleDetailsBean vDTO = new VehicleDetailsBean();
		if(!mtBean.getPk().equals("")){
			vDTO.setVehicleId((Integer.parseInt(mtBean.getPk())));	
		}
		vDTO.setVehicleType(mtBean.getVehicleType());
		if(mtBean.getVehicleTravelAgencyName() != null && !mtBean.getVehicleTravelAgencyName().equals("0")){
		vDTO.setVehicleTravelAgencyName(mtBean.getVehicleTravelAgencyName());
		vDTO.setDriverName(mtBean.getDriverName());
		vDTO.setDriverMobileNo(mtBean.getDriverMobileNo());
		}else{
			vDTO.setVehicleTravelAgencyName("");
			vDTO.setDriverName("");
			vDTO.setDriverMobileNo("");	
		}
		vDTO.setVehicleName(mtBean.getVehicleName());
		vDTO.setVehicleNo(mtBean.getVehicleNo());
		vDTO.setVehicleCategoryId(mtBean.getVehicleCategoryId());
		vDTO.setVehicleModelId(mtBean.getVehicleModelId());
		vDTO.setNoOfPeople(mtBean.getNoOfPeople());
		vDTO.setFuelTypeId(mtBean.getFuelTypeId());
		vDTO.setFuelCapacity(mtBean.getFuelCapacity());
		vDTO.setMileage(mtBean.getMileage());
		vDTO.setInitialReading(mtBean.getInitialReading());
		vDTO.setVehiclePoolType(mtBean.getVehiclePoolType());
		vDTO.setVehicleSensitiveType(mtBean.getVehicleSensitiveType());
		vDTO.setDedicatedPersonSfid(mtBean.getDedicatedPerson());
		vDTO.setRemarks(mtBean.getRemarks());
		vDTO.setStatus(1);
		vDTO.setLastModifiedBy(mtBean.getSfID());
		vDTO.setCreationDate(CPSUtils.getCurrentDate());
		vDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		message = mtDAO.saveVehicleData(vDTO);
		//if vehicle is hired vehicle then save record on mapdetails
		if(mtBean.getVehicleType()==2){
			SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy HH24:mm");
			MTApplicationBean mBeanForMap = new MTApplicationBean();
			//vehicle id saved in map table
			mBeanForMap.setVehicleId(vDTO.getVehicleId());
			mBeanForMap.setDriverId(Integer.parseInt(mtBean.getDriverName()));
			mBeanForMap.setFromDate(CPSUtils.getCurrentDate()+" 00:01");
			//mBeanForMap.setFromTime(CPSUtils.getCurrentDateWithTime().getHours()+":"+CPSUtils.getCurrentDateWithTime().getMinutes());
			mBeanForMap.setFromTime("00:01");
			mBeanForMap.setRemarks("hired");
			mBeanForMap.setPk(mtBean.getPk());
			mBeanForMap.setParam(mtBean.getParam());
			if(!mtBean.getPk().equals("")){
				mBeanForMap.setVehicleId(Integer.parseInt(mtBean.getPk()));
				String mapId=mtDAO.getVehicleDriverMapId(mBeanForMap);
				mBeanForMap.setPk(mapId);
				mBeanForMap.setParam("ChangeDriver");
			}
			saveVehicleDriverMasterData(mBeanForMap);
		/*MTVehicleDriverMapDTO mapDTO=new MTVehicleDriverMapDTO();
		mapDTO.setVehicleId(mtBean.getVehicleId());
		mapDTO.setDriverId(mtBean.getDriverId());
		mapDTO.setAllotmentFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(fromDateTime));
		mapDTO.setAllotmentFromTime(mtBean.getFromTime());
		mapDTO.setCreationDate(CPSUtils.getCurrentDate());
		mapDTO.setStatus(1);
		mapDTO.setRemarks(mtBean.getRemarks());
		mapDTO.setLastModifiedBy(mtBean.getSfID());	
		mapDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		String msg = mtDAO.saveVehicleDriverMasterData(mapDTO,mtBean.getParam());*/
		}
		return message;
	}
	public String deleteVehicleDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteVehicleDetails(mtBean);
		return message;
	}
	
	//for Address
	public List<AddressDetailsBean> addressList() throws Exception {
		List<AddressDetailsBean> addressList = null;
		try {

			addressList = mtDAO.addressList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addressList;

	}
	public String saveAddressDetails(MTApplicationBean mtBean) throws Exception{
		AddressDetailsBean aDTO = new AddressDetailsBean();
		if(!mtBean.getPk().equals("")){
			aDTO.setAddressId((Integer.parseInt(mtBean.getPk())));	
		}
		aDTO.setAddressName(mtBean.getAddressName());
		aDTO.setStatus(1);
		aDTO.setLastModifiedBy(mtBean.getSfID());
		aDTO.setCreationDate(CPSUtils.getCurrentDate());
		aDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		message = mtDAO.saveAddressData(aDTO);
		return message;
	}
	public String deleteAddressDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteAddressDetails(mtBean);
		return message;
	}
	
	//for driver absent
	@SuppressWarnings("unchecked")
	public List getDriverEmplyeeList() throws Exception {
		List<KeyValueDTO> driverList = null;
		try{
			driverList = mtDAO.getDriverEmplyeeList();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return driverList;
	}
	
	public String saveDriverAbsentDetails(MTApplicationBean mtBean) throws Exception {
		
		try{	
			MTDriverAbsentDTO vdaDTO = new MTDriverAbsentDTO();
			if(!mtBean.getPk().equals("")){
				vdaDTO.setId(Integer.parseInt(mtBean.getPk()));
			}
			vdaDTO.setDriverId(Integer.parseInt(mtBean.getDriverName()));
			if(!CPSUtils.isNullOrEmpty(mtBean.getFromDate())){
				mtBean.setFromDate(mtBean.getFromDate()+" "+mtBean.getFromTime());
			}
			vdaDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtBean.getFromDate()));
			vdaDTO.setFromTime(mtBean.getFromTime());
			if(!CPSUtils.isNullOrEmpty(mtBean.getToDate())){
				mtBean.setToDate(mtBean.getToDate()+" "+mtBean.getToTime());
			}
			vdaDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtBean.getToDate()));
			vdaDTO.setToTime(mtBean.getToTime());
			vdaDTO.setRemarks(mtBean.getRemarks());
			vdaDTO.setStatus(1);
			vdaDTO.setLastModifiedBy(mtBean.getSfID());
			vdaDTO.setCreationDate(CPSUtils.getCurrentDate());
			vdaDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			
				message = mtDAO.saveDriverAbsentDetails(vdaDTO);
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return message;
	}
	public String deleteDriverAbsentDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteDriverAbsentDetails(mtBean);
		return message;
	}
	@SuppressWarnings("unchecked")
	public List getDriverAbsentDetails() throws Exception{
		List<MTDriverAbsentDTO> absentList = null;
		try{
			absentList = mtDAO.getDriverAbsentDetails();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return absentList;
	}
	
	//for vehicle absent
	@SuppressWarnings("unchecked")
	public List getVehicleList() throws Exception {
		List<KeyValueDTO> vehicleList = null;
		try{
			vehicleList = mtDAO.getVehicleList();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vehicleList;
	}
	
	
	public String saveVehicleAbsentDetails(MTApplicationBean mtBean) throws Exception {
		try{	
			MTVehicleAbsentDTO vdaDTO = new MTVehicleAbsentDTO();
			if(!mtBean.getPk().equals("")){
				vdaDTO.setId(Integer.parseInt(mtBean.getPk()));
			}
			vdaDTO.setVehicleId(Integer.parseInt(mtBean.getVehicleNo()));
			if(!CPSUtils.isNullOrEmpty(mtBean.getFromDate())){
				mtBean.setFromDate(mtBean.getFromDate()+" "+mtBean.getFromTime());
			}
			vdaDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtBean.getFromDate()));
			vdaDTO.setFromTime(mtBean.getFromTime());
			if(!CPSUtils.isNullOrEmpty(mtBean.getToDate())){
				mtBean.setToDate(mtBean.getToDate()+" "+mtBean.getToTime());
			}
			vdaDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtBean.getToDate()));
			vdaDTO.setToTime(mtBean.getToTime());
			vdaDTO.setRemarks(mtBean.getRemarks());
			vdaDTO.setStatus(1);
			vdaDTO.setLastModifiedBy(mtBean.getSfID());
			vdaDTO.setCreationDate(CPSUtils.getCurrentDate());
			vdaDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			
				message = mtDAO.saveVehicleAbsentDetails(vdaDTO);
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return message;
	}
	public String deleteVehicleAbsentDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteVehicleAbsentDetails(mtBean);
		return message;
	}
	@SuppressWarnings("unchecked")
	public List getVehicleAbsentDetails() throws Exception{
		List<MTVehicleAbsentDTO> absentList = null;
		try{
			absentList = mtDAO.getVehicleAbsentDetails();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return absentList;
	}
	
	//for vehicle-driver mapMTVehicleDriverMapDTO
	public List getVehicleDriverList(MTApplicationBean mtBean) throws Exception {
		List<MTVehicleDriverMapDTO> vehicleDriverList = null;
		try {

			vehicleDriverList = mtDAO.getVehicleDriverList(mtBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleDriverList;

	}
	public List getNotInMapVehicleList() throws Exception {
		List vehicleList = null;
		try {

			vehicleList = mtDAO.getNotInMapVehicleList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleList;
	}
	public List getallNotMappedVehicles() throws Exception {
		List vehicleList = null;
		try {

			vehicleList = mtDAO.getallNotMappedVehicles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleList;
	}
	public List getNotInMapDriverList() throws Exception {
		List driverList = null;
		try {

			driverList = mtDAO.getNotInMapDriverList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driverList;
	}
	public String saveVehicleDriverMasterData(MTApplicationBean mtBean) throws Exception{
		
	//	MTVehicleDriverMapDTO mapDTO = mtDAO.getVehicleDriverMapDetails(mtBean);
		/*------04
		MTVehicleDriverMapDTO mapDTO =new MTVehicleDriverMapDTO();
		if(!mtBean.getPk().equals("")){
			mapDTO.setId(Integer.parseInt(mtBean.getPk()));	
		}
		if(!CPSUtils.isNullOrEmpty(mtBean.getFromDate())){
			mtBean.setFromDate(mtBean.getFromDate()+" "+mtBean.getFromTime());
		}
		
		if(!mtBean.getParam().equals("ChangeDriver")){
			mapDTO.setVehicleId(mtBean.getVehicleId());
			mapDTO.setDriverId(mtBean.getDriverId());
			mapDTO.setAllotmentFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtBean.getFromDate()));
			mapDTO.setAllotmentFromTime(mtBean.getFromTime());
			mapDTO.setStatus(1);
			
		}else{
			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtBean.getFromDate()));
			cal.add(Calendar.MINUTE, -1);
			mapDTO.setAllotmentToDate(cal.getTime());	
			cal.set(0, 0, 0, Integer.parseInt(mtBean.getFromTime().split(":")[0]), Integer.parseInt(mtBean.getFromTime().split(":")[1].trim()));
			cal.add(Calendar.MINUTE, -1);
			mapDTO.setAllotmentToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
			mapDTO.setStatus(10);
		}
		
		mapDTO.setRemarks(mtBean.getRemarks());
		mapDTO.setLastModifiedBy(mtBean.getSfID());
		mapDTO.setCreationDate(CPSUtils.getCurrentDate());
		mapDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		
		message = mtDAO.saveVehicleDriverMasterData(mapDTO);
		if(!mtBean.getPk().equals("")){
		MTVehicleDriverMapDTO mapDTO2 = new MTVehicleDriverMapDTO();
		mapDTO2.setVehicleId(mtBean.getVehicleId());
		mapDTO2.setDriverId(mtBean.getDriverId());
		mapDTO2.setAllotmentFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtBean.getFromDate()));
		mapDTO2.setAllotmentFromTime(mtBean.getFromTime());
		mapDTO2.setStatus(1);
		mapDTO2.setLastModifiedBy(mtBean.getSfID());
		mapDTO2.setCreationDate(CPSUtils.getCurrentDate());
		mapDTO2.setLastModifiedDate(CPSUtils.getCurrentDate());
		mapDTO2.setRemarks(mtBean.getRemarks());
		message = mtDAO.saveVehicleDriverMasterData(mapDTO2);
		}*/
		MTVehicleDriverMapDTO mapDTO =new MTVehicleDriverMapDTO();
		MTVehicleDriverMapDTO oldMapDTO=null;
		String fromDateTime="";
		SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		if(!mtBean.getPk().equals("")){
			oldMapDTO=mtDAO.getVehicleDriverMapDetails(mtBean);
			mapDTO.setId(Integer.parseInt(mtBean.getPk()));	
		}
		if(!CPSUtils.isNullOrEmpty(mtBean.getFromDate())){
//mtBean.setFromDate(mtBean.getFromDate()+" "+mtBean.getFromTime());
			fromDateTime=mtBean.getFromDate()+" "+mtBean.getFromTime();
		}
		if(!mtBean.getParam().equals("ChangeDriver")){
			mapDTO.setVehicleId(mtBean.getVehicleId());
			mapDTO.setDriverId(mtBean.getDriverId());
			mapDTO.setAllotmentFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(fromDateTime));
			mapDTO.setAllotmentFromTime(mtBean.getFromTime());
			mapDTO.setCreationDate(CPSUtils.getCurrentDate());
			mapDTO.setStatus(1);
			mapDTO.setRemarks(mtBean.getRemarks());
			mapDTO.setLastModifiedBy(mtBean.getSfID());	
			mapDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			message = mtDAO.saveVehicleDriverMasterData(mapDTO,mtBean.getParam());
			
			VehicleDetailsBean vehicleBean= mtDAO.getDedicatedVehicleDetails(mtBean.getVehicleId());
			if(vehicleBean.getVehiclePoolType()==1){
				MTVehicleAllocationtDetailsDTO allocationtDetailsDTO=new MTVehicleAllocationtDetailsDTO();
				//allocationtDetailsDTO.setJourneyId(0);
				allocationtDetailsDTO.setFromDate(mapDTO.getAllotmentFromDate());
				allocationtDetailsDTO.setVehicleDriverMapId(mapDTO.getId());
				allocationtDetailsDTO.setStatusFlag(25);
				mtDAO.saveAllocationForDedicated(allocationtDetailsDTO);
			}
			
		}else{
			mapDTO.setDriverId(mtBean.getDriverId());
			mapDTO.setVehicleId(mtBean.getVehicleId());
			mapDTO.setAllotmentFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(fromDateTime));
			mapDTO.setAllotmentFromTime(mtBean.getFromTime());
			mapDTO.setStatus(1);
			mapDTO.setRemarks(mtBean.getRemarks());
			//mapDTO.setCreationDate(df.format(df2.parse(oldMapDTO.getCreationDate())));
			mapDTO.setLastModifiedBy(mtBean.getSfID());	
			mapDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			message = mtDAO.saveVehicleDriverMasterData(mapDTO,mtBean.getParam());
			
		}
		
		
		
		//message = mtDAO.saveVehicleDriverMasterData(mapDTO);
		if(!mtBean.getPk().equals("")){
			/*MTVehicleDriverMapDTO mapDTO2 = new MTVehicleDriverMapDTO();
			BeanUtils.copyProperties(mapDTO2, oldMapDTO);
			mapDTO2.setId(0);
			mapDTO2.setStatus(10);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(fromDateTime));
			cal.add(Calendar.MINUTE, -1);
			mapDTO.setAllotmentToDate(cal.getTime());	
			cal.set(0, 0, 0, Integer.parseInt(mtBean.getFromTime().split(":")[0]), Integer.parseInt(mtBean.getFromTime().split(":")[1].trim()));
			cal.add(Calendar.MINUTE, -1);
			mapDTO.setAllotmentToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
			
			mapDTO2.setLastModifiedBy(mtBean.getSfID());
			mapDTO2.setLastModifiedDate(CPSUtils.getCurrentDate());*/
			///
			
			MTVehicleDriverMapDTO mapDTO2 = new MTVehicleDriverMapDTO();
			mapDTO2.setVehicleId(oldMapDTO.getVehicleId());
			mapDTO2.setDriverId(oldMapDTO.getDriverId());
			
			mapDTO2.setAllotmentFromDate(df.parse(df.format(oldMapDTO.getAllotmentFromDate())));
			mapDTO2.setAllotmentFromTime(oldMapDTO.getAllotmentFromTime());
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(fromDateTime));
			cal.add(Calendar.MINUTE, -1);
			mapDTO2.setAllotmentToDate(df.parse(df.format(cal.getTime())));	
			cal.set(0, 0, 0, Integer.parseInt(mtBean.getFromTime().split(":")[0]), Integer.parseInt(mtBean.getFromTime().split(":")[1].trim()));
			cal.add(Calendar.MINUTE, -1);
			mapDTO2.setAllotmentToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
			
          
			mapDTO2.setStatus(10);
			mapDTO2.setLastModifiedBy(mtBean.getSfID());
			mapDTO2.setCreationDate(CPSUtils.getCurrentDate());
			mapDTO2.setLastModifiedDate(CPSUtils.getCurrentDate());
			mapDTO2.setRemarks(mtBean.getRemarks());
			message = mtDAO.saveVehicleDriverMasterData(mapDTO2,mtBean.getParam());
			mtDAO.updateAllocationMap(oldMapDTO,mapDTO2);
			}
		return message;
		
	}
	public String deleteVehicleDriverDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteVehicleDriverDetails(mtBean);
		return message;
	}
	
	//for getting driver,vehicle absent lists
	@SuppressWarnings("unchecked")
	public List getDriverAbsentList(MTApplicationBean mtBean) throws Exception {
		List<KeyValueDTO> driverList = null;
		try {

			driverList = mtDAO.getDriverAbsentList(mtBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driverList;
	}
	
	@SuppressWarnings("unchecked")
	public List getVehicleAbsentList(MTApplicationBean mtBean) throws Exception {
		List<KeyValueDTO> vehicleList = null;
		try {

			vehicleList = mtDAO.getVehicleAbsentList(mtBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleList;
	}
	
	@SuppressWarnings("unchecked")
	public List getCurrentAvailabeDrivers(MTApplicationBean mtBean) throws Exception{
		List<KeyValueDTO> availableDriverList = null;
		try{
			availableDriverList = mtDAO.getCurrentAvailabeDrivers(mtBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return availableDriverList;
	}
	
	@SuppressWarnings("unchecked")
	public List getAbsentVehicleDrivers() throws Exception {
		List<KeyDTO> absentVehicleDriverList = null;
		try{
			absentVehicleDriverList = mtDAO.getAbsentVehicleDrivers();
		}catch(Exception e){
			e.printStackTrace();
		}
		return absentVehicleDriverList;
	}
	
	public EmployeeBean getEmployeeDetails(MTApplicationBean mbBean) throws Exception{
		EmployeeBean resBean = null;
		try{
			resBean = mtDAO.getEmployeeDetails(mbBean);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resBean;
	} 
	public String checkVehicleDriverMap(MTApplicationBean mbBean) throws Exception{
		message = mtDAO.checkVehicleDriverMap(mbBean);
		return message;
	}
	
	/*public List getAllocatedVehicleList(MTApplicationBean mtBean) throws Exception{
		List<MTVehicleRequestDetailsDTO> allocVehiclesList = null;
		try{
			allocVehiclesList = mtDAO.getAllocatedVehicleList(mtBean);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return allocVehiclesList;
	}*/
	
	public String releaseSelectedVehicle(MTApplicationBean mtBean) throws Exception{
		
		try{
			message = mtDAO.releaseAllSelectedVehicles(mtBean);
			message = CPSConstants.RELEASEVEHICLE;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	public String releaseAllSelectedVehicles(MTApplicationBean mtBean) throws Exception{
		
		try{
			message = mtDAO.releaseAllAllocatedVehicles(mtBean);
			message = CPSConstants.RELEASEVEHICLE;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	//kumari
   public String releaseVehicles(MTApplicationBean mtBean) throws Exception{
		
		try{
			message = mtDAO.releaseVehicles(mtBean);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllAllocatedVehiclesList() throws Exception{
		List<MTVehicleAllocationtDetailsDTO> allotList = null;
		try{
			allotList = mtDAO.getAllAllocatedVehiclesList();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return allotList;
	}
	
	@SuppressWarnings("unchecked")
	public List getMileageList() throws Exception{
		List<VehicleMileageDTO> vmList = null;
		try{
			vmList = mtDAO.getMileageList();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vmList;
	}
	public String saveMileageDetails(MTApplicationBean mtBean) throws Exception{
		VehicleMileageDTO vmDTO = new VehicleMileageDTO();
		if(!mtBean.getPk().equals("")){
			vmDTO.setId(Integer.parseInt(mtBean.getPk()));
		}
			vmDTO.setVehicleId(Integer.parseInt(mtBean.getVehicleNo()));
			vmDTO.setTodayDate(new SimpleDateFormat("dd-MMM-yyyy").parse(mtBean.getTodayDate()));
			vmDTO.setNatureOfDuty(mtBean.getNatureOfDuty());
			vmDTO.setFromPlace(mtBean.getFromPlace());
			vmDTO.setToPlace(mtBean.getToPlace());
			vmDTO.setStartingMilometerRde(Integer.parseInt(mtBean.getStartingMilometerRde()));
			vmDTO.setEndingMilometerRde(Integer.parseInt(mtBean.getEndingMilometerRde()));
			vmDTO.setTotKilometers(Integer.parseInt(mtBean.getTotKilometers()));
			vmDTO.setFuelConsumed(Integer.parseInt(mtBean.getFuelConsumed()));
			
			if(!mtBean.getFuelConsumed().equals("0")){
			vmDTO.setFuel(Integer.parseInt(mtBean.getFuel()));
			vmDTO.setFuelMRD(Integer.parseInt(mtBean.getFuelMRD()));
			}
			vmDTO.setEngineOilConsumed(Integer.parseInt(mtBean.getEngOilConsumed()));
			
			if(!mtBean.getEngOilConsumed().equals("0")){
			vmDTO.setEngineOil(Integer.parseInt(mtBean.getEngineOil()));
			vmDTO.setEngineOilMRD(Integer.parseInt(mtBean.getEngineOilMRD()));
			}
			vmDTO.setCoolentConsumed(Integer.parseInt(mtBean.getCoolentConsumed()));
			if(!mtBean.getCoolentConsumed().equals("0")){
				vmDTO.setCoolent(Integer.parseInt(mtBean.getCoolent()));
				vmDTO.setCoolentMRD(Integer.parseInt(mtBean.getCoolentMRD()));
			}
			vmDTO.setLastModifiedBy(mtBean.getSfID());
			vmDTO.setCreationDate(CPSUtils.getCurrentDate());
			vmDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			vmDTO.setStatus(1);
		
		message = mtDAO.saveMileageDetails(vmDTO);
		return message;
	}
	public String deleteMileageDetails(MTApplicationBean mtBean) throws Exception{
		message = mtDAO.deleteMileageDetails(mtBean);
		return message;
	}
	
	public String checkVehicleRequesterDetails(MTApplicationBean mtBean) throws Exception {
		try{
			message = mtDAO.checkVehicleRequesterDetails(mtBean);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	public List<YearTypeDTO> getYearList() throws Exception {
		List<YearTypeDTO> yearList = null;
		try{
			yearList = mtDAO.getYearList();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return yearList;
	}
	public VehicleMileageDTO getPreDayClosingDetails(MTApplicationBean mtbean) throws Exception{
		String message = "";
		VehicleMileageDTO vehicleMileageDTO = null;
		List<VehicleMileageDTO> meterList = null;
		
		try{
			meterList = mtDAO.getMaxMeterReadings(mtbean);
			vehicleMileageDTO = mtDAO.getPreDayClosingDetails(mtbean);
			if(meterList.size()>0){
				vehicleMileageDTO.setMaxRdeList(meterList);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vehicleMileageDTO;
	}
	
	public List<MTRequestDetailsDTO> getApprovedRequests() throws Exception{
		List<MTRequestDetailsDTO> mtAppReqList = null;
		try{
			mtAppReqList = mtDAO.getApprovedRequests();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mtAppReqList;
	}
	
	//public List<MTVehicleDriverMapDTO> getAvailableVDListForParticularJourney(String journeyId) throws Exception{
	public List<MTVehicleDriverMapDTO> getAvailableVDListForParticularJourney(MTApplicationBean mtbean) throws Exception{
		List<MTVehicleDriverMapDTO> vdList = null;
		try{
			//vdList = mtDAO.getAvailableVDListForParticularJourney(journeyId);
			vdList = mtDAO.getAvailableVDListForParticularJourney(mtbean);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return vdList;
	}
			public List<MTVehicleAllocationtDetailsDTO> getVDListForCombineAlloc(MTApplicationBean mtbean) throws Exception{
				List<MTVehicleAllocationtDetailsDTO> allocList = null;
				try{
					allocList = mtDAO.getVDListForCombineAlloc(mtbean);
				}catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return allocList;
			}
	/*public List<MTVehicleAllocationtDetailsDTO> getAllocationDetails(MTApplicationBean mtbean) throws Exception{
		List<MTVehicleAllocationtDetailsDTO> allocationList = null;
		try{
			allocationList = mtDAO.getAllocationDetails(mtbean);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return allocationList;
	}
	*/
	@SuppressWarnings("deprecation")
	public String saveVehicleAllocationDetails(MTApplicationBean mtbean) throws Exception{
		String message = "";
		MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO=null;
		Calendar fromCal=null;
		Calendar toCal=null;
		try{
			
			//day wise allocation===KUMARI===
			
			/*SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			SimpleDateFormat sd2=new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cal=Calendar.getInstance();
			//=========for same day start===============
			if(CPSUtils.compareTwoDates(sd2.parse(mtbean.getFromDate()), sd2.parse(mtbean.getToDate()))){
				mtVehicleAllocationtDetailsDTO=mtDAO.getVehicleAllocationDetails(mtbean);
				//already exists a free record in alloction
				if(!CPSUtils.isNullOrEmpty(mtVehicleAllocationtDetailsDTO)){
					
					for(int i=0;i<3;i++){
						MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
						if(i==0){
							//new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
	                        if(CPSUtils.compareTwoDatesUptoDateWithTime(mtVehicleAllocationtDetailsDTO.getFromDate().toString(),new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+mtbean.getFromTime())))!=0){
	                        	mtVehicleAllocationtDetailsDTO2.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(mtVehicleAllocationtDetailsDTO.getFromDate())));
	    						cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
	    						cal.add(Calendar.MINUTE, -1);
	    						mtVehicleAllocationtDetailsDTO2.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
	    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
							}
							
						}else if(i==1){
							mtVehicleAllocationtDetailsDTO2.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
							mtVehicleAllocationtDetailsDTO2.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+mtbean.getToTime()));
							mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
						}else if(i==2){
							if(CPSUtils.compareTwoDatesUptoDateWithTime(mtVehicleAllocationtDetailsDTO.getToDate().toString(),new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+mtbean.getToTime())))!=0){
								int to_hrs=mtVehicleAllocationtDetailsDTO.getToDate().getHours();
								int to_mins=mtVehicleAllocationtDetailsDTO.getToDate().getMinutes();
								int min_diff=mtVehicleAllocationtDetailsDTO.getToDate().getMinutes()-Integer.parseInt(mtbean.getToTime().split(":")[1].trim());
								if(Integer.parseInt(mtbean.getToTime().split(":")[0].trim())==to_hrs){
									if(min_diff>1){
										cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
										cal.add(Calendar.MINUTE, 1);
										//mtbean.setToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
										mtVehicleAllocationtDetailsDTO2.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
										mtVehicleAllocationtDetailsDTO2.setToDate(mtVehicleAllocationtDetailsDTO.getToDate());
										mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
									}
									
								}else{
									cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
									cal.add(Calendar.MINUTE, 1);
									//mtbean.setToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
									mtVehicleAllocationtDetailsDTO2.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
									mtVehicleAllocationtDetailsDTO2.setToDate(mtVehicleAllocationtDetailsDTO.getToDate());
									mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
								}
								
							}
							
						}
						if(mtVehicleAllocationtDetailsDTO2.getFromDate()!=null){
							mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
							mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
							mtVehicleAllocationtDetailsDTO2.setRequestType(0);
						
						message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
					    }
					}
					
				}
				//no record exists as free in alloction
				else{
					for(int i=0;i<3;i++){
						mtVehicleAllocationtDetailsDTO=new MTVehicleAllocationtDetailsDTO();
						if(i==0){
							if(Integer.parseInt(mtbean.getFromTime().split(":")[0].trim())==0){
								if(Integer.parseInt(mtbean.getFromTime().split(":")[1].trim())>2){
									mtVehicleAllocationtDetailsDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" 00:01"));
									cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
									cal.add(Calendar.MINUTE, -1);
									//mtbean.setToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
									mtVehicleAllocationtDetailsDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
									mtVehicleAllocationtDetailsDTO.setStatusFlag(20);
								}
							}else{
								mtVehicleAllocationtDetailsDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" 00:01"));
								cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
								cal.add(Calendar.MINUTE, -1);
								//mtbean.setToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
								mtVehicleAllocationtDetailsDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
								mtVehicleAllocationtDetailsDTO.setStatusFlag(20);
							}
							
						}else if(i==1){
							mtVehicleAllocationtDetailsDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
							mtVehicleAllocationtDetailsDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+mtbean.getToTime()));
							mtVehicleAllocationtDetailsDTO.setStatusFlag(25);
						}else if(i==2 && (Integer.parseInt(mtbean.getToTime().split(":")[0].trim())<=23 && Integer.parseInt(mtbean.getToTime().split(":")[1].trim())<58)){
							cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
							cal.add(Calendar.MINUTE, 1);
							//mtbean.setToTime(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
							mtVehicleAllocationtDetailsDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
							mtVehicleAllocationtDetailsDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" 23:59"));
							mtVehicleAllocationtDetailsDTO.setStatusFlag(20);
						}
						if(mtVehicleAllocationtDetailsDTO.getFromDate()!=null){
						mtVehicleAllocationtDetailsDTO.setJourneyId(mtbean.getJourneyId());
						mtVehicleAllocationtDetailsDTO.setVehicleDriverMapId(mtbean.getMapId());
						mtVehicleAllocationtDetailsDTO.setRequestType(0);
						
						message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO);	
					    }
					}
				}
			}//=========for same day end===============
			
			//===========for different day start=============
			else if(!CPSUtils.compareTwoDates(sd2.parse(mtbean.getFromDate()), sd2.parse(mtbean.getToDate()))){
				MTVehicleAllocationtDetailsDTO mtVehicleAllocationtFrom= new MTVehicleAllocationtDetailsDTO();
				MTVehicleAllocationtDetailsDTO mtVehicleAllocationtTo= new MTVehicleAllocationtDetailsDTO();
				List<MTVehicleAllocationtDetailsDTO> allocationtDetailsList=mtDAO.getVehicleAllocationDetailsForDiffDates(mtbean);
				
				
					//already exists a free record in alloction
					if(allocationtDetailsList.size()>0){
						mtVehicleAllocationtFrom=mtDAO.getVehicleAllocationDetailsForPerticulerDate(mtbean.getFromDate()+" "+mtbean.getFromTime(),mtbean.getMapId());
						//fromdate exists case
						if(!CPSUtils.isNullOrEmpty(mtVehicleAllocationtFrom)){
						
							for(int i=0;i<2;i++){
								MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
								if(i==0){
									 if(CPSUtils.compareTwoDatesUptoDateWithTime(mtVehicleAllocationtFrom.getFromDate().toString(),new SimpleDateFormat("yyyy-MM-dd HH:mm").format(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime())))!=0){
			                        	mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(sd.format(mtVehicleAllocationtFrom.getFromDate())));
			    						cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
			    						cal.add(Calendar.MINUTE, -1);
			    						mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
			    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
									 }
									
								}else if(i==1){
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
									mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(sd.format(mtVehicleAllocationtFrom.getToDate())));
									mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
								}
								if(mtVehicleAllocationtDetailsDTO2.getFromDate()!=null){
									mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
									mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
									mtVehicleAllocationtDetailsDTO2.setRequestType(0);
								
								message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
							    }
							}
						}
						//fromdate not existed
						else{
							for(int i=0;i<2;i++){
							MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
							if(i==0){
								if(Integer.parseInt(mtbean.getFromTime().split(":")[0].trim())==0){
									if(Integer.parseInt(mtbean.getFromTime().split(":")[1].trim())>2){
								mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" 00:01"));
								cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
	    						cal.add(Calendar.MINUTE, -1);
	    						mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
	    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
									}
								}else{
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" 00:01"));
									cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
		    						cal.add(Calendar.MINUTE, -1);
		    						mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
		    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
								}
							}
							if(i==1){
								if(Integer.parseInt(mtbean.getFromTime().split(":")[0])==23){
									if(Integer.parseInt(mtbean.getFromTime().split(":")[0])<58){
								mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
								mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" 23:59"));
								mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);	
									}
								}else{
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
									mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" 23:59"));
									mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);	
								}
							}
							if(mtVehicleAllocationtDetailsDTO2.getFromDate()!=null){
								mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
								mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
								mtVehicleAllocationtDetailsDTO2.setRequestType(0);
							
							message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
							}
							}
						}
						mtVehicleAllocationtTo=mtDAO.getVehicleAllocationDetailsForPerticulerDate(mtbean.getToDate()+" "+mtbean.getToTime(),mtbean.getMapId());
						//todate exists case
						if(!CPSUtils.isNullOrEmpty(mtVehicleAllocationtTo)){
							
							for(int i=0;i<2;i++){
								MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
								if(i==0){
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" 00:01"));
									mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime()));
									mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
									
									
								}else if(i==1){
									 if(CPSUtils.compareTwoDatesUptoDateWithTime(mtVehicleAllocationtTo.getToDate().toString(),new SimpleDateFormat("yyyy-MM-dd HH:mm").format(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime())))!=0){

										    int to_hrs=mtVehicleAllocationtTo.getToDate().getHours();
											int to_mins=mtVehicleAllocationtTo.getToDate().getMinutes();
											int min_diff=mtVehicleAllocationtTo.getToDate().getMinutes()-Integer.parseInt(mtbean.getToTime().split(":")[1].trim());
											if(Integer.parseInt(mtbean.getToTime().split(":")[0].trim())==to_hrs){
												if(min_diff>1){				
										    cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
				    						cal.add(Calendar.MINUTE, 1);
											mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
											mtVehicleAllocationtDetailsDTO.setToDate(sd.parse(sd.format(mtVehicleAllocationtTo.getToDate())));
											mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
												}
									 }
											else{
												cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
					    						cal.add(Calendar.MINUTE, 1);
												mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
												mtVehicleAllocationtDetailsDTO.setToDate(sd.parse(sd.format(mtVehicleAllocationtTo.getToDate())));
												mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
											}
									 }
								}
								if(mtVehicleAllocationtDetailsDTO2.getFromDate()!=null){
									mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
									mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
									mtVehicleAllocationtDetailsDTO2.setRequestType(0);
								
								message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
							    }
							}
						}
						//todate not existed JHJJGJ
						else{
							for(int i=0;i<2;i++){
								MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
								if(i==0){
									if(Integer.parseInt(mtbean.getToTime().split(":")[0].trim())==0){
										if(Integer.parseInt(mtbean.getToTime().split(":")[1].trim())>2){
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" 00:01"));
									mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime()));
		    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
										}
									}else{
										mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" 00:01"));
										mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime()));
			    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
									}
								}
								if(i==1){
									if(Integer.parseInt(mtbean.getToTime().split(":")[0])==23){
										if(Integer.parseInt(mtbean.getToTime().split(":")[0])<58){
											cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
				    						cal.add(Calendar.MINUTE, 1);
											mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
											mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" 23:59"));
											mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);	
										}
										
									}else{
										cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
			    						cal.add(Calendar.MINUTE, 1);
										mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
										mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" 23:59"));
										mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);	
									}
									
								}
								if(mtVehicleAllocationtDetailsDTO2.getFromDate()!=null){
									mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
									mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
									mtVehicleAllocationtDetailsDTO2.setRequestType(0);
								
								message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
								}
							}

						}
						//------if days between requested from date and todate greater than 1------
						if(Integer.parseInt(CPSUtils.daysDifference(mtbean.getFromDate(), mtbean.getToDate()))>=1){
							MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
							String fromDate=CPSUtils.nextDate(mtbean.getFromDate());
							String toDate=CPSUtils.previousDate(mtbean.getToDate());
							
							mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(fromDate+" 00:01"));
							mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(fromDate+" 23:59"));
							mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);	
							
							mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
							mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
							mtVehicleAllocationtDetailsDTO2.setRequestType(0);
						
						message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
						}
					}
					//no record exists as free in alloction
					else{
						//------if 1 day between requested from date and todate------
						
							for(int i=0;i<4;i++){
								MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
								if(i==0){
									//check when requested from time is less than or equal to 00:02 
									if(Integer.parseInt(mtbean.getFromTime().split(":")[0].trim())==0){
										if(Integer.parseInt(mtbean.getFromTime().split(":")[1].trim())>2){
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" 00:01"));
									cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
		    						cal.add(Calendar.MINUTE, -1);
		    						mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
		    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
										}
									}else{
										mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" 00:01"));
										cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
			    						cal.add(Calendar.MINUTE, -1);
			    						mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
			    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
									}
								}
								if(i==1){
									if(Integer.parseInt(mtbean.getFromTime().split(":")[0])==23){
										if(Integer.parseInt(mtbean.getFromTime().split(":")[0])<58){
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
								//	mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" 23:59"));---//find prob while deallocation 
									mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" 23:59"));
									mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);	
										}
									}else{
										mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
										//mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" 23:59"));---//find prob while deallocation
										mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getFromDate()+" 23:59"));
										mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);	
									}
								}
								if(i==2){
									if(Integer.parseInt(mtbean.getToTime().split(":")[0].trim())==0){
										if(Integer.parseInt(mtbean.getToTime().split(":")[1].trim())>2){
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" 00:01"));
									mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime()));
		    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
										}
									}else{
										mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" 00:01"));
										mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime()));
			    						mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
									}
								}
								if(i==3 && (Integer.parseInt(mtbean.getToTime().split(":")[0].trim())<=23 && Integer.parseInt(mtbean.getToTime().split(":")[1].trim())<58)){
									
									cal.set(0,0,0,Integer.parseInt(mtbean.getToTime().split(":")[0]),Integer.parseInt(mtbean.getToTime().split(":")[1].trim()));
		    						cal.add(Calendar.MINUTE, 1);
									mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getToDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)));
									mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" 23:59"));
									mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);	
								}
								if(mtVehicleAllocationtDetailsDTO2.getFromDate()!=null){
									mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
									mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
									mtVehicleAllocationtDetailsDTO2.setRequestType(0);
								
								message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
								}
								}
							//------if days between requested from date and todate greater than 1------
							if(Integer.parseInt(CPSUtils.daysDifference(mtbean.getFromDate(), mtbean.getToDate()))>=1){
								MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
								String fromDate=CPSUtils.nextDate(mtbean.getFromDate());
								String toDate=CPSUtils.previousDate(mtbean.getToDate());
								
								mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(fromDate+" 00:01"));
								//mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(fromDate+" 23:59"));---commented on 11-09-2013
								mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(toDate+" 23:59"));
								mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);	
								
								mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
								mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
								mtVehicleAllocationtDetailsDTO2.setRequestType(0);
							
							message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
							}
						
					}
					
			}//===========for different day end=============
*/			
			SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			Calendar cal = Calendar.getInstance();
			//if free record exists(if vehicle is dedicated vehicle free record exist)
			//MTVehicleAllocationtDetailsDTO existAllocationtDetailsDTO=mtDAO.getVehicleAllocationDetails(mtbean);
			/*if(!CPSUtils.isNullOrEmpty(existAllocationtDetailsDTO)){
				for(int i=0;i<3;i++){
					MTVehicleAllocationtDetailsDTO mtVehicleAllocationtDetailsDTO2=new MTVehicleAllocationtDetailsDTO();
					if(i==0){
						if(CPSUtils.compareTwoDatesUptoDateWithTime(existAllocationtDetailsDTO.getFromDate().toString(),new SimpleDateFormat("yyyy-MM-dd HH:mm").format(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime())))!=0){
						mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(sd.format(existAllocationtDetailsDTO.getFromDate())));
						//cal.set(0,0,0,Integer.parseInt(mtbean.getFromTime().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
						cal.setTime(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
						cal.add(Calendar.MINUTE, -1);
						if(cal.get(Calendar.HOUR_OF_DAY)==0 && cal.get(Calendar.MINUTE)==0){
							mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(CPSUtils.previousDate(mtbean.getFromDate())+" 23:59"));
						}else{
							mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(sd.format(cal.getTime())));
						}
						
						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
						}
						
					}else if(i==1){
						mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
						mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime()));
						mtVehicleAllocationtDetailsDTO2.setStatusFlag(25);
					}else if(i==2){
						if(CPSUtils.compareTwoDatesUptoDateWithTime(existAllocationtDetailsDTO.getToDate().toString(),new SimpleDateFormat("yyyy-MM-dd HH:mm").format(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime())))!=0){
						//cal.set(0,0,0,Integer.parseInt(mtbean.getToDate().split(":")[0]),Integer.parseInt(mtbean.getFromTime().split(":")[1].trim()));
						cal.setTime(sd.parse(mtbean.getToDate()+" "+mtbean.getToTime()));
						cal.add(Calendar.MINUTE, 1);
						if(cal.get(Calendar.HOUR_OF_DAY)==0 && cal.get(Calendar.MINUTE)==0){
							mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(CPSUtils.nextDate(mtbean.getToDate())+" 00:01"));
						}else{
							mtVehicleAllocationtDetailsDTO2.setFromDate(sd.parse(sd.format(cal.getTime())));
						}
						
						mtVehicleAllocationtDetailsDTO2.setToDate(sd.parse(sd.format(existAllocationtDetailsDTO.getToDate())));
						mtVehicleAllocationtDetailsDTO2.setStatusFlag(20);
						}
					}
					if(mtVehicleAllocationtDetailsDTO2.getFromDate()!=null){
						mtVehicleAllocationtDetailsDTO2.setJourneyId(mtbean.getJourneyId());
						mtVehicleAllocationtDetailsDTO2.setVehicleDriverMapId(mtbean.getMapId());
						mtVehicleAllocationtDetailsDTO2.setRequestType(0);
					
					message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO2);	
					mtDAO.updateJourneyDetails(mtVehicleAllocationtDetailsDTO2.getJourneyId(),mtVehicleAllocationtDetailsDTO2.getId());
				    }
				}
			}*/
			//if free record not exists
			
			mtVehicleAllocationtDetailsDTO = new MTVehicleAllocationtDetailsDTO();
            mtVehicleAllocationtDetailsDTO.setFromDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getFromDate()+" "+mtbean.getFromTime()));
            mtVehicleAllocationtDetailsDTO.setToDate(new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(mtbean.getToDate()+" "+mtbean.getToTime()));
            mtVehicleAllocationtDetailsDTO.setStatusFlag(25);
			mtVehicleAllocationtDetailsDTO.setJourneyId(mtbean.getJourneyId());
			mtVehicleAllocationtDetailsDTO.setVehicleDriverMapId(mtbean.getMapId());
			mtVehicleAllocationtDetailsDTO.setRequestType(0);
			message=mtDAO.saveVehicleAllocationDetails(mtVehicleAllocationtDetailsDTO);	
			mtDAO.updateJourneyDetails(mtVehicleAllocationtDetailsDTO.getJourneyId(),mtVehicleAllocationtDetailsDTO.getId());
			
		}catch(Exception e){
			message="allocateFail";
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	public String getEmpExist(String sfid) throws Exception {
		
		try{
			message=mtDAO.getEmpExist(sfid);
			if(CPSUtils.compareStrings(message, CPSConstants.NO)){
				message=CPSConstants.INVALID;
			}
		}
		catch(Exception e){
			message=CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	
public EmployeeBean getEmpDetails(String sfid) throws Exception {
	EmployeeBean empBean = null;
		try{
			empBean=mtDAO.getEmpDetails(sfid);
		}
		catch(Exception e){
			message=CPSConstants.FAILED;
			e.printStackTrace();
			throw e;
		}
		return empBean;
	}
	public List<MTRequestDetailsDTO> getAllocatedRequests() throws Exception{
		List<MTRequestDetailsDTO> mtAllocatedReqList = null;
		try{
			mtAllocatedReqList = mtDAO.getAllocatedRequests();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mtAllocatedReqList;
	}
	public String userCancelJourney(MTApplicationBean mtbean) throws Exception{
		try{
			message=mtDAO.userCancelJourney(mtbean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	public List<MTRequestDetailsDTO> getCanceledRequests() throws Exception{
		List<MTRequestDetailsDTO> mtCanceledJourneyList = null;
		try{
			mtCanceledJourneyList = mtDAO.getCanceledRequests();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mtCanceledJourneyList;
	}
	public String deallocateVehicle(MTApplicationBean mtbean) throws Exception{
		try{
			message=mtDAO.deallocateVehicle(mtbean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	public List<EmployeeBean> getAllEmployeeList() throws Exception {
		List<EmployeeBean> empList = null;
		try{
			empList = mtDAO.getAllEmployeeList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	} 
	@SuppressWarnings("unchecked")
	public List getDedicatedVehicles() throws Exception{
		List<MTVehicleAllocationtDetailsDTO> allotList = null;
		try{
			allotList = mtDAO.getDedicatedVehicles();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return allotList;
	}
	public String freeDedicatedVehicles(MTApplicationBean mtbean) throws Exception{
		try{
			message=mtDAO.freeDedicatedVehicles(mtbean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	public String saveCombineAlloc(MTApplicationBean mtbean) throws Exception{
		try{
			message=mtDAO.saveCombineAlloc(mtbean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	public String checkNextAllocationForRelease(MTApplicationBean mtbean) throws Exception{
		try{
			message=mtDAO.checkNextAllocationForRelease(mtbean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	public String updateRequestDetails(MTApplicationBean mtbean) throws Exception{
		try{
			message=mtDAO.updateRequestDetails(mtbean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	public String updateVehicleAllocationDetails(MTApplicationBean mtbean) throws Exception{
		try{
			message=mtDAO.updateVehicleAllocationDetails(mtbean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	public List getVehicleTypeList() throws Exception{
		List<KeyValueDTO> vehicleTypeList=null;
		try{
			vehicleTypeList=mtDAO.getVehicleTypeList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return vehicleTypeList;
	}
	
}
