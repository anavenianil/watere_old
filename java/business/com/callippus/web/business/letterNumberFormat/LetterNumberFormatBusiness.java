package com.callippus.web.business.letterNumberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.ApplicationRoleMappingDTO;
import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.HierarchyDTO;
import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.dto.OrganizationsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.IonCirculatedDetailsDTO;
import com.callippus.web.beans.letterNumberFormat.IonDeptDTO;
import com.callippus.web.beans.letterNumberFormat.IonDesignDTO;
import com.callippus.web.beans.letterNumberFormat.IonFileDTO;
import com.callippus.web.beans.letterNumberFormat.IonOrgDTO;
import com.callippus.web.beans.letterNumberFormat.IonOrgRoleDTO;
import com.callippus.web.beans.letterNumberFormat.IonSfidDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatBean;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberSeriesDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.letterNumberFormat.ILetterNumberFormatDAO;
import com.callippus.web.hrdg.dao.ICommonDAO;



@Service
public class LetterNumberFormatBusiness {
	@Autowired
	private ILetterNumberFormatDAO letterNumberFormatDAO;
	@Autowired
	private ICommonDAO commonDAO;
	
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public List getDeptList() throws Exception{
		List deptList = null;
		try
		{
			deptList = letterNumberFormatDAO.getDeptList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return deptList;
	}
	public String manageLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean) throws Exception {
		String message = null;
		try {

			
			letterNumberFormatBean.setCreationDate(CPSUtils.getCurrentDate());
			letterNumberFormatBean.setCreatedBy(letterNumberFormatBean.getSfid());
			letterNumberFormatBean.setStatus("1");
			if (CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId())) {
				if (CPSUtils.compareStrings(checkLetterNumberSeries(letterNumberFormatBean), CPSConstants.SUCCESS)) {

					Object dto = new LetterNumberSeriesDTO();
					BeanUtils.copyProperties(dto, letterNumberFormatBean);

					message = commonDAO.save(dto);
					
				}

				else {
					message = CPSConstants.DUPLICATE;
				}

			} else {
				if (CPSUtils.compareStrings(checkLetterNumberSeries(letterNumberFormatBean), CPSConstants.SUCCESS)) {
					message = letterNumberFormatDAO.updateLetterNumberSeries(letterNumberFormatBean);
									
				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String checkLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(letterNumberFormatDAO.checkLetterNumberSeries(letterNumberFormatBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}
	public List getLetterNumberSeriesList() throws Exception {
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getLetterNumberSeriesList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getLetterNumberSerisList() throws Exception {
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getLetterNumberSerisList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getLetterNumberFormatList(LetterNumberFormatBean letterNumberFormatBean) throws Exception {
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getLetterNumberFormatList(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean) throws Exception {
		String message = null;
		try {

			
			letterNumberFormatBean.setCreationDate(CPSUtils.getCurrentDate());
			letterNumberFormatBean.setCreatedBy(letterNumberFormatBean.getSfid());
			letterNumberFormatBean.setStatus("1");
			if (CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId())) {
				if (CPSUtils.compareStrings(checkLetterNumberFormat(letterNumberFormatBean), CPSConstants.SUCCESS)) {

					Object dto = new LetterNumberFormatDTO();
					BeanUtils.copyProperties(dto, letterNumberFormatBean);

					message = commonDAO.save(dto);	
				}
				else {
					message = CPSConstants.DUPLICATE;
				}
			} else {
				if (CPSUtils.compareStrings(checkLetterNumberFormat(letterNumberFormatBean), CPSConstants.SUCCESS)) {
					message = letterNumberFormatDAO.updateLetterNumberFormat(letterNumberFormatBean);
									
				} else {
					message = CPSConstants.DUPLICATE;
				}

			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String checkLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(letterNumberFormatDAO.checkLetterNumberFormat(letterNumberFormatBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;

	}
	public LetterNumberFormatBean deleteLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean) throws Exception {
		String message = null;
		try {

			
			letterNumberFormatBean.setCreationDate(CPSUtils.getCurrentDate());
			letterNumberFormatBean.setCreatedBy(letterNumberFormatBean.getSfid());
			letterNumberFormatBean.setStatus("1");

			if (CPSUtils.compareStrings(letterNumberFormatDAO.checkLetterNumberSeres(letterNumberFormatBean), CPSConstants.SUCCESS)) {
				message = letterNumberFormatDAO.deleteLetterNumberSeries(letterNumberFormatBean);
				letterNumberFormatBean.setMessage(message);
			}
			else
			{
				letterNumberFormatBean.setMessage(CPSConstants.DELETEFAIL);
				letterNumberFormatBean.setReason(" in LetterNumberFormat Details");
			}
 

		} catch (Exception e) {
			throw e;
		}
		return letterNumberFormatBean;
	}
	public LetterNumberFormatBean deleteLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean) throws Exception {
		String message = null;
		try {

			
			letterNumberFormatBean.setCreationDate(CPSUtils.getCurrentDate());
			letterNumberFormatBean.setCreatedBy(letterNumberFormatBean.getSfid());
			letterNumberFormatBean.setStatus("1");

			if (CPSUtils.compareStrings(letterNumberFormatDAO.checkLetterNumberFormt(letterNumberFormatBean), CPSConstants.SUCCESS)) {
				message = letterNumberFormatDAO.deleteLetterNumberFormat(letterNumberFormatBean);
				letterNumberFormatBean.setMessage(message);
			}
			else
			{
				letterNumberFormatBean.setMessage(CPSConstants.DELETEFAIL);
				letterNumberFormatBean.setReason(" in ION Master");
			}
 

		} catch (Exception e) {
			throw e;
		}
		return letterNumberFormatBean;
	}
	
	public LetterNumberFormatBean getIncrementLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean)throws Exception {
		List list  = null;
		try
		{
			list  = letterNumberFormatDAO.getLetterNumberFormatDetails(letterNumberFormatBean);
			if(CPSUtils.checkList(list))
			{
				LetterNumberFormatDTO dto = (LetterNumberFormatDTO)list.get(0);
				letterNumberFormatBean.setLetterNumber("ASL/"+dto.getLetterNumberFormat()+"/"+dto.getDeptName()+"/"+dto.getSerialNum()+"/"+dto.getShortForm()+"/"+(dto.getRunningNum()+1));
				list = new ArrayList();
				list.add(0,letterNumberFormatBean.getLetterNumber());
				letterNumberFormatBean.setNumberList(list);
				
			}
			
		}
		catch(Exception e)
		{
			throw e;
		}
		return letterNumberFormatBean;
		
	}
	public List getIONMstList(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getIONMstList(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;	
		}
		return list;
	}
	public LetterNumberFormatBean manageLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		String message = null;
		try {

			
			letterNumberFormatBean.setCreationDate(CPSUtils.getCurrentDate());
			letterNumberFormatBean.setCreatedBy(letterNumberFormatBean.getSfid());
			letterNumberFormatBean.setStatus("1");
			if (CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId())) {
				if (CPSUtils.compareStrings(checkLetterNumberFormatDetails(letterNumberFormatBean), CPSConstants.SUCCESS)) {

					IONDTO dto = new IONDTO();
					BeanUtils.copyProperties(dto, letterNumberFormatBean);
					dto.setCirculationStatus(0);

					message = commonDAO.save(dto);
					letterNumberFormatDAO.updateLetterNumberFormatRunningNum(letterNumberFormatBean);
					letterNumberFormatBean=getIncrementLetterNumberFormat(letterNumberFormatBean);
				}

//				else {
//					message = CPSConstants.DUPLICATE;
//				}

			} else {
				//if (CPSUtils.compareStrings(checkLetterNumberFormat(letterNumberFormatBean), CPSConstants.SUCCESS)) {
					message = letterNumberFormatDAO.updateLetterNumberFormatDetails(letterNumberFormatBean);
					letterNumberFormatBean=getIncrementLetterNumberFormat(letterNumberFormatBean);
									
//				} else {
//					message = CPSConstants.DUPLICATE;
//				}

			}

		} catch (Exception e) {
			throw e;
		}
		letterNumberFormatBean.setMessage(message);
		return letterNumberFormatBean;
	}
	private String checkLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		String message = null;
		try {

			if (CPSUtils.checkList(letterNumberFormatDAO.checkLetterNumberFormatDetails(letterNumberFormatBean))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;
	}
	public List getIONMasterList(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getIONMasterList(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageIONMaster(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		String message = null;
		try {

			
			letterNumberFormatBean.setCreationDate(CPSUtils.getCurrentDate());
			letterNumberFormatBean.setCreatedBy(letterNumberFormatBean.getSfid());
			letterNumberFormatBean.setStatus("1");
			if (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId()) && CPSUtils.compareStrings(letterNumberFormatBean.getCirculationStatus(), "0")) {
				//if (CPSUtils.compareStrings(checkIonLetterNumber(letterNumberFormatBean), CPSConstants.SUCCESS)) {

					IONDTO dto = new IONDTO();
					BeanUtils.copyProperties(dto, letterNumberFormatBean);
					dto.setIonInitiatedSfid((!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getIonInitiatedSfid()))?letterNumberFormatBean.getIonInitiatedSfid():letterNumberFormatBean.getSfid());
					dto.setSfid(letterNumberFormatBean.getSfid());
					message = letterNumberFormatDAO.updateIONdetailsForLetter(dto,letterNumberFormatBean.getId());
					if (CPSUtils.compareStrings(message, CPSConstants.UPDATE)) {
						String[] depts = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDepartmentId())) ? letterNumberFormatBean.getDepartmentId().substring(0,letterNumberFormatBean.getDepartmentId().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(depts) && i < depts.length; i++) {
							
							IonDeptDTO ionDto = new IonDeptDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getIonLetterNumberId()));
							ionDto.setDepartmentId(Integer.parseInt(depts[i]));
							ionDto.setCopyStatus(0);
							commonDAO.save(ionDto);
							
						}
						
						String[] designs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDesignationId())) ? letterNumberFormatBean.getDesignationId().substring(0,letterNumberFormatBean.getDesignationId().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(designs) && i < designs.length; i++) {
							
							IonDesignDTO ionDto = new IonDesignDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getIonLetterNumberId()));
							ionDto.setDesignationId(Integer.parseInt(designs[i]));
							ionDto.setCopyStatus(0);
							commonDAO.save(ionDto);
							
						}
						
						
						String[] orgRole = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrgRoleId())) ? letterNumberFormatBean.getOrgRoleId().substring(0,letterNumberFormatBean.getOrgRoleId().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(orgRole) && i < orgRole.length; i++) {
							
							IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getIonLetterNumberId()));
							ionDto.setOrgRoleId(Integer.parseInt(orgRole[i]));
							ionDto.setOrgOrHirarchy("R");
							commonDAO.save(ionDto);
							
						}
						String[] roleHirarchy = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRoleHirarchyId())) ? letterNumberFormatBean.getRoleHirarchyId().substring(0,letterNumberFormatBean.getRoleHirarchyId().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(orgRole) && i < orgRole.length; i++) {
							
							IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setOrgRoleId(Integer.parseInt(roleHirarchy[i]));
							ionDto.setOrgOrHirarchy("H");
							commonDAO.save(ionDto);
							
						}
						
						String[] sfids = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getSfidNameId())) ? letterNumberFormatBean.getSfidNameId().substring(0,letterNumberFormatBean.getSfidNameId().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(sfids) && i < sfids.length; i++) {
							
							IonSfidDTO ionDto = new IonSfidDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getIonLetterNumberId()));
							ionDto.setSfid(sfids[i]);
							ionDto.setCopyStatus(0);
							commonDAO.save(ionDto);
							
						}
						String[] orgs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrganizationId())) ? letterNumberFormatBean.getOrganizationId().substring(0,letterNumberFormatBean.getOrganizationId().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(orgs) && i < depts.length; i++) {
							
							IonOrgDTO ionDto = new IonOrgDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setOrganizationId(Integer.parseInt(orgs[i]));
							ionDto.setCopyStatus(0);
							commonDAO.save(ionDto);
							
						}
						//Copy
						
						 depts = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDepartmentIdCopy())) ? letterNumberFormatBean.getDepartmentIdCopy().substring(0,letterNumberFormatBean.getDepartmentIdCopy().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(depts) && i < depts.length; i++) {
							
							IonDeptDTO ionDto = new IonDeptDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setDepartmentId(Integer.parseInt(depts[i]));
							ionDto.setCopyStatus(1);
							commonDAO.save(ionDto);
							
						}
						
						 designs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDesignationIdCopy())) ? letterNumberFormatBean.getDesignationIdCopy().substring(0,letterNumberFormatBean.getDesignationIdCopy().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(designs) && i < designs.length; i++) {
							
							IonDesignDTO ionDto = new IonDesignDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setDesignationId(Integer.parseInt(designs[i]));
							ionDto.setCopyStatus(1);
							commonDAO.save(ionDto);
							
						}
						
						
						 orgRole = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrgRoleIdCopy())) ? letterNumberFormatBean.getOrgRoleIdCopy().substring(0,letterNumberFormatBean.getOrgRoleIdCopy().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(orgRole) && i < orgRole.length; i++) {
							
							IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setOrgRoleId(Integer.parseInt(orgRole[i]));
							ionDto.setOrgOrHirarchy("RC");
							commonDAO.save(ionDto);
							
						}
						
						 sfids = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getSfidNameIdCopy())) ? letterNumberFormatBean.getSfidNameIdCopy().substring(0,letterNumberFormatBean.getSfidNameIdCopy().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(sfids) && i < sfids.length; i++) {
							
							IonSfidDTO ionDto = new IonSfidDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setSfid(sfids[i]);
							ionDto.setCopyStatus(1);
							commonDAO.save(ionDto);
							
						}
						 roleHirarchy = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRoleHirarchyIdCopy())) ? letterNumberFormatBean.getRoleHirarchyIdCopy().substring(0,letterNumberFormatBean.getRoleHirarchyIdCopy().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(roleHirarchy) && i < roleHirarchy.length; i++) {
							
							IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setOrgRoleId(Integer.parseInt(roleHirarchy[i]));
							ionDto.setOrgOrHirarchy("HC");
							commonDAO.save(ionDto);
							
						}
						orgs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrganizationIdCopy())) ? letterNumberFormatBean.getOrganizationIdCopy().substring(0,letterNumberFormatBean.getOrganizationIdCopy().length()-1).split(",") : null;
						for (int i = 0; !CPSUtils.isNullOrEmpty(orgs) && i < depts.length; i++) {
							
							IonOrgDTO ionDto = new IonOrgDTO();
							ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
							ionDto.setOrganizationId(Integer.parseInt(orgs[i]));
							ionDto.setCopyStatus(1);
							commonDAO.save(ionDto);
							
						}
					}
				//}
					

			} 
			else
			{
				IONDTO dto = new IONDTO(); 
				BeanUtils.copyProperties(dto, letterNumberFormatBean);
				dto.setIonInitiatedSfid((!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getIonInitiatedSfid()))?letterNumberFormatBean.getIonInitiatedSfid():letterNumberFormatBean.getSfid());
				dto.setSfid(letterNumberFormatBean.getSfid());
				
				message = letterNumberFormatDAO.updateIONdetailsForLetter(dto,letterNumberFormatBean.getId());
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonDeptDTO","","");
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonDesignDTO","","");
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonOrgRoleDTO","R","");
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonSfidDTO","","");
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonOrgRoleDTO","H","");
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonOrgRoleDTO","RC","");
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonOrgRoleDTO","HC","");
				letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonOrgDTO","","");
				
				if (CPSUtils.compareStrings(message, CPSConstants.UPDATE)) {
					String[] depts = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDepartmentId())) ? letterNumberFormatBean.getDepartmentId().substring(0,letterNumberFormatBean.getDepartmentId().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(depts) && i < depts.length; i++) {
						
						IonDeptDTO ionDto = new IonDeptDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setDepartmentId(Integer.parseInt(depts[i]));
						ionDto.setCopyStatus(0);
						commonDAO.save(ionDto);
						
					}
					
					String[] designs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDesignationId())) ? letterNumberFormatBean.getDesignationId().substring(0,letterNumberFormatBean.getDesignationId().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(designs) && i < designs.length; i++) {
						
						IonDesignDTO ionDto = new IonDesignDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setDesignationId(Integer.parseInt(designs[i]));
						ionDto.setCopyStatus(0);
						commonDAO.save(ionDto);
						
					}
					
					
					String[] orgRole = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrgRoleId())) ? letterNumberFormatBean.getOrgRoleId().substring(0,letterNumberFormatBean.getOrgRoleId().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(orgRole) && i < orgRole.length; i++) {
						
						IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setOrgRoleId(Integer.parseInt(orgRole[i]));
						ionDto.setOrgOrHirarchy("R");
						commonDAO.save(ionDto);
						
					}
					
					String[] sfids = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getSfidNameId())) ? letterNumberFormatBean.getSfidNameId().substring(0,letterNumberFormatBean.getSfidNameId().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(sfids) && i < sfids.length; i++) {
						
						IonSfidDTO ionDto = new IonSfidDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setSfid(sfids[i]);
						ionDto.setCopyStatus(0);
						commonDAO.save(ionDto);
						
					}
					String[] roleHirarchy = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRoleHirarchyId())) ? letterNumberFormatBean.getRoleHirarchyId().substring(0,letterNumberFormatBean.getRoleHirarchyId().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(roleHirarchy) && i < roleHirarchy.length; i++) {
						
						IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setOrgRoleId(Integer.parseInt(roleHirarchy[i]));
						ionDto.setOrgOrHirarchy("H");
						commonDAO.save(ionDto);
						
					}
					String[] orgs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrganizationId())) ? letterNumberFormatBean.getOrganizationId().substring(0,letterNumberFormatBean.getOrganizationId().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(orgs) && i < orgs.length; i++) {
						
						IonOrgDTO ionDto = new IonOrgDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setOrganizationId(Integer.parseInt(orgs[i]));
						ionDto.setCopyStatus(0);
						commonDAO.save(ionDto);
						
					}
					
					//Copy
					
					 depts = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDepartmentIdCopy())) ? letterNumberFormatBean.getDepartmentIdCopy().substring(0,letterNumberFormatBean.getDepartmentIdCopy().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(depts) && i < depts.length; i++) {
						
						IonDeptDTO ionDto = new IonDeptDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setDepartmentId(Integer.parseInt(depts[i]));
						ionDto.setCopyStatus(1);
						commonDAO.save(ionDto);
						
					}
					
					 designs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getDesignationIdCopy())) ? letterNumberFormatBean.getDesignationIdCopy().substring(0,letterNumberFormatBean.getDesignationIdCopy().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(designs) && i < designs.length; i++) {
						
						IonDesignDTO ionDto = new IonDesignDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setDesignationId(Integer.parseInt(designs[i]));
						ionDto.setCopyStatus(1);
						commonDAO.save(ionDto);
						
					}
					
					
					 orgRole = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrgRoleIdCopy())) ? letterNumberFormatBean.getOrgRoleIdCopy().substring(0,letterNumberFormatBean.getOrgRoleIdCopy().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(orgRole) && i < orgRole.length; i++) {
						
						IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setOrgRoleId(Integer.parseInt(orgRole[i]));
						ionDto.setOrgOrHirarchy("RC");
						commonDAO.save(ionDto);
						
					}
					
					 sfids = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getSfidNameIdCopy())) ? letterNumberFormatBean.getSfidNameIdCopy().substring(0,letterNumberFormatBean.getSfidNameIdCopy().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(sfids) && i < sfids.length; i++) {
						
						IonSfidDTO ionDto = new IonSfidDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setSfid(sfids[i]);
						ionDto.setCopyStatus(1);
						commonDAO.save(ionDto);
						
					}
					 roleHirarchy = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getRoleHirarchyIdCopy())) ? letterNumberFormatBean.getRoleHirarchyIdCopy().substring(0,letterNumberFormatBean.getRoleHirarchyIdCopy().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(roleHirarchy) && i < roleHirarchy.length; i++) {
						
						IonOrgRoleDTO ionDto = new IonOrgRoleDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setOrgRoleId(Integer.parseInt(roleHirarchy[i]));
						ionDto.setOrgOrHirarchy("HC");
						commonDAO.save(ionDto);
						
					}
					orgs = (!CPSUtils.isNullOrEmpty(letterNumberFormatBean.getOrganizationIdCopy())) ? letterNumberFormatBean.getOrganizationIdCopy().substring(0,letterNumberFormatBean.getOrganizationIdCopy().length()-1).split(",") : null;
					for (int i = 0; !CPSUtils.isNullOrEmpty(orgs) && i < orgs.length; i++) {
						
						IonOrgDTO ionDto = new IonOrgDTO();
						ionDto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
						ionDto.setOrganizationId(Integer.parseInt(orgs[i]));
						ionDto.setCopyStatus(1);
						commonDAO.save(ionDto);
						
					}
				}
			}


		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List getDepartmentList() throws Exception{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getDepartmentList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	
	public List designationList() throws Exception
	{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.designationList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getDeptListBasedOnRole(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		List roleList = null;
		
		try
		{
			roleList = letterNumberFormatDAO.getRole(letterNumberFormatBean);
			
			if(CPSUtils.checkList(roleList)){
				for(int i = 0;i<roleList.size();i++)
				{
				ApplicationRoleMappingDTO dto = (ApplicationRoleMappingDTO)roleList.get(i);
				if(dto.getApplicationRoleId()==CPSConstants.SUPERADMIN)
				{
					list = letterNumberFormatDAO.getDepartmentList();
					break;
				}
				else
				{
					List deptList = getDeptListBasedOnRoles(letterNumberFormatBean);
					if(!CPSUtils.checkList(deptList)){
						list = letterNumberFormatDAO.getDeptListBasedOnRole(letterNumberFormatBean);
					}
					else list=deptList;
					
				}
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	private List getDeptListBasedOnRoles(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		List deptList = null;
		
		try
		{
			
			deptList = letterNumberFormatDAO.getDepartmentListBasedOnSfid(letterNumberFormatBean);
			
		}
		catch(Exception e)
		{
			throw e;
		}
		return deptList;
	}
	public List getDeptRoleBasedList() throws Exception{

		List list = null;
		try
		{
			list = commonDataDAO.getMasterData(CPSConstants.ORGINSTANCEDTO);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public List<EmployeeBean> getSfidList(String str,String type,LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List<EmployeeBean> list = null;

		try
		{
			list = letterNumberFormatDAO.getSfidList(str,type,letterNumberFormatBean);
			if((!CPSUtils.isNullOrEmpty(type) && CPSUtils.compareStrings(type, "forwardBy")) || (!CPSUtils.isNullOrEmpty(type) && CPSUtils.compareStrings(type, "approvedBy")))
			{
				List<EmployeeBean> list1 = null;
				if(CPSUtils.checkList(list))
				{
					list1 = new ArrayList<EmployeeBean>();
					for(int i = 0;i<list.size();i++)
					{
						if(list.get(i)!= null )
						{
						EmployeeBean bean = (EmployeeBean)list.get(i);
						EmployeeBean bean1 = new EmployeeBean();
						if(!CPSUtils.isNullOrEmpty(bean.getUserSfid()))
						{
						bean1.setUserSfid(bean.getUserSfid().split("###")[0]);
						bean1.setNameInServiceBook(bean.getUserSfid().split("###")[1]);
						list1.add(bean1);
						}
						}
					}
					return list1;
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List<IONDTO> getNoticeBoardList(LetterNumberFormatBean letterNumberFormatBean)throws Exception {
		List<IONDTO> list = null;
		try
		{
			list = letterNumberFormatDAO.getNoticeBoardList(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	
	public List getIONDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getIONDetails(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public LetterNumberFormatBean getIONMstDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		try
		{
			
			list = letterNumberFormatDAO.getIONMstDetails(letterNumberFormatBean);
			if(!CPSUtils.checkList(list))
			{
				letterNumberFormatBean.setLetterNumber(null); 
				letterNumberFormatBean.setSubject(null);
				letterNumberFormatBean.setContent(null);
				
				letterNumberFormatBean.setReference(null);
				letterNumberFormatBean.setEnclosers(null);
				letterNumberFormatBean.setCirculationStatus("0");
				letterNumberFormatBean.setPublicStatus("N");
				letterNumberFormatBean.setDeparmentList(null);
				letterNumberFormatBean.setDesignList(null);
				letterNumberFormatBean.setOrgRoleList(null);
				letterNumberFormatBean.setSfidList(null);
				letterNumberFormatBean.setOrgList(null);
				letterNumberFormatBean.setRoleHirarchyList(null);
				letterNumberFormatBean.setDeparmentListCopy(null);
				letterNumberFormatBean.setDesignListCopy(null);
				letterNumberFormatBean.setOrgRoleListCopy(null);
				letterNumberFormatBean.setSfidListCopy(null);
				letterNumberFormatBean.setOrgListCopy(null);
				letterNumberFormatBean.setRoleHirarchyListCopy(null);
				letterNumberFormatBean.setIonInitiatedRoleId(null);
				letterNumberFormatBean.setIonInitiatedSfid(null);
				letterNumberFormatBean.setIonForwardSfid(null);
				letterNumberFormatBean.setIonForwardRoleId(null);
				letterNumberFormatBean.setIonApprovedSfid(null);
				letterNumberFormatBean.setIonApprovedRoleId(null);
				letterNumberFormatBean.setFileEncloserList(null);
				
				
			}
			for(int i = 0;i < list.size();i++)
			{
				IONDTO ionDto = (IONDTO) list.get(i);
				letterNumberFormatBean.setLetterNumber(ionDto.getLetterNumber());
				letterNumberFormatBean.setIonDate(ionDto.getIonDate());
				letterNumberFormatBean.setSubject(ionDto.getSubject());
				letterNumberFormatBean.setContent(ionDto.getContent());
				if(CPSUtils.isNull(ionDto.getContent()))
						letterNumberFormatBean.setContent(ionDto.getDelegation());
				letterNumberFormatBean.setReference(ionDto.getReference());
				letterNumberFormatBean.setEnclosers(ionDto.getEnclosers());
				letterNumberFormatBean.setCirculationStatus(String.valueOf(ionDto.getCirculationStatus()));
				letterNumberFormatBean.setPublicStatus(ionDto.getPublicStatus());
				letterNumberFormatBean.setDeparmentList(ionDto.getDeparmentList());
				letterNumberFormatBean.setDesignList(ionDto.getDesignList());
				letterNumberFormatBean.setOrgRoleList(ionDto.getOrgRoleList());
				letterNumberFormatBean.setRoleHirarchyList(ionDto.getRoleHirarchyList());
				letterNumberFormatBean.setSfidList(ionDto.getSfidList());
				letterNumberFormatBean.setOrgList(ionDto.getOrgList());
				letterNumberFormatBean.setDeparmentListCopy(ionDto.getDeparmentListCopy());
				letterNumberFormatBean.setDesignListCopy(ionDto.getDesignListCopy());
				letterNumberFormatBean.setOrgRoleListCopy(ionDto.getOrgRoleListCopy());
				letterNumberFormatBean.setRoleHirarchyListCopy(ionDto.getRoleHirarchyListCopy());
				letterNumberFormatBean.setSfidListCopy(ionDto.getSfidListCopy());
				letterNumberFormatBean.setOrgListCopy(ionDto.getOrgListCopy());
				letterNumberFormatBean.setIonInitiatedSfid(ionDto.getIonInitiatedSfid());
				letterNumberFormatBean.setIonInitiatedRoleId(ionDto.getIonInitiatedRoleId());
				letterNumberFormatBean.setIonForwardSfid(ionDto.getIonForwardSfid());
				letterNumberFormatBean.setIonForwardRoleId(ionDto.getIonForwardRoleId());
				letterNumberFormatBean.setIonApprovedSfid(ionDto.getIonApprovedSfid());
				letterNumberFormatBean.setIonApprovedRoleId(ionDto.getIonApprovedRoleId());
				letterNumberFormatBean.setIonInitiatedRoleList(getIONInitiatedRoleList(letterNumberFormatBean,"initiatedBy"));
				letterNumberFormatBean.setIonForwardRoleList(getIONInitiatedRoleList(letterNumberFormatBean,"forwardBy"));
				letterNumberFormatBean.setIonApprovedRoleList(getIONInitiatedRoleList(letterNumberFormatBean,"approvedBy"));
				letterNumberFormatBean.setFileEncloserList(ionDto.getFileEncloserList());
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return letterNumberFormatBean;
	}

	public void removeLists(List list, List selectedList,String str) {
		HashMap hm = new HashMap();
		
		try {
			
			for (int i = 0; CPSUtils.checkList(selectedList) && i < selectedList.size(); i++) {
				int id = 0;
				if(CPSUtils.compareStrings(str, "ionDept"))
					id = ((DepartmentsDTO)selectedList.get(i)).getId();
				if(CPSUtils.compareStrings(str, "ionDesign"))
					id = ((DesignationDTO)selectedList.get(i)).getId();
				if(CPSUtils.compareStrings(str, "ionOrgRole"))
					id = ((OrgInstanceDTO)selectedList.get(i)).getId();
				if(CPSUtils.compareStrings(str, "ionRoleHirarchy"))
					id = ((HierarchyDTO)selectedList.get(i)).getRoleID();
				if(CPSUtils.compareStrings(str, "org"))
					id = ((OrganizationsDTO)selectedList.get(i)).getId();
				
				
				hm.put(id, selectedList.get(i));
			}
			for (int i = list.size()-1; CPSUtils.checkList(list) && i >= 0; i--) {
				int id = 0;
				if(CPSUtils.compareStrings(str, "ionDept"))
					id = ((DepartmentsDTO)list.get(i)).getId();
				else if(CPSUtils.compareStrings(str, "ionDesign"))
					id = ((DesignationDTO)list.get(i)).getId();
				else if(CPSUtils.compareStrings(str, "ionRoleHirarchy"))
					id = ((HierarchyDTO)list.get(i)).getRoleID();
				else if(CPSUtils.compareStrings(str, "org"))
					id = ((OrganizationsDTO)list.get(i)).getId();
				if (hm.get(id) != null) {
					list.remove(i);
				} 

			}
			list = list;
		} catch (Exception e) {

		}
	}
	public void removeSfidLists(List list, List selectedList,String str) {
		HashMap hm = new HashMap();
		
		try {
			
			for (int i = 0; CPSUtils.checkList(selectedList) && i < selectedList.size(); i++) {
				String id = "";
				if(CPSUtils.compareStrings(str, "ionSfid"))
					id = ((EmployeeBean)selectedList.get(i)).getUserSfid();
				
				hm.put(id, selectedList.get(i));
			}
			for (int i = list.size()-1; CPSUtils.checkList(list) && i >= 0; i--) {
				String id = "";
				 if(CPSUtils.compareStrings(str, "ionSfid"))
					id = ((EmployeeBean)list.get(i)).getUserSfid();
				if (id != null && hm.get(id) != null) {
					list.remove(i);
				} 

			}
			list = list;
		} catch (Exception e) {

		}
	}
	public List getRoleHirarchyList() throws Exception{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getRoleHirarchyList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
		
	}
	public List<String> getLevel1SfidList(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List<String> list = null;
		try
		{
			list = letterNumberFormatDAO.getLevel1SfidList(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageIONCirculationDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		
			String message = null;
			try {

				
				letterNumberFormatBean.setCreationDate(CPSUtils.getCurrentDate());
				letterNumberFormatBean.setCreatedBy(letterNumberFormatBean.getSfid());
				letterNumberFormatBean.setStatus("1");
				if (CPSUtils.isNullOrEmpty(letterNumberFormatBean.getId())) {
					
						
						JSONObject mainJson1 = new JSONObject(letterNumberFormatBean.getJsonValues1());
						JSONObject mainJson2 = new JSONObject(letterNumberFormatBean.getJsonValues2());
						for(int j=0;j<mainJson1.length();j++) {
							JSONObject subJson1=(JSONObject)mainJson1.get(String.valueOf(j));
							if(mainJson2.length() != 0){
							for(int i=0;i<mainJson2.length();i++) {
								JSONObject subJson2=(JSONObject)mainJson2.get(String.valueOf(i));
								
								IonCirculatedDetailsDTO dto = new IonCirculatedDetailsDTO();
								BeanUtils.copyProperties(dto, letterNumberFormatBean);
								dto.setForwardOrReplayOrFile(letterNumberFormatBean.getSavingType());
								if(CPSUtils.compareStrings(letterNumberFormatBean.getSavingType(),"forwarded")){
								dto.setSfid(subJson2.get("sfid").toString());
								dto.setRefOrgRoleId(letterNumberFormatBean.getRefOrgRoleId());
								}
								else
								{
									dto.setSfid(letterNumberFormatBean.getSfid());	
									dto.setRefOrgRoleId(letterNumberFormatBean.getRefOrgRoleId());
								}
								
								dto.setIonId(Integer.valueOf(subJson1.get("ionId").toString()));
								message = commonDAO.save(dto);
							}}
							else{
								IonCirculatedDetailsDTO dto = new IonCirculatedDetailsDTO();
								BeanUtils.copyProperties(dto, letterNumberFormatBean);
								dto.setForwardOrReplayOrFile(subJson1.get("savingType").toString());
								dto.setSfid(letterNumberFormatBean.getSfid());	
								dto.setRefOrgRoleId(letterNumberFormatBean.getRefOrgRoleId());
								dto.setIonId(Integer.valueOf(subJson1.get("ionId").toString()));
								dto.setRemarks(letterNumberFormatBean.getRemarks());
								
								
								message = commonDAO.save(dto);
							}
						}		
				} 
				else
				{
					if(CPSUtils.compareStrings(letterNumberFormatBean.getSavingType(),"forwarded")){
						letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonCirculatedDetailsDTO",letterNumberFormatBean.getSfid(),letterNumberFormatBean.getRefOrgRoleId());
					}
					JSONObject mainJson1 = new JSONObject(letterNumberFormatBean.getJsonValues1());		
					JSONObject mainJson2 = new JSONObject(letterNumberFormatBean.getJsonValues2());
					for(int j=0;j<mainJson1.length();j++) {
						JSONObject subJson1=(JSONObject)mainJson1.get(String.valueOf(j));
						if(mainJson2.length() != 0){	
						for(int i=0;i<mainJson2.length();i++) {
								JSONObject subJson2=(JSONObject)mainJson2.get(String.valueOf(i));
								
								IonCirculatedDetailsDTO dto = new IonCirculatedDetailsDTO();
								BeanUtils.copyProperties(dto, letterNumberFormatBean);
								dto.setIonId(Integer.valueOf(subJson1.get("ionId").toString()));
								if(CPSUtils.compareStrings(letterNumberFormatBean.getSavingType(),"forwarded")){
								dto.setSfid(subJson2.get("sfid").toString());
								dto.setRefOrgRoleId(letterNumberFormatBean.getRefOrgRoleId());
								dto.setRemarks(letterNumberFormatBean.getSfid().toUpperCase().toString()+":"+letterNumberFormatBean.getRemarks().toString());
								dto.setForwardOrReplayOrFile(letterNumberFormatBean.getSavingType());
								message = commonDAO.save(dto);
								}
								else
								{
									dto.setSfid(letterNumberFormatBean.getSfid());
									dto.setRefOrgRoleId(letterNumberFormatBean.getRefOrgRoleId());
									dto.setForwardOrReplayOrFile(subJson1.get("savingType").toString());
									message = commonDAO.save(dto);
									//message = letterNumberFormatDAO.updateIonCirculationDetails(dto);
								}}
					}else{
						IonCirculatedDetailsDTO dto = new IonCirculatedDetailsDTO();
						BeanUtils.copyProperties(dto, letterNumberFormatBean);
						dto.setForwardOrReplayOrFile(subJson1.get("savingType").toString());
						dto.setSfid(letterNumberFormatBean.getSfid());	
						dto.setRefOrgRoleId(letterNumberFormatBean.getRefOrgRoleId());
						dto.setIonId(Integer.valueOf(subJson1.get("ionId").toString()));
						dto.setRemarks(letterNumberFormatBean.getRemarks());
						message = letterNumberFormatDAO.updateIonCirculationDetails(dto);
					
					}
					}
						
					}
				
			} catch (Exception e) {
				throw e;
			}
			return message;
		
	}
	@SuppressWarnings("unchecked")
	public List<OrgInstanceDTO> getUserOrgRoleList(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List<OrgInstanceDTO> list = null;
		try
		{
			list = letterNumberFormatDAO.getUserOrgRoleList(letterNumberFormatBean,"user");
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
		
	}
	public List<OrgInstanceDTO> getIONInitiatedRoleList(LetterNumberFormatBean letterNumberFormatBean,String type) throws Exception{
		List<OrgInstanceDTO> list = null;
		try
		{
			list = letterNumberFormatDAO.getUserOrgRoleList(letterNumberFormatBean,type);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getOrganizations(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getOrganizations(letterNumberFormatBean); 
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String uploadEncloserFile(LetterNumberFormatBean letterNumberFormatBean, FileUploadBean fub) throws Exception{
		String message = null;
		try
		{
			IonFileDTO dto = new IonFileDTO();
			dto.setIonId(Integer.parseInt(letterNumberFormatBean.getId()));
			dto.setFileId(fub.getFileIds().get("ionFile#"+letterNumberFormatBean.getEnclosureFileName()[0]));
			message = commonDAO.save(dto);
			
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
	}
	public List getIONFileList(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getIONFileList(letterNumberFormatBean); 
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String removeIONEncloserFile(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		String message = null;
		try
		{
			message = letterNumberFormatDAO.deleteIONDeptDetails(letterNumberFormatBean.getId(),"IonFileDTO",letterNumberFormatBean.getFileId(),"");
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
	}
	public void checkHRDGIon(LetterNumberFormatBean letterNumberFormatBean, FileUploadBean fub) throws Exception{
		try
		{
			letterNumberFormatDAO.checkHRDGIon(letterNumberFormatBean,fub);
		}
		catch(Exception e)
		{
			throw e;
		}
	
	}
	public List getIONListToCirculate(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getIONListToCirculate(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public List getFileTypeList()throws Exception
	{
		List list = null;
		try
		{
			list = letterNumberFormatDAO.getFileTypeList();
		}
		catch(Exception e)
		{
			throw e;
		}
		return list;
	}
	public String manageCirculation(LetterNumberFormatBean letterNumberFormatBean)throws Exception
	{
		String message = null;
		try
		{
			message = letterNumberFormatDAO.manageCirculation(letterNumberFormatBean);
		}
		catch(Exception e)
		{
			throw e;
		}
		return message;
	}
	//code for delete
	public String deleteLetterNumberFormatDetails(int id) throws Exception{
		String message = null;
		try{
			message = letterNumberFormatDAO.deleteLetterNumberFormatDetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<IONDTO> getIONDisplayDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception{
		List<IONDTO> displayIonDetails =null;
		try{
			displayIonDetails = letterNumberFormatDAO.getIONDisplayDetails(letterNumberFormatBean);
		}catch (Exception e) {
			throw e;
		}
		return displayIonDetails;
	}


}
