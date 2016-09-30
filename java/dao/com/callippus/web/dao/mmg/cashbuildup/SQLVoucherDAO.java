package com.callippus.web.dao.mmg.cashbuildup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.beans.IRMasterBean;
import com.callippus.web.mmg.cashbuildup.dto.IRItemDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.VoucherTypeDTO;

@Service
public class SQLVoucherDAO implements IVoucherDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLVoucherDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public List<VoucherTypeDTO> getVoucherTypes() throws Exception {
		log.debug("getVoucherTypes method ----> Start");
		Session session = null;
		Transaction tx = null;
		List<VoucherTypeDTO> voucherTypes = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			voucherTypes = new ArrayList<VoucherTypeDTO>();
			Criteria crt = session.createCriteria(VoucherTypeDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("name"));

			voucherTypes = crt.list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getVoucherTypes method ----> End");
		return voucherTypes;
	}

	public String getVoucherId(String beanName, String fieldName) throws Exception {
		log.debug("getVoucherId method ----> Start");
		Session session = null;
		Transaction tx = null;
		String vId = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			Object obj = session.createQuery("select max(substr(" + fieldName + ",length(" + fieldName + ")-3,length(" + fieldName + "))) from " + beanName).uniqueResult();
			if (CPSUtils.isNullOrEmpty(obj))
				vId = "0000";
			else
				vId = obj.toString();

			// vId=crt.list().get(0).toString();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getVoucherId method ----> End");
		return vId;
	}

	@SuppressWarnings("unchecked")
	public List<InventoryMasterDTO> getToInventoryNumsList() throws Exception {
		List<InventoryMasterDTO> list = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			list = session.createCriteria(InventoryMasterDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("inventoryNo")).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public IRMasterBean getSecurityDetails(String demandNo, String status) throws Exception {
		List<Object> list = null;
		Session session = null;
		Transaction tx = null;
		DemandMasterDTO demand = null;
		IRMasterBean invoice = null;
		List<IRItemDetailsDTO> itemslist = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			demand = (DemandMasterDTO) session.createQuery("from DemandMasterDTO m where m.status=? and demandNo=?").setString(0, status).setString(1, demandNo).uniqueResult();
			String sql = "select security.demand_no as demandNo,mmd.id as materialCode,mmd.MATERIAL_NAME as description,mmd.CONSUMABLE_FLAG as cflag,ditem.UNIT_RATE as unitRate,security.memo_no as memoNo,ditem.uom as uom, "
					+ " security.memo_date as memodate,security.QTY as qty from MMG_B_MATERIAL_MASTER mmd,MMG_B_SECURITY_DETAILS security,MMG_B_DEMAND_ITEM_DETAILS ditem where "
					+ " ditem.MATERIAL_CODE=mmd.id and ditem.demand_no=?  and mmd.status=1 and security.demand_no=ditem.demand_no and security.material_code=ditem.material_code and ditem.stage_id=(select max(stage_id) from MMG_B_DEMAND_ITEM_DETAILS where demand_no=?)";
			list = session.createSQLQuery(sql).addScalar("materialCode").addScalar("description").addScalar("cflag").addScalar("memoNo").addScalar("memodate").addScalar("uom").addScalar("unitRate")
					.addScalar("qty").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setString(0, demandNo).setString(1, demandNo).list();
			session.flush();//tx.commit() ;
			invoice = new IRMasterBean();
			BeanUtils.copyProperties(invoice, demand);
			itemslist = new ArrayList<IRItemDetailsDTO>();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) list.get(i);
				IRItemDetailsDTO items = new IRItemDetailsDTO();
				BeanUtils.copyProperties(items, map);
				items.setMemoDate(CPSUtils.formattedDate(map.get("memodate").toString()));
				itemslist.add(items);
			}
			invoice.setIrItems(itemslist);
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return invoice;
	}

	@SuppressWarnings("unchecked")
	public List<IRMasterBean> getInvoiceNumbers(String status) throws Exception {
		log.debug("getInvoiceNumbers() --> Start");
		Session session = null;
		List<IRMasterBean> invoiceList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			invoiceList = session.createQuery("from IRMasterBean m where m.status=?").setString(0, status).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return invoiceList;
	}
}
