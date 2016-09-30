package com.callippus.web.dao.telephone;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.telephone.TelePhoneBillBean;
import com.callippus.web.beans.telephone.TelephoneBillClaimDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeRequestDetailsDTO;
import com.callippus.web.business.requestprocess.RequestProcess;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.beans.incometax.IncomeTaxMasterBean;
@Service
public class TelephoneBillRequestProcess extends RequestProcess {
	  @Autowired
	  private TxRequestProcess txRequestProcess;
      @Autowired
      private HibernateUtils hibernateUtils;
    
      public String initWorkFlow(TelePhoneBillBean telePhoneBillBean) throws Exception{
    	  String message="";
    	  Session session=null;
    	 try{
    		session=hibernateUtils.getSession();
    		telePhoneBillBean.setRequestId(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
    		telePhoneBillBean.setRequestType(CPSConstants.TELEPHONEBILLREQUESTYPE);
    		telePhoneBillBean.setRequestTypeID(CPSConstants.TELEPHONEBILLREQUESTTYPEID);
    		telePhoneBillBean.setSfid(telePhoneBillBean.getSfid());
    		telePhoneBillBean=submitTelephoneBillRequestDetails(telePhoneBillBean,session);
    		if(CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLREQUEST,telePhoneBillBean.getMessage())){
    			RequestBean rb = new RequestBean();
    		    BeanUtils.copyProperties(rb, telePhoneBillBean);
    		    rb.setSfID(telePhoneBillBean.getSfid());
    		    rb.setRequestID(rb.getRequestId());
    		    message=txRequestProcess.initWorkflow(rb);
    		    session.flush();
    		    message=CPSConstants.TELEPHONEBILLREQUEST;
    		}
    		if(CPSUtils.compareStrings(CPSConstants.TELEPHONEBILLREQUEST,message)){
    			EmployeeClaimDetailsDTO employeeClaimDetailsDTO = new EmployeeClaimDetailsDTO();
    			employeeClaimDetailsDTO.setModuleId(0);
    			employeeClaimDetailsDTO.setFundId(0);
    			employeeClaimDetailsDTO.setRequestType(CPSConstants.REIMBURSEMENT);
    			employeeClaimDetailsDTO.setRequestID(Integer.parseInt(telePhoneBillBean.getRequestId()));
    			employeeClaimDetailsDTO.setRequestTypeID(Integer.parseInt(CPSConstants.TELEPHONEBILLREQUESTTYPEID));
    			employeeClaimDetailsDTO.setRefRequestId(Integer.parseInt(telePhoneBillBean.getRequestId()));
    			employeeClaimDetailsDTO.setAmountClaimed(Float.parseFloat(telePhoneBillBean.getGrandTotal()));
    			employeeClaimDetailsDTO.setAppliedDate(CPSUtils.getCurrentDateWithTime());
    			employeeClaimDetailsDTO.setAppliedBy(telePhoneBillBean.getSfid());
    			employeeClaimDetailsDTO.setIpAddress(telePhoneBillBean.getIpAddress());
    			employeeClaimDetailsDTO.setStatus(1);
    			employeeClaimDetailsDTO.setWorkFlowStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
    			session.saveOrUpdate(employeeClaimDetailsDTO);
    		}
    		 }catch (Exception e) {
			throw e;
		   }finally{
			   hibernateUtils.closeSession();
		   }
    	 return message;
      }

//telephone bill submitRequestDetails
public TelePhoneBillBean submitTelephoneBillRequestDetails(TelePhoneBillBean telePhoneBillBean,Session session)throws Exception{
	//Session session=null;
	int Id=0;
	try{
		//session=hibernateUtils.getSession();
       //Id=Integer.parseInt(session.createSQLQuery("select to_char(id) from Pay_It_Fin_Year_Master where To_char(sysdate,'yyyy') >From_Year and To_char(sysdate,'yyyy')<=To_Year and status=1").uniqueResult().toString());
		 Id=Integer.parseInt(session.createSQLQuery("select to_char(id) from financial_year_master where sysdate >=to_date(From_date) and sysdate<to_date(To_date) and status=1").uniqueResult().toString());
		TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO = new TutionFeeRequestDetailsDTO();
		tutionFeeRequestDetailsDTO.setSfid(telePhoneBillBean.getSfid());
		tutionFeeRequestDetailsDTO.setRequestID(telePhoneBillBean.getRequestId());
		tutionFeeRequestDetailsDTO.setRequestType(telePhoneBillBean.getRequestType());
		tutionFeeRequestDetailsDTO.setRequestTypeID(telePhoneBillBean.getRequestTypeID());
		tutionFeeRequestDetailsDTO.setGrandToatal(Integer.parseInt(telePhoneBillBean.getGrandTotal()));
		tutionFeeRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
		tutionFeeRequestDetailsDTO.setCreatedBy(telePhoneBillBean.getSfid());
		tutionFeeRequestDetailsDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
		tutionFeeRequestDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
		tutionFeeRequestDetailsDTO.setLastModifiedBy(telePhoneBillBean.getSfid());
		tutionFeeRequestDetailsDTO.setSanctionedAmount(0);
		tutionFeeRequestDetailsDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TELEPHONEBILLCLAIMID));
		tutionFeeRequestDetailsDTO.setFromDate(telePhoneBillBean.getFromDate());
		tutionFeeRequestDetailsDTO.setToDate(telePhoneBillBean.getToDate());
		tutionFeeRequestDetailsDTO.setIpAddress(telePhoneBillBean.getIpAddress());
		tutionFeeRequestDetailsDTO.setFinancialYearId(Id);
		tutionFeeRequestDetailsDTO.setUserRemarks(telePhoneBillBean.getUserRemarks());
		tutionFeeRequestDetailsDTO.setMissingClaimRemarks(telePhoneBillBean.getMissingClaimRemarks());
		tutionFeeRequestDetailsDTO.setMissingPeriodFrom(telePhoneBillBean.getMissingPeriodFrom());
		tutionFeeRequestDetailsDTO.setMissingPeriodTo(telePhoneBillBean.getMissingPeriodTo());
		session.saveOrUpdate(tutionFeeRequestDetailsDTO);
		telePhoneBillBean.setMessage(CPSConstants.TELEPHONEBILLREQUEST);
		
