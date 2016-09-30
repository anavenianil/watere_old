package com.callippus.web.dao.mmg.cashbuildup;

import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.MMGMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemCodeDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemSubCategoryDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemSubCodeDTO;
import com.callippus.web.mmg.cashbuildup.dto.ProjectCodeDTO;

public interface IMMGMasterDAO {
	public String checkMMGName(MMGMasterBean mmgmasterBean) throws Exception;

	public String createMMGMasterData(Object obj) throws Exception;

	public String updateMMGMasterData(MMGMasterBean mmgmasterBean) throws Exception;

	public String deleteMMGMasterData(MMGMasterBean mmgmasterBean) throws Exception;

	public List<ItemSubCategoryDTO> getItemSubCategory() throws Exception;

	public String deleteMMGCheckMaster(String beanName, String propertyName, String status, String propertyValue) throws Exception;

	public List<ItemCodeDTO> getItemCodeDetails() throws Exception;

	public List<ItemSubCodeDTO> getItemSubCodeDetails() throws Exception;

	public int getInventoryNum() throws Exception;

	public List<InventoryMasterDTO> getInventoryHolderDetails() throws Exception;

	public List<AccountHeadDTO> getAccountHeadDetails(MMGMasterBean mmgmasterBean) throws Exception;

	public List<MMGMasterBean> getMaterialDetails() throws Exception;

	public DemandMasterDTO getDemandDetails(String demandNo, String status) throws Exception;

	public List<DemandMasterDTO> getDemandNumbers(String status) throws Exception;

	public List<AccountHeadDTO> getAccountHeadNos(String sfid) throws Exception;

	public AccountHeadDTO checkingAccountHeadAmt(String inventoryNo) throws Exception;

	public String UpdateDemandStatus(String demandNo, String status) throws Exception;

	public List<ProjectCodeDTO> getProjectList() throws Exception;

	public String saveMMGConfigDetails(MMGMasterBean mmgmasterBean) throws Exception;

	public List<KeyValueDTO> getFinancialYearList() throws Exception;
}
