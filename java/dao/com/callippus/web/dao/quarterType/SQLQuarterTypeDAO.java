package com.callippus.web.dao.quarterType;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.paybill.PayScaleBean;
import com.callippus.web.beans.quarterType.QuarterTypeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.paybill.dto.LicenceFeeChargesDTO;
import com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO;
import com.callippus.web.paybill.dto.PayTxnMastersDetailsDTO;
import com.callippus.web.paybill.dto.VariableIncrementDTO;

@SuppressWarnings("serial")
@Service
public class SQLQuarterTypeDAO implements IQuarterTypeDAO, Serializable {
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

	@SuppressWarnings("unchecked")
	public List<QuarterTypeBean> getQuarterList() throws Exception 
	{
		List<QuarterTypeBean> quarterList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			quarterList = session.createCriteria(QuarterTypeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("quartersType")).addOrder(Order.asc("quarterSubType").ignoreCase()).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return quarterList;
	}
	
	@SuppressWarnings("unchecked")
	public List<PayBillQuartersTypeMasterDTO> getQuarterTypeList() throws Exception
	{
		List<PayBillQuartersTypeMasterDTO> quarterList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			quarterList = session.createCriteria(PayBillQuartersTypeMasterDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("name")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return quarterList;
	}
	
	public String submitQuarterTypeDetails(QuarterTypeBean quarterTypeBean) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (quarterTypeBean.getStatus() == 0) // delete
			{
				quarterTypeBean = (QuarterTypeBean) session.get(QuarterTypeBean.class, Integer.valueOf(quarterTypeBean.getPk()));
				quarterTypeBean.setStatus(0);
				quarterTypeBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(quarterTypeBean);
				quarterTypeBean.setMessage(CPSConstants.DELETE);
			}
			else // duplicate checking
			{
				sb.append("select count(*) cnt from pay_quarters_subtype_master where quarters_sub_type = ? and quarters_type_id = ? and status = 1");
				if (!CPSUtils.isNullOrEmpty(quarterTypeBean.getPk()))
					sb.append("and id != " + quarterTypeBean.getPk());
				String count = session.createSQLQuery(sb.toString()).setString(0, quarterTypeBean.getQuarterSubType()).setString(1, quarterTypeBean.getQuartersType()).uniqueResult().toString();
				if (CPSUtils.compareStrings(count, "0"))
				{
					if (!CPSUtils.isNullOrEmpty(quarterTypeBean.getPk())) // update
					{
						quarterTypeBean.setId(Integer.valueOf(quarterTypeBean.getPk()));
						quarterTypeBean.setLastModifiedBy(quarterTypeBean.getSfid());
						quarterTypeBean.setLastModifiedDate(CPSUtils.getCurrentDate());
						quarterTypeBean.setMessage(CPSConstants.UPDATE);
					}
					else // new
					{
						quarterTypeBean.setCreatedBy(quarterTypeBean.getSfid());
						quarterTypeBean.setLastModifiedBy(quarterTypeBean.getSfid());
						quarterTypeBean.setCreationDate(CPSUtils.getCurrentDate());
						quarterTypeBean.setLastModifiedDate(quarterTypeBean.getCreationDate());
						quarterTypeBean.setMessage(CPSConstants.SUCCESS);
					}
					session.saveOrUpdate(quarterTypeBean);
					quarterTypeBean.setQuarterTypeDetails((PayBillQuartersTypeMasterDTO) session.createCriteria(PayBillQuartersTypeMasterDTO.class).add(Expression.eq("id", Integer.parseInt(quarterTypeBean.getQuartersType()))).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
				}else // duplicate
					quarterTypeBean.setMessage(CPSConstants.DUPLICATE);
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			quarterTypeBean.setMessage(CPSConstants.FAILED);
			throw e;
		} finally {
			//session.close();
		}
		return quarterTypeBean.getMessage();
	}

	@SuppressWarnings("unchecked")
	public List<QuarterTypeBean> getQuarterSubTypeList() throws Exception 
	{
		List<QuarterTypeBean> quarterList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			quarterList = session.createCriteria(QuarterTypeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return quarterList;
	}

	@SuppressWarnings("unchecked")
	public List<LicenceFeeChargesDTO> getLicenceFeeList() throws Exception 
	{
		List<LicenceFeeChargesDTO> licenceFeeList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			licenceFeeList = session.createCriteria(LicenceFeeChargesDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("quartersType")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return licenceFeeList;
	}

	public String submitLicenceFeeDetails(QuarterTypeBean quarterTypeBean) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		String result = "";
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (quarterTypeBean.getStatus() == 0) // delete
			{
				LicenceFeeChargesDTO deletedRecord = (LicenceFeeChargesDTO)session.get(LicenceFeeChargesDTO.class, Integer.parseInt(quarterTypeBean.getPk()));
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("licFeeMastersId", Integer.parseInt(quarterTypeBean.getPk()))).list().size();
				if(size != 0)
				{
					deletedRecord.setStatus(2);
					deletedRecord.setLastModifiedDate(CPSUtils.getCurrentDate());
					deletedRecord.setLastModifiedBy(quarterTypeBean.getSfid());
					session.saveOrUpdate(deletedRecord);
					result = CPSConstants.INACTIVE;
				}
				else
				{
					deletedRecord.setStatus(0);
					deletedRecord.setLastModifiedDate(CPSUtils.getCurrentDate());
					deletedRecord.setLastModifiedBy(quarterTypeBean.getSfid());
					session.saveOrUpdate(deletedRecord);
					result = CPSConstants.DELETE;
				}
			}
			else if(CPSUtils.compareStrings("", quarterTypeBean.getPk())) //New Record
			{
				String check = checkLFeeDTODetails(quarterTypeBean);
				if(CPSUtils.compareStrings(check, CPSConstants.YES)) //There is no duplicate record in the database
				{
					LicenceFeeChargesDTO  oldMasterDTO = (LicenceFeeChargesDTO) session.createCriteria(LicenceFeeChargesDTO.class).add(Expression.eq("quartersType",quarterTypeBean.getQuartersType())).add(Expression.eq("quarterSubType",quarterTypeBean.getQuarterSubType())).add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(oldMasterDTO))
					{
						oldMasterDTO.setStatus(2);
						oldMasterDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						oldMasterDTO.setLastModifiedBy(quarterTypeBean.getSfid());
						session.saveOrUpdate(oldMasterDTO);
					}
					LicenceFeeChargesDTO licenceFeeChargesDTO = new LicenceFeeChargesDTO();
					licenceFeeChargesDTO.setQuartersType(quarterTypeBean.getQuartersType());
					licenceFeeChargesDTO.setQuarterSubType(quarterTypeBean.getQuarterSubType());
					licenceFeeChargesDTO.setLicenceFee(quarterTypeBean.getLicenceFee());
					licenceFeeChargesDTO.setElec(Integer.parseInt(quarterTypeBean.getElec()));
					licenceFeeChargesDTO.setWater(Integer.parseInt(quarterTypeBean.getWater()));
					licenceFeeChargesDTO.setFurn(Integer.parseInt(quarterTypeBean.getFurn()));
					licenceFeeChargesDTO.setCreatedBy(quarterTypeBean.getSfid());
					licenceFeeChargesDTO.setCreationDate(CPSUtils.getCurrentDate());
					licenceFeeChargesDTO.setLastModifiedBy(quarterTypeBean.getSfid());
					licenceFeeChargesDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					licenceFeeChargesDTO.setStatus(1);
					licenceFeeChargesDTO.setEffDate(quarterTypeBean.getEffDate());
					session.saveOrUpdate(licenceFeeChargesDTO);
					result = CPSConstants.SUCCESS;
				}
				else //duplicate exists
					result = CPSConstants.DUPLICATE;
			}
			else //update
			{
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("licFeeMastersId", Integer.parseInt(quarterTypeBean.getPk()))).list().size();
				if(size == 0) //No Dependencies
				{
					LicenceFeeChargesDTO licenceFeeChargesDTO = new LicenceFeeChargesDTO();
					licenceFeeChargesDTO.setId(Integer.parseInt(quarterTypeBean.getPk()));
					licenceFeeChargesDTO.setQuartersType(quarterTypeBean.getQuartersType());
					licenceFeeChargesDTO.setQuarterSubType(quarterTypeBean.getQuarterSubType());
					licenceFeeChargesDTO.setLicenceFee(quarterTypeBean.getLicenceFee());
					licenceFeeChargesDTO.setElec(Integer.parseInt(quarterTypeBean.getElec()));
					licenceFeeChargesDTO.setWater(Integer.parseInt(quarterTypeBean.getWater()));
					licenceFeeChargesDTO.setFurn(Integer.parseInt(quarterTypeBean.getFurn()));
					licenceFeeChargesDTO.setCreatedBy(quarterTypeBean.getSfid());
					licenceFeeChargesDTO.setCreationDate(CPSUtils.getCurrentDate());
					licenceFeeChargesDTO.setLastModifiedBy(quarterTypeBean.getSfid());
					licenceFeeChargesDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					licenceFeeChargesDTO.setStatus(1);
					licenceFeeChargesDTO.setEffDate(quarterTypeBean.getEffDate());
					session.saveOrUpdate(licenceFeeChargesDTO);
					licenceFeeChargesDTO.setQuarterSubTypeDetails((QuarterTypeBean) session.createCriteria(QuarterTypeBean.class).add(Expression.eq(CPSConstants.STATUS, 1)).add(Expression.eq("id", Integer.parseInt(licenceFeeChargesDTO.getQuartersType()))).uniqueResult());
					result = CPSConstants.UPDATE;
				}
				else//Dependencies exists
					result = CPSConstants.NOTUPDATE;
			}
			session.flush();//tx.commit() ;
			session.clear();
		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public String checkLFeeDTODetails(QuarterTypeBean quarterTypeBean) throws Exception
	{
		String message = "";
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			List<LicenceFeeChargesDTO> lfChargesList = session.createCriteria(LicenceFeeChargesDTO.class).add(Expression.eq("quartersType", quarterTypeBean.getQuartersType()))
			.add(Expression.eq("quarterSubType",quarterTypeBean.getQuarterSubType())).add(Expression.eq("effDate", quarterTypeBean.getEffDate())).add(Expression.eq("status", 1)).list();
			if(lfChargesList.size() == 0)
			    message = CPSConstants.YES;
			else
				 message = CPSConstants.NO;
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
@SuppressWarnings("unchecked")
public List<QuarterTypeBean> getPayQuarterSubTypeList(QuarterTypeBean quarterTypeBean) throws Exception{
	List<QuarterTypeBean> quarterSubTYpeList=null;
	Session session=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		quarterSubTYpeList = (List<QuarterTypeBean>)session.createCriteria(QuarterTypeBean.class).add(Expression.eq("quartersType", quarterTypeBean.getQuartersType())).add(Expression.eq("status", 1)).list();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return quarterSubTYpeList;
}
}
