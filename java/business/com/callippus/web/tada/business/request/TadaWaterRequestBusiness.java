package com.callippus.web.tada.business.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.beans.request.TadaWaterRequestBean;
import com.callippus.web.tada.dao.request.ITadaWaterRequestDAO;


@Service
public class TadaWaterRequestBusiness {
	
	@Autowired
	ITadaWaterRequestDAO tadaWaterRequestDAO;
	@Autowired
	private IComonObjectDAO commonObjectDAO;


	public TadaWaterRequestBean getEmpDetails(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getEmpDetails(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	
	public TadaWaterRequestBean getEmpDetailsOne(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getEmpDetailsOne(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	
	
	public TadaWaterRequestBean getDeptDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getDeptDetails(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	public TadaWaterRequestBean getDeptDetailsOne(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getDeptDetailsOne(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	public TadaWaterRequestBean getCurrentWaterReqIdDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getCurrentWaterReqIdDetails(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	public TadaWaterRequestBean getCurrentWaterReqIdDetail(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getCurrentWaterReqIdDetail(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	public TadaWaterRequestBean getCurrentWaterSettlementDetail(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getCurrentWaterSettlementDetail(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	public TadaWaterRequestBean getCurrentWaterReimbursementDetail(TadaWaterRequestBean tadaWaterRequestBean)throws Exception{
		try{
			tadaWaterRequestBean=tadaWaterRequestDAO.getCurrentWaterReimbursementDetail(tadaWaterRequestBean);
			
		}catch(Exception e){
			throw e;
		}
		return tadaWaterRequestBean;
	}
	
	
	@SuppressWarnings("unchecked")
	public TadaWaterRequestBean getBankNameDetails(TadaWaterRequestBean tadaWaterRequestBean) throws Exception {
		try {
			tadaWaterRequestBean.setBankNamesList(commonObjectDAO.getMasterData(CPSConstants.BANKNAMESDTO));
		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

}
