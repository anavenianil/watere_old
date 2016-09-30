package com.callippus.web.business.fpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.fpa.FPARequestBean;
import com.callippus.web.dao.fpa.IFPARequestDAO;
import com.callippus.web.loan.beans.request.LoanRequestBean;

@Service
public class FPARequestBusiness {
	@Autowired
	private IFPARequestDAO fpaRequestDAO;

	public FPARequestBean getFPARequestHomeDetails(FPARequestBean fpaBean)
			throws Exception {
		try {
			fpaBean = fpaRequestDAO.getEmployeeDetails(fpaBean);
			fpaBean = fpaRequestDAO.getPayDetails(fpaBean);
			fpaBean = fpaRequestDAO.getdeptDetails(fpaBean);
		} catch (Exception e) {
			throw e;
		}
		return fpaBean;
	}

	public FPARequestBean checkConstraints(FPARequestBean fpaBean)
			throws Exception {
		try {
			fpaBean = fpaRequestDAO.checkConstraints(fpaBean);
		} catch (Exception e) {
			throw e;
		}
		return fpaBean;
	}

	public FPARequestBean getFPARequestFamilyDetails(FPARequestBean fpaBean)
			throws Exception {
		try {
			fpaBean = fpaRequestDAO.getFamilyDetails(fpaBean);
		} catch (Exception e) {
			throw e;
		}
		return fpaBean;
	}
}
