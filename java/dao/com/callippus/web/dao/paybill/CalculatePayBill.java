package com.callippus.web.dao.paybill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;
import com.callippus.web.paybill.dto.PayBillCanfinDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayHindiInceDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;

@Service
public class CalculatePayBill {
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private PayBillPreProcess payBillPreProcess;

	public String getDA(int basicpay, int gradepay, String runMonth) throws Exception {
		Session session = null;
		String da = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_da(?,?,?) from dual";

			da = session.createSQLQuery(query).setFloat(0, basicpay).setFloat(1, gradepay).setString(2, runMonth).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return da;
	}

	public String getSplPay(String designationID) throws Exception {
		Session session = null;
		String splPay = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_spacial_pay(?) from dual";
			splPay = session.createSQLQuery(query).setString(0, designationID).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return splPay;
	}

	public String getFpa(int fpaGradepay) throws Exception {
		Session session = null;
		String fpa = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_fpa(?) from dual";
			fpa = session.createSQLQuery(query).setFloat(0, fpaGradepay).uniqueResult().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return fpa;
	}

	public String getTra(int basicPay, String sfid, int gradepay, String runMonth) throws Exception {
		Session session = null;
		String tra = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_tra(?,?,?,?) from dual";
			tra = session.createSQLQuery(query).setInteger(0, basicPay).setString(1, sfid).setInteger(2, gradepay).setString(3, runMonth).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return tra;
	}

