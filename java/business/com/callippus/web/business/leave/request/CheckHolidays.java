package com.callippus.web.business.leave.request;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;

@Service
public class CheckHolidays {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public LeaveApplicationBean checkHolidays(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		StringBuilder holidaysDates = null;
		String result = null;
		String holidays = null;
		String userSelectedDate = null;
		try {
			holidaysDates = new StringBuilder();
			session = hibernateUtils.getSession();
			// Added by Naresh
			userSelectedDate = leaveBean.getSelectedDate().toUpperCase();
			String retirementDate  = ((String)session.createSQLQuery("SELECT GET_RETIREMENT_DATE('"+ leaveBean.getSfID() +"') FROM DUAL").uniqueResult()).toUpperCase();
			int dateResult = CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(retirementDate), CPSUtils.convertStringToDate(userSelectedDate));
			if (dateResult != -1) {
				while (true) {
					if (CPSUtils.compareStrings(CPSConstants.PREFIX, leaveBean.getType())) {
						leaveBean.setSelectedDate(CPSUtils.previousDate(leaveBean.getSelectedDate()));
					} else {
						leaveBean.setSelectedDate(CPSUtils.nextDate(leaveBean.getSelectedDate()));
					}
					result = session.createSQLQuery("select case when (select to_char(to_date(?,'DD-MON-YYYY'), 'DY') from dual) ='SUN' then (select to_char(to_date(?,'DD-MON-YYYY'), 'DD')||'(SUN)' from dual) "
							+ "when (select to_char(to_date(?,'DD-MON-YYYY'), 'DY') from dual) ='SAT' then (select to_char(to_date(?,'DD-MON-YYYY'), 'DD')||'(SAT)' from dual) "
							+ "when (select count(*) from holidays_master where status=1 and holiday_date = to_date(?,'dd-mon-yy'))>0 then (select to_char(to_date(?,'DD-MON-YYYY'), 'DD')||'('||holiday||')' "
							+ "from holidays_master where status=1 and holiday_date = to_date(?,'dd-mon-yyyy')) else 'N' end checkResult from dual")
						.addScalar("checkResult", Hibernate.STRING).setString(0, leaveBean.getSelectedDate()).setString(1, leaveBean.getSelectedDate())
						.setString(2, leaveBean.getSelectedDate()).setString(3, leaveBean.getSelectedDate()).setString(4, leaveBean.getSelectedDate())
						.setString(5, leaveBean.getSelectedDate()).setString(6, leaveBean.getSelectedDate()).uniqueResult().toString();
					if (CPSUtils.compareStrings(CPSConstants.N, result))
						break;
					else
						holidaysDates.append(result + ", ");
				}
			}  else {
				leaveBean.setUserIntimation("Leave applied date should not cross employee Retirement date : " + retirementDate);
			} // End
			if (CPSUtils.compareStrings(CPSConstants.PREFIX, leaveBean.getType())) {
				// reverse the list
				String[] prefixList = holidaysDates.toString().split(",");
				holidaysDates.setLength(0);
				for (int i = prefixList.length - 2; i >= 0; i--) {
					holidaysDates.append(prefixList[i] + ", ");
				}
			}
			if (holidaysDates.length() > 0) {
				holidays = holidaysDates.substring(0, holidaysDates.length() - 2);
			}
			leaveBean.setResult(holidays);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public String getHolidaysBetweenDates(LeaveRequestBean leaveRequestBean, String type) throws Exception {
		Session session = null;
		StringBuilder result = null ;
		String holidays = null;
		String selectedDate = null;
		boolean flag = false;
		try {
			session = hibernateUtils.getSession();
			selectedDate = leaveRequestBean.getFromDate();
			String resultDays = (String) session.createSQLQuery("select get_leave_period_days(?,?,?,?,?,?) from dual").setString(0, type.split("#")[0] + "#" + type.split("#")[1])
					.setString(1, leaveRequestBean.getFromDate()).setString(2, CPSUtils.getCurrentDate()).setString(3, leaveRequestBean.getToDate())
					.setInteger(4, Integer.valueOf(leaveRequestBean.getPrevHolidays())).setInteger(5, Integer.valueOf(leaveRequestBean.getNextHolidays())).uniqueResult();
			result = new StringBuilder();
			for (int i = 0; i < resultDays.split("-").length; i++) {
				if (Integer.valueOf(resultDays.split("-")[i]) != 0) {
					int counter = 0;
					for (int j = 0; j < Integer.valueOf(resultDays.split("-")[i]); j++, selectedDate = CPSUtils.nextDate(selectedDate)) {
						//this if condition added for sat day considering(12-Oct-2013)
						//if(!selectedDate.equalsIgnoreCase("12-Oct-2013")){
						// For configuring more holidays as working days, use the same condition with required holiday date by separating with || symbol to the already existed one
						if (CPSUtils.compareStrings(selectedDate.toUpperCase(), "28-SEP-2014")) {
							flag = true;
							continue;
						}
						holidays = session.createSQLQuery("select case when (select to_char(to_date(?,'DD-MON-YYYY'), 'DY') from dual) ='SUN' then (select to_char(to_date(?,'DD-MON-YYYY'), 'DD')||'(SUN)' from dual) "
										+ "when (select to_char(to_date(?,'DD-MON-YYYY'), 'DY') from dual) ='SAT' then (select to_char(to_date(?,'DD-MON-YYYY'), 'DD')||'(SAT)' from dual) "
										+ "when (select count(*) from holidays_master where status=1 and holiday_date = to_date(?,'dd-mon-yy'))>0 "
										+ "then (select to_char(to_date(?,'DD-MON-YYYY'), 'DD')||'('||holiday||')' from holidays_master where status=1 and holiday_date = to_date(?,'dd-mon-yyyy')) "
										+ "else 'N' end checkResult from dual").addScalar("checkResult", Hibernate.STRING).setString(0, selectedDate)
										.setString(1, selectedDate).setString(2, selectedDate).setString(3, selectedDate).setString(4, selectedDate)
										.setString(5, selectedDate).setString(6, selectedDate).uniqueResult().toString();
							if (!CPSUtils.compareStrings(CPSConstants.N, holidays)) {
								counter++;
							}
						//}
					}
					result = result.append((Integer.valueOf(resultDays.split("-")[i]) - counter) + "-");
				} else {
					result = result.append("0-");
				}
			}
			if (flag) {
				leaveRequestBean.setNoOfDays(result.toString().split("-")[1]);
			}
		} catch (Exception e) {
			throw e;
		}
		return result.toString().substring(0, result.toString().length() - 1);
	}
}