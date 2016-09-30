package com.callippus.web.hrdg.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.hrdg.training.request.beans.dto.HrdgTxnRequestDTO;

@Service
public class SQLCommonDAO implements ICommonDAO {
	private static Log log = LogFactory.getLog(SQLCommonDAO.class);
	private static final long serialVersionUID = 5611041426238862266L;

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	
	public String save(Object obj) throws Exception {
		log.debug("save() --> Start");
		String message = null;
		Session session = null;
		
		try {
			session = hibernateUtils.getSession();
			
			
			session.save(obj);
			session.flush();
			
			message = CPSConstants.SUCCESS;
			
			log.debug("message -->" + message);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			
		}
		log.debug("save() --> End");
		return message;
	}
	
	public String checkEmployee(String sfID) throws Exception {
		Session session = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "from EmployeeBean where sfid=? and status=1";
			Query qry = session.createQuery(sql);
			qry.setString(0, sfID);
			if (CPSUtils.checkList(qry.list())) {
				message = CPSConstants.SUCCESS;
			} else {
				message = CPSConstants.INVALID;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			//session.close();
		}
		return message;
	}

}
