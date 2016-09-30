package com.callippus.web.business.domainobject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class QuarterDomainObject {
@Autowired
com.callippus.web.controller.common.HibernateUtils hibernateUtils;


public String updateTxnDetails(QuarterRequestBean processBean) throws Exception {
	Session session=null;
	Transaction transaction=null;
	try{
//VN     session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		session = hibernateUtils.getSession();

		QuarterRequestBean quarterRequestBean=(QuarterRequestBean)session.createCriteria(QuarterRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, processBean.getRequestID())).uniqueResult();
		////transaction=session.beginTransaction();
		quarterRequestBean.setStatus(processBean.getStatus());
		session.flush();
		//session.flush() ; //transaction.commit();
		processBean.setResult(CPSConstants.SUCCESS);
	}catch (Exception e) {
		////transaction.rollback();
		throw e;	
	}finally{
		////session.close();
	}
	return processBean.getResult();
}
}
