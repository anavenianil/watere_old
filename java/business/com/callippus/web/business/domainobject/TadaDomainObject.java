package com.callippus.web.business.domainobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.MRODetailsDTO;
import com.callippus.web.beans.dto.MROPaymentDetailsDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dao.request.SQLTadaRequestDAO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaRequestProcessBean;
import com.callippus.web.tada.dto.TadaTdAccDetDayRepDTO;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;
import com.callippus.web.tada.dto.TadaTdDaNewAccDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaOldDetailsDTO;
import com.callippus.web.tada.dto.TadaTdFoodDetDayRepDTO;
import com.callippus.web.tada.dto.TadaTdJourneyDTO;
import com.callippus.web.tada.dto.TadaTdLeaveDetailsDTO;
import com.callippus.web.tada.dto.TadaTdLocalRMADetailsDTO;
import com.callippus.web.tada.dto.TadaTdRMADailyDTO;
import com.callippus.web.tada.dto.TadaTdRMADayDTO;
import com.callippus.web.tada.dto.TadaTdRMAKmDTO;
import com.callippus.web.tada.dto.TadaTdReqJourneyDTO;
import com.callippus.web.tada.dto.TadaTdSettlementDTO;
import com.callippus.web.tada.dto.TadaTdTotalFoodClaimDTO;
import com.callippus.web.tada.dto.TadaTdTxnDTO;

@Service
public class TadaDomainObject {
	
	
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private SQLTadaRequestDAO sqlTadaRequestDAO;
	
