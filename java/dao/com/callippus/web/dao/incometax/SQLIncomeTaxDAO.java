package com.callippus.web.dao.incometax;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.Alias;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.OverTimeDTO;
import com.callippus.web.beans.dto.PayChallanDetailsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
//import com.callippus.web.beans.incometax.ITaxDeductionsDto;
import com.callippus.web.beans.incometax.IncomeTaxMasterBean;
//import com.callippus.web.beans.incometax.ItaxExceptionSectionsDTO;
import com.callippus.web.beans.incometax.OverTimeVO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.incometax.dto.DBComparisonDTO;
import com.callippus.web.incometax.dto.IncomeTaxArrearsDTO;
import com.callippus.web.incometax.dto.IncomeTaxConfigDTO;
import com.callippus.web.incometax.dto.IncomeTaxDAStatusDTO;
import com.callippus.web.incometax.dto.IncomeTaxDurationDTO;
import com.callippus.web.incometax.dto.IncomeTaxPayBillDTO;
import com.callippus.web.incometax.dto.IncomeTaxRentDTO;
import com.callippus.web.incometax.dto.IncomeTaxRunDTO;
import com.callippus.web.incometax.dto.IncomeTaxRunStatusDTO;
import com.callippus.web.incometax.dto.IncomeTaxSavingsDTO;
import com.callippus.web.incometax.dto.IncomeTaxSavingsRunDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionMappingDTO;
import com.callippus.web.incometax.dto.IncomeTaxStageDTO;
import com.callippus.web.incometax.dto.ItarrearsDto;
import com.callippus.web.incometax.dto.PayBillCreditsMigrationDTO;
import com.callippus.web.incometax.dto.PayBillDebitsMigrationDTO;
import com.callippus.web.incometax.dto.PayBillRecoveriesMigrationDTO;
import com.callippus.web.incometax.dto.PayDBMigrationCoreDTO;
import com.callippus.web.incometax.dto.PayFinYearDTO;
import com.callippus.web.incometax.dto.PrUpdateAllwDTO;
import com.callippus.web.paybill.dto.LicenceFeeChargesDTO;
import com.callippus.web.paybill.dto.PayBillCanfinDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillEmpPaymentDeatilsDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayBillStatusDTO;
import com.callippus.web.paybill.dto.PayBillUserDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.paybill.dto.TLoanLinkDTO;
														
@SuppressWarnings("serial")
@Service
public class SQLIncomeTaxDAO implements IIncomeTaxDAO, Serializable {

	@Autowired
	private HibernateUtils hibernateUtils;
	@Autowired
    private CalculateIncomeTaxPlPrActl calculateIncomeTaxPlPrActl;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	
	
