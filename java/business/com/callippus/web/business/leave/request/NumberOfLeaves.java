package com.callippus.web.business.leave.request;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;

@Service
public class NumberOfLeaves {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String getNoOfDays(LeaveApplicationBean leaveBean) throws Exception { // Modified and Added By Naresh
		// SQL Queries
		final String sql = "SELECT ((TO_DATE(?, 'DD-MON-YYYY') - TO_DATE(?, 'DD-MON-YYYY')) + 1) - ((SELECT COUNT(*) FROM holidays_master "
				+ "WHERE status = 1 AND holiday_date BETWEEN ? AND ?) + (SELECT COUNT(*) FROM (SELECT TO_CHAR(TO_DATE(dates, 'DD-MON-YYYY'), 'DY') days "
				+ "FROM (SELECT TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') + rownum -1, 'DD-MON-YYYY') dates FROM all_objects WHERE rownum <= TO_DATE(?, 'DD-MON-YYYY') - TO_DATE(?, 'DD-MON-YYYY') + 1)) "
				+ "WHERE days = 'SAT' OR days = 'SUN')) noofdays FROM DUAL";
		// Code
		Session session = null;
		String noOfLeaves = null;
		int workingDayCount = 0;
		// For configuring more holidays as working days, Add required date here by separating with , symbol
		String[] configuredDates = {"28-Sep-2014"};
		try {
			session = hibernateUtils.getSession();
			// For making holiday[Saturday and Sunday] as working day we did this (12-Oct-2013) and (28-Sep-2014)
			for (int i = 0; i < configuredDates.length; i++) {
				if (checkDayExistence(configuredDates[i], leaveBean.getFromDate(), leaveBean.getToDate()) != 0) {
					workingDayCount++;
				}
			}
			//original start
			noOfLeaves = session.createSQLQuery(sql).setString(0, leaveBean.getToDate()).setString(1, leaveBean.getFromDate()).setString(2, leaveBean.getFromDate())
					.setString(3, leaveBean.getToDate()).setString(4, leaveBean.getFromDate()).setString(5, leaveBean.getToDate()).setString(6, leaveBean.getFromDate()).uniqueResult().toString();
			//original end
			
			if (workingDayCount != 0) {
				noOfLeaves = String.valueOf(workingDayCount + Integer.parseInt(noOfLeaves));
			}
		} catch (Exception e) {
			throw e;
		}
		return noOfLeaves;
	} // End
	
	public int checkDayExistence(final String date, final String fromDate, final String toDate) throws Exception { // Added By Naresh
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();
			result = session.createSQLQuery("SELECT COUNT(*) FROM DUAL WHERE TO_DATE(UPPER(?), 'DD-MON-YYYY') BETWEEN TO_DATE(UPPER(?), 'DD-MON-YYYY') AND TO_DATE(UPPER(?), 'DD-MON-YYYY')")
					.setString(0, date).setString(1, fromDate).setString(2, toDate).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		}
		return Integer.parseInt(result);
	} // End
	
	public String getCurrentNextYearDays(LeaveApplicationBean leaveBean) throws Exception { // Added By Naresh
		String token = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			token = session.createSQLQuery("SELECT GET_CURRENT_NEXT_BALANCE(?, ?, ?, ?) AS result FROM DUAL").setInteger(0, Integer.parseInt(leaveBean.getLeaveType()))
					.setString(1, leaveBean.getFromDate().toUpperCase()).setString(2, CPSUtils.getCurrentDate().toUpperCase()).setString(3, leaveBean.getToDate().toUpperCase())
					.uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		}
		return token;
	} // End
	
	public String getFutureLeaves(LeaveApplicationBean leaveBean) throws Exception { // Added By Naresh
		String futureLeaves = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			futureLeaves = session.createSQLQuery("SELECT CHECK_FUTURE_BALANCE(?, ?, ?, ?) FROM DUAL").setString(0, leaveBean.getSfID()).setString(1, leaveBean.getLeaveType())
					.setDate(2, CPSUtils.getCurrentDateWithTime()).setString(3, leaveBean.getToDate()).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		}
		return futureLeaves;
	} // End
	
	public String getCurrentNextSpellDays(LeaveApplicationBean leaveBean) throws Exception { // Added By Naresh
		String token = null;
		String notation = null;
		Session session  = null;
		try {
			session = hibernateUtils.getSession();
			notation = (String) session.createSQLQuery("SELECT GET_LEAVE_PERIODS(?, ?, ?, ?, ?, ?) FROM DUAL").setString(0, leaveBean.getFromDate()).setString(1, CPSUtils.getCurrentDate()).setString(2, leaveBean.getToDate())
					.setString(3, leaveBean.getPrevHolidays()).setString(4, leaveBean.getNextHolidays()).setInteger(5, Integer.parseInt(leaveBean.getLeaveType())).uniqueResult();
			token = (String) session.createSQLQuery("SELECT GET_LEAVE_PERIOD_DAYS(?, ?, ?, ?, ?, ?) FROM DUAL").setString(0, notation.split("#")[0] + "#" + notation.split("#")[1])
					.setString(1, leaveBean.getFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveBean.getToDate())
					.setInteger(4, Integer.valueOf(leaveBean.getPrevHolidays())).setInteger(5, Integer.valueOf(leaveBean.getNextHolidays())).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return token.split("-")[1] + "#" + token.split("-")[2];
	} // End
}
