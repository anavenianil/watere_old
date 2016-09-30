package com.callippus.web.dao.misc;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.misc.AdminMisc;
import com.callippus.web.beans.passport.MovablePropertyDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLAdminMiscDAOImpl implements IAdminMisc{
	
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;


	public String saveRecord(AdminMisc adminMisc) throws Exception{
		String message="";
		Session session=null;
		
		try {
			session=hibernateUtils.getSession();
			int id=(Integer)session.save(adminMisc);
			message=CPSConstants.SUCCESS;
			return message+"#"+String.valueOf(id);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String saveMovableRecord(MovablePropertyDTO propertyDTO) throws Exception{
		Session session = null;
		Transaction tx=null;
		String message = null;
		try {
			//session = hibernateUtils.getSession();
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx=session.beginTransaction();
			session.saveOrUpdate(propertyDTO);
			session.flush();//tx.commit() ;
			//hibernateUtils.commitTransaction();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	
	public String deletePropertyRecord(int id)throws Exception{
		String message="";
		Session session=null;
		Transaction tx=null;
		try {
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			//tx=session.beginTransaction();
			session.createSQLQuery("update PROPERTY_DETAILS set staus=0 where id="+id);
			session.flush();//tx.commit() ;
			message=CPSConstants.DELETE;
		} catch (Exception e) {
			//tx.rollback();
			message=CPSConstants.DELETEFAIL;
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	
	public List<MovablePropertyDTO> getMovablePropertyDetails(String sfID)throws Exception{
		Session session = null;
		List<MovablePropertyDTO> list=null;
		MovablePropertyDTO propertyDTO=null;
		try {
			list=new ArrayList<MovablePropertyDTO>();
			session = hibernateUtils.getSession();
			//list=(MovablePropertyDTO)session.createCriteria(MovablePropertyDTO.class).add(Expression.not(Expression.in(CPSConstants.STATUS,new Integer[]{Integer.valueOf(CPSConstants.STATUSCANCELLED),Integer.valueOf(CPSConstants.STATUSDECLINED),0}))).add(Expression.eq("sfID", sfID)).setProjection(Projections.projectionList().add(Projections.max("id"))).list();
			propertyDTO=(MovablePropertyDTO)session.createCriteria(MovablePropertyDTO.class).add(Expression.eq("id", session.createCriteria(MovablePropertyDTO.class).setProjection(Projections.max("id")).uniqueResult())).add(Expression.not(Expression.in(CPSConstants.STATUS,new Integer[]{Integer.valueOf(CPSConstants.STATUSCANCELLED),Integer.valueOf(CPSConstants.STATUSDECLINED),0}))).add(Expression.eq("sfID", sfID)).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(propertyDTO))
				list.add(propertyDTO);
			
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
}
