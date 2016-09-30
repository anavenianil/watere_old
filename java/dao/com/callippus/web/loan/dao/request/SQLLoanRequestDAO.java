package com.callippus.web.loan.dao.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
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
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.loan.beans.dto.FinancialYearDTO;
import com.callippus.web.loan.beans.dto.GPFClosingBalanceDTO;
import com.callippus.web.loan.beans.dto.GPFRulesDTO;
import com.callippus.web.loan.beans.dto.LoanAmountDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanAmountGridDTO;
import com.callippus.web.loan.beans.dto.LoanDesigMappingDTO;
import com.callippus.web.loan.beans.dto.LoanDetailsDTO;
import com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO;
import com.callippus.web.loan.beans.dto.LoanHBAPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanLeavesMappingDTO;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;
import com.callippus.web.loan.beans.dto.LoanTypeDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.loan.beans.request.LoanRequestBean;
import com.callippus.web.paybill.dto.PayBillDearnessAllowanceMasterDTO;

@Service
public class SQLLoanRequestDAO implements ILoanRequestDAO, Serializable {
	private static final long serialVersionUID = 8556996565081604229L;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CommonConstraints commonConstraints;

	public LoanRequestBean getEmployeeDetails(LoanRequestBean loanBean) throws Exception {
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
	public LoanRequestBean getFestivals(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setFestivalsList(session.createSQLQuery(
					"select lfm.id id,lfm.festival_name festivalName from loan_type_details ltd,loan_festival_master lfm "
							+ "where ltd.status=1 and ltd.loan_type_id=? and lfm.status=1 and lfm.id=ltd.loan_subtype_id "
							+ "and ((ltd.period_type_flag='Y' and sysdate between ltd.from_date and ltd.to_date) or ltd.period_type_flag='N')").addScalar("id", Hibernate.INTEGER).addScalar(
					"festivalName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LoanFestivalMasterDTO.class)).setString(0, CPSConstants.FESTIVALLOANID).list());

		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanRequestBean getPayDetails(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setPaymentDetails((EmpPaymentsDTO) session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq(CPSConstants.SFID, loanBean.getSfID())).uniqueResult());
			loanBean.getPaymentDetails().setDa(
					((PayBillDearnessAllowanceMasterDTO) session.get(PayBillDearnessAllowanceMasterDTO.class, Integer.valueOf(session.createCriteria(PayBillDearnessAllowanceMasterDTO.class)
							.setProjection(Projections.projectionList().add(Projections.max("pk"))).uniqueResult().toString()))).getDaValue());
//			loanBean.getPaymentDetails().setGpfClosingBalance(
//					((GPFClosingBalanceDTO) session.get(GPFClosingBalanceDTO.class, Integer.valueOf(session.createCriteria(GPFClosingBalanceDTO.class).setProjection(
//							Projections.projectionList().add(Projections.max("id"))).add(Expression.eq("sfID", loanBean.getSfID())).uniqueResult().toString()))).getBalance());

		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanRequestBean getGpfSubTypes(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			loanBean.setGpfSubTypes(session.createCriteria(GPFRulesDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanRequestBean checkConstraints(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		loanBean.setResult(CPSConstants.SUCCESS);
		StringBuffer sb = new StringBuffer();
		String basicPay=null;
		try {
			
			session = hibernateUtils.getSession();
			
			Criteria ctr = session.createCriteria(LoanDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("loanTypeID", Integer.valueOf(loanBean.getLoanType())));
			if (CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.FESTIVALLOANID)) {
				ctr.add(Expression.eq("loanSubTypeID", Integer.valueOf(loanBean.getLoanSubType())));
			}
			LoanDetailsDTO loanTypeDetails = (LoanDetailsDTO) ctr.uniqueResult();
			//already applied or not check
			if(CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.FESTIVALLOANID)){
			FinancialYearDTO financialyearDTO=null;
			financialyearDTO=(FinancialYearDTO)session.createSQLQuery("select from_date fromDate,to_date toDate from financial_year_master where sysdate between from_date and to_date").addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).uniqueResult();
			List<LoanPaymentDTO> list = null;
			list = (List<LoanPaymentDTO>)session.createCriteria(LoanPaymentDTO.class).add(Expression.eq("sfID", loanBean.getSfID())).add(Expression.and(Expression.ne(CPSConstants.STATUS, Integer.valueOf(CPSConstants.STATUSCANCELLED)), Expression.ne(CPSConstants.STATUS, Integer.valueOf(CPSConstants.STATUSDECLINED)))).add(Expression.eq("loanTypeID", Integer.valueOf(CPSConstants.FESTIVALLOANID))).add(Expression.between("requestedDate", financialyearDTO.getFromDate(), financialyearDTO.getToDate())).list();
			if(CPSUtils.checkList(list)){
				loanBean.setResult(CPSConstants.FAILED);	
				loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.FESTIVALALREADYAPPLIED));
			}
			}
			// Installments checking
			if (!CPSUtils.isNullOrEmpty(loanBean.getRequestedInstalments())) {
				if (loanTypeDetails.getMinInstallments() > Integer.valueOf(loanBean.getRequestedInstalments())
						|| loanTypeDetails.getMaxInstallments() < Integer.valueOf(loanBean.getRequestedInstalments())) {
					// Installments should be between min & max
					loanBean.setResult(CPSConstants.FAILED);
					loanBean.setRemarks(CPSConstants.LOANINVALIDINSTALMENTS.replace("%1%", String.valueOf(loanTypeDetails.getMinInstallments())).replace("%2%",
							String.valueOf(loanTypeDetails.getMaxInstallments())));
				}
			}
			if (!CPSUtils.isNullOrEmpty(loanBean.getRequestedInterestInstalments())) {
				if (loanTypeDetails.getMinInterestInstallments() > Integer.valueOf(loanBean.getRequestedInterestInstalments())
						|| loanTypeDetails.getMaxInterestInstallments() < Integer.valueOf(loanBean.getRequestedInterestInstalments())) {
					// Installments should be between min & max
					loanBean.setResult(CPSConstants.FAILED);
					loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANINVALIDINTERESTINSTALMENTS.replace("%1%",
							String.valueOf(loanTypeDetails.getMinInterestInstallments())).replace("%2%", String.valueOf(loanTypeDetails.getMaxInterestInstallments()))));
				}
			}

			// Minimum Service checking
			if (!CPSUtils.isNullOrEmpty(loanTypeDetails.getExperience())) {
				Calendar now = Calendar.getInstance();
				now.add(Calendar.MONTH, -(int) loanTypeDetails.getExperience() * 12);

				if (now.getTime().before(loanBean.getEmployeeDetails().getDojDrdoInFormat())) {
					// User doesn't have minimum experience to take this loan
					loanBean.setResult(CPSConstants.FAILED);
					loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANMINEXPREMARK.replace("%1%", String.valueOf(loanTypeDetails.getExperience()))));
				}
			}

