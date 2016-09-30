package com.callippus.web.business.arrears;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.arrears.ArrearsBean;
import com.callippus.web.beans.arrears.ArrearsStatusDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.arrears.IArrearsDAO;
import com.callippus.web.dao.paybill.IPayBillMasterDAO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
@Service
public class ArrearsBusiness {
	
	@Autowired
	private IArrearsDAO iArrearsDAO;
	@Autowired
	private IPayBillMasterDAO iPayBillMasterDAO;
	public List<ArrearsStatusDTO> getAIArrearsStatusList()throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			keyList=iArrearsDAO.getAIArrearsStatusList();
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public List<ArrearsStatusDTO> getAIArrearsStatusList(String adminAccDate,String finAccDate)throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			keyList=iArrearsDAO.getAIArrearsDueDrawnDetails(adminAccDate.substring(3), finAccDate.substring(3),finAccDate);
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	
	public String submitAnnualIncrArrearsDetails(ArrearsBean arrearsBean) throws Exception{
		String message="";
		try{
			message=iArrearsDAO.submitAnnualIncrArrearsDetails(arrearsBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<ArrearsStatusDTO> getDaArrearsPreviewList(ArrearsBean arrearsBean)throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			keyList=iArrearsDAO.getDaArrearsPreviewList(arrearsBean);
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public List<ArrearsStatusDTO> getDaArrearsPreviewDetailedList(ArrearsBean arrearsBean)throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			keyList=iArrearsDAO.getDaArrearsPreviewDetailedList(arrearsBean);
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public String submitDAArrearsDetails(ArrearsBean arrearsBean) throws Exception{
		String message="";
		try{
			message=iArrearsDAO.submitDAArrearsDetails(arrearsBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<PayBillEmpCategoryDTO> getEmpPayCategoryList()throws Exception{
		List<PayBillEmpCategoryDTO> keyList=null;
		try{
			keyList=iPayBillMasterDAO.getEmpCategoryDetailsList();
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public ArrearsBean getFpaArrearsDetails(ArrearsBean arrearsBean)throws Exception{
		try{
			String empExists=iPayBillMasterDAO.getEmpExist(arrearsBean.getUserSfid());
			if(CPSUtils.compareStrings(CPSConstants.YES, empExists)){
			arrearsBean=iArrearsDAO.getFpaArrearsDetails(arrearsBean);
			}else{
				arrearsBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		}catch (Exception e) {
			throw e;
		}
		return arrearsBean;
	}
	public void submitFPAArrearsDetails(ArrearsBean arrearsBean)throws Exception{
		try{
			iArrearsDAO.submitFPAArrearsDetails(arrearsBean);
		}catch (Exception e) {
			throw e;
		}
	}
	public List<ArrearsStatusDTO> getPromotionArrearsDetails(ArrearsBean arrearsBean)throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			String empExists=iPayBillMasterDAO.getEmpExist(arrearsBean.getUserSfid());
			if(CPSUtils.compareStrings(CPSConstants.YES, empExists)){
			keyList=iArrearsDAO.getPromotionArrearsDetails(arrearsBean);
			}else{
				arrearsBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public List<ArrearsStatusDTO> getPromotionArrearsPreviewList(ArrearsBean arrearsBean)throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			keyList=iArrearsDAO.getPromotionArrearsPreviewList(arrearsBean);
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public List<ArrearsStatusDTO> submitPromotionArrearsPreviewList(ArrearsBean arrearsBean)throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			iArrearsDAO.submitPromotionArrearsDetails(arrearsBean);
			keyList=iArrearsDAO.getPromotionArrearsPreviewList(arrearsBean);
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public List<ArrearsStatusDTO> getFPAArrearsPrintDetails()throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			
			keyList=iArrearsDAO.getFPAArrearsPrintDetails();
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public List<ArrearsStatusDTO> getPromotionArrearsPrintDetails()throws Exception{
		List<ArrearsStatusDTO> keyList=null;
		try{
			
			keyList=iArrearsDAO.getPromotionArrearsPrintDetails();
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
}