		 // inserting details into tuitionFeeRequestdetails table
		int teleDesigId=((BigDecimal)session.createSQLQuery("select id from TELE_DESIG_ELIGIBILE_DETAILS where DESIGNATION_ID in(select dm.id from designation_master dm,emp_master em where dm.id=em.designation_id and em.sfid='"+telePhoneBillBean.getSfid()+"')").uniqueResult()).intValue();
	
		//list of claimDetails inserting into telephoneBillClaimDetails table
	    JSONObject jsonObj=new JSONObject(telePhoneBillBean.getMainTelephoneList());
	    for(int i=0;i<jsonObj.length();i++){
	    	JSONObject sunJSON = (JSONObject)jsonObj.get(String.valueOf(i));
	    	TelephoneBillClaimDetailsDTO telephoneBillClaimDetailsDTO=new TelephoneBillClaimDetailsDTO();
			telephoneBillClaimDetailsDTO.setSfid(telePhoneBillBean.getSfid());
			telephoneBillClaimDetailsDTO.setClaimId(Integer.parseInt(sunJSON.getString("claimId")));
			telephoneBillClaimDetailsDTO.setRequestId(telePhoneBillBean.getRequestId());
			telephoneBillClaimDetailsDTO.setTelephoneNo((sunJSON.getString("telephoneNo")));
			telephoneBillClaimDetailsDTO.setBillNo(sunJSON.getString("billNo"));
			String billDated=sunJSON.getString("billDated");
			if(CPSUtils.isNullOrEmpty(billDated)){
				telephoneBillClaimDetailsDTO.setBillDated(null);
			}else{
				telephoneBillClaimDetailsDTO.setBillDated((new SimpleDateFormat("dd-MMM-yyyy").parse(sunJSON.getString("billDated"))));
			}
			telephoneBillClaimDetailsDTO.setReceiptNo(sunJSON.getString("receiptNo"));
			String recieptDated=sunJSON.getString("recieptDated");
			if(CPSUtils.isNullOrEmpty(recieptDated)){
				telephoneBillClaimDetailsDTO.setReceiptDated(null);
			}else{
				telephoneBillClaimDetailsDTO.setReceiptDated((new SimpleDateFormat("dd-MMM-yyyy").parse(sunJSON.getString("recieptDated"))));
			}
	        telephoneBillClaimDetailsDTO.setAmountClaimed(Integer.parseInt((sunJSON.getString("amountClaimed"))));
	        telephoneBillClaimDetailsDTO.setServiceTax(sunJSON.getString("serviceTax"));
	        if(CPSUtils.isNullOrEmpty(sunJSON.getString("total"))){
	        	 telephoneBillClaimDetailsDTO.setTotal1(Integer.parseInt(String.valueOf(0)));
	        }else{
	        	 telephoneBillClaimDetailsDTO.setTotal1(Math.round(Float.parseFloat((sunJSON.getString("total")))));
	        }
	        telephoneBillClaimDetailsDTO.setInternetFlag(Integer.parseInt(telePhoneBillBean.getInternetFlag()));
	        telephoneBillClaimDetailsDTO.setCreatedBy(telePhoneBillBean.getSfid());
			telephoneBillClaimDetailsDTO.setLastModifiedBy(telePhoneBillBean.getSfid());
			telephoneBillClaimDetailsDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			telephoneBillClaimDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			telephoneBillClaimDetailsDTO.setEligibilityId(teleDesigId);
			telephoneBillClaimDetailsDTO.setSanctionedInternetFlag(Integer.parseInt(telePhoneBillBean.getInternetFlag()));
			telephoneBillClaimDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
			session.saveOrUpdate(telephoneBillClaimDetailsDTO);
			telePhoneBillBean.setMessage(CPSConstants.TELEPHONEBILLREQUEST);
			session.flush();
		 }
	}catch (Exception e) {
		throw e;
	}
	return telePhoneBillBean;
}
public String approveRequest(RequestBean rb)throws Exception{
	String message=null;
	Session session=null;
	int sanctionedAmount=0;
	int cashAssignmentAmount=0;
	try{
		session=hibernateUtils.getSession();
		rb=txRequestProcess.approvedRequest(rb);
		// code is modified to from update to success
		if(CPSUtils.compareString(CPSConstants.SUCCESS, rb.getMessage()) || CPSUtils.compareString(CPSConstants.UPDATE, rb.getMessage())){
			JSONObject jsonObj=new JSONObject(rb.getJsonValue()); 
			for(int i=0;i<jsonObj.length();i++){
				int cashAssignmentAmt=0;
				JSONObject innerJson =(JSONObject)jsonObj.get(String.valueOf(i));
				int indAmount=Integer.parseInt(innerJson.getString("subTotalAmount"));
				if(innerJson.has("cashAssignmentAmount")){
					cashAssignmentAmt=Integer.parseInt(innerJson.getString("cashAssignmentAmount"));
				}
				JSONObject claimJson=(JSONObject)innerJson.get("telephoneClaimDetails");
				for(int j=0;j<claimJson.length();j++){
					JSONObject innerClaimJson=(JSONObject)claimJson.get(String.valueOf(j));
					TelephoneBillClaimDetailsDTO telephoneBillClaimDetailsDTO=(TelephoneBillClaimDetailsDTO)session.createCriteria(TelephoneBillClaimDetailsDTO.class).add(Expression.eq("requestId", rb.getRequestID())).add(Expression.eq("id", innerClaimJson.getInt("claimId"))).uniqueResult();
					telephoneBillClaimDetailsDTO.setSanctionedAmount(Integer.parseInt(innerClaimJson.getString("sanctionedAmount")));
					telephoneBillClaimDetailsDTO.setSanctionedDate(CPSUtils.getCurrentDateWithTime());
					telephoneBillClaimDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
					session.saveOrUpdate(telephoneBillClaimDetailsDTO);
				}
				sanctionedAmount+=indAmount;
				if(innerJson.has("cashAssignmentAmount")){
				cashAssignmentAmount+=cashAssignmentAmt;
				}
			}
			TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO=(TutionFeeRequestDetailsDTO)session.createCriteria(TutionFeeRequestDetailsDTO.class).add(Expression.eq("requestID", rb.getRequestID())).uniqueResult();
			session.evict(tutionFeeRequestDetailsDTO);
			tutionFeeRequestDetailsDTO.setSanctionedAmount(sanctionedAmount);
			tutionFeeRequestDetailsDTO.setSanctionedDate(CPSUtils.getCurrentDateWithTime());
			tutionFeeRequestDetailsDTO.setCashAssignmentAmount(cashAssignmentAmount);
			tutionFeeRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
			tutionFeeRequestDetailsDTO.setTaskHolderRemarks(rb.getRemarks().replace(",", ""));
			session.saveOrUpdate(tutionFeeRequestDetailsDTO);
		}
		EmployeeClaimDetailsDTO employeeClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID",Integer.parseInt(rb.getRequestID()))).uniqueResult();
		session.evict(employeeClaimDetailsDTO);
		if(!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)){
			employeeClaimDetailsDTO.setWorkFlowStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
			session.saveOrUpdate(employeeClaimDetailsDTO);
		}
		message=rb.getMessage();
		message=CPSConstants.SUCCESS;
	}catch (Exception e) {
		e.printStackTrace();
		System.out.println("fghegfhjjgtrg");
		throw e;
	}
	return message;
}
 //telephonebill cancellation code
public String telephoneCancelRequest(RequestBean rb) throws Exception{
	String message=null;
	Session session=null;
	List<TelephoneBillClaimDetailsDTO> teleClaimList=null;
	try{
		session=hibernateUtils.getSession();
		TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO=(TutionFeeRequestDetailsDTO)session.createCriteria(TutionFeeRequestDetailsDTO.class).add(Expression.eq("requestID", rb.getRequestID())).uniqueResult();
		tutionFeeRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCANCELLED));
		
		teleClaimList=session.createCriteria(TelephoneBillClaimDetailsDTO.class).add(Expression.eq("requestId", rb.getRequestID())).list();
		for(TelephoneBillClaimDetailsDTO telephoneBillClaimDetailsDTO : teleClaimList){
			telephoneBillClaimDetailsDTO.setStatus(9);
			session.saveOrUpdate(telephoneBillClaimDetailsDTO);
		}
		message=CPSConstants.SUCCESS;
	}catch (Exception e) {
		throw e;
	}
	return message;
}
}

