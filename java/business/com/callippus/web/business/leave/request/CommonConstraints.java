package com.callippus.web.business.leave.request;

import java.sql.CallableStatement;
import java.sql.Types;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;

@Service
public class CommonConstraints {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String setRemarkDetails(final String prevDetails, final String newDetails) throws Exception {
		if (CPSUtils.isNullOrEmpty(prevDetails)) {
			if (CPSUtils.isNullOrEmpty(newDetails)) {
				return "";
			} else {
				return newDetails;
			}
		} else {
			if (CPSUtils.isNullOrEmpty(newDetails)) {
				return prevDetails;
			} else {
				return prevDetails + "\n" + newDetails;
			}
		}
	}

	public String setResultDetails(final String newResult) throws Exception {
		if (!CPSUtils.compareStrings(CPSConstants.SUCCESS, newResult)) {
			return newResult;
		} else {
			return CPSConstants.SUCCESS;
		}
	}

	@SuppressWarnings("deprecation")
	public LeaveApplicationBean checkMaxContinuousLeaves(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		String dates = null;
		try {
			session = hibernateUtils.getSession();
			/**
			 * First get the continuous leaves dates (from date & to date)
			 */
			dates = session.createSQLQuery("select to_char((select dates from (select * from (select to_date(?, 'DD-MON-YYYY') - rownum dates from temp_dates) order by dates desc) where to_char(dates, 'DY') != 'SAT' "
					+ "and to_char(dates, 'DY') != 'SUN' and dates not in (select holiday_date from holidays_master where status = 1) and (select count(*) "
					+ "from leave_request_details where status not in (?, ?, ?) and convert_ref_id is null and sfid = ? and dates between from_date and to_date) = 0 "
					+ "and rownum = 1), 'DD-MON-YYYY') ||'#'|| to_char((select dates from (select * from (select to_date(?, 'DD-MON-YYYY') + rownum dates from temp_dates) order by dates) where to_char(dates, 'DY') != 'SAT' "
					+ "and to_char(dates, 'DY') != 'SUN' and dates not in (select holiday_date from holidays_master where status = 1) and (select count(*) from leave_request_details "
					+ "where status not in (?, ?, ?) and convert_ref_id is null and sfid = ? and dates between from_date and to_date) = 0 and rownum = 1), 'DD-MON-YYYY') toDate from dual")
						.setString(0, leaveBean.getFromDate()).setString(1, CPSConstants.STATUSCANCELLED).setString(2, CPSConstants.STATUSDECLINED)
						.setString(3, CPSConstants.STATUSCONVERTED).setString(4, leaveBean.getSfID()).setString(5, leaveBean.getToDate()).setString(6, CPSConstants.STATUSCANCELLED)
						.setString(7, CPSConstants.STATUSDECLINED).setString(8, CPSConstants.STATUSCONVERTED).setString(9, leaveBean.getSfID()).uniqueResult().toString();
			/**
			 * Check the max continuous days between from date & to date, if it crosses 5 year, we should not allow
			 */
			String result = session.createSQLQuery("select case when (MONTHS_BETWEEN(to_date(?, 'DD-MON-YYYY'), to_date(?, 'DD-MON-YYYY')) / 12) > ? then 'false' else 'true' end result from dual")
					.setString(0, dates.split("#")[1]).setString(1, dates.split("#")[0]).setString(2, CPSConstants.MAX_CONTINUOUS_LEAVES).uniqueResult().toString();
			if (CPSUtils.compareStrings(result, CPSConstants.FALSE)) {
				leaveBean.setResult(CPSConstants.FAILED);
				leaveBean.setRemarks(setRemarkDetails(leaveBean.getRemarks(), CPSConstants.MAXCONTINUOUSLEAVES.replace("%1%", CPSConstants.MAX_CONTINUOUS_LEAVES)));
			}
			CallableStatement call = session.connection().prepareCall("{ ? = call LEAVE_BUSINESS_RULES(?, ?, ?, ?, ?, ?) }");
			call.registerOutParameter(1, Types.VARCHAR);
			call.setString(2, dates.split("#")[0]);
			call.setString(3, dates.split("#")[1]);
			call.setString(4, leaveBean.getLeaveType());
			call.setString(5, leaveBean.getFromDate());
			call.setString(6, leaveBean.getToDate());
			call.setString(7, leaveBean.getSfID());
			call.execute();
			result = call.getString(1);
			if (!CPSUtils.compareStrings(result, CPSConstants.TRUE)) {
				leaveBean.setResult(CPSConstants.FAILED);
				leaveBean.setRemarks(setRemarkDetails(leaveBean.getRemarks(), result));
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
	
	// Added by Naresh
	public String checkPaternityDates(final String sfid, final String appliedDate, final int leaveId) throws Exception {
		// sql
		final String sql = "SELECT CASE WHEN TO_DATE(?, 'DD-MON-YYYY') BETWEEN tab.previousAppliedDate AND TO_DATE(ADD_MONTHS(TO_DATE(tab.previousAppliedDate,'DD-MON-YY'), 12)) - 1 "
				+ "THEN 1||'#'||TO_CHAR(tab.previousAppliedDate, 'DD-MON-YYYY') ELSE 0||'#'||TO_CHAR(tab.previousAppliedDate, 'DD-MON-YYYY') END result "
				+ "FROM (SELECT TO_DATE(MAX(requested_date), 'DD-MON-YY') AS previousAppliedDate FROM leave_request_details WHERE sfid  = ? AND leave_type_id = ? AND status NOT IN (?, ?)) tab";
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();
			result = (String) session.createSQLQuery(sql).setString(0, appliedDate).setString(1, sfid).setInteger(2, leaveId).setString(3, CPSConstants.STATUSDECLINED).setString(4, CPSConstants.STATUSCANCELLED).uniqueResult();
		} catch(Exception e) {
			throw e;
		}
		return result;
	} 
	
	public String checkAdoptionDates(final String sfid, final int leaveId) throws Exception {
		// sql query
		/*final String sql1 = "SELECT CASE WHEN TO_DATE(?, 'DD-MON-YYYY') = TO_DATE((SELECT additional_data FROM leave_request_details "
				+ "WHERE sfid = ? AND leave_type_id = ? AND status = ? ), 'DD-MON-YYYY') THEN 1 ELSE 0 END result FROM DUAL";*/
		final String sql = "SELECT TO_CHAR(TO_DATE(additional_data, 'DD-MON-YYYY'), 'DD-MON-YYYY') FROM leave_request_details WHERE sfid = ? AND leave_type_id = ? AND status = ?";
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession();
			result = (String) session.createSQLQuery(sql).setString(0, sfid).setInteger(1, leaveId).setString(2, CPSConstants.STATUSSANCTIONED).uniqueResult();
		} catch(Exception e) {
			throw e;
		}
		return result;
	} // End
}
