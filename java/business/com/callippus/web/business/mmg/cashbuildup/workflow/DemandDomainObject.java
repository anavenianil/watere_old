package com.callippus.web.business.mmg.cashbuildup.workflow;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.business.domainobject.DomainObject;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.mmg.cashbuildup.beans.DemandRequestBean;
import com.callippus.web.mmg.cashbuildup.dto.AccountHeadDTO;
import com.callippus.web.mmg.cashbuildup.dto.DemandItemDetailsDTO;

@Service
public class DemandDomainObject extends DomainObject {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	
	/**
	 * This method will update the stock details
	 * 
	 * @param prb
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String updateFundDetails(DemandRequestBean drb) throws Exception {

		Session session = null;
		Transaction tx = null;
		List<DemandItemDetailsDTO> demandItems = null;
		String message = "";

		try {
			//VN         session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			session = hibernateUtils.getSession();


			if (!CPSUtils.isNull(drb.getDemandItems()))
				demandItems = drb.getDemandItems();
			else {
				String sql = "from DemandItemDetailsDTO where demandNo=?";
				demandItems = (List<DemandItemDetailsDTO>) session.createQuery(sql).setString(0, drb.getDemandNo()).list();
			}
			Float amount = 0.0f;
			for (int i = 0; i < demandItems.size(); i++) {
				DemandItemDetailsDTO demandedItems = (DemandItemDetailsDTO) demandItems.get(i);
				amount += Float.parseFloat(demandedItems.getAmount());
			}
			AccountHeadDTO account = (AccountHeadDTO) session.createCriteria(AccountHeadDTO.class).add(Expression.eq("accId", drb.getAccountHeadId())).uniqueResult();
			Float total = 0.0f;
			Float commit = 0.0f;
			if (!CPSUtils.isNull(account.getConsumedFund()))
				total = Float.parseFloat(account.getConsumedFund()) + amount;
			commit = Float.parseFloat(account.getConsumedFund()) - amount;
			String sql = "update AccountHeadDTO set consumedFund=?,commitedFund=? where id=?";
			////tx = session.beginTransaction();
			session.createQuery(sql).setString(0, String.valueOf(total)).setString(1, String.valueOf(commit)).setString(2, drb.getAccountHeadId()).executeUpdate();
			session.flush();
			//session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			////session.close();
		}
		return message;
	}

	public String UpdateDemandStatus(String demandNo, String status) throws Exception {
		Session session = null;
		String message = "";
		try {
//VN         session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			session = hibernateUtils.getSession();

			session.createQuery("update DemandMasterDTO set status=? where demandNo=?").setString(0, status).setString(1, demandNo).executeUpdate();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		} finally {
			////session.close();
		}
		return message;
	}
}
