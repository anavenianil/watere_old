package com.callippus.web.business.quarterType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.quarterType.IQuarterTypeDAO;

@Service
public class QuarterTypeBusiness {
	@Autowired
	private IQuarterTypeDAO quarterTypeDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public String getCurrentRunMonth() throws Exception
	{
		String runMonth = "";
			try
			{
				runMonth = quarterTypeDAO.getCurrentRunMonth();
				runMonth += " Not started!";
			}catch(Exception e)
			{
				throw e;
			}
		return runMonth;
	}
	@SuppressWarnings("unchecked")
	public QuarterTypeBean getQuarterList(QuarterTypeBean quarterTypeBean) throws Exception
	{
		try
		{
			quarterTypeBean.setQuarterTypeList(commonDataDAO.getMasterData(CPSConstants.PAYBILLQUARTERSTYPEMASTERDTO));
			quarterTypeBean.setQuarterList(quarterTypeDAO.getQuarterList());
		} catch (Exception e) {
			throw e;
		}
		return quarterTypeBean;
	}

	public String submitQuarterTypeDetails(QuarterTypeBean quarterTypeBean) throws Exception 
	{
		try
		{
			quarterTypeBean.setMessage(quarterTypeDAO.submitQuarterTypeDetails(quarterTypeBean));
		} catch (Exception e) {
			throw e;
		}
		return quarterTypeBean.getMessage();
	}

	public QuarterTypeBean getLicenceFeeList(QuarterTypeBean quarterTypeBean) throws Exception
	{
		try
		{
			quarterTypeBean.setQuarterTypeMasterList(quarterTypeDAO.getQuarterTypeList());
			quarterTypeBean.setQuarterSubTypeList(quarterTypeDAO.getQuarterSubTypeList());
			quarterTypeBean.setLicenceFeeList(quarterTypeDAO.getLicenceFeeList());
		} catch (Exception e) {
			throw e;
		}
		return quarterTypeBean;
	}

	public String submitLicenceFeeDetails(QuarterTypeBean quarterTypeBean) throws Exception
	{
		try
		{
			quarterTypeBean.setMessage(quarterTypeDAO.submitLicenceFeeDetails(quarterTypeBean));
		} catch (Exception e) {
			throw e;
		}
		return quarterTypeBean.getMessage();
	}
	public QuarterTypeBean getQuarterSubTypeList(QuarterTypeBean quarterTypeBean) throws Exception
	{
		try
		{
			quarterTypeBean.setLicenceFeeList(quarterTypeDAO.getLicenceFeeList());
			quarterTypeBean.setQuarterSubTypeList(quarterTypeDAO.getPayQuarterSubTypeList(quarterTypeBean));
		} catch (Exception e) {
			throw e;
		}
		return quarterTypeBean;
	}
}