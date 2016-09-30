package com.callippus.web.leave.business.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.leave.beans.request.LeaveWaterApplicationBean;
import com.callippus.web.leave.dao.admin.ILeaveWaterRequestDAO;

@Service
public class LeaveWaterRequestBusiness {
	
	@Autowired
	ILeaveWaterRequestDAO leaveWaterRequestDAO;
	@Autowired
	private IComonObjectDAO commonObjectDAO;
	
	public LeaveWaterApplicationBean getEmpDetails(
			LeaveWaterApplicationBean leaveWaterBean) throws Exception{
		
		try{
			leaveWaterBean=leaveWaterRequestDAO.getEmpDetails(leaveWaterBean);
			
		}catch(Exception e){
			throw e;
		}
		return leaveWaterBean;
	}
	
	@SuppressWarnings("unchecked")
	public LeaveWaterApplicationBean getleaveYearsList(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		try {
			leaveWaterBean.setSetleaveYearsList(commonObjectDAO.getMasterData(CPSConstants.LEAVEYEARSDTO));
		} catch (Exception e) {
			throw e;
		}
		return leaveWaterBean;
	}
	
	@SuppressWarnings("unchecked")
	public LeaveWaterApplicationBean getAvailableLeaveTypes(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		try {
			leaveWaterBean.setErpAvailTypeleavesList(commonObjectDAO.getAvailLeaveData(CPSConstants.AVAILABLELEAVETYPES,leaveWaterBean));
		} catch (Exception e) {
			throw e;
		}
		return leaveWaterBean;
	}

	
	public String getEligibleOrNot(String sfid) throws Exception{
		String reason=null;
		try{
			reason=leaveWaterRequestDAO.getEligibleOrNot(sfid);
		}catch (Exception e) {
			throw e;
		}
		return reason;
	}
	
	
	public LeaveWaterApplicationBean getEligibleOrNot1(LeaveWaterApplicationBean leaveWaterBean,String sfid) throws Exception{
		String EorN=null;
		try{
			EorN=leaveWaterRequestDAO.getEligibleOrNot(sfid);
			leaveWaterBean.setEorN(EorN);
		}catch (Exception e) {
			throw e;
		}
		return leaveWaterBean;
	}

	public String getLeaveDateChecking1(Date fromDate, String sfid) throws Exception {
		
		String datevalidOrNot=null;

		try{
		
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			
			String date=df.format(fromDate);
			//again see this
			//datevalidOrNot=leaveWaterRequestDAO.getLeaveDateChecking(date,sfid);
			System.out.println(datevalidOrNot);
		}catch (Exception e) {
			throw e;
		}
		return datevalidOrNot;
	}

	public LeaveWaterApplicationBean getLeaveDateChecking(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		
		String datevalidOrNot=null;
		try{
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			
			String date=df.format(leaveWaterBean.getFromDate());
			datevalidOrNot=leaveWaterRequestDAO.getLeaveDateChecking(date,leaveWaterBean.getSfID(),leaveWaterBean.getAmendRequestId());
			//System.out.println(datevalidOrNot);
			leaveWaterBean.setType(datevalidOrNot);
			leaveWaterBean.setDateCheck(datevalidOrNot);
		}catch (Exception e) {
			throw e;
		}
		
		return leaveWaterBean;
		
	}
	
	
public LeaveWaterApplicationBean getLeaveToDateChecking(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
		
		String datevalidOrNot=null;
		try{
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			
			String date=df.format(leaveWaterBean.getToDate());
			datevalidOrNot=leaveWaterRequestDAO.getLeaveDateChecking(date,leaveWaterBean.getSfID(),leaveWaterBean.getAmendRequestId());
			//System.out.println(datevalidOrNot);
			leaveWaterBean.setType(datevalidOrNot);
			leaveWaterBean.setDateCheck(datevalidOrNot);
		}catch (Exception e) {
			throw e;
		}
		
		return leaveWaterBean;
		
	}

public LeaveWaterApplicationBean getLeaveNoOfDaysChecking(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
	
	String validOrNot=null;
	try{
		validOrNot=leaveWaterRequestDAO.getLeaveNoOfDaysChecking(leaveWaterBean);
		leaveWaterBean.setType(validOrNot);
		//leaveWaterBean.setDateCheck(validOrNot);
		if(leaveWaterBean.getType()=="valid"){
			
			validOrNot=leaveWaterRequestDAO.getCheckEnterDatesValidOrNot(leaveWaterBean);	
			leaveWaterBean.setDateCheck(validOrNot);
			
		}
		
	}catch (Exception e) {
		throw e;
	}
	
	return leaveWaterBean;
	
}
	

public LeaveWaterApplicationBean thisLeaveAvailableOrNotForU(LeaveWaterApplicationBean leaveWaterBean) throws Exception {
	
	String validOrNot=null;
	try{
		
		
		validOrNot=leaveWaterRequestDAO.thisLeaveAvailableOrNotForU(leaveWaterBean.getSfID(),leaveWaterBean.getLeaveType());
		leaveWaterBean.setType(validOrNot);
		leaveWaterBean.setDateCheck(validOrNot);
	}catch (Exception e) {
		throw e;
	}
	
	return leaveWaterBean;
	
}
	
	
}
