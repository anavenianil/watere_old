package com.callippus.web.dao.arrears;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.arrears.ArrearsBean;
import com.callippus.web.beans.arrears.ArrearsStatusDTO;
import com.callippus.web.beans.arrears.ArrearsTxnDetailsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;

@Service
public class SQLArrearsDAO implements IArrearsDAO{

@Autowired	
private HibernateUtils hibernateUtils;
@Override
@SuppressWarnings("unchecked")
public List<ArrearsStatusDTO> getAIArrearsStatusList()throws Exception{
	List<ArrearsStatusDTO> arrearsStatusList=null;
	Session session=null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			arrearsStatusList=session.createSQLQuery("Select Unique to_char(Ebph.Effective_date,'dd-MON-yyyy')  as adminAccDate, "+
                    " to_char(ande.finance_accepted_date,'dd-MON-yyyy') as financeAccDate " +
                    " From Annual_increment_details Ande,Emp_basic_pay_history Ebph  "+
					" Where Ande.Sfid=Ebph.Sfid And Ebph.Basic_pay = Ande.Increment_basic_pay "+
					" And Ebph.Inc_type='A' "+
					" And Ebph.Status=2 And Ande.Status=2").addScalar("financeAccDate",Hibernate.STRING).
					addScalar("adminAccDate",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
		}catch (Exception e) {
			throw e;
		}finally{
			//session.close();
		}
		return arrearsStatusList;
	}
@Override
@SuppressWarnings("unchecked")
public List<ArrearsStatusDTO> getAIArrearsDueDrawnDetails(String adminEffDate,String financeEffDate,String finDate) throws Exception{
	List<ArrearsStatusDTO> arrearsStatusList=null;
	Session session=null;
	String query=null;
		try{
			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			
			query="Select Tab.Sfid As sfid, "+
                " Rtrim ( Xmlagg (Xmlelement (E, Tab.Arrearsmonth|| '\n')).Extract ('//text()'), '\n') As arrearsMonth, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Drawnbasicpay|| '\n')).Extract ('//text()'), '\n') As drawnBasicPay, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Drawnda|| '\n')).Extract ('//text()'), '\n') As drawnDa, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Drawnhra|| '\n')).Extract ('//text()'), '\n') As drawnHra, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Drawntra|| '\n')).Extract ('//text()'), '\n') As drawnTra, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Duebasicpay|| '\n')).Extract ('//text()'), '\n') As dueBasicPay, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Dueda|| '\n')).Extract ('//text()'), '\n') As dueDa, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Duehra|| '\n')).Extract ('//text()'), '\n') As dueHra, "+
				" Rtrim ( Xmlagg (Xmlelement (E, Tab.Duetra|| '\n')).Extract ('//text()'), '\n') As dueTra, "+
				" Rtrim ( Xmlagg (Xmlelement (E, (Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra)|| '\n')).Extract ('//text()'), '\n') As totalDrawn, "+
				" Rtrim ( Xmlagg (Xmlelement (E, (Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda)|| '\n')).Extract ('//text()'), '\n') As totalDue, "+
				" Rtrim ( Xmlagg (Xmlelement (E, ((Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda)- "+
				" (Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra))|| '\n')).Extract ('//text()'), '\n') As totalDiff, "+
				" Sum(((Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda)-(Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra))) as  total "+
				" From (Select Ande.Sfid As sfid,to_char(Tx.Run_month,'dd-MON-yyyy') As arrearsMonth, Ande.Increment_basic_pay  "+
				" As dueBasicPay,Tx.C_basic_pay As drawnBasicPay,Tx.C_da As drawnDa, "+
				" (Select To_number(Substr((Select Calculate_da(Ande.Increment_basic_pay,Tx.C_grade_pay,Tx.Run_month) From Dual), "+
				" Instr((Select Calculate_da(Ande.Increment_basic_pay,Tx.C_grade_pay,Tx.Run_month) From Dual),'@')+1, "+
				" length((Select Calculate_da(Ande.Increment_basic_pay,Tx.C_grade_pay,Tx.Run_month) From Dual)))) from dual) As dueDa, "+
				" Tx.C_hra As drawnHra,(select calculate_hra(Ande.Increment_basic_pay,Tx.C_grade_pay, (SELECT CASE WHEN calculate_hra_days((select sfid from Annual_increment_details where finance_accepted_date=? ),Run_Month) IS NULL THEN 0 END FROM dual),"
				+ "TO_NUMBER(TO_CHAR(last_day(Add_months(Run_Month,1)),'dd'))) from dual) "+
				" as dueHra,Tx.C_tra As drawnTra,(Select To_number(Substr((Select Calculate_tra(Ande.Increment_basic_pay,Ande.Sfid,Tx.C_grade_pay,Tx.Run_month)  "+
				" from dual),Instr((Select Calculate_tra(Ande.Increment_basic_pay,Ande.Sfid,Tx.C_grade_pay,Tx.Run_month)  "+
				" From Dual),'@')+1,Length((Select Calculate_tra(Ande.Increment_basic_pay,Ande.Sfid,Tx.C_grade_pay,Tx.Run_month)  "+
				" From Dual)))) From Dual) as dueTra,tx.c_grade_pay as drawnGradePay From Annual_increment_details Ande,Emp_basic_pay_history Ebph,Pay_txn_details Tx  "+
				" Where Ande.Sfid=Ebph.Sfid And Ebph.Basic_pay = Ande.Increment_basic_pay And Ebph.Inc_type='A' And  TO_DATE(Ande.Admin_effective_date)=  TO_DATE(Ebph.Effective_date)  "+
				" And Ebph.Status=2 And Ande.Status=2 And  Ande.Sfid Not In (Select atx.Sfid From Arrears_Txn_Details Atx Where Atx.Arrears_Type='AI' "+
                " and Atx.Arrears_Month between To_date(?) And To_date(?) group by atx.sfid) and "+
				" To_char(Ebph.Effective_date,'MON-yyyy')=? And To_char(Ande.Finance_accepted_date,'MON-yyyy')=? And  "+
				" Tx.Run_month Between To_date(?) And To_date(?) And Tx.Status=52 And Tx.Sfid=Ande.Sfid And  "+
				" tx.sfid=ebph.sfid order by tx.sfid,tx.run_month) tab  group by tab.sfid order by tab.sfid ";
			
			arrearsStatusList=session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).
			addScalar("arrearsMonth",Hibernate.STRING).
			addScalar("dueBasicPay",Hibernate.STRING).addScalar("drawnBasicPay",Hibernate.STRING).
			addScalar("dueDa",Hibernate.STRING).addScalar("drawnDa",Hibernate.STRING).
			addScalar("dueHra",Hibernate.STRING).addScalar("drawnHra",Hibernate.STRING).
			addScalar("dueTra",Hibernate.STRING).addScalar("drawnTra",Hibernate.STRING).
			addScalar("totalDrawn",Hibernate.STRING)
			.addScalar("totalDue",Hibernate.STRING)
			.addScalar("total",Hibernate.STRING).setString(0, finDate).setString(1, "01-"+adminEffDate).setString(2, "01-"+financeEffDate).setString(3, adminEffDate).setString(4, financeEffDate).setString(5, "01-"+financeEffDate).setString(6, "01-"+financeEffDate).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return arrearsStatusList;
  }

@Override
@SuppressWarnings("unchecked")
public String submitAnnualIncrArrearsDetails(ArrearsBean arrearsBean) throws Exception{
	String message="";
	Session session=null;
	String sfid=null;
	List<ArrearsStatusDTO> arrearsStatusList=null;
	Transaction tx=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//tx=session.beginTransaction();
		int runId=getArrearsRunId();
		session.createSQLQuery("update Configuration_Details set value=? where name='ARREARS_RUNID'").setInteger(0, runId+1).executeUpdate();
		JSONObject jsonObj=new JSONObject(arrearsBean.getEmpList());
		for(int i=0;i<jsonObj.length();i++){
			sfid=(String)jsonObj.get(String.valueOf(i));
			String query=" Select Tab.Sfid As sfid, to_date(Tab.Arrearsmonth) As runMonth,Tab.Drawnbasicpay As drawnBasicPay,Tab.Drawnda As drawnDa,Tab.Drawnhra As drawnHra, "+
                " Tab.Drawntra As drawnTra, Tab.Duebasicpay As dueBasicPay,Tab.Dueda As dueDa,Tab.Duehra As dueHra,Tab.Duetra As dueTra,"+ 
				" Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra As totalDrawn, "+
				" Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda As totalDue,((Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda)-(Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra)) As totalDiff, "+
				" Sum(((Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda)-(Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra))) as  total "+
				" From (Select Ande.Sfid As sfid,to_char(Tx.Run_month,'dd-MON-yyyy') As arrearsMonth, Ande.Increment_basic_pay  "+
				" As dueBasicPay,Tx.C_basic_pay As drawnBasicPay,Tx.C_da As drawnDa, (Select To_number(Substr((Select Calculate_da(Ande.Increment_basic_pay,Tx.C_grade_pay,Tx.Run_month) From Dual), "+
				" Instr((Select Calculate_da(Ande.Increment_basic_pay,Tx.C_grade_pay,Tx.Run_month) From Dual),'@')+1, "+
				" length((Select Calculate_da(Ande.Increment_basic_pay,Tx.C_grade_pay,Tx.Run_month) From Dual)))) from dual) As dueDa,"+ 
				" Tx.C_hra As drawnHra,(select calculate_hra(Ande.Increment_basic_pay,Tx.C_grade_pay,(SELECT CASE WHEN calculate_hra_days(?,Run_Month) IS NULL THEN 0 END FROM dual),"
				+ "TO_NUMBER(TO_CHAR(last_day(Add_months(Run_Month,1)),'dd'))) from dual) "+
				" As Duehra,Tx.C_Tra As Drawntra,(Select To_Number(Substr((Select Calculate_Tra(Ande.Increment_Basic_Pay,"+
				" Ande.Sfid,Tx.C_grade_pay,Tx.Run_month) from dual),Instr((Select Calculate_tra(Ande.Increment_basic_pay,Ande.Sfid,Tx.C_grade_pay,Tx.Run_month)  "+
				" From Dual),'@')+1,Length((Select Calculate_tra(Ande.Increment_basic_pay,Ande.Sfid,Tx.C_grade_pay,Tx.Run_month) "+
				" From Dual)))) From Dual) as dueTra,tx.c_grade_pay as drawnGradePay From Annual_increment_details Ande,Emp_basic_pay_history Ebph,Pay_txn_details Tx "+
				" Where Ande.Sfid=Ebph.Sfid And Ebph.Basic_pay = Ande.Increment_basic_pay And Ebph.Inc_Type='A' And TO_DATE(Ande.Admin_Effective_Date) =  TO_DATE(Ebph.Effective_Date) "+
				" And Ebph.Status=2 And Ande.Status=2 And To_Char(Ebph.Effective_Date,'MON-yyyy')=to_char(to_date(?),'MON-yyyy')  "+
				" And To_char(Ande.Finance_accepted_date,'MON-yyyy')=to_char(to_date(?),'MON-yyyy') And "+
				" Tx.Run_Month Between To_date(?) And To_date(?) And Tx.Status=52 and  ande.sfid=? and "+
				" Tx.Run_Month!=To_Date(?) And Tx.Sfid=Ande.Sfid And  "+
				" tx.sfid=ebph.sfid order by tx.sfid,tx.run_month) tab group by  Tab.Sfid, Tab.Arrearsmonth,Tab.Drawnbasicpay, Tab.Drawnda, "+
				" Tab.Drawnhra, Tab.Drawntra, Tab.Duebasicpay, Tab.Dueda,Tab.Duehra, Tab.Duetra,Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra, "+
				" Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda,((Tab.Duebasicpay+Tab.Duetra+Tab.Duehra+Tab.Dueda)- "+
				" (Tab.Drawnbasicpay+Tab.Drawnda+Tab.Drawnhra+Tab.Drawntra)) ";
			
			arrearsStatusList=session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).
								addScalar("runMonth",Hibernate.DATE).
								addScalar("dueBasicPay",Hibernate.STRING).addScalar("drawnBasicPay",Hibernate.STRING).
								addScalar("dueDa",Hibernate.STRING).addScalar("drawnDa",Hibernate.STRING).
								addScalar("dueHra",Hibernate.STRING).addScalar("drawnHra",Hibernate.STRING).
								addScalar("dueTra",Hibernate.STRING).addScalar("drawnTra",Hibernate.STRING).
								addScalar("totalDrawn",Hibernate.STRING).addScalar("totalDue",Hibernate.STRING).
								addScalar("total",Hibernate.STRING).setString(0,sfid).setString(1, arrearsBean.getAdminAccDate()).setString(2, arrearsBean.getFinanceAccDate()).setString(3, "01"+arrearsBean.getAdminAccDate().substring(3)).setString(4, "01"+(arrearsBean.getFinanceAccDate().substring(3))).setString(5, sfid).setString(6, arrearsBean.getFinanceAccDate()).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
	         for(int j=0;j<arrearsStatusList.size();j++){
	        	
	        	 ArrearsStatusDTO arrearsStatusDTO =arrearsStatusList.get(j);
	        	 ArrearsTxnDetailsDTO arrearsTxnDetailsDTO =new ArrearsTxnDetailsDTO();
	        	
	        	 arrearsTxnDetailsDTO.setSfid(arrearsStatusDTO.getSfid());
	        	 arrearsTxnDetailsDTO.setArrearsType(CPSConstants.ANNUALINCRTYPEFLAG);
		         arrearsTxnDetailsDTO.setArrearsMonth(arrearsStatusDTO.getRunMonth());
	        	
	        	 arrearsTxnDetailsDTO.setDrawnBasic(Integer.parseInt(arrearsStatusDTO.getDrawnBasicPay()));
	        	 arrearsTxnDetailsDTO.setDrawnHra(Integer.parseInt(arrearsStatusDTO.getDrawnHra()));
	        	 arrearsTxnDetailsDTO.setDrawnDA(Integer.parseInt(arrearsStatusDTO.getDrawnDa()));
	        	 arrearsTxnDetailsDTO.setDrawnTpt(Integer.parseInt(arrearsStatusDTO.getDrawnTra()));
	        	 arrearsTxnDetailsDTO.setDrawnTotal(Integer.parseInt(arrearsStatusDTO.getTotalDrawn()));
	        	 
	        	 arrearsTxnDetailsDTO.setDueBasic(Integer.parseInt(arrearsStatusDTO.getDueBasicPay()));
	        	 arrearsTxnDetailsDTO.setDueHra(Integer.parseInt(arrearsStatusDTO.getDueHra()));
	        	 arrearsTxnDetailsDTO.setDueDA(Integer.parseInt(arrearsStatusDTO.getDueDa()));
	        	 arrearsTxnDetailsDTO.setDueTpt(Integer.parseInt(arrearsStatusDTO.getDueTra()));
	        	 arrearsTxnDetailsDTO.setDueTotal(Integer.parseInt(arrearsStatusDTO.getTotalDue()));
	        	
	        	 arrearsTxnDetailsDTO.setTotalAmount(Integer.parseInt(arrearsStatusDTO.getTotal()));
	        	 arrearsTxnDetailsDTO.setRunId(runId);
	 			 arrearsTxnDetailsDTO.setSequence(j+1);
	        	 session.saveOrUpdate(arrearsTxnDetailsDTO);
		        	
	         } 
		}
		session.flush();//tx.commit() ;
		message=CPSConstants.SUCCESS;
	}catch (Exception e) {
		//tx.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
}
@Override
@SuppressWarnings("unchecked")
public List<ArrearsStatusDTO> getDaArrearsPreviewList(ArrearsBean arrearsBean)throws Exception{
	List<ArrearsStatusDTO> keyList=null;
	Session session=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		String query= " Select to_char(Tab.Runmonth,'dd-MON-yy') as arrearsMonth,to_Date(Tab.Runmonth) as runMonth,Da.Da_Value as payCondrValue From(Select Txn.Run_Month Runmonth,Txn.Da_Master_Id Daid "+
                      " From Pay_Txn_Master_Details Txn Where To_Date(Concat('01-',To_Char(Txn.Run_Month,'MON-yyyy'))) Between To_Date(Concat('01-',To_Char(To_Date(?),'MON-yyyy')))  "+
					  " And to_date(concat('01-',To_Char(To_Date(?),'MON-yyyy'))) Group By Txn.Run_Month,Txn.Da_Master_Id) "+
					  " tab,pay.Pay_Dearness_Allowance_Master da where tab.daid=da.id order by Tab.Runmonth";
		keyList=session.createSQLQuery(query).addScalar("arrearsMonth",Hibernate.STRING).addScalar("runMonth",Hibernate.DATE).addScalar("payCondrValue",Hibernate.INTEGER).setString(0,arrearsBean.getAdminAccDate()).setString(1,arrearsBean.getFinanceAccDate()).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
       for(int i=0;i<keyList.size();i++){
    	   ArrearsStatusDTO arrearsStatusDTO=keyList.get(i);
    	   arrearsStatusDTO.setActualValue(getDaActualValue(arrearsStatusDTO.getArrearsMonth()));
       }
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return keyList;
}
@Override
public int getDaActualValue(String runMonth)throws Exception{
	Session session=null;
	int actualValue=0;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		String query=" Select to_number(Da_value) From (Select Da_date,Da_value From Pay_dearness_allowance_master "+
                     " Where Status=1 And To_Date(Concat('01-',To_Char(To_Date(Da_Date),'MON-yyyy')),'dd-MON-yyyy')<= "+
                     " To_Date(Concat('01-',To_Char(To_Date(?),'MON-yyyy')),'dd-MON-yyyy') order by DA_DATE desc) where rownum=1";
		actualValue=((BigDecimal)session.createSQLQuery(query).setString(0, runMonth).uniqueResult()).intValue();
		
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return actualValue;
}
@Override
@SuppressWarnings("unchecked")
public List<ArrearsStatusDTO> getDaArrearsPreviewDetailedList(ArrearsBean arrearsBean)throws Exception{
	List<ArrearsStatusDTO> keyList=null;
	Session session=null;
	String remarks="";
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		remarks="DA ARREARS W.E.F "+arrearsBean.getAdminAccDate()+" TO "+arrearsBean.getFinanceAccDate();
		 StringBuffer query=new StringBuffer(" Select Super.Sfid as sfid,Super.Empname As empName, "+
						" Rtrim ( Xmlagg (Xmlelement (E, Super.Runmonth|| '\n')).Extract ('//text()'), '\n') As arrearsMonth, "+
						" Rtrim ( Xmlagg (Xmlelement (E, Super.Basicpay|| '\n')).Extract ('//text()'), '\n') As basicPay, "+
						" Rtrim ( Xmlagg (Xmlelement (E, Super.Gradepay|| '\n')).Extract ('//text()'), '\n') As gradePay, "+
						" Rtrim ( Xmlagg (Xmlelement (E, Super.Drawnda|| '\n')).Extract ('//text()'), '\n') As drawnDa, "+
						" Rtrim ( Xmlagg (Xmlelement (E, Super.Dueda|| '\n')).Extract ('//text()'), '\n') As dueDa, "+
						" Rtrim ( Xmlagg (Xmlelement (E, Super.Drawntra|| '\n')).Extract ('//text()'), '\n') As drawnTra, "+
						" Rtrim ( Xmlagg (Xmlelement (E, super.duetra|| '\n')).Extract ('//text()'), '\n') As dueTra, "+
						" Rtrim ( Xmlagg (Xmlelement (E, super.daDiff|| '\n')).Extract ('//text()'), '\n') As daDiff, "+
						" Rtrim ( Xmlagg (Xmlelement (E, Super.Tptdiff|| '\n')).Extract ('//text()'), '\n') As tptDiff, "+
						" Sum(Super.Dadiff) totalDa,Sum(Super.Tptdiff) totTpt,round(Sum(0.1*Super.Dadiff)) As cpf,Sum(Super.Dadiff)-round(Sum(0.1*Super.Dadiff)) As netPmt, "+
						" (sum(super.daDiff)-round(sum(0.1*super.daDiff)))-Sum(Super.Tptdiff) as totAmt from (Select Tab.Sfid,Tab.Empname,Tab.Runmonth,Tab.Basicpay,Tab.Gradepay,Tab.Drawnda,Tab.Dueda, "+
						" tab.drawnTra,tab.duetra,(tab.dueda-tab.drawnda) daDiff,(tab.duetra-tab.drawntra) tptDiff from (Select Txn.Sfid As Sfid ,Emp.Name_In_Service_Book As Empname,Txn.Run_Month As  "+
						" Runmonth,Txn.C_Basic_Pay Basicpay,Txn.C_Grade_Pay As Gradepay,Txn.C_Da As Drawnda, (Select To_number(Substr((Select " +
						" Calculate_Da(Txn.C_Basic_Pay,Txn.C_Grade_Pay,Txn.Run_Month) From Dual),  "+
						" Instr((Select Calculate_Da(Txn.C_Basic_Pay,Txn.C_Grade_Pay,Txn.Run_Month) From Dual),'@')+1,Length((Select Calculate_Da(Txn.C_Basic_Pay,Txn.C_Grade_Pay,Txn.Run_Month) From Dual)))) From Dual) As Dueda, "+
						" Txn.C_Tra As Drawntra,(Select To_Number(Substr((Select Calculate_Tra(Txn.C_Basic_Pay,Txn.Sfid,Txn.C_Grade_Pay,Txn.Run_Month) From Dual)," +
						" Instr((Select Calculate_Tra(Txn.C_Basic_Pay,Txn.Sfid,Txn.C_Grade_Pay,Txn.Run_Month) "+
						" From Dual),'@')+1,Length((Select Calculate_Tra(Txn.C_Basic_Pay,Txn.Sfid,Txn.C_Grade_Pay,Txn.Run_Month) From Dual)))) From Dual)" +
						" As Duetra From Pay_Txn_Details Txn,Emp_Master Emp Where To_Date(Concat('01-',To_Char(Txn.Run_Month,'MON-yyyy'))) Between "+
						" To_Date(Concat('01-',To_Char(To_Date('"+arrearsBean.getAdminAccDate()+"'),'MON-yyyy')))  " +
						" And To_Date(Concat('01-',To_Char(To_Date('"+arrearsBean.getFinanceAccDate()+"'),'MON-yyyy')))  And Txn.Status=52 And Emp.Sfid=Txn.Sfid " +
						" and emp.sfid ");
		 
		 if(CPSUtils.compareStrings(arrearsBean.getCategoryId(), "0")){
			 query.append(" not in(Select Atx.Sfid From Arrears_Txn_Details Atx Where Atx.Arrears_Type='DA' "+
                          " And to_date(Atx.ARREARS_MONTH) Between To_Date(Concat('01-',To_Char(To_Date('"+arrearsBean.getAdminAccDate()+"'),'MON-yyyy')))" +
                          " And To_Date(Concat('01-',To_Char(To_Date('"+arrearsBean.getFinanceAccDate()+"'),'MON-yyyy'))) group by atx.sfid)) Tab " +
		                  " order by tab.sfid,to_date(tab.runMonth)) Super Group By Super.Sfid,Super.Empname ");
		 }else{
			 query.append(" in(select sfid from Emp_Pay_master where Category_Id="+arrearsBean.getCategoryId()+" and status=1 and sfid not in(Select "+
					 " Atx.Sfid From Arrears_Txn_Details Atx Where Atx.Arrears_Type='DA' And To_Date(Atx.Arrears_Month) Between "+
					 " To_Date(Concat('01-',To_Char(To_Date('"+arrearsBean.getAdminAccDate()+"'),'MON-yyyy'))) And To_Date(Concat('01-',To_Char(To_Date('"+arrearsBean.getFinanceAccDate()+"'),"+
					 " 'MON-yyyy'))) group by atx.sfid))) Tab " +
				     " order by tab.sfid,to_date(tab.runMonth)) Super Group By Super.Sfid,Super.Empname ");
		     remarks+=" in r/o "+((PayBillEmpCategoryDTO)session.get(PayBillEmpCategoryDTO.class, Integer.parseInt(arrearsBean.getCategoryId()))).getName().toUpperCase();
		 }
		 
		
	   keyList=session.createSQLQuery(query.toString()).addScalar("sfid",Hibernate.STRING).addScalar("empName",Hibernate.STRING)
	                                        .addScalar("arrearsMonth",Hibernate.STRING).addScalar("basicPay",Hibernate.STRING)
	                                        .addScalar("gradePay",Hibernate.STRING).addScalar("drawnDa",Hibernate.STRING)
	                                        .addScalar("dueDa",Hibernate.STRING).addScalar("drawnTra",Hibernate.STRING)
	                                        .addScalar("dueTra",Hibernate.STRING).addScalar("daDiff",Hibernate.STRING)
	                                        .addScalar("tptDiff",Hibernate.STRING).addScalar("totalDa",Hibernate.STRING)
	                                        .addScalar("totTpt",Hibernate.STRING).addScalar("cpf",Hibernate.STRING)
	                                        .addScalar("netPmt",Hibernate.STRING).addScalar("totAmt",Hibernate.STRING)
	                                        .setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
  	   arrearsBean.setRemarks(remarks);
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return keyList;
}
@Override
@SuppressWarnings("unchecked")
public String submitDAArrearsDetails(ArrearsBean arrearsBean) throws Exception{
	String message="";
	Session session=null;
	String sfid=null;
	List<ArrearsStatusDTO> arrearsStatusList=null;
	Transaction tx=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//tx=session.beginTransaction();
		int runId=getArrearsRunId();
		session.createSQLQuery("update Configuration_Details set value=? where name='ARREARS_RUNID'").setInteger(0, runId+1).executeUpdate();
		JSONObject jsonObj=new JSONObject(arrearsBean.getEmpList());
		for(int i=0;i<jsonObj.length();i++){
			sfid=(String)jsonObj.get(String.valueOf(i));
			String query="Select Super.Sfid as sfid,Super.Empname As empName,to_date(Super.Runmonth) As runMonth,Super.Basicpay As drawnBasicPay, "+
                " Super.Gradepay As drawnGradePay,Super.Drawnda As drawnDa,Super.Dueda As dueDa,Super.Drawntra As drawnTra, "+
				" Super.Duetra As dueTra,Super.Cpf As cpf,super.netPmt as netPmt,super.totAmt as totalDiff From  "+
				" (Select Tab.Sfid,Tab.Empname,Tab.Runmonth,Tab.Basicpay,Tab.Gradepay,Tab.Drawnda,Tab.Dueda, "+
				" Tab.Drawntra,Tab.Duetra,Round((Tab.Dueda-Tab.Drawnda)*0.1) As Cpf,((Tab.Dueda-Tab.Drawnda)-(Round((Tab.Dueda-Tab.Drawnda)*0.1))) As Netpmt,(Tab.Duetra-Tab.Drawntra) As Tptdiff, "+
				" (((Tab.Dueda-Tab.Drawnda)-(Round((Tab.Dueda-Tab.Drawnda)*0.1)))+((Tab.Duetra-Tab.Drawntra))) as totAmt From (Select Txn.Sfid As Sfid  "+
				" ,Emp.Name_In_Service_Book As Empname,Txn.Run_Month As Runmonth,Txn.C_Basic_Pay Basicpay,Txn.C_Grade_Pay As  "+
				" Gradepay,Txn.C_Da As Drawnda, (Select To_Number(Substr((Select Calculate_Da(Txn.C_Basic_Pay,Txn.C_Grade_Pay,Txn.Run_Month) From Dual),  "+
				" Instr((Select Calculate_Da(Txn.C_Basic_Pay,Txn.C_Grade_Pay,Txn.Run_Month) From Dual),'@')+1,Length((Select  "+
				" Calculate_Da(Txn.C_Basic_Pay,Txn.C_Grade_Pay,Txn.Run_Month) From Dual)))) From Dual) As Dueda, "+
				" Txn.C_Tra As Drawntra,(Select To_Number(Substr((Select Calculate_Tra(Txn.C_Basic_Pay,Txn.Sfid, "+
				" Txn.C_Grade_Pay,Txn.Run_Month) From Dual),Instr((Select Calculate_Tra(Txn.C_Basic_Pay,Txn.Sfid,Txn.C_Grade_Pay,Txn.Run_Month) "+
				" From Dual),'@')+1,Length((Select Calculate_Tra(Txn.C_Basic_Pay,Txn.Sfid,Txn.C_Grade_Pay,Txn.Run_Month)  "+
				" From Dual)))) From Dual) As Duetra From Pay_Txn_Details Txn,Emp_Master Emp Where To_Date(Concat('01-',To_Char(Txn.Run_Month,'MON-yyyy'))) Between "+
				" To_Date(Concat('01-',To_Char(To_Date('"+arrearsBean.getAdminAccDate()+"'),'MON-yyyy')))  And  To_Date(Concat" +
				" ('01-',To_Char(To_Date('"+arrearsBean.getFinanceAccDate()+"'),'MON-yyyy')))  And Txn.Status=52 And Emp.Sfid=Txn.Sfid And Emp.Sfid='"+sfid+"') Tab  "+
				" order by tab.sfid,to_date(tab.runMonth)) Super ";
			
			arrearsStatusList=session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).
								addScalar("runMonth",Hibernate.DATE).
								addScalar("drawnBasicPay",Hibernate.STRING).addScalar("drawnGradePay",Hibernate.STRING).
								addScalar("dueDa",Hibernate.STRING).addScalar("drawnDa",Hibernate.STRING).
								addScalar("dueTra",Hibernate.STRING).addScalar("drawnTra",Hibernate.STRING).
								addScalar("cpf",Hibernate.STRING).addScalar("netPmt",Hibernate.STRING).
								addScalar("totalDiff",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
	         for(int j=0;j<arrearsStatusList.size();j++){
	        	 ArrearsStatusDTO arrearsStatusDTO =arrearsStatusList.get(j);
	        	 ArrearsTxnDetailsDTO arrearsTxnDetailsDTO =new ArrearsTxnDetailsDTO();
	        	
	        	 arrearsTxnDetailsDTO.setSfid(arrearsStatusDTO.getSfid());
	        	 arrearsTxnDetailsDTO.setArrearsType(CPSConstants.DAARREARTYPEFLAG);
		         arrearsTxnDetailsDTO.setArrearsMonth(arrearsStatusDTO.getRunMonth());
	        	
	        	 arrearsTxnDetailsDTO.setDrawnBasic(Integer.parseInt(arrearsStatusDTO.getDrawnBasicPay()));
	        	 arrearsTxnDetailsDTO.setDrawnGrade(Integer.parseInt(arrearsStatusDTO.getDrawnGradePay()));
	        	 arrearsTxnDetailsDTO.setDrawnDA(Integer.parseInt(arrearsStatusDTO.getDrawnDa()));
	        	 arrearsTxnDetailsDTO.setDrawnTpt(Integer.parseInt(arrearsStatusDTO.getDrawnTra()));
	        	 
	        	 arrearsTxnDetailsDTO.setDueDA(Integer.parseInt(arrearsStatusDTO.getDueDa()));
	        	 arrearsTxnDetailsDTO.setDueTpt(Integer.parseInt(arrearsStatusDTO.getDueTra()));
	        	 
	        	 arrearsTxnDetailsDTO.setCpf(Integer.parseInt(arrearsStatusDTO.getCpf()));
	        	 arrearsTxnDetailsDTO.setNetPmt(Integer.parseInt(arrearsStatusDTO.getNetPmt()));
	        	
	        	 arrearsTxnDetailsDTO.setTotalAmount(Integer.parseInt(arrearsStatusDTO.getTotalDiff()));
	        	 arrearsTxnDetailsDTO.setRunId(runId);
	 			 arrearsTxnDetailsDTO.setSequence(j+1);
	        	 session.saveOrUpdate(arrearsTxnDetailsDTO);
		        	
	         } 
		}
		session.flush();//tx.commit() ;
		message=CPSConstants.SUCCESS;
	}catch (Exception e) {
		//tx.rollback();
		throw e;
	}finally{
		//session.close();
	}
	return message;
  }
