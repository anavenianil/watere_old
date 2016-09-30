package com.callippus.HealthInsuranceDao;
import com.callippus.healthInsurance.HealthEnrollmentBean;

public interface HealthInsuranceDaoInf {

	 public HealthEnrollmentBean getHealthInsuranceDetails(HealthEnrollmentBean healthInsuranceDTO) throws Exception;
	 public HealthEnrollmentBean getHealthInsuranceEnrollmentDetails(HealthEnrollmentBean healthInsuranceDTO) throws Exception;
	 
		
		//public List<AddressBean> getAddressDetailsMasterData(String addressId) throws Exception;

}
