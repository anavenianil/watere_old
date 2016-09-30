package com.callippus.web.business.passport;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.FamilyRelationDTO;
import com.callippus.web.beans.passport.PassportApplicationDTO;
import com.callippus.web.beans.passport.PassportApplicationDetailsDTO;
import com.callippus.web.beans.passport.PassportBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.passport.IPassportDAO;

@Service
public class PassportBusiness {
	@Autowired
	private IPassportDAO passportDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public PassportBean getPassportHomeDetails(PassportBean passportBean, HttpSession session) throws Exception {
		try {
			passportBean.setPassportList(passportDAO.getEmployeePassportDetails(passportBean.getSfid()));

			int size = passportBean.getPassportList().size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, passportBean.getPassportList().get(size - 1));
				passportBean.getPassportList().remove(size - 1);
			}
		} catch (Exception e) {
			throw e;
		}
		return passportBean;
	}

	public PassportBean deletePassport(PassportBean passportBean, HttpSession session) throws Exception {
		String message = "";
		try {
			message = passportDAO.deletePassport(passportBean.getId());
			passportBean.setMessage(message);
			passportBean.setPassportList(passportDAO.getEmployeePassportDetails(passportBean.getSfid()));
			int size = passportBean.getPassportList().size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, passportBean.getPassportList().get(size - 1));
				passportBean.getPassportList().remove(size - 1);
			}
		} catch (Exception e) {
			throw e;
		}
		return passportBean;
	}

	public PassportBean managePassportDetails(PassportBean passportBean, HttpSession session) throws Exception {
		String message = null;
		List<PassportBean> passportList = null;
		List passportTypeList = null;
		try {
			if (CPSUtils.isNullOrEmpty(passportBean.getId())) {
				passportTypeList = passportDAO.passportTypeChecking(passportBean);
				if (passportTypeList != null && passportTypeList.size() > 0) {
					message = CPSConstants.PASSPORTTYPEEXISTED;
					passportBean.setMessage(message);
				} else {

					int id = commonDataDAO.getTableID(CPSConstants.PASSPORTDETAILSTABLE, CPSConstants.UPDATE);
					passportBean.setId(String.valueOf(id));
					passportBean.setStatus(1);
					passportBean.setCreationDate(CPSUtils.getCurrentDate());
					passportBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					message = passportDAO.managePassportDetails(passportBean);
					passportBean.setMessage(message);
				}
				passportBean.setPassportList(passportDAO.getEmployeePassportDetails(passportBean.getSfid()));
				int size = passportBean.getPassportList().size();
				if (size > 0) {
					session.setAttribute(CPSConstants.MASTERJSON, passportBean.getPassportList().get(size - 1));
					passportBean.getPassportList().remove(size - 1);
				}

			} else {
				passportList = passportDAO.getEditPassportDetails(passportBean.getId());
				if (CPSUtils.checkList(passportList)) {
					for (PassportBean object : passportList) {
						object.setPassPortFor(passportBean.getPassPortFor());
						object.setFamilyMember(passportBean.getFamilyMember());
						object.setValidUpto(passportBean.getValidUpto());
						object.setPassportNumber(passportBean.getPassportNumber());
						object.setIssuePlace(passportBean.getIssuePlace());
						object.setDetails(passportBean.getDetails());
						object.setCreationDate(CPSUtils.formattedDate(object.getCreationDate()));
						object.setLastModifiedDate(CPSUtils.getCurrentDate());
						object.setRemarks(passportBean.getRemarks());
						message = passportDAO.managePassportDetails(object);
						if (message == CPSConstants.SUCCESS) {
							message = CPSConstants.UPDATE;
						}
					}
				}
				passportBean.setMessage(message);
				passportBean.setPassportList(passportDAO.getEmployeePassportDetails(passportBean.getSfid()));
				int size = passportBean.getPassportList().size();
				if (size > 0) {
					session.setAttribute(CPSConstants.MASTERJSON, passportBean.getPassportList().get(size - 1));
					passportBean.getPassportList().remove(size - 1);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return passportBean;
	}

	public List<FamilyRelationDTO> getFamilyMembersList(String sfid) throws Exception {
		List<FamilyRelationDTO> familyList = null;
		try {
			familyList = passportDAO.getFamilyMembersList(sfid);
		} catch (Exception e) {
			throw e;
		}
		return familyList;
	}

	@SuppressWarnings("unchecked")
	public PassportBean getPassportProceedingDetails(PassportBean passportBean) throws Exception{
		try{
		passportBean=passportDAO.getEmployeeDetails(passportBean);
		if(!CPSUtils.isNull(passportBean.getEmpDetails())){
			passportBean.setEmpAddress(passportDAO.getEmpAddress(passportBean.getSfid()));
			passportBean.setProceedingList(commonDataDAO.getMasterData(CPSConstants.PROCEEDINGTYPEDTO));
			passportBean.setFamilyMemberDetails(passportDAO.getFamilyMemberDetails(passportBean));
		}else{
			passportBean.setMessage(CPSConstants.EMPNOTEXISTS);
		}
		}catch(Exception e){
			throw e;
		}return passportBean;
	}

	public String savePassportApplicationDetails(PassportBean passportBean) throws Exception{
		String message="";
		List<PassportApplicationDetailsDTO> list=null;
		try{
			
			list=passportDAO.checkSFIDRecord(passportBean);
			/*if(list.size()>=1 && !CPSUtils.isNullOrEmpty(passportBean.getId())){
				message=CPSConstants.SUCCESS;
			}else if(list.size()==0 && CPSUtils.isNullOrEmpty(passportBean.getId())){
				message=CPSConstants.SUCCESS;
			}
			elsea
				message=CPSConstants.DUPLICATE;*/
			if(list.size()>=1){
				message=CPSConstants.DUPLICATE;
			}else{
				message=CPSConstants.SUCCESS;
			}
			
			if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
				PassportApplicationDetailsDTO bean=new PassportApplicationDetailsDTO();
				BeanUtils.copyProperties(bean,passportBean);
				if(!CPSUtils.isNullOrEmpty(passportBean.getId()))
					bean.setRequestID(Integer.parseInt(passportBean.getId()));
				if(!CPSUtils.isNullOrEmpty(passportBean.getFamilyMemberId()))
					bean.setFamilyMemberId(bean.getFamilyMemberId().substring(0, bean.getFamilyMemberId().length()-1));
				bean.setStatus(1);
				bean.setCreationDate(CPSUtils.getCurrentDate());
				bean.setLastModifiedDate(CPSUtils.getCurrentDate());
				bean.setSfid(passportBean.getSfID().toUpperCase());
				message=passportDAO.submitPassportApplicationDetails(bean);
			}
		}catch(Exception e){
			throw e;
		}return message;
	}
	
	public String deletePassportApplicationDetails(PassportBean passportBean) throws Exception{
		String message="";
		try{
			message=passportDAO.deletePassportApplicationDetails(Integer.parseInt(passportBean.getId()),passportBean.getBeanType());
		}catch(Exception e){
			throw e;
		}return message;
	}
	
	
	public List<PassportApplicationDetailsDTO> getPassportApplicationDetails() throws Exception{
		List<PassportApplicationDetailsDTO> list=null;
		try {
			list=passportDAO.getPassportApplicationDetails();
			for(PassportApplicationDetailsDTO bean:list){
				bean.setIssueDate(CPSUtils.formattedDate(bean.getIssueDate()));
				bean.setReturnDate(CPSUtils.formattedDate(bean.getReturnDate()));
				bean.setValidityDate(CPSUtils.formattedDate(bean.getValidityDate()));
				bean.setDepartureDate(CPSUtils.formattedDate(bean.getDepartureDate()));
				bean.setHqLetterDt(CPSUtils.formattedDate(bean.getHqLetterDt()));
			}
		} catch (Exception e) {
			throw e;
		}return list;
	}
	
	
	public String submitPassportDetailsforNOC(PassportBean passportBean) throws Exception{
		String message="";
		try{
			if(CPSUtils.compareStrings(passportBean.getPassportAppType(), "passport")){
				PassportApplicationDTO bean=new PassportApplicationDTO();
				BeanUtils.copyProperties(bean,passportBean);
				bean.setStatus(1);
				message=passportDAO.submitPassportDetailsforNOC(bean);
			}
			else if(CPSUtils.compareStrings(passportBean.getPassportAppType(), "passport & abroad")){
				//message=savePassportApplicationDetails(passportBean);
				if(CPSUtils.compareStrings(passportBean.getType(),passportBean.getParam())){
					PassportApplicationDTO applicationDTO=new PassportApplicationDTO();
					BeanUtils.copyProperties(applicationDTO,passportBean);
					applicationDTO.setStatus(1);
					message=passportDAO.submitPassportDetailsforNOC(applicationDTO);
				}
				else{
					PassportApplicationDetailsDTO bean=new PassportApplicationDetailsDTO();
					BeanUtils.copyProperties(bean,passportBean);
					if(!CPSUtils.isNullOrEmpty(passportBean.getId()))
						bean.setRequestID(Integer.parseInt(passportBean.getId()));
					if(!CPSUtils.isNullOrEmpty(passportBean.getFamilyMemberId()))
						bean.setFamilyMemberId(bean.getFamilyMemberId().substring(0, bean.getFamilyMemberId().length()-1));
					bean.setStatus(1);
					bean.setCreationDate(CPSUtils.getCurrentDate());
					bean.setLastModifiedDate(CPSUtils.getCurrentDate());
					bean.setSfid(passportBean.getSfID().toUpperCase());
					message=passportDAO.submitPassportApplicationDetails(bean);
					
					PassportApplicationDTO applicationDTO=new PassportApplicationDTO();
					BeanUtils.copyProperties(applicationDTO,passportBean);
					applicationDTO.setId(0);
					applicationDTO.setStatus(1);
					message=passportDAO.submitPassportDetailsforNOC(applicationDTO);
				}
				
			}
			
		}catch(Exception e){
			throw e;
		}return message;
	}
	

	public List<PassportApplicationDTO> getPassportApplicationDetailsForNOC() throws Exception{
		List<PassportApplicationDTO> list=null;
		try {
			list=passportDAO.getPassportApplicationDetailsForNOC();
			/*for(PassportApplicationDTO bean: list){
				bean.setAdminNoteDt(CPSUtils.formattedDate(bean.getAdminNoteDt()));
				bean.setLetterDate(CPSUtils.formattedDate(bean.getLetterDate()));
				bean.setReceivedDt(CPSUtils.formattedDate(bean.getReceivedDt()));
				bean.setIdCertificateDt(CPSUtils.formattedDate(bean.getIdCertificateDt()));
			}*/
		} catch (Exception e) {
			throw e;
		}return list;
	}
	

	public List<PassportApplicationDetailsDTO> getPassportApplicationDetails(PassportBean passportBean) throws Exception{
		List<PassportApplicationDetailsDTO> list=null;
		try {
			if(!CPSUtils.isNullOrEmpty(passportBean.getSfid()))
				list=passportDAO.getPassportApplicationDetails(passportBean.getSfid());
			else
				list=passportDAO.getPassportApplicationDetails(passportBean.getSfID());
			/*if(CPSUtils.compareStrings(passportBean.getParam(),"nocPassportHome") ){
				for(int i=0;i<list.size();i++){
					if(CPSUtils.isNullOrEmpty(list.get(i).getHqLetterNo()) && CPSUtils.isNullOrEmpty(list.get(i).getHqLetterDt())){
						list.remove(i);
					}
				}
				
			}*/
			/*for(PassportApplicationDetailsDTO bean:list){
				bean.setIssueDate(CPSUtils.formattedDate(bean.getIssueDate()));
				bean.setReturnDate(CPSUtils.formattedDate(bean.getReturnDate()));
				bean.setValidityDate(CPSUtils.formattedDate(bean.getValidityDate()));
				bean.setDepartureDate(CPSUtils.formattedDate(bean.getDepartureDate()));
				bean.setCoPeriodFrom(CPSUtils.formattedDate(bean.getCoPeriodFrom()));
				bean.setCoPeriodFrom(CPSUtils.formattedDate(bean.getCoPeriodTo()));
				bean.setPpissueDate(CPSUtils.formattedDate(bean.getPpissueDate()));
				bean.setPpvalidityDate(CPSUtils.formattedDate(bean.getPpvalidityDate()));
			}*/
		} catch (Exception e) {
			throw e;
		}return list;
	}

	
	public List<PassportApplicationDetailsDTO> getSFIDForVerifiedType() throws Exception{
		List<PassportApplicationDetailsDTO> list=null;
		try {
			list=passportDAO.getSFIDForVerifiedType();
		} catch (Exception e) {
			throw e;
		}return list;
	}

	
	public String updatePassportApplicationDetails(PassportBean passportBean) throws Exception{
		String message="";
		try{	
				message=passportDAO.updatePassportApplicationDetails(passportBean);
				
		}catch(Exception e){
			throw e;
		}return message;
	}
	
	public List<PassportApplicationDetailsDTO> getSFIDHQrsDetails() throws Exception{
		List<PassportApplicationDetailsDTO> list=null;
		try {
			list=passportDAO.getSFIDHQrsDetails();
			for(PassportApplicationDetailsDTO bean:list){
				bean.setIssueDate(CPSUtils.formattedDate(bean.getIssueDate()));
				bean.setReturnDate(CPSUtils.formattedDate(bean.getReturnDate()));
				bean.setValidityDate(CPSUtils.formattedDate(bean.getValidityDate()));
				bean.setDepartureDate(CPSUtils.formattedDate(bean.getDepartureDate()));
				bean.setHqLetterDt(CPSUtils.formattedDate(bean.getHqLetterDt()));
			}
		} catch (Exception e) {
			throw e;
		}return list;
	}
	
	
	
}