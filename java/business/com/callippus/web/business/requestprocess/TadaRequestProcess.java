package com.callippus.web.business.requestprocess;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CDADetailsDTO;
import com.callippus.web.beans.dto.DynamicWorkflowTxnDTO;
import com.callippus.web.beans.dto.EmployeeClaimDetailsDTO;
import com.callippus.web.beans.dto.FinanceDetailsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.dto.MRODetailsDTO;
import com.callippus.web.beans.dto.MROPaymentDetailsDTO;
import com.callippus.web.beans.dto.StatusMasterDTO;
import com.callippus.web.beans.dto.TadaProjectDirectorsDTO;
import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.beans.requests.RequestBean;
import com.callippus.web.beans.requests.RequestCommonBean;
import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;
import com.callippus.web.business.domainobject.TadaDomainObject;
import com.callippus.web.business.tx.workflow.TxRequestProcess;
import com.callippus.web.business.tx.workflow.TxWorkflowProcess;
import com.callippus.web.cghs.beans.dto.SingingAuthorityDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.ltc.dto.LtcPenalMasterDTO;
import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dao.approval.ITadaTdApprovalRequestDAO;
import com.callippus.web.tada.dto.CityTypeDTO;
import com.callippus.web.tada.dto.LocalRMADTO;
import com.callippus.web.tada.dto.TaEntitleTypeDTO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaDetailsDTO;
import com.callippus.web.tada.dto.TadaRequestProcessBean;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;
import com.callippus.web.tada.dto.TadaTdDaNewAccDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaNewFoodDetailsDTO;
import com.callippus.web.tada.dto.TadaTdDaOldDetailsDTO;
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
import com.callippus.web.tada.dto.TravelTypeDTO;

@Service
public class TadaRequestProcess extends TxRequestProcess {
	private static Log log = LogFactory.getLog(TadaRequestProcess.class);
	@Autowired
	private TxRequestProcess txRequestProcess;
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;
	@Autowired
	private TadaDomainObject tadaDomainObject;

	@Autowired
	private ITadaTdApprovalRequestDAO tadaTdApprovalRequestDAO;
	@Autowired
	private TxWorkflowProcess txWorkflowProcess;
	
