package com.callippus.web.tada.business.management;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.tada.beans.management.TadaManagementBean;
import com.callippus.web.tada.dao.management.ITadaManagementDAO;
import com.callippus.web.tada.dto.CityTypeDTO;
import com.callippus.web.tada.dto.DaOnTourDTO;
import com.callippus.web.tada.dto.LocalRMADTO;
import com.callippus.web.tada.dto.ProgramDTO;
import com.callippus.web.tada.dto.TaEntitleClassDTO;
import com.callippus.web.tada.dto.TaEntitleExemptionDTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TaMapDTO;
import com.callippus.web.tada.dto.TaTravelTypeMapDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaDetailsDTO;
import com.callippus.web.tada.dto.TravelTypeDTO;
@Service
public class TadaManagementBusiness {
	@Autowired
	private ITadaManagementDAO tadaManagementDAO;
	@Autowired
	private IComonObjectDAO commonDAO;
	public TadaManagementBean submitDaDetails(TadaManagementBean tadaBean)throws Exception {
		try{
			tadaBean.setResult(tadaManagementDAO.checkDaDetails(tadaBean));
			if(!CPSUtils.compareStrings(tadaBean.getResult(),CPSConstants.DUPLICATE)){
				TadaDetailsDTO tadaDetailsDTO=new TadaDetailsDTO();
				if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
					tadaDetailsDTO.setId(Integer.valueOf(tadaBean.getNodeID()));
					tadaDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}
				tadaDetailsDTO.setCityTypeId(tadaBean.getCityClassId());
				tadaDetailsDTO.setPayRangeFrom(tadaBean.getPayRangeFrom());
				tadaDetailsDTO.setPayRangeTo(tadaBean.getPayRangeTo());
				tadaDetailsDTO.setOrd(tadaBean.getOrd());
				tadaDetailsDTO.setHotel(tadaBean.getHotel());
				tadaDetailsDTO.setStatus(1);
				tadaDetailsDTO.setCreatedBy(tadaBean.getSfID());
				tadaDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
				tadaDetailsDTO.setLastModifiedBy(tadaBean.getSfID());
				tadaDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				if(tadaDetailsDTO.getId()==0){
					tadaBean.setResult(tadaManagementDAO.submitDaDetails(tadaDetailsDTO));
				} else {
					tadaManagementDAO.submitDaDetails(tadaDetailsDTO);
					tadaBean.setResult(CPSConstants.UPDATE);
				}
				
			}
			
		
		}catch(Exception e){
			throw e;
		}
		return tadaBean;
	}
	public List<TadaDetailsDTO> getDaDetails(TadaManagementBean tadaBean) throws Exception{
		TadaDetailsDTO tadaDetailsDTO=new TadaDetailsDTO();
		try{
			tadaBean.setDaDetailsList(tadaManagementDAO.getDaDetails(tadaDetailsDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getDaDetailsList();
	}
	public String deleteDaDetails(TadaManagementBean tadaBean) throws Exception {
		try {
			tadaBean.setResult(tadaManagementDAO.deleteDaDetails(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public List<EmpPaymentsDTO> getGradePayDetails(TadaManagementBean tadaBean) throws Exception{
		EmpPaymentsDTO empPaymentsDTO=new EmpPaymentsDTO();
		try{
			tadaBean.setGradePayList(tadaManagementDAO.getGradePayDetails(empPaymentsDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getGradePayList();
	}
	public List<EmpPaymentsDTO> getUniqueGradePayDetails(TadaManagementBean tadaBean) throws Exception{
		EmpPaymentsDTO empPaymentsDTO=new EmpPaymentsDTO();
		try{
			
			tadaBean.setGradePayList(tadaManagementDAO.getUniqueGradePayDetails(empPaymentsDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getGradePayList();
	}
	public TadaManagementBean submitEntitleType(TadaManagementBean tadaBean)throws Exception{
		try{
			tadaBean.setResult(tadaManagementDAO.checkEntitleType(tadaBean));
			if(!CPSUtils.compareStrings(tadaBean.getResult(),CPSConstants.DUPLICATE)){
				TaEntitleTypeDTO taEntitleTypeDTO=new TaEntitleTypeDTO();
				if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
					taEntitleTypeDTO.setId(Integer.valueOf(tadaBean.getNodeID()));
					taEntitleTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}
				taEntitleTypeDTO.setEntitleTypeId(tadaBean.getEntitleTypeId());
				taEntitleTypeDTO.setEntitleClass(tadaBean.getEntitleClass());
				taEntitleTypeDTO.setDescription(tadaBean.getDescription());
				taEntitleTypeDTO.setStatus(1);
				taEntitleTypeDTO.setCreatedBy(tadaBean.getSfID());
				taEntitleTypeDTO.setCreationDate(CPSUtils.getCurrentDate());
				taEntitleTypeDTO.setLastModifiedBy(tadaBean.getSfID());
				taEntitleTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				if(taEntitleTypeDTO.getId()==0){
					tadaBean.setResult(tadaManagementDAO.submitEntitleType(taEntitleTypeDTO));
				} else {
					tadaManagementDAO.submitEntitleType(taEntitleTypeDTO);
					tadaBean.setResult(CPSConstants.UPDATE);
				}
				
				
			}
		}catch(Exception e){
			throw e;
		}
		return tadaBean;
	}
	public List<TaEntitleTypeDTO> getEntitleTypeList(TadaManagementBean tadaBean)throws Exception {
		TaEntitleTypeDTO taEntitleTypeDTO=new TaEntitleTypeDTO();
		try{
			tadaBean.setEntitleTypeList(tadaManagementDAO.getEntitleType(taEntitleTypeDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getEntitleTypeList();
	}
	public List<TaEntitleTypeDTO> getEntitleClassList(TadaManagementBean tadaBean)throws Exception {
		TaEntitleTypeDTO taEntitleTypeDTO=new TaEntitleTypeDTO();
		try{
			tadaBean.setEntitleTypeList(tadaManagementDAO.getEntitleClass(tadaBean));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getEntitleTypeList();
	}
	
	public String deleteEntitleType(TadaManagementBean tadaBean) throws Exception {
		try {
			tadaBean.setResult(tadaManagementDAO.deleteEntitleType(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public TadaManagementBean submitCityType(TadaManagementBean tadaBean)throws Exception {
		try{
			tadaBean.setResult(tadaManagementDAO.checkCityType(tadaBean));
			if(!CPSUtils.compareStrings(tadaBean.getResult(),CPSConstants.DUPLICATE)){
				CityTypeDTO cityTypeDTO=new CityTypeDTO();
				if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
					cityTypeDTO.setId1(Integer.valueOf(tadaBean.getNodeID()));
					cityTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}
				if(CPSUtils.compareStrings(tadaBean.getCityClass(), "A1 Class")){
					cityTypeDTO.setId(1);
				}else if(CPSUtils.compareStrings(tadaBean.getCityClass(), "A Class")){
					cityTypeDTO.setId(2);
				}else if(CPSUtils.compareStrings(tadaBean.getCityClass(), "B1 Class")){
					cityTypeDTO.setId(3);
				}else if(CPSUtils.compareStrings(tadaBean.getCityClass(), "Other")){
					cityTypeDTO.setId(4);
				}
				cityTypeDTO.setCityClass(tadaBean.getCityClass());
				cityTypeDTO.setCityName(tadaBean.getCityName());
				cityTypeDTO.setStatus(1);
				cityTypeDTO.setCreatedBy(tadaBean.getSfID());
				cityTypeDTO.setCreationDate(CPSUtils.getCurrentDate());
				cityTypeDTO.setLastModifiedBy(tadaBean.getSfID());
				cityTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				if(cityTypeDTO.getId1()==0){
					tadaBean.setResult(tadaManagementDAO.submitCityType(cityTypeDTO));
				} else {
					tadaManagementDAO.submitCityType(cityTypeDTO);
					tadaBean.setResult(CPSConstants.UPDATE);
				}
				
			}
			
		
		}catch(Exception e){
			throw e;
		}
		return tadaBean;
	}
	public List<CityTypeDTO> getCityTypeList(TadaManagementBean tadaBean)throws Exception {
		CityTypeDTO cityTypeDTO=new CityTypeDTO();
		try{
			tadaBean.setCityTypeList(tadaManagementDAO.getCityTypeList(cityTypeDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getCityTypeList();
	}
	public List<TadaManagementBean> cityTypeList(TadaManagementBean tadaBean)throws Exception {
		CityTypeDTO cityTypeDTO=new CityTypeDTO();
		try{
			tadaBean.setCityClassList(tadaManagementDAO.cityTypeList(tadaBean));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getCityClassList();
	}
	public String deleteCityType(TadaManagementBean tadaBean) throws Exception {
		try {
			tadaBean.setResult(tadaManagementDAO.deleteCityType(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public List<CityTypeDTO> getUniqueCityTypeList(TadaManagementBean tadaBean)throws Exception {
		CityTypeDTO cityTypeDTO=new CityTypeDTO();
		try{
			tadaBean.setUniqueCityTypeList(tadaManagementDAO.getUniqueCityTypeList(cityTypeDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getUniqueCityTypeList();
	}
	public TadaManagementBean submitTravelType(TadaManagementBean tadaBean)throws Exception {
		try{
			tadaBean.setResult(tadaManagementDAO.checkTravelType(tadaBean));
			if(!CPSUtils.compareStrings(tadaBean.getResult(),CPSConstants.DUPLICATE)){
				TravelTypeDTO travelTypeDTO=new TravelTypeDTO();
				if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
					travelTypeDTO.setId(Integer.valueOf(tadaBean.getNodeID()));
					travelTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}
				
				travelTypeDTO.setTravelType(tadaBean.getTravelType());
				travelTypeDTO.setDescription(tadaBean.getDescription());
				travelTypeDTO.setStatus(1);
				travelTypeDTO.setCreatedBy(tadaBean.getSfID());
				travelTypeDTO.setCreationDate(CPSUtils.getCurrentDate());
				travelTypeDTO.setLastModifiedBy(tadaBean.getSfID());
				travelTypeDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				if(travelTypeDTO.getId()==0){
					tadaBean.setResult(tadaManagementDAO.submitTravelType(travelTypeDTO));
				} else {
					tadaManagementDAO.submitTravelType(travelTypeDTO);
					tadaBean.setResult(CPSConstants.UPDATE);
				}
				
			}
			
		
		}catch(Exception e){
			throw e;
		}
		return tadaBean;
	}
	public List<TravelTypeDTO> getTravelTypeList(TadaManagementBean tadaBean)throws Exception {
		TravelTypeDTO travelTypeDTO=new TravelTypeDTO();
		try{
			
			tadaBean.setTravelTypeList(tadaManagementDAO.getTravelTypeList(travelTypeDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getTravelTypeList();
	}
	public String deleteTravelType(TadaManagementBean tadaBean) throws Exception {
		try {
			tadaBean.setResult(tadaManagementDAO.deleteTravelType(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public TadaManagementBean submitTravelTypeMap(TadaManagementBean tadaBean)throws Exception{
		try {
			List<TaTravelTypeMapDTO> list = new ArrayList<TaTravelTypeMapDTO>();
			JSONObject mainjson = new JSONObject(tadaBean.getExceptionsJson());
			TaTravelTypeMapDTO taTravelTypeMapDTO = new TaTravelTypeMapDTO();
			if (!CPSUtils.isNull(mainjson)) {
				for (int i = 0; i < mainjson.length(); i++) {
					JSONObject items = (JSONObject) mainjson.get(String.valueOf(i));
					
					int gradePay=Integer.parseInt(items.get("gradePay").toString());
					taTravelTypeMapDTO.setGradePay(gradePay);
					taTravelTypeMapDTO.setKey(items.getInt("key"));
					taTravelTypeMapDTO.setValue(items.getInt("value"));
					taTravelTypeMapDTO.setTravelTypeId(items.getInt("travelTypeId"));
					taTravelTypeMapDTO.setCreatedBy(tadaBean.getSfID());
					taTravelTypeMapDTO.setLastModifiedBy(tadaBean.getSfID());
					taTravelTypeMapDTO.setCreationDate(CPSUtils.getCurrentDate());
					taTravelTypeMapDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					if(CPSUtils.compareStrings(items.getString("type"), "check")){
						taTravelTypeMapDTO.setStatus(1);
					} else {
						taTravelTypeMapDTO.setStatus(0);
					}
					tadaBean.setResult(tadaManagementDAO.submitTravelTypeMap(taTravelTypeMapDTO));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean;
	}
	public List<TaMapDTO> getTravelTypeMapList()throws Exception{
		List<TaMapDTO> list=null;
		try{
			list=tadaManagementDAO.getTravelTypeMapList();
		} catch(Exception e){
			throw e;
		}
		return list;
	}
	public List<TaMapDTO> getTravelTypeUnMapList()throws Exception{
		List<TaMapDTO> list=null;
		try{
			list=tadaManagementDAO.getTravelTypeUnMapList();
		} catch(Exception e){
			throw e;
		}
		return list;
	}
	public TadaManagementBean submitEntitlementClass(TadaManagementBean tadaBean)throws Exception{
		try{
			List <TaEntitleClassDTO>list=new ArrayList<TaEntitleClassDTO>();
			List <TaEntitleClassDTO>list1=new ArrayList<TaEntitleClassDTO>();
			TaEntitleClassDTO taEntitleClassDTO=null;
			//tadaBean.setResult(tadaManagementDAO.checkTravelType(tadaBean));
			if(!CPSUtils.compareStrings(tadaBean.getResult(),CPSConstants.DUPLICATE)){
				if(!CPSUtils.isNullOrEmpty(tadaBean.getEntitleClass())){
					String entitleClass[]=tadaBean.getEntitleClass().split(",");
					for(int i=0;i<entitleClass.length;i++){
						taEntitleClassDTO=new TaEntitleClassDTO();
						taEntitleClassDTO.setEntitleClassId(Integer.parseInt(entitleClass[i]));
						taEntitleClassDTO.setGradePay(Integer.parseInt(tadaBean.getGradePay()));
						taEntitleClassDTO.setEntitleTypeId(tadaBean.getTravelTypeId());
						taEntitleClassDTO.setCreatedBy(tadaBean.getSfID());
						taEntitleClassDTO.setCreationDate(CPSUtils.getCurrentDate());
						taEntitleClassDTO.setLastModifiedBy(tadaBean.getSfID());
						taEntitleClassDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						taEntitleClassDTO.setStatus(1);
						list.add(taEntitleClassDTO);
					}
					tadaBean.setResult(tadaManagementDAO.submitEntitlementClass(list));
				}
			}
			if(!CPSUtils.isNullOrEmpty(tadaBean.getNonEntitleClass())){
				String nonEntitleClass[]=tadaBean.getNonEntitleClass().split(",");
				for(int i=0;i<nonEntitleClass.length;i++){
					taEntitleClassDTO=new TaEntitleClassDTO();
					taEntitleClassDTO.setEntitleClassId(Integer.parseInt(nonEntitleClass[i]));
					taEntitleClassDTO.setGradePay(Integer.parseInt(tadaBean.getGradePay()));
					taEntitleClassDTO.setEntitleTypeId(tadaBean.getTravelTypeId());
					taEntitleClassDTO.setCreatedBy(tadaBean.getSfID());
					taEntitleClassDTO.setCreationDate(CPSUtils.getCurrentDate());
					taEntitleClassDTO.setLastModifiedBy(tadaBean.getSfID());
					taEntitleClassDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					taEntitleClassDTO.setStatus(0);
					list1.add(taEntitleClassDTO);
				}
				tadaBean.setResult(tadaManagementDAO.submitNonEntitlementClass(list1));
			}
		
		}catch(Exception e){
			throw e;
		}
		return tadaBean;
	}
	public List<TaEntitleClassDTO> getEntitlementClassList(TadaManagementBean tadaBean)throws Exception {
		TaEntitleClassDTO taEntitleClassDTO=new TaEntitleClassDTO();
		try {
			if(CPSUtils.isNullOrEmpty(tadaBean.getGradePay()) || CPSUtils.compareStrings("Select", tadaBean.getGradePay())){
				taEntitleClassDTO.setEntitleClassList(tadaManagementDAO.getEntitlementClassList());
			} else {
				taEntitleClassDTO.setGradePay(Integer.parseInt(tadaBean.getGradePay()));
				taEntitleClassDTO.setEntitleClassList(tadaManagementDAO.getEntitlementClassList(taEntitleClassDTO));
			}
			
			
		} catch(Exception e){
			throw e;
		}
		return taEntitleClassDTO.getEntitleClassList();
	}
	public List<TaEntitleClassDTO> getEntitlementList(TadaManagementBean tadaBean)throws Exception {
		TaEntitleClassDTO taEntitleClassDTO=new TaEntitleClassDTO();
		try {
			taEntitleClassDTO.setEntitleClassList(tadaManagementDAO.getEntitlementList(taEntitleClassDTO));
		} catch(Exception e){
			throw e;
		}
		return taEntitleClassDTO.getEntitleClassList();
	}
	public List<TaEntitleClassDTO> getEntitlementClassLists(TadaManagementBean tadaBean)throws Exception {
		TaEntitleClassDTO taEntitleClassDTO=new TaEntitleClassDTO();
		try {
			taEntitleClassDTO.setGradePay(Integer.parseInt(tadaBean.getGradePay()));
			taEntitleClassDTO.setEntitleClassList(tadaManagementDAO.getEntitlementClassLists(taEntitleClassDTO));
			
		} catch(Exception e){
			throw e;
		}
		return taEntitleClassDTO.getEntitleClassList();
	}
	public TadaManagementBean submitDaNewDetails(TadaManagementBean tadaBean)throws Exception {
		try{
			tadaBean.setResult(tadaManagementDAO.checkDaNewDetails(tadaBean));
			if(!CPSUtils.compareStrings(tadaBean.getResult(),CPSConstants.DUPLICATE)){
				TadaDaNewDetailsDTO tadaDaNewDetailsDTO=new TadaDaNewDetailsDTO();
				if (!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) {
					tadaDaNewDetailsDTO.setId(Integer.valueOf(tadaBean.getNodeID()));
					tadaDaNewDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				}
				tadaDaNewDetailsDTO.setGradePay(Integer.parseInt(tadaBean.getGradePay()));
				tadaDaNewDetailsDTO.setAccommodationBill(tadaBean.getAccommodationBill());
				tadaDaNewDetailsDTO.setFoodBill(tadaBean.getFoodBill());
				tadaDaNewDetailsDTO.setMilageAllw(tadaBean.getMilageAllw());
				tadaDaNewDetailsDTO.setTravelBy(tadaBean.getTravelBy());
				tadaDaNewDetailsDTO.setDistance(tadaBean.getDistance());
				tadaDaNewDetailsDTO.setAmount(tadaBean.getAmount());
				tadaDaNewDetailsDTO.setStatus(1);
				tadaDaNewDetailsDTO.setCreatedBy(tadaBean.getSfID());
				tadaDaNewDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
				tadaDaNewDetailsDTO.setLastModifiedBy(tadaBean.getSfID());
				tadaDaNewDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				if(tadaDaNewDetailsDTO.getId()==0){
					tadaBean.setResult(tadaManagementDAO.submitDaNewDetails(tadaDaNewDetailsDTO));
				} else {
					tadaManagementDAO.submitDaNewDetails(tadaDaNewDetailsDTO);
					tadaBean.setResult(CPSConstants.UPDATE);
				}
				
			}else{
				TadaDaNewDetailsDTO tadaDaNewDetailsDTO=new TadaDaNewDetailsDTO();                    //This is new condition of new da details
				
				tadaDaNewDetailsDTO.setGradePay(Integer.parseInt(tadaBean.getGradePay()));
				tadaDaNewDetailsDTO.setAccommodationBill(tadaBean.getAccommodationBill());
				tadaDaNewDetailsDTO.setFoodBill(tadaBean.getFoodBill());
				tadaDaNewDetailsDTO.setMilageAllw(tadaBean.getMilageAllw());
				tadaDaNewDetailsDTO.setTravelBy(tadaBean.getTravelBy());
				tadaDaNewDetailsDTO.setDistance(tadaBean.getDistance());
				tadaDaNewDetailsDTO.setAmount(tadaBean.getAmount());
				tadaDaNewDetailsDTO.setStatus(1);
				tadaDaNewDetailsDTO.setCreatedBy(tadaBean.getSfID());
				tadaDaNewDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
				tadaDaNewDetailsDTO.setLastModifiedBy(tadaBean.getSfID());
				tadaDaNewDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
		        tadaBean.setResult(tadaManagementDAO.submitDaNewDetails(tadaDaNewDetailsDTO));
		
			}
			
		
		}catch(Exception e){
			throw e;
		}
		return tadaBean;
	}
	public List<TadaDaNewDetailsDTO> getDaNewDetails(TadaManagementBean tadaBean) throws Exception{
		TadaDaNewDetailsDTO tadaDaNewDetailsDTO=new TadaDaNewDetailsDTO();
		try{
			tadaBean.setDaNewDetailsList(tadaManagementDAO.getDaNewDetails(tadaDaNewDetailsDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaBean.getDaNewDetailsList();
	}
	public String deleteDaNewDetails(TadaManagementBean tadaBean) throws Exception {
		try {
			tadaBean.setResult(tadaManagementDAO.deleteDaNewDetails(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	public TadaManagementBean submitLocalRMA(TadaManagementBean tadaBean) throws Exception {
		LocalRMADTO localRMADTO = new LocalRMADTO();
		try{
			tadaBean.setResult(tadaManagementDAO.checkLocalRMADetails(tadaBean));
			if(!CPSUtils.compareStrings(CPSConstants.DUPLICATE, tadaBean.getResult())){
				if(!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())){
					localRMADTO.setId(Integer.valueOf(tadaBean.getNodeID()));
				}
				if(CPSUtils.compareStrings(tadaBean.getFromPlace(), "Other")){
					localRMADTO.setFromPlace(tadaBean.getOtherFromPlace());
				} else {
					localRMADTO.setFromPlace(tadaBean.getFromPlace());
				}
				if(CPSUtils.compareStrings(tadaBean.getToPlace(), "Other")){
					localRMADTO.setToPlace(tadaBean.getOtherToPlace());
				} else {
					localRMADTO.setToPlace(tadaBean.getToPlace());
				}
				localRMADTO.setLocalrmadistance(tadaBean.getLocalrmadistance());
				localRMADTO.setStatus(1);
				localRMADTO.setLastModifiedBy(tadaBean.getSfID());
				localRMADTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				localRMADTO.setCreatedBy(tadaBean.getSfID());
				localRMADTO.setCreationDate(CPSUtils.getCurrentDate());
				if(localRMADTO.getId()==0){
					tadaBean.setResult(tadaManagementDAO.submitLocalRMADetails(localRMADTO));
				} else {
					tadaManagementDAO.submitLocalRMADetails(localRMADTO);
					tadaBean.setResult(CPSConstants.UPDATE);
				}
			} else {
				
			}
			
		} catch (Exception e) {
			throw e;
		}
		return tadaBean;
	}
	//Start submitDaOnTour() for DaOnTourMaster Screen
	public TadaManagementBean submitDaOnTour(TadaManagementBean tadaBean) throws Exception {
		DaOnTourDTO daOnTourDTO = new DaOnTourDTO();
		try{
			tadaBean.setResult(tadaManagementDAO.checkDaOnTourDetails(tadaBean));
			if(!CPSUtils.compareStrings(CPSConstants.DUPLICATE, tadaBean.getResult())){
				
				daOnTourDTO.setDaRangeFrom(tadaBean.getDaRangeFrom());
				daOnTourDTO.setDaRangeTo(tadaBean.getDaRangeTo());
				daOnTourDTO.setDaOnTour(tadaBean.getDaOnTour());
				daOnTourDTO.setStatus(1);
				daOnTourDTO.setLastModifiedBy(tadaBean.getSfID());
				daOnTourDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				daOnTourDTO.setCreatedBy(tadaBean.getSfID());
				daOnTourDTO.setCreationDate(CPSUtils.getCurrentDate());
				/*if(daOnTourDTO.getId()==0){*/
					tadaBean.setResult(tadaManagementDAO.submitDaOnTourDetails(daOnTourDTO));
				/*} */
			}else if((!CPSUtils.isNullOrEmpty(tadaBean.getNodeID())) && tadaBean.getNodeID()!=0) {
				   
				    daOnTourDTO.setId(Integer.valueOf(tadaBean.getNodeID()));
				    daOnTourDTO.setDaRangeFrom(tadaBean.getDaRangeFrom());
					daOnTourDTO.setDaRangeTo(tadaBean.getDaRangeTo());
					daOnTourDTO.setDaOnTour(tadaBean.getDaOnTour());
					daOnTourDTO.setStatus(1);
					daOnTourDTO.setLastModifiedBy(tadaBean.getSfID());
					daOnTourDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					
					tadaManagementDAO.submitDaOnTourDetails(daOnTourDTO);
					tadaBean.setResult(CPSConstants.UPDATE);
				}
			 /*else {
				daOnTourDTO.setDaRangeFrom(tadaBean.getDaRangeFrom());
				daOnTourDTO.setDaRangeTo(tadaBean.getDaRangeTo());
				daOnTourDTO.setDaOnTour(tadaBean.getDaOnTour());
				daOnTourDTO.setStatus(1);
				daOnTourDTO.setLastModifiedBy(tadaBean.getSfID());
				daOnTourDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				daOnTourDTO.setCreatedBy(tadaBean.getSfID());
				daOnTourDTO.setCreationDate(CPSUtils.getCurrentDate());
			}
			*/
		} catch (Exception e) {
			throw e;
		}
		return tadaBean;
	}
	// End submitDaOnTour() for DaOnTourMaster Screen
	
	public List<LocalRMADTO> getLocalRMADetails(TadaManagementBean tadaBean)throws Exception {
		LocalRMADTO localRMADTO =new LocalRMADTO();
		try{
			tadaBean.setLocalRMAList(tadaManagementDAO.getLocalRMAList(localRMADTO));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getLocalRMAList();
	}
	//start getTourDaDetails for DaOnTour Details
	public List<DaOnTourDTO> getTourDaDetails(TadaManagementBean tadaBean)throws Exception {
		DaOnTourDTO daOnTourDTO =new DaOnTourDTO();
		List<DaOnTourDTO> daOnTourList=null;
		try{
			daOnTourList=tadaManagementDAO.getTourDaDetails(daOnTourDTO);
		} catch (Exception e) {
			throw e;
		}
		return daOnTourList;
	}
	public String deleteDaOnTOur(TadaManagementBean tadaBean)throws Exception {
		try {
			tadaBean.setResult(tadaManagementDAO.deleteDaOnTour(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
	//end
	
	public String deleteLocalRMA(TadaManagementBean tadaBean)throws Exception {
		try {
			tadaBean.setResult(tadaManagementDAO.deleteLocalRMA(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getResult();
	}
//tada project
	public String submitTadaProjectDirectorDetails(TadaManagementBean tadaBean) throws Exception{
		String message="";
		try{
			tadaBean.setResult(tadaManagementDAO.checkTadaProjectDetails(tadaBean));
			if(!CPSUtils.compareStrings(tadaBean.getResult(),CPSConstants.DUPLICATE)){
			TadaProjectDirectorsDTO tadaProjectDirectorsDTO = new TadaProjectDirectorsDTO();
			if(!CPSUtils.isNullOrEmpty(tadaBean.getPk())){
				tadaProjectDirectorsDTO.setId(Integer.parseInt(tadaBean.getPk()));
			}
			tadaProjectDirectorsDTO.setProjectName(tadaBean.getProjectName());
			tadaProjectDirectorsDTO.setSfID(tadaBean.getProjectDirector());
			tadaProjectDirectorsDTO.setStatus(1);
			tadaProjectDirectorsDTO.setProgramCode(tadaBean.getProgramCode());
			
			message=tadaManagementDAO.submitTadaProjectDetails(tadaProjectDirectorsDTO);
			if(!CPSUtils.isNullOrEmpty(tadaBean.getPk())){
				message=CPSConstants.UPDATE;
			}
			}
			}catch (Exception e){
			throw e;
		}
		return message;
	}
	public List<TadaProjectDirectorsDTO> getTadaProjectDirectorList()throws Exception{
		List<TadaProjectDirectorsDTO> projectDirector =null;
		
		try{
			projectDirector=tadaManagementDAO.getTadaProjectDirectorList();
		}catch (Exception e){
			throw e;
		}
		return projectDirector;
	}
	public String deleteTadaProjectDetails(int id) throws Exception{
		String message="";
		try{
			message=tadaManagementDAO.deleteTadaProjectDetails(id);
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	public List<TadaManagementBean> getSfIDList()throws Exception{
		List<TadaManagementBean> list=null;
		try {
			list = tadaManagementDAO.getSfIDList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public List<TadaManagementBean> getProjectsList() throws Exception{
		List<TadaManagementBean> list=null;
		try {
			list = tadaManagementDAO.getProjectsList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String submitTaEntitleExemption(TadaManagementBean tadaBean)throws Exception{
		TaEntitleExemptionDTO taEntitleExemptionDTO=null;
		try {
			taEntitleExemptionDTO=new TaEntitleExemptionDTO();
			if(!CPSUtils.isNullOrEmpty(tadaBean.getNodeID()) && tadaBean.getNodeID()!=0){
				taEntitleExemptionDTO.setId(tadaBean.getNodeID());
			}
			taEntitleExemptionDTO.setSfID(tadaBean.getExemptionSfID());
			taEntitleExemptionDTO.setProjectName(tadaBean.getProjectName());
			taEntitleExemptionDTO.setProgramCode(tadaBean.getProgramCode());
			taEntitleExemptionDTO.setEntitleTypeId(tadaBean.getEntitleTypeId());
			taEntitleExemptionDTO.setRemarks(tadaBean.getDescription());
			taEntitleExemptionDTO.setStatus(1);
			taEntitleExemptionDTO.setCreatedBy(tadaBean.getSfID());
			taEntitleExemptionDTO.setCreationDate(CPSUtils.getCurrentDate());
			taEntitleExemptionDTO.setLastModifiedBy(tadaBean.getSfID());
			taEntitleExemptionDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			tadaBean.setMessage(tadaManagementDAO.submitTaEntitleExemption(taEntitleExemptionDTO));
			if(!CPSUtils.isNullOrEmpty(tadaBean.getNodeID()) && tadaBean.getNodeID()!=0){
				tadaBean.setMessage(CPSConstants.UPDATE);
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getMessage();
	}
	public List<TaEntitleExemptionDTO> getEntitleExemptionList(TadaManagementBean tadaBean)throws Exception{
		List<TaEntitleExemptionDTO> list=null;
		try {
			list = tadaManagementDAO.getEntitleExemptionList(tadaBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String deleteEntitleExemption(TadaManagementBean tadaBean)throws Exception{
		try {
			tadaBean.setMessage(tadaManagementDAO.deleteEntitleExemption(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getMessage();
	}
	public String submitProgram(TadaManagementBean tadaBean)throws Exception{
		ProgramDTO programDTO=null;
		try {
			programDTO=new ProgramDTO();
			if(!CPSUtils.compareStrings(CPSConstants.DUPLICATE, tadaManagementDAO.checkProgramList(tadaBean))){
				if(!CPSUtils.isNullOrEmpty(tadaBean.getNodeID()) && tadaBean.getNodeID()!=0){
					programDTO.setId(tadaBean.getNodeID());
				}
				programDTO.setProgramCode(tadaBean.getProgramCode());
				programDTO.setProgramName(tadaBean.getProgramName().toUpperCase());
				programDTO.setStatus(1);
				programDTO.setCreatedBy(tadaBean.getSfID());
				programDTO.setCreationDate(CPSUtils.getCurrentDate());
				programDTO.setLastModifiedBy(tadaBean.getSfID());
				programDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				tadaBean.setMessage(tadaManagementDAO.submitProgram(programDTO));
				if(!CPSUtils.isNullOrEmpty(tadaBean.getNodeID()) && tadaBean.getNodeID()!=0){
					tadaBean.setMessage(CPSConstants.UPDATE);
				}
			}else{
				tadaBean.setMessage(CPSConstants.DUPLICATE);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getMessage();
	}
	public String deleteProgram(TadaManagementBean tadaBean)throws Exception{
		try {
			tadaBean.setMessage(tadaManagementDAO.deleteProgram(tadaBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaBean.getMessage();
	}
	public List<ProgramDTO> programList()throws Exception{
		List<ProgramDTO> list=null;
		try {
			list = tadaManagementDAO.programList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public List<EmpPaymentsDTO> getGradePayList()throws Exception{
		List<EmpPaymentsDTO> list=null;
		try {
			list=tadaManagementDAO.getGradePayList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
}
