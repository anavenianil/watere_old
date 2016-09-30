package com.callippus.web.tada.dao.request;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.leave.dto.ErpAvailableLeaveSaveDTO;
import com.callippus.web.tada.beans.request.TadaWaterRequestBean;
import com.callippus.web.tada.dto.TadaWaterApprovalRequestDTO;

@Service
public class SQLTadaWaterRequestDAO implements ITadaWaterRequestDAO {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	Session session = null;

	@Override
	public TadaWaterRequestBean getEmpDetails(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {

		try {
			session = hibernateUtils.getSession();

			tadaWaterRequestBean.setEmpDetailsList((EmployeeBean) session
					.createCriteria(EmployeeBean.class)
					.add(Expression.or(
							Expression.eq("userSfid",
									tadaWaterRequestBean.getSfid()),
							Expression.eq("userSfid",
									tadaWaterRequestBean.getSfID())))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

	@Override
	public TadaWaterRequestBean getEmpDetailsOne(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {

		try {
			session = hibernateUtils.getSession();

			Session session1 = null;
			session1 = null;
			session1 = hibernateUtils.getSession();
			String sql = null;

			// tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS
			// deptId,tdrd.SFID AS sfid
			TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO = new TadaWaterApprovalRequestDTO();
			sql = "select tada.SFID AS sfid   FROM TADA_WATER_ADV_REQUEST_DETAILS tada WHERE tada.REQUEST_ID="
					+ tadaWaterRequestBean.getRequestId() + "";
			tadaWaterApprovalRequestDTO = (TadaWaterApprovalRequestDTO) session1
					.createSQLQuery(sql)
					.addScalar("sfid", Hibernate.STRING)
					.setResultTransformer(
							Transformers
									.aliasToBean(TadaWaterApprovalRequestDTO.class))
					.uniqueResult();

			System.out.println(tadaWaterApprovalRequestDTO);

			/*
			 * tadaWaterRequestBean.setEmpDetailsList((EmployeeBean)
			 * session.createCriteria(EmployeeBean.class) .add(Expression.or(
			 * Expression.eq("userSfid", tadaWaterRequestBean.getSfid()),
			 * Expression.eq("userSfid", tadaWaterRequestBean.getSfID())))
			 * .uniqueResult());
			 */
			tadaWaterRequestBean.setEmpDetailsList((EmployeeBean) session
					.createCriteria(EmployeeBean.class)
					.add(Expression.or(Expression.eq("userSfid",
							tadaWaterApprovalRequestDTO.getSfid()), Expression
							.eq("userSfid",
									tadaWaterApprovalRequestDTO.getSfID())))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

	@Override
	public TadaWaterRequestBean getDeptDetails(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {
		String sql = null;
		try {
			session = hibernateUtils.getSession();
			String sfid = tadaWaterRequestBean.getSfid();
			if (sfid == null) {
				sfid = tadaWaterRequestBean.getSfID();
			}
			sql = "select unique dm.department_name as deptName from emp_master em,departments_master dm,org_role_instance ori where dm.department_id=(select ori.department_id from org_role_instance ori where ori.org_role_id=(select em.office_id from emp_master em where em.sfid=?))";
			tadaWaterRequestBean.setDeptDTO((DepartmentsDTO) session
					.createSQLQuery(sql)
					.addScalar("deptName", Hibernate.STRING)
					.setString(0, sfid)
					.setResultTransformer(
							Transformers.aliasToBean(DepartmentsDTO.class))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

	@Override
	public TadaWaterRequestBean getDeptDetailsOne(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {
		String sql = null;
		try {

			session = hibernateUtils.getSession();
			Session session1 = null;
			session1 = null;
			session1 = hibernateUtils.getSession();
			// String sql = null;

			// tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS
			// deptId,tdrd.SFID AS sfid
			TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO = new TadaWaterApprovalRequestDTO();
			sql = "select tada.SFID AS sfid   FROM TADA_WATER_ADV_REQUEST_DETAILS tada WHERE tada.REQUEST_ID="
					+ tadaWaterRequestBean.getRequestId() + "";
			tadaWaterApprovalRequestDTO = (TadaWaterApprovalRequestDTO) session1
					.createSQLQuery(sql)
					.addScalar("sfid", Hibernate.STRING)
					.setResultTransformer(
							Transformers
									.aliasToBean(TadaWaterApprovalRequestDTO.class))
					.uniqueResult();

			String sfid = tadaWaterApprovalRequestDTO.getSfid();
			if (sfid == null) {
				sfid = tadaWaterRequestBean.getSfID();
			}
			sql = "select unique dm.department_name as deptName from emp_master em,departments_master dm,org_role_instance ori where dm.department_id=(select ori.department_id from org_role_instance ori where ori.org_role_id=(select em.office_id from emp_master em where em.sfid=?))";
			tadaWaterRequestBean.setDeptDTO((DepartmentsDTO) session
					.createSQLQuery(sql)
					.addScalar("deptName", Hibernate.STRING)
					.setString(0, sfid)
					.setResultTransformer(
							Transformers.aliasToBean(DepartmentsDTO.class))
					.uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

	// newly added
	@Override
	public TadaWaterRequestBean getCurrentWaterReqIdDetails(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {
		String sql = null;
		TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO = null;
		try {
			session = hibernateUtils.getSession();
			String sfid = tadaWaterRequestBean.getSfid();
			if (sfid == null) {
				sfid = tadaWaterRequestBean.getSfID();
			}
			String requestId = tadaWaterRequestBean.getRequestId();

			// retrive data from db
			sql = "select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt,"
					+ "tdrd.STATUS AS status   "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID=" + requestId + "";
			tadaWaterApprovalRequestDTO = (TadaWaterApprovalRequestDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("deptId", Hibernate.INTEGER)
					.addScalar("sfid", Hibernate.STRING)
					.addScalar("desigId", Hibernate.INTEGER)
					.addScalar("phnNo", Hibernate.STRING)
					.addScalar("claimPurpose", Hibernate.STRING)
					.addScalar("travellingTo", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("foodandAccmAmt", Hibernate.INTEGER)
					.addScalar("daAmt", Hibernate.INTEGER)
					.addScalar("taxiAmt", Hibernate.INTEGER)
					.addScalar("transitAmt", Hibernate.INTEGER)
					.addScalar("totalAmt", Hibernate.INTEGER)
					.addScalar("noOfDays", Hibernate.INTEGER)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("ipAddress", Hibernate.STRING)
					.addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER)
					.addScalar("status", Hibernate.STRING)
					.setResultTransformer(
							Transformers
									.aliasToBean(TadaWaterApprovalRequestDTO.class))
					.uniqueResult();

			tadaWaterRequestBean
					.setTadaWaterApprovalRequestDTO(tadaWaterApprovalRequestDTO);

		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

	// newly added
	@Override
	public TadaWaterRequestBean getCurrentWaterReqIdDetail(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {
		String sql = null;
		TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO = null;
		try {
			session = hibernateUtils.getSession();
			String sfid = tadaWaterRequestBean.getSfid();
			if (sfid == null) {
				sfid = tadaWaterRequestBean.getSfID();
			}
			String requestId = tadaWaterRequestBean.getRequestId();

			// retrive data from db
			sql = "select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, tdrd.AMT_TYPE AS cashorcheck,"
					+ "tdrd.STATUS AS status   "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID=" + requestId + "";
			tadaWaterApprovalRequestDTO = (TadaWaterApprovalRequestDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("deptId", Hibernate.INTEGER)
					.addScalar("sfid", Hibernate.STRING)
					.addScalar("desigId", Hibernate.INTEGER)
					.addScalar("phnNo", Hibernate.STRING)
					.addScalar("claimPurpose", Hibernate.STRING)
					.addScalar("travellingTo", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("foodandAccmAmt", Hibernate.INTEGER)
					.addScalar("daAmt", Hibernate.INTEGER)
					.addScalar("taxiAmt", Hibernate.INTEGER)
					.addScalar("transitAmt", Hibernate.INTEGER)
					.addScalar("totalAmt", Hibernate.INTEGER)
					.addScalar("noOfDays", Hibernate.INTEGER)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("ipAddress", Hibernate.STRING)
					.addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER)
					.addScalar("cashorcheck", Hibernate.STRING)
					.addScalar("status", Hibernate.STRING)
					.setResultTransformer(
							Transformers
									.aliasToBean(TadaWaterApprovalRequestDTO.class))
					.uniqueResult();

			tadaWaterRequestBean
					.setTadaWaterApprovalRequestDTO(tadaWaterApprovalRequestDTO);

		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

	@Override
	public TadaWaterRequestBean getCurrentWaterSettlementDetail(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {
		String sql = null;
		TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO = null;
		try {
			session = hibernateUtils.getSession();
			String sfid = tadaWaterRequestBean.getSfid();
			if (sfid == null) {
				sfid = tadaWaterRequestBean.getSfID();
			}
			String requestId = tadaWaterRequestBean.getRequestId();

			// retrive data from db
			sql = "select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, tdrd.AMT_TYPE AS cashorcheck,"
					+ "tdrd.STATUS AS status ,tdrd.ACTUAL_EXPENDITURE AS actualExpenditure ,tdrd.SETTLEORREIM_AMT AS settleOrReimAmt       "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID=" + requestId + "";
			tadaWaterApprovalRequestDTO = (TadaWaterApprovalRequestDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("deptId", Hibernate.INTEGER)
					.addScalar("sfid", Hibernate.STRING)
					.addScalar("desigId", Hibernate.INTEGER)
					.addScalar("phnNo", Hibernate.STRING)
					.addScalar("claimPurpose", Hibernate.STRING)
					.addScalar("travellingTo", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("foodandAccmAmt", Hibernate.INTEGER)
					.addScalar("daAmt", Hibernate.INTEGER)
					.addScalar("taxiAmt", Hibernate.INTEGER)
					.addScalar("transitAmt", Hibernate.INTEGER)
					.addScalar("totalAmt", Hibernate.INTEGER)
					.addScalar("noOfDays", Hibernate.INTEGER)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("ipAddress", Hibernate.STRING)
					.addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER)
					.addScalar("cashorcheck", Hibernate.STRING)
					.addScalar("status", Hibernate.STRING)
					.addScalar("actualExpenditure", Hibernate.INTEGER)
					.addScalar("settleOrReimAmt", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers
									.aliasToBean(TadaWaterApprovalRequestDTO.class))
					.uniqueResult();

			tadaWaterRequestBean
					.setTadaWaterApprovalRequestDTO(tadaWaterApprovalRequestDTO);

		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

	@Override
	public TadaWaterRequestBean getCurrentWaterReimbursementDetail(
			TadaWaterRequestBean tadaWaterRequestBean) throws Exception {
		String sql = null;
		TadaWaterApprovalRequestDTO tadaWaterApprovalRequestDTO = null;
		try {
			session = hibernateUtils.getSession();
			String sfid = tadaWaterRequestBean.getSfid();
			if (sfid == null) {
				sfid = tadaWaterRequestBean.getSfID();
			}
			String requestId = tadaWaterRequestBean.getRequestId();

			// retrive data from db
			sql = "select tdrd.REQUEST_ID AS requestId,tdrd.DEPARTMENT_ID AS deptId,tdrd.SFID AS sfid, "
					+ "tdrd.DESIGNATION_ID AS desigId,  tdrd.PHONE_NUMBER AS phnNo, tdrd.CLAIM_PURPOSE AS claimPurpose, "
					+ "tdrd.TRAVELLING_TO AS travellingTo, tdrd.FROM_DATE AS fromDate,tdrd.TO_DATE AS toDate, "
					+ "tdrd.FOOD_ACCM_AMT AS foodandAccmAmt,  tdrd.DA_AMT AS daAmt,  tdrd.TAXI_AMT AS taxiAmt, "
					+ "tdrd.TRANSIT_AMT AS transitAmt,  tdrd.TOTAL_AMT AS totalAmt,  tdrd.NOOF_DAYS  AS noOfDays,  "
					+ "tdrd.REMARKS AS reason,  tdrd.IP_ADDRESS AS ipAddress, tdrd.PERDAY_FD_ACC_AMT AS perDayFoodandAccmAmt, tdrd.AMT_TYPE AS cashorcheck,"
					+ "tdrd.STATUS AS status ,tdrd.ACTUAL_EXPENDITURE AS actualExpenditure ,tdrd.SETTLEORREIM_AMT AS settleOrReimAmt       "
					+ "FROM TADA_WATER_ADV_REQUEST_DETAILS tdrd "
					+ "WHERE tdrd.REQUEST_ID=" + requestId + "";

			tadaWaterApprovalRequestDTO = (TadaWaterApprovalRequestDTO) session
					.createSQLQuery(sql)
					.addScalar("requestId", Hibernate.STRING)
					.addScalar("deptId", Hibernate.INTEGER)
					.addScalar("sfid", Hibernate.STRING)
					.addScalar("desigId", Hibernate.INTEGER)
					.addScalar("phnNo", Hibernate.STRING)
					.addScalar("claimPurpose", Hibernate.STRING)
					.addScalar("travellingTo", Hibernate.STRING)
					.addScalar("fromDate", Hibernate.DATE)
					.addScalar("toDate", Hibernate.DATE)
					.addScalar("foodandAccmAmt", Hibernate.INTEGER)
					.addScalar("daAmt", Hibernate.INTEGER)
					.addScalar("taxiAmt", Hibernate.INTEGER)
					.addScalar("transitAmt", Hibernate.INTEGER)
					.addScalar("totalAmt", Hibernate.INTEGER)
					.addScalar("noOfDays", Hibernate.INTEGER)
					.addScalar("reason", Hibernate.STRING)
					.addScalar("ipAddress", Hibernate.STRING)
					.addScalar("perDayFoodandAccmAmt", Hibernate.INTEGER)
					.addScalar("cashorcheck", Hibernate.STRING)
					.addScalar("status", Hibernate.STRING)
					.addScalar("actualExpenditure", Hibernate.INTEGER)
					.addScalar("settleOrReimAmt", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers
									.aliasToBean(TadaWaterApprovalRequestDTO.class))
					.uniqueResult();

			tadaWaterRequestBean
					.setTadaWaterApprovalRequestDTO(tadaWaterApprovalRequestDTO);

		} catch (Exception e) {
			throw e;
		}
		return tadaWaterRequestBean;
	}

}
