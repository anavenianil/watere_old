package com.callippus.web.business.mmg.cashbuildup.workflow;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.requestprocess.RequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.mmg.cashbuildup.beans.VoucherMasterBean;
import com.callippus.web.mmg.cashbuildup.beans.VoucherRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.VoucherItemDetailsDTO;

@Service
public class VoucherRequestProcess extends RequestProcess {
	private static Log log = LogFactory.getLog(VoucherRequestProcess.class);
	@Autowired
	private RequestProcess requestProcess;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private VoucherDomainObject voucherDomainObject;

	/**
	 * This method executed when the inventory holder generating voucher.
	 * 
	 * @param vouRequestBean
	 * @return
	 * @throws Exception
	 */
	public String initWorkflow(VoucherRequestBean vouRequestBean) throws Exception {
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(VoucherRequestBean vouRequestBean)>>>>>>>>>Start");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			vouRequestBean.setRequestID(requestProcess.generateUniqueID(CPSConstants.REQUEST));
			if (CPSUtils.compareStrings(vouRequestBean.getVoucherTypeId(), "8")) {
				vouRequestBean.setRequestType(CPSConstants.CERTIFICATESANCTION);
				vouRequestBean.setRequestTypeID("11");// Demand Request type ID = 11
			} else {
				vouRequestBean.setRequestType(CPSConstants.VOUCHER);
				vouRequestBean.setRequestTypeID("9");// Demand Request type ID = 9
			}

			/**
			 * When voucher is generated by inventory holder voucher details are saved in vouchermaster and voucheritemdetails tables.
			 */
			VoucherMasterBean vMasterBean = null;
			if (CPSUtils.compareStrings(vouRequestBean.getVoucherTypeId(), "8")) {
				vMasterBean = new VoucherMasterBean();
				BeanUtils.copyProperties(vMasterBean, vouRequestBean);
			} else {
				vMasterBean = setChangedValues(vouRequestBean);
			}

			vMasterBean.setStatus("1");
			vMasterBean.setCreationDate(CPSUtils.getCurrentDate());
			vMasterBean.setLastModifiedDate(CPSUtils.getCurrentDate());
			message = submitTxnDetails(vMasterBean);

			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(rb, vouRequestBean);
				message = requestProcess.initWorkflow(rb);
				/**
				 * If No workflow is assigned then update the details in the table
				 */
				if (CPSUtils.compareStrings(CPSConstants.UPDATE, message)) {
					log.debug("::request workflow not assigned, so update in the main table");
					BeanUtils.copyProperties(rb, vouRequestBean);
					message = voucherDomainObject.updateTxnDetails(vouRequestBean);
				}

			}

		} catch (Exception e) {
			throw e;
		}
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(VoucherRequestBean vouRequestBean)>>>>>>>>>End");
		return message;
	}

	public VoucherMasterBean setChangedValues(VoucherRequestBean vouRequestBean) throws Exception {
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>setChangedValues(VoucherRequestBean vouRequestBean)>>>>>>>>>Start");
		VoucherMasterBean vMasterBean = null;
		List<VoucherItemDetailsDTO> voucherItemslist = null;
		try {
			vMasterBean = new VoucherMasterBean();
			BeanUtils.copyProperties(vMasterBean, vouRequestBean);
			JSONArray json = (JSONArray) vouRequestBean.getItemsJson();
			voucherItemslist = new ArrayList<VoucherItemDetailsDTO>();
			for (int i = 0; i < json.size(); i++) {
				JSONObject itemsjson = (JSONObject) json.get(i);
				VoucherItemDetailsDTO items = new VoucherItemDetailsDTO();
				BeanUtils.copyProperties(items, itemsjson);
				items.setCreationDate(CPSUtils.getCurrentDate());
				items.setLastModifiedDate(CPSUtils.getCurrentDate());
				items.setVoucherId(vouRequestBean.getVoucherId());
				items.setStatus("1");
				voucherItemslist.add(items);
			}
			vMasterBean.setVoucherItems(voucherItemslist);
			vouRequestBean.setVoucherItems(voucherItemslist);
		} catch (Exception e) {
			throw e;
		}
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>setChangedValues(VoucherRequestBean vouRequestBean)>>>>>>>>>End");
		return vMasterBean;
	}

	public String submitTxnDetails(VoucherMasterBean vMasterBean) throws Exception {
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>submitTxnDetails(VoucherMasterBean vMasterBean)>>>>>>>>>Start");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.save(vMasterBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("::<<<<<<Method>>>>>>>>>>>>>>>submitTxnDetails(VoucherMasterBean vMasterBean)>>>>>>>>>End");
		return message;
	}

	public String approvedRequest(VoucherRequestBean vrb) throws Exception {
		log.debug("::<<<<<Method>>>>>>>>>>>>>>>approvedRequest(VoucherRequestBean vrb)>>>>>>>>>Start");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(rb, vrb);
			rb = requestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, vrb);
				if (CPSUtils.compareStrings(vrb.getVoucherTypeId(), "8")) {
					rb.setMessage(voucherDomainObject.certificateSanctionTxnDetails(vrb));
				} else
					rb.setMessage(voucherDomainObject.updateTxnDetails(vrb));
				if (CPSUtils.compareStrings(vrb.getVoucherTypeId(), "5")) {
					vrb.setInventoryNo(vrb.getToInventory());
					rb.setMessage(voucherDomainObject.updateTxnDetails(vrb));
				}

			}
		} catch (Exception e) {
			throw e;
		}
		log.debug("::<<<<<Method>>>>>>>>>>>>>>>approvedRequest(VoucherRequestBean vrb)>>>>>>>>>End");
		return rb.getMessage();
	}

	public VoucherRequestBean getVoucherDetails(RequestBean rb) throws Exception {
		log.debug("::<<<<<Method>>>>>>>>>>>>>>>getVoucherDetails(RequestBean rb)>>>>>>>>>Start");
		Session session = null;
		VoucherRequestBean voucherRequestBean = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String voucherId = (String) session.createSQLQuery("select voucher_id from MMG_B_VOUCHER_MASTER where request_id=?").setString(0, rb.getRequestID()).uniqueResult();
			VoucherMasterBean vmaster = (VoucherMasterBean) session.createCriteria(VoucherMasterBean.class).add(Expression.eq("voucherId", voucherId)).uniqueResult();
			voucherRequestBean = new VoucherRequestBean();
			BeanUtils.copyProperties(voucherRequestBean, vmaster);

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("::<<<<<Method>>>>>>>>>>>>>>>getVoucherDetails(RequestBean rb)>>>>>>>>>End");
		return voucherRequestBean;
	}

}