	public String insertFinYearMasterDetails(PayFinYearDTO fyDTO) throws Exception
	{
		Session session = null; // Transaction tx = null;
		String message = "";
		int dup = 0;
		try
		{
			session = hibernateUtils.getSession(); // tx = session.beginTransaction();
			if(fyDTO.getId() == 0)
				dup = session.createCriteria(PayFinYearDTO.class).add(Expression.eq("fyFrom", fyDTO.getFyFrom()))
						.add(Expression.eq("fyTo", fyDTO.getFyTo())).add(Expression.eq("status", 1)).list().size();
			if (dup != 0)
				message = CPSConstants.DUPLICATE;
			else
			{
				message = CPSConstants.SUCCESS;
				session.saveOrUpdate(fyDTO);
				session.flush(); // tx.commit();
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
	public List<PayFinYearDTO> getFinYearList() throws Exception 
	{
		List<PayFinYearDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			keyValueList = session.createCriteria(PayFinYearDTO.class).add(Expression.eq(CPSConstants.STATUS, 1)).list();
		    for(int i = 0; i < keyValueList.size(); i++)
		    {
		    	PayFinYearDTO dto = keyValueList.get(i);
		    	dto.setFyToFrom(dto.getFyFrom() + "-" + String.valueOf(dto.getFyTo()).substring(2));
		    }
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}
	
	//start of saveChallanDetails(P)1712 
	
	public String saveChallanDetails(PayChallanDetailsDTO payChallanDTO,String sfid) throws Exception{
		Session session=null;
		String message=null;
		try{
			
			session=hibernateUtils.getSession();
			/*PayChallanDetailsDTO itSavingsDTO=(PayChallanDetailsDTO)session.createCriteria(PayChallanDetailsDTO.class).add(Expression.eq("sfid",payChallanDTO.getSfid())).add(Expression.eq("itTxnId",payChallanDTO.getItTxnId())).add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult();
			IncomeTaxSavingsRunDTO itSavingsDTO=(IncomeTaxSavingsRunDTO)session.createCriteria(IncomeTaxSavingsRunDTO.class).add(Expression.eq("sfid",sfid)).add(Expression.eq("finYearId",payChallanDTO.getItTxnId())).
			add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult();
			if(!CPSUtils.isNull(itSavingsDTO)){*/
				PayChallanDetailsDTO payChallanDTO2=(PayChallanDetailsDTO)session.createCriteria(PayChallanDetailsDTO.class).add(Expression.eq("sfid",payChallanDTO.getSfid())).add(Expression.eq("itTxnId",payChallanDTO.getItTxnId())).add(Expression.eq(CPSConstants.STATUS,1)).uniqueResult();
				if(!CPSUtils.isNull(payChallanDTO2)){
					
					payChallanDTO2.setItTxnId(payChallanDTO.getItTxnId());
					payChallanDTO2.setChallanNo(payChallanDTO.getChallanNo());
					payChallanDTO2.setChallanDate(payChallanDTO.getChallanDate());
					payChallanDTO2.setChallanAmt(payChallanDTO.getChallanAmt());
					payChallanDTO2.setBankBSRNo(payChallanDTO.getBankBSRNo());
					payChallanDTO2.setStatus(payChallanDTO.getStatus());
					payChallanDTO2.setChallanRemarks(payChallanDTO.getChallanRemarks());
					payChallanDTO2.setSfid(payChallanDTO.getSfid());
					payChallanDTO2.setCreatedBy(payChallanDTO.getCreatedBy());
					payChallanDTO2.setCreationDate(payChallanDTO.getCreationDate());
					
					payChallanDTO2.setLastModifiedBy(payChallanDTO.getLastModifiedBy());
					payChallanDTO2.setLastModifiedDate(payChallanDTO.getLastModifiedDate());
					session.saveOrUpdate(payChallanDTO2);
					message=CPSConstants.UPDATE;
					
				}else{
					//payChallanDTO.setItTxnId(payChallanDTO.getItTxnId());
				    session.saveOrUpdate(payChallanDTO);
				    message = CPSConstants.SUCCESS;
				}
				
			/*}else
				        message="Data not available for this financial year ";*/
			 
				
			}catch(Exception e){
			
			throw e;
		}
		
		return message;
	}
	
	// end of saveChallanDetails(P)1712  
	
	
	// start of getSaveChallanDetails()
	 @SuppressWarnings("unchecked")
	public PayChallanDetailsDTO getSaveChallanDetails(String sfid,String finYearId)throws Exception{
		    PayChallanDetailsDTO payChallanDTO=null;
			Session session = null;
			try {
				session = hibernateUtils.getSession();
				
				/*payChallanDTO = (PayChallanDetailsDTO)session.createSQLQuery("select challan_no challanNo,challan_amt challanAmt,challan_date challanDate,BANK_BSR_NO bankBSRNo from pay_challan_details where it_txn_id =" +
						" (select id from income_tax_conf_run_details where sfid=? and fin_year_id=? and status=1) and status=1").addScalar("challanNo", Hibernate.STRING).
						addScalar("challanAmt", Hibernate.INTEGER).addScalar("challanDate", Hibernate.DATE).addScalar("bankBSRNo", Hibernate.STRING).setString(0, sfid).
						setInteger(1, Integer.parseInt(finYearId)).setResultTransformer(Transformers.aliasToBean(PayChallanDetailsDTO.class)).uniqueResult();*/
				payChallanDTO = (PayChallanDetailsDTO)session.createSQLQuery("select challan_no challanNo,challan_amt challanAmt,challan_date challanDate,BANK_BSR_NO bankBSRNo from pay_challan_details where it_txn_id =?" +
						" and sfid=? and status=1").addScalar("challanNo", Hibernate.STRING).
						addScalar("challanAmt", Hibernate.INTEGER).addScalar("challanDate", Hibernate.DATE).addScalar("bankBSRNo", Hibernate.STRING).setString(1, sfid).
						setInteger(0, Integer.parseInt(finYearId)).setResultTransformer(Transformers.aliasToBean(PayChallanDetailsDTO.class)).uniqueResult();
			} catch (Exception e) {
				throw e;
			} finally {
				//session.close();
			}
			return payChallanDTO;
		 
	 }
	
	//end of getSaveChallanDetails()

	public String deleteFinYearDetails(int id) throws Exception {
		Session session = null;
		String message = "";
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("update  PAY_IT_FIN_YEAR_MASTER set status=0,last_modified_date=sysdate where id=?").setInteger(0, id)
					.executeUpdate();
			message = CPSConstants.DELETE;

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String submitIncomeTaxDetails(IncomeTaxStageDTO taxStage) throws Exception {

		String message = CPSConstants.SUCCESS;
		Session session = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();
			//tx = session.beginTransaction();
			session.saveOrUpdate(taxStage);
			session.flush();
			//tx.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<IncomeTaxStageDTO> getincomeTaxStagelist(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxStageDTO> keyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			if(!CPSUtils.compareString(incomeTaxMasterBean.getTaxableflag(), "B"))
			{
				keyList = (List<IncomeTaxStageDTO>) session.createCriteria(IncomeTaxStageDTO.class)
						.add(Expression.eq("taxableFlag", "B"))
						.add(Expression.eq("age", incomeTaxMasterBean.getAge()))
						.add(Expression.eq("selectedFYear", incomeTaxMasterBean.getSelectedFYear())).add(Expression.eq("status", 1)).addOrder(Order.asc("stageId")).list();
				if(keyList.size() == 0)
				{
					keyList = (List<IncomeTaxStageDTO>) session.createCriteria(IncomeTaxStageDTO.class)
							.add(Expression.eq("taxableFlag", incomeTaxMasterBean.getTaxableflag()))
							.add(Expression.eq("age", incomeTaxMasterBean.getAge()))
							.add(Expression.eq("selectedFYear", incomeTaxMasterBean.getSelectedFYear())).add(Expression.eq("status", 1)).addOrder(Order.asc("stageId")).list();
				}
				else
					keyList.get(0).setDescription(CPSConstants.SAMESLABS);
			}
			else
			{
				keyList = (List<IncomeTaxStageDTO>) session.createCriteria(IncomeTaxStageDTO.class)
					.add(Expression.eq("taxableFlag", incomeTaxMasterBean.getTaxableflag()))
					.add(Expression.eq("age", incomeTaxMasterBean.getAge()))
					.add(Expression.eq("selectedFYear", incomeTaxMasterBean.getSelectedFYear())).add(Expression.eq("status", 1)).addOrder(Order.asc("stageId")).list();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}

	public String deleteIncomeTaxDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxStageDTO> keyList = null;
		Session session = null;
		String message = null;
		Transaction tx = null;
		try {
			session = hibernateUtils.getSession();
			//tx = session.beginTransaction();
			keyList = incomeTaxMasterBean.getIncomeTaxStageList();
			for (int i = 0; i < keyList.size(); i++) 
			{
				IncomeTaxStageDTO incomeTaxStageDTO = keyList.get(i);
				session.createSQLQuery("delete PAY_IT_TAX_SLAB_MASTER where TAX_FLAG = ? and STAGE_ID = ? and FIN_YEAR_ID = ?")
					.setString(0, incomeTaxStageDTO.getTaxableFlag()).setInteger(1, incomeTaxStageDTO.getStageId())
					.setString(2, incomeTaxStageDTO.getSelectedFYear()).executeUpdate();
			}
			message = CPSConstants.DELETE;
			//tx.commit();
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public String submitArrearsDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		Session session = null;
		String message = "";
		Transaction tx = null;
		List<PayBillDTO> usersList = null;
		int newDA = incomeTaxMasterBean.getNewDA();
		int oldDA = incomeTaxMasterBean.getOldDA();
		String remarks = "";
		String runMonthNames = "";
		String runMonthName = "";
		HashSet<String> runList = null;
		try {
			session = hibernateUtils.getSession();
			//tx = session.beginTransaction();
			DateFormat formatter = new SimpleDateFormat("01-MMM-yy");
			String sfDate = formatter.format(incomeTaxMasterBean.getFromDate());
			Date fDate = formatter.parse(sfDate);
			String stDate = formatter.format(incomeTaxMasterBean.getToDate());
			Date tDate = formatter.parse(stDate);
			message = validateRunMonthStatus(incomeTaxMasterBean);
			if (CPSUtils.isNullOrEmpty(message)) {
				message = validateRunMonth(incomeTaxMasterBean.getFromDate(), incomeTaxMasterBean.getToDate());
				runList = new HashSet<String>();
				if (CPSUtils.isNullOrEmpty(message)) {
					String query = "select txn.sfid as sfid,txn.c_basic_pay as basicPay ," + "txn.c_grade_pay as gradePay ," + "txn.c_da as da ,"
							+ "txn.c_tra as tpt ," + "txn.run_month as runMonth from pay_txn_details txn "
							+ "where status=52 and txn.run_month>='" + sfDate + "'and run_month<='" + stDate + "'";
					usersList = session.createSQLQuery(query).addScalar("sfid", Hibernate.STRING).addScalar("basicPay", Hibernate.INTEGER).addScalar(
							"gradePay", Hibernate.INTEGER).addScalar("da", Hibernate.INTEGER).addScalar("tpt", Hibernate.INTEGER).addScalar(
							"runMonth", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PayBillDTO.class)).list();

					for (int i = 0; i < usersList.size(); i++) {
						PayBillDTO payBillDTO = usersList.get(i);
						DateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
						DateFormat sdfSourceList = new SimpleDateFormat("MMM-yyyy");
						Date runMonth = sdfSource.parse(payBillDTO.getRunMonth());
						runMonthName = sdfSourceList.format(runMonth);
						boolean check = checkRunMonth(runMonth, fDate, tDate);
						if (check) {
							double daDue = Math.round((payBillDTO.getBasicPay() + payBillDTO.getGradePay()) * newDA * 0.01);
							double daDrawn = Math.round((payBillDTO.getBasicPay() + payBillDTO.getGradePay()) * oldDA * 0.01);
							int daDiff = (int) (daDue - daDrawn);
							double tptDrawn = payBillDTO.getTpt();
							double tptDue = Math.round((tptDrawn * 100) / (100 + oldDA)) + (newDA * ((tptDrawn * 100) / (100 + oldDA)) * 0.01);
							int tptDiff = (int) (tptDue - tptDrawn);
							int cpf = daDiff * 10 / 100;
							IncomeTaxArrearsDTO incomeTaxArrearsDTO = new IncomeTaxArrearsDTO();
							incomeTaxArrearsDTO.setSfid(payBillDTO.getSfid());
							incomeTaxArrearsDTO.setMonth(runMonth);
							incomeTaxArrearsDTO.setCategoryId(Integer.parseInt(incomeTaxMasterBean.getCategoryId()));
							incomeTaxArrearsDTO.setDateFrom(incomeTaxMasterBean.getFromDate());
							incomeTaxArrearsDTO.setDateTo(incomeTaxMasterBean.getToDate());
							incomeTaxArrearsDTO.setDaFrom(incomeTaxMasterBean.getOldDA());
							incomeTaxArrearsDTO.setDaTo(incomeTaxMasterBean.getNewDA());
							incomeTaxArrearsDTO.setDaTypeFlag(incomeTaxMasterBean.getDaFlag());
							incomeTaxArrearsDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
							incomeTaxArrearsDTO.setCreationDate(CPSUtils.getCurrentDate());
							incomeTaxArrearsDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
							incomeTaxArrearsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
							incomeTaxArrearsDTO.setStatus(1);
							incomeTaxArrearsDTO.setDaDue(daDue);
							incomeTaxArrearsDTO.setDaDrawn(daDrawn);
							incomeTaxArrearsDTO.setDaDiff(daDiff);
							incomeTaxArrearsDTO.setTptDue(tptDue);
							incomeTaxArrearsDTO.setTptDrawn(tptDrawn);
							incomeTaxArrearsDTO.setTptDiff(tptDiff);
							incomeTaxArrearsDTO.setCpf(cpf);

							session.saveOrUpdate(incomeTaxArrearsDTO);
							runList.add(runMonthName);

						}
					}
					if (usersList.size() == 0) {
						remarks = "There are No Employees";
						incomeTaxMasterBean.setRemarks(remarks);
						message = CPSConstants.NOTEXISTS;
					} else {
						message = CPSConstants.SUCCESS;
						Iterator<String> itr = runList.iterator();
						while (itr.hasNext()) {
							runMonthNames += "," + itr.next();
						}
						runMonthNames = runMonthNames.substring(1, runMonthNames.length());
						IncomeTaxDAStatusDTO itDAStatusDTO = new IncomeTaxDAStatusDTO();
						itDAStatusDTO.setFromDate(sfDate);
						itDAStatusDTO.setToDate(stDate);
						itDAStatusDTO.setOldDa(incomeTaxMasterBean.getOldDA());
						itDAStatusDTO.setNewDa(incomeTaxMasterBean.getNewDA());
						itDAStatusDTO.setDaType(incomeTaxMasterBean.getDaFlag());
						itDAStatusDTO.setRunStatus(1);
						session.saveOrUpdate(itDAStatusDTO);
						incomeTaxMasterBean.setMessage(message);
						remarks = "Run Has Been Successfully Completed For  The Following Months:" + runMonthNames;
						incomeTaxMasterBean.setRemarks(remarks);
						//tx.commit();
					}
				} else {
					incomeTaxMasterBean.setRemarks(message);
					message = CPSConstants.ALREADYDONE;
				}
			}

		} catch (Exception e) {
			tx.rollback();
			throw e;

		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings( { "deprecation" })
	public String validateRunMonth(Date fDate, Date tDate) throws Exception {
		int size = 0;
		Session session = null;
		String message = "";
		fDate.setDate(01);
		tDate.setDate(01);

		try {
			session = hibernateUtils.getSession();
			
			while (!fDate.after(tDate)) {
				size = session.createCriteria(IncomeTaxArrearsDTO.class).add(Expression.eq("month", fDate)).list().size();
				if (size > 0) {
					DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					String s = formatter.format(fDate);
					message += s.substring(3, s.length()) + " ,";
				}
				Calendar c = Calendar.getInstance();
				c.setTime(fDate);
				c.add(Calendar.MONTH, 1);
				fDate = c.getTime();
			}
			if (!CPSUtils.isNullOrEmpty(message)) {
				message = "Run Has Already Been Completed For The Following Months : \n " + message;
				message = message.substring(0, message.length() - 1);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String validateRunMonthStatus(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		int size = 0;
		Session session = null;
		String message = "";
		String remarks = "";
		try {
			session = hibernateUtils.getSession();
			DateFormat formatter = new SimpleDateFormat("MMM-yyyy");
			String sfDate = formatter.format(incomeTaxMasterBean.getFromDate());
			Date fDate = formatter.parse(sfDate);
			String stDate = formatter.format(incomeTaxMasterBean.getToDate());
			Date tDate = formatter.parse(stDate);
			while (!fDate.after(tDate)) {
				DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
				String sfDate1 = formatter1.format(fDate);
				size = session.createCriteria(PayBillStatusDTO.class).add(Expression.eq("runMonth", sfDate1)).add(Expression.eq("payStatus", 52))
						.add(Expression.eq("status", 1)).list().size();
				if (size == 0) {
					String s1 = formatter.format(fDate);
					message += s1 + " ,";
				}
				Calendar c = Calendar.getInstance();
				c.setTime(fDate);
				c.add(Calendar.MONTH, 1);
				fDate = c.getTime();
			}
			if (!CPSUtils.isNullOrEmpty(message)) {
				remarks = "\n Pay Bill Not Yet Started For The Following Months: \n " + message;
				remarks = remarks.substring(0, remarks.length() - 1);
				incomeTaxMasterBean.setRemarks(remarks);
				message = CPSConstants.NOTSTARTED;

			}

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public String submitSavingsDetails(IncomeTaxSavingsDTO incomeTaxSavingsDTO) throws Exception 
	{
		Session session = null; // Transaction tx = null;
		String message = "";
		int dup = 0;
		try 
		{
			session = hibernateUtils.getSession(); //tx = session.beginTransaction();
			if(incomeTaxSavingsDTO.getId() == 0)
				dup = session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("sName", incomeTaxSavingsDTO.getsName())).add(Expression.eq("sectionId", incomeTaxSavingsDTO.getSectionId()))
					.add(Expression.eq("sType", incomeTaxSavingsDTO.getsType())).add(Expression.eq("yearFrom", incomeTaxSavingsDTO.getYearFrom())).add(Expression.eq("yearTo", incomeTaxSavingsDTO.getYearTo()))
					.add(Expression.eq("status", 1)).list().size();
			if (dup != 0)
				message = CPSConstants.DUPLICATE;
			else
			{
				session.saveOrUpdate(incomeTaxSavingsDTO);
				session.flush(); //tx.commit();
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) { // tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<IncomeTaxSavingsDTO> getSavingsList() throws Exception {
		List<IncomeTaxSavingsDTO> keyValueList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			keyValueList = session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq(CPSConstants.STATUS, Integer.valueOf("1"))).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}
	
	////////////////u have to verify this code is usful or not------------******
	@SuppressWarnings("unchecked")	// Newly added -- Naresh
	public List<IncomeTaxSavingsDTO> getSavingsListWithSections() throws Exception 
	{
		List<IncomeTaxSavingsDTO> keyValueList = null;
		String sql = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			/*sql="SELECT savm.id AS id,savm.savings_type AS sType,savm.section_id  AS sectionId, secm.section_name AS secName,"+
			  " savm.savings_name AS sName,picd.actual AS actual, savm.year_from AS yearFrom, savm.year_to AS yearTo"+
			" FROM pay_it_savings_type_master savm, pay_it_section_master secm,pay_itax_config_details picd WHERE savm.status   = 1"+
			" AND savm.section_id = secm.id	and picd.savings_type_id = savm.id 	ORDER BY secm.order_no ASC";*/
		// NAGENDRA.V	
			sql="SELECT sm.section_name AS secName, svm.savings_name AS sName,svm.savings_type AS sType,svm.id AS pk,"
					+ " sm.id as id,svm.year_from AS yearFrom,svm.year_to AS yearTo "
					+ " FROM pay_it_section_master sm,"
					+ " pay_it_savings_type_master svm"
					+ " WHERE sm.id   =svm.section_id AND svm.status=1";
			
					
			/*sql = "SELECT savm.id AS id, savm.savings_type AS sType, savm.section_id AS sectionId, secm.section_name AS secName, savm.savings_name AS sName, savm.year_from AS yearFrom, savm.year_to AS yearTo "
					+ "FROM pay_it_savings_type_master savm, pay_it_section_master secm WHERE savm.status = 1 AND savm.section_id = secm.id ORDER BY secm.order_no ASC";*/
			keyValueList = session.createSQLQuery(sql).addScalar("sType", Hibernate.STRING)
					.addScalar("pk", Hibernate.INTEGER).addScalar("secName", Hibernate.STRING)
					.addScalar("sName", Hibernate.STRING).addScalar("yearFrom",Hibernate.INTEGER)
					.addScalar("yearTo",Hibernate.INTEGER).addScalar("id",Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(IncomeTaxSavingsDTO.class)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyValueList;
	}
	public String deleteSavingsDetails(int id) throws Exception 
	{
		Session session = null;
		String message = "";
		try 
		{
			session = hibernateUtils.getSession();
			session.createSQLQuery("update PAY_IT_SAVINGS_TYPE_MASTER set status = 0, last_modified_date = sysdate where id = ?").setInteger(0, id)
					.executeUpdate();
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	//fetching the list form PAY_IT_SAVINGS_TYPE_MASTER for popluate the section in combo box.
	@SuppressWarnings("unchecked")
	public List<IncomeTaxSavingsDTO> getConfigList() throws Exception {
		Session session = null;
		List<IncomeTaxSavingsDTO> keyList = null;
		try {
			session = hibernateUtils.getSession();
			keyList = session.createCriteria(IncomeTaxSavingsDTO.class).add(Expression.eq("status", 1)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}

	public String submitConfigDetails(List<IncomeTaxConfigDTO> configList) throws Exception 
	{
		Session session = null;
		String message = "";
		Transaction tx = null;
		int count = 0;
		IncomeTaxConfigDTO incomeTaxConfigDTO = null;
		IncomeTaxConfigDTO configDTO = null;
		try 
		{
			session = hibernateUtils.getSession();
			//tx = session.beginTransaction();
			for (int i = 0; i < configList.size(); i++) 
			{
				incomeTaxConfigDTO = configList.get(i);
				configDTO = (IncomeTaxConfigDTO) session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("savingsTypeId", incomeTaxConfigDTO.getSavingsTypeId()))
						.add(Expression.eq("selectedFYear", incomeTaxConfigDTO.getSelectedFYear()))
						.add(Expression.eq("sfID", incomeTaxConfigDTO.getSfID())).uniqueResult();
				if (!CPSUtils.isNullOrEmpty(configDTO)) 
				{
					count++;
					configDTO.setActual(incomeTaxConfigDTO.getActual());
					configDTO.setProjection(incomeTaxConfigDTO.getProjection());
					configDTO.setRemarks(incomeTaxConfigDTO.getRemarks());
					configDTO.setSubmissionFlag(incomeTaxConfigDTO.getSubmissionFlag());
					configDTO.setLastModifiedBy(incomeTaxConfigDTO.getLastModifiedBy());
					configDTO.setLastModifiedTime(incomeTaxConfigDTO.getLastModifiedTime());
					session.saveOrUpdate(configDTO);
					session.flush();
				} 
				else 
					session.saveOrUpdate(incomeTaxConfigDTO);
			}
			if (count > 0) 
				message = CPSConstants.UPDATE;
			else 
				message = CPSConstants.SUCCESS;
			//tx.commit();
		} catch (Exception e) {
			tx.rollback();
			message = CPSConstants.FAILED;
			throw e;

		} finally {
			//session.close();
		}
		return message;
	}

	public String validateProjectionDetails(int sectionId) throws Exception 
	{
		Session session = null;
		String message = "";
		String proType = "";
		IncomeTaxSavingsDTO incomeTaxSavingsDTO;
		try 
		{
			session = hibernateUtils.getSession();
			//incomeTaxSavingsDTO = (IncomeTaxSavingsDTO) session.get(IncomeTaxSavingsDTO.class, savingtypeid );
			incomeTaxSavingsDTO = (IncomeTaxSavingsDTO) session.get(IncomeTaxSavingsDTO.class, sectionId);
			proType = incomeTaxSavingsDTO.getsType();
			if (CPSUtils.compareStrings(proType, "Savings"))
				message = "yes";
		} catch (Exception e) {
			
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

/*	@SuppressWarnings("unchecked")
	public List<IncomeTaxConfigDTO> getConfigListDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxConfigDTO> keyList = null;
		Session session = null;
		try 
		{
			session = hibernateUtils.getSession();
			keyList = (List<IncomeTaxConfigDTO>) session.createCriteria(IncomeTaxConfigDTO.class)
					.add(Expression.eq("sfID", incomeTaxMasterBean.getSearchSfid()))
					.add(Expression.eq("selectedFYear", incomeTaxMasterBean.getSelectedFYear())).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}*/
	
	
	
	@SuppressWarnings("unchecked")
	public List<IncomeTaxConfigDTO> getConfigListDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxConfigDTO> keyList = null;
		Session session = null;
		try 
		{
			session = hibernateUtils.getSession();
			keyList = (List<IncomeTaxConfigDTO>) session.createCriteria(IncomeTaxConfigDTO.class)
					.add(Expression.eq("sfID", incomeTaxMasterBean.getSearchSfid()))
					.add(Expression.eq("selectedFYear", incomeTaxMasterBean.getSelectedFYear())).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String getConfigRowDetails(int savingsTypeId, String sfid, String finYear) throws Exception 
	{
		Session session = null;
		String message = CPSConstants.YES;
		IncomeTaxConfigDTO incomeTaxConfigDTO = null;
		try 
		{
			session = hibernateUtils.getSession();
			incomeTaxConfigDTO = (IncomeTaxConfigDTO) session.createCriteria(IncomeTaxConfigDTO.class).add(Expression.eq("sfID", sfid))
					.add(Expression.eq("savingsTypeId", savingsTypeId)).add(Expression.eq("selectedFYear", finYear)).uniqueResult();
			if (CPSUtils.isNullOrEmpty(incomeTaxConfigDTO))
				message = CPSConstants.NO;
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("deprecation")
	public boolean checkRunMonth(Date runMonth, Date fDate, Date tDate) throws Exception {
		boolean check = false;
		try {
			if (runMonth.getYear() >= fDate.getYear() && runMonth.getYear() <= tDate.getYear()) {
				if (runMonth.getMonth() >= fDate.getMonth() && runMonth.getMonth() <= tDate.getMonth()) {
					check = true;
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return check;
	}

	@SuppressWarnings("unchecked")
	public List<IncomeTaxArrearsDTO> getArrearsListDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		List<IncomeTaxArrearsDTO> da1List = null;
		List<IncomeTaxArrearsDTO> da2List = null;
		List<IncomeTaxArrearsDTO> arrearsList = null;
		PayFinYearDTO finYearDTO = null;
		Session session = null;
		int totDa1Diff = 0;
		int totTpt1Diff = 0;
		int totCpf1 = 0;
		int totItRec1 = 0;

		int totDa2Diff = 0;
		int totTpt2Diff = 0;
		int totCpf2 = 0;
		int totItRec2 = 0;
		try {
			session = hibernateUtils.getSession();
			finYearDTO = (PayFinYearDTO) session.get(PayFinYearDTO.class, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear()));
			String sfDate = String.valueOf(finYearDTO.getFyFrom());
			sfDate = "01-JAN-" + sfDate.substring(2, 4);
			String stDate = String.valueOf(finYearDTO.getFyTo() - 1);
			stDate = "01-DEC-" + stDate.substring(2, 4);
			arrearsList = new ArrayList<IncomeTaxArrearsDTO>();
			String query1 = "select arrears.DA_DIFF as daDiff, arrears.TPT_DIFF as tptDiff , "
					+ " arrears.CPF as cpf , arrears.DA_TYPE_FLAG as daTypeFlag ,arrears.IT_REC as itRec from pay_da_arrears_details "
					+ "arrears where DA_TYPE_FLAG=1 and month>='" + sfDate + "' and month<='" + stDate + "' and SFID='"
					+ incomeTaxMasterBean.getSearchSfid() + "'";
			da1List = (List<IncomeTaxArrearsDTO>) session.createSQLQuery(query1).addScalar("daDiff", Hibernate.INTEGER).addScalar("tptDiff",
					Hibernate.INTEGER).addScalar("cpf", Hibernate.INTEGER).addScalar("daTypeFlag", Hibernate.INTEGER).addScalar("itRec",
					Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(IncomeTaxArrearsDTO.class)).list();
			String query2 = "select arrears.DA_DIFF as daDiff, arrears.TPT_DIFF as tptDiff , "
					+ " arrears.CPF as cpf , arrears.DA_TYPE_FLAG as daTypeFlag ,arrears.IT_REC as itRec from pay_da_arrears_details "
					+ "arrears where month>='" + sfDate + "' and month<='" + stDate + "' and SFID='" + incomeTaxMasterBean.getSearchSfid()
					+ "' and DA_TYPE_FLAG=2";
			da2List = (List<IncomeTaxArrearsDTO>) session.createSQLQuery(query2).addScalar("daDiff", Hibernate.INTEGER).addScalar("tptDiff",
					Hibernate.INTEGER).addScalar("cpf", Hibernate.INTEGER).addScalar("daTypeFlag", Hibernate.INTEGER).addScalar("itRec",
					Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(IncomeTaxArrearsDTO.class)).list();
			if (da1List.size() > 0) {
				IncomeTaxArrearsDTO da1DTO = new IncomeTaxArrearsDTO();
				for (int i = 0; i < da1List.size(); i++) {
					IncomeTaxArrearsDTO incomeTaxArrearsDTO1 = da1List.get(i);
					totDa1Diff += incomeTaxArrearsDTO1.getDaDiff();
					totTpt1Diff += incomeTaxArrearsDTO1.getTptDiff();
					totCpf1 += incomeTaxArrearsDTO1.getCpf();
					totItRec1 += incomeTaxArrearsDTO1.getItRec();
				}
				da1DTO.setTotAmt((totDa1Diff - totCpf1) + totTpt1Diff);
				da1DTO.setTotCpfRec(totCpf1);
				da1DTO.setTotItRec(totItRec1);
				da1DTO.setName("DA1");
				arrearsList.add(da1DTO);
			}
			if (da2List.size() > 0) {
				IncomeTaxArrearsDTO da2DTO = new IncomeTaxArrearsDTO();
				for (int i = 0; i < da2List.size(); i++) {
					IncomeTaxArrearsDTO incomeTaxArrearsDTO2 = da2List.get(i);
					totDa2Diff += incomeTaxArrearsDTO2.getDaDiff();
					totTpt2Diff += incomeTaxArrearsDTO2.getTptDiff();
					totCpf2 += incomeTaxArrearsDTO2.getCpf();
					totItRec2 += incomeTaxArrearsDTO2.getItRec();
				}
				da2DTO.setTotAmt((totDa2Diff - totCpf2) + totTpt2Diff);
				da2DTO.setTotCpfRec(totCpf2);
				da2DTO.setTotItRec(totItRec2);
				da2DTO.setName("DA2");
				arrearsList.add(da2DTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return arrearsList;
	}

	public String submitSectionDetails(IncomeTaxSectionDTO incomeTaxSectionDTO) throws Exception
	{
		String message = "";
		Session session = null; //Transaction tx = null;
		try
		{
			session = hibernateUtils.getSession(); //tx = session.beginTransaction();
			if(incomeTaxSectionDTO.getId() == 0)
				message = CPSConstants.SUCCESS;
			else
				message = CPSConstants.UPDATE;
			session.saveOrUpdate(incomeTaxSectionDTO);
			session.flush(); //tx.commit();
			
		} catch (Exception e) { // tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	//to get the all the section available in the section.
	public List<IncomeTaxSectionDTO> getITSectionList() throws Exception {
		List<IncomeTaxSectionDTO> keyList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			keyList = session.createCriteria(IncomeTaxSectionDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("secOrderNo")).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return keyList;
	}

	public String deleteSectionDetails(int id) throws Exception 
	{
		Session session = null;
		String message = "";
		try
		{
			session = hibernateUtils.getSession();
			session.createSQLQuery("update  PAY_IT_SECTION_MASTER set status = 0, last_modified_date = sysdate where id = ?").setInteger(0, id).executeUpdate();
			session.flush();
			message = CPSConstants.DELETE;
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	public int getTrasportAllowance() throws Exception {
		Session session = null;
		int tra = 0;
		try {
			session = hibernateUtils.getSession();
			tra = (Integer.parseInt((String) session.createSQLQuery("select value from CONFIGURATION_DETAILS where name='IT_TRANSPORT_ALLOWANCE'")
					.uniqueResult()));
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return tra;
	}

	public String submitRentDetails(List<IncomeTaxRentDTO> rentList) throws Exception {
		String message = "";
		Session session = null;
		Transaction tx = null;
		DateFormat formatter=null;
		List<IncomeTaxRentDTO> dupList;
		int dup = 0;
		try {
			session = hibernateUtils.getSession();
			formatter = new SimpleDateFormat("dd-MMM-yy");
			//tx = session.beginTransaction();
			for (int i = 0; i < rentList.size(); i++) {
				IncomeTaxRentDTO rentDTO = rentList.get(i);
				if(CPSUtils.compareStrings("edit",rentDTO.getType())){
					String query = "select TO_CHAR(rent_from_date, 'dd-MON-YY') as rentFromDate ,TO_CHAR(rent_to_date, 'dd-MON-YY') as rentToDate , rent as rent,remarks as remarks , id as id ,"
					+ "sfid as sfid from pay_it_rent_details where sfid='" + rentDTO.getSfid()+ "' and FIN_YEAR_ID="
					+ rentDTO.getFinYearId()+ " and status=1 and rent_from_date='"+rentDTO.getRentFromDate()+"' and rent_to_date='"+rentDTO.getRentToDate()+"'and rent="+rentDTO.getRent();
					dupList = session.createSQLQuery(query).addScalar("remarks", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("sfid",
					Hibernate.STRING).addScalar("rentFromDate", Hibernate.STRING).addScalar("rentToDate", Hibernate.STRING).addScalar("rent", Hibernate.INTEGER).setResultTransformer(
					Transformers.aliasToBean(IncomeTaxRentDTO.class)).list();
				}else{
					String query = "select TO_CHAR(rent_from_date, 'dd-MON-YY') as rentFromDate ,TO_CHAR(rent_to_date, 'dd-MON-YY') as rentToDate  , rent as rent,remarks as remarks , id as id ,"
						+ "sfid as sfid from pay_it_rent_details where sfid='" + rentDTO.getSfid()+ "' and FIN_YEAR_ID="
						+ rentDTO.getFinYearId()+ " and status=1 ";
					dupList = session.createSQLQuery(query).addScalar("remarks", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("sfid",
						Hibernate.STRING).addScalar("rentFromDate", Hibernate.STRING).addScalar("rentToDate", Hibernate.STRING).addScalar("rent", Hibernate.INTEGER).setResultTransformer(
						Transformers.aliasToBean(IncomeTaxRentDTO.class)).list();
				}
                 if (dupList.size()==0)
                 {
                	 session.saveOrUpdate(rentDTO);
 					session.flush();
 					message = CPSConstants.SUCCESS;
                 }
                 else {
                	 Date nf=formatter.parse(rentDTO.getRentFromDate());
                	 Date nt=formatter.parse(rentDTO.getRentToDate());
                	 for(Object obj:dupList)
                	 {
                		 IncomeTaxRentDTO temp =(IncomeTaxRentDTO) obj;
                		 Date ot=formatter.parse(temp.getRentToDate());
                		 Date of=formatter.parse(temp.getRentFromDate());
                		 if((CPSUtils.compareTwoDatesUptoDate(nf,of)>=0&&CPSUtils.compareTwoDatesUptoDate(ot,nt)>=0)
                				 ||(CPSUtils.compareTwoDatesUptoDate(nf,of)>=0&&CPSUtils.compareTwoDatesUptoDate(ot,nf)>=0)
                			 ||(CPSUtils.compareTwoDatesUptoDate(nt,of)>=0&&CPSUtils.compareTwoDatesUptoDate(ot,nt)>=0))
                		 {
                			 message = CPSConstants.DUPLICATE;
         					break;
                		 }
                		
                		 
                	 }
                	 if(!CPSUtils.compareString(message, CPSConstants.DUPLICATE))
                	 {
                		 session.saveOrUpdate(rentDTO);
      					session.flush();
      					message = CPSConstants.SUCCESS;
                	 }
					
				}
			}

			//tx.commit();
			

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<IncomeTaxRentDTO> getITRentList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		Session session = null;
		List<IncomeTaxRentDTO> rentList = null;
		try {
			session = hibernateUtils.getSession();
			String query = "select TO_CHAR(rent_from_date, 'dd-MON-YYYY') as rentFromDate ,TO_CHAR(rent_to_date, 'dd-MON-YYYY') as rentToDate , rent as rent,remarks as remarks , id as id ,"
					+ "sfid as sfid from pay_it_rent_details where sfid='" + incomeTaxMasterBean.getSearchSfid() + "' and FIN_YEAR_ID="
					+ incomeTaxMasterBean.getSelectedFYear() + " and status=1  order by rent_from_date";
			rentList = session.createSQLQuery(query).addScalar("remarks", Hibernate.STRING).addScalar("id", Hibernate.INTEGER).addScalar("sfid",
					Hibernate.STRING).addScalar("rentFromDate", Hibernate.STRING).addScalar("rentToDate", Hibernate.STRING).addScalar("rent", Hibernate.INTEGER).setResultTransformer(
					Transformers.aliasToBean(IncomeTaxRentDTO.class)).list();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return rentList;
	}

	@SuppressWarnings("unchecked")
	public List<IncomeTaxDAStatusDTO> getArrearsStatusList() throws Exception {
		List<IncomeTaxDAStatusDTO> arrearsStatusList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			arrearsStatusList = session.createCriteria(IncomeTaxDAStatusDTO.class).add(Expression.eq("runStatus", 1)).list();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return arrearsStatusList;
	}

	public String deleteRentDetails(int id) throws Exception {
		String message = "";
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			session.createSQLQuery("update  PAY_IT_RENT_DETAILS set status=0,last_modified_date=sysdate where id=?").setInteger(0, id)
					.executeUpdate();
			message = CPSConstants.DELETE;

		} catch (Exception e) {
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
			session = hibernateUtils.getSession();
			Query qry = session.createSQLQuery("select value from configuration_details where name=?");
			qry.setString(0, tableName);
			masterObjList = qry.list();
			id = Integer.parseInt(masterObjList.get(0).toString()) + 1;

			Connection con = session.connection();
			String sql = "update configuration_details set value=? where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, tableName);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} 
		return id;
	}

	@SuppressWarnings( { "unchecked" })
	
	public String submitPlannedDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		String message="";
		Session session=null;
		List<PayBillUserDTO> empList = null;
		Date fromMonth=null;
		Date toMonth=null;
		try{
			session=hibernateUtils.getSession();
			//incomeTaxMasterBean.setRunId(getTableID("INCOMETAX_RUN_ID"));
			incomeTaxMasterBean.setRunId(Integer.parseInt(commonDataDAO.getSequenceValue(CPSConstants.INCOMETAXRUNID)));
			
			List<IncomeTaxRunStatusDTO> statusList=	session.createCriteria(IncomeTaxRunStatusDTO.class).add(Expression.eq("finYearId", Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()))).add(Expression.eq("plannedStatus", 1)).list();
			
			if (statusList.size()>0) {
				message = CPSConstants.FAILED;
			}else{
				message=calculateIncomeTaxPlPrActl.submitITStatusDetails(incomeTaxMasterBean);
			}
	
			if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
			
				int remainingNoOfMonths=((BigDecimal)session.createSQLQuery(" Select 12-Count(Runmonth) From (Select Distinct Run_Month Runmonth From Paybill_Status_Details Where "+
                                        " Run_Month Between To_Date('01-MAR-'||(Select from_Year From Pay_IT_Fin_Year_Master where id=?)) and to_Date('01-MAR-'||(Select to_Year From  "+
                                        " Pay_IT_Fin_Year_Master where id=?)))").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).setInteger(1, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
				
				/*int remainingNoOfMonths=((BigDecimal)session.createSQLQuery(" Select Months_Between((Select To_Date('01-MAR-'||To_Year) From "+
                        " Pay_IT_Fin_Year_Master where id=?),(Select To_Date(Run_Month) From "+
						" (Select Run_Month,Rownum  Rowno From (Select Run_Month From Paybill_Status_Details  "+
						" Order By To_Date(Run_Month) Desc) Tab) Where Rowno=1)) Months From Dual").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();*/
	        
				Date startRecoveryMonth=(Date)session.createSQLQuery(" select add_months((select Runmonth from (Select Distinct Run_Month Runmonth From Paybill_Status_Details Where "+
                                        " Run_Month Between To_Date('01-MAR-'||(Select from_Year From Pay_It_Fin_Year_Master Where Id=?)) And To_Date('01-APR-'||(Select To_Year From  "+
                                        " Pay_IT_Fin_Year_Master where id=?)) order by run_month desc) where rownum=1),1) recDate from dual").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).setInteger(1, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
	
				fromMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select from_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
				toMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select to_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
				
				empList =calculateIncomeTaxPlPrActl.getEmployeeList(fromMonth,toMonth);
		  
		         for(int i=0;i<empList.size();i++){
		        	PayBillUserDTO payBillUserDTO=empList.get(i);
			        String sfid=payBillUserDTO.getSfID();
			        System.out.println(sfid);
			        
			        IncomeTaxRunDTO incomeTaxRunDTO =new IncomeTaxRunDTO();
			        incomeTaxRunDTO.setSfid(sfid);
			        incomeTaxRunDTO.setRunType(CPSConstants.ITPLANNEDFLAG);
			        incomeTaxRunDTO.setRunId(incomeTaxMasterBean.getRunId());
			        incomeTaxRunDTO.setStatus(1);
			        incomeTaxRunDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			        incomeTaxRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			        incomeTaxRunDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			        incomeTaxRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			        incomeTaxRunDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
			        incomeTaxMasterBean.setIncomeTaxRunDTO(incomeTaxRunDTO);
			        Calendar cal =Calendar.getInstance();
					cal.set(calculateIncomeTaxPlPrActl.getStartingMonthOfFinYear(incomeTaxMasterBean.getSelectedFYear()), 03, 01);
					
					//kumari test
					//incomeTaxMasterBean.setPayBillList(calculateIncomeTaxPlPrActl.getProjectionPayBillList1(sfid,fromMonth,toMonth));
					incomeTaxMasterBean.setPayItPayBillList(calculateIncomeTaxPlPrActl.getPayItPayBillList(sfid,fromMonth,toMonth));
					
		        	incomeTaxMasterBean=calculateIncomeTaxPlPrActl.submitITPayBillDetails(incomeTaxMasterBean);
		        	
		        	//kumari add function calculate_arrears()--CALCULATE DA1,DA2,PRO,FPA,AI
		        	
		        	incomeTaxRunDTO.setDa1(calculateIncomeTaxPlPrActl.getArrearsDetails("DA",1, sfid, incomeTaxRunDTO.getFinYearId()));
		        	incomeTaxRunDTO.setDa2(calculateIncomeTaxPlPrActl.getArrearsDetails("DA",2, sfid, incomeTaxRunDTO.getFinYearId()));
		        	incomeTaxRunDTO.setPayFixationArrears(calculateIncomeTaxPlPrActl.getArrearsDetails("PRO", 0, sfid, incomeTaxRunDTO.getFinYearId()));
		        	incomeTaxRunDTO.setFpaArrears(calculateIncomeTaxPlPrActl.getArrearsDetails("FPA", 0, sfid, incomeTaxRunDTO.getFinYearId()));
		        	incomeTaxRunDTO.setAnnIncrArrears(calculateIncomeTaxPlPrActl.getArrearsDetails("AI", 0, sfid, incomeTaxRunDTO.getFinYearId()));
		        	/*String allArrears=calculateIncomeTaxPlPrActl.getArrearsDetails(sfid,incomeTaxRunDTO.getFinYearId());
		        	incomeTaxRunDTO.setDa1(Integer.parseInt(allArrears.split("#")[0]));
		        	incomeTaxRunDTO.setDa2(Integer.parseInt(allArrears.split("#")[1]));
		        	incomeTaxRunDTO.setPayFixationArrears(Integer.parseInt(allArrears.split("#")[2]));
		        	incomeTaxRunDTO.setFpaArrears(Integer.parseInt(allArrears.split("#")[3]));
		        	incomeTaxRunDTO.setAnnIncrArrears(Integer.parseInt(allArrears.split("#")[4]));*/
		        	
		        	incomeTaxRunDTO.setArrearsPaid(incomeTaxRunDTO.getDa1()+incomeTaxRunDTO.getDa2()+incomeTaxRunDTO.getPayFixationArrears()+incomeTaxRunDTO.getFpaArrears()+incomeTaxRunDTO.getAnnIncrArrears());
					
		        	int totalIncome=incomeTaxRunDTO.getTotSal()+incomeTaxRunDTO.getArrearsPaid();
					int grandTotal=totalIncome-(incomeTaxRunDTO.getLessHraEolHpl());
					int finalTotal=grandTotal+incomeTaxRunDTO.getGovtSubs();//1)Pay And Allowances
					
					//2) Exemptions
					
					incomeTaxRunDTO.setTotRentPaid(calculateIncomeTaxPlPrActl.getRentPaid(incomeTaxRunDTO.getSfid(), Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())));
					
					incomeTaxRunDTO.setHraExempted(calculateIncomeTaxPlPrActl.calculateHRAExempted(incomeTaxRunDTO.getTotHra(), incomeTaxRunDTO.getTotRentPaid(),0,0));
					
					incomeTaxRunDTO.setPrUpallw(calculateIncomeTaxPlPrActl.getProfessionalUpdateAllowance(payBillUserDTO.getgPay(), Integer.parseInt(payBillUserDTO.getPayDesignationId())));
					//incomeTaxRunDTO.setPrUpallw(calculateIncomeTaxPlPrActl.getProfessionalUpdateAllowance(incomeTaxRunDTO.getSfid())); 
					
					int exemptions=incomeTaxRunDTO.getHraExempted()+incomeTaxRunDTO.getTraAllw()+incomeTaxRunDTO.getPrUpallw();
					
					int payAndAllowancesAfterExemptions=finalTotal-exemptions;
					
					incomeTaxRunDTO.setTotExemptions(exemptions);
					
					//3) Deductions
					
					int totalDeductions=calculateIncomeTaxPlPrActl.submitPlannedExemptionsDetails(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotCghs(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotPTax());
					
					incomeTaxRunDTO.setTotalDeductions(totalDeductions);
					
					int payAndAllowancesAfterDeductions=payAndAllowancesAfterExemptions-totalDeductions;
					
					int totalIncomeFromOtherSources=calculateIncomeTaxPlPrActl.submitPlannedTotalIncomeFromOtherSources(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID());
					 
					incomeTaxRunDTO.setTotOtherIncome(totalIncomeFromOtherSources);
					
					int totalIncomeAfterOtherIncome=payAndAllowancesAfterDeductions+totalIncomeFromOtherSources;
					
					int totalSavings=calculateIncomeTaxPlPrActl.submitPlannedTotalSavings(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxRunDTO.getTotCegis(), incomeTaxRunDTO.getTotPli(), 0, incomeTaxRunDTO.getGovtSubs(), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getRunId());
					
					totalSavings+=incomeTaxRunDTO.getGovtSubs();
					
					incomeTaxRunDTO.setTotSavings(totalSavings);
					
					int taxableIncome=totalIncomeAfterOtherIncome-totalSavings;
					
					incomeTaxRunDTO.setTaxableIncome(taxableIncome);
					
					long roundedOff=Math.round((taxableIncome/10))*10;
					
					incomeTaxRunDTO.setRoundedOff(roundedOff);
					
					int totalIncomeTax=calculateIncomeTax(roundedOff, incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getSelectedFYear());
					
					incomeTaxRunDTO.setTotIncomeTax(totalIncomeTax);
					
					int educationCess=Math.round(totalIncomeTax*103/100f);
					
					incomeTaxRunDTO.setAddEduCess(educationCess);
					
					int recoveredIncomeTax=incomeTaxRunDTO.getTaxRecovered();
					
					incomeTaxRunDTO.setTaxToBeRecovered(educationCess-recoveredIncomeTax);
					
					incomeTaxRunDTO.setTaxPerMonth(incomeTaxRunDTO.getTaxToBeRecovered()/remainingNoOfMonths);
					
					incomeTaxRunDTO.setEffMonth(startRecoveryMonth);
				
					
					session.saveOrUpdate(incomeTaxRunDTO);
			      }
		         hibernateUtils.closeSession();
		         
		      }
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
    @SuppressWarnings("unchecked")
	public String submitProjectedDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		String message="";
		Session session=null;
		List<PayBillUserDTO> empList = null;
		Date fromMonth=null;
		Date toMonth=null;
		List<PayBillDTO> payList=null;
		try{
			session=hibernateUtils.getSession();
			//incomeTaxMasterBean.setRunId(getTableID("INCOMETAX_RUN_ID"));
			incomeTaxMasterBean.setRunId(Integer.parseInt(commonDataDAO.getSequenceValue(CPSConstants.INCOMETAXRUNID)));
			
			List<IncomeTaxRunStatusDTO> statusList=	session.createCriteria(IncomeTaxRunStatusDTO.class).add(Expression.eq("finYearId", Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()))).add(Expression.eq("ProjectedStatus", 1)).list();
			
			if (statusList.size()>0) {
				message = CPSConstants.FAILED;
			}else{
				message=calculateIncomeTaxPlPrActl.submitITStatusDetails(incomeTaxMasterBean);
			}
	
			if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
			
				int remainingNoOfMonths=((BigDecimal)session.createSQLQuery(" Select Months_Between((Select To_Date('01-MAR-'||To_Year) From "+
                        " Pay_IT_Fin_Year_Master where id=?),(Select To_Date(Run_Month) From "+
						" (Select Run_Month,Rownum  Rowno From (Select Run_Month From Paybill_Status_Details  "+
						" Order By To_Date(Run_Month) Desc) Tab) Where Rowno=1)) Months From Dual").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
		        
				Date startRecoveryMonth=(Date)session.createSQLQuery(" select add_months((Select To_Date(Run_Month) From (Select Run_Month,Rownum "+
                        " Rowno From (Select Run_Month From Paybill_Status_Details "+
                        " order by to_date(run_month) desc) tab) where rowno=1),1) from dual").uniqueResult();
	
				fromMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select from_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
				toMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select to_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
				
				empList =calculateIncomeTaxPlPrActl.getEmployeeList(fromMonth,toMonth);
		  
		         for(int i=0;i<empList.size();i++){
		        	 
		        	 PayBillUserDTO payBillUserDTO=empList.get(i);
				        String sfid=payBillUserDTO.getSfID();
				        System.out.println(sfid);
			        
			        IncomeTaxRunDTO incomeTaxRunDTO =new IncomeTaxRunDTO();
			        incomeTaxRunDTO.setSfid(sfid);
			        incomeTaxRunDTO.setRunType(CPSConstants.ITPROJECTEDFLAG);
			        incomeTaxRunDTO.setRunId(incomeTaxMasterBean.getRunId());
			        incomeTaxRunDTO.setStatus(1);
			        incomeTaxRunDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			        incomeTaxRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			        incomeTaxRunDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			        incomeTaxRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			        incomeTaxRunDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
			        incomeTaxMasterBean.setIncomeTaxRunDTO(incomeTaxRunDTO);
			        Calendar cal =Calendar.getInstance();
					cal.set(calculateIncomeTaxPlPrActl.getStartingMonthOfFinYear(incomeTaxMasterBean.getSelectedFYear()), 03, 01);
					incomeTaxMasterBean.setPayBillList(calculateIncomeTaxPlPrActl.getProjectionPayBillList1(sfid,fromMonth,toMonth));		        	
					incomeTaxMasterBean=calculateIncomeTaxPlPrActl.submitITPayBillDetails(incomeTaxMasterBean);
					incomeTaxRunDTO.setArrearsPaid(0);
					int totalIncome=incomeTaxRunDTO.getTotSal()+incomeTaxRunDTO.getArrearsPaid();
					int grandTotal=totalIncome-(incomeTaxRunDTO.getLessHraEolHpl());
					int finalTotal=grandTotal+incomeTaxRunDTO.getGovtSubs();//1)Pay And Allowances
					
					//2) Exemptions
					
                    incomeTaxRunDTO.setTotRentPaid(calculateIncomeTaxPlPrActl.getRentPaid(incomeTaxRunDTO.getSfid(), Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())));
					
					incomeTaxRunDTO.setHraExempted(calculateIncomeTaxPlPrActl.calculateHRAExempted(incomeTaxRunDTO.getTotHra(), incomeTaxRunDTO.getTotRentPaid(),0,0));
					
					incomeTaxRunDTO.setPrUpallw(calculateIncomeTaxPlPrActl.getProfessionalUpdateAllowance(payBillUserDTO.getgPay(), Integer.parseInt(payBillUserDTO.getPayDesignationId())));
					 
					int exemptions=incomeTaxRunDTO.getHraExempted()+incomeTaxRunDTO.getTraAllw()+incomeTaxRunDTO.getPrUpallw();
					int payAndAllowancesAfterExemptions=finalTotal-exemptions;
					
					incomeTaxRunDTO.setTotExemptions(exemptions);
					
					int totalDeductions=calculateIncomeTaxPlPrActl.submitPlannedExemptionsDetails1(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotCghs(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotPTax());
					
					incomeTaxRunDTO.setTotalDeductions(totalDeductions);
					
					int payAndAllowancesAfterDeductions=payAndAllowancesAfterExemptions-(incomeTaxRunDTO.getTotPTax()+totalDeductions);
					
					int totalIncomeFromOtherSources=calculateIncomeTaxPlPrActl.submitPlannedTotalIncomeFromOtherSources1(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID());
					 
					incomeTaxRunDTO.setTotOtherIncome(totalIncomeFromOtherSources);
					
					int totalIncomeAfterOtherIncome=payAndAllowancesAfterDeductions+totalIncomeFromOtherSources;
					
					int totalSavings=calculateIncomeTaxPlPrActl.submitPlannedTotalSavings1(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxRunDTO.getTotCegis(), incomeTaxRunDTO.getTotPli(), 0, incomeTaxRunDTO.getGovtSubs(), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getRunId());
					
					totalSavings+=incomeTaxRunDTO.getGovtSubs();
					
					incomeTaxRunDTO.setTotSavings(totalSavings);
					
					int taxableIncome=totalIncomeAfterOtherIncome-totalSavings;
					
					incomeTaxRunDTO.setTaxableIncome(taxableIncome);
					
					long roundedOff=Math.round((taxableIncome/10))*10;
					
					incomeTaxRunDTO.setRoundedOff(roundedOff);
					
					int totalIncomeTax=calculateIncomeTax(roundedOff, incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getSelectedFYear());
					
					incomeTaxRunDTO.setTotIncomeTax(totalIncomeTax);
					
					int educationCess=totalIncomeTax+((int)(0.03*totalIncomeTax));
					
					incomeTaxRunDTO.setAddEduCess(educationCess);
					
					int recoveredIncomeTax=incomeTaxRunDTO.getTaxRecovered();
					
					incomeTaxRunDTO.setTaxToBeRecovered(educationCess-recoveredIncomeTax);
					
					incomeTaxRunDTO.setTaxPerMonth(incomeTaxRunDTO.getTaxToBeRecovered()/remainingNoOfMonths);
					
					incomeTaxRunDTO.setEffMonth(startRecoveryMonth);
				
					
					session.saveOrUpdate(incomeTaxRunDTO);
			      }
		     }
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
    @SuppressWarnings( {"unchecked"})
	public String submitActualDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		String message="";
		Session session=null;
		List<PayBillUserDTO> empList = null;
		Date fromMonth=null;
		Date toMonth=null;
		try{
			session=hibernateUtils.getSession();
			//incomeTaxMasterBean.setRunId(getTableID("INCOMETAX_RUN_ID"));
			incomeTaxMasterBean.setRunId(Integer.parseInt(commonDataDAO.getSequenceValue(CPSConstants.INCOMETAXRUNID)));
			
			List<IncomeTaxRunStatusDTO> statusList=	session.createCriteria(IncomeTaxRunStatusDTO.class).add(Expression.eq("finYearId", Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()))).add(Expression.eq("actualStatus", 1)).list();
			
			if (statusList.size()>0) {
				message = CPSConstants.FAILED;
			}else{
				message=calculateIncomeTaxPlPrActl.submitITStatusDetails(incomeTaxMasterBean);
			}
	
			if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
			
				int remainingNoOfMonths=((BigDecimal)session.createSQLQuery(" Select Months_Between((Select To_Date('01-MAR-'||To_Year) From "+
                        " Pay_IT_Fin_Year_Master where id=?),(Select To_Date(Run_Month) From "+
						" (Select Run_Month,Rownum  Rowno From (Select Run_Month From Paybill_Status_Details  "+
						" Order By To_Date(Run_Month) Desc) Tab) Where Rowno=1)) Months From Dual").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
		        
				Date startRecoveryMonth=(Date)session.createSQLQuery(" select add_months((Select To_Date(Run_Month) From (Select Run_Month,Rownum "+
                        " Rowno From (Select Run_Month From Paybill_Status_Details "+
                        " order by to_date(run_month) desc) tab) where rowno=1),1) from dual").uniqueResult();
	
				fromMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select from_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
				toMonth=(Date)session.createSQLQuery("select To_Date('01-APR-'||(Select to_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
				
				empList =calculateIncomeTaxPlPrActl.getEmployeeList(fromMonth,toMonth);
		  
		         for(int i=0;i<empList.size();i++){
		        	 
		        	 PayBillUserDTO payBillUserDTO=empList.get(i);
				     String sfid=payBillUserDTO.getSfID();
				     System.out.println(sfid);
			        
			        IncomeTaxRunDTO incomeTaxRunDTO =new IncomeTaxRunDTO();
			        incomeTaxRunDTO.setSfid(sfid);
			        incomeTaxRunDTO.setRunType(CPSConstants.ITACTUALFLAG);
			        incomeTaxRunDTO.setRunId(incomeTaxMasterBean.getRunId());
			        incomeTaxRunDTO.setStatus(1);
			        incomeTaxRunDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			        incomeTaxRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			        incomeTaxRunDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			        incomeTaxRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			        incomeTaxRunDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
			        incomeTaxMasterBean.setIncomeTaxRunDTO(incomeTaxRunDTO);
			        Calendar cal =Calendar.getInstance();
					cal.set(calculateIncomeTaxPlPrActl.getStartingMonthOfFinYear(incomeTaxMasterBean.getSelectedFYear()), 03, 01);
					incomeTaxMasterBean.setPayBillList(calculateIncomeTaxPlPrActl.getProjectionPayBillList(sfid,fromMonth,toMonth));
		        	incomeTaxMasterBean=calculateIncomeTaxPlPrActl.submitITPayBillDetails(incomeTaxMasterBean);
					incomeTaxRunDTO.setArrearsPaid(0);
					int totalIncome=incomeTaxRunDTO.getTotSal()+incomeTaxRunDTO.getArrearsPaid();
					int grandTotal=totalIncome-(incomeTaxRunDTO.getLessHraEolHpl());
					int finalTotal=grandTotal+incomeTaxRunDTO.getGovtSubs();//1)Pay And Allowances
					
					//2) Exemptions
					
                    incomeTaxRunDTO.setTotRentPaid(calculateIncomeTaxPlPrActl.getRentPaid(incomeTaxRunDTO.getSfid(), Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())));
					
					incomeTaxRunDTO.setHraExempted(calculateIncomeTaxPlPrActl.calculateHRAExempted(incomeTaxRunDTO.getTotHra(), incomeTaxRunDTO.getTotRentPaid(),0,0));
					
					incomeTaxRunDTO.setPrUpallw(calculateIncomeTaxPlPrActl.getProfessionalUpdateAllowance(payBillUserDTO.getgPay(), Integer.parseInt(payBillUserDTO.getPayDesignationId())));
					 
					int exemptions=incomeTaxRunDTO.getHraExempted()+incomeTaxRunDTO.getTraAllw()+incomeTaxRunDTO.getPrUpallw();
					int payAndAllowancesAfterExemptions=finalTotal-exemptions;
					
					incomeTaxRunDTO.setTotExemptions(exemptions);
					
					int totalDeductions=calculateIncomeTaxPlPrActl.submitPlannedExemptionsDetails(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotCghs(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotPTax());
					
					incomeTaxRunDTO.setTotalDeductions(totalDeductions);
					
					int payAndAllowancesAfterDeductions=payAndAllowancesAfterExemptions-(incomeTaxRunDTO.getTotPTax()+totalDeductions);
					
					int totalIncomeFromOtherSources=calculateIncomeTaxPlPrActl.submitPlannedTotalIncomeFromOtherSources(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID());
					 
					incomeTaxRunDTO.setTotOtherIncome(totalIncomeFromOtherSources);
					
					int totalIncomeAfterOtherIncome=payAndAllowancesAfterDeductions+totalIncomeFromOtherSources;
					
					int totalSavings=calculateIncomeTaxPlPrActl.submitPlannedTotalSavings(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxRunDTO.getTotCegis(), incomeTaxRunDTO.getTotPli(), 0, incomeTaxRunDTO.getGovtSubs(), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getRunId());
					
					totalSavings+=incomeTaxRunDTO.getGovtSubs();
					
					incomeTaxRunDTO.setTotSavings(totalSavings);
					
					int taxableIncome=totalIncomeAfterOtherIncome-totalSavings;
					
					incomeTaxRunDTO.setTaxableIncome(taxableIncome);
					
					long roundedOff=Math.round((taxableIncome/10))*10;
					
					incomeTaxRunDTO.setRoundedOff(roundedOff);
					
					int totalIncomeTax=calculateIncomeTax(roundedOff, incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getSelectedFYear());
					
					incomeTaxRunDTO.setTotIncomeTax(totalIncomeTax);
					
					int educationCess=totalIncomeTax+((int)0.03*totalIncomeTax);
					
					incomeTaxRunDTO.setAddEduCess(educationCess);
					
					int recoveredIncomeTax=incomeTaxRunDTO.getTaxRecovered();
					
					incomeTaxRunDTO.setTaxToBeRecovered(educationCess-recoveredIncomeTax);
					
					incomeTaxRunDTO.setTaxPerMonth(incomeTaxRunDTO.getTaxToBeRecovered()/remainingNoOfMonths);
					
					incomeTaxRunDTO.setEffMonth(startRecoveryMonth);
				
					
					session.saveOrUpdate(incomeTaxRunDTO);
			      }
		     }
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	// CALUCULATING INCOME TAX BASED ON TAXABLE INCOME

	@SuppressWarnings({ "unchecked" })
	public int calculateIncomeTax(long totIncome, String sfid, String finYearId) throws Exception {
		int incomeTax = 0;
		Session session = null;
		List<IncomeTaxStageDTO> slabList = null;
		int lastStageId = 0;
		int srMaxAge=0;
		String taxFlag="";
		try {
			session = hibernateUtils.getSession();
			EmployeeBean  empBean=(EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", sfid)).uniqueResult();
			//EmployeeBean empBean=(EmployeeBean)session.createQuery("select gender as gender from EmployeeBean where userSfid=?").setString(0, sfid).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).uniqueResult();
			srMaxAge = ((BigDecimal) session.createSQLQuery("SELECT (conf.value) -(ROUND((SYSDATE-emp.DOB)/365)) FROM EMP_MASTER emp,configuration_details conf WHERE SFID='"+sfid+"' and CONF.NAME='AGE_LIMIT_FOR_SRCITIZEN'")
					.uniqueResult()).intValue();
			if(srMaxAge<=0)
				taxFlag="Sr";
			else
				taxFlag=empBean.getGender();
			
			slabList = session.createCriteria(IncomeTaxStageDTO.class).add(
					Expression.eq("taxableFlag", taxFlag)).add(Expression.eq("selectedFYear", finYearId)).addOrder(Order.asc("stageId")).list();
			for (int i = 0; i < slabList.size(); i++) {
				IncomeTaxStageDTO stageDTO = slabList.get(i);
				if (stageDTO.getFrom() <= totIncome && stageDTO.getTo() >= totIncome) {
					lastStageId = stageDTO.getStageId();
					break;
				}
			}
			for (int j = 0; j < slabList.size(); j++) {
				IncomeTaxStageDTO stageDTO1 = slabList.get(j);
				if (stageDTO1.getStageId() < lastStageId) {
					incomeTax = incomeTax+(int)((stageDTO1.getTo()-stageDTO1.getFrom())*(stageDTO1.getTax()/100.0));
				}else if(stageDTO1.getStageId()==lastStageId){
					incomeTax = incomeTax+(int)(((int)totIncome-stageDTO1.getFrom())*(stageDTO1.getTax()/100.0));
				}

			}
		} catch (Exception e) {
			throw e;
		} 
		return incomeTax;
	}
	
	public PayBillDTO getPayMonthDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
		PayBillDTO payBillDTO=null;
		Session session=null;
		int runId;
		try{
			session = hibernateUtils.getSession();
			//for checking edit employee or not //kumari
			//IncomeTaxPayBillDTO itaxPayBillDTO=(IncomeTaxPayBillDTO)session.createCriteria(IncomeTaxPayBillDTO.class).add(Expression.eq("sfid", incomeTaxMasterBean.getSearchSfid())).add(Expression.eq("month", "01-"+incomeTaxMasterBean.getPayMonth())).add(Expression.eq("runType", "edit")).uniqueResult();
			IncomeTaxPayBillDTO itaxPayBillDTO=(IncomeTaxPayBillDTO)session.createQuery("FROM IncomeTaxPayBillDTO WHERE sfid='"+incomeTaxMasterBean.getSearchSfid()+"' AND month='01-"+incomeTaxMasterBean.getPayMonth()+"' AND runType='edit'").uniqueResult();
			if(CPSUtils.isNullOrEmpty(itaxPayBillDTO)){
				payBillDTO=(PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("runMonth", "01-"+incomeTaxMasterBean.getPayMonth())).add(Expression.in("status",new Integer[]{0,51,52,60})).add(Expression.eq("sfid", incomeTaxMasterBean.getSearchSfid())).uniqueResult();
				if(CPSUtils.isNullOrEmpty(payBillDTO)){
					runId = (Integer) session.createCriteria(PayBillStatusDTO.class).setProjection(Projections.max("runID")).uniqueResult();
					payBillDTO=(PayBillDTO) session.createCriteria(PayBillDTO.class).add(Expression.eq("runId",runId)).add(Expression.not(Expression.eq("status", 50))).add(Expression.eq("sfid", incomeTaxMasterBean.getSearchSfid())).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(payBillDTO))
					payBillDTO.setIncomeTax(0);
				}
			}else{
				payBillDTO = new PayBillDTO();
				BeanUtils.copyProperties(payBillDTO, itaxPayBillDTO);
				payBillDTO.setFpa(itaxPayBillDTO.getFpay());
				payBillDTO.setCrMisc(itaxPayBillDTO.getCredMisc());
				payBillDTO.setIncomeTax(itaxPayBillDTO.getiTax());
				payBillDTO.setCess(0);
				payBillDTO.setSecondaryCess(0);
				payBillDTO.setOtBill(itaxPayBillDTO.getETA());
				payBillDTO.setDrMisc1(itaxPayBillDTO.getDebMisc());
				payBillDTO.setDrMisc2(0);
				payBillDTO.setItRec(itaxPayBillDTO.getIncomeTaxRec());
				payBillDTO.setLastModifiedBy(itaxPayBillDTO.getModifiedBy());
				payBillDTO.setLastModifiedTime(itaxPayBillDTO.getModifiedDate());
				
			/*	payBillDTO.setBasicPay(itaxPayBillDTO.getBasicPay());
				payBillDTO.setGradePay(itaxPayBillDTO.getGradePay());
				payBillDTO.setSpecialPay(itaxPayBillDTO.getSpecialPay());
				payBillDTO.setCghs(itaxPayBillDTO.getCghs());
				payBillDTO.setFpa(itaxPayBillDTO.getFpay());
				payBillDTO.setDa(itaxPayBillDTO.getDa());
				payBillDTO.setHra(itaxPayBillDTO.getHra());
				payBillDTO.setCcs(itaxPayBillDTO.getCcs());
				payBillDTO.setTpt(itaxPayBillDTO.getTpt());
				payBillDTO.setCrMisc(itaxPayBillDTO.getCredMisc());
				//payBillDTO.set
				payBillDTO.setGpf(itaxPayBillDTO.getGpf());
				payBillDTO.setCegis(itaxPayBillDTO.getCegis());
				payBillDTO.setProfTax(itaxPayBillDTO.getProfTax());
				payBillDTO.setIncomeTax(itaxPayBillDTO.getiTax());
				payBillDTO.setCess(0);
				payBillDTO.setSecondaryCess(0);
				payBillDTO.setHbaLoan(itaxPayBillDTO.getHbaLoan());
				payBillDTO.setPli(itaxPayBillDTO.getPli());
				payBillDTO.setLic(itaxPayBillDTO.getLic());
				payBillDTO.setOtBill(itaxPayBillDTO.getETA());
				payBillDTO.setEol(itaxPayBillDTO.getEol());
				payBillDTO.setHdfc(itaxPayBillDTO.getHdfc());
				payBillDTO.setGic(itaxPayBillDTO.getGic());
				payBillDTO.setCanfin(itaxPayBillDTO.getCanfin());
				payBillDTO.setDrMisc1(itaxPayBillDTO.getDebMisc());
				payBillDTO.setDrMisc2(0);
				
				payBillDTO.setVariableIncr(itaxPayBillDTO.getVariableIncr());
				payBillDTO.setTwoAddlIncr(itaxPayBillDTO.getTwoAddlIncr());
				payBillDTO.setTotalCredits(itaxPayBillDTO.getTotalCredits());
				payBillDTO.setHdGicCfin(itaxPayBillDTO.getHdGicCfin());
				payBillDTO.setItRec(itaxPayBillDTO.getIncomeTaxRec());
				payBillDTO.setLastModifiedBy(itaxPayBillDTO.getModifiedBy());
				payBillDTO.setLastModifiedTime(itaxPayBillDTO.getModifiedDate());
				payBillDTO.setCess(itaxPayBillDTO.getCess());
				payBillDTO.setSecondaryCess(itaxPayBillDTO.getSecondaryCess());
				payBillDTO.setRemarks(itaxPayBillDTO.getRemarks());
				payBillDTO.setRunId(itaxPayBillDTO.getRunId());*/
				
			}
			  
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return payBillDTO;
	}
	

	@SuppressWarnings("unchecked")
	public List<PayScaleDesignationDTO> getDesignationList() throws Exception{
		Session session=null;
		List<PayScaleDesignationDTO> desingationList=null;
		try{
			session = hibernateUtils.getSession();
			desingationList = session.createCriteria(PayScaleDesignationDTO.class).add(Expression.eq("status", 1)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return desingationList;
	}
	
	public String submitPUADetails(PrUpdateAllwDTO prUpdateAllwDTO) throws Exception
	{
		Session session = null; // Transaction tx = null;
		String message = "";
		int dup = 0;
		try 
		{
			session = hibernateUtils.getSession(); //tx = session.beginTransaction();
			if(prUpdateAllwDTO.getId() == 0)
				dup = session.createCriteria(PrUpdateAllwDTO.class).add(Expression.eq("gradePay", prUpdateAllwDTO.getGradePay()))
					.add(Expression.eq("designationId", prUpdateAllwDTO.getDesignationId())).add(Expression.eq("status", 1)).list().size();
			if (dup != 0)
				message = CPSConstants.DUPLICATE;
			else 
			{
				session.saveOrUpdate(prUpdateAllwDTO);
				session.flush(); //tx.commit();
				session.clear();
				message = CPSConstants.SUCCESS;
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
	public List<PrUpdateAllwDTO> getPUAList() throws Exception{
		Session session=null;
		List<PrUpdateAllwDTO> puaList=null;
		try{
			session = hibernateUtils.getSession();
			puaList = session.createCriteria(PrUpdateAllwDTO.class).add(Expression.eq("status", 1)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
	}
		return puaList;
	}
	
	public String deletePUADetails(int id) throws Exception
	{
		Session session = null;
		String message = "";
		PrUpdateAllwDTO puaDTO = null;
		//Transaction tx=null;
		try
		{
			session = hibernateUtils.getSession();
			//tx = session.beginTransaction();
			puaDTO = (PrUpdateAllwDTO)session.get(PrUpdateAllwDTO.class, id);
			puaDTO.setStatus(0);
			session.saveOrUpdate(puaDTO);
			message = CPSConstants.DELETE;
			//tx.commit();
		}catch (Exception e) {
			//tx.rollback();
			message = CPSConstants.FAILED;
			throw e;
		}finally{
			//session.close();
		}
		return message;
	}


public int getPUAAmount(String sfid) throws Exception{
	Session session=null;
	PrUpdateAllwDTO puaDTO=null;
	int amount=0;
	try{
		session = hibernateUtils.getSession();
		puaDTO=(PrUpdateAllwDTO)session.createSQLQuery("Select Pua.Amount as amount From Pay_it_pua_master Pua Where Designation_id=" +
				"(Select Pay.Id From payband_designation_mapping Pay Where Pay.Designation_id=(Select Des.Id From "+
               "designation_master des where des.id=(select emp.designation_id from emp_master emp where emp.sfid='"+sfid+"'))) and status=1").addScalar("amount",Hibernate.INTEGER).setResultTransformer(
   					Transformers.aliasToBean(PrUpdateAllwDTO.class)).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(puaDTO))
			amount=puaDTO.getAmount();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
    return amount;
}
public String checkITRunStatus(int finyearid,String runType)throws Exception{
	String message="";
	Session session=null;
	IncomeTaxRunStatusDTO runStatusDTO=null;
	try{
		session = hibernateUtils.getSession();
		runStatusDTO=(IncomeTaxRunStatusDTO)session.createCriteria(IncomeTaxRunStatusDTO.class).add(Expression.eq("finYearId", finyearid)).add(Expression.eq("runType", runType)).uniqueResult();
		if(CPSUtils.isNullOrEmpty(runStatusDTO)){
			message=CPSConstants.YES;
		}
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
@SuppressWarnings({ "unchecked", "deprecation" })
public String submitDBMigrationDetails(String month)throws Exception{
	String message=CPSConstants.SUCCESS;
	Session session=null;
	List<EmployeeBean> usersList = null;
    //Transaction tx=null;
    String quarterResult=null;
	try{
		session = hibernateUtils.getSession();
		//tx = session.beginTransaction();
		String userQuery="select sfid as sfid from emp_master where status=1";
		//SELECT COUNT(*) FROM MIS_CREDITS WHERE F_DTMONTHYEAR=TO_CHAR(TO_DATE('01-JAN-11','dd-mon-yyyy')
		String qur="select TO_CHAR(TO_DATE('"+month+"','dd-mon-yyyy')) from dual";
		month=session.createSQLQuery(qur).uniqueResult().toString();
		usersList=session.createSQLQuery(userQuery).addScalar("sfid",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(EmployeeBean.class)).list();
		if(usersList.size()!=0){
		for(int i=0;i<usersList.size();i++){
			EmployeeBean usersDTO=usersList.get(i);
			PayBillCreditsMigrationDTO credDTO =(PayBillCreditsMigrationDTO)session.createCriteria(PayBillCreditsMigrationDTO.class).add(Expression.not(Expression.eq("F_NBASIC", "0"))).add(Expression.eq("F_CPERSNO", usersDTO.getSfid())).add(Expression.eq("F_DTMONTHYEAR", month)).uniqueResult();
			PayBillDebitsMigrationDTO  debDTO = (PayBillDebitsMigrationDTO)session.createCriteria(PayBillDebitsMigrationDTO.class).add(Expression.not(Expression.eq("F_NBASIC", "0"))).add(Expression.eq("F_CPERSNO", usersDTO.getSfid())).add(Expression.eq("F_DTMONTHYEAR", month)).uniqueResult();
			PayBillRecoveriesMigrationDTO recDTO=(PayBillRecoveriesMigrationDTO)session.createCriteria(PayBillRecoveriesMigrationDTO.class).add(Expression.not(Expression.eq("F_NBASIC", "0"))).add(Expression.eq("F_CPERSNO", usersDTO.getSfid())).add(Expression.eq("F_DTMONTHYEAR", month)).uniqueResult();
			PayDBMigrationCoreDTO coreDTO = new PayDBMigrationCoreDTO();//added for testing
			//PayDBMigrationCoreDTO coreDTO=(PayDBMigrationCoreDTO)session.createCriteria(PayDBMigrationCoreDTO.class).add(Expression.eq("F_CPERSNO", usersDTO.getSfid())).uniqueResult();
			PayBillEmpPaymentDeatilsDTO payDTO=(PayBillEmpPaymentDeatilsDTO) session.createCriteria(PayBillEmpPaymentDeatilsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sfID", usersDTO.getSfid())).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(credDTO)&&!CPSUtils.isNullOrEmpty(debDTO)&&!CPSUtils.isNullOrEmpty(recDTO)&&!CPSUtils.isNullOrEmpty(coreDTO)){
			if(CPSUtils.isNullOrEmpty(payDTO)){
			payDTO = new PayBillEmpPaymentDeatilsDTO();
			}
			payDTO.setSfID(credDTO.getF_CPERSNO());
			payDTO.setBasicPay(Integer.parseInt(credDTO.getF_NBASIC()));
			payDTO.setgPay(Integer.parseInt(credDTO.getF_NDPAY()));
			payDTO.setStatus(1);
			payDTO.setPayStopFlag("No");
			payDTO.setCategoryID(Integer.parseInt(credDTO.getF_CDESTYPE().trim()));
			if(Integer.parseInt(credDTO.getF_NCREDIT5())!=0){
				String query = "select coalesce( round(((select max(increment_value) from pay_variable_increment_master where grade_pay_from<=? and grade_pay_to>=? and status=1))),0) varIncr from dual";
         		int varIncr = Integer.parseInt(session.createSQLQuery(query).setFloat(0, Integer.parseInt(credDTO.getF_NDPAY())).setFloat(1, Integer.parseInt(credDTO.getF_NDPAY())).uniqueResult().toString());
				payDTO.setVarIncrPts(Integer.parseInt(credDTO.getF_NCREDIT5())/varIncr);
			}else{
				payDTO.setVarIncrPts(0);
			}
			if(Integer.parseInt(credDTO.getF_NTPT())!=0){
				payDTO.setTptFlag("Yes");
			}else{
				payDTO.setTptFlag("No");
			}
			payDTO.setTwoAddIncr(Integer.parseInt(credDTO.getF_NFPAY()));
			if(Integer.parseInt(credDTO.getF_NCREDIT3())!=0){
				String query="select coalesce( ((select grade_pay from PAY_FAMILY_PLANNING_MASTER where allowance_amount=? and status=1)),0) fpagrade from dual";
				int fpagrade = Integer.parseInt(session.createSQLQuery(query).setInteger(0, Integer.parseInt(credDTO.getF_NCREDIT3())).uniqueResult().toString());
				payDTO.setFpaGradePay(fpagrade);
				/*String query1="update emp_master set family_planning='Yes' where sfid='"+credDTO.getF_CPERSNO()+"'";
				session.createSQLQuery(query1).executeUpdate();
				*/
			
			}else{
				payDTO.setFpaGradePay(0);
				/*String query1="update emp_master set family_planning='No' where sfid='"+credDTO.getF_CPERSNO()+"'";
				session.createSQLQuery(query1).executeUpdate();*/
			}
			if(!CPSUtils.isNullOrEmpty(recDTO.getF_VC2BANKACCNO())){
				payDTO.setBankAccNo(recDTO.getF_VC2BANKACCNO());
				payDTO.setBankFlag("Yes");
				payDTO.setBankBranch("0");
			}else{
				payDTO.setBankAccNo("0");
				payDTO.setBankFlag("No");
				payDTO.setBankBranch("0");
			}
			if(!CPSUtils.isNullOrEmpty(debDTO.getF_NGPFC())){
				payDTO.setGpfSubAmt(Integer.parseInt(debDTO.getF_NGPFC()));
				payDTO.setGpfFlag("Yes");
			}else{
				payDTO.setGpfSubAmt(0);
				payDTO.setGpfFlag("No");
			}
			if(!CPSUtils.isNullOrEmpty(recDTO.getF_NCCSC())){
				payDTO.setCcsSubAmt(Integer.parseInt(recDTO.getF_NCCSC()));
				payDTO.setCcsMemFlag("Yes");
				payDTO.setCcsNo(recDTO.getF_NCCSMEMNO());
			}else{
				payDTO.setCcsSubAmt(0);
				payDTO.setCcsMemFlag("No");
				payDTO.setCcsNo(recDTO.getF_NCCSMEMNO());
			}if(!CPSUtils.isNullOrEmpty(debDTO.getF_NITAX())){
				payDTO.setIncomeTaxAmt(Integer.parseInt(debDTO.getF_NITAX()));
			}else{
				payDTO.setIncomeTaxAmt(0);
			}if(!CPSUtils.isNullOrEmpty(coreDTO.getF_VC2PANNO())){
				payDTO.setPanCardNo(coreDTO.getF_VC2PANNO());
			}else{
				payDTO.setPanCardNo("");
			}
			if(Integer.parseInt(debDTO.getF_NCGEIS())!=0){
				String query="select coalesce( ((select GROUP_ID from pay_cgeis_master where (after_member=? or before_member=?) and status=1)),1) cgeisId from dual";
				int cgeisId = Integer.parseInt(session.createSQLQuery(query).setInteger(0, Integer.parseInt(debDTO.getF_NCGEIS())).setInteger(1, Integer.parseInt(debDTO.getF_NCGEIS())).uniqueResult().toString());
				payDTO.setCgeisGroupID(cgeisId);
				if(Integer.parseInt(debDTO.getF_NCGEIS())==40 ||Integer.parseInt(debDTO.getF_NCGEIS())==20 ||Integer.parseInt(debDTO.getF_NCGEIS())==10 ||Integer.parseInt(debDTO.getF_NCGEIS())==5 ){
					payDTO.setCgeisMemFlag("No");
				}else{
					payDTO.setCgeisMemFlag("Yes");
				}
			}else{
				payDTO.setCgeisGroupID(0);
			}
			List<TLoanLinkDTO> loalList=session.createSQLQuery("Select F_cpersno as F_CPERSNO,F_ncurrentinsno as F_NCURRENTINSNO,F_NBALANCE as F_NBALANCE,F_vc2loanname as F_VC2LOANNAME," +
					"F_ninstamt as F_NINSTAMT,F_ntotinstno as F_NTOTINSTNO,F_nloantype as F_NLOANTYPE ,F_VC2REMARK as F_VC2REMARK From T_loan_link Where F_cpersno='"+debDTO.getF_CPERSNO()+"'and F_ncurrentinsno!=" +
					"F_ntotinstno And F_cpersno In (Select Sfid From Emp_master Where Status=1)").addScalar("F_CPERSNO",Hibernate.STRING).addScalar("F_NCURRENTINSNO",Hibernate.INTEGER).addScalar("F_VC2LOANNAME",Hibernate.STRING).
					addScalar("F_NINSTAMT",Hibernate.INTEGER).addScalar("F_NBALANCE",Hibernate.INTEGER).addScalar("F_NTOTINSTNO",Hibernate.INTEGER).addScalar("F_NLOANTYPE",Hibernate.INTEGER).addScalar("F_VC2REMARK",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TLoanLinkDTO.class)).list();
			for(int t=0;t<loalList.size();t++){
				TLoanLinkDTO tLoanLinkDTO = loalList.get(t);
				PayBillLoanDTO payBillLoanDTO= new PayBillLoanDTO();
				payBillLoanDTO.setSfid(payDTO.getSfID());
				payBillLoanDTO.setPresentInst(tLoanLinkDTO.getF_NCURRENTINSNO());
				payBillLoanDTO.setTotalInst(tLoanLinkDTO.getF_NTOTINSTNO());
				payBillLoanDTO.setTotalAmt(tLoanLinkDTO.getF_NTOTINSTNO()*tLoanLinkDTO.getF_NINSTAMT());
				payBillLoanDTO.setEmi(tLoanLinkDTO.getF_NINSTAMT());
				payBillLoanDTO.setLoanDeduType(tLoanLinkDTO.getF_VC2REMARK());
				if(CPSUtils.compareStrings(tLoanLinkDTO.getF_VC2LOANNAME(),"HBA")){
					payBillLoanDTO.setLoanType(9);
				}else if(CPSUtils.compareStrings(tLoanLinkDTO.getF_VC2LOANNAME(),"CAR")){
					payBillLoanDTO.setLoanType(4);
				}else if(CPSUtils.compareStrings(tLoanLinkDTO.getF_VC2LOANNAME(),"SCOOTER")){
					payBillLoanDTO.setLoanType(7);
				}else if(CPSUtils.compareStrings(tLoanLinkDTO.getF_VC2LOANNAME(),"GPF")){
					payBillLoanDTO.setLoanType(2);
				}else if(CPSUtils.compareStrings(tLoanLinkDTO.getF_VC2LOANNAME(),"PC")){
					payBillLoanDTO.setLoanType(6);
				}else if(CPSUtils.compareStrings(tLoanLinkDTO.getF_VC2LOANNAME(),"FADV")){
					payBillLoanDTO.setLoanType(1);
				}
				payBillLoanDTO.setStatus(1);
				payBillLoanDTO.setCreationDate(CPSUtils.getCurrentDate());
				payBillLoanDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				session.saveOrUpdate(payBillLoanDTO);
				
			}
			
			if(Integer.parseInt(recDTO.getF_NCORTATCH())!=0){
				PayBillCanfinDTO canDTO=(PayBillCanfinDTO) session.createCriteria(PayBillCanfinDTO.class).add(Expression.eq("sfid", debDTO.getF_CPERSNO())).add(Expression.eq("deductionID", 4)).add(Expression.eq("status", 1)).uniqueResult();
				if(CPSUtils.isNullOrEmpty(canDTO)){
					canDTO = new PayBillCanfinDTO();
				}
				canDTO.setSfid(debDTO.getF_CPERSNO());
				canDTO.setDeductionID(4);
				canDTO.setPresentInst(2);
				canDTO.setNoOfInst(3);
				canDTO.setAmount(Float.parseFloat(recDTO.getF_NCORTATCH()));
				canDTO.setStatus(1);
				canDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse("01-JAN-2011"));
				session.saveOrUpdate(canDTO);
				
			}if(Integer.parseInt(recDTO.getF_NHDFC())!=0){
				PayBillCanfinDTO canDTO=(PayBillCanfinDTO) session.createCriteria(PayBillCanfinDTO.class).add(Expression.eq("sfid", debDTO.getF_CPERSNO())).add(Expression.eq("deductionID", 2)).add(Expression.eq("status", 1)).uniqueResult();
				if(CPSUtils.isNullOrEmpty(canDTO)){
					canDTO = new PayBillCanfinDTO();
				}
				canDTO.setSfid(debDTO.getF_CPERSNO());
				canDTO.setDeductionID(2);
				canDTO.setPresentInst(2);
				canDTO.setNoOfInst(3);
				canDTO.setAmount(Float.parseFloat(recDTO.getF_NHDFC()));
				canDTO.setStatus(1);
				canDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse("01-JAN-2011"));
				session.saveOrUpdate(canDTO);
				
			}if(Integer.parseInt(recDTO.getF_NLIC())!=0){
				PayBillCanfinDTO canDTO=(PayBillCanfinDTO) session.createCriteria(PayBillCanfinDTO.class).add(Expression.eq("sfid", debDTO.getF_CPERSNO())).add(Expression.eq("deductionID", 3)).add(Expression.eq("status", 1)).uniqueResult();
				if(CPSUtils.isNullOrEmpty(canDTO)){
					canDTO = new PayBillCanfinDTO();
				}
				canDTO.setSfid(debDTO.getF_CPERSNO());
				canDTO.setDeductionID(3);
				canDTO.setPresentInst(2);
				canDTO.setNoOfInst(3);
				canDTO.setAmount(Float.parseFloat(recDTO.getF_NLIC()));
				canDTO.setStatus(1);
				canDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse("01-JAN-2011"));
				session.saveOrUpdate(canDTO);
				
			}
			quarterResult=session.createSQLQuery("select get_quarter_details('"+payDTO.getSfID()+"') from dual").uniqueResult().toString();
			
			if(CPSUtils.compareStrings(quarterResult.split("@")[0],"Y")){
				List<LicenceFeeChargesDTO> lfList=(List<LicenceFeeChargesDTO>) session.createCriteria(LicenceFeeChargesDTO.class).addOrder(Order.desc("licenceFee")).add(Expression.le("licenceFee", debDTO.getF_NRENT())).add(Expression.eq("status", 1)).list();
				if(lfList.size()!=0){
				LicenceFeeChargesDTO licenceFeeChargesDTO=lfList.get(0);
		        PayQuarterManagementDTO payQuarterManagementDTO =new PayQuarterManagementDTO();
			    payQuarterManagementDTO.setSfid(payDTO.getSfID());
			    payQuarterManagementDTO.setOccupiedDate(new Date("01-JUN-2012"));
			    payQuarterManagementDTO.setCreatedBy("SF0781");
			    payQuarterManagementDTO.setLastModifiedBy("SF0781");
			    payQuarterManagementDTO.setQuarterNo(quarterResult.split("@")[1]);
			    payQuarterManagementDTO.setQuartersType(licenceFeeChargesDTO.getQuarterSubType());
			    payQuarterManagementDTO.setStatus(1);
			    payDTO.setQuartersFlag("Yes");
			    payDTO.setElectricityBillAmt(Integer.parseInt(debDTO.getF_NELEC()));
			    session.saveOrUpdate(payQuarterManagementDTO);
			}else{
				PayQuarterManagementDTO payQuarterManagementDTO =new PayQuarterManagementDTO();
			    payQuarterManagementDTO.setSfid(payDTO.getSfID());
			    payQuarterManagementDTO.setOccupiedDate(new SimpleDateFormat("dd-MMM-yyyy").parse("01-JUN-2012"));
			    payQuarterManagementDTO.setCreatedBy("SF0781");
			    payQuarterManagementDTO.setLastModifiedBy("SF0781");
			    payQuarterManagementDTO.setQuarterNo(quarterResult.split("@")[1]);
			    payQuarterManagementDTO.setQuartersType("0");	
			    payQuarterManagementDTO.setStatus(1);
			    payDTO.setQuartersFlag("Yes");
			    payDTO.setElectricityBillAmt(Integer.parseInt(debDTO.getF_NELEC()));
			    session.saveOrUpdate(payQuarterManagementDTO);
			}
			}else{
				payDTO.setQuartersFlag("No");
			}
			 session.saveOrUpdate(payDTO);
		}
	
		}
		//tx.commit();
		}
	}
	catch (Exception e) {
		throw e;
	}
	finally{
		//session.close();
	}
	return message;
}
@SuppressWarnings("unchecked")
public Map compareDBMigrationDetails(String month,String flag,IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	List<DBComparisonDTO> compList=null;
	Session session=null;
	int creditsMatched=0;
	int debitsMatched=0;
	int recoveriesMatched=0;
	int takeHomeMathced=0;
	Map map = null;
	
	//for getting only credits,debits,recoveries matched or unmatched list //kumari 
	List<DBComparisonDTO> creditsList= new ArrayList<DBComparisonDTO>();
	List<DBComparisonDTO> debitsList= new ArrayList<DBComparisonDTO>();
	List<DBComparisonDTO> recoveriesList= new ArrayList<DBComparisonDTO>();
	try{
		session = hibernateUtils.getSession();
		 /*DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
	    String[] months = dateFormatSymbols.getShortMonths();
		String cpsMonth=month.split("/")[0];
		String year=(month.split("/")[2]).substring(0, 4);
		year=String.valueOf(Integer.parseInt(year)+1);
		String runMonth="01-"+months[Integer.parseInt(cpsMonth)-1]+"-"+year;*/
		//String qur="select TO_CHAR(TO_DATE('"+month+"','dd-mon-yyyy')) from dual";
		//month=session.createSQLQuery(qur).uniqueResult().toString();
		if(CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getType())){
			compList = (List<DBComparisonDTO>) session.createSQLQuery(
					"Select cps.sfid as sfid ,db.f_vc2name as name ,Cps.C_basic_pay As cpsbasic ,Cr.F_nbasic As misbasic," + "Cps.C_grade_pay As cpsgrade,Cr.F_ndpay As misgrade,Cps.C_da As cpsda,"
							+ "Cr.F_nda As misda,Cps.C_hra As cpshra,Cr.F_nhra As mishra,Cps.C_two_addl_incr As cpctwoaddincr," + "Cr.F_nfpay As mistwoaddincr,"
							+ "Cps.C_special_pay As cpsspay,Cr.F_nspay As misspay,Cps.C_tra As cpstra," + "Cr.F_ntpt As mistra,Cps.C_wash_allow As cpswash,"
							+ " Cps.c_risk_allowance as misRisk,Cps.c_risk_allowance as cpsRisk, Cps.c_dupt_allowance as misDupt, Cps.c_dupt_allowance as cpsDupt, "
							+ " Cps.c_hindi_incentives as misHindi, Cps.c_hindi_incentives as cpsHindi, Cps.c_xerox_allow as misXerox, Cps.c_xerox_allow as cpsXerox, "
							+ " Cps.c_misc as misMisc, Cps.c_misc as cpsMisc, " 
							+ "Cr.F_nwashall As miswash,Cps.C_fpa As cpsfpa,Cr.F_ncredit3 As misfpa,Cps.C_var_incr As cpsvarincr," + "Cr.F_ncredit5 As misvarincr,"
							+ " Cps.D_cghs As cpscghs,Db.F_ncghs As miscghs,Cps.D_cegis As cpscgeis,Db.F_ncgeis As miscgeis," + "Cps.D_gpf_amt As cpsgpf,Db.F_ngpfc As"
							+ " misgpf,Cps.D_income_tax+Cps.D_educ_cess+Cps.D_shec As cpsitax,Db.F_nitax As misitax," + "Cps.D_prof_tax As cpsptax,Db.F_nproftax As "
							+ " misptax,CPS.D_CON_CYCLE_INST_AMT As cpscycle,Db.F_vc2cycleinst As miscycle," + "CPS.D_CON_CAR_INST_AMT As cpscar,Db.F_ncar As miscar,"
							+ " CPS.D_CON_SCOOTER_INST_AMT As cpsscooter,Db.F_nconv As misscooter,CPS.D_PC_INST_AMT As cpspc," + "Db.F_npc As mispc,"
							+ "CPS.D_HBA_INST_AMT As cpshba,Db.F_nhba As mishba,CPS.D_FES_INST_AMT As cpsFest,db.f_nfadv As misFest,"
							+ "cps.d_rent As cpsRent,db.f_nrent As misRent,cps.d_water As cpsWater, db.f_nwater As misWater," + "cps.d_electricity As cpsElec,db.f_nelec As misElec,"
							+ "cps.d_furniture As cpsFurn ,cps.R_GIC as cpsGic,cps.R_CANFIN as cpsCanfin,db.f_nfurniture As misFurn,cps.d_eol_hpl As cpsEol,db.f_neolhpl As misEol,"
							+ " cps.d_tada As cpsTada,db.f_ntada As misTada,cps.r_ben_fund As cpsBenFund,rec.f_nben As misBenFund," + "cps.r_resi_secu As cpsResSecu,rec.f_nsecurity As misResSecu,"
							+ "cps.r_mess As cpsMess,rec.f_nmess As misMess,cps.r_wel_fund As cpsWelFund,rec.f_nwelc As misWelFund,rec.f_nwelr As misWelReFund ," + "cps.r_reg_fund As cpsRegFund,rec.f_nreg as misRegFund ,cps.r_court_attch As cpsCourt,"
							+ " rec.f_ncortatch As misCourt,cps.r_hdfc As cpsHdfc,rec.f_nhdfc As misHdfc,cps.r_lic As cpsLic," + "rec.f_nlic As misLic,cps.r_ccs_fund As cpsCcsc,"
							+ " rec.f_nccsc As misCcsc,cps.r_ccs_refund As cpsCcsr,rec.f_nccsr As misCcsr,cr.f_ncredit1 As cr1," + "cr.f_ncredit2 As cr2,cr.f_ncredit4 As cr4,"
							+ " db.f_ndebit1 as db1,db.f_ndebit2 as db2,db.f_ndebit3 as db3,db.f_ndebit4 as db4,db.f_ndebit5 as db5," + "rec.f_nrec1 as rec1,rec.f_nrec2 as rec2,"
							+ "rec.f_nrec3 as rec3,rec.f_nrec4 as rec4,rec.f_nrec5 as rec5," + "cps.gross_pay As cpsCredits,cr.f_ngrosspay As misCredits"
							+ ",cps.tot_debits As cpsDebits,to_char(to_number(cps.gross_pay)-to_number(cr.f_ngrosspay)) As creditDiff,cr.F_VC2REMARK as crRemarks ," +
									"db.F_VC2REMARK as dbRemarks,rec.F_VC2REMARK as recRemarks," + "db.f_ntotdebits As misDebits,"
							+ " to_char(to_number(cps.tot_debits)-to_number(db.f_ntotdebits)) as debitDiff,cps.tot_recs As cpsRec , "
							+ " Rec.F_ntotrecoveries As misrec ,to_char(To_number(Cps.Tot_recs)-To_number(Rec.F_ntotrecoveries)) As Recdiff," + "Cps.Take_home As "
							+ "Cpstakehome,Rec.F_nfinalpay As mistakehome,to_char(To_number(Cps.Take_home)-To_number(Rec.F_nfinalpay)) As "
							+ "takeHomeDiff, (select name_in_service_book from emp_master where sfid=cps.sfid) as nameInServiceBook," 
							+ "(select upper(des_master.name) as designation from designation_master des_master,emp_master emp where des_master.status=1 " 
							+ "and emp.designation_id=des_master.id and emp.sfid=cps.sfid) as designation," 
							+ "(select cat_master.name from category_master cat_master,designation_mappings des_map,emp_master emp where " 
							+ "emp.designation_id=des_map.desig_id and des_map.category_id=cat_master.id and emp.sfid=cps.sfid  and cat_master.status=1) AS category,upper(cr.f_vc2desig) as misDesignation " //new column added to get finance designation
							+ " from pay_txn_details cps,mis_credits cr, mis_debits db, "
							+ " mis_recoveries rec where db.f_cpersno = cr.f_cpersno and db.f_dtmonthyear = cr.f_dtmonthyear and " + "Rec.F_dtmonthyear = Cr.F_dtmonthyear And "
							+ " rec.f_dtmonthyear = db.f_dtmonthyear and rec.f_dtmonthyear='"+month+"' and "
							+ " rec.f_cpersno = db.f_cpersno and rec.f_cpersno = cr.f_cpersno and cps.sfid= cr.f_cpersno "
							+ " and cps.sfid= db.f_cpersno and cps.sfid= rec.f_cpersno and cps.status!=50 and cps.run_month='"+month+"' order by " 
							+ "(select cat_master.order_by from category_master cat_master,designation_mappings des_map,emp_master emp where " 
									+ "emp.designation_id=des_map.desig_id and des_map.category_id=cat_master.id and emp.sfid=cps.sfid  and cat_master.status=1),"
							+ "(select des_master.order_no as designation from designation_master des_master,emp_master emp where des_master.status=1 " 
									+ "and emp.designation_id=des_master.id and emp.sfid=cps.sfid)," 
									+ "cr.f_cpersno ,to_number(creditDiff),to_number(debitDiff),to_number(Recdiff)").addScalar("sfid", Hibernate.STRING).
			       addScalar("cpsbasic",Hibernate.STRING). addScalar("misbasic",Hibernate.STRING).addScalar("cpsgrade",Hibernate.STRING).addScalar("misgrade",Hibernate.STRING).
			       addScalar("cpsda",Hibernate.STRING).addScalar("name",Hibernate.STRING).addScalar("misda",Hibernate.STRING).addScalar("cpshra",Hibernate.STRING).
			       addScalar("mishra",Hibernate.STRING).addScalar("cpctwoaddincr",Hibernate.STRING).addScalar("mistwoaddincr",Hibernate.STRING).addScalar("cpsspay",Hibernate.STRING).
			       addScalar("misspay",Hibernate.STRING).addScalar("cpstra",Hibernate.STRING).addScalar("mistra",Hibernate.STRING).addScalar("cpswash",Hibernate.STRING).
			       addScalar("miswash",Hibernate.STRING).addScalar("misWelReFund",Hibernate.STRING).addScalar("cpsfpa",Hibernate.STRING).addScalar("misfpa",Hibernate.STRING).
			       addScalar("mistra",Hibernate.STRING).addScalar("cpsvarincr",Hibernate.STRING).addScalar("misvarincr",Hibernate.STRING).addScalar("cpscghs",Hibernate.STRING).
			       addScalar("miscghs",Hibernate.STRING).addScalar("cpscgeis",Hibernate.STRING).addScalar("miscgeis",Hibernate.STRING).addScalar("cpsgpf",Hibernate.STRING).
			       addScalar("misgpf",Hibernate.STRING).addScalar("cpsitax",Hibernate.STRING).addScalar("misitax",Hibernate.STRING).addScalar("cpsptax",Hibernate.STRING).
			       addScalar("misptax",Hibernate.STRING).addScalar("cpscycle",Hibernate.STRING).addScalar("miscycle",Hibernate.STRING).addScalar("cpscar",Hibernate.STRING).
			       addScalar("miscar",Hibernate.STRING).addScalar("cpsscooter",Hibernate.STRING).addScalar("misscooter",Hibernate.STRING).addScalar("cpspc",Hibernate.STRING).
			       addScalar("mispc",Hibernate.STRING).addScalar("cpshba",Hibernate.STRING).addScalar("mishba",Hibernate.STRING).addScalar("cpsFest",Hibernate.STRING).
			       addScalar("misFest",Hibernate.STRING).addScalar("cpsRent",Hibernate.STRING).addScalar("misRent",Hibernate.STRING).addScalar("cpsWater",Hibernate.STRING).
			       addScalar("misWater",Hibernate.STRING).addScalar("cpsElec",Hibernate.STRING).addScalar("misElec",Hibernate.STRING).addScalar("cpsTada",Hibernate.STRING).
			       addScalar("misTada",Hibernate.STRING).addScalar("cpsBenFund",Hibernate.STRING).addScalar("misBenFund",Hibernate.STRING).addScalar("cpsResSecu",Hibernate.STRING).
			       addScalar("misResSecu",Hibernate.STRING).addScalar("cpsMess",Hibernate.STRING).addScalar("misMess",Hibernate.STRING).addScalar("cpsWelFund",Hibernate.STRING).
			       addScalar("misWelFund",Hibernate.STRING).addScalar("cpsRegFund",Hibernate.STRING).addScalar("misRegFund",Hibernate.STRING).addScalar("cpsCourt",Hibernate.STRING).
			       addScalar("misCourt",Hibernate.STRING).addScalar("cpsHdfc",Hibernate.STRING).addScalar("cpsCanfin",Hibernate.STRING).addScalar("cpsGic",Hibernate.STRING).
			       addScalar("misHdfc",Hibernate.STRING).addScalar("cpsLic",Hibernate.STRING).addScalar("misLic",Hibernate.STRING).addScalar("cpsCcsc",Hibernate.STRING).
			       addScalar("misCcsc",Hibernate.STRING).addScalar("cpsCcsr",Hibernate.STRING).addScalar("misCcsr",Hibernate.STRING).addScalar("cr1",Hibernate.STRING).
			       addScalar("cr2",Hibernate.STRING).addScalar("cr4",Hibernate.STRING).addScalar("db1",Hibernate.STRING).addScalar("db2",Hibernate.STRING).
			       addScalar("db3",Hibernate.STRING).addScalar("db4",Hibernate.STRING).addScalar("db5",Hibernate.STRING).addScalar("rec1",Hibernate.STRING).
			       addScalar("rec2",Hibernate.STRING).addScalar("rec3",Hibernate.STRING).addScalar("rec4",Hibernate.STRING).addScalar("rec5",Hibernate.STRING).
			       addScalar("misDebits",Hibernate.STRING).addScalar("crRemarks",Hibernate.STRING).addScalar("dbRemarks",Hibernate.STRING).addScalar("recRemarks",Hibernate.STRING).
			       addScalar("misCredits",Hibernate.STRING).addScalar("misRec",Hibernate.STRING).addScalar("cpsDebits",Hibernate.STRING).addScalar("cpsCredits",Hibernate.STRING).
			       addScalar("cpsRec",Hibernate.STRING).addScalar("cpsTakeHome",Hibernate.STRING).addScalar("misTakeHome",Hibernate.STRING).addScalar("creditDiff",Hibernate.STRING).
			       addScalar("debitDiff",Hibernate.STRING).addScalar("recDiff",Hibernate.STRING).addScalar("takeHomeDiff",Hibernate.STRING).
			       addScalar("nameInServiceBook",Hibernate.STRING).addScalar("designation",Hibernate.STRING).addScalar("category",Hibernate.STRING).
			       addScalar("misDesignation", Hibernate.STRING).addScalar("misRisk",Hibernate.STRING).
			       addScalar("cpsRisk",Hibernate.STRING).addScalar("misDupt",Hibernate.STRING).addScalar("cpsDupt",Hibernate.STRING).
			       addScalar("misHindi",Hibernate.STRING).addScalar("cpsHindi",Hibernate.STRING).addScalar("misMisc",Hibernate.STRING).addScalar("cpsMisc",Hibernate.STRING).
			       addScalar("misXerox",Hibernate.STRING).addScalar("cpsXerox",Hibernate.STRING).
			       
			         setResultTransformer(
			   					Transformers.aliasToBean(DBComparisonDTO.class)).list();
		}else{

				compList = (List<DBComparisonDTO>) session.createSQLQuery("select txnfrommonth.sfid as sfid," +
						"(SELECT name_in_service_book FROM emp_master WHERE sfid=txnfrommonth.sfid) AS name,txnfrommonth.c_basic_pay as cpsbasic,txntomonth.c_basic_pay as misbasic," +
						"txnfrommonth.c_grade_pay as cpsgrade,txntomonth.c_grade_pay as misgrade,txnfrommonth.c_da as cpsda,txntomonth.c_da as misda,"
						+ " txntomonth.c_risk_allowance as misRisk,txnfrommonth.c_risk_allowance as cpsRisk, txntomonth.c_dupt_allowance as misDupt, txnfrommonth.c_dupt_allowance as cpsDupt, "
						+ " txntomonth.c_hindi_incentives as misHindi, txnfrommonth.c_hindi_incentives as cpsHindi, txntomonth.c_xerox_allow as misXerox, txnfrommonth.c_xerox_allow as cpsXerox, "
						+ " txntomonth.c_misc as misMisc, txnfrommonth.c_misc as cpsMisc, " +
	//nagendra 			
						" txntomonth.D_GPF_SA as misGpfsa, txnfrommonth.D_GPF_SA as cpsGpfsa,txntomonth.D_MISC2 as misMisc2, txnfrommonth.D_MISC2 as cpsMisc2,  " +
	                    " txntomonth.D_MISC1 as misMisc1, txnfrommonth.D_MISC1 as cpsMisc1, "+
						" txntomonth.D_MEDI as misMedi, txnfrommonth.D_MEDI as cpsMedi, txntomonth.D_LTC as misLtc, txnfrommonth.D_LTC as cpsLtc, "+
	                  	" txntomonth.D_PLI as misPli, txnfrommonth.D_PLI as cpsPli, "+
						"txnfrommonth.c_hra as cpshra,txntomonth.c_hra as mishra,txnFromMonth.c_two_addl_incr as cpctwoaddincr,txntomonth.c_two_addl_incr as mistwoaddincr," +
						"txnfrommonth.c_special_pay as cpsspay,txntomonth.c_special_pay as misspay,txnfrommonth.c_tra as cpstra,txntomonth.c_tra as mistra," +
						"txnfrommonth.c_wash_allow as cpswash,txntomonth.c_wash_allow as miswash,txnfrommonth.c_fpa as cpsfpa,txntomonth.c_fpa as misfpa," +
						"txnfrommonth.c_var_incr as cpsvarincr,txntomonth.c_var_incr as misvarincr,txnfrommonth.d_cghs as cpscghs,txntomonth.d_cghs as miscghs," +
						"txnfrommonth.d_cegis as cpscgeis,txntomonth.d_cegis as miscgeis,txnfrommonth.d_gpf_amt as cpsgpf,txntomonth.d_gpf_amt as misgpf," +
						"txnfrommonth.d_income_tax+txnfrommonth.d_educ_cess+txnfrommonth.d_shec as cpsTitax,txntomonth.d_income_tax+txntomonth.d_educ_cess+txntomonth.d_shec as misTitax," +

						"txnfrommonth.d_income_tax as cpsitax,txntomonth.d_income_tax as misitax," +
						"txnfrommonth.d_educ_cess as cpsEducess, txntomonth.d_educ_cess as misEducess,txnfrommonth.d_shec as cpsShec,txntomonth.d_shec as misShec,"+
						"txnfrommonth.d_prof_tax as cpsptax,txntomonth.d_prof_tax as misptax,txnfrommonth.d_con_cycle_inst_amt as cpscycle,txntomonth.d_con_cycle_inst_amt as miscycle," +
						"txnfrommonth.d_con_car_inst_amt as cpscar,txntomonth.d_con_car_inst_amt as miscar,txnfrommonth.d_con_scooter_inst_amt as cpsscooter,txntomonth.d_con_scooter_inst_amt as misscooter," +
						"txnfrommonth.d_pc_inst_amt as cpspc,txntomonth.d_pc_inst_amt as mispc,"+
						"txnfrommonth.D_PC_INST as mispcInstall,txntomonth.D_PC_INST as cpscInstall,"+
						"txnfrommonth.D_PC_TOT_INST as mispcTotamount,txntomonth.D_PC_TOT_INST as cpspcTotamount,"+
						" txnfrommonth.d_hba_inst_amt as cpshba,txntomonth.d_hba_inst_amt as mishba," +
						"txnfrommonth.d_fes_inst_amt as cpsFest,txntomonth.d_fes_inst_amt as misFest ,"+
						"txnfrommonth.D_FEST_INST as cpsFestInstall,txntomonth.D_FEST_INST as misFestInstall ,"+
						"txnfrommonth.D_FEST_TOT_INST as cpsFesttotal,txntomonth.D_FEST_TOT_INST as misFesttotal ,"+
						"txnfrommonth.D_CON_CAR_INST as cpsCarinstall,txntomonth.D_CON_CAR_INST as misCarinstall ,"+
						"txnfrommonth.D_CON_CAR_TOT_INST as cpsCartot,txntomonth.D_CON_CAR_TOT_INST as misCartot ,"+
						"txnfrommonth.D_CON_SCOOTER_INST as cpsScotterinstall,txntomonth.D_CON_SCOOTER_INST as misScotterinstall ,"+
						"txnfrommonth.D_CON_SCOOTER_TOT_INST as cpsScottertot,txntomonth.D_CON_SCOOTER_TOT_INST as misScottertot ,"+
						"txnfrommonth.D_CON_CYCLE_INST as cpsCycleinstall,txntomonth.D_CON_CYCLE_INST as misCycleinstall ,"+
						"txnfrommonth.D_CON_CYCLE_TOT_INST as cpsCycletot,txntomonth.D_CON_CYCLE_TOT_INST as misCycletot ,"+
						"txnfrommonth.D_HBA_INST as cpsHbainstall,txntomonth.D_HBA_INST as misHbainstall ,"+
						"txnfrommonth.D_HBA_TOT_INST as cpsHbatot,txntomonth.D_HBA_TOT_INST as misHbatot ,"+
						"txnfrommonth.D_GPF_INST_AMT as cpsgpfinstall,txntomonth.D_GPF_INST_AMT as misgpfinstall ,"+	
						"txnfrommonth.D_GPF_PRE_INST as cpsgpfpreinstall,txntomonth.D_GPF_PRE_INST as misgpfpreinstall ,"+	
						"txnfrommonth.D_GPF_TOT_INST as cpsgpfTotinstall,txntomonth.D_GPF_TOT_INST as misgpfTotinstall ,"+	
						" txnfrommonth.d_rent as cpsRent,txntomonth.d_rent as misRent," +
						"txnfrommonth.d_water as cpsWater,txntomonth.d_water as misWater,txnfrommonth.d_electricity as cpsElec,txntomonth.d_electricity as misElec," +
						"txnfrommonth.d_furniture as cpsFurn,txntomonth.d_furniture as misFurn,txnfrommonth.r_gic as cpsGic,txntomonth.r_gic as rec5," +
						"txnfrommonth.r_canfin as cpsCanfin,txntomonth.r_canfin as rec1,txnfrommonth.d_eol_hpl as cpsEol,txntomonth.d_eol_hpl as misEol,txnfrommonth.d_tada as cpsTada,txntomonth.d_tada as misTada," +
						"txnfrommonth.r_ben_fund as cpsBenFund,txntomonth.r_ben_fund as misBenFund,txnfrommonth.r_resi_secu as cpsResSecu,txntomonth.r_resi_secu as misResSecu," +
						"txnfrommonth.r_mess as cpsMess,txntomonth.r_mess as misMess,txnfrommonth.r_wel_fund as cpsWelFund,txntomonth.r_wel_fund as misWelFund," +
						"txnfrommonth.r_reg_fund as cpsRegFund,txntomonth.r_reg_fund as misRegFund,txnfrommonth.r_court_attch as cpsCourt,txntomonth.r_court_attch as misCourt," +
						"txnfrommonth.R_CRT_TOT_INST as rmisCrtTotIN,txntomonth.R_CRT_TOT_INST as rcpsCrtTotIN, "+
						"txnfrommonth.R_CRT_P_INST as rMisCrtpIn,txntomonth.R_CRT_P_INST as rCpsCrtpIn, "+
						"txnfrommonth.R_GIC_P_INST as rMisGicpIN,txntomonth.R_GIC_P_INST as rCpsGicpIN, "+
						"txnfrommonth.R_GIC_TOT_INST as rMisGicTotIn,txntomonth.R_GIC_TOT_INST as rCpsGicTotIn, "+
						"txnfrommonth.R_LIC_P_INST as rMisLicpinst,txntomonth.R_LIC_P_INST as rCpsLicpinst, "+
						"txnfrommonth.R_LIC_TOT_INST as rMisLicTotInst,txntomonth.R_LIC_TOT_INST as rCpsLicTotInst, "+
						"txnfrommonth.R_MISC1 as rmismis1,txntomonth.R_MISC1 as rcpsmis1, "+
						"txnfrommonth.R_MISC2 as rMismis2,txntomonth.R_MISC2 as rcpsmis2, "+
						"txnfrommonth.R_MISC3 as rMismis3,txntomonth.R_MISC3 as rcpsmis3, "+
						"txnfrommonth.R_HDFC_TOT_INST as rMisHdfcTotInstall,txntomonth.R_HDFC_TOT_INST as rCpsHdfcTotInstall, "+
						"txnfrommonth.R_HDFC_P_INST as rmisHdfcpinstall,txntomonth.R_HDFC_P_INST as rcpsHdfcpinstall, "+
						"txnfrommonth.R_CANFIN_P_INST as rMisCanfinpInstall,txntomonth.R_CANFIN_P_INST as rCpsCanfinpInstall, "+
						"txnfrommonth.R_CANFIN_TOT_INST as rMisCanfinTotInstall,txntomonth.R_CANFIN_TOT_INST as rCpsCanfinTotInstall, "+
						"txnfrommonth.R_WEL_FUND as rMisWelfund,txntomonth.R_WEL_FUND as rCpsWelfund, "+
						"txnfrommonth.R_WEL_LOAN as rMiswelLoan,txntomonth.R_WEL_LOAN as rCpswelLoan, "+
						"txnfrommonth.R_WEL_INST as rMiswelInst,txntomonth.R_WEL_INST as rCpsdwelInst, "+
						"txnfrommonth.R_WEL_TOT_INST as rmiswelTotInstall,txntomonth.R_WEL_TOT_INST as rcpswelTotInstall, "+
						"txnfrommonth.r_hdfc as cpsHdfc,txntomonth.r_hdfc as misHdfc,txnfrommonth.r_lic as cpsLic,txntomonth.r_lic as misLic,txnfrommonth.r_ccs_fund as cpsCcsc,txntomonth.r_ccs_fund as misCcsc," +
						"txnfrommonth.r_ccs_refund as cpsCcsr,txntomonth.r_ccs_refund as misCcsr,txnfrommonth.gross_pay as cpsCredits,txntomonth.gross_pay as misCredits," +
						"txnfrommonth.tot_debits as cpsDebits,txntomonth.tot_debits as misDebits,txnfrommonth.tot_recs as cpsRec,txntomonth.tot_recs as misrec," +
						"txnfrommonth.Take_home as Cpstakehome,txntomonth.Take_home as mistakehome, to_char(to_number(txnfrommonth.gross_pay)-to_number(txntomonth.gross_pay))as creditDiff, " +
						"to_char(to_number(txnfrommonth.tot_debits)-to_number(txntomonth.tot_debits)) as debitDiff ,to_char(to_number(txnfrommonth.Tot_recs)-to_number(txntomonth.Tot_recs)) as recDiff, " +
						"to_char(to_number(txnfrommonth.Take_home)-to_number(txntomonth.Take_home)) as takeHomeDiff,(select cat_master.name from category_master cat_master,designation_mappings des_map,emp_master emp where emp.designation_id=des_map.desig_id and des_map.category_id =cat_master.id and emp.sfid =txnFromMonth.sfid and cat_master.status =1)as category," +
						"(select upper(name) from designation_master des,pay_txn_master_details paytxn where paytxn.pay_designation_id=des.id and paytxn.run_month=txnfrommonth.run_month and paytxn.sfid=txnfrommonth.sfid) as designation," +
						"(select upper(name) from designation_master des,pay_txn_master_details paytxn where paytxn.pay_designation_id=des.id and paytxn.run_month=txntomonth.run_month and paytxn.sfid=txntomonth.sfid) as misDesignation,txnFromMonth.cr_remarks as crRemarks,txnFromMonth.dr_remarks as dbRemarks,txnFromMonth.remarks as recRemarks" +
						" from pay_txn_details txnFromMonth,pay_txn_details  " +
						"txnToMonth where txnfrommonth.run_month='"+month+"' and txnfrommonth.status!=50 and txntomonth.run_month='"+incomeTaxMasterBean.getCompareToMonth()+"'and txntomonth.status!=50 and txnfrommonth.sfid=txntomonth.sfid order by (select cat_master.order_by from category_master " +
						"cat_master,designation_mappings des_map,emp_master emp where emp.designation_id=des_map.desig_id and des_map.category_id =cat_master.id and emp.sfid =txnfrommonth.sfid and cat_master.status =1),(select des_master.order_no AS designation from designation_master des_master," +
						"emp_master emp where des_master.status=1 and emp.designation_id =des_master.id and emp.sfid =txnfrommonth.sfid),txnfrommonth.sfid,to_number(creditDiff),to_number(debitDiff),to_number(Recdiff)"
						).addScalar("sfid", Hibernate.STRING).
				       addScalar("cpsbasic",Hibernate.STRING). addScalar("misbasic",Hibernate.STRING).addScalar("cpsgrade",Hibernate.STRING).addScalar("misgrade",Hibernate.STRING).
				       addScalar("cpsda",Hibernate.STRING).addScalar("name",Hibernate.STRING).addScalar("misda",Hibernate.STRING).addScalar("cpshra",Hibernate.STRING).
				       addScalar("mishra",Hibernate.STRING).addScalar("cpctwoaddincr",Hibernate.STRING).addScalar("mistwoaddincr",Hibernate.STRING).addScalar("cpsspay",Hibernate.STRING).
				       addScalar("misspay",Hibernate.STRING).addScalar("cpstra",Hibernate.STRING).addScalar("mistra",Hibernate.STRING).addScalar("cpswash",Hibernate.STRING).
				       addScalar("miswash",Hibernate.STRING)/*.addScalar("misWelReFund",Hibernate.STRING)*/.addScalar("cpsfpa",Hibernate.STRING).addScalar("misfpa",Hibernate.STRING).
				       addScalar("mistra",Hibernate.STRING).addScalar("cpsvarincr",Hibernate.STRING).addScalar("misvarincr",Hibernate.STRING).addScalar("cpscghs",Hibernate.STRING).
				       addScalar("miscghs",Hibernate.STRING).addScalar("cpscgeis",Hibernate.STRING).addScalar("miscgeis",Hibernate.STRING).addScalar("cpsgpf",Hibernate.STRING).
				       addScalar("misgpf",Hibernate.STRING).addScalar("cpsitax",Hibernate.STRING).addScalar("misitax",Hibernate.STRING).addScalar("cpsptax",Hibernate.STRING).
				       addScalar("misptax",Hibernate.STRING).addScalar("cpscycle",Hibernate.STRING).addScalar("miscycle",Hibernate.STRING).addScalar("cpscar",Hibernate.STRING).
				       addScalar("miscar",Hibernate.STRING).addScalar("cpsscooter",Hibernate.STRING).addScalar("misscooter",Hibernate.STRING).addScalar("cpspc",Hibernate.STRING).
				       addScalar("mispc",Hibernate.STRING).addScalar("cpshba",Hibernate.STRING).addScalar("mishba",Hibernate.STRING).addScalar("cpsFest",Hibernate.STRING).
				       addScalar("misFest",Hibernate.STRING).addScalar("cpsRent",Hibernate.STRING).addScalar("misRent",Hibernate.STRING).addScalar("cpsWater",Hibernate.STRING).
				       addScalar("misWater",Hibernate.STRING).addScalar("cpsElec",Hibernate.STRING).addScalar("misElec",Hibernate.STRING).addScalar("cpsTada",Hibernate.STRING).
				       addScalar("misTada",Hibernate.STRING).addScalar("cpsBenFund",Hibernate.STRING).addScalar("misBenFund",Hibernate.STRING).addScalar("cpsResSecu",Hibernate.STRING).
				       addScalar("misResSecu",Hibernate.STRING).addScalar("cpsMess",Hibernate.STRING).addScalar("misMess",Hibernate.STRING).addScalar("cpsWelFund",Hibernate.STRING).
				       addScalar("misWelFund",Hibernate.STRING).addScalar("cpsRegFund",Hibernate.STRING).addScalar("misRegFund",Hibernate.STRING).addScalar("cpsCourt",Hibernate.STRING).
				       addScalar("misCourt",Hibernate.STRING).addScalar("cpsHdfc",Hibernate.STRING).addScalar("cpsCanfin",Hibernate.STRING).addScalar("cpsGic",Hibernate.STRING).
				       addScalar("misHdfc",Hibernate.STRING).addScalar("cpsLic",Hibernate.STRING).addScalar("misLic",Hibernate.STRING).addScalar("cpsCcsc",Hibernate.STRING).
				       addScalar("misCcsc",Hibernate.STRING).addScalar("cpsCcsr",Hibernate.STRING).addScalar("misCcsr",Hibernate.STRING)
				       //.addScalar("cr1",Hibernate.STRING).addScalar("cr2",Hibernate.STRING).addScalar("cr4",Hibernate.STRING).addScalar("db1",Hibernate.STRING).addScalar("db2",Hibernate.STRING).
				       //addScalar("db3",Hibernate.STRING).addScalar("db4",Hibernate.STRING).addScalar("db5",Hibernate.STRING)
				       .addScalar("rec1",Hibernate.STRING).addScalar("rec5",Hibernate.STRING).
				       //addScalar("rec2",Hibernate.STRING).addScalar("rec3",Hibernate.STRING).addScalar("rec4",Hibernate.STRING).
				       addScalar("misDebits",Hibernate.STRING).
				       addScalar("crRemarks",Hibernate.STRING).addScalar("dbRemarks",Hibernate.STRING).addScalar("recRemarks",Hibernate.STRING).
				       addScalar("misCredits",Hibernate.STRING).addScalar("misRec",Hibernate.STRING).addScalar("cpsDebits",Hibernate.STRING).addScalar("cpsCredits",Hibernate.STRING).
				       addScalar("cpsRec",Hibernate.STRING).addScalar("cpsTakeHome",Hibernate.STRING).addScalar("misTakeHome",Hibernate.STRING).
				       addScalar("creditDiff",Hibernate.STRING).addScalar("debitDiff",Hibernate.STRING).addScalar("recDiff",Hibernate.STRING).addScalar("takeHomeDiff",Hibernate.STRING).
				       //addScalar("nameInServiceBook",Hibernate.STRING).
				       addScalar("designation",Hibernate.STRING).addScalar("category",Hibernate.STRING).addScalar("misDesignation", Hibernate.STRING).
				       addScalar("misRisk",Hibernate.STRING).addScalar("cpsRisk",Hibernate.STRING).
				       addScalar("misDupt",Hibernate.STRING).addScalar("cpsDupt",Hibernate.STRING).
				       addScalar("misHindi",Hibernate.STRING).addScalar("cpsHindi",Hibernate.STRING).
				       addScalar("misMisc",Hibernate.STRING).addScalar("cpsMisc",Hibernate.STRING).
				       addScalar("misXerox",Hibernate.STRING).addScalar("cpsXerox",Hibernate.STRING).
				       addScalar("misPli",Hibernate.STRING).addScalar("cpsPli",Hibernate.STRING).
				       addScalar("misGpfsa",Hibernate.STRING).addScalar("cpsGpfsa",Hibernate.STRING).
				       addScalar("misLtc",Hibernate.STRING).addScalar("cpsLtc",Hibernate.STRING).
				       addScalar("misMedi",Hibernate.STRING).addScalar("cpsMedi",Hibernate.STRING).
				       addScalar("misMisc2",Hibernate.STRING).addScalar("cpsMisc2",Hibernate.STRING).
				       addScalar("misMisc1",Hibernate.STRING).addScalar("cpsMisc1",Hibernate.STRING).
				       //nagendra
				       addScalar("rmisCrtTotIN",Hibernate.STRING).addScalar("rcpsCrtTotIN",Hibernate.STRING).
				       addScalar("rMisCrtpIn",Hibernate.STRING).addScalar("rCpsCrtpIn",Hibernate.STRING).
				       addScalar("rMisGicpIN",Hibernate.STRING).addScalar("rCpsGicpIN",Hibernate.STRING).
				       addScalar("rMisGicTotIn",Hibernate.STRING).addScalar("rCpsGicTotIn",Hibernate.STRING).
				       addScalar("rMisLicpinst",Hibernate.STRING).addScalar("rCpsLicpinst",Hibernate.STRING).
				       addScalar("rMisLicTotInst",Hibernate.STRING).addScalar("rCpsLicTotInst",Hibernate.STRING).
				       addScalar("rmismis1",Hibernate.STRING).addScalar("rcpsmis1",Hibernate.STRING).
				       addScalar("rMismis2",Hibernate.STRING).addScalar("rcpsmis2",Hibernate.STRING).
				       addScalar("rMismis3",Hibernate.STRING).addScalar("rcpsmis3",Hibernate.STRING).
				      ///stared here...
				       addScalar("rMiswelInst",Hibernate.STRING).addScalar("rCpsdwelInst",Hibernate.STRING).
				       addScalar("rMisWelfund",Hibernate.STRING).addScalar("rCpsWelfund",Hibernate.STRING).
				       addScalar("rMiswelLoan",Hibernate.STRING).addScalar("rCpswelLoan",Hibernate.STRING).
				       addScalar("rmiswelTotInstall",Hibernate.STRING).addScalar("rcpswelTotInstall",Hibernate.STRING).
				       addScalar("rmisHdfcpinstall",Hibernate.STRING).addScalar("rcpsHdfcpinstall",Hibernate.STRING).
				       addScalar("rMisHdfcTotInstall",Hibernate.STRING).addScalar("rCpsHdfcTotInstall",Hibernate.STRING).
				       addScalar("rMisCanfinpInstall",Hibernate.STRING).addScalar("rCpsCanfinpInstall",Hibernate.STRING).
				       addScalar("rMisCanfinTotInstall",Hibernate.STRING).addScalar("rCpsCanfinTotInstall",Hibernate.STRING).
				       addScalar("mispcInstall",Hibernate.STRING).addScalar("cpscInstall",Hibernate.STRING).
				       addScalar("mispcTotamount",Hibernate.STRING).addScalar("cpspcTotamount",Hibernate.STRING).
				       addScalar("misFestInstall",Hibernate.STRING).addScalar("cpsFestInstall",Hibernate.STRING).
				       addScalar("misFesttotal",Hibernate.STRING).addScalar("cpsFesttotal",Hibernate.STRING).
				       addScalar("misCycleinstall",Hibernate.STRING).addScalar("cpsCycleinstall",Hibernate.STRING).
				       addScalar("misCycletot",Hibernate.STRING).addScalar("cpsCycletot",Hibernate.STRING).
				       addScalar("misCarinstall",Hibernate.STRING).addScalar("cpsCarinstall",Hibernate.STRING).
				       addScalar("misCartot",Hibernate.STRING).addScalar("cpsCartot",Hibernate.STRING).
				       addScalar("misHbainstall",Hibernate.STRING).addScalar("cpsHbainstall",Hibernate.STRING).
				       addScalar("misHbatot",Hibernate.STRING).addScalar("cpsHbatot",Hibernate.STRING).
				       addScalar("misScotterinstall",Hibernate.STRING).addScalar("cpsScotterinstall",Hibernate.STRING).
				       addScalar("misScottertot",Hibernate.STRING).addScalar("cpsScottertot",Hibernate.STRING).
				       addScalar("misgpfinstall",Hibernate.STRING).addScalar("cpsgpfinstall",Hibernate.STRING).
				       addScalar("misgpfpreinstall",Hibernate.STRING).addScalar("cpsgpfpreinstall",Hibernate.STRING).
				       addScalar("misgpfTotinstall",Hibernate.STRING).addScalar("cpsgpfTotinstall",Hibernate.STRING).
				       addScalar("cpsEol",Hibernate.STRING).addScalar("misEol",Hibernate.STRING).
				       addScalar("misEducess",Hibernate.STRING).addScalar("cpsEducess",Hibernate.STRING).
				       addScalar("misShec",Hibernate.STRING).addScalar("cpsShec",Hibernate.STRING).
				       addScalar("misTitax",Hibernate.STRING).addScalar("cpsTitax",Hibernate.STRING).
				              setResultTransformer(Transformers.aliasToBean(DBComparisonDTO.class)).list();
			
				
				
				
		}
			
			List<DBComparisonDTO> mathedList= new ArrayList<DBComparisonDTO>();
			//for total employees count 
			int totRunEmpCount=((BigDecimal)session.createSQLQuery("select count(distinct sfid) from pay_txn_details where run_month='"+month+"' and status!=50").uniqueResult()).intValue();
			
			//for count of matched //kumari
			int basicMatched=0;
			int gradeMatched=0;
			int daMatched=0;
			int hraMatched=0;
			int twoAddIncMatched=0;
			int spayMatched=0;
			int traMatched=0;
			int washMatched=0;
			int fpaMatched=0;
			int varIncrMatched=0;
			int chgsMatched=0;
			int cgeisMatched=0;
			int gpfMatched=0;
			int itaxMatched=0;
			int ptaxMatched=0;
			int cycleMatched=0;
			int carMatched=0;
			int scooterMatched=0;
			int pcMatched=0;
			int hbaMatched=0;
			int festMatched=0;
			int rentMatched=0;
			int waterMatched=0;
			int elecMatched=0;
			int tadaMatched=0;
			int benFundMatched=0;
			int resSecuMatched=0;
			int messMatched=0;
			int welFundMatched=0;
			int regFundMatched=0;
			int courtMatched=0;
			int hdfcMatched=0;
			int licMatched=0;
			int gicMatched=0;
			int canfinMatched=0;
			int ccscMatched=0;
			int ccsrMatched=0;
			int riskMatched=0;
			int hindiMatched=0;
			int duptMatched=0;
			int miscMatched=0;
			int xeroxMatched=0;
			int misc2Matched=0;
			int misc1Matched=0;
			int gpfsaMatched=0;
			int pliMatched=0;
			int mediMatched=0;
			int ltcMatched=0;
			int gpfrecMatched=0;
		

			int educessMatched=0;
			int shecMatched=0;
			int eolMatched=0;
			

			int  crtpinMatched=0;
			int mis1Matched=0;
			int mis2Matched=0;
			int mis3Matched=0;
			int crtTotinMatched=0;
			int welfundMatched=0;
			int welloanMatched=0;
			int titaxMatched=0;
			
		
	
		
			
			
			
			
			//--kumari
			
			if(flag.equals("Y")){
				for(int m=0;m<compList.size();m++){
					DBComparisonDTO mDTO=compList.get(m);
					if(Integer.parseInt(mDTO.getTakeHomeDiff())==0){
						mathedList.add(mDTO);
						//for only credits matched list
						if(Integer.parseInt(mDTO.getCreditDiff())==0){
						creditsList.add(mDTO);
						}
						if(Integer.parseInt(mDTO.getDebitDiff())==0){
							debitsList.add(mDTO);
							}
						if(Integer.parseInt(mDTO.getRecDiff())==0){
							recoveriesList.add(mDTO);
							}
					}
				}
			}else{
				for(int m=0;m<compList.size();m++){
					DBComparisonDTO umDTO=compList.get(m);
					if(Integer.parseInt(umDTO.getTakeHomeDiff())!=0){
						mathedList.add(umDTO);
						//for only credits matched list
						if(Integer.parseInt(umDTO.getCreditDiff())!=0){
						creditsList.add(umDTO);
						}
						if(Integer.parseInt(umDTO.getDebitDiff())!=0){
							debitsList.add(umDTO);
							}
						if(Integer.parseInt(umDTO.getRecDiff())!=0){
							recoveriesList.add(umDTO);
							}
					}
			
				}
			}
			
			for(int i=0;i<mathedList.size();i++){
				DBComparisonDTO dBComparisonDTO =mathedList.get(i);
				if(Integer.parseInt(dBComparisonDTO.getDebitDiff())==0){
					debitsMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCreditDiff())==0){
					creditsMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getRecDiff())==0){
					recoveriesMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getTakeHomeDiff())==0){
					takeHomeMathced++;
				}
				
				//for count of matched//kumari
				if(Integer.parseInt(dBComparisonDTO.getCpsbasic())==Integer.parseInt(dBComparisonDTO.getMisbasic())){
					basicMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsgrade())==Integer.parseInt(dBComparisonDTO.getMisgrade())){
					gradeMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsda())==Integer.parseInt(dBComparisonDTO.getMisda())){
					daMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpshra())==Integer.parseInt(dBComparisonDTO.getMishra())){
					hraMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpctwoaddincr())==Integer.parseInt(dBComparisonDTO.getMistwoaddincr())){
					twoAddIncMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsspay())==Integer.parseInt(dBComparisonDTO.getMisspay())){
					spayMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpstra())==Integer.parseInt(dBComparisonDTO.getMistra())){
					traMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpswash())==Integer.parseInt(dBComparisonDTO.getMiswash())){
					washMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsfpa())==Integer.parseInt(dBComparisonDTO.getMisfpa())){
					fpaMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsvarincr())==Integer.parseInt(dBComparisonDTO.getMisvarincr())){
					varIncrMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpscghs())==Integer.parseInt(dBComparisonDTO.getMiscghs())){
					chgsMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpscgeis())==Integer.parseInt(dBComparisonDTO.getMiscgeis())){
					cgeisMatched++;
				}
				if(!CPSUtils.isNullOrEmpty(dBComparisonDTO.getMisgpf())){
				if(Integer.parseInt(dBComparisonDTO.getCpsgpf())==Integer.parseInt(dBComparisonDTO.getMisgpf())){
					gpfMatched++;
				}
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsitax())==Integer.parseInt(dBComparisonDTO.getMisitax())){
					itaxMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsitax())==Integer.parseInt(dBComparisonDTO.getMisitax())){
					ptaxMatched++;
				}
				if(!CPSUtils.isNullOrEmpty(dBComparisonDTO.getMiscycle())){
				if(Integer.parseInt(dBComparisonDTO.getCpscycle())==Integer.parseInt(dBComparisonDTO.getMiscycle())){
					cycleMatched++;
				}
				}
				if(Integer.parseInt(dBComparisonDTO.getCpscar())==Integer.parseInt(dBComparisonDTO.getMiscar())){
					carMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsscooter())==Integer.parseInt(dBComparisonDTO.getMisscooter())){
					scooterMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpspc())==Integer.parseInt(dBComparisonDTO.getMispc())){
					pcMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpshba())==Integer.parseInt(dBComparisonDTO.getMishba())){
					hbaMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsFest())==Integer.parseInt(dBComparisonDTO.getMisFest())){
					festMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsRent())==Integer.parseInt(dBComparisonDTO.getMisRent())){
					rentMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsWater())==Integer.parseInt(dBComparisonDTO.getMisWater())){
					waterMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsElec())==Integer.parseInt(dBComparisonDTO.getMisElec())){
					elecMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsTada())==Integer.parseInt(dBComparisonDTO.getMisTada())){
					tadaMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsBenFund())==Integer.parseInt(dBComparisonDTO.getMisBenFund())){
					benFundMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsResSecu())==Integer.parseInt(dBComparisonDTO.getMisResSecu())){
					resSecuMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsMess())==Integer.parseInt(dBComparisonDTO.getMisMess())){
					messMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsWelFund())==Integer.parseInt(dBComparisonDTO.getMisWelFund())){
					welFundMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsRegFund())==Integer.parseInt(dBComparisonDTO.getMisRegFund())){
					regFundMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsCourt())==Integer.parseInt(dBComparisonDTO.getMisCourt())){
					courtMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsHdfc())==Integer.parseInt(dBComparisonDTO.getMisHdfc())){
					hdfcMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsLic())==Integer.parseInt(dBComparisonDTO.getMisLic())){
					licMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsGic())==Integer.parseInt(dBComparisonDTO.getRec5())){
					gicMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsCanfin())==Integer.parseInt(dBComparisonDTO.getRec1())){
					canfinMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsCcsc())==Integer.parseInt(dBComparisonDTO.getMisCcsc())){
					ccscMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsCcsr())==Integer.parseInt(dBComparisonDTO.getMisCcsr())){
					ccsrMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsRisk())==Integer.parseInt(dBComparisonDTO.getMisRisk())){
					riskMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsHindi())==Integer.parseInt(dBComparisonDTO.getCpsHindi())){
					hindiMatched++;
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsDupt())==Integer.parseInt(dBComparisonDTO.getMisDupt())){
					duptMatched++;
				
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsMisc())==Integer.parseInt(dBComparisonDTO.getMisMisc())){
					 miscMatched++;
				
				}
				if(Integer.parseInt(dBComparisonDTO.getCpsXerox())==Integer.parseInt(dBComparisonDTO.getMisXerox())){
					
					 xeroxMatched++;
				
				}
				
