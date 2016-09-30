package com.callippus.web.business.domainobject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.FinanceDetailsDTO;
import com.callippus.web.beans.dto.MRODetailsDTO;
import com.callippus.web.beans.dto.MROPaymentDetailsDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.business.requestprocess.LtcRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.ltc.dto.LtcRequestProcessBean;

@Service
public class LtcDomainObject {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private LtcRequestProcess ltcRequestProcess;

	public String updateTxnDetails (LtcRequestProcessBean ltcrb)throws Exception {
		String message= "";
		Session session=null;
		EmployeeClaimDetailsDTO employeeClaimDetailsDTO=null;
		FinanceDetailsDTO financeDetails=null;
		StringBuffer sql=new StringBuffer();
		try {
            session=hibernateUtils.getSession();
             if(CPSUtils.compareStrings(CPSConstants.LTCADVANCE, ltcrb.getType()) || CPSUtils.compareStrings(CPSConstants.LTC_APP_ADV, ltcrb.getType())){
            	 employeeClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(ltcrb.getRequestID()))).add(Expression.eq("requestType",CPSConstants.ADVANCE)).uniqueResult();
            	 if (!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)) {
            		 financeDetails = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, employeeClaimDetailsDTO.getId())).uniqueResult();
     				if (CPSUtils.isNullOrEmpty(financeDetails)) {
     					FinanceDetailsDTO financeDetailsDTO = new FinanceDetailsDTO();
     					financeDetailsDTO.setReferenceID(employeeClaimDetailsDTO.getId());
     					financeDetailsDTO.setAmount(Float.valueOf(ltcrb.getIssuedAmount()));
     					financeDetailsDTO.setStatus(1);
     					financeDetailsDTO.setCreatedBy(ltcrb.getSfID());
    					financeDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
    					financeDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
    					financeDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
     					session.save(financeDetailsDTO);
            	 }else{
            		 financeDetails.setAmount(Float.valueOf(ltcrb.getIssuedAmount()));
            		 financeDetails.setLastModifiedBy(ltcrb.getSfID());
 					 financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
 					 session.update(financeDetails);
            	 }
     				employeeClaimDetailsDTO.setWorkFlowStatus(29);
     				session.update(employeeClaimDetailsDTO);
            	 }
            	sql.append("update LtcAdvanceRequestDTO set status=29 ");
            	if(!CPSUtils.isNull(ltcrb.getIssuedAmount())) {
            		//sql.append(",issuedAmount="+ltcrb.getIssuedAmount());	
            		sql.append(",amountPerEachInfant="+ltcrb.getAmountPerEachInfant());	
            		sql.append(",amountPerPerson="+ltcrb.getAmountPerPerson());	
            		sql.append(",doPartId="+ltcrb.getDoPartId());	
            	}
            	sql.append(" where requestId=?");
            	session.createQuery(sql.toString()).setString(0, ltcrb.getRequestId()).executeUpdate();
            	session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=29 where requestID=?").setString(0, ltcrb.getRequestId()).executeUpdate();
            }else if(CPSUtils.compareStrings("LTC ADVANCE AMENDMENT", ltcrb.getType())){
            	
            }else{
            	sql.append("update LtcApprovalRequestDTO set doPartId=?,status=29 where requestId=?");
            	session.createQuery(sql.toString()).setString(0, ltcrb.getDoPartNo()).setString(1, ltcrb.getRequestId()).executeUpdate();
            	session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=29 where requestID=?").setString(0, ltcrb.getRequestId()).executeUpdate();
            	/**
            	 * Update the do_part_id in leave_request_details
            	 */
            	/**
				 * Leave_request_details status should be updated to completed state when attached to Ltc... so status 8 is adding in below query on 07-05-2014
				 * 
				 */
				session.createSQLQuery("update leave_request_details set do_part_id=?,status=8   where request_id=(select leave_request_id from ltc_request_details where request_id=?)").setString(0, ltcrb.getDoPartNo()).setString(1, ltcrb.getRequestId()).executeUpdate();
            }
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	
	public String updateRefundTxnDetails (RequestBean rb)throws Exception {
		String message= "";
		Session session=null;
		String values = null;
		String query = null;
		try {
            session=hibernateUtils.getSession();
            session.createQuery("update LtcRefundRequestDTO set status=? where requestID=?").setString(0, CPSConstants.STATUSCOMPLETED).setString(1, rb.getRequestID()).executeUpdate();
            if(CPSUtils.compareStrings(rb.getRequestType(),CPSConstants.LTC_CANCEL)) {
            	query = "select case when lrd.encashment_days is not null then (lrd.encashment_days||'#'||lrd.encash_leave_type_id||'#'||lrd.sfid) else '' end value from ltc_request_details lrd,ltc_refund_request refund where lrd.request_id= refund.reference_id and refund.request_id=? ";
				values = (String)session.createSQLQuery(query).setString(0, rb.getRequestID()).uniqueResult();
            }else {
            	session.createQuery("update LtcAdvanceRequestDTO set excessAmount=?,excessAmountFine=?,MROPaidNo=?,MROPaidDate=? where requestId=(select referenceID from LtcRefundRequestDTO refund where refund.requestID=?)").setString(0, rb.getExcessAmount()).setString(1, rb.getExcessAmountFine()).setString(2, rb.getMROPaidNo()).setDate(3, rb.getMROPaidDate()).setString(4, rb.getRequestID()).executeUpdate();
            	query = "select case when lard.encashment_days is not null then (lard.encashment_days||'#'||lard.encash_leave_type_id||'#'||lard.sfid) else '' end value from ltc_advance_request_details lard,ltc_refund_request refund where lard.request_id=refund.reference_id and refund.request_id=? ";
				values = (String)session.createSQLQuery(query).setString(0, rb.getRequestID()).uniqueResult();
            }
	        if(!CPSUtils.isNull(values)) {
	        	ltcRequestProcess.updateLtcLeaveEncashDays(values.split("#")[2],values.split("#")[1],values.split("#")[0],"Due to LTC cancel/decline");
				session.createSQLQuery("update leave_emp_spells set ltc_encashment_days=(ltc_encashment_days-?) where sfid=? and leave_type_id=?").setFloat(0, Float.parseFloat(values.split("#")[0])).setString(1, values.split("#")[2]).setString(2, values.split("#")[1]).executeUpdate();
			}
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	public String updateReimbursementTxnDetails (LtcRequestProcessBean ltcrb)throws Exception {
		String message= "";
		Session session=null;
		EmployeeClaimDetailsDTO employeeClaimDetails=null;
		FinanceDetailsDTO financeDetails=null;
		try {
            session=hibernateUtils.getSession();
          /*  if(!CPSUtils.isNullOrEmpty(ltcrb.getExcessAmountFine()) && !CPSUtils.isNullOrEmpty(ltcrb.getExcessAmount())){
            	ltcrb.setSettleAmount(String.valueOf(Integer.parseInt(ltcrb.getExcessAmount()) - Integer.parseInt(ltcrb.getExcessAmountFine())));
            }*/
            if(CPSUtils.compareStrings(ltcrb.getRequestType(), CPSConstants.LTCREIMBURSEMENT)){
            	session.createQuery("update LtcApprovalRequestDTO set status=8 where requestId=(select referenceId from LtcReimbursementDTO where requestId=?)").setString(0, ltcrb.getRequestId()).executeUpdate();
            }else if(CPSUtils.compareStrings(ltcrb.getRequestType(), CPSConstants.LTCSETTLEMENT)){
            	session.createQuery("update LtcAdvanceRequestDTO set status=8,settlementAmount=? where requestId=(select referenceId from LtcReimbursementDTO where requestId=?)").setString(0, ltcrb.getSettleAmount()).setString(1, ltcrb.getRequestID()).executeUpdate();
            }
            session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=8 where requestID=(select refRequestId from EmployeeClaimDetailsDTO where requestID=?)").setString(0, ltcrb.getRequestID()).executeUpdate();
            if(!CPSUtils.isNullOrEmpty(ltcrb.getAmount())) {
            	 String amountRestriction[] = ltcrb.getAmount().split("@");
                 for(int i=0;i<amountRestriction.length;i++) {
                 	String idValue[] = amountRestriction[i].split("-");
                 	session.createQuery("update  LtcJourneyDetailsDTO set amtAfterRestriction=? where id=?")
                 	.setString(0, idValue[0].trim())
                 	.setString(1, idValue[1]).executeUpdate();
                 }
            }
            if(Integer.parseInt(ltcrb.getSettleAmount())>0) {
            //	session.createQuery("update LtcReimbursementDTO set amount=?,status=? where requestId=?").setString(0, ltcrb.getSettleAmount()).setString(1, CPSConstants.STATUSCOMPLETED).setString(2, ltcrb.getRequestID()).executeUpdate();
            	 if(CPSUtils.compareStrings(ltcrb.getRequestType(), CPSConstants.LTCREIMBURSEMENT)){
            	 employeeClaimDetails = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(ltcrb.getRequestID()))).add(Expression.eq("requestType",CPSConstants.REIMBURSEMENT)).uniqueResult();
            	 }else if(CPSUtils.compareStrings(ltcrb.getRequestType(), CPSConstants.LTCSETTLEMENT)){
            	 employeeClaimDetails = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(ltcrb.getRequestID()))).add(Expression.eq("requestType",CPSConstants.SETTLEMENT)).uniqueResult();
            	 }
            	 if (!CPSUtils.isNullOrEmpty(employeeClaimDetails)) {
            		 financeDetails = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID, employeeClaimDetails.getId())).uniqueResult();
     				if (CPSUtils.isNullOrEmpty(financeDetails)) {
     					FinanceDetailsDTO financeDetailsDTO = new FinanceDetailsDTO();
     					financeDetailsDTO.setReferenceID(employeeClaimDetails.getId());
     					financeDetailsDTO.setAmount(Float.valueOf(ltcrb.getSettleAmount()));
     					financeDetailsDTO.setStatus(1);
     					financeDetailsDTO.setCreatedBy(ltcrb.getSfID());
    					financeDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
    					financeDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
    					financeDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
    					employeeClaimDetails.setWorkFlowStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
     					session.save(financeDetailsDTO);
     					session.save(employeeClaimDetails);
     				}
     				else{
     				//	FinanceDetailsDTO financeDetailsDTO = new FinanceDetailsDTO();
     					//financeDetailsDTO.setId(financeDetails.getId());
     					//financeDetailsDTO.setReferenceID(employeeClaimDetails.getId());
     					financeDetails.setAmount(Float.valueOf(ltcrb.getSettleAmount()));
     					//financeDetailsDTO.setStatus(1);
     					financeDetails.setCreatedBy(ltcrb.getSfID());
     					//financeDetails.setCreationTime(CPSUtils.getCurrentDateWithTime());
     					financeDetails.setLastModifiedBy(ltcrb.getSfID());
     					financeDetails.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
    					employeeClaimDetails.setWorkFlowStatus(Integer.valueOf(CPSConstants.STATUSCOMPLETED));
     					
     					session.saveOrUpdate(financeDetails);
     					session.update(employeeClaimDetails);
     					session.flush();
     				}
            	 }
            }else {
            	 EmployeeClaimDetailsDTO employeeClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID,Integer.valueOf(ltcrb.getRequestID()))).uniqueResult();
            	 if(!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)){
            		 FinanceDetailsDTO financeDetailsDTO=(FinanceDetailsDTO)session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID,employeeClaimDetailsDTO.getId())).uniqueResult();
            		 if(!CPSUtils.isNullOrEmpty(financeDetailsDTO)){
            			 MRODetailsDTO mroDetailsDTO=(MRODetailsDTO)session.createCriteria(MRODetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID,financeDetailsDTO.getId())).uniqueResult();
            			 if(!CPSUtils.isNullOrEmpty(mroDetailsDTO)){
            				/* MROPaymentDetailsDTO mroPaymentDetailsDTO=new MROPaymentDetailsDTO();
            				 mroPaymentDetailsDTO.setReferenceID(mroDetailsDTO.getId());
            				 mroPaymentDetailsDTO.setMroNumber(ltcrb.getMROPaidNo());
            				 mroPaymentDetailsDTO.setMroDate(ltcrb.getMROPaidDate());
            				 mroPaymentDetailsDTO.setTotalAmount(mroDetailsDTO.getTotalAmount());
            				 mroPaymentDetailsDTO.setCreatedBy(ltcrb.getSfID());
            				 mroPaymentDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
            				 mroPaymentDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
            				 mroPaymentDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
            				 session.saveOrUpdate(mroPaymentDetailsDTO);
            				 hibernateUtils.commitTransaction();*/          				 
            				 
            				 
            				 financeDetailsDTO.setId(financeDetailsDTO.getId());
            				 financeDetailsDTO.setReferenceID(employeeClaimDetailsDTO.getId());
            				 financeDetailsDTO.setAmount(Float.valueOf(ltcrb.getSettleAmount()));
            				 financeDetailsDTO.setStatus(1);
            				 financeDetailsDTO.setCreatedBy(ltcrb.getSfID());
            				 financeDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
            				 financeDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
            				 financeDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
     	  					//int fdId = (Integer)session.save(nFinanceDetailsDTO);
     	        			session.saveOrUpdate(financeDetailsDTO);
     	        			//int fdId = (Integer)session.save(nFinanceDetailsDTO);
     	  					//hibernateUtils.commitTransaction();
     	  					//start mro details saving code 
     	  					

    	  					//MRODetailsDTO mroDetailsDTO=new MRODetailsDTO();
    	  					//mroDetailsDTO.setReferenceID(financeDetailsDTO.getId());
    	  					//mroDetailsDTO.setReferenceID(fdId);
    	  					mroDetailsDTO.setReferenceID(financeDetailsDTO.getId());
    	  					if(!CPSUtils.isNullOrEmpty(ltcrb.getNoOfDays())){	  			
    	  						mroDetailsDTO.setNoOfDays(Integer.parseInt(ltcrb.getNoOfDays()));
    	  					}else
    	  					mroDetailsDTO.setNoOfDays(ltcrb.getPenalNoOfDays());
    	  					mroDetailsDTO.setPenalInterestAmount(Float.valueOf(ltcrb.getExcessAmountFine()));
    	  					mroDetailsDTO.setPenalInterestId(ltcrb.getPenalInterestId());
    	  					mroDetailsDTO.setUnUtilizedBalance(-(Integer.valueOf(ltcrb.getExcessAmount())));
    	  					mroDetailsDTO.setTotalAmount(-(Integer.valueOf(ltcrb.getExcessAmount())+(-Integer.valueOf(ltcrb.getExcessAmountFine()))));
    	  					mroDetailsDTO.setCreatedBy(ltcrb.getSfID());
    	  					mroDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
    	  					mroDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
    	  					mroDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
    	  					mroDetailsDTO.setId(mroDetailsDTO.getId());
    	  					//session.save(mroDetailsDTO);
    	  					session.update(mroDetailsDTO);
    	  					session.flush();
    	  					//hibernateUtils.commitTransaction();
    	  					
    	  					MROPaymentDetailsDTO mroPaymentDetailsDTO=(MROPaymentDetailsDTO)session.createCriteria(MROPaymentDetailsDTO.class).add(Expression.eq(CPSConstants.REFERENCEID,mroDetailsDTO.getId())).uniqueResult();
    	  					
    	  					if(CPSUtils.isNullOrEmpty(mroPaymentDetailsDTO)){
    	  					if(!CPSUtils.isNullOrEmpty(ltcrb.getMROPaidNo())){
    		  					 MROPaymentDetailsDTO mroPaymentDetailsDTO1=new MROPaymentDetailsDTO();
    	        				 //mroPaymentDetailsDTO.setReferenceID(fdId);
    	  						
    	  						mroPaymentDetailsDTO1.setReferenceID(mroDetailsDTO.getId());
    		  					//mroPaymentDetailsDTO.setReferenceID(financeDetailsDTO.getId());
    	        				 mroPaymentDetailsDTO1.setMroNumber(ltcrb.getMROPaidNo());
    	        				 mroPaymentDetailsDTO1.setMroDate(ltcrb.getMROPaidDate());
    	        				 mroPaymentDetailsDTO1.setTotalAmount(Float.valueOf(ltcrb.getSettleAmount()));
    	        				 mroPaymentDetailsDTO1.setCreatedBy(ltcrb.getSfID());
    	        				 mroPaymentDetailsDTO1.setCreationTime(CPSUtils.getCurrentDateWithTime());
    	        				 mroPaymentDetailsDTO1.setLastModifiedBy(ltcrb.getSfID());
    	        				 mroPaymentDetailsDTO1.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
    	        				 //session.save(mroPaymentDetailsDTO);
    	        				 session.saveOrUpdate(mroPaymentDetailsDTO1);
    	        				 session.flush();
    	        				 hibernateUtils.commitTransaction();}}
    	  					else{
    	  						mroPaymentDetailsDTO.setId(mroPaymentDetailsDTO.getId());
    	  						mroPaymentDetailsDTO.setReferenceID(mroDetailsDTO.getId());
   	        				 mroPaymentDetailsDTO.setMroNumber(ltcrb.getMROPaidNo());
   	        				 mroPaymentDetailsDTO.setMroDate(ltcrb.getMROPaidDate());
   	        				 mroPaymentDetailsDTO.setTotalAmount(Float.valueOf(ltcrb.getSettleAmount()));
   	        				 mroPaymentDetailsDTO.setCreatedBy(ltcrb.getSfID());
   	        				 mroPaymentDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
   	        				 mroPaymentDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
   	        				 mroPaymentDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
   	        				session.update(mroPaymentDetailsDTO);
   	        				session.flush();
   	        				hibernateUtils.commitTransaction();
    	  					}
            			 }
            			 else{

     	  					MRODetailsDTO mroDetailsDTO1=new MRODetailsDTO();
     	  					//mroDetailsDTO.setReferenceID(financeDetailsDTO.getId());
     	  					//mroDetailsDTO.setReferenceID(fdId);
     	  					mroDetailsDTO1.setReferenceID(financeDetailsDTO.getId());
     	  					if(!CPSUtils.isNullOrEmpty(ltcrb.getNoOfDays())){	  			
    	  						mroDetailsDTO1.setNoOfDays(Integer.parseInt(ltcrb.getNoOfDays()));
    	  					}else
     	  					mroDetailsDTO1.setNoOfDays(ltcrb.getPenalNoOfDays());
     	  					mroDetailsDTO1.setPenalInterestAmount(Float.valueOf(ltcrb.getExcessAmountFine()));
     	  					mroDetailsDTO1.setPenalInterestId(ltcrb.getPenalInterestId());
     	  					mroDetailsDTO1.setUnUtilizedBalance(-(Integer.valueOf(ltcrb.getExcessAmount())));
     	  					mroDetailsDTO1.setTotalAmount(-(Integer.valueOf(ltcrb.getExcessAmount())+(-Integer.valueOf(ltcrb.getExcessAmountFine()))));
     	  					mroDetailsDTO1.setCreatedBy(ltcrb.getSfID());
     	  					mroDetailsDTO1.setCreationTime(CPSUtils.getCurrentDateWithTime());
     	  					mroDetailsDTO1.setLastModifiedBy(ltcrb.getSfID());
     	  					mroDetailsDTO1.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
     	  					//session.save(mroDetailsDTO);
     	  					int mrid =(Integer)session.save(mroDetailsDTO1);
     	  					
     	  					; 
     	  				 if(!CPSUtils.isNullOrEmpty(ltcrb.getMROPaidDate())){
            				 MROPaymentDetailsDTO mroPaymentDetailsDTO=new MROPaymentDetailsDTO();
            				 mroPaymentDetailsDTO.setReferenceID(mrid);
            				 mroPaymentDetailsDTO.setMroNumber(ltcrb.getMROPaidNo());
            				 mroPaymentDetailsDTO.setMroDate(ltcrb.getMROPaidDate());
            				 mroPaymentDetailsDTO.setTotalAmount(mroDetailsDTO1.getTotalAmount());
            				 mroPaymentDetailsDTO.setCreatedBy(ltcrb.getSfID());
            				 mroPaymentDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
            				 mroPaymentDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
            				 mroPaymentDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
            				 session.saveOrUpdate(mroPaymentDetailsDTO);
            				 hibernateUtils.commitTransaction();
            			 }
            			 }
            		 }
            		 
            		 
            	//** Start insert new record in finance_details if settlement amount is -ve
        			  
            		 else{
	        			FinanceDetailsDTO nFinanceDetailsDTO = new FinanceDetailsDTO();
	        			nFinanceDetailsDTO.setReferenceID(employeeClaimDetailsDTO.getId());
	        			nFinanceDetailsDTO.setAmount(Float.valueOf(ltcrb.getSettleAmount()));
	        			nFinanceDetailsDTO.setStatus(1);
	        			nFinanceDetailsDTO.setCreatedBy(ltcrb.getSfID());
	        			nFinanceDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
	        			nFinanceDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
	        			nFinanceDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
	  					//int fdId = (Integer)session.save(nFinanceDetailsDTO);
	        			session.saveOrUpdate(nFinanceDetailsDTO);
	        			//int fdId = (Integer)session.save(nFinanceDetailsDTO);
	  					hibernateUtils.commitTransaction();
	  					//start mro details saving code 
	  					
	  					if(!CPSUtils.isNullOrEmpty(ltcrb.getExcessAmount())){
	  					MRODetailsDTO mroDetailsDTO=new MRODetailsDTO();
	  					//mroDetailsDTO.setReferenceID(financeDetailsDTO.getId());
	  					//mroDetailsDTO.setReferenceID(fdId);
	  					mroDetailsDTO.setReferenceID(nFinanceDetailsDTO.getId());
	  					/*if(ltcrb.getLtcpenalNoofDays()!=0){	  			
	  						ltcrb.setPenalNoOfDays(ltcrb.getLtcpenalNoofDays());
	  					}*/
	  					
	  					if(!CPSUtils.isNullOrEmpty(ltcrb.getNoOfDays())){	  			
	  						mroDetailsDTO.setNoOfDays(Integer.parseInt(ltcrb.getNoOfDays()));
	  					}
	  					else
	  					mroDetailsDTO.setNoOfDays(ltcrb.getPenalNoOfDays());
	  					mroDetailsDTO.setPenalInterestAmount(Float.valueOf(ltcrb.getExcessAmountFine()));
	  					mroDetailsDTO.setPenalInterestId(ltcrb.getPenalInterestId());
	  					mroDetailsDTO.setUnUtilizedBalance(-(Integer.valueOf(ltcrb.getExcessAmount())));
	  					mroDetailsDTO.setTotalAmount(-(Integer.valueOf(ltcrb.getExcessAmount())+(-Integer.valueOf(ltcrb.getExcessAmountFine()))));
	  					mroDetailsDTO.setCreatedBy(ltcrb.getSfID());
	  					mroDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
	  					mroDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
	  					mroDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
	  					
	  					
	  					int  mdId = (Integer)session.save(mroDetailsDTO);
	  					hibernateUtils.commitTransaction();
	  					//end
	  					if(!CPSUtils.isNullOrEmpty(ltcrb.getMROPaidNo())){
	  					 MROPaymentDetailsDTO mroPaymentDetailsDTO=new MROPaymentDetailsDTO();
        				 //mroPaymentDetailsDTO.setReferenceID(fdId);
	  					
	  					mroPaymentDetailsDTO.setReferenceID(mdId);
        				 mroPaymentDetailsDTO.setMroNumber(ltcrb.getMROPaidNo());
        				 mroPaymentDetailsDTO.setMroDate(ltcrb.getMROPaidDate());
        				 mroPaymentDetailsDTO.setTotalAmount(Float.valueOf(ltcrb.getSettleAmount()));
        				 mroPaymentDetailsDTO.setCreatedBy(ltcrb.getSfID());
        				 mroPaymentDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
        				 mroPaymentDetailsDTO.setLastModifiedBy(ltcrb.getSfID());
        				 mroPaymentDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
        				 //session.save(mroPaymentDetailsDTO);
        				 session.saveOrUpdate(mroPaymentDetailsDTO);
        				 session.flush();
        				 hibernateUtils.commitTransaction();}}
            		
            		 }
        			 /** End insert new record in finance_details if settlement amount is -ve 
        			  * 
        			  */
            	 }
            	 
            	session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=? where requestID=?").setString(0, CPSConstants.STATUSCOMPLETED).setString(1, ltcrb.getRequestID()).executeUpdate();
            	//session.createQuery("update  LtcAdvanceRequestDTO set status=?,MROPaidNo=?,MROPaidDate=? where requestId=(select referenceId from LtcReimbursementDTO where requestId=?)").setString(0, CPSConstants.STATUSCOMPLETED).setString(1, ltcrb.getMROPaidNo()).setDate(2, ltcrb.getMROPaidDate()).setString(3, ltcrb.getRequestID()).executeUpdate();
            }
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	public String updateRestrictionAmount(RequestBean rb)throws Exception {
		Session session=null;
		String message = null;
		EmployeeClaimDetailsDTO employeeClaimDetailsDTO=null;
		try {
            session=hibernateUtils.getSession();
			
			if(!CPSUtils.isNull(rb.getAmount())) {
           	 String amountRestriction[] = rb.getAmount().split("@");
                for(int i=0;i<amountRestriction.length;i++) {
                	String idValue[] = amountRestriction[i].split("-");
                	session.createQuery("update  LtcJourneyDetailsDTO set amtAfterRestriction=? where id=?")
                	.setString(0, idValue[0])
                	.setString(1, idValue[1]).executeUpdate();
                }
           }
			employeeClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq(CPSConstants.REQUESTID, Integer.valueOf(rb.getRequestID()))).uniqueResult();
			if (!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)) {
				FinanceDetailsDTO financeDetailsDTO = new FinanceDetailsDTO();
				financeDetailsDTO.setReferenceID(employeeClaimDetailsDTO.getId());
				financeDetailsDTO.setAmount(Integer.valueOf(rb.getExcessAmount())+(-Integer.valueOf(rb.getExcessAmountFine())));
				financeDetailsDTO.setStatus(1);
				financeDetailsDTO.setCreatedBy(rb.getSfID());
				financeDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				financeDetailsDTO.setLastModifiedBy(rb.getSfID());
				financeDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				///session.save(financeDetailsDTO);
				session.saveOrUpdate(financeDetailsDTO);
				
				MRODetailsDTO mroDetailsDTO=new MRODetailsDTO();
				mroDetailsDTO.setReferenceID(financeDetailsDTO.getId());
				mroDetailsDTO.setNoOfDays(rb.getPenalNoOfDays());
				mroDetailsDTO.setPenalInterestAmount(Float.valueOf(rb.getExcessAmountFine()));
				mroDetailsDTO.setPenalInterestId(rb.getPenalInterestId());
				mroDetailsDTO.setUnUtilizedBalance(-(Integer.valueOf(rb.getExcessAmount())));
				mroDetailsDTO.setTotalAmount(-(Integer.valueOf(rb.getExcessAmount())+(-Integer.valueOf(rb.getExcessAmountFine()))));
				mroDetailsDTO.setCreatedBy(rb.getSfID());
				mroDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				mroDetailsDTO.setLastModifiedBy(rb.getSfID());
				mroDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				//session.save(mroDetailsDTO);
				session.saveOrUpdate(mroDetailsDTO);
			}
         // session.createQuery("update LtcAdvanceRequestDTO set excessAmount=to_char(-1*?) where requestId=(select referenceId from LtcReimbursementDTO where requestId=?)").setString(0, rb.getExcessAmount()).setString(1, rb.getRequestID()).executeUpdate();
        //  String pi = (String)session.createSQLQuery("select  round((lard.excess_amount*round(sysdate-to_date(lard.dv_date)))/365*10/100)||''  pi from ltc_reimbursement_details reim,ltc_advance_request_details lard where reim.request_id=? and lard.request_id=reim.reference_id").setString(0, requestID).uniqueResult();
          session.createQuery("update LtcAdvanceRequestDTO set excessAmount=to_char(-1*?),excessAmountFine=to_char(?) where requestId=(select refRequestId from EmployeeClaimDetailsDTO where requestID=?)").setString(0, rb.getExcessAmount()).setString(1, rb.getExcessAmountFine()).setString(2, rb.getRequestID()).executeUpdate();
           message = CPSConstants.SUCCESS;
        
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public String updateRequestStatus (Class beanName,String statusID,String referenceID)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
            sql = "update "+beanName.getSimpleName()+" set status=? where requestId=?";
            Query qry=session.createQuery(sql);
            qry.setString(0, statusID);
            qry.setString(1, referenceID);
            qry.executeUpdate();
            message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
}