	@SuppressWarnings("deprecation")
	public int getHra(int basic, int grade,String sfid,Date runMonth) throws Exception {
		Session session = null;
		int hra = 0;
		int noOfDays=0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			noOfDays=getQuarterFlagForSingleEmployee(sfid, runMonth);
			String query = "select calculate_hra(?,?,?,?) Hra from dual";
		
			hra = Integer.parseInt(session.createSQLQuery(query).setInteger(0, basic).setInteger(1, grade).setInteger(2, noOfDays).setInteger(3, CPSUtils.getNoofDaysInMonth(runMonth.getMonth()+1)).uniqueResult().toString());
    	} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return hra;
	}

	public String getVarIncr(int gradepay, int varIncrPts) throws Exception {
		Session session = null;
		String varIncr = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_var_incr(?,?) from dual";

			varIncr = session.createSQLQuery(query).setFloat(0, gradepay).setFloat(1, varIncrPts).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return varIncr;
	}

	public String getWashAllowance(String sfid) throws Exception {
		Session session = null;
		String washAllow = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_wash_allw(?) from dual";
			washAllow = session.createSQLQuery(query).setString(0, sfid).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return washAllow;
	}

	public String getLicenceFee(String sfid) throws Exception {
		Session session = null;
		String rent = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_licence_fee(?) from dual";

			rent = session.createSQLQuery(query).setString(0, sfid).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return rent;
	}

	public String getCGHS(int gradepay) throws Exception {
		Session session = null;
		String cghs = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_cghs(?) from dual";
			cghs = session.createSQLQuery(query).setFloat(0, gradepay).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return cghs;
	}

	public String getCEGIS(String sfid, int groupid) throws Exception {
		Session session = null;
		String cegis = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_cgeis(?,?) from dual";
			cegis = session.createSQLQuery(query).setString(0, sfid).setInteger(1, groupid).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return cegis;
	}

	public int getITax(int incometax) throws Exception {
		Session session = null;
		int itTax = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_itax(?) from dual";

			itTax = Integer.parseInt(session.createSQLQuery(query).setFloat(0, incometax).uniqueResult().toString());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return itTax;
	}

	public int getCess(int incometax) throws Exception {
		Session session = null;
		int cess = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_cess(?) from dual";

			cess = Integer.parseInt(session.createSQLQuery(query).setFloat(0, incometax).uniqueResult().toString());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return cess;
	}

	public int getSHEC(int incometax) throws Exception {
		Session session = null;
		int shec = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_shec(?) from dual";

			shec = Integer.parseInt(session.createSQLQuery(query).setFloat(0, incometax).uniqueResult().toString());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return shec;
	}

	public String getProfTax(String sfid, int grossPay) throws Exception {
		Session session = null;
		String profTax = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_ptax(?,?) from dual";
			profTax = session.createSQLQuery(query).setString(0, sfid).setInteger(1, grossPay).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return profTax;
	}

	public String getResSecurity(String sfid) throws Exception {
		Session session = null;
		String resSecu = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_res_security(?) from dual";
			resSecu = session.createSQLQuery(query).setString(0, sfid).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return resSecu;
	}

	public int getCcsRecovery(String sfid, int ccsAmount) throws Exception {
		Session session = null;
		int ccsRecovery = 0;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_ccs_rec(?,?) from dual";
			ccsRecovery = Integer.parseInt(session.createSQLQuery(query).setString(0, sfid).setFloat(1, ccsAmount).uniqueResult().toString());

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return ccsRecovery;

	}

	public String getMess(String desigId,String quarterFlag) throws Exception {
		Session session = null;
		String mess = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
            
			String query = "select calculate_mess(?,?) from dual";
			mess = session.createSQLQuery(query).setString(0, desigId).setString(1, quarterFlag).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return mess;

	}

	public String getWaterBill() throws Exception {
		Session session = null;
		String water = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_water_bill(?) from dual";
			water = session.createSQLQuery(query).setDate(0, null).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return water;

	}

	public String getFurnBill() throws Exception {
		Session session = null;
		String furn = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_furn_bill(?) from dual";
			furn = session.createSQLQuery(query).setDate(0, null).uniqueResult().toString();
			
			
			

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return furn;

	}

	public String getRegFund(String desigId) throws Exception {
		Session session = null;
		String regFund = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_reg_fund(?) from dual";
			regFund = session.createSQLQuery(query).setString(0, desigId).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return regFund;

	}

	public String getWelFund(String desigId) throws Exception {
		Session session = null;
		String welFund = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_wel_fund(?) from dual";
			welFund = session.createSQLQuery(query).setString(0, desigId).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return welFund;

	}

	public String getBenFund(String desigId) throws Exception {
		Session session = null;
		String benFund = "";
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			String query = "select calculate_ben_fund(?) from dual";
			benFund = session.createSQLQuery(query).setString(0, desigId).uniqueResult().toString();

		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return benFund;

	}

	@SuppressWarnings("unchecked")
	public Object[] getLoan(String sfid, String loanType, String runMonth, int runId, String userId) throws Exception {
		Session session = null;
		Object[] retValue = new Object[] { 0, 0, 0, 0, null, null, null };
		PayBillLoanDTO payBillLoanDTO = null;
		List<PayBillLoanDTO> payBillLoanList= null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			payBillLoanList = session.createCriteria(PayBillLoanDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("loanType", Integer.parseInt(loanType))).addOrder(
					Order.asc("loanDeduType")).add(Expression.eq("status", 1)).add(Expression.or(Expression.le("recoveryStartDate", CPSUtils.convertStringToDate("01-"+runMonth)), Expression.eq("recoveryStartDate", CPSUtils.convertStringToDate("01-"+runMonth)))).list();
			for(int i=0;i<payBillLoanList.size();i++){
				if (payBillLoanList.size() == 2) {
					i++;
					payBillLoanDTO = payBillLoanList.get(i);
				} else if (CPSUtils.compareStrings(payBillLoanList.get(i).getLoanDeduType(), "P")) {
					payBillLoanDTO = payBillLoanList.get(i);
				}else {
                     // checking interest once again
					    payBillLoanDTO = payBillLoanList.get(i);
						LoanPaymentDTO loanPaymentDTO = (LoanPaymentDTO) session.get(LoanPaymentDTO.class, payBillLoanDTO.getReferenceId());
						if(!CPSUtils.isNullOrEmpty(loanPaymentDTO)){
							payBillLoanDTO.setReferenceId(loanPaymentDTO.getId());//(wrong)insted of setting loan payment id to loan_ref_id set to reference_id
							payBillLoanDTO.setSfid(loanPaymentDTO.getSfID());
							payBillLoanDTO.setLoanDeduType("I");
							payBillLoanDTO.setLoanType(loanPaymentDTO.getLoanTypeID());
							payBillLoanDTO.setEmi((int) loanPaymentDTO.getInterestEmi());
							payBillLoanDTO.setTotalAmt((int) loanPaymentDTO.getInterestAmount());
							payBillLoanDTO.setOutStandAmt((int) loanPaymentDTO.getInterestAmount());
							payBillLoanDTO.setTotalInst(loanPaymentDTO.getSanctionedIntInstalments());
							payBillLoanDTO.setPresentInst(1);
					} 
				}
				retValue[0]=payBillLoanDTO.getEmi();
				retValue[1]=payBillLoanDTO.getTotalInst();
				retValue[2]=payBillLoanDTO.getPresentInst();
				retValue[3]=payBillLoanDTO.getId();
				payBillLoanDTO.setRunId(runId);
				payBillLoanDTO.setLastModifiedBy(userId);
				payBillLoanDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				retValue[4]=payBillLoanDTO;
			}
			
			if(payBillLoanList.size()==0){
				retValue=payBillPreProcess.getLoanFromPreProcess(sfid, loanType, runMonth, userId,runId);
			}
	} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return retValue;
	}

	public Object[] getDeduction(String sfid, String loanType, String runMonth,int runId,String userId) throws Exception {
		Session session = null;
		Object[] retValue = new Object[]{0.0f,0,0,0,null};
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			String query=" Select id as id,sfid as sfid,Deduction_Type as deductionID,Eff_Date as effDate," +
					     " status as status,run_id as runId,reference_id as referenceId,Present_Inst as presentInst," +
					     " Inst_Amount as amount,No_Of_Inst as noOfInst,Created_By as createdBy,Creation_Date as creationTime," +
					     " Modified_By as lastModifiedBy,Modified_Date as lastModifiedTime From Pay_Deductions_Details Where Status=1 And Sfid=? And Deduction_Type=? And "+
                         " To_date(Eff_Date)<=to_date(?)";
			
			PayBillCanfinDTO payBillCanfinDTO=(PayBillCanfinDTO)session.createSQLQuery(query).addScalar("id",Hibernate.INTEGER).
			addScalar("effDate",Hibernate.DATE).addScalar("status",Hibernate.INTEGER).addScalar("runId",Hibernate.INTEGER).addScalar("noOfInst",Hibernate.INTEGER).addScalar("referenceId",Hibernate.INTEGER).addScalar("sfid",Hibernate.STRING).addScalar("deductionID",Hibernate.INTEGER).addScalar("presentInst",Hibernate.INTEGER).addScalar("amount",Hibernate.FLOAT).addScalar("createdBy",Hibernate.STRING).addScalar("creationTime",Hibernate.DATE).addScalar("lastModifiedBy",Hibernate.STRING).addScalar("lastModifiedTime",Hibernate.DATE).setString(0, sfid).setString(1, loanType).setString(2, "01-"+runMonth).setResultTransformer(Transformers.aliasToBean(PayBillCanfinDTO.class)).uniqueResult();
			
			if (!CPSUtils.isNullOrEmpty(payBillCanfinDTO)) {
				retValue[0] = payBillCanfinDTO.getAmount();
				retValue[1] = payBillCanfinDTO.getNoOfInst();
				retValue[2] = payBillCanfinDTO.getPresentInst();
				retValue[3] = payBillCanfinDTO.getId();
				payBillCanfinDTO.setRunId(runId);
				payBillCanfinDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				payBillCanfinDTO.setLastModifiedBy(userId);
				retValue[4] = payBillCanfinDTO;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return retValue;
	}

	public Object[] getHindiIncentive(String sfid,int basicPay,int gradePay, String runMonth) throws Exception {
		Object[] hindiList = new Object[2];
		Session session = null;
		try {
			session = hibernateUtils.getSession();//session = sessionFactory.openSession();
			PayHindiInceDTO payHindiInceDTO = (PayHindiInceDTO) session.createCriteria(PayHindiInceDTO.class).add(Expression.eq("sfid", sfid)).add(
					Expression.le("startDate", session.createSQLQuery("select to_date('01-" + runMonth + "') from dual").uniqueResult())).add(Expression.eq("status", 1)).add(
					Expression.ge("endDate", session.createSQLQuery("select to_date('01-" + runMonth + "') from dual").uniqueResult())).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(payHindiInceDTO)) {
				if (payHindiInceDTO.getTotInst() == payHindiInceDTO.getPresentInst()) {
					payHindiInceDTO.setStatus(2);
				} else {
					payHindiInceDTO.setPresentInst(payHindiInceDTO.getPresentInst() + 1);
				}
				hindiList[0] = session.createSQLQuery("Select Calculate_hindiinc(?,?,?) From Dual").setString(0, sfid).setInteger(1, basicPay).setInteger(2, gradePay).uniqueResult();
				hindiList[1] = payHindiInceDTO;
			} else {
				hindiList[0] = new BigDecimal(0);
				hindiList[1] = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return hindiList;
	}
	/*  
	 * Method meant to return for how many days HRA has to be given by considering occupied Date and vacation Date
	 * 
	 * 
	 */
	@SuppressWarnings("deprecation")
	public int getQuarterFlagForSingleEmployee(String sfid,Date runMonth)throws Exception{
		int noOfDays=0;
		Session session=null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			PayQuarterManagementDTO payQuarterManagementDTO	=(PayQuarterManagementDTO)session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("status", 1)).uniqueResult();
	        if(!CPSUtils.isNullOrEmpty(payQuarterManagementDTO)){
	        	int check=CPSUtils.compareTwoDatesUptoMonth(runMonth, payQuarterManagementDTO.getOccupiedDate());
	        	if(check==0){
	        		noOfDays=payQuarterManagementDTO.getOccupiedDate().getDate()-1;
	        		if(!CPSUtils.isNullOrEmpty(payQuarterManagementDTO.getVacationDate())){
		   	        	 check=CPSUtils.compareTwoDatesUptoMonth(runMonth, payQuarterManagementDTO.getVacationDate());
		   	        	 if(check==0){
		   	        		 noOfDays+=(CPSUtils.getNoofDaysInMonth(runMonth.getMonth()+1))-(payQuarterManagementDTO.getVacationDate().getDate());
		   	        	 }
		        	  }
	        	}else if(check==1){
	        		if(!CPSUtils.isNullOrEmpty(payQuarterManagementDTO.getVacationDate())){
	   	        	 check=CPSUtils.compareTwoDatesUptoMonth(runMonth, payQuarterManagementDTO.getVacationDate());
	   	        	 if(check==0){
	   	        		 noOfDays+=(CPSUtils.getNoofDaysInMonth(runMonth.getMonth()+1))-(payQuarterManagementDTO.getVacationDate().getDate());
	   	        	 }else if(check==1){
	   	        		noOfDays=CPSUtils.getNoofDaysInMonth(runMonth.getMonth()+1);
	   	        	 }
	        	  }
	        	}else{
	        		noOfDays=CPSUtils.getNoofDaysInMonth(runMonth.getMonth()+1);
	        	}
	        }else{
	        	noOfDays=CPSUtils.getNoofDaysInMonth(runMonth.getMonth()+1);
	        }
	    }catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return noOfDays;
	}
	public PayQuarterManagementDTO getEmployeeQuarterDetails(String sfid,Date runMonth) throws Exception{
		PayQuarterManagementDTO payQuarterManagementDTO=null;
		Session session =null;
		try{
			session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
			payQuarterManagementDTO=(PayQuarterManagementDTO)session.createCriteria(PayQuarterManagementDTO.class).add(Expression.eq("sfid", sfid)).add(Expression.eq("status", 1)).uniqueResult();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return payQuarterManagementDTO;
	}
public PromoteesEntryDTO getPromotedTopMostRecord(String runMonth,String sfid)throws Exception{
	Session session =null;
	PromoteesEntryDTO promoteesEntryDTO=null;
	try{
		session = hibernateUtils.getSession(); //session = sessionFactory.openSession();
		String query="Select Id as id,Grade_Pay as newGradePay,Promoted_Designation as promotedDesignation,Sfid as sfID,Two_Addl_Inc as twoAddl,Variable_Increments as varIncPt, "+
		" Var_Inc_Effective_Date as presentEffectiveDate,Var_Inc_End_Date as varIncEndDate From (Select Id,Grade_Pay,Promoted_Designation,Promoted_Effective_Date,Sfid,Two_Addl_Inc,Variable_Increments, "+
		" Var_Inc_Effective_Date,Var_Inc_End_Date From Emp_Grade_Pay_History Where Sfid=? And Status=1  "+
		" And Promoted_Effective_Date<=To_Date(?)  order by Promoted_Effective_Date desc) where Rownum=1";
		promoteesEntryDTO=(PromoteesEntryDTO)session.createSQLQuery(query).addScalar("id",Hibernate.INTEGER).addScalar("newGradePay",Hibernate.FLOAT)
        .addScalar("promotedDesignation",Hibernate.INTEGER).addScalar("sfID",Hibernate.STRING).addScalar("twoAddl",Hibernate.STRING).
         addScalar("varIncPt",Hibernate.INTEGER).addScalar("presentEffectiveDate",Hibernate.DATE).addScalar("varIncEndDate",Hibernate.DATE).setString(0, sfid).setString(1, runMonth).setResultTransformer(Transformers.aliasToBean(PromoteesEntryDTO.class)).uniqueResult();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return promoteesEntryDTO;
 }
}
