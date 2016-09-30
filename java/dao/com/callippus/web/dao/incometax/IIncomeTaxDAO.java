package com.callippus.web.dao.incometax;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;



import com.callippus.web.beans.dto.OverTimeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.dto.PayChallanDetailsDTO;
import com.callippus.web.beans.incometax.IncomeTaxMasterBean;
import com.callippus.web.incometax.dto.IncomeTaxArrearsDTO;
import com.callippus.web.incometax.dto.IncomeTaxConfigDTO;
import com.callippus.web.incometax.dto.IncomeTaxDAStatusDTO;
import com.callippus.web.incometax.dto.IncomeTaxDurationDTO;
import com.callippus.web.incometax.dto.IncomeTaxPayBillDTO;
import com.callippus.web.incometax.dto.IncomeTaxRentDTO;
import com.callippus.web.incometax.dto.IncomeTaxRunStatusDTO;
import com.callippus.web.incometax.dto.IncomeTaxSavingsDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionMappingDTO;
import com.callippus.web.incometax.dto.IncomeTaxStageDTO;
import com.callippus.web.incometax.dto.ItarrearsDto;
import com.callippus.web.incometax.dto.PayFinYearDTO;
import com.callippus.web.incometax.dto.PrUpdateAllwDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.incometax.OverTimeVO;

public interface IIncomeTaxDAO {
     public String insertFinYearMasterDetails(PayFinYearDTO fyDTO) throws Exception ;
	
	public List<PayFinYearDTO> getFinYearList() throws Exception;
	
	public String deleteFinYearDetails(int id) throws Exception;
	
	public String submitIncomeTaxDetails(IncomeTaxStageDTO taxStage) throws Exception;
	
	public List<IncomeTaxStageDTO> getincomeTaxStagelist(IncomeTaxMasterBean incomeTaxMasterBean)  throws Exception;
	
	public String deleteIncomeTaxDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
	
	public String submitArrearsDetails(IncomeTaxMasterBean incomeTaxMasterBean)  throws Exception;
	
	public String submitSavingsDetails(IncomeTaxSavingsDTO incomeTaxSavingsDTO) throws Exception;
	
	public List<IncomeTaxSavingsDTO> getSavingsList()  throws Exception;
	
	public List<IncomeTaxSavingsDTO> getSavingsListWithSections() throws Exception;
	public String deleteSavingsDetails(int id) throws Exception;
	
	public List<IncomeTaxSavingsDTO> getConfigList() throws Exception;
	
	public String submitConfigDetails(List<IncomeTaxConfigDTO> configList) throws Exception ;
	
	public String validateProjectionDetails(int savingsTypeId) throws Exception ;
	
	public List<IncomeTaxConfigDTO> getConfigListDetails(IncomeTaxMasterBean incomeTaxMasterBean)  throws Exception ;
	
	public String getConfigRowDetails(int savingsTypeId, String sfid, String finYear) throws Exception ;
		
	public List<IncomeTaxArrearsDTO> getArrearsListDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
	
	public List<IncomeTaxDAStatusDTO> getArrearsStatusList() throws Exception ;
	
    public String submitSectionDetails(IncomeTaxSectionDTO incomeTaxSectionDTO) throws Exception;
	
	public List<IncomeTaxSectionDTO> getITSectionList() throws Exception ;
	
	public String deleteSectionDetails(int id) throws Exception;
	
	public int getTrasportAllowance() throws Exception;
	
	public String submitRentDetails(List<IncomeTaxRentDTO> rentList) throws Exception;
	
	public List<IncomeTaxRentDTO> getITRentList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
	
	public String deleteRentDetails(int id) throws Exception;
	
	
	public String submitProjectedDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
	 
    
    public String submitPlannedDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    
    
    public String submitActualDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    
    
    public List<PayScaleDesignationDTO> getDesignationList() throws Exception;
    
    public String submitPUADetails(PrUpdateAllwDTO prUpdateAllwDTO) throws Exception;
    
    public List<PrUpdateAllwDTO> getPUAList() throws Exception;
    
    public String deletePUADetails(int id) throws Exception;
    
    public String checkITRunStatus(int finyearid,String runType)throws Exception;
    
    public String submitDBMigrationDetails(String month)throws Exception;
    
    @SuppressWarnings("unchecked")
	public Map compareDBMigrationDetails(String month,String flag,IncomeTaxMasterBean incomeTaxMasterBean)throws Exception;
    
    public PayBillDTO getPayMonthDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception;
    
    public List<DesignationDTO> getEmpDesignationList()throws Exception;
    	
    public String incomeTaxStatusDetails() throws Exception;
    
    public List<IncomeTaxSectionMappingDTO> getSectionMappingDetails() throws Exception;
    
    public String submitIncomeTaxSectionMappingDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception;

    public String deleteIncomeTaxSectionMappingDetails(int id)throws Exception;
    
    public List<IncomeTaxRunStatusDTO> getIncomeTaxRunStatusList() throws Exception;


    
    
	public String saveChallanDetails(PayChallanDetailsDTO payChallanDTO,String sfid) throws Exception;  //This function for saveChallanDetails(P)
    public PayChallanDetailsDTO getSaveChallanDetails(String sfid,String finYearId)throws Exception;
    
    public IncomeTaxMasterBean getEditEmpList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    
    public String submitTransferEmpDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    
    //for display totals in all pages footer
    public IncomeTaxMasterBean getITTotals(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;

    
    // OT
    public List<Integer> getYearList() throws Exception;
    
    public List<KeyValueDTO> getCategoryList() throws Exception;
    public List<KeyValueDTO> getDesignations() throws Exception;
    public OverTimeVO getOTDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    public List<OverTimeVO> getOTDetailsList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    
    //for change it run screen
    public String submitITDurationDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    public List<IncomeTaxDurationDTO> getITDurationDetails() throws Exception;
    public String deleteITDurationDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    public String saveOTDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
    public List<IncomeTaxSavingsDTO> getItSavedSections(Integer year) throws Exception;
	public String getkey(String sectionId) throws Exception;
	public List<IncomeTaxSectionDTO> getdistinctSections() throws Exception; 
	public List<IncomeTaxSavingsDTO> getSectionName(String sectionId) throws Exception;


	public Integer chekchild(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception;
	public String checkSeniorCitizen(String Sfid)throws Exception;

	public IncomeTaxMasterBean getEmpDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
/*
	public List<IncomeTaxSectionMappingDTO> getRelatedSection(IncomeTaxMasterBean incometaxmasterbean,
			String serniorcitizen,int selectedFyear) throws Exception;*/

	public Integer getSectionId(int savingTypeId) throws Exception;
	
	public String saveItArrears(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception;
	

	public List<ItarrearsDto> getItarrears(IncomeTaxMasterBean itmb) throws Exception;

/*	public List<IncomeTaxSectionMappingDTO> getRelatedSection(
			IncomeTaxMasterBean incometaxmasterbean, String selectedFYear,
			String serniorcitizen);*/

	public List<IncomeTaxSectionMappingDTO> getRelatedSection(
			IncomeTaxMasterBean incometaxmasterbean, String serniorcitizen,
			String seletedFyear) throws Exception;
	
}
