package com.callippus.web.dao.telephone;

import java.util.List;

import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneBillClaimDetailsDTO;
import com.callippus.web.beans.telephone.TelephoneCashAssignmentDTO;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;

@Service
public interface ITelePhoneBillDAO {
	
	public List<DesignationDTO> getTelephoneDesignationDeSelectedList() throws Exception;
	
	public List<DesignationDTO> getTelephoneDesignationSelectedList(TelePhoneBillBean telePhoneBillBean)throws Exception;
	
	//public List<KeyValueDTO> getSelectedEmployeeList(String designationId) throws Exception;
	
	//public List<KeyValueDTO> getNotSelectedEmployeeList(String designationId) throws Exception;
	
	public String submitTelephoneElibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
	
	//public TelePhoneBillBean getTeleBillConfDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
	
	public TelePhoneBillBean getEmpDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
	
	public List<TutionFeeClaimMasterDTO> getTelephoneBillClaimMasterDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
	
	public int  checkSelectedEmployeeExisted(String sfid) throws Exception;
	
	public List<TelePhoneBillBean> getTelephoneBillClaimRequestDetails(int requestId,WorkFlowMappingBean workFlowMappingBean)throws Exception;
	
	public List<TelephoneBillClaimDetailsDTO> getTelephoneBillClaimListDetails(int requestId) throws Exception;
	
	public String submitTelephoneDesignationEligibileDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
	
    public List<TelephoneDesigEligibilityDetailsDTO>  getTelephoneDesigEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
    
    public List<KeyValueDTO> getTelephoneSelectedEmployeeList()throws Exception;
    
    public List<KeyValueDTO> getSelectedEmployeeList(String designationId,TelePhoneBillBean telePhoneBillBean)throws Exception;
    
    public List<KeyValueDTO> getNotSelectedEmployeeList(String designationId,TelePhoneBillBean telePhoneBillBean) throws Exception;
    
    public String submitTelephoneEmployeeSelectedList(TelePhoneBillBean telePhoneBillBean)throws Exception;
    
    public String deleteDesignationEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
    
    public String checkTelephoneBillApplicantDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
    
    public TelePhoneBillBean checkTelephoneBillInternetDetails(TelePhoneBillBean telePhoneBillBean)throws Exception;
    
    public String submitTelephoneCashAssignment(TelephoneCashAssignmentDTO telephoneCashAssignmentDTO)throws Exception;
    
    public List<TelephoneCashAssignmentDTO> getTelephoneCashAssignmentDetails()throws Exception;
    
    public String getTeleCashDetails(String sfid) throws Exception;
    
    public String deleteTeleCashAssignmentDetails(int id)throws Exception;
    
    public TelePhoneBillBean checkMissingPeriodDetails(TelePhoneBillBean telephoneBillBean)throws Exception;
    
    public String checkingTelephoneBillCashAssignmentDetails(TelePhoneBillBean telePhoneBillBean) throws Exception;
    
    public String checkTelephoneBillEnableLoginLink(TelePhoneBillBean telePhoneBillBean) throws Exception;
    
   
}
