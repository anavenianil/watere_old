package com.callippus.web.tada.dao.management;

import java.util.List;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.tada.beans.management.TadaManagementBean;
import com.callippus.web.tada.beans.request.TadaRequestBean;
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

public interface ITadaManagementDAO {
	
	public String checkDaDetails(TadaManagementBean tadaBean) throws Exception;
	
	public String submitDaDetails(TadaDetailsDTO tadaDetailsDTO)throws Exception;
	
	public String deleteDaDetails(TadaManagementBean tadaBean) throws Exception;
	
	public List<EmpPaymentsDTO> getGradePayDetails(EmpPaymentsDTO empPaymentsDTO)throws Exception;
	
	public List<EmpPaymentsDTO> getUniqueGradePayDetails(EmpPaymentsDTO empPaymentsDTO)throws Exception;
	
	public String checkEntitleType(TadaManagementBean tadaBean) throws Exception;
	
	public String submitEntitleType(TaEntitleTypeDTO taEntitleTypeDTO)throws Exception;
	
	public List<TaEntitleTypeDTO> getEntitleType(TaEntitleTypeDTO taEntitleTypeDTO)throws Exception;
	
	public List<TaEntitleTypeDTO> getEntitleClass(TadaManagementBean tadaBean)throws Exception;
	
	public String deleteEntitleType(TadaManagementBean tadaBean) throws Exception;
	
	public String checkCityType(TadaManagementBean tadaBean) throws Exception;
	
	public String submitCityType(CityTypeDTO cityTypeDTO)throws Exception;
	
	public List<CityTypeDTO> getCityTypeList(CityTypeDTO cityTypeDTO)throws Exception;
	
	public List<TadaManagementBean> cityTypeList(TadaManagementBean tadaBean)throws Exception;
	
	public String deleteCityType(TadaManagementBean tadaBean) throws Exception;
	
	public List<TadaDetailsDTO> getDaDetails(TadaDetailsDTO tadaDetailsDTO)throws Exception;
	
	public List<CityTypeDTO> getUniqueCityTypeList(CityTypeDTO cityTypeDTO)throws Exception;
	
	public String checkTravelType(TadaManagementBean tadaBean) throws Exception;
	
	public String submitTravelType(TravelTypeDTO travelTypeDTO)throws Exception;
	
	public List<TravelTypeDTO> getTravelTypeList(TravelTypeDTO travelTypeDTO)throws Exception;
	
	public String deleteTravelType(TadaManagementBean tadaBean) throws Exception;
	
	public String submitTravelTypeMap(TaTravelTypeMapDTO taTravelTypeMapDTO)throws Exception;
	
	public List<TaMapDTO> getTravelTypeMapList()throws Exception;
	
	public List<TaMapDTO> getTravelTypeUnMapList()throws Exception;
	
	public String submitEntitlementClass(List<TaEntitleClassDTO> taEntitleClassDTOList)throws Exception;
	
	public String submitNonEntitlementClass(List<TaEntitleClassDTO> taEntitleClassDTOList)throws Exception;
	
	public List<TaEntitleClassDTO> getEntitlementClassList(TaEntitleClassDTO taEntitleClassDTO)throws Exception;
	
	public List<TaEntitleClassDTO> getEntitlementList(TaEntitleClassDTO taEntitleClassDTO)throws Exception;
	
	public List<TaEntitleClassDTO> getEntitlementClassList()throws Exception;
	
	public List<TaEntitleClassDTO> getEntitlementClassLists(TaEntitleClassDTO taEntitleClassDTO)throws Exception;
	
	public String checkDaNewDetails(TadaManagementBean tadaBean) throws Exception;
	
	public String submitDaNewDetails(TadaDaNewDetailsDTO tadaDaNewDetailsDTO)throws Exception;
	
	public List<TadaDaNewDetailsDTO> getDaNewDetails(TadaDaNewDetailsDTO tadaDaNewDetailsDTO)throws Exception;
	
	public String deleteDaNewDetails(TadaManagementBean tadaBean) throws Exception;
	
	public String checkLocalRMADetails(TadaManagementBean tadaBean)throws Exception;
	
	public String submitLocalRMADetails(LocalRMADTO localRMADTO)throws Exception;
	
	public List<LocalRMADTO> getLocalRMAList(LocalRMADTO localRMADTO)throws Exception;
	
	public List<DaOnTourDTO> getTourDaDetails(DaOnTourDTO daOnTourDTO)throws Exception;     //This function for DaOnTour master screen
	public String submitDaOnTourDetails(DaOnTourDTO daOnTourDTO)throws Exception;          //This function for DaOnTour master screen  
	public String checkDaOnTourDetails(TadaManagementBean tadaBean)throws Exception;      //This function for DaOnTour master screen 
	public String deleteDaOnTour(TadaManagementBean tadaBean)throws Exception ;          //This function for DaOnTour master screen 
	
	public String deleteLocalRMA(TadaManagementBean tadaBean)throws Exception;
	
	public String submitTadaProjectDetails(TadaProjectDirectorsDTO tadaProjectDirectorsDTO) throws Exception;
	
	public List<TadaProjectDirectorsDTO> getTadaProjectDirectorList()throws Exception;
	
	public String deleteTadaProjectDetails(int id)throws Exception;

	public String checkTadaProjectDetails(TadaManagementBean tadaBean) throws Exception;
	
	public List<TadaManagementBean> getSfIDList()throws Exception;
	
	public List<TadaManagementBean>  getProjectsList()throws Exception;
	
	public String submitTaEntitleExemption(TaEntitleExemptionDTO taEntitleExemptionDTO)throws Exception;
	
	public List<TaEntitleExemptionDTO> getEntitleExemptionList(TadaManagementBean tadBean)throws Exception;
	
	public String deleteEntitleExemption(TadaManagementBean tadaBean)throws Exception;
	
	public String submitProgram(ProgramDTO programDTO)throws Exception;
	
	public String deleteProgram(TadaManagementBean tadaBean)throws Exception;
	
	public List<ProgramDTO> programList()throws Exception;
	
	public String checkProgramList(TadaManagementBean tadaBean)throws Exception;
	
	public List<EmpPaymentsDTO> getGradePayList()throws Exception;
	
	public String getEntitleExemptionOption(TadaRequestBean tadaRequestBean)throws Exception;
	
	public String getRequesterRoleID(TadaRequestBean tadaRequestBean)throws Exception;

	public void removeRowId(TadaRequestBean tadaRequestBean) throws Exception;
	
	
}
