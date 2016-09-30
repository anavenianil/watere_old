package com.callippus.web.ltc.dao.request;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.ltc.beans.request.LTCWaterRequestBean;
import com.callippus.web.ltc.dto.LTCWaterRequestDTO;
import com.callippus.web.tada.dto.TadaWaterApprovalRequestDTO;
@Service
public class SQLLTCWaterRequestDAO implements ILTCWaterRequestDAO {
	
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	
	
	Session session = null;

	@Override
	public LTCWaterRequestBean getEmpDetails(
			LTCWaterRequestBean ltcWaterRequestBean) throws Exception {
		
		try {
			session = hibernateUtils.getSession();

			ltcWaterRequestBean.setEmpDetailsList((EmployeeBean) session
					.createCriteria(EmployeeBean.class)
					.add(Expression.or(
							Expression.eq("userSfid",
									ltcWaterRequestBean.getSfid()),
							Expression.eq("userSfid",
									ltcWaterRequestBean.getSfID())))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return ltcWaterRequestBean;
		
	}

	
	
	

	@Override
	public LTCWaterRequestBean getEmpDetailsOne(
			LTCWaterRequestBean ltcWaterRequestBean) throws Exception {
		
		try {
			session = hibernateUtils.getSession();

			
			
			
			Session session1 = null;
			String sql=null;
			session1=hibernateUtils.getSession();
			String sfid=ltcWaterRequestBean.getSfid();
			if(sfid==null){
				sfid=ltcWaterRequestBean.getSfID();
			}
			String requestId=ltcWaterRequestBean.getRequestId();
			
			LTCWaterRequestDTO ltcWaterRequestDTO1=new LTCWaterRequestDTO();
			
			sql="select ltc.SFID AS sfID  "
					+ " FROM LTC_WATER_REQUEST_DETAILS ltc "
					+ "WHERE ltc.REQUEST_ID="+requestId+"";
					
			ltcWaterRequestDTO1 = (LTCWaterRequestDTO) session1
					.createSQLQuery(sql)
					.addScalar("sfID", Hibernate.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(LTCWaterRequestDTO.class))
					.uniqueResult();
			
			System.out.println(ltcWaterRequestDTO1);
			
			
			ltcWaterRequestBean.setEmpDetailsList((EmployeeBean) session
					.createCriteria(EmployeeBean.class)
					.add(Expression.or(
							Expression.eq("userSfid",
									ltcWaterRequestDTO1.getSfID()),
							Expression.eq("userSfid",
									ltcWaterRequestDTO1.getSfID())))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return ltcWaterRequestBean;
		
	}

	
	
	
	
	
	
	
	@Override
	public LTCWaterRequestBean getDeptDetails(
			LTCWaterRequestBean ltcWaterRequestBean) throws Exception {
		
		
		
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			String sfid=ltcWaterRequestBean.getSfid();
			if(sfid==null){
				sfid=ltcWaterRequestBean.getSfID();
			}
			sql="select unique dm.department_name as deptName from emp_master em,departments_master dm,org_role_instance ori where dm.department_id=(select ori.department_id from org_role_instance ori where ori.org_role_id=(select em.office_id from emp_master em where em.sfid=?))";
			ltcWaterRequestBean.setDeptDTO((DepartmentsDTO)session.createSQLQuery(sql).addScalar("deptName", Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).uniqueResult());
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	
	

	@Override
	public LTCWaterRequestBean getDeptDetailsOne(
			LTCWaterRequestBean ltcWaterRequestBean) throws Exception {
		
		
		
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			
			Session session1 = null;
			session1 = null;
			session1 = hibernateUtils.getSession();
			//String sql = null;
			
	LTCWaterRequestDTO ltcWaterRequestDTO1=new LTCWaterRequestDTO();
			
			sql="select ltc.SFID AS sfID  "
					+ " FROM LTC_WATER_REQUEST_DETAILS ltc "
					+ "WHERE ltc.REQUEST_ID="+ltcWaterRequestBean.getRequestId()+"";
					
			ltcWaterRequestDTO1 = (LTCWaterRequestDTO) session1
					.createSQLQuery(sql)
					.addScalar("sfID", Hibernate.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(LTCWaterRequestDTO.class))
					.uniqueResult();
			
			System.out.println(ltcWaterRequestDTO1);
			
			
			
			
			
			String sfid=ltcWaterRequestDTO1.getSfID();
			if(sfid==null){
				sfid=ltcWaterRequestBean.getSfID();
			}
			sql="select unique dm.department_name as deptName from emp_master em,departments_master dm,org_role_instance ori where dm.department_id=(select ori.department_id from org_role_instance ori where ori.org_role_id=(select em.office_id from emp_master em where em.sfid=?))";
			ltcWaterRequestBean.setDeptDTO((DepartmentsDTO)session.createSQLQuery(sql).addScalar("deptName", Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(DepartmentsDTO.class)).uniqueResult());
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	
	
	
	
	@Override
	public LTCWaterRequestBean getCurrentWaterReqIdDetails(LTCWaterRequestBean ltcWaterRequestBean)throws Exception {
		String sql=null;
		LTCWaterRequestDTO ltcWaterRequestDTO=null;
		try{
			session=hibernateUtils.getSession();
			String sfid=ltcWaterRequestBean.getSfid();
			if(sfid==null){
				sfid=ltcWaterRequestBean.getSfID();
			}
		
			String requestId=ltcWaterRequestBean.getRequestId();
			
		
		/*	sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_NAME AS deptName,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt,"
					+ "tdrd.STATUS AS status   "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID="+requestId+"";*/
			
			
			//retrive data from db
			sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
					+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
					+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
					+ " ltc.STARTHOLIDAY AS startHoliday,  ltc.RETURNHOLIDAY AS returnHoliday, ltc.NO_OF_DAYS AS nod,  "
					+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
					+ " ltc.LEAVE_TYPE AS leaveType,  ltc.APPLIED_DATE AS applyDate, ltc.STATUS AS status, "
					+ " ltc.IPADDRESS AS ipAddress "
					+ " FROM LTC_WATER_REQUEST_DETAILS ltc where REQUEST_ID="+requestId+"";
					
					
		//	ltcWaterRequestDTO=(LTCWaterRequestDTO) session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("status", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			
			ltcWaterRequestDTO=(LTCWaterRequestDTO) session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("startHoliday", Hibernate.DATE).addScalar("returnHoliday",Hibernate.DATE).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("applyDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
		
			ltcWaterRequestBean.setLtcWaterRequestDTO(ltcWaterRequestDTO);
			
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	
	
	//newly added
	@Override
	public LTCWaterRequestBean getCurrentWaterReqIdDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception {
		String sql=null;
		LTCWaterRequestDTO ltcWaterRequestDTO=null;
		try{
			session=hibernateUtils.getSession();
			String sfid=ltcWaterRequestBean.getSfid();
			if(sfid==null){
				sfid=ltcWaterRequestBean.getSfID();
			}
			String requestId=ltcWaterRequestBean.getRequestId();
			
			//retrive data from db
			/*sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_NAME AS deptName,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, tdrd.AMT_TYPE AS cashorcheck,"
					+ "tdrd.STATUS AS status   "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID="+requestId+"";*/
			
			
			sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
					+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
					+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
					+ "  ltc.NO_OF_DAYS AS nod,  "
					+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
					+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
					+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt,ltc.AMOUNT_ADULTS AS amountAdults,ltc.AMOUNT_CHILDREN AS amountChildren,ltc.TOTAL_ADULTS_AMT AS adultsTotAmt,ltc.TOTAL_CHILDREN_AMT AS childrenTotAmt,ltc.STARTHOLIDAY AS startHoliday,ltc.RETURNHOLIDAY AS returnHoliday  "
					+ " FROM LTC_WATER_REQUEST_DETAILS ltc "
					+ "WHERE ltc.REQUEST_ID="+requestId+"";
					
			//ltcWaterRequestDTO=(LTCWaterRequestDTO) session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("cashorcheck", Hibernate.STRING).addScalar("status", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			//new
			ltcWaterRequestDTO=(LTCWaterRequestDTO)	session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("ipAddress", Hibernate.STRING).addScalar("totalTicketsAmt", Hibernate.INTEGER).addScalar("amountAdults", Hibernate.INTEGER).addScalar("amountChildren", Hibernate.INTEGER).addScalar("adultsTotAmt", Hibernate.INTEGER).addScalar("childrenTotAmt", Hibernate.INTEGER).addScalar("startHoliday", Hibernate.DATE).addScalar("returnHoliday", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			
			
			
			ltcWaterRequestBean.setLtcWaterRequestDTO(ltcWaterRequestDTO);
			
		
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	@Override
	public LTCWaterRequestBean getCurrentWaterSettlementDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception {
		String sql=null;
		LTCWaterRequestDTO ltcWaterRequestDTO=null;
		try{
			session=hibernateUtils.getSession();
			String sfid=ltcWaterRequestBean.getSfid();
			if(sfid==null){
				sfid=ltcWaterRequestBean.getSfID();
			}
			String requestId=ltcWaterRequestBean.getRequestId();
			
			//retrive data from db
		/*	sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_NAME AS deptName,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, tdrd.AMT_TYPE AS cashorcheck,"
					+ "tdrd.STATUS AS status ,tdrd.ACTUAL_EXPENDITURE AS actualExpenditure ,tdrd.SETTLEORREIM_AMT AS settleOrReimAmt       "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID="+requestId+"";
			ltcWaterRequestDTO=(LTCWaterRequestDTO) session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("cashorcheck", Hibernate.STRING).addScalar("status", Hibernate.STRING).addScalar("actualExpenditure", Hibernate.INTEGER).addScalar("settleOrReimAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			*/
			
			sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
					+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
					+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
					+ "  ltc.NO_OF_DAYS AS nod,  "
					+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
					+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
					+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt,ltc.AMOUNT_ADULTS AS amountAdults,ltc.AMOUNT_CHILDREN AS amountChildren,ltc.TOTAL_ADULTS_AMT AS adultsTotAmt,ltc.TOTAL_CHILDREN_AMT AS childrenTotAmt,ltc.STARTHOLIDAY AS startHoliday,ltc.RETURNHOLIDAY AS returnHoliday,  "
					+ "ltc.REF_LETTER_DATE AS refLetterDate,ltc.REF_LETTER_NO AS refLetterNo,ltc.SETTLEORREIM_AMT AS ltcsettleOrReimAmt,ltc.ACTUAL_EXPENDITURE AS ltcactualExpenditure,ltc.OTHER_AMT as otherAmt "
					+ " FROM LTC_WATER_REQUEST_DETAILS ltc "
					+ "WHERE ltc.REQUEST_ID="+requestId+"";
					
			ltcWaterRequestDTO=(LTCWaterRequestDTO)	session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("ipAddress", Hibernate.STRING).addScalar("totalTicketsAmt", Hibernate.INTEGER).addScalar("amountAdults", Hibernate.INTEGER).addScalar("amountChildren", Hibernate.INTEGER).addScalar("adultsTotAmt", Hibernate.INTEGER).addScalar("childrenTotAmt", Hibernate.INTEGER).addScalar("startHoliday", Hibernate.DATE).addScalar("returnHoliday", Hibernate.DATE).addScalar("refLetterDate", Hibernate.DATE).addScalar("refLetterNo", Hibernate.STRING).addScalar("ltcsettleOrReimAmt", Hibernate.INTEGER).addScalar("ltcactualExpenditure", Hibernate.INTEGER).addScalar("otherAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			
			
			ltcWaterRequestBean.setLtcWaterRequestDTO(ltcWaterRequestDTO);
			
		
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	
	@Override
	public LTCWaterRequestBean getCurrentWaterReimbursementDetail(LTCWaterRequestBean ltcWaterRequestBean)throws Exception {
		String sql=null;
		LTCWaterRequestDTO ltcWaterRequestDTO=null;
		try{
			session=hibernateUtils.getSession();
			String sfid=ltcWaterRequestBean.getSfid();
			if(sfid==null){
				sfid=ltcWaterRequestBean.getSfID();
			}
			String requestId=ltcWaterRequestBean.getRequestId();
			
			//retrive data from db
	/*		sql="select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_NAME AS deptName,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, tdrd.AMT_TYPE AS cashorcheck,"
					+ "tdrd.STATUS AS status ,tdrd.ACTUAL_EXPENDITURE AS actualExpenditure ,tdrd.SETTLEORREIM_AMT AS settleOrReimAmt       "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID="+requestId+"";*/
			
			
			sql="select ltc.REQUEST_ID AS requestId,ltc.DEPARTMENT_ID AS deptName,ltc.SFID AS sfID,  "
					+ "ltc.DESIGNATION_ID AS designation,  ltc.PHONE_NUMBER AS phnNo, ltc.LTC_TYPE AS typeOfLtc, "
					+ " ltc.LTC_YEAR_ID AS ltcYear, ltc.HOMETOWNADDR AS hometownAddr,  "
					+ "  ltc.NO_OF_DAYS AS nod,  "
					+ " ltc.NOOF_ADULTS_TICKETS AS noOfAdultsTickets,  ltc.NOOF_CHILDREN_TICKETS  AS noOfChildrenTickets,  "
					+ " ltc.LEAVE_TYPE AS leaveType,  ltc.STATUS AS status, "
					+ " ltc.IPADDRESS AS ipAddress,ltc.TOTAL_TICKETS_AMT AS totalTicketsAmt,ltc.AMOUNT_ADULTS AS amountAdults,ltc.AMOUNT_CHILDREN AS amountChildren,ltc.TOTAL_ADULTS_AMT AS adultsTotAmt,ltc.TOTAL_CHILDREN_AMT AS childrenTotAmt,ltc.STARTHOLIDAY AS startHoliday,ltc.RETURNHOLIDAY AS returnHoliday,  "
					+ "ltc.REF_LETTER_DATE AS refLetterDate,ltc.REF_LETTER_NO AS refLetterNo,ltc.SETTLEORREIM_AMT AS ltcsettleOrReimAmt,ltc.ACTUAL_EXPENDITURE AS ltcactualExpenditure,ltc.OTHER_AMT as otherAmt "
					+ " FROM LTC_WATER_REQUEST_DETAILS ltc "
					+ "WHERE ltc.REQUEST_ID="+requestId+"";
			
			
			
			//ltcWaterRequestDTO=(LTCWaterRequestDTO) session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("claimPurpose", Hibernate.STRING).addScalar("travellingTo", Hibernate.STRING).addScalar("fromDate", Hibernate.DATE).addScalar("toDate", Hibernate.DATE).addScalar("foodandAccmAmt", Hibernate.INTEGER).addScalar("daAmt",Hibernate.INTEGER).addScalar("taxiAmt", Hibernate.INTEGER).addScalar("transitAmt", Hibernate.INTEGER).addScalar("totalAmt", Hibernate.INTEGER).addScalar("noOfDays",Hibernate.INTEGER).addScalar("reason", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER).addScalar("cashorcheck", Hibernate.STRING).addScalar("status", Hibernate.STRING).addScalar("actualExpenditure", Hibernate.INTEGER).addScalar("settleOrReimAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			ltcWaterRequestDTO=(LTCWaterRequestDTO)	session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("deptName", Hibernate.STRING).addScalar("sfID", Hibernate.STRING).addScalar("designation", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("ltcYear", Hibernate.STRING).addScalar("hometownAddr", Hibernate.STRING).addScalar("nod", Hibernate.INTEGER).addScalar("noOfAdultsTickets", Hibernate.INTEGER).addScalar("noOfChildrenTickets", Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("ipAddress", Hibernate.STRING).addScalar("totalTicketsAmt", Hibernate.INTEGER).addScalar("amountAdults", Hibernate.INTEGER).addScalar("amountChildren", Hibernate.INTEGER).addScalar("adultsTotAmt", Hibernate.INTEGER).addScalar("childrenTotAmt", Hibernate.INTEGER).addScalar("startHoliday", Hibernate.DATE).addScalar("returnHoliday", Hibernate.DATE).addScalar("refLetterDate", Hibernate.DATE).addScalar("refLetterNo", Hibernate.STRING).addScalar("ltcsettleOrReimAmt", Hibernate.INTEGER).addScalar("ltcactualExpenditure", Hibernate.INTEGER).addScalar("otherAmt", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LTCWaterRequestDTO.class)).uniqueResult();
			
			ltcWaterRequestBean.setLtcWaterRequestDTO(ltcWaterRequestDTO);
			
		
		}catch(Exception e){
			throw e;
		}
		return ltcWaterRequestBean;
	}
	public String getLtcYear(String ltcYear,String sfid) throws Exception{
		Session session =null;
		String ltcYearID =null;
		try{
			session=hibernateUtils.getSession();
			String query="select LTC_YEAR_ID from LTC_WATER_REQUEST_DETAILS where LTC_YEAR_ID='"+ltcYear+"' and SFID='"+sfid+"' and STATUS IN('1','2') ";
			ltcYearID =(String)session.createSQLQuery(query).uniqueResult();
		}catch (Exception e) {
			throw e;
		}
		return ltcYearID;
	}

/*	
	public LTCWaterRequestBean saveTxnDeatils(String subJson,LTCWaterRequestBean ltcWaterRequestBean) throws Exception{
		Session session =null;
		LTCWaterRequestBean ltcYearID =null;
		try{
			session=hibernateUtils.getSession();
			//String query="select LTC_YEAR_ID from LTC_WATER_REQUEST_DETAILS where LTC_YEAR_ID='"+ltcYear+"' and SFID='"+sfid+"' ";
		//	ltcYearID =(String)session.createSQLQuery(query).uniqueResult();
		}catch (Exception e) {
			throw e;
		}
		return ltcYearID;
	}*/
	
	
	
}
