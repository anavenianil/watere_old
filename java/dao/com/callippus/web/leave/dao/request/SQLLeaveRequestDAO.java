package com.callippus.web.leave.dao.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.business.leave.request.CheckHolidays;
import com.callippus.web.business.leave.request.CheckLeaveMapping;
import com.callippus.web.business.leave.request.CommonConstraints;
import com.callippus.web.business.leave.request.NumberOfLeaves;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dto.AvailableLeavesDTO;
import com.callippus.web.leave.dto.EmpLeaveSpellsDTO;
import com.callippus.web.leave.dto.LeaveAmendmentDTO;
import com.callippus.web.leave.dto.LeaveCardDTO;
import com.callippus.web.leave.dto.LeaveExceptionDetailsDTO;
import com.callippus.web.leave.dto.LeaveFamilyDTO;
import com.callippus.web.leave.dto.LeaveRequestExceptionsDTO;
import com.callippus.web.leave.dto.LeaveTxnDTO;
import com.callippus.web.leave.dto.LeaveYearlyBalanceDTO;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

@SuppressWarnings("serial")
@Service
public class SQLLeaveRequestDAO implements ILeaveRequestDAO, Serializable {
	@Autowired
	private CheckHolidays checkHolidays;
	@Autowired
	private NumberOfLeaves numberOfLeaves;
	@Autowired
	private CheckLeaveMapping checkLeaveMapping;
	@Autowired
	private CommonConstraints commonConstraints;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getAvailableLeaves(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			// get employee gender
			EmployeeBean employeeBean = (EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq(CPSConstants.USERSFID, leaveBean.getSfID().toUpperCase())).uniqueResult();
			if (!CPSUtils.isNull(employeeBean)) {
				leaveBean.setEmpGender(employeeBean.getGender());
				/*List<LeaveManagementBean> leaves = session.createCriteria(LeaveManagementBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
						Restrictions.or(Restrictions.eq(CPSConstants.ELIGIBILITYFLAG, "B"), Restrictions.eq(CPSConstants.ELIGIBILITYFLAG, employeeBean.getGender()))).list();*/
				List<LeaveManagementBean> leaves = session.createSQLQuery("select ltd.* from leave_type_details ltd, leave_type_master ltm where ltd.leave_type_id = ltm.id "
						+ "and ltd.eligibility_flag in (?, ?) and ltm.status = 1 and ltd.status = 1 order by ltm.order_no").addEntity(LeaveManagementBean.class)
						.setString(0, "B").setString(1, employeeBean.getGender()).list();
				// Employee with less than 5 years of service is not eligible to apply Study Leave  
				int years = CPSUtils.yearsDifference(CPSUtils.formattedDate(employeeBean.getDojGovt().split(" ")[0]));
				for (LeaveManagementBean leaveKind : leaves) {
					if (CPSUtils.compareStrings(leaveKind.getLeaveTypeId(), CPSConstants.SL) && (years < Integer.parseInt(CPSConstants.SLELIGIBILITYCONFIGURATION))) {
						leaves.remove(leaveKind);
						break;
					}
				}
				// If the employee is married male employee then show paternity leave
				if (CPSUtils.compareStrings(employeeBean.getGender(), "M") && employeeBean.getMarital() != 1) {
					for (LeaveManagementBean leave : leaves) {
						if (CPSUtils.compareStrings(leave.getLeaveTypeId(), CPSConstants.PL)) {
							leaves.remove(leave);
							break;
						}
					}
				}
				leaveBean.setLeaveDetailsList(leaves);
			} else {
				leaveBean.setResult(CPSConstants.INVALID);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getSpecialCasualList(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			leaveBean
					.setSpecialCasualList(session
							.createSQLQuery(
									"select id,category_type categoryType,leave_sub_type leaveSubType,to_char(from_date,'DD-MON-YYYY') strFromDate,to_char(to_date,'DD-MON-YYYY') strToDate,eligibility eligibilityFlag,no_of_days noOfDays,second_time_remarks secondTimeRemarks,"
											+ "prior_approval_flag priorApprovalFlag,notice_period_flag noticePeriodFlag,medical_certificate_flag medicalCertFlag,other_certificate_flag otherCertFlag,certificate_name otherFileName "
											+ "from (select * from special_casual_leaves where status=1 and eligibility in ('B',?) and category_type='T1' union select * from special_casual_leaves where status=1 and eligibility in ('B',?) "
											+ "and category_type='T2' and to_date+no_of_days>=sysdate)").addScalar("id", Hibernate.INTEGER).addScalar("categoryType", Hibernate.STRING).addScalar(
									"leaveSubType", Hibernate.STRING).addScalar("strFromDate", Hibernate.STRING).addScalar("strToDate", Hibernate.STRING)
							.addScalar("eligibilityFlag", Hibernate.STRING).addScalar("noOfDays", Hibernate.STRING).addScalar("secondTimeRemarks", Hibernate.STRING).addScalar("priorApprovalFlag",
									Hibernate.STRING).addScalar("noticePeriodFlag", Hibernate.STRING).addScalar("medicalCertFlag", Hibernate.STRING).addScalar("otherCertFlag", Hibernate.STRING)
							.addScalar("otherFileName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(SpecialCasualLeaveDTO.class)).setString(0, leaveBean.getEmpGender()).setString(
									1, leaveBean.getEmpGender()).list());

		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getFamilyMembers(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean
					.setFamilyMembersList(session.createCriteria(FamilyBean.class).add(Expression.eq(CPSConstants.SFID, leaveBean.getSfID())).add(Expression.eq(CPSConstants.STATUS, 1)).add(
							Restrictions.or(Restrictions.eq(CPSConstants.RELATIONID, CPSConstants.SONID), Restrictions.eq(CPSConstants.RELATIONID, CPSConstants.DAUGHTERID)))
							.addOrder(Order.asc("dob")).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getAppliedLeaves(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();
			query.append("select ltm.code leaveCode, lrd.sfid sfID, lrd.status status, case when lrd.convert_ref_id is null then 'N' else to_char(lrd.convert_ref_id) end convertRefID, "
					+ "case when lrd.leave_sub_type_id is null then ltm.leave_type else (select scl.leave_sub_type from special_casual_leaves scl "
					+ "where lrd.leave_sub_type_id = scl.id) end leaveType, case when lrd.other_remarks is not null then (case when lrd.other_remarks like '%proceed%' then to_char(lrd.from_date - lrd.prev_holidays, 'DD-Mon-YYYY') else to_char(lrd.from_date, 'DD-Mon-YYYY') end) else to_char(lrd.from_date, 'DD-Mon-YYYY') end fromDate, case when lrd.other_remarks is not null then (case when lrd.other_remarks like '%proceed%' then to_char(lrd.to_date + lrd.next_holidays, 'DD-Mon-YYYY') else to_char(lrd.to_date, 'DD-Mon-YYYY') end) else to_char(lrd.to_date, 'DD-Mon-YYYY') end toDate, "
					+ "case when (lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) = .5 then '0.5' when lrd.other_remarks like '%proceed%' then to_char(lrd.debit_leaves) else lrd.no_of_days || '' end || ' (' ||(select code from leave_type_master "
					+ "where id = lrd.debit_from) || ')' noOfDays, lrd.request_id requestID, sm.status statusMsg, ltd.leave_conversion leaveConversion, "
					+ "ltd.leave_cancellation leaveCancellation, (select name_in_service_book from emp_master where sfid = lrd.sfid) name, (select name from designation_master "
					+ "where id = lrd.designation_id) designationName, (select count(count(request_stage)) from request_workflow_history where "
					+ "request_id = lrd.request_id group by request_stage) requestStage, (select max(id) from request_workflow_history where request_id = lrd.request_id) historyID, lrd.other_remarks otherRemarks "
					+ "from leave_type_master ltm, leave_type_master ltm1, leave_type_details ltd, status_master sm, leave_request_details lrd, dopart_ii_type_desig_master dtdm, "
					+ "dopart_ii_type_master dtm, designation_master dm where ltm.id = ltd.leave_type_id and ltm.status = 1 and dtm.status = 1 "
					+ "and ltd.status = 1 and lrd.leave_type_id = ltm.id and lrd.status = sm.id and dtdm.type_id = dtm.id and dtdm.status = 1 and lrd.designation_id = dtdm.designation_id "
					+ "and ltm1.status = 1 and ltm1.id = lrd.debit_from AND lrd.designation_id = dm.id");

			if (!CPSUtils.isNullOrEmpty(leaveBean.getSfID())) {
				query.append(" and lrd.sfid = '" + leaveBean.getSfID() + "' and lrd.status not in (" + CPSConstants.STATUSDECLINED + "," + CPSConstants.STATUSCANCELLED + ","
						+ CPSConstants.STATUSCONVERTED + ") ");
			} else { // Added by Naresh
				query.append(" and lrd.status in (" + CPSConstants.STATUSSANCTIONED + "," + CPSConstants.STATUSCANCELLED +") AND (lrd.id IN (select reference_id "
						+ "from leave_cancellation_details where status = 29) OR lrd.status IN (29)) and ltd.do_part_flag = 'Y'");
			}

			if (!CPSUtils.isNullOrEmpty(leaveBean.getFromDate())) {
				query.append(" and lrd.to_date >= '" + leaveBean.getFromDate() + "'");
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
				query.append(" and lrd.from_date <= '" + leaveBean.getToDate() + "'");
			}
			/*
			 * commented  as requirement changed  should not be enable again
			 * if (!CPSUtils.isNullOrEmpty(leaveBean.getDoPartDate()) && (Integer.parseInt(leaveBean.getGazettedType()) != 1) ) { // Narayana
			
				query.append(" and lrd.from_date <= '" + leaveBean.getDoPartDate() + "'");
			}*/
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
				if (leaveBean.getGazettedType().equals("G")) {
					leaveBean.setGazettedType("GAZETTED");
				} else if (leaveBean.getGazettedType().equals("NG")) {
					leaveBean.setGazettedType("NON GAZETTED");
				}*/
			if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
				query.append(" and dtm.id=" + leaveBean.getGazettedType() +"");
			}
			/*if(CPSUtils.compareStrings(leaveBean.getType(),"dateWiseLeaveList")){
				query.append(" and lrd.from_date <= '" + leaveBean.getDoPartDate() + "'");
			}
			}*/
			if (CPSUtils.compareStrings(leaveBean.getType(), CPSConstants.ADMIN)) {
				query.append(" and ltd.do_part_flag='Y' and lrd.status not in(" + CPSConstants.STATUSCOMPLETED + ") ");
			} else {
				//query.append(" and add_months(lrd.from_date,6) > '" + CPSUtils.getCurrentDate() + "'");
			}
			query.append(" order by lrd.sfid, lrd.from_date desc");
			leaveBean.setAppliedLeavesList(session.createSQLQuery(query.toString()).addScalar("leaveCode").addScalar("status", Hibernate.INTEGER)
					.addScalar("convertRefID").addScalar("sfID").addScalar("leaveType").addScalar("fromDate").addScalar("toDate").addScalar("requestID", Hibernate.STRING)
					.addScalar("noOfDays", Hibernate.STRING).addScalar("statusMsg").addScalar("leaveConversion").addScalar("leaveCancellation").addScalar("name")
					.addScalar("designationName").addScalar("requestStage", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("otherRemarks", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(LeaveRequestBean.class)).list());
			/**
			 * For getting the cancelled leaves of completed requests
			 */
			if (CPSUtils.isNullOrEmpty(leaveBean.getParam()) && (!CPSUtils.isNullOrEmpty(leaveBean.getType())) &&  leaveBean.getType().equals("dateWiseLeaveList")) {
				StringBuilder query1 = new StringBuilder();
				query1.append("SELECT ltm.code leaveCode, lrd.sfid sfID, lrd.status status, CASE WHEN lrd.convert_ref_id IS NULL THEN 'N' ELSE TO_CHAR(lrd.convert_ref_id) "
						+ "END convertRefID, CASE WHEN lrd.leave_sub_type_id IS NULL THEN ltm.leave_type ELSE (SELECT leave_sub_type FROM special_casual_leaves "
						+ "WHERE id = lrd.leave_sub_type_id) END leaveType, TO_CHAR(lrd.from_date, 'DD-Mon-YYYY') fromDate, TO_CHAR(lrd.to_date, 'DD-Mon-YYYY') toDate, "
						+ "CASE WHEN (lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) = .5 THEN '0.5' ELSE (lrd.debit_leaves + lrd.prev_holidays + "
						+ "lrd.next_holidays) || '' END || ' (' || ltm.code || ')' noOfDays, lrd.request_id requestID, 'SANCTIONED' statusMsg, ltd.leave_conversion leaveConversion, "
						+ "ltd.leave_cancellation leaveCancellation, (SELECT name_in_service_book FROM emp_master WHERE sfid = lrd.sfid) name, "
						+ "(SELECT name FROM designation_master WHERE id = lrd.designation_id) designationName, (SELECT COUNT(COUNT(request_stage)) FROM "
						+ "request_workflow_history WHERE request_id = lrd.request_id GROUP BY request_stage) requestStage, (SELECT MAX(id) FROM request_workflow_history "
						+ "WHERE request_id = lrd.request_id) historyID FROM leave_request_details lrd, leave_type_master ltm, leave_type_details ltd, "
						+ "dopart_ii_type_desig_master dtdm, dopart_ii_type_master dtm WHERE lrd.status IN(9) AND lrd.do_part_id  IS NULL AND lrd.id IN "
						+ "(SELECT reference_id FROM leave_cancellation_details WHERE status NOT IN (6, 9)) and ltm.id = lrd.leave_type_id and ltd.leave_type_id = ltm.id "
						+ "and ltm.status = 1 and ltd.status = 1 AND dtdm.type_id = dtm.id AND lrd.designation_id = dtdm.designation_id and dtdm.status = 1 "
						+ "and dtm.status = 1 and ltd.do_part_flag = 'Y'");

				if (!CPSUtils.isNullOrEmpty(leaveBean.getSfID())) {
					query1.append(" and lrd.sfid = '" + leaveBean.getSfID() + "'");
				}
				if (!CPSUtils.isNullOrEmpty(leaveBean.getFromDate())) {
					query1.append(" and lrd.to_date >= '" + leaveBean.getFromDate() + "'");
				}
				if (!CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
					query1.append(" and lrd.from_date <= '" + leaveBean.getToDate() + "'");
				}
				/*
				 * commented  as requirement changed  should not be enable again
				 * if ((!CPSUtils.isNullOrEmpty(leaveBean.getDoPartDate())) && (Integer.parseInt(leaveBean.getGazettedType()) != 1)) {
					query1.append(" and lrd.from_date <= '" + leaveBean.getDoPartDate() + "'");
				}*/
				if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
					query1.append(" and dtm.id = " + leaveBean.getGazettedType() +"");
				}
				query1.append(" order by lrd.sfid,lrd.from_date desc");
				leaveBean.getAppliedLeavesList().addAll(session.createSQLQuery(query1.toString()).addScalar("leaveCode").addScalar("status", Hibernate.INTEGER)
						.addScalar("convertRefID").addScalar("sfID").addScalar("leaveType").addScalar("fromDate").addScalar("toDate").addScalar("requestID", Hibernate.STRING)
						.addScalar("noOfDays", Hibernate.STRING).addScalar("statusMsg", Hibernate.STRING).addScalar("leaveConversion").addScalar("leaveCancellation")
						.addScalar("name").addScalar("designationName").addScalar("requestStage", Hibernate.STRING).addScalar("historyID", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(LeaveRequestBean.class)).list());
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getCancelledLeaves(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();
			query.append("select lcd.request_id requestID, lcd.sfid sfID, emp.name_in_service_book name, lrd.no_of_days || '(' || ltm.code || ')' noOfDays, "
					+ "to_char(lrd.from_date,'dd-Mon-YYYY') fromDate, to_char(lrd.to_date, 'dd-Mon-YYYY') toDate, desig.name designationName, ltm.leave_type leaveType, "
					+ "sm.status statusMsg from leave_cancellation_details lcd, emp_master emp, leave_request_details lrd, dopart_ii_type_desig_master dtdm, "
					+ "dopart_ii_type_master dtm, designation_master desig, leave_type_master ltm, leave_type_details ltd, status_master sm where dtm.id = dtdm.type_id "
					+ "and dtdm.designation_id = lrd.designation_id and lcd.status = " + CPSConstants.STATUSSANCTIONED + " and lcd.sfid = emp.sfid "
					+ "and emp.status = 1 and lrd.id = lcd.reference_id and desig.id = emp.designation_id and ltm.id = lrd.leave_type_id "
					+ "and dtm.status = 1 and ltd.status = 1 and ltd.leave_type_id = ltm.id and ltd.do_part_flag = 'Y' and sm.id = lcd.status");

			if (!CPSUtils.isNullOrEmpty(leaveBean.getFromDate())) {
				query.append(" and lcd.REQUESTED_DATE between '" + leaveBean.getFromDate() + "'");
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
				query.append(" and '" + leaveBean.getToDate() + "'");
			}
			/*
			 * 
			 * commented  as requirement changed  should not be enable again
			 * if ((!CPSUtils.isNullOrEmpty(leaveBean.getDoPartDate())) && (Integer.parseInt(leaveBean.getGazettedType()) != 1)) { //Narayana
				query.append(" and lrd.from_date <= '" + leaveBean.getDoPartDate() + "'");
			}*/
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
				if (leaveBean.getGazettedType().equals("G")) {
					leaveBean.setGazettedType("GAZETTED");
				} else if (leaveBean.getGazettedType().equals("NG")) {
					leaveBean.setGazettedType("NON GAZETTED");
				}*/
			if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
				query.append(" and dtm.id = " + leaveBean.getGazettedType() + "");
			}
			/*if(CPSUtils.compareStrings(leaveBean.getType(),"dateWiseLeaveList")){
				query.append(" and lrd.from_date <= '" + leaveBean.getDoPartDate() + "'");
			}
			}*/
			query.append(" order by lcd.requested_date");
			leaveBean.setCancelledLeavesList(session.createSQLQuery(query.toString()).addScalar("sfID").addScalar("leaveType").addScalar("noOfDays", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.STRING).addScalar("toDate", Hibernate.STRING).addScalar("requestID", Hibernate.STRING).addScalar("name")
					.addScalar("designationName").addScalar("statusMsg").setResultTransformer(Transformers.aliasToBean(LeaveRequestBean.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getConvertedLeaves(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			StringBuilder query = new StringBuilder();
			query.append("select max(lcd.id), lcd.request_id requestID, lcd.no_of_days || '(' || ltm.code || ')' noOfDays, to_char(lcd.from_date, 'dd-Mon-YYYY') fromDate, "
					+ "to_char(lcd.to_date, 'dd-Mon-YYYY') toDate, lcd.sfid sfID, emp.name_in_service_book name, desig.name designationName, ltm.leave_type leaveType, "
					+ "sm.status statusMsg from leave_conversion_details lcd, emp_master emp, leave_request_details lrd, dopart_ii_type_desig_master dtdm, "
					+ "dopart_ii_type_master dtm, designation_master desig, leave_type_master ltm, leave_type_details ltd, status_master sm where dtm.id = dtdm.type_id "
					+ "and dtdm.designation_id = lrd.designation_id and lcd.status = " + CPSConstants.STATUSSANCTIONED + " and lcd.sfid = emp.sfid "
					+ "and emp.status = 1 and lrd.id = lcd.reference_id and desig.id = emp.designation_id and ltm.id = lrd.leave_type_id "
					+ "and ltd.status = 1 and ltd.do_part_flag = 'Y' and ltd.leave_type_id = ltm.id and dtm.status = 1 and sm.id = lcd.status");
			if (!CPSUtils.isNullOrEmpty(leaveBean.getFromDate())) {
				query.append(" and lcd.to_date >= '" + leaveBean.getFromDate() + "'");
			}
			if (!CPSUtils.isNullOrEmpty(leaveBean.getToDate())) {
				query.append(" and lcd.from_date <= '" + leaveBean.getToDate() + "'");
			}
			/*
			 * commented  as requirement changed  should not be enable again
			 * if ((!CPSUtils.isNullOrEmpty(leaveBean.getDoPartDate())) && (Integer.parseInt(leaveBean.getGazettedType()) != 1)) { //Narayana and modified by rajendra
				query.append(" and lcd.from_date <= '" + leaveBean.getDoPartDate() + "'");
			}*/
			/*if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
				if (leaveBean.getGazettedType().equals("G")) {
					leaveBean.setGazettedType("GAZETTED");
				} else if (leaveBean.getGazettedType().equals("NG")) {
					leaveBean.setGazettedType("NON GAZETTED");
				}*/
			if (!CPSUtils.isNullOrEmpty(leaveBean.getGazettedType())) {
				query.append(" and dtm.id = " + leaveBean.getGazettedType() + "");
			}
			/*if(CPSUtils.compareStrings(leaveBean.getType(),"dateWiseLeaveList")){
				query.append(" and lrd.from_date <= '" + leaveBean.getDoPartDate() + "'");
			}
			}*/
			query.append(" group by lcd.request_id, lcd.sfid, emp.name_in_service_book, desig.name, ltm.leave_type, sm.status, lcd.from_date, lcd.to_date, lcd.no_of_days, ltm.code");
			leaveBean.setConvertedLeavesList(session.createSQLQuery(query.toString()).addScalar("sfID").addScalar("leaveType").addScalar("noOfDays", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.STRING).addScalar("toDate", Hibernate.STRING).addScalar("requestID", Hibernate.STRING).addScalar("name")
					.addScalar("designationName").addScalar("statusMsg").setResultTransformer(Transformers.aliasToBean(LeaveRequestBean.class)).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getFamilyImpactDetails(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setLeaveFamilyImpactDetails(session.createCriteria(LeaveFamilyDTO.class).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	public LeaveApplicationBean getHolidays(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = checkHolidays.checkHolidays(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AddressBean> getAddressDetails(final String sfid) throws Exception {
		Session session = null;
		List<AddressBean> addressList = null;
		try {
			session = hibernateUtils.getSession();
			addressList = session
					.createSQLQuery(
							"select adm.address_type_id addressTypeId,adm.address1 address1,adm.address2 address2,adm.address3 address3,adm.city city,dm.name district,sm.name state,adm.pincode pincode,adm.phone_number phone,"
									+ "adm.mobile_number mobile,adm.email email,adm.nearest_airport nearestAirport,adm.nearest_rly_stn nearestRyStation from address_details_master adm,state_master sm,district_master dm where adm.status=1 "
									+ "and adm.state=sm.id and adm.district=dm.id and adm.sfid=?").addScalar("address1", Hibernate.STRING).addScalar("address1", Hibernate.STRING).addScalar(
							"address2", Hibernate.STRING).addScalar("address3", Hibernate.STRING).addScalar("addressTypeId", Hibernate.STRING).addScalar("city", Hibernate.STRING).addScalar(
							"district", Hibernate.STRING).addScalar("state", Hibernate.STRING).addScalar("pincode", Hibernate.STRING).addScalar("phone", Hibernate.STRING).addScalar("email",
							Hibernate.STRING).addScalar("mobile", Hibernate.STRING).addScalar("nearestRyStation", Hibernate.STRING).addScalar("nearestAirport", Hibernate.STRING).setResultTransformer(
							Transformers.aliasToBean(AddressBean.class)).setString(0, sfid).list();

		} catch (Exception e) {
			throw e;
		}
		System.out.println("Adress list::----------------::  "+addressList);
		return addressList;
		
	}

	@Override
	public LeaveApplicationBean getNoOfDays(LeaveApplicationBean leaveBean) throws Exception {
		try {
			if (CPSUtils.compareStrings(CPSConstants.EL, leaveBean.getLeaveType())) {
				if (CPSUtils.isNullOrEmpty(leaveBean.getPrevHolidays())) {
					leaveBean.setPrevHolidays("0");
				}
				if (CPSUtils.isNullOrEmpty(leaveBean.getNextHolidays())) {
					leaveBean.setNextHolidays("0");
				}
				leaveBean.setCurrentFutureYearDays(numberOfLeaves.getCurrentNextSpellDays(leaveBean));
				leaveBean.setFutureAvailableLeaves(numberOfLeaves.getFutureLeaves(leaveBean));
			} else if (CPSUtils.compareStrings(CPSConstants.CL, leaveBean.getLeaveType())) {
				leaveBean.setResult(numberOfLeaves.getNoOfDays(leaveBean));
				leaveBean.setCurrentFutureYearDays(numberOfLeaves.getCurrentNextYearDays(leaveBean));
				leaveBean.setFutureAvailableLeaves(numberOfLeaves.getFutureLeaves(leaveBean));
			} else if (CPSUtils.compareStrings(CPSConstants.SCL, leaveBean.getLeaveType())) {
				leaveBean.setResult(numberOfLeaves.getNoOfDays(leaveBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LeaveExceptionDetailsDTO> getExceptionDetails() throws Exception {
		List<LeaveExceptionDetailsDTO> exceptionalList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			exceptionalList = session.createCriteria(LeaveExceptionDetailsDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		}
		return exceptionalList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvailableLeavesDTO> getLeaveCreditsList(final String sfid) throws Exception {
		List<AvailableLeavesDTO> availLeaveCredits = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			availLeaveCredits = session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", sfid)).list();
		} catch (Exception e) {
			throw e;
		}
		return availLeaveCredits;
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getOfficeIDAndPhone(final String sfid) throws Exception {
		Session session = null;
		HashMap<String, String> hmap = null;
		try {
			session = hibernateUtils.getSession();
			hmap = (HashMap<String, String>) session
					.createSQLQuery(
							"select dept.department_id||'' department,emp.internal_phone_no,emp.designation_id||'' designation from emp_master emp,org_role_instance ori,departments_master dept where emp.status=1 and dept.status=1 and ori.status=1 and emp.office_id=ori.org_role_id and ori.department_id=dept.department_id and emp.sfid=?")
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setString(0, sfid).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return hmap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getLeaveDetails(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		LeaveRequestBean lrb = null;
		String type = null;
		try {
			type = leaveBean.getType();
			session = hibernateUtils.getSession();
			lrb = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, leaveBean.getRequestID())).uniqueResult();
			BeanUtils.copyProperties(lrb, leaveBean);
			if (!CPSUtils.isNullOrEmpty(lrb.getOtherRemarks()) && leaveBean.getOtherRemarks().contains(CPSConstants.PROCEED)) {
				leaveBean.setFromDate(CPSUtils.addDays(lrb.getStrFromDate(), String.valueOf(-Integer.parseInt(leaveBean.getPrevHolidays()))));
				leaveBean.setToDate(CPSUtils.addDays(lrb.getStrToDate(), leaveBean.getNextHolidays()));
			}
			leaveBean.setType(type);
			leaveBean.setRequestExceptionList(session.createCriteria(LeaveRequestExceptionsDTO.class).add(Expression.eq("referenceID", Integer.valueOf(leaveBean.getRequestID()))).list());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	public LeaveApplicationBean checkRequests(LeaveApplicationBean leaveBean) throws Exception {
		// SQL Queries
		final String sql = "select count(*) from (select request_id from leave_cancellation_details where reference_id = (select id from leave_request_details "
				+ "where request_id = ?) and status not in (?, ?, ?) union select request_id from leave_conversion_details where reference_id = "
				+ "(select id from leave_request_details where request_id = ?) and status not in (?, ?, ?))";
		// Code
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(session.createSQLQuery(sql).setString(0, leaveBean.getRequestID()).setString(1, CPSConstants.STATUSCANCELLED).setString(2, CPSConstants.STATUSDECLINED)
					.setString(3, CPSConstants.STATUSCONVERTED).setString(4, leaveBean.getRequestID()).setString(5, CPSConstants.STATUSCANCELLED).setString(6, CPSConstants.STATUSDECLINED).setString(
							7, CPSConstants.STATUSCONVERTED).uniqueResult().toString(), "0")) {
				leaveBean.setResult(CPSConstants.SUCCESS);
			} else {
				leaveBean.setResult(CPSConstants.FAILED);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getLeaveBalaceDetails(LeaveApplicationBean leaveBean) throws Exception {
		// SQL Queries
		final String sql = "select ltm.id, al.sfid sfID, ltm.leave_type leaveType, case when ltd.max_shown_flag = 'Y' then al.available_leaves ||'' "
				+ "when ltd.max_shown_flag = 'N' then (case when ltd.max_leaves >= al.available_leaves then al.available_leaves ||'' "
				+ "else ltd.max_leaves || '+' || (al.available_leaves - ltd.max_leaves) end) else al.available_leaves ||'' end leaves from available_leaves al, "
				+ "leave_type_master ltm, leave_type_details ltd where al.leave_type_id = ltm.id and ltm.id = ltd.leave_type_id and al.sfid = ? "
				+ "and ltd.view_flag = 'Y' and ltm.status = 1 and ltd.status = 1 order by ltm.order_no";
		// Code
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setLeaveBalanceList(session.createSQLQuery(sql).addScalar("sfID").addScalar("leaveType").addScalar("id", Hibernate.INTEGER)
					.addScalar("leaves").setResultTransformer(Transformers.aliasToBean(AvailableLeavesDTO.class)).setString(0,leaveBean.getSfID()).list());

			// CCL is available for married female employees or Adopting child case also
			if (!checkCCLAvailability(leaveBean.getSfID())) {
				List<AvailableLeavesDTO> list = leaveBean.getLeaveBalanceList();
				for (AvailableLeavesDTO availDTO : list) {
					if (availDTO.getId() == Integer.parseInt(CPSConstants.CCL)) {
						list.remove(availDTO);
						break;
					}
				}
				leaveBean.setLeaveBalanceList(list);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getConfiguredConvertLeaves(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		List<AvailableLeavesDTO> availableLeaves = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setConversionMappingDetails(session.createSQLQuery("select ltm2.id id,ltm2.leave_type leaveType,(CASE WHEN LTM2.ID=4 THEN AL.AVAILABLE_LEAVES/2 "
					+ "ELSE AL.AVAILABLE_LEAVES END) availableLeaves,(CASE WHEN LTM2.ID=4 THEN AL.AVAILABLE_LEAVES/2 ELSE AL.AVAILABLE_LEAVES END) presentLeaves,"
					+ "lcm.no_of_days mapDays from leave_conversion_mapping lcm,leave_type_master ltm1,leave_type_master ltm2,available_leaves al where lcm.status=1 "
					+ "and lcm.converted_from=? and ltm1.status=1 and ltm2.status=1 and ltm1.id=lcm.converted_from and ltm2.id=lcm.converted_to and "
					+ "al.leave_type_id=(case when lcm.converted_to=4 then 2 else LCM.CONVERTED_TO end) and al.sfid=?").addScalar("leaveType")
					.addScalar("presentLeaves", Hibernate.STRING).addScalar("availableLeaves", Hibernate.FLOAT).addScalar("mapDays", Hibernate.FLOAT)
					.addScalar("id", Hibernate.INTEGER).setString(0, leaveBean.getLeaveTypeID()).setString(1, leaveBean.getSfID())
					.setResultTransformer(Transformers.aliasToBean(AvailableLeavesDTO.class)).list());
			Iterator<AvailableLeavesDTO> it = leaveBean.getConversionMappingDetails().iterator();
			availableLeaves = new ArrayList<AvailableLeavesDTO>();
			while (it.hasNext()) {
				AvailableLeavesDTO availableLeavesDTO = it.next();
				availableLeavesDTO.setLeaveDetails((LeaveManagementBean)session.createCriteria(LeaveManagementBean.class).add(Expression.eq("leaveTypeId", String.valueOf(availableLeavesDTO.getId()))).uniqueResult());
				availableLeaves.add(availableLeavesDTO);
			}
			leaveBean.setConversionMappingDetails(availableLeaves);

		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	private LeaveManagementBean getLeaveTypeDetails(final String requestID) throws Exception {
		Session session = null;
		LeaveManagementBean leaveBean = null;
		LeaveRequestBean leaveReqBean = null;
		try {
			session = hibernateUtils.getSession();

			leaveReqBean = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestID)).uniqueResult();
			leaveBean = (LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("leaveTypeId", leaveReqBean.getLeaveTypeID())).add(
					Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			leaveBean.setLeaveRequestDetails(leaveReqBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	public LeaveApplicationBean checkCancelLeaveConstraints(LeaveApplicationBean leaveAppBean) throws Exception {
		LeaveManagementBean leaveTypeDetails = null;
		try {
			leaveTypeDetails = getLeaveTypeDetails(leaveAppBean.getRequestID()); // First get the conversion possibility flag
			if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveCancellation(), "S")) { // Service book entry
				if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveRequestDetails().getServiceBookFlag(), CPSConstants.Y)) {
					// Already service book entry completed, so this leave cannot be cancelled
					leaveAppBean.setRemarks(CPSConstants.CANCELSERVICEBOOKENTRYCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}
			} else if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveCancellation(), "P")) { // Publish in DO part
				if (!CPSUtils.isNull(leaveTypeDetails.getLeaveRequestDetails().getDoPartDetails())) {
					// Already published in DO part, so this leave cannot be cancelled
					leaveAppBean.setRemarks(CPSConstants.CANCELPUBLISHEDDOPARTCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}	
			} else if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveCancellation(), "M")) { // One month from the date of completion of leave
				if (Math.abs(Float.parseFloat(CPSUtils.daysDifference(leaveTypeDetails.getLeaveRequestDetails().getStrToDate(), CPSUtils.getCurrentDate()))) > 30) {
					// One month completed, so this leave cannot be cancelled
					leaveAppBean.setRemarks(CPSConstants.CANCELONEMONTHCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}
				/*if (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(CPSUtils.addNoOfMonths(leaveTypeDetails.getLeaveRequestDetails().getStrToDate(), String.valueOf(1))), CPSUtils.convertStringToDate(CPSUtils.getCurrentDate())) ==  1 ||
						CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(CPSUtils.addNoOfMonths(CPSUtils.getCurrentDate(), String.valueOf(-1))), CPSUtils.convertStringToDate(leaveTypeDetails.getLeaveRequestDetails().getStrToDate())) ==  1) {
					// One month completed, so this leave cannot be cancelled
					leaveAppBean.setRemarks(CPSConstants.CANCELONEMONTHCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}*/
			} /*else if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveCancellation(), "Y")) {
				if (Float.parseFloat(CPSUtils.daysDifference(CPSUtils.formatDate(leaveTypeDetails.getLeaveRequestDetails().getRequestedDate()), CPSUtils.getCurrentDate())) > 30) {
					// One month completed, so this leave cannot be cancelled
					leaveAppBean.setRemarks(CPSConstants.CANCELONEMONTHCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}
			}*/
		} catch (Exception e) {
			throw e;
		}
		return leaveAppBean;
	}

	@Override
	public LeaveApplicationBean checkConvertLeaveConstraints(LeaveApplicationBean leaveAppBean) throws Exception {
		JSONArray convertLeaves = null;
		JSONObject leave = null;
		String lastLeaveTypeID = null;
		ArrayList<String> leavesMap = new ArrayList<String>();
		LeaveManagementBean leaveTypeDetails = null;
		try {
			leaveTypeDetails = getLeaveTypeDetails(leaveAppBean.getRequestID()); // First get the conversion possibility flag
			if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveConversion(), "S")) { // service book entry
				if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveRequestDetails().getServiceBookFlag(), CPSConstants.Y)) {
					// Already service book entry completed, so this leave cannot be converted
					leaveAppBean.setRemarks(CPSConstants.CONVERTSERVICEBOOKENTRYCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}
			} else if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveConversion(), "P")) { // publish in DO part
				if (!CPSUtils.isNull(leaveTypeDetails.getLeaveRequestDetails().getDoPartDetails())) {
					// Already published in DO part, so this leave cannot be converted
					leaveAppBean.setRemarks(CPSConstants.CONVERTPUBLISHEDDOPARTCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}
			} else if (CPSUtils.compareStrings(leaveTypeDetails.getLeaveConversion(), "M")) { // One month from the date of completion of leave
				if (Math.abs(Float.parseFloat(CPSUtils.daysDifference(leaveTypeDetails.getLeaveRequestDetails().getStrToDate(), CPSUtils.getCurrentDate()))) > 30) {
					// One month completed, so this leave cannot be converted
					leaveAppBean.setRemarks(CPSConstants.CONVERTONEMONTHCOMPLETED);
					leaveAppBean.setResult(CPSConstants.FAILED);
				}
			}

			if (!CPSUtils.compareStrings(leaveAppBean.getResult(), CPSConstants.FAILED)) { // leave conversion possible
				// check mappings
				convertLeaves = (JSONArray) JSONSerializer.toJSON(leaveAppBean.getConvertLeaves());
				for (int i = 0; i < convertLeaves.size(); i++) {
					leave = (JSONObject) convertLeaves.get(i);
					if (i == 0) { // check the previous leave
						LeaveApplicationBean leavePrevBean = checkLeaveMapping.checkOtherLeaveMapping(leave.get("fromDate").toString(), leaveAppBean.getSfID(), 
								leave.get("leaveTypeId").toString(), CPSConstants.PREVIOUS);
						leaveAppBean.setRemarks(commonConstraints.setRemarkDetails(leaveAppBean.getRemarks(), leavePrevBean.getRemarks()));
						leaveAppBean.setPrevHolidays(String.valueOf(leavePrevBean.getHolidays()));
					} else if (i == convertLeaves.size() - 1) { // check next leave
						LeaveApplicationBean leaveNextBean = checkLeaveMapping.checkOtherLeaveMapping(leave.get("toDate").toString(), leaveAppBean.getSfID(), leave.get("leaveTypeId").toString(),
								CPSConstants.NEXT);
						leaveAppBean.setRemarks(commonConstraints.setRemarkDetails(leaveAppBean.getRemarks(), leaveNextBean.getRemarks()));
						leaveAppBean.setNextHolidays(String.valueOf(leaveNextBean.getHolidays()));
					}
					if (i != 0) {
						leavesMap.add(lastLeaveTypeID + "#" + leave.get("leaveTypeId").toString());
						lastLeaveTypeID = leave.get("leaveTypeId").toString();
					} else {
						lastLeaveTypeID = leave.get("leaveTypeId").toString();
					}
				}
				if (CPSUtils.checkList(leavesMap)) {
					leaveAppBean.setRemarks(commonConstraints.setRemarkDetails(checkLeaveMapping.checkMappingArray(leavesMap), leaveAppBean.getRemarks()));
				}
				if (!CPSUtils.isNullOrEmpty(leaveAppBean.getRemarks())) {
					leaveAppBean.setResult(CPSConstants.FAILED);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveAppBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getLeaveCardDetails(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setCasualLeaveCard(getLeaveCard(leaveBean.getSfID(), "'" + CPSConstants.CL + "'", CPSConstants.BALANCE, leaveBean.getCurrentYear(), "'" + CPSConstants.CL + "'", CPSConstants.CL));
			leaveBean.setEarnedLeaveCard(getLeaveCard(leaveBean.getSfID(), "'" + CPSConstants.EL + "','" + CPSConstants.EOLWOMC + "','" + CPSConstants.EOL + "'", CPSConstants.BALANCE, leaveBean
					.getCurrentYear(), "'" + CPSConstants.EL + "'", CPSConstants.EL));
			leaveBean.setHalfPayLeaveCard(getLeaveCard(leaveBean.getSfID(), "'" + CPSConstants.HPL + "','" + CPSConstants.CML + "','" + CPSConstants.LND + "'", CPSConstants.BALANCE, leaveBean
					.getCurrentYear(), "'" + CPSConstants.HPL + "'", CPSConstants.HPL));
			
			// Display CCL when the employee is married or the employee adopted child
			if (checkCCLAvailability(leaveBean.getSfID())) {
				leaveBean.setChildCareLeaveCard(getLeaveCard(leaveBean.getSfID(), "'" + CPSConstants.CCL + "'", CPSConstants.BALANCE, leaveBean.getCurrentYear(), "'" + CPSConstants.CCL + "'", CPSConstants.CCL));
			}
			leaveBean.setSpecialCasualLeaveCard(getLeaveCard(leaveBean.getSfID(), "'" + CPSConstants.SCL + "'", CPSConstants.SCL, leaveBean.getCurrentYear(), null, null));
			leaveBean.setOtherLeaveCard(getLeaveCard(leaveBean.getSfID(), "'" + CPSConstants.ML + "','" + CPSConstants.PL + "','" + CPSConstants.SL + "','" + CPSConstants.PREG + "','"
					+ CPSConstants.ABORTION + "','" + CPSConstants.ADOPT + "'", CPSConstants.NOBALANCE, leaveBean.getCurrentYear(), null, null));
			leaveBean.setYearAvailableLeaves(session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("type", 0)).add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("yearID", Integer.parseInt(getYearId(String.valueOf(Integer.parseInt(leaveBean.getCurrentYear()) - 1))))).list());
		    
			leaveBean.setOnetimeEntryBalance(session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("oneTimeEntryFlag", 1)).add(Expression.sql("TO_CHAR(txn_from_date, 'YYYY') = " + String.valueOf(Integer.parseInt(leaveBean.getCurrentYear())) + "")).list());      
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public boolean checkCCLAvailability(final String sfID) throws Exception {
		// SQL Queries
		final String sql = "select case when emp.gender = 'M' then 'false' when emp.marital_id = ? then 'true' when (select count(*) from family_details fd "
				+ "where fd.sfid = ? and fd.status = 1 and fd.relation_id in (?, ?)) > 0 then 'true' else 'false' end from emp_master emp where emp.sfid = ?";
		// Code
		Session session = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();
			String result = session.createSQLQuery(sql).setString(0, CPSConstants.MARRIEDID).setString(1, sfID).setString(2, CPSConstants.SONID)
					.setString(3, CPSConstants.DAUGHTERID).setString(4, sfID).uniqueResult().toString();
			if (CPSUtils.compareStrings(result, CPSConstants.TRUE)) status = true;
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public List<LeaveCardDTO> getLeaveCard(final String sfID, final String leaveTypeIDs, final String type, final String year, final String avilLeaveID, String leaveId) throws Exception {
		List<LeaveCardDTO> leaveCardArr = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try {
			leaveCardArr = new ArrayList<LeaveCardDTO>();
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(type, CPSConstants.BALANCE)) {
				/*sb
						.append("select noOfDays days,to_char(from_date,'DD-MON-YYYY') fromDate,to_char(to_date,'DD-MON-YYYY') toDate,status,appliedType requestType,leaveType, "
								+ "leave_type_id,case when leave_type_id=6 or leave_type_id=15 then null else SUM( (CASE WHEN rownum=1 THEN (case when to_char(sysdate,'YYYY')=? then (select available_leaves from available_leaves where sfid=? and leave_type_id in ("
								+ avilLeaveID
								+ ")) else (select balance from leave_yearly_balance where id=(select max(id) from leave_yearly_balance where status=1 and sfid=? and year_id=(select id from year_master where name=? and status=1) and type=0 and leave_type_id in ("
								+ avilLeaveID
								+ ")) ) end) WHEN rownum!=1  THEN case when PREV_LeaveID=6 then 0 when PREV_LeaveID=15 then 0 else -1* PREV_days end ELSE 0 END) ) OVER (ORDER BY ROWNUM) end balance from (select leave_type_id,leaveType,noOfDays,from_date,to_date,status,applied_date,"
								+ "appliedType,LAG(noOfDays, 1, 0)  OVER (ORDER BY rownum) AS PREV_days,LAG(leave_type_id, 1, 0)  OVER (ORDER BY rownum) AS PREV_LeaveID from ( select leave_type_id,leaveType,noOfDays,from_date,to_date,status,applied_date,appliedType from (" +
								"select leave_type_id,leaveType,noOfDays,from_date,to_date,status,to_date(applied_date) applied_date,appliedType from ( "
								+ "select ltm.id leave_type_id,ltm.leave_type leaveType,case when ltm.id="
								+ CPSConstants.EOL
								+ " then ltd.no_of_leaves/10 when ltm.id="
								+ CPSConstants.EOLWOMC
								+ " then ltd.no_of_leaves/10 else ltd.no_of_leaves end noOfDays,ltd.txn_from_date from_date,ltd.txn_to_date to_date,'COMPLETED' status,ltd.creation_date applied_date,"
								+ "sm.status appliedType from leave_txn_details ltd,leave_type_master ltm,status_master sm where ltd.sfid=? and ltm.status=1 and ltm.id=ltd.leave_type_id and to_char(ltd.txn_from_date,'YYYY')=? and ltd.one_time_entry_flag=0 "
								+ "and to_char(ltd.txn_from_date,'DD-MON-YYYY')!='01-JAN-2011' and sm.id=ltd.txn_type and ltm.id in ");
				if (CPSUtils.compareStrings(avilLeaveID, "'" + CPSConstants.HPL + "'")) {
					sb.append("(" + avilLeaveID + ")");
				} else {
					sb.append("(" + leaveTypeIDs + ")");
				}
				sb
						.append(" union select ltm1.id leave_type_id,case when ltm1.id=ltm2.id then ltm1.leave_type else ltm1.leave_type||'('||ltm2.leave_type||')' end leaveType,case when lrd.present_period_debit=0 then -(lrd.debit_leaves+lrd.prev_holidays+lrd.next_holidays) else -lrd.present_period_debit end "
								+ "noOfDays,lrd.from_date,lrd.to_date,sm.status,lrd.requested_date applied_date,'Leave Request' appliedType from leave_request_details lrd,leave_type_master ltm1,leave_type_master ltm2,status_master sm where "
								+ "lrd.debit_from=ltm1.id and lrd.convert_ref_id is null and ltm1.status=1 and ltm2.status=1 and lrd.status=sm.id and ltm2.id=lrd.leave_type_id and lrd.sfid=?  and upper(sm.status) not in (upper('cancelled'),upper('declined'),upper('converted')) "
								+ "and lrd.debit_from in ("
								+ leaveTypeIDs
								+ ") and to_char(lrd.from_date,'YYYY')=? union select  ltm1.id leave_type_id,case when ltm1.id=ltm2.id then ltm1.leave_type else ltm1.leave_type||'('||ltm2.leave_type||')' end leaveType,-lcd.debit_leaves noOfDays,"
								+ "lcd.from_date,lcd.to_date,sm.status,lcd.requested_date applied_date,'Leave Conversion' appliedType from leave_conversion_details lcd,leave_type_master ltm1,leave_type_master ltm2,status_master sm where ltm1.status=1 and ltm2.status=1 and "
								+ "ltm1.id=lcd.debit_from and ltm2.id=lcd.converted_to and sm.id=lcd.status and lcd.sfid=? and upper(sm.status) not in (upper('cancelled'),upper('declined')) and lcd.debit_from in ("
								+ leaveTypeIDs + ") and to_char(lcd.from_date,'YYYY')=? )) order by applied_date desc) ) order by applied_date,rownum desc ");

				leaveCardArr = session.createSQLQuery(sb.toString()).addScalar("days", Hibernate.STRING).addScalar("fromDate").addScalar("toDate").addScalar("status").addScalar("requestType")
						.addScalar("leaveType").addScalar("balance", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LeaveCardDTO.class)).setString(0, year).setString(1, sfID)
						.setString(2, sfID).setString(3, year).setString(4, sfID).setString(5, year).setString(6, sfID).setString(7, year).setString(8, sfID).setString(9, year).list();*/

				
				// Added by Narayana, modified by Rajendra and modified by Naresh
				sb.append("SELECT request_id requestId, TO_CHAR(appliedDate, 'DD-MON-YYYY  HH:MI:SS') AS appliedDate, noOfDays days, totalDays, TO_CHAR(from_date, 'DD-MON-YYYY') fromDate, "
						+ "(CASE WHEN TO_CHAR(to_date, 'DD-MON-YYYY') IS NULL THEN TO_CHAR(from_date, 'DD-MON-YYYY') ELSE TO_CHAR(to_date, 'DD-MON-YYYY') END) toDate, "
						+ "status, appliedType requestType, leaveType, leave_type_id, doPartNo,"
						+ "CASE WHEN leave_type_id = 6 OR leave_type_id = 15 THEN NULL ELSE SUM((CASE WHEN rownum = 1 THEN (CASE WHEN TO_CHAR(sysdate, 'YYYY') = ? "
						+ "THEN (SELECT available_leaves FROM available_leaves WHERE sfid = ? AND leave_type_id IN ("+ avilLeaveID +")) "
						+ "ELSE (SELECT balance FROM leave_yearly_balance WHERE id = (SELECT MAX(id) FROM leave_yearly_balance WHERE status = 1 AND sfid = ? "
						+ "AND year_id = (SELECT id FROM year_master WHERE name = ? AND status = 1) AND type = 0 AND leave_type_id IN ("+ avilLeaveID +"))) END) "
						+ "WHEN rownum != 1 THEN CASE WHEN PREV_LeaveID = 6 THEN 0 WHEN PREV_LeaveID = 15 THEN 0 ELSE -1 * PREV_days END ELSE 0 END)) "
						+ "OVER (ORDER BY ROWNUM) END balance FROM (SELECT request_id, leave_type_id, leaveType, noOfDays, to_char(totalDays) as totalDays, "
						+ "from_date, to_date, status, applied_date as appliedDate, doPartNo, appliedType, LEAD(noOfDays, 1, 0) OVER (ORDER BY rownum) AS PREV_days, "
						+ "LEAD(leave_type_id, 1, 0) OVER (ORDER BY rownum) AS PREV_LeaveID FROM (SELECT request_id, leave_type_id, leaveType, noOfDays, totalDays, "
						+ "from_date, to_date, status, applied_date, doPartNo, appliedType FROM (SELECT request_id, leave_type_id, leaveType, "
						+ "noOfDays, totalDays, from_date, to_date, status, applied_date, ");
				if (!CPSUtils.compareStrings(CPSConstants.CL, leaveId)) {
					sb.append("(CASE WHEN to_date(from_date, 'DD-MON-YY HH:MI:SS') < to_date('01-JUL-'||TO_CHAR(from_date, 'YYYY'), 'DD-MM-YY') "
						+ "AND to_date(to_date, 'DD-MON-YY HH:MI:SS') >= to_date('01-JUL-'||TO_CHAR(to_date, 'YYYY'), 'DD-MM-YY') AND status NOT IN (upper('cancelled'), upper('declined')) "
						+ "AND request_id = 'Transaction' THEN to_date(from_date, 'DD-MON-YY') ELSE to_date(applied_date, 'DD-MON-YY HH:MI:SS') END) vapplieddate, ");
				}			
					sb.append("doPartNo, appliedType FROM (SELECT 'Transaction' request_id, ltm.id leave_type_id, ltm.leave_type leaveType, CASE WHEN ltm.id = " + CPSConstants.EOL
						+ " THEN ROUND(ltd.no_of_leaves / 10) WHEN ltm.id = " + CPSConstants.EOLWOMC + " THEN ROUND(ltd.no_of_leaves / 10) WHEN ltm.id = " + CPSConstants.CL + " THEN ltd.no_of_leaves ELSE ROUND(ltd.no_of_leaves) END noOfDays, "
						+ "ABS(ltd.no_of_leaves) totalDays, ltd.txn_from_date from_date, ltd.txn_to_date to_date, 'COMPLETED' status, ltd.creation_date applied_date, '**NO**' doPartNo, " 
						+ "(SELECT CASE WHEN(sm.status = 'Previous Request' OR sm.status = 'Previous Period Leave') THEN sm.status||' - '||ltd.remarks "
						+ "WHEN(SUBSTR(ltd.no_of_leaves, 1, 1) = '-' AND sm.status = 'Credit/Lapse') THEN 'Lapse' WHEN(SUBSTR(ltd.no_of_leaves, 1, 1) != '-' AND sm.status = 'Credit/Lapse') "
						+ "THEN 'Credit' ELSE sm.status END appliedtype FROM dual) AS appliedType FROM leave_txn_details ltd, leave_type_master ltm, status_master sm "
						+ "WHERE ltd.sfid = ? AND ltm.status = 1 And ltd.status = 1 AND ltm.id = ltd.leave_type_id AND TO_CHAR(ltd.txn_from_date, 'YYYY') = ? "
						+ "AND ltd.one_time_entry_flag = 0 AND TO_CHAR(ltd.txn_from_date, 'DD-MON-YYYY') != '01-JAN-2011' AND sm.id = ltd.txn_type AND ltm.id IN ");
				if (CPSUtils.compareStrings(avilLeaveID, "'" + CPSConstants.HPL + "'")) {
					sb.append("(" + avilLeaveID + ")");
				} else {
					sb.append("(" + leaveTypeIDs + ")");
				}
				if (CPSUtils.compareStrings(CPSConstants.CL, leaveId)) {
					sb.append(" UNION SELECT TO_CHAR(lrd.request_id) request_id, ltm1.id leave_type_id, CASE WHEN ltm1.id = ltm2.id THEN ltm1.leave_type "
						+ "ELSE ltm1.leave_type || '(' || ltm2.leave_type || ')' END leaveType, -(lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) noOfDays, "
						+ "lrd.no_of_days totalDays, lrd.from_date, lrd.to_date, sm.status, lrd.requested_date applied_date, (SELECT ref_number FROM reference_number_details "
						+ "WHERE id = lrd.do_part_id) doPartNo, 'Leave Request' appliedType FROM leave_request_details lrd, leave_type_master ltm1, leave_type_master ltm2, "
						+ "status_master sm WHERE lrd.debit_from = ltm1.id AND lrd.convert_ref_id IS NULL AND ltm1.status = 1 AND ltm2.status = 1 AND lrd.status = sm.id "
						+ "AND ltm2.id = lrd.leave_type_id AND lrd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined'), upper('converted')) " 
						+ "AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.from_date, 'YYYY') = ? UNION SELECT TO_CHAR(lrd.request_id) request_id, "
						+ "ltm1.id leave_type_id, CASE WHEN ltm1.id = ltm2.id THEN ltm1.leave_type ELSE ltm1.leave_type || '(' || ltm2.leave_type || ')'  END leaveType, "
						+ "(0) noOfDays, (lrd.no_of_days) totalDays, lrd.from_date, lrd.to_date, sm.status, lrd.requested_date applied_date, (SELECT ref_number "
						+ "FROM reference_number_details WHERE id = lrd.do_part_id) doPartNo, case when sm.status = upper('declined') then 'Declined Leave Request' else 'User Cancelled Leave Request' end appliedType "
						+ "FROM leave_request_details lrd, leave_type_master ltm1, leave_type_master ltm2, status_master sm WHERE lrd.debit_from = ltm1.id "
						+ "AND lrd.convert_ref_id IS NULL AND ltm1.status = 1 AND ltm2.status = 1 AND lrd.status = sm.id AND ltm2.id = lrd.leave_type_id "
						+ "AND lrd.sfid = ? And lrd.id NOT IN (SELECT lcd.reference_id FROM leave_cancellation_details lcd WHERE lcd.requested_by = '"+ sfID +"' AND lcd.status in(8,29)) "
						+ "AND upper(sm.status) IN (upper('cancelled'), upper('declined')) AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.from_date, 'YYYY') = ? "
						+ "UNION SELECT TO_CHAR(lcd.request_id) request_id, ltm1.id leave_type_id, CASE WHEN ltm1.id = ltm2.id THEN ltm1.leave_type "
						+ "ELSE ltm1.leave_type|| '(' || ltm2.leave_type || ')' END leaveType, -(lcd.debit_leaves) noOfDays, lcd.debit_leaves totalDays, " 
						+ "lcd.from_date, lcd.to_date, sm.status, lcd.requested_date applied_date, (SELECT ref_number FROM reference_number_details "
						+ "WHERE id = lcd.do_part_id) doPartNo, 'Leave Conversion' appliedType FROM leave_conversion_details lcd, leave_type_master ltm1, "
						+ "leave_type_master ltm2, status_master sm WHERE ltm1.status = 1 AND ltm2.status = 1 AND ltm1.id = lcd.debit_from AND ltm2.id = lcd.converted_to "
						+ "AND sm.id = lcd.status AND lcd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined')) AND lcd.debit_from "
						+ "IN ("+ leaveTypeIDs +") AND TO_CHAR(lcd.from_date, 'YYYY') = ? UNION SELECT TO_CHAR(lcd.request_id) request_id, ltm1.id leave_type_id, "
						+ "ltm1.leave_type leaveType, CASE WHEN lcd.status = 2 THEN 0 ELSE (lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) END noOfDays, "
						+ "lrd.no_of_days totalDays, lrd.from_date, lrd.to_date, sm.status, lcd.requested_date applied_date, (SELECT ref_number FROM reference_number_details "
						+ "WHERE id = lcd.do_part_id) doPartNo, 'Leave Cancellation' appliedType FROM leave_cancellation_details lcd, leave_request_details lrd, "
						+ "leave_type_master ltm1, status_master sm WHERE ltm1.status = 1 AND ltm1.id = lrd.leave_type_id AND sm.id = lcd.status AND lcd.sfid = lrd.sfid "
						+ "AND lcd.reference_id = lrd.id AND lcd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined')) AND lrd.debit_from "
						+ "IN ("+ leaveTypeIDs +") AND TO_CHAR(lcd.requested_date, 'YYYY') = ? AND LCD.STATUS IN (2, 29, 8) UNION SELECT TO_CHAR(lcd.request_id) request_id, "
						+ "ltm1.id leave_type_id, ltm1.leave_type leaveType, 0 noOfDays, lrd.no_of_days totalDays, lrd.from_date, lrd.to_date, sm.status, lcd.requested_date applied_date, "
						+ "(SELECT ref_number FROM reference_number_details WHERE id = lcd.do_part_id) doPartNo, (SELECT CASE WHEN (sm.status = upper('cancelled')) "
						+ "THEN 'Cancelled Leave Cancellation' WHEN (sm.status = upper('declined')) THEN 'Declined Leave Cancellation' END appliedType FROM dual) AS appliedType " 
						+ "FROM leave_cancellation_details lcd, leave_request_details lrd, leave_type_master ltm1, status_master sm WHERE ltm1.status = 1 "
						+ "AND ltm1.id = lrd.leave_type_id AND sm.id = lcd.status AND lcd.sfid = lrd.sfid AND lcd.reference_id = lrd.id AND lcd.sfid = ? " 
						+ "AND upper(sm.status) IN (upper('cancelled'), upper('declined')) AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.from_date, 'YYYY') = ? "
						+ "AND lcd.status NOT IN (29, 8) UNION SELECT TO_CHAR(lrd.request_id) request_id, ltm1.id leave_type_id, ltm1.leave_type leaveType, "
						+ "-(lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) noOfDays, lrd.no_of_days totalDays, lrd.from_date, lrd.to_date, sm.status, "
						+ "lrd.requested_date applied_date, (SELECT ref_number FROM reference_number_details WHERE id = lrd.do_part_id) doPartNo, 'Cancelled Leave Request' appliedType " 
						+ "FROM leave_cancellation_details lcd, leave_request_details lrd, leave_type_master ltm1, status_master sm WHERE ltm1.status = 1 "
						+ "AND ltm1.id = lrd.leave_type_id AND sm.id = lrd.status AND lcd.sfid = lrd.sfid AND lcd.reference_id = lrd.id AND lcd.status NOT "
						+ "IN (9, 6, 2) AND lcd.sfid = ? AND lrd.status NOT IN (2, 6) AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.from_date, 'YYYY') = ?)) "
						+ "ORDER BY TO_CHAR(applied_date, 'YYMMDD HH:MI:SS') ASC) ORDER BY rownum DESC) ORDER BY rownum DESC");
				} else {
					sb.append(" UNION SELECT TO_CHAR(lrd.request_id) request_id, ltm1.id leave_type_id, CASE WHEN ltm1.id = ltm2.id THEN ltm1.leave_type "
						+ "ELSE ltm1.leave_type || '(' || ltm2.leave_type || ')' END leaveType, -(lrd.debit_leaves) noOfDays, "
						+ "lrd.no_of_days totalDays, case when lrd.other_remarks is not null then (case when lrd.other_remarks like '%proceed%' then (lrd.from_date - lrd.prev_holidays) else lrd.from_date end) else lrd.from_date end, case when lrd.other_remarks is not null then (case when lrd.other_remarks like '%proceed%' then (lrd.to_date + lrd.next_holidays) else lrd.to_date end) else lrd.to_date end, sm.status, lrd.requested_date applied_date, (SELECT ref_number FROM reference_number_details "
						+ "WHERE id = lrd.do_part_id) doPartNo, 'Leave Request' appliedType FROM leave_request_details lrd, leave_type_master ltm1, leave_type_master ltm2, "
						+ "status_master sm WHERE lrd.debit_from = ltm1.id AND lrd.convert_ref_id IS NULL AND ltm1.status = 1 AND ltm2.status = 1 AND lrd.status = sm.id "
						+ "AND ltm2.id = lrd.leave_type_id AND lrd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined'), upper('converted')) " 
						+ "AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.requested_date, 'YYYY') = ? UNION SELECT TO_CHAR(lrd.request_id) request_id, "
						+ "ltm1.id leave_type_id, CASE WHEN ltm1.id = ltm2.id THEN ltm1.leave_type ELSE ltm1.leave_type || '(' || ltm2.leave_type || ')'  END leaveType, "
						+ "case when (select count(1) from leave_pastleaves_declined where lrd_request_id = lrd.request_id) = 0 then (0) else -debit_leaves end noOfDays, (lrd.no_of_days) totalDays, case when lrd.other_remarks is not null then (case when lrd.other_remarks like '%proceed%' then (lrd.from_date - lrd.prev_holidays) else lrd.from_date end) else lrd.from_date end, case when lrd.other_remarks is not null then (case when lrd.other_remarks like '%proceed%' then (lrd.to_date + lrd.next_holidays) else lrd.to_date end) else lrd.to_date end, sm.status, lrd.requested_date applied_date, (SELECT ref_number "
						+ "FROM reference_number_details WHERE id = lrd.do_part_id) doPartNo, case when sm.status = upper('declined') then 'Declined Leave Request' else 'User Cancelled Leave Request' end appliedType "
						+ "FROM leave_request_details lrd, leave_type_master ltm1, leave_type_master ltm2, status_master sm WHERE lrd.debit_from = ltm1.id "
						+ "AND lrd.convert_ref_id IS NULL AND ltm1.status = 1 AND ltm2.status = 1 AND lrd.status = sm.id AND ltm2.id = lrd.leave_type_id "
						+ "AND lrd.sfid = ? And lrd.id NOT IN (SELECT lcd.reference_id FROM leave_cancellation_details lcd WHERE lcd.requested_by = '"+ sfID +"' AND lcd.status in(8,29)) "
						+ "AND upper(sm.status) IN (upper('cancelled'), upper('declined')) AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.requested_date, 'YYYY') = ? "
						+ "UNION SELECT TO_CHAR(lcd.request_id) request_id, ltm1.id leave_type_id, CASE WHEN ltm1.id = ltm2.id THEN ltm1.leave_type "
						+ "ELSE ltm1.leave_type|| '(' || ltm2.leave_type || ')' END leaveType, -(lcd.debit_leaves) noOfDays, lcd.debit_leaves totalDays, " 
						+ "lcd.from_date, lcd.to_date, sm.status, lcd.requested_date applied_date, (SELECT ref_number FROM reference_number_details "
						+ "WHERE id = lcd.do_part_id) doPartNo, 'Leave Conversion' appliedType FROM leave_conversion_details lcd, leave_type_master ltm1, "
						+ "leave_type_master ltm2, status_master sm WHERE ltm1.status = 1 AND ltm2.status = 1 AND ltm1.id = lcd.debit_from AND ltm2.id = lcd.converted_to "
						+ "AND sm.id = lcd.status AND lcd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined')) AND lcd.debit_from "
						+ "IN ("+ leaveTypeIDs +") AND TO_CHAR(lcd.requested_date, 'YYYY') = ? UNION SELECT TO_CHAR(lcd.request_id) request_id, ltm1.id leave_type_id, "
						+ "ltm1.leave_type leaveType, CASE WHEN lcd.status = 2 THEN 0 ELSE (lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) END noOfDays, "
						+ "lrd.no_of_days totalDays, lrd.from_date, lrd.to_date, sm.status, lcd.requested_date applied_date, (SELECT ref_number FROM reference_number_details "
						+ "WHERE id = lcd.do_part_id) doPartNo, 'Leave Cancellation' appliedType FROM leave_cancellation_details lcd, leave_request_details lrd, "
						+ "leave_type_master ltm1, status_master sm WHERE ltm1.status = 1 AND ltm1.id = lrd.leave_type_id AND sm.id = lcd.status AND lcd.sfid = lrd.sfid "
						+ "AND lcd.reference_id = lrd.id AND lcd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined')) AND lrd.debit_from "
						+ "IN ("+ leaveTypeIDs +") AND TO_CHAR(lcd.requested_date, 'YYYY') = ? AND LCD.STATUS IN (2, 29, 8) UNION SELECT TO_CHAR(lcd.request_id) request_id, "
						+ "ltm1.id leave_type_id, ltm1.leave_type leaveType, 0 noOfDays, lrd.no_of_days totalDays, lrd.from_date, lrd.to_date, sm.status, lcd.requested_date applied_date, "
						+ "(SELECT ref_number FROM reference_number_details WHERE id = lcd.do_part_id) doPartNo, (SELECT CASE WHEN (sm.status = upper('cancelled')) "
						+ "THEN 'Cancelled Leave Cancellation' WHEN (sm.status = upper('declined')) THEN 'Declined Leave Cancellation' END appliedType FROM dual) AS appliedType " 
						+ "FROM leave_cancellation_details lcd, leave_request_details lrd, leave_type_master ltm1, status_master sm WHERE ltm1.status = 1 "
						+ "AND ltm1.id = lrd.leave_type_id AND sm.id = lcd.status AND lcd.sfid = lrd.sfid AND lcd.reference_id = lrd.id AND lcd.sfid = ? " 
						+ "AND upper(sm.status) IN (upper('cancelled'), upper('declined')) AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.requested_date, 'YYYY') = ? "
						+ "AND lcd.status NOT IN (29, 8) UNION SELECT TO_CHAR(lrd.request_id) request_id, ltm1.id leave_type_id, ltm1.leave_type leaveType, "
						+ "-(lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) noOfDays, lrd.no_of_days totalDays, lrd.from_date, lrd.to_date, sm.status, "
						+ "lrd.requested_date applied_date, (SELECT ref_number FROM reference_number_details WHERE id = lrd.do_part_id) doPartNo, 'Cancelled Leave Request' appliedType " 
						+ "FROM leave_cancellation_details lcd, leave_request_details lrd, leave_type_master ltm1, status_master sm WHERE ltm1.status = 1 "
						+ "AND ltm1.id = lrd.leave_type_id AND sm.id = lrd.status AND lcd.sfid = lrd.sfid AND lcd.reference_id = lrd.id AND lcd.status NOT "
						+ "IN (9, 6, 2) AND lcd.sfid = ? AND lrd.status NOT IN (2, 6) AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.requested_date, 'YYYY') = ?)) "
						+ "ORDER BY TO_CHAR(vapplieddate, 'YYMMDD') ASC) ORDER BY rownum DESC) ORDER BY rownum DESC");
				}
				leaveCardArr = session.createSQLQuery(sb.toString()).addScalar("requestId", Hibernate.STRING).addScalar("doPartNo", Hibernate.STRING)
						.addScalar("appliedDate", Hibernate.STRING).addScalar("days", Hibernate.STRING).addScalar("fromDate").addScalar("toDate").addScalar("status")
						.addScalar("requestType").addScalar("leaveType").addScalar("totalDays", Hibernate.STRING).addScalar("balance", Hibernate.STRING)
						.setResultTransformer(Transformers.aliasToBean(LeaveCardDTO.class)).setString(0, year).setString(1, sfID).setString(2, sfID)
						.setString(3, year).setString(4, sfID).setString(5, year).setString(6, sfID).setString(7, year).setString(8, sfID).setString(9, year)
						.setString(10, sfID).setString(11, year).setString(12, sfID).setString(13, year).setString(14, sfID).setString(15, year).setString(16, sfID)
						.setString(17, year).list();

			} else if (CPSUtils.compareStrings(type, CPSConstants.SCL)) {
				sb.append("select to_char(lrd.request_id) requestId, (select ref_number from reference_number_details where id = lrd.do_part_id) doPartNo, "
						+ "scl.leave_sub_type leaveType, to_char(lrd.from_date, 'DD-Mon-YYYY') fromDate, to_char(lrd.to_date, 'DD-Mon-YYYY') toDate, "
						+ "-(lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) days, sm.status from leave_request_details lrd, special_casual_leaves scl, status_master sm "
						+ "where scl.status = 1 and upper(sm.status) not in (upper('cancelled'), upper('declined')) and lrd.convert_ref_id is null "
						+ "and lrd.status = sm.id and lrd.leave_type_id = ? and lrd.leave_sub_type_id = scl.id and lrd.sfid = ? and to_char(lrd.from_date, 'YYYY') = ? order by lrd.from_date");

				leaveCardArr = session.createSQLQuery(sb.toString()).addScalar("requestId", Hibernate.STRING).addScalar("doPartNo", Hibernate.STRING)
						.addScalar("days", Hibernate.STRING).addScalar("fromDate").addScalar("toDate").addScalar("status").addScalar("leaveType")
						.setResultTransformer(Transformers.aliasToBean(LeaveCardDTO.class)).setString(0, CPSConstants.SCL).setString(1, sfID).setString(2, year).list();
			} else {
				sb.append("select requestId, doPartNo, leave_type_id, leaveType, noOfDays days, to_char(from_date, 'DD-Mon-YYYY') fromDate, to_char(to_date, 'DD-Mon-YYYY') toDate, "
						+ "status, appliedType requestType from (select to_char(request_id) requestId, (SELECT ref_number FROM reference_number_details "
						+ "WHERE id = lrd.do_part_id) doPartNo, ltm1.id leave_type_id, case when ltm1.id = ltm2.id then ltm1.leave_type else ltm1.leave_type || '(' || ltm2.leave_type || ')' end leaveType, "
						+ "-(lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) noOfDays, lrd.from_date, lrd.to_date, sm.status, lrd.requested_date, "
						+ "'Leave Request' appliedType from leave_request_details lrd, leave_type_master ltm1, leave_type_master ltm2, status_master sm "
						+ "where lrd.debit_from = ltm1.id and lrd.convert_ref_id is null and ltm1.status = 1 and ltm2.status = 1 and lrd.status = sm.id "
						+ "and ltm2.id = lrd.leave_type_id and lrd.sfid = ?  and upper(sm.status) not in (upper('cancelled'), upper('declined'), upper('converted')) "
						+ "and lrd.debit_from in ("+ leaveTypeIDs +") and to_char(lrd.from_date, 'YYYY') = ? union select to_char(request_id) requestId, "
						+ "(SELECT ref_number FROM reference_number_details WHERE id = lcd.do_part_id) doPartNo, ltm1.id leave_type_id, case when ltm1.id = ltm2.id "
						+ "then ltm1.leave_type else ltm1.leave_type || '(' || ltm2.leave_type || ')' end leaveType, -(lcd.debit_leaves) noOfDays, lcd.from_date, "
						+ "lcd.to_date, sm.status, lcd.requested_date, 'Leave Conversion' appliedType from leave_conversion_details lcd, leave_type_master ltm1, "
						+ "leave_type_master ltm2, status_master sm where ltm1.status = 1 and ltm2.status = 1 and ltm1.id = lcd.debit_from and ltm2.id = lcd.converted_to "
						+ "and sm.id = lcd.status and lcd.sfid = ? and upper(sm.status) not in (upper('cancelled'), upper('declined')) and lcd.debit_from in (" + leaveTypeIDs + ") "
						+ "and to_char(lcd.from_date, 'YYYY') = ? UNION SELECT TO_CHAR(lcd.request_id) requestId, (select ref_number from reference_number_details "
						+ "where id = lcd.do_part_id) doPartNo, ltm1.id leave_type_id, ltm1.leave_type leaveType, (lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) noOfDays, "
						+ "lrd.from_date, lrd.to_date, sm.status, lcd.requested_date applied_date, 'Leave Cancellation' appliedType FROM leave_cancellation_details lcd, "
						+ "leave_request_details lrd, leave_type_master ltm1, status_master sm WHERE ltm1.status = 1 AND ltm1.id = lrd.leave_type_id AND sm.id = lcd.status "
						+ "AND lcd.sfid = lrd.sfid AND lcd.reference_id = lrd.id AND lcd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined')) "
						+ "AND lrd.debit_from IN ("+ leaveTypeIDs +") AND TO_CHAR(lrd.from_date, 'YYYY') = ? UNION SELECT TO_CHAR(lrd.request_id) requestId, "
						+ "(select ref_number from reference_number_details where id = lrd.do_part_id) doPartNo, ltm1.id leave_type_id, ltm1.leave_type leaveType, "
						+ "-(lrd.debit_leaves + lrd.prev_holidays + lrd.next_holidays) noOfDays, lrd.from_date, lrd.to_date, sm.status, lrd.requested_date applied_date, "
						+ "'Leave Request' appliedType FROM leave_cancellation_details lcd, leave_request_details lrd, leave_type_master ltm1, status_master sm "
						+ "WHERE ltm1.status = 1 AND ltm1.id = lrd.leave_type_id AND sm.id = lrd.status AND lcd.sfid = lrd.sfid AND lcd.reference_id = lrd.id "
						+ "AND lcd.sfid = ? AND upper(sm.status) NOT IN (upper('cancelled'), upper('declined')) AND lrd.debit_from IN ("+ leaveTypeIDs +") "
						+ "AND TO_CHAR(lrd.from_date, 'YYYY') = ?) order by from_date ASC");

				leaveCardArr = session.createSQLQuery(sb.toString()).addScalar("requestId", Hibernate.STRING).addScalar("doPartNo", Hibernate.STRING)
						.addScalar("days", Hibernate.STRING).addScalar("fromDate").addScalar("toDate").addScalar("status").addScalar("requestType")
						.addScalar("leaveType").setResultTransformer(Transformers.aliasToBean(LeaveCardDTO.class)).setString(0, sfID).setString(1, year)
						.setString(2, sfID).setString(3, year).setString(4, sfID).setString(5, year).setString(6, sfID).setString(7, year).list();
			}
		} catch (Exception e) {
			
			throw e;
		}
		return leaveCardArr;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean getEmployeeDetails(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		HashMap<String, String> hmap = null;
		try {
			session = hibernateUtils.getSession();
			hmap = (HashMap<String, String>) session
					.createSQLQuery(
							"select emp.name_in_service_book empname,to_char(emp.doj_asl) dateOfJoining,dm.type gaztype,desig.name designation,dept.department_name department from emp_master emp,designation_mappings dm,designation_master desig,org_role_instance ori,departments_master dept "
									+ "where emp.designation_id=dm.desig_id and emp.sfid=?  and desig.id=emp.designation_id  and emp.office_id=ori.org_role_id and ori.department_id=dept.department_id")
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setString(0, leaveBean.getSfID()).uniqueResult();
			leaveBean.setEmpName(hmap.get("EMPNAME"));
			leaveBean.setGazType(hmap.get("GAZTYPE"));
			leaveBean.setDesignation(hmap.get("DESIGNATION"));
			leaveBean.setDepartment(hmap.get("DEPARTMENT"));
			leaveBean.setDateOfJoining(hmap.get("DATEOFJOINING"));
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	@Override
	public String getLeaveRequestPkValue(final String requestID) throws Exception {
		Session session = null;
		String referenceID = null;
		try {
			session = hibernateUtils.getSession();
			referenceID = String.valueOf(((LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestID)).uniqueResult()).getId());
		} catch (Exception e) {
			throw e;
		}
		return referenceID;
	}

	@Override
	public LeaveApplicationBean saveOrUpdateSpellsCount(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		EmpLeaveSpellsDTO empLeaveSpells = null;
		try {
			session = hibernateUtils.getSession();

			if (CPSUtils.convertToInteger(leaveBean.getSelectedLeaveDetails().getSpellsPerYear()) > 0 || CPSUtils.convertToInteger(leaveBean.getSelectedLeaveDetails().getSpellsInService()) > 0) {
				// we should count the spells
				empLeaveSpells = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).add(
						Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getSelectedLeaveDetails().getLeaveTypeId()))).uniqueResult();

				if (CPSUtils.isNull(empLeaveSpells)) {
					empLeaveSpells = new EmpLeaveSpellsDTO();
					empLeaveSpells.setSfID(leaveBean.getSfID());
					empLeaveSpells.setLeaveTypeID(Integer.valueOf(leaveBean.getSelectedLeaveDetails().getLeaveTypeId()));
					empLeaveSpells.setYearSpellsCount(1);
					empLeaveSpells.setServiceSpellsCount(1);
				} else {
					if (!CPSUtils.compareStrings(CPSConstants.PROCEED, leaveBean.getType())) {
						empLeaveSpells.setYearSpellsCount(empLeaveSpells.getYearSpellsCount() + 1);
						empLeaveSpells.setServiceSpellsCount(empLeaveSpells.getServiceSpellsCount() + 1);
					}
				}
				session.saveOrUpdate(empLeaveSpells);
				session.flush();
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<YearTypeDTO> getYearsList() throws Exception {
		Session session = null;
		List<YearTypeDTO> yearsList = null;
		try {
			session = hibernateUtils.getSession();
			yearsList = session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.NAME)).list();
		} catch (Exception e) {
			throw e;
		}
		return yearsList;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<YearTypeDTO> getYearsList(final String sfid) throws Exception { // Added by Naresh
		Session session = null;
		List<YearTypeDTO> yearsList = null;
		String joinedYear = null;
		String retirementYear = null;
		try {
			session = hibernateUtils.getSession();
			joinedYear = getASLJoiningDate(sfid).split("-")[2];
			retirementYear = getRetirementDate(sfid).split("-")[2];
			if (Integer.parseInt(joinedYear) >= Integer.parseInt(CPSConstants.LEAVESCRIPTSTARTDATE)) {
				yearsList = session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.NAME))
					.add(Restrictions.between(CPSConstants.NAME, joinedYear, retirementYear)).list();
			} else {
				yearsList = session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc(CPSConstants.NAME))
					.add(Restrictions.between(CPSConstants.NAME, CPSConstants.LEAVESCRIPTSTARTDATE, retirementYear)).list();
			}
		} catch (Exception e) {
			throw e;
		}
		return yearsList;
	} // End
	@Override
	public String getYearId(final String year) throws Exception {
		Session session = null;
		String yearsId = "";
		try {
			session = hibernateUtils.getSession();
			yearsId = String.valueOf(((YearTypeDTO)session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("name", year)).uniqueResult()).getId());
		} catch (Exception e) {
			throw e;
		}
		return yearsId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DoPartDTO> getDoPartList(LeaveAdministratorBean leaveBean)throws Exception {
		Session session = null;
		List<DoPartDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			//Integer [] status = {1, 2};
			//Integer [] gazettedType = {0, Integer.parseInt(leaveBean.getGazettedType())};
			//list = session.createSQLQuery("select rnd.id id,rnd.ref_number doPartNumber,rnd.ref_date doDate from reference_number_details rnd,dopart_ii_type_master dtm where rnd.type_id=dtm.id and rnd.status=1 and dtm.status=1 and rnd.type_id=?")
			//.addScalar("id", Hibernate.INTEGER).addScalar("doPartNumber", Hibernate.STRING).addScalar("doDate", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DoPartDTO.class)).setInteger(0,Integer.parseInt(leaveBean.getGazettedType())).list();
			list = session.createCriteria(DoPartDTO.class).add(Expression.in(CPSConstants.STATUS, new Integer[]{1, 2})).add(Expression.in("typeId", new Integer[]{0, Integer.parseInt(leaveBean.getGazettedType())}))
					.addOrder(Order.asc("id")).addOrder(Order.asc("doPartDate")).list();		
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	public LeaveApplicationBean getRetirementDate(LeaveApplicationBean leaveBean) throws Exception{
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			String retirementDate  = ((String)session.createSQLQuery("SELECT GET_RETIREMENT_DATE('"+ leaveBean.getSfID() +"') FROM DUAL").uniqueResult()).toUpperCase();
			leaveBean.setRetirementDate(retirementDate);
		} catch(Exception e) {
			throw e;
		}
		return leaveBean;
	}
	
	@Override
	public String getRetirementDate(final String sfid) throws Exception { // Added by Naresh
		Session session = null;
		String retirementDate = null;
		final String sql = "SELECT GET_RETIREMENT_DATE(?) FROM DUAL"; 
		try {
			session = hibernateUtils.getSession();
			retirementDate  = ((String) session.createSQLQuery(sql).setString(0, sfid).uniqueResult()).toUpperCase();
		} catch(Exception e) {
			throw e;
		}
		return retirementDate;
	}
	
	@Override
	public String getASLJoiningDate(final String sfid) throws Exception {
		Session session = null;
		String joinDate = null;
		final String sql = "SELECT TO_CHAR(DOJ_ASL, 'DD-MON-YYYY') FROM emp_master WHERE sfid = ?";
		try {
			session = hibernateUtils.getSession();
			joinDate = ((String) session.createSQLQuery(sql).setString(0, sfid).uniqueResult()).toUpperCase();
		} catch(Exception e) {
			throw e;
		}
		return joinDate;
	}

	@Override
	public String checkBalance(LeaveApplicationBean leaveAppBean) throws Exception {
		Session session = null;
		String result = null;
		String resultDays = null;
		String endResult = null;
		String expectedBalance;
		LeaveRequestBean lrb = null;
		try {
			session = hibernateUtils.getSession();
			expectedBalance = (String) session.createSQLQuery("SELECT CHECK_FUTURE_BALANCE(?, ?, ?, ?) FROM DUAL").setString(0, leaveAppBean.getSfID())
					.setString(1, leaveAppBean.getLeaveType()).setDate(2, leaveAppBean.getRequestedDate()).setString(3, leaveAppBean.getToDate()).uniqueResult();
			if (Float.parseFloat(expectedBalance) > 0) {
				if (CPSUtils.isNullOrEmpty(leaveAppBean.getPrevHolidays())) {
					leaveAppBean.setPrevHolidays("0");
				}
				if (CPSUtils.isNullOrEmpty(leaveAppBean.getNextHolidays())) {
					leaveAppBean.setNextHolidays("0");
				}
				result = (String) session.createSQLQuery("SELECT GET_LEAVE_PERIODS(?, ?, ?, ?, ?, ?) FROM DUAL").setString(0, leaveAppBean.getFromDate()).setString(1, CPSUtils.getCurrentDate()).setString(2, leaveAppBean.getToDate())
						.setString(3, leaveAppBean.getPrevHolidays()).setString(4, leaveAppBean.getNextHolidays()).setInteger(5, Integer.parseInt(leaveAppBean.getLeaveType())).uniqueResult();
				if (CPSUtils.compareStrings(CPSConstants.EL, leaveAppBean.getLeaveType())) {
					resultDays = (String) session.createSQLQuery("SELECT GET_LEAVE_PERIOD_DAYS(?, ?, ?, ?, ?, ?) FROM DUAL").setString(0, result.split("#")[0] + "#" + result.split("#")[1])
							.setString(1, leaveAppBean.getFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveAppBean.getToDate())
							.setInteger(4, Integer.valueOf(leaveAppBean.getPrevHolidays())).setInteger(5, Integer.valueOf(leaveAppBean.getNextHolidays())).uniqueResult();
					if ((Float.parseFloat(resultDays.split("-")[1]) + Float.parseFloat(resultDays.split("-")[2])) <= Float.parseFloat(expectedBalance)) {
						endResult = CPSConstants.SUCCESS;
					} else {
						endResult = CPSConstants.FAILED + "#" + expectedBalance;
					}
				} else {
					lrb = new LeaveRequestBean();
					BeanUtils.copyProperties(leaveAppBean, lrb);
					resultDays = checkHolidays.getHolidaysBetweenDates(lrb, result);
					if (Integer.parseInt(leaveAppBean.getFromDate().split("-")[2]) > Integer.parseInt(CPSUtils.getCurrentYear())) {
						if (CPSUtils.compareStrings(leaveAppBean.getFromHalfDayFlag(), CPSConstants.Y)) {
							resultDays = resultDays.split("-")[0] + "-" + resultDays.split("-")[1] + "-" + (Float.parseFloat(resultDays.split("-")[2]) - 0.5f);
						}
						if (CPSUtils.compareStrings(leaveAppBean.getToHalfDayFlag(), CPSConstants.Y)) {
							resultDays = resultDays.split("-")[0] + "-" + resultDays.split("-")[1] + "-" + (Float.parseFloat(resultDays.split("-")[2]) - 0.5f);
						}
					} else {
						if (CPSUtils.compareStrings(leaveAppBean.getToHalfDayFlag(), CPSConstants.Y)) {
							resultDays = resultDays.split("-")[0] + "-" + resultDays.split("-")[1] + "-" + (Float.parseFloat(resultDays.split("-")[2]) - 0.5f);
						}
					}
					if (Float.parseFloat(resultDays.split("-")[2]) <= Float.parseFloat(expectedBalance)) {
						endResult = CPSConstants.SUCCESS;
					} else {
						endResult = CPSConstants.FAILED + "#" + expectedBalance;
					}
				}
			} else {
				endResult = CPSConstants.FAILED + "#" + expectedBalance;	
			}
		} catch(Exception e) {
			throw e;
		}
		return endResult;
	}
	
	@Override
	public String leaveImpact(LeaveApplicationBean leaveAppBean) throws Exception {
		String result = null;
		Session session = null;
		KeyValueDTO obj = null;
		try {
			session = hibernateUtils.getSession();
			obj = (KeyValueDTO) session.createSQLQuery("SELECT additional_data AS name, debit_leaves AS value FROM leave_request_details WHERE sfid = ? AND status NOT IN (?, ?) AND leave_type_id = ?")
					.addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING)
					.setString(0, leaveAppBean.getSfID()).setString(1, CPSConstants.STATUSDECLINED).setString(2, CPSConstants.STATUSCANCELLED)
					.setString(3, leaveAppBean.getLeaveType()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(obj)) {
				if (CPSUtils.compareStrings(leaveAppBean.getAdditionalData(), obj.getName())) {
					if (CPSUtils.compareStrings(leaveAppBean.getAdditionalData(), "Ph.D") && (Integer.parseInt(leaveAppBean.getNoOfDays()) > (Integer.parseInt(leaveAppBean.getSelectedLeaveDetails().getMaxPerSPell()) - Integer.parseInt(obj.getValue())))) {
						result = CPSConstants.FAILED + "#" + (Integer.parseInt(leaveAppBean.getSelectedLeaveDetails().getMaxPerSPell()) - Integer.parseInt(obj.getValue()));
					} else if (CPSUtils.compareStrings(leaveAppBean.getAdditionalData(), "PG") && (Integer.parseInt(leaveAppBean.getNoOfDays()) > (Integer.parseInt(CPSConstants.PGMAXLEAVES) - Integer.parseInt(obj.getValue())))) {
						result = CPSConstants.FAILED + "#" + (Integer.parseInt(CPSConstants.PGMAXLEAVES) - Integer.parseInt(obj.getValue()));
					}
 				} else {
 					result = CPSConstants.SUCCESS;
 				}
			} else {
				result = CPSConstants.SUCCESS;
			}
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public String changeRecordStatus(String requestId, String sfid, String type) throws Exception {
		// SQL Queries
		String sql = "UPDATE leave_request_details SET status = ? WHERE request_id = ?";
		String sql1 = "UPDATE request_workflow_history SET status = ?, stage_status = ?, remarks = ? WHERE id = (SELECT max(id) FROM request_workflow_history WHERE request_id = ?)";
		// Code
		String result = null;
		int updated = 0;
		LeaveAmendmentDTO amendDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			// change record status to cancelled in leave_request_details
			updated = type.equals(CPSConstants.CANCELLEAVERECORDSTATUS) ? session.createSQLQuery(sql).setString(0, CPSConstants.STATUSCANCELLED).setString(1, requestId).executeUpdate() : session.createSQLQuery(sql).setString(0, CPSConstants.STATUSSANCTIONED).setString(1, requestId).executeUpdate();
			if (updated != 0) {
				updated = 0;
				// change record status to cancelled in request_workflow_history
				updated = type.equals(CPSConstants.CANCELLEAVERECORDSTATUS) ? session.createSQLQuery(sql1).setString(0, CPSConstants.STATUSCANCELLED).setString(1, CPSConstants.STATUSCANCELLED).setString(2, CPSConstants.LEAVEAMENDMENTMESSAGE).setString(3, requestId).executeUpdate() : session.createSQLQuery(sql1).setString(0, CPSConstants.STATUSSANCTIONED).setString(1, CPSConstants.STATUSSANCTIONED).setString(2, "").setString(3, requestId).executeUpdate();
				// insert details into leave_amendment_details
				if (CPSUtils.compareStrings(CPSConstants.CANCELLEAVERECORDSTATUS, type)) {
					amendDTO = new LeaveAmendmentDTO();
					amendDTO.setRequestId(Integer.parseInt(requestId));
					amendDTO.setCreatedBy(sfid);
					amendDTO.setCreationTime(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
					amendDTO.setLastModifiedBy(sfid);
					amendDTO.setLastModifiedTime(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
					session.save(amendDTO);
				} else {
					amendDTO = (LeaveAmendmentDTO) session.get(LeaveAmendmentDTO.class, Integer.parseInt(requestId));
					session.delete(amendDTO);
				}
				result = CPSConstants.SUCCESS;
			} else {
				result = CPSConstants.FAILED;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			session.flush();
		}
		return result;
	}
	
	@Override
	public LeaveRequestBean getLeaveDetails(String requestId) throws Exception {
		LeaveRequestBean appliedLeave = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			appliedLeave = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestId)).uniqueResult();
			appliedLeave.setSelectedLeaveDetails((LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("status", 1)).add(Expression.eq("leaveTypeId", appliedLeave.getLeaveTypeID())).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return appliedLeave;
	}
	
	@Override
	public String getLeaveFutureBound() throws Exception {
		String result = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			result = session.createSQLQuery("select value from configuration_details where name = '" + CPSConstants.LEAVEFUTUREBOUND + "'").uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public String getLeavePastBound() throws Exception {
		String result = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			result = session.createSQLQuery("select value from configuration_details where name = '" + CPSConstants.LEAVEPASTBOUND + "'").uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		}
		return result;
	} // End
}
