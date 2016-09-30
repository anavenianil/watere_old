package com.callippus.web.dao.higherQualification;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.higherQualification.HQBean;
import com.callippus.web.beans.higherQualification.dto.HQDetailsDTO;
import com.callippus.web.beans.higherQualification.dto.HQSanctionDTO;
@Service
public class SQLHqDao implements IHQDao{
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Override
	public HQBean getHQDetails(HQBean hqBean) throws Exception {
		Session session = null;
		Transaction transaction=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//transaction=session.beginTransaction();
			HQDetailsDTO hqDetailsDTO=(HQDetailsDTO)session.createCriteria(HQDetailsDTO.class).add(Expression.eq("sfID", hqBean.getSfID())).uniqueResult();
		/*	hqBean=(HQBean) session.createSQLQuery("select emp.name_in_service_book name,emp.sfid sfID,dm.name designation,emp.dob dob,emp.doj_drdo doj,rm.id,rm.name religion,qm.name course,dsm.department_name departmentName " +
					"from emp_master emp,designation_master dm,religion_master rm,qualification_details qd,qualification_master qm,departments_master dsm,org_role_instance ori where dm.id=emp.designation_id " +
					"and emp.sfid=? and emp.religion_id=rm.id and qd.qualification_id=qm.id and qd.sfid=emp.sfid and qm.status=1 and qd.status=1  and ori.org_role_id=emp.office_id and ori.department_id=dsm.department_id and ori.status=1 and dsm.status=1").addScalar("departmentName").addScalar("dob",Hibernate.DATE).addScalar("doj",Hibernate.DATE).addScalar("course").addScalar("designation").addScalar("sfID").addScalar("name").setString(0,hqBean.getSfID()).setResultTransformer(Transformers.aliasToBean(HQBean.class)).uniqueResult();
			*/hqBean.setHqDetailsDTO(hqDetailsDTO);
			session.flush() ; //transaction.commit();
		} catch (Exception e) {
			//transaction.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return hqBean;
	}
	@Override
	public HQBean getSanctionedHQDetails(HQBean hqBean) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//transaction=session.beginTransaction();
			HQSanctionDTO hqSanctionDTO=(HQSanctionDTO)session.createCriteria(HQSanctionDTO.class).add(Expression.eq("sfID", hqBean.getSfID())).uniqueResult();
			hqBean.setHqSanctionDTO(hqSanctionDTO);
			session.flush() ; //transaction.commit();
		}catch(Exception e){
			//transaction.rollback();
			throw e;
		}
		finally{
			//session.close();
		}
		return hqBean;
	}

}
