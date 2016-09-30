package com.callippus.web.tada.business.request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.tada.beans.management.TadaManagementBean;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dao.management.ITadaManagementDAO;
import com.callippus.web.tada.dao.request.ITadaRequestDAO;
import com.callippus.web.tada.dto.CityTypeDTO;
import com.callippus.web.tada.dto.TaEntitleClassDTO;
import com.callippus.web.tada.dto.TaEntitleExemptionDTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TaMapDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;
import com.callippus.web.tada.dto.TadaTdJDADetailsDTO;
import com.callippus.web.tada.dto.TravelTypeDTO;

@Service
public class TadaRequestBusiness {
	@Autowired
	ITadaRequestDAO tadaRequestDAO;
	/*@Autowired
	ITadaWaterRequestDAO itadaWaterRequestDAO;*/
	@Autowired
	ITadaManagementDAO tadaManagementDAO;
	public TadaRequestBean getEmpDetails(TadaRequestBean tadaRequestBean)throws Exception{
		try{
			tadaRequestBean=tadaRequestDAO.getEmpDetails(tadaRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	public TadaRequestBean getDeptDetails(TadaRequestBean tadaRequestBean)throws Exception{
		try{
			tadaRequestBean=tadaRequestDAO.getDeptDetails(tadaRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	public TadaRequestBean getPayDetails(TadaRequestBean tadaRequestBean)throws Exception {
		try{
			tadaRequestBean=tadaRequestDAO.getPayDetails(tadaRequestBean);
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	public TadaRequestBean getConveyanceMode(TadaRequestBean tadaRequestBean)throws Exception {
		try{
			tadaRequestBean=tadaRequestDAO.getConveyanceMode(tadaRequestBean);
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	public List<TaMapDTO> getTravelTypeMapList(TadaRequestBean tadaRequestBean)throws Exception{
		List<TaMapDTO> list=null;
		try{
			list=tadaRequestDAO.getTravelTypeMapList(tadaRequestBean);
		} catch(Exception e){
			throw e;
		}
		return list;
	}
	public List<TaEntitleTypeDTO> getEntitleTypeList(TadaRequestBean tadaRequestBean)throws Exception {
		TaEntitleTypeDTO taEntitleTypeDTO=new TaEntitleTypeDTO();
		try{
			tadaRequestBean.setEntitleTypeList(tadaRequestDAO.getEntitleType(taEntitleTypeDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean.getEntitleTypeList();
	}
	public List<TaEntitleClassDTO> getEntitlementClassList(TadaRequestBean tadaRequestBean)throws Exception {
		TaEntitleClassDTO taEntitleClassDTO=new TaEntitleClassDTO();
		try {
			tadaRequestBean=getPayDetails(tadaRequestBean);
			taEntitleClassDTO.setGradePay(Integer.parseInt(tadaRequestBean.getPayDetailsList().getGradePay()));
			taEntitleClassDTO.setEntitleClassList(tadaRequestDAO.getEntitlementClassList(taEntitleClassDTO));
			
		} catch(Exception e){
			throw e;
		}
		return taEntitleClassDTO.getEntitleClassList();
	}
	public List<TravelTypeDTO> getTravelTypeList(TadaRequestBean tadaRequestBean)throws Exception {
		TravelTypeDTO travelTypeDTO=new TravelTypeDTO();
		try{
			
			tadaRequestBean.setTravelTypeList(tadaRequestDAO.getTravelTypeList(travelTypeDTO));
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean.getTravelTypeList();
	}
	public TadaRequestBean getAmendmentDetails(TadaRequestBean tadaRequestBean)throws Exception{
		try{
			tadaRequestBean=tadaRequestDAO.getAmendmentDetails(tadaRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}
	public List<TadaTdDaNewFoodDetailsDTO> getFoodDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdDaNewFoodDetailsDTO> list=null;
		try {
			list=tadaRequestDAO.getFoodDetails(tadaRequestBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public TadaDaNewDetailsDTO getDaNewDetails(TadaRequestBean tadaRequestBean)throws Exception {
		try{
			
			tadaRequestBean.setTadaDaNewDetails(tadaRequestDAO.getDaNewDetails(tadaRequestBean));
		}catch(Exception e){
			throw e;
		}
		return tadaRequestBean.getTadaDaNewDetails();
	}
	public List<CityTypeDTO> getCitiesList(TadaRequestBean tadaRequestBean)throws Exception{
		List<CityTypeDTO> list=null;
		CityTypeDTO cityTypeDTO=new CityTypeDTO();
		try {
			list=tadaManagementDAO.getCityTypeList(cityTypeDTO);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public List<TadaTdJDADetailsDTO> getJDADetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdJDADetailsDTO> jdaList=null;
		try {
			jdaList=tadaRequestDAO.getJDADetails(tadaRequestBean);
		} catch (Exception e) {
			throw e;
		}
		return jdaList;
	}
	public List<TadaProjectDirectorsDTO> getPrjDirNamesList(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaProjectDirectorsDTO> prjDirNamesList=null;
		try {
			prjDirNamesList=tadaRequestDAO.getPrjDirNamesList(tadaRequestBean);
		} catch (Exception e) {
			throw e;
		}
		return prjDirNamesList;
	}
	public List<KeyValueDTO> getTadaLeaveTypeList(TadaRequestBean tadaRequestBean)throws Exception{
		List<KeyValueDTO> tdLeaveTypeList=null;
		try {
			tdLeaveTypeList = tadaRequestDAO.getTadaLeaveTypeList(tadaRequestBean);
		} catch (Exception e) {
			throw e;
		}
		return tdLeaveTypeList;
	}
	public List<TaEntitleExemptionDTO> getEntitleExemptionList()throws Exception{
		List<TaEntitleExemptionDTO> list=null;
		TadaManagementBean tadaBean=new TadaManagementBean();
		try {
			list = tadaManagementDAO.getEntitleExemptionList(tadaBean);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String getEntitleExemptionOption(TadaRequestBean tadaRequestBean)throws Exception{
		try {
			tadaRequestBean.setEntitlementExemption(tadaManagementDAO.getEntitleExemptionOption(tadaRequestBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaRequestBean.getEntitlementExemption();
	}
	public String getRequesterRoleID(TadaRequestBean tadaRequestBean)throws Exception{
		try {
			tadaRequestBean.setRequesterRoleID(tadaManagementDAO.getRequesterRoleID(tadaRequestBean));
		} catch (Exception e) {
			throw e;
		}
		return tadaRequestBean.getRequesterRoleID();
	}
	public void removeRowId(TadaRequestBean tadaRequestBean) throws Exception{
		try {
			tadaManagementDAO.removeRowId(tadaRequestBean);
		} catch (Exception e) {
			throw e;
		}
	}
	public void getTdAppliedUsers(TadaRequestBean tadaRequestBean)throws Exception{
		try {
			tadaRequestDAO.getTdAppliedUsers(tadaRequestBean);
		} catch (Exception e) {
			throw e;
		}
	}
	public void getDirectorateList(TadaRequestBean tadaRequestBean)throws Exception{
		try {
			tadaRequestDAO.getDirectorateList(tadaRequestBean);
		} catch (Exception e) {
			throw e;
		}
	}
	public TadaRequestBean getTadaRequestTimeBoundDetails(TadaRequestBean tadaRequestBean)throws Exception {                           //This is new function of TimeBound Details
		try{
			tadaRequestBean=tadaRequestDAO.getTadaRequestTimeBoundDetails(tadaRequestBean);
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean;
	}

}
