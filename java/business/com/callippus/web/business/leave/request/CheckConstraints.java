package com.callippus.web.business.leave.request;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dto.AvailableLeavesDTO;
import com.callippus.web.leave.dto.LeaveYearlyBalanceDTO;

@Service
public class CheckConstraints {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CheckLeaveMapping checkLeaveMapping;
	@Autowired
	private NormalLeaveConstraints normalLeaveConstraints;
	@Autowired
	private SpecialLeaveConstraints specialLeaveConstraints;
	@Autowired
	private CommonConstraints commonConstraints;
	@Autowired
	private CheckHolidays checkHolidays;

	public LeaveApplicationBean checkLeaveConstraints(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setSelectedLeaveDetails((LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("status", 1)).add(Expression.eq("leaveTypeId", leaveBean.getLeaveType())).uniqueResult());

			// Leaves Mapping & duplicate checking
			leaveBean = checkLeaveMapping.checkLeaveMapping(leaveBean);
			if (!CPSUtils.compareStrings(CPSConstants.FAILED, leaveBean.getResult())) {
				// check whether the applied leave is special casual leave or not
				if (CPSUtils.isNullOrEmpty(leaveBean.getLeaveSubType())) { // Normal leave
					// If the leave type is CL & the leave applied not in current year then we should check the balance in leave_yearly_balance table
					if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.CL) && ((Integer.parseInt(leaveBean.getFromDate().split("-")[2]) < Integer.parseInt(CPSUtils.getCurrentYear()) && Integer.parseInt(leaveBean.getToDate().split("-")[2]) <= Integer.parseInt(CPSUtils.getCurrentYear())))) {
						leaveBean = checkCLBalance(leaveBean);
					}
					if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.FAILED)) {
						leaveBean = normalLeaveConstraints.leaveConstraints(leaveBean);
					}
				} else {
					leaveBean = specialLeaveConstraints.leaveConstraints(leaveBean); // Special casual leave
				}
	
				leaveBean = commonConstraints.checkMaxContinuousLeaves(leaveBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean checkCLBalance(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		AvailableLeavesDTO availableLeaves = null;
		LeaveYearlyBalanceDTO leaveYearlyBalanceDTO = null;
		LeaveYearlyBalanceDTO fromYearBalance = null;
		LeaveYearlyBalanceDTO toYearBalance = null;
		boolean validationStatus = true;
		try {
			session = hibernateUtils.getSession();
			String prevHolidays = CPSUtils.isNullOrEmpty(leaveBean.getPrevHolidays()) ? "0" : leaveBean.getPrevHolidays();
			String nextHolidays = CPSUtils.isNullOrEmpty(leaveBean.getNextHolidays()) ? "0" : leaveBean.getNextHolidays();
			String result = (String) session.createSQLQuery("select get_leave_periods(?, ?, ?, ?, ?, ?) from dual").setString(0, leaveBean.getFromDate()).setString(1, CPSUtils.getCurrentDate())
					.setString(2, leaveBean.getToDate()).setString(3, prevHolidays).setString(4, nextHolidays).setInteger(5, Integer.parseInt(leaveBean.getLeaveType())).uniqueResult();				
			String resultDays = (String) session.createSQLQuery("select get_leave_period_days(?, ?, ?, ?, ?, ?) from dual").setString(0, result.split("#")[0] + "#" + result.split("#")[1])
					.setString(1, leaveBean.getFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveBean.getToDate()).setInteger(4, Integer.valueOf(prevHolidays)).setInteger(5, Integer.valueOf(nextHolidays)).uniqueResult();
			if (CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getHolidayIntFlag(), "N")) {
				LeaveRequestBean leaveRequestBean = new LeaveRequestBean();
				BeanUtils.copyProperties(leaveBean, leaveRequestBean );
				leaveRequestBean.setPrevHolidays(prevHolidays);
				leaveRequestBean.setNextHolidays(nextHolidays);
				resultDays = checkHolidays.getHolidaysBetweenDates(leaveRequestBean, result);
			}
			// checking whether half day leave is applied or not, if applied we have to subtract 0.5 leave	
			float fromHalfDays = Float.parseFloat(resultDays.split("-")[0]);
			float toHalfDays = Float.parseFloat(resultDays.split("-")[1]);
			if (CPSUtils.compareStrings(leaveBean.getFromHalfDayFlag(), "Y")) {
				if (fromHalfDays != 0) fromHalfDays -= 0.5f;
				else if (toHalfDays != 0) toHalfDays -= 0.5f;
			}
			if (CPSUtils.compareStrings(leaveBean.getToHalfDayFlag(), "Y")) {
				if (fromHalfDays != 0) fromHalfDays -= 0.5f;
				else if (toHalfDays != 0) toHalfDays -= 0.5f;
			}
			// complete past years
			if (CPSUtils.compareStrings(leaveBean.getFromDate().split("-")[2], leaveBean.getToDate().split("-")[2])) {
				leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
						.add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CL))).add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getToDate().split("-")[2])).uniqueResult()).getId()))
						.add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.TYPE, 0)).uniqueResult();
				if (!CPSUtils.isNullOrEmpty(leaveYearlyBalanceDTO)) {
					if (leaveYearlyBalanceDTO.getBalance() < fromHalfDays) {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(leaveYearlyBalanceDTO.getBalance()) + " previous year " + leaveBean.getToDate().split("-")[2]));
					} else {
						leaveBean.setResult(CPSConstants.SUCCESS);
					}
				} else {
					leaveBean.setResult(CPSConstants.FAILED);
					leaveBean.setRemarks(CPSConstants.INSUFFIENTLEAVES);
				}
			} else { // past year combination
				if (Integer.parseInt(leaveBean.getFromDate().split("-")[2]) < Integer.parseInt(CPSUtils.getCurrentYear()) && Integer.parseInt(leaveBean.getToDate().split("-")[2]) < Integer.parseInt(CPSUtils.getCurrentYear())) {
					fromYearBalance = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
							.add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CL))).add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getFromDate().split("-")[2])).uniqueResult()).getId()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.TYPE, 0)).uniqueResult();
					toYearBalance = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
							.add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CL))).add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getToDate().split("-")[2])).uniqueResult()).getId()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.TYPE, 0)).uniqueResult();
					if (!CPSUtils.isNullOrEmpty(fromYearBalance) && !CPSUtils.isNullOrEmpty(toYearBalance)) {
						if (fromYearBalance.getBalance() < fromHalfDays) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(fromYearBalance.getBalance()) + " previous year " + leaveBean.getFromDate().split("-")[2]));
							validationStatus = false;
						}
						if (toYearBalance.getBalance() < toHalfDays) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(fromYearBalance.getBalance()) + " previous year " + leaveBean.getToDate().split("-")[2]));
							validationStatus = false;
						}
						if (validationStatus) {
							leaveBean.setResult(CPSConstants.SUCCESS);
						}
					} else {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.INSUFFIENTLEAVES);
					} // past and current combination
				} else if (Integer.parseInt(leaveBean.getFromDate().split("-")[2]) < Integer.parseInt(CPSUtils.getCurrentYear()) && Integer.parseInt(leaveBean.getToDate().split("-")[2]) <= Integer.parseInt(CPSUtils.getCurrentYear())) {
					fromYearBalance = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID()))
							.add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CL))).add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getFromDate().split("-")[2])).uniqueResult()).getId()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.TYPE, 0)).uniqueResult();
					availableLeaves = (AvailableLeavesDTO) session.createCriteria(AvailableLeavesDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CL))).uniqueResult();
					if (!CPSUtils.isNullOrEmpty(fromYearBalance) && !CPSUtils.isNullOrEmpty(availableLeaves)) {
						if (fromYearBalance.getBalance() < fromHalfDays) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(fromYearBalance.getBalance()) + " previous year " + leaveBean.getFromDate().split("-")[2]));
							validationStatus = false;
						}
						if (availableLeaves.getAvailableLeaves() < toHalfDays) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(fromYearBalance.getBalance()) + " previous year " + leaveBean.getToDate().split("-")[2]));
							validationStatus = false;
						}
						if (validationStatus) {
							leaveBean.setResult(CPSConstants.SUCCESS);
						}
					} else {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.INSUFFIENTLEAVES);
					}
				}
			}
			
			/*if (CPSUtils.isNull(leaveYearlyBalanceDTO)) {
				if (CPSUtils.compareStrings("SH#next", result.split("#")[0] + "#" + result.split("#")[1])) {
					if (Float.valueOf(resultDays.split("-")[1]) != 0) {
						if (!CPSUtils.isNullOrEmpty(availableLeaves) && (availableLeaves.getAvailableLeaves() < toHalfDays)) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(availableLeaves.getAvailableLeaves())));
						}
					} else {
						leaveBean.setResult(CPSConstants.SUCCESS);
					}
				} else if (CPSUtils.compareStrings("FH#previous", result.split("#")[0] + "#" + result.split("#")[1])) {
					leaveYearlyBalanceDTO = (LeaveYearlyBalanceDTO) session.createCriteria(LeaveYearlyBalanceDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).add(Expression.eq("leaveTypeID", Integer.valueOf(CPSConstants.CL)))
							.add(Expression.eq("yearID", ((YearTypeDTO) session.createCriteria(YearTypeDTO.class).add(Expression.eq(CPSConstants.NAME, leaveBean.getFromDate().split("-")[2])).uniqueResult()).getId()))
							.add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq(CPSConstants.TYPE, 0)).uniqueResult();
					if (!CPSUtils.isNullOrEmpty(availableLeaves) && (availableLeaves.getAvailableLeaves() < toHalfDays)) {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(availableLeaves.getAvailableLeaves())));
					} else if (leaveYearlyBalanceDTO.getBalance() < fromHalfDays) {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(leaveYearlyBalanceDTO.getBalance()) + " Previous Year"));
					} else {
						leaveBean.setResult(CPSConstants.SUCCESS);
					}
				} else {
					if (!CPSUtils.isNullOrEmpty(availableLeaves) && (availableLeaves.getAvailableLeaves() < Float.parseFloat(resultDays.split("-")[0]) + Float.parseFloat(resultDays.split("-")[1]) + Float.parseFloat(resultDays.split("-")[2]))) {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.INSUFFIENTLEAVES);
					} else {
						leaveBean.setResult(CPSConstants.SUCCESS);
					}
				}
			} else {
				if (CPSUtils.compareStrings("FH#previous", result.split("#")[0] + "#" + result.split("#")[1])) {
					if (Float.valueOf(resultDays.split("-")[1]) != 0) {
						if (!CPSUtils.isNullOrEmpty(availableLeaves) && (availableLeaves.getAvailableLeaves() < toHalfDays)) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(availableLeaves.getAvailableLeaves())));
						} else if (leaveYearlyBalanceDTO.getBalance() < fromHalfDays) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(leaveYearlyBalanceDTO.getBalance()) + " previous year"));
						} else {
							leaveBean.setResult(CPSConstants.SUCCESS);
						}
					} else {
						if (leaveYearlyBalanceDTO.getBalance() < fromHalfDays) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(leaveYearlyBalanceDTO.getBalance()) + " previous year"));
						} else {
							leaveBean.setResult(CPSConstants.SUCCESS);
						}
					}
				} else {
					if (leaveYearlyBalanceDTO.getBalance() < Float.parseFloat(leaveBean.getNoOfDays())) {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(CPSConstants.INSUFFICIENTLEAVECREDIT.replace("%1%", String.valueOf(leaveYearlyBalanceDTO.getBalance())));
					} else {
						leaveBean.setResult(CPSConstants.SUCCESS);
					}
				}
			}*/
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
}