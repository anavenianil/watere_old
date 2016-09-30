package com.callippus.web.business.mmg.cashbuildup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.mmg.cashbuildup.IMMGMasterDAO;
import com.callippus.web.dao.mmg.cashbuildup.ISecurityDAO;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.SecurityDetailsDTO;

@Service
public class SecurityDetailsBusiness {
	@Autowired
	private ISecurityDAO securityDAO;
	@Autowired
	private IMMGMasterDAO mmgMasterDAO;

	public SecurityDetailsDTO getDemandNumbers(SecurityDetailsDTO security) throws Exception {
		try {
			security.setDemandList(securityDAO.getDemandNumber());
		} catch (Exception e) {
			throw e;
		}
		return security;

	}

	public SecurityDetailsDTO getDemandDetails(SecurityDetailsDTO security, HttpServletRequest request) throws Exception {
		int demandItemsQty = 0;
		int receivedItemsQty = 0;
		int count = 0;
		List<Object> demandItemsList = null;
		List<Object> securityItemsList = null;
		try {
			demandItemsList = new ArrayList<Object>();
			request.getSession(true);
			DemandMasterDTO demand = mmgMasterDAO.getDemandDetails(security.getDemandNo(), CPSConstants.DEMANDSETTLE);
			// BeanUtils.copyProperties(security,demand);
			securityItemsList = securityDAO.getSecurityItemDetails(security.getDemandNo());
			for (int i = 0; i < demand.getDemandItems().size(); i++) {
				DemandItemDetailsDTO dItems = demand.getDemandItems().get(i);
				if (CPSUtils.checkList(securityItemsList)) {
					receivedItemsQty = 0;
					DemandItemDetailsDTO securityItems = null;
					for (Object s : securityItemsList) {
						securityItems = new DemandItemDetailsDTO();
						BeanUtils.copyProperties(securityItems, s);
						if (CPSUtils.compareStrings(dItems.getMaterialCode(), securityItems.getMaterialCode())) {
							receivedItemsQty += Integer.parseInt(securityItems.getQty());
							demandItemsList.add(securityItems);
						}
					}
					demandItemsQty = Integer.parseInt(dItems.getQty());
					if (receivedItemsQty < demandItemsQty) {
						demandItemsQty = demandItemsQty - receivedItemsQty;
						dItems.setQty(String.valueOf(demandItemsQty));
						demandItemsList.add(dItems);
						count = count + 1;
					}
				} else {
					count = count + 1;
					demandItemsList.add(dItems);
				}
			}
			if (count == 0) {
				mmgMasterDAO.UpdateDemandStatus(security.getDemandNo(), CPSConstants.DEMANDSECURITY);
			}
			security.setDemandItems(demandItemsList);
			security.setDemandDate(demand.getDemandDate());
		} catch (Exception e) {
			throw e;
		}

		return security;

	}

	/**
	 * This method saves the data enter at Security Check when demanded items reached to Security gate
	 * 
	 * @param security
	 *            contains security check data
	 * @param request
	 *            HttpServletRequest
	 * @return message -->if data inserted Successfully,return success
	 */

	public String saveSecurityCheckItems(SecurityDetailsDTO security, HttpServletRequest request) throws Exception {
		String message = "";
		HttpSession session = null;
		try {
			session = request.getSession();
			JSONObject json = (JSONObject) JSONSerializer.toJSON(security.getSecurityCheckJson());
			for (int i = 1; i <= json.size(); i++) {
				JSONObject securityItemsJson = (JSONObject) json.get(String.valueOf(i));
				SecurityDetailsDTO sec = new SecurityDetailsDTO();
				sec.setMaterialCode(securityItemsJson.getString("materialCode"));
				sec.setQty(securityItemsJson.getString("qty"));
				sec.setUom(securityItemsJson.get("uom").toString());
				sec.setCreationDate(CPSUtils.getCurrentDate());
				sec.setLastModifiedDate(CPSUtils.getCurrentDate());
				sec.setVerifiedBy(session.getAttribute(CPSConstants.SFID).toString());
				sec.setStatus("1");
				sec.setMemoNo(security.getMemoNo());
				sec.setMemoDate(security.getMemoDate());
				sec.setDemandNo(securityItemsJson.get("demandNo").toString());
				security.setDemandNo(securityItemsJson.get("demandNo").toString());
				message = securityDAO.saveSecurityCheckDetails(sec);
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	// public SecurityDetailsDTO getDemandDetailsAtSecurity(SecurityDetailsDTO security,HttpServletRequest request)
	// {
	// List<SecurityDetailsDTO> demandItemsAtSecurity=null;
	// HttpSession session=null;
	// try{
	// demandItemsAtSecurity=new ArrayList<SecurityDetailsDTO>();
	// session=request.getSession(true);
	// demandItemsAtSecurity=securityDAO.getDemandDetailsAtSecurityCheck(security.getDemandNo());
	// security.setDemandItems(demandItemsAtSecurity);
	// }catch (Exception e) {
	// throw e;
	// }
	//		
	// return security;
	// }
}
