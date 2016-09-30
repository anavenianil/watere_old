package com.callippus.web.business.quarter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.quarter.QuarterBean;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.quarter.IQuarterDAO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

@Service
public class QuarterBusiness {
	@Autowired
	private IQuarterDAO quarterDAO;

	public QuarterBean getQuarterAdminHomeDetails(QuarterBean quarterBean) throws Exception {
		try
		{
			quarterBean.setQuarterSubTypeList(quarterDAO.getQuarterSubTypeList());
			quarterBean.setQuarterDetails(quarterDAO.getQuarterDetailsList(quarterBean));
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}

	public QuarterBean manageQuarterDetails(QuarterBean quarterBean) throws Exception {
		PayQuarterManagementDTO quarterManagementDTO = new PayQuarterManagementDTO();
		try
		{
			if (!CPSUtils.isNullOrEmpty(quarterBean.getNodeID())) // Update
				quarterManagementDTO.setId(Integer.valueOf(quarterBean.getNodeID()));
			quarterManagementDTO.setCreatedBy(quarterBean.getLogInSfID());
			quarterManagementDTO.setCreationDate(CPSUtils.getCurrentDate());
			quarterManagementDTO.setLastModifiedBy(quarterBean.getLogInSfID());
			quarterManagementDTO.setLastModifiedDate(quarterManagementDTO.getCreationDate());
			quarterManagementDTO.setStatus(1);
			quarterManagementDTO.setSfid(quarterBean.getSfid());
			quarterManagementDTO.setOccupiedDate(quarterBean.getOccupiedDate());
			quarterManagementDTO.setQuarterNo(quarterBean.getQuarterNo());
			quarterManagementDTO.setQuartersType(quarterBean.getQuarterSubType());
			quarterBean.setResult(quarterDAO.submitQuarterDetails(quarterManagementDTO));
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}

	public List<PayQuarterManagementDTO> getQuarterDetails(QuarterBean quarterBean) throws Exception {
		try
		{
			quarterBean.setQuarterDetails(quarterDAO.getQuarterDetailsList(quarterBean));
		} catch (Exception e) {
			throw e;
		}
		return quarterBean.getQuarterDetails();
	}

	public QuarterBean deleteQuarterDetails(QuarterBean quarterBean) throws Exception {
		try
		{
			quarterBean.setResult(quarterDAO.deleteQuarterDetails(quarterBean));
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}
	
	public QuarterBean getUserQuarterDetails(QuarterBean quarterBean) throws Exception {
		try
		{
			quarterBean = quarterDAO.getUserQuarterDetails(quarterBean);
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}

	public QuarterBean getQuarterEmuHomeDetails(QuarterBean quarterBean) throws Exception {
		quarterBean.setEmuQuarterDetails(quarterDAO.getQuarterEmuDetailsList(quarterBean));
		return quarterBean;
	}
}