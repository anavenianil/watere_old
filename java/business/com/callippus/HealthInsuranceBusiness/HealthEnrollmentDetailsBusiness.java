package com.callippus.HealthInsuranceBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.HealthInsuranceDao.HealthEnrollmentDetailsDaoInf;
import com.callippus.healthInsurance.HealthEnrollmentDetailsDTO;
import com.callippus.healthInsurance.HealthInsuranceDTO;

@Service
public class HealthEnrollmentDetailsBusiness {
	
	@Autowired
	private  HealthEnrollmentDetailsDaoInf  enrollmentDetailsDaoInf;

	public HealthEnrollmentDetailsDTO getHealthEnrollmentDetails(HealthEnrollmentDetailsDTO healthEnrollmentDetailsDTO) throws Exception
	
{
		
		
		try{
			
			healthEnrollmentDetailsDTO=enrollmentDetailsDaoInf.getHealthEnrollmentDetails(healthEnrollmentDetailsDTO);
		
			
		}catch(Exception e){
			throw e;
		}
		return healthEnrollmentDetailsDTO;
	}
}
