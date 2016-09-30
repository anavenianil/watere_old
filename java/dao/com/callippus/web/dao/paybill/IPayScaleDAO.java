package com.callippus.web.dao.paybill;

import java.util.List;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.paybill.PayScaleBean;
import com.callippus.web.paybill.dto.PayAllwDetailsDTO;
import com.callippus.web.paybill.dto.PayAllwTypeDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.paybill.dto.PaybandDTO;
import com.callippus.web.paybill.dto.TwpAddIncrPayScaleRangeDTO;

public interface IPayScaleDAO {

	public String getCurrentRunMonth() throws Exception;
	
	public String submitProfessionalTaxDetails(PayScaleBean PayScaleBean) throws Exception;

	public String submitVariableIncrementDetails(PayScaleBean payScaleBean) throws Exception;

	public String submitFamilyPlanningDetails(PayScaleBean payScaleBean) throws Exception;

	public String submitTravellingAllowanceDetails(PayScaleBean payScaleBean) throws Exception;

	public List<PayScaleBean> getPayScaleList(PayScaleBean payScaleBean) throws Exception;
	
	public List<PayScaleDesignationDTO> getPayDesignationList() throws Exception;

	public String submitPayScaleDesignationDetails(PayScaleBean payScaleBean) throws Exception;

	public List<PaybandDTO> getPayBandList() throws Exception;

	public String insertPayBandDetails(PayScaleBean payScaleBean) throws Exception;

	public String deletePayBandMasterDetails(PaybandDTO paybandDTO) throws Exception;

    public List<DesignationDTO> getPayScaleDesignationList() throws Exception;
    
    public String submitTWAIPayScaleDetails(PayScaleBean payScaleBean) throws Exception ;
    
    public List<TwpAddIncrPayScaleRangeDTO> getTwoAddIncrPayScaleList() throws Exception;
    
    public String deleteTAIPayScaleDetails(int id,String sfid) throws Exception;
    
    public List<PayAllwTypeDTO> getPayAllwTypeList() throws Exception;
    
    public String submitPayConfDetails(PayScaleBean payScaleBean) throws Exception;
    
    public List<PayAllwDetailsDTO> getPayAllwDetailsList() throws Exception;
    
    public String deletePayAllwDetails(int id,String sfid) throws Exception;
    
    public String deletePayAllwType(int id,String sfid) throws Exception;
    
    public String submitpayAllwTypeDetails(PayScaleBean payScaleBean) throws Exception;

}