package com.callippus.web.business.leave.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.management.LeaveManagementBean;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dto.LeaveTypeDTO;

@Service
public class CheckLeaveMapping {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CommonConstraints commonConstraints;

	/**
	 * 1) Check whether any leave is already applied in between these date 
	 * 2) Check any other leave applied continuously with this leave, if yes check the mapping 
	 * 3) If the continuously same leaves are applied then we should check the holidays flag
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param sfid
	 * @param leaveTypeID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public LeaveApplicationBean checkLeaveMapping(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		leaveBean.setResult(CPSConstants.SUCCESS);
		try {
			session = hibernateUtils.getSession();
			/**
			 * Check whether any leave is applied in these days or not, also check whether the applied leaves are cancelled or declined.
			 * If the leaves are cancelled/declined then user can able to apply another leave between these days
			 */
			/*String sql = "select count(*)||'' cnt from leave_request_details where sfid=? and status not in (?,?,?) and ((to_char(from_date,'DD-Mon-YYYY')=? and from_half_day_flag=?) or "
					+ "(to_char(from_date,'DD-Mon-YYYY')=? and to_half_day_flag=?) or (to_char(to_date,'DD-Mon-YYYY')=? and from_half_day_flag=?) or "
					+ "(to_char(to_date,'DD-Mon-YYYY')=? and to_half_day_flag=?) or to_date(?,'DD-MON-YY') between to_date((select case when ?='Y' then from_date+1 else from_date end from dual),'DD-MON-YY') and to_date((select case when ?='Y' then to_date-1 else to_date end from dual),'DD-MON-YY') or "
					+ "to_date(?,'DD-MON-YY') between to_date((select case when ?='Y' then from_date+1 else from_date end from dual),'DD-MON-YY') and to_date((select case when ?='Y' then to_date-1 else to_date end from dual),'DD-MON-YY'))";
			*/ //corrected By Narayana
			String sql = "select count(*)||'' cnt from leave_request_details where sfid = ? and status not in (?, ?, ?) and ((to_char(from_date, 'DD-Mon-YYYY') = ? "
					+ "and from_half_day_flag = ?) or (to_char(from_date, 'DD-Mon-YYYY') = ? and to_half_day_flag = ?) or (to_char(to_date, 'DD-Mon-YYYY') = ? "
					+ "and from_half_day_flag = ?) or (to_char(to_date, 'DD-Mon-YYYY') = ? and to_half_day_flag = ?) or to_date(?, 'DD-MON-YYYY') between "
					+ "to_date((case when ? = 'Y' then to_char(from_date + 1, 'DD-MON-YYYY') else to_char(from_date, 'DD-MON-YYYY') end), 'DD-MON-YYYY') "
					+ "and to_date((case when ? = 'Y' then to_char(to_date - 1, 'DD-MON-YYYY') else to_char(to_date, 'DD-MON-YYYY') end), 'DD-MON-YYYY') "
					+ "or to_date(?, 'DD-MON-YYYY') between to_date((case when ? = 'Y' then to_char(from_date + 1, 'DD-MON-YYYY') else to_char(from_date, 'DD-MON-YYYY') end), 'DD-MON-YYYY') "
					+ "and to_date((case when ? = 'Y' then to_char(to_date - 1, 'DD-MON-YYYY') else to_char(to_date, 'DD-MON-YYYY') end), 'DD-MON-YYYY') " 
 					+ "or from_date between to_date(?, 'DD-MON-YYYY') and to_date(?, 'DD-MON-YYYY') or to_date between to_date(?, 'DD-MON-YYYY') and to_date(?, 'DD-MON-YYYY') " 
					+ "or to_date(?,'DD-MON-YYYY') between from_date and to_date or to_date(?, 'DD-MON-YYYY') between from_date and to_date)";
			int records = Integer.valueOf(session.createSQLQuery(sql).setString(0, leaveBean.getSfID()).setString(1, CPSConstants.STATUSCANCELLED).setString(2, CPSConstants.STATUSDECLINED)
					.setString(3, CPSConstants.STATUSCONVERTED).setString(4, leaveBean.getFromDate()).setString(5, leaveBean.getFromHalfDayFlag())
					.setString(6, leaveBean.getFromDate()).setString(7, leaveBean.getToHalfDayFlag()).setString(8, leaveBean.getToDate()).setString(9, leaveBean.getFromHalfDayFlag())
					.setString(10, leaveBean.getToDate()).setString(11, leaveBean.getToHalfDayFlag()).setString(12, leaveBean.getFromDate()).setString(13, leaveBean.getToHalfDayFlag())
					.setString(14, leaveBean.getFromHalfDayFlag()).setString(15, leaveBean.getToDate()).setString(16, leaveBean.getToHalfDayFlag())
					.setString(17, leaveBean.getFromHalfDayFlag()).setString(18, leaveBean.getFromDate()).setString(19, leaveBean.getToDate())
					.setString(20, leaveBean.getFromDate()).setString(21, leaveBean.getToDate()).setString(22, leaveBean.getFromDate()).setString(23, leaveBean.getToDate())
					.uniqueResult().toString());

