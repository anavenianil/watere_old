package com.callippus.web.dao.fpa;

import com.callippus.web.beans.fpa.FPARequestBean;

public interface IFPARequestDAO {

	public FPARequestBean getEmployeeDetails(FPARequestBean fpaBean) throws Exception;

	public FPARequestBean getPayDetails(FPARequestBean fpaBean) throws Exception;

	public FPARequestBean checkConstraints(FPARequestBean fpaBean)throws Exception;

	public FPARequestBean getFamilyDetails(FPARequestBean fpaBean)throws Exception;

	public FPARequestBean getdeptDetails(FPARequestBean fpaBean)throws Exception;
}
