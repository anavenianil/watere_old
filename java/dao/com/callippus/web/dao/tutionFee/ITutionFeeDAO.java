package com.callippus.web.dao.tutionFee;

import java.util.List;
import java.util.Map;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.tutionFee.ClaimTypeMasterDTO;
import com.callippus.web.beans.tutionFee.TuitionFeeAcedemicYearDTO;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeLimitMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeRequestDetailsDTO;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.incometax.dto.PayFinYearDTO;
public interface ITutionFeeDAO{
	
	public TutionFeeBean getFamilyDetails(TutionFeeBean tutionFeeBean) throws Exception;
	
	public List<TutionFeeBean> getTfClaimRequestDetails(int requestId,WorkFlowMappingBean workFlowMappingBean) throws Exception;
	
	public List<List<TutionFeeClaimDetailsDTO>> getClaimListDetais(int requestId)throws Exception;
	
	public List<TuitionFeeAcedemicYearDTO> getTutionFeeAcademicYearList(TutionFeeBean tutionFeeBean) throws Exception;
	
	public String checkTutionFeeApplicantDetails(TutionFeeBean tutionFeeBean)throws Exception;
	
	public String getTutionFeeConfigDetails() throws Exception;
	
	public Map<String,List<TutionFeeBean>> getTfClaimFinanceDetails(int claimTypeId,TutionFeeBean tfbean) throws Exception;
	
	public String saveCDAFinanceDetails(TutionFeeBean tutionFeeBean)throws Exception;
	
	public String submitTuitionFeeClaimMasterDetails(TutionFeeClaimMasterDTO tutionFeeClaimMasterDTO)throws Exception;
	
	public List<TutionFeeClaimMasterDTO> getTuitionFeeClaimMasterDetails()throws Exception;
	
	public List<TutionFeeBean> getTuitionFeeSendToCDAList(int billNo,int finYearId,int claimTypeId,TutionFeeBean tutionFeeBean)throws Exception;
	
	public int getCurrentFinancialYearId()throws Exception;
	
	public int getFinancialYearId(String finYearName) throws Exception;
	
	public String getCurrentFinancialYear(int claimTypeId) throws Exception;
	
	public String saveCDADetails(TutionFeeBean tutionFeeBean)throws Exception;

	public String submitTutionFeeAcademicYearMasterDetails(TuitionFeeAcedemicYearDTO tuitionFeeAcedemicYearDTO)throws Exception;
	
	public String deleteTuitionFeeClaimDetails(int id)throws Exception;
	
	public List<TuitionFeeAcedemicYearDTO> getTutionFeeAcademicYearDetails()throws Exception;
	
	public String deleteTutionFeeAcademicYearMasterDetails(int id)throws Exception;
	
	public String submitTutionFeeLimitMaster(TutionFeeLimitMasterDTO tuitionFeeLimitMasterDTO)throws Exception;
	
	public List<TutionFeeLimitMasterDTO> getTutionFeeLimitMasterDetails()throws Exception;
	
	public String deleteTutionFeeLimitMasterDetails(int id) throws Exception;
	
	public List<KeyValueDTO> setTutionFeeRequestDetails(String acedemicType,String type,String userSfid,String academicYear,String familyChildId) throws Exception;
	
	public String getIpAddress(String requestId) throws Exception;
	
	public List<ClaimTypeMasterDTO> getClaimTypeMasterDetails()throws Exception;
	
	public String checkTutionFeeClaimDetails(TutionFeeBean tutionFeeBean) throws Exception;
	
	public String checkingTutionFeeAcademicYear(TutionFeeBean tutionFeeBean)throws Exception;
	
	public List<TutionFeeRequestDetailsDTO> checkingTuitionFeeAppliedDetails(TutionFeeBean tutionFeeBean) throws Exception;
	
	public List<PayFinYearDTO> getYearDetails(TutionFeeBean tutionFeeBean) throws Exception ;
	
	public List<TutionFeeRequestDetailsDTO> telephoneBillAplliedDetails(TutionFeeBean tutionFeeBean) throws Exception;
	
	public String tutionFeeOrgRoleId(String sfid)throws Exception;
	
	public String checkTuitionFeeLimitMaster(TutionFeeBean tutionFeeBean) throws Exception;
	
}
