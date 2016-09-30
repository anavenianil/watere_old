package com.callippus.web.business.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.configuration.ConfigurationBean;
import com.callippus.web.beans.configuration.SigningAuthorityDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.configuration.IConfigurationDAO;

@Service
public class ConfigurationBusiness {
	@Autowired
	private IConfigurationDAO configurationDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public ConfigurationBean getConfigurationDetails() throws Exception {
		ConfigurationBean configurationBean = null;
		try {
			configurationBean = new ConfigurationBean();
			configurationBean = configurationDAO.getConfigurationDetails();
			configurationBean.setInstanceList(commonDataDAO.getMasterData(CPSConstants.ORGINSTANCEDTO));
			configurationBean.setCmlWoMcExceptionIdList(configurationDAO.getExceptionalList("4"));
			configurationBean.setLndWoMcExceptionIdList(configurationDAO.getExceptionalList("5"));
		} catch (Exception e) {
			throw e;
		}
		return configurationBean;
	}

	public String submitConfigurationDetails(String configurationDetails) throws Exception {
		String message = "";
		try {
			message = configurationDAO.submitConfigurationDetails(configurationDetails);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String submitSigningAuthorityDetails(ConfigurationBean configurationBean)throws Exception{
		String message ="";
		try{
			message=configurationDAO.submitSigningAuthorityDetails(configurationBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<SigningAuthorityDTO> getSigningAuthorityDetails() throws Exception{
		List<SigningAuthorityDTO> authorityDetails = new ArrayList<SigningAuthorityDTO>();
		try{
			authorityDetails=configurationDAO.getSigningAuthorityList();
		}catch (Exception e) {
			throw e;
		}
		return authorityDetails;
	}
	public String deleteSigningAuthorityDetails(int id) throws Exception{
		String message="";
		try{
			message=configurationDAO.deleteSigningAuthorityDetails(id);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String setConfigurationValue(String name,String value)throws Exception{
		String message="fail";
		message=commonDataDAO.setConfigurationValue(name, value);
		return message;
	}
}
