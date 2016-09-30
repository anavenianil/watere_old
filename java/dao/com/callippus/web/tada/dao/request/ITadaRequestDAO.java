package com.callippus.web.tada.dao.request;

import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dto.TaEntitleClassDTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TaMapDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaTdAccDetDayRepDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;
import com.callippus.web.tada.dto.TadaTdJDADetailsDTO;
import com.callippus.web.tada.dto.TravelTypeDTO;

public interface ITadaRequestDAO {
	public TadaRequestBean getEmpDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public TadaRequestBean getPayDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public TadaRequestBean getDaOnTourDetails(TadaRequestBean tadaRequestBean)throws Exception;        //This metod for DaOnTourDetails method
	public TadaRequestBean getTadaRequestTimeBoundDetails(TadaRequestBean tadaRequestBean) throws Exception;   //This method for Tada REquest Time bound detaols
	
	public TadaRequestBean getConveyanceMode(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<TaMapDTO> getTravelTypeMapList(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<TaEntitleTypeDTO> getEntitleType(TaEntitleTypeDTO taEntitleTypeDTO)throws Exception;
	
	public List<TaEntitleClassDTO> getEntitlementClassList(TaEntitleClassDTO taEntitleClassDTO)throws Exception;
	
	public List<TravelTypeDTO> getTravelTypeList(TravelTypeDTO travelTypeDTO)throws Exception;
	
	public TadaRequestBean getAmendmentDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public TadaRequestBean getDeptDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<TadaDaNewDetailsDTO> getDaNewDetailsList()throws Exception;
	
	public List<TadaTdDaNewFoodDetailsDTO> getFoodDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<TadaTdDaNewFoodDetailsDTO> getDayFoodDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<TadaTdAccDetDayRepDTO> getDayAccDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public TadaDaNewDetailsDTO getDaNewDetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<TadaTdJDADetailsDTO> getJDADetails(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<TadaProjectDirectorsDTO> getPrjDirNamesList(TadaRequestBean tadaRequestBean)throws Exception;
	
	public List<KeyValueDTO> getTadaLeaveTypeList(TadaRequestBean tadRequestBean)throws Exception;
	
	public void getDirectorateList(TadaRequestBean tadaRequestBean)throws Exception;
	
	public void getTdAppliedUsers(TadaRequestBean tadaRequestBean)throws Exception;

}