				//--kumari
				if(dBComparisonDTO.getCpsLtc()!=null && dBComparisonDTO.getMisLtc()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsLtc())==Integer.parseInt(dBComparisonDTO.getMisLtc())){
					 ltcMatched++;
				
				}
				}
				if(dBComparisonDTO.getCpsPli()!=null && dBComparisonDTO.getMisPli()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsPli())==Integer.parseInt(dBComparisonDTO.getMisPli())){
					 pliMatched++;
				
				}
				}
				if(dBComparisonDTO.getCpsMedi()!=null && dBComparisonDTO.getMisMedi()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsMedi())==Integer.parseInt(dBComparisonDTO.getMisMedi())){
					 mediMatched++;
				
				}
				}
				if(dBComparisonDTO.getCpsGpfsa()!=null && dBComparisonDTO.getMisGpfsa()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsGpfsa())==Integer.parseInt(dBComparisonDTO.getMisGpfsa())){
					 gpfsaMatched++;
				}
				}
				if(dBComparisonDTO.getCpsMisc1()!=null && dBComparisonDTO.getMisMisc1()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsMisc1())==Integer.parseInt(dBComparisonDTO.getMisMisc1())){
					 misc1Matched++;
				}
				}
				if(dBComparisonDTO.getCpsMisc2()!=null && dBComparisonDTO.getMisMisc2()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsMisc2())==Integer.parseInt(dBComparisonDTO.getMisMisc2())){
					
					misc2Matched++;
				}
				}
			/*	if(Integer.parseInt(dBComparisonDTO.getrCpsdwelInst())==Integer.parseInt(dBComparisonDTO.getrMiswelInst())){
					
					welinstallMatched++;
				
				}*/
				if(dBComparisonDTO.getrCpsCrtpIn()!=null && dBComparisonDTO.getrMisCrtpIn()!=null){
				if(Integer.parseInt(dBComparisonDTO.getrCpsCrtpIn())==Integer.parseInt(dBComparisonDTO.getrMisCrtpIn())){
		
					crtpinMatched++;
				}
				}
					
					/*if(Integer.parseInt(dBComparisonDTO.getrCpsLicpinst())==Integer.parseInt(dBComparisonDTO.getrMisLicpinst())){
						
						licpinstallMatched++;
					
					}if(Integer.parseInt(dBComparisonDTO.getrCpsLicTotInst())==Integer.parseInt(dBComparisonDTO.getrMisLicTotInst())){
						
						lictotalinstallMatched++;
					
					}*/
				if(dBComparisonDTO.getRcpsmis1()!=null && dBComparisonDTO.getRmismis1()!=null){
				if(Integer.parseInt(dBComparisonDTO.getRcpsmis1())==Integer.parseInt(dBComparisonDTO.getRmismis1())){
						
						mis1Matched++;
				}
					}/*if(Integer.parseInt(dBComparisonDTO.getrCpsGicpIN())==Integer.parseInt(dBComparisonDTO.getrMisGicpIN())){
						
						gicpinMatched++;
					
					}
				*/
					
				if(dBComparisonDTO.getRcpsmis2()!=null && dBComparisonDTO.getrMismis2()!=null){	
					if(Integer.parseInt(dBComparisonDTO.getRcpsmis2())==Integer.parseInt(dBComparisonDTO.getrMismis2())){
						
						mis2Matched++;
					}
					}
				if(dBComparisonDTO.getRcpsmis3()!=null && dBComparisonDTO.getRcpsmis3()!=null){
				if(Integer.parseInt(dBComparisonDTO.getRcpsmis3())==Integer.parseInt(dBComparisonDTO.getRcpsmis3())){
						
						mis3Matched++;
				}
					}	
					/*if(Integer.parseInt(dBComparisonDTO.getrCpsGicTotIn())==Integer.parseInt(dBComparisonDTO.getrMisGicTotIn())){
						
						gictotinMatched++;
					
					}*/
				if(dBComparisonDTO.getRcpsCrtTotIN()!=null && dBComparisonDTO.getRmisCrtTotIN()!=null){
				if(Integer.parseInt(dBComparisonDTO.getRcpsCrtTotIN())==Integer.parseInt(dBComparisonDTO.getRmisCrtTotIN())){
						
						crtTotinMatched++;
				}
					}
					/*if(Integer.parseInt(dBComparisonDTO.getrCpsCanfinpInstall())==Integer.parseInt(dBComparisonDTO.getrMisCanfinpInstall())){
									
						canfinpMatched++;
										
										}
					if(Integer.parseInt(dBComparisonDTO.getrCpsCanfinTotInstall())==Integer.parseInt(dBComparisonDTO.getrMisCanfinTotInstall())){
						
						canfintotMatched++;
					
					}
					
					if(Integer.parseInt(dBComparisonDTO.getrCpsWelfund())==Integer.parseInt(dBComparisonDTO.getrMisWelfund())){
						
						weltotinMatched++;
					
					}*/
				/*	if(Integer.parseInt(dBComparisonDTO.getRcpsHdfcpinstall())==Integer.parseInt(dBComparisonDTO.getRmisHdfcpinstall())){
						
						hdfcpMatched++;
					
					}
					
					if(Integer.parseInt(dBComparisonDTO.getrCpsHdfcTotInstall())==Integer.parseInt(dBComparisonDTO.getrMisHdfcTotInstall())){
						
						hdfctotMatched++;
					
					}*/
				//NAGENDRA.V
				if(dBComparisonDTO.getrCpsWelfund()!=null && dBComparisonDTO.getrMisWelfund()!=null){
				if(Integer.parseInt(dBComparisonDTO.getrCpsWelfund())==Integer.parseInt(dBComparisonDTO.getrMisWelfund())){
						
						welfundMatched++;
				}
							}
				if(dBComparisonDTO.getCpsgpfpreinstall()!=null && dBComparisonDTO.getMisgpfpreinstall()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsgpfpreinstall())==Integer.parseInt(dBComparisonDTO.getMisgpfpreinstall())){
								
								gpfrecMatched++;
				}
							}
				if(dBComparisonDTO.getrCpswelLoan()!=null && dBComparisonDTO.getrMiswelLoan()!=null){
				if(Integer.parseInt(dBComparisonDTO.getrCpswelLoan())==Integer.parseInt(dBComparisonDTO.getrMiswelLoan())){
							
							welloanMatched++;
				}
						}
				if(dBComparisonDTO.getCpsEducess()!=null && dBComparisonDTO.getMisEducess()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsEducess())==Integer.parseInt(dBComparisonDTO.getMisEducess())){
													
													educessMatched++;
				}
												}
				if(dBComparisonDTO.getCpsEol()!=null && dBComparisonDTO.getMisEol()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsEol())==Integer.parseInt(dBComparisonDTO.getMisEol())){
							
							eolMatched++;
				}
						}
				if(dBComparisonDTO.getCpsShec()!=null && dBComparisonDTO.getMisShec()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsShec())==Integer.parseInt(dBComparisonDTO.getMisShec())){
							
							shecMatched++;
				}
						}
						
				if(dBComparisonDTO.getCpsTitax()!=null && dBComparisonDTO.getMisTitax()!=null){
				if(Integer.parseInt(dBComparisonDTO.getCpsTitax())==Integer.parseInt(dBComparisonDTO.getMisTitax())){
							
							titaxMatched++;
				}
						}
			}
		map = new HashMap<String,Object>();
		map.put("dbList", mathedList);
		map.put("debitsMatched", debitsMatched);
		map.put("creditsMatched", creditsMatched);
		map.put("recoveriesMatched", recoveriesMatched);
		map.put("takeHomeMathced", takeHomeMathced);
		map.put("totRunEmpCount", totRunEmpCount);
		
		//for only credits,debits,recoveries list matched or unmatched//kumari
		map.put("creditsList", creditsList);
		map.put("debitsList", debitsList);
		map.put("recoveriesList", recoveriesList);
		
		//for count of matched//kumari
		Map countMap=new HashMap<String, Integer>();
		
		countMap.put("basicPayMatched", basicMatched);
		countMap.put("gradePayMatched", gradeMatched);
		countMap.put("daMatched", daMatched);
		countMap.put("hraMatched", hraMatched);
		countMap.put("twoAddIncMatched", twoAddIncMatched);
		countMap.put("spayMatched", spayMatched);
		countMap.put("traMatched", traMatched);
		countMap.put("washMatched", washMatched);
		countMap.put("fpaMatched", fpaMatched);
		countMap.put("varIncrMatched", varIncrMatched);
		countMap.put("cghsMatched", chgsMatched);
		countMap.put("cgeisMatched", cgeisMatched);
		countMap.put("gpfMatched", gpfMatched);
		countMap.put("itaxMatched", itaxMatched);
		countMap.put("ptaxMatched", ptaxMatched);
		countMap.put("cycleMatched", cycleMatched);
		countMap.put("carMatched", carMatched);
		countMap.put("scooterMatched", scooterMatched);
		countMap.put("pcMatched", pcMatched);
		countMap.put("hbaMatched", hbaMatched);
		countMap.put("festMatched", festMatched);
		countMap.put("rentMatched", rentMatched);
		countMap.put("waterMatched", waterMatched);
		countMap.put("elecMatched", elecMatched);
		countMap.put("tadaMatched", tadaMatched);
		countMap.put("benFundMatched", benFundMatched);
		countMap.put("resSecuMatched", resSecuMatched);
		countMap.put("messMatched", messMatched);
		countMap.put("welFundMatched", welFundMatched);
		countMap.put("regFundMatched", regFundMatched);
		countMap.put("courtMatched", courtMatched);
		countMap.put("hdfcMatched", hdfcMatched);
		countMap.put("licMatched", licMatched);
		countMap.put("gicMatched", gicMatched);
		countMap.put("canfinMatched", canfinMatched);
		countMap.put("ccscMatched", ccscMatched);
		countMap.put("ccsrMatched", ccsrMatched);
		countMap.put("riskMatched", riskMatched);
		countMap.put("hindiMatched", hindiMatched);
		countMap.put("duptMatched", duptMatched);
		countMap.put("miscMatched", miscMatched);
		countMap.put("xeroxMatched", xeroxMatched);
		countMap.put("ccscMatched", ccscMatched);
		countMap.put("pliMatched", pliMatched);
		countMap.put("mediMatched", mediMatched);
		countMap.put("gpfsaMatched", gpfsaMatched);
		countMap.put("misc1Matched", misc1Matched);
		countMap.put("misc2Matched", misc2Matched);
		countMap.put("ltcMatched", ltcMatched);
		countMap.put("mis3Matched", mis3Matched);
		countMap.put("mis2Matched", mis2Matched);
		countMap.put("mis1Matched", mis1Matched);

		countMap.put("crtpinMatched", crtpinMatched);
		countMap.put("welloanMatched", welloanMatched);
	
		countMap.put("crtTotinMatched", crtTotinMatched);

		countMap.put("welfundMatched", welfundMatched);

		countMap.put("gpfrecMatched", gpfrecMatched);
		countMap.put("educessMatched", educessMatched);
		countMap.put("shecMatched", shecMatched);
		countMap.put("eolMatched",eolMatched);
		countMap.put("titaxMatched", titaxMatched);
		map.put("matchedCount", countMap);
	

		//--kumari
	
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return map;
}
@SuppressWarnings("unchecked")
public List<DesignationDTO> getEmpDesignationList()throws Exception
{
	List<DesignationDTO> designationList = null;
	Session session = null;
	try{
		session = hibernateUtils.getSession();
		designationList = session.createCriteria(DesignationDTO.class).add(Expression.eq("status", 1)).list();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return designationList;
}
public String incomeTaxStatusDetails()throws Exception{
	Session session=null;
	String message="";
	try{
		session = hibernateUtils.getSession();
		IncomeTaxRunStatusDTO incomeTaxRunStatusDTO	=(IncomeTaxRunStatusDTO) session.get(IncomeTaxRunStatusDTO.class,Integer.parseInt(session.createCriteria(IncomeTaxRunStatusDTO.class).setProjection(Projections.max("id")).uniqueResult().toString()));
        if(!CPSUtils.isNullOrEmpty(incomeTaxRunStatusDTO)){
        	message=(incomeTaxRunStatusDTO.getRunType().equals("pl")?"Planned":(incomeTaxRunStatusDTO.getRunType().equals("pr")?"Projected":"Actual"))+" Completed For The Financial Year <b><font color=green>"+incomeTaxRunStatusDTO.getFinDetails().getFyToFrom()+"</font></b>";
        }
	}
	catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
@SuppressWarnings("unchecked")
public List<IncomeTaxSectionMappingDTO> getSectionMappingDetails() throws Exception{
	Session session = null;
	List<IncomeTaxSectionMappingDTO> keyList = null;
	try{
		session = hibernateUtils.getSession();
		session.clear();
		keyList = session.createCriteria(IncomeTaxSectionMappingDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("sectionId")).list();
		/*for (IncomeTaxSectionMappingDTO incomeTaxSectionMappingDTO : keyList) {
			IncomeTaxSectionDTO incomeTaxSectionDTO=new IncomeTaxSectionDTO();
			incomeTaxSectionDTO=(IncomeTaxSectionDTO)session.createCriteria(IncomeTaxSectionDTO.class).add(Expression.eq("id", incomeTaxSectionMappingDTO.getSectionId())).uniqueResult();
			incomeTaxSectionMappingDTO.setSectionDetails(incomeTaxSectionDTO);
		}*/
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return keyList;
}

@SuppressWarnings("unchecked")
public String submitIncomeTaxSectionMappingDetails(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception
{
	Session session = null;
	String message = null;
	int dup = 0;
	List<IncomeTaxSectionMappingDTO> keyList = null;
	try
	{
		IncomeTaxSectionMappingDTO incomeTaxSectionMappingDTO = new IncomeTaxSectionMappingDTO();
		incomeTaxSectionMappingDTO.setSectionId(Integer.parseInt(incomeTaxMasterBean.getSavingsTypeId()));
		incomeTaxSectionMappingDTO.setDisabilityFlag(Integer.parseInt(incomeTaxMasterBean.getDisability()));
		incomeTaxSectionMappingDTO.setGenderFlag(Integer.parseInt(incomeTaxMasterBean.getGender()));
		incomeTaxSectionMappingDTO.setSrCitizenFlag(Integer.parseInt(incomeTaxMasterBean.getSrCitizen()));
		incomeTaxSectionMappingDTO.setLimit(Integer.parseInt(incomeTaxMasterBean.getLimit()));
		incomeTaxSectionMappingDTO.setYearFrom(Integer.parseInt(incomeTaxMasterBean.getValidFrom()));
		incomeTaxSectionMappingDTO.setYearTo(Integer.parseInt(incomeTaxMasterBean.getValidTo()));
		incomeTaxSectionMappingDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
		incomeTaxSectionMappingDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		incomeTaxSectionMappingDTO.setStatus(1);
		incomeTaxSectionMappingDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
		incomeTaxSectionMappingDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
		incomeTaxSectionMappingDTO.setPercentage(Integer.valueOf(incomeTaxMasterBean.getPercentage()));
				
		session = hibernateUtils.getSession();
		if(CPSUtils.compareStrings(incomeTaxMasterBean.getGender(), "2"))
		{
			if(CPSUtils.compareStrings(CPSConstants.EDIT, incomeTaxMasterBean.getType()))
			{
				incomeTaxSectionMappingDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
				message = CPSConstants.UPDATE;
			}
			else
				message = CPSConstants.SUCCESS;
			if(CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getPk()))
				dup = session.createCriteria(IncomeTaxSectionMappingDTO.class).add(Expression.eq("sectionId", incomeTaxSectionMappingDTO.getSectionId()))
					.add(Expression.eq("genderFlag", incomeTaxSectionMappingDTO.getGenderFlag())).add(Expression.eq("disabilityFlag", incomeTaxSectionMappingDTO.getDisabilityFlag()))
					.add(Expression.eq("srCitizenFlag", incomeTaxSectionMappingDTO.getSrCitizenFlag())).add(Expression.eq("yearFrom", incomeTaxSectionMappingDTO.getYearFrom()))
					.add(Expression.eq("yearTo", incomeTaxSectionMappingDTO.getYearTo())).add(Expression.eq("status", incomeTaxSectionMappingDTO.getStatus())).list().size();
			if(dup != 0)
				message = CPSConstants.DUPLICATE;
		}
		else
		{
			if(CPSUtils.compareStrings(incomeTaxMasterBean.getType(), ""))
			keyList = (List<IncomeTaxSectionMappingDTO>)session.createCriteria(IncomeTaxSectionMappingDTO.class).add(Expression.eq("sectionId", incomeTaxSectionMappingDTO.getSectionId()))
					.add(Expression.eq("genderFlag", 2)).add(Expression.eq("disabilityFlag", incomeTaxSectionMappingDTO.getDisabilityFlag()))
					.add(Expression.eq("srCitizenFlag", incomeTaxSectionMappingDTO.getSrCitizenFlag())).add(Expression.eq("yearFrom", incomeTaxSectionMappingDTO.getYearFrom()))
					.add(Expression.eq("yearTo", incomeTaxSectionMappingDTO.getYearTo())).add(Expression.eq("status", incomeTaxSectionMappingDTO.getStatus())).list();
			if(!CPSUtils.isNullOrEmpty(keyList) && keyList.size()>0) 
			{
				dup = 1;
				message = CPSConstants.SAMESECTION;
			}
			else
			{
				if(CPSUtils.compareStrings(CPSConstants.EDIT, incomeTaxMasterBean.getType()))
				{
					incomeTaxSectionMappingDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
					message = CPSConstants.UPDATE;
				}
				else
					message = CPSConstants.SUCCESS;
				if(CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getPk()))
					dup = session.createCriteria(IncomeTaxSectionMappingDTO.class).add(Expression.eq("sectionId", incomeTaxSectionMappingDTO.getSectionId()))
						.add(Expression.eq("genderFlag", incomeTaxSectionMappingDTO.getGenderFlag())).add(Expression.eq("disabilityFlag", incomeTaxSectionMappingDTO.getDisabilityFlag()))
						.add(Expression.eq("srCitizenFlag", incomeTaxSectionMappingDTO.getSrCitizenFlag())).add(Expression.eq("yearFrom", incomeTaxSectionMappingDTO.getYearFrom()))
						.add(Expression.eq("yearTo", incomeTaxSectionMappingDTO.getYearTo())).add(Expression.eq("status", incomeTaxSectionMappingDTO.getStatus())).list().size();
				if(dup != 0)
					message = CPSConstants.DUPLICATE;
			}	
		}
		if(dup == 0)
		{
			session.saveOrUpdate(incomeTaxSectionMappingDTO);
			session.flush(); 
			session.clear();
		}
	}catch (Exception e) {
		message = CPSConstants.FAILED;
		throw e;
	}finally{
		
	}
	return message;
}
public String deleteIncomeTaxSectionMappingDetails(int id)throws Exception
{
	String message = "";
	Session session = null; //Transaction tx=null;
	try
	{
		session = hibernateUtils.getSession(); //tx = session.beginTransaction();
		IncomeTaxSectionMappingDTO incomeTaxSectionMappingDTO = (IncomeTaxSectionMappingDTO)session.get(IncomeTaxSectionMappingDTO.class, id);
		incomeTaxSectionMappingDTO.setStatus(0);
		session.saveOrUpdate(incomeTaxSectionMappingDTO);
		message = CPSConstants.DELETE;
		session.flush(); //tx.commit();
	}catch (Exception e) { //tx.rollback();
		message = CPSConstants.FAILED;
		throw e;
	}finally{
		 //session.close();
	}
	return message;
 }
@SuppressWarnings("unchecked")
public List<IncomeTaxRunStatusDTO> getIncomeTaxRunStatusList() throws Exception{
	Session session=null;
	List<IncomeTaxRunStatusDTO> keyList=null;
	try{
		session=hibernateUtils.getSession();
		keyList=session.createCriteria(IncomeTaxRunStatusDTO.class).list();
	}catch (Exception e) {
		throw e;
	}
	return keyList;
}
@SuppressWarnings("unchecked")
public IncomeTaxMasterBean getEditEmpList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
	Session session=null;
	 List <EmployeeBean> empList = null;
	 try{
		 session=hibernateUtils.getSession();
		 String qry = "select emp.doj_asl from emp_master emp where emp.status=1 and EMP.APPOINTMENT_TYPE_ID IN (2,3) AND emp.sfid=?";
		 Object obj= session.createSQLQuery(qry).setString(0, incomeTaxMasterBean.getSearchSfid()).uniqueResult();
		 if(!CPSUtils.isNullOrEmpty(obj)){
			 String qry1 = "select DISTINCT ptxn.run_id from pay_txn_details ptxn where ? IN (ptxn.RUN_MONTH)";
			 Object obj1 = session.createSQLQuery(qry1).setString(0, "01-"+incomeTaxMasterBean.getPayMonth()).uniqueResult();
			 if(!CPSUtils.isNullOrEmpty(obj1)){
				 //String qry2 = "SELECT * FROM PAY_TXN_DETAILS WHERE SFID=? AND RUN_MONTH=? AND " +
	 		       //            "RUN_MONTH<(SELECT DOJ_ASL FROM EMP_MASTER WHERE SFID=? AND STATUS=1)";
				 String qry2 = "SELECT CASE WHEN (?<(SELECT MIN(RUN_MONTH) FROM PAY_TXN_DETAILS WHERE " +
				 		       "SFID=?))THEN 'yes' else 'no' end as res from dual";
	            Object res = session.createSQLQuery(qry2).setString(0, "01-"+incomeTaxMasterBean.getPayMonth()).setString(1, incomeTaxMasterBean.getSearchSfid()).uniqueResult(); 
	            
	            if(res.toString().equals("yes")){
					 incomeTaxMasterBean.setIsEditEmp("YES");
				 }else{
					 incomeTaxMasterBean.setIsEditEmp("NO");
				 }
			 }else{
				 incomeTaxMasterBean.setIsEditEmp("NO");
			 }	
			 
		 }else{
			 incomeTaxMasterBean.setIsEditEmp("NO");
		 }
	 }catch(Exception e){
		 e.printStackTrace();
		 throw e;
	 }
	 return incomeTaxMasterBean;
}
@SuppressWarnings("unchecked")
public String submitProjectedDetails3(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	String message="";
	Session session=null;
	List<PayBillUserDTO> empList = null;
	Date fromMonth=null;
	Date toMonth=null;
	List<PayBillDTO> payList=null;
	try{
		session=hibernateUtils.getSession();
		incomeTaxMasterBean.setRunId(getTableID("INCOMETAX_RUN_ID"));
		
		List<IncomeTaxRunStatusDTO> statusList=	session.createCriteria(IncomeTaxRunStatusDTO.class).add(Expression.eq("finYearId", Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()))).add(Expression.eq("ProjectedStatus", 1)).list();
		
		if (statusList.size()>0) {
			message = CPSConstants.FAILED;
		}else{
			message=calculateIncomeTaxPlPrActl.submitITStatusDetails(incomeTaxMasterBean);
		}

		if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
		
			int remainingNoOfMonths=((BigDecimal)session.createSQLQuery(" Select Months_Between((Select To_Date('01-MAR-'||To_Year) From "+
                    " Pay_IT_Fin_Year_Master where id=?),(Select To_Date(Run_Month) From "+
					" (Select Run_Month,Rownum  Rowno From (Select Run_Month From Paybill_Status_Details  "+
					" Order By To_Date(Run_Month) Desc) Tab) Where Rowno=1)) Months From Dual").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
	        
			Date startRecoveryMonth=(Date)session.createSQLQuery(" select add_months((Select To_Date(Run_Month) From (Select Run_Month,Rownum "+
                    " Rowno From (Select Run_Month From Paybill_Status_Details "+
                    " order by to_date(run_month) desc) tab) where rowno=1),1) from dual").uniqueResult();

			fromMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select from_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
			toMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select to_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
			
			empList =calculateIncomeTaxPlPrActl.getEmployeeList(fromMonth,toMonth);
	  
	         for(int i=0;i<empList.size();i++){
	        	 
	        	 PayBillUserDTO payBillUserDTO=empList.get(i);
			        String sfid=payBillUserDTO.getSfID();
			        System.out.println(sfid);
		        
		        IncomeTaxRunDTO incomeTaxRunDTO =new IncomeTaxRunDTO();
		        incomeTaxRunDTO.setSfid(sfid);
		        incomeTaxRunDTO.setRunType(CPSConstants.ITPROJECTEDFLAG);
		        incomeTaxRunDTO.setRunId(incomeTaxMasterBean.getRunId());
		        incomeTaxRunDTO.setStatus(1);
		        incomeTaxRunDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
		        incomeTaxRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
		        incomeTaxRunDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
		        incomeTaxRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		        incomeTaxRunDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
		        incomeTaxMasterBean.setIncomeTaxRunDTO(incomeTaxRunDTO);
		        Calendar cal =Calendar.getInstance();
				cal.set(calculateIncomeTaxPlPrActl.getStartingMonthOfFinYear(incomeTaxMasterBean.getSelectedFYear()), 03, 01);
				incomeTaxMasterBean.setPayBillList(calculateIncomeTaxPlPrActl.getProjectionPayBillList1(sfid,fromMonth,toMonth));
				/*//-------my code start----------//
				payList=calculateIncomeTaxPlPrActl.getProjectionPayBillListed(empList,fromMonth,toMonth);
				List<PayBillDTO> payList1=new ArrayList<PayBillDTO>();
				for (PayBillDTO payBillDTO : payList) {
					if(CPSUtils.compareStrings(empList.get(i).getSfID().toUpperCase(), payBillDTO.getSfid().toUpperCase())){
						payList1.add(payBillDTO);
					}
				}
				incomeTaxMasterBean.setPayBillList(payList1);
				//--------my code end----------//*/		        	
				incomeTaxMasterBean=calculateIncomeTaxPlPrActl.submitITPayBillDetails(incomeTaxMasterBean);
				incomeTaxRunDTO.setArrearsPaid(0);
				int totalIncome=incomeTaxRunDTO.getTotSal()+incomeTaxRunDTO.getArrearsPaid();
				int grandTotal=totalIncome-(incomeTaxRunDTO.getLessHraEolHpl());
				int finalTotal=grandTotal+incomeTaxRunDTO.getGovtSubs();//1)Pay And Allowances
				
				//2) Exemptions
				
                incomeTaxRunDTO.setTotRentPaid(calculateIncomeTaxPlPrActl.getRentPaid(incomeTaxRunDTO.getSfid(), Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())));
				
				incomeTaxRunDTO.setHraExempted(calculateIncomeTaxPlPrActl.calculateHRAExempted(incomeTaxRunDTO.getTotHra(), incomeTaxRunDTO.getTotRentPaid(),0,0));
				
				incomeTaxRunDTO.setPrUpallw(calculateIncomeTaxPlPrActl.getProfessionalUpdateAllowance(payBillUserDTO.getgPay(), Integer.parseInt(payBillUserDTO.getPayDesignationId())));
				 
				int exemptions=incomeTaxRunDTO.getHraExempted()+incomeTaxRunDTO.getTraAllw()+incomeTaxRunDTO.getPrUpallw();
				int payAndAllowancesAfterExemptions=finalTotal-exemptions;
				
				incomeTaxRunDTO.setTotExemptions(exemptions);
				
				int totalDeductions=calculateIncomeTaxPlPrActl.submitPlannedExemptionsDetails(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotCghs(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotPTax());
				
				incomeTaxRunDTO.setTotalDeductions(totalDeductions);
				
				int payAndAllowancesAfterDeductions=payAndAllowancesAfterExemptions-(incomeTaxRunDTO.getTotPTax()+totalDeductions);
				
				int totalIncomeFromOtherSources=calculateIncomeTaxPlPrActl.submitPlannedTotalIncomeFromOtherSources(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID());
				 
				incomeTaxRunDTO.setTotOtherIncome(totalIncomeFromOtherSources);
				
				int totalIncomeAfterOtherIncome=payAndAllowancesAfterDeductions+totalIncomeFromOtherSources;
				
				int totalSavings=calculateIncomeTaxPlPrActl.submitPlannedTotalSavings(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxRunDTO.getTotCegis(), incomeTaxRunDTO.getTotPli(), 0, incomeTaxRunDTO.getGovtSubs(), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getRunId());
				
				totalSavings+=incomeTaxRunDTO.getGovtSubs();
				
				incomeTaxRunDTO.setTotSavings(totalSavings);
				
				int taxableIncome=totalIncomeAfterOtherIncome-totalSavings;
				
				incomeTaxRunDTO.setTaxableIncome(taxableIncome);
				
				long roundedOff=Math.round((taxableIncome/10))*10;
				
				incomeTaxRunDTO.setRoundedOff(roundedOff);
				
				int totalIncomeTax=calculateIncomeTax(roundedOff, incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getSelectedFYear());
				
				incomeTaxRunDTO.setTotIncomeTax(totalIncomeTax);
				
				int educationCess=totalIncomeTax+((int)(0.03*totalIncomeTax));
				
				incomeTaxRunDTO.setAddEduCess(educationCess);
				
				int recoveredIncomeTax=incomeTaxRunDTO.getTaxRecovered();
				
				incomeTaxRunDTO.setTaxToBeRecovered(educationCess-recoveredIncomeTax);
				
				incomeTaxRunDTO.setTaxPerMonth(incomeTaxRunDTO.getTaxToBeRecovered()/remainingNoOfMonths);
				
				incomeTaxRunDTO.setEffMonth(startRecoveryMonth);
			
				
				session.saveOrUpdate(incomeTaxRunDTO);
		      }
	     }
	}catch (Exception e) {
		throw e;
	}
	return message;
}
public String submitTransferEmpDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
	Session session=null;
	String message="";
	try{
		 session=hibernateUtils.getSession();
		 IncomeTaxPayBillDTO incomeTaxPayBillDTO;
		  incomeTaxPayBillDTO = (IncomeTaxPayBillDTO)session.createCriteria(IncomeTaxPayBillDTO.class).add(Expression.eq("sfid", incomeTaxMasterBean.getSearchSfid())).add(Expression.eq("month", new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+incomeTaxMasterBean.getPayMonth()))).add(Expression.eq("runType", "edit")).uniqueResult();
		 if(CPSUtils.isNullOrEmpty(incomeTaxPayBillDTO)){
			 incomeTaxPayBillDTO = new IncomeTaxPayBillDTO();
			 incomeTaxPayBillDTO.setCreationDate(new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())));
		 }
		 int runId=getRunMonthId("01-"+incomeTaxMasterBean.getPayMonth());
		 incomeTaxPayBillDTO.setSfid(incomeTaxMasterBean.getSearchSfid());
		 incomeTaxPayBillDTO.setMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+incomeTaxMasterBean.getPayMonth()));
		 
		 incomeTaxPayBillDTO.setBasicPay(Integer.parseInt(incomeTaxMasterBean.getBasic()));
		 incomeTaxPayBillDTO.setGradePay(Integer.parseInt(incomeTaxMasterBean.getGradePay()));
		 incomeTaxPayBillDTO.setSpecialPay(Integer.parseInt(incomeTaxMasterBean.getsPay()));
		 //incomeTaxPayBillDTO.setVariableIncr(0);
		 incomeTaxPayBillDTO.setVariableIncr(incomeTaxMasterBean.getVarInc());
		 incomeTaxPayBillDTO.setDa(Integer.parseInt(incomeTaxMasterBean.getDa()));
		 //incomeTaxPayBillDTO.setTwoAddlIncr(0);
		 incomeTaxPayBillDTO.setTwoAddlIncr(incomeTaxMasterBean.getTwoAddInc());
		 incomeTaxPayBillDTO.setHra(Integer.parseInt(incomeTaxMasterBean.getHra()));
		 incomeTaxPayBillDTO.setTpt(Integer.parseInt(incomeTaxMasterBean.getTpt()));
		 incomeTaxPayBillDTO.setETA(Integer.parseInt(incomeTaxMasterBean.getOtBill()));
		 incomeTaxPayBillDTO.setCredMisc(Integer.parseInt(incomeTaxMasterBean.getcMisc()));
		 //incomeTaxPayBillDTO.setTotalCredits(0);
		 incomeTaxPayBillDTO.setTotalCredits(incomeTaxMasterBean.getTotalCredits());
		 incomeTaxPayBillDTO.setGpf(Integer.parseInt(incomeTaxMasterBean.getGpf()));
		 incomeTaxPayBillDTO.setCegis(Integer.parseInt(incomeTaxMasterBean.getCgeis()));
		 incomeTaxPayBillDTO.setCghs(Integer.parseInt(incomeTaxMasterBean.getCghs()));
		 incomeTaxPayBillDTO.setProfTax(Integer.parseInt(incomeTaxMasterBean.getpTax()));
		 incomeTaxPayBillDTO.setPli(Integer.parseInt(incomeTaxMasterBean.getPli()));
		 incomeTaxPayBillDTO.setHbaLoan(Integer.parseInt(incomeTaxMasterBean.getHba()));
		 //incomeTaxPayBillDTO.setHdGicCfin(0);
		 incomeTaxPayBillDTO.setHdGicCfin(incomeTaxMasterBean.getHdGicCfin());
		 incomeTaxPayBillDTO.setLic(Integer.parseInt(incomeTaxMasterBean.getLic()));
		 incomeTaxPayBillDTO.setEol(Integer.parseInt(incomeTaxMasterBean.getEolHpl()));
		 incomeTaxPayBillDTO.setDebMisc(Integer.parseInt(incomeTaxMasterBean.getdMisc()));
		 //incomeTaxPayBillDTO.setIncomeTaxRec(0);
		 incomeTaxPayBillDTO.setIncomeTaxRec(incomeTaxMasterBean.getITaxRecovery());
		 incomeTaxPayBillDTO.setRunId(runId);
		 incomeTaxPayBillDTO.setRunType("edit");	 
		 incomeTaxPayBillDTO.setFpay(Integer.parseInt(incomeTaxMasterBean.getfPay()));
		 incomeTaxPayBillDTO.setCcs(Integer.parseInt(incomeTaxMasterBean.getCcs()));
		 incomeTaxPayBillDTO.setHdfc(Integer.parseInt(incomeTaxMasterBean.getHdfc()));
		 incomeTaxPayBillDTO.setGic(Integer.parseInt(incomeTaxMasterBean.getGic()));
		 incomeTaxPayBillDTO.setCanfin(Integer.parseInt(incomeTaxMasterBean.getCanfin()));
		 incomeTaxPayBillDTO.setiTax(Integer.parseInt(incomeTaxMasterBean.getiTax()));
		 incomeTaxPayBillDTO.setCess(incomeTaxMasterBean.getCess());
		 incomeTaxPayBillDTO.setSecondaryCess(incomeTaxMasterBean.getSecondaryCess());
		 
		// incomeTaxPayBillDTO.setCreationDate(new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())));
		 incomeTaxPayBillDTO.setModifiedDate(new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())));
		 //incomeTaxPayBillDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
		 incomeTaxPayBillDTO.setModifiedBy(incomeTaxMasterBean.getSfID());
		 incomeTaxPayBillDTO.setRemarks(incomeTaxMasterBean.getPayRemarks());
		
		 
		 //int val = (Integer)session.save(incomeTaxPayBillDTO);
		 session.saveOrUpdate(incomeTaxPayBillDTO);
		 session.flush();
		
		 message = CPSConstants.SUCCESS;
		 //incomeTaxPayBillDTO.setFPAY
		 hibernateUtils.closeSession();
		
		 //incomeTaxPayBillDTO.setCCS
		 
		 
		 //incomeTaxPayBillDTO.setARR(it set to 0)
		 
		 
		 
		 //incomeTaxPayBillDTO.setITAX(add incomeTax,cess,secondaryCess)
		
		 
		 
		 
		 
		 //incomeTaxPayBillDTO.setHDFC
		 //incomeTaxPayBillDTO.setGIC
		 //incomeTaxPayBillDTO.setCANFIN
		
	}catch (Exception e) {
		e.printStackTrace();
		System.out.println("fdsfklj");
		message = CPSConstants.FAILED;
		throw e;
	}
	return message;
}
public int getRunId() throws Exception{
	int id=0;
	Session session=null;
	try{
		session = hibernateUtils.getSession();
		String qry = "select max(run_id) from pay_it_paybill_details";
		 Object obj=session.createSQLQuery(qry).uniqueResult();
		 //id=(Integer)obj;
		  //BigDecimal bg = (BigDecimal)obj;
		 id=((BigDecimal)obj).intValue();
		 //id=(In)
	}catch(Exception e){
		throw e;
	}
	return id;
}
@SuppressWarnings("unchecked")
public String submitProjectedDetails2(IncomeTaxMasterBean incomeTaxMasterBean)throws Exception{
	String message="";
	Session session=null;
	List<PayBillUserDTO> empList = null;
	Date fromMonth=null;
	Date toMonth=null;
	List<PayBillDTO> payList=null;
	try{
		session=hibernateUtils.getSession();
		incomeTaxMasterBean.setRunId(getTableID("INCOMETAX_RUN_ID"));
		
		List<IncomeTaxRunStatusDTO> statusList=	session.createCriteria(IncomeTaxRunStatusDTO.class).add(Expression.eq("finYearId", Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()))).add(Expression.eq("ProjectedStatus", 1)).list();
		
		if (statusList.size()>0) {
			message = CPSConstants.FAILED;
		}else{
			message=calculateIncomeTaxPlPrActl.submitITStatusDetails(incomeTaxMasterBean);
		}

		if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
		
			int remainingNoOfMonths=((BigDecimal)session.createSQLQuery(" Select Months_Between((Select To_Date('01-MAR-'||To_Year) From "+
                    " Pay_IT_Fin_Year_Master where id=?),(Select To_Date(Run_Month) From "+
					" (Select Run_Month,Rownum  Rowno From (Select Run_Month From Paybill_Status_Details  "+
					" Order By To_Date(Run_Month) Desc) Tab) Where Rowno=1)) Months From Dual").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
	        
			Date startRecoveryMonth=(Date)session.createSQLQuery(" select add_months((Select To_Date(Run_Month) From (Select Run_Month,Rownum "+
                    " Rowno From (Select Run_Month From Paybill_Status_Details "+
                    " order by to_date(run_month) desc) tab) where rowno=1),1) from dual").uniqueResult();

			fromMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select from_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
			toMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select to_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
			
			empList =calculateIncomeTaxPlPrActl.getEmployeeList(fromMonth,toMonth);
	  
	         for(int i=0;i<empList.size();i++){
	        	 
	        	 PayBillUserDTO payBillUserDTO=empList.get(i);
			        String sfid=payBillUserDTO.getSfID();
			        System.out.println(sfid);
		        
		        IncomeTaxRunDTO incomeTaxRunDTO =new IncomeTaxRunDTO();
		        incomeTaxRunDTO.setSfid(sfid);
		        incomeTaxRunDTO.setRunType(CPSConstants.ITPROJECTEDFLAG);
		        incomeTaxRunDTO.setRunId(incomeTaxMasterBean.getRunId());
		        incomeTaxRunDTO.setStatus(1);
		        incomeTaxRunDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
		        incomeTaxRunDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
		        incomeTaxRunDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
		        incomeTaxRunDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
		        incomeTaxRunDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
		        incomeTaxMasterBean.setIncomeTaxRunDTO(incomeTaxRunDTO);
		        Calendar cal =Calendar.getInstance();
				cal.set(calculateIncomeTaxPlPrActl.getStartingMonthOfFinYear(incomeTaxMasterBean.getSelectedFYear()), 03, 01);
				incomeTaxMasterBean.setPayBillList(calculateIncomeTaxPlPrActl.getProjectionPayBillList1(sfid,fromMonth,toMonth));
				/*//-------my code start----------//
				payList=calculateIncomeTaxPlPrActl.getProjectionPayBillListed(empList,fromMonth,toMonth);
				List<PayBillDTO> payList1=new ArrayList<PayBillDTO>();
				for (PayBillDTO payBillDTO : payList) {
					if(CPSUtils.compareStrings(empList.get(i).getSfID().toUpperCase(), payBillDTO.getSfid().toUpperCase())){
						payList1.add(payBillDTO);
					}
				}
				incomeTaxMasterBean.setPayBillList(payList1);
				//--------my code end----------//*/		        	
				incomeTaxMasterBean=calculateIncomeTaxPlPrActl.submitITPayBillDetails(incomeTaxMasterBean);
				incomeTaxRunDTO.setArrearsPaid(0);
				int totalIncome=incomeTaxRunDTO.getTotSal()+incomeTaxRunDTO.getArrearsPaid();
				int grandTotal=totalIncome-(incomeTaxRunDTO.getLessHraEolHpl());
				int finalTotal=grandTotal+incomeTaxRunDTO.getGovtSubs();//1)Pay And Allowances
				
				//2) Exemptions
				
                incomeTaxRunDTO.setTotRentPaid(calculateIncomeTaxPlPrActl.getRentPaid(incomeTaxRunDTO.getSfid(), Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())));
				
				incomeTaxRunDTO.setHraExempted(calculateIncomeTaxPlPrActl.calculateHRAExempted(incomeTaxRunDTO.getTotHra(), incomeTaxRunDTO.getTotRentPaid(),0,0));
				
				incomeTaxRunDTO.setPrUpallw(calculateIncomeTaxPlPrActl.getProfessionalUpdateAllowance(payBillUserDTO.getgPay(), Integer.parseInt(payBillUserDTO.getPayDesignationId())));
				 
				int exemptions=incomeTaxRunDTO.getHraExempted()+incomeTaxRunDTO.getTraAllw()+incomeTaxRunDTO.getPrUpallw();
				int payAndAllowancesAfterExemptions=finalTotal-exemptions;
				
				incomeTaxRunDTO.setTotExemptions(exemptions);
				
				int totalDeductions=calculateIncomeTaxPlPrActl.submitPlannedExemptionsDetails(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotCghs(),incomeTaxMasterBean.getIncomeTaxRunDTO().getTotPTax());
				
				incomeTaxRunDTO.setTotalDeductions(totalDeductions);
				
				int payAndAllowancesAfterDeductions=payAndAllowancesAfterExemptions-(incomeTaxRunDTO.getTotPTax()+totalDeductions);
				
				int totalIncomeFromOtherSources=calculateIncomeTaxPlPrActl.submitPlannedTotalIncomeFromOtherSources(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getRunId(), incomeTaxMasterBean.getSfID());
				 
				incomeTaxRunDTO.setTotOtherIncome(totalIncomeFromOtherSources);
				
				int totalIncomeAfterOtherIncome=payAndAllowancesAfterDeductions+totalIncomeFromOtherSources;
				
				int totalSavings=calculateIncomeTaxPlPrActl.submitPlannedTotalSavings(sfid, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()), incomeTaxRunDTO.getTotCegis(), incomeTaxRunDTO.getTotPli(), 0, incomeTaxRunDTO.getGovtSubs(), incomeTaxMasterBean.getRunTypeFlag(), incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getRunId());
				
				totalSavings+=incomeTaxRunDTO.getGovtSubs();
				
				incomeTaxRunDTO.setTotSavings(totalSavings);
				
				int taxableIncome=totalIncomeAfterOtherIncome-totalSavings;
				
				incomeTaxRunDTO.setTaxableIncome(taxableIncome);
				
				long roundedOff=Math.round((taxableIncome/10))*10;
				
				incomeTaxRunDTO.setRoundedOff(roundedOff);
				
				int totalIncomeTax=calculateIncomeTax(roundedOff, incomeTaxMasterBean.getSfID(), incomeTaxMasterBean.getSelectedFYear());
				
				incomeTaxRunDTO.setTotIncomeTax(totalIncomeTax);
				
				int educationCess=totalIncomeTax+((int)(0.03*totalIncomeTax));
				
				incomeTaxRunDTO.setAddEduCess(educationCess);
				
				int recoveredIncomeTax=incomeTaxRunDTO.getTaxRecovered();
				
				incomeTaxRunDTO.setTaxToBeRecovered(educationCess-recoveredIncomeTax);
				
				incomeTaxRunDTO.setTaxPerMonth(incomeTaxRunDTO.getTaxToBeRecovered()/remainingNoOfMonths);
				
				incomeTaxRunDTO.setEffMonth(startRecoveryMonth);
			
				
				session.saveOrUpdate(incomeTaxRunDTO);
		      }
	     }
	}catch (Exception e) {
		throw e;
	}
	return message;
}
public int getRunMonthId(String date) throws Exception{
	int runid;
	try{
		Session session=hibernateUtils.getSession();
		 runid=((BigDecimal)session.createSQLQuery("select DISTINCT run_id from PAY_TXN_DETAILS where run_month='"+date+"'").uniqueResult()).intValue();
	}catch(Exception e){
		throw e;
	}
	return runid;
}

