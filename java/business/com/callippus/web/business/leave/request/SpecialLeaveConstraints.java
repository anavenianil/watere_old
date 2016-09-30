package com.callippus.web.business.leave.request;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.request.LeaveApplicationBean;
import com.callippus.web.leave.dto.SpecialCasualLeaveDTO;

@Service
public class SpecialLeaveConstraints {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private CommonConstraints commonConstraints;

	public LeaveApplicationBean leaveConstraints(LeaveApplicationBean leaveBean) throws Exception {
		Session session = null;
		String applLeaves = null;
		try {
			session = hibernateUtils.getSession();
			leaveBean.setSeletedSpecialLeaveDetails((SpecialCasualLeaveDTO) session.get(SpecialCasualLeaveDTO.class, Integer.valueOf(leaveBean.getLeaveSubType())));
			if (CPSUtils.compareStrings(leaveBean.getSeletedSpecialLeaveDetails().getCategoryType(), CPSConstants.SPECIALCATEGORYTYPE1)) {
				applLeaves = session.createSQLQuery("select count(*) from leave_request_details where sfid = ? and convert_ref_id is null and leave_sub_type_id = ? and status in (?, ?, ?)").setString(0, leaveBean.getSfID())
						.setString(1, leaveBean.getLeaveSubType()).setString(2, CPSConstants.STATUSPENDING).setString(3, CPSConstants.STATUSCOMPLETED).setString(4, CPSConstants.STATUSSANCTIONED)
						.uniqueResult().toString();
				if (Integer.parseInt(applLeaves) == 1) { // Add second time remarks
					if (!CPSUtils.isNullOrEmpty(leaveBean.getSeletedSpecialLeaveDetails().getSecondTimeRemarks())) {
						leaveBean.setOtherRemarks(leaveBean.getSeletedSpecialLeaveDetails().getSecondTimeRemarks().trim());
					} else {
						leaveBean.setResult(CPSConstants.FAILED);
						leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.AVAILABLEONLYONETIME));
					}
				} else if (Integer.valueOf(applLeaves) > 1) { // Failed
					leaveBean.setResult(CPSConstants.FAILED);
					leaveBean.setRemarks(commonConstraints.setRemarkDetails(leaveBean.getRemarks(), CPSConstants.AVAILABLEONLYTWOTIMES));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return leaveBean;
	}
}
