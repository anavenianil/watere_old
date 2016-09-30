package com.callippus.web.tada.dao.request;

import com.callippus.web.tada.beans.request.TadaWaterRequestBean;


public interface ITadaWaterRequestDAO {
	
	public TadaWaterRequestBean getEmpDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;
	
	public TadaWaterRequestBean getEmpDetailsOne(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;
	
	public TadaWaterRequestBean getDeptDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;
	
	public TadaWaterRequestBean getDeptDetailsOne(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;
	
	public TadaWaterRequestBean getCurrentWaterReqIdDetails(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;
	
	public TadaWaterRequestBean getCurrentWaterReqIdDetail(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;
	
	
	public TadaWaterRequestBean getCurrentWaterSettlementDetail(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;
	
	public TadaWaterRequestBean getCurrentWaterReimbursementDetail(TadaWaterRequestBean tadaWaterRequestBean)throws Exception;


}
