package com.callippus.web.dao.paybill;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.increment.AnnualIncrementDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.loan.beans.dto.LoanPaymentDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayProPreProcessDTO;
@Service
public class PayBillPreProcess {
	private static Log log = LogFactory.getLog(PayBillPreProcess.class);
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	public Object[] getLoanFromPreProcess(String sfid, String loanType,String runMonth,String userId,int runId) throws Exception {
		Session session = null;
		Object[] retValue = new Object[]{0,0,0,0,null,null,null};
		Map<String,PayBillLoanDTO> loanList=null;
		PayBillLoanDTO payBillLoanDTO=null;
		try{
		    session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			loanList=getLoanPreProcessElements(sfid, runMonth, Integer.parseInt(loanType),userId);
			if(loanList.size()!=0){
				payBillLoanDTO=loanList.get("principal");
				payBillLoanDTO.setRunId(runId);
				retValue[0]=payBillLoanDTO.getEmi();
				retValue[1]=payBillLoanDTO.getTotalInst();
				retValue[2]=payBillLoanDTO.getPresentInst();
				retValue[4]=payBillLoanDTO;
				retValue[5]=loanList.get("interest");
			}
		}catch (Exception e) {
			throw e;
		} finally {
			//session.close();
		}
		return retValue;
	}
	public Map<String,PayBillLoanDTO> getLoanPreProcessElements(String sfid,String runMonth,int loanTypeId,String userId)throws Exception{
		 Session session=null;
		 PayBillLoanDTO loanPrincipalDTO=null;
		 PayBillLoanDTO loanInterestDTO=null;
		 Map<String,PayBillLoanDTO> loanList=null;
		 try{
			 log.debug("<-----Loan PreProcess for the sfid "+sfid+" and loan type"+loanTypeId+"--------->");
			 session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			 loanList=new TreeMap<String, PayBillLoanDTO>();
			 LoanPaymentDTO loanPaymentDTO	=(LoanPaymentDTO) session.createSQLQuery("Select id as id,recovery_start_date as recStartDate ,Sfid as sfID,Loan_type_id  as loanTypeID,Emi as emi,Sanctioned_amount as sanctionedAmount,Sanctioned_instalments as sanctionedInstalments,Interest_amount as interestAmount"+
	                                          ",interest_emi as interestEmi,sanctioned_int_instalments as sanctionedIntInstalments,LAST_EMI as lastEmi,INTEREST_LAST_EMI AS interestLastEmi from loan_payment_details where sfid='"+sfid+"' and loan_type_id="+loanTypeId+" and to_char(recovery_start_date,'Mon-yyyy')='"+runMonth+"' and status=8 ")
	                                          .addScalar("sfID",Hibernate.STRING).addScalar("id",Hibernate.INTEGER).addScalar("loanTypeID",Hibernate.INTEGER).addScalar("emi",Hibernate.FLOAT).addScalar("sanctionedAmount",Hibernate.FLOAT).addScalar("lastEmi",Hibernate.FLOAT).addScalar("interestLastEmi",Hibernate.FLOAT).addScalar("sanctionedInstalments",Hibernate.INTEGER).
	                                          addScalar("interestAmount",Hibernate.FLOAT).addScalar("interestEmi",Hibernate.FLOAT).addScalar("recStartDate",Hibernate.DATE).addScalar("sanctionedIntInstalments",Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(LoanPaymentDTO.class)).uniqueResult();
			
			 if(!CPSUtils.isNullOrEmpty(loanPaymentDTO)){
				 loanPrincipalDTO=new PayBillLoanDTO();
				 loanPrincipalDTO.setLoanRefId(loanPaymentDTO.getId());
				 loanPrincipalDTO.setSfid(loanPaymentDTO.getSfID());
				 loanPrincipalDTO.setLoanDeduType("P");
				 loanPrincipalDTO.setLoanType(loanPaymentDTO.getLoanTypeID());
				 loanPrincipalDTO.setEmi((int)loanPaymentDTO.getEmi());
				 //satya.
				 if(CPSUtils.compareString(CPSConstants.FESTIVALLOANID, String.valueOf(loanTypeId)))
					 loanPrincipalDTO.setEmi((int)(loanPaymentDTO.getSanctionedAmount()/loanPaymentDTO.getSanctionedInstalments()));
				 
				 loanPrincipalDTO.setTotalAmt((int)loanPaymentDTO.getSanctionedAmount());
				 loanPrincipalDTO.setOutStandAmt((int)loanPaymentDTO.getSanctionedAmount()-loanPrincipalDTO.getEmi());
				 loanPrincipalDTO.setTotalInst(loanPaymentDTO.getSanctionedInstalments());
				 loanPrincipalDTO.setPresentInst(1);
				 loanPrincipalDTO.setStatus(1);
				 loanPrincipalDTO.setCreatedBy(userId);
				 loanPrincipalDTO.setLastModifiedBy(userId);
				 loanPrincipalDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				 loanPrincipalDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				 loanPrincipalDTO.setRecoveryStartDate(loanPaymentDTO.getRecStartDate());
				 
				 loanList.put("principal", loanPrincipalDTO);
				 
				 loanInterestDTO=new PayBillLoanDTO();
				 loanInterestDTO.setLoanRefId(loanPaymentDTO.getId());
				 loanInterestDTO.setSfid(loanPaymentDTO.getSfID());
				 loanInterestDTO.setLoanDeduType("I");
				 loanInterestDTO.setLoanType(loanPaymentDTO.getLoanTypeID());
				 loanInterestDTO.setEmi((int)loanPaymentDTO.getInterestEmi());
				 loanInterestDTO.setTotalAmt((int)loanPaymentDTO.getInterestAmount());
				 loanInterestDTO.setOutStandAmt(loanInterestDTO.getTotalAmt()-loanInterestDTO.getEmi());
				 loanInterestDTO.setTotalInst(loanPaymentDTO.getSanctionedIntInstalments());
				 loanInterestDTO.setPresentInst(1);
				 loanInterestDTO.setStatus(1);
				 loanInterestDTO.setCreatedBy(userId);
				 loanInterestDTO.setLastModifiedBy(userId);
				 loanInterestDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				 loanInterestDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				 loanInterestDTO.setRecoveryStartDate(loanPaymentDTO.getRecStartDate());
				 
				 loanList.put("interest", loanInterestDTO);
			 }
			 
	 
			 
		 }catch (Exception e) {
				throw e;
			}finally{
				//session.close();
			}
		return loanList;
	 }
	public String basicPayPreProcessWithRespToAnnualIncrement(String sfid,String runMonth) throws Exception{
		 Session session=null;
		 int basicPay=-100;
		 String crRemarks="Granted Annual Increment Vide D.O.Part No.";
		 int annIncrId=0;
		 int empBasicPayHistoryId=0;
		 try{
			 session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			
			 String query="Select  ANNINCRID as annIncrId,EBPHID as empBasicPayHistoryId,Sfid as userSfid,Dopartno as doPartNo,Incrbasicpay as incrementBasicPay From "+
	                      "Pay_ann_incr_pre_process Where Sfid='"+sfid+"' "+ 
	                      "and FINACDATE=upper('"+runMonth+"')";

			 AnnualIncrementDTO annualIncrementDTO = (AnnualIncrementDTO)session.createSQLQuery(query).addScalar("userSfid",Hibernate.STRING).
			 addScalar("incrementBasicPay",Hibernate.FLOAT).addScalar("annIncrId",Hibernate.INTEGER).addScalar("empBasicPayHistoryId",Hibernate.INTEGER).addScalar("doPartNo",Hibernate.INTEGER).
			 setResultTransformer(Transformers.aliasToBean(AnnualIncrementDTO.class)).uniqueResult();
			 if(!CPSUtils.isNullOrEmpty(annualIncrementDTO)){
				DoPartDTO doPartDTO =(DoPartDTO) session.get(DoPartDTO.class, annualIncrementDTO.getDoPartNo());
				crRemarks+=doPartDTO.getDoPartNumber()+" DT "+(new SimpleDateFormat("dd/MMM/yyyy").format(doPartDTO.getDoPartDate()));
				 basicPay= annualIncrementDTO.getIncrementBasicPay().intValue();
				 annIncrId=annualIncrementDTO.getAnnIncrId();
				 empBasicPayHistoryId=annualIncrementDTO.getEmpBasicPayHistoryId();
			 }
			 crRemarks=basicPay+"@"+crRemarks+"@"+annIncrId+"@"+empBasicPayHistoryId;
		 }catch (Exception e) {
				throw e;
			}finally{
				//session.close();
			}
		 return crRemarks;
	 }
	public PayProPreProcessDTO integratingWithPromotion(String sfid,String runMonth,int gradeId)throws Exception{
		 Session session=null;
		 String crRemarks="Pay Fixation Vide D.O.Part No.";
		 PayProPreProcessDTO integrateDTO=null;
		 try{
			 session = hibernateUtils.getSession(); //session=sessionFactory.openSession();
			 String query=" select rowno as rowno,Basicid As basicId,Basicpay As newBasic,Financeaccdate "+
                          " As financeAccDate,fixationdopartid As payFixationDoPartId,Gradeid As gradeId,Gradepay As newGrade,Incrdopartid As varIncrDoPartId,  "+
                          " Incrstartdate As Incrstartdate,Sfid As Sfid,Twoadd As Newtwoadd,Varincepoints As Varincrpoints,VARINCRENDDATE As VARINCRENDDATE,designationId as designationId From (Select Rownum rowno,Basicid ,Basicpay ,Financeaccdate  "+
                          " ,Fixationdopartid,Gradeid,Gradepay ,Incrdopartid,INCRSTARTDATE,Sfid,Twoadd,Varincepoints,VARINCRENDDATE,designationId From Pay_Pro_Integration  "+
                          " Where sfid=? and Financeaccdate<=To_Date(?)  order by Financeaccdate desc) where rowno=1";

			 PayProPreProcessDTO payProPreProcessDTO=(PayProPreProcessDTO) session.createSQLQuery(query).addScalar("basicId", Hibernate.INTEGER).
             addScalar("newBasic",Hibernate.INTEGER).addScalar("financeAccDate",Hibernate.DATE).addScalar("basicId", Hibernate.INTEGER).
             addScalar("payFixationDoPartId",Hibernate.INTEGER).addScalar("gradeId",Hibernate.INTEGER).
             addScalar("newGrade",Hibernate.INTEGER).addScalar("varIncrDoPartId",Hibernate.INTEGER).addScalar("varIncrStartDate",Hibernate.DATE).addScalar("sfid",Hibernate.STRING).addScalar("newTwoAdd",Hibernate.INTEGER).addScalar("varIncrPoints",Hibernate.INTEGER).addScalar("varIncrEndDate",Hibernate.DATE).setString(0, sfid).setString(1, runMonth).uniqueResult();
		   if(!CPSUtils.isNullOrEmpty(payProPreProcessDTO)&& gradeId!=payProPreProcessDTO.getGradeId()){
			   integrateDTO=new PayProPreProcessDTO();
			   integrateDTO.setNewBasic(payProPreProcessDTO.getNewBasic());
			   integrateDTO.setNewGrade(payProPreProcessDTO.getNewGrade());
			   integrateDTO.setNewTwoAdd(payProPreProcessDTO.getNewTwoAdd());
			   integrateDTO.setBasicId(payProPreProcessDTO.getBasicId());
			   integrateDTO.setGradeId(payProPreProcessDTO.getGradeId());
			   integrateDTO.setDesignationId(payProPreProcessDTO.getDesignationId());
			   DoPartDTO doPartDTO =(DoPartDTO) session.get(DoPartDTO.class, payProPreProcessDTO.getPayFixationDoPartId());
				crRemarks+=doPartDTO.getDoPartNumber()+" DT "+(new SimpleDateFormat("dd/MMM/yyyy").format(doPartDTO.getDoPartDate()));
				int check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth),payProPreProcessDTO.getVarIncrStartDate());
			    if(check>=0){
			    	check=CPSUtils.compareTwoDatesUptoMonth(new SimpleDateFormat("dd-MMM-yyyy").parse("01-"+runMonth), payProPreProcessDTO.getVarIncrStartDate());
				    if(check<0){
				    	integrateDTO.setVarIncrPoints(payProPreProcessDTO.getVarIncrPoints());
				    	doPartDTO =(DoPartDTO) session.get(DoPartDTO.class, payProPreProcessDTO.getVarIncrDoPartId());
							crRemarks+=doPartDTO.getDoPartNumber()+" DT "+(new SimpleDateFormat("dd/MMM/yyyy").format(doPartDTO.getDoPartDate()));
					}else{
				    	integrateDTO.setVarIncrPoints(0);
				    }
			    }else{
			    	integrateDTO.setVarIncrPoints(0);
			    }
		   }
		 }catch (Exception e) {
				throw e; 
			}finally{
				//session.close();
			}
			return integrateDTO;
	 }
}
