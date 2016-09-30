package com.callippus.web.dao.paybill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.EmpBasicPayHistoryDTO;
import com.callippus.web.beans.dto.EmpTotalPayDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.paybill.PayBillMasterBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.paybill.dto.EmpPaymentDetailsLogDTO;
import com.callippus.web.paybill.dto.PayBillCGEISMasterDTO;
import com.callippus.web.paybill.dto.PayBillCGHSMasterDTO;
import com.callippus.web.paybill.dto.PayBillCanfinDTO;
import com.callippus.web.paybill.dto.PayBillCcsDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillDearnessAllowanceMasterDTO;
import com.callippus.web.paybill.dto.PayBillDuesDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
import com.callippus.web.paybill.dto.PayBillEmpPaymentDeatilsDTO;
import com.callippus.web.paybill.dto.PayBillGroupMasterDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;
import com.callippus.web.paybill.dto.PayBillResidentialSecurityMasterDTO;
import com.callippus.web.paybill.dto.PayDeductionDTO;
import com.callippus.web.paybill.dto.PayEolDTO;
import com.callippus.web.paybill.dto.PayGPFDetailsDTO;
import com.callippus.web.paybill.dto.PayHindiInceDTO;
import com.callippus.web.paybill.dto.PayMastersPortingDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.paybill.dto.PayTxnMastersDetailsDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;

@SuppressWarnings("serial")
@Service
public class SQLPayBillMasterDAO implements IPayBillMasterDAO, Serializable {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
    
	public String getCurrentRunMonth() throws Exception
	{
		Session session = null;
		String result = null;
			try
			{
				session = hibernateUtils.getSession();
				result = session.createSQLQuery("select to_char(Add_Months(run_month, 1), 'Mon-YYYY') from paybill_status_details where status not in(0,2) and run_month = (select max(run_month) from paybill_status_details  where status not in(0,2))").uniqueResult().toString();
			}catch(Exception e)
			{
				throw e;
			}
		return result;
	}
	