public IncomeTaxMasterBean getITTotals(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
	Date fromMonth=null;
	Date toMonth=null;
	try{
		Session session=hibernateUtils.getSession();
		fromMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select from_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
		toMonth=(Date)session.createSQLQuery("select To_Date('01-MAR-'||(Select to_Year From Pay_It_Fin_Year_Master Where Id=?)) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult();
      // System.out.println("from month"+fromMonth);
      // System.out.println("to month"+toMonth);
		//for pay details
		int totSal=((BigDecimal)session.createSQLQuery("select get_it_pay_bill_totals(?,?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).setString(3, "TOTAL_CREDITS").uniqueResult()).intValue();
      // int totSal=((BigDecimal)session.createSQLQuery("select CAL_SUM_FOR_IT(?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setString(1,incomeTaxMasterBean.getSelectedFYear()).setString(2,"credits").uniqueResult()).intValue();
      
       //System.out.println("total salaries"+totSal);
		
		int arrearsPaid=((BigDecimal)session.createSQLQuery("Select calculate_tot_arrears_paid(?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
		//System.out.println("arrears paid"+arrearsPaid);
		int totalIncome=totSal+arrearsPaid;
		 //System.out.println("total income totalsal+arrearspaid "+totalIncome);
		 
		 // you have to see once .
		int hraEolHpl=((BigDecimal)session.createSQLQuery("select calculatet_it_LessHraEolHpl(?,?,?) from dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).uniqueResult()).intValue();
		int grandTot=totalIncome-hraEolHpl;
		//System.out.println("grand total total income - hra eolhpl"+grandTot);
		int totGpf=((BigDecimal)session.createSQLQuery("select get_it_pay_bill_totals(?,?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).setString(3, "GPF").uniqueResult()).intValue();
		//System.out.println("total gpf"+totGpf);
		int finalTot=grandTot+totGpf;
		
		incomeTaxMasterBean.setTotSal(totSal);
		incomeTaxMasterBean.setArrearsPaid(arrearsPaid);
		incomeTaxMasterBean.setTotIncome(totalIncome);
		incomeTaxMasterBean.setTotHraEolHpl(hraEolHpl);
		incomeTaxMasterBean.setGrandTot(grandTot);
		incomeTaxMasterBean.setTotGpf(totGpf);
		incomeTaxMasterBean.setFinalTot(finalTot);
		
		//for rent
		//commented by naagendra.v
	/*	int totRentPaid=((BigDecimal)session.createSQLQuery("select Calculate_Rent_Paid(?,?) from dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
		int totHra=((BigDecimal)session.createSQLQuery("select get_it_pay_bill_totals(?,?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).setString(3, "HRA").uniqueResult()).intValue();
		int hraExempted =0;
		if(totHra<totRentPaid){
			 hraExempted =totHra;
		}else{
			 hraExempted =totRentPaid;
		}*/
		int hraExempted =((BigDecimal)session.createSQLQuery("select cal_hra(?,?) from dual").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).setString(1, incomeTaxMasterBean.getSearchSfid()).uniqueResult()).intValue();;
		// System.out.println("hra value"+hraExempted);
		int traAllw=((BigDecimal)session.createSQLQuery("select Calculatet_It_Traallw(?) from dual").setString(0, incomeTaxMasterBean.getSearchSfid()).uniqueResult()).intValue();
		int prUpAllw=((BigDecimal)session.createSQLQuery("select Calculatet_It_Profesionalupalw(?) from dual").setString(0, incomeTaxMasterBean.getSearchSfid()).uniqueResult()).intValue();
		int payAlwAfterExemptions= finalTot-(hraExempted+traAllw+prUpAllw);
		
		incomeTaxMasterBean.setHraExempted(hraExempted);
		incomeTaxMasterBean.setTraAllw(traAllw);
		incomeTaxMasterBean.setPrUpallw(prUpAllw);
		incomeTaxMasterBean.setPayAlwAfterExemption(payAlwAfterExemptions);
		
		//for deductions
		int totCGHS=((BigDecimal)session.createSQLQuery("select get_it_pay_bill_totals(?,?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).setString(3, "CGHS").uniqueResult()).intValue();
		int totProfTax=((BigDecimal)session.createSQLQuery("select get_it_pay_bill_totals(?,?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).setString(3, "PROF_TAX").uniqueResult()).intValue();
		
 		int totalDeductions=((BigDecimal) session.createSQLQuery("select NVL(SUM(actual),0) from pay_it_savings_type_master a,"
 				+ " pay_it_section_mapping_master b,pay_itax_config_details c"
 				+ " where c.sfid=? and c.savings_type_id=b.id and"
 				+ " b.section_id=a.id  and c.status=1 and a.savings_type='Exemptions' and c.selected_fyear=?")
 				.setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
 				
 		int toalDedutionAsPerRules=	((BigDecimal) session.createSQLQuery("SELECT  NVL(SUM(res),0) from"
 				+ " (select CASE  WHEN least(c.actual, b.limit) = 0"
 				+ " THEN least(c.actual, c.actual)"
 				+ " ELSE least(c.actual, b.limit)"
 				+ " END res "
 				+ " FROM pay_it_savings_type_master a,  pay_it_section_mapping_master b, pay_itax_config_details c "
 				+ " WHERE c.sfid= ?"
 				+ " AND c.savings_type_id=b.id AND b.section_id=a.id AND c.status=1 AND a.savings_type='Exemptions'"
 				+ " AND c.selected_fyear =?)")
 				.setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
 				
 				//System.out.println("total dedutions as per income tax rules"+toalDedutionAsPerRules);
 		 				/*((BigDecimal)session.createSQLQuery("select calculate_it_deductions(?,?,?,?,?,?,?) from dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).setString(2, "pl").setString(3,incomeTaxMasterBean.getSfID()).setInteger(4, totCGHS).setInteger(5, totProfTax).setString(6, "Exemptions").uniqueResult()).intValue();*/
		int payAndAllAfterDeductions=payAlwAfterExemptions-totalDeductions;
		
		incomeTaxMasterBean.setTotPTax(totProfTax);
		incomeTaxMasterBean.setTotDeductions(totalDeductions);
		incomeTaxMasterBean.setPayAllowAfterDeductions(payAndAllAfterDeductions);
		
		//for other income
		//int totalIncomeFromOtherSources=((BigDecimal)session.createSQLQuery("select calculate_it_income_from_other(?,?,?,?,?) from dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).setString(2, "pl").setString(3, incomeTaxMasterBean.getSfID()).setString(4, "Other Income Sources").uniqueResult()).intValue();
		int totalIncomeFromOtherSources=((BigDecimal) session.createSQLQuery("select NVL(SUM(actual),0) from pay_it_savings_type_master a,"
 				+ " pay_it_section_mapping_master b,pay_itax_config_details c"
 				+ " where c.sfid=? and c.savings_type_id=b.id and"
 				+ " b.section_id=a.id  and c.status=1 and a.savings_type='Other Income Sources' and c.selected_fyear=?")
 				.setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
 		System.out.println(totalIncomeFromOtherSources);
		int totIncomeWithOther=payAndAllAfterDeductions+totalIncomeFromOtherSources;
		
		incomeTaxMasterBean.setIncomeFromOther(totalIncomeFromOtherSources);
		incomeTaxMasterBean.setTotalIncomeWithOther(totIncomeWithOther);
		
		//for savings
		int totalSavings=((BigDecimal) session.createSQLQuery("select NVL(SUM(actual),0) from pay_it_savings_type_master a, pay_it_section_mapping_master b,"
				+ " pay_itax_config_details c where c.sfid=? and c.savings_type_id=b.id and b.section_id=a.id "
				+ " and c.status=1 and a.savings_type='Savings' and c.selected_fyear=?")
				.setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
		incomeTaxMasterBean.setSavingstotal(totalSavings);
		int totCgeis=((BigDecimal)session.createSQLQuery("select get_it_pay_bill_totals(?,?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).setString(3, "CGEIS").uniqueResult()).intValue();
		int pli=0;
		int tuitionFee=0;
		/*int totalSavings=((BigDecimal)session.createSQLQuery("select Calculate_It_Tot_Savings(?,?,?,?,?,?,?,?,?) from dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setInteger(1, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).setInteger(2, totCgeis).setInteger(3, pli).setInteger(4, tuitionFee).setInteger(5, totGpf).setString(6, "pl").setString(7, incomeTaxMasterBean.getSfID()).setString(8, "Savings").uniqueResult()).intValue();*/
		totalSavings+=totGpf;
		int taxableIncome=totIncomeWithOther-totalSavings;
		int roundedOff=(taxableIncome/10)*10;
		int totIncomeTax=((BigDecimal)session.createSQLQuery("select Calculate_It_Final_Itax(?,?,?) from dual").setString(0, String.valueOf(roundedOff)).setString(1, incomeTaxMasterBean.getSearchSfid()).setInteger(2, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
		int educationCess=Math.round((totIncomeTax*3)/100f);
		int itRecovred=((BigDecimal)session.createSQLQuery("select get_it_pay_bill_totals(?,?,?,?) From Dual").setString(0, incomeTaxMasterBean.getSearchSfid()).setDate(1, fromMonth).setDate(2, toMonth).setString(3, "IT_REC").uniqueResult()).intValue();
		
		int remainingNoOfMonths=((BigDecimal)session.createSQLQuery(" Select 12-Count(Runmonth) From (Select Distinct Run_Month Runmonth From Paybill_Status_Details Where "+
                " Run_Month Between To_Date('01-MAR-'||(Select from_Year From Pay_IT_Fin_Year_Master where id=?)) and to_Date('01-MAR-'||(Select to_Year From  "+
                " Pay_IT_Fin_Year_Master where id=?)))").setInteger(0, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).setInteger(1, Integer.valueOf(incomeTaxMasterBean.getSelectedFYear())).uniqueResult()).intValue();
		int taxPerMonth=0;
				/*((totIncomeTax+educationCess)-itRecovred)/remainingNoOfMonths;*/
		
		incomeTaxMasterBean.setTotSavings(totalSavings);
		incomeTaxMasterBean.setTaxableIncome(taxableIncome);
		incomeTaxMasterBean.setRoundedOff(roundedOff);
		incomeTaxMasterBean.setTotIncomeTax(totIncomeTax);
		incomeTaxMasterBean.setEducationCess(educationCess);
		incomeTaxMasterBean.setTotIncomeTaxRecovered(itRecovred);
		incomeTaxMasterBean.setTaxPerMonth(taxPerMonth);
		
	}catch(Exception e){
		throw e;
	}
	return incomeTaxMasterBean;
}


public String submitITDurationDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
	Session session=null;
	String message="";
	
try
{
	 session=hibernateUtils.getSession();
	 SimpleDateFormat sd=new SimpleDateFormat("yyyy");
	 PayFinYearDTO payFinYearDTO=(PayFinYearDTO)session.createCriteria(PayFinYearDTO.class).add(Expression.eq("id", Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()))).uniqueResult();
	String qry="Select Count(*) From Income_Tax_Duration_Details Where Fin_Year_Id=? And Run_Type=?  and Status=1 or" +
			   " (? Between From_Date and To_Date) or (? Between From_Date and To_Date)";
	 int existingRecords=((BigDecimal)session.createSQLQuery(qry).setInteger(0, Integer.parseInt(incomeTaxMasterBean.getSelectedFYear())).setString(1, incomeTaxMasterBean.getRunTypeFlag()).setDate(2, incomeTaxMasterBean.getFromDate()).setDate(3, incomeTaxMasterBean.getToDate()).uniqueResult()).intValue();
	 
	 int fromYear=Integer.parseInt(sd.format(incomeTaxMasterBean.getFromDate()));
	 int toYear=Integer.parseInt(sd.format(incomeTaxMasterBean.getToDate()));
	 
	 //check fromdate and to dates in between fin year fromdate and todate
	 if((fromYear>=payFinYearDTO.getFyFrom() && fromYear<=payFinYearDTO.getFyTo()) && (toYear>=payFinYearDTO.getFyFrom() && toYear<=payFinYearDTO.getFyTo())){
/*		 if(existingRecords==0){
		 IncomeTaxDurationDTO incomeTaxDurationDTO = new IncomeTaxDurationDTO();
	 if(!incomeTaxMasterBean.getPk().equals("")){
		 incomeTaxDurationDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
	 }
	 incomeTaxDurationDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
	 incomeTaxDurationDTO.setRunType(incomeTaxMasterBean.getRunTypeFlag());
	 incomeTaxDurationDTO.setFromDate(incomeTaxMasterBean.getFromDate());
	 incomeTaxDurationDTO.setToDate(incomeTaxMasterBean.getToDate());
	 incomeTaxDurationDTO.setStatus(1);
	 session.saveOrUpdate(incomeTaxDurationDTO);
	 if(!incomeTaxMasterBean.getPk().equals("")){
		 message = CPSConstants.UPDATE;
	 }else{
		 message = CPSConstants.SUCCESS; 
	 }
	}else{
			 message = CPSConstants.DUPLICATE;  
		 }*/
		 if(!incomeTaxMasterBean.getPk().equals("")){
			 if(existingRecords<2){
				 IncomeTaxDurationDTO incomeTaxDurationDTO = new IncomeTaxDurationDTO();
				 incomeTaxDurationDTO.setId(Integer.parseInt(incomeTaxMasterBean.getPk()));
				 incomeTaxDurationDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
				 incomeTaxDurationDTO.setRunType(incomeTaxMasterBean.getRunTypeFlag());
				 incomeTaxDurationDTO.setFromDate(incomeTaxMasterBean.getFromDate());
				 incomeTaxDurationDTO.setToDate(incomeTaxMasterBean.getToDate());
				 incomeTaxDurationDTO.setStatus(1);
				 session.saveOrUpdate(incomeTaxDurationDTO);
				 message = CPSConstants.UPDATE;
			 }else{
				 message = CPSConstants.DUPLICATE;
			 }
		 }else{
			 if(existingRecords==0){
				 IncomeTaxDurationDTO incomeTaxDurationDTO = new IncomeTaxDurationDTO();
				 incomeTaxDurationDTO.setFinYearId(Integer.parseInt(incomeTaxMasterBean.getSelectedFYear()));
				 incomeTaxDurationDTO.setRunType(incomeTaxMasterBean.getRunTypeFlag());
				 incomeTaxDurationDTO.setFromDate(incomeTaxMasterBean.getFromDate());
				 incomeTaxDurationDTO.setToDate(incomeTaxMasterBean.getToDate());
				 incomeTaxDurationDTO.setStatus(1);
				 session.saveOrUpdate(incomeTaxDurationDTO);
				 session.saveOrUpdate(incomeTaxDurationDTO);
				 message = CPSConstants.SUCCESS; 
			 }else{
				 message = CPSConstants.DUPLICATE;
			 }
		 }
	 }
	 else{
		 message="itDatesBetweenFinYear";
	 }
	 
}catch(Exception e){
	message = CPSConstants.FAILED;
	throw e;

}
return message;
}

// Start: OverTime(OT) implementation

@SuppressWarnings("unchecked")
public List<Integer> getYearList() throws Exception {
	List<Integer> yearList = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		yearList = session.createSQLQuery("select distinct year as id from ot_hrs").addScalar("id", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
	} catch(Exception e) {
		throw e;
	}
	return yearList;
}

@SuppressWarnings("unchecked")
public List<KeyValueDTO> getCategoryList() throws Exception {
	List<KeyValueDTO> catList = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		String sql = "SELECT DISTINCT pecm.id AS id, pecm.category_name AS name FROM pay_txn_master_details ptmd, pay_emp_category_master pecm, ot_hrs oh "
				+ "WHERE ptmd.sfid = oh.sfid AND pecm.id = ptmd.category_id AND pecm.status = 1 ORDER BY id ASC";
		catList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
	} catch(Exception e) {
		throw e;
	}
	return catList;
}

@SuppressWarnings("unchecked")
public List<KeyValueDTO> getDesignations() throws Exception {
	List<KeyValueDTO> desigList = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		String sql = "SELECT DISTINCT dm.id AS id, dm.name AS name FROM pay_txn_master_details ptmd, designation_master dm, ot_hrs oh "
				+ "WHERE ptmd.sfid = oh.sfid AND dm.id = ptmd.pay_designation_id AND dm.status = 1 ORDER BY id";
		desigList = session.createSQLQuery(sql).addScalar("id", Hibernate.INTEGER).addScalar("name", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
	} catch(Exception e) {
		throw e;
	}
	return desigList;
}

public OverTimeVO getOTDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
	OverTimeVO obj = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		int count = ((BigDecimal)session.createSQLQuery("SELECT COUNT(*) FROM emp_master em, pay_txn_details ptd WHERE em.sfid = ? AND em.status = 1 "
				+ "AND ptd.sfid = em.sfid AND ptd.c_grade_pay  between 1 and 4200 AND ptd.status IN (1, 60, 51, 52) AND ptd.run_id = (SELECT MAX(run_id) FROM pay_txn_details)")
				.setString(0, incomeTaxMasterBean.getSearchSfid()).uniqueResult()).intValue();
		if(count != 0) {
			String sql = "SELECT em.sfid AS employeeSfid, em.name_in_service_book AS name, dm.name AS designationName, TO_CHAR(ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra) AS totalPay, "
					+ "oh.shrs AS singleHour, oh.dhrs AS doubleHour, TO_CHAR((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200) AS singleRate, "
					+ "TO_CHAR((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100) AS doubleRate, TO_CHAR(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200) * oh.shrs) AS singleAmount, "
					+ "TO_CHAR(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100) * oh.dhrs) AS doubleAmount, "
					+ "TO_CHAR(ROUND((((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200) * oh.shrs) + (((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100) * oh.dhrs))) AS totalOTAmount, "
					+ "TO_CHAR( ? ||'-'|| ? ) AS month FROM emp_master em, pay_txn_details ptd, pay_txn_master_details ptmd, designation_master dm, ot_hrs oh "
					+ "WHERE em.sfid = ? AND ptd.sfid = em.sfid AND ptmd.sfid = ptd.sfid AND dm.id = ptmd.pay_designation_id "
					+ "AND ptmd.run_month = (SELECT to_date('01-' ||MONTH || YEAR, 'dd-Mon-yy') FROM ot_hrs WHERE MONTH = ? AND YEAR = ? "
					+ "AND sfid = em.sfid) AND ptd.run_month = ptmd.run_month AND ptd.status IN (1, 60, 51, 52) AND ptd.c_grade_pay BETWEEN 1 AND 4200 "
					+ "AND oh.sfid  = ptd.sfid AND oh.month = ? AND oh.year = ?";
			obj = (OverTimeVO)session.createSQLQuery(sql).addScalar("employeeSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING)
				.addScalar("totalPay", Hibernate.STRING).addScalar("singleHour", Hibernate.STRING).addScalar("doubleHour", Hibernate.STRING)
				.addScalar("singleRate", Hibernate.STRING).addScalar("doubleRate", Hibernate.STRING).addScalar("singleAmount", Hibernate.STRING)
				.addScalar("doubleAmount", Hibernate.STRING).addScalar("totalOTAmount", Hibernate.STRING).addScalar("month", Hibernate.STRING)
				.setString(0, incomeTaxMasterBean.getPayMonth()).setInteger(1, incomeTaxMasterBean.getYear())
				.setString(2, incomeTaxMasterBean.getSearchSfid()).setString(3, incomeTaxMasterBean.getPayMonth()).setInteger(4, incomeTaxMasterBean.getYear())
				.setString(5, incomeTaxMasterBean.getPayMonth()).setInteger(6, incomeTaxMasterBean.getYear())
				.setResultTransformer(Transformers.aliasToBean(OverTimeVO.class)).uniqueResult();
		}
	} catch(Exception e) {
		throw e;
	}
	return obj;
}

@SuppressWarnings("unchecked")
public List<OverTimeVO> getOTDetailsList(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
	List<OverTimeVO> list = null;
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		if(!CPSUtils.isNullOrEmpty(incomeTaxMasterBean.getCategoryId())) {
			String sql = "SELECT em.sfid AS employeeSfid, em.name_in_service_book AS name, dm.name AS designationName, "
				+ "TO_CHAR(ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra) AS totalPay, oh.shrs AS singleHour, oh.dhrs AS doubleHour, "
				+ "TO_CHAR((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200) AS singleRate, TO_CHAR((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100) AS doubleRate, "
				+ "TO_CHAR(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200)*oh.shrs) AS singleAmount, TO_CHAR(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100)*oh.dhrs) AS doubleAmount, "
				+ "TO_CHAR(ROUND((((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200)*oh.shrs)+(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100)*oh.dhrs))) AS totalOTAmount, "
				+ "TO_CHAR(?||'-'||?) AS MONTH FROM pay_txn_master_details ptmd, pay_txn_details ptd, emp_master em, designation_master dm, ot_hrs oh "
				+ "WHERE ptmd.category_id = ? AND em.sfid = ptmd.sfid AND em.status = 1 AND ptd.sfid = em.sfid AND ptd.status IN (1, 60, 51, 52 ) "
				+ "AND ptd.run_month = (SELECT DISTINCT to_date('01-'||MONTH||YEAR, 'dd-MON-yy') FROM ot_hrs WHERE MONTH = ? AND YEAR = ?) AND ptmd.run_month = ptd.run_month "
				+ "AND oh.sfid = em.sfid AND oh.sfid not in (select sfid from ot_txn_details where month = ?||'-'||?) AND oh.month = ? AND oh.year = ? "
				+ "AND dm.id = em.designation_id AND dm.status = 1 AND ptd.c_grade_pay BETWEEN 1 AND 4200 ORDER BY dm.name";
			
			list = session.createSQLQuery(sql).addScalar("employeeSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING)
				.addScalar("totalPay", Hibernate.STRING).addScalar("singleHour", Hibernate.STRING).addScalar("doubleHour", Hibernate.STRING)
				.addScalar("singleRate", Hibernate.STRING).addScalar("doubleRate", Hibernate.STRING).addScalar("singleAmount", Hibernate.STRING)
				.addScalar("doubleAmount", Hibernate.STRING).addScalar("totalOTAmount", Hibernate.STRING).addScalar("month", Hibernate.STRING)
				.setString(0, incomeTaxMasterBean.getPayMonth()).setInteger(1, incomeTaxMasterBean.getYear())
				.setString(2, incomeTaxMasterBean.getCategoryId()).setString(3, incomeTaxMasterBean.getPayMonth()).setInteger(4, incomeTaxMasterBean.getYear())
				.setString(5, incomeTaxMasterBean.getPayMonth()).setInteger(6, incomeTaxMasterBean.getYear())
				.setString(7, incomeTaxMasterBean.getPayMonth()).setInteger(8, incomeTaxMasterBean.getYear())
				.setResultTransformer(Transformers.aliasToBean(OverTimeVO.class)).list();
		} else {
			String sql = "SELECT em.sfid AS employeeSfid, em.name_in_service_book AS name, dm.name AS designationName, TO_CHAR(ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra) AS totalPay, "
					+ "oh.shrs AS singleHour, oh.dhrs AS doubleHour, TO_CHAR((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200) AS singleRate, "
					+ "TO_CHAR((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100) AS doubleRate, TO_CHAR(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200)*oh.shrs) AS singleAmount, "
					+ "TO_CHAR(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100)*oh.dhrs) AS doubleAmount, "
					+ "TO_CHAR(ROUND((((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/200)*oh.shrs)+(((ptd.c_basic_pay + ptd.c_grade_pay + ptd.c_da + ptd.c_hra)/100)*oh.dhrs))) AS totalOTAmount, "
					+ "TO_CHAR(?||'-'||?) AS MONTH FROM pay_txn_master_details ptmd, pay_txn_details ptd, emp_master em, designation_master dm, ot_hrs oh "
					+ "WHERE ptmd.pay_designation_id = ? AND em.sfid = ptmd.sfid AND em.status = 1 AND ptd.sfid = em.sfid AND ptd.status IN (1, 60, 51, 52 ) "
					+ "AND ptd.run_month = (SELECT DISTINCT to_date('01-'||MONTH||YEAR, 'dd-MON-yy') FROM ot_hrs WHERE MONTH = ? AND YEAR = ?) AND ptmd.run_month = ptd.run_month "
					+ "AND oh.sfid = em.sfid AND oh.sfid NOT IN (SELECT sfid FROM ot_txn_details WHERE MONTH = ?||'-'||?) AND oh.month = ? AND oh.year = ? AND dm.id = em.designation_id AND ptd.c_grade_pay BETWEEN 1 AND 4200 ORDER BY dm.name";
			
			list = session.createSQLQuery(sql).addScalar("employeeSfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("designationName", Hibernate.STRING)
					.addScalar("totalPay", Hibernate.STRING).addScalar("singleHour", Hibernate.STRING).addScalar("doubleHour", Hibernate.STRING)
					.addScalar("singleRate", Hibernate.STRING).addScalar("doubleRate", Hibernate.STRING).addScalar("singleAmount", Hibernate.STRING)
					.addScalar("doubleAmount", Hibernate.STRING).addScalar("totalOTAmount", Hibernate.STRING).addScalar("month", Hibernate.STRING)
					.setString(0, incomeTaxMasterBean.getPayMonth()).setInteger(1, incomeTaxMasterBean.getYear())
					.setString(2, incomeTaxMasterBean.getDesignationId()).setString(3, incomeTaxMasterBean.getPayMonth()).setInteger(4, incomeTaxMasterBean.getYear())
					.setString(5, incomeTaxMasterBean.getPayMonth()).setInteger(6, incomeTaxMasterBean.getYear())
					.setString(7, incomeTaxMasterBean.getPayMonth()).setInteger(8, incomeTaxMasterBean.getYear())
					.setResultTransformer(Transformers.aliasToBean(OverTimeVO.class)).list();
		}	
	} catch(Exception e) {
		throw e;
	}
	return list;
}

public String saveOTDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
	String message = "";
	Session session = null;
	try
	{
		session = hibernateUtils.getSession();
		JSONObject jsonObj = new JSONObject(incomeTaxMasterBean.getOtDetailsList());
		for(int i = 0; i < jsonObj.length(); i++) {
			OverTimeDTO otDTO = new OverTimeDTO();
			JSONObject obj = (JSONObject)jsonObj.get(String.valueOf(i));
			otDTO.setSfid(obj.getString("sfid"));
			otDTO.setSingleHours(obj.getString("shours"));
			otDTO.setDoubleHours(obj.getString("dhours"));
			otDTO.setSingleRate(obj.getString("srate"));
			otDTO.setDoubleRate(obj.getString("drate"));
			otDTO.setSingleAmount(obj.getString("samount"));
			otDTO.setDoubleAmount(obj.getString("damount"));
			otDTO.setTotalOTAmount(Integer.parseInt(obj.getString("otAmount")));
			otDTO.setMonth(obj.getString("month"));
			otDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			otDTO.setCreatedBy(incomeTaxMasterBean.getSfID());
			otDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			otDTO.setLastModifiedBy(incomeTaxMasterBean.getSfID());
			int dup = ((BigDecimal)session.createSQLQuery("select count(*) from ot_txn_details where sfid = ? and month = ?").setString(0, otDTO.getSfid()).setString(1, otDTO.getMonth()).uniqueResult()).intValue();
			if(dup == 0) {
				session.saveOrUpdate(otDTO);
				session.flush();
			}
		}
		session.clear();
		message = CPSConstants.SUCCESS;
	} catch(Exception e) {
		message = CPSConstants.FAILED;
		throw e;
	}
	return message;
}

// End: OT

@SuppressWarnings("unchecked")
public List<IncomeTaxDurationDTO> getITDurationDetails() throws Exception{
	List<IncomeTaxDurationDTO> itDurationList=null;
	Session session=null;
	try{
		session=hibernateUtils.getSession();
		itDurationList=session.createCriteria(IncomeTaxDurationDTO.class).add(Expression.eq("status", 1)).list();
	}catch(Exception e){
		throw e;
	}
	return itDurationList;
}
public String deleteITDurationDetails(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception{
	Session session=null;
	String message="";
	try {
		session=hibernateUtils.getSession();
		session.createQuery("update IncomeTaxDurationDTO set status=0 where id=?").setInteger(0, Integer.parseInt(incomeTaxMasterBean.getPk())).executeUpdate();
		message = CPSConstants.DELETE;
	} catch(Exception e) {
		message = CPSConstants.FAILED;
		throw e;
	}
	return message;
}





	@SuppressWarnings("unchecked")
	@Override
	
	// method for fecting the section id and section name  and populate the list in incometaxmappingmaster screen
	public List<IncomeTaxSavingsDTO> getItSavedSections(Integer year)
			throws Exception {
		
		
		
		  Session session=null;
		  List<IncomeTaxSavingsDTO> savingsections=null;
		  String query=null;
		  try {
			  session = hibernateUtils.getSession();
			/*  query="SELECT pistm.id as pk,pistm.section_id as sectionId,pism.section_name as secName,pistm.savings_name FROM "
			  		+ "pay_it_savings_type_master pistm, pay_it_section_master pism "
			  		+ "WHERE pistm.section_id = pism.id  and pism.status=1";*/
			 //NAGENDRA.V 
			  query="SELECT pistm.id     AS sectionId,pism.section_name AS secName"
			  		+ "  FROM pay_it_savings_type_master pistm, pay_it_section_master pism"
			  		+ "  WHERE pistm.section_id = pism.id"
			  		+ "  AND pism.status        =1";
			  savingsections=session.createSQLQuery(query).addScalar("sectionId",Hibernate.INTEGER)
				/*.addScalar("pk",Hibernate.INTEGER)*/
					  .addScalar("secName",Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(IncomeTaxSavingsDTO.class)).list();
			
			  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  
		  
		
		return savingsections;
		
		
			}
	
	public Integer getSectionId( int forgienkey) throws Exception{
		Integer forgienkeyvalue=null;
		String query=null;
		Session session=null;
				
		 try {
			/*  session = hibernateUtils.getSession();
			  query="select id  from pay_it_savings_type_master where section_id="+forgienkey;
			
			  forgienkeyvalue=Integer.valueOf((session.createSQLQuery(query)));*/
			
			  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  

		
		return null;
		
		
	}
//NAaGENDRA.V
	@Override
	public String getkey(String savingtypeid) throws Exception {
		String forgienkeyvalue=null;
		String query=null;
		Session session=null;
				
		 try {
			  session = hibernateUtils.getSession();
			  query="select id  from pay_it_savings_type_master where section_id="+savingtypeid;
			
			/*  forgienkeyvalue=String.valueOf(session.createSQLQuery();*/
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  

		
		return forgienkeyvalue;
	}

	//NaAGENDRA.V
	@SuppressWarnings("unchecked")
	public List<IncomeTaxSavingsDTO> getSectionName(String sectionId) throws Exception {
		List<IncomeTaxSavingsDTO> sectionName=null;
		
		Session session=null;
				
		 try {
			  session = hibernateUtils.getSession();
		/*	  query="select id,savings_name from pay_it_savings_type_master where section_id=1 "+sectionId;*/
			
			  sectionName=session.createCriteria(IncomeTaxSavingsDTO.class)
					  .add(Expression.eq("sectionId",Integer.valueOf(sectionId )))
					  .add(Expression.eq("status",1)).list();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sectionName;
		  
	}
	/*	
		return sectionName;
	}
  */
	// method is resposiable for getting the unique section name 

//NAaGENDRA.V	
	@SuppressWarnings("unchecked")
	public List<IncomeTaxSectionDTO> getdistinctSections() throws Exception {
		List<IncomeTaxSectionDTO> sectionlist=null;
		String query=null;
		Session session=null;
				
		 try {
			  session = hibernateUtils.getSession();
			  query="SELECT section_name as secName,id as id FROM pay_it_section_master"
			  		+ "	WHERE id IN"
			  		+ "	(SELECT DISTINCT(section_id) FROM pay_it_savings_type_master where status=1)";

			
			  sectionlist=session.createSQLQuery(query).addScalar("secName",Hibernate.STRING)
					  .addScalar("id",Hibernate.INTEGER)
					  .setResultTransformer(Transformers.aliasToBean(IncomeTaxSectionDTO.class)).list();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  

		
		return sectionlist;
	}


	
	//NaAGENDRA.V
	//method is responsible  for  check the related child records are configured or not. 
	public Integer chekchild(IncomeTaxMasterBean incometaxbean) throws Exception {
		Integer sectionCount = null;
		String query=null;
		Session session=null;
		
		try {
			session=hibernateUtils.getSession();
			if (incometaxbean.getType().equals("section")){
				query="select count(*) from  pay_it_savings_type_master where section_id=? and status=1";
				System.out.println("primary key values is "+incometaxbean.getPk());
				sectionCount=((BigDecimal) session.createSQLQuery(query).setInteger(0, Integer.valueOf(incometaxbean.getPk()))
						.uniqueResult()).intValue();
			}else if(incometaxbean.getType().equals("savingsections")){				
				query="select count(*) from  pay_it_section_mapping_master where section_id=? and status=1";
		    	System.out.println("primary key values is "+incometaxbean.getPk());
		    	sectionCount=((BigDecimal) session.createSQLQuery(query).setInteger(0, Integer.valueOf(incometaxbean.getPk()))
		    			.uniqueResult()).intValue();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return sectionCount;
	}
	
	//NAGENDRA.V
	// method for feching the senior citizen or not
	  public String checkSeniorCitizen(String Sfid) throws Exception{
			Session session = null;
			String Query=null;
			String value=null;
			try {
				
				session=hibernateUtils.getSession();
				//calling the fn for evaluate person is senior citizan or not by passing sfid
				 Query="select evaluate_seniorcitizen(?) from dual";
				value=(String)session.createSQLQuery(Query).setString(0, Sfid).uniqueResult();
				
				if(value.equals("yes")){
					value="1";
				}
				else if (value.equals("no")) {
					value="0";
					
				}else{
							value="Senior Citizen not found";
					/*value = value.equals("yes")?"1":"0";*/throw new Exception(value);
									
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
				
				// TODO: handle exception
			}
			
		  
		  
		return value;
		  
	  }

	@Override
	public IncomeTaxMasterBean getEmpDetails(
			IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		String Query = null;
		IncomeTaxMasterBean imaster = null;

		try {

			session = hibernateUtils.getSession();
			Query = "select handicap_id as disability,gender as gender from  emp_master "
					+ "where  sfid='"
					+ incomeTaxMasterBean.getSearchSfid()
					+ "'";
			imaster = (IncomeTaxMasterBean) session
					.createSQLQuery(Query)
					.addScalar("gender", Hibernate.STRING)
					.addScalar("disability", Hibernate.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(IncomeTaxMasterBean.class))
					.uniqueResult();
			imaster.setSfID(incomeTaxMasterBean.getSelectSfid());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imaster;
	}

	@SuppressWarnings("unchecked")
	//NAGENDRA.V
	@Override
	// method is resposiable for fecthing list of sections
	// input: seniorCitizen values, incometaxmasterbean,seleted fyear 
	//output: incometaxsectionmappingdto with list.
	public List<IncomeTaxSectionMappingDTO> getRelatedSection(
			IncomeTaxMasterBean incometaxmasterbean,String selectedfyear, String serniorcitizen)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
	   Integer gendervalue=null;
		List<IncomeTaxSectionMappingDTO> EligibleSections =null;
		
		try {
			session = hibernateUtils.getSession();
			
			  if(incometaxmasterbean.getGender().equals("M")|| incometaxmasterbean.getGender().equals("m")){
				  gendervalue=1;
			  }else if (incometaxmasterbean.getGender().equals("F")|| incometaxmasterbean.getGender().equals("f")){
				  gendervalue=0;
			  }
			  else{
				  throw new Exception("gender value is not available");
			  }
		
			  
			 
			EligibleSections=session.createCriteria(IncomeTaxSectionMappingDTO.class)
					.add(Expression.or(Expression.eq("srCitizenFlag",  Integer.valueOf(serniorcitizen)),
										Expression.eq("srCitizenFlag", 2)))
					.add(Expression.or(Expression.eq("genderFlag",gendervalue),
										Expression.eq("genderFlag", 2)))
					.add(Expression.or(Expression.eq("disabilityFlag",Integer.valueOf(incometaxmasterbean.getDisability())),
										Expression.eq("disabilityFlag",2)))
					 .add(Expression.eq("yearTo",Integer.valueOf(selectedfyear)))
					.list();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		return EligibleSections;
	}

	public String saveItArrears(IncomeTaxMasterBean incomeTaxMasterBean) throws Exception {
		String message = "";
		Session session = null;
		ItarrearsDto arrearsdto=null;
		try {
		session=hibernateUtils.getSession();
		arrearsdto=new ItarrearsDto();
		
		arrearsdto.setCpsrecovary(incomeTaxMasterBean.getCpsRec());
		arrearsdto.setAmount(Integer.valueOf(incomeTaxMasterBean.getAmount()));
		arrearsdto.setName(incomeTaxMasterBean.getName());
		arrearsdto.setItrecovary((incomeTaxMasterBean.getITRec()));
		arrearsdto.setSfid(incomeTaxMasterBean.getSearchSfid());
		arrearsdto.setYearid(Integer.valueOf(incomeTaxMasterBean.getSelectedFYear()));
		arrearsdto.setStatus(1);
		if(incomeTaxMasterBean.getPk()!=null && !incomeTaxMasterBean.getPk().equals("")){
			arrearsdto.setId(Integer.valueOf(incomeTaxMasterBean.getPk()));
		 session.update(arrearsdto);
		 session.flush();
		 message ="update";
		}
		else{
			session.save(arrearsdto);
			 session.flush();
			message="success";
			
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItarrearsDto> getItarrears(IncomeTaxMasterBean itmb) {
		// TODO Auto-generated method stub
		List<ItarrearsDto> arrearsdto=null;
		Session session = null;
		String query=null;
		
		try {
			session=hibernateUtils.getSession();
			query="select  id as id , name as name  ,amount as amount ,cps_recovary as cpsrecovary , itax_recovary as itrecovary from it_arrears "
					+ "where sfid='"+itmb.getSearchSfid() +"' and status=1 and YEARID="+itmb.getSelectedFYear() ;
			arrearsdto=	session.createSQLQuery(query).addScalar("name",Hibernate.STRING).addScalar("amount",Hibernate.INTEGER)
			.addScalar("cpsrecovary",Hibernate.STRING).addScalar("itrecovary",Hibernate.STRING).addScalar("id",Hibernate.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(ItarrearsDto.class)).list();
			
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return arrearsdto;
	}
	
	
	
	
	  

}

