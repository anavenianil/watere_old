package com.callippus.web.retriments.dao;

import java.util.List;

import com.callippus.web.retriments.beans.RetrimentBean;
import com.callippus.web.retriments.dto.RetrimentDTO;

public interface IRetrimentsDAO {
	
	

	public RetrimentBean submitRetrimentDetails(RetrimentBean retrimentBean) throws Exception;

	public List<RetrimentDTO> getRetrimentBenfitDetails(RetrimentBean retrimentBean)throws Exception;

	public String deleteRetrimentDetails(RetrimentBean retrimentBean)throws Exception;

	public String chageEmployeeDetails(String changeSfid)throws Exception;

	public RetrimentDTO getEmpDetails(Integer id)throws Exception;

	public RetrimentBean submitRetrimentPayDetails(RetrimentBean retrimentBean)throws Exception;
	

}
