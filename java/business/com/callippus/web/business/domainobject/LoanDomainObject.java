package com.callippus.web.business.domainobject;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.beans.requests.LoanRequestProcessBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.loan.beans.dto.ErpLoanRequestDTO;
import com.callippus.web.loan.beans.dto.LoanCDADetailsDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;
import com.callippus.web.loan.beans.request.ErpLoanRequestBean;

@Service
public class LoanDomainObject {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String updateTxnDetails(ErpLoanRequestDTO erpLoanRequestDTO) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			ErpLoanRequestDTO lrd = (ErpLoanRequestDTO) session
					.createCriteria(ErpLoanRequestDTO.class)
					.add(Expression.eq(CPSConstants.REQUESTID,
							erpLoanRequestDTO.getRequestID()))
					.uniqueResult();
			//BeanUtils.copyProperties(erpLoanRequestDTO, lrd);
			if(lrd.getLoanStatus()==0){
				lrd.setEligibleAmount(erpLoanRequestDTO.getEligibleAmount());
				lrd.setPreviousLoanAmount(erpLoanRequestDTO.getPreviousLoanAmount());
				lrd.setMonthlyDeduction(erpLoanRequestDTO.getMonthlyDeduction());
			}
			if(lrd.getLoanStatus()==1){
				lrd.setApprovedAmount(erpLoanRequestDTO.getApprovedAmount());
			}
			lrd.setLoanStatus(lrd.getLoanStatus()+1);
			session.saveOrUpdate(lrd);
			erpLoanRequestDTO.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return erpLoanRequestDTO.getResult();
	}
	
	public String updateTxnDetails(LoanRequestProcessBean processBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanHBAPaymentDTO loanPaymentDTO = (LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(processBean.getRequestID())))
					.uniqueResult();
			if(CPSUtils.compareStrings(String.valueOf(processBean.getStatus()),CPSConstants.STATUSCOMPLETED)){
				if(loanPaymentDTO.getLoanTypeID()== Integer.valueOf(CPSConstants.FESTIVALLOANID)){
					LoanCDADetailsDTO loanCDADTO=new LoanCDADetailsDTO();
					loanCDADTO.setRequestID(Integer.valueOf(processBean.getRequestID()));
					loanCDADTO.setStatus(1);
					loanCDADTO.setCreatedBy(processBean.getSfID());
					loanCDADTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					loanCDADTO.setLastModifiedBy(processBean.getSfID());
					loanCDADTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					loanCDADTO.setHqReturnFlag(0);
					session.saveOrUpdate(loanCDADTO);
				}
			}
			loanPaymentDTO.setSanctionedAmount(processBean.getSanctionedAmount());
			loanPaymentDTO.setSanctionedDate(processBean.getSanctionedDate());
			loanPaymentDTO.setSanctionedInstalments(processBean.getSanctionedInstalments());
			loanPaymentDTO.setRecStartDate(processBean.getRecStartDate());
			loanPaymentDTO.setEmi(processBean.getEmi());
			loanPaymentDTO.setBankAccount(processBean.getBankAccount());
			loanPaymentDTO.setBankBranch(processBean.getBankBranch());
			loanPaymentDTO.setLoanRefNo(processBean.getLoanRefNo());
			loanPaymentDTO.setStatus(processBean.getStatus());
			session.saveOrUpdate(loanPaymentDTO);

			processBean = (LoanRequestProcessBean) session.get(LoanRequestProcessBean.class, processBean.getRequestID());
			processBean.setStatus(loanPaymentDTO.getStatus());
			session.saveOrUpdate(processBean);

			processBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}
	
	public String updateTxnDetails(LoanHBARequestProcessBean processBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanHBAPaymentDTO loanPaymentDTO = (LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(processBean.getRequestID())))
					.uniqueResult();
			loanPaymentDTO.setSanctionedAmount(processBean.getSanctionedAmount());
			loanPaymentDTO.setSanctionedDate(processBean.getSanctionedDate());
			loanPaymentDTO.setSanctionedInstalments(processBean.getSanctionedInstalments());
			loanPaymentDTO.setRecStartDate(processBean.getRecStartDate());
			loanPaymentDTO.setEmi(processBean.getEmi());
			loanPaymentDTO.setBankAccount(processBean.getBankAccount());
			loanPaymentDTO.setBankBranch(processBean.getBankBranch());
			loanPaymentDTO.setLoanRefNo(processBean.getLoanRefNo());
			loanPaymentDTO.setStatus(processBean.getStatus());
			session.saveOrUpdate(loanPaymentDTO);

			session.createSQLQuery("update loan_hba_request_details set status=? where request_id=?").setInteger(0, loanPaymentDTO.getStatus()).setString(1, processBean.getRequestID()).executeUpdate();

			processBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}
}
