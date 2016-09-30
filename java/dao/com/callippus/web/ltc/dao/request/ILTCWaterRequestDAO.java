package com.callippus.web.ltc.dao.request;

import com.callippus.web.ltc.beans.request.LTCWaterRequestBean;

public interface ILTCWaterRequestDAO {
	
public LTCWaterRequestBean getEmpDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;

public LTCWaterRequestBean getEmpDetailsOne(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;
	
	public LTCWaterRequestBean getDeptDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;
	
	public LTCWaterRequestBean getDeptDetailsOne(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;
	
	public LTCWaterRequestBean getCurrentWaterReqIdDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;
	
	public LTCWaterRequestBean getCurrentWaterReqIdDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;
	
	public LTCWaterRequestBean getCurrentWaterSettlementDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;
	
	
	public LTCWaterRequestBean getCurrentWaterReimbursementDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception;
	
	public String getLtcYear(String ltcYear,String sfid) throws Exception;
	
	
	//public LTCWaterRequestBean saveTxnDeatils(String subJson,LTCWaterRequestBean ltcWaterRequestBean) throws Exception;

	
}
