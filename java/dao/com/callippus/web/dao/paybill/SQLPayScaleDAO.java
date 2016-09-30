package com.callippus.web.dao.paybill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.paybill.PayScaleBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.ConnectionUtil;
import com.callippus.web.paybill.dto.FamilyPlanningDTO;
import com.callippus.web.paybill.dto.PayAllwDetailsDTO;
import com.callippus.web.paybill.dto.PayAllwTypeDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.paybill.dto.PayTxnMastersDetailsDTO;
import com.callippus.web.paybill.dto.PaybandDTO;
import com.callippus.web.paybill.dto.TravellingAllowanceDTO;
import com.callippus.web.paybill.dto.TwpAddIncrPayScaleRangeDTO;
import com.callippus.web.paybill.dto.VariableIncrementDTO;

@SuppressWarnings("serial")
@Service
public class SQLPayScaleDAO implements IPayScaleDAO, Serializable {
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
	public List<PayScaleBean> getPayScaleList(PayScaleBean payScaleBean) throws Exception
	{
		List<PayScaleBean> payScaleList = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.PROFESSIONALTAX))
				sb.append("select id, gross_pay_from||'-'||gross_pay_to as name, tax_amount as secondTypeValue, to_char(eff_date, 'dd-Mon-yyyy') as effDateString from pay_professional_tax_master where status = 1 order by gross_pay_from");
			else if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.VARIABLEINCREMENT))
				sb.append("select id, grade_pay_from||'-'||grade_pay_to as name, increment_value as secondTypeValue, to_char(eff_date, 'dd-Mon-yyyy') as effDateString from pay_variable_increment_master where status = 1 order by increment_value");
			else if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.FAMILYPLANNING))
				sb.append("select id, grade_pay as name, allowance_amount as secondTypeValue, to_char(eff_date,'dd-Mon-yyyy') as effDateString from pay_family_planning_master where status = 1 order by grade_pay");
			else if (CPSUtils.compareStrings(payScaleBean.getType(), CPSConstants.TRAVELLINGALLOWANCE))
			{
				sb.append("select id, basic_pay_from||'-'||basic_pay_to as basicFm, grade_pay_from||'-'||grade_pay_to as name, allowance_amount as secondTypeValue, to_char(eff_date, 'dd-Mon-yyyy') as effDateString from pay_tra_master where status = 1 order by grade_pay_from asc");
				payScaleList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("basicFm", Hibernate.STRING).addScalar("name", Hibernate.STRING)
						.addScalar("secondTypeValue", Hibernate.STRING).addScalar("effDateString",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayScaleBean.class)).list();
				return payScaleList;
			}
			else
				sb.append("select id, payband_type||'-'||range_from||'-'||range_to as name, status as secondTypeValue, to_char(eff_date, 'dd-Mon-yyyy') as effDateString from pay_payband_master where status = 1 order by payband_type");
			
			payScaleList = session.createSQLQuery(sb.toString()).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING).addScalar("secondTypeValue", Hibernate.STRING)
					.addScalar("effDateString",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayScaleBean.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return payScaleList;
	}

	public String submitProfessionalTaxDetails(PayScaleBean payScaleBean) throws Exception 
	{
		Session session = null;
		Transaction tx = null;
		String result = "";
		try 
		{
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if (payScaleBean.getStatus() == 0) // delete
			{
				PayScaleBean deletedRecord = (PayScaleBean)session.get(PayScaleBean.class, Integer.parseInt(payScaleBean.getPk()));
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("profTaxId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				if(size != 0)
				{
					deletedRecord.setStatus(2);
					deletedRecord.setLastModifiedDate(CPSUtils.getCurrentDate());
					deletedRecord.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(deletedRecord);
					result = CPSConstants.INACTIVE;
				}
				else
				{
					deletedRecord.setStatus(0);
					deletedRecord.setLastModifiedDate(CPSUtils.getCurrentDate());
					deletedRecord.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(deletedRecord);
					result = CPSConstants.DELETE;
				}
			}
			else if(CPSUtils.compareStrings("",payScaleBean.getPk())) //New Record
			{
				String check = checkProfTaxDTODetails(payScaleBean);
				if(CPSUtils.compareStrings(check, CPSConstants.YES)) //There is no duplicate record in the database
				{
					PayScaleBean  oldMasterDTO = (PayScaleBean) session.createCriteria(PayScaleBean.class).add(Expression.eq("firstTypeValue",payScaleBean.getFirstTypeValue())).add(Expression.eq("to",payScaleBean.getTo())).add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(oldMasterDTO))
					{
						oldMasterDTO.setStatus(2);
						oldMasterDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						oldMasterDTO.setLastModifiedBy(payScaleBean.getSfid());
						session.saveOrUpdate(oldMasterDTO);
					}
					payScaleBean.setCreatedBy(payScaleBean.getSfid());
					payScaleBean.setCreationDate(CPSUtils.getCurrentDate());
					payScaleBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					payScaleBean.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(payScaleBean);
					result = CPSConstants.SUCCESS;
				}
				else //duplicate exists
					result = CPSConstants.DUPLICATE;
			}
			else //update
			{
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("profTaxId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				if(size == 0) //No Dependencies
				{//satya
				//	PayScaleBean dto=(PayScaleBean)session.get(PayScaleBean.class, Integer.parseInt(payScaleBean.getPk()));
					payScaleBean.setId(Integer.parseInt(payScaleBean.getPk()));
//					payScaleBean.setCreatedBy(dto.getCreatedBy());
//					payScaleBean.setCreationDate(dto.getCreationDate());
					payScaleBean.setLastModifiedBy(payScaleBean.getSfid());
					payScaleBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					session.saveOrUpdate(payScaleBean);
					result = CPSConstants.UPDATE;
				}
				else //Dependencies exists
					result = CPSConstants.NOTUPDATE;
			}
			session.flush();//tx.commit() ;

		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public String checkProfTaxDTODetails(PayScaleBean payScaleBean) throws Exception {
		String message="";
		Session session=null;
		try{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			List<PayScaleBean> payScaleBeanList = session.createCriteria(PayScaleBean.class)
			.add(Expression.eq("firstTypeValue", payScaleBean.getFirstTypeValue()))
			.add(Expression.eq("to",payScaleBean.getTo()))
			.add(Expression.eq("effDate", payScaleBean.getEffDate()))
			.add(Expression.eq("status", 1)).list();
			if(payScaleBeanList.size()==0)
			    message=CPSConstants.YES;
			else
				 message=CPSConstants.NO;
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public String checkVarIncrDTODetails(PayScaleBean payScaleBean) throws Exception {
		String message="";
		Session session=null;
		try{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			List<VariableIncrementDTO> varIncrList = session.createCriteria(VariableIncrementDTO.class)
			.add(Expression.eq("gradePayFrom", payScaleBean.getFirstTypeValue()))
			.add(Expression.eq("gradePayTo",payScaleBean.getTo()))
			.add(Expression.eq("effDate", payScaleBean.getEffDate()))
			.add(Expression.eq("status", 1)).list();
			if(varIncrList.size()==0)
			    message=CPSConstants.YES;
			else
				 message=CPSConstants.NO;
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public String checkFPADTODetails(PayScaleBean payScaleBean) throws Exception {
		String message="";
		Session session=null;
		try{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			List<FamilyPlanningDTO> varIncrList = session.createCriteria(FamilyPlanningDTO.class)
			.add(Expression.eq("gradePayFrom", payScaleBean.getFirstTypeValue()))
			.add(Expression.eq("effDate", payScaleBean.getEffDate()))
			.add(Expression.eq("status", 1)).list();
			if(varIncrList.size()==0)
			    message=CPSConstants.YES;
			else
				 message=CPSConstants.NO;
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public String checkTRADTODetails(PayScaleBean payScaleBean) throws Exception {
		String message="";
		Session session=null;
		try{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			List<TravellingAllowanceDTO> varIncrList = session.createCriteria(TravellingAllowanceDTO.class)
			.add(Expression.eq("basicPayFrom", payScaleBean.getBasicFm()))
			.add(Expression.eq("basicPayTo", payScaleBean.getBasicTo()))
			.add(Expression.eq("gradePayFrom", payScaleBean.getFirstTypeValue()))
			.add(Expression.eq("gradePayTo", payScaleBean.getTo()))
			.add(Expression.eq("effDate", payScaleBean.getEffDate()))
			.add(Expression.eq("status", 1)).list();
			if(varIncrList.size()==0)
			    message=CPSConstants.YES;
			else
				 message=CPSConstants.NO;
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	public String submitVariableIncrementDetails(PayScaleBean payScaleBean) throws Exception 
	{
		Session session = null;
		Transaction tx = null;
		String result = "";
		try 
		{
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(payScaleBean.getStatus() == 0) //delete
			{ 
				VariableIncrementDTO variableIncrementDTO = (VariableIncrementDTO)session.get(VariableIncrementDTO.class, Integer.parseInt(payScaleBean.getPk()));
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("varIncrId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				if(size != 0)
				{
					variableIncrementDTO.setStatus(2);
					variableIncrementDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					variableIncrementDTO.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(variableIncrementDTO);
					result = CPSConstants.INACTIVE;
				}
				else
				{
					variableIncrementDTO.setStatus(0);
					variableIncrementDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					variableIncrementDTO.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(variableIncrementDTO);
					result = CPSConstants.DELETE;
				}
			}
			else if(CPSUtils.compareStrings("", payScaleBean.getPk())) //New Record
			{
				String check = checkVarIncrDTODetails(payScaleBean);
				if(CPSUtils.compareStrings(check, CPSConstants.YES)) //there is no duplicate record in the database
				{
					VariableIncrementDTO  oldMasterDTO = (VariableIncrementDTO) session.createCriteria(VariableIncrementDTO.class).add(Expression.eq("gradePayFrom",payScaleBean.getFirstTypeValue())).add(Expression.eq("gradePayTo",payScaleBean.getTo())).add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(oldMasterDTO))
					{
						oldMasterDTO.setStatus(2);
						oldMasterDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						oldMasterDTO.setLastModifiedBy(payScaleBean.getSfid());
						session.saveOrUpdate(oldMasterDTO);
					}
					VariableIncrementDTO variableIncrementDTO = new VariableIncrementDTO();
					variableIncrementDTO.setGradePayFrom(payScaleBean.getFirstTypeValue());
					variableIncrementDTO.setGradePayTo(payScaleBean.getTo());
					variableIncrementDTO.setIncrementValue(payScaleBean.getSecondTypeValue());
					variableIncrementDTO.setCreatedBy(payScaleBean.getSfid());
					variableIncrementDTO.setCreationDate(CPSUtils.getCurrentDate());
					variableIncrementDTO.setLastModifiedBy(payScaleBean.getSfid());
					variableIncrementDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					variableIncrementDTO.setStatus(1);
					variableIncrementDTO.setEffDate(payScaleBean.getEffDate());
					session.saveOrUpdate(variableIncrementDTO);
					result = CPSConstants.SUCCESS;
				}else //duplicate exists
					result = CPSConstants.DUPLICATE;			
			}
			else //update
			{
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("varIncrId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				if(size == 0) //No Dependencies
				{
					VariableIncrementDTO variableIncrementDTO = new VariableIncrementDTO();
					variableIncrementDTO.setId(Integer.parseInt(payScaleBean.getPk()));
					variableIncrementDTO.setGradePayFrom(payScaleBean.getFirstTypeValue());
					variableIncrementDTO.setGradePayTo(payScaleBean.getTo());
					variableIncrementDTO.setIncrementValue(payScaleBean.getSecondTypeValue());
					variableIncrementDTO.setCreatedBy(payScaleBean.getSfid());
					variableIncrementDTO.setCreationDate(CPSUtils.getCurrentDate());
					variableIncrementDTO.setLastModifiedBy(payScaleBean.getSfid());
					variableIncrementDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					variableIncrementDTO.setStatus(1);
					variableIncrementDTO.setEffDate(payScaleBean.getEffDate());
					session.saveOrUpdate(variableIncrementDTO);
					result = CPSConstants.UPDATE;
				}else //Dependencies exists
					result = CPSConstants.NOTUPDATE;
			}
			session.flush();//tx.commit() ;
		}
		catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	public String submitFamilyPlanningDetails(PayScaleBean payScaleBean) throws Exception 
	{
		Session session = null;
		Transaction tx = null;
		String result = "";
		try 
		{
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(payScaleBean.getStatus() == 0) //delete
			{
				FamilyPlanningDTO familyPlanningDTO = (FamilyPlanningDTO)session.get(FamilyPlanningDTO.class, Integer.parseInt(payScaleBean.getPk()));
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("fpaMastersId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				if(size != 0)
				{
					familyPlanningDTO.setStatus(2);
					familyPlanningDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					familyPlanningDTO.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(familyPlanningDTO);
					result = CPSConstants.INACTIVE;
				}
				else
				{
					familyPlanningDTO.setStatus(0);
					familyPlanningDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					familyPlanningDTO.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(familyPlanningDTO);
					result = CPSConstants.DELETE;
				}
			}
			else if(CPSUtils.compareStrings("",payScaleBean.getPk())) //New Record
			{
				String check = checkFPADTODetails(payScaleBean);
				if(CPSUtils.compareStrings(check, CPSConstants.YES)) //There is no duplicate record in the database
				{
					FamilyPlanningDTO  oldMasterDTO = (FamilyPlanningDTO) session.createCriteria(FamilyPlanningDTO.class).add(Expression.eq("gradePayFrom",payScaleBean.getFirstTypeValue())).add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(oldMasterDTO))
					{
						oldMasterDTO.setStatus(2);
						oldMasterDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						oldMasterDTO.setLastModifiedBy(payScaleBean.getSfid());
						session.saveOrUpdate(oldMasterDTO);
					}
					FamilyPlanningDTO familyPlanningDTO = new FamilyPlanningDTO();
					familyPlanningDTO.setGradePayFrom(payScaleBean.getFirstTypeValue());
					familyPlanningDTO.setGradePayTo(payScaleBean.getTo());
					familyPlanningDTO.setRate(payScaleBean.getSecondTypeValue());
					familyPlanningDTO.setCreatedBy(payScaleBean.getSfid());
					familyPlanningDTO.setCreationDate(CPSUtils.getCurrentDate());
					familyPlanningDTO.setLastModifiedBy(payScaleBean.getSfid());
					familyPlanningDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					familyPlanningDTO.setStatus(1);
					familyPlanningDTO.setEffDate(payScaleBean.getEffDate());
					session.saveOrUpdate(familyPlanningDTO);
					result = CPSConstants.SUCCESS;
				}
				else //duplicate exists
					result = CPSConstants.DUPLICATE;
			}
			else  //update
			{
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("fpaMastersId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				if(size == 0) //No Dependencies
				{
					FamilyPlanningDTO familyPlanningDTO = new FamilyPlanningDTO();
					familyPlanningDTO.setId(Integer.parseInt(payScaleBean.getPk()));
					familyPlanningDTO.setGradePayFrom(payScaleBean.getFirstTypeValue());
					familyPlanningDTO.setGradePayTo(payScaleBean.getTo());
					familyPlanningDTO.setRate(payScaleBean.getSecondTypeValue());
					familyPlanningDTO.setCreatedBy(payScaleBean.getSfid());
					familyPlanningDTO.setCreationDate(CPSUtils.getCurrentDate());
					familyPlanningDTO.setLastModifiedBy(payScaleBean.getSfid());
					familyPlanningDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					familyPlanningDTO.setStatus(1);
					familyPlanningDTO.setEffDate(payScaleBean.getEffDate());
					session.saveOrUpdate(familyPlanningDTO);
					result = CPSConstants.UPDATE;
				}
				else 	//Dependencies exists
					result = CPSConstants.NOTUPDATE;
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	public String submitTravellingAllowanceDetails(PayScaleBean payScaleBean) throws Exception 
	{
		Session session = null;
		Transaction tx = null;
		String result = "";
		try 
		{
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(payScaleBean.getStatus() == 0) //delete
			{ 
				TravellingAllowanceDTO travellingAllowanceDTO = (TravellingAllowanceDTO)session.get(TravellingAllowanceDTO.class, Integer.parseInt(payScaleBean.getPk()));
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("traMasterId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				if(size != 0)
				{
					travellingAllowanceDTO.setStatus(2);
					travellingAllowanceDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					travellingAllowanceDTO.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(travellingAllowanceDTO);
					result = CPSConstants.INACTIVE;
				}
				else
				{
					travellingAllowanceDTO.setStatus(0);
					travellingAllowanceDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					travellingAllowanceDTO.setLastModifiedBy(payScaleBean.getSfid());
					session.saveOrUpdate(travellingAllowanceDTO);
					result = CPSConstants.DELETE;
				}
			}
			else if(CPSUtils.compareStrings("", payScaleBean.getPk())) //New Record
			{
				String check = checkTRADTODetails(payScaleBean); //Checking duplication
				if(CPSUtils.compareStrings(check, CPSConstants.YES)) //There is no duplicate record in the database
				{
					TravellingAllowanceDTO  oldRecord = (TravellingAllowanceDTO) session.createCriteria(TravellingAllowanceDTO.class).add(Expression.eq("gradePayFrom",payScaleBean.getFirstTypeValue())).add(Expression.eq("gradePayTo",payScaleBean.getTo())).add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(oldRecord))
					{
						oldRecord.setStatus(2);
						oldRecord.setLastModifiedDate(CPSUtils.getCurrentDate());
						oldRecord.setLastModifiedBy(payScaleBean.getSfid());
						session.saveOrUpdate(oldRecord);
					}
					TravellingAllowanceDTO travellingAllowanceDTO = new TravellingAllowanceDTO();
					travellingAllowanceDTO.setBasicPayFrom(payScaleBean.getBasicFm());
					travellingAllowanceDTO.setBasicPayTo(payScaleBean.getBasicTo());
					travellingAllowanceDTO.setGradePayFrom(payScaleBean.getFirstTypeValue());
					travellingAllowanceDTO.setGradePayTo(payScaleBean.getTo());
					travellingAllowanceDTO.setBasicPayFrom(payScaleBean.getBasicFm());
					travellingAllowanceDTO.setBasicPayTo(payScaleBean.getBasicTo());
					travellingAllowanceDTO.setTravellingAllowance(payScaleBean.getSecondTypeValue());
					travellingAllowanceDTO.setCreatedBy(payScaleBean.getSfid());
					travellingAllowanceDTO.setCreationDate(CPSUtils.getCurrentDate());
					travellingAllowanceDTO.setLastModifiedBy(payScaleBean.getSfid());
					travellingAllowanceDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					travellingAllowanceDTO.setStatus(1);
					travellingAllowanceDTO.setEffDate(payScaleBean.getEffDate());
					session.saveOrUpdate(travellingAllowanceDTO);
					result = CPSConstants.SUCCESS;
				}
				else //Duplicate exists
				{
					result = CPSConstants.DUPLICATE;
				}
			}
			else //update
			{
				 int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("traMasterId", Integer.parseInt(payScaleBean.getPk()))).list().size();
				 if(size == 0) //No Dependencies
				 {
					 TravellingAllowanceDTO travellingAllowanceDTO = new TravellingAllowanceDTO();
					 travellingAllowanceDTO.setId(Integer.parseInt(payScaleBean.getPk()));
					 travellingAllowanceDTO.setGradePayFrom(payScaleBean.getFirstTypeValue());
					 travellingAllowanceDTO.setGradePayTo(payScaleBean.getTo());
					 travellingAllowanceDTO.setBasicPayFrom(payScaleBean.getBasicFm());
					 travellingAllowanceDTO.setBasicPayTo(payScaleBean.getBasicTo());
					 travellingAllowanceDTO.setTravellingAllowance(payScaleBean.getSecondTypeValue());
					 travellingAllowanceDTO.setCreatedBy(payScaleBean.getSfid());
					 travellingAllowanceDTO.setCreationDate(CPSUtils.getCurrentDate());
					 travellingAllowanceDTO.setLastModifiedBy(payScaleBean.getSfid());
					 travellingAllowanceDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					 travellingAllowanceDTO.setStatus(1);
					 travellingAllowanceDTO.setEffDate(payScaleBean.getEffDate());
					 session.saveOrUpdate(travellingAllowanceDTO);
					 result = CPSConstants.UPDATE;
				}
				else //Dependencies exists
				{
					result = CPSConstants.NOTUPDATE;
				}
			}
			session.flush();//tx.commit() ;

		} catch (Exception e) {
			result = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return result;
	}

	// payScale designation
	@SuppressWarnings("unchecked")
	public List<PayScaleDesignationDTO> getPayDesignationList() throws Exception {
		List<PayScaleDesignationDTO> payDesignationList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			payDesignationList = session.createCriteria(PayScaleDesignationDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("designation")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return payDesignationList;
	}

	public String submitPayScaleDesignationDetails(PayScaleBean payScaleBean) throws Exception 
	{
		Session session = null;
		Transaction tx = null;
		StringBuffer sb = new StringBuffer();
		PayScaleDesignationDTO payScaleDesignationDTO = null;
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			String currentDate = CPSUtils.getCurrentDate();
			if (payScaleBean.getStatus() == 0) // delete 
			{
				payScaleDesignationDTO = (PayScaleDesignationDTO) session.get(PayScaleDesignationDTO.class, Integer.valueOf(payScaleBean.getPk()));
				payScaleDesignationDTO.setStatus(0);
				payScaleDesignationDTO.setLastModifiedBy(payScaleBean.getSfid());
				payScaleDesignationDTO.setLastModifiedDate(currentDate);
				session.saveOrUpdate(payScaleDesignationDTO);
				payScaleBean.setMessage(CPSConstants.DELETE);
			} 
			else // duplicate checking 
			{
				sb.append("select count(*) cnt from payband_designation_mapping where designation_id = ? and status = 1 ");
				if (!CPSUtils.isNullOrEmpty(payScaleBean.getPk()))
					sb.append("and id != " + payScaleBean.getPk());
				String count = session.createSQLQuery(sb.toString()).setString(0, payScaleBean.getDesignation()).uniqueResult().toString();
				if (CPSUtils.compareStrings(count, "0")) 
				{
					if (!CPSUtils.isNullOrEmpty(payScaleBean.getPk()))	// update 
					{
						payScaleDesignationDTO = new PayScaleDesignationDTO();
						payScaleDesignationDTO.setId(Integer.valueOf(payScaleBean.getPk()));
					} 
					else	// new 
					{
						payScaleDesignationDTO = new PayScaleDesignationDTO();
						payScaleDesignationDTO.setCreatedBy(payScaleBean.getSfid());
					}
					payScaleDesignationDTO.setStatus(1);
					payScaleDesignationDTO.setDesignation(payScaleBean.getDesignation());
					payScaleDesignationDTO.setPayband(payScaleBean.getPayband());
					payScaleDesignationDTO.setGradePay(payScaleBean.getGradePay());
					payScaleDesignationDTO.setCreationDate(currentDate);
					payScaleDesignationDTO.setLastModifiedBy(payScaleBean.getSfid());
					payScaleDesignationDTO.setLastModifiedDate(currentDate);
					session.saveOrUpdate(payScaleDesignationDTO);
					//satya.
					payScaleDesignationDTO.setDesignationDetails((DesignationDTO) session.createCriteria(DesignationDTO.class).add(Expression.eq("id", Integer.parseInt(payScaleDesignationDTO.getDesignation()))).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
					payScaleDesignationDTO.setPaybandDetails((PaybandDTO) session.createCriteria(PaybandDTO.class).add(Expression.eq("id", Integer.parseInt(payScaleDesignationDTO.getPayband()))).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
					payScaleBean.setMessage(CPSConstants.SUCCESS);
				} 
				else // duplicate 
					payScaleBean.setMessage(CPSConstants.DUPLICATE);
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return payScaleBean.getMessage();
	}

	@SuppressWarnings("unchecked")
	public List<PaybandDTO> getPayBandList() throws Exception
	{
		List<PaybandDTO> PaybandList = null;
		Session session = null;
		try
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			PaybandList = session.createCriteria(PaybandDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).addOrder(Order.asc("rangeFrom")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return PaybandList;
	}

	public String insertPayBandDetails(PayScaleBean payScaleBean) throws Exception 
	{
		Session session = null;
		String message = "";
		Transaction tx = null;
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			if(CPSUtils.isNullOrEmpty(payScaleBean.getPk())) //New Record
			{
				PaybandDTO dupDTO = (PaybandDTO) session.createCriteria(PaybandDTO.class).add(Expression.eq("effDate", payScaleBean.getEffDate())).add(Expression.eq("name", payScaleBean.getName())).add(Expression.eq("status", 1)).uniqueResult();
				if(CPSUtils.isNullOrEmpty(dupDTO))
				{
					PaybandDTO tempDTO = (PaybandDTO) session.createCriteria(PaybandDTO.class).add(Expression.eq("name", payScaleBean.getName())).add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(tempDTO))
					{
						tempDTO.setStatus(2);
						tempDTO.setLastModifiedBy(payScaleBean.getSfid());
						tempDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
						session.saveOrUpdate(tempDTO);
					}
					PaybandDTO paybandDTO = new PaybandDTO();
					paybandDTO.setName(payScaleBean.getName());
					paybandDTO.setRangeFrom(Integer.parseInt(payScaleBean.getRangeFrom()));
					paybandDTO.setRangeTo(Integer.parseInt(payScaleBean.getRangeTo()));
					paybandDTO.setEffDate(payScaleBean.getEffDate());
					paybandDTO.setCreatedBy(payScaleBean.getSfid());
					paybandDTO.setLastModifiedBy(payScaleBean.getSfid());
					paybandDTO.setCreationDate(CPSUtils.getCurrentDate());
					paybandDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					paybandDTO.setStatus(1);
					session.saveOrUpdate(paybandDTO);
					message = CPSConstants.SUCCESS;
				}
				else
					message = CPSConstants.DUPLICATE;
			}
			else // Edit Record
			{
				int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("payBandId", Integer.parseInt(payScaleBean.getPk()))).add(Expression.eq("status", 1)).list().size();
				if(size == 0)
				{
					PaybandDTO paybandDTO = new PaybandDTO();
					paybandDTO.setId(Integer.parseInt(payScaleBean.getPk()));
					paybandDTO.setName(payScaleBean.getName());
					paybandDTO.setRangeFrom(Integer.parseInt(payScaleBean.getRangeFrom()));
					paybandDTO.setRangeTo(Integer.parseInt(payScaleBean.getRangeTo()));
					paybandDTO.setEffDate(payScaleBean.getEffDate());
					paybandDTO.setLastModifiedBy(payScaleBean.getSfid());
					paybandDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
					paybandDTO.setStatus(1);
					session.saveOrUpdate(paybandDTO);
					message = CPSConstants.UPDATE;
				}
				else
					message = CPSConstants.NOTUPDATE;
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	

	public String deletePayBandMasterDetails(PaybandDTO paybandDTO) throws Exception 
	{
		Session session = null;
		String message = "";
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			int size = session.createCriteria(PayTxnMastersDetailsDTO.class).add(Expression.eq("payBandId", paybandDTO.getId())).add(Expression.eq("status", 1)).list().size();
			if(size == 0)
			{
				session.createSQLQuery("update PAY_PAYBAND_MASTER set status = 0, modified_date = sysdate, modified_by = ? where id = ?").setString(0, paybandDTO.getLastModifiedBy()).setInteger(1, paybandDTO.getId()).executeUpdate();
				message = CPSConstants.DELETE;
			}
			else
			{
				session.createSQLQuery("update PAY_PAYBAND_MASTER set status = 2, modified_date = sysdate, modified_by = ? where id = ?").setString(0, paybandDTO.getLastModifiedBy()).setInteger(1, paybandDTO.getId()).executeUpdate();
				message = CPSConstants.INACTIVE;
			}
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<DesignationDTO> getPayScaleDesignationList() throws Exception 
	{
		List<DesignationDTO> keyList = null;
		Session session = null;
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			keyList = session.createCriteria(DesignationDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("name")).list();
		} catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return keyList;
	}
	public String submitTWAIPayScaleDetails(PayScaleBean payScaleBean) throws Exception 
	{
		Session session = null;
		String message = "";
		Transaction tx = null;
		try 
		{
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			//tx=session.beginTransaction();
			if(CPSUtils.isNullOrEmpty(payScaleBean.getPk()))
			{
				TwpAddIncrPayScaleRangeDTO keyDTO = (TwpAddIncrPayScaleRangeDTO) session.createCriteria(TwpAddIncrPayScaleRangeDTO.class).add(Expression.eq("designationId", Integer.parseInt(payScaleBean.getDesignationId())))
									.add(Expression.eq("effDate", payScaleBean.getEffDate())).add(Expression.eq("status", 1)).uniqueResult();
				if(CPSUtils.isNullOrEmpty(keyDTO))
				{
					keyDTO = (TwpAddIncrPayScaleRangeDTO) session.createCriteria(TwpAddIncrPayScaleRangeDTO.class).add(Expression.eq("designationId", Integer.parseInt(payScaleBean.getDesignationId())))
									.add(Expression.eq("status", 1)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(keyDTO))
					{
						keyDTO.setStatus(2);
						keyDTO.setLastModifiedBy(payScaleBean.getSfid());
						keyDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						session.saveOrUpdate(keyDTO);
					}
					TwpAddIncrPayScaleRangeDTO twpAddIncrPayScaleRangeDTO = new TwpAddIncrPayScaleRangeDTO();
					twpAddIncrPayScaleRangeDTO.setDesignationId(Integer.parseInt(payScaleBean.getDesignationId()));
					twpAddIncrPayScaleRangeDTO.setAmount(Integer.parseInt(payScaleBean.getGradePay()));
					twpAddIncrPayScaleRangeDTO.setEffDate(payScaleBean.getEffDate());
					twpAddIncrPayScaleRangeDTO.setCreatedBy(payScaleBean.getSfid());
					twpAddIncrPayScaleRangeDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					twpAddIncrPayScaleRangeDTO.setLastModifiedBy(payScaleBean.getSfid());
					twpAddIncrPayScaleRangeDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					twpAddIncrPayScaleRangeDTO.setStatus(1);
					session.saveOrUpdate(twpAddIncrPayScaleRangeDTO);
					twpAddIncrPayScaleRangeDTO.setDesignationDetails((DesignationDTO) session.createCriteria(DesignationDTO.class).add(Expression.eq("id",twpAddIncrPayScaleRangeDTO.getDesignationId())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
					message = CPSConstants.SUCCESS;
				}
				else
					message = CPSConstants.DUPLICATE;
			}
			else //UPDATE
			{
				TwpAddIncrPayScaleRangeDTO twpAddIncrPayScaleRangeDTO = new TwpAddIncrPayScaleRangeDTO();
				twpAddIncrPayScaleRangeDTO.setId(Integer.parseInt(payScaleBean.getPk()));
				twpAddIncrPayScaleRangeDTO.setDesignationId(Integer.parseInt(payScaleBean.getDesignationId()));
				twpAddIncrPayScaleRangeDTO.setAmount(Integer.parseInt(payScaleBean.getGradePay()));
				twpAddIncrPayScaleRangeDTO.setEffDate(payScaleBean.getEffDate());
				twpAddIncrPayScaleRangeDTO.setCreatedBy(payScaleBean.getSfid());
				twpAddIncrPayScaleRangeDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				twpAddIncrPayScaleRangeDTO.setLastModifiedBy(payScaleBean.getSfid());
				twpAddIncrPayScaleRangeDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				twpAddIncrPayScaleRangeDTO.setStatus(1);
				session.saveOrUpdate(twpAddIncrPayScaleRangeDTO);
				twpAddIncrPayScaleRangeDTO.setDesignationDetails((DesignationDTO) session.createCriteria(DesignationDTO.class).add(Expression.eq("id",twpAddIncrPayScaleRangeDTO.getDesignationId())).add(Expression.eq(CPSConstants.STATUS, 1)).uniqueResult());
				message = CPSConstants.UPDATE;
			}
			session.flush();//tx.commit() ;
		} catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<TwpAddIncrPayScaleRangeDTO> getTwoAddIncrPayScaleList() throws Exception
	{
		List<TwpAddIncrPayScaleRangeDTO> keyList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			keyList = session.createCriteria(TwpAddIncrPayScaleRangeDTO.class).add(Expression.eq("status", 1)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return keyList;
	}
	 public String deleteTAIPayScaleDetails(int id,String sfid) throws Exception
	 {
		 Session session = null;
		 String message = "";
		 Transaction tx =null;
		 try
		 {
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			//tx = session.beginTransaction();
			TwpAddIncrPayScaleRangeDTO twpAddIncrPayScaleRangeDTO = (TwpAddIncrPayScaleRangeDTO) session.get(TwpAddIncrPayScaleRangeDTO.class, id);
			twpAddIncrPayScaleRangeDTO.setStatus(0);
			twpAddIncrPayScaleRangeDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			twpAddIncrPayScaleRangeDTO.setLastModifiedBy(sfid);
			session.saveOrUpdate(twpAddIncrPayScaleRangeDTO);
			session.flush();//tx.commit() ;
			message = CPSConstants.DELETE;
		}catch (Exception e) {
				//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		}finally{
				//session.close();
		}
		return message;
	 }
	@SuppressWarnings("unchecked")
	public List<PayAllwTypeDTO> getPayAllwTypeList() throws Exception{ 
		 Session session=null;
		 List<PayAllwTypeDTO> keyList=null;
		 try{
				session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
				keyList=session.createCriteria(PayAllwTypeDTO.class).add(Expression.eq("status", 1)).list();
				
		 }catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return keyList;
	}
	 public String submitPayConfDetails(PayScaleBean payScaleBean) throws Exception{
		 Session session = null;
			String message="";
			Transaction tx=null;
			try {
				session = hibernateUtils.getSession();//session = sessionFactory.openSession();
				//tx=session.beginTransaction();
				if(CPSUtils.isNullOrEmpty(payScaleBean.getPk())){
					PayAllwDetailsDTO keyDTO=(PayAllwDetailsDTO) session.createCriteria(PayAllwDetailsDTO.class).add(Expression.eq("allwTypeId", Integer.parseInt(payScaleBean.getCofigurationId())))
					.add(Expression.eq("effDate", payScaleBean.getEffDate())).add(Expression.eq("status", 1)).uniqueResult();
					if(CPSUtils.isNullOrEmpty(keyDTO)){
						keyDTO=(PayAllwDetailsDTO) session.createCriteria(PayAllwDetailsDTO.class).add(Expression.eq("allwTypeId", Integer.parseInt(payScaleBean.getCofigurationId())))
						.add(Expression.eq("status", 1)).uniqueResult();
						if(!CPSUtils.isNullOrEmpty(keyDTO)){
							keyDTO.setStatus(2);
							keyDTO.setLastModifiedBy(payScaleBean.getSfid());
							keyDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
							session.saveOrUpdate(keyDTO);
						}
						PayAllwDetailsDTO payAllwDetailsDTO = new PayAllwDetailsDTO();
						payAllwDetailsDTO.setAllwTypeId(Integer.parseInt(payScaleBean.getCofigurationId()));
						payAllwDetailsDTO.setAmount(Integer.parseInt(payScaleBean.getAmount()));
						payAllwDetailsDTO.setEffDate(payScaleBean.getEffDate());
						payAllwDetailsDTO.setCreatedBy(payScaleBean.getSfid());
						payAllwDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
						payAllwDetailsDTO.setLastModifiedBy(payScaleBean.getSfid());
						payAllwDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						payAllwDetailsDTO.setStatus(1);
						session.saveOrUpdate(payAllwDetailsDTO);
						message=CPSConstants.SUCCESS;
						
					}else{
						message=CPSConstants.DUPLICATE;
					}
				}else{//UPDATE
					PayAllwDetailsDTO payAllwDetailsDTO = new PayAllwDetailsDTO();
					payAllwDetailsDTO.setId(Integer.parseInt(payScaleBean.getPk()));
					payAllwDetailsDTO.setAllwTypeId(Integer.parseInt(payScaleBean.getCofigurationId()));
					payAllwDetailsDTO.setAmount(Integer.parseInt(payScaleBean.getAmount()));
					payAllwDetailsDTO.setEffDate(payScaleBean.getEffDate());
					payAllwDetailsDTO.setCreatedBy(payScaleBean.getSfid());
					payAllwDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					payAllwDetailsDTO.setLastModifiedBy(payScaleBean.getSfid());
					payAllwDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					payAllwDetailsDTO.setStatus(1);
					session.saveOrUpdate(payAllwDetailsDTO);
					message=CPSConstants.UPDATE;
				}
				session.flush();//tx.commit() ;
			} catch (Exception e) {
				//tx.rollback();
				throw e;
			}finally{
				//session.close();
			}
			return message;
	 }
	 @SuppressWarnings("unchecked")
	public List<PayAllwDetailsDTO> getPayAllwDetailsList() throws Exception{
		 List<PayAllwDetailsDTO> keyList=null;
		 Session session=null;
		 try{
			 session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			 keyList = session.createCriteria(PayAllwDetailsDTO.class).add(Expression.eq("status", 1)).list();
			 for(int i=0;i<keyList.size();i++){
				 PayAllwDetailsDTO payAllwDetailsDTO=keyList.get(i);
				 PayAllwTypeDTO payAllwDetails=(PayAllwTypeDTO)session.get(PayAllwTypeDTO.class, payAllwDetailsDTO.getAllwTypeId());
				 payAllwDetailsDTO.setConfTypeDetails(payAllwDetails);
			 }
		 }catch (Exception e) {
		     throw e;
		}
		 finally{
			 //session.close();
		 }
		return keyList;
	 }
	 public String deletePayAllwDetails(int id,String sfid) throws Exception{
		 Session session=null;
		 String message="";
		 Transaction tx =null;
		 try{
				session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
				//tx = session.beginTransaction();
				PayAllwDetailsDTO payAllwDetailsDTO=(PayAllwDetailsDTO) session.get(PayAllwDetailsDTO.class, id);
				payAllwDetailsDTO.setStatus(0);
				payAllwDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				payAllwDetailsDTO.setLastModifiedBy(sfid);
				session.saveOrUpdate(payAllwDetailsDTO);
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
	 public String submitpayAllwTypeDetails(PayScaleBean payScaleBean) throws Exception{
		 Session session=null;
		 String message="";
		 Transaction tx =null;
		 try{
				session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
				//tx = session.beginTransaction();
				PayAllwTypeDTO payAllwTypeDTO = new PayAllwTypeDTO();
				if(!CPSUtils.isNullOrEmpty(payScaleBean.getPk())){
					payAllwTypeDTO.setId(Integer.parseInt(payScaleBean.getPk()));
				}
				payAllwTypeDTO.setAllwType(payScaleBean.getName());
				payAllwTypeDTO.setCreatedBy(payScaleBean.getSfid());
				payAllwTypeDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				payAllwTypeDTO.setLastModifiedBy(payScaleBean.getSfid());
				payAllwTypeDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				payAllwTypeDTO.setStatus(1);
				session.saveOrUpdate(payAllwTypeDTO);
				message=CPSConstants.SUCCESS;
				session.flush();//tx.commit() ;
		 }catch (Exception e) {
			 //tx.rollback();
			throw e;
		}
		 finally {
				ConnectionUtil.closeConnection(session, null, null);
			}
		 return message;
	 }
	 public String deletePayAllwType(int id,String sfid) throws Exception{
		 Session session=null;
		 String message="";
		 Transaction tx =null;
		 try{
				session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
				//tx = session.beginTransaction();
				PayAllwTypeDTO payAllwTypeDTO=(PayAllwTypeDTO) session.get(PayAllwTypeDTO.class, id);
				payAllwTypeDTO.setStatus(0);
				payAllwTypeDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				payAllwTypeDTO.setLastModifiedBy(sfid);
				session.saveOrUpdate(payAllwTypeDTO);
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
}
