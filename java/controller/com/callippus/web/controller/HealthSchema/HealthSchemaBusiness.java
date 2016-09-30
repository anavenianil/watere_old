/*package com.callippus.web.controller.HealthSchema;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.callippus.HealthInsuranceDao.HealthEnrollmentDaoImpl;
import com.callippus.HealthInsuranceDao.HealthEnrollmentDaoInf;
import com.callippus.healthInsurance.HealthEnrollmentBean;
import com.callippus.web.cghs.beans.request.CghsRequestBean;
import com.callippus.web.controller.common.CPSUtils;
public class HealthSchemaBusiness {
		@Autowired
		private HealthEnrollmentDaoInf dao;	
	//@SuppressWarnings("unchecked")
	public HealthEnrollmentBean saveCdaAmont(HealthEnrollmentBean bean) throws Exception{ 
		try {
			 JSONObject mainJson = new JSONObject(bean.getJsonValue());
			 if(!CPSUtils.isNullOrEmpty(bean.getType())){
	    		 for(int i=0;i<mainJson.length();i++) {
						JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
						bean.setMessage(dao.saveCdaAmont(subJson,bean));	
	    		 }			  
	    	 }
	}
		catch (Exception e) {
			throw e;
		}
		return bean;
	}
	}


*/