package com.callippus.web.leave.dao.admin;

import java.util.Date;

import com.callippus.web.leave.beans.request.LeaveWaterApplicationBean;



public interface ILeaveWaterRequestDAO {
	
	public LeaveWaterApplicationBean getEmpDetails(LeaveWaterApplicationBean leaveWaterBean)throws Exception;
	
	
	public String getEligibleOrNot(String sfid) throws Exception;
	
	public String getLeaveDateChecking(String fromDate, String sfid,String AmandRequest) throws Exception;
	
	public String getLeaveNoOfDaysChecking(LeaveWaterApplicationBean leaveWaterBean) throws Exception;
	
	public String getCheckEnterDatesValidOrNot(LeaveWaterApplicationBean leaveWaterBean) throws Exception;
	
	
	public String thisLeaveAvailableOrNotForU(String sfid,String leaveType) throws Exception;


	/*public String getLeaveNoOfDaysChecking(
			LeaveWaterApplicationBean leaveWaterBean);*/
	
	
	//public LeaveWaterApplicationBean getAvailableLeaves(LeaveWaterApplicationBean leaveWaterBean)throws Exception;
	
	

}
