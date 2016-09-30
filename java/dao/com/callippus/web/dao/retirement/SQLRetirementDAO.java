package com.callippus.web.dao.retirement;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CghsAdvanceRequestDTO;
import com.callippus.web.beans.dto.RetirementTypeDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.retirement.RetirementBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class SQLRetirementDAO implements IRetirementDAO, Serializable {
	private static Log log = LogFactory.getLog(SQLRetirementDAO.class);
	private static final long serialVersionUID = 7048899710717627065L;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;


	public List<RetirementBean> getRetirementHomeDetails(RetirementBean retirementBean) throws Exception {
		log.debug("getRetirementHomeDetails ---> method start");
		List retirementList = null;
		Session session = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsq = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			con = session.connection();
			String sql = "select rd.id id,rd.sfid sfid1,rd.idno idno,rd.retirement_type_id retirement_type,to_char(rd.retirement_date,'DD-Mon-YYYY') retirement_date,rd.reference_number reference_number,"
					+ "rtm.name rtypename from retirement_details rd,retirement_type_master rtm where rd.sfid=? and rd.status=1 " + "and rtm.id=rd.retirement_type_id";
			log.debug("sql query --->" + sql);
			ps = con.prepareStatement(sql);
			ps.setString(1, retirementBean.getSfid());
			rsq = ps.executeQuery();
			retirementList = new ArrayList<RetirementBean>();
			JSONObject outerjson = new JSONObject();
			int i = 0;
			while (rsq.next()) {

				LinkedHashMap<String, String> innerjson = new LinkedHashMap();

				innerjson.put("id", rsq.getString("id"));
				innerjson.put("SFID", rsq.getString("sfid1"));
				innerjson.put("ID No", rsq.getString("idno"));
				innerjson.put("Retirement Type", rsq.getString("rtypename"));
				innerjson.put("Retirement Date", rsq.getString("retirement_date"));
				innerjson.put("Reference Number", rsq.getString("reference_number"));

				RetirementBean rBean = new RetirementBean();
				rBean.setId(rsq.getString("id"));
				rBean.setIdNo(rsq.getString("idno"));
				rBean.setRetirementType(rsq.getString("retirement_type"));
				if (!CPSUtils.isNull(rsq.getString("retirement_date"))) {
					rBean.setRetirementDate(rsq.getString("retirement_date"));
				}
				rBean.setReferenceNumber(rsq.getString("reference_number"));
				rBean.setRetirementTypeName(rsq.getString("rtypename"));
				retirementList.add(rBean);
				outerjson.put(String.valueOf(i), innerjson);
				i++;
			}

			outerjson.put(String.valueOf(i), CPSConstants.RETIREMENT);
			retirementList.add(outerjson);
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, ps, rsq);
		}
		log.debug("getRetirementHomeDetails ---> method end");
		return retirementList;
	}

	public String manageRetirementDetails(RetirementBean retirementBean) throws Exception {
		log.debug("manageRetirementDetails ---> method start");
		Session session = null;
		Transaction tx = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			retirementBean.setStatus(1);
			session.saveOrUpdate(retirementBean);
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		log.debug("manageRetirementDetails ---> method end");
		return message;
	}

	/**
	 * CHECKING VALIDATION FOR ONE PERSON CAN HAVE ONLY ONE TYPE OF RETIREMENT
	 */
	public boolean checkRetirementDetails(RetirementBean retirementBean) throws Exception {

		log.debug("checkRetirementDetails ---> method start");
		boolean status = true;
		Session session = null;
		ResultSet rsq = null;
		PreparedStatement pstmt = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Connection con = session.connection();
			String sql = "select count(*) from retirement_details where sfid=? and status =1";
			log.debug("sql query --->" + sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, retirementBean.getSfid());
			rsq = pstmt.executeQuery();
			if (rsq.next()) {
				if (rsq.getInt(1) > 0) {
					status = false;
					retirementBean.setMessage("duplicate");
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, rsq);
		}
		log.debug("checkRetirementDetails ---> method end");
		return status;
	}

	public String deleteRetirementDetails(RetirementBean retirementBean) throws Exception {
		log.debug("deleteRetirementDetails ---> method start");
		Session session = null;
		Transaction tx = null;
		String message = null;
		PreparedStatement pstmt = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			Connection con = session.connection();
			String sql = "update retirement_details set status=0,last_modified_date=sysdate where id=?";
			log.debug("sql query --->" + sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, retirementBean.getId());
			pstmt.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		log.debug("deleteRetirementDetails ---> method end");
		return message;
	}

	public List<RetirementTypeDTO> getRetirementTypeList() throws Exception {
		log.debug("getRetirementTypeList ---> method start");
		List<RetirementTypeDTO> list = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Criteria crt = session.createCriteria(RetirementTypeDTO.class);
			crt.add(Expression.eq("status", 1));
			crt.addOrder(Order.asc("name"));
			list = crt.list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		log.debug("getRetirementTypeList ---> method end");
		return list;
	}

	public String updateRetirementDetails(RetirementBean retirementBean) throws Exception {
		log.debug("updateRetirementDetails ---> method start");
		Session session = null;
		Transaction tx = null;
		PreparedStatement pst = null;
		Connection con = null;
		String message = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			con = session.connection();
			String sql = "UPDATE RETIREMENT_DETAILS SET IDNO=?,RETIREMENT_TYPE_ID=?,RETIREMENT_DATE=?,REFERENCE_NUMBER=?,LAST_MODIFIED_DATE=SYSDATE WHERE ID=? AND STATUS=1";
			log.debug("sql query --->" + sql);
			pst = con.prepareStatement(sql);
			pst.setString(1, retirementBean.getIdNo());
			pst.setString(2, retirementBean.getRetirementType());
			pst.setString(3, retirementBean.getRetirementDate());
			pst.setString(4, retirementBean.getReferenceNumber());
			pst.setString(5, retirementBean.getId());
			pst.executeUpdate();
			session.flush();//tx.commit() ;
			message = CPSConstants.SUCCESS;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pst, null);
		}
		log.debug("updateRetirementDetails ---> method end");
		return message;
	}
	
	public List getRetirementReportDetails(RetirementBean retirementBean) throws Exception
	{
		log.debug("getRetirementReportDetails ---> method start");
		Session session = null;
		List list = null;
		String str = "";
		try {
			session = hibernateUtils.getSession();
			str = "select emp.userSfid as userSfid,emp.nameInServiceBook as nameInServiceBook from EmployeeBean emp,YearTypeDTO year where  To_char(Add_months(emp.dob,12*60),'YYYY')=year.name and emp.status=1 and year.id="+retirementBean.getYearId();
			if(!CPSUtils.isNullOrEmpty(retirementBean.getYearId()))
			list = session.createQuery(str).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
			
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getRetirementReportDetails ---> method end");
		return list;
	}
	public List getYearList(RetirementBean retirementBean) throws Exception
	{
		log.debug("getRetirementReportDetails ---> method start");
		Session session = null;
		List list = null;
		String str = "";
		Object o = null;
		YearTypeDTO d = null;
		try { 
			session = hibernateUtils.getSession();
		list = session.createCriteria(YearTypeDTO.class).add(Expression.eq("status", 1)).addOrder(Order.desc("id")).list();
			
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getRetirementReportDetails ---> method end");
		return list;
	}
	public List getRetirementFianlDetails(RetirementBean retirementBean) throws Exception
	{
		log.debug("getRetirementReportDetails ---> method start");
		Session session = null;
		List list = null;
		String str = "";
		
		try { 
			session = hibernateUtils.getSession();
			str = "select emp.userSfid as userSfid,emp.nameInServiceBook as nameInServiceBook,emp.retiredDate as retiredDate from EmployeeBean emp where emp.retiredDate<to_date('"+retirementBean.getEndDate()+"','dd-Mon-yyyy') and emp.retiredDate>to_date('"+retirementBean.getStartDate()+"','dd-Mon-yyyy')";//between (to_date("+retirementBean.getEndDate()+",'dd-Mon-yyyy'),to_date("+retirementBean.getStartDate()+",'dd-Mon-yyyy'))";
			list = session.createQuery(str).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getRetirementReportDetails ---> method end");
		return list;
	}
	public List getRetirementFinalDetail(RetirementBean retirementBean)throws Exception
	{

		log.debug("getRetirementReportDetails ---> method start");
		Session session = null;
		List list = null;
		String str = "";
		
		try { 
			session = hibernateUtils.getSession();
			str = "Select Lmst.id as leaveTypeId,Lmst.Leave_type as leaveType,Sum(LRD.NO_OF_LEAVES) as totalLeaves FROM leave_txn_details lrd,Leave_type_master Lmst Where Lmst.Id=Lrd.Leave_type_id And Lrd.Sfid='"+retirementBean.getUserSfid()+"' GROUP BY Lmst.Leave_type,Lmst.id";

			list = session.createSQLQuery(str).addScalar("leaveTypeId",Hibernate.INTEGER).addScalar("leaveType",Hibernate.STRING).addScalar("totalLeaves", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RetirementBean.class)).list();
		} catch (Exception e) {
			
			throw e;
		} finally {
			
		}
		log.debug("getRetirementReportDetails ---> method end");
		return list;
	}
	
	
}