@Override
@SuppressWarnings("deprecation")
public ArrearsBean getFpaArrearsDetails(ArrearsBean arrearsBean)throws Exception{
	Session session=null;
	List<ArrearsStatusDTO> keyList=null;
	int totalArrears=0;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		
		String	query=" Select Sub.Sfid As sfid,Sub.Empname As empName,Sub.Opdate As opDate,sub.dueGradePay as dueGradePay, "+
                " Sub.Oplocation As opLocation,sub.fpaflag as fpaFlag,sub.fpagrade as fpaGradePay,Sub.Effdate As effDate,Sub.Duebasicpay As dueBasicPay,Sub.Fpa As dueFpa,sub.totAmount as total, "+
                " sub.designation as designation,sub.drawn as drawnFpa from (Select Super.Sfid As Sfid,Super.Empname As Empname,Super.Opdate As Opdate,nvl(arr.totAmount,-100) as totAmount, "+
				" Super.Oploc As Oplocation,Super.Effdate As Effdate,Super.Basic As Duebasicpay,super.fpagradepay as fpa,0 as drawn, "+
				" Super.Grade As Duegradepay,Super.Desig As Designation,super.fpaflag,super.fpagrade From "+
				" (Select Em.Sfid Sfid,Em.Name_In_Service_Book Empname,Em.Family_Planning fpaflag,Payment.Fpa_Gradepay fpagrade, "+
				" To_Date(Tab.Opdate) Opdate,Tab.Oploc Oploc,Desig.Name Desig,To_Date(Tab.Effdate) Effdate,Payment.Basic_Pay Basic,Payment.Grade_Pay Grade,(Select Allowance_Amount From Pay_Family_Planning_Master "+
				" Where Grade_Pay=Payment.fpa_gradepay And Status=1) as fpagradepay "+
				" From Emp_Master Em,Designation_Master desig,(Select Fpa.Basic_Pay Basic,fpa.sfid sfid, "+
				" Fpa.Operation_Date Opdate,Fpa.Operation_Location Oploc,Fpa.Pay_Effective_Date Effdate, "+
				" Fpa.Grade_Pay Grade From Fpa_Request_Details Fpa Where Status=8) "+
				" Tab,pay.Emp_Pay_master Payment Where Em.Sfid=Tab.Sfid(+) And Em.Status=1 And Em.Sfid=? And Em.Sfid=Payment.Sfid "+
				" And Em.Designation_Id=Desig.Id) Super,(Select Unique Arrears.Sfid Usersfid,Arrears.Total_Amount Totamount From Arrears_Txn_Details  "+
				" arrears where arrears_type='FPA') arr where Super.sfid=arr.userSfid(+)) Sub ";

			ArrearsStatusDTO arrearsStatusDTO=(ArrearsStatusDTO)session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).
		                                   addScalar("empName",Hibernate.STRING).addScalar("opDate",Hibernate.DATE).addScalar("total",Hibernate.STRING).
		                                   addScalar("opLocation",Hibernate.STRING).addScalar("effDate",Hibernate.DATE).
		                                   addScalar("fpaFlag",Hibernate.STRING).addScalar("fpaGradePay",Hibernate.STRING).
		                                   addScalar("designation",Hibernate.STRING).addScalar("dueFpa",Hibernate.STRING).
		                                   addScalar("dueBasicPay",Hibernate.STRING).addScalar("dueGradePay",Hibernate.STRING).setString(0, arrearsBean.getUserSfid()).
		                                   setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(arrearsStatusDTO.getOpDate())&&!CPSUtils.isNullOrEmpty(arrearsStatusDTO.getEffDate())&&!CPSUtils.isNullOrEmpty(arrearsStatusDTO.getOpLocation())){
			keyList=new ArrayList<ArrearsStatusDTO>();
			Date from =arrearsStatusDTO.getOpDate();
			Date opDate=(Date) from.clone();
			from.setDate(01);
			Date to =arrearsStatusDTO.getEffDate();
			to.setDate(01);
			DateFormat df=new SimpleDateFormat("dd-MMM-yyyy");
			String fromDate=df.format(from);
			String toDate=df.format(to);
			int size=arrearsStatusDTO.getOpDate().getMonth()+((BigDecimal)session.createSQLQuery("select round((to_date('"+toDate+"')-to_date('"+fromDate+"'))/30) from dual").uniqueResult()).intValue();
			for(int i=arrearsStatusDTO.getOpDate().getMonth()+1;i<size;i++){
				ArrearsStatusDTO bean=new ArrearsStatusDTO();
				bean.setSfid(arrearsStatusDTO.getSfid());
				Date d=arrearsStatusDTO.getOpDate();
				d.setMonth(d.getMonth()+1);
				bean.setArrearsMonth(new SimpleDateFormat("MMM-yyyy").format(d));
				bean.setDueFpa(arrearsStatusDTO.getDueFpa());
				bean.setDrawnFpa("0");
				totalArrears+=Integer.parseInt(arrearsStatusDTO.getDueFpa());
				keyList.add(bean);
			}
			arrearsBean.setAdminAccDate(CPSUtils.formatDate(opDate));
			arrearsBean.setFinanceAccDate(new SimpleDateFormat("MMM-yyyy").format(arrearsStatusDTO.getEffDate()));
			arrearsBean.setArrearsStatusList(keyList);
			arrearsBean.setTotalArrears((String.valueOf(totalArrears)));
			arrearsBean.setOpLocation(arrearsStatusDTO.getOpLocation());
			arrearsBean.setCheck(arrearsStatusDTO.getTotal());
			
			if(Integer.parseInt(arrearsStatusDTO.getTotal())>=0){
				arrearsBean.setMessage("arrearsGiven");
				arrearsBean.setRemarks("Arrears Given Already from <b><font color='blue'>"+arrearsBean.getArrearsStatusList().get(0).getArrearsMonth()+"</font></b> To :<b><font color='blue'>"+arrearsBean.getArrearsStatusList().get(arrearsBean.getArrearsStatusList().size()-1).getArrearsMonth()+"</font></b>");
			}else{
				arrearsBean.setMessage(CPSConstants.SUCCESS);
			}
		}else{
			arrearsBean.setMessage("notApplied");
			arrearsBean.setRemarks("not Applied for Family Planning Allowance");
		}
		arrearsBean.setEmpName(arrearsStatusDTO.getEmpName());
		arrearsBean.setDesignation(arrearsStatusDTO.getDesignation());
		arrearsBean.setBasicPay(arrearsStatusDTO.getDueBasicPay());
		arrearsBean.setGradePay(arrearsStatusDTO.getDueGradePay());
		arrearsBean.setFpaFlag(arrearsStatusDTO.getFpaFlag());
		arrearsBean.setFpaGradePay(arrearsStatusDTO.getFpaGradePay());
		
		
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return arrearsBean;
}
@Override
public void submitFPAArrearsDetails(ArrearsBean arrearsBean)throws Exception{
	Session session=null;
	Transaction txn=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		//txn=session.beginTransaction();
		arrearsBean=getFpaArrearsDetails(arrearsBean);
		if(CPSUtils.compareStrings(arrearsBean.getMessage(),CPSConstants.SUCCESS)){
			int runId=getArrearsRunId();
			session.createSQLQuery("update Configuration_Details set value=? where name='ARREARS_RUNID'").setInteger(0, runId+1).executeUpdate();
		for(int i=0;i<arrearsBean.getArrearsStatusList().size();i++){
			ArrearsStatusDTO arrearsStatusDTO=arrearsBean.getArrearsStatusList().get(i);
			ArrearsTxnDetailsDTO arrearsTxnDetailsDTO=new ArrearsTxnDetailsDTO();
			arrearsTxnDetailsDTO.setSfid(arrearsStatusDTO.getSfid());
			arrearsTxnDetailsDTO.setDrawnFpa(0);
			arrearsTxnDetailsDTO.setDueFpa(Integer.parseInt(arrearsStatusDTO.getDueFpa()));
			arrearsTxnDetailsDTO.setTotalAmount(Integer.parseInt(arrearsStatusDTO.getDueFpa()));
			arrearsTxnDetailsDTO.setArrearsMonth(CPSUtils.convertStringToDate("01-"+arrearsStatusDTO.getArrearsMonth()));
			arrearsTxnDetailsDTO.setArrearsType(CPSConstants.FPAARREARTYPEFLAG);
			arrearsTxnDetailsDTO.setRunId(runId);
			arrearsTxnDetailsDTO.setSequence(i+1);
			
			session.saveOrUpdate(arrearsTxnDetailsDTO);
		}
		session.flush() ; //txn.commit();
		arrearsBean.setMessage("arrearsGiven");
		arrearsBean.setRemarks("Arrears Given Successfully from <b><font color='blue'>"+arrearsBean.getArrearsStatusList().get(0).getArrearsMonth()+"</font></b> To :<b><font color='blue'>"+arrearsBean.getArrearsStatusList().get(arrearsBean.getArrearsStatusList().size()-1).getArrearsMonth()+"</font></b>");
	  }
	}catch (Exception e) {
		//txn.rollback();
		throw e;
	}finally{
		//session.close();
	}
 }
