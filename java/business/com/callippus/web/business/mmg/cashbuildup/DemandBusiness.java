package com.callippus.web.business.mmg.cashbuildup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.mmg.cashbuildup.IDemandDAO;
import com.callippus.web.dao.mmg.cashbuildup.IMMGMasterDAO;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandTypeDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.MaterialMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.ProjectCodeDTO;

@Service
public class DemandBusiness {
	@Autowired
	private IDemandDAO demandDAO;
	@Autowired
	private IComonObjectDAO commonDAO;
	@Autowired
	private IMMGMasterDAO mmgmasterDAO;

	@SuppressWarnings("unchecked")
	public DemandMasterDTO getMasterData(DemandMasterDTO demand, HttpServletRequest request) throws Exception {
		List<InventoryMasterDTO> invList = null;
		List<ProjectCodeDTO> projectCodes = null;
		List<DemandTypeDTO> demandTypes = null;
		HttpSession session = null;
		try {
			session = request.getSession(true);
			invList = commonDAO.getInventoryNumsList(session.getAttribute(CPSConstants.SFID).toString());
			projectCodes = commonDAO.getMasterData(CPSConstants.PROJECTCODEMASTER);
			demandTypes = demandDAO.getDemandTypes();
			demand.setInvList(invList);
			demand.setProjectList(projectCodes);
			demand.setAccountHeadList(mmgmasterDAO.getAccountHeadNos(demand.getInventoryNo()));
			demand.setDemandTypeList(demandTypes);
			demand.setDemandDate(CPSUtils.getCurrentDateWithTime());
		} catch (Exception e) {
			throw e;
		}
		return demand;
	}

	public DemandMasterDTO raiseDemand(DemandMasterDTO demand, HttpServletRequest request) throws Exception {
		String message = null;
		HttpSession session = null;
		List<DemandItemDetailsDTO> demanditemslist = null;
		try {
			session = request.getSession(true);
			JSONArray json = (JSONArray) session.getAttribute(CPSConstants.DEMANDITEMSJSON);
			demanditemslist = new ArrayList<DemandItemDetailsDTO>();
			Float total = 0.0f;
			for (int i = 0; i < json.size(); i++) {
				net.sf.json.JSONObject itemsjson = (net.sf.json.JSONObject) json.get(i);
				DemandItemDetailsDTO items = new DemandItemDetailsDTO();
				BeanUtils.copyProperties(items, itemsjson);
				items.setId(null);
				items.setStatus("1");
				items.setAmountTypeId("0");
				items.setStageId("1");
				items.setDemandNo(demand.getDemandNo());
				items.setCreationDate(CPSUtils.getCurrentDate());
				items.setLastModifiedDate(CPSUtils.getCurrentDate());
				items.setChangedBy(demand.getSfid());
				demanditemslist.add(items);
				total = total + (Float.parseFloat(itemsjson.getString("unitRate")) * Float.parseFloat(itemsjson.getString("qty")));
			}
			demand.setTotalCost(total.toString());
			demand.setDemandItems(demanditemslist);
			demand.setIpAddress(request.getRemoteAddr());
			demand.setRaisedBy(session.getAttribute(CPSConstants.SFID).toString());
			demand.setDemandDate(CPSUtils.getCurrentDateWithTime());
			demand.setCreationDate(CPSUtils.getCurrentDateWithTime());
			demand.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			demand.setStatus(Integer.parseInt(CPSConstants.DEMANDSAVEDRAFT));
			message = saveDraft(demand);
			commonDAO.getTableID("MMG_B_DEMAND_MASTER", CPSConstants.UPDATE);
			demand.setMessage(message);
		} catch (Exception e) {
			throw e;
		}
		return demand;
	}

