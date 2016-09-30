package com.callippus.web.dao.paybill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmpBasicPayHistoryDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.paybill.PayBillBean;
import com.callippus.web.beans.paybill.PayBillMasterBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.paybill.dto.PayBillCanfinDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillDuesDTO;
import com.callippus.web.paybill.dto.PayBillEmpPaymentDeatilsDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayBillLogDTO;
import com.callippus.web.paybill.dto.PayBillStatusDTO;
import com.callippus.web.paybill.dto.PayBillUserDTO;
import com.callippus.web.paybill.dto.PayProPreProcessDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.paybill.dto.PayTxnMasterLogDTO;
import com.callippus.web.paybill.dto.PayTxnMastersDetailsDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;

@SuppressWarnings("serial")
@Service
public class SQLPayBillDAO implements IPayBillDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLPayBillDAO.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CalculatePayBill calculatePayBill;
	@Autowired
	private PayBillPreProcess payBillPreProcess;
	@Autowired
	private IPayBillMasterDAO iPayBillMasterDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

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

	public String getEmpPayStopFlag(String sfid) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from emp_pay_master where sfid=? and pay_stop='Yes' ").setString(0, sfid).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	public String getEmpPayStopRemarks(String sfid) throws Exception {
		Session session = null;
		String remarks = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			remarks=(String)session.createSQLQuery("select PAY_STOP_REMARKS from emp_pay_master where sfid=?").setString(0, sfid).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return remarks;
	}

	public int getPayEmpID(String sfid) throws Exception {
		Session session = null;
		int message = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			// message = Integer.parseInt(session.createSQLQuery("select id from pay_txn_details where sfid=? and status=60 ").setString(0, sfid).uniqueResult().toString());
			PayBillDTO payBillDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.or(Expression.eq("status", 1), Expression.eq("status", 60)))
					.uniqueResult();//0 is changed to 1 by Narayana
			message = payBillDTO.getId();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	public PayBillDTO getExistedPayDetails(String sfid) throws Exception {
		Session session = null;
		PayBillDTO payBillDTO = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
        	payBillDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.or(Expression.eq("status", 1), Expression.eq("status", 60)))
					.uniqueResult();//0 is changed to 1 by Narayana
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return payBillDTO;
	}
	public String getPayEmpExist(String sfid) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from emp_pay_master where sfid=? and status=1 ").setString(0, sfid).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unused")
	public PayBillDTO getEmpPayDeatails(String sfid,String runMonth) throws Exception {

		PayBillDTO keyValueDTO = null;
		PayBillDTO originalDTO = null;
		Session session = null;
		String name = "";
		String message = "";
		StringBuffer sb = new StringBuffer();
		sb.append("");
		int due = 0;
		int totalDue = 0;
		DateFormat format1 = null;
		DateFormat format2 = null;
		try {
			format1 = new SimpleDateFormat("MMM-yyyy");
			format2 = new SimpleDateFormat("yyyy-MM-dd");
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//keyValueDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.or(Expression.eq("status", 1), Expression.eq("status", 60)))
			//		.uniqueResult();//0 is changed to 1 by Narayana
			keyValueDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("runMonth", "01-"+runMonth)).add(Expression.in("status", new Integer[]{1,60,51,52}))
			.uniqueResult();//0 is changed to 1 by Narayana
			if (!CPSUtils.isNullOrEmpty(keyValueDTO)) {
				keyValueDTO.setRunMonth(format1.format((format2.parse(keyValueDTO.getRunMonth()))));
			}
			session.evict(keyValueDTO);

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueDTO;
	}

	public PayBillDTO getEmpPaymentDetails(String sfid) throws Exception {// status=50

		PayBillDTO keyValueDTO = null;
		PayBillStatusDTO payStatusDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			payStatusDTO = (PayBillStatusDTO) session.get(PayBillStatusDTO.class, Integer.parseInt(session.createCriteria(PayBillStatusDTO.class).setProjection(Projections.max("id")).uniqueResult()
					.toString()));
			keyValueDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("runId", payStatusDTO.getRunID())).add(Expression.eq("status", 50))
					.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueDTO;
	}

	public EmployeeBean getEmployeeDetails(String sfID) throws Exception {
		Session session = null;
		EmployeeBean empBean = null;
		String sql = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		/*	sql = "select emp.sfid sfid,dept1.department_name directoratename,dept2.department_name divisionname," + "emp.name_in_service_book name,dm.name designationName ,pecm.paybill_type as type, pecm.category_name as categoryId" // new columns paybilltype,categoryType added in query
					+ "	from emp_master emp,org_role_instance ori1,org_role_instance ori2,departments_master dept1," + " departments_master dept2,designation_master dm ,Pay_emp_category_master Pecm,Emp_pay_master Epm" 
					+ "	where emp.sfid=? and dept1.status=1 and dept2.status=1 and ori1.status=1 " + " and ori2.status=1 and emp.directorate_id=ori1.org_role_id "
					+ "	and emp.office_id=ori2.org_role_id and ori1.department_id=dept1.department_id and " + "	ori2.department_id=dept2.department_id and dm.status=1 "
					+ "	and emp.designation_id=dm.id and pecm.id=epm.category_id and epm.sfid=emp.sfid";
			empBean = (EmployeeBean) session.createSQLQuery(sql).addScalar("sfid").addScalar("directorateName").addScalar("divisionName").addScalar("name").addScalar("designationName").addScalar("type").addScalar("categoryId")
					.setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).setString(0, sfID).uniqueResult();*/
			
			sql = "select emp.sfid sfid,dept1.department_name directoratename,dept2.department_name divisionname," + "emp.name_in_service_book name,dm.name designationName ,pecm.paybill_type as type, pecm.category_name as categoryId,labs.name workingLocation" // new columns paybilltype,categoryType added in query
			+ "	from emp_master emp,org_role_instance ori1,org_role_instance ori2,departments_master dept1," + " departments_master dept2,designation_master dm ,Pay_emp_category_master Pecm,Emp_pay_master Epm,organizations_master labs" 
			+ "	where emp.sfid=? and dept1.status=1 and dept2.status=1 and ori1.status=1 " + " and ori2.status=1 and emp.directorate_id=ori1.org_role_id "
			+ "	and emp.office_id=ori2.org_role_id and ori1.department_id=dept1.department_id and " + "	ori2.department_id=dept2.department_id and dm.status=1 "
			+ "	and emp.designation_id=dm.id and pecm.id=epm.category_id and epm.sfid=emp.sfid and labs.id=emp.working_location";
	empBean = (EmployeeBean) session.createSQLQuery(sql).addScalar("sfid").addScalar("directorateName").addScalar("divisionName").addScalar("name").addScalar("designationName").addScalar("type").addScalar("categoryId").addScalar("workingLocation", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).setString(0, sfID).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return empBean;
	}

	public String validateBasicSal(String sfid, int basicPay) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String Query = "select validate_basic_pay(?,?) from dual";

			message = (String) session.createSQLQuery(Query).setString(0, sfid).setFloat(1, basicPay).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateGradePay(String sfid, int gradePay) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_grade_pay(?,?) from dual";
			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, gradePay).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateSpecialPay(String sfid, int splPay) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_spay(?,?) from dual";
			message = (String) session.createSQLQuery(query).setString(0, sfid).setInteger(1, splPay).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateGPFAmount(String sfid, int basicpay, int gradepay, int gpfSubAmt) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_gpf_amt(?,?,?) from dual";

			message = (String) session.createSQLQuery(query).setFloat(0, basicpay).setFloat(1, gradepay).setFloat(2, gpfSubAmt).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateFPAAmount(String sfid, int amount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_fpa(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, amount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateWashAllowance(String sfid, int amt) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select validate_wash_allw(?,?) from dual";

			message = (String) session.createSQLQuery(sql).setString(0, sfid).setFloat(1, amt).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateXeroxAllowance(int amt) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select validate_xerox_allw(?) from dual";

			message = (String) session.createSQLQuery(sql).setFloat(0, amt).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateTRAAmount(String sfid, int amount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_tra(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, amount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateHRAAmount(String sfid, int amount, int basicpay, int gradepay) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_hra(?,?,?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, basicpay).setFloat(2, gradepay).setFloat(3, amount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateTwoADDlIncrAmount(String sfid, int amount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_two_add_incr(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, amount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateVarIncrAmount(String sfid, int amount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String query = "select validate_var_incr(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, amount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateCGHSAmount(String sfid, int amount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_cghs(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, amount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public int getEmpDue(String sfid, String name) throws Exception {
		Session session = null;
		int recAmt = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			List<PayBillDuesDTO> list = (List<PayBillDuesDTO>) session.createCriteria(PayBillDuesDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("deductionName", name)).add(
					Expression.eq("status", 1)).list();
			for (int i = 0; i < list.size(); i++) {
				PayBillDuesDTO dueDTO = list.get(i);
				if (CPSUtils.compareStrings(dueDTO.getDeductionType(), "credit")) {
					recAmt += dueDTO.getRecAmount();
				} else if (CPSUtils.compareStrings(dueDTO.getDeductionType(), "debit")) {
					recAmt -= dueDTO.getRecAmount();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return recAmt;
	}

	@SuppressWarnings("unchecked")
	public void updateDuesDTO(PayBillDTO payBillDTO, int recovery, String name) throws Exception {
		Session session = null;
		Transaction tx = null;
		List<PayBillDuesDTO> creditList = null;
		@SuppressWarnings("unused")
		List<PayBillDuesDTO> debitList = null;
		int precovery = -recovery;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			creditList = session.createCriteria(PayBillDuesDTO.class).add(Expression.eq("sfid", payBillDTO.getSfid())).add(Expression.eq("deductionName", name)).add(
					Expression.eq("deductionType", "credit")).add(Expression.eq("status", 1)).list();
			debitList = session.createCriteria(PayBillDuesDTO.class).add(Expression.eq("sfid", payBillDTO.getSfid())).add(Expression.eq("deductionName", name)).add(
					Expression.eq("deductionType", "debit")).add(Expression.eq("status", 1)).list();
			if (recovery < 0) {
				for (int i = 0; i < creditList.size(); i++) {
					PayBillDuesDTO creditDueDTO = creditList.get(i);
					if (precovery >= creditDueDTO.getRecAmount() && creditDueDTO.getStatus() != 0) {
						precovery -= creditDueDTO.getRecAmount();
						creditDueDTO.setRecAmount(0);
						creditDueDTO.setStatus(0);
						creditDueDTO.setModifiedDate(CPSUtils.getCurrentDate());
					} else if (precovery < creditDueDTO.getRecAmount() && creditDueDTO.getStatus() != 0) {
						creditDueDTO.setRecAmount(creditDueDTO.getRecAmount() - precovery);
						creditDueDTO.setModifiedDate(CPSUtils.getCurrentDate());
						precovery = 0;
					}
					session.saveOrUpdate(creditDueDTO);
				}
			}
			if (recovery > 0) {
				PayBillDuesDTO payDue = new PayBillDuesDTO();
				payDue.setAmount(recovery);
				payDue.setCreationDate(CPSUtils.getCurrentDate());
				payDue.setDeductionName(name);
				payDue.setDeductionType("credit");
				payDue.setModifiedDate(CPSUtils.getCurrentDate());
				payDue.setRecAmount(recovery);
				payDue.setStatus(1);
				payDue.setSfid(payBillDTO.getSfid());
				payDue.setCreatedBy(payBillDTO.getCreatedBy());
				payDue.setModifiedBy(payBillDTO.getLastModifiedBy());
				session.saveOrUpdate(payDue);

			}
			session.flush();//tx.commit() ;

		} catch (Exception e) {
			//tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}

	}

	public String validateCEGISAmount(String sfid, int amount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_cgegis(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, amount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateDAAmount(int amount, int basicpay, int gradepay) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_da(?,?,?) from dual";

			message = (String) session.createSQLQuery(query).setFloat(0, amount).setFloat(1, basicpay).setFloat(2, gradepay).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String updateEmpPayBillDetails(PayBillDTO paydto) throws Exception {

		Session session = null;
		String message = "";
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			// CREDITS

			String message1 = validateBasicSal(paydto.getSfid(), paydto.getBasicPay());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message = message1 + "\n";

			message1 = validateGradePay(paydto.getSfid(), paydto.getGradePay());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateSpecialPay(paydto.getSfid(), paydto.getSpecialPay());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateTwoADDlIncrAmount(paydto.getSfid(), paydto.getTwoAddlIncr());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateHRAAmount(paydto.getSfid(), paydto.getHra(), paydto.getBasicPay(), paydto.getGradePay());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateDAAmount(paydto.getDa(), paydto.getBasicPay(), paydto.getGradePay());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateVarIncrAmount(paydto.getSfid(), paydto.getVariableIncr());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateFPAAmount(paydto.getSfid(), paydto.getFpa());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateTRAAmount(paydto.getSfid(), paydto.getTpt());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateXeroxAllowance(paydto.getXeroxAllowance());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateWashAllowance(paydto.getSfid(), paydto.getWashAllowance());

			message1 = validateRent(paydto.getSfid(), paydto.getRent());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "Rent", message1);

			message1 = validateGPFAmount(paydto.getSfid(), paydto.getBasicPay(), paydto.getGradePay(), paydto.getGpf());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "GPF", message1);

			message1 = validateWaterBill(paydto.getSfid(), paydto.getWater());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "Water Bill", message1);

			message1 = validateElectricity(paydto.getSfid(), paydto.getElec());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "Elec Bill", message1);

			message1 = validateFurnitureBill(paydto.getSfid(), paydto.getFurn());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "Furn Bill", message1);

			message1 = validateITTax(paydto.getSfid(), paydto.getIncomeTax(), paydto.getCess(), paydto.getSecondaryCess());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateProfessionalTax(paydto.getTotalCredits(), paydto.getProfTax());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";

			message1 = validateCGHSAmount(paydto.getSfid(), paydto.getCghs());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "CGHS", message1);

			message1 = validateCEGISAmount(paydto.getSfid(), paydto.getCegis());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "CEGIS", message1);

			message1 = validateLoan(paydto.getSfid(), CPSConstants.FESTIVALLOANID);
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1)) {
				message1 = "Fest Loan Not Applicable";
				message += validate(paydto, "Festival Advance", message1);
			}

			message1 = validateLoan(paydto.getSfid(), CPSConstants.GPFADVANCELOANID);
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1)) {
				message1 = "Gpf Advance Loan Not Applicable";
				message += validate(paydto, "Gpf Advance", message1);
			}

			message1 = validateLoan(paydto.getSfid(), CPSConstants.MOTORCARLOANID);
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1)) {
				message1 = "Car Loan Not Applicable";
				message += validate(paydto, "Car Loan", message1);
			}

			message1 = validateLoan(paydto.getSfid(), CPSConstants.MOTORCYCLELOANID);
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1)) {
				message1 = "Cycle Loan Not Applicable";
				message += validate(paydto, "Cycle Loan", message1);
			}

			message1 = validateLoan(paydto.getSfid(), CPSConstants.PCLOANID);
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1)) {
				message1 = "PC Loan Not Applicable";
				message += validate(paydto, "PC Loan", message1);
			}

			message1 = validateLoan(paydto.getSfid(), CPSConstants.MOTORSCOOTERLOANID);
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1)) {
				message1 = "Scooter Loan  Not Applicable";
				message += validate(paydto, "Scooter Loan", message1);
			}

			message1 = validateLoan(paydto.getSfid(), CPSConstants.HBALOANID);
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1)) {
				message1 = "HBA Loan  Not Applicable";
				message += validate(paydto, "HBA", message1);
			}

			// Recoveries

			message1 = validateResSecurity(paydto.getSfid(), paydto.getResSecu());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "Res Secu", message1);

			message1 = validateMess(paydto.getSfid(), paydto.getMess());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "Mess", message1);

			message1 = validateWelFund(paydto.getSfid(), paydto.getWelC());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "WelFund", message1);

			message1 = validateBenFund(paydto.getSfid(), paydto.getBenvolentFund());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "BenFund", message1);

			message1 = validateRegFund(paydto.getSfid(), paydto.getRegimentalFund());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += validate(paydto, "RegFund", message1);

			message1 = validateCcsSubsAmount(paydto.getSfid(), paydto.getCcs());
			if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
				message += message1 + "\n";
			if (paydto.getCanfin() > 0) {
				message1 = validateDeduction(paydto.getSfid(), CPSConstants.CANFIN);

				if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
					message += validate(paydto, "Canfin", message1);

			}
			if (paydto.getHdfc() > 0) {
				message1 = validateDeduction(paydto.getSfid(), CPSConstants.HDFC);

				if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
					message += validate(paydto, "Hdfc", message1);

			}
			if (paydto.getLic() > 0) {
				message1 = validateDeduction(paydto.getSfid(), CPSConstants.LIC);

				if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
					message += validate(paydto, "Lic", message1);

			}
			if (paydto.getCourtAttachment() > 0) {
				message1 = validateDeduction(paydto.getSfid(), CPSConstants.COURTATTACHMENT);

				if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
					message += validate(paydto, "Court Attachment", message1);

			}
			if (paydto.getGic() > 0) {
				message1 = validateDeduction(paydto.getSfid(), CPSConstants.GIC);

				if (!CPSUtils.compareStrings(CPSConstants.YES, message1))
					message += validate(paydto, "Gic", message1);

			}
			message = "";
			
			if (CPSUtils.isNullOrEmpty(message)) {
            	//tx = session.beginTransaction();
            	PayBillDTO existedBean=getExistedPayDetails(paydto.getSfid());
                paydto.setId(existedBean.getId());
                paydto.setRunMonth(CPSUtils.formattedDate(existedBean.getRunMonth()));
                paydto.setCreatedBy(existedBean.getCreatedBy());
                paydto.setCreationTime(existedBean.getCreationTime());
                paydto.setRunId(existedBean.getRunId());
                paydto.setRunSubId(existedBean.getRunSubId());
                
                //start:for installment no not reset //kumari
                paydto.setGpfPreInst(existedBean.getGpfPreInst());
                paydto.setGpfTotAmt(existedBean.getGpfTotAmt());//tot installments
                paydto.setCarInstNumb(existedBean.getCarInstNumb());
                paydto.setCarTotInst(existedBean.getCarTotInst());
                paydto.setScooterInstNumb(existedBean.getScooterInstNumb());
                paydto.setScooterTotInst(existedBean.getScooterTotInst());
                paydto.setCycleInstNumb(existedBean.getCycleInstNumb());
                paydto.setCycleTotInst(existedBean.getCycleTotInst());
                paydto.setHbaInstNumb(existedBean.getHbaInstNumb());
                paydto.setHbaTotInst(existedBean.getHbaTotInst());
                paydto.setPcInstNumb(existedBean.getPcInstNumb());
                paydto.setPcTotInst(existedBean.getPcTotInst());
                paydto.setFestivInstNumb(existedBean.getFestivInstNumb());
                paydto.setFestivTotInst(existedBean.getFestivTotInst());
                paydto.setWelInst(existedBean.getWelInst());
                paydto.setWelTotinst(existedBean.getWelTotinst());
                paydto.setCanfinInstNumb(existedBean.getCanfinInstNumb());
                paydto.setCanfinTotInst(existedBean.getCanfinTotInst());
                paydto.setLicInstNumb(existedBean.getLicInstNumb());
                paydto.setLicTotInst(existedBean.getLicTotInst());
                paydto.setGicInstNumb(existedBean.getGicInstNumb());
                paydto.setGicTotInst(existedBean.getGicTotInst());
                paydto.setHdfcInstNumb(existedBean.getHdfcInstNumb());
                paydto.setHdfcTotInst(existedBean.getHdfcTotInst());
                paydto.setCourtInstNumb(existedBean.getCourtInstNumb());
                paydto.setCourtTotInst(existedBean.getCourtTotInst());  
              //end:for installment no not reset //kumari
                
                paydto.setStatus(60);
                session.clear();
				session.saveOrUpdate(paydto);
				session.flush();//tx.commit() ;
				
				/**
				 * -------------------------IT Code Start-------------------------------->
				 */
				//Updating Data in PAY_IT_PAYBILL_DETAILS TABLE
			    int finYearId = getFinYearId(paydto.getRunMonth());
			    //int runId=((BigDecimal)session.createSQLQuery("select incometax_run_id_seq.NEXTVAL from dual").uniqueResult()).intValue();
				session.createSQLQuery("{call it_insert_paybill_details(?,?,?,?)}").setInteger(0, finYearId).setString(1, paydto.getRunMonth()).setString(2, paydto.getSfid()).setInteger(3, paydto.getRunId()).executeUpdate();
				String qry =" SELECT RUN_TYPE FROM(SELECT ID,RUN_TYPE,ROWNUM ROWVAL FROM INCOME_TAX_DURATION_DETAILS WHERE STATUS=1 AND " +
			    "FIN_YEAR_ID=2 AND TO_DATE < to_date(?,'dd-Mon-yyyy') ORDER BY TO_DATE DESC) WHERE ROWVAL=1";
				String runType = (String)session.createSQLQuery(qry).setString(0, paydto.getRunMonth()).uniqueResult();
				if(CPSUtils.isNullOrEmpty(runType)){
					runType="pl";
				}
				//int runId=((BigDecimal)session.createSQLQuery("select incometax_run_id_seq.NEXTVAL from dual").uniqueResult()).intValue();
					//insert into income_tax_txn_details
					session.createSQLQuery("{call IT_INSERT_IT_TXN_DETAILS(?,?,?,?,?,?)}").setString(0, paydto.getSfid()).setInteger(1, finYearId).setString(2, runType).setString(3, paydto.getLastModifiedBy()).setString(4, paydto.getRunMonth()).setInteger(5, paydto.getRunId()).executeUpdate();
					//insert into income_tax_conf_run_details(Exemptions)
					session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, paydto.getSfid()).setInteger(1, finYearId).setString(2, runType).setInteger(3, paydto.getRunId()).setString(4, paydto.getLastModifiedBy()).setString(5, "Exemptions").executeUpdate();
					//insert into income_tax_conf_run_details(Other Income Sources)
					session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, paydto.getSfid()).setInteger(1, finYearId).setString(2, runType).setInteger(3, paydto.getRunId()).setString(4, paydto.getLastModifiedBy()).setString(5, "Other Income Sources").executeUpdate();
					//insert into income_tax_conf_run_details(Savings)
					session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, paydto.getSfid()).setInteger(1, finYearId).setString(2, runType).setInteger(3, paydto.getRunId()).setString(4, paydto.getLastModifiedBy()).setString(5, "Savings").executeUpdate();
				
					
				/**
				 * -------------------------IT Code End-------------------------------->
				 */
				
				message = CPSConstants.YES;
			}
		} catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String validateLoan(String sfid, String loanType) throws Exception {
		Session session = null;
		String message = "No";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_loan(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setString(1, loanType).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateRent(String sfid, int rentAmount) throws Exception {

		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_rent(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, rentAmount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String validateElectricity(String sfid, int elecAmount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_elec(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, elecAmount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateWaterBill(String sfid, int waterAmount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_water(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, waterAmount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateFurnitureBill(String sfid, int furAmount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_furn(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, furAmount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateITTax(String sfid, int itTax, int educCess, int shec) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when "
					+ "(select income_tax from emp_pay_master where sfid=?) =(?+?+?) then "
					+ "case when ?=(select (select income_tax from emp_pay_master where sfid=?)-(round(((((select income_tax from emp_pay_master where sfid=?)*97)/100)*3)/100)) incometax from dual) "
					+ "then "
					+ "case when ?=(select (round(((((select income_tax from emp_pay_master where sfid=?)*97)/100)*2)/100)) incometax from dual) "
					+ "then "
					+ "case when ?=(select (round(((((select income_tax from emp_pay_master where sfid=?)*97)/100)*1)/100)) incometax from dual) "
					+ "then 'Yes' else "
					+ "concat('SHEC value should be ',(select (round(((((select income_tax from emp_pay_master where sfid=?)*97)/100)*1)/100)) incometax from dual)) end "
					+ "else concat('Edu cess value should be ',(select (round(((((select income_tax from emp_pay_master where sfid=?)*97)/100)*2)/100)) incometax from dual)) end "
					+ "else concat('IT value should be ',(select (select income_tax from emp_pay_master where sfid=?)-(round(((((select income_tax from emp_pay_master where sfid=?)*97)/100)*3)/100)) incometax from dual)) "
					+ "end " + "else concat('IT total not matched, value is ',(select income_tax from emp_pay_master where sfid=?)) end " + "temp from dual ";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, itTax).setFloat(2, educCess).setFloat(3, shec).setFloat(4, itTax).setString(5, sfid).setString(6, sfid)
					.setFloat(7, educCess).setString(8, sfid).setFloat(9, shec).setString(10, sfid).setString(11, sfid).setString(12, sfid).setString(13, sfid).setString(14, sfid).setString(15, sfid)
					.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateDeduction(String sfid, String loanType) throws Exception {
		Session session = null;
		String message = "No";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_deduction(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setString(1, loanType).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateResSecurity(String sfid, int resAmount) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_res_secu(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, resAmount).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String validateProfessionalTax(int totalCredits, int profTax) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select Validate_prof_tax(?,?) from dual";

			message = (String) session.createSQLQuery(query).setFloat(0, totalCredits).setFloat(1, profTax).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String validateRegFund(String sfid, int regFund) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_reg_fund(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, regFund).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String validateBenFund(String sfid, int benFund) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_ben_fund(?,?) from dual";
			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, benFund).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	} 

	public String validateWelFund(String sfid, int welFund) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_wel_fund(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, welFund).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String validateCcsSubsAmount(String sfid, int ccsAmout) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_ccs(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, ccsAmout).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}

	public String validateMess(String sfid, int mess) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select validate_mess(?,?) from dual";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, mess).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;

	}
//nagendra.v 
	// synchronize.
	@SuppressWarnings("unchecked")
	public synchronized PayBillStatusDTO startPayAutoRun(String runMonth, String userId) throws Exception {
		Session session = null;
		List<PayBillUserDTO> usersList = null;
		String message = "";
		//Transaction tx = null;
		PayBillStatusDTO payBillStatusDTO = null;
		try { 
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			payBillStatusDTO = new PayBillStatusDTO();
			///int runid = ((BigDecimal) session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE").setString(0, "PAY_RUNID").uniqueResult()).intValue();
			///int runSubId = ((BigDecimal) session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE").setString(0, "PAY_RUN_SUB_ID").uniqueResult()).intValue();
			int runid = Integer.parseInt(commonDataDAO.getSequenceValue(CPSConstants.RUNID));
			int runSubId =Integer.parseInt(commonDataDAO.getSequenceValue(CPSConstants.RUNSUBID));
			 int finYearId = getFinYearId("01-"+runMonth);
			
			String sql = "select count(*) from paybill_status_details where to_char(run_month,'Mon-yyyy')=? ";
			if (Integer.parseInt(session.createSQLQuery(sql).setString(0, runMonth).uniqueResult().toString()) > 0) {
				message = CPSConstants.FAILED;
			} else {
				
				/**
				 * START Updating Emp_Pay_Master table column INCOME_TAX With INCOME_TAX_TXN_DETAILS table TAX_PER_MONTH column
				 */
				//String qry = "UPDATE EMP_PAY_MASTER EPM SET EPM.INCOME_TAX=(SELECT TAX_PER_MONTH FROM INCOME_TAX_TXN_DETAILS WHERE FIN_YEAR_ID="+finYearId+" AND TAX_EFF_MONTH=to_date('01-"+runMonth+"') AND SFID=EPM.SFID) " ;
				String qry="UPDATE EMP_PAY_MASTER EPM SET EPM.INCOME_TAX=(SELECT CASE WHEN EXISTS(SELECT TAX_PER_MONTH FROM INCOME_TAX_TXN_DETAILS" +
						" WHERE FIN_YEAR_ID="+finYearId+" AND TAX_EFF_MONTH=TO_DATE('01-"+runMonth+"') AND SFID =EPM.SFID) THEN (SELECT NVL(TAX_PER_MONTH,0) FROM INCOME_TAX_TXN_DETAILS" +
						" WHERE FIN_YEAR_ID="+finYearId+" AND TAX_EFF_MONTH=TO_DATE('01-"+runMonth+"') AND SFID =EPM.SFID) ELSE EPM.INCOME_TAX  END FROM dual) WHERE STATUS  =1 AND EPM.SFID IN" +
						" (SELECT F_CPERSNO FROM MIS_CREDITS WHERE F_NGROSSPAY !=0)";			 
				//session.createSQLQuery(qry).executeUpdate();
				/**
				 * END Updating Emp_Pay_Master table column INCOME_TAX With INCOME_TAX_TXN_DETAILS table TAX_PER_MONTH column
				 */
				
				payBillStatusDTO.setRunID(runid);
				payBillStatusDTO.setRunSubId(runSubId);
				payBillStatusDTO.setRunMonth("1-" + runMonth);
				payBillStatusDTO.setStatus(1);
				payBillStatusDTO.setPayStatus(Integer.parseInt(CPSConstants.PAYAUTORUNSTATUS));
				payBillStatusDTO.setCreatedBy(userId);
				payBillStatusDTO.setLastModifiedBy(userId);
				payBillStatusDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				payBillStatusDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				session.saveOrUpdate(payBillStatusDTO);
				message = CPSConstants.SUCCESS;
	
			}

			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				/*String query = "select emp.sfid as sfID,epd.basic_id as basicId,epd.grade_id as gradeId,Epd.Category_Id as categoryId,epd.pay_designation_id as payDesignationId,family_planning as fpaFlag," + "handicap_id as phType, "
						+ "grade_pay as gPay,basic_pay as basicPay,epd.ccs_flag as ccsMemFlag,epd.ccs_sub as ccsSubAmt," + "epd.cgeis_group_id as cgeisGroupID,"
						+ "epd.cgeis_mem_status as cgeisMemFlag,epd.fpa_gradepay as fpaGradePay,epd.gpf_flag as gpfFlag,epd.gpf_subs_amt as gpfSubAmt," + "epd.income_tax as incomeTaxAmt,"
						+ "epd.tra_eligible_flag as tptFlag,epd.cghs_flag as cghsFlag," + "epd.two_addl_incr as twoAddIncr,epd.var_incr_points  as varIncrPts,epd.PAY_STOP as payStopFlag ,"
						+ " epd.ccs_no as ccsNo" + " from emp_master emp,emp_pay_master epd " + "where epd.sfid = emp.sfid and emp.sfid in(select F_CPERSNO from MIS_CREDITS where F_NGROSSPAY !=0)";//  */

				String query = "select emp.sfid as sfID,epd.basic_id as basicId,epd.grade_id as gradeId,Epd.Category_Id as categoryId,epd.pay_designation_id as payDesignationId,family_planning as fpaFlag," + "handicap_id as phType, "
				+ "grade_pay as gPay,basic_pay as basicPay,epd.ccs_flag as ccsMemFlag,epd.ccs_sub as ccsSubAmt," + "epd.cgeis_group_id as cgeisGroupID,"
				+ "epd.cgeis_mem_status as cgeisMemFlag,epd.fpa_gradepay as fpaGradePay,epd.gpf_flag as gpfFlag,epd.gpf_subs_amt as gpfSubAmt," + "epd.income_tax as incomeTaxAmt,"
				+ "epd.tra_eligible_flag as tptFlag,epd.cghs_flag as cghsFlag," + "epd.two_addl_incr as twoAddIncr,epd.var_incr_points  as varIncrPts,epd.PAY_STOP as payStopFlag ,"
				+ " epd.ccs_no as ccsNo" + " from emp_master emp,emp_pay_master epd " + "where epd.sfid = emp.sfid";//  AND EMP.SFID='SF0781'
				// status =0 condition removed from emp_master

				usersList = session.createSQLQuery(query).addScalar("sfID", Hibernate.STRING).addScalar("basicId", Hibernate.STRING).addScalar("gradeId", Hibernate.STRING).addScalar("categoryId", Hibernate.STRING).addScalar("payDesignationId", Hibernate.STRING).addScalar("fpaFlag", Hibernate.STRING).addScalar("phType",
						Hibernate.INTEGER).addScalar("gPay", Hibernate.INTEGER).addScalar("basicPay", Hibernate.INTEGER).addScalar("ccsMemFlag", Hibernate.STRING).addScalar("ccsSubAmt",
						Hibernate.INTEGER).addScalar("cgeisGroupID", Hibernate.INTEGER).addScalar("fpaGradePay", Hibernate.INTEGER).addScalar(
						"gpfFlag", Hibernate.STRING).addScalar("gpfSubAmt", Hibernate.INTEGER).addScalar("payStopFlag", Hibernate.STRING).addScalar("incomeTaxAmt", Hibernate.INTEGER).addScalar("ccsNo", Hibernate.STRING).addScalar("tptFlag", Hibernate.STRING).addScalar("cghsFlag", Hibernate.STRING).addScalar("twoAddIncr",
						Hibernate.INTEGER).addScalar("varIncrPts", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayBillUserDTO.class)).list();
				//tx = session.beginTransaction();
				System.out.println("===========basicpays runid"+runid);
				hibernateUtils.closeSession();
				session = hibernateUtils.getSession();
				session.createSQLQuery("{call pay_run_basic_pay_details (?,?,?,?)}").setString(0, "01-"+runMonth).setInteger(1, runid).setInteger(2, runSubId).setString(3, userId).executeUpdate();
				hibernateUtils.closeSession();
               //START--LOANDETAILS--//
					//these loan,deduction procedures for all emps later make it for one emp(based on time taken to run)
				session = hibernateUtils.getSession();
					session.createSQLQuery("{Call Pay_Run_Loan_Details(?,?,?)}").setString(0, "01-"+runMonth).setInteger(1, runid).setString(2, userId).executeUpdate();
					hibernateUtils.closeSession();
					//END--LOAN DETAILS
					
					//START--DEDUCTION DETAILS--//
					session = hibernateUtils.getSession();
					session.createSQLQuery("{call Pay_Run_Deduction_Details(?,?,?)}").setString(0, "01-"+runMonth).setInteger(1, runid).setString(2, userId).executeUpdate();
					hibernateUtils.closeSession();
					//END--DEDUCTION DETAILS--//
					System.out.println("==========runid"+runid);
					System.out.println(runMonth);
					session=hibernateUtils.getSession();
					session.createSQLQuery("{call pay_run_debits(?,?)}").setInteger(0, runid).setString(1, "01-"+runMonth).executeUpdate();
					hibernateUtils.closeSession();
					session = hibernateUtils.getSession();
					session.createSQLQuery("{Call Pay_Run_Credits(?,?)}").setInteger(0, runid).setString(1, "01-"+runMonth).executeUpdate();
					hibernateUtils.closeSession();
					session = hibernateUtils.getSession();
					session.createSQLQuery("{call pay_run_recoveries(?)}").setInteger(0, runid).executeUpdate();
					hibernateUtils.closeSession();
					
				/*for (int i = 0; i < usersList.size(); i++) {*/
					//START---pay_run_basic_pay_details PROCEDURE--------
					
					/*PayBillUserDTO pbuser = usersList.get(i);
					PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO=(PayBillEmpPaymentDeatilsDTO)session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", pbuser.getSfID())).add(Expression.eq("status", 1)).uniqueResult();
			
					PayQuarterManagementDTO payQuarterManagementDTO=calculatePayBill.getEmployeeQuarterDetails(pbuser.getSfID(), CPSUtils.convertStringToDate("01-"+runMonth));
					log.debug("<-------------PAY START FOR SFID :: " + pbuser.getSfID() + " -------------->");
					System.out.println("<-------------PAY START FOR SFID :: " + pbuser.getSfID() + " -------------->");
					PayBillDTO payDTO = new PayBillDTO();
					PayTxnMastersDetailsDTO payTxnMastersDetailsDTO = new PayTxnMastersDetailsDTO();
					String idWithValue = "";
					payDTO.setSfid(pbuser.getSfID());
					payTxnMastersDetailsDTO.setSfid(pbuser.getSfID());
					payTxnMastersDetailsDTO.setPayDesignationId(Integer.parseInt(pbuser.getPayDesignationId()));
					payTxnMastersDetailsDTO.setCategoryId(Integer.parseInt(pbuser.getCategoryId()));
					payTxnMastersDetailsDTO.setBasicId(Integer.parseInt(pbuser.getBasicId()));
					payTxnMastersDetailsDTO.setGradeId(Integer.parseInt(pbuser.getGradeId()));
					PayScaleDesignationDTO payScaleDesignationDTO = (PayScaleDesignationDTO) session.createCriteria(PayScaleDesignationDTO.class).add(
							Expression.eq("designation", pbuser.getPayDesignationId())).add(Expression.eq("status", 1)).uniqueResult();
					payTxnMastersDetailsDTO.setPayBandId(Integer.parseInt(payScaleDesignationDTO.getPayband()));
	
	
					
					// Credits
					
					// Basic Pay Manipulations  
					
					payDTO.setBasicPay(pbuser.getBasicPay());
					
					//START===invalid.cant take grade pay from Emp_basic_Pay_History===kumari===
					 List<EmpBasicPayHistoryDTO> empBasicPayHistoryList=session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,pbuser.getSfID())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("presentEffectiveDate", session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,pbuser.getSfID())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.not(Expression.in("incrementType", new String[]{"P"}))).setProjection(Projections.max("presentEffectiveDate")).uniqueResult())).add(Expression.ne("incrementType", "P")).addOrder(Order.asc("incrementType")).list();
					 for(int k=0;k<empBasicPayHistoryList.size();k++){
						 EmpBasicPayHistoryDTO empBasicPayHistoryDTO=empBasicPayHistoryList.get(k);
						 payBillEmpPaymentDeatilsDTO.setBasicRefId(empBasicPayHistoryDTO.getId()); 
						 payDTO.setBasicPay((int)empBasicPayHistoryDTO.getBasicPay());
					 }
					 //END===invalid.cant take grade pay from Emp_basic_Pay_History===kumari===
					 
					 String bascPayFromAnnualIncrWithRemarks = payBillPreProcess.basicPayPreProcessWithRespToAnnualIncrement(pbuser.getSfID(), runMonth);
						if (Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]) > 0) {
							payDTO.setBasicPay(Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]));
							payBillEmpPaymentDeatilsDTO.setBasicRefId(Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[3]));
							payDTO.setCrRemarks(bascPayFromAnnualIncrWithRemarks.split("@")[1]);
						}
						
						// Grade Pay Manipulations  	
				
					payDTO.setGradePay(pbuser.getgPay());
					payDTO.setTwoAddlIncr(pbuser.getTwoAddIncr());
					
					//START===invalid.cant take grade pay from Emp_grade_Pay_History===kumari===
					   PromoteesEntryDTO promoteesEntryDTO=calculatePayBill.getPromotedTopMostRecord("01-"+runMonth, payDTO.getSfid());
						if(!CPSUtils.isNullOrEmpty(promoteesEntryDTO)){
							payDTO.setGradePay((int)promoteesEntryDTO.getNewGradePay());
							payDTO.setTwoAddlIncr(Integer.parseInt(promoteesEntryDTO.getTwoAddl()));
							int check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth),promoteesEntryDTO.getPresentEffectiveDate());
						    if(check>=0){
						    	check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth), promoteesEntryDTO.getVarIncEndDate());
							    if(check<0){
							    	pbuser.setVarIncrPts(promoteesEntryDTO.getVarIncPt());
							    }else{
							    	pbuser.setVarIncrPts(0);
							    }
						    }else{
						    	pbuser.setVarIncrPts(0);
						    }
						    payBillEmpPaymentDeatilsDTO.setGradeRefId(promoteesEntryDTO.getId());
						}
					
					PayProPreProcessDTO payProPreProcessDTO=payBillPreProcess.integratingWithPromotion(pbuser.getSfID(), "01-"+runMonth, payBillEmpPaymentDeatilsDTO.getGradeRefId());

					if(!CPSUtils.isNullOrEmpty(payProPreProcessDTO)){
						payDTO.setBasicPay(payProPreProcessDTO.getNewBasic());
						payBillEmpPaymentDeatilsDTO.setBasicRefId(payProPreProcessDTO.getBasicId());
						payDTO.setGradePay(payProPreProcessDTO.getNewGrade());
						payBillEmpPaymentDeatilsDTO.setGradeRefId(payProPreProcessDTO.getGradeId());
						payDTO.setTwoAddlIncr(payProPreProcessDTO.getNewTwoAdd());
						pbuser.setVarIncrPts(payProPreProcessDTO.getVarIncrPoints());
						payTxnMastersDetailsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
						payBillEmpPaymentDeatilsDTO.setPayDesignationId(String.valueOf(payProPreProcessDTO.getDesignationId()));
						payTxnMastersDetailsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
	               }*/
					//END---pay_run_basic_pay_details PROCEDURE--------
					
					
				    ///PAY PROCEDURE STOPED HERE===KUMARI
					/*idWithValue = calculatePayBill.getVarIncr(payDTO.getGradePay(), pbuser.getVarIncrPts());
					payDTO.setVariableIncr(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setVarIncrId(Integer.parseInt(idWithValue.split("@")[0]));

					

					idWithValue = calculatePayBill.getDA(payDTO.getBasicPay(), payDTO.getGradePay(), "01-" + runMonth);
					payDTO.setDa(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setDaMasterId(Integer.parseInt(idWithValue.split("@")[0]));

					idWithValue = calculatePayBill.getSplPay("" + pbuser.getPayDesignationId());
					payDTO.setSpecialPay(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setSpecialPayId(Integer.parseInt(idWithValue.split("@")[0]));

					if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getFpaFlag())) {

						idWithValue = calculatePayBill.getFpa(pbuser.getFpaGradePay());
						payDTO.setFpa(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setFpaMastersId(Integer.parseInt(idWithValue.split("@")[0]));
					} else {
						payTxnMastersDetailsDTO.setFpaMastersId(0);
						payDTO.setFpa(0);
					}

					if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getTptFlag())) {
						idWithValue = calculatePayBill.getTra(payDTO.getBasicPay(), pbuser.getSfID(), payDTO.getGradePay(), "01-" + runMonth);
						payDTO.setTpt(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setTraMasterId(Integer.parseInt(idWithValue.split("@")[0]));
					} else {
						payDTO.setTpt(0);
						payTxnMastersDetailsDTO.setTraMasterId(0);
					}*/
					
					
					//payDTO.setHra(calculatePayBill.getHra(payDTO.getBasicPay(), payDTO.getGradePay(),payDTO.getSfid(),new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth)));
					
                   /* idWithValue = calculatePayBill.getWashAllowance(pbuser.getSfID());
					payDTO.setWashAllowance(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setWashAllwId(Integer.parseInt(idWithValue.split("@")[0]));
					
					payDTO.setXeroxAllowance(0);

				
					
					payDTO.setRiskAllowance(0);
					payDTO.setDeputAllowance(0);
					payDTO.setCrMisc(0);

					Object[] hindiInc = calculatePayBill.getHindiIncentive(pbuser.getSfID(),payDTO.getBasicPay(),payDTO.getGradePay(), runMonth);
					payDTO.setHindiIncentive(((BigDecimal) hindiInc[0]).intValue());
					if (!CPSUtils.isNullOrEmpty(hindiInc[1]))
						session.saveOrUpdate(hindiInc[1]);

					/*int totalCredits = payDTO.getBasicPay() + payDTO.getGradePay() + payDTO.getDa() + payDTO.getSpecialPay() + payDTO.getFpa() + payDTO.getTwoAddlIncr() + payDTO.getTpt()
							+ payDTO.getHra() + payDTO.getWashAllowance() + payDTO.getXeroxAllowance() + payDTO.getVariableIncr() + payDTO.getHindiIncentive() + payDTO.getRiskAllowance()
							+ payDTO.getDeputAllowance() + payDTO.getCrMisc();
					payDTO.setTotalCredits(totalCredits);*/
					//int totalCredits=64010;
					
					// Deduction
					/*if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getGpfFlag())) {
						if (Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]) > 0) {
							String flag = iPayBillMasterDAO.checkGPFmember(pbuser.getSfID());
							if (!CPSUtils.compareStrings(flag, CPSConstants.YES)) {
								payDTO.setGpf((int) Math.round((payDTO.getBasicPay() + payDTO.getGradePay() + payDTO.getDa()) * 10 / 100));
								PayBillEmpPaymentDeatilsDTO empPayDTO = (PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(
										Expression.eq("sfID", pbuser.getSfID())).add(Expression.eq("status", 1)).uniqueResult();
								empPayDTO.setGpfSubAmt(payDTO.getGpf());
								session.saveOrUpdate(empPayDTO);
							} else {
								payDTO.setGpf(pbuser.getGpfSubAmt());
							}
						} else {
							payDTO.setGpf(pbuser.getGpfSubAmt());
						}
					} else
						payDTO.setGpf(0);

					payDTO.setPli(0);

					if (CPSUtils.compareStrings(pbuser.getCghsFlag(), CPSConstants.YES)) {
						idWithValue = calculatePayBill.getCGHS(payDTO.getGradePay());
						payDTO.setCghs(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setCghsMastersId(Integer.parseInt(idWithValue.split("@")[0]));
					} else {
						payDTO.setCghs(0);
						payTxnMastersDetailsDTO.setCghsMastersId(0);
					}

					idWithValue = calculatePayBill.getCEGIS(pbuser.getSfID(), pbuser.getCgeisGroupID());
					payDTO.setCegis(Integer.parseInt(idWithValue.split("@")[1]));

					payTxnMastersDetailsDTO.setCgeisMastersId(Integer.parseInt(idWithValue.split("@")[0]));

					if (!CPSUtils.isNullOrEmpty(payQuarterManagementDTO)) {

						idWithValue = calculatePayBill.getLicenceFee(pbuser.getSfID());
						payDTO.setRent(Integer.parseInt(idWithValue.split("@")[1]));
						payDTO.setElec(Integer.parseInt(idWithValue.split("@")[4]));
						payDTO.setWater(Integer.parseInt(idWithValue.split("@")[2]));
						payDTO.setFurn(Integer.parseInt(idWithValue.split("@")[3]));
						payTxnMastersDetailsDTO.setLicFeeMastersId(Integer.parseInt(idWithValue.split("@")[0]));

						idWithValue = calculatePayBill.getResSecurity(pbuser.getSfID());
						payDTO.setResSecu(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setResSecuMastersId(Integer.parseInt(idWithValue.split("@")[0]));

					} else {
						payDTO.setRent(0);
						payDTO.setResSecu(0);
						payDTO.setElec(0);
						payDTO.setWater(0);
						payDTO.setFurn(0);
					}*/
					/*Object[] loan = new Object[]{0,0,0,0,null,null,null};*/
					
					
					/*Object[] loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORCYCLELOANID, runMonth,runid, userId);

					payDTO.setCycleLoan((Integer) loan[0]);
					payDTO.setCycleTotInst((Integer) loan[1]);
					payDTO.setCycleInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCycleLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);
					
					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.FESTIVALLOANID, runMonth,runid, userId);
					payDTO.setFestivalAdv((Integer) loan[0]);
					payDTO.setFestivTotInst((Integer) loan[1]);
					payDTO.setFestivInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setFestLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);
					
					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.GPFADVANCELOANID, runMonth,runid, userId);
					payDTO.setGpfRecovery((Integer) loan[0]);
					payDTO.setGpfTotAmt((Integer) loan[1]);
					payDTO.setGpfPreInst((Integer) loan[2]);
					payTxnMastersDetailsDTO.setGpfAdvanceLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);

					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORCARLOANID, runMonth,runid, userId);
					payDTO.setCarLoan((Integer) loan[0]);
					payDTO.setCarTotInst((Integer) loan[1]);
					payDTO.setCarInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCarLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);

					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.PCLOANID, runMonth,runid, userId);
					payDTO.setPcLoan((Integer) loan[0]);
					payDTO.setPcTotInst((Integer) loan[1]);
					payDTO.setPcInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setPcLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);


					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORSCOOTERLOANID, runMonth,runid, userId);
					payDTO.setScooterLoan((Integer) loan[0]);
					payDTO.setScooterTotInst((Integer) loan[1]);
					payDTO.setScooterInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setScooterLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);

					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.HBALOANID, runMonth,runid, userId);
					payDTO.setHbaLoan((Integer) loan[0]);
					payDTO.setHbaTotInst((Integer) loan[1]);
					payDTO.setHbaInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setHbaLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);
					
                    loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.WELFARELOANID, runMonth,runid, userId);
					
					payDTO.setWelRefund((Integer) loan[1]);
					payDTO.setWelTotinst((Integer) loan[1]);
					payDTO.setWelInst((Integer) loan[1]);
					payTxnMastersDetailsDTO.setWelFundId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);*/

					/*payDTO.setIncomeTax(calculatePayBill.getITax(pbuser.getIncomeTaxAmt()));
					payDTO.setCess(calculatePayBill.getCess(pbuser.getIncomeTaxAmt()));
					payDTO.setSecondaryCess(calculatePayBill.getSHEC(pbuser.getIncomeTaxAmt()));
					if (pbuser.getIncomeTaxAmt() < payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess())
						payDTO.setIncomeTax(payDTO.getIncomeTax() - 1);
					else if (pbuser.getIncomeTaxAmt() > payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess())
						payDTO.setIncomeTax(payDTO.getIncomeTax() + 1);
                    //write query for getting total credits
					idWithValue = calculatePayBill.getProfTax(pbuser.getSfID(), 64010);
					payDTO.setProfTax(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setProfTaxId(Integer.parseInt(idWithValue.split("@")[0]));
					payDTO.setGpfSa(0);
					payDTO.setTada(0);
					payDTO.setMedical(0);
					payDTO.setLtc(0);
					payDTO.setEol(0);
					payDTO.setDrMisc1(0);
					payDTO.setDrMisc2(0);

					int totalDebits = payDTO.getGpf() + payDTO.getGpfSa() + payDTO.getGpfRecovery() + payDTO.getPli() + payDTO.getCghs() + payDTO.getCegis() + payDTO.getRent() + payDTO.getElec()
							+ payDTO.getWater() + payDTO.getFurn() + payDTO.getCycleLoan() + payDTO.getFestivalAdv() + payDTO.getCarLoan() + payDTO.getPcLoan() + payDTO.getScooterLoan()
							+ payDTO.getHbaLoan() + payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess() + payDTO.getProfTax() + payDTO.getTada() + payDTO.getMedical()
							+ payDTO.getLtc() + payDTO.getEol() + payDTO.getDrMisc1() + payDTO.getDrMisc2();
					payDTO.setTotalDebits(totalDebits);*/
					//write query for getting total credits
					//payDTO.setNetPay(totalCredits - totalDebits);

					// Coin Cut or Recoveries
//--start:recoveries procedure added--
					/*idWithValue = calculatePayBill.getWelFund(pbuser.getPayDesignationId());
					payDTO.setWelC(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setWelFundId(Integer.parseInt(idWithValue.split("@")[0]));
					
					idWithValue = calculatePayBill.getMess(pbuser.getPayDesignationId(),payQuarterManagementDTO==null?"No":"Yes");
					payDTO.setMess(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setMessId(Integer.parseInt(idWithValue.split("@")[0]));
					

					idWithValue = calculatePayBill.getBenFund(pbuser.getPayDesignationId());
					payDTO.setBenvolentFund(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setBenFundId(Integer.parseInt(idWithValue.split("@")[0]));

					idWithValue = calculatePayBill.getRegFund(pbuser.getPayDesignationId());
					payDTO.setRegimentalFund(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setRegFundId(Integer.parseInt(idWithValue.split("@")[0]));

					payDTO.setCcs(pbuser.getCcsSubAmt());
					payDTO.setCcsrecovery(calculatePayBill.getCcsRecovery(pbuser.getSfID(), pbuser.getCcsSubAmt()));*/
					//--end:recoveries procedure added--

					/*loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.CANFIN, runMonth,runid,userId);

					payDTO.setCanfin(((Float) loan[0]).intValue());
					payDTO.setCanfinTotInst((Integer) loan[1]);
					payDTO.setCanfinInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCanfinDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.HDFC, runMonth,runid,userId);
					payDTO.setHdfc(((Float) loan[0]).intValue());
					payDTO.setHdfcTotInst((Integer) loan[1]);
					payDTO.setHdfcInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setHdfcDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.GIC, runMonth,runid,userId);
					payDTO.setGic(((Float) loan[0]).intValue());
					payDTO.setGicTotInst((Integer) loan[1]);
					payDTO.setGicInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setGicDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.LIC, runMonth,runid,userId);
					payDTO.setLic(((Float) loan[0]).intValue());
					payDTO.setLicTotInst((Integer) loan[1]);
					payDTO.setLicInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setLicDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.COURTATTACHMENT, runMonth,runid,userId);
					payDTO.setCourtAttachment(((Float) loan[0]).intValue());
					payDTO.setCourtTotInst((Integer) loan[1]);
					payDTO.setCourtInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCourtAttchDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);*/
					
					//start:recoveries----//
					/*payDTO.setWelRefund(0);
					payDTO.setRecMisc1(0);
					payDTO.setRecMisc2(0);
					payDTO.setRecMisc3(0);
					payDTO.setCcsrecovery(0);
					int totalRecoveries = payDTO.getWelC() + payDTO.getMess() + payDTO.getBenvolentFund() + payDTO.getRegimentalFund() + payDTO.getCcs() + payDTO.getCcsrecovery() + payDTO.getCanfin()
							+ payDTO.getHdfc() + payDTO.getGic() + payDTO.getLic() + payDTO.getCourtAttachment() + payDTO.getWelRefund() + payDTO.getResSecu() + payDTO.getRecMisc1()
							+ payDTO.getRecMisc2() + payDTO.getRecMisc3();

					payDTO.setTotalRecovery(totalRecoveries);
					payDTO.setFinalPay(totalCredits - (totalDebits + totalRecoveries));*/
					//end:recoveries----//
					
					//START---pay_run_basic_pay_details PROCEDURE--------
					/*payDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					payTxnMastersDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					payDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					payTxnMastersDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					payDTO.setCreatedBy(userId);
					payTxnMastersDetailsDTO.setCreatedBy(userId);
					payDTO.setLastModifiedBy(userId);
					payTxnMastersDetailsDTO.setLastModifiedBy(userId);
					payDTO.setRunId(runid);
					payDTO.setRunSubId(runSubId);
					payTxnMastersDetailsDTO.setRunId(runid);
					payTxnMastersDetailsDTO.setRunSubId(runSubId);
					payDTO.setRunMonth("1-" + runMonth);
					payTxnMastersDetailsDTO.setRunMonth("1-" + runMonth);
					payDTO.setStatus(Integer.parseInt(CPSConstants.PAYAUTORUNSTATUS));
					payTxnMastersDetailsDTO.setStatus(50);
					payTxnMastersDetailsDTO.setEolHplId(0);

					PayBillDTO payDTO1 = new PayBillDTO();

					if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getPayStopFlag())) {
						payDTO1.setSfid(payDTO.getSfid());
						payDTO1.setRunId(payDTO.getRunId());
						payDTO1.setRunMonth(payDTO.getRunMonth());
						payDTO1.setCreationTime(payDTO.getCreationTime());
						payDTO1.setCreatedBy(payDTO.getCreatedBy());
						payDTO1.setLastModifiedBy(payDTO.getLastModifiedBy());
						payDTO1.setLastModifiedTime(payDTO.getLastModifiedTime());
						payDTO1.setStatus(1);
					} else {
						BeanUtils.copyProperties(payDTO1, payDTO);
						payDTO1.setStatus(1); //o is changed to 1 by Narayana
					}
					session.saveOrUpdate(payTxnMastersDetailsDTO);
					session.saveOrUpdate(payDTO);
					session.saveOrUpdate(payDTO1);
					session.saveOrUpdate(payBillEmpPaymentDeatilsDTO);
					session.flush();*/
					//END---pay_run_basic_pay_details PROCEDURE--------
					
					
					/*//START--LOANDETAILS--//
					//these loan,deduction procedures for all emps later make it for one emp(based on time taken to run)
					session.createSQLQuery("{Call Pay_Run_Loan_Details(?,?,?)}").setString(0, "01-"+runMonth).setInteger(1, runid).setString(2, userId).executeUpdate();
					//END--LOAN DETAILS
					
					//START--DEDUCTION DETAILS--//
					session.createSQLQuery("{call Pay_Run_Deduction_Details(?,?,?)}").setString(0, "01-"+runMonth).setInteger(1, runid).setString(2, userId).executeUpdate();
					
					//END--DEDUCTION DETAILS--//
					session.createSQLQuery("{Call Pay_Run_Credits(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}").setString(0, pbuser.getSfID()).setInteger(1, runid).setString(2, "01-"+runMonth).setInteger(3, payDTO.getBasicPay()).setInteger(4, payDTO.getGradePay()).setInteger(5, pbuser.getVarIncrPts()).setString(6, pbuser.getPayDesignationId()).setString(7, pbuser.getFpaFlag()).setInteger(8, pbuser.getFpaGradePay()).setString(9, pbuser.getTptFlag()).setInteger(10, payDTO.getTwoAddlIncr()).setInteger(11, payDTO.getHra()).setInteger(12, payDTO.getHindiIncentive()).setString(13, pbuser.getPayStopFlag()).executeUpdate();
					System.out.println("credit PARAMS===="+ pbuser.getSfID()+"-"+runid+"--01-"+runMonth+"-"+payDTO.getBasicPay()+"-"+ payDTO.getGradePay()+"-"+ pbuser.getVarIncrPts()+"-"+pbuser.getPayDesignationId()+"-"+ pbuser.getFpaFlag()+"-"+ pbuser.getFpaGradePay()+"-"+ pbuser.getTptFlag()+"-"+ payDTO.getTwoAddlIncr()+"-"+ payDTO.getHra()+"-"+ payDTO.getHindiIncentive());
					session.createSQLQuery("{call pay_run_debits(?,?,?,?,?,?,?,?,?,?,?,?)}").setString(0, pbuser.getSfID()).setInteger(1, runid).setInteger(2, Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0])).setInteger(3, payDTO.getBasicPay()).setInteger(4,payDTO.getGradePay()).setInteger(5,payDTO.getDa()).setString(6, pbuser.getGpfFlag()).setInteger(7, pbuser.getGpfSubAmt()).setString(8, pbuser.getCghsFlag()).setInteger(9, pbuser.getIncomeTaxAmt()).setInteger(10, pbuser.getCgeisGroupID()).setString(11, pbuser.getPayStopFlag()).executeUpdate();
					System.out.println("debit PARAMS===="+ pbuser.getSfID()+"-"+runid+"-"+Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0])+"-"+payDTO.getBasicPay()+"-"+ payDTO.getGradePay()+"-"+ payDTO.getDa()+"-"+pbuser.getGpfFlag()+"-"+ pbuser.getGpfSubAmt()+"-"+ pbuser.getCghsFlag()+"-"+ pbuser.getIncomeTaxAmt()+"-"+ pbuser.getCgeisGroupID());
					session.createSQLQuery("{call pay_run_recoveries(?,?,?,?,?)}").setString(0, pbuser.getSfID()).setInteger(1, runid).setString(2, pbuser.getPayDesignationId()).setInteger(3, pbuser.getCcsSubAmt()).setString(4, pbuser.getPayStopFlag()).executeUpdate();
					System.out.println("recovery PARAMS===="+ pbuser.getSfID()+"-"+runid+"-"+pbuser.getPayDesignationId()+"-"+pbuser.getCcsSubAmt());*/
					//int totalCredits=((BigDecimal)session.createSQLQuery("select Gross_Pay from pay_txn_details where status=50 and sfid=? and Run_Id=?").setString(0, pbuser.getSfID()).setInteger(1, runid).uniqueResult()).intValue();
				/*}*/
				//session.flush();//tx.commit() ;
				message = CPSConstants.SUCCESS;

				payBillStatusDTO.setRunStatus(0);
				payBillStatusDTO.setManualStatus(1);
				payBillStatusDTO.setUserStatus(0);
				payBillStatusDTO.setDescription(message);
				payBillStatusDTO.setCount(getCountToGeneratePayBill());
				payBillStatusDTO.setGeneratedCount(getCountForAlreadyGeneratedPayBill(runMonth));
				
				/*//START--LOANDETAILS--//
				
				session.createSQLQuery("{Call Pay_Run_Loan_Details(?,?,?)}").setString(0, "01-"+runMonth).setInteger(1, runid).setString(2, userId).executeUpdate();
				//END--LOAN DETAILS
				
				//START--DEDUCTION DETAILS--//
				session.createSQLQuery("{call Pay_Run_Deduction_Details(?,?,?)}").setString(0, "01-"+runMonth).setInteger(1, runid).setString(2, userId).executeUpdate();
				
				//END--DEDUCTION DETAILS--//
*/				/**
				 * ------------------------IT CODE START---------------->
				 */
				//Inserting Data into PAY_IT_PAYBILL_DETAILS TABLE
				//int runId=((BigDecimal)session.createSQLQuery("select incometax_run_id_seq.NEXTVAL from dual").uniqueResult()).intValue();
				session.createSQLQuery("{call it_insert_paybill_details(?,?,?,?)}").setInteger(0, finYearId).setString(1, "01-"+runMonth).setString(2, "").setInteger(3, runid).executeUpdate();
	
				String qry =" SELECT RUN_TYPE FROM(SELECT ID,RUN_TYPE,ROWNUM ROWVAL FROM INCOME_TAX_DURATION_DETAILS WHERE STATUS=1 AND " +
						    "FIN_YEAR_ID=? AND TO_DATE < to_date(?,'dd-Mon-yyyy') ORDER BY TO_DATE DESC) WHERE ROWVAL=1";
				String runType = (String)session.createSQLQuery(qry).setInteger(0, finYearId).setString(1, "01-"+runMonth).uniqueResult();
				if(CPSUtils.isNullOrEmpty(runType)){
					runType="pl";
				}
				//int runId=((BigDecimal)session.createSQLQuery("select incometax_run_id_seq.NEXTVAL from dual").uniqueResult()).intValue();
				System.out.println("==========sfid=========finYearId"+finYearId+"runType"+runType+"userId"+userId+"runMonth"+runMonth);
				//insert into income_tax_txn_details
				session=hibernateUtils.getSession();
				session.createSQLQuery("{call IT_INSERT_IT_TXN_DETAILS(?,?,?,?,?,?)}").setString(0, "").setInteger(1, finYearId).setString(2, runType).setString(3, userId).setString(4, "01-"+runMonth).setInteger(5, runid).executeUpdate();
			hibernateUtils.closeSession();
				//insert into income_tax_conf_run_details(Exemptions)
			session=hibernateUtils.getSession();
				session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, "").setInteger(1, finYearId).setString(2, runType).setInteger(3, runid).setString(4, userId).setString(5, "Exemptions").executeUpdate();
				//insert into income_tax_conf_run_details(Other Income Sources)
				hibernateUtils.closeSession();
				session=hibernateUtils.getSession();
				session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, "").setInteger(1, finYearId).setString(2, runType).setInteger(3, runid).setString(4, userId).setString(5, "Other Income Sources").executeUpdate();
				hibernateUtils.closeSession();
				//insert into income_tax_conf_run_details(Savings)
				session=hibernateUtils.getSession();
				session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, "").setInteger(1, finYearId).setString(2, runType).setInteger(3, runid).setString(4, userId).setString(5, "Savings").executeUpdate();
				hibernateUtils.closeSession();
				/*for (int i = 0; i < usersList.size(); i++) {
					PayBillUserDTO pbuser1 = usersList.get(i);
					System.out.println("==========sfid========="+pbuser1.getSfID()+"finYearId"+finYearId+"runType"+runType+"userId"+userId+"runMonth"+runMonth);
					//insert into income_tax_txn_details
					session.createSQLQuery("{call IT_INSERT_IT_TXN_DETAILS(?,?,?,?,?)}").setString(0, pbuser1.getSfID()).setInteger(1, finYearId).setString(2, runType).setString(3, userId).setString(4, "01-"+runMonth).executeUpdate();
					//insert into income_tax_conf_run_details(Exemptions)
					session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, pbuser1.getSfID()).setInteger(1, finYearId).setString(2, runType).setInteger(3, runId).setString(4, userId).setString(5, "Exemptions").executeUpdate();
					//insert into income_tax_conf_run_details(Other Income Sources)
					session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, pbuser1.getSfID()).setInteger(1, finYearId).setString(2, runType).setInteger(3, runId).setString(4, userId).setString(5, "Other Income Sources").executeUpdate();
					//insert into income_tax_conf_run_details(Savings)
					session.createSQLQuery("{ call Insert_It_Pl_Exemptions(?,?,?,?,?,?) }").setString(0, pbuser1.getSfID()).setInteger(1, finYearId).setString(2, runType).setInteger(3, runId).setString(4, userId).setString(5, "Savings").executeUpdate();
				}*/
					
				
				/**
				 * -------------------------IT CODE END---------------->
				 */
			} else {
				message = CPSConstants.AUTORUNEXIST;
			}
		} catch (Exception e) {
			//tx.rollback();
			e.printStackTrace();
			hibernateUtils.closeSession();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//tx.commit();
			//session.close();
		}
		return payBillStatusDTO;
	}

	public int getTableID(String tableName) throws Exception {
		Session session = null;
		int id = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			id = (Integer) session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE").setString(0, tableName).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return id;
	}

	public PayBillStatusDTO getRunMonthDetails() throws Exception {
		Session session = null;
		PayBillStatusDTO payBillStatusDTO = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
            payBillStatusDTO = (PayBillStatusDTO) session.createCriteria(PayBillStatusDTO.class).add(Expression.or(Expression.eq("payStatus", 50), Expression.eq("payStatus", 51))).add(
					Expression.eq("status", 1)).uniqueResult();
			

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return payBillStatusDTO;
	}
	//code for supplementary
    public PayBillStatusDTO getSuppleeqmentaryRunMonthDetails(PayBillBean payBillBean) throws Exception{
    	Session session = null;
    	PayBillStatusDTO payBillStatusDTO = null;
    	try{
    		session = hibernateUtils.getSession();
    		//payBillStatusDTO=(PayBillStatusDTO)session.createCriteria(PayBillStatusDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("payStatus", 52)).uniqueResult();
    	    String query ="select run_id as runID,run_sub_id as runSubId from paybill_status_details where pay_status=52 and run_month = '"+"01-"+payBillBean.getRunMonth()+"'";
    	    payBillStatusDTO=(PayBillStatusDTO)session.createSQLQuery(query).addScalar("runID", Hibernate.INTEGER).addScalar("runSubId", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayBillStatusDTO.class)).uniqueResult();
    	}catch (Exception e) {
			throw e;
		}
    	return payBillStatusDTO;
    }
	public PayBillStatusDTO getPayBillStatus() throws Exception {

		Session session = null;
		PayBillStatusDTO paybillstatusDTO = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select count(*) from paybill_status_details";
			 //satya.
			if (Integer.parseInt(session.createSQLQuery(query).uniqueResult().toString()) > 0) {
				paybillstatusDTO = (PayBillStatusDTO) session
						.createSQLQuery(

								"Select To_Char(Run_Month,'Mon-yyyy') As runMonth ,Run_Id As runID,Pay_Status As payStatus,To_Char(Add_Months(Run_Month,1),'Mon-yyyy') As name from Paybill_Status_Details where status=1 and run_month=(select max(run_month) from paybill_status_details where status not in(0,2)) order by Run_Month desc")

								//"Select To_Char(Run_Month,'Mon-yyyy') As runMonth ,Run_Id As runID,Pay_Status As payStatus,To_Char(Add_Months(Run_Month,1),'Mon-yyyy') As name from Paybill_Status_Details where rownum=1 order by Run_Month desc")
						//"Select To_Char(Run_Month,'Mon-yyyy') As runMonth ,Run_Id As runID,Pay_Status As payStatus,To_Char(Add_Months(Run_Month,1),'Mon-yyyy') As name from Paybill_Status_Details where run_month=(select max(run_month) from paybill_status_details) order by Run_Month desc,pay_status desc")

						.addScalar("runMonth", Hibernate.STRING).addScalar("runID", Hibernate.INTEGER).addScalar("payStatus", Hibernate.INTEGER).addScalar("name", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(PayBillStatusDTO.class)).list().get(0);
				if (paybillstatusDTO.getPayStatus() == Integer.parseInt(CPSConstants.PAYAUTORUNSTATUS)) {
					paybillstatusDTO.setRunStatus(0);
					paybillstatusDTO.setManualStatus(1);
					paybillstatusDTO.setUserStatus(0);
				} else if (paybillstatusDTO.getPayStatus() == Integer.parseInt(CPSConstants.PAYMANUALRUNSTATUS)) {
					paybillstatusDTO.setRunStatus(0);
					paybillstatusDTO.setManualStatus(2);
					paybillstatusDTO.setUserStatus(1);
				} else if (paybillstatusDTO.getPayStatus() == Integer.parseInt(CPSConstants.PAYUSERVISIBLESTATUS)) {
					paybillstatusDTO.setRunStatus(1);
					paybillstatusDTO.setManualStatus(0);
					paybillstatusDTO.setUserStatus(0);
					paybillstatusDTO.setDescription(paybillstatusDTO.getRunMonth() + " month pay bill completed.");
					
					paybillstatusDTO.setRunMonth(paybillstatusDTO.getName());

				}
				paybillstatusDTO.setCount(getCountToGeneratePayBill());
				paybillstatusDTO.setGeneratedCount(getCountForAlreadyGeneratedPayBill(paybillstatusDTO.getRunMonth()));
			    //new code to get missing sfid list
				paybillstatusDTO.setSfidList(missingSfidForAlreadyGeneratedPayBill(paybillstatusDTO.getRunMonth()));
			} else {
                String messsage = (String) session.createSQLQuery("select to_char(sysdate,'Mon-yyyy') as run_month from dual").uniqueResult();
				paybillstatusDTO = new PayBillStatusDTO();
				paybillstatusDTO.setRunStatus(1);
				paybillstatusDTO.setManualStatus(0);
				paybillstatusDTO.setUserStatus(0);
				paybillstatusDTO.setRunMonth(messsage);
				paybillstatusDTO.setCount(getCountToGeneratePayBill());
				paybillstatusDTO.setGeneratedCount(getCountForAlreadyGeneratedPayBill(messsage));
				
			}
			
		
	} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return paybillstatusDTO;
	}

	@SuppressWarnings("unchecked")
	public PayBillStatusDTO changeStatusToManual(String runMonth, String sfid) throws Exception {
		Session session = null;
		PayBillStatusDTO paybillstatusDTO = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			
			String sql = "select count(*) from pay_txn_details where take_home<0 and (status=1 or status=60 )and to_char(run_month,'Mon-yyyy')=?"; //0 is changed to 1 by Narayana
			
			if (Integer.parseInt(session.createSQLQuery(sql).setString(0, runMonth).uniqueResult().toString()) > 0) {
				paybillstatusDTO = new PayBillStatusDTO();
				paybillstatusDTO.setRunMonth(runMonth);
				paybillstatusDTO.setRunStatus(0);
				paybillstatusDTO.setManualStatus(1);
				paybillstatusDTO.setUserStatus(0);
				paybillstatusDTO.setDescription(CPSConstants.NEGPAYEMPEXIST);

			} else {
				paybillstatusDTO = getRunMonthDetails();
				paybillstatusDTO.setRunMonth("1-" + runMonth);
				paybillstatusDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				paybillstatusDTO.setPayStatus(Integer.parseInt(CPSConstants.PAYMANUALRUNSTATUS));
				paybillstatusDTO.setLastModifiedBy(sfid);
				paybillstatusDTO.setRunStatus(0);
				paybillstatusDTO.setManualStatus(2);
				paybillstatusDTO.setUserStatus(1);
				paybillstatusDTO.setDescription(CPSConstants.SUCCESS);
				session.saveOrUpdate(paybillstatusDTO);

				String updateQuery = "update pay_txn_details set status=? where status in(?,?) ";
				session.createSQLQuery(updateQuery).setString(0, CPSConstants.PAYMANUALRUNSTATUS).setString(1, "1").setString(2, "60").executeUpdate(); //0 is changed to 1 by Narayana
				//session.createSQLQuery("update id_generator set value=value+1 where table_name='PAY_RUNID'").executeUpdate();
				//session.createSQLQuery("update id_generator set value=? where table_name='PAY_RUN_SUB_ID'").setInteger(0,1).executeUpdate();
				
				List<PayBillLoanDTO> payBillLoanList=session.createCriteria(PayBillLoanDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("runId", paybillstatusDTO.getRunID())).list();
				for(PayBillLoanDTO payBillLoanDTO:payBillLoanList){
					if(payBillLoanDTO.getPresentInst()!=payBillLoanDTO.getTotalInst()){
						PayBillLoanDTO newLoanForNextMonthDTO=new PayBillLoanDTO();
						BeanUtils.copyProperties(newLoanForNextMonthDTO, payBillLoanDTO);
						newLoanForNextMonthDTO.setId(0);
						newLoanForNextMonthDTO.setRunId(0);
						newLoanForNextMonthDTO.setReferenceId(payBillLoanDTO.getId());
						newLoanForNextMonthDTO.setPresentInst(newLoanForNextMonthDTO.getPresentInst()+1);
						 //satya.
						if(newLoanForNextMonthDTO.getPresentInst()==newLoanForNextMonthDTO.getTotalInst()){
								newLoanForNextMonthDTO.setEmi(newLoanForNextMonthDTO.getOutStandAmt());
							}
						newLoanForNextMonthDTO.setOutStandAmt(newLoanForNextMonthDTO.getOutStandAmt()-newLoanForNextMonthDTO.getEmi());
							
						session.saveOrUpdate(newLoanForNextMonthDTO);
					}
					payBillLoanDTO.setStatus(2);
					session.saveOrUpdate(payBillLoanDTO);
				}
				
				List<PayBillCanfinDTO> payBillDeductionList=session.createCriteria(PayBillCanfinDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("runId", paybillstatusDTO.getRunID())).list();
				for(PayBillCanfinDTO payBillCanfinDTO:payBillDeductionList){
					if (payBillCanfinDTO.getPresentInst()!=payBillCanfinDTO.getNoOfInst()) {
						PayBillCanfinDTO newDeductionDTO=new PayBillCanfinDTO();
						BeanUtils.copyProperties(newDeductionDTO, payBillCanfinDTO);
						newDeductionDTO.setId(0);
						newDeductionDTO.setRunId(0);
						newDeductionDTO.setReferenceId(payBillCanfinDTO.getId());
						newDeductionDTO.setPresentInst(newDeductionDTO.getPresentInst()+1);
					   
						session.saveOrUpdate(newDeductionDTO);
					}
					payBillCanfinDTO.setStatus(2);
					session.saveOrUpdate(payBillCanfinDTO);
				}
				
				
				List<PayBillDTO> payTxnList=session.createCriteria(PayBillDTO.class).add(Expression.eq("runId", paybillstatusDTO.getRunID())).add(Expression.eq("status",50)).list();
				
				for(PayBillDTO dto:payTxnList){
					//System.out.println("---------------------------------------");
					//System.out.println("forloop execution"+dto.getSfid());
					//System.out.println("dto object value"+dto);
					PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO=(PayBillEmpPaymentDeatilsDTO)
							session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", dto.getSfid()))
							.add(Expression.eq("status", 1)).uniqueResult();
					 //  System.out.println("basic rfid"+payBillEmpPaymentDeatilsDTO.getBasicRefId());
					if(!CPSUtils.isNullOrEmpty(payBillEmpPaymentDeatilsDTO)){
						if(payBillEmpPaymentDeatilsDTO.getBasicRefId()!=0){
                            System.out.println(payBillEmpPaymentDeatilsDTO.getSfID());
                       
                            System.out.println("Basic pay refenceid"+payBillEmpPaymentDeatilsDTO.getBasicRefId());
                            EmpBasicPayHistoryDTO empBasicPayHistoryDTO=(EmpBasicPayHistoryDTO)session.get(EmpBasicPayHistoryDTO.class, payBillEmpPaymentDeatilsDTO.getBasicRefId());
                   //testing code start by nagendra
                           
                            if(empBasicPayHistoryDTO!=null)
                            	 /*System.out.println("basic pay"+empBasicPayHistoryDTO.getBasicPay());
                            System.out.println("basic rfid"+payBillEmpPaymentDeatilsDTO.getBasicRefId());
                              System.out.println("empbaiscpayhistorydto"+empBasicPayHistoryDTO);*/
                            	
                      //testing code end...      	
							payBillEmpPaymentDeatilsDTO.setBasicPay((int)empBasicPayHistoryDTO.getBasicPay());
							//--no need to update basic_pay_history it is already updated at annual increment update screen--//kumari
							/*if(payBillEmpPaymentDeatilsDTO.getBasicRefId()!=payBillEmpPaymentDeatilsDTO.getBasicId()){
								EmpBasicPayHistoryDTO empBasicPayPreviousDTO=(EmpBasicPayHistoryDTO)session.get(EmpBasicPayHistoryDTO.class, payBillEmpPaymentDeatilsDTO.getBasicId());
								if(!CPSUtils.isNullOrEmpty(empBasicPayPreviousDTO)){
								empBasicPayPreviousDTO.setStatus(2);
								//satya.
								empBasicPayPreviousDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
								session.saveOrUpdate(empBasicPayPreviousDTO);
								}
							}*/
						
							/*
							 * 26/12/2014 :: Check this code 
							 */
							payBillEmpPaymentDeatilsDTO.setBasicId(payBillEmpPaymentDeatilsDTO.getBasicRefId());
							payBillEmpPaymentDeatilsDTO.setBasicRefId(0);
						
						}
						if(payBillEmpPaymentDeatilsDTO.getGradeRefId()!=0){
                            System.out.println(payBillEmpPaymentDeatilsDTO.getSfID());
							PromoteesEntryDTO promoteesEntryDTO=(PromoteesEntryDTO)session.get(PromoteesEntryDTO.class, payBillEmpPaymentDeatilsDTO.getGradeRefId());
							if(promoteesEntryDTO !=null){
								int check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth),promoteesEntryDTO.getPresentEffectiveDate());
							    if(check>=0){
							    	check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth),promoteesEntryDTO.getVarIncEndDate());
								    if(check<0) payBillEmpPaymentDeatilsDTO.setVarIncrPts(promoteesEntryDTO.getVarIncPt());
								    else payBillEmpPaymentDeatilsDTO.setVarIncrPts(0);
							    }else payBillEmpPaymentDeatilsDTO.setVarIncrPts(0);
							    //--update grade pay in emp_pay_master--//kumari
								payBillEmpPaymentDeatilsDTO.setgPay((int)promoteesEntryDTO.getNewGradePay());
								
							    payBillEmpPaymentDeatilsDTO.setGradeId(payBillEmpPaymentDeatilsDTO.getGradeRefId());
							    payBillEmpPaymentDeatilsDTO.setGradeRefId(0);
							    payBillEmpPaymentDeatilsDTO.setPayDesignationId(promoteesEntryDTO.getPromotedDesignation());
						}
							}
							 
						String flag = iPayBillMasterDAO.checkGPFmember(payBillEmpPaymentDeatilsDTO.getSfID());
						if (!CPSUtils.compareStrings(flag, CPSConstants.YES)) {
							payBillEmpPaymentDeatilsDTO.setGpfSubAmt((int)Math.round(0.1*(payBillEmpPaymentDeatilsDTO.getBasicPay()+payBillEmpPaymentDeatilsDTO.getgPay()+Integer.parseInt(calculatePayBill.getDA(payBillEmpPaymentDeatilsDTO.getBasicPay(), payBillEmpPaymentDeatilsDTO.getgPay(), "01-"+runMonth).split("@")[1]))));
						}
						payBillEmpPaymentDeatilsDTO.setCreationDate(payBillEmpPaymentDeatilsDTO.getCreationDate());
						payBillEmpPaymentDeatilsDTO.setLastModifiedDate(payBillEmpPaymentDeatilsDTO.getLastModifiedDate());
						session.saveOrUpdate(payBillEmpPaymentDeatilsDTO);
					}
		         } 
			}
			
			session.flush();//tx.commit() ;
			paybillstatusDTO.setCount(getCountToGeneratePayBill());
			paybillstatusDTO.setGeneratedCount(getCountForAlreadyGeneratedPayBill(runMonth));
		}catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return paybillstatusDTO;
	}

	public PayBillStatusDTO changeStatusToVisible(String runMonth, String sfid) throws Exception {
		Session session = null;
		PayBillStatusDTO paybillstatusDTO = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			paybillstatusDTO = getRunMonthDetails();
			paybillstatusDTO.setRunMonth("1-" + runMonth);
			paybillstatusDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			paybillstatusDTO.setPayStatus(Integer.parseInt(CPSConstants.PAYUSERVISIBLESTATUS));
			paybillstatusDTO.setStatus(1);
			paybillstatusDTO.setLastModifiedBy(sfid);
			paybillstatusDTO.setRunStatus(0);
			paybillstatusDTO.setManualStatus(2);
			paybillstatusDTO.setUserStatus(2);
			paybillstatusDTO.setCount(getCountToGeneratePayBill());
			paybillstatusDTO.setGeneratedCount(getCountForAlreadyGeneratedPayBill(runMonth));
			paybillstatusDTO.setDescription(CPSConstants.SUCCESS);
			session.saveOrUpdate(paybillstatusDTO);
			String updateQuery = "update pay_txn_details set status=? where status=? ";
			session.createSQLQuery(updateQuery).setString(0, CPSConstants.PAYUSERVISIBLESTATUS).setString(1, CPSConstants.PAYMANUALRUNSTATUS).executeUpdate();
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return paybillstatusDTO;
	}

	@SuppressWarnings("unchecked")
	public PayBillStatusDTO startRun(String userId, String runMonth,PayBillBean payBillBean) throws Exception {
		Session session = null;
		List<PayBillUserDTO> usersList = null;
		Transaction tx = null;
		String message = "";
		PayBillStatusDTO payBillStatusDTO = new PayBillStatusDTO();
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(payBillBean.getType().equals("Supplementary")){
				payBillStatusDTO = getSuppleeqmentaryRunMonthDetails(payBillBean);
			}else{
				payBillStatusDTO = getRunMonthDetails();
			}
			
			int runid = payBillStatusDTO.getRunID();
            int runSubId=payBillStatusDTO.getRunSubId();
            
            if(payBillBean.getType().equals("Supplementary")){
            	String[] desigList=payBillBean.getSearchSfid().split(",");
            	List sfidList =new ArrayList();
            	for(int i=0;i<desigList.length;i++){
            		sfidList.add(desigList[i]);
            	}
            	String sql = "select emp.sfid as sfID,epd.basic_id as basicId,epd.grade_id as gradeId,Epd.Category_Id as categoryId,epd.pay_designation_id as payDesignationId,family_planning as fpaFlag," + "handicap_id as phType, "
    			+ "grade_pay as gPay,basic_pay as basicPay,epd.ccs_flag as ccsMemFlag,epd.ccs_sub as ccsSubAmt," + "epd.cgeis_group_id as cgeisGroupID,"
    			+ "epd.cgeis_mem_status as cgeisMemFlag,epd.fpa_gradepay as fpaGradePay,epd.gpf_flag as gpfFlag,epd.gpf_subs_amt as gpfSubAmt," + "epd.income_tax as incomeTaxAmt,"
    			+ "epd.tra_eligible_flag as tptFlag,epd.cghs_flag as cghsFlag," + "epd.two_addl_incr as twoAddIncr,epd.var_incr_points  as varIncrPts,epd.PAY_STOP as payStopFlag ,"
    			+ " epd.ccs_no as ccsNo" + " from emp_master emp,emp_pay_master epd " + "where epd.sfid = emp.sfid and epd.status=1 and epd.sfid in (:sfids)";//0 is changed to 1 by Narayana

             usersList = session.createSQLQuery(sql).addScalar("sfID", Hibernate.STRING).addScalar("basicId", Hibernate.STRING).addScalar("gradeId", Hibernate.STRING).addScalar("categoryId", Hibernate.STRING).addScalar("payDesignationId", Hibernate.STRING).addScalar("fpaFlag", Hibernate.STRING).addScalar("phType",
    					Hibernate.INTEGER).addScalar("gPay", Hibernate.INTEGER).addScalar("basicPay", Hibernate.INTEGER).addScalar("ccsMemFlag", Hibernate.STRING).addScalar("ccsSubAmt",
    					Hibernate.INTEGER).addScalar("cgeisGroupID", Hibernate.INTEGER).addScalar("fpaGradePay", Hibernate.INTEGER).addScalar(
    					"gpfFlag", Hibernate.STRING).addScalar("gpfSubAmt", Hibernate.INTEGER).addScalar("payStopFlag", Hibernate.STRING).addScalar("incomeTaxAmt", Hibernate.INTEGER).addScalar("ccsNo", Hibernate.STRING).addScalar("tptFlag", Hibernate.STRING).addScalar("cghsFlag", Hibernate.STRING).addScalar("twoAddIncr",
    					Hibernate.INTEGER).addScalar("varIncrPts", Hibernate.INTEGER).setParameterList("sfids", sfidList).setResultTransformer(Transformers.aliasToBean(PayBillUserDTO.class)).list();
            }else{
           
            	String sql = "select emp.sfid as sfID,epd.basic_id as basicId,epd.grade_id as gradeId,Epd.Category_Id as categoryId,epd.pay_designation_id as payDesignationId,family_planning as fpaFlag," + "handicap_id as phType, "
			+ "grade_pay as gPay,basic_pay as basicPay,epd.ccs_flag as ccsMemFlag,epd.ccs_sub as ccsSubAmt," + "epd.cgeis_group_id as cgeisGroupID,"
			+ "epd.cgeis_mem_status as cgeisMemFlag,epd.fpa_gradepay as fpaGradePay,epd.gpf_flag as gpfFlag,epd.gpf_subs_amt as gpfSubAmt," + "epd.income_tax as incomeTaxAmt,"
			+ "epd.tra_eligible_flag as tptFlag,epd.cghs_flag as cghsFlag," + "epd.two_addl_incr as twoAddIncr,epd.var_incr_points  as varIncrPts,epd.PAY_STOP as payStopFlag ,"
			+ " epd.ccs_no as ccsNo" + " from emp_master emp,emp_pay_master epd " + "where epd.sfid = emp.sfid and epd.status=1 and epd.sfid not in (select sfid from pay_txn_details where status in (1,60))";//0 is changed to 1 by Narayana

         usersList = session.createSQLQuery(sql).addScalar("sfID", Hibernate.STRING).addScalar("basicId", Hibernate.STRING).addScalar("gradeId", Hibernate.STRING).addScalar("categoryId", Hibernate.STRING).addScalar("payDesignationId", Hibernate.STRING).addScalar("fpaFlag", Hibernate.STRING).addScalar("phType",
					Hibernate.INTEGER).addScalar("gPay", Hibernate.INTEGER).addScalar("basicPay", Hibernate.INTEGER).addScalar("ccsMemFlag", Hibernate.STRING).addScalar("ccsSubAmt",
					Hibernate.INTEGER).addScalar("cgeisGroupID", Hibernate.INTEGER).addScalar("fpaGradePay", Hibernate.INTEGER).addScalar(
					"gpfFlag", Hibernate.STRING).addScalar("gpfSubAmt", Hibernate.INTEGER).addScalar("payStopFlag", Hibernate.STRING).addScalar("incomeTaxAmt", Hibernate.INTEGER).addScalar("ccsNo", Hibernate.STRING).addScalar("tptFlag", Hibernate.STRING).addScalar("cghsFlag", Hibernate.STRING).addScalar("twoAddIncr",
					Hibernate.INTEGER).addScalar("varIncrPts", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayBillUserDTO.class)).list();
            }
			for (int i = 0; i < usersList.size(); i++) {

				PayBillUserDTO pbuser = usersList.get(i);
				log.debug("<-------------PAY START FOR SFID :: " + pbuser.getSfID() + " -------------->");
				PayBillDTO payDTO = new PayBillDTO();
				PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO=(PayBillEmpPaymentDeatilsDTO)session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", pbuser.getSfID())).add(Expression.eq("status", 1)).uniqueResult();
				PayQuarterManagementDTO payQuarterManagementDTO=calculatePayBill.getEmployeeQuarterDetails(pbuser.getSfID(), CPSUtils.convertStringToDate("01-"+runMonth));
				PayTxnMastersDetailsDTO payTxnMastersDetailsDTO = new PayTxnMastersDetailsDTO();
				String idWithValue = "";
				payDTO.setSfid(pbuser.getSfID());
				payTxnMastersDetailsDTO.setSfid(pbuser.getSfID());
				payTxnMastersDetailsDTO.setPayDesignationId(Integer.parseInt(pbuser.getPayDesignationId()));
				payTxnMastersDetailsDTO.setCategoryId(Integer.parseInt(pbuser.getCategoryId()));
				payTxnMastersDetailsDTO.setBasicId(Integer.parseInt(pbuser.getBasicId()));
				payTxnMastersDetailsDTO.setGradeId(Integer.parseInt(pbuser.getGradeId()));
				PayScaleDesignationDTO payScaleDesignationDTO = (PayScaleDesignationDTO) session.createCriteria(PayScaleDesignationDTO.class).add(
						Expression.eq("designation", pbuser.getPayDesignationId())).add(Expression.eq("status", 1)).uniqueResult();
				payTxnMastersDetailsDTO.setPayBandId(Integer.parseInt(payScaleDesignationDTO.getPayband()));
				// Credits
				/* Basic Pay Manipulations  */
				
				payDTO.setBasicPay(pbuser.getBasicPay());
				
				/*List<EmpBasicPayHistoryDTO> empBasicPayHistoryList=session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,pbuser.getSfID())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("presentEffectiveDate", session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,pbuser.getSfID())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.not(Expression.in("incrementType", new String[]{"P"}))).setProjection(Projections.max("presentEffectiveDate")).uniqueResult())).add(Expression.ne("incrementType", "P")).addOrder(Order.asc("incrementType")).list();
				 for(int k=0;k<empBasicPayHistoryList.size();k++){
					 EmpBasicPayHistoryDTO empBasicPayHistoryDTO=empBasicPayHistoryList.get(k);
					 payBillEmpPaymentDeatilsDTO.setBasicRefId(empBasicPayHistoryDTO.getId()); 
					 payDTO.setBasicPay((int)empBasicPayHistoryDTO.getBasicPay());
				 }*/
				 
				 String bascPayFromAnnualIncrWithRemarks = payBillPreProcess.basicPayPreProcessWithRespToAnnualIncrement(pbuser.getSfID(), runMonth);
					if (Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]) > 0) {
						payDTO.setBasicPay(Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]));
						payBillEmpPaymentDeatilsDTO.setBasicRefId(Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[3]));
						payDTO.setCrRemarks(bascPayFromAnnualIncrWithRemarks.split("@")[1]);
					}
					
					/* Grade Pay Manipulations  */	
			
				payDTO.setGradePay(pbuser.getgPay());
				payDTO.setTwoAddlIncr(pbuser.getTwoAddIncr());
				
			   PromoteesEntryDTO promoteesEntryDTO=calculatePayBill.getPromotedTopMostRecord("01-"+runMonth, payDTO.getSfid());
				if(!CPSUtils.isNullOrEmpty(promoteesEntryDTO)){
					payDTO.setGradePay((int)promoteesEntryDTO.getNewGradePay());
					payDTO.setTwoAddlIncr(Integer.parseInt(promoteesEntryDTO.getTwoAddl()));
					int check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth),promoteesEntryDTO.getPresentEffectiveDate());
				    if(check>=0){
				    	check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth), promoteesEntryDTO.getVarIncEndDate());
					    if(check<0){
					    	pbuser.setVarIncrPts(promoteesEntryDTO.getVarIncPt());
					    }else{
					    	pbuser.setVarIncrPts(0);
					    }
				    }else{
				    	pbuser.setVarIncrPts(0);
				    }
				    payBillEmpPaymentDeatilsDTO.setGradeRefId(promoteesEntryDTO.getId());
				}else{
					payBillEmpPaymentDeatilsDTO.setGradeRefId(payBillEmpPaymentDeatilsDTO.getGradeId());
				}
			    
				PayProPreProcessDTO payProPreProcessDTO=payBillPreProcess.integratingWithPromotion(pbuser.getSfID(), "01-"+runMonth, payBillEmpPaymentDeatilsDTO.getGradeRefId());

				if(!CPSUtils.isNullOrEmpty(payProPreProcessDTO)){
					payDTO.setBasicPay(payProPreProcessDTO.getNewBasic());
					payBillEmpPaymentDeatilsDTO.setBasicRefId(payProPreProcessDTO.getBasicId());
					payDTO.setGradePay(payProPreProcessDTO.getNewGrade());
					payBillEmpPaymentDeatilsDTO.setGradeRefId(payProPreProcessDTO.getGradeId());
					payDTO.setTwoAddlIncr(payProPreProcessDTO.getNewTwoAdd());
					pbuser.setVarIncrPts(payProPreProcessDTO.getVarIncrPoints());
					payTxnMastersDetailsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
					payBillEmpPaymentDeatilsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
					payTxnMastersDetailsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
               }
				
				idWithValue = calculatePayBill.getVarIncr(payDTO.getGradePay(), pbuser.getVarIncrPts());
				payDTO.setVariableIncr(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setVarIncrId(Integer.parseInt(idWithValue.split("@")[0]));

				
				idWithValue = calculatePayBill.getDA(payDTO.getBasicPay(), payDTO.getGradePay(), "01-" + runMonth);
				payDTO.setDa(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setDaMasterId(Integer.parseInt(idWithValue.split("@")[0]));

				idWithValue = calculatePayBill.getSplPay("" + pbuser.getPayDesignationId());
				payDTO.setSpecialPay(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setSpecialPayId(Integer.parseInt(idWithValue.split("@")[0]));

				if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getFpaFlag())) {

					idWithValue = calculatePayBill.getFpa(pbuser.getFpaGradePay());
					payDTO.setFpa(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setFpaMastersId(Integer.parseInt(idWithValue.split("@")[0]));
				} else {
					payTxnMastersDetailsDTO.setFpaMastersId(0);
					payDTO.setFpa(0);
				}

			
				if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getTptFlag())) {
					idWithValue = calculatePayBill.getTra(payDTO.getBasicPay(), pbuser.getSfID(), payDTO.getGradePay(), "01-" + runMonth);
					payDTO.setTpt(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setTraMasterId(Integer.parseInt(idWithValue.split("@")[0]));
				} else {
					payDTO.setTpt(0);
					payTxnMastersDetailsDTO.setTraMasterId(0);
				}
				payDTO.setHra(calculatePayBill.getHra(payDTO.getBasicPay(), payDTO.getGradePay(),payDTO.getSfid(),new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth)));
				
               idWithValue = calculatePayBill.getWashAllowance(pbuser.getSfID());
				payDTO.setWashAllowance(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setWashAllwId(Integer.parseInt(idWithValue.split("@")[0]));
				
				payDTO.setXeroxAllowance(0);
			
				payDTO.setRiskAllowance(0);
				payDTO.setDeputAllowance(0);
				payDTO.setCrMisc(0);

				Object[] hindiInc = calculatePayBill.getHindiIncentive(pbuser.getSfID(),payDTO.getBasicPay(),payDTO.getGradePay(), runMonth);
				payDTO.setHindiIncentive(((BigDecimal) hindiInc[0]).intValue());
				if (!CPSUtils.isNullOrEmpty(hindiInc[1]))
					session.saveOrUpdate(hindiInc[1]);

				int totalCredits = payDTO.getBasicPay() + payDTO.getGradePay() + payDTO.getDa() + payDTO.getSpecialPay() + payDTO.getFpa() + payDTO.getTwoAddlIncr() + payDTO.getTpt()
						+ payDTO.getHra() + payDTO.getWashAllowance() + payDTO.getXeroxAllowance() + payDTO.getVariableIncr() + payDTO.getHindiIncentive() + payDTO.getRiskAllowance()
						+ payDTO.getDeputAllowance() + payDTO.getCrMisc();
				payDTO.setTotalCredits(totalCredits);

				// Deduction
				if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getGpfFlag())) {
					if (Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]) > 0) {
						String flag = iPayBillMasterDAO.checkGPFmember(pbuser.getSfID());
						if (!CPSUtils.compareStrings(flag, CPSConstants.YES)) {
							payDTO.setGpf((int) Math.round((payDTO.getBasicPay() + payDTO.getGradePay() + payDTO.getDa()) * 10 / 100));
							PayBillEmpPaymentDeatilsDTO empPayDTO = (PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(
									Expression.eq("sfID", pbuser.getSfID())).add(Expression.eq("status", 1)).uniqueResult();
							empPayDTO.setGpfSubAmt(payDTO.getGpf());
							session.saveOrUpdate(empPayDTO);
						} else {
							payDTO.setGpf(pbuser.getGpfSubAmt());
						}
					} else {
						payDTO.setGpf(pbuser.getGpfSubAmt());
					}
				} else
					payDTO.setGpf(0);

				payDTO.setPli(0);

				if (CPSUtils.compareStrings(pbuser.getCghsFlag(), CPSConstants.YES)) {
					idWithValue = calculatePayBill.getCGHS(payDTO.getGradePay());
					payDTO.setCghs(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setCghsMastersId(Integer.parseInt(idWithValue.split("@")[0]));
				} else {
					payDTO.setCghs(0);
					payTxnMastersDetailsDTO.setCghsMastersId(0);
				}

				idWithValue = calculatePayBill.getCEGIS(pbuser.getSfID(), pbuser.getCgeisGroupID());
				payDTO.setCegis(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setCgeisMastersId(Integer.parseInt(idWithValue.split("@")[0]));

				if (!CPSUtils.isNullOrEmpty(payQuarterManagementDTO)) {

					idWithValue = calculatePayBill.getLicenceFee(pbuser.getSfID());
					payDTO.setRent(Integer.parseInt(idWithValue.split("@")[1]));
					payDTO.setElec(Integer.parseInt(idWithValue.split("@")[4]));
					payDTO.setWater(Integer.parseInt(idWithValue.split("@")[2]));
					payDTO.setFurn(Integer.parseInt(idWithValue.split("@")[3]));
					payTxnMastersDetailsDTO.setLicFeeMastersId(Integer.parseInt(idWithValue.split("@")[0]));

					idWithValue = calculatePayBill.getResSecurity(pbuser.getSfID());
					payDTO.setResSecu(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setResSecuMastersId(Integer.parseInt(idWithValue.split("@")[0]));

				} else {
					payDTO.setRent(0);
					payDTO.setResSecu(0);
					payDTO.setElec(0);
					payDTO.setWater(0);
					payDTO.setFurn(0);
				}
				Object[] loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORCYCLELOANID, runMonth,runid, userId);

				payDTO.setCycleLoan((Integer) loan[0]);
				payDTO.setCycleTotInst((Integer) loan[1]);
				payDTO.setCycleInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setCycleLoanId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);
				
				loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.FESTIVALLOANID, runMonth,runid, userId);
				payDTO.setFestivalAdv((Integer) loan[0]);
				payDTO.setFestivTotInst((Integer) loan[1]);
				payDTO.setFestivInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setFestLoanId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);
				
				loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.GPFADVANCELOANID, runMonth,runid, userId);
				payDTO.setGpfRecovery((Integer) loan[0]);
				payDTO.setGpfTotAmt((Integer) loan[1]);
				payDTO.setGpfPreInst((Integer) loan[2]);
				payTxnMastersDetailsDTO.setGpfAdvanceLoanId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);

				loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORCARLOANID, runMonth,runid, userId);
				payDTO.setCarLoan((Integer) loan[0]);
				payDTO.setCarTotInst((Integer) loan[1]);
				payDTO.setCarInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setCarLoanId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);

				loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.PCLOANID, runMonth,runid, userId);
				payDTO.setPcLoan((Integer) loan[0]);
				payDTO.setPcTotInst((Integer) loan[1]);
				payDTO.setPcInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setPcLoanId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);


				loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORSCOOTERLOANID, runMonth,runid, userId);
				payDTO.setScooterLoan((Integer) loan[0]);
				payDTO.setScooterTotInst((Integer) loan[1]);
				payDTO.setScooterInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setScooterLoanId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);

				loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.HBALOANID, runMonth,runid, userId);
				payDTO.setHbaLoan((Integer) loan[0]);
				payDTO.setHbaTotInst((Integer) loan[1]);
				payDTO.setHbaInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setHbaLoanId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);
				
				loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.WELFARELOANID, runMonth,runid, userId);
				
				payDTO.setWelRefund((Integer) loan[1]);
				payDTO.setWelTotinst((Integer) loan[1]);
				payDTO.setWelInst((Integer) loan[1]);
				payTxnMastersDetailsDTO.setWelFundId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				if (!CPSUtils.isNullOrEmpty(loan[5]))
					session.saveOrUpdate(loan[5]);

				payDTO.setIncomeTax(calculatePayBill.getITax(pbuser.getIncomeTaxAmt()));
				payDTO.setCess(calculatePayBill.getCess(pbuser.getIncomeTaxAmt()));
				payDTO.setSecondaryCess(calculatePayBill.getSHEC(pbuser.getIncomeTaxAmt()));
				if (pbuser.getIncomeTaxAmt() < payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess())
					payDTO.setIncomeTax(payDTO.getIncomeTax() - 1);
				else if (pbuser.getIncomeTaxAmt() > payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess())
					payDTO.setIncomeTax(payDTO.getIncomeTax() + 1);

				idWithValue = calculatePayBill.getProfTax(pbuser.getSfID(), totalCredits);
				payDTO.setProfTax(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setProfTaxId(Integer.parseInt(idWithValue.split("@")[0]));
				payDTO.setGpfSa(0);
				payDTO.setTada(0);
				payDTO.setMedical(0);
				payDTO.setLtc(0);
				payDTO.setEol(0);
				payDTO.setDrMisc1(0);
				payDTO.setDrMisc2(0);

				int totalDebits = payDTO.getGpf() + payDTO.getGpfSa() + payDTO.getGpfRecovery() + payDTO.getPli() + payDTO.getCghs() + payDTO.getCegis() + payDTO.getRent() + payDTO.getElec()
						+ payDTO.getWater() + payDTO.getFurn() + payDTO.getCycleLoan() + payDTO.getFestivalAdv() + payDTO.getCarLoan() + payDTO.getPcLoan() + payDTO.getScooterLoan()
						+ payDTO.getHbaLoan() + payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess() + payDTO.getProfTax() + payDTO.getTada() + payDTO.getMedical()
						+ payDTO.getLtc() + payDTO.getEol() + payDTO.getDrMisc1() + payDTO.getDrMisc2();
				payDTO.setTotalDebits(totalDebits);
				payDTO.setNetPay(totalCredits - totalDebits);

				// Coin Cut or Recoveries

				idWithValue = calculatePayBill.getWelFund(pbuser.getPayDesignationId());
				payDTO.setWelC(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setWelFundId(Integer.parseInt(idWithValue.split("@")[0]));
				
				idWithValue = calculatePayBill.getMess(pbuser.getPayDesignationId(),payQuarterManagementDTO==null?"No":"Yes");
				payDTO.setMess(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setMessId(Integer.parseInt(idWithValue.split("@")[0]));
				

				idWithValue = calculatePayBill.getBenFund(pbuser.getPayDesignationId());
				payDTO.setBenvolentFund(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setBenFundId(Integer.parseInt(idWithValue.split("@")[0]));

				idWithValue = calculatePayBill.getRegFund(pbuser.getPayDesignationId());
				payDTO.setRegimentalFund(Integer.parseInt(idWithValue.split("@")[1]));
				payTxnMastersDetailsDTO.setRegFundId(Integer.parseInt(idWithValue.split("@")[0]));

				payDTO.setCcs(pbuser.getCcsSubAmt());
				payDTO.setCcsrecovery(calculatePayBill.getCcsRecovery(pbuser.getSfID(), pbuser.getCcsSubAmt()));

				loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.CANFIN, runMonth,runid,userId);

				payDTO.setCanfin(((Float) loan[0]).intValue());
				payDTO.setCanfinTotInst((Integer) loan[1]);
				payDTO.setCanfinInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setCanfinDeduId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				
				loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.HDFC, runMonth,runid,userId);
				payDTO.setHdfc(((Float) loan[0]).intValue());
				payDTO.setHdfcTotInst((Integer) loan[1]);
				payDTO.setHdfcInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setHdfcDeduId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				
				loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.GIC, runMonth,runid,userId);
				payDTO.setGic(((Float) loan[0]).intValue());
				payDTO.setGicTotInst((Integer) loan[1]);
				payDTO.setGicInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setGicDeduId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				
				loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.LIC, runMonth,runid,userId);
				payDTO.setLic(((Float) loan[0]).intValue());
				payDTO.setLicTotInst((Integer) loan[1]);
				payDTO.setLicInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setLicDeduId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				
				loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.COURTATTACHMENT, runMonth,runid,userId);
				payDTO.setCourtAttachment(((Float) loan[0]).intValue());
				payDTO.setCourtTotInst((Integer) loan[1]);
				payDTO.setCourtInstNumb((Integer) loan[2]);
				payTxnMastersDetailsDTO.setCourtAttchDeduId((Integer) loan[3]);
				if (!CPSUtils.isNullOrEmpty(loan[4]))
					session.saveOrUpdate(loan[4]);
				
				payDTO.setWelRefund(0);
				payDTO.setRecMisc1(0);
				payDTO.setRecMisc2(0);
				payDTO.setRecMisc3(0);
				payDTO.setCcsrecovery(0);
				int totalRecoveries = payDTO.getWelC() + payDTO.getMess() + payDTO.getBenvolentFund() + payDTO.getRegimentalFund() + payDTO.getCcs() + payDTO.getCcsrecovery() + payDTO.getCanfin()
						+ payDTO.getHdfc() + payDTO.getGic() + payDTO.getLic() + payDTO.getCourtAttachment() + payDTO.getWelRefund() + payDTO.getResSecu() + payDTO.getRecMisc1()
						+ payDTO.getRecMisc2() + payDTO.getRecMisc3();

				payDTO.setTotalRecovery(totalRecoveries);
				payDTO.setFinalPay(totalCredits - (totalDebits + totalRecoveries));

				payDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				payTxnMastersDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				payDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				payTxnMastersDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				payDTO.setCreatedBy(userId);
				payTxnMastersDetailsDTO.setCreatedBy(userId);
				payDTO.setLastModifiedBy(userId);
				payTxnMastersDetailsDTO.setLastModifiedBy(userId);
				payDTO.setRunId(runid);
				payDTO.setRunSubId(runSubId);
				payTxnMastersDetailsDTO.setRunId(runid);
				payTxnMastersDetailsDTO.setRunSubId(runSubId);
				payDTO.setRunMonth("1-" + runMonth);
				payTxnMastersDetailsDTO.setRunMonth("1-" + runMonth);
				payDTO.setStatus(Integer.parseInt(CPSConstants.PAYAUTORUNSTATUS));
				payTxnMastersDetailsDTO.setStatus(Integer.parseInt(CPSConstants.PAYAUTORUNSTATUS));
				payTxnMastersDetailsDTO.setEolHplId(0);

				PayBillDTO payDTO1 = new PayBillDTO();

				if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getPayStopFlag())) {
					payDTO1.setSfid(payDTO.getSfid());
					payDTO1.setRunId(payDTO.getRunId());
					payDTO1.setRunSubId(runSubId);
					payDTO1.setRunMonth(payDTO.getRunMonth());
					payDTO1.setCreationTime(payDTO.getCreationTime());
					payDTO1.setCreatedBy(payDTO.getCreatedBy());
					payDTO1.setLastModifiedBy(payDTO.getLastModifiedBy());
					payDTO1.setLastModifiedTime(payDTO.getLastModifiedTime());
					payDTO1.setStatus(1);
				} else {
					BeanUtils.copyProperties(payDTO1, payDTO);
					payDTO1.setStatus(1); //0 is changed to 1 by Narayana
				}
				session.saveOrUpdate(payTxnMastersDetailsDTO);
				session.saveOrUpdate(payDTO);
				session.saveOrUpdate(payDTO1);
				session.saveOrUpdate(payBillEmpPaymentDeatilsDTO);
				
			}

			message = CPSConstants.SUCCESS;

			if (usersList.size() == 0)
				message = "No User(s) found.";
			else
				message = CPSConstants.SUCCESS;
			

			session.flush();//tx.commit() ;
			
			payBillStatusDTO.setRunStatus(0);
			payBillStatusDTO.setManualStatus(1);
			payBillStatusDTO.setUserStatus(0);
			payBillStatusDTO.setDescription(message);
			payBillStatusDTO.setCount(getCountToGeneratePayBill());
			payBillStatusDTO.setGeneratedCount(getCountForAlreadyGeneratedPayBill(runMonth));

		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return payBillStatusDTO;
	}

	@SuppressWarnings("unchecked")
	public List<PayBillDTO> getNegativePayEmployees() throws Exception {
		List<PayBillDTO> listuser = null;
		Session session = null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			/*
			 * listuser = (List<PayBillDTO>) session.createCriteria(PayBillDTO.class).add(Expression.le("finalPay", 0)).list();
			 */
			String sql = "select sfid as sfid,take_home as finalPay,gross_pay as totalCredits,net_pay as netPay,tot_debits as totalDebits,tot_recs as totalRecovery from pay_txn_details where take_home<0 and to_char(run_month,'Mon-yyyy')=(select to_char(max(run_month),'Mon-yyyy') from paybill_status_details where status=1) and (status!=50)";//0 is changed to 1 by Narayana
			listuser = session.createSQLQuery(sql).addScalar("sfid").addScalar("finalPay", Hibernate.INTEGER).addScalar("totalCredits", Hibernate.INTEGER).addScalar("netPay", Hibernate.INTEGER)
					.addScalar("totalDebits", Hibernate.INTEGER).addScalar("totalRecovery", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayBillDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return listuser;
	}

	public PayBillDTO showPaySlip(String sfid, String runmonth) throws Exception {

		PayBillDTO paybillDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String month = "01-" + runmonth;
			paybillDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("status", Integer.parseInt(CPSConstants.PAYUSERVISIBLESTATUS))).add(Expression.eq("runMonth", month)).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return paybillDTO;
	}

	public String validate(PayBillDTO paydto, String name, String message) throws Exception {
		String message1 = "";

		try {
			int recAmount = getEmpDue(paydto.getSfid(), name);// recAmount= credits-debits for name
			PayBillDTO paybillDTO = getEmpPaymentDetails(paydto.getSfid());// record from pay_txn_details where status=50

			// CGHS
			if (CPSUtils.compareStrings(name, "CGHS")) {
				// int actualCGHSAmount= getCGHSAmount(paydto.getSfid());
				if (paydto.getCghs() > paybillDTO.getCghs()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getCghs() - paybillDTO.getCghs()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + ".  Please dont exceed the amount Rs." + (recAmount + paybillDTO.getCghs()) + "\n";
				} else if (paydto.getCghs() < paybillDTO.getCghs()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}

			else if (CPSUtils.compareStrings(name, "GPF")) {
				if (paydto.getGpf() > paybillDTO.getGpf()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getGpf() - paybillDTO.getGpf()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + ".  Please dont exceed the amount Rs." + (recAmount + paybillDTO.getCghs()) + "\n";
				} else if (paydto.getGpf() < paybillDTO.getGpf()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}

			// CEGIS
			else if (CPSUtils.compareStrings(name, "CEGIS")) {
				if (paydto.getCegis() > paybillDTO.getCegis()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getCegis() - paybillDTO.getCegis()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getCegis()) + "\n";
				} else if (paydto.getCegis() < paybillDTO.getCegis()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}

			// FESTIVAL ADVANCE

			else if (CPSUtils.compareStrings(name, "Festival Advance")) {
				if (paydto.getFestivalAdv() > paybillDTO.getFestivalAdv()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = "Eligible Fest Advance is Rs." + paybillDTO.getFestivalAdv() + "\n";
					else if (recAmount < (paydto.getFestivalAdv() - paybillDTO.getFestivalAdv()))
						message1 += "Eligible Fest Advance is Rs." + paybillDTO.getFestivalAdv() + "\n" + " and " + "\n Eligible " + name + " DUE amount is " + recAmount
								+ " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getFestivalAdv()) + "\n";
				} else if (paydto.getFestivalAdv() < paybillDTO.getFestivalAdv()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = "Eligible Fest Advance is Rs." + paybillDTO.getFestivalAdv() + "\n";
					}

				}
			}

			// Car Loan
			else if (CPSUtils.compareStrings(name, "Car Loan")) {
				if (paydto.getCarLoan() > paybillDTO.getCarLoan()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = "Eligible Car Loan is Rs." + paybillDTO.getCarLoan() + "\n";
					else if (recAmount < (paydto.getCarLoan() - paybillDTO.getCarLoan()))
						message1 += "Eligible Car Loan is Rs." + paybillDTO.getCarLoan() + "\n" + " and " + "\n Eligible " + name + " DUE amount is " + recAmount
								+ " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getCarLoan()) + "\n";
				} else if (paydto.getCarLoan() < paybillDTO.getCarLoan()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = "Eligible Car Loan is Rs." + paybillDTO.getCarLoan() + "\n";
					}

				}
			}

			// Cycle Loan

			else if (CPSUtils.compareStrings(name, "Cycle Loan")) {
				if (paydto.getCycleLoan() > paybillDTO.getCycleLoan()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = "Eligible Cycle Loan is Rs." + paybillDTO.getCycleLoan() + "\n";
					else if (recAmount < (paydto.getCycleLoan() - paybillDTO.getCycleLoan()))
						message1 += "Eligible Cycle Loan is Rs." + paybillDTO.getCycleLoan() + "\n" + " and " + "\n Eligible " + name + " DUE amount is " + recAmount
								+ " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getCycleLoan()) + "\n";
				} else if (paydto.getCycleLoan() < paybillDTO.getCycleLoan()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = "Eligible Cycle Loan is Rs." + paybillDTO.getCycleLoan() + "\n";
					}

				}
			}

			// GPF Recovery(GPF Advance Loan)

			else if (CPSUtils.compareStrings(name, "Gpf Advance")) {
				if (paydto.getGpfRecovery() > paybillDTO.getGpfRecovery()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = "Eligible Gpf Advance is Rs." + paybillDTO.getGpfRecovery() + "\n";
					else if (recAmount < (paydto.getGpfRecovery() - paybillDTO.getGpfRecovery()))
						message1 += "Eligible Gpf Advance is Rs." + paybillDTO.getGpfRecovery() + "\n" + " and " + "\n Eligible " + name + " DUE amount is " + recAmount
								+ " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getGpfRecovery()) + "\n";
				} else if (paydto.getGpfRecovery() < paybillDTO.getGpfRecovery()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = "Eligible Gpf Advance is Rs." + paybillDTO.getGpfRecovery() + "\n";
					}

				}
			}
			// PC Loan

			else if (CPSUtils.compareStrings(name, "PC Loan")) {
				if (paydto.getPcLoan() > paybillDTO.getPcLoan()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = "Eligible PC Loan is Rs." + paybillDTO.getPcLoan() + "\n";
					else if (recAmount < (paydto.getPcLoan() - paybillDTO.getPcLoan()))
						message1 += "Eligible PC Loan is Rs." + paybillDTO.getPcLoan() + "\n" + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs."
								+ (recAmount + paybillDTO.getPcLoan()) + "\n";
				} else if (paydto.getPcLoan() < paybillDTO.getPcLoan()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = "Eligible PC Loan is Rs." + paybillDTO.getPcLoan() + "\n";
					}

				}
			}
			// Scooter Loan

			else if (CPSUtils.compareStrings(name, "Scooter Loan")) {
				if (paydto.getScooterLoan() > paybillDTO.getScooterLoan()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = "Eligible Scooter Loan is Rs." + paybillDTO.getScooterLoan() + "\n";
					else if (recAmount < (paydto.getScooterLoan() - paybillDTO.getScooterLoan()))
						message1 += "Eligible Scooter Loan is Rs." + paybillDTO.getScooterLoan() + "\n" + " and " + "\n Eligible " + name + " DUE amount is " + recAmount
								+ " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getScooterLoan()) + "\n";
				} else if (paydto.getScooterLoan() < paybillDTO.getScooterLoan()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = "Eligible Scooter Loan is Rs." + paybillDTO.getScooterLoan() + "\n";
					}

				}
			}
			// HBA

			else if (CPSUtils.compareStrings(name, "HBA")) {
				if (paydto.getHbaLoan() > paybillDTO.getHbaLoan()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = "Eligible HBA is Rs." + paybillDTO.getHbaLoan() + "\n";
					else if (recAmount < (paydto.getHbaLoan() - paybillDTO.getHbaLoan()))
						message1 += "Eligible HBA is Rs." + paybillDTO.getHbaLoan() + "\n" + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs."
								+ (recAmount + paybillDTO.getHbaLoan()) + "\n";
				} else if (paydto.getHbaLoan() < paybillDTO.getHbaLoan()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = "Eligible HBA is Rs." + paybillDTO.getHbaLoan() + "\n";
					}

				}
			}
			// Rent

			else if (CPSUtils.compareStrings(name, "Rent")) {
				if (paydto.getRent() > paybillDTO.getRent()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getRent() - paybillDTO.getRent()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getRent()) + "\n";
				} else if (paydto.getRent() < paybillDTO.getRent()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}

			// Water Bill

			else if (CPSUtils.compareStrings(name, "Water Bill")) {
				if (paydto.getWater() > paybillDTO.getWater()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getWater() - paybillDTO.getWater()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getWater()) + "\n";
				} else if (paydto.getWater() < paybillDTO.getWater()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Elec Bill

			else if (CPSUtils.compareStrings(name, "Elec Bill")) {
				if (paydto.getElec() > paybillDTO.getElec()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getElec() - paybillDTO.getElec()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getElec()) + "\n";
				} else if (paydto.getElec() < paybillDTO.getElec()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Furn Bill

			else if (CPSUtils.compareStrings(name, "Furn Bill")) {
				if (paydto.getFurn() > paybillDTO.getFurn()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getFurn() - paybillDTO.getFurn()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getFurn()) + "\n";
				} else if (paydto.getFurn() < paybillDTO.getFurn()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}

			// BenFund

			else if (CPSUtils.compareStrings(name, "BenFund")) {
				if (paydto.getBenvolentFund() > paybillDTO.getBenvolentFund()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getBenvolentFund() - paybillDTO.getBenvolentFund()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getBenvolentFund())
								+ "\n";
				} else if (paydto.getBenvolentFund() < paybillDTO.getBenvolentFund()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Res Secu

			else if (CPSUtils.compareStrings(name, "Res Secu")) {
				if (paydto.getResSecu() > paybillDTO.getResSecu()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getResSecu() - paybillDTO.getResSecu()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getResSecu()) + "\n";
				} else if (paydto.getResSecu() < paybillDTO.getResSecu()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Mess

			else if (CPSUtils.compareStrings(name, "Mess")) {
				if (paydto.getMess() > paybillDTO.getMess()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getMess() - paybillDTO.getMess()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getMess()) + "\n";
				} else if (paydto.getMess() < paybillDTO.getMess()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// WelFund

			else if (CPSUtils.compareStrings(name, "WelFund")) {
				if (paydto.getWelRefund() > paybillDTO.getWelRefund()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getWelRefund() - paybillDTO.getWelRefund()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getWelRefund()) + "\n";
				} else if (paydto.getWelRefund() < paybillDTO.getWelRefund()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// RegFund

			else if (CPSUtils.compareStrings(name, "RegFund")) {
				if (paydto.getRegimentalFund() > paybillDTO.getRegimentalFund()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getRegimentalFund() - paybillDTO.getRegimentalFund()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getRegimentalFund())
								+ "\n";
				} else if (paydto.getRegimentalFund() < paybillDTO.getRegimentalFund()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Canfin

			else if (CPSUtils.compareStrings(name, "Canfin")) {
				if (paydto.getCanfin() > paybillDTO.getCanfin()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getCanfin() - paybillDTO.getCanfin()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getCanfin()) + "\n";
				} else if (paydto.getCanfin() < paybillDTO.getCanfin()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Hdfc

			else if (CPSUtils.compareStrings(name, "Hdfc")) {
				if (paydto.getHdfc() > paybillDTO.getHdfc()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getHdfc() - paybillDTO.getHdfc()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getHdfc()) + "\n";
				} else if (paydto.getHdfc() < paybillDTO.getHdfc()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Lic

			else if (CPSUtils.compareStrings(name, "Lic")) {
				if (paydto.getLic() > paybillDTO.getLic()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getLic() - paybillDTO.getLic()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getLic()) + "\n";
				} else if (paydto.getLic() < paybillDTO.getLic()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Court Attachment

			else if (CPSUtils.compareStrings(name, "Court Attachment")) {
				if (paydto.getCourtAttachment() > paybillDTO.getCourtAttachment()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getCourtAttachment() - paybillDTO.getCourtAttachment()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getCourtAttachment())
								+ "\n";
				} else if (paydto.getCourtAttachment() < paybillDTO.getCourtAttachment()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}
			// Gic

			else if (CPSUtils.compareStrings(name, "Gic")) {
				if (paydto.getGic() > paybillDTO.getGic()) {
					if (recAmount == 0 || paybillDTO.getFinalPay() < 0)
						message1 = message + "\n";
					else if (recAmount < (paydto.getGic() - paybillDTO.getGic()))
						message1 += message + " and " + "\n Eligible " + name + " DUE amount is " + recAmount + " .Please dont exceed the amount Rs." + (recAmount + paybillDTO.getGic()) + "\n";
				} else if (paydto.getGic() < paybillDTO.getGic()) {
					if (paybillDTO.getFinalPay() > 0 || paydto.getFinalPay() > 0) {
						message1 = message + "\n";
					}

				}
			}

		} catch (Exception e) {
			throw e;
		}
		return message1;
	}

	@SuppressWarnings("unchecked")
	public List<PayBillDuesDTO> getPayBillDuesList(String sfid, String type) throws Exception {
		Session session = null;
		List<PayBillDuesDTO> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			list = (List<PayBillDuesDTO>) session.createCriteria(PayBillDuesDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("deductionName", type)).add(Expression.eq("status", 1))
					.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PayBillStatusDTO> getPaySlipMonthList() throws Exception {
		List<PayBillStatusDTO> keyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyList = session.createCriteria(PayBillStatusDTO.class).add(Expression.eq("payStatus", 52)).addOrder(Order.desc("id")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}

	@SuppressWarnings("unchecked")
	public List<String> getPaySlipYearList() throws Exception {
		List<String> keyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String query = "Select  UNIQUE To_char(Run_month,'yyyy') From  Paybill_status_details where pay_status=52 ORDER BY TO_CHAR(Run_month,'yyyy')";
			keyList = session.createSQLQuery(query).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}
	@SuppressWarnings("unchecked")
	public List<String> getPaySlipMonthList(String year) throws Exception {
		List<String> keyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String query = "select to_char(run_month,'Mon') from paybill_status_details where to_char(run_month,'yyyy')=? and status=1  and Pay_Status=? order by Run_Month";
			keyList = session.createSQLQuery(query).setString(0, year).setInteger(1, Integer.parseInt(CPSConstants.PAYUSERVISIBLESTATUS)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}
   @SuppressWarnings("unchecked")
	public PayBillStatusDTO startRegeneratingPayBill(String runMonth, String userId) throws Exception {
		Session session = null;
		List<PayBillUserDTO> usersList = null;
		String message = "";
		Transaction tx = null;
		PayBillStatusDTO payBillStatusDTO = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			payBillStatusDTO = new PayBillStatusDTO();
			//int runSubId = ((BigDecimal) session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE").setString(0, "PAY_RUN_SUB_ID").uniqueResult()).intValue();;
			//session.createSQLQuery("update id_generator set value=? where table_name='PAY_RUN_SUB_ID'").setInteger(0, runSubId+1).executeUpdate();
			int runSubId =Integer.parseInt(commonDataDAO.getSequenceValue(CPSConstants.RUNSUBID));
            payBillStatusDTO = (PayBillStatusDTO) session.createCriteria(PayBillStatusDTO.class).add(Expression.eq("runMonth", "01-" + runMonth)).add(Expression.eq("status", 1)).uniqueResult();

			if (CPSUtils.isNullOrEmpty(payBillStatusDTO)) {
				message = CPSConstants.FAILED;
			} else {
				payBillStatusDTO.setRunMonth(CPSUtils.formattedDate(payBillStatusDTO.getRunMonth()));
				PayBillStatusDTO payBillStatusLogDTO = new PayBillStatusDTO();
				BeanUtils.copyProperties(payBillStatusLogDTO, payBillStatusDTO);
				payBillStatusLogDTO.setId(0);
				payBillStatusLogDTO.setRunSubId(runSubId);

				payBillStatusDTO.setStatus(2);
				payBillStatusLogDTO.setCreatedBy(userId);
				payBillStatusLogDTO.setLastModifiedBy(userId);
				payBillStatusLogDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				payBillStatusLogDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				payBillStatusDTO.setLastModifiedBy(userId);
				payBillStatusDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());

				session.saveOrUpdate(payBillStatusDTO);
				session.saveOrUpdate(payBillStatusLogDTO);

				message = CPSConstants.SUCCESS;
			}

			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, message)) {
				String query = "select emp.sfid as sfID,epd.basic_id as basicId,epd.grade_id as gradeId,Epd.Category_Id as categoryId,epd.pay_designation_id as payDesignationId,family_planning as fpaFlag," + "handicap_id as phType, "
				+ "grade_pay as gPay,basic_pay as basicPay,epd.ccs_flag as ccsMemFlag,epd.ccs_sub as ccsSubAmt," + "epd.cgeis_group_id as cgeisGroupID,"
				+ "epd.cgeis_mem_status as cgeisMemFlag,epd.fpa_gradepay as fpaGradePay,epd.gpf_flag as gpfFlag,epd.gpf_subs_amt as gpfSubAmt," + "epd.income_tax as incomeTaxAmt,"
				+ "epd.tra_eligible_flag as tptFlag,epd.cghs_flag as cghsFlag," + "epd.two_addl_incr as twoAddIncr,epd.var_incr_points  as varIncrPts,epd.PAY_STOP as payStopFlag ,"
				+ " epd.ccs_no as ccsNo" + " from emp_master emp,emp_pay_master epd " + "where epd.sfid = emp.sfid and epd.status=1 And "
                + "emp.sfid in (select sfid from Pay_Txn_Details txn where txn.run_id=? and status in (1,60)) and emp.sfid='SF0846'";  //0 is changed to 1 by Narayana

		// status =0 condition removed from emp_master

		        usersList = session.createSQLQuery(query).addScalar("sfID", Hibernate.STRING).addScalar("basicId", Hibernate.STRING).addScalar("gradeId", Hibernate.STRING).addScalar("categoryId", Hibernate.STRING).addScalar("payDesignationId", Hibernate.STRING).addScalar("fpaFlag", Hibernate.STRING).addScalar("phType",
				Hibernate.INTEGER).addScalar("gPay", Hibernate.INTEGER).addScalar("basicPay", Hibernate.INTEGER).addScalar("ccsMemFlag", Hibernate.STRING).addScalar("ccsSubAmt",
				Hibernate.INTEGER).addScalar("cgeisGroupID", Hibernate.INTEGER).addScalar("fpaGradePay", Hibernate.INTEGER).addScalar(
				"gpfFlag", Hibernate.STRING).addScalar("gpfSubAmt", Hibernate.INTEGER).addScalar("payStopFlag", Hibernate.STRING).addScalar("incomeTaxAmt", Hibernate.INTEGER).addScalar("ccsNo", Hibernate.STRING).addScalar("tptFlag", Hibernate.STRING).addScalar("cghsFlag", Hibernate.STRING).addScalar("twoAddIncr",
				Hibernate.INTEGER).addScalar("varIncrPts", Hibernate.INTEGER).setInteger(0, payBillStatusDTO.getRunID()).setResultTransformer(Transformers.aliasToBean(PayBillUserDTO.class)).list();

				for (int i = 0; i < usersList.size(); i++) {

					PayBillUserDTO pbuser = usersList.get(i);
					System.out.print(pbuser.getSfID());
					System.out.print("Regeneration Started For*****************************************************"+pbuser.getSfID()+"**********************************************************");
					PayQuarterManagementDTO payQuarterManagementDTO=calculatePayBill.getEmployeeQuarterDetails(pbuser.getSfID(), CPSUtils.convertStringToDate("01-"+runMonth));
					PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO=(PayBillEmpPaymentDeatilsDTO)session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", pbuser.getSfID())).add(Expression.eq("status", 1)).uniqueResult();
					log.debug("<-------------PAY START FOR SFID :: " + pbuser.getSfID() + " -------------->");
					PayBillDTO payDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", pbuser.getSfID()))
							.add(Expression.eq("runId",payBillStatusDTO.getRunID())).add(Expression.in("status", new Integer[] {50})).list().get(0);
					if (!CPSUtils.isNullOrEmpty(payDTO)) {
						payDTO.setRunMonth(CPSUtils.formattedDate(payDTO.getRunMonth()));
						PayBillLogDTO payBillLogDTO = new PayBillLogDTO();
						BeanUtils.copyProperties(payBillLogDTO, payDTO);
						payDTO.setCrRemarks("");
						payDTO.setDrRemarks("");
						payDTO.setRecRemarks("");
						payBillLogDTO.setId(0);
						session.saveOrUpdate(payBillLogDTO);
					} else {
						payDTO = new PayBillDTO();
					}
                    PayTxnMastersDetailsDTO payTxnMastersDetailsDTO = (PayTxnMastersDetailsDTO) session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("sfid", pbuser.getSfID())).add(
					        Expression.eq("runId", payBillStatusDTO.getRunID())).add(Expression.eq("status", 50)).uniqueResult();
			         if (CPSUtils.isNullOrEmpty(payTxnMastersDetailsDTO)) {
				     payTxnMastersDetailsDTO = new PayTxnMastersDetailsDTO();
			        }else{
			        	payTxnMastersDetailsDTO.setRunMonth(CPSUtils.formattedDate(payTxnMastersDetailsDTO.getRunMonth()));
			        	PayTxnMasterLogDTO payTxnMasterLogDTO = new PayTxnMasterLogDTO();
			        	BeanUtils.copyProperties(payTxnMasterLogDTO, payTxnMastersDetailsDTO);
			        	payTxnMasterLogDTO.setId(0);
			        	session.saveOrUpdate(payTxnMasterLogDTO);
			        }
					String idWithValue = "";
					payDTO.setSfid(pbuser.getSfID());
					payTxnMastersDetailsDTO.setSfid(pbuser.getSfID());
					payTxnMastersDetailsDTO.setPayDesignationId(Integer.parseInt(pbuser.getPayDesignationId()));
					payTxnMastersDetailsDTO.setCategoryId(Integer.parseInt(pbuser.getCategoryId()));
					payTxnMastersDetailsDTO.setBasicId(Integer.parseInt(pbuser.getBasicId()));
					payTxnMastersDetailsDTO.setGradeId(Integer.parseInt(pbuser.getGradeId()));
					PayScaleDesignationDTO payScaleDesignationDTO = (PayScaleDesignationDTO) session.createCriteria(PayScaleDesignationDTO.class).add(
							Expression.eq("designation", pbuser.getPayDesignationId())).add(Expression.eq("status", 1)).uniqueResult();
					payTxnMastersDetailsDTO.setPayBandId(Integer.parseInt(payScaleDesignationDTO.getPayband()));
					// Credits
					
					/* Basic Pay Manipulations  */	
					payDTO.setBasicPay(pbuser.getBasicPay());
					
					/*List<EmpBasicPayHistoryDTO> empBasicPayHistoryList=session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,pbuser.getSfID())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.eq("presentEffectiveDate", session.createCriteria(EmpBasicPayHistoryDTO.class).add(Expression.eq(CPSConstants.SFID,pbuser.getSfID())).add(Expression.eq(CPSConstants.STATUS,1)).add(Expression.not(Expression.in("incrementType", new String[]{"P"}))).setProjection(Projections.max("presentEffectiveDate")).uniqueResult())).add(Expression.ne("incrementType", "P")).addOrder(Order.asc("incrementType")).list();
					 for(int k=0;k<empBasicPayHistoryList.size();k++){
						 EmpBasicPayHistoryDTO empBasicPayHistoryDTO=empBasicPayHistoryList.get(k);
						 payBillEmpPaymentDeatilsDTO.setBasicRefId(empBasicPayHistoryDTO.getId()); 
						 payDTO.setBasicPay((int)empBasicPayHistoryDTO.getBasicPay());
					 }*/
					 
					 String bascPayFromAnnualIncrWithRemarks = payBillPreProcess.basicPayPreProcessWithRespToAnnualIncrement(pbuser.getSfID(), runMonth);
						if (Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]) > 0) {
							payDTO.setBasicPay(Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]));
							payBillEmpPaymentDeatilsDTO.setBasicRefId(Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[3]));
							payDTO.setCrRemarks(bascPayFromAnnualIncrWithRemarks.split("@")[1]);
						}
						
						/* Grade Pay Manipulations  */	
				
					payDTO.setGradePay(pbuser.getgPay());
					payDTO.setTwoAddlIncr(pbuser.getTwoAddIncr());
					
				   PromoteesEntryDTO promoteesEntryDTO=calculatePayBill.getPromotedTopMostRecord("01-"+runMonth, payDTO.getSfid());
					if(!CPSUtils.isNullOrEmpty(promoteesEntryDTO)){
						payDTO.setGradePay((int)promoteesEntryDTO.getNewGradePay());
						payDTO.setTwoAddlIncr(Integer.parseInt(promoteesEntryDTO.getTwoAddl()));
						int check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth),promoteesEntryDTO.getPresentEffectiveDate());
					    if(check>=0){
					    	check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth),promoteesEntryDTO.getVarIncEndDate());
						    if(check<0){
						    	pbuser.setVarIncrPts(promoteesEntryDTO.getVarIncPt());
						    }else{
						    	pbuser.setVarIncrPts(0);
						    }
					    }else{
					    	pbuser.setVarIncrPts(0);
					    }
					    payBillEmpPaymentDeatilsDTO.setGradeRefId(promoteesEntryDTO.getId());
					}else{
						payBillEmpPaymentDeatilsDTO.setGradeRefId(payBillEmpPaymentDeatilsDTO.getGradeId());
					}
				    
					PayProPreProcessDTO payProPreProcessDTO=payBillPreProcess.integratingWithPromotion(pbuser.getSfID(), "01-"+runMonth, payBillEmpPaymentDeatilsDTO.getGradeRefId());

					if(!CPSUtils.isNullOrEmpty(payProPreProcessDTO)){
						payDTO.setBasicPay(payProPreProcessDTO.getNewBasic());
						payBillEmpPaymentDeatilsDTO.setBasicRefId(payProPreProcessDTO.getBasicId());
						payDTO.setGradePay(payProPreProcessDTO.getNewGrade());
						payBillEmpPaymentDeatilsDTO.setGradeRefId(payProPreProcessDTO.getGradeId());
						payDTO.setTwoAddlIncr(payProPreProcessDTO.getNewTwoAdd());
						pbuser.setVarIncrPts(payProPreProcessDTO.getVarIncrPoints());
						payTxnMastersDetailsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
						payBillEmpPaymentDeatilsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
						payTxnMastersDetailsDTO.setPayDesignationId(payProPreProcessDTO.getDesignationId());
	               }
					
					idWithValue = calculatePayBill.getVarIncr(payDTO.getGradePay(), pbuser.getVarIncrPts());
					payDTO.setVariableIncr(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setVarIncrId(Integer.parseInt(idWithValue.split("@")[0]));

					idWithValue = calculatePayBill.getDA(payDTO.getBasicPay(), payDTO.getGradePay(), "01-" + runMonth);
					payDTO.setDa(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setDaMasterId(Integer.parseInt(idWithValue.split("@")[0]));

					idWithValue = calculatePayBill.getSplPay("" + pbuser.getPayDesignationId());
					payDTO.setSpecialPay(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setSpecialPayId(Integer.parseInt(idWithValue.split("@")[0]));

					if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getFpaFlag())) {

						idWithValue = calculatePayBill.getFpa(pbuser.getFpaGradePay());
						payDTO.setFpa(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setFpaMastersId(Integer.parseInt(idWithValue.split("@")[0]));
					} else {
						payTxnMastersDetailsDTO.setFpaMastersId(0);
						payDTO.setFpa(0);
					}

					
					if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getTptFlag())) {
						idWithValue = calculatePayBill.getTra(payDTO.getBasicPay(), pbuser.getSfID(), payDTO.getGradePay(), "01-" + runMonth);
						payDTO.setTpt(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setTraMasterId(Integer.parseInt(idWithValue.split("@")[0]));
					} else {
						payDTO.setTpt(0);
						payTxnMastersDetailsDTO.setTraMasterId(0);
					}
					
					payDTO.setHra(calculatePayBill.getHra(payDTO.getBasicPay(), payDTO.getGradePay(),payDTO.getSfid(),new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth)));

					idWithValue = calculatePayBill.getWashAllowance(pbuser.getSfID());
					payDTO.setWashAllowance(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setWashAllwId(Integer.parseInt(idWithValue.split("@")[0]));
					
					payDTO.setXeroxAllowance(0);

				    payDTO.setRiskAllowance(0);
					payDTO.setDeputAllowance(0);
					payDTO.setCrMisc(0);

					Object[] hindiInc = calculatePayBill.getHindiIncentive(pbuser.getSfID(),payDTO.getBasicPay(),payDTO.getGradePay(), runMonth);
					payDTO.setHindiIncentive(((BigDecimal) hindiInc[0]).intValue());
					if (!CPSUtils.isNullOrEmpty(hindiInc[1]))
						session.saveOrUpdate(hindiInc[1]);

					int totalCredits = payDTO.getBasicPay() + payDTO.getGradePay() + payDTO.getDa() + payDTO.getSpecialPay() + payDTO.getFpa() + payDTO.getTwoAddlIncr() + payDTO.getTpt()
							+ payDTO.getHra() + payDTO.getWashAllowance() + payDTO.getXeroxAllowance() + payDTO.getVariableIncr() + payDTO.getHindiIncentive() + payDTO.getRiskAllowance()
							+ payDTO.getDeputAllowance() + payDTO.getCrMisc();
					payDTO.setTotalCredits(totalCredits);

					// Deduction
					if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getGpfFlag())) {
						if (Integer.parseInt(bascPayFromAnnualIncrWithRemarks.split("@")[0]) > 0) {
							String flag = iPayBillMasterDAO.checkGPFmember(pbuser.getSfID());
							if (!CPSUtils.compareStrings(flag, CPSConstants.YES)) {
								payDTO.setGpf((int) Math.round((payDTO.getBasicPay() + payDTO.getGradePay() + payDTO.getDa()) * 10 / 100));
								PayBillEmpPaymentDeatilsDTO empPayDTO = (PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(
										Expression.eq("sfID", pbuser.getSfID())).add(Expression.eq("status", 1)).uniqueResult();
								empPayDTO.setGpfSubAmt(payDTO.getGpf());
								session.saveOrUpdate(empPayDTO);
							} else {
								payDTO.setGpf(pbuser.getGpfSubAmt());
							}
						} else {
							payDTO.setGpf(pbuser.getGpfSubAmt());
						}
					} else
						payDTO.setGpf(0);

					payDTO.setPli(0);

					if (CPSUtils.compareStrings(pbuser.getCghsFlag(), CPSConstants.YES)) {
						idWithValue = calculatePayBill.getCGHS(payDTO.getGradePay());
						payDTO.setCghs(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setCghsMastersId(Integer.parseInt(idWithValue.split("@")[0]));
					} else {
						payDTO.setCghs(0);
						payTxnMastersDetailsDTO.setCghsMastersId(0);
					}

					idWithValue = calculatePayBill.getCEGIS(pbuser.getSfID(), pbuser.getCgeisGroupID());
					payDTO.setCegis(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setCgeisMastersId(Integer.parseInt(idWithValue.split("@")[0]));

					if (!CPSUtils.isNullOrEmpty(payQuarterManagementDTO)) {
						idWithValue = calculatePayBill.getLicenceFee(pbuser.getSfID());
						payDTO.setRent(Integer.parseInt(idWithValue.split("@")[1]));
						payDTO.setElec(Integer.parseInt(idWithValue.split("@")[4]));
						payDTO.setWater(Integer.parseInt(idWithValue.split("@")[2]));
						payDTO.setFurn(Integer.parseInt(idWithValue.split("@")[3]));
						payTxnMastersDetailsDTO.setLicFeeMastersId(Integer.parseInt(idWithValue.split("@")[0]));

						idWithValue = calculatePayBill.getResSecurity(pbuser.getSfID());
						payDTO.setResSecu(Integer.parseInt(idWithValue.split("@")[1]));
						payTxnMastersDetailsDTO.setResSecuMastersId(Integer.parseInt(idWithValue.split("@")[0]));

					} else {
						payDTO.setRent(0);
						payDTO.setResSecu(0);
						payDTO.setElec(0);
						payDTO.setWater(0);
						payDTO.setFurn(0);
					}
					Object[] loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORCYCLELOANID, runMonth,payBillStatusDTO.getRunID(), userId);

					payDTO.setCycleLoan((Integer) loan[0]);
					payDTO.setCycleTotInst((Integer) loan[1]);
					payDTO.setCycleInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCycleLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);
					
					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.FESTIVALLOANID, runMonth,payBillStatusDTO.getRunID(), userId);
					payDTO.setFestivalAdv((Integer) loan[0]);
					payDTO.setFestivTotInst((Integer) loan[1]);
					payDTO.setFestivInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setFestLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);
					
					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.GPFADVANCELOANID, runMonth,payBillStatusDTO.getRunID(), userId);
					payDTO.setGpfRecovery((Integer) loan[0]);
					payDTO.setGpfTotAmt((Integer) loan[1]);
					payDTO.setGpfPreInst((Integer) loan[2]);
					payTxnMastersDetailsDTO.setGpfAdvanceLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);

					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORCARLOANID, runMonth,payBillStatusDTO.getRunID(), userId);
					payDTO.setCarLoan((Integer) loan[0]);
					payDTO.setCarTotInst((Integer) loan[1]);
					payDTO.setCarInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCarLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);

					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.PCLOANID, runMonth,payBillStatusDTO.getRunID(), userId);
					payDTO.setPcLoan((Integer) loan[0]);
					payDTO.setPcTotInst((Integer) loan[1]);
					payDTO.setPcInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setPcLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);


					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.MOTORSCOOTERLOANID, runMonth,payBillStatusDTO.getRunID(), userId);
					payDTO.setScooterLoan((Integer) loan[0]);
					payDTO.setScooterTotInst((Integer) loan[1]);
					payDTO.setScooterInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setScooterLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);

					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.HBALOANID, runMonth,payBillStatusDTO.getRunID(), userId);
					payDTO.setHbaLoan((Integer) loan[0]);
					payDTO.setHbaTotInst((Integer) loan[1]);
					payDTO.setHbaInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setHbaLoanId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);
					
					loan = calculatePayBill.getLoan(pbuser.getSfID(), CPSConstants.WELFARELOANID, runMonth,payBillStatusDTO.getRunID(), userId);
					
					payDTO.setWelRefund((Integer) loan[0]);
					payDTO.setWelTotinst((Integer) loan[1]);
					payDTO.setWelInst((Integer) loan[2]);
					payTxnMastersDetailsDTO.setWelFundId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					if (!CPSUtils.isNullOrEmpty(loan[5]))
						session.saveOrUpdate(loan[5]);

					payDTO.setIncomeTax(calculatePayBill.getITax(pbuser.getIncomeTaxAmt()));
					payDTO.setCess(calculatePayBill.getCess(pbuser.getIncomeTaxAmt()));
					payDTO.setSecondaryCess(calculatePayBill.getSHEC(pbuser.getIncomeTaxAmt()));
					if (pbuser.getIncomeTaxAmt() < payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess())
						payDTO.setIncomeTax(payDTO.getIncomeTax() - 1);
					else if (pbuser.getIncomeTaxAmt() > payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess())
						payDTO.setIncomeTax(payDTO.getIncomeTax() + 1);

					idWithValue = calculatePayBill.getProfTax(pbuser.getSfID(), totalCredits);
					payDTO.setProfTax(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setProfTaxId(Integer.parseInt(idWithValue.split("@")[0]));
					payDTO.setGpfSa(0);
					payDTO.setTada(0);
					payDTO.setMedical(0);
					payDTO.setLtc(0);
					payDTO.setEol(0);
					payDTO.setDrMisc1(0);
					payDTO.setDrMisc2(0);

					int totalDebits = payDTO.getGpf() + payDTO.getGpfSa() + payDTO.getGpfRecovery() + payDTO.getPli() + payDTO.getCghs() + payDTO.getCegis() + payDTO.getRent() + payDTO.getElec()
							+ payDTO.getWater() + payDTO.getFurn() + payDTO.getCycleLoan() + payDTO.getFestivalAdv() + payDTO.getCarLoan() + payDTO.getPcLoan() + payDTO.getScooterLoan()
							+ payDTO.getHbaLoan() + payDTO.getIncomeTax() + payDTO.getCess() + payDTO.getSecondaryCess() + payDTO.getProfTax() + payDTO.getTada() + payDTO.getMedical()
							+ payDTO.getLtc() + payDTO.getEol() + payDTO.getDrMisc1() + payDTO.getDrMisc2();
					payDTO.setTotalDebits(totalDebits);
					payDTO.setNetPay(totalCredits - totalDebits);

					// Coin Cut or Recoveries

					idWithValue = calculatePayBill.getWelFund(pbuser.getPayDesignationId());
					payDTO.setWelC(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setWelFundId(Integer.parseInt(idWithValue.split("@")[0]));
					
					idWithValue = calculatePayBill.getMess(pbuser.getPayDesignationId(),payQuarterManagementDTO==null?"No":"Yes");
					payDTO.setMess(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setMessId(Integer.parseInt(idWithValue.split("@")[0]));

					idWithValue = calculatePayBill.getBenFund(pbuser.getPayDesignationId());
					payDTO.setBenvolentFund(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setBenFundId(Integer.parseInt(idWithValue.split("@")[0]));

					idWithValue = calculatePayBill.getRegFund(pbuser.getPayDesignationId());
					payDTO.setRegimentalFund(Integer.parseInt(idWithValue.split("@")[1]));
					payTxnMastersDetailsDTO.setRegFundId(Integer.parseInt(idWithValue.split("@")[0]));

					payDTO.setCcs(pbuser.getCcsSubAmt());
					payDTO.setCcsrecovery(calculatePayBill.getCcsRecovery(pbuser.getSfID(), pbuser.getCcsSubAmt()));
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.CANFIN, runMonth,payBillStatusDTO.getRunID(),userId);
             		payDTO.setCanfin(((Float) loan[0]).intValue());
					payDTO.setCanfinTotInst((Integer) loan[1]);
					payDTO.setCanfinInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCanfinDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.HDFC, runMonth,payBillStatusDTO.getRunID(),userId);
					payDTO.setHdfc(((Float) loan[0]).intValue());
					payDTO.setHdfcTotInst((Integer) loan[1]);
					payDTO.setHdfcInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setHdfcDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.GIC, runMonth,payBillStatusDTO.getRunID(),userId);
					payDTO.setGic(((Float) loan[0]).intValue());
					payDTO.setGicTotInst((Integer) loan[1]);
					payDTO.setGicInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setGicDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.LIC, runMonth,payBillStatusDTO.getRunID(),userId);
					payDTO.setLic(((Float) loan[0]).intValue());
					payDTO.setLicTotInst((Integer) loan[1]);
					payDTO.setLicInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setLicDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					loan = calculatePayBill.getDeduction(pbuser.getSfID(), CPSConstants.COURTATTACHMENT, runMonth,payBillStatusDTO.getRunID(),userId);
					payDTO.setCourtAttachment(((Float) loan[0]).intValue());
					payDTO.setCourtTotInst((Integer) loan[1]);
					payDTO.setCourtInstNumb((Integer) loan[2]);
					payTxnMastersDetailsDTO.setCourtAttchDeduId((Integer) loan[3]);
					if (!CPSUtils.isNullOrEmpty(loan[4]))
						session.saveOrUpdate(loan[4]);
					
					payDTO.setRecMisc1(0);
					payDTO.setRecMisc2(0);
					payDTO.setRecMisc3(0);
					payDTO.setCcsrecovery(0);
					int totalRecoveries = payDTO.getWelC() + payDTO.getMess() + payDTO.getBenvolentFund() + payDTO.getRegimentalFund() + payDTO.getCcs() + payDTO.getCcsrecovery() + payDTO.getCanfin()
							+ payDTO.getHdfc() + payDTO.getGic() + payDTO.getLic() + payDTO.getCourtAttachment() + payDTO.getWelRefund() + payDTO.getResSecu() + payDTO.getRecMisc1()
							+ payDTO.getRecMisc2() + payDTO.getRecMisc3();

					payDTO.setTotalRecovery(totalRecoveries);
					payDTO.setFinalPay(totalCredits - (totalDebits + totalRecoveries));

					payDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					payTxnMastersDetailsDTO.setCreationDate(CPSUtils.getCurrentDate());
					payDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					payTxnMastersDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					payDTO.setCreatedBy(userId);
					payTxnMastersDetailsDTO.setCreatedBy(userId);
					payDTO.setLastModifiedBy(userId);
					payTxnMastersDetailsDTO.setLastModifiedBy(userId);
					payTxnMastersDetailsDTO.setRunId(payBillStatusDTO.getRunID());
					payDTO.setRunMonth("1-" + runMonth);
					payDTO.setRunId(payBillStatusDTO.getRunID());
					payDTO.setRunSubId(runSubId);
					payTxnMastersDetailsDTO.setRunMonth("1-" + runMonth);
					payDTO.setStatus(Integer.parseInt(CPSConstants.PAYAUTORUNSTATUS));
					payTxnMastersDetailsDTO.setStatus(50);
					payTxnMastersDetailsDTO.setRunSubId(runSubId);
					payTxnMastersDetailsDTO.setEolHplId(0);
					PayBillDTO payDTO1 = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.in("status", new Integer[] { 1, 60 })).add(Expression.eq("sfid", pbuser.getSfID())).add(
							Expression.eq("runId",payBillStatusDTO.getRunID())).uniqueResult();  //0 is changed to 1 by Narayana
					if (!CPSUtils.isNullOrEmpty(payDTO1)) {
						PayBillLogDTO payBillLogDTO = new PayBillLogDTO();
						BeanUtils.copyProperties(payBillLogDTO, payDTO1);
						payBillLogDTO.setRunMonth(CPSUtils.formattedDate(payBillLogDTO.getRunMonth()));
						payBillLogDTO.setId(0);
						session.saveOrUpdate(payBillLogDTO);
					} else {
						payDTO1 = new PayBillDTO();
					}
					if (CPSUtils.compareStrings(CPSConstants.YES, pbuser.getPayStopFlag())) {
						payDTO1.setSfid(payDTO.getSfid());
						payDTO1.setRunId(payDTO.getRunId());
						payDTO1.setRunSubId(runSubId);
						payDTO1.setRunMonth(payDTO.getRunMonth());
						payDTO1.setCreationTime(payDTO.getCreationTime());
						payDTO1.setCreatedBy(payDTO.getCreatedBy());
						payDTO1.setLastModifiedBy(payDTO.getLastModifiedBy());
						payDTO1.setLastModifiedTime(payDTO.getLastModifiedTime());
						payDTO1.setStatus(1);
					} else {
						int beanId = payDTO1.getId();
						BeanUtils.copyProperties(payDTO1, payDTO);
						payDTO1.setId(beanId);
						payDTO1.setRunSubId(runSubId);
						payDTO1.setStatus(1);//0 is changed to 1 by Narayana
					}
					session.saveOrUpdate(payTxnMastersDetailsDTO);
					session.saveOrUpdate(payDTO);
					session.saveOrUpdate(payDTO1);
					session.saveOrUpdate(payBillEmpPaymentDeatilsDTO);
					System.out.print("Regeneration Ended For*****************************************************"+pbuser.getSfID()+"**********************************************************");
				}

				session.flush();//tx.commit() ;
				message = CPSConstants.SUCCESS;

				payBillStatusDTO.setRunStatus(0);
				payBillStatusDTO.setManualStatus(1);
				payBillStatusDTO.setUserStatus(0);
				payBillStatusDTO.setDescription(message);
				payBillStatusDTO.setCount(getCountToGeneratePayBill());
				payBillStatusDTO.setGeneratedCount(getCountForAlreadyGeneratedPayBill(runMonth));

			} else {
				message = CPSConstants.AUTORUNEXIST;
			}
		} catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return payBillStatusDTO;
	}
	public String getCountToGeneratePayBill() throws Exception{
		String count=null;
		Session session=null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			count=(String)session.createSQLQuery(" Select Count(*) count From Emp_Master Emp,Emp_Pay_Master Epd Where epd.sfid = emp.sfid and epd.status=1").uniqueResult().toString();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return count;
	}
	public String getCountForAlreadyGeneratedPayBill(String runMonth) throws Exception{
		String count=null;
		Session session=null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			count=(String)session.createSQLQuery("Select Count(*) From Pay_Txn_Details Where to_char(Run_Month,'Mon-yyyy')='"+runMonth+"' and Status=50").uniqueResult().toString();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return count;
	}
  // new code for missing sfid after paybillgenerated
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> missingSfidForAlreadyGeneratedPayBill(String runMonth)throws Exception{
		List<KeyValueDTO> sfidList =null;
		Session session=null;
		try{
			session = hibernateUtils.getSession();
			sfidList=session.createSQLQuery("select emp.sfid ||'(' ||em.name_in_service_book || ')' as name,dm.name as value from emp_pay_master emp,designation_master dm ,emp_master em where dm.id=emp.pay_designation_id and dm.status=1 and emp.status=1 and em.status=1 and em.sfid=emp.sfid  and emp.pay_designation_id=em.designation_id and emp.sfid not in(select sfid from pay_txn_details where run_month ='"+"01-"+runMonth+"')").addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}
		return sfidList;
	}
	public PayBillBean countOfRegularAndSupplementaryForGenPayBill(PayBillBean payBillBean) throws Exception{
		Session session = null;
		try{
			session=hibernateUtils.getSession();
		String regCount	=((BigDecimal)session.createSQLQuery("Select Count(*) From Pay_txn_details Ptd,Pay_emp_category_master Pecm, Emp_pay_master Emp Where To_char(Run_month,'Mon-yyyy')='"+payBillBean.getRunMonth()+"' And Emp.Sfid =Ptd.Sfid And Emp.Category_id =Pecm.Id And Ptd.Status =50 and pecm.paybill_type='Regular'").uniqueResult()).toString();
		String suppCount=((BigDecimal)session.createSQLQuery("Select Count(*) From Pay_txn_details Ptd,Pay_emp_category_master Pecm, Emp_pay_master Emp Where To_char(Run_month,'Mon-yyyy')='"+payBillBean.getRunMonth()+"' And Emp.Sfid =Ptd.Sfid And Emp.Category_id =Pecm.Id And Ptd.Status =50 and pecm.paybill_type='Supplementary'").uniqueResult()).toString();
		payBillBean.setPayRegCount(regCount);
		payBillBean.setPaySuppCount(suppCount);
		}catch (Exception e) {
			throw e;
		}
		return payBillBean;
	} 
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getPayAutoRunList() throws Exception {
		List<KeyValueDTO> keyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//keyList = session.createCriteria(PayBillStatusDTO.class).add(Expression.eq("status", 1)).addOrder(Order.desc("runMonth")).list();
			keyList = session.createSQLQuery("select to_char(run_month,'dd-MON-yyyy') key,to_char(run_month,'MON-yyyy') value from paybill_status_details where status=1 order by run_month desc").addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}
	//supplimentary bill code
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getEmpSupplimentaryBillDetails(PayBillBean payBillBean) throws Exception{
		Session session = null;
		List<KeyValueDTO> empSuppList =null;
		try{
			session=hibernateUtils.getSession();
			String query="Select em.sfid as key, Emp.Sfid || '(' ||em.Name_in_service_book || ')' As name From Emp_pay_master Emp, Emp_master Em,pay_emp_category_master pecm Where Emp.Sfid=Em.Sfid And  Emp.Status=1 and pecm.id=emp.category_id And '"+payBillBean.getPayRunMonth()+"' Between Em.Doj_govt And Get_retirement_date (Emp.Sfid) and pecm.paybill_type='Supplementary' and emp.sfid not in (Select Sfid From Pay_txn_details Where Run_month ='"+payBillBean.getPayRunMonth()+"')";
			empSuppList=session.createSQLQuery(query).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}
		return empSuppList;
	}
	
	//for getting FinYear Id of Particular Date
	@SuppressWarnings("unchecked")
	public int getFinYearId(String month) throws Exception{
		Session session = null;
		int finYearId=0;
		
		try{
			session=hibernateUtils.getSession();
			String query="select id from  pay_it_fin_year_master where status=1 and to_date('"+month+"','dd-Mon-yyyy') " +
					" between to_date('01-Mar-'||from_year,'dd-Mon-yyyy') and to_date('01-Feb-'||to_year,'dd-Mon-yyyy')";
			Object obj = session.createSQLQuery(query).uniqueResult();
			if(obj!=null && obj!=""){
				finYearId = Integer.parseInt(obj.toString());	
			}
		}catch (Exception e) {
			throw e;
		}
		return finYearId;
	}
	 // month list for edit employee
	@SuppressWarnings("unchecked")
	public List<String> getMonthListForEditEmployee(String year) throws Exception {
		List<String> keyList = null;
		
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
		/*	String query = "select to_char(run_month,'Mon') from paybill_status_details where to_char(run_month,'yyyy')=? and status=1  and Pay_Status in (50,51,52) order by Run_Month";*/
			
			// query for getting the max month from a year.....which year having 
			// status is 1 and pay status in 50,51,52.
			String query="select to_char(run_month,'Mon') " +
					"from paybill_status_details " +
					"where to_char(run_month,'yyyy')=?" +
					" and status=1  and Pay_Status in (50,51,52)" +
					" order by Run_Month desc";
		keyList = session.createSQLQuery(query).setString(0, year).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}
	 // year list for edit employee
	@SuppressWarnings("unchecked")
	public List<String> getYearListForEditEmployee() throws Exception {
		List<String> keyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//String query = "Select  UNIQUE To_char(Run_month,'yyyy') From  Paybill_status_details where pay_status in(50,51,52) ORDER BY TO_CHAR(Run_month,'yyyy')";
		  
			//QUERY FOR GETTING THE YEARS FROM PAYBILL_STATUS_DETAILS TABLE IN DECENDING ORDER
					
			String query="SELECT UNIQUE TO_CHAR(Run_month,'yyyy')"+
		 				" FROM Paybill_status_details"+
		 				" WHERE pay_status IN(50,51,52)"+
		 				" ORDER BY TO_CHAR(Run_month,'yyyy') desc";
			/*//queary for getting  year from the paybill_status_details table
			//where pay_status in 50,51,52 and using by order by clause.
			
			
*/			keyList = session.createSQLQuery(query).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}

	@Override
	public List<String> getEmpNameList(String empName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getOTCategoryList() throws Exception
	{
		List<KeyValueDTO> catList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();
			String sql = "SELECT DISTINCT pecm.id AS id, pecm.category_name AS name FROM pay_txn_master_details ptmd, pay_emp_category_master pecm, ot_hrst oh "
					+ "WHERE ptmd.sfid = oh.sfid AND pecm.id = ptmd.category_id AND pecm.status = 1 ORDER BY id ASC";
			catList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
		}catch(Exception e)
		{
			throw e;
		}
		return catList;
	}
	//recalculation for edit employee data //kumari
	public PayBillBean getRecalcultedPayDetails(PayBillBean payBillBean) throws Exception
	{ 
		Session session = null;
		PayBillBean recalculatedPayDetails=null;
		try
		{
			session = hibernateUtils.getSession();
			java.util.Date date=CPSUtils.convertStringToDate("01-"+payBillBean.getRunMonth());
			PayTxnMastersDetailsDTO txnMastersDetailsDTO=(PayTxnMastersDetailsDTO)session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("sfid", payBillBean.getSearchSfid())).add(Expression.eq("runMonth", "01-"+payBillBean.getRunMonth().toUpperCase())).uniqueResult();
			
			int noOfDaysInMonth=CPSUtils.getNoofDaysInMonth(date.getMonth()+1);
			String query = "select nvl(calculate_da_val("+payBillBean.getBasicPay()+","+payBillBean.getGradePay()+",'01-"+payBillBean.getRunMonth().toUpperCase()+"'),0) da," +
					       " nvl(calculate_hra("+payBillBean.getBasicPay()+","+payBillBean.getGradePay()+",calculate_hra_days('"+payBillBean.getSearchSfid()+"','01-"+payBillBean.getRunMonth().toUpperCase()+"'),"+noOfDaysInMonth+"),0) hra," +
					       " nvl(case when "+txnMastersDetailsDTO.getTraMasterId()+"!=0 then Calculate_Tra_Val("+payBillBean.getBasicPay()+",'"+payBillBean.getSearchSfid()+"',"+payBillBean.getGradePay()+",'01-"+payBillBean.getRunMonth()+"') END ,0)tpt," +
					       " (case when  check_gpf_cps_member('"+payBillBean.getSearchSfid()+"')='CPS' and "+payBillBean.getPayDetails().getGpf()+"!=0 then calculate_cps_amount("+payBillBean.getBasicPay()+","+payBillBean.getGradePay()+") ELSE "+payBillBean.getPayDetails().getGpf()+" END) gpf from dual"; //here if sfid eligible for cps then only recalculate cps amount or else display old gpf amount //kumari
			
            Object obj = session.createSQLQuery(query).addScalar("da",Hibernate.INTEGER).addScalar("hra", Hibernate.INTEGER).addScalar("tpt", Hibernate.INTEGER).addScalar("gpf", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayBillBean.class)).uniqueResult();
           // setFloat(0, payBillBean.getBasicPay()).setFloat(1, payBillBean.getGradePay()).setString(2, "01-"+payBillBean.getRunMonth().toUpperCase()).setFloat(3, payBillBean.getBasicPay()).setFloat(4, payBillBean.getGradePay()).setString(5, payBillBean.getSearchSfid()).setString(6, "01-"+payBillBean.getRunMonth().toUpperCase()).setInteger(7, noOfDaysInMonth).setFloat(8, payBillBean.getBasicPay()).setString(9, payBillBean.getSearchSfid()).setFloat(10, payBillBean.getGradePay()).setString(11, "01-"+payBillBean.getRunMonth().toUpperCase()).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(obj)){
			recalculatedPayDetails=(PayBillBean)obj;
		} 
            
		}catch(Exception e)
		{
			throw e;
		}
		return recalculatedPayDetails;
	}

	@Override
	public  PayBillStatusDTO CheckPayAutoRun(PayBillBean paybillbean)
			throws Exception {
		// TODO Auto-generated method stub
		  
		try {
			 
			  
			  
			  
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 
		return null;
	}
	
}