	public String updateTxnDetails (TadaRequestProcessBean tadaRPB)throws Exception {
		String message= "";
		Session session=null;
		StringBuffer sql=new StringBuffer();
		StringBuffer sql1=new StringBuffer();
		StringBuffer sql2=new StringBuffer();
		StringBuffer sql3=new StringBuffer();
		StringBuffer sql4=new StringBuffer();
		TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
		TadaApprovalRequestDTO tadaApprovalRequestDTO1=null;
		try {
            session=hibernateUtils.getSession();
            if(CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, tadaRPB.getType()) || CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, tadaRPB.getType())){
            	tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("ammendementId", tadaRPB.getRequestId())).uniqueResult();
            	tadaApprovalRequestDTO1=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRPB.getRequestId())).uniqueResult();
            	if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
            		if(CPSUtils.compareStrings(tadaApprovalRequestDTO.getAdvanceFlag(), "C")){
            			sql.append("update TadaApprovalRequestDTO set status = 9 where requestId=?");
                    	session.createQuery(sql.toString()).setString(0, tadaApprovalRequestDTO.getRequestId()).executeUpdate();
                    	session.createQuery("update TadaTdTxnDTO set status = 9 where requestId=?").setString(0, tadaApprovalRequestDTO.getRequestId()).executeUpdate();
                    	TadaTdTxnDTO tadaTdTxnDTO = (TadaTdTxnDTO)session.createCriteria(TadaTdTxnDTO.class).add(Expression.eq("requestId", tadaRPB.getRequestId())).uniqueResult();
                    	if(!CPSUtils.isNullOrEmpty(tadaTdTxnDTO)){
                    		tadaTdTxnDTO.setStatus(8);
                    		session.clear();
                        	session.saveOrUpdate(tadaTdTxnDTO);
                        	session.flush();
                    	}
                    	if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO1)){
                    		tadaApprovalRequestDTO1.setStatus(8);
                    		session.clear();
                        	session.saveOrUpdate(tadaApprovalRequestDTO1);
                        	session.flush();
                    	}
                    }else{
                		sql.append("update TadaApprovalRequestDTO set status = 8 where requestId=?");
                    	session.createQuery(sql.toString()).setString(0, tadaRPB.getRequestId()).executeUpdate();
                    	session.createQuery("update TadaTdTxnDTO set status = 8 where requestId=?").setString(0, tadaRPB.getRequestId()).executeUpdate();
                	}
            		
            	}else{
            		sql.append("update TadaApprovalRequestDTO set status = 8 where requestId=?");
                	session.createQuery(sql.toString()).setString(0, tadaRPB.getRequestId()).executeUpdate();
            	}
            	if(CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, tadaRPB.getType())){
            		TadaApprovalRequestDTO tadaApprovalRequestDTO2=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRPB.getRequestId())).uniqueResult();
            		if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO2) && CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO2.getProjectName()))
            			session.createQuery("update TadaApprovalRequestDTO set projectName = ? where requestId=?").setString(0, tadaRPB.getProjectType()).setString(1, tadaRPB.getRequestId()).executeUpdate();
            		message = CPSConstants.SUCCESS;
            	} else{
            		message = CPSConstants.SUCCESS;
            	}
            } else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, tadaRPB.getType())){
            	 TadaApprovalRequestDTO tdApprove = new TadaApprovalRequestDTO();
            	 JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
            	   if(!CPSUtils.isNullOrEmpty(mainJson.getString("projectName")))
            		   tdApprove.setProjectName(mainJson.getString("projectName"));
            	
            	 if(CPSUtils.compareStrings(tadaRPB.getApprovedStage(), "2")){
            		sql.append("update TadaTdAdvanceRequestDTO set status = 8, advanceAmountAftRes = ? where requestId=?");
                	session.createQuery(sql.toString()).setFloat(0, tadaRPB.getIssuedAdvAmount()).setString(1, tadaRPB.getRequestId()).executeUpdate();
                	message = CPSConstants.SUCCESS;
                	
                	if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
                		//TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO =new TadaTdAdvanceRequestDTO();
                		//tadaTdAdvanceRequestDTO = (TadaTdAdvanceRequestDTO) session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("requestId",tadaRPB.getRequestId())).uniqueResult();
                		sql3.append("update EmployeeClaimDetailsDTO set workFlowStatus=8 where requestID=?");
    	            	session.createQuery(sql3.toString()).setInteger(0, Integer.parseInt(tadaRPB.getRequestId())).executeUpdate();
    	            	message = CPSConstants.SUCCESS;
                	}
                	
                	if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
                		TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO =new TadaTdAdvanceRequestDTO();
                		tadaTdAdvanceRequestDTO = (TadaTdAdvanceRequestDTO) session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("requestId",tadaRPB.getRequestId())).uniqueResult();
                		sql2.append("update TadaApprovalRequestDTO set advanceFlag = 'Y',projectName = ? where requestId=?");
    	            	session.createQuery(sql2.toString()).setString(1, tadaTdAdvanceRequestDTO.getReferenceRequestID()).setString(0, tdApprove.getProjectName()).executeUpdate();
    	            	message = CPSConstants.SUCCESS;
                	}
                	
            	}
            	
            	JSONObject tdAdvDetailsJson=(JSONObject)mainJson.get("tdAdvDetails");
            	for(int i=0;i<tdAdvDetailsJson.length();i++){
            		JSONObject valueJson=(JSONObject)tdAdvDetailsJson.get(String.valueOf(i));
            		TadaTdReqJourneyDTO tadaTdReqJourneyDTO=new TadaTdReqJourneyDTO();
            		tadaTdReqJourneyDTO=(TadaTdReqJourneyDTO)session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("id", valueJson.getInt("id"))).uniqueResult();
            		tadaTdReqJourneyDTO.setTicketFareAftRes(Float.parseFloat(valueJson.getString("ticketFareAftRes")));
                    session.clear();
            		session.saveOrUpdate(tadaTdReqJourneyDTO);
            		session.flush();
            	}
            	
            	session.createQuery("update TadaTdAdvanceRequestDTO set accAmtPerDay=?,foodAmtPerDay=?,advanceAmountAftRes=?,daType=? where requestId=?").setFloat(0, Float.parseFloat(mainJson.getString("accAmtPerDay"))).setFloat(1, Float.parseFloat(mainJson.getString("foodAmtPerDay"))).setFloat(2, tadaRPB.getIssuedAdvAmount()).setString(3, mainJson.getString("issueAuthority")).setString(4, mainJson.getString("reqId")).executeUpdate();
            	session.createQuery("update TadaApprovalRequestDTO set projectName=? where requestId=?").setString(0, tdApprove.getProjectName()).setString(1, tadaRPB.getRequestId()).executeUpdate();
            	
            }
            if(CPSUtils.compareStrings(tadaRPB.getApprovedStage(), "2")){
            	if(CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
                	if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
                    	sql1.append("update TadaTdTxnDTO set status = 8 where requestId=?");
                    	session.createQuery(sql1.toString()).setString(0, tadaRPB.getRequestId()).executeUpdate();
                    	message = CPSConstants.SUCCESS;
                    }
                }
            }
            if(!CPSUtils.compareStrings(tadaRPB.getApprovedStage(), "1")){
            	if(!CPSUtils.compareStrings(tadaRPB.getParam(), "wait")){
            		if(CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, tadaRPB.getType())){
                    	EmployeeClaimDetailsDTO empClaimDetailsDTO = (EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRPB.getRequestId()))).add(Expression.eq("requestTypeID", 49)).add(Expression.eq("workFlowStatus", 2)).uniqueResult();
                    	sql4.append("update TadaApprovalRequestDTO set reimFlag = 'Y' where requestId=?");
                    	session.createQuery(sql4.toString()).setInteger(0, empClaimDetailsDTO.getRefRequestId()).executeUpdate();
                    	message = CPSConstants.SUCCESS; 
                    }
                	if(CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, tadaRPB.getType())){
                    	EmployeeClaimDetailsDTO empClaimDetailsDTO = (EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRPB.getRequestId()))).add(Expression.eq("requestTypeID", 48)).add(Expression.or(Expression.eq("workFlowStatus", 2), Expression.eq("workFlowStatus", 8))).uniqueResult();
                    	sql4.append("update TadaApprovalRequestDTO set settFlag = 'Y' where requestId=?");
                    	session.createQuery(sql4.toString()).setInteger(0, empClaimDetailsDTO.getRefRequestId()).executeUpdate();
                    	message = CPSConstants.SUCCESS; 
                    }
            	}
            	if(CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, tadaRPB.getType()) || CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, tadaRPB.getType())){
            		if(!CPSUtils.compareStrings(tadaRPB.getParam(), "wait")){
            			sql.append("update EmployeeClaimDetailsDTO set workFlowStatus = 8 where requestID=?");
                    	session.createQuery(sql.toString()).setInteger(0, Integer.parseInt(tadaRPB.getRequestId())).executeUpdate();
            		}
                 	session.createQuery("update TadaTdSettlementDTO set amountClaimedAftRes=? where requestID=?").setFloat(0, tadaRPB.getTotalTadaTdCalAmount()).setInteger(1, Integer.parseInt(tadaRPB.getRequestId())).executeUpdate();
                	message = CPSConstants.SUCCESS;
                }
            }
            /*if(!CPSUtils.isNullOrEmpty(tadaRPB.getStayDaNewAmount())) {
            	String daNewAmountRestriction[] = tadaRPB.getStayDaNewAmount().split("@");
                for(int i=0;i<daNewAmountRestriction.length;i++) {
                	String idValue[] = daNewAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdDaNewDetailsDTO set stayDurationAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getFoodDaNewAmount())) {
            	String daNewAmountRestriction[] = tadaRPB.getFoodDaNewAmount().split("@");
                for(int i=0;i<daNewAmountRestriction.length;i++) {
                	String idValue[] = daNewAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdDaNewDetailsDTO set foodAmountAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getAccDaNewAmount())) {
            	String daNewAmountRestriction[] = tadaRPB.getAccDaNewAmount().split("@");
                for(int i=0;i<daNewAmountRestriction.length;i++) {
                	String idValue[] = daNewAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdDaNewDetailsDTO set accAmountAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getDaNewAmount())) {
            	String daNewAmountRestriction[] = tadaRPB.getDaNewAmount().split("@");
                for(int i=0;i<daNewAmountRestriction.length;i++) {
                	String idValue[] = daNewAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdDaNewDetailsDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getDistanceAftRes())) {
            	String rmaKmAmountRestriction[] = tadaRPB.getDistanceAftRes().split("@");
                for(int i=0;i<rmaKmAmountRestriction.length;i++) {
                	String idValue[] = rmaKmAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdRMAKmDTO set distanceAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getAmountPerKmAftRes())) {
            	String rmaKmAmountRestriction[] = tadaRPB.getAmountPerKmAftRes().split("@");
                for(int i=0;i<rmaKmAmountRestriction.length;i++) {
                	String idValue[] = rmaKmAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdRMAKmDTO set amountPerKmAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getRmaKmAmount())) {
            	String rmaKmAmountRestriction[] = tadaRPB.getRmaKmAmount().split("@");
                for(int i=0;i<rmaKmAmountRestriction.length;i++) {
                	String idValue[] = rmaKmAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdRMAKmDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getAmountPerDayAftRes())) {
            	String rmaDayAmountRestriction[] = tadaRPB.getAmountPerDayAftRes().split("@");
                for(int i=0;i<rmaDayAmountRestriction.length;i++) {
                	String idValue[] = rmaDayAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdRMADayDTO set amountPerDayAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getRmaDayAmount())) {
            	String rmaDayAmountRestriction[] = tadaRPB.getRmaDayAmount().split("@");
                for(int i=0;i<rmaDayAmountRestriction.length;i++) {
                	String idValue[] = rmaDayAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdRMADayDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getJourneyAmount())) {
            	String journeyAmountRestriction[] = tadaRPB.getJourneyAmount().split("@");
                for(int i=0;i<journeyAmountRestriction.length;i++) {
                	String idValue[] = journeyAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdJourneyDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStayDaOldAmount())){
            	String daOldAmountRestriction[] = tadaRPB.getStayDaOldAmount().split("@");
                for(int i=0;i<daOldAmountRestriction.length;i++) {
                	String idValue[] = daOldAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdDaOldDetailsDTO set stayDurationAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                } 
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getAmountPerDay())){
            	String daOldAmountRestriction[] = tadaRPB.getAmountPerDay().split("@");
                for(int i=0;i<daOldAmountRestriction.length;i++) {
                	String idValue[] = daOldAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdDaOldDetailsDTO set amountPerDayAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                } 
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getDaOldAmount())){
            	String daOldAmountRestriction[] = tadaRPB.getDaOldAmount().split("@");
                for(int i=0;i<daOldAmountRestriction.length;i++) {
                	String idValue[] = daOldAmountRestriction[i].split("-");
                	session.createQuery("update  TadaTdDaOldDetailsDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                } 
            }*/
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrTicketFareAftRes())) {
            	String ticketFareAftRes[] = tadaRPB.getStrTicketFareAftRes().split("@");
            	 for(int i=0;i<ticketFareAftRes.length;i++) {
                	String idValue[] = ticketFareAftRes[i].split("-");
                	session.createQuery("update  TadaTdJourneyDTO set ticketFareAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrClaimedJourneyAmtAftRes())){
            	String claimedJourneyAmtAftRes[] = tadaRPB.getStrClaimedJourneyAmtAftRes().split("@");
            	for(int i=0;i<claimedJourneyAmtAftRes.length;i++) {
                	String idValue[] = claimedJourneyAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdJourneyDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrAccAmtAftRes()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrClaimedAccAmtAftRes())) {
            	String accAmtAftRes[] = tadaRPB.getStrAccAmtAftRes().split("@");
            	String claimedAccAmtAftRes[] = tadaRPB.getStrClaimedAccAmtAftRes().split("@");
                for(int i=0;i<accAmtAftRes.length;i++) {
                	String idValue[] = accAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdDaNewAccDetailsDTO set accAmountAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<claimedAccAmtAftRes.length;i++) {
                	String idValue[] = claimedAccAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdDaNewAccDetailsDTO set claimedAmountAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrRMAKmDisAftRes()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrRMAKmAmtAftRes()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrClaimedRMAKmAftRes())) {
            	String RMAKmDisAftRes[] = tadaRPB.getStrRMAKmDisAftRes().split("@");
            	String RMAKmAmtAftRes[] = tadaRPB.getStrRMAKmAmtAftRes().split("@");
            	String claimedRMAKmAftRes[] = tadaRPB.getStrClaimedRMAKmAftRes().split("@");
                for(int i=0;i<RMAKmDisAftRes.length;i++) {
                	String idValue[] = RMAKmDisAftRes[i].split("-");
                	session.createQuery("update  TadaTdRMAKmDTO set distanceAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<RMAKmAmtAftRes.length;i++) {
                	String idValue[] = RMAKmAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdRMAKmDTO set amountPerKmAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<claimedRMAKmAftRes.length;i++) {
                	String idValue[] = claimedRMAKmAftRes[i].split("-");
                	session.createQuery("update  TadaTdRMAKmDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrRMADailyDisAftRes()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrRMADailyAmtAftRes()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrClaimedRMADailyAftRes())) {
            	String RMADailyDisAftRes[] = tadaRPB.getStrRMADailyDisAftRes().split("@");
            	String RMADailyAmtAftRes[] = tadaRPB.getStrRMADailyAmtAftRes().split("@");
            	String claimedRMADailyAftRes[] = tadaRPB.getStrClaimedRMADailyAftRes().split("@");
                for(int i=0;i<RMADailyDisAftRes.length;i++) {
                	String idValue[] = RMADailyDisAftRes[i].split("-");
                	session.createQuery("update  TadaTdRMADailyDTO set distanceAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<RMADailyAmtAftRes.length;i++) {
                	String idValue[] = RMADailyAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdRMADailyDTO set amountPerKmAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<claimedRMADailyAftRes.length;i++) {
                	String idValue[] = claimedRMADailyAftRes[i].split("-");
                	session.createQuery("update  TadaTdRMADailyDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrRMALocalDisAftRes()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrRMALocalAmtAftRes()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrClaimedRMALocalAftRes())) {
            	String RMALocalDisAftRes[] = tadaRPB.getStrRMALocalDisAftRes().split("@");
            	String RMALocalAmtAftRes[] = tadaRPB.getStrRMALocalAmtAftRes().split("@");
            	String claimedRMALocalAftRes[] = tadaRPB.getStrClaimedRMALocalAftRes().split("@");
                for(int i=0;i<RMALocalDisAftRes.length;i++) {
                	String idValue[] = RMALocalDisAftRes[i].split("-");
                	session.createQuery("update  TadaTdLocalRMADetailsDTO set localDistanceAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<RMALocalAmtAftRes.length;i++) {
                	String idValue[] = RMALocalAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdLocalRMADetailsDTO set amountPerKmLocalAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<claimedRMALocalAftRes.length;i++) {
                	String idValue[] = claimedRMALocalAftRes[i].split("-");
                	session.createQuery("update  TadaTdLocalRMADetailsDTO set claimedAmountAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrRMADayAmtPerDay()) && !CPSUtils.isNullOrEmpty(tadaRPB.getStrRMADayAmtAftRes())) {
            	String RMADayAmtPerDay[] = tadaRPB.getStrRMADayAmtPerDay().split("@");
            	String RMADayAmtAftRes[] = tadaRPB.getStrRMADayAmtAftRes().split("@");
                for(int i=0;i<RMADayAmtPerDay.length;i++) {
                	String idValue[] = RMADayAmtPerDay[i].split("-");
                	session.createQuery("update  TadaTdRMADayDTO set amountPerDayAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
                for(int i=0;i<RMADayAmtAftRes.length;i++) {
                	String idValue[] = RMADayAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdRMADayDTO set amountAftRestriction=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }
            /*if(!CPSUtils.isNullOrEmpty(tadaRPB.getStrFoodAmtAftRes())) {
            	String foodAmtAftRes[] = tadaRPB.getStrFoodAmtAftRes().split("@");
                for(int i=0;i<foodAmtAftRes.length;i++) {
                	String idValue[] = foodAmtAftRes[i].split("-");
                	session.createQuery("update  TadaTdDaNewFoodDetailsDTO set foodAmountAftRes=? where id=?").setString(0, idValue[0]).setString(1, idValue[1]).executeUpdate();
                }
            }*/
            if(CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, tadaRPB.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, tadaRPB.getRequestType())){
            	JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
    			JSONObject oldDaJson = (JSONObject)mainJson.getJSONObject("oldDaDetails");
    			for(int i=0;i<oldDaJson.length();i++){
    				JSONObject subJson=(JSONObject)oldDaJson.get(String.valueOf(i));
    				TadaTdDaOldDetailsDTO tadaTdDaOldDetailsDTO=(TadaTdDaOldDetailsDTO)session.get(TadaTdDaOldDetailsDTO.class, subJson.getInt("id"));
    				if(!CPSUtils.isNullOrEmpty(tadaTdDaOldDetailsDTO)){
    					tadaTdDaOldDetailsDTO.setJdaDays(Float.parseFloat(subJson.getString("jdadays")));
    					tadaTdDaOldDetailsDTO.setJdaAmount(Float.parseFloat(subJson.getString("jdaAmount")));
    					tadaTdDaOldDetailsDTO.setTotalJdaAmount(Float.parseFloat(subJson.getString("jdaAmountTot")));
    					tadaTdDaOldDetailsDTO.setSdaDays(Float.parseFloat(subJson.getString("sdadays")));
    					tadaTdDaOldDetailsDTO.setSdaAmount(Float.parseFloat(subJson.getString("sdaAmount")));
    					tadaTdDaOldDetailsDTO.setTotalSdaAmount(Float.parseFloat(subJson.getString("sdaAmountTot")));
    					session.clear();
    					session.saveOrUpdate(tadaTdDaOldDetailsDTO);
    					session.flush();
    				}
    			}
                if(!CPSUtils.isNullOrEmpty(tadaRPB.getTotalFoodAmount()) && !CPSUtils.compareStrings(tadaRPB.getTotalFoodAmount(), "undefined")){
                	TadaTdTotalFoodClaimDTO tadaTdFoodClaimDTO = (TadaTdTotalFoodClaimDTO)session.createCriteria(TadaTdTotalFoodClaimDTO.class).add(Expression.eq("settlementRequestId", tadaRPB.getRequestId())).uniqueResult();
                	if(CPSUtils.isNullOrEmpty(tadaTdFoodClaimDTO)){
                		tadaTdFoodClaimDTO=new TadaTdTotalFoodClaimDTO();
                	}
                	tadaTdFoodClaimDTO.setFoodAmountChoice("rep1");
                	tadaTdFoodClaimDTO.setSettlementRequestId(tadaRPB.getRequestId());
                	tadaTdFoodClaimDTO.setTotalClaimedFoodAmount(Float.parseFloat(tadaRPB.getTotalFoodAmount()));
                	session.clear();
                	session.saveOrUpdate(tadaTdFoodClaimDTO);
                	session.flush();
                }
            	JSONObject journeyJson=(JSONObject)mainJson.get("journeyDetails");
            	for(int i=0;i<journeyJson.length();i++){
            		JSONObject valueJson=(JSONObject)journeyJson.get(String.valueOf(i));
            		if(valueJson.has("id"))
            		{
            			TadaTdJourneyDTO tadaTdJourneyDTO=new TadaTdJourneyDTO();
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
            			tadaTdJourneyDTO=(TadaTdJourneyDTO)session.get(TadaTdJourneyDTO.class, valueJson.getInt("id"));
            		
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), "") && CPSUtils.compareStrings(valueJson.getString("editType"), "delete"))
            			session.delete(tadaTdJourneyDTO);
            		    session.flush();
            		}
            		
            	}
            	for(int i=0;i<journeyJson.length();i++){
            		JSONObject valueJson=(JSONObject)journeyJson.get(String.valueOf(i));
            		if(valueJson.has("id"))
            		{
            			TadaTdJourneyDTO tadaTdJourneyDTO=new TadaTdJourneyDTO();
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
            			tadaTdJourneyDTO=(TadaTdJourneyDTO)session.get(TadaTdJourneyDTO.class, valueJson.getInt("id"));
            		
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), "") && CPSUtils.compareStrings(valueJson.getString("editType"), "delete"))
            			session.delete(tadaTdJourneyDTO);
            		
            		}
            		
            	}
            	for(int i=0;i<journeyJson.length();i++){
            		JSONObject valueJson=(JSONObject)journeyJson.get(String.valueOf(i));
            		if(valueJson.has("id"))
            		{
            			TadaTdJourneyDTO tadaTdJourneyDTO=new TadaTdJourneyDTO();
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
            			tadaTdJourneyDTO=(TadaTdJourneyDTO)session.get(TadaTdJourneyDTO.class, valueJson.getInt("id"));
            		tadaTdJourneyDTO.setSettlementRequestId(tadaRPB.getRequestId());	
            		tadaTdJourneyDTO.setJourneyDate(CPSUtils.convertStringToDate(valueJson.getString("journeyDeptDate")));
            		tadaTdJourneyDTO.setStartTime(valueJson.getString("journeyDeptTime"));
            		tadaTdJourneyDTO.setStartStation(valueJson.getString("journeyDeptStn"));
            		tadaTdJourneyDTO.setJourneyEndDate(CPSUtils.convertStringToDate(valueJson.getString("journeyArrDate")));
            		tadaTdJourneyDTO.setEndTime(valueJson.getString("journeyArrTime"));
            		tadaTdJourneyDTO.setEndStation(valueJson.getString("journeyArrStn"));
            		tadaTdJourneyDTO.setDistanceJourney(Float.parseFloat(valueJson.getString("journeyDistance")));
            		tadaTdJourneyDTO.setModeOfTravel(valueJson.getString("modeOfTravel"));
            		tadaTdJourneyDTO.setTicketFare(Float.parseFloat(valueJson.getString("ticketFare")));
            		tadaTdJourneyDTO.setTicketFareAftRes(Float.parseFloat(valueJson.getString("ticketFareAftRes")));
            		tadaTdJourneyDTO.setTotalJourneyAmount(Float.parseFloat(valueJson.getString("ticketCancelAmount")));
            		tadaTdJourneyDTO.setTicketNo(valueJson.getString("ticketNo"));
            		tadaTdJourneyDTO.setAmountAftRestriction(Float.parseFloat(valueJson.getString("ticketCancelAmountAftRes")));
            		
            		  if(CPSUtils.compareStrings(valueJson.getString("editType"), "add"))
            			  session.clear();
            			session.saveOrUpdate(tadaTdJourneyDTO);
            		    session.flush();
            		}
            		
            	}
            	///
            	JSONObject accJson=(JSONObject)mainJson.get("accDetails");
            	for(int i=0;i<accJson.length();i++){
            		JSONObject valueJson1=(JSONObject)accJson.get(String.valueOf(i));
            		if(valueJson1.has("id"))
            		{
            		TadaTdDaNewAccDetailsDTO tadaTdDaNewAccDetailsDTO=new TadaTdDaNewAccDetailsDTO();
            		if(!CPSUtils.compareStrings(valueJson1.getString("id"), ""))
            			tadaTdDaNewAccDetailsDTO=(TadaTdDaNewAccDetailsDTO)session.get(TadaTdDaNewAccDetailsDTO.class, valueJson1.getInt("id"));
            	    if(!CPSUtils.compareStrings(valueJson1.getString("id"), "") && CPSUtils.compareStrings(valueJson1.getString("editType"), "delete"))
            			session.delete(tadaTdDaNewAccDetailsDTO);
            	        session.flush();
            		}
            	}
            		for(int j=0;j<accJson.length();j++){
                		JSONObject valueJson=(JSONObject)accJson.get(String.valueOf(j));
                		if(valueJson.has("id"))
                		{
                		TadaTdDaNewAccDetailsDTO tadaTdDaNewAccDetailsDTO=new TadaTdDaNewAccDetailsDTO();
                		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
                			tadaTdDaNewAccDetailsDTO=(TadaTdDaNewAccDetailsDTO)session.get(TadaTdDaNewAccDetailsDTO.class, valueJson.getInt("id"));
                		if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDSETTLEMENTS))
                			tadaTdDaNewAccDetailsDTO.setType("tadaTdSettlement");
                		else if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDREIMBURSEMENT))
                			tadaTdDaNewAccDetailsDTO.setType("tadaTdReimbursement");
                		tadaTdDaNewAccDetailsDTO.setSettlementRequestId(tadaRPB.getRequestId());
                		tadaTdDaNewAccDetailsDTO.setFromDate(CPSUtils.convertStringToDate(valueJson.getString("accFromDate")));
                		tadaTdDaNewAccDetailsDTO.setToDate(CPSUtils.convertStringToDate(valueJson.getString("accToDate")));
                		tadaTdDaNewAccDetailsDTO.setAccAmount(Float.parseFloat(valueJson.getString("accAmount")));
                		tadaTdDaNewAccDetailsDTO.setAccAmountAftRes(Float.parseFloat(valueJson.getString("accAmountAftRes")));
                		tadaTdDaNewAccDetailsDTO.setClaimedAmount(Float.parseFloat(valueJson.getString("claimedAmount")));
                		tadaTdDaNewAccDetailsDTO.setClaimedAmountAftRes(Float.parseFloat(valueJson.getString("claimedAmountAftRes")));
                		
                	 if(CPSUtils.compareStrings(valueJson.getString("editType"), "add"))
                		 session.clear();
                			session.saveOrUpdate(tadaTdDaNewAccDetailsDTO);
                		    session.flush();
            		
            		}
            	}
            	JSONObject foodJson=(JSONObject)mainJson.get("foodDetails");
            	for(int i=0;i<foodJson.length();i++){
            		JSONObject valueJson1=(JSONObject)foodJson.get(String.valueOf(i));
            		if(valueJson1.has("id"))
            		{
            		TadaTdDaNewFoodDetailsDTO tadaTdDaNewFoodDetailsDTO=new TadaTdDaNewFoodDetailsDTO();
            		if(!CPSUtils.compareStrings(valueJson1.getString("id"), ""))
            			tadaTdDaNewFoodDetailsDTO=(TadaTdDaNewFoodDetailsDTO)session.get(TadaTdDaNewFoodDetailsDTO.class, valueJson1.getInt("id"));
            		if(!CPSUtils.compareStrings(valueJson1.getString("id"), "") && CPSUtils.compareStrings(valueJson1.getString("editType"), "delete"))
            			session.delete(tadaTdDaNewFoodDetailsDTO);
            		    session.flush();
            		}
            	}
            		for(int j=0;j<foodJson.length();j++){
                		JSONObject valueJson=(JSONObject)foodJson.get(String.valueOf(j));
                		if(valueJson.has("id"))
                		{
                		TadaTdDaNewFoodDetailsDTO tadaTdDaNewFoodDetailsDTO=new TadaTdDaNewFoodDetailsDTO();
                		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
                			tadaTdDaNewFoodDetailsDTO=(TadaTdDaNewFoodDetailsDTO)session.get(TadaTdDaNewFoodDetailsDTO.class, valueJson.getInt("id"));
                		if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDSETTLEMENTS))
                			tadaTdDaNewFoodDetailsDTO.setType("tadaTdSettlement");
                		else if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDREIMBURSEMENT))
                			tadaTdDaNewFoodDetailsDTO.setType("tadaTdReimbursement");
                		tadaTdDaNewFoodDetailsDTO=(TadaTdDaNewFoodDetailsDTO)session.get(TadaTdDaNewFoodDetailsDTO.class, valueJson.getInt("id"));
                		tadaTdDaNewFoodDetailsDTO.setFromDate(CPSUtils.convertStringToDate(valueJson.getString("foodFromDate")));
                		tadaTdDaNewFoodDetailsDTO.setToDate(CPSUtils.convertStringToDate(valueJson.getString("foodToDate")));
                		tadaTdDaNewFoodDetailsDTO.setFoodAmount(Float.parseFloat(valueJson.getString("foodAmount")));
                		tadaTdDaNewFoodDetailsDTO.setFoodAmountAftRes(Float.parseFloat(valueJson.getString("foodAmountAftRes")));
                		tadaTdDaNewFoodDetailsDTO.setClaimedAmount(Float.parseFloat(valueJson.getString("claimedFoodAmount")));
                		
                		if(CPSUtils.compareStrings(valueJson.getString("editType"), "add"))
                			session.clear();
                			session.saveOrUpdate(tadaTdDaNewFoodDetailsDTO);
                		    session.flush();

                		
                		}
                	}
            		
            	////
            	JSONObject rmaKmJson=(JSONObject)mainJson.get("rmaKmDetails");
            	for(int i=0;i<rmaKmJson.length();i++){
            		JSONObject valueJson1=(JSONObject)rmaKmJson.get(String.valueOf(i));
            		if(valueJson1.has("id"))
            		{
            		TadaTdRMAKmDTO tadaTdRMAKmDTO=new TadaTdRMAKmDTO();
            		if(!CPSUtils.compareStrings(valueJson1.getString("id"), ""))
            			tadaTdRMAKmDTO=(TadaTdRMAKmDTO)session.get(TadaTdRMAKmDTO.class, valueJson1.getInt("id"));
            		
            		if(!CPSUtils.compareStrings(valueJson1.getString("id"), "") && CPSUtils.compareStrings(valueJson1.getString("editType"), "delete"))
            			session.delete(tadaTdRMAKmDTO);
            		    session.flush();
            		}
            	}
            		for(int j=0;j<rmaKmJson.length();j++){
                		JSONObject valueJson=(JSONObject)rmaKmJson.get(String.valueOf(j));
                		if(valueJson.has("id"))
                		{
                		TadaTdRMAKmDTO tadaTdRMAKmDTO=new TadaTdRMAKmDTO();
                		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
                			tadaTdRMAKmDTO=(TadaTdRMAKmDTO)session.get(TadaTdRMAKmDTO.class, valueJson.getInt("id"));
                		if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDSETTLEMENTS))
                			tadaTdRMAKmDTO.setType("tadaTdSettlement");
                		else if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDREIMBURSEMENT))
                			tadaTdRMAKmDTO.setType("tadaTdReimbursement");
                		tadaTdRMAKmDTO.setSettlementRequestId(tadaRPB.getRequestId());
                		tadaTdRMAKmDTO.setDateRMAKm(CPSUtils.convertStringToDate(valueJson.getString("rmaKmDate")));
                		tadaTdRMAKmDTO.setFromPlace(valueJson.getString("rmaKmFromPlace"));
                		tadaTdRMAKmDTO.setToPlace(valueJson.getString("rmaKmToPlace"));
                		tadaTdRMAKmDTO.setTravelBy(valueJson.getString("rmaKmTravelBy"));
                		tadaTdRMAKmDTO.setDistance(Float.parseFloat(valueJson.getString("rmaKmDiatance")));
                		tadaTdRMAKmDTO.setDistanceAftRes(Float.parseFloat(valueJson.getString("rmaKmDiatanceAftRes")));
                		tadaTdRMAKmDTO.setAmountPerKm(Float.parseFloat(valueJson.getString("rmaKmAmountPerKm")));
                		tadaTdRMAKmDTO.setAmountPerKmAftRes(Float.parseFloat(valueJson.getString("rmaKmAmountPerKmAftRes")));
                		tadaTdRMAKmDTO.setTotalRMAKmAmount(Float.parseFloat(valueJson.getString("rmaKmClaimedAmount")));
                		tadaTdRMAKmDTO.setAmountAftRestriction(Float.parseFloat(valueJson.getString("rmaKmClaimedAmountAftRes")));
                		if(!CPSUtils.compareStrings(valueJson.getString("id"), "") && CPSUtils.compareStrings(valueJson.getString("editType"), "delete"))
                			session.delete(tadaTdRMAKmDTO);
                		else if(CPSUtils.compareStrings(valueJson.getString("editType"), "add"))
                			session.clear();
                			session.saveOrUpdate(tadaTdRMAKmDTO);
                		    session.flush();
                		}
                	}
            		
           //// 		
            		
            	JSONObject rmaDayJson=(JSONObject)mainJson.get("rmaDayDetails");
            	for(int i=0;i<rmaDayJson.length();i++){
            		JSONObject valueJson=(JSONObject)rmaDayJson.get(String.valueOf(i));
            		if(valueJson.has("id"))
            		{
            		TadaTdRMADayDTO tadaTdRMADayDTO=new TadaTdRMADayDTO();
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
            			tadaTdRMADayDTO=(TadaTdRMADayDTO)session.get(TadaTdRMADayDTO.class, valueJson.getInt("id"));
            		
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), "") && CPSUtils.compareStrings(valueJson.getString("editType"), "delete"))
            			session.delete(tadaTdRMADayDTO);
            		
            		else if(CPSUtils.compareStrings(valueJson.getString("editType"), "add"))
            			session.saveOrUpdate(tadaTdRMADayDTO);
            		    session.flush();
            		}
            	}
            		for(int i=0;i<rmaDayJson.length();i++){
                		JSONObject valueJson=(JSONObject)rmaDayJson.get(String.valueOf(i));
                		if(valueJson.has("id"))
                		{
                		TadaTdRMADayDTO tadaTdRMADayDTO=new TadaTdRMADayDTO();
                		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
                			tadaTdRMADayDTO=(TadaTdRMADayDTO)session.get(TadaTdRMADayDTO.class, valueJson.getInt("id"));
                		if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDSETTLEMENTS))
                			tadaTdRMADayDTO.setType("tadaTdSettlement");
                		else if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDREIMBURSEMENT))
                			tadaTdRMADayDTO.setType("tadaTdReimbursement");
                		tadaTdRMADayDTO.setSettlementRequestId(tadaRPB.getRequestId());
                		tadaTdRMADayDTO.setDateRMADay(CPSUtils.convertStringToDate(valueJson.getString("rmaDayDate")));
                		tadaTdRMADayDTO.setFromPlace(valueJson.getString("rmaDayFromPlace"));
                		tadaTdRMADayDTO.setToPlace(valueJson.getString("rmaDayToPlace"));
                		tadaTdRMADayDTO.setAmountPerDay(Float.parseFloat(valueJson.getString("rmaDayAmountPerDay")));
                		tadaTdRMADayDTO.setAmountPerDayAftRes(Float.parseFloat(valueJson.getString("rmaDayAmountPerDayAftRes")));
                		tadaTdRMADayDTO.setTotalRMADayAmount(Float.parseFloat(valueJson.getString("rmaDayClaimedAmount")));
                		tadaTdRMADayDTO.setAmountAftRestriction(Float.parseFloat(valueJson.getString("rmaDayClaimedAmountAftRes")));
                	   if(CPSUtils.compareStrings(valueJson.getString("editType"), "add"))
                		   session.clear();
                			session.saveOrUpdate(tadaTdRMADayDTO);
                		    session.flush();
                		}
                	}
            		
            	JSONObject rmaDailyJson=(JSONObject)mainJson.get("rmaDailyDetails");
            	for(int i=0;i<rmaDailyJson.length();i++){
            		JSONObject valueJson=(JSONObject)rmaDailyJson.get(String.valueOf(i));
            		if(valueJson.has("id"))
            		{
            		TadaTdRMADailyDTO tadaTdRMADailyDTO=new TadaTdRMADailyDTO();
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
            			tadaTdRMADailyDTO=(TadaTdRMADailyDTO)session.get(TadaTdRMADailyDTO.class, valueJson.getInt("id"));
            		
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), "") && CPSUtils.compareStrings(valueJson.getString("editType"), "delete")){
            			valueJson.remove("id");                               //The prasad 
            			session.delete(tadaTdRMADailyDTO);
            		    session.flush();
            		}
            			
            		}
            	}
            	
            	for(int i=0;i<rmaDailyJson.length();i++){
            		JSONObject valueJson=(JSONObject)rmaDailyJson.get(String.valueOf(i));
            		if(valueJson.has("id"))
            		{
            		TadaTdRMADailyDTO tadaTdRMADailyDTO=new TadaTdRMADailyDTO();
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
            			tadaTdRMADailyDTO=(TadaTdRMADailyDTO)session.get(TadaTdRMADailyDTO.class, valueJson.getInt("id"));
            		if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDSETTLEMENTS))
            			tadaTdRMADailyDTO.setType("tadaTdSettlement");
            		else if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDREIMBURSEMENT))
            			tadaTdRMADailyDTO.setType("tadaTdReimbursement");
            		tadaTdRMADailyDTO.setSettlementRequestId(tadaRPB.getRequestId());
            		tadaTdRMADailyDTO.setDateRMAKm(CPSUtils.convertStringToDate(valueJson.getString("rmaDailyDate")));
            		tadaTdRMADailyDTO.setFromPlace(valueJson.getString("rmaDailyFromPlace"));
            		tadaTdRMADailyDTO.setToPlace(valueJson.getString("rmaDailyToPlace"));
            		tadaTdRMADailyDTO.setTravelBy(valueJson.getString("rmaDailyTravelBy"));
            		tadaTdRMADailyDTO.setDistance(Float.parseFloat(valueJson.getString("rmaDailyDiatance")));
            		tadaTdRMADailyDTO.setDistanceAftRes(Float.parseFloat(valueJson.getString("rmaDailyDiatanceAftRes")));
            		tadaTdRMADailyDTO.setAmountPerKm(Float.parseFloat(valueJson.getString("rmaDailyAmountPerKm")));
            		tadaTdRMADailyDTO.setAmountPerKmAftRes(Float.parseFloat(valueJson.getString("rmaDailyAmountPerKmAftRes")));
            		tadaTdRMADailyDTO.setTotalRMAKmAmount(Float.parseFloat(valueJson.getString("rmaDailyClaimedAmount")));
            		tadaTdRMADailyDTO.setAmountAftRestriction(Float.parseFloat(valueJson.getString("rmaDailyClaimedAmountAftRes")));
            		
            		if(CPSUtils.compareStrings(valueJson.getString("editType"), "add"))
            			session.clear();
            			session.saveOrUpdate(tadaTdRMADailyDTO);
            		    session.flush();
            		}
            	}
            	JSONObject rmaLocalJson=(JSONObject)mainJson.get("rmaLocalDetails");
            	for(int i=0;i<rmaLocalJson.length();i++){
            		JSONObject valueJson=(JSONObject)rmaLocalJson.get(String.valueOf(i));
            		if(valueJson.has("id"))
            		{
            		TadaTdLocalRMADetailsDTO tadaTdLocalRMADetailsDTO=new TadaTdLocalRMADetailsDTO();
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), ""))
            			tadaTdLocalRMADetailsDTO=(TadaTdLocalRMADetailsDTO)session.get(TadaTdLocalRMADetailsDTO.class, valueJson.getInt("id"));
            		
            		if(!CPSUtils.compareStrings(valueJson.getString("id"), "") && CPSUtils.compareStrings(valueJson.getString("editType"), "delete")){
            			valueJson.remove("id");  
            			session.delete(tadaTdLocalRMADetailsDTO);
            		}
            		    session.flush();
            		}
            	}
            		for(int j=0;j<rmaLocalJson.length();j++){
                		JSONObject valueJson1=(JSONObject)rmaLocalJson.get(String.valueOf(j));
                		if(valueJson1.has("id"))
                		{
                		TadaTdLocalRMADetailsDTO tadaTdLocalRMADetailsDTO=new TadaTdLocalRMADetailsDTO();
                		if(!CPSUtils.compareStrings(valueJson1.getString("id"), ""))
                			tadaTdLocalRMADetailsDTO=(TadaTdLocalRMADetailsDTO)session.get(TadaTdLocalRMADetailsDTO.class, valueJson1.getInt("id"));
                		if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDSETTLEMENTS))
                			tadaTdLocalRMADetailsDTO.setType("tadaTdSettlement");
                		else if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDREIMBURSEMENT))
                			tadaTdLocalRMADetailsDTO.setType("tadaTdReimbursement");
                		tadaTdLocalRMADetailsDTO.setSettlementRequestId(tadaRPB.getRequestId());
                		tadaTdLocalRMADetailsDTO.setLocalRMADate(CPSUtils.convertStringToDate(valueJson1.getString("rmaLocalDate")));
                		tadaTdLocalRMADetailsDTO.setLocalFromPlace(valueJson1.getString("rmaLocalFromPlace"));
                		tadaTdLocalRMADetailsDTO.setLocalToPlace(valueJson1.getString("rmaLocalToPlace"));
                		tadaTdLocalRMADetailsDTO.setLocalCMode(valueJson1.getString("rmaLocalTravelBy"));
                		tadaTdLocalRMADetailsDTO.setLocalDistance(Float.parseFloat(valueJson1.getString("rmaLocalDiatance")));
                		tadaTdLocalRMADetailsDTO.setLocalDistanceAftRes(Float.parseFloat(valueJson1.getString("rmaLocalDiatanceAftRes")));
                		tadaTdLocalRMADetailsDTO.setAmountPerKmLocal(Float.parseFloat(valueJson1.getString("rmaLocalAmountPerKm")));
                		tadaTdLocalRMADetailsDTO.setAmountPerKmLocalAftRes(Float.parseFloat(valueJson1.getString("rmaLocalAmountPerKmAftRes")));
                		tadaTdLocalRMADetailsDTO.setClaimedAmount(Float.parseFloat(valueJson1.getString("rmaLocalClaimedAmount")));
                		tadaTdLocalRMADetailsDTO.setClaimedAmountAftRes(Float.parseFloat(valueJson1.getString("rmaLocalClaimedAmountAftRes")));
            		 if(CPSUtils.compareStrings(valueJson1.getString("editType"), "add"))
            			 session.clear();
            			session.saveOrUpdate(tadaTdLocalRMADetailsDTO);
            		    session.flush();
            		}
            		}
            	
            	JSONObject foodDay=(JSONObject)mainJson.get("foodDayDetails");
            	for(int i=0;i<foodDay.length();i++){
            		JSONObject valueJson=(JSONObject)foodDay.get(String.valueOf(i));
            		TadaTdFoodDetDayRepDTO tadaTdFoodDetDayRepDTO=new TadaTdFoodDetDayRepDTO();
            		tadaTdFoodDetDayRepDTO=(TadaTdFoodDetDayRepDTO)session.get(TadaTdFoodDetDayRepDTO.class, valueJson.getInt("id"));
            		tadaTdFoodDetDayRepDTO.setRepresentationDate(valueJson.getString("repDate"));
            		tadaTdFoodDetDayRepDTO.setRepresentationAmount1(Float.parseFloat(valueJson.getString("repAmount")));
            		session.clear();
            		session.saveOrUpdate(tadaTdFoodDetDayRepDTO);
            		session.flush();
            	}
            	JSONObject accDay=(JSONObject)mainJson.get("accDayDetails");
            	for(int i=0;i<accDay.length();i++){
            		JSONObject valueJson=(JSONObject)accDay.get(String.valueOf(i));
            		TadaTdAccDetDayRepDTO tadaTdAccDetDayRepDTO=new TadaTdAccDetDayRepDTO();
            		tadaTdAccDetDayRepDTO=(TadaTdAccDetDayRepDTO)session.get(TadaTdAccDetDayRepDTO.class, valueJson.getInt("id"));
            		tadaTdAccDetDayRepDTO.setRepresentationDate(valueJson.getString("repDate"));
            		tadaTdAccDetDayRepDTO.setAdmisAmount(Float.parseFloat(valueJson.getString("repAmount")));
            		session.clear();
            		session.saveOrUpdate(tadaTdAccDetDayRepDTO);
            		session.flush();
            	}
               
            }
            
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	@SuppressWarnings("unchecked")
	public String updateTxnDetailsStatus (String statusID,String requestId,Class beanName,String ipAddress)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
		String sql1=null;
		String sql2=null;
		String sql3=null;
		TadaTdAdvanceRequestDTO tadaAdvReqDTO=new TadaTdAdvanceRequestDTO();
		TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
		String advReqId=null;
        try {
            session = hibernateUtils.getSession();
            if(CPSUtils.compareStrings(beanName.getSimpleName(), "TadaTdAdvanceRequestDTO")){
            	sql = "update "+beanName.getSimpleName()+" set status=? where requestId=?";
            	session.createQuery(sql).setString(0, statusID).setString(1, requestId).executeUpdate();
            	tadaAdvReqDTO = (TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("requestId", requestId)).uniqueResult();
            	sql1 = "update TadaApprovalRequestDTO set advanceFlag = 'N' where requestId=?";
            	session.createQuery(sql1).setString(0, tadaAdvReqDTO.getReferenceRequestID()).executeUpdate();
            	sql2 = "update TadaTdTxnDTO set status=? where requestId=?";
            	session.createQuery(sql2).setString(0, statusID).setString(1, requestId).executeUpdate();
            	sql3 = "update TadaTdReqJourneyDTO set ticketFare=0, ticketFareAftRes=0 where referenceId=?";
            	session.createQuery(sql3).setInteger(0, Integer.parseInt(tadaAdvReqDTO.getReferenceRequestID())).executeUpdate();
            	message = CPSConstants.SUCCESS;
            } else{
            	tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("ammendementId", requestId)).uniqueResult();
            	if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
            		sql1 = "update TadaApprovalRequestDTO set status=8  where requestId=?";
            		session.createQuery(sql1).setString(0, tadaApprovalRequestDTO.getRequestId()).executeUpdate();
            		
            		sql2 = "update TadaTdTxnDTO set status=8 where requestId=?";
            		session.createQuery(sql2).setString(0, tadaApprovalRequestDTO.getRequestId()).executeUpdate();
            		
            		sql3 = "update RequestCommonBean set status=8,remarks='' where requestID=?";
            		session.createQuery(sql3).setString(0, tadaApprovalRequestDTO.getRequestId()).executeUpdate();
            		
            		TadaApprovalRequestDTO tadaApprovalRequestDTO2=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", requestId)).uniqueResult();
            		if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO2)){
            			advReqId = (String)session.createSQLQuery("select to_char(max(ttard.request_id)) from tada_td_adv_request_details ttard where ttard.ref_request_id=?").setString(0, requestId).uniqueResult();
            			if(!CPSUtils.isNullOrEmpty(advReqId)){
            				if(tadaApprovalRequestDTO2.getStayDuration()==0){
            					if(CPSUtils.compareStrings(tadaApprovalRequestDTO2.getAdvanceFlag(), "Y") || CPSUtils.compareStrings(tadaApprovalRequestDTO2.getAdvanceFlag(), "C")){
            						session.createQuery("update TadaTdAdvanceRequestDTO set status=8 where requestId=?").setString(0, advReqId).executeUpdate();
                                	session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=8 where requestID=?").setInteger(0, Integer.parseInt(advReqId)).executeUpdate();
                                	session.createSQLQuery("update request_workflow_history set status=8 where id=(select max(id) from request_workflow_history where request_id=?)").setInteger(0, Integer.parseInt(advReqId)).executeUpdate();
                                	session.createQuery("update TadaApprovalRequestDTO set status=9 where requestId=?").setString(0, tadaApprovalRequestDTO2.getRequestId()).executeUpdate();
                                	session.createQuery("update TadaTdTxnDTO set status=9 where requestId=?").setString(0, tadaApprovalRequestDTO2.getRequestId()).executeUpdate();
            					}else if(CPSUtils.compareStrings(tadaApprovalRequestDTO2.getAdvanceFlag(), "P")){
            						session.createQuery("update TadaTdAdvanceRequestDTO set status=2 where requestId=?").setString(0, advReqId).executeUpdate();
                                	session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=2 where requestID=?").setInteger(0, Integer.parseInt(advReqId)).executeUpdate();
                                	session.createSQLQuery("update request_workflow_history set status=2 where id=(select max(id) from request_workflow_history where request_id=?)").setInteger(0, Integer.parseInt(advReqId)).executeUpdate();
            					}
            					
            				}else{
            					session.createQuery("update TadaTdAdvanceRequestDTO set referenceRequestID=? where requestId=?").setString(0, tadaApprovalRequestDTO.getRequestId()).setString(1, advReqId).executeUpdate();
                    			session.createQuery("update EmployeeClaimDetailsDTO set refRequestId=? where requestID=?").setInteger(0, Integer.parseInt(tadaApprovalRequestDTO.getRequestId())).setInteger(1, Integer.parseInt(advReqId)).executeUpdate();
            				}
            			}else if(tadaApprovalRequestDTO2.getStayDuration()==0){
            				TadaTdTxnDTO tadaTdTxnDTO=(TadaTdTxnDTO)session.createCriteria(TadaTdTxnDTO.class).add(Expression.eq("requestId", tadaApprovalRequestDTO2.getRequestId())).uniqueResult();
            				tadaTdTxnDTO.setStatus(Integer.parseInt(statusID));
            				session.clear();
            				session.saveOrUpdate(tadaTdTxnDTO);
            				tadaApprovalRequestDTO2.setStatus(Integer.parseInt(statusID));
            				session.clear();
                			session.saveOrUpdate(tadaApprovalRequestDTO2);
            			}else{
            				sql = "update "+beanName.getSimpleName()+" set status=? where requestId=?";
                            Query qry=session.createQuery(sql);
                            qry.setString(0, statusID);
                            qry.setString(1, requestId);
                            qry.executeUpdate();
            			}
                    }
            	}else{
            		TadaApprovalRequestDTO tadaApprovalRequestDTO2=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", requestId)).uniqueResult();
            		session.createQuery("update TadaTdTxnDTO set status=9 where requestId=?").setString(0, requestId).executeUpdate();
            		if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO2)){
            			if(CPSUtils.compareStrings(tadaApprovalRequestDTO2.getAdvanceFlag(), "P") || CPSUtils.compareStrings(tadaApprovalRequestDTO2.getAdvanceFlag(), "Y")){
            				TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("referenceRequestID", requestId)).add(Expression.ne("status", 9)).uniqueResult();
            				if(!CPSUtils.isNullOrEmpty(tadaTdAdvanceRequestDTO)){
            					tadaTdAdvanceRequestDTO.setStatus(Integer.parseInt(statusID));
            					session.createSQLQuery("update request_workflow_history set status=9,stage_status=9,actioned_date=sysdate,ip_address=? where id=(select max(id) from request_workflow_history where request_id=?)").setString(0, ipAddress).setInteger(1, Integer.parseInt(tadaTdAdvanceRequestDTO.getRequestId())).executeUpdate();
            					session.createQuery("update TadaTdTxnDTO set status=9 where requestId=?").setString(0, tadaTdAdvanceRequestDTO.getRequestId()).executeUpdate();
            				}
            			}
            			tadaApprovalRequestDTO2.setStatus(Integer.parseInt(statusID));
            			session.clear();
            			session.saveOrUpdate(tadaApprovalRequestDTO2);
            			session.flush();
            		}
            	}
                message = CPSConstants.SUCCESS;
            }
            
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	@SuppressWarnings("unchecked")
	public String updateSettlementTxnDetailsStatus (String statusID,String requestId,Class beanName)throws Exception {
		String message= "";
		Session session=null;
		String sql = null;
        try {
            session = hibernateUtils.getSession();
            sql = "update "+beanName.getSimpleName()+" set workFlowStatus=? where requestID=?";
            Query qry=session.createQuery(sql);
            qry.setString(0, statusID);
            qry.setString(1, requestId);
            qry.executeUpdate();
             message = CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	public String updateTdReimDetails(String status, String requestID)throws Exception{
		String message=null;
		Session session=null;
		session=hibernateUtils.getSession();
		try{
			session.createSQLQuery("update TADA_TD_REQUEST_DETAILS set REIM_FLAG='N' where REQUEST_ID=(select REF_REQUEST_ID from EMP_CLAIM_DETAILS where REQUEST_ID=?)").setInteger(0, Integer.parseInt(requestID)).executeUpdate();
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String updateTdSettDetails(String status, String requestID)throws Exception{
		String message=null;
		Session session=null;
		session=hibernateUtils.getSession();
		try{
			session.createSQLQuery("update TADA_TD_REQUEST_DETAILS set SETT_FLAG='N' where REQUEST_ID=(select REF_REQUEST_ID from EMP_CLAIM_DETAILS where REQUEST_ID=?)").setInteger(0, Integer.parseInt(requestID)).executeUpdate();
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	/*public String submitMroDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			MRODetailsDTO mroDetailsDTO=new MRODetailsDTO();
			mroDetailsDTO.setReferenceID(Integer.parseInt(tadaRPB.getRequestId()));
			if(CPSUtils.compareStrings(tadaRPB.getPenalInterestReq(), "yes")){
				mroDetailsDTO.setNoOfDays(Integer.parseInt(tadaRPB.getNoOfDays()));
				mroDetailsDTO.setPenalInterestAmount(Integer.parseInt(tadaRPB.getExcessAmountFine()));
				int fineAmount=Integer.parseInt(tadaRPB.getExcessAmountFine());
				float amount=Float.parseFloat(tadaRPB.getExcessAmount());
				if(fineAmount<0){
					fineAmount=(-1)*(fineAmount);
				}
				if(amount<0){
					amount=(-1)*(amount);
				}
				mroDetailsDTO.setUnUtilizedBalance(amount);
				mroDetailsDTO.setTotalAmount(fineAmount+amount);
			} else {
				mroDetailsDTO.setNoOfDays(0);
				mroDetailsDTO.setPenalInterestAmount(0);
				//int fineAmount=Integer.parseInt(tadaRPB.getExcessAmountFine());
				float amount=Float.parseFloat(tadaRPB.getExcessAmount());
//				if(fineAmount<0){
//					fineAmount=(-1)*(fineAmount);
//				}
				if(amount<0){
					amount=(-1)*(amount);
				}
				mroDetailsDTO.setUnUtilizedBalance(amount);
				mroDetailsDTO.setTotalAmount(amount);
			}
			mroDetailsDTO.setCreatedBy(tadaRPB.getSfID());
			mroDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
			mroDetailsDTO.setLastModifiedBy(tadaRPB.getSfID());
			mroDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			mroDetailsDTO.setPenalInterestId(tadaRPB.getPenalInterestId());
			
			session.saveOrUpdate(mroDetailsDTO);
			hibernateUtils.closeSession();
		}catch(Exception e){
			throw e;
		}
		return message;
	}*/
	@SuppressWarnings("unchecked")
	public String submitMroDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		try{
			
			session=hibernateUtils.getSession();
			JSONObject tempJson = new JSONObject(tadaRPB.getJsonValue());
			JSONObject mainJson = (JSONObject)tempJson.getJSONObject("mroDetails");
	
			for(int i=0;i<mainJson.length();i++){
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				MRODetailsDTO mroDetailsDTO=new MRODetailsDTO();
				
				
				if(CPSUtils.compareStrings(subJson.getString("id"), "")){
					mroDetailsDTO.setReferenceID(Integer.parseInt(tadaRPB.getRequestId()));
					if(CPSUtils.compareStrings(subJson.getString("penalInterestReq"), "yes")){
						mroDetailsDTO.setNoOfDays(subJson.getInt("noOfDays"));
						mroDetailsDTO.setUnUtilizedBalance(subJson.getInt("unUtliliseBal"));    //here replace (-1)*Float.parseFloat(tadaRPB.getExcessAmount() with subJson.getInt("unUtliliseBal")
						mroDetailsDTO.setTotalAmount(subJson.getInt("penalInterest")+(subJson.getInt("unUtliliseBal")));   // (-1)*Integer.parseInt(tadaRPB.getExcessAmount()
					}else{
						mroDetailsDTO.setNoOfDays(0);
						mroDetailsDTO.setUnUtilizedBalance(subJson.getInt("unUtliliseBal"));    //here replace (-1)*Float.parseFloat(tadaRPB.getExcessAmount() with subJson.getInt("unUtliliseBal")
						mroDetailsDTO.setTotalAmount(subJson.getInt("unUtliliseBal"));    //here replace (-1)*Float.parseFloat(tadaRPB.getExcessAmount() with subJson.getInt("unUtliliseBal")
					}
					mroDetailsDTO.setPenalInterestAmount(subJson.getInt("penalInterest"));
					mroDetailsDTO.setCreatedBy(tadaRPB.getSfID());
					mroDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					mroDetailsDTO.setLastModifiedBy(tadaRPB.getSfID());
					mroDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					if(CPSUtils.isNullOrEmpty(tadaRPB.getPenalInterestId())){
						mroDetailsDTO.setPenalInterestId(subJson.getInt("PenalInterestId"));
					}
					if(!CPSUtils.isNullOrEmpty(tadaRPB.getPenalInterestId())){
					mroDetailsDTO.setPenalInterestId(tadaRPB.getPenalInterestId());}
					session.saveOrUpdate(mroDetailsDTO);
					session.flush();
				}else{                                //This Condition has been added for Tada Settlement hold changes
					
					mroDetailsDTO=(MRODetailsDTO)session.createCriteria(MRODetailsDTO.class).add(Expression.eq("id", subJson.getInt("id"))).uniqueResult();
					mroDetailsDTO.setId(mroDetailsDTO.getId());
         			mroDetailsDTO.setNoOfDays(subJson.getInt("noOfDays"));
					mroDetailsDTO.setUnUtilizedBalance(subJson.getInt("unUtliliseBal"));
					mroDetailsDTO.setPenalInterestAmount(subJson.getInt("penalInterest"));
					mroDetailsDTO.setTotalAmount(mroDetailsDTO.getUnUtilizedBalance()+mroDetailsDTO.getPenalInterestAmount());
					
					mroDetailsDTO.setCreatedBy(tadaRPB.getSfID());
					mroDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					mroDetailsDTO.setLastModifiedBy(tadaRPB.getSfID());
					mroDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					mroDetailsDTO.setPenalInterestId(tadaRPB.getPenalInterestId());
					session.saveOrUpdate(mroDetailsDTO);
					session.clear();
					session.saveOrUpdate(mroDetailsDTO);
					session.flush();
					
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	/*@SuppressWarnings("unchecked")
	public String submitMroPaymentDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		List<MRODetailsDTO> mroDetailsList=null;
		try{
			session=hibernateUtils.getSession();
			mroDetailsList=session.createCriteria(MRODetailsDTO.class).add(Expression.eq("referenceID", Integer.parseInt(tadaRPB.getRequestId()))).list();
			for (MRODetailsDTO mroDetailsDTO : mroDetailsList) {
				MROPaymentDetailsDTO mroPaymentDetailsDTO1=(MROPaymentDetailsDTO)session.createCriteria(MROPaymentDetailsDTO.class).add(Expression.eq("referenceID", mroDetailsDTO.getId())).uniqueResult();
				if(CPSUtils.isNullOrEmpty(mroPaymentDetailsDTO1)){
					MROPaymentDetailsDTO mroPaymentDetailsDTO=new MROPaymentDetailsDTO();
					mroPaymentDetailsDTO.setReferenceID(mroDetailsDTO.getId());
					mroPaymentDetailsDTO.setMroNumber(tadaRPB.getMROPaidNo());
					mroPaymentDetailsDTO.setMroDate(tadaRPB.getMROPaidDate());
					mroPaymentDetailsDTO.setTotalAmount(mroDetailsDTO.getTotalAmount());
					mroPaymentDetailsDTO.setCreatedBy(tadaRPB.getSfID());
					mroPaymentDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
					mroPaymentDetailsDTO.setLastModifiedBy(tadaRPB.getSfID());
					mroPaymentDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
					session.saveOrUpdate(mroPaymentDetailsDTO);
					break;
				}else{
					session.saveOrUpdate(mroPaymentDetailsDTO1);
				}
				
			}
			message=CPSConstants.SUCCESS;
		}catch(Exception e){
			throw e;
		}
		return message;
	}*/
	public String submitMroPaymentDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		MRODetailsDTO mroDetailsDTO=null;
		try{
			session=hibernateUtils.getSession();
			JSONObject tempJson = new JSONObject(tadaRPB.getJsonValue());
			JSONObject mainJson = (JSONObject)tempJson.getJSONObject("mroPaymentDetails");
			
			
			
			for(int i=0;i<mainJson.length();i++){
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				
				mroDetailsDTO=(MRODetailsDTO)session.createCriteria(MRODetailsDTO.class).add(Expression.eq("id", subJson.getInt("mroId"))).uniqueResult();
				MROPaymentDetailsDTO mroPaymentDetailsDTO=new MROPaymentDetailsDTO();
				mroPaymentDetailsDTO.setReferenceID(mroDetailsDTO.getId());
				if(subJson.has("mroNo")){
					mroPaymentDetailsDTO.setMroNumber(subJson.getString("mroNo"));
				}else{
					mroPaymentDetailsDTO.setMroNumber(null);
				}
				if(subJson.has("mroDate")){
					mroPaymentDetailsDTO.setMroDate(CPSUtils.convertStringToDate(subJson.get("mroDate").toString()));
				}else{
					mroPaymentDetailsDTO.setMroDate(null);
				}
				
				mroPaymentDetailsDTO.setTotalAmount(mroDetailsDTO.getTotalAmount());
				mroPaymentDetailsDTO.setCreatedBy(tadaRPB.getSfID());
				mroPaymentDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				mroPaymentDetailsDTO.setLastModifiedBy(tadaRPB.getSfID());
				mroPaymentDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				session.clear();
				session.saveOrUpdate(mroPaymentDetailsDTO);
				session.flush();
			}
			message=CPSConstants.SUCCESS;
		}catch(Exception e){
			throw e;
		}
		return message;
	}

	@SuppressWarnings({ "unchecked", "null" })
	public List<TadaTdAccDetDayRepDTO> submitTdNewDaAccDetails(TadaRequestProcessBean tadaRPB)throws Exception{



		
	    List<TadaTdAccDetDayRepDTO> daNewAccDayRepList = null;

		String message=null;
		Session session = null;
		float claimedAmount=0;
		float eligibleAmount=0;
		float admisAmount=0;
		List<TadaTdDaNewAccDetailsDTO> daNewAccList=null;
		String strMinDate=null;
		String strMaxDate=null;
		Date minDate=null;
		Date maxDate=null;
		Date tempToDate = null;
		TadaTdAccDetDayRepDTO tadaTdAccDetDayRepDTO=null;
		List<TadaTdAccDetDayRepDTO> tempTdAccList=new ArrayList<TadaTdAccDetDayRepDTO>();
		List<LeaveRequestBean> leaveDetailsList=new ArrayList<LeaveRequestBean>();
		List<TadaTdLeaveDetailsDTO> tdAttachedLeaveList=null;
		int leaveDays=0;
		String leaveStartDate=null;
		List<TadaTdAccDetDayRepDTO> tdAccLeaveList=new ArrayList<TadaTdAccDetDayRepDTO>();
		List<TadaTdAccDetDayRepDTO> duplicateList=new ArrayList<TadaTdAccDetDayRepDTO>();
		Float daOnTourPercentage=null;  //This property for DaOnTour percentage
		try {
		

						 

			
			
			 session = hibernateUtils.getSession();
			
			
		
			 

			duplicateList=session.createCriteria(TadaTdAccDetDayRepDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(tadaRPB.getRequestId()))).list();



			if(duplicateList.size()>0){
			Query query = session.createSQLQuery("delete from TADA_TD_ACC_DET_DAY_REP where id=?");
			for(int i=0;i<=duplicateList.size()-1;i++){
			query.setInteger(0, duplicateList.get(i).getId()).executeUpdate();
			}
			session.flush();
			
			}
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			JSONObject accJson=(JSONObject)mainJson.get("accDetails");
        	for(int n=0;n<accJson.length();n++){
        		JSONObject valueJson1=(JSONObject)accJson.get(String.valueOf(n));
        		if(valueJson1.has("id"))
        		{
        		TadaTdDaNewAccDetailsDTO tadaTdDaNewAccDetailsDTO= new TadaTdDaNewAccDetailsDTO();
        		if(!CPSUtils.compareStrings(valueJson1.getString("id"), ""))
        			tadaTdDaNewAccDetailsDTO=(TadaTdDaNewAccDetailsDTO)session.createCriteria(TadaTdDaNewAccDetailsDTO.class).add(Expression.eq("id", valueJson1.getInt("id"))).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(tadaTdDaNewAccDetailsDTO)){
					TadaTdSettlementDTO tadaTdSettlementDTO1=(TadaTdSettlementDTO)session.createCriteria(TadaTdSettlementDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRPB.getRequestId()))).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(tadaTdSettlementDTO1)){
						if(CPSUtils.compareStrings(tadaTdSettlementDTO1.getRequestType(), "settlement"))
							tadaTdDaNewAccDetailsDTO.setType("tadaTdSettlement");
	            		else if(CPSUtils.compareStrings(tadaTdSettlementDTO1.getRequestType(), "reimbursement"))
	            			tadaTdDaNewAccDetailsDTO.setType("tadaTdReimbursement");
					}
        			
        			
        			
        		tadaTdDaNewAccDetailsDTO.setSettlementRequestId(tadaRPB.getRequestId());
        		tadaTdDaNewAccDetailsDTO.setFromDate(CPSUtils.convertStringToDate(valueJson1.getString("accFromDate")));
        		tadaTdDaNewAccDetailsDTO.setToDate(CPSUtils.convertStringToDate(valueJson1.getString("accToDate")));
        		tadaTdDaNewAccDetailsDTO.setAccAmount(Float.parseFloat(valueJson1.getString("accAmount")));
        		tadaTdDaNewAccDetailsDTO.setAccAmountAftRes(Float.parseFloat(valueJson1.getString("accAmountAftRes")));
        		tadaTdDaNewAccDetailsDTO.setClaimedAmount(Float.parseFloat(valueJson1.getString("claimedAmount")));
        		tadaTdDaNewAccDetailsDTO.setClaimedAmountAftRes(Float.parseFloat(valueJson1.getString("claimedAmountAftRes")));
        		if(!CPSUtils.compareStrings(valueJson1.getString("id"), "") && CPSUtils.compareStrings(valueJson1.getString("editType"), "delete")){
        			session.delete(tadaTdDaNewAccDetailsDTO);
        		    session.flush();
				}
        		else if(CPSUtils.compareStrings(valueJson1.getString("editType"), "add")){
        			session.saveOrUpdate(tadaTdDaNewAccDetailsDTO);
        		    session.flush();
        		    session.clear();
        		}
        		}
        	}
        	}
        	
			duplicateList=session.createCriteria(TadaTdAccDetDayRepDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(tadaRPB.getRequestId()))).list();
			

			if(duplicateList.size()==0){
				daNewAccList=session.createCriteria(TadaTdDaNewAccDetailsDTO.class).add(Expression.eq("settlementRequestId", tadaRPB.getRequestId())).addOrder(Order.asc("fromDate")).addOrder(Order.desc("toDate")).list(); 
				TadaDaNewDetailsDTO tadaDaNewDetailsDTO=new TadaDaNewDetailsDTO();
				EmployeeClaimDetailsDTO empClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRPB.getRequestId()))).uniqueResult();
				EmpPaymentsDTO empPaymentsDTO=(EmpPaymentsDTO)session.createCriteria(EmpPaymentsDTO.class).add(Expression.eq("sfid", empClaimDetailsDTO.getAppliedBy())).uniqueResult();
				tadaDaNewDetailsDTO=(TadaDaNewDetailsDTO)session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq("gradePay", Integer.parseInt(empPaymentsDTO.getGradePay()))).add(Expression.eq("status", 1)).uniqueResult();
				
				daOnTourPercentage=Float.parseFloat(session.createSQLQuery("SELECT daontour.da_on_tour FROM tada_da_on_tour_master daontour,(SELECT DA_VALUE FROM PAY_DEARNESS_ALLOWANCE_MASTER"
						+ " WHERE STATUS=1 AND DA_DATE = (SELECT MAX(DA_DATE) FROM PAY_DEARNESS_ALLOWANCE_MASTER WHERE STATUS=1 )) payda"
						+ " WHERE status =1 AND payda.da_value between daontour.da_range_from and daontour.da_range_to").uniqueResult().toString());     //This is new value of new Da percentage.
				daOnTourPercentage=daOnTourPercentage/100;                         //These two lines are added for daO
				minDate=daNewAccList.get(0).getFromDate();
				maxDate=daNewAccList.get(daNewAccList.size()-1).getToDate();
				strMinDate=CPSUtils.formatDate(minDate);
				strMaxDate=CPSUtils.formatDate(maxDate);
				for(int p=0;p<=daNewAccList.size()-1;p++){
                tempToDate = daNewAccList.get(p).getToDate();
                  if(CPSUtils.compareTwoDatesUptoDate(tempToDate, maxDate)>0){
                	  maxDate = tempToDate;
                  }
                }
				
				strMinDate=CPSUtils.formatDate(minDate);
				strMaxDate=CPSUtils.formatDate(maxDate);
				int noOfDays=Integer.parseInt(CPSUtils.daysDifference(strMinDate, strMaxDate))+2;
				for(int i=0;i<noOfDays;i++){
					tadaTdAccDetDayRepDTO=new TadaTdAccDetDayRepDTO();
					tadaTdAccDetDayRepDTO.setRepresentationDate(strMinDate);
					tempTdAccList.add(tadaTdAccDetDayRepDTO);
					strMinDate=CPSUtils.nextDate(strMinDate);
				}
				TadaTdSettlementDTO tadaTdSettlementDTO=(TadaTdSettlementDTO)session.createCriteria(TadaTdSettlementDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRPB.getRequestId()))).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(tadaTdSettlementDTO)){
					tdAttachedLeaveList=session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", tadaTdSettlementDTO.getRefRequestId())).list();
				}
				for (TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO : tdAttachedLeaveList) {
					LeaveRequestBean leaveRequestBean=(LeaveRequestBean)session.createCriteria(LeaveRequestBean.class).add(Expression.eq("requestID", String.valueOf(tadaTdLeaveDetailsDTO.getLeaveRequestId()))).uniqueResult();
					leaveDetailsList.add(leaveRequestBean);
				}
				if(!leaveDetailsList.isEmpty()){
					for (LeaveRequestBean leaveRequestBean : leaveDetailsList) {
						leaveStartDate=leaveRequestBean.getStrFromDate();
						leaveDays=Integer.parseInt(CPSUtils.daysDifference(leaveRequestBean.getStrFromDate(), leaveRequestBean.getStrToDate()))+2;
						for(int i=0;i<leaveDays;i++){
							TadaTdAccDetDayRepDTO tdAccLeaveDTO=new TadaTdAccDetDayRepDTO();
							tdAccLeaveDTO.setRepresentationDate(leaveStartDate);
							if(CPSUtils.compareStrings(leaveRequestBean.getFromHalfDayFlag(), "Y")){
								if(i==0){
									tdAccLeaveDTO.setHalfDayFlag("Y");
								}
							}
							if(CPSUtils.compareStrings(leaveRequestBean.getToHalfDayFlag(), "Y")){
								if(i==leaveDays-1){
									tdAccLeaveDTO.setHalfDayFlag("Y");
								}
							}
							tdAccLeaveList.add(tdAccLeaveDTO);
							leaveStartDate=CPSUtils.nextDate(leaveStartDate);
						}
					}
				}		
				JSONObject accJson1=(JSONObject)mainJson.get("accDetails");
				/*for(int j=0;j<tempTdAccList.size();j++){
					for(int i=0;i<accDetailsJson.length();i++){
						JSONObject valueJson=(JSONObject)accDetailsJson.get(String.valueOf(i));
						if(CPSUtils.convertStringToDate(tempTdAccList.get(j).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accFromDate").toString()))>=0 && CPSUtils.convertStringToDate(tempTdAccList.get(j).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accToDate").toString()))<=0){
							float noOfDays1=Integer.parseInt(CPSUtils.daysDifference(valueJson.get("accFromDate").toString(), valueJson.get("accToDate").toString()))+2;
							tempTdAccList.get(j).setSettlementRequestId(Integer.parseInt(tadaRPB.getRequestId()));
							tempTdAccList.get(j).setRepresentationDate(tempTdAccList.get(j).getRepresentationDate());
							claimedAmount=valueJson.getInt("accAmountAftRes")/noOfDays1;
							tempTdAccList.get(j).setClaimedAmount(claimedAmount);
							eligibleAmount=Float.parseFloat(String.valueOf(((tadaDaNewDetailsDTO.getAccommodationBill())+(tadaDaNewDetailsDTO.getAccommodationBill()*0.25f))));
							tempTdAccList.get(j).setEligibleAmount(eligibleAmount);
							for(int k=0;k<tdAccLeaveList.size();k++){
								if(CPSUtils.convertStringToDate(tdAccLeaveList.get(k).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accFromDate").toString()))>=0 && 
										CPSUtils.convertStringToDate(tdAccLeaveList.get(k).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accToDate").toString()))<=0){
									if(CPSUtils.compareStrings(tdAccLeaveList.get(k).getHalfDayFlag(), "Y")){
										noOfDays1=noOfDays1-0.5f;
									}else{
										noOfDays1=noOfDays1-1;
									}
								}
							}
							admisAmount=valueJson.getInt("claimedAmountAftRes")/noOfDays1;
							tempTdAccList.get(j).setAdmisAmount(admisAmount);
						}
					}
				}*/
				// Newly added *****************START
				for(int j=0;j<tempTdAccList.size();j++){
					claimedAmount=0;
					admisAmount=0;
					for(int i=0;i<accJson1.length();i++){
						JSONObject valueJson=(JSONObject)accJson1.get(String.valueOf(i));
						if(CPSUtils.convertStringToDate(tempTdAccList.get(j).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accFromDate").toString()))==0 || CPSUtils.convertStringToDate(tempTdAccList.get(j).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accToDate").toString()))==0){
							float noOfDays1=Integer.parseInt(CPSUtils.daysDifference(valueJson.get("accFromDate").toString(), valueJson.get("accToDate").toString()))+2;
							claimedAmount += valueJson.getInt("accAmountAftRes")/noOfDays1;
							admisAmount +=valueJson.getInt("claimedAmountAftRes")/noOfDays1;
							for(int k=0;k<tdAccLeaveList.size();k++){
								if(CPSUtils.convertStringToDate(tdAccLeaveList.get(k).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accFromDate").toString()))>=0 && 
										CPSUtils.convertStringToDate(tdAccLeaveList.get(k).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accToDate").toString()))<=0){
									if(CPSUtils.compareStrings(tdAccLeaveList.get(k).getHalfDayFlag(), "Y")){
										noOfDays1=noOfDays1-0.5f;
									}else{
										noOfDays1=noOfDays1-1;
									}
								}
							}
						
						
						}else if(CPSUtils.convertStringToDate(tempTdAccList.get(j).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accFromDate").toString()))>0 && CPSUtils.convertStringToDate(tempTdAccList.get(j).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accToDate").toString()))<0){
							float noOfDays1=Integer.parseInt(CPSUtils.daysDifference(valueJson.get("accFromDate").toString(), valueJson.get("accToDate").toString()))+2;
							claimedAmount += valueJson.getInt("accAmountAftRes")/noOfDays1;
							admisAmount += valueJson.getInt("claimedAmountAftRes")/noOfDays1;
							

							for(int k=0;k<tdAccLeaveList.size();k++){
								if(CPSUtils.convertStringToDate(tdAccLeaveList.get(k).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accFromDate").toString()))>=0 && 
										CPSUtils.convertStringToDate(tdAccLeaveList.get(k).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(valueJson.get("accToDate").toString()))<=0){
									if(CPSUtils.compareStrings(tdAccLeaveList.get(k).getHalfDayFlag(), "Y")){
										noOfDays1=noOfDays1-0.5f;
									}else{
										noOfDays1=noOfDays1-1;
									}
								}
							}
						}
					}
				eligibleAmount=Float.parseFloat(String.valueOf(((tadaDaNewDetailsDTO.getAccommodationBill())+(tadaDaNewDetailsDTO.getAccommodationBill()*daOnTourPercentage))));         //Here i changed of da 0.25 to 0.50
				tempTdAccList.get(j).setEligibleAmount(eligibleAmount);
				tempTdAccList.get(j).setClaimedAmount(claimedAmount);
				if(admisAmount>eligibleAmount){
					tempTdAccList.get(j).setAdmisAmount(eligibleAmount);
				}else{
					tempTdAccList.get(j).setAdmisAmount(admisAmount);
				}
			//	tempTdAccList.get(j).setAdmisAmount(admisAmount);                    For this i added condition for provide restrict amount
				tempTdAccList.get(j).setSettlementRequestId(Integer.parseInt(tadaRPB.getRequestId()));
				tempTdAccList.get(j).setRepresentationDate(tempTdAccList.get(j).getRepresentationDate());
			
			}
				//***** Newly added *****************END
				
				for(int j=0;j<tempTdAccList.size();j++){
					for(int k=0;k<tdAccLeaveList.size();k++){
						if(CPSUtils.convertStringToDate(tdAccLeaveList.get(k).getRepresentationDate()).compareTo(CPSUtils.convertStringToDate(tempTdAccList.get(j).getRepresentationDate()))==0){
							if(CPSUtils.compareStrings(tdAccLeaveList.get(k).getHalfDayFlag(), "Y")){
								tempTdAccList.get(j).setAdmisAmount(tempTdAccList.get(j).getAdmisAmount()/2);
							}else{
								tempTdAccList.get(j).setAdmisAmount(0);
							}
						} 
					}
				}
				for(int i=0;i<tempTdAccList.size();i++){
					session.saveOrUpdate(tempTdAccList.get(i));
					session.flush();
					//session.evict(tempTdAccList.get(i));
					if(session.contains(tempTdAccList.get(i))){
						System.out.println(tempTdAccList.get(i));
						session.clear();
					}
					//session.saveOrUpdate(tempTdAccList.get(i));
										//session.flush();

				}
			}
			
			//daNewAccDayRepList = session.createCriteria(TadaTdAccDetDayRepDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(tadaRPB.getRequestId()))).list();
			TadaRequestBean tadaRequestBean = new TadaRequestBean();
			tadaRequestBean.setRequestId(tadaRPB.getRequestId());
			daNewAccDayRepList = sqlTadaRequestDAO.getDayAccDetails(tadaRequestBean);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return daNewAccDayRepList;
}
}


