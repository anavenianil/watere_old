package com.callippus.HealthInsuranceDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.healthInsurance.HealthEnrollmentBean;
import com.callippus.healthInsurance.HealthInsuranceDTO;

@Service
public  class  HealthInsuranceDaoImpl implements HealthInsuranceDaoInf  { 
	
	private static Log log = LogFactory.getLog(HealthInsuranceDaoImpl.class);
	private static final long serialVersionUID = 7048899710717627065L;
	
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	

	@SuppressWarnings("unchecked")
	public HealthEnrollmentBean  getHealthInsuranceDetails(HealthEnrollmentBean  healthInsuranceDTO ) throws Exception {
		log.debug("getHealthInsuranceDTO  ---> method start");
		Session session = null;
		List<HealthInsuranceDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			String sql = "select e.sfid as sfid ,e.NAME_IN_SERVICE_BOOK as fullName ,e.DOJ_ASL as joiningDate,e.STATUS as status,d.NAME as designation  from EMP_MASTER e join DESIGNATION_MASTER d on e.DESIGNATION_ID=d.id where e.status=1 and e.sfid  not in (select h.sfid from HEALTH_INSURANCE_SCHEME h)"; 	
			list= session.createSQLQuery(sql).addScalar("sfid", Hibernate.STRING).addScalar("fullName",  Hibernate.STRING).addScalar("joiningDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).addScalar("designation", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(HealthInsuranceDTO.class)).list();
			healthInsuranceDTO.setHealthInsuranceDetails(list);
	} catch (Exception e) {
		//con.rollback();
		throw e;
	}
		return healthInsuranceDTO;
		
}

 
	@SuppressWarnings("unchecked")
	@Override
	public HealthEnrollmentBean getHealthInsuranceEnrollmentDetails(HealthEnrollmentBean  healthInsuranceDTO) throws Exception {
		log.debug("HealthInsuranceDaoImpl  ---> getHealthInsuranceEnrollmentDetails() start");
		Session session = null;
		List<HealthInsuranceDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			String hql = "FROM HealthEnrollmentDTO";
			list = session.createQuery(hql).list(); 
			healthInsuranceDTO.setHealthInsuranceDetails(list);
	} catch (Exception e) {
		throw e;
	}
		return healthInsuranceDTO;
	}
}

