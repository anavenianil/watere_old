package com.callippus.web.controller.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateUtils {
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Log log = LogFactory.getLog("com.callippus.web.controller.common.HibernateUtils");
	
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	public void createSession(Session session) {
		try {
			log.debug("Session Id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "session is closed") : "null"));
			threadSession.set(session);
			beginTransaction(session);
			log.debug("HibernateUtils ----> createSession() ::::::");
		} catch(Exception e) {
			e.printStackTrace();
			log.debug("HibernateUtils---->createSession() **** WARNING UNABLE TO CREATE SESSION *****");
		}
	}
	
	public Session getSession() throws Exception {
		System.out.println("HibernateUtils ---> getSession() -----> sessionFactory Id : " + sessionFactory.hashCode());
		log.debug("HibernateUtils ---> getSession() -----> sessionFactory Id : " + sessionFactory.hashCode()); 
		
		Session session = (Session) threadSession.get();
		log.debug("HibernateUtils---->getSession() START : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		
		if (session == null || !session.isOpen()) {
			log.debug("HibernateUtils ---> getSession() **** WARNING SESSION IS NULL OR NOT OPEN *****");
			throw new Exception("Session is null");
		}
		log.debug("HibernateUtils ---> getSession() END : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		
		return session;
	}

	public void beginTransaction(Session session) {
		log.debug("HibernateUtils ---> beginTransaction() START : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		Transaction tx = session.beginTransaction();
		threadTransaction.set(tx);
		log.debug("HibernateUtils ---> beginTransaction() END : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
	}

	//Dummy--> flush added 
	public void closeSession() {
		Session session = (Session) threadSession.get();
		if (session != null && session.isOpen()){
			session.flush();
		}
	}
	
	public void closeSessionNew() throws Exception {
		Session session = (Session) threadSession.get();
		log.debug("HibernateUtils ---> closeSession() START : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		
		if (session != null && session.isOpen()) {
			try {
				commitTransaction();
			} catch (Exception e) {
				log.debug("HibernateUtils ---> closeSession() **** WARNING UNABLE TO COMMIT TXN*****");
				e.printStackTrace();
				throw e;
			} finally {			
				session.close();
				log.debug("HibernateUtils ---> closeSession() ---> commit::::::");
				threadSession.set(null);
				session = null;
			}
		} else {
			log.debug("HibernateUtils ---> closeSession() **** WARNING SESSION IS NULL OR NOT OPEN *****");
			threadSession.set(null);
			session = null;
			log.debug("HibernateUtils ---> closeSession() END : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		}
	}

	public void commitTransaction() {
		Session session = (Session) threadSession.get();
		log.debug("HibernateUtils ---> commitTransaction() START : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		Transaction tx = (Transaction) threadTransaction.get();
		if (tx != null) {
			tx.commit();
			threadTransaction.set(null);
		} else {
			log.debug("HibernateUtils ---> commitTransaction() **** WARNING TX IS NULL*****");
		}
		log.debug("HibernateUtils ---> commitTransaction() END : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
	}
	//Dummy--> middle used-- added 
	public static void rollbackTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
			tx.rollback();
			tx = null;
			threadTransaction.set(tx);
		}
	}
	
	public static void rollbackTransactionNew() {
		log.debug("HibernateUtils ---> rollbackTransactionNew() start*********");
		
		Transaction tx = (Transaction) threadTransaction.get();
		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
			tx.rollback();
			threadTransaction.set(null);
		} else {
			log.debug("HibernateUtils ---> rollbackTransactionNew() **** WARNING TX IS NULL*****");
		}
		Session session = (Session) threadSession.get();
		log.debug("HibernateUtils---->rollbackTransactionNew() start : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		
		if (session != null && session.isOpen()) {
			session.close();
			log.debug("HibernateUtils ---> rollbackTransactionNew()--> ::::::");
			System.out.println("HibernateUtils---->rollbackTransaction()-->::::::");
		} else {
			log.debug("HibernateUtils ---> rollbackTransactionNew() **** WARNING SESSION IS NULL*****");
		}
		threadSession.set(null);
		session = null;
		log.debug("HibernateUtils---->rollbackTransactionNew() END : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
	}

	public Session openNewSession1() {
		log.debug("HibernateUtils ---> openNewSession() start*******");
		
		Session session = sessionFactory.openSession(); 
		log.debug("HibernateUtils---->openNewSession() END : session id : " + (session != null ? (session.isOpen() ? session.connection().hashCode() : "*****session is not null but it is closed*****") : "null"));
		
		return session;
	}
}