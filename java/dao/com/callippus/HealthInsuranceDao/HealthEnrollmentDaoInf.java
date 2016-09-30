package com.callippus.HealthInsuranceDao;

import org.json.JSONObject;

import com.callippus.healthInsurance.HealthEnrollmentBean;
public interface HealthEnrollmentDaoInf {
	public String saveCdaAmont(JSONObject subJson , HealthEnrollmentBean bean) throws Exception;

	public String getHealthInsEmpList(HealthEnrollmentBean healthInsuranceBean) throws Exception;

	public String saveSubscriptionDetails(HealthEnrollmentBean healthInsuranceBean) throws Exception;

}
