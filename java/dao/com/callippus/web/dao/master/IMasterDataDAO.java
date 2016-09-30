package com.callippus.web.dao.master;

import java.util.List;

import com.callippus.web.beans.dto.AwardCategoryDTO;
import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.LetterNumberReferenceDTO;
import com.callippus.web.beans.dto.PinNumberDTO;
import com.callippus.web.beans.dto.RequestWorkDTO;
import com.callippus.web.beans.master.MasterDataBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;

public interface IMasterDataDAO {
	public String createMasterData(MasterDataBean masterBean) throws Exception;

	public String updateMasterData(MasterDataBean masterBean) throws Exception;

	public String deleteMasterData(MasterDataBean masterBean) throws Exception;

	public MasterDataBean getDesignationTableList(MasterDataBean masterBean) throws Exception;

	public String createDesignation(MasterDataBean masterBean) throws Exception;

	public String createDesigMapping(MasterDataBean masterBean) throws Exception;

	public String updateDesignation(MasterDataBean masterBean) throws Exception;

	public String updateDesignationMapping(MasterDataBean masterBean) throws Exception;

	public String deleteDesignation(String desigID) throws Exception;

	public List getDisLocationList() throws Exception;

	public List<AwardCategoryDTO> getAwardOrganizationList() throws Exception;

	public String checkName(MasterDataBean masterBean) throws Exception;

	public List<DistrictTypeDTO> getDistrictStateList() throws Exception;

	public List<RequestWorkDTO> getRequestDetails() throws Exception;

	public String updateRequestTypes(String requestFlags) throws Exception;

	public List getPinNumberList() throws Exception;

	public String validSfid(MasterDataBean masterBean) throws Exception;

	public String chageEmployeeDetails(String changeSfid) throws Exception;

	public List<QuarterTypeBean> getSubQuarterList() throws Exception;

	public List getAwardMasterList(MasterDataBean masterDataBean)throws Exception;

	public List getAwardCategoryList()throws Exception;

	public String updateAwardMasterData(MasterDataBean masterBean)throws Exception;

	public List checkAwardMasterData(MasterDataBean masterBean)throws Exception;

	public List getCityList()throws Exception;
	
	public List<LetterNumberReferenceDTO> getLetterNoRefList(MasterDataBean masterbean)throws Exception;

	public String manageLetterData(MasterDataBean masterBean) throws Exception;

	

	public String deleteLetterData(MasterDataBean masterBean) throws Exception;
	
	public String deleteAwardMasterDetails(int id) throws Exception;
	
	public List<PinNumberDTO> getPinNoSfidList(String sfid)throws Exception;
}
