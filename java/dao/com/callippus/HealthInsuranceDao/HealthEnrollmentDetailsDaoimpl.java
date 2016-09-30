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

import com.callippus.healthInsurance.HealthEnrollmentDetailsDTO;
import com.callippus.healthInsurance.HealthInsuranceDTO;

@Service
public class HealthEnrollmentDetailsDaoimpl implements HealthEnrollmentDetailsDaoInf {
	
	private static Log log = LogFactory.getLog(HealthInsuranceDaoImpl.class);
	private static final long serialVersionUID = 7048899710717627065L;
	
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	

	@SuppressWarnings("unchecked")
	public HealthEnrollmentDetailsDTO  getHealthEnrollmentDetails(HealthEnrollmentDetailsDTO  healthEnrollmentDetailsDTO ) throws Exception {
		log.debug("getHealthInsuranceDTO  ---> method start");
		//List<HealthInsuranceDTO> list =null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		List list = null;
		try {
			session = hibernateUtils.getSession();
			con = session.connection();
			String sql = "select  SFID as sfid ,EMP_NAME as fullName ,DESIGNATION as designation , JOINING_DATE as joiningDate , STATUS as status ,ENROLLMENTDATE as fromDate from HEALTH_INSURANCE_SCHEME  where status=1 "; 
    
	
			list= session.createSQLQuery(sql).addScalar("sfid", Hibernate.STRING).addScalar("fullName",  Hibernate.STRING).addScalar("joiningDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).addScalar("designation", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(HealthEnrollmentDetailsDTO.class)).list();
			System.out.println(list.toString());
			healthEnrollmentDetailsDTO.setHealthEnrollmentDetails(list);
			


				
				
				
				
	} catch (Exception e) {
		//con.rollback();
		throw e;
	}
		return healthEnrollmentDetailsDTO;
	}
}