			// If the request type is GPF, check the requested amount with the balance
			if (CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.GPFADVANCELOANID) || CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.GPFWITHDRAWALLOANID)) {
				loanBean.setResult(checkGPFBalanceAmount(loanBean));
			}

			if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.INSUFFICIENT)) {
				loanBean.setResult(CPSConstants.FAILED);
				loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.INSUFFICIENTBALANCE));
			} else {
				// Amount Details
				sb
						.append("select lad.id id,lad.loan_type_id loanTypeID,lad.loan_subtype_id loanSubTypeID,lad.gaz_type gazType,lad.impact_on_pay_flag payFlag, lad.impact_on_multiple_flag multipleFlag,lad.impact_on_da_flag daFlag,lad.impact_on_balance_flag balanceFlag,lad.impact_on_months_pay_flag monthsFlag,lad.da_percentage daPercentage from loan_amount_details lad,loan_designation_mapping ldm,emp_master emp "
								+ "where lad.status=1 and ldm.status=1 and lad.id=ldm.loan_amount_id and lad.loan_type_id=? and emp.designation_id=ldm.designation_id and emp.sfid=?");
//				if (CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.FESTIVALLOANID)) {
//					sb.append(" and lad.loan_subtype_id=" + loanBean.getLoanSubType());
//				}

				LoanAmountDetailsDTO loanAmountDetails = (LoanAmountDetailsDTO) session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("loanTypeID", Hibernate.INTEGER)
						.addScalar("loanSubTypeID", Hibernate.INTEGER).addScalar("gazType", Hibernate.STRING).addScalar("payFlag", Hibernate.STRING).addScalar("multipleFlag", Hibernate.STRING)
						.addScalar("daFlag", Hibernate.STRING).addScalar("balanceFlag", Hibernate.STRING).addScalar("monthsFlag", Hibernate.STRING).addScalar("daPercentage", Hibernate.INTEGER)
						.setResultTransformer(Transformers.aliasToBean(LoanAmountDetailsDTO.class)).setInteger(0, Integer.valueOf(loanBean.getLoanType())).setString(1,
								loanBean.getEmployeeDetails().getUserSfid()).uniqueResult();

				if (!CPSUtils.isNull(loanAmountDetails)) {
					if (CPSUtils.compareStrings(loanAmountDetails.getMultipleFlag(), CPSConstants.Y)) {
						// Get how many times this employee had taken this loan
						ctr = session.createCriteria(LoanHBAPaymentDTO.class).add(Expression.eq("sfID", loanBean.getSfID())).add(
								Expression.ne(CPSConstants.STATUS, Integer.valueOf(CPSConstants.STATUSCANCELLED)))
								.add(Expression.ne(CPSConstants.STATUS, Integer.valueOf(CPSConstants.STATUSDECLINED))).add(Expression.eq("loanTypeID", Integer.valueOf(loanBean.getLoanType())));
						if (CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.FESTIVALLOANID)) {
							ctr.add(Expression.eq("loanSubTypeID", Integer.valueOf(loanBean.getLoanSubType())));
						}
						loanBean.setStepID(Integer.valueOf(ctr.setProjection(Projections.rowCount()).uniqueResult().toString()) + 1);
					} else {
						loanBean.setStepID(1);
					}
					loanBean.setLoanAmountDetails(loanAmountDetails);

					String Result= loanBean.getResult();
					if (loanBean.getStepID() != 1) {
						// Check sub-sequence loan constraints
						loanBean.setResult(checkSubSequentConstraints(loanBean));
					}
					if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.FAILED)) {
						loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANSUBSEQCONSTRAINT));
					} else {
						loanBean.setResult(Result);
						if(CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.FESTIVALLOANID)){
							basicPay=loanBean.getPaymentDetails().getBasicPay();
							loanBean.getPaymentDetails().setBasicPay("0");
						}
						loanBean = getConfiguredLoanAmount(loanBean);
						if(CPSUtils.compareStrings(loanBean.getLoanType(), CPSConstants.FESTIVALLOANID)){
							loanBean.getPaymentDetails().setBasicPay(basicPay);
						}
						if (!CPSUtils.isNull(loanBean.getLoanAmountGridDetails())) {
							if (CPSUtils.isNullOrEmpty(loanBean.getReqAmount())) {
								loanBean.setReqAmount(String.valueOf(loanBean.getLoanAmountGridDetails().getAmount()));
								//for Festival advance if amount in amount details table is zero then he is not eligible for that loan
								if(CPSUtils.compareStrings(loanBean.getReqAmount(), "0.0")){
									loanBean.setResult(CPSConstants.FAILED);
									loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANNOTELIGIBLE));
								}
							}else if (Float.valueOf(loanBean.getReqAmount()) > loanBean.getLoanAmountGridDetails().getAmount()) {
								// Loan amount exceed
								loanBean.setResult(CPSConstants.FAILED);
								loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANAMOUNTEXCEEDREMARK.replace("%1%", String.valueOf(loanBean
										.getLoanAmountGridDetails().getAmount()))));
							}
						} else {
							loanBean.setResult(CPSConstants.FAILED);
							loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANNOTELIGIBLE));
						}
					}
				} else {
					loanBean.setResult(CPSConstants.FAILED);
					loanBean.setRemarks(commonConstraints.setRemarkDetails(loanBean.getRemarks(), CPSConstants.LOANNOTELIGIBLE));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return loanBean;
	}

	public String checkSubSequentConstraints(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			/*loanBean
					.setResult(session
							.createSQLQuery(
									"select case when lag.subsequence_relation='M' then (select case when MONTHS_BETWEEN(sysdate,max(lpd.sanctioned_date))>=lag.subsequence_months "
											+ "then 'success' else 'failed' end from loan_payment_details lpd,loan_amount_details lad where lad.status=1 and lpd.status not in (?,?) and lpd.loan_type_id=lad.loan_type_id and "
											+ "lad.loan_subtype_id=lpd.loan_subtype_id and lad.id=? and lpd.sfid=?) else (case when ?='N' then 'failed' else 'success' end) end from loan_amount_grid lag where "
											+ "lag.id=(select min(lag.id) from loan_amount_grid lag where lag.status=1 and lag.loan_amount_id=? and lag.stage_id=?)").setString(0,
									CPSConstants.STATUSCANCELLED).setString(1, CPSConstants.STATUSDECLINED).setInteger(2, loanBean.getLoanAmountDetails().getId()).setString(3, loanBean.getSfID())
							.setString(4, loanBean.getPrevLoanRecFlag()).setInteger(5, loanBean.getLoanAmountDetails().getId()).setInteger(6, loanBean.getStepID()).uniqueResult().toString());
	*/	} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public String checkGPFBalanceAmount(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean
					.setResult(session
							.createSQLQuery(
									"select case when (select lgc1.closing_balance-(select case when sum(lpd.sanctioned_amount) is null then 0 else sum(lpd.sanctioned_amount) end from loan_payment_details lpd where status not in (?,?) and lpd.sfid=lgc1.sfid and lpd.sanctioned_date>lgc1.to_date and lpd.loan_type_id in (?,?)) from loan_gpf_close_balance lgc1 where lgc1.sfid=? and lgc1.status=1 and "
											+ "lgc1.to_date=(select max(lgc.to_date) from loan_gpf_close_balance lgc where lgc.sfid=? and lgc.status=1))>=? then 'success' else 'insufficient' end res from dual")
							.setString(0, CPSConstants.STATUSCANCELLED).setString(1, CPSConstants.STATUSDECLINED).setString(2, CPSConstants.GPFADVANCELOANID).setString(3,
									CPSConstants.GPFWITHDRAWALLOANID).setString(4, loanBean.getSfID()).setString(5, loanBean.getSfID()).setString(6, loanBean.getReqAmount()).uniqueResult().toString());
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
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
	public LoanRequestBean getConfiguredLoanAmount(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			LoanAmountGridDTO loanAmtGrid = (LoanAmountGridDTO) session
					.createSQLQuery(
							"select id,amount,subsequence_relation subseqRelation,subsequence_months subseqMonths from (select id,amount,subsequence_relation,subsequence_months,ROW_NUMBER() OVER (ORDER BY amount) rowno from (select id,amount,subsequence_relation,subsequence_months from ("
									+ "select lag.id,case when ?='Y' then lag.amount+(select (floor((select da_value from(select da_value,rownum rowno from pay_dearness_allowance_master where status=1 order by da_date desc) where rowno=1)/?))*(lag.da/100)*lag.amount from dual) else lag.amount end amount,case when lad.loan_type_id=1 then 0 else lag.pay_or_gpf+1 end fromAmt,"
									+ "case when lead(lag.pay_or_gpf,1,0) over(order by lag.pay_or_gpf) = 0 then (case when lad.loan_type_id=1 then to_char(lag.pay_or_gpf+1) else '2000000' end) else lead(lag.pay_or_gpf,1,0) over(order by lag.pay_or_gpf) end toAmt,lag.subsequence_relation,lag.subsequence_months from loan_amount_grid lag ,loan_amount_details lad where lad.id=lag.loan_amount_id and lag.status=1 and lag.stage_id=? and lag.pay_balance_relation=1 and lag.loan_amount_id=?) pay "
									+ "where ? between pay.fromAmt and pay.toAmt union select id,((lag.amount/100)*?)+(select (floor((select da_value from(select da_value,rownum rowno from pay_dearness_allowance_master where status=1 order by da_date desc) where rowno=1)/?))*(lag.da/100)*lag.amount from dual) amount,subsequence_relation,subsequence_months from loan_amount_grid lag "
									+ "where lag.status=1 and lag.stage_id=? and lag.pay_balance_relation=2 and lag.loan_amount_id=? union select id,(lag.amount*?)+(select (floor((select da_value from(select da_value,rownum rowno from pay_dearness_allowance_master where status=1 order by da_date desc) where rowno=1)/?))*(lag.da/100)*lag.amount from dual),subsequence_relation,subsequence_months from loan_amount_grid lag "
									+ " where lag.status=1 and lag.stage_id=? and lag.pay_balance_relation=3 and lag.loan_amount_id=?)) where rowno=1").addScalar("id", Hibernate.INTEGER).addScalar(
							"amount", Hibernate.FLOAT).addScalar("subseqRelation", Hibernate.STRING).addScalar("subseqMonths", Hibernate.INTEGER).setResultTransformer(
							Transformers.aliasToBean(LoanAmountGridDTO.class)).setString(0,loanBean.getLoanAmountDetails().getDaFlag()).setInteger(1, loanBean.getLoanAmountDetails().getDaPercentage())// Loan Amount Details DA Percentage
					.setInteger(2, loanBean.getStepID()).setInteger(3, loanBean.getLoanAmountDetails().getId()).setFloat(4,
							(Float.valueOf(loanBean.getPaymentDetails().getBasicPay()) + Float.valueOf(loanBean.getPaymentDetails().getGradePay()))).setFloat(5,
							loanBean.getPaymentDetails().getGpfClosingBalance()).setInteger(6, loanBean.getLoanAmountDetails().getDaPercentage())// Loan Amount Details DA Percentage
					.setInteger(7, loanBean.getStepID()).setInteger(8, loanBean.getLoanAmountDetails().getId()).setFloat(9,
							(Float.valueOf(loanBean.getPaymentDetails().getBasicPay()) + Float.valueOf(loanBean.getPaymentDetails().getGradePay()))).setInteger(10,
							loanBean.getLoanAmountDetails().getDaPercentage()).setInteger(11, loanBean.getStepID()).setInteger(12, loanBean.getLoanAmountDetails().getId()).uniqueResult();

			loanBean.setLoanAmountGridDetails(loanAmtGrid);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	@SuppressWarnings("unchecked")
	public LoanRequestBean getLoanTypeMaster(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			loanBean
					.setLoanTypeMasterList(session
							.createSQLQuery(
									"select distinct ltm.id,ltm.loan_name loanName from loan_type_master ltm,loan_type_details ltd "
											+ "where ltm.status=1 and ltd.status=1 and ltm.id=ltd.loan_type_id and ((ltd.period_type_flag='Y' and sysdate between ltd.from_date and ltd.to_date) or ltd.period_type_flag='N')")
							.addScalar("id", Hibernate.INTEGER).addScalar("loanName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LoanTypeMasterDTO.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanRequestBean editInstallmentsDetails(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LoanPaymentDTO loanPaymentDTO = new LoanPaymentDTO();
			loanPaymentDTO = (LoanHBAPaymentDTO) session.createCriteria(LoanHBAPaymentDTO.class).add(Expression.eq(CPSConstants.REQUESTID, loanBean.getNodeID())).uniqueResult();
			loanPaymentDTO.setRequestedAmount(Float.valueOf(loanBean.getReqAmount()));
			loanPaymentDTO.setRequestedInstalments(loanBean.getRequestedInstalments());
			loanPaymentDTO.setRequestedInterestInstalments(loanBean.getRequestedInterestInstalments());

			session.saveOrUpdate(loanPaymentDTO);

		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanRequestBean getdeptDetails(LoanRequestBean loanBean) throws Exception {
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
	
	public LoanRequestBean getGpfDetails(LoanRequestBean loanBean)
			throws Exception {
		Session session = null;
		GPFClosingBalanceDTO gpfClosingBalanceDTO = null;
		try {
			session = hibernateUtils.getSession();
			gpfClosingBalanceDTO=(GPFClosingBalanceDTO)session.createCriteria(GPFClosingBalanceDTO.class).add(Expression.eq("sfID", loanBean.getSfID())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			if(!CPSUtils.isNull(gpfClosingBalanceDTO)) {
			loanBean.getPaymentDetails().setGpfClosingBalance(
					((GPFClosingBalanceDTO) session.get(GPFClosingBalanceDTO.class, Integer.valueOf(gpfClosingBalanceDTO.getId()))).getBalance());
			}else {
				loanBean.setResult(CPSConstants.FAILED);
				loanBean.setRemarks(CPSConstants.GPFNOTAVAILABLE);
			}

		}catch (Exception e) {
			throw e;
		}
		return loanBean;
	}


	public LoanRequestBean getGpfClosingBalanceDetails(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		GPFClosingBalanceDTO gpfClosingBalanceDTO = null;
		try {
		session = hibernateUtils.getSession();
		gpfClosingBalanceDTO=(GPFClosingBalanceDTO)session.createCriteria(GPFClosingBalanceDTO.class).add(Expression.eq("sfID", loanBean.getSfID())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
		if(!CPSUtils.isNull(gpfClosingBalanceDTO)) {
		loanBean.getPaymentDetails().setGpfClosingBalance(
				((GPFClosingBalanceDTO) session.get(GPFClosingBalanceDTO.class, Integer.valueOf(gpfClosingBalanceDTO.getId()))).getBalance());
		}
	}catch (Exception e) {
		throw e;
	}
	return loanBean;
}
	
	@SuppressWarnings("unchecked")
	public LoanRequestBean getLoanTypeDetails(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanTypeDetailsList(session.createCriteria(LoanDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	@SuppressWarnings("unchecked")
	public LoanRequestBean getLoanLeavesMapping(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanLeavesDetailsList(session.createCriteria(LoanLeavesMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	@SuppressWarnings("unchecked")
	public LoanRequestBean getLoanAmountList(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanAmountDetailsList(session.createCriteria(LoanAmountDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	@SuppressWarnings("unchecked")
	public LoanRequestBean getLoanDesignationMappings(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanDesigMappingList(session.createCriteria(LoanDesigMappingDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	@SuppressWarnings("unchecked")
	public LoanRequestBean getLoanAmountGridDetails(LoanRequestBean loanBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			loanBean.setLoanAmountGrid(session.createCriteria(LoanAmountGridDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
}
