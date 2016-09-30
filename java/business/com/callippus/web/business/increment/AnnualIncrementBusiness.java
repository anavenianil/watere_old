package com.callippus.web.business.increment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.increment.AnnualIncrementBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.increment.IAnnualIncrementDAO;

@Service
public class AnnualIncrementBusiness {

	@Autowired
	private IAnnualIncrementDAO incrementDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public AnnualIncrementBean getEmpDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			incrementDAO.getEmpDetails(annualIncrementBean);
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}

	public AnnualIncrementBean submitIncrementDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			annualIncrementBean = incrementDAO.submitIncrementDetails(annualIncrementBean);
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}

	@SuppressWarnings("unchecked")
	public AnnualIncrementBean getTypeMasterDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			annualIncrementBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}

	public AnnualIncrementBean getIncrementCasualitiesList(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			annualIncrementBean = incrementDAO.getIncrementCasualityList(annualIncrementBean);
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}

	public AnnualIncrementBean getDoPartList(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			annualIncrementBean = incrementDAO.getDoPartList(annualIncrementBean);
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}

	public AnnualIncrementBean getEmpIncrementDoPartDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			annualIncrementBean = incrementDAO.getEmpIncrementDoPartDetails(annualIncrementBean);
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}

	public AnnualIncrementBean submitIncrementToPayDetails(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			annualIncrementBean = incrementDAO.submitIncrementToPayDetails(annualIncrementBean);
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}

	public AnnualIncrementBean getPaybillRunmonth(AnnualIncrementBean annualIncrementBean) throws Exception {
		try {
			annualIncrementBean.setPayBillRunMonth(commonDataDAO.getPaybillRunmonth());
		} catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
	//code for new screen
	public AnnualIncrementBean getAnnualIncrementBasicPayIdDetails(AnnualIncrementBean annualIncrementBean) throws Exception{
		try{
			annualIncrementBean=incrementDAO.getAnnualIncrementBasicPayIdDetails(annualIncrementBean);
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
	public AnnualIncrementBean getAnnualIncrementDetailsInFinance(AnnualIncrementBean annualIncrementBean) throws Exception{
		try{
			annualIncrementBean=incrementDAO.getAnnualIncrementDetailsInFinance(annualIncrementBean);
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
	public AnnualIncrementBean getUpdateAnnualIncrementDetails(AnnualIncrementBean annualIncrementBean) throws Exception{
		try{
			annualIncrementBean=incrementDAO.updateAnnualIncrementDetails(annualIncrementBean);
		}catch (Exception e) {
			throw e;
		}
	return annualIncrementBean;
	}
	public AnnualIncrementBean getEmpNullDetails(AnnualIncrementBean annualIncrementBean)throws Exception{
		try{
			annualIncrementBean=incrementDAO.getEmpNullDetails(annualIncrementBean);
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
	public AnnualIncrementBean getEmpNotNullDetails(AnnualIncrementBean annualIncrementBean)throws Exception{
		try{
			annualIncrementBean=incrementDAO.getEmpNotNullDetails(annualIncrementBean);
		}catch (Exception e) {
			throw e;
		}
		return annualIncrementBean;
	}
}
