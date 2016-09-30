package com.callippus.web.dao.tutionFee;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.telephone.TelephoneBillClaimDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeBean;
import com.callippus.web.beans.tutionFee.TutionFeeClaimDetailsDTO;
import com.callippus.web.beans.tutionFee.TutionFeeRequestDetailsDTO;
import com.callippus.web.business.requestprocess.RequestProcess;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
@Service
public class TutionFeeRequestProcess extends RequestProcess{
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	public String initWorkflow(TutionFeeBean tfb) throws Exception {
		String message="";
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			tfb.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			tfb.setRequestType(CPSConstants.TUTIONFEEREQUESTYPE);
			tfb.setRequestTypeID(CPSConstants.TUTIONFEEREQUESTTYPEID);
			tfb.setSfID(tfb.getSfid());
			tfb=submitTutionFeeRequestDetails(tfb,session);
			if(CPSUtils.compareStrings(CPSConstants.TUTIONFEEREQUEST, tfb.getMessage())){
				RequestBean rb =new RequestBean();
				BeanUtils.copyProperties(rb, tfb);
				rb.setRequestId(rb.getRequestID());
				message = txRequestProcess.initWorkflow(rb);
				message = CPSConstants.TUTIONFEEREQUEST;
			}
			if(CPSUtils.compareStrings(CPSConstants.TUTIONFEEREQUEST, message)){
				EmployeeClaimDetailsDTO empClaimDetailsDTO= new EmployeeClaimDetailsDTO();
				empClaimDetailsDTO.setModuleId(0);
				empClaimDetailsDTO.setFundId(0);
				empClaimDetailsDTO.setRequestType(CPSConstants.REIMBURSEMENT);
				empClaimDetailsDTO.setRequestID(Integer.parseInt(tfb.getRequestID()));
				empClaimDetailsDTO.setRequestTypeID(Integer.parseInt(CPSConstants.TUTIONFEEREQUESTTYPEID));
				empClaimDetailsDTO.setRefRequestId(Integer.parseInt(tfb.getRequestID()));
				empClaimDetailsDTO.setAmountClaimed(Integer.parseInt(tfb.getGrandTotal()));
				empClaimDetailsDTO.setAppliedDate(CPSUtils.getCurrentDateWithTime());
				empClaimDetailsDTO.setAppliedBy(tfb.getSfID());
				empClaimDetailsDTO.setIpAddress(tfb.getIpAddress());
				empClaimDetailsDTO.setStatus(1);
				empClaimDetailsDTO.setWorkFlowStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
				session.saveOrUpdate(empClaimDetailsDTO);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			hibernateUtils.closeSession();
		}
		return message;
	}
	public TutionFeeBean submitTutionFeeRequestDetails(TutionFeeBean tutionFeeBean,Session session) throws Exception{
		//Session session=null;
		int Id=0;
		try{
			//session=hibernateUtils.getSession();
			JSONObject sunJSON=null;
			JSONObject jsonObj=new JSONObject(tutionFeeBean.getMainClaimList());
			for(int i=0;i<jsonObj.length();i++){
				 sunJSON = (JSONObject)jsonObj.get(String.valueOf(i));
				String childRelationId=sunJSON.getString("childId");
				String limitId=sunJSON.getString("limitId");
				childRelationId=childRelationId.split("subfield")[1];
				JSONObject subSubJSON = (JSONObject) sunJSON.get("childList");
				for(int j=0;j<subSubJSON.length();j++){
					JSONObject claimJSON  = (JSONObject) subSubJSON.get(String.valueOf(j));
					TutionFeeClaimDetailsDTO tutionFeeClaimDetailsDTO = new TutionFeeClaimDetailsDTO();
					tutionFeeClaimDetailsDTO.setChildRelationId(Integer.parseInt(childRelationId));
					tutionFeeClaimDetailsDTO.setLimitId(Integer.parseInt(limitId));
					tutionFeeClaimDetailsDTO.setClassId(Integer.parseInt(sunJSON.getString("classId")));
					tutionFeeClaimDetailsDTO.setClaimId(Integer.parseInt(claimJSON.getString("claimId")));
					tutionFeeClaimDetailsDTO.setRequestId(Integer.parseInt(tutionFeeBean.getRequestID()));
					tutionFeeClaimDetailsDTO.setSfid(tutionFeeBean.getSfid());
					tutionFeeClaimDetailsDTO.setBoardType(sunJSON.getString("boardType"));
					if(CPSUtils.isNullOrEmpty("appNumber")){
						tutionFeeClaimDetailsDTO.setAppNo(null);
					}else{
						tutionFeeClaimDetailsDTO.setAppNo(claimJSON.getString("appNumber"));
					}
					//tutionFeeClaimDetailsDTO.setAppNo(claimJSON.getString("appNumber"));
					String appDate=claimJSON.getString("dated");
					if(CPSUtils.isNullOrEmpty(appDate)){
						tutionFeeClaimDetailsDTO.setAppDate(null);
					}else{
						tutionFeeClaimDetailsDTO.setAppDate(new SimpleDateFormat("dd-MMM-yyyy").parse(claimJSON.getString("dated")));
					}
					if(CPSUtils.isNullOrEmpty("amount")){
						tutionFeeClaimDetailsDTO.setAmount(0);
					}else{
						tutionFeeClaimDetailsDTO.setAmount(Integer.parseInt(claimJSON.getString("amount")));
					}
					//tutionFeeClaimDetailsDTO.setAmount(CPSUtils.isNullOrEmpty(claimJSON.getString("amount"))?0:Integer.parseInt(claimJSON.getString("amount")));
					tutionFeeClaimDetailsDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TUTIONFEECLAIMID));
					tutionFeeClaimDetailsDTO.setCreatedBy(tutionFeeBean.getSfid());
					tutionFeeClaimDetailsDTO.setLastModifiedBy(tutionFeeBean.getSfid());
					tutionFeeClaimDetailsDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
					tutionFeeClaimDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
					tutionFeeClaimDetailsDTO.setFromDate(CPSUtils.convertStringToDate(sunJSON.getString("fromDate")));
					tutionFeeClaimDetailsDTO.setToDate(CPSUtils.convertStringToDate(sunJSON.getString("toDate")));
					tutionFeeClaimDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
					session.saveOrUpdate(tutionFeeClaimDetailsDTO);
					session.flush();
				}
			}
			TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO =new TutionFeeRequestDetailsDTO();
			//Id=Integer.parseInt(session.createSQLQuery("select to_char(id) from Pay_It_Fin_Year_Master where To_char(sysdate,'yyyy') >From_Year and To_char(sysdate,'yyyy')<=To_Year and status=1").uniqueResult().toString());
			Id=Integer.parseInt(session.createSQLQuery("select to_char(id) from financial_year_master where sysdate >=to_date(From_date) and sysdate<to_date(To_date) and status=1").uniqueResult().toString());
			tutionFeeRequestDetailsDTO.setSfid(tutionFeeBean.getSfid());
			tutionFeeRequestDetailsDTO.setGrandToatal(Integer.parseInt(tutionFeeBean.getGrandTotal()));
			tutionFeeRequestDetailsDTO.setRequestID(tutionFeeBean.getRequestID());
			tutionFeeRequestDetailsDTO.setRequestType(tutionFeeBean.getRequestType());
			tutionFeeRequestDetailsDTO.setRequestTypeID(tutionFeeBean.getRequestTypeID());
			//tutionFeeRequestDetailsDTO.setFromDate(CPSUtils.convertStringToDate(sunJSON.getString("fromDate"))); // added fromdate and to date to save in request details table
			//tutionFeeRequestDetailsDTO.setToDate(CPSUtils.convertStringToDate(sunJSON.getString("toDate")));
			tutionFeeRequestDetailsDTO.setCreatedBy(tutionFeeBean.getSfid());
			tutionFeeRequestDetailsDTO.setLastModifiedBy(tutionFeeBean.getSfid());
			tutionFeeRequestDetailsDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
			tutionFeeRequestDetailsDTO.setLastModifiedDate(CPSUtils.getCurrentDateWithTime());
			tutionFeeRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSPENDING));
			tutionFeeRequestDetailsDTO.setIpAddress(tutionFeeBean.getIpAddress());
			tutionFeeRequestDetailsDTO.setClaimTypeId(Integer.parseInt(CPSConstants.TUTIONFEECLAIMID));
			tutionFeeRequestDetailsDTO.setFinancialYearId(Id);
			session.saveOrUpdate(tutionFeeRequestDetailsDTO);
			tutionFeeBean.setMessage(CPSConstants.TUTIONFEEREQUEST);
		}catch (Exception e) {
			throw e;
		}
		return tutionFeeBean;
	}
	@SuppressWarnings("unchecked")
	public String approveRequest(RequestBean rb) throws Exception{
		String message=null;
		Session session =null;
		int sanctionedAmt=0;
		List<TutionFeeClaimDetailsDTO>  tutionFeeClaimDetailsList=null;
		try{
			session=hibernateUtils.getSession();
			rb=txRequestProcess.approvedRequest(rb);
			if(CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage()) || CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())){
				TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO=(TutionFeeRequestDetailsDTO) session.createCriteria(TutionFeeRequestDetailsDTO.class).add(Expression.eq("requestID", rb.getRequestID())).uniqueResult();
				JSONObject jsonObj=new JSONObject(rb.getJsonValue()); 
				for(int j=0;j<jsonObj.length();j++){
					JSONObject innerJson=(JSONObject) jsonObj.get(String.valueOf(j));
					int childRelationId=Integer.parseInt(innerJson.getString("childId").substring(12));
					int indAmount=Integer.parseInt(innerJson.getString("subTotalAmount"));
					JSONObject claimJson=(JSONObject) innerJson.get("claimDetails");
					for(int i=0;i<claimJson.length();i++){
						JSONObject innerClaimJson=(JSONObject) claimJson.get(String.valueOf(i));
						tutionFeeClaimDetailsList=session.createCriteria(TutionFeeClaimDetailsDTO.class).
						add(Expression.eq("requestId", Integer.parseInt(rb.getRequestID()))).add(Expression.eq("childRelationId", childRelationId)).add(Expression.eq("claimId", Integer.parseInt(innerClaimJson.getString("claimId")))).list();
						
						for(TutionFeeClaimDetailsDTO tutionFeeClaimDetailsDTO : tutionFeeClaimDetailsList){
							if(Integer.parseInt(innerClaimJson.getString("tuitionClaimTableId"))==tutionFeeClaimDetailsDTO.getId()){
							tutionFeeClaimDetailsDTO.setSanctionedAmount(Integer.parseInt(innerClaimJson.getString("sanctionedAmount")));
							tutionFeeClaimDetailsDTO.setSanctionedDate(CPSUtils.getCurrentDateWithTime());
							tutionFeeClaimDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
							session.saveOrUpdate(tutionFeeClaimDetailsDTO);
							session.flush();
							}
						}
					}
					sanctionedAmt+=indAmount;
				}
				tutionFeeRequestDetailsDTO.setSanctionedAmount(sanctionedAmt);
				tutionFeeRequestDetailsDTO.setSanctionedDate(CPSUtils.getCurrentDateWithTime());
				tutionFeeRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
				session.saveOrUpdate(tutionFeeRequestDetailsDTO);
				
				EmployeeClaimDetailsDTO employeeClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(rb.getRequestID()))).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(employeeClaimDetailsDTO)){
					employeeClaimDetailsDTO.setWorkFlowStatus(Integer.parseInt(CPSConstants.STATUSCOMPLETED));
					session.saveOrUpdate(employeeClaimDetailsDTO);
				}
			}
			message=rb.getMessage();
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
		throw e;
		}
		return message;
	}
