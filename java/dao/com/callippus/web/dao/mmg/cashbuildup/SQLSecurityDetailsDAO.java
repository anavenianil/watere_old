package com.callippus.web.dao.mmg.cashbuildup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.mmg.cashbuildup.dto.SecurityDetailsDTO;
import com.callippus.web.mmg.cashbuildup.dto.StockDetailsDTO;

@SuppressWarnings("serial")
@Service
public class SQLSecurityDetailsDAO implements ISecurityDAO, Serializable {

	private static Log log = LogFactory.getLog(SQLSecurityDetailsDAO.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public List<SecurityDetailsDTO> getDemandNumber() throws Exception {
		log.debug("getDemandNumbers()-->start");
		Session session = null;
		List demandNoList = null;
		Transaction tx = null;
		try {
			demandNoList = new ArrayList<SecurityDetailsDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "select dm.demandNo as demandNo,dm.demandDate as  demandDate from DemandMasterDTO dm where status=?";
			log.debug("query --> " + sql);
			demandNoList = session.createQuery(sql).setString(0, CPSConstants.DEMANDSETTLE).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getDemandNumbers()-->End");
		return demandNoList;
	}

	public String saveSecurityCheckDetails(SecurityDetailsDTO itemsAtSecurity) throws Exception {
		log.debug("::SQLSecurityDetailsDAO>>>>>>>>>>>>>>>saveSecurityCheckDetails(security)) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String message = "";
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(itemsAtSecurity);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;

		} catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}

		log.debug("::SQLSecurityDetailsDAO>>>>>>>>>>>>>>>saveSecurityCheckDetails(security)) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getSecurityItemDetails(String demandNo) throws Exception {
		log.debug("::SQLSecurityDetailsDAO>>>>>>>>>>>>>>>getSecurityItemDetails(demandNo) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		List<Object> securityItemsList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select material.material_name as description,security.qty as qty,security.uom as uom,security.material_code as materialCode,"
					+ " security.MEMO_NO as memoNo,security.MEMO_DATE as memoDate from MMG_B_SECURITY_DETAILS security,MMG_B_MATERIAL_MASTER material "
					+ " where security.MATERIAL_CODE=material.id and security.DEMAND_NO=?";
			securityItemsList = session.createSQLQuery(sql).addScalar("memoDate", Hibernate.DATE).addScalar("description", Hibernate.STRING).addScalar("qty", Hibernate.STRING).addScalar(
					"materialCode", Hibernate.STRING).addScalar("uom", Hibernate.STRING).addScalar("memoNo", Hibernate.STRING).setString(0, demandNo).setResultTransformer(
					Transformers.aliasToBean(SecurityDetailsDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("::SQLSecurityDetailsDAO>>>>>>>>>>>>>>>getSecurityItemDetails(demandNo) End>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return securityItemsList;
	}

	public String updateStockDetails(String demandNo, String materialCode, String receivedqty, int id) throws Exception {
		log.debug("::SQLSecurityDetailsDAO>>>>>>>>>>>>>>>updateStockDetails(demandNo) Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		StockDetailsDTO stock = null;
		Session session = null;
		Transaction tx = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String inventoryNo = (String) session.createQuery("select inventoryNo from DemandMasterDTO where demandNo=?").setString(0, demandNo).uniqueResult();
			stock = (StockDetailsDTO) session.createCriteria(StockDetailsDTO.class).add(Expression.eq("materialCode", materialCode)).add(Expression.eq("inventoryNo", inventoryNo)).uniqueResult();
			if (!CPSUtils.isNull(stock)) {
				String sql = "update StockDetailsDTO set stockHeld=? where materialCode=? and inventoryNo=?";
				int qty = Integer.parseInt(stock.getStockHeld());
				qty = qty + Integer.parseInt(receivedqty);
				//tx = session.beginTransaction();
				session.createQuery(sql).setString(0, String.valueOf(qty)).setString(1, materialCode).setString(2, inventoryNo).executeUpdate();
				session.flush();//tx.commit() ;
				message = CPSConstants.UPDATE;
			} else {
				stock = new StockDetailsDTO();
				stock.setInventoryNo(inventoryNo);
				stock.setId(String.valueOf(id));
				stock.setCreationDate(CPSUtils.getCurrentDate());
				stock.setLastModifiedDate(CPSUtils.getCurrentDate());
				//tx = session.beginTransaction();
				session.save(stock);
				session.flush();//tx.commit() ;
				message = CPSConstants.INSERT;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
}
