package com.callippus.web.dao.passport;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.dto.FamilyRelationDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.passport.PassportApplicationDTO;
import com.callippus.web.beans.passport.PassportApplicationDetailsDTO;
import com.callippus.web.beans.passport.PassportBean;
import com.callippus.web.beans.passport.PassportRequestProcessBean;

public interface IPassportDAO {

	public ArrayList<PassportBean> getEmployeePassportDetails(String sfid) throws Exception;

	public String deletePassport(String id) throws Exception;

	public String managePassportDetails(PassportBean passportBean) throws Exception;

	public List<PassportBean> getEditPassportDetails(String passportId) throws Exception;

	public List<FamilyRelationDTO> getFamilyMembersList(String sfid) throws Exception;

	public List passportTypeChecking(PassportBean passportBean) throws Exception;

	public PassportBean getEmployeeDetails(PassportBean passportBean) throws Exception;
	
	public List<FamilyBean> getFamilyMemberDetails(PassportBean passportBean) throws Exception;
	
	public EmployeeBean getEmpDetails(PassportRequestProcessBean passportBean) throws Exception;
	
	public String getEmpAddress(String sfid) throws Exception;
	
	//public List<PassportApplicationDetailsDTO> checkSFIDRecord(String sfid) throws Exception;
	public List<PassportApplicationDetailsDTO> checkSFIDRecord(PassportBean passportBean) throws Exception;

	public String submitPassportApplicationDetails(PassportApplicationDetailsDTO detailsDTO)throws Exception;
	
	public String deletePassportApplicationDetails(int id,String bean)throws Exception;
	
	public List<PassportApplicationDetailsDTO> getPassportApplicationDetails() throws Exception;
	
	public List<PassportApplicationDetailsDTO> getPassportApplicationDetails(String sfid) throws Exception;
	
	public String submitPassportDetailsforNOC(PassportApplicationDTO applicationDTO)throws Exception;
	
	public List<PassportApplicationDTO> getPassportApplicationDetailsForNOC() throws Exception;
	
	public List<PassportApplicationDetailsDTO> getSFIDForVerifiedType() throws Exception;
	
	public List<PassportApplicationDetailsDTO> getSFIDHQrsDetails() throws Exception;
	
	public String updatePassportApplicationDetails(PassportBean passportBean)throws Exception;
	
	//public String updatePassportApplicationDetails()throws Exception;
	
}