public int getArrearsRunId()throws Exception{
	Session session =null;
	int runId=0;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		runId=((BigDecimal)session.createSQLQuery("select to_number(value) from Configuration_Details where name='ARREARS_RUNID'").uniqueResult()).intValue();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return runId;
}
@Override
@SuppressWarnings("unchecked")
public List<ArrearsStatusDTO> getPromotionArrearsDetails(ArrearsBean arrearsBean)throws Exception{
	Session session =null;
	List<ArrearsStatusDTO> keyList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		EmployeeBean empBean=(EmployeeBean) session.createCriteria(EmployeeBean.class).add(Expression.eq("userSfid", arrearsBean.getUserSfid())).add(Expression.eq("status", 1)).uniqueResult();
		arrearsBean.setEmpName(empBean.getNameInServiceBook());
		arrearsBean.setDesignation(empBean.getDesignationDetails().getName());
		String query="select assessmentId as assessmentId,to_char(PROMOTIONACCEPTANCEDATE,'dd-MON-yy') as adminAccDate,Desigfrom as fromDesignation,Desigto as toDesignation,to_char(PROMOTIONEFFECTIVEDATE,'dd-MON-yy') as financeAccDate from Pay_Pro_Arrears_Details where sfid=?";
		keyList=session.createSQLQuery(query).addScalar("assessmentId",Hibernate.STRING).addScalar("adminAccDate",Hibernate.STRING).addScalar("fromDesignation",Hibernate.STRING).addScalar("toDesignation",Hibernate.STRING).addScalar("financeAccDate",Hibernate.STRING).setString(0, arrearsBean.getUserSfid()).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
	}catch (Exception e) {
		throw e;
	}finally{
		//session.close();
	}
	return keyList;
}
@Override
@SuppressWarnings({ "unchecked"})
public List<ArrearsStatusDTO> getPromotionArrearsPreviewList(ArrearsBean arrearsBean)throws Exception{
	Session session =null;
	List<ArrearsStatusDTO> keyList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
	       String query="select * from(Select sfid,C_Basic_Pay As drawnBasicPay, "+
						" (Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?) As dueBasicPay, "+
						" C_Grade_Pay As drawnGradePay,(Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?)As dueGradePay, "+
						" C_Da As drawnDa, "+
						" (Select To_Number(Substr((Select Calculate_Da((Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?), "+
						" (Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),Run_Month) From Dual),   "+
						" Instr((Select  Calculate_Da((Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?), "+
						" (Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),Run_Month) From Dual),'@')+1,Length((Select   "+
						" Calculate_Da((Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?), "+
						" (Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),Run_Month) From Dual)))) From Dual) As dueDa, "+
						" C_Tra As drawnTra, "+
						" (Select To_Number(Substr((Select Calculate_tra((Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),sfid, "+
						" (Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),Run_Month) From Dual),   "+
						" Instr((Select  Calculate_tra((Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),sfid, "+
						" (Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),Run_Month) From Dual),'@')+1,Length((Select   "+
						" Calculate_Tra((Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),Sfid, "+
						" (Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),Run_Month) From Dual)))) From Dual) As dueTra, "+
						" C_Fpa As drawnFpa, "+
						" (Select Case When 'Yes'=(Select Family_Planning From Emp_Master Epd Where Epd.Sfid=txn.sfid) Then  "+
						" (Select Calculate_Fpa((Select To_Number(NEWGRADEPAY) From Pay_Pro_Arrears_Details Where Assessmentid=?)) From Dual)  "+
						" Else '0' End As Temp From Dual) As dueFpa, "+
						"  C_Two_Addl_Incr As drawnTwoAddIncr, "+
						"  (Select Newtwoaddincr  From Pay_Pro_Arrears_Details Where Assessmentid=?) as dueTwoAddIncr, "+
						" C_Hra As drawnHra, "+
						" (Select Calculate_Hra((Select NEWBASICPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),"+
						" (Select NEWGRADEPAY From Pay_Pro_Arrears_Details Where Assessmentid=?),(SELECT  CASE WHEN calculate_hra_days(?,Run_Month) IS NULL THEN 0 END  from dual) ,TO_NUMBER(TO_CHAR(last_day(Add_months(Run_Month,1)),'dd'))) from dual ) as dueHra,"+
						" to_date(Run_Month) as runMonth "+
						" From Pay_Txn_Details txn Where Run_Month  Between   "+
						" (Select to_date(concat('01-',to_char(PROMOTIONACCEPTANCEDATE,'MON-yyyy'))) From Pay_Pro_Arrears_Details Where Assessmentid=?)   "+
						" And (Select PROMOTIONACCEPTANCEDATE  From Pay_Pro_Arrears_Details Where Assessmentid=?) And   "+
						" Sfid=(Select Sfid From Pay_Pro_Arrears_Details Where Assessmentid=?) And Status=52  "+
						" And To_Char(Run_Month,'MON-yyyy')!=(Select To_Char(PROMOTIONEFFECTIVEDATE,'MON-yyyy') From Pay_Pro_Arrears_Details Where Assessmentid=?) " +
						" order by txn.id) Tab1 Where Tab1.Sfid Not In(Select Sfid From Arrears_Txn_Details "+
                        " where Arrears_Type='PRO' and to_char(Arrears_Month,'MON-yyyy')=to_char(tab1.Runmonth,'MON-yyyy'))";

	   
	        keyList= session.createSQLQuery(query).addScalar("drawnBasicPay",Hibernate.STRING).addScalar("dueBasicPay",Hibernate.STRING).
	                 addScalar("drawnGradePay",Hibernate.STRING).addScalar("dueGradePay",Hibernate.STRING).
	                 addScalar("drawnDa",Hibernate.STRING).addScalar("dueDa",Hibernate.STRING).
	                 addScalar("drawnTra",Hibernate.STRING).addScalar("dueTra",Hibernate.STRING).
	                 addScalar("drawnFpa",Hibernate.STRING).addScalar("dueFpa",Hibernate.STRING).
	                 addScalar("drawnHra",Hibernate.STRING).addScalar("dueHra",Hibernate.STRING).
	                 addScalar("drawnTwoAddIncr",Hibernate.STRING).addScalar("dueTwoAddIncr",Hibernate.STRING).
	                 addScalar("runMonth",Hibernate.DATE)
	                .setInteger(0,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(1,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setInteger(2,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(3,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setInteger(4,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(5,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(6,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setInteger(7,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(8,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(9,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setInteger(10,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(11,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(12,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setInteger(13,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(14,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(15,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setInteger(16,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(17,Integer.parseInt(arrearsBean.getAssessmentId())).setString(18,arrearsBean.getUserSfid()).setInteger(19,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setInteger(20,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(21,Integer.parseInt(arrearsBean.getAssessmentId())).setInteger(22,Integer.parseInt(arrearsBean.getAssessmentId()))
	                .setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
   SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yy");
       Calendar from=Calendar.getInstance();
       from.setTime(sdf.parse(arrearsBean.getAdminAccDate()));
       Calendar to=Calendar.getInstance();
      
       if(!CPSUtils.isNullOrEmpty(arrearsBean.getFinanceAccDate())){
       to.setTime(sdf.parse(arrearsBean.getFinanceAccDate()));
       }
       int size=((BigDecimal)session.createSQLQuery(" Select Months_Between(Trunc(To_Date('"+arrearsBean.getFinanceAccDate()+"'),'MON'),"+
                                                    " trunc(to_date('"+arrearsBean.getAdminAccDate()+"'),'MON')) temp from dual").uniqueResult()).intValue();
       
       for(int i=0;i<size;i++){
    	int dayOfTheMonth=from.get(Calendar.DATE);
    	int noOfDaysInMonth= from.getMaximum(Calendar.DATE);
    	   for(int j=0;j<keyList.size();j++){
    		   ArrearsStatusDTO arrearsStatusDTO=keyList.get(j);
    		   from.set(Calendar.DATE, 1);
    		   if(CPSUtils.compareStrings((sdf.format(arrearsStatusDTO.getRunMonth())),sdf.format(from.getTime()))){
    			
    			arrearsStatusDTO.setDrawnBasicPay(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDrawnBasicPay())/(float)noOfDaysInMonth))));
    			arrearsStatusDTO.setDueBasicPay(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDueBasicPay())/(float)noOfDaysInMonth))));
    			
    			arrearsStatusDTO.setDrawnGradePay(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDrawnGradePay())/(float)noOfDaysInMonth))));
    			arrearsStatusDTO.setDueGradePay(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDueGradePay())/(float)noOfDaysInMonth))));
    			
    			arrearsStatusDTO.setDrawnDa(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDrawnDa())/(float)noOfDaysInMonth))));
    			arrearsStatusDTO.setDueDa(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDueDa())/(float)noOfDaysInMonth))));
    			
    			arrearsStatusDTO.setDrawnTra(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDrawnTra())/(float)noOfDaysInMonth))));
    			arrearsStatusDTO.setDueTra(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDueTra())/(float)noOfDaysInMonth))));
    			
    			arrearsStatusDTO.setDrawnHra(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDrawnHra())/(float)noOfDaysInMonth))));
    			arrearsStatusDTO.setDueHra(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDueHra())/(float)noOfDaysInMonth))));
    			
    			arrearsStatusDTO.setDrawnFpa(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDrawnFpa())/(float)noOfDaysInMonth))));
    			arrearsStatusDTO.setDueFpa(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDueFpa())/(float)noOfDaysInMonth))));
    			
    			arrearsStatusDTO.setDrawnTwoAddIncr(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDrawnTwoAddIncr())/(float)noOfDaysInMonth))));
    			arrearsStatusDTO.setDueTwoAddIncr(String.valueOf(Math.round((noOfDaysInMonth-dayOfTheMonth+1)*((float)Integer.parseInt(arrearsStatusDTO.getDueTwoAddIncr())/(float)noOfDaysInMonth))));
    			
    			arrearsStatusDTO.setTotalDue(String.valueOf(Integer.parseInt(arrearsStatusDTO.getDueBasicPay())+
    					Integer.parseInt(arrearsStatusDTO.getDueGradePay())+Integer.parseInt(arrearsStatusDTO.getDueHra())+
    					Integer.parseInt(arrearsStatusDTO.getDueFpa())+Integer.parseInt(arrearsStatusDTO.getDueTwoAddIncr())+
    					Integer.parseInt(arrearsStatusDTO.getDueTra())+Integer.parseInt(arrearsStatusDTO.getDueDa())));
    			
    			arrearsStatusDTO.setTotalDrawn(String.valueOf(Integer.parseInt(arrearsStatusDTO.getDrawnBasicPay())+
    					Integer.parseInt(arrearsStatusDTO.getDrawnGradePay())+Integer.parseInt(arrearsStatusDTO.getDrawnHra())+
    					Integer.parseInt(arrearsStatusDTO.getDrawnFpa())+Integer.parseInt(arrearsStatusDTO.getDrawnTwoAddIncr())+
    					Integer.parseInt(arrearsStatusDTO.getDrawnTra())+Integer.parseInt(arrearsStatusDTO.getDrawnDa())));
    			
    			arrearsStatusDTO.setTotalDiff(String.valueOf(Integer.parseInt(arrearsStatusDTO.getTotalDue())-Integer.parseInt(arrearsStatusDTO.getTotalDrawn())));
    			arrearsStatusDTO.setSfid(arrearsBean.getUserSfid());
    			arrearsStatusDTO.setArrearsMonth(new SimpleDateFormat("MMM-yyyy").format(arrearsStatusDTO.getRunMonth()));
    			break;
    		   }
    	   }
    	   from.add(Calendar.MONTH, 1);
       }
	}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally{
		//session.close();
	}
	return keyList;
 }
