package com.callippus.web.loan.dao.management;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.loan.beans.dto.ErpLoanPurposeDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.loan.beans.management.ErpLoanManagementBean;
import com.callippus.web.loan.beans.management.LoanManagementBean;

@SuppressWarnings("serial")
@Service
public class SQLErpLoanManagementDAO implements IErpLoanManagementDAO, Serializable {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	

	

	@SuppressWarnings("unchecked")
	public ErpLoanManagementBean submitLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception {
		String result = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			
			List<ErpLoanPurposeDTO> list = null;
			list = session.createCriteria(ErpLoanPurposeDTO.class).add(Expression.eq("loanType", loanBean.getLoanType())).add(Expression.eq("loanType",loanBean.getLoanType())).add(Expression.eq(CPSConstants.STATUS, 1)).list();
			if (CPSUtils.checkList(list)) {
				loanBean.setResult(CPSConstants.DUPLICATE);
			}else{
				ErpLoanPurposeDTO  erpLoanPurposeDTO= new ErpLoanPurposeDTO();
				if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
					
					// update
					//erpLoanTypeMasterDTO.setId(Integer.valueOf(loanBean.getNodeID()));
					erpLoanPurposeDTO = (ErpLoanPurposeDTO) session.get(ErpLoanPurposeDTO.class, Integer.valueOf(loanBean.getNodeID()));
					erpLoanPurposeDTO.setLastModifiedBy(loanBean.getSfID());
					erpLoanPurposeDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					erpLoanPurposeDTO.setLoanType(loanBean.getLoanType());
				}else{
					erpLoanPurposeDTO.setLoanType(loanBean.getLoanType());
					erpLoanPurposeDTO.setStatus(1);
					erpLoanPurposeDTO.setCreatedBy(loanBean.getSfID());
					erpLoanPurposeDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					erpLoanPurposeDTO.setLastModifiedBy(loanBean.getSfID());
					erpLoanPurposeDTO.setLastModifiedTime(erpLoanPurposeDTO.getCreationTime());

				}
			
				loanBean.setResult(CPSConstants.SUCCESS);
				session.saveOrUpdate(erpLoanPurposeDTO);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	@SuppressWarnings("unchecked")
	public ErpLoanManagementBean getLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setErpLoanTypeMasterList(session.createCriteria(ErpLoanPurposeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	//delete
	public ErpLoanManagementBean deleteLoanTypeMasterDetails(ErpLoanManagementBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			ErpLoanPurposeDTO loanMasterBean = (ErpLoanPurposeDTO) session.get(ErpLoanPurposeDTO.class, Integer.valueOf(loanBean.getNodeID()));
			loanMasterBean.setLastModifiedBy(loanBean.getSfID());
			loanMasterBean.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			loanMasterBean.setStatus(0);
			session.saveOrUpdate(loanMasterBean);
			loanBean.setResult(CPSConstants.DELETE);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	

	
}
