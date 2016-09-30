package com.callippus.web.dao.arrears;

import java.util.List;

import org.springframework.stereotype.Service;

import com.callippus.web.beans.arrears.ArrearsBean;
import com.callippus.web.beans.arrears.ArrearsStatusDTO;
@Service
public interface IArrearsDAO {
	public List<ArrearsStatusDTO> getAIArrearsStatusList()throws Exception;
	
	public List<ArrearsStatusDTO> getAIArrearsDueDrawnDetails(String adminEffDate,String financeEffDate,String finDtae) throws Exception;

	public String submitAnnualIncrArrearsDetails(ArrearsBean arrearsBean) throws Exception;
	
	public List<ArrearsStatusDTO> getDaArrearsPreviewList(ArrearsBean arrearsBean)throws Exception;
	
	public int getDaActualValue(String runMonth)throws Exception;
	
	public List<ArrearsStatusDTO> getDaArrearsPreviewDetailedList(ArrearsBean arrearsBean)throws Exception;
	
	public String submitDAArrearsDetails(ArrearsBean arrearsBean) throws Exception;
	
	public ArrearsBean getFpaArrearsDetails(ArrearsBean arrearsBean)throws Exception;
	
	public void submitFPAArrearsDetails(ArrearsBean arrearsBean)throws Exception;
	
	public List<ArrearsStatusDTO> getPromotionArrearsDetails(ArrearsBean arrearsBean)throws Exception;
	
	public List<ArrearsStatusDTO> getPromotionArrearsPreviewList(ArrearsBean arrearsBean)throws Exception;
	
	public void submitPromotionArrearsDetails(ArrearsBean arrearsBean)throws Exception;
	
	public List<ArrearsStatusDTO> getFPAArrearsPrintDetails()throws Exception;
	public List<ArrearsStatusDTO> getPromotionArrearsPrintDetails() throws Exception;
}
