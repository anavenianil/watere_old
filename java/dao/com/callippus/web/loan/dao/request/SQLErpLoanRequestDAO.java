package com.callippus.web.loan.dao.request;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.business.leave.request.CommonConstraints;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.loan.beans.request.ErpLoanRequestBean;
import com.callippus.web.loan.beans.request.LoanRequestBean;
import com.callippus.web.paybill.dto.PayBillDearnessAllowanceMasterDTO;

@Service
public class SQLErpLoanRequestDAO implements ErpILoanRequestDAO, Serializable {
	private static final long serialVersionUID = 8556996565081604229L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CommonConstraints commonConstraints;

	public ErpLoanRequestBean getEmployeeDetails(ErpLoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setEmployeeDetails((EmployeeBean) session.get(EmployeeBean.class, loanBean.getSfID()));
			
			
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public ErpLoanRequestBean getPayDetails(ErpLoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setPaymentDetails((EmpPaymentsDTO) session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq(CPSConstants.SFID, loanBean.getSfID())).uniqueResult());
	/*		loanBean.getPaymentDetails().setDa(
					((PayBillDearnessAllowanceMasterDTO) session.get(PayBillDearnessAllowanceMasterDTO.class, Integer.valueOf(session.createCriteria(PayBillDearnessAllowanceMasterDTO.class)
							.setProjection(Projections.projectionList().add(Projections.max("pk"))).uniqueResult().toString()))).getDaValue());
		*/} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public ErpLoanRequestBean getdeptDetails(ErpLoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanRequestBean loanReqBean = (LoanRequestBean) session.createSQLQuery(
					"select department_id deptId from org_role_instance org,emp_master emp where org.status=1 and emp.office_id=org.org_role_id and emp.status=1 and emp.sfid=? ").addScalar("deptId",
					Hibernate.INTEGER).setString(0, loanBean.getSfID()).setResultTransformer(Transformers.aliasToBean(LoanRequestBean.class)).uniqueResult();
			loanBean.setDeptId(loanReqBean.getDeptId());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}


	
	
	
	public String getEmpExist(String sfid) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
             
			if (Integer.valueOf(session.createSQLQuery("select count(*) from emp_master where sfid=? and status=1 ").setString(0, sfid).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	
	
	public ErpLoanRequestBean getLoanTypeMaster(ErpLoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			loanBean.setErpLoanTypes(session.createSQLQuery(
					"select * from erp_loan_purpose")
							.list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	
	@SuppressWarnings("unchecked")
	public ErpLoanRequestBean checkConstraints(ErpLoanRequestBean loanBean)
			throws Exception {
		Session session = null;
		loanBean.setResult(CPSConstants.SUCCESS);
		StringBuffer sb = new StringBuffer();
		String basicPay = null;
		try {
			session = hibernateUtils.getSession();

			//to check Experience
			BigDecimal exp = (BigDecimal) session
					.createSQLQuery("SELECT round((SYSDATE - doj_govt)/365)  from emp_master where sfid=?")
					.setString(0, loanBean.getSfID()).uniqueResult();
			loanBean.setExperience(exp.toString());
			
			/*loanBean = (ErpLoanRequestBean) session
					.createCriteria(ErpLoanRequestDTO.class)
					.add(Expression.and(Expression.ne("loanStatus",0), Expression.ne("loanStatus",1)))
					.add(Expression.eq("outstandingAmount", 0.0f))
					.add(Expression.eq("sfID", loanBean.getSfID()))
					.uniqueResult();*/
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return loanBean;
	}
}
