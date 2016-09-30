package com.callippus.HealthInsuranceDao;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.healthInsurance.HealthEnrollmentBean;
import com.callippus.healthInsurance.HealthEnrollmentDTO;
import com.callippus.healthInsurance.HealthInsuranceDTO;
import com.callippus.healthInsurance.HealthSubscriptionDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;

@Service
public class HealthEnrollmentDaoImpl implements HealthEnrollmentDaoInf,
		Serializable {
	private static final long serialVersionUID = -7753238725888858447L;

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@SuppressWarnings("unchecked")
	@Override
	public String saveCdaAmont(JSONObject subJson, HealthEnrollmentBean bean)
			throws Exception {
		String message = null;
		Session session = null;
		// EmployeeClaimDetailsDTO employeeClaimDetailsDTO=null;
		// FinanceDetailsDTO financeDetails=null;
		HealthInsuranceDTO dto = null;
		try {
			session = hibernateUtils.getSession();
			// dto = (HealthInsuranceDTO)
			// session.createCriteria(HealthInsuranceDTO.class).list();
			HealthEnrollmentDTO hdto = new HealthEnrollmentDTO();
			if (!CPSUtils.isNullOrEmpty(subJson.get("sfid").toString())) {
				hdto.setSfid(subJson.getString("sfid").toString());
			}
			if (!CPSUtils.isNullOrEmpty(subJson.get("fullName").toString())) {
				hdto.setFullName(subJson.getString("fullName").toString());
			}
			if (!CPSUtils.isNullOrEmpty(subJson.get("Desg").toString())) {
				hdto.setDesignation(subJson.getString("Desg").toString());
			}
			if (!CPSUtils.isNullOrEmpty(subJson.get("status").toString())) {
				hdto.setStatus(Integer
						.valueOf(subJson.get("status").toString()));
			}

			if (!CPSUtils.isNullOrEmpty(subJson.get("Doj").toString())) {
				hdto.setJoiningDate(CPSUtils.convertStringToDate1(subJson.get(
						"Doj").toString()));
			}
			if (!CPSUtils.isNullOrEmpty(subJson.get("EnrollmentDate")
					.toString())) {
				hdto.setFromDate(CPSUtils.convertStringToDate2(subJson.get(
						"EnrollmentDate").toString()));

			}

			/*
			 * if(!CPSUtils.isNullOrEmpty(subJson.get("Doj").toString())){
			 * hdto.setJoiningDate
			 * (CPSUtils.convertStringToDate(subJson.get("Doj").toString())); }
			 * if
			 * (!CPSUtils.isNullOrEmpty(subJson.get("EnrollmentDate").toString(
			 * ))){ hdto.setFromDate(CPSUtils.convertStringToDate(subJson.get(
			 * "EnrollmentDate").toString()));
			 * 
			 * }
			 */

			session.clear();
			session.save(hdto);
			session.flush();

			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public String getHealthInsEmpList(HealthEnrollmentBean healthInsuranceBean)
			throws Exception {
		String message = null;
		Session session = null;
		session = hibernateUtils.getSession();
		// String
		// sql="select his.SFID AS sfid ,his.EMP_NAME AS fullName ,his.DESIGNATION AS designation,his.JOINING_DATE AS joiningDate,his.ENROLLMENTDATE AS fromDate from HEALTH_INSURANCE_SCHEME  his where STATUS=1 and ( UPPER(SFID) LIKE UPPER('%"+healthInsuranceBean.getNameOrSfid()+"%') or UPPER(EMP_NAME) LIKE UPPER('%"+healthInsuranceBean.getNameOrSfid()+"%')) ";

		String sql = "select his.SFID AS sfid ,his.EMP_NAME AS fullName ,his.DESIGNATION AS designation,his.JOINING_DATE AS joiningDate, "
				+ "his.ENROLLMENTDATE AS fromDate ,(select BASIC_PAY from EMP_BASIC_PAY_HISTORY where status=1 and SFID=his.SFID ) AS basicPay "
				+ " from HEALTH_INSURANCE_SCHEME  his where STATUS=1 and ( UPPER(SFID) LIKE UPPER('%"
				+ healthInsuranceBean.getNameOrSfid()
				+ "%') or UPPER(EMP_NAME) LIKE UPPER('%"
				+ healthInsuranceBean.getNameOrSfid() + "%')) ";
		healthInsuranceBean.setHealthEnrollmentDTO(session
				.createSQLQuery(sql)
				.addScalar("sfid", Hibernate.STRING)
				.addScalar("fullName", Hibernate.STRING)
				.addScalar("designation", Hibernate.STRING)
				.addScalar("joiningDate", Hibernate.DATE)
				.addScalar("fromDate", Hibernate.DATE)
				.addScalar("basicPay", Hibernate.INTEGER)
				.setResultTransformer(
						Transformers.aliasToBean(HealthEnrollmentDTO.class))
				.list());

		if (healthInsuranceBean.getHealthEnrollmentDTO().size() == 0) {
			message = "listhavedata";
		}

		System.out.println("This is the htdto:"
				+ healthInsuranceBean.getHealthEnrollmentDTO().toString());

		return message;
	}

	@Override
	public String saveSubscriptionDetails(
			HealthEnrollmentBean healthInsuranceBean) throws Exception {
		Session session = null;
		String message = null;
		session = hibernateUtils.getSession();
		try {

			HealthSubscriptionDTO healthSubscriptionDTO = new HealthSubscriptionDTO();
			healthSubscriptionDTO.setSfId(healthInsuranceBean.getSfId());
			healthSubscriptionDTO.setEffctiveDate(healthInsuranceBean
					.getEffctiveDate());
			healthSubscriptionDTO.setHicName(healthInsuranceBean.getHicName());
			healthSubscriptionDTO.setHicNo(healthInsuranceBean.getHicNo());
			healthSubscriptionDTO.setStatus(1);
			healthSubscriptionDTO.setSubScriptiomAmt(healthInsuranceBean
					.getSubScriptiomAmt());

			session.save(healthSubscriptionDTO);
			session.flush();
			session.clear();
			/*
			 * session.createQuery(
			 * "update HealthEnrollmentDTO set status='2' , effctiveDate=?,subScriptiomAmt=?,hicName=?,hicNo=? where sfId=? "
			 * ) .setDate(0,
			 * healthInsuranceBean.getEffctiveDate()).setInteger(1,
			 * healthInsuranceBean.getSubScriptiomAmt()).setString(2,
			 * healthInsuranceBean.getHicName()).setInteger(3,
			 * healthInsuranceBean
			 * .getHicNo()).setString(4,healthInsuranceBean.getSfId
			 * ()).executeUpdate(); message = CPSConstants.SUCCESS;
			 */
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

}