			if (records == 0) {
				leaveBean.setDate(leaveBean.getFromDate());
				leaveBean.setDateType(CPSConstants.PREVIOUS);
				leaveBean = checkOtherLeaveMapping(leaveBean);
				if (CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.SUCCESS)) {
					leaveBean.setDate(leaveBean.getToDate());
					leaveBean.setDateType(CPSConstants.NEXT);
					leaveBean = checkOtherLeaveMapping(leaveBean);
				}
			} else {
				/*boolean status = true;
				if (CPSUtils.compareStrings(CPSConstants.CL, leaveBean.getLeaveType())) {
					String qry = "SELECT count(*) FROM (SELECT sfid, leave_type_id, from_date, to_date, from_half_day_flag, to_half_day_flag, no_of_days "
							+ "FROM LEAVE_REQUEST_DETAILS WHERE SFID = ? AND STATUS NOT IN (?, ?, ?) AND (TO_CHAR(FROM_DATE, 'DD-Mon-YYYY') = ? "
							+ "OR TO_CHAR(TO_DATE, 'DD-Mon-YYYY') = ? OR FROM_DATE BETWEEN TO_DATE(?, 'DD-Mon-YYYY') AND TO_DATE(?, 'DD-MON-YYYY') "
							+ "OR TO_DATE BETWEEN TO_DATE(?, 'DD-Mon-YYYY') AND TO_DATE(?, 'DD-MON-YYYY') OR TO_DATE(?, 'DD-Mon-YYYY') BETWEEN FROM_DATE "
							+ "AND TO_DATE OR TO_DATE(?, 'DD-Mon-YYYY') BETWEEN FROM_DATE AND TO_DATE)) WHERE (TRUNC(NO_OF_DAYS, 0) = NO_OF_DAYS OR TRUNC(NO_OF_DAYS, 0) != NO_OF_DAYS) AND LEAVE_TYPE_ID = ?";
					int recordCount =  Integer.parseInt(session.createSQLQuery(qry).setString(0, leaveBean.getSfID()).setString(1, CPSConstants.STATUSCANCELLED)
							.setString(2, CPSConstants.STATUSDECLINED).setString(3, CPSConstants.STATUSCONVERTED).setString(4, leaveBean.getFromDate())
							.setString(5, leaveBean.getToDate()).setString(6, leaveBean.getFromDate()).setString(7, leaveBean.getToDate()).setString(8, leaveBean.getFromDate())
							.setString(9, leaveBean.getToDate()).setString(10, leaveBean.getFromDate()).setString(11, leaveBean.getToDate()).setInteger(12, Integer.parseInt(CPSConstants.CL))
							.uniqueResult().toString());
					if (recordCount == 1) {
						List<KeyValueDTO> keyRecord = (List<KeyValueDTO>) session.createSQLQuery("SELECT from_half_day_flag AS name, to_half_day_flag AS key, from_date AS flag, to_date AS value FROM (SELECT sfid, leave_type_id, from_date, to_date, from_half_day_flag, to_half_day_flag, no_of_days FROM LEAVE_REQUEST_DETAILS WHERE SFID = ? AND STATUS NOT IN (?, ?, ?) AND (TO_CHAR(FROM_DATE, 'DD-Mon-YYYY') = ? OR TO_CHAR(TO_DATE, 'DD-Mon-YYYY') = ? OR FROM_DATE BETWEEN TO_DATE(?, 'DD-Mon-YYYY') AND TO_DATE(?, 'DD-MON-YYYY') OR TO_DATE BETWEEN TO_DATE(?, 'DD-Mon-YYYY') AND TO_DATE(?, 'DD-MON-YYYY') OR TO_DATE(?, 'DD-Mon-YYYY') BETWEEN FROM_DATE AND TO_DATE OR TO_DATE(?, 'DD-Mon-YYYY') BETWEEN FROM_DATE AND TO_DATE)) WHERE (TRUNC(NO_OF_DAYS, 0) = NO_OF_DAYS OR TRUNC(NO_OF_DAYS, 0) != NO_OF_DAYS) AND LEAVE_TYPE_ID = ?").addScalar("name", Hibernate.STRING).addScalar("key", Hibernate.STRING).addScalar("flag", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, leaveBean.getSfID()).setString(1, CPSConstants.STATUSCANCELLED).setString(2, CPSConstants.STATUSDECLINED).setString(3, CPSConstants.STATUSCONVERTED).setString(4, leaveBean.getFromDate()).setString(5, leaveBean.getToDate()).setString(6, leaveBean.getFromDate()).setString(7, leaveBean.getToDate()).setString(8, leaveBean.getFromDate()).setString(9, leaveBean.getToDate()).setString(10, leaveBean.getFromDate()).setString(11, leaveBean.getToDate()).setInteger(12, Integer.parseInt(CPSConstants.CL)).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
						if (!CPSUtils.isNullOrEmpty(keyRecord)) {
							if (keyRecord.size() == 1 && (keyRecord.get(0).getName().equals(CPSConstants.Y) || keyRecord.get(0).getKey().equals(CPSConstants.Y))) {
								if (CPSUtils.compareStrings(CPSUtils.formattedDate(keyRecord.get(0).getFlag()), CPSUtils.formattedDate(keyRecord.get(0).getValue()))) {
									if (keyRecord.get(0).getName().equals(leaveBean.getFromHalfDayFlag())) {
										status = false;
									} else if (leaveBean.getFromHalfDayFlag().equals(CPSConstants.N) && leaveBean.getToHalfDayFlag().equals(CPSConstants.N)) {
										status = false;
									}
								} else {
									status = false;
								}
							} else {
								status = false;
							}
						} else {
							status = false;
						}
					} else {
						status = false;
					}
				} else {
					status = false;
				}
				if (!status) {*/
					leaveBean.setResult(commonConstraints.setResultDetails(CPSConstants.FAILED));
					leaveBean.setRemarks(CPSConstants.LEAVEDUPLICATE);
				//}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	/**
	 * While converting leaves this method will be called
	 * 
	 * @param date
	 * @param sfid
	 * @param leaveTypeID
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public LeaveApplicationBean checkOtherLeaveMapping(final String date, final String sfid, final String leaveTypeID, final String type) throws Exception {
		LeaveApplicationBean leaveBean = new LeaveApplicationBean();
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setSfID(sfid);
			leaveBean.setDate(date);
			leaveBean.setDateType(type);
			leaveBean.setLeaveType(leaveTypeID);
			leaveBean.setSelectedLeaveDetails((LeaveManagementBean) session.createCriteria(LeaveManagementBean.class).add(Expression.eq("status", 1))
					.add(Expression.eq("leaveTypeId", leaveTypeID)).uniqueResult());
			leaveBean = checkOtherLeaveMapping(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean checkOtherLeaveMapping(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		boolean status = true;
		leaveBean.setStartDate(leaveBean.getDate());
		String leaveTypeID1 = null;
		String leaveTypeID2 = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = hibernateUtils.getSession();

			String checkWorkingDay = "select case when (select to_char(to_date(?, 'DD-MON-YYYY'), 'DY') from dual) = 'SUN' or (select to_char(to_date(?, 'DD-MON-YYYY'), 'DY') "
					+ "from dual) = 'SAT' or (select count(*) from holidays_master where status = 1 and holiday_date = to_date(?, 'dd-mon-yy')) > 0 then "
					+ "(select to_char(to_date(?, 'DD-MON-YYYY'), 'DD-MON-YYYY') from dual) else null end as checkResult from dual";
			sb.append("select leave_type_id leaveTypeID, no_of_days noOfDays from leave_request_details where ? between from_date and to_date and sfid = ? "
					+ "and status not in(?, ?, ?) and convert_ref_id is null and ");
			if (CPSUtils.compareStrings(leaveBean.getDateType(), CPSConstants.PREVIOUS)) {
				sb.append("next_holidays = 0 and ((from_half_day_flag = 'N' and to_half_day_flag = 'N') or (from_half_day_flag = 'N' and to_half_day_flag = 'Y'))");
			} else {
				sb.append("prev_holidays = 0 and ((from_half_day_flag = 'N' and to_half_day_flag = 'N') or (from_half_day_flag = 'Y' and to_half_day_flag = 'N')) ");
			}
			// If any half day flag is Y then we should check
			if ((CPSUtils.compareStrings(leaveBean.getFromHalfDayFlag(), CPSConstants.Y) && CPSUtils.compareStrings(leaveBean.getDateType(), CPSConstants.NEXT))
					|| (CPSUtils.compareStrings(leaveBean.getToHalfDayFlag(), CPSConstants.Y) && CPSUtils.compareStrings(leaveBean.getDateType(), CPSConstants.PREVIOUS))) {
				sb.append(" and 1 != 1");
			}
			// String anyOtherLeave = "select leave_type_id,no_of_days from leave_request_details where ? between from_date and to_date and sfid=? and status not in(?,?) and ";
			while (status) {
				if (CPSUtils.compareStrings(leaveBean.getDateType(), CPSConstants.PREVIOUS)) {
					leaveBean.setDate(CPSUtils.previousDate(leaveBean.getDate()));
				} else {
					leaveBean.setDate(CPSUtils.nextDate(leaveBean.getDate()));
				}
				String checkResult = (String) session.createSQLQuery(checkWorkingDay).setString(0, leaveBean.getDate()).setString(1, leaveBean.getDate())
						.setString(2, leaveBean.getDate()).setString(3, leaveBean.getDate()).uniqueResult();
				if (CPSUtils.isNullOrEmpty(checkResult)) {
					status = false;
					LeaveRequestBean leaveReqBean = (LeaveRequestBean) session.createSQLQuery(sb.toString()).addScalar("leaveTypeID", Hibernate.STRING).addScalar("noOfDays", Hibernate.STRING)
							.setResultTransformer(Transformers.aliasToBean(LeaveRequestBean.class)).setString(0, leaveBean.getDate()).setString(1, leaveBean.getSfID())
							.setString(2, CPSConstants.STATUSCANCELLED).setString(3, CPSConstants.STATUSDECLINED).setString(4, CPSConstants.STATUSCONVERTED).uniqueResult();

					if (!CPSUtils.isNull(leaveReqBean)) {
						// other leave was applied on that day, so check the mapping
						if (!CPSUtils.compareStrings(leaveBean.getLeaveType(), leaveReqBean.getLeaveTypeID())) {
							String endResult = null;
							leaveBean.setResult(CPSConstants.SUCCESS);
							if (CPSUtils.compareStrings(leaveBean.getDateType(), CPSConstants.PREVIOUS)) {
								leaveTypeID1 = leaveReqBean.getLeaveTypeID();
								leaveTypeID2 = leaveBean.getLeaveType();
								endResult = checkMapping(leaveReqBean.getLeaveTypeID(), leaveBean.getLeaveType());
								if (CPSUtils.compareStrings(CPSConstants.SUCCESS, endResult)) {
									if (leaveBean.getSelectedLeaveDetails() != null && !CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.NOTAPPLICABLE)) {
										leaveBean = checkInterveningFlag(leaveBean);
										leaveBean.setTotalNumberOfDays(Float.parseFloat(leaveReqBean.getNoOfDays()) + leaveBean.getTotalNumberOfDays() + CPSUtils.convertToFloat(leaveBean.getHolidays()));
										leaveBean = checkMaxNoOfLeaves(leaveBean);
									}
								} else {
									leaveBean.setResult(commonConstraints.setResultDetails(endResult));
								}
							} else if (CPSUtils.compareStrings(leaveBean.getDateType(), CPSConstants.NEXT)) {
								leaveTypeID1 = leaveBean.getLeaveType();
								leaveTypeID2 = leaveReqBean.getLeaveTypeID();
								endResult = checkMapping(leaveBean.getLeaveType(), leaveReqBean.getLeaveTypeID());
								if (CPSUtils.compareStrings(CPSConstants.SUCCESS, endResult)) {
									if (leaveBean.getSelectedLeaveDetails() != null && !CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.NOTAPPLICABLE)) {
										leaveBean = checkInterveningFlag(leaveBean);
										leaveBean.setTotalNumberOfDays(Float.parseFloat(leaveReqBean.getNoOfDays()) + leaveBean.getTotalNumberOfDays() + CPSUtils.convertToFloat(leaveBean.getHolidays()));
										leaveBean = checkMaxNoOfLeaves(leaveBean);
									}
								} else {
									leaveBean.setResult(commonConstraints.setResultDetails(endResult));
								}
							}
							if (CPSUtils.compareStrings(CPSConstants.FAILED, leaveBean.getResult())) {
								leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.NOMAPPING.replace("%1%", getLeaveType(leaveTypeID1)).replace("%2%",
										getLeaveType(leaveTypeID2))));
							}
						} else {
							// The user as applied the same leave continuously, so check the intervening flag, If the flag is 'Y' then we should 
							// give a response that we are adding holidays in between days also
							if (leaveBean.getSelectedLeaveDetails() != null && !CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.NOTAPPLICABLE)) {
								leaveBean = checkInterveningFlag(leaveBean);
								// Check whether total leaves or less than the max number of leaves or not
								leaveBean.setTotalNumberOfDays(Float.parseFloat(leaveReqBean.getNoOfDays()) + leaveBean.getTotalNumberOfDays() + CPSUtils.convertToFloat(leaveBean.getHolidays()));
								leaveBean = checkMaxNoOfLeaves(leaveBean);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	private LeaveApplicationBean checkMaxNoOfLeaves(LeaveApplicationBean leaveBean) throws Exception {
		try {
			if (!CPSUtils.isNullOrEmpty(leaveBean.getSelectedLeaveDetails().getMaxPerSPell())
					&& CPSUtils.convertToFloat(leaveBean.getSelectedLeaveDetails().getMaxPerSPell()) < leaveBean.getTotalNumberOfDays()) {
				leaveBean.setResult(commonConstraints.setResultDetails(CPSConstants.FAILED));
				leaveBean.setRemarks(CPSConstants.MAXLEAVESPERSPELLEXP.replace("%1%", leaveBean.getSelectedLeaveDetails().getMaxPerSPell()));
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public String checkMappingArray(ArrayList<String> leaves) throws Exception {
		String message = null;
		try {
			for (Iterator<String> it = leaves.iterator(); it.hasNext();) {
				String[] leaveTypeIDs = it.next().split("#");

				if (CPSUtils.compareStrings(checkMapping(leaveTypeIDs[0], leaveTypeIDs[1]), CPSConstants.FAILED)) {
					message = commonConstraints.setRemarkDetails(CPSConstants.NOMAPPING.replace("%1%", getLeaveType(leaveTypeIDs[0])).replace("%2%", getLeaveType(leaveTypeIDs[1])), message);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	private String getLeaveType(final String leaveTypeID) throws Exception {
		Session session = null;
		String leaveType = null;
		try {
			session = hibernateUtils.getSession();
			leaveType = ((LeaveTypeDTO) session.get(LeaveTypeDTO.class, Integer.valueOf(leaveTypeID))).getLeaveType().toString();
		} catch (Exception e) {
			throw e;
		}
		return leaveType;

	}

	public String checkMapping(final String leaveTypeId1, final String leaveTypeId2) throws Exception {
		String message = CPSConstants.SUCCESS;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			int result = Integer.valueOf(session.createSQLQuery("select count(*)||'' cnt from leave_relation_mappings where status = 1 and leave_type_id1 = ? and leave_type_id2 = ?")
					.setString(0, leaveTypeId1).setString(1, leaveTypeId2).uniqueResult().toString());
			if (result == 0) {
				// no mapping exists, so the user should not apply this leave
				message = CPSConstants.FAILED;
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public LeaveApplicationBean checkInterveningFlag(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		String diff = "0";
		try {
			session = hibernateUtils.getSession();
			if (CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getHolidayIntFlag(), CPSConstants.Y)) {
				// We should consider days between leaves
				if (CPSUtils.compareStrings(leaveBean.getDateType(), CPSConstants.PREVIOUS)) {
					diff = session.createSQLQuery("select count(*) from (select to_char(to_date(?, 'dd-mon-yyyy') + rownum - 1, 'DD-MON-YYYY') dates "
								+ "from all_objects where rownum <= (to_date(?, 'dd-mon-yyyy') - 1) - to_date(?, 'dd-mon-yyyy') + 1) where (select count(*) "
								+ "from leave_request_details where sfid = ? and convert_ref_id is null and status not in (?, ?) and dates between "
								+ "from_date and to_date) = 0").setString(0, leaveBean.getDate()).setString(1, leaveBean.getStartDate()).setString(2, leaveBean.getDate())
								.setString(3, leaveBean.getSfID()).setString(4, CPSConstants.STATUSDECLINED).setString(5, CPSConstants.STATUSCANCELLED).uniqueResult().toString();
					if (Integer.valueOf(diff) > 0) {
						if (CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.Y)
								|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.EOL)
								|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.EOLWOMC)) {
							leaveBean.setPrevHolidays(diff);
						} else if (CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.N)) {
							leaveBean.setPrevHolidays(String.valueOf(Float.valueOf(diff) * Float.valueOf(leaveBean.getSelectedLeaveDetails().getDebitMappingLeaves())));
						}
						leaveBean.setHolidays(leaveBean.getPrevHolidays());
						leaveBean.setResult(commonConstraints.setResultDetails(CPSConstants.SUCCESS));
						leaveBean.setRemarks((CPSConstants.CONTINUOUSLEAVESAPPLIED.replace("%1%", leaveBean.getHolidays() + " " + leaveBean.getSelectedLeaveDetails().getLeaveTypeDetails().getCode()))
								.replace("%2%", leaveBean.getSelectedLeaveDetails().getLeaveTypeDetails().getCode()));
						leaveBean.setPreviousDays(Integer.parseInt(diff));
					}
				} else {
					diff = session.createSQLQuery("select count(*) from (select to_char(to_date(?, 'dd-mon-yyyy') + rownum, 'DD-MON-YYYY') dates from "
							+ "all_objects where rownum <= (to_date(?, 'dd-mon-yyyy') - 1) - to_date(?, 'dd-mon-yyyy') + 1) where (select count(*) "
							+ "from leave_request_details where sfid = ? and convert_ref_id is null and status not in (?, ?) and dates between "
							+ "from_date and to_date) = 0").setString(0, leaveBean.getStartDate()).setString(1, leaveBean.getDate()).setString(2, leaveBean.getStartDate())
							.setString(3, leaveBean.getSfID()).setString(4, CPSConstants.STATUSDECLINED).setString(5, CPSConstants.STATUSCANCELLED).uniqueResult().toString();
					if (Integer.valueOf(diff) > 0) {
						if (CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.Y)
								|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.EOL)
								|| CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getLeaveTypeId(), CPSConstants.EOLWOMC)) {
							leaveBean.setNextHolidays(diff);
						} else if (CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getDirectDebitFlag(), CPSConstants.N)) {
							leaveBean.setNextHolidays(String.valueOf(Float.valueOf(diff) * Float.valueOf(leaveBean.getSelectedLeaveDetails().getDebitMappingLeaves())));
						}

						if (!CPSUtils.isNullOrEmpty(leaveBean.getHolidays())) {
							leaveBean.setHolidays(String.valueOf(Float.valueOf(leaveBean.getHolidays()) + Float.valueOf(leaveBean.getNextHolidays())));
						} else {
							leaveBean.setHolidays(leaveBean.getNextHolidays());
						}
						leaveBean.setResult(commonConstraints.setResultDetails(CPSConstants.SUCCESS));
						leaveBean.setRemarks((CPSConstants.CONTINUOUSLEAVESAPPLIED.replace("%1%", leaveBean.getHolidays() + " " + leaveBean.getSelectedLeaveDetails().getLeaveTypeDetails().getCode())
								.replace("%2%", leaveBean.getSelectedLeaveDetails().getLeaveTypeDetails().getCode())));
						leaveBean.setNextDays(Integer.parseInt(diff));
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
}
