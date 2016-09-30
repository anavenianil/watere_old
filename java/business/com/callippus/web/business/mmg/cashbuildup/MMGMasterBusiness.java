package com.callippus.web.business.mmg.cashbuildup;

import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.mmg.cashbuildup.IMMGMasterDAO;
import com.callippus.web.mmg.cashbuildup.beans.MMGMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.CompanyMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemCategoryDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemCodeDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemSubCategoryDTO;
import com.callippus.web.mmg.cashbuildup.dto.ItemSubCodeDTO;
import com.callippus.web.mmg.cashbuildup.dto.MaterialMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.ProjectCodeDTO;
import com.callippus.web.mmg.cashbuildup.dto.TaxTypeDTO;
import com.callippus.web.mmg.cashbuildup.dto.UomDTO;
import com.callippus.web.mmg.cashbuildup.dto.VoucherTypeDTO;

@Service
public class MMGMasterBusiness {
	private static Log log = LogFactory.getLog(MMGMasterBusiness.class);
	@Autowired
	private IMMGMasterDAO mmgMasterDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public String manageMMGMasterData(MMGMasterBean mmgmasterBean) throws Exception {
		String message = null;
		try {
			if (CPSUtils.isNullOrEmpty(mmgmasterBean.getId())) {
				mmgmasterBean.setBenaName(getMMGBeanName(mmgmasterBean.getType()));
				String tableName = getMMGTableName(mmgmasterBean.getType());
				mmgmasterBean.setTableName(tableName);

				message = mmgMasterDAO.checkMMGName(mmgmasterBean);
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO)) {
						mmgmasterBean.setCategoryCode(mmgmasterBean.getCategoryCode().split("#")[0]);
						mmgmasterBean.setSubCategoryCode(mmgmasterBean.getSubCategoryCode().split("#")[0]);
						mmgmasterBean.setItemCode(mmgmasterBean.getItemCode().split("#")[0]);
						mmgmasterBean.setItemSubCode(mmgmasterBean.getItemSubCode().split("#")[0]);
						mmgmasterBean.setCompanyCode(mmgmasterBean.getCompanyCode().split("#")[0]);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO)) {
						mmgmasterBean.setInvId(String.valueOf((commonDataDAO.getTableID(tableName, CPSConstants.UPDATE))));
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER)) {
						mmgmasterBean.setAccId(String.valueOf((commonDataDAO.getTableID(tableName, CPSConstants.UPDATE))));
					} else
						mmgmasterBean.setId(String.valueOf((commonDataDAO.getTableID(tableName, CPSConstants.UPDATE))));
					mmgmasterBean.setCreationDate(CPSUtils.getCurrentDate());
					mmgmasterBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					mmgmasterBean.setStatus(1);

					if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.TAXTYPEDTO)) {
						TaxTypeDTO taxType = new TaxTypeDTO();
						BeanUtils.copyProperties(taxType, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(taxType);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.UOMDTO)) {
						UomDTO uomDto = new UomDTO();
						BeanUtils.copyProperties(uomDto, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(uomDto);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.VOUCHERTYPEDTO)) {
						VoucherTypeDTO vouchType = new VoucherTypeDTO();
						BeanUtils.copyProperties(vouchType, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(vouchType);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.COMPANYMASTERDTO)) {
						CompanyMasterDTO company = new CompanyMasterDTO();
						BeanUtils.copyProperties(company, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(company);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCATEGORYDTO)) {
						ItemCategoryDTO itemCategory = new ItemCategoryDTO();
						BeanUtils.copyProperties(itemCategory, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(itemCategory);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCATEGORYDTO)) {
						ItemSubCategoryDTO itemSubCategory = new ItemSubCategoryDTO();
						BeanUtils.copyProperties(itemSubCategory, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(itemSubCategory);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMCODEDTO)) {
						ItemCodeDTO itemCode = new ItemCodeDTO();
						BeanUtils.copyProperties(itemCode, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(itemCode);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ITEMSUBCODEDTO)) {
						ItemSubCodeDTO itemSubCode = new ItemSubCodeDTO();
						BeanUtils.copyProperties(itemSubCode, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(itemSubCode);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO)) {
						MaterialMasterDTO materialMaster = new MaterialMasterDTO();
						BeanUtils.copyProperties(materialMaster, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(materialMaster);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO)) {
						InventoryMasterDTO inventoryMaster = new InventoryMasterDTO();
						mmgmasterBean.setSfid(mmgmasterBean.getSfid().split(",")[0]);
						if (!CPSUtils.isNullOrEmpty(mmgmasterBean.getProjectName())) {
							mmgmasterBean.setInventoryNo(mmgmasterBean.getInventoryNo() + "P");
						}
						BeanUtils.copyProperties(inventoryMaster, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(inventoryMaster);
					} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER)) {
						AccountHeadDTO accHead = new AccountHeadDTO();
						BeanUtils.copyProperties(accHead, mmgmasterBean);
						message = mmgMasterDAO.createMMGMasterData(accHead);
					}

				}
			} else {

				mmgmasterBean.setTableName(getMMGTableName(mmgmasterBean.getType()));
				mmgmasterBean.setBenaName(getMMGBeanName(mmgmasterBean.getType()));
				if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO)) {
					mmgmasterBean.setMaterialCode(mmgmasterBean.getId());
					mmgmasterBean.setCategoryCode(mmgmasterBean.getCategoryCode().split("#")[0]);
					mmgmasterBean.setSubCategoryCode(mmgmasterBean.getSubCategoryCode().split("#")[0]);
					mmgmasterBean.setItemCode(mmgmasterBean.getItemCode().split("#")[0]);
					mmgmasterBean.setItemSubCode(mmgmasterBean.getItemSubCode().split("#")[0]);
					mmgmasterBean.setCompanyCode(mmgmasterBean.getCompanyCode().split("#")[0]);
				} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO)) {
					mmgmasterBean.setInvId(mmgmasterBean.getId());
				} else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER)) {
					mmgmasterBean.setAccId(mmgmasterBean.getId());
				}
				message = mmgMasterDAO.checkMMGName(mmgmasterBean);
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
					message = mmgMasterDAO.updateMMGMasterData(mmgmasterBean);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deleteMMGMasterData(MMGMasterBean mmgmasterBean) throws Exception {
		String message = null;
		boolean status = true;
		try {
			mmgmasterBean.setTableName(getMMGTableName(mmgmasterBean.getType()));
			mmgmasterBean.setBenaName(getMMGBeanName(mmgmasterBean.getType()));
			if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.MATERIALMASTERDTO))
				mmgmasterBean.setMaterialCode(mmgmasterBean.getId());
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.INVENTORYMASTERDTO))
				mmgmasterBean.setInvId(mmgmasterBean.getId());
			else if (CPSUtils.compareStrings(mmgmasterBean.getBenaName(), CPSConstants.ACCOUNTHEADMASTER))
				mmgmasterBean.setAccId(mmgmasterBean.getId());
			message = getMMGTableDetails(mmgmasterBean.getType());

			if (!CPSUtils.isNullOrEmpty(message)) {
				String[] record = message.split(",");
				for (int i = 0; i <= record.length - 1; i++) {
					message = mmgMasterDAO.deleteMMGCheckMaster(record[i].split("#")[0], record[i].split("#")[1], record[i].split("#")[2], mmgmasterBean.getId());
					if (CPSUtils.compareStrings(message, CPSConstants.FAILED)) {
						status = false;
						break;
					}
				}
			}
			if (status) {
				message = mmgMasterDAO.deleteMMGMasterData(mmgmasterBean);
			} else {
				message = CPSConstants.DELETEFAIL;
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public MMGMasterBean getMMGMasterData(MMGMasterBean mmgmasterBean) throws Exception {
		try {
			if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ITEMSUBCATEGORY))
				mmgmasterBean.setMasterDataList(mmgMasterDAO.getItemSubCategory());
			else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ITEMCODE))
				mmgmasterBean.setMasterDataList(mmgMasterDAO.getItemCodeDetails());
			else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ITEMSUBCODE))
				mmgmasterBean.setMasterDataList(mmgMasterDAO.getItemSubCodeDetails());
			else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.MATERIAL))
				mmgmasterBean.setMasterDataList(mmgMasterDAO.getMaterialDetails());
			else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.IVENTHOLDER)) {
				mmgmasterBean.setMasterDataList(mmgMasterDAO.getInventoryHolderDetails());
			} else if (CPSUtils.compareStrings(mmgmasterBean.getType(), CPSConstants.ACCHEAD)) {
				mmgmasterBean.setMasterDataList(mmgMasterDAO.getAccountHeadDetails(mmgmasterBean));
			} else
				mmgmasterBean.setMasterDataList(commonDataDAO.getMasterData(getMMGBeanName(mmgmasterBean.getType())));
		} catch (Exception e) {
			throw e;
		}
		return mmgmasterBean;
	}

	public String getMMGBeanName(String type) throws Exception {
		try {
			if (CPSUtils.compareStrings(type, CPSConstants.TAXTYPE)) {
				return CPSConstants.TAXTYPEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.UOM)) {
				return CPSConstants.UOMDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.VOUCHERTYPE)) {
				return CPSConstants.VOUCHERTYPEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCOMPANY)) {
				return CPSConstants.COMPANYMASTERDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCATEGORY)) {
				return CPSConstants.ITEMCATEGORYDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMSUBCATEGORY)) {
				return CPSConstants.ITEMSUBCATEGORYDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCODE)) {
				return CPSConstants.ITEMCODEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMSUBCODE)) {
				return CPSConstants.ITEMSUBCODEDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.MATERIAL)) {
				return CPSConstants.MATERIALMASTERDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.IVENTHOLDER)) {
				return CPSConstants.INVENTORYMASTERDTO;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ACCHEAD)) {
				return CPSConstants.ACCOUNTHEADMASTER;
			}

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String getMMGTableName(String type) throws Exception {
		try {
			if (CPSUtils.compareStrings(type, CPSConstants.TAXTYPE)) {
				return CPSConstants.MMG_B_TAX_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.UOM)) {
				return CPSConstants.MMG_B_UOM_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.VOUCHERTYPE)) {
				return CPSConstants.MMG_B_VOUCHER_TYPE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCOMPANY)) {
				return CPSConstants.MMG_B_COMPANY_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCATEGORY)) {
				return CPSConstants.MMG_B_ITEM_CATEGORY_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMSUBCATEGORY)) {
				return CPSConstants.MMG_B_ITEM_SUB_CATEGORY_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCODE)) {
				return CPSConstants.MMG_B_ITEM_CODE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMSUBCODE)) {
				return CPSConstants.MMG_B_ITEM_SUB_CODE_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.MATERIAL)) {
				return CPSConstants.MMG_B_MATERIAL_MASTER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.IVENTHOLDER)) {
				return CPSConstants.MMG_B_INVENTORY_HOLDER;
			} else if (CPSUtils.compareStrings(type, CPSConstants.ACCHEAD)) {
				return CPSConstants.MMG_B_ACCOUNT_HEAD_MASTER;
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String getMMGTableDetails(String type) throws Exception {
		String message = "";
		try {
			if (CPSUtils.compareStrings(type, CPSConstants.UOM)) {
				return CPSConstants.MATERIALMASTERDTO + "#" + "uom" + "#" + "status" + "," + CPSConstants.LEDGERDTO + "#" + "uom" + "#" + "status" + "," + CPSConstants.VOUCHERITEMDETAILSDTO + "#"
						+ "uom" + "#" + "status" + "," + CPSConstants.DEMANDITEMDETAILSDTO + "#" + "uom" + "#" + "status" + "," + CPSConstants.STOCKDETAILSDTO + "#" + "uom" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCOMPANY)) {
				return CPSConstants.MATERIALMASTERDTO + "#" + "companyCode" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCODE)) {
				return CPSConstants.MATERIALMASTERDTO + "#" + "itemCode" + "#" + "status" + "," + CPSConstants.ITEMCODEDTO + "#" + "itemCode" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMSUBCODE)) {
				return CPSConstants.MATERIALMASTERDTO + "#" + "itemSubCode" + "#" + "status" + "," + CPSConstants.ITEMSUBCODEDTO + "#" + "itemSubCode" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMCATEGORY)) {
				return CPSConstants.MATERIALMASTERDTO + "#" + "categoryCode" + "#" + "status" + "," + CPSConstants.ITEMCODEDTO + "#" + "categoryId" + "#" + "status" + ","
						+ CPSConstants.ITEMCATEGORYDTO + "#" + "categoryCode" + "#" + "status" + "," + CPSConstants.ITEMSUBCATEGORYDTO + "#" + "categoryId" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.ITEMSUBCATEGORY)) {
				return CPSConstants.MATERIALMASTERDTO + "#" + "subCategoryCode" + "#" + "status" + "," + CPSConstants.ITEMCODEDTO + "#" + "subCategoryId" + "#" + "status" + ","
						+ CPSConstants.ITEMSUBCATEGORYDTO + "#" + "subCategoryCode" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.MATERIAL)) {
				return CPSConstants.MATERIALMASTERDTO + "#" + "materialCode" + "#" + "status" + "," + CPSConstants.LEDGERDTO + "#" + "materialCode" + "#" + "status" + ","
						+ CPSConstants.VOUCHERITEMDETAILSDTO + "#" + "materialCode" + "#" + "status" + "," + CPSConstants.STOCKDETAILSDTO + "#" + "materialCode" + "#" + "status" + ","
						+ CPSConstants.SECURITYDETAILSDTO + "#" + "materialCode" + "#" + "status" + "," + CPSConstants.DEMANDITEMDETAILSDTO + "#" + "materialCode" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.VOUCHERTYPE)) {
				// return CPSConstants.IRITEMDETAILS+"#" + "voucherType" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.INVENTORYHOLDER)) {
				return CPSConstants.LEDGERDTO + "#" + "inventoryNo" + "#" + "status" + "," + CPSConstants.STOCKDETAILSDTO + "#" + "inventoryNo" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.TAXTYPE)) {
				return CPSConstants.DEMANDITEMDETAILSDTO + "#" + "taxTypeId" + "#" + "status" + "," + CPSConstants.IRITEMDETAILS + "#" + "taxTypeId" + "#" + "status";
			} else if (CPSUtils.compareStrings(type, CPSConstants.ACCHEAD)) {
				return CPSConstants.FUNDSALLOTEDDETAILSDTO + "#" + "accountHeadId" + "#" + "status";
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List getMMGParentDataList(String beanName) throws Exception {
		List list = null;
		try {

			list = commonDataDAO.getMasterData(beanName);

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public int getInventoryNum() throws Exception {
		int inventoryNo = 0;
		try {
			inventoryNo = mmgMasterDAO.getInventoryNum();

		} catch (Exception e) {
			throw e;
		}
		return inventoryNo;
	}

	public List<KeyValueDTO> getSfidList() throws Exception {
		List<KeyValueDTO> list = null;
		try {
			list = commonDataDAO.getSfidList();

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<Object> getDivisionList() throws Exception {
		List<Object> list = null;
		try {
			list = commonDataDAO.getOrgDivisionList();

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<Object> getFundTypeList() throws Exception {
		List<Object> list = null;
		try {
			list = commonDataDAO.getFundTypeList();

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public MMGMasterBean getInventoryDetails(MMGMasterBean mmgmasterBean) throws Exception {

		try {
			InventoryMasterDTO invMasterDTO = commonDataDAO.getInventoryHolderDetails("", mmgmasterBean.getSfid(), mmgmasterBean.getOrgRoleId());
			if (!CPSUtils.isNullOrEmpty(invMasterDTO)) {
				BeanUtils.copyProperties(mmgmasterBean, invMasterDTO);
			}
		} catch (Exception e) {
			throw e;
		}
		return mmgmasterBean;
	}

	public MMGMasterBean getInventoryHolderDetails(MMGMasterBean mmgmasterBean) throws Exception {

		try {
			JSONObject outerjson = new JSONObject();
			int i = 0;
			for (InventoryMasterDTO imdto : mmgMasterDAO.getInventoryHolderDetails()) {
				if (CPSUtils.compareStrings(imdto.getSfid(), mmgmasterBean.getSfid())) {
					InventoryMasterDTO invMasterDTO = commonDataDAO.getInventoryHolderDetails("", imdto.getSfid(), imdto.getOrgRoleId());
					LinkedHashMap innerjson = new LinkedHashMap();
					innerjson.put("id", imdto.getOrgRoleId());
					innerjson.put("Inventory Number", imdto.getInventoryNo());
					innerjson.put("SFID", imdto.getSfid());
					innerjson.put("Directorate", invMasterDTO.getDirectorateName());
					innerjson.put("Division", invMasterDTO.getDivisionName());
					innerjson.put("Name", invMasterDTO.getHolderName());
					innerjson.put("Designation", invMasterDTO.getDesignation());
					innerjson.put("Phone", invMasterDTO.getPhone());
					// innerjson.put("Demand Purchase Limit", imdto.getDemandPurchaseLimit());
					innerjson.put("Inventory For", imdto.getInventoryHolderTypeName());
					outerjson.put(String.valueOf(i), innerjson);
					i++;
				}
			}
			outerjson.put(String.valueOf(i), CPSConstants.INVENTORYHOLDER);
			mmgmasterBean.setInventoryDetails(outerjson);
		} catch (Exception e) {
			throw e;
		}
		return mmgmasterBean;
	}

	public MMGMasterBean inventoryDetails(MMGMasterBean mmgmasterBean) throws Exception {
		try {
			for (InventoryMasterDTO imdto : mmgMasterDAO.getInventoryHolderDetails()) {
				if (CPSUtils.compareStrings(imdto.getSfid(), mmgmasterBean.getSfid())) {
					InventoryMasterDTO invMasterDTO = commonDataDAO.getInventoryHolderDetails("", imdto.getSfid(), mmgmasterBean.getOrgRoleId());
					mmgmasterBean.setId(imdto.getInvId());
					mmgmasterBean.setInventoryNo(imdto.getInventoryNo());
					;
					mmgmasterBean.setSfid(imdto.getSfid());
					mmgmasterBean.setDirectorateName(invMasterDTO.getDirectorateName());
					mmgmasterBean.setDivisionName(invMasterDTO.getDivisionName());
					mmgmasterBean.setHolderName(invMasterDTO.getHolderName());
					mmgmasterBean.setDesignation(invMasterDTO.getDesignation());
					mmgmasterBean.setPhone(invMasterDTO.getPhone());
					mmgmasterBean.setDemandPurchaseLimit(imdto.getDemandPurchaseLimit());
					mmgmasterBean.setInventoryHolderType(imdto.getInventoryHolderType());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return mmgmasterBean;
	}

	public MMGMasterBean saveMMGConfigDetails(MMGMasterBean mmgmasterBean) throws Exception {
		try {
			if (mmgmasterBean.getType().equals("financialYear")) {
				mmgmasterBean.setName("FINANCIAL_YEAR");
				mmgmasterBean.setTypeValue(mmgmasterBean.getFinancialYear());
			} else if (mmgmasterBean.getType().equals("demandPurchaseLimit")) {
				mmgmasterBean.setName("DEMAND_LIMIT");
				mmgmasterBean.setTypeValue(mmgmasterBean.getDemandPurchaseLimit());
				mmgmasterBean.setDescription(mmgmasterBean.getPurchaseLimitDesc());
			}
			mmgmasterBean.setMessage(mmgMasterDAO.saveMMGConfigDetails(mmgmasterBean));
		} catch (Exception e) {
			throw e;
		}
		return mmgmasterBean;
	}

	public List<ProjectCodeDTO> getProjectList() throws Exception {
		List<ProjectCodeDTO> list = null;
		try {
			list = mmgMasterDAO.getProjectList();

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<KeyValueDTO> getFinancialYearList() throws Exception {
		List<KeyValueDTO> list = null;
		try {
			list = mmgMasterDAO.getFinancialYearList();

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

}
