package com.callippus.web.business.telephone;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneBillClaimDetailsDTO;
import com.callippus.web.beans.telephone.TelephoneCashAssignmentDTO;
import com.callippus.web.beans.telephone.TelephoneDesigEligibilityDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeClaimMasterDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.telephone.ITelePhoneBillDAO;
import com.callippus.web.dao.telephone.SQLTelePhoneBillDAO;
@Service
public class TelePhoneBillBusiness {
	@Autowired
	private ITelePhoneBillDAO iTelePhoneBillDAO;
	public List<DesignationDTO> getTelephoneDesignationDeSelectedList() throws Exception{
		List<DesignationDTO> telephoneDesignationDeSelectedList=null;
		try{
			telephoneDesignationDeSelectedList=iTelePhoneBillDAO.getTelephoneDesignationDeSelectedList();
		}catch (Exception e) {
			throw e;
		}
		return telephoneDesignationDeSelectedList;
	}
	public List<DesignationDTO>  getTelephoneDesignationSelectedList(TelePhoneBillBean telePhoneBillBean)throws Exception{
		List<DesignationDTO>  telephoneDesignationSelectedList =null;
		try{
			telephoneDesignationSelectedList=iTelePhoneBillDAO.getTelephoneDesignationSelectedList(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return telephoneDesignationSelectedList;
	}
	public List<KeyValueDTO>  getTelephoneSelectedEmployeeList()throws Exception {
		List<KeyValueDTO>  telephoneSelectedEmployeeList =null;
		try{
			telephoneSelectedEmployeeList=iTelePhoneBillDAO.getTelephoneSelectedEmployeeList();
		}catch (Exception e) {
			throw e;
		}
		return telephoneSelectedEmployeeList;
	}
	/*public TelePhoneBillBean getEmployeeWithDesignationWise(TelePhoneBillBean telePhoneBillBean) throws Exception{
		try{
			telePhoneBillBean.setSelectedEmployeeList(iTelePhoneBillDAO.getSelectedEmployeeList(telePhoneBillBean.getDesignationId()));
			telePhoneBillBean.setNotSelectedEmployeeList(iTelePhoneBillDAO.getNotSelectedEmployeeList(telePhoneBillBean.getDesignationId()));
			//telePhoneBillBean=iTelePhoneBillDAO.getTeleBillConfDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return telePhoneBillBean;
	}*/
	//code for getting the list of the employee based on the selected designation
	public TelePhoneBillBean getEmployeeSfidDesignationWiseList(TelePhoneBillBean telePhoneBillBean)throws Exception{
		try{
			telePhoneBillBean.setSelectedEmployeeList(iTelePhoneBillDAO.getSelectedEmployeeList(telePhoneBillBean.getDesignationId(),telePhoneBillBean));
		    telePhoneBillBean.setNotSelectedEmployeeList(iTelePhoneBillDAO.getNotSelectedEmployeeList(telePhoneBillBean.getDesignationId(),telePhoneBillBean));
		}catch (Exception e) {
			throw e;
		}
		return telePhoneBillBean;
	}
	public String submitTelephoneElibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message=null;
		try{
			message=iTelePhoneBillDAO.submitTelephoneElibilityDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public TelePhoneBillBean getEmpDetails( TelePhoneBillBean telePhoneBillBean)throws Exception{
		
		try{
			telePhoneBillBean=iTelePhoneBillDAO.getEmpDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return telePhoneBillBean;
	}
	//list of claim details of tutionfee
	public List<TutionFeeClaimMasterDTO> getTelephoneBillClaimMasterDetails(TelePhoneBillBean telePhoneBillBean)throws Exception
	{
		
		List<TutionFeeClaimMasterDTO> telephoneClaimList=null; 
		try{
			telephoneClaimList=iTelePhoneBillDAO.getTelephoneBillClaimMasterDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return telephoneClaimList;
	}
	
	public int  checkSelectedEmployeeExisted(String sfid) throws Exception{
		int check=-1;
		try{
			ITelePhoneBillDAO iTelePhoneBillDAO=new SQLTelePhoneBillDAO();
			check=iTelePhoneBillDAO.checkSelectedEmployeeExisted(sfid);
		}catch (Exception e) {
			throw e;
		}
		return check;
	}
	//code for submit telephonebill designation eligibility details
	public String submitTelephoneDesigEligibilityDetails(TelePhoneBillBean telePhoneBillBean) throws Exception{
		String message=null;
		try{
			message=iTelePhoneBillDAO.submitTelephoneDesignationEligibileDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String submitTelephoneEmpEligibiltyDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message = null;
		try{
			message=iTelePhoneBillDAO.submitTelephoneEmployeeSelectedList(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	 // commenting old working code before 25102013
	/*public List<TelephoneDesigEligibilityDetailsDTO> getTelephoneDesigEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		 List<TelephoneDesigEligibilityDetailsDTO>  telephoneDesigList = null;
		try{
			telephoneDesigList=iTelePhoneBillDAO.getTelephoneDesigEligibilityDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return telephoneDesigList;
	}*/
	  //present code on 28102013
	public List<TelephoneDesigEligibilityDetailsDTO> getTelephoneDesigEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
	 List<TelephoneDesigEligibilityDetailsDTO>  telephoneDesigList = null;
	try{
		telephoneDesigList=iTelePhoneBillDAO.getTelephoneDesigEligibilityDetails(telePhoneBillBean);
	}catch (Exception e) {
		throw e;
	}
	return telephoneDesigList;
    }
	// delete designationeligibility details
	public String deleteDesignationEligibilityDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message="";
		try{
			message=iTelePhoneBillDAO.deleteDesignationEligibilityDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String checkTelephoneBillApplicantDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message="";
		try{
			message=iTelePhoneBillDAO.checkTelephoneBillApplicantDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public TelePhoneBillBean checkTelephoneBillInternetDetails(TelePhoneBillBean telePhoneBillBean)throws Exception{
		try{
			telePhoneBillBean = iTelePhoneBillDAO.checkTelephoneBillInternetDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return telePhoneBillBean;
	}
	//cash assignment
	public String submitTelephoneCashAssignment(TelePhoneBillBean telePhoneBillBean)throws Exception{
		String message ="";
		try{
			TelephoneCashAssignmentDTO telephoneCashAssignmentDTO =new TelephoneCashAssignmentDTO();
			telePhoneBillBean.setMessage(iTelePhoneBillDAO.checkingTelephoneBillCashAssignmentDetails(telePhoneBillBean));
			if(!CPSUtils.compareStrings(telePhoneBillBean.getMessage(), CPSConstants.DUPLICATE)){
				if(!CPSUtils.isNullOrEmpty(telePhoneBillBean.getPk())){
					telephoneCashAssignmentDTO.setId(Integer.parseInt(telePhoneBillBean.getPk()));
				}
				telephoneCashAssignmentDTO.setSfid(telePhoneBillBean.getTeleSfid());
				telephoneCashAssignmentDTO.setFromDate(telePhoneBillBean.getFromDate());
				telephoneCashAssignmentDTO.setToDate(telePhoneBillBean.getToDate());
				telephoneCashAssignmentDTO.setCreatedBy(telePhoneBillBean.getSfid());
				telephoneCashAssignmentDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
				telephoneCashAssignmentDTO.setLastModifiedBy(telePhoneBillBean.getSfid());
				telephoneCashAssignmentDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
				telephoneCashAssignmentDTO.setStatus(1);
				message=iTelePhoneBillDAO.submitTelephoneCashAssignment(telephoneCashAssignmentDTO);
				if(!CPSUtils.isNullOrEmpty(telePhoneBillBean.getPk())){
					message=CPSConstants.UPDATE;
				}
			}
			else{
			message=CPSConstants.DUPLICATE;
		}
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<TelephoneCashAssignmentDTO> getTelephoneCashAssignment() throws Exception{
		List<TelephoneCashAssignmentDTO> teleCashDetails=null;
		try{
			teleCashDetails=iTelePhoneBillDAO.getTelephoneCashAssignmentDetails();
		}catch (Exception e) {
			throw e;
		}
		return teleCashDetails;
	}
	public String getCashAssignmentDetails(String sfid) throws Exception{
		String cashSfid="";
		try{
			cashSfid=iTelePhoneBillDAO.getTeleCashDetails(sfid);
		}catch (Exception e) {
			throw e;
		}
		return cashSfid;
	}
	public String deleteCashAssignmentDetails(int id)throws Exception{
		String message=null;
		try{
			message=iTelePhoneBillDAO.deleteTeleCashAssignmentDetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public TelePhoneBillBean checkMissingPeriodDetails(TelePhoneBillBean telePhoneBillBean) throws Exception{
		try{
			telePhoneBillBean=iTelePhoneBillDAO.checkMissingPeriodDetails(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return telePhoneBillBean;
	}
	public String checkTelephoneBillEnableLoginLink(TelePhoneBillBean telePhoneBillBean) throws Exception{
		String message = null;
		try{
			message=iTelePhoneBillDAO.checkTelephoneBillEnableLoginLink(telePhoneBillBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
}