	public DemandMasterDTO getDemandDetails(DemandMasterDTO demand, HttpServletRequest request) throws Exception {
		HttpSession session = null;
		DemandMasterDTO demandBean = null;
		try {
			session = request.getSession(true);
			demandBean = mmgmasterDAO.getDemandDetails(demand.getDemandNo(), CPSConstants.DEMANDSAVEDRAFT);
			BeanUtils.copyProperties(demand, demandBean);
			if (!CPSUtils.isNull(demand)) {
				session.setAttribute(CPSConstants.DEMANDITEMSJSON, (JSONArray) JSONSerializer.toJSON(demand.getDemandItems()));
			}
			getInventoryHolder(demand.getInventoryNo(), request, "draftDetails");
			demand.setDemandNo(demandBean.getDemandNo());
		} catch (Exception e) {
			throw e;
		}
		return demand;
	}

	public String saveItem(String itemsjson, HttpServletRequest request) throws Exception {
		String message = "";
		HttpSession session = null;
		try {
			session = request.getSession(true);
			/*
			 * This if statement is used to check whether addeditem is existed in stockdetails table for inventory holder.
			 */
			/*
			 * if(CPSUtils.compareStrings(request.getParameter(CPSConstants.TYPE), CPSConstants.EXTERNALISSUEVOUVHER) || CPSUtils.compareStrings(request.getParameter(CPSConstants.TYPE),
			 * CPSConstants.EXPENSEVOUVHER) || CPSUtils.compareStrings(request.getParameter(CPSConstants.TYPE), CPSConstants.TRANSFERVOUVHER)){
			 * message=demandDAO.checkInvHolderItem(request.getParameter(CPSConstants.INVENTORYNO),new JSONObject(itemsjson).getString("materialCode")); } if(CPSUtils.compareStrings(message,
			 * CPSConstants.SUCCESS) || CPSUtils.compareStrings(message, "")){
			 */
			if (!CPSUtils.isNull(session.getAttribute(CPSConstants.DEMANDITEMSJSON))) {
				boolean flag = false;
				JSONArray json = (JSONArray) session.getAttribute(CPSConstants.DEMANDITEMSJSON);
				int size = json.size();
				for (int i = 0; i < size; i++) {
					JSONObject requestjson = new JSONObject(itemsjson);
					net.sf.json.JSONObject sessionjson = (net.sf.json.JSONObject) json.get(i);
					if (CPSUtils.compareStrings(sessionjson.getString("materialCode"), requestjson.getString("materialCode"))) {
						if (!CPSUtils.compareStrings(requestjson.get("qty").toString(), "")) {
							sessionjson.put("qty", requestjson.get("qty"));
							sessionjson.put("unitRate", requestjson.get("unitRate"));
							sessionjson.put("uom", requestjson.get("uom"));
						}
						if (!CPSUtils.isNull(requestjson.get("amountType")))
							sessionjson.put("amountType", requestjson.get("amountType"));
						if (!CPSUtils.isNull(requestjson.get("cncConvert")))
							sessionjson.put("cncConvert", requestjson.get("cncConvert"));
						if (!CPSUtils.isNull(requestjson.get("uomConvert")))
							sessionjson.put("uomConvert", requestjson.get("uomConvert"));
						flag = true;
					}
				}
				if (!flag) {
					List<Object> demandItemDetails = getDemandItemDetails(itemsjson, request);
					json.addAll(demandItemDetails);
				}
				session.setAttribute(CPSConstants.DEMANDITEMSJSON, json);
				message = CPSConstants.SUCCESS;
			} else {
				List<Object> demandItemDetails = getDemandItemDetails(itemsjson, request);
				if (CPSUtils.compareStrings(request.getParameter(CPSConstants.TYPE), CPSConstants.EXTERNALISSUEVOUVHER)
						|| CPSUtils.compareStrings(request.getParameter(CPSConstants.TYPE), CPSConstants.EXPENSEVOUVHER)
						|| CPSUtils.compareStrings(request.getParameter(CPSConstants.TYPE), CPSConstants.TRANSFERVOUVHER)
						|| CPSUtils.compareStrings(request.getParameter(CPSConstants.TYPE), CPSConstants.CONDEMNATIONVOUVHER)) {
					for (int i = 0; i < demandItemDetails.size(); i++) {
						DemandItemDetailsDTO demanddto = (DemandItemDetailsDTO) demandItemDetails.get(i);
						int qty = demandDAO.checkInvHolderItem(request.getParameter(CPSConstants.INVENTORYNO), demanddto.getMaterialCode());
						if (qty < Integer.parseInt(demanddto.getQty())) {
							message = "lessQty";
						} else {
							session.setAttribute(CPSConstants.DEMANDITEMSJSON, (JSONArray) JSONSerializer.toJSON(demandItemDetails));
							message = CPSConstants.SUCCESS;
						}
					}
				} else {
					session.setAttribute(CPSConstants.DEMANDITEMSJSON, (JSONArray) JSONSerializer.toJSON(demandItemDetails));
					message = CPSConstants.SUCCESS;
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<Object> getDemandItemDetails(String jsonvalue, HttpServletRequest request) throws Exception {
		List<Object> demandItemsList = null;
		JSONObject json = null;
		HttpSession session = null;
		try {
			session = request.getSession(true);
			json = new JSONObject(jsonvalue);
			demandItemsList = new ArrayList<Object>();
			DemandItemDetailsDTO demandItems = new DemandItemDetailsDTO();
			demandItems.setMaterialCode(json.getString("materialCode"));
			demandItems.setQty(json.getString("qty"));
			demandItems.setUnitRate(json.getString("unitRate"));
			demandItems.setUom(json.getString("uom"));
			demandItems.setDemandNo(json.getString("demandNo"));
			if (!CPSUtils.isNullOrEmpty(json.getString("cncConvert"))) {
				demandItems.setCncConvert(json.getString("cncConvert"));
			}
			if (!CPSUtils.isNullOrEmpty(json.getString("uomConvert"))) {
				demandItems.setUomConvert(json.getString("uomConvert"));
			}
			if (!CPSUtils.isNullOrEmpty(json.getString("qty"))) {
				demandItems.setAmount(String.valueOf(json.getInt("unitRate") * json.getInt("qty")));
			}
			JSONArray sessionjson = (JSONArray) session.getAttribute("materialsJson");
			for (int i = 0; i < sessionjson.size(); i++) {
				net.sf.json.JSONObject materialjson = (net.sf.json.JSONObject) sessionjson.get(i);
				if (CPSUtils.compareStrings(materialjson.getString("materialCode"), json.getString("materialCode"))) {
					// net.sf.json.JSONObject uom=(net.sf.json.JSONObject)materialjson.get("uomName");
					// demandItems.setUom(uom.getString("name"));
					demandItems.setDescription(materialjson.getString("materialName"));
					demandItems.setCflag(materialjson.getString("consumableFlag"));
				}
			}
			demandItemsList.add(demandItems);
		} catch (Exception e) {
			throw e;
		}
		return demandItemsList;
	}

	public void getInventoryHolder(String invno, HttpServletRequest request, String type) throws Exception {
		HttpSession session = null;
		try {
			session = request.getSession(true);
			InventoryMasterDTO inventory = commonDAO.getInventoryHolderDetails(invno, "", "");
			if (!CPSUtils.compareStrings(type, "draftDetails")) {
				int id = commonDAO.getTableID("MMG_B_DEMAND_MASTER", CPSConstants.CREATE);
				String demandNo = "ASL" + "/" + inventory.getDirectorateName() + "/" + inventory.getDivisionName() + "/" + id;
				inventory.setDemandNo(demandNo);
			}
			session.setAttribute("resultType", "inventory");
			session.setAttribute("inventoryHolder", (net.sf.json.JSONObject) JSONSerializer.toJSON(inventory));
			session.setAttribute("accountHeadList", (net.sf.json.JSONArray) JSONSerializer.toJSON(mmgmasterDAO.getAccountHeadNos(invno)));
		} catch (Exception e) {
			throw e;
		}
	}

	public DemandMasterDTO getSearchValues(DemandMasterDTO demand, HttpServletRequest request) throws Exception {
		HttpSession session = null;
		try {
			session = request.getSession(true);
			List<MaterialMasterDTO> materials = commonDAO.searchValues(demand);
			session.setAttribute("resultType", demand.getSearch());
			session.setAttribute("materialsJson", (net.sf.json.JSONArray) JSONSerializer.toJSON(materials));
		} catch (Exception e) {
			throw e;
		}
		return demand;
	}

	public void deleteItem(String jsonvalue, HttpServletRequest request) throws Exception {
		HttpSession session = null;
		try {
			session = request.getSession(true);
			JSONArray json = (JSONArray) session.getAttribute(CPSConstants.DEMANDITEMSJSON);
			for (int i = 0; i < json.size(); i++) {
				net.sf.json.JSONObject sessionjson = (net.sf.json.JSONObject) json.get(i);
				JSONObject requestjson = new JSONObject(jsonvalue);
				if (CPSUtils.compareStrings(sessionjson.getString("materialCode"), requestjson.getString("materialCode"))) {
					json.remove(i);
				}
			}
			session.setAttribute(CPSConstants.DEMANDITEMSJSON, json);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<DemandMasterDTO> getDraftDetails(String status, String sfid) throws Exception {
		List<DemandMasterDTO> demandList = null;
		try {
			demandList = demandDAO.getDraftDetails(status, sfid);
		} catch (Exception e) {
			throw e;
		}
		return demandList;
	}

	public String checkBudgetAccountHead(DemandRequestBean drb) throws Exception {
		AccountHeadDTO accdto = null;
		String message = "";
		try {
			accdto = demandDAO.checkingAccountHeadAmt(drb.getAccountHeadId());
			JSONArray json = (JSONArray) drb.getItemsJson();
			Float total = 0.0f;
			for (int i = 0; i < json.size(); i++) {
				net.sf.json.JSONObject itemsjson = (net.sf.json.JSONObject) json.get(i);
				total = total + (Float.parseFloat(itemsjson.getString("unitRate")) * Float.parseFloat(itemsjson.getString("qty")));
			}
			Float totalFund = Float.parseFloat(accdto.getAllottedFund()) - Float.parseFloat(accdto.getCommitedFund());
			if (total < totalFund) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.FAILED;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List<DemandMasterDTO> getDemandNumbers() throws Exception {
		List<DemandMasterDTO> demandList = null;
		try {
			demandList = mmgmasterDAO.getDemandNumbers(CPSConstants.DEMANDRAISED);
		} catch (Exception e) {
			throw e;
		}
		return demandList;
	}

	public String checkDemandLimit(DemandRequestBean drb) throws Exception {
		String message = "";
		InventoryMasterDTO inv = null;
		try {
			String demandlimit = demandDAO.getDemandLimit();
			JSONArray json = (JSONArray) drb.getItemsJson();
			Float total = 0.0f;
			for (int i = 0; i < json.size(); i++) {
				net.sf.json.JSONObject itemsjson = (net.sf.json.JSONObject) json.get(i);
				total = total + (Float.parseFloat(itemsjson.getString("unitRate")) * Float.parseFloat(itemsjson.getString("qty")));
			}
			if (total < Float.parseFloat(demandlimit)) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.FAILED;
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String saveDraft(DemandMasterDTO demand) throws Exception {
		String message = "";
		try {
			DemandMasterDTO demandexist = demandDAO.getDemandDetails(demand.getDemandNo());
			if (!CPSUtils.isNull(demandexist)) {
				message = demandDAO.deleteDemandItems(demand.getDemandNo());
				List demandItems = demand.getDemandItems();
				Float total = 0.0f;
				for (int i = 0; i < demandItems.size(); i++) {
					DemandItemDetailsDTO itemsList = (DemandItemDetailsDTO) demandItems.get(i);
					demandDAO.saveDemand(itemsList);
					total = total + (Float.parseFloat(itemsList.getUnitRate()) * Float.parseFloat(itemsList.getQty()));
				}
				if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS))
					message = demandDAO.updateDemandAmount(demand.getDemandNo(), String.valueOf(total));
			} else {
				message = demandDAO.saveDemand(demand);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deleteDraft(String demandNo) throws Exception {
		String message = "";
		try {
			message = demandDAO.deleteDemandItems(demandNo);
			message = demandDAO.deleteDemand(demandNo);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
}
