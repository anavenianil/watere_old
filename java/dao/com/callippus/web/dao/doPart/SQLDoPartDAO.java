package com.callippus.web.dao.doPart;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.doPart.DoPartBean;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
@Service
public class SQLDoPartDAO implements IDoPartDAO{
	
	@Autowired
	private HibernateUtils hibernateUtils ;
	
	
	List<DoPartDTO> doPartList=null;
	String message="";
	@SuppressWarnings("unchecked")
	@Override
	public List<DoPartDTO> getDoPartDetails(DoPartBean doPartBean) throws Exception {
		Session session = null;
		String[] gazType=null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if(CPSUtils.isNullOrEmpty(doPartBean.getYear()))
					doPartBean.setYear(CPSUtils.getCurrentYear());
			if(CPSUtils.isNullOrEmpty(doPartBean.getGazType())) {
				gazType=new String[2];
				gazType[0]="G";gazType[1]="NG";
				doPartList=session.createCriteria(DoPartDTO.class).add(Expression.in("status", new Integer[]{1,60})).add(Expression.eq("year",doPartBean.getYear())).addOrder(Order.desc("doPartDate")).add(Expression.in("gazType", gazType)).list();
			}else {
				if(!doPartBean.getGazType().contains("#")){
					gazType=new String[2];
					gazType[0]="G";gazType[1]="NG";
					doPartList=session.createCriteria(DoPartDTO.class).add(Expression.in("status", new Integer[]{1,60})).add(Expression.eq("year",doPartBean.getYear())).addOrder(Order.desc("doPartDate")).add(Expression.in("gazType", gazType)).list();	
				}else{
					gazType=doPartBean.getGazType().split("#");
					doPartList=session.createCriteria(DoPartDTO.class).add(Expression.in("status", new Integer[]{1,60})).add(Expression.eq("year",doPartBean.getYear())).addOrder(Order.desc("doPartDate")).add(Expression.eq("gazType", gazType[0])).add(Expression.eq("typeId", Integer.parseInt(gazType[1]))).list();
				}
			}
			
		} catch (Exception e) {
			throw e;
		} finally {

			//session.close();

		}
		return doPartList; 
}
	@Override
	public String deleteDOPartDetails(int id) throws Exception {
		Session session = null;
		Transaction transaction=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			DoPartDTO doPartDTO=(DoPartDTO)session.get(DoPartDTO.class,id);
			//transaction=session.beginTransaction();
			if(doPartDTO!=null){
				doPartDTO.setDoPartDate(doPartDTO.getDoPartDate());
				doPartDTO.setPreDoPartDate(doPartDTO.getPreDoPartDate());
				doPartDTO.setStatus(0);
				message=CPSConstants.DELETE;
			}else{
				message=CPSConstants.FAILED;
			}
			session.flush() ; //transaction.commit();
		} catch (Exception e) {
			//transaction.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@Override
	public String submitDOPartDetails(DoPartBean doPartBean) throws Exception {
		Session session = null;
		
		DoPartDTO doPartDTO=null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if(!CPSUtils.isNull(doPartBean.getId()) && doPartBean.getId()!=0){
						//update
						doPartDTO=(DoPartDTO)session.get(DoPartDTO.class,doPartBean.getId());
						message=CPSConstants.UPDATE;
					}else{
						// duplicate check
						doPartDTO=(DoPartDTO)session.createCriteria(DoPartDTO.class).add(Expression.eq("doPartNumber", doPartBean.getDoPartNo())).add(Expression.eq("dOPartDate",String.valueOf(doPartBean.getDoPartDate().getYear()+1900))).add(Expression.in("status",new Integer[]{1,60})).uniqueResult();
						if(!CPSUtils.isNullOrEmpty(doPartDTO)){
							message=CPSConstants.DUPLICATE;
						}else{
						//save
						doPartDTO =new DoPartDTO();
						doPartDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
						doPartDTO.setCreatedBy(doPartBean.getSfid());
						message=CPSConstants.SUCCESS;
						}
					}if(message.equalsIgnoreCase(CPSConstants.SUCCESS) || message.equalsIgnoreCase(CPSConstants.UPDATE)){
					doPartDTO.setDescription(doPartBean.getDescription());
					doPartDTO.setDistribution(doPartBean.getDistribution());
					doPartDTO.setGazType(doPartBean.getGazType().split("#")[0]);
					doPartDTO.setDoPartNumber(doPartBean.getDoPartNo());
					doPartDTO.setPreDoPartNo(doPartBean.getPreDoPartNo());
					doPartDTO.setDoPartDate(doPartBean.getDoPartDate());
					doPartDTO.setPreDoPartDate(doPartBean.getPreDoPartDate());
					doPartDTO.setTypeId(Integer.valueOf(doPartBean.getGazType().split("#")[1]));
					doPartDTO.setRequestTypeID("D");
					if(doPartBean.getVerifyFlag().equalsIgnoreCase("y"))
					doPartDTO.setStatus(60);
					else
					doPartDTO.setStatus(1);

					//transaction=session.beginTransaction();

					session.saveOrUpdate(doPartDTO);

					session.flush() ; //transaction.commit();

					}
		} catch (Exception e) {

			//transaction.rollback();

			throw e;
		} finally {

			//session.close();

		}
		return message;
	}
}
