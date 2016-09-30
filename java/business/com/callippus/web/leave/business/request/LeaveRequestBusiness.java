package com.callippus.web.leave.business.request;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.business.leave.request.CheckConstraints;
import com.callippus.web.business.requestprocess.LeaveRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.hrdg.file.dao.IFileDAO;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dao.request.ILeaveRequestDAO;

@Service
public class LeaveRequestBusiness {
	@Autowired
	private ILeaveRequestDAO leaveRequestDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private CheckConstraints checkConstraints;
	@Autowired
	private IFileDAO fileDAO;
	@Autowired
	private LeaveRequestProcess leaveRequestProcess;

	public LeaveApplicationBean getLeaveApplicationDetails(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getAvailableLeaves(leaveBean);
			if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.INVALID)) {
				leaveBean = leaveRequestDAO.getFamilyMembers(leaveBean);
				leaveBean = leaveRequestDAO.getAppliedLeaves(leaveBean);
				leaveBean = leaveRequestDAO.getFamilyImpactDetails(leaveBean);

				leaveBean.setExceptionDetailsList(leaveRequestDAO.getExceptionDetails());
				leaveBean.setLeaveCreditsList(leaveRequestDAO.getLeaveCreditsList(leaveBean.getSfID()));

				// special casual leaves
				leaveBean = leaveRequestDAO.getSpecialCasualList(leaveBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean getHolidays(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getHolidays(leaveBean);		
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public List<AddressBean> getAddressDetails(String sfid) throws Exception {
		List<AddressBean> addressDetails = null;
		try {
			addressDetails = leaveRequestDAO.getAddressDetails(sfid);
		} catch (Exception e) {
			throw e;
		}
		return addressDetails;
	}

	public LeaveApplicationBean getNoOfDays(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getNoOfDays(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean checkLeaveConstraints(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = checkConstraints.checkLeaveConstraints(leaveBean);
			// CL, EL -ve balance case - Avoiding possibility of -ve balance occurrence of CL, EL during script run : Naresh
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, leaveBean.getResult()) && (CPSUtils.compareStrings(CPSConstants.EL, leaveBean.getLeaveType()) || CPSUtils.compareStrings(CPSConstants.CL, leaveBean.getLeaveType()))) {
				String result = null;
				if (CPSUtils.compareStrings(CPSConstants.EL, leaveBean.getLeaveType()) && (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(leaveBean.getToDate()), leaveBean.getRequestedDate()) >= 0)) {
					CPSUtils.monthMap();
					int toMonthId = Integer.parseInt(CPSUtils.monthMap.get(leaveBean.getToDate().split("-")[1]));
					int currentMonthId = Integer.parseInt(CPSUtils.monthMap.get(CPSUtils.getCurrentDate().split("-")[1]));
					if (((1 <= currentMonthId && currentMonthId <= 6) && (7 <= toMonthId && toMonthId <= 12))
							|| ((7 <= currentMonthId && currentMonthId <= 12) && (1 <= toMonthId && toMonthId <= 6))) {
						result = leaveRequestDAO.checkBalance(leaveBean);
					}
				} else if (CPSUtils.compareStrings(CPSConstants.CL, leaveBean.getLeaveType()) && (Integer.parseInt(leaveBean.getToDate().split("-")[2]) > Integer.parseInt(CPSUtils.getCurrentYear()))) {
					result = leaveRequestDAO.checkBalance(leaveBean);
				}
				if (!CPSUtils.isNullOrEmpty(result)) {
					if (!CPSUtils.compareStrings(CPSConstants.SUCCESS, result)) {
						leaveBean.setResult(result.split("#")[0]);
						leaveBean.setRemarks("You cannot apply more than " + result.split("#")[1] + " leave(s) for the selected leave type.");
					} else {
						leaveBean.setResult(result);
					}
				}
			} else if (CPSUtils.compareStrings(CPSConstants.SL, leaveBean.getLeaveType()) && CPSUtils.compareStrings(CPSConstants.SUCCESS, leaveBean.getResult())) {
				String result = leaveRequestDAO.leaveImpact(leaveBean);
				if (!CPSUtils.compareStrings(CPSConstants.SUCCESS, result)) {
					leaveBean.setResult(result.split("#")[0]);
					leaveBean.setRemarks("You cannot apply more than " + result.split("#")[1] + " leave(s) for the selected leave sub-type.");
				} else {
					leaveBean.setResult(result);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean saveOrUpdateSpellsCount(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.saveOrUpdateSpellsCount(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public HashMap<String, String> getOfficeIDAndPhone(String sfid) throws Exception {
		HashMap<String, String> hmap = null;
		try {
			hmap = new HashMap<String, String>();
			hmap = leaveRequestDAO.getOfficeIDAndPhone(sfid);
		} catch (Exception e) {
			throw e;
		}
		return hmap;
	}

	public LeaveApplicationBean getLeaveDetails(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getLeaveDetails(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean checkRequests(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.checkRequests(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean getLeaveBalaceDetails(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getLeaveBalaceDetails(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	// end

	public LeaveApplicationBean getConfiguredConvertLeaves(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getConfiguredConvertLeaves(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean checkConvertLeaveConstraints(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.checkConvertLeaveConstraints(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;

	}

	public LeaveApplicationBean checkCancelLeaveConstraints(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.checkCancelLeaveConstraints(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;

	}

	public String getLeaveRequestPkValue(String requestID) throws Exception {
		String referenceID = null;
		try {
			referenceID = leaveRequestDAO.getLeaveRequestPkValue(requestID);
		} catch (Exception e) {
			throw e;
		}
		return referenceID;
	}

	public LeaveApplicationBean getLeaveCardDetails(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getLeaveCardDetails(leaveBean);
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public LeaveApplicationBean getEmployeeDetails(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getEmployeeDetails(leaveBean);
			leaveBean = leaveRequestDAO.getRetirementDate(leaveBean);
			leaveBean.setLastWorkingDay(leaveBean.getRetirementDate());
			if (CPSUtils.compareTwoDatesUptoDate(CPSUtils.convertStringToDate(leaveBean.getRetirementDate()), CPSUtils.convertStringToDate(CPSUtils.getCurrentDate())) == 1) {
				leaveBean.setRetirementDate(leaveBean.getDateOfJoining());	
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public String checkEmployee(String sfid) throws Exception {
		String result = null;
		try {
			result = commonDataDAO.checkEmployee(sfid);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public LeaveApplicationBean getLeaveTxnHomeDetails(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean.setYearsList(leaveRequestDAO.getYearsList());
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}

	public FilesBean getDBFile(int id) throws Exception {
		FilesBean file = null;
		try {
			file = fileDAO.downloadFromDatabase(id);
		} catch (Exception e) {
			throw e;
		}
		return file;
	}
	
	public LeaveApplicationBean getDefaultDetails(LeaveApplicationBean leaveBean) throws Exception {
		try {
			leaveBean = leaveRequestDAO.getAvailableLeaves(leaveBean);
			if (!CPSUtils.compareStrings(leaveBean.getResult(), CPSConstants.INVALID)) {
				leaveBean = leaveRequestDAO.getFamilyMembers(leaveBean);
				leaveBean = leaveRequestDAO.getFamilyImpactDetails(leaveBean);
				leaveBean.setExceptionDetailsList(leaveRequestDAO.getExceptionDetails());
				leaveBean.setLeaveCreditsList(leaveRequestDAO.getLeaveCreditsList(leaveBean.getSfID()));
				// special casual leaves
				leaveBean = leaveRequestDAO.getSpecialCasualList(leaveBean);
			}
		} catch(Exception e) {
			throw e;
		}
		return leaveBean;
	}
	
	public String cancelAppliedLeave(LeaveApplicationBean leaveAppBean) throws Exception {
		String result = null;
		LeaveRequestBean lrb = null;
		try {
			result = leaveRequestDAO.changeRecordStatus(leaveAppBean.getRequestID(), leaveAppBean.getSfID(), CPSConstants.CANCELLEAVERECORDSTATUS);
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, result)) {
				lrb = leaveRequestDAO.getLeaveDetails(leaveAppBean.getRequestID());
				leaveRequestProcess.creaditLeaves(null, null, lrb, null);
				leaveRequestProcess.saveOrUpdateLeaveSpells(lrb.getSfID(), lrb.getLeaveTypeID(), CPSConstants.SUBTRACT, lrb.getOtherRemarks());
				leaveRequestProcess.updateLeaveCountForWoMC(lrb, CPSConstants.SUBTRACT);
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public String rollBackAppliedLeaveCancellation(LeaveApplicationBean leaveAppBean) throws Exception {
		String result = null;
		LeaveRequestBean lrb = null;
		try {
			result = leaveRequestDAO.changeRecordStatus(leaveAppBean.getRequestID(), leaveAppBean.getSfID(), CPSConstants.ROLLBACKAPPLIEDLEAVECANCELLATION);
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, result)) {
				lrb = leaveRequestDAO.getLeaveDetails(leaveAppBean.getRequestID());
				leaveRequestProcess.updateLeaveCredits(lrb);
				leaveRequestProcess.saveOrUpdateLeaveSpells(lrb.getSfID(), lrb.getLeaveTypeID(), CPSConstants.ADD, null);
				leaveRequestProcess.updateLeaveCountForWoMC(lrb, CPSConstants.ADD);
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public String getLeaveFutureBound() throws Exception {
		String upperBound = null;
		try {
			upperBound = leaveRequestDAO.getLeaveFutureBound();
		} catch (Exception e) {
			throw e;
		}
		return upperBound;
	}
	
	public String getLeavePastBound() throws Exception {
		String upperBound = null;
		try {
			upperBound = leaveRequestDAO.getLeavePastBound();
		} catch (Exception e) {
			throw e;
		}
		return upperBound;
	}
}
