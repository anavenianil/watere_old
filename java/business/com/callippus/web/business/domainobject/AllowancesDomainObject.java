package com.callippus.web.business.domainobject;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.AllowancesRequestProcessBean;
import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.paybill.dto.FamilyPlanningDTO;
import com.callippus.web.paybill.dto.PayBillEmpPaymentDeatilsDTO;
import com.callippus.web.paybill.dto.PayBillStatusDTO;

@Service
public class AllowancesDomainObject {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String updateTxnDetails(AllowancesRequestProcessBean processBean) throws Exception {
		Session session = null;
		FamilyPlanningDTO familyPlanning = null;
		Date payeffecDate=null;
		try {
			session = hibernateUtils.getSession();
			AllowancesRequestProcessBean fpaprocessBean;
			fpaprocessBean = (AllowancesRequestProcessBean) session.get(AllowancesRequestProcessBean.class, processBean.getRequestID());
			if (CPSUtils.compareStrings(String.valueOf(processBean.getStatus()), CPSConstants.STATUSCOMPLETED)) {
				familyPlanning = (FamilyPlanningDTO) session.createCriteria(FamilyPlanningDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
						Expression.eq(CPSConstants.GRADEPAY, String.valueOf(fpaprocessBean.getGradePay()))).uniqueResult();
				payeffecDate=(Date)session.createSQLQuery("select add_months(run_month,1) from paybill_status_details where id=?").setInteger(0, (Integer)session.createCriteria(PayBillStatusDTO.class).setProjection(Projections.max(CPSConstants.ID)).uniqueResult()).uniqueResult();
				fpaprocessBean.setPayEffectiveDate(payeffecDate);
				if(familyPlanning != null){
				fpaprocessBean.setSanctionedFpaAmount(Integer.valueOf(familyPlanning.getRate()));
				}
				fpaprocessBean.setSanctionedFpaDate(CPSUtils.getCurrentDateWithTime());

				EmployeeBean empBean = null;
				/*empBean = (EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
						Expression.eq(CPSConstants.USERSFID, fpaprocessBean.getSfID())).uniqueResult();*/
				//empBean.setFamPlanning(CPSConstants.YES);
				//session.saveOrUpdate(empBean);
				
				/*String qry = "update EmployeeBean set lastModifiedDate=?,famPlanning=? where status=? and sfid=?";
				Query sqlqry=session.createQuery(qry);
				sqlqry.setString(1, CPSUtils.getCurrentDate());
				sqlqry.setString(2, "Yes");
				sqlqry.setString(3, fpaprocessBean.getRequestedBy());
				sqlqry.setInteger(1, 1);
				sqlqry.executeUpdate();*/
		        session.createQuery("update EmployeeBean set lastModifiedDate='"+CPSUtils.getCurrentDate()+"',famPlanning='Yes' where status=1 and sfid='"+fpaprocessBean.getRequestedBy()+"'").executeUpdate();
				PayBillEmpPaymentDeatilsDTO empPaymentDetails = null;
				empPaymentDetails = (PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(
						Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("sfID", fpaprocessBean.getSfID())).uniqueResult();
				empPaymentDetails.setFpaGradePay((int) fpaprocessBean.getGradePay());
				session.saveOrUpdate(empPaymentDetails);
			}
			fpaprocessBean.setStatus(processBean.getStatus());
			session.saveOrUpdate(fpaprocessBean);
			processBean.setResult(CPSConstants.SUCCESS);
			hibernateUtils.closeSession();
		} catch (Exception e) {
			throw e;
		}
		return processBean.getResult();
	}

}
