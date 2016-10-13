package com.callippus.web.business.requestprocess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.requests.CancelLeaveRequestBean;
import com.callippus.web.beans.requests.ConvertLeaveRequestBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.leave.request.CheckHolidays;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.admin.LeaveAdministratorBean;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dao.request.ILeaveRequestDAO;
import com.callippus.web.leave.dto.AvailableLeavesDTO;
import com.callippus.web.leave.dto.EmpLeaveSpellsDTO;
import com.callippus.web.leave.dto.ErpAvailableLeaveSaveDTO;
import com.callippus.web.leave.dto.ErpAvailableLeavesDTO;
import com.callippus.web.leave.dto.ErpEmpLeavesDTO;
import com.callippus.web.leave.dto.ErpUsedLeavesDTO;
import com.callippus.web.leave.dto.LeaveAccountDTO;
import com.callippus.web.leave.dto.LeaveDetailsDTO;
import com.callippus.web.leave.dto.LeaveRequestExceptionsDTO;
import com.callippus.web.leave.dto.LeaveTxnDTO;
import com.callippus.web.leave.dto.LeaveTypeDTO;
import com.callippus.web.leave.dto.LeaveYearlyBalanceDTO;
import com.callippus.web.leave.dto.PastLeavesDTO;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

@Service
public class LeaveRequestProcess extends TxRequestProcess {

	private static Log log = LogFactory.getLog(LeaveRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CheckHolidays checkHolidays;
	@Autowired
	private ILeaveRequestDAO iLeaveRequestDAO;

	/**
	 * Initial workflow.
	 *
	 * @param lrb
	 *            the lrb
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String initWorkflow(LeaveRequestBean lrb) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflow(LeaveRequestBean lrb)>>>>>>>>>");
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			lrb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));

			if (CPSUtils.compareStrings(lrb.getLeaveRequestType(), CPSConstants.CANCEL)) {
				lrb.setRequestType(CPSConstants.CANCELLEAVEREQ);
				lrb.setRequestTypeID(CPSConstants.LEAVECANCELREQUESTID);
				lrb.setResult(submitCancelTxnDetails(lrb));
			} else if (CPSUtils.compareStrings(lrb.getLeaveRequestType(), CPSConstants.CONVERT)) {
				lrb.setRequestType(CPSConstants.CONVERTLEAVEREQ);
				lrb.setRequestTypeID(CPSConstants.LEAVECONVERTREQUESTID);
				lrb.setResult(submitConvertTxnDetails(lrb));
			} else {
				lrb.setRequestType(CPSConstants.LEAVEREQ);
				lrb.setRequestTypeID(CPSConstants.LEAVEREQUESTID);
				lrb.setResult(submitTxnDetails(lrb));
			}

			if (CPSUtils.compareStrings(lrb.getResult(), CPSConstants.SUCCESS)) {
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(lrb, rb);
				rb.setDescription(" Leave Period is  <b>"+lrb.getFromDate()+"</b>  to  <b>"+lrb.getToDate()+"</b>");
				if(lrb.getRequestedBy()!=null)
					rb.setAppliedBy(lrb.getRequestedBy());
				lrb.setResult(txRequestProcess.initWorkflow(rb));
			}
		} catch (Exception e) {
			throw e;
		}
		return lrb.getResult();
	}

	/**
	 * Insert leave request txn details. And update the available leaves.
	 *
	 * @param leaveRequestBean
	 *            the leave request bean
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String submitTxnDetails(LeaveRequestBean leaveRequestBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>submitTxnDetails(LeaveRequestBean leaveRequestBean)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.isNullOrEmpty(leaveRequestBean.getLeaveSubType())) {
				leaveRequestBean.setLeaveTypeDetails(leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails());
				leaveRequestBean.setLeaveSubTypeDetails(new SpecialCasualLeaveDTO());
			} else {
				leaveRequestBean.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.SCL)));
				leaveRequestBean.setLeaveSubTypeDetails((SpecialCasualLeaveDTO) session.get(SpecialCasualLeaveDTO.class, Integer.valueOf(leaveRequestBean.getLeaveSubType())));
			}

			leaveRequestBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());

			if (CPSUtils.isNull(leaveRequestBean.getPrevHolidays())) {
				leaveRequestBean.setPrevHolidays("0");
			}
			if (CPSUtils.isNull(leaveRequestBean.getNextHolidays())) {
				leaveRequestBean.setNextHolidays("0");
			}

			//added by Narayana to resolve bug No:ESS-718/////for CML, prefix,sufix not required
			if (leaveRequestBean.getLeaveType().equals("4")) {
				leaveRequestBean.setPrefix("");
				leaveRequestBean.setSuffix("");
			}

			// insert applied leave (txn) details
			session.saveOrUpdate(leaveRequestBean);
			session.flush();

			// insert exceptions
			getExceptionalDetails(leaveRequestBean.getExceptionalDetails(), String.valueOf(leaveRequestBean.getId()));

			// update leaves count w/o medical certificate case (For LND, an employee an able to apply 180 days in entire service & Incase of CML 90 days in entire service)
			updateLeaveCountForWoMC(leaveRequestBean, CPSConstants.ADD);

			// update leave credits
			if (CPSUtils.isNullOrEmpty(leaveRequestBean.getLeaveSubType())) {
				/**
				 * EL leaves will be debitted, if they exceed 300 while credit. So check whether 01-Jan & 1-Jul is in between from date & to date, If so we should split the no of days and consider
				 * holidays in that.
				 */
				if (CPSUtils.compareStrings(CPSConstants.EL, leaveRequestBean.getLeaveType()) || CPSUtils.compareStrings(CPSConstants.CL, leaveRequestBean.getLeaveType())) {
					leaveRequestBean = assignPeriodDebits(leaveRequestBean);
				} else {
					if (Integer.valueOf(leaveRequestBean.getSelectedLeaveDetails().getDebitMappingID()) != 0) {
						leaveRequestBean.setPresentPeriodDebit((Float.parseFloat(leaveRequestBean.getNoOfDays()) + Float.parseFloat(leaveRequestBean.getNextHolidays())
								+ Float.parseFloat(leaveRequestBean.getPrevHolidays())) * CPSUtils.convertToFloat(leaveRequestBean.getSelectedLeaveDetails().getDebitMappingLeaves()));
					} else {
						leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(leaveRequestBean.getNoOfDays()) + Float.parseFloat(leaveRequestBean.getNextHolidays())
								+ Float.parseFloat(leaveRequestBean.getPrevHolidays()));
					}
					leaveRequestBean.setPreviousPeriodDebit(0);
					leaveRequestBean.setNextPeriodDebit(0);
				}

