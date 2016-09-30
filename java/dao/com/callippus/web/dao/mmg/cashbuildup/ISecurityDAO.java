package com.callippus.web.dao.mmg.cashbuildup;

import java.util.List;

import com.callippus.web.mmg.cashbuildup.dto.SecurityDetailsDTO;

public interface ISecurityDAO {
	public List<SecurityDetailsDTO> getDemandNumber() throws Exception;

	public List<Object> getSecurityItemDetails(String demandNo) throws Exception;

	public String saveSecurityCheckDetails(SecurityDetailsDTO security) throws Exception;

	public String updateStockDetails(String demandNo, String materialCode, String qty, int id) throws Exception;
}
