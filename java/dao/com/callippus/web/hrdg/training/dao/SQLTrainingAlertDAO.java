package com.callippus.web.hrdg.training.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLTrainingAlertDAO implements ITrainingAlertDAO{
	private static Log log = LogFactory.getLog(SQLTrainingAlertDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;

	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	
	public String submitAlert(Object obj) throws Exception
	{
		log.debug("submitAlert() --> Start");
		String message = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();
			
			session.save(obj);
			

			message = CPSConstants.SUCCESS;
			log.debug("message -->" + message);
			session.flush();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("save() --> End");
		return message;
	}
	

}
