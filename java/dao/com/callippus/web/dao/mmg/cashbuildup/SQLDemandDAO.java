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
import com.callippus.web.mmg.cashbuildup.beans.DemandMasterDTO;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandTypeDTO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;

@SuppressWarnings("serial")
@Service
public class SQLDemandDAO implements IDemandDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLDemandDAO.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public DemandMasterDTO getDemandDetails(String demandNo) throws Exception {
		Session session = null;
		DemandMasterDTO demand = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			demand = (DemandMasterDTO) session.createCriteria(DemandMasterDTO.class).add(Expression.eq("demandNo", demandNo)).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return demand;
	}

	public String saveDemand(Object demand) throws Exception {
		Session session = null;
		String msg = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//Transaction tx = session.beginTransaction();
			session.save(demand);
			session.flush();//tx.commit() ;
			msg = CPSConstants.SUCCESS;
		} catch (Exception e) {
			msg = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return msg;
	}

	@SuppressWarnings("unchecked")
	public List<DemandTypeDTO> getDemandTypes() throws Exception {
		Session session = null;
		List<DemandTypeDTO> demandTypes = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			demandTypes = (List<DemandTypeDTO>) session.createSQLQuery(
					"select id,demand_type_name as demandTypeName,demand_description as demandDesc,status,creation_date as creationDate,last_modified_date as lastModifiedDate from MMG_B_DEMAND_TYPE")
					.addScalar("id", Hibernate.STRING).addScalar("demandTypeName", Hibernate.STRING).addScalar("demandDesc", Hibernate.STRING).addScalar("creationDate", Hibernate.STRING).addScalar(
							"lastModifiedDate", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DemandTypeDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return demandTypes;
	}

	public int checkInvHolderItem(String inventoryNo, String materialCode) throws Exception {
		Session session = null;
		Transaction tx = null;
		int qty = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			Object obj = session.createQuery("select sum(stockHeld) from StockDetailsDTO where inventoryNo=? and materialCode=? and status=1").setString(0, inventoryNo).setString(1, materialCode)
					.uniqueResult();
			session.flush();//tx.commit() ;
			if (!CPSUtils.isNullOrEmpty(obj))
				qty = Integer.parseInt(obj.toString());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return qty;
	}

	@SuppressWarnings("unchecked")
	public List<DemandMasterDTO> getDraftDetails(String status, String sfid) throws Exception {
		Session session = null;
		List<DemandMasterDTO> demandList = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			demandList = session.createCriteria(DemandMasterDTO.class).add(Expression.eq("status", Integer.parseInt(status))).add(Expression.eq("raisedBy", sfid)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return demandList;
	}

	public List<DemandMasterDTO> getDemandNumber() throws Exception {
		log.debug("getDemandNumbers()-->start");
		Session session = null;
		List demandNoList = null;
		Transaction tx = null;
		try {
			demandNoList = new ArrayList<DemandMasterDTO>();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String sql = "select dm.demandNo as demandNo from DemandMasterDTO dm";
			log.debug("query --> " + sql);
			demandNoList = session.createQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getDemandNumbers()-->End");
		return demandNoList;
	}

	public String updateCommittedFund(String amount, String accountHead, String flag) throws Exception {
		log.debug("updateCommittedFund()-->start");
		Session session = null;
		Transaction tx = null;
		String message = "";
		Float commitFund = 0.0f;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String committedFund = (String) session.createQuery("select commitedFund from AccountHeadDTO where accId=?").setString(0, accountHead).uniqueResult();
			if (CPSUtils.compareStrings(flag, "credit")) {
				commitFund = Float.parseFloat(committedFund) + Float.parseFloat(amount);
			} else {
				commitFund = Float.parseFloat(committedFund) - Float.parseFloat(amount);
			}

			//tx = session.beginTransaction();
			session.createQuery("update AccountHeadDTO set commitedFund=? where accId=?").setString(0, String.valueOf(commitFund)).setString(1, accountHead).executeUpdate();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public AccountHeadDTO checkingAccountHeadAmt(String accId) throws Exception {
		log.debug("checkingAccountHeadAmt()-->start");
		AccountHeadDTO accdto = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			accdto = (AccountHeadDTO) session.createCriteria(AccountHeadDTO.class).add(Expression.eq("accId", accId)).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return accdto;
	}

	public InventoryMasterDTO getInventoryHolder(String invId) throws Exception {
		log.debug("checkingAccountHeadAmt()-->start");
		InventoryMasterDTO invdto = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			invdto = (InventoryMasterDTO) session.createCriteria(InventoryMasterDTO.class).add(Expression.eq("invId", invId)).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return invdto;
	}

	public String deleteDemandItems(String demandNo) throws Exception {
		log.debug("deleteDemandItems()-->start");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "delete from mmg_b_demand_item_details where demand_no=?";
			//tx = session.beginTransaction();
			session.createSQLQuery(sql).setString(0, demandNo).executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String updateDemandAmount(String demandNo, String amount) throws Exception {
		log.debug("deleteDemandItems()-->start");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "update mmg_b_demand_master set total_cost=? where demand_no=?";
			//tx = session.beginTransaction();
			session.createSQLQuery(sql).setString(0, amount).setString(1, demandNo).executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String deleteDemand(String demandNo) throws Exception {
		log.debug("deleteDemandItems()-->start");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "delete from mmg_b_demand_master where demand_no=?";
			//tx = session.beginTransaction();
			session.createSQLQuery(sql).setString(0, demandNo).executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String getDemandLimit() throws Exception {
		log.debug("getDemandLimit()-->start");
		String demandLimit = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select value from configuration_details where name='DEMAND_LIMIT'";
			//tx = session.beginTransaction();
			demandLimit = (String) session.createSQLQuery(sql).uniqueResult();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return demandLimit;
	}
}
