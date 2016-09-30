package com.callippus.HealthInsuranceBusiness;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.HealthInsuranceDao.HealthEnrollmentDaoInf;
import com.callippus.healthInsurance.HealthEnrollmentBean;
@Service	
public class HealthEnrollmentBusiness {

	@Autowired
	private HealthEnrollmentDaoInf dao;	
	@SuppressWarnings("unchecked")	
public HealthEnrollmentBean saveCdaAmont(HealthEnrollmentBean bean) throws Exception{ 
	try {
		 JSONObject mainJson = new JSONObject(bean.getJsonValue());
		 //if(!CPSUtils.isNullOrEmpty(bean.getType())){
    		 for(int i=0;i<mainJson.length();i++) {
					JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
					bean.setMessage(dao.saveCdaAmont(subJson,bean));	
    		 }			  
    	 //}
}
	catch (Exception e) {
		throw e;
	}
	return bean;
}
	public HealthEnrollmentBean getHealthInsEmpList(
			HealthEnrollmentBean healthInsuranceBean) throws Exception {
		healthInsuranceBean.setMessage(dao.getHealthInsEmpList(healthInsuranceBean));
		return healthInsuranceBean;
	}
	public void updateHealthSubscriptionDetails(
			HealthEnrollmentBean healthInsuranceBean) throws Exception {
		try {
		healthInsuranceBean.setMessage(dao.saveSubscriptionDetails(healthInsuranceBean));
		}catch(Exception e){
			throw e;
		}
		
	}
}
