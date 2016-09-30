package com.callippus.web.dao.higherQualification;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.higherQualification.HQRequestBean;
@Service
public class SQLHQRequestDao implements IHQRequestDao{
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Override
	public HQRequestBean getHQRequestHome(String sfID) throws Exception {
		Session session = null;
		Transaction transaction=null;
		HQRequestBean hqRequestBean=null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//transaction=session.beginTransaction();
			hqRequestBean=(HQRequestBean) session.createSQLQuery("select emp.name_in_service_book name,emp.sfid sfID,TO_CHAR(dm.id) desigID" +
					",dm.name designation,emp.dob dob,emp.doj_drdo doj,rm.id,rm.name religion,qm.name course,dsm.department_name departmentName " +
					"from emp_master emp,designation_master dm,religion_master rm,qualification_details qd,qualification_master qm,departments_master dsm,org_role_instance ori where dm.id=emp.designation_id " +
					"and emp.sfid=? and emp.religion_id=rm.id and qd.qualification_id=qm.id and qd.sfid=emp.sfid and qm.status=1 and qd.status=1  and ori.org_role_id=emp.office_id and ori.department_id=dsm.department_id and ori.status=1 and dsm.status=1 and qd.year=(select max(year) from qualification_details where sfid=emp.sfid)").addScalar("departmentName").addScalar("dob",Hibernate.DATE).addScalar("doj",Hibernate.DATE).addScalar("course").addScalar("designation").addScalar("sfID").addScalar("name").addScalar("desigID",Hibernate.STRING).setString(0,sfID).setResultTransformer(Transformers.aliasToBean(HQRequestBean.class)).uniqueResult();
			session.flush() ; //transaction.commit();
		} catch (Exception e) {
			//transaction.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return hqRequestBean;
	}

}
