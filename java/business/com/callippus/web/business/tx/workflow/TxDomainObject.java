package com.callippus.web.business.tx.workflow;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.HibernateUtils;

@Service
public class TxDomainObject {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	public int getTableID(String tableName) throws Exception {
		List<String> masterObjList = null;
		Session session = null;
		Transaction tx = null;
		int id = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();

			masterObjList = session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE").setString(0, tableName).list();
			id = Integer.parseInt(masterObjList.get(0).toString()) + 1;

			session.createSQLQuery("update id_generator set value=? where table_name=?").setInteger(0, id).setString(1, tableName).executeUpdate();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return id;
	}
}