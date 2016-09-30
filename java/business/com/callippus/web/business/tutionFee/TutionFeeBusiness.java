package com.callippus.web.business.tutionFee;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.tutionFee.ClaimTypeMasterDTO;
import com.callippus.web.beans.tutionFee.TuitionFeeAcedemicYearDTO;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeLimitMasterDTO;
import com.callippus.web.beans.tutionFee.TutionFeeRequestDetailsDTO;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.tutionFee.ITutionFeeDAO;
import com.callippus.web.incometax.dto.PayFinYearDTO;
@Service
public class TutionFeeBusiness {
	@Autowired
	private ITutionFeeDAO iTutionFeeDAO;
	public TutionFeeBean getFamilyDetails(TutionFeeBean tutionFeeBean) throws Exception{
		try{
			tutionFeeBean=iTutionFeeDAO.getFamilyDetails(tutionFeeBean);
		}catch (Exception e) {
			throw e;
		}
		return tutionFeeBean;
	}
	public List<TuitionFeeAcedemicYearDTO> getTutionFeeAcademicYearList(TutionFeeBean tutionFeeBean) throws Exception{
		List<TuitionFeeAcedemicYearDTO> keyList=null;
		try{
			keyList=iTutionFeeDAO.getTutionFeeAcademicYearList(tutionFeeBean);
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public String checkTutionFeeApplicantDetails(TutionFeeBean tutionFeeBean)throws Exception{
		String message="";
		try{
			message=iTutionFeeDAO.checkTutionFeeApplicantDetails(tutionFeeBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String getTutionFeeConfigDetails() throws Exception{
		String message="";
		try{
			message=iTutionFeeDAO.getTutionFeeConfigDetails();
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public Map<String,List<TutionFeeBean>> getTfClaimFinanceDetails(int claimTypeId,TutionFeeBean tfbean) throws Exception{
		Map<String,List<TutionFeeBean>> keyList=null;
		try{
			keyList=iTutionFeeDAO.getTfClaimFinanceDetails(claimTypeId, tfbean);
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public String saveCDAFinanceDetails(TutionFeeBean tutionFeeBean)throws Exception{
		String message="";
		try{
			message=iTutionFeeDAO.saveCDAFinanceDetails(tutionFeeBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String submitTuitionFeeClaimDetails(TutionFeeBean tutionFeeBean)throws Exception{
		String message="";
		try{
			TutionFeeClaimMasterDTO tutionFeeClaimMasterDTO=new TutionFeeClaimMasterDTO();
			tutionFeeBean.setMessage(iTutionFeeDAO.checkTutionFeeClaimDetails(tutionFeeBean));
			if(!CPSUtils.compareStrings(tutionFeeBean.getMessage(), CPSConstants.DUPLICATE)){
				if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getPk())){
					tutionFeeClaimMasterDTO.setId(Integer.parseInt(tutionFeeBean.getPk()));
				}
				tutionFeeClaimMasterDTO.setClaimName(tutionFeeBean.getClaimName());
				tutionFeeClaimMasterDTO.setClaimTypeId((Integer.parseInt(tutionFeeBean.getClaimType())));
				tutionFeeClaimMasterDTO.setOrderNo(tutionFeeBean.getClaimOrderNo());
				tutionFeeClaimMasterDTO.setStatus(1);
				tutionFeeClaimMasterDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				tutionFeeClaimMasterDTO.setCreatedBy(tutionFeeBean.getSfid());
				tutionFeeClaimMasterDTO.setLastModifiedBy(tutionFeeBean.getSfid());
				tutionFeeClaimMasterDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				
				message=iTutionFeeDAO.submitTuitionFeeClaimMasterDetails(tutionFeeClaimMasterDTO);
				if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getPk())){
					message=CPSConstants.UPDATE;
				}
			}else{
				message=CPSConstants.DUPLICATE;
			}
			
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<TutionFeeClaimMasterDTO> getTuitionFeeClaimMasterDetails()throws Exception
	{
		
		List<TutionFeeClaimMasterDTO> claimList=null; 
		try{
			claimList=iTutionFeeDAO.getTuitionFeeClaimMasterDetails();
		}catch (Exception e) {
			throw e;
		}
		return claimList;
	}
	public List<ClaimTypeMasterDTO> getClaimTypeMasterDetails()throws Exception
	{
		
		List<ClaimTypeMasterDTO> claimList=null; 
		try{
			claimList=iTutionFeeDAO.getClaimTypeMasterDetails();
		}catch (Exception e) {
			throw e;
		}
		return claimList;
	}
	public String getTutionFeeOrgRoleId(String sfid)throws Exception{
		String message = null;
		try{
			message=iTutionFeeDAO.tutionFeeOrgRoleId(sfid);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<TutionFeeBean> getTuitionFeeSendToCDAList(String billNo,int claimTypeId,TutionFeeBean tutionFeeBean) throws Exception{
		List<TutionFeeBean> keyList=null;
		int finYearId=0;
		int billNumber=0;
		try{
			if(claimTypeId==1){
				billNumber=Integer.parseInt(billNo.split("/")[6]);
				finYearId=iTutionFeeDAO.getFinancialYearId(billNo.split("/")[4]);
				keyList=iTutionFeeDAO.getTuitionFeeSendToCDAList(billNumber,finYearId,claimTypeId,tutionFeeBean);
			}else if(claimTypeId==2){
				billNumber=Integer.parseInt(billNo.split("/")[4]);
				keyList=iTutionFeeDAO.getTuitionFeeSendToCDAList(billNumber,finYearId,claimTypeId,tutionFeeBean);
			}
			
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public String setBillNumberInSendToCDA(int claimTypeId)throws Exception{
		String billNo=null;
		try{
			if(claimTypeId==1){
				billNo="ASL/22/FIN/3041/"+iTutionFeeDAO.getCurrentFinancialYear(claimTypeId)+"/CEA/";
			}else if(claimTypeId==2){
				billNo="ASL/FIN/022/RESI PHONE/";
			}
			
		}catch (Exception e) {
			throw e;
		}
		return billNo;
	}
	//tutionfeelimitdetails
    public TutionFeeBean setTutionFeeRequestDetails(TutionFeeBean tutionFeeBean)throws Exception{
     try{
    	 if(tutionFeeBean.getAcademicType().equals("State") && tutionFeeBean.getType().equals("Quarterly")){
    		 tutionFeeBean.setStateQuarterList(iTutionFeeDAO.setTutionFeeRequestDetails("State", "Quartarly",tutionFeeBean.getSfid(),tutionFeeBean.getAcademicYear(),tutionFeeBean.getFamilyChildId()));
    	 }
    	 if(tutionFeeBean.getAcademicType().equals("State") && tutionFeeBean.getType().equals("Halfyearly")){
    		 tutionFeeBean.setStateHalfList(iTutionFeeDAO.setTutionFeeRequestDetails("State", "Halfyearly",tutionFeeBean.getSfid(),tutionFeeBean.getAcademicYear(),tutionFeeBean.getFamilyChildId()));
    	 }
    	 if(tutionFeeBean.getAcademicType().equals("State") && tutionFeeBean.getType().equals("Annually")){
    		 tutionFeeBean.setStateAnnualList(iTutionFeeDAO.setTutionFeeRequestDetails("State", "Annually",tutionFeeBean.getSfid(),tutionFeeBean.getAcademicYear(),tutionFeeBean.getFamilyChildId()));
    	 }
    	 if(tutionFeeBean.getAcademicType().equals("Central") && tutionFeeBean.getType().equals("Quarterly")){
    		 tutionFeeBean.setCentralQuarterList(iTutionFeeDAO.setTutionFeeRequestDetails("Central", "Quartarly",tutionFeeBean.getSfid(),tutionFeeBean.getAcademicYear(),tutionFeeBean.getFamilyChildId()));
    	 }
    	 if(tutionFeeBean.getAcademicType().equals("Central")&& tutionFeeBean.getType().equals("Halfyearly")){
    		 tutionFeeBean.setCentralHalfList(iTutionFeeDAO.setTutionFeeRequestDetails("Central", "Halfyearly",tutionFeeBean.getSfid(),tutionFeeBean.getAcademicYear(),tutionFeeBean.getFamilyChildId()));
    	 }
    	 if(tutionFeeBean.getAcademicType().equals("Central") && tutionFeeBean.getType().equals("Annually")){
    		 tutionFeeBean.setCentralAnnualList(iTutionFeeDAO.setTutionFeeRequestDetails("Central", "Annually",tutionFeeBean.getSfid(),tutionFeeBean.getAcademicYear(),tutionFeeBean.getFamilyChildId())); 
    	 }
		 
	  }catch(Exception e){
		  throw e;
	  }
	  return tutionFeeBean;
    }
   public String saveCDADetails(TutionFeeBean tutionFeeBean)throws Exception{
		String message="";
		try{
			message=iTutionFeeDAO.saveCDADetails(tutionFeeBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String deleteTuitionFeeClaimDetails(int id)throws Exception{
		String message="";
		try{
			message=iTutionFeeDAO.deleteTuitionFeeClaimDetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String deleteTutionFeeLimitMasterDetails(int id)throws Exception{
		String message="";
		try{
			message=iTutionFeeDAO.deleteTutionFeeLimitMasterDetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
   public String submitTutionFeeAcademicYearMaster(TutionFeeBean tutionFeeBean)throws Exception{

		String message="";
		try{
			TuitionFeeAcedemicYearDTO  tuitionFeeAcedemicYearDTO=new TuitionFeeAcedemicYearDTO();
			tutionFeeBean.setMessage(iTutionFeeDAO.checkingTutionFeeAcademicYear(tutionFeeBean));
			if(!CPSUtils.compareStrings(tutionFeeBean.getMessage(), CPSConstants.DUPLICATE)){
			    if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getPk())){
				tuitionFeeAcedemicYearDTO.setId(Integer.parseInt(tutionFeeBean.getPk()));
			    }
			tuitionFeeAcedemicYearDTO.setClassName(tutionFeeBean.getClassName());
			tuitionFeeAcedemicYearDTO.setOrderNo(tutionFeeBean.getClaimOrderNo());
			tuitionFeeAcedemicYearDTO.setStatus(1);
			tuitionFeeAcedemicYearDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			tuitionFeeAcedemicYearDTO.setCreatedBy(tutionFeeBean.getSfid());
			tuitionFeeAcedemicYearDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			tuitionFeeAcedemicYearDTO.setLastModifiedBy(tutionFeeBean.getSfid());
			message=iTutionFeeDAO.submitTutionFeeAcademicYearMasterDetails(tuitionFeeAcedemicYearDTO);
			if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getPk())){
				message=CPSConstants.UPDATE;
			}
		}else{
			message=CPSConstants.DUPLICATE;
		}
		}catch(Exception e){
		throw e;
	    }
		return message;
	}
   public String submitTutionFeeLimitMaster(TutionFeeBean tutionFeeBean)throws Exception{

		String message="";
		try{
			TutionFeeLimitMasterDTO  tutionFeeLimitMasterDTO = new TutionFeeLimitMasterDTO();
			tutionFeeBean.setMessage(iTutionFeeDAO.checkTuitionFeeLimitMaster(tutionFeeBean));
			if(!CPSUtils.compareStrings(tutionFeeBean.getMessage(), CPSConstants.DUPLICATE)){
				if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getPk())){
				tutionFeeLimitMasterDTO.setId(Integer.parseInt(tutionFeeBean.getPk()));
				}
		   
			tutionFeeLimitMasterDTO.setAcademicType(tutionFeeBean.getAcademicType());
			tutionFeeLimitMasterDTO.setStatus(1);
			tutionFeeLimitMasterDTO.setLimit(tutionFeeBean.getLimit());
			tutionFeeLimitMasterDTO.setFromDate1(tutionFeeBean.getFromDate1());
			tutionFeeLimitMasterDTO.setToDate1(tutionFeeBean.getToDate1());
			tutionFeeLimitMasterDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			tutionFeeLimitMasterDTO.setCreatedBy(tutionFeeBean.getSfid());
			tutionFeeLimitMasterDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			tutionFeeLimitMasterDTO.setLastModifiedBy(tutionFeeBean.getSfid());
			if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getType())){
				tutionFeeLimitMasterDTO.setType(tutionFeeBean.getType());
			}
			if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getTypeQuartarly())){
				tutionFeeLimitMasterDTO.setType(tutionFeeBean.getTypeQuartarly());
			}
			if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getTypeHalfyearly())){
				tutionFeeLimitMasterDTO.setType(tutionFeeBean.getTypeHalfyearly());
			}
		    message=iTutionFeeDAO.submitTutionFeeLimitMaster(tutionFeeLimitMasterDTO);
			if(!CPSUtils.isNullOrEmpty(tutionFeeBean.getPk())){
				message=CPSConstants.UPDATE;
			}
			}else{
				message=CPSConstants.DUPLICATE;
			}
		}catch(Exception e){
		throw e;
	    }
		return message;
	}
	public List<TuitionFeeAcedemicYearDTO> getTutionFeeAcademicYearDetails() throws Exception{
		List<TuitionFeeAcedemicYearDTO>  classNameList=null;
		try{
			classNameList=iTutionFeeDAO.getTutionFeeAcademicYearDetails();
		}catch(Exception e){
			throw e;
		}
		return classNameList;
	}
	public String deleteTutionFeeAcademicYearMasterDetails(int id) throws Exception{
		String message="";
		try{
			message=iTutionFeeDAO.deleteTutionFeeAcademicYearMasterDetails(id);
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	
	public List<TutionFeeLimitMasterDTO> getTutionFeeLimitMasterDetails() throws Exception{
		List<TutionFeeLimitMasterDTO> academicTypeList =null;
		try{
			academicTypeList=iTutionFeeDAO.getTutionFeeLimitMasterDetails();
		}catch(Exception e){
			throw e;
		}
		return academicTypeList;
	}
// checking the tutionfee applicants details before applying
    public List<TutionFeeRequestDetailsDTO> checkingTuitionFeeAppliedDetails(TutionFeeBean tutionFeeBean) throws Exception{
		List<TutionFeeRequestDetailsDTO> tuitionFeeApplicantDetails = null;
		try{
			tuitionFeeApplicantDetails=iTutionFeeDAO.checkingTuitionFeeAppliedDetails(tutionFeeBean);
		}catch (Exception e) {
			throw e;
		}
		return tuitionFeeApplicantDetails;
	}
    public List<TutionFeeRequestDetailsDTO> telephoneBillAppliedDetails(TutionFeeBean tutionFeeBean) throws Exception{
    	List<TutionFeeRequestDetailsDTO> telephoneBillApplieddetails=null;
    	try{
    		telephoneBillApplieddetails=iTutionFeeDAO.telephoneBillAplliedDetails(tutionFeeBean);
    	}catch (Exception e) {
			throw e;
		}
    	return telephoneBillApplieddetails;
    }
    public List<PayFinYearDTO> getYearDetails(TutionFeeBean tutionFeeBean) throws Exception {
		List<PayFinYearDTO> yearTypeDetails = null;
		try {
			yearTypeDetails = iTutionFeeDAO.getYearDetails(tutionFeeBean);

		} catch (Exception e) {
			throw e;
		}
		return yearTypeDetails;
    }
}