				leaveRequestBean = updateLeaveCredits(leaveRequestBean);

			} else {
				leaveRequestBean.setDebitFrom(leaveRequestBean.getLeaveTypeDetails().getId());
				leaveRequestBean.setDebitLeaves(leaveRequestBean.getNoOfDays());
				leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(leaveRequestBean.getNoOfDays()) + Float.parseFloat(leaveRequestBean.getNextHolidays()) + Float.parseFloat(leaveRequestBean.getPrevHolidays()));
				leaveRequestBean.setPreviousPeriodDebit(0);
				leaveRequestBean.setNextPeriodDebit(0);
			}
			if (CPSUtils.isNullOrEmpty(leaveRequestBean.getOtherRemarks()) && !CPSUtils.isNullOrEmpty(leaveRequestBean.getType())) {
				leaveRequestBean.setOtherRemarks(leaveRequestBean.getType());
			} else if (!CPSUtils.isNullOrEmpty(leaveRequestBean.getType())) {
				leaveRequestBean.setOtherRemarks(leaveRequestBean.getOtherRemarks() + "#" + leaveRequestBean.getType());
			}
			if (CPSUtils.compareStrings(leaveRequestBean.getType(), CPSConstants.AMENDMENT)) {
				session.createSQLQuery("UPDATE leave_amendment_details SET lrd_amended_id = ? WHERE lrd_request_id = ?").setInteger(0, Integer.parseInt(leaveRequestBean.getRequestID())).setString(1, leaveRequestBean.getReferenceId()).executeUpdate();
			}
			// update the debit details
			session.saveOrUpdate(leaveRequestBean);
			session.flush();
			leaveRequestBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveRequestBean.getResult();
	}

	public LeaveRequestBean assignPeriodDebits(LeaveRequestBean leaveRequestBean) throws Exception {
		Session session = null;
		String resultDays = null;
		LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = null;
		int type = 0, type1 = 0;
		int fromYear = 0, toYear = 0, currentYear = 0;
		boolean flag = false;
		try {
			session = hibernateUtils.getSession();
			/**
			 * Check whether the leaves around '01-JAN' or '01-JUL', If they are then whether the leaves applied date before the from_date or after the to_date
			 */
			String result = (String) session.createSQLQuery("select get_leave_periods(?, ?, ?, ?, ?, ?) from dual").setString(0, leaveRequestBean.getFromDate()).setString(1, CPSUtils.getCurrentDate()).setString(2, leaveRequestBean.getToDate())
					.setString(3, leaveRequestBean.getPrevHolidays()).setString(4, leaveRequestBean.getNextHolidays()).setInteger(5, Integer.parseInt(leaveRequestBean.getLeaveType())).uniqueResult();
			resultDays = (String) session.createSQLQuery("select get_leave_period_days(?, ?, ?, ?, ?, ?) from dual").setString(0, result.split("#")[0] + "#" + result.split("#")[1])
					.setString(1, leaveRequestBean.getFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveRequestBean.getToDate())
					.setInteger(4, Integer.valueOf(leaveRequestBean.getPrevHolidays())).setInteger(5, Integer.valueOf(leaveRequestBean.getNextHolidays())).uniqueResult();
			if (CPSUtils.compareStrings(result.split("#")[0], "FH") && CPSUtils.compareStrings(leaveRequestBean.getLeaveType(), CPSConstants.EL)) {
				type = 0;
			} else if (CPSUtils.compareStrings(result.split("#")[0], "SH") && CPSUtils.compareStrings(leaveRequestBean.getLeaveType(), CPSConstants.EL)) {
				// Added by Narayana and Modified by Naresh
				fromYear = Integer.parseInt(leaveRequestBean.getFromDate().split("-")[2]);
				toYear = Integer.parseInt(leaveRequestBean.getToDate().split("-")[2]);
				currentYear = Integer.parseInt(CPSUtils.getCurrentDate().split("-")[2]);
				if (result.equals("SH#previous#ALL")) {
					if (fromYear < currentYear && toYear < currentYear) {
						CPSUtils.monthMap();
						int fromMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveRequestBean.getFromDate().split("-")[1]));
						int toMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveRequestBean.getToDate().split("-")[1]));
						if ((fromMonthId >= 1 && fromMonthId <= 6) && (toMonthId >= 1 && toMonthId <= 6)) { // Both are in same spell JAN - JUN
							type = 1;
						} else if ((fromMonthId >= 1 && fromMonthId <= 6) && (toMonthId >= 7 && toMonthId <= 12)) { // Both are in different spells JAN - JUN & JUL - DEC
							type = 0;
							type1 = 1;
							flag = true;
						} else if ((fromMonthId >= 7 && fromMonthId <= 12) && (toMonthId >= 1 && toMonthId <= 6)) { // Both are in different spells JUL - DEC & JAN - JUN
							type = 0;
							type1 = 1;
						} else if ((fromMonthId >= 7 && fromMonthId <= 12) && (toMonthId >= 7 && toMonthId <= 12)) { // Both are in same spell JUL - DEC
							type = 0;
						}
					} else if (fromYear < currentYear && toYear == currentYear) { 
						type = 0;
						type1 = 1;
					} else {
						type = 1;
					}
					
				} else {
					type = 1;
				} // End
			} else {
				type = 0;
			}
			if (CPSUtils.compareStrings(leaveRequestBean.getSelectedLeaveDetails().getHolidayIntFlag(), "N")) {
				resultDays = checkHolidays.getHolidaysBetweenDates(leaveRequestBean, result);
			}
			
			if (CPSUtils.compareStrings(result.split("#")[1], CPSConstants.NEXT)) {
				leaveRequestBean.setPreviousPeriodDebit(0);
						/*		result = session.createSQLQuery(
										"select case " + "when add_months(to_date('01-JAN','DD-MON'),12) between to_date(?,'DD-MON-YYYY')-? and (to_date(?,'DD-MON-YYYY')+?) "
												+ "then (to_date('01-JAN-'||?,'DD-MON-YYYY'))-((to_date(?,'DD-MON-YYYY'))-?) || '-' || "
												+ "(((((to_date(?,'DD-MON-YYYY'))+?)-to_date('01-JAN-'||?,'DD-MON-YYYY')))+1) "
												+ "when to_date('01-JUL','DD-MON') between (to_date(?,'DD-MON-YYYY')-?) and (to_date(?,'DD-MON-YYYY')+?) "
												+ "then (to_date('01-JUL-'||?,'DD-MON-YYYY'))-((to_date(?,'DD-MON-YYYY'))-?)  || '-' || "
												+ "(((((to_date(?,'DD-MON-YYYY'))+?)-to_date('01-JUL-'||?,'DD-MON-YYYY')))+1) else '0-0' end res from dual").setString(0, leaveRequestBean.getFromDate()).setString(1,
										leaveRequestBean.getPrevHolidays()).setString(2, leaveRequestBean.getToDate()).setString(3, leaveRequestBean.getNextHolidays()).setString(4,
										leaveRequestBean.getToDate().split("-")[2]).setString(5, leaveRequestBean.getFromDate()).setString(6, leaveRequestBean.getPrevHolidays()).setString(7,
										leaveRequestBean.getToDate()).setString(8, leaveRequestBean.getNextHolidays()).setString(9, leaveRequestBean.getToDate().split("-")[2]).setString(10,
										leaveRequestBean.getFromDate()).setString(11, leaveRequestBean.getPrevHolidays()).setString(12, leaveRequestBean.getToDate()).setString(13, leaveRequestBean.getNextHolidays())
										.setString(14, leaveRequestBean.getToDate().split("-")[2]).setString(15, leaveRequestBean.getFromDate()).setString(16, leaveRequestBean.getPrevHolidays()).setString(17,
												leaveRequestBean.getToDate()).setString(18, leaveRequestBean.getNextHolidays()).setString(19, leaveRequestBean.getToDate().split("-")[2]).uniqueResult().toString();
						*/
				leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(resultDays.split("-")[1]));
				leaveRequestBean.setNextPeriodDebit(Float.parseFloat(resultDays.split("-")[2]));

				if (CPSUtils.compareStrings(leaveRequestBean.getFromHalfDayFlag(), "Y")) {
					if (Float.parseFloat(resultDays.split("-")[1]) == 0) {
						leaveRequestBean.setNextPeriodDebit(leaveRequestBean.getNextPeriodDebit() - 0.5f);
					} else {
						leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() - 0.5f);
					}
				}
				if (CPSUtils.compareStrings(leaveRequestBean.getToHalfDayFlag(),"Y")) {
					leaveRequestBean.setNextPeriodDebit(leaveRequestBean.getNextPeriodDebit() - 0.5f);
				}
			} else if (CPSUtils.compareStrings(result.split("#")[1], CPSConstants.PREVIOUS)) {
				leaveRequestBean.setNextPeriodDebit(0);
							/*	result = session.createSQLQuery(
										"select case " + "when to_date('01-JAN','DD-MON') between to_date(?,'DD-MON-YYYY')-? and (to_date(?,'DD-MON-YYYY')+?) "
												+ "then (to_date('01-JUL-'||?,'DD-MON-YYYY'))-((to_date(?,'DD-MON-YYYY'))-?) || '-' || "
												+ "(((((to_date(?,'DD-MON-YYYY'))+?)-to_date('01-JUL-'||?,'DD-MON-YYYY')))+1) "
												+ "when to_date('01-JUL','DD-MON') between (to_date(?,'DD-MON-YYYY')-?) and (to_date(?,'DD-MON-YYYY')+?) "
												+ "then (to_date('01-JUL-'||?,'DD-MON-YYYY'))-((to_date(?,'DD-MON-YYYY'))-?)  || '-' || "
												+ "(((((to_date(?,'DD-MON-YYYY'))+?)-to_date('01-JUL-'||?,'DD-MON-YYYY')))+1) else '0-0' end res from dual").setString(0, leaveRequestBean.getFromDate()).setString(1,
										leaveRequestBean.getPrevHolidays()).setString(2, leaveRequestBean.getToDate()).setString(3, leaveRequestBean.getNextHolidays()).setString(4,
										leaveRequestBean.getToDate().split("-")[2]).setString(5, leaveRequestBean.getFromDate()).setString(6, leaveRequestBean.getPrevHolidays()).setString(7,
										leaveRequestBean.getToDate()).setString(8, leaveRequestBean.getNextHolidays()).setString(9, leaveRequestBean.getToDate().split("-")[2]).setString(10,
										leaveRequestBean.getFromDate()).setString(11, leaveRequestBean.getPrevHolidays()).setString(12, leaveRequestBean.getToDate()).setString(13, leaveRequestBean.getNextHolidays())
										.setString(14, leaveRequestBean.getToDate().split("-")[2]).setString(15, leaveRequestBean.getFromDate()).setString(16, leaveRequestBean.getPrevHolidays()).setString(17,
												leaveRequestBean.getToDate()).setString(18, leaveRequestBean.getNextHolidays()).setString(19, leaveRequestBean.getToDate().split("-")[2]).uniqueResult().toString();
							*/
				leaveRequestBean.setPreviousPeriodDebit(Float.parseFloat(resultDays.split("-")[0]));
				leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(resultDays.split("-")[1]));

				// Check whether half day leave is applied or not if applied we have to subtract 0.5 leave
				if (CPSUtils.compareStrings(leaveRequestBean.getFromHalfDayFlag(), "Y")) {
					leaveRequestBean.setPreviousPeriodDebit(leaveRequestBean.getPreviousPeriodDebit() - 0.5f);
				}
				if (CPSUtils.compareStrings(leaveRequestBean.getToHalfDayFlag(), "Y")) {
					if (Float.parseFloat(resultDays.split("-")[1]) == 0) {
						leaveRequestBean.setPreviousPeriodDebit(leaveRequestBean.getPreviousPeriodDebit() - 0.5f);
					} else {
						leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() - 0.5f);
					}
				}
				// Debit from the previous balance, if there were no laps then debit the leaves from current balance also
				YearTypeDTO yearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveRequestBean.getFromDate().split("-")[2]))
						.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
				Integer maxId = (Integer)session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
						.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveRequestBean.getLeaveType()))).add(Expression.eq("yearID", yearDTO.getId()))
						.add(Expression.eq("type", type)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
				if (!CPSUtils.isNullOrEmpty(maxId)) {
					leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, maxId);
				}
				
				if (CPSUtils.compareStrings(leaveRequestBean.getLeaveType(), CPSConstants.EL)) {
					if (type1 == 0) {
						if (!CPSUtils.isNullOrEmpty(leaveYearlyBalanceDTO)) {
							if (leaveYearlyBalanceDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) { // Leaves were lapsed
								String transResult = updateLeaveTransactionLapsRecord(leaveYearlyBalanceDTO.getSfID(), (leaveYearlyBalanceDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS)), 
										leaveRequestBean.getPreviousPeriodDebit(), leaveYearlyBalanceDTO.getType(), Integer.parseInt(yearDTO.getName()), CPSConstants.DEBIT);
								float debitLeaves = 0.0f; 
								if (Float.parseFloat(transResult.split("#")[1]) > 0.0f) {
									leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + Float.parseFloat(transResult.split("#")[1]));
									debitLeaves = leaveRequestBean.getPreviousPeriodDebit() - Float.parseFloat(transResult.split("#")[1]);
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() - debitLeaves);
									insertTxnDetails(leaveRequestBean.getSfID(), leaveRequestBean.getFromDate(), leaveYearlyBalanceDTO.getLeaveTypeDetails(), -debitLeaves, 
											CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), leaveRequestBean.getToDate(), leaveRequestBean.getRequestID());
								} else {
									debitLeaves = Float.parseFloat(transResult.split("#")[0]) - Float.parseFloat(transResult.split("#")[1]);
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + debitLeaves);
									insertTxnDetails(leaveRequestBean.getSfID(), leaveRequestBean.getFromDate(), leaveYearlyBalanceDTO.getLeaveTypeDetails(), debitLeaves, 
											CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), leaveRequestBean.getToDate(), leaveRequestBean.getRequestID());
								}
								session.saveOrUpdate(leaveYearlyBalanceDTO);
								session.flush();
							} else { // Not lapsed
								leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + leaveRequestBean.getPreviousPeriodDebit());
							}
						} else {
							leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + leaveRequestBean.getPreviousPeriodDebit());
						}
						/*	if (!CPSUtils.isNull(leaveYearlyBalanceDTO)) {
								if (leaveYearlyBalanceDTO.getBalance() >= Float.valueOf(CPSConstants.MAXELS)) {
									// Leaves were lapsed
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() - leaveRequestBean.getPreviousPeriodDebit());
												if (leaveYearlyBalanceDTO.getBalance() < Float.valueOf(CPSConstants.MAXELS)) {
										leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + (Float.valueOf(CPSConstants.MAXELS) - leaveYearlyBalanceDTO.getBalance()));
									}
								} else {
									// Not lapsed
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() - Float.parseFloat(resultDays.split("-")[0]));
									leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPreviousPeriodDebit() + leaveRequestBean.getPresentPeriodDebit());//Narayana
								}
								session.saveOrUpdate(leaveYearlyBalanceDTO);
								session.flush();
							}
						*/
					} else { // New code for previous year laps to present year previous laps
						if (fromYear < currentYear && toYear == currentYear) {
							leaveRequestBean = newAssignPeriodDebits(leaveRequestBean, "FH#previous", type, type1);
						} else {
							if (flag) {
								type = 1;
								type1 = 0;
							}
							leaveRequestBean = assignPreviousPeriodCombinationDebit(leaveRequestBean, "SH#previousCombination", type, type1);
						}
						return leaveRequestBean;
					}
				} else {
					if (result.contains("FH#previous")) {
						if (!CPSUtils.isNull(leaveYearlyBalanceDTO)) {
							if (leaveYearlyBalanceDTO.getBalance() > 0 && leaveYearlyBalanceDTO.getBalance() >= leaveRequestBean.getPreviousPeriodDebit() && (leaveRequestBean.getPresentPeriodDebit() != 0.0f)) {
								// Leaves were lapsed
								leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() - leaveRequestBean.getPreviousPeriodDebit());
								session.saveOrUpdate(leaveYearlyBalanceDTO);
								session.flush();
							}
						}
					} else {
						leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + leaveRequestBean.getPreviousPeriodDebit());
						leaveRequestBean.setPreviousPeriodDebit(0);
					}
				}
			} else if (CPSUtils.compareStrings(result.split("#")[1], CPSConstants.PRESENT)) {
				leaveRequestBean.setPreviousPeriodDebit(0);
				leaveRequestBean.setNextPeriodDebit(0);
				/*leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(resultDays.split("-")[1]) + Float.parseFloat(leaveRequestBean.getNextHolidays())
						+ Float.parseFloat(leaveRequestBean.getPrevHolidays()));*/
				leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(resultDays.split("-")[1]));
				if (CPSUtils.compareStrings(leaveRequestBean.getFromHalfDayFlag(), "Y")) {
					leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() - 0.5f);
				}
				if (CPSUtils.compareStrings(leaveRequestBean.getToHalfDayFlag(), "Y")) {
					leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() - 0.5f);
				}
			} else {
				leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(leaveRequestBean.getNoOfDays()) + Float.parseFloat(leaveRequestBean.getNextHolidays()) + Float.parseFloat(leaveRequestBean.getPrevHolidays()));
				leaveRequestBean.setNextPeriodDebit(0);
				leaveRequestBean.setPreviousPeriodDebit(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveRequestBean;
	}

	/**
	 * Update leave count in case of without medical certificate. In entire service an employee can able take 180 LND & 90 CML on without MC.
	 *
	 * @param leaveBean
	 *            the leave bean
	 * @throws Exception
	 *             the exception
	 */
	public void updateLeaveCountForWoMC(LeaveRequestBean leaveBean, final String type) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateLeaveCountForWoMC(LeaveRequestBean leaveBean)>>>>>>>>>");
		Session session = null;
		EmpLeaveSpellsDTO empSpellsDTO = null;
		try {
			session = hibernateUtils.getSession();

			// In case of Without medical certificate for LND, CML & EOL we should update the count
			if (CPSUtils.compareStrings(leaveBean.getAdditionalData(), CPSConstants.WOMC)
					&& (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.LND) || CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.CML))
					|| CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOLWOMC)) {

				if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.LND)) {
					empSpellsDTO = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.LND))).add(
							Expression.eq("sfID", leaveBean.getSfID())).uniqueResult();
				} else if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.CML)) {
					empSpellsDTO = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CML))).add(
							Expression.eq("sfID", leaveBean.getSfID())).uniqueResult();
				} else if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.EOLWOMC)) {
					empSpellsDTO = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.EOLWOMC))).add(
							Expression.eq("sfID", leaveBean.getSfID())).uniqueResult();
				}
				if (CPSUtils.isNull(empSpellsDTO)) {
					empSpellsDTO = new EmpLeaveSpellsDTO();
					empSpellsDTO.setSfID(leaveBean.getSfID());
					empSpellsDTO.setLeaveTypeID(Integer.valueOf(leaveBean.getLeaveType()));
				}
				if (CPSUtils.compareStrings(type, CPSConstants.ADD)) {
					empSpellsDTO.setLeavesCount(empSpellsDTO.getLeavesCount() + CPSUtils.convertToInteger(leaveBean.getNoOfDays()) + CPSUtils.convertToInteger(leaveBean.getPrevHolidays())
							+ CPSUtils.convertToInteger(leaveBean.getNextHolidays()));
				} else {
					empSpellsDTO.setLeavesCount(empSpellsDTO.getLeavesCount()
							- (Integer.valueOf(leaveBean.getNoOfDays()) + CPSUtils.convertToInteger(leaveBean.getPrevHolidays()) + CPSUtils.convertToInteger(leaveBean.getNextHolidays())));
				}
				session.saveOrUpdate(empSpellsDTO);
				session.flush();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Gets the exceptional details.
	 *
	 * @param exceptionalDetails
	 *            the exceptional details
	 * @param referenceID
	 *            the reference id
	 * @return the exceptional details
	 * @throws Exception
	 *             the exception
	 */
	public void getExceptionalDetails(final String exceptionalDetails, final String referenceID) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>getExceptionalDetails(String exceptionalDetails, String referenceID)>>>>>>>>>");
		try {
			JSONObject exceptionalJson = (JSONObject) JSONSerializer.toJSON(exceptionalDetails);
			String value = exceptionalJson.get("priorApprExceptions").toString();
			if (!CPSUtils.isNullOrEmpty(value)) {
				insertExceptionalDetails(value, referenceID);
			}
			value = exceptionalJson.get("minDaysExceptions").toString();
			if (!CPSUtils.isNullOrEmpty(value)) {
				insertExceptionalDetails(value, referenceID);
			}
			value = exceptionalJson.get("maxDaysExceptions").toString();
			if (!CPSUtils.isNullOrEmpty(value)) {
				insertExceptionalDetails(value, referenceID);
			}
			value = exceptionalJson.get("mcExceptions").toString();
			if (!CPSUtils.isNullOrEmpty(value)) {
				insertExceptionalDetails(value, referenceID);
			}
			value = exceptionalJson.get("fitnessExceptions").toString();
			if (!CPSUtils.isNullOrEmpty(value)) {
				insertExceptionalDetails(value, referenceID);
			}
			value = exceptionalJson.get("otherAvailExceptions").toString();
			if (!CPSUtils.isNullOrEmpty(value)) {
				insertExceptionalDetails(value, referenceID);
			}
			value = exceptionalJson.get("otherCertExceptions").toString();
			if (!CPSUtils.isNullOrEmpty(value)) {
				insertExceptionalDetails(value, referenceID);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Insert exceptional details.
	 *
	 * @param exception
	 *            the exception
	 * @param referenceID
	 *            the reference id
	 * @throws Exception
	 *             the exception
	 */
	public void insertExceptionalDetails(final String exception, final String referenceID) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>insertExceptionalDetails(String exception, String referenceID)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			LeaveRequestExceptionsDTO leaveReqExcepDTO = new LeaveRequestExceptionsDTO();
			leaveReqExcepDTO.setReferenceID(Integer.valueOf(referenceID));
			leaveReqExcepDTO.setExceptionID(Integer.valueOf(exception));
			session.saveOrUpdate(leaveReqExcepDTO);
			session.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Update leave credits.
	 *
	 * @param leaveRequestBean
	 *            the leave request bean
	 * @return the leave request bean
	 * @throws Exception
	 *             the exception
	 */
	public LeaveRequestBean updateLeaveCredits(LeaveRequestBean leaveRequestBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateLeaveCredits(LeaveRequestBean leaveRequestBean)>>>>>>>>>");
		Session session = null;
		AvailableLeavesDTO availableLeaves = null;
		LeaveTypeDTO debitLeaveTypeDTO = null;
		String noOfDays = leaveRequestBean.getNoOfDays();
		int yearID = 0;
		try {
			session = hibernateUtils.getSession();

			// Check whether the leave type is CL & the user is trying to applied in the previous year. In this case we should update the leave_yearly_balance table
			if (CPSUtils.compareStrings(leaveRequestBean.getLeaveType(), CPSConstants.CL) && !CPSUtils.compareStrings(leaveRequestBean.getToDate().split("-")[2], CPSUtils.getCurrentYear())
					&& Integer.valueOf(leaveRequestBean.getToDate().split("-")[2]) < Integer.valueOf(CPSUtils.getCurrentYear())) {
				yearID = ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveRequestBean.getToDate().split("-")[2])).uniqueResult()).getId();
				session.clear();
				LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
						.add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CL))).add(Expression.eq("yearID", yearID)).add(Expression.eq(CPSConstants.STATUS, 1))
						.add(Expression.eq(CPSConstants.TYPE, 0)).uniqueResult();

				leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() - Float.parseFloat(noOfDays));
				session.saveOrUpdate(leaveYearlyBalanceDTO);
				session.flush();
				leaveRequestBean.setDebitFrom(Integer.valueOf(CPSConstants.CL));
				leaveRequestBean.setDebitLeaves(noOfDays);
			} else {
				if (CPSUtils.compareStrings(leaveRequestBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.NOTAPPLICABLE)) {
					leaveRequestBean.setDebitFrom(leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails().getId());
				} else {
					if (CPSUtils.compareStrings(leaveRequestBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.Y)) {
						availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
								.add(Expression.eq("leaveTypeDetails", leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails())).uniqueResult();
						if (CPSUtils.isNull(availableLeaves)) {
							debitLeaveTypeDTO = leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails();
						}
					} else {
						/**
						 * For EOL the leaves should debit from EL, but we should add the leaves to the EOL, when the EL credits EOL will be deducted
						 */
						if (leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails().getId() == Integer.valueOf(CPSConstants.EOL)
								|| leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails().getId() == Integer.valueOf(CPSConstants.EOLWOMC)) {
							availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
									.add(Expression.eq("leaveTypeDetails", leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails())).uniqueResult();
							if (CPSUtils.isNull(availableLeaves)) {
								debitLeaveTypeDTO = leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails();
							}
						} else {
							debitLeaveTypeDTO = (LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(leaveRequestBean.getSelectedLeaveDetails().getDebitMappingID()));
							availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
									.add(Expression.eq("leaveTypeDetails", debitLeaveTypeDTO)).uniqueResult();
						}
					}

					if (CPSUtils.isNull(availableLeaves)) {
						availableLeaves = new AvailableLeavesDTO();
						availableLeaves.setSfID(leaveRequestBean.getSfID());
						availableLeaves.setLeaveTypeDetails(debitLeaveTypeDTO);
					}

					if (CPSUtils.compareStrings(leaveRequestBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.Y)) {
						if (CPSUtils.compareStrings(CPSConstants.EL, leaveRequestBean.getLeaveType())) {
							if (leaveRequestBean.getPresentPeriodDebit() != 0.0f) {
								availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() - leaveRequestBean.getPresentPeriodDebit());
							}
						} else if (CPSUtils.compareStrings(CPSConstants.CL, leaveRequestBean.getLeaveType())) {
							availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() - leaveRequestBean.getPresentPeriodDebit());
						} else {
							availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() - (CPSUtils.convertToFloat(leaveRequestBean.getNoOfDays())
									+ CPSUtils.convertToFloat(leaveRequestBean.getPrevHolidays()) + CPSUtils.convertToFloat(leaveRequestBean.getNextHolidays())));
						}
					} else {
						/**
						 * For EOL the leaves should debit from EL, but we should add the leaves to the EOL, when the EL credits EOL will be deducted
						 */
						if (leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails().getId() == Integer.valueOf(CPSConstants.EOL)
								|| leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails().getId() == Integer.valueOf(CPSConstants.EOLWOMC)) {

							// Add the eol to the existing eols
							availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() + (CPSUtils.convertToFloat(leaveRequestBean.getNoOfDays())
									+ CPSUtils.convertToFloat(leaveRequestBean.getPrevHolidays()) + CPSUtils.convertToFloat(leaveRequestBean.getNextHolidays())));
						} else {
							availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() - ((CPSUtils.convertToFloat(leaveRequestBean.getNoOfDays())
									* CPSUtils.convertToFloat(leaveRequestBean.getSelectedLeaveDetails().getDebitMappingLeaves())) + CPSUtils.convertToFloat(leaveRequestBean.getPrevHolidays())
									+ CPSUtils.convertToFloat(leaveRequestBean.getNextHolidays())));

							noOfDays = String.valueOf(CPSUtils.convertToFloat(leaveRequestBean.getNoOfDays()) * CPSUtils.convertToFloat(leaveRequestBean.getSelectedLeaveDetails().getDebitMappingLeaves()));
						}
					}

					session.saveOrUpdate(availableLeaves);
					session.flush();
					leaveRequestBean.setDebitFrom(availableLeaves.getLeaveTypeDetails().getId());
				}
				if (leaveRequestBean.getDebitFrom() == Integer.parseInt(CPSConstants.EL) || leaveRequestBean.getDebitFrom() == Integer.parseInt(CPSConstants.CL)) {
					if (leaveRequestBean.getNextPeriodDebit() > 0 && leaveRequestBean.getPresentPeriodDebit() > 0) {
						leaveRequestBean.setDebitLeaves(String.valueOf(leaveRequestBean.getPresentPeriodDebit()));
					} else if (leaveRequestBean.getNextPeriodDebit() > 0 && leaveRequestBean.getPresentPeriodDebit() == 0) {
						leaveRequestBean.setDebitLeaves(String.valueOf(0));
					} else if (leaveRequestBean.getPreviousPeriodDebit() > 0 && leaveRequestBean.getPresentPeriodDebit() > 0) {
						leaveRequestBean.setDebitLeaves(String.valueOf(leaveRequestBean.getPresentPeriodDebit()));
					} else if (leaveRequestBean.getPreviousPeriodDebit() > 0 && leaveRequestBean.getPresentPeriodDebit() == 0) {
						leaveRequestBean.setDebitLeaves(String.valueOf(0));
					} else {
						leaveRequestBean.setDebitLeaves(String.valueOf(Float.parseFloat(noOfDays) + Float.parseFloat(leaveRequestBean.getPrevHolidays()) + Float.parseFloat(leaveRequestBean.getNextHolidays())));
					}
				} else {
					leaveRequestBean.setDebitLeaves(String.valueOf(Float.parseFloat(noOfDays) + Float.parseFloat(leaveRequestBean.getPrevHolidays()) + Float.parseFloat(leaveRequestBean.getNextHolidays())));
				}

				// If the leave type is LND we should debit from the LND & also from the HPL
				if (CPSUtils.compareStrings(String.valueOf(leaveRequestBean.getSelectedLeaveDetails().getLeaveTypeDetails().getId()), CPSConstants.LND)) {
					availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
							.add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.HPL))).uniqueResult();
					if (CPSUtils.isNull(availableLeaves)) {
						availableLeaves = new AvailableLeavesDTO();
						availableLeaves.setSfID(leaveRequestBean.getSfID());
						availableLeaves.setLeaveTypeDetails((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(CPSConstants.HPL)));
						availableLeaves.setAvailableLeaves(-(CPSUtils.convertToFloat(leaveRequestBean.getNoOfDays()) + CPSUtils.convertToFloat(leaveRequestBean.getPrevHolidays())
								+ CPSUtils.convertToFloat(leaveRequestBean.getNextHolidays())));
					} else {
						availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() - (CPSUtils.convertToFloat(leaveRequestBean.getNoOfDays())
								+ CPSUtils.convertToFloat(leaveRequestBean.getPrevHolidays()) + CPSUtils.convertToFloat(leaveRequestBean.getNextHolidays())));
					}
					session.saveOrUpdate(availableLeaves);
					session.flush();
					leaveRequestBean.setDebitFrom(Integer.valueOf(CPSConstants.HPL));
					leaveRequestBean.setDebitLeaves(String.valueOf(CPSUtils.convertToFloat(leaveRequestBean.getNoOfDays()) + CPSUtils.convertToFloat(leaveRequestBean.getPrevHolidays())
							+ CPSUtils.convertToFloat(leaveRequestBean.getNextHolidays())));
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			session.clear();
		}
		return leaveRequestBean;
	}

	/**
	 * Insert cancel request txn details in the leave_cancellation_details table.
	 *
	 * @param leaveRequestBean
	 *            the leave request bean
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	public String submitCancelTxnDetails(LeaveRequestBean leaveRequestBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>submitCancelTxnDetails(LeaveRequestBean leaveRequestBean)>>>>>>>>>");
		Session session = null;
		CancelLeaveRequestBean crb = null;
		try {
			session = hibernateUtils.getSession();
			crb = new CancelLeaveRequestBean();
			BeanUtils.copyProperties(leaveRequestBean, crb);
			crb.setRequestedDate(CPSUtils.getCurrentDateWithTime());

			session.saveOrUpdate(crb);
			session.flush();
			leaveRequestBean.setResult(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return leaveRequestBean.getResult();
	}

	/**
	 * This method will be called when a user wants to approve the request.
	 *
	 * @param lrb
	 *            the lrb
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String approvedRequest(LeaveRequestBean lrb) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(LeaveRequestBean lrb)>>>>>>>>>");
		RequestBean rb = null;
		try {
			rb = new RequestBean();
			BeanUtils.copyProperties(lrb, rb);
			rb = txRequestProcess.approvedRequest(rb);
			//txRequestProcess.initWorkflow(rb);
			rb.setResult(rb.getMessage());
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getResult())) {
				if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.LEAVE)) {
					rb.setResult(txRequestProcess.updateRequestHistory(rb.getHistoryID(), CPSConstants.SANCTIONED, rb.getIpAddress(), rb.getRemarks(), CPSConstants.STATUSSANCTIONED));
					rb.setResult(updateLeaveRequestStatus(lrb.getRequestID(), rb.getRequestType(), CPSConstants.STATUSSANCTIONED, null));
				} else if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.CANCELLEAVEREQ)) {
					rb.setResult(txRequestProcess.updateRequestHistory(rb.getHistoryID(), CPSConstants.SANCTIONED, rb.getIpAddress(), rb.getRemarks(), CPSConstants.STATUSCOMPLETED));
					rb.setResult(approveCancelledRequest(lrb.getRequestID(), rb.getIpAddress(),lrb.getSfID()));
				} else if (CPSUtils.compareStrings(rb.getRequestType(), CPSConstants.CONVERTLEAVEREQ)) {
					rb.setResult(txRequestProcess.updateRequestHistory(rb.getHistoryID(), CPSConstants.SANCTIONED, rb.getIpAddress(), rb.getRemarks(), CPSConstants.STATUSCOMPLETED));
					rb.setResult(approveConvertedRequest(lrb.getRequestID(), rb.getIpAddress()));
				}
				// rb.setResult(getLeaveDetailsForAccount(lrb.getRequestID(), rb.getRequestType()));
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getResult();
	}

	/**
	 * When caonverted request workflow completed, this method will be called.
	 *
	 * @param requestID
	 *            the request id
	 * @param ipAddress
	 *            the ip address
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String approveConvertedRequest(final String requestID, final String ipAddress) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approveConvertedRequest(String requestID, String ipAddress)>>>>>>>>>");
		Session session = null;
		String result = null;
		List<ConvertLeaveRequestBean> convertedList = null;
		ConvertLeaveRequestBean convertedLeaveBean = null;
		String leaveRequestID = null;
		try {
			session = hibernateUtils.getSession();
			convertedList = session.createCriteria(ConvertLeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(requestID))).list();
			Iterator<ConvertLeaveRequestBean> leave = convertedList.iterator();

			while (leave.hasNext()) {
				convertedLeaveBean = leave.next();
				leaveRequestID = convertedLeaveBean.getReferenceDetails().getRequestID();
				// change the leave request status to converted which was applied earlier
				updateLeaveRequestStatus(leaveRequestID, CPSConstants.LEAVE, CPSConstants.STATUSCONVERTED, null);
				// change the leave request history status to converted
				updateLeaveRequestHistory(leaveRequestID, CPSConstants.STATUSCONVERTED, ipAddress, null);
				// change the leave converted request to sanctioned
				updateLeaveRequestStatus(requestID, CPSConstants.CONVERTLEAVEREQ, CPSConstants.STATUSSANCTIONED, null);
				LeaveRequestBean leaveRequestBean = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq("convertionID", String.valueOf(convertedLeaveBean.getId()))).uniqueResult();
				// change the converted leaves in leave_request_details to sanctioned
				updateLeaveRequestStatus(leaveRequestBean.getRequestID(), CPSConstants.LEAVE, CPSConstants.STATUSSANCTIONED, null);
				session.flush();
				// credit the leaves
				creaditLeaves(CPSConstants.CONVERTLEAVEREQ, null, convertedLeaveBean.getReferenceDetails(), null);
			}

			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * Update leave request history.
	 *
	 * @param leaveRequestID
	 *            the leave request id
	 * @param updatedStatus
	 *            the updated status
	 * @param ipAddress
	 *            the ip address
	 * @throws Exception
	 *             the exception
	 */
	public void updateLeaveRequestHistory(final String leaveRequestID, final String updatedStatus, final String ipAddress, final String sfid) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateLeaveRequestHistory(String leaveRequestID, String updatedStatus, String ipAddress)>>>>>>>>>");
		Session session = null;
		String	remarks = "";
		try {

			if(updatedStatus == "9" || updatedStatus.equals("9")) {
				remarks = ""+sfid+" Cancelled leave request on this Ip Adress"+ipAddress+" on "+CPSUtils.getCurrentDate()+"";
			}
			session = hibernateUtils.getSession();
			session
			.createSQLQuery(
					"update request_workflow_history set status=?, REMARKS=?,stage_status=? where id=(select historyID from (select max(id) historyID,max(request_stage) from request_workflow_history where request_id=? ))")
					.setString(0, updatedStatus).setString(1, remarks).setString(2, updatedStatus).setString(3, leaveRequestID).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * When leave cancellation request was completed, then this method will be called.
	 *
	 * @param requestID
	 *            the request id
	 * @param ipAddress
	 *            the ip address
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String approveCancelledRequest(final String requestID, final String ipAddress, final String sfid) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>approveCancelledRequest(String requestID, String ipAddress)>>>>>>>>>");
		Session session = null;
		String result = null;
		String leaveRequestID = null;
		try {
			session = hibernateUtils.getSession();
			CancelLeaveRequestBean cancelBean = (CancelLeaveRequestBean) session.createCriteria(CancelLeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestID)).uniqueResult();
			leaveRequestID = cancelBean.getReferenceDetails().getRequestID();
			// change the leave request status to cancelled
			updateLeaveRequestStatus(leaveRequestID, CPSConstants.LEAVE, CPSConstants.STATUSCANCELLED, null);
			updateConvertedLeaveRequestStatus(leaveRequestID, CPSConstants.STATUSCANCELLED);
			// change the leave request history status to cancelled
			updateLeaveRequestHistory(leaveRequestID, CPSConstants.STATUSCANCELLED, ipAddress, sfid);
			// change the leave cancellation request to sanctioned
			updateLeaveRequestStatus(requestID, CPSConstants.CANCELLEAVEREQ, CPSConstants.STATUSSANCTIONED, null);
			// credit the leaves
			creaditLeaves(CPSConstants.CANCELLEAVEREQ, null, cancelBean.getReferenceDetails(), null);
			// update spells count
			saveOrUpdateLeaveSpells(cancelBean.getReferenceDetails().getSfID(), cancelBean.getReferenceDetails().getLeaveTypeID(), CPSConstants.SUBTRACT, cancelBean.getReferenceDetails().getOtherRemarks());
			cancelBean.getReferenceDetails().setLeaveType(cancelBean.getReferenceDetails().getLeaveTypeID());
			updateLeaveCountForWoMC(cancelBean.getReferenceDetails(), CPSConstants.ADD);
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		}
		return result;
	}

	/**
	 * When the user applied same leave continuously in more than one spell, then the days between two leaves will be considered. If the user cancelled one of that leave we should reset the values to
	 * 0.
	 *
	 * @param leaveReqBean
	 *            the leave req bean
	 * @throws Exception
	 *             the exception
	 */
	public void updateContinuousHolidays(LeaveRequestBean leaveReqBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateContinuousHolidays(LeaveRequestBean leaveReqBean)>>>>>>>>>");
		Session session = null;
		String result = null;
		AvailableLeavesDTO availLeaves = null;
		try {
			session = hibernateUtils.getSession();

			result = (String) session.createSQLQuery("select debit_from || '#' || next_holidays || '#' || request_id from (select request_id, debit_from, "
					+ "debit_leaves, prev_holidays, next_holidays from leave_request_details where status not in (?, ?, ?) and sfid = ? and "
					+ "to_date < to_date(?, 'DD-MON-YYYY') order by from_date desc) where rownum = 1").setString(0, CPSConstants.STATUSCANCELLED)
					.setString(1, CPSConstants.STATUSDECLINED).setString(2, CPSConstants.STATUSCONVERTED).setString(3, leaveReqBean.getSfID()).setString(4, leaveReqBean.getStrToDate()).uniqueResult();
			if (!CPSUtils.isNull(result) && result.split("#").length == 3) {
				if (Float.parseFloat(result.split("#")[1]) != 0) { // set the value to 0 & credit the leaves
					session.createSQLQuery("update leave_request_details set next_holidays = 0 where request_id = ?").setString(0, result.split("#")[2]).executeUpdate();
					availLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveReqBean.getSfID()))
							.add(Expression.eq("leaveTypeID", Integer.valueOf(result.split("#")[0]))).uniqueResult();
					if (!CPSUtils.isNull(availLeaves)) {
						availLeaves.setAvailableLeaves(availLeaves.getAvailableLeaves() + Float.valueOf(result.split("#")[1]));
						session.saveOrUpdate(availLeaves);
						session.flush();
					}
				}
			}

			result = (String) session.createSQLQuery("select debit_from || '#' || prev_holidays || '#' || request_id from (select request_id, debit_from, "
					+ "debit_leaves, prev_holidays, next_holidays from leave_request_details where status not in (?, ?, ?) and convert_ref_id is null and sfid = ? "
					+ "and to_date > to_date(?, 'DD-MON-YYYY') order by from_date) where rownum = 1").setString(0, CPSConstants.STATUSCANCELLED)
					.setString(1, CPSConstants.STATUSDECLINED).setString(2, CPSConstants.STATUSCONVERTED).setString(3, leaveReqBean.getSfID()).setString(4, leaveReqBean.getStrToDate()).uniqueResult();
			if (!CPSUtils.isNull(result) && result.split("#").length == 3) {
				if (Float.parseFloat(result.split("#")[1]) != 0) { // set the value to 0 & credit the leaves
					session.createSQLQuery("update leave_request_details set prev_holidays = 0 where request_id = ?").setString(0, result.split("#")[2]).executeUpdate();
					availLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveReqBean.getSfID()))
							.add(Expression.eq("leaveTypeID", Integer.valueOf(result.split("#")[0]))).uniqueResult();
					if (!CPSUtils.isNull(availLeaves)) {
						availLeaves.setAvailableLeaves(availLeaves.getAvailableLeaves() + Float.valueOf(result.split("#")[1]));
						session.saveOrUpdate(availLeaves);
						session.flush();
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Leaves without medical certificate count update method.
	 *
	 * @param requestID
	 *            the request id
	 * @param type
	 *            the type
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String updateLeaveCount(final String requestID, final String type) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateLeaveCount(String requestID, String type)>>>>>>>>>");
		Session session = null;
		String result = null;
		LeaveRequestBean leaveBean = null;
		CancelLeaveRequestBean cancelBean = null;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(type, CPSConstants.LEAVE)) {
				leaveBean = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq("requestID", requestID)).uniqueResult();
				saveOrUpdateLeaveSpells(leaveBean.getSfID(), leaveBean.getLeaveTypeID(), CPSConstants.SUBTRACT, leaveBean.getOtherRemarks());
				leaveBean.setLeaveType(leaveBean.getLeaveTypeID());
				updateLeaveCountForWoMC(leaveBean, CPSConstants.SUBTRACT);
			} else if (CPSUtils.compareStrings(type, CPSConstants.CANCELLEAVEREQ)) {
				cancelBean = (CancelLeaveRequestBean) session.createCriteria(CancelLeaveRequestBean.class).add(Expression.eq("requestID", requestID)).uniqueResult();
				saveOrUpdateLeaveSpells(cancelBean.getSfID(), cancelBean.getReferenceDetails().getLeaveTypeID(), CPSConstants.ADD, null);
				cancelBean.getReferenceDetails().setLeaveType(cancelBean.getReferenceDetails().getLeaveTypeID());
				updateLeaveCountForWoMC(cancelBean.getReferenceDetails(), CPSConstants.ADD);
			}
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * Save or update leave spells.
	 *
	 * @param sfid
	 *            the sfid
	 * @param leaveTypeID
	 *            the leave type id
	 * @param type
	 *            the type
	 * @throws Exception
	 *             the exception
	 */
	public void saveOrUpdateLeaveSpells(final String sfid, final String leaveTypeID, final String type, final String leaveCase) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>saveOrUpdateLeaveSpells(String sfid, String leaveTypeID, String type)>>>>>>>>>");
		Session session = null;
		EmpLeaveSpellsDTO empLeaveSpells = null;
		try {
			session = hibernateUtils.getSession();
			if (!CPSUtils.compareStrings(leaveTypeID, CPSConstants.EL) && !CPSUtils.compareStrings(leaveTypeID, CPSConstants.EOL) && !CPSUtils.compareStrings(leaveTypeID, CPSConstants.EOLWOMC)) {

				LeaveManagementBean leaveMngBean = (LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("leaveTypeId", leaveTypeID)).add(
						Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();

				if (CPSUtils.convertToInteger(leaveMngBean.getSpellsPerYear()) > 0 || CPSUtils.convertToInteger(leaveMngBean.getSpellsInService()) > 0) {
					empLeaveSpells = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("sfID", sfid)).add(
							Expression.eq("leaveTypeID", Integer.valueOf(leaveTypeID))).uniqueResult();

					if (!CPSUtils.isNull(empLeaveSpells)) {
						if (CPSUtils.compareStrings(type, CPSConstants.SUBTRACT) && !CPSUtils.compareStrings(CPSConstants.PROCEED, leaveCase)) {
							empLeaveSpells.setYearSpellsCount(empLeaveSpells.getYearSpellsCount() - 1);
							empLeaveSpells.setServiceSpellsCount(empLeaveSpells.getServiceSpellsCount() - 1);
						} else if (CPSUtils.compareStrings(type, CPSConstants.ADD)) {
							empLeaveSpells.setYearSpellsCount(empLeaveSpells.getYearSpellsCount() + 1);
							empLeaveSpells.setServiceSpellsCount(empLeaveSpells.getServiceSpellsCount() + 1);
						}
						if (empLeaveSpells.getYearSpellsCount() < 0) {
							empLeaveSpells.setYearSpellsCount(0);
						}
						session.saveOrUpdate(empLeaveSpells);
						session.flush();
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Update leave request status.
	 *
	 * @param requestID
	 *            the request id
	 * @param requestType
	 *            the request type
	 * @param status
	 *            the status
	 * @param doPartNo
	 *            the do part no
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String updateLeaveRequestStatus(final String requestID, final String requestType, final String status, final String doPartNo) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateLeaveRequestStatus(String requestID, String requestType, String status, String doPartNo)>>>>>>>>>");
		String result = null;
		Session session = null;
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();
			session.clear();

			sb.append("update ");
			if (CPSUtils.compareStrings(requestType, CPSConstants.LEAVE)) {
				sb.append("leave_request_details ");
			} else if (CPSUtils.compareStrings(requestType, CPSConstants.CANCELLEAVEREQ)) {
				sb.append("leave_cancellation_details ");
			} else if (CPSUtils.compareStrings(requestType, CPSConstants.CONVERTLEAVEREQ)) {
				sb.append("leave_conversion_details ");
				flag = true;
			}
			sb.append("set status=? ");
			if (!CPSUtils.isNullOrEmpty(doPartNo)) {
				sb.append(", do_part_id=" + doPartNo);
			}
			sb.append(" where request_id=?");
			session.createSQLQuery(sb.toString()).setString(0, status).setString(1, requestID).executeUpdate();
			if (flag && (CPSUtils.compareStrings(CPSConstants.STATUSDECLINED, status) || CPSUtils.compareStrings(CPSConstants.STATUSCANCELLED, status))) { // Added by Naresh
				session.createSQLQuery("UPDATE leave_request_details set status = ? WHERE convert_ref_id = (SELECT id FROM leave_conversion_details WHERE request_id = ?)")
						.setInteger(0, Integer.parseInt(status)).setString(1, requestID).executeUpdate();
			} // End
            result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			session.flush();
			session.clear();
		}
		return result;
	}

	/**
	 * Creadit leaves.
	 *
	 * @param requestID
	 *            the request id
	 * @param leaveBean
	 *            the leave bean
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	
	public String creaditLeaves(final String requestType, final String requestID, LeaveRequestBean leaveBean, final String actionTakenBy) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>creaditLeaves(String requestID, LeaveRequestBean leaveBean)>>>>>>>>>");
		Session session = null;
		String result = null;
		AvailableLeavesDTO availableLeaves = null;
		int yearID = 0, type = 0, type1 = 0;
		boolean flag = true, flag1 = false;
		String periodType = null;
		int fromYear = 0, toYear = 0, currentYear = 0;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.isNull(leaveBean)) {
				leaveBean = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestID)).uniqueResult();
			}
			/*if (CPSUtils.compareStrings(CPSConstants.CONVERTLEAVEREQ, requestType) || CPSUtils.compareStrings(CPSConstants.CANCELLEAVEREQ, requestType)) {
				periodType = (String) session.createSQLQuery("select get_leave_periods(?,?,?,?,?,?) from dual").setString(0, CPSUtils.formattedDate(leaveBean.getFromDate())).setString(1, CPSUtils.getCurrentDate())
						.setString(2, CPSUtils.formattedDate(leaveBean.getToDate())).setString(3, leaveBean.getPrevHolidays()).setString(4, leaveBean.getNextHolidays()).setInteger(5, Integer.parseInt(leaveBean.getLeaveTypeID())).uniqueResult();
			} else {
				periodType = (String) session.createSQLQuery("select get_leave_periods(?,?,?,?,?,?) from dual").setString(0, CPSUtils.formattedDate(leaveBean.getFromDate())).setString(1, CPSUtils.formatDate(leaveBean.getRequestedDate()))
						.setString(2, CPSUtils.formattedDate(leaveBean.getToDate())).setString(3, leaveBean.getPrevHolidays()).setString(4,leaveBean.getNextHolidays()).setInteger(5, Integer.parseInt(leaveBean.getLeaveTypeID())).uniqueResult();
			}*/
			periodType = (String) session.createSQLQuery("SELECT GET_LEAVE_PERIODS(?, ?, ?, ?, ?, ?) FROM DUAL").setString(0, CPSUtils.formattedDate(leaveBean.getFromDate()))
					.setString(1, CPSUtils.getCurrentDate()).setString(2, CPSUtils.formattedDate(leaveBean.getToDate())).setInteger(3, Integer.valueOf(leaveBean.getPrevHolidays()))
					.setInteger(4, Integer.valueOf(leaveBean.getNextHolidays())).setInteger(5, Integer.parseInt(leaveBean.getLeaveTypeID())).uniqueResult();
			availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
					.add(Expression.eq("leaveTypeID", leaveBean.getDebitFrom())).uniqueResult();

			if (CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.CL) && !CPSUtils.compareStrings(leaveBean.getStrToDate().split("-")[2], CPSUtils.getCurrentYear())
					&& Integer.valueOf(leaveBean.getStrToDate().split("-")[2]) < Integer.valueOf(CPSUtils.getCurrentYear())) {
				yearID = ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrToDate().split("-")[2])).uniqueResult()).getId();

				LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
						.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getLeaveTypeID()))).add(Expression.eq("yearID", yearID)).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();

				leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + Float.valueOf(leaveBean.getDebitLeaves()) + Float.valueOf(leaveBean.getPrevHolidays())
						+ Float.valueOf(leaveBean.getNextHolidays()));
				session.saveOrUpdate(leaveYearlyBalanceDTO);
				session.flush();
			} else if (CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.EL)) {
				String requestedDate = CPSUtils.formatDate(leaveBean.getRequestedDate());
				// Handling very past leaves
				if ((Integer.parseInt(CPSUtils.getCurrentYear()) - Integer.parseInt(requestedDate.split("-")[2]) >= 2) || (Integer.parseInt(requestedDate.split("-")[2]) == Integer.parseInt(CPSUtils.getCurrentYear()) - 1) && (1 <= leaveBean.getRequestedDate().getMonth() && leaveBean.getRequestedDate().getMonth() <= 6)) {
					int spellType = (1 <= leaveBean.getRequestedDate().getMonth() && leaveBean.getRequestedDate().getMonth() <= 6) ? 1 : 0;
					LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, Integer.valueOf(session.createCriteria(LeaveYearlyBalanceDTO.class)
							.add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("type", spellType)).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom())))
							.add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, requestedDate.split("-")[2])).uniqueResult()).getId()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult().toString()));
					if ((leaveYearlyBalanceDTO.getBalance() + leaveBean.getPresentPeriodDebit() <= Float.parseFloat(CPSConstants.MAXELS)) && (availableLeaves.getAvailableLeaves() + leaveBean.getPresentPeriodDebit() <= Float.parseFloat(CPSConstants.MAXELS) + 15.0f)) {
						availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() + leaveBean.getPresentPeriodDebit());
						session.saveOrUpdate(availableLeaves);
					} else {
						insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), -leaveBean.getPresentPeriodDebit(),
								CPSConstants.PREVIOUSPERIODLEAVEID, actionTakenBy, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
					}
					insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), leaveBean.getPresentPeriodDebit(),
							CPSConstants.PREVIOUSPERIODLEAVEID, actionTakenBy, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
					PastLeavesDTO pastLeaveRecord = new PastLeavesDTO();
					pastLeaveRecord.setRequestId(Integer.parseInt(leaveBean.getRequestID()));
					pastLeaveRecord.setSfid(leaveBean.getSfID());
					pastLeaveRecord.setDescription(null);
					pastLeaveRecord.setCreatedBy(actionTakenBy);
					pastLeaveRecord.setCreationTime(CPSUtils.getCurrentDateWithTime());
					session.save(pastLeaveRecord);
					session.flush();
				} else {
					if (leaveBean.getPreviousPeriodDebit() > 0) {
						if (CPSUtils.compareStrings(periodType.split("#")[0], "FH")) {
							type = 0;
						} else { // Type = 1 Added by Narayana and Modified by Naresh
							fromYear = Integer.parseInt(CPSUtils.formattedDate(leaveBean.getFromDate()).split("-")[2]);
							toYear = Integer.parseInt(CPSUtils.formattedDate(leaveBean.getToDate()).split("-")[2]);
							currentYear = Integer.parseInt(CPSUtils.getCurrentDate().split("-")[2]);
							if (periodType.equals("SH#previous#ALL")) {
								if (fromYear < currentYear && toYear < currentYear) {
									CPSUtils.monthMap();
									int fromMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveBean.getStrFromDate().split("-")[1]));
									int toMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveBean.getStrToDate().split("-")[1]));
									if ((fromMonthId >= 1 && fromMonthId <= 6) && (toMonthId >= 1 && toMonthId <= 6)) { // Both are in same spell JAN - JUN
										type = 1;
									} else if ((fromMonthId >= 1 && fromMonthId <= 6) && (toMonthId >= 7 && toMonthId <= 12)) { // Both are in different spells JAN - JUN & JUL - DEC
										type = 1;
										type1 = 1;
										flag1 = true;
									} else if ((fromMonthId >= 7 && fromMonthId <= 12) && (toMonthId >= 1 && toMonthId <= 6)) { // Both are in different spells JUL - DEC & JAN - JUN
										type = 0;
										type1 = 1;
									} else if ((fromMonthId >= 7 && fromMonthId <= 12) && (toMonthId >= 7 && toMonthId <= 12)) { // Both are in same spell JUL - DEC
										type = 0;
									}
									flag = false;
								} else if (fromYear < currentYear && toYear == currentYear) {
									type = 0;
									type1 = 1;
									flag = false;
								} else {
									type = 1;
									flag = false;
								}
							} else {
								type = 1;
							}
						} // End
						
						if (type1 == 0) { // Credit previous period balance
								// Added by Naresh : crediting lapse case leaves if any were existed and applied
							YearTypeDTO yearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2]))
									.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
							Integer maxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
									.add(Expression.eq("type", type)).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom())))
									.add(Expression.eq("yearID", yearDTO.getId())).add(Expression.eq(CPSConstants.STATUS, 1))
									.setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult();
							
							if (!CPSUtils.isNullOrEmpty(maxId)) {
								LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, maxId);
								float creditBalance = 0.0f;
								if (fromYear == currentYear && currentYear == toYear) {
									if (leaveBean.getPreviousPeriodDebit() != 0.0f) {
										LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
													.add(Expression.eq("txnDate", leaveBean.getStrFromDate())).add(Expression.eq("txnToDate", leaveBean.getStrToDate()))
													.add(Expression.eq("status", 1)).add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
										if (!CPSUtils.isNull(txnDTO)) {
											updateLeaveTransactionLapsRecord(leaveYearlyBalanceDTO.getSfID(), (leaveYearlyBalanceDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - leaveBean.getPreviousPeriodDebit()), 
													-txnDTO.getNoOfLeaves(), leaveYearlyBalanceDTO.getType(), Integer.parseInt(yearDTO.getName()), CPSConstants.CREDIT);
											txnDTO.setStatus(0);
											txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
											txnDTO.setTxnToDate(CPSUtils.formattedDate(txnDTO.getTxnToDate()));
											txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
											
											leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() - txnDTO.getNoOfLeaves());
											
											session.saveOrUpdate(txnDTO);
											session.saveOrUpdate(leaveYearlyBalanceDTO);
											session.flush();
										}
									}
								} else {
									if (Integer.parseInt(leaveBean.getDebitLeaves()) == 0) {
										creditBalance = leaveBean.getPreviousPeriodDebit();
									} else {
										creditBalance = ((leaveBean.getPreviousPeriodDebit() - leaveBean.getPresentPeriodDebit()) > 0) 
												? leaveBean.getPreviousPeriodDebit() - leaveBean.getPresentPeriodDebit() : leaveBean.getPresentPeriodDebit() - leaveBean.getPreviousPeriodDebit();
									}
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + creditBalance);
									if (leaveYearlyBalanceDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
										updateLeaveTransactionLapsRecord(leaveYearlyBalanceDTO.getSfID(), (leaveYearlyBalanceDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - leaveBean.getPreviousPeriodDebit()), 
												creditBalance, leaveYearlyBalanceDTO.getType(), Integer.parseInt(yearDTO.getName()), CPSConstants.CREDIT);
										LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
												.add(Expression.eq("txnDate", leaveBean.getStrFromDate())).add(Expression.eq("txnToDate", leaveBean.getStrToDate()))
												.add(Expression.eq("status", 1)).add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
										txnDTO.setStatus(0);
										txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
										txnDTO.setTxnToDate(CPSUtils.formattedDate(txnDTO.getTxnToDate()));
										txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
										session.saveOrUpdate(txnDTO);
									}
									session.saveOrUpdate(leaveYearlyBalanceDTO);
									session.flush();
								}	
							}
						} else if (type == 0 && type1 == 1) {
							LeaveYearlyBalanceDTO leaveYearlyBalanceFromDTO = null, leaveYearlyBalanceToDTO = null;
							float fdays = 0.0f, tdays = 0.0f;
							String resultDays = null;
							if (fromYear < currentYear && toYear == currentYear) {
								resultDays = (String) session.createSQLQuery("select get_leave_period_days(?, ?, ?, ?, ?, ?) from dual").setString(0, "FH#previous")
									.setString(1, leaveBean.getStrFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveBean.getStrToDate())
									.setInteger(4, Integer.valueOf(leaveBean.getPrevHolidays())).setInteger(5, Integer.valueOf(leaveBean.getNextHolidays())).uniqueResult();
							} else {
								resultDays = (String) session.createSQLQuery("select get_leave_period_days(?, ?, ?, ?, ?, ?) from dual").setString(0, "SH#previousCombination")
										.setString(1, leaveBean.getStrFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveBean.getStrToDate())
										.setInteger(4, Integer.valueOf(leaveBean.getPrevHolidays())).setInteger(5, Integer.valueOf(leaveBean.getNextHolidays())).uniqueResult();
							}
							if (Integer.parseInt(leaveBean.getDebitLeaves()) == 0) {
								fdays = Integer.parseInt(resultDays.split("-")[0]);
								tdays = Integer.parseInt(resultDays.split("-")[1]);
							
								YearTypeDTO fromYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2]))
										.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
								Integer fMaxId = (Integer)session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
										.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", fromYearDTO.getId()))
										.add(Expression.eq("type", type)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
		
								if (!CPSUtils.isNullOrEmpty(fMaxId)) {
									leaveYearlyBalanceFromDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, fMaxId);
									leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() + fdays);
																	
									if (leaveYearlyBalanceFromDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
										updateLeaveTransactionLapsRecord(leaveYearlyBalanceFromDTO.getSfID(), (leaveYearlyBalanceFromDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - fdays), 
												fdays, leaveYearlyBalanceFromDTO.getType(), Integer.parseInt(fromYearDTO.getName()), CPSConstants.CREDIT);
										LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
												.add(Expression.eq("txnDate", leaveBean.getStrFromDate())).add(Expression.eq("status", 1))
												.add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
										txnDTO.setStatus(0);
										txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
										txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
										session.saveOrUpdate(txnDTO);
									}
									session.saveOrUpdate(leaveYearlyBalanceFromDTO);
									session.flush();
								}
		
								YearTypeDTO toYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrToDate().split("-")[2]))
										.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
								Integer tMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
										.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", toYearDTO.getId()))
										.add(Expression.eq("type", type1)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
		
								if (!CPSUtils.isNullOrEmpty(tMaxId)) {
									leaveYearlyBalanceToDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, tMaxId);
									leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() + tdays);
									
									if (leaveYearlyBalanceToDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
										updateLeaveTransactionLapsRecord(leaveYearlyBalanceToDTO.getSfID(), (leaveYearlyBalanceToDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - tdays), 
												tdays, leaveYearlyBalanceToDTO.getType(), Integer.parseInt(toYearDTO.getName()), CPSConstants.CREDIT);
										LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
												.add(Expression.eq("txnDate", CPSUtils.addNoOfDays(leaveBean.getStrFromDate(), resultDays.split("-")[0])))
												.add(Expression.eq("status", 1)).add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
										txnDTO.setStatus(0);
										txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
										txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
										session.saveOrUpdate(txnDTO);
									}
									session.saveOrUpdate(leaveYearlyBalanceToDTO);
									session.flush();							
								}
								leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
								leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
								leaveBean.setPreviousPeriodDebit(0);
								flag = false;
							} else {
								float creditBalance = ((leaveBean.getPreviousPeriodDebit() - leaveBean.getPresentPeriodDebit()) > 0) ? leaveBean.getPreviousPeriodDebit() - leaveBean.getPresentPeriodDebit() 
										: leaveBean.getPresentPeriodDebit() - leaveBean.getPreviousPeriodDebit();
								if (creditBalance != 0.0f) {
									YearTypeDTO fromYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2]))
											.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
									Integer fMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
											.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", fromYearDTO.getId()))
											.add(Expression.eq("type", type)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
									if (!CPSUtils.isNullOrEmpty(fMaxId)) {
										leaveYearlyBalanceFromDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, fMaxId);
									}
									
									YearTypeDTO toYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrToDate().split("-")[2]))
											.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
									Integer tMaxId = (Integer)session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
											.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", toYearDTO.getId()))
											.add(Expression.eq("type", type1)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
									if (!CPSUtils.isNullOrEmpty(tMaxId)) {
										leaveYearlyBalanceToDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, tMaxId);
									}
									
									if (creditBalance == 15) {
										int n = ((BigDecimal) session.createSQLQuery("SELECT COUNT(1) FROM leave_txn_details WHERE sfid = ? AND leave_type_id = ? "
												+ "AND (SUBSTR(no_of_leaves, 1, 1) = '-' OR no_of_leaves = 0) AND TO_CHAR(txn_from_date, 'MON-YYYY') = 'JAN-'||? "
												+ "AND txn_type = ? AND status IN (1, 0)").setString(0, leaveBean.getSfID()).setString(1, CPSConstants.EL)
											.setInteger(2, Integer.parseInt(fromYearDTO.getName()) + 1).setString(3, CPSConstants.AUTORUNSTATUSID).uniqueResult()).intValue();
										if (n != 0) {
											fdays = creditBalance;
											tdays = 0;
										} else {
											fdays = 0;
											tdays = creditBalance;
										}
									} else {
										tdays = creditBalance - (Float.parseFloat(CPSUtils.daysDifference(leaveBean.getStrFromDate(), "31-Dec-" + leaveBean.getStrFromDate().split("-")[2])) + 1.0f);
										fdays = creditBalance - tdays;
									}
															
									if (!CPSUtils.isNullOrEmpty(fMaxId)) {
										if (fdays > 15) fdays = 15;
										leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() + fdays);
										if (leaveYearlyBalanceFromDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
											updateLeaveTransactionLapsRecord(leaveYearlyBalanceFromDTO.getSfID(), (leaveYearlyBalanceFromDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - fdays), 
													fdays, leaveYearlyBalanceFromDTO.getType(), Integer.parseInt(fromYearDTO.getName()), CPSConstants.CREDIT);
											LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
													.add(Expression.eq("txnDate", leaveBean.getStrFromDate())).add(Expression.eq("status", 1))
													.add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
											txnDTO.setStatus(0);
											txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
											txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
											session.saveOrUpdate(txnDTO);
										}
										session.saveOrUpdate(leaveYearlyBalanceFromDTO);
										session.flush();
									}
									
									if (!CPSUtils.isNullOrEmpty(tMaxId)) {
										if (tdays > 15) tdays = 15;
										leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() + tdays);
										
										if (leaveYearlyBalanceToDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
											updateLeaveTransactionLapsRecord(leaveYearlyBalanceToDTO.getSfID(), (leaveYearlyBalanceToDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - tdays), 
													tdays, leaveYearlyBalanceToDTO.getType(), Integer.parseInt(toYearDTO.getName()), CPSConstants.CREDIT);
											LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
													.add(Expression.eq("txnDate", CPSUtils.addNoOfDays(leaveBean.getStrFromDate(), resultDays.split("-")[0])))
													.add(Expression.eq("status", 1)).add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
											txnDTO.setStatus(0);
											txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
											txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
											session.saveOrUpdate(txnDTO);
										}
										session.saveOrUpdate(leaveYearlyBalanceToDTO);
										session.flush();	
									}
									leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
									leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
									leaveBean.setPreviousPeriodDebit(0);
									flag = true;
								}
							}
						} else if (type == 1 && type1 == 1) {
							LeaveYearlyBalanceDTO leaveYearlyBalanceFromDTO = null, leaveYearlyBalanceToDTO = null;
							float fdays = 0.0f, tdays = 0.0f;
							String resultDays = null;
												
							resultDays = (String) session.createSQLQuery("select get_leave_period_days(?, ?, ?, ?, ?, ?) from dual").setString(0, "SH#previousCombination")
										.setString(1, leaveBean.getStrFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveBean.getStrToDate())
										.setInteger(4, Integer.valueOf(leaveBean.getPrevHolidays())).setInteger(5, Integer.valueOf(leaveBean.getNextHolidays())).uniqueResult();
							if (flag1) type1 = 0;
							if (Integer.parseInt(leaveBean.getDebitLeaves()) == 0) {
								fdays = Integer.parseInt(resultDays.split("-")[0]);
								tdays = Integer.parseInt(resultDays.split("-")[1]);
							
								YearTypeDTO fromYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2]))
										.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
								Integer fMaxId = (Integer)session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
										.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", fromYearDTO.getId()))
										.add(Expression.eq("type", type)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
		
								if (!CPSUtils.isNullOrEmpty(fMaxId)) {
									leaveYearlyBalanceFromDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, fMaxId);
									leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() + fdays);
																	
									if (leaveYearlyBalanceFromDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
										updateLeaveTransactionLapsRecord(leaveYearlyBalanceFromDTO.getSfID(), (leaveYearlyBalanceFromDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - fdays), 
												fdays, leaveYearlyBalanceFromDTO.getType(), Integer.parseInt(fromYearDTO.getName()), CPSConstants.CREDIT);
										
										LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
												.add(Expression.eq("txnDate", leaveBean.getStrFromDate())).add(Expression.eq("txnToDate", leaveBean.getStrToDate()))
												.add(Expression.eq("status", 1)).add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
										txnDTO.setStatus(0);
										txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
										txnDTO.setTxnToDate(CPSUtils.formattedDate(txnDTO.getTxnToDate()));
										txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
										session.saveOrUpdate(txnDTO);
										session.flush();
									}
									session.saveOrUpdate(leaveYearlyBalanceFromDTO);
									session.flush();
								}
		
								YearTypeDTO toYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrToDate().split("-")[2]))
										.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
								Integer tMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
										.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", toYearDTO.getId()))
										.add(Expression.eq("type", type1)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
		
								if (!CPSUtils.isNullOrEmpty(tMaxId)) {
									leaveYearlyBalanceToDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, tMaxId);
									leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() + tdays);
									
									if (leaveYearlyBalanceToDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
										updateLeaveTransactionLapsRecord(leaveYearlyBalanceToDTO.getSfID(), (leaveYearlyBalanceToDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - tdays), 
												tdays, leaveYearlyBalanceToDTO.getType(), Integer.parseInt(toYearDTO.getName()), CPSConstants.CREDIT);
										
										LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
												.add(Expression.eq("txnDate", CPSUtils.addNoOfDays(leaveBean.getStrFromDate(), resultDays.split("-")[0])))
												.add(Expression.eq("txnToDate", leaveBean.getStrToDate())).add(Expression.eq("status", 1))
												.add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
										txnDTO.setStatus(0);
										txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
										txnDTO.setTxnToDate(CPSUtils.formattedDate(txnDTO.getTxnToDate()));
										txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
										session.saveOrUpdate(txnDTO);
									}
									session.saveOrUpdate(leaveYearlyBalanceToDTO);
									session.flush();							
								}
								leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
								leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
								leaveBean.setPreviousPeriodDebit(0);
								flag = false;
							} else {
								float creditBalance = ((leaveBean.getPreviousPeriodDebit() - leaveBean.getPresentPeriodDebit()) > 0) ? leaveBean.getPreviousPeriodDebit() - leaveBean.getPresentPeriodDebit() 
										: leaveBean.getPresentPeriodDebit() - leaveBean.getPreviousPeriodDebit();
								if (creditBalance != 0.0f) {
									tdays = creditBalance - (Float.parseFloat(CPSUtils.daysDifference(leaveBean.getStrFromDate(), "30-Jun-" + leaveBean.getStrFromDate().split("-")[2])));
									fdays = (tdays <= 15) ? creditBalance - tdays : creditBalance - 15; 
									tdays = (tdays <= 15) ? tdays : 15;
									
									YearTypeDTO fromYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2]))
											.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
									Integer fMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
											.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", fromYearDTO.getId()))
											.add(Expression.eq("type", type)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
									
									if (!CPSUtils.isNullOrEmpty(fMaxId)) {
										leaveYearlyBalanceFromDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, fMaxId);
										leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() + fdays);
																		
										if (leaveYearlyBalanceFromDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
											updateLeaveTransactionLapsRecord(leaveYearlyBalanceFromDTO.getSfID(), (leaveYearlyBalanceFromDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - fdays), 
													fdays, leaveYearlyBalanceFromDTO.getType(), Integer.parseInt(fromYearDTO.getName()), CPSConstants.CREDIT);
	
											LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
													.add(Expression.eq("txnDate", leaveBean.getStrFromDate())).add(Expression.eq("txnToDate", leaveBean.getStrToDate()))
													.add(Expression.eq("status", 1)).add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
											txnDTO.setStatus(0);
											txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
											txnDTO.setTxnToDate(CPSUtils.formattedDate(txnDTO.getTxnToDate()));
											txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
											session.saveOrUpdate(txnDTO);
										}
										session.saveOrUpdate(leaveYearlyBalanceFromDTO);
										session.flush();
									}
									
									YearTypeDTO toYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrToDate().split("-")[2]))
											.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
									Integer tMaxId = (Integer)session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
											.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom()))).add(Expression.eq("yearID", toYearDTO.getId()))
											.add(Expression.eq("type", type1)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
			
									if (!CPSUtils.isNullOrEmpty(tMaxId)) {
										leaveYearlyBalanceToDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, tMaxId);
										leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() + tdays);
										
										if (leaveYearlyBalanceToDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
											updateLeaveTransactionLapsRecord(leaveYearlyBalanceToDTO.getSfID(), (leaveYearlyBalanceToDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS) - tdays), 
													tdays, leaveYearlyBalanceToDTO.getType(), Integer.parseInt(toYearDTO.getName()), CPSConstants.CREDIT);
											
											LeaveTxnDTO txnDTO = (LeaveTxnDTO) session.createCriteria(LeaveTxnDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
													.add(Expression.eq("txnDate", CPSUtils.addNoOfDays(leaveBean.getStrFromDate(), resultDays.split("-")[0])))
													.add(Expression.eq("txnToDate", leaveBean.getStrToDate())).add(Expression.eq("status", 1))
													.add(Expression.eq("txnType", CPSConstants.PREVIOUSPERIODLEAVEID)).uniqueResult();
											txnDTO.setStatus(0);
											txnDTO.setTxnDate(CPSUtils.formattedDate(txnDTO.getTxnDate()));
											txnDTO.setTxnToDate(CPSUtils.formattedDate(txnDTO.getTxnToDate()));
											txnDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.formatDate(txnDTO.getCreationDate())));
											session.saveOrUpdate(txnDTO);
										}
										session.saveOrUpdate(leaveYearlyBalanceToDTO);
										session.flush();	
									}
								}
								leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
								leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
								leaveBean.setPreviousPeriodDebit(0);
								flag = true;
							}
						}
					} // End
					if (leaveBean.getPresentPeriodDebit() > 0) {
						// Check whether next debit leaves exists or not, if leaves were exist & the script was executed then we should add those leaves
						if (leaveBean.getNextPeriodDebit() > 0) {
							if (checkScriptRun(leaveBean)) { // No Script executed
								leaveBean.setNextPeriodDebit(0);
								leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
								leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
							} else {
								// Add leaves in txn details
								insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), leaveBean.getNextPeriodDebit(),
										CPSConstants.PREVIOUSREQUESTSTATUSID, null, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
								if (!CPSUtils.compareStrings(leaveBean.getStrFromDate().split("-")[2], leaveBean.getStrToDate().split("-")[2])) {
									if (CPSUtils.compareStrings(CPSUtils.getCurrentYear(), leaveBean.getStrToDate().split("-")[2])) {
										LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, Integer.valueOf(session.createCriteria(LeaveYearlyBalanceDTO.class)
												.add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("type",0)).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom())))
												.add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2])).uniqueResult()).getId()))
												.add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult().toString()));
										float previousYearBalance = leaveYearlyBalanceDTO.getBalance();
										leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + leaveBean.getPresentPeriodDebit());
	
										if (previousYearBalance > Float.valueOf(CPSConstants.MAXELS)) {
											insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), -leaveBean.getPresentPeriodDebit(),
													CPSConstants.AUTORUNSTATUSID, null, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
										} else if (leaveYearlyBalanceDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) {
											insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), -(leaveYearlyBalanceDTO.getBalance()-Float.valueOf(CPSConstants.MAXELS)),
													CPSConstants.AUTORUNSTATUSID, null, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
										}
										leaveBean.setPresentPeriodDebit(0);
										leaveBean.setFromDate(leaveBean.getStrFromDate());
										leaveBean.setToDate(leaveBean.getStrToDate());
									}
								} else {
									LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, Integer.valueOf(session.createCriteria(LeaveYearlyBalanceDTO.class)
											.add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("type", 1)).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom())))
											.add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2])).uniqueResult()).getId()))
											.add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult().toString()));
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + leaveBean.getPresentPeriodDebit());
									leaveBean.setPresentPeriodDebit(0);
									leaveBean.setFromDate(leaveBean.getStrFromDate());
									leaveBean.setToDate(leaveBean.getStrToDate());
								}
							}
						} else if (leaveBean.getPreviousPeriodDebit() > 0) {
							if (type1 == 0 || (type == 0 && type1 == 1)) {
								leaveBean.setPreviousPeriodDebit(0);
								leaveBean.setFromDate(leaveBean.getStrFromDate());
								leaveBean.setToDate(leaveBean.getStrToDate());
								flag = true;
							}
						} else if ((Integer.parseInt(CPSUtils.formatDate(leaveBean.getRequestedDate()).split("-")[2]) == Integer.parseInt(CPSUtils.getCurrentYear()) - 1) && (7 <= leaveBean.getRequestedDate().getMonth() && leaveBean.getRequestedDate().getMonth() <= 12)) {
							if (!checkScriptRun(leaveBean)) { // Script executed
								LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, Integer.valueOf(session.createCriteria(LeaveYearlyBalanceDTO.class)
										.add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("type", 0)).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom())))
										.add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2])).uniqueResult()).getId()))
										.add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult().toString()));
								if (leaveYearlyBalanceDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) { // Lapse Record existed
									updateLeaveTransactionLapsRecord(leaveYearlyBalanceDTO.getSfID(), leaveYearlyBalanceDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS), 
											leaveBean.getPresentPeriodDebit(), leaveYearlyBalanceDTO.getType(), Integer.parseInt(CPSUtils.formatDate(leaveBean.getRequestedDate()).split("-")[2]), CPSConstants.CREDIT);
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + leaveBean.getPresentPeriodDebit());
									session.saveOrUpdate(leaveYearlyBalanceDTO);
									session.flush();
								} else {
									insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), leaveBean.getPresentPeriodDebit(),
											CPSConstants.PREVIOUSPERIODLEAVEID, actionTakenBy, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
									PastLeavesDTO pastLeaveRecord = new PastLeavesDTO();
									pastLeaveRecord.setRequestId(Integer.parseInt(leaveBean.getRequestID()));
									pastLeaveRecord.setSfid(leaveBean.getSfID());
									pastLeaveRecord.setDescription(null);
									pastLeaveRecord.setCreatedBy(actionTakenBy);
									pastLeaveRecord.setCreationTime(CPSUtils.getCurrentDateWithTime());
									session.save(pastLeaveRecord);
									insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), -leaveBean.getPresentPeriodDebit(),
											CPSConstants.PREVIOUSPERIODLEAVEID, actionTakenBy, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
									session.flush();
								}
								flag = false;
							}
						}
					} else {
						if (leaveBean.getNextPeriodDebit() > 0) {
							if (checkScriptRun(leaveBean)) { // No Script executed
								leaveBean.setNextPeriodDebit(0);
								leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
								leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
							} else { // Add leaves in txn details
								insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), leaveBean.getNextPeriodDebit(),
										CPSConstants.PREVIOUSREQUESTSTATUSID, null, CPSUtils.getCurrentDate(), null, leaveBean.getRequestID());
							}
						}
					}
					if (flag) { // Credit Present balance
						availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() + leaveBean.getPresentPeriodDebit() + leaveBean.getPreviousPeriodDebit() + leaveBean.getNextPeriodDebit());
						session.saveOrUpdate(availableLeaves);
						session.flush();
					}
				}
			} else {
				availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).add(
						Expression.eq("leaveTypeID", leaveBean.getDebitFrom())).uniqueResult();
				if (!CPSUtils.isNull(availableLeaves)) {
					if (availableLeaves.getLeaveTypeID() == Integer.valueOf(CPSConstants.EOL) || availableLeaves.getLeaveTypeID() == Integer.valueOf(CPSConstants.EOLWOMC)) {
						availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves()
								- (Float.valueOf(leaveBean.getDebitLeaves()) + Float.valueOf(leaveBean.getPrevHolidays()) + Float.valueOf(leaveBean.getNextHolidays())));
					} else {
						/*if (CPSUtils.compareStrings(CPSConstants.CONVERTLEAVEREQ, requestType) || CPSUtils.compareStrings(CPSConstants.CANCELLEAVEREQ, requestType)) {
							result = (String) session.createSQLQuery("select get_leave_periods(?,?,?,?,?,?) from dual").setString(0,CPSUtils.formattedDate(leaveBean.getFromDate())).setString(1, CPSUtils.getCurrentDate())
								.setString(2, CPSUtils.formattedDate(leaveBean.getToDate())).setInteger(3, Integer.valueOf(leaveBean.getPrevHolidays())).setInteger(4,Integer.valueOf(leaveBean.getNextHolidays())).setInteger(5, Integer.parseInt(leaveBean.getLeaveTypeID())).uniqueResult();
						} else {
							result = (String) session.createSQLQuery("select get_leave_periods(?,?,?,?,?,?) from dual").setString(0, CPSUtils.formattedDate(leaveBean.getFromDate())).setString(1, CPSUtils.formatDate(leaveBean.getRequestedDate()))
									.setString(2, CPSUtils.formattedDate(leaveBean.getToDate())).setInteger(3, Integer.valueOf(leaveBean.getPrevHolidays())).setInteger(4,Integer.valueOf(leaveBean.getNextHolidays())).setInteger(5, Integer.parseInt(leaveBean.getLeaveTypeID())).uniqueResult();
						}*/
						result = (String) session.createSQLQuery("SELECT GET_LEAVE_PERIODS(?, ?, ?, ?, ?, ?) FROM DUAL").setString(0, CPSUtils.formattedDate(leaveBean.getFromDate()))
								.setString(1, CPSUtils.getCurrentDate()).setString(2, CPSUtils.formattedDate(leaveBean.getToDate())).setInteger(3, Integer.valueOf(leaveBean.getPrevHolidays()))
								.setInteger(4, Integer.valueOf(leaveBean.getNextHolidays())).setInteger(5, Integer.parseInt(leaveBean.getLeaveTypeID())).uniqueResult();
						if (availableLeaves.getLeaveTypeID() == Integer.valueOf(CPSConstants.CL)) {
							//&& (CPSUtils.compareStrings(result.split("#")[0] + "#" + result.split("#")[1], "SH#next") || CPSUtils.compareStrings(result.split("#")[0] + "#" + result.split("#")[1], "FH#previous") || CPSUtils.compareStrings(result.split("#")[0] + "#" + result.split("#")[1], "FH#present"))
							// String resultDays=(String)session.createSQLQuery("select get_leave_period_days(?,?,?,?,?) from dual").setString(0, result).setString(1, leaveBean.getFromDate()).setString(2, leaveBean.getToDate()).setInteger(3, Integer.valueOf(leaveBean.getPrevHolidays())).setInteger(4, Integer.valueOf(leaveBean.getNextHolidays())).uniqueResult();
							if (leaveBean.getPresentPeriodDebit() > 0) {
								// Check whether next debit leaves exists or not if the leaves were exists & the script was executed then we should add those leaves
								if (leaveBean.getNextPeriodDebit() > 0) {
									if (checkScriptRun(leaveBean)) { // No Script executed
										leaveBean.setNextPeriodDebit(0);
										leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
										leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
									} else { // Add leaves in txn details
										insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), leaveBean.getNextPeriodDebit(),
												CPSConstants.PREVIOUSREQUESTSTATUSID, null, CPSUtils.getCurrentDate(), null, requestID);

										LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, Integer.valueOf(session.createCriteria(LeaveYearlyBalanceDTO.class)
												.add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("type",0)).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getDebitFrom())))
												.add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2])).uniqueResult()).getId()))
												.add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.projectionList().add(Projections.max("id"))).uniqueResult().toString()));
										leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + leaveBean.getPresentPeriodDebit());
										leaveBean.setPresentPeriodDebit(0);
										leaveBean.setFromDate(leaveBean.getStrFromDate());
										leaveBean.setToDate(leaveBean.getStrToDate());
									}
								} else if (leaveBean.getPreviousPeriodDebit() > 0) {
									int pastYearId = ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getStrFromDate().split("-")[2])).uniqueResult()).getId();
									LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
											.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getLeaveTypeID()))).add(Expression.eq("yearID", pastYearId)).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
									leaveYearlyBalanceDTO.setBalance(leaveYearlyBalanceDTO.getBalance() + Float.valueOf(leaveBean.getPreviousPeriodDebit()));
									session.saveOrUpdate(leaveYearlyBalanceDTO);
									leaveBean.setPreviousPeriodDebit(0);
								}
							} else {
								if (leaveBean.getNextPeriodDebit() > 0) {
									if (checkScriptRun(leaveBean)) { // No Script executed
										leaveBean.setNextPeriodDebit(0);
										leaveBean.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
										leaveBean.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));
									} else { // Add leaves in txn details
										insertTxnDetails(availableLeaves.getSfID(), CPSUtils.getCurrentDate(), availableLeaves.getLeaveTypeDetails(), leaveBean.getNextPeriodDebit(),
												CPSConstants.PREVIOUSREQUESTSTATUSID, null, CPSUtils.getCurrentDate(), null, requestID);
									}
								}
							}
							// Credit Present balance
							availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() + leaveBean.getPresentPeriodDebit() + leaveBean.getPreviousPeriodDebit() + leaveBean.getNextPeriodDebit());
							//session.saveOrUpdate(availableLeaves);
							//session.flush();
						} else {
							availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() + Float.valueOf(leaveBean.getDebitLeaves()) + Float.valueOf(leaveBean.getPrevHolidays())
									+ Float.valueOf(leaveBean.getNextHolidays()));
						}
					}
					session.saveOrUpdate(availableLeaves);
					session.evict(leaveBean);
					session.flush();
				}

				/**
				 * If the leave type is LND, then we should credit the leaves in LND & HPL
				 */
				if (leaveBean.getLeaveTypeID() == CPSConstants.LND) {
					availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).add(
							Expression.eq("leaveTypeID", leaveBean.getLeaveTypeID())).uniqueResult();
					availableLeaves.setAvailableLeaves(availableLeaves.getAvailableLeaves() + Float.valueOf(leaveBean.getNoOfDays()));
					session.saveOrUpdate(availableLeaves);
					session.flush();
				}
				// if not converted type request
				if (!CPSUtils.compareStrings(CPSConstants.CONVERTLEAVEREQ, requestType)) {
					// update the prev & next holidays in case of continuous same leave in multiple spell
					updateContinuousHolidays(leaveBean);
				}
			}
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		}
		return result;
	}

	public boolean checkScriptRun(LeaveRequestBean leaveBean) throws Exception { // Added by Naresh
		boolean status = true;
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();
			result = session.createSQLQuery("SELECT CHECK_SCRIPT_RUN(?, ?, ?, ?) FROM DUAL").setInteger(0, Integer.parseInt(leaveBean.getStrToDate().toUpperCase().split("-")[2]))
								.setString(1, leaveBean.getStrFromDate().toUpperCase()).setString(2, leaveBean.getStrToDate().toUpperCase())
								.setString(3, leaveBean.getSfID()).uniqueResult().toString();
			if (Integer.valueOf(result) > 0) {
				status = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	} // End

	/**
	 * Insert conversion txn details.
	 *
	 * @param leaveBean
	 *            the leave bean
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	public String submitConvertTxnDetails(LeaveRequestBean leaveBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>submitConvertTxnDetails(LeaveRequestBean leaveRequestBean)>>>>>>>>>");
		Session session = null;
		ConvertLeaveRequestBean convertBean = null;
		JSONArray convertLeaves = null;
		JSONObject leave = null;
		LeaveRequestBean leaveRequestBean = null, leaveReferenceBean = null;
		try {
			session = hibernateUtils.getSession();
			// Get converting leaves list with user populated data
			convertLeaves = (JSONArray) JSONSerializer.toJSON(leaveBean.getConvertLeaves());
			for (int i = 0; i < convertLeaves.size(); i++) {
				leave = (JSONObject) convertLeaves.get(i);
				convertBean = new ConvertLeaveRequestBean();
				convertBean.setRequestID(Integer.valueOf(leaveBean.getRequestID()));
				convertBean.setSfID(leaveBean.getSfID());
				convertBean.setReferenceID(leaveBean.getReferenceID());
				convertBean.setConvertedTo(leave.getString("leaveTypeId"));
				convertBean.setFromDate(leave.getString("fromDate"));
				convertBean.setToDate(leave.getString("toDate"));
				convertBean.setPrefix(leave.getString("prefix"));
				convertBean.setSuffix(leave.getString("suffix"));
				convertBean.setNoOfDays(leave.getString("appliedLeaves"));
				convertBean.setReason(leaveBean.getReason());
				convertBean.setIpAddress(leaveBean.getIpAddress());
				convertBean.setRequestedBy(leaveBean.getRequestedBy());
				convertBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
				convertBean.setStatus(leaveBean.getStatus());

				// Get referenced leave record which was already applied by the user earlier
				leaveReferenceBean = (LeaveRequestBean) session.get(LeaveRequestBean.class, leaveBean.getReferenceID());
				leaveRequestBean = new LeaveRequestBean();
				//BeanUtils.copyProperties(leaveReferenceBean, leaveRequestBean);
				leaveRequestBean.setId(0);
				leaveRequestBean.setSfID(convertBean.getSfID());
				leaveRequestBean.setLeaveTypeDetails((LeaveTypeDTO)session.get(LeaveTypeDTO.class, Integer.valueOf(convertBean.getConvertedTo())));
				leaveRequestBean.setAddress(leaveReferenceBean.getAddress());
				leaveRequestBean.setContactNumber(leaveReferenceBean.getContactNumber());
				leaveRequestBean.setDepartmentDetails(leaveReferenceBean.getDepartmentDetails());
				leaveRequestBean.setDepartmentID(leaveReferenceBean.getDepartmentID());
				leaveRequestBean.setDesignationID(leaveReferenceBean.getDesignationID());
				leaveRequestBean.setPrevHolidays(leaveReferenceBean.getPrevHolidays());
				leaveRequestBean.setFromHalfDayFlag(leaveReferenceBean.getFromHalfDayFlag());
				leaveRequestBean.setFromDate(convertBean.getFromDate());
				leaveRequestBean.setNextHolidays(leaveReferenceBean.getNextHolidays());
				leaveRequestBean.setToHalfDayFlag(leaveReferenceBean.getToHalfDayFlag());
				leaveRequestBean.setToDate(convertBean.getToDate());
				leaveRequestBean.setPrefix(convertBean.getPrefix());
				leaveRequestBean.setSuffix(convertBean.getSuffix());
				leaveRequestBean.setNoOfDays(convertBean.getNoOfDays());
				leaveRequestBean.setInternalNo(leaveReferenceBean.getInternalNo());
				/*if (CPSUtils.isNullOrEmpty(leaveRequestBean.getPrevHolidays())) {
					leaveRequestBean.setPrevHolidays("0");
				}
				if (CPSUtils.isNullOrEmpty(leaveBean.getNextHolidays())) {
					leaveRequestBean.setNextHolidays("0");
				}*/
				leaveRequestBean.setRequestedBy(convertBean.getRequestedBy());
				leaveRequestBean.setLeaveType(convertBean.getConvertedTo());
				leaveRequestBean.setSelectedLeaveDetails((LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("status", 1)).add(
						Expression.eq("leaveTypeId", leave.getString("leaveTypeId"))).uniqueResult());
				if (CPSUtils.compareStrings(convertBean.getConvertedTo(), CPSConstants.EL) || CPSUtils.compareStrings(convertBean.getConvertedTo(), CPSConstants.CL)) {
					leaveRequestBean = assignPeriodDebits(leaveRequestBean);
				} else {
					if (Integer.valueOf(leaveRequestBean.getSelectedLeaveDetails().getDebitMappingID()) != 0) {
						leaveRequestBean.setPresentPeriodDebit((Float.parseFloat(leaveRequestBean.getNoOfDays()) + Float.parseFloat(leaveRequestBean.getNextHolidays())
								+ Float.parseFloat(leaveRequestBean.getPrevHolidays())) * CPSUtils.convertToFloat(leaveRequestBean.getSelectedLeaveDetails().getDebitMappingLeaves()));
					} else {
						leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(leaveRequestBean.getNoOfDays()) + Float.parseFloat(leaveRequestBean.getNextHolidays())
								+ Float.parseFloat(leaveRequestBean.getPrevHolidays()));
					}
					leaveRequestBean.setPreviousPeriodDebit(0);
					leaveRequestBean.setNextPeriodDebit(0);
				}
				// Update leave account balance
				leaveRequestBean = updateLeaveCredits(leaveRequestBean);
				leaveRequestBean.setServiceBookFlag(leaveRequestBean.getSelectedLeaveDetails().getServiceBookFlag());
				leaveRequestBean.setIpAddress(convertBean.getIpAddress());
				leaveRequestBean.setDoPartNo(null);
				leaveRequestBean.setLeaveSubType(null);
				leaveRequestBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
				leaveRequestBean.setStatus(convertBean.getStatus());
				leaveRequestBean.setConvertedLeaveDetails(convertBean);
				// Save conversion details
				convertBean.setDebitFrom(leaveRequestBean.getDebitFrom());
				convertBean.setDebitLeaves(leaveRequestBean.getDebitLeaves());
				session.saveOrUpdate(convertBean);
				session.flush();
				// Save converting leave record
				leaveRequestBean.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
				session.saveOrUpdate(leaveRequestBean);
				session.flush();

				// Update leaves spells
				saveOrUpdateLeaveSpells(leaveRequestBean.getSfID(), leaveRequestBean.getLeaveType(), CPSConstants.ADD, leaveRequestBean.getOtherRemarks());

				leaveBean.setResult(CPSConstants.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean.getResult();
	}

	/**
	 * Roll back converted leaves.
	 *
	 * @param rb
	 *            the rb
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String rollBackConvertedLeaves(RequestBean rb) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>rollBackConvertedLeaves(RequestBean rb)>>>>>>>>>");
		String result = null;
		Session session = null;
		List<ConvertLeaveRequestBean> convertedList = null;
		try {
			session = hibernateUtils.getSession();
			convertedList = session.createCriteria(ConvertLeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(rb.getRequestID()))).list();
			for (int i = 0; i < convertedList.size(); i++) {
				AvailableLeavesDTO avilLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("leaveTypeID", convertedList.get(i).getDebitFrom())).add(
						Expression.eq("sfID", convertedList.get(i).getSfID())).uniqueResult();
				avilLeaves.setAvailableLeaves(avilLeaves.getAvailableLeaves() + Float.parseFloat(convertedList.get(i).getDebitLeaves()));
				session.saveOrUpdate(avilLeaves);
				session.flush();
				saveOrUpdateLeaveSpells(convertedList.get(i).getSfID(), convertedList.get(i).getConvertedTo(), CPSConstants.SUBTRACT, convertedList.get(i).getReferenceDetails().getOtherRemarks());
			}
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * Leave auto run script.
	 *
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String autoRunScript(String date) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>autoRunScript()>>>>>>>>>");
		Session session = null;
		List<LeaveManagementBean> leaveList = null;
		LeaveManagementBean leaveManagementBean = null;
		String sysDate = null;
		boolean status = true;
		String message = "";
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.isNullOrEmpty(date)) date = CPSUtils.getCurrentDate();
			// Duplicate checking by Narayana
			if ((date.toUpperCase().split("-")[1].equals("JAN") || date.toUpperCase().split("-")[1].equals("JUL"))
					&& (date.split("-")[2].equals(CPSUtils.getCurrentDate().split("-")[2]))) {
				StringBuffer sb = new StringBuffer();
				sb.append("select count(*) from leave_yearly_balance where type = ");
				if (date.toUpperCase().split("-")[1].equals("JAN")) {
					//String sql = "select count(*) from leave_yearly_balance where type=0 and year_id=65";
					sb.append("0");
					sb.append(" and year_id = ");
					sb.append(String.valueOf(Integer.parseInt(iLeaveRequestDAO.getYearId(date.split("-")[2])) - 1));
				} else { // for July
					sb.append("1");
					sb.append(" and year_id = ");
					sb.append(iLeaveRequestDAO.getYearId(date.split("-")[2]));
				}
				Object obj = session.createSQLQuery(sb.toString()).uniqueResult();
				if (obj != null && !obj.toString().equals("0")) { // Already script run
					status = false;
					message = "duplicateAutoRun";
				}
			} else { // Not a valid month or Not a valid current year
				status = false;
				message = "invalidAutoRun";
			}
			if (status) {
				// Check whether date is new year date or not, if it is new year date we should copy the balances to other table
				if (date.toUpperCase().indexOf("01-JAN") != -1) {
					copyLeaveBalances(String.valueOf(Integer.valueOf(date.split("-")[2]) - 1), "0", CPSConstants.ALL);
					resetYearSpells();
					resetCLLeaves();
				} else if (date.toUpperCase().indexOf("01-JUL") != -1) {
					copyLeaveBalances(String.valueOf(Integer.valueOf(date.split("-")[2])), "1", CPSConstants.EL);
				}

				sysDate = CPSUtils.getCurrentDate();
				leaveList = session.createCriteria(LeaveManagementBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.ne(CPSConstants.LEAVEDURATIONID, "0")).list();
				Iterator<LeaveManagementBean> leave = leaveList.iterator();
				while (leave.hasNext()) {
					leaveManagementBean = leave.next();
					leaveManagementBean.setCurrentDate(date);
					leaveManagementBean.setSystemDate(sysDate);
					if (CPSUtils.compareStrings(leaveManagementBean.getLeaveDurationDetails().getName(), CPSConstants.DAYS)) {
						System.out.println("from autoRunScript()>>>>>>>>updateDaysMappingLeaves()>>>>" + leaveManagementBean.getLeaveTypeDetails().getId());
						updateDaysMappingLeaves(leaveManagementBean);
					} else if (CPSUtils.compareStrings(leaveManagementBean.getLeaveDurationDetails().getName(), CPSConstants.MONTHS)) {
						System.out.println("from autoRunScript()>>>>>>>>updateMonthsMappingLeaves()>>>>" + leaveManagementBean.getLeaveTypeDetails().getId());
						updateMonthsMappingLeaves(leaveManagementBean);
						leaveManagementBean.setExperience(leaveManagementBean.getLeaveCredits());
						//newEmployeeAvailedLeaves(leaveManagementBean); // New employee availing leaves code is commented based on requirement
					} else if (CPSUtils.compareStrings(leaveManagementBean.getLeaveDurationDetails().getName(), CPSConstants.YEARS)) {
						System.out.println("from autoRunScript()>>>>>>>>updateYearsMappingLeaves()>>>>" + leaveManagementBean.getLeaveTypeDetails().getId());
						updateYearsMappingLeaves(leaveManagementBean);
						leaveManagementBean.setExperience(String.valueOf(Math.round(Float.valueOf(leaveManagementBean.getLeaveCredits()) * 12)));
						//newEmployeeAvailedLeaves(leaveManagementBean);	// New employee availing leaves code is commented based on requirement
					} else if (CPSUtils.compareStrings(leaveManagementBean.getLeaveDurationDetails().getName(), CPSConstants.SERVICE)) {
						updateServiceMappingLeaves(leaveManagementBean);
					}
				}
				message = "autoRunCompleted";
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public void copyLeaveBalances(final String year, final String type, final String backUp) throws Exception {
		Session session = null;
		List<AvailableLeavesDTO> list = null;
		Criteria ctr = null;
		try {
			session = hibernateUtils.getSession();
			ctr = session.createCriteria(AvailableLeavesDTO.class);
			if (CPSUtils.compareStrings(backUp, CPSConstants.EL)) {
				ctr.add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.EL)));
			}
			list = ctr.addOrder(Order.asc("sfID")).list();
			int yearId = ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq("name", year)).uniqueResult()).getId();
			for (AvailableLeavesDTO balanceDTO : list) {
				//if (balanceDTO.getSfID().equals("SF0920")) { // Remove if condition only (Testing for single employee)
				if (checkBalanceCopy(balanceDTO.getSfID(), balanceDTO.getLeaveTypeID(), yearId, type)) {
					log.debug("copyLeaveBalances() --> Back-up leave balance for the employee ----------> " + balanceDTO.getSfID());
					System.out.println("copyLeaveBalances() --> Back-up leave balance for the employee ----------> " + balanceDTO.getSfID());
					LeaveYearlyBalanceDTO leaveBalanceDTO = new LeaveYearlyBalanceDTO();
					leaveBalanceDTO.setSfID(balanceDTO.getSfID());
					leaveBalanceDTO.setLeaveTypeID(balanceDTO.getLeaveTypeID());
					leaveBalanceDTO.setBalance(balanceDTO.getAvailableLeaves());
					leaveBalanceDTO.setStatus(1);
					leaveBalanceDTO.setYearID(yearId);
					leaveBalanceDTO.setType(Integer.valueOf(type));
					session.saveOrUpdate(leaveBalanceDTO);
					session.flush();
				}
				//} // Remove if condition only (Testing for single employee)
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean checkBalanceCopy(final String sfID, final int leaveTypeID, final int yearId, final String type) throws Exception {
		Session session = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();
			LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq(CPSConstants.STATUS, 1))
					.add(Expression.eq("leaveTypeID", leaveTypeID)).add(Expression.eq("sfID", sfID)).add(Expression.eq("type", Integer.valueOf(type))).add(Expression.eq("yearID", yearId)).uniqueResult();
			if (CPSUtils.isNull(leaveYearlyBalanceDTO)) {
				status = true;
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	/**
	 * Update days mapping leaves.
	 *
	 * @param leaveManagementBean
	 *            the leave management bean
	 * @throws Exception
	 *             the exception
	 */
	public void updateDaysMappingLeaves(LeaveManagementBean leaveManagementBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateDaysMappingLeaves(LeaveManagementBean leaveManagementBean)>>>>>>>>>");
		// SQL Queries
		final String checkDate = "select (to_date(?, 'DD-MON-YYYY') - to_date((select value from configuration_details where name = ?), 'DD-MON-YYYY')) / "
				+ "(select leave_credits from leave_type_details where leave_type_id = ? and status = 1) cnt from dual";
		// Code
		Session session = null;
		String count = null;
		try {
			session = hibernateUtils.getSession();
			count = session.createSQLQuery(checkDate).setString(0, leaveManagementBean.getCurrentDate()).setString(1, CPSConstants.LEAVE_SCRIPT_START_DATE)
					.setString(2, leaveManagementBean.getLeaveTypeId()).uniqueResult().toString();
			if (CPSUtils.isInteger(count)) { // If the count is round value then update available leaves
				leaveManagementBean.setExperience("0");
				creditLeaves(leaveManagementBean);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Update months mapping leaves.
	 *
	 * @param leaveManagementBean
	 *            the leave management bean
	 * @throws Exception
	 *             the exception
	 */
	public void updateMonthsMappingLeaves(LeaveManagementBean leaveManagementBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateMonthsMappingLeaves(LeaveManagementBean leaveManagementBean)>>>>>>>>>");
		// SQL Queries
		final String checkDate = "select (months_between(to_date(?, 'DD-MON-YYYY'), to_date((select value from configuration_details where name = ?), 'DD-MON-YYYY'))) / "
					+ "(select leave_credits from leave_type_details where leave_type_id = ? and status = 1) cnt from dual";
		// Code
		Session session = null;
		String count = null;
		try {
			session = hibernateUtils.getSession();
			count = session.createSQLQuery(checkDate).setString(0, leaveManagementBean.getCurrentDate()).setString(1, CPSConstants.LEAVE_SCRIPT_START_DATE)
					.setString(2, leaveManagementBean.getLeaveTypeId()).uniqueResult().toString();
			if (CPSUtils.isInteger(count)) {
				//leaveManagementBean.setExperience("1");
				//System.out.println("from updateMonthsMappingLeaves()>>>>>>>>>>>>>");
				creditLeaves(leaveManagementBean);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Update years mapping leaves.
	 *
	 * @param leaveManagementBean
	 *            the leave management bean
	 * @throws Exception
	 *             the exception
	 */
	public void updateYearsMappingLeaves(LeaveManagementBean leaveManagementBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateYearsMappingLeaves(LeaveManagementBean leaveManagementBean)>>>>>>>>>");
		// SQL Queries
		final String checkDate = "select (months_between(to_date(?, 'DD-MON-YYYY'), to_date((select value from configuration_details where name = ?), 'DD-MON-YYYY'))) / "
				+ "((select leave_credits from leave_type_details where leave_type_id = ? and status = 1) * 12) cnt from dual";
		// Code
		Session session = null;
		String count = null;
		try {
			session = hibernateUtils.getSession();
			count = session.createSQLQuery(checkDate).setString(0, leaveManagementBean.getCurrentDate()).setString(1, CPSConstants.LEAVE_SCRIPT_START_DATE)
					.setString(2, leaveManagementBean.getLeaveTypeId()).uniqueResult().toString();
			if (CPSUtils.isInteger(count)) {
				//leaveManagementBean.setExperience("12");
				creditLeaves(leaveManagementBean);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Update service mapping leaves.
	 *
	 * @param leaveManagementBean
	 *            the leave management bean
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void updateServiceMappingLeaves(LeaveManagementBean leaveManagementBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateServiceMappingLeaves(LeaveManagementBean leaveManagementBean)>>>>>");
		// SQL Queries
		final String getEmployees = "select sfid key from (select sfid from emp_master where sfid not in (select sfid from available_leaves where leave_type_id = ?) "
				+ "and status = 1 and ? in ('B', gender) and flag is null) where sfid not in (select sfid from leave_exceptional_employees where status = 1) order by sfid";
		// Code
		Session session = null;
		AvailableLeavesDTO availLeaves = null;
		List<KeyDTO> list = null;
		KeyDTO keyDTO = null;
		try {
			session = hibernateUtils.getSession();
			
			list = session.createSQLQuery(getEmployees).addScalar("key").setString(0, String.valueOf(leaveManagementBean.getLeaveTypeDetails().getId()))
					.setString(1, leaveManagementBean.getEligibilityFlag()).setResultTransformer(Transformers.aliasToBean(KeyDTO.class)).list();
			
			Iterator<KeyDTO> empIt = list.iterator();
			while (empIt.hasNext()) {
				keyDTO = empIt.next();
				//if (keyDTO.getKey().equals("SF0920")) { // Remove if condition only (Testing for single employee)
				if (checkCreditOnCurrentDate(keyDTO.getKey(), CPSConstants.SERVICE, leaveManagementBean.getLeaveTypeDetails())) {
					log.debug("updateServiceMappingLeaves --> Updating service leaves for the employee ----------> " + keyDTO.getKey());
					System.out.println("updateServiceMappingLeaves --> Updating service leaves for the employee ----------> " + keyDTO.getKey());
					if (insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), leaveManagementBean.getLeaveTypeDetails(), Float.parseFloat(leaveManagementBean.getNoOfDays()),
							CPSConstants.AUTORUNSTATUSID, null, leaveManagementBean.getSystemDate(), null, null)) {

						// If the leave type is CCL, then we should assign the CCL to married female employees only
						if (checkMaritalStatus(keyDTO.getKey(), leaveManagementBean.getLeaveTypeDetails().getId())) {
							availLeaves = new AvailableLeavesDTO();
							availLeaves.setSfID(keyDTO.getKey());
							availLeaves.setLeaveTypeDetails(leaveManagementBean.getLeaveTypeDetails());
							availLeaves.setAvailableLeaves(Float.valueOf(leaveManagementBean.getNoOfDays()));
							session.save(availLeaves);
						}
					}
				}
				//} // Remove if condition only (Testing for single employee)
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Checking marital status
	 *
	 * @param sfID
	 * @return
	 * @throws Exception
	 */
	public boolean checkMaritalStatus(final String sfID, final int leaveTypeID) throws Exception {
		// SQL Queries
		final String sql = "select count(*) from family_details where sfid = ? and relation_id in (?, ?) and status = 1";
		// Code
		boolean status = true;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if (leaveTypeID == Integer.valueOf(CPSConstants.CCL)) {
				String count = session.createSQLQuery(sql).setString(0, sfID).setString(1, CPSConstants.SONID).setString(2, CPSConstants.DAUGHTERID).uniqueResult().toString();
				if (CPSUtils.compareStrings(count, "0")) {
					status = false;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	/**
	 * Credit leaves from backend script.
	 *
	 * @param leaveManagementBean
	 *            the leave management bean
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void creditLeaves(LeaveManagementBean leaveManagementBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>creditLeaves(LeaveManagementBean leaveManagementBean)>>>>>>>>>");
		Session session = null;
		List<KeyDTO> list = null;
		AvailableLeavesDTO availLeaves = null;
		AvailableLeavesDTO eol = null;
		float creditLeave = 0.0f;
		AvailableLeavesDTO eolwomc = null;
		String currentDate = leaveManagementBean.getCurrentDate();
		try {
			session = hibernateUtils.getSession();
			// Get the employees other than service, outside employees and exceptional employees
			list = session.createSQLQuery("select sfid key from emp_master where status = 1 and ? in ('B', gender) and sfid not in (select sfid from leave_exceptional_employees "
					+ "where status = 1) and flag is null order by sfid").addScalar("key", Hibernate.STRING).setString(0, leaveManagementBean.getEligibilityFlag())
					.setResultTransformer(Transformers.aliasToBean(KeyDTO.class)).list();

			for (KeyDTO keyDTO : list) {
				//if (CPSUtils.compareStrings(keyDTO.getKey(), "SF0920")) {	// Remove code (Testing for single employee)
				log.debug("creditLeaves() --> Crediting leaves for the employee ----------> " + keyDTO.getKey());
				System.out.println("creditLeaves() --> Crediting leaves for the employee ----------> " + keyDTO.getKey());
				// check whether already inserted on this day or not
				if (checkCreditOnCurrentDate(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), leaveManagementBean.getLeaveTypeDetails())) {
					// update the leave credits
					availLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Restrictions.eq("sfID", keyDTO.getKey()))
							.add(Restrictions.eq("leaveTypeDetails", leaveManagementBean.getLeaveTypeDetails())).uniqueResult();
					if (CPSUtils.isNull(availLeaves)) {
						availLeaves = new AvailableLeavesDTO();
					}
					availLeaves.setNoOfMonths(leaveManagementBean.getLeaveCredits());
					availLeaves.setFromDate(currentDate);
					// get the to date
					if (CPSUtils.compareStrings(String.valueOf(leaveManagementBean.getLeaveDurationDetails().getId()), CPSConstants.DAYSID)) {
						// Add no of days to system date
						availLeaves.setDurationType("D");
						availLeaves.setToDate(CPSUtils.addNoOfDays(availLeaves.getFromDate(), availLeaves.getNoOfMonths()));
					} else if (CPSUtils.compareStrings(String.valueOf(leaveManagementBean.getLeaveDurationDetails().getId()), CPSConstants.MONTHID)) {
						// Add no of months to system date
						availLeaves.setDurationType("M");
						availLeaves.setToDate(CPSUtils.addNoOfMonths(availLeaves.getFromDate(), availLeaves.getNoOfMonths()));
					} else if (CPSUtils.compareStrings(String.valueOf(leaveManagementBean.getLeaveDurationDetails().getId()), CPSConstants.YEARID)) {
						// Add no of months to system date
						availLeaves.setDurationType("Y");
						availLeaves.setToDate(CPSUtils.addNoOfMonths(availLeaves.getFromDate(), String.valueOf(Math.round(Float.parseFloat(availLeaves.getNoOfMonths()) * 12))));
					} else if (CPSUtils.compareStrings(String.valueOf(leaveManagementBean.getLeaveDurationDetails().getId()), CPSConstants.SERVICEID)) {
						availLeaves.setNoOfMonths("0");
						availLeaves.setDurationType("S");
						availLeaves.setToDate(null);
					}
					availLeaves.setSfID(keyDTO.getKey());
					availLeaves.setLeaveTypeDetails(leaveManagementBean.getLeaveTypeDetails());

					// check whether the leave credit type is EL, then we should check the EOL, EOLWOMC that were applied earlier.
					if (leaveManagementBean.getLeaveTypeDetails().getId() == Integer.valueOf(CPSConstants.EL)) { // Credit leave type is EL
						float totalAvailELs = availLeaves.getAvailableLeaves(); // Available leave balance of an employee just before script run process has been started
						float totalELs = availLeaves.getAvailableLeaves() + Float.parseFloat(leaveManagementBean.getNoOfDays()); // Available leave balance of an employee after credit
						// Check whether already EOL is available or not
						eol = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Restrictions.eq("sfID", keyDTO.getKey()))
								.add(Restrictions.eq("leaveTypeID", Integer.valueOf(CPSConstants.EOL))).uniqueResult();
						if (!CPSUtils.isNull(eol) && eol.getAvailableLeaves() > 0) {
							float disleave = eol.getAvailableLeaves() / Float.parseFloat(CPSConstants.ELWITHEOLCONFIGURATION);
							if (totalELs > disleave) {
								insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), eol.getLeaveTypeDetails(), -disleave,
										CPSConstants.AUTORUNSTATUSID, null, leaveManagementBean.getSystemDate(), null, null);
								totalELs = totalELs - disleave;
								eol.setAvailableLeaves(0);
							} else {
								disleave = disleave - totalELs;
								insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), eol.getLeaveTypeDetails(), -totalELs,
										CPSConstants.AUTORUNSTATUSID, null, leaveManagementBean.getSystemDate(), null, null);
								totalELs = 0;
								eol.setAvailableLeaves(disleave * Float.parseFloat(CPSConstants.ELWITHEOLCONFIGURATION));
							}
						}
						// Check whether already EOLWOMC is available or not
						eolwomc = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Restrictions.eq("sfID", keyDTO.getKey()))
								.add(Restrictions.eq("leaveTypeID", Integer.valueOf(CPSConstants.EOLWOMC))).uniqueResult();
						if (!CPSUtils.isNull(eolwomc) && eolwomc.getAvailableLeaves() > 0) {
							float disleave = eolwomc.getAvailableLeaves() / Float.parseFloat(CPSConstants.ELWITHEOLCONFIGURATION);
							if (totalELs > disleave) {
								insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), eolwomc.getLeaveTypeDetails(), -disleave,
										CPSConstants.AUTORUNSTATUSID, null, leaveManagementBean.getSystemDate(), null, null);
								totalELs = totalELs - disleave;
								eolwomc.setAvailableLeaves(0);
							} else {
								disleave = disleave - totalELs;
								insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), eolwomc.getLeaveTypeDetails(), -totalELs,
										CPSConstants.AUTORUNSTATUSID, null, leaveManagementBean.getSystemDate(), null, null);
								totalELs = 0;
								eolwomc.setAvailableLeaves(disleave * Float.parseFloat(CPSConstants.ELWITHEOLCONFIGURATION));
							}
						}

						if (CPSUtils.compareStrings(leaveManagementBean.getMaxShownFlag(), CPSConstants.N)) {
							float nextPending = 0;
							// Corrected by Narayana and Modified by Naresh for previous request
							List<LeaveRequestBean> leaveReqList = new ArrayList<LeaveRequestBean>();
							if (leaveManagementBean.getCurrentDate().contains("Jan") || leaveManagementBean.getCurrentDate().contains("Jul")) {
								leaveReqList = session.createSQLQuery("select to_char(from_date, 'DD-Mon-YYYY') fromDate, to_char(to_date, 'DD-Mon-YYYY') toDate, next_period_debit nextPeriodDebit, request_id requestId "
										+ "from leave_request_details where (FROM_DATE BETWEEN TO_DATE(?) AND (ADD_MONTHS(TO_DATE(?), 6) - 1) OR TO_DATE BETWEEN to_date(?) AND (ADD_MONTHS(to_date(?), 6)) - 1) "
										+ "and next_period_debit > 0 and status not in (?, ?) and convert_ref_id is null and sfid = ? and leave_type_id = ?")
										.addScalar("fromDate", Hibernate.STRING).addScalar("toDate", Hibernate.STRING).addScalar("nextPeriodDebit", Hibernate.FLOAT).addScalar("requestId", Hibernate.STRING)
										.setResultTransformer(Transformers.aliasToBean(LeaveRequestBean.class)).setString(0, leaveManagementBean.getCurrentDate())
										.setString(1, leaveManagementBean.getCurrentDate()).setString(2, leaveManagementBean.getCurrentDate()).setString(3, leaveManagementBean.getCurrentDate())
										.setString(4, CPSConstants.STATUSDECLINED).setString(5, CPSConstants.STATUSCANCELLED).setString(6, keyDTO.getKey())
										.setInteger(7, Integer.valueOf(CPSConstants.EL)).list();
							}
							if (!leaveReqList.isEmpty()) {
								String spellStatus = "FH";
								if (CPSUtils.compareStrings(leaveManagementBean.getCurrentDate().split("-")[1].toUpperCase(), "JAN")) {
									spellStatus = "SH";
								}
								CPSUtils.monthMap();
								int currentMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveManagementBean.getCurrentDate().split("-")[1]));
								for (LeaveRequestBean leaveBean : leaveReqList) {
									int fromMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveBean.getFromDate().split("-")[1]));
									String previousRecordFromDate = spellStatus.equals("SH") ? ((Integer.parseInt(leaveBean.getFromDate().split("-")[2]) < Integer.parseInt(leaveManagementBean.getCurrentDate().split("-")[2]))
											? leaveManagementBean.getCurrentDate() : leaveBean.getFromDate()) : ((fromMonthId < currentMonthId)
													? leaveManagementBean.getCurrentDate() : leaveBean.getFromDate());
									// Insert into Leave Transaction Details
									insertTxnDetails(keyDTO.getKey(), previousRecordFromDate, leaveManagementBean.getLeaveTypeDetails(), -leaveBean.getNextPeriodDebit(),
											CPSConstants.PREVIOUSREQUESTSTATUSID, null, leaveManagementBean.getSystemDate(), leaveBean.getToDate(), leaveBean.getRequestId()); // CPSUtils.addNoOfDays(leaveManagementBean.getSystemDate(), "-1")
									// Insert in leave account
									LeaveDetailsDTO leaveDetails = new LeaveDetailsDTO();
									leaveDetails.setLeaveTypeID(CPSConstants.EL);
									leaveDetails.setType(CPSConstants.REQUEST);
									leaveDetails.setNoOfDays(String.valueOf(leaveBean.getNextPeriodDebit()));
									leaveDetails.setSfID(keyDTO.getKey());
									leaveDetails.setFromDate(leaveBean.getFromDate());
									leaveDetails.setToDate(leaveBean.getToDate());

									updateLeaveAccount(leaveDetails);

									nextPending += leaveBean.getNextPeriodDebit();
								}
							}
							float bal = totalAvailELs;
							if (Float.parseFloat(leaveManagementBean.getMaxLeaves()) < bal) { // Lapse
								float laps = bal - Float.parseFloat(leaveManagementBean.getMaxLeaves());
								insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), leaveManagementBean.getLeaveTypeDetails(), -laps, CPSConstants.AUTORUNSTATUSID, null,
										CPSUtils.addNoOfDays(leaveManagementBean.getSystemDate(), "-1"), null, null);
								// Insert lapse into leave account
								LeaveDetailsDTO leaveDetails = new LeaveDetailsDTO();
								leaveDetails.setLeaveTypeID(CPSConstants.EL);
								leaveDetails.setType(CPSConstants.LAPS);
								leaveDetails.setNoOfDays(String.valueOf((laps)));
								leaveDetails.setSfID(keyDTO.getKey());

								updateLeaveAccount(leaveDetails);

								availLeaves.setAvailableLeaves(Float.parseFloat(leaveManagementBean.getMaxLeaves()) + Float.parseFloat(leaveManagementBean.getNoOfDays()) - nextPending);
								creditLeave = Float.parseFloat(leaveManagementBean.getNoOfDays());
							} else {
								availLeaves.setAvailableLeaves(totalELs - nextPending);
								creditLeave = totalELs - totalAvailELs;
							}
						} else {
							availLeaves.setAvailableLeaves(totalELs);
							creditLeave = totalELs - totalAvailELs;
						}
					} else if (leaveManagementBean.getLeaveTypeDetails().getId() == Integer.valueOf(CPSConstants.CL)) { // Credit Leave type is CL
						float totalCLs = Float.parseFloat(leaveManagementBean.getNoOfDays());
						float nextPending = 0.0f;
						// Added by Narayana
						List<LeaveRequestBean> leaveReqList = session.createSQLQuery("select to_char(from_date, 'DD-Mon-YYYY') fromDate, to_char(to_date, 'DD-Mon-YYYY') toDate, next_period_debit nextPeriodDebit, request_id requestId "
								+ "from leave_request_details WHERE (FROM_DATE BETWEEN TO_DATE(?) AND (ADD_MONTHS(TO_DATE(?), 12) - 1) OR TO_DATE BETWEEN to_date(?) AND (ADD_MONTHS(to_date(?), 12)) - 1) "
								+ "and next_period_debit > 0 and status not in (?, ?) and convert_ref_id is null and sfid = ? and leave_type_id = ?")
								.addScalar("fromDate", Hibernate.STRING).addScalar("toDate", Hibernate.STRING).addScalar("nextPeriodDebit", Hibernate.FLOAT).addScalar("requestId", Hibernate.STRING)
								.setResultTransformer(Transformers.aliasToBean(LeaveRequestBean.class)).setString(0, leaveManagementBean.getCurrentDate())
								.setString(1, leaveManagementBean.getCurrentDate()).setString(2, leaveManagementBean.getCurrentDate()).setString(3, leaveManagementBean.getCurrentDate())
								.setString(4, CPSConstants.STATUSDECLINED).setString(5, CPSConstants.STATUSCANCELLED).setString(6, keyDTO.getKey())
								.setInteger(7, Integer.valueOf(CPSConstants.CL)).list();

						for (LeaveRequestBean leaveBean : leaveReqList) {
							String previousRecordFromDate = (Integer.parseInt(leaveBean.getFromDate().split("-")[2]) < Integer.parseInt(leaveManagementBean.getCurrentDate().split("-")[2])) ? leaveManagementBean.getCurrentDate() : leaveBean.getFromDate();
							// Insert into Leave Transaction Details
							insertTxnDetails(keyDTO.getKey(), previousRecordFromDate, leaveManagementBean.getLeaveTypeDetails(), -leaveBean.getNextPeriodDebit(),
									CPSConstants.PREVIOUSREQUESTSTATUSID, null, leaveManagementBean.getSystemDate(), leaveBean.getToDate(), leaveBean.getRequestId());
							// Insert in leave account
							LeaveDetailsDTO leaveDetails = new LeaveDetailsDTO();
							leaveDetails.setLeaveTypeID(CPSConstants.CL);
							leaveDetails.setType(CPSConstants.REQUEST);
							leaveDetails.setNoOfDays(String.valueOf((leaveBean.getNextPeriodDebit())));
							leaveDetails.setSfID(keyDTO.getKey());
							leaveDetails.setFromDate(leaveBean.getFromDate());
							leaveDetails.setToDate(leaveBean.getToDate());

							updateLeaveAccount(leaveDetails);

							nextPending += leaveBean.getNextPeriodDebit();
						}
						availLeaves.setAvailableLeaves(totalCLs - nextPending);
						creditLeave = totalCLs;
				 	} else {
						/**
						 * If the available leaves more than the maximum leaves, then extra leaves should be lapse
						 */
						if (CPSUtils.compareStrings(leaveManagementBean.getMaxShownFlag(), CPSConstants.N)) {
							// check the max limit
							if (Float.parseFloat(leaveManagementBean.getMaxLeaves()) < availLeaves.getAvailableLeaves()) {
								// Insert lapse leaves into Leave Transaction Details
								insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), leaveManagementBean.getLeaveTypeDetails(), -(Float.valueOf(availLeaves.getAvailableLeaves())
										- Float.valueOf(leaveManagementBean.getMaxLeaves())), CPSConstants.AUTORUNSTATUSID, null, CPSUtils.addNoOfDays(leaveManagementBean.getSystemDate(), "-1"), null, null);
								creditLeave = Float.parseFloat(leaveManagementBean.getNoOfDays());
								availLeaves.setAvailableLeaves(Float.parseFloat(leaveManagementBean.getMaxLeaves()) + Float.parseFloat(leaveManagementBean.getNoOfDays()));
							} else {
								creditLeave = Float.parseFloat(leaveManagementBean.getNoOfDays());
								availLeaves.setAvailableLeaves(availLeaves.getAvailableLeaves() + creditLeave);
							}
						} else {
							creditLeave = Float.parseFloat(leaveManagementBean.getNoOfDays());
							availLeaves.setAvailableLeaves(availLeaves.getAvailableLeaves() + creditLeave);
						}
					}

					if (insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), leaveManagementBean.getLeaveTypeDetails(), creditLeave,
							CPSConstants.AUTORUNSTATUSID, null, leaveManagementBean.getSystemDate(), availLeaves.getToDate(), null)) {
						// Txn details inserted successfully, so we should update the available leaves
						session.flush();
						session.saveOrUpdate(availLeaves);
						session.flush();
						session.clear();
						if (!CPSUtils.isNull(availLeaves)) {
							session.flush();
							if (availLeaves.getLeaveTypeID() != Integer.parseInt(CPSConstants.CL)) {
								availLeaves.setAvailableLeaves(CPSUtils.round(availLeaves.getAvailableLeaves()));
							}
							session.saveOrUpdate(availLeaves);
							session.flush();
							session.clear();
							// insert leave account details
							availLeaves.setLeaveCredits(creditLeave);
							insertLeaveAccountCredits(availLeaves);
						}
						if (!CPSUtils.isNull(eol)) {
							eol.setAvailableLeaves(CPSUtils.round(eol.getAvailableLeaves()));
							session.saveOrUpdate(eol);
							session.flush();
						}
						if (!CPSUtils.isNull(eolwomc)) {
							eolwomc.setAvailableLeaves(CPSUtils.round(eolwomc.getAvailableLeaves()));
							session.saveOrUpdate(eolwomc);
							session.flush();
						}
					}
				}
				//} // Remove code (Testing for single employee)
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Check credit on current date.
	 *
	 * @param sfid
	 *            the sfid
	 * @param currentDate
	 *            the current date
	 * @param leaveTypeDetails
	 *            the leave type details
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean checkCreditOnCurrentDate(final String sfid, final String currentDate, LeaveTypeDTO leaveTypeDetails) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>checkCreditOnCurrentDate(String sfid, String currentDate, LeaveTypeDTO leaveTypeDetails)>>>>>>>>>");
		// SQL Queries
		final String service = "select count(*) from leave_txn_details where sfid = ? and leave_type_id = ? and one_time_entry_flag = 0";
		final String nonService = "select count(*) from leave_txn_details where sfid = ? and to_char(txn_from_date, 'DD-MON-YYYY') = to_char(to_date(?, 'DD-MON-YYYY'), 'DD-MON-YYYY') "
						+ "and leave_type_id = ? and one_time_entry_flag = 0";
		// Code
		Session session = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(currentDate, CPSConstants.SERVICE)) {
				if (Integer.valueOf(session.createSQLQuery(service).setString(0, sfid).setString(1, String.valueOf(leaveTypeDetails.getId())).uniqueResult().toString()) == 0) {
					status = true;
				}
			} else {
				if (Integer.valueOf(session.createSQLQuery(nonService).setString(0, sfid).setString(1, currentDate).setString(2, String.valueOf(leaveTypeDetails.getId())).uniqueResult().toString()) == 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	/**
	 * Insert txn details while auto run.
	 *
	 * @param sfID
	 *            the sf id
	 * @param currentDate
	 *            the current date
	 * @param leaveTypeDetails
	 *            the leave type details
	 * @param noOfDays
	 *            the no of days
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean insertTxnDetails(final String sfID, final String currentDate, LeaveTypeDTO leaveTypeDetails, final float noOfDays, final String txnType, final String createdBy,
			final String creationDate, final String txnToDate, final String referenceId) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>insertTxnDetails(String sfID, String currentDate, LeaveTypeDTO leaveTypeDetails, float noOfDays)>>>>>>>>>");
		Session session = null;
		LeaveTxnDTO leaveTxnDTO = null;
		boolean status = false;
		try {
			session = hibernateUtils.getSession();
			leaveTxnDTO = new LeaveTxnDTO();
			leaveTxnDTO.setLeaveTypeDetails(leaveTypeDetails);
			leaveTxnDTO.setNoOfLeaves(noOfDays);
			leaveTxnDTO.setSfID(sfID);
			leaveTxnDTO.setTxnDate(currentDate);
			leaveTxnDTO.setTxnType(txnType);
			leaveTxnDTO.setCreatedBy(createdBy);
			leaveTxnDTO.setCreationDate(CPSUtils.convertStringToDate(creationDate));
			leaveTxnDTO.setStatus(1);
			leaveTxnDTO.setTxnToDate(txnToDate);
			if ((CPSUtils.compareStrings(CPSConstants.PREVIOUSREQUESTSTATUSID, txnType) || (CPSUtils.compareStrings(CPSConstants.PREVIOUSPERIODLEAVEID, txnType))) 
					&& !CPSUtils.isNullOrEmpty(referenceId)) {
				leaveTxnDTO.setRemarks(referenceId);
			}
			session.save(leaveTxnDTO);
			status = true;
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	/**
	 * New employee availed leaves.
	 *
	 * @param leaveManagementBean
	 *            the leave management bean
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void newEmployeeAvailedLeaves(LeaveManagementBean leaveManagementBean) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>newEmployeeAvailedLeaves(LeaveManagementBean leaveManagementBean)>>>>>>>>>");
		Session session = null;
		AvailableLeavesDTO availLeaves = null;
		List<KeyDTO> list = null;
		KeyDTO keyDTO = null;
		String getEmployees = null;
		float leaveCredits = 0.0f;
		String currentDate = leaveManagementBean.getCurrentDate();
		try {
			session = hibernateUtils.getSession();
			// get the employees other than service & outside employees and also we should not get the exceptional employees
			getEmployees = "select sfid AS key from (select sfid from emp_master where status=1 and sfid not in (select sfid from (select sfid,ROW_NUMBER() OVER (PARTITION BY SFID ORDER BY txn_from_date DESC) rowno "
					+ "from leave_txn_details where ((no_of_leaves=(select ltd.no_of_days from leave_type_details ltd where ltd.leave_type_id=?) and add_months(txn_from_date,?)>to_date(?,'DD-MON-YYYY')) "
					+ "or add_months(txn_from_date,1)>to_date(?,'DD-MON-YYYY')) and leave_type_id=? and one_time_entry_flag=0) where rowno=1 ) and '"
					+ leaveManagementBean.getEligibilityFlag() + "' in ('B',gender) and flag is null) where sfid not in (select sfid from leave_exceptional_employees where status=1)";

			list = session.createSQLQuery(getEmployees).addScalar("key", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyDTO.class)).setString(0, leaveManagementBean.getLeaveTypeId())
					.setString(1, leaveManagementBean.getExperience()).setString(2, leaveManagementBean.getCurrentDate()).setString(3, leaveManagementBean.getCurrentDate())
					.setString(4, leaveManagementBean.getLeaveTypeId()).list();

			Iterator<KeyDTO> empIt = list.iterator();
			while (empIt.hasNext()) {
				keyDTO = empIt.next();
				// check whether already inserted on this day
				if (checkCreditOnCurrentDate(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), leaveManagementBean.getLeaveTypeDetails())) {
					availLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Restrictions.eq("sfID", keyDTO.getKey())).add(
							Restrictions.eq("leaveTypeDetails", leaveManagementBean.getLeaveTypeDetails())).uniqueResult();
					if (CPSUtils.isNull(availLeaves)) {
						availLeaves = new AvailableLeavesDTO();
					}
					availLeaves.setNoOfMonths("1");
					availLeaves.setFromDate(currentDate);
					availLeaves.setToDate(CPSUtils.addNoOfMonths(availLeaves.getFromDate(), availLeaves.getNoOfMonths()));

					/**
					 * If the available leaves more than the maximum leaves, then extra leaves should be lapse
					 */
					if (CPSUtils.compareStrings(leaveManagementBean.getMaxShownFlag(), CPSConstants.N)) {
						// check the max limit
						if (Float.parseFloat(leaveManagementBean.getMaxLeaves()) < availLeaves.getAvailableLeaves()) {
							leaveCredits = Float.parseFloat(leaveManagementBean.getMaxLeaves()) + CPSUtils.convertToFloat(leaveManagementBean.getNewEmpAvailLeaves())
									- availLeaves.getAvailableLeaves();
							availLeaves.setAvailableLeaves(Float.parseFloat(leaveManagementBean.getMaxLeaves()) + CPSUtils.convertToFloat(leaveManagementBean.getNewEmpAvailLeaves()));
						} else {
							leaveCredits = CPSUtils.convertToFloat(leaveManagementBean.getNewEmpAvailLeaves());
							availLeaves.setAvailableLeaves(availLeaves.getAvailableLeaves() + leaveCredits);
						}
					} else {
						leaveCredits = CPSUtils.convertToFloat(leaveManagementBean.getNewEmpAvailLeaves());
						availLeaves.setAvailableLeaves(availLeaves.getAvailableLeaves() + leaveCredits);
					}

					if (CPSUtils.isNull(availLeaves.getSfID())) {
						availLeaves.setSfID(keyDTO.getKey());
						availLeaves.setLeaveTypeDetails(leaveManagementBean.getLeaveTypeDetails());
					}

					if (insertTxnDetails(keyDTO.getKey(), leaveManagementBean.getCurrentDate(), leaveManagementBean.getLeaveTypeDetails(), leaveCredits, CPSConstants.AUTORUNSTATUSID, null,
							leaveManagementBean.getSystemDate(), availLeaves.getToDate(), null)) {
						// Txn details inserted success fully, so we should update the available leaves
						if (!CPSUtils.isNull(availLeaves)) {
							session.saveOrUpdate(availLeaves);
							session.flush();
							// insert leave account details
							availLeaves.setLeaveCredits(leaveCredits);
							availLeaves.setDurationType("M");
							insertLeaveAccountCredits(availLeaves);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Gets the leave details for account.
	 *
	 * @param requestID
	 *            the request id
	 * @param type
	 *            the type
	 * @return the leave details for account
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String getLeaveDetailsForAccount(final String requestID, final String type) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>getLeaveDetailsForAccount(String requestID, String type)>>>>>>>>>");
		String result = CPSConstants.SUCCESS;
		Session session = null;
		LeaveDetailsDTO leaveDetailsDTO = new LeaveDetailsDTO();
		try {
			session = hibernateUtils.getSession();
			leaveDetailsDTO.setType(CPSConstants.REQUEST);
			if (CPSUtils.compareStrings(type, CPSConstants.LEAVE)) {
				LeaveRequestBean leaveBean = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestID)).uniqueResult();
				// If the leave type is EL/HPL/CML/LND/EOL,EOLWOMC then insert into leave account
				if (CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.EL) || CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.HPL)
						|| CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.CML) || CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.LND)
						|| CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.EOL) || CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.EOLWOMC)) {
					leaveDetailsDTO.setSfID(leaveBean.getSfID());
					leaveDetailsDTO.setLeaveTypeID(leaveBean.getLeaveTypeID());
					leaveDetailsDTO.setFromDate(CPSUtils.formattedDate(leaveBean.getFromDate()));
					leaveDetailsDTO.setToDate(CPSUtils.formattedDate(leaveBean.getToDate()));

					if (CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.EL)) {
						leaveDetailsDTO.setNoOfDays(String.valueOf(leaveBean.getPresentPeriodDebit()));
					} else if (CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), String.valueOf(leaveBean.getDebitFrom()))) {
						leaveDetailsDTO.setNoOfDays(String.valueOf(Float.parseFloat(leaveBean.getDebitLeaves()) + Float.parseFloat(leaveBean.getPrevHolidays())
								+ Float.parseFloat(leaveBean.getNextHolidays())));
					} else {
						if (CPSUtils.compareStrings(leaveBean.getLeaveTypeID(), CPSConstants.LND)) {
							leaveDetailsDTO.setNoOfDays(String.valueOf(Float.parseFloat(leaveBean.getDebitLeaves()) + Float.parseFloat(leaveBean.getPrevHolidays())
									+ Float.parseFloat(leaveBean.getNextHolidays())));
						} else {
							LeaveManagementBean leaveMngtBean = (LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(
									Expression.eq("leaveTypeId", leaveBean.getLeaveTypeID())).uniqueResult();
							leaveDetailsDTO.setNoOfDays(String.valueOf((Float.parseFloat(leaveBean.getDebitLeaves()) + Float.parseFloat(leaveBean.getPrevHolidays()) + Float.parseFloat(leaveBean
									.getNextHolidays()))
									/ Float.parseFloat(leaveMngtBean.getDebitMappingLeaves())));
						}
					}

					leaveDetailsDTO.setRequestID(requestID);
					leaveDetailsDTO.setAdditionalData(leaveBean.getAdditionalData());
					updateLeaveAccount(leaveDetailsDTO);
				}
			} else if (CPSUtils.compareStrings(type, CPSConstants.CANCELLEAVEREQ)) {
				CancelLeaveRequestBean cancelReqBean = (CancelLeaveRequestBean) session.createCriteria(CancelLeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestID))
						.uniqueResult();
				leaveDetailsDTO.setLeaveTypeID(cancelReqBean.getReferenceDetails().getLeaveTypeID());
				/**
				 * If the leave type is EL, EOL, HPL, CML & LND then we should update the status to 0
				 */
				if (CPSUtils.compareStrings(leaveDetailsDTO.getLeaveTypeID(), CPSConstants.EL) || CPSUtils.compareStrings(leaveDetailsDTO.getLeaveTypeID(), CPSConstants.HPL)
						|| CPSUtils.compareStrings(leaveDetailsDTO.getLeaveTypeID(), CPSConstants.CML) || CPSUtils.compareStrings(leaveDetailsDTO.getLeaveTypeID(), CPSConstants.LND)
						|| CPSUtils.compareStrings(leaveDetailsDTO.getLeaveTypeID(), CPSConstants.EOL) || CPSUtils.compareStrings(leaveDetailsDTO.getLeaveTypeID(), CPSConstants.EOLWOMC)) {
					// We should insert new Row

					leaveDetailsDTO.setSfID(cancelReqBean.getReferenceDetails().getSfID());
					leaveDetailsDTO.setFromDate(cancelReqBean.getReferenceDetails().getStrFromDate());
					leaveDetailsDTO.setToDate(cancelReqBean.getReferenceDetails().getStrToDate());

					leaveDetailsDTO.setNoOfDays("-"
							+ String.valueOf(Float.parseFloat(cancelReqBean.getReferenceDetails().getDebitLeaves()) + Float.parseFloat(cancelReqBean.getReferenceDetails().getPrevHolidays())
									+ Float.parseFloat(cancelReqBean.getReferenceDetails().getNextHolidays())));

					leaveDetailsDTO.setRequestID(requestID);
					leaveDetailsDTO.setAdditionalData(cancelReqBean.getReferenceDetails().getAdditionalData());

					// updateLeaveAccountStatus(leaveDetailsDTO);
					updateLeaveAccount(leaveDetailsDTO);
				}
			} else if (CPSUtils.compareStrings(type, CPSConstants.CONVERTLEAVEREQ)) {
				List<ConvertLeaveRequestBean> convList = session.createCriteria(ConvertLeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(requestID))).list();
				ConvertLeaveRequestBean convLeaveBean = null;
				Iterator<ConvertLeaveRequestBean> leave = convList.iterator();
				String leaveTypeID = null;
				String referenceID = null;
				while (leave.hasNext()) {
					convLeaveBean = leave.next();

					if (CPSUtils.isNullOrEmpty(referenceID)) {
						referenceID = String.valueOf(convLeaveBean.getReferenceID());
						leaveTypeID = convLeaveBean.getReferenceDetails().getLeaveTypeID();

						/**
						 * If the leave type is EL, EOL, HPL, CML & LND then we should update the status to 0
						 */
						if (CPSUtils.compareStrings(leaveTypeID, CPSConstants.EL) || CPSUtils.compareStrings(leaveTypeID, CPSConstants.HPL) || CPSUtils.compareStrings(leaveTypeID, CPSConstants.CML)
								|| CPSUtils.compareStrings(leaveTypeID, CPSConstants.LND) || CPSUtils.compareStrings(leaveTypeID, CPSConstants.EOL)
								|| CPSUtils.compareStrings(leaveTypeID, CPSConstants.EOLWOMC)) {
							leaveDetailsDTO.setRequestID(convLeaveBean.getReferenceDetails().getRequestID());
							updateLeaveAccountStatus(leaveDetailsDTO);
						}
					}
					// If the leave type is EL/HPL/CML/LND/EOL,EOLWOMC then insert into leave account
					if (CPSUtils.compareStrings(convLeaveBean.getConvertedTo(), CPSConstants.EL) || CPSUtils.compareStrings(convLeaveBean.getConvertedTo(), CPSConstants.HPL)
							|| CPSUtils.compareStrings(convLeaveBean.getConvertedTo(), CPSConstants.CML) || CPSUtils.compareStrings(convLeaveBean.getConvertedTo(), CPSConstants.LND)
							|| CPSUtils.compareStrings(convLeaveBean.getConvertedTo(), CPSConstants.EOL) || CPSUtils.compareStrings(convLeaveBean.getConvertedTo(), CPSConstants.EOLWOMC)) {

						leaveDetailsDTO.setSfID(convLeaveBean.getSfID());
						leaveDetailsDTO.setLeaveTypeID(convLeaveBean.getConvertedTo());
						leaveDetailsDTO.setFromDate(CPSUtils.formattedDate(convLeaveBean.getFromDate()));
						leaveDetailsDTO.setToDate(CPSUtils.formattedDate(convLeaveBean.getToDate()));
						leaveDetailsDTO.setNoOfDays(convLeaveBean.getNoOfDays());
						leaveDetailsDTO.setRequestID(requestID);

						updateLeaveAccount(leaveDetailsDTO);
					}
				}
			}
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * Update leave account status.
	 *
	 * @param leaveDetails
	 *            the leave details
	 * @throws Exception
	 *             the exception
	 */
	public void updateLeaveAccountStatus(LeaveDetailsDTO leaveDetails) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateLeaveAccountStatus(LeaveDetailsDTO leaveDetails)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LeaveAccountDTO leaveAccount = (LeaveAccountDTO) session.createCriteria(LeaveAccountDTO.class).add(Expression.eq(CPSConstants.REQUESTID, leaveDetails.getRequestID())).uniqueResult();
			if (!CPSUtils.isNull(leaveAccount)) {
				leaveAccount.setStatus("0");
				leaveAccount.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());

				session.saveOrUpdate(leaveAccount);
				session.flush();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Insert leave account credits.
	 *
	 * @param availLeave
	 *            the avail leave
	 * @throws Exception
	 *             the exception
	 */
	public void insertLeaveAccountCredits(AvailableLeavesDTO availLeave) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>insertLeaveAccountCredits(AvailableLeavesDTO availLeave)>>>>>>>>>");
		LeaveDetailsDTO leaveDetails = new LeaveDetailsDTO();
		try {
			leaveDetails.setSfID(availLeave.getSfID());
			leaveDetails.setFromDate(availLeave.getFromDate());
			leaveDetails.setToDate(availLeave.getToDate());
			leaveDetails.setNoOfDays(String.valueOf(availLeave.getLeaveCredits()));
			leaveDetails.setLeaveTypeID(String.valueOf(availLeave.getLeaveTypeDetails().getId()));
			leaveDetails.setNoOfMonths(availLeave.getNoOfMonths() + availLeave.getDurationType());
			leaveDetails.setType(CPSConstants.CREDIT);

			updateLeaveAccount(leaveDetails);

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Update leave account.
	 *
	 * @param leaveDetails
	 *            the leave details
	 * @throws Exception
	 *             the exception
	 */
	public void updateLeaveAccount(LeaveDetailsDTO leaveDetails) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateLeaveAccount(LeaveDetailsDTO leaveDetails)>>>>>>>>>");
		try {
			/**
			 * If the leave type is EL, HPL, CML, LND, EOL & EOLWOMC then we should insert
			 */
			if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EL) || CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.HPL)
					|| CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.CML) || CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.LND)
					|| CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOL) || CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOLWOMC)) {
				HashMap<String, String> leaveMap = createLeaveAccountMap();
				leaveMap.put("sfID", leaveDetails.getSfID());

				LeaveAccountDTO leaveAccount = getLastTxnBalance(leaveDetails.getSfID(), leaveDetails.getLeaveTypeID());

				if (CPSUtils.isNull(leaveAccount)) {
					leaveAccount = new LeaveAccountDTO();
					leaveAccount.setEleven("0");
					leaveAccount.setThirtyfive("0");
					leaveMap.put("eleven", leaveAccount.getEleven());
					leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
				}
				if (CPSUtils.compareStrings(leaveDetails.getType(), CPSConstants.REQUEST)) {
					// Check whether already request was inserted or not
					if (checkRequestInLeaveAccount(leaveDetails.getSfID(), leaveDetails.getRequestID())) {
						System.out.println("SFID:" + leaveDetails.getSfID());
						leaveMap.put("requestID", leaveDetails.getRequestID());
						if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EL)) {
							leaveMap.put("eight", leaveDetails.getFromDate());
							leaveMap.put("nine", leaveDetails.getToDate());
							leaveMap.put("ten", leaveDetails.getNoOfDays());

							// Deduct EL's
							leaveMap.put("eleven", String.valueOf(Float.parseFloat(leaveAccount.getEleven()) - Float.parseFloat(leaveDetails.getNoOfDays())));
							leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
						} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.HPL)) {
							leaveMap.put("seventeen", leaveDetails.getFromDate());
							leaveMap.put("eighteen", leaveDetails.getToDate());
							leaveMap.put("nineteen", leaveDetails.getNoOfDays());

							// Deduct HPL's
							leaveMap.put("eleven", leaveAccount.getEleven());
							leaveMap.put("thirtyfive", String.valueOf(Float.parseFloat(leaveAccount.getThirtyfive()) - Float.parseFloat(leaveDetails.getNoOfDays())));
						} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.CML)) {
							if (CPSUtils.compareStrings(leaveDetails.getAdditionalData(), CPSConstants.WOMC)) {
								leaveMap.put("twentythree", leaveDetails.getFromDate());
								leaveMap.put("twentyfour", leaveDetails.getToDate());
								leaveMap.put("twentyfive", leaveDetails.getNoOfDays());
							} else {
								leaveMap.put("twenty", leaveDetails.getFromDate());
								leaveMap.put("twentyone", leaveDetails.getToDate());
								leaveMap.put("twentytwo", leaveDetails.getNoOfDays());
							}
							// Deduct HPL's
							leaveMap.put("eleven", leaveAccount.getEleven());
							leaveMap.put("thirtyfive", String.valueOf(Float.parseFloat(leaveAccount.getThirtyfive()) - Float.parseFloat(leaveDetails.getNoOfDays())));
						} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.LND)) {
							if (CPSUtils.compareStrings(leaveDetails.getAdditionalData(), CPSConstants.WOMC)) {
								leaveMap.put("thirty", leaveDetails.getFromDate());
								leaveMap.put("thirtyone", leaveDetails.getToDate());
								leaveMap.put("thirtytwo", leaveDetails.getNoOfDays());
							} else {
								leaveMap.put("twentyseven", leaveDetails.getFromDate());
								leaveMap.put("twentyeight", leaveDetails.getToDate());
								leaveMap.put("twentynine", leaveDetails.getNoOfDays());
							}
							// Deduct HPL's
							leaveMap.put("eleven", leaveAccount.getEleven());
							leaveMap.put("thirtyfive", String.valueOf(Float.parseFloat(leaveAccount.getThirtyfive()) - Float.parseFloat(leaveDetails.getNoOfDays())));
						} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOL)) {
							leaveMap.put("thirtysix", "EOL-" + leaveDetails.getFromDate() + " to " + leaveDetails.getToDate());
							leaveMap.put("five", leaveDetails.getNoOfDays());
							leaveMap.put("six", String.valueOf(Float.valueOf(leaveDetails.getNoOfDays()) / 10));
							// Deduct EL's
							leaveMap.put("eleven", String.valueOf(Float.parseFloat(leaveAccount.getEleven()) - (Float.valueOf(leaveDetails.getNoOfDays()) / 10)));
							leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
						} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOLWOMC)) {
							leaveMap.put("thirtysix", "EOL-" + leaveDetails.getFromDate() + " to " + leaveDetails.getToDate());
							leaveMap.put("five", leaveDetails.getNoOfDays());
							leaveMap.put("six", String.valueOf(Float.valueOf(leaveDetails.getNoOfDays()) / 10));
							// Deduct EL's
							leaveMap.put("eleven", String.valueOf(Float.parseFloat(leaveAccount.getEleven()) - (Float.valueOf(leaveDetails.getNoOfDays()) / 10)));
							leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
						}
					}
				} else if (CPSUtils.compareStrings(leaveDetails.getType(), CPSConstants.CREDIT)) {
					if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EL)) {
						leaveMap.put("one", leaveDetails.getFromDate());
						leaveMap.put("two", leaveDetails.getToDate());
						leaveMap.put("three", leaveDetails.getNoOfMonths());
						leaveMap.put("four", leaveDetails.getNoOfDays());

						// Credit EL's
						leaveMap.put("eleven", String.valueOf(Float.parseFloat(leaveAccount.getEleven()) + Float.parseFloat(leaveDetails.getNoOfDays())));
						leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
					} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.HPL)) {
						leaveMap.put("twelve", leaveDetails.getNoOfMonths());
						leaveMap.put("thirteen", leaveDetails.getNoOfDays());
						// Credit HPL's
						leaveMap.put("eleven", leaveAccount.getEleven());
						leaveMap.put("thirtyfive", String.valueOf(Float.parseFloat(leaveAccount.getThirtyfive()) + Float.parseFloat(leaveDetails.getNoOfDays())));
					} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOL) || CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOLWOMC)) {
						leaveMap.put("five", leaveDetails.getNoOfDays());
						leaveMap.put("six", String.valueOf(Float.valueOf(leaveDetails.getNoOfDays()) / 10));
						// Deduct EL's
						leaveMap.put("eleven", String.valueOf(Float.parseFloat(leaveAccount.getEleven()) - (Float.valueOf(leaveDetails.getNoOfDays()) / 10)));
						leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
					}
				} else if (CPSUtils.compareStrings(leaveDetails.getType(), CPSConstants.BALANCE)) {
					if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EL)) {
						leaveMap.put("eleven", leaveDetails.getNoOfDays());
						leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
					} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.HPL)) {
						leaveMap.put("eleven", leaveAccount.getEleven());
						leaveMap.put("thirtyfive", leaveDetails.getNoOfDays());
					} else if (CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOL) || CPSUtils.compareStrings(leaveDetails.getLeaveTypeID(), CPSConstants.EOLWOMC)) {
						leaveMap.put("five", leaveDetails.getNoOfDays());
						leaveMap.put("six", String.valueOf(Float.valueOf(leaveDetails.getNoOfDays()) / 10));

						leaveMap.put("eleven", String.valueOf(Float.parseFloat(leaveAccount.getEleven()) - (Float.valueOf(leaveDetails.getNoOfDays()) / 10)));
						leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
					}
				} else if (CPSUtils.compareStrings(leaveDetails.getType(), CPSConstants.LAPS)) {
					leaveMap.put("three", "L");
					leaveMap.put("four", "-" + leaveDetails.getNoOfDays());

					// Deduct EL's
					leaveMap.put("eleven", String.valueOf(Float.parseFloat(leaveAccount.getEleven()) - Float.valueOf(leaveDetails.getNoOfDays())));
					leaveMap.put("thirtyfive", leaveAccount.getThirtyfive());
				}
				if (!CPSUtils.isNullOrEmpty(leaveDetails.getCreationDate())) {
					leaveMap.put("creationDate", leaveDetails.getCreationDate());
				}
				System.out.println("from updateLeaveAccount(): " + leaveAccount.getSfID());
				insertLeaveAccountDetails(leaveMap);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public LeaveAccountDTO getLastTxnBalance(final String sfID, final String leaveTypeID) throws Exception {
		Session session = null;
		LeaveAccountDTO leaveAccount = null;
		try {
			session = hibernateUtils.getSession();

			Object id = session.createCriteria(LeaveAccountDTO.class).add(Expression.eq(CPSConstants.STATUS, "1")).add(Expression.eq("sfID", sfID)).setProjection(Projections.max("id")).uniqueResult();
			//System.out.println(">>>>>>>>>>>>max id>>>> : " + id);
			if (!CPSUtils.isNullOrEmpty(id)) {
				leaveAccount = (LeaveAccountDTO) session.get(LeaveAccountDTO.class, Integer.valueOf(id.toString()));
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveAccount;
	}

	public boolean checkRequestInLeaveAccount(final String sfID, final String requestID) throws Exception {
		Session session = null;
		boolean status = true;
		try {
			session = hibernateUtils.getSession();

			String cnt = session.createSQLQuery("select count(*) from leave_account where status=1 and sfid=? and request_id=? and upper(to_char(creation_date,'DD-MON-YYYY'))!=upper(?)").setString(0,
					sfID).setString(1, requestID).setString(2, CPSUtils.getCurrentDate()).uniqueResult().toString();
			if (!CPSUtils.compareStrings(cnt, "0")) {
				status = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return status;
	}

	/**
	 * Creates the leave account map.
	 *
	 * @return the hash map
	 * @throws Exception
	 *             the exception
	 */
	public static HashMap<String, String> createLeaveAccountMap() throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>createLeaveAccountMap()>>>>>>>>>");
		HashMap<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			map.put("requestID", null);
			map.put("sfID", null);
			map.put("one", null);
			map.put("two", null);
			map.put("three", null);
			map.put("four", null);
			map.put("five", null);
			map.put("six", null);
			map.put("seven", null);
			map.put("eight", null);
			map.put("nine", null);
			map.put("ten", null);
			map.put("eleven", "0");//added by Narayana
			map.put("twelve", null);
			map.put("thirteen", null);
			map.put("fourteen", null);
			map.put("fifteen", null);
			map.put("sixteen", null);
			map.put("seventeen", null);
			map.put("eighteen", null);
			map.put("nineteen", null);
			map.put("twenty", null);
			map.put("twentyone", null);
			map.put("twentytwo", null);
			map.put("twentythree", null);
			map.put("twentyfour", null);
			map.put("twentyfive", null);
			map.put("twentysix", null);
			map.put("twentyseven", null);
			map.put("twentyeight", null);
			map.put("twentynine", null);
			map.put("thirty", null);
			map.put("thirtyone", null);
			map.put("thirtytwo", null);
			map.put("thirtythree", null);
			map.put("thirtyfour", null);
			map.put("thirtyfive", "0");//added by Narayana
			map.put("thirtysix", null);
			map.put("creationDate", null);

		} catch (Exception e) {
			throw e;
		}
		return map;
	}

	/**
	 * Insert leave account details.
	 *
	 * @param map
	 *            the map
	 * @throws Exception
	 *             the exception
	 */
	public void insertLeaveAccountDetails(HashMap<String, String> map) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>insertLeaveAccountDetails(HashMap<String, String> map)>>>>>>>>>");
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			LeaveAccountDTO leaveAccount = new LeaveAccountDTO();
			leaveAccount.setRequestID(map.get(CPSConstants.REQUESTID));
			leaveAccount.setSfID(map.get("sfID"));
			leaveAccount.setStatus("1");
			leaveAccount.setOne(map.get("one"));
			leaveAccount.setTwo(map.get("two"));
			leaveAccount.setThree(map.get("three"));
			leaveAccount.setFour(map.get("four"));
			leaveAccount.setFive(map.get("five"));
			leaveAccount.setSix(map.get("six"));
			leaveAccount.setSeven(map.get("seven"));
			leaveAccount.setEight(map.get("eight"));
			leaveAccount.setNine(map.get("nine"));
			leaveAccount.setTen(map.get("ten"));
			leaveAccount.setEleven(map.get("eleven"));
			leaveAccount.setTwelve(map.get("twelve"));
			leaveAccount.setThirteen(map.get("thirteen"));
			leaveAccount.setFourteen(map.get("fourteen"));
			leaveAccount.setFifteen(map.get("fifteen"));
			leaveAccount.setSixteen(map.get("sixteen"));
			leaveAccount.setSeventeen(map.get("seventeen"));
			leaveAccount.setEighteen(map.get("eighteen"));
			leaveAccount.setNineteen(map.get("nineteen"));
			leaveAccount.setTwenty(map.get("twenty"));
			leaveAccount.setTwentyone(map.get("twentyone"));
			leaveAccount.setTwentyone(map.get("twentyone"));
			leaveAccount.setTwentytwo(map.get("twentytwo"));
			leaveAccount.setTwentythree(map.get("twentythree"));
			leaveAccount.setTwentyfour(map.get("twentyfour"));
			leaveAccount.setTwentyfive(map.get("twentyfive"));
			leaveAccount.setTwentysix(map.get("twentysix"));
			leaveAccount.setTwentyseven(map.get("twentyseven"));
			leaveAccount.setTwentyeight(map.get("twentyeight"));
			leaveAccount.setTwentynine(map.get("twentynine"));
			leaveAccount.setThirty(map.get("thirty"));
			leaveAccount.setThirtyone(map.get("thirtyone"));
			leaveAccount.setThirtytwo(map.get("thirtytwo"));
			leaveAccount.setThirtythree(map.get("thirtythree"));
			leaveAccount.setThirtyfour(map.get("thirtyfour"));
			leaveAccount.setThirtyfive(map.get("thirtyfive"));
			leaveAccount.setThirtysix(map.get("thirtysix"));
			if (!CPSUtils.isNullOrEmpty(map.get("creationDate"))) {
				leaveAccount.setCreationDate(CPSUtils.convertStringToDate(map.get("creationDate")));
			} else {
				leaveAccount.setCreationDate(CPSUtils.getCurrentDateWithTime());
			}
			leaveAccount.setLastModifiedDate(leaveAccount.getCreationDate());
			System.out.println("from insertLeaveAccountDetails(): " + map.get("sfID"));
			session.saveOrUpdate(leaveAccount);
			session.flush();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Run year script.
	 *
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String runYearScript(String date) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>runYearScript()>>>>>>>>>");
		String result = null;
		// Session session = null;
		try {
			// session = hibernateUtils.getSession();
			// hibernateUtils.beginTransaction(session);

			// backupCurrentYearLeaves(getNewYearID(date.split("-")[2]));
			// resetYearSpells();
			// resetCLLeaves();
			// //session.close();
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	// public int getNewYearID(final String year) throws Exception {
	// log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>runYearScript()>>>>>>>>>");
	// int result = 0;
	// Session session = null;
	// try {
	// session = hibernateUtils.getSession();
	//
	// YearTypeDTO yearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.NAME, year)).uniqueResult();
	//
	// if (CPSUtils.isNull(yearDTO)) {
	// // Insert
	// yearDTO = new YearTypeDTO();
	// yearDTO.setName(year);
	// yearDTO.setStatus(1);
	// yearDTO.setCreationDate(CPSUtils.getCurrentDate());
	// yearDTO.setLastModifiedDate(yearDTO.getCreationDate());
	// session.save(yearDTO);
	// }
	// result = yearDTO.getId();
	// } catch (Exception e) {
	// throw e;
	// }
	// return result;
	// }

	/**
	 * Reset the CL leaves to 0
	 *
	 * @throws Exception
	 */
	public void resetCLLeaves() throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession(); // Remove the following code : and sfid = ? .setString(1, "SF0781") (Testing for single employee)
			session.createSQLQuery("update available_leaves set available_leaves = 0 where leave_type_id = ?").setString(0, CPSConstants.CL).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Reset year spells.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void resetYearSpells() throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>resetYearSpells()>>>>>>>>>");
		Session session = null;
		List<EmpLeaveSpellsDTO> empSpellsList = null;
		try {
			session = hibernateUtils.getSession();

			empSpellsList = session.createCriteria(EmpLeaveSpellsDTO.class).add(Restrictions.or(Restrictions.eq("leaveTypeID", Integer.valueOf(CPSConstants.CCL)), Restrictions.eq("leaveTypeID", Integer.valueOf(CPSConstants.SL)))).addOrder(Order.asc("sfID")).list();

			for (EmpLeaveSpellsDTO empSpellsDTO : empSpellsList) {
				//if (empSpellsDTO.getSfID().equals("SF0920")) { // Remove if condition only (Testing for single employee)
				log.debug("resetYearSpells() --> resetting year spells for the employee ----------> " + empSpellsDTO.getSfID());
				System.out.println("resetYearSpells() --> resetting year spells for the employee ----------> " + empSpellsDTO.getSfID());
				empSpellsDTO.setYearSpellsCount(0);
				session.saveOrUpdate(empSpellsDTO);
				session.flush();
				
				String result = (String) session.createSQLQuery("SELECT GET_EMP_YEAR_SPELLS(?, ?) FROM DUAL").setString(0, empSpellsDTO.getSfID())
						.setInteger(1, empSpellsDTO.getLeaveTypeID()).uniqueResult();
				if (Integer.parseInt(result) != 0) {
					empSpellsDTO.setYearSpellsCount(Integer.parseInt(result));
					session.saveOrUpdate(empSpellsDTO);
					session.flush();
				}
				//} // Remove if condition only (Testing for single employee)
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Adds the leave balance to current year.
	 *
	 * @throws Exception
	 *             the exception
	 */
	// public void backupCurrentYearLeaves(final int yearID) throws Exception {
	// log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>addLeaveBalanceToCurrentYear()>>>>>>>>>");
	// Session session = null;
	// List<AvailableLeavesDTO> leaves = null;
	// try {
	// session = hibernateUtils.getSession();
	//
	// leaves = session.createCriteria(AvailableLeavesDTO.class).list();
	// for (AvailableLeavesDTO row : leaves) {
	// row.setYearID(yearID);
	// if (leaveBackUpCheck(row)) {
	// insertLeaveBackup(row);
	// }
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	// }
	// public boolean leaveBackUpCheck(AvailableLeavesDTO leave) throws Exception {
	// boolean status = false;
	// Session session = null;
	// try {
	// session = hibernateUtils.getSession();
	//
	// LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leave.getSfID())).add(
	// Expression.eq("leaveTypeID", leave.getLeaveTypeID())).add(Expression.eq("yearID", leave.getYearID())).uniqueResult();
	//
	// if (CPSUtils.isNull(leaveYearlyBalanceDTO)) {
	// status = true;
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	// return status;
	// }
	//
	// public void insertLeaveBackup(AvailableLeavesDTO leave) throws Exception {
	// Session session = null;
	// try {
	// session = hibernateUtils.getSession();
	//
	// LeaveYearlyBalanceDTO yearlyBalance = new LeaveYearlyBalanceDTO();
	// yearlyBalance.setSfID(leave.getSfID());
	// yearlyBalance.setLeaveTypeID(leave.getLeaveTypeID());
	// yearlyBalance.setBalance(leave.getAvailableLeaves());
	// yearlyBalance.setYearID(leave.getYearID());
	// yearlyBalance.setStatus(1);
	// session.saveOrUpdate(yearlyBalance);
	// } catch (Exception e) {
	// throw e;
	// }
	// }
	public LeaveApplicationBean checkNoOfLeaves(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();

			if (CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.EL)
					|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.HPL)
					|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.CL)
					|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.CCL)
					|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.SL)) {
				String result = session.createSQLQuery("select case when ?='Y' then (select case when available_leaves>=? then 'true' else 'false' end from available_leaves "
						+ "where sfid=? and leave_type_id=?) when ?='N' then (select case when available_leaves>=? then 'true' else 'false' end from available_leaves "
						+ "where sfid=? and leave_type_id=?) else 'true' end result from dual").setString(0, leaveBean.getSelectedLeaveDetails().getDirectDebitFlag())
						.setString(1, String.valueOf(Float.valueOf(leaveBean.getNoOfDays()) + CPSUtils.convertToFloat(leaveBean.getPrevHolidays()) + CPSUtils.convertToFloat(leaveBean.getNextHolidays())))
						.setString(2, leaveBean.getSfID()).setString(3, leaveBean.getSelectedLeaveDetails().getLeaveTypeId()).setString(4, leaveBean.getSelectedLeaveDetails().getDirectDebitFlag())
						.setString(5, String.valueOf((Float.valueOf(leaveBean.getNoOfDays()) + CPSUtils.convertToFloat(leaveBean.getPrevHolidays()) + CPSUtils.convertToFloat(leaveBean.getNextHolidays()))
							* CPSUtils.convertToFloat(leaveBean.getSelectedLeaveDetails().getDebitMappingLeaves()))).setString(6, leaveBean.getSfID())
						.setString(7, leaveBean.getSelectedLeaveDetails().getDebitMappingID()).uniqueResult().toString();

				if (CPSUtils.compareStrings(result, CPSConstants.FALSE)) {
					leaveBean.setRemarks(CPSConstants.INSUFFIENTLEAVES);
					leaveBean.setResult(CPSConstants.FAILED);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	/**
	 * Decline the leave cancel request, whenever decline the leave request
	 *
	 * @param rb
	 * @return
	 * @throws Exception
	 */
	public String declineLeaveCancelRequest(RequestBean rb) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>declineLeaveCancelRequest(String requestID)>>>>>>>>>");
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();
			LeaveRequestBean leaveRequestDetails = (LeaveRequestBean) session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, rb.getRequestID())).uniqueResult();
			if (!CPSUtils.isNull(leaveRequestDetails)) {
				CancelLeaveRequestBean cancelLeaveReq = (CancelLeaveRequestBean) session.createCriteria(CancelLeaveRequestBean.class).add(
						Expression.eq(CPSConstants.REFERENCEID, leaveRequestDetails.getId())).uniqueResult();
				if (!CPSUtils.isNull(cancelLeaveReq)) {
					cancelLeaveReq.setStatus(Integer.valueOf(CPSConstants.STATUSDECLINED));
					session.saveOrUpdate(cancelLeaveReq);
					session.flush();
					// decline the request in the workflow
					String historyID = session.createSQLQuery("select max(id) from request_workflow_history where request_id=?").setString(0, cancelLeaveReq.getRequestID()).uniqueResult().toString();
					txRequestProcess.declinedRequest(historyID, rb.getIpAddress(), rb.getRemarks(), rb.getType(), CPSConstants.STATUSDECLINED);
				}
				
				rb.setMessage("declined");
				txRequestProcess.sendMail(rb);
			}
			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public String checkLeaveAttachedToLtc(String requestID) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>checkLeaveAttachedToLtc(String requestID)>>>>>>>>>");
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();
			if (Integer.parseInt((String) session.createSQLQuery("select count(*)||'' from (select leave_request_id from ltc_request_details where "
					+ "leave_request_id is not null and status not in(?,?) and leave_request_id=? and advance_flag is null  union select leave_request_id "
					+ "from ltc_advance_request_details where leave_request_id is not null and status not in(?,?) and leave_request_id=?)").setString(0, CPSConstants.STATUSCANCELLED)
					.setString(1, CPSConstants.STATUSDECLINED).setString(2, requestID).setString(3, CPSConstants.STATUSCANCELLED).setString(4, CPSConstants.STATUSDECLINED)
					.setString(5, requestID).uniqueResult()) > 0) {
				result = CPSConstants.LTC_LEAVE_REMARKS;
				//		session.createSQLQuery("update request_workflow_history set status=?,stage_status=? where id=(select max(id) from request_workflow_history where request_id=?)").setString(0, CPSConstants.STATUSPENDING).setString(1, CPSConstants.STATUSPENDING).setString(2, requestID).executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public String updateConvertedLeaveRequestStatus(final String requestID,final String status) throws Exception {
		log.debug("<<<<<LeaveRequestProcess<<<<<<Method>>>>>>>>>>>>>>>updateConvertedLeaveRequestStatus(String requestID)>>>>>>>>>");
		String result = null;
		Session session = null;
		LeaveRequestBean leaveBean = null;
		ConvertLeaveRequestBean convertBean = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean = (LeaveRequestBean)session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, requestID)).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(leaveBean.getConvertedLeaveDetails())) {
				convertBean = (ConvertLeaveRequestBean)session.get(ConvertLeaveRequestBean.class,leaveBean.getConvertedLeaveDetails().getId());
				convertBean.setStatus(Integer.valueOf(status));
				convertBean.setFromDate(CPSUtils.formattedDate(convertBean.getFromDate()));
				convertBean.setToDate(CPSUtils.formattedDate(convertBean.getToDate()));
				session.saveOrUpdate(convertBean);
				session.flush();
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public String updateFileStatus(LeaveApplicationBean leaveApplicationBean) throws Exception {
		Session session = null;
		LeaveRequestBean leaveBean = null;
		try{
			//StringBuffer sb = new StringBuffer();
			//sb.append("update LeaveRequestBean set ");
			session = hibernateUtils.getSession();
			leaveBean = (LeaveRequestBean)session.createCriteria(LeaveRequestBean.class).add(Expression.eq(CPSConstants.REQUESTID, leaveApplicationBean.getRequestID())).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(leaveApplicationBean.getMcFile())){
				//sb.append(" medicalCertName="+leaveApplicationBean.getMedicalCertName());
				leaveBean.setMedicalCertName(leaveApplicationBean.getMedicalCertName());
			}
			if(!CPSUtils.isNullOrEmpty(leaveApplicationBean.getFitnessFile())){
				//sb.append(" ,fitnessCertName="+leaveApplicationBean.getFitnessCertName());
				leaveBean.setFitnessCertName(leaveApplicationBean.getFitnessCertName());
			}
			if(!CPSUtils.isNullOrEmpty(leaveApplicationBean.getOtherCertFile())){
				//sb.append(" ,otherCertName="+leaveApplicationBean.getOtherCertName());
				leaveBean.setOtherCertName(leaveApplicationBean.getOtherCertName());
			}
			//sb.append(" where requestID="+leaveBean.getRequestID());
			//session.createQuery(sb.toString()).executeUpdate();
			session.update(leaveBean);
			session.flush();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return CPSConstants.SUCCESS;
	}

	// Added by Narayana and Modified by Naresh
	public LeaveRequestBean newAssignPeriodDebits(LeaveRequestBean leaveRequestBean, final String periodType, final int type, final int type1) throws Exception {
		Session session = null;
		String resultDays = null;
		LeaveYearlyBalanceDTO leaveYearlyBalanceFromDTO = null, leaveYearlyBalanceToDTO = null;
		try {
			session = hibernateUtils.getSession();
			resultDays = (String) session.createSQLQuery("select get_leave_period_days(?, ?, ?, ?, ?, ?) from dual").setString(0, periodType).setString(1, leaveRequestBean.getFromDate())
					.setString(2, CPSUtils.getCurrentDate()).setString(3, leaveRequestBean.getToDate()).setInteger(4, Integer.valueOf(leaveRequestBean.getPrevHolidays()))
					.setInteger(5, Integer.valueOf(leaveRequestBean.getNextHolidays())).uniqueResult();

			float fdays = Integer.parseInt(resultDays.split("-")[0]);
			float tdays = Integer.parseInt(resultDays.split("-")[1]);
			
			YearTypeDTO fromYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveRequestBean.getFromDate().split("-")[2]))
					.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			Integer fMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
					.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveRequestBean.getLeaveType()))).add(Expression.eq("yearID", fromYearDTO.getId()))
					.add(Expression.eq("type",type)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();

			if (!CPSUtils.isNullOrEmpty(fMaxId)) {
				leaveYearlyBalanceFromDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, fMaxId);
			}

			YearTypeDTO toYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveRequestBean.getToDate().split("-")[2]))
					.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
			Integer tMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
					.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveRequestBean.getLeaveType()))).add(Expression.eq("yearID", toYearDTO.getId()))
					.add(Expression.eq("type",type1)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();

			if (!CPSUtils.isNullOrEmpty(tMaxId)) {
				leaveYearlyBalanceToDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, tMaxId);
			}

			if (!CPSUtils.isNull(leaveYearlyBalanceFromDTO)) {
				/*if (leaveYearlyBalanceFromDTO.getBalance() >= Float.valueOf(CPSConstants.MAXELS)) { // Leaves were lapsed
					leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() - fdays);
					leaveRequestBean.setPreviousPeriodDebit(fdays);

					if (leaveYearlyBalanceFromDTO.getBalance() < Float.valueOf(CPSConstants.MAXELS)) {
						extraDays += Float.valueOf(CPSConstants.MAXELS) - leaveYearlyBalanceFromDTO.getBalance();
					}
				} else { // Not lapsed
					leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() - fdays);
					extraDays += fdays;
				}
				session.saveOrUpdate(leaveYearlyBalanceFromDTO);
				session.flush();*/
				
				if (leaveYearlyBalanceFromDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) { // Leaves were lapsed
					String transResult = updateLeaveTransactionLapsRecord(leaveYearlyBalanceFromDTO.getSfID(), (leaveYearlyBalanceFromDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS)), 
							fdays, leaveYearlyBalanceFromDTO.getType(), Integer.parseInt(fromYearDTO.getName()), CPSConstants.DEBIT);
					float debitLeaves = 0.0f;
					if (Float.parseFloat(transResult.split("#")[1]) > 0.0f) {
						leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + Float.parseFloat(transResult.split("#")[1]));
						debitLeaves = Float.parseFloat(transResult.split("#")[0]);
					} else {
						debitLeaves = Float.parseFloat(transResult.split("#")[0]) - Float.parseFloat(transResult.split("#")[1]);
					}
					leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() + debitLeaves);
					insertTxnDetails(leaveRequestBean.getSfID(), leaveRequestBean.getFromDate(), leaveYearlyBalanceFromDTO.getLeaveTypeDetails(), debitLeaves, 
							CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), null, leaveRequestBean.getRequestID());
				} else { // Not lapsed
					leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + fdays);
				}
				session.saveOrUpdate(leaveYearlyBalanceFromDTO);
				session.flush();	
			}
			if (!CPSUtils.isNull(leaveYearlyBalanceToDTO)) {
				/*if (leaveYearlyBalanceToDTO.getBalance() >= Float.valueOf(CPSConstants.MAXELS)) { // Leaves were lapsed
					leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() - tdays);
					leaveRequestBean.setPreviousPeriodDebit(leaveRequestBean.getPreviousPeriodDebit() + tdays);

					if (leaveYearlyBalanceToDTO.getBalance() < Float.valueOf(CPSConstants.MAXELS)) {
						extraDays += Float.valueOf(CPSConstants.MAXELS) - leaveYearlyBalanceToDTO.getBalance();
					}
				} else { // Not lapsed
					leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() - tdays);
					extraDays += tdays;
				}
				session.saveOrUpdate(leaveYearlyBalanceToDTO);
				session.flush();*/
				
				if (leaveYearlyBalanceToDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) { // Leaves were lapsed
					String fromDate = CPSUtils.addNoOfDays(leaveRequestBean.getFromDate(), resultDays.split("-")[0]);
					String transResult = updateLeaveTransactionLapsRecord(leaveYearlyBalanceToDTO.getSfID(), (leaveYearlyBalanceToDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS)), 
							tdays, leaveYearlyBalanceToDTO.getType(), Integer.parseInt(toYearDTO.getName()), CPSConstants.DEBIT);
					float debitLeaves = 0.0f; 
					if (Float.parseFloat(transResult.split("#")[1]) > 0.0f) {
						leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + Float.parseFloat(transResult.split("#")[1]));
						debitLeaves = Float.parseFloat(transResult.split("#")[0]);
					} else {
						debitLeaves = Float.parseFloat(transResult.split("#")[0]) - Float.parseFloat(transResult.split("#")[1]);
					}
					leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() + debitLeaves);
					insertTxnDetails(leaveRequestBean.getSfID(), fromDate, leaveYearlyBalanceToDTO.getLeaveTypeDetails(), debitLeaves, 
							CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), null, leaveRequestBean.getRequestID());
				} else { // Not lapsed
					leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + tdays);
				}
				session.saveOrUpdate(leaveYearlyBalanceToDTO);
				session.flush();
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveRequestBean;
	} // End
	
	/**
	 * @param sfid, leaveTypdId, lapsDays, days, spellType, year, transType
	 * @throws Exception
	 * @return String
	 */
	// Added by Naresh
	public String updateLeaveTransactionLapsRecord(final String sfid, final float lapsDays, final float days, final int spellType, final int year, final String transType) throws Exception {
		// SQL Queries
		final String sql = "SELECT id AS id, sfid AS sfID, no_of_leaves AS noOfLeaves, txn_from_date AS txnDate, txn_to_date AS txnToDate, remarks AS remarks, "
				+ "txn_type AS txnType, status AS status FROM leave_txn_details WHERE sfid = ? AND leave_type_id = ? AND no_of_leaves = ? AND TO_CHAR(txn_from_date, 'MON-YYYY') = "
				+ "(SELECT CASE WHEN 0 = ? THEN 'JAN-'||? ELSE 'JUL-'||? END FROM DUAL) AND txn_type = ? ";
		final String sql1 = "SELECT id AS id, sfid AS sfID, no_of_leaves AS noOfLeaves, txn_from_date AS txnDate, txn_to_date AS txnToDate, remarks AS remarks, "
				+ "txn_type AS txnType, status AS status FROM leave_txn_details WHERE sfid = ? AND leave_type_id = ? AND (SUBSTR(no_of_leaves, 1, 1) = '-' OR no_of_leaves = 0) AND "
				+ "TO_CHAR(txn_from_date, 'MON-YYYY') = (SELECT CASE WHEN 0 = ? THEN 'JAN-'||? ELSE 'JUL-'||? END FROM DUAL) AND txn_type = ? ";
		// Code
		String transactionResult = null;
		float leaves = 0.0f;
		Session session = null;
		LeaveTxnDTO leaveTransaction = null;
		StringBuffer sb = null;
		try {
			session = hibernateUtils.getSession();
			leaveTransaction = (LeaveTxnDTO) session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("sfID", Hibernate.STRING).addScalar("noOfLeaves", Hibernate.FLOAT)
					.addScalar("txnDate", Hibernate.STRING).addScalar("txnToDate", Hibernate.STRING).addScalar("remarks", Hibernate.STRING)
					.addScalar("txnType", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setString(0, sfid).setString(1, CPSConstants.EL)
					.setFloat(2, -lapsDays).setInteger(3, spellType).setInteger(4, (year + 1)).setInteger(5, year).setString(6, CPSConstants.AUTORUNSTATUSID)
					.setResultTransformer(Transformers.aliasToBean(LeaveTxnDTO.class)).uniqueResult();
			if (CPSUtils.isNullOrEmpty(leaveTransaction)) {
				leaveTransaction = (LeaveTxnDTO) session.createSQLQuery(sql1).addScalar("id", Hibernate.INTEGER).addScalar("sfID", Hibernate.STRING).addScalar("noOfLeaves", Hibernate.FLOAT)
						.addScalar("txnDate", Hibernate.STRING).addScalar("txnToDate", Hibernate.STRING).addScalar("remarks", Hibernate.STRING)
						.addScalar("txnType", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setString(0, sfid).setString(1, CPSConstants.EL)
						.setInteger(2, spellType).setInteger(3, (year + 1)).setInteger(4, year).setString(5, CPSConstants.AUTORUNSTATUSID).setResultTransformer(Transformers.aliasToBean(LeaveTxnDTO.class)).uniqueResult();
			}
			if (!CPSUtils.isNullOrEmpty(leaveTransaction) && CPSUtils.compareStrings(CPSConstants.DEBIT, transType)) {
				sb = new StringBuffer();
				transactionResult = String.valueOf(leaveTransaction.getNoOfLeaves());
				leaves = leaveTransaction.getNoOfLeaves() + days;
				if (leaves < 0.0f) {
					sb.append("UPDATE leave_txn_details SET no_of_leaves = ? WHERE id = ? ");
					session.createSQLQuery(sb.toString()).setFloat(0, leaves).setInteger(1, leaveTransaction.getId()).executeUpdate();
				} else {
					session.createSQLQuery("UPDATE leave_txn_details SET no_of_leaves = 0, status = 0 WHERE id = ?").setInteger(0, leaveTransaction.getId()).executeUpdate();
				}
			} else if (!CPSUtils.isNullOrEmpty(leaveTransaction) && CPSUtils.compareStrings(CPSConstants.CREDIT, transType)) {
				sb = new StringBuffer();
				transactionResult = String.valueOf(leaveTransaction.getNoOfLeaves());
				leaves = leaveTransaction.getNoOfLeaves() - days;
				if (leaves < 0.0f) {
					sb.append("UPDATE leave_txn_details SET no_of_leaves = ?, status = 1 WHERE id = ?");
					session.createSQLQuery(sb.toString()).setFloat(0, leaves).setInteger(1, leaveTransaction.getId()).executeUpdate();
				} else {
					session.createSQLQuery("UPDATE leave_txn_details SET no_of_leaves = 0, status = 0 WHERE id = ?").setInteger(0, leaveTransaction.getId()).executeUpdate();
				}
			}
			transactionResult += "#" + String.valueOf(leaves);
		} catch (Exception e) {
			throw e;
		}
		return transactionResult;
	} // End

	public LeaveRequestBean assignPreviousPeriodCombinationDebit(LeaveRequestBean leaveRequestBean, final String periodType, final int type, final int type1) throws Exception {
		Session session = null;
		String resultDays = null;
		boolean yearSpell = false;
		LeaveYearlyBalanceDTO leaveYearlyBalanceFromDTO = null, leaveYearlyBalanceToDTO = null;
			try {
				session = hibernateUtils.getSession();
				resultDays = (String) session.createSQLQuery("select get_leave_period_days(?, ?, ?, ?, ?, ?) from dual").setString(0, periodType).setString(1, leaveRequestBean.getFromDate())
						.setString(2, CPSUtils.getCurrentDate()).setString(3, leaveRequestBean.getToDate()).setInteger(4, Integer.valueOf(leaveRequestBean.getPrevHolidays()))
						.setInteger(5, Integer.valueOf(leaveRequestBean.getNextHolidays())).uniqueResult();
				
				YearTypeDTO fromYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveRequestBean.getFromDate().split("-")[2]))
						.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
				Integer fMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
						.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveRequestBean.getLeaveType()))).add(Expression.eq("yearID", fromYearDTO.getId()))
						.add(Expression.eq("type", type)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
				if (!CPSUtils.isNullOrEmpty(fMaxId)) {
					leaveYearlyBalanceFromDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, fMaxId);
				}
				
				YearTypeDTO toYearDTO = (YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveRequestBean.getToDate().split("-")[2]))
						.add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult();
				Integer tMaxId = (Integer) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveRequestBean.getSfID()))
						.add(Expression.eq("leaveTypeID", Integer.valueOf(leaveRequestBean.getLeaveType()))).add(Expression.eq("yearID", toYearDTO.getId()))
						.add(Expression.eq("type", type1)).add(Expression.eq(CPSConstants.STATUS, 1)).setProjection(Projections.max("id")).uniqueResult();
				if (!CPSUtils.isNullOrEmpty(tMaxId)) {
					leaveYearlyBalanceToDTO = (LeaveYearlyBalanceDTO) session.get(LeaveYearlyBalanceDTO.class, tMaxId);
				}
				
				float fdays = Integer.parseInt(resultDays.split("-")[0]);
				float tdays = Integer.parseInt(resultDays.split("-")[1]);
				
				if (!CPSUtils.isNull(leaveYearlyBalanceFromDTO) && !CPSUtils.isNull(leaveYearlyBalanceToDTO)) {
					CPSUtils.monthMap();
					int fromMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveRequestBean.getFromDate().split("-")[1]));
					int toMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveRequestBean.getToDate().split("-")[1]));
					yearSpell = ((fromMonthId >= 7 && fromMonthId <= 12) && (toMonthId >= 1 && toMonthId <= 6)) ? true : false;// Both are in different spells JUL - DEC & JAN - JUN
				
					if (leaveYearlyBalanceFromDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) { // Leaves were lapsed
						String transResult = updateLeaveTransactionLapsRecord(leaveYearlyBalanceFromDTO.getSfID(), (leaveYearlyBalanceFromDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS)), 
								fdays, leaveYearlyBalanceFromDTO.getType(), Integer.parseInt(fromYearDTO.getName()), CPSConstants.DEBIT);
						float debitFromLeaves = 0.0f;
						if (Float.parseFloat(transResult.split("#")[1]) > 0.0f) {
							leaveRequestBean.setPresentPeriodDebit(Float.parseFloat(transResult.split("#")[1]));
							debitFromLeaves = Float.parseFloat(transResult.split("#")[0]);
						} else {
							debitFromLeaves = Float.parseFloat(transResult.split("#")[0]) - Float.parseFloat(transResult.split("#")[1]);
						}
						leaveYearlyBalanceFromDTO.setBalance(leaveYearlyBalanceFromDTO.getBalance() + debitFromLeaves);
						if (yearSpell) {
							insertTxnDetails(leaveRequestBean.getSfID(), leaveRequestBean.getFromDate(), leaveYearlyBalanceFromDTO.getLeaveTypeDetails(), debitFromLeaves, 
									CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), null, leaveRequestBean.getRequestID());
						} else {
							insertTxnDetails(leaveRequestBean.getSfID(), leaveRequestBean.getFromDate(), leaveYearlyBalanceToDTO.getLeaveTypeDetails(), debitFromLeaves, 
									CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), leaveRequestBean.getToDate(), leaveRequestBean.getRequestID());
						}
					} else { // Not lapsed
						leaveRequestBean.setPresentPeriodDebit(fdays);
					}
					session.saveOrUpdate(leaveYearlyBalanceFromDTO);
					session.flush();	
				
					if (leaveYearlyBalanceToDTO.getBalance() > Float.valueOf(CPSConstants.MAXELS)) { // Leaves were lapsed
						String transResult = updateLeaveTransactionLapsRecord(leaveYearlyBalanceToDTO.getSfID(), (leaveYearlyBalanceToDTO.getBalance() - Float.valueOf(CPSConstants.MAXELS)), 
								tdays, leaveYearlyBalanceToDTO.getType(), Integer.parseInt(toYearDTO.getName()), CPSConstants.DEBIT);
						float debitToLeaves = 0.0f; 
						if (Float.parseFloat(transResult.split("#")[1]) > 0.0f) {
							leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + Float.parseFloat(transResult.split("#")[1]));
							debitToLeaves = Float.parseFloat(transResult.split("#")[0]);
						} else {
							debitToLeaves = Float.parseFloat(transResult.split("#")[0]) - Float.parseFloat(transResult.split("#")[1]);
						}
						leaveYearlyBalanceToDTO.setBalance(leaveYearlyBalanceToDTO.getBalance() + debitToLeaves);
						String fromDate = CPSUtils.addNoOfDays(leaveRequestBean.getFromDate(), resultDays.split("-")[0]);
						if (yearSpell) {
							insertTxnDetails(leaveRequestBean.getSfID(), fromDate, leaveYearlyBalanceFromDTO.getLeaveTypeDetails(), debitToLeaves, 
									CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), null, leaveRequestBean.getRequestID());
						} else {
							insertTxnDetails(leaveRequestBean.getSfID(), fromDate, leaveYearlyBalanceToDTO.getLeaveTypeDetails(), debitToLeaves, 
									CPSConstants.PREVIOUSPERIODLEAVEID, leaveRequestBean.getRequestedBy(), CPSUtils.getCurrentDate(), leaveRequestBean.getToDate(), leaveRequestBean.getRequestID());
						}
					} else { // Not lapsed
						leaveRequestBean.setPresentPeriodDebit(leaveRequestBean.getPresentPeriodDebit() + tdays);
					}
					session.saveOrUpdate(leaveYearlyBalanceToDTO);
					session.flush();
				} else {
					leaveRequestBean.setPresentPeriodDebit(fdays + tdays);
				}
		} catch(Exception e) {
			throw e;
		}
		return leaveRequestBean;
	}

	public LeaveAdministratorBean geterpEmpLeaveBalance(LeaveAdministratorBean leaveBean) throws Exception  {
		
		Session session = null;
		String sql =null;
		ErpAvailableLeavesDTO erpAvailableLeavesDTO=null;
		ErpEmpLeavesDTO erpEmpLeavesDTO=null;
		try {
			session = hibernateUtils.getSession();
			//sql="select leave.ANNUAL_LEAVES AS annualLeaves,leave.MATERNITY_LEAVES AS maternityLeaves,leave.PETERNITY_LEAVES AS peternityLeaves,leave.SICK_LEAVES AS sickLeaves FROM ERP_AVAILABLE_LEAVES leave WHERE leave.SFID='"+leaveBean.getSfID()+"'";
			//	erpAvailableLeavesDTO=(ErpAvailableLeavesDTO) session.createSQLQuery(sql).addScalar("annualLeaves", Hibernate.INTEGER).addScalar("maternityLeaves", Hibernate.INTEGER).addScalar("peternityLeaves", Hibernate.INTEGER).addScalar("sickLeaves", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(ErpAvailableLeavesDTO.class)).uniqueResult();
			//leaveBean.setErpAvailableLeavesDTO(erpAvailableLeavesDTO);
			
			sql="select leave.LEAVE_TYPE AS leaveType,leave.LEAVE_CODE AS leaveCode,leave.NO_OF_DAYS AS noOfDays  FROM ERP_EMPLOEE_LEAVES leave WHERE leave.SFID='"+leaveBean.getSfID()+"'";
			leaveBean.setErpEmpAppliedLeavesList =session.createSQLQuery(sql).addScalar("leaveType", Hibernate.STRING).addScalar("leaveCode", Hibernate.STRING).addScalar("noOfDays",  Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(ErpEmpLeavesDTO.class)).list();
			
			
		} catch (Exception e) {
			throw e;
		
		}finally{
			session.flush();
		}
		
		
		return leaveBean;
	}

	@SuppressWarnings("unchecked")
	public List<ErpAvailableLeaveSaveDTO> getEmployeeAppliedLeavesList(
			LeaveAdministratorBean leaveBean) throws Exception {
		
		List<ErpAvailableLeaveSaveDTO> erpAppliedLeavesList=null;
		Session session=null;
		String sql=null;
		String sfid=leaveBean.getSfID();
	
		try{
			session=hibernateUtils.getSession();
			
			sql="select leave.REQUEST_ID AS requestId, leave.LEAVE_TYPE_ID AS leaveType ,leave.FROM_DATE AS fromDate,leave.TO_DATE AS toDate, "
					+ "leave.NO_OF_DAYS AS noOfDays,REQUESTED_DATE AS applyDate  "
					+ "FROM ERP_LEAVE_REQUEST_DETAILS leave where STATUS='2' and SFID='"+leaveBean.getSfID()+"' order by leaveType ";
			
			log.debug("sql"+sql);
			leaveBean.setErpAppliedLeavesList =session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("leaveType", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("noOfDays", Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(ErpAvailableLeaveSaveDTO.class)).list();
		
		}catch(Exception e){
			throw e;
		}
		
		////////////////
		
		Session session1=null;
		ErpUsedLeavesDTO erpUsedLeavesDTO = null;
		session1=hibernateUtils.getSession();
		try{
		
		/*	String sql1="select unique (select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='AL' and SFID='"+leaveBean.getSfID()+"') AS aL, "
					+ "(select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='SL' and SFID='"+leaveBean.getSfID()+"') AS sL, "
							+ " (select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='PL' and SFID='"+leaveBean.getSfID()+"') AS pL , "
									+ "(select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='ML' and SFID='"+leaveBean.getSfID()+"') AS mL   "
											+ " from ERP_LEAVE_REQUEST_DETAILS where SFID='"+leaveBean.getSfID()+"' ";
		
			*/
			
			String sql1="select unique (select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='AL' and SFID='"+leaveBean.getSfID()+"') AS aL1, "
					+ "(select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='SL' and SFID='"+leaveBean.getSfID()+"') AS sL1, "
							+ " (select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='PL' and SFID='"+leaveBean.getSfID()+"') AS pL1 ,  "
								+ "(select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='ML' and SFID='"+leaveBean.getSfID()+"') AS mL1 ,  "
										+ "(select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='CL' and SFID='"+leaveBean.getSfID()+"') AS cL1 , "
											+ "(select sum(NO_OF_DAYS) FROM ERP_LEAVE_REQUEST_DETAILS  where STATUS='2' and LEAVE_TYPE_ID='ComL' and SFID='"+leaveBean.getSfID()+"') AS comL1  "
												+ " from ERP_LEAVE_REQUEST_DETAILS where SFID='"+leaveBean.getSfID()+"' ";
		
			
			erpUsedLeavesDTO = (ErpUsedLeavesDTO) session1
					.createSQLQuery(sql1)
					.addScalar("aL1", Hibernate.STRING)
					.addScalar("sL1", Hibernate.STRING)
					.addScalar("pL1", Hibernate.STRING)
					.addScalar("mL1", Hibernate.STRING)
					.addScalar("cL1", Hibernate.STRING).addScalar("comL1", Hibernate.STRING)
				//	.addScalar("pL", Hibernate.INTEGER)
					//.addScalar("mL", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers.aliasToBean(ErpUsedLeavesDTO.class))
					.uniqueResult();

			leaveBean.setErpUsedLeavesDTO(erpUsedLeavesDTO);
		}catch(Exception e){
			throw e;
		}
		
		return erpAppliedLeavesList;
	}
	
	
}
