package com.callippus.web.dao.quarter;

import java.util.List;

import com.callippus.web.beans.quarter.QuarterBean;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

public interface IQuarterDAO {
	
	public List<QuarterTypeBean> getQuarterSubTypeList() throws Exception;

	public List<PayQuarterManagementDTO> getQuarterDetailsList(QuarterBean quarterBean) throws Exception;

	public String submitQuarterDetails(PayQuarterManagementDTO quarterManagementDTO) throws Exception;

	public String deleteQuarterDetails(QuarterBean quarterBean) throws Exception;
	
	public QuarterBean getUserQuarterDetails(QuarterBean quarterBean) throws Exception;

	public List<QuarterRequestBean> getQuarterEmuDetailsList(QuarterBean quarterBean)throws Exception;
	
	

}