@Override
public void submitPromotionArrearsDetails(ArrearsBean arrearsBean)throws Exception{
	Session session=null;
	Transaction txn=null;
	List<ArrearsStatusDTO> keyList=null;
    try{
    	session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
    	//txn=session.beginTransaction();
    	keyList=getPromotionArrearsPreviewList(arrearsBean);
    	int runId=getArrearsRunId();
    	session.createSQLQuery("update Configuration_Details set value=? where name='ARREARS_RUNID'").setInteger(0, runId+1).executeUpdate();
		
    	for(int i=0;i<keyList.size();i++){
    		ArrearsStatusDTO arrearsStatusDTO=keyList.get(i);
    		ArrearsTxnDetailsDTO arrearsTxnDetailsDTO=new ArrearsTxnDetailsDTO();
    		
    		arrearsTxnDetailsDTO.setArrearsMonth(arrearsStatusDTO.getRunMonth());
    		arrearsTxnDetailsDTO.setSequence(i+1);
    		arrearsTxnDetailsDTO.setSfid(arrearsStatusDTO.getSfid());
    		arrearsTxnDetailsDTO.setArrearsType(CPSConstants.PROMOTIONARREARTYPEFLAG);
    		arrearsTxnDetailsDTO.setRunId(runId);
    		
    		arrearsTxnDetailsDTO.setDrawnBasic(Integer.parseInt(arrearsStatusDTO.getDrawnBasicPay()));
    		arrearsTxnDetailsDTO.setDrawnGrade(Integer.parseInt(arrearsStatusDTO.getDrawnGradePay()));
    		arrearsTxnDetailsDTO.setDrawnHra(Integer.parseInt(arrearsStatusDTO.getDrawnHra()));
    		arrearsTxnDetailsDTO.setDrawnTpt(Integer.parseInt(arrearsStatusDTO.getDrawnTra()));
    		arrearsTxnDetailsDTO.setDrawnFpa(Integer.parseInt(arrearsStatusDTO.getDrawnFpa()));
    		arrearsTxnDetailsDTO.setDrawnAddIncr(Integer.parseInt(arrearsStatusDTO.getDrawnTwoAddIncr()));
    		arrearsTxnDetailsDTO.setDrawnDA(Integer.parseInt(arrearsStatusDTO.getDrawnDa()));
    		
    		arrearsTxnDetailsDTO.setDrawnTotal(Integer.parseInt(arrearsStatusDTO.getTotalDrawn()));
    		
    		arrearsTxnDetailsDTO.setDueBasic(Integer.parseInt(arrearsStatusDTO.getDueBasicPay()));
    		arrearsTxnDetailsDTO.setDueGrade(Integer.parseInt(arrearsStatusDTO.getDueGradePay()));
    		arrearsTxnDetailsDTO.setDueHra(Integer.parseInt(arrearsStatusDTO.getDueHra()));
    		arrearsTxnDetailsDTO.setDueTpt(Integer.parseInt(arrearsStatusDTO.getDueTra()));
    		arrearsTxnDetailsDTO.setDueFpa(Integer.parseInt(arrearsStatusDTO.getDueFpa()));
    		arrearsTxnDetailsDTO.setDueAddIncr(Integer.parseInt(arrearsStatusDTO.getDueTwoAddIncr()));
    		arrearsTxnDetailsDTO.setDueDA(Integer.parseInt(arrearsStatusDTO.getDueDa()));
    		
    		arrearsTxnDetailsDTO.setDueTotal(Integer.parseInt(arrearsStatusDTO.getTotalDue()));
    		
    		arrearsTxnDetailsDTO.setTotalAmount(Integer.parseInt(arrearsStatusDTO.getTotalDiff()));
    		
    		session.saveOrUpdate(arrearsTxnDetailsDTO);
    	}
    	session.flush();
    	session.flush() ; //txn.commit();
    	arrearsBean.setMessage(CPSConstants.SUCCESS);
    }catch (Exception e) {
    	//txn.rollback();
		throw e;
	}finally{
		//session.close();
	}
 }