	public String submitPayBillCGEISDetails(PayBillCGEISMasterDTO payBillCGEISDTO) throws Exception 
	{
		Session session = null;
		String result = null;
		//Transaction tx = null;
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(payBillCGEISDTO.getPk() == 0)
				result = CPSConstants.SUCCESS;
			else
				result = CPSConstants.UPDATE;
			session.saveOrUpdate(payBillCGEISDTO);
			session.flush();//tx.commit() ;
			//satya
			payBillCGEISDTO.setGroupMasterDTO((PayBillGroupMasterDTO) session.createCriteria(PayBillGroupMasterDTO.class).add(Expression.eq("id",payBillCGEISDTO.getGroupId())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
		} catch (Exception e) {
			//tx.rollback();
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public List<PayBillMasterBean> getGradePayList(PayBillMasterBean payBillMasterBean) throws Exception
	{
		Session session = null;
		List<PayBillMasterBean> gradePayList = new ArrayList<PayBillMasterBean>();
		List temp=null; 
		try
		{
			session = hibernateUtils.getSession();
			temp = session.createSQLQuery("select distinct grade_pay as gradePay from payband_designation_mapping order by grade_pay asc").addScalar("gradePay", Hibernate.STRING).list();
			for(int i=0;i<temp.size();i++)
			{
				PayBillMasterBean pbmBean=new PayBillMasterBean();
				pbmBean.setGradePay(temp.get(i).toString());
				gradePayList.add(pbmBean);
			}
		}catch (Exception e) {
			throw e;
		}
		return gradePayList;
	}
	
	
	public PayBillMasterBean submitPayBillCGHSDetails(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		Session session = null;
		//Transaction tx = null;
		try 
		{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			//tx=session.beginTransaction();
			if(CPSUtils.isNullOrEmpty(payBillMasterBean.getNodeID()))	//New Record
			{
				String check = checkCGHSDTODetails(payBillMasterBean);
				if(CPSUtils.compareStrings(check, CPSConstants.YES)) //there is no duplicate record in the database
				{
					PayBillCGHSMasterDTO  oldMasterDTO = (PayBillCGHSMasterDTO) session.createCriteria(PayBillCGHSMasterDTO.class).add(Expression.eq("gradePay", Float.parseFloat(payBillMasterBean.getGradePay()))).add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(oldMasterDTO))
					{
						oldMasterDTO.setStatus(2);
						oldMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
						oldMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
						session.saveOrUpdate(oldMasterDTO);
					}
					PayBillCGHSMasterDTO payBillCGHSMasterDTO = new PayBillCGHSMasterDTO();
					payBillCGHSMasterDTO.setGradePay(Float.parseFloat(payBillMasterBean.getGradePay()));
					payBillCGHSMasterDTO.setAllowanceAmount(Float.parseFloat(payBillMasterBean.getAllowanceAmount()));
					payBillCGHSMasterDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()));
					payBillCGHSMasterDTO.setCreatedBy(payBillMasterBean.getSfID());
					payBillCGHSMasterDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
					payBillCGHSMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
					payBillCGHSMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
					payBillCGHSMasterDTO.setStatus(1);
					session.saveOrUpdate(payBillCGHSMasterDTO);
					payBillMasterBean.setMessage(CPSConstants.SUCCESS);
				}
				else //duplicate exists
					payBillMasterBean.setMessage(CPSConstants.DUPLICATE);
			}
			else //update
			{
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("cghsMastersId", Integer.parseInt(payBillMasterBean.getNodeID()))).list().size();
				if(size == 0) //No Dependencies
				{
					PayBillCGHSMasterDTO payBillCGHSMasterDTO = new PayBillCGHSMasterDTO();
					payBillCGHSMasterDTO.setPk(Integer.parseInt(payBillMasterBean.getNodeID()));
					payBillCGHSMasterDTO.setGradePay(Float.parseFloat(payBillMasterBean.getGradePay()));
					payBillCGHSMasterDTO.setAllowanceAmount(Float.parseFloat(payBillMasterBean.getAllowanceAmount()));
					payBillCGHSMasterDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()));
					payBillCGHSMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
					payBillCGHSMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
					payBillCGHSMasterDTO.setStatus(1);
					session.saveOrUpdate(payBillCGHSMasterDTO);
					payBillMasterBean.setMessage(CPSConstants.UPDATE);
				}
				else //Dependencies exists
					payBillMasterBean.setMessage(CPSConstants.NOTUPDATE);
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			payBillMasterBean.setMessage(CPSConstants.FAILED);
			throw e;
		} finally {
			//session.close();
		}
		return payBillMasterBean;
	}
	@SuppressWarnings("unchecked")
	public String checkCGHSDTODetails(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		String message = "";
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			List<PayBillCGHSMasterDTO> payBillCGHSMasterList = session.createCriteria(PayBillCGHSMasterDTO.class).add(Expression.eq("gradePay", Float.parseFloat(payBillMasterBean.getGradePay())))
						.add(Expression.eq("effDate", new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()))).add(Expression.eq("status", 1)).list();
			if(payBillCGHSMasterList.size() == 0)
			    message = CPSConstants.YES;
			else
				 message = CPSConstants.NO;
		}catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	public Object getOldMasterDTODetails(int id,String type) throws Exception{
		Object obj=null;
		Session session = null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			if (CPSUtils.compareStrings(type, CPSConstants.CGHS)) {
				obj = session.get(PayBillCGHSMasterDTO.class, id);
			}
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return obj;
	}
	@SuppressWarnings("unchecked")
	public String deletePayBillMasterRecord(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		Session session = null;
		String result = null;
		//Transaction tx = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.CGHS)) // CGHS Details deleting 
			{
				PayBillCGHSMasterDTO payBillCGHSDTO = (PayBillCGHSMasterDTO) session.get(PayBillCGHSMasterDTO.class, Integer.valueOf(payBillMasterBean.getNodeID()));
				payBillCGHSDTO.setStatus(0);
				payBillCGHSDTO.setModifiedBy(payBillMasterBean.getSfID());
				payBillCGHSDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
				List<PayTxnMastersDetailsDTO> payTxnMastersDetailsList =  session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("cghsMastersId", Integer.parseInt(payBillMasterBean.getNodeID()))).list();
				if(payTxnMastersDetailsList.size() == 0)
				{
					payBillCGHSDTO.setStatus(0);
					result = CPSConstants.DELETE;
				}
				else
				{
					payBillCGHSDTO.setStatus(2);
					result = CPSConstants.INACTIVE;
				}
				session.saveOrUpdate(payBillCGHSDTO);
			} 
			else if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.CGEIS)) // CGEIS Details deleting 
			{ 
				PayBillCGEISMasterDTO payBillCGEISDTO = (PayBillCGEISMasterDTO) session.get(PayBillCGEISMasterDTO.class, Integer.valueOf(payBillMasterBean.getNodeID()));
				payBillCGEISDTO.setStatus(0);
				payBillCGEISDTO.setModifiedBy(payBillMasterBean.getSfID());
				payBillCGEISDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
				session.saveOrUpdate(payBillCGEISDTO);
				result = CPSConstants.DELETE;
			} 
			else if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.DEARNESSALLOWANCE)) // DAMASTER Details deleting 
			{
				PayBillDearnessAllowanceMasterDTO payBillDearnessAllowanceMasterDTO = (PayBillDearnessAllowanceMasterDTO) session.get(PayBillDearnessAllowanceMasterDTO.class, Integer.valueOf(payBillMasterBean.getNodeID()));
				List<PayTxnMastersDetailsDTO> payTxnMastersDetailsList =  session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("daMasterId", Integer.parseInt(payBillMasterBean.getNodeID()))).list();
				if(payTxnMastersDetailsList.size() == 0)
				{
					payBillDearnessAllowanceMasterDTO.setStatus(0);
					payBillDearnessAllowanceMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
					payBillDearnessAllowanceMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
					session.saveOrUpdate(payBillDearnessAllowanceMasterDTO);
					result = CPSConstants.DELETE;
				}
				else
				{
					payBillDearnessAllowanceMasterDTO.setStatus(2);
					payBillDearnessAllowanceMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
					payBillDearnessAllowanceMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
					session.saveOrUpdate(payBillDearnessAllowanceMasterDTO);
					result = CPSConstants.INACTIVE;
				}
			}
			else if (CPSUtils.compareStrings(payBillMasterBean.getType(), CPSConstants.RESIDENTIALSECURITY)) // RSMASTER Details deleting
			{
				PayBillResidentialSecurityMasterDTO payBillResidentialSecurityMasterDTO = (PayBillResidentialSecurityMasterDTO) session.get(PayBillResidentialSecurityMasterDTO.class, Integer.valueOf(payBillMasterBean.getNodeID()));
				payBillResidentialSecurityMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
				payBillResidentialSecurityMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("resSecuMastersId", payBillResidentialSecurityMasterDTO.getPk())).add(Expression.eq("status", 1)).list().size();
				if(size == 0)
				{
					payBillResidentialSecurityMasterDTO.setStatus(0);
					result = CPSConstants.DELETE;
				}
				else
				{
					payBillResidentialSecurityMasterDTO.setStatus(2);
					result = CPSConstants.INACTIVE;
				}
				session.saveOrUpdate(payBillResidentialSecurityMasterDTO);
			}
			session.flush();//tx.commit() ;
			
		} catch (Exception e) {
			//tx.rollback();
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String submitPayBillDearNessAllowanceDetails(PayBillDearnessAllowanceMasterDTO payBillDearnessAllowanceMasterDTO) throws Exception 
	{
		Session session = null;
		String result = null;
		//Transaction tx = null;
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(payBillDearnessAllowanceMasterDTO.getPk() == 0)
			{
				session.saveOrUpdate(payBillDearnessAllowanceMasterDTO);
				result = CPSConstants.SUCCESS;
			}
			else
			{
				List<PayTxnMastersDetailsDTO> payTxnMastersDetailsList =  session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("daMasterId", payBillDearnessAllowanceMasterDTO.getPk())).list();
				if(payTxnMastersDetailsList.size() == 0)
				{
					session.saveOrUpdate(payBillDearnessAllowanceMasterDTO);
					result = CPSConstants.UPDATE;
				}
				else
					result = CPSConstants.NOTUPDATE;
			}
			session.flush();//tx.commit() ;
			session.clear();
		} catch (Exception e) {
			//tx.rollback();
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean submitPayBillResidentialSecurityDetails(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		Session session = null;
		//Transaction tx = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(CPSUtils.compareStrings("edit", payBillMasterBean.getType()))
			{
				PayBillResidentialSecurityMasterDTO editRecord = (PayBillResidentialSecurityMasterDTO) session.get(PayBillResidentialSecurityMasterDTO.class, Integer.parseInt(payBillMasterBean.getNodeID()));
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("resSecuMastersId", Integer.parseInt(payBillMasterBean.getNodeID()))).add(Expression.eq("status", 1)).list().size();
				if(size == 0) //No dependency
				{
					editRecord.setQuarterTypeId(Integer.parseInt(payBillMasterBean.getQuarterTypeId()));
					editRecord.setAmount(Float.parseFloat(payBillMasterBean.getAmount()));
					editRecord.setModifiedBy(payBillMasterBean.getSfID());
					editRecord.setModifiedDate(CPSUtils.getCurrentDateWithTime());
					editRecord.setStatus(1);
					editRecord.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()));
			    	session.saveOrUpdate(editRecord);
			    	payBillMasterBean.setMessage(CPSConstants.UPDATE);
				}
				else
					payBillMasterBean.setMessage(CPSConstants.NOTUPDATE);
			}
			else //New Record
			{
				List<PayBillResidentialSecurityMasterDTO> resSecuList = session.createCriteria(PayBillResidentialSecurityMasterDTO.class).add(Expression.eq("quarterTypeId", Integer.parseInt(payBillMasterBean.getQuarterTypeId()))).add(Expression.eq("effDate", new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()))).add(Expression.eq("status", 1)).list();
			    if(resSecuList.size() == 0) //No duplicate
			    {
			    	PayBillResidentialSecurityMasterDTO dupDTO = (PayBillResidentialSecurityMasterDTO) session.createCriteria(PayBillResidentialSecurityMasterDTO.class).add(Expression.eq("quarterTypeId", Integer.parseInt(payBillMasterBean.getQuarterTypeId()))).add(Expression.eq("status", 1)).uniqueResult();
			    	if(!CPSUtils.isNullOrEmpty(dupDTO))
			    	{
			    		dupDTO.setStatus(2);
			    		dupDTO.setModifiedBy(payBillMasterBean.getSfID());
			    		dupDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
			    		session.saveOrUpdate(dupDTO);
			    	}
			    	PayBillResidentialSecurityMasterDTO payBillResidentialSecurityMasterDTO = new PayBillResidentialSecurityMasterDTO();
			    	payBillResidentialSecurityMasterDTO.setQuarterTypeId(Integer.parseInt(payBillMasterBean.getQuarterTypeId()));
			    	payBillResidentialSecurityMasterDTO.setAmount(Float.parseFloat(payBillMasterBean.getAmount()));
			    	payBillResidentialSecurityMasterDTO.setCreatedBy(payBillMasterBean.getSfID());
			    	payBillResidentialSecurityMasterDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			    	payBillResidentialSecurityMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
			    	payBillResidentialSecurityMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
			    	payBillResidentialSecurityMasterDTO.setStatus(1);
			    	payBillResidentialSecurityMasterDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()));
			    	session.saveOrUpdate(payBillResidentialSecurityMasterDTO);
			    	payBillResidentialSecurityMasterDTO.setQuartersTypeMasterDTO((PayBillQuartersTypeMasterDTO) session.createCriteria(PayBillQuartersTypeMasterDTO.class).add(Expression.eq("id",payBillResidentialSecurityMasterDTO.getQuarterTypeId())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
			    	payBillMasterBean.setMessage(CPSConstants.SUCCESS);
			    }
			    else //Duplicate
			    	payBillMasterBean.setMessage(CPSConstants.DUPLICATE);
			}
			session.flush();//tx.commit() ;

		} catch (Exception e) {
			//tx.rollback();
			payBillMasterBean.setMessage(CPSConstants.FAILED);
			throw e;
		} finally {
			//session.close();
		}
		return payBillMasterBean;
	}

	public String submitPayBillCcsDetails(ArrayList<PayBillCcsDTO> arrList) throws Exception {

		Session session = null;
		String message = "";
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			for (int i = 0; i < arrList.size(); i++) {

				session.saveOrUpdate(arrList.get(i));
				String sfid = arrList.get(i).getSfid();
				float total = arrList.get(i).getTotal();
				float ccsSub = arrList.get(i).getThrift();

				float recovery = total - ccsSub;
				int id = getPresentPayTxnID(sfid.toUpperCase());
				if (id != 0) {
					String updateQuery = "update pay_txn_details set r_ccs_refund=?,TOT_RECS=TOT_RECS+?,TAKE_HOME=TAKE_HOME-? where id=?";
					session.createSQLQuery(updateQuery).setFloat(0, recovery).setFloat(1, recovery).setFloat(2, recovery).setInteger(3, id).executeUpdate();
				}
			}
			session.flush();//tx.commit() ;
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

	public int getPresentPayTxnID(String sfID) throws Exception {
		int Id = 0;
		Session session = null;

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			PayBillDTO payBillDTO = (PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("sfid", sfID)).add(Expression.in("status", new Integer[]{0,60})).uniqueResult();
			if (!CPSUtils.isNull(payBillDTO)) {
				Id = payBillDTO.getId();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();

		}
		return Id;
	}

	public int getPresentIDDetails(String sfID) throws Exception {
		int Id = 0;
		Session session = null;

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			PayBillCcsDTO payBillDTO = (PayBillCcsDTO) session.createCriteria(PayBillCcsDTO.class).add(Expression.eq("sfid", sfID)).add(Expression.eq("status", 0)).uniqueResult();
			if (!CPSUtils.isNull(payBillDTO)) {
				Id = payBillDTO.getId();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();

		}
		return Id;
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getDeductionMasterDeatails() throws Exception {

		List<KeyValueDTO> keyValueDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select id as id,deduction_type as name from PAY_DEDUCTION_TYPE_MASTER where STATUS=1  ";
			keyValueDTO = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueDTO;
	}

	public String updateCanfinDetails(PayBillCanfinDTO canfinDTO) throws Exception {
		Session session = null;
		String message = "";
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(canfinDTO);

			session.flush();//tx.commit() ;

			canfinDTO.setDeductionDetails((PayDeductionDTO) session.createCriteria(PayDeductionDTO.class).add(Expression.eq("id",canfinDTO.getDeductionID())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());

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

	@SuppressWarnings("unchecked")
	public String insertEmpCategoryMasterDetails(PayBillEmpCategoryDTO cDTO) throws Exception 
	{
		Session session = null;
		String message = "";
		//Transaction tx = null;
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
	    	/*List<PayBillEmpCategoryDTO> keyList = session.createCriteria(PayBillEmpCategoryDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("name", cDTO.getName())).add(Expression.eq("payBillType", cDTO.getPayBillType())).list();
			if(keyList.isEmpty())
			{*/
				session.saveOrUpdate(cDTO);
				session.flush();//tx.commit() ;
				session.clear();
				message = CPSConstants.SUCCESS;
			/*}
			else
				message=CPSConstants.DUPLICATE;*/
		} catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<PayBillEmpCategoryDTO> getEmpCategoryDetailsList() throws Exception {

		List<PayBillEmpCategoryDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = session.createCriteria(PayBillEmpCategoryDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).addOrder(Order.asc("payOrderNo")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}

	public String deleteEmpCategoryMasterDetails(PayBillEmpCategoryDTO cDTO) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			int size = session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("categoryID", cDTO.getId())).list().size();
			if (size == 0) {
				session.createSQLQuery("update  PAY_EMP_CATEGORY_MASTER set status=0,last_modified_date=sysdate,last_modified_by=? where id=?").setString(0, cDTO.getLastModifiedBy()).setInteger(1,
						cDTO.getId()).executeUpdate();
				message = CPSConstants.DELETE;
			} else
				message = CPSConstants.EXISTS;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String getEmpExist(String sfid) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from emp_master where sfid=? and status=1 ").setString(0, sfid).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	public String getPayEmpExist(String sfid) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from emp_pay_master where sfid=? and status=1 ").setString(0, sfid).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<PayScaleDesignationDTO> getPayScaleDetailsList() throws Exception {

		List<PayScaleDesignationDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = session.createCriteria(PayScaleDesignationDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}

	public PayBillEmpPaymentDeatilsDTO getEmpDetails(String sfid) throws Exception {
		PayBillEmpPaymentDeatilsDTO keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = (PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", sfid)).add(Expression.eq("status", 1)).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}

	@SuppressWarnings("unchecked")
	public List<PayBillLoanDTO> getEmpLoanDetails(String sfid) throws Exception {
		List<PayBillLoanDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = session.createCriteria(PayBillLoanDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).add(Expression.eq("sfid", sfid)).addOrder(Order.desc("loanDeduType")).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}

		return keyValueList;
	}

	@SuppressWarnings("unchecked")
	public List<LoanTypeMasterDTO> getLoanMasterList() throws Exception {
		List<LoanTypeMasterDTO> keyValueList = null;
		Session session = null;
		Integer arry[] = { Integer.parseInt(CPSConstants.FESTIVALLOANID), Integer.parseInt(CPSConstants.GPFADVANCELOANID), Integer.parseInt(CPSConstants.MOTORCARLOANID),
				Integer.parseInt(CPSConstants.MOTORSCOOTERLOANID), Integer.parseInt(CPSConstants.PCLOANID), Integer.parseInt(CPSConstants.HBALOANID), Integer.parseInt(CPSConstants.CYCLELOANID)

		};

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).add(Expression.in("id", arry)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}

		return keyValueList;
	}

	public String getEmpLoanExist(PayBillLoanDTO pblDTO) throws Exception {

		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from pay_loan_details where sfid=? and loan_type=? and LOAN_DEDU_TYPE=? and status=1 ").setString(0, pblDTO.getSfid()).setInteger(1,
					pblDTO.getLoanType()).setString(2, pblDTO.getLoanDeduType()).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String verfiyLoanEditDetailsExist(int pk, int deductionType) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when loan_type=? then 'Yes' else " + "case when (select count(*) from pay_loan_details where loan_type=? and status=1)>0 " + "then 'No' else 'Yes' end "
					+ "end as tes from " + "(select loan_type from pay_loan_details where id=?)";
			message = (String) session.createSQLQuery(query).setInteger(0, deductionType).setInteger(1, deductionType).setInteger(2, pk).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String updateEmpLoanDetails(PayBillLoanDTO pblDTO) throws Exception {

		Session session = null;
		String message = "";
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(pblDTO);

			session.flush();//tx.commit() ;

			session.flush();//tx.commit() ;
			pblDTO.setLoanMasterDetails((LoanTypeMasterDTO) session.createCriteria(LoanTypeMasterDTO.class).add(Expression.eq("id",pblDTO.getLoanType())).add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult());

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

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getQuarterDetails() throws Exception {

		List<KeyValueDTO> keyValueDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String sql = "select id as id,quarters_sub_type as name from PAY_QUARTERS_SUBTYPE_MASTER where STATUS=1  ";
			keyValueDTO = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueDTO;
	}

	@SuppressWarnings("unchecked")
	public List<PayBillCanfinDTO> getEmpCCDetails(String sfid) throws Exception {

		List<PayBillCanfinDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = session.createCriteria(PayBillCanfinDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).add(Expression.eq("sfid", sfid)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}

		return keyValueList;
	}

	public String deleteEmpCCDetails(PayBillCanfinDTO cDTO) throws Exception {

		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			session.createSQLQuery("update  PAY_DEDUCTIONS_DETAILS set status=0,modified_date=sysdate,modified_by=? where id=? and sfid=?").setString(0, cDTO.getLastModifiedBy()).setInteger(1,
					cDTO.getId()).setString(2, cDTO.getSfid()).executeUpdate();
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getAllowanceGroupList(String deductionID) throws Exception {

		List<KeyValueDTO> keyValueDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (deductionID.equalsIgnoreCase("SPLALLOWANCE")) {
				String Query = "select name, id from designation_master where id not in (select value from pay_allowance_configuration where allowance_name = 'SPLALLOWANCE') and status = 1 order by order_no asc";
				keyValueDTO = session.createSQLQuery(Query).addScalar("id", Hibernate.INTEGER).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			} else {
				String Query1 = "select name, id from group_master where id not in (select value from pay_allowance_configuration where allowance_name = 'WASHALLOWANCE') and status = 1 order by name";
				keyValueDTO = session.createSQLQuery(Query1).addScalar("id", Hibernate.INTEGER).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueDTO;
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getAllowanceSelGroupList(String deductionID) throws Exception {

		List<KeyValueDTO> keyValueDTO = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (deductionID.equalsIgnoreCase("SPLALLOWANCE")) {
				String Query = "select name as name,id as id from designation_master where id  in (select value from pay_allowance_configuration where allowance_name='SPLALLOWANCE' ) and status=1";

				keyValueDTO = session.createSQLQuery(Query).addScalar("id", Hibernate.INTEGER).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			} else {
				String Query1 = "select name as name,id as id from group_master where id  in (select value from pay_allowance_configuration where allowance_name='WASHALLOWANCE' ) and status=1";
				keyValueDTO = session.createSQLQuery(Query1).addScalar("id", Hibernate.INTEGER).addScalar("name").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueDTO;
	}

	public String deleteAllowanceConfigurationList(String pk) throws Exception {

		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			session.createSQLQuery("delete from pay_allowance_configuration where allowance_name=?").setString(0, pk).executeUpdate();
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String manageAllowanceConfigurationList(String allowanceType, String pk) throws Exception {

		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			deleteAllowanceConfigurationList(allowanceType);
			if (!pk.equals("")) {
				String[] roleLinks = pk.split(",");
				for (int i = 0; i < roleLinks.length; i++) {
					session.createSQLQuery("insert into  pay_allowance_configuration values(?,?)").setString(0, allowanceType).setString(1, roleLinks[i]).executeUpdate();
				}
			}
			message = CPSConstants.SUCCESS;

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String updateEmpDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		Session session = null;
		String message = "";
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO = new PayBillEmpPaymentDeatilsDTO();
			BeanUtils.copyProperties(payBillEmpPaymentDeatilsDTO, payBillMasterBean);
			payBillEmpPaymentDeatilsDTO.setSfID(payBillMasterBean.getSfidSearchKey().toUpperCase());
			payBillEmpPaymentDeatilsDTO.setCreatedBy(payBillMasterBean.getSfID()); //newly added
			payBillEmpPaymentDeatilsDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			payBillEmpPaymentDeatilsDTO.setLastModifiedBy(payBillMasterBean.getSfID());
			payBillEmpPaymentDeatilsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			payBillEmpPaymentDeatilsDTO.setPayStopFlag("No");
			String tempMessage = "";
			tempMessage = validateBasicSal(payBillMasterBean.getSfidSearchKey().toUpperCase(), payBillEmpPaymentDeatilsDTO.getBasicPay());

			if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
				message += tempMessage + "\n";
			tempMessage = validateGradePay(payBillMasterBean.getSfidSearchKey().toUpperCase(), payBillEmpPaymentDeatilsDTO.getgPay());

			if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
				    message += tempMessage + "\n";
			
			int id = getPresentSFIDDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
			if (id != 0)
				payBillEmpPaymentDeatilsDTO.setId(id);

			String member = checkGPFmember(payBillMasterBean.getSfidSearchKey().toUpperCase());
			String gpf = "";
			String cps = "";
			if (CPSUtils.compareStrings(member, CPSConstants.YES)) {
				gpf = payBillMasterBean.getGpfAccountNo();

				if (CPSUtils.compareStrings(CPSConstants.YES, payBillMasterBean.getGpfFlag())) {
					tempMessage = validateGPFAmount(payBillMasterBean.getSfidSearchKey().toUpperCase(), payBillEmpPaymentDeatilsDTO.getBasicPay(), payBillEmpPaymentDeatilsDTO.getgPay(),
							payBillEmpPaymentDeatilsDTO.getGpfSubAmt());
					if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
						message += tempMessage + "\n";
				}

			} else {
				cps = payBillMasterBean.getGpfAccountNo();

				if (CPSUtils.compareStrings(CPSConstants.YES, payBillMasterBean.getGpfFlag())) {
					tempMessage = validateCPSAmount(payBillMasterBean.getSfidSearchKey().toUpperCase(), payBillEmpPaymentDeatilsDTO.getBasicPay(), payBillEmpPaymentDeatilsDTO.getgPay(),
							payBillEmpPaymentDeatilsDTO.getGpfSubAmt());
					if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
						message += tempMessage + "\n";
				}

			}

			PayQuarterManagementDTO pqmDTO = new PayQuarterManagementDTO();
			if (CPSUtils.compareStrings(payBillMasterBean.getQuartersFlag(), CPSConstants.YES)) {

				int pkid = checkSfidDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
				if (pkid != 0)
					pqmDTO.setId(pkid);
				pqmDTO.setSfid(payBillMasterBean.getSfidSearchKey().toUpperCase());
				pqmDTO.setQuarterNo(payBillMasterBean.getQuarterNo());
				pqmDTO.setQuartersType(payBillMasterBean.getQuarterTypeId());
				pqmDTO.setCreatedBy(payBillMasterBean.getSfID());
				pqmDTO.setLastModifiedBy(payBillMasterBean.getSfID());
				pqmDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				pqmDTO.setCreationDate(CPSUtils.getCurrentDate());
				pqmDTO.setStatus(1);
			}

			if (CPSUtils.isNullOrEmpty(message)) {

				//tx = session.beginTransaction();
				payBillEmpPaymentDeatilsDTO.setStatus(1);
				EmployeeBean employeeBean=(EmployeeBean)session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", payBillEmpPaymentDeatilsDTO.getSfID())).uniqueResult();
				payBillEmpPaymentDeatilsDTO.setPayDesignationId(employeeBean.getDesignation());
				session.saveOrUpdate(payBillEmpPaymentDeatilsDTO);
				if(!gpf.equals("")&&!cps.equals("")){
				session.createSQLQuery("update  emp_master set gpf_ac_no=?,pran_no=?,family_planning=?,last_modified_date=sysdate,last_modified_by=? where  sfid=?").setString(0, gpf)
						.setString(1, cps).setString(2, payBillMasterBean.getFpaFlag()).setString(3, payBillMasterBean.getSfID()).setString(4, payBillMasterBean.getSfidSearchKey().toUpperCase())
						.executeUpdate();
				}
				else
				{
					session.createSQLQuery("update  emp_master set family_planning=?,last_modified_date=sysdate,last_modified_by=? where  sfid=?").setString(0, payBillMasterBean.getFpaFlag()).setString(1, payBillMasterBean.getSfID()).setString(2, payBillMasterBean.getSfidSearchKey().toUpperCase())
					.executeUpdate();
				}

				if (CPSUtils.compareStrings(payBillMasterBean.getQuartersFlag(), CPSConstants.YES)) {
					session.saveOrUpdate(pqmDTO);
				}

				session.flush();//tx.commit() ;
				message = CPSConstants.SUCCESS;
			}

		} catch (Exception e) {
			//tx.rollback();
			if (CPSUtils.isNullOrEmpty(message)) {
				message = CPSConstants.FAILED;
			}
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateBasicSal(String sfid, float basicPay) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String Query = "select case when count(*)>0  then 'YES' else 'Basic Pay value is not in prescribed band' end as valid from (select range_from,range_to from pay_payband_master where "
					+ " id=(select payband_type_id from payband_designation_mapping  where designation_id=(select designation_id from emp_master where sfid=?))) where " + " range_from<=? and range_to>=?";

			message = (String) session.createSQLQuery(Query).setString(0, sfid).setFloat(1, basicPay).setFloat(2, basicPay).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateBasicSalWithNewDesig(float basicPay, String designationID) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String Query = "select case when count(*)>0  then 'YES' else 'Basic Pay value is not in prescribed band' end as valid from (select range_from,range_to from pay_payband_master where "
					+ " id=(select payband_type_id from payband_designation_mapping  where designation_id=?)) where " + " range_from<=? and range_to>=?";

			message = (String) session.createSQLQuery(Query).setString(0, designationID).setFloat(1, basicPay).setFloat(2, basicPay).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateGradePay(String sfid, float gradePay) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when count(*)>0  then 'YES' else 'Grade pay value not in prescribed amount' end as valid from (select grade_pay from payband_designation_mapping "
					+ " where designation_id=(select designation_id from emp_master where sfid=?))  where grade_pay=?";

			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, gradePay).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public int getActualGradePay(String sfid) throws Exception {
		Session session = null;
		int actualGradePay;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select grade_pay from payband_designation_mapping where designation_id=(select designation_id from emp_master where sfid='"+sfid+"')";

			actualGradePay = (Integer) session.createSQLQuery(query).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return actualGradePay;
	}
	public String validateGPFAmount(String sfid, float basicpay, float gradepay, float gpfSubAmt) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			/*String query = "select case when ?>0 then " + "case when ?>?+? then 	'Not Exceed Basic pay plus Grade pay' else " + "case when ?<round((?+?)*(6/100),0)  then  "
					+ "'Subscription amount is less than 6 % of basic pay plus grade pay' else 'Yes' end end " + "else 'Value should not be zero' end " + "as name " + "from dual";

			message = (String) session.createSQLQuery(query).setFloat(0, gpfSubAmt).setFloat(1, gpfSubAmt).setFloat(2, basicpay).setFloat(3, gradepay).setFloat(4, gpfSubAmt).setFloat(5, basicpay)
					.setFloat(6, gradepay).uniqueResult();*/
			
			//create sql function--kumari
			message = (String) session.createSQLQuery("select validate_gpf_amt(?,?,?) from dual").setFloat(0, basicpay).setFloat(1, gradepay).setFloat(2, gpfSubAmt).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateCPSAmount(String sfid, float basicpay, float gradepay, float gpfSubAmt) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			/*String query = "select round((?+?+(select (floor((select da_value from(select da_value,rownum rowno from pay_dearness_allowance_master "
					+ "where status=1 order by da_date desc) where rowno=(Select Max(Rowno) R From((Select Da_value,Rownum Rowno From Pay_dearness_allowance_master Where Status=1 " +
					  "Order By Da_date Desc))))))*(?+?)/100 as temp from dual))*0.1,0) " + "as CPS from dual";

			float value = Float.parseFloat(session.createSQLQuery(query).setFloat(0, basicpay).setFloat(1, gradepay).setFloat(2, basicpay).setFloat(3, gradepay).uniqueResult().toString());*/
			
			//converted to sql function--kumari
			float value = Float.parseFloat(session.createSQLQuery("select calculate_cps_amount(?,?) from dual").setFloat(0, basicpay).setFloat(1, gradepay).uniqueResult().toString());
			
			if (value == gpfSubAmt) {
				message = CPSConstants.YES;

			} else {
				message = "Subscription amount should be 10% of basic pay, grade pay and DA("+(int)value+"/-).\n";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public int getPresentSFIDDetails(String sfID) throws Exception {
		int Id = 0;
		Session session = null;

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			PayBillEmpPaymentDeatilsDTO payBillDTO = (PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", sfID)).uniqueResult();
			session.evict(payBillDTO);
			if (!CPSUtils.isNull(payBillDTO)) {
				Id = payBillDTO.getId();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();

		}
		return Id;
	}

	public String checkGPFmember(String sfid) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when (select doj_govt from emp_master where sfid=?)<='31/DEC/2003'  then 'Yes' else 'No' end as valid from dual";

			message = session.createSQLQuery(query).setString(0, sfid).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public int checkSfidDetails(String sfid) throws Exception {
		int Id = 0;
		Session session = null;

		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			PayQuarterManagementDTO payBillDTO = (PayQuarterManagementDTO) session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("status", 1))
					.uniqueResult();
			if (!CPSUtils.isNull(payBillDTO)) {
				Id = payBillDTO.getId();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();

		}
		return Id;
	}

	public EmployeeBean getEmployeeDetails(String sfID) throws Exception {
		Session session = null;
		EmployeeBean empBean = null;
		String sql = null;
		String rd = null;
		String sd = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
/*			sql = "select emp.family_planning famPlanning,emp.gpf_ac_no gpfAcNo,emp.pran_no pranNo, emp.gender gender,emp.handicap_id handicap,emp.sfid sfid,emp.designation_id designationId,emp.directorate_id directorateId,emp.office_id officeId, "
					+ "to_char(emp.doj_govt,'dd-Mon-yyyy') dojGovt,to_char(emp.dob,'dd-Mon-yyyy') dob,dept1.department_name directorateName "
					+ ",dept2.department_name divisionName,emp.name_in_service_book name,emp.internal_phone_no internalNo,dm.name designationName,epd.grade_pay gradePay "
					+ ",etm.name employmentTypeId,emp.employment_type_id employmentId "
					+ "from emp_master emp,org_role_instance ori1,org_role_instance ori2,departments_master dept1,departments_master dept2,designation_master dm "
					+ ",employment_type_master etm,emp_pay_master epd "
					+ "where emp.sfid=? and dept1.status=1 and dept2.status=1 and ori1.status=1 and ori2.status=1 and emp.directorate_id=ori1.org_role_id "
					+ "and emp.office_id=ori2.org_role_id and ori1.department_id=dept1.department_id and ori2.department_id=dept2.department_id and dm.status=1 "
					+ "and emp.designation_id=dm.id and etm.status=1 and etm.id=emp.employment_type_id and epd.sfid=emp.sfid";
					
					empBean = (EmployeeBean) session.createSQLQuery(sql).addScalar("sfid").addScalar("designationId", Hibernate.STRING).addScalar("officeId", Hibernate.STRING).addScalar("directorateId",
					Hibernate.STRING).addScalar("dojGovt").addScalar("dob", Hibernate.STRING).addScalar("famPlanning", Hibernate.STRING).addScalar("gpfAcNo", Hibernate.STRING).addScalar("pranNo",
					Hibernate.STRING).addScalar("gender", Hibernate.STRING).addScalar("directorateName").addScalar("divisionName").addScalar("handicap", Hibernate.INTEGER).addScalar("name")
					.addScalar("internalNo").addScalar("designationName").addScalar("gradePay", Hibernate.STRING).addScalar("employmentTypeId").addScalar("employmentId", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).setString(0, sfID).uniqueResult();
					*/
			sql = "select emp.family_planning famPlanning,emp.gpf_ac_no gpfAcNo,emp.pran_no pranNo, emp.gender gender,emp.handicap_id handicap,emp.sfid sfid,emp.designation_id designationId,emp.directorate_id directorateId,emp.office_id officeId, "
				+ "to_char(emp.doj_govt,'dd-Mon-yyyy') dojGovt,to_char(emp.dob,'dd-Mon-yyyy') dob,dept1.department_name directorateName "
				+ ",dept2.department_name divisionName,emp.name_in_service_book name,emp.internal_phone_no internalNo,dm.name designationName,epd.grade_pay gradePay "
				+ ",etm.name employmentTypeId,emp.employment_type_id employmentId,labs.name workingLocation,emp.reservation_type_id  reservation  "    //new field is added to bean reservation
				+ "from emp_master emp,org_role_instance ori1,org_role_instance ori2,departments_master dept1,departments_master dept2,designation_master dm "
				+ ",employment_type_master etm,emp_pay_master epd, organizations_master labs "
				+ "where emp.sfid=? and dept1.status=1 and dept2.status=1 and ori1.status=1 and ori2.status=1 and emp.directorate_id=ori1.org_role_id "
				+ "and emp.office_id=ori2.org_role_id and ori1.department_id=dept1.department_id and ori2.department_id=dept2.department_id and dm.status=1 "
				+ "and emp.designation_id=dm.id and etm.status=1 and etm.id=emp.employment_type_id and epd.sfid=emp.sfid and labs.status=1 and labs.id=emp.working_location";
			empBean = (EmployeeBean) session.createSQLQuery(sql).addScalar("sfid").addScalar("designationId", Hibernate.STRING).addScalar("officeId", Hibernate.STRING).addScalar("directorateId",
					Hibernate.STRING).addScalar("dojGovt").addScalar("dob", Hibernate.STRING).addScalar("famPlanning", Hibernate.STRING).addScalar("gpfAcNo", Hibernate.STRING).addScalar("pranNo",
					Hibernate.STRING).addScalar("gender", Hibernate.STRING).addScalar("directorateName").addScalar("divisionName").addScalar("handicap", Hibernate.INTEGER).addScalar("name")
					.addScalar("internalNo").addScalar("designationName").addScalar("gradePay", Hibernate.STRING).addScalar("employmentTypeId").addScalar("employmentId", Hibernate.INTEGER).addScalar("workingLocation", Hibernate.STRING).addScalar("reservation", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).setString(0, sfID).uniqueResult();
			rd = session.createSQLQuery("select get_retirement_date('"+sfID+"') from dual").uniqueResult().toString();
			empBean.setRetirementDate(rd);
			sd = session.createSQLQuery("select seniority_date from emp_master where sfid='"+sfID+"'").uniqueResult().toString();
			empBean.setSeniorityDate(CPSUtils.formattedDate(sd));
			
			String message = checkGPFmember(sfID);
			if (CPSUtils.compareStrings(message, CPSConstants.NO)) {
				empBean.setGpfAcNo(empBean.getPranNo());
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return empBean;
	}

	public PayQuarterManagementDTO getEmpQuarterDetails(String sfid) throws Exception {
		PayQuarterManagementDTO keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyValueList = (PayQuarterManagementDTO) session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("status", 1)).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}

	public String updateEmpPayDetails(PayBillMasterBean payBillMasterBean) throws Exception {

		Session session = null;
		String message = "";
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//to insert into emp_pay_master_bkp for backup record
			PayBillEmpPaymentDeatilsDTO oldPayBillEmpPaymentDeatilsDTO=(PayBillEmpPaymentDeatilsDTO)session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", payBillMasterBean.getSfidSearchKey().toUpperCase())).uniqueResult();
			EmpPaymentDetailsLogDTO empPaymentDetailsLogDTO =new EmpPaymentDetailsLogDTO();
			BeanUtils.copyProperties(empPaymentDetailsLogDTO, oldPayBillEmpPaymentDeatilsDTO);
			empPaymentDetailsLogDTO.setId(0);
			empPaymentDetailsLogDTO.setCreationDate(CPSUtils.getCurrentDate());
			session.save(empPaymentDetailsLogDTO);
			session.flush();
			//end
			PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO = new PayBillEmpPaymentDeatilsDTO();
			BeanUtils.copyProperties(payBillEmpPaymentDeatilsDTO, payBillMasterBean);
			payBillEmpPaymentDeatilsDTO.setSfID(payBillMasterBean.getEmpID().toUpperCase());
			payBillEmpPaymentDeatilsDTO.setgPay(Integer.parseInt(payBillMasterBean.getType()));
			payBillEmpPaymentDeatilsDTO.setLastModifiedBy(payBillMasterBean.getSfID()); //newly added 
			payBillEmpPaymentDeatilsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			payBillEmpPaymentDeatilsDTO.setBasicRefId(oldPayBillEmpPaymentDeatilsDTO.getBasicRefId());
			payBillEmpPaymentDeatilsDTO.setGradeRefId(oldPayBillEmpPaymentDeatilsDTO.getGradeRefId());
			
			String tempMessage = "";
			String member = checkGPFmember(payBillMasterBean.getEmpID().toUpperCase());
			String prevGpf = "0";
			String gpfType = "";
			String gpfupdateStatus = CPSConstants.SAME;
			String cpsupdateStatus = CPSConstants.SAME;
			/*tempMessage = validateBasicSalWithNewDesig(payBillEmpPaymentDeatilsDTO.getBasicPay(), payBillMasterBean.getPayDesignationId());

			if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
				message += tempMessage + "\n";
			 */
			int id = getPresentSFIDDetails(payBillMasterBean.getEmpID().toUpperCase());
			if (id != 0)
				payBillEmpPaymentDeatilsDTO.setId(id);

			String gpfAccount = "";
			if (CPSUtils.compareStrings(member, CPSConstants.YES)) {
				gpfAccount = payBillMasterBean.getGpfAccountNo();

				if (CPSUtils.compareStrings(CPSConstants.YES, payBillMasterBean.getGpfFlag())) {
					if (payBillEmpPaymentDeatilsDTO.getGpfSubAmt() == 0) {
						if (getEmpAge(payBillMasterBean.getEmpID().toUpperCase()) < 717) {

							tempMessage = validateGPFAmount(payBillMasterBean.getEmpID().toUpperCase(), payBillEmpPaymentDeatilsDTO.getBasicPay(), payBillEmpPaymentDeatilsDTO.getgPay(),
									payBillEmpPaymentDeatilsDTO.getGpfSubAmt());
							if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
								message += tempMessage + "\n";

							if (CPSUtils.compareStrings(member, CPSConstants.YES) && CPSUtils.compareStrings(CPSConstants.YES, tempMessage)) {
								tempMessage = verifyGpfSubscription(payBillEmpPaymentDeatilsDTO.getGpfSubAmt(), payBillMasterBean.getEmpID().toUpperCase());
								if (!CPSUtils.compareStrings(tempMessage, CPSConstants.SAME)) {
									if (CPSUtils.compareStrings(tempMessage.split("#")[0], CPSConstants.YES)) {
										gpfType = tempMessage.split("#")[2];
										prevGpf = tempMessage.split("#")[1];
										gpfupdateStatus = CPSConstants.YES;
									} else {
										message += tempMessage + "\n";
									}
								}
							}
						}
					} else {
						tempMessage = validateGPFAmount(payBillMasterBean.getEmpID().toUpperCase(), payBillEmpPaymentDeatilsDTO.getBasicPay(), payBillEmpPaymentDeatilsDTO.getgPay(),
								payBillEmpPaymentDeatilsDTO.getGpfSubAmt());
						if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
							message += tempMessage + "\n";

						if (CPSUtils.compareStrings(member, CPSConstants.YES) && CPSUtils.compareStrings(CPSConstants.YES, tempMessage)) {
							tempMessage = verifyGpfSubscription(payBillEmpPaymentDeatilsDTO.getGpfSubAmt(), payBillMasterBean.getEmpID().toUpperCase());
							if (!CPSUtils.compareStrings(tempMessage, CPSConstants.SAME)) {
								if (CPSUtils.compareStrings(tempMessage.split("#")[0], CPSConstants.YES)) {
									gpfType = tempMessage.split("#")[2];
									prevGpf = tempMessage.split("#")[1];
									gpfupdateStatus = CPSConstants.YES;
								} else {
									message += tempMessage + "\n";
								}
							}
						}
					}
				}
				gpfupdateStatus = CPSConstants.YES;
			} else {
				gpfAccount = payBillMasterBean.getGpfAccountNo();
                if(!CPSUtils.compareStrings(CPSConstants.NO, payBillMasterBean.getGpfFlag())){
				tempMessage = validateCPSAmount(payBillMasterBean.getEmpID().toUpperCase(), payBillEmpPaymentDeatilsDTO.getBasicPay(), payBillEmpPaymentDeatilsDTO.getgPay(),
						payBillEmpPaymentDeatilsDTO.getGpfSubAmt());
				if (!CPSUtils.compareStrings(CPSConstants.YES, tempMessage))
					message += tempMessage + "\n";
                }
                cpsupdateStatus = CPSConstants.YES;
			}
			if (CPSUtils.isNullOrEmpty(message)) {
				//tx = session.beginTransaction();
				//payBillEmpPaymentDeatilsDTO.setStatus(1);
				 
				if(!CPSUtils.compareStrings(gpfupdateStatus, CPSConstants.SAME)){
					session.createQuery("update EmployeeBean set gpfAcNo=? where userSfid=?").setString(0, gpfAccount).setString(1, payBillMasterBean.getEmpID().toUpperCase()).executeUpdate();
				}else if(!CPSUtils.compareStrings(cpsupdateStatus, CPSConstants.SAME))
				{
					session.createQuery("update EmployeeBean set pran_no=? where userSfid=?").setString(0, gpfAccount).setString(1, payBillMasterBean.getEmpID().toUpperCase()).executeUpdate();
				}
				
				session.flush();
				session.clear();
				session.saveOrUpdate(payBillEmpPaymentDeatilsDTO);
                if (CPSUtils.compareStrings(gpfupdateStatus, CPSConstants.YES) && !CPSUtils.isNullOrEmpty(gpfType)) {
					PayGPFDetailsDTO paygpfDTO = new PayGPFDetailsDTO();
					paygpfDTO.setPresentGpf(payBillEmpPaymentDeatilsDTO.getGpfSubAmt());
					paygpfDTO.setPreviousGpf(Integer.parseInt(prevGpf));
					paygpfDTO.setType(gpfType);
					paygpfDTO.setCreatedBy(payBillMasterBean.getSfID());
					paygpfDTO.setCreationDate(CPSUtils.getCurrentDate());
					paygpfDTO.setLastModifiedBy(payBillMasterBean.getSfID());
					paygpfDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					paygpfDTO.setSfid(payBillMasterBean.getEmpID().toUpperCase());
					paygpfDTO.setStatus(1);
					session.saveOrUpdate(paygpfDTO);
				}

				session.flush();//tx.commit() ;
				message = CPSConstants.SUCCESS;
			}

		} catch (Exception e) {
			//tx.rollback();
			if (CPSUtils.isNullOrEmpty(message)) {
				message = CPSConstants.FAILED;
			}
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	private String verifyGpfSubscription(int gpfSubAmt, String sfid) throws Exception {

		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when ?=gpf_subs_amt then 'Same' else case when ?<gpf_subs_amt then "
					+ "case when (select count(*) from pay_gpf_sub_change_details where sfid=? and type='D' and to_char(creation_date,'yyyy')=to_char(sysdate,'yyyy'))<1 "
					+ "then 'Yes'||'#'||gpf_subs_amt||'#'||'D' else 'GPF Subscription decrement once in this year' end else "
					+ "case when (select count(*) from pay_gpf_sub_change_details where sfid=? and type='I' and to_char(creation_date,'yyyy')=to_char(sysdate,'yyyy'))<2 "
					+ "then 'Yes'||'#'||gpf_subs_amt||'#'||'I' else 'GPF Subscription increased twice in this year' end end end as tes "
					+ "from (select gpf_subs_amt from emp_pay_master where sfid=?)";
			message = (String) session.createSQLQuery(query).setFloat(0, gpfSubAmt).setFloat(1, gpfSubAmt).setString(2, sfid).setString(3, sfid).setString(4, sfid).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getEOLLeaves(String sfid, String fromDate, String toDate,String division) throws Exception {
		Session session = null;
		List<KeyValueDTO> keyValueDTO = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			/*String query = "select request_id id,debit_leaves+next_holidays+prev_holidays name,ref_number key,to_char(ref_date,'dd-Mon-yyyy') value,(select leave_type from leave_type_master where id in (lrd.debit_from)) flag from leave_request_details lrd,reference_number_details rnd "
					+ "where from_date>=? and to_date<=? and debit_from in (?,?,?) and lrd.do_part_id=rnd.id and lrd.status=8 and sfid=? and request_id not in (select request_id from pay_eol_mapping)"
					+ "union all "
					+ "select request_id id,debit_leaves name,ref_number key,to_char(ref_date,'dd-Mon-yyyy') value,(select leave_type from leave_type_master where id in (lcd.debit_from)) flag from leave_conversion_details lcd,reference_number_details rnd "
					+ "where from_date>=? and to_date<=? and debit_from in (?,?,?) and lcd.do_part_id=rnd.id  and lcd.status=8 and sfid=? and request_id not in (select request_id from pay_eol_mapping)";

			keyValueDTO= session.createSQLQuery(query).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value").addScalar("flag")
					.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).setString(0, fromDate).setString(1, toDate).setString(2, CPSConstants.HPL).setString(3, CPSConstants.EOL)
					.setString(4, CPSConstants.EOLWOMC).setString(5, sfid).setString(6, fromDate).setString(7, toDate).setString(8, CPSConstants.HPL).setString(9, CPSConstants.EOL).setString(10,
							CPSConstants.EOLWOMC).setString(11, sfid).list();*/
			
			//For getting all types of leaves //added by kumari
		
			
			if(sfid.equals("")){
				sfid=division;
			}
			
			if(!sfid.equals(division) && !fromDate.equals("") && !toDate.equals("") ){
				String query = "select request_id id,debit_leaves+next_holidays+prev_holidays name,ref_number key,to_char(ref_date,'dd-Mon-yyyy') value,(select leave_type from leave_type_master where id in (lrd.debit_from)) flag from leave_request_details lrd,reference_number_details rnd "
						+ "where from_date>=? and to_date<=? and lrd.do_part_id=rnd.id and lrd.status=8 and sfid=? and request_id not in (select request_id from pay_eol_mapping)"
						+ "union all "
						+ "select request_id id,debit_leaves name,ref_number key,to_char(ref_date,'dd-Mon-yyyy') value,(select leave_type from leave_type_master where id in (lcd.debit_from)) flag from leave_conversion_details lcd,reference_number_details rnd "
						+ "where from_date>=? and to_date<=? and lcd.do_part_id=rnd.id  and lcd.status=8 and sfid=? and request_id not in (select request_id from pay_eol_mapping)";
				
				
				keyValueDTO= session.createSQLQuery(query).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value").addScalar("flag")
						.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
						.setString(0, fromDate).setString(1, toDate).setString(2, sfid).setString(3, fromDate)
						.setString(4, toDate).setString(5, sfid)
						.list();
			}else if(!sfid.equals(division) && !fromDate.equals("")){
				//query for getting the selected  leaves for the period.
				// input from date and sfid
				//naagendra.v
				String queryforlimit="SELECT request_id id,  debit_leaves+next_holidays+prev_holidays name,ref_number KEY, TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
						+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lrd.debit_from)) flag "
						+ " FROM leave_request_details lrd,reference_number_details rnd WHERE from_date    >=?"
						+ " AND lrd.do_part_id  =rnd.id AND lrd.status=8 AND sfid=? AND request_id NOT IN "
						+ " (SELECT request_id FROM pay_eol_mapping) "
						+ " UNION ALL"
						+ " SELECT request_id id,debit_leaves name,ref_number KEY,TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
						+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lcd.debit_from)) flag "
						+ " FROM leave_conversion_details lcd, reference_number_details rnd WHERE from_date>=? "
						+ " AND lcd.do_part_id  =rnd.id AND lcd.status =8 AND sfid=? AND request_id NOT IN"
						+ "  (SELECT request_id FROM pay_eol_mapping)";
				keyValueDTO= session.createSQLQuery(queryforlimit).addScalar("id", Hibernate.INTEGER)
							.addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value")
							.addScalar("flag").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
							.setString(0, fromDate).setString(1, sfid).setString(2, fromDate)
							.setString(3, sfid)
							.list();
			}else if(!sfid.equals(division)){
				 String queryBySfid="SELECT request_id id,debit_leaves+next_holidays+prev_holidays name, ref_number KEY,"
				 		+ " TO_CHAR(ref_date,'dd-Mon-yyyy') value,(SELECT leave_type FROM leave_type_master WHERE id IN (lrd.debit_from)) flag "
				 		+ " FROM leave_request_details lrd,reference_number_details rnd WHERE  sfid=? AND lrd.do_part_id  =rnd.id "
				 		+ " AND lrd.status=8 AND request_id NOT IN (SELECT request_id FROM pay_eol_mapping) "
				 		+ " UNION ALL "
				 		+ " SELECT request_id,debit_leaves name,ref_number KEY,TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
				 		+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lcd.debit_from)) flag"
				 		+ " FROM leave_conversion_details lcd, reference_number_details rnd WHERE  sfid=?"
				 		+ " AND lcd.do_part_id  =rnd.id AND lcd.status=8 AND request_id NOT IN"
				 		+ "  (SELECT request_id FROM pay_eol_mapping )";
				keyValueDTO= session.createSQLQuery(queryBySfid).addScalar("id", Hibernate.INTEGER)
						.addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value")
						.addScalar("flag").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
						.setString(0, sfid).setString(1, sfid)
						.list();
		}else if(!fromDate.equals("")){
			    String queryByFromDate="SELECT request_id id,sfid,debit_leaves+next_holidays+prev_holidays name, ref_number KEY,"
			    		+ " TO_CHAR(ref_date,'dd-Mon-yyyy') value,(SELECT leave_type FROM leave_type_master "
			    		+ " WHERE id IN (lrd.debit_from)) flag FROM leave_request_details lrd,"
			    		+ " reference_number_details rnd "
			    		+ " WHERE from_date>=? AND lrd.do_part_id=rnd.id AND lrd.status=8 AND request_id NOT IN"
			    		+ " (SELECT request_id FROM pay_eol_mapping)"
			    		+ " UNION ALL"
			    		+ " SELECT request_id id,sfid,debit_leaves name,ref_number KEY,TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
			    		+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lcd.debit_from)) flag "
			    		+ " FROM leave_conversion_details lcd,reference_number_details rnd "
			    		+ " WHERE from_date>=? AND lcd.do_part_id=rnd.id AND lcd.status=8 "
			    		+ " AND request_id NOT IN (SELECT request_id FROM pay_eol_mapping)";
			keyValueDTO= session.createSQLQuery(queryByFromDate).addScalar("id", Hibernate.INTEGER)
					.addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value").addScalar("sfid")
					.addScalar("flag").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
					.setString(0, fromDate).setString(1, fromDate)
					.list();
		}else if(!toDate.equals("")){
			
			  String querybyToDate="SELECT request_id id,sfid,debit_leaves+next_holidays+prev_holidays name,ref_number KEY,"
			  		+ " TO_CHAR(ref_date,'dd-Mon-yyyy') value,(SELECT leave_type FROM leave_type_master "
			  		+ " WHERE id IN (lrd.debit_from)) flag FROM leave_request_details lrd,reference_number_details rnd "
			  		+ " WHERE  to_date<=? AND lrd.do_part_id  =rnd.id AND lrd.status=8 AND request_id NOT IN "
			  		+ " (SELECT request_id FROM pay_eol_mapping)"
			  		+ " UNION ALL "
			  		+ " SELECT request_id id,sfid,debit_leaves name,ref_number KEY,TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
			  		+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lcd.debit_from)) flag "
			  		+ " FROM leave_conversion_details lcd,reference_number_details rnd WHERE to_date<=?"
			  		+ " AND lcd.do_part_id  =rnd.id AND lcd.status=8 AND request_id NOT IN"
			  		+ " (SELECT request_id FROM pay_eol_mapping)";
			  
			keyValueDTO= session.createSQLQuery(querybyToDate).addScalar("id", Hibernate.INTEGER)
					.addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value").addScalar("sfid")
					.addScalar("flag").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
					.setString(0, toDate).setString(1, toDate)
					.list();
		}else if(!sfid.equals(division) && !toDate.equals("")){
			String queryforSfidandtodate="SELECT request_id id,sfid,debit_leaves+next_holidays+prev_holidays name,"
					+ " ref_number KEY,TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
					+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lrd.debit_from)) flag "
					+ " FROM leave_request_details lrd,reference_number_details rnd WHERE  to_date<=? AND"
					+ " lrd.do_part_id  =rnd.id AND lrd.status=8 AND sfid=? AND request_id NOT IN "
					+ " (SELECT request_id FROM pay_eol_mapping) "
					+ " UNION ALL "
					+ " SELECT request_id id,sfid,debit_leaves name,ref_number KEY,TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
					+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lcd.debit_from)) flag "
					+ " FROM leave_conversion_details lcd,reference_number_details rnd WHERE to_date<=? "
					+ " AND lcd.do_part_id  =rnd.id AND lcd.status =8 AND sfid=? AND request_id NOT IN"
					+ " (SELECT request_id FROM pay_eol_mapping)";
			
			
			keyValueDTO= session.createSQLQuery(queryforSfidandtodate).addScalar("id", Hibernate.INTEGER)
					.addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value").addScalar(sfid)
					.addScalar("flag").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
					.setString(0, toDate).setString(1, sfid).setString(2, toDate)
					.setString(3, sfid)
					.list();
		}else if(!toDate.equals("") && !fromDate.equals("") ){
			String queryfortodateandfromdate="SELECT request_id id,sfid,debit_leaves+next_holidays+prev_holidays name,"
					+ " ref_number KEY,  TO_CHAR(ref_date,'dd-Mon-yyyy') value,(SELECT leave_type FROM "
					+ " leave_type_master WHERE id IN (lrd.debit_from)) flag FROM leave_request_details lrd,"
					+ " reference_number_details rnd WHERE from_date >=? AND to_date <=? AND lrd.do_part_id  =rnd.id"
					+ " AND lrd.status =8 AND request_id NOT IN (SELECT request_id FROM pay_eol_mapping) "
					+ " UNION ALL "
					+ " SELECT request_id id,sfid,debit_leaves name,ref_number KEY,TO_CHAR(ref_date,'dd-Mon-yyyy') value,"
					+ " (SELECT leave_type FROM leave_type_master WHERE id IN (lcd.debit_from)) flag "
					+ " FROM leave_conversion_details lcd,reference_number_details rnd WHERE from_date>=?"
					+ " AND to_date<=? AND lcd.do_part_id  =rnd.id AND lcd.status=8 AND request_id NOT IN "
					+ " (SELECT request_id FROM pay_eol_mapping)";
			
			keyValueDTO= session.createSQLQuery(queryfortodateandfromdate).addScalar("id", Hibernate.INTEGER)
					.addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value")
					.addScalar("flag").addScalar("sfid").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class))
					.setString(0, toDate).setString(1, sfid).setString(2, toDate)
					.setString(3, sfid)
					.list();
			
		}
			
			
			
			else{
				//added by naagendra
				//getting all kinds of leaves	
				String queryforgetall="SELECT request_id id,sfid,debit_leaves+next_holidays+prev_holidays name,ref_number KEY,"
						+ " TO_CHAR(ref_date,'dd-Mon-yyyy') value,(SELECT leave_type FROM leave_type_master WHERE id IN "
						+ " (lrd.debit_from)) flag FROM leave_request_details lrd, reference_number_details rnd WHERE "
						+ " lrd.do_part_id  =rnd.id AND lrd.status =8 and leave_type_id in (2,6,15) AND request_id NOT IN "
						+ "  (SELECT request_id FROM pay_eol_mapping)"
						+ " UNION ALL"
						+ " SELECT request_id id,sfid, debit_leaves name, "
						+ "  ref_number KEY,   TO_CHAR(ref_date,'dd-Mon-yyyy') value, "
						+ "  (SELECT leave_type FROM leave_type_master WHERE id IN (lcd.debit_from)) flag "
						+ " FROM leave_conversion_details lcd,reference_number_details rnd WHERE  lcd.do_part_id  =rnd.id "
						+ " AND lcd.status=8 AND request_id NOT IN   (SELECT request_id FROM pay_eol_mapping)";
				
				keyValueDTO=session.createSQLQuery(queryforgetall).addScalar("id", Hibernate.INTEGER)
						.addScalar("name", Hibernate.STRING).addScalar("key").addScalar("value").addScalar("flag")
						.addScalar("sfid").setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueDTO;

	}

	public String verfiyCCDetailsExist(String sfid, String deductionType) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from pay_deductions_details where sfid=? and status=1 and deduction_type=?").setString(0, sfid).setString(1, deductionType)
					.uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String verfiyCCEditDetailsExist(int pk, int deductionType) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when deduction_type=? then 'Yes' else " + "case when (select count(*) from pay_deductions_details where deduction_type=? and status=1)>0 "
					+ "then 'No' else 'Yes' end " + "end as tes from " + "(select deduction_type from pay_deductions_details where id=?)";
			message = (String) session.createSQLQuery(query).setInteger(0, deductionType).setInteger(1, deductionType).setInteger(2, pk).uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String getEmpEntryDetails(String sfid) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from emp_pay_master where sfid=? and status=1").setString(0, sfid).uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<PayBillDuesDTO> getEmpDuesDetails(String sfid) throws Exception {
		List<PayBillDuesDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String query = "select id as id,d_amt amount,d_name as deductionName,d_type as deductionType,r_amt as recAmount,creation_date as creationDate from pay_dues_details  where sfid=? and r_amt>0 and status=1 order by d_name";

			keyValueList = session.createSQLQuery(query).addScalar("id", Hibernate.INTEGER).addScalar("deductionName").addScalar("deductionType").addScalar("amount", Hibernate.INTEGER).addScalar(
					"recAmount", Hibernate.INTEGER).addScalar("creationDate" ,Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayBillDuesDTO.class)).setString(0, sfid).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}

		return keyValueList;
	}

	public int getEmpAge(String sfid) throws Exception {
		Session session = null;
		int age = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select coalesce(trunc( months_between( sysdate, dob )),0) age " + " from emp_master where sfid=?";
			age = Integer.parseInt(session.createSQLQuery(query).setString(0, sfid).uniqueResult().toString());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return age;
	}

	public String validateDueMess(String sfid, float amt) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when 0=? then 'Yes' else "
					+ "case when (select type from designation_mappings where desig_id=(select designation_id from emp_master where sfid=?))='GAZETTED' "
					+ "then 'Yes'  else  'Mess charges are not applicable for staff' end  end as mess from dual";

			message = (String) session.createSQLQuery(query).setFloat(0, amt).setString(1, sfid).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateDueRegFund(String sfid, float amt) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select case when 0=? then 'Yes' else "
					+ "case when (select type from designation_mappings where desig_id=(select designation_id from emp_master where sfid=?))='GAZETTED' "
					+ "then 'Yes'  else  'Regimental fund  is not applicable for staff' end  end as reg from dual";

			message = (String) session.createSQLQuery(query).setFloat(0, amt).setString(1, sfid).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String updateEmpDues(PayBillDuesDTO payduesDTO) throws Exception {

		Session session = null;
		String message = "Yes";
		//Transaction tx = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (CPSUtils.compareStrings(payduesDTO.getDeductionType(), "credit")) {

				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "GpfAdv")) {
					message = validateLoan(payduesDTO.getSfid(), CPSConstants.FESTIVALLOANID);

				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "CarLoan")) {
					message = validateLoan(payduesDTO.getSfid(), CPSConstants.FESTIVALLOANID);

				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "ScooterLoan")) {
					message = validateLoan(payduesDTO.getSfid(), CPSConstants.FESTIVALLOANID);

				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "FestAdv")) {
					message = validateLoan(payduesDTO.getSfid(), CPSConstants.FESTIVALLOANID);

				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "PcLoan")) {
					message = validateLoan(payduesDTO.getSfid(), CPSConstants.FESTIVALLOANID);

				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "HBA")) {
					message = validateLoan(payduesDTO.getSfid(), CPSConstants.FESTIVALLOANID);

				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "CycleLoan")) {
					message = validateLoan(payduesDTO.getSfid(), CPSConstants.FESTIVALLOANID);

				}

				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "Canfin")) {
					message = validateDeduction(payduesDTO.getSfid(), CPSConstants.CANFIN);
				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "Gic")) {
					message = validateDeduction(payduesDTO.getSfid(), CPSConstants.CANFIN);
				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "CourtAttachment")) {
					message = validateDeduction(payduesDTO.getSfid(), CPSConstants.CANFIN);
				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "Hdfc")) {
					message = validateDeduction(payduesDTO.getSfid(), CPSConstants.CANFIN);
				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "Lic")) {
					message = validateDeduction(payduesDTO.getSfid(), CPSConstants.CANFIN);
				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "Rent") || CPSUtils.compareStrings(payduesDTO.getDeductionName(), "ElecBill")
						|| CPSUtils.compareStrings(payduesDTO.getDeductionName(), "FurnBill") || CPSUtils.compareStrings(payduesDTO.getDeductionName(), "WaterBill")
						|| CPSUtils.compareStrings(payduesDTO.getDeductionName(), "ResSecu")) {
					message = validateLFCharges(payduesDTO.getSfid(), payduesDTO.getAmount());
				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "Mess")) {
					message = validateDueMess(payduesDTO.getSfid(), payduesDTO.getAmount());
				}
				if (CPSUtils.compareStrings(payduesDTO.getDeductionName(), "RegFund")) {
					message = validateDueRegFund(payduesDTO.getSfid(), payduesDTO.getAmount());
				}

			} else {

				message = validateDuesSum(payduesDTO.getAmount(), payduesDTO.getDeductionName(), payduesDTO.getSfid());

			}

			if (CPSUtils.compareStrings(CPSConstants.YES, message)) {
				//tx = session.beginTransaction();
				session.saveOrUpdate(payduesDTO);
				session.flush();//tx.commit() ;
				message = CPSConstants.SUCCESS;
			}

		} catch (Exception e) {
			//tx.rollback();
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	
	
	// Start of getAssessmentTypeList()

		@SuppressWarnings("unchecked")
		public List<CategoryDTO> getAssessmentTypeList() throws Exception {
			List<CategoryDTO> list = null;
			Session session = null;
			try {
				session = hibernateUtils.getSession();
				// list =
				// session.createCriteria(CategoryDTO.class).add(Expression.eq(CPSConstants.STATUS,
				// 1)).list();
				list = session
						.createSQLQuery(
								"select distinct pcm.id id,pcm.category_name name from pay_emp_category_master pcm,payband_designation_mapping pdm where pcm.id=pdm.payband_type_id")
						.addScalar("id", Hibernate.INTEGER).addScalar("name",
								Hibernate.STRING).setResultTransformer(
								Transformers.aliasToBean(CategoryDTO.class)).list();
			} catch (Exception e) {
				throw e;
			}
			return list;
		}

		// end of getAssessmentTypeList()

		// start public List<PayBillDTO>getTotalPayDetails(String dept) throws
		// Exception

		@SuppressWarnings("unchecked")
		public List<PayBillDTO> getTotalPayDetails(String payRunMonth,int categoryId) throws Exception {
			List<PayBillDTO> list = null;
			Session session = null;
			try {
				
				
				session = hibernateUtils.getSession();
				list = session
						.createSQLQuery(
								"SELECT ptd.id id, ptd.sfid sfid,emp.name_in_service_book name,dm.name designation,ptd.tot_debits totalDebits," +
								" ptd.gross_pay totalCredits,ptd.net_pay netPay FROM (SELECT sfid FROM emp_pay_master epm WHERE epm.CATEGORY_ID=?) m," +
								"pay_txn_details ptd,emp_master emp,designation_master dm WHERE m.sfid=ptd.sfid AND" +
								" ptd.run_month=? AND" +
								" ptd.status IN (51,52) and m.sfid=emp.sfid and dm.id=emp.designation_id AND " +
								"ptd.id NOT IN(SELECT etpd.pay_txn_id FROM Emp_tot_pay_details etpd where etpd.status=1)")
						        .addScalar("id",
								Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("name",
								Hibernate.STRING).addScalar("designation",
								Hibernate.STRING).addScalar("totalDebits",
								Hibernate.INTEGER).addScalar("totalCredits",
								Hibernate.INTEGER).addScalar("netPay",Hibernate.INTEGER).setResultTransformer(
								Transformers.aliasToBean(PayBillDTO.class)).setInteger(0,categoryId).setString(1, payRunMonth).list();
				
				
			} catch (Exception e) {
				throw e;
			}
			return list;
		}
		
		//Start of 
		
		public String saveTOTPayDetails(PayBillMasterBean payBillMasterBean)throws Exception{
		
			Session session=null;
			String message="";
			//Transaction tx=null;
			try{
				session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
				//tx=session.beginTransaction();
				JSONObject jsonObj=new JSONObject(payBillMasterBean.getMainPayList());
				for(int i=0;i<jsonObj.length();i++){
					JSONObject innerJson=(JSONObject) jsonObj.get(String.valueOf(i));
					EmpTotalPayDTO empTotPayDTO=(EmpTotalPayDTO) session.createCriteria(EmpTotalPayDTO.class).add(Expression.eq("payTxnId", Integer.parseInt(innerJson.getString("payID")))).add(Expression.eq("status",1)).uniqueResult();
					
					if(CPSUtils.isNullOrEmpty(empTotPayDTO)){
						 empTotPayDTO=new EmpTotalPayDTO();
					     empTotPayDTO.setDvDate(CPSUtils.convertStringToDate(payBillMasterBean.getDvDate()));
					     empTotPayDTO.setDvNo(payBillMasterBean.getDvNo());
					     empTotPayDTO.setPayTxnId(Integer.parseInt(innerJson.getString("payID")));
					     empTotPayDTO.setCreatedBy(payBillMasterBean.getSfID());
					     empTotPayDTO.setCreationDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
					     empTotPayDTO.setLastModifiedBy(payBillMasterBean.getSfID());
					     empTotPayDTO.setLastModifiedDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
					     empTotPayDTO.setStatus(1);
					    
					     session.saveOrUpdate(empTotPayDTO);
					     session.flush();
					     message=CPSConstants.SUCCESS;
					}
					else{
						 empTotPayDTO.setLastModifiedBy(payBillMasterBean.getSfID());
					     empTotPayDTO.setLastModifiedDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
						 session.saveOrUpdate(empTotPayDTO);
						 session.flush();
						 message="recordexists";
					}
				  
				}
				session.flush();//tx.commit() ;
			}catch (Exception e) {
				//tx.rollback();
				e.printStackTrace();
				throw e;
			}finally{
				//session.close();
			}
			return message;

		}
		
		
		//End of 

	
	
	
	
	
	
	
	
                                              
	private String validateDuesSum(int amount, String deductionName, String sfid) throws Exception {
		Session session = null;
		String message = "No";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String query = "select case when (select COALESCE(sum(r_amt),0) from pay_dues_details where sfid=? and status=1 and d_name=? and d_type='credit')>=?+(select COALESCE(sum(r_amt),0) from pay_dues_details where sfid=? and status=1 and d_name=? and d_type='debit') then "
			+" 'Yes' else 'Debit sum is greater than credit sum' end tes from dual ";
			message = (String) session.createSQLQuery(query).setString(0, sfid).setString(1, deductionName).setFloat(2, amount).setString(3, sfid).setString(4, deductionName).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateLoan(String sfid, String loanType) throws Exception {
		Session session = null;
		String message = "NO";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			if (Integer.valueOf(session.createSQLQuery("select count(*) from pay_loan_details where sfid=? and loan_type=? and status=1 ").setString(0, sfid).setString(1, loanType).uniqueResult()
					.toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateDeduction(String sfid, String loanType) throws Exception {
		Session session = null;
		String message = "No";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			if (Integer.valueOf(session.createSQLQuery("select count(*) from pay_deductions_details where sfid=? and deduction_type=? and status=1 ").setString(0, sfid).setString(1, loanType)
					.uniqueResult().toString()) > 0) {
				message = "YES";
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateLFCharges(String sfid, float amt) throws Exception {
		Session session = null;
		String message = "No";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String query = "select case when (select quarters_flag from emp_pay_master where sfid=?) ='No' then " + "case when 0!=? then 'Employee NOT using quarters' else 'Yes' end "
					+ "else 'Yes' end tes from dual";
			message = (String) session.createSQLQuery(query).setString(0, sfid).setFloat(1, amt).uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public String updateEmpEolDetails(String sfid, String type, String accessSfid,Date runmonth) throws Exception {
		Session session = null;
		String message = "No";
		//Transaction tx = null;
		List<PayBillLoanDTO> keyValueList = null;
		List<PayBillLoanDTO> keyValueList1 = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String[] st = type.split(",");
			int sum=0;
			for (int i = 0; i < st.length; i++) {

				PayEolDTO payeolDTO = new PayEolDTO();
				payeolDTO.setRequestID(Integer.parseInt(st[i]));
				payeolDTO.setSfid(sfid);
				payeolDTO.setStatus(1);
				payeolDTO.setRunMonth(runmonth);
				payeolDTO.setCreatedBy(accessSfid);
				payeolDTO.setLastModifiedBy(accessSfid);
				payeolDTO.setCreationDate(CPSUtils.getCurrentDate());
				payeolDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(payeolDTO);

				PayBillEmpPaymentDeatilsDTO pdto = (PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("sfID", sfid)).add(Expression.eq("status", 1)).uniqueResult();
				String leaveConversionQuery = "select da_value emi,(toDate-fromDate)+1 presentInst,to_char(toDate,'Mon') loanDeduType,converted_to totalInst,debit_from loanType from ( "
						+ "select da_value,case when from_date<dafromdate then dafromdate else from_date end fromdate, "
						+ "case when to_date>daToDate then daToDate-1 else to_date end toDate,converted_to,debit_from "
						+ "from (select (select leave_type_id from leave_request_details lrd where lrd.id=lcd.reference_id) converted_to,lcd.debit_from,tab.da_date daFromDate,tab.da_end_date daToDate,tab.da_value,lcd.from_date,lcd.to_date "
						+ "from leave_conversion_details lcd,(select da_date,da_value,case when LEAD(da_date) OVER (ORDER BY da_date) is null then sysdate else LEAD(da_date) OVER (ORDER BY da_date) end da_end_date "
						+ "from pay_dearness_allowance_master where status=1) tab "
						+ "where (lcd.from_date between tab.da_date and tab.da_end_date or lcd.to_date between tab.da_date and tab.da_end_date "
						+ "or tab.da_date between lcd.from_date and lcd.to_date) and lcd.request_id=? and lcd.debit_from=any(?,?,?)))";
				keyValueList = session.createSQLQuery(leaveConversionQuery).addScalar("emi", Hibernate.INTEGER).addScalar("loanDeduType", Hibernate.STRING).
				        addScalar("presentInst", Hibernate.INTEGER).addScalar("totalInst", Hibernate.INTEGER)
						.addScalar("loanType", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(PayBillLoanDTO.class)).
						setString(0, st[i]).setString(1, CPSConstants.EOL).setString(2,
						CPSConstants.EOLWOMC).setString(3, CPSConstants.HPL).list();

				if (keyValueList != null) {
					for(int j=0;j<keyValueList.size();j++){
						PayBillLoanDTO pbdto=keyValueList.get(j);
						int convertedFrom=pbdto.getTotalInst();
						int convertedTo=pbdto.getLoanType();
						if(convertedTo!=convertedFrom)
						{
							String noOfLeavesQuery="select no_of_days  from leave_conversion_mapping where converted_from=? and converted_to=?";
						
							PayEolDTO pdto1 = (PayEolDTO) session.createSQLQuery(noOfLeavesQuery).addScalar("id", Hibernate.INTEGER)
							.setResultTransformer(Transformers.aliasToBean(PayEolDTO.class)).setInteger(0, convertedFrom).setInteger(1, convertedTo).uniqueResult();
							if(pdto1==null)
							{
								pdto1=new PayEolDTO();
								pdto1.setId(1);
							}
							float da=((pdto.getBasicPay()+pdto.getgPay())*pbdto.getEmi())/100;
							float total=(pdto.getBasicPay()+pdto.getgPay()+da)*(pdto1.getId()*pbdto.getPresentInst())/CPSUtils.getNoofDaysInMonth(pbdto.getLoanDeduType());
							
							sum+=total;
						}
					}
				}

				String leaveRequestQuery = "select da_value emi,(toDate-fromDate)+1 presentInst,to_char(toDate,'Mon') loanDeduType from ( select da_value,"
						+ "case when from_date<dafromdate then dafromdate else from_date end fromdate, "
						+ "case when to_date>daToDate then daToDate-1 else to_date end toDate "
						+ "from (select tab.da_date dafromdate,tab.da_end_date datodate,tab.da_value, "
						+ "lcd.from_date-lcd.prev_holidays from_date,lcd.to_date+lcd.next_holidays to_date "
						+ "from leave_request_details lcd,(select da_date,da_value,case when LEAD(da_date) OVER (ORDER BY da_date) is null then sysdate else LEAD(da_date) OVER (ORDER BY da_date) end da_end_date "
						+ "from pay_dearness_allowance_master where status=1) tab "
						+ "where (lcd.from_date between tab.da_date and tab.da_end_date or lcd.to_date between tab.da_date and tab.da_end_date "
						+ "or tab.da_date between lcd.from_date and lcd.to_date ) and lcd.request_id=? and lcd.debit_from=any(?,?,?)))";

				keyValueList1 = session.createSQLQuery(leaveRequestQuery).addScalar("emi", Hibernate.INTEGER).addScalar("loanDeduType", Hibernate.STRING).addScalar("presentInst", Hibernate.INTEGER).setResultTransformer(
						Transformers.aliasToBean(PayBillLoanDTO.class)).setString(0, st[i]).setString(1, CPSConstants.EOL).setString(2,CPSConstants.EOLWOMC).setString(3, CPSConstants.HPL).list();
				if (keyValueList1.size() != 0) {
					for(int k=0;k<keyValueList1.size();k++){
						PayBillLoanDTO pbdto=keyValueList1.get(k);
							float da=((pdto.getBasicPay()+pdto.getgPay())*pbdto.getEmi())/100;
							float total=(pdto.getBasicPay()+pdto.getgPay()+da)*(pbdto.getPresentInst())/CPSUtils.getNoofDaysInMonth(pbdto.getLoanDeduType());
							sum+=total;
					}
				}

			}
			
			PayBillDuesDTO pbduesDTO=new PayBillDuesDTO();
			
			pbduesDTO.setAmount(sum);
			pbduesDTO.setRecAmount(sum);
			pbduesDTO.setSfid(sfid);
			pbduesDTO.setDeductionName("EOL");
			pbduesDTO.setDeductionType("credit");
			pbduesDTO.setStatus(1);
			pbduesDTO.setCreatedBy(accessSfid);
			pbduesDTO.setModifiedBy(accessSfid);
			pbduesDTO.setModifiedDate(CPSUtils.getCurrentDate());
			pbduesDTO.setCreationDate(CPSUtils.getCurrentDate());
			session.saveOrUpdate(pbduesDTO);
			session.flush();//tx.commit() ;

		} catch (Exception e) {
			//tx.rollback();
			throw e;

		} finally {
			//session.close();
		}
		return message;
	}
	
	@SuppressWarnings( { "unchecked", "deprecation" })
	public int getTableID(String tableName) throws Exception {
		List masterObjList = null;
		Session session = null;
		int id = 0;
		PreparedStatement pstmt = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			Query qry = session.createSQLQuery("select value from id_generator where table_name=? FOR UPDATE");
			qry.setString(0, tableName);
			masterObjList = qry.list();
			id = Integer.parseInt(masterObjList.get(0).toString()) + 1;

			Connection con = session.connection();
			String sql = "update id_generator set value=? where table_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, tableName);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			ConnectionUtil.closeConnection(session, pstmt, null);
		}
		return id;
	}
	
	public String getPayMastersEffDate() throws Exception{
		String effMonth="";
		Session session=null;
		PayMastersPortingDTO portDTO=null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			 portDTO=(PayMastersPortingDTO)session.createCriteria(PayMastersPortingDTO.class).add(Expression.eq("runId", session.createCriteria(PayMastersPortingDTO.class).setProjection(Projections.max("runId")).uniqueResult())).uniqueResult();
			 effMonth=CPSUtils.formatDate(portDTO.getToDate());
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return effMonth;
	}

@SuppressWarnings("deprecation")
public String submitPayHindiIncDetails(PayBillMasterBean payBillMasterBean) throws Exception{
	String message="";
	Session session=null;
	//Transaction tx=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//tx=session.beginTransaction();
		message=checkHindiEmpExist(payBillMasterBean.getSfidSearchKey());
		if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)){
		PayHindiInceDTO payHindiDTO = new PayHindiInceDTO();
		if(CPSUtils.compareStrings("edit", payBillMasterBean.getType()))
			payHindiDTO.setId(Integer.parseInt(payBillMasterBean.getPk()));
		    payHindiDTO.setSfid(payBillMasterBean.getSfidSearchKey());
			payHindiDTO.setStartDate(CPSUtils.convertStringToDate(payBillMasterBean.getStartDate()));
			payHindiDTO.setEndDate(CPSUtils.convertStringToDate(payBillMasterBean.getEndDate()));
			payHindiDTO.setTotInst(payHindiDTO.getEndDate().getMonth()-payHindiDTO.getStartDate().getMonth()+1);
			payHindiDTO.setPresentInst(Integer.parseInt(payBillMasterBean.getInst()));
			payHindiDTO.setStatus(1);
			payHindiDTO.setCreatedBy(payBillMasterBean.getSfID());
			payHindiDTO.setLastModifiedBy(payBillMasterBean.getSfID());
			payHindiDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			payHindiDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			session.saveOrUpdate(payHindiDTO);
	    	session.flush();//tx.commit() ;
	    	message=CPSConstants.SUCCESS;
		}
	}catch (Exception e) {
		//tx.rollback();
		throw e;
	}finally{
	//session.close();
	}
	return message;
	
 }
@SuppressWarnings("unchecked")
public List<PayHindiInceDTO> getPayHindiInceList() throws Exception{
	 List<PayHindiInceDTO> list=null;
	 Session session=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		list = session.createCriteria(PayHindiInceDTO.class).add(Expression.eq("status", 1)).list();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return list;
	
  }
public String deletePayHindiDetails(int id ,String sfid) throws Exception{
	 Session session=null;
	 String message="";
	 //Transaction tx=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//tx=session.beginTransaction();
		PayHindiInceDTO payHindiInceDTO=(PayHindiInceDTO)session.load(PayHindiInceDTO.class, id);
		payHindiInceDTO.setStatus(0);
		payHindiInceDTO.setLastModifiedBy(sfid);
		session.update(payHindiInceDTO);
		session.flush();//tx.commit() ;
		message=CPSConstants.DELETE;
		
	}catch (Exception e) {
		//tx.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
public String checkHindiEmpExist(String sfid) throws Exception{
	 Session session=null;
	 String message="";
	 try{
		 session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		 EmployeeBean empBean = (EmployeeBean)session.get(EmployeeBean.class, sfid);
		 if(CPSUtils.isNullOrEmpty(empBean)){
			 message=CPSConstants.EMPNOTEXISTS;
		 }else{
			 PayHindiInceDTO payHindiInceDTO = (PayHindiInceDTO)session.createCriteria(PayHindiInceDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("status", 1)).uniqueResult();
			 if(!CPSUtils.isNullOrEmpty(payHindiInceDTO)){
				 message=CPSConstants.DUPLICATE;
			 }else{
				 message=CPSConstants.SUCCESS;
			 }
		 }
	 }catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
}
public PayBillMasterBean getEmpCorePayDetails(PayBillMasterBean payBillMasterBean) throws Exception{
Session session=null;
	try{
		 session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		 EmployeeBean empDTO=(EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", payBillMasterBean.getSfidSearchKey())).add(Expression.eq("status", 1)).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(empDTO)){
		 PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO =
			(PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).
			add(Expression.eq("sfID", payBillMasterBean.getSfidSearchKey())).add(Expression.eq("status", 1))
			.uniqueResult();
		 if(CPSUtils.isNullOrEmpty(payBillEmpPaymentDeatilsDTO)){
			 payBillMasterBean.setMessage(CPSConstants.SUCCESS);
			 EmpBasicPayHistoryDTO empBasicPayHistoryDTO=
				 (EmpBasicPayHistoryDTO)session.createCriteria(EmpBasicPayHistoryDTO.class)
				 .add(Expression.eq(CPSConstants.SFID,payBillMasterBean.getSfidSearchKey()))
				 .add(Expression.eq(CPSConstants.STATUS,1))
				 .add(Expression.eq("presentEffectiveDate", 
				      session.createCriteria(EmpBasicPayHistoryDTO.class)
				      .add(Expression.eq(CPSConstants.SFID,payBillMasterBean.getSfidSearchKey()))
				      .add(Expression.eq(CPSConstants.STATUS,1))
				      .setProjection(Projections.max("presentEffectiveDate")).uniqueResult())).uniqueResult();
			 PromoteesEntryDTO promoteesEntryDTO=
				 (PromoteesEntryDTO)session.createCriteria(PromoteesEntryDTO.class)
				 .add(Expression.eq("sfID", payBillMasterBean.getSfidSearchKey()))
				 .add(Expression.eq("promotedEffectiveDate", 
						 session.createCriteria(PromoteesEntryDTO.class)
						 .add(Expression.eq("sfID",payBillMasterBean.getSfidSearchKey()))
						 .add(Expression.eq(CPSConstants.STATUS,1))
						 .setProjection(Projections.max("promotedEffectiveDate")).uniqueResult()))
						 .add(Expression.eq("status", 1)).uniqueResult();
		     if(CPSUtils.isNullOrEmpty(empBasicPayHistoryDTO)&&CPSUtils.isNullOrEmpty(promoteesEntryDTO)){
		    	 payBillMasterBean.setMessage(CPSConstants.NOTEXISTS);
		    	 payBillMasterBean.setRemarks("<font color=red>Employee Basic,Grade Pay not exists</font>");
		     }else{
		    	String queryforcgiesflag= "select cgeis_mem_status from emp_pay_master where sfid=?";
		    	 payBillMasterBean.setBasicPay((int)empBasicPayHistoryDTO.getBasicPay());
		       	 payBillMasterBean.setgPay((int)promoteesEntryDTO.getNewGradePay());
		    	 payBillMasterBean.setBasicId(String.valueOf(empBasicPayHistoryDTO.getId()));
		    	 payBillMasterBean.setGradeId(String.valueOf(promoteesEntryDTO.getId()));
		    	 String cigiesflagvalue=(String)session.createSQLQuery(queryforcgiesflag).setString(0, payBillMasterBean.getSfidSearchKey()).uniqueResult();
		    	 payBillMasterBean.setCgeisMemFlag(cigiesflagvalue);
		    	 //System.out.println("masterflag is................"+cigiesflagvalue);
		    
		    
		    
		    	 if(!CPSUtils.isNullOrEmpty(promoteesEntryDTO.getTwoAddl()))
		    	 payBillMasterBean.setTwoAddIncr(Integer.parseInt(promoteesEntryDTO.getTwoAddl()));
		    	 else
		    		 payBillMasterBean.setTwoAddIncr(0); 
		    	 payBillMasterBean.setVarIncrPts(promoteesEntryDTO.getVarIncPt());
		    	 
		    	 payBillMasterBean.setRemarks("<b>Name:</b> "+"<font color=blue>"+empDTO.getNameInServiceBook()+"</font> <b>Designation:</b><font color=blue>"+empDTO.getDesignationDetails().getName()+"</font>");
		     //for automatically calculate gpf/cps sub amount----added by kumari
		    	 
		    	 if(checkGPFmember(payBillMasterBean.getSfidSearchKey().toUpperCase()).equals("Yes")){
		    		String gpfRange=Math.round(((payBillMasterBean.getBasicPay()+payBillMasterBean.getgPay())*6)/100)+"-"+(payBillMasterBean.getBasicPay()+payBillMasterBean.getgPay());
		    	 payBillMasterBean.setGpfRange(gpfRange);
		    	
		    	 }else{
					int cpsAmt =Integer.parseInt(session.createSQLQuery("select calculate_cps_amount(?,?) from dual").setFloat(0, payBillMasterBean.getBasicPay()).setFloat(1, payBillMasterBean.getgPay()).uniqueResult().toString());
					payBillMasterBean.setGpfSubAmt(cpsAmt);
		    	 }
		     }
		 }else{
			 payBillMasterBean.setMessage(CPSConstants.EXISTS);
			 payBillMasterBean.setRemarks("<font color=red>Employee Data Already Exists</font>");
		 }
	}else{
		payBillMasterBean.setMessage(CPSConstants.EXISTS);
		 payBillMasterBean.setRemarks("<font color=red>Employee Not Exists</font>");
	}
		//code for feching the CGIES values.....
	KeyValueDTO keyValueDTO =(KeyValueDTO)session.createSQLQuery("select reservation_type_id as key ,(select group_id from designation_mappings where desig_id=(select designation_id from emp_master where sfid='"+payBillMasterBean.getSfidSearchKey()+"')) as value from emp_master where sfid='"+payBillMasterBean.getSfidSearchKey()+"'").addScalar("key", Hibernate.STRING).addScalar("value", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).uniqueResult();
	if(!CPSUtils.isNullOrEmpty(keyValueDTO)){
		payBillMasterBean.setReservation(Integer.parseInt(keyValueDTO.getKey()));
		payBillMasterBean.setCgeisGroupID(Integer.parseInt(keyValueDTO.getValue()));
	}
	
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return payBillMasterBean;
}
@SuppressWarnings("unchecked")
public List<KeyValueDTO> getEmployeesNotExistedInPayBill()throws Exception{
	List<KeyValueDTO> empList=null;
	Session session=null;
	try{
		session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
		String query=" Select Sfid as name From (Select Sfid,Max(Effective_Date) "+ 
		" From Emp_Basic_Pay_History Group By Sfid Order By Sfid) Where Sfid Not In(Select Sfid From Emp_Pay_Master Pay where status=1) "+
		" and sfid in(select sfid from emp_master where status=1 and WORKING_LOCATION IN (1,5))And Sfid In(Select Sfid From (Select Sfid,Max(Promoted_Effective_Date) From Emp_grade_Pay_History Group By Sfid)) Order By Sfid";
		empList=session.createSQLQuery(query).addScalar("name",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();

	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return empList;
}

public List checkEmpCategoryDetailsList(PayBillEmpCategoryDTO cDTO)throws Exception 
{
	List empList=null;
	Session session=null;
	try{
		session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
		String query="from PayBillEmpCategoryDTO where status=1 and trim(upper(name))='"+cDTO.getName().toUpperCase().trim()+"'";
		if(!CPSUtils.isNullOrEmpty(cDTO.getId()))
		    query += " and id!="+cDTO.getId();
		empList=session.createQuery(query).list();

	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return empList;
}

public String checkUpdateEmpDues(PayBillMasterBean payBillMasterBean) throws Exception 
{
	Session session = null;
	String message = "";
	try
	{
		Criteria crit = null;
		session = hibernateUtils.getSession();
		crit = session.createCriteria(PayBillDuesDTO.class).add(Expression.ilike("sfid", payBillMasterBean.getEmpID())).add(Expression.ilike("deductionName", payBillMasterBean.getDeductionID())).add(Expression.eq("status", 1));
		if(CPSUtils.checkList(crit.list()))
		{
			message = CPSConstants.DUPLICATE;
		}
	}catch (Exception e) 
	{
		throw e;
	}finally{}
	return message;
}
@SuppressWarnings("unchecked")
public String checkEmpCategoryDetailsList(PayBillMasterBean payBillMasterBean) throws Exception
{
	Session session = null;
	String message = null;
	List<PayBillEmpCategoryDTO> list = null;
	try
	{
		session = hibernateUtils.getSession();
		list = session.createCriteria(PayBillEmpCategoryDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("payBillType", payBillMasterBean.getPayBillType())).add(Expression.ilike("name", payBillMasterBean.getCategoryName().trim())).list();
		session.clear();
		if(!CPSUtils.isNullOrEmpty(payBillMasterBean.getPk())){
			if(list.size()>1)
			message = CPSConstants.DUPLICATE;
			
		}else{
		  if(CPSUtils.checkList(list))
		  message = CPSConstants.DUPLICATE;
		}
	}catch (Exception e) {
		throw e;
	}
	return message;
}
public String submitDetails(PayBillMasterBean payBillMasterBean) throws Exception
{
	Session session = null;
	String message = "";
	try
	{
		session = hibernateUtils.getSession();
		int id =((BigDecimal)session.createSQLQuery("select ID from EMP_PAY_MASTER where SFID = '"+ payBillMasterBean.getSfidSearchKey() +"'").uniqueResult()).intValue();
		PayBillEmpPaymentDeatilsDTO bankDeatilsDTO = (PayBillEmpPaymentDeatilsDTO)session.get(PayBillEmpPaymentDeatilsDTO.class, id);
		bankDeatilsDTO.setBankAccNo(payBillMasterBean.getBankAccNo());
		bankDeatilsDTO.setBankBranch(payBillMasterBean.getBankBranch());
		bankDeatilsDTO.setBranchCode(payBillMasterBean.getBranchCode());
		session.saveOrUpdate(bankDeatilsDTO);
		/*session.createSQLQuery("update EMP_PAY_MASTER set BANK_ACCT_NO = ?, BANK_BRANCH_NO = ?, BANK_BRANCH_CODE = ? where SFID = ?")
			.setString(0, payBillMasterBean.getBankAccNo())
			.setString(1, payBillMasterBean.getBankBranch())
			.setString(2, payBillMasterBean.getBranchCode())
			.setString(3, payBillMasterBean.getSfidSearchKey()).executeUpdate();*/
		session.flush();
		message = CPSConstants.UPDATE;
		 
	}catch(Exception e)	
	{
		message = CPSConstants.FAILED;
		throw e;
	}finally{}
	return message;
}

public String employeeName(String sfid) throws Exception
{
	String str = "";
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		str = session.createSQLQuery("select name_in_service_book from emp_master where sfid = '"+ sfid+"'").uniqueResult().toString();
	}catch(Exception e)
	{
		throw e;
	}
	return str;
}

@SuppressWarnings("unchecked")
public List<String> getEmpNameList(String empName) throws Exception
{
	List<String> nameList = null;
	Session  session = null;
	try
	{
	 	Character oldchar = empName.charAt(0);
		Character newchar = Character.toUpperCase(oldchar);
		String name = 	empName.replaceFirst(oldchar.toString(), newchar.toString());
     	session = hibernateUtils.getSession();
		nameList = session.createSQLQuery("select NAME_IN_SERVICE_BOOK ||'-'|| SFID from Emp_Master where FIRST_NAME like '"+ name +"%' and status = 1").list();	
	}catch (Exception e) 
	{
		throw e;
	}
	return nameList;
}

public PayBillEmpPaymentDeatilsDTO getDetails(String sfid) throws Exception
{
	PayBillEmpPaymentDeatilsDTO bankDeatilsDTO = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		int id =((BigDecimal)session.createSQLQuery("select ID from EMP_PAY_MASTER where SFID = '"+ sfid +"'").uniqueResult()).intValue();
		bankDeatilsDTO = (PayBillEmpPaymentDeatilsDTO)session.get(PayBillEmpPaymentDeatilsDTO.class, id);
		/*record = session.createSQLQuery("select SFID as sfidSearchKey, BANK_ACCT_NO as bankAccNo, BANK_BRANCH_NO as bankBranch, BANK_BRANCH_CODE as branchCode from EMP_PAY_MASTER where SFID = '"+ sfid+"'")
				.addScalar("sfidSearchKey", Hibernate.STRING)
				.addScalar("bankAccNo", Hibernate.STRING)
				.addScalar("bankBranch", Hibernate.STRING)
				.addScalar("branchCode", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayBillMasterBean.class)).list();
		*/
	}catch(Exception e)
	{
		throw e;
	}
	return bankDeatilsDTO;
}

  public String  getLabid() throws Exception{
		Session session = null;
		String value = null;
		 
	  try {
			session = hibernateUtils.getSession();
				  value= session.createSQLQuery("select value from id_generator where table_name='LAB_ID'").uniqueResult().toString();
				 // System.out.println("value is apperared."+value);
		} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
	
	}
	return value;
	  
  }




/*
public PayBillMasterBean getRecordDetails(String sfid) throws Exception
{
	PayBillMasterBean payBillMasterBean = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		//int id =((BigDecimal)session.createSQLQuery("select ID from EMP_PAY_MASTER where SFID = '"+ payBillMasterBean.getSfidSearchKey() +"'").uniqueResult()).intValue();
		payBillMasterBean = (PayBillMasterBean)session.createSQLQuery("select SFID as sfidSearchKey, BANK_ACCT_NO as bankAccNo, BANK_BRANCH_NO as bankBranch, BANK_BRANCH_CODE as branchCode from EMP_PAY_MASTER where SFID = '"+ sfid+"'")
		.addScalar("sfidSearchKey", Hibernate.STRING)
		.addScalar("bankAccNo", Hibernate.STRING)
		.addScalar("bankBranch", Hibernate.STRING)
		.addScalar("branchCode", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayBillMasterBean.class)).uniqueResult();
	}catch(Exception e)
	{
		throw e;
	}
	return payBillMasterBean;
}
*/
}