package com.callippus.HealthInsuranceBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.callippus.HealthInsuranceDao.HealthInsuranceDaoInf;
import com.callippus.healthInsurance.HealthEnrollmentBean;

@Service
public class HealthInsuranceBusiness {
	@Autowired
	private  HealthInsuranceDaoInf  healthInsuranceDaoInf;

	public HealthEnrollmentBean getHealthInsuranceDetails(HealthEnrollmentBean healthInsuranceDTO) throws Exception
	{
		try{
			healthInsuranceDTO=healthInsuranceDaoInf.getHealthInsuranceDetails(healthInsuranceDTO);
		}catch(Exception e){
			throw e;
		}
		return healthInsuranceDTO; 
	} 
	
	public HealthEnrollmentBean getHealthInsuranceEnrollmentDetails(HealthEnrollmentBean healthInsuranceDTO) throws Exception{
		try{
			//healthInsuranceDTO=healthInsuranceDaoInf.getHealthInsuranceDetails(healthInsuranceDTO);
			healthInsuranceDTO=healthInsuranceDaoInf.getHealthInsuranceEnrollmentDetails(healthInsuranceDTO); 
		}catch(Exception e){
			throw e;
		}
		return healthInsuranceDTO;
	}
}
