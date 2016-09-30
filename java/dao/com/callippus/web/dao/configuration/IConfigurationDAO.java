package com.callippus.web.dao.configuration;

import java.util.List;

import com.callippus.web.beans.configuration.ConfigurationBean;
import com.callippus.web.beans.configuration.SigningAuthorityDTO;
import com.callippus.web.beans.dto.KeyValueDTO;

public interface IConfigurationDAO {

	public ConfigurationBean getConfigurationDetails() throws Exception;

	public String submitConfigurationDetails(String configurationDetails) throws Exception;

	public List<KeyValueDTO> getExceptionalList(String id) throws Exception;
	
	public String submitSigningAuthorityDetails(ConfigurationBean configurationBean)throws Exception;
	
	public List<SigningAuthorityDTO> getSigningAuthorityList()throws Exception;
	
	public String deleteSigningAuthorityDetails(int id)throws Exception;

}
