package com.callippus.web.dao.quarter;

import java.util.List;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

public interface IQuarterRequestDAO {
	public QuarterRequestBean getEmployeeDetails(QuarterRequestBean quarterBean) throws Exception;

	public QuarterRequestBean getEmployeePaymentDetails(QuarterRequestBean quarterBean) throws Exception;

	public List<QuarterTypeBean> getQuarterSubTypeList() throws Exception;

	public QuarterRequestBean getQuarterTypeList(QuarterRequestBean quarterBean)throws Exception;
	
	public List<QuarterRequestBean> getQuarterEmuDetailsList(String type)throws Exception;
	
	public QuarterRequestBean getQuarterSubTypeDetails(QuarterRequestBean quarterBean)throws Exception;
	
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeDetails(QuarterRequestBean quarterBean)throws Exception;
	
	public String saveQuarterOfflineDetails(PayQuarterManagementDTO bean)throws Exception;
	
	public String updateQuarterDetailsWithEMU(QuarterRequestBean quarterBean)throws Exception;
	
	public List<QuarterRequestBean> getEMUSendingSFID()throws Exception;
	
	public String saveEmuRequestDetails(QuarterRequestBean quarterBean)throws Exception;
	
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeList()throws Exception;
	
	public List<QuarterRequestBean> getUniqueGradePays()throws Exception;
	
	public String saveQuarterGradePays(QuarterRequestBean bean)throws Exception;
	
	public List getMappedQuarterGrades(String quarterID) throws Exception;
	
	public QuarterRequestBean getOfflineEntry(QuarterRequestBean quarterRequestBean) throws Exception;
	
	public List<QuarterRequestBean> getQuarterDetailsList(QuarterRequestBean quarterRequestBean) throws Exception;
	
	public List<PayQuarterManagementDTO> getQuarterOfflineDetailsList(String sfid) throws Exception;
	
	public EmployeeBean getEmployeeDetails(String sfid)throws Exception;
	
	public String deleteQuarterOfflineDetails(int id)throws Exception;
	
	public QuarterRequestBean getAppliedQuarterType(QuarterRequestBean qrb)throws Exception;
	
	public List<IONDTO> getEMULetterNumbersList(QuarterRequestBean qrb)throws Exception;
	
}
