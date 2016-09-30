package com.callippus.web.dao.quarterType;

import java.util.List;

import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.paybill.dto.LicenceFeeChargesDTO;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;

public interface IQuarterTypeDAO {
	
	public String getCurrentRunMonth() throws Exception;
	
	public List<QuarterTypeBean> getQuarterList() throws Exception;

	public String submitQuarterTypeDetails(QuarterTypeBean quarterTypeBean) throws Exception;

	public List<LicenceFeeChargesDTO> getLicenceFeeList() throws Exception;

	public String submitLicenceFeeDetails(QuarterTypeBean quarterTypeBean) throws Exception;

	public List<QuarterTypeBean> getQuarterSubTypeList() throws Exception;
	
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeList() throws Exception ;
	
	public List<QuarterTypeBean> getPayQuarterSubTypeList(QuarterTypeBean quarterTypeBean) throws Exception;
}