@Override
@SuppressWarnings("unchecked")
public List<ArrearsStatusDTO> getFPAArrearsPrintDetails()throws Exception{
	Session session=null;
	List<ArrearsStatusDTO> keyList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		 String query=" Select Main.Sfid as sfid,Sum(Main.Total_Amount) as total,Emp.Name_In_Service_Book as empName,desig.name as designation, "+
                      " Rtrim ( Xmlagg (Xmlelement (E, to_char(Main.Arrears_Month,'MON-yyyy')|| '\n')).Extract ('//text()'), '\n') as arrearsMonth, "+
			          " Rtrim ( Xmlagg (Xmlelement (E, to_char(Main.Total_Amount)|| '\n')).Extract ('//text()'), '\n') as totalDiff "+
			          " From Arrears_Txn_Details Main,Emp_Master Emp,Designation_Master Desig  Where Main.Arrears_Type='FPA' "+
			          " and emp.sfid=main.sfid and Emp.Designation_Id=desig.id group by main.sfid,Emp.Name_In_Service_Book,desig.name";
		 
		 keyList= session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).addScalar("total",Hibernate.STRING).addScalar("empName",Hibernate.STRING).addScalar("designation",Hibernate.STRING).addScalar("arrearsMonth",Hibernate.STRING).addScalar("totalDiff",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
	}catch (Exception e) {
    	throw e;
	}finally{
		//session.close();
	}
	return keyList;
}
@Override
@SuppressWarnings("unchecked")
public List<ArrearsStatusDTO> getPromotionArrearsPrintDetails() throws Exception{
	Session session=null;
	List<ArrearsStatusDTO> keyList=null;
	try{
		session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
		 String query=" Select Main.Sfid As Sfid,Sum(main.total_amount) As Total,Emp.Name_In_Service_Book As Empname,Desig.Name As Designation," +
		 		"Rtrim ( Xmlagg(Xmlelement(e,to_char(main.arrears_month,'MON-yyyy')||'\n')).extract('//text()'),'\n') as arrearsMonth," +
		 		"Rtrim ( Xmlagg (Xmlelement (E, Main.Total_Amount||'\n')).Extract('//text()'),'\n') As totalDiff" +
		 		" From Arrears_Txn_Details Main,Emp_Master Emp,Designation_Master Desig  Where Main.Arrears_Type='PRO'" +
		 		" and emp.sfid=main.sfid and Emp.Designation_Id=desig.id group by main.sfid,Emp.Name_In_Service_Book,desig.name";
		 keyList= session.createSQLQuery(query).addScalar("sfid",Hibernate.STRING).addScalar("total",Hibernate.STRING).addScalar("empName",Hibernate.STRING).addScalar("designation",Hibernate.STRING).addScalar("arrearsMonth",Hibernate.STRING).addScalar("totalDiff",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ArrearsStatusDTO.class)).list();
	}catch (Exception e) {
    	throw e;
	}finally{
		//session.close();
	}
	return keyList;
}
}
