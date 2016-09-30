package com.callippus.web.business.leave.request;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dto.EmpLeaveSpellsDTO;

@Service
public class NormalLeaveConstraints {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CommonConstraints commonConstraints;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public LeaveApplicationBean leaveConstraints(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		EmpLeaveSpellsDTO empSpellsDTO = null;
		leaveBean.setRequestedDate(CPSUtils.getCurrentDateWithTime());
		try {
			session = hibernateUtils.getSession();
			empSpellsDTO = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("sfID", leaveBean.getSfID())).add(
					Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getLeaveType()))).uniqueResult();

			if (!(CPSUtils.isNullOrEmpty(leaveBean.getSelectedLeaveDetails().getSpellsInService()) || CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getSpellsInService(), "0"))) {
				// No of spells in entire service was configured for this leave, so we should check this constraint
				if (!CPSUtils.isNull(empSpellsDTO) && empSpellsDTO.getServiceSpellsCount() >= Integer.parseInt(leaveBean.getSelectedLeaveDetails().getSpellsInService())) {
					leaveBean.setResult(CPSConstants.FAILED);
					leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.SPELLSINENTIRESERVICE.replace("%1%", leaveBean.getSelectedLeaveDetails().getSpellsInService())));
				}
			}
			if (!(CPSUtils.isNullOrEmpty(leaveBean.getSelectedLeaveDetails().getSpellsPerYear()) || CPSUtils.compareStrings(leaveBean.getSelectedLeaveDetails().getSpellsPerYear(), "0")) && !CPSUtils.compareStrings(CPSConstants.FAILED, leaveBean.getResult())) {
				// No of spells per year was configured for this leave, so we should check this constraint
				if (!CPSUtils.isNull(empSpellsDTO) && empSpellsDTO.getYearSpellsCount() >= Integer.parseInt(leaveBean.getSelectedLeaveDetails().getSpellsPerYear())) {
					if (CPSUtils.compareStrings(String.valueOf(empSpellsDTO.getLeaveTypeID()), CPSConstants.PL)) {
						// Added by Naresh for checking gap between paternity leave dates
						String result = commonConstraints.checkPaternityDates(leaveBean.getSfID(), leaveBean.getFromDate(), Integer.parseInt(leaveBean.getLeaveType()));
						int dateResult = CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(result.split("#")[1]), leaveBean.getRequestedDate());
							if (empSpellsDTO.getYearSpellsCount() >= 1 && dateResult == -1) {
								if (Integer.parseInt(result.split("#")[0]) != 0) {
									leaveBean.setResult(CPSConstants.FAILED);
									leaveBean.setRemarks("The time period for paternity leaves request should be minimum of one year");
								}
							} else {
								leaveBean.setResult(CPSConstants.FAILED);
								leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.SPELLSPERYEAR.replace("%1%", leaveBean.getSelectedLeaveDetails().getSpellsPerYear())));
							}
					} else if (CPSUtils.compareStrings(String.valueOf(empSpellsDTO.getLeaveTypeID()), CPSConstants.ADOPT)) {
						// Added by Naresh for checking Adoption dates for children
						String result = commonConstraints.checkAdoptionDates(leaveBean.getSfID(), Integer.parseInt(leaveBean.getLeaveType()));
						if (empSpellsDTO.getYearSpellsCount() >= 1 && !CPSUtils.isNullOrEmpty(result)) {
							int dateResult = CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(result), CPSUtils.convertStringToDate(leaveBean.getAdditionalData()));
							if (dateResult == 0) {
								leaveBean.setResult(CPSConstants.FAILED);
								leaveBean.setRemarks("Not possible to apply for same adoption date as you have already requested");
							}
						} else {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.SPELLSPERYEAR.replace("%1%", leaveBean.getSelectedLeaveDetails().getSpellsPerYear())));
						}
					} else if (CPSUtils.compareStrings(String.valueOf(empSpellsDTO.getLeaveTypeID()), CPSConstants.CCL) || CPSUtils.compareStrings(String.valueOf(empSpellsDTO.getLeaveTypeID()), CPSConstants.SL)) {
						if (Integer.parseInt(leaveBean.getFromDate().split("-")[2]) <=  Integer.parseInt(CPSUtils.getCurrentYear())) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.SPELLSPERYEAR.replace("%1%", leaveBean.getSelectedLeaveDetails().getSpellsPerYear())));
						} else {
							if (CPSUtils.compareStrings(String.valueOf(empSpellsDTO.getLeaveTypeID()), CPSConstants.CCL)) {
								String result = (String) session.createSQLQuery("SELECT COUNT(1) FROM leave_request_details WHERE sfid = ? AND leave_type_id = ? AND status NOT IN (6, 9) "
										+ "AND convert_ref_id IS NULL AND TO_NUMBER(TO_CHAR(from_date, 'YYYY')) = TO_NUMBER(TO_CHAR(sysdate, 'YYYY')) + 1")
										.setString(0, leaveBean.getSfID()).setInteger(1, Integer.parseInt(leaveBean.getLeaveType())).uniqueResult().toString();
								if (Integer.parseInt(result) >= Integer.parseInt(leaveBean.getSelectedLeaveDetails().getSpellsPerYear())) {
									leaveBean.setResult(CPSConstants.FAILED);
									leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.SPELLSPERYEAR.replace("%1%", leaveBean.getSelectedLeaveDetails().getSpellsPerYear())));
								}
							}
						}
					} else {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.SPELLSPERYEAR.replace("%1%", leaveBean.getSelectedLeaveDetails().getSpellsPerYear())));
					}
				} else if (!CPSUtils.isNull(empSpellsDTO) && CPSUtils.compareStrings(String.valueOf(empSpellsDTO.getLeaveTypeID()), CPSConstants.ADOPT)) {
					// Added by Naresh for checking Adoption dates for children
					String result = commonConstraints.checkAdoptionDates(leaveBean.getSfID(), Integer.parseInt(leaveBean.getLeaveType()));
					if (empSpellsDTO.getYearSpellsCount() >= 1 && !CPSUtils.isNullOrEmpty(result)) {
						int dateResult = CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(result), CPSUtils.convertStringToDate(leaveBean.getAdditionalData()));
						if (dateResult == 0) {
							leaveBean.setResult(CPSConstants.FAILED);
							leaveBean.setRemarks("Not possible to apply for same adoption date as you have already requested");
						}
					}
				} // End
			}

			/**
			 * If the leave type is LND or CML & the request was applied without medical certificate, then we should check the constraints
			 */
			if ((CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.LND) && CPSUtils.compareStrings(leaveBean.getMcExceptionID(), commonDataDAO.getConfigurationValue(CPSConstants.LND_WO_MC_EXCEPTIONID)))
					|| CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.CML)
					&& CPSUtils.compareStrings(leaveBean.getMcExceptionID(), commonDataDAO.getConfigurationValue(CPSConstants.CML_WO_MC_EXCEPTIONID))) {
				empSpellsDTO = (EmpLeaveSpellsDTO) session.createCriteria(EmpLeaveSpellsDTO.class).add(Expression.eq("leaveTypeID", Integer.valueOf(leaveBean.getLeaveType())))
						.add(Expression.eq("sfID", leaveBean.getSfID())).uniqueResult();
				leaveBean.setAdditionalData(CPSConstants.WOMC);
				if ((CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.CML) && empSpellsDTO.getLeavesCount() + Float.valueOf(leaveBean.getNoOfDays()) > Float.valueOf(CPSConstants.MAXNOOFCMLSWOMC))) {
					leaveBean.setResult(CPSConstants.FAILED);
					leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.MAXNOOFDAYSCOUNTWOMC.replace("%1%", CPSConstants.MAXNOOFCMLSWOMC)));
				} else if (CPSUtils.compareStrings(leaveBean.getLeaveType(), CPSConstants.LND) && empSpellsDTO.getLeavesCount() + Float.valueOf(leaveBean.getNoOfDays()) > Float.valueOf(CPSConstants.MAXNOOFLNDSWOMC)) {
					leaveBean.setResult(CPSConstants.FAILED);
					leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.MAXNOOFDAYSCOUNTWOMC.replace("%1%", CPSConstants.MAXNOOFLNDSWOMC)));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
}