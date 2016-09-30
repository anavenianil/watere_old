package com.callippus.web.dao.fpa;

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
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.fpa.FPARequestBean;
import com.callippus.web.beans.requests.FPARequestProcessBean;
import com.callippus.web.business.leave.request.CommonConstraints;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLFPARequestDAO implements IFPARequestDAO {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CommonConstraints commonConstraints;

	public FPARequestBean getEmployeeDetails(FPARequestBean fpaBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			fpaBean.setEmployeeDetails((EmployeeBean) session.get(EmployeeBean.class, fpaBean.getSfID()));
		} catch (Exception e) {
			throw e;
		}
		return fpaBean;
	}

	public FPARequestBean getFamilyDetails(FPARequestBean fpaBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			fpaBean.setFamilyDetails((FamilyBean) session.createCriteria(FamilyBean.class).add(Expression.eq(CPSConstants.SFID, fpaBean.getSfID())).add(Expression.eq(CPSConstants.RELATIONID, 3))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return fpaBean;
	}

	public FPARequestBean getPayDetails(FPARequestBean fpaBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			fpaBean.setPaymentDetails((EmpPaymentsDTO) session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq(CPSConstants.SFID, fpaBean.getSfID())).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return fpaBean;

	}

	@SuppressWarnings("unchecked")
	public FPARequestBean checkConstraints(FPARequestBean fpaBean) throws Exception {
		Session session = null;
		fpaBean.setResult(CPSConstants.SUCCESS);
		List<FPARequestProcessBean> fpaRequestList = null;
		int childCount, childUniqueDate;
		try {

			session = hibernateUtils.getSession();
			// single time FPA application check
			fpaRequestList = (List<FPARequestProcessBean>) session.createCriteria(FPARequestProcessBean.class).add(Expression.eq("sfID", fpaBean.getSfID())).add(
					Expression.not(Expression.in(CPSConstants.STATUS, new Integer[]{Integer.valueOf(CPSConstants.STATUSCANCELLED),Integer.valueOf(CPSConstants.STATUSDECLINED)}))).list();
			if (CPSUtils.checkList(fpaRequestList)) {
				fpaBean.setResult(CPSConstants.FAILED);
				fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPASINGLETIMEREMARK));
			} else if (CPSUtils.compareStrings(fpaBean.getEmployeeDetails().getFamPlanning(), CPSConstants.YES)) {
				fpaBean.setResult(CPSConstants.FAILED);
				fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAALREADYAVAIL));
			} else if (fpaBean.getEmployeeDetails().getMarital() == Integer.valueOf(CPSConstants.MARRIEDID)) {
				if (CPSUtils.compareStrings(fpaBean.getEmployeeDetails().getGender(), CPSConstants.MALE)) {
					fpaBean.setFamilyDetails((FamilyBean) session.createCriteria(FamilyBean.class).add(Expression.eq(CPSConstants.SFID, fpaBean.getSfID())).add(
							Expression.eq(CPSConstants.RELATIONID, CPSConstants.RELATIONWIFE)).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
					Calendar now = Calendar.getInstance();
					now.add(Calendar.MONTH, -(int) Integer.valueOf(CPSConstants.MALEEMPAGE) * 12);
					// male employee age should be less than 50
					if (now.getTime().after(fpaBean.getEmployeeDetails().getDobInFormat())) {
						fpaBean.setResult(CPSConstants.FAILED);
						fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDEMPAGE.replace("%1%", String.valueOf(CPSConstants.MALEEMPAGE))));
					}
					now = Calendar.getInstance();
					Calendar present = Calendar.getInstance();
					now.add(Calendar.MONTH, -(int) Integer.valueOf(CPSConstants.FEMALEEMPAGE) * 12);
					present.add(Calendar.MONTH, -(int) Integer.valueOf("20") * 12);
					// male employee spouse age should be between 20 and 45
					System.out.println(!CPSUtils.isNullOrEmpty(fpaBean.getFamilyDetails()));
					if(!CPSUtils.isNullOrEmpty(fpaBean.getFamilyDetails().getDobFormated())){
					if (now.getTime().after(fpaBean.getFamilyDetails().getDobFormated()) || present.getTime().before(fpaBean.getFamilyDetails().getDobFormated())) {
						fpaBean.setResult(CPSConstants.FAILED);
						fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDEMPSPOUCEAGE.replace("%1%", String.valueOf("Wife")).replace("%2%",
								String.valueOf(CPSConstants.FEMALEEMPAGE))));
					}
					}else{
						if(Integer.valueOf(fpaBean.getFamilyDetails().getAge())<20 || Integer.valueOf(fpaBean.getFamilyDetails().getAge())>Integer.valueOf(CPSConstants.FEMALEEMPAGE) ){
							fpaBean.setResult(CPSConstants.FAILED);
							fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDEMPSPOUCEAGE.replace("%1%", String.valueOf("Wife")).replace("%2%",
									String.valueOf(CPSConstants.FEMALEEMPAGE))));
						}
						
					}
				} else if (CPSUtils.compareStrings(fpaBean.getEmployeeDetails().getGender(), CPSConstants.FEMALE)) {
					fpaBean.setFamilyDetails((FamilyBean) session.createCriteria(FamilyBean.class).add(Expression.eq(CPSConstants.SFID, fpaBean.getSfID())).add(
							Expression.eq(CPSConstants.RELATIONID, CPSConstants.RELATIONHUSBAND)).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
					Calendar now = Calendar.getInstance();
					now.add(Calendar.MONTH, -(int) Integer.valueOf(CPSConstants.FEMALEEMPAGE) * 12);
					// female employee age should be less than 45
					if (now.getTime().after(fpaBean.getEmployeeDetails().getDobInFormat())) {
						fpaBean.setResult(CPSConstants.FAILED);
						fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDEMPAGE.replace("%1%", String.valueOf(CPSConstants.FEMALEEMPAGE))));
					}
					now = Calendar.getInstance();
					now.add(Calendar.MONTH, -(int) Integer.valueOf(CPSConstants.MALEEMPAGE) * 12);
					// female employee spouse age should be less than 50
					if(!CPSUtils.isNullOrEmpty(fpaBean.getFamilyDetails().getDobFormated())){
					if (now.getTime().after(fpaBean.getFamilyDetails().getDobFormated())) {
						fpaBean.setResult(CPSConstants.FAILED);
						fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDEMPSPOUCEAGE.replace("%1%", String.valueOf("Husband")).replace("%2%",
								String.valueOf(CPSConstants.MALEEMPAGE))));
					}
					}else{
						if(Integer.valueOf(fpaBean.getFamilyDetails().getAge())>Integer.valueOf(CPSConstants.MALEEMPAGE) ){
							fpaBean.setResult(CPSConstants.FAILED);
							fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDEMPSPOUCEAGE.replace("%1%", String.valueOf("Husband")).replace("%2%",
									String.valueOf(CPSConstants.MALEEMPAGE))));
						}
						
					}
				}

				childCount = session.createCriteria(FamilyBean.class).add(Expression.eq(CPSConstants.SFID, fpaBean.getSfID())).add(
						Expression.or(Expression.eq(CPSConstants.RELATIONID, CPSConstants.RELATIONSON), Expression.eq(CPSConstants.RELATIONID, CPSConstants.RELATIONDAUGHTER))).add(
						Expression.eq(CPSConstants.STATUS, 1)).list().size();
				childUniqueDate = session.createCriteria(FamilyBean.class).add(Expression.eq(CPSConstants.SFID, fpaBean.getSfID())).add(
						Expression.or(Expression.eq(CPSConstants.RELATIONID, CPSConstants.RELATIONSON), Expression.eq(CPSConstants.RELATIONID, CPSConstants.RELATIONDAUGHTER))).add(
						Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.distinct(Projections.property("dob"))).list().size();
				if (childCount == childUniqueDate) {
					// Employee should have at least one surviving child and not more than two
					if (childCount < 1 || childCount > 2) {
						fpaBean.setResult(CPSConstants.FAILED);
						fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDCHILD));
					}
				} else if (childCount == 3) {
					// check if the second Pregnancy had twin child
					FamilyBean fb1 = (FamilyBean) session.createSQLQuery(
							"select to_char(dob) dob,count(*) status  from family_details where relation_id in (?,?) and status=1 and sfid=? group by dob  having count(*)=1").addScalar("dob",
							Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setInteger(0, Integer.valueOf(CPSConstants.RELATIONSON)).setInteger(1,
							Integer.valueOf(CPSConstants.RELATIONDAUGHTER)).setString(2, fpaBean.getSfID()).setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).uniqueResult();
					FamilyBean fb2 = (FamilyBean) session.createSQLQuery(
							"select to_char(dob) dob,count(*) status from family_details where relation_id in (?,?) and status=1 and sfid=? group by dob  having count(*)=2").addScalar("dob",
							Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setInteger(0, Integer.valueOf(CPSConstants.RELATIONSON)).setInteger(1,
							Integer.valueOf(CPSConstants.RELATIONDAUGHTER)).setString(2, fpaBean.getSfID()).setResultTransformer(Transformers.aliasToBean(FamilyBean.class)).uniqueResult();
					if ((CPSUtils.convertStringToDate(fb1.getDob())).after(CPSUtils.convertStringToDate(fb2.getDob()))) {
						fpaBean.setResult(CPSConstants.FAILED);
						fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDCHILD));
					}

				} else if (childCount > 3) {
					fpaBean.setResult(CPSConstants.FAILED);
					fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDCHILD));
				}
			} else {
				// employee should be married
				fpaBean.setResult(CPSConstants.FAILED);
				fpaBean.setRemarks(commonConstraints.setRemarkDetails(fpaBean.getRemarks(), CPSConstants.FPAINVALIDMARITAL));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return fpaBean;
	}

	public FPARequestBean getdeptDetails(FPARequestBean fpaBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			FPARequestBean fpa = (FPARequestBean) session.createSQLQuery(
					"select department_id deptId from org_role_instance org,emp_master emp where org.status=1 and emp.office_id=org.org_role_id and emp.status=1 and emp.sfid=? ").addScalar("deptId",
					Hibernate.INTEGER).setString(0, fpaBean.getSfID()).setResultTransformer(Transformers.aliasToBean(FPARequestBean.class)).uniqueResult();
			fpaBean.setDeptId(fpa.getDeptId());
		} catch (Exception e) {
			throw e;
		}
		return fpaBean;
	}
}
