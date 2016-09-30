package com.callippus.web.leave.dao.request;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.leave.beans.request.LeaveWaterApplicationBean;
import com.callippus.web.leave.dao.admin.ILeaveWaterRequestDAO;
import com.callippus.web.leave.dto.ErpAvailableLeaveSaveDTO;

@Service
public class SQLLeaveWaterRequestDAO implements ILeaveWaterRequestDAO {

	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	Session session = null;

	@Override
	public LeaveWaterApplicationBean getEmpDetails(
			LeaveWaterApplicationBean leaveWaterBean) throws Exception {

		try {
			session = hibernateUtils.getSession();

			leaveWaterBean
					.setEmpDetailsList((EmployeeBean) session
							.createCriteria(EmployeeBean.class)
							.add(Expression.or(
									Expression.eq("userSfid",
											leaveWaterBean.getSfid()),
									Expression.eq("userSfid",
											leaveWaterBean.getSfID())))
							.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return leaveWaterBean;
	}

	public String getEligibleOrNot(String sfid) throws Exception {
		Session session = null;
		String ltcYearID = null;
		BigDecimal ltcYearID1 = null;

		try {
			session = hibernateUtils.getSession();
			
		/*	String query = "select ID from ERP_AVAILABLE_LEAVES where SFID='"
					+ sfid + "' ";*/
			
			String	 query="select MAX(ID) from ERP_EMPLOEE_LEAVES where SFID='"+sfid+"' ";
			 
			ltcYearID1 = (BigDecimal) session.createSQLQuery(query)
					.uniqueResult();
			if (ltcYearID1 != null) {
				ltcYearID = "Eligible";
			} else {
				ltcYearID = "NOTEligible";
			}

		} catch (Exception e) {
			throw e;
		}
		return ltcYearID;
	}
	
	@SuppressWarnings("unchecked")
	public String getLeaveDateChecking(String fromDate, String sfid,String amandRequest) throws Exception{


		List<ErpAvailableLeaveSaveDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		String datevalidOrNot=null;
		try{
			session=hibernateUtils.getSession();
			
if(amandRequest!=null){
	//System.out.println("amand request not null");
	sql="select NO_OF_DAYS AS noOfDays from ERP_LEAVE_REQUEST_DETAILS where '"+fromDate+"' between FROM_DATE and TO_DATE and SFID='"+sfid+"' and STATUS in('1','2') and REQUEST_ID != '"+amandRequest+"'";
	
}else{
			sql="select NO_OF_DAYS AS noOfDays from ERP_LEAVE_REQUEST_DETAILS where '"+fromDate+"' between FROM_DATE and TO_DATE and SFID='"+sfid+"' and STATUS in('1','2')";
}			
			finAdvList =session.createSQLQuery(sql).addScalar("noOfDays", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ErpAvailableLeaveSaveDTO.class)).list();
			
			int i=finAdvList.size();
			
			if( i>0){
				datevalidOrNot="NotVaild";
			}else{
				datevalidOrNot="Vaild";
			}
			
			
		}catch (Exception e) {
			throw e;
		}
		return datevalidOrNot;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String getLeaveNoOfDaysChecking(LeaveWaterApplicationBean leaveWaterBean) throws Exception{
		Session session=null;
		String validOrNot=null;
		BigDecimal availableLeaves = null;
		String query =null;

		try {
			session = hibernateUtils.getSession();
			 BigDecimal appliedLeaves = new BigDecimal(leaveWaterBean.getNoOfDays());
			if (CPSUtils.compareStrings(CPSConstants.AL,leaveWaterBean.getLeaveType())
					|| CPSUtils.compareStrings(CPSConstants.CLAL,leaveWaterBean.getLeaveType())) {
				// query = "select ANNUAL_LEAVES AS annualLeaves from ERP_AVAILABLE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' ";
				 
				 //ADDED 27/06/2016
				 query = "select NO_OF_DAYS AS noOfDays from ERP_EMPLOEE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' AND LEAVE_CODE='AL' "; 
				 
				 
				 
				 
			} else{
				 query = "select NO_OF_DAYS AS noOfDays from ERP_EMPLOEE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' AND LEAVE_CODE='"+leaveWaterBean.getLeaveType()+"' ";
			}
			
			
			/*
			
			else if (CPSUtils.compareStrings(CPSConstants.SLU,leaveWaterBean.getLeaveType())) {
				// query = "select SICK_LEAVES AS sickLeaves  from ERP_AVAILABLE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' ";
				 
				 
				 query = "select NO_OF_DAYS AS noOfDays from ERP_EMPLOEE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' AND LEAVE_CODE='"+leaveWaterBean.getLeaveType()+"' ";
				 
				 
			} else if (CPSUtils.compareStrings(CPSConstants.MLU,leaveWaterBean.getLeaveType())) {
				// query = "select MATERNITY_LEAVES AS maternityLeaves from ERP_AVAILABLE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' ";
				 
				 query = "select NO_OF_DAYS AS noOfDays from ERP_EMPLOEE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' AND LEAVE_CODE='"+leaveWaterBean.getLeaveType()+"' ";
				 
				 
			} else 	if (CPSUtils.compareStrings(CPSConstants.PLU,leaveWaterBean.getLeaveType())) {
				
				// query = "select PETERNITY_LEAVES AS peternityLeaves from ERP_AVAILABLE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' ";
				 
				 query = "select NO_OF_DAYS AS noOfDays from ERP_EMPLOEE_LEAVES where SFID='"+leaveWaterBean.getSfID()+"' AND LEAVE_CODE='"+leaveWaterBean.getLeaveType()+"' ";
				 
				 
			}*/
			availableLeaves = (BigDecimal) session.createSQLQuery(query).uniqueResult();
			int result = appliedLeaves.compareTo(availableLeaves); 
			if(result == -1 || result == 0){
				validOrNot="valid";
			}else{
				validOrNot="notvalid";
			}
		} catch (Exception e) {
			throw e;
		}
		return validOrNot;
	}

	
	@SuppressWarnings("unchecked")
	public String getCheckEnterDatesValidOrNot(LeaveWaterApplicationBean leaveWaterBean) throws Exception{


		List<ErpAvailableLeaveSaveDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		String validOrNot=null;
		try{
			session=hibernateUtils.getSession();
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			
			String date1=df.format(leaveWaterBean.getFromDate());
			
			String date2=df.format(leaveWaterBean.getToDate());
			

			sql="select NO_OF_DAYS AS noOfDays from ERP_LEAVE_REQUEST_DETAILS where FROM_DATE between '"+date1+"' and '"+date2+"'  and TO_DATE  between '"+date1+"' and '"+date1+"'  and SFID='"+leaveWaterBean.getSfID()+"' and STATUS in('1','2')";
					
			finAdvList =session.createSQLQuery(sql).addScalar("noOfDays", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ErpAvailableLeaveSaveDTO.class)).list();
			
			int i=finAdvList.size();
			
			if( i>0){
				validOrNot="notvalid";
			}else{
				validOrNot="valid";
			}
			
			
		}catch (Exception e) {
			throw e;
		}
		return validOrNot;
	}
	
	
	
	public String thisLeaveAvailableOrNotForU(String sfid,String leaveCode) throws Exception {
		Session session = null;
		String queryReturn = null;
		BigDecimal queryResult = null;
		String	 query=null;
		try {
			session = hibernateUtils.getSession();
			if(leaveCode=="CL" || leaveCode.equals("CL")){
				 query="select MAX(ID) from ERP_EMPLOEE_LEAVES where SFID='"+sfid+"' and LEAVE_CODE='AL' ";
				
			}else{
			
			 query="select MAX(ID) from ERP_EMPLOEE_LEAVES where SFID='"+sfid+"' and LEAVE_CODE='"+leaveCode+"' ";
			}
			 
			queryResult = (BigDecimal) session.createSQLQuery(query)
					.uniqueResult();
			if (queryResult != null) {
				queryReturn = "Eligible";
			} else {
				queryReturn = "NOTEligible";
			}

		} catch (Exception e) {
			throw e;
		}
		return queryReturn;
	}
	
	
	
	

}
