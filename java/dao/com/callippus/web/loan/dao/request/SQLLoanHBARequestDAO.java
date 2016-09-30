package com.callippus.web.loan.dao.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.LoanHBARequestProcessBean;
import com.callippus.web.business.leave.request.CommonConstraints;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.loan.beans.dto.GPFClosingBalanceDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanHBATypeMasterDTO;
import com.callippus.web.loan.beans.request.LoanHBARequestBean;
import com.callippus.web.paybill.dto.PayBillDearnessAllowanceMasterDTO;

@Service
public class SQLLoanHBARequestDAO implements ILoanHBARequestDAO, Serializable {
	private static final long serialVersionUID = 856999565089604229L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CommonConstraints commonConstraints;

	public LoanHBARequestBean getEmployeeDetails(LoanHBARequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setEmployeeDetails((EmployeeBean) session.get(EmployeeBean.class, loanBean.getSfID()));
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanHBARequestBean getHbaLoanTypes(LoanHBARequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setHbaLoanTypesList(session.createCriteria(LoanHBATypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanHBARequestBean checkConstraints(LoanHBARequestBean loanBean) throws Exception {
		Session session = null;
		loanBean.setResult(CPSConstants.SUCCESS);
		try {
			session = hibernateUtils.getSession();
			Calendar now = Calendar.getInstance();
			Float maxAdvance;
			Float maxHouseCost = null;
			now.add(Calendar.MONTH, -(int) Integer.valueOf(CPSConstants.HBAMINEXP) * 12);

			// Installments checking
			if (!CPSUtils.isNullOrEmpty(loanBean.getNoOfInstalPrinciple())) {
				if (Integer.valueOf(CPSConstants.HBALOANMAXPRINCIPLEINSTALLMENTS) < Integer.valueOf(loanBean.getNoOfInstalPrinciple())) {
					// Installments less than max
					loanBean.setResult(CPSConstants.FAILED);
					loanBean.setRemarks(CPSConstants.HBALOANMAXPRINCIPLEINSTALLMENTSMARK);
				}
			}
			if (!CPSUtils.isNullOrEmpty(loanBean.getNoOfInstalInterest())) {
				if (Integer.valueOf(CPSConstants.HBALOANMAXINTERESTINSTALLMENTS) < Integer.valueOf(loanBean.getNoOfInstalInterest())) {
					// Installments less than max
					loanBean.setResult(CPSConstants.FAILED);
					loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.HBALOANMAXINTERESTINSTALLMENTSMARK));
				}
			}

			// Employee Employment type checking
			if (loanBean.getEmployeeDetails().getEmploymentId() == Integer.valueOf(CPSConstants.HBAEMPTYPEID) || !(now.getTime().before(loanBean.getEmployeeDetails().getDojDrdoInFormat()))) {
				// Minimum Service checking
				// User doen's have minimum experience to take this loan or User is not a permanent Employee to take this loan
			} else {
				loanBean.setResult(CPSConstants.FAILED);
				loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.HBALOANEMPTYPEREMARK.replace("%1%", CPSConstants.HBAMINEXP)));
			}

			// cost of house check
			if (loanBean.getHouseTotalCost() > 134 * Float.valueOf(loanBean.getPaymentDetails().getBasicPay()) || loanBean.getHouseTotalCost() > Float.valueOf(CPSConstants.TOTALHOUSECOSTLIMITAMT)) {
				loanBean.setResult(CPSConstants.FAILED);
				if (Float.valueOf(CPSConstants.TOTALHOUSECOSTLIMITAMT) < 134 * Float.valueOf(loanBean.getPaymentDetails().getBasicPay())) {
					maxHouseCost = Float.valueOf(CPSConstants.TOTALHOUSECOSTLIMITAMT);
				} else {
					maxHouseCost = 134 * Float.valueOf(loanBean.getPaymentDetails().getBasicPay());
				}
				loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.HOUSECOSTAMOUNTEXCEEDREMARK.replace("%1%", String.valueOf(maxHouseCost))));
			}

			// HBALoan amount Exceeding check
			if (Float.valueOf(loanBean.getAdvanceReq()) > Float.valueOf(CPSConstants.HBAMAXADVAMT) || (loanBean.getAdvanceReq() > 34 * Integer.valueOf(loanBean.getPaymentDetails().getBasicPay()))) {
				// Loan amount exceed
				loanBean.setResult(CPSConstants.FAILED);
				if (Integer.valueOf(CPSConstants.HBAMAXADVAMT) < 34 * Integer.valueOf(loanBean.getPaymentDetails().getBasicPay()))
					maxAdvance = Float.valueOf(CPSConstants.HBAMAXADVAMT);
				else
					maxAdvance = 34 * Float.valueOf(loanBean.getPaymentDetails().getBasicPay());
				loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANAMOUNTEXCEEDREMARK.replace("%1%", String.valueOf(maxAdvance))));
			}
			// single time HBA loan application check
			List<LoanHBARequestProcessBean> list = null;
			list = session.createCriteria(LoanHBARequestProcessBean.class).add(Expression.eq("sfID", loanBean.getSfID())).add(
					Expression.ne(CPSConstants.STATUS, Integer.valueOf(CPSConstants.STATUSCANCELLED))).list();
			if (CPSUtils.checkList(list)) {
				loanBean.setResult(CPSConstants.FAILED);
				loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.HBASINGLETIMEREMARK));
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanHBARequestBean getPayDetails(LoanHBARequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setPaymentDetails((EmpPaymentsDTO) session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq(CPSConstants.SFID, loanBean.getSfID())).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanHBARequestBean editHBADetails(LoanHBARequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanHBAPaymentDTO loanPaymentDTO = new LoanHBAPaymentDTO();
			LoanHBARequestProcessBean loanHBARequestProcessBean = new LoanHBARequestProcessBean();
			loanHBARequestProcessBean = (LoanHBARequestProcessBean) session.createCriteria(LoanHBARequestProcessBean.class).add(
					Expression.eq(CPSConstants.REQUESTID, String.valueOf(loanBean.getNodeID()))).add(Expression.eq(CPSConstants.FLOORTYPE, Integer.valueOf(CPSConstants.FLOOR0))).uniqueResult();
			loanHBARequestProcessBean.setAdvanceReq(loanBean.getAdvanceReq());
			loanHBARequestProcessBean.setNoOfInstalPrinciple(loanBean.getNoOfInstalPrinciple());
			loanHBARequestProcessBean.setNoOfInstalInterest(loanBean.getNoOfInstalInterest());

			loanPaymentDTO = (LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(Expression.eq(CPSConstants.REQUESTID, loanBean.getNodeID())).uniqueResult();
			loanPaymentDTO.setRequestedAmount(loanBean.getAdvanceReq());
			loanPaymentDTO.setRequestedInstalments(loanBean.getNoOfInstalPrinciple() + loanBean.getNoOfInstalInterest());

			session.saveOrUpdate(loanHBARequestProcessBean);
			session.saveOrUpdate(loanPaymentDTO);

		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanHBARequestBean getdeptDetails(LoanHBARequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanHBARequestBean loanReqBean = (LoanHBARequestBean) session.createSQLQuery(
					"select department_id deptId from org_role_instance org,emp_master emp where org.status=1 and emp.office_id=org.org_role_id and emp.status=1 and emp.sfid=? ").addScalar("deptId",
					Hibernate.INTEGER).setString(0, loanBean.getSfID()).setResultTransformer(Transformers.aliasToBean(LoanHBARequestBean.class)).uniqueResult();
			loanBean.setDeptId(loanReqBean.getDeptId());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

}
