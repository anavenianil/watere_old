package com.callippus.web.dao.quarter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatDTO;
import com.callippus.web.beans.quarter.QuarterRequestBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;

@Service
public class SQLQuarterRequestDAO implements IQuarterRequestDAO {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	@Transactional(readOnly = true)
	public QuarterRequestBean getEmployeeDetails(QuarterRequestBean quarterBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
			quarterBean.setEmployeeDetails((EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", quarterBean.getSfID())).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		return quarterBean;
	}

	@Transactional(readOnly = true)
	public QuarterRequestBean getEmployeePaymentDetails(QuarterRequestBean quarterBean) throws Exception {
		Session session = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
			quarterBean.setEmployeePaymentDetails((EmpPaymentsDTO) session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq("sfid", quarterBean.getSfID())).uniqueResult());
		} catch (Exception e) {
			throw e;
		}
		 finally {
				ConnectionUtil.closeConnection(session, null, null);
			}
		return quarterBean;
	}

	@SuppressWarnings("unchecked")
	public List<QuarterTypeBean> getQuarterSubTypeList() throws Exception {
		Session session = null;
		List<QuarterTypeBean> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list = session.createCriteria(QuarterTypeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QuarterRequestBean getQuarterTypeList(QuarterRequestBean quarterBean) throws Exception {
		List<PayBillQuartersTypeMasterDTO> payBillQuartersTypeList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			/*payBillQuartersTypeList=session.createSQLQuery("SELECT PQTM.QUARTER_TYPE name,PQTM.ID id FROM payband_designation_mapping PDM,DESIGNATION_MASTER DM,EMP_MASTER EM,PAY_QUARTERS_TYPE_MASTER PQTM " +
					"WHERE DM.ID=PDM.DESIGNATION_ID AND EM.DESIGNATION_ID=DM.ID AND EM.STATUS=1 AND PDM.STATUS=1  AND EM.SFID=? AND PQTM.ID <= (SELECT QTM.ID FROM PAY_QUARTERS_TYPE_MASTER QTM " +
					"WHERE QTM.ID = (CASE WHEN PDM.GRADE_PAY BETWEEN 0 AND 2549 THEN 0 ELSE " +
					"(CASE WHEN PDM.GRADE_PAY BETWEEN 2550 AND 3049 THEN 1 ELSE " +
					"(CASE WHEN PDM.GRADE_PAY BETWEEN 3050 AND 5499 THEN 2 ELSE " +
					"(CASE WHEN PDM.GRADE_PAY BETWEEN 5500 AND 8499 THEN 3 ELSE " +
					"(CASE WHEN PDM.GRADE_PAY BETWEEN 8500 AND 11999 THEN 4 ELSE 5 END) END) END) END) END))").addScalar("name").addScalar("id",Hibernate.INTEGER).setString(0, quarterBean.getSfID()).setResultTransformer(Transformers.aliasToBean(PayBillQuartersTypeMasterDTO.class)).list();
			*/
			//payBillQuartersTypeList=session.createSQLQuery("SELECT PQTM.QUARTER_TYPE name,PQTM.ID id from PAY_QUARTERS_TYPE_MASTER PQTM").addScalar("name").addScalar("id",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayBillQuartersTypeMasterDTO.class)).list();
			EmpPaymentsDTO empPaymentsDTO = (EmpPaymentsDTO)session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq("sfid", quarterBean.getSfID())).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(empPaymentsDTO)) {
				if(Integer.parseInt(empPaymentsDTO.getGradePay()) != 0) {
					payBillQuartersTypeList = session.createSQLQuery("select qtm.id id,qtm.quarter_type name from PAY_QUARTERS_TYPE_MASTER qtm, "
							+ "(select unique quarter_id from quarter_grade_pay_mapping where grade_pay <= (select grade_pay from emp_payment_details where sfid = ?) "
							+ "and grade_pay != 0 and status = 1) tab where qtm.id = tab.quarter_id order by qtm.quarter_type").addScalar("name").addScalar("id", Hibernate.INTEGER)
							.setResultTransformer(Transformers.aliasToBean(PayBillQuartersTypeMasterDTO.class)).setString(0,quarterBean.getSfID()).list();
				}
				else {
					payBillQuartersTypeList = session.createSQLQuery("select qtm.id id,qtm.quarter_type name from PAY_QUARTERS_TYPE_MASTER qtm, "
							+ "(select unique quarter_id from quarter_grade_pay_mapping where grade_pay >= (select grade_pay from emp_payment_details where sfid = ?) "
							+ "and status = 1) tab where qtm.id = tab.quarter_id order by qtm.quarter_type").addScalar("name").addScalar("id",Hibernate.INTEGER)
							.setResultTransformer(Transformers.aliasToBean(PayBillQuartersTypeMasterDTO.class)).setString(0,quarterBean.getSfID()).list();
				}
			}
			quarterBean.setQuarterTypeList(payBillQuartersTypeList);
			//quarterBean.setQuarterTypeList(session.createCriteria(PayBillQuartersTypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.desc("id")).list());
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return quarterBean;
	}
	