//tutionfee cancellation code
	@SuppressWarnings("unchecked")
	public String cancelRequest(RequestBean rb)throws Exception{
		String message=null;
		Session session=null;
		List<TutionFeeClaimDetailsDTO> claimList=null;
		try{
 			session=hibernateUtils.getSession();
			TutionFeeRequestDetailsDTO tutionFeeRequestDetailsDTO=(TutionFeeRequestDetailsDTO)session.createCriteria(TutionFeeRequestDetailsDTO.class).add(Expression.eq("requestID", rb.getRequestID())).uniqueResult();
			tutionFeeRequestDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCANCELLED));
			session.saveOrUpdate(tutionFeeRequestDetailsDTO);
			
			/*TutionFeeClaimDetailsDTO tutionFeeClaimDetailsDTO=(TutionFeeClaimDetailsDTO)session.createCriteria(TutionFeeClaimDetailsDTO.class).add(Expression.eq("requestId", Integer.parseInt(rb.getRequestID()))).L();
			tutionFeeClaimDetailsDTO.setStatus(Integer.parseInt(CPSConstants.STATUSCANCELLED));
			session.saveOrUpdate(tutionFeeClaimDetailsDTO);*/
			claimList=session.createCriteria(TutionFeeClaimDetailsDTO.class).add(Expression.eq("requestId",Integer.parseInt(rb.getRequestID()))).list();
			//session.saveOrUpdate(claimList);
			for (TutionFeeClaimDetailsDTO tutionFeeClaimDetailsDTO : claimList) {
				tutionFeeClaimDetailsDTO.setStatus(9);
				session.saveOrUpdate(tutionFeeClaimDetailsDTO);
			}
			message=CPSConstants.SUCCESS;
		}catch(Exception e){
			throw e;
		}
		return message;
	}

}
