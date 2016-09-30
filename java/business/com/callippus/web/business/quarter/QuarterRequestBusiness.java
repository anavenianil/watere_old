package com.callippus.web.business.quarter;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.QuarterGradePayMappingDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.paybill.IPayBillMasterDAO;
import com.callippus.web.dao.quarter.IQuarterRequestDAO;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

@Service
public class QuarterRequestBusiness {

	@Autowired
	private IQuarterRequestDAO quarterRequestDAO;
	@Autowired
	private IPayBillMasterDAO payBillMasterDAO;

	public QuarterRequestBean getQuarterRequestHomeDetails(QuarterRequestBean quarterBean) throws Exception {
		try {
			quarterBean = quarterRequestDAO.getEmployeeDetails(quarterBean);
			if(!CPSUtils.compareStrings(quarterBean.getParam(), "quarterOfflineDetailsEntry")) {
				if(!CPSUtils.isNull(quarterBean.getEmployeeDetails())) {
					quarterBean = quarterRequestDAO.getEmployeePaymentDetails(quarterBean);
					quarterBean = quarterRequestDAO.getQuarterTypeList(quarterBean);
				}else
					quarterBean.setResult(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}
	
	public QuarterRequestBean getQuarterSubTypeList(QuarterRequestBean quarterBean) throws Exception {
		try {
			quarterBean = quarterRequestDAO.getQuarterSubTypeDetails(quarterBean);
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}
	
	public List<QuarterRequestBean> getQuarterEmuDetailsList(String type)throws Exception {
		List<QuarterRequestBean> list = null;
		try {
			list = quarterRequestDAO.getQuarterEmuDetailsList(type); 
			if(CPSUtils.compareStrings(type, "occupied") || CPSUtils.compareStrings(type, "vacated")) {
				for(QuarterRequestBean bean:list) {
					bean.setAllotedDt(CPSUtils.formattedDate(bean.getAllotedDt()));
					bean.setOccupiedDt(CPSUtils.formattedDate(bean.getOccupiedDt()));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	public String updateQuarterDetailsWithEMU(QuarterRequestBean quarterBean)throws Exception{
		String message = "";
		try {
			if(CPSUtils.compareStrings("alloted",quarterBean.getType())){
				JSONObject mainJson = new JSONObject(quarterBean.getJsonValues());
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					quarterBean.setQuarterType(subJson.get("quarterType").toString());
					quarterBean.setRequestID(subJson.get("requestID").toString());
					quarterBean.setSfID(subJson.get("sfID").toString());
					quarterBean.setQuarterNo(subJson.get("quarterNo").toString());
					quarterBean.setAllotedDt(subJson.get("allotedDt").toString());
					quarterBean.setAllotedId(quarterBean.getAllotedId());
					message=quarterRequestDAO.updateQuarterDetailsWithEMU(quarterBean);
				}
			}
			else if(CPSUtils.compareStrings("occupied",quarterBean.getType())){
				JSONObject mainJson = new JSONObject(quarterBean.getJsonValues());
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					quarterBean.setRequestID(subJson.get("requestID").toString());
					quarterBean.setSfID(subJson.get("sfID").toString());
					quarterBean.setOccupiedDt(subJson.get("occupiedDt").toString());
					quarterBean.setOccupiedId(quarterBean.getAllotedId());
					message=quarterRequestDAO.updateQuarterDetailsWithEMU(quarterBean);
				}
			}
			else if(CPSUtils.compareStrings("vacated",quarterBean.getType())){
				JSONObject mainJson = new JSONObject(quarterBean.getJsonValues());
				for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					quarterBean.setRequestID(subJson.get("requestID").toString());
					quarterBean.setSfID(subJson.get("sfID").toString());
					quarterBean.setVacatedDt(subJson.get("vacatedDt").toString());
					quarterBean.setVacatedId(quarterBean.getAllotedId());
					message=quarterRequestDAO.updateQuarterDetailsWithEMU(quarterBean);
				}
			}
			
			
		     return	message;
		} catch (Exception e) {
			message=CPSConstants.FAILED;
			throw e;
		}
	}
	
	public List<QuarterRequestBean> getEMUSendingSFIDList()throws Exception{
		List<QuarterRequestBean> list=null;
		try {
			list=quarterRequestDAO.getEMUSendingSFID();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	public String saveEmuRequestDetails(QuarterRequestBean quarterBean)throws Exception{
		String message = "";
		try {
			
			JSONObject mainJson = new JSONObject(quarterBean.getJsonValues());
			for(int i=0;i<mainJson.length();i++) {
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				quarterBean.setRequestID(subJson.get("requestID").toString());
				quarterBean.setSfID(subJson.get("sfID").toString());
				message=quarterRequestDAO.saveEmuRequestDetails(quarterBean);
			}
		     return	message;
		} catch (Exception e) {
			throw e;
		}
	}
	
	//config screen starts
	public List<QuarterRequestBean> getUniqueGradePays()throws Exception{
		List<QuarterRequestBean> list=null;
		try {
			list=quarterRequestDAO.getUniqueGradePays();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeList()throws Exception{
		List<PayBillQuartersTypeMasterDTO> list=null;
		try {
			list=quarterRequestDAO.getQuarterTypeList();
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeDetails(QuarterRequestBean bean)throws Exception{
		List<PayBillQuartersTypeMasterDTO> list=null;
		try {
			list=quarterRequestDAO.getQuarterTypeDetails(bean);
		} catch (Exception e) {
			throw e;
		} 
		return list;
	}
	
	public String saveQuarterOfflineDetails(QuarterRequestBean quarterBean) throws Exception {
		String message = null;
		PayQuarterManagementDTO bean = null;
		List<PayQuarterManagementDTO> record = null;
		boolean flag = false;
		try
		{
			bean = new PayQuarterManagementDTO();
			if(!CPSUtils.isNullOrEmpty(quarterBean.getParentID())) {
				bean.setId(Integer.parseInt(quarterBean.getParentID()));
				message = CPSConstants.UPDATE;
			} else {
				bean.setCreatedBy(quarterBean.getSfID1());
				bean.setCreationTime(CPSUtils.getCurrentDateWithTime());
				message = CPSConstants.SUCCESS;
			}
			bean.setSfid(quarterBean.getSfID1().toUpperCase());
			bean.setQuarterNo(quarterBean.getQuarterNo());
			bean.setQuartersType(quarterBean.getQuarterSubTypeId());
			bean.setStatus(1);
			bean.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			bean.setLastModifiedBy(quarterBean.getSfID1());
			bean.setOccupiedDate(quarterBean.getOccupiedDate());
			bean.setVacationDate(quarterBean.getVacationDate());
			/*if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {	// New functionality added by Naresh
				record = quarterRequestDAO.getQuarterOfflineDetailsList(quarterBean.getSfID1());
				if(CPSUtils.isNull(record) || record.size() == 0)
					flag = true;
				else {
					if(CPSUtils.isNull(record.get(0).getVacationDate()))
						message = CPSConstants.VACATIONDATEMIS;
					else {
						if(CPSUtils.compareTwoDatesUptoDate(bean.getOccupiedDate(), record.get(0).getVacationDate()) == 1 )
							flag = true;
						else
							message = CPSConstants.INVALIDVACATIONDATE;
					}
				}
			}	// End
			if(CPSUtils.compareStrings(CPSConstants.UPDATE, message) || flag == true)*/
				message = quarterRequestDAO.saveQuarterOfflineDetails(bean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public String saveQuarterGradePays(QuarterRequestBean quarterBean) throws Exception {
		String message = null;
		try {
			message = quarterRequestDAO.saveQuarterGradePays(quarterBean);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List<QuarterGradePayMappingDTO> getMappedQuarterGrades(String quarterID) throws Exception {
		 List<QuarterGradePayMappingDTO> list=null;
		
		 try {
			 	list=quarterRequestDAO.getMappedQuarterGrades(quarterID);
			 return list;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public List<PayQuarterManagementDTO> getQuarterOfflineDetailsList(String sfid) throws Exception{
		List<PayQuarterManagementDTO> list=null;
		try {
			list=quarterRequestDAO.getQuarterOfflineDetailsList(sfid);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	public List<QuarterTypeBean> getQuarterSubTypeList() throws Exception{
		List<QuarterTypeBean> quarterSubTypeList=null;
		try{
			quarterSubTypeList=quarterRequestDAO.getQuarterSubTypeList();
		} catch (Exception e) {
			throw e;
		}
		return quarterSubTypeList;
	}
	public EmployeeBean getEmployeeDetails(String sfid)throws Exception{
		EmployeeBean employeeBean=null;
		try{
			employeeBean=quarterRequestDAO.getEmployeeDetails(sfid);
		}catch (Exception e) {
			throw e;
		}
		return employeeBean;
	}	
	public String deleteQuarterOfflineDetails(int id)throws Exception{
		String message=null;
		try{
			message=quarterRequestDAO.deleteQuarterOfflineDetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public QuarterRequestBean getAppliedQuarterType(QuarterRequestBean qrb)throws Exception{
		try {
			qrb = quarterRequestDAO.getAppliedQuarterType(qrb);
		} catch (Exception e) {
			throw e;
		}
		return qrb;
	}
	public List<IONDTO> getEMULetterNumbersList(QuarterRequestBean qrb)throws Exception{
		List<IONDTO> list=null;
		try {
			list = quarterRequestDAO.getEMULetterNumbersList(qrb);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	public String getEmployeeExistance(String sfid) throws Exception {
		String message = "";
		try {
				message = payBillMasterDAO.getEmpExist(sfid);
				if(CPSUtils.compareStrings(CPSConstants.NO, message))
					message = CPSConstants.EMPNOTEXISTS;
		} catch(Exception e) {
			throw e;
		}
		return message;
	}
}
