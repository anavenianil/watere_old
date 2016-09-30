package com.callippus.web.dao.increment;

import com.callippus.web.beans.increment.AnnualIncrementBean;

public interface IAnnualIncrementDAO {

	public AnnualIncrementBean getEmpDetails(AnnualIncrementBean annualIncrementBean) throws Exception;

	public AnnualIncrementBean submitIncrementDetails(AnnualIncrementBean annualIncrementBean)throws Exception;

	public AnnualIncrementBean getIncrementCasualityList(AnnualIncrementBean annualIncrementBean)throws Exception;

	public AnnualIncrementBean getDoPartList(AnnualIncrementBean annualIncrementBean)throws Exception;

	public AnnualIncrementBean getEmpIncrementDoPartDetails(AnnualIncrementBean annualIncrementBean)throws Exception;

	public AnnualIncrementBean submitIncrementToPayDetails(AnnualIncrementBean annualIncrementBean)throws Exception;
	
	public AnnualIncrementBean getAnnualIncrementBasicPayIdDetails(AnnualIncrementBean annualIncrementBean)throws Exception;

	public AnnualIncrementBean updateAnnualIncrementDetails(AnnualIncrementBean annualIncrementBean) throws Exception;
	
	public AnnualIncrementBean getEmpNullDetails(AnnualIncrementBean annualIncrementBean)throws Exception;
	
	public AnnualIncrementBean getEmpNotNullDetails(AnnualIncrementBean annualIncrementBean)throws Exception;
	
	public AnnualIncrementBean getAnnualIncrementDetailsInFinance(AnnualIncrementBean annualIncrementBean)throws Exception;
}
