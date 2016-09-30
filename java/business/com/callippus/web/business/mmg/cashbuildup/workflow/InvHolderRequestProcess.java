package com.callippus.web.business.mmg.cashbuildup.workflow;

import java.util.ArrayList;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.WorkflowTxnDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.domainobject.InventoryDomainObject;
import com.callippus.web.business.requestprocess.RequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.mmg.cashbuildup.dto.InventoryHolderRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;

@Service
public class InvHolderRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(InvHolderRequestProcess.class);
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private InventoryDomainObject inventoryDomainObject;

	/**
	 * @param InventoryHolderRequestBean
	 * @return
	 * @throws Exception
	 */
	public String initWorkflow(InventoryHolderRequestBean invRequestBean) throws Exception {
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(VoucherRequestBean vouRequestBean)>>>>>>>>>Start");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			invRequestBean.setRequestID(requestProcess.generateUniqueID(CPSConstants.REQUEST));
			invRequestBean.setRequestType(CPSConstants.INVENTORYHOLDERREQUEST);
			invRequestBean.setRequestTypeID("12");// Demand Request type ID = 11
			invRequestBean = setChangedValues(invRequestBean);
			message = requestProcess.submitTxnDetails(invRequestBean.getTxnDetails());

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(rb, invRequestBean);
				message = requestProcess.initWorkflow(rb);
				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, message)) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, invRequestBean);
					message = inventoryDomainObject.updateTxnDetails(invRequestBean);
				}

			}

		} catch (Exception e) {
			throw e;
		}
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(VoucherRequestBean vouRequestBean)>>>>>>>>>End");
		return message;
	}

	public InventoryHolderRequestBean setChangedValues(InventoryHolderRequestBean invRequestBean) throws Exception {
		Session session = null;
		JSONObject changedValues = null;
		ArrayList<WorkflowTxnDTO> txnDetails = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			txnDetails = new ArrayList<WorkflowTxnDTO>();
			InventoryMasterDTO invDTO = (InventoryMasterDTO) session.createCriteria(InventoryMasterDTO.class).add(Expression.eq("inventoryNo", invRequestBean.getInventoryNo())).uniqueResult();
			if (CPSUtils.compareStrings(invRequestBean.getType(), CPSConstants.DELETE)) {
				txnDetails.add(requestProcess.txnRowDetails(invRequestBean.getRequestTypeID(), invRequestBean.getRequestID(), invRequestBean.getInventoryNo(), "DELETE", invRequestBean.getIpAddress(),
						"DELETE", invRequestBean.getInventoryNo(), invDTO.getInvId(), "Inventory Holder Number"));
			} else {
				changedValues = (JSONObject) JSONSerializer.toJSON(invRequestBean.getChangedValues());

				/**
				 * When the request is from IE browser empty fields are assigned to null value
				 */
				if (CPSUtils.compareStrings(changedValues.getString("inventoryNo"), "null")) {
					changedValues.put("inventoryNo", "");
				}
				if (CPSUtils.compareStrings(changedValues.getString("sfid"), "null")) {
					changedValues.put("sfid", "");
				}
				if (CPSUtils.compareStrings(changedValues.getString("inventoryHolderType"), "null")) {
					changedValues.put("inventoryHolderType", "");
				}
				if (CPSUtils.compareStrings(changedValues.getString("orgRoleId"), "null")) {
					changedValues.put("orgRoleId", "");
				}
				if (!CPSUtils.isNullOrEmpty(invDTO)) {
					/*
					 * txnDetails.add(requestProcess.txnRowDetails(invRequestBean.getRequestTypeID(), invRequestBean.getRequestID(), invRequestBean.getInventoryNo(), "INVENTORY_NO",
					 * invRequestBean.getIpAddress(), invRequestBean.getInventoryNo(), invRequestBean.getInventoryNo(), invDTO.getInvId(), "Inventory Number"));
					 */

					if (!CPSUtils.compareStrings(invDTO.getSfid(), changedValues.getString("sfid")) && !CPSUtils.bothNullOrNot(invDTO.getSfid(), changedValues.getString("sfid"))) {
						changedValues.put("sfid", CPSUtils.setEmptyIfNull(changedValues.getString("sfid")));

						txnDetails.add(requestProcess.txnRowDetails(invRequestBean.getRequestTypeID(), invRequestBean.getRequestID(), invRequestBean.getInventoryNo(), "SFID", invRequestBean
								.getIpAddress(), changedValues.getString("sfid"), invDTO.getSfid(), invDTO.getInvId(), "SFID"));
					}
					if (!CPSUtils.compareStrings(invDTO.getInventoryHolderType(), changedValues.getString("inventoryHolderType"))
							&& !CPSUtils.bothNullOrNot(invDTO.getInventoryHolderType(), changedValues.getString("inventoryHolderType"))) {
						if (CPSUtils.compareStrings(invDTO.getInventoryHolderType(), "30"))
							invDTO.setInventoryHolderType("Buildup");
						else if (CPSUtils.compareStrings(invDTO.getInventoryHolderType(), "31"))
							invDTO.setInventoryHolderType("Project");
						if (CPSUtils.compareStrings(changedValues.getString("inventoryHolderType"), "30"))
							changedValues.put("inventoryHolderType", "Buildup");
						else if (CPSUtils.compareStrings(changedValues.getString("inventoryHolderType"), "31"))
							changedValues.put("inventoryHolderType", "Project");
						changedValues.put("inventoryHolderType", CPSUtils.setEmptyIfNull(changedValues.getString("inventoryHolderType")));
						txnDetails.add(requestProcess.txnRowDetails(invRequestBean.getRequestTypeID(), invRequestBean.getRequestID(), invRequestBean.getInventoryNo(), "INVENTORY_HOLDER_TYPE",
								invRequestBean.getIpAddress(), changedValues.getString("inventoryHolderType"), invDTO.getInventoryHolderType(), invDTO.getInvId(), "Iventory Holder Type"));
					}
					if (!CPSUtils.compareStrings(invDTO.getSfid(), changedValues.getString("orgRoleId")) && !CPSUtils.bothNullOrNot(invDTO.getSfid(), changedValues.getString("orgRoleId"))) {
						changedValues.put("orgRoleId", CPSUtils.setEmptyIfNull(changedValues.getString("orgRoleId")));

						txnDetails.add(requestProcess.txnRowDetails(invRequestBean.getRequestTypeID(), invRequestBean.getRequestID(), invRequestBean.getInventoryNo(), "ORG_ROLE_ID", invRequestBean
								.getIpAddress(), changedValues.getString("orgRoleId"), invDTO.getOrgRoleId(), invDTO.getInvId(), "ORGROLEID"));
					}

				}
			}
			invRequestBean.setTxnDetails(txnDetails);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return invRequestBean;
	}

	public String approvedRequest(InventoryHolderRequestBean invBean) throws Exception {
		log.debug("::<<<<<AddressRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(AddressRequestBean prb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(rb, invBean);
			rb = requestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, invBean);
				rb.setMessage(inventoryDomainObject.updateTxnDetails(invBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

}
