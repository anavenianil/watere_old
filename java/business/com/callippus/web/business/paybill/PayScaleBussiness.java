package com.callippus.web.business.paybill;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.paybill.PayScaleBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.paybill.IPayScaleDAO;
import com.callippus.web.paybill.dto.PayAllwDetailsDTO;
import com.callippus.web.paybill.dto.PayAllwTypeDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PaybandDTO;
import com.callippus.web.paybill.dto.TwpAddIncrPayScaleRangeDTO;

@Service
public class PayScaleBussiness {
	@Autowired
	private IPayScaleDAO payScaleDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public String getCurrentRunMonth() throws Exception
	{
		String runMonth = "";
			try
			{
				runMonth = payScaleDAO.getCurrentRunMonth();
				runMonth += " Not started!";
			}catch(Exception e)
			{
				throw e;
			}
		return runMonth;
	}
	
	public PayScaleBean getPayScaleList(PayScaleBean payScaleBean) throws Exception 
	{
		try
		{
			payScaleBean.setPayScaleList(payScaleDAO.getPayScaleList(payScaleBean));
		} catch (Exception e) {
			payScaleBean.setMessage(CPSConstants.FAILED);
			throw e;
		}
		return payScaleBean;
	}

	public String submitPayScaleDetails(PayScaleBean payScaleBean) throws Exception 
	{
		String message = null;
		try {
			if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.PROFESSIONALTAX)) {
				message = payScaleDAO.submitProfessionalTaxDetails(payScaleBean);
			} else if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.VARIABLEINCREMENT)) {
				message = payScaleDAO.submitVariableIncrementDetails(payScaleBean);
			} else if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.FAMILYPLANNING)) {
				message = payScaleDAO.submitFamilyPlanningDetails(payScaleBean);
			} else if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.TRAVELLINGALLOWANCE)) {
				message = payScaleDAO.submitTravellingAllowanceDetails(payScaleBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public PayScaleBean getDesignationPaybandList(PayScaleBean payScaleBean) throws Exception {
		try {

			payScaleBean.setDesignationList(commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO));
			payScaleBean.setPaybandList(payScaleDAO.getPayScaleList(payScaleBean));
			payScaleBean.setPayDesignationList(payScaleDAO.getPayDesignationList());
		} catch (Exception e) {
			throw e;
		}
		return payScaleBean;
	}

	public String submitPayScaleDesignationDetails(PayScaleBean payScaleBean) throws Exception {
		try {
			payScaleBean.setMessage(payScaleDAO.submitPayScaleDesignationDetails(payScaleBean));
		} catch (Exception e) {
			throw e;
		}
		return payScaleBean.getMessage();
	}

	public List<PaybandDTO> getPayBandList() throws Exception
	{
		List<PaybandDTO> paybandList = null;
		try
		{
			paybandList = payScaleDAO.getPayBandList();
		} catch (Exception e) {
			throw e;
		}
		return paybandList;
	}

	public PayScaleBean insertPayBandDetails(PayScaleBean payScaleBean) throws Exception 
	{
		String message = "";
		try 
		{
			message = payScaleDAO.insertPayBandDetails(payScaleBean);
			payScaleBean.setMessage(message);
			payScaleBean.setPayBandList(payScaleDAO.getPayBandList());
		} catch (Exception e) {
			throw e;
		}
		return payScaleBean;
	}

	public String deletePayBandDetails(PayScaleBean payScaleBean) throws Exception 
	{
		String message = "";
		try
		{
			PaybandDTO paybandDTO = new PaybandDTO();
			paybandDTO.setId(Integer.parseInt(payScaleBean.getPk()));
			paybandDTO.setLastModifiedBy(payScaleBean.getSfid());
			paybandDTO.setLastModifiedDate(payScaleBean.getLastModifiedDate());
			message = payScaleDAO.deletePayBandMasterDetails(paybandDTO);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<DesignationDTO> getPayScaleDesignationList() throws Exception 
	{
		List<DesignationDTO> keyList = null;
		try {
			keyList = payScaleDAO.getPayScaleDesignationList();
		} catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public String submitTWAIPayScaleDetails(PayScaleBean payScaleBean) throws Exception {
		String message = "";
		try {
			message = payScaleDAO.submitTWAIPayScaleDetails(payScaleBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<TwpAddIncrPayScaleRangeDTO> getTwoAddIncrPayScaleList() throws Exception
	{
		List<TwpAddIncrPayScaleRangeDTO> keyList = null;
		try{
			keyList = payScaleDAO.getTwoAddIncrPayScaleList();
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public String deleteTAIPayScaleDetails(int id, String sfid) throws Exception
	{
		String message = "";
		try
		{
			message = payScaleDAO.deleteTAIPayScaleDetails(id, sfid);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<PayAllwTypeDTO> getPayAllwTypeList() throws Exception{
		List<PayAllwTypeDTO> keyList=null;
		try{
			keyList = payScaleDAO.getPayAllwTypeList();
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	public String submitPayConfDetails(PayScaleBean payScaleBean) throws Exception{
		String message="";
		try{
			message=payScaleDAO.submitPayConfDetails(payScaleBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<PayAllwDetailsDTO> getPayAllwDetailsList() throws Exception{
		List<PayAllwDetailsDTO> keyList=null;
		try{
			keyList = payScaleDAO.getPayAllwDetailsList();
		}catch (Exception e) {
			throw e;
		}
		return keyList;
	}
	 public String deletePayAllwDetails(int id,String sfid) throws Exception{
		 String message="";
			try{
				message=payScaleDAO.deletePayAllwDetails(id, sfid);
			}catch (Exception e) {
				throw e;
			}
			return message;
	 }
	  public String submitpayAllwTypeDetails(PayScaleBean payScaleBean) throws Exception{
			String message="";
			try{
				message=payScaleDAO.submitpayAllwTypeDetails(payScaleBean);
			}catch (Exception e) {
				throw e;
			}
			return message;
		}
	  public String deletePayAllwType(int id,String sfid) throws Exception{
			 String message="";
				try{
					message=payScaleDAO.deletePayAllwType(id, sfid);
				}catch (Exception e) {
					throw e;
				}
				return message;
		 }
}