	@SuppressWarnings("unchecked")
	public List<QuarterRequestBean> getQuarterEmuDetailsList(String type)throws Exception {
		List<QuarterRequestBean> list = null;
		Session session = null;
		String sql = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if(CPSUtils.compareStrings("alloted", type))
				list = session.createCriteria(QuarterRequestBean.class).add(Expression.eq(CPSConstants.STATUS, Integer.parseInt(CPSConstants.STATUSCOMPLETED))).add(Expression.isNull("allotedDate")).add(Expression.isNotNull("letterDt")).add(Expression.isNotNull("letterNo")).addOrder(Order.asc("letterNo")).addOrder(Order.asc("requestID")).list();
			else if(CPSUtils.compareStrings("occupied", type)) {
				sql = "select unique qrd.sfid sfID,qrd.request_id requestID,pqsm.quarters_sub_type quarterType,pqm.quarter_no quarterNo,qrd.alloted_date allotedDt from quarter_request_details qrd,pay_quarter_management_details pqm,pay_quarters_subtype_master pqsm where qrd.status=8 and qrd.wf_status=3 and qrd.sfid=pqm.sfid and pqm.status=50 and pqm.quarter_sub_type=pqsm.id";
				list = session.createSQLQuery(sql).addScalar("sfID").addScalar("requestID").addScalar("quarterType").addScalar("quarterNo").addScalar("allotedDt",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(QuarterRequestBean.class)).list();
			}
			else if(CPSUtils.compareStrings("vacated", type)) {
				sql = "select unique qrd.sfid sfID,qrd.request_id requestID,pqsm.quarters_sub_type quarterType,pqm.quarter_no quarterNo,qrd.alloted_date allotedDt,pqm.occupied_date occupiedDt,qrd.occupied_id occupiedId from quarter_request_details qrd,pay_quarter_management_details pqm,pay_quarters_subtype_master pqsm where qrd.status=8 and qrd.wf_status=4 and qrd.sfid=pqm.sfid and pqm.status=1 and pqm.quarter_sub_type=pqsm.id order by qrd.occupied_id,qrd.request_id asc";
				list = session.createSQLQuery(sql).addScalar("sfID").addScalar("requestID").addScalar("quarterType").addScalar("quarterNo").addScalar("allotedDt",Hibernate.STRING).addScalar("occupiedDt",Hibernate.STRING).addScalar("occupiedId", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(QuarterRequestBean.class)).list();
			}
			else if(CPSUtils.compareStrings("vacationCmpl", type)) {
				sql = "select unique qrd.sfid sfID,qrd.request_id requestID,pqsm.quarters_sub_type quarterType,pqm.quarter_no quarterNo,to_char(qrd.alloted_date,'dd-Mon-yyyy') allotedDt,to_char(pqm.occupied_date,'dd-Mon-yyyy') occupiedDt,qrd.occupied_id occupiedId,to_char(Pqm.Vacation_date,'dd-Mon-yyyy') vacatedDt from quarter_request_details qrd,pay_quarter_management_details pqm,pay_quarters_subtype_master pqsm where qrd.status=8 and qrd.wf_status=5 and qrd.sfid=pqm.sfid and pqm.status=51 and pqm.quarter_sub_type=pqsm.id order by qrd.occupied_id,qrd.request_id asc";
				list = session.createSQLQuery(sql).addScalar("sfID").addScalar("requestID").addScalar("quarterType").addScalar("quarterNo").addScalar("allotedDt",Hibernate.STRING).addScalar("occupiedDt",Hibernate.STRING).addScalar("occupiedId", Hibernate.STRING).addScalar("vacatedDt", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(QuarterRequestBean.class)).list();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public QuarterRequestBean getQuarterSubTypeDetails(QuarterRequestBean quarterBean)throws Exception{
			Session session = null;
			try {
				session = hibernateUtils.getSession();//session = sessionFactory.openSession();
				quarterBean.setQuarterSubTypeList(session.createCriteria(QuarterTypeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).list()) ;
			} catch (Exception e) {
				throw e;
			} finally {
				//session.close();
			}
			return quarterBean;
	}
	
	public String updateQuarterDetailsWithEMU(QuarterRequestBean quarterBean)throws Exception {
		Session session = null;
		String message = "";
		String sql = "";
		Query query = null;
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(CPSUtils.compareStrings("alloted", quarterBean.getType())) {
				sql = "update QuarterRequestBean set allotedDate=?,wfStatus=?,allotment=?,allotedId=? where requestID=?";
				query = session.createQuery(sql);
				query.setString(0, quarterBean.getAllotedDt());
				query.setInteger(1, Integer.parseInt(CPSConstants.QUARTER_ALLOTED));
				query.setString(2,"Y");
				query.setString(3,quarterBean.getAllotedId());
				query.setString(4, quarterBean.getRequestID());
				query.executeUpdate();
				message = CPSConstants.UPDATE;
				if(CPSUtils.compareStrings(message,CPSConstants.UPDATE)) {
					sql = "update PayQuarterManagementDTO set quartersType=?,quarterNo=?, lastModifiedTime=?,lastModifiedBy=? where sfid=? and status=?";
					query = session.createQuery(sql);
					query.setString(0, quarterBean.getQuarterType());
					query.setString(1, quarterBean.getQuarterNo());
					query.setString(2, CPSUtils.getCurrentDate());
					query.setString(3, quarterBean.getSfID1());
					query.setString(4, quarterBean.getSfID());
					query.setInteger(5, Integer.valueOf(CPSConstants.STATUSUPDATIONREQUIRED));
					query.executeUpdate();
					session.flush();//tx.commit() ;
					message = CPSConstants.SUCCESS;
				}
			}
			else if(CPSUtils.compareStrings("occupied", quarterBean.getType())) {
				sql = "update QuarterRequestBean set wfStatus=?,occupiedId=? where requestID=?";
				query = session.createQuery(sql);
				query.setInteger(0, Integer.parseInt(CPSConstants.QUARTER_OCCUPIED));
				query.setString(1,quarterBean.getAllotedId());
				query.setString(2, quarterBean.getRequestID());
				query.executeUpdate();
				message = CPSConstants.UPDATE;
				if(CPSUtils.compareStrings(message, CPSConstants.UPDATE)) {
					sql = "update PayQuarterManagementDTO set occupiedDate=?,lastModifiedTime=?,lastModifiedBy=?,status=? where sfid=? and status=? ";
					query = session.createQuery(sql);
					query.setString(0, quarterBean.getOccupiedDt());
					query.setString(1, CPSUtils.getCurrentDate());
					query.setString(2, quarterBean.getSfID1());
					query.setInteger(3, 1);
					query.setString(4, quarterBean.getSfID());
					query.setInteger(5, Integer.valueOf(CPSConstants.STATUSUPDATIONREQUIRED));
					query.executeUpdate();
					session.flush();//tx.commit() ;
					message = CPSConstants.SUCCESS;
				}
			}
			else if(CPSUtils.compareStrings("vacated", quarterBean.getType())) {
				sql = "update QuarterRequestBean set wfStatus=?,vacatedId=? where requestID=?";
				query = session.createQuery(sql);
				query.setInteger(0, Integer.parseInt(CPSConstants.QUARTER_VACATED));
				query.setString(1,quarterBean.getAllotedId());
				query.setString(2, quarterBean.getRequestID());
				query.executeUpdate();
				message = CPSConstants.UPDATE;
				if(CPSUtils.compareStrings(message, CPSConstants.UPDATE)) {
					sql = "update PayQuarterManagementDTO set vacationDate=?,lastModifiedTime=?,lastModifiedBy=?,status=? where sfid=? and status=? ";
					query = session.createQuery(sql);
					query.setString(0, quarterBean.getVacatedDt());
					query.setString(1, CPSUtils.getCurrentDate());
					query.setString(2, quarterBean.getSfID1());
					query.setInteger(3, Integer.parseInt(CPSConstants.PAY_QUARTER_VACATED));
					query.setString(4, quarterBean.getSfID());
					query.setInteger(5, 1);
					query.executeUpdate();
					session.flush();//tx.commit() ;
					message = CPSConstants.SUCCESS;
				}
		   }
		} catch (Exception e) { //tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<QuarterRequestBean> getEMUSendingSFID()throws Exception {
		Session session = null;
		String sql = "";
		List<QuarterRequestBean> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			sql = "select unique qrd.sfid sfID,emp.name_in_service_book name,dm.name designationID,qrd.request_id requestID,qrd.request_type_id requestTypeID " +
				"from request_workflow_history rwh,quarter_request_details qrd,pay_quarter_management_details pqm," +
				"emp_master emp,designation_master dm where rwh.request_id=qrd.request_id and rwh.status=8 and qrd.status=8 " +
				"and pqm.sfid=qrd.sfid and pqm.status=50 and emp.sfid=qrd.sfid and emp.designation_id=dm.id and qrd.wf_status=1"; 
			list = session.createSQLQuery(sql).addScalar("sfID").addScalar("name").addScalar("designationID").addScalar("requestID").addScalar("requestTypeID",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(QuarterRequestBean.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}

	public String saveEmuRequestDetails(QuarterRequestBean quarterBean)throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("update QUARTER_REQUEST_DETAILS qrd set qrd.letter_no=?,qrd.letter_date=?,qrd.wf_status=? where qrd.request_id=? and qrd.sfid=? and qrd.status=?")
			.setString(0, quarterBean.getLetterNo()).setString(1, quarterBean.getLetterDt()).setInteger(2, Integer.parseInt(CPSConstants.QUARTER_EMU_PENDING)).setString(3, quarterBean.getRequestID()).setString(4, quarterBean.getSfID()).setString(5, CPSConstants.STATUSCOMPLETED).executeUpdate();
			hibernateUtils.closeSession();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List<QuarterRequestBean> getUniqueGradePays()throws Exception {
		Session session = null;
		List<QuarterRequestBean> list = null;
		try {
			session = hibernateUtils.getSession();
		/*	return list=session.createSQLQuery("select unique pdm.grade_pay as gradePay from payband_designation_mapping pdm where pdm.status=1 order by pdm.grade_pay")
			     .setResultTransformer(Transformers.aliasToBean(QuarterRequestBean.class)).list();*/
		//	return list=session.createSQLQuery("select unique epd.grade_pay as gradePayID from emp_payment_details epd order by epd.grade_pay")
			list = session.createSQLQuery("select unique pdm.grade_pay gradePayID from payband_designation_mapping pdm where pdm.status=1 order by pdm.grade_pay")
		     .addScalar("gradePayID",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(QuarterRequestBean.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeList()throws Exception { 
		Session session = null;
		List<PayBillQuartersTypeMasterDTO> list = null;
		try {
			session = hibernateUtils.getSession();
			list = session.createCriteria(PayBillQuartersTypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS,1)).addOrder(Order.asc("name")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
		
	public String saveQuarterGradePays(QuarterRequestBean bean)throws Exception {
		Session session = null;
		String message = null;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "delete QUARTER_GRADE_PAY_MAPPING where QUARTER_ID=? and id!=0";
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getQuarterType());
			ps.executeUpdate();

			//String[] gradePays = (bean.getGradePayValueId().substring(0, bean.getGradePayValueId().length() - 1)).split(",");
			String[] gradePays = bean.getSelectedGradePays().split(",");
			/*sql = "insert into QUARTER_GRADE_PAY_MAPPING(ID,QUARTER_ID,GRADE_PAY,STATUS,CREATION_DATE,LAST_MODIFIED_DATE,LAST_MODIFIED_BY) " +
					"values((select case when (select max(id)+1 from QUARTER_GRADE_PAY_MAPPING) is null then 0 else (select max(id)+1 from QUARTER_GRADE_PAY_MAPPING) end from dual),?,?,?," +
					"(select case when (select creation_date from QUARTER_GRADE_PAY_MAPPING) is null then sysdate else (select creation_date from QUARTER_GRADE_PAY_MAPPING) end from dual),?,?)";*/
			
			sql = "insert into QUARTER_GRADE_PAY_MAPPING(ID,QUARTER_ID,GRADE_PAY,STATUS,CREATION_DATE,LAST_MODIFIED_DATE,LAST_MODIFIED_BY) " +
					"values((select case when (select max(id)+1 from QUARTER_GRADE_PAY_MAPPING) is null then 0 else (select max(id)+1 from QUARTER_GRADE_PAY_MAPPING) end from dual),?,?,?," +
					"sysdate,?,?)";
			for (int i = 0; i < gradePays.length; i++) {
				ps1 = con.prepareStatement(sql);
				ps1.setString(1, bean.getQuarterType());
				ps1.setString(2, gradePays[i]);
				ps1.setInt(3, 1);
				ps1.setString(4, CPSUtils.getCurrentDate());
				ps1.setString(5, bean.getSfID());
				ps1.executeUpdate();
			}
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, null);
			ConnectionUtil.closeConnection(null, ps1, null);
		}
		return message;
	}
	
	public List getMappedQuarterGrades(String quarterID) throws Exception {
		List list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String hql = "from QuarterGradePayMappingDTO q where q.status=1 and q.quarterID=?";
			Query qry = session.createQuery(hql);
			qry.setInteger(0, Integer.valueOf(quarterID));
			list = qry.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	
	public QuarterRequestBean getOfflineEntry(QuarterRequestBean quarterRequestBean) throws Exception {
		return quarterRequestBean;
	}
	
	public List<QuarterRequestBean> getQuarterDetailsList(QuarterRequestBean quarterRequestBean) throws Exception{
		List<QuarterRequestBean> list=null;
		try {
			
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeDetails(QuarterRequestBean quarterBean)throws Exception{
		Session session = null;
		List<PayBillQuartersTypeMasterDTO> list = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			list = session.createCriteria(PayBillQuartersTypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	
	public String saveQuarterOfflineDetails(PayQuarterManagementDTO bean)throws Exception{
		Session session = null;
		String message = null;
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(bean);
			session.flush();//tx.commit() ;
			session.clear();
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	public String deleteQuarterOfflineDetails(int id)throws Exception{
		Session session = null;
		String message = null;
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			PayQuarterManagementDTO payQuarterManagementDTO=(PayQuarterManagementDTO)session.get(PayQuarterManagementDTO.class, id);
			payQuarterManagementDTO.setStatus(0);
			session.saveOrUpdate(payQuarterManagementDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<PayQuarterManagementDTO> getQuarterOfflineDetailsList(String sfid) throws Exception {
		List<PayQuarterManagementDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			list = session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq("sfid",sfid.toUpperCase())).add(Expression.not(Expression.eq("status", 0))).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return list;
	}
	
	public EmployeeBean getEmployeeDetails(String sfid)throws Exception{
		EmployeeBean employeeBean=null;
		Session session = null;
		try{
			session=hibernateUtils.getSession();
			employeeBean=(EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", sfid)).uniqueResult();
		}catch (Exception e) {
			throw e;
		}
		return employeeBean;
	}
	
	public QuarterRequestBean getAppliedQuarterType(QuarterRequestBean qrb)throws Exception{
		Session session=null;
		PayQuarterManagementDTO payQuarterManagementDTO=null;
		QuarterRequestBean qrb1=new QuarterRequestBean();
		try {
			session=hibernateUtils.getSession();
			payQuarterManagementDTO = (PayQuarterManagementDTO)session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq("sfid", qrb.getSfID())).add(Expression.isNull("vacationDate")).add(Expression.eq("status", 1)).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(payQuarterManagementDTO)){
				BeanUtils.copyProperties(qrb1, qrb);
				qrb = (QuarterRequestBean)session.createCriteria(QuarterRequestBean.class).add(Expression.eq("sfID", qrb.getSfID())).add(Expression.ne("wfStatus", 5)).setFetchSize(1).setMaxResults(1).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(qrb))
					qrb.setEligibility(CPSUtils.convertFirstLetterToUpperCase(qrb.getEligibility()));
				else{
					qrb=new QuarterRequestBean();
					BeanUtils.copyProperties(qrb, qrb1);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return qrb;
	}
	
	@SuppressWarnings("unchecked")
	public List<IONDTO> getEMULetterNumbersList(QuarterRequestBean qrb)throws Exception {
		Session session = null;
		List<IONDTO> ionList = null;
		String DeptId = null;
		LetterNumberFormatDTO letterNoFormatDTO = null;
		try {
			session = hibernateUtils.getSession();
			DeptId = (String)session.createSQLQuery("Select Unique to_char(DM.DEPARTMENT_ID) As Deptname From Emp_master Em,Departments_master Dm,Org_role_instance Ori "
					+ "Where Dm.Department_id=(Select Ori.Department_id From Org_role_instance Ori Where ori.org_role_id=(select em.office_id "
					+ "from emp_master em where em.sfid=?))").setString(0, qrb.getSfID()).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(DeptId)) {
				letterNoFormatDTO = (LetterNumberFormatDTO)session.createCriteria(LetterNumberFormatDTO.class).add(Expression.eq("deptNum", Integer.parseInt(DeptId))).setFetchSize(1).setMaxResults(1).uniqueResult();
			}
			if(!CPSUtils.isNullOrEmpty(letterNoFormatDTO)) {
				ionList = session.createCriteria(IONDTO.class).add(Expression.eq("letterFormatId", letterNoFormatDTO.getId())).list();
			}
		} catch (Exception e) {
			throw e;
		}
		return ionList;
	}
}
