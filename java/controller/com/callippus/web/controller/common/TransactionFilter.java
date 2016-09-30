package com.callippus.web.controller.common;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class TransactionFilter extends OncePerRequestFilter {

	public static Log log = LogFactory.getLog(TransactionFilter.class);

	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		com.callippus.web.controller.common.HibernateUtils hibernateUtils = new HibernateUtils();
		Session session = null;
			try {
				ServletContext servletContext = request.getSession().getServletContext();
				WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
				SessionFactory sessionFactory = (SessionFactory) autowireCapableBeanFactory.getBean(SessionFactory.class);
				
				System.out.println("TransactionFilter -----> sessionFactory Id START : " + sessionFactory.hashCode());
				log.debug("TransactionFilter -----> sessionFactory Id START : " + sessionFactory.hashCode());
				
				session = sessionFactory.openSession();
				
				log.debug("TransactionFilter -----> session Id Before doFilter() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
				System.out.println("TransactionFilter -----> session Id Before doFilter() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
				
				hibernateUtils.createSession(session);
				filterChain.doFilter(request, response);
				
				log.debug("TransactionFilter -----> session Id After doFilter() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
				System.out.println("TransactionFilter -----> session Id After doFilter() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
				
				hibernateUtils.closeSessionNew();
				
				log.debug("TransactionFilter -----> session Id After closeSessionNew() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed Successfully")));
				System.out.println("TransactionFilter -----> session Id After closeSessionNew() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed Successfully")));
				
				System.out.println("TransactionFilter -----> sessionFactory Id END : " + sessionFactory.hashCode());
				log.debug("TransactionFilter -----> sessionFactory Id END : " + sessionFactory.hashCode());
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("TransactionFilter -----> session Id Before rollbackTransactionNew() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
				System.out.println("TransactionFilter -----> session Id Before rollbackTransactionNew() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
				
				HibernateUtils.rollbackTransactionNew();
				
				log.debug("TransactionFilter -----> session Id After rollbackTransactionNew() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
				System.out.println("TransactionFilter -----> session Id After rollbackTransactionNew() : " + ((session == null) ? "null" : (session.isOpen() ? session.connection().hashCode() : "session is closed")));
			}
			return;
		}
}