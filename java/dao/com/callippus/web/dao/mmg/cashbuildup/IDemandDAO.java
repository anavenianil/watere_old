package com.callippus.web.dao.mmg.cashbuildup;

import java.util.List;

import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandTypeDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;

public interface IDemandDAO {
	public DemandMasterDTO getDemandDetails(String demandNo) throws Exception;

	public String saveDemand(Object demand) throws Exception;

	public List<DemandTypeDTO> getDemandTypes() throws Exception;

	public int checkInvHolderItem(String inventoryNo, String materialCode) throws Exception;

	public List<DemandMasterDTO> getDraftDetails(String status, String sfid) throws Exception;

	public List<DemandMasterDTO> getDemandNumber() throws Exception;

	public String updateCommittedFund(String amount, String accountHead, String flag) throws Exception;

	public AccountHeadDTO checkingAccountHeadAmt(String accId) throws Exception;

	public InventoryMasterDTO getInventoryHolder(String invId) throws Exception;

	public String deleteDemandItems(String demandNo) throws Exception;

	public String updateDemandAmount(String demandNo, String amount) throws Exception;

	public String deleteDemand(String demandNo) throws Exception;

	public String getDemandLimit() throws Exception;
}
