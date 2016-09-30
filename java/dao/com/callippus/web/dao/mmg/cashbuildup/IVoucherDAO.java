package com.callippus.web.dao.mmg.cashbuildup;

import java.util.List;

import com.callippus.web.mmg.cashbuildup.beans.IRMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.VoucherTypeDTO;

public interface IVoucherDAO {
	public List<VoucherTypeDTO> getVoucherTypes() throws Exception;

	public String getVoucherId(String beanName, String fieldName) throws Exception;

	public List<InventoryMasterDTO> getToInventoryNumsList() throws Exception;

	public IRMasterBean getSecurityDetails(String demandNo, String status) throws Exception;

	public List<IRMasterBean> getInvoiceNumbers(String status) throws Exception;
}