	public String initWorkflow(TadaRequestProcessBean tadaRPB) throws Exception {
		//log.debug("::<<<<<LtcRequestProcess<<<<<<Method>>>>>>>>>>>>>>>initWorkflowForLtcApprovalRequest(LtcRequestProcessBean ltcrb)>>>>>>>>>");
		String message = null;
		try {
			/**
			 * Generate Unique ID for this Request
			 */
			
			tadaRPB.setRequestID(txRequestProcess.generateUniqueID(CPSConstants.REQUEST));
			tadaRPB.setRequestId(tadaRPB.getRequestID());
			if(CPSUtils.compareStrings(CPSConstants.TADA_APPROVAL, tadaRPB.getType())) {
				if(Integer.parseInt(tadaRPB.getAuthorizedMove())==1){
					tadaRPB.setRequestTypeID("45");
					tadaRPB.setRequestType(CPSConstants.TADATDBUILDUP);
				} else if(Integer.parseInt(tadaRPB.getAuthorizedMove())==2){
					tadaRPB.setRequestTypeID("46");
					tadaRPB.setRequestType(CPSConstants.TADATDPROJECT);
				}
				if(CPSUtils.compareStrings(CPSConstants.TADA_APPROVAL, tadaRPB.getAmendmentType())){
					tadaRPB = setTadaMemberDetails(tadaRPB);
					tadaRPB.setTadaRequestType(CPSConstants.TADAAPPROVAL);
					message=submitTxnDetails(tadaRPB);
					if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
						message = updateTxnDetails(tadaRPB);
						if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
							submitTdReqJourneyDetails(tadaRPB);
							message=submitTdTxnDetails(tadaRPB);
						}
						if(!CPSUtils.compareStrings(tadaRPB.getLeaveId(), "")){
							submitTdLeaveDetails(tadaRPB);
						}
						if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
							if(CPSUtils.compareStrings(tadaRPB.getRequestTypeID(), "46")){
								DynamicWorkflowTxnDTO dynamicWorkflowTxnDTO = new DynamicWorkflowTxnDTO();
								dynamicWorkflowTxnDTO.setRequestID(Integer.parseInt(tadaRPB.getRequestId()));
								dynamicWorkflowTxnDTO.setRequestTypeID(46);
								if(CPSUtils.isNullOrEmpty(tadaRPB.getProjDirName()))
									dynamicWorkflowTxnDTO.setDynamicTo(tadaRPB.getSfID());
								else
									dynamicWorkflowTxnDTO.setDynamicTo(tadaRPB.getProjDirName());
								dynamicWorkflowTxnDTO.setStatus(1);
								tadaRPB.setDynamicWorkFlowTxnDTO(dynamicWorkflowTxnDTO);
								message=insertProjDirDetails(tadaRPB);
							}
						}
					}
				} else {
					tadaRPB = setTadaMemberDetails(tadaRPB);
					tadaRPB.setTadaRequestType(CPSConstants.TADAAPPROVAL);
					message=submitTxnDetails(tadaRPB);
					if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
						submitTdReqJourneyDetails(tadaRPB);
						message=submitTdTxnDetails(tadaRPB);
					}
					if(!CPSUtils.compareStrings(tadaRPB.getLeaveId(), "")){
						submitTdLeaveDetails(tadaRPB);
					}
					if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
						if(CPSUtils.compareStrings(tadaRPB.getRequestTypeID(), "46")){
							DynamicWorkflowTxnDTO dynamicWorkflowTxnDTO = new DynamicWorkflowTxnDTO();
							dynamicWorkflowTxnDTO.setRequestID(Integer.parseInt(tadaRPB.getRequestId()));
							dynamicWorkflowTxnDTO.setRequestTypeID(46);
							if(CPSUtils.isNullOrEmpty(tadaRPB.getProjDirName()))
								dynamicWorkflowTxnDTO.setDynamicTo(tadaRPB.getSfID());
							else
								dynamicWorkflowTxnDTO.setDynamicTo(tadaRPB.getProjDirName());
							dynamicWorkflowTxnDTO.setStatus(1);
							tadaRPB.setDynamicWorkFlowTxnDTO(dynamicWorkflowTxnDTO);
							message=insertProjDirDetails(tadaRPB);
						}
					}
				}
				
			} else if(CPSUtils.compareStrings(CPSConstants.TADA_TD_ADVANCE, tadaRPB.getType())){
				tadaRPB.setRequestType(CPSConstants.TADATDADVANCES);
				tadaRPB.setRequestTypeID("47");
				if(CPSUtils.compareStrings(CPSConstants.TADA_TD_ADVANCE, tadaRPB.getAmendmentType())){
					message=submitTdAdvTxnDetails(tadaRPB);
					
					if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
						message = updateTxnDetails(tadaRPB);
					}
				} else{
					message=submitTdAdvTxnDetails(tadaRPB);
				}
				if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
					message=submitTdTxnDetails(tadaRPB);
					submitTdReqJourneyDetails(tadaRPB);
				}
			} else if(CPSUtils.compareStrings(CPSConstants.TADA_TD_SETTLEMENT, tadaRPB.getType())){
				tadaRPB.setRequestType(CPSConstants.TADATDSETTLEMENTS);
				tadaRPB.setInnerRequestType(CPSConstants.SETTLEMENT);
				tadaRPB.setRequestTypeID("48");
				setTadaMemberDetails(tadaRPB);
				message=checkSettlementDetails(tadaRPB);
				if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
					if(CPSUtils.compareStrings(CPSConstants.TADA_TD_SETTLEMENT, tadaRPB.getAmendmentType())){
						updateTxnDetails(tadaRPB);
						submitTdSettlementDetails(tadaRPB);
					} else{
						submitTdSettDetails(tadaRPB);
						submitTdSettlementDetails(tadaRPB);
					}
					submitTdClaimDetials(tadaRPB);
					submitTdTxnDetails(tadaRPB);
					message=updateTdSettDetails(tadaRPB);
				}
			} else if(CPSUtils.compareStrings(CPSConstants.TADA_TD_REIMBURSEMENT, tadaRPB.getType())){
				tadaRPB.setRequestType(CPSConstants.TADATDREIMBURSEMENT);
				tadaRPB.setInnerRequestType(CPSConstants.REIMBURSEMENT);
				tadaRPB.setRequestTypeID("49");
				setTadaMemberDetails(tadaRPB);
				
				if(CPSUtils.compareStrings(CPSConstants.TADA_TD_REIMBURSEMENT, tadaRPB.getAmendmentType())){
					updateTxnDetails(tadaRPB);
					submitTdSettlementDetails(tadaRPB);
				} else {
					submitTdSettDetails(tadaRPB);
					submitTdSettlementDetails(tadaRPB);
				}
				submitTdClaimDetials(tadaRPB);	
				submitTdTxnDetails(tadaRPB);
				message=updateTdReimDetails(tadaRPB);
			}
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				/**
				 * decide the work flow & insert into history table
				 */
				RequestBean rb = new RequestBean();
				BeanUtils.copyProperties(tadaRPB, rb);
				txRequestProcess.initWorkflow(rb);
			}
		} catch(Exception e){
			message = CPSConstants.FAILED;
			throw e;
		}
		//hibernateUtils.closeSession();
		if(CPSUtils.compareStrings(message, CPSConstants.SUCCESS)){
			if(CPSUtils.compareStrings(CPSConstants.TADA_APPROVAL, tadaRPB.getType())){
				message = CPSConstants.TDREQUESTSUBMIT;
			} else if(CPSUtils.compareStrings(CPSConstants.TADA_TD_ADVANCE, tadaRPB.getType())){
				message = CPSConstants.TDADVANCESUBMIT;
			} else if(CPSUtils.compareStrings(CPSConstants.TADA_TD_SETTLEMENT, tadaRPB.getType())){
				message = CPSConstants.TDSETTLEMENTSUBMIT;
			} else {
				message = CPSConstants.TDREIMSUBMIT;
			}
			tadaRPB.setMessage(message);
		}else if(!CPSUtils.compareStrings(tadaRPB.getRemarks(), "")){
			tadaRPB.setMessage(tadaRPB.getRemarks());
		}
		return tadaRPB.getMessage();
	}
	public TadaRequestProcessBean setTadaMemberDetails(TadaRequestProcessBean tadaRPB) throws Exception {
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			TadaRequestBean empBean = (TadaRequestBean) tadaRPB.getSession().getAttribute(CPSConstants.EMPLOYEEDETAILS);
			String workingLocation=(String)session.createQuery("select workingLocation from OuterEmployeeBean where userSfid=?").setString(0, tadaRPB.getSfid().toUpperCase()).uniqueResult();
			tadaRPB.setDesignation(empBean.getDesignation());
			tadaRPB.setPhnNo(empBean.getPhnNo());
			tadaRPB.setGradePay(empBean.getGradePay());
			tadaRPB.setDesignationID(String.valueOf(empBean.getEmpDetailsList().getDesignationDetails().getId()));
			tadaRPB.setOrganizationID(workingLocation);
		} catch (Exception e) {
			throw e;
		}
		return tadaRPB;
	}
	@SuppressWarnings("unchecked")
	public String submitTxnDetails(TadaRequestProcessBean tadaRPB) throws Exception {
		Session session = null;
		TadaApprovalRequestDTO tadaApprDTO = null;
		@SuppressWarnings("unused")
		TadaTdTxnDTO tadaTdTxnDTO=null;
		List<TadaApprovalRequestDTO> list=null;
		List<TadaApprovalRequestDTO> list1=null;
		String movementOrderNo=null;
		String maxDate=null;
		String minDate=null;
		Criteria crit=null;
	
		try {
			log.debug("TadaRequestProcess >>>>>>>>>>>> submitTxnDetails(TadaRequestProcessBean tadaRPB) >>>>> Start");
			maxDate="31-Dec-"+CPSUtils.getCurrentYear()+"";
			minDate="01-Jan-"+CPSUtils.getCurrentYear()+"";
			tadaApprDTO = new TadaApprovalRequestDTO();
			tadaTdTxnDTO = new TadaTdTxnDTO();
			BeanUtils.copyProperties(tadaRPB, tadaApprDTO);
			tadaApprDTO.setCreationDate(CPSUtils.getCurrentDate());
			tadaApprDTO.setApplyDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
			tadaApprDTO.setStatus(2);
			tadaApprDTO.setAmmendementId(tadaRPB.getId());
			tadaApprDTO.setDesigId(tadaRPB.getEmpDetailsList().getDesignation());
			tadaApprDTO.setPhnNo(tadaRPB.getEmpDetailsList().getPersonalNumber());
			if(!CPSUtils.isNullOrEmpty(tadaRPB.getEntitlementExemption()))
				tadaApprDTO.setEntitleExemption(tadaRPB.getEntitlementExemption());
			else
				tadaApprDTO.setEntitleExemption("2");
			session = hibernateUtils.getSession();
			if(tadaApprDTO.getStayDuration()!=0){
				movementOrderNo =(String)session.createSQLQuery("select MOVEMENT_ORDER_NO as movementOrderNo from tada_td_request_details where request_id=(select max(request_id) as requestid from tada_td_request_details " +
				"where apply_date between ? and ? and movement_order_no is not null)").addScalar("movementOrderNo", Hibernate.STRING).setString(0, minDate).setString(1, maxDate).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(movementOrderNo)){
				if(CPSUtils.isNullOrEmpty(tadaRPB.getAmendmentType())){                                 //This Condition Has been added for request process on single movementOrderNo
						tadaApprDTO.setMovementOrderNo("ASL/TADA/"+CPSUtils.getCurrentYear()+"/"+(Integer.parseInt(movementOrderNo.split("/")[3])+1)+"");
					}else{
						tadaApprDTO.setMovementOrderNo(movementOrderNo);
					}
				}else{
					tadaApprDTO.setMovementOrderNo("ASL/TADA/"+CPSUtils.getCurrentYear()+"/1");
				}
			}
			crit=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("sfID", tadaRPB.getSfID()));
			list1=crit.list();
			TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=null;
			EmployeeClaimDetailsDTO empClaimDetailsDTO = null;
			if(!CPSUtils.isNullOrEmpty(tadaRPB.getAmendmentReqId())){
				tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("referenceRequestID", tadaRPB.getAmendmentReqId())).add(Expression.ne("status", 9)).uniqueResult();
				TadaApprovalRequestDTO tadaApprovalRequestDTO = (TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRPB.getAmendmentReqId())).uniqueResult();
			    empClaimDetailsDTO = (EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(tadaRPB.getAmendmentReqId()))).add(Expression.eq("requestTypeID", 47)).add(Expression.and(Expression.ne("workFlowStatus", 9), Expression.ne("workFlowStatus", 6))).uniqueResult();
	    		if(!CPSUtils.isNullOrEmpty(tadaTdAdvanceRequestDTO) && !CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO) && !CPSUtils.isNullOrEmpty(empClaimDetailsDTO)){
	    			tadaApprDTO.setAdvanceFlag(tadaApprovalRequestDTO.getAdvanceFlag());
	    			tadaTdAdvanceRequestDTO.setReferenceRequestID(tadaRPB.getRequestId());
//	    			session.clear();
//	    			session.saveOrUpdate(tadaTdAdvanceRequestDTO);
//	    			session.flush();
	    			empClaimDetailsDTO.setRefRequestId(Integer.parseInt(tadaRPB.getRequestId()));
	    			
	    			}
			}
			if(list1.size()==0){
				
				session.save(tadaApprDTO);
				session.flush();
				if(!CPSUtils.isNullOrEmpty(tadaTdAdvanceRequestDTO)){
					
					session.saveOrUpdate(tadaTdAdvanceRequestDTO);
					session.saveOrUpdate(empClaimDetailsDTO);
	    			session.flush();
				}
				
				tadaRPB.setMessage(CPSConstants.SUCCESS);
				
			} else {
				crit=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.or(Expression.eq("status", 2), Expression.eq("status", 8))).add(Expression.eq("sfID", tadaRPB.getSfID()));
				list = crit.list();
				if(list.size()==0){
					
					session.save(tadaApprDTO);
					session.flush();
					if(!CPSUtils.isNullOrEmpty(tadaTdAdvanceRequestDTO)){
						
						session.saveOrUpdate(tadaTdAdvanceRequestDTO);
						session.saveOrUpdate(empClaimDetailsDTO);
		    			session.flush();
					}
					tadaRPB.setMessage(CPSConstants.SUCCESS);
				}
				else {
					if(!CPSUtils.compareStrings(CPSConstants.TADA_APPROVAL, tadaRPB.getAmendmentType())){
						for (TadaApprovalRequestDTO tadaApprovalRequestDTO : list) {
							TadaApprovalRequestDTO tadApprovalRequestDTO2=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.or(Expression.between("departureDate", tadaRPB.getDepartureDate(), tadaRPB.getArrivalDate()), Expression.and(Expression.le("departureDate", tadaRPB.getDepartureDate()), Expression.gt("arrivalDate", tadaRPB.getDepartureDate())))).add(Expression.eq("requestId", tadaApprovalRequestDTO.getRequestId())).uniqueResult();
							if(!CPSUtils.isNullOrEmpty(tadApprovalRequestDTO2)){
								if((tadApprovalRequestDTO2.getStayDuration()!=0) || (tadApprovalRequestDTO2.getStayDuration()==0 && tadApprovalRequestDTO2.getStatus()==2)){
									tadaRPB.setRemarks("TD Request is already applied in between "+CPSUtils.formatDate(tadaApprovalRequestDTO.getDepartureDate())+" and "+CPSUtils.formatDate(tadaApprovalRequestDTO.getArrivalDate())+"");
								}	
							}
						}
						if(CPSUtils.compareStrings(tadaRPB.getRemarks(), "") || CPSUtils.isNullOrEmpty(tadaRPB.getRemarks())){
							
							session.save(tadaApprDTO);
							session.flush();
							if(!CPSUtils.isNullOrEmpty(tadaTdAdvanceRequestDTO)){
								
								session.saveOrUpdate(tadaTdAdvanceRequestDTO);
								session.saveOrUpdate(empClaimDetailsDTO);
				    			session.flush();
							}
							tadaRPB.setMessage(CPSConstants.SUCCESS);
						}
					}else{
						
						session.save(tadaApprDTO);
						session.flush();
						if(!CPSUtils.isNullOrEmpty(tadaTdAdvanceRequestDTO)){
							
							session.saveOrUpdate(tadaTdAdvanceRequestDTO);
							session.saveOrUpdate(empClaimDetailsDTO);
			    			session.flush();
						}
						tadaRPB.setMessage(CPSConstants.SUCCESS);
					}
					
				}
				
				//session.save(tadaApprDTO);
				//tadaRPB.setMessage(CPSConstants.SUCCESS);
			}
			
			
		} catch (Exception e) {
			tadaRPB.setMessage(CPSConstants.FAILED);
			throw e;
		}finally{
			session.clear();
		}
		
		return tadaRPB.getMessage();
	}
	public String submitTdTxnDetails(TadaRequestProcessBean tadaRPB) throws Exception {
		Session session = null;
		TadaTdTxnDTO tadaTdTxnDTO=null;
		try {
			log.debug("TadaRequestProcess >>>>>>>>>>>> submitTxnDetails(TadaRequestProcessBean tadaRPB) >>>>> Start");
			tadaTdTxnDTO = new TadaTdTxnDTO();
			tadaTdTxnDTO.setRequestId(tadaRPB.getRequestId());
			tadaTdTxnDTO.setRequestType(tadaRPB.getRequestType());
			tadaTdTxnDTO.setSfID(tadaRPB.getSfID());
			tadaTdTxnDTO.setStatus(2);
			tadaTdTxnDTO.setJourneyDate(tadaRPB.getDepartureDate());
			session = hibernateUtils.getSession();
			session.save(tadaTdTxnDTO);
			session.flush();
			tadaRPB.setMessage(CPSConstants.SUCCESS);
			tadaRPB.setRemarks(CPSConstants.TDREQUESTSUBMIT);
			
		} catch (Exception e) {
			tadaRPB.setMessage(CPSConstants.FAILED);
			throw e;
		} 
		return tadaRPB.getMessage();
	}
	
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getTadaTdRequestDetails(WorkFlowMappingBean workflowMap) throws Exception {
	Session session = null;
	String sql =null;
	String qry = null;
	String qry1 = null;
	TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
	TadaApprovalRequestDTO tadaApprovalRequestDTO1=null;
	TadaTdAdvanceRequestDTO tadaAdvanceRequestDTO=null;
	TadaTdAdvanceRequestDTO tadaAdvanceRequestDTO1=null;
	EmployeeClaimDetailsDTO empClaimDetailsDTO=null;
	EmployeeClaimDetailsDTO empClaimDetailsDTO1=null;
	WorkFlowMappingBean workflowMap1=null;
	List<TadaTdLeaveDetailsDTO> tdLeaveList=null;
	List<LeaveRequestBean> leaveDetails=new ArrayList<LeaveRequestBean>();
	
	try {

		session = hibernateUtils.getSession();//session = sessionFactory.openSession();

		

		String requestId=workflowMap.getRequestId();
		
		//old code
		/*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.TATKAL_QUOTA as tatkalQuota, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne  from TADA_TD_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and tdtt.id=(select TDRD2.CONVEYANCE_MODE from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and tdem.id=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
        workflowMap.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("tatkalQuota", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());
		*/
		
		//new code
		//original commented by bkr 25/04/2016
		//sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,tdrd.ticket_cancel_charges ticketCancelCharges, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.project_name as projectName, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,tdrd.project_name as projectName,tdrd.remarks as remarks,tdrd.advance_flag as advanceFlag,tdrd.status as status from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
		//added by bkr 25/04/2016
		sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,tdrd.ticket_cancel_charges ticketCancelCharges, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace,  tdrd.PURPOSE as purpose,  tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.project_name as projectName, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,tdrd.project_name as projectName,tdrd.remarks as remarks,tdrd.advance_flag as advanceFlag,tdrd.status as status from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
		tadaApprovalRequestDTO=(TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("ticketCancelCharges",Hibernate.FLOAT).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("purpose", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("remarks", Hibernate.STRING).addScalar("advanceFlag", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult();
		tadaApprovalRequestDTO1=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("ammendementId", tadaApprovalRequestDTO.getRequestId())).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO1)){
			tadaApprovalRequestDTO.setAmmendementId(tadaApprovalRequestDTO1.getRequestId());
			workflowMap.setTadaAmendmentDetails(tadaApprovalRequestDTO1);
		}
		workflowMap.setTadaApprovalRequestDTO(tadaApprovalRequestDTO);
		workflowMap.setTadaTdReqJourneyList(session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", Integer.parseInt(requestId))).addOrder(Order.asc("id")).list());
		tadaAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("referenceRequestID", requestId)).add(Expression.or(Expression.eq("status", 2), Expression.eq("status", 8))).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(tadaAdvanceRequestDTO)){
			qry="select max(rwh.id) as historyID,rwh.status as status,tttd.request_type as requestType from request_workflow_history rwh," +
					"tada_td_adv_request_details ttard,tada_td_txn_details tttd " +
					"where rwh.request_id=? and ttard.request_id=? and tttd.request_id=? and ttard.status not in (9,6) and rwh.id=(select max(id) from request_workflow_history where " +
					"request_id=?) group by rwh.status, tttd.request_type";
			tadaAdvanceRequestDTO1=(TadaTdAdvanceRequestDTO)session.createSQLQuery(qry).addScalar("historyID",Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).setString(0, tadaAdvanceRequestDTO.getRequestId())
			.setString(1, tadaAdvanceRequestDTO.getRequestId()).setString(2, tadaAdvanceRequestDTO.getRequestId()).setString(3, tadaAdvanceRequestDTO.getRequestId()).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
			tadaAdvanceRequestDTO1.setRequestId(tadaAdvanceRequestDTO.getRequestId());
			StatusMasterDTO statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", tadaAdvanceRequestDTO1.getStatus())).uniqueResult();
			tadaAdvanceRequestDTO1.setStrStatus(statusMasterDTO.getName());
			workflowMap.setTadaTdAdvanceRequestDTO(tadaAdvanceRequestDTO1);
		}
		empClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(requestId))).add(Expression.eq("requestTypeID", 48)).add(Expression.and(Expression.ne("workFlowStatus", 9), Expression.ne("workFlowStatus", 6))).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(empClaimDetailsDTO)){
			qry1="select max(rwh.id) as tdSettlementHistoryID,rwh.request_id as tdSettlementReqId,ecd.workflow_status as tdSettStatus " +
					"from request_workflow_history rwh,emp_claim_details ecd where rwh.request_id=ecd.request_id and ecd.ref_request_id=? and ecd.request_type_id=48 and ecd.workflow_status not in (9,6) " +
					"group by rwh.request_id, ecd.workflow_status ";
			workflowMap1 = (WorkFlowMappingBean)session.createSQLQuery(qry1).addScalar("tdSettlementHistoryID", Hibernate.STRING).addScalar("tdSettlementReqId", Hibernate.STRING)
			.addScalar("tdSettStatus", Hibernate.INTEGER).setInteger(0, Integer.parseInt(requestId)).setResultTransformer(Transformers.aliasToBean(WorkFlowMappingBean.class)).uniqueResult();
			StatusMasterDTO statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", workflowMap1.getTdSettStatus())).uniqueResult();
			workflowMap.setTdSettlementHistoryID(workflowMap1.getTdSettlementHistoryID());
			workflowMap.setTdSettlementReqId(workflowMap1.getTdSettlementReqId());
			workflowMap.setTdSettStrStatus(statusMasterDTO.getName());
		}
		empClaimDetailsDTO1=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(requestId))).add(Expression.eq("requestTypeID", 49)).add(Expression.and(Expression.ne("workFlowStatus", 9), Expression.ne("workFlowStatus", 6))).uniqueResult();
		if(!CPSUtils.isNullOrEmpty(empClaimDetailsDTO1)){
			qry1="select max(rwh.id) as tdReimHistoryID,rwh.request_id as tdReimReqId,ecd.workflow_status as tdReimStatus " +
					"from request_workflow_history rwh,emp_claim_details ecd where rwh.request_id=ecd.request_id and ecd.ref_request_id=? and ecd.request_type_id=49 and ecd.workflow_status not in (9,6) " +
					"group by rwh.request_id, ecd.workflow_status ";
			workflowMap1 = (WorkFlowMappingBean)session.createSQLQuery(qry1).addScalar("tdReimHistoryID", Hibernate.STRING).addScalar("tdReimReqId", Hibernate.STRING)
			.addScalar("tdReimStatus", Hibernate.INTEGER).setInteger(0, Integer.parseInt(requestId)).setResultTransformer(Transformers.aliasToBean(WorkFlowMappingBean.class)).uniqueResult();
			StatusMasterDTO statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", workflowMap1.getTdReimStatus())).uniqueResult();
			workflowMap.setTdReimHistoryID(workflowMap1.getTdReimHistoryID());
			workflowMap.setTdReimReqId(workflowMap1.getTdReimReqId());
			workflowMap.setTdReimStrStatus(statusMasterDTO.getName());
		}
		tdLeaveList = session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(requestId))).list();
		if(tdLeaveList.size()!=0){
			for (TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO : tdLeaveList) {
				LeaveRequestBean leaveRequestBean=new LeaveRequestBean();
				leaveRequestBean=(LeaveRequestBean)session.createCriteria(LeaveRequestBean.class).add(Expression.eq("requestID", String.valueOf(tadaTdLeaveDetailsDTO.getLeaveRequestId()))).add(Expression.ne("status", 9)).uniqueResult();
				leaveDetails.add(leaveRequestBean);
			}
			workflowMap.setTdLeaveDetailsList(leaveDetails);
		}
	} catch (Exception e) {
		throw e;
	} finally {

		//session.close();

		session.flush();

	}
	return workflowMap;
	}
	public WorkFlowMappingBean getStageStatus(WorkFlowMappingBean workflowMap)throws Exception{
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			if(!CPSUtils.compareStrings(workflowMap.getRequesterSfid(), workflowMap.getSfid())){
				sql="select max(status) as status from REQUEST_WORKFLOW_HISTORY where request_id=? and assigned_from=? and assigned_to=?";
				workflowMap.setRequestCommonBean((RequestCommonBean)session.createSQLQuery(sql).addScalar("status", Hibernate.INTEGER).setString(0, workflowMap.getRequestId()).setString(1, workflowMap.getRequesterSfid()).setString(2, workflowMap.getSfid()).setResultTransformer(Transformers.aliasToBean(RequestCommonBean.class)).uniqueResult());
			}
			
			
		} catch(Exception e){
			throw e;
		}
		return workflowMap;
	}
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getPrjDirList(WorkFlowMappingBean workflowMap)throws Exception{
		Session session=null;
		List<TadaProjectDirectorsDTO> list=null;
		try{
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(workflowMap.getSfid().toUpperCase(), "SF0002") || CPSUtils.compareStrings(workflowMap.getSfid().toUpperCase(), "SF0008") || CPSUtils.compareStrings(workflowMap.getSfid().toUpperCase(), "SF0251")){
				DynamicWorkflowTxnDTO dynamicWorkflowTxnDTO=(DynamicWorkflowTxnDTO)session.createCriteria(DynamicWorkflowTxnDTO.class).add(Expression.eq("requestID", Integer.parseInt(workflowMap.getRequestId()))).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(dynamicWorkflowTxnDTO)){
					list=session.createCriteria(TadaProjectDirectorsDTO.class).add(Expression.eq("status", 1)).list();
				}
			}else{
				list=session.createCriteria(TadaProjectDirectorsDTO.class).add(Expression.eq("status", 1)).add(Expression.eq("sfID", workflowMap.getSfid())).list();
			}
			workflowMap.setTadaPrjDirList(list);
		} catch(Exception e){
			throw e;
		}
		return workflowMap;
	}
	@SuppressWarnings("unchecked")
	public TadaRequestBean getTadaTdRequestDetails(TadaRequestBean tadaRequestBean) throws Exception {
		@SuppressWarnings("unused")
		List<TadaApprovalRequestDTO> list = null;
		Session session = null;
		String sql =null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

		

			
			
				if(CPSUtils.compareStrings(tadaRequestBean.getType(), "tadaTdSettlement")){
					String requestId=tadaRequestBean.getRequestId();
					//old code
				    /*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.TATKAL_QUOTA as tatkalQuota, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,ttard.advance_amount as tadaAdvanceAmount,cd.cda_amount as cdaAmount from TADA_TD_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd,TADA_TD_ADV_REQUEST_DETAILS ttard,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd where tdrd.REQUEST_ID="+requestId+" and tdtt.id=(select TDRD2.CONVEYANCE_MODE from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and tdem.id=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and ttard.ref_request_id="+requestId+" and ecd.ref_request_id="+requestId+" and ecd.id=cd.reference_id and ttard.status=8 and ecd.workflow_status=8";
		            tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("tatkalQuota", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("cdaAmount", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());
					list=session.createSQLQuery("select empPay.basic_pay,empPay.grade_pay from emp_payment_details empPay,tada_td_request_details tdrd where empPay.sfid=(select tdrd1.sfid from tada_td_request_details tdrd1 where tdrd1.request_id="+tadaRequestBean.getRequestId()+")").list();*/
					
					//new code
		sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, Tdrd.Ticket_Cancel_Charges as ticketCancelCharges, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.project_name as projectName, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,ttard.advance_amount as tadaAdvanceAmount,cd.cda_amount as cdaAmount,tdrd.status as status from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,TADA_TD_ADV_REQUEST_DETAILS ttard,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd,finance_details fd  where tdrd.REQUEST_ID="+requestId+" and  epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and ttard.ref_request_id="+requestId+" and ecd.ref_request_id="+requestId+" and fd.id= cd.reference_id"
				+ " And ecd.id = fd.REFERENCE_ID  and ttard.status=8 and ecd.workflow_status=8";
		            tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ticketCancelCharges", Hibernate.FLOAT).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("cdaAmount", Hibernate.FLOAT).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());
		            session.flush();
		            if(tadaRequestBean.getTadaApprovalRequestDTO().getAmmendementId()!=null){
		            	TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRequestBean.getTadaApprovalRequestDTO().getAmmendementId())).uniqueResult();
		            	if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
		            		if(tadaApprovalRequestDTO.getStayDuration()==0){
		            			tadaRequestBean.getTadaApprovalRequestDTO().setStayDuration(0);
		            		}
		            	}
		            }
					list=session.createSQLQuery("select empPay.basic_pay,empPay.grade_pay from emp_payment_details empPay,tada_td_request_details tdrd where empPay.sfid=(select tdrd1.sfid from tada_td_request_details tdrd1 where tdrd1.request_id="+tadaRequestBean.getRequestId()+")").list();
				}
				if(CPSUtils.compareStrings(tadaRequestBean.getRequestType(), CPSConstants.SETTLEMENT)){
					if(tadaRequestBean.getTadaApprovalRequestDTO()==null){
						String requestId=tadaRequestBean.getRequestId();
						//old code
						/*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.TATKAL_QUOTA as tatkalQuota, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,cd.cda_amount as tadaAdvanceAmount from TADA_TD_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd where tdrd.REQUEST_ID=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+") and tdtt.id=(select TDRD2.CONVEYANCE_MODE from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+")) and tdem.id=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+")) and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+")) and ecd.request_id="+requestId+" and ecd.workflow_status=8 and cd.reference_id=(select id from EMP_CLAIM_DETAILS where ref_request_id=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+") and request_type_id=47)";
					    tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("tatkalQuota", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());*/
					    //new code
						sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid,Tdrd.Ticket_Cancel_Charges as ticketCancelCharges,tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,cd.cda_amount as tadaAdvanceAmount,tdrd.status as status from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd where tdrd.REQUEST_ID=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+")) and ecd.request_id="+requestId+" and ecd.workflow_status=8 and cd.reference_id=(select fd.id from EMP_CLAIM_DETAILS ecd,finance_details fd  where ecd.ref_request_id=(select ref_request_id from EMP_CLAIM_DETAILS where request_id="+requestId+") and ecd.request_type_id=47 And fd.reference_id = ecd.id)";
					    tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("ticketCancelCharges", Hibernate.FLOAT).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());
					}
				} else{
					if(tadaRequestBean.getTadaApprovalRequestDTO()==null){
						String requestId=tadaRequestBean.getRequestId();
						//old code
						/*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.TATKAL_QUOTA as tatkalQuota, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne from TADA_TD_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and tdtt.id=(select TDRD2.CONVEYANCE_MODE from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and tdem.id=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
						tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("tatkalQuota", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());*/
						//new code
						//original commented by bkr 25/04/2016
						//sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,Tdrd.Ticket_Cancel_Charges as ticketCancelCharges,tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,tdrd.status as status,tdrd.project_name as projectName from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
					
						sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,Tdrd.Ticket_Cancel_Charges as ticketCancelCharges,tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.PURPOSE as purpose,  tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,tdrd.status as status,tdrd.project_name as projectName from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
						tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("ticketCancelCharges", Hibernate.FLOAT).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("purpose", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("projectName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());
					}
				}
			    if(CPSUtils.compareStrings(tadaRequestBean.getType(), CPSConstants.TADA_TD_REIMBURSEMENT)){
			    	String requestId=tadaRequestBean.getRequestId();
			    	//old code
					/*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.TATKAL_QUOTA as tatkalQuota, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne from TADA_TD_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and tdtt.id=(select TDRD2.CONVEYANCE_MODE from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and tdem.id=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
					tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("tatkalQuota", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());*/
			        //new code
			    	sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, Tdrd.Ticket_Cancel_Charges as ticketCancelCharges,tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,tdrd.status as status,tdrd.project_name as projectName from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
					tadaRequestBean.setTadaApprovalRequestDTO((TadaApprovalRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("ticketCancelCharges",Hibernate.INTEGER).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("projectName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).uniqueResult());
			    }
		} catch (Exception e) {
			throw e;
		}
		finally{

			//session.close();

			session.flush();

		}
		return tadaRequestBean;
	}
	@SuppressWarnings("unchecked")
	public WorkFlowMappingBean getTadatdAdvReqDetails(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		String sql =null;
		String sql1=null;
		TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=null;
		String refReqId=null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			

			String requestId=workflowMap.getRequestId();
			//old code
			/*sql1="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,cd.cda_amount as cdaAmount,ecd.workflow_status as status,tdrd.ref_request_id as referenceRequestID from TADA_TD_ADV_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd where tdrd.REQUEST_ID="+requestId+" and TDTT.TRAVEL_TYPE=(select TDRD2.CONVEYANCE_MODE from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and TDEM.ENTITLE_CLASS=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and tdtt.status=1 and tdem.status=1 and cd.reference_id=(select id from EMP_CLAIM_DETAILS where request_id="+requestId+") and cd.reference_id=ecd.id";
			tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createSQLQuery(sql1).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("cdaAmount", Hibernate.INTEGER).addScalar("status", Hibernate.INTEGER).addScalar("referenceRequestID", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
			*/
			
			//new code
			//original code added by bkr 25/04/2016
		//	sql1="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,tdrd.REF_REQUEST_ID as referenceRequestID,tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ADVANCE_AMOUNT_AFT_RES as advanceAmountAftRes,cd.cda_amount as cdaAmount,ecd.workflow_status as status,tdrd.ref_request_id as referenceRequestID,tdrd.no_of_days as noOfDaysAcc,tdrd.acc_amount_per_day as accAmtPerDay,tdrd.food_amount_per_day as foodAmtPerDay,cd.dv_no as dvNumber,cd.dv_date as dvDate,tdrd.status as status from TADA_TD_ADV_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd,finance_details fd  where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and  cd.reference_id=fd.id And ecd.request_id ="+requestId+" and  ecd.id = fd.REFERENCE_ID";
			
			sql1="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,tdrd.REF_REQUEST_ID as referenceRequestID,tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.ONTRANSIT as onTransit, tdrd.TAXI as taxi, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ADVANCE_AMOUNT_AFT_RES as advanceAmountAftRes,cd.cda_amount as cdaAmount,ecd.workflow_status as status,tdrd.ref_request_id as referenceRequestID,tdrd.no_of_days as noOfDaysAcc,tdrd.acc_amount_per_day as accAmtPerDay,tdrd.food_amount_per_day as foodAmtPerDay,cd.dv_no as dvNumber,cd.dv_date as dvDate,tdrd.status as status from TADA_TD_ADV_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd,finance_details fd  where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and  cd.reference_id=fd.id And ecd.request_id ="+requestId+" and  ecd.id = fd.REFERENCE_ID";
			
			tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createSQLQuery(sql1).addScalar("basicPay", Hibernate.STRING).addScalar("referenceRequestID",Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("advanceAmountAftRes", Hibernate.FLOAT).addScalar("cdaAmount", Hibernate.INTEGER).addScalar("status", Hibernate.INTEGER).addScalar("referenceRequestID", Hibernate.STRING).addScalar("noOfDaysAcc", Hibernate.INTEGER).addScalar("accAmtPerDay", Hibernate.FLOAT).addScalar("foodAmtPerDay", Hibernate.FLOAT).addScalar("dvNumber", Hibernate.STRING).addScalar("dvDate", Hibernate.DATE).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
			if(tadaTdAdvanceRequestDTO==null){
				//old code
				/*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ref_request_id as referenceRequestID from TADA_TD_ADV_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and TDTT.TRAVEL_TYPE=(select TDRD2.CONVEYANCE_MODE from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and TDEM.ENTITLE_CLASS=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and tdtt.status=1 and tdem.status=1";
				tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("referenceRequestID", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
				*/
				
				//new code
				//original sql commented by bkr 25/04/2016
				sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,tdrd.REF_REQUEST_ID as referenceRequestID, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ADVANCE_AMOUNT_AFT_RES as advanceAmountAftRes,tdrd.ref_request_id as referenceRequestID,tdrd.no_of_days as noOfDaysAcc,tdrd.acc_amount_per_day as accAmtPerDay,tdrd.food_amount_per_day as foodAmtPerDay,tdrd.status as status from TADA_TD_ADV_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
				//added by bkr 25/04/2016
				sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId,tdrd.REF_REQUEST_ID as referenceRequestID, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.TAXI as taxi, tdrd.ONTRANSIT as onTransit, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ADVANCE_AMOUNT_AFT_RES as advanceAmountAftRes,tdrd.ref_request_id as referenceRequestID,tdrd.no_of_days as noOfDaysAcc,tdrd.acc_amount_per_day as accAmtPerDay,tdrd.food_amount_per_day as foodAmtPerDay,tdrd.status as status from TADA_TD_ADV_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
			
				//original sql commented by bkr 25/04/2016
				//tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("referenceRequestID",Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("advanceAmountAftRes", Hibernate.FLOAT).addScalar("referenceRequestID", Hibernate.STRING).addScalar("noOfDaysAcc", Hibernate.INTEGER).addScalar("accAmtPerDay", Hibernate.FLOAT).addScalar("foodAmtPerDay", Hibernate.FLOAT).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
		
				//added by bkr 25/04/2016
				tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("referenceRequestID",Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("taxi", Hibernate.FLOAT).addScalar("onTransit", Hibernate.FLOAT).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("advanceAmountAftRes", Hibernate.FLOAT).addScalar("referenceRequestID", Hibernate.STRING).addScalar("noOfDaysAcc", Hibernate.INTEGER).addScalar("accAmtPerDay", Hibernate.FLOAT).addScalar("foodAmtPerDay", Hibernate.FLOAT).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult();
			
			}
			refReqId=tadaTdAdvanceRequestDTO.getReferenceRequestID();
			workflowMap.setTadaTdAdvanceRequestDTO(tadaTdAdvanceRequestDTO);
			for(int i=0;i<=i;i++){
				TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("ammendementId", refReqId)).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
					if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO.getAdvanceFlag())){
						refReqId=tadaApprovalRequestDTO.getRequestId();
					}else{
						break;
					}
				}else{
					break;
				}
			}
			workflowMap.setTadaTdReqJourneyList(session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", Integer.parseInt(refReqId))).list());
			workflowMap.setTadaApprovalRequestDTO((TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", refReqId)).uniqueResult());
			String orgRoleName=(String)session.createQuery("select name from OrgInstanceDTO where id in (select roleInstanceId from EmpRoleMappingDTO where sfid=?) and name in (?,?) and status=1").setString(0, workflowMap.getSfid()).setString(1, "TADA TASK HOLDER").setString(2, "TA DA /Medical Section Head").uniqueResult();
			workflowMap.setRoleInstanceName(orgRoleName);
		} catch (Exception e) {
			throw e;
		}
		finally{


			//session.close();






			session.flush();

		}
		return workflowMap;
	}
	public WorkFlowMappingBean getTadatdAdvReqDetailsList(WorkFlowMappingBean workflowMap) throws Exception {
		Session session = null;
		String sql =null;
		String sql1 =null;
		try {

			session = hibernateUtils.getSession();//session = sessionFactory.openSession();

			

			if(CPSUtils.compareStrings(workflowMap.getRequestType(), CPSConstants.TADATDSETTLEMENTS)){
				String requestId=workflowMap.getRequestId();
				//old code
				/*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ref_request_id as referenceRequestID,ecd.workflow_status as status from TADA_TD_ADV_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd where tdrd.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6)) and TDTT.TRAVEL_TYPE=(select TDRD2.CONVEYANCE_MODE from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6))) and TDEM.ENTITLE_CLASS=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6))) and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in (9,6))) and cd.reference_id=(select id from emp_claim_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and request_type_id=47 and workflow_status=8) and ecd.workflow_status=8 and ecd.id=cd.reference_id and tdtt.status=1 and tdem.status=1";
		        workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("referenceRequestID", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());*/
				//new code
				//original query commented by bkr 26/04/2016 
				sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid,trd.ticket_cancel_charges as ticketCancelCharges, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ref_request_id as referenceRequestID,ecd.workflow_status as status,cd.dv_no as dvNumber from TADA_TD_ADV_REQUEST_DETAILS tdrd,TADA_TD_REQUEST_DETAILS trd,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd,finance_details fd  where tdrd.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6)) and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in (9,6))) and cd.reference_id=(select fd.id from emp_claim_details ecd,finance_details fd  where ecd.ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+")    And ecd.id = fd.REFERENCE_ID   and ecd.request_type_id=47 and ecd.workflow_status=8) and ecd.workflow_status=8 and fd.id=cd.reference_id And ecd.id = fd.REFERENCE_ID  and tdrd.ref_request_id = trd.request_id";
		    //added by bkr 26/04/2016 
				sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid,trd.ticket_cancel_charges as ticketCancelCharges,trd.purpose as purpose, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,tdrd.ref_request_id as referenceRequestID,ecd.workflow_status as status,cd.dv_no as dvNumber from TADA_TD_ADV_REQUEST_DETAILS tdrd,TADA_TD_REQUEST_DETAILS trd,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd,finance_details fd  where tdrd.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6)) and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in (9,6))) and cd.reference_id=(select fd.id from emp_claim_details ecd,finance_details fd  where ecd.ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+")    And ecd.id = fd.REFERENCE_ID   and ecd.request_type_id=47 and ecd.workflow_status=8) and ecd.workflow_status=8 and fd.id=cd.reference_id And ecd.id = fd.REFERENCE_ID  and tdrd.ref_request_id = trd.request_id";
				//original query commented by bkr 26/04/2016 
				//workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("ticketCancelCharges", Hibernate.FLOAT).addScalar("referenceRequestID", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("dvNumber", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());
				
				workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("ticketCancelCharges", Hibernate.FLOAT).addScalar("purpose", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("dvNumber", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());
			    
				
				if(CPSUtils.isNullOrEmpty(workflowMap.getTadaTdAdvanceRequestDTO())){
			    	//old code
			    	/*sql1="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,cd.cda_amount as cdaAmount,tdrd.ref_request_id as referenceRequestID,ecd.workflow_status as status from TADA_TD_ADV_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd where tdrd.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6)) and TDTT.TRAVEL_TYPE=(select TDRD2.CONVEYANCE_MODE from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6))) and TDEM.ENTITLE_CLASS=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_ADV_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6))) and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in (9,6))) and cd.reference_id=(select id from emp_claim_details where request_id="+requestId+" and request_type_id=48 and workflow_status in(8,60)) and ecd.workflow_status in(8,60) and ecd.id=cd.reference_id and tdtt.status=1 and tdem.status=1";
			    	workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql1).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("cdaAmount", Hibernate.INTEGER).addScalar("referenceRequestID", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());*/
			    	//new code
			    	sql1="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as tadaAdvanceAmount,cd.cda_amount as cdaAmount,tdrd.ref_request_id as referenceRequestID,ecd.workflow_status as status,cd.dv_no as dvNumber from TADA_TD_ADV_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd,finance_details fd  where tdrd.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6)) and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select request_id from tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in (9,6))) and cd.reference_id=(select  fd.id from emp_claim_details ecd1,finance_details fd  where ecd1.request_id="+requestId+" and ecd1.request_type_id=48 and ecd1.workflow_status in(8,60) and fd.reference_id=ecd1.id)  And ecd.id = fd.REFERENCE_ID   and ecd.workflow_status in(8,60) and fd.id=cd.reference_id  And  ecd.id = fd.REFERENCE_ID";
			    	
			    	workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql1).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("cdaAmount", Hibernate.INTEGER).addScalar("referenceRequestID", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("dvNumber", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());
			    }
			    if(CPSUtils.isNullOrEmpty(workflowMap.getTadaTdAdvanceRequestDTO())){
			    	String sql2="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid,trd.ticket_cancel_charges as ticketCancelCharges, tdrd.DESIGNATION_ID as desigId, " +
			    			"tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, " +
			    			"tdrd.DA_TYPE as daType, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne, tdrd.ADVANCE_AMOUNT as " +
			    			"tadaAdvanceAmount,cd.cda_amount as cdaAmount,tdrd.ref_request_id as referenceRequestID,ecd.workflow_status as status,cd.dv_no as dvNumber from " +
			    			"TADA_TD_ADV_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd,finance_details fd,TADA_TD_REQUEST_DETAILS trd where tdrd.REQUEST_ID=(select request_id from " +
			    			"tada_td_adv_request_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in(9,6)) " +
			    			"and epd.sfid=(select tdrd3.sfid from TADA_TD_ADV_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select request_id from tada_td_adv_request_details " +
			    			"where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+") and status not in (9,6))) and cd.reference_id=" +
			    			"(select  fd.id from emp_claim_details ecd1,finance_details fd  where ecd1.ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+")  And ecd1.id = fd.REFERENCE_ID  and ecd1.request_type_id=47 and ecd1.workflow_status in(8,60)) and ecd.workflow_status in(8,60) " +
			    			"and fd.id=cd.reference_id and ecd.id = fd.REFERENCE_ID and tdrd.ref_request_id = trd.request_id";
			    	workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql2).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("ticketCancelCharges", Hibernate.FLOAT).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("cdaAmount", Hibernate.INTEGER).addScalar("referenceRequestID", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("dvNumber", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());
				}
			}else {
				String requestId=workflowMap.getRequestId();
				//old code
				/*sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.TATKAL_QUOTA as tatkalQuota, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne from TADA_TD_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID=(select ref_request_id from emp_claim_details where request_id="+requestId+") and tdtt.id=(select TDRD2.CONVEYANCE_MODE from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select ref_request_id from emp_claim_details where request_id="+requestId+")) and tdem.id=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID=(select ref_request_id from emp_claim_details where request_id="+requestId+")) and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select ref_request_id from emp_claim_details where request_id="+requestId+")) and tdtt.status=1 and tdem.status=1";
				workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("tatkalQuota", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());*/
				//new code
				sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid,tdrd.ticket_cancel_charges as ticketCancelCharges,tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID=(select ref_request_id from emp_claim_details where request_id="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID=(select ref_request_id from emp_claim_details where request_id="+requestId+"))";
				workflowMap.setTadaTdAdvanceRequestDTO((TadaTdAdvanceRequestDTO) session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("ticketCancelCharges", Hibernate.FLOAT).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaTdAdvanceRequestDTO.class)).uniqueResult());
			}
			String orgRoleName=(String)session.createQuery("select name from OrgInstanceDTO where id in (select roleInstanceId from EmpRoleMappingDTO where sfid=?) and name in (?,?) and status=1").setString(0, workflowMap.getSfid()).setString(1, "TADA TASK HOLDER").setString(2, "TA DA /Medical Section Head").uniqueResult();
			workflowMap.setRoleInstanceName(orgRoleName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		finally{

			//session.close();

			session.flush();

		}
		return workflowMap;
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getTdRequestDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaApprovalRequestDTO> list=null;
		Session session=null;
		String sql=null;
		TadaApprovalRequestDTO tadaApprDTO=null;
		try {

			session = hibernateUtils.getSession(); //session=sessionFactory.openSession();

			

			String sfid=tadaRequestBean.getSfid();
			if(sfid==null){
				sfid = tadaRequestBean.getSfID();
				tadaRequestBean.setSfid(sfid);
			}
			
			//tadaApprDTO = tadaTdApprovalRequestDAO.getRefRequestId(tadaRequestBean);
             System.out.println("KHKJHJKH");
			tadaApprDTO = tadaTdApprovalRequestDAO.getRefRequestId(tadaRequestBean);
			
			if(tadaApprDTO!=null){
				if(CPSUtils.compareStrings(tadaApprDTO.getAdvanceFlag(), "Y")){
					String requestId=tadaApprDTO.getRequestId();
					
					
					//original commented by bkr 26/04/2016 
					//sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,ttard.advance_amount as tadaAdvanceAmount from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,TADA_TD_ADV_REQUEST_DETAILS ttard where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and ttard.ref_request_id="+requestId+" and ttard.status not in(9,6)";
					sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace,tdrd.PURPOSE as purpose, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,ttard.advance_amount as tadaAdvanceAmount from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd,TADA_TD_ADV_REQUEST_DETAILS ttard where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+") and ttard.ref_request_id="+requestId+" and ttard.status not in(9,6)";
					
					//original commented by bkr 26/04/2016 
					//list=session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
					list=session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("purpose", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			
				
				} else {
					//old code
					/*String requestId=tadaApprDTO.getRequestId();
					sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdtt.TRAVEL_TYPE as conveyanceMode, tdem.ENTITLE_CLASS as entitlementClass, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.TATKAL_QUOTA as tatkalQuota, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne from TADA_TD_REQUEST_DETAILS tdrd,TADA_TRAVEL_TYPE tdtt,TADA_TA_ENTITLETYPE_MASTER tdem,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and tdtt.id=(select TDRD2.CONVEYANCE_MODE from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and tdem.id=(select TDRD2.ENTITLEMENT_CLASS from TADA_TD_REQUEST_DETAILS tdrd2 where TDRD2.REQUEST_ID="+requestId+") and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
					list=session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("conveyanceMode", Hibernate.STRING).addScalar("entitlementClass", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("tatkalQuota", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
					*/
					//new code
					String requestId=tadaApprDTO.getRequestId();
					
					
					//original query commented by bkr 26/04/2016 
					
				//	sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,tdrd.project_name as projectName from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
					//addedby bkr 26/04/2016
					sql="select epd.BASIC_PAY as basicPay,epd.GRADE_PAY as gradePay,tdrd.REQUEST_ID as requestId, tdrd.SFID as sfid, tdrd.DESIGNATION_ID as desigId, tdrd.PHONE_NUMBER as phnNo, tdrd.STAY_DURATION as stayDuration, tdrd.TD_WORK_PLACE as tdWorkPlace,tdrd.PURPOSE as purpose, tdrd.AUTHORIZED_MOVE_ID as authorizedMove, tdrd.DA_TYPE as daType, tdrd.ENTITLE_EXEMPTION as entitleExemption, tdrd.AMMENDEMENT_ID as ammendementId, tdrd.IP_ADDRESS as ipAddress, to_char(tdrd.departure_date ,'dd-Mon-yyyy') as departureDateOne,tdrd.project_name as projectName from TADA_TD_REQUEST_DETAILS tdrd,EMP_PAYMENT_DETAILS epd where tdrd.REQUEST_ID="+requestId+" and epd.sfid=(select tdrd3.sfid from TADA_TD_REQUEST_DETAILS tdrd3 where tdrd3.REQUEST_ID="+requestId+")";
					
					//original query commented by bkr 26/04/2016 
					//list=session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("projectName",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
					list=session.createSQLQuery(sql).addScalar("basicPay", Hibernate.STRING).addScalar("gradePay", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("desigId", Hibernate.INTEGER).addScalar("phnNo", Hibernate.STRING).addScalar("stayDuration",Hibernate.INTEGER).addScalar("tdWorkPlace", Hibernate.STRING).addScalar("purpose", Hibernate.STRING).addScalar("authorizedMove",Hibernate.STRING).addScalar("daType", Hibernate.STRING).addScalar("entitleExemption", Hibernate.STRING).addScalar("ammendementId", Hibernate.STRING).addScalar("ipAddress", Hibernate.STRING).addScalar("departureDateOne", Hibernate.STRING).addScalar("projectName",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
				}
				//crit=session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaApprDTO.getRequestId()));
				//list=crit.list();
			} else {
				tadaRequestBean.setRemarks(CPSConstants.TDNOTAPPLY);
				tadaRequestBean.setResult(CPSConstants.TDNOTAPPLY);
			}
			
			
		} catch(Exception e) {
			throw e;
		}
		finally {

			//session.close();

			

		}
		return list;
	}
	public String insertProjDirDetails(TadaRequestProcessBean processBean)throws Exception {
		Session session=null;
		String message=null;
		try{
			session=hibernateUtils.getSession();
			session.save(processBean.getDynamicWorkFlowTxnDTO());
			session.flush();
			message=CPSConstants.SUCCESS;
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	public String approvedRequest(TadaRequestProcessBean processBean) throws Exception {
		//log.debug("::<<<<<TadaRequestProcessBean<<<<<<Method>>>>>>>>>>>>>>>approvedRequest(TadaRequestProcessBean processBean)>>>>>>>>>");
		RequestBean rb = null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
//			String designationID=(String)session.createSQLQuery("select to_char(designation_id) from emp_master where sfid=(select sfid from tada_td_request_details where request_id=?)").setInteger(0, Integer.parseInt(processBean.getRequestId())).uniqueResult();
//			processBean.setDesignationID(designationID);
			rb = new RequestBean();
			if(CPSUtils.compareStrings(processBean.getRequestTypeID(), "45") || CPSUtils.compareStrings(processBean.getRequestTypeID(), "46"))
				processBean.setHistoryID(processBean.getHistoryID().split(",")[0]);
			BeanUtils.copyProperties(processBean, rb);
			rb = txRequestProcess.approvedRequest(rb);
			if (CPSUtils.compareStrings(CPSConstants.UPDATE, rb.getMessage())) {
				log.debug("::request workflow completed, so update in the main table");
				BeanUtils.copyProperties(rb, processBean);
				if(CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDBUILDUP) || CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDPROJECT) || CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDADVANCES)){
					processBean.setApprovedStage("2");
					tadaDomainObject.updateTxnDetails(processBean);
                    /*   if(!CPSUtils.isNullOrEmpty(processBean.getJsonValue())){
                    	   JSONObject mainJson = new JSONObject(processBean.getJsonValue());
                    	   JSONObject mroDetails = (JSONObject)mainJson.getJSONObject("mroDetails");
                    	   tadaDomainObject.submitMroDetails(processBean);}*/
				} else if(CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDSETTLEMENTS)){
					JSONObject mainJson = new JSONObject(processBean.getJsonValue());
					JSONObject accJson=(JSONObject)mainJson.get("accDetails");
					JSONObject mroPaymentJson=(JSONObject)mainJson.get("mroPaymentDetails");
					if(Float.parseFloat(processBean.getExcessAmount())<0 && !CPSUtils.compareStrings("", processBean.getExcessAmountFine())){
						tadaDomainObject.updateTxnDetails(processBean);
						if(accJson.length()>0){
							//tadaDomainObject.submitTdNewDaAccDetails(processBean);
						
						if(Integer.parseInt(processBean.getExcessAmount())<0){
						rb.setMessage(tadaDomainObject.submitMroPaymentDetails(processBean));
						}
					} 
						
						
						if(Integer.parseInt(processBean.getExcessAmount())<0 && accJson.length()==0 && mroPaymentJson.length()>0  ){
							rb.setMessage(tadaDomainObject.submitMroPaymentDetails(processBean));
							
						}else {
						
					}
						}else{
						if(accJson.length()>0){
							//tadaDomainObject.submitTdNewDaAccDetails(processBean);
						}
						rb.setMessage(tadaDomainObject.updateTxnDetails(processBean));
					}	
				} else if(CPSUtils.compareStrings(processBean.getRequestType(), CPSConstants.TADATDREIMBURSEMENT)){
					tadaDomainObject.updateTxnDetails(processBean);
					JSONObject mainJson = new JSONObject(processBean.getJsonValue());
					JSONObject accJson=(JSONObject)mainJson.get("accDetails");
					if(accJson.length()>0){
						tadaDomainObject.submitTdNewDaAccDetails(processBean);
					}
				}
			} else if(CPSUtils.compareStrings(CPSConstants.SUCCESS, rb.getMessage()) && !CPSUtils.isNullOrEmpty(processBean.getProjectType())){
				TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", processBean.getRequestId())).uniqueResult();
				tadaApprovalRequestDTO.setProjectName(processBean.getProjectType());
				
				session.saveOrUpdate(tadaApprovalRequestDTO);
				session.flush();
			} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, processBean.getRequestType())){
				processBean.setApprovedStage("1");
				tadaDomainObject.updateTxnDetails(processBean);
			} else if((CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, processBean.getRequestType()) || (CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, processBean.getRequestType()))) && CPSUtils.compareStrings(rb.getParentID(), "SF0794") ){
			
				// REMOVED CODE: (&& CPSUtils.compareStrings(rb.getParentID(), "SF0794")) ON 05-05-2014 BECAUSE OF WORKFLOW CHANGE
				
				processBean.setApprovedStage("1");
				tadaDomainObject.updateTxnDetails(processBean);
				tadaDomainObject.submitMroDetails(processBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return rb.getMessage();
	}
	@SuppressWarnings("unchecked")
	public String submitTdAdvTxnDetails(TadaRequestProcessBean tadaRPB) throws Exception {
		Session session = null;
		TadaTdAdvanceRequestDTO tadaTdAdvDTO = null;
		TadaApprovalRequestDTO tadaApprDTO=null;
		TadaTdTxnDTO tadaTdTxnDTO =null;
		EmployeeClaimDetailsDTO empClaimDetailsDTO=null;
		List<TadaTdAdvanceRequestDTO> list=null;
		List<TadaTdAdvanceRequestDTO> list1=null;
		Criteria crit=null;
		try {
			StringBuffer sql=new StringBuffer();
			log.debug("TadaRequestProcess >>>>>>>>>>>> submitTdAdvTxnDetails(TadaRequestProcessBean tadaRPB) >>>>> Start");
			tadaTdAdvDTO = new TadaTdAdvanceRequestDTO();
			tadaApprDTO = new TadaApprovalRequestDTO();
			tadaTdTxnDTO =new TadaTdTxnDTO();
			tadaApprDTO = tadaTdApprovalRequestDAO.getRefRequestId(tadaRPB);
//			if(CPSUtils.compareStrings(CPSConstants.TADA_TD_ADVANCE, tadaRPB.getAmendmentType())){
//				tadaTdAdvDTO = tadaTdApprovalRequestDAO.getTadaTdApprovalDetails(tadaRPB.getAmendmentReqId());
//			} else{
//				tadaTdAdvDTO = tadaTdApprovalRequestDAO.getTadaTdApprovalDetails(tadaRPB.getReferenceRequestID());
//			}
//			if(tadaTdAdvDTO.getRequestId()==null){
//				tadaRPB.setRemarks(CPSConstants.TADA_TD_NOT_COMPLETE);
//				return tadaRPB.getRemarks();
//			} else {
				BeanUtils.copyProperties(tadaRPB, tadaTdAdvDTO);
				tadaTdAdvDTO.setCreationDate(CPSUtils.getCurrentDate());
				tadaTdAdvDTO.setStatus(2);
				tadaTdAdvDTO.setReferenceRequestID(tadaApprDTO.getRequestId());
				tadaTdAdvDTO.setApplyDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
				tadaTdAdvDTO.setDesigId(tadaRPB.getEmpDetailsList().getDesignation());
				tadaTdAdvDTO.setTadaAdvanceAmount(tadaRPB.getTadaAdvanceAmount());
				
				tadaTdTxnDTO.setStatus(2);
				session = hibernateUtils.getSession();
				//session1 = hibernateUtils.getSession();
				crit=session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("sfID", tadaRPB.getSfID()));
				log.debug("crit "+crit);
				list1=crit.list();
				if(list1.size()==0){
					session.save(tadaTdAdvDTO);
					session.flush();
					if(!CPSUtils.isNullOrEmpty(tadaRPB.getProjectName())){
						sql.append("update TadaApprovalRequestDTO set advance_flag = 'P',projectName=? where requestId=?");
						session.createQuery(sql.toString()).setString(0, tadaRPB.getProjectName()).setString(1, tadaApprDTO.getRequestId()).executeUpdate();
					}else{
						sql.append("update TadaApprovalRequestDTO set advance_flag = 'P' where requestId=?");
						session.createQuery(sql.toString()).setString(0, tadaApprDTO.getRequestId()).executeUpdate();
					}
					tadaRPB.setMessage(CPSConstants.SUCCESS);
				} else {
					crit=session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.or(Expression.eq("status", 2), Expression.eq("status", 4))).add(Expression.eq("referenceRequestID", tadaTdAdvDTO.getReferenceRequestID())).add(Expression.eq("sfID", tadaRPB.getSfID()));
					list = crit.list();
					if(list.size()==0){
						session.save(tadaTdAdvDTO);
						session.flush();
						if(!CPSUtils.isNullOrEmpty(tadaRPB.getProjectName())){
							sql.append("update TadaApprovalRequestDTO set advance_flag = 'P',projectName=? where requestId=?");
							session.createQuery(sql.toString()).setString(0, tadaRPB.getProjectName()).setString(1, tadaApprDTO.getRequestId()).executeUpdate();
						}else{
							sql.append("update TadaApprovalRequestDTO set advance_flag = 'P' where requestId=?");
							session.createQuery(sql.toString()).setString(0, tadaApprDTO.getRequestId()).executeUpdate();
						}
						tadaRPB.setMessage(CPSConstants.SUCCESS);
					}
					else {
						tadaRPB.setRemarks(CPSConstants.TDADVAPPLIED);
					}
				}
				if(CPSUtils.compareStrings(CPSConstants.SUCCESS, tadaRPB.getMessage())){
					empClaimDetailsDTO =new EmployeeClaimDetailsDTO();
					tadaRPB.setInnerRequestType(CPSConstants.ADVANCE);
					empClaimDetailsDTO.setModuleId(7);
					empClaimDetailsDTO.setFundId(0);
					empClaimDetailsDTO.setRequestID(Integer.parseInt(tadaRPB.getRequestId()));
					empClaimDetailsDTO.setRefRequestId(Integer.parseInt(tadaApprDTO.getRequestId()));
					empClaimDetailsDTO.setAmountClaimed(tadaRPB.getTadaAdvanceAmount());
					empClaimDetailsDTO.setWorkFlowStatus(2);
					empClaimDetailsDTO.setStatus(1);
					empClaimDetailsDTO.setAppliedBy(tadaRPB.getSfid());
					empClaimDetailsDTO.setRequestType(tadaRPB.getInnerRequestType());
					empClaimDetailsDTO.setAppliedDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
					empClaimDetailsDTO.setIpAddress(tadaRPB.getIpAddress());
					empClaimDetailsDTO.setRequestType(tadaRPB.getInnerRequestType());
					empClaimDetailsDTO.setRequestTypeID(Integer.parseInt(tadaRPB.getRequestTypeID()));
					session.save(empClaimDetailsDTO);
					session.flush();
					tadaRPB.setMessage(CPSConstants.SUCCESS);
				}
				
				return tadaRPB.getMessage();
				
//			}
			
			
		} catch (Exception e) {
			tadaRPB.setMessage(CPSConstants.FAILED);
			throw e;
		} 
		
	}
	public String updateStatusToDecline(String requestID, String requestType, String status,String ipAddress) throws Exception {
		String message = null;
		try {
			if(CPSUtils.compareStrings(requestType, CPSConstants.TADATDBUILDUP) || CPSUtils.compareStrings(requestType, CPSConstants.TADATDPROJECT)){
				message = tadaDomainObject.updateTxnDetailsStatus(status, requestID, TadaApprovalRequestDTO.class,ipAddress);
				if(CPSUtils.compareStrings(CPSConstants.SUCCESS, message)){
					message = tadaDomainObject.updateTxnDetailsStatus(status, requestID, TadaTdTxnDTO.class,ipAddress);
				}
			} else if(CPSUtils.compareStrings(requestType, CPSConstants.TADATDADVANCES)){
				tadaDomainObject.updateTxnDetailsStatus(status, requestID, TadaTdAdvanceRequestDTO.class,ipAddress);
				tadaDomainObject.updateTxnDetailsStatus(status, requestID, TadaTdTxnDTO.class,ipAddress);
				message = tadaDomainObject.updateSettlementTxnDetailsStatus(status, requestID, EmployeeClaimDetailsDTO.class);
				
			} else if(CPSUtils.compareStrings(requestType, CPSConstants.TADATDSETTLEMENTS) || CPSUtils.compareStrings(requestType, CPSConstants.SETTLEMENT)){
				message = tadaDomainObject.updateSettlementTxnDetailsStatus(status, requestID, EmployeeClaimDetailsDTO.class);
				message = tadaDomainObject.updateTdSettDetails(status,requestID);
			} 
			if(CPSUtils.compareStrings(requestType, CPSConstants.TADA_TD_REIMBURSEMENT) || CPSUtils.compareStrings(requestType, CPSConstants.REIMBURSEMENT) || CPSUtils.compareStrings(requestType, CPSConstants.TADATDREIMBURSEMENT)){
				message = tadaDomainObject.updateSettlementTxnDetailsStatus(status, requestID, EmployeeClaimDetailsDTO.class);
				message = tadaDomainObject.updateTdReimDetails(status,requestID);
			}
			
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;

	}
	
	@SuppressWarnings("unchecked")
	public List<TadaTdTxnDTO> getTdTxnDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaTdTxnDTO> list=null;
		Session session=null;
		String sql1=null;
		List<KeyValueDTO> leaveDetailsList=null;
		String qry=null;
		try {
			String sfid=tadaRequestBean.getSfid();
			session = hibernateUtils.getSession();
//			sql="select tttd.sfid as sfID,tttd.request_id as requestId,tttd.request_type as requestType,tttd.status as status,max(rwh.id) as historyID,ttrd.advance_flag as advanceFlag,to_char(tttd.journey_date,'DD-Mon-YYYY') as journeyDateOne,ttard.request_id as advRequestId from REQUEST_WORKFLOW_HISTORY rwh,TADA_TD_TXN_DETAILS tttd,TADA_TD_REQUEST_DETAILS ttrd,TADA_TD_ADV_REQUEST_DETAILS ttard where rwh.request_id=tttd.request_id and tttd.sfid=?  and rwh.status not in(9,6) and tttd.status not in(9,6) and ttrd.request_id=rwh.request_id and ttrd.request_id=ttard.ref_request_id and ttard.status not in(9,6) group by tttd.sfid, tttd.request_id, tttd.request_type, tttd.status, ttrd.advance_flag, to_char(tttd.journey_date,'DD-Mon-YYYY'), ttard.request_id";
//			list = session.createSQLQuery(sql).addScalar("sfID", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("advanceFlag", Hibernate.STRING).addScalar("journeyDateOne", Hibernate.STRING).addScalar("advRequestId", Hibernate.INTEGER).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(TadaTdTxnDTO.class)).list();
//			if(list==null){
				sql1="select tttd.sfid as sfID,tttd.request_id as requestId,tttd.request_type as requestType,tttd.status as status,max(rwh.id) as historyID," +
						"ttrd.advance_flag as advanceFlag,to_char(min(ttrjd.departure_date),'DD-Mon-YYYY') as journeyDateOne,ttrd.reim_flag as reimFlag," +
						"ttrd.sett_flag as settFlag,ttrd.stay_duration as stayDuration,ttrd.ammendement_id as amendmentId,to_char(ttrd.arrival_date,'DD-Mon-YYYY') as strArrivalDate " +
						"from REQUEST_WORKFLOW_HISTORY rwh,TADA_TD_TXN_DETAILS tttd,TADA_TD_REQUEST_DETAILS ttrd,TADA_TD_REQ_JOURNEY_DETAILS ttrjd where " +
						"rwh.request_id=tttd.request_id and tttd.sfid=? and tttd.status not in(9) and ttrd.request_id=rwh.request_id and ttrjd.reference_id=ttrd.request_id " +
						"group by tttd.sfid, tttd.request_id, tttd.request_type, tttd.status, ttrd.advance_flag, to_char(tttd.journey_date,'DD-Mon-YYYY'), ttrd.reim_flag ," +
						"ttrd.sett_flag, ttrd.stay_duration, ttrd.ammendement_id, to_char(ttrd.arrival_date,'DD-Mon-YYYY') order by tttd.request_id desc";
				list = session.createSQLQuery(sql1).addScalar("sfID", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("advanceFlag", Hibernate.STRING).addScalar("journeyDateOne", Hibernate.STRING).addScalar("reimFlag", Hibernate.STRING).addScalar("settFlag",Hibernate.STRING).addScalar("stayDuration", Hibernate.INTEGER).addScalar("amendmentId", Hibernate.STRING).addScalar("strArrivalDate", Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(TadaTdTxnDTO.class)).list();
				if(list.size()!=0){
					for (TadaTdTxnDTO tadaTdTxnDTO2 : list) {
						StatusMasterDTO statusMasterDTO=new StatusMasterDTO();
						statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", tadaTdTxnDTO2.getStatus())).uniqueResult();
						tadaTdTxnDTO2.setStrStatus(statusMasterDTO.getName());
						qry="select lrd.request_id as key,ltm.leave_type||'('||to_char(lrd.from_date,'dd-Mon-yyyy')||' '||'To'||' '||to_char(lrd.to_date,'dd-Mon-yyyy')||')' " +
						"as name,lrd.status as value from leave_request_details lrd,leave_type_master ltm where lrd.sfid=? and lrd.leave_type_id=ltm.id and lrd.from_date>=? and lrd.to_date<=? and lrd.status not in(9,6) AND lrd.leave_type_id = 3";
						leaveDetailsList=session.createSQLQuery(qry).addScalar("key", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("value", Hibernate.STRING).setString(0, tadaTdTxnDTO2.getSfID()).setString(1, tadaTdTxnDTO2.getJourneyDateOne()).setString(2, tadaTdTxnDTO2.getStrArrivalDate()).setResultTransformer(Transformers.aliasToBean(KeyValueDTO.class)).list();
						TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO=(TadaTdLeaveDetailsDTO)session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(tadaTdTxnDTO2.getRequestId()))).uniqueResult();
						if(leaveDetailsList.size()>0){
							for (KeyValueDTO keyValueDTO : leaveDetailsList) {
								if(!CPSUtils.isNullOrEmpty(tadaTdLeaveDetailsDTO))
									keyValueDTO.setFlag("yes");
								else
									keyValueDTO.setFlag("no");
							}
							tadaTdTxnDTO2.setLeaveDetailsList(leaveDetailsList);
						}
					}
				}
//			}
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdTxnDTO> getTdAdvTxnDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaTdTxnDTO> list=null;
		Session session=null;
		String sql=null;
		try {
			String sfid=tadaRequestBean.getSfid();
			session = hibernateUtils.getSession();
//			sql1="select tttd.sfid as sfID,tttd.request_id as requestId,ttard.ref_request_id as refRequestId,tttd.request_type as requestType,tttd.status as status,max(rwh.id) as historyID,to_char(tttd.journey_date,'DD-Mon-YYYY') as journeyDateOne,cd.cda_amount as cdaAmount from REQUEST_WORKFLOW_HISTORY rwh,TADA_TD_TXN_DETAILS tttd,TADA_TD_ADV_REQUEST_DETAILS ttard,EMP_CLAIM_DETAILS ecd,CDA_DETAILS cd where rwh.request_id=tttd.request_id and tttd.sfid=?  and rwh.status not in(9,6) and tttd.status not in(9,6) and ttard.request_id=rwh.request_id and ecd.request_id=ttard.request_id and ecd.id=cd.reference_id and ecd.workflow_status not in(9,6,60) group by tttd.sfid, tttd.request_id, tttd.request_type, tttd.status, to_char(tttd.journey_date,'DD-Mon-YYYY'), cd.cda_amount, ttard.ref_request_id";
//			list = session.createSQLQuery(sql1).addScalar("sfID", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("refRequestId", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("journeyDateOne", Hibernate.STRING).addScalar("cdaAmount", Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(TadaTdTxnDTO.class)).list();
//			if(list.size()==0){
				sql="select tttd.sfid as sfID,tttd.request_id as requestId,ttard.ref_request_id as refRequestId,tttd.request_type as requestType,ecd.workflow_status as status,max(rwh.id) as historyID,to_char(tttd.journey_date,'DD-Mon-YYYY') as journeyDateOne from REQUEST_WORKFLOW_HISTORY rwh,TADA_TD_TXN_DETAILS tttd,TADA_TD_ADV_REQUEST_DETAILS ttard,EMP_CLAIM_DETAILS ecd where rwh.request_id=tttd.request_id and tttd.sfid=?  and rwh.status not in(9,6) and tttd.status not in(9,6) and ttard.request_id=rwh.request_id and ecd.applied_by=? and ecd.workflow_status not in(9,6) and ecd.request_id=ttard.request_id group by tttd.sfid, tttd.request_id, ttard.ref_request_id, tttd.request_type, ecd.workflow_status, to_char(tttd.journey_date,'DD-Mon-YYYY')";
				list = session.createSQLQuery(sql).addScalar("sfID", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("refRequestId", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).addScalar("status", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("journeyDateOne", Hibernate.STRING).setString(0, sfid).setString(1, sfid).setResultTransformer(Transformers.aliasToBean(TadaTdTxnDTO.class)).list();
//				
//				itr=list1.iterator();
//				while(itr.hasNext()){
//					list.add(itr.next());
//				}
//			}
//			
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdTxnDTO> getTdCdaDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaTdTxnDTO> list=null;
		Session session=null;
		String sql=null;
		try{
			session = hibernateUtils.getSession();
			String sfid=tadaRequestBean.getSfid();
			sql="select cd.cda_amount as cdaAmount,ecd.request_id as requestId,ecd.ref_request_id as refRequestId from emp_claim_details ecd,cda_details cd,finance_details fd where ecd.applied_by=? and fd.id=cd.reference_id And  ecd.id=fd.REFERENCE_ID  and ecd.request_type_id=47";
			list = session.createSQLQuery(sql).addScalar("cdaAmount", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("refRequestId",Hibernate.STRING).setString(0, sfid).setResultTransformer(Transformers.aliasToBean(TadaTdTxnDTO.class)).list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<EmployeeClaimDetailsDTO> getTdSettTxnDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<EmployeeClaimDetailsDTO> list=null;
		Session session=null;
		String sql=null;
		String sql1=null;
		String sql2=null;
		try {
			session = hibernateUtils.getSession();
			sql="select ecd.request_id as requestID,max(rwh.id) as id,ecd.ref_request_id as refRequestID,ecd.request_type as requestType,ecd.workflow_status as workFlowStatus,cd.cda_amount as amountClaimed from EMP_CLAIM_DETAILS ecd,REQUEST_WORKFLOW_HISTORY rwh,CDA_DETAILS cd,finance_details fd  where rwh.request_id=ecd.request_id and ecd.applied_by=? and ecd.request_type_id=48 and ecd.workflow_status not in(9,6,60) and fd.id=cd.reference_id And fd.REFERENCE_ID = ecd.id  group by ecd.request_id, ecd.ref_request_id, ecd.request_type, ecd.workflow_status, cd.cda_amount";
			list = session.createSQLQuery(sql).addScalar("requestID", Hibernate.INTEGER).addScalar("id", Hibernate.INTEGER).addScalar("refRequestId",Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).addScalar("workFlowStatus", Hibernate.INTEGER).addScalar("amountClaimed", Hibernate.FLOAT).setString(0, tadaRequestBean.getSfid()).setResultTransformer(Transformers.aliasToBean(EmployeeClaimDetailsDTO.class)).list();
			if(list.size()==0){
				sql1="select ecd.request_id as requestID,max(rwh.id) as id,ecd.ref_request_id as refRequestID,ecd.request_type as requestType,ecd.workflow_status as workFlowStatus  from EMP_CLAIM_DETAILS ecd,REQUEST_WORKFLOW_HISTORY rwh where rwh.request_id=ecd.request_id and ecd.applied_by=? and ecd.request_type_id=48 and ecd.workflow_status not in(9,6,60) group by ecd.request_id, ecd.ref_request_id, ecd.request_type, ecd.workflow_status, ecd.request_id, ecd.ref_request_id, ecd.request_type";
				list = session.createSQLQuery(sql1).addScalar("requestID", Hibernate.INTEGER).addScalar("id", Hibernate.INTEGER).addScalar("refRequestId",Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).addScalar("workFlowStatus", Hibernate.INTEGER).setString(0, tadaRequestBean.getSfid()).setResultTransformer(Transformers.aliasToBean(EmployeeClaimDetailsDTO.class)).list();		
			}
			if(list.size()==0){
				sql2="select ecd.request_id as requestID,max(rwh.id) as id,ecd.ref_request_id as refRequestID,ecd.request_type as requestType,ecd.workflow_status as workFlowStatus  from EMP_CLAIM_DETAILS ecd,REQUEST_WORKFLOW_HISTORY rwh where rwh.request_id=ecd.request_id and ecd.applied_by=? and ecd.request_type_id=47 and ecd.workflow_status not in(9,6,60) group by ecd.request_id, ecd.ref_request_id, ecd.request_type, ecd.workflow_status, ecd.request_id, ecd.ref_request_id, ecd.request_type";
				list = session.createSQLQuery(sql2).addScalar("requestID", Hibernate.INTEGER).addScalar("id", Hibernate.INTEGER).addScalar("refRequestId",Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).addScalar("workFlowStatus", Hibernate.INTEGER).setString(0, tadaRequestBean.getSfid()).setResultTransformer(Transformers.aliasToBean(EmployeeClaimDetailsDTO.class)).list();		
			}
			
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<EmployeeClaimDetailsDTO> getTdReimTxnDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<EmployeeClaimDetailsDTO> list=null;
		Session session=null;
		String sql=null;
		String sql1=null;
		try {
			session = hibernateUtils.getSession();
			sql="select ecd.request_id as requestID,max(rwh.id) as id,ecd.ref_request_id as refRequestID,ecd.request_type as requestType,ecd.workflow_status as workFlowStatus,cd.cda_amount as amountClaimed from EMP_CLAIM_DETAILS ecd,REQUEST_WORKFLOW_HISTORY rwh,CDA_DETAILS cd,finance_details fd where rwh.request_id=ecd.request_id and ecd.applied_by=? and ecd.request_type_id=49 and ecd.workflow_status not in(9,6,60) and fd.id=cd.reference_id And ecd.id = fd.reference_id group by ecd.request_id, ecd.ref_request_id, ecd.request_type, ecd.workflow_status, cd.cda_amount";
			list = session.createSQLQuery(sql).addScalar("requestID", Hibernate.INTEGER).addScalar("id", Hibernate.INTEGER).addScalar("refRequestId",Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).addScalar("workFlowStatus", Hibernate.INTEGER).addScalar("amountClaimed", Hibernate.FLOAT).setString(0, tadaRequestBean.getSfid()).setResultTransformer(Transformers.aliasToBean(EmployeeClaimDetailsDTO.class)).list();
			if(list.size()==0){
				sql1="select ecd.request_id as requestID,max(rwh.id) as id,ecd.ref_request_id as refRequestID,ecd.request_type as requestType,ecd.workflow_status as workFlowStatus  from EMP_CLAIM_DETAILS ecd,REQUEST_WORKFLOW_HISTORY rwh where rwh.request_id=ecd.request_id and ecd.applied_by=? and ecd.request_type_id=49 and ecd.workflow_status not in(9,6,60) group by ecd.request_id, ecd.ref_request_id, ecd.request_type, ecd.workflow_status, ecd.request_id, ecd.ref_request_id, ecd.request_type";
				list = session.createSQLQuery(sql1).addScalar("requestID", Hibernate.INTEGER).addScalar("id", Hibernate.INTEGER).addScalar("refRequestId",Hibernate.INTEGER).addScalar("requestType", Hibernate.STRING).addScalar("workFlowStatus", Hibernate.INTEGER).setString(0, tadaRequestBean.getSfid()).setResultTransformer(Transformers.aliasToBean(EmployeeClaimDetailsDTO.class)).list();		
			}
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<RequestCommonBean> getTdTxnHistoryDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<RequestCommonBean> list=null;
		Session session=null;
		Criteria crit=null;
		try {
			session = hibernateUtils.getSession();
			crit = session.createCriteria(RequestCommonBean.class).add(Expression.eq("historyID", tadaRequestBean.getRequestId()));
			list = crit.list();
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	
	public String updateTxnDetails (TadaRequestProcessBean tadaRPB)throws Exception {
		String message= "";
		Session session=null;
		StringBuffer sql=new StringBuffer();
		StringBuffer sql1=new StringBuffer();
		StringBuffer sql2=new StringBuffer();
		StringBuffer sql3=new StringBuffer();
		StringBuffer sql4=new StringBuffer();
		StringBuffer sql5=new StringBuffer();
		TadaApprovalRequestDTO tadaApprovalRequestDTO=null;
		try {
            session=hibernateUtils.getSession();
            
            
            if(CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, tadaRPB.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, tadaRPB.getRequestType())){
            	tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRPB.getAmendmentReqId())).uniqueResult();
            	if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
            		if((CPSUtils.compareStrings(tadaApprovalRequestDTO.getAdvanceFlag(), "P") || CPSUtils.compareStrings(tadaApprovalRequestDTO.getAdvanceFlag(), "Y")) && 
            				!CPSUtils.compareStrings(tadaApprovalRequestDTO.getAdvanceFlag(), "C")){
            			TadaTdAdvanceRequestDTO tadaTdAdvanceRequestDTO=(TadaTdAdvanceRequestDTO)session.createCriteria(TadaTdAdvanceRequestDTO.class).add(Expression.eq("referenceRequestID", tadaRPB.getAmendmentReqId())).add(Expression.ne("status", 9)).uniqueResult();
            			if(!CPSUtils.isNullOrEmpty(tadaTdAdvanceRequestDTO)){
            				if(tadaRPB.getStayDuration()==0){
            					session.createQuery("update TadaTdTxnDTO set status=9 where requestId=?").setString(0, tadaTdAdvanceRequestDTO.getRequestId()).executeUpdate();
                				session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=9 where requestID=?").setInteger(0, Integer.parseInt(tadaTdAdvanceRequestDTO.getRequestId())).executeUpdate();
                				session.createSQLQuery("update REQUEST_WORKFLOW_HISTORY set status=9 where id=(select max(id) from REQUEST_WORKFLOW_HISTORY where request_id=?)").setString(0, tadaTdAdvanceRequestDTO.getRequestId()).executeUpdate();
//                				tadaTdAdvanceRequestDTO.setStatus(9);
//                				session.saveOrUpdate(tadaTdAdvanceRequestDTO);
            				}
            			}
            		}
            	}
            	sql.append("update TadaApprovalRequestDTO set status = 9,ammendementId=? where requestId=?");
            	session.createQuery(sql.toString()).setString(0, tadaRPB.getRequestId()).setString(1, tadaRPB.getAmendmentReqId()).executeUpdate();
            	sql2.append("update RequestCommonBean set status = 9 where requestID=?");
            	session.createQuery(sql2.toString()).setString(0, tadaRPB.getAmendmentReqId()).executeUpdate();
            	sql1.append("update TadaTdTxnDTO set status = 9 where requestId=?");
            	session.createQuery(sql1.toString()).setString(0, tadaRPB.getAmendmentReqId()).executeUpdate();
            	if(CPSUtils.compareStrings(CPSConstants.TADA_APPROVAL, tadaRPB.getAmendmentType())){
            		session.createSQLQuery("update REQUEST_WORKFLOW_HISTORY set remarks=? where id=(select max(id) from REQUEST_WORKFLOW_HISTORY where request_id=?)").setString(0, tadaRPB.getRequestType()+" request is cancelled due to amendment.").setString(1, tadaRPB.getAmendmentReqId()).executeUpdate();
            	}
            	message = CPSConstants.SUCCESS;
            } else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, tadaRPB.getRequestType())){
            	sql3.append("update TADA_TD_REQUEST_DETAILS set ADVANCE_FLAG = 'N' where REQUEST_ID=(select REF_REQUEST_ID from TADA_TD_ADV_REQUEST_DETAILS where REQUEST_ID=?)");
            	session.createSQLQuery(sql3.toString()).setString(0, tadaRPB.getAmendmentReqId()).executeUpdate();
            	sql2.append("update RequestCommonBean set status = 9 where requestID=?");
            	session.createQuery(sql2.toString()).setString(0, tadaRPB.getAmendmentReqId()).executeUpdate();
            	sql1.append("update TadaTdTxnDTO set status = 9 where requestId=?");
        		session.createQuery(sql1.toString()).setString(0, tadaRPB.getAmendmentReqId()).executeUpdate();
        		sql4.append("update EmployeeClaimDetailsDTO set workFlowStatus = 9 where requestID=?");
        		session.createQuery(sql4.toString()).setString(0, tadaRPB.getAmendmentReqId()).executeUpdate();
        		sql.append("update TadaTdAdvanceRequestDTO set status = 9 where requestId=?");
            	session.createQuery(sql.toString()).setString(0, tadaRPB.getAmendmentReqId()).executeUpdate();
            	sql5.append("update TADA_TD_REQ_JOURNEY_DETAILS set TICKET_FARE=0,TICKET_FARE_AFT_RES=0 where REFERENCE_ID=(select REF_REQUEST_ID from TADA_TD_ADV_REQUEST_DETAILS where REQUEST_ID=?)");
            	session.createSQLQuery(sql5.toString()).setInteger(0, Integer.parseInt(tadaRPB.getAmendmentReqId())).executeUpdate();
            	session.flush();
            	message = CPSConstants.SUCCESS;
            } else if(CPSUtils.compareStrings(CPSConstants.TADATDSETTLEMENTS, tadaRPB.getRequestType())){
            	sql3.append("update TADA_TD_REQUEST_DETAILS set SETT_FLAG = 'N' where REQUEST_ID=(select REF_REQUEST_ID from EMP_CLAIM_DETAILS where REQUEST_ID=?)");
            	session.createSQLQuery(sql3.toString()).setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
            	sql.append("update EmployeeClaimDetailsDTO set workFlowStatus = 9 where requestID=?");
            	session.createQuery(sql.toString()).setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
            	sql1.append("update RequestCommonBean set status = 9 where requestID=?");
            	session.createQuery(sql1.toString()).setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
            } else if(CPSUtils.compareStrings(CPSConstants.TADATDREIMBURSEMENT, tadaRPB.getRequestType())){
            	sql3.append("update TADA_TD_REQUEST_DETAILS set REIM_FLAG = 'N' where REQUEST_ID=(select REF_REQUEST_ID from EMP_CLAIM_DETAILS where REQUEST_ID=?)");
            	session.createSQLQuery(sql3.toString()).setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
            	sql.append("update EmployeeClaimDetailsDTO set workFlowStatus = 9 where requestID=?");
            	session.createQuery(sql.toString()).setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
            	sql1.append("update RequestCommonBean set status = 9 where requestID=?");
            	session.createQuery(sql1.toString()).setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
                
            }
        }catch (Exception e) {
			throw e;
		}
		return message;
		
	}
	@SuppressWarnings("unchecked")
	public List<TadaDaNewDetailsDTO> getDaNewDetails(TadaRequestBean tadaRequestBean) throws Exception{
		Session session=null;
		Criteria crit=null;
		List<TadaDaNewDetailsDTO> list=null;
		try{
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TadaDaNewDetailsDTO.class).add(Expression.eq("status", 1));
			list=crit.list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<CityTypeDTO> getCityList(TadaRequestBean tadaRequestBean) throws Exception{
		Session session=null;
		Criteria crit=null;
		List<CityTypeDTO> list=null;
		try{
			session=hibernateUtils.getSession();
			crit=session.createCriteria(CityTypeDTO.class).add(Expression.eq("status", 1)).addOrder(Order.asc("cityName"));
			list=crit.list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaDetailsDTO> getDaOldDetails(TadaRequestBean tadaRequestBean) throws Exception{
		Session session=null;
		Criteria crit=null;
		List<TadaDetailsDTO> list=null;
		try{
			session=hibernateUtils.getSession();
			crit=session.createCriteria(TadaDetailsDTO.class).add(Expression.eq("status", 1));
			list=crit.list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	public TadaRequestBean saveFinDetails(TadaRequestBean tadaRPB)throws Exception{
		Session session=null;
		FinanceDetailsDTO finDetailsDTO=null;
		EmployeeClaimDetailsDTO empClaimDetailsDTO=null;
		try{
			session = hibernateUtils.getSession();
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			String type=tadaRPB.getType();
//			if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaReimbursement")||CPSUtils.compareStrings(tadaRPB.getType(),"cdaSettlement")||CPSUtils.compareStrings(tadaRPB.getType(),"cdaAdvance")){
//				if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaReimbursement")){
//					 type="reimbursement";
//				}
//		                      
//				if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaSettlement")){
//					type="settlement";	
//				}
//				if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaAdvance")){
//					type="advance";
//				}
//					
//			}
			for(int i=0;i<mainJson.length();i++){
				
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				empClaimDetailsDTO = new EmployeeClaimDetailsDTO();
				empClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", subJson.getInt("requestId"))).add(Expression.eq("requestType", type)).add(Expression.eq("workFlowStatus", 8)).uniqueResult();
				finDetailsDTO = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq("referenceID", empClaimDetailsDTO.getId())).uniqueResult();
				if(finDetailsDTO==null){
					finDetailsDTO = new FinanceDetailsDTO();
				}
				finDetailsDTO.setAmount(subJson.getInt("tadaAdvanceAmount"));
				finDetailsDTO.setAccountentSign(subJson.getString("accOfficer"));
				finDetailsDTO.setSanctionNo(subJson.getString("sanctionNo"));
				finDetailsDTO.setBillNo(subJson.getString("billNo"));
				finDetailsDTO.setCreatedBy(tadaRPB.getSfid());
				finDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				finDetailsDTO.setLastModifiedBy(tadaRPB.getSfid());
				finDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				finDetailsDTO.setStatus(1);
				finDetailsDTO.setReferenceID(empClaimDetailsDTO.getId());
				
				if(CPSUtils.compareStrings(tadaRPB.getType(),"advance")){                          //here i added new functionality of amount editable in 
					Query qry=session.createSQLQuery("update tada_td_adv_request_details set advance_amount_aft_res =? where ref_request_id=?");
					qry.setFloat(0,subJson.getInt("tadaAdvanceAmount")).setInteger(1,subJson.getInt("requestId")).executeUpdate();
				}
				
				
				if(session.createSQLQuery("select fd.amount amount from finance_details fd,emp_claim_details ecd where fd.reference_id=ecd.id and ecd.ref_request_id="+empClaimDetailsDTO.getRefRequestId()+"").list().size()==0){
					session.save(finDetailsDTO);
					session.flush();
				} else{
					finDetailsDTO.setId(finDetailsDTO.getId());
					
					session.saveOrUpdate(finDetailsDTO);
					session.flush();
				}
				
			}
			tadaRPB.setResult("success");          // This is add for  success message 
		}catch(Exception e){
			throw e;
		}
		hibernateUtils.closeSession();
		return tadaRPB;
	}
	public TadaRequestBean saveCdaAmount(TadaRequestBean tadaRPB)throws Exception{
		Session session=null;
		CDADetailsDTO cdaDetailsDTO=null;
		FinanceDetailsDTO finDetailsDTO=null;
		EmployeeClaimDetailsDTO empClaimDetailsDTO=null;
		try{
			session = hibernateUtils.getSession();
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			String type=tadaRPB.getType();
			if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaReimbursement")||CPSUtils.compareStrings(tadaRPB.getType(),"cdaSettlement")||CPSUtils.compareStrings(tadaRPB.getType(),"cdaAdvance")){
				if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaReimbursement")){
					 type="reimbursement";
				}
		                      
				if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaSettlement")){
					type="settlement";	
				}
				if(CPSUtils.compareStrings(tadaRPB.getType(),"cdaAdvance")){
					type="advance";
				}
					
			}
		
			for(int i=0;i<mainJson.length();i++){
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				empClaimDetailsDTO = new EmployeeClaimDetailsDTO();
				empClaimDetailsDTO = (EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", subJson.getInt("requestId"))).add(Expression.eq("requestType", type)).add(Expression.eq("workFlowStatus", 8)).uniqueResult();
				finDetailsDTO = (FinanceDetailsDTO) session.createCriteria(FinanceDetailsDTO.class).add(Expression.eq("referenceID", empClaimDetailsDTO.getId())).uniqueResult();
				
				
				cdaDetailsDTO = (CDADetailsDTO) session.createCriteria(CDADetailsDTO.class).add(Expression.eq("referenceID", finDetailsDTO.getId())).uniqueResult();
				if(cdaDetailsDTO==null){
					cdaDetailsDTO = new CDADetailsDTO();
				}
				if(CPSUtils.compareStrings(tadaRPB.getType(),"advance")){
					if(CPSUtils.compareStrings(subJson.getString("issueAuthority"), "asl")){        //This new condition for cda or asl
						cdaDetailsDTO.setCdaAmount(subJson.getInt("tadaAdvanceAmount"));
						cdaDetailsDTO.setDvDate(CPSUtils.getCurrentDateWithTime());
						cdaDetailsDTO.setDvNumber(subJson.getString("sanctionNo"));
					 }
					
				}else{ 
					if(!CPSUtils.compareStrings(subJson.getString("cdaAmount"), ""))
						cdaDetailsDTO.setCdaAmount(subJson.getInt("cdaAmount"));
						if(!CPSUtils.compareStrings(subJson.getString("dvNo"), ""))
			 			cdaDetailsDTO.setDvNumber(subJson.getString("dvNo"));
						if(!CPSUtils.compareStrings(subJson.getString("dvDate"), ""))
						cdaDetailsDTO.setDvDate(CPSUtils.convertStringToDate(subJson.getString("dvDate")));
				}
				
				cdaDetailsDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				cdaDetailsDTO.setCreatedBy(tadaRPB.getSfid());
				cdaDetailsDTO.setLastModifiedBy(tadaRPB.getSfid());
				cdaDetailsDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				
				if(!CPSUtils.isNullOrEmpty(finDetailsDTO)){
				cdaDetailsDTO.setReferenceID(finDetailsDTO.getId());
				}
				if(session.createSQLQuery("select cd.cda_amount cdaAmount from cda_details cd,emp_claim_details ecd,finance_details fd where cd.reference_id=fd.id And fd.reference_id = ecd.id  and  ecd.ref_request_id="+empClaimDetailsDTO.getRefRequestId()+"").list().size()==0){
					session.save(cdaDetailsDTO);
					session.flush();
				}else{
					cdaDetailsDTO.setId(cdaDetailsDTO.getId());
					
					session.saveOrUpdate(cdaDetailsDTO);
					session.flush();
				}
				
				tadaRPB.setMessage(CPSConstants.SUCCESS);
			}
		}catch(Exception e){
			throw e;
		}
		finally{
			session.flush();
			session.clear();
		}
		hibernateUtils.closeSession();
		return tadaRPB;
	}
	public TadaRequestBean updateClosedStatus(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			String type=tadaRequestBean.getType();
			if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaReimbursement")||CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaSettlement")){
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaReimbursement")){
					 type="reimbursement";
				}
		                      
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaSettlement")){
					type="settlement";	
				}
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaAdvance")){
					type="advance";
				}
					
			}
			JSONObject mainJson = new JSONObject(tadaRequestBean.getJsonValue());
			for(int i=0;i<mainJson.length();i++){
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				if((!CPSUtils.compareStrings(subJson.getString("cdaAmount"), "")) && (CPSUtils.compareStrings(CPSConstants.SETTLEMENT, type) || CPSUtils.compareStrings(CPSConstants.REIMBURSEMENT, type))){
					session.createQuery("update EmployeeClaimDetailsDTO set workFlowStatus=60 where refRequestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
				}
				
			}
			
		} catch (Exception e) {
			throw e;
		}
		return tadaRequestBean;
	}
	public TadaRequestBean updateFlagDetails(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			String type=tadaRequestBean.getType();
			if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaReimbursement")||CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaSettlement")||CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaAdvance")){
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaReimbursement")){
					 type="reimbursement";
				}
		                      
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaSettlement")){
					type="settlement";	
				}
				if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaAdvance")){
					type="advance";
				}
				
					
			}
			JSONObject mainJson = new JSONObject(tadaRequestBean.getJsonValue());
			for(int i=0;i<mainJson.length();i++){
				JSONObject subJson=(JSONObject)mainJson.get(String.valueOf(i));
				/*if((!CPSUtils.compareStrings(subJson.getString("cdaAmount"), "")) && (CPSUtils.compareStrings(CPSConstants.ADVANCE, tadaRequestBean.getType()) || CPSUtils.compareStrings(CPSConstants.SETTLEMENT, tadaRequestBean.getType()) || CPSUtils.compareStrings(CPSConstants.REIMBURSEMENT, tadaRequestBean.getType()))){
					session.createQuery("update TadaApprovalRequestDTO set settFlag='C' where requestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
					hibernateUtils.closeSession();
					session=hibernateUtils.getSession();
					session.createQuery("update TadaApprovalRequestDTO set reimFlag='C' where requestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
					hibernateUtils.closeSession();
					session=hibernateUtils.getSession();
					session.createQuery("update TadaApprovalRequestDTO set advanceFlag='C' where requestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
				}*/
				if(CPSUtils.compareStrings(CPSConstants.ADVANCE, type)){                                                  //These condition has been change into nested conditions
					if(CPSUtils.compareStrings(subJson.getString("issueAuthority"), "asl")){
						session.createQuery("update TadaApprovalRequestDTO set advanceFlag='C' where requestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
			}else if(CPSUtils.compareStrings(tadaRequestBean.getType(),"cdaAdvance")){
				if(!CPSUtils.compareStrings(subJson.getString("cdaAmount"), "")){
				session.createQuery("update TadaApprovalRequestDTO set advanceFlag='C' where requestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
				}  
				}	
					}else if(CPSUtils.compareStrings(CPSConstants.SETTLEMENT, type)){
					if(!CPSUtils.compareStrings(subJson.getString("cdaAmount"), "")){
						session.createQuery("update TadaApprovalRequestDTO set settFlag='C' where requestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
			
					}
				} else if(CPSUtils.compareStrings(CPSConstants.REIMBURSEMENT, type)){
					if(!CPSUtils.compareStrings(subJson.getString("cdaAmount"), "")){
					session.createQuery("update TadaApprovalRequestDTO set reimFlag='C' where requestId=?").setInteger(0, subJson.getInt("requestId")).executeUpdate();
				}
					}
				
			}
			tadaRequestBean.setResult(CPSConstants.SUCCESS);
		
			
			
		} catch (Exception e) {
			throw e;
		}
		return tadaRequestBean;
	}
	/*public String submitTdDaNewDetails(TadaRequestProcessBean tadaRPB)throws Exception {
		Session session=null;
		String message=null;
		session=hibernateUtils.getSession();
		try{
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			JSONObject daNewDetailsJson=(JSONObject)mainJson.get("daNewDetails");
			for (int i = 3; i < daNewDetailsJson.length(); i++) {
				JSONObject valueJson=(JSONObject)daNewDetailsJson.get(String.valueOf(i));
				TadaTdDaNewDetailsDTO tadaTdDaNewDetailsDTO =new TadaTdDaNewDetailsDTO();
				tadaTdDaNewDetailsDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdDaNewDetailsDTO.setDaNewDate(CPSUtils.convertStringToDate(valueJson.get("date").toString()));
				tadaTdDaNewDetailsDTO.setFoodAmount(valueJson.getInt("food"));
				tadaTdDaNewDetailsDTO.setAccommodationAmount(valueJson.getInt("accommodation"));
				tadaTdDaNewDetailsDTO.setTotalAmount(valueJson.getInt("amount"));
				tadaTdDaNewDetailsDTO.setType(tadaRPB.getType());
				tadaTdDaNewDetailsDTO.setAmountAftRestriction(0);
				session.save(tadaTdDaNewDetailsDTO);
				message=CPSConstants.SUCCESS;
			}
		} catch(Exception e){
			throw e;
		}
		return message;
	}
	public String submitTdRMAKmDetails(TadaRequestProcessBean tadaRPB)throws Exception {
		Session session=null;
		String message=null;
		session=hibernateUtils.getSession();
		try{
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			JSONObject daNewRMAKmJson=(JSONObject)mainJson.get("daNewRMAKmDetails");
			for(int i=3;i<daNewRMAKmJson.length();i++){
				JSONObject valueJson=(JSONObject)daNewRMAKmJson.get(String.valueOf(i));
				TadaTdRMAKmDTO tadaTdRMAKmDTO=new TadaTdRMAKmDTO();
				tadaTdRMAKmDTO.setDateRMAKm(CPSUtils.convertStringToDate(valueJson.get("dateRMAKm").toString()));
				tadaTdRMAKmDTO.setFromPlace(valueJson.getString("fromPlace"));
				tadaTdRMAKmDTO.setToPlace(valueJson.getString("toPlace"));
				tadaTdRMAKmDTO.setTravelBy(valueJson.getString("travelBy"));
				tadaTdRMAKmDTO.setDistance(valueJson.getInt("distance"));
				tadaTdRMAKmDTO.setAmountPerKm(valueJson.getInt("amountPerKm"));
				tadaTdRMAKmDTO.setTotalRMAKmAmount(valueJson.getInt("totalRMAKmAmount"));
				tadaTdRMAKmDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdRMAKmDTO.setType(tadaRPB.getType());
				tadaTdRMAKmDTO.setAmountAftRestriction(0);
				Integer x=(Integer)session.save(tadaTdRMAKmDTO);
				message=CPSConstants.SUCCESS;
			}
		} catch(Exception e){
			throw e;
		}
		return message;
	}
	public String submitTdJourneyDetails(TadaRequestProcessBean tadaRPB)throws Exception {
		Session session=null;
		String message=null;
		session=hibernateUtils.getSession();
		try{
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			JSONObject journeyJson=(JSONObject)mainJson.get("journeyDetails");
			for(int i=3;i<journeyJson.length();i++){
				JSONObject valueJson=(JSONObject)journeyJson.get(String.valueOf(i));
				TadaTdJourneyDTO tadaTdJourneyDTO=new TadaTdJourneyDTO();
				BeanUtils.copyProperties(valueJson, tadaTdJourneyDTO);
				tadaTdJourneyDTO.setJourneyDate(CPSUtils.convertStringToDate(valueJson.get("journeyDate").toString()));
				tadaTdJourneyDTO.setStartTime(valueJson.getString("startTime"));
				tadaTdJourneyDTO.setStartStation(valueJson.getString("startStation"));
				tadaTdJourneyDTO.setJourneyEndDate(CPSUtils.convertStringToDate(valueJson.get("journeyEndDate").toString()));
				tadaTdJourneyDTO.setEndTime(valueJson.getString("endTime"));
				tadaTdJourneyDTO.setEndStation(valueJson.getString("endStation"));
				tadaTdJourneyDTO.setDistanceJourney(valueJson.getInt("distanceJourney"));
				tadaTdJourneyDTO.setModeOfTravel(valueJson.getString("modeOfTravel"));
				tadaTdJourneyDTO.setTicketFare(valueJson.getInt("ticketFare"));
				tadaTdJourneyDTO.setTicketNo(valueJson.getString("ticketNo"));
				tadaTdJourneyDTO.setTotalJourneyAmount(valueJson.getInt("totalJourneyAmount"));
				tadaTdJourneyDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdJourneyDTO.setType(tadaRPB.getType());
				tadaTdJourneyDTO.setAmountAftRestriction(0);
				session.save(tadaTdJourneyDTO);
				message=CPSConstants.SUCCESS;
			}
		} catch(Exception e){
			throw e;
		}
		return message;
	}*/
	public String submitTdReqJourneyDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		Session session=null;
		String message=null;
		session=hibernateUtils.getSession();
		TravelTypeDTO travelTypeDTO=null;
		TaEntitleTypeDTO taEntitleTypeDTO=null;
		try{
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			JSONObject reqJourneyDetails=(JSONObject)mainJson.get("reqJourneyDetails");
			for (int i = 0; i < reqJourneyDetails.length(); i++) {
				JSONObject valueJson=(JSONObject)reqJourneyDetails.get(String.valueOf(i));
				TadaTdReqJourneyDTO tadaTdReqJourneyDTO=new TadaTdReqJourneyDTO();
				if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDBUILDUP) || CPSUtils.compareStrings(tadaRPB.getRequestType(),CPSConstants.TADATDPROJECT)){
					tadaTdReqJourneyDTO.setReferenceId(Integer.parseInt(tadaRPB.getRequestId()));
					tadaTdReqJourneyDTO.setDepartureDate(CPSUtils.convertStringToDate(valueJson.get("departueDate").toString()));
					tadaTdReqJourneyDTO.setArrivalDate(CPSUtils.convertStringToDate(valueJson.get("arrivalDate").toString()));
					tadaTdReqJourneyDTO.setFromPlace(valueJson.getString("fromPlace"));
					tadaTdReqJourneyDTO.setToPlace(valueJson.getString("toPlace"));
					tadaTdReqJourneyDTO.setNoOfDays(valueJson.getInt("days"));
					travelTypeDTO = (TravelTypeDTO)session.createCriteria(TravelTypeDTO.class).add(Expression.eq("id", valueJson.getInt("modeOfConveyance"))).uniqueResult();
					tadaTdReqJourneyDTO.setConveyanceMode(travelTypeDTO.getTravelType());
					taEntitleTypeDTO = (TaEntitleTypeDTO)session.createCriteria(TaEntitleTypeDTO.class).add(Expression.eq("id", valueJson.getInt("classOfEntitlement"))).uniqueResult();
					tadaTdReqJourneyDTO.setClassOfEntitlement(taEntitleTypeDTO.getEntitleClass());
					tadaTdReqJourneyDTO.setTatkalQuota(valueJson.getString("tatkalQuota"));
//					if(!CPSUtils.isNullOrEmpty(tadaRPB.getAmendmentReqId())){
//						TadaTdReqJourneyDTO tadaTdReqJourneyDTO2=(TadaTdReqJourneyDTO)session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", tadaRPB.getAmendmentReqId())).list();
//					}
				}else if(CPSUtils.compareStrings(tadaRPB.getRequestType(), CPSConstants.TADATDADVANCES)){
					tadaTdReqJourneyDTO=(TadaTdReqJourneyDTO)session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("id", valueJson.getInt("id"))).uniqueResult();
					tadaTdReqJourneyDTO.setTicketFare(Float.parseFloat(valueJson.getString("ticketFare")));
				}
				
				session.saveOrUpdate(tadaTdReqJourneyDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	private String submitTdClaimDetials(TadaRequestProcessBean tadaRPB)throws Exception{
		Session session=null;
		String message=null;
		session=hibernateUtils.getSession();
		String refRequestId=null;
		try{
			EmployeeClaimDetailsDTO empClaimDetailsDTO=new EmployeeClaimDetailsDTO();
			if(CPSUtils.compareStrings(CPSConstants.TADA_TD_SETTLEMENT, tadaRPB.getAmendmentType()) || CPSUtils.compareStrings(CPSConstants.TADA_TD_REIMBURSEMENT, tadaRPB.getAmendmentType())){
				empClaimDetailsDTO =(EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRPB.getReferenceRequestID()))).uniqueResult();
				empClaimDetailsDTO.setRefRequestId(empClaimDetailsDTO.getRefRequestId());
			} else {
				refRequestId=tadaRPB.getReferenceRequestID();
				for(int i=0;i<=i;i++){
					TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", refRequestId)).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
						if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO.getAmmendementId())){
							refRequestId=tadaApprovalRequestDTO.getAmmendementId();
						}else{
							break;
						}
					}else
						break;
				}
				if(!CPSUtils.isNullOrEmpty(refRequestId)){
					empClaimDetailsDTO.setRefRequestId(Integer.parseInt(refRequestId));
				}else{
					empClaimDetailsDTO.setRefRequestId(Integer.parseInt(tadaRPB.getReferenceRequestID()));
				}
			}
			empClaimDetailsDTO.setModuleId(7);
			empClaimDetailsDTO.setFundId(0);
			empClaimDetailsDTO.setRequestTypeID(Integer.parseInt(tadaRPB.getRequestTypeID()));
			empClaimDetailsDTO.setRequestID(Integer.parseInt(tadaRPB.getRequestId()));
			empClaimDetailsDTO.setAmountClaimed(tadaRPB.getClaimAmount());
			empClaimDetailsDTO.setStatus(1);
			empClaimDetailsDTO.setWorkFlowStatus(2);
			empClaimDetailsDTO.setAppliedBy(tadaRPB.getSfid());
			empClaimDetailsDTO.setRequestType(tadaRPB.getInnerRequestType());
			empClaimDetailsDTO.setAppliedDate(CPSUtils.convertStringToDate(CPSUtils.getCurrentDate()));
			empClaimDetailsDTO.setIpAddress(tadaRPB.getIpAddress());
			session.save(empClaimDetailsDTO);
			message=CPSConstants.SUCCESS;
			session.flush();
		}catch(Exception e){
			message=CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
	private String submitTdSettDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		Session session=null;
		String message=null;
		session=hibernateUtils.getSession();
		try{
			TadaTdSettlementDTO tadaTdSettlementDTO=new TadaTdSettlementDTO();
			EmployeeClaimDetailsDTO empClaimDetailsDTO=new EmployeeClaimDetailsDTO();
			if(CPSUtils.compareStrings(CPSConstants.TADA_TD_SETTLEMENT, tadaRPB.getAmendmentType()) || CPSUtils.compareStrings(CPSConstants.TADA_TD_REIMBURSEMENT, tadaRPB.getAmendmentType())){
				empClaimDetailsDTO =(EmployeeClaimDetailsDTO) session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRPB.getReferenceRequestID()))).uniqueResult();
				tadaTdSettlementDTO.setRefRequestId(empClaimDetailsDTO.getRefRequestId());
			} else {
				tadaTdSettlementDTO.setRefRequestId(Integer.parseInt(tadaRPB.getReferenceRequestID()));
			}
			tadaTdSettlementDTO.setRequestID(Integer.parseInt(tadaRPB.getRequestId()));
			tadaTdSettlementDTO.setAmountClaimed(tadaRPB.getClaimAmount());
			tadaTdSettlementDTO.setAppliedBy(tadaRPB.getSfid());
			tadaTdSettlementDTO.setRequestType(tadaRPB.getInnerRequestType());
			tadaTdSettlementDTO.setUserRemarks(tadaRPB.getUserRemarks());
			session.save(tadaTdSettlementDTO);
			message=CPSConstants.SUCCESS;
			session.flush();
		}catch(Exception e){
			message=CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
	public String updateTdReimDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		session=hibernateUtils.getSession();
		try{
			if(CPSUtils.compareStrings(tadaRPB.getAmendmentType(), CPSConstants.TADA_TD_REIMBURSEMENT)){
				session.createSQLQuery("update TADA_TD_REQUEST_DETAILS set REIM_FLAG='P' where REQUEST_ID=(select REF_REQUEST_ID from EMP_CLAIM_DETAILS where REQUEST_ID=?)").setInteger(0, Integer.parseInt(tadaRPB.getReferenceRequestID())).executeUpdate();
			} else{
				session.createQuery("update TadaApprovalRequestDTO set reimFlag='P' where requestId=?").setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
			}
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String updateTdSettDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		session=hibernateUtils.getSession();
		try{
			if(CPSUtils.compareStrings(tadaRPB.getAmendmentType(), CPSConstants.TADA_TD_REIMBURSEMENT)){
				session.createSQLQuery("update TADA_TD_REQUEST_DETAILS set SETT_FLAG='P' where REQUEST_ID=(select REF_REQUEST_ID from EMP_CLAIM_DETAILS where REQUEST_ID=?)").setInteger(0, Integer.parseInt(tadaRPB.getReferenceRequestID())).executeUpdate();
			} else{
				//session.createQuery("update TadaApprovalRequestDTO set settFlag='P' where requestId=?").setString(0, tadaRPB.getReferenceRequestID()).executeUpdate();
				for(int i=0;i<=i;i++){
					TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("requestId", tadaRPB.getReferenceRequestID())).uniqueResult();
					if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO.getAmmendementId())){
						tadaRPB.setReferenceRequestID(tadaApprovalRequestDTO.getAmmendementId());
					}else{
						session.createQuery("update TadaApprovalRequestDTO set settFlag='P' where requestId=?").setString(0, tadaApprovalRequestDTO.getRequestId()).executeUpdate();
						break;
					}
				}
			}
			message=CPSConstants.SUCCESS;
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewDetailsDTO> getTadaTdDaNewDetails(WorkFlowMappingBean workflowMap) throws Exception {
		List<TadaTdDaNewDetailsDTO> tdDaNewDetailsList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			tdDaNewDetailsList = session.createCriteria(TadaTdDaNewDetailsDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
			session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		} catch (Exception e) {
			throw e;
		} 
		return tdDaNewDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewAccDetailsDTO> getTadaTdDaNewAccDetails(WorkFlowMappingBean workflowMap) throws Exception {
		List<TadaTdDaNewAccDetailsDTO> tdDaNewAccDetailsList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			tdDaNewAccDetailsList = session.createCriteria(TadaTdDaNewAccDetailsDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).addOrder(Order.asc("fromDate")).list();
			session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		} catch (Exception e) {
			throw e;
		} 
		return tdDaNewAccDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewFoodDetailsDTO> getTadaTdDaNewFoodDetails(WorkFlowMappingBean workflowMap) throws Exception {
		List<TadaTdDaNewFoodDetailsDTO> tdDaNewFoodDetailsList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			tdDaNewFoodDetailsList = session.createCriteria(TadaTdDaNewFoodDetailsDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
			session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		} catch (Exception e) {
			throw e;
		} 
		return tdDaNewFoodDetailsList;
	}
	public TadaTdTotalFoodClaimDTO getTdFoodDayTotalDetails(WorkFlowMappingBean workflowMap)throws Exception {
		TadaTdTotalFoodClaimDTO tdFoodDayTotalDTO = null;
		Session session=null;
		try {
			session = hibernateUtils.getSession();
			tdFoodDayTotalDTO=(TadaTdTotalFoodClaimDTO)session.createCriteria(TadaTdTotalFoodClaimDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return tdFoodDayTotalDTO;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaOldDetailsDTO> getTadaTdDaOldDetails(WorkFlowMappingBean workflowMap) throws Exception {
		List<TadaTdDaOldDetailsDTO> tdDaOldDetailsList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			tdDaOldDetailsList = session.createCriteria(TadaTdDaOldDetailsDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(workflowMap.getRequestId()))).list();
			session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		} catch (Exception e) {
			throw e;
		} 
		return tdDaOldDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdRMAKmDTO> getTadaTdRMAKmDetails(WorkFlowMappingBean workflowMap) throws Exception{
		List<TadaTdRMAKmDTO> tdRMAKmList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdRMAKmList = session.createCriteria(TadaTdRMAKmDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		}catch(Exception e){
			throw e;
		}
		return tdRMAKmList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdRMADailyDTO> getTadaTdRMADailyDetails(WorkFlowMappingBean workflowMap) throws Exception{
		List<TadaTdRMADailyDTO> tdRMADailyList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdRMADailyList = session.createCriteria(TadaTdRMADailyDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		}catch(Exception e){
			throw e;
		}
		return tdRMADailyList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdRMADayDTO> getTadaTdRMADayDetails(WorkFlowMappingBean workflowMap) throws Exception{
		List<TadaTdRMADayDTO> tdRMADayList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdRMADayList = session.createCriteria(TadaTdRMADayDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		}catch(Exception e){
			throw e;
		}
		return tdRMADayList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdLocalRMADetailsDTO> getTadaTdRMALocalDetails(WorkFlowMappingBean workflowMap) throws Exception{
		List<TadaTdLocalRMADetailsDTO> tdRMALocalList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdRMALocalList = session.createCriteria(TadaTdLocalRMADetailsDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).list();
		}catch(Exception e){
			throw e;
		}
		return tdRMALocalList;
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<TadaTdJourneyDTO> getTadaTdJourneyDetails(WorkFlowMappingBean workflowMap) throws Exception{
		List<TadaTdJourneyDTO> tdJourneyList = null;
		
		Order order = null;
		
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdJourneyList = session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", workflowMap.getRequestId())).addOrder(order.asc("id")).list();
		   
		}catch(Exception e){
			throw e;
		}
		return tdJourneyList;
	}
	@SuppressWarnings("unchecked")
	public List<EmployeeClaimDetailsDTO> getEmpClaimDetails(WorkFlowMappingBean workflowMap) throws Exception{
		List<EmployeeClaimDetailsDTO> empClaimDetailsList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			empClaimDetailsList = session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("requestID", Integer.parseInt(workflowMap.getRequestId()))).list();
		}catch(Exception e){
			throw e;
		}
		return empClaimDetailsList;
	}
	public CDADetailsDTO getCdaDetails(WorkFlowMappingBean workflowMap) throws Exception{
		CDADetailsDTO cdaDetailsDTO = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			int requestId=Integer.parseInt(workflowMap.getRequestId());
			cdaDetailsDTO = (CDADetailsDTO)session.createSQLQuery("select cd.cda_amount as cdaAmount from emp_claim_details ecd,cda_details cd where ecd.request_id=(select request_id from emp_claim_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+" and request_type_id=48) and request_type_id=47 and workflow_status in(8,60)) and cd.reference_id=(select fd.id from emp_claim_details ecd1,finance_details fd  where ecd1.request_id=(select request_id from emp_claim_details where ref_request_id=(select ref_request_id from emp_claim_details where request_id="+requestId+" and request_type_id=48) and   request_type_id=47 and  workflow_status in(8,60)) And fd.reference_id = ecd1.id)").addScalar("cdaAmount", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(CDADetailsDTO.class)).uniqueResult();
		}catch(Exception e){
			throw e;
		}
		return cdaDetailsDTO;
	}
	@SuppressWarnings("unchecked")
	public List<LtcPenalMasterDTO> getPenalInterest()throws Exception{
		Session session=null;
		List<LtcPenalMasterDTO> list=null;
		try{
			session=hibernateUtils.getSession();
			list=session.createCriteria(LtcPenalMasterDTO.class).add(Expression.sql(" validity_from=(select max(validity_from) from penal_interest_master)")).list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	public LtcPenalMasterDTO getPenalInterestRate()throws Exception{
		Session session=null;
		LtcPenalMasterDTO ltcPenalMasterDTO=new LtcPenalMasterDTO();
		try{
			session=hibernateUtils.getSession();
			ltcPenalMasterDTO=(LtcPenalMasterDTO)session.createCriteria(LtcPenalMasterDTO.class).add(Expression.sql(" validity_from=(select max(validity_from) from penal_interest_master)")).uniqueResult();
		}catch(Exception e){
			throw e;
		}
		return ltcPenalMasterDTO;
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinAdvDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaApprovalRequestDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
//			sql="select ttrd.request_id requestId,ttrd.sfid sfid,em.name_in_service_book name,ttard.advance_amount tadaAdvanceAmount," +
//					"ttard.advance_amount_aft_res as advanceAmountAftRes,ttard.request_id as referenceRequestID,max(rwh.id) as historyID," +
//					"ttard.da_type as daType ,to_char(ttrd.departure_date,'dd-Mon-yyyy') as departureDateOne,ttrd.authorized_move_id as authorizedMove,ttrd.project_name as projectName " +
//					"from tada_td_request_details ttrd,emp_master em,tada_td_adv_request_details ttard,emp_claim_details ecd," +
//					"request_workflow_history rwh where (ttrd.advance_flag='Y' or ttrd.advance_flag='C') and (ttrd.sett_flag is null or ttrd.sett_flag='N') and ttrd.sfid=em.sfid and ttard.sfid=ttrd.sfid and " +
//					"ecd.request_type_id=47 and ttard.ref_request_id=ecd.ref_request_id and ttard.request_id=ecd.request_id and " +
//					"ttrd.status not in(9,6)and ecd.workflow_status not in(9,6,2,60) and ttard.status not in(9,6,2) and " +
//					"ttrd.request_id=ttard.ref_request_id and ttard.request_id=rwh.request_id group by ttrd.request_id, ttrd.sfid, " +
//					"em.name_in_service_book, ttard.advance_amount, ttard.advance_amount_aft_res, ttard.request_id, ttard.da_type," +
//					"ttrd.departure_date,ttrd.authorized_move_id,ttrd.project_name order by ttard.request_id desc";
			
			sql="select ttrd.request_id requestId,ttrd.sfid sfid,em.name_in_service_book name,ttard.advance_amount tadaAdvanceAmount," +
					"ttard.advance_amount_aft_res as advanceAmountAftRes,ttard.request_id as referenceRequestID,max(rwh.id) as historyID," +
					"ttard.da_type as daType ,to_char(ttrd.departure_date,'dd-Mon-yyyy') as departureDateOne,ttrd.authorized_move_id as authorizedMove,ttrd.project_name as projectName,"
					+ "fd.SANCTION_NUMBER sanctionNo,fd.BILL_NUMBER billNo, fd.ACCOUNTENT_SIGN accountentSign" +
					" from tada_td_request_details ttrd,emp_master em,tada_td_adv_request_details ttard, emp_claim_details ecd LEFT OUTER JOIN finance_details fd ON(ecd.id=fd.reference_id)," +
					"request_workflow_history rwh where (ttrd.advance_flag='Y') and (ttrd.sett_flag is null or ttrd.sett_flag='N') and ttrd.sfid=em.sfid and ttard.sfid=ttrd.sfid and " +
					"ecd.request_type_id=47 and ttard.ref_request_id=ecd.ref_request_id and ttard.request_id=ecd.request_id and " +
					"ttrd.status not in(9,6)and ecd.workflow_status not in(9,6,2,60) and ttard.status not in(9,6,2) and " +
					"ttrd.request_id=ttard.ref_request_id and ttard.request_id=rwh.request_id AND fd.sanction_number IS NULL group by ttrd.request_id, ttrd.sfid, " +
					"em.name_in_service_book, ttard.advance_amount, ttard.advance_amount_aft_res, ttard.request_id, ttard.da_type," +
					"ttrd.departure_date,ttrd.authorized_move_id,ttrd.project_name,fd.SANCTION_NUMBER,fd.BILL_NUMBER,fd.ACCOUNTENT_SIGN order by ttard.request_id desc";
			
			finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING)
					.addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("advanceAmountAftRes", Hibernate.FLOAT).addScalar("referenceRequestID", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("daType", Hibernate.STRING)
					.addScalar("departureDateOne", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			
			tadaRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		return finAdvList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaRequestBean> getAccOffList(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaRequestBean> accOffList=null;
		Session session=null;
		try{
			session=hibernateUtils.getSession();
			accOffList = session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list();
		}catch(Exception e){
			throw e;
		}
		return accOffList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinAdvCompleteDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaApprovalRequestDTO> finAdvCmplList=null;
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			sql="select ecd.ref_request_id requestId, fd.sanction_number sanctionNo,fd.bill_number billNo,fd.accountent_sign accountentSign,cd.dv_no dvNo,to_char(cd.dv_date,'DD-Mon-YYYY')as dvDate,cd.cda_amount cdaAmount from FINANCE_DETAILS fd,CDA_DETAILS cd,EMP_CLAIM_DETAILS ecd where ecd.id=fd.reference_id and fd.id=cd.reference_id and ecd.module_id=7 and ecd.request_type_id=47";
			finAdvCmplList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).addScalar("cdaAmount", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
		}catch(Exception e){
			throw e;
		}
		return finAdvCmplList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinSettlementDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaApprovalRequestDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		List<Integer> reqidlist = new ArrayList<Integer>();
		try{
			session=hibernateUtils.getSession();
		
//			sql="select ecd1.id as id,ttrd.request_id requestId,ecd.request_id as referenceRequestID,ttrd.sfid sfid,em.name_in_service_book name," +
//					"ecd.total_amount tadaSettlementAmount,ttard.advance_amount_aft_res as tadaAdvanceAmount,max(rwh.id) as historyID,ttrd.authorized_move_id as authorizedMove,ttrd.project_name as projectName,fd.sanction_number as sanctionNo from tada_td_request_details ttrd," +
//					"emp_master em,emp_claim_details ecd,emp_claim_details ecd1,tada_td_adv_request_details ttard,request_workflow_history rwh,finance_details fd where " +
//					"ttrd.advance_flag in ('Y','C') and ttrd.sfid=em.sfid and ttrd.request_id=ecd.ref_request_id and ttrd.request_id=ecd1.ref_request_id and " +
//					"ecd.request_type_id=48 and ecd.workflow_status=8 and rwh.request_id=ecd.request_id and ecd.ref_request_id=ecd1.ref_request_id and ecd1.workflow_status=8 " +
//					"and ecd1.request_type_id=47 and ttard.ref_request_id=ecd1.ref_request_id and ttard.status=8 and fd.reference_id=ecd1.id group by ecd1.id, ttrd.request_id, ecd.request_id, " +
//					"ttrd.sfid, em.name_in_service_book, ecd.total_amount, ttard.advance_amount_aft_res,ttrd.authorized_move_id ,ttrd.project_name,fd.sanction_number order by ecd.request_id desc";			
//		
			sql="select ttrd.request_id requestId,ecd.request_id referenceRequestID ,ttrd.sfid sfid,em.name_in_service_book name,ecd.total_amount tadaSettlementAmount,ttard.advance_amount_aft_res as tadaAdvanceAmount, " +
					"max(rwh.id) historyID,ttrd.authorized_move_id as authorizedMove,ttrd.project_name as projectName,fd.SANCTION_NUMBER sanctionNo,fd.BILL_NUMBER billNo,fd.ACCOUNTENT_SIGN accountentSign  from tada_td_request_details ttrd,emp_master em,tada_td_adv_request_details ttard,emp_claim_details ecd LEFT OUTER JOIN finance_details fd "
					+ " ON(ecd.id=fd.reference_id),request_workflow_history rwh where ttrd.SETT_FLAG in ('Y') " +
					"and ttrd.sfid=em.sfid and ttrd.request_id=ecd.ref_request_id and ecd.request_type_id=48 and ecd.workflow_status=8 and rwh.request_id=ecd.request_id AND ttard.ref_request_id=ecd.ref_request_id AND ttard.status=8 AND fd.sanction_number IS NULL " +
					"group by ttrd.request_id, ecd.request_id, ttrd.sfid, em.name_in_service_book, ecd.total_amount, ttard.advance_amount_aft_res,ttrd.authorized_move_id,ttrd.project_name,"
					+ "fd.SANCTION_NUMBER, fd.BILL_NUMBER, fd.ACCOUNTENT_SIGN order by ecd.request_id desc";			
			 
//			finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("tadaSettlementAmount",Hibernate.INTEGER).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("tadaSettlementAmount", Hibernate.INTEGER).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).
					addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			
			   for (TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList) {
				reqidlist.add(Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID()));		
			}
				List<TadaTdSettlementDTO> tadaTdSettlementDTO=  new  ArrayList<TadaTdSettlementDTO>();

				List<TadaTdSettlementDTO> tadaTdSettlementDTO3=  new  ArrayList<TadaTdSettlementDTO>();
				//tadaTdSettlementDTO= session.createCriteria(TadaTdSettlementDTO.class).add(Expression.in("requestID", reqidlist)).list();
				//This code is used to above 1000 records in tadasettlement:: added by rakesh
				
				if(reqidlist.size()>1000){
				tadaTdSettlementDTO= session.createCriteria(TadaTdSettlementDTO.class).add(Restrictions.in("requestID", reqidlist.subList(0, 999))).list();
				if(reqidlist.size()>999){
					reqidlist.subList(0, 999).clear();
					tadaRequestBean.setThoousnd("yes");
				tadaTdSettlementDTO3= session.createCriteria(TadaTdSettlementDTO.class).add(Restrictions.in("requestID", reqidlist)).list();
				}
				else{
					tadaRequestBean.setThoousnd("no");
				}
				
				/* Criteria tadaTdSettlementDTO100= session.createCriteria(TadaTdSettlementDTO.class).add(Restrictions.in("requestID", reqidlist));
	           				
				Disjunction or = Restrictions.disjunction();
				if(reqidlist.size()>1000){
					while(reqidlist.size()>1000){
					List<?> subList = reqidlist.subList(0, 1000);
					or.add(Restrictions.in("requestID", subList));
					reqidlist.subList(0, 1000).clear();
				
				}}
				
				tadaTdSettlementDTO100.add(Restrictions.in("requestID", reqidlist));
				tadaTdSettlementDTO100.add(or);*/
				
				//if(reqidlist.size()<=999){
				for(TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList){
				for(TadaTdSettlementDTO tadaTdSettlementDTO1 : tadaTdSettlementDTO){
					if(tadaTdSettlementDTO1.getRequestID()==Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID())){
				tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO1.getAmountClaimedAftRes());
					
//				break;
					}
				}
			}
				//}
				
				if(tadaRequestBean.getThoousnd().equals("yes")){
				
				for(TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList){
					for(TadaTdSettlementDTO tadaTdSettlementDTO4 : tadaTdSettlementDTO3){
						if(tadaTdSettlementDTO4.getRequestID()==Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID())){
					tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO4.getAmountClaimedAftRes());
						
//					break;
						}
					}
				}
				}
				
		}
				else{
					tadaTdSettlementDTO= session.createCriteria(TadaTdSettlementDTO.class).add(Expression.in("requestID", reqidlist)).list();
					
					for(TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList){
						for(TadaTdSettlementDTO tadaTdSettlementDTO1 : tadaTdSettlementDTO){
							if(tadaTdSettlementDTO1.getRequestID()==Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID())){
						tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO1.getAmountClaimedAftRes());
							
//						break;
							}
						}
					}
					
					
				}
		//	tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO.getAmountClaimedAftRes());
			
			tadaRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		return finAdvList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinSettlementCompleteDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaApprovalRequestDTO> finAdvCmplList=null;
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			sql="select ecd.ref_request_id requestId, fd.sanction_number sanctionNo,fd.bill_number billNo,fd.accountent_sign accountentSign,cd.dv_no dvNo,to_char(cd.dv_date,'DD-Mon-YYYY')as dvDate,cd.cda_amount cdaAmount from FINANCE_DETAILS fd,CDA_DETAILS cd,EMP_CLAIM_DETAILS ecd where ecd.id=fd.reference_id and fd.id=cd.reference_id and ecd.module_id=7 and ecd.request_type_id=48";
			finAdvCmplList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).addScalar("cdaAmount", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
		}catch(Exception e){
			throw e;
		}
		return finAdvCmplList;
	}
	
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinReimDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaApprovalRequestDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		List<Integer> sfidList = new ArrayList<Integer>();
		List<TadaTdSettlementDTO> tadaTdSettlementDTO=new ArrayList<TadaTdSettlementDTO>();
		try{
			session=hibernateUtils.getSession();
			sql="select ttrd.request_id requestId,ecd.request_id referenceRequestID ,ttrd.sfid sfid,em.name_in_service_book name,ecd.total_amount tadaAdvanceAmount," +
					"max(rwh.id) historyID,ttrd.authorized_move_id as authorizedMove,ttrd.project_name as projectName,fd.SANCTION_NUMBER sanctionNo,fd.BILL_NUMBER billNo,fd.ACCOUNTENT_SIGN accountentSign  from tada_td_request_details ttrd,emp_master em,emp_claim_details ecd LEFT OUTER JOIN finance_details fd "
					+ " ON(ecd.id=fd.reference_id),request_workflow_history rwh where ttrd.reim_flag in ('Y') " +
					"and ttrd.sfid=em.sfid and ttrd.request_id=ecd.ref_request_id and ecd.request_type_id=49 and ecd.workflow_status=8 and rwh.request_id=ecd.request_id AND fd.sanction_number IS NULL " +
					"group by ttrd.request_id, ecd.request_id, ttrd.sfid, em.name_in_service_book, ecd.total_amount,ttrd.authorized_move_id,ttrd.project_name,"
					+ "fd.SANCTION_NUMBER, fd.BILL_NUMBER, fd.ACCOUNTENT_SIGN order by ecd.request_id desc";			
			finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).
					addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			for (TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList) {
				
				sfidList.add(Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID()));
			}
			
				tadaTdSettlementDTO= session.createCriteria(TadaTdSettlementDTO.class).add(Expression.in("requestID",sfidList )).list();
				
				for (TadaTdSettlementDTO tadaTdSettlementDTO1 : tadaTdSettlementDTO) {
					for (TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList) {
						if(tadaTdSettlementDTO1.getRequestID()==Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID())){
							tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO1.getAmountClaimedAftRes());	
						}
			
				}
			}
			tadaRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		System.out.println("ddrdgdf"); 
		return finAdvList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinReimCompleteDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaApprovalRequestDTO> finAdvCmplList=null;
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			sql="select ecd.ref_request_id requestId, fd.sanction_number sanctionNo,fd.bill_number billNo,fd.accountent_sign accountentSign,cd.dv_no dvNo,to_char(cd.dv_date,'DD-Mon-YYYY')as dvDate,cd.cda_amount cdaAmount from FINANCE_DETAILS fd,CDA_DETAILS cd,EMP_CLAIM_DETAILS ecd where ecd.id=fd.reference_id and fd.id=cd.reference_id and ecd.module_id=7 and ecd.request_type_id=49";
			finAdvCmplList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).addScalar("cdaAmount", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
		}catch(Exception e){
			throw e;
		}
		return finAdvCmplList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewDetailsDTO> getTadaTdDaNewDetails(TadaRequestBean tadaRequestBean) throws Exception {
		List<TadaTdDaNewDetailsDTO> tdDaNewDetailsList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			tdDaNewDetailsList = session.createCriteria(TadaTdDaNewDetailsDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list();
			session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list();
		} catch (Exception e) {
			throw e;
		} 
		return tdDaNewDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaOldDetailsDTO> getTadaTdDaOldDetails(TadaRequestBean tadaRequestBean) throws Exception {
		List<TadaTdDaOldDetailsDTO> tdDaOldDetailsList = null;
		Session session = null;
		try {
			session = hibernateUtils.getSession();
			tdDaOldDetailsList = session.createCriteria(TadaTdDaOldDetailsDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list();
			session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list();
		} catch (Exception e) {
			throw e;
		} 
		return tdDaOldDetailsList;
	}
	public String getDaOldType(TadaRequestBean tadaRequestBean)throws Exception {
		Session session=null;
		String sql=null;
		try{
			session=hibernateUtils.getSession();
			sql="select distinct da_type_rate as daTypeRate from tada_td_da_old_details where settlement_request_id=?";
			TadaTdDaOldDetailsDTO tadaTdDaOldDetailsDTO=(TadaTdDaOldDetailsDTO)session.createSQLQuery(sql).addScalar("daTypeRate", Hibernate.STRING).setString(0, tadaRequestBean.getRequestId()).setResultTransformer(Transformers.aliasToBean(TadaTdDaOldDetailsDTO.class)).uniqueResult();
			tadaRequestBean.setDaTypeRate(tadaTdDaOldDetailsDTO.getDaTypeRate());
		} catch(Exception e){
			throw e;
		}
		return tadaRequestBean.getDaTypeRate();
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdRMAKmDTO> getTadaTdRMAKmDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaTdRMAKmDTO> tdRMAKmList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdRMAKmList = session.createCriteria(TadaTdRMAKmDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list();
		}catch(Exception e){
			throw e;
		}
		return tdRMAKmList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdRMADayDTO> getTadaTdRMADayDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaTdRMADayDTO> tdRMADayList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdRMADayList = session.createCriteria(TadaTdRMADayDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list();
		}catch(Exception e){
			throw e;
		}
		return tdRMADayList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdJourneyDTO> getTadaTdJourneyDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaTdJourneyDTO> tdJourneyList = null;
		Session session = null;
		try{
			session = hibernateUtils.getSession();
			tdJourneyList = session.createCriteria(TadaTdJourneyDTO.class).add(Expression.eq("settlementRequestId", tadaRequestBean.getRequestId())).list();
		}catch(Exception e){
			throw e;
		}
		return tdJourneyList;
	}
	@SuppressWarnings("unchecked")
	public List<LocalRMADTO> getLocalRMAList(TadaRequestBean tadaRequestBean) throws Exception{
		Session session=null;
		List<LocalRMADTO> list=null;
		try{
			session=hibernateUtils.getSession();
			list=session.createCriteria(LocalRMADTO.class).add(Expression.eq("status", 1)).list();
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<CityTypeDTO> getToPlaceList(TadaRequestBean tadaRequestBean)throws Exception {
		List<CityTypeDTO> list=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list = session.createCriteria(CityTypeDTO.class).add(Expression.ne("cityName", tadaRequestBean.getFromPlace())).add(Expression.eq("status", 1)).addOrder(Order.asc("cityName")).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String submitTdSettlementDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		Session session=null;
		String message=null;
		session=hibernateUtils.getSession();
		try{
			JSONObject mainJson = new JSONObject(tadaRPB.getJsonValue());
			//JSONObject daNewDetailsJson=(JSONObject)mainJson.get("daNewDetails");
			JSONObject daNewAccDetailsJson=(JSONObject)mainJson.get("daNewAccDetails");
			JSONObject daNewFoodDetailsJson=(JSONObject)mainJson.get("daNewFoodDetails");
			JSONObject daOldDetailsJson=(JSONObject)mainJson.get("daOldDetails");
			JSONObject daNewRMAKmJson=(JSONObject)mainJson.get("daNewRMAKmDetails");
			JSONObject journeyJson=(JSONObject)mainJson.get("journeyDetails");
			JSONObject daNewRMADayJson=(JSONObject)mainJson.get("daNewRMADayDetails");
			JSONObject localRMAJson=(JSONObject)mainJson.get("localRMADetails");
			JSONObject daNewRMADailyJson=(JSONObject)mainJson.get("daNewRMADailyDetails");
			for (int i = 0; i < daNewAccDetailsJson.length(); i++) {
				JSONObject valueJson=(JSONObject)daNewAccDetailsJson.get(String.valueOf(i));
				TadaTdDaNewAccDetailsDTO tadaTdDaNewAccDetailsDTO =new TadaTdDaNewAccDetailsDTO();
				tadaTdDaNewAccDetailsDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdDaNewAccDetailsDTO.setFromDate(CPSUtils.convertStringToDate(valueJson.get("fromDate").toString()));
				//tadaTdDaNewAccDetailsDTO.setInTime(valueJson.getString("inTime"));
				tadaTdDaNewAccDetailsDTO.setToDate(CPSUtils.convertStringToDate(valueJson.get("toDate").toString()));
				//tadaTdDaNewAccDetailsDTO.setOutTime(valueJson.getString("outTime"));
				tadaTdDaNewAccDetailsDTO.setAccAmount(Float.parseFloat(valueJson.getString("accAmount")));
				//tadaTdDaNewAccDetailsDTO.setCalAccAmount(Float.parseFloat(valueJson.getString("calAccAmount")));
				tadaTdDaNewAccDetailsDTO.setClaimedAmount(Float.parseFloat(valueJson.getString("amountClaimed")));
				tadaTdDaNewAccDetailsDTO.setType(tadaRPB.getType());
				
				session.saveOrUpdate(tadaTdDaNewAccDetailsDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
			for (int i = 0; i < daNewFoodDetailsJson.length(); i++) {
				JSONObject valueJson=(JSONObject)daNewFoodDetailsJson.get(String.valueOf(i));
				TadaTdDaNewFoodDetailsDTO tadaTdDaNewFoodDetailsDTO =new TadaTdDaNewFoodDetailsDTO();
				tadaTdDaNewFoodDetailsDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdDaNewFoodDetailsDTO.setFromDate(CPSUtils.convertStringToDate(valueJson.get("fromDate").toString()));
				tadaTdDaNewFoodDetailsDTO.setToDate(CPSUtils.convertStringToDate(valueJson.get("toDate").toString()));
				tadaTdDaNewFoodDetailsDTO.setFoodAmount(Float.parseFloat(valueJson.getString("foodAmount")));
				//tadaTdDaNewFoodDetailsDTO.setCalFoodAmount(Float.parseFloat(valueJson.getString("calFoodAmount")));
				tadaTdDaNewFoodDetailsDTO.setClaimedAmount(Float.parseFloat(valueJson.getString("amountClaimed")));
				tadaTdDaNewFoodDetailsDTO.setType(tadaRPB.getType());
				session.saveOrUpdate(tadaTdDaNewFoodDetailsDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
			for (int i = 0; i < daOldDetailsJson.length(); i++) {
				JSONObject valueJson=(JSONObject)daOldDetailsJson.get(String.valueOf(i));
				TadaTdDaOldDetailsDTO tadaTdDaOldDetailsDTO =new TadaTdDaOldDetailsDTO();
				tadaTdDaOldDetailsDTO.setSettlementRequestId(Integer.parseInt(tadaRPB.getRequestId()));
				tadaTdDaOldDetailsDTO.setDaOldDate(CPSUtils.convertStringToDate(valueJson.get("oldDaDate").toString()));
				tadaTdDaOldDetailsDTO.setJdaDays(Float.parseFloat(valueJson.getString("jdaDays")));
				tadaTdDaOldDetailsDTO.setJdaAmount(Float.parseFloat(valueJson.getString("jdaAmount")));
				tadaTdDaOldDetailsDTO.setTotalJdaAmount(Float.parseFloat(valueJson.getString("totalJdaAmount")));
				tadaTdDaOldDetailsDTO.setSdaDays(Float.parseFloat(valueJson.getString("sdaDays")));
				tadaTdDaOldDetailsDTO.setSdaAmount(Float.parseFloat(valueJson.getString("sdaAmount")));
				tadaTdDaOldDetailsDTO.setTotalSdaAmount(Float.parseFloat(valueJson.getString("totalSdaAmount")));
				tadaTdDaOldDetailsDTO.setDaTypeRate(valueJson.getString("daTypeRate"));
				session.saveOrUpdate(tadaTdDaOldDetailsDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
			for(int i=0;i<daNewRMAKmJson.length();i++){
				JSONObject valueJson=(JSONObject)daNewRMAKmJson.get(String.valueOf(i));
				TadaTdRMAKmDTO tadaTdRMAKmDTO=new TadaTdRMAKmDTO();
				tadaTdRMAKmDTO.setDateRMAKm(CPSUtils.convertStringToDate(valueJson.get("dateRMAKm").toString()));
				tadaTdRMAKmDTO.setFromPlace(valueJson.getString("fromPlace"));
				tadaTdRMAKmDTO.setToPlace(valueJson.getString("toPlace"));
				tadaTdRMAKmDTO.setTravelBy(valueJson.getString("travelBy"));
				tadaTdRMAKmDTO.setDistance(Float.parseFloat(valueJson.getString("distance")));
				tadaTdRMAKmDTO.setAmountPerKm(Float.parseFloat(valueJson.getString("amountPerKm")));
				tadaTdRMAKmDTO.setTotalRMAKmAmount(Float.parseFloat(valueJson.getString("totalRMAKmAmount")));
				tadaTdRMAKmDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdRMAKmDTO.setType(tadaRPB.getType());
				tadaTdRMAKmDTO.setAmountAftRestriction(0);
				session.saveOrUpdate(tadaTdRMAKmDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
			for(int i=0;i<journeyJson.length();i++){
				JSONObject valueJson=(JSONObject)journeyJson.get(String.valueOf(i));
				TadaTdJourneyDTO tadaTdJourneyDTO=new TadaTdJourneyDTO();
				BeanUtils.copyProperties(valueJson, tadaTdJourneyDTO);
				tadaTdJourneyDTO.setJourneyDate(CPSUtils.convertStringToDate(valueJson.get("journeyDate").toString()));
				tadaTdJourneyDTO.setStartTime(valueJson.getString("startTime"));
				tadaTdJourneyDTO.setStartStation(valueJson.getString("startStation"));
				tadaTdJourneyDTO.setJourneyEndDate(CPSUtils.convertStringToDate(valueJson.get("journeyEndDate").toString()));
				tadaTdJourneyDTO.setEndTime(valueJson.getString("endTime"));
				tadaTdJourneyDTO.setEndStation(valueJson.getString("endStation"));
				tadaTdJourneyDTO.setDistanceJourney(Float.parseFloat(valueJson.getString("distanceJourney")));
				tadaTdJourneyDTO.setModeOfTravel(valueJson.getString("modeOfTravel"));
				tadaTdJourneyDTO.setTicketFare(Float.parseFloat(valueJson.getString("ticketFare")));
				tadaTdJourneyDTO.setTicketNo(valueJson.getString("ticketNo"));
				tadaTdJourneyDTO.setTotalJourneyAmount(Float.parseFloat(valueJson.getString("totalJourneyAmount")));
				tadaTdJourneyDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdJourneyDTO.setType(tadaRPB.getType());
				tadaTdJourneyDTO.setAmountAftRestriction(0);
				session.saveOrUpdate(tadaTdJourneyDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
			for(int i=0;i<daNewRMADayJson.length();i++){
				JSONObject valueJson=(JSONObject)daNewRMADayJson.get(String.valueOf(i));
				TadaTdRMADayDTO tadaRMADayDTO=new TadaTdRMADayDTO();
				BeanUtils.copyProperties(valueJson, tadaRMADayDTO);
				tadaRMADayDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaRMADayDTO.setDateRMADay(CPSUtils.convertStringToDate(valueJson.get("dateRMADay").toString()));
				tadaRMADayDTO.setFromPlace(valueJson.getString("fromPlace"));
				tadaRMADayDTO.setToPlace(valueJson.getString("toPlace"));
				tadaRMADayDTO.setAmountPerDay(Float.parseFloat(valueJson.getString("amountPerDay")));
				tadaRMADayDTO.setCalAmountPerDay(Float.parseFloat(valueJson.getString("calAmountPerDay")));
				tadaRMADayDTO.setAmountPerDayAftRes(0);
				tadaRMADayDTO.setTotalRMADayAmount(Float.parseFloat(valueJson.getString("totalRMADayAmount")));
				tadaRMADayDTO.setType(tadaRPB.getType());
				tadaRMADayDTO.setAmountAftRestriction(0);
				session.saveOrUpdate(tadaRMADayDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
			for(int i=0;i<localRMAJson.length();i++){
				JSONObject valueJson=(JSONObject)localRMAJson.get(String.valueOf(i));
				TadaTdLocalRMADetailsDTO tadaTdLocalRMADetailsDTO=new TadaTdLocalRMADetailsDTO();
				BeanUtils.copyProperties(valueJson, tadaTdLocalRMADetailsDTO);
				tadaTdLocalRMADetailsDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdLocalRMADetailsDTO.setLocalRMADate(CPSUtils.convertStringToDate(valueJson.get("dateRMALocal").toString()));
				tadaTdLocalRMADetailsDTO.setLocalFromPlace(valueJson.getString("fromPlaceLocal"));
				tadaTdLocalRMADetailsDTO.setLocalToPlace(valueJson.getString("toPlaceLocal"));
				tadaTdLocalRMADetailsDTO.setLocalCMode(valueJson.getString("localCMode"));
				tadaTdLocalRMADetailsDTO.setLocalDistance(Float.parseFloat(valueJson.getString("localRMADistance")));
				tadaTdLocalRMADetailsDTO.setAmountPerKmLocal(Float.parseFloat(valueJson.getString("localRMAAmtPerKm")));
				tadaTdLocalRMADetailsDTO.setClaimedAmount(Float.parseFloat(valueJson.getString("totalRMALocalAmount")));
				tadaTdLocalRMADetailsDTO.setType(tadaRPB.getType());
				session.saveOrUpdate(tadaTdLocalRMADetailsDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
			for(int i=0;i<daNewRMADailyJson.length();i++){
				JSONObject valueJson=(JSONObject)daNewRMADailyJson.get(String.valueOf(i));
				TadaTdRMADailyDTO tadaTdRMADailyDTO=new TadaTdRMADailyDTO();
				tadaTdRMADailyDTO.setDateRMAKm(CPSUtils.convertStringToDate(valueJson.get("dateRMAKm").toString()));
				tadaTdRMADailyDTO.setFromPlace(valueJson.getString("fromPlace"));
				tadaTdRMADailyDTO.setToPlace(valueJson.getString("toPlace"));
				tadaTdRMADailyDTO.setTravelBy(valueJson.getString("travelBy"));
				tadaTdRMADailyDTO.setDistance(Float.parseFloat(valueJson.getString("distance")));
				tadaTdRMADailyDTO.setAmountPerKm(Float.parseFloat(valueJson.getString("amountPerKm")));
				tadaTdRMADailyDTO.setTotalRMAKmAmount(Float.parseFloat(valueJson.getString("totalRMAKmAmount")));
				tadaTdRMADailyDTO.setSettlementRequestId(tadaRPB.getRequestId());
				tadaTdRMADailyDTO.setType(tadaRPB.getType());
				tadaTdRMADailyDTO.setAmountAftRestriction(0);
				session.saveOrUpdate(tadaTdRMADailyDTO);
				session.flush();
				message=CPSConstants.SUCCESS;
			}
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdReqJourneyDTO> getTdReqJourneyDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdReqJourneyDTO> tdReqJourneyList=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			if(CPSUtils.compareStrings(CPSConstants.TADATDBUILDUP, tadaRequestBean.getRequestType()) || CPSUtils.compareStrings(CPSConstants.TADATDPROJECT, tadaRequestBean.getRequestType())){
				tdReqJourneyList = session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", Integer.parseInt(tadaRequestBean.getRequestId()))).list();
			} else if(CPSUtils.compareStrings(CPSConstants.TADATDADVANCES, tadaRequestBean.getRequestType())){
				tdReqJourneyList = session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", Integer.parseInt(tadaRequestBean.getReferenceRequestID()))).list();
			}
			
		} catch (Exception e) {
			throw e;
		}
		return tdReqJourneyList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdTxnDTO> getTadaTdDetails(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdTxnDTO> list=null;
		Session session=null;
		String qry=null;
		try {
			session=hibernateUtils.getSession();
			qry="select tttd.request_id as requestId,tttd.request_type as requestType,max(rwh.id) as historyID,sm.status as strStatus from tada_td_txn_details tttd," +
					"request_workflow_history rwh,status_master sm where tttd.sfid=? and rwh.request_id=tttd.request_id and rwh.status=sm.id group by tttd.request_id, " +
					"tttd.request_type, sm.status";
			list = session.createSQLQuery(qry).addScalar("requestId", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("strStatus", Hibernate.STRING).setString(0,tadaRequestBean.getSfid()).setResultTransformer(Transformers.aliasToBean(TadaTdTxnDTO.class)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String submitTdLeaveDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			String leaveIds[]=tadaRPB.getLeaveId().split(",");
			for(int i=0;i<leaveIds.length;i++){
				TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO=new TadaTdLeaveDetailsDTO();
				tadaTdLeaveDetailsDTO.setRefRequestId(Integer.parseInt(tadaRPB.getRequestId()));
				tadaTdLeaveDetailsDTO.setLeaveRequestId(Integer.parseInt(leaveIds[i]));
				session.save(tadaTdLeaveDetailsDTO);
			}
			message=CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdTxnDTO> getTdAmendmentDetails(TadaRequestBean tadaRequestBean) throws Exception{
		List<TadaTdTxnDTO> list=null;
		Session session=null;
		String sql1=null;
		try {
			String sfid=tadaRequestBean.getSfid();
			session = hibernateUtils.getSession();
			sql1="select tttd.sfid as sfID,tttd.request_id as requestId,tttd.request_type as requestType,tttd.status as status,max(rwh.id) as historyID," +
					"ttrd.advance_flag as advanceFlag,to_char(min(ttrjd.departure_date),'DD-Mon-YYYY') as journeyDateOne,ttrd.reim_flag as reimFlag,ttrd.sett_flag as settFlag," +
					"ttrd.stay_duration as stayDuration,ttrd.ammendement_id as amendmentId from REQUEST_WORKFLOW_HISTORY rwh,TADA_TD_TXN_DETAILS tttd," +
					"TADA_TD_REQUEST_DETAILS ttrd,TADA_TD_REQ_JOURNEY_DETAILS ttrjd where rwh.request_id=tttd.request_id and tttd.sfid=? and " +
					"ttrd.request_id=rwh.request_id and ttrjd.reference_id=ttrd.request_id and ttrd.ammendement_id>0 and ttrd.advance_flag='C' " +
					"group by tttd.sfid, tttd.request_id, tttd.request_type, tttd.status, ttrd.advance_flag, ttrd.reim_flag, ttrd.sett_flag, " +
					"ttrd.stay_duration, ttrd.ammendement_id order by tttd.request_id desc";
			list = session.createSQLQuery(sql1).addScalar("sfID", Hibernate.STRING).addScalar("requestId", Hibernate.STRING).addScalar("requestType", Hibernate.STRING).
			addScalar("status", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("advanceFlag", Hibernate.STRING).addScalar("journeyDateOne", Hibernate.STRING).
			addScalar("reimFlag", Hibernate.STRING).addScalar("settFlag",Hibernate.STRING).addScalar("stayDuration", Hibernate.INTEGER).addScalar("amendmentId", Hibernate.INTEGER).
			setString(0, sfid).setResultTransformer(Transformers.aliasToBean(TadaTdTxnDTO.class)).list();
			if(list.size()!=0){
				for (TadaTdTxnDTO tadaTdTxnDTO2 : list) {
					StatusMasterDTO statusMasterDTO=new StatusMasterDTO();
					statusMasterDTO=(StatusMasterDTO)session.createCriteria(StatusMasterDTO.class).add(Expression.eq("id", tadaTdTxnDTO2.getStatus())).uniqueResult();
					tadaTdTxnDTO2.setStrStatus(statusMasterDTO.getName());
				}
			}
		} catch(Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<LeaveRequestBean> getTdAttachedLeaveList(WorkFlowMappingBean workFlowMappingBean)throws Exception{
		List<LeaveRequestBean> tdAttachedLeaveList=new ArrayList<LeaveRequestBean>();
		Session session=null;
		TadaTdSettlementDTO tadaTdSettlementDTO=null;
		List<TadaTdLeaveDetailsDTO> tadaTdLeaveList=null;
		try {
			session=hibernateUtils.getSession();
			tadaTdSettlementDTO=(TadaTdSettlementDTO)session.createCriteria(TadaTdSettlementDTO.class).add(Expression.eq("requestID", Integer.parseInt(workFlowMappingBean.getRequestId()))).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(tadaTdSettlementDTO)){
				tadaTdLeaveList=session.createCriteria(TadaTdLeaveDetailsDTO.class).add(Expression.eq("refRequestId", tadaTdSettlementDTO.getRefRequestId())).list();
				if(tadaTdLeaveList.size()!=0){
					for (TadaTdLeaveDetailsDTO tadaTdLeaveDetailsDTO : tadaTdLeaveList) {
						LeaveRequestBean leaveRequestBean=new LeaveRequestBean();
						leaveRequestBean=(LeaveRequestBean)session.createCriteria(LeaveRequestBean.class).add(Expression.eq("requestID", String.valueOf(tadaTdLeaveDetailsDTO.getLeaveRequestId()))).uniqueResult();
						tdAttachedLeaveList.add(leaveRequestBean);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return tdAttachedLeaveList;
	}
	@SuppressWarnings("unchecked")
	public List<MRODetailsDTO> getMRODetails(WorkFlowMappingBean workFlowMappingBean)throws Exception{
		List<MRODetailsDTO> tempMroDetailsList=null;
		List<MRODetailsDTO> mroDetailsList=new ArrayList<MRODetailsDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			tempMroDetailsList=session.createCriteria(MRODetailsDTO.class).add(Expression.eq("referenceID", Integer.parseInt(workFlowMappingBean.getRequestId()))).addOrder(Order.asc("id")).list();
			for (MRODetailsDTO mroDetailsDTO : tempMroDetailsList) {
				MROPaymentDetailsDTO mroPaymentDetailsDTO=new MROPaymentDetailsDTO();
				mroPaymentDetailsDTO=(MROPaymentDetailsDTO)session.createCriteria(MROPaymentDetailsDTO.class).add(Expression.eq("referenceID", mroDetailsDTO.getId())).uniqueResult();
				mroDetailsDTO.setMroPaymentDetailsDTO(mroPaymentDetailsDTO);
				mroDetailsList.add(mroDetailsDTO);
			}
		} catch (Exception e) {
			throw e;
		}
		return mroDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List<MROPaymentDetailsDTO> getMROPaymentDetailsDTO(WorkFlowMappingBean workFlowMappingBean)throws Exception{
		List<MROPaymentDetailsDTO> mroPaymentDetailsList=new ArrayList<MROPaymentDetailsDTO>();
		List<MRODetailsDTO> mroDetailsList=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			mroDetailsList=session.createCriteria(MRODetailsDTO.class).add(Expression.eq("referenceID", Integer.parseInt(workFlowMappingBean.getRequestId()))).list();
			for (MRODetailsDTO mroDetailsDTO : mroDetailsList) {
				MROPaymentDetailsDTO mroPaymentDetailsDTO=new MROPaymentDetailsDTO();
				mroPaymentDetailsDTO=(MROPaymentDetailsDTO)session.createCriteria(MROPaymentDetailsDTO.class).add(Expression.eq("referenceID", mroDetailsDTO.getId())).uniqueResult();
				mroPaymentDetailsList.add(mroPaymentDetailsDTO);
			}
		} catch (Exception e) {
			throw e;
		}
		return mroPaymentDetailsList;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaOldDetailsDTO> getAllOldDaDetails(String requetId)throws Exception{
		List<TadaTdDaOldDetailsDTO> list=new ArrayList<TadaTdDaOldDetailsDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(TadaTdDaOldDetailsDTO.class).add(Expression.eq("settlementRequestId", Integer.parseInt(requetId))).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewAccDetailsDTO> getAllNewDaAccDetails(String requetId)throws Exception{
		List<TadaTdDaNewAccDetailsDTO> list=new ArrayList<TadaTdDaNewAccDetailsDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(TadaTdDaNewAccDetailsDTO.class).add(Expression.eq("settlementRequestId", requetId)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdDaNewFoodDetailsDTO> getAllNewDaFoodDetails(String requetId)throws Exception{
		List<TadaTdDaNewFoodDetailsDTO> list=new ArrayList<TadaTdDaNewFoodDetailsDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(TadaTdDaNewFoodDetailsDTO.class).add(Expression.eq("settlementRequestId", requetId)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdRMAKmDTO> getAllRMAKmDetails(String requetId)throws Exception{
		List<TadaTdRMAKmDTO> list=new ArrayList<TadaTdRMAKmDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(TadaTdRMAKmDTO.class).add(Expression.eq("settlementRequestId", requetId)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdRMADayDTO> getAllRMADayDetails(String requetId)throws Exception{
		List<TadaTdRMADayDTO> list=new ArrayList<TadaTdRMADayDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			list=session.createCriteria(TadaTdRMADayDTO.class).add(Expression.eq("settlementRequestId", requetId)).list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public TadaRequestBean getTdJourneyDates(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		String departureDate=null;
		String arrivalDate=null;
	    Float tadaDaPercentage=null;    // new propertety for da retrive
		try {
			session=hibernateUtils.getSession();
			departureDate=(String)session.createSQLQuery("select to_char(min(departure_date),'DD-Mon-yyyy') from tada_td_req_journey_details where reference_id=?").setInteger(0, Integer.parseInt(tadaRequestBean.getRequestId())).uniqueResult();
			arrivalDate=(String)session.createSQLQuery("select to_char(max(arrival_date),'DD-Mon-yyyy') from tada_td_req_journey_details where reference_id=?").setInteger(0, Integer.parseInt(tadaRequestBean.getRequestId())).uniqueResult();
			tadaDaPercentage=  Float.parseFloat(session.createSQLQuery("SELECT max(daontour.da_on_tour) FROM tada_da_on_tour_master daontour,(SELECT DA_VALUE FROM PAY_DEARNESS_ALLOWANCE_MASTER"
					+ " WHERE STATUS=1 AND DA_DATE = (SELECT MAX(DA_DATE) FROM PAY_DEARNESS_ALLOWANCE_MASTER WHERE STATUS=1 )) payda"
					+ " WHERE status =1 AND  (da_range_to<daontour.da_on_tour or da_range_from <daontour.da_on_tour)group by payda.DA_VALUE").uniqueResult().toString());     //This is new value of new Da percentage.
		    tadaRequestBean.setTadaDaPercentage(tadaDaPercentage);             //This is new conditon for Tadada value
			tadaRequestBean.setDepartureDateOne(departureDate);
			tadaRequestBean.setStrArrivalDate(arrivalDate);
			tadaRequestBean.setDepartureDate(CPSUtils.convertStringToDate(departureDate));
			tadaRequestBean.setArrivalDate(CPSUtils.convertStringToDate(arrivalDate));
		} catch (Exception e) {
			throw e;
		}
		return tadaRequestBean;
	}
	@SuppressWarnings("unchecked")
	public List<TadaTdReqJourneyDTO> getTdReqJourneyList(TadaRequestBean tadaRequestBean)throws Exception{
		List<TadaTdReqJourneyDTO> tempTdReqJourneyList=null;
		List<TadaTdReqJourneyDTO> tdReqJourneyList=new ArrayList<TadaTdReqJourneyDTO>();
		List<TadaTdReqJourneyDTO> tdReqJourneyList1=new ArrayList<TadaTdReqJourneyDTO>();
		List<TadaTdReqJourneyDTO> tdReqJourneyList2=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			for(int i=0;i<=i;i++){
				tempTdReqJourneyList=session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", Integer.parseInt(tadaRequestBean.getRequestId()))).list();
				for (TadaTdReqJourneyDTO tadaTdReqJourneyDTO : tempTdReqJourneyList) {
					tdReqJourneyList.add(tadaTdReqJourneyDTO);
				}
				TadaApprovalRequestDTO tadaApprovalRequestDTO=(TadaApprovalRequestDTO)session.createCriteria(TadaApprovalRequestDTO.class).add(Expression.eq("ammendementId", tadaRequestBean.getRequestId())).uniqueResult();
				if(!CPSUtils.isNullOrEmpty(tadaApprovalRequestDTO)){
					/*tempTdReqJourneyList1=session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("referenceId", Integer.parseInt(tadaApprovalRequestDTO.getRequestId()))).list();
					for (TadaTdReqJourneyDTO tadaTdReqJourneyDTO2 : tempTdReqJourneyList1) {
						tdReqJourneyList.add(tadaTdReqJourneyDTO2);
					}*/
					tadaRequestBean.setRequestId(tadaApprovalRequestDTO.getRequestId());
				}else{
					break;
				}
			}
			/*for (TadaTdReqJourneyDTO tadaTdReqJourneyDTO : tdReqJourneyList) {
				TadaTdReqJourneyDTO tadaTdReqJourneyDTO2=new TadaTdReqJourneyDTO();
				BeanUtils.copyProperties(tadaTdReqJourneyDTO, tadaTdReqJourneyDTO2);
				tadaTdReqJourneyDTO2.setId(0);
				tadaTdReqJourneyDTO2.setReferenceId(0);
				tadaTdReqJourneyDTO2.setNoOfDays(0);
				tadaTdReqJourneyDTO2.setTicketFare(0);
				tadaTdReqJourneyDTO2.setTicketFareAftRes(0);
				tadaTdReqJourneyDTO2.setArrivalDate(null);
				tadaTdReqJourneyDTO2.setDepartureDate(null);
				tdReqJourneyList1.add(tadaTdReqJourneyDTO2);
			}*/
			for(int i=0;i<tdReqJourneyList.size();i++){
				for(int j=i+1;j<tdReqJourneyList.size();j++){
					if(!CPSUtils.isNullOrEmpty(tdReqJourneyList.get(i)) && !CPSUtils.isNullOrEmpty(tdReqJourneyList.get(j))){
						if(CPSUtils.compareString(tdReqJourneyList.get(i).getStrDepartureDate(), tdReqJourneyList.get(j).getStrDepartureDate()) && 
								CPSUtils.compareString(tdReqJourneyList.get(i).getStrArrivalDate(), tdReqJourneyList.get(j).getStrArrivalDate()) && 
								CPSUtils.compareString(tdReqJourneyList.get(i).getFromPlace(), tdReqJourneyList.get(j).getFromPlace()) && 
								CPSUtils.compareString(tdReqJourneyList.get(i).getToPlace(), tdReqJourneyList.get(j).getToPlace()) && 
								CPSUtils.compareString(tdReqJourneyList.get(i).getConveyanceMode(), tdReqJourneyList.get(j).getConveyanceMode()) && 
								CPSUtils.compareString(tdReqJourneyList.get(i).getClassOfEntitlement(), tdReqJourneyList.get(j).getClassOfEntitlement()) && 
								CPSUtils.compareString(tdReqJourneyList.get(i).getTatkalQuota(), tdReqJourneyList.get(j).getTatkalQuota())){
							tdReqJourneyList.set(j, null);
						}
					}
				}
			}
			for (TadaTdReqJourneyDTO tadaTdReqJourneyDTO : tdReqJourneyList) {
				if(!CPSUtils.isNullOrEmpty(tadaTdReqJourneyDTO)){
					tdReqJourneyList1.add(tadaTdReqJourneyDTO);
				}
			}
			for (TadaTdReqJourneyDTO tadaTdReqJourneyDTO : tdReqJourneyList1) {
				float ticketFare=0;
				float ticketFareAftRes=0;
				tdReqJourneyList2=session.createCriteria(TadaTdReqJourneyDTO.class).add(Expression.eq("strDepartureDate", tadaTdReqJourneyDTO.getStrDepartureDate())).add(Expression.eq("strArrivalDate", tadaTdReqJourneyDTO.getStrArrivalDate()))
				.add(Expression.eq("fromPlace", tadaTdReqJourneyDTO.getFromPlace())).add(Expression.eq("toPlace", tadaTdReqJourneyDTO.getToPlace())).add(Expression.eq("conveyanceMode", tadaTdReqJourneyDTO.getConveyanceMode()))
				.add(Expression.eq("classOfEntitlement", tadaTdReqJourneyDTO.getClassOfEntitlement())).add(Expression.eq("tatkalQuota", tadaTdReqJourneyDTO.getTatkalQuota())).list();
				if(!CPSUtils.isNullOrEmpty(tdReqJourneyList2)){
					for (TadaTdReqJourneyDTO tadaTdReqJourneyDTO1 : tdReqJourneyList2) {
						if(tadaTdReqJourneyDTO1.getTicketFare()>0){
							ticketFare=tadaTdReqJourneyDTO1.getTicketFare();
						}
						if(tadaTdReqJourneyDTO1.getTicketFareAftRes()>0){
							ticketFareAftRes=tadaTdReqJourneyDTO1.getTicketFareAftRes();
						}
					}
				}
				tadaTdReqJourneyDTO.setTicketFare(ticketFare);
				tadaTdReqJourneyDTO.setTicketFareAftRes(ticketFareAftRes);
			}
		} catch (Exception e) {
			throw e;
		}
		return tdReqJourneyList1;
	}
	public WorkFlowMappingBean getProjectDirSfid(WorkFlowMappingBean workFlowMappingBean)throws Exception{
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			DynamicWorkflowTxnDTO dynamicWorkflowTxnDTO=(DynamicWorkflowTxnDTO)session.createCriteria(DynamicWorkflowTxnDTO.class).add(Expression.eq("requestID", Integer.parseInt(workFlowMappingBean.getRequestId()))).uniqueResult();
			workFlowMappingBean.setProjectDirSfid(dynamicWorkflowTxnDTO.getDynamicTo());
		} catch (Exception e) {
			throw e;
		}
		return workFlowMappingBean;
	}
	@SuppressWarnings("unchecked")
	public List<WorkFlowMappingBean> getWorkFlowList(int reqTypeId)throws Exception{
		Session session=null;
		List<WorkFlowMappingBean> list=null;
		String workflowId=null;
		try {
			session=hibernateUtils.getSession();
			workflowId=(String)session.createSQLQuery("select to_char(workflow_id) from workflow_master where workflow_name=" +
					"(select request_type from request_master where request_type_id=?)").setInteger(0, reqTypeId).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(workflowId)){
				list=session.createCriteria(WorkFlowMappingBean.class).add(Expression.eq("workflowId", workflowId)).addOrder(Order.asc("stageId")).list();
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public TadaRequestBean getSuperiorIds(TadaRequestBean tadaRequestBean)throws Exception{
		try {
			for(int i=0;i<tadaRequestBean.getBuildUpWfList().size()-1;i++){
				/*if(CPSUtils.compareStrings(tadaRequestBean.getBuildUpWfList().get(i).getTo(), CPSConstants.BOSSID)){
					if(!CPSUtils.isNullOrEmpty(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.BOSSID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"))){
						tadaRequestBean.setBossId(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.BOSSID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"));
					}
				}*/
				if(CPSUtils.compareStrings(tadaRequestBean.getBuildUpWfList().get(i).getTo(), CPSConstants.BOSSID)){
					if(!CPSUtils.isNullOrEmpty(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.BOSSID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"))){
						tadaRequestBean.setBossId(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.BOSSID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"));
					}
				}
				if(!CPSUtils.isNullOrEmpty(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), "9", String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"))){
					tadaRequestBean.setTdId(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), "9", String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"));
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getTdId()))
						tadaRequestBean.setTdId(tadaRequestBean.getTdId().concat("#"+getRequesterRoleID(tadaRequestBean.getTdId().split("#")[1])+"#"+getNameInServiceBook(tadaRequestBean.getTdId().split("#")[0])));
				}
				if(!CPSUtils.isNullOrEmpty(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.BOSSESBOSS, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"))){
					tadaRequestBean.setBossesBossId(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.BOSSESBOSS, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"));
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getBossesBossId()))
						tadaRequestBean.setBossesBossId(tadaRequestBean.getBossesBossId().concat("#"+getRequesterRoleID(tadaRequestBean.getBossesBossId().split("#")[1])+"#"+getNameInServiceBook(tadaRequestBean.getBossesBossId().split("#")[0])));
				}
				if(!CPSUtils.isNullOrEmpty(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.DYTECHDIRID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"))){
					tadaRequestBean.setDyTechDirId(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.DYTECHDIRID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"));
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getDyTechDirId()))
						tadaRequestBean.setDyTechDirId(tadaRequestBean.getDyTechDirId().concat("#"+getRequesterRoleID(tadaRequestBean.getDyTechDirId().split("#")[1])+"#"+getNameInServiceBook(tadaRequestBean.getDyTechDirId().split("#")[0])));
				}
				if(!CPSUtils.isNullOrEmpty(txWorkflowProcess.getInstanceSFID("4"))){
					tadaRequestBean.setAdId(txWorkflowProcess.getInstanceSFID("4"));
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getAdId()))
						tadaRequestBean.setAdId(tadaRequestBean.getAdId().concat("#"+getRequesterRoleID(tadaRequestBean.getAdId().split("#")[1])+"#"+getNameInServiceBook(tadaRequestBean.getAdId().split("#")[0])));
				}
				if(!CPSUtils.isNullOrEmpty(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.LABDIRID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"))){
					tadaRequestBean.setLabDirId(txWorkflowProcess.getAssignedID(tadaRequestBean.getSfid(), CPSConstants.LABDIRID, String.valueOf(tadaRequestBean.getEmpDetailsList().getOffice()), CPSConstants.APPROVED, tadaRequestBean.getRequesterRoleID(), "0"));
					if(!CPSUtils.isNullOrEmpty(tadaRequestBean.getLabDirId()))
						tadaRequestBean.setLabDirId(tadaRequestBean.getLabDirId().concat("#"+getRequesterRoleID(tadaRequestBean.getLabDirId().split("#")[1])+"#"+getNameInServiceBook(tadaRequestBean.getLabDirId().split("#")[0])));
				}
				if(CPSUtils.compareStrings(tadaRequestBean.getBuildUpWfList().get(i).getFrom(), CPSConstants.DYTECHDIRID)){
					tadaRequestBean.setType("yes");
				}else{
					tadaRequestBean.setType("no");
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return tadaRequestBean;
	}
	@SuppressWarnings("unchecked")
	public List<RequestCommonBean> getPendingReqList(TadaRequestBean tadaRequestBean)throws Exception{
		List<String> reqIdsList=null;
		List<RequestCommonBean> requestsList=new ArrayList<RequestCommonBean>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			reqIdsList = session.createQuery("select requestId from TadaApprovalRequestDTO where sfID=? and status=2").setString(0, tadaRequestBean.getSfid()).list();
			if(!CPSUtils.isNullOrEmpty(reqIdsList)){
				for (String reqId : reqIdsList) {
					RequestCommonBean reqBean=new RequestCommonBean();
					reqBean = (RequestCommonBean)session.createCriteria(RequestCommonBean.class).add(Expression.eq("requestID", reqId)).add(Expression.eq("status", 2)).uniqueResult();
					requestsList.add(reqBean);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return requestsList;
	}
	public List<KeyValueDTO> getKeyList(TadaRequestBean tadaRequestBean)throws Exception{
		List<KeyValueDTO> list=new ArrayList<KeyValueDTO>();
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			for(int i=0;i<tadaRequestBean.getPendingReqList().size();i++){
				KeyValueDTO keyDTO=new KeyValueDTO();
				keyDTO.setName((String)session.createQuery("select name from WorkFlowRelationDTO where id=?").setInteger(0, tadaRequestBean.getPendingReqList().get(i).getAssignedRoleID()).uniqueResult());
				/*String name=(String)session.createQuery("select to_char(name) from WorkFlowRelationDTO where id=(select to from WorkFlowMappingBean " +
						"where workflowId=? and stageId=?)").setString(0, tadaRequestBean.getPendingReqList().get(i).getWorkflowID()).
						setString(1, tadaRequestBean.getPendingReqList().get(i).getStageID()).uniqueResult();
				keyDTO.setName(name);
				String id=(String)session.createQuery("select to_char(id) from WorkFlowRelationDTO where id=(select to from WorkFlowMappingBean " +
						"where workflowId=? and stageId=?)").setString(0, tadaRequestBean.getPendingReqList().get(i).getWorkflowID()).
						setString(1, tadaRequestBean.getPendingReqList().get(i).getStageID()).uniqueResult();*/
				keyDTO.setId(tadaRequestBean.getPendingReqList().get(i).getAssignedRoleID());
				keyDTO.setKey(tadaRequestBean.getPendingReqList().get(i).getParentID());
				keyDTO.setValue((String)session.createQuery("select nameInServiceBook from EmployeeBean where userSfid=?").setString(0, tadaRequestBean.getPendingReqList().get(i).getParentID()).uniqueResult());
				keyDTO.setFlag(tadaRequestBean.getPendingReqList().get(i).getRequestID());
				list.add(keyDTO);
			}
			for (KeyValueDTO keyValueDTO : list) {
				if(CPSUtils.isNullOrEmpty(keyValueDTO.getName())){
					keyValueDTO.setName((String)session.createQuery("select name from OrgInstanceDTO where id=?").setInteger(0, keyValueDTO.getId()).uniqueResult());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String getRequesterRoleID(String orgRoleID)throws Exception{
		Session session=null;
		String orgRoleName=null;
		try {
			session=hibernateUtils.getSession();
			orgRoleName=(String)session.createQuery("select name from OrgInstanceDTO where id=?").setInteger(0, Integer.parseInt(orgRoleID)).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return orgRoleName;
	}
	public String getNameInServiceBook(String sfid)throws Exception{
		Session session=null;
		String empName=null;
		try {
			session=hibernateUtils.getSession();
			empName=(String)session.createQuery("select nameInServiceBook from EmployeeBean where userSfid=?").setString(0, sfid).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return empName;
	}
	@SuppressWarnings("unchecked")
	public List<TadaProjectDirectorsDTO> getProjList(TadaRequestBean tadaRequestBean)throws Exception{
		Session session=null;
		List<TadaProjectDirectorsDTO> list=null;
		DynamicWorkflowTxnDTO dynDTO=null;
		try {
			session=hibernateUtils.getSession();
			dynDTO=(DynamicWorkflowTxnDTO)session.createCriteria(DynamicWorkflowTxnDTO.class).add(Expression.eq("requestID", Integer.parseInt(tadaRequestBean.getRequestId()))).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(dynDTO)){
				if(CPSUtils.compareStrings(dynDTO.getDynamicTo().toUpperCase(), "SF0008"))
					list=session.createCriteria(TadaProjectDirectorsDTO.class).add(Expression.eq("status", 1)).list();
				else
					list=session.createCriteria(TadaProjectDirectorsDTO.class).add(Expression.eq("sfID",dynDTO.getDynamicTo())).add(Expression.eq("status", 1)).list();
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public String checkSettlementDetails(TadaRequestProcessBean tadaRPB)throws Exception{
		String message=null;
		Session session=null;
		try {
			session=hibernateUtils.getSession();
			EmployeeClaimDetailsDTO empClaimDetailsDTO=(EmployeeClaimDetailsDTO)session.createCriteria(EmployeeClaimDetailsDTO.class).add(Expression.eq("refRequestId", Integer.parseInt(tadaRPB.getReferenceRequestID()))).add(Expression.or(Expression.eq("requestTypeID", 48), Expression.eq("requestTypeID", 49))).add(Expression.eq("workFlowStatus", 2)).uniqueResult();
			if(!CPSUtils.isNullOrEmpty(empClaimDetailsDTO)){
				tadaRPB.setRemarks("TD Settlement Request is already applied");
				message=CPSConstants.FAILED;
			}else{
				message=CPSConstants.SUCCESS;
			}
			
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public String getTdUserRemarks(WorkFlowMappingBean workflowMappingBean)throws Exception{
		Session session=null;
		TadaTdSettlementDTO tadaTdSettlementDTO=null;
		try {
			session=hibernateUtils.getSession();
			tadaTdSettlementDTO=(TadaTdSettlementDTO)session.createCriteria(TadaTdSettlementDTO.class).add(Expression.eq("requestID", Integer.parseInt(workflowMappingBean.getRequestId()))).uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return tadaTdSettlementDTO.getUserRemarks();
	}
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinReimSanctionDetails(
			TadaRequestBean tadaRequestBean)throws Exception {                                  //This is new Method of CdaReimbursmentDetails

		List<TadaApprovalRequestDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		List<Integer> reqidlist = new ArrayList<Integer>();
		try{
			session=hibernateUtils.getSession();
			sql="SELECT ttrd.request_id requestId, ecd.request_id referenceRequestID ,ttrd.sfid sfid,em.name_in_service_book name,"
					+ "ecd.total_amount tadaAdvanceAmount, ttrd.authorized_move_id AS authorizedMove, ttrd.project_name AS projectName,"
					+ "  MAX(rwh.id) historyID,fd.SANCTION_NUMBER sanctionNo,fd.BILL_NUMBER billNo,"
					+ "fd.ACCOUNTENT_SIGN accountentSign, cd.dv_no dvNo,to_char(cd.dv_date,'DD-Mon-YYYY')as dvDate,cd.cda_amount cdaAmount FROM tada_td_request_details ttrd,emp_master em,"
					+ "emp_claim_details ecd ,request_workflow_history rwh,finance_details fd left outer join cda_details cd on(cd.reference_id = fd.id)	WHERE ttrd.reim_flag  IN ('Y')	AND ttrd.sfid=em.sfid"
					+ "  AND rwh.request_id=ecd.request_id AND ttrd.request_id=ecd.ref_request_id AND ecd.request_type_id=49 and ecd.id=fd.reference_id"
					+ " GROUP BY ttrd.request_id, ecd.request_id,ttrd.sfid, em.name_in_service_book, ecd.total_amount,ttrd.authorized_move_id,"
					+ " ttrd.project_name, fd.SANCTION_NUMBER, fd.BILL_NUMBER, fd.ACCOUNTENT_SIGN, cd.dv_no, cd.dv_date,cd.cda_amount ORDER BY ecd.request_id DESC";
			
			finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).
					addScalar("name", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).
					addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).
					addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).addScalar("cdaAmount", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			
			
			
//			session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).
//			addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
//	
			
			if(!finAdvList.isEmpty()){
				for (TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList) {
					reqidlist.add(Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID()));		
				}
					List<TadaTdSettlementDTO> tadaTdSettlementDTO=  new  ArrayList<TadaTdSettlementDTO>();
					tadaTdSettlementDTO= session.createCriteria(TadaTdSettlementDTO.class).add(Expression.in("requestID", reqidlist)).list();
					
				for(TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList){
					for(TadaTdSettlementDTO tadaTdSettlementDTO1 : tadaTdSettlementDTO){
						if(tadaTdSettlementDTO1.getRequestID()==Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID())){
					tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO1.getAmountClaimedAftRes());
//     				break;
					}
					}
				}
				
			}
			
		
		//	tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO.getAmountClaimedAftRes());
			
			tadaRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
		}catch(Exception e){
			throw e;
		}
		return finAdvList;
	
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TadaApprovalRequestDTO> getFinSettlementSanctionDetails(TadaRequestBean tadaRequestBean)throws Exception{     // This is new function for CdaTadaSettlementDetails 
		List<TadaApprovalRequestDTO> finAdvList=null;
		Session session=null;
		String sql=null;
		List<Integer> reqidlist = new ArrayList<Integer>();
		try{
			session=hibernateUtils.getSession();
		
//			sql="select ecd1.id as id,ttrd.request_id requestId,ecd.request_id as referenceRequestID,ttrd.sfid sfid,em.name_in_service_book name," +
//					"ecd.total_amount tadaSettlementAmount,ttard.advance_amount_aft_res as tadaAdvanceAmount,max(rwh.id) as historyID,ttrd.authorized_move_id as authorizedMove,ttrd.project_name as projectName,fd.sanction_number as sanctionNo from tada_td_request_details ttrd," +
//					"emp_master em,emp_claim_details ecd,emp_claim_details ecd1,tada_td_adv_request_details ttard,request_workflow_history rwh,finance_details fd where " +
//					"ttrd.advance_flag in ('Y','C') and ttrd.sfid=em.sfid and ttrd.request_id=ecd.ref_request_id and ttrd.request_id=ecd1.ref_request_id and " +
//					"ecd.request_type_id=48 and ecd.workflow_status=8 and rwh.request_id=ecd.request_id and ecd.ref_request_id=ecd1.ref_request_id and ecd1.workflow_status=8 " +
//					"and ecd1.request_type_id=47 and ttard.ref_request_id=ecd1.ref_request_id and ttard.status=8 and fd.reference_id=ecd1.id group by ecd1.id, ttrd.request_id, ecd.request_id, " +
//					"ttrd.sfid, em.name_in_service_book, ecd.total_amount, ttard.advance_amount_aft_res,ttrd.authorized_move_id ,ttrd.project_name,fd.sanction_number order by ecd.request_id desc";			
		
			      sql="SELECT  distinct ttrd.request_id requestId,ecd.request_id referenceRequestID ,ttrd.sfid sfid, em.name_in_service_book name,"
			      		+ "ecd.total_amount tadaSettlementAmount, ttard.advance_amount_aft_res as tadaAdvanceAmount, ttrd.authorized_move_id AS authorizedMove,"
			      		+ "  ttrd.project_name AS projectName, MAX(rwh.id) historyID,fd.SANCTION_NUMBER sanctionNo, fd.BILL_NUMBER billNo,"
			      		+ " fd.ACCOUNTENT_SIGN accountentSign, cd.dv_no dvNo,TO_CHAR(cd.dv_date,'DD-Mon-YYYY')AS dvDate,cd.cda_amount cdaAmount FROM tada_td_request_details ttrd,"
			      		+ " emp_master em, tada_td_adv_request_details ttard, emp_claim_details ecd , request_workflow_history rwh,finance_details fd LEFT OUTER JOIN cda_details cd "
			      		+ "ON(cd.reference_id= fd.id) WHERE ttrd.sett_flag  IN ('Y') AND ttrd.sfid=em.sfid   AND rwh.request_id=ecd.request_id"
			      		+ " AND ttrd.request_id    =ecd.ref_request_id AND ecd.request_type_id=48 AND ecd.id=fd.reference_id  "
			      		+ "AND ttard.ref_request_id =ecd.ref_request_id AND ttard.status =8 GROUP BY ttrd.request_id, ecd.request_id,ttrd.sfid,"
			      		+ "em.name_in_service_book,ecd.total_amount,ttard.advance_amount_aft_res,ttrd.authorized_move_id, ttrd.project_name,"
			      		+ "fd.SANCTION_NUMBER,fd.BILL_NUMBER,fd.ACCOUNTENT_SIGN, cd.dv_no,cd.dv_date,cd.cda_amount ORDER BY ecd.request_id DESC";
			
//	finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).
//			      addScalar("name", Hibernate.STRING).addScalar("tadaSettlementAmount",Hibernate.INTEGER).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			      finAdvList =  session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).
					addScalar("name", Hibernate.STRING).addScalar("tadaSettlementAmount", Hibernate.INTEGER).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).
					addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).
					addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).addScalar("dvNo", Hibernate.STRING).addScalar("dvDate", Hibernate.STRING).addScalar("cdaAmount", Hibernate.FLOAT).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
			      if(!finAdvList.isEmpty()){
		
	
	for (TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList) {
				reqidlist.add(Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID()));		 
			}
				List<TadaTdSettlementDTO> tadaTdSettlementDTO=  new  ArrayList<TadaTdSettlementDTO>();
				tadaTdSettlementDTO= session.createCriteria(TadaTdSettlementDTO.class).add(Expression.in("requestID", reqidlist)).list();
				
			for(TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList){
				for(TadaTdSettlementDTO tadaTdSettlementDTO1 : tadaTdSettlementDTO){
					if(tadaTdSettlementDTO1.getRequestID()==Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID())){    //newly added condition
				tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO1.getAmountClaimedAftRes());
  			break;
					}
				}
			}
		//	tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO.getAmountClaimedAftRes());
			
			tadaRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
	}
	}catch(Exception e){
			throw e;
		}
		return finAdvList;
	}
	




//This is function for getFinAdvSanctionDetails
@SuppressWarnings("unchecked")
public List<TadaApprovalRequestDTO> getFinAdvSanctionDetails(TadaRequestBean tadaRequestBean)throws Exception{
	
	List<TadaApprovalRequestDTO> finAdvList=null;
	Session session=null;
	String sql=null;
	List<Integer> reqidlist = new ArrayList<Integer>();
	try{
		session=hibernateUtils.getSession();
	
		     
		 sql="SELECT  distinct ttrd.request_id requestId,ttard.request_id referenceRequestID ,ttrd.sfid sfid, em.name_in_service_book name,"                              //Query has been changed . condition add for cda records
		      		+ " to_number(ttard.advance_amount) tadaAdvanceAmount,to_number(ttard.advance_amount_aft_res)  AS advanceAmountAftRes,ttrd.authorized_move_id AS authorizedMove,"
		      		+ "  ttrd.project_name AS projectName, MAX(rwh.id) historyID,ttard.da_type AS daType , TO_CHAR(ttrd.departure_date,'dd-Mon-yyyy') AS departureDateOne,"
		      		+ "fd.SANCTION_NUMBER sanctionNo, fd.BILL_NUMBER billNo,"
		      		+ " fd.ACCOUNTENT_SIGN accountentSign  FROM tada_td_request_details ttrd,"
		      		+ " emp_master em, tada_td_adv_request_details ttard, emp_claim_details ecd , request_workflow_history rwh,finance_details fd LEFT OUTER JOIN cda_details cd "
		      		+ "ON(cd.reference_id= fd.id) WHERE ttrd.advance_flag  IN ('Y') AND ttrd.sfid=em.sfid   AND rwh.request_id=ecd.request_id"
		      		+ " AND ttrd.request_id    =ecd.ref_request_id AND ecd.request_type_id=47 AND ecd.id=fd.reference_id  "
		      		+ "AND ttard.ref_request_id =ecd.ref_request_id AND ttard.status =8 AND ttard.da_type='cda' GROUP BY ttrd.request_id, ttard.request_id,ttrd.sfid,"
		      		+ "em.name_in_service_book,  ttard.advance_amount,ttard.advance_amount_aft_res,ttrd.authorized_move_id, ttrd.project_name,ttard.da_type, ttrd.departure_date,"
		      		+ "fd.SANCTION_NUMBER,fd.BILL_NUMBER,fd.ACCOUNTENT_SIGN ORDER BY ttard.request_id DESC";
		
//finAdvList = session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).
//		      addScalar("name", Hibernate.STRING).addScalar("tadaSettlementAmount",Hibernate.INTEGER).addScalar("tadaAdvanceAmount", Hibernate.INTEGER).addScalar("historyID", Hibernate.STRING).addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list();
		      finAdvList =  session.createSQLQuery(sql).addScalar("requestId", Hibernate.STRING).addScalar("referenceRequestID", Hibernate.STRING).addScalar("sfid", Hibernate.STRING).
				addScalar("name", Hibernate.STRING).addScalar("tadaAdvanceAmount",Hibernate.INTEGER).addScalar("advanceAmountAftRes",Hibernate.INTEGER).
				addScalar("authorizedMove", Hibernate.STRING).addScalar("projectName", Hibernate.STRING).addScalar("historyID", Hibernate.STRING).addScalar("daType",Hibernate.STRING).addScalar("departureDateOne",Hibernate.STRING).addScalar("sanctionNo", Hibernate.STRING).
				addScalar("billNo", Hibernate.STRING).addScalar("accountentSign", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(TadaApprovalRequestDTO.class)).list(); 
	      if(!finAdvList.isEmpty()){
for (TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList) {
			reqidlist.add(Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID()));		 
		}
			List<TadaTdSettlementDTO> tadaTdSettlementDTO=  new  ArrayList<TadaTdSettlementDTO>();
			tadaTdSettlementDTO= session.createCriteria(TadaTdSettlementDTO.class).add(Expression.in("requestID", reqidlist)).list();
			
		for(TadaApprovalRequestDTO tadaApprovalRequestDTO : finAdvList){
			for(TadaTdSettlementDTO tadaTdSettlementDTO1 : tadaTdSettlementDTO){
				if(tadaTdSettlementDTO1.getRequestID()==Integer.parseInt(tadaApprovalRequestDTO.getReferenceRequestID())){
			tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO1.getAmountClaimedAftRes());
		    break;
				}
			}
		}
	//	tadaApprovalRequestDTO.setTdSettAmountAftRes(tadaTdSettlementDTO.getAmountClaimedAftRes());
		
		tadaRequestBean.setAccountOfficerList(session.createCriteria(SingingAuthorityDTO.class).add(Expression.eq("type", "ACC")).add(Expression.eq("status", 1)).list());
	
		      }
		      }catch(Exception e){
		throw e;
	}
	return finAdvList;
}

}

