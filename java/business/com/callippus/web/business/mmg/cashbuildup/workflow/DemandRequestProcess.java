package com.callippus.web.business.mmg.cashbuildup.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.requestprocess.RequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.mmg.cashbuildup.IDemandDAO;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.FundsAllotedDetailsDTO;

@Service
public class DemandRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(DemandRequestProcess.class);
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private DemandDomainObject demandDomainObject;
	@Autowired
	private IComonObjectDAO commonDAO;
	@Autowired
	private IDemandDAO demandDAO;

	/**
	 * This method will be called when a user requested for demand raise
	 * 
	 * @param prb
	 * @return
	 */

	public String initWorkflow(DemandRequestBean drb) throws Exception {
		log.debug("::<<<<<DemandRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(DemandRequestBean prb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			drb.setRequestID(requestProcess.generateUniqueID(CPSConstants.REQUEST));
			if (CPSUtils.compareStrings(drb.getType(), "Demand")) {
				drb.setRequestType(CPSConstants.DEMAND);
				drb.setRequestTypeID("6");// Demand Request type ID = 6
			} else if (CPSUtils.compareStrings(drb.getType(), "DemandCancel")) {
				drb.setRequestType(CPSConstants.CANCELDEMAND);
				drb.setRequestTypeID("13");
			}

			/**
			 * When a demand is raised then they are saved in demand master and demand item details
			 */
			DemandMasterDTO demand = null;
			if (CPSUtils.compareStrings(drb.getType(), "Demand")) {
				demand = setChangedValues(drb);
				if (CPSUtils.compareStrings(String.valueOf(drb.getStatus()), CPSConstants.DEMANDSAVEDRAFT)) {
					message = demandDomainObject.UpdateDemandStatus(demand.getDemandNo(), CPSConstants.DEMANDRAISED);
				} else {
					demand.setStatus(Integer.parseInt(CPSConstants.DEMANDRAISED));
					message = submitTxnDetails(demand);
				}

				/**
				 * When a demand is raised update committed fund
				 */
				demandDAO.updateCommittedFund(demand.getTotalCost(), drb.getAccountHeadId(), "credit");
			} else
				message = CPSConstants.SUCCESS;

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(rb, drb);
				message = requestProcess.initWorkflow(rb);

				/**
				 * Request Id will be updated for the demand
				 */
				updateRequestIdForDemand(drb, rb.getRequestID(), drb.getType());
				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, message)) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, drb);

					if (CPSUtils.compareStrings(drb.getType(), "Demand")) {
						message = demandDomainObject.updateFundDetails(drb);
						/**
						 * Update the demand settled status
						 */
						demandDomainObject.UpdateDemandStatus(demand.getDemandNo(), CPSConstants.DEMANDSETTLE);
					} else {
						demandDAO.updateCommittedFund(drb.getTotalCost(), drb.getAccountHeadId(), "debit");
						demandDomainObject.UpdateDemandStatus(drb.getDemandNo(), CPSConstants.DEMANDCANCEL);
					}

				}
			}

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public DemandMasterDTO setChangedValues(DemandRequestBean drb) throws Exception {
		DemandMasterDTO demand = null;
		List<DemandItemDetailsDTO> demanditemslist = null;
		try {
			demand = new DemandMasterDTO();
			BeanUtils.copyProperties(demand, drb);
			commonDAO.getTableID("MMG_B_DEMAND_MASTER", CPSConstants.UPDATE);
			JSONArray json = (JSONArray) drb.getItemsJson();
			demanditemslist = new ArrayList<DemandItemDetailsDTO>();
			Float total = 0.0f;
			for (int i = 0; i < json.size(); i++) {
				JSONObject itemsjson = (JSONObject) json.get(i);
				DemandItemDetailsDTO items = new DemandItemDetailsDTO();
				BeanUtils.copyProperties(items, itemsjson);
				if (CPSUtils.compareStrings(drb.getApprovedDept(), "Budget")) {
					if (!CPSUtils.isNullOrEmpty(drb.getAmountType())) {
						String[] st = drb.getAmountType().split(",");
						for (int j = 1; j <= st.length; j++) {
							if (CPSUtils.compareStrings(st[i].split("#")[1].toString(), items.getMaterialCode())) {
								items.setAmountTypeId(st[i].split("#")[0]);
							}
						}
					}
				} else if (CPSUtils.isNullOrEmpty(items.getAmountType())) {
					items.setAmountTypeId("0");
				}
				if (CPSUtils.compareStrings(drb.getApprovedDept(), "CFA")) {
					String[] qty = drb.getQty().split(",");
					String[] unitRate = drb.getUnitRate().split(",");
					items.setQty(qty[i]);
					items.setUnitRate(unitRate[i]);
				}
				items.setId(null);
				items.setTaxTypeId("0");
				items.setDemandNo(drb.getDemandNo());
				items.setCreationDate(CPSUtils.getCurrentDate());
				items.setLastModifiedDate(CPSUtils.getCurrentDate());
				if (CPSUtils.isNull(drb.getStageID()) && CPSUtils.isNullOrEmpty(items.getStageId()))
					items.setStageId("1");
				items.setChangedBy(drb.getSfID());
				demanditemslist.add(items);
				total = total + (Float.parseFloat(items.getUnitRate()) * Float.parseFloat(items.getQty()));
			}
			demand.setTotalCost(total.toString());
			demand.setDemandItems(demanditemslist);
			demand.setCreationDate(CPSUtils.getCurrentDateWithTime());
			demand.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			demand.setDemandDate(CPSUtils.getCurrentDateWithTime());
			drb.setDemandItems(demanditemslist);
			demand.setStatus(Integer.parseInt(CPSConstants.DEMANDRAISED));
		} catch (Exception e) {
			throw e;
		}
		return demand;
	}

	/**
	 * This method will be called when a user wants to approve the request
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	public String approvedRequest(DemandRequestBean drb) throws Exception {
		log.debug("::<<<<<DemandRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(DemandRequestBean prb)>>>>>>>>>");
		RequestBean rb = null;
		String message = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(rb, drb);
			rb = requestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(drb, rb);
				if (CPSUtils.compareStrings(drb.getType(), "Demand")) {
					message = demandDomainObject.UpdateDemandStatus(drb.getDemandNo(), CPSConstants.DEMANDSETTLE);
					message = demandDomainObject.updateFundDetails(drb);
				} else {
					message = demandDAO.updateCommittedFund(drb.getTotalCost(), drb.getAccountHeadId(), "debit");
					message = demandDomainObject.UpdateDemandStatus(drb.getDemandNo(), CPSConstants.DEMANDCANCEL);
					message = cancelRequest(rb.getIpAddress(), rb.getRemarks(), rb.getRequestID());
				}
				rb.setMessage(message);

			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}

	public String submitTxnDetails(Object demand) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>submitTxnDetails(RequestBean rb)>>>>>>>>>");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.save(demand);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String setValues(DemandRequestBean drb) throws Exception {
		log.debug("::<<<<<RequestProcess<<<<<<Method>>>>>>>>>>>>>>>setValues(DemandRequestBean drb)>>>>>>>>>");
		String message = null;
		Session session = null;
		String projectcode = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (CPSUtils.compareStrings(drb.getApprovedDept(), CPSConstants.MMG)) {

				String controlNo = drb.getMmgControlNo();
				//Transaction tx = session.beginTransaction();
				session.createQuery("update DemandMasterDTO set mmgControlNo=?,mmgControlDate=? where demandNo=?").setString(0, controlNo).setDate(1, CPSUtils.getCurrentDateWithTime()).setString(2,
						drb.getDemandNo()).executeUpdate();
				session.flush();//tx.commit() ;
				message = CPSConstants.SUCCESS;

			} else if (CPSUtils.compareStrings(drb.getApprovedDept(), CPSConstants.CFA) || CPSUtils.compareStrings(drb.getApprovedDept(), CPSConstants.BUDGET)) {
				DemandMasterDTO demand = new DemandMasterDTO();
				BeanUtils.copyProperties(demand, drb);
				demand = setChangedValues(drb);
				//Transaction tx = session.beginTransaction();
				session.createQuery("update DemandMasterDTO set total_cost=? where demandNo=?").setString(0, demand.getTotalCost()).setString(1, demand.getDemandNo()).executeUpdate();
				session.flush();//tx.commit() ;
				List<DemandItemDetailsDTO> list = demand.getDemandItems();
				for (int i = 0; i < list.size(); i++) {
					DemandItemDetailsDTO demanditems = (DemandItemDetailsDTO) list.get(i);
					int id = Integer.parseInt(demanditems.getStageId()) + 1;
					demanditems.setStageId(String.valueOf(id));
					submitTxnDetails(demanditems);
				}
				message = CPSConstants.SUCCESS;
			} else if (CPSUtils.compareStrings(drb.getApprovedDept(), CPSConstants.FINANCE)) {
				if (!CPSUtils.isNullOrEmpty(drb.getPaymentTypeId())) {
					FundsAllotedDetailsDTO payment = new FundsAllotedDetailsDTO();
					BeanUtils.copyProperties(payment, drb);
					int id = commonDAO.getTableID("MMG_B_FUNDS_ALLOTTED_DETAILS", CPSConstants.UPDATE);
					payment.setId(String.valueOf(id));
					payment.setCreationDate(CPSUtils.getCurrentDateWithTime());
					payment.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
					payment.setStatus("1");
					if (CPSUtils.compareStrings(payment.getPaymentTypeId(), "1"))
						payment.setFundAmount(drb.getFundAmount());
					if (CPSUtils.compareStrings(payment.getPaymentTypeId(), "2"))
						payment.setFundAmount(drb.getSettleAmount());
					submitTxnDetails(payment);
				}
				session.createQuery("update DemandMasterDTO set status=? where demandNo=?").setInteger(0, 4).setString(1, CPSUtils.getCurrentDate()).executeUpdate();
				message = CPSConstants.SUCCESS;

			} else
				message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public void updateRequestIdForDemand(DemandRequestBean drb, String requestId, String type) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "";
			if (CPSUtils.compareStrings(drb.getType(), "Demand")) {
				sql = "update DemandMasterDTO set requestId=? where demandNo=?";
			} else {
				sql = "update DemandMasterDTO set cancelRequestId=?,reason=? where demandNo=?";
			}
			Query qry = session.createQuery(sql);
			if (CPSUtils.compareStrings(drb.getType(), "Demand")) {
				qry.setString(0, requestId).setString(1, drb.getDemandNo()).executeUpdate();
			} else {
				qry.setString(0, requestId).setString(1, drb.getReason()).setString(2, drb.getDemandNo()).executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public String declineRequest(RequestBean rb) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			HashMap<String, Object> resultset = (HashMap<String, Object>) session.createSQLQuery(
					"select account_head_id as accId,total_cost,demand_no as total from MMG_B_DEMAND_MASTER where request_id=?").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setString(0,
					rb.getRequestID()).uniqueResult();
			demandDAO.updateCommittedFund(resultset.get("TOTAL").toString(), resultset.get("ACCID").toString(), "debit");
			demandDomainObject.UpdateDemandStatus(resultset.get("DEMANDNO").toString(), CPSConstants.DEMANDCANCEL);
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String cancelRequest(String ipAddress, String remarks, String cancelrequestId) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String requestId = (String) session.createSQLQuery("select request_id from MMG_B_DEMAND_MASTER where cancel_request_id=?").setString(0, cancelrequestId).uniqueResult();
			String historyID = (String) session.createSQLQuery("select max(id) as historyId from REQUEST_WORKFLOW_HISTORY where request_id=?").addScalar("historyId", Hibernate.STRING).setString(0,
					requestId).uniqueResult();
			requestProcess.declinedRequest(historyID, ipAddress, remarks, CPSConstants.CANCELLED, CPSConstants.STATUSCANCELLED);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

}
